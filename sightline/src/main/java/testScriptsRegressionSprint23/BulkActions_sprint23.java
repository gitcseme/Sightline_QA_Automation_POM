package testScriptsRegressionSprint23;

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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_sprint23 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54485", enabled = true, groups = { "regression" })
	public void verifyDocCountFluctuation_ABM() throws Exception {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);

		String SaveSearchName = "ABM_SavedSearch" + Utility.dynamicNameAppender();
		String folderTagNAme = "ABM" + Utility.dynamicNameAppender();
		String folderTagNAme1 = "ABM" + Utility.dynamicNameAppender();
		String assignmentName1 = "ABM" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id:RPMXCON-54485");
		baseClass.stepInfo("Verifying the fluctuation of document count for all the bulk actions "
				+ "in Advanced Batch Management report");

		// Basic Search and Bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionSearch.verifyPureHitsCount();
		sessionSearch.saveSearch(SaveSearchName);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		assignment.assignmentCreation(assignmentName1, Input.codeFormName);
		assignment.addReviewerAndDistributeDocs();
		baseClass.stepInfo(assignmentName1 + " Assignment Created and distributed to RMU/Rev");

		// generate report in ABM
		baseClass.stepInfo("**Advance batch management report generation at assignment level**");
		ABMReportPage AbmReportPage = new ABMReportPage(driver);
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, false, false);

		// select doc from report table
		String count = AbmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);

		baseClass.stepInfo("**click action button-bulk folder- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(true);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme, count);
		sessionSearch.getActionPopupCloseBtn().Click();

		baseClass.stepInfo("**Advance batch management report generation at assignment level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, false, false);

		// select doc from report table
		count = AbmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);

		baseClass.stepInfo("**click action button-bulk tag- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(false);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme, count);
		sessionSearch.getActionPopupCloseBtn().Click();
		
		baseClass.stepInfo("**Advance batch management report generation at assignment level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, false, false);

		// select doc from report table
		count = AbmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);
		baseClass.stepInfo("**click action button-bulk assign- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.bulkAssign();
		sessionSearch.verifyDocsFluctuation_BulkAssign(count);
		sessionSearch.getActionPopupCloseBtn().Click();

		baseClass.stepInfo("**Advance batch management report generation at document level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, true, false);
		AbmReportPage.docSelection();

		baseClass.stepInfo("**click action button-bulk folder- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(true);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme1, "1");
		sessionSearch.getActionPopupCloseBtn().Click();

		baseClass.stepInfo("**Advance batch management report generation at document level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, true, false);
		AbmReportPage.docSelection();

		baseClass.stepInfo("**click action button-bulk tag- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(false);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme1, "1");
		sessionSearch.getActionPopupCloseBtn().Click();

		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, true, false);
		AbmReportPage.docSelection();

		baseClass.stepInfo("**click action button-bulk assign- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.bulkAssign();
		sessionSearch.verifyDocsFluctuation_BulkAssign("1");
		sessionSearch.getActionPopupCloseBtn().Click();

		assignment.deleteAssgnmntUsingPagination(assignmentName1);
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
		System.out.println("**Executed  BulkActions_sprint23 .**");
	}
}
