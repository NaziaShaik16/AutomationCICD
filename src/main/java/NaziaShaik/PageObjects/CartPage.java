package NaziaShaik.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import NaziaShaik.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	
WebDriver driver;
    
    public CartPage(WebDriver driver)
    {
    	     super(driver);
    	     this.driver=driver;
    	     PageFactory.initElements(driver, this);
    }
    
    @FindBy(css=".totalRow button")
    WebElement checkoutEle;
    
    @FindBy(css=".cartSection h3")
    List<WebElement> productTitles;
    
    public boolean VerifyProductDisplay(String productName)
    {
    	
    	boolean match = productTitles.stream()
                .anyMatch(product -> product.getText().equalsIgnoreCase(productName));
       	return match;
    }
    
    public CheckoutPage goToCheckout()
    {
    	   
    	   checkoutEle.click();
    	   return new CheckoutPage(driver);
    	
    }

   
}
