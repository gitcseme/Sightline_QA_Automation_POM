package testScriptsRegressionSprint28;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Dashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression28 {


	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass base;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;

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
		base = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

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
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
	
	
	/**
	 * @author NA Testcase No:RPMXCON-54495
	 * @Description:To Verify 'Keep families together' field enabled/disabled
	 **/
	@Test(description = "RPMXCON-54495", enabled = true, groups = { "regression" })
	public void verifykeepFamilyTogether() throws Exception {
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();
		
		String assignmentGRP = "AssigGRP" + Utility.dynamicNameAppender();
		
		base.stepInfo("RPMXCON-54495");
		base.stepInfo("To Verify 'Keep families together' field enabled/disabled");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);
		
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGRP, "yes");
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		
		assignment.editAssgnGrp(assignmentGRP, "No");
		driver.waitForPageToBeReady();
		assignment.checkForToggleEnable(assignment.getAssgn_keepFamiliesTogetherToggle());
		assignment.checkForToggleEnable(assignment.getAssgn_keepEmailThreadTogetherToggle());
		assignment.checkForToggleEnable(assignment.getAssgnGrp_Create_DrawPooltoggle());
		
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssgnGrp(assignmentGRP, "NO");
		driver.waitForPageToBeReady();
		assignment.toggleEnableOrDisableOfAssignPage(false, true, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssgnGrp(assignmentGRP, "No");
		driver.waitForPageToBeReady();
		boolean drawPool = assignment.verifyToggleEnableORDisabled(assignment.getAssgnGrp_Create_DrawPooltoggle(), "Draw From Pool");
		boolean keepEmail = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepEmailThreadTogetherToggle(), "keep Email Toogle");
	    boolean keepFamily = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepFamiliesTogetherToggle(), "Keep Family Toogle");		
	    asserts.assertFalse(drawPool);
	    asserts.assertFalse(keepEmail);
	    asserts.assertFalse(keepFamily);
	    asserts.assertAll();
	    base.passedStep("verified - 'Keep families together' field enabled/disabled");
	    loginPage.logout();
	    
		}
	/**
	 * @author Brundha.T RPMXCON-54761
	 * @Description :To verify that RMU is able to view/add Add Reviewers pop on
	 *              click of Add Pending User Reviewer button
	 */
	@Test(description = "RPMXCON-54761", enabled = true, groups = { "regression" })
	public void verifyReviewersCountInWidget() throws InterruptedException {

		
		base.stepInfo("Test case Id: RPMXCON-54761");
		base.stepInfo(
				"To verify that RMU is able to view/add Add Reviewers pop on click of Add Pending User Reviewer button");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		String assgngrpName = "assignmentGrp" + Utility.dynamicNameAppender();
		String newAssign = "assignment" + Utility.dynamicNameAppender();
		String FirstName = "QA1" + Utility.dynamicNameAppender();
		String LastName = "Automation";
		String MailID = "testing" + Utility.dynamicNameAppender() + "@consilio.com";
		
		UserManagement user = new UserManagement(driver);
		AssignmentsPage agnmt=new AssignmentsPage(driver);
		SessionSearch search=new SessionSearch(driver);
		Dashboard dashBoard =new Dashboard(driver);
		
		user.navigateToUsersPAge();
		base.stepInfo("Creating pending active user for reviewer");
		user.createUser(FirstName, LastName, Input.Reviewer, MailID, " ", Input.projectName);
		System.out.println(MailID);
		loginPage.logout();
		
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		
		base.stepInfo("Create Assignment Group");
		agnmt.createAssgnGroup(assgngrpName);
		agnmt.selectAssignmentGroup(assgngrpName);
		agnmt.createAssignmentFromAssgnGroup(newAssign,Input.codingFormName);
		
		base.stepInfo("Bulk Assigning");
		search.basicContentSearch(Input.testData1);
		search.bulkAssignExistingForCopyAssignment(assgngrpName);
		
		
		base.stepInfo("Adding Reviewer and distributing");
		agnmt.selectAssignmentGroup(assgngrpName);
		if (!agnmt.getSelectAssignmentHighlightCheck(newAssign).isElementAvailable(5)) {
			agnmt.getSelectAssignment(newAssign).waitAndClick(5);
		}
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(3);
		base.waitForElement(agnmt.getAssignmentAction_EditAssignment());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(3);
		agnmt.addReviewerAndDistribute(MailID);
		
		base.stepInfo("Verifying reviewers count in RMU dashboard");
		dashBoard.navigateToDashboard();
		dashBoard.AddNewWidgetToDashboard(Input.ReviewerProductivity);
		base.waitTime(2);
		int Size = dashBoard.getReviewerslist().size();
		base.ValidateElementCollection_Presence(dashBoard.getReviewerslist(),
				"Reviewers in dashboard");
		System.out.println(Size);
		if (Size == 6) {
			base.passedStep("Reviewers are displayed in dashboard");
		} else {
			base.failedStep("Reviewers are not displayed as expected");
		}
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);
		
		// delete the created user
		user.filterTodayCreatedUser();
		user.filterByName(MailID);
		user.deleteUser();
		loginPage.logout();
	}

}
