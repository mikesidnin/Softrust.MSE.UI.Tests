import org.junit.jupiter.api.Assertions;
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

@Owner("Mikhail Sidnin")
@Feature("Журнал направлений на МСЭ.")
class bMseJournalTests extends TestBase {

    @Test
    @Tag("web")
    @DisplayName("Поиск по номеру направления в журнале МСЭ.")
    void journalMseNumberTests() {

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

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по ФИО направления в журнале МСЭ.")
    void journalMseFIOTests() {
        step("Поиск по ФИО направления.", () -> {

            open(urlMse);

            findButton.click();

            int rnd = new Random().nextInt(10) + 1;
            String rndFio = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-fio')])[" + rnd + "]")).getText();

            fioControl.setValue(rndFio);
            findButton.click();

            String currentFio = fioControl2.getValue();

            analyseTable("fio", currentFio);
        });

        step("Очистка поля фильтрации ФИО.", () -> {

            String valueOld = fioControl2.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = fioControl2.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }
    @Test
    @Tag("web")
    @DisplayName("Поиск по дате подачи с в журнале МСЭ.")
    void journalMseDateBeginTests() {

        step("Поиск по дате подачи с.", () -> {

            open(urlMse);

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

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по дате подачи по в журнале МСЭ.")
    void journalMseDateEndTests() {

        step("Поиск по дате подачи по.", () -> {

            open(urlMse);

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

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по статусу направления в журнале МСЭ.")
    void journalMseStatusTests() {

        step("Поиск по статусу направления", () -> {

            open(urlMse);
            statusControl.click();

            for (int i = 0; i < statusControlValues.length; i++) {

                statusControl.click();
                statusControlValues[i].click();
                findButton.click();

                String currentStatus = statusControl.getValue();
                System.out.println(currentStatus);

                analyseTable("status", currentStatus);
                eraiseButton.click();
            }
            statusControl.click();
            statusControlValues[1].click();
        });

        step("Очистка поля фильтрации статус.", () -> {

            String valueOld = statusControl.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = statusControl.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по заключению направления в журнале МСЭ.")
    void journalMseResultTests() {

        step("Поиск по заключению направления", () -> {

            open(urlMse);

            for (int i = 0; i < resultControlValues.length; i++) {

                resultControl.click();
                resultControlValues[i].click();
                findButton.click();

                String currentConclusion = resultControl.getValue();
                System.out.println(currentConclusion);

                analyseTable("conclusion", currentConclusion);
                eraiseButton.click();
            }
            resultControl.click();
            resultControlValues[1].click();
        });

        step("Очистка поля фильтрации заключение.", () -> {

            String valueOld = resultControl.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = resultControl.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по автору и статусу направления в журнале МСЭ.")
    void journalMseAutorTests() {

        step("Поиск по автору и статусу направления на МСЭ.", () -> {

            open(urlMse);

            statusControl.click();
            statusControlValues[0].click();
            findButton.click();

            int rowsStatus = Integer.parseInt(countRecGrid.getText());
            eraiseButton.click();

            statusControl.click();
            statusControlValues[0].click();
            authorControl.click();
            authorFirstValue.click();
            findButton.click();

            int rowsStatusAndAuthor = Integer.parseInt(countRecGrid.getText());
            assertNotEquals(rowsStatus, rowsStatusAndAuthor, "Количество строк с автором и без одинаковое!");

            String currentAuthor = authorControl.getValue();
            String cutAuthor = currentAuthor.substring(currentAuthor.indexOf("-") + 2, currentAuthor.indexOf("(") - 1);
            System.out.println(cutAuthor);

            analyseTable("author", cutAuthor);
        });

        step("Очистка поля фильтрации автор.", () -> {

            String valueOld = authorControl.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = authorControl.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });

    }

    @Test
    @Tag("web")
    @DisplayName("Поиск по члену комиссии в журнале МСЭ.")
    void journalMseMemberTests() {

        step("Поиск по члену комиссии.", () -> {

            open(urlMse);

            findButton.click();

            int rowsStatus = Integer.parseInt(countRecGrid.getText());
            eraiseButton.click();

            memberControl.click();;
            memberFirstValue.click();
            findButton.click();

            int rowsStatusAndAuthor = Integer.parseInt(countRecGrid.getText());
            assertNotEquals(rowsStatus, rowsStatusAndAuthor, "Количество всех строк совпадает с количеством строк члена комиссии!");

            String currentMember = memberControl.getValue();
            String cutMember = currentMember.substring(currentMember.indexOf("-") + 2, currentMember.indexOf("(") - 1);

            analyseTable("docCommissions", cutMember);
        });

        step("Очистка поля фильтрации член комиссии.", () -> {

            String valueOld = memberControl.getValue();
            System.out.println(valueOld);

            eraiseButton.click();

            String valueNew = memberControl.getValue();
            System.out.println(valueNew);

            assertNotSame(valueNew, valueOld, "Ошибка при очистке полей фильтрации");
        });
    }
}




