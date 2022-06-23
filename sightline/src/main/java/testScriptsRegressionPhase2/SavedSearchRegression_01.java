package testScriptsRegressionPhase2;

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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression_01 {

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
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @Author
	 * @Description : To verify as a Reviewer user login, I will be able to search a
	 *              saved query based on search status 'COMPLETED' under Shared
	 *              folder
	 */
	@Test(description = "RPMXCON-47571", enabled = true, groups = { "regression" })
	public void verifyingAsReviewerApplyingCompleteStatus() {

		base.stepInfo("Test case Id: RPMXCON-47571 Saved search");
		base.stepInfo(
				"To verify as a Reviewer user login, I will be able to search a saved query based on search status 'COMPLETED' under Shared folder");

		// login as reviewer
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("logged in as : " + Input.rev1FullName);

		// selecting default security group and applying complete status
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.CheckStatus("COMPLETED");

		// logout
		login.logout();
	}

	/**
	 * @Author
	 * @Description : To verify , I will be able to search a saved query based on
	 *              search name in 'Shared with <Security Group>' search group
	 */
	@Test(description = "RPMXCON-47572", dataProvider = "SavedSearchwithUsers", enabled = true, groups = {
			"regression" })
	public void verifyUserAbleToSearchSavedQueryUnderSpecificSearchGroup(String username, String password,
			String fullName) throws InterruptedException {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-47572 Saved search");
		base.stepInfo(
				"To verify , I will be able to search a saved query based on search name in 'Shared with <Security Group>' search group");

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("logged in as : " + fullName);

		// performing basic Content Search and Saving the Search
		session.basicContentSearch(Input.searchString2);
		session.saveSearchAtAnyRootGroup(searchName, Input.shareSearchDefaultSG);

		// performing search in saved search page to get the savedSearch saved under
		// specific search group
		saveSearch.selectSavedSearchTAb(searchName, Input.shareSearchDefaultSG, "No");
		softAssertion.assertEquals(saveSearch.getSearchName(searchName).isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Saved Search is displayed for Searched Name");

		// logout
		login.logout();
	}

	/**
	 * @Author
	 * @Description : RMU User - Verify that appropriate search Group appears under
	 *              the respective security Group on Saved Search Screen.
	 */
	@Test(description = "RPMXCON-48121", enabled = true, groups = { "regression" })
	public void verifyThatAppropriateSGAppearsUnderRespectiveSecurityGroup() throws Exception {
		UserManagement userManagement = new UserManagement(driver);
		SecurityGroupsPage securityGroupsPage = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48121 Saved search");
		base.stepInfo(
				"RMU User - Verify that appropriate search Group appears under the respective security Group on Saved Search Screen.");

		// login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa2FullName);

		// Create security group
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroup);

		// access to security group to RMU
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		// logOut
		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in as : " + Input.rmu2FullName);

		// creating new search group
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// selecting created security group and verifying the presence of newly created
		// search group
		base.selectsecuritygroup(securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, newNode);
		softAssertion.assertEquals(saveSearch.getSavedSearchGroupName(newNode).isElementAvailable(5), false);

		// selecting default security group and verifying the presence of newly created
		// search group
		base.selectsecuritygroup(Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, newNode);
		softAssertion.assertEquals(saveSearch.getSavedSearchGroupName(newNode).isElementAvailable(5), true);
		softAssertion.assertAll();

		// deleting search group
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// logout
		login.logout();

		// login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa2FullName);

		// deleting the newly created Security group
		securityGroupsPage.deleteSecurityGroups(securityGroup);

		// logOut
		login.logout();

	}

	/**
	 * @Author
	 * @Description : Verify that correct number of documents appears when user
	 *              Selects \"View In DocView\" action from Advanced Search Screen
	 */
	@Test(description = "RPMXCON-57091", enabled = true, groups = { "regression" })
	public void verifyingNumbersOfDocumentsInDocViewWithPureHitCount() throws InterruptedException {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57091 Saved search");
		base.stepInfo(
				"Verify that correct number of documents appears when user Selects \"View In DocView\" action from Advanced Search Screen");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// performing basic Content Search and Saving the Search
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		// selecting the saved Search and clicking Edit button
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearch(searchName);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		base.stepInfo("Edit button clicked in SavedSaerch Page");

		// getting the pureHit count
		int pureHit = Integer.parseInt(session.getPureHitsCount().getText());

		// getting the Document count in DocView page
		session.ViewInDocView();
		int docCount = docView.verifyingDocCount();

		// comparing the pureHit with Document count
		softAssertion.assertEquals(pureHit, docCount);
		softAssertion.assertAll();
		base.passedStep("pure Hit Count equals to Document Count in DocView Page");

		// logOut
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
