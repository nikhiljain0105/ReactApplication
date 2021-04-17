package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import businessModule.TestSetup;

public class  React_SettingsPage extends TestSetup {

	public React_SettingsPage(WebDriver driver) {
		React_SettingsPage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static WebElement input_UrlProfilePic(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@placeholder='URL of profile picture']"))));
	}		
	
	public static WebElement button_UpdateSettings(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@type='submit']"))));
	}
}
