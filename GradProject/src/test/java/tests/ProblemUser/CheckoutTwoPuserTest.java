package tests.ProblemUser;

import Base.LoginProblemUser;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.Checkout2Page;
import pages.ProductPage;
import utils.PropertyReader;

public class CheckoutTwoPuserTest extends LoginProblemUser {

    @Step("Navigate to Checkout Step Two Page")
    @BeforeMethod
    public void setUp() {
        // Setup code before each test method
        CartPage cartPage = new CartPage(driver);
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        productPage.goToCart();
        cartPage.clickOnCheckoutButton();
        driver.get(PropertyReader.getProperty("checkout-step-two.url"));
    }

    @Test
    @Tag("CheckoutPage Two")
    @Description("Verify Finish Order Button Redirects to Order Complete Page")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.TRIVIAL)
//verfiy finish order button redirects to order complete page
    public void FinishOrderButtonIsDisplayed() {

        Checkout2Page checkout2Page = new Checkout2Page(driver);
        checkout2Page.clickFinishButton();
        String currentUrl = driver.getCurrentUrl();
    Assert.assertEquals(currentUrl, PropertyReader.getProperty("checkout-complete.url"));
    }


    @Test
    @Tag("CheckoutPage Two")
    @Description("Verify Cancel Button Redirects to Inventory Page")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    //verify redirection of the cancel button
    public void CancelButtonRedirection() {

        Checkout2Page checkout2Page = new Checkout2Page(driver);
        checkout2Page.clickCancelButton();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, PropertyReader.getProperty("inventory.url"));
    }

    @Test
    @Tag("CheckoutPage Two")
    @Description("Verify Summary Order Details Are Correct")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
//verfiy summary order details are correct

    public void SummaryDetailsAreCorrect() {

        Checkout2Page checkout2Page = new Checkout2Page(driver);

        Assert.assertTrue(checkout2Page.isSummaryDetailsCorrect("29.99"));

    }


}
