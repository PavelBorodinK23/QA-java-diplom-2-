package stellar_burgers.api;

import io.restassured.response.Response;
import stellar_burgers.models.User;

import static io.restassured.RestAssured.given;

public class AuthClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    public Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(BASE_URL + "/auth/register");
    }

    public Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(BASE_URL + "/auth/login");
    }

    public Response logout(String refreshToken) {
        return given()
                .header("Content-type", "application/json")
                .body("{\"token\": \"" + refreshToken + "\"}")
                .when()
                .post(BASE_URL + "/auth/logout");
    }
}
