package com.kalilpeixoto.pontointeligentekotlin.services.impl

import com.kalilpeixoto.pontointeligentekotlin.documents.Lancamento
import com.kalilpeixoto.pontointeligentekotlin.repositories.LancamentoRepository
import com.kalilpeixoto.pontointeligentekotlin.services.LancamentoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class LancamentoServiceImpl(val lancamentoRepository: LancamentoRepository) : LancamentoService {
    override fun buscarPorFuncionarioId(id: String, pageRequest: PageRequest): Page<Lancamento> =
            this.lancamentoRepository.findByFuncionarioId(id, pageRequest)

    override fun buscarPorId(id: String): Lancamento? = this.lancamentoRepository.findOne(id)

    override fun persistir(lancamento: Lancamento): Lancamento = this.lancamentoRepository.save(lancamento)

    override fun remover(id: String) = this.lancamentoRepository.delete(id)
}