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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
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

public class ManageCommects_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
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
	 * @author Vijaya.Rani ModifyDate:16/08/2022 RPMXCON-52535
	 * @throws Exception
	 * @Description To verify whether user is able to create comment after changing
	 *              role to RMU from Project Admin by Project Admin.
	 * 
	 */
	@Test(description = "RPMXCON-52535", enabled = true, groups = { "regression" })
	public void verifyAbleToCreateCommentAfterChangingRole() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52535");
		baseClass.stepInfo(
				"To verify whether user is able to create comment after changing role to RMU from Project Admin by Project Admin.");
		userManage = new UserManagement(driver);
		CommentsPage comments = new CommentsPage(driver);
		String commentName1 = "comment" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Navigate to users Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		if (userManage.getManageRole_Functionality_Manage().Enabled()) {
			baseClass.passedStep("By Default Manage rights is checked successfullly");
		} else {
			baseClass.failedStep("By Default Manage rights is Not checked");
		}
		userManage.saveButtonOfFunctionTab();
		baseClass.stepInfo("Manage Check Box is Verified");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();

		comments.navigateToCommentsPage();
		comments.AddComments(commentName1);
		driver.waitForPageToBeReady();
		comments.DeleteComments(commentName1);
		baseClass.passedStep("Manage > Comments page is able to create, modify and delete comment.");
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
