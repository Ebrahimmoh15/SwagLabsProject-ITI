package Base;

import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import utils.PropertyReader;


public class BaseLogin extends Base {
    protected LoginPage loginPage;

    @Step("Logging in before each test method")
    @BeforeMethod
    public void loginToApplication() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("ValidUsername"),
                PropertyReader.getProperty("ValidPassword")
        );
        Assert.assertTrue(
                loginPage.IsLoggedIn("https://www.saucedemo.com/inventory.html"),
                "Login failed, inventory page not reached"
        );
    }


}
