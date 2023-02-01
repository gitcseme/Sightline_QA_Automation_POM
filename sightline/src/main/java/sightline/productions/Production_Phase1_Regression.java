package sightline.productions;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Phase1_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	DocListPage docPage;
	DocViewPage docViewPage;
	SoftAssert softAssertion;

	String foldername;
	String tagname;
	String productionname;
	String batesNumber;
	String productionSet;
	String templateName;
	String exportname;
	String TempName;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();
		base = new BaseClass(driver);
		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55991
	 * @Description:Verify that if Production is regenerated then status should be
	 *                     change on Production progress status bar on Tile View
	 */

	@Test(description = "RPMXCON-55991", enabled = true, groups = { "regression" })
	public void verifyStatusAfterRegeneratingProductionInHomePage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-55991- Production Sprint 06");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndRegeneratingAgain();

		// Go To Production Home Page
		page.navigateToProductionPage();
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Preparing Data", productionname);
		page.verifyProductionStatusInHomePage("Pre-Gen Checks", productionname);
		page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);

		// Clicking continue generation button in generate page

		driver.waitForPageToBeReady();
		page.getGearIconForProdName(productionname).waitAndClick(10);

		base.waitForElement(page.getOpenWizard());
		page.getOpenWizard().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(page.getbtnContinueGeneration());
		page.getbtnContinueGeneration().waitAndClick(10);

		// Go To Production Home Page
		page.navigateToProductionPage();
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Prod Generation ", productionname);
		page.verifyProductionStatusInHomePage("Generating Load Files", productionname);
		page.verifyProductionStatusInHomePage("Exporting Files ", productionname);
		page.verifyProductionStatusInHomePage("Creating Archive", productionname);
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55992
	 * @Description:Verify that if Producion is regnerated then status should be
	 *                     change on Production progress status bar on Generate page
	 */

	@Test(description = "RPMXCON-55992", enabled = true, groups = { "regression" })
	public void verifyStatusAfterRegeneratingProductionInGeneratePage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-55992- Production Sprint 06");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Wait until Generate button enables
		base.waitForElement(page.getbtnProductionGenerate());
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		driver.getWebDriver().get(Input.url + "Production/Home");
		
		page.reGenarate(productionname);
		base.passedStep(
				"Producion is regnerated  then status should be change on Production progress status bar on Generate page");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56087
	 * @Description:Verify that Production should generate successfully by selecting
	 *                     only DAT and 'Generate TIFF' option with TechIssue
	 *                     Placeholder
	 */

	@Test(description = "RPMXCON-56087", enabled = true, groups = { "regression" })
	public void generateTIFFWithTechIssue() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-56087- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagname, Input.technicalIssue);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("Generated TIFF with Tech Issue Placeholder by selecting only DAT");

		// Delete Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56089
	 * @Description:Verify that Production should generate successfully by selecting
	 *                     only DAT and 'Generate TIFF' option with Natively
	 *                     Produced Documents Placeholder
	 */

	@Test(description = "RPMXCON-56089", enabled = true, groups = { "regression" })
	public void generateTIFFWithNativelyPlaceholder() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56089- Production Sprint 06");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("Generated Production with DAT and Natively Produced Documents Placholder");

		// Delete Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56093
	 * @Description:Verify the error message for DAT component when 'Don't add any
	 *                     data field '
	 */

	@Test(description = "RPMXCON-56093", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDAT() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56093- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();
		base.waitForElement(page.getDATTab());
		page.getDATTab().Click();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();
		base.waitForElement(page.getErrorMsgInDAT());
		String actual = page.getErrorMsgInDAT().getText();
		String expected = "No fields are added in the DAT section. Please complete before navigating to the next step.";
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep(
				"Error message displayed in DAT as No fields are added in the DAT section. Please complete before navigating to the next step.");
		loginPage.logout();

	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56092
	 * @Description:Verify the error message for DAT component when 'Add same data
	 *                     field more than one time'
	 */

	@Test(description = "RPMXCON-56092", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDATByRepeatingSameField() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56092- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();

		base.waitForElement(page.getBtnAddFieldInDAt());
		page.getBtnAddFieldInDAt().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(page.getDAT_FieldClassification2());
		page.getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(Input.bates);

		base.waitForElement(page.getDAT_SourceField2());
		page.getDAT_SourceField2().selectFromDropdown().selectByVisibleText(Input.batesNumber);

		base.waitForElement(page.getDAT_DATField2());
		page.getDAT_DATField2().SendKeys("B" + Utility.dynamicNameAppender());

		driver.scrollPageToTop();

		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		base.waitForElement(page.getErrorMsgPopupInDAT());
		String actual = page.getErrorMsgPopupInDAT().getText();
		String expected = "In the DAT configuration, at least one of the project fields is mapped to multiple DAT fields. Do you want to continue with this same configuration?";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep(
				"Error message displayed in DAT as In the DAT configuration, at least one of the project fields is mapped to multiple DAT fields. Do you want to continue with this same configuration?");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56098
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     with different source field and same DAT field name'
	 */

	@Test(description = "RPMXCON-56098", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDATForSameDatFieldName() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56098- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		batesNumber = "B" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesNumber);

		// Create instance of Robot class
		Robot robot = new Robot();

		// Create instance of Clipboard class
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		// Set the String to Enter
		StringSelection stringSelection = new StringSelection(batesNumber);

		// Copy the String to Clipboard
		clipboard.setContents(stringSelection, null);

		base.waitForElement(page.getBtnAddFieldInDAt());
		page.getBtnAddFieldInDAt().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(page.getDAT_FieldClassification2());
		page.getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(Input.bates);

		base.waitForElement(page.getDAT_SourceField2());
		page.getDAT_SourceField2().selectFromDropdown().selectByVisibleText(Input.ChildBates);

		page.getDAT_DATField2().Click();
		base.waitForElement(page.getDAT_DATField2());

		// Paste the text

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		driver.scrollPageToTop();

		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		base.waitForElement(page.getErrorMsgInDATForSameDatField());
		String actual = page.getErrorMsgInDATForSameDatField().getText();
		String expected = "Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep(
				"Error message displayed in DAT as Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56097
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     with special character'
	 */

	@Test(description = "RPMXCON-56097", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDATWithSpecialCharacter() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56097- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String special = "T?T";
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, special);

		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		base.waitForElement(page.getErrorMsgInDATWithSpecialCharacter());
		String actual = page.getErrorMsgInDATWithSpecialCharacter().getText();
		String expected = "Special characters other than underscore (_) are not allowed in the  DAT field names. Please check.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep(
				"Error message displayed in DAT as Special characters other than underscore (_) are not allowed in the  DAT field names. Please check.");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56096
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     other that alphabet character'
	 */

	@Test(description = "RPMXCON-56096", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDATAddingOtherCharacter() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56096- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String special = "?";
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, special);

		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		base.waitForElement(page.getErrorMsgInDATWithOtherCharacter());
		String actual = page.getErrorMsgInDATWithOtherCharacter().getText();
		String expected = "DAT field names must begin with an alphabet. Please check.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep("Error message displayed in DAT as DAT field names must begin with an alphabet. Please check.");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:12/01/21 TESTCASE No:RPMXCON-56094
	 * @Description:Verify the error message for DAT component when 'Don't add bate
	 *                     number data field'
	 */
	@Test(description = "RPMXCON-56094", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDATByNotAddingBates() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56094- Production Sprint 07");

		UtilityLog.info(Input.prodPath);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		batesNumber = "B" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		base.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();

		base.waitForElement(page.getDATTab());
		page.getDATTab().Click();

		base.waitForElement(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.productionText);

		base.waitForElement(page.getDAT_SourceField1());
		driver.waitForPageToBeReady();
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.tiffPageCountNam);

		base.waitForElement(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(batesNumber);

		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		base.waitForElement(page.getErrorMsgInDATWithoutAddingBates());
		String actual = page.getErrorMsgInDATWithoutAddingBates().getText();
		String expected = "Bates Number must be selected in the DAT for a production.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep("Error message displayed in DAT asBates Number must be selected in the DAT for a production.");
		loginPage.logout();

	}

	/**
	 * @Author Sowndarya.Velraj created on:12/01/21 TESTCASE No:RPMXCON-56095
	 * @Description:Verify the error message for DAT component when 'don't select
	 *                     DAT component'
	 */
	@Test(description = "RPMXCON-56095", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDATByNotSelectingDAT() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56095- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		batesNumber = "B" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		base.waitForElement(page.getDATChkBox());

		base.waitForElement(page.getDATTab());
		page.getDATTab().Click();

		base.waitForElement(page.getDAT_FieldClassification1());
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);

		base.waitForElement(page.getDAT_SourceField1());
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.batesNumber);

		base.waitForElement(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(batesNumber);

		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		base.waitForElement(page.getErrorMsgInDATWithoutSelectingDAT());
		String actual = page.getErrorMsgInDATWithoutSelectingDAT().getText();
		String expected = "Selection of the DAT component is mandatory for a production.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		base.passedStep(
				"Error message displayed in DAT as Selection of the DAT component is mandatory for a production.");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56044
	 * @Description:Verify that once Generation is completed it should displays
	 *                     'Prod Generation Complete' status in Grid View on
	 *                     Production Home page
	 */

	@Test(description = "RPMXCON-56044", enabled = true, groups = { "regression" })
	public void verifyPostGenCompleteStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56044- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		driver.waitForPageToBeReady();

		// Go To Production Home Page
		page.navigateToProductionPage();
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);
		base.passedStep(
				" Generation is completed and displays 'Prod Generation Complete' status in Grid View on Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/2/21 TESTCASE No:RPMXCON-56042
	 * @Description:Verify that it should displays 'Pre-Gen Checks - 19999/20000
	 *                     docs' status on Grid View in Production Home page
	 *                     TESTCASE No:RPMXCON-56036
	 * @Description:Verify that after Pre-gen checks is in progress, it will
	 *                     displays status on Production Grid view
	 */
	@Test(description = "RPMXCON-56042,RPMXCON-56036", enabled = true, groups = { "regression" })
	public void verifyPreGenChecksInHomePage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56042- Production Sprint 07");
		base.stepInfo("Test case Id: RPMXCON-56036- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Starting the generation
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(540);
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.waitForPageToBeReady();

		base.stepInfo("Going to Home Page");
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks", productionname);
		base.passedStep(
				" Generation is completed and displays 'Pre-Gen Checks - 19999/20000 docs' status in Grid View on Production Home page");

//		loginPage.logout();
//		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/3/21 TESTCASE No:RPMXCON-56039
	 * @Description:Verify that after Pregen check completed it should displays
	 *                     'Reserving Bates Range' status on Grid View on Production
	 *                     Home page
	 */
	@Test(description = "RPMXCON-56039", enabled = true, groups = { "regression" })
	public void verifyReservingBatesRangeInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56039- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Starting the generation
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(540);
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.waitForPageToBeReady();

		base.stepInfo("Going to Home Page");
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);
		base.passedStep(" 'Reserving Bates Range' status on Grid View on Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/3/21 TESTCASE No:RPMXCON-56038
	 * @Description:Verify that after Pregen checks completed it should displays
	 *                     'Pre-Gen Checks Complete' status on Production Grid View
	 */
	@Test(description = "RPMXCON-56038", enabled = true, groups = { "regression" })
	public void verifyPreGenChecksInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56038- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);

		page.goToProductionGridView();
		page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks Complete", productionname);
		base.passedStep(" verified 'Pre-Gen Checks Complete 'status on Grid View on Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-56016
	 * @Description:Verify that after Pregen checks completed it should displays
	 *                     'Pre-Gen Checks Complete' status on Progress bar in Tile
	 *                     View on Production Home page
	 */
	@Test(description = "RPMXCON-56016", enabled = true, groups = { " regression" })
	public void verifyPreGenChecksCompleteInHomePage() throws Exception {

		base.stepInfo("Test case Id RPMXCON-56016- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Starting the generation
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Pre-Gen Checks Complete", productionname);
		base.passedStep("verified 'Pre-Gen Checks Complete 'status on  Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-55985
	 * @Description:Verify that once Post Geneation is in progress, it will displays
	 *                     status on Production Generation page as 'Post-Gen QC
	 *                     checks In Progress'
	 */
	@Test(description = "RPMXCON-55985", enabled = true, groups = { " regression" })

	public void verifyPostGenQCchecksInProgressInGeneratePage() throws Exception {

		base.stepInfo("Test case Id RPMXCON-55985- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Starting the generation
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(120);
		Reporter.log("Wait for generate to complete, true");
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		page.getbtnContinueGeneration().isElementAvailable(60);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}

		page.verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");
		base.passedStep(" Generation is completed and displays Post-Gen QC Checks In Progress' status Generation page");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-55984
	 * @Description:Verify that once Post Generation is in progress, it will
	 *                     displays status on Production Progress bar ,Tile View as
	 *                     'Post-Gen QC Checks In Progress'
	 */
	@Test(description = "RPMXCON-55984", enabled = true, groups = { " regression" })
	public void verifyPostGenQCchecksInProgressInTileView() throws Exception {

		base.stepInfo("Test case Id RPMXCON-55984- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		// Starting the generation
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		base.waitTime(1);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks In Progress", productionname);
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);
		base.passedStep(
				" Post Generation is in progress, it will displays status on Production Progress bar ,Tile View as 'Post-Gen QC Checks In Progress'");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/14/21 TESTCASE No:RPMXCON-48849
	 * @Description:Verify that user can download only Produced DAT file by
	 *                     selecting 'Download DAT file'
	 */
	@Test(description = "RPMXCON-48849", enabled = true, groups = { " regression" })
	public void verifyDownloadDATFile() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48849- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitForElement(page.getQC_Download());
		page.getQC_Download().waitAndClick(10);
		base.waitForElement(page.getClkBtnDownloadDATFiles());
		page.getClkBtnDownloadDATFiles().waitAndClick(5);
		BaseClass base = new BaseClass(driver);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/14/21 TESTCASE No:RPMXCON-48664
	 * @Description:Run Production by selecting components like DAT,TIFF,NATIVE and
	 *                  with selection of multiple tags with audio files
	 */
	@Test(description = "RPMXCON-48664", enabled = true, groups = { " regression" })
	public void verifyAudioFilesWithMultipleBranding() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48664- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.VerifyaudioSearchThreshold("morning", "International English", "70");
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTiffSectionBranding();
		BaseClass base = new BaseClass(driver);
		base.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
		base.passedStep(
				"Production generated  by selecting components like DAT,TIFF,NATIVE and with selection of multiple tags with audio files");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49250
	 * @Description:To verify In Productions DAT, provide the TIFFPageCount for each
	 *                 document should be zero when only DAT component is selected
	 */
	@Test(description = "RPMXCON-49250", enabled = true, groups = { " regression" })
	public void verifyDocumentCountForDAT() throws Exception {

		base.stepInfo("Test case Id RPMXCON-49250- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString20Docs);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATWithMultipleDropDown();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, tagname,
				beginningBates);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49251
	 * @Description:To verify In Productions DAT, TIFFPageCount for each document
	 *                 should be displayed when production is done with any
	 *                 component
	 */
	@Test(description = "RPMXCON-49250", enabled = true, groups = { " regression" })
	public void verifyDocumentCountForDATWithOtherComponent() throws Exception {

		base.stepInfo("Test case Id RPMXCON-49250- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString20Docs);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATWithMultipleDropDown();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, tagname,
				beginningBates);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49106
	 * @Description:To verify the count 'Total Documents: ' on Production Summary
	 *                 page
	 */
	@Test(description = "RPMXCON-49106", enabled = true, groups = { " regression" })
	public void verifyTotalPagesOnSummary() throws Exception {

		base.stepInfo("Test case Id RPMXCON-49106- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTiffSectionBranding();
		BaseClass base = new BaseClass(driver);
		base.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		String expected = page.getValueTotalDocuments().getText();
		base.passedStep("Total Documents:" + expected);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49107
	 * @Description:To verify that Total Pages count on Production-Summary page
	 */
	@Test(description = "RPMXCON-49107", enabled = true, groups = { " regression" })
	public void verifyTotalPagesCountOnSummary() throws Exception {

		base.stepInfo("Test case Id RPMXCON-49107- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTiffSectionBranding();
		BaseClass base = new BaseClass(driver);
		base.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		String expected = page.getValueTotalPagesCount().getText();
		base.passedStep("Total Page count is:" + expected);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/26/21 TESTCASE No:RPMXCON-48505
	 * @Description:Verify that Tiff should generate with Burned Redaction if Only
	 *                     Burn Redaction is enabled
	 */
	@Test(description = "RPMXCON-48505", enabled = true, groups = { " regression" })
	public void verifyTiffWithBurnRedaction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48505- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tag = "Default Redaction Tag";

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction(tag);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:12/26/21 TESTCASE No:RPMXCON-47791
	 * @Description:To Verify appropriate display of data is occurring in 'Summary &
	 *                 Preview' tab.
	 */
	@Test(description = "RPMXCON-47791", enabled = true, groups = { " regression" })
	public void verifySummaryAndPreview() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47791- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
//		String tag = "Default Redaction Tag";
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTiffSectionBranding();
		BaseClass base = new BaseClass(driver);
		base.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		String totalDocumentsCount = page.getValueTotalDocuments().getText();
		base.passedStep("Total Documents:" + totalDocumentsCount);

		String totalPagesCount = page.getValueTotalPagesCount().getText();
		base.passedStep("Total Page count is:" + totalPagesCount);

		String totalCustodians = page.getValueNumberOfCustodians().getText();
		base.passedStep("Number of custodians:" + totalCustodians);

		String FirstAndLastDocuments = page.getValueFirstLastDocs().getText();
		base.passedStep("First And Last Documents:" + FirstAndLastDocuments);

		String redactedDocs = page.getValueRedactedDocs().getText();
		base.passedStep("Count Of Redacted Documents:" + redactedDocs);

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/27/21 TESTCASE No:RPMXCON-48502
	 * @Description:To verify that if annotation layer option is selected in PDF
	 *                 section and document is redacted then selected Metadata
	 *                 should not be displayed on DAT
	 */
	@Test(description = "RPMXCON-48502", enabled = true, groups = { " regression" })
	public void verifyPDFWithAnnotationLayer() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48502- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		base.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Searching for a content and performing bulk folder action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Navigating to Production Home page");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getDATRedactionsCBox());
		page.getDATRedactionsCBox().waitAndClick(10);
		page.fillingNativeSection();
		page.fillingPDFWithRedactedDocumentsInAnnotationLayer();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		base.stepInfo("Deleting the tags and folders after the production gets completed");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/27/21 TESTCASE No:RPMXCON-48503
	 * @Description:To verify that if annotation layer option is selected in MP3
	 *                 section and Audio is redacted then selected Metadata should
	 *                 not be displayed on DATF
	 */
	@Test(description = "RPMXCON-48503", enabled = true, groups = { " regression" })
	public void verifyMP3WithAnnotationLayer() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48503- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		base.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Searching for a content and performing bulk folder action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Navigating to Production Home page");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getDATRedactionsCBox());
		page.getDATRedactionsCBox().waitAndClick(10);
		page.fillingNativeSection();
		page.fillingMP3();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		base.stepInfo("Deleting the tags and folders after the production gets completed");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/30/21 TESTCASE No:RPMXCON-48322
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 TIFF components.
	 */
	@Test(description = "RPMXCON-48322", enabled = true, groups = { " regression" })
	public void verifyGenerateLoadFileForTIFF() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48322- Production Sprint 09");
		UtilityLog.info(Input.prodPath);
		boolean flag;

		base.stepInfo("Navigating to Production Home page and creating new production set");
		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.getTIFFChkBox().waitAndClick(5);
		page.getTIFFTab().waitAndClick(5);
		page.getRadioButton_GenerateTIFF().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		page.getAdvancedTabInTIFF().waitAndClick(5);
		flag = page.getGenerateLoadFile_TIFF().GetAttribute("value").contains("true");

		if (!flag) {

			Assert.assertTrue(false);
			base.failedStep("Generate Load File is not enabled by default  for TIFF components.");
		} else {
			Assert.assertTrue(true);
			base.passedStep("Generate Load File is  enabled by default  for TIFF components.");
		}
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:12/30/21 TESTCASE No:RPMXCON-48323
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 PDF components.
	 */
	@Test(description = "RPMXCON-48323", enabled = true, groups = { " regression" })
	public void verifyGenerateLoadFileForPDF() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48323- Production Sprint 09");
		UtilityLog.info(Input.prodPath);
		boolean flag;

		base.stepInfo("Navigating to Production Home page and creating new production set");
		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.getTIFFChkBox().waitAndClick(5);
		page.getTIFFTab().waitAndClick(5);
		page.getPDFGenerateRadioButton().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		page.getAdvancedTabInTIFF().waitAndClick(5);
		flag = page.getGenerateLoadFile_TIFF().GetAttribute("value").contains("true");

		if (!flag) {

			Assert.assertTrue(false);
			base.failedStep("Generate Load File is not enabled by default  for PDF components.");
		} else {
			Assert.assertTrue(true);
			base.passedStep("Generate Load File is  enabled by default  for PDF components.");
		}
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47821
	 * @Description:To verify Production Generation for NATIVE/PDF/TIFF/Text
	 */

	@Test(description = "RPMXCON-47821", enabled = true, groups = { "regression" })
	public void verifyProductionWithPriviledgedDocuments() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47821- Production Sprint 09");
		base.stepInfo("To verify Production Generation for NATIVE/PDF/TIFF/Text");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString5);
		sessionSearch.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);
		docPage.Selectpagelength("50");
		docPage.documentSelection(17);
		driver.scrollPageToTop();
		docPage.bulkTagExistingFromDoclist(tagname);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		base.passedStep("verified Production Generation for NATIVE/PDF/TIFF/Text");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/03/22 TESTCASE No:RPMXCON-48532
	 * @Description:To verify that confirmation message is displays if Blank Page
	 *                 Removal option is enable.
	 */
	@Test(description = "RPMXCON-48532", enabled = true, groups = { " regression" })
	public void verifyConfirmationMessageWithBlankPageEnabled() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48532- Production Sprint 09");
		base.stepInfo("To verify that confirmation message is displays if Blank Page Removal option is enable.");
		UtilityLog.info(Input.prodPath);
		String expected = "Enabling Blank Page Removal doubles the overall production time. Are you sure you want to continue?";

		base.stepInfo("Navigating to Production Home page and creating new production set");
		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.getTIFFChkBox().waitAndClick(5);
		page.getTIFFTab().waitAndClick(5);
		driver.scrollPageToTop();
		page.getBlankPageRemovalToggle().waitAndClick(5);
		String actual = page.getBlankPageRemovalToggleConfirmationMessage().getText();
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		softAssertion.assertAll();
		base.passedStep("verified that confirmation message is displays if Blank Page Removal option is enabled");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:01/03/22 TESTCASE No:RPMXCON-48531
	 * @Description:To verify that if Blank Page Removal toggle is ON then it should
	 *                 produced Tiff without blank pages
	 */
	@Test(description = "RPMXCON-48531", enabled = true, groups = { " regression" })
	public void generateProductionWithBlankPageEnabled() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48531- Production Sprint 09");
		base.stepInfo(
				"To verify that if Blank Page Removal toggle is ON then it should produced Tiff without blank pages");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("ID00001312");
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		driver.scrollPageToTop();
		page.getBlankPageRemovalToggle().waitAndClick(5);
		page.continueButtonInBlankPageRemovalToggle().waitAndClick(5);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-58562
	 * @Description:Verify the name of load files should be used the name of the
	 *                     Export
	 */
	@Test(description = "RPMXCON-58562", enabled = true, groups = { " regression" })
	public void verifySameNameForLoadFilesAndExportName() throws Exception {

		base.stepInfo("Test case Id RPMXCON-58562- Production Sprint 10");
		base.stepInfo("Verify the name of load files should be used the name of the Export");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create export with TIFF
		exportname = "E" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectExportSetFromDropDown();
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		base.passedStep("Verified the name of load files should be used the name of the  Export");

		// To delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-55927
	 * @Description:Verify 'Placeholders' section in Tiff/PDF components
	 */
	@Test(description = "RPMXCON-55927", enabled = true, groups = { " regression" })
	public void verifyPlaceholdersInTIFF() throws Exception {

		base.stepInfo("Test case Id RPMXCON-55927- Production Sprint 10");
		base.stepInfo("Verify 'Placeholders' section in Tiff/PDF components");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getPriveldge_TextArea());
		base.waitForElement(page.getPriveldge_TextArea());

		if (page.getPriveldge_TextArea().isDisplayed()) {
			base.passedStep("Verified 'Placeholders' section in Tiff/PDF components");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-55925
	 * @Description:Verify Native section in Production Components section
	 */
	@Test(description = "RPMXCON-55925", enabled = true, groups = { " regression" })
	public void verifyNativeSectionComponent() throws Exception {

		base.stepInfo("Test case Id RPMXCON-55925- Production Sprint 10");
		base.stepInfo("Verify Native section in Production Components section");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingNativeSection();
		if (page.nativeSectionBlueText().isDisplayed()) {
			base.passedStep("Verified Native section in Production Components section");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56008
	 * @Description:Verify that user can download the production by using the
	 *                     Shareable link for 'DAT Only'
	 */
	@Test(description = "RPMXCON-56008", enabled = true, groups = { " regression" })
	public void verifySharableLinkForDAT() throws Exception {
		base.stepInfo("Test case Id RPMXCON-56008- Production Sprint 10");
		base.stepInfo("Verify that user can download the production by using the Shareable link for 'DAT Only'");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigateToProductionPage();
		driver.Navigate().refresh();

		page.prodGenerationInProgressStatus(productionname);
		page.navigateToProductionPage();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.getQC_Download().waitAndClick(10);
		page.getClkBtnDownloadDATFiles().waitAndClick(10);
		base.waitTime(3);
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String downloadsHome = "C:\\BatchPrintFiles\\downloads";
		String home = System.getProperty("user.home");
		page.isFileDownloaded(downloadsHome, name);
		base.passedStep("Verified that user can download the production by using the Shareable link for 'DAT Only'");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56015
	 * @Description:Verify that on if user paste the sharable link and gives the
	 *                     correct password then it should download the zip file
	 */
	@Test(description = "RPMXCON-56015", enabled = true, groups = { " regression" })
	public void verifySharableLinkByCorrectPassword() throws Exception {
		base.stepInfo("Test case Id RPMXCON-56015- Production Sprint 10");
		base.stepInfo(
				"Verify that on if user paste the sharable link and gives the correct password then it should download the zip file");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigateToProductionPage();
		driver.Navigate().refresh();

		page.prodGenerationInProgressStatus(productionname);
		page.navigateToProductionPage();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.verifyDownloadProductionUsingSharableLink();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56014
	 * @Description:Verify that error should displays if user paste the shareable
	 *                     link with incorrect password
	 */
	@Test(description = "RPMXCON-56014", enabled = true, groups = { " regression" })
	public void verifyErrorInSharableLinkByIncorrectPassword() throws Exception {
		base.stepInfo("Test case Id RPMXCON-56014- Production Sprint 10");
		base.stepInfo("Verify that error should displays if user paste the shareable link with incorrect password");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.prodGenerationInProgressStatus(productionname);
		page.navigateToProductionPage();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.verifyDownloadProductionUsingSharableLinkAndCheckErrorMessage();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/12/22 TESTCASE No:RPMXCON-56013
	 * @Description:Verify that error should be displays if user enters invalid URL
	 */
	@Test(description = "RPMXCON-56013", enabled = true, groups = { " regression" })
	public void verifyErrorInSharableLinkWithInvalidURL() throws Exception {

		base.stepInfo("Test case Id RPMXCON-56013- Production Sprint 10");
		base.stepInfo("Verify that error should displays if user paste the shareable link with incorrect password");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.prodGenerationInProgressStatus(productionname);
		page.navigateToProductionPage();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.verifyDownloadProductionUsingInvalidLink();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/12/22 TESTCASE No:RPMXCON-48326
	 * @Description:To verify that production should generate successfully if user
	 *                 disabled the 'Generate Load File'
	 */
	@Test(description = "RPMXCON-48326", enabled = true, groups = { " regression" })
	public void verifyToggleOffForGenerateLoadFile() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48326- Production Sprint 10");
		base.stepInfo(
				"To verify that production should generate successfully if user disabled the 'Generate Load File'");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.getAdvancedTabInTIFF().waitAndClick(10);
		page.getGenerateLoadFile().waitAndClick(10);
		page.fillingTextSection();
		driver.scrollingToBottomofAPage();
		page.getAdvancedTabInText().waitAndClick(10);
		page.generateLoadFileToggleInTextComponent().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedProductionComponent());
		driver.waitForPageToBeReady();
		page.fillingMP3();
		driver.scrollingToBottomofAPage();
		page.getAdvancedTabInMP3().waitAndClick(10);
		base.clickButton(page.getMp3GenerateLoadFile());
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/13/22 TESTCASE No:RPMXCON-48186
	 * @Description:To Verify Generate Section for Production Name and Status.
	 */
	@Test(description = "RPMXCON-48186", enabled = true, groups = { " regression" })
	public void verifyProductionNameAndStatus() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48186- Production Sprint 10");
		base.stepInfo("To Verify Generate Section for Production Name and Status.");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		String actual = page.productionNameInGeneratePage(productionname).getText();
		String expected = productionname;
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		softAssertion.assertAll();

		if (page.getStatusDraftTxt().isDisplayed()) {
			String draftStatus = page.getStatusDraftTxt().getText();
			base.passedStep("status enabled as:" + draftStatus);
		}

		if (page.getbtnProductionGenerate().isDisplayed()) {
			base.passedStep("Generate Button is enabled");
		}

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/13/22 TESTCASE No:RPMXCON-48145
	 * @Description:To Verify Redaction Check box along with Privilege Check box, In
	 *                 Generated DAT of Production.
	 */
	@Test(description = "RPMXCON-48145", enabled = true, groups = { " regression" })
	public void verifyCheckboxInDAT() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48145- Production Sprint 10");
		base.stepInfo("To Verify Redaction Check box along with Privilege Check box, In Generated DAT of Production.");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getRedactDATCheckBox().waitAndClick(10);
		base.stepInfo("Redaction checbox is selected in DAT component");
		page.getDATPrivledgedCheckbox().waitAndClick(10);
		base.stepInfo("Privileged checbox is selected in DAT component");
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/13/22 TESTCASE No:RPMXCON-47822
	 * @Description:To verify Redacted Document count should get displayed on
	 *                 Summary & Preview tab
	 */
	@Test(description = "RPMXCON-47822", enabled = true, groups = { " regression" })
	public void verifyRedactedCountOnSummaryTab() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47822- Production Sprint 10");
		base.stepInfo("To verify Redacted Document count should get displayed on Summary & Preview tab");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String zero = "0";
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedactionAndSelectingOneTag();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();
		String redactedCount = page.redactedDocumentsInSummaryPage().getText();
		System.out.println("Redacted count :" + redactedCount);
		if (redactedCount != zero) {
			base.passedStep("Recated count is not zero as per the pre requisite");
		}

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/13/22 TESTCASE No:RPMXCON-47823
	 * @Description:To verify Bates Number Generated in Production can be start with
	 *                 {0}.
	 */
	@Test(description = "RPMXCON-47823", enabled = true, groups = { " regression" })
	public void verifyBatesNumberAsZero() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47823- Production Sprint 10");
		base.stepInfo("To verify Bates Number Generated in Production can be start with {0}.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = "0";
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedactionAndSelectingOneTag();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/28/21 TESTCASE No:RPMXCON-47844
	 * @Description:To Verify the Create/Display of Template with newly created
	 *                 Project and Production Set.
	 */
	@Test(description = "RPMXCON-47844", enabled = true, groups = { " regression" })
	public void createTemplateWithNewProductionSet() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47844- Production Sprint 10");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		templateName = "templateName" + Utility.dynamicNameAppender();
		productionSet = "productionSet" + Utility.dynamicNameAppender();

		base.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Searching for a content and performing bulk folder action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Navigating to Production Home page and creating new production set");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.CreateProductionSets(productionSet);
		page.navigateToProductionPageByNewProductionSet(productionSet);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.navigateToProductionPageByNewProductionSet(productionSet);
		page.saveProductionAsTemplateAndVerifyInManageTemplateTab(productionname, templateName);

		base.stepInfo("Deleting the tags and folders after the production gets completed");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/18/22 TESTCASE No:RPMXCON-47845
	 * @Description:To Verify the View of the Custom Template
	 */
	@Test(description = "RPMXCON-47845", enabled = true, groups = { " regression" })
	public void verifyCustomTemplate() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47845- Production Sprint 10");
		base.stepInfo("To Verify the View of the Custom Template");
		UtilityLog.info(Input.prodPath);

		templateName = "templateName" + Utility.dynamicNameAppender();
		productionSet = "productionSet" + Utility.dynamicNameAppender();

		base.stepInfo("Navigating to Production Home page and creating new production set");
		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.CreateProductionSets(productionSet);
		page.navigateToProductionPageByNewProductionSet(productionSet);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		driver.waitForPageToBeReady();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.navigateToProductionPageByNewProductionSet(productionSet);
		driver.waitForPageToBeReady();
		page.getProductionFromHomepage(productionname).isDisplayed();
		page.saveProductionAsTemplateAndVerifyInManageTemplateTab(productionname, templateName);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:01/19/22 TESTCASE No:RPMXCON-55696
	 * @Description:To Verify Sorting configured in the production is being honored
	 *                 by the generated production
	 */
	@Test(description = "RPMXCON-55696", enabled = true, groups = { " regression" })
	public void verifySortingInProduction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-55696- Production Sprint 10");
		base.stepInfo("To Verify Sorting configured in the production is being honored by the generated production");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedactionAndSelectingOneTag();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		base.stepInfo("Sorting configured in the production");
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/19/22 TESTCASE No:RPMXCON-55696
	 * @Description:To Verify User will be able to enter production components
	 *                 information on the self production wizard
	 */
	@Test(description = "RPMXCON-55696", enabled = true, groups = { " regression" })
	public void verifyProductionComponents() throws Exception {

		base.stepInfo("Test case Id RPMXCON-55696- Production Sprint 10");
		base.stepInfo(
				"To Verify User will be able to enter production components information on the self production wizard");
		UtilityLog.info(Input.prodPath);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithDisablePrivilegdedDocs();
		page.fillingTextSection();
		page.getComponentsMarkComplete().waitAndClick(10);
		base = new BaseClass(driver);
		base.VerifySuccessMessage("Mark Complete successful");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/20/22 TESTCASE No:RPMXCON-47925
	 * @Description:To Verify in Natives section of Productions, Product Native
	 *                 Files for select file types are to being honored by the
	 *                 production
	 */
	@Test(description = "RPMXCON-47925", enabled = true, groups = { " regression" })
	public void verifyNativesForSelectedFiles() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47925- Production Sprint 11");
		base.stepInfo(
				"To Verify in Natives section of Productions, Product Native Files for select file types are to being honored by the production");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getNativeChkBox().waitAndClick(10);
		page.getNativeTab().waitAndClick(10);
		page.getChkBoxNative_SpreadSheets().waitAndClick(10);
		page.getChkBoxNative_WordTextFiles().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		base.stepInfo("Sorting configured in the production");
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/20/22 TESTCASE No:RPMXCON-47927
	 * @Description:To Verify ProductionBatesRange in production slip sheet.
	 */
	@Test(description = "RPMXCON-47927", enabled = true, groups = { " regression" })
	public void verifySlipSheetInProduction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47927- Production Sprint 11");
		base.stepInfo("To Verify ProductionBatesRange in production slip sheet.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		driver.waitForPageToBeReady();
		page.slipSheetToggleEnable();
		driver.scrollingToBottomofAPage();
		page.availableFieldSelection("ProductionBatesRange");
		base.stepInfo("Slip Sheet is enabled and ProductionBatesRange checkbox is selected");
		driver.waitForPageToBeReady();
		page.getAddSelected().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		base.stepInfo("Sorting configured in the production");
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/21/22 TESTCASE No:RPMXCON-48269
	 * @Description:To Verify In Productions, the produced DAT should have DAT filed
	 *                 name configured in DAT component.
	 */
	@Test(description = "RPMXCON-48269", enabled = true, groups = { " regression" })
	public void verifyDATFieldName() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48269- Production Sprint 11");
		base.stepInfo(
				"To Verify In Productions, the produced DAT should have DAT filed name configured in DAT component.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		batesNumber = "B" + Utility.dynamicNameAppender();
		String datField = "BN";
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, datField);
		base.stepInfo("BN is passed in DAT field");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		base.stepInfo("Sorting configured in the production");
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/21/22 TESTCASE No:RPMXCON-48144
	 * @Description:To Verify Redaction check box under DAT Section in Production
	 *                 Component Section.
	 */
	@Test(description = "RPMXCON-48144", enabled = true, groups = { " regression" })
	public void verifyRedactionCheckboxInDAT() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48144- Production Sprint 11");
		base.stepInfo("To Verify Redaction check box under DAT Section in Production Component Section.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDATRedactionsCBox().waitAndClick(10);
		base.stepInfo("Redaction checkbox is selected in DAT components");
		page.fillingTIFFSectionwithBurnRedaction(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		base.stepInfo("Sorting configured in the production");
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/25/22 TESTCASE No:RPMXCON-47902
	 * @Description:To Verify Admin will be able to overwrite an existing production
	 *                 with new production documents and with new bates numbers, if
	 *                 the production is not flagged as Locked
	 */
	@Test(description = "RPMXCON-47902", enabled = true, groups = { " regression" })
	public void verifyOverwriteDocumentFromProduction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47902- Production Sprint 11");
		base.stepInfo(
				"To Verify Admin will be able to overwrite an existing production with new production documents and with new bates numbers, if the production is not flagged as Locked");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		driver.waitForPageToBeReady();
		// Go To Production Home Page
		page.navigateToProductionPage();
		driver.Navigate().refresh();

		page.prodGenerationInProgressStatus(productionname);
		driver.waitForPageToBeReady();
		page.navigateToProductionPage();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		base.stepInfo("In progress productions are filtered and In progress production from homepage is selected");
		page.getQC_backbutton().waitAndClick(10);
		for (int i = 0; i < 4; i++) {
			page.getBckBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		page.getMarkIncompleteButton().waitAndClick(10);
		page.fillingSelectDocumentUsingTags(tagname);
		base.stepInfo("Documents overwritted by selecting from tags");
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("Production generated successfully by overwritting documents");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/25/22 TESTCASE No:RPMXCON-48572
	 * @Description:To verify that the pre-gen checks continue to show through out
	 *                 the next steps of the production.
	 */
	@Test(description = "RPMXCON-48572", enabled = true, groups = { " regression" })
	public void verifyBatesRangeAfterProduction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48572- Production Sprint 11");
		base.stepInfo(
				"To verify that the pre-gen checks continue to show through out the next steps of the production.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		driver.waitForPageToBeReady();
		page.getProd_BatesRange().isDisplayed();
		base.passedStep("Bates Range is displayed in generate page");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/27/22 TESTCASE No:RPMXCON-47897
	 * @Description:To Verify Document Selection Section on the self production
	 *                 wizard For Folder
	 */
	@Test(description = "RPMXCON-47897", enabled = true, groups = { " regression" })
	public void verifyDocumentSelectionWithFolder() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47897- Production Sprint 11");
		base.stepInfo("To Verify Document Selection Section on the self production wizard For Folder");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);

		if (page.getDocumentSelectionLink().isDisplayed()) {
			base.passedStep("Total Documents selected is displayed correctly on the self production wizard For Folder");
		}

		else {
			base.failedMessage("Total Documents selected is not displayed on the self production wizard For Folder");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/27/22 TESTCASE No:RPMXCON-47892
	 * @Description:To Verify numbering and sorting Section on the self production
	 *                 wizard for Numbering
	 */
	@Test(description = "RPMXCON-47892", enabled = true, groups = { " regression" })
	public void verifyNumberingAndSorting() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47892- Production Sprint 11");
		base.stepInfo("To Verify numbering and sorting Section on the self production wizard for Numbering");
		UtilityLog.info(Input.prodPath);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();

		if (page.pageRadioButton().Selected()) {
			base.passedStep("Page Radio button is selected by default");
		} else {
			base.failedMessage("Page Radio button is not selected by default");
		}
		page.getNumbering_Document_RadioButton().waitAndClick(10);

		if (page.getBeginningSubBatesNumber().GetAttribute("value").contains("1")) {
			base.passedStep("Beginning bates number is 1 by default");
		} else {
			base.failedMessage("Beginning bates number is not 1 by default");
		}
		if (page.getBeginningSubBatesNumber().GetAttribute("value").contains("5")) {
			base.passedStep("Minimum length number is 5 by default");
		} else {
			base.failedMessage("Minimum length number is not 5 by default");
		}

		page.pageRadioButton().ScrollTo();
		page.pageRadioButton().waitAndClick(10);

		if (page.specifyBatesNumbering().Selected()) {
			base.passedStep("specify bates numbering is selected by default");
		} else {
			base.failedMessage("specify bates numbering is not selected  by default");
		}

		if (page.beginningBatesInFormat().GetAttribute("value").contains("0")) {
			base.passedStep("Beginning bates is 0 by default");
		} else {
			base.failedMessage("Beginning bates is not 0 by default");
		}

		page.getNumbering_Document_RadioButton().ScrollTo();
		page.getNumbering_Document_RadioButton().waitAndClick(10);
		page.useMetadataFied().ScrollTo();
		page.useMetadataFied().waitAndClick(10);

		if (page.getExportPrefixId().isDisplayed() && page.getExportSuffixId().isDisplayed()) {
			base.passedStep("Prefix and suffix is displayed in user metadata field");
		}

		else {
			base.failedMessage("Prefix and suffix is not displayed in user metadata field");
		}
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Tiff/PDF should generate with Priv
	 *              placeholdering even though File group/tag based placeholdering
	 *              is exists. [RPMXCON-48506]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48506", enabled = true, groups = { "regression" })
	public void verifyTIffOrPdfWithPrivPlaceholder() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagname = "TAG" + Utility.dynamicNameAppender();
		String folder = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Test case Id: RPMXCON-48506 Production Sprint 11");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Priv placeholdering even though File group/tag based placeholdering is exists.");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(folder, Input.securityGroup);

		// search for folder
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(folder);

		// create production with DAT,Native,PDF& ingested Text
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingNativeDocsPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(folder);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.waitTime(4);
		String name = page.getProduction().getText().trim();
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(folder);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/28/22 TESTCASE No:RPMXCON-49362
	 * @Description:Verify if PA Select the Production using a template that has
	 *                     only Native Files selected in the native components, then
	 *                     Component tab should Complete without any error.
	 */
	@Test(description = "RPMXCON-49362", enabled = true, groups = { " regression" })
	public void verifyComponentTabWithoutAnyError() throws Exception {

		base.stepInfo("Test case Id RPMXCON-49362- Production Sprint 11");
		base.stepInfo(
				"Verify if PA Select the Production using a template that has only Native Files selected in the native components, then Component tab should Complete without any error.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		// Go To Production Home Page
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.prodGenerationInProgressStatus(productionname);
		driver.waitForPageToBeReady();
		page.navigateToProductionPage();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		base.stepInfo("completed productions are filtered and In progress production from homepage is selected");
		page.getQC_backbutton().waitAndClick(10);
		for (int i = 0; i < 6; i++) {
			page.getBckBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		page.getMarkIncompleteButton().waitAndClick(10);
		if (page.getNativeTab().Selected()) {
			base.passedStep("Native section is selected by default");
		}
		page.getComponentsMarkComplete().waitAndClick(10);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that Tiff/PDF should generate with File/Tag-based
	 *              placeholdering if Only File/Tag-based Placeholdering is exists
	 *              [RPMXCON-48507]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48507", enabled = true, groups = { "regression" })
	public void verifyTIFFAndGenerate() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagname = "TAG" + Utility.dynamicNameAppender();
		String folder = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Test case Id: RPMXCON-48507 Production Sprint 11");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with File/Tag-based placeholdering if Only File/Tag-based Placeholdering is exists");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(folder, Input.securityGroup);

		// search for folder
		sessionSearch.basicContentSearch("yellow");
		sessionSearch.bulkFolderExisting(folder);

		// create production with DAT,Native,tiff with native placeholder
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.burnRedactionWithRedactionTagInTiffSection(Input.defaultRedactionTag);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(folder);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
		base.waitTime(2);
		page.extractFile();

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:01/28/22 TESTCASE No:RPMXCON-48334
	 * @Description:To verify that the selected metadata is not displayed in DAT if
	 *                 the doc has at least one of the selected Redaction tags for
	 *                 Audio files
	 */
	@Test(description = "RPMXCON-48334", enabled = true, groups = { " regression" })
	public void verifyDATWithRedactionsCheckboxForAudioFiles() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48334- Production Sprint 11");
		base.stepInfo("To Verify in Priv Guard View in Doclist and DocView.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDATRedactionsCBox().waitAndClick(10);
		base.stepInfo("Redaction checkbox is selected in DAT component");
		page.advancedProductionComponentsMP3WithBurnReductionTag();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.waitTime(4);
		String name = page.getProduction().getText().trim();
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Sowndarya.Velraj created on:02/02/22 TESTCASE No:RPMXCON-47904
	 * @Description:To verify status on the tile of a production on the landing page
	 *                 should show correct production status
	 */
	@Test(description = "RPMXCON-47904", enabled = true, groups = { " regression" })
	public void verifyProductionStatusInTileView() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47904- Production Sprint 11");
		base.stepInfo(
				"To verify status on the tile of a production on the landing page should show correct production status");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		page.advancedProductionComponentsMP3WithBurnReductionTag();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		driver.waitForPageToBeReady();
		page.navigateToProductionPage();

		base.stepInfo("Checking availability of In progress status production ");
		if (page.productionNameInHomePage().Displayed()) {
			base.passedStep("production in In Progress Status Displayed");
		} else {
			base.failedStep("production in In Progress Status not Displayed");
		}

		driver.Navigate().refresh();

		base.stepInfo("Checking availability of Completed status production ");
		if (page.productionNameInHomePage().Displayed()) {
			base.passedStep("production in Completed Status Displayed");
		} else {
			base.failedStep("production in Completed Status not Displayed");
		}

		driver.Navigate().refresh();

		base.stepInfo("Checking availability of Failed status production ");
		page.gettxtProdGenerationFailed();
		if (page.productionNameInHomePage().Displayed()) {
			base.passedStep("production in failed Status Displayed");
		} else {
			base.failedStep("production in failed Status not Displayed");
		}
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-47979
	 * @Description:To Verify OCRing of Text component of the production, only for
	 *                 Redacted Documents when "OCR non-redacted docs... " option is
	 *                 selected in Production-text component
	 */
	@Test(description = "RPMXCON-47979", enabled = true, groups = { " regression" })
	public void verifyTextFileComponentInGeneratedProduction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47979- Production ");
		base.stepInfo(
				"To Verify OCRing of Text component of the production, only for Redacted Documents when \"OCR non-redacted docs... \" option is selected in Production-text component");
		UtilityLog.info(Input.prodPath);

		tagname = "Tag" + Utility.dynamicNameAppender();
		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(Input.securityGroup);
		base.waitTime(2);
		BaseClass base = new BaseClass(driver);
		base.impersonatePAtoRMU();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage doc = new DocViewPage(driver);
		doc.getDocView_MiniDoc_Selectdoc(4).waitAndClick(5);
		doc.getDocView_MiniDoc_Selectdoc(5).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.datMetaDataTiffPageCount();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.textComponentVerification();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.getIncludeFamilies().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.extractFile();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		File TiffFile = new File(home + "/Downloads/" + "VOL0001/Load Files/" + name + "_TIFF.OPT");
		File TextFile = new File(home + "/Downloads/VOL0001/Text/0001");
		if (TextFile.exists()) {
			base.passedStep("Text file generated successfully");
		} else {
			base.failedStep("Text document is not generated successfully");
		}

		if (TiffFile.exists()) {
			base.passedStep("Tiff is generated successfully");
		} else {
			base.failedStep("Tiff is not generated successfully");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is displayed as expected");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	///

	/**
	 * @author Aathith Test case id-RPMXCON-49334
	 * @Description To verify that Production should generate successfully with ICE
	 *              data
	 * 
	 */
	@Test(description = "RPMXCON-49334", enabled = true, groups = { "regression" })
	public void verifyIceDataGenerateSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49334 -Production Component");
		base.stepInfo("To verify that Production should generate successfully with ICE data");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int doccount = 3;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(doccount);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();

		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(home + "/Downloads/VOL0001/Natives");

		if (Native.exists()) {
			base.passedStep("Native folder exist for generated IcE data");
		} else {
			base.failedStep("filetype is not displayed as expected");
		}

		base.passedStep("verified that Production should generate successfully with ICE data");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49373
	 * @Description To verify that all produced Natives files should be provided by
	 *              file types for ICE processed data.
	 * 
	 */
	@Test(description = "RPMXCON-49373", enabled = true, groups = { "regression" })
	public void verifyIceDatafilesGenerateSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49373 -Production Component");
		base.stepInfo(
				"To verify that all produced Natives files should be provided by file types for ICE processed data.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int doccount = 3;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(doccount);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int number = Integer.parseInt(beginningBates);
		int lastfile = number + doccount;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		page.extractFile();
		driver.waitForPageToBeReady();

		for (int i = number; i < lastfile; i++) {
			File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + i + suffixID + ".docx");
			File Textfile = new File(home + "/Downloads/VOL0001/Text/0001/" + prefixID + i + suffixID + ".txt");
			File TiffFile = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + i + suffixID + ".tiff");
			if (Native.exists()) {
				base.passedStep("Native file are generated correctly");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
			if (Textfile.exists()) {
				base.passedStep("Text file are generated correctly");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
			if (TiffFile.exists()) {
				base.passedStep("Tiff file are generated coreectly");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
		}
		base.passedStep(
				"verifid that all produced Natives files should be provided by file types for ICE processed data.");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49337
	 * @Description: To verify that document should produced with 'Tech Issues Docs'
	 *               placeholdering by selecting more than one Tag
	 */
	@Test(description = "RPMXCON-49337", enabled = true, groups = { "regression" })
	public void verifyTeccIssueDocPlaceholdering() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-49337 ");
		base.stepInfo(
				"To verify that document should produced with 'Tech Issues Docs' placeholdering by selecting more than one Tag");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.technicalIssue);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastFile = docno + firstFile;
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagname, tagname1, Input.technicalIssue);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.technicalIssue);

		base.passedStep(
				"Verified that document should produced with 'Tech Issues Docs' placeholdering by selecting more than one Tag");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-49867
	 * @Description Verify that Production should be generated successfully if PDF
	 *              documents are ICE processed with Mapped set
	 * @Hint : test case run on the project Regression_AllDataset_Consilio1 in UAT
	 *       environment
	 */
	@Test(description = "RPMXCON-49867", enabled = true, groups = { "regression" })
	public void verifyPdfIceMappedSetProdGenSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49867 -Production Component");
		base.stepInfo(
				"Verify that Production should be generated successfully if PDF documents are  ICE processed with Mapped set");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName(Input.pdfDataSet);
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.passedStep(
				"Verified that Production should be generated successfully if PDF documents are  ICE processed with Mapped set");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49046
	 * @Description Verify in Productions, when there is no document with multiple
	 *              tags it should provide the message that there is 0 "Documents
	 *              with Multiple Branding Tags"
	 * 
	 */
	@Test(description = "RPMXCON-49046", enabled = true, groups = { "regression" })
	public void verifyMultipleDocumentCountisZero() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49046 -Production Component");
		base.stepInfo(
				"Verify in Productions, when there is no document with multiple tags it should provide the message that there is 0 \"Documents with Multiple Branding Tags\"");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String text = Input.telecaSearchString;
		String value = "0";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(text);
		base.stepInfo("total number of document : " + docno);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelectionIncludeChildDoc(5);
		doc.bulkTagExistingFromDoclist(tagname);

		doc.documentSelectionIncludeChildDoc(5);
		doc.documentSelectionIncludeChildDoc(10);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.specifyBrandingInTiffSection(tagname, tagname1, text);

		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");

		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGeneration().isElementAvailable(30);
		page.getDocumentWithMultipleBrandingTagsOnGenerationPage().isElementAvailable(10);
		String count = page.getDocumentWithMultipleBrandingTagsOnGenerationPage().getText().trim();

		if (value.equals(count)) {
			base.passedStep("Multiple branding tags, here it should displayed 0");
			System.out.println("pass");
		} else {
			base.failedStep("verificatioin failed");
			System.out.println("failed");
		}

		base.passedStep(
				"Verified in Productions, when there is no document with multiple tags it should provide the message that there is 0 \"Documents with Multiple Branding Tags\"");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48034
	 * @Description: To Verify "Enable Placeholders by Selecting File Types" for
	 *               (.mdb/.mdf) under TIFF /PDF Should works for Production.
	 */
	@Test(description = "RPMXCON-48034", enabled = true, groups = { "regression" })
	public void verifyPlaceholderForMDB() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48034 ");
		base.stepInfo(
				"To Verify \"Enable Placeholders by Selecting File Types\" for  (.mdb/.mdf) under TIFF /PDF Should works for Production.");

		// foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String testing = Input.comment;

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		// tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.MetaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.sourceDocIdDB992);
		// sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastFile = docno + firstFile;
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithNativelyProducedDocs(Input.dbFile, testing);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, testing);

		base.passedStep(
				"Verified \"Enable Placeholders by Selecting File Types\" for  (.mdb/.mdf) under TIFF /PDF Should works for Production.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security
		// Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 3/03/22 NA Modified date: NA Modified by:NA
	 * Description :To Verify if a document has both landscape and portrait pages,
	 * the rotation selected is applied only to the pages that are in landscape
	 * layout. 'RPMXCON-48031'
	 * 
	 */
	@Test(description = "RPMXCON-48031", enabled = true, groups = { "regression" })
	public void verifyDocsBothLandScapeRotationLayout() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48031 -Production Sprint 13");
		base.stepInfo(
				"To Verify if a document has both landscape and portrait pages, the rotation selected is applied only to the pages that are in landscape layout.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "35ID00000169");
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		driver.scrollPageToTop();
		page.getRotationDropDown().ScrollTo();
		page.getRotationDropDown().selectFromDropdown().selectByVisibleText("Rotate 90 degrees clock-wise");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		System.out.println("Unzipped the downloaded files");
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String firstFile = prefixID + beginningBates + suffixID;
		File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".tiff");

		BufferedImage bimg = ImageIO.read(file);
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		if (width < height) {
			base.passedStep("Rotation is applied only to the pages is landscape successfully");
		} else {
			base.failedStep("Rotation is not applied only to the pages is not landscape");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49374
	 * @Description To verify that all produced Natives files should be provided by
	 *              file types for NUIX processed data.
	 * 
	 */
	@Test(description = "RPMXCON-49374", enabled = true, groups = { "regression" })
	public void verifyNuixDatafilesGenerateSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49374 -Production Component");
		base.stepInfo(
				"To verify that all produced Natives files should be provided by file types for NUIX processed data.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int doccount = 3;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(doccount);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastfile = firstFile + doccount;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.isImageFileExist(firstFile, lastfile, prefixID, suffixID);
		page.isTextFileExist(firstFile, lastfile, prefixID, suffixID);
		page.isNativeDocxFileExist(firstFile, lastfile, prefixID, suffixID);

		base.passedStep(
				"verified that all produced Natives files should be provided by file types for NUIX processed data.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-48255
	 * @Description To Verify Placeholder for Privilege Doc at Priv Guard section on
	 *              Mark complete.
	 * 
	 */
	@Test(description = "RPMXCON-48255", enabled = true, groups = { "regression" })
	public void verifyPlaceholderPrivDocAtPrivGuard() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48255 -Production Component");
		base.stepInfo("To Verify Placeholder for Privilege Doc at Priv Guard section on Mark complete.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = "1";
		int firstFile = Integer.parseInt(beginningBates);
		int lastfile = firstFile + docno;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.getDoNotProduceFullContentTiff().ScrollTo();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		String text = Integer.toString(docno);
		page.verifyText(page.getDocumentSelectionLink(), text);

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.fillingPrivGuardPageWithPrivPlaceHolder(tagname);
		page.clickElementNthtime(page.getBackButton(), 4);
		page.getTIFFTab().waitAndClick(10);
		page.toggleOnCheck(page.getTIFF_EnableforPrivilegedDocs());
		page.visibleCheck(Input.searchString2);
		driver.scrollPageToTop();
		page.clickElementNthtime(page.getNextButton(), 4);
		driver.waitForPageToBeReady();

		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.verifyText(page.getPrivDocCountInSummaryPage(), text);
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndVerfyingBatesRangeValue(beginningBates);

		page.extractFile();
		page.isImageFileExist(firstFile, lastfile, prefixID, suffixID);

		base.passedStep("Verified Placeholder for Privilege Doc at Priv Guard section on Mark complete.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-60902
	 * @Description Verify Production should be generated successfully for the
	 *              documents with annotation
	 * 
	 */
	@Test(description = "RPMXCON-60902", enabled = true, groups = { "regression" })
	public void verifyProdGenDocumentWithAnnotation() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-60902 -Production Component");
		base.stepInfo("Verify Production should be generated successfully for the documents with annotation ");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject("AutomationAdditionalDataProject");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("PDF Annotations");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		page.pdf_Verification_In_Generated_Pdf(prefixID, suffixID, beginningBates);

		base.passedStep("Verified Production should be generated successfully for the documents with annotation ");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-56146
	 * @Description Verify that if problem docs is assigned as Priv doc then
	 *              Production should generated with Priv Placeholder for the same
	 *              document
	 * 
	 */
	@Test(description = "RPMXCON-56146", enabled = true, groups = { "regression" })
	public void verifyPrivPlaceholderGenerateSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56146 -Production Component");
		base.stepInfo(
				"Verify that if problem docs is assigned as Priv doc then Production should generated with Priv Placeholder for the same document");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject("AutomationAdditionalDataProject");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.MetaDataSearchInBasicSearch(Input.docName, "ID00002051");
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastfile = firstFile + docno;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		page.pdf_Verification_In_Generated_PlaceHolder(firstFile, lastfile, prefixID, suffixID, Input.tagNameTechnical);

		base.passedStep(
				"Verified that if problem docs is assigned as Priv doc then Production should generated with Priv Placeholder for the same document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 3/03/22 NA Modified date: NA Modified by:NA
	 * Description :To Verify Removal of Redaction Tag from a documents Should get
	 * produced in Production for Native. 'RPMXCON-48038'
	 * 
	 */
	@Test(description = "RPMXCON-48038", enabled = true, groups = { "regression" })
	public void verifyRemovalRedactionTagProductionForNative() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48038 -Production Component");
		base.stepInfo(
				"To Verify Removal of Redaction Tag from a documents Should get produced in Production for Native");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int nativefile = Integer.parseInt(beginningBates);
		int NativeDocStart = nativefile + 3;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		driver.waitForPageToBeReady();
		page.getQC_Download().isElementAvailable(160);
		page.getCopyPath().isDisplayed();
		page.getCopyPath().Click();
		page.getQC_Download().waitAndClick(10);
		page.getQC_Downloadbutton_allfiles().waitAndClick(10);
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		driver.waitForPageToBeReady();
		page.clickBackBtnandSelectingNative(7, tagname);
		driver.scrollingToBottomofAPage();
		page.getTIFF_EnableforPrivilegedDocs().isDisplayed();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();

		driver.waitForPageToBeReady();

		File Native1 = new File(
				home + "\\Downloads\\VOL0001\\Natives\\0001\\" + prefixID + NativeDocStart + suffixID + ".doc");

		if (Native1.exists()) {
			base.passedStep("Native file are generated correctly : " + prefixID + NativeDocStart + suffixID + ".doc");
			System.out.println("passeed");
		} else {
			base.failedStep("verification failed");
			System.out.println("failed");

		}

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani Modify Date: 14/03/2022 Test case id-RPMXCON-47886
	 * @Description To Verify TIFF Section with various options
	 *
	 */

	@Test(description = "RPMXCON-47886", enabled = true, groups = { "regression" })
	public void verifyTiffSectionVariosOption() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47886 -Production Component");
		base.stepInfo("To Verify TIFF Section with various options");
		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		BaseClass base = new BaseClass(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		driver.waitForPageToBeReady();
		page.getTIFFChkBox().waitAndClick(10);
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		driver.waitForPageToBeReady();

		page.visibleCheck("Page Options:");
		page.visibleCheck("Branding:");
		page.getElementDisplayCheck(page.getPrivPlaceHolder());
		base.stepInfo("Privledge placholder is displayed");
		page.getTiff_NativeDoc().waitAndClick(10);
		page.getElementDisplayCheck(page.getNativePlaceHolder());
		base.stepInfo("Native placeholder is displayed");
		page.visibleCheck("Redactions:");

		page.getTiffAdvanceBtn().ScrollTo();
		page.getTiffAdvanceBtn().waitAndClick(10);
		page.visibleCheck("Slip Sheets:");
		page.getSlipSheets().waitAndClick(10);

		page.visibleCheck("METADATA");
		page.visibleCheck("WORKPRODUCT");
		page.visibleCheck("CALCULATED");

		page.getSlipSheetWorkProduct().waitAndClick(10);
		page.getSlipSheetWorkProductFolderProduction().ScrollTo();
		page.getSlipSheetWorkProductFolderProduction().waitAndClick(10);
		base.waitForElement(page.getbtnAddToSelect());
		page.getbtnAddToSelect().waitAndClick(10);
		page.getElementDisplayCheck(page.getSelectedFieldItems());
		base.stepInfo("Document is added");
		page.getRemoveBtnInSlipSheet().waitAndClick(10);
		if (page.getSelectedFieldItems().isElementAvailable(1)) {
			base.failedStep("document not removed");
		} else {
			base.passedStep("Document got removed");
		}
		base.stepInfo("Field Add-Remove Functionality worked well");

		// adv btn open close verification
		SoftAssert softAssertion = new SoftAssert();
		boolean flag5 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("down");
		if (flag5) {
			softAssertion.assertTrue(flag5);
			base.passedStep("Advanced button is open");
		} else {
			base.failedStep("verification failed");
		}
		page.getAdvanceBtnOpenCloseCheck().ScrollTo();
		driver.scrollPageToTop();
		page.getAdvanceBtnOpenCloseCheck().ScrollTo();
		page.getAdvanceBtnOpenCloseCheck().waitAndClick(10);
		driver.waitForPageToBeReady();
		boolean flag6 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("right");
		if (flag6) {
			softAssertion.assertTrue(flag6);
			softAssertion.assertAll();
			base.passedStep("Advanced button is close");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Advance Show/Hide Button should work as expected (show/Hide).");

		base.passedStep("Verified TIFF Section with various options");

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 15/03/22 NA Modified date: NA Modified by:NA
	 * Description :Verify concatenated email value should be displayed correctly on
	 * Slip Sheets in Production. 'RPMXCON-55940'
	 *
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-55940", enabled = true, groups = { "regression" })
	public void verifyEmailValueDisplayOnSlipSheets() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55940 -Production Sprint 13");
		base.stepInfo("Verify concatenated email value should be displayed correctly on Slip Sheets in Production.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String s1 = "EmailAuthorAddress";
		String s2 = "EmailAuthorName";
		String s3 = "EmailBCCNamesAndAddresses";
		String s4 = "EmailCCNamesAndAddresses";
		String s5 = "AuthorName";

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("IngestionName", "C113_GD_994_Native_Text_ForProduction_20211223121754233");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.addingSlipSheetWorkProduct(s1, s2, s3, s4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		String PDocCount = page.getProductionDocCount().getText();
		int docno = Integer.parseInt(PDocCount);
		int lastfile = firstFile + docno;
		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s1);
		page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s5);
		page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s3);
		page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s4);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-49729
	 * @Description Verify that branding is applied on all pages for image based
	 *              documents on generated PDF file
	 * 
	 */
	@Test(description = "RPMXCON-49729", enabled = true, groups = { "regression" })
	public void verifyDiffFileBrandingGenerateSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49729 -Production Component");
		base.stepInfo("Verify that branding is applied on all pages for  image based documents on generated PDF file");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject("AutomationAdditionalDataProject");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("RPMXCON39718");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();

		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		String PDocCount = page.getProductionDocCount().getText();
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.extractFile();
		page.pdf_Verification_In_Generated_PlaceHolder(firstFile, lastfile, prefixID, suffixID, Input.searchString4);

		base.passedStep(
				"Verified that branding is applied on all pages for  image based documents on generated PDF file");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-47932
	 * @Description To verify, User able to edit a production to regenerate it
	 * 
	 */
	@Test(description = "RPMXCON-47932", enabled = true, groups = { "regression" })
	public void verifyRegenerateWithEditSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47932 -Production Component");
		base.stepInfo("To verify, User able to edit a production to regenerate it");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String newProdName = "N" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getBackButton().waitAndClick(5);
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		page.clickElementNthtime(page.getBackButton(), 6);
		page.getMarkIncompleteButton().waitAndClick(5);
		page.getDATTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getDAT_DATField1().SendKeys("B" + Utility.dynamicNameAppender());
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(newProdName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.passedStep("verified, User able to edit a production to regenerate it");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-48814
	 * @Description Verify that Production should generated successfully for
	 *              Natively PDF documents
	 * @Hint : test case run on the project Regression_AllDataset_Consilio1 in UAT
	 *       environment
	 */
	@Test(description = "RPMXCON-48814", enabled = true, groups = { "regression" })
	public void verifyProdGenSuccussNativePdf() throws Exception {

		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);

		base.stepInfo("RPMXCON-48814 -Production Component");
		base.stepInfo("Verify that Production should generated successfully for Natively PDF documents");

		base.selectproject(Input.additionalDataProject);
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("NativelyPDF");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.passedStep("Verified that Production should generated successfully for Natively PDF documents");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-55952
	 * @Description Verify that if DAU clicks on project from dashboard and Copy the
	 *              Production URL which is not part of assigned domain Project
	 * 
	 */
	@Test(description = "RPMXCON-55952", enabled = true, groups = { "regression" })
	public void verifyProdUrlNotPartOfDomain() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55952 -Production Sprint 10");
		base.stepInfo(
				"Verify that if DAU clicks on project from dashboard and Copy the Production URL which is not part of assigned domain Project");
		base = new BaseClass(driver);

		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoPA();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(Input.securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		driver.waitForPageToBeReady();
		page.getDaAdditionalDataProject(Input.additionalDataProject).waitAndClick(10);
		page.gotoDAtoRMU(Input.additionalDataProject).waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("switched to RMU");

		page = new ProductionPage(driver);
		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgHeader().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep(
				"Verified that if DAU clicks on project from dashboard and Copy the Production URL which is not part of assigned domain Project");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-47924
	 * @Description To Verify Production Generation in Different Security Group.
	 * 
	 */
	@Test(description = "RPMXCON-47924", enabled = true, groups = { "regression" })
	public void verifyProdDiffSecuriyGroup() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47924 -Production Component");
		base.stepInfo("To Verify Production Generation in Different Security Group.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String securityGroup = "Production_Security_Group" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.translationDocumentId);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.selectAllDocs();
		doc.docListToBulkRelease(securityGroup);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);

		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.addTagToSecurityGroup(securityGroup, tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.passedStep("To Verify Production Generation in Different Security Group.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49725
	 * @Description Verify that branding is applied on all pages for image based
	 *              documents on generated TIFF file
	 * 
	 */
	@Test(description = "RPMXCON-49725", enabled = true, groups = { "regression" })
	public void verifyBrandingText() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49725 -Production Component");
		base.stepInfo("Verify that branding is applied on all pages for  image based documents on generated TIFF file");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;
		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.searchString4);

		base.passedStep(
				"Verified that branding is applied on all pages for  image based documents on generated TIFF file");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-47969
	 * @Description To Verify Correct Count of Native Documents produce in
	 *              (Production in Different Security Group).
	 * 
	 */
	@Test(description = "RPMXCON-47969", enabled = true, groups = { "regression" })
	public void verifyProdPrivCoutDiffSecuriyGroup() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47969 -Production Component");
		base.stepInfo(
				"To Verify Correct  Count of Native Documents produce in (Production in Different Security Group).");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String securityGroup = "Production_Security_Group" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(securityGroup);
		sessionSearch.bulkFolderExisting(foldername);

		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.addFolderToSecurityGroup(securityGroup, foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.verifyText(page.getNumberOfNativeDocs(), "0");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.passedStep(
				"To Verify Correct  Count of Native Documents produce in (Production in Different Security Group).");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55993
	 * @Description: Verify that status text along with the docment counts show
	 *               properly in the space available for the progress bar on tile
	 *               view
	 */
	@Test(description = "RPMXCON-55993", enabled = true, groups = { "regression" })
	public void verifyStatusTextAndDocumentCountWithDifferentResolution() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55993 -Production component");
		base.stepInfo(
				"Verify that status text along with the docment counts show properly in the space available for the progress bar on tile view");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		int[] diemen = { 1920, 1080 };
		double[] zoom = { 0.75, 0.80, 0.90, 1, 1.25 };
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		base.stepInfo("Production with different progress status should be available");

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		for (int i = 0; i < zoom.length; i++) {

			Dimension pram1 = new Dimension(diemen[0], diemen[1]);
			driver.Manage().window().setSize(pram1);
			((JavascriptExecutor) driver.getWebDriver()).executeScript("document.body.style.zoom = '" + zoom[i] + "'");

			driver.Navigate().refresh();
			if (page.getProductionFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar text is displayed in : " + diemen[0] + "x" + diemen[1]
						+ " with zoom size : " + zoom[i]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar text is not displayed");
				System.out.println("Not displayed");
			}
			if (page.getProductionDocCountFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar document count is displayed in : " + diemen[0] + "x" + diemen[1]
						+ " with zoom size : " + zoom[i]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar document count is not displayed");
				System.out.println("Not displayed");
			}
		}

		base.passedStep(
				"Verified that status text along with the docment counts show properly in the space available for the progress bar on tile view");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith created on:NA modified by:NA TESTCASE No:RPMXCON-47926
	 * @Description:To Verify PRIV flag configured in the DAT section of Production
	 *                 is to being honored for all docs in the generated production
	 */
	@Test(description = "RPMXCON-47926", enabled = true, groups = { "regression" })
	public void verifyDatFiledisBlank() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case id : RPMXCON-47926 ");
		base.stepInfo(
				"To Verify PRIV flag configured in the DAT section of Production is to being honored for all docs in the generated production");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addNewFieldOnDAT();
		page.addDatField(1, "Production", "TIFFPageCount");
		page.getPrivledgedDATCheckBox(1).waitAndClick(10);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.getIncludeFamilies().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();

		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}

		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		for (String a : lines) {
			System.out.println(a);
			String[] arrOfStr = a.split("þ");
			for (String text : arrOfStr) {
				System.out.println("value : " + text);
				if (text.trim().equals("") || text.isEmpty()) {
					base.passedStep("meta data field is blank");
				} else {
					base.stepInfo("this field is bates number");
				}
			}
		}
		brReader.close();
		loginPage.logout();
		base.passedStep(
				"Verified PRIV flag configured in the DAT section of Production is to being honored for all docs in the generated production");
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56050
	 * @Description: Verify that after destination copy is completed it should
	 *               displays 'Exporting Files Complete' status on Grid View
	 */
	@Test(description = "RPMXCON-56050", groups = { "regression" })
	public void verifyExportinFilesCompleteStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56050 -Production Compinent");
		base.stepInfo(
				"Verify that after destination copy is completed it should displays 'Exporting Files Complete' status on Grid  View");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		// Go To Production Home Page
		page.goToProductionGridView();
		page.verifyProductionStatusInGridViewForHighVolumeProject("Exporting Files Complete", productionname);

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56047
	 * @Description: Verify that after LST generation completed it should displays '
	 *               Generating Load Files Complete' status on Grid View on home
	 *               page
	 */
	@Test(description = "RPMXCON-56047", groups = { "regression" })
	public void verifyGeneratingLoadCompleteStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56047 -Production Compinent");
		base.stepInfo(
				"Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Grid View on home page");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		// Go To Production Home Page
		page.goToProductionGridView();
		page.verifyProductionStatusInGridViewForHighVolumeProject("Generating Load Files Complete", productionname);

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-60903
	 * @Description Verify Production should be generated successfully with the
	 *              redacted documents (for documents with annotation)
	 * 
	 */
	@Test(description = "RPMXCON-60903", enabled = true, groups = { "regression" })
	public void verifyAnotationForRedactionPdf() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-60903 -Production Component");
		base.stepInfo(
				"Verify Production should be generated successfully with the redacted documents (for documents with annotation)");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("PDF Annotations");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagname);
		doc.selectAllDocs();
		doc.bulkFolderExisting(foldername);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.additionalDataProject);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.selectFolderViewInDocView(foldername);

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		docView.documentSelection(3);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		page.pdf_Verification_In_Generated_Pdf(prefixID, suffixID, beginningBates);

		base.passedStep(
				"Verified Production should be generated successfully with the redacted documents (for documents with annotation)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani created on:25/03/2022 modified by:NA TESTCASE
	 *         No:RPMXCON-47171
	 * @Description:Verify that PDF files should be copied to folder when 'Split Sub
	 *                     Folders' is ON with split count as 10. 'RPMXCON-47171'
	 */
	@Test(description = "RPMXCON-47171", enabled = true, groups = { "regression" })
	public void verifyTheSubFolderAfterGenrationPDFFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47171 -Production Sprint 14");
		base.stepInfo(
				"Verify that PDF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname);

		// document for pdf section
		ProductionPage page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		String home = System.getProperty("user.home");

		File dirPdf = new File(home + "\\Downloads\\VOL0001\\PDF");
		File dirText = new File(home + "\\Downloads\\VOL0001\\Text");
		File dirNative = new File(home + "\\Downloads\\VOL0001\\Natives");
		int dirPdfCount = page.dirFoldersCount(dirPdf);
		int dirTextCount = page.dirFoldersCount(dirText);
		int dirNativeCount = page.dirFoldersCount(dirNative);
		if (1 != dirPdfCount) {
			System.out.println("pdf verified");
			base.stepInfo("Pdf folder is split");
		}
		if (1 != dirNativeCount) {
			System.out.println("Native verified");
			base.stepInfo("Native folder is split");
		}
		if (1 != dirTextCount) {
			System.out.println("Text verified");
			base.stepInfo("Text folder is split");
		}
		base.passedStep("files is split as per the split folder count");
		base.passedStep(
				"Verified that PDF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10");

		page.deleteFiles();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55983
	 * @Description: Verify that once Archiving is in progress, it will displays
	 *               status on Production Progress bar Tile View as 'Creating
	 *               Archive - 10%'
	 */
	@Test(description = "RPMXCON-55983", enabled = true, groups = { "regression" })
	public void verifiyCreateArchicTenPercenteOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55983 - Production Component");
		base.stepInfo(
				"Verify that once Archiving is in progress, it will displays status on Production Progress bar Tile View as 'Creating Archive - 10%'");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInTileViewForHighVolumeProject("Creating Archive - 10%", productionname);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55982
	 * @Description: Verify that once Archiving is in progress, it will displays
	 *               status on Production Generation page as 'Creating Archive -
	 *               10%'
	 */
	@Test(description = "RPMXCON-55982", enabled = true, groups = { "regression" })
	public void createArchivingTenPercentStatusVerifyOnGenPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55982 -Production");
		base.stepInfo(
				"Verify that once Archiving is in progress, it will displays status on Production Generation page as 'Creating Archive - 10%'");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Wait until Generate button enables
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		driver.waitForPageToBeReady();
		page.verifyProductionStatusInGenerationPageForHighVolumeProject("Creating Archive - 10%");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-49726
	 * @Description Verify that branding is applied on all pages for image based
	 *              documents on generated PDF file
	 * 
	 */
	@Test(description = "RPMXCON-49726", enabled = true, groups = { "regression" })
	public void verifyPdfBrandingGenerateSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49726 -Production Component");
		base.stepInfo("Verify that branding is applied on all pages for  image based documents on generated PDF file");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName(Input.pdfDataSet);
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();

		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		String PDocCount = page.getProductionDocCount().getText();
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.extractFile();
		page.pdf_Verification_In_Generated_PlaceHolder(firstFile, lastfile, prefixID, suffixID, Input.searchString4);

		base.passedStep(
				"Verified that branding is applied on all pages for  image based documents on generated PDF file");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55979
	 * @Description: Verify that after LST generation completed it should displays '
	 *               Generating Load Files Complete' status on Production Progress
	 *               bar Tile View
	 */
	@Test(description = "RPMXCON-55979", groups = { "regression" })
	public void verifiyLSTGenCompleteOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55979 -Production Component");
		base.stepInfo(
				"Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Progress bar Tile View");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Generating Load Files", productionname);
		base.passedStep(
				"Verified that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Progress bar Tile View");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55978
	 * @Description: Verify that after LST generation completed it should displays '
	 *               Generating Load Files Complete' status on Production Generation
	 *               Page
	 */
	@Test(description = "RPMXCON-55978", groups = { "regression" })
	public void verifyGenerationLoadFileCompleteStatusInGenPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55978 -Production");
		base.stepInfo(
				"Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Generation Page");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Wait until Generate button enables
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
		page.verifyProductionStatusInGenPage("Load File Generation");
		base.passedStep(
				"Verified that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Generation Page");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-58561
	 * @Description Verify the load files should be created under the mentioned
	 *              default directory in the Export location
	 * 
	 */
	@Test(description = "RPMXCON-58561", groups = { "regression" })
	public void verifyLoadFileMentionedDirectory() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-58561 -Production");
		base.stepInfo(
				"Verify the load files should be created under the mentioned default directory in the Export location");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.fillingMP3();
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.advancedProductionComponentsTranslations();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.GetVolumeLocation().selectFromDropdown().selectByVisibleText("In Delivery Folder");
		page.visibleCheck("Load Files:");
		page.visibleCheck("Folder Name:");
		page.fillingExportLocationPage(productionname);

		String location = page.getProductionOutputLocation_VolumeName().GetAttribute("value");
		String loadfile = page.getProductionComponentsFolderDetails_FolderName_LoadFiles().GetAttribute("value");

		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String name = page.getProduction().getText().trim();
		page.getCopyPath().waitAndClick(10);

		String actualCopedText = page.getCopiedTextFromClipBoard();

		String parentTab = page.openNewTab(actualCopedText);
		page.getFileDir(location).waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getFileDir(loadfile).waitAndClick(10);
		driver.waitForPageToBeReady();

		page.visibleCheck(name + "_DAT.dat");
		page.visibleCheck(name + "_MP3.lst");
		page.visibleCheck(name + "_Native.lst");
		page.visibleCheck(name + "_Text.lst");
		page.visibleCheck(name + "_TIFF.OPT");
		page.visibleCheck(name + "_TRANSLATION.lst");

		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		base.passedStep(
				"Verified the load files should be created under the mentioned default directory in the Export location");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-48314
	 * @Description Verify that Producing a PDF with text highlighting and reviewer
	 *              remarks, block redactions and 'this page', must not eliminate
	 *              any text characters unintentionally in the produced document
	 * 
	 */
	@Test(description = "RPMXCON-48314", enabled = true, groups = { "regression" })
	public void verifyProducedPadWithTextReviewRemarkRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48314 -Production Component");
		base.stepInfo(
				"Verify that Producing a PDF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int docCount = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		docView.documentSelection(docCount);
		driver.waitForPageToBeReady();
		docViewRedactions.RedactTextInDocView(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		String home = System.getProperty("user.home");
		File Native = new File(home + "/Downloads/VOL0001/Natives/");
		page.extractFile();
		page.isdatFileExist();
		page.isfileisExists(Native);
		page.pdf_Verification_In_Generated_Pdf(prefixID, suffixID, beginningBates);
		page.pdf_Verification_In_Generated_PlaceHolder(prefixID, suffixID, beginningBates, "REDACTED");

		base.passedStep(
				"Verified that Producing a PDF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-48315
	 * @Description Verify that Producing a TIFF with text highlighting and reviewer
	 *              remarks, block redactions and 'this page', must not eliminate
	 *              any text characters unintentionally in the produced document
	 * 
	 */
	@Test(description = "RPMXCON-48315", enabled = true, groups = { "regression" })
	public void verifyProducedTiffWithTextReviewRemarkRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48315 -Production Component");
		base.stepInfo(
				"Verify that Producing a TIFF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int docCount = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		docView.documentSelection(docCount);
		driver.waitForPageToBeReady();
		docViewRedactions.RedactTextInDocView(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionBurnRedaction(Redactiontag1, "REDACTED");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		String home = System.getProperty("user.home");
		File Native = new File(home + "/Downloads/VOL0001/Natives/");
		page.extractFile();
		page.isdatFileExist();
		page.isfileisExists(Native);
		page.OCR_Verification_In_Generated_Tiff_tess4j(prefixID, suffixID, beginningBates);

		base.passedStep(
				"Verified that Producing a TIFF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-48494
	 * @Description To verify that If user select RedactionTag and if Audio document
	 *              is associated to the selected Redaction Tag then Native should
	 *              not produced
	 * 
	 */
	@Test(description = "RPMXCON-48494", enabled = true, groups = { "regression" })
	public void verifyRedactionTagforAudioDoc() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48494-Production Component");
		base.stepInfo(
				"To verify that If user select RedactionTag and if Audio document is associated to the selected Redaction Tag then Native should not produced");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("IngestionName", "B2F9_Automation_AllSources_20211130043120500");
		sessionSearch.selectOperatorInBasicSearch("AND");
		sessionSearch.SearchMetaDataWithoutUrlPassing("AudioPlayerReady", "1");
		sessionSearch.addPureHit();
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		tagsAndFolderPage.selectFolderViewInDocView(foldername);
		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.deleteAllAppliedRedactions();
		driver.waitForPageToBeReady();
		docViewRedactions.clickOnAddRedactionForAudioDocument();
		docViewRedactions.addAudioRedaction(Input.startTime, Input.endTime, Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionBurnRedaction(Redactiontag1, "REDACTED");
		page.fillingTextSection();
		page.fillingMP3FileWithBurnRedaction(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;
		String home = System.getProperty("user.home");
		File Native = new File(home + "/Downloads/VOL0001/Natives/" + prefixID + beginningBates + suffixID + ".MP3");
		driver.waitForPageToBeReady();

		page.waitForFileDownload();
		page.extractFile();

		page.isMp3FileExist(firstFile, lastFile, prefixID, suffixID);

		if (Native.exists()) {
			System.out.println(" file is Exists in pointed directory");
			base.failedStep(Native + " file is Exists in pointed directory");
		} else {
			base.passedStep("Natives is not be produced for MP3 if it is associated to selected Redaction Tag ");
		}

		base.passedStep(
				"verifed that If user select RedactionTag and if Audio document is associated to the selected Redaction Tag then Native should not produced");

		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-47172
	 * @Description Verify that PDF files should be copied to folder when 'Split Sub
	 *              Folders' is OFF with split count as 10
	 */
	@Test(description = "RPMXCON-47172", enabled = true, groups = { "regression" })
	public void verifyPdfFileAreNotSplit() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47172 -Production");
		base.stepInfo(
				"Verify that PDF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 10");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.visibleCheck("Numbering and Sorting");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.visibleCheck("Priv Guard");
		page.fillingPrivGuardPage();
		page.visibleCheck("Production Location");
		driver.scrollingToBottomofAPage();
		page.getsplitSubFolderbtn().waitAndClick(10);
		base.stepInfo("split sub folder toggle as OFF");
		driver.scrollPageToTop();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.visibleCheck("Summary & Preview");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		String home = System.getProperty("user.home");

		File dirPdf = new File(home + "\\Downloads\\VOL0001\\PDF");
		File dirNative = new File(home + "\\Downloads\\VOL0001\\Natives");
		File dirText = new File(home + "\\Downloads\\VOL0001\\Text");
		softAssertion = new SoftAssert();
		int dirPdfCount = page.dirFoldersCount(dirPdf);
		int dirTextCount = page.dirFoldersCount(dirText);
		softAssertion.assertEquals(1, dirPdfCount);
		softAssertion.assertEquals(1, dirTextCount);
		softAssertion.assertEquals(1, dirNative);
		base.passedStep("files is not split as 'Split Folder' toggle was OFF");
		base.passedStep(
				"Verified that PDF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 10");

		page.deleteFiles();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-48316
	 * @Description Verify the production for Audio files which includes the Audio
	 *              redaction extend to end of the audio file
	 * 
	 */
	@Test(description = "RPMXCON-48316", enabled = true, groups = { "regression" })
	public void verifyProductionMp3File() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48316 -Production Component");
		base.stepInfo(
				"Verify the production for Audio files which includes the Audio redaction extend to end of the audio file");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("AudioEndFile");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagname);
		doc.selectAllDocs();
		doc.bulkFolderExisting(foldername);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.additionalDataProject);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.selectFolderViewInDocView(foldername);

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		docView.documentSelection(3);
		docViewRedactions.deleteAllAppliedRedactions();
		driver.waitForPageToBeReady();
		docViewRedactions.clickOnAddRedactionForAudioDocument();
		docViewRedactions.addAudioRedaction(Input.startTime, Input.endTime, Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingMP3FileWithBurnRedaction(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;
		page.waitForFileDownload();
		page.extractFile();

		page.isMp3FileExist(firstFile, lastFile, prefixID, suffixID);
		base.passedStep(
				"Verified the production for Audio files which includes the Audio redaction extend to end of the audio file");

		page.deleteFiles();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description :To verify that in Production, if sorting option is Sort by
	 *              Selected Tags and 'Keep Families Together' check box is selected
	 *              then produced document should be sorted by Tags with FamilyID [
	 *              RPMXCON-49228]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49228", enabled = true, groups = { "regression" })
	public void verifySortByTags() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String foldername = "prodFolder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49228 Production Sprint 13");
		base.stepInfo(
				"To verify that in Production, if sorting option is Sort by Selected Tags and 'Keep Families Together' check box is selected then produced document should be sorted by Tags with FamilyID");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doclist = new DocListPage(driver);
		doclist.selectFirstParentDocumentWithChildDocument();
		String docId = doclist.getParentDocumetId();
		System.out.println(docId);
		driver.scrollPageToTop();
		doclist.bulkTagExistingFromDoclist(tagname);

		base.stepInfo("Starting the production");
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addNewFieldOnDAT();
		page.addDatField(1, "Doc Basic", "DocID");
		page.fillingNativeSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.fillingTextSection();
		page.advancedProductionComponentsTranslations();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPageWithSortByTags(prefixID, suffixID, beginningBates, tagname);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.waitForFileDownload();
		page.extractFile();

		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}

		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		for (String a : lines) {
			System.out.println(a);
		}

		System.out.println("secount row value : " + lines.get(1));
		if (lines.get(1).contains(docId)) {
			base.passedStep("Document is sorted as per order");
		} else {
			base.failedStep("failed");
		}

		brReader.close();

		base.passedStep(
				"verified that in Production, if sorting option is Sort by Selected Tags and 'Keep Families Together' check box is selected then produced document should be sorted by Tags with FamilyID");
		page.deleteFiles();

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-49781
	 * @Description Verify that if document is produced and if user rotate the
	 *              redacted images then after copying to any other file then
	 *              redacted image should not be displayed
	 * 
	 */
	@Test(description = "RPMXCON-49781", enabled = true, groups = { "regression" })
	public void verifyAfterRotationRedactionNotDisplayed() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49781 -Production Component");
		base.stepInfo(
				"Verify that if document is produced  and if user rotate the redacted images then after copying to any other file then redacted image should not be displayed");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("RPMXCON39718");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(3);
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(3);
		doc.bulkFolderExisting(foldername);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.additionalDataProject);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.selectFolderViewInDocView(foldername);

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		docView.documentSelection(3);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String firstDocument = prefixID + beginningBates + suffixID;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.selectBurnRedaction(Redactiontag1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.waitForFileDownload();
		page.extractFile();

		String home = System.getProperty("user.home");
		File source = new File(home + "\\Downloads\\VOL0001\\PDF\\0001\\" + firstDocument + ".pdf");
		File dest = new File(home + "\\Downloads\\CopiedPdf.pdf");

		page.copyFileUsingStream(source, dest);
		int pageCount = page.pdfToJpgConverter(dest);
		page.RotatePdfFile(dest, pageCount);

		PDDocument document = PDDocument.load(dest);
		if (!document.isEncrypted()) {
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document);
			System.out.println("Text:" + text);
			if (!text.contains("RED")) {
				base.passedStep("Redacted area is not displayed");
			} else {
				base.failedStep("Redacted area displayed");
			}
		}
		document.close();
		base.passedStep(
				"Verified that if document is produced  and if user rotate the redacted images then after copying to any other file then redacted image should not be displayed");

		page.deleteFiles();
		loginPage.logout();

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			// loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
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
