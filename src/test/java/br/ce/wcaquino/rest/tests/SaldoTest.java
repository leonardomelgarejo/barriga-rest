package br.ce.wcaquino.rest.tests;

import br.ce.wcaquino.rest.BaseTest;
import br.ce.wcaquino.rest.utils.BarrigaUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SaldoTest extends BaseTest {
    @Test
    @DisplayName("Deve calcular saldo contas")
    public void deveCalcularSaldoContas(){
        Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para saldo");

        given()
        .when()
            .get("/saldo")
        .then()
            .statusCode(200)
            .body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("534.00"))
        ;
    }
}
