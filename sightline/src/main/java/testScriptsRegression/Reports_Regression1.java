package testScriptsRegression;

import static org.testng.Assert.fail;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.ReportsPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Regression1 {

	ReportsPage reportPage;
	Driver driver;
	LoginPage loginPage;
	SessionSearch search;
	SoftAssert softAssertion;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	BaseClass baseClass;
	CommunicationExplorerPage comExpPage;
    TagCountbyTagReport tagCounts;
    DocListPage DLPage;
    String TagName2="ReportsTag"+Utility.dynamicNameAppender();
	String TagName1="ReportsTag"+Utility.dynamicNameAppender();
	String TagName3="ReportsTag"+Utility.dynamicNameAppender();
	int pureHit;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		search = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		reportPage = new ReportsPage(driver);	
	}
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyDocCount_ABM() throws Exception {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String SaveSaerchName ="ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		DocViewPage docViewPage = new DocViewPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();

		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-48789");
		baseClass.stepInfo("Validate data on Advanced batch management report when Domain and Project"
				+ " Admin associated to Assignments of a project");

		// Basic Search and Bulk Assign
		sessionsearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Search for text input completed");
		String Hits = sessionsearch.verifyPureHitsCount();
		sessionsearch.saveSearch(SaveSaerchName);
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		assignmentsPage.assignmentCreation(assignmentName1, Input.codeFormName);
		assignmentsPage.add4ReviewerAndDistribute();
		baseClass.stepInfo(assignmentName1 + "  Assignment Created and distributed to DA/PA/RMU/Rev");
		baseClass.impersonateRMUtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName1);
		baseClass.stepInfo("Doc is viewed in the docView Successfully fro RMU User.");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName1);
		baseClass.stepInfo("Doc is viewed in the docView Successfully for PA user");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.impersonateDAtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName1);
		baseClass.stepInfo("Doc is viewed in the docView Successfully for DA user.");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		ABMReportPage AbmReportPage = new ABMReportPage(driver);
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSaerchName, assignmentName1, false, true);
		int ToDoDocs = AbmReportPage.validateReport(AbmReportPage.getListToDo_Text(assignmentName1));
		int completedDocs = AbmReportPage.validateReport(AbmReportPage.getListCompletedDocs_Text(assignmentName1));
		int ActualTotalDocs = ToDoDocs + completedDocs;
		softAssertion.assertEquals(Integer.parseInt(Hits), ActualTotalDocs);
		softAssertion.assertEquals(3, completedDocs);
		softAssertion.assertAll();
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSaerchName, assignmentName1, true, true);
		baseClass.passedStep("Sucessfully Validated data on Advanced batch management report when Domain and Project Admin associated to Assignments of a project");
		loginPage.logout();
	}
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyABM_BG_Notification() throws Exception {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String SaveSaerchName ="ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		String SaveSaerch_docLevel ="ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		baseClass.selectproject(Input.highVolumeProject);
		String assignmentName1="assgnment" + Utility.dynamicNameAppender();
		String assignmentName ="assgnment" + Utility.dynamicNameAppender();
		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-56807");
		baseClass.stepInfo("Validate ABM report loads successfully from notification with source as Searches");
		//  Search and Bulk Assign
		search.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		search.getAdvancedSearchLinkCurrent().Click();
		driver.waitForPageToBeReady();
		int Bgcount = baseClass.initialBgCount();
		// Content search in advanced search page
		search.advancedContentBGSearch(Input.TallyCN, 1, false);
		baseClass.stepInfo("performed a content search in advanced search pagae and it goes to back ground. ");
		// checking for notification for BG search completed status.
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return baseClass.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait120);
		baseClass.stepInfo("Notifications are notified after BackGround Search completion.");
		sessionsearch.saveSearch(SaveSaerch_docLevel);
		baseClass.stepInfo("Search with text input "+Input.TallyCN); 
		sessionsearch.bulkAssign();
		// create Assignment and distrubute docs
		sessionsearch.bulkAssign();
		assignmentsPage.FinalizeAssignmentAfterBulkAssign();
		assignmentsPage.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		assignmentsPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName); 

		baseClass.selectproject(Input.highVolumeProject);
		//  Search and saving the saerch
		search.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		search.getAdvancedSearchLinkCurrent().Click();
		driver.waitForPageToBeReady();
		search.advancedContentBGSearch(Input.SearchString_HighVolume, 1, false);
		baseClass.stepInfo("performed a content search in advanced search pagae and it goes to back ground. ");
		// checking for notification for BG search completed status.
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return baseClass.initialBgCount() == Bgcount + 2;
			}
		}), Input.wait120);
		baseClass.stepInfo("Notifications are notified after BackGround Search completion.");
		baseClass.stepInfo("Search with text input " + Input.SearchString_HighVolume);
		sessionsearch.saveSearch(SaveSaerchName);
		sessionsearch.bulkAssign();
		// create Assignment 
		assignmentsPage.FinalizeAssignmentAfterBulkAssign();
		assignmentsPage.createAssignment_fromAssignUnassignPopup(assignmentName1, Input.codeFormName);
		assignmentsPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName1);
		
		ABMReportPage AbmReportPage = new ABMReportPage(driver);
		baseClass.stepInfo("Generating ABM with manage batch at document level.");
		AbmReportPage.generateABM_BackGroundReportGeneration(SaveSaerchName,SaveSaerch_docLevel, assignmentName1,assignmentName,true);
		baseClass.selectproject(Input.highVolumeProject);
		baseClass.stepInfo("Generating ABM with manage batch at assignment level.");
		AbmReportPage.generateABM_BackGroundReportGeneration(SaveSaerchName,SaveSaerch_docLevel, assignmentName1,assignmentName,false);
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" }};
		return users;
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password },{ Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	
	@AfterClass(alwaysRun = true)
	public void close() {
			System.out.println("Executed Reports Regression1");
	}

}
