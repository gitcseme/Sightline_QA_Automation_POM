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

public class Production_Regression24 {
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
	SavedSearch saveSearch;
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
	 * @author Brundha Testcase No:RPMXCON-49727
	 * @Description:Verify that branding with Bates Number and 'Confidentiality' is
	 *                     applied on all pages for image based documents for
	 *                     generated TIFF/PDF file
	 **/
	@Test(description = "RPMXCON-49727", enabled = true, groups = { "regression" })
	public void verifyingBrandingTextInReGeneratedDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(Input.additionalDataProject);
		base.stepInfo("RPMXCON-49727 -Production Component");
		base.stepInfo(
				"Verify that branding with Bates Number and 'Confidentiality' is applied on all pages for image based documents for generated TIFF/PDF file");

		DocViewPage Doc = new DocViewPage(driver);
		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String PlaceholderText = "Confidentiality";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("RequirePDFGeneration", Input.pageCount);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		sessionSearch.bulkTagExisting(tagname);
		doc.documentSelection(3);
		doc.viewSelectedDocumentsInDocView();
		base.waitTime(8);
		int PageCount = Doc.getTotalPagesCount();

		DocRedactions.selectDoc2();
		driver.waitForPageToBeReady();
		int PageCount2Doc = Doc.getTotalPagesCount();

		DocRedactions.doclistTable(3).waitAndClick(10);
		driver.waitForPageToBeReady();
		int PageCount3Doc = Doc.getTotalPagesCount();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.fillingBrandingInTiffSection(Input.batesNumber, PlaceholderText);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageForOtherProject(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		driver.waitForPageToBeReady();
		int Count = Integer.valueOf(beginningBates) + PageCount;
		int LastDoc = Count + PageCount2Doc;
		page.extractFile();
		driver.waitForPageToBeReady();
		String BatesNumber = prefixID + beginningBates + suffixID;
		String BatesNumber1 = prefixID + Count + suffixID;
		String BatesNumber2 = prefixID + LastDoc + suffixID;
		
		String []Bates= {BatesNumber,BatesNumber1,BatesNumber2};
		String home = System.getProperty("user.home");
		for(int i=0;i<Bates.length;i++) {
		File fileName = new File(home + "/Downloads/VOL0001/PDF/0001/" + Bates[i] + ".pdf");
		driver.waitForPageToBeReady();
		System.out.println(Bates[i]);
		
		if(fileName.exists()) {
			base.passedStep(" Batesnumber is maintained in sequence order");
		}else {
			base.failedStep("Bates number is not maintained in sequence order");
		}
		}
		
		page.pdfVerificationInDownloadedFile(BatesNumber,PageCount, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber1, PageCount2Doc, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber2, PageCount3Doc, prefixID, PlaceholderText);
		loginPage.logout();
	}
	/**
	 * @author Brundha Testcase No:RPMXCON-49118
	 * @Description:To verify that value of Redacted Documents on Production-Summary tab
	 **/
	@Test(description = "RPMXCON-49118", enabled = true, groups = { "regression" })
	public void verifyingRedactDocOnSummaryTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		
		base.stepInfo("RPMXCON-49118 -Production Component");
		base.stepInfo("To verify that value of Redacted Documents on Production-Summary tab");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		String Foldername = "Fold" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(Foldername,Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.comments);
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		DocRedactions.doclistTable(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		DocRedactions.redactRectangleUsingOffset(10,10,60,60);
		DocRedactions.selectingRedactionTag2(Redactiontag1);
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		DocRedactions.doclistTable(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		DocRedactions.redactRectangleUsingOffset(10,10,70,70);
		DocRedactions.selectingRedactionTag2(Redactiontag1);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.fillingBurnRedaction(Redactiontag1, "White with black font", false, null, null);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(Foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		
		driver.waitForPageToBeReady();
		String RedactDocCount=page.redactedDocCountInSummaryPage().getText();
		System.out.println(RedactDocCount);
		base.stepInfo("verifying Redacted Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(RedactDocCount),2, "Redacted document count is displayed as expected","Redacted Doc count mismatch");
		loginPage.logout();
		
	}
	
	/**
	 * @author Brundha Testcase No:RPMXCON-49117
	 * @Description:To verify that the value of Privileged Documents on production summary tab
	 **/
	@Test(description = "RPMXCON-49117", enabled = true, groups = { "regression" })
	public void verifyingPrivDocCountOnSummaryTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		
		base.stepInfo("RPMXCON-49117 -Production Component");
		base.stepInfo("To verify that the value of Privileged Documents on production summary tab");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Foldername = "p" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(Foldername,Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		int docCount=3;
		doc.documentSelection(docCount);
		sessionSearch.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagname, tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(Foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		
		driver.waitForPageToBeReady();
		String PrivDocCount=page.getPrivDocCountInSummaryPage().getText().trim();
		System.out.println(PrivDocCount);
		base.stepInfo("verifying Priviledged Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(PrivDocCount),docCount, "Priviledged document count is displayed as expected","Priviledged Doc count mismatch");
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
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}
}
