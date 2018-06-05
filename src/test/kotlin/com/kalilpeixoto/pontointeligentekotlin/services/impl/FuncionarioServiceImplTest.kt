package com.kalilpeixoto.pontointeligentekotlin.services.impl

import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.enums.PerfilEnum
import com.kalilpeixoto.pontointeligentekotlin.repositories.FuncionarioRepository
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
import com.kalilpeixoto.pontointeligentekotlin.utils.SenhaUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class FuncionarioServiceImplTest {

    private val CPF: String = "12345678"
    private val ID: String = "1"
    private val EMPRESA_ID: String = "20"
    private val EMAIL: String = "email@email.com"

    @MockBean
    private val funcionarioRepository: FuncionarioRepository? = null

    @Autowired
    private val funcionarioService: FuncionarioService? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        BDDMockito.given(funcionarioRepository?.save(Mockito.any(Funcionario::class.java))).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findByCpf(CPF)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findByEmail(EMAIL)).willReturn(funcionario())
        BDDMockito.given(funcionarioRepository?.findOne(ID)).willReturn(funcionario())
    }

    @Test
    fun testPersistirFuncionario() {
        val funcionario: Funcionario? = this.funcionarioService?.persistir(funcionario())
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorCpf() {
        val funcionario: Funcionario? = this.funcionarioService?.buscarPorCpf(CPF)
        Assert.assertNotNull(funcionario)
    }

    @Test
    fun testBuscarFuncionarioPorEmail() {
        val funcionario: Funcionario? = this.funcionarioService?.buscarPorEmail(EMAIL)
        Assert.assertNotNull(funcionario)
    }


    @Test
    fun testBuscarFuncionarioPorId() {
        val funcionario: Funcionario? = this.funcionarioService?.buscarPorId(ID)
        Assert.assertNotNull(funcionario)
    }

    private fun funcionario(): Funcionario = Funcionario("Nome", EMAIL, SenhaUtils().gerarBCrypt("123456"),
            CPF, PerfilEnum.ROLE_USUARIO, EMPRESA_ID, 0.0, 0.0f, 0.0f, ID)
}
