package homework_18;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ApiTestDemowebshop {

    @Test
    void addToCartWithCookie() {
        ValidatableResponse response =
                given()
                        .log().all()
                        .contentType("application/json; charset=utf-8")
                        .cookie("Nop.customer=35b76c08-0231-43bc-870d-52f56ed79d09;")
//                        .body("addtocart_14.EnteredQuantity=1")
                        .when()
                        .post("http://demowebshop.tricentis.com/addproducttocart/details/14/2")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));
        System.out.println(response);

    }
}


