package testHelpers;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pageObjects.JournalMsePage.*;
import static pageObjects.JournalMsePage.nextButton;

public class AnalyseTable {

    public static void analyseTable(String column, String currentValue) {
        assert currentValue != null;

        //----Убеждаемся, что в результате поиска есть хотя бы одна строка-------------------------------------------
        countRecGrid.shouldBe(Condition.visible);
        int numberRows = Integer.parseInt(countRecGrid.getText());
        assertNotEquals(0, numberRows, "Грида пустая!");
        int matValue = Integer.parseInt(matSelectValue.getText());
        int lastPageRows = numberRows % 10;

        //----В результате поиска менее 10 строк---------------------------------------------------------------------
        if (numberRows <= matValue) {
            for (int j = 1; j <= numberRows; j++) {
                //----Подставляем название ячейки и номер, забираем значение и сравниваем с эталоном-----------------
                String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
            }
        }
        //----Если в результате поска более 10 строк, то проверяем все вкладки, пока кнопка "След." активна----------
        else {
            while (nextButton.isEnabled()) {
                //----Проверяем все вкладки, кроме последней---------------------------------------------------------
                for (int j = 1; j <= matValue; j++) {
                    //----Подставляем название ячейки и номер, забираем значение и сравниваем с эталоном-------------
                    String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                    assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
                }
                nextButton.click();
            }
            //----Проверяем последнюю вкладку--------------------------------------------------------------------
            for (int j = 1; j <= lastPageRows; j++) {
                //----Подставляем название ячейки и номер, забираем значение и сравниваем с эталоном-----------------
                String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
            }
        }
    }
}
