import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pageObjects.JournalMsePage.*;
import static testHelpers.AnalyseTable.*;

@Owner("Mikhail Sidnin")
@Feature("Направление на МСЭ.")
@DisplayName("Направление на МСЭ.")
class DirectionMseTests extends TestBase {

    @Test
    @Tag("web")
    @DisplayName("Открытие направления на МСЭ из журнала МСЭ.")
    void openDirectionMseTests() {

        step("Фильтруем направления по статусу Сформирован.", () -> {

            open(urlMse);

            statusControl.click();
            statusControlValues[0].click();
            findButton.click();

            String interestedValue = statusControl.getValue();
            analyseTable("status", interestedValue);
        });

        step("Выбираем случайное направление со статусом Сформирован.", () -> {

            int maxPage = Integer.parseInt(currentLastPageButton.getText()) - 1;
            int matValue = Integer.parseInt(matSelectValue.getText()) - 1;
            int numberRows = Integer.parseInt(countRecGrid.getText());

            int lastPageRows = numberRows % 10;
            int randomLine;
            int randomPage = new Random().nextInt(maxPage) + 1;

            if (randomPage == maxPage){
                randomLine = new Random().nextInt(lastPageRows) + 1;
            }
            else {
                randomLine = new Random().nextInt(matValue) + 1;
            }

            firstPageButton.click();

            if (randomPage != 1) {
                for (int i = 1; i <= randomPage - 1; i++){
                    nextPageButton.click();
                    sleep(1000);
                }
            }

            randomPersonFio = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-fio')])[" + randomLine + "]")).getText();
            $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-fio')])[" + randomLine + "]")).doubleClick();

            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String personFioHeader = $(By.xpath("//span[@class='ng-tns-c1-0']")).getText();
            assertTrue(personFioHeader.equalsIgnoreCase(randomPersonFio), "ФИО пациента в журнале не совпадает с ФИО в направлении.");

            urlRandomDirectionMse = WebDriverRunner.url();
        });


    }
}
