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

    @Test
    @DisplayName("Não deve incluir conta com o mesmo nome")
    public void naoDeveInserirContaMesmoNome(){
        given()
            .header("Authorization", "JWT " + TOKEN)
            .body("{ \"nome\": \"conta alterada\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(400)
            .body("error", is("Já existe uma conta com esse nome!"))
        ;
    }

    @Test
    @DisplayName("Deve inserir movimentacao com sucesso")
    public void deveInserirMovimentacaoComSucesso(){
        Movimentacao mov = new Movimentacao();
        mov.setConta_id(2239295);
//        mov.setUsuario_id(usuario_id);
        mov.setDescricao("Descrição da movimentação");
        mov.setEnvolvido("Envolvido na mov");
        mov.setTipo("REC");
        mov.setData_transacao("01/01/2000");
        mov.setData_pagamento("10/05/2010");
        mov.setValor(100f);
        mov.setStatus(true);

        given()
            .header("Authorization", "JWT " + TOKEN)
            .body(mov)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(201)
        ;
    }

}
