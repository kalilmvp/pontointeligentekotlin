package com.kalilpeixoto.pontointeligentekotlin.utils

import org.junit.Assert
import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SenhaUtilsTest {

    private val SENHA = "123456";
    private val ENCODER = BCryptPasswordEncoder()

    @Test
    fun testGerarHashSenha(){
        val hash = SenhaUtils().gerarBCrypt(SENHA)

        Assert.assertTrue(ENCODER.matches(SENHA, hash))
    }

}