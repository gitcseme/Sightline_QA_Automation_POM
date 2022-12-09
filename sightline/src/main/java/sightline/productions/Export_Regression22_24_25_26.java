package sightline.productions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression22_24_25_26 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	SoftAssert softAssertion;

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
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50629
	 * @Description:Verify that Export should generated successfully and documents
	 *                     should exported with Comments/Signautre
	 **/
	@Test(description = "RPMXCON-50629", enabled = true, groups = { "regression" })
	public void verifyingCommentsInGeneratedExportFile() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50629");
		base.stepInfo(
				"Verify that Export should generated successfully and documents should exported with Comments/Signautre");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String Tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tagname, "Select Tag Classification");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting dataset and navigating to doclist page");
		dataset.SelectingUploadedDataSetViewInDoclist("RPMXCON40140");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(1);
		doc.bulkTagExisting(Tagname);
		doc.documentSelection(1);
		base.waitTime(4);
		doc.docListToDocView();
		DocViewPage docview = new DocViewPage(driver);
		base.waitTime(6);
		base.waitTillElemetToBeClickable(docview.getDocView_IconDownload());
		docview.getDocView_IconDownload().Click();
		base.waitTillElemetToBeClickable(docview.getDOcViewDoc_DownloadOption("PDF"));
		docview.getDOcViewDoc_DownloadOption("PDF").Click();
		base.waitUntilFileDownload();
		String fileName = base.GetFileName();
		String ActualPDFText = page.verifyingTextInPDFFile(fileName);
		
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
		page.fillingDocumentSelectionWithTag(Tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedText);
		base.stepInfo("Export Path" + actualCopedText);
		String parentTab = page.openNewTab(actualCopedText);
		
		base.stepInfo("Accessing the Copypoth URL");
		String[] Values= {"VOL0001","Natives/","0001/"};
		for(int i=0;i<Values.length;i++) {
			driver.waitForPageToBeReady();
			if(page.getFileDir(Values[i]).isElementAvailable(2)) {
			page.getFileDir(Values[i]).waitAndClick(10);
			}else {
				base.stepInfo("URL is not accessible");
				base.failedStep("URL is not found");
			}
		}
		base.stepInfo("getting the text inside pdf document");
		if (page.getFirstPDFImageFile(prefixID + suffixID, subBates).isElementAvailable(2)) {

			page.getFirstPDFImageFile(prefixID + suffixID, subBates).waitAndClick(10);
			String CurrentUrl=driver.getWebDriver().getCurrentUrl();
			System.out.println(CurrentUrl);
			 String DownloadedFile = page.getPdfContent(CurrentUrl);
			 softAssertion = new SoftAssert();
			 base.stepInfo("verifying whether the comments are applied in all pages");
			 if (ActualPDFText.equals(DownloadedFile)) {
				 base.passedStep("Comments are displayed in all pages of downloaded file");
				} else  {
					base.failedStep( "Comments are not displayed");
				}

		} else if (page.getFirstPDFImageFile(prefixID + "0" + suffixID, subBates).isElementAvailable(2)) {

			page.getFirstPDFImageFile(prefixID + "0" + suffixID, subBates).waitAndClick(10);
			String CurrentUrl=driver.getWebDriver().getCurrentUrl();
			System.out.println(CurrentUrl);
			 String DownloadedFile2 = page.getPdfContent(CurrentUrl);
			 softAssertion.assertEquals(ActualPDFText,DownloadedFile2);
			 base.passedStep("Comments are displayed in all pages of downloaded file");
		} else {
			base.failedStep("PDF file is not generated");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		loginPage.logout();

	}
	
		
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50648
	 * @Description: Verify that after Post Generation is completed, it will
	 *               displays status on Export Progress bar Tile View as 'Completed'
	 **/
	@Test(description = "RPMXCON-50648", enabled = true, groups = { "regression" })
	public void verifyCompletedStatusInTileView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50648");
		base.stepInfo(
				"Verify that after Post Generation is completed, it will displays status on Export Progress bar Tile View as 'Completed'");

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

		page.navigateToProductionPage();
		page.selectDefaultExport();
		base.stepInfo("verifying Completed status in Tile view");
		page.verifyingProductionStatusInHomePage("Post-Gen QC Checks In Progress", exportName);
		page.verifyingProductionStatusInHomePage("Completed", exportName);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50647
	 * @Description: Verify that after Post Generation is completed, it will
	 *               displays status on Export generation page as 'Completed'
	 **/
	@Test(description = "RPMXCON-50647", enabled = true, groups = { "regression" })
	public void verifyCompletedStatusInGenPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50647");
		base.stepInfo(
				"Verify that after Post Generation is completed, it will displays status on Export generation page as 'Completed'");

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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		driver.waitForPageToBeReady();
		page.getBackButton().waitAndClick(5);
		base.stepInfo("verifying Completed status in Generate Page");
		page.verifyProductionStatusInGenPage("Completed");
		loginPage.logout();

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
	
	/**
	 * @author  sowndarya.velraj  created on:NA modified by:NA TESTCASE No:RPMXCON-49247
	 * @Description: verify In Export DAT, provide the TIFFPageCount for each document
	 **/
	@Test(description = "RPMXCON-49247", enabled = true, groups = { "regression" })
	public void verifyExportDatProvideTiffpageCount() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49247");
		base.stepInfo("To verify In Export DAT, provide the TIFFPageCount for each document");

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
	    String exportName = "Export" + Utility.dynamicNameAppender();
	    String subBates = page.getRandomNumber(2);
	    
	    tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();		
   		DocListPage doclist = new DocListPage(driver);
   		doclist.documentSelection(2);
   		doclist.bulkTagExisting(tagName);	
		
		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
        page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.addDATFieldAtThirdRow(Input.docBasic,Input.docName,Input.documentID);
		page.fillingNativeSection();	
		page.selectGenerateOption(false);
		page.fillingAdvancedInTiffSection();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(5);
		page.fillingSlipSheet(true, tagName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();	
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);	
		base.waitForElement(page.getFileDir("VOL0001"));
		page.getFileDir("VOL0001").waitAndClick(3);
		base.waitForElement(page.getLoadFileLink());
		page.getLoadFileLink().waitAndClick(3);
		base.waitForElement(page.getDatFileLink(exportName));
		page.getDatFileLink(exportName).waitAndClick(3);
		String DatFileText = page.getDATFileText().getText();
		System.out.println(DatFileText);
		String[] c = DatFileText.split("\\s+");
		for (int i = 1; i < c.length; i++) {
			String Dat = c[i];
			String[] DatFile = Dat.split("þ");
			System.out.println(Integer.valueOf(DatFile[3]));
			base.digitCompareEquals(Integer.valueOf(DatFile[3]), Integer.valueOf(Input.pageCount),
					"Tiffpage count is displayed", "Tiff page count is not displayed");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExport(exportName);
		page.fillingDATSection();
        page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.addDATFieldAtThirdRow(Input.docBasic,Input.docName,Input.documentID);
		page.fillingNativeSection();	
		page.selectGenerateOption(true);
		page.fillingSlipSheet(true, tagName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();	
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		String actualCopedText1 = page.getCopiedTextFromClipBoard();
		String parentTab1 = page.openNewTab(actualCopedText1);	
		base.waitForElement(page.getFileDir("VOL0001"));
		page.getFileDir("VOL0001").waitAndClick(3);
		base.waitForElement(page.getLoadFileLink());
		page.getLoadFileLink().waitAndClick(3);
		base.waitForElement(page.getDatFileLink(exportName));
		page.getDatFileLink(exportName).waitAndClick(3);
		String DatFileText1 = page.getDATFileText().getText();
		System.out.println(DatFileText1);
		String[] c1 = DatFileText1.split("\\s+");
		for (int i = 1; i < c1.length; i++) {
			String Dat = c1[i];
			String[] DatFile = Dat.split("þ");
			System.out.println(Integer.valueOf(DatFile[3]));
			base.digitCompareEquals(Integer.valueOf(DatFile[3]), Integer.valueOf(Input.pageCount),
					"Tiffpage count is displayed", "Tiff page count is not displayed");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab1);

		// delete tags and folders
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteTagWithClassification(tagName, Input.securityGroup);
		loginPage.logout();
	}
	
	
	 /**
     * @author  sowndarya.velraj  created on:NA modified by:NA TESTCASE No:RPMXCON-47978
     * @Description: Verify In Production, Bates Number for branding & in Productions (Exports), Bates information in the export data.
     **/
    @Test(description = "RPMXCON-47978", enabled = true, groups = { "regression" })
    public void verifyBatesNumBrandinExpData() throws Exception {
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        base.stepInfo("Test case Id: RPMXCON-47978");
        base.stepInfo("To Verify In Production, Bates Number for branding & in Productions (Exports), Bates information in the export data.");

       String tagName = "Tag" + Utility.dynamicNameAppender();
        String prefixID = Input.randomText + Utility.dynamicNameAppender();
        String suffixID = Input.randomText + Utility.dynamicNameAppender();
        String prefixID1= Input.randomText + Utility.dynamicNameAppender();
        String suffixID1 = Input.randomText + Utility.dynamicNameAppender();
        String exportName = "Export" + Utility.dynamicNameAppender();
        String subBates = page.getRandomNumber(2);
        productionname = "p" + Utility.dynamicNameAppender();
        String beginningBates = page.getRandomNumber(2);
        String prodBatesRange = "B1-10";
        
        tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);
        int purehit = sessionSearch.basicContentSearch(Input.testData1);
        sessionSearch.bulkTagExisting(tagName);    

       base = new BaseClass(driver);
        page.navigateToProductionPage();
        page.selectingDefaultSecurityGroup();
        page.addANewProduction(productionname);
        page.fillingDATSection();
        page.addDATFieldAtSecondRow(Input.bates, "ProductionBatesRange", prodBatesRange);
        page.tiffBrandingSelection(tagName);
        page.tiffPrivilegeDocumentSelection(tagName);
        page.slipSheetToggleEnable();
        page.navigateToNextSection();
        page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
        page.navigateToNextSection();
        page.fillingDocumentSelectionWithTag(tagName);
        page.navigateToNextSection();
        page.fillingPrivGuardPage();
        page.fillingProductionLocationPage(productionname);
        page.navigateToNextSection();
        page.fillingSummaryAndPreview();
        page.fillingGeneratePageWithContinueGenerationPopup();
        
        page.navigateToProductionPage();
        page.selectingDefaultSecurityGroup();
        page.selectDefaultExport();
        page.addANewExportwithProduction(exportName,productionname);
        page.getNextButton().waitAndClick(10);
        page.navigateToNextSection();
        page.navigateToNextSection();
        page.fillingDocumentSelectionWithTag(tagName);
        page.navigateToNextSection();
        base.waitForElement(page.getOkButton());
        page.getOkButton().waitAndClick(3);
        page.fillingExportLocationPage(exportName);
        page.navigateToNextSection();
        page.fillingSummaryAndPreview();
        page.fillingExportGeneratePageWithContinueGenerationPopup();
        
        String batesRange = prefixID + beginningBates + suffixID ;
        String actualCopedText = page.getCopiedTextFromClipBoard();
        String parentTab = page.openNewTab(actualCopedText);   
        page.goToImageFiles();
        page.verifyTiffFile(purehit, prefixID, suffixID, subBates, batesRange);
        
        // delete tags and folders
        tagsAndFolderPage.navigateToTagsAndFolderPage();
        tagsAndFolderPage.DeleteTagWithClassification(tagName, Input.securityGroup);
        loginPage.logout();
    }
	
	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE No:RPMXCON-63853
	 * @Description: Verify that for existing production/export/template with configured natively placeholdering,should be with enabled native placeholdering under TIFF/PDF section
	 **/
	@Test(description = "RPMXCON-63853", enabled = true, groups = { "regression" })
	public void verifyExisProdWithConfigNativPH() throws Exception {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-63853");
		base.stepInfo("Verify that for existing production/export/template with configured natively placeholdering,"
				+ " should be with enabled native placeholdering under TIFF/PDF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
	    String tempTIFF = "Temp" + Utility.dynamicNameAppender();
	    String tempPDF = "Temp" + Utility.dynamicNameAppender();
	    
	    tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
	    sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);	
		
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
        page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
	    page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, tempTIFF);
		
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
        page.fillingPDFSectionwithNativelyPlaceholder(tagname);
	    page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, tempPDF);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		String exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExportWithTemplate(exportName, tempTIFF);	
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(3);
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		base.waitForElement(page.getNativeDocsPlaceholderText());
		String actphTextTiff = page.getNativeDocsPlaceholderText().getText();
		softAssertion.assertEquals(tagname, actphTextTiff);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExportWithTemplate(exportName, tempPDF);
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(3);
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		base.waitForElement(page.gettextRedactionPlaceHolder());
		String actphTextPdf = page.gettextRedactionPlaceHolder().getText();
		softAssertion.assertEquals(tagname, actphTextPdf);
		softAssertion.assertAll();
		
		base.passedStep("Verify that for existing production/export/template with configured natively placeholdering,"
				+ " should be with enabled native placeholdering under TIFF/PDF section");
		loginPage.logout();
	}
}
