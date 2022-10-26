package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

public class DataHelper {
    private static final Faker faker = new Faker();

    private DataHelper() {
    }

    @Value
    public static class UserData {
        String login;
        String password;
    }

    public static UserData getUser() {
        return new UserData("vasya", "qwerty123");
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    @Value
    public static class VerificationCode {
        String verifyCode;
    }

    public static VerificationCode getValidCode(String login) {
        return new VerificationCode(SQLHelper.getVerificationCode(login));
    }

    public static VerificationCode getRandomCode() {
        return new VerificationCode(faker.numerify("######"));
    }
}