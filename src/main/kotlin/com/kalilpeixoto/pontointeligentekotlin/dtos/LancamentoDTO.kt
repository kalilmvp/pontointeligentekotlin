package com.kalilpeixoto.pontointeligentekotlin.dtos

import com.kalilpeixoto.pontointeligentekotlin.enums.TipoEnum
import org.hibernate.validator.constraints.NotEmpty
import java.util.*

data class LancamentoDTO (
        val id:String? = null,

        @get:NotEmpty(message = "Data não pode ser vazia")
        val data: Date,
        @get:NotEmpty(message = "Tipo não pode ser vazio")
        val tipo: TipoEnum,

        val funcionarioId:String,
        val descricao:String? = null,
        val localizacao:String? = null
)