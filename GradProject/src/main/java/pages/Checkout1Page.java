package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;
import utils.WaitHelper;

public class Checkout1Page {
    WebDriver driver;
    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public Checkout1Page(WebDriver driver) {
        this.driver = driver;
    }


    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }
    public void clickContinue() {
        driver.findElement(continueButton).click();
    }
    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public boolean isInCheckoutTwoPage(String url)
    {
        WaitHelper waitHelper= new WaitHelper(driver);
        waitHelper.waitForUrlContains(PropertyReader.getProperty("checkout-step-two.url", "chrome"));
        return driver.getCurrentUrl().equals(url);
    }
    public String getErrorMessage() {

        return driver.findElement(errorMessage).getText();
    }

    public String getFirstNameValue() {
        return driver.findElement(firstNameField).getAttribute("value");
    }

    public String getLastNameValue() {
        return driver.findElement(lastNameField).getAttribute("value");
    }
    public String getPostalCodeValue() {
        return driver.findElement(postalCodeField).getAttribute("value");
    }
}
