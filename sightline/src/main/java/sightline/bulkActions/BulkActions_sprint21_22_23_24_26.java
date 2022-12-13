package sightline.bulkActions;

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
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CodingForm;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_sprint21_22_23_24_26 {

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
	
	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object [][]users = {{ Input.rmu1userName, Input.rmu1password }, {Input.pa1userName, Input.pa1password } };
		return users;
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54487
	 * @Description:verify that in Bulk Assign modal should not display 'InProgress' warning message
	 **/
	@Test(description = "RPMXCON-54487", enabled = true, groups = { "regression" })
	public void verifyBulkAssignModelWarning() throws Exception {
		
		baseClass.stepInfo("RPMXCON-54487");
		baseClass.stepInfo("To verify that in Bulk Assign modal should not display 'InProgress' warning message");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		
		sessionSearch.navigateToSessionSearchPageURL();	
		sessionSearch.basicContentSearch(Input.testData1);		
		sessionSearch.bulkAssign();
		
		if (!baseClass.getWarningsMsgHeader().isElementAvailable(1)) {
			baseClass.passedStep("Warning Not Occuered When Total Count in INPROGRESS As Expected");
		} else {
			baseClass.failedStep(baseClass.getWarningMsg().getText() + " is occurred");
		}
		baseClass.passedStep("Verified - that in Bulk Assign modal should not display 'InProgress' warning message");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-65743
	 * @Description: Verify Warning message to validate for conflicts during bulk tagging from Saved search
	 **/
	@Test(description = "RPMXCON-65743", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyValidateConflictSS(String username, String password) throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		SavedSearch search = new SavedSearch(driver);
		TallyPage tally  = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		
		String tagname1 = "ATag1" + Utility.dynamicNameAppender();
		String tagname2 = "ATag1" + Utility.dynamicNameAppender();
		String cfName = "Coding" + Utility.dynamicNameAppender();
		String searchName = "Search" + Utility.dynamicNameAppender();
		String expLongText = "Warning: Bulk tagging/untagging may violate coding logic and create coding conflicts";
		String expShortText = "Below is a Tally Report of all your selected documents to be bulk tagged/untagged, reported "
				              + "by the tags each document presently carries in the coding form(s). In this report you can see where coding "
				              + "conflicts may arise. Please confirm that no coding conflicts exist with the set of documents you are about to bulk tag/untag.";
		
		baseClass.stepInfo("RPMXCON - 65743");
		baseClass.stepInfo("Verify Warning message to validate for conflicts during bulk tagging from Saved search");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname1);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);	
		coding.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		coding.saveCodingForm2TagsWithGrpAssociat(cfName, tagname1, tagname2);
		loginPage.logout();
		
		loginPage.loginToSightLine(username, password);
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		String actLongText = sessionSearch.getPopupHeader().getText();
	    String actShortTex = sessionSearch.getPopupText().getText();
		asserts.assertEquals(expLongText, actLongText);
		asserts.assertEquals(expShortText, actShortTex);
		asserts.assertAll();
		
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		asserts.assertTrue(sessionSearch.getTallyContinue().Enabled());
		asserts.assertTrue(sessionSearch.getNoBtn().Enabled());
		asserts.assertAll();
		
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if(sessionSearch.getNoBtn().Enabled()) {
			sessionSearch.getNoBtn().waitAndClick(5);
			baseClass.passedStep("Cancel Button Enabled And Successfully Clicked in POPUP");
		} else {
			baseClass.failedStep("Cancel ButtonDisabled");
		}
		driver.waitForPageToBeReady();
		asserts.assertTrue(search.getSelectWithName(searchName).Visible());
		asserts.assertAll();
	
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		sessionSearch.getTallyContinue().waitAndClick(5);
		baseClass.verifyMegaPhoneIconAndBackgroundTasks(true, true);	
		baseClass.waitForElement(batchPrint.getBatchId(1));
		String idValue = batchPrint.getBatchId(1).getText();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		asserts.assertNotNull(status);
		asserts.assertAll();
	
		search.savedSearch_Searchandclick(searchName);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if(!sessionSearch.getReportTotalCount().isElementAvailable(1) ){
			asserts.assertFalse(sessionSearch.getGoToTallyReport().Enabled());
			asserts.assertAll();
			baseClass.passedStep("Button Disabled As Expected");
		} else {
			baseClass.failedStep("Not as Expected");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getReportTotalCount());		
		String expTotalCount = sessionSearch.getReportTotalCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String expMetadata = sessionSearch.getReportMetadat().getText();
		baseClass.waitForElement(sessionSearch.getGoToTallyReport());
		sessionSearch.getGoToTallyReport().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tally.getTallyCount());
		String actTotalCount = tally.getTallyCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String actMetadata = sessionSearch.getReportMetadat().getText();	
		asserts.assertEquals(expTotalCount, actTotalCount);
		asserts.assertEquals(expMetadata, actMetadata);
		asserts.assertAll();
		baseClass.passedStep("Verify Warning message to validate for conflicts during bulk tagging from Saved search");
		loginPage.logout();
			
	}
	
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-54484", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsSelectedFluctuation() throws InterruptedException, ParseException, IOException {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo(
				"Verifying the fluctuation of document count for " + "all the bulk actions in Manage Assignments");
		baseClass.stepInfo("Test case Id: RPMXCON-54484");

		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String folderTagNAme = "folderTag" + Utility.dynamicNameAppender();

		String count = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.bulkAssign();
		assignment.FinalizeAssignmentAfterBulkAssign();
		assignment.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.scrollingToElementofAPage(assignment.getAssignmentSaveButton());
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);

		assignment.navigateToAssignmentsPage();
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(assignment.getSelectAssignment(assignmentName));
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Tag All Option");
		assignment.Bulk_TagAll_FolderAll(true);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme, count);

		assignment.navigateToAssignmentsPage();
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(assignment.getSelectAssignment(assignmentName));
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Folder All Option");
		assignment.Bulk_TagAll_FolderAll(false);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme, count);

		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	
	/**
	 * @author Raghuram.A
	 * @Date: 16/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that while the 'Total Selected Count' is In Progress,
	 *              Continue button should be disabled. RPMXCON-54489
	 */
	@Test(description = "RPMXCON-54489", groups = { "regression" }, enabled = true)
	public void verifyBulkAssignCountLoadActions() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54489 Bulk Actions");
		baseClass.stepInfo(
				"To verify that while the 'Total Selected Count' is In Progress, Continue button should be disabled.");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing searching and saving it in newly created node
		baseClass.stepInfo(
				"**Step-2 Go to Search   Search by any keyword  Select Pure hits and add into shopping cart**");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		// Add purehit and perform Bulk Action
		baseClass.stepInfo("**Step-3 Select Bulk Assign action**");
		sessionSearch.checkAddPureHit();
		sessionSearch.performBulkActionM("Bulk Assign");
		driver.waitForPageToBeReady();

		// 'Total Selected Document' count is in progress Select Existing assignment
		baseClass.stepInfo("**Step-4 'Total Selected Document' count is in progress    Select Existing assignment**");
		baseClass.stepInfo("Verify Continue button is in Disabled state");
		sessionSearch.verifyContinuButtonIsStatus("Disabled", "existingassignment"); // newassignmentdiv

		// Verify Continue button is disabled from Unassign document section
		baseClass.stepInfo("**Step-5 Verify Continue button is disabled from Unassign document section**");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		sessionSearch.performBulkActionM("Bulk Assign");
		driver.waitForPageToBeReady();
		sessionSearch.unassignClick();

		baseClass.stepInfo("Verify Continue button is in Disabled state in Unassign");
		sessionSearch.verifyContinuButtonIsStatus("Disabled", "divUnAssignDocuments"); // newassignmentdiv

		// logout
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A
	 * @Date: 16/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that in Bulk Assign, the Parent level sampling method
	 *              is working as expected. RPMXCON-54463
	 */
	@Test(description = "RPMXCON-54463", groups = { "regression" }, enabled = true)
	public void verifyParentDocCountDisplayedAsExpected() throws InterruptedException {

		AssignmentsPage assign = new AssignmentsPage(driver);
		DocListPage docList = new DocListPage(driver);

		String finalCount;
		String docCount;
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		String assignName = "assignName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-54463 Bulk Actions");
		baseClass.stepInfo("To verify that in Bulk Assign, the Parent level sampling method is working as expected");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating new Search Group under My Saved Search
		String nodeName = savedSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// Assignment creation
		baseClass.stepInfo("**Step-2 Create new assignment from assignments**");
		assign.createAssignment(assignName, Input.codingFormName);

		// performing searching and saving it in newly created node
		baseClass.stepInfo("**Step-3 Go to saved searches and click on assign to bulk assign**");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, nodeName);

		// Navigate to SavedSearch Page
		savedSearch.navigateToSSPage();
		savedSearch.selectNode1(nodeName);

		// Bulk Assign
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked Assign Icon");

		// Bulk Assign via SavedSearch
		baseClass.stepInfo("**Step-4 Select assignment name and sampling method as Parent level and finalize**");
		finalCount = assign.assignwithSamplemethod(assignName, "Parent Level Docs Only", null);

		baseClass.stepInfo("**Step-5 Go to assignments and select the assignment  created**");
		assign.Viewindoclistfromassgn(assignName);
		driver.waitForPageToBeReady();

		// Parent doc count verification
		baseClass.stepInfo(
				"**Step-6 Navigate to doc list of assignment and check if the parent level docs are assigned**");
		docCount = docList.verifyingDocCount();

		// Doc count comparision
		baseClass.stepInfo("Verify that count displayed in the \"Total\" are the count of Parent Documents only.");
		baseClass.textCompareEquals(finalCount, docCount, "Only Parent Level documents are listed in the doclist page",
				"Other than parent level docs are listed - Count mismatches");

		// Delete created Node
		baseClass.stepInfo("Initiating delete nodes");
		savedSearch.navigateToSavedSearchPage();
		savedSearch.deleteNode(Input.mySavedSearch, nodeName);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54461", enabled = true, groups = { "regression" })
	public void verifyBulkActions_BulkAssign_Precentage() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String SearchName1 = "ss" + Utility.dynamicNameAppender();
		AssignmentsPage assign = new AssignmentsPage(driver);

		baseClass.stepInfo("TestCase ID-RPMXCON-54461");
		baseClass.stepInfo(
				"To verify that in Bulk Assign, the % sampling method is selecting a truly random selection of documents");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("**Creating Assignment and saved serch as Pre Requisites**");
		assign.createAssignment(assignmentName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName1, Input.mySavedSearch);
		baseClass.stepInfo("Created a SavedSearch " + SearchName1);
		savedSearch = new SavedSearch(driver);
		// Launch DocView via Saved Search>Assignment
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		baseClass.stepInfo("**Selecting SavedSearch and navigating to doc list to take doc ids of all docs**");
		savedSearch.saveSearchToDoclist();
		DocListPage dlPage = new DocListPage(driver);
		List<String> docID = dlPage.getColumnValue("DOCID", false);
		
		baseClass.stepInfo("**Bulk assign docs with Percentage sample method**");
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		savedSearch.getSavedSearchToBulkAssign().ScrollTo();
		savedSearch.getSavedSearchToBulkAssign().Click();
		int countAssigned = Integer.parseInt(assign.assignwithSamplemethod(assignmentName, "Percentage", "50"));
		
		// taking first half of the docids froms DocId's list retrieved from
		// savedSerch>DocList Page before performing bulk assign
		List<String> docId_subList = docID.subList(0, countAssigned);
		assign.ViewAllDocsinDocListBtnINActionDD();
		List<String> docID_randomAssign = dlPage.getColumnValue("DOCID", false);
		
		SoftAssert sa = new SoftAssert();
		baseClass.stepInfo("DocIds of Assigned Docs from ASsignment" + docID_randomAssign.toString());
		baseClass.stepInfo("DocIds of first 50 % of docfroms saved saerch which we used as source to assign \n");
		baseClass.stepInfo(docID_randomAssign.toString());
		// Verifying whether docs are assigned randomly
		sa.assertNotEquals(docID_randomAssign, docId_subList);
		assign.deleteAssgnmntUsingPagination(assignmentName);
		sa.assertAll();
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54462", enabled = true, groups = { "regression" })
	public void verifyBulkActions_BulkAssign_Standard() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String SearchName1 = "ss" + Utility.dynamicNameAppender();
		String countToAssign = "5";
		AssignmentsPage assign = new AssignmentsPage(driver);

		baseClass.stepInfo("TestCase ID-RPMXCON-54462");
		baseClass.stepInfo("To verify that in Bulk Assign, the " + "standard sampling method is assigning documents");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("**Creating Assignment and saved search as Pre Requisites**");
		assign.createAssignment(assignmentName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName1, Input.mySavedSearch);
		baseClass.stepInfo("Created a SavedSearch " + SearchName1);

		savedSearch = new SavedSearch(driver);
		baseClass.stepInfo("**Bulk assign docs with Standard sample method**");
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		savedSearch.getSavedSearchToBulkAssign().ScrollTo();
		savedSearch.getSavedSearchToBulkAssign().Click();
		// assign docs using sample method and verifies the same no of docs is
		// updated in assignment page
		assign.assignwithSamplemethod(assignmentName, "Count of Selected Docs", countToAssign);
		baseClass.passedStep("The Docs in the saved search  assigned to assignment .");

		assign.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54485", enabled = true, groups = { "regression" })
	public void verifyDocCountFluctuation_ABM() throws Exception {
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);

		String SaveSearchName = "ABM_SavedSearch" + Utility.dynamicNameAppender();
		String folderTagNAme = "ABM" + Utility.dynamicNameAppender();
		String folderTagNAme1 = "ABM" + Utility.dynamicNameAppender();
		String assignmentName1 = "ABM" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id:RPMXCON-54485");
		baseClass.stepInfo("Verifying the fluctuation of document count for all the bulk actions "
				+ "in Advanced Batch Management report");

		// Basic Search and Bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionSearch.verifyPureHitsCount();
		sessionSearch.saveSearch(SaveSearchName);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		assignment.assignmentCreation(assignmentName1, Input.codeFormName);
		assignment.addReviewerAndDistributeDocs();
		baseClass.stepInfo(assignmentName1 + " Assignment Created and distributed to RMU/Rev");

		// generate report in ABM
		baseClass.stepInfo("**Advance batch management report generation at assignment level**");
		ABMReportPage AbmReportPage = new ABMReportPage(driver);
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, false, false);

		// select doc from report table
		String count = AbmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);

		baseClass.stepInfo("**click action button-bulk folder- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(true);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme, count);
		sessionSearch.getActionPopupCloseBtn().Click();

		baseClass.stepInfo("**Advance batch management report generation at assignment level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, false, false);

		// select doc from report table
		count = AbmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);

		baseClass.stepInfo("**click action button-bulk tag- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(false);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme, count);
		sessionSearch.getActionPopupCloseBtn().Click();
		
		baseClass.stepInfo("**Advance batch management report generation at assignment level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, false, false);

		// select doc from report table
		count = AbmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);
		baseClass.stepInfo("**click action button-bulk assign- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.bulkAssign();
		sessionSearch.verifyDocsFluctuation_BulkAssign(count);
		sessionSearch.getActionPopupCloseBtn().Click();

		baseClass.stepInfo("**Advance batch management report generation at document level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, true, false);
		AbmReportPage.docSelection();

		baseClass.stepInfo("**click action button-bulk folder- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(true);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme1, "1");
		sessionSearch.getActionPopupCloseBtn().Click();

		baseClass.stepInfo("**Advance batch management report generation at document level**");
		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, true, false);
		AbmReportPage.docSelection();

		baseClass.stepInfo("**click action button-bulk tag- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.BulkTag_Folder(false);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme1, "1");
		sessionSearch.getActionPopupCloseBtn().Click();

		AbmReportPage.validateRevListAndgenerateABM_Report(SaveSearchName, assignmentName1, true, false);
		AbmReportPage.docSelection();

		baseClass.stepInfo("**click action button-bulk assign- "
				+ "verification  of doc selected count fluctuation in action pop up**");
		AbmReportPage.bulkAssign();
		sessionSearch.verifyDocsFluctuation_BulkAssign("1");
		sessionSearch.getActionPopupCloseBtn().Click();

		assignment.deleteAssgnmntUsingPagination(assignmentName1);
		loginPage.logout();
	}
	
	/**
	 * @author Jayanthi.Ganesan
	 */

	@Test(description = "RPMXCON-53164", enabled = true, groups = { "regression" })
	public void verifyBulkAssign_PAimpersonateAsRMU() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53614");
		baseClass.stepInfo("RMU impersonated from PAU should be able assign assignments to PAU user");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as PA'" + Input.pa1userName + "'");
		assignment = new AssignmentsPage(driver);

		// impersonating to Rmu
		baseClass.impersonatePAtoRMU();

		String assign = "Assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Successfully impersonated as RMU");

		List<String> userToAssign = Arrays.asList(Input.pa1userName, Input.pa2userName);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssign();
		assignment.assignmentCreation(assign, Input.codingFormName);

		// docs assigning to PA user impersonated RMU.
		assignment.addReviewersUsingList(userToAssign, "PAU");

		baseClass.passedStep(" PAU users impersonated as RMU can able to assign doc to assignment.");

		assignment.deleteAssgnmntUsingPagination(assign);

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53165", enabled = true, groups = { "regression" })
	public void verifyBulkAssign_DAimpersonateAsRMU() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53165");
		baseClass.stepInfo("RUM impersonated from DA should be able assign assignments to DA users");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as DA'" + Input.da1userName + "'");
		assignment = new AssignmentsPage(driver);
		baseClass.waitTime(2); //to avoid page loading issue for DA user login
		// impersonating to Rmu
		baseClass.impersonateDAtoRMU();

		String assign = "Assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Successfully impersonated as RMU");

		List<String> userToAssign = Arrays.asList(Input.da1userName);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssign();
		assignment.assignmentCreation(assign, Input.codingFormName);

		// docs assigning to DA user impersonated RMU.
		assignment.addReviewersUsingList(userToAssign, "DA");

		baseClass.passedStep(" DAU users impersonated as RMU can able to assign doc to assignment.");

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53557", enabled = true, groups = { "regression" })
	public void verifyBulktag() throws Exception {
		SavedSearch saveSearch = new SavedSearch(driver);

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String tagName = "tagName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-53557 ");
		baseClass.stepInfo("Verify that As a RMU  I will be able to assign Bulk Tag from saved search");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1userName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.searchString1);
		int hitCount = Integer.parseInt(sessionSearch.verifyPureHitsCount());
		sessionSearch.saveSearch(searchName1);

		// selecting the savedSearch
		saveSearch.navigateToSSPage();
		saveSearch.SaveSearchToBulkTag(searchName1, tagName);
		baseClass.stepInfo("Bulk tag done from saved search screen " + tagName);

		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		// verifying for Tag Completed dcos count.
		tf.verifyTagDocCount(tagName, hitCount);

		// delete tags
		tf.navigateToTagsAndFolderPage();
		tf.deleteAllTags(tagName);

		// Deleting the SavedSearch
		saveSearch.navigateToSSPage();
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		loginPage.logout();

	}

	/**
	 * @author Raghuram.A
	 * @Date: 10/03/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that in Bulk Assign, the Email inclusive sampling
	 *              method working as expected. RPMXCON-54464
	 */
	@Test(description = "RPMXCON-54464", groups = { "regression" }, enabled = true)
	public void verifyParentDocCountDisplayedAsExpected2() throws InterruptedException {

		AssignmentsPage assign = new AssignmentsPage(driver);
		DocListPage docList = new DocListPage(driver);

		String finalCount;
		String docCount;
		String assignName = "assignName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-54464 Bulk Actions");
		baseClass.stepInfo("To verify that in Bulk Assign, the Email inclusive sampling method working as expected");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Assignment creation
		baseClass.stepInfo("**Step-2 Create new assignment from assignments**");
		assign.createAssignment(assignName, Input.codingFormName);

		// performing searching and saving it in newly created node
		baseClass.stepInfo("**Step-3 Search for inclusive email  and click on assign to bulk assign**");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// Bulk Assign via SessionSearch
		baseClass.stepInfo("**Step-4 Select assignment name and sampling method as inclusive email  and finalize**");
		finalCount = assign.assignwithSamplemethod(assignName, "Inclusive Email", null);

		baseClass.stepInfo("**Step-5 Go to assignments and select the assignment  created**");
		assign.Viewindoclistfromassgn(assignName);
		driver.waitForPageToBeReady();

		// Parent doc count verification
		baseClass.stepInfo(
				"**Step-6 Navigate to doc list of assignment and check if the parent level docs are assigned**");
		docCount = docList.verifyingDocCount();

		// Doc count comparision
		baseClass.stepInfo(
				"Verify that count displayed in the \"Total\" are the count of inclusive email Documents only.");
		baseClass.textCompareEquals(finalCount, docCount,
				"Only Inclusive Email Documents are listed in the doclist page",
				"Other than parent level docs are listed - Count mismatches");

		// logout
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
		System.out.println("**Executed  BulkActions_sprint23 .**");
	}
}
