package pageObjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class DirectionMsePage {
    public static SelenideElement headerPatientFio = $(By.xpath("//span[@class='ng-tns-c1-0']")),
            numberMse = $(By.xpath("(//input[contains(@class,'mat-input-element mat-form-field-autofill-control')])[3]")),
            closeDirectionButton = $(By.xpath("//button[text()=' Закрыть ']"));

}
