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
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression23_24 {

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
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54624 Description
	 * :Verify that user can select all documents from all pages from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54624", enabled = true, groups = { "regression" })
	public void verifySelectAllDocumentBulkAssignInExistingAssignFromListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54624");
		baseClass.stepInfo(
				"Verify that user can select all documents from all pages from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment.");
		String assignName = "assign" + Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a All document and select bulk assign action");
		docExplorer.SelectingAllDocuments("No");
		driver.scrollingToBottomofAPage();
		String count = docExplorer.getDocExp_DocumentList_info().getText().toString();
		String []Doc=count.split(" ");
		String ListViewCount=Doc[3];
		System.out.println(ListViewCount);
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");

		assignment.assignwithSamplemethod(assignName, "Count of Selected Docs",ListViewCount);
		baseClass.passedStep("User can select All Document and bulk assign to existing assignment");
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54637 
	 * Description:Verify that user should be able to remove documents from Folder from Doc Explorer.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54637", enabled = true, groups = { "regression" })
	public void verifyRevomeDocumentsFromFolderDocExplorerPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54637");
		baseClass.stepInfo(
				"Verify that user should be able to remove documents from Folder from Doc Explorer.");
		TagsAndFoldersPage tagAndFolder=new TagsAndFoldersPage(driver);
		String folderName="folder"+ Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
	
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		
		tagAndFolder.navigateToTagsAndFolderPage();
		tagAndFolder.CreateFolderInRMU(folderName);
		
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulkFolder in unfolder action");
		docExplorer.selectDocument(10);
		docExplorer.bulkFolderExistingInUnFolderDocs(folderName);
		baseClass.passedStep(
				"Records saved successfully  Documents is unfoldered from the selected folder Successfully");
		
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagAndFolder.verifyFolderDocCount(folderName, 0);

		loginPage.logout();
		
	}
	
	/**
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54640 Description
	 * :Verify that user should be able to unassign documents from assignment(s)
	 * from doc explorer page.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54640", enabled = true, groups = { "regression" })
	public void verifyUnAssignDocsFromAssignmentFromDocExplorerPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54640");
		baseClass.stepInfo(
				"Verify that user should be able to unassign documents from assignment(s) from doc explorer page.");
		String assignName = "assign" + Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SavedSearch saveSearch =new SavedSearch(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk unassign action");
		docExplorer.selectDocument(10);
		docExplorer.docExp_BulkAssign();
		assignment.UnassignDocsfromExistingAssgn(assignName);
		int initialBg = baseClass.initialBgCount();

		// verify Notification 
		baseClass.checkNotificationCount(initialBg,1);
		baseClass.waitForElement(baseClass.getBullHornIcon());
		baseClass.getBullHornIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String actStatus = saveSearch.getNotificationStatus(1).getText();
		System.out.println(actStatus);
		String expStatus = "Your Bulkaction-Unassign with Bulkaction-Unassign Id";

		if (actStatus.contains(expStatus) && actStatus.contains("COMPLETED")) {
			baseClass.passedStep("selected documents is unassigned from the selected assignment");
		} else {
			baseClass.failedStep("selected documents is not unassigned from the selected assignmen");
		}
		baseClass.passedStep(
				"Verified - as an RMU login into the Application, When user applying Unassign documents from Assignment,"
						+ " user will be able to see the background task in notification window");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-54697
	 * @throws Exception
	 * @Description Verify that “IngestionName” (Only For PA) Filter with "Exclude"
	 *              functionality is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54697", enabled = true, groups = { "regression" })
	public void verifyIngestionNameWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54697");
		baseClass.stepInfo(
				"Verify that “IngestionName” (Only For PA) Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random = Input.ingestionAutomationAllSource;
		String random1 = Input.JanMultiPTIFF;

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeIngestionNameFilter(random);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForIngestionName();

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeIngestionNameFilter(random);
		docExplorer.performUpdateExculdeIngestionNameFilter(random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForIngestionName();

		loginPage.logout();

	}
	/**
	 * Author : date: 06/10/2022 TestCase Id:RPMXCON-54681 Description
	 * :Verify that “Folders” Filter with "Include" functionality is working
	 * correctly on Doc Explorer list.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54681", enabled = true, groups = { "regression" })
	public void verifyFoldersIncludeFunctionalityWorking() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54681");
		baseClass.stepInfo(
				"Verify that “Folders” Filter with \"Include\" functionality is working correctly on Doc Explorer list.");

		String random = Input.atternoyClient + Utility.dynamicNameAppender();
		String random2 = Input.confidential + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random, Input.securityGroup);

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random2, Input.securityGroup);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.waitForPageToBeReady();
		String Docs = docexp.getDocExp_DocID().getText();
		
		docexp.selectDocumentsAndBulkFolder(1, random);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(1, random2);

		baseClass.stepInfo("Perform include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performIncludeFolderFilter(random);

		baseClass.stepInfo("Verify documents after applying include functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);

		baseClass.stepInfo("Refresh page");
		docexp.refreshPage();

		baseClass.stepInfo("Perform include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performIncludeFolderFilter(random);

		baseClass.stepInfo("Perform another include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performUpdateIncludeFolderFilter(random2);

		baseClass.stepInfo("Verify documents after applying include functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);
		loginPage.logout();
	}

	/**
	 * Author : date: 06/10/2022 TestCase Id:RPMXCON-54956 Description
	 * :Verify that Include filter functionality works properly when TAG name
	 * contains word between on Concept Explorer Report screen
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54956", enabled = true, groups = { "regression" })
	public void verifyIncludeFilterFunctionalityWorksTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54956");
		baseClass.stepInfo(
				"Verify that Include filter functionality works properly when TAG name contains word between on Concept Explorer Report screen");

		String Tagname = " Docs between 1 and 5  " + Utility.dynamicNameAppender();
		SessionSearch sessionSearch = new SessionSearch(driver);
		CommunicationExplorerPage communiExplore = new CommunicationExplorerPage(driver);
		ConceptExplorerPage CEPage = new ConceptExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		sessionSearch.basicContentSearch(Input.testData1);
		String Doccount = sessionSearch.getPureHitsCount().getText();
		sessionSearch.bulkTag(Tagname);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CEPage.ValidateConceptExplorerreport();
		communiExplore.getfilterDocumentBy("Tags", true, Tagname);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);

		// verify Include filter is work
		baseClass.waitForElement(communiExplore.getReportPanelText());
		String Tagcount = communiExplore.getReportPanelText().getText();
		if (Tagcount.contains(Doccount)) {
			baseClass.passedStep(
					"Include filter functionality is work properly and Records filtered on Concept Explorer Reportscreen ");

		} else {
			baseClass.failedStep("Include filter is not worked");

		}
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/10/2022 RPMXCON-54953
	 * @throws Exception
	 * @Description Verify that filter functionality works properly when Folder name
	 *              contains word between on SubTally screen.
	 */
	@Test(description = "RPMXCON-54953", enabled = true, groups = { "regression" })
	public void verifyfilterFunctionalityOnSubTallyScreen() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54953");
		baseClass.stepInfo(
				"Verify that filter functionality works properly when Folder name contains word between on SubTally screen");
		Utility utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		TagsAndFoldersPage tagAndFol = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		baseClass.stepInfo("Create a new Folder contains word between");
		tagAndFol.CreateFolder(random1, Input.securityGroup);

		baseClass.stepInfo("Verify created folder is added to folder structure");
		driver.Navigate().refresh();
		tagAndFol.verifyFolderNameIsAddedToStructure(random1);

		baseClass.stepInfo("Select documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(10, random1);

		SecurityGroupsPage securityGroup = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Navigate to security group");
		securityGroup.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("Add Folder to security group");
		securityGroup.addFolderToSecurityGroup(Input.securityGroup, random1);

		TallyPage tally = new TallyPage(driver);

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
		tally.applyingSubTallyFolderField(random1);

		baseClass.stepInfo("Verify documents count by folder name subtally");
		tally.verifyDocumentsCountByFolderNameSubTally(random1);
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
