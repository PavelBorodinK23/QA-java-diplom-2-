package stellar_burgers.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import stellar_burgers.models.User;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    @Step("Получение информации о пользователе")
    public Response getUserInfo(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .get(BASE_URL + "/auth/user");
    }

    @Step("Обновление информации о пользователе")
    public Response updateUserInfo(User user, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(BASE_URL + "/auth/user");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URL + "/auth/user");
    }
}
