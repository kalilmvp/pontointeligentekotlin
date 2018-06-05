package com.kalilpeixoto.pontointeligentekotlin.services

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa

interface EmpresaService {

    fun findByCnpj(cnpj: String): Empresa?

    fun persistir(empresa: Empresa): Empresa
}