package tests.StandradUser;

import Base.BaseLogin;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.PropertyReader;

public class CheckoutCompleteTest extends BaseLogin {
    @Step("Setup before each test method")
    @BeforeMethod
    public void setUp() {
        // Setup code before each test method
        CartPage cartPage = new CartPage(driver);
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        productPage.goToCart();
        cartPage.clickOnCheckoutButton();
        driver.get(PropertyReader.getProperty("checkout-complete.url"));
    }


    //verfiy thank you message is displayed
    @Test
    @Tag("CheckoutCompleteTest")
    @Description("Verify that the thank you message is displayed on the checkout complete page.")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.CRITICAL)
    public void ThankYouMessageIsDisplayed() {
        CheckoutCompletePage checkoutCompletePage =new CheckoutCompletePage(driver);
        Assert.assertTrue(checkoutCompletePage.isThankYouMessageDisplayed());

    }
//verify back to products button redirection
    @Test
    @Tag("CheckoutCompleteTest")
    @Description("Verify that the Back to Products button redirects to the inventory page.")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void BackToProductsButtonRedirection() {
        CheckoutCompletePage    checkoutCompletePage   =new CheckoutCompletePage(driver);
        checkoutCompletePage.clickBackToProductsButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, PropertyReader.getProperty("inventory.url"));
    }


//logout from menu
    @Test
    @Tag("CheckoutCompleteTest")
    @Description("Verify that the user can log out from the menu on the checkout complete page.")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.MINOR)
    public void LogoutFromMenu() {
        CheckoutCompletePage   checkoutCompletePage   =new CheckoutCompletePage(driver);
        checkoutCompletePage.logoutFromMenu();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, PropertyReader.getProperty("base.url"));
    }


    @Test
    @Tag("CheckoutCompleteTest")
    @Description("Verify that the thank you message is displayed on the checkout complete page.")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.CRITICAL)
    public void MenuItemsAreDisplayed() {
        CheckoutCompletePage   checkoutCompletePage   =new CheckoutCompletePage(driver);
           Assert.assertTrue(checkoutCompletePage.MenuItemsAreDisplayed());
    }
}
