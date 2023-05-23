package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.Dimension;
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
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Phase2_Regression {

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
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-65050
	 * @throws Exception
	 * @Description Verify that error message does not display and application
	 *              accepts - when 'Share by email' entered with < > * ; ‘ / ( ) # &
	 *              ” from Doc Explorer > Export > Schedule Report.
	 */
	@Test(description = "RPMXCON-65050", enabled = true, groups = { "regression" })
	public void verifyMutliValueFieldaDisplayCustomColumn() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-65050");
		baseClass.stepInfo(
				"Verify that error message does not display and application accepts - when 'Share by email' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Schedule Report.");
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String Email = "new@email.com";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Go to DocExplorer Pge And ExportData and Schedule report");
		docExplorer.docExloerExportData(Input.pa1FullName, Email);

		baseClass.passedStep(
				"Error message is displayed Successfully when 'Share by email' entered with characters-> \r\n"
						+ "< > * ; ‘ / ( ) # &");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-54590
	 * @throws Exception
	 * @Description Verify the documents from "Doc Explorer" page for PA and RMU.
	 */
	@Test(description = "RPMXCON-54590", enabled = true, groups = { "regression" })
	public void verifyDocsFromDocExplorerPageInPAAndRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54590");
		baseClass.stepInfo("Verify the documents from \"Doc Explorer\" page for PA and RMU.");
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("DocExplorer Docs Are Published PAU");
		docExplorer.docExloerRelease(Input.securityGroup);
		baseClass.passedStep("Documents presented on the page is reflect the published docs in the project for a PAU");

		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Navigate to DocExplorer page");
		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		if (baseClass.text("ID000").isDisplayed()) {
			baseClass.passedStep(" Documents presented on the page is reflect the released docs in the SG for an RMU ");
		} else {
			baseClass.failedStep(
					" Documents presented on the page is not reflect the released docs in the SG for an RMU ");
		}
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("DocExplorer Docs Are unPublished RMU");
		docExplorer.docExloerUnRelease(Input.securityGroup);
		baseClass.passedStep("Documents presented on the page is reflect the unreleased docs in the SG for an RMU");

		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Navigate to DocExplorer page");
		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		if (baseClass.text("ID000").isDisplayed()) {
			baseClass.passedStep("Documents presented on the page is reflect the released docs in the SG for an RMU ");
		} else {
			baseClass.failedStep(
					" Documents presented on the page is not reflect the released docs in the SG for an RMU ");
		}
		loginPage.logout();
	}


	/**
	 * @author Vijaya.Rani ModifyDate:17/11/2022 RPMXCON-54986
	 * @throws Exception
	 * @Description Verify that after resize browser Doc Explorer grid does not
	 *              resize.
	 */
	@Test(description = "RPMXCON-54986", enabled = true, groups = { "regression" })
	public void verifyAfterBrowserDocExplorerGridNotResize() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54986");
		baseClass.stepInfo("Verify that after resize browser Doc Explorer grid does not resize.");

		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		String originalSize = docexp.getDocExplorerGrid().GetAttribute("style").trim();
		System.out.println(originalSize);
		baseClass.stepInfo(originalSize);
		loginPage.logout();

		baseClass.stepInfo("Set browser resolution as 1280*1024 ");
		Dimension pram1 = new Dimension(1280, 1024);
		driver.Manage().window().setSize(pram1);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		String changedSize = docexp.getDocExplorerGrid().GetAttribute("style").trim();
		System.out.println(changedSize);
		baseClass.stepInfo(changedSize);
		if (originalSize.equals(changedSize)) {
			baseClass.passedStep("The grid is not resize.");
		} else {
			baseClass.failedStep("The grid is resize.");
		}
		loginPage.logout();

	}

	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-54995
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate filter and ticks
	 *              the 'Select All' check-box and navigates Doc-Explorer to DocList
	 *              then documents gets loaded on DocList screen.
	 */
	@Test(description = "RPMXCON-54995", enabled = true, groups = { "regression" })
	public void verifyMasterDateSelectAllDocsGetsLoadedOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54995");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate filter and ticks the 'Select All' check-box and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");

		docexp.getApplyFilter().waitAndClick(10);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes as expected ");
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
			baseClass
					.passedStep("Navigated to DocList page is NOT displayed any run-time error on screen as expected.");

		} else {
			baseClass.failedStep("error is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54996
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and DocFileType
	 *              filters and ticks the 'Select All' check-box and navigates
	 *              Doc-Explorer to DocList then documents gets loaded on DocList
	 *              screen.
	 */
	@Test(description = "RPMXCON-54996", enabled = true, groups = { "regression" })
	public void verifyMasterDateDocFileTypeLoadedOnDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54996");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and DocFileType filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocList then documents gets loaded on DocList screen.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		String FileType = "Spreadsheet";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys(FileType);
		baseClass.waitForElement(docexp.getApplyFilter());
		docexp.getApplyFilter().waitAndClick(10);
		if (baseClass.text(FileType).isElementPresent()) {
			baseClass.passedStep("DocFileType filters has been configured on Doc Explorer screen as expected. ");

		} else {
			baseClass.failedStep("DocFileType filters is Not configured");
		}
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes");
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
		loginPage.logout();
	}

	/**
	 * @author Krishna ModifyDate:NA RPMXCON-54998
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and
	 *              EmailRecipientNames filters and ticks the 'Select All' check-box
	 *              and navigates Doc-Explorer to DocList then documents gets loaded
	 *              on DocList screen.
	 */
	@Test(description = "RPMXCON-54998", enabled = true, groups = { "regression" })
	public void verifyMasterDateEmailRecipientDocsGetsLoadedOnDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54998");
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
		baseClass.waitForElement(docexp.getEmailRecipientColumnTextField());
		docexp.getEmailRecipientColumnTextField().SendKeys(Input.emailReciepientName2);
		baseClass.waitForElement(docexp.getApplyFilter());
		docexp.getApplyFilter().waitAndClick(10);
		if (baseClass.text(Input.emailReciepientName2).isElementPresent()) {
			baseClass.passedStep(
					"EmailRecipientNames  filters has been get configured on Doc Explorer screen as expected. ");

		} else {
			baseClass.failedStep("EmailRecipientNames  filters is Not configured");
		}
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Select all checkboxes");
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
			baseClass.passedStep("Navigated to DocList page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:17/11/2022 RPMXCON-54729
	 * @throws Exception
	 * @Description Verify that “EmailAuthor” Column header Filter with special
	 *              characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54729", enabled = true, groups = { "regression" })
	public void verifyEmailAuthorColumnHeaderFilterWithSpecialChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54729");
		baseClass.stepInfo(
				"Verify that “EmailAuthor” Column header Filter with special characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] specialChars = { "@", ".", "(", ")", "-", "'", "," };

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);

		// verify EmailAuthor names in Special Chars
		docexp.verifyEmailAuthorValuesInDocExp(specialChars);
		baseClass.passedStep(
				"Verify that “EmailAuthor” Column header Filter with special characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 17/11/2022 TestCase Id:RPMXCON-54948
	 * Description:Verify that Include filter functionality works properly when
	 * Folder name contains word between on Doc Explorer screen.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54948", enabled = true, groups = { "regression" })
	public void verifyFoldersIncludeFunctionalityWorkingDocExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54948");
		baseClass.stepInfo(
				"Verify that Include filter functionality works properly when Folder name contains word between on Doc Explorer screen.");

		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random, Input.securityGroup);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.waitForPageToBeReady();
		String Docs = docexp.getDocExp_DocID().getText();

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(1, random);

		baseClass.stepInfo("Perform include filter by folder");
		driver.waitForPageToBeReady();
		docexp.performIncludeFolderFilter(random);

		baseClass.stepInfo("Verify documents after applying include functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);
		baseClass.passedStep(
				"Include filter functionality is work properly and Records is filtered according to earlier steps on Doc Explorer screen");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54951
	 * @throws Exception
	 * @Description Verify that filter functionality works properly when Folder name
	 *              contains word between on Tally screen.
	 */
	@Test(description = "RPMXCON-54951", enabled = true, groups = { "regression" })
	public void verifyFolderFilterFunctionalityOnTallyScreen() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54951");
		baseClass.stepInfo(
				"Verify that filter functionality works properly when Folder name contains word between on Tally screen.");
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

		baseClass.stepInfo("Select Tally by FolderName");
		tally.selectTallyByFolderName(random1);

		tally.verifyTallyChart();
		baseClass.passedStep(
				"Filter functionality is work properly and Records filtered according to earlier steps on Tally screen ");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:17/11/2022 RPMXCON-54992
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate filter and selects
	 *              check-boxes manually and navigates Doc-Explorer to DocView then
	 *              documents gets loaded on DocView screen.
	 */
	@Test(description = "RPMXCON-54992", enabled = true, groups = { "regression" })
	public void verifyMasterDateDocsGetsLoadedOnDocViewScreen() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54992");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate filter and selects check-boxes manually and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

		DocViewPage docView = new DocViewPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes");
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docView.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("navigated to Docview page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 17/11/2022 TestCase Id:RPMXCON-54910
	 * Description:Verify that Keyword should automatically added to New assignment
	 * through Bulk Assign from Doc Explorer.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54910", enabled = true, groups = { "regression" })
	public void verifyKeywordInNewAssignmentThroughBulkAssign() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54910");
		baseClass.stepInfo(
				"Verify that Keyword should automatically added to New assignment through Bulk Assign from Doc Explorer.");

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String AssignName1 = Input.randomText + Utility.dynamicNameAppender();
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		String keyword = "es";
		String colour2 = Input.KeyWordColour;
		String rgbCode2 = "rgb(0, 0, 255)";
		String HaxCode2 = "#0000ff";

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword one");
		keywordPage.addKeyword(keywordName1, keyword, colour2);

		baseClass.stepInfo(" Basic content search for");
		session.basicContentSearch(Input.testTenthDocId);

		baseClass.stepInfo("Create bulk assign with new assignment one with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignName1, Input.codingFormName);

		baseClass.stepInfo("unmapping all keywords from first assignment");
		assignmentPage.unmappingKeywordsFromAssignment(AssignName1);

		baseClass.stepInfo("aading keyword to first assignment");
		assignmentPage.addKeywordToAssignment(keywordName1);

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.selectAssignmentToViewinDocview(AssignName1);

		docView.verifyExpectedDocumentIsDisplayedInMiniDocList(Input.testTenthDocId);

		baseClass.stepInfo("verify highlight keyword in document for first assignment");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode2, HaxCode2);

		baseClass.stepInfo("Delete Assgnmnt Using Pagination");
		assignmentPage.deleteAssignment(AssignName1);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keywordName1);

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:17/11/2022 RPMXCON-54993
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and DocFileType
	 *              filters and selects check-boxes manually and navigates
	 *              Doc-Explorer to DocView then documents gets loaded on DocView
	 *              screen.
	 */
	@Test(description = "RPMXCON-54993", enabled = true, groups = { "regression" })
	public void verifyMasterDateDocFileTypeDocsGetsLoadedOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54993");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and DocFileType filters and selects check-boxes manually and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

		DocViewPage docView = new DocViewPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		String FileType = "Spreadsheet";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys(FileType);
		baseClass.waitForElement(docexp.getApplyFilter());
		docexp.getApplyFilter().waitAndClick(10);
		if (baseClass.text(FileType).isElementPresent()) {
			baseClass.passedStep("DocFileType filters has been configured on Doc Explorer screen as expected. ");

		} else {
			baseClass.failedStep("DocFileType filters is Not configured");
		}
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes");
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docView.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("navigated to Docview page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:22/11/2022 RPMXCON-54597
	 * @throws Exception
	 * @DescriptionVerify that on mouse hover of the folder, should display full
	 *                    name of the folder
	 */
	@Test(description = "RPMXCON-54597", enabled = true, groups = { "regression" })
	public void verifyingMouseHoverInFolderName() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54597");
		baseClass.stepInfo("Verify that on mouse hover of the folder, should display full name of the folder");
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		baseClass.stepInfo("Navigating to DocExplorer page");
		docExplorer.navigateToDocExplorerPage();

		baseClass.stepInfo("Mouse hover the folder name");
		int Count = docExplorer.getFolderName().size();
		System.out.println(Count);
		for (int i = Count; i > 1; i--) {
			String fol = docExplorer.getFolderNameText(i).getText();
			System.out.println("fol value" + fol);
			int SizeOfString = fol.length();
			System.out.println(SizeOfString);
			if (SizeOfString > 28) {
				Actions actions = new Actions(driver.getWebDriver());
				actions.moveToElement(docExplorer.getFolderNameText(i).getWebElement()).click().build().perform();
				baseClass.waitTime(3);
				String ToolTipText = docExplorer.getFolderNameToolTip(i).getText().trim();
				System.out.println("MouseHoverText" + ToolTipText);
				if (ToolTipText.equals(fol)) {
					baseClass.passedStep("Mouseover text of foldername is displayed with fullname");
				} else {
					baseClass.failedStep("Mouseover text of foldername  is not displayed as expected");
				}
				break;
			} else if (i == 1) {
				baseClass.failedStep("large foldername name is not displayed");
			} else {
				driver.waitForPageToBeReady();
			}
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/11/2022 RPMXCON-54989
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and DocFileType
	 *              filters and ticks the 'Select All' check-box and navigates
	 *              Doc-Explorer to DocView then documents gets loaded on DocView
	 *              screen.
	 */
	@Test(description = "RPMXCON-54989", enabled = true, groups = { "regression" })
	public void verifyMasterDateDocFileTypeDocsLoadedInDocViewScreen() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54989");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and DocFileType filters and ticks the 'Select All' check-box and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

		DocViewPage docView = new DocViewPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		String FileType = "Spreadsheet";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys(FileType);
		baseClass.waitForElement(docexp.getApplyFilter());
		docexp.getApplyFilter().waitAndClick(10);
		if (baseClass.text(FileType).isElementPresent()) {
			baseClass.passedStep("DocFileType filters has been configured on Doc Explorer screen as expected. ");

		} else {
			baseClass.failedStep("DocFileType filters is Not configured");
		}
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes");
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docView.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("navigated to Docview page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/11/2022 RPMXCON-54988
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate filter and selects
	 *              check-boxes manually and navigates Doc-Explorer to DocView then
	 *              documents gets loaded on DocView screenVerify that when a user
	 *              configures MasterDate filter and ticks the 'Select All'
	 *              check-box and navigates Doc-Explorer to DocView then documents
	 *              gets loaded on DocView screen..
	 */
	@Test(description = "RPMXCON-54988", enabled = true, groups = { "regression" })
	public void verifyMasterDateDocsLoadedInDocViewScreen() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54988");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate filter and selects check-boxes manually and navigates Doc-Explorer to DocView then documents gets loaded on DocView screenVerify that when a user configures MasterDate filter and ticks the 'Select All' check-box and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

		DocViewPage docView = new DocViewPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		docexp.navigateToDocExplorerPage();
		docList.dateFilter("after", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		driver.waitForPageToBeReady();
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		baseClass.stepInfo("Selected all checkboxes");
		if (docList.getYesAllPageDocs().isDisplayed()) {
			docList.getYesAllPageDocs().waitAndClick(5);
			docList.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(3);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docView.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("navigated to Docview page is displayed without any runtime error as expected");

		} else {
			baseClass.failedStep("error displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 22/11/2022 TestCase Id:RPMXCON-54982 Description
	 * :Doc List: Verify that results should be displayed if filtering columns
	 * (metadata) value contain special characters (angular brackets < > and also
	 * other special character).
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54982", enabled = true, groups = { "regression" })
	public void verifyDisplayFiltersInDocListWithSpecialChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54982");
		baseClass.stepInfo(
				"Doc List: Verify that results should be displayed if filtering columns (metadata) value contain special characters (angular brackets < > and also other special character).");

		sessionSearch = new SessionSearch(driver);
		docList = new DocListPage(driver);
		docExplorer = new DocExplorerPage(driver);
		String domain1 = "(#NOS OCRM FKNMS-ALL);";

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// EmailAuthorName Include
		baseClass.stepInfo("EmailAuthorName Include");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.include(domain1);
		driver.waitForPageToBeReady();
		if (baseClass.text(domain1).isDisplayed()) {
			baseClass.passedStep(
					"EmailAuthorName Include result is displayed if filtering value contain special characters ");
		} else {
			baseClass.failedStep("No record found");
		}

		docExplorer.refreshPage();
		// EmailAuthorName Exclude
		baseClass.stepInfo("EmailAuthorName Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain1);
		driver.waitForPageToBeReady();
		if (baseClass.text(domain1).isDisplayed()) {
			baseClass.passedStep(
					"EmailAuthorName Exclude result is displayed if filtering value contain special characters ");
		} else {
			baseClass.failedStep("No record found");
		}
		docExplorer.refreshPage();
		// EmailRecipientnames Include
		baseClass.stepInfo("EmailRecipientnames Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.exclude(domain1);
		driver.waitForPageToBeReady();
		if (baseClass.text(domain1).isDisplayed()) {
			baseClass.passedStep(
					"EmailRecipientnames Exclude result is displayed if filtering value contain special characters ");
		} else {
			baseClass.failedStep("No record found");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/11/2022 RPMXCON-54726
	 * @throws Exception
	 * @Description Verify that list view should sort by Master Date (ASC or DESC)
	 *              after applying multiple Filters (CustodianName and DocFileType)
	 *              with "Exclude" functionality.
	 */
	@Test(description = "RPMXCON-54726", enabled = true, groups = { "regression" })
	public void verifyExcludeInCustodianAndDocFiletypeAfterAscDesc() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54726");
		baseClass.stepInfo(
				"Verify that list view should sort by Master Date (ASC or DESC) after applying multiple Filters (CustodianName and DocFileType) with \"Exclude\" functionality.");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docList = new DocListPage(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagAndFol = new TagsAndFoldersPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Create a new tag contains word between");
		tagAndFol.CreateTag(random, Input.securityGroup);

		docExplorer.navigateToDocExplorerPage();
		docExplorer.selectDocumentsAndBulkTag(3, random);

		// MasterDate Filter
		docList.dateFilter("before", "2009/09/20", null);
		baseClass.passedStep("master date filter added");
		// Tag Excliude filter
		docExplorer.performExculdeTagFilter(random);
		// sort in Descending Order
		baseClass.waitTime(3);
		docExplorer.verifyingDescendingOrderInColumn();

		driver.waitForPageToBeReady();
		tagAndFol.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagAndFol.DeleteTag(random, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/11/2022 RPMXCON-54725
	 * @throws Exception
	 * @Description Verify that list view should sort by Master Date (ASC or DESC)
	 *              after applying multiple Filters (CustodianName and DocFileType)
	 *              with "Exclude" functionality
	 */
	@Test(description = "RPMXCON-54725", enabled = true, groups = { "regression" })
	public void verifyExcludeAndMasterDateAfterAscDesc() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54725");
		baseClass.stepInfo(
				"Verify that list view should sort by Master Date (ASC or DESC) after applying multiple Filters (CustodianName and DocFileType) with \"Exclude\" functionality.");
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docList = new DocListPage(driver);
		String domain1 = "Amit";
		String domain = "Document";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// CustodianName Exclude
		baseClass.stepInfo("CustodianName Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getCustodianNameFilter());
		docList.getCustodianNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain1);
		docList.getAddtoFilter().waitAndClick(5);
		docList.getApplyFilter().waitAndClick(5);

		// DocFileType Exclude
		baseClass.stepInfo("DocFileType Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getGetDocFIleTypeFilter());
		docList.getGetDocFIleTypeFilter().waitAndClick(5);
		docList.excludeDoclist(domain);
		docList.getAddtoFilter().waitAndClick(5);
		docList.getApplyFilter().waitAndClick(5);
		// sort in Descending Order
		baseClass.waitTime(3);
		docExplorer.verifyingDescendingOrderInColumn();

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:02/11/2022 RPMXCON-54693
	 * @throws Exception
	 * @Description Verify that “EmailRecipientNames” Filter with "Exclude"
	 *              functionality is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54693", enabled = true, groups = { "regression" })
	public void verifyEmailRecipientNamesWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54693");
		baseClass.stepInfo(
				"Verify that “EmailRecipientNames” Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random = "Amit.Bandal@consilio.com";
		String random1 = "Ajay.Tiwari@symphonyteleca.com";

		baseClass.stepInfo("Perform exclude filter by EmailRecipientNames");
		docExplorer.performExculdeEmailRecipientNamesFilter(random);

		baseClass.stepInfo("Verify documents after applying exclude functionality by EmailRecipientNames");
		docExplorer.verifyExcludeFunctionlityForEmailRecipientNames();

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by EmailRecipientNames");
		docExplorer.performExculdeEmailRecipientNamesFilter(random);
		docExplorer.performUpdateExculdeEmailFilter(random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by EmailRecipientNames");
		docExplorer.verifyExcludeFunctionlityForEmailRecipientNames();

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:04/11/2022 RPMXCON-54646
	 * @throws Exception
	 * @Description Verify the list of fields from the list view should be displayed
	 *              in capital
	 */
	@Test(description = "RPMXCON-54646", enabled = true, groups = { "regression" })
	public void verifyingCapitalInListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54646");
		baseClass.stepInfo("Verify the list of fields from the list view should be displayed in capital");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		docExplorer.navigateToDocExplorerPage();
		for (int i = 2; i <= 9; i++) {
			driver.waitForPageToBeReady();
			String ColValue = docExplorer.getListViewHeader(i).getText();
			System.out.println(ColValue);

			baseClass.stepInfo("The list view field is:" + ColValue);
			String CompareString = ColValue.toLowerCase();
			System.out.println(CompareString);

			baseClass.stepInfo("The list view field Compare value:" + CompareString);
			if (!ColValue.equals(CompareString)) {
				baseClass.passedStep("The list of fields from the list view is displayed in capital");
			} else {
				baseClass.failedStep("The list of fields from the list view is not displayed in capital");
			}
		}
		loginPage.logout();
	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-54653
	 * @throws Exception
	 * @Description Verify that each family should be displayed together with same
	 *              color coding of the rows in list view of doc explorer
	 */
	@Test(description = "RPMXCON-54653", enabled = true, groups = { "regression" })
	public void verifyFamilyDisplayedColorCodingRowsInListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54653");
		baseClass.stepInfo(
				"Verify that each family should be displayed together with same color coding of the rows in list view of doc explorer");
		String folderNumber = "13";
		String folderName = "Enron Data (23)";
		String folderName1 = "Amol (2)";

		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		docexp.navigateToDocExplorerPage();

		baseClass.stepInfo("verifying the count of documents of folder in tree strecture");
		docexp.verifyDOcExplorerFolderDocCount(folderNumber);

		String color = docexp.getfolderFromTreeByName(folderName).getWebElement().getCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = docexp.getfolderFromTreeByName(folderName1).getWebElement()
				.getCssValue("background-color");
		System.out.println(ExpectedColor);
		if (color.equals(ExpectedColor)) {
			baseClass.passedStep(
					"Each family is displayed together with same color coding of the rows in the list view of doc explorer as expected");
		} else {
			baseClass.failedStep("not displayed ");
		}
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/11/2022 RPMXCON-54733
	 * @throws Exception
	 * @Description Verify that “CustodianName” Column header Filter with CJK
	 *              characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54733", enabled = true, groups = { "regression" })
	public void verifyCustodianNameColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54733");
		baseClass.stepInfo(
				"Verify that “CustodianName” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] custoName = { "新", "华", "社", "记", "者", "吴", "晶" };

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.additionalDataProject);

		// verify EmailRecipient names in CJK Chars
		docexp.verifyCustodianNameValuesInDocExp(custoName);
		baseClass.passedStep(
				"Verify that “CustodianName” Column header Filter with CJK characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/11/2022 RPMXCON-54730
	 * @throws Exception
	 * @Description Verify that “EmailReceipients” Column header Filter with special
	 *              characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54730", enabled = true, groups = { "regression" })
	public void verifyEmailReceipientsColumnHeaderFilterWithSpecialChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54730");
		baseClass.stepInfo(
				"Verify that “EmailReceipients” Column header Filter with special characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] specialChars = { "`", "@", "&", ".", ":", "(", ")", "-", "'", ",", "/", "_", };

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.additionalDataProject);

		// verify EmailRecipient names in CJK Chars
		docexp.verifyEmailRecipientValuesInDocExp(specialChars);
		baseClass.passedStep(
				"Verify that “EmailReceipients” Column header Filter with special characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 15/11/2022 TestCase Id:RPMXCON-54949
	 * Description:Verify that Exclude filter functionality works properly when
	 * Folder name contains word between on Doc Explorer screen.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54949", enabled = true, groups = { "regression" })
	public void verifyFoldersExcludeFunctionalityWorkingDocExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54949");
		baseClass.stepInfo(
				"Verify that Exclude filter functionality works properly when Folder name contains word between on Doc Explorer screen.");

		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create a new folder contains word between");
		tagAndFolder.CreateFolder(random, Input.securityGroup);

		baseClass.stepInfo("Select some documets from doc explorer table and bulk folder selected documents");
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.waitForPageToBeReady();
		String Docs = docexp.getDocExp_DocID().getText();

		baseClass.stepInfo("Perform Exclude filter by folder");
		driver.waitForPageToBeReady();
		docexp.performExculdeFolderFilter(random);

		baseClass.stepInfo("Verify documents after applying Exclude functionality by folder");
		driver.waitForPageToBeReady();
		docexp.verifyIncludeFunctionlityForDocFileType(Docs);
		baseClass.passedStep(
				"Exclude filter functionality is work properly and Records is filtered according to earlier steps on Doc Explorer screen");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54735
	 * @throws Exception
	 * @Description Verify that “EmailReceipients” Column header Filter with CJK
	 *              characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54735", enabled = true, groups = { "regression" })
	public void verifyEmailReceipientsColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54735");
		baseClass.stepInfo(
				"Verify that “EmailReceipients” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] cjkChar = { "好", "人", "良い", "一个", "一個" };

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);

		// verify EmailRecipient names in CJK Chars
		docexp.verifyEmailRecipientValuesInDocExp(cjkChar);
		baseClass.passedStep(
				"Verify that “EmailReceipients” Column header Filter with CJK characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:17/10/2022 RPMXCON-54732
	 * @throws Exception
	 * @Description Verify that “EmailSubject/Filename” Column header Filter with
	 *              CJK characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54732", enabled = true, groups = { "regression" })
	public void verifyEmailSubjectFilenameColumnHeaderFilterWithCJKChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54732");
		baseClass.stepInfo(
				"Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] cjkChar = { "让", "我", "打", "电", "话", "给", "你", "延", "长", "石", "油", "集", "团" };

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		baseClass.selectproject(Input.projectName01);

		// verify EmailRecipient names in CJK Chars
		docexp.verifyEmailSubjectFilenameValuesInDocExp(cjkChar);
		baseClass.passedStep(
				"Verify that “EmailSubject/Filename” Column header Filter with CJK characters is working Successfully on Doc Explorer list");

		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 17/10/2022 TestCase Id:RPMXCON-54633 Description
	 * :Verify that user can select a single document from List view, and select
	 * action as Bulk Assign from Actions drop down to Assign document to new
	 * assignment.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54633", enabled = true, groups = { "regression" })
	public void verifySingleDocumentBulkAssignInListViewDoxExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54633");
		baseClass.stepInfo(
				"Verify that user can select a single document from List view, and select action as Bulk Assign from Actions drop down to Assign document to new assignment");
		String assignName = "assign" + Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk assign action");
		docExplorer.selectDocument(1);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");

		assignment.assignwithSamplemethod(assignName, "Count of Selected Docs", "1");
		baseClass.passedStep("User can select single doc and bulk assign to new assignment");

		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 17/10/2022 TestCase Id:RPMXCON-54634 Description
	 * :Verify that user can select multiple documents from List view, and select
	 * action as Bulk Assign from Actions drop down to Assign documents to existing
	 * assignment.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54634", enabled = true, groups = { "regression" })
	public void verifyMultipleDocumentBulkAssignInListViewDoxExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54634");
		baseClass.stepInfo(
				"Verify that user can select multiple documents from List view, and select action as Bulk Assign from Actions drop down to Assign documents to existing assignment");
		String assignName = "assign" + Utility.dynamicNameAppender();
		AssignmentsPage assignment = new AssignmentsPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Select a multiple document and select bulk assign action");
		docExplorer.selectDocument(5);
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");

		assignment.assignwithSamplemethod(assignName, "Count of Selected Docs", "5");
		baseClass.passedStep("User can select single doc and bulk assign to existing assignment");
		loginPage.logout();

	}

	/**
	 * @author N/A
	 * @Description : Verify that error message does not display and application
	 *              accepts - when 'Report Name' entered with < > * ; ‘ / ( ) # & ”
	 *              from Doc Explorer > Export > Save Report [RPMXCON-65051]
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
		baseClass.selectproject(Input.additionalDataProject);

		docExp.docExpExportDataSaveReport(custReportName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		ReportsPage report = new ReportsPage(driver);
		report.navigateToReportsPage("");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getThisLink(custReportName));

		if (report.getThisLink(custReportName).isElementAvailable(10)) {
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
	 * @author Brundha.T Id:RPMXCON-54644 Description Verify the page navigation
	 *         from the list view of doc explorer page
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
		int RowHeader = baseClass.getIndex(docExplorer.getTableHeader(), "DOCID");

		docExplorer.verifyingNavigationOption(RowHeader, docExplorer.getNavigationPageNumber("2"), "NotCompareEqual");
		docExplorer.verifyingNavigationOption(RowHeader, docExplorer.getLastPage(), "yes");
		String NextBtn = docExplorer.getNavigationBtn("Next").GetAttribute("class");
		System.out.println(NextBtn);

		baseClass.compareTextViaContains(NextBtn, "disabled", "Previous Button is disabled",
				"Previous button is not disabled");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docExplorer.verifyingNavigationOption(RowHeader, docExplorer.getFirstPage(), "Compare");
		String PreviousBtn = docExplorer.getNavigationBtn("Previous").GetAttribute("class");
		System.out.println(PreviousBtn);
		baseClass.compareTextViaContains(PreviousBtn, "disabled", "Previous Button is disabled",
				"Previous button is not disabled");

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

		// Select multiple folder and get count
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
		int treeViewCount = fold + fold1 + fold2 + fold3;
		System.out.println(treeViewCount);
		baseClass.stepInfo("Docs count in multiple folder from treeview:" + treeViewCount);

		int listViewCount = docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("Docs count in rightside list view:" + listViewCount);
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 19/10/2022 TestCase Id:RPMXCON-54601 Description
	 * :Verify the Tree View for new project when Dataset is not uploaded and
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
		//softAssert.assertAll();
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
	 * @Description Verify that “EmailSubject/Filename” Column header Filter with
	 *              special characters is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54728", enabled = true, groups = { "regression" })
	public void verifyEmailSubjectFilenameColumnHeaderFilterWithSpecialChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54728");
		baseClass.stepInfo(
				"Verify that “EmailSubject/Filename” Column header Filter with special characters is working correctly on Doc Explorer list.");

		DocExplorerPage docexp = new DocExplorerPage(driver);
		String[] specialChars = { "~", "!", "@", "#", "$", "%", "&", ";", "(", ")", "_", "-", "+", "=", "[", "]", "?",
				"/", ".", "'" };

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
	 * @Description Verify that “Comments” Filter with "Exclude" functionality is
	 *              working correctly on Doc Explorer list.
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
		CommentsPage comments = new CommentsPage(driver);
		String random = Input.reviewed + Utility.dynamicNameAppender();
		String random1 = "Completed" + Utility.dynamicNameAppender();

		comments.navigateToCommentsPage();
		comments.AddComments(random);
		comments.AddComments(random1);

		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("Perform exclude filter by Comments");
		docExplorer.performExculdeCommentsFilter(random);

		baseClass.stepInfo("Verify documents after applying exclude functionality by Comments");
		docExplorer.verifyExcludeFunctionlityForComments();

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by Comments");
		docExplorer.performExculdeCommentsFilter(random);
		docExplorer.performUpdateExculdeEmailFilter(random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by Comments");
		docExplorer.verifyExcludeFunctionlityForComments();

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Id:RPMXCON-54635 Description :Verify that user can select
	 *         all documents from page from List view, and select action as Bulk
	 *         Assign from Actions drop down to Assign documents to new assignment
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
		driver.waitForPageToBeReady();
		docExplorer.SelectingAllDocuments("No");
		driver.scrollingToBottomofAPage();
		String count = docExplorer.getDocExp_DocumentList_info().getText().toString();
		String[] Doc = count.split(" ");
		String ListViewCount = Doc[3];
		System.out.println(ListViewCount);
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();

		baseClass.stepInfo("Selecting new Assignment with sample method");
		Assign.selectNewAssignmentWithSampleMethod("Count of Selected Docs", ListViewCount, true);
		Assign.quickAssignCreation(assigname, Input.codeFormName);
		Assign.editCopyAssignmentUsingPaginationConcept(assigname);
		baseClass.waitForElement(Assign.getDistributeTab());
		Assign.getDistributeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		String AssignmentsCounts = Assign.getAssgn_docsToDistribute().GetAttribute("value");
		baseClass.digitCompareEquals(Integer.valueOf(ListViewCount), Integer.valueOf(AssignmentsCounts),
				"Document count is displayed in assignment", "DocCount is Not Displayed as expected");
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
		baseClass.waitTime(1);
		docExplorer.getfolderFromTreeByNumber("1").waitAndClick(10);
		driver.waitForPageToBeReady();
		docExplorer.SelectingAllDocuments("yes");
		int ListViewCount = docExplorer.getDocumentCountFromListView();
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();
		Assign.selectNewAssignmentWithSampleMethod("Count of Selected Docs", String.valueOf(ListViewCount), true);
		Assign.quickAssignCreation(assigname, Input.codeFormName);
		Assign.editCopyAssignmentUsingPaginationConcept(assigname);
		baseClass.waitForElement(Assign.getDistributeTab());
		Assign.getDistributeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		String AssignmentsCounts = Assign.getAssgn_docsToDistribute().GetAttribute("value");
		baseClass.digitCompareEquals(Integer.valueOf(AssignmentsCounts), ListViewCount,
				"Document count is displayed in assignment", "DocCount is Not Displayed as expected");

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

	/**
	 * @author Brundha.T TestCase Id:RPMXCON-54617 Description:Verify that user can
	 *         select a single document from List view, and select action as Bulk
	 *         Folder from Actions drop down to Folder document
	 *
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54617", enabled = true, groups = { "regression" })
	public void verifyingBulkFolderInDocExplorer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54617");
		baseClass.stepInfo(
				"Verify that user can select a single document from List view, and select action as Bulk Folder from Actions drop down to Folder document");

		String Foldername = "Fold" + Utility.dynamicNameAppender();
		int Doc = 1;
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Selecting Doc in tree view
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(baseClass.text("Doc Explorer"), "Doc Explorer Page");
		docExplorer.getfolderFromTreeByNumber("3").waitAndClick(5);

		driver.waitForPageToBeReady();
		int DocumentInList = docExplorer.getDocumentsName().size();
		if (DocumentInList >= 1) {
			baseClass.passedStep("Documents are loaded for selected folder in tree view");
		} else {
			baseClass.failedStep("Documents are not loaded for selected folder in tree view");
		}
		driver.waitForPageToBeReady();
		docExplorer.getDocumentsCheckBoxbyRowNum(Doc).waitAndClick(5);
		int TotalDocCount = docExplorer.newBulkFolder(Foldername);
		System.out.println(TotalDocCount);
		baseClass.digitCompareEquals(Doc, TotalDocCount, "Document count is displayed as expected",
				"Doc Count is not displayed");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		baseClass.waitTime(2);
		tagsAndFolderPage.verifyFolderDocCount(Foldername, Doc);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TestCase Id:RPMXCON-54606 Description:Verify that user can
	 *         select all documents from page from List view, and select action as
	 *         View in DocView from Actions drop down
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54606", enabled = true, groups = { "regression" })
	public void verifyingDocumentInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54606");
		baseClass.stepInfo(
				"Verify that user can select all documents from page from List view, and select action as View in DocView from Actions drop down");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Selecting Doc in tree view
		for (int i = 1; i <= 2; i++) {
			docExplorer.navigateToDocExplorerPage();
			driver.waitForPageToBeReady();
			baseClass.ValidateElement_Presence(baseClass.text("Doc Explorer"), "Doc Explorer Page");
			if (i == 1) {
				baseClass.stepInfo("Selecting a folder in Tree view");
				docExplorer.getfolderFromTreeByNumber("1").waitAndClick(5);
			} else {
				baseClass.stepInfo("Selecting Multiple folder in Tree view");
				docExplorer.selectMultipleFoldersOfTree(4);
			}
			int CountOfDoc = docExplorer.DocviewDocCountByIndex(3);
			if (Integer.valueOf(CountOfDoc) != 0) {
				baseClass.passedStep("Documents are loaded for selected folder in treeview");
			} else {
				baseClass.failedStep("Documents are not loaded");
			}
			docExplorer.SelectingAllDocuments("No");
			docExplorer.docExpViewInDocView();
			DocViewPage Docview = new DocViewPage(driver);
			baseClass.waitTime(2);
			int DocCountInDocView = Docview.verifyingDocCount();

			baseClass.stepInfo("Validating Document count ");
			baseClass.digitCompareEquals(Integer.valueOf(CountOfDoc), DocCountInDocView,
					"Selected documents from list view is displayed on doc view", "Documents are not loaded");

		}
		loginPage.logout();
	}

	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-54994
	 * @throws Exception
	 * @Description Verify that when a user configures MasterDate and EmailSubject
	 *              filters and selects check-boxes manually and navigates
	 *              Doc-Explorer to DocView then documents gets loaded on DocView
	 *              screen.
	 */
	@Test(description = "RPMXCON-54994", enabled = true, groups = { "regression" })
	public void verifyMasterDateEmailDocsGetsLoadedOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54994");
		baseClass.stepInfo(
				"Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocView then documents gets loaded on DocView screen.");

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
		driver.waitForPageToBeReady();
		int allDoc = docexp.getDocumentsName().size();
		driver.waitForPageToBeReady();
		docexp.selectDocument(allDoc);
		baseClass.stepInfo("Select all checkboxes manually as expected");
		driver.waitForPageToBeReady();
		docexp.docExpViewInDocView();

		// verify displayed without runtime error
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docview.getMiniDocListTable());
		baseClass.verifyUrlLanding(Input.url + "en-us/DocumentViewer/DocView", " on doc View page",
				"Not on doc view page");
		if (docview.getMiniDocListTable().isElementPresent()) {
			baseClass.passedStep("Docview page is displayed without any runtime error");

		} else {
			baseClass.failedStep("displayed with error");
		}
	}

	/**
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54624 Description
	 * :Verify that user can select all documents from all pages from List view, and
	 * select action as Bulk Assign from Actions drop down to Assign documents to
	 * existing assignment.
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
		String[] Doc = count.split(" ");
		String ListViewCount = Doc[3];
		System.out.println(ListViewCount);
		driver.scrollPageToTop();
		docExplorer.docExp_BulkAssign();
		baseClass.stepInfo("select existing assignment with sampling method and finalize");

		assignment.assignwithSamplemethod(assignName, "Count of Selected Docs", ListViewCount);
		baseClass.passedStep("User can select All Document and bulk assign to existing assignment");
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 25/10/2022 TestCase Id:RPMXCON-54637
	 * Description:Verify that user should be able to remove documents from Folder
	 * from Doc Explorer.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54637", enabled = true, groups = { "regression" })
	public void verifyRevomeDocumentsFromFolderDocExplorerPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54637");
		baseClass.stepInfo("Verify that user should be able to remove documents from Folder from Doc Explorer.");
		TagsAndFoldersPage tagAndFolder = new TagsAndFoldersPage(driver);
		String folderName = "folder" + Utility.dynamicNameAppender();
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
		SavedSearch saveSearch = new SavedSearch(driver);

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
		baseClass.checkNotificationCount(initialBg, 1);
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
	 * Author : date: 06/10/2022 TestCase Id:RPMXCON-54681 Description :Verify that
	 * “Folders” Filter with "Include" functionality is working correctly on Doc
	 * Explorer list.
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
	 * Author : date: 06/10/2022 TestCase Id:RPMXCON-54956 Description :Verify that
	 * Include filter functionality works properly when TAG name contains word
	 * between on Concept Explorer Report screen
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

	/**
	 * Author :Arunkumar date: 26/09/2022 TestCase Id:RPMXCON-54595 Description
	 * :Verify when user clicks on folder name from the tree view
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54595", enabled = true, groups = { "regression" })
	public void verifyCountInFolderAndTreeView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54595");
		baseClass.stepInfo("Verify when user clicks on folder name from the tree view");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", " on doc explorer page",
				"Not on doc explorer page");
		int treeViewCount = docExplorer.getFolderCountFromTreeView("1");
		baseClass.stepInfo("Docs count in folder tree view:" + treeViewCount);
		int listViewCount = docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("Docs count in rightside list view:" + listViewCount);
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 26/09/2022 TestCase Id:RPMXCON-54612 Description
	 * :Verify that Action drop down should be disable when no document is selected
	 * from List View
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54612", enabled = true, groups = { "regression" })
	public void verifyActionDropdownInListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54612");
		baseClass.stepInfo("Verify action dropdown status in list view when no docs selected");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", " by default on doc explorer page",
				"Not on doc explorer page");
		baseClass.stepInfo("verify docs count in tree view and list view");
		int treeViewCount = docExplorer.getFolderCountFromTreeView("1");
		baseClass.stepInfo("count in folder tree view:" + treeViewCount);
		int listViewCount = docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("document count in rightside list view:" + listViewCount);
		docExplorer.verifyDocsCountInFolderAndListView(treeViewCount, listViewCount);
		baseClass.stepInfo("verify action dropdown status");
		String status = docExplorer.actionDropdown().GetAttribute("disabled");
		System.out.println(status);
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("Action dropdown in list view disabled when no docs selected");
		} else {
			baseClass.failedStep("Action dropdown enabled when no docs selected");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 26/09/2022 TestCase Id:RPMXCON-54621 Description
	 * :Verify that user can select a single document from List view, and select
	 * action as Bulk Assign from Actions drop down to Assign document to existing
	 * assignment
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54621", enabled = true, groups = { "regression" })
	public void verifySingleDocumentBulkAssignInListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54621");
		baseClass.stepInfo("Verify bulk assign to existing assignment");
		String assignName = "assign" + Utility.dynamicNameAppender();

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
		assignment.assignwithSamplemethod(assignName, "Count of Selected Docs", "1");
		baseClass.passedStep("User can select single doc and bulk assign to existing assignment");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 27/09/2022 TestCase Id:RPMXCON-55000 Description
	 * :Verify that when a user configures MasterDate and DocFileType filters and
	 * selects check-boxes manually and navigates Doc-Explorer to DocList then
	 * documents gets loaded on DocList screen.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-55000", enabled = true, groups = { "regression" })
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
		int docsCount = docExplorer.getDocumentCountFromListView();
		docExplorer.selectDocument(docsCount);
		baseClass.stepInfo("select action as view in doclist and verify");
		docExplorer.navigateToDoclistFromDocExplorer();
		baseClass.passedStep("Able to navigate from docexplorer to doclist without any errors");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 27/09/2022 TestCase Id:RPMXCON-54999 Description
	 * :Verify that when a user configures MasterDate filter and selects check-boxes
	 * manually and navigates Doc-Explorer to DocList then documents gets loaded on
	 * DocList screen.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54999", enabled = true, groups = { "regression" })
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
		int docsCount = docExplorer.getDocumentCountFromListView();
		docExplorer.selectDocument(docsCount);
		baseClass.stepInfo("select action as view in doclist and verify");
		docExplorer.navigateToDoclistFromDocExplorer();
		baseClass.passedStep("User able to navigate from docexplorer to doclist without any errors");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 27/09/2022 TestCase Id:RPMXCON-54656 Description
	 * :Verify the family field values from list view and should be displayed
	 * correctly
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54656", enabled = true, groups = { "regression" })
	public void verifyFamilyFieldValuesFromListView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54656");
		baseClass.stepInfo("Verify the family field values from list view and should be displayed correctly");
		String[] familyField = { "S", "P", "C", "PC" };
		String[] values = { "Standalone", "Parent", "Child", "Parent and Child" };

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

		// verify home page display
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep("Application Home Page is displayed successfully");
		} else {
			baseClass.failedStep("Application Home Page is not displayed ");
		}
		// Click Left menu docexplorer icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docExplorer.getDocExplorerTab());
		docExplorer.getDocExplorerTab().waitAndClick(5);
		if (docExplorer.getPresentDocCount().Displayed()) {
			baseClass.passedStep("Doc Explorer Page is appeared Successfully");
		} else {
			baseClass.failedStep("Doc Explorer Page is not appeared.");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-54627
	 * @throws Exception
	 * @Description Verify that user can select all documents from current page from
	 *              List view, and select action as Bulk Release from Actions drop
	 *              down to Release documents.
	 */
	@Test(description = "RPMXCON-54627", enabled = true, groups = { "regression" })
	public void verifySlectAllDocsInCurrentpageBulkRelease() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54627");
		baseClass.stepInfo(
				"Verify that user can select all documents from current page from List view, and select action as Bulk Release from Actions drop down to Release documents.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		SoftAssert softAssert = new SoftAssert();
		DocListPage docList = new DocListPage(driver);

		// verify DocExplorer home Page
		softAssert.assertTrue(docExplorer.getPresentDocCount().isDisplayed());
		baseClass.passedStep("User is logged in successfully and it is \"Doc Explorer\" page ");

		// Select folder In Treeview
		driver.waitForPageToBeReady();
		docExplorer.getAllDocumentsCount().waitAndClick(5);
		baseClass.stepInfo(
				" Successfully clicked a folder, it show the docs corresponding to that folder in the list view on the right hand side.");

		// Select All Documents verify popup
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		String Actualmsg = docList.getSelectDocsOnAllPages().getText();
		String expectmsg = "Do you want to make selection across all the pages?";
		softAssert.assertEquals(Actualmsg, expectmsg);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		// Click OK btn
		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		// BulkRelease
		docExplorer.docExplorerRelease(Input.securityGroup);
		baseClass.passedStep(
				"Success message is displayed as Success! Records saved successfully and selected documents from current page is released to the selected security group(s)");
		softAssert.assertAll();
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-54632
	 * @throws Exception
	 * @Description Verify that user can select all documents from all pages from
	 *              List view, and select action as Export Data from Actions drop
	 *              down to export.
	 */
	@Test(description = "RPMXCON-54632", enabled = true, groups = { "regression" })
	public void verifySlectAllDocsInCurrentpageExportData() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54632");
		baseClass.stepInfo(
				"Verify that user can select all documents from all pages from List view, and select action as Export Data from Actions drop down to export.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		SoftAssert softAssert = new SoftAssert();
		DataSets data = new DataSets(driver);
		DocListPage docList = new DocListPage(driver);

		// verify DocExplorer home Page
		softAssert.assertTrue(docExplorer.getPresentDocCount().isDisplayed());
		baseClass.passedStep("User is logged in successfully and it is \"Doc Explorer\" page ");

		// Select folder In Treeview
		driver.waitForPageToBeReady();
		docExplorer.getAllDocumentsCount().waitAndClick(5);
		baseClass.stepInfo(
				" Successfully clicked a folder, it show the docs corresponding to that folder in the list view on the right hand side.");

		// Select All Documents verify popup
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		String Actualmsg = docList.getSelectDocsOnAllPages().getText();
		String expectmsg = "Do you want to make selection across all the pages?";
		softAssert.assertEquals(Actualmsg, expectmsg);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		// Click OK btn
		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		// Export data
		docExplorer.docExplorerexportData();
		driver.waitForPageToBeReady();
		data.getBullHornIcon().waitAndClick(5);
		String downloadMsg = data.getNotificationMsg().getText();
		String expected = "Your export is ready please click here to download";
		softAssert.assertEquals(downloadMsg, expected);
		baseClass.passedStep(
				"Notification is displayed and on click of the same download the file  Make sure that in downloaded file is selected all documents from all pages");
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
		// Select Default All documents form Treeview
		int treeViewCount = docExplorer.getFolderCountFromTreeView("1");
		baseClass.stepInfo("Docs count in folder tree view:" + treeViewCount);

		// ListView count
		int listViewCount = docExplorer.getDocumentCountFromListView();
		baseClass.stepInfo("Docs count in rightside list view:" + listViewCount);

		// verify all documents display
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
