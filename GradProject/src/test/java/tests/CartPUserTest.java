package tests;

import Base.LoginProblemUser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;
import utils.PropertyReader;

public class CartPUserTest extends LoginProblemUser {



    @BeforeMethod
    public void navigateToCartPage() {
        // Navigate to the cart page before running tests

        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();

        driver.get(PropertyReader.getProperty("cart.url", "chrome"));
    }

    //verify remove button functionality test case
    @Test
    public void RemoveButtonFunctionalityTC() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnRemoveButton();
        Assert.assertTrue(cartPage.IsItemRemoved());
    }
    //verfiy continue shopping button functionality test case
    @Test
    public void ContinueShoppingButtonFunctionalityTC() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnContinueShoppingButton();
        Assert.assertTrue(cartPage.isInCartPage(PropertyReader.getProperty("inventory.url", "chrome")));
    }
    //verfiy checkout button navigates to checkout page test case
    @Test
    public void CheckoutButtonNavigatesToCheckoutPageTC() {
        CartPage cartPage = new CartPage(driver);
        cartPage.clickOnCheckoutButton();
        assert cartPage.IsInCheckoutPage(PropertyReader.getProperty("checkout-step-one.url", "chrome"));
    }
//verfiy item details in cart page test case
    @Test
    public void ItemDetailsInCartPageTC() {
        CartPage cartPage = new CartPage(driver);
        String itemDetails = cartPage.getItemDetails();
        Assert.assertEquals(itemDetails, "Sauce Labs Backpack");
    }

}
