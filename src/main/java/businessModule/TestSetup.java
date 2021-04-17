package businessModule;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utility.TestDataUtils;

public class TestSetup {

	public static LinkedHashMap<String, List<Map<String, String>>> testDataMap;
	public static Map<String, String> executionData = new HashMap<>();
	public static List<Map<String, String>> testDataSet;

	public static Map<String, String> ConfigtestDataRow = new HashMap<String, String>();
	public static String LogCommentTxt = "";
	public static WebDriver driver;
	public static String url;
	public static String defaultPath;

	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports report;
	public static ExtentTest test;

	public static Wait<WebDriver> wait;
	
	public static Wait<WebDriver> fluentwait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(100))
				.pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class, TimeoutException.class);
	}

	public static WebDriver getDriver() throws Exception {
		
		System.out.println("Path: " + System.getProperty("user.dir"));
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\Browser Drivers\\ChromeDriver.exe");

		defaultPath = System.getProperty("user.dir") + "\\testData\\";
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--disable-infobars");

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);

		options.setExperimentalOption("prefs", chromePrefs);

		driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		return driver;
	}

	@BeforeSuite
	public void getTestDataFromExcel() throws Exception 
	{
		testDataMap = TestDataUtils.getExceldata("TestData");
		ConfigtestDataRow = TestDataUtils.getConfigurationExceldata("Configuration");
	}

	@BeforeMethod
	public void getDriverInstance() throws Exception {
			driver = getDriver();
	}
	
	@BeforeMethod
	public void createExtentReport(ITestResult result) throws Exception {
		String methodname = result.getMethod().getMethodName();
		test = report.createTest(methodname);
	}

	public static String LogComment(WebElement we) {

		if (!(we.getAttribute("title") == "0" || we.getAttribute("title") == "" || we.getAttribute("title") == null
				|| we.getAttribute("title").isEmpty() == true)) {
			return we.getAttribute("title");
		}
		if (!(we.getAttribute("aria-label") == "0" || we.getAttribute("aria-label") == ""
				|| we.getAttribute("aria-label") == null || we.getAttribute("aria-label").isEmpty() == true)) {
			return we.getAttribute("aria-label");
		}
		if (!(we.getAttribute("innerText") == "0" || we.getAttribute("innerText") == ""
				|| we.getAttribute("innerText") == null || we.getAttribute("innerText").isEmpty() == true)) {
			return we.getAttribute("innerText");
		}
		if (!(we.getAttribute("text") == "0" || we.getAttribute("text") == "" || we.getAttribute("text") == null
				|| we.getAttribute("text").isEmpty() == true)) {
			return we.getAttribute("text");
		}
		if (!(we.getText() == "0" || we.getText() == "" || we.getText() == null || we.getText().isEmpty() == true)) {
			return we.getText();
		}
		return "";
	}

	@BeforeSuite
	public static void startTest() {
		
		String executionReportFolder ="ExecutionReport"+ new SimpleDateFormat("yyyyMMdd_hh.mm.ss").format(new Date());
		File outputFolder= new File(System.getProperty("user.dir")+"\\ExecutionReport");
		File file = new File(outputFolder+"\\"+executionReportFolder);
	    outputFolder.mkdir();
	    file.mkdir();
		htmlreporter = new ExtentHtmlReporter(file+"\\"+"ExtentReportResults.html");

		htmlreporter.config().setDocumentTitle("Automation Report");
		htmlreporter.config().setReportName("React Application Automation Report");
		htmlreporter.config().setTheme(Theme.DARK);

		report = new ExtentReports();
		report.attachReporter(htmlreporter);

		report.setSystemInfo("Host Name", "React Application Automation");
		report.setSystemInfo("Environment", TestSetup.url);
	}

	@AfterMethod
	public static void CloseBrowser() throws Exception {
		 driver.quit();
	}

	@AfterSuite
	public static void endTest() throws Exception {
		// report.endTest(test);
		report.flush();
		Thread.sleep(3000);
	}

	public static String screenCapture(WebDriver driver, String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + "_"
				+ dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@AfterMethod
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			// test.log(Status.FAIL, "Test Case Failed is "+result.getName());
			// test.log(Status.FAIL, "Test Case Failed is "+result.getThrowable());
			String methodname = result.getMethod().getMethodName();

			String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
			test.fail("<details>" + "<summary>" + "<b>" + "<font color =" + "red>" + "Exception Occured:Click to see"
					+ "</font>" + "</b>" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>"
					+ " \n");
			String failurelogg = "<b>" + "TEST CASE: - " + methodname.toUpperCase() + " FAILED" + "<b>";
			Markup m1 = MarkupHelper.createLabel(failurelogg, ExtentColor.RED);
			test.log(Status.FAIL, m1);
			String screenshotPath = screenCapture(driver, result.getName());
			// To add it in the extent report
			test.fail("Test Case Failed Snapshot is below " + test.addScreenCaptureFromPath(screenshotPath));

		} else if (result.getStatus() == ITestResult.SKIP) {
			// test.log(Status.SKIP, "Test Case Skipped is "+result.getName());
			String methodname = result.getMethod().getMethodName();
			String logtext = "<b>" + "TEST CASE: - " + methodname.toUpperCase() + " SKIPPED" + "<b>";
			Markup m = MarkupHelper.createLabel(logtext, ExtentColor.BLUE);
			test.skip(m);
		} else if (result.getStatus() == ITestResult.SUCCESS) {

			// test.log(Status.SKIP, "Test Case Passed is "+result.getName());
			String methodname = result.getMethod().getMethodName();
			String logtext = "<b>" + "TEST CASE: - " + methodname.toUpperCase() + " PASSED" + "<b>";
			Markup m = MarkupHelper.createLabel(logtext, ExtentColor.GREEN);
			test.pass(m);
		}

	}
}
