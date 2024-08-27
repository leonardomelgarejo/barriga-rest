package br.ce.wcaquino.rest.tests;

import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.utils.DataUtils;
import io.restassured.RestAssured;
import io.restassured.specification.FilterableRequestSpecification;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("BarrigaTest")
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BarrigaTest extends BaseTest {
    private static String CONTA_NAME = "Conta " + System.nanoTime();
    private static Integer CONTA_ID;
    private static Integer MOV_ID;

    @BeforeAll
    public static void login(){
        Map<String, String> login = new HashMap<>();
        login.put("email","melgarejom.leonardo@gmail.com");
        login.put("senha", "1234");

        String TOKEN = given()
            .log().all()
            .body(login)
        .when()
            .post("/signin")
        .then()
            .log().all()
            .statusCode(200)
            .extract().path("token");

        RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
    }

    @Test
    @Order(1)
    @Disabled
    @DisplayName("Deve incluir conta com sucesso")
    public void deveIncluirContaComSucesso(){
        CONTA_ID = given()
            .body("{ \"nome\": \""+CONTA_NAME+"\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(201)
                .extract().path("id")
        ;
    }

    @Test
    @Order(2)
    @Disabled
    @DisplayName("Deve alterar conta com sucesso")
    public void deveAlterarContaComSucesso(){
        given()
            .body("{ \"nome\": \""+CONTA_NAME+" alterada\" }")
            .pathParam("id", CONTA_ID)
        .when()
            .put("/contas/{id}")
        .then()
            .statusCode(200)
            .body("nome", is(CONTA_NAME+ " alterada"))
        ;
    }

    @Test
    @Order(3)
    @Disabled
    @DisplayName("Não deve incluir conta com o mesmo nome")
    public void naoDeveInserirContaMesmoNome(){
        given()
            .body("{ \"nome\": \"conta alterada\" }")
        .when()
            .post("/contas")
        .then()
            .statusCode(400)
            .body("error", is("Já existe uma conta com esse nome!"))
        ;
    }

    @Test
    @Order(4)
    @Disabled
    @DisplayName("Deve inserir movimentacao com sucesso")
    public void deveInserirMovimentacaoComSucesso(){
        MovimentacaoTest mov = getMovimentacaoValida();

        MOV_ID = given()
            .body(mov)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(201)
            .extract().path("id")
        ;
    }

    @Test
    @Order(5)
    @Disabled
    @DisplayName("Deve validar campos obrigatórios da movimentação")
    public void deveValidarCamposObrigatoriosMovimentacao(){
        given()
            .body("{}")
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
            .body("$", hasSize(8))
            .body("msg", hasItems(
                    "Data da Movimentação é obrigatório",
                    "Data do pagamento é obrigatório",
                    "Descrição é obrigatório",
                    "Interessado é obrigatório",
                    "Valor é obrigatório",
                    "Valor deve ser um número",
                    "Conta é obrigatório",
                    "Situação é obrigatório"
            ))
        ;
    }

    @Test
    @Order(6)
    @Disabled
    @DisplayName("Não deve inserir movimentação com data futura")
    public void naoDeveInserirMovimentacaoComDataFutura(){
        MovimentacaoTest mov = getMovimentacaoValida();
        mov.setData_transacao(DataUtils.getDataDiferencaDias(2));

        given()
            .body(mov)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(400)
            .body("$", hasSize(1))
            .body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
        ;
    }

    @Test
    @Order(7)
    @Disabled
    @DisplayName("Não deve remover conta com movimentação")
    public void naoDeveRemoverContaComMovimentacao(){
        given()
            .pathParam("id", CONTA_ID)
        .when()
            .delete("/contas/{id}")
        .then()
            .statusCode(500)
            .body("constraint", is("transacoes_conta_id_foreign"))
        ;
    }

    @Test
    @Order(8)
    @Disabled
    @DisplayName("Deve calcular saldo contas")
    public void deveCalcularSaldoContas(){
        given()
        .when()
            .get("/saldo")
        .then()
            .statusCode(200)
            .body("find{it.conta_id == "+CONTA_ID+"}.saldo", is("100.00"))
        ;
    }

    @Test
    @Order(9)
    @Disabled
    @DisplayName("Deve remover movimentação")
    public void deveRemoverMovimentacao(){
        given()
            .pathParam("id", MOV_ID)
        .when()
            .delete("/transacoes/{id}")
        .then()
            .statusCode(204)
        ;
    }

    @Test
    @Order(10)
    @Disabled
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

    private MovimentacaoTest getMovimentacaoValida(){
        MovimentacaoTest mov = new MovimentacaoTest();
        mov.setConta_id(CONTA_ID);
        mov.setDescricao("Descrição da movimentação");
        mov.setEnvolvido("Envolvido na mov");
        mov.setTipo("REC");
        mov.setData_transacao(DataUtils.getDataDiferencaDias(-1));
        mov.setData_pagamento(DataUtils.getDataDiferencaDias(5));
        mov.setValor(100f);
        mov.setStatus(true);
        return mov;
    }
}
