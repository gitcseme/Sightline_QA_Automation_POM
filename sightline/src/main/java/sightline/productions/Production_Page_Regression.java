package sightline.productions;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.interactions.Actions;
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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;


public class Production_Page_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
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


	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();
		baseClass = new BaseClass(driver);
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

@Test(description="RPMXCON-55991",enabled = true, groups = { "regression" } )
	public void verifyStatusAfterRegeneratingProductionInHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55991- Production Sprint 06");
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

		baseClass.waitForElement(page.getOpenWizard());
		page.getOpenWizard().waitAndClick(5);
        driver.waitForPageToBeReady();
		baseClass.waitForElement(page.getbtnContinueGeneration());
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

	@Test(description="RPMXCON-55992",enabled = true, groups = { "regression" } )
	public void verifyStatusAfterRegeneratingProductionInGeneratePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55992- Production Sprint 06");
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
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page = new ProductionPage(driver);
		page.reGenarate(productionname);
		baseClass.passedStep(
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

	@Test(description="RPMXCON-56087",enabled = true,groups = { "regression" } )
	public void generateTIFFWithTechIssue() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-56087- Production Sprint 06");
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
		baseClass.passedStep("Generated TIFF with Tech Issue Placeholder by selecting only DAT");

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

	@Test(description="RPMXCON-56089",enabled = true,groups = { "regression" } )
	public void generateTIFFWithNativelyPlaceholder() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56089- Production Sprint 06");
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
		baseClass.passedStep("Generated Production with DAT and Natively Produced Documents Placholder");

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

	@Test(description="RPMXCON-56093",enabled = true, groups = { "regression" } )
	public void verifyErrorMessageInDAT() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56093- Production Sprint 06");
		UtilityLog.info(Input.prodPath);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		baseClass.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();
		baseClass.waitForElement(page.getDATTab());
		page.getDATTab().Click();
		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();
		baseClass.waitForElement(page.getErrorMsgInDAT());
		String actual = page.getErrorMsgInDAT().getText();
		String expected = "No fields are added in the DAT section. Please complete before navigating to the next step.";
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
				"Error message displayed in DAT as No fields are added in the DAT section. Please complete before navigating to the next step.");
		loginPage.logout();

	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56092
	 * @Description:Verify the error message for DAT component when 'Add same data
	 *                     field more than one time'
	 */

	@Test(description="RPMXCON-56092",enabled = true, groups = { "regression" } )
	public void verifyErrorMessageInDATByRepeatingSameField() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56092- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();

		baseClass.waitForElement(page.getBtnAddFieldInDAt());
		page.getBtnAddFieldInDAt().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		baseClass.waitForElement(page.getDAT_FieldClassification2());
		page.getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(Input.bates);

		baseClass.waitForElement(page.getDAT_SourceField2());
		page.getDAT_SourceField2().selectFromDropdown().selectByVisibleText(Input.batesNumber);

		baseClass.waitForElement(page.getDAT_DATField2());
		page.getDAT_DATField2().SendKeys("B" + Utility.dynamicNameAppender());

		driver.scrollPageToTop();

		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		baseClass.waitForElement(page.getErrorMsgPopupInDAT());
		String actual = page.getErrorMsgPopupInDAT().getText();
		String expected = "In the DAT configuration, at least one of the project fields is mapped to multiple DAT fields. Do you want to continue with this same configuration?";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
				"Error message displayed in DAT as In the DAT configuration, at least one of the project fields is mapped to multiple DAT fields. Do you want to continue with this same configuration?");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56098
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     with different source field and same DAT field name'
	 */

	@Test(description="RPMXCON-56098",enabled = true,  groups = { "regression" } )
	public void verifyErrorMessageInDATForSameDatFieldName() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56098- Production Sprint 07");
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

		baseClass.waitForElement(page.getBtnAddFieldInDAt());
		page.getBtnAddFieldInDAt().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		baseClass.waitForElement(page.getDAT_FieldClassification2());
		page.getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(Input.bates);

		baseClass.waitForElement(page.getDAT_SourceField2());
		page.getDAT_SourceField2().selectFromDropdown().selectByVisibleText(Input.ChildBates);

		page.getDAT_DATField2().Click();
		baseClass.waitForElement(page.getDAT_DATField2());

		// Paste the text

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		driver.scrollPageToTop();

		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		baseClass.waitForElement(page.getErrorMsgInDATForSameDatField());
		String actual = page.getErrorMsgInDATForSameDatField().getText();
		String expected = "Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
				"Error message displayed in DAT as Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56097
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     with special character'
	 */

	@Test(description="RPMXCON-56097",enabled = true,  groups = { "regression" } )
	public void verifyErrorMessageInDATWithSpecialCharacter() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56097- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
	
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String special = "T?T";
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, special);

		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		baseClass.waitForElement(page.getErrorMsgInDATWithSpecialCharacter());
		String actual = page.getErrorMsgInDATWithSpecialCharacter().getText();
		String expected = "Special characters other than underscore (_) are not allowed in the  DAT field names. Please check.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
				"Error message displayed in DAT as Special characters other than underscore (_) are not allowed in the  DAT field names. Please check.");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56096
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     other that alphabet character'
	 */

	@Test(description="RPMXCON-56096",enabled = true, groups = { "regression" } )
	public void verifyErrorMessageInDATAddingOtherCharacter() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56096- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String special = "?";
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, special);

		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		baseClass.waitForElement(page.getErrorMsgInDATWithOtherCharacter());
		String actual = page.getErrorMsgInDATWithOtherCharacter().getText();
		String expected = "DAT field names must begin with an alphabet. Please check.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
				"Error message displayed in DAT as DAT field names must begin with an alphabet. Please check.");
		loginPage.logout();
	}
	
	/**
	 * @Author Sowndarya.Velraj created on:12/01/21 TESTCASE No:RPMXCON-56094
	 * @Description:Verify the error message for DAT component when 'Don't add bate
	 *                     number data field'
	 */
	@Test(description="RPMXCON-56094",enabled = true,  groups = { "regression" } )
	public void verifyErrorMessageInDATByNotAddingBates()  throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56094- Production Sprint 07");
		
		UtilityLog.info(Input.prodPath);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		batesNumber = "B" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		baseClass.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();

		baseClass.waitForElement(page.getDATTab());
		page.getDATTab().Click();

		baseClass.waitForElement(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.productionText);

		baseClass.waitForElement(page.getDAT_SourceField1());
		driver.waitForPageToBeReady();
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.tiffPageCountNam);

		baseClass.waitForElement(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(batesNumber);

		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		baseClass.waitForElement(page.getErrorMsgInDATWithoutAddingBates());
		String actual = page.getErrorMsgInDATWithoutAddingBates().getText();
		String expected = "Bates Number must be selected in the DAT for a production.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
				"Error message displayed in DAT asBates Number must be selected in the DAT for a production.");
		loginPage.logout();
		
	}

	/**
	 * @Author Sowndarya.Velraj created on:12/01/21 TESTCASE No:RPMXCON-56095
	 * @Description:Verify the error message for DAT component when 'don't select
	 *                     DAT component'
	 */
	@Test(description="RPMXCON-56095",enabled = true,  groups = { "regression" } )
	public void verifyErrorMessageInDATByNotSelectingDAT() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56095- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		batesNumber = "B" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		baseClass.waitForElement(page.getDATChkBox());

		baseClass.waitForElement(page.getDATTab());
		page.getDATTab().Click();

		baseClass.waitForElement(page.getDAT_FieldClassification1());
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);

		baseClass.waitForElement(page.getDAT_SourceField1());
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.batesNumber);

		baseClass.waitForElement(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(batesNumber);

		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();

		baseClass.waitForElement(page.getErrorMsgInDATWithoutSelectingDAT());
		String actual = page.getErrorMsgInDATWithoutSelectingDAT().getText();
		String expected = "Selection of the DAT component is mandatory for a production.";

		softAssertion = new SoftAssert();
		softAssertion.assertEquals(actual, expected);
		baseClass.passedStep(
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

	@Test(description="RPMXCON-56044",enabled = true,  groups = { "regression" } )
	public void verifyPostGenCompleteStatusInGridView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56044- Production Sprint 07");
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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-56042,RPMXCON-56036",enabled = true, groups = { "regression" } )
	public void verifyPreGenChecksInHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56042- Production Sprint 07");
		baseClass.stepInfo("Test case Id: RPMXCON-56036- Production Sprint 07");
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
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(540);
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Going to Home Page");
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks", productionname);
		baseClass.passedStep(
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
	@Test(description="RPMXCON-56039",enabled = true, groups = { "regression" } )
	public void verifyReservingBatesRangeInGridView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56039- Production Sprint 07");
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
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(540);
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Going to Home Page");
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);
		baseClass.passedStep(" 'Reserving Bates Range' status on Grid View on Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/3/21 TESTCASE No:RPMXCON-56038
	 * @Description:Verify that after Pregen checks completed it should displays
	 *                     'Pre-Gen Checks Complete' status on Production Grid View
	 */
	@Test(description="RPMXCON-56038",enabled = true,  groups = { "regression" } )
	public void verifyPreGenChecksInGridView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56038- Production Sprint 07");
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

		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		
		page.goToProductionGridView();
		page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks Complete", productionname);
		baseClass.passedStep(" verified 'Pre-Gen Checks Complete 'status on Grid View on Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-56016
	 * @Description:Verify that after Pregen checks completed it should displays
	 *                     'Pre-Gen Checks Complete' status on Progress bar in Tile
	 *                     View on Production Home page
	 */
	@Test(description="RPMXCON-56016",enabled = true,  groups = { " regression" } )
	public void verifyPreGenChecksCompleteInHomePage() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-56016- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);

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
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		
		page.verifyProductionStatusInHomePage("Pre-Gen Checks Complete", productionname);
		baseClass.passedStep("verified 'Pre-Gen Checks Complete 'status on  Production Home page");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-55985
	 * @Description:Verify that once Post Geneation is in progress, it will displays
	 *                     status on Production Generation page as 'Post-Gen QC
	 *                     checks In Progress'
	 */
	@Test(description="RPMXCON-55985",enabled = true, groups = { " regression" } )

	public void verifyPostGenQCchecksInProgressInGeneratePage() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55985- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
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
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(120);
		Reporter.log("Wait for generate to complete, true");
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		
		page.getbtnContinueGeneration().isElementAvailable(60);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			baseClass.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}

		page.verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");
		baseClass.passedStep(
				" Generation is completed and displays Post-Gen QC Checks In Progress' status Generation page");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-55984
	 * @Description:Verify that once Post Generation is in progress, it will
	 *                     displays status on Production Progress bar ,Tile View as
	 *                     'Post-Gen QC Checks In Progress'
	 */
	@Test(description="RPMXCON-55984",enabled = true,groups = { " regression" } )
	public void verifyPostGenQCchecksInProgressInTileView() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55984- Production Sprint 07");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname,Input.tagNamePrev);

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
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		baseClass.waitTime(1);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			baseClass.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}
		
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks In Progress", productionname);
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete",productionname);
		baseClass.passedStep(
				" Post Generation is in progress, it will displays status on Production Progress bar ,Tile View as 'Post-Gen QC Checks In Progress'");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/14/21 TESTCASE No:RPMXCON-48849
	 * @Description:Verify that user can download only Produced DAT file by
	 *                     selecting 'Download DAT file'
	 */
	@Test(description="RPMXCON-48849",enabled = true, groups = { " regression" } )
	public void verifyDownloadDATFile() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48849- Production Sprint 08");
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
		baseClass.waitForElement(page.getQC_Download());
		page.getQC_Download().waitAndClick(10);
		baseClass.waitForElement(page.getClkBtnDownloadDATFiles());
		page.getClkBtnDownloadDATFiles().waitAndClick(5);
		BaseClass baseClass=new BaseClass(driver);
		baseClass.VerifySuccessMessage("Your Production Archive download will get started shortly");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/14/21 TESTCASE No:RPMXCON-48664
	 * @Description:Run Production by selecting components like DAT,TIFF,NATIVE and
	 *                  with selection of multiple tags with audio files
	 */
	@Test(description="RPMXCON-48664",enabled = true, groups = { " regression" } )
	public void verifyAudioFilesWithMultipleBranding() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48664- Production Sprint 08");
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
		BaseClass baseClass=new BaseClass(driver);
		baseClass.CloseSuccessMsgpopup();
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
		baseClass.passedStep(
				"Production generated  by selecting components like DAT,TIFF,NATIVE and with selection of multiple tags with audio files");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49250
	 * @Description:To verify In Productions DAT, provide the TIFFPageCount for each
	 *                 document should be zero when only DAT component is selected
	 */
	@Test(description="RPMXCON-49250",enabled = true, groups = { " regression" } )
	public void verifyDocumentCountForDAT() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49250- Production Sprint 08");
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
	@Test(description="RPMXCON-49250",enabled = true, groups = { " regression" } )
	public void verifyDocumentCountForDATWithOtherComponent() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49250- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname,"Privileged");

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
	@Test(description="RPMXCON-49106",enabled = true, groups = { " regression" } )
	public void verifyTotalPagesOnSummary() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49106- Production Sprint 08");
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
		BaseClass baseClass=new BaseClass(driver);
		baseClass.CloseSuccessMsgpopup();
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
		baseClass.passedStep("Total Documents:" + expected);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49107
	 * @Description:To verify that Total Pages count on Production-Summary page
	 */
	@Test(description="RPMXCON-49107",enabled = true, groups = { " regression" })
	public void verifyTotalPagesCountOnSummary() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49107- Production Sprint 08");
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
		BaseClass baseClass=new BaseClass(driver);
		baseClass.CloseSuccessMsgpopup();
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
		baseClass.passedStep("Total Page count is:" + expected);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/26/21 TESTCASE No:RPMXCON-48505
	 * @Description:Verify that Tiff should generate with Burned Redaction if Only
	 *                     Burn Redaction is enabled
	 */
	@Test(description="RPMXCON-48505",enabled = true, groups = { " regression" } )
	public void verifyTiffWithBurnRedaction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48505- Production Sprint 08");
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
	@Test(description="RPMXCON-47791",enabled = true, groups = { " regression" } )
	public void verifySummaryAndPreview() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47791- Production Sprint 08");
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
		BaseClass baseClass=new BaseClass(driver);
		baseClass.CloseSuccessMsgpopup();
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
		baseClass.passedStep("Total Documents:" + totalDocumentsCount);

		String totalPagesCount = page.getValueTotalPagesCount().getText();
		baseClass.passedStep("Total Page count is:" + totalPagesCount);

		String totalCustodians = page.getValueNumberOfCustodians().getText();
		baseClass.passedStep("Number of custodians:" + totalCustodians);

		String FirstAndLastDocuments = page.getValueFirstLastDocs().getText();
		baseClass.passedStep("First And Last Documents:" + FirstAndLastDocuments);

		String redactedDocs = page.getValueRedactedDocs().getText();
		baseClass.passedStep("Count Of Redacted Documents:" + redactedDocs);

		
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
	@Test(description="RPMXCON-48502",enabled = true, groups = { " regression" } )
	public void verifyPDFWithAnnotationLayer() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48502- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Searching for a content and performing bulk folder action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		baseClass.stepInfo("Navigating to Production Home page");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		baseClass.waitForElement(page.getDATRedactionsCBox());
		page.getDATRedactionsCBox().waitAndClick(10);
		page.fillingNativeSection();
		page.fillingPDFWithRedactedDocumentsInAnnotationLayer();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		baseClass.stepInfo("Deleting the tags and folders after the production gets completed");
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
	@Test(description="RPMXCON-48503",enabled = true, groups = { " regression" } )
	public void verifyMP3WithAnnotationLayer() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48503- Production Sprint 08");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Searching for a content and performing bulk folder action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		baseClass.stepInfo("Navigating to Production Home page");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		baseClass.waitForElement(page.getDATRedactionsCBox());
		page.getDATRedactionsCBox().waitAndClick(10);
		page.fillingNativeSection();
		page.fillingMP3();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		baseClass.stepInfo("Deleting the tags and folders after the production gets completed");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:12/30/21 TESTCASE No:RPMXCON-48322
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 TIFF components.
	 */
	@Test(description="RPMXCON-48322",enabled = true, groups = { " regression" } )
	public void verifyGenerateLoadFileForTIFF() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48322- Production Sprint 09");
		UtilityLog.info(Input.prodPath);
		boolean flag;

		baseClass.stepInfo("Navigating to Production Home page and creating new production set");
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
			baseClass.failedStep("Generate Load File is not enabled by default  for TIFF components.");
		} else {
			Assert.assertTrue(true);
			baseClass.passedStep("Generate Load File is  enabled by default  for TIFF components.");
		}
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:12/30/21 TESTCASE No:RPMXCON-48323
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 PDF components.
	 */
	@Test(description="RPMXCON-48323",enabled = true, groups = { " regression" } )
	public void verifyGenerateLoadFileForPDF() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48323- Production Sprint 09");
		UtilityLog.info(Input.prodPath);
		boolean flag;

		baseClass.stepInfo("Navigating to Production Home page and creating new production set");
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
			baseClass.failedStep("Generate Load File is not enabled by default  for PDF components.");
		} else {
			Assert.assertTrue(true);
			baseClass.passedStep("Generate Load File is  enabled by default  for PDF components.");
		}
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47821
	 * @Description:To verify Production Generation for NATIVE/PDF/TIFF/Text
	 */

	@Test(description="RPMXCON-47821",enabled = true, groups = { "regression" } )
	public void verifyProductionWithPriviledgedDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47821- Production Sprint 09");
		baseClass.stepInfo("To verify Production Generation for NATIVE/PDF/TIFF/Text");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
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

		baseClass.passedStep("verified Production Generation for NATIVE/PDF/TIFF/Text");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/03/22 TESTCASE No:RPMXCON-48532
	 * @Description:To verify that confirmation message is displays if Blank Page
	 *                 Removal option is enable.
	 */
	@Test(description="RPMXCON-48532",enabled = true, groups = { " regression" } )
	public void verifyConfirmationMessageWithBlankPageEnabled() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48532- Production Sprint 09");
		baseClass.stepInfo("To verify that confirmation message is displays if Blank Page Removal option is enable.");
		UtilityLog.info(Input.prodPath);
		String expected = "Enabling Blank Page Removal doubles the overall production time. Are you sure you want to continue?";

		baseClass.stepInfo("Navigating to Production Home page and creating new production set");
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
		baseClass.passedStep("verified that confirmation message is displays if Blank Page Removal option is enabled");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:01/03/22 TESTCASE No:RPMXCON-48531
	 * @Description:To verify that if Blank Page Removal toggle is ON then it should
	 *                 produced Tiff without blank pages
	 */
	@Test(description="RPMXCON-48531",enabled = true, groups = { " regression" } )
	public void generateProductionWithBlankPageEnabled() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48531- Production Sprint 09");
		baseClass.stepInfo(
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
	@Test(description="RPMXCON-58562",enabled = true, groups = { " regression" } )
	public void verifySameNameForLoadFilesAndExportName() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-58562- Production Sprint 10");
		baseClass.stepInfo("Verify the name of load files should be used the name of the Export");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);

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
		baseClass.passedStep("Verified the name of load files should be used the name of the  Export");

		// To delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname,Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-55927
	 * @Description:Verify 'Placeholders' section in Tiff/PDF components
	 */
	@Test(description="RPMXCON-55927",enabled = true, groups = { " regression" } )
	public void verifyPlaceholdersInTIFF() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55927- Production Sprint 10");
		baseClass.stepInfo("Verify 'Placeholders' section in Tiff/PDF components");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		baseClass.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getPriveldge_TextArea());
		baseClass.waitForElement(page.getPriveldge_TextArea());

		if (page.getPriveldge_TextArea().isDisplayed()) {
			baseClass.passedStep("Verified 'Placeholders' section in Tiff/PDF components");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-55925
	 * @Description:Verify Native section in Production Components section
	 */
	@Test(description="RPMXCON-55925",enabled = true, groups = { " regression" } )
	public void verifyNativeSectionComponent() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55925- Production Sprint 10");
		baseClass.stepInfo("Verify Native section in Production Components section");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		productionname = " p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingNativeSection();
		if (page.nativeSectionBlueText().isDisplayed()) {
			baseClass.passedStep("Verified Native section in Production Components section");
		}
		loginPage.logout();
	}
	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56008
	 * @Description:Verify that user can download the production by using the
	 *                     Shareable link for 'DAT Only'
	 */
	@Test(description="RPMXCON-56008",enabled = true, groups = { " regression" } )
	public void verifySharableLinkForDAT() throws Exception {
		baseClass.stepInfo("Test case Id RPMXCON-56008- Production Sprint 10");
		baseClass.stepInfo("Verify that user can download the production by using the Shareable link for 'DAT Only'");
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
		baseClass.waitTime(3);
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String downloadsHome = "C:\\BatchPrintFiles\\downloads";
		String home = System.getProperty("user.home");
		page.isFileDownloaded(downloadsHome, name);
		baseClass.passedStep(
				"Verified that user can download the production by using the Shareable link for 'DAT Only'");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56015
	 * @Description:Verify that on if user paste the sharable link and gives the
	 *                     correct password then it should download the zip file
	 */
	@Test(description="RPMXCON-56015",enabled = true, groups = { " regression" } )
	public void verifySharableLinkByCorrectPassword() throws Exception {
		baseClass.stepInfo("Test case Id RPMXCON-56015- Production Sprint 10");
		baseClass.stepInfo(
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
	@Test(description="RPMXCON-56014",enabled = true, groups = { " regression" } )
	public void verifyErrorInSharableLinkByIncorrectPassword() throws Exception {
		baseClass.stepInfo("Test case Id RPMXCON-56014- Production Sprint 10");
		baseClass
				.stepInfo("Verify that error should displays if user paste the shareable link with incorrect password");
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
	@Test(description="RPMXCON-56013",enabled = true, groups = { " regression" } )
	public void verifyErrorInSharableLinkWithInvalidURL() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-56013- Production Sprint 10");
		baseClass
				.stepInfo("Verify that error should displays if user paste the shareable link with incorrect password");
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
	@Test(description="RPMXCON-48326",enabled = true, groups = { " regression" } )
	public void verifyToggleOffForGenerateLoadFile() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48326- Production Sprint 10");
		baseClass.stepInfo(
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
		baseClass.clickButton(page.getAdvancedProductionComponent());
		driver.waitForPageToBeReady();
		page.fillingMP3();
		driver.scrollingToBottomofAPage();
		page.getAdvancedTabInMP3().waitAndClick(10);
		baseClass.clickButton(page.getMp3GenerateLoadFile());
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
	@Test(description="RPMXCON-48186",enabled = true, groups = { " regression" } )
	public void verifyProductionNameAndStatus() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48186- Production Sprint 10");
		baseClass.stepInfo("To Verify Generate Section for Production Name and Status.");
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
			baseClass.passedStep("status enabled as:" + draftStatus);
		}

		if (page.getbtnProductionGenerate().isDisplayed()) {
			baseClass.passedStep("Generate Button is enabled");
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
	@Test(description="RPMXCON-48145",enabled = true, groups = { " regression" } )
	public void verifyCheckboxInDAT() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48145- Production Sprint 10");
		baseClass.stepInfo(
				"To Verify Redaction Check box along with Privilege Check box, In Generated DAT of Production.");
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
		baseClass.stepInfo("Redaction checbox is selected in DAT component");
		page.getDATPrivledgedCheckbox().waitAndClick(10);
		baseClass.stepInfo("Privileged checbox is selected in DAT component");
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
	@Test(description="RPMXCON-47822",enabled = true, groups = { " regression" } )
	public void verifyRedactedCountOnSummaryTab() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47822- Production Sprint 10");
		baseClass.stepInfo("To verify Redacted Document count should get displayed on Summary & Preview tab");
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
			baseClass.passedStep("Recated count is not zero as per the pre requisite");
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
	@Test(description="RPMXCON-47823",enabled = true, groups = { " regression" } )
	public void verifyBatesNumberAsZero() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47823- Production Sprint 10");
		baseClass.stepInfo("To verify Bates Number Generated in Production can be start with {0}.");
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
	@Test(description="RPMXCON-47844",enabled = true, groups = { " regression" } )
	public void createTemplateWithNewProductionSet() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47844- Production Sprint 10");
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		templateName = "templateName" + Utility.dynamicNameAppender();
		productionSet = "productionSet" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		baseClass.stepInfo("Searching for a content and performing bulk folder action");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		baseClass.stepInfo("Navigating to Production Home page and creating new production set");
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

		baseClass.stepInfo("Deleting the tags and folders after the production gets completed");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/18/22 TESTCASE No:RPMXCON-47845
	 * @Description:To Verify the View of the Custom Template
	 */
	@Test(description="RPMXCON-47845",enabled = true, groups = { " regression" } )
	public void verifyCustomTemplate() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47845- Production Sprint 10");
		baseClass.stepInfo("To Verify the View of the Custom Template");
		UtilityLog.info(Input.prodPath);

		templateName = "templateName" + Utility.dynamicNameAppender();
		productionSet = "productionSet" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Navigating to Production Home page and creating new production set");
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
	@Test(description="RPMXCON-55696",enabled = true, groups = { " regression" } )
	public void verifySortingInProduction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55696- Production Sprint 10");
		baseClass.stepInfo(
				"To Verify Sorting configured in the production is being honored by the generated production");
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
		baseClass.stepInfo("Sorting configured in the production");
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
	@Test(description="RPMXCON-55696",enabled = true, groups = { " regression" } )
	public void verifyProductionComponents() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55696- Production Sprint 10");
		baseClass.stepInfo(
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
		baseClass=new BaseClass(driver);
		baseClass.VerifySuccessMessage("Mark Complete successful");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/20/22 TESTCASE No:RPMXCON-47925
	 * @Description:To Verify in Natives section of Productions, Product Native
	 *                 Files for select file types are to being honored by the
	 *                 production
	 */
	@Test(description="RPMXCON-47925",enabled = true, groups = { " regression" } )
	public void verifyNativesForSelectedFiles() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47925- Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.stepInfo("Sorting configured in the production");
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
	@Test(description="RPMXCON-47927",enabled = true, groups = { " regression" })
	public void verifySlipSheetInProduction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47927- Production Sprint 11");
		baseClass.stepInfo("To Verify ProductionBatesRange in production slip sheet.");
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
		baseClass.stepInfo("Slip Sheet is enabled and ProductionBatesRange checkbox is selected");
		driver.waitForPageToBeReady();
		page.getAddSelected().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		baseClass.stepInfo("Sorting configured in the production");
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
	@Test(description="RPMXCON-48269",enabled = true, groups = { " regression" } )
	public void verifyDATFieldName() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48269- Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.stepInfo("BN is passed in DAT field");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		baseClass.stepInfo("Sorting configured in the production");
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
	@Test(description="RPMXCON-48144",enabled = true, groups = { " regression" } )
	public void verifyRedactionCheckboxInDAT() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48144- Production Sprint 11");
		baseClass.stepInfo("To Verify Redaction check box under DAT Section in Production Component Section.");
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
		baseClass.stepInfo("Redaction checkbox is selected in DAT components");
		page.fillingTIFFSectionwithBurnRedaction(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		baseClass.stepInfo("Sorting configured in the production");
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
	@Test(description="RPMXCON-47902",enabled = true, groups = { " regression" } )
	public void verifyOverwriteDocumentFromProduction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47902- Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.stepInfo("In progress productions are filtered and In progress production from homepage is selected");
		page.getQC_backbutton().waitAndClick(10);
		for (int i = 0; i < 4; i++) {
			page.getBckBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		page.getMarkIncompleteButton().waitAndClick(10);
		page.fillingSelectDocumentUsingTags(tagname);
		baseClass.stepInfo("Documents overwritted by selecting from tags");
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.stepInfo("Production generated successfully by overwritting documents");
		
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
	@Test(description="RPMXCON-48572",enabled = true, groups = { " regression" } )
	public void verifyBatesRangeAfterProduction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48572- Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.passedStep("Bates Range is displayed in generate page");

		
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
	@Test(description="RPMXCON-47897",enabled = true, groups = { " regression" } )
	public void verifyDocumentSelectionWithFolder() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47897- Production Sprint 11");
		baseClass.stepInfo("To Verify Document Selection Section on the self production wizard For Folder");
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
			baseClass.passedStep(
					"Total Documents selected is displayed correctly on the self production wizard For Folder");
		}

		else {
			baseClass.failedMessage(
					"Total Documents selected is not displayed on the self production wizard For Folder");
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
	@Test(description="RPMXCON-47892",enabled = true, groups = { " regression" } )
	public void verifyNumberingAndSorting() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47892- Production Sprint 11");
		baseClass.stepInfo("To Verify numbering and sorting Section on the self production wizard for Numbering");
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
			baseClass.passedStep("Page Radio button is selected by default");
		} else {
			baseClass.failedMessage("Page Radio button is not selected by default");
		}
		page.getNumbering_Document_RadioButton().waitAndClick(10);

		if (page.getBeginningSubBatesNumber().GetAttribute("value").contains("1")) {
			baseClass.passedStep("Beginning bates number is 1 by default");
		} else {
			baseClass.failedMessage("Beginning bates number is not 1 by default");
		}
		if (page.getBeginningSubBatesNumber().GetAttribute("value").contains("5")) {
			baseClass.passedStep("Minimum length number is 5 by default");
		} else {
			baseClass.failedMessage("Minimum length number is not 5 by default");
		}

		page.pageRadioButton().ScrollTo();
		page.pageRadioButton().waitAndClick(10);

		if (page.specifyBatesNumbering().Selected()) {
			baseClass.passedStep("specify bates numbering is selected by default");
		} else {
			baseClass.failedMessage("specify bates numbering is not selected  by default");
		}

		if (page.beginningBatesInFormat().GetAttribute("value").contains("0")) {
			baseClass.passedStep("Beginning bates is 0 by default");
		} else {
			baseClass.failedMessage("Beginning bates is not 0 by default");
		}

		page.getNumbering_Document_RadioButton().ScrollTo();
		page.getNumbering_Document_RadioButton().waitAndClick(10);
		page.useMetadataFied().ScrollTo();
		page.useMetadataFied().waitAndClick(10);

		if (page.getExportPrefixId().isDisplayed() && page.getExportSuffixId().isDisplayed()) {
			baseClass.passedStep("Prefix and suffix is displayed in user metadata field");
		}

		else {
			baseClass.failedMessage("Prefix and suffix is not displayed in user metadata field");
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
	@Test(description="RPMXCON-48506",enabled = true, groups = { "regression" } )
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

		baseClass.stepInfo("Test case Id: RPMXCON-48506 Production Sprint 11");
		baseClass.stepInfo(
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

		baseClass.waitTime(4);
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
	@Test(description="RPMXCON-49362",enabled = true, groups = { " regression" } )
	public void verifyComponentTabWithoutAnyError() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49362- Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.stepInfo("completed productions are filtered and In progress production from homepage is selected");
		page.getQC_backbutton().waitAndClick(10);
		for (int i = 0; i < 6; i++) {
			page.getBckBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		page.getMarkIncompleteButton().waitAndClick(10);
		if (page.getNativeTab().Selected()) {
			baseClass.passedStep("Native section is selected by default");
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
	@Test(description="RPMXCON-48507",enabled = true, groups = { "regression" } )
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

		baseClass.stepInfo("Test case Id: RPMXCON-48507 Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.waitTime(2);
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
	@Test(description="RPMXCON-48334",enabled = true, groups = { " regression" } )
	public void verifyDATWithRedactionsCheckboxForAudioFiles() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48334- Production Sprint 11");
		baseClass.stepInfo("To Verify in Priv Guard View in Doclist and DocView.");
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
		baseClass.stepInfo("Redaction checkbox is selected in DAT component");
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

		baseClass.waitTime(4);
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
	@Test(description="RPMXCON-47904",enabled = true, groups = { " regression" } )
	public void verifyProductionStatusInTileView() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47904- Production Sprint 11");
		baseClass.stepInfo(
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
		
		baseClass.stepInfo("Checking availability of In progress status production ");
		if (page.productionNameInHomePage().Displayed()) {
			baseClass.passedStep("production in In Progress Status Displayed");
		} else {
			baseClass.failedStep("production in In Progress Status not Displayed");
		}

		driver.Navigate().refresh();

		baseClass.stepInfo("Checking availability of Completed status production ");
		if (page.productionNameInHomePage().Displayed()) {
			baseClass.passedStep("production in Completed Status Displayed");
		} else {
			baseClass.failedStep("production in Completed Status not Displayed");
		}

		driver.Navigate().refresh();

		baseClass.stepInfo("Checking availability of Failed status production ");
		page.gettxtProdGenerationFailed();
		if (page.productionNameInHomePage().Displayed()) {
			baseClass.passedStep("production in failed Status Displayed");
		} else {
			baseClass.failedStep("production in failed Status not Displayed");
		}
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-47979
	 * @Description:To Verify OCRing of Text component of the production, only for Redacted Documents when "OCR non-redacted docs... " option is selected in Production-text component
	 */
	@Test(description="RPMXCON-47979",enabled = true, groups = { " regression" })
	public void verifyTextFileComponentInGeneratedProduction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47979- Production ");
		baseClass.stepInfo(
				"To Verify OCRing of Text component of the production, only for Redacted Documents when \"OCR non-redacted docs... \" option is selected in Production-text component");
		UtilityLog.info(Input.prodPath);
		
		tagname = "Tag" + Utility.dynamicNameAppender();
        foldername="Folder"+Utility.dynamicNameAppender();
        String prefixID = "A_" + Utility.dynamicNameAppender();
    	String suffixID = "_P" + Utility.dynamicNameAppender();
    	
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
		
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.waitTime(2);
		BaseClass baseClass=new BaseClass(driver);
		baseClass.impersonatePAtoRMU();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();
		
		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage doc= new DocViewPage(driver);
		doc.getDocView_MiniDoc_Selectdoc(4).waitAndClick(5);
		doc.getDocView_MiniDoc_Selectdoc(5).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10,100,100);
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
		String home= System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.extractFile();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		File TiffFile = new File(home + "/Downloads/" + "VOL0001/Load Files/" + name + "_TIFF.OPT");
		File TextFile=new File(home + "/Downloads/VOL0001/Text/0001");
		if(TextFile.exists()) {
			baseClass.passedStep("Text file generated successfully");
		}else {
			baseClass.failedStep("Text document is not generated successfully");
		}

		if (TiffFile.exists()) {
			baseClass.passedStep("Tiff is generated successfully");
		} else {
			baseClass.failedStep("Tiff is not generated successfully");
		}
		if (DatFile.exists()) {
			baseClass.passedStep("Dat file is displayed as expected");
		} else {
			baseClass.failedStep("Dat file is not displayed as expected");
		}
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
		
	}

	//64 cases
	

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			//loginPage.logoutWithoutAssert();
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
