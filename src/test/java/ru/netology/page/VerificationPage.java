package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement code = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");
    private final SelenideElement emptyCode = $("[data-test-id='code'] .input__sub");

    public VerificationPage() {
        code.shouldBe(visible, Duration.ofSeconds(5));
    }

    public DashboardPage validVerify (String verificationCode) {
        code.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void verify (String verificationCode) {
        code.setValue(verificationCode);
        verifyButton.click();
    }

    public void emptyCode() {
        verifyButton.click();
        emptyCode.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void errorMessage(String expectedText) {
        errorMessage.shouldHave(text(expectedText)).shouldBe(visible);
    }
}


