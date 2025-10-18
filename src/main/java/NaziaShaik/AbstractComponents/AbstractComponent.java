package NaziaShaik.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import NaziaShaik.PageObjects.CartPage;
import NaziaShaik.PageObjects.OrderPage;

public class AbstractComponent {

    WebDriver driver;

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;

    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }
    
    public void waitForWebElementToAppear(WebElement findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }
    
    public CartPage goToCartPage() {
        try {
            Thread.sleep(3000); // wait 3 seconds for overlay/spinner to disappear
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cartHeader.click();
        CartPage cartpage = new CartPage(driver);
        return cartpage;
    }

    
    public OrderPage goToOrdersPage() throws InterruptedException
    {
        Thread.sleep(3000); // wait for spinner to disappear
        orderHeader.click();
        OrderPage orderpage = new OrderPage(driver);
        return orderpage;
    }


    

    public void waitForElementToDisappear(WebElement ele) throws InterruptedException {
        Thread.sleep(1000);
        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // wait.until(ExpectedConditions.invisibilityOf(ele));
    }
}
