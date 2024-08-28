package br.ce.wcaquino.rest.suite;


import br.ce.wcaquino.rest.tests.AuthTest;
import br.ce.wcaquino.rest.tests.ContasTest;
import br.ce.wcaquino.rest.tests.MovimentacaoTest;
import br.ce.wcaquino.rest.tests.SaldoTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(value = {
        ContasTest.class,
        MovimentacaoTest.class,
        SaldoTest.class,
        AuthTest.class
})
public class SuiteTest {

}
