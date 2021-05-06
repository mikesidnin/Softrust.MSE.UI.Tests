import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Tag;

import static com.codeborne.selenide.Condition.*;
import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.Selenide.*;
import static pageObjects.JournalMsePage.*;
import static pageObjects.WebMisPage.*;
import static testHelpers.GetUrl.openURLWebMis;


@Owner("Mikhail Sidnin")
@Feature("Журнал направлений на МСЭ.")
@DisplayName("Логин и переход в журнал МСЭ.")
class WebMisLoginTests extends TestBase {

    @Test
    @Tag("web")
    @DisplayName("Переход в журнал направлений на МСЭ.")
    void routToMseJournal() {

        step("Логин в ВебМИС.", () -> {

            openURLWebMis();
            sleep(1000);


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
                    end = start + 10 * 1000;

            while (System.currentTimeMillis() < end) {
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


            urlMse = WebDriverRunner.url();
        });
    }
}

