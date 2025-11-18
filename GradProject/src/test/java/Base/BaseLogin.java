package Base;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import tests.Base;
import utils.PropertyReader;


public class BaseLogin extends Base {
    protected LoginPage loginPage;

    @BeforeMethod
    public void loginToApplication() {
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        loginPage.login(
                PropertyReader.getProperty("ValidUsername", "chrome"),
                PropertyReader.getProperty("ValidPassword", "chrome")
        );
        Assert.assertTrue(
                loginPage.IsLoggedIn("https://www.saucedemo.com/inventory.html"),
                "Login failed, inventory page not reached"
        );
    }


}
