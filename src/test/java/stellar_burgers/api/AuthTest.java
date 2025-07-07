package stellar_burgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import stellar_burgers.models.User;
import stellar_burgers.utils.RandomDataGenerator;
import stellar_burgers.utils.TestData;

import static org.hamcrest.Matchers.equalTo;

public class AuthTest {
    private final AuthClient authClient = new AuthClient();
    private final TestData testData = new TestData();

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUser() {
        User user = new User(
                RandomDataGenerator.generateRandomEmail(),
                testData.getCorrectPassword(),
                testData.getCorrectName());

        Response response = authClient.createUser(user);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void createExistingUser() {
        User user = new User(
                testData.getExistingEmail(),
                testData.getCorrectPassword(),
                testData.getCorrectName());

        Response response = authClient.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    public void createUserWithoutRequiredField() {
        User user = new User(
                testData.getExistingEmail(),
                testData.getCorrectPassword(),
                null);

        Response response = authClient.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Успешный вход пользователя")
    public void loginSuccess() {
        User user = new User(
                testData.getExistingEmail(),
                testData.getCorrectPassword(),
                testData.getCorrectName());

        Response response = authClient.login(user);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Вход с неверными учетными данными")
    public void loginWithInvalidCredentials() {
        User user = new User(
                testData.getNonExistingEmail(),
                testData.getIncorrectPassword(),
                testData.getCorrectName());

        Response response = authClient.login(user);
        response.then()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
    }
}
