package NaziaShaik.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import NaziaShaik.PageObjects.CartPage;
import NaziaShaik.PageObjects.CheckoutPage;
import NaziaShaik.PageObjects.ConfirmationPage;
import NaziaShaik.PageObjects.LandingPage;
import NaziaShaik.PageObjects.OrderPage;
import NaziaShaik.PageObjects.ProductCatalogue;
import NaziaShaik.TestComponents.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {

    String productName = "ZARA COAT 3";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

        // Login to the application
        ProductCatalogue productcatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));

        // Get the list of products
        List<WebElement> products = productcatalogue.getProductList();

        // Add product to cart
        productcatalogue.addProductToCart(input.get("product"));

        // Navigate to cart page
        CartPage cartpage = productcatalogue.goToCartPage();

        // Verify product is displayed in cart
        Boolean match = cartpage.VerifyProductDisplay(input.get("product"));
        Assert.assertTrue(match);

        // Proceed to checkout
        CheckoutPage checkoutpage = cartpage.goToCheckout();

        // Select country
        checkoutpage.selectCountry("india");

        // Submit order and verify confirmation
        ConfirmationPage confirmationpage = checkoutpage.submitOrder();
        String confirmMessage = confirmationpage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        System.out.println("Order placed successfully: " + confirmMessage);
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest() throws InterruptedException {

        // Login to check order history
        ProductCatalogue productcatalogue = landingPage.loginApplication("naziashaik888@gmail.com", "Shinchan@786");

        // Navigate to orders page
        OrderPage orderpage = productcatalogue.goToOrdersPage();

        // Verify product is in order history
        Assert.assertTrue(orderpage.VerifyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        // Read data from JSON file
        List<HashMap<String, String>> data = getJsonDataToMap(
                System.getProperty("user.dir") + "\\src\\test\\java\\NaziaShaik\\data\\PurchaseOrder.json");

        return new Object[][] {{data.get(0)}, {data.get(1)}};
    }

    /*
    @DataProvider
    public Object[][] getData() {
        return new Object[][] {
            {"naziashaik888@gmail.com", "Shinchan@786", "ZARA COAT 3"},
            {"shanwaz123@gmail.com", "Doreman@786", "ADIDAS ORIGINAL"}
        };
    }
    */

    /*
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("email", "naziashaik888@gmail.com");
    map.put("password", "Shinchan@786");
    map.put("product", "ZARA COAT 3");

    HashMap<String, String> map1 = new HashMap<String, String>();
    map1.put("email", "shanwaz123@gmail.com");
    map1.put("password", "Doreman@786");
    map1.put("product", "ADIDAS ORIGINAL");
    */
}
