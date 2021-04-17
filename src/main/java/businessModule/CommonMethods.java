package businessModule;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;

public class CommonMethods extends TestSetup {
	
	public Map<String, String> testDataRow = new HashMap<String, String>();

	public static void ClickObject(WebElement we) //method for clicking on an element
	{
		try 
		{
			LogCommentTxt = LogComment(we);
			we.click();
			test.log(Status.PASS, "Successfully Clicked on Object : " + LogCommentTxt);
		} 
		catch (Exception e) 
		{
			// test.log(Status.FAIL, "Fail to Click on Object : " + LogCommentTxt);
		}
	}
	public static void EnterText(WebElement we, String value) //method for entering text in an element
	{
		try 
		{
			LogCommentTxt = LogComment(we);
			we.sendKeys(value);
			test.log(Status.PASS, "Successfully Entered Text : " + value + " In Field : " + LogCommentTxt);
		} 
		catch (Exception e)
		{
			// test.log(Status.FAIL, "Fail to Enter Text : " + value + " In Field : " +
			// LogCommentTxt);
		}
	}
}

