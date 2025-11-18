package tests;
import Base.BaseLogin;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductPage;


public class AddtoCartTest extends BaseLogin {

    //verify products shows in product page is visiable test case
    @Test
    public void DisplayedProductPageItemsTC() {
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.IsProductVisible());
    }

    //add one product to cart test cases
    @Test
    public void AddOneToCartTc() {
        ProductPage productPage = new ProductPage(driver);
        productPage.AddOneToCart();
        Assert.assertEquals(productPage.getNumberOfItemsInCart(), "1");
    }
    //add multiple products to cart test case
    @Test
    public void addProductsToCartTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductsToCart();
        Assert.assertEquals(productPage.getNumberOfItemsInCart(), "3");
    }

    //remove item from cart test case
    @Test
    public void RemoveItemFromCartTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductsToCart();
        productPage.RemoveItemFromCart();
        Assert.assertEquals(productPage.getNumberOfItemsInCart(), "2");
    }

//Product item show all details test case
    @Test
    public void ProductItemDetailsTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.ViewProductItemDetails();
        Assert.assertTrue(productPage.isProductItemDetailsDisplayed());
        driver.navigate().back();
    }
//Verify sorting A to Z test case
    @Test
    public void SortAToZTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.Sorting("az");
        Assert.assertTrue(   productPage.isSortedAscending(productPage.getProductNames()));
    }

    //Verify sorting Z to A test case
   @Test
    public void SortZToATC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.Sorting("za");
        Assert.assertTrue(
                productPage.isSortedDescending(productPage.getProductNames())
        );
    }

    //sorting from low to high in prices
    @Test
    public void SortByPriceLowToHigh() {
        ProductPage productPage = new ProductPage(driver);
        productPage.selectSortOption("Price (low to high)");

        Assert.assertTrue(
                productPage.isSortedAscending(productPage.getProductPrices()),
                "Products are not sorted by price low → high"
        );
    }
//sorting from high to low in prices
    @Test
    public void SortByPriceHighToLow() {
        ProductPage productPage = new ProductPage(driver);
        productPage.selectSortOption("Price (high to low)");

        Assert.assertTrue(
                productPage.isSortedDescending(productPage.getProductPrices()),
                "Products are not sorted by price high → low"
        );
    }
    //verify icon cart is clickable and naviagte to cart page
    @Test
    public void CartIconClickableTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.ClickOnCartIcon();
        Assert.assertTrue(productPage.IsOnCartPage());
    }
//verify Add product from details page
    @Test
    public void AddProductFromDetailsPageTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.ViewProductItemDetails();
        productPage.AddItemFromDetailsPage();
        Assert.assertEquals(productPage.getNumberOfItemsInCart(), "1");
    }
    //verify Remove product from details page
    @Test
    public void RemoveProductFromDetailsPageTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.ViewProductItemDetails();

        productPage.AddItemFromDetailsPage();
        productPage.RemoveItemFromDetailsPage();
        Assert.assertNotEquals(productPage.getNumberOfItemsInCart(),"1");
    }

    //verfiy back to products button from details page
    @Test
    public void BackToProductsFromDetailsPageTC() {
        ProductPage productPage = new ProductPage(driver);
        productPage.ViewProductItemDetails();
        productPage.BackToProductsFromDetailsPage();
        Assert.assertTrue(productPage.IsProductVisible());
    }
}
