package testScriptsRegressionSprint28;

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

public class ExportRegression28 {
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
	 * @author Brundha.T TESTCASE No:RPMXCON-50667
	 * @Description: Verify that after Pre-gen checks is in progress, it will
	 *               displays status on Production Grid view
	 **/
	@Test(description = "RPMXCON-50667", enabled = true, groups = { "regression" })
	public void verifyPreGenCheckInProgressStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50667");
		base.stepInfo(
				"Verify that after Pre-gen checks is in progress, it will displays status on Production Grid view");

		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		String[] Username = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };

		for (int i = 0; i < Username.length; i++) {

			loginPage.loginToSightLine(Username[i], Password[i]);
			base.stepInfo("Logged in as" + Username[i]);

			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

			int purehit = sessionSearch.basicContentSearch(Input.telecaSearchString);
			sessionSearch.bulkFolderExisting(FolderName);

			base = new BaseClass(driver);
			page.navigateToProductionPage();
			page.selectingDefaultSecurityGroup();
			page.selectDefaultExport();
			page.addANewExport(exportName);
			page.fillingDATSection();
			page.fillingNativeSection();
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
			base.stepInfo("verifying Pre-gen checks status in grid view");
			page.verifyProductionStatusInHomePageGridView(" Pre-Gen Checks - 0/"+purehit+" Docs", exportName);

			loginPage.logout();
		}

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50676
	 * @Description:Verify that once LST generation is started it should displays '
	 *                     Generating Load Files' status on Production-Export Grid
	 *                     View
	 **/
	@Test(description = "RPMXCON-50676", enabled = true, groups = { "regression" })
	public void verifyGeneratingloadFileStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50676");
		base.stepInfo(
				"Verify that once LST generation is started it should displays ' Generating Load Files' status on Production-Export Grid View");

		String FolderName ="Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

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
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWaitForContinueGeneration();

		driver.Navigate().refresh();
		base.waitTime(1);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Generating Load Files status in grid view");
		page.verifyProductionStatusInHomePageGridView("Generating Load Files", exportName);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50670
	 * @Description:Verify that after Pregen check completed it should displays
	 *                     'Reserving Bates Range' status on Grid View on
	 *                     Production-Export Home page
	 **/
	@Test(description = "RPMXCON-50670", enabled = true, groups = { "regression" })
	public void verifyReservingBatesRangeStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50670");
		base.stepInfo(
				"Verify that after Pregen check completed it should displays 'Reserving Bates Range' status on Grid View on Production-Export Home page");

		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

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
		page.fillingNativeSection();
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
		base.waitTime(2);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Reserving Bates Range status in grid view");
		page.verifyProductionStatusInHomePageGridView("Reserving Bates Range", exportName);

		loginPage.logout();

	}
	/**
	 * @author Brundha.T No:RPMXCON-49123
	 * @Description:To verify that in Production-Export-Privileged Placeholder
	 *                 section, Metadata Field drop down should be sorted by alpha
	 *                 ascending
	 **/

	@Test(description = "RPMXCON-49123", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInTechDocMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49123-Export Component");
		base.stepInfo(
				"To verify that in Production-Export-Privileged Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		List<String> TIFFFPrivMetaData = new ArrayList<>();
		List<String> PDFPrivMetaData = new ArrayList<>();
		ProductionPage page = new ProductionPage(driver);

		String exportName = "Export" + Utility.dynamicNameAppender();

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		base.waitForElement(page.getPrivInsertMetadataField());
		page.getPrivInsertMetadataField().waitAndClick(5);
		List<String> PrivilegedMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		PrivilegedMetaDataField.replaceAll(String::toUpperCase);
		System.out.println("Before Sorting order" + PrivilegedMetaDataField);
		for (String Tiff : PrivilegedMetaDataField) {
			TIFFFPrivMetaData.add(Tiff);
		}
		Collections.sort(TIFFFPrivMetaData);
		System.out.println("After Sorting order" + TIFFFPrivMetaData);
		if (PrivilegedMetaDataField.equals(TIFFFPrivMetaData)) {
			base.passedStep("Sorting order is maintained  in TIFF");
		} else {
			base.failedStep("Sorting order is not maintained in TIFF");
		}

		page.navigatingToProductionHomePage();
		String ExportName1 = "Export" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectDefaultExport();
		page.addANewExport(ExportName1);
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		page.getTIFFTab().waitAndClick(10);
		driver.scrollPageToTop();
		page.getPDFGenerateRadioButton().Enabled();
		page.getPDFGenerateRadioButton().waitAndClick(10);
		driver.scrollingToElementofAPage(page.getPrivInsertMetadataField());
		base.waitForElement(page.getPrivInsertMetadataField());
		page.getPrivInsertMetadataField().waitAndClick(5);
		List<String> PDFPrivilegedMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		PDFPrivilegedMetaDataField.replaceAll(String::toUpperCase);
		System.out.println("Before Sorting" + PDFPrivilegedMetaDataField);
		for (String PDFPriv : PDFPrivilegedMetaDataField) {
			PDFPrivMetaData.add(PDFPriv);
		}
		Collections.sort(PDFPrivMetaData);
		System.out.println("After Sorting" + PDFPrivMetaData);
		if (PDFPrivilegedMetaDataField.equals(PDFPrivMetaData)) {
			base.passedStep("Sorting order is maintained in PDF");
		} else {
			base.failedStep("Sorting order is not maintained In PDF");
		}
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
