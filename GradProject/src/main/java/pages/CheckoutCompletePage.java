package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.time.Duration;

public class CheckoutCompletePage {


    private final By THANK_YOU_MESSAGE = By.className("complete-header");
    private final By BacktoProductbtn = By.id("back-to-products");
    private final By menubtn   = By.id("react-burger-menu-btn");
    private final By logoutbtn = By.id("logout_sidebar_link");
    private final By Allitems = By.id("inventory_sidebar_link");
    private final By Aboutbtn = By.id("about_sidebar_link");
    private final By Resetbtn = By.id("reset_sidebar_link");
    private final By MenuBarContainer = By.className("bm-menu-wrap");


    WebDriver driver;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Check if Thank You message is displayed")
    public boolean isThankYouMessageDisplayed() {
        return driver.findElement(THANK_YOU_MESSAGE).isDisplayed();
    }

    @Step("Click Back to Products button")
    public void clickBackToProductsButton() {
        driver.findElement(BacktoProductbtn).click();
    }

    @Step("Logout from menu")
    public void logoutFromMenu() {
        driver.findElement(menubtn).click();

        //wait for the menu to be fully visible (optional, depending on the app's behavior)
        WaitHelper waitHelper= new WaitHelper(driver);
        waitHelper.waitForVisibility(logoutbtn);
        driver.findElement(logoutbtn).click();
    }

    @Step("Verify all menu items are displayed")
    // verfiy all menu items are displayed
    public boolean MenuItemsAreDisplayed() {
        driver.findElement(menubtn).click();
        WaitHelper waitHelper= new WaitHelper(driver);
        waitHelper.waitForVisibility(MenuBarContainer);

        Boolean AllmenuDisplayed =driver.findElement(Allitems).isDisplayed() &&
                driver.findElement(Aboutbtn).isDisplayed() &&
                driver.findElement(Resetbtn).isDisplayed() &&
                driver.findElement(logoutbtn).isDisplayed();

        return AllmenuDisplayed;
    }
}
