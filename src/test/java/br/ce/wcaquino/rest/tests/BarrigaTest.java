package br.ce.wcaquino.rest.tests;

import br.ce.wcaquino.rest.core.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@DisplayName("BarrigaTest")
public class BarrigaTest extends BaseTest {

    @Test
    @DisplayName("Não deve acessar o recurso /contas sem token")
    public void naoDeveAcessarAPISemToken(){
        given()
        .when()
            .get("/contas")
        .then()
            .statusCode(401)
        ;
    }

    @Test
    @DisplayName("Deve incluir conta com suesso")
    public void deveIncluirContaComSucesso(){
        Map<String, String> login = new HashMap<>();
        login.put("email","melgarejom.leonardo@gmail.com");
        login.put("senha", "1234");

        String token = given()
                    .body(login)
                .when()
                    .post("/signin")
                .then()
                    .statusCode(200)
                    .extract().path("token");

        given()
            .header("Authorization", "JWT " + token)
            .body("{ \"nome\": \"conta qualquer\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
        ;
    }
}
