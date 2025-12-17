package tests.ProblemUser;

import Base.LoginProblemUser;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;
import utils.PropertyReader;

public class CartPUserTest extends LoginProblemUser {



    @BeforeMethod
    @Step("Navigate to Cart Page")
    @Severity(SeverityLevel.BLOCKER)
    public void navigateToCartPage() {
        // Navigate to the cart page before running tests

        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();

        driver.get(PropertyReader.getProperty("cart.url"));
    }

    //verify remove button functionality test case
    @Test
    @Tag("CartPage")
    @Description("Verify Remove Button Functionality")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.BLOCKER)
    public void RemoveButtonFunctionalityTC() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnRemoveButton();
        Assert.assertTrue(cartPage.IsItemRemoved());
    }


    //verfiy continue shopping button functionality test case
    @Test
    @Tag("CartPage")
    @Description("Verify Continue Shopping Button Functionality")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void ContinueShoppingButtonFunctionalityTC() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnContinueShoppingButton();
        Assert.assertTrue(cartPage.isInCartPage(PropertyReader.getProperty("inventory.url")));
    }


    //verfiy checkout button navigates to checkout page test case
    @Test
    @Tag("CartPage")
    @Description("Verify Checkout Button Navigates to Checkout Page")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void CheckoutButtonNavigatesToCheckoutPageTC() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnCheckoutButton();
        assert cartPage.IsInCheckoutPage(PropertyReader.getProperty("checkout-step-one.url"));
    }

//verfiy item details in cart page test case
    @Test
    @Tag("CartPage")
    @Description("Verify Item Details in Cart Page")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void ItemDetailsInCartPageTC() {
        CartPage cartPage = new CartPage(driver);
        String itemDetails = cartPage.getItemDetails();
        Assert.assertEquals(itemDetails, "Sauce Labs Backpack");
    }

}
