package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import bsh.util.Util;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression3 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
	SoftAssert softAssertion;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String productionname;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}

	/**
	 * @author Jeevitha
	 * @Description : [Batch Redactions- TC 12386]Verify that PDF should Export with
	 *              Burned Redaction if Only Burn Redaction is enabled for batch
	 *              redacted documents (RPMXCON-53464)
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
	// @Test(enabled = true, dataProvider = "Users", groups = { "regression" },
	// priority = 1)
	public void createExport(String username, String password) throws InterruptedException {
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String exportname = "E" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String newExport = "Ex" + Utility.dynamicNameAppender();

		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-53464 Batch Redaction Sprint 07");
		base.stepInfo(
				"[Batch Redactions- TC 12386]Verify that PDF should Export with Burned Redaction if Only Burn Redaction is enabled for batch redacted documents");

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		base.selectproject();
		session = new SessionSearch(driver);
		session.basicContentSearch(testData1);
		session.bulkFolderExisting(foldername);

		// create export with PDF
		ProductionPage page = new ProductionPage(driver);
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		// Delete created folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		login.logout();
	}

	@DataProvider(name = "testData")
	public Object[][] testData() {
		return new Object[][] { { "ProximitySe*" }, { "Proximity*" }, };
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that Batch Redaction should be successful when selected
	 *              Saved search is with wildcard (*) search from Batch Redaction
	 *              Home page(RPMXCON-53350)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "testData", groups = { "regression" }, priority = 2)
	public void verifyBRWithSavedSearchAsWildcard(String data) throws InterruptedException {
		String searchName = "SearchName*" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53350   Bacth Redaction  Sprint-7");
		base.stepInfo(
				"Verify that Batch Redaction should be successful when selected Saved search is with wildcard (*) search from Batch Redaction Home page");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearchInNewNode(searchName, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that Proximity Queries should return all text that
	 *              required a redaction during Batch Redaction
	 *              Analysis(RPMXCON-53432)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyThatProximityQueries() throws InterruptedException {
		String searchName = "SearchNames" + Utility.dynamicNameAppender();
		String data = "\"government money\"~7";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53432   Bacth Redaction  Sprint-7");
		base.stepInfo(
				"Verify that Proximity Queries should return all text that required a redaction during Batch Redaction Analysis");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearchInNewNode(searchName, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that for each batch redactions components icon for
	 *              universal delete should be displayed from doc view
	 *              redactions(RPMXCON-53403)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyEachBRForDeleteIcon() throws InterruptedException {
		String searchName = "SearchNames" + Utility.dynamicNameAppender();
		String data = Input.testData1;
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions DCRedactions = new DocViewRedactions(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53403   Bacth Redaction  Sprint-7");
		base.stepInfo(
				"Verify that for each batch redactions components icon for universal delete should be displayed from doc view redactions");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearchInNewNode(searchName, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Traverse from Saved search To Doc view
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.getDocView_button().waitAndClick(20);
		driver.waitForPageToBeReady();
		docview.verifyRedactionPanel();
		DCRedactions.verifyRedactionsSubMenu();

		base.waitForElement(DCRedactions.getTrashIcon(data));
		if (DCRedactions.getTrashIcon(data).isElementPresent()) {
			System.out.println("TrashIcon Is displayed for : " + data);
			base.passedStep("TrashIcon Is displayed for : " + data);
		}

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify when batch redaction executed when exact dupes in
	 *              different security groups, without shared annotation layer and
	 *              NO shared redaction tags, then on doc view no redaction info
	 *              (coordinates, tags and history) shown in the dupe(RPMXCON-53427)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyInDiffSGWithDiffRedactionTAg() throws Exception {
		String securityGroup = "SG0" + Utility.dynamicNameAppender();
		String layer = "Layer00" + Utility.dynamicNameAppender();
		String search = "Search01" + Utility.dynamicNameAppender();
		String tagName = "Search01" + Utility.dynamicNameAppender();

		DocViewPage docview = new DocViewPage(driver);
		RedactionPage redact = new RedactionPage(driver);
		UserManagement userManagement = new UserManagement(driver);
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Login as a PA
		base.stepInfo("Test case Id:RPMXCON-53427  Batch Redaction");
		base.stepInfo(
				"Verify when batch redaction executed when exact dupes in different security groups, without shared annotation layer and NO shared redaction tags, then on doc view no redaction info (coordinates, tags and history) shown in the dupe");

		// Craete security group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		security.AddSecurityGroup(securityGroup);
		driver.Navigate().refresh();
		security.selectSecurityGroup(securityGroup);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.AddAnnotation(layer);
		redact.AddRedaction(tagName, "RMU");

		// Assign Diff redaction tag and Annotation layer to SG
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		security.selectSecurityGroup(securityGroup);
		security.assignAnnotationToSG(layer);
		security.assignRedactionTagtoSG(tagName);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu2userName);
		if (userManagement.getSavePassword().isElementAvailable(3)) {
			userManagement.getSavePassword().waitAndClick(3);
			driver.waitForPageToBeReady();
		}

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.testData1, "Yes", "Third");
		session.bulkReleaseNearDupeAndDoc(securityGroup);

		login.logout();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.testData1, "Yes", "Third");
		session.saveSearchInNewNode(search, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(search, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		login.logout();

		// Login as a RMU

		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.selectsecuritygroup(securityGroup);

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("And");
		session.basicContentSearchWithSaveChanges(Input.testData1, "Yes", "Third");

		// Purehit to docview
		session.ViewInDocView();
		docview.verifyRedactionPanel();

		// exact dupe to doc view
		driver.getWebDriver().get(Input.url + "Search/Searches"); //
		session.Removedocsfromresults();
		session.ViewNearDupeDocumentsInDocView();
		docview.verifyRedactionPanel();

		base.selectsecuritygroup(Input.securityGroup);

		login.logout();
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Delete Sg
		redact.DeleteRedaction(tagName);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify when batch redaction executed with same doc in different
	 *              security groups, without shared annotation layer, with shared
	 *              redaction tags, no redaction info (coordinates, tags and
	 *              history) shown in the doc in 2nd sec group.(RPMXCON-53426)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyInDiffSGWithSameRedactionTAg() throws Exception {
		String securityGroup = "SG0" + Utility.dynamicNameAppender();
		String layer = "Layer00" + Utility.dynamicNameAppender();
		String search = "Search01" + Utility.dynamicNameAppender();
		String tagName = "Search01" + Utility.dynamicNameAppender();

		DocViewPage docview = new DocViewPage(driver);
		RedactionPage redact = new RedactionPage(driver);
		UserManagement userManagement = new UserManagement(driver);
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Login as a PA
		base.stepInfo("Test case Id:RPMXCON-53426  Batch Redaction");
		base.stepInfo(
				"Verify when batch redaction executed with same doc in different security groups, without shared annotation layer, with shared redaction tags, no redaction info (coordinates, tags and history) shown in the doc in 2nd sec group.");

		// Craete security group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		security.AddSecurityGroup(securityGroup);
		driver.Navigate().refresh();
		security.selectSecurityGroup(securityGroup);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.AddAnnotation(layer);

		// Assign Diff redaction tag and Annotation layer to SG
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		security.selectSecurityGroup(securityGroup);
		security.assignAnnotationToSG(layer);
		security.assignRedactionTagtoSG(Input.defaultRedactionTag);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu2userName);
		if (userManagement.getSavePassword().isElementAvailable(3)) {
			userManagement.getSavePassword().waitAndClick(3);
			driver.waitForPageToBeReady();
		}

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGroup);
		login.logout();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(search, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(search, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		login.logout();

		// Login as a RMU

		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.selectsecuritygroup(securityGroup);

		// Create saved search
		session.basicContentSearch(Input.testData1);

		// Purehit to docview
		session.ViewInDocView();
		docview.verifyRedactionPanel();

		base.selectsecuritygroup(Input.securityGroup);
		login.logout();

		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// delete Security Group
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @author sowndariya.velraj
	 * @Description :[Batch Redactions- TC 11680]To verify that PDF should burn
	 *              multiple batch redactions and display the correct text for each
	 *              redaction
	 * 
	 * @Description :[Batch Redactions- TC 6827]To verify that if annotation layer
	 *              option is selected in PDF section and document is batch redacted
	 *              then selected Metadata should not be displayed on DAT
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
	// @Test(enabled = true, dataProvider = "Users", groups = { "regression" },
	// priority = 7)
	public void generateProductionWithMultipleRedactionTags(String username, String password)
			throws InterruptedException {
		String tagname = "FirstTag" + Utility.dynamicNameAppender();
		String tagname2 = "SecondTag" + Utility.dynamicNameAppender();

		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-53463 Batch Redaction Sprint 07");
		base.stepInfo(
				"[Batch Redactions- TC 11680]To verify that PDF should burn multiple batch redactions and display the correct text for each redaction");
		base.stepInfo("Test case Id: RPMXCON-53461 Batch Redaction Sprint 07");
		base.stepInfo(
				"[Batch Redactions- TC 6827]To verify that if annotation layer option is selected in PDF section and document is batch redacted then selected Metadata should not be displayed on DAT");

		// create tag and folder
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		driver.Navigate().refresh();
		tagsAndFolderPage.createNewTagwithClassification(tagname2, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname2);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithBurnRedaction(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageWithTag(tagname, tagname2);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"verified that PDF should burn multiple batch redactions and  display the correct text for each redaction");
		base.passedStep(
				"verify that if annotation layer option is selected in PDF section and document is batch redacted then selected Metadata should not be displayed on DAT");

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify the My Background Tasks page once user started 'Batch
	 *              Redactions'(RPMXCON-53342)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyBackgroundTasksPage() throws Exception {
		String search = "Search01" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53342  Batch Redaction  Sprint-7");
		base.stepInfo("Verify the My Background Tasks page once user started 'Batch Redactions'");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(search, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(search, true);

		final int Bgcount = base.initialBgCount();
		System.out.println(Bgcount);

		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		// Navigate to BAckGroundTAsk PAge
		base.waitForElement(batch.getDocCountFromTable(search));
		String docCount = batch.getDocCountFromTable(search).getText();
		batch.verifyBgTaskheaderAndCount(search, docCount, Bgcount);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify the My Background Tasks page once user started 'Batch
	 *              Unredactions' / 'Rollback' (RPMXCON-53343)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyBackgroundTasksPageForRollback() throws Exception {
		String search = "Search01" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53343  Batch Redaction  Sprint-7");
		base.stepInfo(
				"Verify the My Background Tasks page once user started 'Batch Unredactions' / 'Rollback'\r\n" + "");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(search, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(search, true);

		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		final int Bgcount = base.initialBgCount();
		System.out.println(Bgcount);
		base.waitForElement(batch.getDocCountFromTable(search));
		String docCount = batch.getDocCountFromTable(search).getText();

		// perform RollBack
		batch.verifyRollback(search, "Yes");

		// Navigate to BAckGroundTAsk PAge
		batch.verifyBgTaskheaderAndCount(search, docCount, Bgcount);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : [TODO from recording]Verify the document from doc view when on
	 *              demand search terms and batch redaction terms are
	 *              same[RPMXCON-53422]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyDemandSearchTerm() throws Exception {
		String searchName = "SearchNames" + Utility.dynamicNameAppender();
		String data = Input.testData1;
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions DCRedactions = new DocViewRedactions(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53422   Bacth Redaction  Sprint-7");
		base.stepInfo(
				"[TODO from recording]Verify the document from doc view when on demand search terms and batch redaction terms are same");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearchInNewNode(searchName, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Traverse from Saved search To Doc view
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.getDocView_button().waitAndClick(20);
		driver.waitForPageToBeReady();
		docview.verifyRedactionPanel();

		DCRedactions.performClickSearchIconAndX(data, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
	}

	/**
	 * @author Aathiht.Senthilkumar RPMXCON-53462
	 * @Description : [Batch Redactions- TC 11372]Verify that production should
	 *              generated with modified Redaction placeholder text for batch
	 *              redacted documents.
	 * @throws InterruptedException
	 */
