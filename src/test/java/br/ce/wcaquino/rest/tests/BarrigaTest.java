package br.ce.wcaquino.rest.tests;

import br.ce.wcaquino.rest.core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("BarrigaTest")
public class BarrigaTest extends BaseTest {

    private String TOKEN;

    @BeforeEach
    public void login(){
        Map<String, String> login = new HashMap<>();
        login.put("email","melgarejom.leonardo@gmail.com");
        login.put("senha", "1234");

        TOKEN = given()
            .body(login)
        .when()
            .post("/signin")
        .then()
            .statusCode(200)
            .extract().path("token");
    }

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
    @DisplayName("Deve incluir conta com sucesso")
    public void deveIncluirContaComSucesso(){
        given()
            .header("Authorization", "JWT " + TOKEN)
            .body("{ \"nome\": \"conta qualquer\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
        ;
    }

    @Test
    @DisplayName("Deve alterar conta com sucesso")
    public void deveAlterarContaComSucesso(){
        given()
            .header("Authorization", "JWT " + TOKEN)
            .body("{ \"nome\": \"conta alterada\" }")
        .when()
            .put("/contas/2239295")
        .then()
            .statusCode(200)
            .body("nome", is("conta alterada"))
        ;
    }
}
