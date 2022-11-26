package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_26 {
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

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              EmailSentDate date/time field with "Range"operator.RPMXCON-57235
	 */

	@Test(description = "RPMXCON-57235", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForEmailSentDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "EmailSentDate";
		String operator = "Range";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57235 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for EmailSentDate date/time field with \"Range\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for EmailSentDate
		// date/time field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savedSearch_SearchandSelect(searchName, "Yes");
		savedSearch.savedSearchExecute_Draft(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for EmailSentDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "EmailSentDate date/time" field search result return documents
		// which satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              EmailSentDate date/time field with \"Is\"operator.RPMXCON-57234
	 */

	@Test(description = "RPMXCON-57234", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForEmailSentDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "EmailSentDate";
		String operator = "IS";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57234 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for EmailSentDate date/time field with \"Is\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for EmailSentDate
		// date/time field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for EmailSentDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "EmailSentDate date/time" field search result return documents
		// which satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              CreateDate date/time field with \"Range\"operator.RPMXCON-57233
	 */

	@Test(description = "RPMXCON-57233", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForCreateDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "CreateDate";
		String operator = "Range";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57233 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for CreateDate date/time field with \"Range\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for CreateDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for CreateDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "CreateDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that CreateDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Saved Search >> Execute works properly for
	 *              CreateDate date/time field with "Is"operator[RPMXCON-57232]
	 */

	@Test(description = "RPMXCON-57232", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForCreateDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "CreateDate";
		String operator = "IS";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57232 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for CreateDate date/time field with \"Is\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for CreateDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for CreateDate  date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "CreateDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that CreateDate date/time field search result should return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

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

		System.out.println("**Executed Advanced search Regression2_21**");
	}

}
