package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_sprint24 {

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
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-54484", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsSelectedFluctuation() throws InterruptedException, ParseException, IOException {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo(
				"Verifying the fluctuation of document count for " + "all the bulk actions in Manage Assignments");
		baseClass.stepInfo("Test case Id: RPMXCON-54484");

		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String folderTagNAme = "folderTag" + Utility.dynamicNameAppender();

		String count = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.bulkAssign();
		assignment.FinalizeAssignmentAfterBulkAssign();
		assignment.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.scrollingToElementofAPage(assignment.getAssignmentSaveButton());
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);

		assignment.navigateToAssignmentsPage();
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(assignment.getSelectAssignment(assignmentName));
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Tag All Option");
		assignment.Bulk_TagAll_FolderAll(true);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme, count);

		assignment.navigateToAssignmentsPage();
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(assignment.getSelectAssignment(assignmentName));
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Folder All Option");
		assignment.Bulk_TagAll_FolderAll(false);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme, count);

		assignment.deleteAssgnmntUsingPagination(assignmentName);
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
