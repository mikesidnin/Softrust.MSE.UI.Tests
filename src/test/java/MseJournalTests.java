import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Assertions;
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
import static org.junit.jupiter.api.Assertions.*;

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

            urlMse = WebDriverRunner.url();
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Тесты полей фильтрации в журнале МСЭ.")
    void journalMseTests() {

        step("Поиск по номеру направления.", () -> {

            open(urlMse);

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
            sleep(1000);
            gridSortByDateButton.click();
            sleep(1000); // Дважды кликаем и ждем, для того, чтобы отсортировать гриду

            String valueDateGrid = gridDateCell.getText();
            String valueDateControl = dateEndControl.getValue();

            Date dateControl = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateControl);
            Date dateGrid = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateGrid);

            System.out.println(dateControl.getTime());
            System.out.println(dateGrid.getTime());

            assertTrue(dateControl.getTime() >= dateGrid.getTime());
        });

        step("Очистка поля фильтрации Дата по.", () -> {

            String valueOld = dateEndControl.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = dateEndControl.getValue();
            System.out.println(valueNew);

            Assertions.assertNotSame(valueNew, valueOld, "");
        });

        step("Поиск по статусу направления", () -> {

            /*Внешний цикл по всем статусам направлений.
            Смотрим число найденый записей по статусу.
            Если < 10, то бегаем по числу строк.
            Если > 10, то бегаем по всем вкладкам.
            На последней вкладке смотрим на строки,
            кол-во которых остаток при делении на 10 общ числа строк.
            */
            for (int i = 0; i < statusControlValues.length; i++) {

                statusControl.click();
                statusControlValues[i].click();
                findButton.click();

                String currentStatus = statusControl.getValue();
                System.out.println(currentStatus);

                int numberRows = Integer.parseInt(countRecGrid.getText());
                int lastPageRows = numberRows % 10;
                Assertions.assertNotEquals(0, numberRows, "Грида пустая!");

                if (numberRows <= 10){
                    for (int j = 1; j <= numberRows; j++) {
                        String statusLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-status')]//span)[" + j + "]")).getText();
                        Assertions.assertEquals(currentStatus, statusLabel, "Статус не совпадает!");
                    }
                }
                else {
                    while (nextButton.isEnabled()) {
                        for (int j = 1; j <= 10; j++) {
                            String statusLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-status')]//span)[" + j + "]")).getText();
                            Assertions.assertEquals(currentStatus, statusLabel, "Статус не совпадает!");
                        }
                        nextButton.click();
                    }

                    for (int j = 1; j <= lastPageRows; j++) {
                        String statusLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-status')]//span)[" + j + "]")).getText();
                        Assertions.assertEquals(currentStatus, statusLabel, "Статус не совпадает!");
                    }
                }
                eraiseButton.click();
            }
        });

        step("Поиск по заключению направления", () -> {

            for (int i = 0; i < resultControlValues.length; i++) {

                resultControl.click();
                resultControlValues[i].click();
                findButton.click();

                String currentResult = resultControl.getValue();
                System.out.println(currentResult);

                int numberRows = Integer.parseInt(countRecGrid.getText());
                int lastPageRows = numberRows % 10;
                Assertions.assertNotEquals(0, numberRows, "Грида пустая!");

                if (numberRows <= 10){
                    for (int j = 1; j <= numberRows; j++) {
                        String statusLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-conclusion')])["+ j + "]")).getText();
                        Assertions.assertEquals(currentResult, statusLabel, "Заключение не соответствует!");
                    }
                }
                else {
                    while (nextButton.isEnabled()) {
                        for (int j = 1; j <= 10; j++) {
                            String statusLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-conclusion')])["+ j + "]")).getText();
                            Assertions.assertEquals(currentResult, statusLabel, "Заключение не соответствует!");
                        }
                        nextButton.click();
                    }

                    for (int j = 1; j <= lastPageRows; j++) {
                        String statusLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-conclusion')])["+ j + "]")).getText();
                        Assertions.assertEquals(currentResult, statusLabel, "Заключение не соответствует!");
                    }
                }
                eraiseButton.click();
            }
        });

        step("Очистка поля фильтрации Дата по.", () -> {

            statusControl.click();
            statusControlValues[0].click();
            findButton.click();

            int rowsStatus = Integer.parseInt(countRecGrid.getText());
            eraiseButton.click();

            statusControl.click();
            statusControlValues[0].click();
            $(By.xpath("//input[@placeholder='Автор направления']")).click();
            $(By.xpath("//span[@class='mat-option-text']")).click();
            findButton.click();

            int rowsStatusAndAutor = Integer.parseInt(countRecGrid.getText());
            assertNotEquals(rowsStatus,rowsStatusAndAutor,"Количество строк с автором и без одинаковое!");

            String currentAutor= $(By.xpath("//input[@placeholder='Автор направления']")).getValue();
            System.out.println(currentAutor);

            int lastPageRows = rowsStatusAndAutor % 10;
            Assertions.assertNotEquals(0, rowsStatusAndAutor, "Грида пустая!");

            if (rowsStatusAndAutor <= 10){
                for (int j = 1; j <= rowsStatusAndAutor; j++) {
                    String autorLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-author')])["+ j + "]")).getText();
                    assertTrue(currentAutor.contains(autorLabel), "Автор не соответствует!");
                }
            }
            else {
                while (nextButton.isEnabled()) {
                    for (int j = 1; j <= 10; j++) {
                        String autorLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-author')])["+ j + "]")).getText();
                        assertTrue(currentAutor.contains(autorLabel), "Автор не соответствует!");
                    }
                    nextButton.click();
                }

                for (int j = 1; j <= lastPageRows; j++) {
                    String autorLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-author')])["+ j + "]")).getText();
                    assertTrue(currentAutor.contains(autorLabel), "Автор не соответствует!");
                }
            }
            eraiseButton.click();

        });

    }
}



