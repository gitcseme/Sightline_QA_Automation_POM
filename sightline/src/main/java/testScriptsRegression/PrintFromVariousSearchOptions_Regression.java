package testScriptsRegression;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class PrintFromVariousSearchOptions_Regression {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	
	String namesg1 = "Default_1234" + Utility.dynamicNameAppender();
	String namesg2 = "Default_5678" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		
		/**
		 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Test Case
		 * Test Case Id: RPMXCON-52233
		 * Module: DocView - Sprint 2
		 * Printing of Document as PA from various search options
		 */
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);

		// Login as a RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	
	@Test(groups={"regression"},priority=0)
	public void printRedactedDocsAsPA() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52233- DocView- Sprint 2");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
// printing from session search
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.printIcon().waitAndClick(20);	
		  baseClass.VerifySuccessMessage("Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
			baseClass.stepInfo("Success message has been verified");
	    	driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.bullhornIconRedColour().Visible() && docViewRedact.bullhornIconRedColour().Enabled();
				}
			}), Input.wait30);
			docViewRedact.bullhornIconRedColour().waitAndClick(20);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.viewAllBtn().Visible() && docViewRedact.viewAllBtn().Enabled();
				}
			}), Input.wait30);
			docViewRedact.viewAllBtn().waitAndClick(15);
			// Thread sleep added for the session to move to next page to extract url		
			Thread.sleep(4000);
			String urlBackgroundfromdocview = driver.getUrl();
			assertEquals(urlBackgroundfromdocview, "https://sightlinept.consilio.com/Background/BackgroundTask");
			baseClass.passedStep("Navigated to document download page");
	    	
// printing from Doclist 
		    baseClass.stepInfo("Printing Document from Doc List as PA");
			wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.gotoSearchTab().getWebElement()));
			actions.moveToElement(docViewRedact.gotoSearchTab().getWebElement());
			actions.click().build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.gotoSessionSearch().getWebElement()));
			actions.moveToElement(docViewRedact.gotoSessionSearch().getWebElement());
			actions.click().build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.actionsDropDown().getWebElement()));
			docViewRedact.actionsDropDown().getWebElement().click();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
	        docViewRedact.clickOnPreviewDocumentButton();
	        driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
				}
			}), Input.wait30);
			docViewRedact.printIcon().waitAndClick(10);      
	        baseClass.VerifySuccessMessage("Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
	        docViewRedact.clickOnClosePreviewButton();
	    	baseClass.stepInfo("Success message has been verified");
	    	driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.bullhornIconRedColour().Visible() && docViewRedact.bullhornIconRedColour().Enabled();
				}
			}), Input.wait30);
			docViewRedact.bullhornIconRedColour().waitAndClick(20);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.viewAllBtn().Visible() && docViewRedact.viewAllBtn().Enabled();
				}
			}), Input.wait30);
			docViewRedact.viewAllBtn().waitAndClick(10);
		// Thread sleep added for the session to move to next page to extract url
			Thread.sleep(4000);
			String urlBackgroundfromdoclist = driver.getUrl();
			assertEquals(urlBackgroundfromdoclist, "https://sightlinept.consilio.com/en-us/Background/BackgroundTask");
			baseClass.passedStep("Navigated to document download page");
				        
// printing from DocExplorer	 
	       baseClass.stepInfo("#### Go to Doc Explorer and Press Print Icon  ####");
	        docViewRedact.selectSideMenu("Doc Explorer");
	        docViewRedact.enterDocId(Input.searchDocId);
	        docViewRedact.clickOnPreviewEyeIcon();
	        driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
				}
			}), Input.wait30);
			docViewRedact.printIcon().waitAndClick(25);
	        baseClass.VerifySuccessMessage("Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
	        docViewRedact.clickOnClosePreviewButton();
	    	baseClass.stepInfo("Success message has been verified");
	    	driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.bullhornIconRedColour().Visible() && docViewRedact.bullhornIconRedColour().Enabled();
				}
			}), Input.wait30);
			docViewRedact.bullhornIconRedColour().waitAndClick(20);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.viewAllBtn().Visible() && docViewRedact.viewAllBtn().Enabled();
				}
			}), Input.wait30);
			docViewRedact.viewAllBtn().waitAndClick(15);
			// Thread sleep added for the session to move to next page to extract url		
			Thread.sleep(4000);
			String urlBackgroundfromdocexplorer= driver.getUrl();
			assertEquals(urlBackgroundfromdocexplorer, "https://sightlinept.consilio.com/Background/BackgroundTask");
			baseClass.passedStep("Navigated to document download page");
}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		if(ITestResult.FAILURE==result.getStatus()){
	   		Utility bc = new Utility(driver);
	   		bc.screenShot(result);
	   	}
		try {
			loginPage.closeBrowser();
		} finally {
			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV RPMXCON 52233******");
	}

}
