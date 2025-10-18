package NaziaShaik.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import NaziaShaik.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// WebElement useremail=
	// driver.findElement(By.id("userEmail")).sendKeys(username);

	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement passwordEle;

	@FindBy(id = "login")
	WebElement submit;

	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;

	public ProductCatalogue loginApplication(String email, String password) throws InterruptedException {
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);

		Thread.sleep(2000); // ✅ wait for 2 seconds to ensure button is clickable

		submit.click();

		ProductCatalogue productcatalogue = new ProductCatalogue(driver);
		return productcatalogue;
	}

	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client/");
	}

}
