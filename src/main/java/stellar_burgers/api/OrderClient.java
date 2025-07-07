package stellar_burgers.api;

import io.restassured.response.Response;
import stellar_burgers.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api";

    public Response createOrder(Order order, String accessToken) {
        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(BASE_URL + "/orders")
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public Response createOrderWithoutAuth(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(BASE_URL + "/orders")
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public Response getIngredients() {
        return given()
                .when()
                .get(BASE_URL + "/ingredients");
    }
}