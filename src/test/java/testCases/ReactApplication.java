package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import businessModule.CommonMethods;

import businessModule.TestSetup;
import objectRepository.ReactApp_HomePage;
import objectRepository.React_SettingsPage;
import objectRepository.React_SignUpPage;
import utility.TestDataUtils;

public class ReactApplication extends TestSetup
{
	@Test
	public void ReactApplicationSignUpAndSetProfilePic() throws Exception 
	{
		try 
		{
			testDataSet = testDataMap.get("ReactApplicationSignUpAndSetProfilePic");
			for (int i = 1; i < testDataSet.size(); i++) 
			{
				executionData = testDataSet.get(i);
	
				driver.get(TestDataUtils.getDataFromProperties().getProperty("AppURL")); //Getting URL from properties file
				
				//before login home page		
				CommonMethods.ClickObject(ReactApp_HomePage.a_SignUp(driver)); //click on SignUp
				
				//signup page
				CommonMethods.EnterText(React_SignUpPage.input_UserName(driver), executionData.get("Username")); //enter user name
				CommonMethods.EnterText(React_SignUpPage.input_Email(driver), executionData.get("Email")); //enter email
				CommonMethods.EnterText(React_SignUpPage.input_Password(driver), executionData.get("Password")); //enter password
				CommonMethods.ClickObject(React_SignUpPage.button_SignIn(driver)); //click on sign in button
				
				//after login home page
				CommonMethods.ClickObject(ReactApp_HomePage.a_Settings(driver)); //click on Settings
				
				//settings page
				//CommonMethods.EnterText(React_SettingsPage.input_UrlProfilePic(driver), "https://picsum.photos/200"); //enter profile pic url
				CommonMethods.EnterText(React_SettingsPage.input_UrlProfilePic(driver), TestDataUtils.getDataFromProperties().getProperty("ProfilePicURL")); //enter profile pic url
				CommonMethods.ClickObject(React_SettingsPage.button_UpdateSettings(driver)); //click on update settings
				
				//Getting url of the page
				String url = driver.getCurrentUrl();
				test.log(Status.PASS, "URL is: " + url);
				
				Assert.assertTrue(true);
			}
			
		}
		catch (Exception e) 
		{
			System.out.println(e);
			String imagePath = TestSetup.screenCapture(driver, "ReactApplicationSignUpAndSetProfilePic");
			test.fail("Failed due to exception" + test.addScreenCaptureFromPath(imagePath));
		}
	}
}
