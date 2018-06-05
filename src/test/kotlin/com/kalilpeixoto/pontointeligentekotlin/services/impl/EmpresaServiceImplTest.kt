package com.kalilpeixoto.pontointeligentekotlin.services.impl

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import com.kalilpeixoto.pontointeligentekotlin.repositories.EmpresaRepository
import com.kalilpeixoto.pontointeligentekotlin.services.EmpresaService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EmpresaServiceImplTest {

    private val ID = "1"
    private val CNPJ = "123456789"

    @Autowired
    private val empresaService: EmpresaService? = null

    @MockBean
    private val empresaRepository: EmpresaRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(empresaRepository?.findOne(ID)).willReturn(empresa())
        BDDMockito.given(empresaRepository?.findByCnpj(CNPJ)).willReturn(empresa())
        BDDMockito.given(empresaRepository?.save(empresa())).willReturn(empresa())
    }

    @Test
    fun testBuscarEmpresaPorId(){
        val empresa: Empresa? = empresaService?.findById(ID)
        Assert.assertNotNull(empresa)
        Assert.assertEquals(ID, empresa?.id)
    }

    @Test
    fun testBuscarEmpresaPorCnpj(){
        val empresa: Empresa? = empresaService?.findByCnpj(CNPJ)
        Assert.assertNotNull(empresa)
        Assert.assertEquals(CNPJ, empresa?.cnpj)
    }

    @Test
    fun testPersistirEmpresa() {
        val empresa: Empresa? = empresaService?.persistir(empresa())
        Assert.assertNotNull("Empresa nao pode ser nula", empresa)
        Assert.assertEquals(CNPJ, empresa?.cnpj)
        Assert.assertEquals("Nova razão social", empresa?.razaoSocial)
    }

    private fun empresa(): Empresa = Empresa(CNPJ, "Nova razão social", ID)
}