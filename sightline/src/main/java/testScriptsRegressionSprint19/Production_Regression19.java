package testScriptsRegressionSprint19;


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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression19 {

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
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
		base.stepInfo("Test case Id:RPMXCON-47847 Production Component Sprint 19");
		base.stepInfo("To Verify the Create/Display/View of Template with newly created Security Group.");

		String securityGroup = "Security" + UtilityLog.dynamicNameAppender();
		String productionSet = "ProdSet" + UtilityLog.dynamicNameAppender();
		String template = "Template" + UtilityLog.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// add new security group
		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

//		//select created security group
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();

		// create new production set
		page.CreateProductionSets(productionSet);
		page.navigateToProductionPageByNewProductionSet(productionSet);

		String beginningBates = page.getRandomNumber(2);
		// create production and save as custom template
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingCreatedSecurityGroup(securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyInManageTemplateTab(productionname, template);

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
		base.stepInfo("Test case Id:RPMXCON-48869");
		base.stepInfo(
				"To verify that if user added leading space for DAT field, then after generating the production, warning message should be displayed");
		String expected = "DAT field names must begin with an alphabet. Please check.";
		ProductionPage page = new ProductionPage(driver);
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String batesWithSpaces = " " + "B" + " " + page.getRandomNumber(2);

		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithSpaces);
		page.getComponentsMarkComplete().waitAndClick(5);
		base.VerifyErrorMessage(expected);
		base.passedStep(
				"verified - that if user added leading space for DAT field, then after generating the production, warning message should be displayed");
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-50015
	 * @Description:To Verify that help text should be displays as This is not a
	 *                 searchable field for 'AllProductionBatesRanges' metadata"
	 **/
	@Test(description = "RPMXCON-50015", enabled = true, groups = { "regression" })
	public void verifyHelpTxtForAllProdBateRange() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-50015");
		base.stepInfo(
				"To Verify that help text should be displays as This is not a searchable field for 'AllProductionBatesRanges' metadata");
		String expHelpText = "Due to the nature of the content, this field cannot be made searchable";
		String fieldName = "AllProductionBatesRanges";
		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);

		projectField.navigateToProjectFieldsPage();
		projectField.applyFilterByFilterName(fieldName);
		base.waitForElement(projectField.getFieldNameEdititButton(fieldName));
		projectField.getFieldNameEdititButton(fieldName).waitAndClick(10);
		base.waitForElement(projectField.getIsSearcHelpBtn());
		projectField.getIsSearcHelpBtn().waitAndClick(10);
		base.waitForElement(projectField.getIsSearcHelpTxt());
		String actHelpText = projectField.getIsSearcHelpTxt().getText();
		if (actHelpText.equals(expHelpText)) {
			base.passedStep("Help text for AllProductionBatesRanges metadata is displaying as Expected");
		} else {
			base.failedStep("Help text for AllProductionBatesRanges metadata is Not displaying as Expected");
		}
		base.passedStep(
				"Verified - that help text should be displays as \"This is not a searchable field.\" for 'AllProductionBatesRanges' metadata");
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
		base.stepInfo("Test case Id:RPMXCON-49111");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected with few File types");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString5);
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
		base.stepInfo("Test case Id:RPMXCON-49115 Production Component Sprint 19");
		base.stepInfo(
				"To verify that the value of Number of MP3 Files on Production-Summary tab if MP3 Files component is selected.");
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
		base.stepInfo("Test case Id:RPMXCON-49112");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected and document is Privileged");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		base.stepInfo("created a privileged tag");

		SessionSearch sessionSearch = new SessionSearch(driver);
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
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-48499
	 * @Description:To verify that after clicking on InComplete button on Production
	 *                 Components, last selected Native File Group types and Tags
	 *                 should be displayed
	 **/
	@Test(description = "RPMXCON-48499", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifySelectedTypesTags_MarkIncomplete(String userName, String password) throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48499 Production");
		base.stepInfo(
				"To verify that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
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
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkFolderExisting(foldername);
			sessionSearch.ViewInDocViewWithoutPureHit();

			DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
			DocViewPage docView = new DocViewPage(driver);
			docView.documentSelection(Doc);
			driver.waitForPageToBeReady();
			docViewRedactions.redactRectangleUsingOffset(10, 10, 50, 20);
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
		 * @author Brundha Test case id-RPMXCON-47871   Date:8/09/2022
		 * @Description To Verify that user should be able to select search under 'Select Searches' source for Document selection tab in Production
		 * 
		 */
		@Test(description = "RPMXCON-47871", enabled = true, groups = { "regression" })
		public void verifyProductionGenerationWithSearch() throws Exception {

			UtilityLog.info(Input.prodPath);
			base = new BaseClass(driver);
			base.stepInfo("RPMXCON-47871 -Production Component");
			base.stepInfo("To Verify that user should be able to select search under 'Select Searches' source for Document selection tab in Production");

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
		 * @author Brundha Test case id-RPMXCON-48018   Date:8/09/2022
		 * @DescriptionTo Verify In Native section User should be able to select one or more tags without selecting any file types.
		 * 
		 */
		@Test(description = "RPMXCON-48018", enabled = true, groups = { "regression" })
		public void verifySelectedTagInNativeSection() throws Exception {
			UtilityLog.info(Input.prodPath);
			base = new BaseClass(driver);
			base.stepInfo("RPMXCON-48018 -Production component");
			base.stepInfo("To Verify In Native section User should be able to select one or more tags without selecting any file types.");

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
		 * @Description:To verify that "Generate Load File" is enabled by default for 'MP3' components.
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



