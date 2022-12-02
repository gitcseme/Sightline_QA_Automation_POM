package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import pageFactory.BaseClass;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_27 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	SecurityGroupsPage securityGroupsPage;
	DocViewRedactions docViewRedact;

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
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);

	}

	@DataProvider(name = "invaildProximitySearchQueriesRightQuotesOnly")
	public Object[][] invaildProximitySearchQueriesRightQuotesOnly() {
		return new Object[][] { { "Precision AND “ProximitySearch Truthful~5" },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful~5" },
				{ "\"Truthful Balance\" AND Precision AND \"ProximitySearch Truthful~5" },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful ~5" } };
	}

	@DataProvider(name = "invaildProximitySearchQueriesLeftQuotesOnly")
	public Object[][] invaildProximitySearchQueriesLeftQuotesOnly() {
		return new Object[][] { { "Precision AND ProximitySearch Truthful”~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful” ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful \" ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful\" ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" } };
	}

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Left double quotes only and combined with other
	 *              criteria in Advanced Search Query Screen. RPMXCON-57300
	 */
	@Test(description = "RPMXCON-57300", enabled = true, dataProvider = "invaildProximitySearchQueriesLeftQuotesOnly", groups = {
			"regression" })
	public void verifyBellyBandMessageForSearchQueriesWithOperatorHavingLeftDoubleQuoteOnly(String searches)
			throws InterruptedException {

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57300 Advanced Search");
		baseClass.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Left double quotes only and combined with other criteria in Advanced Search Query Screen.");

		// navigate to advanced search Page
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("Navigating to Advanced Search page");

		// configuring and searching
		baseClass.stepInfo("**Step-2 & 3 Configure query like @TestData and Click on \"Search\" button *");
		baseClass.stepInfo("Input String : " + searches);
		sessionSearch.advanedContentDraftSearch(searches);
		sessionSearch.getQuerySearchButton().waitAndClick(5);
		baseClass.stepInfo("Configuring and performing search");

		// verifying the warning message
		baseClass.stepInfo(
				"**Step-4 & 5 Observe that application displays warning message and Verify that pop up title*");
		sessionSearch.verifyProximitySearch();

		// logout
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Right double quotes only and combined with other
	 *              criteria in Advanced Search Query Screen. RPMXCON-57301
	 */
	@Test(description = "RPMXCON-57301", enabled = true, dataProvider = "invaildProximitySearchQueriesRightQuotesOnly", groups = {
			"regression" })
	public void verifyBellyBandMessageForSearchQueriesWithOperatorHavingRightDoubleQuoteOnly(String searchString1)
			throws InterruptedException {

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57301 Advanced Search");
		baseClass.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Right double quotes only and combined with other criteria in Advanced Search Query Screen.");

		// navigate to advanced search Page
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("Navigating to Advanced Search page");

		// configuring and searching
		baseClass.stepInfo("**Step-2 & 3 Configure query like @TestData and Click on \"Search\" button *");
		baseClass.stepInfo("Input String : " + searchString1);
		sessionSearch.advanedContentDraftSearch(searchString1);
		sessionSearch.getQuerySearchButton().waitAndClick(5);
		baseClass.stepInfo("Configuring and performing search");

		// verifying the warning message
		baseClass.stepInfo(
				"**Step-4 & 5 Observe that application displays warning message and Verify that pop up title*");
		sessionSearch.verifyProximitySearch();

		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T Test Case id:RPMXCON-48939
	 * @Description :Verify Work Product selection is working for Edge and Chrome
	 *              browsers
	 */

	@Test(description = "RPMXCON-48939", enabled = true, groups = { "regression" })
	public void verifySearchQueryInWorkProduct() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48939 Advanced Search");
		baseClass.stepInfo("Verify Work Product selection is working for Edge and Chrome browsers");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As:" + Input.pa1userName);
		String TagName = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.createNewTagwithClassification(TagName, "Select Tag Classification");

		SessionSearch search = new SessionSearch(driver);
	    search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		loginPage.logout();

		String[] UserName = { Input.pa1userName, Input.rmu1userName, Input.rev1userName };
		String[] Password = { Input.pa1password, Input.rmu1password, Input.rev1password };
		for (int i = 0; i < UserName.length; i++) {
			loginPage.loginToSightLine(UserName[i], Password[i]);
			baseClass.stepInfo("Logged in As:" + UserName[i]);

			baseClass.stepInfo("Switching to work product");
			search.switchToWorkproduct();
			search.selectTagInASwp(TagName);
			search.serarchWP();
			
			baseClass.stepInfo("verifying Selected Search query is displayed");
			baseClass.ValidateElement_Presence(search.getSelectQueryText(TagName),"Search Query");

			loginPage.logout();
		}

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

		System.out.println("**Executed Advanced search Regression2_21**");
	}

}
