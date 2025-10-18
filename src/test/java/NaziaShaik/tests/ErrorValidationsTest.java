package NaziaShaik.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import NaziaShaik.PageObjects.CartPage;
import NaziaShaik.PageObjects.CheckoutPage;
import NaziaShaik.PageObjects.ConfirmationPage;
import NaziaShaik.PageObjects.ProductCatalogue;
import NaziaShaik.TestComponents.BaseTest;
import NaziaShaik.TestComponents.Retry;

public class ErrorValidationsTest extends BaseTest {

	
	 @Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	   public void LoginErrorValidation() throws IOException, InterruptedException
	   {

	        String productName = "ZARA COAT 3";
	        landingPage.loginApplication("naziashaik33743872@gmail.com", "Shinchan@1234567");
	        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	   }
	
	

	 @Test
	   public void ProductErrorValidation() throws IOException, InterruptedException
	   {

	        String productName = "ZARA COAT 3";
	        ProductCatalogue productcatalogue = landingPage.loginApplication("shanwaz123@gmail.com", "Doreman@786");
	        List<WebElement> products = productcatalogue.getProductList();
	        productcatalogue.addProductToCart(productName);
	        CartPage cartpage =  productcatalogue.goToCartPage();
	        
	        Boolean match = cartpage.VerifyProductDisplay("ZARA COAT 333");
	        Assert.assertFalse(match);
	       
	        
	   
	    }
}
