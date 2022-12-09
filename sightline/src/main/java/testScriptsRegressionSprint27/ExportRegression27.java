package testScriptsRegressionSprint27;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ExportRegression27 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	ProductionPage page;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
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
		base = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50672
	 * @Description: Verify that after Reserving Bates Range completed it should
	 *               displays 'Reserving Bates Range Complete' status on Grid View
	 *               on Production-export Home page
	 **/
	@Test(description = "RPMXCON-50672", enabled = true, groups = { "regression" })
	public void verifyReservingBatesRangeCompleteStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50672");
		base.stepInfo(
				"Verify that after Reserving Bates Range completed it should displays 'Reserving Bates Range Complete' status on Grid View on Production-export Home page");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as"+Input.pa1userName);
		
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Reserving Bates Range completed status in grid view");
		page.verifyProductionStatusInHomePageGridView("Reserving Bates Range Complete", exportName);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50333
	 * @Description:Verify that Production-Export status displays in Draft with
	 *                     blank progress bar on Tile View
	 **/
	@Test(description = "RPMXCON-50333", enabled = true, groups = { "regression" })
	public void verifyDraftStatusInTileView() throws Exception {
		
		base.stepInfo("Test case Id: RPMXCON-50333");
		base.stepInfo("Verify that Production-Export  status displays in Draft with blank progress bar on Tile View");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo(Input.pa1userName + " logged in to sightline successfully");

		String exportName = "Export" + Utility.dynamicNameAppender();

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportAndSave(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();

		page.navigateToProductionPage();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();

		base.stepInfo("verifying Draft status in Tile view");
		base.waitTime(2);
		String ExpStatus = page.getProductionFromHomePage(exportName).getText();
		System.out.println(ExpStatus);
		if (ExpStatus.equals("")) {
			base.passedStep("blank progress bar is displayed as expected");
		} else {
			base.failedStep("Blank progress bar is not displayed");
		}
		base.ValidateElement_Presence(page.getDraftState(exportName), "DRAFT State");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-49256
	 * @Description:To verify that in Export, 'Click here to View and select the
	 *                 bates number(S)' link should not be shown
	 **/
	@Test(description = "RPMXCON-49256", enabled = true, groups = { "regression" })
	public void verifyInVisiblityOfClickHerelink() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49256");
		base.stepInfo(
				"To verify that in Export, 'Click here to View and select the bates number(S)' link should not be shown");
		String exportName = "Export" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();

		base.stepInfo("verifying Click here link is not shown");
		if (!page.getClickHereLink().isDisplayed()) {
			base.passedStep("Click here to View and select the bates number(S)' link is not displayed");
		} else {
			base.failedStep("Click here to View and select the bates number(S)' link is displayed");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50675
	 * @Description:Verify that once Generation is completed it should displays
	 *                     'Export File Complete' status on Grid View on
	 *                     Production-Export Home page
	 **/
	@Test(description = "RPMXCON-50675", enabled = true, groups = { "regression" })
	public void verifyExportFileCompleteStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50675");
		base.stepInfo(
				"Verify that once Generation is completed it should displays 'Export File Complete' status on Grid View on Production-Export Home page");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGenerate().isElementAvailable(3);
		if (page.getbtnContinueGenerate().isDisplayed()) {
			page.getbtnContinueGenerate().waitAndClick(2);
		}
		driver.Navigate().refresh();
		page.navigateToProductionPage();
		base.waitTime(1);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		base.waitForElement(page.getGridView());
		page.getGridView().waitAndClick(10);
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Export File Complete status in Grid view");
		page.verifyProductionStatusInHomePageGridView("Exporting Files Complete", exportName);
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50680
	 * @Description:Verify that in Production-export components, TIFF/PDF section
	 *                     displays options for Generating TIFF or Generating PDF
	 **/
	@Test(description = "RPMXCON-50680", enabled = true, groups = { "regression" })
	public void verifyGenerateoptionInComponentTab() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50680");
		base.stepInfo(
				"Verify that in Production-export components, TIFF/PDF section displays options for Generating TIFF or Generating PDF");
		String exportName = "Export" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("verifying Generate TIFF and PDF option in Component tab");
		base.ValidateElement_Presence(base.text("Generate TIFF"), "Generating TIFF option");
		base.ValidateElement_Presence(base.text("Generate PDF"), "Generating PDF option");
		base.stepInfo("Verifying  Generate TIFF radio button in Component tab");
		page.getCheckBoxCheckedVerification(page.getGenrateTIFFRadioButton());

		if (page.getTiffContainer("Generate TIFF").isDisplayed()&&page.getTiffContainer("Generate PDF").isDisplayed()) {
			base.passedStep("Separate PDF section is not displayed in Component section.");
		} else {
			base.failedStep("Separate PDF section is displayed in Component section.");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-47803
	 * @Description:To Verify Changes in Basic Info Page ( for Export)
	 **/
	@Test(description = "RPMXCON-47803", enabled = true, groups = { "regression" })
	public void verifyingBasicInfoTab() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47803");
		base.stepInfo("To Verify Changes in Basic Info Page ( for Export)");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		
		base.waitForElement(page.getAddNewExport());
		page.getAddNewExport().Click();
		
		driver.waitForPageToBeReady();
		base.stepInfo("verifying Basic info label");
		if(page.getBasicInfoLabel().isDisplayed()) {
			base.passedStep("Basic info label is displayed");
		}else {
			base.failedStep("Basic info label is not displayed");
		}
		
		
		base.stepInfo("verifying the field like Name,Description and Template");
		base.ValidateElement_Presence(base.text("Name"),"Name Field");
		base.ValidateElement_Presence(page.getProductionName(),"Name Field");
		
		base.ValidateElement_Presence(base.text("Description"),"Description Field");
		base.ValidateElement_Presence(page.getProductionDesc(),"Description Field");
		
		base.ValidateElement_Presence(base.text("Template"),"Template Field");
		base.ValidateElement_Presence(page.getprod_LoadTemplate(),"Template Field");
		
		
		base.stepInfo("verifying toggle option and dropdown option");
		base.validatingGetTextElement(page.getToggleOptionText(),"Do you want to export prior production versions?");
		base.ValidateElement_Presence(page.getDropdownoptionText(),"Select Production Text");
		
		base.stepInfo("verifying the availability of Save,Markcomplete and Next Button");
		base.elementDisplayCheck(page.getSaveButton());
		base.elementDisplayCheck(page.getMarkCompleteLink());
		base.elementDisplayCheck(page.getNextButton());
		loginPage.logout();
	}
	
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50689
	 * @Description:Verify the error message for DAT component when 'In export don’t select DocID and Bate Bumber data field'
	 **/
	@Test(description = "RPMXCON-50689", enabled = true, groups = { "regression" })
	public void verifyErrorMsgInDATSection() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50689");
		base.stepInfo("Verify the error message for DAT component when 'In export don’t select DocID and Bate Bumber data field'");
		String exportName = "Export" + Utility.dynamicNameAppender();
		String BatesNum = "Bates" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSectionWithBates(Input.bates,"ChildBates",BatesNum);
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();
		base.stepInfo("verifying error message in DAT section");
		base.VerifyErrorMessage("Either Bates Number or DocID must be selected in the DAT for an export.");
		
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
		System.out.println("******TEST CASES FOR EXPORT EXECUTED******");

	}
}
