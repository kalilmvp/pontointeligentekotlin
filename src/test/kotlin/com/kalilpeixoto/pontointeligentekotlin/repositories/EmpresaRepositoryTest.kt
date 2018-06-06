package com.kalilpeixoto.pontointeligentekotlin.repositories

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class EmpresaRepositoryTest {

    private val CNPJ = "12134341212412"
    private val ID = "1"
    private var empresa: Empresa? = null

    @Autowired
    private val empresaRepository: EmpresaRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        this.empresa = this.empresaRepository?.save(empresa())
    }

    @Test
    fun testPersisteEmpresa(){
        Assert.assertNotNull("A empresa nao pode ser nula", this.empresa)
        Assert.assertEquals("O ID tem que ser igual a 1", ID, this.empresa?.id)
    }

    @Test
    fun testFindByCnpj(){
        val empresa: Empresa? = this.empresaRepository?.findByCnpj(CNPJ)

        Assert.assertNotNull("A empresa nao pode ser nula", empresa)
        Assert.assertEquals("O CNPJ tem que ser igual a '12134341212412'", CNPJ, empresa?.cnpj)
    }

    private fun empresa(): Empresa {
        return Empresa(CNPJ, "Nova razão social", ID)
    }
}