package stellar_burgers.api;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stellar_burgers.models.User;
import stellar_burgers.utils.RandomDataGenerator;
import stellar_burgers.utils.TestData;

import static org.hamcrest.Matchers.equalTo;

public class UserTest {
    private final AuthClient authClient = new AuthClient();
    private final UserClient userClient = new UserClient();
    private final TestData testData = new TestData();
    private String accessToken;
    private User testUser;

    @Before
    public void setUp() {
        testUser = new User(
                RandomDataGenerator.generateRandomEmail(),
                testData.getCorrectPassword(),
                testData.getCorrectName());

        authClient.createUser(testUser);
        Response loginResponse = authClient.login(testUser);
        accessToken = loginResponse.then().extract().path("accessToken");
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Получение информации о пользователе")
    public void getUserInfo() {
        Response response = userClient.getUserInfo(accessToken);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(testUser.getEmail()))
                .body("user.name", equalTo(testUser.getName()));
    }

    @Test
    @DisplayName("Обновление информации о пользователе")
    public void updateUserInfo() {
        User updatedUser = new User(
                RandomDataGenerator.generateRandomEmail(),
                "newPassword123",
                "New Name");

        Response response = userClient.updateUserInfo(updatedUser, accessToken);
        response.then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("user.email", equalTo(updatedUser.getEmail()))
                .body("user.name", equalTo(updatedUser.getName()));
    }

    @Test
    @DisplayName("Получение информации о пользователе без авторизации")
    public void getUserInfoWithoutAuth() {
        Response response = userClient.getUserInfo("");
        response.then()
                .statusCode(401)
                .body("message", equalTo("You should be authorised"));
    }
}
