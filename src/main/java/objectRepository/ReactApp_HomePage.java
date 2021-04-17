package objectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import businessModule.TestSetup;

public class  ReactApp_HomePage extends TestSetup {

	public ReactApp_HomePage(WebDriver driver) {
		ReactApp_HomePage.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public static WebElement a_SignUp(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(@href,'register')]"))));
	}
	public static WebElement a_Settings(WebDriver driver) 
	{
		return fluentwait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[contains(@href,'settings')]"))));
	}
}
