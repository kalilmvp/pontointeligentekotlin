package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.documents.Lancamento
import com.kalilpeixoto.pontointeligentekotlin.dtos.LancamentoDTO
import com.kalilpeixoto.pontointeligentekotlin.enums.PerfilEnum
import com.kalilpeixoto.pontointeligentekotlin.enums.TipoEnum
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
import com.kalilpeixoto.pontointeligentekotlin.services.LancamentoService
import com.kalilpeixoto.pontointeligentekotlin.utils.SenhaUtils
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.text.SimpleDateFormat
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class LancamentoControllerTest {

    private val urlBase: String = "/api/lancamentos/"
    private val ID_FUNCIONARIO: String = "1"
    private val ID_LANCAMENTO: String = "1"
    private val tipo: String = TipoEnum.INICIO_TRABALHO.name
    private val data: Date = Date()

    private val FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val lancamentoService: LancamentoService? = null
    @MockBean
    private val funcionarioService: FuncionarioService? = null

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testCadastrar() {
        val lancamento: Lancamento = this.obterDadosLancamento()

        BDDMockito.given(this.funcionarioService?.buscarPorId(ID_FUNCIONARIO)).willReturn(funcionario())
        BDDMockito.given(this.lancamentoService?.persistir(lancamento)).willReturn(lancamento)

        mockMvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.data.tipo").value(tipo))
                .andExpect(jsonPath("$.data.data").value(FORMATTER.format(data)))
                .andExpect(jsonPath("$.data.funcionarioId").value(ID_FUNCIONARIO))
                .andExpect(jsonPath("$.erros").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testCadastrarComFuncionarioInvalido() {
        val lancamento: Lancamento = this.obterDadosLancamento()

        BDDMockito.given(this.funcionarioService?.buscarPorId(ID_FUNCIONARIO)).willReturn(null)

        mockMvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(this.obterJsonRequisicaoPost())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.erros").isNotEmpty)
                .andExpect(jsonPath("$.erros").value("Funcionário não encontrado, ID inexistente"))
                .andExpect(jsonPath("$.data").isEmpty)
    }


    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun testRemoverLancamento() {
        BDDMockito.given(this.lancamentoService?.buscarPorId(ID_LANCAMENTO)).willReturn(this.obterDadosLancamento())

        mockMvc!!.perform(MockMvcRequestBuilders.delete(urlBase + ID_LANCAMENTO)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Throws(JsonProcessingException::class)
    private fun obterJsonRequisicaoPost(): String {
        val lancamentoDto: LancamentoDTO = LancamentoDTO(
                FORMATTER.format(data), tipo, ID_FUNCIONARIO, "Descrição",
                "1.234,4.234")
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(lancamentoDto)
    }

    private fun obterDadosLancamento(): Lancamento =
            Lancamento(ID_LANCAMENTO, data, TipoEnum.valueOf(tipo), ID_FUNCIONARIO, "Nova descrição")

    private fun funcionario(): Funcionario = Funcionario("Nome", "email@email.com",
            SenhaUtils().gerarBCrypt("123456"), "11122233444", PerfilEnum.ROLE_USUARIO,
            "1", 0.0, 0.0f, 0.0f, ID_FUNCIONARIO)
}