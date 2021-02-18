import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.*;
import static helpers.Environment.*;
import static helpers.LoadCredentials.getCredentialsFromJson;
import static io.qameta.allure.Allure.parameter;


public class TestBase {
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
                    journalMseTitle = $(By.xpath("//div[text()=' Журнал направлений на медико-социальную экспертизу (МСЭ) ']"));

    //----Служебные--------------------------------------------------------------------------------------------------
    SelenideElement body = $("body");


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
            user = "sidnin_doc";
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

    //----Собираем УРЛ белого случая лечения---------------------------------------------------------------------
    public void openURLWithMkabTap(String mkabID, String tapID, String docPrvdId) {

        String ipAddress = "http://109.95.224.42:2165/", //"http://109.95.224.42:2165/",
                relativePath = "test2/tap/card",
                ticket = "ticket=IhNpmO2lhSwbyJ1orHAD7qLggeE9S95511DXiMdsj3w5k220ljbVUxm0dip%2Bqupr7EaWXDIx%2BAIMTpb9cbtswnOPFJaPhIflTvaM2%2FYsk5CXrCvG6DgJpRgn4geoCNscGgSXZmR8J%2FcnGhMmxb3Z05OafJ51%2B2vDddXbjucEe9XjEw0PkUPz7pru5I7gM7vMz6lIbjDiV4g3fZiYD8EvODcDDANWXziHQjTrPhyhR0x64QC7d4iitOPGni%2Bg38kAvW6BGahH%2BVi9r6NUidg8rTxB36taAgHVFT01eAkjf%2BMbSFNOl%2BKT0CucPKjw%2BD6mJgbunKwaDvQiEXclRDrcMrkw9jc%3D",
                mkabIdPart = "MkabId=" + mkabID,
                tapIdPart = "TapId=" + tapID,
                docPrvdIdPart = "DocPrvdId=" + docPrvdId,
                misUrl = "MisUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2",
                returnUrl = "ReturnUrl=http:%2F%2F192.168.7.54%2Fmis%2Ftest2%2FSchedule";

        open(ipAddress + relativePath + "?" + ticket+ "&" + mkabIdPart + "&" + tapIdPart + "&" + docPrvdIdPart + "&" + misUrl + "&" + returnUrl);
    }

    public void openURLWebMis() {

        String ipAddress = "http://192.168.7.54/",
               relativePath = "mis/test2/";

        open(ipAddress + relativePath);
    }

}
