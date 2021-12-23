package testScriptsRegression;

import java.awt.AWTException;
import java.awt.List;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagCountByTagsReport_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	String hitsCount;
	String hitsCount1;
	TagCountbyTagReport tagCountPage;
	String tagName = "AAtag" + Utility.dynamicNameAppender();
	String tagName1 = "AAtag" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		// Search and save it
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		hitsCount = search.verifyPureHitsCount();
        search.verifyBulkTag(tagName);
	    bc.selectproject();
		// Search and save it
		search.basicContentSearch(Input.searchString5);
		hitsCount1 = search.verifyPureHitsCount();
	    search.verifyBulkTag(tagName1);
		lp.logout();
		lp.quitBrowser();
	}

	 @Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1)
	public void ValidateSearchTermreport_BulkTag(String username, String password, String role)
			throws InterruptedException, AWTException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56235");
		bc.stepInfo("To verify that User can view Tags Count by Tag Report by Tagwise.");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		java.util.List<String> elementNames = new ArrayList<String>();
		ArrayList<String> tagNamesExpectedList = new ArrayList<String>(Arrays.asList(tagName,tagName1));
		tagCountPage=new TagCountbyTagReport(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		tagCountPage.GenerateTagCountreport_Multipletags(tagName, tagName1);
		elementNames=tagCountPage.getTagNamesFromReport();
		if(elementNames.containsAll(tagNamesExpectedList)) {
			softAssertion.assertTrue(true);
			softAssertion.assertTrue(tagCountPage.verifyTagDocCount(tagName,hitsCount,1));
			softAssertion.assertTrue(tagCountPage.verifyTagDocCount(tagName1,hitsCount1,1));
			
		}else {
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
		bc.passedStep("Report generated and  Tagged Doc Count displayed as per the Tags");
		
	}


	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			Input in = new Input();
			in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllTags("TCRtag");
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	}
}
