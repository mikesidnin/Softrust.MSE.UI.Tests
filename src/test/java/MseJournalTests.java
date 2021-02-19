import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byAttribute;
import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;
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
    }

    @Test
    @Tag("web")
    @DisplayName("Тесты полей фильтрации в журнале МСЭ.")
    void journalMseTests() {

        step("Поиск по номеру направления.", () -> {
            openURLMseJournal(docPrvdId);

            fioControl.setValue("470101-202005");
            findButton.click();

            String numberControl = fioControl.getText();
            System.out.println(numberControl);
            assertTrue(numberControl.contains("470101"));
            assertTrue(numberControl.contains("202005"));

            String countRec = $(".count-rec").getText();
            System.out.println(countRec);
            assertTrue(countRec.contains("1"));

            String personName =$(By.xpath("//td[@role='gridcell'])[3]")).getText();
            System.out.println(personName);
            assertTrue(personName.contains("Жмышенко"));
            assertTrue(personName.contains("Валерий"));
            assertTrue(personName.contains("Альбертович"));

        });

    }
}



