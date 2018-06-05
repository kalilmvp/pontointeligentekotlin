package com.kalilpeixoto.pontointeligentekotlin.documents

import com.kalilpeixoto.pontointeligentekotlin.enums.TipoEnum
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Lancamento (
    @Id
    val id:String? = null,
    val data:Date,
    val tipo:TipoEnum,
    val funcionarioId:String,
    val descricao:String? = null,
    val localizacao:String? = null
)