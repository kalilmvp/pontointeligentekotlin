package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.dtos.FuncionarioDTO
import com.kalilpeixoto.pontointeligentekotlin.response.Response
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
import com.kalilpeixoto.pontointeligentekotlin.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/funcionarios")
class FuncionarioController(val funcionarioService: FuncionarioService) {

    @PutMapping(value = "/{id}")
    fun atualizar(@PathVariable("id") id: String, @Valid @RequestBody funcionarioDto: FuncionarioDTO,
                  result: BindingResult): ResponseEntity<Response<FuncionarioDTO>> {

        val response: Response<FuncionarioDTO> = Response<FuncionarioDTO>()
        val funcionario: Funcionario? = funcionarioService.buscarPorId(id)

        if (funcionario == null) {
            result.addError(ObjectError("funcionario", "Funcionário não encontrado."))
        }

        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage)
            return ResponseEntity.badRequest().body(response)
        }

        val funcAtualizar: Funcionario = atualizarDadosFuncionario(funcionario!!, funcionarioDto)
        funcionarioService.persistir(funcAtualizar)
        response.data = converterFuncionarioDto(funcAtualizar)

        return ResponseEntity.ok(response)
    }

    private fun atualizarDadosFuncionario(funcionario: Funcionario,
                                          funcionarioDto: FuncionarioDTO): Funcionario {
        var senha: String
        if (funcionarioDto.senha == null) {
            senha = funcionario.senha
        } else {
            senha = SenhaUtils().gerarBCrypt(funcionarioDto.senha)
        }

        return Funcionario(funcionarioDto.nome, funcionario.email, senha,
                funcionario.cpf, funcionario.perfil, funcionario.empresaId,
                funcionarioDto.valorHora?.toDouble(),
                funcionarioDto.qtdHorasTrabalhoDia?.toFloat(),
                funcionarioDto.qtdHorasAlmoco?.toFloat(),
                funcionario.id)
    }

    private fun converterFuncionarioDto(funcionario: Funcionario): FuncionarioDTO =
            FuncionarioDTO(funcionario.nome, funcionario.email, "",
                    funcionario.valorHora.toString(), funcionario.qtdHorasTrabalhoDia.toString(),
                    funcionario.qtdHorasAlmoco.toString(), funcionario.id)
}