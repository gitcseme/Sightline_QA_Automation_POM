package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Consilio {
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

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" } };
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

	@Test(description = "RPMXCON-69128", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyExpectedSearchTermsInTheftOfCustomerInfo(String username, String password, String fullname)
			throws InterruptedException {
		String searchName = "Outside Help";
		String Query = Input.TheftOfCustomerInfoOutsideHelp;

		base.stepInfo("Test case Id: RPMXCON-69128 - Saved Search");
		base.stepInfo(
				"Verify that PBM module Theft of Customer Info terms has expected query present under Outside Help");

		// Login as RMU user
		base.stepInfo("Login to sightline and Select Project**");
		login.loginToSightLine(username, password);
		
		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// Select DEPIPTheft
		saveSearch.selectSearchGroupTab(Input.DEPIPTheft, Input.preBuilt);

		// Validate DEPIPTheft
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.DEPIPTheft), Input.DEPIPTheft);

		saveSearch.rootGroupExpansion();

		// Select TheftOfCustomerInfo
		saveSearch.selectSearchGroupTab(Input.TheftOfCustomerInfo, Input.DEPIPTheft);

		saveSearch.savedSearch_Searchandclick(searchName);
		if (saveSearch.getSearchName(searchName).isElementPresent()) {
			base.passedStep("Saved search query is displayed as"+searchName);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}
		saveSearch.savedSearchEdit(searchName);
		session.verifyDraftedQueryPresentInSearchbox(Query);
		base.passedStep("Saved search query is displayed" + Query);
	}

	@Test(description = "RPMXCON-69127", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyExpectedSearchTermsInInvolvedOutsidedersAndRemovFollowingSearchName(String username,
			String password, String fullname) throws InterruptedException {
		String searchName = "Outside Help";
		String Query = Input.OutsideHelp;
		String searchName1 = "Salary Match";
		String Query1 = Input.SalaryMatch;
		String searchName2 = "Talk it over";

		base.stepInfo("Test case Id: RPMXCON-69127 - Saved Search");
		base.stepInfo(
				"Verify that PBM module Involved Outsiders terms has expected query present under Salary Match, Outside Help and following search name should be removed Talk it over.");

		// Login as RMU user
		base.stepInfo("Login to sightline and Select Project**");
		login.loginToSightLine(username, password);

		/*Verify search query for search name "Outside Help"*/
		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// Select DEPIPTheft
		saveSearch.selectSearchGroupTab(Input.DEPIPTheft, Input.preBuilt);

		// Validate DEPIPTheft
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.DEPIPTheft), Input.DEPIPTheft);

		saveSearch.rootGroupExpansion();

		// Select Involvedoutsiders
		saveSearch.selectSearchGroupTab(Input.Involvedoutsiders, Input.DEPIPTheft);

		saveSearch.savedSearch_Searchandclick(searchName);
		if (saveSearch.getSearchName(searchName).isElementPresent()) {
			base.passedStep("Saved search query is displayed as"+searchName);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}
		saveSearch.savedSearchEdit(searchName);
		session.verifyDraftedQueryPresentInSearchbox(Query);
		base.passedStep("Saved search query is displayed" + Query);
		
		/*Verify search query for search name "Salary Match"*/
		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// Select DEPIPTheft
		saveSearch.selectSearchGroupTab(Input.DEPIPTheft, Input.preBuilt);

		// Validate DEPIPTheft
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.DEPIPTheft), Input.DEPIPTheft);

		saveSearch.rootGroupExpansion();

		// Select Involvedoutsiders
		saveSearch.selectSearchGroupTab(Input.Involvedoutsiders, Input.DEPIPTheft);

		saveSearch.savedSearch_Searchandclick(searchName1);
		if (saveSearch.getSearchName(searchName1).isElementPresent()) {
			base.passedStep("Saved search query is displayed as"+searchName1);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}
		saveSearch.savedSearchEdit(searchName1);
		session.verifyDraftedQueryPresentInSearchbox(Query1);
		base.passedStep("Saved search query is displayed" + Query1);
		/* Verify search query for search name "Talk it over" should not present */
		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// Select DEPIPTheft
		saveSearch.selectSearchGroupTab(Input.DEPIPTheft, Input.preBuilt);

		// Validate DEPIPTheft
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.DEPIPTheft), Input.DEPIPTheft);

		saveSearch.rootGroupExpansion();

		// Select Involvedoutsiders
		saveSearch.selectSearchGroupTab(Input.Involvedoutsiders, Input.DEPIPTheft);
		saveSearch.getSaveSearchID(searchName2);
		;
		if (saveSearch.getSearchName(searchName2) != null) {
			base.passedStep("Saved search query is displayed as expected");
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}
	}
	
	 @Test(description = "RPMXCON-69124", enabled = true, dataProvider =
	 "SavedSearchwithUsers", groups = {
	 "regression" })
	public void verifyExpectedSearchTermsInGenericPII(String username, String password, String fullname)
			throws InterruptedException {
		String searchName = "Child Related Content";
		String Query = Input.ChildRelatedContent;
		String searchName1 = "Sexual Orientation";
		String Query1 = Input.SexualOrientation;
		String searchName2 = "Pregnancy or Divorce";
		String Query2 = Input.PregnancyORDivorce;

		base.stepInfo("Test case Id: RPMXCON-69124 - Saved Search");
		base.stepInfo(
				"Verify that PBM module Generic PII terms has expected query present under Child Related Content, Sexual Orientation, Pregnancy or Divorce search name.");

		// Login as RMU user
		base.stepInfo("Login to sightline and Select Project**");
		login.loginToSightLine(username, password);

		/* Verify search query for search name "ChildRelatedContent" */
		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// Select PII
		saveSearch.selectSearchGroupTab(Input.PII, Input.preBuilt);

		// Validate PII
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.PII), Input.PII);

		saveSearch.rootGroupExpansion();

		// Select EUPII
		saveSearch.selectSearchGroupTab(Input.EUPII, Input.PII);
		// Validate EUPII
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.EUPII), Input.EUPII);

		saveSearch.rootGroupExpansion();

		// Select GenericPIITerms
		saveSearch.selectSearchGroupTab(Input.GenericPIITerms, Input.EUPII);

		saveSearch.savedSearch_Searchandclick(searchName);

		if (saveSearch.getSearchName(searchName).isElementPresent()) {
			base.passedStep("Saved search query is displayed." + searchName);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchSelectRadioButton().waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query);
		base.passedStep("Saved search query is displayed" + Query);
		/* Verify search query for search name "Sexual Orientation" */
		driver.NavigateBack();

		saveSearch.savedSearch_Searchandclick(searchName1);

		if (saveSearch.getSearchName(searchName1).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName1);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}
		saveSearch.getSavedSearchSelectRadioButton().waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query1);
		base.passedStep("Saved search query is displayed" + Query1);
		/* Verify search query for search name "Pregnancy or Divorce" */

		driver.NavigateBack();

		saveSearch.savedSearch_Searchandclick(searchName2);

		if (saveSearch.getSearchName(searchName2).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName2);
		} else {
			base.failedStep("Saved search query is not displayed as expected");

		}
		saveSearch.getSavedSearchSelectRadioButton().waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query2);
		base.passedStep("Saved search query is displayed" + Query2);
	}

	@Test(description = "RPMXCON-69125", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyExpectedSearchTermsInGDPRTermsEnglish(String username, String password, String fullname)
			throws InterruptedException {
		String searchName = "401k";
		String Query = Input.K;
		String searchName1 = "child";
		String Query1 = Input.child;
		String searchName2 = "doctor";
		String Query2 = Input.doctor;
		String searchName3 = "firing";
		String Query3 = Input.firing;
		String searchName4 = "homo";
		String Query4 = Input.homo;
		String searchName5 = "homesex*";
		String Query5 = Input.homesex;
		
		base.stepInfo("Test case Id: RPMXCON-69125 - Saved Search");
		base.stepInfo(
				"Verify that PBM module GDPR Terms-English terms has expected query present under 401(k),child,doctor ,firing,homo,homesex*,homophob*,homoerot*,parent,personal,sex*,sexual.");

		// Login as RMU user
		base.stepInfo("Login to sightline and Select Project**");
		login.loginToSightLine(username, password);

		// Navigate to Saved Search page and select default SG Tab
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSearchGroupTab(Input.preBuilt, Input.shareSearchDefaultSG);

		// validate Pre-Built model Search group
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.preBuilt), Input.preBuilt);
		saveSearch.rootGroupExpansion();

		// Select PII
		saveSearch.selectSearchGroupTab(Input.PII, Input.preBuilt);

		// Validate PII
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.PII), Input.PII);

		saveSearch.rootGroupExpansion();

		// Select EUPII
		saveSearch.selectSearchGroupTab(Input.EUPII, Input.PII);
		// Validate EUPII
		base.ValidateElement_Presence(saveSearch.getSavedSearchGroupName(Input.EUPII), Input.EUPII);

		saveSearch.rootGroupExpansion();

		// Select GDPRTermsEnglish
		saveSearch.selectSearchGroupTab(Input.GDPRTermsEnglish, Input.EUPII);

		/* Verify search query for search name "401k" */
		saveSearch.savedSearch_Searchandclick(searchName);

		if (saveSearch.getSearchName(searchName).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchSelectRadioButton().waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query);
		base.passedStep("Saved search query is displayed" + Query);
		/* Verify search query for search name "child" */
		driver.NavigateBack();
		saveSearch.savedSearch_Searchandclick(searchName1);

		if (saveSearch.getSearchName(searchName1).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName1);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchDisplayedSearchRadioButton(1).waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query1);
		base.passedStep("Saved search query is displayed" + Query1);
		/* Verify search query for search name "Doctor" */
		driver.NavigateBack();
		saveSearch.savedSearch_Searchandclick(searchName2);

		if (saveSearch.getSearchName(searchName2).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName2);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchDisplayedSearchRadioButton(3).waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query2);
		base.passedStep("Saved search query is displayed" + Query2);
		/* Verify search query for search name "firing" */
		driver.NavigateBack();
		saveSearch.savedSearch_Searchandclick(searchName3);

		if (saveSearch.getSearchName(searchName3).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName3);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchDisplayedSearchRadioButton(1).waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query3);
		base.passedStep("Saved search query is displayed" + Query3);
		/* Verify search query for search name "homo" */
		driver.NavigateBack();
		saveSearch.savedSearch_Searchandclick(searchName4);

		if (saveSearch.getSearchName(searchName4).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName4);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchDisplayedSearchRadioButton(1).waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query4);
		base.passedStep("Saved search query is displayed" + Query4);
		/* Verify search query for search name "homesex" */
		driver.NavigateBack();
		saveSearch.savedSearch_Searchandclick(searchName5);

		if (saveSearch.getSearchName(searchName5).isElementPresent()) {
			base.passedStep("Saved search query is displayed" + searchName5);
		} else {
			base.failedStep("Saved search query is not  displayed as expected");

		}

		saveSearch.getSavedSearchSelectRadioButton().waitAndClick(5);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifyDraftedQueryPresentInSearchbox(Query5);
		base.passedStep("Saved search query is displayed" + Query5);
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
//	                                                         TODO: handle exception
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
