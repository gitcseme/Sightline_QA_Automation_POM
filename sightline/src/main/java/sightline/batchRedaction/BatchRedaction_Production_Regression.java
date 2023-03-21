package sightline.batchRedaction;

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

public class BatchRedaction_Production_Regression {

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
	String tagname;
	String redactionStyle = "White with black font";

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
		Object[][] users = { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }, 
				};
		return users;
	}

	@DataProvider(name = "reserveWords")
	public Object[][] dataMethod() {
		return new Object[][] { 
			    { "\"##[0-9]{5}-[0-9]{4}\"", "RPMXCON-53377" },
				{ "\"##[5-9]{5}-[1-4]{4}\"", "RPMXCON-53376" },
				{ " \"##[0-9]{3}-[0-1]{2}-[1-4]{4}\"", "RPMXCON-53374" },
				{ "\"##[0-9]{3}-[0-9]{3}-[0-9]{4}\"", "RPMXCON-53373" }, };
	}

	/**
	 * @throws InterruptedException
	 * @created By Jeevitha.R Description: Verify that batch redaction with saved
	 *          search for zip code ,phonenumber
	 * 
	 */
	@Test(description ="RPMXCON-53377,RPMXCON-53376,RPMXCON-53374,RPMXCON-53373",
			enabled = true, dataProvider = "reserveWords", groups = { "regression" })
	public void verifyBatchRedact(String data, String testCaseId) throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID : " + testCaseId + "  Bacth Redaction");

		// Test Case Description
		if (testCaseId.contentEquals("RPMXCON-53377")) {
			base.selectproject(Input.additionalDataProject);
			base.stepInfo(
					"Verify that batch redaction with saved search for zip code with format like \"##[0-9]{5}-[0-9]{4}\"");
		} else if (testCaseId.contentEquals("RPMXCON-53376")) {
			base.stepInfo(
					"Verify that batch redaction should be successful with saved search for ZIPCODE with format like \"##[5-9]{5}-[1-4]{4}\"");
		} else if (testCaseId.contentEquals("RPMXCON-53374")) {
			base.stepInfo(
					"Verify that Batch Redaction when Saved search is with regular expression including SSN like \"##[0-9]{3}-[0-1]{2}-[1-4]{4}\" /SSN starting with charactersfrom Batch Redaction Home page");
		} else if (testCaseId.contentEquals("RPMXCON-53373")) {
			base.selectproject(Input.additionalDataProject);
			base.stepInfo(
					"Verify that Batch Redaction when Saved search is with regular expression with phone number format like \"##[0-9]{3}-[0-9]{3}-[0-9]{4}\" from Batch Redaction Home page");
		}

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearch(searchName);

		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that Batch Redaction should be successful when selected
	 *              Saved search is with regular expression with format like
	 *              \"##77\" from Batch Redaction Home page (RPMXCON-53348)
	 * @Description : Verify that Rollback should be successful when selected Saved
	 *              search is with regular expression [Pattern] (RPMXCON-53349)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-53348", enabled = true, groups = { "regression" })
	public void verifyBatchRedactWithRegExp() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		String data = "\"##77\"";
		DocViewMetaDataPage docviewMetadata = new DocViewMetaDataPage(driver);

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.additionalDataProject);
		
		base.stepInfo("Testcase ID : RPMXCON-53348  Bacth Redaction");
		// Test Case Description
		base.stepInfo(
				"Verify that Batch Redaction should be successful when selected Saved search is with regular expression with format like \"##77\" from Batch Redaction Home page");
		base.stepInfo("Testcase ID : RPMXCON-53349  Bacth Redaction");
		base.stepInfo(
				"Verify that Rollback should be successful when selected Saved search is with regular expression [Pattern]");

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearch(searchName);

		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		base.stepInfo("Testcase ID : RPMXCON-53349  Bacth Redaction");
		base.stepInfo(
				"Verify that Rollback should be successful when selected Saved search is with regular expression [Pattern]");

		// perform RollBack
		batch.verifyRollback(searchName, "No");
		batch.verifyRollback(searchName, "Yes");

		// verify Rollback Notification
		batch.rollbackBatchRedactionReport();

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		driver.waitForPageToBeReady();
		docviewMetadata.verifyBrowseAllHistory_RedactionAddedStatus(false);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that batch redaction when saved search with pattern
	 *              \"simple\" email - 99% coverage[RPMXCON-53375]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-53375", enabled = true, groups = { "regression" })
	public void verifyBrWithPatternEmail() throws Exception {
		String searchName = "SearchNames" + Utility.dynamicNameAppender();
		String data = "\"##[a-z0-9._%+\\-]+@[a-z0-9.\\-]+\\.[a-z]{2,}\"";

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.additionalDataProject);
		
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
	 * @Description : [Batch Redactions- TC 12386]Verify that PDF should Export with
	 *              Burned Redaction if Only Burn Redaction is enabled for batch
	 *              redacted documents (RPMXCON-53464)
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-53464", enabled = true, dataProvider = "Users", groups = { "regression" })
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
		page.navigateToProductionPage();
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
	@Test(description ="RPMXCON-53463", enabled = true, dataProvider = "Users", groups = { "regression" })
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
	 * @author Aathiht.Senthilkumar RPMXCON-53462
	 * @Description : [Batch Redactions- TC 11372]Verify that production should
	 *              generated with modified Redaction placeholder text for batch
	 *              redacted documents.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-53462", enabled = true, groups = { "regression" })
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
		String beginningBates1 = page.getRandomNumber(2);

		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates1);
		
		page.navigateToProductionPage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates2 = page.getRandomNumber(2);

		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname1,
				beginningBates2);
		login.logout();
		
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page.navigateToProductionPage();
		String productionname3 = "p" + Utility.dynamicNameAppender();
		String beginningBates3 = page.getRandomNumber(2);

		page.addANewProduction(productionname3);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname3,
				beginningBates3);
		
		page.navigateToProductionPage();
		String productionname4 = "p" + Utility.dynamicNameAppender();
		String beginningBates4 = page.getRandomNumber(2);

		page.addANewProduction(productionname4);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithBurnRedaction(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname4,
				beginningBates4);
		base.passedStep(
				"Verified that production should generated with modified Redaction placeholder text for batch redacted documents");

		// Delete created folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
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
	@Test(description ="RPMXCON-53475", enabled = true, groups = { "regression" })
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
	 * @author Jeevitha Description : To verify that if annotation layer option is
	 *         selected in Tiff section and document is batch redacted then selected
	 *         Metadata should not be displayed on DAT(RPMXCON-53460 )
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-53460", enabled = true,dataProvider = "Users", groups = { "regression" })
	public void verifyAnnotationLayer(String username, String password) throws InterruptedException {
		tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String PrefixID = "A_" + Utility.dynamicNameAppender();
		;
		String SuffixID = "_P" + Utility.dynamicNameAppender();
		;
		String foldername = "FolderProd" + Utility.dynamicNameAppender();

		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id:RPMXCON-53460    Batch Redaction");

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for the created folder and check the pure hit count
		session = new SessionSearch(driver);
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getDATRedactionsCBox());
		page.getDATRedactionsCBox().waitAndClick(10);
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedaction(redactionStyle, "layer", null);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(PrefixID, SuffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		login.logout();
	}

	/**
	 * @author Jeevitha Description : To Verify Redaction Style in PDF & TIFF
	 *         Section"White with Black font" is selected, the redaction applied
	 *         will have white redaction with black redaction text if any
	 *         specified(RPMXCON-53458 )
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-53458", enabled = true,dataProvider = "Users", groups = { "regression" })
	public void verifyRedactionStyle(String username, String password) throws InterruptedException {
		String productionname = "P" + Utility.dynamicNameAppender();
		String PrefixID = "A_" + Utility.dynamicNameAppender();
		String SuffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		
		tagname = "Tag" + Utility.dynamicNameAppender();
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id:RPMXCON-53458    Batch Redaction");

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for the created folder and check the pure hit count
		session = new SessionSearch(driver);
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedaction(redactionStyle, "layer", null);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(PrefixID, SuffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		login.logout();

	}

	/**
	 * @author Jeevitha Description : To Verify Redaction text is printed on the
	 *         redactions burned on the TIFFs(RPMXCON-53457 )
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-53457", enabled = true,groups = { "regression" })
	public void verifyRedactionText() throws InterruptedException {
		String productionname = "P" + Utility.dynamicNameAppender();
		String PrefixID = "A_" + Utility.dynamicNameAppender();
		;
		String SuffixID = "_P" + Utility.dynamicNameAppender();
		;
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-53457    Batch Redaction");

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for the created folder and check the pure hit count
		session = new SessionSearch(driver);
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedaction(null, "Tags", Input.defaultRedactionTag);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(PrefixID, SuffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		login.logout();

	}

	/**
	 * @author Jeevitha Description :To verify that redaction text should be printed
	 *         on burned redaction if user selects Tiff OR PDF(RPMXCON-53459 )
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-53459", enabled = true,dataProvider = "Users", groups = { "regression" })
	public void verifyRedactionText2(String username, String password) throws InterruptedException {
		String productionname = "P" + Utility.dynamicNameAppender();
		String PrefixID = "A_" + Utility.dynamicNameAppender();
		String SuffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id:RPMXCON-53459    Batch Redaction");

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for the created folder and check the pure hit count
		session = new SessionSearch(driver);
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		// generate TIFF file
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedaction(null, "Tag", Input.defaultRedactionTag);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(PrefixID, SuffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// Generate PDF File
		page.uncommitFunction();
		page.clickBackBtnUntilElementFound(page.getTIFFTab());

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitForElement(page.getbtnComponentsMarkIncomplete());
		page.getbtnComponentsMarkIncomplete().waitAndClick(10);
		base.waitForElement(page.getPDFGenerateRadioButton());
		page.getPDFGenerateRadioButton().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Check whether user can complete the Batch redaction on not
	 *              redacted content search data documents (RPMXCON-61152)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-61152", enabled = true, groups = { "regression" })
	public void checkWhetherCanCompleteBrAndDownloadPreReport() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-61152  Bacth Redaction ");
		base.stepInfo(
				"Check whether user can complete the Batch redaction on not redacted content search data documents");

		// basic search and view in docview
		session.basicContentSearch(Input.searchString2);
		session.ViewInDocView();
		docview.verifyPanel();

		// navigate back to SS page and again view in doc view
		session.navigateToSessionSearchPageURL();
		session.removePureHitsFromSelectedResult();
		session.addNewSearch();
		session.multipleBasicContentSearch(Input.testData1);
		session.ViewInDocView();
		docview.verifyPanel();

		// save the search
		driver.waitForPageToBeReady();
		session.navigateToSessionSearchPageURL();
		session.saveSearch(searchName);

		// perform BatchRedaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);

		// Download Pre-Redaction Report
		String fileName = batch.verifyPreRedactionReport();

		// Select Tag and verify history
		batch.loadBatchRedactionPage(Input.mySavedSearch);
		base.waitForElement(batch.getViewReportForSavedSearch(searchName));
		batch.getViewReportForSavedSearch(searchName).waitAndClick(10);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyHistoryStatus(searchName);

		// verify Expected Redaction
		batch.verifyExpectedRedactionCount(fileName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Check whether user can complete the Batch redaction upon the
	 *              Text redaction of other than searched data on few of the
	 *              resulted searched documents using same redaction tag
	 *              (RPMXCON-61151)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-61151", enabled = true, groups = { "regression" })
	public void checkWhetherCanCompleteBrUponTextRedaction() throws Exception {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);
		DocViewRedactions dcRedact = new DocViewRedactions(driver);

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-61151  Bacth Redaction ");
		base.stepInfo(
				"Check whether user can complete the Batch redaction upon the Text redaction of other than searched data on few of the resulted searched documents using same redaction tag");

		// basic search and view in docview
		session.basicContentSearch(Input.searchString2);
		session.ViewInDocView();
		dcRedact.RedactTextInDocView(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		dcRedact.selectingRedactionTag2(Input.defaultRedactionTag);

		// navigate back to SS page and again view in doc view
		session.navigateToSessionSearchPageURL();
		session.removePureHitsFromSelectedResult();
		session.addNewSearch();
		session.multipleBasicContentSearch(Input.testData1);
		session.ViewInDocView();
		docview.verifyPanel();

		// save the search
		driver.waitForPageToBeReady();
		session.navigateToSessionSearchPageURL();
		session.saveSearch(searchName);

		// perform BatchRedaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);

		// Download Pre-Redaction Report
		String fileName = batch.verifyPreRedactionReport();

		// Select Tag and verify history
		batch.loadBatchRedactionPage(Input.mySavedSearch);
		base.waitForElement(batch.getViewReportForSavedSearch(searchName));
		batch.getViewReportForSavedSearch(searchName).waitAndClick(10);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyHistoryStatus(searchName);

		// verify Expected Redaction
		batch.verifyExpectedRedactionCount(fileName);

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
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
