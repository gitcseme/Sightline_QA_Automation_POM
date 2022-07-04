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
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearchRegression2 {

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

	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "“ProximitySearch (Truthful OR Recall)”~9" },
				{ "\"ProximitySearch (Truthful OR Recall)\"~9" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity having 2 words with
	 *              OR in Basic Search Query Screen.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57303", enabled = true, dataProvider = "data", groups = { "regression" })
	public void verifyResultForTwoWords(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57303 Basic Search");
		base.stepInfo("Verify that result appears for proximity having 2 words with OR in Basic Search Query Screen.");

		// Verify Expanded Query
		session.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		base.waitForElement(session.getYesQueryAlert());
		session.getYesQueryAlert().waitAndClick(10);

		// verify session search Page
		session.returnPurehitCount();
		session.verifySessionSearchPageUrl();

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that UnFolder works properly using Bulk Folder Action
	 *              in Basic Search Screen
	 * @param data1
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57186", enabled = true, groups = { "regression" })
	public void verifyPhraseForSearch1() throws InterruptedException {
		String folder = "FOLDER" + Utility.dynamicNameAppender();

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57186 Basic Search");
		base.stepInfo("Verify that UnFolder works properly using Bulk Folder Action in Basic Search Screen");

		// perform bulk folder
		int purehit=session.basicContentSearch(Input.searchString5);
		session.bulkFolder(folder);

		//verify doc count after bulk folder
		TagsAndFoldersPage tagsAndFolder = new TagsAndFoldersPage(driver);
		tagsAndFolder.navigateToTagsAndFolderPage();
		base.stepInfo("navigated to Tags & folder page");
		tagsAndFolder.selectingFolderAndVerifyingTheDocCount(folder, purehit);
		
		//unfolder doc in session search page
		session.navigateToSessionSearchPageURL();
		base.stepInfo("navigated to session search page");
		session.bulkUnFolder(folder);
		
		//verify doc count after bulk unfolder
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.selectingFolderAndVerifyingTheDocCount(folder, 0);
		
		//delet folder
		tagsAndFolder.deleteAllFolders(folder);

		softAssertion.assertAll();
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
