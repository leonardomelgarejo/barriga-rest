package br.ce.wcaquino.rest.tests.refac.suite;


import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.tests.refac.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(value = {
        SetupTest.class,
        ContasTest.class,
        MovimentacaoTest.class,
        SaldoTest.class,
        AuthTest.class
})
public class SuiteDeTestes extends BaseTest {

}
