package sightline.manage;

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

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ManageComments_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52723
	 * @Description Verify that special characters should not be allowed to add
	 *              comment.
	 */
	@Test(description = "RPMXCON-52723", enabled = true, groups = { "regression" })
	public void verifySpecialCharactersNotAllowedComment() {

		baseClass.stepInfo("Test case Id: RPMXCON-52723");
		baseClass.stepInfo("Verify that special characters should not be allowed to add comment.");
		String commentName = "comment@#&";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		CommentsPage comments = new CommentsPage(driver);
		// Add comments
		comments.AddCommentsWithErrorMsg(commentName);
		// get Error msg
		baseClass.passedStep(
				"Error message is displayed as ' Only alphanumeric characters and underscore are allowed '.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		// Add comments
		comments.AddCommentsWithErrorMsg(commentName);
		// get Error msg
		baseClass.passedStep(
				"Error message is displayed as ' Only alphanumeric characters and underscore are allowed '.");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52722
	 * @Description Verify that comment should not be created with space.
	 */
	@Test(description = "RPMXCON-52722", enabled = true, groups = { "regression" })
	public void verifySpecesNotAllowedComment() {

		baseClass.stepInfo("Test case Id: RPMXCON-52722");
		baseClass.stepInfo("Verify that comment should not be created with space.");
		String commentName = "add&^  comments";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		CommentsPage comments = new CommentsPage(driver);
		// Add comments
		comments.AddCommentsWithErrorMsg(commentName);
		// get Error msg
		baseClass.passedStep(
				"Error message is displayed as ' Only alphanumeric characters and underscore are allowed '.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		// Add comments
		comments.AddCommentsWithErrorMsg(commentName);
		// get Error msg
		baseClass.passedStep(
				"Error message is displayed as ' Only alphanumeric characters and underscore are allowed '.");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52686
	 * @throws InterruptedException
	 * @Description Verify after impersonation user should not be allowed to edit
	 *              the comment.
	 */
	@Test(description = "RPMXCON-52686", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationNotAllowedToEdit() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52686");
		baseClass.stepInfo("Verify after impersonation user should not be allowed to edit the comment.");
		CommentsPage comments = new CommentsPage(driver);
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		// Impersonate SA to PA
		baseClass.impersonateSAtoPA();
		// Edit Btn
		comments.EditCommentsIsDisabled();
		baseClass.stepInfo("Edit comment pop up is open, and comment is not editable  ");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		// Impersonate SA to RMU
		baseClass.impersonateSAtoRMU();
		// Edit Btn
		comments.EditCommentsIsDisabled();
		baseClass.stepInfo("Edit comment pop up is open, and comment is not editable  ");
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		// Impersonate PA to RMU
		baseClass.impersonatePAtoRMU();
		// Edit Btn
		comments.EditCommentsIsDisabled();
		baseClass.stepInfo("Edit comment pop up is open, and comment is not editable  ");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52685
	 * @throws InterruptedException
	 * @Description Verify comment should not be editable from manage comments.
	 */
	@Test(description = "RPMXCON-52685", enabled = true, groups = { "regression" })
	public void verifyNotAllowedToEditComments() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52685");
		baseClass.stepInfo("Verify comment should not be editable from manage comments.");
		CommentsPage comments = new CommentsPage(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		String commentName = "comment"+Utility.dynamicNameAppender();
		comments.AddComments(commentName);
		// Edit Btn
		comments.EditCommentsIsDisabled();
		baseClass.stepInfo("Edit comment pop up is open, and comment is not editable  ");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		String commentName1 = "comment"+Utility.dynamicNameAppender();
		comments.AddComments(commentName1);
		// Edit Btn
		comments.EditCommentsIsDisabled();
		baseClass.stepInfo("Edit comment pop up is open, and comment is not editable  ");
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

}
