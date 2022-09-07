package testScriptsRegressionSprint19;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
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

public class ProductionRegression_Sprint19 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
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
		page = new ProductionPage(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
	}

	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47847
	 * @Description:To Verify the Create/Display/View of Template with newly created
	 *                 Security Group.
	 **/
	@Test(description = "RPMXCON-47847", enabled = true, groups = { "regression" })
	public void verifyTemplateInSecurityGroup() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-47847 Production Component Sprint 19");
		base.stepInfo("To Verify the Create/Display/View of Template with newly created Security Group.");
		
		String securityGroup = "Security" + UtilityLog.dynamicNameAppender();
		String productionSet = "ProdSet" + UtilityLog.dynamicNameAppender();
		String template = "Template" + UtilityLog.dynamicNameAppender();

		// add new security group
		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// select created security group
		productionname = "p" + Utility.dynamicNameAppender();

		// create new production set
		page.navigateToProductionPage();
		page.CreateProductionSets(productionSet);
		page.navigateToProductionPageByNewProductionSet(productionSet);

		// create production and save as custom template
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingCreatedSecurityGroup(securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyInManageTemplateTab(productionname, template);
		loginPage.logout();

	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-48869
	 * @Description:To verify that if user added leading space for DAT field, then
	 *                 after generating the production, warning message should be
	 *                 displayed
	 **/
	@Test(description = "RPMXCON-48869", enabled = true, groups = { "regression" })
	public void verifyErrorMessageinDATWithLeadSpc() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-48869");
		base.stepInfo(
				"To verify that if user added leading space for DAT field, then after generating the production, warning message should be displayed");
		String expected = "DAT field names must begin with an alphabet. Please check.";

		productionname = "p" + Utility.dynamicNameAppender();
		String batesWithSpaces = " " + "B" + " " + page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithSpaces);
		page.getComponentsMarkComplete().waitAndClick(5);
		base =new BaseClass(driver);
		base.VerifyErrorMessage(expected);
		base.passedStep(
				"verified - that if user added leading space for DAT field, then after generating the production, warning message should be displayed");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-50015
	 * @Description:To Verify that help text should be displays as This is not a
	 *                 searchable field for 'AllProductionBatesRanges' metadata"
	 **/
	@Test(description = "RPMXCON-50015", enabled = true, groups = { "regression" })
	public void verifyHelpTxtForAllProdBateRange() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-50015");
		base.stepInfo(
				"To Verify that help text should be displays as This is not a searchable field for 'AllProductionBatesRanges' metadata");
		String expHelpText = "Due to the nature of the content, this field cannot be made searchable";
		String fieldName = "AllProductionBatesRanges";
		
		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);
        base = new BaseClass(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.applyFilterByFilterName(fieldName);
		base.waitTillElemetToBeClickable(projectField.getApplyButton());
		base.waitForElement(projectField.getFieldNameEdititButton(fieldName));
		projectField.getFieldNameEdititButton(fieldName).waitAndClick(3);
		base.waitTillElemetToBeClickable(projectField.getFieldNameEdititButton(fieldName));
		base.waitForElement(projectField.getIsSearcHelpBtn());
		projectField.getIsSearcHelpBtn().waitAndClick(3);
		base.waitForElement(projectField.getIsSearcHelpTxt());
		String actHelpText = projectField.getIsSearcHelpTxt().getText();
		if (actHelpText.equals(expHelpText)) {
			base.passedStep("Help text for AllProductionBatesRanges metadata is displaying as Expected");
		} else {
			base.failedStep("Help text for AllProductionBatesRanges metadata is Not displaying as Expected");
		}
		base.passedStep(
				"Verified - that help text should be displays as \"This is not a searchable field.\" for 'AllProductionBatesRanges' metadata");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49111
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected with
	 *                 few File types
	 **/
	@Test(description = "RPMXCON-49111", enabled = true, groups = { "regression" })
	public void verifyNativeCount() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-49111");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected with few File types");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.searchString5);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";

		if (!(nativeCount == count)) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if Native component");
		} else {
			base.failedStep("verification not successful");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49115
	 * @Description:To verify that the value of Number of MP3 Files on
	 *                 Production-Summary tab if MP3 Files component is selected.
	 **/

	@Test(description = "RPMXCON-49115", enabled = true, groups = { "regression" })
	public void verifyMP3Count() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-49115 Production Component Sprint 19");
		base.stepInfo(
				"To verify that the value of Number of MP3 Files on Production-Summary tab if MP3 Files component is selected.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
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
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";

		if (!(nativeCount == count)) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if Native component");
		} else {
			base.failedStep("verification not successful");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49112
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected and
	 *                 document is Privileged
	 **/
	@Test(description = "RPMXCON-49112", enabled = true, groups = { "regression" })
	public void verifyNativeCountWithPrivTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-49112");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected and document is Privileged");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		base.stepInfo("created a privileged tag");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";

		if (!(nativeCount == count)) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if  Native component is selected and document is Privileged");
		} else {
			base.failedStep("verification not successful");
		}

		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-48499
	 * @Description:To verify that after clicking on InComplete button on Production
	 *                 Components, last selected Native File Group types and Tags
	 *                 should be displayed
	 **/
	public void verifySelectedTypesTags_MarkIncomplete(String userName, String password) throws Exception {
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48499 Production");
		base.stepInfo(
				"To verify that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");

		// create tag and folder
		base =new BaseClass(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getNativeChkBox());
		page.getNativeChkBox().Click();
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.clickComponentMarkCompleteAndIncomplete();
		base.waitForElement(page.getNativeTab());
		page.getNativeTab().Click();

		List<String> types = base.availableListofElementsWithAttributeValues(page.getAllNativeFileTypes(), "value");
		for (String type : types) {
			System.out.println(type);
			page.verifyingNativeSectionFileType(type);
		}
		base.passedStep("All Native File Types are Selected After Mark InComplete");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNativeSelectTags());
		page.getNativeSelectTags().waitAndClick(10);
		base.waitForElement(page.getNativeCheckBox(tagname));
		driver.waitForPageToBeReady();
		if (page.getNativeCheckBox(tagname).GetAttribute("class").contains("clicked")) {
			base.passedStep(
					"Verified - that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");
		} else {
			base.failedStep(
					"after clicking on InComplete button on Production Components, last selected Tags not displayed");
		}
		loginPage.logout();
	}
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49981
	 * @Description:To verify that RMU can view the existing productions in his
	 *                 Security Group
	 **/
	@Test(description = "RPMXCON-49981", enabled = true, groups = { "regression" })
	public void verifyProductionFromRMU() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-49981 Production Component Sprint 19");
		base.stepInfo("To verify that RMU can view the existing productions in his Security Group");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String productionNameInPA = page.addANewProduction(productionname);
		System.out.println(productionNameInPA);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page.navigateToProductionPage();
		base.waitForElement(page.getProductionFromHomepage(productionname));
		String productionNameInRMU = page.getProductionFromHomepage(productionname).getText();
		System.out.println(productionNameInRMU);
		softAssertion.assertEquals(productionNameInPA, productionNameInRMU);
		softAssertion.assertAll();
		base.passedStep("verified that RMU can view the existing productions in his Security Group");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49816
	 * @Description:To verify that Audio production with redaction should generate
	 *                 successfully if bates number includes hyphens with space
	 **/
	@Test(description = "RPMXCON-49816", enabled = true, groups = { "regression" })
	public void verifyBatesWithHypensAndSpace() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-49816 Production Component Sprint 19");
		base.stepInfo(
				"To verify that Audio production with redaction should generate successfully if bates number includes hyphens with space");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String batesWithHypenAndSpace = "B" + " " + page.getRandomNumber(2) + "-";
		System.out.println(batesWithHypenAndSpace);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithHypenAndSpace);
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, batesWithHypenAndSpace);
		base.passedStep(
				"verified that Audio production with Redaction should generate successfully if bates number contains Hypen");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47896
	 * @Description:To Verify Sorting options from numbering and sorting Section of
	 *                 production wizard
	 **/
	@Test(description = "RPMXCON-47896", enabled = true, groups = { "regression" })
	public void verifyDefaultSelectionInNumbering() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-47896 Production Component Sprint 19");
		base.stepInfo("To Verify Sorting options from numbering and sorting Section of production wizard");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();

		// verify sort by metadata is clicked by default
		page.verifyDefaultSelection_SortByMetadata();

		// verify sort by selected tags
		page.verifySortByTags_SortedByAscending();

		// verify custom sort link
		page.verifyCustomSort_Link();

		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47938
	 * @Description:To Verify Sorting Based on Tag in Production
	 **/
	@Test(description = "RPMXCON-47938", enabled = true, groups = { "regression" })
	public void verifySortingInTag() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-47938 Production Component Sprint 19");
		base.stepInfo("To Verify Sorting Based on Tag in Production");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();

		// verify sort by selected tags
		base.waitForElement(page.getSortingRadioBtn());
		page.getSortingRadioBtn().waitAndClick(10);

		List<String> availableTags = base.availableListofElements(page.getTotalTagsInSorting());
		List<String> availableTags2 = base.availableListofElements(page.getTotalTagsInSorting());
		System.out.println(availableTags);
		base.verifyOriginalSortOrder(availableTags, availableTags2, "Ascending", true);
		base.passedStep("'Available Tags' are sorted");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47719
	 * @Description:To Verify ProjectAdmin will be able to enter production
	 *                 components information on the self production wizard
	 **/
	@Test(description = "RPMXCON-47719", enabled = true, groups = { "regression" })
	public void verifySelfProductionWizard() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-47719 Production Component Sprint 19");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to enter production components information on the self production wizard");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingAdvancedProductionComponent();
		page.fillingMP3();
		page.getComponentsMarkComplete().waitAndClick(10);
		base.passedStep("Following components can be selected   DAT,TIFF,NATIVE,PDF,TEXT,MP3 etc");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49101
	 * @Description:To verify that EmailAuthorNameAndAddress,
	 *                 EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and
	 *                 EmailBCCNamesAndAddresses fields should be display properly
	 *                 in the correct format in the DAT.
	 **/
	@Test(description = "RPMXCON-49101", enabled = true, groups = { "regression" })
	public void verifyDATWithEmailClassification() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-49101 Production Component Sprint 19");
		base.stepInfo(
				"To verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses fields should be display properly in the correct format in the DAT.");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String productionName = page.addANewProduction(productionname);
		System.out.println(productionName);
		page.fillingDATSection();
		page.addNewFieldOnDAT();
		page.addDatField(1, "Email", "EmailAuthorNameAndAddress");
		page.addNewFieldOnDAT();
		page.addDatField(2, "Email", "EmailToNamesAndAddresses");
		page.addNewFieldOnDAT();
		page.addDatField(3, "Email", "EmailBCCNamesAndAddresses");
		page.addNewFieldOnDAT();
		page.addDatField(4, "Email", "EmailCCNamesAndAddresses");
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.extractFile();

		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47741
	 * @Description:To Verify default Filter By selections for Production status
	 *                 (In-Progress, ...). Verify for both Grid and Tile view
	 **/
	@Test(description = "RPMXCON-47741", enabled = true, groups = { "regression" })
	public void verifyFilterByINPROGRESS() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47741");
		base.stepInfo(
				"To Verify default Filter By selections for Production status (In-Progress, ...). Verify for both Grid and Tile view");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		BaseClass base = new BaseClass(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// Create Prod for failed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Create Prod for Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

		// Create Prod for Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		// view all state
		page = new ProductionPage(driver);
		base.waitForElement(page.getFilterByButton());
		page.getFilterByButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getElementDisplayCheck(page.getFilterByDRAFT());
		page.getElementDisplayCheck(page.getFilterByINPROGRESS());
		page.getElementDisplayCheck(page.getFilterByFAILED());
		page.getElementDisplayCheck(page.getFilterByCOMPLETED());
		base.stepInfo("All option is available in the list");
		base.waitForElement(page.getFilterByINPROGRESS());
		page.getFilterByINPROGRESS().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getRefreshButton().waitAndClick(10);
		base.stepInfo("Filtered all options");

		// view INPROGRESS status only
		page.filterInprogressProduction();
		page.verifyProdctionState(page.getProductionSate(), "INPROGRESS");

		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();

		int status = base.getIndex(page.getGridWebTableHeader(), "STATUS");
		driver.waitForPageToBeReady();
		page.verifyProductionStateWebTableGridView(status, "INPROGRESS");

		base.passedStep(
				"Verified - that default Filter By selections for Production status (In-Progress, ...). Verify for both Grid and Tile view");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}
	
	@Test(description = "RPMXCON-47739", enabled = true, groups = { "regression" })
	public void verifySortByinTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47739");
		base.stepInfo(
				"To Verify the default Sorting and required sequence of sorting. And default production sorting (Most recent First). Tile View.");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

