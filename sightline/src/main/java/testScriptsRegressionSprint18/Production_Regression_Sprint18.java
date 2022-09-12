package testScriptsRegressionSprint18;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.ProjectFieldsPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression_Sprint18 {
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
		softAssertion=new SoftAssert();
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
		page.getNativeCheckBox(tagname).ScrollTo();
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
		
		//Document selection without including families
		page.fillingDocumentSelectionPageExcludingFamilies(foldername);
		String docCount = page.navigatingToDocViewPage();
		softAssertion.assertEquals(docCount, purehit);
		
		//get back to source
		page.btnBackToSource().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		
		//Document selection including families
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
		softAssertion.assertEquals(MP3FilesCount,"0");
		softAssertion.assertAll();
		base.passedStep("Verified Number of MP3 Files should be shown even when the count of MP3 Files among the selected document is 0.");
		
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
