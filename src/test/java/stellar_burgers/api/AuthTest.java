package stellar_burgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import stellar_burgers.models.User;
import stellar_burgers.utils.RandomDataGenerator;
import stellar_burgers.utils.TestData;

import static org.hamcrest.Matchers.equalTo;

public class AuthTest {
    private final AuthClient authClient = new AuthClient();
    private final TestData testData = new TestData();
    private User existingUser;

    @Before
    public void setUp() {
        // Создаем тестового пользователя перед тестами на логин
        existingUser = new User(
                RandomDataGenerator.generateRandomEmail(),
                testData.getCorrectPassword(),
                testData.getCorrectName());
        authClient.createUser(existingUser);
    }

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
        Response response = authClient.createUser(existingUser);
        response.then()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без email")
    public void createUserWithoutEmail() {
        User user = new User(
                null,
                testData.getCorrectPassword(),
                testData.getCorrectName());

        Response response = authClient.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPassword() {
        User user = new User(
                RandomDataGenerator.generateRandomEmail(),
                null,
                testData.getCorrectName());

        Response response = authClient.createUser(user);
        response.then()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Успешный вход пользователя")
    public void loginSuccess() {
        Response response = authClient.login(existingUser);
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
