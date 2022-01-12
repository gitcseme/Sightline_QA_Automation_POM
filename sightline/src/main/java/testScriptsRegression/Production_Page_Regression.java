package testScriptsRegression;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
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
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String productionname;
	String batesNumber;
	String productionSet;
	String templateName;
	String exportname;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55991
	 * @Description:Verify that if Production is regenerated then status should be
	 *                     change on Production progress status bar on Tile View
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 1)
	public void verifyStatusAfterRegeneratingProductionInHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55991- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		driver.waitForPageToBeReady();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Preparing Data", productionname);
		page.verifyProductionStatusInHomePage("Pre-Gen Checks", productionname);
		page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);

		// Clicking continue generation button in generate page
		baseClass.waitForElement(page.getProductionFromHomePage(productionname));
		page.getProductionFromHomePage(productionname).waitAndClick(10);
		baseClass.waitForElement(page.getbtnContinueGeneration());
		page.getbtnContinueGeneration().waitAndClick(10);

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Prod Generation ", productionname);
		page.verifyProductionStatusInHomePage("Generating Load Files", productionname);
		page.verifyProductionStatusInHomePage("Exporting Files ", productionname);
		page.verifyProductionStatusInHomePage("Creating Archive", productionname);
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55992
	 * @Description:Verify that if Producion is regnerated then status should be
	 *                     change on Production progress status bar on Generate page
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 2)
	public void verifyStatusAfterRegeneratingProductionInGeneratePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55992- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56087
	 * @Description:Verify that Production should generate successfully by selecting
	 *                     only DAT and 'Generate TIFF' option with TechIssue
	 *                     Placeholder
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 3)
	public void generateTIFFWithTechIssue() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56087- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		page.fillingTiffSectionTechIssueWithEnteringText(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		baseClass.passedStep("Generated TIFF with Tech Issue Placeholder by selecting only DAT");

		// Delete Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56089
	 * @Description:Verify that Production should generate successfully by selecting
	 *                     only DAT and 'Generate TIFF' option with Natively
	 *                     Produced Documents Placeholder
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 4)
	public void generateTIFFWithNativelyPlaceholder() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56089- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56093
	 * @Description:Verify the error message for DAT component when 'Don't add any
	 *                     data field '
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 5)
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

	}

	/**
	 * @Author Sowndarya.Velraj created on:11/25/21 TESTCASE No:RPMXCON-56092
	 * @Description:Verify the error message for DAT component when 'Add same data
	 *                     field more than one time'
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 6)
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
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56098
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     with different source field and same DAT field name'
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 7)
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
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56097
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     with special character'
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 8)
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
	}

	/**
	 * @Author Sowndarya.Velraj created on:11/30/21 TESTCASE No:RPMXCON-56096
	 * @Description:Verify the error message for DAT component when 'Add data field
	 *                     other that alphabet character'
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 9)
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
	}

	/**
	 * @Author Sowndarya.Velraj created on:12/01/21 TESTCASE No:RPMXCON-56094
	 * @Description:Verify the error message for DAT component when 'Don't add bate
	 *                     number data field'
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 10)
	public void verifyErrorMessageInDATByNotAddingBates() throws Exception {

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
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.Production);

		baseClass.waitForElement(page.getDAT_SourceField1());
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.TIFFPageCount);

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
	}

	/**
	 * @Author Sowndarya.Velraj created on:12/01/21 TESTCASE No:RPMXCON-56095
	 * @Description:Verify the error message for DAT component when 'don't select
	 *                     DAT component'
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 11)
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
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56044
	 * @Description:Verify that once Generation is completed it should displays
	 *                     'Prod Generation Complete' status in Grid View on
	 *                     Production Home page
	 */

	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 12)
	public void verifyPostGenCompleteStatusInGridView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56044- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);
		baseClass.passedStep(
				" Generation is completed and displays 'Prod Generation Complete' status in Grid View on Production Home page");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/2/21 TESTCASE No:RPMXCON-56042
	 * @Description:Verify that it should displays 'Pre-Gen Checks - 19999/20000
	 *                     docs' status on Grid View in Production Home page
	 *                     TESTCASE No:RPMXCON-56036
	 * @Description:Verify that after Pre-gen checks is in progress, it will
	 *                     displays status on Production Grid view
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 12)
	public void verifyPreGenChecksInHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56042- Production Sprint 07");
		baseClass.stepInfo("Test case Id: RPMXCON-56036- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks", productionname);
		baseClass.passedStep(
				" Generation is completed and displays 'Pre-Gen Checks - 19999/20000 docs' status in Grid View on Production Home page");

