package br.ce.wcaquino.rest.tests;

import br.ce.wcaquino.rest.BaseTest;
import br.ce.wcaquino.rest.utils.BarrigaUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ContasTest extends BaseTest {
    @Test
    @DisplayName("Deve incluir conta com sucesso")
    public void deveIncluirContaComSucesso(){
        given()
            .body("{ \"nome\": \"Conta inserida\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
        ;
    }

    @Test
    @DisplayName("Deve alterar conta com sucesso")
    public void deveAlterarContaComSucesso(){
        Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para alterar");

        given()
            .body("{ \"nome\": \"Conta alterada\" }")
            .pathParam("id", CONTA_ID)
        .when()
            .put("/contas/{id}")
        .then()
            .statusCode(200)
            .body("nome", is("Conta alterada"))
        ;
    }

    @Test
    @DisplayName("Não deve incluir conta com o mesmo nome")
    public void naoDeveInserirContaMesmoNome(){
        given()
            .body("{ \"nome\": \"Conta mesmo nome\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(400)
            .body("error", is("Já existe uma conta com esse nome!"))
        ;
    }

}
