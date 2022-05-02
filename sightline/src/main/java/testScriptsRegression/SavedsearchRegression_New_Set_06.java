package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
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
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedsearchRegression_New_Set_06 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	MiniDocListPage miniDocListPage;
	SearchTermReportPage searchTerm;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}


	@DataProvider(name = "UserAndSearchGroup")
	public Object[][] UserAndSearchGroup() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.mySavedSearch },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.mySavedSearch },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		saveSearch = new SavedSearch(driver);
		session = new SessionSearch(driver);
		softAssertion = new SoftAssert();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that While editing saved search which has conceptual
	 *              search, slider value(Precision Sensitivity) and Precision
	 *              Sensitivity text box value should display its actual value
	 *              (value set during the searches) [RPMXCON-63580]
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "AllTheUsers", groups = { "regression" }, priority = 1)
	public void verifyingPrecisionSensitivityValueWhileConfiguringWithrecisionSensitivityValueWhileEditing(
			String username, String password, String fullName) throws Exception {

		String conceptSearch = "search" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-63580 Saved Search");
		base.stepInfo(
				"Verify that While editing Saved serach which has conceptual search, slider value (precision sensitivity) and precision sensitivity text box value should display its actual value (Value set during the searches)");

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);

		// Create New Search Group
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// Conceptual Search
		session.navigateToAdvancedSearchPage();
		String precisionValueBefore = session.verifyingPrecisionValue(true, Input.conceptualSearchString1, true, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(conceptSearch, newNode);

		// selecting the savedSearch and click edit button
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		saveSearch.savesearch_Edit(conceptSearch);

		driver.waitForPageToBeReady();
		session.getModifyASearch().waitAndClick(10);
		String precisionValueAfterEdit = session.verifyingPrecisionValue(false, null, false, false);

		String passMsg = precisionValueBefore
				+ " is the Precision value Before  and After traverse from savedsaerch page is "
				+ precisionValueAfterEdit;
		String failMsg = "Precision value befor and after is not Same";
		base.textCompareEquals(precisionValueAfterEdit, precisionValueBefore, passMsg, failMsg);

		// performing the search and verifying the Search Result Displayed
		session.saveAndReturnPureHitCount();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Nexidia Upgrade - Validate editing/executing non-audio
	 *              searches on . an exising project with having audio documents
	 *              [RPMXCON-47154]
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "UserAndSearchGroup", groups = { "regression" }, priority = 2)
	public void validateEditingOrExecutingNonaudioSearches(String username, String password, String fullname,
			String rootGroup) throws Exception {
		String search1 = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();

		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullname);

		base.stepInfo("Test case Id: RPMXCON-47154");
		base.stepInfo(
				"Nexidia Upgrade - Validate editing/executing non-audio searches on an exising project with having audio documents");

		String newNode = saveSearch.createSearchGroupAndReturn(rootGroup, "", Input.yesButton);
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNodeUnderGroup(search2, newNode, rootGroup);
		
		base.selectproject();
		int purehit=session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(search1, rootGroup);

		saveSearch.selectSavedSearchTAb(search1, rootGroup, "Yes");
		saveSearch.getSavedSearchEditButton().waitAndClick(3);

		base.waitForElement(session.getNewSearchButton());
		session.verifySessionSearchPage();
		driver.waitForPageToBeReady();

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearchTAb(search1, rootGroup, "Yes");
		base.waitForElement(saveSearch.getCount(search2));
		String countBeforeExecuting = saveSearch.getCount(search2).getText();
		String countAfterExecuting=saveSearch.savedSearchExecute_Draft(search1, purehit);

		String passMSg = " Count Before And after execution is same : " + countAfterExecuting;
		String failMsg = " Count Before And after execution is not same : ";
		base.textCompareEquals(countAfterExecuting, countBeforeExecuting, passMSg, failMsg);

		saveSearch.selectNodeUnderSpecificSearchGroup(rootGroup, newNode);
		base.waitForElement(saveSearch.getCount(search2));
		countBeforeExecuting = saveSearch.getCount(search2).getText();
		saveSearch.performExecute();
		saveSearch.savedSearch_SearchandSelect(search2, "Yes");
		countAfterExecuting = saveSearch.getCount(search2).getText();

		base.textCompareEquals(countAfterExecuting, countBeforeExecuting, passMSg, failMsg);

		login.logout();

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				login.logoutWithoutAssert();
			} catch (Exception e) {
//							 TODO: handle exception
			}
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
			login.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}