package tests;

import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.PropertyReader;

public class loginTestProblemUser extends tests.Base {

//Loginwith valid credentials test case
    @Test
    @Tag("Login")
    public void ValidLoginTC() {
        driver.get(PropertyReader.getProperty("base.url", "chrome"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("problem_user", "chrome"),
                PropertyReader.getProperty("problem_user_password", "chrome")
        );
        Assert.assertTrue(
                loginPage.IsLoggedIn(PropertyReader.getProperty("inventory.url", "chrome")));
    }

    //Login with invalid password
    @Test
    @Tag("Login")
    public void InvalidPasswordLoginTC() {
        driver.get(PropertyReader.getProperty("base.url", "chrome"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("problem_user", "chrome"),
                PropertyReader.getProperty("InvalidPassword", "chrome")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageLogin()
        );
    }

    //Login with empty username
    @Test
    @Tag("Login")
    public void EmptyUsernameLoginTC() {
        driver.get(PropertyReader.getProperty("base.url", "chrome"));;
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(PropertyReader.getProperty("EmptyUsername", "chrome"),
                PropertyReader.getProperty("problem_user_password", "chrome")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageEmptyLogin()
        );
    }
    //Login with empty password
    @Test
    @Tag("Login")
    public void EmptyPasswordLoginTC() {
        driver.get(PropertyReader.getProperty("base.url", "chrome"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("problem_user", "chrome"),
                PropertyReader.getProperty("EmptyPassword", "chrome")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageEmptyLogin()
        );
    }
    @Test
    @Tag("Login")
    //Login with locked out user
    public void LockedOutUserLoginTC() {
        driver.get(PropertyReader.getProperty("base.url", "chrome"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("LockedOutUsername", "chrome"),
                PropertyReader.getProperty("ValidPassword", "chrome")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageLockoutLogin()
        );
    }
@Test
@Tag("Login")
    //login with performance glitch user
    public void PerformanceGlitchUserLoginTC() {
    driver.get(PropertyReader.getProperty("base.url", "chrome"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("PerformanceGlitchUsername", "chrome"),
                PropertyReader.getProperty("ValidPassword", "chrome")
        );
        Assert.assertTrue(
                loginPage.IsLoggedIn(PropertyReader.getProperty("inventory.url", "chrome")));
    }
    @Test
    @Tag("Login")
    //Login with invalid username
    public void InvalidUsernameLoginTC() {
        driver.get(PropertyReader.getProperty("base.url", "chrome"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("InvalidUsername", "chrome"),
                PropertyReader.getProperty("ValidPassword", "chrome")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageLogin()
        );
    }
}
