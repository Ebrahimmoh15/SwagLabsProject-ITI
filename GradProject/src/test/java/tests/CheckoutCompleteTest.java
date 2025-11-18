package tests;

import Base.BaseLogin;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.PropertyReader;

public class CheckoutCompleteTest extends BaseLogin {
    @BeforeMethod
    public void setUp() {
        // Setup code before each test method
        CartPage cartPage = new CartPage(driver);
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        productPage.goToCart();
        cartPage.clickOnCheckoutButton();
        driver.get(PropertyReader.getProperty("checkout-complete.url", "chrome"));
    }

    //verfiy thank you message is displayed
    @Test
    public void ThankYouMessageIsDisplayed() {
        CheckoutCompletePage checkoutCompletePage =new CheckoutCompletePage(driver);
        Assert.assertTrue(checkoutCompletePage.isThankYouMessageDisplayed());

    }
//verify back to products button redirection
    @Test
    public void BackToProductsButtonRedirection() {
        CheckoutCompletePage    checkoutCompletePage   =new CheckoutCompletePage(driver);
        checkoutCompletePage.clickBackToProductsButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, PropertyReader.getProperty("inventory.url", "chrome"));
    }
//logout from menu
@Test
    public void LogoutFromMenu() {
        CheckoutCompletePage   checkoutCompletePage   =new CheckoutCompletePage(driver);
        checkoutCompletePage.logoutFromMenu();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, PropertyReader.getProperty("base.url", "chrome"));
    }

    @Test
    public void MenuItemsAreDisplayed() {
        CheckoutCompletePage   checkoutCompletePage   =new CheckoutCompletePage(driver);
           Assert.assertTrue(checkoutCompletePage.MenuItemsAreDisplayed());
    }
}
