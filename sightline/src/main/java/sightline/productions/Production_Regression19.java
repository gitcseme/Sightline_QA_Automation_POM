package sightline.productions;


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
import org.testng.annotations.Listeners;
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			docViewRedactions.redactRectangleUsingOffset(10, 10,40, 20);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			BaseClass base = new BaseClass(driver);
			base.stepInfo("Test case Id:RPMXCON-48319- Production Component");
			base.stepInfo(
					"To verify that When the \"Natives\" component is selected and if no file group type OR no tag is selected, then system should provide Error message \"You must select at least a file group type or a tag in Native component.\" during Mark Complete action.");

			String ExpectedMsg="To export natives, you must select at least a file group type or a tag in the Native section.";
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
		 * @Description:To Verify ProjectAdmin will be able to enter document selection and output information on the self production wizard
		 */
		@Test(description = "RPMXCON-47615", enabled = true, groups = { "regression" })

		public void verifyDocCountInDocumentSelectionTab() throws Exception {
			UtilityLog.info(Input.prodPath);
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			base.stepInfo("RPMXCON-47615-from Production Component");
			base.stepInfo("To Verify ProjectAdmin will be able to enter document selection and output information on the self production wizard");
			
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
			String DocCount=page.getDocumentSelectionLink().getText();
			System.out.println("Document Count is:" + DocCount);
			base.digitCompareEquals(PureHit, Integer.valueOf(DocCount), "Total Document count is displayed ","Document is not dispalyed");
			loginPage.logout();
		}
		
		
		/**
		 * @author Brundha.T No:RPMXCON-47972 Date:8/12/2022
		 * @Description:To Verify Production should not error out for (Bates Number) in SlipSheet.
		 **/

		@Test(description = "RPMXCON-47972", enabled = true, groups = { "regression" })
		public void verifyingSlipSheetBatesNumberWithoutError() throws Exception {
			
			UtilityLog.info(Input.prodPath);
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
		 * @Description:To verify that in Production-Privileged Placeholder section, Metadata Field drop down should be sorted by alpha ascending
		 **/

		@Test(description = "RPMXCON-49122", enabled = true, groups = { "regression" })
		public void verifyingAscendingOrerInPrivDocMetaDataField() throws Exception {
			UtilityLog.info(Input.prodPath);
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			BaseClass base = new BaseClass(driver);
			base.stepInfo("Test case Id:RPMXCON-49122- Production Component");
			base.stepInfo("To verify that in Production-Privileged Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

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



