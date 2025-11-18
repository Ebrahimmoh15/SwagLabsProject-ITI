package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.PropertyReader;
import utils.WaitHelper;

public class CartPage {

    private WebDriver driver ;

    private final By CheckoutButton = By.id("checkout");
    private final By RemoveButton = By.id("remove-sauce-labs-backpack");
    private final By ContinueShoppingButton = By.id("continue-shopping");
    private final By Itemdetails = By.className("inventory_item_name");
    private final By CartBadge = By.className("shopping_cart_badge");
    //constructor
    public CartPage(WebDriver driver )
    {
        this.driver=driver;
        utils.WaitHelper waitHelper = new utils.WaitHelper(driver);
    }

    //function to click on checkout button
    public void clickOnCheckoutButton()
    {
        driver.findElement(CheckoutButton).click();
    }
    //function to click on remove button
    public void clickOnRemoveButton() {
        driver.findElement(RemoveButton).click();
    }

    //function to click on continue shopping button
    public void clickOnContinueShoppingButton() {
            driver.findElement(ContinueShoppingButton).click();
    }
    //function to get item details
    public String getItemDetails() {
        return driver.findElement(Itemdetails).getText();

    }
    //function to check if we are in cart page
    public boolean isInCartPage (String expectedUrl) {
        WaitHelper waitHelper = new WaitHelper(driver);
        waitHelper.waitForUrlContains(PropertyReader.getProperty("inventory.url", "chrome"));
        return driver.getCurrentUrl().equals(expectedUrl);

    }
//function check item removed
    public boolean IsItemRemoved() {
        return
                driver.findElements(CartBadge).isEmpty();
    }

    public boolean IsInCheckoutPage(String expectedUrl) {
        WaitHelper waitHelper = new WaitHelper(driver);
        waitHelper.waitForUrlContains(PropertyReader.getProperty("checkout-step-one.url", "chrome"));
        return driver.getCurrentUrl().equals(expectedUrl);
    }
}

