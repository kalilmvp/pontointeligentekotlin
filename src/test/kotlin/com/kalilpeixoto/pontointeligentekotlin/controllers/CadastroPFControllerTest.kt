package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.dtos.CadastroPfDTO
import com.kalilpeixoto.pontointeligentekotlin.enums.PerfilEnum
import com.kalilpeixoto.pontointeligentekotlin.services.EmpresaService
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CadastroPFControllerTest{

    private val urlBase: String = "/api/cadastrar-pf"

    private val EMPRESA_ID: String = "1"
    private val FUNCIONARIO_ID: String = "1"
    private val CNPJ: String = "60609756000164"

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val empresaService: EmpresaService? = null
    @MockBean
    private val funcionarioService: FuncionarioService? = null

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun adicionarTest(){
        val funcionario: Funcionario = funcionario()
        val empresa = empresa()

        BDDMockito.given(this.empresaService?.findByCnpj(CNPJ)).willReturn(empresa)
        BDDMockito.given(this.funcionarioService?.persistir(funcionario)).willReturn(funcionario)

        mockMvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(this.obterJsonRequisicaoPost(funcionario, empresa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.nome").value(funcionario.nome))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value(funcionario.email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(FUNCIONARIO_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cpf").value(funcionario.cpf))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.valorHora").value(funcionario.valorHora))
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun adicionarPfComEmpresaNaoCadastradaTest(){
        val funcionario: Funcionario = funcionario()
        val empresa = empresa()

        BDDMockito.given(this.empresaService?.findByCnpj(CNPJ)).willReturn(null)

        mockMvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(this.obterJsonRequisicaoPost(funcionario, empresa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value("Empresa não cadastrada."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)

    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun adicionarPFJaCadastradoTest(){
        val funcionario: Funcionario = funcionario()
        val empresa = empresa()

        BDDMockito.given(this.empresaService?.findByCnpj(CNPJ)).willReturn(empresa)
        BDDMockito.given(this.funcionarioService?.buscarPorCpf(funcionario.cpf)).willReturn(funcionario)

        mockMvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(this.obterJsonRequisicaoPost(funcionario, empresa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value("CPF já existente."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)

    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun adicionarPFComEmailJaCadastradoTest(){
        val funcionario: Funcionario = funcionario()
        val empresa = empresa()

        BDDMockito.given(this.empresaService?.findByCnpj(CNPJ)).willReturn(empresa)
        BDDMockito.given(this.funcionarioService?.buscarPorEmail(funcionario.email)).willReturn(funcionario)

        mockMvc!!.perform(MockMvcRequestBuilders.post(urlBase)
                .content(this.obterJsonRequisicaoPost(funcionario, empresa))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value("Email já existente."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)

    }

    @Throws(JsonProcessingException::class)
    private fun obterJsonRequisicaoPost(funcionario: Funcionario, empresa: Empresa): String {
        return ObjectMapper().writeValueAsString(
                CadastroPfDTO(funcionario.nome, funcionario.email, funcionario.senha, funcionario.cpf,
                        empresa.cnpj, empresa.id.toString(),funcionario.valorHora.toString(),
                        funcionario.qtdHorasTrabalhoDia.toString(),
                        funcionario.qtdHorasTrabalhoDia.toString(),
                        funcionario.id))
    }

    private fun funcionario(): Funcionario = Funcionario("Nome",
            "teste@email.com", SenhaUtils().gerarBCrypt("123456"), "01968778144",
            PerfilEnum.ROLE_USUARIO, "1", 0.0, 0.0f, 0.0f,
            FUNCIONARIO_ID)

    private fun empresa(): Empresa = Empresa(CNPJ, "Nova razão social", EMPRESA_ID)

}