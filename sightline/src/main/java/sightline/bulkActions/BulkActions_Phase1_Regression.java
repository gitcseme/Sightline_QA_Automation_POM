package sightline.bulkActions;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
		
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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
