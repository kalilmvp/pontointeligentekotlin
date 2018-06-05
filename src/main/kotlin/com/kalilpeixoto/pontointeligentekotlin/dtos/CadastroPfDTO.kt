package com.kalilpeixoto.pontointeligentekotlin.dtos

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.br.CPF

data class CadastroPfDTO (

        @get:NotEmpty(message = "Nome não pode ser vazio")
        @get:Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres")
        val nome: String = "",

        @get:NotEmpty(message = "Email não pode ser vazio")
        @get:Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres")
        @get:Email(message = "Email inválido")
        val email: String = "",

        @get:NotEmpty(message = "Senha não pode ser vazia")
        val senha: String = "",

        @get:NotEmpty(message = "CPF não pode ser vazio")
        @get:CPF(message = "CPG inválido")
        val cpf: String = "",

        val empresaId: String? = null,
        val valorHora: String? = null,
        val qtdHorasTrabalhoDia: String? = null,
        val qtdHorasAlmoco: String? = null,

        val id: String? = null
)