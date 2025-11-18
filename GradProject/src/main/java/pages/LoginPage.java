package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitHelper;

import java.time.Duration;

public class LoginPage {

    //varaible

    private WebDriver driver ;

    //locators
    private final By username= By.id("user-name");
    private final By password = By.id("password");
    private final By loginbtn = By.id("login-button");
    private final By errorivlogin = By.cssSelector("h3[data-test='error']");

    //constructor
    public LoginPage(WebDriver driver)
    {
        this.driver=driver;
    }

    //actions

    public void login (String user , String pass )
    {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        WaitHelper waitHelper =new WaitHelper(driver);
        waitHelper.waitForClickable(loginbtn);
        waitHelper.waitForVisibility(loginbtn);
        driver.findElement(loginbtn).click();
    }

    public boolean IsLoggedIn (String expectedUrl)
    {
        return driver.getCurrentUrl().equals(expectedUrl);
    }
    public boolean ErrorMessageLogin()
    {
        return driver.findElement(errorivlogin).getText().contains("Username and password do not match any user in this service");
    }
    public boolean ErrorMessageEmptyLogin()
    {
        return driver.findElement(errorivlogin).getText().contains(" is required");
    }

    public boolean ErrorMessageLockoutLogin()
    {
        return driver.findElement(errorivlogin).getText().contains("has been locked out.");
    }


}
