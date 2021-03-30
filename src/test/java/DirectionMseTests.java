import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pageObjects.DirectionMsePage;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pageObjects.JournalMsePage.*;
import static pageObjects.DirectionMsePage.*;
import static testHelpers.AnalyseTable.*;
import static testHelpers.GetUrl.openURLRandomDirection;

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
            sleep(1000);

            statusControl.click();
            statusControlValues[0].click();
            findButton.click();

            String interestedValue = statusControl.getValue();
            analyseTable("status", interestedValue);
        });

        step("Выбираем случайное направление со статусом Сформирован.", () -> {

            randomPersonFio = getRandomLineTextAndDoubleClick("fio");
            assertFalse(snackbar.exists(), "Неизвестная ошибка.");

            String personFioHeader = headerPatientFio.getText();
            assertTrue(personFioHeader.equalsIgnoreCase(randomPersonFio), "ФИО пациента в журнале не совпадает с ФИО в направлении.");

            urlRandomDirectionMse = WebDriverRunner.url();
            System.out.println(urlRandomDirectionMse);
        });
    }

        @Test
        @Tag("web")
        @DisplayName("Открытие направления на МСЭ из журнала МСЭ.")
        void protocolVkTests() {

            step("Фильтруем направления по статусу Сформирован.", () -> {

                if (urlRandomDirectionMse.contains("ticket")){
                    open(urlRandomDirectionMse);
                }
                else{
                    open(openURLRandomDirection(urlRandomDirectionMse));
                }


            });


        }


}
