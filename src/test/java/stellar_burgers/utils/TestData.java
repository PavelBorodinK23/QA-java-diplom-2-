package stellar_burgers.utils;

import java.util.UUID;

public class TestData {
    // Генерация уникальных данных для каждого теста
    public String getExistingEmail() {
        return "test-user-" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public String getNonExistingEmail() {
        return "non-existing-" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public String getCorrectPassword() {
        return "password-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getIncorrectPassword() {
        return "wrong-password-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getCorrectName() {
        return "Test User " + UUID.randomUUID().toString().substring(0, 8);
    }

    public String getBunId() { return "61c0c5a71d1f82001bdaaa6d"; }
    public String getMainId() { return "61c0c5a71d1f82001bdaaa71"; }
    public String getSauceId() { return "61c0c5a71d1f82001bdaaa72"; }
    public String getInvalidIngredient() { return "000000000000000000000000"; }
}