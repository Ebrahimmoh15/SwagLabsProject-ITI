package tests;

import Base.BaseLogin;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.Checkout1Page;
import pages.ProductPage;
import utils.PropertyReader;


public class CheckoutOneTest extends BaseLogin {

    @BeforeMethod
    public void navigateToCartPage() {
        // Navigate to the cart page before running tests
        CartPage cartPage = new CartPage(driver);
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        productPage.goToCart();
        cartPage.clickOnCheckoutButton();
        driver.get(PropertyReader.getProperty("checkout-step-one.url", "chrome"));
    }

    @Test
    public void ValidDateCheckoutOneTC() {
        // Step 1: Initialize Checkout1Page
        Checkout1Page checkout1Page = new Checkout1Page(driver);

        // Step 2: Enter valid data into the fields
        checkout1Page.enterFirstName(PropertyReader.getProperty("ValidFirstNameCheckout", "chrome"));
        checkout1Page.enterLastName(PropertyReader.getProperty("validLastNameCheckout", "chrome"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("ValidPostalCodeCheckout", "chrome"));

        // Step 3: Perform soft assertions for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("ValidFirstNameCheckout", "chrome"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("validLastNameCheckout", "chrome"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("ValidPostalCodeCheckout", "chrome"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        // Step 4: Click continue and verify navigation to Checkout Step Two
        checkout1Page.clickContinue();
        Assert.assertTrue(checkout1Page.isInCheckoutTwoPage(PropertyReader.getProperty("checkout-step-two.url", "chrome")));
    }

    //verfiy cancel button functionality test case
    @Test
    public void CancelButtonFunctionalityTC() {
        pages.Checkout1Page checkout1Page = new pages.Checkout1Page(driver);
        checkout1Page.clickCancel();
        Assert.assertTrue(checkout1Page.isInCheckoutTwoPage(PropertyReader.getProperty("cart.url", "chrome")));
    }
    //verfiy missing first name error message test case
    @Test
    public void MissingFirstNameErrorMessageTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.enterFirstName(PropertyReader.getProperty("EmptyFirstNameCheckout", "chrome"));
        checkout1Page.enterLastName(PropertyReader.getProperty("validLastNameCheckout", "chrome"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("ValidPostalCodeCheckout", "chrome"));
        //soft assertion for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("EmptyFirstNameCheckout", "chrome"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("validLastNameCheckout", "chrome"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("ValidPostalCodeCheckout", "chrome"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        checkout1Page.clickContinue();
        String errorMessage = "Error: First Name is required";
        Assert.assertTrue(checkout1Page.getErrorMessage().contains(errorMessage));
    }

    //verfiy missing last name error message test case
    @Test
    public void MissingLastNameErrorMessageTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.enterFirstName(PropertyReader.getProperty("ValidFirstNameCheckout", "chrome"));
        checkout1Page.enterLastName(PropertyReader.getProperty("EmptyLastNameCheckout", "chrome"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("ValidPostalCodeCheckout", "chrome"));
        //soft assertion for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("ValidFirstNameCheckout", "chrome"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("EmptyLastNameCheckout", "chrome"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("ValidPostalCodeCheckout", "chrome"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        checkout1Page.clickContinue();
        String errorMessage = "Error: Last Name is required";
        Assert.assertTrue(checkout1Page.getErrorMessage().contains(errorMessage));
    }

    //verfiy missing postal code error message test case
    @Test
    public void MissingPostalCodeErrorMessageTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.enterFirstName(PropertyReader.getProperty("ValidFirstNameCheckout", "chrome"));
        checkout1Page.enterLastName(PropertyReader.getProperty("validLastNameCheckout", "chrome"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("EmptyPostalCodeCheckout", "chrome"));
        //soft assertion for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("ValidFirstNameCheckout", "chrome"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("validLastNameCheckout", "chrome"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("EmptyPostalCodeCheckout", "chrome"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        checkout1Page.clickContinue();
        String errorMessage = "Error: Postal Code is required";
        Assert.assertTrue(checkout1Page.getErrorMessage().contains(errorMessage));


    }
}
