package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static Faker faker = new Faker();

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    @Value
    public static class VerificationCode {
        private String verificationCode;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getFakeLogin() {
        return faker.name().username();
    }

    public static String getFakePassword() {
        return faker.internet().password();
    }

    public static AuthInfo getFakeUser() {
        return new AuthInfo(getFakeLogin(), getFakePassword());
    }

    public static String getFakeVerificationCode() {
        return (faker.number().digits(6));
    }
}

