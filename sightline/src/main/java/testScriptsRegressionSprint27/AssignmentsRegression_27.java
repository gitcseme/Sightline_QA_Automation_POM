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
	 * @Description: To Verify that if the Assignment name is long, the last three
	 *               visible characters are an ellipsis
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
	 * @Description: To Verify that if the Assignment name is long, the last three
	 *               visible characters are an ellipsis
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


	/**
	 * @author
	 * @Description : Validate Assignment classification values on Assignment
	 *              screen. [RPMXCON-54508]
	 */
	@Test(description = "RPMXCON-54508", enabled = true, groups = { "regression" })
	public void validateAssignmentClassificationValuesOnAssignmentScreen() {

		List<String> listOfClassifictionOptions = new ArrayList<String>(Arrays.asList("1LR", "2LR", "QC"));
    	baseClass.stepInfo("Test case Id: RPMXCON-54508 Assignments.");
		baseClass.stepInfo("Validate Assignment classification values on Assignment screen.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// navigating to create new assignment page
		baseClass.stepInfo("navigating to Create new Assignment page.");
		assignment.navigateToAssignmentsPage();
		assignment.NavigateToNewEditAssignmentPage("new");

		// verify that Classification list should contain following values 1LR, 2LR and
		// QC
		assignment.verifyClassificationOptionsFromAssignmentPage(listOfClassifictionOptions);
		baseClass.passedStep("verified that Classification list contain following values 1LR, 2LR and QC.");

		// logOut
		loginPage.logout();
	}


	/**
	 * @author NA Testcase No:RPMXCON-54325
	 * @Description:Verify that the full Assignment name appears in a mouse over tool tip, and the same is true of Assignment Groups
	 **/
	@Test(description = "RPMXCON-54325", enabled = true, groups = { "regression" })
	public void verifyFullAssignmentNameToolTip() throws Exception{	
	    AssignmentsPage assign = new AssignmentsPage(driver);
	    SessionSearch session = new SessionSearch(driver);
	    SoftAssert asserts = new SoftAssert();
	
	    String assignMentGRP = "AssignGRP" + Utility.randomCharacterAppender(18);
        String assignmentName = "Assignment" + Utility.randomCharacterAppender(18);
	    String expStatus = "ellipsis";   
	
        baseClass.stepInfo("RPMXCON-54325");
	    baseClass.stepInfo("Verify that the full Assignment name appears in a mouse over tool tip, and the same is true of Assignment Groups");
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
        assign.navigateToAssignmentsPage();
        driver.waitForPageToBeReady();
        assign.createAssgnGroup(assignMentGRP);
        driver.waitForPageToBeReady();
	    assign.createAssignment(assignmentName , Input.codingFormName);	
	    driver.waitForPageToBeReady();
	    
	    session.navigateToSessionSearchPageURL();
	    driver.waitForPageToBeReady();
	    session.basicContentSearch(Input.testData1);
	    driver.waitForPageToBeReady();	
	    session.bulkAssignExisting(assignmentName);
	    driver.waitForPageToBeReady();
	
	    session.bulkAssign();
	    driver.waitForPageToBeReady();
 	    baseClass.waitForElement(session.getSelectAssignmentExisting(assignmentName)); 
	    String actStatusAssignDoc = session.getSelectAssignmentExisting(assignmentName).GetCssValue("text-overflow").trim();
	    System.out.println(actStatusAssignDoc);
	    
	    baseClass = new BaseClass(driver);
	    baseClass.moveWaitAndClick(session.getSelectAssignmentExisting(assignmentName), 15);
		baseClass.waitTime(2);
	    String actStatusPopAssign = session.getExisAssignPOPOVER().GetCssValue("text-overflow").trim();
	    System.out.println(actStatusPopAssign);
	    
	    baseClass.waitForElement(session.getSelectAssignmentExisting(assignMentGRP)); 
		baseClass.mouseHoverOnElement(session.getSelectAssignmentExisting(assignMentGRP));
		baseClass.waitTime(2);
	    String actStatusPopAssignGRP = session.getExisAssignPOPOVER().GetCssValue("text-overflow").trim();
	    System.out.println(actStatusPopAssignGRP);
	    
	    asserts.assertEquals(actStatusAssignDoc, expStatus);
	    asserts.assertNotEquals(actStatusPopAssign, expStatus);
	    asserts.assertNotEquals(actStatusPopAssignGRP, expStatus);
		asserts.assertAll();
	    baseClass.passedStep("Verified - that the full Assignment name appears in a mouse over tool tip, and the same is true of Assignment Groups");
	      loginPage.logout();
	}

	

	/**
	 * @author
	 * @Description :"Verify when user selects \"Inclusive Emails\"
	 *  sampling method from Assign Documents pop up."RPMXCON-54343
	 */
	
	@Test(description = "RPMXCON-54343", enabled = true, groups = { "regression" })
    public void verifyUserSelectInclusiveEmailsSamplingMethodFromAssignDocumentsPopUp() {
		
		String samplingMethodOption = "Inclusive Email";
		baseClass.stepInfo("Test case Id: RPMXCON-54343 Assignments.");
		baseClass.stepInfo("Verify when user selects \"Inclusive Emails\" sampling method from Assign Documents pop up.");
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		// Performing Search and drag the result to shopping cart 
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.addPureHit();
		//Performing Bulk Assign Action
		sessionSearch.bulkAssign();
		
		//Select Sampling Method as 'Inclusive Emails' From  'Existing Assignment' tab
		baseClass.stepInfo("Selecting Sampling Method as 'Inclusive Emails' From  'Existing Assignment' tab.");
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(samplingMethodOption,true,false);
		baseClass.passedStep("Verified that On selection of this sampling method-> Input field is no longer present in 'Existing Assignment' tab.");
		
		
		//Select Sampling Method as 'Inclusive Emails' From  'New Assignment' tab
		baseClass.stepInfo("Selecting Sampling Method as 'Inclusive Emails' From  'New Assignment' tab.");
	    driver.Navigate().refresh();
	    sessionSearch.bulkAssign();
		baseClass.waitForElement(sessionSearch.getAssgn_NewAssignmnet());
		sessionSearch.getAssgn_NewAssignmnet().waitAndClick(5);
		baseClass.waitTime(4);
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethodFromNewAssignmentab(samplingMethodOption,true);
		baseClass.passedStep("Verified that On selection of this sampling method-> Input field is no longer present in 'New Assignment' tab.");	
		
		// logOut
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:6/12/2022 RPMXCON-54219
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Draw from pool - Verify the display of 'Draw from Pool' Action
	 *              when 'Draw From Pool' option is enabled in Assignment with draw
	 *              from pool (i.e 20) is equal to the total docs assigned (i.e. 20)
	 *              and 1 document is completed.
	 */
	@Test(description = "RPMXCON-54219", enabled = true, groups = { "regression" })
	public void verifyDrawFromPoolIsNotDisplayedComplteCodingForm() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54219");
		baseClass.stepInfo(
				"Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is equal to the total docs assigned (i.e. 20) and 1 document is completed.");

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
    	baseClass.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");
		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// distributing docs
		agnmt.addReviewerInManageReviewerTab();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		agnmt.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docView.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(Input.codingFormName);
		docView.codingFormSaveButton();
		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		if (!agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(3)) {
			baseClass.passedStep("Draw pool link is Not displayed");
		} else {
			baseClass.failedStep("Draw pool link is displayed");
		}
		if (agnmt.getrev_assgnprogress(assignmentName).isDisplayed()) {
			baseClass.passedStep("progress bar is displayed with 1 document completed and 19 left to do");
		} else {
			baseClass.failedStep("progress bar is not displayed");
		}
		
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:6/12/2022 RPMXCON-54395
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the word Redistribute in all the pop up.
	 */
	@Test(description = "RPMXCON-54395", enabled = true, groups = { "regression" })
	public void verifyRedistributeAllPopup() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54395");
		baseClass.stepInfo(
				"Verify the word Redistribute in all the pop up.");
		AssignmentsPage assignment = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");
		
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create Assignment");
		assignment.createAssignmentNew(assignmentName, Input.codeFormName);
	
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).ScrollTo();
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(5);
		assignment.getAssgn_ManageRev_Action().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.waitForElement(assignment.getAssgn_RedistributeDoc());
		assignment.getAssgn_RedistributeDoc().waitAndClick(10);
		driver.waitForPageToBeReady();
		String CompareString="Redistribute Documents";
		String ActualString = assignment.getMoveNotificationMessage().getText();
		System.out.println(ActualString);
		baseClass.compareTextViaContains(ActualString, CompareString, "Redistribute document is displayed in Belly band message",
				"Redistribute document is not displayed in Belly band message");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:6/12/2022 RPMXCON-54399
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the 'Keep families together when distributing' has a contextual help icon in Redistribute Documents pop up.
	 */
	@Test(description = "RPMXCON-54399", enabled = true, groups = { "regression" })
	public void verifyRedistributeKeepFamiliesDistributingHelpIconDisplay() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54399");
		baseClass.stepInfo(
				"Verify the 'Keep families together when distributing' has a contextual help icon in Redistribute Documents pop up");
		AssignmentsPage assignment = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");
		
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create Assignment");
		assignment.createAssignmentNew(assignmentName, Input.codeFormName);
		
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).ScrollTo();
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(5);
		assignment.getAssgn_ManageRev_Action().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.waitForElement(assignment.getAssgn_RedistributeDoc());
		assignment.getAssgn_RedistributeDoc().waitAndClick(10);
		baseClass.getYesBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTime(8);
		if(assignment.getKeepFamiliesHelpIcon().isElementAvailable(3)) {
			baseClass.passedStep("The help icon is displayed for 'Keep families together when distributing' in Redistribute Documents pop up");
		}else {
			baseClass.failedStep("The help icon is not displayed for Redistribute Documents pop up");
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
