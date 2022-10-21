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

		baseClass.stepInfo("Test case Id: RPMXCON-54715");
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
