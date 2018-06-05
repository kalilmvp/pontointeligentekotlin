package com.kalilpeixoto.pontointeligentekotlin.services.impl

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        EmpresaServiceImplTest::class,
        FuncionarioServiceImplTest::class,
        LancamentoServiceImplTest::class
)
class ServiceSuiteImplTest {

}