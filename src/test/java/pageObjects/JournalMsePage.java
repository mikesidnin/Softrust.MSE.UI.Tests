package pageObjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;

public class JournalMsePage {

    public static SelenideElement snackbar = $(By.xpath(".//simple-snack-bar[@class='mat-simple-snackbar ng-star-inserted']/span")),
                    journalMseTitle = $(By.xpath("//div[text()=' Журнал направлений на медико-социальную экспертизу (МСЭ) ']")),
                    fioControl = $(byAttribute("formcontrolname","fio")),
                    fioControl2 = $(By.xpath("//input[contains(@class,'mat-input-element mat-form-field-autofill-control')]")),
                    statusControl = $(By.xpath("//input[@placeholder='Статус']")),
                    resultControl = $(By.xpath("//input[@placeholder='Заключение']")),
                    countRecGrid = $(".count-rec"),
                    matSelectValue = $(".mat-select-value"),
                    dateBeginControl = $(By.xpath("//input[@name='date_valid']")),
                    dateEndControl = $(By.xpath("(//input[@name='date_valid'])[2]")),
                    authorControl = $("#mat-input-4"),
                    memberControl = $(By.id("mat-input-3")),
                    memberFirstValue = $(By.xpath("//span[@class='mat-option-text']")),
                    authorFirstValue = $(By.xpath("//span[@class='mat-option-text']")),
                    gridFio = $(By.xpath("//td[contains(@class,'mat-cell cdk-column-fio')]")),
                    gridDateCell = $(By.xpath("(//td[@role='gridcell'])[2]")),
                    eraseButton = $(".dashed_link");

    public static SelenideElement gridSortByDateButton = $(By.xpath("//button[@class='mat-sort-header-button']")),
                    findButton = $(By.xpath("//span[text()='Найти']")),
                    nextButton = $(By.xpath("(//div[@class='page-element']//button)[3]"));

    //----Массив локаторов статусов направлений-----------------------------------------------------------------------
    //----Сделано в качестве тренеровки и эксперимента. Способ рабочий------------------------------------------------
    public static SelenideElement[] statusControlValues = {$(By.xpath("//span[@class='mat-option-text']")),
                    $(By.xpath("(//span[@class='mat-option-text'])[2]")),
                    $(By.xpath("(//span[@class='mat-option-text'])[3]")),
                    $(By.xpath("//mat-option[@title='Ошибка при отправке']//span[1]")),
                    $(By.xpath("//mat-option[@title='Зарегистрирован']//span[1]")),
                    $(By.xpath("//mat-option[@title='Ошибка регистрации']//span[1]")),
                    $(By.xpath("//mat-option[@title='Аннулирован']//span[1]"))
    };
    //----Сделано в качестве тренеровки и эксперимента. Способ рабочий------------------------------------------------
    public static SelenideElement[] resultControlValues = {$(By.xpath("//span[@class='mat-option-text']")),
                    $(By.xpath("(//span[@class='mat-option-text'])[2]"))};

}
