package com.kalilpeixoto.pontointeligentekotlin.repositories

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        EmpresaRepositoryTest::class,
        FuncionarioRepositoryTest::class,
        LancamentoRepositoryTest::class
)
class RepositorySuiteImplTest {

}