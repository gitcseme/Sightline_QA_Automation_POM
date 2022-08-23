package testScriptsRegressionSprint19;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
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
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression19_01 {
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
	 * @author Brundha TESTCASE No:RPMXCON-49346 Date:8/17/2022
	 * @Description:To verify that Tiff/PDF should generate with Tech Issue
	 *                 placeholdering even though File group/tag based
	 *                 placeholdering is exists.
	 */
	@Test(description = "RPMXCON-49346", enabled = true, groups = { "regression" })

	public void GenerateProductionForTechIssuePlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49346-from Production");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Tech Issue placeholdering even though File group/tag based placeholdering is exists.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(page.getNativeDocsPlaceholderText());
		page.getNativeDocsPlaceholderText().SendKeys(Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.isfileisExists(TiffFile);

		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNameTechnical);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername,Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48332 Date:8/17/2022
	 * @Description:To verify that Production should generated successfully with LST
	 *                 which is enabled by default
	 **/

	@Test(description = "RPMXCON-48332", enabled = true, groups = { "regression" })
	public void verifyingGenerationOfLoadFiles() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48332- Production Component");
		base.stepInfo("To verify that Production should generated successfully with LST which is enabled by default");

		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().isElementAvailable(5);
		page.getbtnProductionGenerate().Click();
		page.getbtnContinueGeneration().isElementAvailable(80);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			page.getbtnContinueGeneration().waitAndClick(2);
		}
		page.verifyProductionStatusInGenPage("Load File Generation In Progress");
		page.getDocumentGeneratetext().isElementAvailable(180);
		page.getConfirmProductionCommit().Click();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-47747 Date:8/17/2022
	 * @Description:Project Admin should be able to enter branding information on
	 *                      the self production wizard for TIFF/PDF component
	 **/

	@Test(description = "RPMXCON-47747", enabled = true, groups = { "regression" })
	public void verifyingUserCanEnterBranding() throws Exception {
		BaseClass base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-47747- Production Component");
		base.stepInfo(
				"Project Admin should be able to enter branding information on the self production wizard for TIFF/PDF component");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		driver.scrollingToElementofAPage(page.getTIFF_CenterHeaderBranding());
		page.getTIFF_CenterHeaderBranding().waitAndClick(5);
		new Actions(driver.getWebDriver()).moveToElement(page.getTIFF_EnterBranding().getWebElement()).click();
		page.getTIFF_EnterBranding().SendKeys(Input.searchString1);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("Verified branding information on the self production wizard for TIFF/PDF component");
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47792 Date:8/17/2022
	 * @Description:To Verify Production Name and Status is displayed properly on
	 *                 the Generate tab.
	 */
	@Test(description = "RPMXCON-47792", enabled = true, groups = { "regression" })

	public void verifyingProductionStatus() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47792-from Production Component");
		base.stepInfo("To Verify Production Name and Status is displayed properly on the Generate tab.");

		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().isElementAvailable(5);
		page.getbtnProductionGenerate().Click();
		page.verifyProductionStatusInGenPage("Draft");
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");
		page.getbtnContinueGeneration().isElementAvailable(20);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			page.getbtnContinueGeneration().waitAndClick(2);
		}
		page.getDocumentGeneratetext().isElementAvailable(180);
		page.getConfirmProductionCommit().isElementAvailable(10);
		page.getQC_backbutton().waitAndClick(2);
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		base.ValidateElement_Presence(page.getRegenerateBtn(), "Regenerate Button");
		page.visibleCheck(productionname);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().isElementAvailable(5);
		page.getbtnProductionGenerate().Click();
		page.verifyProductionStatusInGenPage("Draft");
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");
		page.getbtnContinueGeneration().isElementAvailable(10);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			page.getbtnContinueGeneration().waitAndClick(2);
		}
		page.getDocumentGeneratetext().isElementAvailable(180);
		page.getConfirmProductionCommit().isElementAvailable(10);
		page.getQC_backbutton().waitAndClick(2);
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		base.ValidateElement_Presence(page.getRegenerateBtn(), "Regenerate Button");
		driver.waitForPageToBeReady();
		page.visibleCheck(productionname1);

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47893 Date:8/17/2022
	 * @Description To Verify Document Selection Section on the self production
	 *              wizard For Folder
	 * 
	 */
	@Test(description = "RPMXCON-47893", enabled = true, groups = { "regression" })
	public void verifyDocumentSelectionInProductionWizard() throws Exception {

		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47893 -Production Component");
		base.stepInfo("To Verify Document Selection Section on the self production wizard For Folder");

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.documentCountInProductionWizard(PureHit);
		page.fillingDocumentSelectionWithTag(tagname);
		page.documentCountInProductionWizard(PureHit);
		page.fillingDocuSelectionPage(SearchName, null);
		page.documentCountInProductionWizard(PureHit);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-49179 Date:8/17/2022
	 * @Description:To verify that in Production-Numbering and Sorting
	 *                 section,Metadata Field drop down should be sorted by alpha
	 *                 ascending
	 **/

	@Test(description = "RPMXCON-49179", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInSortingTabMetaData() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-49179- Production Component");
		base.stepInfo(
				"To verify that in Production-Numbering and Sorting section,Metadata Field drop down should be sorted by alpha ascending");

		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();
		List<String> MetaDataFieldValue = base
				.availableListofElements(page.getSortingMetDataList("lstSortingMetaData"));
		base.verifyOriginalSortOrder(MetaDataFieldValue, MetaDataFieldValue, "Ascending", true);
		driver.waitForPageToBeReady();
		List<String> SubSortingMetaData = base
				.availableListofElements(page.getSortingMetDataList("lstSubSortingMetaData"));
		base.verifyOriginalSortOrder(SubSortingMetaData, SubSortingMetaData, "Ascending", true);

		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-47715 Date:8/17/2022
	 * @Description To Verify ProjectAdmin will be able to enter numbering and
	 *              sorting information on the self production wizard
	 * 
	 */
	@Test(description = "RPMXCON-47715", enabled = true, groups = { "regression" })
	public void verifyBatesNumberInProductionWizard() throws Exception {

		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47715 -Production Component");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to enter numbering and sorting information on the self production wizard");

		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(2);
		base.VerifySuccessMessage("Mark Complete successful");
		base.waitTime(2);
		String actualText = page.getBeginningBates().GetAttribute("value");
		String actualTextInPrefix = page.gettxtBeginningBatesIDPrefix().GetAttribute("value");
		base.textCompareEquals(actualText, beginningBates, "BatesNumber Retained on MarkComplete"," BatesNumber not Retained");
		base.textCompareEquals(actualTextInPrefix, prefixID, "BatesNumber Retained on MarkComplete"," BatesNumber not Retained");
				
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
		System.out.println("******TEST CASES FOR Production EXECUTED******");

	}


}
