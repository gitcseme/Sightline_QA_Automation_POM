package testScriptsRegressionSprint22;

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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
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
