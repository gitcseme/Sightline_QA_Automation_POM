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
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
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
	 * @author NA Testcase No:RPMXCON-54404
	 * @Description:Verify when RMU can input when count of left Docid in Reviewer batch.
	 **/
	@Test(description = "RPMXCON-54404", enabled = true, groups = { "regression" })
	public void verifyRMUCanInputRev() throws Exception {
	AssignmentsPage assignment = new AssignmentsPage(driver);
	SessionSearch session = new SessionSearch(driver);
	
	String assignmentName = "Assignment" + Utility.dynamicNameAppender();
	String expLeftTODO = "10";
	String expCount = "20";	
	
	base.stepInfo("RPMXCON-54404");
	base.stepInfo("Verify when RMU can input when count of left Docid in Reviewer batch.");
	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	base.stepInfo("Logged in As : " + Input.rmu1userName);
	
	assignment.navigateToAssignmentsPage();
	assignment.createAssignment(assignmentName, Input.codeFormName);
	
	session.basicContentSearch(Input.searchString2);
	session.bulkAssign();
	assignment.assignwithSamplemethod(assignmentName, "Count of Selected Docs", "20");
	assignment.editAssignmentUsingPaginationConcept(assignmentName);
	assignment.add2ReviewerAndDistribute();
	
	assignment.navigateToAssignmentsPage();
	assignment.editAssignmentUsingPaginationConcept(assignmentName);
	base.waitForElement(assignment.getAssignment_ManageReviewersTab());
	assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
	
	base.waitForElement(assignment.getDistributedDocs(Input.rmu1userName, "4"));
	String rmuLeftTodo = assignment.getDistributedDocs(Input.rmu1userName, "4").getText();
	String revLeftTodo = assignment.getDistributedDocs(Input.rev1userName, "4").getText();
	base.textCompareEquals(rmuLeftTodo, expLeftTODO, "RMU LeftToDoCount Expected" ,"RMU LeftToDoCount Not Expected");
	base.textCompareEquals(revLeftTodo, expLeftTODO, "REV LeftToDoCount Expected" ,"REV LeftToDoCountNot Expected");
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	assignment.RedistributeDocstouser(Input.rmu1userName);
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	base.waitForElement(assignment.getAssignment_ManageReviewersTab());
	assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
	base.waitForElement(assignment.getDistributedDocs(Input.rev1userName, "4"));
	String actCount = assignment.getDistributedDocs(Input.rev1userName, "4").getText();
	base.textCompareEquals(actCount, expCount, "All the Docs Shared", "All the Docs Not Shared");
	base.passedStep("Verified - when RMU can input when count of left Docid in Reviewer batch.");
	loginPage.logout();
	}
	
}
