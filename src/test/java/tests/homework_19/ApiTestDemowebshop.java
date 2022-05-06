package tests.homework_19;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ApiTestDemowebshop {

    @BeforeAll
     static void beforeAll() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com";
    }

    @Test
    @DisplayName("Добавление товара в wishlist")
    void addToWishlist() {
        ValidatableResponse response =
                given()
                        .log().all()
                        .contentType("application/json; charset=utf-8")
                        .cookie("Nop.customer=35b76c08-0231-43bc-870d-52f56ed79d09;")
                        .when()
                        .post("/addproducttocart/details/14/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));

    }

    @Test
    @DisplayName("Подписка на пустой email")
    void subscribeEmptyTest() {
        ValidatableResponse response =
                given()
                        .contentType("application/json; charset=utf-8")
                        .cookie("Nop.customer=f3eb1b09-5d79-4e6a-8414-6b688f676df7;")
                        .post("/subscribenewsletter")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("Success", is(false))
                        .body("Result", is("Enter valid email"));
    }

    @Test
    @DisplayName("Подписка на email")
    void subscribeFullTest() {
        ValidatableResponse response =
                given()
                        .formParam("email", "test@qa.guru")
                        .post("/subscribenewsletter")
                        .then()
                        .statusCode(200)
                        .body("Success", is(true))
                        .body("Result", is("Thank you for signing up! A verification email has been sent. We appreciate your interest."));
    }
}


