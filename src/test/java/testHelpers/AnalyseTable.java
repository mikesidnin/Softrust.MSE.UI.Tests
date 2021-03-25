package testHelpers;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pageObjects.JournalMsePage.*;
import static pageObjects.JournalMsePage.nextPageButton;

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
            while (nextPageButton.isEnabled()) {
                //----Проверяем все вкладки, кроме последней---------------------------------------------------------
                for (int j = 1; j <= matValue; j++) {
                    //----Подставляем название ячейки и номер, забираем значение и сравниваем с эталоном-------------
                    String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                    assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
                }
                nextPageButton.click();
            }
            //----Проверяем последнюю вкладку--------------------------------------------------------------------
            for (int j = 1; j <= lastPageRows; j++) {
                //----Подставляем название ячейки и номер, забираем значение и сравниваем с эталоном-----------------
                String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + j + "]")).getText();
                assertTrue(nameLabel.contains(currentValue), column + " в гриде не совпадает с поиском!");
            }
        }
    }

    public static boolean isOnTable(String column, String interestedValue) {

        countRecGrid.shouldBe(Condition.visible);
        int numberRows = Integer.parseInt(countRecGrid.getText());
        assertNotEquals(0, numberRows, "Грида пустая!");

        int matValue = Integer.parseInt(matSelectValue.getText());
        boolean result = false;

        for (int i = 1; i <= matValue; i++) {
            String nameLabel = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + i + "]")).getText();
            result = nameLabel.contains(interestedValue);
        }
        return result;
    }

    public static String getRandomLineTextAndDoubleClick (String column){

        int maxPage = Integer.parseInt(currentLastPageButton.getText()) - 1;
        int matValue = Integer.parseInt(matSelectValue.getText()) - 1;
        int numberRows = Integer.parseInt(countRecGrid.getText());

        int lastPageRows = numberRows % 10;
        int randomLine;
        int randomPage = new Random().nextInt(maxPage) + 1;

        if (randomPage == maxPage) {
            randomLine = new Random().nextInt(lastPageRows) + 1;
        } else {
            randomLine = new Random().nextInt(matValue) + 1;
        }

        firstPageButton.click();

        if (randomPage != 1) {
            for (int i = 1; i <= randomPage - 1; i++) {
                nextPageButton.click();
                sleep(1000);
            }
        }
        String result = $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + randomLine + "]")).getText();
        $(By.xpath("(//td[contains(@class,'mat-cell cdk-column-" + column + "')])[" + randomLine + "]")).doubleClick();
        return result;
    }


}
