package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import automationLibrary.Element;
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
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName }
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

	/**
	 * @author Jeevitha
	 * @Description : To check that when user clicks on little arrow at \"Pre-Built
	 *              Models\" Search Group then the folder should get expanded with
	 *              its pre-created saved search groups/present sub-folder.
	 *              //additional inputs can be added based on future enhancements
	 */
	@Test(description = "RPMXCON-64862", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyFoldersUnderPreBuiltArrow(String username, String password, String fullname)
			throws InterruptedException {
		String dataSet[] = { Input.DEPIPTheft, Input.Discrimination, Input.FCPA,
				Input.Harassment, Input.NR_Detection ,Input.PII,Input.PRIV};
		List<String> actualList = new ArrayList<String>();

		base.stepInfo("Test case Id: RPMXCON-64862 - Saved Search");
		base.stepInfo(
				"To check that when user clicks on little arrow at \"Pre-Built Models\" Search Group then the folder should get expanded with its pre-created saved search groups/present sub-folder.");

		// Login as user
		login.loginToSightLine(username, password);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// get the available tabs under Pre-built model and verify the list
		actualList = base.availableListofElements(saveSearch.getListOfGroupsUnderTab());

		String passMsg = actualList + " : is avialable tabs under Pre-Built";
		String failMsg = actualList + " : is not present";
		base.compareArraywithDataList(dataSet, actualList, true, passMsg, failMsg);

		base.passedStep("Pre-Built Models Selected And little Arrow is CLicked");

		// Logout Application
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that when users select \"Pre-Built Models\" Search
	 *              Group then \"Execute\" button from action panel is disable.
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64864", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyexcuteBtnForPReBuilt(String username, String password, String fullname)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-64864 - Saved Search");
		base.stepInfo(
				"Verify that when users select \"Pre-Built Models\" Search Group then \"Execute\" button from action panel is disable.");

		// Login as user
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + fullname);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group and arrow Clickable
		saveSearch.rootGroupExpansion();
		base.passedStep("Pre-Built Models Selected And Arrow is CLicked");
		Element executeBtnStatus = saveSearch.getSavedSearchExecuteButton();
		saveSearch.checkButtonDisabled(executeBtnStatus, "Should be disabled", "Execute");

		// Logout Application
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that when users select other pre-created saved search
	 *              groups/models/sub-folder from "Pre-Built Models" Search Group
	 *              then "Execute" button from action panel is enable and can
	 *              perform execution.
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64865", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void executeFolderUnderPreBuilt(String username, String password, String fullname)
			throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		String dataSet[] = { Input.DEPIPTheft, Input.Discrimination, Input.FCPA,
				Input.Harassment, Input.NR_Detection ,Input.PII,Input.PRIV};
		List<String> actualList = new ArrayList<String>();

		base.stepInfo("Test case Id: RPMXCON-64865 - Saved Search");
		base.stepInfo(
				"Verify that when users select other pre-created saved search groups/models/sub-folder from \"Pre-Built Models\" Search Group then \"Execute\" button from action panel is enable and can perform execution.");

		// Login as user
		login.loginToSightLine(username, password);

		// configure query
		session.basicContentSearch(Input.searchString5);
		session.saveSearchInPreBuiltModels(searchName, null, Input.DEPIPTheft, Input.shareSearchDefaultSG, false, true);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// get the available tabs under Pre-built model and verify the list
		actualList = base.availableListofElements(saveSearch.getListOfGroupsUnderTab());

		String passMsg = actualList + " : is avialable tabs under Pre-Built";
		String failMsg = actualList + " : is not present";
		base.compareArraywithDataList(dataSet, actualList, true, passMsg, failMsg);

		base.passedStep("Pre-Built Models Selected And little Arrow is CLicked");

		// execute folder under pre-built model
		saveSearch.getSharedGroupName(Input.DEPIPTheft).waitAndClick(10);
		base.stepInfo("Clicked : " + Input.DEPIPTheft);

		base.waitForElement(saveSearch.getSavedSearchExecuteButton());
		Element executeBtnStatus = saveSearch.getSavedSearchExecuteButton();
		saveSearch.checkButtonEnabled(executeBtnStatus, "Should be Enabled", "Execute");
		saveSearch.performExecute();

		// verify bull horn and click view all btn
		base.clickButton(base.getBullHornIcon());
		base.getBckTask_SelectAll().waitAndClick(10);
		base.stepInfo("Clicked View all Button");

		// Logout Application
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To check that we have Helper text icon present at "Pre-Built
	 *              Models" Search Group (UI).
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-64874", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyHelpIconForPreBuilt(String username, String password, String fullname)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-64874 - Saved Search");
		base.stepInfo("To check that we have Helper text icon present at \"Pre-Built Models\" Search Group (UI).");

		// Login as RMU user
		base.stepInfo("Login to sightline and Select Project**");
		login.loginToSightLine(username, password);

		// Navigate to Saved Search page
		saveSearch.navigateToSavedSearchPage();
		base.stepInfo(" Navigate to search- saved search");
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate help icon presence
		base.ValidateElement_Presence(saveSearch.getPreBuiltHelpIcon(), Input.preBuilt + " Help Icon");

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
