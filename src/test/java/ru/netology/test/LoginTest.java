package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class LoginTest {
    LoginPage loginPage;

    @AfterAll
    static void cleanAll() {
        cleanDatabase();
    }

    @AfterEach
    void cleanAuthCode() {
        cleanAuthcode();
    }

    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void validLoginAndVerify() { //вход с верными логином и паролем
        var verificationPage = loginPage.validLogin(getAuthInfo());
        verificationPage.validVerify(SQLHelper.getVerificationCode());
    }

    @Test
    void invalidLoginAndPassword() { //вход с некорректными логином и паролем
        loginPage.login(getFakeUser());
        loginPage.errorMessage("Неверно указан логин или пароль");
    }

    @Test
    void invalidVerificationCode() { //вход с некорректным кодом
        var verificationPage = loginPage.validLogin(getAuthInfo());
        verificationPage.verify(DataHelper.getFakeVerificationCode());
        verificationPage.errorMessage("Неверно указан код");
    }

    @Test
    void loginFieldWithoutLogin() { //в поле ЛОГИН ничего не введено
        loginPage.emptyLogin(getAuthInfo(), "Поле обязательно для заполнения");
    }

    @Test
    void passwordFieldWithoutPassword() { //в поле ПАРОЛЬ ничего не введено
        loginPage.emptyPassword(getAuthInfo(), "Поле обязательно для заполнения");
    }

    @Test
    void codeFieldWithoutCode() { //в поле КОД ничего не введено
        var verificationPage = loginPage.validLogin(getAuthInfo());
        verificationPage.emptyCode();
    }

    @Test
    void blocksUserIfPasswordIsInvalidForThreeTimes() { //пользователя блокируют, если он ввёл пароль неправильно 3 раза
        var authInfo = DataHelper.getAuthInfo();
        for (int i = 0; i < 3; i++) {
            refresh();
            loginPage.login(new AuthInfo(authInfo.getLogin(), getFakePassword()));
            loginPage.errorMessage("Неверно указан логин или пароль");
        }
        Assertions.assertEquals("blocked", getStatus());
    }

}