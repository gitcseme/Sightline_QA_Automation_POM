package sightline.docview;

import java.awt.Robot;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_ProductionFlow_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;

	String productionname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String folder;
	String tag;

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

		// Login as a RMU
		loginPage = new LoginPage(driver);
//		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
//		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52207 Description : Producing pdf with text highliting
	 * DocView- production flow
	 */

	@Test(description ="RPMXCON-52207",groups = { "regression" })
	public void verifyProductionForHighlitedDoc() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52207");
		folder = "Highlited_" + Utility.dynamicNameAppender();
		tag = "Tag" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(folder, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tag, "Privileged");
// search for the doc with highlights and assign to folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.duplicateDocId);
		sessionSearch.bulkFolderExisting(folder);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
// Adding and generating new production for selected Doc
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction(tag);
		page.fillingTextSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(folder);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		loginPage.logout();
	}
	
	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52208 Description : Producing Tiff with text highliting
	 * DocView- production flow
	 */
	
	@Test(description ="RPMXCON-52208",enabled=false, groups = { "regression" })
	public void verifyProductionForTiffDoc() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52208");
		String beginningBates = page.getRandomNumber(2);
		baseClass.stepInfo("Verify that Producing a TIFF with text highlighting working properly and not eliminate any text characters in the produced document.");
		folder = "Highlited_" + Utility.dynamicNameAppender();
		tag = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFolderPage.CreateFolder(folder, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tag, "Privileged");
// search for the doc with highlights and assign to folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.duplicateDocId);
		sessionSearch.bulkFolderExisting(folder);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
// Adding and generating new production for selected Doc		
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction(tag);
		page.fillingTextSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(folder);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		loginPage.logout();
	}
	
	/**
	 * Author : Vijaya.Rani date: 05/02/22 NA Modified date: NA Modified by:NA
	 * Description :User can load the produced document by clicking the drop down
	 * selection.'RPMXCON-51269' Sprint: 12
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51269",enabled = true, groups = { "regression" })
	public void verifyProducedDocumentClickingDropDownSelection() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51269");
		baseClass.stepInfo("User can load the produced document by clicking the drop down selection.");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		UtilityLog.info(Input.prodPath);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		baseClass.passedStep("Document produced the clicked production is loaded successfully");
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		baseClass.passedStep("Document produced the clicked production is loaded successfully");
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		baseClass.passedStep("Document produced the clicked production is loaded successfully");

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
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW/REDACTIONS EXECUTED******");
		try {
			loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
