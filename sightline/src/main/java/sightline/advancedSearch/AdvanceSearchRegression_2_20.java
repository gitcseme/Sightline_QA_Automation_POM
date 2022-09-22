package sightline.advancedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvanceSearchRegression_2_20 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	AssignmentsPage assignmentPage;
	DocViewPage docView;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		docList = new DocListPage(driver);
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "assignmentHelperWindoStatus")
	public Object[][] assignmentHelperWindoStatus() {
		Object[][] users = { { "Completed" }, { "Assigned" } };
		return users;
	}

	/**
	 * @author
	 * @Date: 25/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that all session searches are purged at the time of
	 *              logout on Advanced Search Result screen.RPMXCON-48368
	 */
	@Test(description = "RPMXCON-48368", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyAllSesssionSearchesPurgedAtTimeOfLogoutOnAdvancedSearchResultScreen(String username,
			String password) throws Exception {

		SoftAssert assertion = new SoftAssert();
		String searchString = "Query; \"(all team) (thanks regards reply)\"~1000";
		int defaultSearchCount = 1;

		// login as Users
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-48368 Advanced Search");
		baseClass.stepInfo(
				"Verify that all session searches are purged at the time of logout on Advanced Search Result screen.");

		// configuring and performing the search
		baseClass.stepInfo("Performing Search using the SearchString '*'");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advancedSearchContentMetadata(Input.searchStringStar);
		sessionSearch.getQuerySearchBtn().waitAndClick(10);
		// clicking the 'When All Results are Ready' button and handling the generated
		// backGround ID
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();

		// configuring and performing the search
		baseClass.stepInfo(
				"Performing Search using the SearchString 'Query; \\\"(all team) (thanks regards reply)\\\"~1000'");
		sessionSearch.getNewSearchButton().waitAndClick(5);
		sessionSearch.advancedSearchContentMetadata(searchString);
		sessionSearch.getQuerySearchBtn().waitAndClick(10);
		sessionSearch.tallyContinue(5);
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();

		// logOut
		loginPage.logout();

		// login as Users
		loginPage.loginToSightLine(username, password);

		// navigating to advanced search page and verifying that all session searches
		// are purged at the time of logout on Advanced search Result Screen.
		sessionSearch.navigateToAdvancedSearchPage();
		int countOfSearchInPanel = sessionSearch.getSearchPanelCount().size();
		assertion.assertEquals(countOfSearchInPanel, defaultSearchCount);
		assertion.assertEquals((boolean)sessionSearch.getPureHitsCountNumText().isDisplayed(), false);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that all session searches are purged at the time of logout on Advanced search Result  Screen.");

		// logOut
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
