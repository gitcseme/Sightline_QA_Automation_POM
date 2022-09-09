package sightline.productions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.bytebuddy.agent.builder.AgentBuilder.InitializationStrategy.SelfInjection.Split;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression18_1 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		base = new BaseClass(driver);
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	/**
	 * @author Brundha.T No:RPMXCON-48865 Date:8/02/2022
	 * @Description:To verify that generated DAT has the header with Space as
	 *                 specified in the production DAT configuration
	 **/

	@Test(description = "RPMXCON-48865", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithSpace() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48865- Production Component");
		base.stepInfo(
				"To verify that generated DAT has the header with Space as specified in the production DAT configuration");
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, Input.conceptualSearchString1);
		page.fillingNativeSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, Input.conceptualSearchString1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48866 Date:8/02/2022
	 * @Description:To verify that generated DAT has the header with Underscore as
	 *                 specified in the production DAT configuration
	 **/

	@Test(description = "RPMXCON-48866", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithUnderscore() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48866- Production Component");
		base.stepInfo(
				"To verify that generated DAT has the header with Underscore as specified in the production DAT configuration");
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "Author_Name" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNumber);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48867 Date:8/02/2022
	 * @Description:To verify that generated DAT has the header with hyphen as
	 *                 specified in the production DAT configuration
	 **/

	@Test(description = "RPMXCON-48867", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithHyphen() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48867- Production Component");
		base.stepInfo(
				"To verify that generated DAT has the header with hyphen as specified in the production DAT configuration");
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "Begin-Bates" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNumber);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:8/02/2022 TestCase Id :RPMXCON-48864 Description To
	 *         verify that user can add "hyphen" in DAT fields
	 * 
	 */
	@Test(description = "RPMXCON-48864", enabled = true, groups = { "regression" })
	public void verifyingDatFieldInComponentTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass baseClass = new BaseClass(driver);
		base.stepInfo("RPMXCON-48864 -Production Component");
		base.stepInfo("To verify that user can add 'hyphen' in DAT fields");
		String BatesNumber = "Begin-Bates" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(), "Production Page");
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.getMarkCompleteLink().Click();
		baseClass.VerifySuccessMessage("Mark Complete successful");
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		base.passedStep("verified that user can add 'hyphen' in DAT fields");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:8/04/2022 TestCase Id :RPMXCON-49042 Description To
	 *         verify that 'Last Modify Date' should contain the date when the
	 *         production was last "saved" through "Save"
	 * 
	 */

	@Test(description = "RPMXCON-49042", enabled = true, groups = { "regression" })
	public void saveProductionVerifyingModifiedDate() throws Exception {

		BaseClass base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49042 -Production Component");
		base.stepInfo(
				"To verify that 'Last Modify Date' should contain the date when the production was last 'saved' through 'Save'");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(2);
		page.getProductionName().SendKeys(productionname);
		page.getSaveOption().waitAndClick(5);

		page.navigatingToProductionHomePage();
		page.getDatFileLink(productionname).waitAndClick(2);
		page.fillingBasicInfo(productionname1);
		page.getSaveOption().waitAndClick(2);

		page.navigatingToProductionHomePage();
		page.getGridView().waitAndClick(2);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		String createdTime = page.getGridProdValues(productionname1, 11).getText();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date" + date1);
		boolean flag = createdTime.contains(date1);
		if (flag) {
			base.passedStep("current date is displayed on production grid view");
		} else {
			base.failedStep(" current date is not Displayed");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:8/04/2022 TestCase Id :RPMXCON-49043 Description:To
	 *         verify that 'Last Modify Date' should contain the date when the
	 *         production was last "saved" through "Mark Complete"
	 * 
	 */

	@Test(description = "RPMXCON-49043", enabled = true, groups = { "regression" })
	public void markCompleteProductionVerifyingLastModifiedDate() throws Exception {

		BaseClass base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49043 -Production Component");
		base.stepInfo(
				"To verify that 'Last Modify Date' should contain the date when the production was last 'saved' through 'Mark Complete'");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(2);
		page.getProductionName().SendKeys(productionname);
		page.getMarkCompleteLink().waitAndClick(5);

		page.navigatingToProductionHomePage();
		page.getDatFileLink(productionname).waitAndClick(2);
		page.getBackButton().waitAndClick(3);
		page.getMarkInCompleteBtn().waitAndClick(3);
		page.fillingBasicInfo(productionname1);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(2);
		page.navigatingToProductionHomePage();
		page.getGridView().waitAndClick(6);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		String createdTime = page.getGridProdValues(productionname1, 11).getText();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date" + date1);
		boolean flag = createdTime.contains(date1);
		if (flag) {
			base.passedStep("current date is displayed on production grid view");
		} else {
			base.failedStep("date is not contain in text");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48870 Date:8/04/2022
	 * @Description:To verify that DAT file should be generated with field having
	 *                 Space, Underscore and hyphen combinations
	 **/

	@Test(description = "RPMXCON-48870", enabled = true ,groups = { "regression" })
	public void verifyingDATFileWithHyphenSpaceAndUnderScore() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48870- Production Component");
		base.stepInfo(
				"To verify that DAT file should be generated with field having Space, Underscore and hyphen combinations");
		UtilityLog.info(Input.prodPath);
		 base = new BaseClass(driver);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String BatesNumber = "Email Author_Name-cc" + page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNumber);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48325 Date:8/04/2022
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 Translations components.
	 **/

	@Test(description = "RPMXCON-48325", enabled = true, groups = { "regression" })
	public void verifyingTranslationLSTToggle() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-48325- Production Component");
		base.stepInfo("To verify that 'Generate Load File' is enabled by default for Translations components.");
		UtilityLog.info(Input.prodPath);
		String ActualColor = "#a9c981";

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.advancedProductionComponentsTranslations();
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedArrow("last"));
		driver.scrollingToBottomofAPage();
		page.verifyingToggleEnabledOrDisabled(page.getTranslationLSTToggle("last"), ActualColor);

	}

	/**
	 * @author Brundha.T No:RPMXCON-48302 Date:8/04/2022
	 * @Description:To verify the value of 'BatesNumber' in production DAT
	 **/

	@Test(description = "RPMXCON-48302", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithBatesNumber() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48302- Production Component");
		base.stepInfo("To verify the value of 'BatesNumber' in production DAT");
				
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
        String BatesNumber="B"+Utility.dynamicNameAppender();
        
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates,Input.batesNumber,BatesNumber);
		page.fillingNativeSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 1, 1,prefixID+beginningBates+suffixID);

		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		 page = new ProductionPage(driver);
		String beginningBates1 = page.getRandomNumber(2);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.addingDatField(0, Input.bates,Input.batesNumber,BatesNumber);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String name1 = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile1 = new File(home + "/Downloads/VOL0001/Load Files/" + name1 + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile1, 1, 1,prefixID1+beginningBates1+suffixID1);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T No:RPMXCON-48936 Date:8/04/2022
	 * @Description:To verify that after clicking on Generate button from Production-Generate tab, the Generate button is disabled
	 **/

	@Test(description = "RPMXCON-48936", enabled = true, groups = { "regression" })
	public void verifyingGenerateOptionIsDisabled() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48936- Production Component");
		base.stepInfo("To verify that after clicking on Generate button from Production-Generate tab, the Generate button is disabled");
				
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
        String ActualColor = "#3276b1";
        
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().isElementAvailable(10);
		page.getbtnProductionGenerate().waitAndClick(5);
		page.verifyingToggleEnabledOrDisabled(page.getbtnProductionGenerate(),ActualColor);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
		
	}

	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
	
	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Production EXECUTED******");

	}

}
