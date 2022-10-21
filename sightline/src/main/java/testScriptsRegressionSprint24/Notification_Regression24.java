package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Notification_Regression24 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	BatchPrintPage batch;

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
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
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

	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-53776
	 * @Description:To verify as an RMU user login, Start/End Date Sorting works correctly on My Background Tasks
	 **/
	@Test(description = "RPMXCON-53776", enabled = true, groups = { "regression" })
	public void verifyRMUStartDateinBGPage() throws Exception {
		base.stepInfo("RPMXCON - 53776");
		base.stepInfo("To verify as an RMU user login, Start/End Date Sorting works correctly "
				+ "on My Background Tasks");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1userName);
		
		base = new BaseClass(driver);
		base.selectproject(Input.largeVolDataProject);
		
		base.waitForElement(sessionSearch.getBullHornIcon());
        sessionSearch.getBullHornIcon().waitAndClick(20);
        
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		sessionSearch.SearchBtnAction();
		sessionSearch.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.advancedNewContentSearchNotPureHit("*");
		sessionSearch.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.advancedNewContentSearchNotPureHit("*");
	    sessionSearch.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.advancedNewContentSearchNotPureHit("*");
		sessionSearch.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		base.waitForElement(sessionSearch.getBullHornIcon());
		sessionSearch.getBullHornIcon().waitAndClick(20);
		base.waitForElement(sessionSearch.getViewAllBtn());
		sessionSearch.getViewAllBtn().waitAndClick(20);	
		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to BG Page");
		base.waitForElement(sessionSearch.getStartDateButton());
		sessionSearch.getStartDateButton().waitAndClick(3);
		driver.waitForPageToBeReady();
		List<String> startDate1 = base.availableListofElements(sessionSearch.getStartDatesInBG());
		List<String> startDate2 = base.availableListofElements(sessionSearch.getStartDatesInBG());
		base.verifyOriginalSortOrder(startDate1, startDate2, "Ascending", true);
		base.passedStep("verified - as an RMU user login, Start/End Date Sorting works correctly on"
				+ "My Background Tasks");
	    loginPage.logout();
	}
}
