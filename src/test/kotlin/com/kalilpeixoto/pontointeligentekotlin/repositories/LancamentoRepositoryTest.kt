package com.kalilpeixoto.pontointeligentekotlin.repositories

import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.documents.Lancamento
import com.kalilpeixoto.pontointeligentekotlin.enums.TipoEnum
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
class LancamentoRepositoryTest {
    private val ID: String = "1"
    private val FUNCIONARIO_ID: String = "10"

    private var lancamento: Lancamento? = null

    @Autowired
    private val lancamentoRepository: LancamentoRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp(){
        this.lancamento = this.lancamentoRepository?.save(lancamento())
    }

    @Test
    fun findByFuncionarioIdTest(){
        val lancamentos: Page<Lancamento>? = this.lancamentoRepository?.findByFuncionarioId(
                FUNCIONARIO_ID,
                PageRequest(0, 10))

        Assert.assertNotNull("Lancamentos nao podem estar nulos", lancamentos)
        Assert.assertEquals("Lancamentos tem que tamanho: '1'",
                1L,
                lancamentos?.totalElements)
    }


    private fun lancamento(): Lancamento = Lancamento(ID, Date(), TipoEnum.INICIO_ALMOCO, FUNCIONARIO_ID)
}