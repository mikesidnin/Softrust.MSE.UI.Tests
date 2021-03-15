import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.*;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.*;
import static helpers.Environment.*;
import static testHelpers.GetMseUrl.*;

public class TestBase {

    static String urlMse;

    SelenideElement body = $("body");

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
            docPrvdId = "2521";
        }

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
