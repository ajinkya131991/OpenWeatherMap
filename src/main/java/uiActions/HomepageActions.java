package uiActions;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import testBase.TestBase;

public class HomepageActions extends TestBase
{
	public static final Logger log = Logger.getLogger(HomepageActions.class.getName());	

	//Locators
	@FindBy(xpath="//label[contains(text(),'Search')]/../input")
	WebElement searchTextBox;
	
	@FindBy(xpath="//label[contains(text(),'Search')]/../../button")
	WebElement searchButton;
	
	@FindBy(xpath="//*[@id=\"forecast_list_ul\"]/div")
	WebElement notFoundValidationMessage;
		
	@FindBy(xpath="//*[@id='forecast_list_ul']/table/tbody/tr[1]/td[2]/b/a")
	WebElement weatherLocation;
		
	public HomepageActions(WebDriver driver) throws IOException 
	{
		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);  
	}
	
	public void invalidCityName()
	{		
		searchTextBox.clear();
		searchTextBox.sendKeys(properties.getProperty("invalidCityName"));
		log.info("2. Enters an invalid city name");
		searchButton.click();
		waitForLoad(driver);
		log.info("3. Searches for the weather");
		String validationMessage = notFoundValidationMessage.getAttribute("innerText").toString();
		Assert.assertEquals(validationMessage.substring(1),properties.getProperty("alertValidationMessage"));
		log.info("4. Verifies that website suggests city is \"Not found\"");		
	}
	
	public void validCityName()
	{		
		searchTextBox.clear();
		searchTextBox.sendKeys(properties.getProperty("validCityName"));
		log.info("2. Enters an invalid city name");
		searchButton.click();
		waitForLoad(driver);
		log.info("3. Searches for the weather");
		
		waitForElement(driver, 5, weatherLocation);		
		Assert.assertEquals(weatherLocation.getText(), properties.getProperty("location"));
		log.info("4.Verifies that website successfully returns weather details for the city.");
	}
}
