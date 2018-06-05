package com.kalilpeixoto.pontointeligentekotlin.dtos

import org.hibernate.validator.constraints.NotEmpty

data class LancamentoDTO (
        val id:String? = null,

        @get:NotEmpty(message = "Data não pode ser vazia")
        val data: String? = null,
        @get:NotEmpty(message = "Tipo não pode ser vazio")
        val tipo: String? = null,

        val funcionarioId:String? = null,
        val descricao:String? = null,
        val localizacao:String? = null
)