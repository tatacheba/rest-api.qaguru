package tests.homework_19;

import io.restassured.RestAssured;
import models.reqresinTests.ReqresinTests_CredentialsLombok;
import models.reqresinTests.ReqresinTests_GenerateTokenResponseLombok;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {
    ReqresinTests_CredentialsLombok credentials = new ReqresinTests_CredentialsLombok();

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successfulLogin() {
        /*
        request: https://reqres.in/api/login

        data:
        {
            "email": "eve.holt@reqres.in",
            "password": "cityslicka"
        }

        response:
        {
            "token": "QpwL5tke4Pnpja7X4"
        }
         */
        credentials.setEmail("eve.holt@reqres.in");
        credentials.setPassword("cityslicka");
        ReqresinTests_GenerateTokenResponseLombok tokenResponse =
                given()
                        .filter(withCustomTemplates())
                        .contentType(JSON)
                        .body(credentials)
                        .log().body()
                        .when()
                        .post("/api/login")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body(matchesJsonSchemaInClasspath("schemas/succefullLogin_reqres_response_schema.json"))
                        .extract().as(ReqresinTests_GenerateTokenResponseLombok.class);
        assertThat(tokenResponse.getToken()).hasSizeGreaterThan(10).startsWith("Qpw");
    }

    @Test
    void missingLogin() {
        /*
        request: https://reqres.in/api/login

        data:
        {
            "email": "eve.holt@reqres.in"
        }

        response:
        {
            "error": "Missing password"
        }

         */
        credentials.setEmail("eve.holt@reqres.in");
        ReqresinTests_GenerateTokenResponseLombok tokenResponse =
                given()
                        .filter(withCustomTemplates())
                        .body(credentials)
                        .log().body()
                        .contentType(JSON)
                        .when()
                        .post("/api/login")
                        .then()
                        .log().body()
                        .statusCode(400)
                        .body(matchesJsonSchemaInClasspath("schemas/missingLogin_reqres_response_schema.json"))
                   .extract().as(ReqresinTests_GenerateTokenResponseLombok.class);
        assertThat(tokenResponse.getError()).isEqualTo("Missing password");
    }


}
