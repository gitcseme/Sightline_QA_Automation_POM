package testScriptsRegressionSprint16;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression2 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
//				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
//				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } 
				};
		return users;
	}

	/**
	 * @author Jeevitha
	 * @Description : To check that we have a new default Search Group added called
	 *              \"Pre-Built Models\" Search Group under \"Shared with Default
	 *              Security Group\".
	 */
	@Test(description = "RPMXCON-64860", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyPreBuiltUnderDefaultSG(String username, String password, String fullname)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-64860 - Saved Search");
		base.stepInfo(
				"To check that we have a new default Search Group added called \"Pre-Built Models\" Search Group under \"Shared with Default Security Group\".");

		// Login as user
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + fullname);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), "Pre-Built Models");

		// Logout Application
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : To check that we have a little arrow at "Pre-Built Models"
	 *              Search Group and its clickable .
	 */
	@Test(description = "RPMXCON-64861", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyPreBuiltArrowUnderDefaultSG(String username, String password, String fullname)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-64861 - Saved Search");
		base.stepInfo("To check that we have a little arrow at \"Pre-Built Models\" Search Group and its clickable .");

		// Login as user
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + fullname);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group and arrow Clickable
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), "Pre-Built Models");
		saveSearch.rootGroupExpansion();
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.DEPIPTheft), "DEP Ip Theft");
		base.passedStep("Pre-Built Models Selected And Arrow is CLicked");

		// Logout Application
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @Date: 07/05/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To check that when users mouse hover on Helper text icon
	 *              present at \"Pre-Built Models\" Search Group then an information
	 *              pop-up should get opened
	 */
	@Test(description = "RPMXCON-64875", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyAddingSGunderMysavedSearchAndDefaultSGPreBuilt(String username, String password, String fullname)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-64875 - Saved Search");
		base.stepInfo(
				"To check that when users mouse hover on Helper text icon present at \"Pre-Built Models\" Search Group then an information pop-up should get opened");
		String expectedhelpText = Input.preBuiltHelpText;

		// Login as RMU user
		base.stepInfo("Login to sightline and Select Project**");
		login.loginToSightLine(username, password);

		// Navigate to Saved Search page
		base.stepInfo(" Navigate to search- saved search");
		saveSearch.navigateToSavedSearchPage();
		base.stepInfo("Expected helpText message : " + expectedhelpText);
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// Help Icon and text verification
		saveSearch.getPreBuiltHelpIcon().waitAndClick(3);
		driver.waitForPageToBeReady();
		base.stepInfo(" To verify the tool tip with the disclaimer text ");
		String actualHelpText = saveSearch.getPreBuiltHelpTextContent().getText();
		base.stepInfo("Actual helpText message : " + actualHelpText);
		base.textCompareEquals(actualHelpText, expectedhelpText, "Disclaimer text message displayed as expected",
				"Disclaimer text message displayed not as expected");

		// Logout Application
		login.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
