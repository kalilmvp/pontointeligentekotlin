package com.kalilpeixoto.pontointeligentekotlin.dtos

data class EmpresaDTO (
    val cnpj:String,
    val razaoSocial:String,
    val id:String? = null
)