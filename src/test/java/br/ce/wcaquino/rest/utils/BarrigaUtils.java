package br.ce.wcaquino.rest.utils;

import static io.restassured.RestAssured.given;

public class BarrigaUtils {

    public static Integer getIdContaPeloNome(String nome){
        return given()
                .when()
                    .get("/contas?nome="+nome)
                .then()
                    .extract()
                        .path("id[0]");
    }

    public static Integer getIdMovPelaDescricao(String desc){
        return given()
               .when()
                    .get("/transacoes?descricao="+desc)
               .then()
                .extract()
                    .path("id[0]");
    }
}
