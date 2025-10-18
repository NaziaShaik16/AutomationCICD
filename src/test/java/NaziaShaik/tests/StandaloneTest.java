package NaziaShaik.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneTest {

    public static void main(String[] args) {

        String Productname = "ZARA COAT 3";
        String username = "naziashaik888@gmail.com";
        String password = "Shinchan@786";
        String country = "India";

        // Setup ChromeDriver with options to reduce logging warnings
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        
      //  LandingPage landpage=new LandingPage(driver);

        driver.get("https://rahulshettyacademy.com/client/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ----------- LOGIN -----------
        driver.findElement(By.id("userEmail")).sendKeys(username);
        driver.findElement(By.id("userPassword")).sendKeys(password);
        driver.findElement(By.xpath("//input[@value='Login']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        // ----------- SELECT PRODUCT -----------
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = products.stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(Productname))
                .findFirst().orElse(null);

        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        // Wait for toast and animation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        // ----------- GO TO CART -----------
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        // Verify product in cart
        List<WebElement> cartProducts = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
        boolean match = cartProducts.stream()
                .anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(Productname));
        Assert.assertTrue(match);

        // ----------- CHECKOUT -----------
        driver.findElement(By.cssSelector(".totalRow button")).click();

        // ----------- SELECT COUNTRY -----------
        driver.findElement(By.xpath("//input[@placeholder='Select Country']")).sendKeys(country);

        // Wait until suggestions appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-item")));

        // Get all suggestions and select the correct country
        List<WebElement> options1 = driver.findElements(By.cssSelector(".ta-item"));
        for (WebElement value : options1) {
            if (value.getText().equalsIgnoreCase(country)) {
                value.click();
                break;
            }
        }

        // Wait for backdrop overlay to disappear
      //  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ta-backdrop")));

        // ----------- PLACE ORDER -----------
        driver.findElement(By.cssSelector(".btnn")).click();

        // ----------- VERIFY ORDER SUCCESS -----------
    //    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".hero-primary")));
        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

        System.out.println("Order placed successfully: " + confirmMessage);

        driver.quit();
    }
}
