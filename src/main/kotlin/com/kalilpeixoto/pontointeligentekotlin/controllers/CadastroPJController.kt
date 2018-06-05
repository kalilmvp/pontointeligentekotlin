package com.kalilpeixoto.pontointeligentekotlin.controllers

import com.kalilpeixoto.pontointeligentekotlin.documents.Empresa
import com.kalilpeixoto.pontointeligentekotlin.documents.Funcionario
import com.kalilpeixoto.pontointeligentekotlin.dtos.CadastroPjDTO
import com.kalilpeixoto.pontointeligentekotlin.enums.PerfilEnum
import com.kalilpeixoto.pontointeligentekotlin.response.Response
import com.kalilpeixoto.pontointeligentekotlin.services.EmpresaService
import com.kalilpeixoto.pontointeligentekotlin.services.FuncionarioService
import com.kalilpeixoto.pontointeligentekotlin.utils.SenhaUtils
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/cadastrar-pj")
class CadastroPJController(val empresaService: EmpresaService,
                           val funcionarioService: FuncionarioService) {

    @PostMapping
    fun adicionar(@Valid @RequestBody cadastroPjDTO: CadastroPjDTO,
                  result: BindingResult): ResponseEntity<Response<CadastroPjDTO>> {

        val response: Response<CadastroPjDTO> = Response<CadastroPjDTO>()

        this.validarDadosExistentes(cadastroPjDTO, result)

        if (result.hasErrors()) {
            for (erro in result.allErrors) response.erros.add(erro.defaultMessage)
            return ResponseEntity.badRequest().body(response)
        }

        val empresa: Empresa = converterDtoParaEmpresa(cadastroPjDTO)
        empresaService.persistir(empresa)

        val funcionario: Funcionario = converterDtoParaFuncionario(cadastroPjDTO, empresa)
        funcionarioService.persistir(funcionario)
        response.data = converterCadastroPJDto(funcionario, empresa)

        return ResponseEntity.ok(response)
    }

    private fun validarDadosExistentes(cadastroPJDto: CadastroPjDTO, result: BindingResult) {
        if (empresaService.findByCnpj(cadastroPJDto.cnpj) != null)
            result.addError(ObjectError("empresa", "Empresa já existente."))

        if (funcionarioService.buscarPorCpf(cadastroPJDto.cpf) != null)
            result.addError(ObjectError("funcionario", "CPF já existente."))

        if (funcionarioService.buscarPorEmail(cadastroPJDto.email) != null)
            result.addError(ObjectError("funcionario", "Email já existente."))
    }

    private fun converterDtoParaEmpresa(cadastroPJDto: CadastroPjDTO): Empresa =
            Empresa(cadastroPJDto.cnpj, cadastroPJDto.razaoSocial)


    private fun converterDtoParaFuncionario(cadastroPJDto: CadastroPjDTO, empresa: Empresa) =
            Funcionario(cadastroPJDto.nome, cadastroPJDto.email,
                    SenhaUtils().gerarBCrypt(cadastroPJDto.senha), cadastroPJDto.cpf,
                    PerfilEnum.ROLE_ADMIN, empresa.id.toString())

    private fun converterCadastroPJDto(funcionario: Funcionario, empresa: Empresa): CadastroPjDTO =
            CadastroPjDTO(funcionario.nome, funcionario.email, "", funcionario.cpf,
                    empresa.cnpj, empresa.razaoSocial, funcionario.id)
}