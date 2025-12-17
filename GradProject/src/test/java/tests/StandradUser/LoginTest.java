package tests.StandradUser;

import Base.Base;
import io.qameta.allure.*;
import io.qameta.allure.testng.Tag;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.PropertyReader;



@Epic("User Authentication")
@Feature("Login")
public class LoginTest extends Base {


    @BeforeSuite
    public void setup2() {
        System.setProperty("autoGenerateReport", "true");
        System.setProperty("autoOpenReport", "true");
    }

//Loginwith valid credentials test case
    @Test
    @Tag("Login")
    @Description("Login with valid username and password")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.BLOCKER)
    public void ValidLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("ValidUsername"),
                PropertyReader.getProperty("ValidPassword")
        );
        Assert.assertTrue(
                loginPage.IsLoggedIn(PropertyReader.getProperty("inventory.url")));
    }

    //Login with invalid password
    @Test
    @Tag("Login")
    @Description("Login with valid username and invalid password")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.BLOCKER)
    public void InvalidPasswordLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("ValidUsername"),
                PropertyReader.getProperty("InvalidPassword")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageLogin()
        );
    }

    //Login with empty username
    @Test
    @Tag("Login")
    @Description("Login with empty username and valid password")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.BLOCKER)
    public void EmptyUsernameLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(PropertyReader.getProperty("EmptyUsername"),
                PropertyReader.getProperty("ValidPassword")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageEmptyLogin()
        );
    }
    //Login with empty password
    @Test
    @Tag("Login")
    @Description("Login with valid username and empty password")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.BLOCKER)
    public void EmptyPasswordLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("ValidUsername"),
                PropertyReader.getProperty("EmptyPassword")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageEmptyLogin()
        );
    }
    @Test
    @Tag("Login")
    @Description("Login with locked out user")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.MINOR)
    //Login with locked out user
    public void LockedOutUserLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("LockedOutUsername"),
                PropertyReader.getProperty("ValidPassword")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageLockoutLogin()
        );
    }
    @Test
    @Tag("Login")
    @Description("Login with performance glitch user")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.MINOR)
    //login with performance glitch user
    public void PerformanceGlitchUserLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("PerformanceGlitchUsername"),
                PropertyReader.getProperty("ValidPassword")
        );
        Assert.assertTrue(
                loginPage.IsLoggedIn(PropertyReader.getProperty("inventory.url")));
    }
    @Test
    @Tag("Login")
    @Tag("Login")
    @Description("Login with invalid username and valid password")
    @Owner("Ebrahim ")
    @Severity(SeverityLevel.BLOCKER)
    //Login with invalid username
    public void InvalidUsernameLoginTC() {
        driver.get(PropertyReader.getProperty("base.url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("InvalidUsername"),
                PropertyReader.getProperty("ValidPassword")
        );
        Assert.assertTrue(
                loginPage.ErrorMessageLogin()
        );
    }
}
