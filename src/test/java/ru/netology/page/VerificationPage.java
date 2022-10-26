package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement codeFieldEmptyNotification = $("[data-test-id='code'] .input__sub");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement notification = $("[data-test-id=error-notification]");
    private final SelenideElement errorButton = $("[data-test-id='error-notification'] button");

    public VerificationPage() {
        codeField.should(visible);
        verifyButton.should(visible);
        notification.should(hidden);
        errorButton.should(hidden);
    }

    public void insert(String code) {
        codeField.val(code);
        verifyButton.click();
    }

    public DashboardPage success() {
        notification.should(hidden);
        errorButton.should(hidden);
        return new DashboardPage();
    }

    public void failed() {
        notification.should(visible);
        notification.$(".notification__content").
                should(text("Ошибка! " + "Неверно указан код! Попробуйте ещё раз."));
        errorButton.click();
        notification.should(hidden);
    }

    public void emptyCode() {
        codeFieldEmptyNotification.should(text("Поле обязательно для заполнения"));
    }
}