package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout2Page {
    WebDriver driver;

    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");
    private final By summaryTotalLabel = By.className("summary_total_label");
    private final By summaryTaxLabel = By.className("summary_tax_label");
    private final By summarySubtotalLabel = By.className("summary_subtotal_label");

    public Checkout2Page(WebDriver driver) {
        this.driver = driver;
    }

    //funciton to check summary details are correct
    public boolean isSummaryDetailsCorrect(String expectedTotal) {
        String totalText = driver.findElement(summarySubtotalLabel).getText();
        return totalText.contains(expectedTotal);
    }

    public void clickFinishButton() {
        driver.findElement(finishButton).click();
    }

    public void clickCancelButton() {
        driver.findElement(cancelButton).click();
    }
}
