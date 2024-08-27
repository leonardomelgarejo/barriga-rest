package br.ce.wcaquino.rest.tests.refac;

import br.ce.wcaquino.rest.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthTest extends BaseTest {
    @Test
    @DisplayName("Não deve acessar o recurso /contas sem token")
    public void naoDeveAcessarAPISemToken(){
        FilterableRequestSpecification req = (FilterableRequestSpecification) RestAssured.requestSpecification;
        req.removeHeader("Authorization");

        given()
        .when()
            .get("/contas")
        .then()
            .statusCode(401)
        ;
    }
}
