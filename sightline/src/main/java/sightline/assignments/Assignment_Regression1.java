package sightline.assignments;

import static org.testng.Assert.assertNotEquals;

import java.awt.AWTException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	AssignmentsPage agnmt;
	StringWriter sw;
	PrintWriter pw;
	String searchText;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		softAssertion=new SoftAssert ();
		searchText = Input.TallySearch;
	}

	/**
	 * @throws InterruptedException
	 * @description Verify that RMU can move the assignment from Non cascade parent
	 *              moving to cascade parent which is cascade child RPMXCON-54967
	 */
	@Test(description ="RPMXCON-54967", enabled = true, groups = { "regression" })
	public void validateMove_NonCascadeToCascadeGrpWithChild() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String nonCascadeAsgnGrpName = "NonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String nonCascadeAssgnName = "nonCascadeAssign" + Utility.dynamicNameAppender();
		String childCascadeAssgnGroup = "childCascadeAssgnGroup" + Utility.dynamicNameAppender();
		bc.stepInfo("Verify that RMU can move the assignment from Non cascade parent "
				+ "moving to cascade parent which is cascade child");
		bc.stepInfo("Test case Id: RPMXCON-54967");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(cascadeAsgnGrpName, cascadeSettings_yes);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName);

		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createCascadeNonCascadeAssgnGroup(childCascadeAssgnGroup, cascadeSettings_No);
		bc.stepInfo("Created A Child Assign group " + childCascadeAssgnGroup
				+ " under Parent Cascade Assignment group -" + cascadeAsgnGrpName + "");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(nonCascadeAsgnGrpName, cascadeSettings_No);
		bc.stepInfo("Created A NonCascade Assignment group -" + nonCascadeAsgnGrpName);

		agnmt.selectAssignmentGroup(nonCascadeAsgnGrpName);
		agnmt.createAssignmentFromAssgnGroup(nonCascadeAssgnName, Input.codeFormName);
		bc.stepInfo("Created Non Cascade Assignment-" + nonCascadeAssgnName + "under Non-Cascade assignment group-"
				+ nonCascadeAsgnGrpName + "");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		agnmt.dragAndDrop(nonCascadeAsgnGrpName, cascadeAsgnGrpName);
		agnmt.verifyMoveWarning();
		agnmt.validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(nonCascadeAssgnName, cascadeAsgnGrpName,
				nonCascadeAsgnGrpName, cascadeSettings_No);
		agnmt.DeleteChildAssgnGroup(childCascadeAssgnGroup, cascadeAsgnGrpName);
		agnmt.DeleteChildAssgnGroup(nonCascadeAsgnGrpName, cascadeAsgnGrpName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName);
		lp.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that RMU can move the assignment from Self cascade parent
	 *              moving to cascade parent RPMXCON-54968
	 */
	@Test(description ="RPMXCON-54968",enabled = true, groups = { "regression" })
	public void validateMove_CascadeToCascadeAssignGroup() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String parentCascadeAsgnGrpName = "parentCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assgnCascadeName = "cascadeAssign" + Utility.dynamicNameAppender();
		bc.stepInfo("Verify that RMU can move the assignment from Self cascade parent " + "moving to cascade parent");
		bc.stepInfo("Test case Id:RPMXCON-54968");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(parentCascadeAsgnGrpName, cascadeSettings_yes);
		bc.stepInfo("Created A Parent Cascade Assignment group -" + parentCascadeAsgnGrpName);

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(cascadeAsgnGrpName, cascadeSettings_yes);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName);

		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createAssignmentFromAssgnGroup(assgnCascadeName, Input.codeFormName);
		bc.stepInfo("Created  Cascade Assignment-" + assgnCascadeName + "under Cascade assignment group-"
				+ cascadeAsgnGrpName + "");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		agnmt.dragAndDrop(cascadeAsgnGrpName, parentCascadeAsgnGrpName);
		agnmt.verifyMoveWarning();
		agnmt.validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(assgnCascadeName, parentCascadeAsgnGrpName,
				cascadeAsgnGrpName, cascadeSettings_No);
		agnmt.DeleteChildAssgnGroup(cascadeAsgnGrpName, parentCascadeAsgnGrpName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(parentCascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * @throws InterruptedException
	 * @description Verify that RMU can move the assignment from Child cascade
	 *              parent moving to non cascade parent (RPMXCON-54969)
	 */
	@Test(description ="RPMXCON-54969",enabled = true, groups = { "regression" })

	public void validateMove_CascadeChildToNonCascadeAssignGroup() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String nonCascadeAsgnGrpName = "NonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String cascadeAssgnName = "cascadeAssign" + Utility.dynamicNameAppender();
		String childCascadeAssgnGroup = "childCascadeAssgnGroup" + Utility.dynamicNameAppender();
		bc.stepInfo("Verify that RMU can move the assignment from Child cascade"
				+ "   parent moving to non cascade parent");
		bc.stepInfo("Test case Id:RPMXCON-54969");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(cascadeAsgnGrpName, cascadeSettings_yes);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName);

		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createCascadeNonCascadeAssgnGroup(childCascadeAssgnGroup, cascadeSettings_No);
		bc.stepInfo("Created A Child Assign group " + childCascadeAssgnGroup
				+ " under Parent Cascade Assignment group -" + cascadeAsgnGrpName + "");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(nonCascadeAsgnGrpName, cascadeSettings_No);
		bc.stepInfo("Created A NonCascade Assignment group -" + nonCascadeAsgnGrpName);

		agnmt.selectChildAssgnGrpFromParentAssgnGroup(cascadeAsgnGrpName, childCascadeAssgnGroup);
		agnmt.createAssignmentFromAssgnGroup(cascadeAssgnName, Input.codeFormName);
		bc.stepInfo("Created A Cascade Assignment-" + cascadeAssgnName + "under Child Cascade assignment group-"
				+ cascadeAsgnGrpName + "");

		agnmt.selectChildAssgnGrpFromParentAssgnGroup(cascadeAsgnGrpName, childCascadeAssgnGroup);
		agnmt.dragAndDrop(childCascadeAssgnGroup, nonCascadeAsgnGrpName);
		agnmt.validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(cascadeAssgnName, nonCascadeAsgnGrpName,
				childCascadeAssgnGroup, cascadeSettings_yes);
		agnmt.DeleteChildAssgnGroup(childCascadeAssgnGroup, nonCascadeAsgnGrpName);
		agnmt.DeleteChildAssgnGroup(cascadeAsgnGrpName, nonCascadeAsgnGrpName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(nonCascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * @throws InterruptedException
	 * @description Verify that RMU can move the assignment from Child cascade
	 *              parent moving to cascade parent (RPMXCON-54970)
	 */
	@Test(description ="RPMXCON-54970",enabled = true, groups = { "regression" })
	public void validateMove_CascadeChildToCascadeAssignGroup() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String cascadeAsgnGrpName2 = "2CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String cascadeAssgnName = "cascadeAssign" + Utility.dynamicNameAppender();
		String childCascadeAssgnGroup = "childCascadeAssgnGroup" + Utility.dynamicNameAppender();
		bc.stepInfo("Verify that RMU can move the assignment from Child cascade"
				+ " parent moving to cascade parent (RPMXCON-54970)");
		bc.stepInfo("Test case Id:RPMXCON-54970");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(cascadeAsgnGrpName, cascadeSettings_yes);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName);

		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createCascadeNonCascadeAssgnGroup(childCascadeAssgnGroup, cascadeSettings_No);
		bc.stepInfo("Created A Child Assign group " + childCascadeAssgnGroup
				+ " under Parent Cascade Assignment group -" + cascadeAsgnGrpName + "");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(cascadeAsgnGrpName2, cascadeSettings_yes);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName2);

		agnmt.selectChildAssgnGrpFromParentAssgnGroup(cascadeAsgnGrpName, childCascadeAssgnGroup);
		agnmt.createAssignmentFromAssgnGroup(cascadeAssgnName, Input.codeFormName);
		bc.stepInfo("Created A Cascade Assignment-" + cascadeAssgnName + "under Child Cascade assignment group-"
				+ cascadeAsgnGrpName + "");
		agnmt.selectChildAssgnGrpFromParentAssgnGroup(cascadeAsgnGrpName, childCascadeAssgnGroup);
		agnmt.dragAndDrop(childCascadeAssgnGroup, cascadeAsgnGrpName2);
		agnmt.verifyMoveWarning();
		agnmt.validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(cascadeAssgnName, cascadeAsgnGrpName2,
				childCascadeAssgnGroup, cascadeSettings_No);
		agnmt.DeleteChildAssgnGroup(childCascadeAssgnGroup, cascadeAsgnGrpName2);
		agnmt.DeleteChildAssgnGroup(cascadeAsgnGrpName, cascadeAsgnGrpName2);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName2);
		lp.logout();
	}

	/**
	 * @author Jayanthi-Indium
	 * @throws InterruptedException
	 * @description Verify that RMU can move the assignment from Non cascade parent
	 *              to non cascade parent and user can edit the assignment from any
	 *              group(RPMXCON-54965)
	 */
	@Test(description ="RPMXCON-54965",enabled = true, groups = { "regression" })
	public void validateMove_nonCascadeToNonCascadeAssignGroup() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String noncascadeAsgnGrpName = "nonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String nonCascadeAsgnGrpName_1 = "2nonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assgnNonCascadeName = "nonCascadeAssign" + Utility.dynamicNameAppender();
		bc.stepInfo("Verify that RMU can move the assignment from Non cascade parent"
				+ " to non cascade parent and user can edit the assignment from any group");
		bc.stepInfo("Test case Id:RPMXCON-54965");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(noncascadeAsgnGrpName, cascadeSettings_No);
		bc.stepInfo("Created A NonCascade Assignment group -" + noncascadeAsgnGrpName);

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(nonCascadeAsgnGrpName_1, cascadeSettings_No);
		bc.stepInfo("Created A Non Cascade Assignment group -" + nonCascadeAsgnGrpName_1);

		agnmt.selectAssignmentGroup(nonCascadeAsgnGrpName_1);
		agnmt.createAssignmentFromAssgnGroup(assgnNonCascadeName, Input.codeFormName);
		bc.stepInfo("Created Non Cascade Assignment-" + assgnNonCascadeName + "under Non-Cascade assignment group-"
				+ nonCascadeAsgnGrpName_1 + "");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		agnmt.dragAndDrop(nonCascadeAsgnGrpName_1, noncascadeAsgnGrpName);
		agnmt.validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(assgnNonCascadeName, noncascadeAsgnGrpName,
				nonCascadeAsgnGrpName_1, cascadeSettings_yes);
		agnmt.DeleteChildAssgnGroup(nonCascadeAsgnGrpName_1, noncascadeAsgnGrpName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(noncascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that RMU can move the assignment from Non cascade parent
	 *              to cascade parent and user can edit the any
	 *              assignment(RPMXCON-54966)
	 * 
	 */
	@Test(description ="RPMXCON-54966",enabled = true, groups = { "regression" })
	public void validateMove_NonCascadeToCascadeAssignGroup() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String nonCascadeAsgnGrpName = "NonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assgnNonCascadeName = "NoncascadeAssign" + Utility.dynamicNameAppender();
		bc.stepInfo("Test case Id:RPMXCON-54966");
		bc.stepInfo("Verify that RMU can move the assignment from Non cascade parent"
				+ " to cascade parent and user can edit the assignment");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(cascadeAsgnGrpName, cascadeSettings_yes);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName);

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(nonCascadeAsgnGrpName, cascadeSettings_No);
		bc.stepInfo("Created A NonCascade Assignment group -" + nonCascadeAsgnGrpName);

		agnmt.selectAssignmentGroup(nonCascadeAsgnGrpName);
		agnmt.createAssignmentFromAssgnGroup(assgnNonCascadeName, Input.codeFormName);
		bc.stepInfo("Create d A Non Cascade Assignment-" + assgnNonCascadeName
				+ "under Non-Cascade Parent assignment group-" + nonCascadeAsgnGrpName + "");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.dragAndDrop(nonCascadeAsgnGrpName, cascadeAsgnGrpName);
		agnmt.verifyMoveWarning();
		agnmt.validateEditAssignmentBySelectingAssgnFromChildAssgnGrp(assgnNonCascadeName, cascadeAsgnGrpName,
				nonCascadeAsgnGrpName, cascadeSettings_No);
		agnmt.DeleteChildAssgnGroup(nonCascadeAsgnGrpName, cascadeAsgnGrpName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * @author Jayanthi-Indium
	 * @throws InterruptedException
	 * @description To verify that Sort by Metadata option set in Assignment group
	 *              set to Cascading options ON gets correctly propagated through
	 *              Bulk Assignment RPMXCON-54962
	 */

	@Test(description ="RPMXCON-54962",enabled = true, groups = { "regression" })
	public void VerifyTheCascadeSettingsInBulkAssign() throws InterruptedException, ParseException, IOException {
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String assignmentGroup = "assgnGrp" + Utility.dynamicNameAppender();
		bc.stepInfo(
				"Verify the RMU user able to create a cascading assignment group with sort by meta data option enabled and "
						+ "validate it propagates through bulk assign");
		bc.stepInfo("Test case Id:RPMXCON-54962");
		search = new SessionSearch(driver);
		agnmt.createCascadeAssgnGroupWithSortBymetadata(assignmentGroup);
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignWithNewAssgn(assignmentGroup);
		agnmt.validateMetadataOption(assignmentName, Input.codeFormName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(assignmentGroup);
		lp.logout();
	}

	/**
	 * @author Jayanthi-Indium
	 * @throws InterruptedException
	 * @description To verify the functionality of the Instructions while editing
	 *              existing Assignment.RPMXCON-53797
	 */
	@Test(description ="RPMXCON-53797",enabled = true, groups = { "regression" })

	public void validateInstructionPopUpInAssignments() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify the functionality of the Instructions while editing existing Assignment.");
		bc.stepInfo("Test case Id:RPMXCON-53797");
		String assignmentName = "AR1assignment" + Utility.dynamicNameAppender();
		search = new SessionSearch(driver);
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.ValidateInstructionPopUpInEditAndNewAssignment(Input.searchString1, "Edit");
		agnmt.addReviewerAndDistributeDocs(Input.rev3userName);
		lp.logout();
		lp.loginToSightLine(Input.rev3userName, Input.rev3password);
		bc.stepInfo("Logged in as review user");
		agnmt.verifyInstructionTextInDistDocs(assignmentName, Input.searchString1);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to view the configure button on Edit
	 *               Assignment Page with descending Metadata selection
	 *               (RPMXCON-54918)
	 */
	@Test(description ="RPMXCON-54918",enabled = true, groups = { "regression" })
	public void validateConfigureButtonInExistingAssignment() throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo("To verify that RMU is able to view the configure button on Edit"
				+ " Assignment Page with descending Metadata selection");
		bc.stepInfo("Test case Id:RPMXCON-54918");
		search = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		String PureHitCount = search.verifyPureHitsCount();
		bc.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyConfigureBtnAndSelectSortBymetaData(Input.metaDataName, "yes");
		agnmt.addReviewerAndDistributeDocs(Input.rev3userName);
		String docCount = agnmt.verifydocsCountInAssgnPage(assignmentName);
		lp.logout();
		lp.loginToSightLine(Input.rev3userName, Input.rev3password);
		bc.stepInfo("Logged in as reviewer user");
		agnmt.verifyTheMetaDataSortingAsPerRMUUser(assignmentName, Input.metaDataName);
		lp.logout();
		lp.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertEquals(PureHitCount, docCount);
		bc.passedStep("The total documents are equal as per the pure hit count");
		softAssertion.assertAll();
		lp.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to view the configure button on New
	 *               Assignment Page with descending Metadata selection
	 *               (RPMXCON-54917)
	 */

	@Test(description ="RPMXCON-54917",enabled = true, groups = { "regression" })
	public void validateConfigureButtonInNewAssignment() throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();

		bc.stepInfo("To verify that RMU is able to view the configure button on New Assignment"
				+ " Page with descending Metadata selection");
		bc.stepInfo("Test case Id:RPMXCON-54917");
		search = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		agnmt.verifyConfigureBtnAndSelectSortBymetaData(Input.metaDataName, "no");
		bc.stepInfo("Created assignment with name" + assignmentName);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		String PureHitCount = search.verifyPureHitsCount();	
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs(Input.rev3userName);
		String docCount = agnmt.verifydocsCountInAssgnPage(assignmentName);
		lp.logout();
		lp.loginToSightLine(Input.rev3userName, Input.rev3password);
		bc.stepInfo("Logged in as review user");
		agnmt.verifyTheMetaDataSortingAsPerRMUUser(assignmentName, Input.metaDataName);
		lp.logout();
		lp.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertEquals(PureHitCount, docCount);
		softAssertion.assertAll();
		bc.passedStep("The total documents are equal as per the pure hit count");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan	
	 * @description: To verify that RMU is able to sort the documents by selecting
	 *               System define metadata. RPMXCON-53821
	 */
	@Test(description ="RPMXCON-53821",enabled = true, groups = { "regression" })
	public void VerifySystemDefineMetaDataSorting() throws InterruptedException {
		bc.stepInfo("To verify that RMU is able to sort the documents by selecting System define metadata.");
		bc.stepInfo("Test case Id:RPMXCON-53821");
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		search = new SessionSearch(driver);
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		agnmt.Assgnwithdocumentsequence("FamilyMemberCount", "Ascending");
		bc.stepInfo("Created assignment with name" + assignmentName);	
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);	
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs(Input.rev3userName);
		lp.logout();
		lp.loginToSightLine(Input.rev3userName, Input.rev3password);
		bc.stepInfo("Logged in as review user");
		agnmt.verifySystemDefineMetaDataSorting_DocList(assignmentName);
		lp.logout();
		lp.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that on Instruction pop up formating toolbar is
	 *               provided.
	 */ 
	@Test(description ="RPMXCON-53916",enabled = true, groups = { "regression" })
	public void ValidateFormatingToolBarInInstructionPopup() throws InterruptedException, AWTException {
		bc.stepInfo("To verify that on Instruction pop up formating toolbar is provided.");
		bc.stepInfo("Test case Id:RPMXCON-53916");
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		agnmt.verifyFormattingToolBar_InstructionPopUp("No");
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Created assignment with name" + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyFormattingToolBar_InstructionPopUp("No");
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		agnmt.deleteSelectedAgnmt(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that if 'Show Docs Counts' is OFF  then document
	 *               counts is not displayed on the Assignment. (RPMXCON-54164)
	 */
	@Test(description ="RPMXCON-54164",enabled = true, groups = { "regression" })
	public void validateDocCountdisplay() throws InterruptedException {
		bc.stepInfo(
				"To verify that if 'Show Docs Counts' is OFF  then document counts is not displayed on the Assignment.");
		bc.stepInfo("Test case Id: RPMXCON-54164");
		agnmt.verifyDocCountDisplay();
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify the functionality of the Instructions while creating
	 *               New Assignment.. (RPMXCON-53796)
	 */
	@Test(description ="RPMXCON-53796",enabled = true, groups = { "regression" })
	public void validateInstructionPopUpInNewAssignment() throws InterruptedException {
		bc.stepInfo("To verify the functionality of the Instructions while creating New Assignment.");
		bc.stepInfo("Test case Id:RPMXCON-53796");
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		search = new SessionSearch(driver);
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		agnmt.ValidateInstructionPopUpInEditAndNewAssignment(Input.searchString1, "New");
		bc.stepInfo("Created assignment with name" + assignmentName);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);	
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs(Input.rev3userName);
		lp.logout();
		lp.loginToSightLine(Input.rev3userName, Input.rev3password);
		bc.stepInfo("Logged in as review user");
		agnmt.verifyInstructionTextInDistDocs(assignmentName, Input.searchString1);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that in Redistribute Documents pop up only those
	 *               Pending Reviewers are displayed which are associated to the
	 *               Assignments(RPMXCON-54764)
	 * 
	 */
	@Test(description ="RPMXCON-54764",enabled = true, groups = { "regression" })
	public void verifyThedistributedUserNotInRedistributeList()
			throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo(
				"To verify that in Redistribute Documents pop up only those Pending Reviewers are displayed which are associated to the Assignments");
		bc.stepInfo("Test case Id:RPMXCON54764");
		search = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		String Distributeduser = agnmt.addMultipleReviewersAndDistributeToOnereviewer(Input.rev3userName,Input.rmu3userName);
		System.out.println(Distributeduser);
		String redistributeUser = agnmt.VerifyUserNotInListAfterRedistributedDocs();
		System.out.println(redistributeUser);
		if (Input.rmu3userName != redistributeUser) {
			softAssertion.assertNotEquals(Input.rmu3userName, redistributeUser);
			bc.passedStep(
					"Sucessfully verified that in Redistribute Documents pop up only those Pending Reviewers are displayed which are "
							+ "associated to the Assignments");
		} else {
			bc.failedStep("Distributed Reviewer user appeared in redistribute document list.");
		}
		softAssertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to view option "View All Docs in
	 *               DocList"(RPMXCON-53803)
	 */
	@Test(description ="RPMXCON-53803",enabled = true, groups = { "regression" })
	public void verifyViewAllDocsinDocList() throws InterruptedException, IOException {
		bc.stepInfo("To verify that RMU is able to view option \"View All Docs in DocList\"");
		bc.stepInfo("Test case Id:RPMXCON-53803");
		agnmt.verifViewAllDocsinDocListBtnINActionDD();
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description: To verify that RMU can create New Assignments from Assign/Unassign Documents pop up (RPMXCON-53812)
	 */
	@Test(description ="RPMXCON-53812",enabled = true, groups = { "regression" })
	public void verifyToCreateNewAgnmtFromAssgnUnassgnDocPopUP() throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentGroup = "assgnGrp" + Utility.dynamicNameAppender();
		bc.stepInfo("Verify that RMU can create New Assignments from Assign/Unassign Documents pop up");
		bc.stepInfo("Test case Id:RPMXCON-53812");
		search = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(assignmentGroup,"No");
		int docs= search.basicContentSearch(searchText);
		System.out.println(docs);
		search.bulkAssignWithNewAssgn(assignmentGroup);
		agnmt.createNewAssignmentFromAssgnUnassgnPopUp(assignmentName, Input.codeFormName,assignmentGroup);
     	driver.scrollingToElementofAPage(agnmt.getAssgnGrp_Select(assignmentGroup));
     	driver.Navigate().refresh();
		agnmt.selectAssignmentGroup(assignmentGroup);
		agnmt.getSelectAssignment(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.waitForElement(agnmt.getAssignmentAction_EditAssignment());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(10);
		bc.stepInfo("Assignment edit option is clicked");
		agnmt.Assgnwithdocumentsequence(Input.metaDataName, "Ascending");	
		bc.passedStep("Meta data is selected in sort by metadata under document presentation sequence");
		agnmt.selectAssignmentGroup(assignmentGroup);
		bc.waitForElement(agnmt.getDocCountInAssgnPg(assignmentName));
		String docCount =  agnmt.getDocCountInAssgnPg(assignmentName).getText();
		System.out.println(docCount);
		try {
		softAssertion.assertEquals(docs,Integer.parseInt(docCount));
		bc.passedStep("The Docs assigned in search page to assignment and in the assigments grid view are equal");
		}catch(Exception e) {
			bc.failedStep("The Docs assigned in search page to assignment and in the assigments grid view are not equal");
		}
		bc.waitForElement(agnmt.getSelectAssignment(assignmentName));
		agnmt.Checkclickedstatus(assignmentName);
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentActionDropdown());
		agnmt.getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(agnmt.getAssignmentAction_DeleteAssignment());
		agnmt.getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(3);
		bc.VerifySuccessMessage("Assignment deleted successfully");
		bc.CloseSuccessMsgpopup();
		agnmt.DeleteAssgnGroup(assignmentGroup);
		softAssertion.assertAll();
		lp.logout();
	}
	
	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description: To verify that RMU is able to sort the documents by selecting option as Doc ID (RPMXCON-53826)

	 */
	@Test(description ="RPMXCON-53826",enabled = true, groups = { "regression" })
	public void verifyDocSortedByDocId() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify that RMU is able to sort the documents by selecting option as Doc ID.");
		bc.stepInfo("Test case Id:RPMXCON-53826");
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		search = new SessionSearch(driver);
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		driver.scrollingToBottomofAPage();
		agnmt.dragAndDropLiveSequence(" Email Threads");
    	agnmt.dragAndDropLiveSequence(" Near Duplicate Docs");
    	agnmt.dragAndDropLiveSequence(" Family Members");
    	bc.passedStep("Except DocId, all other options are moved from live sequence");
    	driver.scrollPageToTop();
        bc.waitForElement(agnmt.getAssignmentSaveButton());        
        agnmt.getAssignmentSaveButton().waitAndClick(5);
        bc.stepInfo("Created assignment with name"+assignmentName);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs(Input.rev3userName);
        lp.logout();
		lp.loginToSightLine(Input.rev3userName, Input.rev3password);
		bc.stepInfo("Logged in as reviewer user");
		agnmt.verifyTheDocIdListInReviwerPgDocList(assignmentName);
		lp.logout();
		lp.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}
	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description To verify the functionality of Standard methods for assigning the documents to one/multiple Assignment. 
	 *              (RPMXCON-53635)

	 */
	@Test(description ="RPMXCON-53635",enabled = true, groups = { "regression" })
	public void VerifyStandardMethods_assignDocs() throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentName3 = "AR1Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo("To verify the functionality of Standard methods for assigning the documents to one/multiple Assignment.");
		bc.stepInfo("Test case Id:RPMXCON-53635");
		search = new SessionSearch(driver);
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		agnmt.createAssignment(assignmentName2, Input.codeFormName);
		agnmt.createAssignment(assignmentName3, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName);
		bc.stepInfo("Created assignment with name" + assignmentName2);	
		bc.stepInfo("Created assignment with name" + assignmentName3);
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.assignAndVerifyDocsCountwithSamplemethod(assignmentName3,"Count of Selected Docs",null);
		bc.selectproject();
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.assignAndVerifyDocsCountwithSamplemethod(assignmentName,"Count of Selected Docs",assignmentName2);
		lp.logout();
	}
	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description: To verify the functionality of Standard methods for assigning the documents to one/multiple Assignment.
	 *               RPMXCON-53636
	 */
	@Test(description ="RPMXCON-53636",enabled = true, groups = { "regression" })
	public void VerifyPercentageMethods_assignDocs() throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentName3 = "AR1Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo("To verify the functionality of Percentage methods for assigning the documents to one/multiple Assignment.");
		bc.stepInfo("Test case Id:RPMXCON-53636");
		search = new SessionSearch(driver);
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName);
		agnmt.createAssignment(assignmentName2, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName2);
		agnmt.createAssignment(assignmentName3, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName3);	
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.assignAndVerifyDocsCountwithSamplemethod(assignmentName3,"Percentage",null);
		bc.selectproject();
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.assignAndVerifyDocsCountwithSamplemethod(assignmentName,"Percentage",assignmentName2);			
		softAssertion.assertAll();
		lp.logout();
	}
	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description: To verify that RMU cannot change the Metadata options if Cascading options are ON for Assignment Group (RPMXCON-53830)

	 */
	@Test(description ="RPMXCON-53830",enabled = true, groups = { "regression" })
	public void verifyCascadedSortByMetadataChangesInAssignment() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify that RMU cannot change the Metadata options if Cascading options are ON for Assignment Group");
		bc.stepInfo("Test case Id:RPMXCON-53830");
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentGroup = "assgnGrp" + Utility.dynamicNameAppender();
		agnmt.createCascadeAssgnGroupWithSortBymetadata(assignmentGroup);
		agnmt.selectAssignmentGroup(assignmentGroup);
		agnmt.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");		
		bc.waitForElement(agnmt.getAssgGrptionDropdown());
		agnmt.getAssgnGrp_Select(assignmentGroup).waitAndClick(10);
		driver.scrollPageToTop();
		agnmt.getAssgGrptionDropdown().waitAndClick(10);
		agnmt.getAssgnGrp_Edit().WaitUntilPresent();
		agnmt.getAssgnGrp_Edit().waitAndClick(20);
		bc.stepInfo("Assignment group edit option is clicked");
		agnmt.Assgnwithdocumentsequence(Input.metaDataName, "Ascending");
		driver.scrollPageToTop();
		bc.stepInfo("Meta data is selected in sort by metadata under document presentation sequence");
		agnmt.getSelectAssignment(assignmentName).waitAndClick(10);
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(agnmt.getAssignmentAction_EditAssignment());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(10);
		bc.stepInfo("Assignment edit option is clicked");
		agnmt.validatedTheSortByAndSortType(Input.metaDataName, "Ascending");	
		lp.logout();
	}
	
	/**
	 * @author Jayanthi.ganesan 
	 * 
	 * @description: To verify that RMU cannot change the Category options if Cascading options are ON for Assignment Group (RPMXCON-53829)

	 */
	@Test(description ="RPMXCON-53829",enabled = true, groups = { "regression" })
	public void verifyCascadedCategoryChangesInAssignment() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify that RMU cannot change the Category options if Cascading options are ON for Assignment Group");
		bc.stepInfo("Test case Id:RPMXCON-53829");
		String assignmentName = "AR1Assignment" + Utility.dynamicNameAppender();
		String assignmentGroup = "assgnGrp" + Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(assignmentGroup,"Yes");
		agnmt.selectAssignmentGroup(assignmentGroup);
		agnmt.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");		
		bc.waitForElement(agnmt.getAssgGrptionDropdown());
		bc.waitTillElemetToBeClickable(agnmt.getAssgnGrp_Select(assignmentGroup));	
		agnmt.getAssgnGrp_Select(assignmentGroup).waitAndClick(10);		
		driver.scrollPageToTop();
		agnmt.getAssgGrptionDropdown().waitAndClick(10);
		agnmt.getAssgnGrp_Edit().WaitUntilPresent();
		agnmt.getAssgnGrp_Edit().waitAndClick(20);
		bc.stepInfo("Assignment group edit option is clicked");
		agnmt.dragAndDropLiveSequence(" Email Threads");
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(10);
		bc.passedStep("The category option updated successfully");
		agnmt.getSelectAssignment(assignmentName).waitAndClick(10);
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(10);
		bc.waitForElement(agnmt.getAssignmentAction_EditAssignment());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(10);
		bc.stepInfo("Assignment edit option is clicked");
		agnmt.validateTheAvailableCriteriaInCategory(" Email Threads");
		lp.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		bc = new BaseClass(driver);
		sw = new StringWriter();
		pw = new PrintWriter(sw);
		lp.loginToSightLine(Input.rmu3userName, Input.rmu3password);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {			
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
			UtilityLog.info("Executed Assignment Regression1 class.");
			
	}

}