//	@Test(groups = { "regression" }, priority = 11)
	public void verifyModifiedPlaceholdertext() throws InterruptedException {
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-53462 Batch Redaction Sprint 07");
		base.stepInfo(
				"[Batch Redactions- TC 11372]Verify that production should generated with modified Redaction placeholder text for batch redacted documents");

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		base.selectproject();
		session = new SessionSearch(driver);
		session.basicContentSearch(testData1);
		session.bulkFolderExisting(foldername);

		// create export with PDF
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that production should generated with modified Redaction placeholder text for batch redacted documents");

		// Delete created folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that batch redaction when saved search with pattern
	 *              \"simple\" email - 99% coverage[RPMXCON-53375]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyBrWithPatternEmail() throws Exception {
		String searchName = "SearchNames" + Utility.dynamicNameAppender();
		String data = "\"##[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}\"";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53375   Bacth Redaction  Sprint-7");
		base.stepInfo("Verify that batch redaction when saved search with pattern \"simple\" email - 99% coverage");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify on click of 'Cancel and Rollback' when batch redaction is
	 *                     'In progress' should cancel the batch
	 *                     redaction(RPMXCON-53417)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyCancelAndRollback() throws InterruptedException, IOException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53417  Bacth Redaction ");
		base.stepInfo(
				"Verify on click of 'Cancel and Rollback' when batch redaction is 'In progress' should cancel the batch redaction");

		// Search The Query
		int purehit = session.basicContentSearch(Input.searchString6);
		session.saveSearch(searchName);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// clicked no btn in Cancel redaction popup
		batch.verifyCancelAndRollBack(searchName, false);

		// clicked Yes btn in Cancel redaction popup
		batch.verifyCancelAndRollBack(searchName, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify that \"Analyze Search for Redaction\" and \"View & Redact
	 *                     Search\" buttons are NOT Visible on Batch Redaction
	 *                     Screen.(RPMXCON-53503)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyBR() throws Exception {
		String searchName = "79ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWX"
				+ Utility.dynamicNameAppender();
		String data = Input.testData1;

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53503   Bacth Redaction  Sprint-8");
		base.stepInfo(
				"Verify that \"Analyze Search for Redaction\" and \"View & Redact Search\" buttons are NOT Visible on Batch Redaction Screen.");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(data);

		// will save the query on created node under my saved search
		session.saveSearchInNodewithChildNode(searchName, newNode);

		// perform Batch redaction
		batch.verifyNodeAnalyseAndViewBtn(newNode, searchName, null, null);

		// Delete Created Node
		saveSearch.navigateToSavedSearchPage();
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verfiy that In the Batch Redactions Search tree, the timestamp
	 *              corresponding to Batch Uploaded searches presents Last Completed
	 *              Time (Saved Search) [RPMXCON-53508]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyBRSearchTreeTimestamp() throws Exception {
		String searchName = "search" + Utility.dynamicNameAppender();
		String data = Input.testData1;

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Test Case Description
		base.stepInfo("Testcase ID : RPMXCON-53508   Bacth Redaction  Sprint-8");
		base.stepInfo(
				"Verfiy that In the Batch Redactions Search tree, the timestamp corresponding to Batch Uploaded searches presents Last Completed Time (Saved Search)");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");

		// will load the session search page and search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(searchName, newNode);

		// get search time from Savedsaerch page
		String completedTime = saveSearch.verifyCompletedTime(searchName, newNode, Input.mySavedSearch, true);

		batch.loadBatchRedactionPage(searchName);

		// get search time from BatchRedaction page
		String timeStampOfSearchInBR = batch.verifyCompletedTimeOfSearchInBR(searchName, newNode);
		softAssertion.assertEquals(completedTime, timeStampOfSearchInBR);
		softAssertion.assertAll();

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify 'Rollback' for successfully completed batch redaction
	 *              when few more documents are released matching with the same
	 *              saved search criteria[RPMXCON-53471]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyRollbackSuccessfullyCompletedBR() throws Exception {
		String search = "Search" + UtilityLog.dynamicNameAppender();
		String othSG = "Security Group_" + UtilityLog.dynamicNameAppender();
		String defSG = Input.securityGroup;

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement userManagemet = new UserManagement(driver);

		base.stepInfo("Test case Id: RPMXCON-53471   batch redcation Sprint-8");
		base.stepInfo(
				"Verify 'Rollback' for successfully completed batch redaction when few more documents are released matching with the same saved search criteria");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(othSG);
		base.stepInfo(othSG + " : created");
		sg.assignRedactionTagtoSG(Input.defaultRedactionTag);

		// Assign access to SG
		userManagemet.assignAccessToSecurityGroups(othSG, Input.rmu1userName);
		if (userManagemet.getSavePassword().isElementAvailable(3)) {
			userManagemet.getSavePassword().waitAndClick(3);
			driver.waitForPageToBeReady();
		}
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(othSG);

		login.logout();
		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectsecuritygroup(othSG);

		// Search The Query
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		batch.VerifyBatchRedaction_ElementsDisplay(search, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		// perform RollBack first time for the same search
		batch.verifyRollback(search, "Yes");

		batch.VerifyBatchRedaction_ElementsDisplay(search, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		// perform RollBack Second time for the same search
		batch.verifyRollback(search, "Yes");

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		base.selectsecuritygroup(defSG);
		login.logout();

		// Deleting the SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		sg.deleteSecurityGroups(othSG);
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that redacted documents should be searched successfully
	 *              with the redaction label after execution of batch
	 *              redaction[RPMXCON-53431]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyRedactedDocuments() throws Exception {
		String search = "Search" + UtilityLog.dynamicNameAppender();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id: RPMXCON-53431   batch redcation Sprint-8");
		base.stepInfo(
				"Verify that redacted documents should be searched successfully with the redaction label after execution of batch redaction");

		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		batch.VerifyBatchRedaction_ElementsDisplay(search, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(search);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		int purehit = session.saveAndReturnPureHitCount();
		base.stepInfo(Input.defaultRedactionTag + " is selected in Work Product and purihit count is: " + purehit);

		// Delete Search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author sowndariya.velraj
	 * @Description :Verify that redacted text should not be shifted in produced
	 *              document when batch redaction is executed for the documents with
	 *              bullet points
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
//	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void generateProductionWithMultipleRedactionTags() throws InterruptedException {

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-53475 Batch Redaction Sprint 08");
		base.stepInfo(
				"Verify that redacted text should not be shifted in produced document when batch redaction is executed for the documents with bullet points");

		base.stepInfo("Search and save the test data");
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch("CH 43");

		batch.VerifyBatchRedaction_ElementsDisplay("CH 43", true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus("CH 43");

		// create production with DAT,Native,TIFF with Redaction andSelecting documents
		// from saved search
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage("CH 43", null);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"Redacted text should not  shifted in produced document when batch redaction is executed for the documents with bullet points");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that 'View Report and Apply Redactions' button from
	 *              Batch Redactions page is displayed for Saved search only after
	 *              Analyzing the saved search for redaction[RPMXCON-48804]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyViewReportBtn() throws Exception {
		String search = "Search" + Utility.dynamicNameAppender();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id: RPMXCON-48804   batch redcation Sprint-8");
		base.stepInfo(
				"Verify that 'View Report and Apply Redactions' button from Batch Redactions page is displayed for Saved search only after Analyzing the saved search for redaction");

		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(search);

		batch.loadBatchRedactionPage(search);
		batch.performAnalysisGroupForRedcation(search, null, purehit, false);

		batch.verifyViewReportBtn(search, null);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that when navigated to the last redaction should be
	 *              able to navigate to first redaction on click of '>' from doc
	 *              view redactions [Cycle from First to Last while toggle between
	 *              redacted terms ] [RPMXCON-53401]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyingRedactionInDocView() throws Exception {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);

		base.stepInfo("Test case Id:RPMXCON-53401");
		base.stepInfo(
				"Verify that when navigated to the last redaction should be able to navigate to first redaction on click of '>' from doc view redactions [Cycle from First to Last while toggle between redacted terms ]");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// creating Batch Redaction
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getConfirmationBtn("Yes").waitAndClick(5);
		batch.verifyHistoryStatus(searchName);

		// selecting the Configured Saved search

		saveSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();

		base.waitForElement(docview.getDocView_RedactIcon());
		base.waitTillElemetToBeClickable(docview.getDocView_RedactIcon());
		docview.getDocView_RedactIcon().waitAndClick(10);

		// getting the All Redaction total Count
		Element allRedacCount = docview.getDocView_AllRedactionCount();
		int redactionCount = docview.getRedactionCount(allRedacCount, "All Redaction");

		// getting the Batch Redaction total count
		Element batchRedactCount = docview.getDocView_BatchRedactionCount();
		int batchRedactionCount = docview.getRedactionCount(batchRedactCount, "Batch Redaction");

		// getting the Component Batch Redaction total count
		Element componentCount = docview.getComponentBatchRedactionsRatioCount();
		int ComponentBatchCount = docview.getRedactionCount(componentCount, "Component Batch Redaction");

		// navigating through All Redaction
		Element allredaction = docview.getAllredactionForwardNavigate();
		Element alRedactCount = docview.getDocView_AllRedactionCount();
		docview.navigateToRedaction(redactionCount, allredaction, alRedactCount, true, false);

		// navigating through Batch Redaction
		Element batchRedact = docview.BatchredactionForwardNavigate();
		Element batchRedactCOunt = docview.getDocView_BatchRedactionCount();
		docview.navigateToRedaction(batchRedactionCount, batchRedact, batchRedactCOunt, true, false);

		// navigating through Component Batch Redaction
		Element componentBR = docview.componentBatchredactionForwardNavigate();
		Element componentBRCount = docview.getComponentBatchRedactionsRatioCount();
		docview.navigateToRedaction(ComponentBatchCount, componentBR, componentBRCount, true, false);

		// Delete Searche
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @Author Raghuram
	 * @Description : Verify that for All redactions navigation option to redactions
	 *              should be displayed [<, >] on doc view redactions panel
	 *              [RPMXCON-53398]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyingAllRedactionsNavigationOptionInDocView() throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);

		base.stepInfo("Test case Id:RPMXCON-53398");
		base.stepInfo(
				"Verify that for All redactions navigation option to redactions should be displayed [<, >] on doc view redactions panel");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// creating Batch Redaction
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getConfirmationBtn("Yes").waitAndClick(5);
		batch.verifyHistoryStatus(searchName);

		// selecting the Configured Saved search
		saveSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();

		base.waitForElement(docview.getDocView_RedactIcon());
		base.waitTillElemetToBeClickable(docview.getDocView_RedactIcon());
		docview.getDocView_RedactIcon().waitAndClick(10);

		// getting the All Redaction total Count
		Element allRedacCount = docview.getDocView_AllRedactionCount();
		int redactionCount = docview.getRedactionCount(allRedacCount, "All Redaction");

		// navigating through All Redaction
		Element allredaction = docview.getAllredactionForwardNavigate();
		Element alRedactCount = docview.getDocView_AllRedactionCount();
		docview.navigateToRedaction(redactionCount, allredaction, alRedactCount, true, true);

		// Delete Searche
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that for Batch redactions navigation option to redacted
	 *              terms should be displayed [<, >] on doc view redactions panel
	 *              [RPMXCON-53399]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyingBatchRedactionsNavigationOptionInDocView() throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions dcRedact = new DocViewRedactions(driver);

		base.stepInfo("Test case Id:RPMXCON-53399");
		base.stepInfo(
				"Verify that for Batch redactions navigation option to redacted terms should be displayed [<, >] on doc view redactions panel");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// creating Batch Redaction
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getConfirmationBtn("Yes").waitAndClick(5);
		batch.verifyHistoryStatus(searchName);

		// selecting the Configured Saved search
		saveSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();

		base.waitForElement(docview.getDocView_RedactIcon());
		base.waitTillElemetToBeClickable(docview.getDocView_RedactIcon());
		docview.getDocView_RedactIcon().waitAndClick(10);
		dcRedact.verifyRedactionsSubMenu();

		// getting the Batch Redaction total count
		Element batchRedactCount = docview.getDocView_BatchRedactionCount();
		int batchRedactionCount = docview.getRedactionCount(batchRedactCount, "Batch Redaction");

		// navigating through Batch Redaction
		Element batchRedact = docview.BatchredactionForwardNavigate();
		Element batchRedactCOunt = docview.getDocView_BatchRedactionCount();
		docview.navigateToRedaction(batchRedactionCount, batchRedact, batchRedactCOunt, true, false);
		docview.navigatePreviousRedact(1, true, false, batchRedactCOunt, "");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Raghuram
	 * @Description :Verify that for each Batch redactions components navigation
	 *              option to redacted terms should be displayed [<,>] on doc view
	 *              redatcions panel [RPMXCON-53400]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void verifyingComponentBatchRedactionsNavigationOptionInDocView() throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions dcRedact = new DocViewRedactions(driver);

		base.stepInfo("Test case Id:RPMXCON-53400");
		base.stepInfo(
				"Verify that for each Batch redactions components navigation option to redacted terms should be displayed [<,>] on doc view redatcions panel");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// creating Batch Redaction
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getConfirmationBtn("Yes").waitAndClick(5);
		batch.verifyHistoryStatus(searchName);

		// selecting the Configured Saved search
		saveSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();

		base.waitForElement(docview.getDocView_RedactIcon());
		base.waitTillElemetToBeClickable(docview.getDocView_RedactIcon());
		docview.getDocView_RedactIcon().waitAndClick(10);
		dcRedact.verifyRedactionsSubMenu();

		// getting the Component Batch Redaction total count
		Element componentCount = docview.getComponentBatchRedactionsRatioCount();
		int ComponentBatchCount = docview.getRedactionCount(componentCount, "Component Batch Redaction");

		// navigating through Component Batch Redaction
		Element componentBR = docview.componentBatchredactionForwardNavigate();
		Element componentBRCount = docview.getComponentBatchRedactionsRatioCount();
		docview.navigateToRedaction(ComponentBatchCount, componentBR, componentBRCount, true, false);
		docview.navigatePreviousRedact(1, false, true, componentBRCount, Input.testData1);

		// Delete Searches
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify when RMU has search group with saved search in draft
	 *              mode, In Progress status under 'My Saved Search' should not be
	 *              displayed on batch redaction page[RPMXCON-53368]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifySavedSearchInDraftMode() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id:RPMXCON-53368");
		base.stepInfo(
				"Verify when RMU has search group with saved search in draft mode, In Progress status under 'My Saved Search' should not be displayed on batch redaction page");

		// Create saved search
		session.navigateToSessionSearchPageURL();
		int purehit = session.basicContentSearchWithSaveChanges(Input.testData1, "No", "First");
		session.saveSearch(searchName);

		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that batch redaction should be successful when saved
	 *              search is with Credit card number [RPMXCON-53379]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void performBRWithCreditCardNum() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String data = " \"##[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{2,4}\"";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id:RPMXCON-53379");
		base.stepInfo("Verify that batch redaction should be successful when saved search is with Credit card number");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearchInNewNode(searchName, null);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :[Covered localization]Verify when no action on click of 'No'
	 *              button from belly band message displays on click of 'View and
	 *              Redact' [RPMXCON-53386]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyRedactionTag() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53386");
		base.stepInfo(
				"[Covered localization]Verify when no action on click of 'No' button from belly band message displays on click of 'View and Redact'");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// Edit Profile Language to German
		login.editProfile("German - Germany");
		base.stepInfo("Successfully selected German Language");

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "No");

		String redactionaTag = base.getCurrentDropdownValue(batch.getRedactionTagDropDown());
		softAssertion.assertEquals(Input.defaultRedactionTag, redactionaTag);

		// Edit Profile Language to English.
		batch.getCloseBtn().waitAndClick(5);
		login.editProfile("English - United States");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Raghuram
	 * @Description : Verify that on click of delete icon of batch redaction
	 *              component belly band message should be displayedon doc view
	 *              [Covered localization] [RPMXCON-53404]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyConfirmationMessagelocalized() throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);

		base.stepInfo("Test case Id:RPMXCON-53404");
		base.stepInfo(
				"Verify that on click of delete icon of batch redaction component belly band message should be displayedon doc view [Covered localization]");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// selecting saved search for docView
		saveSearch.savedSearchToDocView(searchName);

		// chaning the language into German
		login.editProfile("German - Germany");
		base.stepInfo("Successfully selected German Language");

		// verifying redaction panel
		docview.verifyPanel();

		// verifying warning message whether it is Localized to German language
		docViewRedactions.VerifyDeletePopUp_BatchRedact(false);

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// deleting the saved search
		saveSearch.deleteSearch(searchName, "My Saved Search", "Yes");

		login.logout();
	}

	/**
	 * @Author Raghuram
	 * @Description : Verify that when navigated to the first redaction should be
	 *              able to navigate to last redactionon click of '<' from doc view
	 *              redactions [Cycle from First to Last while toggle between
	 *              redacted terms ] [RPMXCON-53402]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void verifyNavigatingToFirstAndLastRedaction() throws Exception {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);

		base.stepInfo("Test case Id:RPMXCON-53402");
		base.stepInfo(
				"Verify that when navigated to the first redaction should be able to navigate to last redactionon click of '<' from doc view redactions [Cycle from First to Last while toggle between redacted terms ]");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// selecting saved search for docView
		saveSearch.savedSearchToDocView(searchName);

		// verifying redaction panel
		docview.verifyPanel();

		// All Redactions navigating to First and Last Redaction
		base.stepInfo("For All Redactions navigating to First and Last Redaction");
		docview.navigateToRedaction(1, docview.getAllredactionForwardNavigate(), docview.getDocView_AllRedactionCount(),
				false, true);

		// Batch Redactions navigating to First and Last Redaction
		base.stepInfo("For Batch Redactions navigating to First and Last Redaction");
		docview.navigateToRedaction(1, docview.BatchredactionForwardNavigate(),
				docview.getDocView_BatchRedactionCount(), false, false);
		docview.navigatePreviousRedact(1, true, false, docview.getDocView_BatchRedactionCount(), null);

		// Batch Redaction Component navigating to First and Last Redaction
		base.stepInfo("For Batch Redaction Component navigating to First and Last Redaction");
		docview.navigateToRedaction(1, docview.componentBatchredactionForwardNavigate(),
				docview.getComponentBatchRedactionsRatioCount(), false, false);
		docview.navigatePreviousRedact(1, false, true, docview.getComponentBatchRedactionsRatioCount(), "crammer");

		// deleting the saved search
		saveSearch.deleteSearch(searchName, "My Saved Search", "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : [Covered localization] Verify that on click of 'Yes' button
	 *              from belly band message displays on click of 'Trash' icon, all
	 *              redactions occurances of the clicked batch redaction component
	 *              should be deleted from the document from doc view
	 *              [RPMXCON-53406]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyWarningMessageOnClickingTrashIcon() throws Exception {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docview = new DocViewPage(driver);

		base.stepInfo("Test case Id:RPMXCON-53406");
		base.stepInfo(
				"[Covered localization] Verify that on click of 'Yes' button from belly band message displays on click of 'Trash' icon, all redactions occurances of the clicked batch redaction component should be deleted from the document from doc view");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// selecting saved search for docView
		saveSearch.savedSearchToDocView(searchName);

		// chaning the language into German
		login.editProfile("German - Germany");
		String expectedMsg = "Profil erfolgreich aktualisiert";
		base.VerifySuccessMessageInGerman(expectedMsg);
		base.CloseSuccessMsgpopup();

		// verifying redaction panel
		docview.verifyPanel();

		// redaction count before Deletion
		base.stepInfo("Redaction count Before performing Delete Action");
		docview.getRedactionCount(docview.getDocView_AllRedactionCount(), "All Redactions");
		docview.getRedactionCount(docview.getDocView_BatchRedactionCount(), "Batch Redactions");

		// verifying warning message and click No button
		docViewRedactions.VerifyDeletePopUp_BatchRedact(false);

		// verifying warning message and click Yes button
		driver.waitForPageToBeReady();
		docViewRedactions.VerifyDeletePopUp_BatchRedact(true);

		// redaction count after Deletion
		base.stepInfo("updated Redaction Count After performing Delete Action");
		docview.getRedactionCount(docview.getDocView_AllRedactionCount(), "All Redactions");
		docview.getRedactionCount(docview.getDocView_BatchRedactionCount(), "Batch Redactions");

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that batch redaction should be successful when 'View
	 *              and Redact Searches' is selected for the search group with saved
	 *              searches (RPMXCON-53385)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyWhenUserClickAnalyzeGroup() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53385 Batch Redaction ");
		base.stepInfo(
				"Verify that batch redaction should be successful when 'View and Redact Searches' is selected for the search group with saved searches");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");

		// will load the session search page and search
		base.selectproject();
		int purehit = session.basicContentSearch(Input.testData1);

		// will save the query on created node under my saved search
		session.saveSearchInNewNode(searchName, newNode);

		// Check Analyse btn After search is saved
		batch.loadBatchRedactionPage(newNode);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false);
		batch.performViewReportAndApplyRedactions(newNode);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// bullhorn value before
		int bgCountBefore = base.initialBgCount();

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// bullhorn value after
		int bgCountAfter = base.initialBgCount();

		softAssertion.assertNotEquals(bgCountBefore, bgCountAfter);
		base.passedStep("Bull horn icon has New Notification");

		String docCount = batch.getDocCountFromTable(searchName).getText();
		softAssertion.assertNotEquals(docCount, Input.TextEmpty);
		base.stepInfo(docCount + " : is The Unique Document Count displayed in history");

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify batch redaction history record when batch redaction
	 *              fails for search and check for tooltip (RPMXCON-53333)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyFailedStatusToolTip() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String Annotation = "AT" + Utility.dynamicNameAppender();

		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		UserManagement users = new UserManagement(driver);

		// will login As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Tescase ID :RPMXCON-53333 Batch Redaction ");
		base.stepInfo(
				"Verify batch redaction history record when batch redaction fails for search and check for tooltip");

		// add SG and assign annotation and redaction tag
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(securityGroup);
		sgpage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		annotation.AddAnnotation(Annotation);
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.selectSecurityGroup(securityGroup);
		sgpage.assignAnnotationToSG(Annotation);

		// access to security group to Rmu
		users.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		// Create saved search
		session.basicContentSearch(Input.searchString5);
		session.bulkRelease(securityGroup);
		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		base.selectsecuritygroup(securityGroup);
		int purehit = session.basicContentSearch(Input.searchString5);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// delete Annotation
		annotation.deleteAnnotation(Annotation);
		batch.loadBatchRedactionPage(searchName);
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();

		// verify Failed status
		batch.verifyBatchHistoryStatus(searchName);

		String tooltipMsg = batch.getColorStatusToolTip(searchName).GetAttribute("data-content");
		String expectedMsg = "Hard failure.  There may be partial redactions.  Please contact your administrator.";
		base.textCompareEquals(tooltipMsg, expectedMsg, expectedMsg, "Tooltip is not displayed");

		// verify Doc count
		String docCount = batch.getDocCountFromTable(searchName).getText();
		base.compareTextViaContains(docCount, "0 /", docCount + " : is the docCount Displayed", "");

		base.selectsecuritygroup(Input.securityGroup);
		login.logout();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		sgpage.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 01/13/21 Modified date:N/A Modified by: Description
	 *         : Verify that inline scroll bar should be displayed for Saved
	 *         searches section on Batch Redaction home page when count is greater
	 *         than 10 : RPMXCON-53358 - Sprint 10
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void chechInlineScrollBarDisplayedforSavedSearchSection() throws InterruptedException {
		int limit = 9;
		String searchName = "SearchName_" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53358 Sprint 10");
		base.stepInfo(
				"Verify that inline scroll bar should be displayed for Saved searches section on Batch Redaction home page when count is greater than 10");
		base.stepInfo("Limit reach for scoll bar is : " + (limit + 1));

		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		base.selectproject();

		batch.verifyScrollBar(limit);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 01/17/21 Modified date:N/A Modified by: Description
	 *         : [Covered localization]Verify that belly band message should be
	 *         displayed on click of 'View and Redact' button and on confirming
	 *         message in right bottom tray to inform the user that background
	 *         process is started : RPMXCON-53338 - Sprint 10
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verfyTheBatchRedactionReportInBackGroundTaskAndDownload() throws Exception {

		String searchName = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id:RPMXCON-53338 Sprint 10");
		base.stepInfo(
				"[Covered localization]Verify that belly band message should be displayed on click of 'View andRedact' button and on confirming message in right bottom tray to inform the user that background process is started");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// chaning the language into German
		login.editProfile("German - Germany");
		base.stepInfo("Successfully selected German Language");

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		String bullHornValue = batch.getBullHornIcon_CC().getText();
		System.out.println(bullHornValue);
		int valueBforeAnalysis = Integer.parseInt(bullHornValue);

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// verifying the notification in bullhorn icon and downloading the Batch
		// redaction report
		batch.VerifyBGMessageForBatchRedactionReportDownload(searchName, valueBforeAnalysis);
		batch.verifyBatchRedactionFileDownload_Dynamic();

		// Edit Profile Language to English.
		login.editProfile("English - United States");

		// deleting the saved search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that when redaction occurance is present on same page
	 *              more than once then on rollback orphanredaction tag should not
	 *              be created(RPMXCON-53510)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyOrphanRedactioTagAfterRollBack() throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String tagName = "Tag Name" + Utility.dynamicNameAppender();
		String componentBR = "Component Batch Redaction";
		DocViewPage docview = new DocViewPage(driver);
		RedactionPage redact = new RedactionPage(driver);

		base.stepInfo("Test case Id:RPMXCON-53510");
		base.stepInfo(
				"Verify that when redaction occurance is present on same page more than once then on rollback orphanredaction tag should not be created");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// creating the redaction tag
		redact.AddRedaction(tagName, "RMU");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(tagName, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// selecting the Configured Saved search
		saveSearch.savedSearchToDocView(searchName);

		// verifying redaction panel
		docview.verifyPanel();

		// getting the total count of redaction
		int redactionCount = docview.getRedactionCount(docview.getComponentBatchRedactionsRatioCount(),
				"Component Batch Redaction");

		// verifying the presence of redaction tag
		docview.verifyPresenceOfOrphanRedactionTag(redactionCount, tagName,
				docview.componentBatchredactionForwardNavigate());

		// Rollbacking the select savedSearch
		batch.loadBatchRedactionPage(searchName);
		driver.scrollingToBottomofAPage();
		batch.verifyRollback(searchName, "Yes");

		// selecting the rollBacked Saved search
		saveSearch.savedSearchToDocView(searchName);

		// verifying redaction panel
		docview.verifyPanel();

		// getting the total count of redaction after rollback
		int redactionCount1 = docview.getRedactionCount(docview.getComponentBatchRedactionsRatioCount(), componentBR);

		// verifying the presence of redaction tag after rollBack
		docview.verifyPresenceOfOrphanRedactionTag(redactionCount1, tagName,
				docview.componentBatchredactionForwardNavigate());

		// delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// delete Redaction tag
		redact.DeleteRedaction(tagName);

		login.logout();

	}

	@DataProvider(name = "multipleSearchTerm")
	public Object[][] multipleSearchTerm() {
		Object[][] searchTerm = { 
				{ "denise" },
				{"*@consilio.com"},
//				{ "\"##[1-9]{3}-[1-9]{3}-[1-9]{4}\"" },
//				{ "*@enron.com" }, 
//				{"\"denise legasse\"~2"},
		};
		return searchTerm;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that delete and navigation icon should not be shifted
	 *              when serach term involves email addresses that are wrapped with
	 *              '<' and '>' [RPMXCON-53513]
	 * @param searchTerm
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "multipleSearchTerm", groups = { "regression" }, priority = 35)
	public void verifyRedactionNavigationIconAndDeleteIcon(String searchTerm) throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);

		base.stepInfo("Test case Id:RPMXCON-53513");
		base.stepInfo(
				"Verify that delete and navigation icon should not be shifted when serach term involves email addresses that are wrapped with '<' and '>'");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		session.basicContentSearch(searchTerm);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// selecting saved search for docView
		saveSearch.savedSearchToDocView(searchName);

		// verifying redaction panel
		docview.verifyPanel();

		// verifying the Navigation and Trash Icon
		docview.verifyingPositionOfNavigationIconAndTrashIcon(true, true, true);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : [Covered localization]Verify that informative message /error
	 *              message should be displayed on batch redactions page when
	 *              annotation layer, redaction tag is not mapped to the security
	 *              group [RPMXCON-53335]
	 * @param searchTerm
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void verifyingInformativeErrorMessageInBothLanguage() throws Exception {

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		String securityGroupName = "securityGroup" + Utility.dynamicNameAppender();
		UserManagement user = new UserManagement(driver);
		base.stepInfo("Test case Id:RPMXCON-53335");
		base.stepInfo(
				"[Covered localization]Verify that informative message /error message should be displayed on batch redactions page when annotation layer, redaction tag is not mapped to the security group");

		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroupName);

		// assigning the security group to user
		user.assignAccessToSecurityGroups(securityGroupName, Input.rmu1userName);

		login.logout();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// selecting the assigned security group
		base.selectsecuritygroup(securityGroupName);

		// verifying the Informative/Error message
		batch.verifyInformativeErrorMessage();

		// chaning the language into German
		login.editProfile("German - Germany");
		base.stepInfo("Successfully selected German Language");

		batch.verifyInformativeErrorMessage();

		// Edit Profile Language to English.
		login.editProfile("English - United States");
		base.selectsecuritygroup(Input.securityGroup);

		login.logout();

		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// deleting the security group
		security.deleteSecurityGroups(securityGroupName);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : Verify Batch Redaction navigation should work if there are
	 *              multiple batch redactions that are numeric [RPMXCON-53502]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void verifyRedactionNavigationIconAndDeleteIcon1() throws Exception {
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);
		String searchTerm = Integer.toString(Input.metaDataCNcount);

		base.stepInfo("Test case Id:RPMXCON-53502");
		base.stepInfo(
				"Verify Batch Redaction navigation should work if there are multiple batch redactions that are numeric");

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Create saved search
		session.basicContentSearch(searchTerm);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// selecting saved search for docView
		saveSearch.savedSearchToDocView(searchName);

		// verifying redaction panel
		docview.verifyPanel();

		// getting the Batch Redaction total count
		Element batchRedactCount = docview.getDocView_BatchRedactionCount();
		int batchRedactionCount = docview.getRedactionCount(batchRedactCount, "Batch Redaction");

		// navigating through Batch Redaction
		Element batchRedact = docview.BatchredactionForwardNavigate();
		Element batchRedactCOunt = docview.getDocView_BatchRedactionCount();
		docview.navigateToRedaction(batchRedactionCount, batchRedact, batchRedactCOunt, true, false);

		// delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that on click of 'Analyze Search for Redaction' button
	 *              of the Saved Search(es), success message should be displayed
	 *              [RPMXCON-53330]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyAnalyzeSearchForSavedSearch() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id:RPMXCON-53330 Batch Redaction");
		base.stepInfo(
				"Verify that on click of 'Analyze Search for Redaction' button of the Saved Search(es), success message should be displayed");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		batch.loadBatchRedactionPage(searchName);
		batch.performAnalysisGroupForRedcation(searchName, null, purehit, false);
		batch.verifyViewReportBtn(searchName, null);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the document history actions once Rollback is completed
	 *              successfully [RPMXCON-53467]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyDocumentHistoryAfterRollback() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String redactionTag = "Redact" + Utility.dynamicNameAppender();
		RedactionPage redact = new RedactionPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);

		String passMsg = "Rollback operation is applied on Document";
		String failMsg = "Redaction Count is still the same";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id:RPMXCON-53467 Batch Redaction");
		base.stepInfo("Verify the document history actions once Rollback is completed successfully");

		// add Redaction Tag
		redact.AddRedaction(redactionTag, "RMU");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(redactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// view docs in docview page
		session.ViewInDocView();
		docview.verifyRedactionPanel();

		// getting the Batch Redaction total count
		Element batchRedactCount = docview.getDocView_BatchRedactionCount();
		int batchRedactionCount = docview.getRedactionCount(batchRedactCount, "Batch Redaction");

		// perform ROLLBACK and view docs in DOCVIEW
		batch.loadBatchRedactionPage(searchName);
		batch.verifyRollback(searchName, Input.yesButton);
		session.ViewInDocView();
		docview.verifyRedactionPanel();

		// getting the Batch Redaction total count After Rollback
		Element batchRedactCount2 = docview.getDocView_BatchRedactionCount();
		int batchRedactionCountAfter = docview.getRedactionCount(batchRedactCount, "Batch Redaction");

		// compare count Before and After Rollback
		base.textCompareNotEquals(String.valueOf(batchRedactionCountAfter), String.valueOf(batchRedactionCount),
				passMsg, failMsg);

		// verify History On docview page
		docviewMetadata.verifyBrowseAllHistory_RedactionAddedStatus(true);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Localization : Verify that Relevant re-analyzed message
	 *              appears on "Batch Redaction" screen when user clicks on "Analyze
	 *              Search for Redactions" - having same document at the same time
	 *              on 2 different TABS [RPMXCON-53520]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyRelevantReAnalyzeMsgInGer() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53520 Batch Redaction");
		base.stepInfo(
				"Localization : Verify that Relevant re-analyzed message appears on \"Batch Redaction\" screen when user clicks on \"Analyze Search for Redactions\" - having same document at the same time on 2 different TABS");

		// Create saved search
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// Edit Profile to German
		login.editProfile("German - Germany");
		base.stepInfo("Profile Language is set to German");

		// first tab
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);

		// open duplicate tab
		String firstUserWindow = driver.CurrentWindowHandle();
		base.openDuplicateTab();

		// second tab
		driver.switchToChildWindow();
		base.stepInfo("Switched To Second Window");
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		String secondWindow = driver.CurrentWindowHandle();

		// first tab
		driver.switchToWindow(firstUserWindow);
		base.stepInfo("Switched To First Window");
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);
		base.stepInfo("Analyze Button is clicked On First Window");

		// second tab
		driver.switchToWindow(secondWindow);
		base.stepInfo("Switched To Second Window");
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);
		base.stepInfo("Analyze Button is clicked On Second Window");

		// verify Error Message
		base.VerifyErrorMessageInGerman(
				"DE: One or more of your selected searches are currently being re-analyzed. Please refresh and try again.");

		// Edit Profile to English
		login.editProfile("English - United States");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Relevant re-analyzed message appears on \"Batch
	 *              Redaction\" screen when user clicks on \"Analyze Search for
	 *              Redactions\" - having same document at the same time on 2
	 *              different TABS [RPMXCON-53516]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void verifyRelevantReAnalyzeMsgInEng() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53516 Batch Redaction");
		base.stepInfo(
				"Verify that Relevant re-analyzed message appears on \"Batch Redaction\" screen when user clicks on \"Analyze Search for Redactions\" - having same document at the same time on 2 different TABS");

		// Edit Profile to English
		login.editProfile("English - United States");

		// Create saved search
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// first tab
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);

		// open duplicate tab
		String firstUserWindow = driver.CurrentWindowHandle();
		base.openDuplicateTab();

		// second tab
		driver.switchToChildWindow();
		base.stepInfo("Switched To Second Window");
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		String secondWindow = driver.CurrentWindowHandle();

		// first tab
		driver.switchToWindow(firstUserWindow);
		base.stepInfo("Switched To First Window");
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);
		base.stepInfo("Analyze Button is clicked On First Window");

		// second tab
		driver.switchToWindow(secondWindow);
		base.stepInfo("Switched To Second Window");
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);
		base.stepInfo("Analyze Button is clicked On Second Window");

		// verify Error Message
		base.VerifyErrorMessage(
				"One or more of your selected searches are currently being re-analyzed. Please refresh and try again.");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that tool tip should be displayed for the partial batch
	 *              redactions on doc view redactions panel[RPMXCON-53397]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyCompletedErrorToolTip() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions DCRedactions = new DocViewRedactions(driver);

		String expected = "Partial Batch Redactions";
		String passMsg = expected + " is Displayed in Tooltip";
		String failMsg = "Expected ToolTipMsg is not Dispalyed";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53397 Batch Redaction");
		base.stepInfo(
				"Verify that tool tip should be displayed for the partial batch redactions on doc view redactions panel");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		base.ValidateElement_Presence(batch.getCompletedWithErrorsStatus(searchName), "Completed with Error");

		// Traverse from Saved search To Doc view
		saveSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();
		docview.verifyRedactionPanel();
		DCRedactions.verifyRedactionsSubMenu();

		// verifyCompleted Icon and ToolTip
		batch.loadBatchRedactionPage(searchName);
		batch.verifyCompletedErrorToolTip(searchName, expected, passMsg, failMsg);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Raghuram.A date: 02/7/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-53518
	 * @Description : Verify that Relevant Rollback message appears on "Batch
	 *              Redaction" screen when user tries to perform rollback having
	 *              same document at the same time on 2 different TABS - Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verfifyRollBackMsgFromTabTwo() throws InterruptedException, ParseException, AWTException {
		SessionSearch sessionSearch = new SessionSearch(driver);
		String SearchName = "SearchName" + Utility.dynamicNameAppender();

		base.stepInfo("Test case id :RPMXCON-53518 Sprint 12");
		base.stepInfo(
				"Verify that Relevant Rollback message appears on \"Batch Redaction\" screen when user tries to perform rollback having same document at the same time on 2 different TABS");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Search and Save a Search
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(SearchName);

		// perform Batch redaction and verify Status
		batch.VerifyBatchRedaction_ElementsDisplay(SearchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");
		batch.verifyBatchHistoryStatus(SearchName);

		// perform RollBack
		base.stepInfo("Perform RollBack from Tab 1");
		batch.rollBack(SearchName);

		// Another way - Press ALT + D + Enter
		base.stepInfo("Initiating Duplicate Current tab");
		String firstUserWindow = driver.CurrentWindowHandle();
		base.openDuplicateTab();

		// Switch to Child Tab
		base.stepInfo("Switch to Child Tab ( Duplicated tab )");
		driver.switchToChildWindow();
		driver.waitForPageToBeReady();

		// perform RollBack
		base.stepInfo("Perform RollBack from Tab 2");
		batch.rollBack(SearchName);

		// Switch back to Initial tab
		base.stepInfo("Switch back to Tab 1");
		driver.switchToWindow(firstUserWindow);

		// Rollback confirmation from Tab 1
		base.waitTime(4);
		base.stepInfo("Rollback confirmation from Tab 1");
		batch.rollBackActionConfirmation("Yes");
		String ExpectedMsg = "Your request to Roll Back this Batch Redaction has been added to the background.  Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.";
		base.VerifySuccessMessageB(ExpectedMsg);

		// Switch to Child Tab
		base.stepInfo("Switch back to Tab 2");
		driver.switchToChildWindow();
		driver.waitForPageToBeReady();

		// Rollback confirmation from Tab 2
		base.stepInfo(
				"Verify that Relevant Rollback message appears on \"Batch Redaction\" screen when user tries to perform rollback having same document at the same time on 2 different TABS");
		base.stepInfo("Rollback confirmation from Tab2");
		batch.getPopupYesBtn().waitAndClick(2);
		String ExpectedMsgErr = "A Rollback for this Batch Redaction has already been requested. Please refresh to see status.";
		base.VerifyErrorMessage(ExpectedMsgErr);
		base.passedStep(
				"Verified Msg : A Rollback for this Batch Redaction has already been requested. Please refresh to see status.");

		driver.Navigate().refresh();
		String statsMsg = batch.getRollbackMsg(SearchName).getText();
		base.compareTextViaContains(statsMsg, "Rollback Completed", "Successfullfy rollbacked", "Error in rollback");

		// Delete search
		base.stepInfo("Initiating delete Searh");
		saveSearch.deleteSearch(SearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Relevant need to be re-analyzed message appears on
	 *              \"Batch Redaction\" screen when user tries to perform Redaction
	 *              and Analysed having same document at the same time on 2
	 *              different TABS [RPMXCON-53519]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void performRedactionAndAnalyzeSameDoc() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String expectedErrorMsg = "One or more of your selected searches need to be re-analyzed. Please refresh and try again.";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53519 Batch Redaction");
		base.stepInfo(
				"Verify that Relevant need to be re-analyzed message appears on \"Batch Redaction\" screen when user tries to perform Redaction and Analysed having same document at the same time on 2 different TABS");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// first tab
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);

		// open duplicate tab
		String firstUserWindow = driver.CurrentWindowHandle();
		base.openDuplicateTab();

		// second tab
		driver.switchToChildWindow();
		base.stepInfo("Switched To Second Window");
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		String secondWindow = driver.CurrentWindowHandle();

		// first tab
		driver.switchToWindow(firstUserWindow);
		base.stepInfo("Switched To First Window");
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);

		// second tab
		driver.switchToWindow(secondWindow);
		base.stepInfo("Switched To Second Window");
		batch.performAnalysisGroupForRedcation(searchName, null, purehit, false);
		batch.verifyViewReportBtn(searchName, null);

		// first tab
		driver.switchToWindow(firstUserWindow);
		base.stepInfo("Switched To First Window");
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(searchName);

		// second tab
		driver.switchToWindow(secondWindow);
		base.stepInfo("Switched To Second Window");
		base.waitForElement(batch.getViewReportForSavedSearch(searchName));
		batch.getViewReportForSavedSearch(searchName).waitAndClick(10);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		base.stepInfo("Analyze Button is clicked On Second Window");

		// verify Error Message
		base.VerifyErrorMessage(expectedErrorMsg);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Localization : Verify that Relevant redacted message appears on
	 *              \"Batch Redaction\" screen when user tries to perform Redaction
	 *              having same document at the same time on 2 different TABS
	 *              [RPMXCON-53521]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void analyzeSameDocAtSameTime() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String expectedErrorMsg = "DE: One or more of your selected searches are currently being redacted. Please refresh and try again.";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-53521 Batch Redaction");
		base.stepInfo(
				"Localization : Verify that Relevant redacted message appears on \"Batch Redaction\" screen when user tries to perform Redaction having same document at the same time on 2 different TABS");

		// Create saved search
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// Edit Profile to German
		login.editProfile("German - Germany");
		base.stepInfo("Profile Language is set to German");

		// first tab
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);

		// open duplicate tab
		String firstUserWindow = driver.CurrentWindowHandle();
		base.openDuplicateTab();

		// second tab
		driver.switchToChildWindow();
		base.stepInfo("Switched To Second Window");
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		String secondWindow = driver.CurrentWindowHandle();

		// first tab
		driver.switchToWindow(firstUserWindow);
		base.stepInfo("Switched To First Window");
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);

		// second tab
		driver.switchToWindow(secondWindow);
		base.stepInfo("Switched To Second Window");
		batch.performAnalysisGroupForRedcation(searchName, null, purehit, false);
		base.waitForElement(batch.getViewReportForSavedSearch(searchName));
		batch.getViewReportForSavedSearch(searchName).waitAndClick(10);

		// first tab
		driver.switchToWindow(firstUserWindow);
		base.stepInfo("Switched To First Window");
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);

		// second tab
		driver.switchToWindow(secondWindow);
		base.stepInfo("Switched To Second Window");
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		base.stepInfo("Analyze Button is clicked On Second Window");

		// verify Error Message
		base.VerifyErrorMessageInGerman(expectedErrorMsg);

		// Edit Profile to English
		login.editProfile("English - United States");
		base.stepInfo("Profile Language is set to English");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		batch = new BatchRedactionPage(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//			login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
