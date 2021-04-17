package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import businessModule.TestSetup;

public class  React_SignUpPage extends TestSetup {

	public React_SignUpPage(WebDriver driver) {
		React_SignUpPage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static WebElement input_UserName(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@placeholder='Username']"))));
	}
	
	public static WebElement input_Password(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@placeholder='Password']"))));
	}
	
	public static WebElement input_Email(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@placeholder='Email']"))));
	}
	
	public static WebElement button_SignIn(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[@type='submit']"))));
	}
}
