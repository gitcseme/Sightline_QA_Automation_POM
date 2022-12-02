package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.asserts.SoftAssert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression26 {
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
		String Tagname = "FolderName" + Utility.dynamicNameAppender();
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
		dataset.selectDataSetWithName("RPMXCON40140");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(1);
		doc.bulkTagExistingFromDoclist(Tagname);
		doc.documentSelection(1);
		doc.docListToDocView();
		DocViewPage docview = new DocViewPage(driver);
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
		
		String[] Values= {"VOL0001","Natives/","0001/"};
		for(int i=0;i<Values.length;i++) {
			driver.waitForPageToBeReady();
			page.getFileDir(Values[i]).waitAndClick(10);
			driver.waitForPageToBeReady();	
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
