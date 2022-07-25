package testScriptsRegressionSprint18;

import java.awt.AWTException;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;

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
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
	}

	@DataProvider(name = "searchTerms")
	public Object[][] searchTerms() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "\"Good Morn!ing2\"" },
				{ Input.pa1userName, Input.pa1password, "\"Good Morn~ing1\"" },
				{ Input.rev1userName, Input.rev1password, "\"Good Morn@ing3\"" } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that warning message appears in Advanced Search when a
	 *              user configured an audio search term with any special characters
	 *              and performed "copy to New Search"
	 * @param username
	 * @param password
	 * @param User
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49368", dataProvider = "searchTerms", enabled = true, groups = { "regression" })
	public void verifyWarningMessageAndCopyToNew(String username, String password, String searchTerm)
			throws ParseException, Exception {

		// login as User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49368  Advanced Search");
		baseClass.stepInfo(
				"Verify that warning message appears in Advanced Search when a user configured an audio search term with any special characters and performed \"copy to New Search\"");

		// configure audio query
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.CreateDraftaudioSearchQuery(Input.audioSearchString1, Input.language);
		baseClass.hitEnterKey(1);

		// edit query and add spcl character
		sessionSearch.modifySearchTextArea(1, Input.audioSearchString1, "\"Good Morn~ing1\"", "Save");
		driver.waitForPageToBeReady();

		// verify copy to new search action
		sessionSearch.verifyCopyToNewSearch();

		// verify warning message
		sessionSearch.verifyWarningMessage(true, 1);

		// logOut
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify as an PA user login into the Application, I will be
	 *              able to select multiple Security Group as an search criteria & I
	 *              am able to search a query based on that
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57076", enabled = true, groups = { "regression" })
	public void verifySearchForSG() throws ParseException, Exception {

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57076  Advanced Search");
		baseClass.stepInfo(
				"To verify as an PA user login into the Application, I will be able to select multiple Security Group as an search criteria & I am able to search a query based on that");

		// select security group in WP
		sessionSearch.switchToWorkproduct();
		sessionSearch.workProductSearch("security group", Input.securityGroup, false);

		// configure content query
		sessionSearch.advancedContentSearchWithSearchChanges(Input.searchString2, "No");
		int purehit = sessionSearch.saveAndReturnPureHitCount();
		baseClass.stepInfo("Search result is dispalyed and purehit is : " + purehit);

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
