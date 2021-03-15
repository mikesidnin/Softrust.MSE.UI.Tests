package pageObjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class WebMisPage {

    public static SelenideElement loginControl = $("#Login"),
            welcomeTitle = $(".main-title"),
            passwordControl = $("#Password"),
            signInButton = $(By.xpath("//input[contains(@class,'login-button linput')]")),
            closeButton = $(By.xpath("//span[text()='Закрыть']")),
            dashboardButton = $(By.xpath("//img[@alt='Логотип']")),
            mseButton = $(By.xpath("//span[text()='Журнал направлений на МСЭ']")),
            exitButton = $(By.xpath("//span[text()='Выход']"));
}
