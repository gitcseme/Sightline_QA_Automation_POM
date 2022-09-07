package testScriptsRegressionSprint19;

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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression19_1 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	TagsAndFoldersPage tag;
	ProductionPage page;
	SessionSearch search;
	SoftAssert soft;
	
	String prefixID;
	String suffixID;
	String foldername;
	String tagName;
	String exportName;
	String productionname;
	String beginningBates;
	String subBates;
	String templateName;
	
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
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :To Verify Export using Production as Basis if there is count for "Number Of Export Document Selection Mismatch"
	 */
	@Test(description = "RPMXCON-47937", enabled = true, groups = { "regression" })
	public void verifyProductionBasicWithExport() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-47937");
		base.stepInfo("To Verify Export using Production as Basis if there is count for \"Number Of Export Document Selection Mismatch\"");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String foldername1 = "FolderProd" + Utility.dynamicNameAppender();
		tagName = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
	
		tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(foldername, Input.securityGroup);
		tag.CreateFolder(foldername1, Input.securityGroup);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);
		search.bulkFolderExisting(foldername1);
		search.bulkTagExisting(tagName);	
		
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.getSelectFolder(foldername1).waitAndClick(5);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		page.navigatingToProductionHomePage();
		exportName = "Export" + Utility.dynamicNameAppender();
		templateName = "Temp" + Utility.dynamicNameAppender();
		page.savedTemplateAndNewProdcution(productionname, templateName);
		subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportWithTemplate(exportName,templateName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		base.passedStep("Export done successfully with out any error");
		
		tag.navigateToTagsAndFolderPage();
		tag.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		tag.DeleteFolderWithSecurityGroup(foldername1, Input.securityGroup);
		
		loginPage.logout();
		base.passedStep("Verified Export using Production as Basis if there is count for \"Number Of Export Document Selection Mismatch\"");

	}
	
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :To Verify file group type (.mdb/.mdf) option on selection in Translations section, the corresponding translations should be considered for Export.
	 */
	@Test(description = "RPMXCON-48036", enabled = true, groups = { "regression" })
	public void verifyMBDFileTypeInExport() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48036");
		base.stepInfo("To Verify file group type (.mdb/.mdf) option on selection in Translations section, the corresponding translations should be considered for Export.");

		tagName = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
		String[] souredocids = {"STC4_00000992", "STC4_00000001", "STC4_00000028"};
	
		tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		
		search = new SessionSearch(driver);
		search.basicSourceDocIdsSearch(souredocids);
		search.bulkTagExisting(tagName);	
		
		page = new ProductionPage(driver);
		exportName = "Export" + Utility.dynamicNameAppender();
		templateName = "Temp" + Utility.dynamicNameAppender();
		subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagName);
		page.ddnselectFileType().selectFromDropdown().selectByVisibleText("Database Files (.mdb, .mdf, etc.)");
		page.fillingTranslationByFileType(".mdb");
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
		
		tag.navigateToTagsAndFolderPage();
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		
		loginPage.logout();
		base.passedStep("Verified file group type (.mdb/.mdf) option on selection in Translations section, the corresponding translations should be considered for Export.");

	}
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :Verify that if PA selects the Export with Production and has only tags selected in the native components section, then Component tab should Complete without any error.
	 */
	@Test(description = "RPMXCON-49358", enabled = true, groups = { "regression" })
	public void verifyProductionComponentInExport() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49358");
		base.stepInfo("Verify that if PA selects the Export with Production and has only tags selected in the native components section, then Component tab should Complete without any error.");

		tagName = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
	
		tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);	
		
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		
		page.navigatingToProductionHomePage();
		exportName = "Export" + Utility.dynamicNameAppender();
		templateName = "Temp" + Utility.dynamicNameAppender();
		page.savedTemplateAndNewProdcution(productionname, templateName);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportWithTemplate(exportName,templateName);
		driver.waitForPageToBeReady();
		page.getDATTab().waitAndClick(10);
		page.getElementDisplayCheck(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		
		tag.navigateToTagsAndFolderPage();
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		
		loginPage.logout();
		base.passedStep("Verify that if PA selects the Export with Production and has only tags selected in the native components section, then Component tab should Complete without any error.");

	}
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :Verify  Native section in Production Components section
	 */
	@Test(description = "RPMXCON-49384", enabled = true, groups = { "regression" })
	public void verifyNativeSection() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-49384");
		base.stepInfo("Verify  Native section in Production Components section");

		String export = "Export Set"+ Utility.dynamicNameAppender();
	
		page = new ProductionPage(driver);
		page.createNewExport(export);
		page.getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(export+" (Export Set)");
		exportName = "Export" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewExport(exportName);
		page.getNativeTab().waitAndClick(5);
		
		//verify
		soft.assertTrue(base.text("Produce Native Files").isElementAvailable(4));
		soft.assertEquals(page.getNative_text_Color().GetAttribute("class"),"blue-text");
		base.passedStep("Newly text was added in Production");
		
		page.getNative_AdvToggle().waitAndClick(5);
		soft.assertTrue(base.text("Do not produce Natives of").isElementAvailable(5));
		soft.assertTrue(base.text("Generate Load File (LST)").isElementAvailable(3));
		soft.assertTrue(base.text("Parents of Privileged and Redacted Documents").isElementAvailable(3));
		soft.assertTrue(base.text("Complete Families of Privileged and Redacted Documents").isElementAvailable(3));
		base.passedStep("Verified Advanced option");
		
		soft.assertAll();
		loginPage.logout();
		base.passedStep("Verified  Native section in Production Components section");

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
		System.out.println("******TEST CASES FOR Export cases EXECUTED******");

	}
}
