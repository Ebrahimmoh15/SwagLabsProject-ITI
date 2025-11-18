package tests;

import Base.LoginProblemUser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.Checkout2Page;
import pages.ProductPage;
import utils.PropertyReader;

public class CheckoutTwoPuserTest extends LoginProblemUser {

    @BeforeMethod
    public void setUp() {
        // Setup code before each test method
        CartPage cartPage = new CartPage(driver);
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        productPage.goToCart();
        cartPage.clickOnCheckoutButton();
        driver.get(PropertyReader.getProperty("checkout-step-two.url", "chrome"));
    }
//verfiy finish order button redirects to order complete page
@Test
    public void FinishOrderButtonIsDisplayed() {

        Checkout2Page checkout2Page = new Checkout2Page(driver);
        checkout2Page.clickFinishButton();
        String currentUrl = driver.getCurrentUrl();
    Assert.assertEquals(currentUrl, PropertyReader.getProperty("checkout-complete.url", "chrome"));
    }
    //verify redirection of the cancel button
    @Test
    public void CancelButtonRedirection() {

        Checkout2Page checkout2Page = new Checkout2Page(driver);
        checkout2Page.clickCancelButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, PropertyReader.getProperty("inventory.url", "chrome"));
    }

//verfiy summary order details are correct
    @Test
    public void SummaryDetailsAreCorrect() {

        Checkout2Page checkout2Page = new Checkout2Page(driver);

        Assert.assertTrue(checkout2Page.isSummaryDetailsCorrect("29.99"));

    }


}
