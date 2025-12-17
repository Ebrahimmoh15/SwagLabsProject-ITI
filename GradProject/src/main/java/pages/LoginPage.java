package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.WaitHelper;

import java.time.Duration;

public class LoginPage {
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);

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
    @Step("Logging in with username: {user}")
    public void login (String user , String pass )
    {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        WaitHelper waitHelper =new WaitHelper(driver);
        waitHelper.waitForClickable(loginbtn);
        waitHelper.waitForVisibility(loginbtn);
        driver.findElement(loginbtn).click();
        log.info("Attempting to log in with username: {}" , user);
    }
@Step("Verifying login status for expected URL: {expectedUrl}")
    public boolean IsLoggedIn (String expectedUrl)
    {
        return driver.getCurrentUrl().equals(expectedUrl);
    }
    @Step("Checking for error message on login failure")
    public boolean ErrorMessageLogin()
    {
        return driver.findElement(errorivlogin).getText().contains("Username and password do not match any user in this service");
    }
    @Step("Checking for error message on empty login fields")
    public boolean ErrorMessageEmptyLogin()
    {
        return driver.findElement(errorivlogin).getText().contains(" is required");
    }
@Step("Checking for error message on locked out user login attempt")
    public boolean ErrorMessageLockoutLogin()
    {
        return driver.findElement(errorivlogin).getText().contains("has been locked out.");
    }


}
