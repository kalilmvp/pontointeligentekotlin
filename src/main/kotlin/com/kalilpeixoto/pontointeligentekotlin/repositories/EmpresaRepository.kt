package com.kalilpeixoto.pontointeligentekotlin.repositories

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpresaRepository : MongoRepository<Empresa, String>{
    fun findByCnpj(cnpj:String): Empresa
}