import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byAttribute;
import static helpers.Environment.*;
import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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

            String numberControl = fioControl2.getValue();
            System.out.println(numberControl);
            assertTrue(numberControl.contains("470101"));
            assertTrue(numberControl.contains("202005"));

            String countRecValue = countRecGrid.getText();
            System.out.println(countRecValue);
            assertTrue(countRecValue.contains("1"));

            String personNameText = gridFio.getText();
            System.out.println(personNameText);
            assertTrue(personNameText.contains("Жмышенко"));
            assertTrue(personNameText.contains("Валерий"));
            assertTrue(personNameText.contains("Альбертович"));
        });

        step("Очистка поля фильтрации ФИО.", () -> {

            String valueOld = fioControl2.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = fioControl2.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld);

        });

        step("Поиск по ФИО направления.", () -> {

            fioControl.setValue("Проверочный");
            findButton.click();

            String numberControl = fioControl2.getValue();
            System.out.println(numberControl);
            assertTrue(numberControl.contains("Проверочный"));

            String countRecValue = countRecGrid.getText();
            System.out.println(countRecValue);
            assertTrue(countRecValue.contains("1"));

            String personNameText = gridFio.getText();
            System.out.println(personNameText);
            assertTrue(personNameText.contains("Проверочный"));
            assertTrue(personNameText.contains("Николай"));
            assertTrue(personNameText.contains("Сергеевич"));
        });

        step("Очистка поля фильтрации ФИО.", () -> {

            String valueOld = fioControl2.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = fioControl2.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld);

        });

        step("Поиск по дате подачи с.", () -> {

            dateBeginControl.setValue("01.02.2021");
            findButton.click();
            gridSortByDateButton.click();

            sleep(1000); // Ждем пока отрисуется грида чтобы забрать нужное значение

            String valueDateGrid = gridDateCell.getText();
            String valueDateControl = dateBeginControl.getValue();

            Date dateControl = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateControl);
            Date dateGrid = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateGrid);

            System.out.println(dateControl.getTime());
            System.out.println(dateGrid.getTime());

            assertTrue(dateControl.getTime() <= dateGrid.getTime());
        });

        step("Очистка поля фильтрации Дата с.", () -> {

            String valueOld = dateBeginControl.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = dateBeginControl.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld);

        });

        step("Поиск по дате подачи по.", () -> {

            dateEndControl.setValue("19.02.2021");
            findButton.click();
            gridSortByDateButton.click();

            sleep(1000); // Ждем пока отрисуется грида чтобы забрать нужное значение

            String valueDateGrid = gridDateCell.getText();
            String valueDateControl = dateEndControl.getValue();

            Date dateControl = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateControl);
            Date dateGrid = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateGrid);

            System.out.println(dateControl.getTime());
            System.out.println(dateGrid.getTime());

            assertTrue(dateControl.getTime() <= dateGrid.getTime());
        });





    }
}



