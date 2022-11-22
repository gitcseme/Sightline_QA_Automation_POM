package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Bulkactions_Regression26 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	String tagname;
	String foldername;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		baseClass = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());

		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}	
	
	/**
	 * @author NA Testcase No:RPMXCON-54487
	 * @Description:verify that in Bulk Assign modal should not display 'InProgress' warning message
	 **/
	@Test(description = "RPMXCON-54487", enabled = true, groups = { "regression" })
	public void verifyBulkAssignModelWarning() throws Exception {
		
		baseClass.stepInfo("RPMXCON-54487");
		baseClass.stepInfo("To verify that in Bulk Assign modal should not display 'InProgress' warning message");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		
		sessionSearch.navigateToSessionSearchPageURL();	
		sessionSearch.basicContentSearch(Input.testData1);		
		sessionSearch.bulkAssign();
		
		if (!baseClass.getWarningsMsgHeader().isElementAvailable(1)) {
			baseClass.passedStep("Warning Not Occuered When Total Count in INPROGRESS As Expected");
		} else {
			baseClass.failedStep(baseClass.getWarningMsg().getText() + " is occurred");
		}
		baseClass.passedStep("Verified - that in Bulk Assign modal should not display 'InProgress' warning message");
		loginPage.logout();
	}
}
