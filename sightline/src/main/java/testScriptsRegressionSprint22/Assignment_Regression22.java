package testScriptsRegressionSprint22;

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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression22 {

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
	 * Author :Arunkumar date: 21/09/2022 TestCase Id:RPMXCON-53654
	 * Description :To verify that in Distribute Document section it displays Reviewers name 
	 * as Last name First Name and User Name 
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-53654",enabled = true, groups = { "regression" })
	public void verifyReviewerNameFormat() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53654");
		baseClass.stepInfo("verify reviewers name display in distribute document section");
		String assignmentName = "assign"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as REV");
		baseClass.stepInfo("get first and last name of reviewer");
		baseClass.waitForElement(loginPage.getSignoutMenu());
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(loginPage.getEditProfile());
		loginPage.getEditProfile().waitAndClick(10);
		baseClass.waitForElement(loginPage.getFirstName());
		String firstName = loginPage.getFirstName().GetAttribute("value");
		baseClass.waitForElement(loginPage.getLastName());
		String lastName = loginPage.getLastName().GetAttribute("value");
		loginPage.getEditprofilesave().waitAndClick(10);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("create new assignment");
		assignment.createAssignment(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Edit assignment -add reviewer");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.addReviewerInManageReviewerTab();
		baseClass.stepInfo("verify reviewer name format in distribute documents section");
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().waitAndClick(10);
		baseClass.waitForElement(assignment.getReviewerInDistributeTab(Input.rev1userName));
		if(assignment.getReviewerInDistributeTab(Input.rev1userName).isElementAvailable(10)) {
			String actualUser = assignment.getReviewerInDistributeTab(Input.rev1userName).getText().trim();
			baseClass.stepInfo("Actual username format: " +actualUser);
			String expected =lastName+", "+firstName+"("+Input.rev1userName+")";
			baseClass.stepInfo("Expected username format: " +expected);
			softAssert.assertEquals(actualUser, expected);
			softAssert.assertAll();
		}
		else {
			baseClass.failedStep("Added reviewer not available in distribute docs tab");
		}
		baseClass.passedStep("Reviewer name displayed in expected format");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 22/09/2022 TestCase Id:RPMXCON-53634
	 * Description :To verify that RMU is able to view Assingment Group and Assignments tree 
	 * in Assign/Unassign Document pop up wrt to Projects. 
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-53634",enabled = true, groups = { "regression" })
	public void verifyAssiignUnassignPopupAsRMU() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53634");
		baseClass.stepInfo("verify assign/Unassign popup wrt projects");
		String[] project = {Input.projectName,Input.additionalDataProject};
		String[] assignName ={"assign1"+Utility.dynamicNameAppender(),"assign2"+Utility.dynamicNameAppender()};
		String[] assignGroup ={"assignGroup1"+Utility.dynamicNameAppender(),"assignGroup2"+Utility.dynamicNameAppender()};
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("create assignment and group wrt to project");
		for(int i=0;i<project.length;i++) {
			baseClass.selectproject(project[i]);
			assignment.createAssgnGroup(assignGroup[i]);
			assignment.selectAssignmentGroup(assignGroup[i]);
			assignment.createAssignment(assignName[i], Input.codeFormName);
		}
		baseClass.selectproject(project[0]);
		baseClass.stepInfo("go to search and perform new search");
		sessionSearch.basicContentSearch("text");
		baseClass.stepInfo("drag the search result tile and select bulk assign");
		sessionSearch.addPureHit();
		sessionSearch.bulkAssign();
		assignment.verifyBulkAssignPopupWrtToProject(assignName[0],assignName[1]);
		baseClass.stepInfo("change the project and verify the bulk assign popup");
		baseClass.selectproject(project[1]);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch("text");
		baseClass.stepInfo("drag the search result tile and select bulk assign");
		sessionSearch.addPureHit();
		sessionSearch.bulkAssign();
		assignment.verifyBulkAssignPopupWrtToProject(assignName[1],assignName[0]);
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
		System.out.println("**Executed  Assignment_Regression22 .**");
	}
}
