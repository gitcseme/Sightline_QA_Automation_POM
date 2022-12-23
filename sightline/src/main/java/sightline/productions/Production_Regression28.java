package sightline.productions;

import java.io.File;
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
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression28 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
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
		softAssertion=new SoftAssert();
		page= new ProductionPage(driver);

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
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-49214
	 * @Description:To verify that upon completion of uncommit, notification should be displayed on right top corner
	 **/
	@Test(description = "RPMXCON-49214", enabled = true, groups = { "regression" })
	public void verifyCommitAndUncommit() throws Exception {
		
		UtilityLog.info(Input.prodPath);	
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Test Cases Id : RPMXCON-49214");
		base.stepInfo("To verify that upon completion of uncommit, notification should be displayed on right top corner");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base = new BaseClass(driver);
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		 DocListPage doc = new DocListPage(driver);
		 doc.documentSelection(2);
		 doc.bulkTagExisting(tagname);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		
		base.stepInfo("Committing the production");
		page.fillingGeneratePageWithContinueGenerationPopup();
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			base.waitTime(2);
		}

		base.stepInfo("Uncommitting the committed production");
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			base.waitTime(2);
		}
		
		//verify nofication, backgroud task
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		String actual=base.getFirstBackRoundTask().getText();	
		String expected = "Uncommit successfully completed for the production "+productionname;
		if (actual.equals(expected)) {
			base.passedStep("Uncommit is successful");
		}
		else {
			base.failedMessage("Uncommit is not successful");
		}
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.waitForElement(page.getConfirmProductionCommit());
		page.getConfirmProductionCommit().isDisplayed();
		base.passedStep("Link name is commit");
		
		
		page.navigateToProductionPage();
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete",productionname);
		
		base.passedStep("Verified - completion of uncommit, notification should be displayed on right top corner");
	    loginPage.logout();
	}
}






















