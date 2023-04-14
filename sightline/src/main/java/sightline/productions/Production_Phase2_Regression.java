package sightline.productions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Phase2_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
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
		base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49817
	 * @Description:To verify that Audio production with redaction should generate
	 *                 successfully if bates number includes hyphens
	 **/

	@Test(description = "RPMXCON-49817", enabled = true, groups = { "regression" })
	public void verifyBatesNumberWithHypens() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49817 Production Component Sprint 18");
		base.stepInfo(
				"To verify that Audio production with redaction should generate successfully if bates number includes hyphens");
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
		String batesWithHypen = "B" + page.getRandomNumber(2) + "-";
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
		base.passedStep(
				"verified that Audio production with Redaction should generate successfully if bates number contains Hypen");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49818
	 * @Description:To verify that Audio production with Redaction should generate
	 *                 successfully if bates number contains Space in between
	 **/

	@Test(description = "RPMXCON-49818", enabled = true, groups = { "regression" })
	public void verifyBatesNumberWithSpace() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49818 Production Component Sprint 18");
		base.stepInfo(
				"To verify that Audio production with Redaction should generate successfully if bates number contains Space in between");
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
		String batesWithSpace = "B" + " " + page.getRandomNumber(2);
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
		base.passedStep(
				"verified that Audio production with Redaction should generate successfully if bates number contains Space in between");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48878
	 * @Description:To verify that error message should be display if user map the
	 *                 multiple source field to single DAT fields
	 **/

	@Test(description = "RPMXCON-48878", enabled = true, groups = { "regression" })
	public void verifyErrorMessageinDAT() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48878 Production Component Sprint 18");
		base.stepInfo(
				"To verify that error message should be display if user map the multiple source field to single DAT fields");
		String expected = "Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.";
		ProductionPage page = new ProductionPage(driver);
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, Input.bates);
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.bates);
		page.getComponentsMarkComplete().waitAndClick(5);
		base.VerifyErrorMessage(expected);
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47885
	 * @Description:To Verify Numbering And Sorting Page with (Page Level -Document
	 *                 Level/Format ;//Beginning Bates-Prefix-Suffix-Min
	 *                 Length;//Use Metadata Field -Prefix-Suffix;//Sort By &
	 *                 Sub-sort By
	 **/

	@Test(description = "RPMXCON-47885", enabled = true, groups = { "regression" })
	public void verifyNumberingPageComponents() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-47885 Production Component Sprint 18");
		base.stepInfo(
				"To Verify Numbering And Sorting Page with (Page Level -Document Level/Format ;//Beginning Bates-Prefix-Suffix-Min Length;//Use Metadata Field -Prefix-Suffix;//Sort By & Sub-sort By");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		if (page.pageRadioButton().isElementAvailable(2)) {

			page.getBeginningBates().isDisplayed();
			page.gettxtBeginningBatesIDPrefix().isDisplayed();
			page.gettxtBeginningBatesIDSuffix().isDisplayed();
			page.gettxtBeginningBatesIDMinNumLength().isDisplayed();
			page.getlstSortingMetaData().isDisplayed();
			page.getlstSubSortingMetaData().isDisplayed();
			base.passedStep(
					"Displayed page->Page Level Format-/Beginning Bates-Prefix-Suffix-Min Length  Use Metadata Field -Prefix-Suffix;Sort By & Sub-sort By");
		} else {
			base.failedStep("Page level options are not displayed");
		}
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49100
	 * @Description:To verify that EmailAuthorNameAndAddress,
	 *                 EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and
	 *                 EmailBCCNamesAndAddresses should be available under the
	 *                 "Email" category by default to select in the DAT for a
	 *                 production.
	 **/
	@Test(description = "RPMXCON-49100", enabled = true, groups = { "regression" })
	public void verifyEmailSourceField() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49100 Production Component Sprint 18");
		base.stepInfo(
				"To verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses should be available under the \"Email\" category by default to select in the DAT for a production.");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.getDATChkBox().waitAndClick(10);
		page.getDATTab().waitAndClick(10);
		base.waitForElement(page.getDAT_FieldClassification1());
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Email");
		if (page.getDAT_SourceField1().Displayed()) {
			page.emailAuthorNameAddress().isElementAvailable(2);
			page.EmailBCCNamesAndAddresses().isElementAvailable(2);
			page.EmailCCNamesAndAddresses().isElementAvailable(2);
			page.emailToAuthorNameAddress().isElementAvailable(2);
			base.passedStep("Email related source field  present");
		} else {
			base.failedStep("Email related source field not present");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Descripion : To verify Duplicate Production Name Should not be Allowed in
	 *             Production.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47819", groups = { "regression" }, enabled = true)
	public void createProdWithSameName() throws Exception {
		String prodName = "Production" + Utility.dynamicNameAppender();
		String expectedMsg = "60001000011 : You cannot create this production since a production with the same name already exists in the project.";
		ProductionPage production = new ProductionPage(driver);
		BaseClass base = new BaseClass(driver);

		base.stepInfo("Test case Id: RPMXCON-47819 Production");
		base.stepInfo("To verify Duplicate Production Name Should not be Allowed in Production.");

		// create Production wit random Name
		production.navigateToProductionPage();
		production.addANewProductionAndSave(prodName);

		// create production with same name as Before
		driver.Navigate().refresh();
		production.navigateToProductionPage();
		production.addANewProductionAndSave(prodName);

		// verify error message
		base.VerifyErrorMessage(expectedMsg);
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify Next and Back button navigation from Priv Guard &
	 *              Production Location tab
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47817", enabled = true, groups = { "regression" })
	public void verifyPrivGuardSavedDetails() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-47817 Production");
		base.stepInfo("To verify Next and Back button navigation from Priv Guard & Production Location tab");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// perform bulk tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create new production and select Doc's
		ProductionPage page = new ProductionPage(driver);
		String batesWithSpace = "B" + " " + page.getRandomNumber(2);
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

		// insert Privileged Tag in Priv Guard Page & get textbox value
		page.priviledgeDocCheck(true, tagname);
		List<String> valueBeforeNavigate = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();
		base.stepInfo("Navigated to Production location page");

		// Click Back Btn in production location page
		page.clickBackBtnUntilElementFound(page.getAddRuleBtn());
		base.stepInfo("Navigated Back to Priv Guard page");
		List<String> valueAfterNavigate = page.privGuardTextBoxValue();

		// verify saved details in Priv Guard Page
		String passMsg = "Saved Details are Retained after navigating back to Priv Guard page ";
		String FailMsg = "Saved Details are not Retained";
		base.listCompareEquals(valueAfterNavigate, valueBeforeNavigate, passMsg, FailMsg);

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48498
	 * @Description:To verify that after clicking on InComplete button on Production
	 *                 Components, last selected Tags should be displayed.
	 **/
	@Test(description = "RPMXCON-48498", enabled = true, groups = { "regression" })
	public void verifySelectedTags_MarkIncomplete() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48498 Production Component Sprint 18");
		base.stepInfo(
				"To verify that after clicking on InComplete button on Production Components, last selected Tags should be displayed.");
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.clickComponentMarkCompleteAndIncomplete();
		base.waitForElement(page.getNativeTab());
		page.getNativeTab().waitAndClick(10);
		base.waitForElement(page.getNativeSelectTags());
		page.getNativeSelectTags().waitAndClick(10);
		//page.getNativeCheckBox(tagname).ScrollTo();
		base.waitForElement(page.getNativeCheckBox(tagname));
		driver.waitForPageToBeReady();
		page.getNativeCheckBox(tagname).GetAttribute("class").contains("clicked");
		base.passedStep(
				"after clicking on InComplete button on Production Components, last selected Tags should be displayed");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48942
	 * @Description:To verify that after clicking on Generate button from
	 *                 Production-Generate tab, user cannot click on the Generate
	 *                 button again.
	 **/
	@Test(description = "RPMXCON-48942", enabled = true, groups = { "regression" })
	public void verifyDisabledGenerateButton() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48942 Production Component Sprint 18");
		base.stepInfo(
				"To verify that after clicking on Generate button from Production-Generate tab, user cannot click on the Generate button again.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().ScrollTo();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().GetAttribute("disabled").contains("disabled");
		base.passedStep(
				"verified that after clicking on Generate button from Production-Generate tab, user cannot click on the Generate button again");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48940
	 * @Description:To verify that after clicking on Generate button from
	 *                 Production-Generate tab, status should be changed to the In
	 *                 Progress
	 **/
	@Test(description = "RPMXCON-48940", enabled = true, groups = { "regression" })
	public void verifyInProgressStatus() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48940 Production Component Sprint 18");
		base.stepInfo(
				"To verify that after clicking on Generate button from Production-Generate tab, status should be changed to the In Progress");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().ScrollTo();
		page.getbtnProductionGenerate().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (page.getInProgressStatus().isDisplayed()) {
			base.passedStep(
					"verify that after clicking on Generate button from Production-Generate tab, status should be changed to the In Progress");
		} else {
			base.failedStep(
					"After clicking on Generate button from Production-Generate tab, status has not changed to  In Progress");
		}

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47810
	 * @Description:To Verify 'Productions & Export' On Production & Export Home
	 *                 page
	 **/
	@Test(description = "RPMXCON-47810", enabled = true, groups = { "regression" })
	public void verifyProductionAndExport() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-47810 Production Component Sprint 18");
		base.stepInfo("To Verify 'Productions & Export' On Production & Export Home page");
		ProductionPage page = new ProductionPage(driver);
		base = new BaseClass(driver);

		base.stepInfo("Verify the page label as 'Productions & Export'");
		page.getProductionAndExportHeader().isDisplayed();

		base.stepInfo("Verify the Production create link");
		page.getProdExportSet().isDisplayed();

		base.stepInfo("Verify the already created Production drop down list");
		List<String> lstOfProductionAndExportSet = base.availableListofElements(page.getSelectProdcutionOptions());
		System.out.println(lstOfProductionAndExportSet);

		base.stepInfo(
				"Verify on selection of 'Production Set', the below label immediately changes to 'Production Set'.");
		page.navigateToProductionPage();
		page.getProdExport_ProductionSets().waitAndClick(10);
		page.getProdExport_ProductionSets().selectFromDropdown().selectByIndex(1);

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49078
	 * @Description:Production Document Selection to DocList
	 **/
	@Test(description = "RPMXCON-49078", enabled = true, groups = { "regression" })
	public void DocumentSelectionVerification() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49078 Production Component Sprint 18");
		base.stepInfo("Production Document Selection to DocList");

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();

		// Document selection without including families
		page.fillingDocumentSelectionPageExcludingFamilies(foldername);
		String docCount = page.navigatingToDocViewPage();
		softAssertion.assertEquals(docCount, purehit);

		// get back to source
		page.btnBackToSource().waitAndClick(10);
		page.getBackButton().waitAndClick(10);

		// Document selection including families
		base.waitForElement(page.getbtnComponentsMarkIncomplete());
		page.getBtnMarkIncomplete().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getIncludeFamilies());
		page.getIncludeFamilies().waitAndClick(10);
		driver.scrollPageToTop();
		String docCountIncludingFamilies = page.navigatingToDocViewPage();
		System.out.println(docCountIncludingFamilies);
		base.passedStep("verified Production Document Selection to DocList");

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48273
	 * @Description:To Verify Number of MP3 Files should be shown even when the
	 *                 count of MP3 Files among the selected document is 0.
	 **/

	@Test(description = "RPMXCON-48273", enabled = true, groups = { "regression" })
	public void verifyMP3Count() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48273 Production Component Sprint 18");
		base.stepInfo(
				"To Verify Number of MP3 Files should be shown even when the count of MP3 Files among the selected document is 0.");
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
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String MP3FilesCount = page.countOfNumberOfMP3().getText();
		System.out.println(MP3FilesCount);
		softAssertion.assertEquals(MP3FilesCount, "0");
		softAssertion.assertAll();
		base.passedStep(
				"Verified Number of MP3 Files should be shown even when the count of MP3 Files among the selected document is 0.");

	}

	////

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47916
	 * @Description:To Verify the placeholder text is available in the generated
	 *                 placeholder PDFs/TIFF's
	 */

	@Test(description = "RPMXCON-47916", enabled = true, groups = { "regression" })
	public void verifyPlaceholderText() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47916- Production Sprint 16");
		base.stepInfo("To Verify the placeholder text is available in the generated placeholder PDFs/TIFF's.");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;

		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.searchString4);
		base.stepInfo("verified placeholder in tiff");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47895
	 * @Description:To Verify Quality Control & Confirmation Section on the self
	 *                 production wizard
	 */

	@Test(description = "RPMXCON-47895", enabled = true, groups = { "regression" })
	public void verifySelfProductionWizard() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47895- Production Sprint 16");
		base.stepInfo("To Verify Quality Control & Confirmation Section on the self production wizard");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.navigateToProductionPage();
		page.prodGenerationInProgressStatus(productionname);
		base.stepInfo("Navigated to QC Tab");
		if (page.getProduction().isDisplayed()) {
			System.out.println("Production name is present : " + prodName);
		}
		Element count = page.getProductionDocCount();
		if (page.getProductionDocCount().isDisplayed()) {
			System.out.println("Generated Document count : " + count);
		}
		if (page.getConfirmProductionCommit().isDisplayed()) {
			System.out.println("commit Button is available");
		}
		if (page.getCopyPath().isDisplayed()) {
			System.out.println("CopyPath Button is available");
		}
		if (page.getQC_Download().isDisplayed()) {
			System.out.println("Download Button is available");
		}
		if (page.automatedCheck_prodfiles().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		if (page.automatedCheck_DocumentCounts().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		if (page.automatedCheck_BlankPageRemoval().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		if (page.automatedCheck_BatesNumberGeneration().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		base.passedStep("verified QC Tab");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47935
	 * @Description:To verify On Document Level Numbering Bates Number should get
	 *                 displayed on Production Home Page.
	 */

	@Test(description = "RPMXCON-47935", enabled = true, groups = { "regression" })
	public void verifyDocumentLevelNumbering() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47935- Production Sprint 16");
		base.stepInfo(
				"To verify On Document Level Numbering Bates Number should get displayed on Production Home Page.");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingDocumentPage(beginningBates, prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.navigateToProductionPage();
		if (page.batesRangeInHomePage(suffixID).isElementAvailable(5)) {
			System.out.println("Bates Range is displayed ");

		}
		base.passedStep("Bates Number is displayed on Production tile");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47929
	 * @Description:To Verify in DAT section of a production configuration step
	 *                 availability of calculated fields
	 */

	@Test(description = "RPMXCON-47929", enabled = true, groups = { "regression" })
	public void verifyDATSectionOfCalculatedFields() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47929- Production Sprint 16");
		base.stepInfo("To Verify in DAT section of a production configuration step availability of calculated fields");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.datMetaDataTiffPageCount();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingDocumentPage(beginningBates, prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified DAT section of a production configuration step availability of calculated fields");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47963
	 * @Description:To Verify Place holder for PDF file type in Production
	 *                 Generation
	 */

	@Test(description = "RPMXCON-47963", enabled = true, groups = { "regression" })
	public void verifyPlaceholderTextForPDF() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47963- Production Sprint 16");
		base.stepInfo("To Verify Place holder for PDF file type in Production Generation");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingPDFWithNativelyProduceddDocsSelectingFileType(tagname, Input.searchString4, Input.orderCriteria,
				Input.pdfFileType);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;

		page.extractFile();
		page.OCR_Verification_In_Generated_PDF(firstFile, lastFile, prefixID, suffixID, Input.searchString4);
		base.stepInfo("verified placeholder in PDF");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47905
	 * @Description:To Verify The Bates No Generated is in Sync with Bates No enter
	 *                 in The Application for Generation
	 */

	@Test(description = "RPMXCON-47905", enabled = true, groups = { "regression" })
	public void verifyBatesNumberSync() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47905- Production Sprint 16");
		base.stepInfo(
				"To Verify The Bates No Generated is in Sync with Bates No enter in The Application for Generation");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, beginningBates);
		base.stepInfo("verified Bates Number Sync With Generates Files");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47911
	 * @Description:To Verify In generated load files, the key is the ID (e.g. bates
	 *                 number) of the produced document, not the DocID of the
	 *                 document
	 */

	@Test(description = "RPMXCON-47911", enabled = true, groups = { "regression" })
	public void verifyKeyIDAndBatesSync() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47911- Production Sprint 16");
		base.stepInfo(
				"To Verify In generated load files, the key is the ID (e.g. bates number) of the produced document, not the DocID of the document");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		String datField = page.fillingDatWithSpecificClassification(Input.productionText, Input.tiffPageCountNam,
				Input.bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, datField);
		base.stepInfo("Verified the key is the ID in generated load files,");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47919
	 * @Description:To Verify In Summary step, Should Display the Redaction
	 *                 Documents Count
	 **/

	@Test(description = "RPMXCON-47919", enabled = true, groups = { "regression" })
	public void verifyRedactedCountInSummary() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-47915- Production Sprint 16");
		base.stepInfo("To Verify In Summary step, Should Display the Redaction Documents Count");
		UtilityLog.info(Input.prodPath);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.selectproject();
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);

		String redactTag = "Redaction" + Utility.dynamicNameAppender();
		RedactionPage redact = new RedactionPage(driver);
		this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
		redact.selectDefaultSecurityGroup();
		redact.manageRedactionTagsPage(redactTag);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString4);
		sessionSearch.viewInDocView();

		DocViewRedactions docview = new DocViewRedactions(driver);
		docview.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docview.selectingRedactionTag(redactTag);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.TIFFSectionEnableBurnRedactionAndSelectRedactedTag(redactTag);
		page.navigateToNextSection();
		String batesNum = page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + batesNum);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();
		String docCount = page.redactedDocCountInSummaryPage().getText();
		int redactedCount = Integer.parseInt(docCount);
		int count = 0;

		if (redactedCount > count) {
			base.passedStep("Redacted count displayed");
		}
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-63064
	 * @Description:Verify that user should be able to change the automatically
	 *                     enabled native placeholdering under TIFF/PDF section from
	 *                     new production
	 **/

	@Test(description = "RPMXCON-63064", enabled = true, groups = { "regression" })
	public void verifyNativePlaceholderInSpreadsheet() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-63064- Production Sprint 16");
		base.stepInfo(
				"Verify that user should be able to change the automatically enabled native placeholdering under TIFF/PDF section from new production");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		page.getTIFFTab().waitAndClick(10);
		page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.verifyEnableNativelyProduceddocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.stepInfo("Create production by selecting PDF and using required inputs");
		String beginningBates2 = page.getRandomNumber(2);
		String productionname2 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String prodName2 = page.addANewProduction(productionname2);
		System.out.println("created a new production - " + prodName2);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		page.getTIFFTab().waitAndClick(10);
		page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.getPDFGenerateRadioButton().waitAndClick(10);
		page.verifyEnableNativelyProduceddocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates2);
		System.out.println("Bates Number is : " + beginningBates2);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-63711
	 * @Description:Field Delimiter values should display consistently on all
	 *                    project selections in Export DAT componen
	 **/

	@Test(description = "RPMXCON-63711", enabled = true, groups = { "regression" })
	public void verifyFieldDelimeter() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-63711- Production Sprint 16");
		base.stepInfo(
				"Field Delimiter values should display consistently on all project selections in Export DAT componen");
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Create Export using required inputs");
		String export = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String exportName = "E" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(export);
		}
		page.addANewExport(exportName);
		page.getDATTab().waitAndClick(10);
		page.getDATChkBox().waitAndClick(10);

		String defaultFielsSep = page.defaultFielsSeperator().getText();
		System.out.println("Default Field seperator -" + defaultFielsSep);

		String defaultTextQualifier = page.defaultTextQualifier().getText();
		System.out.println("Default Text Qualifier -" + defaultTextQualifier);

		String defaultMultiValue = page.defaultMultiValue().getText();
		System.out.println("Default Multi value -" + defaultMultiValue);

		String defaultNewLineSeperator = page.defaultNewLineSeperator().getText();
		System.out.println("Default New Line Seperator -" + defaultNewLineSeperator);
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-64076
	 * @Description:Verify for new production Native, Spreadsheets, and Multimedia
	 *                     checkbox should be checked
	 **/

	@Test(description = "RPMXCON-64076", enabled = true, groups = { "regression" })
	public void verifySpreadsheetAndMultimedia() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-64076- Production Sprint 16");
		base.stepInfo("Verify for new production Native, Spreadsheets, and Multimedia checkbox should be checked");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String prefixID = "A" + Utility.dynamicNameAppender();
		String suffixID = "P" + Utility.dynamicNameAppender();
		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		String defSelectedFiletype = page.ddnselectFileType().getText();
		System.out.println("File type:" + defSelectedFiletype);

		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48865 Date:8/02/2022
	 * @Description:To verify that generated DAT has the header with Space as
	 *                 specified in the production DAT configuration
	 **/

	@Test(description = "RPMXCON-48865", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithSpace() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48865- Production Component");
		base.stepInfo(
				"To verify that generated DAT has the header with Space as specified in the production DAT configuration");
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
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, Input.conceptualSearchString1);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, Input.conceptualSearchString1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48866 Date:8/02/2022
	 * @Description:To verify that generated DAT has the header with Underscore as
	 *                 specified in the production DAT configuration
	 **/

	@Test(description = "RPMXCON-48866", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithUnderscore() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48866- Production Component");
		base.stepInfo(
				"To verify that generated DAT has the header with Underscore as specified in the production DAT configuration");
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "Author_Name" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNumber);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48867 Date:8/02/2022
	 * @Description:To verify that generated DAT has the header with hyphen as
	 *                 specified in the production DAT configuration
	 **/

	@Test(description = "RPMXCON-48867", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithHyphen() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48867- Production Component");
		base.stepInfo(
				"To verify that generated DAT has the header with hyphen as specified in the production DAT configuration");
		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "Begin-Bates" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNumber);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:8/02/2022 TestCase Id :RPMXCON-48864 Description To
	 *         verify that user can add "hyphen" in DAT fields
	 * 
	 */
	@Test(description = "RPMXCON-48864", enabled = true, groups = { "regression" })
	public void verifyingDatFieldInComponentTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass baseClass = new BaseClass(driver);
		base.stepInfo("RPMXCON-48864 -Production Component");
		base.stepInfo("To verify that user can add 'hyphen' in DAT fields");
		String BatesNumber = "Begin-Bates" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(), "Production Page");
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.getMarkCompleteLink().Click();
		baseClass.VerifySuccessMessage("Mark Complete successful");
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		base.passedStep("verified that user can add 'hyphen' in DAT fields");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:8/04/2022 TestCase Id :RPMXCON-49042 Description To
	 *         verify that 'Last Modify Date' should contain the date when the
	 *         production was last "saved" through "Save"
	 * 
	 */

	@Test(description = "RPMXCON-49042", enabled = true, groups = { "regression" })
	public void saveProductionVerifyingModifiedDate() throws Exception {

		BaseClass base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49042 -Production Component");
		base.stepInfo(
				"To verify that 'Last Modify Date' should contain the date when the production was last 'saved' through 'Save'");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(2);
		page.getProductionName().SendKeys(productionname);
		page.getSaveOption().waitAndClick(5);

		page.navigatingToProductionHomePage();
		page.getDatFileLink(productionname).waitAndClick(2);
		page.fillingBasicInfo(productionname1);
		page.getSaveOption().waitAndClick(2);

		page.navigatingToProductionHomePage();
		page.getGridView().waitAndClick(2);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		String createdTime = page.getGridProdValues(productionname1, 11).getText();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date" + date1);
		boolean flag = createdTime.contains(date1);
		if (flag) {
			base.passedStep("current date is displayed on production grid view");
		} else {
			base.failedStep(" current date is not Displayed");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:8/04/2022 TestCase Id :RPMXCON-49043 Description:To
	 *         verify that 'Last Modify Date' should contain the date when the
	 *         production was last "saved" through "Mark Complete"
	 * 
	 */

	@Test(description = "RPMXCON-49043", enabled = true, groups = { "regression" })
	public void markCompleteProductionVerifyingLastModifiedDate() throws Exception {

		BaseClass base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49043 -Production Component");
		base.stepInfo(
				"To verify that 'Last Modify Date' should contain the date when the production was last 'saved' through 'Mark Complete'");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(2);
		page.getProductionName().SendKeys(productionname);
		page.getMarkCompleteLink().waitAndClick(5);

		page.navigatingToProductionHomePage();
		page.getDatFileLink(productionname).waitAndClick(2);
		page.getBackButton().waitAndClick(3);
		page.getMarkInCompleteBtn().waitAndClick(3);
		page.fillingBasicInfo(productionname1);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(2);
		page.navigatingToProductionHomePage();
		page.getGridView().waitAndClick(6);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		String createdTime = page.getGridProdValues(productionname1, 11).getText();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date" + date1);
		boolean flag = createdTime.contains(date1);
		if (flag) {
			base.passedStep("current date is displayed on production grid view");
		} else {
			base.failedStep("date is not contain in text");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48870 Date:8/04/2022
	 * @Description:To verify that DAT file should be generated with field having
	 *                 Space, Underscore and hyphen combinations
	 **/

	@Test(description = "RPMXCON-48870", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithHyphenSpaceAndUnderScore() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48870- Production Component");
		base.stepInfo(
				"To verify that DAT file should be generated with field having Space, Underscore and hyphen combinations");
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String BatesNumber = "Email Author_Name-cc" + page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNumber);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48325 Date:8/04/2022
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 Translations components.
	 **/

	@Test(description = "RPMXCON-48325", enabled = true, groups = { "regression" })
	public void verifyingTranslationLSTToggle() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-48325- Production Component");
		base.stepInfo("To verify that 'Generate Load File' is enabled by default for Translations components.");
		UtilityLog.info(Input.prodPath);
		String ActualColor = "#a9c981";

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.advancedProductionComponentsTranslations();
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedArrow("last"));
		driver.scrollingToBottomofAPage();
		page.verifyingToggleEnabledOrDisabled(page.getTranslationLSTToggle("last"), ActualColor);

	}

	/**
	 * @author Brundha.T No:RPMXCON-48302 Date:8/04/2022
	 * @Description:To verify the value of 'BatesNumber' in production DAT
	 **/

	@Test(description = "RPMXCON-48302", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithBatesNumber() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48302- Production Component");
		base.stepInfo("To verify the value of 'BatesNumber' in production DAT");

		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "B" + Utility.dynamicNameAppender();

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
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 1, 1, prefixID + beginningBates + suffixID);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page = new ProductionPage(driver);
		String beginningBates1 = page.getRandomNumber(2);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.addingDatField(0, Input.bates, Input.batesNumber, BatesNumber);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String name1 = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile1 = new File(home + "/Downloads/VOL0001/Load Files/" + name1 + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile1, 1, 1, prefixID1 + beginningBates1 + suffixID1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48936 Date:8/04/2022
	 * @Description:To verify that after clicking on Generate button from
	 *                 Production-Generate tab, the Generate button is disabled
	 **/

	@Test(description = "RPMXCON-48936", enabled = true, groups = { "regression" })
	public void verifyingGenerateOptionIsDisabled() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48936- Production Component");
		base.stepInfo(
				"To verify that after clicking on Generate button from Production-Generate tab, the Generate button is disabled");

		UtilityLog.info(Input.prodPath);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String ActualColor = "#3276b1";

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
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().isElementAvailable(10);
		page.getbtnProductionGenerate().waitAndClick(5);
		page.verifyingToggleEnabledOrDisabled(page.getbtnProductionGenerate(), ActualColor);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	///

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-47939 Description :To
	 *         Verify in the tree structure of tags, disable and grey out the tags
	 *         that are not of type Privileged.
	 * 
	 */
	@Test(description = "RPMXCON-47939", enabled = true, groups = { "regression" })
	public void verifyPrivilegedSelectedAndOtherTagDisabled() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47939 -Production Component");
		base.stepInfo(
				"To Verify in the tree structure of tags, disable and grey out the tags that are not of type Privileged.");

		String Tagname = "Tag" + Utility.dynamicNameAppender();
		String Tagname1 = "Tag" + Utility.dynamicNameAppender();
		String Tagname2 = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(Tagname1, "Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(Tagname2, "Select Tag Classification");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyPrivTagSelectionAndDisableOfOtherTag(Tagname, Tagname1, Tagname2);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname1, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname2, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-47891 Description :To
	 *         Verify Manage Template Tab
	 * 
	 */
	@Test(description = "RPMXCON-47891", enabled = true, groups = { "regression" })
	public void verifyingManageTemplateInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47891 -Production Component");
		base.stepInfo("To Verify Manage Template Tab");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		driver.waitForPageToBeReady();
		page = new ProductionPage(driver);
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		page.verifyingComponentTabSection();

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49240 Description :To
	 *         verify that Project Admin can enter the information for the bates
	 *         number fields.
	 * 
	 */
	@Test(description = "RPMXCON-49240", enabled = true, groups = { "regression" })
	public void verifyingAdminCanEnterBatesNumber() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-49240 -Production Component");
		base.stepInfo("To verify that Project Admin can enter the information for the bates number fields.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		driver.scrollPageToTop();
		page.getSaveOption().waitAndClick(10);
		base.VerifySuccessMessage("Information Saved Successfully");
		String BatesNumber = page.gettxtBeginningBatesIDPrefix().GetAttribute("value");
		String BatesNumbers = page.gettxtBeginningBatesIDSuffix().GetAttribute("value");
		String[] BatesNumberInSortingTab = { BatesNumber, BatesNumbers };

		for (int i = 0; i < BatesNumberInSortingTab.length; i++) {
			System.out.println(BatesNumberInSortingTab[i]);
			if (BatesNumberInSortingTab[i] != null) {
				base.passedStep("Project admin can enter the bates number field");
			} else {
				base.failedStep("Project admin cannot enter the bates number field");
			}
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49249 Description :To
	 *         verify that if user click on 'X' icon then Next Bates Number should
	 *         not be populated into the bates number fields
	 * 
	 */
	@Test(description = "RPMXCON-49249", enabled = true, groups = { "regression" })
	public void validatingNextBatesNumberNotPopulatedInSortingTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49249 -Production Component");
		base.stepInfo(
				"To verify that if user click on 'X' icon then Next Bates Number should not be populated into the bates number fields");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		page = new ProductionPage(driver);
		String SubBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.getNumbering_Document_RadioButton().waitAndClick(10);
		page.getNumbering_Document_BeginningBates_Text().SendKeys(SubBates);
		page.verifyAvailbleLinkAtNumberingAndSorting();
		base.waitForElement(page.getClickHereLink());
		page.getClickHereLink().Click();
		base.waitTime(2);
		base.ValidateElement_Presence(page.getNextBatesNumber(), "Next Bates Number");
		page.getCloseIconInManageTemplate().waitAndClick(10);
		String actualText = page.getBeginningBates().GetAttribute("value");
		System.out.println("The actual txt" + actualText);
		base.digitCompareEquals(Integer.valueOf(actualText), 0, "Bates Number are not auto populated as expected",
				"Bates Number are auto populated");

		// delete folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49964 Description
	 *         :Verify that if Redaction placeholder text is blank then error should
	 *         be displayed
	 * 
	 */
	@Test(description = "RPMXCON-49964", enabled = true, groups = { "regression" })
	public void verifyingBurnRedactionPlaceholderErrorMsg() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-49964 -Production Component");
		base.stepInfo("Verify that if Redaction placeholder text is blank then error should be displayed");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();
		page.gettextRedactionPlaceHolder().Clear();
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifyErrorMessage(
				"You must specify the redaction text that you want to see in TIFF/PDF burned redactions.");

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/28/2022 TestCase Id :RPMXCON-63062 Description
	 *         :Verify that for new production in TIFF/PDF section native
	 *         placeholdering should be enabled by default with requested text for
	 *         spreadsheets and multimedia files
	 * 
	 */
	@Test(description = "RPMXCON-63062", enabled = true, groups = { "regression" })
	public void verifyNativeFileWithRequestedText() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-63062 -Production Component");
		base.stepInfo(
				"Verify that for new production in TIFF/PDF section native placeholdering should be enabled by default with requested text for spreadsheets and multimedia files");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		driver.waitForPageToBeReady();
		String ActualText = page.getNativeDocsPlaceholderText().getText();
		String ExpectedText = "Document Produced in Native Format.";
		base.textCompareEquals(ActualText, ExpectedText, "Default text in native placeholder is displayed as expected",
				"Text is not Displayed as expected");
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/28/2022 TestCase Id :RPMXCON-49963 Description
	 *         Verify that RED text should be displayed in Abbreviated Text box in
	 *         TIFF/PDF
	 * 
	 */
	@Test(description = "RPMXCON-49963", enabled = true, groups = { "regression" })
	public void verifyRedTextInBurnRedactionToggle() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-49963 -Production Component");
		base.stepInfo("Verify that RED text should be displayed in Abbreviated Text box in TIFF/PDF");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.burnRedactionWithRedactionTagInTiffSection(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();
		String BurnRedactionText = page.getBurnRedactionText().GetAttribute("value");
		base.textCompareEquals(BurnRedactionText, Input.stampRed, "BurnRedaction abbreviated text is displayed ",
				"Text is not displayed");
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-47869 Description To
	 *         Verify in production, the DAT check box selection is mandatory to
	 *         Save the Production Component Section
	 * 
	 */
	@Test(description = "RPMXCON-47869", enabled = true, groups = { "regression" })
	public void verifyingDATSectionIsMandatoryForProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47869 -Production Component");
		base.stepInfo(
				"To Verify in production, the DAT check box selection is mandatory to Save the Production Component Section");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(), "Production Page");
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.scrollPageToTop();
		base.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();
		page.getMarkCompleteLink().Click();
		base.VerifyErrorMessage("Selection of the DAT component is mandatory for a production.");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-47874 Description To
	 *         Verify Redaction section of TIFF and PDF components, Insert Metadata
	 *         field options availability.
	 * 
	 */
	@Test(description = "RPMXCON-47874", enabled = true, groups = { "regression" })
	public void verifyingInsertMetaDataInRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47874 -Production Component");
		base.stepInfo(
				"To Verify Redaction section of TIFF and PDF components, Insert Metadata field options availability.");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		page.gettextRedactionPlaceHolder().Clear();
		page.getBurnRedaction_InsertMetaData().waitAndClick(3);
		page.getNativeMetaDataFieldDropdown().selectFromDropdown().selectByVisibleText(Input.batesNumber);
		page.getPopUpOkButtonInserMetaData().waitAndClick(3);
		String PaceholderText = page.gettextRedactionPlaceHolder().getText();
		base.compareTextViaContains(PaceholderText, Input.batesNumber, "Placeholder text is with Inserted meta data",
				"Placeholder is not updated with metadata");
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-48862 Description To
	 *         verify that user can add "space" in DAT fields
	 * 
	 */
	@Test(description = "RPMXCON-48862", enabled = true, groups = { "regression" })
	public void verifyingDatField() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass baseClass = new BaseClass(driver);
		base.stepInfo("RPMXCON-48862 -Production Component");
		base.stepInfo("To verify that user can add 'space' in DAT fields");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(), "Production Page");
		page.addANewProduction(productionname);
		baseClass.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();
		page.getDATTab().Click();
		driver.waitForPageToBeReady();
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.batesNumber);
		baseClass.waitForElement(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(Input.conceptualSearchString1);
		page.getMarkCompleteLink().Click();
		baseClass.VerifySuccessMessage("Mark Complete successful");
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		base.passedStep("verified that user can add 'space' in DAT fields");

		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-48863 Description To
	 *         verify that user can add "underscore" in DAT fields
	 * 
	 */
	@Test(description = "RPMXCON-48863", enabled = true, groups = { "regression" })
	public void verifyingDatFieldInComponentTab2() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass baseClass = new BaseClass(driver);
		base.stepInfo("RPMXCON-48863 -Production Component");
		base.stepInfo("To verify that user can add 'underscore' in DAT fields");
		String BatesNumber = "A_Author" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(), "Production Page");
		page.addANewProduction(productionname);
		baseClass.waitForElement(page.getDATChkBox());
		page.getDATChkBox().Click();
		page.getDATTab().Click();
		driver.waitForPageToBeReady();
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);
		page.getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.batesNumber);
		page.getDAT_DATField1().SendKeys(BatesNumber);
		page.getMarkCompleteLink().Click();
		baseClass.VerifySuccessMessage("Mark Complete successful");
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		base.passedStep("verified that user can add 'underscore' in DAT fields");
		loginPage.logout();
	}

///

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
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
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
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
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
		driver.waitForPageToBeReady();
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
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
		driver.waitForPageToBeReady();
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
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
		base.textCompareEquals(actualText, beginningBates, "BatesNumber Retained on MarkComplete",
				" BatesNumber not Retained");
		base.textCompareEquals(actualTextInPrefix, prefixID, "BatesNumber Retained on MarkComplete",
				" BatesNumber not Retained");

		loginPage.logout();

	}

	///

	/**
	 * @author Brundha.T No:RPMXCON-47776 Date:8/09/2022
	 * @Description:To Verify In-Progress/Complete Production the availability of
	 *                 'Delete' Option in drop down action menu should be disable
	 **/

	@Test(description = "RPMXCON-47776", enabled = true, groups = { "regression" })
	public void verifyDeleteOptionIsDisabled() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-47776- Production Component");
		base.stepInfo(
				"To Verify In-Progress/Complete Production the availability of 'Delete' Option in drop down action menu should be disable");

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
		page.getbtnProductionGenerate().isElementAvailable(10);
		page.getbtnProductionGenerate().waitAndClick(5);
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");
		page.navigateToProductionPage();
		page.getGearIconForProdName(productionname).waitAndClick(5);
		String ActualText = page.getDeletOption(productionname).GetAttribute("class");
		driver.waitForPageToBeReady();
		base.compareTextViaContains(ActualText, "disable", "Delete option is disabled as expected",
				"Delete Option is not disabled as expected");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-47714 Date:8/09/2022
	 * @Description:To Verify Admin will be able do Privileged Doc Check in Priv
	 *                 guard section by specifying various privileged rules.
	 **/

	@Test(description = "RPMXCON-47714", enabled = true, groups = { "regression" })
	public void verifyTagSelectedInTextBox() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-47714- Production Component");
		base.stepInfo(
				"To Verify Admin will be able do Privileged Doc Check in Priv guard section by specifying various privileged rules.");

		UtilityLog.info(Input.prodPath);

		tagname = "tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		String ActualText = page.TagInTextBox().getText();
		base.compareTextViaContains(ActualText, tagname, "Tagname is displayed in the query box",
				"Tagname is not displayed");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-47971 Date:8/09/2022
	 * @Description:To Verify Type dropdown in the DAT section of the production for
	 *                 all the correct values
	 **/

	@Test(description = "RPMXCON-47971", enabled = true, groups = { "regression" })
	public void verifyingSourceFieldOrderInDatSection() throws Exception {

		UtilityLog.info(Input.prodPath);

		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47971- Production Component");
		base.stepInfo("To Verify Type dropdown in the DAT section of the production for all the correct values");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.getDATChkBox().waitAndClick(2);
		page.getDATTab().waitAndClick(2);
		driver.waitForPageToBeReady();
		page.getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);
		List<String> textBoxValue = base.availableListofElements(page.getDAT_SourceField());
		base.verifyOriginalSortOrder(textBoxValue, textBoxValue, "Ascending", true);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48497 Date:8/09/2022
	 * @Description:To verify that after clicking on InComplete button on Production
	 *                 Components, last selected Native File Group types should be
	 *                 retained.
	 **/

	@Test(description = "RPMXCON-48497", enabled = true, groups = { "regression" })
	public void verifyingNativeFileTypeChecked() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-48497- Production Component");
		base.stepInfo(
				"To verify that after clicking on InComplete button on Production Components, last selected Native File Group types should be retained.");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectingNativeFileType();
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		page.getMarkInCompleteBtn().waitAndClick(2);
		page.getNativeChkBox().Click();
		base.waitTime(1);
		page.getCheckBoxCheckedVerification(page.getNativeCheckBoxChecked(Input.dbFile));
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-47818 Date:8/09/2022
	 * @Description:To verify Redacted Document Count as per Source Selection in
	 *                 Production on Priv Guard Page should be displayed
	 **/

	@Test(description = "RPMXCON-47818", enabled = true, groups = { "regression" })
	public void verifyRedactedDocumentCountInPrivGuard() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-47818- Production Component");
		base.stepInfo(
				"To verify Redacted Document Count as per Source Selection in Production on Priv Guard Page should be displayed");

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String Redactiontag1 = "Redaction" + Utility.dynamicNameAppender();
		int Doc = Integer.valueOf(Input.pageCount);
		String productionname = "Prod" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		docView.documentSelection(Doc);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 40, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.priviledgeDocCheck(false, Redactiontag1);
		String docCount = page.getDocumentSelectionLink().getText();
		base.digitCompareEquals(Integer.valueOf(docCount), Doc, "Redacted document count is displayed as expected",
				"Redacted document count is not as expected");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-47871 Date:8/09/2022
	 * @Description To Verify that user should be able to select search under
	 *              'Select Searches' source for Document selection tab in
	 *              Production
	 * 
	 */
	@Test(description = "RPMXCON-47871", enabled = true, groups = { "regression" })
	public void verifyProductionGenerationWithSearch() throws Exception {

		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47871 -Production Component");
		base.stepInfo(
				"To Verify that user should be able to select search under 'Select Searches' source for Document selection tab in Production");

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(SearchName, null);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48018 Date:8/09/2022
	 * @DescriptionTo Verify In Native section User should be able to select one or
	 *                more tags without selecting any file types.
	 * 
	 */
	@Test(description = "RPMXCON-48018", enabled = true, groups = { "regression" })
	public void verifySelectedTagInNativeSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48018 -Production component");
		base.stepInfo(
				"To Verify In Native section User should be able to select one or more tags without selecting any file types.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTag(tagname, tagname1, false);
		page.nativeFileTypeCheckBox(Input.MetaDataFileType).waitAndClick(3);
		page.nativeFileTypeCheckBox(Input.NativeFileType).waitAndClick(3);
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		page.getNativeTab().waitAndClick(3);
		driver.waitForPageToBeReady();
		String NativeTag = page.getNativeTags().getText();
		String Tags = tagname + "," + tagname1;
		base.textCompareEquals(Tags, NativeTag, "Native tag is displayed in production component as expecetd",
				"Tag is not displayed as expected");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48324 Date:8/09/2022
	 * @Description:To verify that "Generate Load File" is enabled by default for
	 *                 'MP3' components.
	 **/

	@Test(description = "RPMXCON-48324", enabled = true, groups = { "regression" })
	public void verifyingLSTToggleInMp3Tab() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-48324- Production Component");
		base.stepInfo("To verify that 'Generate Load File' is enabled by default for 'MP3' components.");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		page.SelectMP3FileAndVerifyLstFile();
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49376 Date:8/12/2022
	 * @Description:To verify that when the option "do not export PDFs for natively
	 *                 produced docs" is enabled,and document is associated to
	 *                 'TechIssue', then production should generate without any
	 *                 error
	 */
	@Test(description = "RPMXCON-49376", enabled = true, groups = { "regression" })

	public void GenerateProductionForTechIssueDocument() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49376-from Production Component");
		base.stepInfo(
				"To verify that when the option 'do not export PDFs for natively produced docs' is enabled,and document is associated to 'TechIssue', then production should generate without any error");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectTechIssueDoc(tagname);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-49130 Date:8/12/2022
	 * @Description:To verify that in Production-Slip Sheet, Metadata Field should
	 *                 be sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49130", enabled = true, groups = { "regression" })
	public void verifyingSlipSheetMetaData() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49130- Production Component");
		base.stepInfo("To verify that in Production-Slip Sheet, Metadata Field should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.selectGenerateOption(false);
		page.slipSheetToggleEnable();
		driver.waitForPageToBeReady();
		List<String> MetaDataField = base.availableListofElements(page.getMetaDataValues());
		base.verifyOriginalSortOrder(MetaDataField, MetaDataField, "Ascending", true);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname1);
		page.selectGenerateOption(true);
		page.slipSheetToggleEnable();
		driver.scrollingToBottomofAPage();
		List<String> PDFMetaDataField = base.availableListofElements(page.getMetaDataValues());
		base.verifyOriginalSortOrder(PDFMetaDataField, PDFMetaDataField, "Ascending", true);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-49132 Date:8/12/2022
	 * @Description:To verify that in Production-Slip Sheet, Calculated Field should
	 *                 be sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49132", enabled = true, groups = { "regression" })
	public void verifyingSlipSheetCalculatedValues() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49132- Production Component");
		base.stepInfo("To verify that in Production-Slip Sheet, Calculated Field should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.selectGenerateOption(false);
		page.slipSheetToggleEnable();
		driver.scrollingToBottomofAPage();
		page.getSlipSheetCalculatedTab().waitAndClick(5);
		List<String> CalculatedFieldValue = base.availableListofElements(page.getCalculatedValues());
		base.verifyOriginalSortOrder(CalculatedFieldValue, CalculatedFieldValue, "Ascending", true);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname1);
		page.selectGenerateOption(true);
		page.slipSheetToggleEnable();
		driver.scrollingToBottomofAPage();
		page.getSlipSheetCalculatedTab().waitAndClick(5);
		List<String> PDFCalculatedFieldValue = base.availableListofElements(page.getCalculatedValues());
		base.verifyOriginalSortOrder(PDFCalculatedFieldValue, PDFCalculatedFieldValue, "Ascending", true);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48319 Date:8/12/2022
	 * @Description:To verify that When the "Natives" component is selected and if
	 *                 no file group type OR no tag is selected, then system should
	 *                 provide Error message "You must select at least a file group
	 *                 type or a tag in Native component." during Mark Complete
	 *                 action.
	 **/

	@Test(description = "RPMXCON-48319", enabled = true, groups = { "regression" })
	public void verifyingErrorMsgInNativeSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-48319- Production Component");
		base.stepInfo(
				"To verify that When the \"Natives\" component is selected and if no file group type OR no tag is selected, then system should provide Error message \"You must select at least a file group type or a tag in Native component.\" during Mark Complete action.");

		String ExpectedMsg = "To export natives, you must select at least a file group type or a tag in the Native section.";
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getNativeTab().Click();
		driver.waitForPageToBeReady();
		page.nativeFileTypeCheckBox(Input.MetaDataFileType).waitAndClick(2);
		page.nativeFileTypeCheckBox(Input.NativeFileType).waitAndClick(2);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifyErrorMessage(ExpectedMsg);

		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47615 Date:8/12/2022
	 * @Description:To Verify ProjectAdmin will be able to enter document selection
	 *                 and output information on the self production wizard
	 */
	@Test(description = "RPMXCON-47615", enabled = true, groups = { "regression" })

	public void verifyDocCountInDocumentSelectionTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47615-from Production Component");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to enter document selection and output information on the self production wizard");

		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
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
		driver.scrollPageToTop();
		page.getMarkCompleteLink().Click();
		base.waitTime(2);
		String DocCount = page.getDocumentSelectionLink().getText();
		System.out.println("Document Count is:" + DocCount);
		base.digitCompareEquals(PureHit, Integer.valueOf(DocCount), "Total Document count is displayed ",
				"Document is not dispalyed");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-47972 Date:8/12/2022
	 * @Description:To Verify Production should not error out for (Bates Number) in
	 *                 SlipSheet.
	 **/

	@Test(description = "RPMXCON-47972", enabled = true, groups = { "regression" })
	public void verifyingSlipSheetBatesNumberWithoutError() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47972- Production Component");
		base.stepInfo("To Verify Production should not error out for (Bates Number) in SlipSheet.");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.slipSheetToggleEnable();
		page.getSlipSheetCalculatedTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		page.getMetaDataFieldCheckBox(Input.batesNumber).waitAndClick(5);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-49122 Date:8/12/2022
	 * @Description:To verify that in Production-Privileged Placeholder section,
	 *                 Metadata Field drop down should be sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49122", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrerInPrivDocMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49122- Production Component");
		base.stepInfo(
				"To verify that in Production-Privileged Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.getTIFFTab().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getTIFF_EnableforPrivilegedDocs());
		page.getPrivInsertMetadataField().waitAndClick(2);
		List<String> CalculatedFieldValue = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(CalculatedFieldValue, CalculatedFieldValue, "Ascending", true);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.getTIFFTab().waitAndClick(5);
		driver.scrollPageToTop();
		page.getPDFGenerateRadioButton().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getTIFF_EnableforPrivilegedDocs());
		page.getPrivInsertMetadataField().waitAndClick(2);
		List<String> PDFCalculatedFieldValue = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFCalculatedFieldValue, PDFCalculatedFieldValue, "Ascending", true);
		loginPage.logout();

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
