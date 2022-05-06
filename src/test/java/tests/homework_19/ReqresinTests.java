package tests.homework_19;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.filters(new AllureRestAssured());
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

        String authorizedData = "{\"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\"}";

        given()
                .filter(withCustomTemplates())
                .body(authorizedData)
                .contentType(JSON)
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
//                .body("token", is("QpwL5tke4Pnpja7X4"));
                .body("token.size()", (greaterThan(10)));
    }

    @Test
    void missingPasswordLogin() {
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

        String authorizedData = "{\"email\": \"eve.holt@reqres.in\"}";

        given()
                .body(authorizedData)
                .contentType(JSON)
                .when()
                .post("/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }


}
