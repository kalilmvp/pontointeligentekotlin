package com.kalilpeixoto.pontointeligentekotlin.repositories

import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.enums.PerfilEnum
import com.kalilpeixoto.pontointeligentekotlin.utils.SenhaUtils
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class FuncionarioRepositoryTest {
    private val CPF: String = "12345678"
    private val ID: String = "1"
    private val EMPRESA_ID: String = "20"
    private val EMAIL: String = "email@email.com"

    private var funcionario: Funcionario? = null

    @Autowired
    private val funcionarioRepository: FuncionarioRepository? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        this.funcionario = this.funcionarioRepository?.save(funcionario())
    }

    @Test
    fun findByEmailTest() {
        val funcionario: Funcionario? = this.funcionarioRepository?.findByEmail(EMAIL)

        Assert.assertNotNull("Funcionario nao pode ser nulo", funcionario)
        Assert.assertEquals("Funcionario tem que ter email: 'email@email.com'",
                EMAIL,
                funcionario?.email)
    }

    @Test
    fun findByCPFTest() {
        val funcionario: Funcionario? = this.funcionarioRepository?.findByCpf(CPF)

        Assert.assertNotNull("Funcionario nao pode ser nulo", funcionario)
        Assert.assertEquals("Funcionario tem que ter CPF: '12345678'",
                CPF,
                funcionario?.cpf)
    }

    private fun funcionario(): Funcionario = Funcionario("Nome", EMAIL,
            SenhaUtils().gerarBCrypt("123456"),
            CPF, PerfilEnum.ROLE_USUARIO, EMPRESA_ID, 0.0, 0.0f,
            0.0f, ID)
}