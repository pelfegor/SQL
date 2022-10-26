package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthTest {
    DataHelper.UserData user;
    DataHelper.VerificationCode code;
    LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
        user = DataHelper.getUser();
        loginPage = new LoginPage();
    }

    @AfterEach
    public void setDownVerifyCodeTable() {
        SQLHelper.reloadVerifyCodeTable();
    }

    @AfterAll
    static void setDownAll() {
        SQLHelper.setDown();
    }

    @Test
    public void shouldAuth() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();
        code = DataHelper.getValidCode(user.getLogin());
        verifyPage.insert(code.getVerifyCode());
        verifyPage.success();
    }

    @Test
    public void shouldNotAuthWithInvalidPassword() {
        var password = DataHelper.getRandomPassword();
        loginPage.insert(user.getLogin(), password);
        loginPage.failed();
    }

    @Test
    public void shouldNotAuthWithInvalidLogin() {
        var login = DataHelper.getRandomLogin();
        loginPage.insert(login, user.getPassword());
        loginPage.failed();
    }

    @Test
    public void shouldNotificationWithEmptyPassword() {
        loginPage.insert(user.getLogin(), null);
        loginPage.emptyPassword();
    }

    @Test
    public void shouldNotificationWithEmptyLogin() {
        loginPage.insert(null, user.getPassword());
        loginPage.emptyLogin();
    }

    @Test
    public void shouldNotAuthWithInvalidVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();
        code = DataHelper.getRandomCode();
        verifyPage.insert(code.getVerifyCode());
        verifyPage.failed();
    }

    @Test
    public void shouldNotificationWithEmptyVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();
        verifyPage.insert(null);
        verifyPage.emptyCode();
    }

    @Test
    public void shouldNotAuthWithOldVerifyCode() {
        loginPage.insert(user.getLogin(), user.getPassword());
        var verifyPage = loginPage.success();
        code = DataHelper.getValidCode(user.getLogin());
        verifyPage.insert(code.getVerifyCode());
        verifyPage.success();

        open("http://localhost:9999/");
        loginPage.insert(user.getLogin(), user.getPassword());
        verifyPage = loginPage.success();
        verifyPage.insert(code.getVerifyCode());
        verifyPage.failed();
    }
}