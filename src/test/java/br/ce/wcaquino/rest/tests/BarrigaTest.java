package br.ce.wcaquino.rest.tests;

import br.ce.wcaquino.rest.core.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BarrigaTest extends BaseTest {

    @Test
    public void naoDeveAcessarAPISemToke(){
        given()
        .when()
            .get("/contas")
        .then()
            .statusCode(401)
        ;
    }
}
