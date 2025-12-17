package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.WaitHelper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductPage {
    //variable
    private final WebDriver driver ;

    //locators
    private final By  Addbackpack =By.id("add-to-cart-sauce-labs-backpack");
    private final By  AddbikeLight =By.id("add-to-cart-sauce-labs-bike-light");
    private final By  AddboltTShirt = By.id("add-to-cart-sauce-labs-bolt-t-shirt");
    private final By AddlabsFleeceJacket = By.id("add-to-cart-sauce-labs-fleece-jacket");
    private final By AddlabsOnesie = By.id("add-to-cart-sauce-labs-onesie");
    private final By AddlabsTShirtRed = By.id("add-to-cart-test.allthethings()-t-shirt-(red)");
    private final By CartIcon = By.cssSelector(".shopping_cart_link");
    private final By ImageOfBackpack = By.cssSelector("img[alt='Sauce Labs Backpack']");
    private final By ImageOfBikeLight = By.cssSelector("img[alt='Sauce Labs Bike Light']");
    private final By ImageOfBoltTShirt = By.cssSelector("img[alt='Sauce Labs Bolt T-Shirt']");
    private final By ImageOfFleeceJacket = By.cssSelector("img[alt='Sauce Labs Fleece Jacket']");
    private final By ImageOfOnesie = By.cssSelector("img[alt='Sauce Labs Onesie']");
    private final By ImageOfTShirtRed = By.cssSelector("img[alt='Test.allTheThings() T-Shirt (Red)']");
    private final By RemoveButton = By.id("remove-sauce-labs-backpack");
    private final By Details =By.id("item_4_title_link");
    private final By ProductDetails = By.cssSelector(".inventory_details_desc.large_size");
    private final By SortDropdown = By.cssSelector(".product_sort_container");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    private final By Backtoproductbutton = By.id("back-to-products");
    //constructor
    public ProductPage(WebDriver driver )
    {
        this.driver =driver;
    }
    //actions

    @Step("Add multiple products to cart")
    public void addProductsToCart()
    {
        //wait for page to load could be added here
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(Addbackpack).click();
       driver.findElement(AddbikeLight).click();
        driver.findElement(AddboltTShirt).click();
      /*  driver.findElement(AddlabsFleeceJacket).click();
        driver.findElement(AddlabsOnesie).click();
        driver.findElement(AddlabsTShirtRed).click(); */
    }
    @Step("Add one product to cart")
    public void AddOneToCart ()
    {
        driver.findElement(Addbackpack).click();
    }
    @Step("Navigate to cart page")
    public void goToCart()
    {
        driver.findElement(CartIcon).click();
    }
    //function to check if we are in product page
    @Step("Check if in product page")
    public boolean isInProductPage (String expectedUrl)
    {
        return driver.getCurrentUrl().equals(expectedUrl);
    }
    //function to get number of items in cart
    @Step("Get number of items in cart")
    public String getNumberOfItemsInCart()
    {
        return driver.findElement(CartIcon).getText();
    }
    @Step("Check if product page is loaded")
    //function to check loaded page
    public boolean isPageLoaded()
    {
        return driver.findElement(Addbackpack).isDisplayed();
    }


//function to check for product is visible
    @Step("Check if products are visible")
    public boolean IsProductVisible()
    {
        WaitHelper waitHelper = new WaitHelper(driver);
        waitHelper.waitForVisibility(ImageOfBikeLight);
        waitHelper.waitForVisibility(ImageOfBackpack);
        waitHelper.waitForVisibility(ImageOfBoltTShirt);
        waitHelper.waitForVisibility(ImageOfFleeceJacket);
        waitHelper.waitForVisibility(ImageOfOnesie);
        waitHelper.waitForVisibility(ImageOfTShirtRed);
        Boolean ProductVisible =
        driver.findElement(ImageOfBikeLight).isDisplayed() &&
        driver.findElement(ImageOfBackpack).isDisplayed() &&
        driver.findElement(ImageOfBoltTShirt).isDisplayed()    &&
        driver.findElement(ImageOfFleeceJacket).isDisplayed() &&
        driver.findElement(ImageOfOnesie).isDisplayed() &&
        driver.findElement(ImageOfTShirtRed).isDisplayed();
        return ProductVisible;

    }

    @Step("Remove item from cart")
    //Function to remove item from cart
    public  void RemoveItemFromCart ()
    {

        driver.findElement(RemoveButton).click();
    }

    @Step("View product item details")
    public void ViewProductItemDetails() {
        driver.findElement(Details).click();
    }
@Step("Check if product item details are displayed")
    public boolean isProductItemDetailsDisplayed() {

        return driver.findElement(ProductDetails).isDisplayed();
    }
@Step("Sort products by {value}")
    public void Sorting(String value) {

        new Select(driver.findElement(SortDropdown)).selectByValue(value);

    }
@Step("Check if list is sorted in ascending order")
    public boolean isSortedAscending(List<? extends Comparable> list) {
        List<Comparable> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        return sorted.equals(list);
    }

    @Step("Get all product names as list")
    public List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(itemNames);
        List<String> names = new ArrayList<>();
        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }
        return names;
    }
    @Step("Check if list is sorted in descending order")
    public boolean isSortedDescending(List<? extends Comparable> list) {
        List<Comparable> sorted = new ArrayList<>(list);
        Collections.sort(sorted, Collections.reverseOrder());
        return sorted.equals(list);
    }

    // Get all product prices as list (numeric values)
    @Step("Get all product prices as list")
    public List<Double> getProductPrices() {
        List<WebElement> elements = driver.findElements(itemPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : elements) {
            prices.add(Double.parseDouble(e.getText().replace("$", "").trim()));
        }
        return prices;
    }

    @Step("Select sort option: {optionText}")
    // Select sort option
    public void selectSortOption(String optionText) {
        WebElement dropdown = driver.findElement(sortDropdown);
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }
    @Step("Click on cart icon")
    public void ClickOnCartIcon() {
        driver.findElement(CartIcon).click();
    }
    @Step("Check if on cart page")
    public boolean IsOnCartPage() {
        return driver.getCurrentUrl().equals("https://www.saucedemo.com/cart.html");
    }

    @Step("Add item from details page")
    public void AddItemFromDetailsPage() {

        driver.findElement(By.id("add-to-cart")).click();
    }

    @Step("Remove item from details page")
    public void RemoveItemFromDetailsPage() {
        driver.findElement(By.id("remove")).click();
    }
    @Step("Navigate back to products from details page")
    public void BackToProductsFromDetailsPage() {
        driver.findElement(Backtoproductbutton).click();
    }
}
