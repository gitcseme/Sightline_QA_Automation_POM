package sightline.smallComponents;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Phase1_Regression {

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
	CodingForm codingpage;

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
	@Test(description ="RPMXCON-54952",groups = { "regression" } )
	public void filterFunctionalityOnSubTallyScreen() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54952");
		baseClass.stepInfo(
				"#### Verify that filter functionality works properly when TAG name contains word between on SubTally screen ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName +  Utility.dynamicNameAppender();
		final String random1 = random;
		tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random1, Input.securityGroup);

		baseClass.stepInfo("Verify created tag is added to tag structure");
		driver.Navigate().refresh();
		tagAndFol.verifyTagNameIsAddedToStructure(random1);

		docexp = new DocExplorerPage(driver);

		baseClass.stepInfo("Select documets from doc explorer table and bulk tag selected documents");
		docexp.selectDocAndBulkTag(random1);

		securityGroup = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Navigate to security group");
		securityGroup.navigateToSecurityGropusPageURL();
		
		baseClass.stepInfo("Add tag to security group");
		securityGroup.addTagToSecurityGroup(Input.securityGroup, random1);

		tally = new TallyPage(driver);

		baseClass.stepInfo("Navigate to tally page");
		tally.navigateTo_Tallypage();
		
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
	@Test(description ="RPMXCON-54950",groups = { "regression" })
	public void filterFunctionalityOnTallyScreen() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54950");
		baseClass.stepInfo(
				"####  Verify that filter functionality works properly when TAG name contains word between on Tally screen. ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName +  Utility.dynamicNameAppender();
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

		baseClass.stepInfo("Navigate to security group");
		securityGroup.navigateToSecurityGropusPageURL();
		
		baseClass.stepInfo("Add tag to security group");
		securityGroup.addTagToSecurityGroup(Input.securityGroup, random1);

		tally = new TallyPage(driver);

		baseClass.stepInfo("Navigate to tally page");
		tally.navigateTo_Tallypage();
		
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
	@Test(description ="RPMXCON-54947",groups = { "regression" })
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
	@Test(description ="RPMXCON-54946",groups = { "regression" })
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
	@Test(description ="RPMXCON-54699",groups = { "regression" })
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
	 @Test(description ="RPMXCON-54682",groups={"regression"})
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
		
		//create coding form
		String cfName = "CF" + Utility.dynamicNameAppender();
		codingpage = new CodingForm(driver);
		codingpage.commentRequired(cfName);
		codingpage.AssignCFstoSG(cfName);
		
		baseClass.stepInfo("Selecting the document in docExplorer page");
		ArrayList<String> documentId=docExplorer.documentsSelection(1);
		
		baseClass.stepInfo("View document in doc view on doc explorer");
		docExplorer.docExpViewInDocView();
		
		baseClass.stepInfo("Filling Document Comments in docview page");
		docViewPage.fillingTheDocviewCommentsSection(Input.reviewed);
		
		baseClass.stepInfo("Verifying selected documents are visible in docexplorerpage");
		docExplorer.verifyingTheSelectedDocumentInDocExplorerPage(documentId,Input.documentComments);
		
		
		codingpage = new CodingForm(driver);
		codingpage.AssigndefaultCFstoSG(Input.codingFormName);
		loginPage.logout();
	 }
	 
	 /**
		 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
		 * Testcase id : 55001- Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.
		 * Description : Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocList.
		 */		
		 @Test(description ="RPMXCON-55001",groups={"regression"})
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
			docExplorer.getDocumentsCheckBoxbyRowNum(1).waitAndClick(10);
			
			
			baseClass.stepInfo("View document in doc view on doc explorer");
			docExplorer.navigateToDoclistFromDocExplorer();
			
			baseClass.stepInfo("Verify Doc List Header");
			docExplorer.verifyDocListHeader();
//			loginPage.logout();
		
		 }
		 
		 /**
			 * @author Gopinath
			 * @TestCase Id:RPMXCON-54642 Verify the documents from list view
			 * @Description:To Verify the documents from list view.
			 */
			@Test(description ="RPMXCON-54642",groups={"regression"})
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
			@Test(description ="RPMXCON-54695",alwaysRun = true, groups = { "regression" } )
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
			@Test(description ="RPMXCON-54593",alwaysRun = true, groups = { "regression" } )
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
			@Test(description ="RPMXCON-54978",alwaysRun = true, groups = { "regression" } )
			public void verifyBackgroundProcessOfPrintDocument() throws InterruptedException {
				String pdfDocId = "STC4_00000871";
				String textDocId = "ID00000206";
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54978 sprint 12");
				baseClass.stepInfo("###Doc Explorer: Preview- Verify that on click of the print icon success message should be displayed to inform the user that it is processed in background task ###");
				
				DocViewPage docView = new DocViewPage(driver);
				SessionSearch session = new SessionSearch(driver);
				DocViewMetaDataPage docViewMetaData=new DocViewMetaDataPage(driver);
				loginPage.logout();
				loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
				
				baseClass.stepInfo("Basic  content search ");
				session.basicContentSearch(Input.searchText.toLowerCase());
				
				baseClass.stepInfo("View serached dos in Docview");
				session.ViewInDocView();
				
				docView.selectDocIdInMiniDocList(pdfDocId);
				
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
			@Test(description ="RPMXCON-54698",alwaysRun = true, groups = { "regression" } )
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
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54610 Verify that user can select all documents from page from List view, 
			 *                     and select action as View in DocList from Actions drop down
			 * @Description:To Verify that user can select all documents from page from List view, 
			 *               and select action as View in DocList from Actions drop down
			 * @throws AWTException
			 */
			@Test(description ="RPMXCON-54610",alwaysRun = true, groups = { "regression" } )
			public void verifyViewInDocListForSelectedDocs() throws AWTException {
				int numberOfFolders=14;
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54610 Sprint 12");
				baseClass.stepInfo("###Verify that user can select all documents from page from List view, and select action as View in DocList from Actions drop down");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("select folder and verify in doclist");
				docexp.verifyDocList(Integer.toString(numberOfFolders));
				
				baseClass.stepInfo("Select all current page docs and view in doc list");
				docexp.viewIndocListOfCurrentPageDocs();
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("select multiple folders");
				docexp.selectMultipleFoldersOfTree(numberOfFolders);
				
				baseClass.stepInfo("Select all current page docs and view in doc list");
				docexp.viewIndocListOfCurrentPageDocs();
				
				
			}
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54589 Verify the default menu for the PA and RMU after login
			 * @Description:To Verify the default menu for the PA and RMU after login
			 */
			@Test(description ="RPMXCON-54589",alwaysRun = true, groups = { "regression" } )
			public void verfifyDefaultMenuForPAandRMU() {
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54589 sprint 12");
				baseClass.stepInfo("###Verify the default menu for the PA and RMU after login###");
				docexp = new DocExplorerPage(driver);
				HomePage homePage= new HomePage(driver);
				loginPage.logout();
				
				baseClass.stepInfo("Login as Project Administrator");
				loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
				
				baseClass.stepInfo("verify default menu for Project Administrator");
				docexp.verifyDocExplorerPageHeader();
				
				loginPage.logout();
				
				baseClass.stepInfo("Login as Review manager");
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
				baseClass.stepInfo("verify default menu for Review manager");
				homePage.verifyDashboardPageAsDefaultMenu();
				
			}
			
			/**  
			 * @author Gopinath
			 * @TestCase id:54594 Verify the display of folder name when name is too long
			 * @Description:To Verify the display of folder name when name is too long
			 */
			@Test(description ="RPMXCON-54594",alwaysRun = true, groups = { "regression" } )
			public void verifyLongfolderName() {
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54594 Sprint 12");
				baseClass.stepInfo("### Verify the display of folder name when name is too long###");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("verify long folder name is displayed on tooltip");
				docexp.verifyFolderName();
				
			}
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54598 Verify when folder count is with the groups of thousands
			 * @Description:To Verify when folder count is with the groups of thousands.
			 */
			@Test(description ="RPMXCON-54598",alwaysRun = true, groups = { "regression" })
			public void verifyDocExplorerFolderCountFormat() {
				String folderNumber="1";
				baseClass=new BaseClass(driver);
				baseClass.stepInfo("###Verify when folder count is with the groups of thousands###");
				baseClass.stepInfo("Test case Id: RPMXCON-54598 ");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("verify folder count format");
				docexp.verifyDocExpFolderCountFormat(folderNumber);
				
			}
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54592 Verify the default structure of the Tree View and the document list from right panel
			 * @Description:To Verify the default structure of the Tree View and the document list from right panel
			 */
			@Test (description ="RPMXCON-54592",alwaysRun = true, groups = { "regression" } )
			public void verifyDefaultStructureOfTreeView() {
				String folderName="Enron Data (23)";
				int folderLevel=5;
				baseClass=new BaseClass(driver);
				baseClass.stepInfo("###Verify the default structure of the Tree View and the document list from right panel###");
				baseClass.stepInfo("Test case Id: RPMXCON-54592 Sprint 12");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to DocExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("verify docexplorer default tree structure");
				docexp.verrifyDocExpDefaultTreeStructure(folderName);
				
				baseClass.stepInfo("verify docexplorer folder level structure");
				docexp.verifyDocExpFolderLevel(folderName, folderLevel);
				
			}
			
			/** 
			 * @author Gopinath
			 * @TestCase Id:54588 Verify the "Doc Explorer" left menu for the PA and RMU
			 * @Description To Verify the "Doc Explorer" left menu for the PA and RMU
			 */
			@Test(description ="RPMXCON-54588",alwaysRun = true, groups = { "regression" } )
			public void verifyDocExplorerLeftMenuPaAndRmu() {
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("###Verify the 'Doc Explorer' left menu for the PA and RMU###");
				baseClass.stepInfo("Test case Id: RPMXCON-54588 ");
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("verify doc explorer presented in left menu panal");
				docexp.verifyDocExplorerInLeftMenu();
				
				loginPage.logout();
				
				baseClass.stepInfo("login as Review manager");
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
				baseClass.stepInfo("verfify doc explorer view Below dash board in left menu for ");
				docexp.verifyDocExplorerBelowDashBoard();
				
				loginPage.logout();
				
				baseClass.stepInfo("login as Project Administrator");
				loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
				
				baseClass.stepInfo("verify doc explorer view above Datasets in leftmenu");
				docexp.verifyDocExplorerAboveDatasets();

			}
			
			/**
			 * @author Gopinath
			 * @TestCase Id:54591 Verify the Tree View is displayed from the left side of the "Doc Explorer" page
			 * @Description:To Verify the Tree View is displayed from the left side of the "Doc Explorer" page
			 */  
			@Test(description ="RPMXCON-54591",alwaysRun = true, groups = { "regression" })
			public void verifyDocExplorerTreeView() {
				String folderName="Enron Data (23)";
				int folderLevel=5;
				baseClass = new BaseClass(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-54952");
				baseClass.stepInfo("Verify the Tree View is displayed from the left side of the 'Doc Explorer' page");
				loginPage.logout();
				
				baseClass.stepInfo("Login review manager");
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
				docexp = new DocExplorerPage(driver);
				
				baseClass.stepInfo("Navigating to docExplorer page");
				docexp.navigateToDocExplorerPage();
				
				baseClass.stepInfo("Verify folder structure");
				docexp.verrifyDocExpDefaultTreeStructure(folderName);
				baseClass.stepInfo("verify zero doc folders are no displayed");
				docexp.verifyDOcExplorerNoZeroDocFolder();
				
				baseClass.stepInfo("verify doc explorer folder and datasets");
				docexp.verrifyDocExplorerFolder(folderName);
				
				baseClass.stepInfo("verify docexplorer folder level structure");
				driver.Navigate().refresh();
				docexp.verifyDocExpFolderLevel(folderName,folderLevel );
				
				
			}
			
			
			/**
			 * @author ChandraMadmin
			 * @TestCase Id:63482 check that on DocExplorer screen-Lower folders from the Tree View should be visible
			 * @Description:To check that on DocExplorer screen-Lower folders from the Tree View should be visible
			 */
			@Test(description ="RPMXCON-63482",enabled = true)
			public void verifyDocExpLowerFolderVisible() {
				baseClass = new BaseClass(driver);
				loginPage = new LoginPage(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-63482");
				baseClass.stepInfo("To check that on DocExplorer screen-Lower folders from the Tree View should be visible");
				int numberOfFolders = 3;

				Map<String, String> credentials = new HashMap<String, String>();
				credentials.put("PA", Input.pa1userName + ":" + Input.pa1password);
				credentials.put("RMU", Input.rmu1userName + ":" + Input.rmu1password);
				Set<String> users = credentials.keySet();
				for (String user : users) {
					String userName = credentials.get(user).split(":")[0];
					String password = credentials.get(user).split(":")[1];
					loginPage.logout();
					baseClass.stepInfo("Login as " + user);
					loginPage.loginToSightLine(userName, password, Input.additionalDataProject);

					docexp = new DocExplorerPage(driver);

					baseClass.stepInfo("Navigating to docExplorer page");
					docexp.navigateToDocExplorerPage();

					baseClass.stepInfo("Step:3&4 verify arrow button and sub folders");
					docexp.verifySubFoldersDisplayed();

					baseClass.stepInfo("Step:5 verify selected folder docs displayed in the right panal");
					docexp.verifyDocList("1");

					baseClass.stepInfo("select multiple folders");
					docexp.selectMultipleFoldersOfTree(numberOfFolders);

					baseClass.stepInfo("Step:6 verify multiple folders count");
					docexp.verifyMultiFoldersCount(numberOfFolders);
				}

			}
			
			/**
			 * @author 
			 * @TestCase Id:63456 To check that on DocExplorer screen - Application should not show little arrow if the subfolder that don’t have any subfolder(s).
			 * @Description:To To check that on DocExplorer screen - Application should not show little arrow if the subfolder that don’t have any subfolder(s).
			 */
			@Test(description ="RPMXCON-63456",enabled = true)
			public void verifyDocExpLowerArrowButtonNotDisplayed() {
				baseClass = new BaseClass(driver);
				loginPage = new LoginPage(driver);
				baseClass.stepInfo("Test case Id: RPMXCON-63456");
				baseClass.stepInfo("To check that on DocExplorer screen - Application should not show little arrow if the subfolder that don’t have any subfolder(s).");
				int numberOfFolders = 3;
				String folderName="Amit (1)";
				String folderNumber="1";

				Map<String, String> credentials = new HashMap<String, String>();
				credentials.put("PA", Input.pa1userName + ":" + Input.pa1password);
				credentials.put("RMU", Input.rmu1userName + ":" + Input.rmu1password);
				Set<String> users = credentials.keySet();
				for (String user : users) {
					String userName = credentials.get(user).split(":")[0];
					String password = credentials.get(user).split(":")[1];
					loginPage.logout();
					baseClass.stepInfo("Login as " + user);
					loginPage.loginToSightLine(userName, password, Input.additionalDataProject);

					docexp = new DocExplorerPage(driver);

					baseClass.stepInfo("Navigating to docExplorer page");
					docexp.navigateToDocExplorerPage();

					baseClass.stepInfo("Step:3&4 verify arrow button and sub folders");
					docexp.verifySubFoldersDisplayed();

					baseClass.stepInfo("Step:5 verify arrow button is not displayed for folder not having any subfolder");
					driver.Navigate().refresh();
					docexp.verifyArrowButtonNotDisplayed(folderName);
					
					baseClass.stepInfo("Step:6 verify selected folder docs displayed in the right panal");
					docexp.verifyDocList(folderNumber);

					baseClass.stepInfo("select multiple folders");
					docexp.selectMultipleFoldersOfTree(numberOfFolders);

					baseClass.stepInfo("Step:6 verify multiple folders count");
					docexp.verifyMultiFoldersCount(numberOfFolders);
				}

			}
			
	/**@AfterMethod(alwaysRun = true)
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
	}**/
			
			@AfterClass(alwaysRun = true)
			public void close() {
				try {
					loginPage.quitBrowser();
				} catch (Exception e) {
					System.out.println("Sessions already closed");
				}
			}

			@AfterMethod(alwaysRun = true)
			private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
				baseClass = new BaseClass(driver);
				Reporter.setCurrentTestResult(result);
				if (ITestResult.FAILURE == result.getStatus()) {
					Utility baseClass = new Utility(driver);
					baseClass.screenShot(result);
				}
				try {
					loginPage.quitBrowser();
				} catch (Exception e) {
					loginPage.quitBrowser();
				}
			}
}
