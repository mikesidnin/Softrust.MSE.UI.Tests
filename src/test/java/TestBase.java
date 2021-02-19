import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.*;
import static helpers.Environment.*;


public class TestBase {

     //----Локаторы---------------------------------------------------------------------------------------------------
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
                    countRecGrid = $(".count-rec"),
                    dateBeginControl = $(By.xpath("//input[@name='date_valid']")),
                    dateEndControl = $(By.xpath("(//input[@name='date_valid'])[2]")),
                    gridFio = $(By.xpath("//td[contains(@class,'mat-cell cdk-column-fio')]")),
                    gridDateCell = $(By.xpath("(//td[@role='gridcell'])[2]")),
                    eraiseButton = $(".dashed_link"),
                    gridSortByDateButton = $(By.xpath("//button[@class='mat-sort-header-button']")),
                    findButton = $(By.xpath("//span[text()='Найти']"));

    //----Служебные--------------------------------------------------------------------------------------------------
    SelenideElement body = $("body");
//-------------------------------------------------------------------------------------------------------------------

    //----Собираем УРЛ журнала МСЭ-----------------------------------------------------------------------------------
    public void openURLMseJournal(String docPrvdId) {

        String ipAddress = "http://109.95.224.42:2165/",
                relativePath = "test2/mse/log",
                ticket = "ticket=D9VQnls2TN%2B2s%2FwzBNicUCtcrH1JNeDL6%2BRSxXP6jeJ%2FhYi6e%2FnGu13NyHHvOVBmmP%2BETKS%2FoKu%2FQraiIvDFoVWFUFZEhXMbiAauqiPVXFVP6vTnUOFTt49dWUKrLJu9qQ9jKZrXXXi%2Fv6VkaxQMVqcjfkV2ctNH5UXIdnUymK2FwDrwrUpwEtwKG9yrqvOnTwFM7NNxX%2BzH3lZd1sKNgRRnk1M4GqLT3uJFQ0Tkif%2BxaflrVRtMqRMen58nmCVjM%2FL0b4dFxdL7Yvlbyb5OvlP2qnf%2F5yfz9%2BfhQXSjiK5NMlmnYlwlEiae%2BhdLY2jxvxjjknDJwxIXxmrRvbt7jq1thpE%3D",
                docPrvdIdPart = "DocPrvdId=" + docPrvdId,
                misUrl = "MisUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2",
                returnUrl = "ReturnUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2%2FMain%2FDefault";

        open(ipAddress + relativePath + "?" + ticket+ "&" + docPrvdIdPart + "&" + misUrl + "&" + returnUrl);
    }
    //----Собираем УРЛ зеленого МИСа---------------------------------------------------------------------------------
    public void openURLWebMis() {

        String ipAddress = "http://192.168.7.54/",
                relativePath = "mis/test2/";

        open(ipAddress + relativePath);
    }
//-------------------------------------------------------------------------------------------------------------------

    @BeforeAll
    @Step("Tests setup")
    public static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        configureSelenide();
    }

    @BeforeEach
    public void BeforeEachTest(){
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
