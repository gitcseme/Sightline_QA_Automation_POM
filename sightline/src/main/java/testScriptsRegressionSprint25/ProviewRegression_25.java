package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProviewRegression_25 {
	Driver driver;
	LoginPage login;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssert;
	Categorization categorize;
	TagsAndFoldersPage tagsAndFolder;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "USERS")
	public Object[][] USERS() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that project admin can view the save searched result
	 *              on proview page. [RPMXCON-54126]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54126", enabled = true, groups = { "regression" })
	public void verifyAllTagDisplayed() throws Exception {

		base.stepInfo("RPMXC0N-54126  Proview");
		base.stepInfo("To verify that project admin can view the save searched result on proview page.");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in as RMU User");

		// fetch All Available Searches in project
		session.navigateToAdvancedSearchPage();
		session.switchToWorkproduct();
		session.getSavedSearchResult().waitAndClick(10);

		base.waitForElementCollection(session.getTree());
		List<String> searchlist = base.availableListofElements(session.getTree());

		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Fetch displayed Searches in Categorization page
		categorize.selectTrainingSet("Identify by Saved Search");
		driver.scrollingToBottomofAPage();

		base.waitForElementCollection(categorize.getAvailableSearches());
		List<String> searchPresentInCategorize = base.availableListofElements(categorize.getAvailableSearches());

		// Verify All existing Searches is Displayed

		List<String> expectedSearchList = base.sortTheList(searchlist, true);
		driver.waitForPageToBeReady();
		List<String> actualSearchList = base.sortTheList(searchPresentInCategorize, true);
		boolean flag = base.compareListViaContains(expectedSearchList, actualSearchList);
		base.printResutInReport(flag, "All existing Searches are Displayed.", "All existing Searches is not Displayed.",
				"Pass");

		// Logout
		login.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssert = new SoftAssert();
		categorize = new Categorization(driver);
		session = new SessionSearch(driver);
		tagsAndFolder = new TagsAndFoldersPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
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

		System.out.println("Executed :Reviewer Result Report Regression ");
	}
}
