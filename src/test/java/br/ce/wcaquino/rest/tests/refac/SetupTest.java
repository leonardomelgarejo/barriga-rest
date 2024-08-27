package br.ce.wcaquino.rest.tests.refac;

import br.ce.wcaquino.rest.core.BaseTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetupTest extends BaseTest {

    private static ValidatableResponse resetResponse;
    @BeforeAll
    public static void login() {
        RestAssured.defaultParser = Parser.JSON;

        Map<String, String> login = new HashMap<>();
        login.put("email", "melgarejom.leonardo@gmail.com");
        login.put("senha", "1234");

        String TOKEN = given()
            .body(login)
        .when()
            .post("/signin")
        .then()
            .statusCode(200)
            .extract().path("token");

        RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);

        resetResponse = given().when().get("/reset").then().statusCode(200);
    }

    @Test
    @DisplayName("Setup executado com sucesso")
    public void setupTest() {
        int statusCode = resetResponse.extract().statusCode();
        assertEquals(200, statusCode, "O status code do reset deveria ser 200");
    }
}
