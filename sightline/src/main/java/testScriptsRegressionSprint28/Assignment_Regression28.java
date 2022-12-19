package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
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

		boolean drawPool = assignment.verifyToggleEnableORDisabled(assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool");
		boolean keepEmail = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepEmailThreadTogetherToggle(),
				"keep Email Toogle");
		boolean keepFamily = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepFamiliesTogetherToggle(),
				"Keep Family Toogle");
		asserts.assertFalse(drawPool);
		asserts.assertFalse(keepEmail);
		asserts.assertFalse(keepFamily);
		asserts.assertAll();
		base.passedStep("verified - 'Keep families together' field enabled/disabled");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54404    
	 * @Description:Verify when RMU can input when count of left Docid in Reviewer
	 *                     batch.     
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
		base.textCompareEquals(rmuLeftTodo, expLeftTODO, "RMU LeftToDoCount Expected",
				"RMU LeftToDoCount Not Expected");
		base.textCompareEquals(revLeftTodo, expLeftTODO, "REV LeftToDoCount Expected", "REV LeftToDoCountNot Expected");
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
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		Dashboard dashBoard = new Dashboard(driver);

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
		agnmt.createAssignmentFromAssgnGroup(newAssign, Input.codingFormName);

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
		base.ValidateElementCollection_Presence(dashBoard.getReviewerslist(), "Reviewers in dashboard");
		List<String> Reviewers = new ArrayList<>();
		List<WebElement> elementList = dashBoard.getReviewerslist().FindWebElements();
		for (WebElement webElementNames : elementList) {
			String RevName = webElementNames.getText();
			Reviewers.add(RevName);
		}
		System.out.println(Reviewers);
		base.stepInfo("Reviewers in reviewer productivity widget" + Reviewers);
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

	/**
	 * @author
	 * @Description :Verify the functionality of Redistribution for uneven numbers
	 *              of Documents is following "off the top" logic.RPMXCON-54402
	 */
	@Test(description = "RPMXCON-54402", groups = { "regression" })
	public void verifyFunctionalityOfRedistributionForUnevenDocumentFollowingOffTopLogics()
			throws InterruptedException {

		List<String> listOfReviewers = new ArrayList<String>(Arrays.asList("PA", "RMU"));
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-54402");
		base.stepInfo(
				"verify the functionality of Redistribution for uneven numbers of Documents is following \"off the top\" logic.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search and bulk assign
		base.stepInfo("performing basic search and bulk assign.");
		int pureHitCount = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// creating assignment
		assignment.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		base.stepInfo("Assignment Created : " + assignmentName);

		// adding reviewers and distributing the documents to reviewers.
		base.stepInfo("adding reviewers and distributing the documents to reviewers.");
		int docCountToDistribute = assignment.oddOrEvenDocumentCountToDistribute(pureHitCount, "odd");
		assignment.distributeTheGivenDocCountToReviewer(Integer.toString(docCountToDistribute));
		assignment.addReviewers(listOfReviewers);

		// redistributing the documents to reviewers.
		base.stepInfo("redistributing the documents to reviewers.");
		assignment.selectReviewerAndClickRedistributeAction();
		base.waitForElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName));
		assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName).waitAndClick(5);
		base.waitForElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName));
		assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName).waitAndClick(5);
		assignment.getAssgn_Redistributepopup_save().waitAndClick(10);
		driver.Navigate().refresh();
		base.waitForElement(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		base.waitTime(6);

		// Verify the Redistribution calculation happened among Reviewer 2 and reviewer
		// 3 From reviewer 1 Batch.
		int leastDocCount = (int) (docCountToDistribute / 2);
		int expectedPaDocCount = leastDocCount;
		base.waitTime(3);
		int actualPaDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.pa1userName).getText());
		base.digitCompareEquals(expectedPaDocCount, actualPaDocCount,
				"Expected document count : '" + expectedPaDocCount + "' match with actual document count : '"
						+ actualPaDocCount + "'",
				"Expected document count : '" + expectedPaDocCount + "' doesn't match with actual document count : '"
						+ actualPaDocCount + "'");
		int expectedRmuDocCount = leastDocCount + 1;
		base.waitTime(3);
		int actualRmuDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.rmu1userName).getText());
		base.digitCompareEquals(expectedRmuDocCount, actualRmuDocCount,
				"Expected document count : '" + expectedRmuDocCount + "' match with actual document count : '"
						+ actualRmuDocCount + "'",
				"Expected document count : '" + expectedRmuDocCount + "' doesn't match with actual document count : '"
						+ actualRmuDocCount + "'");
		base.stepInfo(
				"Verified that Redistribution calculation happened among Reviewer 2 and reviewer 3 From reviewer 1 Batch.");

		// LogOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Assignment objects appear with a checkbox, and Assignment Group
	 *  objects appear with a folder icon in Assign Documents pop up.RPMXCON-54339
	 */
	@Test(description = "RPMXCON-54339", enabled = true, groups = { "regression" })
	public void verifyAssignmentObjectAppearWithCheckboxAndAssignmentGroupObjectAppearWithFolder() throws Exception {
		
		String assignmentName = "assignment"+Utility.dynamicNameAppender();
		String assignmentGroupName = "assignmentGroup"+Utility.dynamicNameAppender();
		
		System.out.println(assignmentName);
		System.out.println(assignmentGroupName);
		
		base.stepInfo("RPMXCON-54339");
		base.stepInfo("Verify that Assignment objects appear with a checkbox, and Assignment Group objects appear with a folder icon in Assign Documents pop up.");
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		//creating Assignment Group
		base.stepInfo("creating Assignment Group.");
		assignment.navigateToAssignmentsPage();
		assignment.createAssgnGroup(assignmentGroupName);
		//creating Assignment 
		base.stepInfo("creating Assignment.");
		assignment.createAssignment(assignmentName,Input.codingFormName);
		
		//performing basic content search 
		base.stepInfo("performing basic content search .");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		base.waitTime(2);
		
		// verify that Assignment Group object folder icon appear in Existing Assignment. 
		base.ValidateElement_Presence(assignment.getAssignmentGroupFolderIconInExistingAssignmentInBulkAssignTab(assignmentGroupName),"Assignment Group Folder Icon in Existing Assignment.");
		base.stepInfo("verified that Assignment Group object folder icon appear in Existing Assignment.");
		
		// verify that Assignment object checkbox appear in Existing Assignment.
		base.ValidateElement_Presence(assignment.getAssignmentCheckBoxInExistingAssignmentInBulkAssignTab(assignmentName),"Assignment CheckBox in Existing Assignment.");
		base.stepInfo("verified that Assignment object checkbox appear in Existing Assignment.");
		
		// verify that Assignment Group object folder icon appear in New Assignment.
		base.waitForElement(sessionSearch.getBulkAssign_NewAssignment());
		sessionSearch.getBulkAssign_NewAssignment().waitAndClick(5);
		base.ValidateElement_Presence(assignment.getAssignmentGroupFolderIconInNewAssignmentInBulkAssignTab(assignmentGroupName),"Assignment Group Folder Icon in New Assignment.");
		base.stepInfo("verified that Assignment Group object folder icon appear in New Assignment.");
		
		// deleting assignment
		assignment.navigateToAssignmentsPage();
		assignment.DeleteAssgnGroup(assignmentGroupName);
		// deleting assignment group
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		
		// logout
		loginPage.logout();
	}
	
}
