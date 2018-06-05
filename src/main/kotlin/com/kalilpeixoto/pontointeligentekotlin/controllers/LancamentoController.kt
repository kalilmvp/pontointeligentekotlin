package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.documents.Lancamento
import com.kalilpeixoto.pontointeligentekotlin.dtos.LancamentoDTO
import com.kalilpeixoto.pontointeligentekotlin.enums.TipoEnum
import com.kalilpeixoto.pontointeligentekotlin.response.Response
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
import com.kalilpeixoto.pontointeligentekotlin.services.LancamentoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import javax.validation.Valid

@RestController
@RequestMapping("/api/lancamentos")
class LancamentoController(val lancamentoService: LancamentoService,
                           val funcionarioService: FuncionarioService) {

    private val FORMATTER = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @Value("\${paginacao.qtd_por_pagina}")
    private val qtdPorPagina: Int = 15

    @PostMapping
    fun adicionar(@Valid @RequestBody lancamentoDTO:LancamentoDTO,
                  result: BindingResult): ResponseEntity<Response<LancamentoDTO>>{

        val response: Response<LancamentoDTO> = Response<LancamentoDTO>()

        this.validarFuncionario(lancamentoDTO, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage)
            return ResponseEntity.badRequest().body(response)
        }

        val lancamento: Lancamento = this.converterDTOToLancamento(lancamentoDTO, result)
        this.lancamentoService.persistir(lancamento)
        response.data = this.converterLancamentoToDTO(lancamento)

        return ResponseEntity.ok(response)

    }

    private fun validarFuncionario(lancamento: LancamentoDTO, result: BindingResult) {
        if (lancamento.funcionarioId == null) {
            result.addError(ObjectError("funcionario", "Funcionário não informado"))
            return
        }

        val funcionario: Funcionario? = this.funcionarioService.buscarPorId(lancamento.funcionarioId)
        if (funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado, ID inexistente"))
            return
        }
    }

    private fun converterDTOToLancamento(lancamentoDTO: LancamentoDTO, result: BindingResult): Lancamento {

        if (lancamentoDTO.id != null) {
            val lancamento: Lancamento? = this.lancamentoService.buscarPorId(lancamentoDTO.id!!)
            if (lancamento == null) result.addError(ObjectError(
                    "lancamento",
                    "Lançamento não encontrado"))
        }

        return Lancamento(lancamentoDTO.id, this.FORMATTER.parse(lancamentoDTO.data),
                TipoEnum.valueOf(lancamentoDTO.tipo!!), lancamentoDTO.funcionarioId!!, lancamentoDTO.descricao,
                lancamentoDTO.localizacao)
    }

    private fun converterLancamentoToDTO(lancamento: Lancamento): LancamentoDTO? =
        LancamentoDTO(lancamento.id!!,  FORMATTER.format(lancamento.data), lancamento.tipo.toString(),
                lancamento.funcionarioId, lancamento.descricao, lancamento.localizacao)

}