//		loginPage.logout();
//		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	}

	/**
	 * @author Sowndarya.Velraj created on:12/3/21 TESTCASE No:RPMXCON-56039
	 * @Description:Verify that after Pregen check completed it should displays
	 *                     'Reserving Bates Range' status on Grid View on Production
	 *                     Home page
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 13)
	public void verifyReservingBatesRangeInGridView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56039- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);
		baseClass.passedStep(" 'Reserving Bates Range' status on Grid View on Production Home page");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/3/21 TESTCASE No:RPMXCON-56038
	 * @Description:Verify that after Pregen checks completed it should displays
	 *                     'Pre-Gen Checks Complete' status on Production Grid View
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { "regression" }, priority = 14)
	public void verifyPreGenChecksInGridView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56038- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks Complete", productionname);
		baseClass.passedStep(" verified 'Pre-Gen Checks Complete 'status on Grid View on Production Home page");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-56016
	 * @Description:Verify that after Pregen checks completed it should displays
	 *                     'Pre-Gen Checks Complete' status on Progress bar in Tile
	 *                     View on Production Home page
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { " regression" }, priority = 15)
	public void verifyPreGenChecksCompleteInHomePage() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-56016- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		this.driver.getWebDriver().get(Input.url + " TagsAndFoldersTagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, " Default Security Group");
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

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Going to Home Page");
		this.driver.getWebDriver().get(Input.url + " ProductionHome");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks Complete", productionname);
		baseClass.passedStep("verified 'Pre-Gen Checks Complete 'status on  Production Home page");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-55985
	 * @Description:Verify that once Post Geneation is in progress, it will displays
	 *                     status on Production Generation page as 'Post-Gen QC
	 *                     checks In Progress'
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { " regression" }, priority = 16)

	public void verifyPostGenQCchecksInProgressInGeneratePage() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55985- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		this.driver.getWebDriver().get(Input.url + " TagsAndFoldersTagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, " Default Security Group");
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

		baseClass.waitForElement(page.getbtnContinueGeneration());
		page.getbtnContinueGeneration().waitAndClick(10);

		page.verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");
		baseClass.passedStep(
				" Generation is completed and displays Post-Gen QC Checks In Progress' status Generation page");

	}

	/**
	 * @author Sowndarya.Velraj created on:12/10/21 TESTCASE No:RPMXCON-55984
	 * @Description:Verify that once Post Generation is in progress, it will
	 *                     displays status on Production Progress bar ,Tile View as
	 *                     'Post-Gen QC Checks In Progress'
	 */
	@Test(enabled = false, dataProvider = "Users", groups = { " regression" }, priority = 17)
	public void verifyPostGenQCchecksInProgressInTileView() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-55984- Production Sprint 07");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		this.driver.getWebDriver().get(Input.url + " TagsAndFoldersTagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, " Default Security Group");
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

		baseClass.waitForElement(page.getbtnContinueGeneration());
		page.getbtnContinueGeneration().waitAndClick(10);

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Going to Home Page");
		this.driver.getWebDriver().get(Input.url + "ProductionHome");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Post-Generation QC Checks In Progress", productionname);
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);
		baseClass.passedStep(
				" Post Generation is in progress, it will displays status on Production Progress bar ,Tile View as 'Post-Gen QC Checks In Progress'");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/14/21 TESTCASE No:RPMXCON-48849
	 * @Description:Verify that user can download only Produced DAT file by
	 *                     selecting 'Download DAT file'
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 18)
	public void verifyDownloadDATFile() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48849- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
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
		baseClass.VerifySuccessMessage("Your Production DAT Archive download will get started shortly");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/14/21 TESTCASE No:RPMXCON-48664
	 * @Description:Run Production by selecting components like DAT,TIFF,NATIVE and
	 *                  with selection of multiple tags with audio files
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 19)
	public void verifyAudioFilesWithMultipleBranding() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48664- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
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
		page.fillingTiffSectionBranding();
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
		baseClass.passedStep(
				"Production generated  by selecting components like DAT,TIFF,NATIVE and with selection of multiple tags with audio files");

	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49250
	 * @Description:To verify In Productions DAT, provide the TIFFPageCount for each
	 *                 document should be zero when only DAT component is selected
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 20)
	public void verifyDocumentCountForDAT() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49250- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

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

	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49251
	 * @Description:To verify In Productions DAT, TIFFPageCount for each document
	 *                 should be displayed when production is done with any
	 *                 component
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 21)
	public void verifyDocumentCountForDATWithOtherComponent() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49250- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

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

	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49106
	 * @Description:To verify the count 'Total Documents: ' on Production Summary
	 *                 page
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 22)
	public void verifyTotalPagesOnSummary() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49106- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

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
		page.fillingTiffSectionBranding();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String expected = page.getValueTotalDocuments().getText();
		baseClass.passedStep("Total Documents:" + expected);

	}

	/**
	 * @author Sowndarya.Velraj created on:12/23/21 TESTCASE No:RPMXCON-49107
	 * @Description:To verify that Total Pages count on Production-Summary page
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 23)
	public void verifyTotalPagesCountOnSummary() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-49107- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

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
		page.fillingTiffSectionBranding();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String expected = page.getValueTotalPagesCount().getText();
		baseClass.passedStep("Total Page count is:" + expected);

	}

	/**
	 * @author Sowndarya.Velraj created on:12/26/21 TESTCASE No:RPMXCON-48505
	 * @Description:Verify that Tiff should generate with Burned Redaction if Only
	 *                     Burn Redaction is enabled
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 24)
	public void verifyTiffWithBurnRedaction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48505- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tag = "Default Redaction Tag";

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

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

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");

	}

	/**
	 * @author Sowndarya.Velraj created on:12/26/21 TESTCASE No:RPMXCON-47791
	 * @Description:To Verify appropriate display of data is occurring in 'Summary &
	 *                 Preview' tab.
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 25)
	public void verifySummaryAndPreview() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47791- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
//		String tag = "Default Redaction Tag";
		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

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
		page.fillingTiffSectionBranding();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();

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

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/27/21 TESTCASE No:RPMXCON-48502
	 * @Description:To verify that if annotation layer option is selected in PDF
	 *                 section and document is redacted then selected Metadata
	 *                 should not be displayed on DAT
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 26)
	public void verifyPDFWithAnnotationLayer() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48502- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

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
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/27/21 TESTCASE No:RPMXCON-48503
	 * @Description:To verify that if annotation layer option is selected in MP3
	 *                 section and Audio is redacted then selected Metadata should
	 *                 not be displayed on DATF
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 27)
	public void verifyMP3WithAnnotationLayer() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48503- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

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
//		page.fillingTheTIFFSection();
		page.fillingMP3();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		baseClass.stepInfo("Deleting the tags and folders after the production gets completed");
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/28/21 TESTCASE No:RPMXCON-47844
	 * @Description:To Verify the Create/Display of Template with newly created
	 *                 Project and Production Set.
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 28)
	public void createTemplateWithNewProductionSet() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47844- Production Sprint 08");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		templateName = "templateName" + Utility.dynamicNameAppender();
		productionSet = "productionSet" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

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

		driver.getWebDriver().get(Input.url + "Production/Home");
		page.selectingDefaultSecurityGroup();
		page.navigateToProductionPageByNewProductionSet(productionSet);
		page.prodGenerationInCompletedStatus(productionname);
		page.saveProductionAsTemplateAndVerifyInManageTemplateTab(productionname, templateName);

		baseClass.stepInfo("Deleting the tags and folders after the production gets completed");
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	}

	/**
	 * @author Sowndarya.Velraj created on:12/30/21 TESTCASE No:RPMXCON-48322
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 TIFF components.
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 29)
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

	}

	/**
	 * @author Sowndarya.Velraj created on:12/30/21 TESTCASE No:RPMXCON-48323
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 PDF components.
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 30)
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

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47821
	 * @Description:To verify Production Generation for NATIVE/PDF/TIFF/Text
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 31)
	public void verifyProductionWithPriviledgedDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47821- Production Sprint 09");
		baseClass.stepInfo("To verify Production Generation for NATIVE/PDF/TIFF/Text");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating tags and folders in Tags/Folders Page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");

		baseClass.passedStep("verified Production Generation for NATIVE/PDF/TIFF/Text");
	}

	/**
	 * @author Sowndarya.Velraj created on:01/03/22 TESTCASE No:RPMXCON-48532
	 * @Description:To verify that confirmation message is displays if Blank Page
	 *                 Removal option is enable.
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 32)
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

	}

	/**
	 * @author Sowndarya.Velraj created on:01/03/22 TESTCASE No:RPMXCON-48531
	 * @Description:To verify that if Blank Page Removal toggle is ON then it should
	 *                 produced Tiff without blank pages
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 33)
	public void generateProductionWithBlankPageEnabled() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48531- Production Sprint 09");
		baseClass.stepInfo(
				"To verify that if Blank Page Removal toggle is ON then it should produced Tiff without blank pages");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
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
	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-58562
	 * @Description:Verify the name of load files should be used the name of the
	 *                     Export
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 34)
	public void verifySameNameForLoadFilesAndExportName() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-58562- Production Sprint 10");
		baseClass.stepInfo("Verify the name of load files should be used the name of the Export");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, " Privileged");

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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		baseClass.passedStep("Verified the name of load files should be used the name of the  Export");

		// To delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");

	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-55927
	 * @Description:Verify 'Placeholders' section in Tiff/PDF components
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 35)
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
	}

	/**
	 * @author Sowndarya.Velraj created on:01/10/22 TESTCASE No:RPMXCON-55925
	 * @Description:Verify Native section in Production Components section
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 36)
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

	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56008
	 * @Description:Verify that user can download the production by using the
	 *                     Shareable link for 'DAT Only'
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 37)
	public void verifySharableLinkForDAT() throws Exception {
		baseClass.stepInfo("Test case Id RPMXCON-56008- Production Sprint 10");
		baseClass.stepInfo("Verify that user can download the production by using the Shareable link for 'DAT Only'");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page.prodGenerationInProgressStatus();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.getQC_Download().waitAndClick(10);
		page.getClkBtnDownloadDATFiles().waitAndClick(10);
		baseClass.waitTime(3);
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String downloadsHome = "C:\\BatchPrintFiles\\downloads";
		page.isFileDownloaded(downloadsHome, name);
		baseClass.passedStep(
				"Verified that user can download the production by using the Shareable link for 'DAT Only'");

	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56015
	 * @Description:Verify that on if user paste the sharable link and gives the
	 *                     correct password then it should download the zip file
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 38)
	public void verifySharableLinkByCorrectPassword() throws Exception {
		baseClass.stepInfo("Test case Id RPMXCON-56015- Production Sprint 10");
		baseClass.stepInfo(
				"Verify that on if user paste the sharable link and gives the correct password then it should download the zip file");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page.prodGenerationInProgressStatus();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.verifyDownloadProductionUsingSharableLink();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/11/22 TESTCASE No:RPMXCON-56014
	 * @Description:Verify that error should displays if user paste the shareable
	 *                     link with incorrect password
	 */
	@Test(enabled = false, groups = { " regression" }, priority = 39)
	public void verifyErrorInSharableLinkByIncorrectPassword() throws Exception {
		baseClass.stepInfo("Test case Id RPMXCON-56014- Production Sprint 10");
		baseClass
				.stepInfo("Verify that error should displays if user paste the shareable link with incorrect password");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.prodGenerationInProgressStatus();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.verifyDownloadProductionUsingSharableLinkAndCheckErrorMessage();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/12/22 TESTCASE No:RPMXCON-56013
	 * @Description:Verify that error should be displays if user enters invalid URL
	 */
	@Test(enabled = true, groups = { " regression" }, priority = 40)
	public void verifyErrorInSharableLinkWithInvalidURL() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-56013- Production Sprint 10");
		baseClass
				.stepInfo("Verify that error should displays if user paste the shareable link with incorrect password");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.prodGenerationInProgressStatus();
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.verifyDownloadProductionUsingInvalidLink();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/12/22 TESTCASE No:RPMXCON-48326
	 * @Description:To verify that production should generate successfully if user
	 *                 disabled the 'Generate Load File'
	 */
	@Test(enabled = true, groups = { " regression" }, priority = 41)
	public void verifyToggleOffForGenerateLoadFile() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48326- Production Sprint 10");
		baseClass.stepInfo(
				"To verify that production should generate successfully if user disabled the 'Generate Load File'");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

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
		page.getAdvancedTabInText().waitAndClick(10);
		page.generateLoadFileToggleInTextComponent().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.fillingMP3();
		page.getAdvancedTabInMP3().waitAndClick(10);
		page.getMp3GenerateLoadFile().waitAndClick(10);
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
	}

	@DataProvider(name = "PAandRMU")
	public Object[][] PAandRMU() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName } };
		return users;
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
//			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PRODUCTIONS EXECUTED******");
		try {
//			loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
