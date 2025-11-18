package pages;

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
    public void AddOneToCart ()
    {
        driver.findElement(Addbackpack).click();
    }

    public void goToCart()
    {
        driver.findElement(CartIcon).click();
    }
    //function to check if we are in product page
    public boolean isInProductPage (String expectedUrl)
    {
        return driver.getCurrentUrl().equals(expectedUrl);
    }
    //function to get number of items in cart
    public String getNumberOfItemsInCart()
    {
        return driver.findElement(CartIcon).getText();
    }

    //function to check loaded page
    public boolean isPageLoaded()
    {
        return driver.findElement(Addbackpack).isDisplayed();
    }


//function to check for product is visible
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

    //Function to remove item from cart
    public  void RemoveItemFromCart ()
    {

        driver.findElement(RemoveButton).click();
    }

    public void ViewProductItemDetails() {
        driver.findElement(Details).click();
    }

    public boolean isProductItemDetailsDisplayed() {

        return driver.findElement(ProductDetails).isDisplayed();
    }

    public void Sorting(String value) {

        new Select(driver.findElement(SortDropdown)).selectByValue(value);

    }

    public boolean isSortedAscending(List<? extends Comparable> list) {
        List<Comparable> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        return sorted.equals(list);
    }
    public List<String> getProductNames() {
        List<WebElement> elements = driver.findElements(itemNames);
        List<String> names = new ArrayList<>();
        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }
        return names;
    }
    public boolean isSortedDescending(List<? extends Comparable> list) {
        List<Comparable> sorted = new ArrayList<>(list);
        Collections.sort(sorted, Collections.reverseOrder());
        return sorted.equals(list);
    }
    // Get all product prices as list (numeric values)
    public List<Double> getProductPrices() {
        List<WebElement> elements = driver.findElements(itemPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement e : elements) {
            prices.add(Double.parseDouble(e.getText().replace("$", "").trim()));
        }
        return prices;
    }
    // Select sort option
    public void selectSortOption(String optionText) {
        WebElement dropdown = driver.findElement(sortDropdown);
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
    }

    public void ClickOnCartIcon() {
        driver.findElement(CartIcon).click();
    }

    public boolean IsOnCartPage() {
        return driver.getCurrentUrl().equals("https://www.saucedemo.com/cart.html");
    }

    public void AddItemFromDetailsPage() {

        driver.findElement(By.id("add-to-cart")).click();
    }

    public void RemoveItemFromDetailsPage() {
        driver.findElement(By.id("remove")).click();
    }

    public void BackToProductsFromDetailsPage() {
        driver.findElement(Backtoproductbutton).click();
    }
}
