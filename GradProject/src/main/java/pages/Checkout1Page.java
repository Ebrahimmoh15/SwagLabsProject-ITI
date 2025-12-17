package pages;

import io.qameta.allure.Step;
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

@Step("Enter first name: {firstName}")
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    @Step("Enter last name: {lastName}")
    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    @Step("Enter postal code: {postalCode}")
    public void enterPostalCode(String postalCode) {
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    @Step("Click Continue button")
    public void clickContinue() {
        driver.findElement(continueButton).click();
    }

    @Step("Click Cancel button")
    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    @Step("Check if in Checkout Step Two page with URL: {url}")
    public boolean isInCheckoutTwoPage(String url)
    {
        WaitHelper waitHelper= new WaitHelper(driver);
        waitHelper.waitForUrlContains(PropertyReader.getProperty("checkout-step-two.url"));
        return driver.getCurrentUrl().equals(url);
    }

    @Step("Get error message text")
    public String getErrorMessage() {

        return driver.findElement(errorMessage).getText();
    }

    @Step("Get First Name field value")
    public String getFirstNameValue() {
        return driver.findElement(firstNameField).getAttribute("value");
    }
    @Step("Get Last Name field value")
    public String getLastNameValue() {
        return driver.findElement(lastNameField).getAttribute("value");
    }

    @Step("Get Postal Code field value")
    public String getPostalCodeValue() {
        return driver.findElement(postalCodeField).getAttribute("value");
    }
}
