package stellar_burgers.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator {
    public static String generateRandomEmail() {
        return "user" + ThreadLocalRandom.current().nextInt(10000, 99999) + "@example.com";
    }
}