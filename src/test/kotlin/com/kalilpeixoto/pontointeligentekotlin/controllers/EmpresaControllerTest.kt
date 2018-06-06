package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import com.kalilpeixoto.pontointeligentekotlin.services.EmpresaService
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
class EmpresaControllerTest{

    private val urlBase: String = "/api/empresas"

    private val EMPRESA_ID: String = "1"
    private val CNPJ: String = "12121212"

    @Autowired
    private val mockMvc: MockMvc? = null

    @MockBean
    private val empresaService: EmpresaService? = null

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun buscarPorIdTest(){
        val empresa: Empresa = empresa()

        BDDMockito.given(this.empresaService?.findById(EMPRESA_ID)).willReturn(empresa)

        mockMvc!!.perform(MockMvcRequestBuilders.get(urlBase.plus("/").plus(EMPRESA_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cnpj").value(empresa.cnpj))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.razaoSocial").value(empresa.razaoSocial))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(EMPRESA_ID))
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun buscarPorIdNaoEncontradoTest(){
        val empresa: Empresa = empresa()

        BDDMockito.given(this.empresaService?.findById(EMPRESA_ID)).willReturn(null)

        mockMvc!!.perform(MockMvcRequestBuilders.get(urlBase.plus("/").plus(EMPRESA_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value(
                        "Empresa não econtrada para o ID ${empresa.id}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun buscarPorCnpjTest(){
        val empresa: Empresa = empresa()

        BDDMockito.given(this.empresaService?.findByCnpj(CNPJ)).willReturn(empresa)

        mockMvc!!.perform(MockMvcRequestBuilders.get(urlBase.plus("/cnpj/").plus(CNPJ))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cnpj").value(empresa.cnpj))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.razaoSocial").value(empresa.razaoSocial))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(EMPRESA_ID))
    }

    @Test
    @Throws(Exception::class)
    @WithMockUser
    fun buscarPorCnpjNaoEncontradoTest(){
        val empresa: Empresa = empresa()

        BDDMockito.given(this.empresaService?.findByCnpj(CNPJ)).willReturn(null)

        mockMvc!!.perform(MockMvcRequestBuilders.get(urlBase.plus("/cnpj/").plus(CNPJ))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").isNotEmpty)
                .andExpect(MockMvcResultMatchers.jsonPath("$.erros").value(
                        "Empresa não econtrada para o CNPJ ${empresa.cnpj}"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty)
    }

    private fun empresa(): Empresa = Empresa(CNPJ, "Nova razão social", EMPRESA_ID)
}