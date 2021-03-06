import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static io.qameta.allure.Allure.step;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static pageObjects.DirectionMsePage.*;
import static pageObjects.JournalMsePage.*;
import static testHelpers.AnalyseTable.*;


@Owner("Mikhail Sidnin")
@Feature("Журнал направлений на МСЭ.")
@DisplayName("Журнал направлений на МСЭ.")
class MseJournalTests extends TestBase{

    @Test
    @Tag("web")
    @DisplayName("Поиск по номеру направления в журнале МСЭ.")
    void journalMseNumberTests() {

        step("Поиск по номеру направления.", () -> {

            open(urlMse);
            sleep(1000);
            if (!statusControl.isEnabled()){ sleep(3000);}

            randomPersonFio = getRandomLineTextAndDoubleClick("fio");
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String personFioHeader = headerPatientFio.getText();
            assertTrue(personFioHeader.equalsIgnoreCase(randomPersonFio), "ФИО пациента в журнале не совпадает с ФИО в направлении.");

            String randomNumber = numberMse.getValue();
            closeDirectionButton.click();

            fioControl.setValue(randomNumber);
            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");


            String numberControl = fioControl2.getValue();
            assert numberControl != null;
            assert randomNumber != null;
            assertTrue(numberControl.contains(randomNumber));

            String countRecValue = countRecGrid.getText();
            assertTrue(countRecValue.contains("1"));

            String personNameText = gridFio.getText();
            assertTrue(personNameText.contains(randomPersonFio));
        });

        step("Очистка поля фильтрации ФИО.", () -> {

            String valueOld = fioControl2.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = fioControl2.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по ФИО направления в журнале МСЭ.")
    void journalMseFIOTests() {
        step("Поиск по ФИО направления.", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}

            findButton.click();

            int rnd = new Random().nextInt(10) + 1;
            String rndFio = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-fio')])[" + rnd + "]")).getText();

            fioControl.setValue(rndFio);
            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String currentFio = fioControl2.getValue();
            analyseTable("fio", currentFio);
        });

        step("Очистка поля фильтрации ФИО.", () -> {

            String valueOld = fioControl2.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = fioControl2.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }
    @Test
    @Tag("web")
    @DisplayName("Поиск по дате подачи с в журнале МСЭ.")
    void journalMseDateBeginTests() {

        step("Поиск по дате подачи с.", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}

            dateBeginControl.setValue("01.02.2021");
            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            gridSortByDateButton.click();

            sleep(1000); // Ждем пока отрисуется грида чтобы забрать нужное значение

            String valueDateGrid = gridDateCell.getText();
            String valueDateControl = dateBeginControl.getValue();

            Date dateControl = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateControl);
            Date dateGrid = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateGrid);

            assertTrue(dateControl.getTime() <= dateGrid.getTime());
        });

        step("Очистка поля фильтрации Дата с.", () -> {

            String valueOld = dateBeginControl.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = dateBeginControl.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по дате подачи по в журнале МСЭ.")
    void journalMseDateEndTests() {

        step("Поиск по дате подачи по.", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}

            dateEndControl.setValue("19.02.2021");
            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            gridSortByDateButton.click();
            sleep(1000);
            gridSortByDateButton.click();
            sleep(1000); // Дважды кликаем и ждем, для того, чтобы отсортировать гриду

            String valueDateGrid = gridDateCell.getText();
            String valueDateControl = dateEndControl.getValue();

            Date dateControl = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateControl);
            Date dateGrid = new SimpleDateFormat("dd.MM.yyyy").parse(valueDateGrid);

            assertTrue(dateControl.getTime() >= dateGrid.getTime());
        });

        step("Очистка поля фильтрации Дата по.", () -> {

            String valueOld = dateEndControl.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = dateEndControl.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по статусу направления в журнале МСЭ.")
    void journalMseStatusTests() {

        step("Поиск по статусу направления", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}
            statusControl.click();

            for (int i = 0; i < statusControlValues.length; i++) {

                statusControl.click();
                statusControlValues[i].click();
                findButton.click();

                assertFalse(snackbar.exists(), "Неизвестная ошибка.");

                String currentStatus = statusControl.getValue();
                analyseTable("status", currentStatus);

                eraseButton.click();
            }
            statusControl.click();
            statusControlValues[1].click();
        });

        step("Очистка поля фильтрации статус.", () -> {

            String valueOld = statusControl.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = statusControl.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по заключению направления в журнале МСЭ.")
    void journalMseResultTests() {

        step("Поиск по заключению направления", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}

            for (int i = 0; i < resultControlValues.length; i++) {

                resultControl.click();
                resultControlValues[i].click();
                findButton.click();
                assertFalse(snackbar.exists(), "Неизвестная ошибка.");

                String currentConclusion = resultControl.getValue();
                analyseTable("conclusion", currentConclusion);
                eraseButton.click();
            }
            resultControl.click();
            resultControlValues[1].click();
        });

        step("Очистка поля фильтрации заключение.", () -> {

            String valueOld = resultControl.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = resultControl.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по автору и статусу направления в журнале МСЭ.")
    void journalMseAutorTests() {

        step("Поиск по автору и статусу направления на МСЭ.", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}

            statusControl.click();
            statusControlValues[0].click();
            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            int rowsStatus = Integer.parseInt(countRecGrid.getText());
            eraseButton.click();

            statusControl.click();
            statusControlValues[0].click();
            authorControl.click();
            authorFirstValue.click();
            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            int rowsStatusAndAuthor = Integer.parseInt(countRecGrid.getText());
            assertNotEquals(rowsStatus, rowsStatusAndAuthor, "Количество строк с автором и без одинаковое!");

            String currentAuthor = authorControl.getValue();
            String cutAuthor = currentAuthor.substring(currentAuthor.indexOf("-") + 2, currentAuthor.indexOf("(") - 1);

            analyseTable("author", cutAuthor);
        });

        step("Очистка поля фильтрации автор.", () -> {

            String valueOld = authorControl.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = authorControl.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });

    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по члену комиссии в журнале МСЭ.")
    void journalMseMemberTests() {

        step("Поиск по члену комиссии.", () -> {

            open(urlMse);
            if (!statusControl.isEnabled()){ sleep(3000);}

            findButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            int rowsStatus = Integer.parseInt(countRecGrid.getText());
            eraseButton.click();

            memberControl.click();
            memberFirstValue.click();
            findButton.click();

            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            int rowsStatusAndAuthor = Integer.parseInt(countRecGrid.getText());
            assertNotEquals(rowsStatus, rowsStatusAndAuthor, "Количество всех строк совпадает с количеством строк члена комиссии!");

            String currentMember = memberControl.getValue();
            String cutMember = currentMember.substring(currentMember.indexOf("-") + 2, currentMember.indexOf("(") - 1);

            analyseTable("docCommissions", cutMember);
        });

        step("Очистка поля фильтрации член комиссии.", () -> {

            String valueOld = memberControl.getValue();

            eraseButton.click();
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String valueNew = memberControl.getValue();
            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }
/*
Тест убран, так как непонятно как запустить прокси на удаленном сервере.
Локально с прокси в конфиге тест отрабатывает корректно.
Однако в Jenkins падают все тесты из-за прокси.

    @Test
    @Tag("web")
    @DisplayName("Проверка скачивания EXCEL файла.")
    void journalMseExcelDownload() {

        step("Скачивание Excel файла.", () -> {
            open(urlMse);
            File excelReport = excelButton.download(5000);
            assertTrue(excelReport.exists(), "Файл не найден.");
        });
    }
*/
}




