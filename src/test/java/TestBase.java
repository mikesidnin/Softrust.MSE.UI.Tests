import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.*;
import static helpers.Environment.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestBase {

      static String urlMse;

//----Локаторы-------------------------------------------------------------------------------------------------------
    //----Зеленый МИС------------------------------------------------------------------------------------------------
    SelenideElement loginControl = $("#Login"),
                    welcomeTitle = $(".main-title"),
                    passwordControl = $("#Password"),
                    signInButton = $(By.xpath("//input[contains(@class,'login-button linput')]")),
                    closeButton = $(By.xpath("//span[text()='Закрыть']")),
                    dashboardButton = $(By.xpath("//img[@alt='Логотип']")),
                    mseButton = $(By.xpath("//span[text()='Журнал направлений на МСЭ']")),
                    exitButton = $(By.xpath("//span[text()='Выход']"));

    //----Журнал МСЭ-------------------------------------------------------------------------------------------------
    SelenideElement snackbar = $(By.xpath(".//simple-snack-bar[@class='mat-simple-snackbar ng-star-inserted']/span")),
                    journalMseTitle = $(By.xpath("//div[text()=' Журнал направлений на медико-социальную экспертизу (МСЭ) ']")),
                    fioControl = $(byAttribute("formcontrolname","fio")),
                    fioControl2 = $(By.xpath("//input[contains(@class,'mat-input-element mat-form-field-autofill-control')]")),
                    statusControl = $("#mat-input-1"),
                    resultControl = $(By.xpath("//input[@placeholder='Заключение']")),
                    countRecGrid = $(".count-rec"),
                    dateBeginControl = $(By.xpath("//input[@name='date_valid']")),
                    dateEndControl = $(By.xpath("(//input[@name='date_valid'])[2]")),
                    authorControl = $("#mat-input-4"),
                    memberControl = $(By.id("mat-input-3")),
                    memberFirstValue = $(By.xpath("//span[@class='mat-option-text']")),
                    authorFirstValue = $(By.xpath("//span[@class='mat-option-text']")),
                    gridFio = $(By.xpath("//td[contains(@class,'mat-cell cdk-column-fio')]")),
                    gridDateCell = $(By.xpath("(//td[@role='gridcell'])[2]")),
                    eraiseButton = $(".dashed_link"),
                    gridSortByDateButton = $(By.xpath("//button[@class='mat-sort-header-button']")),
                    findButton = $(By.xpath("//span[text()='Найти']")),
                    nextButton = $(By.xpath("(//div[@class='page-element']//button)[3]"));

    //----Массив локаторов статусов направлений-----------------------------------------------------------------------
    //----Сделано в качестве тренеровки и эксперимента. Способ рабочий------------------------------------------------
    SelenideElement[] statusControlValues = {$(By.xpath("//span[@class='mat-option-text']")),
                                             $(By.xpath("(//span[@class='mat-option-text'])[2]")),
                                             $(By.xpath("(//span[@class='mat-option-text'])[3]")),
                                             $(By.xpath("//mat-option[@title='Ошибка при отправке']//span[1]")),
                                             $(By.xpath("//mat-option[@title='Зарегистрирован']//span[1]")),
                                             $(By.xpath("//mat-option[@title='Ошибка регистрации']//span[1]")),
                                             $(By.xpath("//mat-option[@title='Аннулирован']//span[1]"))
    };
    //----Сделано в качестве тренеровки и эксперимента. Способ рабочий------------------------------------------------
    SelenideElement[] resultControlValues = {$(By.xpath("//span[@class='mat-option-text']")),
                                             $(By.xpath("(//span[@class='mat-option-text'])[2]"))};

    //----Служебные--------------------------------------------------------------------------------------------------
    SelenideElement body = $("body");

//-------------------------------------------------------------------------------------------------------------------

    //----Собираем УРЛ журнала МСЭ-----------------------------------------------------------------------------------
    public String openURLMseJournal(String docPrvdId) {

        String ipAddress = "http://109.95.224.42:2165/",
                relativePath = "test2/mse/log",
                ticket = "ticket=D9VQnls2TN%2B2s%2FwzBNicUCtcrH1JNeDL6%2BRSxXP6jeJ%2FhYi6e%2FnGu13NyHHvOVBmmP%2BETKS%2FoKu%2FQraiIvDFoVWFUFZEhXMbiAauqiPVXFVP6vTnUOFTt49dWUKrLJu9qQ9jKZrXXXi%2Fv6VkaxQMVqcjfkV2ctNH5UXIdnUymK2FwDrwrUpwEtwKG9yrqvOnTwFM7NNxX%2BzH3lZd1sKNgRRnk1M4GqLT3uJFQ0Tkif%2BxaflrVRtMqRMen58nmCVjM%2FL0b4dFxdL7Yvlbyb5OvlP2qnf%2F5yfz9%2BfhQXSjiK5NMlmnYlwlEiae%2BhdLY2jxvxjjknDJwxIXxmrRvbt7jq1thpE%3D",
                docPrvdIdPart = "DocPrvdId=" + docPrvdId,
                misUrl = "MisUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2",
                returnUrl = "ReturnUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2%2FMain%2FDefault";

        return ipAddress + relativePath + "?" + ticket+ "&" + docPrvdIdPart + "&" + misUrl + "&" + returnUrl;
    }

    //----Собираем УРЛ зеленого МИСа---------------------------------------------------------------------------------
    public void openURLWebMis() {

        String ipAddress = "http://192.168.7.54/",
                relativePath = "mis/test2/";

        open(ipAddress + relativePath);
    }

    //----Метод анализа интересующих ячеек во всей результирующей таблице--------------------------------------------
            /*Внешний цикл по всем статусам направлений.
            Смотрим число найденый записей по статусу.
            Если < 10, то бегаем по числу строк.
            Если > 10, то бегаем по всем вкладкам.
            На последней вкладке смотрим на строки,
            кол-во которых остаток при делении на 10 общ числа строк.*/
    public void analyseTable(String column, String currentValue) {

        countRecGrid.shouldBe(Condition.visible);
        int numberRows = Integer.parseInt(countRecGrid.getText());
        Assertions.assertNotEquals(0, numberRows, "Грида пустая!");
        int lastPageRows = numberRows % 10;

        if (numberRows <= 10) {
            for (int j = 1; j <= numberRows; j++) {
                String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                assert currentValue != null;
                assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
            }
        } else {
            while (nextButton.isEnabled()) {
                for (int j = 1; j <= 10; j++) {
                    String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                    assert currentValue != null;
                    assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
                }
                nextButton.click();
            }

            for (int j = 1; j <= lastPageRows; j++) {
                String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                assert currentValue != null;
                assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
            }
        }
    }
//-------------------------------------------------------------------------------------------------------------------

    @BeforeAll
    @Step("Tests setup")
    public static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        configureSelenide();
    }

    @BeforeEach
    public void BeforeEachTest() {
        Configuration.startMaximized = true;
        //----Если настройка Jenkins пустая, то берем дефолтные значения + дебаг и тестирование-------------------------
        if (mkabId == null || tapId == null || docPrvdId == null) {
            mkabId = "2662334";
            tapId = "2670620";
            docPrvdId = "347";
        }
        //----Если настройка Jenkins пустая, то берем дефолтные значения + дебаг и тестирование-------------------------
        if (user == null || password == null) {
            user = "sidnin_doc"; //admin
            password = "11";
        }

        if (urlMse == null) {
            urlMse = openURLMseJournal(docPrvdId);
        }
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach(){
        String sessionId = getSessionId();
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        closeWebDriver();
        if (isVideoOn) attachVideo(sessionId);
    }
}
