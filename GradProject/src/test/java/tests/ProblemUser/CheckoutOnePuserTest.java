package tests.ProblemUser;

import Base.LoginProblemUser;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CartPage;
import pages.Checkout1Page;
import pages.ProductPage;
import utils.PropertyReader;

public class CheckoutOnePuserTest extends LoginProblemUser {

    @BeforeMethod
    @Step("Navigate to Cart Page and proceed to Checkout Step One")
    public void navigateToCartPage() {
        // Navigate to the cart page before running tests
        CartPage cartPage = new CartPage(driver);
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        productPage.goToCart();
        cartPage.clickOnCheckoutButton();
        driver.get(PropertyReader.getProperty("checkout-step-one.url"));
    }

    @Test
    @Tag("CheckoutPage one")
    @Description("Verify entering valid data in Checkout Step One")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void ValidDateCheckoutOneTC() {
        // Step 1: Initialize Checkout1Page
        Checkout1Page checkout1Page = new Checkout1Page(driver);

        // Step 2: Enter valid data into the fields
        checkout1Page.enterFirstName(PropertyReader.getProperty("ValidFirstNameCheckout"));
        checkout1Page.enterLastName(PropertyReader.getProperty("validLastNameCheckout"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("ValidPostalCodeCheckout"));

        // Step 3: Perform soft assertions for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("ValidFirstNameCheckout"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("validLastNameCheckout"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("ValidPostalCodeCheckout"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");
        // Collects all soft assertion failures

        // Step 4: Click continue and verify navigation to Checkout Step Two
       checkout1Page.clickContinue();
       Assert.assertTrue(checkout1Page.isInCheckoutTwoPage("https://www.saucedemo.com/checkout-step-two.html"), "Did not navigate to Checkout Step Two page.");
    }

    //verfiy cancel button functionality test case
    @Test
    @Tag("CheckoutPage one")
    @Description("Verify Cancel Button Functionality in Checkout Step One")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.TRIVIAL)
    public void CancelButtonFunctionalityTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.clickCancel();
        Assert.assertTrue(checkout1Page.isInCheckoutTwoPage(PropertyReader.getProperty("cart.url")));
    }


    //verfiy missing first name error message test case
    @Test
    @Tag("CheckoutPage one")
    @Description("Verify Missing First Name Error Message in Checkout Step One")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void MissingFirstNameErrorMessageTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.enterFirstName(PropertyReader.getProperty("EmptyFirstNameCheckout"));
        checkout1Page.enterLastName(PropertyReader.getProperty("validLastNameCheckout"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("ValidPostalCodeCheckout"));
        //soft assertion for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("EmptyFirstNameCheckout"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("validLastNameCheckout"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("ValidPostalCodeCheckout"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        checkout1Page.clickContinue();
        String errorMessage = "Error: First Name is required";
        Assert.assertTrue(checkout1Page.getErrorMessage().contains(errorMessage));
    }

    //verfiy missing last name error message test case
    @Test
    @Tag("CheckoutPage one")
    @Description("Verify Missing Last Name Error Message in Checkout Step One")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void MissingLastNameErrorMessageTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.enterFirstName(PropertyReader.getProperty("ValidFirstNameCheckout"));
        checkout1Page.enterLastName(PropertyReader.getProperty("EmptyLastNameCheckout"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("ValidPostalCodeCheckout"));
        //soft assertion for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("ValidFirstNameCheckout"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("EmptyLastNameCheckout"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("ValidPostalCodeCheckout"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        checkout1Page.clickContinue();
        String errorMessage = "Error: Last Name is required";
        Assert.assertTrue(checkout1Page.getErrorMessage().contains(errorMessage));
    }

    //verfiy missing postal code error message test case
    @Test
    @Tag("CheckoutPage one")
    @Description("Verify Missing Postal Code Error Message in Checkout Step One")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.NORMAL)
    public void MissingPostalCodeErrorMessageTC() {
        Checkout1Page checkout1Page = new Checkout1Page(driver);
        checkout1Page.enterFirstName(PropertyReader.getProperty("ValidFirstNameCheckout"));
        checkout1Page.enterLastName(PropertyReader.getProperty("validLastNameCheckout"));
        checkout1Page.enterPostalCode(PropertyReader.getProperty("EmptyPostalCodeCheckout"));
        //soft assertion for the entered data
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(checkout1Page.getFirstNameValue(), PropertyReader.getProperty("ValidFirstNameCheckout"), "First name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getLastNameValue(), PropertyReader.getProperty("validLastNameCheckout"), "Last name value on page does not match expected.");
        softAssert.assertEquals(checkout1Page.getPostalCodeValue(), PropertyReader.getProperty("EmptyPostalCodeCheckout"), "Postal code value on page does not match expected.");
        softAssert.assertAll("Checkout One page data entry verification failed.");

        checkout1Page.clickContinue();
        String errorMessage = "Error: Postal Code is required";
        Assert.assertTrue(checkout1Page.getErrorMessage().contains(errorMessage));


    }
}
