package testScriptsRegressionSprint25;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression25 {
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
	 * @author Brundha Testcase No:RPMXCON-49116
	 * @Description:To verify that the value of Number of MP3 Files on
	 *                 Production-Summary tab if MP3 file component is selected and
	 *                 Priv tags selected in TIFF/PDF component.
	 **/
	@Test(description = "RPMXCON-49116", enabled = true, groups = { "regression" })
	public void verifyingPrivDocCountOnSummaryTab() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-49116 -Production Component");
		base.stepInfo(
				"To verify that the value of Number of MP3 Files on Production-Summary tab if MP3 file component is selected and Priv tags selected in TIFF/PDF component.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Foldername = "p" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(Foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		int PureHit=sessionSearch.metaDataSearchInBasicSearch("DocFileType", ".mp3");
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		int docCount = 5;
		doc.documentSelection(docCount);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagname,Input.searchString4);
		page.advancedProductionComponentsMP3();
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		page.getNextButton().waitAndClick(5);
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(Foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();

		driver.waitForPageToBeReady();
		String Mp3DocCount = page.countOfNumberOfMP3().getText().trim();
		System.out.println(Mp3DocCount);
		int Mp3FileCount=PureHit-docCount;
		System.out.println(Mp3FileCount);
		base.stepInfo("verifying Mp3 Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(Mp3DocCount),Mp3FileCount,
				"Mp3 document count is displayed as expected", "Mp3 Doc count mismatched");
		loginPage.logout();
	}
	/**
	 * @author Brundha Testcase No:RPMXCON-49381
	 * @Description:Verify 'Advanced Options' should be removed from the DAT
	 *                     component section.
	 **/
	@Test(description = "RPMXCON-49381", enabled = true, groups = { "regression" })
	public void verifyingDATSection() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-49381 -Production Component");
		base.stepInfo("Verify 'Advanced Options' should be removed from the DAT component section.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getDATChkBox());
		page.getDATChkBox().waitAndClick(10);

		base.waitForElement(page.getDATTab());
		page.getDATTab().waitAndClick(10);
		if(!page.getAdvancedToggle().isDisplayed()) {
			base.passedStep("Advanced option is removed from DAT section");
		}else{
			base.failedStep("Advanced option is not removed");
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
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}
}
