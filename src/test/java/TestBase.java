import com.codeborne.selenide.Configuration;
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
import static testHelpers.GetUrl.*;

public class TestBase {

    static String urlMse;
    static String urlRandomDirectionMse;
    static String randomPersonFio;

    @BeforeAll
    @Step("Tests setup")
    public static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        configureSelenide();
    }

    @BeforeEach
    public void beforeEachTest() {
        Configuration.startMaximized = true;

        //----Если настройка Jenkins пустая, то берем дефолтные значения + дебаг и тестирование-------------------------
        mkabId = (mkabId == null) ? "2662334" : mkabId;
        tapId = (tapId == null) ? "2670151" : tapId;
        docPrvdId = (docPrvdId == null) ? "2521" : docPrvdId;
        directionId = (directionId == null) ? "419" : directionId;

        user = (user == null) ? "sidnin_doc" : user;
        password = (password == null) ? "11" : password;

        urlMse = (urlMse == null) ? openURLMseJournal(docPrvdId) : urlMse;
        urlRandomDirectionMse = (urlRandomDirectionMse == null) ? openURLDirectionMse(mkabId, tapId, directionId, docPrvdId) : urlRandomDirectionMse;
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
