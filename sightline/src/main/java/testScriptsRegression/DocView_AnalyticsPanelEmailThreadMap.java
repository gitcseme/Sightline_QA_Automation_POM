package testScriptsRegression;

import java.io.IOException;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanelEmailThreadMap {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SoftAssert softAssertion;

	@BeforeMethod(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();

		driver = new Driver();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the thread map toggle is enabled, the
	 *              length of the email thread should not be a constraint and with
	 *              email threads such as the above (23 mails and 140 participants)
	 *              from Analytics panel child window 'RPMXCON-51846'
	 * 
	 */
	@Test(priority = 1)
	public void verifyDocumentFromAnalyticsPanelWithEmailThreadMap()
			throws ParseException, InterruptedException, IOException {

		String searchName = Input.SavedSearch;
		int pureHit = Input.pureHitCount;
		String codingfrom = "cfC1" + Utility.dynamicNameAppender();
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		String textValue = Input.TextValue;
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		SavedSearch savedSearch = new SavedSearch(driver);
		baseClass = new BaseClass(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		savedSearch.SaveSearchToBulkAssign(searchName, assignmentName, codingfrom, pureHit);
		baseClass.stepInfo(
				"Doc is Assigned from Saved Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentPage.editAssignment(assignmentName);
		assignmentPage.addReviewerAndDistributeDocs(assignmentName, pureHit);
		baseClass.stepInfo("Reviewers are added and the doc are distributed successfully");
		assignmentPage.SelectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
	//	docViewAnalytics.docViewAnalyticsPanelThread();
		baseClass.stepInfo(
				"Thread Map toggled and Email thread is viewed from Analytic Child window and verified successfully");
		docViewAnalytics.verifyDocToViewInDocView(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is viewed form the Dasboard successfully");
	//	docViewAnalytics.docViewAnalyticsPanelThread();
		baseClass.stepInfo(
				"Thread Map toggled and Email thread is viewed from Analytic Child window and verified successfully");
		Element text = docViewAnalytics.getDocView_DocumentThreadMap();
		softAssertion.assertEquals(text, textValue);
		baseClass.passedStep(
				"To Verify that when the thread map toggle is enabled, the length of the email thread should not be a constraint and with email threads such as the above (23 mails and 140 participants) from Analytics panel child window");
	}

	@AfterMethod(alwaysRun = true)

	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {
			loginPage.closeBrowser();

		}
		LoginPage.clearBrowserCache();
	}

}
