package testScriptsRegressionSprint18;

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

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression {
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
		Reporter.setCurrentTestResult(result);
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
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49817
	 * @Description:To verify that Audio production with redaction should generate successfully if bates number includes hyphens
	 **/

	@Test(description = "RPMXCON-49817", enabled = true, groups = { "regression" })
	public void verifyBatesNumberWithHypens() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49817 Production Component Sprint 18");
		base.stepInfo("To verify that Audio production with redaction should generate successfully if bates number includes hyphens");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String batesWithHypen="B"+page.getRandomNumber(2)+"-";
		System.out.println(batesWithHypen);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithHypen);
		driver.waitForPageToBeReady();
		page.fillingMP3FileWithBurnRedaction();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, batesWithHypen);
		base.passedStep("verified that Audio production with Redaction should generate successfully if bates number contains Hypen");
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49818
	 * @Description:To verify that Audio production with Redaction should generate successfully if bates number contains Space in between
	 **/

	@Test(description = "RPMXCON-49818", enabled = true, groups = { "regression" })
	public void verifyBatesNumberWithSpace() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49818 Production Component Sprint 18");
		base.stepInfo("To verify that Audio production with Redaction should generate successfully if bates number contains Space in between");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String batesWithSpace="B"+" "+page.getRandomNumber(2);
		System.out.println(batesWithSpace);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithSpace);
		driver.waitForPageToBeReady();
		page.fillingMP3FileWithBurnRedaction();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, batesWithSpace);
		base.passedStep("verified that Audio production with Redaction should generate successfully if bates number contains Space in between");
	}
	

	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48878
	 * @Description:To verify that error message should be display if user map the multiple source field to single DAT fields
	 **/

	@Test(description = "RPMXCON-48878", enabled = true, groups = { "regression" })
	public void verifyErrorMessageinDAT() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48878 Production Component Sprint 18");
		base.stepInfo("To verify that error message should be display if user map the multiple source field to single DAT fields");
		String expected="Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.";
		ProductionPage page = new ProductionPage(driver);
		base=new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, Input.bates);
		page.addDATFieldAtSecondRow(Input.productionText,Input.tiffPageCountNam,Input.bates);
		page.getComponentsMarkComplete().waitAndClick(5);
		base.VerifyErrorMessage(expected);
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47885
	 * @Description:To Verify Numbering And Sorting Page with (Page Level -Document Level/Format ;//Beginning Bates-Prefix-Suffix-Min Length;//Use Metadata Field -Prefix-Suffix;//Sort By & Sub-sort By
	 **/

	@Test(description = "RPMXCON-47885", enabled = true, groups = { "regression" })
	public void verifyNumberingPageComponents() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-47885 Production Component Sprint 18");
		base.stepInfo("To Verify Numbering And Sorting Page with (Page Level -Document Level/Format ;//Beginning Bates-Prefix-Suffix-Min Length;//Use Metadata Field -Prefix-Suffix;//Sort By & Sub-sort By");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		if(page.pageRadioButton().isElementAvailable(2)) {
			
			page.getBeginningBates().isDisplayed();
			page.gettxtBeginningBatesIDPrefix().isDisplayed();
			page.gettxtBeginningBatesIDSuffix().isDisplayed();
			page.gettxtBeginningBatesIDMinNumLength().isDisplayed();
			page.getlstSortingMetaData().isDisplayed();
			page.getlstSubSortingMetaData().isDisplayed();
			base.passedStep("Displayed page->Page Level Format-/Beginning Bates-Prefix-Suffix-Min Length  Use Metadata Field -Prefix-Suffix;Sort By & Sub-sort By");
		}
		else {
			base.failedStep("Page level options are not displayed");
		}
				}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49100
	 * @Description:To verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses should be available under the "Email" category by default to select in the DAT for a production.
	 **/
	@Test(description = "RPMXCON-49100", enabled = true, groups = { "regression" })
	public void verifyEmailSourceField() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49100 Production Component Sprint 18");
		base.stepInfo("To verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses should be available under the \"Email\" category by default to select in the DAT for a production.");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.getDATChkBox().Click();
		page.getDATTab().Click();
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Email");
		if(page.getDAT_SourceField1().Displayed()) {
			page.emailAuthorNameAddress().isElementAvailable(2);
			page.EmailBCCNamesAndAddresses().isElementAvailable(2);
			page.EmailCCNamesAndAddresses().isElementAvailable(2);
			page.emailToAuthorNameAddress().isElementAvailable(2);
			base.passedStep("Email related source field  present");
		}
		else {
			base.failedStep("Email related source field not present");
		}		
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
}

