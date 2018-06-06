package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.dtos.FuncionarioDTO
import com.kalilpeixoto.pontointeligentekotlin.dtos.LancamentoDTO
import com.kalilpeixoto.pontointeligentekotlin.enums.PerfilEnum
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
import com.kalilpeixoto.pontointeligentekotlin.utils.SenhaUtils
import org.junit.Before
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class FuncionarioControllerTest{

    private val urlBase: String = "/api/funcionarios"

    private val FUNCIONARIO_ID: String = "1"

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val funcionarioService: FuncionarioService? = null

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun atualizarTest(){
        val funcionario: Funcionario = funcionario()

        BDDMockito.given(this.funcionarioService?.buscarPorId(FUNCIONARIO_ID)).willReturn(funcionario)

        mockMvc!!.perform(MockMvcRequestBuilders.put(urlBase.plus("/").plus(FUNCIONARIO_ID))
                .content(this.obterJsonRequisicaoPost(funcionario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nome").value(funcionario.nome))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(funcionario.email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(FUNCIONARIO_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun atualizarFuncionarioNaoEncontradoTest(){
        val funcionario: Funcionario = funcionario()

        BDDMockito.given(this.funcionarioService?.buscarPorId("1212")).willReturn(funcionario)

        mockMvc!!.perform(MockMvcRequestBuilders.put(urlBase.plus("/").plus(FUNCIONARIO_ID))
                .content(this.obterJsonRequisicaoPost(funcionario))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value("Funcionário não encontrado."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }

    @Throws(JsonProcessingException::class)
    private fun obterJsonRequisicaoPost(funcionario: Funcionario): String {
        return ObjectMapper().writeValueAsString(FuncionarioDTO(funcionario.nome, funcionario.email, "",
                funcionario.valorHora.toString(), funcionario.qtdHorasTrabalhoDia.toString(),
                funcionario.qtdHorasAlmoco.toString(), funcionario.id))
    }

    private fun funcionario(): Funcionario = Funcionario("Nome",
            "teste@email.com", SenhaUtils().gerarBCrypt("123456"), "121212",
            PerfilEnum.ROLE_USUARIO, "1", 0.0, 0.0f, 0.0f,
            FUNCIONARIO_ID)


}