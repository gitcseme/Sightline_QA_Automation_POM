package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression26 {
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
		String[] specialChars = { "@", ".", ";", "(", ")", "-", "'", ",", "!", "#", "&" };

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

		tagAndFol.navigateToTagsAndFolderPage();
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
		System.out.println("**Executed  DocExplorer_Regression26.**");
	}

}
