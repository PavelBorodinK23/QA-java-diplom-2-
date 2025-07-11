package stellar_burgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellar_burgers.models.User;

import static io.restassured.RestAssured.given;

public class AuthClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(BASE_URL + "/auth/register");
    }

    @Step("Авторизация пользователя")
    public Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(BASE_URL + "/auth/login");
    }

    @Step("Выход пользователя")
    public Response logout(String refreshToken) {
        return given()
                .header("Content-type", "application/json")
                .body("{\"token\": \"" + refreshToken + "\"}")
                .when()
                .post(BASE_URL + "/auth/logout");
    }
}
