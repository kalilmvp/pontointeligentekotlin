package com.kalilpeixoto.pontointeligentekotlin.services.impl

import com.kalilpeixoto.pontointeligentekotlin.documents.Lancamento
import com.kalilpeixoto.pontointeligentekotlin.enums.TipoEnum
import com.kalilpeixoto.pontointeligentekotlin.repositories.LancamentoRepository
import com.kalilpeixoto.pontointeligentekotlin.services.LancamentoService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import kotlin.collections.ArrayList

@RunWith(SpringRunner::class)
@SpringBootTest
class LancamentoServiceImplTest {
    private val ID: String = "1"
    private val FUNCIONARIO_ID: String = "10"

    @Autowired
    private val lancamentoService: LancamentoService? = null

    @MockBean
    private val lancamentoRepository: LancamentoRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(lancamentoRepository?.save(Mockito.any(Lancamento::class.java))).willReturn(lancamento())
        BDDMockito.given(lancamentoRepository?.findOne(ID)).willReturn(lancamento())
        BDDMockito.given(lancamentoRepository?.findByFuncionarioId(
                FUNCIONARIO_ID,
                PageRequest(0, 10))).willReturn(PageImpl(ArrayList<Lancamento>()))
    }

    @Test
    fun testBuscarFuncionarioPorId(){
        val lancamentos: Page<Lancamento>? =
                this.lancamentoService?.buscarPorFuncionarioId(FUNCIONARIO_ID, PageRequest(0, 10))
        Assert.assertNotNull(lancamentos)
    }

    @Test
    fun testPersistirLancamento(){
        val lancamento: Lancamento? = this.lancamentoService?.persistir(lancamento())
        Assert.assertNotNull(lancamento)
    }

    @Test
    fun testBuscarPorId(){
        val lancamento: Lancamento? = this.lancamentoService?.buscarPorId(ID)
        Assert.assertNotNull(lancamento)
    }

    private fun lancamento(): Lancamento = Lancamento(ID, Date(), TipoEnum.INICIO_ALMOCO, FUNCIONARIO_ID)
}