//		// Pre-requisites
//		// create tag 
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		BaseClass base = new BaseClass(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

//	//  Create Prod for failed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Create Prod for Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

//		  Create Prod for Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		if (page.getLoadMore().Enabled()) {
			do {
				driver.scrollingToBottomofAPage();
				if(page.getNoProdExistText().isElementAvailable(4)) {
					break;
				}
			} while (page.getLoadMore().Visible());
		}

		base.waitForElementCollection(page.getProductionSate());
		List<String> expProdStatusOrder = base.getAvailableListofElements(page.getProductionSate());
		System.out.println(expProdStatusOrder);
		Collections.sort(expProdStatusOrder);
		System.out.println(expProdStatusOrder);
		base.stepInfo("Before Sorting : " + expProdStatusOrder);

		base.waitForElement(page.getSortByButton());
		page.getSortByButton().selectFromDropdown().selectByValue("Status");
		base.waitForElement(page.getRefreshButton());
		page.getRefreshButton().Click();
		driver.waitForPageToBeReady();

		if (page.getLoadMore().Enabled()) {
			do {
				driver.scrollingToBottomofAPage();
				if(page.getNoProdExistText().isElementAvailable(4)) {
					break;
				}
			} while (page.getLoadMore().Visible());
		}

		base.waitForElementCollection(page.getProductionSate());
		List<String> actProdStatusOrder = base.getAvailableListofElements(page.getProductionSate());
		System.out.println(actProdStatusOrder);
		base.stepInfo("After Sorting : " + actProdStatusOrder);

		if (expProdStatusOrder.equals(actProdStatusOrder)) {
			base.passedStep("Productions list sorted as per the selected list in Tile View   ");
		} else {
			base.failedStep("Productions list Not sorted as per the selected list in Tile View   ");
		}

		base.passedStep(
				"Verified that default Sorting and required sequence of sorting. And default production sorting (Most recent First). Tile View.");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47816
	 * @Description:To Verify Production Guard Page option (close) Issue for Tag and
	 *                 Redaction.
	 **/
	@Test(description = "RPMXCON-47816", enabled = true, groups = { "regression" })
	public void verifyProdGuardCloseOpt() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47816");
		base.stepInfo("To Verify Production Guard Page option (close) Issue for Tag and Redaction");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		base.waitForElementCollection(page.getPrivGuardTextBox_Value());
		page.fillingPrivGuardPage();
		base.stepInfo("Navigated to Production location page");

		// Click Back Btn in production location page
		page.clickBackBtnUntilElementFound(page.getAddRuleBtn());
		base.stepInfo("Navigated Back to Priv Guard page");

		if (page.getRemoveLink().Visible()) {
			base.failedStep("Remove Option displaying after Mark Complete");
		} else {
			base.passedStep("Remove Option Not displaying after Mark Complete");
		}

		base.waitForElement(page.getMarkInCompleteBtn());
		page.getMarkInCompleteBtn().waitAndClick(3);

		if (page.getRemoveLink().Visible()) {
			base.passedStep("Remove Option displaying after Mark InComplete");
		} else {
			base.failedStep("Remove Option Not displaying after Mark Complete");
		}

		base.passedStep("verified - that Production Guard Page option (close) Issue for Tag and Redaction");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47998
	 * @Description:To Verify In Priv Guard, the message saying that the production
	 *                 has privileged documents
	 **/
	@Test(description = "RPMXCON-47998", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyPrivGuardNotDisWarniMSg(String userName, String password) throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(userName, password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47998");
		base.stepInfo("To Verify In Priv Guard, the message saying that the production has privileged documents "
				+ "even when there are no privileged documents");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("Warning message not be displayed as there is no Priv document in selected document source");
		base.passedStep("Verified In Priv Guard, the message saying that the production has privileged documents "
				+ "even when there are no privileged documents");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-63080
	 * @Description:To Verify production regeneration without any change in default
	 *                 enabled native placeholder under TIFF section
	 **/
	@Test(description = "RPMXCON-63080", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyProdRegeFrDefNatPHTiff(String userName, String password) throws Exception {

		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-63080");
		base.stepInfo("To Verify production regeneration without any change in "
				+ "default enabled native placeholder under TIFF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndRegeneratingAgain();
		page.getbtnContinueGeneration().isElementAvailable(320);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}
		page.getbtnContinueGeneration().waitAndClick(10);
		base.waitForElement(page.getQC_Download());
		page.getQC_Download().Click();
		base.waitForElement(page.getQC_Downloadbutton_allfiles());
		page.getQC_Downloadbutton_allfiles().Click();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();

		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File tiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		System.out.println(DatFile);

		if (Native.exists()) {
			base.passedStep("Native placeholder generated for the selected file type ");
		} else {
			base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		if (tiffFile.exists()) {
			base.passedStep("Tiff is generated successfully");
		} else {
			base.failedStep("Tiff is not generated successfully");
		}
		base.passedStep("Verify production regeneration without any change in "
				+ "default enabled native placeholder under TIFF section");
		loginPage.logout();
	}
	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-63084
	 * @Description:To Verify production regeneration with change(additional, change
	 *                 existing file type to spreadsheet only) in default enabled
	 *                 native placeholder under PDF section
	 **/
	@Test(description = "RPMXCON-63084", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyProdRegeFrDefNatPHpdf(String userName, String password) throws Exception {

		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-63084");
		base.stepInfo("To Verify production regeneration with change(additional, change "
				+ "existing file type to spreadsheet only) in default enabled native placeholder under PDF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();

		String name = page.getProduction().getText().trim();
		String home = System.getProperty("user.home");
		File file = new File(home + "/Downloads/" + name + ".zip");
		File file1 = new File(Input.fileDownloadLocation + name + ".zip");

		if (file.exists()) {
			driver.waitForPageToBeReady();
			file.delete();
		} else if (file1.exists()) {
			driver.waitForPageToBeReady();
			file1.delete();
		}

		base.waitForElement(page.getQC_backbutton());
		page.getQC_backbutton().waitAndClick(10);

		base.waitForElement(page.getMarkInCompleteBtn());
		page.getMarkInCompleteBtn().waitAndClick(10);

		page.clickBackBtnUntilElementFound(page.getProductionName());
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);

		driver.waitForPageToBeReady();
		base.waitForElement(page.getbtnComponentsMarkIncomplete());
		page.getbtnComponentsMarkIncomplete().waitAndClick(10);
		;

		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();

		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getSelectMultiFileTypeInTifffNative(2, "Images"));
		page.getSelectMultiFileTypeInTifffNative(2, "Images").waitAndClick(5);
		driver.scrollPageToTop();

		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();

		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		System.out.println(DatFile);

		if (Native.exists()) {
			base.passedStep("Native placeholder generated for the selected file type ");
		} else {
			base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		if (pdfFile.exists()) {
			base.passedStep("Pdf is generated successfully");
		} else {
			base.failedStep("Pdf is not generated successfully");
		}
		base.passedStep(
				"Verified - production regeneration with change(additional, change existing file type to spreadsheet only) "
						+ "in default enabled native placeholder under PDF section");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47936
	 * @Description:To Verify Slip Sheet->Work Product Should get saved in
	 *                 Production Component Section
	 **/
	@Test(description = "RPMXCON-47936", enabled = true, groups = { "regression" })
	public void verifyDefaultSelection() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test Cases Id : RPMXCON-47936");
		base.stepInfo("To Verify Slip Sheet->Work Product Should get saved in Production Component Section");

		productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.slipSheetToggleEnable();
		page.fillingSlipSheetInTiffSection("MasterDateDateOnly");
		page.fillingSlipSheetInTiffSection("DocID");
		page.clickComponentMarkCompleteAndIncomplete();
		page.getTIFFTab().waitAndClick(10);
		page.getAdvancedTabInTIFF().waitAndClick(10);
		page.toVerifyDefaultSelection("MasterDateDateOnly");
		page.toVerifyDefaultSelection("DocID");
		base.passedStep("Verified Slip Sheet->Work Product Should get saved in Production Component Section");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48001
	 * @Description:To Verify DAT Bates number generated should be in sync with
	 *                 actual bates number generated for the Documents.
	 **/
	@Test(description = "RPMXCON-48001", enabled = true, groups = { "regression" })
	public void verifySubBates() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-48001 Production Component Sprint 19");
		base.stepInfo(
				"To Verify DAT Bates number generated should be in sync with actual bates number generated for the Documents.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String datField = "B" + page.getRandomNumber(2);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, datField);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentLevelAndSubBates(beginningBates, prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String PDocCount = page.getProductionDocCount().getText();
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.extractFile();
		page.verifyDATFileForSubBatesNumber(firstFile, lastfile, prefixID, suffixID);
		base.passedStep("verified that Bates Number in DAT file should be AK01000.00001");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49110
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected and
	 *                 Tags is also selected.
	 **/
	@Test(description = "RPMXCON-49110", enabled = true, groups = { "regression" })
	public void verifyNativeCountInSummaryWithSelectedTags() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-49110");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected and Tags is also selected.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		int doCount = sessionSearch.basicContentSearch(Input.testData1);
		System.out.println(doCount);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String count = page.countOfNumberOfNative().getText();
		System.out.println(count);
		int nativeCount = Integer.parseInt(count);
		System.out.println(nativeCount);
		softAssertion.assertEquals(nativeCount, doCount);
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49109
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is not selected.
	 **/
	@Test(description = "RPMXCON-49109", enabled = true, groups = { "regression" })
	public void verifyNativeCountInSummary() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-49109");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is not selected.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "mp3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";
		softAssertion.assertEquals(nativeCount, count);
		softAssertion.assertAll();
		base.passedStep(
				"the value of Number of Natives on  Production-Summary tab if Native component is not selected.");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49120
	 * @Description:To verify that in Production-Branding, Metadata Field drop down should be sorted by alpha ascending
	 **/
	@Test(description = "RPMXCON-49120", enabled = true, groups = { "regression" })
	public void verifyBrandingSorting() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-49120");
		base.stepInfo("To verify that in Production-Branding, Metadata Field drop down should be sorted by alpha ascending");

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.stepInfo("Verify meta data list in drop down native will be in ascending order on pdf section.");
		page.verifyMetaDataDropdownNativeAscendingOrderPdfSec();
		base.stepInfo("Verify meta data list in drop down native will be in ascending order on tiff section.");
		page.verifyMetaDataDropdownNativeAscendingOrderTiffSec();
		loginPage.logout();
		
	}
	
	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47858
	 * @Description:Verify QC & Result page displays Review Production and Production location details.
	 **/
	@Test(description = "RPMXCON-47858", enabled = true, groups = { "regression" })
	public void verifyQCPage() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47858");
		base.stepInfo("Verify QC & Result page displays Review Production and Production location details.");
		
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String productionNameInPA = page.addANewProduction(productionname);
		System.out.println(productionNameInPA);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		
		base.ValidateElement_PresenceReturn(page.getConfirmProductionCommit());
		base.printResutInReport(base.ValidateElement_PresenceReturn(page.getConfirmProductionCommit()),
				"commit button is visible", "commit button is not  visible", "Pass");
		
		base.ValidateElement_PresenceReturn(page.getCopyPath());
		base.printResutInReport(base.ValidateElement_PresenceReturn(page.getCopyPath()),
				"copy path button is visible", "copy path button is not  visible", "Pass");
		
		base.ValidateElement_PresenceReturn(page.getQC_Download());
		base.printResutInReport(base.ValidateElement_PresenceReturn(page.getQC_Download()),
				"Download button is visible", "Download button is not  visible", "Pass");
		
		loginPage.logout();
		
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47805
	 * @Description:To Verify that Load file should be created successfully as part of the production generation
	 **/
	@Test(description = "RPMXCON-47805", enabled = true, groups = { "regression" })
	public void verifyLoadFileGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test case Id:RPMXCON-47805 Production Component Sprint 19");
		base.stepInfo("To Verify that Load file should be created successfully as part of the production generation");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String PDocCount = page.getProductionDocCount().getText();
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.verifyLoadFileDocumentsAfterDownload(firstFile,lastfile);
		
		base.passedStep("Verified that Load file should be created successfully as part of the production generation");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47982
	 * @Description:To Verify Branding provided for a document should not overlapping/written over the actual content, on Preview
	 **/
	@Test(description="RPMXCON-47982",enabled = true, groups = { "regression" } )
	public void verifyBrndOverActualText() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47982");
		base.stepInfo("To Verify Branding provided for a document should not overlapping/written over the actual content, on Preview");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String brandingString = "Testing";
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	    base = new BaseClass(driver);
	    tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
	    
	    SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);		
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);	
	    page.fillingDATSection();
	    page.verifyTheTagOnRightBranding(tagname, brandingString);
	    page.navigateToNextSection();
	    page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewButtonInSummaryTab();
        base.waitUntilFileDownload();
	
		String name =prefixID + beginningBates +suffixID;
		String home = System.getProperty("user.home");	
		File file = new File(home + "/Downloads/" + name + ".pdf");
		File file1 = new File(Input.fileDownloadLocation + name + ".pdf");
		if (file.exists()) {
		try {
			String url = home + "/Downloads/";
			String content = page.verifyBrandingOverlapping(url, name+".pdf", brandingString ,0);
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		else if (file1.exists()) {
		try {
			String url1 = Input.fileDownloadLocation;
			String content1 = page.verifyBrandingOverlapping(url1, name+".pdf", brandingString,0);
			System.out.println(content1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		base.passedStep("Verified-Branding provided for a document should not overlapping/written "
				+ "over the actual content, on Preview");
		}
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
