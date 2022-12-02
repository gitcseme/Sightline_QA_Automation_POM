package testScriptsRegressionSprint27;

import java.awt.AWTException;
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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AssignmentsRegression_27 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
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
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :New Assignment - Keywords created by PAU and RMU part of the
	 *              security group should be selected for the assignment.
	 *              RPMXCON-54752
	 */
	// @Test(description = "RPMXCON-54752", enabled = true, groups = { "regression"
	// })
	public void verifyNewAssignmentKeywordsCreatedByPAUandRMUPartOfSecurityGrpSelectedForAssignment()
			throws AWTException, InterruptedException {
		keyPage = new KeywordPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		List<String> keywordName01 = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e"));
		List<String> keywordName02 = new ArrayList<String>(Arrays.asList("f", "g", "h", "i", "j"));
		List<String> color01 = new ArrayList<String>(Arrays.asList("Aqua", "Blue", "Gold", "Pink", "Aqua"));

		// login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54752 Assignments");
		baseClass.stepInfo(
				"New Assignment - Keywords created by PAU and RMU part of the security group should be selected for the assignment.");

		// creating keywords as PAU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keywords as PAU");
		List<String> listOfKeywordGroup01 = keyPage.addMultipleKeywords(keywordName01, color01, true,
				Input.securityGroup);
		loginPage.logout();

		// creating keywords as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keywords as RMU");
		List<String> listOfKeywordGroup02 = keyPage.addMultipleKeywords(keywordName02, color01, false,
				Input.securityGroup);

		// Adding into list
		keywordName01.addAll(keywordName02);
		listOfKeywordGroup01.addAll(listOfKeywordGroup02);
		String[] allKeywordsGroup = new String[listOfKeywordGroup01.size()];
		listOfKeywordGroup01.toArray(allKeywordsGroup);

		// creating assignment with keywords
		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);

		// verify that Add Keyword window should pop-up with all keywords associated the
		// security group (keywords created by both PAU and RMU) selected
		assignment.verifyKeywordsAvailabilityInAssignment(allKeywordsGroup);
		assignment.verifyAddedKeywordsChecked();
		baseClass.passedStep(
				"verified that Add Keyword window pop-up with all keywords associated the security group (keywords created by both PAU and RMU) selected.");
		assignment.getSaveBtn().waitAndClick(5);
		baseClass.stepInfo("assignment : '" + assignmentName + " created.");

		// performing bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// adding reviewers and distributing documents to reviewers
		baseClass.stepInfo("adding reviewers and distributing documents to reviewers");
		assignment.add2ReviewerAndDistribute();

		// logOut
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from DashBoard and navigating to DocView
		baseClass.stepInfo("selecting assignment from DashBoard and navigating to DocView.");
		assignment.SelectAssignmentByReviewer(assignmentName);

		// Keyword should be highlighted on the DocView
		docView.verifyKeywordsOnDocView(keywordName01);
		baseClass.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		baseClass.passedStep("verified that Keywords are highlighted on the DocView.");

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete all keywords
		keyPage.navigateToKeywordPage();
		keyPage.deleteMultipleKeywords(listOfKeywordGroup01);

		// delete assignment
		assignment.deleteAssignment(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54340
	 * @Description: To Verify that if the Assignment name is long, the last three visible characters are an ellipsis
	 **/
	@Test(description = "RPMXCON-54340", enabled = true, groups = { "regression" })
	public void verifyAssignmentNameLong() throws Exception {
		AssignmentsPage assign = new AssignmentsPage(driver);
		SessionSearch session = new SessionSearch(driver);

		String assignmentName = "Assignment" + Utility.randomCharacterAppender(18);
		String expStatus = "ellipsis";

		baseClass.stepInfo("RPMXCON-54340");
		baseClass.stepInfo(
				"To Verify that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignmentName, Input.codingFormName);
		driver.waitForPageToBeReady();

		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();

		session.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(session.getBulkAssignAssignDocumentsButton());
		session.getBulkAssignAssignDocumentsButton().waitAndClick(5);
		baseClass.waitForElement(session.getSelectAssignmentExisting(assignmentName));
		String actStatusAssignDoc = session.getSelectAssignmentExisting(assignmentName).GetCssValue("text-overflow")
				.trim();
		System.out.println(actStatusAssignDoc);

		baseClass.waitForElement(session.getUnAssignRadioBtn());
		session.getUnAssignRadioBtn().waitAndClick(5);
		baseClass.waitForElement(session.getExistingAssignmentToUnAssign(assignmentName));
		String actStatusUnAssignDoc = session.getExistingAssignmentToUnAssign(assignmentName)
				.GetCssValue("text-overflow").trim();
		System.out.println(actStatusAssignDoc);

		if (expStatus.equalsIgnoreCase(actStatusAssignDoc) && expStatus.equalsIgnoreCase(actStatusUnAssignDoc)) {
			baseClass.passedStep("For long Assignment name, last three visible characters displayed as an ellipsis");
		} else {
			baseClass
					.failedStep("For long Assignment name, last three visible characters Not displayed as an ellipsis");
		}
		baseClass.passedStep(
				"Verified - that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54324
	 * @Description: To Verify that if the Assignment name is long, the last three visible characters are an ellipsis
	 **/
	@Test(description = "RPMXCON-54324", enabled = true, groups = { "regression" })
	public void verifyAssignmentNameLongDocs() throws Exception {
		AssignmentsPage assign = new AssignmentsPage(driver);
		SessionSearch session = new SessionSearch(driver);

		String assignmentName = "Assignment" + Utility.randomCharacterAppender(18);
		String expStatus = "ellipsis";

		baseClass.stepInfo("RPMXCON-54324");
		baseClass.stepInfo(
				"To Verify that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();

		assign.createAssignment(assignmentName, Input.codingFormName);
		driver.waitForPageToBeReady();

		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();

		session.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(session.getBulkAssignAssignDocumentsButton());
		session.getBulkAssignAssignDocumentsButton().waitAndClick(5);
		baseClass.waitForElement(session.getSelectAssignmentExisting(assignmentName));
		String actStatusAssignDoc = session.getSelectAssignmentExisting(assignmentName).GetCssValue("text-overflow")
				.trim();
		System.out.println(actStatusAssignDoc);

		baseClass.waitForElement(session.getUnAssignRadioBtn());
		session.getUnAssignRadioBtn().waitAndClick(5);
		baseClass.waitForElement(session.getExistingAssignmentToUnAssign(assignmentName));
		String actStatusUnAssignDoc = session.getExistingAssignmentToUnAssign(assignmentName)
				.GetCssValue("text-overflow").trim();
		System.out.println(actStatusAssignDoc);

		if (expStatus.equalsIgnoreCase(actStatusAssignDoc) && expStatus.equalsIgnoreCase(actStatusUnAssignDoc)) {
			baseClass.passedStep("For long Assignment name, last three visible characters displayed as an ellipsis");
		} else {
			baseClass
					.failedStep("For long Assignment name, last three visible characters Not displayed as an ellipsis");
		}
		baseClass.passedStep(
				"Verified - that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T Test case Id:RPMXCON-54400
	 * @throws InterruptedException 
	 * @Description :Verify the User is able to see all RMU , Reviewers mapped to
	 *              Assignment.
	 */
	@Test(description = "RPMXCON-54400", enabled = true, groups = { "regression" })
	public void VerifyMappedUsersInAssignment() throws InterruptedException {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54400 Assignments");
		baseClass.stepInfo("Verify the User is able to see all RMU , Reviewers mapped to Assignment.");

		String assignmentName = "AssgnName" + Utility.dynamicNameAppender();
		String[] users = { Input.rmu1userName, Input.rev1userName };

		baseClass.stepInfo("Navigating to Assignment Page.");
		assignment.navigateToAssignmentsPage();
		
		baseClass.stepInfo("Create Assignment");
		assignment.createAssignment(assignmentName, Input.codeFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		
		baseClass.stepInfo("Add Reviewers");
		assignment.assignReviewers(users);
		
		assignment.navigateToAssignmentsPage();
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verifying Mapped users in Assignment");
		int Size=assignment.getDashboadAssgn().size();
		System.out.println(Size);
		if(Size==2) {
			baseClass.ValidateElement_Presence(assignment.getAssgn_ManageRev_selectReviewer(Input.rmu1userName),"Mapped user");
			baseClass.ValidateElement_Presence(assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName),"Mapped user");
			baseClass.passedStep("The user can able to see the mapped RMU/Rev Users to the Assignment");
		}else {
			baseClass.failedStep("The user can't able to see the mapped RMU/Rev Users to the Assignment");
		}

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
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
