package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id='login'] input");
    private final SelenideElement password = $("[data-test-id='password'] input");
    private final SelenideElement loginButton= $("[data-test-id='action-login']");
    private final SelenideElement error = $("[data-test-id='error-notification'] .notification__content");
    private final SelenideElement emptyLogin = $("[data-test-id='login'] .input__sub");
    private final SelenideElement emptyPassword = $("[data-test-id='password'] .input__sub");

    public VerificationPage validLogin (DataHelper.AuthInfo data){
        login.setValue(data.getLogin());
        password.setValue(data.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void login (DataHelper.AuthInfo data){
        login.setValue(data.getLogin());
        password.setValue(data.getPassword());
        loginButton.click();
    }

    public void emptyLogin(DataHelper.AuthInfo info, String text) {
        password.setValue(info.getLogin());
        loginButton.click();
        emptyLogin.shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    public void emptyPassword(DataHelper.AuthInfo info, String text) {
        login.setValue(info.getLogin());
        loginButton.click();
        emptyPassword.shouldBe(Condition.visible).shouldHave(Condition.text(text));
    }

    public void errorMessage(String expectedText) {
        error.shouldHave(text(expectedText)).shouldBe(visible);
    }
}
