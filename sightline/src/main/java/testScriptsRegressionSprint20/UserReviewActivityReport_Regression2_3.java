package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserReviewActivityReport_Regression2_3 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	UserReviewActivityReport userActivityRptPg;
	AssignmentsPage agnmt;
	SessionSearch search;
	DocViewPage docview;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify the Actions in the User Review Activity report and in Document Audit report
	 */
	@Test(description = "RPMXCON-56475",groups = {"regression" },enabled = true)
	public void verifyActionTypesInReports() {
		bc.stepInfo("Test case Id: RPMXCON-56475");
		bc.stepInfo("Verify the Actions in the User Review Activity report and in Document Audit report");
		SoftAssert sa = new SoftAssert();
		List<String> actualActionsListInUserReviewActivityRpt = new ArrayList<>();
		List<String> actualActionsListInDocumentAuditRpt = new ArrayList<>();
		ArrayList<String> expectedList = new ArrayList<String>(Arrays.asList("Added to Production", "Assigned to Assignment", "Batch Printed", "Comment Added", "Commented Removed", "Completed", "Download Native", "FOLDERBULK",
				"FOLDERCODESAME", "Foldered", "FOLDEREXPLICIT", "FOLDERPROPAGATED", "Highlighting Added", "Highlighting Removed", "Ingested", "Produced", "Production Deleted", "Published", "Redaction Added", "Redaction Removed",
				"Released to Security Group", "Remarks Added", "Remarks Removed", "Removed from Production", "Save Custom Field", "Saved", "Tagged - Bulk", "Tagged - Code Same", "Tagged - Explicit", "Tagged - Propagate",
				"Removed from Assignment", "UnCompleted", "UNFOLDERBULK", "UNFOLDERCODESAME", "UnFoldered", "UNFOLDEREXPLICIT", "UNFOLDERPROPAGATED", "UnPublished", "Removed from Assignment", "Untagged - Bulk",
				"Untagged - Code Same", "Untagged - Explicit", "Untagged - Propagate", "Viewed", "Viewed Images", "Viewed Translations"));
		agnmt = new AssignmentsPage(driver);
		search =new SessionSearch(driver);
		docview = new DocViewPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userActivityRptPg.navigateToUserReviewActivityReport();
		bc.waitTillElemetToBeClickable(userActivityRptPg.getActionTypeExpandIcon());
		userActivityRptPg.getActionTypeExpandIcon().waitAndClick(10);
		actualActionsListInUserReviewActivityRpt =bc.getAvailableListofElements(userActivityRptPg.actionsList());
		sa.assertEquals(expectedList, actualActionsListInUserReviewActivityRpt,"Actions list are not displayed as expected");
		sa.assertAll();		
		bc.passedStep("The expected actions list are present in user review activity report page");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		userActivityRptPg.navigateToDocumentAuditReport();
		bc.waitTillElemetToBeClickable(userActivityRptPg.getActionTypeExpandIcon());
		userActivityRptPg.getActionTypeExpandIcon().waitAndClick(10);
		actualActionsListInDocumentAuditRpt =bc.getAvailableListofElements(userActivityRptPg.actionsList());
		sa.assertEquals(expectedList, actualActionsListInDocumentAuditRpt,"Actions list are not displayed as expected");
		sa.assertAll();		
		bc.passedStep("The expected actions list are present in document audit report page");
		lp.logout();
	}

	

	

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		userActivityRptPg = new UserReviewActivityReport(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
//				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
