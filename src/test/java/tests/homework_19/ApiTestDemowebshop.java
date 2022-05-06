package tests.homework_19;

import io.restassured.RestAssured;
import models.demowebTests.DemowebTests_CredentialsLombok;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static listeners.CustomAllureListener.withCustomTemplates;
import static org.hamcrest.Matchers.*;

public class ApiTestDemowebshop {
    DemowebTests_CredentialsLombok credentials = new DemowebTests_CredentialsLombok();

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    @Test
    @DisplayName("Добавление товара в wishlist")
    void addToWishlist() {
        credentials.setCookie("Nop.customer=35b76c08-0231-43bc-870d-52f56ed79d09;");
        given()
                .filter(withCustomTemplates())
                .contentType("application/json; charset=utf-8")
                .body(credentials)
                .when()
                .post("/addproducttocart/details/14/2")
                .then()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/addToWishlist_demoweb_response_schema.json"))
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"))
                .body("updatetopwishlistsectionhtml", not(nullValue()));
    }

    @Test
    @DisplayName("Подписка на пустой email")
    void subscribeEmptyTest() {
        credentials.setCookie("Nop.customer=35b76c08-0231-43bc-870d-52f56ed79d09;");
        given()
                .filter(withCustomTemplates())
                .contentType("application/json; charset=utf-8")
                .body(credentials)
                .post("/subscribenewsletter")
                .then()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/subscribeTest_demoweb_response_schema.json"))
                .body("Success", is(false))
                .body("Result", is("Enter valid email"));
    }

    @Test
    @DisplayName("Подписка на email")
    void subscribeFullTest() {
        credentials.setEmail("test@qa.guru");
        credentials.setCookie("Nop.customer=35b76c08-0231-43bc-870d-52f56ed79d09;");
        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(credentials)
                .log().body()
                .post("/subscribenewsletter")
                .then()
                .statusCode(200)
                .log().body()
                .body(matchesJsonSchemaInClasspath("schemas/subscribeTest_demoweb_response_schema.json"))
                .body("Success", is(true))
                .body("Result", is("Thank you for signing up! A verification email has been sent. We appreciate your interest."));

    }
}


