package br.ce.wcaquino.rest.tests.refac;

import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.utils.BarrigaUtils;
import br.ce.wcaquino.rest.utils.DataUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MovimentacaoTest extends BaseTest {
    @Test
    @DisplayName("Deve inserir movimentacao com sucesso")
    public void deveInserirMovimentacaoComSucesso(){
        br.ce.wcaquino.rest.tests.MovimentacaoTest mov = getMovimentacaoValida();

        given()
            .body(mov)
        .when()
            .post("/transacoes")
        .then()
            .statusCode(201)
        ;
    }

    @Test
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
    @DisplayName("Não deve inserir movimentação com data futura")
    public void naoDeveInserirMovimentacaoComDataFutura(){
        br.ce.wcaquino.rest.tests.MovimentacaoTest mov = getMovimentacaoValida();
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
    @DisplayName("Não deve remover conta com movimentação")
    public void naoDeveRemoverContaComMovimentacao(){
        Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta com movimentacao");

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
    @DisplayName("Deve remover movimentação")
    public void t10_deveRemoverMovimentacao(){
        Integer MOV_ID = BarrigaUtils.getIdMovPelaDescricao("Movimentacao para exclusao");

        given()
            .pathParam("id", MOV_ID)
        .when()
            .delete("/transacoes/{id}")
        .then()
            .statusCode(204)
        ;
    }

    private br.ce.wcaquino.rest.tests.MovimentacaoTest getMovimentacaoValida(){
        br.ce.wcaquino.rest.tests.MovimentacaoTest mov = new br.ce.wcaquino.rest.tests.MovimentacaoTest();
        mov.setConta_id(BarrigaUtils.getIdContaPeloNome("Conta para movimentacoes"));
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
