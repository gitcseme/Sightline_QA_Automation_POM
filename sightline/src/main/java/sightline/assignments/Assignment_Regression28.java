package sightline.assignments;

import java.awt.AWTException;
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
import pageFactory.CodingForm;
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
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
	
	/**
	 * @author NA
	 * @Description :Verify that user can save/sort single or multiple coding form successfully .[RPMXCON-67511]
	 */
	@Test(description = "RPMXCON-67511", enabled = true, groups = { "regression" })
	public void verifySingleMultiCFSortExisProjCasCadeOFF()
			throws InterruptedException {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		listOfCFAfterSorting02.add(Input.codeFormName);
		String assignmentGroup01 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentGroup02 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-67511");
		base.stepInfo("Verify that user can save/sort single or multiple coding form successfully "
				+ "while editing existing assignment in a user created assignment group with cascade setting OFF for existing project");
		List<String> listOfCodingForm = codingForm.createCodingformBasedOnCondition(3);

		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup01, "No");
		base.passedStep("creating assignment Group : '" + assignmentGroup01 + "' with cascading setting OFF");

		base.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup01);
		assignment.createAssignmentFromAssgnGroup(assignmentName01, Input.codingFormName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		base.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		assignment.add2ReviewerAndDistribute();

		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		listOfCFAfterSorting01 = assignment.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		base.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);
        base.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = base
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		base.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		base.stepInfo("verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup02, "No");
		base.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup02);
		assignment.createAssignmentFromAssgnGroup(assignmentName02, listOfCFAfterSorting01.get(1));
		
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		base.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		assignment.add2ReviewerAndDistribute();

		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		List<String> listOfCFName = new ArrayList<String>();
		listOfCFName.add(Input.codeFormName);
		listOfCFAfterSorting02 = assignment.editExistingCodingForm(listOfCFName, Input.codeFormName, false);
		base.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);
		base.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = base
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		base.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		base.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.navigateToCodingFormPage();
		codingForm.DeleteMultipleCodingform(listOfCodingForm);
		base.passedStep("Verify that user can save/sort single or multiple coding form successfully while editing existing assignment "
				+ "in a user created assignment group with cascade setting OFF for existing project");
		loginPage.logout();		
	}
	
	/**
	 * @author NA
	 * @Description :Verify that user can save/sort single or multiple coding form successfully while creating new assignment in root assignment group for existing project[RPMXCON-67508]
	 */
	@Test(description = "RPMXCON-67508", enabled = true, groups = { "regression" })
	public void verifySingleMultiCFSortNewProjRootGRP() throws Exception {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		listOfCFAfterSorting02.add(Input.codeFormName);
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-67508");
		base.stepInfo("Verify that user can save/sort single or multiple coding form successfully "
				+ "while creating new assignment in root assignment group for existing project");
		codingForm.createCodingformBasedOnCondition(3);

		assignment.navigateToAssignmentsPage();
		base.stepInfo("creating assignment under Root Assignment group");
		assignment.createAssignment_withoutSave(assignmentName01, Input.codingFormName);
		listOfCFAfterSorting01 = assignment.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		base.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(5);
		
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		base.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName01);
		assignment.add2ReviewerAndDistribute();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);
        base.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = base
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		base.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		base.stepInfo("verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createAssignment_withoutSave(assignmentName02, listOfCFAfterSorting01.get(1));
		List<String> listOfCFName = new ArrayList<String>();
		listOfCFName.add(Input.codeFormName);
		listOfCFAfterSorting02 = assignment.editExistingCodingForm(listOfCFName, Input.codeFormName, false);
		base.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);
		
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		base.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName02);
		assignment.add2ReviewerAndDistribute();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);
		base.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = base
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		base.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		base.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();
		
		String assignmentName03 = "Assignment03" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithNewAssignment();
		assignment.createAssignment_fromAssignUnassignPopup(assignmentName03, Input.codingFormName);
		List<String> listOfCFAfterSorting03 = assignment.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		base.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(5);
		base.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName03);
		assignment.add2ReviewerAndDistribute();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName03);
        base.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown03 = base
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		base.compareListViaContains(listOfCFAfterSorting03, listOfCFInDocViewDropDown03);
		base.stepInfo("verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		base.passedStep("Verified - that user can save/sort single or multiple coding form successfully "
				+ "while creating new assignment in root assignment group for existing project");
		loginPage.logout();		
	}
	
	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :New Assignment - Keywords created by PAU and RMU part of the
	 *              security group should be selected for the assignment.
	 *              RPMXCON-54752
	 */
	@Test(description = "RPMXCON-54752", enabled = true, groups = { "regression" })
	public void verifyNewAssignmentKeywordsCreatedByPAUandRMUPartOfSecurityGrpSelectedForAssignment()
			throws AWTException, InterruptedException {
		keyPage = new KeywordPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String[][] keyword01 = { { "a", "Aqua" }, { "b", "Blue" }, { "c", "Gold" }, { "d", "Pink" }, { "e", "Aqua" } };
		String[][] keyword02 = { { "f", "Aqua" }, { "g", "Blue" }, { "h", "Gold" }, { "i", "Pink" }, { "j", "Aqua" } };

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-54752 Assignments");
		base.stepInfo(
				"New Assignment - Keywords created by PAU and RMU part of the security group should be selected for the assignment.");

		// creating keywords as PAU
		keyPage.navigateToKeywordPage();
		base.stepInfo("creating keywords as PAU");
		List<String> listOfKeywordGroup01 = keyPage.addMultipleKeywords(keyword01, true, Input.securityGroup);
		loginPage.logout();

		// creating keywords as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keyPage.navigateToKeywordPage();
		base.stepInfo("creating keywords as RMU");
		List<String> listOfKeywordGroup02 = keyPage.addMultipleKeywords(keyword02, true, Input.securityGroup);

		// Adding into list
		listOfKeywordGroup01.addAll(listOfKeywordGroup02);

		// creating assignment with keywords
		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);

		// verify that Add Keyword window should pop-up with all keywords associated the
		// security group (keywords created by both PAU and RMU) selected
		assignment.verifyKeywordsAvailabilityInAssignment(listOfKeywordGroup01);
		assignment.verifyAddedKeywordsChecked();
		base.passedStep(
				"verified that Add Keyword window pop-up with all keywords associated the security group (keywords created by both PAU and RMU) selected.");
		assignment.getSaveBtn().waitAndClick(5);
		base.stepInfo("assignment : '" + assignmentName + " created.");

		// performing bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// adding reviewers and distributing documents to reviewers
		base.stepInfo("adding reviewers and distributing documents to reviewers");
		assignment.add2ReviewerAndDistribute();

		// logOut
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from DashBoard and navigating to DocView
		base.stepInfo("selecting assignment from DashBoard and navigating to DocView.");
		assignment.SelectAssignmentByReviewer(assignmentName);

		// Keyword should be highlighted on the DocView
		docView.verifyKeywordsOnDocView(null,keyword01);
		base.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		base.passedStep("verified that Keywords are highlighted on the DocView.");
		docView.verifyKeywordsOnDocView(null,keyword02);
		base.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		base.passedStep("verified that Keywords are highlighted on the DocView.");

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
}
