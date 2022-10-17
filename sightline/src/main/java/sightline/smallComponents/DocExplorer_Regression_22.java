package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression_22 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

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
		docExplorer = new DocExplorerPage(driver);

	}
	
	/**
	 * Author :Arunkumar date: 26/09/2022 TestCase Id:RPMXCON-54595
	 * Description :Verify when user clicks on folder name from the tree view
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54595",enabled = true, groups = { "regression" })
	public void verifyCountInFolderAndTreeView() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54595");
		baseClass.stepInfo("Verify when user clicks on folder name from the tree view");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", " on doc explorer page",
				"Not on doc explorer page");
		int treeViewCount=docExplorer.getFolderCountFromTreeView("1");
		baseClass.stepInfo("Docs count in folder tree view:" +treeViewCount);
		int listViewCount=docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("Docs count in rightside list view:" +listViewCount);
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 26/09/2022 TestCase Id:RPMXCON-54612
	 * Description :Verify that Action drop down should be disable when no document
	 *  is selected from List View
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54612",enabled = true, groups = { "regression" })
	public void verifyActionDropdownInListView() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54612");
		baseClass.stepInfo("Verify action dropdown status in list view when no docs selected");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", " by default on doc explorer page",
				"Not on doc explorer page");
		baseClass.stepInfo("verify docs count in tree view and list view");
		int treeViewCount=docExplorer.getFolderCountFromTreeView("1");
		baseClass.stepInfo("count in folder tree view:" +treeViewCount);
		int listViewCount=docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("document count in rightside list view:" +listViewCount);
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
		baseClass.stepInfo("verify action dropdown status");
		String status = docExplorer.actionDropdown().GetAttribute("disabled");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("Action dropdown in list view disabled when no docs selected");
		}
		else {
			baseClass.failedStep("Action dropdown enabled when no docs selected");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 26/09/2022 TestCase Id:RPMXCON-54621
	 * Description :Verify that user can select a single document from List view, and select action as 
	 * Bulk Assign from Actions drop down to Assign document to existing assignment
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54621",enabled = true, groups = { "regression" })
	public void verifySingleDocumentBulkAssignInListView() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54621");
		baseClass.stepInfo("Verify bulk assign to existing assignment");
		String assignName = "assign"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Create assignment");
		AssignmentsPage assignment = new AssignmentsPage(driver);
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a single document and select bulk assign action");
		docExplorer.selectDocument(1);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");
		assignment.assignwithSamplemethod(assignName,"Count of Selected Docs","1");
		baseClass.passedStep("User can select single doc and bulk assign to existing assignment");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 27/09/2022 TestCase Id:RPMXCON-55000
	 * Description :Verify that when a user configures MasterDate and DocFileType filters and 
	 * selects  check-boxes manually and navigates Doc-Explorer to DocList then documents gets 
	 * loaded on DocList screen.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-55000",enabled = true, groups = { "regression" })
	public void verifyMasterDateAndDocFileTypeFilter() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-55000");
		baseClass.stepInfo("Verify masterdate and docfiletype filter on doc explorer");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		docExplorer.navigateToDocExplorerPage();
		docList = new DocListPage(driver);
		baseClass.stepInfo("Add docfiletype filter");
		baseClass.waitForElement(docExplorer.getDocExp_GetDocFIleTypeFilter());
		docExplorer.getDocExp_GetDocFIleTypeFilter().waitAndClick(10);
		docList.include("Spreadsheet");
		baseClass.passedStep("docfiletype filter added");
		baseClass.stepInfo("Add masterdate filter and apply");
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.stepInfo("Select all checkboxes");
		int docsCount =docExplorer.getDocumentCountFromListView();
		docExplorer.selectDocument(docsCount);
		baseClass.stepInfo("select action as view in doclist and verify");
		docExplorer.navigateToDoclistFromDocExplorer();
		baseClass.passedStep("Able to navigate from docexplorer to doclist without any errors");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 27/09/2022 TestCase Id:RPMXCON-54999
	 * Description :Verify that when a user configures MasterDate filter and
	 * selects  check-boxes manually and navigates Doc-Explorer to DocList then documents gets 
	 * loaded on DocList screen.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54999",enabled = true, groups = { "regression" })
	public void verifyMasterDateFilter() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54999");
		baseClass.stepInfo("Verify masterdate filter on doc explorer");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		docExplorer.navigateToDocExplorerPage();
		docList = new DocListPage(driver);
		baseClass.stepInfo("Add masterdate filter and apply");
		docList.dateFilter("after", "2020/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.stepInfo("Select all checkboxes");
		int docsCount =docExplorer.getDocumentCountFromListView();
		docExplorer.selectDocument(docsCount);
		baseClass.stepInfo("select action as view in doclist and verify");
		docExplorer.navigateToDoclistFromDocExplorer();
		baseClass.passedStep("User able to navigate from docexplorer to doclist without any errors");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 27/09/2022 TestCase Id:RPMXCON-54656
	 * Description :Verify the family field values from list view and should be displayed correctly
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54656",enabled = true, groups = { "regression" })
	public void verifyFamilyFieldValuesFromListView() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54656");
		baseClass.stepInfo("Verify the family field values from list view and should be displayed correctly");
		String[] familyField= {"S","P","C","PC"};
		String[] values = {"Standalone","Parent","Child","Parent and Child"};
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("verify fields available in list view");
		docExplorer.verifyDocExpFieldAvailabilityInListView();
		baseClass.stepInfo("Verify family field values from list view");
		docExplorer.verifyFamilyFieldValuesInDocExp(familyField, values);
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-47227
	 * @throws Exception
	 * @Description Shared Steps: Doc Explorer Login Screen.
	 */
	@Test(description = "RPMXCON-47227", enabled = true, groups = { "regression" })
	public void verifyDocExplorerLoginScreen() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47227");
		baseClass.stepInfo("Shared Steps: Doc Explorer Login Screen.");
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		//verify home page display
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep("Application Home Page is displayed successfully");
		} else {
			baseClass.failedStep("Application Home Page is not displayed ");
		}
		//Click Left menu docexplorer icon
 		driver.waitForPageToBeReady();
 		baseClass.waitForElement(docExplorer.getDocExplorerTab());
		docExplorer.getDocExplorerTab().waitAndClick(5);
		if(docExplorer.getPresentDocCount().Displayed()) {
			baseClass.passedStep("Doc Explorer Page is appeared Successfully");
		}else {
			baseClass.failedStep("Doc Explorer Page is not appeared.");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-54627
	 * @throws Exception
	 * @Description Verify that user can select all documents from current page from List view, and select action as Bulk Release from Actions drop down to Release documents.
	 */
	@Test(description = "RPMXCON-54627", enabled = true, groups = { "regression" })
	public void verifySlectAllDocsInCurrentpageBulkRelease() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54627");
		baseClass.stepInfo("Verify that user can select all documents from current page from List view, and select action as Bulk Release from Actions drop down to Release documents.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		SoftAssert softAssert=new SoftAssert(); 
		DocListPage docList=new DocListPage(driver);
		
		//verify DocExplorer  home Page
		softAssert.assertTrue(docExplorer.getPresentDocCount().isDisplayed());
		baseClass.passedStep("User is logged in successfully and it is \"Doc Explorer\" page ");
		 
		//Select folder In Treeview
		driver.waitForPageToBeReady();
		docExplorer.getAllDocumentsCount().waitAndClick(5);
		baseClass.stepInfo(" Successfully clicked a folder, it show the docs corresponding to that folder in the list view on the right hand side.");
		
		//Select All Documents verify popup
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		String Actualmsg =docList.getSelectDocsOnAllPages().getText();
		String expectmsg ="Do you want to make selection across all the pages?";
		softAssert.assertEquals(Actualmsg, expectmsg);
		baseClass.passedStep("Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");
		
		//Click OK btn
		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");
		
		//BulkRelease
		docExplorer.docExplorerRelease(Input.securityGroup);
		baseClass.passedStep("Success message is displayed as Success! Records saved successfully and selected documents from current page is released to the selected security group(s)");
		softAssert.assertAll();
		loginPage.logout();
			
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-54632
	 * @throws Exception
	 * @Description Verify that user can select all documents from all pages from List view, and select action as Export Data from Actions drop down to export.
	 */
	@Test(description = "RPMXCON-54632", enabled = true, groups = { "regression" })
	public void verifySlectAllDocsInCurrentpageExportData() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54632");
		baseClass.stepInfo("Verify that user can select all documents from all pages from List view, and select action as Export Data from Actions drop down to export.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		SoftAssert softAssert=new SoftAssert(); 
		DataSets data = new DataSets(driver);
		DocListPage docList=new DocListPage(driver);
		
		//verify DocExplorer  home Page
		softAssert.assertTrue(docExplorer.getPresentDocCount().isDisplayed());
		baseClass.passedStep("User is logged in successfully and it is \"Doc Explorer\" page ");
		 
		//Select folder In Treeview
		driver.waitForPageToBeReady();
		docExplorer.getAllDocumentsCount().waitAndClick(5);
		baseClass.stepInfo(" Successfully clicked a folder, it show the docs corresponding to that folder in the list view on the right hand side.");
		
		//Select All Documents verify popup
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		String Actualmsg =docList.getSelectDocsOnAllPages().getText();
		String expectmsg ="Do you want to make selection across all the pages?";
		softAssert.assertEquals(Actualmsg, expectmsg);
		baseClass.passedStep("Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");
		
		//Click OK btn
		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");
		
		//Export data
		docExplorer.docExplorerexportData();
		driver.waitForPageToBeReady();
		data.getBullHornIcon().waitAndClick(5);
		String downloadMsg = data.getNotificationMsg().getText();
		String expected="Your export is ready please click here to download";
		softAssert.assertEquals(downloadMsg, expected);
		baseClass.passedStep("Notification is displayed and on click of the same download the file  Make sure that in downloaded file is selected all documents from all pages");
		softAssert.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:29/09/2022 RPMXCON-54638
	 * @throws Exception
	 * @Description Verify that user should be able to remove all documents from Tag
	 *              from Doc Explorer.
	 */
	@Test(description = "RPMXCON-54638", enabled = true, groups = { "regression" })
	public void verifyRemoveAllDocumentsFromTagInDocExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54638");
		baseClass.stepInfo("Verify that user should be able to remove all documents from Tag from Doc Explorer.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagAndFol = new TagsAndFoldersPage(driver);
		String random = "Tag" + Utility.dynamicNameAppender();
		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random, Input.securityGroup);

		// Select folder In Treeview
		driver.waitForPageToBeReady();
		docExplorer.navigateToDocExplorerURL();
		docExplorer.getAllDocumentsCount().waitAndClick(5);
		baseClass.stepInfo(
				" Successfully clicked a folder, it show the docs corresponding to that folder in the list view on the right hand side.");

		baseClass.stepInfo("Select a mulitple document and select bulk Tag action");
		docExplorer.selectDocument(10);

		docExplorer.selectDocAndBulkTag(random);
		baseClass.passedStep("Success message is displayed, Documents should be Untagged from the selected tag");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:29/09/2022 RPMXCON-54691
	 * @throws Exception
	 * @Description Verify that “DocFileType” Filter with "Exclude" functionality is
	 *              working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54691", enabled = true, groups = { "regression" })
	public void verifyDocFileTypeWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54691");
		baseClass.stepInfo(
				"Verify that “DocFileType” Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random = "Document";
		String random1 = "Image";

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeDocFileTypeFilter(random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForDocFileType();

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeDocFileTypeFilter(random1);

		baseClass.stepInfo("Perform another exclude filter by DocFileType");
		docExplorer.performUpdateExculdeDocFileTypeFilter(random);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForDocFileType();

	
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:29/09/2022 RPMXCON-54661
	 * @throws Exception
	 * @Description Verify that “DocFileType” Column header Filter is working
	 *              correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54661", enabled = true, groups = { "regression" })
	public void verifyDocFileTypeColumnheaderInDocExploerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54661");
		baseClass.stepInfo(
				"Verify that “DocFileType” Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random1 = "Document";

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.DocumentInDocFileTypeFilters(random1);

		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 29/09/2022 TestCase Id:RPMXCON-54651 Description
	 * :Verify the default list view from doc explorer.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54651", enabled = true, groups = { "regression" })
	public void verifyDefaultListViewInDocExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54651");
		baseClass.stepInfo("Verify the default list view from doc explorer");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		docExplorer.navigateToDocExplorerPage();
		//Select Default All documents form Treeview 
		int treeViewCount = docExplorer.getFolderCountFromTreeView("1");
		baseClass.stepInfo("Docs count in folder tree view:" + treeViewCount);
		
		//ListView count
		int listViewCount = docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("Docs count in rightside list view:" + listViewCount);
		
		//verify all documents display
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
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
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
	
	
}
