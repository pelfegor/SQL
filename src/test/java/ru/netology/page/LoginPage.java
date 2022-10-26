package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement loginInputEmptyNotification = $("[data-test-id='login'] .input__sub");
    private final SelenideElement passwordField = $("[data-test-id=password] input");
    private final SelenideElement passwordInputEmptyNotification = $("[data-test-id='password'] .input__sub");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement notification = $("[data-test-id=error-notification]");
    private final SelenideElement errorButton = $("[data-test-id='error-notification'] button");

    public LoginPage() {
        loginField.should(visible);
        passwordField.should(visible);
        loginButton.should(visible);
        notification.should(hidden);
    }

    public void insert(String login, String password) {
        loginField.val(login);
        passwordField.val(password);
        loginButton.click();
    }

    public VerificationPage success() {
        notification.should(hidden);
        errorButton.should(hidden);
        return new VerificationPage();
    }

    public void failed() {
        notification.should(visible);
        notification.$(".notification__content").
                should(text("Ошибка! " + "Неверно указан логин или пароль"));
        errorButton.click();
        notification.should(hidden);
    }

    public void emptyLogin() {
        loginInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }

    public void emptyPassword() {
        passwordInputEmptyNotification.should(text("Поле обязательно для заполнения"));
    }
}