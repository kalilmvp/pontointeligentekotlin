package com.kalilpeixoto.pontointeligentekotlin.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Empresa (
    val cnpj:String,
    val razaoSocial:String,
    @Id
    val id:String? = null
)