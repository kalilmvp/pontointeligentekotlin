package com.kalilpeixoto.pontointeligentekotlin.services.impl

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import com.kalilpeixoto.pontointeligentekotlin.repositories.EmpresaRepository
import com.kalilpeixoto.pontointeligentekotlin.services.EmpresaService
import org.springframework.stereotype.Service

@Service
class EmpresaServiceImpl(val empresaRepository: EmpresaRepository) : EmpresaService {

    override fun findByCnpj(cnpj: String): Empresa? = empresaRepository.findByCnpj(cnpj)

    override fun persistir(empresa: Empresa): Empresa = empresaRepository.save(empresa)
}