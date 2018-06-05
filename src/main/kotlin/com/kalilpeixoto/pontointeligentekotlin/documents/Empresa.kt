package com.kalilpeixoto.pontointeligentekotlin.documents

import org.springframework.data.annotation.Id

class Empresa (
    @Id
    val id:String? = null,
    val cnpj:String,
    val razaoSocial:String
)