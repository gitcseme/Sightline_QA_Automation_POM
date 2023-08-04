package sightline.bulkActions;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CodingForm;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Phase1_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	DocExplorerPage docexp;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFoldersPage;
	SoftAssert softAssertion;
	DocListPage doclist;
	SavedSearch savedSearch;
	
	
	String AssignmentOne = "AssignmentOne" + Utility.dynamicNameAppender();
	String AssignmentTwo = "AssignmentTwo" + Utility.dynamicNameAppender();
	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}
	
	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 48818 - Verify Total Email Threaded Count when bulk foldering.
	 * @Description : Verify Total Email Threaded Count when bulk foldering.
	 */
    @Test(description ="RPMXCON-48818",alwaysRun = true,groups={"regression"})
	public void verifyTotalEmailThreadedCountbulkFolderingFromDocView() throws Exception {		
		baseClass=new BaseClass(driver);
		String emailSubject = "MID C Question";
		String folderName = Input.randomText+Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-48818 Sprint 12");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("#### Verify Total Email Threaded Count when bulk foldering ####");
	
		docexp = new DocExplorerPage(driver);
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		UtilityLog.info("Logged in as User: " + Input.rmu2userName);
		Reporter.log("Logged in as User: " + Input.rmu2password);
		
		baseClass.selectproject("AutomationAdditionalDataProject");
		
		baseClass.stepInfo("Navigating to DocExplorer page");
		docexp.navigateToDocExplorerPage();
		
		docexp.enterFileNameInFileNameFilter(emailSubject);
		
		baseClass.stepInfo("Selecting the document in docExplorer page");
		docexp.selectAllDocumentsFromCurrentPage();
		
		baseClass.stepInfo("View document in doc view on doc explorer");
		driver.waitForPageToBeReady();
		docexp.docExpViewInDocView();
		
		baseClass.stepInfo("Select all documents from mini doc list.");
		int totalDoc = docView.selectAllDocumentsInMiniDocList();
		
		baseClass.stepInfo("Create new folder and add selected document to it");
		docViewMetaDataPage.createNewFolderAndAddSelectedDocument(folderName);
	
		TagsAndFoldersPage folder = new TagsAndFoldersPage(driver);
		
		baseClass.stepInfo("Navigate to tags and folder page.");
		folder.navigateToTagsAndFolderPage();
		
		baseClass.stepInfo("Verify Folder Doc Count");
		folder.verifyFolderDocCount(folderName, totalDoc);
		
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 04/05/2022 Modified by: Baskar
	 * Description:To verify that if the documents are being assigned to multiple assignments, 
	 * the same "sample documents representative like documents" are being assigned to each of the selected assignments.
	 * @return 
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-54300",enabled = true, groups = { "regression" })
	public void validatingBulkAssignAssignmentCount() throws InterruptedException  {
		baseClass.stepInfo("Test case Id: RPMXCON-54300");
		baseClass.stepInfo("To verify that if the documents are being assigned to multiple assignments,"
				+ " the same \"sample documents + representative like documents\" are being assigned to "
				+ "each of the selected assignments.");
		AssignmentsPage agnmt=new AssignmentsPage(driver);
		sessionSearch=new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String assignCountOne="";
		String assignCountTwo="";
		int size=2;
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
		// creating multiple assignment
		for (int i = 1; i <= size; i++) {
			if (i == 1) {
				agnmt.createAssignment(AssignmentOne, Input.codeFormName);
				System.out.println("inside loop:" + AssignmentOne);
			}
			if (i == 2) {
				agnmt.createAssignment(AssignmentTwo, Input.codeFormName);
			}

		}

		// Session search to bulk assign to multiple assignment
		int pureHitAssignCount=sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		agnmt.getSelectAssignmentToBulkAssign(AssignmentOne).waitAndClick(5);
		agnmt.getSelectAssignmentToBulkAssign(AssignmentTwo).waitAndClick(5);
		baseClass.waitForElement(agnmt.getContinueBulkAssign());
		agnmt.getContinueBulkAssign().waitAndClick(30);
		baseClass.waitForElement(agnmt.getAssgn_TotalCount());
		baseClass.waitForElement(agnmt.getFinalizeButton());
		agnmt.getFinalizeButton().waitAndClick(10);
		baseClass.stepInfo("Selected document for bulk assign are added to multiple assignment");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		baseClass.waitTime(5);
		baseClass.waitForElement(agnmt.getNumberOfAssignmentsToBeShown());
		agnmt.getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(agnmt.getAssgn_Pagination());
		int count = ((agnmt.getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			driver.waitForPageToBeReady();
				Boolean status = agnmt.getSelectAssignment(AssignmentOne).isDisplayed();
				Boolean statusTwo = agnmt.getSelectAssignment(AssignmentTwo).isDisplayed();
				if (status == true&&statusTwo==true) {
					driver.scrollingToElementofAPage(agnmt.getSelectAssignment(AssignmentOne));
					assignCountOne=agnmt.getSelectAssignmentDocCount(AssignmentOne).getText();
					assignCountTwo=agnmt.getSelectAssignmentDocCount(AssignmentTwo).getText();
				}
			
			else {
				driver.scrollingToBottomofAPage();
				baseClass.waitForElement(agnmt.getAssgnPaginationNextButton());
				agnmt.getAssgnPaginationNextButton().Click();
				baseClass.stepInfo("Expected assignment not found in the page " + i);
			}
			}
		System.out.println(assignCountOne);
		softAssertion.assertEquals(Integer.parseInt(assignCountOne), pureHitAssignCount);
		softAssertion.assertEquals(Integer.parseInt(assignCountTwo), pureHitAssignCount);
		softAssertion.assertAll();
		baseClass.passedStep("Assigned document to different assignmnet are displaying with document "
				+ "count as expected action done using bulk action");
		loginPage.logout();

	}

	
	/**
	 * @author Baskar  Date: 04/05/22 Modified date:N/A Modified by: 
	 * @Description:To verify that if user delete the Tag/Folder,
	 *               it should not displayed Bulk Tag/Folder modal
	 */
	@Test(description ="RPMXCON-54265",enabled = true, groups = { "regression" })
	public void verifyRMUfolderEditDelete() throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-54265");
		TagsAndFoldersPage tagAndFolderPage = new TagsAndFoldersPage(driver);
		sessionSearch=new SessionSearch(driver);
		DocListPage doclist=new DocListPage(driver);
		String tag = "newTag" + Utility.dynamicNameAppender();
		String folder = "newFolder" + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"To verify that if user delete the Tag/Folder, it should not "
				+ "displayed Bulk Tag/Folder modal");
		// login as PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);

		// create tag and folder
		tagAndFolderPage.CreateTag(tag, Input.securityGroup);
		tagAndFolderPage.CreateFolder(folder, Input.securityGroup);
		baseClass.stepInfo("New tag and folder created");
		
		// deleting the tag and folder
		tagAndFolderPage.DeleteTag(tag, Input.securityGroup);
		tagAndFolderPage.DeleteFolder(folder, Input.securityGroup);
        baseClass.stepInfo("Deleting the tag and folder which created");
        
		// Session search to bulk assign to multiple assignment
		int pureHitAssignCount = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		
		// selecting doc from doclist
		doclist.selectingAllDocuments();
		doclist.validatingTagExisting(tag);
		doclist.validatingFolderExisting(folder);
		baseClass.passedStep("Deleted tag and folder are not displaying in existing");
        
		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Baskar  Date: 09/05/22 Modified date:N/A Modified by: 
	 * @Description:Verify that bulk assign with 'Parent Level Docs' 
	 *               sample method displays correct count if 'SourceParentDocID' is blank
	 */
	@Test(description ="RPMXCON-54512",enabled = true, groups = { "regression" })
	public void verifyBulkAssignBlankSourceParentDocID() throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-54512");
		sessionSearch=new SessionSearch(driver);
		DocListPage doclist=new DocListPage(driver);
		baseClass.stepInfo(
				"Verify that bulk assign with 'Parent Level Docs' sample method "
				+ "displays correct count if 'SourceParentDocID' is blank");
		// login as PA
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Session search to doclist
		int totalCount=sessionSearch.basicContentSearchForTwoItems(Input.sourceBlank,Input.sourceParentBlank);
		sessionSearch.ViewInDocList();
		
		// selecting doc from doclist
		doclist.selectColumnMetaDataSelection();
		
		// main method validation for parent level docss
		doclist.selectingBlankDocs(Input.sourceBlank,Input.sourceParentBlank,2,totalCount);
        
		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Baskar  Date: 09/05/22 Modified date:N/A Modified by: 
	 * @Description:Verify when "Parent Level Only" sample method is selected to 
	 *              bulk assign when SourceParentDocID is not same as SourceDocID 
	 *              and no child for the child document
	 */
	@Test(description ="RPMXCON-54511",enabled = true, groups = { "regression" })
	public void verifyBulkAssignBlankWithNoChild() throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-54511");
		sessionSearch=new SessionSearch(driver);
		DocListPage doclist=new DocListPage(driver);
		baseClass.stepInfo(
				"Verify when \"Parent Level Only\" sample method is selected to bulk assign when "
				+ "SourceParentDocID is not same as SourceDocID and no child for the child document");
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Session search to doclist
		int totalCount=sessionSearch.basicContentSearch(Input.sourceParentBlank);
		sessionSearch.ViewInDocList();
		
		// selecting doc from doclist
		doclist.selectColumnMetaDataSelection();
		
		// main method validation for parent level docss
		doclist.selectingDocsBasedOnSources(Input.sourceParentBlank,2,totalCount);
        
		// logout
		loginPage.logout();
	}
	/**
	 * @throws Exception
	 * @Author :Indium-Baskar
	 * @Description : Verify the "Bulk Assign" functionality in selected results as
	 *              a RMU login
	 */
	@Test(description = "RPMXCON-48684", enabled = true, groups = { "regression" })
	public void verifyRmuCanBulkAssignMultipleDrag2() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-48684");
		baseClass.stepInfo("Verify the \"Bulk Assign\" functionality in selected results as a RMU login");
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		// Login as rmu
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		UtilityLog.info("Logged in as User: " + Input.rmu2userName);

		// searching for multiple times
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getPureHitAddButton().isElementAvailable(2);
		sessionSearch.getPureHitAddButton().waitAndClick(5);
		baseClass.stepInfo("Draged first purehit count");
		sessionSearch.addNewSearch();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getEnterSearchString_UsingPosition(5).Visible();
			}
		}), Input.wait60);
		sessionSearch.getEnterSearchString_UsingPosition(5).SendKeys(Input.searchString2);

		// Click on Search button
		sessionSearch.getSecondSearchBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getPureHit_UsingLast().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		sessionSearch.getPureHit_UsingLast().waitAndClick(20);
		int pureHits = Integer.parseInt(sessionSearch.getPureHit_UsingLast().getText());
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.assignmentNameValidation(assignmentName);
		baseClass.passedStep("Rmu can able to assign docs from session serach using multiple query");
		// logout
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Indium-Baskar
	 * @Description : Verify that As a Project Admin login I will be able to Release
	 *              document from saved search
	 */
	@Test(description = "RPMXCON-48685", enabled = true, groups = { "regression" })
	public void verifyPaUserCanReleaseDocsFromSavedSearch() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-48685");
		baseClass.stepInfo(
				"Verify that As a Project Admin login I will be able " + "to Release document from saved search");
		String savedsSarch = "Assign" + Utility.dynamicNameAppender();
		String securitygroupname1 = "securitygroupname" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);

		// Login as pa
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		UtilityLog.info("Logged in as User: " + Input.pa2userName);

		// Creating a new sg
		securityGroupPage.navigateToSecurityGropusPageURL();
		securityGroupPage.AddSecurityGroup(securitygroupname1);

		// saving the search as per pre-requistes
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedsSarch);

		// from save search releasing docs to sg
		savedSearch.savedSearch_Searchandclick(savedsSarch);
		savedSearch.preRequestCreation(savedsSarch, securitygroupname1);
		baseClass.VerifySuccessMessage("Records saved successfully");
		securityGroupPage.deleteSecurityGroups(securitygroupname1);
		baseClass.passedStep("As a Pa user document released to new sg from saved search");

		// logout
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54487
	 * @Description:verify that in Bulk Assign modal should not display 'InProgress'
	 *                     warning message
	 **/
	@Test(description = "RPMXCON-54487", enabled = true, groups = { "regression" })
	public void verifyBulkAssignModelWarning() throws Exception {

		baseClass.stepInfo("RPMXCON-54487");
		baseClass.stepInfo("To verify that in Bulk Assign modal should not display 'InProgress' warning message");
		sessionSearch=new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Logged in As : " + Input.rmu2userName);

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
	
	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.rmu2userName, Input.rmu2password }, { Input.pa2userName, Input.pa2password } };
		return users;
	}

	/**
	 * @author NA Testcase No:RPMXCON-65743
	 * @Description: Verify Warning message to validate for conflicts during bulk
	 *               tagging from Saved search
	 **/
	@Test(description = "RPMXCON-65743", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyValidateConflictSS(String username, String password) throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		SavedSearch search = new SavedSearch(driver);
		TallyPage tally = new TallyPage(driver);
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
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
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
		if (sessionSearch.getNoBtn().Enabled()) {
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
		if (!sessionSearch.getReportTotalCount().isElementAvailable(1)) {
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
		sessionSearch=new SessionSearch(driver);
		// login as RMU
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		baseClass.stepInfo(
				"Verifying the fluctuation of document count for " + "all the bulk actions in Manage Assignments");
		baseClass.stepInfo("Test case Id: RPMXCON-54484");

		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String folderTagNAme = "folderTag" + Utility.dynamicNameAppender();
		agnmt=new AssignmentsPage(driver);

		String count = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.scrollingToElementofAPage(agnmt.getAssignmentSaveButton());
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);

		agnmt.navigateToAssignmentsPage();
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Tag All Option");
		agnmt.Bulk_TagAll_FolderAll(true);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme, count);

		agnmt.navigateToAssignmentsPage();
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Folder All Option");
		agnmt.Bulk_TagAll_FolderAll(false);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme, count);

		agnmt.deleteAssgnmntUsingPagination(assignmentName);
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
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
	
}
