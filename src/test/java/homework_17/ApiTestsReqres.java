package homework_17;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTestsReqres {

    @Test
    void singleUser() {
        get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void createUser() {
        String user = "{\"email\": \"captainamerica@marvel.com\", \"first_name\": \"Steve\", \"last_name\": \"Rogers\", \"job\": \"Captain America\"}";
        given()
                .contentType(JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201);
    }

    @Test
      void listResource() {
        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("data.id[3]", is(4))
                .body("data.name[3]", is("aqua sky"))
                .body("data.year[3]", is(2003))
                .body("data.color[3]", is("#7BC4C4"))
                .body("data.pantone_value[3]", is("14-4811"));
    }

    @Test
    void resourceNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);
    }
}
