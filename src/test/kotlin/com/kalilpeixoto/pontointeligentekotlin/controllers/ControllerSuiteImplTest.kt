package com.kalilpeixoto.pontointeligentekotlin.controllers

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        FuncionarioControllerTest::class,
        LancamentoControllerTest::class,
        EmpresaControllerTest::class,
        CadastroPFControllerTest::class,
        CadastroPJControllerTest::class
)
class ControllerSuiteImplTest {}