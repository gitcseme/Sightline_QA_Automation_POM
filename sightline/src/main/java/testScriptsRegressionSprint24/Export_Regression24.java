package testScriptsRegressionSprint24;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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

public class Export_Regression24 {
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
	 * @author Brundha.T TESTCASE No:RPMXCON-50669
	 * @Description: Verify that after Pregen checks completed it should displays
	 *               'Pre-Gen Checks Complete' status on Production-Export Grid View
	 **/
	@Test(description = "RPMXCON-50669", enabled = true, groups = { "regression" })
	public void verifyPreGenCheckCompletedStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50669");
		base.stepInfo(
				"Verify that after Pregen checks completed it should displays 'Pre-Gen Checks Complete' status on Production-Export Grid View");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagName, "Select Tag Classification");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.navigateToProductionPage();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Pre-Gen Checks Complete status in Grid view");
		page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks Complete", exportName);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50646
	 * @Description: Verify that once Post Geneation is in progress, it will
	 *               displays status on Export Generation page as 'Post-Gen QC
	 *               checks in progress'
	 **/
	@Test(description = "RPMXCON-50646", enabled = true, groups = { "regression" })
	public void verifyPostGenInProgressInGenPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50646");
		base.stepInfo(
				"Verify that once Post Geneation is in progress, it will displays status on Export Generation page as 'Post-Gen QC checks in progress'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.telecaSearchString);
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
		base.stepInfo("verifying Post-Gen QC Checks In Progress status in Generate page");
		page.verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50645
	 * @Description: Verify that once Post Geneation is in progress, it will
	 *               displays status on Production Progress bar ,Tile View as
	 *               'Post-Gen checks in progress'
	 **/
	@Test(description = "RPMXCON-50645", enabled = true, groups = { "regression" })
	public void verifyPostGenCheckInProgressStatusInTileView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50645");
		base.stepInfo(
				"Verify that once Post Geneation is in progress, it will displays status on Production Progress bar ,Tile View as 'Post-Gen checks in progress'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.telecaSearchString);
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

		page.navigateToProductionPage();
		page.selectDefaultExport();
		base.stepInfo("verifying Post-Gen QC Checks In Progress status in Tile view");
		page.verifyingProductionStatusInHomePage("Creating Archive Complete/Skipped", exportName);
		page.verifyingProductionStatusInHomePage("Post-Gen QC Checks In Progress", exportName);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50666
	 * @Description:Verify that Production status displays as Draft on Production
	 *                     Grid View
	 **/
	@Test(description = "RPMXCON-50666", enabled = true, groups = { "regression" })
	public void verifyDraftStatusInGridView() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-50666");
		base.stepInfo("Verify that Production status displays as Draft on Production Grid View");

		String[] UserName = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };
		for (int i = 0; i < UserName.length; i++) {

			loginPage.loginToSightLine(UserName[i], Password[i]);
			base.stepInfo(UserName[i] + " logged in to sightline successfully");

			String FolderName = "Folder" + Utility.dynamicNameAppender();
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
			page.addANewExportAndSave(exportName);
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
			base.waitForElement(page.getbtnProductionGenerate());

			page.navigateToProductionPage();
			page.selectDefaultExport();
			driver.waitForPageToBeReady();
			page.getGridView().waitAndClick(10);
			base.stepInfo("verifying Draft status in grid view");
			page.verifyProductionStatusInHomePageGridView("DRAFT", exportName);
			loginPage.logout();
		}
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
