package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	TagsAndFoldersPage tagAndFol;
	TallyPage tally;
	SecurityGroupsPage securityGroup;
	DocExplorerPage docexp;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Ex" + "ecution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/*
	 * Author : Gopinath Created date: 01-09-2021 Modified date: Modified by:
	 * Testcase id : 54952 - Verify that filter functionality works properly when
	 * TAG name contains word between on SubTally screen. Description : To verify
	 * filter functionality works on subtally.
	 */
	@Test(groups = { "regression" }, priority = 1)
	public void filterFunctionalityOnSubTallyScreen() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54952");
		baseClass.stepInfo(
				"#### Verify that filter functionality works properly when TAG name contains word between on SubTally screen ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName + utility.dynamicNameAppender();
		final String random1 = random;
		tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random1, Input.securityGroup);

		baseClass.stepInfo("Verify created tag is added to tag structure");
		tagAndFol.verifyTagNameIsAddedToStructure(random1);

		docexp = new DocExplorerPage(driver);

		baseClass.stepInfo("Select documets from doc explorer table and bulk tag selected documents");
		docexp.selectDocAndBulkTag(random1);

		securityGroup = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Add tag to security group");
		securityGroup.addTagToSecurityGroup(Input.securityGroup, random1);

		tally = new TallyPage(driver);

		baseClass.stepInfo("Select project as souce for tally");
		tally.selectSourceByProject();

		baseClass.stepInfo("Select Tally by Meta Data Field");
		tally.selectTallyByMetaDataField(Input.metaDataName);

		baseClass.stepInfo("Select documents and click on action drop down");
		tally.tallyActions();

		baseClass.stepInfo("Selecting sub tally from drop down");
		tally.selectSubTallyFromActionDropDown();

		baseClass.stepInfo("Applying subtally from sub tally field");
		tally.applyingSubTallyField(random1);

		baseClass.stepInfo("Verify documents count by tag name subtally");
		tally.verifyDocumentsCountByTagNameSubTally(random1);
		loginPage.logout();
	}
	
	
	/*
	 * Author : Gopinath Created date: 01-09-2021 Modified date: Modified by:
	 * Testcase id : 54950 - Verify that filter functionality works properly when TAG name contains word between on Tally screen.
	 * Description : Verify filter functionality works on tally screen.
	 */
	@Test(groups = { "regression" }, priority = 2)
	public void filterFunctionalityOnTallyScreen() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54950");
		baseClass.stepInfo(
				"####  Verify that filter functionality works properly when TAG name contains word between on Tally screen. ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName + utility.dynamicNameAppender();
		final String random1 = random;
		tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random1, Input.securityGroup);

		baseClass.stepInfo("Verify created tag is added to tag structure");
		tagAndFol.verifyTagNameIsAddedToStructure(random1);

		docexp = new DocExplorerPage(driver);

		baseClass.stepInfo("Select documets from doc explorer table and bulk tag selected documents");
		docexp.selectDocAndBulkTag(random1);

		securityGroup = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Add tag to security group");
		securityGroup.addTagToSecurityGroup(Input.securityGroup, random1);

		tally = new TallyPage(driver);

		baseClass.stepInfo("Select project as souce for tally");
		tally.selectSourceByProject();

		baseClass.stepInfo("Select Tally by tag name");
		tally.selectTallyByTagName(random1);
		
		baseClass.stepInfo("Verify tally chart rect is displayed");
		tally.verifyTallyChartRectIsDisplayed(true);
		loginPage.logout();
		
	}

	
	/*
	 * Author : Gopinath Created date: 08-10-2021 Modified date: Modified by:
	 * Testcase id : 54947 - Verify that Exclude filter functionality works properly when TAG name contains word between on Doc Explorer screen.
	 * Description : Verify exculde filter for tag name is working properly in DocExplorer.
	 */
	@Test(groups = { "regression" }, priority = 3)
	public void verifyTagExcludeFilterOnDocExplorer() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54947 Docexplorer of Sprint-4 ");
		baseClass.stepInfo(
				"####  Verify that Exclude filter functionality works properly when TAG name contains word between on Doc Explorer screen. ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random1, Input.securityGroup);

		docexp = new DocExplorerPage(driver);
		
		baseClass.stepInfo("Select some documets from doc explorer table and bulk tag selected documents");
		docexp.selectDocumentsAndBulkTag(3,random1);
		
		baseClass.stepInfo("Get total number of documents.");
		int totalDocCount = docexp.totalDocumentsCount();
		
		baseClass.stepInfo("Perform exclude filter by tag");
		docexp.performExculdeTagFilter(random1);
		
		baseClass.stepInfo("Verify documents after applying exclude functionality by tag");
		docexp.verifyExcludeFunctionlityForTag(totalDocCount, 3);
		loginPage.logout();
	}

	/*
	 * Author : Gopinath Created date: 08-10-2021 Modified date: Modified by:
	 * Testcase id : 54946 - Verify that Include filter functionality works properly when TAG name contains word between on Doc Explorer screen.
	 * Description : Verify include filter for tag name is working properly in DocExplorer.
	 */
	@Test(groups = { "regression" }, priority = 4)
	public void verifyTagIncludeFilterOnDocExplorer() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54946 Docexplorer of Sprint-4 ");
		baseClass.stepInfo(
				"####  Verify that Include filter functionality works properly when TAG name contains word between on Doc Explorer screen.. ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random1, Input.securityGroup);

		docexp = new DocExplorerPage(driver);
		
		baseClass.stepInfo("Select some documets from doc explorer table and bulk tag selected documents");
		ArrayList<String> selectedDocs = docexp.selectDocumentsAndBulkTag(3,random1);
		
		baseClass.stepInfo("Refresh page");
		docexp.refreshPage();
		
		baseClass.stepInfo("Perform exclude filter by tag");
		docexp.performInculdeTagFilter(random1);
		
		baseClass.stepInfo("Verify documents after applying include functionality by tag");
		docexp.verifyIncludeTagFilterWorksProperly(selectedDocs);
		loginPage.logout();
	}
	
	
	/*
	 * Author : Gopinath Created date: 08-10-2021 Modified date: Modified by:
	 * Testcase id : 54699 - Verify that Folders Filter with "Exclude" functionality is working correctly on Doc Explorer list.
	 * Description : Verify exculde filter for folder name is working properly in DocExplorer.
	 */
	@Test(groups = { "regression" }, priority = 5)
	public void verifyFolderExcludeFilterOnDocExplorer() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54699 Docexplorer of Sprint-4 ");
		baseClass.stepInfo(
				"####  Verify that Folders Filter with 'Exclude' functionality is working correctly on Doc Explorer list. ####");
		utility = new Utility(driver);
		String random = Input.atternoyClient + Utility.dynamicNameAppender();
		String random2 = Input.confidential + Utility.dynamicNameAppender();
		
		final String random1 = random;
		final String random3 = random2;
		tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFol.CreateFolder(random1, Input.securityGroup);
		
		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFol.CreateFolder(random3, Input.securityGroup);

		docexp = new DocExplorerPage(driver);
		
		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(3,random1);
		
		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(3,random3);
		
		baseClass.stepInfo("Get total number of documents.");
		int totalDocCount = docexp.totalDocumentsCount();
		
		baseClass.stepInfo("Perform exclude filter by folder");
		docexp.performExculdeFolderFilter(random1);
		
		baseClass.stepInfo("Verify documents after applying exclude functionality by folder");
		docexp.verifyExcludeFunctionlityForFolder(totalDocCount, 3);
		
		baseClass.stepInfo("Refresh page");
		docexp.refreshPage();
		
		baseClass.stepInfo("Perform exclude filter by folder");
		docexp.performExculdeFolderFilter(random1);
	
		baseClass.stepInfo("Perform another exclude filter by folder");
		docexp.performUpdateExculdeFolderFilter(random3);
		
		baseClass.stepInfo("Verify documents after applying exclude functionality by folder");
		docexp.verifyExcludeFunctionlityForFolder(totalDocCount, 3);
		loginPage.logout();		
	}

	
	 /**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54682- Verify that Comments Filter with 'Include' functionality is working correctly on Doc Explorer list..
	 * Description : Verify comments filter include functionality working in DocExlorer 
	 */		
	 @Test(groups={"regression"},priority = 6)
	 public void verifyCommentFilterFunctionality() throws InterruptedException, AWTException {
		baseClass=new BaseClass(driver);
	    DocViewPage docViewPage=new DocViewPage(driver);
	    baseClass.stepInfo("Test case Id: RPMXCON-54682- DocExplorer Sprint 04");
		baseClass.stepInfo("#### Verify that Comments Filter with 'Include' functionality is working correctly on Doc Explorer list ####");	
		
		DocExplorerPage docExplorer=new DocExplorerPage(driver);
		
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		baseClass.stepInfo("Selecting the document in docExplorer page");
		ArrayList<String> documentId=docExplorer.documentsSelection(1);
		
		baseClass.stepInfo("View document in doc view on doc explorer");
		docExplorer.docExpViewInDocView();
		
		baseClass.stepInfo("Filling Document Comments in docview page");
		docViewPage.fillingTheDocviewCommentsSection(Input.reviewed);
		
		baseClass.stepInfo("Verifying selected documents are visible in docexplorerpage");
		docExplorer.verifyingTheSelectedDocumentInDocExplorerPage(documentId,Input.documentComments);
		loginPage.logout();
	 }
	 
	 /**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
		 * Testcase id : 55001- Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.
		 * Description : Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocList.
		 */		
		 @Test(groups={"regression"},priority = 7)
		 public void verifyUserNavigateToDocListWithFilters(){
			baseClass=new BaseClass(driver);
		    baseClass.stepInfo("Test case Id: RPMXCON-55001- DocExplorer Sprint 08");
			baseClass.stepInfo("#### Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocList. ####");	
			
			DocExplorerPage docExplorer=new DocExplorerPage(driver);
			
			baseClass.stepInfo("Navigate To Doc Explorer Page");
			docExplorer.navigateToDocExplorerPage();
			
			baseClass.stepInfo("Selecting the document in docExplorer page");
			docExplorer.masterDateWithEmailSubject(Input.proximity, Input.masterDate);
			
			baseClass.stepInfo("Selecting the document in docExplorer page");
			docExplorer.selectDocument(1);
			
			baseClass.stepInfo("View document in doc view on doc explorer");
			docExplorer.navigateToDocViewFromDocExplorer();
			
			baseClass.stepInfo("Verify Doc List Header");
			docExplorer.verifyDocListHeader();
			loginPage.logout();
		
		 }
		 
		 /**
			 * @author Gopinath
			 * @TestCase Id:RPMXCON-54642 Verify the documents from list view
			 * @Description:To Verify the documents from list view.
			 */
			@Test(groups={"regression"},priority = 8)
			public void verifyDocExplorerDocList() {
				String folderNumber="14";
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54642 Sprint 12");
				baseClass.stepInfo("###Verify the documents from list view###");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("Select folder");
				docexp.verifyDocList(folderNumber);
				
				loginPage.logout();
				
			}
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54695 Verify that “EmailRecipientDomains” Filter with "Exclude" functionality is working correctly on Doc Explorer list.
			 * @Description:To Verify that “EmailRecipientDomains” Filter with "Exclude" functionality is working correctly on Doc Explorer list.
			 */
			@Test
			public void verifyEmailDomainExclude() {
				String functionality="exclude";
				String domain1="aol.com";
				String domain2="gmail.com";
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54695");
				baseClass.stepInfo("###Verify that “EmailRecipientDomains” Filter with 'Exclude' functionality is working correctly on Doc Explorer list.###");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("filter EmailRecipientDomain Exclude of "+domain1);
				docexp.emailRecipientDomainFiletr(functionality, domain1);
				
				baseClass.stepInfo("verifying document Exclude of domain"+domain1);
				docexp.verifyDocumentsExcludeOfEmailReceipientDomain(domain1);
				
				baseClass.stepInfo("filter with multiple values");
				docexp.UpdateFilter(domain2);
				baseClass.waitForElement(docexp.getApplyFilter());
				docexp.getApplyFilter().waitAndClick(5);
				
				baseClass.stepInfo("verifying document Exclude of domain"+domain2);
				docexp.verifyDocumentsExcludeOfEmailReceipientDomain(domain2);
				
				
			}
	
			/**
			 * @author Gopinath
			 * @TestCase Id:RPMXCON-54593 Verify that correct count should be displayed for each node from the tree structure
			 * @Description:To Verify that correct count should be displayed for each node from the tree structure
			 */
			@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
			public void verifyDocExplorerDocCount() {
				String folderNumber="13";//having more docs for better verification
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54593");
				baseClass.stepInfo("###Verify that correct count should be displayed for each node from the tree structure###");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("verifying the count of documents of folder in tree strecture");
				docexp.verifyDOcExplorerFolderDocCount(folderNumber);
				
			}
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54978 Doc Explorer: Preview- Verify that on click of the print icon success message should be displayed to inform the user that it is processed in background task
			 * @Description:To Doc Explorer: Preview- Verify that on click of the print icon success message should be displayed to inform the user that it is processed in background task
			 * @throws InterruptedException 
			 */
			@Test(alwaysRun = true, groups = { "regression" }, priority = 7)
			public void verifyBackgroundProcessOfPrintDocument() throws InterruptedException {
				String pdfDocId = "ID00004847";
				String textDocId = "ID00004707";
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54978 sprint 12");
				baseClass.stepInfo("###Doc Explorer: Preview- Verify that on click of the print icon success message should be displayed to inform the user that it is processed in background task ###");
				
				DocViewPage docView = new DocViewPage(driver);
				SessionSearch session = new SessionSearch(driver);
				DocViewMetaDataPage docViewMetaData=new DocViewMetaDataPage(driver);
				loginPage.logout();
				loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
				
				baseClass.stepInfo("Basic  content search ");
				session.basicContentSearch(Input.testData1);
				
				baseClass.stepInfo("View serached dos in Docview");
				session.ViewInDocView();
				
				baseClass.stepInfo("Select pdf document");
				docView.ScrollAndSelectDocument(pdfDocId);
				
				baseClass.stepInfo("add redaction");
				docViewMetaData.clickOnRedactAndRectangle();
				docViewMetaData.redactbyrectangle(5, 5, Input.defaultRedactionTag);
				driver.scrollPageToTop();
				
				baseClass.waitForElementToBeGone(baseClass.getSuccessMsgHeader(), 10);
				
				baseClass.stepInfo("print pdf document");
				docView.performPrintDocument(pdfDocId);
				
				baseClass.stepInfo("opening document from Background tasks");
				docView.openDocumentFromBgNotifacation();
				
				driver.Navigate().refresh();//refresh to add redaction to second document 
				baseClass.stepInfo("Select text document");
				docView.selectDocToViewInDocViewPanal(textDocId);
				
				baseClass.stepInfo("add redaction");
				docViewMetaData.clickOnRedactAndRectangle();
				baseClass.waitTime(2);
				docViewMetaData.redactbyrectangle(5, 5, Input.defaultRedactionTag);
				driver.scrollPageToTop();
				
				baseClass.waitForElementToBeGone(baseClass.getSuccessMsgHeader(), 10);
				
				baseClass.stepInfo("print text document");
				docView.performPrintDocument(textDocId);
				
				baseClass.stepInfo("opening document from Background tasks");
				docView.openDocumentFromBgNotifacation();
				
				
			}
			/**
			 * @author Gopinath
			 * @TestCase Id:54698 Verify that “Tags” Filter with "Exclude" functionality is working correctly on Doc Explorer list
			 * @Description:To Verify that “Tags” Filter with "Exclude" functionality is working correctly on Doc Explorer list
			 * @throws InterruptedException
			 */
			@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
			public void verifyTagFilterWithExclude() throws InterruptedException {
				String tag1="Attorney_Client";
				String tag2="Confidential";
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54698 sprint 12");
				baseClass.stepInfo("###Verify that “Tags” Filter with 'Exclude' functionality is working correctly on Doc Explorer list###");
				SessionSearch session= new SessionSearch(driver);
				docexp = new DocExplorerPage(driver);

				baseClass.stepInfo("basic content search");
				session.basicContentSearch(Input.translationDocument);
				
				baseClass.stepInfo("bulk tag");
				session.bulkTagExisting(tag1);
				driver.waitForPageToBeReady();
				
				baseClass.stepInfo("bulk tag");
				
				session.bulkTagExisting(tag2);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("perform exclude filter for tag "+Input.atternoyClient);
				docexp.performExculdeTagFilter(tag1);
				
				baseClass.stepInfo("verify documents exclude of tag "+Input.atternoyClient);
				docexp.verifyExcludeDocumentsOfTag(Input.translationDocument);
				
				baseClass.stepInfo("perform exclude filter with multiple values");
				docexp.UpdateFilter(tag2);
				baseClass.waitForElement(docexp.getApplyFilter());
				docexp.getApplyFilter().waitAndClick(5);
				
				baseClass.stepInfo("verify documents exclude of tag "+Input.confidential);
				docexp.verifyExcludeDocumentsOfTag(Input.translationDocument);
				
			}
			
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//loginPage.quitBrowser();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}

}
