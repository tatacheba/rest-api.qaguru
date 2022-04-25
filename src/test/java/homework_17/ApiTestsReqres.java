package homework_17;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ApiTestsReqres {

    @Test
    void singleUser() {
        get("https://reqres.in/api/users/5")
                .then()
                .statusCode(200)
                .body("data.id", is(5));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete("https://reqres.in/api/users/5")
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
    void updateUser() {
        String userUpdate = "{\"email\": \"captainamerica@marvel.com\", \"first_name\": \"Steve\", \"last_name\": \"Rogers\", \"job\": \"Captain America\"}";
        given()
                .contentType(JSON)
                .body(userUpdate)
                .when()
                .patch("https://reqres.in/api/users/5")
                .then()
                .statusCode(200)
                .body("first_name", is("Steve"))
                .body("job", is("Captain America"));
    }

    @Test
    void resourceNotFound() {
        given()
                .when()
                .get("https://reqres.in/api/unknown/27")
                .then()
                .statusCode(404);
    }
}
