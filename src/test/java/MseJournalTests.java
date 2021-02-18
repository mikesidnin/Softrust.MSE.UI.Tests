import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Owner("Mikhail Sidnin")
@Feature("Журнал направлений на МСЭ.")
class MseJournalTests extends TestBase {

    @Test
    @Tag("web")
    @DisplayName("Переход в журнал направлений на МСЭ.")
    void routToMseJournal() {

        step("Логин в ВебМИС.", () -> {
            openURLWebMis();

            welcomeTitle.shouldHave(text("Вход в систему"));
            welcomeTitle.shouldBe(visible);

            loginControl.setValue(user);
            passwordControl.setValue(password);
            signInButton.click();

            body.shouldHave(text("Выход"));
            exitButton.shouldBe(visible);
        });

        step("Переход на дашборд.", () -> {
            //----В течении 10 секунд ждем выплывающие сообщения и закрываем их-------------------------------------
            long start = System.currentTimeMillis(),
                 end = start + 10*1000;

            while(System.currentTimeMillis() < end){
                if (closeButton.isDisplayed()) {
                    closeButton.click();
                }
            }
            //------------------------------------------------------------------------------------------------------
            dashboardButton.click();

            mseButton.scrollIntoView(("{behavior: \"instant\", block: \"center\", inline: \"center\"}"));
            mseButton.shouldBe(visible);
        });

        step("Переход в журнал направлений на МСЭ.", () -> {
            mseButton.scrollIntoView(("{behavior: \"instant\", block: \"center\", inline: \"center\"}"));
            mseButton.click();

            switchTo().window(1);
            body.shouldHave(text("Журнал направлений на медико-социальную экспертизу (МСЭ)"));
            journalMseTitle.shouldBe(visible);
        });

    @Test
    @Tag("web")
    @DisplayName("Переход в журнал направлений на МСЭ.")
    void journalMseTests() {

        step("Переход в журнал направлений на МСЭ.", () -> {

        });

    }
}



