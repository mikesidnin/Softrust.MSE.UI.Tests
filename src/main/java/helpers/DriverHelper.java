package helpers;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import drivers.CustomWebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.Configuration.proxyEnabled;
import static com.codeborne.selenide.FileDownloadMode.HTTPGET;
import static com.codeborne.selenide.FileDownloadMode.PROXY;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.openqa.selenium.logging.LogType.BROWSER;
import static helpers.Environment.*;


public class DriverHelper {

    public static void configureSelenide() {
        Configuration.baseUrl = baseUrlProperty;
        Configuration.browser = CustomWebDriver.class.getName();
        Configuration.timeout = 10000;
        Configuration.proxyEnabled = true;
        Configuration.proxyHost = "192.168.7.152";
        Configuration.proxyPort = 8888;
        Configuration.fileDownload = PROXY;
    }

    public static String getSessionId(){
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString().replace("selenoid","");
    }

    public static String getConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }
}