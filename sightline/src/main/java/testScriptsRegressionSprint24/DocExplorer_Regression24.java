package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.ElementCollection;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression24 {

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
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54735
	 * @throws Exception
	 * @Description Verify that “EmailReceipients” Column header Filter with CJK characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54735", enabled = true, groups = { "regression" })
	public void verifyEmailReceipientsColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54735");
		baseClass.stepInfo(
				"Verify that “EmailReceipients” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] cjkChar = {"好","人","良い","一个","一個"};
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);
		
		//verify EmailRecipient names in CJK Chars
		docexp.verifyEmailRecipientValuesInDocExp(cjkChar);
		baseClass.passedStep("Verify that “EmailReceipients” Column header Filter with CJK characters is working Successfully on Doc Explorer list");
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54732
	 * @throws Exception
	 * @Description Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54732", enabled = true, groups = { "regression" })
	public void verifyEmailSubjectFilenameColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54732");
		baseClass.stepInfo(
				"Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver); 
		String[] cjkChar = {"让","我","打","电","话","给","你","延","长","石","油","集","团"};
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);
		
		//verify EmailRecipient names in CJK Chars
		docexp.verifyEmailSubjectFilenameValuesInDocExp(cjkChar);
		baseClass.passedStep("Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working Successfully on Doc Explorer list");
		
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani  date: 17/10/2022 TestCase Id:RPMXCON-54633
	 * Description :Verify that user can select a single document from List view, and select action as Bulk Assign from Actions drop down to Assign document to new assignment.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54633",enabled = true, groups = { "regression" })
	public void verifySingleDocumentBulkAssignInListViewDoxExplorer() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54633");
		baseClass.stepInfo("Verify that user can select a single document from List view, and select action as Bulk Assign from Actions drop down to Assign document to new assignment");
		String assignName = "assign"+Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);

		//Login As RMU 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk assign action");
		docExplorer.selectDocument(1);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");
		
		assignment.assignwithSamplemethod(assignName,"Count of Selected Docs","1");
		baseClass.passedStep("User can select single doc and bulk assign to new assignment");
		
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani  date: 17/10/2022 TestCase Id:RPMXCON-54634
	 * Description :Verify that user can select multiple documents from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54634",enabled = true, groups = { "regression" })
	public void verifyMultipleDocumentBulkAssignInListViewDoxExplorer() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54634");
		baseClass.stepInfo("Verify that user can select multiple documents from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment");
		String assignName = "assign"+Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);
		
		//Login As RMU 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk assign action");
		docExplorer.selectDocument(5);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");
		
		assignment.assignwithSamplemethod(assignName,"Count of Selected Docs","5");
		baseClass.passedStep("User can select single doc and bulk assign to existing assignment");
		loginPage.logout();
	
	}

	
	/**
	 * @author N/A
	 * @Description : Verify that error message does not display and application accepts -
	 *                when 'Report Name' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Save Report 
	 *                [RPMXCON-65051]
	 */
	@Test(description = "RPMXCON-65051", groups = { "regression" }, enabled = true)
	public void verifyErrorMsgSaveReport() throws Exception {
		String custReportName = "< > * ; ‘ / ( ) # &" + Utility.randomCharacterAppender(4);
		
		baseClass.stepInfo("RPMXCON - 65051 ");
		baseClass.stepInfo("To Verify that error message does not display and application accepts - "
				+ "when 'Report Name' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Save Report");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As : " + Input.pa1userName);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		
		docExp.docExpExportDataSaveReport(custReportName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		ReportsPage report = new ReportsPage(driver);
		report.navigateToReportsPage("");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getThisLink(custReportName));
		
		if(report.getThisLink(custReportName).isElementAvailable(10)) {
			baseClass.passedStep("Application accept 'Report Name :" + custReportName + "Successfully..");
		} else {
			baseClass.failedStep("Application Not accept 'Report Name :" + custReportName);
		}
		
		report.deleteCustomReport(custReportName);
		baseClass.passedStep("Verified - that error message does not display and application accepts - "
				+ "when 'Report Name' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Save Report");
    		loginPage.logout();
	}

	/**
	 * @author Brundha.T Id:RPMXCON-54644 
	 * Description Verify the page navigation
	 * from the list view of doc explorer page
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54644", enabled = true, groups = { "regression" })
	public void verifyingNavigationPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54644");
		baseClass.stepInfo("Verify the page navigation from the list view of doc explorer page");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		int RowHeader=baseClass.getIndex(docExplorer.getTableHeader(),"DOCID");
		
		docExplorer.verifyingNavigationOption(RowHeader,docExplorer.getNavigationPageNumber("2"),"NotCompareEqual");
		docExplorer.verifyingNavigationOption(RowHeader,docExplorer.getLastPage(),"yes");
		String NextBtn=docExplorer.getNavigationBtn("Next").GetAttribute("class");
		System.out.println(NextBtn);
		
		baseClass.compareTextViaContains(NextBtn, "disabled", "Previous Button is disabled", "Previous button is not disabled");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docExplorer.verifyingNavigationOption(RowHeader,docExplorer.getFirstPage(),"Compare");
		String PreviousBtn=docExplorer.getNavigationBtn("Previous").GetAttribute("class");
		System.out.println(PreviousBtn);
		baseClass.compareTextViaContains(PreviousBtn, "disabled", "Previous Button is disabled", "Previous button is not disabled");

		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani date: 20/10/2022 TestCase Id:RPMXCON-54596 
	 * Description:Verify when user selects multiple folders from the tree view
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54596", enabled = true, groups = { "regression" })
	public void verifySelectMultipleFoldersFormTreeView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54596");
		baseClass.stepInfo("Verify when user selects multiple folders from the tree view");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		//Select multiple folder and get count
		ElementCollection Table = driver.FindElementsByXPath("//div[@id='divNodeTree']//ul//li");
		List<WebElement> Tag = Table.FindWebElements();
		
		docExplorer.getfolderFromTreeByNumber("7").waitAndClick(5);
		int fold = docExplorer.getcount("3");
		Actions actions = new Actions(driver.getWebDriver());
		actions.keyDown(Keys.LEFT_CONTROL).click(Tag.get(2));
		int fold1 = docExplorer.getcount("2");
		actions.click(Tag.get(3));
		int fold2 = docExplorer.getcount("3");
		actions.click(Tag.get(4));
		int fold3 = docExplorer.getcount("4");
		actions.keyUp(Keys.LEFT_CONTROL).build().perform();
		int treeViewCount = fold+fold1 + fold2 + fold3;
		System.out.println(treeViewCount);
		baseClass.stepInfo("Docs count in multiple folder from treeview:" + treeViewCount);

		int listViewCount = docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("Docs count in rightside list view:" + listViewCount);
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 19/10/2022 TestCase Id:RPMXCON-54601
	 * Description :Verify the Tree View for new project when Dataset is not uploaded and
	 * Ingestion is not completed
	 */
	@Test(description = "RPMXCON-54601", enabled = true, groups = { "regression" })
	public void verifyTreeViewNewProjectDatasetIsZero() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54601");
		baseClass.stepInfo(
				"Verify the Tree View for new project when Dataset is not uploaded and Ingestion is not completed");
		SoftAssert softAssert = new SoftAssert();
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.NonDomainProject);

		int expectedCount = 0;
		int ActualCount = docExplorer.getAllDocumentFolderCountFromTreeView();
		baseClass.stepInfo("Docs count in folder tree view:" + ActualCount);
		softAssert.assertEquals(expectedCount, ActualCount);
		baseClass.passedStep("Tree view is displayed with 'All Document' having count as zero ");
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 20/10/2022 TestCase Id:RPMXCON-54715 
	 * Description:Verify the default menu when SA/DA impersonates as PA/RMU
	 */
	@Test(description = "RPMXCON-54715", enabled = true, groups = { "regression" })
	public void verifyDefaultMenuSADAImpersonateAsPARMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54715 DocExplorer");
		baseClass.stepInfo("Verify the default menu when SA/DA impersonates as PA/RMU");
		SoftAssert softAssert = new SoftAssert();
		DomainDashboard domainDash = new DomainDashboard(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");
		// impersonate SA to PA
		baseClass.stepInfo("Impersonate As SA to PA");
		baseClass.impersonateSAtoPA();
		// verify docexplorer leftmenu panel
		docExplorer.verifyDocExplorerInLeftMenu();
		baseClass.stepInfo("Doc Explorer is presented in the left menu panel");
		// check docexplorer above datasets
		docExplorer.verifyDocExplorerAboveDatasets();
		baseClass.stepInfo("Doc Explorer is present in the above datasets");
		// docexplorerpage as default in PA
		docExplorer.verifyDocExplorerPageHeader();
		baseClass.stepInfo("After impersonating as PAU, \"Doc Explorer\" page is shown as default.");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");
		// impersonate SA to RMU
		baseClass.stepInfo("Impersonate As SA to RMU");
		baseClass.impersonateSAtoRMU();
		// verify docexplorer leftmenu panel
		docExplorer.verifyDocExplorerInLeftMenu();
		baseClass.stepInfo("Doc Explorer is presented in the left menu panel");
		// check docexplorer after dashboard
		docExplorer.verifyDocExplorerBelowDashBoard();
		baseClass.stepInfo("Doc Explorer is present in the after dashBoard in RMU");
		// DashBoard page as default in RMU
		softAssert.assertTrue(domainDash.getUserHomePage().isDisplayed());
		baseClass.passedStep("After impersonating as RMU, Dashboard page is shown by default.");
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		// impersonate DA to PA
		baseClass.stepInfo("Impersonate As DA to PA");
		baseClass.impersonateDAtoPA();
		// verify docexplorer leftmenu panel
		docExplorer.verifyDocExplorerInLeftMenu();
		baseClass.stepInfo("Doc Explorer is presented in the left menu panel");
		// check docexplorer above datasets
		docExplorer.verifyDocExplorerAboveDatasets();
		baseClass.stepInfo("Doc Explorer is present in the above datasets");
		// docexplorerpage as default in PA
		docExplorer.verifyDocExplorerPageHeader();
		baseClass.stepInfo("After impersonating as PAU, \"Doc Explorer\" page is shown as default.");
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		// impersonate DA to RMU
		baseClass.stepInfo("Impersonate As DA to RMU");
		baseClass.impersonateDAtoRMU();
		// verify docexplorer leftmenu panel
		docExplorer.verifyDocExplorerInLeftMenu();
		baseClass.stepInfo("Doc Explorer is presented in the left menu panel");
		// check docexplorer after dashboard
		docExplorer.verifyDocExplorerBelowDashBoard();
		baseClass.stepInfo("Doc Explorer is present in the after dashBoard in RMU");
		// DashBoard page as default in RMU
		softAssert.assertTrue(domainDash.getUserHomePage().isDisplayed());
		baseClass.passedStep("After impersonating as RMU, Dashboard page is shown by default.");
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:20/10/2022 RPMXCON-54728                
	 * @throws Exception
	 * @Description Verify that “EmailSubject/Filename” Column header Filter with special characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54728", enabled = true, groups = { "regression" })
	public void verifyEmailSubjectFilenameColumnHeaderFilterWithSpecialChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54728");
		baseClass.stepInfo(
				"Verify that “EmailSubject/Filename” Column header Filter with special characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] specialChars = {"~","!","@","#","$","%","&",";","(",")","_","-","+","=","[","]","?","/",".","'" };

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// verify EmailRecipient names in Special Chars
		docexp.verifyEmailSubjectFilenameValuesInDocExp(specialChars);
		baseClass.passedStep(
				"Verify that “EmailSubject/Filename” Column header Filter with special characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:20/10/2022 RPMXCON-54700
	 * @throws Exception
	 * @Description Verify that “Comments” Filter with "Exclude" functionality is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54700", enabled = true, groups = { "regression" })
	public void verifyCommentsWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54700");
		baseClass.stepInfo(
				"Verify that “Comments” Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		CommentsPage comments=new CommentsPage(driver);
		String random = Input.reviewed+ Utility.dynamicNameAppender();
		String random1 = "Completed"+ Utility.dynamicNameAppender();
		
		comments.navigateToCommentsPage();
		comments.AddComments(random);
		comments.AddComments(random1);

		docExplorer.navigateToDocExplorerPage();
		String Docs = docExplorer.getDocExp_DocID().getText();
		baseClass.stepInfo("Perform exclude filter by Comments");
		docExplorer.performExculdeCommentsFilter(random,null);

		baseClass.stepInfo("Verify documents after applying exclude functionality by Comments");
		docExplorer.verifyExcludeFunctionlityForComments(Docs);

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by Comments");
		docExplorer.performExculdeCommentsFilter(random,random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by Comments");
		docExplorer.verifyExcludeFunctionlityForComments(Docs);

		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T Id:RPMXCON-54635 Description :Verify that user can select all
	 *         documents from page from List view, and select action as Bulk Assign
	 *         from Actions drop down to Assign documents to new assignment
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54635", enabled = true, groups = { "regression" })
	public void verifyDocumentInPagesAfterBulkAssign() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54635");
		baseClass.stepInfo(
				"Verify that user can select all documents from page from List view, and select action as Bulk Assign from Actions drop down to Assign documents to new assignment");

		AssignmentsPage Assign = new AssignmentsPage(driver);
		String assigname = "assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Navigating to Docexplorer page");
		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		docExplorer.getfolderFromTreeByNumber("1").waitAndClick(10);
		docExplorer.SelectingAllDocuments("No");
		driver.scrollingToBottomofAPage();
		String count = docExplorer.getDocExp_DocumentList_info().getText().toString();
		String []Doc=count.split(" ");
		String ListViewCount=Doc[3];
		System.out.println(ListViewCount);
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();
		
		baseClass.stepInfo("Selecting new Assignment with sample method");
		Assign.selectNewAssignmentWithSampleMethod("Count of Selected Docs",ListViewCount,true);
		Assign.quickAssignCreation(assigname, Input.codeFormName);
		String AssignmentCount=Assign.selectAssignmentToView(assigname);
		baseClass.digitCompareEquals(Integer.valueOf(ListViewCount),Integer.valueOf(AssignmentCount), "Document count is displayed in assignment",
				"DocCount is Not Displayed as expected");

	}

	/**
	 * @author Brundha.T Id:RPMXCON-54636 Description:Verify that user can select
	 *         all documents from all pages from List view, and select action as
	 *         Bulk Assign from Actions drop down to Assign documents to new
	 *         assignment
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54636", enabled = true, groups = { "regression" })
	public void verifyDocumentInAllPagesAfterBulkAssign() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54636");
		baseClass.stepInfo(
				"Verify that user can select all documents from all pages from List view, and select action as Bulk Assign from Actions drop down to Assign documents to new assignment");

		AssignmentsPage Assign = new AssignmentsPage(driver);
		String assigname = "assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		docExplorer.getSelectAllDocuments().waitAndClick(10);
		driver.waitForPageToBeReady();
		docExplorer.SelectingAllDocuments("yes");
		int ListViewCount= docExplorer.getDocumentCountFromListView();
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();
		Assign.selectNewAssignmentWithSampleMethod("Count of Selected Docs",String.valueOf(ListViewCount),true);
		Assign.quickAssignCreation(assigname, Input.codeFormName);
		String DocCountInAssignMent = Assign.selectAssignmentToView(assigname);
		baseClass.digitCompareEquals(Integer.valueOf(DocCountInAssignMent), ListViewCount, "Document count is displayed in assignment",
				"DocCount is Not Displayed as expected");

	}
	
	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54991
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and
	 *              EmailRecipientNames filters and ticks the 'Select All' check-box
	 *              and navigates Doc-Explorer to DocView then documents gets loaded
	 *              on DocView screen.
	 */
	@Test(description = "RPMXCON-54991", enabled = true, groups = { "regression" })
	public void verifyMasterDateEmailRecipientNamesDocsGetsLoadedOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54991");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and EmailRecipientNames filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		String EmailRecipienetname = Input.emailReciepientName2;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getEmailRecipientColumnTextField());
		docexp.getEmailRecipientColumnTextField().SendKeys(EmailRecipienetname);
		docexp.getApplyFilter().waitAndClick(10);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(8);
		docexp.getDocExp_SelectAllDocs().waitAndClick(5);
		baseClass.stepInfo("Select all checkboxes in docexp");
		driver.waitForPageToBeReady();
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docview.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docview.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("navigated to Docview page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error displayed");
		}
	}
	
	
	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54990
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and EmailSubject
	 *              filters and ticks the 'Select All' check-box and navigates
	 *              Doc-Explorer to DocView then documents gets loaded on DocView
	 *              screen.
	 */
	@Test(description = "RPMXCON-54990", enabled = true, groups = { "regression" })
	public void verifyMasterDateEmailSubjectsDocsGetsLoadedOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54990");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and EmailSubject filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME"));
		docexp.getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME").SendKeys("Proximity");
		docexp.getApplyFilter().waitAndClick(10);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Select all checkboxes");
		driver.waitForPageToBeReady();
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docview.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docview.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("Docview page is displayed without any runtime error");

		} else {
			baseClass.failedStep("erroris  displayed");
		}
	}

	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54997
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and EmailSubject
	 *              filters and ticks the 'Select All' check-box and navigates
	 *              Doc-Explorer to DocList then documents gets loaded on DocList
	 *              screen.
	 */
	@Test(description = "RPMXCON-54997", enabled = true, groups = { "regression" })
	public void verifyMasterDateEmailSubjectsDocsGetsLoadedOnDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54997");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and EmailSubject filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME"));
		docexp.getDocExp_EmailSubFileSearchName("EMAILSUBJECT/FILENAME").SendKeys("Proximity");
		docexp.getApplyFilter().waitAndClick(10);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Select all checkboxes");
		driver.waitForPageToBeReady();
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.navigateToDoclistFromDocExplorer();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.verifyUrlLanding(Input.url + "en-us/Document/DocList", " on doc List page", "Not on doc List page");
		if (docList.getHeaderText().isElementPresent()) {
			baseClass.passedStep("Navigated to DocList page is displayed without any runtime error");

		} else {
			baseClass.failedStep("error is displayed");
		}
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
