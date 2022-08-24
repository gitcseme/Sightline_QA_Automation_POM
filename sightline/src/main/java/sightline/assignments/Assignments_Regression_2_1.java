package sightline.assignments;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression_2_1 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssertion;

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
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * @author
	 * @throws InterruptedException
	 * @Date: 07/13/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that RMU is able to view Assingment Group and
	 *              Assignments tree in Assign/Unassign Document pop up..
	 *              RPMXCON-53633
	 */
	@Test(description = "RPMXCON-53633", enabled = true, groups = { "regression" })
	public void verifyRmuAbleToViewAssignmentGroupAndAssignmentTreeInAssignOrUnAssignDocumentPopUp() {

		baseClass.stepInfo("Test case Id: RPMXCON-53633");
		baseClass.stepInfo(
				"To verify that RMU is able to view Assingment Group and Assignments tree in Assign/Unassign Document pop up.");

		// Login as Reviewer Manager
		baseClass.stepInfo(
				"1. Login to the application as RM User.  2. Goto Search --> New Search.  3. Enter text to search and click on Search.  4. Drag the Tile under Selected Results and from drop down select Bulk Assign.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);

		// performing bulk assign action
		sessionSearch.bulkAssign();

		// verifying the presence of Assign/Unassigned pop heading
		baseClass.ValidateElement_Presence(sessionSearch.getbulkassgnpopup(), "Assign/Unassigned pop");

		// verifying whether the Assign Documents is selected by Default
		String bgColorRbg = sessionSearch.getBulkAssignAssignDocumentsButton().GetCssValue("border-color");
		String bgColorHexa = baseClass.rgbTohexaConvertorCustomized(bgColorRbg, 4);
		System.out.println(bgColorHexa);
		baseClass.textCompareEquals(bgColorHexa, Input.progresBarColor,
				" Assign Document option is selected by default", " Assign Document option is not selected by default");

		// verifying whether the Existing Assignment Tab is selected by Default
		baseClass.printResutInReport(
				baseClass.ValidateElement_PresenceReturn(sessionSearch.getBulkAssignSelectedExistingAssignment()),
				"Existing Assignment tab is selected by Default.",
				"Existing Assignment tab is not selected by Default.", "Pass");

		// verifying the presence of tree of Assignment Group and Assignments
		baseClass.ValidateElementCollection_Presence(assignPage.getSelectcopyAssgnmToBulkAssign(),
				"Tree of Assignment Group and Assignments");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException
	 * @Date: 07/13/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that only assigned documents are displayed in Mini
	 *              DocList. RPMXCON-54045
	 */
	@Test(description = "RPMXCON-54045", enabled = true, groups = { "regression" })
	public void verifyThatOnlyAssignedDocumentsDisplayedInMiniDocList() throws InterruptedException {

		String assgngrpName = "assgnGrp" + Utility.dynamicNameAppender();
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-54045");
		baseClass.stepInfo("To verify that only assigned documents are displayed in Mini DocList");

		// Login as Reviewer Manager
		baseClass.stepInfo("**Step-1 Login to RPMX as RMU**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment Group
		baseClass.stepInfo("**Step-2 & 3 Go to Manage --> Assignment and Select Assignment Group from tree view**");
		assignPage.navigateToAssignmentsPage();
		assignPage.createCascadeAssgnGroupWithSortBymetadata(assgngrpName);
		baseClass.stepInfo("Assignment Group is Created");

		// creating assignment under newly created Assignment group
		assignPage.selectAssignmentGroup(assgngrpName);
		assignPage.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);

		// assign documents to created assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);

		// assigning reviewers to the Assignment and distributing the documents to the
		// reviewers
		baseClass.stepInfo(
				"**Step-4 Select Assignment from grid view which has documents and Reviewers assigned.    Select Edit Assignment from Action Dropdown**");
		assignPage.selectAssignmentGroup(assgngrpName);
		assignPage.getSelectAssignment(assignmentName).waitAndClick(5);
		assignPage.getAssignmentActionDropdown().waitAndClick(10);
		assignPage.assignmentActions("Edit");

		baseClass.stepInfo("**Step-5 Select Manage Reviewer tab**");
		assignPage.add2ReviewerAndDistribute();
		baseClass.passedStep("reviewers assigned to the Assignment and documents distributed to the reviewers");

		// verifying the count of document assigned to the reviewer with count of
		// documents listed in mini docList
		baseClass.stepInfo(
				"**Step-6 Select Reviewers from Grid View which has documents assigned.    From Action dropdown select View All Docs in DocView.    Verify that only assigned documents to reviewer should be displayed in Mini DocList.**");
		assignPage.Assignment_ManageRevtab_ViewinDocView();
		baseClass.passedStep(
				"verified that count of documents assigned to reviewer match with count of documents listed in Mini DocList");

		// logOut
		loginPage.logout();
	}
  
	/**
	 * @author Iyappan.Kasinathan
	 * @description Verifying the Live sequence in Assignment group and Assignment
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-49088",enabled = true, groups = { "regression" })
	public void verifyCascadedLiveSequenceOrderChangesInAssignment() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Verifying the Live sequence in Assignment group and Assignment");
		baseClass.stepInfo("Test case Id:RPMXCON-49088");
		String assignmentGroup = "assgnGrp" + Utility.dynamicNameAppender();
		SoftAssert sa = new SoftAssert();
		List<String> ListeBeforAlteredInAssgnGrp = new ArrayList<>();
		List<String> ListeAfterAlteredInAssgnGrp = new ArrayList<>();
		List<String> ListInAssgnmnt = new ArrayList<>();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGroup,"Yes");		
		ListeBeforAlteredInAssgnGrp =baseClass.getAvailableListofElements(assignPage.getLiveSequenceMetadatas());
		assignPage.dragAndDropLiveSequenceFromTopToBottom(" Email Threads"," DocID");
		ListeAfterAlteredInAssgnGrp =baseClass.getAvailableListofElements(assignPage.getLiveSequenceMetadatas());
		sa.assertNotEquals(ListeBeforAlteredInAssgnGrp, ListeAfterAlteredInAssgnGrp);
		baseClass.waitTillElemetToBeClickable(assignPage.SaveButton());
		assignPage.SaveButton().waitAndClick(10);
		assignPage.selectAssignmentGroup(assignmentGroup);
		baseClass.waitForElement(assignPage.getAssignmentActionDropdown());
		assignPage.getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignPage.getAssignmentAction_NewAssignment());
		baseClass.waitTillElemetToBeClickable(assignPage.getAssignmentAction_NewAssignment());
		assignPage.getAssignmentAction_NewAssignment().waitAndClick(20);
		ListInAssgnmnt =baseClass.getAvailableListofElements(assignPage.getLiveSequenceMetadatas());
		sa.assertEquals(ListeAfterAlteredInAssgnGrp, ListInAssgnmnt);
		sa.assertAll();
		baseClass.passedStep("The order of live sequence cascadedly reflected in assignment page");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.DeleteAssgnGroup(assignmentGroup);
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that validations are displayed if Mandatory fields are not entered on \"New Assignment\" page
	 * @throws InterruptedException
	 * @throws ParseException-
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-53603",enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldValidations() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("To verify that validations are displayed if Mandatory fields are not entered on \"New Assignment\" page");
		baseClass.stepInfo("Test case Id:RPMXCON-53603");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		baseClass.waitForElement(assignPage.getAssignmentActionDropdown());
		assignPage.getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignPage.getAssignmentAction_NewAssignment());
		baseClass.waitTillElemetToBeClickable(assignPage.getAssignmentAction_NewAssignment());
		assignPage.getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(assignPage.SaveButton());
		assignPage.SaveButton().waitAndClick(10);
		assignPage.validateErrorMessage(assignPage.getQB_AssignemntName_ErrorMSg(), "Name required");
		assignPage.validateErrorMessage(assignPage.getCodingForm_AssignemntName_ErrorMSg(), "Please select Coding Form");
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that alphanumeric are accepted in "Assignment Group Name" and "Assignment Name" fields
	 * @throws InterruptedException
	 * @throws ParseException-
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-53592",enabled = true, groups = { "regression" })
	public void verifyAgnmtNameAcceptsAlphaNumericValues() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("To verify that alphanumeric are accepted in \"Assignment Group Name\" and \"Assignment Name\" fields");
		baseClass.stepInfo("Test case Id:RPMXCON-53592");
		ArrayList<String> splCharacters = new ArrayList<String>(Arrays.asList("!", "@", "#","$","%","^","&","*","(",")"));
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String assignmentGroup1 = "agnmntGrp"+Utility.dynamicNameAppender();
		String assignment1 = "agnmnt"+Utility.dynamicNameAppender();
	    this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
	    assignPage.createCascadeNonCascadeAssgnGroup(assignmentGroup1,"Yes");
	    if(assignPage.getAssgnGrp_Select(assignmentGroup1).isElementAvailable(5)) {
	    	baseClass.passedStep("The assignment group added with alpha numeric naming convention created successfully");
	    }else {
	    	baseClass.failedStep("The assignment group doesn't accept alpha numeric values");
	    }
	    baseClass.stepInfo("Successfully verifed the assignment group accepted the alpha numeric values");
		for(String value: splCharacters) {
			String assignmentGroup = "agnmntGrp"+value;
		    this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		    assignPage.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGroup,"Yes");
		    assignPage.validateErrorMessage(assignPage.getQB_AssignemntName_ErrorMSg(), "Please enter an assignment name without using special characters");
		}
		baseClass.stepInfo("Successfully verifed the assignment group not accepting the special characters");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
	    assignPage.createAssignment(assignment1,Input.codingFormName);
	    this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
	    assignPage.selectAssignmentToView(assignment1);
	    if(assignPage.getSelectAssignment(assignment1).isElementAvailable(5)) {
	    	baseClass.passedStep("The assignment added with alpha numeric naming convention created successfully");
	    }else {
	    	baseClass.failedStep("The assignment name doesn't accept alpha numeric values");
	    }
	    baseClass.stepInfo("Successfully verifed the assignment name accepted the alpha numeric values");
		for(String value: splCharacters) {
			String assignment = "agnmnt"+value;
		    this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		    assignPage.createAssignment_withoutSave(assignment,Input.codingFormName);
		    assignPage.validateErrorMessage(assignPage.getQB_AssignemntName_ErrorMSg(), "Please enter an assignment name without using special characters");
		}
		assignPage.deleteAssignment(assignment1);
		baseClass.waitTime(3);
		assignPage.DeleteAssgnGroup(assignmentGroup1);
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that RMU can delete the assignment to which documents are associated.
	 * @throws InterruptedException
	 * @throws ParseException-
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-53609",enabled = true, groups = { "regression" })
	public void verifyRMUDeletesAssgnmnt() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("To verify that RMU can delete the assignment to which documents are associated.");
		baseClass.stepInfo("Test case Id:RPMXCON-53609");
		SessionSearch search = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignPage.add3ReviewerAndDistribute();
		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		baseClass.waitTillElemetToBeClickable(assignPage.getAssignmentsInreviewerPg());
		assignPage.getAssignmentsInreviewerPg().waitAndClick(10);
		baseClass.waitForElement(assignPage.getAssignmentsCompletedCountInreviewerPg(assignmentName));
		String docs = assignPage.getAssignmentsCompletedCountInreviewerPg(assignmentName).getText();
		int count = Integer.parseInt(docs);
		if(count>=0) {
			baseClass.passedStep("Completed coloumn value shows only numeric values");
		}else {
			baseClass.failedStep("Completed coloumn value not displayed the values as expected");
		}
		assignPage.deleteSelectedAgnmt(assignmentName);
		
	}

  /**
  	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @Description :To verify that validation is displayed if there is zero documents assigned 
	 * and RMU selects View All Docs In DocList.
	 */
	@Test(description = "RPMXCON-54050", enabled = true, groups = { "regression" })
	public void verifyValidations_ViewInDocList() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54050");
		baseClass.stepInfo("To verify that validation is displayed if there is zero documents "
				+ "assigned and RMU selects View All Docs In DocList.");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);

		// performing bulk assign action
		sessionSearch.bulkAssign();
		assignPage.assignmentCreationAsPerCf(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment and bulk assigned docs with name  -"+assgnName);
		String userToAdd[] = { Input.rev1userName };
		assignPage.assignReviewers(userToAdd);

		assignPage.getAssignment_ManageReviewersTab().waitAndClick(10);
		assignPage.getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(10);
		assignPage.selectActionsInManageRev(assignPage.getAssignmentActionManageTab_ViewinDocList());

		baseClass.VerifyWarningMessage("No documents available to do the selected action");
		baseClass.stepInfo("If there is zero documents "
				+ "assigned and RMU selects View All Docs In DocList  validation is displayed and verified.");
		assignPage.deleteAssgnmntUsingPagination(assgnName);
		// logout
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: To verify that RMU is able to add Add Reviewers pop on click of Add Reviewer button
	 */
	@Test(description ="RPMXCON-53621",enabled = true, groups = { "regression" })
	public void verifyRmuAbleToAddReviewersAndClosePopup() throws InterruptedException, AWTException {
		baseClass.stepInfo("To verify that RMU is able to add Add Reviewers pop on click of Add Reviewer button");
		baseClass.stepInfo("Test case Id: RPMXCON-53621");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		String userToAdd[] = { Input.rev1userName };
		assignPage.assignReviewers(userToAdd);
		assignPage.getAssignment_ManageReviewersTab().waitAndClick(10);
		if(assignPage.getAssgn_ManageRev_selectReviewer(Input.rev1userName).isElementAvailable(5)) {
			baseClass.passedStep("Selected reviewr from reviewer popup displayed in manage reviewers table");
		}else {
			baseClass.failedStep("Selected reviewr from reviewer popup not displayed in manage reviewers table");
		}
		assignPage.verifyUserInReviewerTab(Input.rmu1userName);
		assignPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: To verify that in "DOCUMENT PRESENTATION SEQUENCE" different sequence options are displayed as per selection of tab by RMU.
	 */
	@Test(description ="RPMXCON-53599",enabled = true, groups = { "regression" })
	public void verifySequeceOptionsDisplayedAsPerSelection() throws InterruptedException, AWTException {
		baseClass.stepInfo("To verify that in \"DOCUMENT PRESENTATION SEQUENCE\" different sequence options are displayed as per selection of tab by RMU.");
		baseClass.stepInfo("Test case Id: RPMXCON-53599");
		SoftAssert sa = new SoftAssert();
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		ArrayList<String> expectedLiveSequenceOrder = new ArrayList<String>(Arrays.asList("Email Threads", "Family Members", "Near Duplicate Docs","DocID"));
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignPage.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		sa.assertEquals(assignPage.getCategoryStatus().GetAttribute("aria-expanded"),"true","Category tab is not selected as default");
		baseClass.passedStep("DOCUMENT PRESENTATION SEQUENCE default tab is selected as \"Category\" as expected");
		List<String> actualLiveSequenceOrder = baseClass.getAvailableListofElements(assignPage.getLiveSequenceMetadatas());
		sa.assertEquals(expectedLiveSequenceOrder, actualLiveSequenceOrder,"The order of the live sequence are not displayed as expected");
		baseClass.passedStep("The order of the live sequence are displayed as expected");
		assignPage.sortBymetaDataBtn().waitAndClick(5);
		baseClass.waitTillElemetToBeClickable(assignPage.SelectMetadata());
		assignPage.SelectMetadata().waitAndClick(3);
		if(assignPage.SelectMetadataFromDropDown("DocFileName").isElementAvailable(5)) {
			baseClass.passedStep("Metadatas are displayed in the dropdown as expected");			
		}else {
			baseClass.failedStep("Metadatas are not displayed in the dropdown as expected");
		}
		sa.assertAll();
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the Default live sequence while creating the new assignment through bulk actions
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49087", enabled = true, groups = { "regression" })
	public void verifyDefLivSeqinAssignCrtThrBulkAction() throws InterruptedException {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
        
        baseClass.stepInfo("Test case Id: RPMXCON-49087 Assignments");
		baseClass.stepInfo(
				"Verify the Default live sequence while creating the new assignment through bulk actions");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);		
		search.basicContentSearch(Input.searchString1);
		search.bulkAssignWithNewAssignment();
		
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);		
		agnmt.VerifyLiveSequence();
		baseClass.stepInfo("The live sequence order Displayed Email, then Family Members, then Near Dupes and then DocID "
				+ "In New Assignment Page");
		
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);	
		baseClass.stepInfo("Assignment Created Through Bulk Action");
		
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Assignment Opened In Edit Assignment Page");
		agnmt.VerifyLiveSequence();
		baseClass.stepInfo("The Live Sequence Order Displays As We Expected In Edit Assignment Page");
		
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		baseClass.passedStep("Successfully Verified That - the Default live sequence while creating the new assignment through bulk actions");
		loginPage.logout();		
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify that in DOCUMENT PRESENTATION SEQUENCE  RMU is able to drag and drop the sequencing options in Category tab
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
    @Test(description = "RPMXCON-53745", enabled = true, groups = { "regression" })
	public void verifyRMUCanDDSeqOptionsInCategoryTab() throws InterruptedException, ParseException, IOException {
		String assgnGrp = "AR3assignGrp" + Utility.dynamicNameAppender();
		
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53745 Assignments");
		baseClass.stepInfo(
				"Verify that in DOCUMENT PRESENTATION SEQUENCE  RMU is able to drag and drop the sequencing options in Category tab");	
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		agnmt.navigateToAssignmentsPage();
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(assgnGrp, "No");
		driver.scrollingToElementofAPage(agnmt.CatogeryTabClicked());
		baseClass.ValidateElement_Presence(agnmt.CatogeryTabClicked(), "Category Tab is selected as Default");
		baseClass.ValidateElement_Presence(agnmt.getAssgn_DocSequence_SortbyMetadata(), "Sort By MetaData Tab");
		baseClass.stepInfo("Verified that DOCUMENT PRESENTATION SEQUENCE default tab is selected as Category and other tab Sort By Metadata are also available.");
		driver.scrollingToBottomofAPage();
		
		agnmt.dragAndDropLiveSequence(" Email Threads");
		baseClass.ValidateElement_Presence(agnmt.getunderAvailableCriteria(" Email Threads"), "Email Threads");
		baseClass.stepInfo("Email Threads Successfully Dragged From Live Sequence to Available Criteria");
		agnmt.dragAndDropLiveSeqfrmAvailableCrit(" Email Threads");
		baseClass.ValidateElement_Presence(agnmt.liveSequenceSorts(" Email Threads"), "Email Threads");
		baseClass.stepInfo("Email Threads Successfully Dragged From Available Criteria to Live Sequence");
		
		agnmt.dragAndDropLiveSequence(" Family Members");
		baseClass.ValidateElement_Presence(agnmt.getunderAvailableCriteria(" Family Members"), "Family Members");
		baseClass.stepInfo("Family Members Successfully Dragged From Live Sequence to Available Criteria");
		agnmt.dragAndDropLiveSeqfrmAvailableCrit(" Family Members");
		baseClass.ValidateElement_Presence(agnmt.liveSequenceSorts(" Family Members"), "Family Members");
		baseClass.stepInfo("Email Threads Successfully Dragged From Available Criteriae to Live Sequence");
		
		agnmt.dragAndDropLiveSequence(" Near Duplicate Docs");
		baseClass.ValidateElement_Presence(agnmt.getunderAvailableCriteria(" Near Duplicate Docs"), "Near Duplicate Docs");
		baseClass.stepInfo("Near Duplicate Docs Successfully Dragged From Live Sequence to Available Criteria");
		agnmt.dragAndDropLiveSeqfrmAvailableCrit(" Near Duplicate Docs");
		baseClass.ValidateElement_Presence(agnmt.liveSequenceSorts(" Near Duplicate Docs"), "Near Duplicate Docs");
		baseClass.stepInfo("Near Duplicate Docs Successfully Dragged From Available Criteriae to Live Sequence");
		
		agnmt.dragAndDropLiveSequence(" DocID");
		baseClass.ValidateElement_Presence(agnmt.getunderAvailableCriteria(" DocID"), "DocID");
		baseClass.stepInfo("DocID Successfully Dragged From Live Sequence to Available Criteria");
		agnmt.dragAndDropLiveSeqfrmAvailableCrit(" DocID");
		baseClass.ValidateElement_Presence(agnmt.liveSequenceSorts(" DocID"), "DocID");
		baseClass.stepInfo("DocID Successfully Dragged From Available Criteriae to Live Sequence");
		
		baseClass.passedStep("Verified that - in DOCUMENT PRESENTATION SEQUENCE  RMU is able to drag and drop the sequencing options in Category tab");
		loginPage.logout();
	}
    /**
     * @author Iyappan.Kasinathan
     * @description Verify that assignments functionality is working correctly and Properly
     * @throws Exception
     */
    @Test(description = "RPMXCON-54271", enabled = true, groups = { "regression" })
	public void verifyAssignFunctionalityWorkingProperly() throws Exception {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String keywordName = "key"+Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		KeywordPage	keyword = new KeywordPage(driver);
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);		
		baseClass.stepInfo("Test case Id: RPMXCON-54271");
		baseClass.stepInfo("Verify that assignments functionality is working correctly and Properly");
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithColor(keywordName, "Blue");
		
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);	
		
		baseClass.waitForElement(agnmt.getAssgnGrp_Create_DrawPoolCount());
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("2500");
		
		agnmt.configInstructionKeywordMDFieldinAssignPage(Input.searchString1, Input.metaDataName);
		agnmt.enableAllToogleUnderPresentationControl();	
		baseClass.stepInfo("All the toggles under 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' are Enabled and Green in Color");
		System.out.println("All the toggles under 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' are Enabled and Green in Color");
		baseClass.waitTime(2);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().Click();
		
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Documents Assigned To Assignments Successfully");	
	
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.distributeTheGivenDocCountToReviewer("10");	
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().Click();
		baseClass.VerifySuccessMessage("Assignment updated successfully");
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer user");
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Document Successfully Distributed to the User");	
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		baseClass.passedStep("Verified That - Assignments Functionality is Working Correctly and Properly");
		loginPage.logout();
	}
	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}
	
	 /**
     * @author Krishna
     * @description Verify that - After impersonation (Project Admin to RMU)- Application disallow special characters in New Assignments screen.
     * @throws Exception
     */
	
	@Test(description = "RPMXCON-54437", groups = { "regression" })
	public void verifyErrorMsgForSpclCharNewAssignScrPAtoRMU() throws InterruptedException {

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54437");
		baseClass.stepInfo(
				"Verify that - After impersonation (Project Admin to RMU)- Application disallow special characters in New Assignments screen.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As :" + Input.pa1userName);
		baseClass.impersonatePAtoRMU();

		agnmt.navigateToAssignmentsPage();
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			agnmt.selectAssignmentGroup("Root");
			agnmt.NavigateToNewEditAssignmentPage("New");
			baseClass.waitForElement(agnmt.getAssignmentName());
			agnmt.getAssignmentName().SendKeys(renameAssign);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			agnmt.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that After impersonation (Project Admin to RMU)- Application disallow special characters in New Assignments screen");
		loginPage.logout();
	}
	

	 /**
    * @author Krishna
    * @description Verify that - After impersonation (Project Admin to RMU)- Application disallow special characters in New Assignments screen.
    * @throws Exception
    */

	@Test(description = "RPMXCON-54438", groups = { "regression" })
	public void verifyErrorMsgForSpclCharEditAssignScrPAtoRMU() throws InterruptedException {

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54438");
		baseClass.stepInfo(
				"Verify that - After impersonation (Project Admin to RMU)- Application disallow special characters in Edit Assignments screen");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As :" + Input.rmu1userName);

		agnmt.createAssignment(assignmentName, Input.codingFormName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As :" + Input.pa1userName);
		baseClass.impersonatePAtoRMU();

		agnmt.navigateToAssignmentsPage();
		agnmt.editAssignment(assignmentName);

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			baseClass.waitForElement(agnmt.getAssignmentName());
			agnmt.getAssignmentName().SendKeys(renameAssign);
			driver.scrollPageToTop();
			baseClass.waitForElement(agnmt.getAssignmentSaveButton());
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			agnmt.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that After impersonation (Project Admin to RMU)- Application disallow special characters in Edit Assignments screen");
		loginPage.logout();
	}
	

	 /**
    * @author Krishna
    * @description Verify that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.
    * @throws Exception
    */

	@Test(description = "RPMXCON-54439", groups = { "regression" })
	public void verifyErrorMsgForSpclCharNewAssignScr() throws InterruptedException {

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54439");
		baseClass.stepInfo(
				"Verify that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);

		agnmt.navigateToAssignmentsPage();
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			agnmt.selectAssignmentGroup("Root");
			agnmt.NavigateToNewEditAssignmentPage("New");
			baseClass.copyandPasteString(renameAssign, agnmt.getAssignmentName());
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			agnmt.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
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
		System.out.println("**Executed  QuickBatchRegression2_1 Regression5**");
	}

}
