package testScriptsRegression;

import java.awt.AWTException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.openqa.selenium.support.Color;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	SessionSearch search;
	SavedSearch savedSearch;
	DocListPage docList;
	TagsAndFoldersPage tagsAndFolderPage;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AssignmentsPage assignmentsPage;
	String foldername;
	String tagname;
	String productionname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();
		base = new BaseClass(driver);
		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * @author : Gopinath created on:NA modified by:NA @Testcase_ID :
	 *         RPMXCON_56134 - Verify that for the saved template under TIFF/PDF
	 *         component.
	 * @Description : Verify that for the saved template under TIFF/PDF component
	 *              and Available Fields in Slip Sheets should be disabled.
	 */
	////////@Test(groups = { "regression" }, priority = 1)
	public void verifyingTiffComponentsInManageTemplate() throws InterruptedException, AWTException {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case Id: RPMXCON-56134 -Production Sprint 04");
		base.stepInfo(
				"#### Verify that for the saved template under TIFF and PDF component and Available Fields in Slip Sheets should be disabled. ####");

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Verify manage template filling tiff section");
		page.verifyingManageTemplateFillingTiffSection();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_ID :
	 *         RPMXCON_56102 - Verify the error message for TIFF/PDF component when
	 *         'Enable Tech issue doc without tag or text'
	 * @Description: Verify the error message for TIFF/PDF component when 'Enable
	 *               Tech issue doc without tag or text'.
	 */
	////////@Test(groups = { "regression" }, priority = 2)
	public void verifyingTiffComponentsWithoutTagOrText() throws InterruptedException, AWTException {

		ProductionPage page = new ProductionPage(driver);
		base.stepInfo("Test case Id: No:RPMXCON_56102 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for TIFF and PDF component when Enable Tech issue doc without tag or text. ####");

		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Select defalut security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling native section");
		page.fillingNativeSection();

		base.stepInfo("Filling tiff section tech issue without entering tect");
		page.fillingTiffSectionTechIssueWithoutEnteringText(Input.randomText);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_ID :
	 *         RPMXCON_56107 - Verify the error message for TIFF/PDF component when
	 *         'don't add any text in specify redaction text section'.
	 * @Description : Verify the error message for TIFF/PDF component when 'don't
	 *              add any text in specify redaction text section'.
	 */
	////////@Test(groups = { "regression" }, priority = 3)
	public void verifyingTiffComponentsWithoutTextInSpecifyRedactionSection()
			throws InterruptedException, AWTException {
		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Test case Id: No:RPMXCON_56107 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for TIFF and PDF component when dont add any text in specify redaction text section. ####");

		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Select defalut security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling native section");
		page.fillingNativeSection();

		base.stepInfo("Filling tiff section without entering text in specific redaction");
		page.fillingTiffSectionWithoutEnteringTextInSpecifyRedaction();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_ID :
	 *         RPMXCON_56106 - Verify the error message for TIFF/PDF component when
	 *         'Enable redaction without selecting redaction tag'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Enable
	 *              redaction without selecting redaction tag'.
	 */
	////////@Test(groups = { "regression" }, priority = 4)
	public void verifyingTiffComponentsWithoutTagInSpecifyRedactionSection() throws InterruptedException, AWTException {
		base.stepInfo("Test case Id: No:RPMXCON_56106 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for TIFF and PDF component when Enable redaction without selecting redaction tag ####");

		ProductionPage page = new ProductionPage(driver);
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Select defalut security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling native section");
		page.fillingNativeSection();

		base.stepInfo("Filling tiff section without entering tag in specific redaction.");
		page.fillingTiffSectionWithoutEnteringTagInSpecifyRedaction(Input.randomText);
		loginPage.logout();

	}

	/**
	 * @author : Gopinath created on:NA modified by:NA @TestCase ID :
	 *         RPMXCON_56105 - Verify the error message for TIFF/PDF component when
	 *         'Specify branding without tag and with text'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Specify
	 *              branding without tag and with text' .
	 */
	////////@Test(groups = { "regression" }, priority =5 )
	public void verifyTiffSectionSpecifyBrandingWithoutTag() throws Exception {

		base.stepInfo("Test case Id: No:RPMXCON_56105 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for TIFF and PDF component when Specify branding without tag and with text ####");

		ProductionPage page = new ProductionPage(driver);
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Verify the message on tiff section specify branding without tag");
		page.TiffSectionSpecifyBrandingWithoutTag(Input.randomText);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID :
	 *         RPMXCON_56104 - Verify the error message for TIFF/PDF component when
	 *         'Specify branding with tag and without text'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Specify
	 *              branding with tag and without text'.
	 */
	////////@Test(groups = { "regression" }, priority =6 )
	public void verifyTiffSectionSpecifyBrandingWithoutText() throws Exception {

		base.stepInfo("Test case Id: No:RPMXCON_56104 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for TIFF and PDF component when Specify branding with tag and without text ####");
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String tagNam = Input.randomText + Utility.dynamicNameAppender();
		final String tagName = tagNam;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Verify  the message on tiff section specify branding without text");
		page.TiffSectionSpecifyBrandingWithTagandWithoutText(tagName);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @TestCase Id :
	 *         RPMXCON_56103 - Verify the error message for TIFF/PDF component when
	 *         'Specify branding without tag and text'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Specify
	 *              branding without tag and text'.
	 */
	////////@Test(groups = { "regression" }, priority =7 )
	public void TiffSectionSpecifyBrandingWithoutTagAndText() throws Exception {

		base.stepInfo("Test case Id: No:RPMXCON_56103 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for TIFF/PDF component when 'Specify branding without tag and text' ####");
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Verify the message on tiff section specify branding without tag and text");
		page.TiffSectionSpecifyBrandingWithoutTagandText();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID :
	 *         RPMXCON_56131 - Verify that if Tag is already specified with
	 *         'Natively Produced Documents' placeholder then that Tag will
	 *         available again to select for branding.
	 * @Description : Verify that if Tag is already specified with 'Natively
	 *              Produced Documents' placeholder then that Tag will available
	 *              again to select for branding.
	 */
	////////@Test(groups = { "regression" }, priority =8 )
	public void verifyTiffSectionSelectingNativeProducedDocsAndBrandingTag() throws Exception {

		base.stepInfo("Test case Id: No:RPMXCON_56131 -Production Sprint 04");
		base.stepInfo(
				"#### Verify that if Tag is already specified with Natively Produced Documents placeholder then that Tag will available again to select for branding ####");

		String tagNam = Input.randomText + Utility.dynamicNameAppender();
		final String tagName = tagNam;
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);
		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Verify the tag selected in the natively produced doc is enabled in specify branding ");
		page.selectingNativelyProducedDocsAndBrandingTag(tagName, Input.randomText);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID :
	 *         RPMXCON_56127 - Verify that if Tag is already specified with
	 *         Placeholder then that Tag will available again to select for
	 *         branding.
	 * @Description : Verify that if Tag is already specified with Placeholder then
	 *              that Tag will available again to select for branding
	 */
	////////@Test(groups = { "regression" }, priority =9 )
	public void verifyTagSelectedPrivilegedTagEnableBandingTag() throws Exception {

		base.stepInfo("Test case Id: RPMXCON_56127 -Production Sprint 04");
		base.stepInfo(
				"#### Verify that if Tag is already specified with Placeholder then that Tag will available again to select for branding ####");

		String tagNam = Input.randomText + Utility.dynamicNameAppender();
		final String tagName = tagNam;
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);
		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Select privileged tag and enter place holder value");
		page.selectPrivilegedTagAndEnterPlaceHolderValue(tagName, Input.randomText);

		base.stepInfo("Verify tag enabled after seelcting tag in previliged");
		page.verifyTagEnabledAfterSelectingTagInPreviliged(tagName);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID :
	 *         RPMXCON_56108 - Verify the error message for MP3 component when
	 *         'Enable burn redaction without selecting redaction tag'.
	 * @Description : Verify the error message for MP3 component when 'Enable burn
	 *              redaction without selecting redaction tag'.
	 */
	////////@Test(groups = { "regression" }, priority =10 )
	public void VerifyMsgByEnableBurnRedactionWithoutSelectRedactionTag() throws Exception {

		base.stepInfo("Test case Id: No:RPMXCON_56108 -Production Sprint 04");
		base.stepInfo(
				"#### Verify the error message for MP3 component when Enable burn redaction without selecting redaction tag ####");
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Verify the tag selected in the natively produced doc is enabled in specify branding ");
		page.verifyWarningMessageDisplayedByWithoutSelectingMp3RedationTag();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON_48500 : To verify that if annotation layer option is selected
	 *         in Tiff section and document is redacted then selected Metadata
	 *         should not be displayed on DAT.
	 * @Description : Verify that if annotation layer option is selected in Tiff
	 *              section and document is redacted then selected Metadata should
	 *              not be displayed on DAT.
	 */
	////////@Test(groups = { "regression" }, priority = 11)
	public void generateProductionWithoutSelectedMetadata() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48500 Production - Sprint 04");
		base.stepInfo(
				"#### Verify that if annotation layer option is selected in Tiff section and document is redacted then selected Metadata should not be displayed on DAT. ####");
		String testData1 = Input.testData1;

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(testData1);
		search.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Add New Field On DAT");
		page.addNewFieldOnDAT();

		base.stepInfo("Filling Meta Data To DAT Fields");
		page.fillingMetaDataToDATFields();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.passedStep("Generated production successfully");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON_48491 : To verify that if annotation layer option is selected
	 *         and non audio document is redacted then native should not copied.
	 * @Description : Verify that if annotation layer option is selected and non
	 *              audio document is redacted then native should not copied.
	 */
	////////@Test(groups = { "regression" }, priority = 12)
	public void generateProductionWithNonAudioDocument() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48491 Production - Sprint 04");
		base.stepInfo(
				"#### Verify that if annotation layer option is selected and non audio document is redacted then native should not copied. ####");

		String testData1 = Input.testData1;

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(testData1);
		search.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.passedStep("Generated production successfully");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-48312 : To verify that redaction text should be printed on
	 *         burned redaction if user selects Tiff.
	 * @Description : Verify that redaction text should be printed on burned
	 *              redaction if user selects Tiff.
	 */
	////////@Test(groups = { "regression" }, priority = 13)
	public void verifyingRedactedTextPrintedOnBurnRedactionSelectingTIFF() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48312 Production-sprint:04");
		base.stepInfo(
				"#### Verify that redaction text should be printed on burned redaction if user selects Tiff. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		base.stepInfo("Filling text section");
		page.fillingTextSection();

		base.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.stepInfo("verified  the reduction text printed on selecting TIFF ");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-48310 : To verify that redaction text should be printed on
	 *         burned redaction if user selects only PDF.
	 * @Description : Verify that redaction text should be printed on burned
	 *              redaction if user selects only PDF.
	 */
	////////@Test(groups = { "regression" }, priority = 14)
	public void verifyingRedactedTextPrintedOnSelectingPDF() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48310 Production-sprint:04");
		base.stepInfo(
				"#### Verify that redaction text should be printed on burned redaction if user selects only PDF. ####");
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling PDF Section with Burn Redaction");
		page.fillingPDFSectionwithBurnRedaction(tagname);

		base.stepInfo("Filling text section");
		page.fillingTextSection();

		base.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.stepInfo("verified  the reduction text printed on selecting PDF");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-48600 : To verify that user can export only after pre-gen
	 *         check is completed.
	 * @Description :Verify that user can export only after pre-gen check is
	 *              completed.
	 */
	////////@Test(groups = { "regression" }, priority = 15)
	public void verifyExportButtonEnabledAfterPreGenCompletion() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48600 Production-sprint:04");
		base.stepInfo("#### Verify that user can export only after pre-gen check is completed. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag

		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Logged in as User: " + Input.pa2FullName);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling text section");
		page.fillingTextSection();

		base.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("Click on navigate back");
		page.navigateBack();

		base.stepInfo("Verify export bates button is enabled");
		page.verifyExportBatesButtonIsEnabled();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-49218 : To verify that in Advanced Search, search against
	 *         uncommit Production should not display any results.
	 * @Description : Verify that in Advanced Search, search against uncommit
	 *              Production should not display any results.
	 */
	////////@Test(groups = { "regression" }, priority = 16)
	public void verifyCommitAndUnCommitProductionResults() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49218 Production-sprint:04");
		base.stepInfo(
				"#### Verify that in Advanced Search, search against uncommit Production should not display any results. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag

		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Logged in as User: " + Input.pa2FullName);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling text section");
		page.fillingTextSection();

		base.stepInfo("Navigate to next section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("Get document select count");
		int docCount = page.getDocumentSelectedCount();

		base.stepInfo("Get production name");
		String productionName = page.getProductionNam();

		base.stepInfo("Switch to work product");
		search.switchToWorkproduct();

		base.stepInfo("Select production");
		search.selectProduction(productionName);

		base.stepInfo("Verify pure hit count with production count");
		search.verifyPureHitsCountWithProductionCount(docCount);

		page = new ProductionPage(driver);

		base.stepInfo("Perform operation on production filter");
		page.ProductionFilter();

		base.stepInfo("Select row by production name and open in wizard.");
		page.selectRowByProductionNameAndOpenWizard(productionName);

		base.stepInfo("Click on uncommit bates");
		page.uncommitBates();

		page = new ProductionPage(driver);

		base.stepInfo("Perform new search by new work product");
		search.performNewSearchByNewWorkProduct(productionName);

		base.stepInfo("Verify pure hit count with production count after uncommit");
		search.verifyPureHitsCountWithManyProductionCount(0);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-49998 : Verify if user included only default branding text
	 *         then branding text should display on 'Preview' document and on
	 *         produced documents also.
	 * @Description : Verify if user included only default branding text then
	 *              branding text should display on 'Preview' document and on
	 *              produced documents also.
	 */
	////////@Test(groups = { "regression" }, priority = 17)
	public void defaultBrandingTextDisplayedInPrieviewDocs() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49998 Production-sprint:04");
		base.stepInfo(
				"#### Verify if user included only default branding text then branding text should display on Preview document and on produced documents also. ####");
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section With Default Branding Text");
		page.fillingTIFFSectionWithDefaultBrandingText(Input.randomText);

		base.stepInfo("Filling Native Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-50016 : Verify that if 'AllProductionBatesRanges' is
	 *         non-searchable for existing project, user cannot make it as
	 *         Searchable field.
	 * @Description :Verify that if 'AllProductionBatesRanges' is non-searchable for
	 *              existing project, user cannot make it as Searchable field.
	 */
	////////@Test(groups = { "regression" }, priority = 18)
	public void verifyingTheSearchableFieldInAllProductionBatesRanges() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50016 Production-sprint:04");
		base.stepInfo(
				"#### Verify that if 'AllProductionBatesRanges' is non-searchable for existing project, user cannot make it as Searchable field. ####");

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		ProjectFieldsPage projectFieldsPage = new ProjectFieldsPage(driver);

		base.stepInfo("Verifying Is Searchable Bates Range Is Selected");
		projectFieldsPage.verifyingIsSearchableBatesRangeIsSelected();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON-49242 : To verify that Project Admin can modify the populated
	 *         the bates number fields and production should generated with modified
	 *         bates number.
	 * @Description : To verify that Project Admin can modify the populated the
	 *              bates number fields and production should generated with
	 *              modified bates number.
	 */
	////////@Test(groups = { "regression" }, priority = 19)
	public void SelectBateNumberInNumberingAndShorting() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49242 Production-sprint:04");
		base.stepInfo(
				"#### To verify that Project Admin can modify the populated the bates number fields and production should generated with modified bates number. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section With Default Branding Text");
		page.fillingTIFFSectionWithDefaultBrandingText(Input.randomText);

		base.stepInfo("Filling Native Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("ReFilling The Numbering And Sorting Page");
		page.reFillingTheNumberingAndSortingPage(prefixID, suffixID);

		base.stepInfo("ReFilling The Numbering And Sorting Page");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_48021-generateTheProductionAfterNativeSectionIsSelectedWithTags
	 *         : Verify Natives of the documents having the selected tags should be
	 *         excluded from production.
	 * @Description : Verify Natives of the documents having the selected tags
	 *              should be excluded from production, in case the documents have
	 *              selected redactions or selected PRIV tags.
	 */

	////////@Test(groups = { "regression" }, priority = 20)
	public void generateTheProductionAfterNativeSectionIsSelectedWithTags() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48021 -Production Sprint 05");
		base.stepInfo(
				"#### Verify Natives of the documents having the selected tags should be excluded from production, in case the documents have selected redactions or selected PRIV tags. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section With Tags");
		page.fillingNativeSectionWithTags(tagname);

		base.stepInfo("Verifying Native Tag In TIFF Section Burn Redaction");
		page.verifyingNativeTagInTIFFSectionBurnRedaction(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling generate page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-48022 : To Verify In Productions, TIFF/ PDF generation is
	 *         failing for the SAME document while it is working fine sometimes.
	 * @Description : Verify In Productions, TIFF/ PDF generation is failing for the
	 *              SAME document while it is working fine sometimes
	 */

	////////@Test(groups = { "regression" }, priority = 21)
	public void verifyingProductionWithExcelFileDocuments() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48022 -Production Sprint 05");
		base.stepInfo(
				"#### Verify In Productions, TIFF and PDF generation is failing for the SAME document while it is working fine sometimes ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.excelFileDocument);

		for (int i = 0; i < 2; i++) {

			base.stepInfo("Bulk Folder Existing");
			sessionSearch.bulkFolderExisting(foldername);

			ProductionPage page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			base.stepInfo("Selecting Default SecurityGroup");
			page.selectingDefaultSecurityGroup();

			String productionname = Input.randomText + Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();

			base.stepInfo("Add New Production");
			page.addANewProduction(productionname);

			base.stepInfo("Filling DAT Section");
			page.fillingDATSection();

			base.stepInfo("Filling Native Section With Tags");
			page.fillingNativeSectionWithTags(tagname);

			base.stepInfo("Verifying Native Tag In TIFF Section Burn Redaction");
			page.verifyingNativeTagInTIFFSectionBurnRedaction(tagname);

			base.stepInfo("Filling Text Section");
			page.fillingTextSection();

			base.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();

			base.stepInfo("Filling Numbering And Sorting Page");
			page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

			base.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();

			base.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionPage(foldername);

			base.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();

			base.stepInfo("Filling Priv Guard Page");
			page.fillingPrivGuardPage();

			base.stepInfo("filling Production Location Page And Passing Text");
			page.fillingProductionLocationPageAndPassingText(productionname);

			base.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();

			base.stepInfo("Filling Summary And Preview");
			page.fillingSummaryAndPreview();

			base.stepInfo("Filling generate page");
			page.fillingGeneratePage();
			loginPage.logout();

		}

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID :
	 *         RPMXCON-49114 - To verify that the value of 'Number of MP3 Files' on
	 *         Production-Summary tab if MP3 Files component is selected.
	 * @Description : verify that the value of 'Number of MP3 Files' on
	 *              Production-Summary tab if MP3 Files component is selected.
	 */

	////////@Test(groups = { "regression" }, priority = 22)
	public void assertionOnGenerateProdctionPageByErrorDocuments() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49114 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that the value of Number of MP3 Files on Production-Summary tab if MP3 Files component is selected. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Audio Search");
		// sessionSearch.audioSearch(Input.audioSearch,Input.audioLanguage);
		sessionSearch.basicContentSearch(Input.mp3FileDocument);

		base.stepInfo("Bulk Tag Existing");
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview And Verifying MP3 Files");
		page.fillingSummaryAndPreviewAndVerifyingMP3Files();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-49227 : Verify that in Production, if sorting option is Sort
	 *         by MetaData and 'Keep Families Together' check box is selected.
	 * @Description : verify that in Production, if sorting option is Sort by
	 *              MetaData and 'Keep Families Together' check box is selected.
	 */
	////////@Test(groups = { "regression" }, priority = 23)
	public void verifyingTheSortingOfProducedDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49227 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that in Production, if sorting option is Sort by MetaData and Keep Families Together check box is selected. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Filling Sorting Secion");
		page.fillingSortingSecion();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling generate page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-55700 : Verify file group type (.mdb/.mdf) option on
	 *         selection in Translations section
	 * @Description : Verify file group type (.mdb/.mdf) option on selection in
	 *              Translations section.
	 */

	////////@Test(groups = { "regression" }, priority = 24)
	public void verifyingTheTranslationnInProductionComponent() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55700 -Production Sprint 05");
		base.stepInfo("#### Verify file group type .mdb or .mdf option on selection in Translations section. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.testData1);

		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Advanced Production Component");
		page.fillingAdvancedProductionComponent();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-55679 : To Verify On the Summary step of a production, the
	 *         counts under the OCR/TIFF should Display Count.
	 * @Description : Verify On the Summary step of a production, the counts under
	 *              the OCR/TIFF should Display Count.
	 */
	////////@Test(groups = { "regression" }, priority = 25)
	public void verifyingTheCountOfOCRAndTiffInSummarySection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55679 -Production Sprint 05");
		base.stepInfo(
				"#### Verify On the Summary step of a production, the counts under the OCR or TIFF should Display Count. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section With Tags");
		page.fillingNativeSectionWithTags(tagname);

		base.stepInfo("Verifying Native Tag In TIFF Section Burn Redaction");
		page.verifyingNativeTagInTIFFSectionBurnRedaction(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		for (int i = 0; i < 2; i++) {
			base.stepInfo("Click on navigate back");
			page.navigateBack();
		}

		base.stepInfo("OCR And TIFF Count In Summary And Preview");
		page.OCRAndTIFFCountInSummaryAndPreview();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-49237 : To verify that link should be available on Numbering
	 *         and Sorting tab
	 * @Description : Verify that link should be available on Numbering and Sorting
	 *              tab
	 */
	////////@Test(groups = { "regression" }, priority = 26)
	public void verifyingLinkInNumberingAndSorting() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49237 -Production Sprint 05");
		base.stepInfo("#### Verify that link should be available on Numbering and Sorting tab ####");
		ProductionPage page = new ProductionPage(driver);

		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Selecting default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Verify availble Link At Numbering And Sorting");
		page.verifyAvailbleLinkAtNumberingAndSorting();
		loginPage.logout();

	}

	/**
	 * @author : Gopinath created on:NA modified by:NA @Testcase id :
	 *         RPMXCON_49221 : To verify that after uncommit if user regenerate the
	 *         production , it should generate successfully
	 * @Description : Verify that after uncommit if user regenerate the production ,
	 *              it should generate successfully.
	 */
	////////@Test(groups = { "regression" }, priority =27 )
	public void ProductionGenerateSuccessfullyAfterUnCommit() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49221 Production 05");
		base.stepInfo(
				"#### Verify that after uncommit if user regenerate the production , it should generate successfully. ####");
		String tagNamePrev = Input.tagNamePrev;
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling PDF Section");
		page.fillingPDFSection(tagname, tagNamePrev);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page AFter Uncommit");
		page.fillingGeneratePageAFterUncommit();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON_50033 : To verify that PDF should burn multiple redactions
	 *         and display the correct text for each redaction.
	 * @Description : To verify that PDF should burn multiple redactions and display
	 *              the correct text for each redaction.
	 */
	////////@Test(groups = { "regression" }, priority =28 )
	public void SelectingRedactionTagsInPDFSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50033 Production 05");
		base.stepInfo(
				"#### Verify that PDF should burn multiple redactions and display the correct text for each redaction. ####");
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag2 = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		UtilityLog.info(Input.prodPath);

		RedactionPage redactionpage = new RedactionPage(driver);

		base.stepInfo("Select Default Security Group");
		redactionpage.selectDefaultSecurityGroup();

		base.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		base.stepInfo("Select Default Security Group");
		redactionpage.selectDefaultSecurityGroup();

		base.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling PDF Section With Redaction Tags");
		page.fillingPDFSectionWithRedactionTags(Redactiontag1, Redactiontag2);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID :
	 *         RPMXCON_56007 : Verify that user can download the production by using
	 *         the Shareable link for 'All Files'.
	 * @Description :Verify that user can download the production by using the
	 *              Shareable link for 'All Files'.
	 */
@Test(groups = { "regression" }, priority = 29)
	public void verifyDownloadProductionUsingSharableLink() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56007 Production - Sprint 05");
		base.stepInfo(
				"#### Verify that user can download the production by using the Shareable link for All Files.. ####");
		String testData1 = Input.testData1;

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		search = new SessionSearch(driver);
		search.basicContentSearch(testData1);
		search.bulkFolderExisting(foldername);

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add new production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.passedStep("Generated production successfully");

		base.stepInfo("Verify download production using sharable link.");
		page.verifyDownloadProductionUsingSharableLink();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-49224 : To verify that after uncommit, if user change the
	 *         source in Document Selection tab, it should regenerate and commit
	 *         Production successfully.
	 * @Description : Verify that after uncommit, if user change the source in
	 *              Document Selection tab, it should regenerate and commit
	 *              Production successfully
	 */
@Test(groups = { "regression" }, priority = 30)
	public void modifyingTheDocumentSelectionTabAndGenerateTheProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49224 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that after uncommit, if user change the source in Document Selection tab, it should regenerate and commit Production successfully ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		sessionSearch.basicContentSearch(testData1);

		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("Filling Generate Pageand Uncommit TheProduction ");
		page.fillingGeneratePageandUncommitTheProduction();

		base.stepInfo("Modifying Document Selection And Mark complete");
		page.modifyingDocumentSelectionAndMarkcomplete(tagname);

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56010 : Verify that even after Uncommit the producion user
	 *         can download the Production.
	 * @Description : Verify that even after Uncommit the producion user can
	 *              download the Production.
	 */
@Test(groups = { "regression" }, priority = 31)
	public void productionGeneratedSuccessfullyAfterUnCommit() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56010 Production Sprint-5");
		base.stepInfo("#### Verify that  even after Uncommit the producion user can download the Production. ####");

		String tagNamePrev = Input.tagNamePrev;
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling PDF Section");
		page.fillingPDFSection(tagname, tagNamePrev);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page AFter Uncommit");
		page.fillingGeneratePageAFterUncommit();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-56085 : Verify that Production should generate successfully
	 *         by selecting only DAT and 'Generate TIFF' option with Priv
	 *         Placholder.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate TIFF' option with Priv
	 *              Placholder.
	 */
@Test(groups = { "regression" }, priority = 32)
	public void TiffWithPrivPlaceholderAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56085 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that Production should generate successfully by selecting only DAT and Generate TIFF option with Priv Placholder. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Select Priv Docs In Tiff Section");
		page.selectPrivDocsInTiffSection(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.stepInfo("Production is generated successfully on Selecting Priv docs placeholder");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56084 : Verify that Production should generate successfully
	 *         by selecting only DAT and 'Generate PDF' option.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate PDF' option.
	 */
@Test(groups = { "regression" }, priority = 33)
	public void GenerateProductionByFillingDATAndPDFSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56084 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that Production should generate successfully by selecting only DAT and Generate PDF option. ####");
		String tagNamePrev = Input.tagNamePrev;
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT and PDF
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling PDF Section");
		page.fillingPDFSection(tagname, tagNamePrev);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling priv Guard page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.stepInfo("Production is generated successfully on filling DAT and PDF section");
		loginPage.logout();
	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA TESTCASE @Testcase No
	 *         : RPMXCON-56128 : Verify that if Redaction Tag/s is already specified
	 *         with Redaction Text then that Redaction Tag.
	 * @Description : Verify that if Redaction Tag/s is already specified with
	 *              Redaction Text then that Redaction Tag.
	 */

	////////@Test(groups = { "regression" }, priority = 34)
	public void verifyClickMarkIncompleteDisablesALreadyRedactedTags() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56128 Production");
		base.stepInfo(
				"####Verify that if Redaction Tags is already specified with Redaction Text then that Redaction Tag. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites

		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// create production and fill dat field and verify specify controls

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Click Component Mark Complete And Incomplete");
		page.clickComponentMarkCompleteAndIncomplete();

		base.stepInfo("Filling TIFF With Burning Redactions And Previously Selected Tag Disabled");
		page.fillingTIFFWithBurningRedactionsAndPreviouslySelectedTagDisabled(tagname);
		loginPage.logout();
	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56062 : Verify Production should generate successfully with
	 *         page counting if user selects PDF and TEXT.
	 * @Description : Verify Production should generate successfully with page
	 *              counting if user selects PDF and TEXT.
	 */

	////////@Test(groups = { "regression" }, priority = 35)
	public void generateProductionByFillingDATAndPDFAndTextSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56062 -Production");
		base.stepInfo(
				"#### Verify Production should generate successfully with page counting if user selects PDF and TEXT. ####");

		String tagNamePrev = Input.tagNamePrev;
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT and PDF

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling PDF Section");
		page.fillingPDFSection(tagname, tagNamePrev);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Filling Text Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Naviagte to next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Naviagte to next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Naviagte to next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("Production is generated successfully on filling DAT,PDF and TEXT section");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON-55717 : To Verify in Productions, for a document with
	 *         AudioPlayReady, only the MP3 file variant is produced as MP3 files.
	 * @Description : To Verify in Productions, for a document with AudioPlayReady,
	 *              only the MP3 file variant is produced as MP3 files.
	 */

	////////@Test(groups = { "regression" }, priority = 36)
	public void selectAudioSearchAndGenerateProdcution() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55717 -Production");
		base.stepInfo(
				"####To Verify in Productions, for a document with AudioPlayReady, only the MP3 file variant is produced as MP3 files. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Audio Search");
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Select MP3 Files");
		page.selectMP3Files();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56126 : Verify that if Tag is already specified with
	 *         Privileged Placeholder then that Tag should not be available.
	 * @Description : Verify that if Tag is already specified with Privileged
	 *              Placeholder then that Tag should not be available.
	 */
	////////@Test(groups = { "regression" }, priority = 37)
	public void verifyTagIsEnabledForAllDocsEnabledforPrivTag() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56126 -Production");
		base.stepInfo(
				"#### Verify that if Tag is already specified with Privileged Placeholder then that Tag should not be available. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// verifying that privledged docs selected tag is enabled at native docs.
		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Select Priv Docs In Tiff Section");
		page.selectPrivDocsInTiffSection(tagname);

		base.stepInfo("Select Priv Docs In Tiff Section");
		page.fillingTextSection();

		base.stepInfo("click Component Mark Complete And Incomplete");
		page.clickComponentMarkCompleteAndIncomplete();

		base.stepInfo("Filling TIFF With Natively Produced Docs And Previously Selected Tag Enabled");
		page.fillingTIFFWithNativelyProducedDocsAndPreviouslySelectedTagEnabled(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56125 : Verify that if Tag is already specified with Right
	 *         Branding then that Tag should not be available.
	 * @Description : Verify that if Tag is already specified with Right Branding
	 *              then that Tag should not be available.
	 */
	////////@Test(groups = { "regression" }, priority =38)
	public void tiffSectionRightBranding() throws Exception {
		base.stepInfo("Test case Id: No:RPMXCON_56125 -Production");
		base.stepInfo(
				"#### Verify that if Tag is already specified with Right Branding then that Tag should not be available. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Verify The Tag On Right Branding");
		page.verifyTheTagOnRightBranding(tagname, Input.randomText);

		base.stepInfo("filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Click Component Mark Complete And Incomplete");
		page.clickComponentMarkCompleteAndIncomplete();

		base.stepInfo("Again Selecting Right Header Branding");
		page.againSelectingRightHeaderBranding(tagname);
		base.stepInfo("verifying tag selected in the  specify branding at right Header branding");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_id :
	 *         RPMXCON-55923 : Verify that in Production, DocFileExtensionCorrected
	 *         should be used in the file name as Native, when DocFileExtension and
	 *         DocFileExtensionCorrected is having different value.
	 * @Description : Verify that in Production, DocFileExtensionCorrected should be
	 *              used in the file name as Native, when DocFileExtension and
	 *              DocFileExtensionCorrected is having different value.
	 */
	////////@Test(groups = { "regression" }, priority = 39)
	public void generatingProductionForCorrectedExtesionFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55923 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that in Production, DocFileExtensionCorrected should be used in the file name as Native, when DocFileExtension and DocFileExtensionCorrected is having different value ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.fileExtDiffDocument);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Rotation Landscape");
		page.selectPrivDocsInTiffSection(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-55924 : Verify that in Production Native file will have no
	 *         extension, when file extension and file extension corrected as blank
	 *         values.
	 * @Description : Verify that in Production Native file will have no extension,
	 *              when file extension and file extension corrected as blank
	 *              values.
	 */
	////////@Test(groups = { "regression" }, priority = 40)
	public void generatingProductionWithoutExtesionFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55923 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that in Production Native file will have no extension, when file extension and file extension corrected as blank values ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.fileExtBlankDocument);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Rotation Landscape");
		page.selectPrivDocsInTiffSection(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-49138 : Verify that when text is exported for file group type
	 *         then it should export the actual text file and not the placeholder.
	 * @Description : Verify that when text is exported for file group type then it
	 *              should export the actual text file and not the placeholder.
	 */
	////////@Test(groups = { "regression" }, priority = 41)
	public void selectTextFileInNativeProducedDocsAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49138 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that when text is exported for file group type then it should export the actual text file and not the placeholder. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		String testData1 = Input.testData1;
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF With Natively Produced Docs");
		page.fillingTIFFWithNativelyProducedDocs(Input.randomText);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-55775 : To verify that If user select PrivTag and if
	 *         translations document is associated to that tag then Native should
	 *         not produced.
	 * @Description : Verify that If user select PrivTag and if translations
	 *              document is associated to that tag then Native should not
	 *              produced
	 */
	////////@Test(groups = { "regression" }, priority =42)
	public void fillingPrivPlaceholderAndGenerateProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55775 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that If user select PrivTag and if translations document is associated to that tag then Native should not produced ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.translationDocument);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Select Priv Docs In Tiff Section");
		page.selectPrivDocsInTiffSection(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("Production is generated successfully on Selecting Priv docs placeholder");
		loginPage.logout();
	}

	/**
	 * @author : Gopinath Created on:NA Modified by:NA @Testcase_Id :
	 *         RPMXCON_55941 : Verify that in Doc View, images tab should displayed
	 *         with produced documents having comments/signautre.
	 * @Description: Verify that in Doc View, images tab should displayed with
	 *               produced documents having comments/signautre.
	 */
	////////@Test(groups = { "regression" },priority = 43)
	public void verifyDocViewImagesTabForCommentsAndSignautre() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		base.stepInfo("RPMXCON-55941 Production-sprint:05");
		base.stepInfo(
				"#### Verify that in Doc View, images tab should displayed with produced documents having comments/signautre. ####");

		UtilityLog.info(Input.prodPath);

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		SessionSearch sessionSearch = new SessionSearch(driver);

		// create production using dat/ingested text

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.signDocumentId);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContiuneRegenerate();

		sessionSearch = new SessionSearch(driver);

		base.stepInfo("Select Docview At Action");
		sessionSearch.selectDocviewAtAction();

		DocViewPage docViewPage = new DocViewPage(driver);

		base.stepInfo("Verify Doc View Images");
		docViewPage.verifyDocViewImages();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56088 : Verify that Production should generate successfully
	 *         by selecting only DAT and 'Generate PDF' option with TechIssue
	 *         Placholder.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate PDF' option with TechIssue
	 *              Placholder.
	 */
	////////@Test(groups = { "regression" }, priority = 44)
	public void pdfSecionWithTechIssueDocAndGenratingProdcution() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56088 Production");
		base.stepInfo(
				"#### Verify that Production should generate successfully by selecting only DAT and  'Generate PDF' option with TechIssue Placholder. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.technicalIssue);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling PDF With Tech Issue Docs");
		page.fillingPDFWithTechIssueDocs(tagname, Input.randomText);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.stepInfo("production generated successfully on filling DAT and PDF section TechissueDocs");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id :
	 *         RPMXCON_56090 : Verify that Production should generate successfully
	 *         by selecting only DAT and 'Generate PDF' option with Natively
	 *         Produced Documents Placholder.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate PDF' option with Natively
	 *              Produced Documents Placholder.
	 */
	////////@Test(groups = { "regression" }, priority = 45)
	public void pdfSectionWithNativelyProdcuedDocAndGenratingProdcution() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56090 Production");
		base.stepInfo(
				"#### Verify that Production should generate successfully by selecting only DAT and 'Generate PDF' option with Natively Produced Documents Placholder. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// create production and pass production name as load files name
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling PDF With Natively Produced Docs");
		page.fillingPDFWithNativelyProduceddDocs(tagname, Input.randomText);

		base.stepInfo("PDF section with Natively produced docs docs are filled");

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		base.stepInfo("production generated successfully on filling DAT and PDF section NativelyProducedDocs");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-49222 : To verify that after uncommit the production, Action
	 *         "Production Deleted" should be displayed in 'Document Audit Report'.
	 * @Description : Verify that after uncommit the production, Action "Production
	 *              Deleted" should be displayed in 'Document Audit Report'.
	 */
	////////@Test(groups = { "regression" }, priority = 46)
	public void verifyingTheAuditReportInGeneratedDocument() throws Exception {

		UtilityLog.info(Input.prodPath);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		base.stepInfo("RPMXCON-49222 Production-sprint:05");
		base.stepInfo(
				"#### Verify that after uncommit the production, Action \"Production Deleted\" should be displayed in  'Document Audit Report'.####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document SelectionPage");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Commit The Production");
		page.commitTheProduction();

		ReportsPage reportPage = new ReportsPage(driver);

		base.stepInfo("Select Document Audit Report");
		reportPage.selectDocumentAuditReport(Input.crammerDocId, Input.produced);

		page = new ProductionPage(driver);

		base.stepInfo("Perform operation on production filter");
		page.ProductionFilter();

		base.stepInfo("Select row by production name and open in wizard.");
		page.selectRowByProductionNameAndOpenWizard(productionname);

		base.stepInfo("Click on uncommit bates");
		page.uncommitBates();

		reportPage = new ReportsPage(driver);

		base.stepInfo("Select Document Audit Report");
		reportPage.selectDocumentAuditReport(Input.crammerDocId, Input.producedDeleted);
		loginPage.logout();

	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA @TestCase_id :
	 *         RPMXCON-56082 : Verify that in Production components, TIFF/PDF
	 *         section displays options for Generating TIFF or Generating PDF
	 * @Description: Verify that in Production components, TIFF/PDF section displays
	 *               options for Generating TIFF or Generating PDF.
	 */
	////////@Test(groups = { "regression" }, priority =47)
	public void verifyingGenerateTIFFandPDF() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case Id: RPMXCON-56082 -Production Sprint 05");
		base.stepInfo(
				"#### Verify that in Production components, TIFF/PDF section displays options for Generating TIFF or Generating PDF.####");

		String productionname = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Verfying Generate TIFF And PDF");
		page.verfyingGenerateTIFFAndPDF();

		base.stepInfo("Generate TIFF and Pdf Radio button is displayed");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-55986 : Verify that after Post
	 *         Geneation is completed, it will displays status on Production
	 *         Generation page as 'Post Generation QC Check Complete'
	 * @Description : Verify that after Post Geneation is completed, it will
	 *              displays status on Production Generation page.
	 */
	////////@Test(groups = { "regression" }, priority = 48)
	public void verifyTheProductionStatusInProducedGeneration() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55986 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that after Post Geneation is completed, it will displays status on Production Generation page. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Verifying Production Status In Generate");
		page.verifyingProductionStatusInGenerate();

		base.stepInfo("Production status in generate page is verified successfully");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-48874 : To verify that Production should generate
	 *         successfully if user map the one source field to multiple DAT fields.
	 * @Description : To verify that Production should generate successfully if user
	 *              map the one source field to multiple DAT fields.
	 */
	////////@Test(groups = { "regression" }, priority = 49)
	public void addDifferentDATFieldAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48874 -Production Sprint 06");
		base.stepInfo(
				"#### To verify that Production should generate successfully if user map the one source field to multiple DAT fields. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("filling DAT Section With Diff DAT Field");
		page.fillingDATSectionWithDiffDATField();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("DAT section with different DAT field is generated successfully");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-49970 : Verify that after updating the placeholder text on
	 *         uncompleting the production, production should generate with the
	 *         updated redaction text.
	 * @Description : Verify that after updating the placeholder text on
	 *              uncompleting the production, production should generate with the
	 *              updated redaction text.
	 */
	////////@Test(groups = { "regression" }, priority = 50)
	public void updatingtextInBurnRedactionAndGeneratingProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49970 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that after updating the placeholder text on uncompleting the production, production should generate with the updated redaction text.. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);

		base.stepInfo("Manage Redaction Tags Page");
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(Redactiontag1);

		base.stepInfo("Click Component Mark Complete And Incomplete");
		page.clickComponentMarkCompleteAndIncomplete();

		base.stepInfo("Filling Placeholder In Burn Redaction");
		page.fillingPlaceholderInBurnRedaction(Input.randomText);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-48951 : To verify that user can load the template in New
	 *         Production and Generate for TIFF file.
	 * @Description : Verify that user can load the template in New Production and
	 *              Generate for TIFF file.
	 */
	////////@Test(groups = { "regression" }, priority = 51)
	public void verifyingTIFFSectionInManageTemplate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48951 -Production Sprint 06");
		base.stepInfo(
				"#### To verify that user can load the template in New Production and Generate for TIFF file. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String Templatename = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page1 = new ProductionPage(driver);

		base.stepInfo("Add New Production");
		page1.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page1.fillingDATSection();

		base.stepInfo("Filling Tiff Section Branding");
		page1.fillingTiffSectionBranding();

		ProductionPage Page = new ProductionPage(driver);

		base.stepInfo("Select Saved Template And Manage Template");
		Page.selectSavedTemplateAndManageTemplate(productionname, Templatename);

		base.stepInfo("Verify Tiff Section Is Selected");
		Page.verifyTiffSectionIsSelected();
		// botClose3
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		productionname = Input.randomText + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Base Info Load Template");
		page.baseInfoLoadTemplate(productionname, Templatename);

		base.stepInfo("Verify DAT And TIFF Field");
		page.verifyDATAndTIFFField();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-48953 : To verify that after loading the template PA can
	 *         change the configuration in any of the steps.
	 * @Description : Verify that after loading the template PA can change the
	 *              configuration in any of the steps.
	 */
	////////@Test(groups = { "regression" }, priority = 52)
	public void verifyingPDFSectionInManageTemplate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48953 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that after loading the template PA can change the configuration in any of the steps.. ####");

		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Logged in as User: " + Input.pa2FullName);

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String tagNamePrev = Input.tagNamePrev;
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		ProductionPage page1 = new ProductionPage(driver);

		base.stepInfo("Add New Production");
		page1.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page1.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page1.fillingNativeSection();

		base.stepInfo("Filling PDF Section");
		page1.fillingPDFSection(tagName, tagNamePrev);

		base.stepInfo("Filling Text Section");
		page1.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page1.navigateToNextSection();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select Saved Template And Manage Template");
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);

		base.stepInfo("Verify PDF Section Is Selected");
		page.verifyPDFSectionIsSelected();
		// botClose3

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		productionname = Input.randomText + Utility.dynamicNameAppender();
		page = new ProductionPage(driver);

		base.stepInfo("Base Info Load Template");
		page.baseInfoLoadTemplate(productionname, Templatename);

		base.stepInfo("Verify DAT And PDF Field");
		page.verifyDATAndPDFField();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-48952 : To verify that user can load the template in New
	 *         Production and Generate PDF files.
	 * @Description : Verify that user can load the template in New Production and
	 *              Generate PDF files
	 */
	////////@Test(groups = { "regression" }, priority = 53)
	public void verifyingPDFFilesGeneratedOnManageTemplate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48952 -Production Sprint 06");
		base.stepInfo("#### Verify that user can load the template in New Production and Generate PDF files.. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String Templatename = Input.randomText + Utility.dynamicNameAppender();
		ProductionPage page1 = new ProductionPage(driver);
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Add New Production");
		page1.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page1.fillingDATSection();

		base.stepInfo("Filling PDF Section Branding");
		page1.fillingPDFSectionBranding();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Filling PDF Section Branding");
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);

		base.stepInfo("Verify Production Component");
		page.verifyProductionComponent();
		// botClose3
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		productionname = Input.randomText + Utility.dynamicNameAppender();

		page = new ProductionPage(driver);

		base.stepInfo("Base Info Load Template");
		page.baseInfoLoadTemplate(productionname, Templatename);

		base.stepInfo("Verify DAT And TIFF Field");
		page.verifyDATAndTIFFField();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-48000 : Verify Branding Bates number
	 * @Description: Verify Branding Bates number
	 */
	////////@Test(groups = { "regression" }, priority = 54)
	public void fillingTIFFSectionWithBatesNumberAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48000 -Production Sprint 06");
		base.stepInfo("#### Verify Branding Bates number.. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.batesDocumentId);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);

		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section With Bates Number");
		page.fillingTIFFSectionWithBatesNumber();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Document Page");
		page.fillingNumberingAndSortingDocumentPage(prefixID);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-49999 : Verify if multiple tag-based brandings are specified
	 *         then first matching tab-based branding should be display on 'Preview'
	 *         document and on produced documents also.
	 * @Description : Verify if multiple tag-based brandings are specified then
	 *              first matching tab-based branding should be display.
	 */
	////////@Test(groups = { "regression" }, priority = 55)
	public void generateTheProductionBySelectingTags() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49999 -Production Sprint 06");
		base.stepInfo(
				"#### Verify if multiple tag-based brandings are specified then first matching tab-based branding should be display... ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagName1 = Input.randomText + Utility.dynamicNameAppender();
		String tagName2 = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName1, Input.tagNamePrev);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName2, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Specify Branding In Tiff Section");
		page.specifyBrandingInTiffSection(tagName1, tagName2, Input.randomText);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-55957 : Verify that if 'Volume Included' toggle is OFF then
	 *         "Archive for FTP" should archive everything in the Production
	 *         Directory.
	 * @Description : Verify that if 'Volume Included' toggle is OFF then "Archive
	 *              for FTP" should archive everything in the Production Directory.
	 */
	////////@Test(groups = { "regression" }, priority = 56)
	public void verifyingProductionAfterAudioIncludedToggleDisabled() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55957 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that if 'Volume Included' toggle is OFF then 'Archive for FTP' should archive everything in the Production Directory.... ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction(tagName);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Prodcution Selection Volume Included");
		page.fillingProdcutionSelectionVolumeIncluded(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Copy Path In QC Tab");
		page.copyPathInQCTab();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-48496 : To verify that the selected metadata is not displayed
	 *         in DAT.
	 * @Description : Verify that the selected metadata is not displayed in DAT if
	 *              the document has at least one of the selected PRIV tags in PRIV
	 *              placeholdering for PDF.
	 */
	////////@Test(groups = { "regression" }, priority = 57)
	public void fillingPDFPrivDocsAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-48496 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that the selected metadata is not displayed in DAT if the document has at least one of the selected PRIV tags in PRIV placeholdering for PDF.. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling DAT Section With Priv Checked");
		page.fillingDATSectionWithPrivChecked();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Select Priv Docs In Tiff Section");
		page.selectPrivDocsInTiffSection(tagName);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();

		base.stepInfo("Production is generated successfully on Selecting PDF Priv tag");
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-56138 : Verify that Count displays correctly on Priv Guard if
	 *         few of the parents and few child of other parents marked as Priv
	 *         document.
	 * @Description : Verify that Count displays correctly on Priv Guard if few of
	 *              the parents and few child of other parents marked as Priv
	 *              document.
	 */
	////////@Test(groups = { "regression" }, priority = 58)
	public void selectingPrivTagAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56138 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that Count displays correctly on Priv Guard if few of the parents and few child of other parents marked as Priv document. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagName1 = Input.randomText + Utility.dynamicNameAppender();
		String tagName2 = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName1, Input.tagNamePrev);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagName2, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		base.stepInfo("View In Doc List");
		sessionSearch.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		base.stepInfo("Selecting Parent Document");
		docPage.selectingParentDocument();

		base.stepInfo("Bulk Tag Existing");
		docPage.bulkTagExisting(tagName1);

		base.stepInfo("Selecting Child Document");
		docPage.selectingChildDocument();

		base.stepInfo("Bulk Tag Existing");
		docPage.bulkTagExisting(tagName2);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling DAT Section With Source ID");
		page.fillingDATSectionWithSourceID();

		base.stepInfo("Filling DAT Section With Priv Checked");
		page.fillingDATSectionWithPrivChecked();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling Priv Tags In TIFF Section");
		page.fillingPrivTagsInTIFFSection(tagName1, tagName2);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page With Tag");
		page.fillingDocSelectionPageWithTag(tagName1, tagName2);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-56074 : Verify the static text in Production-Text component
	 *         section.
	 * @Description : Verify the static text in Production-Text component section.
	 */
	////////@Test(groups = { "regression" }, priority = 59)
	public void verifyTextSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56074 -Production Sprint 06");
		base.stepInfo("#### Verify the static text in Production-Text component section. ####");

		String productionname = Input.randomText + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling Text Section And Verfying Text");
		page.fillingTextSectionAndVerfyingText(Input.exText);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56068 :Verify if TIFF selecting for
	 *         Priv Placeholdering file then PageCount is always 1 and it will skip
	 *         the 'DOCPGCOUNTUPDT'
	 * @Description : Verify that after selecting tiffpagecount in DAT section, it
	 *              will generate the production.
	 */
	////////@Test(groups = { "regression" }, priority = 49)
	public void selectTiffPageCountAndGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56068 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that after Post Generation is completed, it will displays status on Production Generation page. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Add New Field On DAT");
		page.addNewFieldOnDAT();

		base.stepInfo("filling tiffpagecount in DAT");
		page.fillingTiffPageCountToDATFields();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Select Priv Docs In Tiff Section with insert metadata field");
		page.selectPrivDocsInTiffSectionWithMetaDataField(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56069 :Verify if TIFF selecting for
	 *         Tech Placeholdering file then PageCount is always 1 and it will skip
	 *         the 'DOCPGCOUNTUPDT'
	 * @Description : Verify that after selecting tiffpagecount in DAT section, it
	 *              will generate the production.
	 */
	////////@Test(groups = { "regression" }, priority = 50)
	public void selectTiffPageCountAndTechPlaceholderGenerateProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56069 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that after Post Geneation is completed, it will displays status on Production Generation page. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.technicalIssue);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Add New Field On DAT");
		page.addNewFieldOnDAT();

		base.stepInfo("filling tiffpagecount in DAT");
		page.fillingTiffPageCountToDATFields();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("filling TIFF section techissue with insert meta data field");
		page.fillingTiffSectionTechIssueMetaDataField(tagname);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56067 :Verify page counting is not
	 *         skipped if Production exports only Natives,text(not ingested)
	 * @Description :generate the production on filling native and text section.
	 */
	////////@Test(groups = { "regression" }, priority = 51)
	public void generateProductionWithNativeAndTextSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56067 -Production Sprint 06");
		base.stepInfo("#### verify the generation of production  on filling native and text section. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Add New Field On DAT");
		page.addNewFieldOnDAT();

		base.stepInfo("filling tiffpagecount in DAT");
		page.fillingTiffPageCountToDATFields();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56065 :Verify page counting is skipped
	 *         if Production exports only Text (If text ingested)
	 * @Description :generate the production on filling text section.
	 */
	////////@Test(groups = { "regression" }, priority = 51)
	public void generateProductionWithTextSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56065 -Production Sprint 06");
		base.stepInfo("#### verify the production generation  on filling  text section. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Add New Field On DAT");
		page.addNewFieldOnDAT();

		base.stepInfo("filling tiffpagecount in DAT");
		page.fillingTiffPageCountToDATFields();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56059 :Verify page counting is skipped
	 *         if Production exports only Natives,text (Text is ingested) with DAT
	 * @Description :generate the production on filling text section.
	 */
	////////@Test(groups = { "regression" }, priority = 52)
	public void generateProductionWithDATNativeAndTextSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56059 -Production Sprint 06");
		base.stepInfo("#### verify the production generation  on filling  text section. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id :
	 *         RPMXCON-55942 :Verify that Production should generated successfully
	 *         and PDF/TIFF should produced with Comments/Signautre (Documents
	 *         processed through ICE)
	 * @Description : Verify that after loading the template PA can change the
	 *              configuration in any of the steps.
	 */
	////////@Test(groups = { "regression" }, priority = 53)
	public void fillingPDFSectionGeneratingTheProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55942 -Production Sprint 07");
		base.stepInfo("#### verify the production generation  on filling  text section. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create tag with classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.documentId);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56057 : Verify page counting is
	 *         skipped if Production exports only Natives.
	 * @Description : Verify page counting is skipped if Production exports only
	 *              Natives.
	 */
	////////@Test(groups = { "regression" }, priority = 53)
	public void selectDATAndNativeSectionsForProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56057 -Production Sprint 06");
		base.stepInfo("#### Verify page counting is skipped if Production exports only Natives. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling PDF Section");
		page.fillingPDFSection(tagname, Input.tagNamePrev);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");

		page.fillingGeneratePage();

		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-56053 : Verify that once Post
	 *         Geneation is in progress, it will displays status on ProductionGrid
	 *         View as 'Post-Gen QC Checks In Progress'.
	 * @Description : Verify that once Post Geneation is in progress, it will
	 *              displays status on ProductionGrid View as 'Post-Gen QC Checks In
	 *              Progress'.
	 */
	////////@Test(groups = { "regression" }, priority = 54)
	public void verifyPostGenProgressStatusonProductionHomePage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56053 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that once Post Geneation is in progress, it will displays status on ProductionGrid View as 'Post-Gen QC Checks In Progress'. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.searchString20Docs);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Navigate to production page");
		page.navigateToProductionPage();

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButton();
		driver.waitForPageToBeReady();

		// Go To Production Home Page
		base.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();

		base.stepInfo("Refresh page");
		driver.Navigate().refresh();

		base.stepInfo("Verify Production Status In HomePage");
		page.verifyProductionStatusInHomePage(Input.postGenQcChecks, productionname);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-55974 : Verify that after Pre-gen
	 *         checks is in progress, it will displays status on Production
	 *         Generation page
	 * @Description : Verify that after Pre-gen checks is in progress, it will
	 *              displays status on Production Generation page.
	 */
	////////@Test(groups = { "regression" }, priority = 55)
	public void verifyPreGenChecksStatusonProductionPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55974 -Production Sprint 06");
		base.stepInfo(
				"#### Verify that after Pre-gen checks is in progress, it will displays status on Production Generation page. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Navigate To Session Search Page URL");
		sessionSearch.navigateToSessionSearchPageURL();

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.searchString20Docs);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Navigate to production page");
		page.navigateToProductionPage();

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page And Regenerating Again");
		page.clickOnGenerateButtonAndVerifyPreGenChecksStatus();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-49815 :To verify that Production
	 *         should generate successfully if Prefix is up to 50 characters
	 * @Description :verify the generation of production when prefix with 50
	 *              character.
	 */
	////////@Test(groups = { "regression" }, priority = 52)
	public void generationOfProductionWithalphabetinPrefix() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49815 -Production Sprint 07");
		base.stepInfo("#### verify the production generation  on filling  prefix . ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		String prefixID = Utility.randomCharacterAppender(50);
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageAndVerfyingBatesRange(prefixID);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-49814 :To verify that Production
	 *         should generate successfully if Suffix is up to 50 characters
	 * @Description :verify the generation of production when suffix with 50
	 *              character.
	 */
	////////@Test(groups = { "regression" }, priority = 52)
	public void generationOfProductionWithalphabetinSuffix() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49814 -Production Sprint 07");
		base.stepInfo("#### verify the production generation  on filling suffix . ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Utility.randomCharacterAppender(50);

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageAndVerfyingBatesRange(suffixID);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-49813 :To verify that Production
	 *         should generate successfully if prefix and Suffix is less than 50
	 *         characters
	 * @Description :verify the generation of production when prefix and suffix is
	 *              less than 50 character.
	 */
	//////@Test(groups = { "regression" }, priority = 58)
	public void generationOfProductionWithPrefixAndSuffix() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49813 -Production Sprint 07");
		base.stepInfo("#### verify the production generation  on filling prefix and suffix . ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		String prefixID = Utility.randomCharacterAppender(10);
		String suffixID = Utility.randomCharacterAppender(10);

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageAndVerfyingBatesRange(suffixID);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA
	 *         @Testcase_Id:RPMXCON-49812 :To verify that Production using
	 *         template should generate with Prefix and Suffix having 50 characters
	 * @Description :verify the generation of production when prefix and suffix 50
	 *              character with template.
	 */
	//////@Test(groups = { "regression" }, priority = 1)
	public void generationOfProductionWithPrefixAndSuffixWithTemplate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49812 -Production Sprint 07");
		base.stepInfo("#### verify the production generation  on filling prefix and suffix with template. ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String foldername1 = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();
		String productionname1 = Input.randomText + Utility.dynamicNameAppender();
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname1);

		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();

		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingTIFFSectionwithBurnRedaction();

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		page.fillingProductionLocationPageAndPassingText(productionname1);

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageAndVerfyingBatesRange(suffixID);

		TagsAndFoldersPage tagsAndFolderPage1 = new TagsAndFoldersPage(driver);

		base.stepInfo("refreshing the page");
		driver.Navigate().refresh();

		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage1.navigateToTagsAndFolderPage();

		base.stepInfo("Create folder");
		tagsAndFolderPage1.CreateFolder(foldername1, Input.securityGroup);

		ProductionPage Page = new ProductionPage(driver);

		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		Page.addProductionFilter();
		base.stepInfo("Select Saved Template And Manage Template");
		Page.savedTemplateAndNewProdcution(productionname1, Templatename);

		base.stepInfo("Base Info Load Template");
		Page.baseInfoLoadTemplate(productionname, Templatename);

		base.stepInfo("Navigate To Next Section");
		Page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		Page.verifyingPrefixAndSuffixText(prefixID, suffixID);

		base.stepInfo("Navigate To Next Section");
		Page.navigateToNextSection();

		base.stepInfo("Filling Document Selection Page");
		Page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigate To Next Section");
		Page.navigateToNextSection();

		base.stepInfo("Filling Priv Guard Page");
		Page.fillingPrivGuardPage();

		base.stepInfo("Filling Production Location Page And Passing Text");
		Page.fillingProductionLocationPageAndPassingText(productionname);

		base.stepInfo("Navigate To Next Section");
		Page.navigateToNextSection();

		base.stepInfo("Filling Summary And Preview");
		Page.fillingSummaryAndPreview();

		base.stepInfo("Filling Generate Page");
		Page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55336
	 * @Description: To verify the DAT check box is selectable and show/hide works.
	 */
	////@Test(groups = { "regression" }, priority = 60)
	public void verifyingShowAndHideOfDATCheckBox() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55336 -Production Sprint 08");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);

		base.stepInfo("verifying show and hide of DAT checkbox");
		page.verfyingDATCheckBox();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-56021
	 * @Description: Verify that once Generation is completed it should displays
	 *               'Completed' status on Progress bar in Tile View on Production
	 *               Home page
	 */
	////@Test(groups = { "regression" }, priority = 61)
	public void completedStatusVerificationOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56021 -Production Sprint 08");

		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();

		String productionname = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.addProductionFilter();
		page.verifyingProductionStatusInHomePage("Completed", productionname);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48650
	 * @Description: To verify that if "Do not produce full content TIFF / PDFs or
	 *               placeholder TIFF / PDFs for Natively Produced Docs" is disabled
	 *               , then PDFs is generated with Placeholder
	 */
	//@Test(groups = { "regression" }, priority = 62)
	public void verifyThatNativelyProducedDocsWithoutPlaceholder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48650 -Production Sprint 08");

		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.SelectPDFGenerateRadioButton();
		page.fillingPrivledgedDocForPDFSection(tagname, tagNamePrev);
		page.fillingTextSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48643
	 * @Description:To verify that on PDF section, 'Do Not Produce PDFs for Natively
	 *                 Produced Docs' option is disabled by default
	 */
	//@Test(groups = { "regression" }, priority = 63)
	public void verifyingNativelyProducedDocsToggle() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48643 -Production Sprint 08");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);

		base.stepInfo(" Verify the Natively produced docs toggle");
		page.verifyingNativelyProducedToggle();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49236
	 * @Description:To verify that in Production, from Document Selection tab, on
	 *                 clicking on document count link it should redirect to Doc
	 *                 List page with correct document count
	 */
	@Test(groups = { "regression" }, priority = 64)
	public void verifyNavigationToDocListPageFromDocumentSelectionTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49236 -Production Sprint 08");

		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigating to doclist page");
		String docCount = page.navigatingToDocViewPage();

		DocListPage doc = new DocListPage(driver);
		base.stepInfo("Navigated  to doclist page and verifying the DocCount");
		String DocumentCount = doc.verifyingDocCount();

		base.stepInfo("Navigated back to Production page");
		page.verifyNavigationToProductionPage();
		base.textCompareEquals(docCount, DocumentCount, "The document count is equal as expected",
				"The document count is not equal as expected");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49239
	 * @Description:To verify that after selecting the Next BatesNumbers, value
	 *                 should be auto-populated
	 */
	@Test(groups = { "regression" }, priority = 65)
	public void SelectNextBatesNumberAndVerifyingAutoPopulatedValue() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49239 -Production Sprint 08");
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
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
		page.navigatingBackToNumberingAndSortingPage();
		page.SelectNextBatesNumber();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49241
	 * @Description:To verify that 'Click here to View and select the bates
	 *                 number(S)' link should not be available in Mark Complete
	 *                 mode.
	 */
	@Test(groups = { "regression" }, priority = 66)
	public void verifyClickHerelinkNotAvailableInMarkComplete() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49241 -Production Sprint 08");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.verifyClickHereLinkNotAvailableAtMarkComplete();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49243
	 * @Description:To verify that after clicking on 'Mark InComplete' button ,
	 *                 ''Click here to View and select the bates number(S)'' should
	 *                 be available and user can select the bates numbers
	 */
	@Test(groups = { "regression" }, priority = 67)
	public void verifyClickHerelinkAvailableInMarkInComplete() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49243 -Production Sprint 08");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.verifyClickHereLinkNotAvailableAtMarkComplete();
		page.getMarkInCompleteBtn().waitAndClick(10);
		page.enteringNewNextBatesNumber();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49133
	 * @Description:To verify that when text is exported for Priv file then it
	 *                 should export the text with the Placeholder
	 */
	@Test(groups = { "regression" }, priority = 68)
	public void verifyExportInTIFFAndPDFPriviledgedPlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49133 -Production Sprint 08");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String exportname = "E" + Utility.dynamicNameAppender();
		String exportname1 = "E" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch session = new SessionSearch(driver);
		session.basicContentSearch(testData1);
		session.bulkFolderExisting(foldername);

		// create export with TIFF
		ProductionPage page = new ProductionPage(driver);
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("Export for priviledged doc in Tiff section is to generate");
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page = new ProductionPage(driver);
		String text1 = page.getProdExport_ProductionSets().getText();
		if (text1.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(exportname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("Export for priviledged doc in pdf section is to generate");
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49108
	 * @Description:To verify that ' Number Of Custodians' on Production Summary
	 *                 page
	 */
	@Test(groups = { "regression" }, priority = 69)
	public void verifyingUniqueCustodianInSummaryAndPreviewTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49108 -Production Sprint 08");
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
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
		page.verifyingUniqueCustodianNameInSummaryPreviewTab();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48662
	 * @Description:Create a Production with the Prerequisite: MP3 files and by
	 *                     selecting just the DAT file as a production component
	 */
	@Test(groups = { "regression" }, priority = 70)
	public void verifyProductionGenerateForMP3Docs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48662 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.getMetaDataSearch();
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48661
	 * @Description:Verify that production should be generated successfully for
	 *                     audio files
	 */
	@Test(groups = { "regression" }, priority = 71)
	public void verifyProductionGenerateForAudioFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48661 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.SelectMP3FileAndVerifyLstFile();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48492
	 * @Description:To verify that If user select PrivTag and if Audio document is
	 *                 associated to that tag then Native should not produced
	 */
	@Test(groups = { "regression" }, priority = 72)
	public void SelectPrivTagWithAudioDocumentAndNativeNotProduced() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48492 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.getMP3CheckBox().waitAndClick(10);
		page.fillingTextSection();
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
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48493
	 * @Description:To verify that If user select PrivTag and if Audio document is
	 *                 not associated to that tag then Native should be produced
	 */
	@Test(groups = { "regression" }, priority = 73)
	public void SelectPrivTagWithAudioDocumentAndNativeProduced() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48493 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.getMP3CheckBox().waitAndClick(10);
		page.fillingTextSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48504
	 * @Description:To verify that Tiff/PDF should generate with Priv placeholdering
	 *                 even though Burn redactions and File group/tag based
	 *                 placeholdering is exists.
	 */
	@Test(groups = { "regression" }, priority = 74)
	public void ProductionGenerateWithPrivHolderWithBurnRedaction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48504 -Production Sprint 09");
		String productionname = "p" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String Redactiontag1;
		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingNativeDocsPlaceholder(tagname1);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.burnRedactionWithRedactionTag(Redactiontag1);
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

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingNativeDocsPlaceholder(tagname1);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48204
	 * @Description:To verify In Productions DAT, provide the TIFFPageCount for each
	 *                 document produced
	 */
	@Test(groups = { "regression" }, priority = 75)
	public void verifyProductionDATProvideTIFFPageCount() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48204 -Production Sprint 09");
		String bates = "B" + Utility.dynamicNameAppender();
		String bates1 = "B" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Production", "TIFFPageCount", bates);
		page.addDATFieldAtThirdRow("Doc Basic", "DocID", bates1);
		page.fillingNativeSection();
		page.fillingPDFSectionWithBrandingText();
		page.slipSheetToggleEnable();
		page.availableFieldSelection("BatesNumber");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Production", "TIFFPageCount", bates);
		page.addDATFieldAtThirdRow("Doc Basic", "DocID", bates1);
		page.fillingNativeSection();
		page.fillingTIFFSectionWithBatesNumber();
		page.slipSheetToggleEnable();
		page.availableFieldSelection("BatesNumber");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48379
	 * @Description To verify that If user select RedactionTag and if non-audio
	 *              document is associated to the selected Redaction Tag then Native
	 *              should not produced
	 * 
	 */
	@Test(groups = { "regression" }, priority = 76)
	public void verifyNativeIsNotProducedAtGeneration() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48379 -Production Sprint 09");

		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTIFFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48380
	 * @Description To verify that If user select RedactionTag and if non-audio
	 *              document is not associated to the selected Redaction Tag then
	 *              Native should produced
	 * 
	 */
	@Test(groups = { "regression" }, priority = 77)
	public void verifyNativeIsProducedAtGeneration() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48380 -Production Sprint 09");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

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
		page.fillingTextSection();
		page.fillingTIFFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48376
	 * @Description To verify that user select the PrivTag and if non-audio document
	 *              is not associated to that selected tag then Native should
	 *              produced.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 78)
	public void VerifyPrivTagNotAssociatedNativeProduced() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48376 -Production Sprint 09");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48378
	 * @Description To verify that if PRIV document is selected and TIFF or PDF
	 *              sections are not selected then Native should be generated
	 * 
	 */
	@Test(groups = { "regression" }, priority = 79)
	public void verifyPDFOrTIFFNotSelectedNativeGenerate() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48378 -Production Sprint 09");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48375
	 * @Description To verify that If user select PrivTag and if non-audio document
	 *              is associated to that tag then Native should not produced
	 * 
	 */
	@Test(groups = { "regression" }, priority = 80)
	public void verifyNativeNotProduced() throws Exception {

		UtilityLog.info(Input.prodPath);
		;
		base.stepInfo("RPMXCON-48375 -Production Sprint 09");
		base.stepInfo(
				"To verify that If user select PrivTag and if non-audio document is associated to that tag then Native should not produced");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
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

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48534
	 * @Description To verify that if user click on Yes button on confirmation
	 *              message, Tiff/PDF should produced with blank pages
	 * 
	 */
	@Test(groups = { "regression" }, priority = 81)
	public void verifyTiffWithBlankPagesAfterGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48534 -Production Sprint 09");
		base.stepInfo(
				"To verify that if user click on Yes button on confirmation message, Tiff/PDF should produced with blank pages");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectBlankRemovalInTiffSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48304
	 * @Description To verify the value of BeginningAttachBates should be displayed
	 *              Beginning bates of the parent of the family on Production
	 * 
	 */
	@Test(groups = { "regression" }, priority = 82)
	public void BeginningAttachBatesInDatAndGenerateProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48304 -Production Sprint 09");
		base.stepInfo(
				"To verify the value of BeginningAttachBates should be displayed Beginning bates of the parent of the family on Production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = "B" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Bates", "BeginingAttachBates", BatesNumber);
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
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48318
	 * @Description To verify that Production should be generated successfully if
	 *              there is one single non-redacted area.
	 */
	@Test(groups = { "regression" }, priority = 83)
	public void verifyProductionGeneratedwithNonRedactedArea() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48318 -Production Sprint 09");
		base.stepInfo(
				"To verify that Production should be generated successfully if there is one single non-redacted area.");

		String redactiontag = "Redaction" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(redactiontag);
		System.out.println("First Redaction Tag is created" + redactiontag);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch("morning", "North American English");
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocView();

		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();

		DocViewRedactions redact = new DocViewRedactions(driver);
		redact.deleteAllAppliedRedactions();
		redact.clickOnAddRedactionForAudioDocument();
		redact.addAudioRedaction(Input.startTime, Input.endTime, redactiontag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.burnRedactionWithRedactionTagInTiffSection(redactiontag);
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
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48381
	 * @Description To verify that if Redacted document is selected and Tiff/PDF
	 *              sections are not selected then Native should be generated
	 * 
	 */
	@Test(groups = { "regression" }, priority = 84)
	public void verifyNativeProducedAtGeneration() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48381 -Production Sprint 09");
		base.stepInfo(
				"To verify that if Redacted document is selected and Tiff/PDF sections are not selected then Native should be generated");

		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		String Redactiontag;
		Redactiontag = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(Redactiontag);
		System.out.println("First Redaction Tag is created" + Redactiontag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Pre-requisites
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49055
	 * @Description Verify Remove documents option is not getting displayed in
	 *              Production.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 85)
	public void verifyRemoveDocumentOptionNotDisplay() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49055-Production Sprint 09");
		base.stepInfo("Verify Remove documents option is not getting displayed in Production.");

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Applying production filter");
		page.addProductionFilter();

		base.stepInfo("Verifying the dropdown option in the completed production");
		page.verifyDropDownValueInCompletedProduction("Remove");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49053
	 * @Description Verify Add documents option is not getting displayed in
	 *              Production.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 86)
	public void verifyAddDocumentOptionNotDisplay() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49053-Production Sprint 09");
		base.stepInfo("Verify Add documents option is not getting displayed in Production.");

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Applying production filter");
		page.addProductionFilter();

		base.stepInfo("Verifying the dropdown option in the completed production");
		page.verifyDropDownValueInCompletedProduction("Add");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-49058
	 * @Description In Productions, text was produced with redaction burned, when
	 *              Burn Redactions option was disabled-2
	 * 
	 */
	@Test(groups = { "regression" }, priority = 87)
	public void productionWithBurnedRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49058 -Production Sprint 09");
		base.stepInfo(
				"In Productions, text was produced with redaction burned, when Burn Redactions option was disabled-2");

		tagname = "Tag" + Utility.dynamicNameAppender();
		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		String Redactiontag;
		Redactiontag = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(Redactiontag);
		System.out.println("First Redaction Tag is created" + Redactiontag);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag);

		docViewRedactions.nextDocViewBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
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
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55944
	 * @Description Verify that REDACTED text should displayd by default if user
	 *              selects the Redaction Tag in TIFF
	 * 
	 */
	@Test(groups = { "regression" }, priority = 88)
	public void verifyPlaceholderInTIFFBurnRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55944 -Production Sprint 10");
		base.stepInfo("Verify that REDACTED text should displayd by default if user selects the Redaction Tag in TIFF");

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		page.selectGenerateOption(false);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55945
	 * @Description Verify that REDACTED text should displayd by default if user
	 *              selects the Redaction Tag in PDF
	 * 
	 */
	@Test(groups = { "regression" }, priority = 89)
	public void verifyPlaceholderInPDFBurnRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55945 -Production Sprint 10");
		base.stepInfo("Verify that REDACTED text should displayd by default if user selects the Redaction Tag in PDF");

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		page.selectGenerateOption(true);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55926
	 * @Description Verify changes in Text component section
	 * 
	 */
	@Test(groups = { "regression" }, priority = 90)
	public void verifyTextInTextSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55926 -Production Sprint 10");
		base.stepInfo("Verify changes in Text component section");

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.verifyTextInTextSection();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55947
	 * @Description:Verify if user included branding that is based on the tag types
	 *                     only then branding should be display on 'Preview'
	 *                     document and on produced documents also
	 */
	@Test(groups = { "regression" }, priority = 91)
	public void verifyBrandingSectionInGenration() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55947 -Production Sprint 10");
		base.stepInfo(
				"Verify if user included branding that is based on the tag types only then branding should be display on 'Preview' document and on produced documents also");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectBrandingInTiffAndPdfSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectBrandingInTiffAndPdfSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55976
	 * @Description Verify that after Pregen check completed it should displays
	 *              'Reserving Bates Range' status on Production Geneartion page
	 * 
	 */
	@Test(groups = { "regression" }, priority = 92)
	public void verifyStatusInGeneratePage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55976 -Production Sprint 10");
		base.stepInfo(
				"Verify that after Pregen check completed it should displays 'Reserving Bates Range' status on Production Geneartion page");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
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
		driver.waitForPageToBeReady();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Pre-Generation Checks Completed");
		page.verifyProductionStatusInGenPage("Reserving Bates Range");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55977
	 * @Description: Verify that it should displays 'Pre-Gen Check - 19999/20000
	 *               docs' status on Progress bar in Production Tile View
	 */
	@Test(groups = { "regression" }, priority = 93)
	public void preGenChecksStatusVerifyOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55977 -Production Sprint 10");
		base.stepInfo(
				"Verify that it should displays 'Pre-Gen Check - 19999/20000 docs' status on Progress bar in Production Tile View");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify Pre-gen checks is in progress status on Tile view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
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
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyProductionStatusInHomePage("Pre-Gen Checks - 6/6 Docs", productionname);

		// Deleting folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55954
	 * @Description Verify that use cannot access the Production deatils by copying
	 *              the Production URL if user does not have Production rights
	 * 
	 */
	@Test(groups = { "regression" }, priority = 94)
	public void verifyingProductionAccess() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("RPMXCON-55954 -Production Sprint 10");
		base.stepInfo(
				"Verify that use cannot access the Production deatils by copying the Production URL if user does not have Production rights");

		base = new BaseClass(driver);
		base.UnSelectTheProductionChecKboxInUser(Input.pa1userName);
		driver.Navigate().refresh();
		base.impersonateSAtoPA();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base = new BaseClass(driver);
		base.UnSelectTheProductionChecKboxInUser(Input.pa1userName);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47775
	 * @Description To Verify the availability of 'Delete' Option in drop down
	 *              action menu For(Draft Mode)
	 * 
	 */
	@Test(groups = { "regression" }, priority = 95)
	public void AvailabilityOfDeleteOptionInDraftMode() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47775 -Production Sprint 10");
		base.stepInfo("To Verify the availability of 'Delete' Option in drop down action menu For(Draft Mode");
		String productionname = "p" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();

		base.stepInfo_DataBase("Navigating back to Production home page");
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		base.stepInfo("Deleting the Drafted production");
		page.deleteProduction(productionname);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48271
	 * @Description To Verify Produced PDFs should be available for being presented
	 *              in the DocView for the document
	 * 
	 */
	@Test(groups = { "regression" }, priority = 96)
	public void PDFDocumentDisplayedInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48271 -Production Sprint 10");
		base.stepInfo(
				"To Verify Produced PDFs should be available for being presented in the DocView for the document");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		base.stepInfo("View searched for  docs in Doc view");
		sessionsearch.ViewInDocView();

		driver.waitForPageToBeReady();
		DocViewPage doc = new DocViewPage(driver);
		doc.clickOnImageTab();
		driver.waitForPageToBeReady();
		doc.verifyProductionNameForPDFFileInDocView(productionname);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47749
	 * @Description:Admin able to view production guard information on the self
	 *                    production wizard
	 */
	@Test(groups = { "regression" }, priority = 97)
	public void verifyProductionGuardInformation() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47749 -Production Sprint 10");

		base.stepInfo("Admin able to view production guard information on the self production wizard");
		String testData1 = Input.testData1;
		String tagname = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		driver.scrollingToBottomofAPage();

		page.getTIFFTab().waitAndClick(10);

		page.fillingPrivledgedDocForPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();

		page.AddRuleAndRemoveRule(tagname);

		base.waitTillElemetToBeClickable(page.getRemoveLink());
		page.getRemoveLink().Click();

		if (page.getTags().isDisplayed()) {
			base.failedStep("rules popup screen is displayed");
		} else {
			base.passedStep("Rules popup screen is not displayed as expected");
		}

		page.AddRuleAndRemoveRule(tagname);
		String Doc = page.getDocumentSelectionLink().getText();
		base.stepInfo("Navigating to Docview page");

		base.digitCompareEquals(purehit, Integer.parseInt(Doc), "Priv guard and purehit Documents count are equal",
				"Priv guard and purehit Documents count are not  equal");

		page.getDocView().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();

		MiniDocListPage miniDocListPage = new MiniDocListPage(driver);
		base.waitTillElemetToBeClickable(miniDocListPage.getDocumentCountFromDocView());
		driver.waitForPageToBeReady();
		if (miniDocListPage.getDocumentCountFromDocView().Displayed()) {
			String sizeofList = miniDocListPage.getDocumentCountFromDocView().getWebElement().getText();
			driver.waitForPageToBeReady();
			System.out.println("Doc view" + sizeofList);

			String[] doccount = sizeofList.split(" ");
			String Document = doccount[1];
			System.out.println("doclist page document count is" + Document);

			base.digitCompareEquals(Integer.parseInt(Doc), Integer.parseInt(Document),
					"Document count is equal as expected", "Count Mismatches with the Documents");
		}
		for (int i = 0; i < 2; i++) {
			driver.Navigate().back();
		}
		for (int i = 0; i < 3; i++) {
			driver.waitForPageToBeReady();
			page.getNextButton().waitAndClick(10);
		}
		String docCount = page.VerifyingDocListCountWithPrivGaurdCount();

		DocListPage doc = new DocListPage(driver);

		base.stepInfo("Navigated  to doclist page and verifying the DocCount");
		String DocumentCount = doc.verifyingDocCount();

		base.textCompareEquals(docCount, DocumentCount, "The document count is equal as expected",
				"The document count is not equal as expected");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55955
	 * @Description Verify that RMU can access the Production by copying the
	 *              Production URL if user is part of that security group/Project
	 * 
	 */
	@Test(groups = { "regression" }, priority = 98)
	public void verifyingProductionPageAccessUsingSecurityGroup() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("RPMXCON-55955 -Production Sprint 10");
		base.stepInfo(
				"Verify that RMU can access the Production by copying the Production URL if user is part of that security group/Project");

		base = new BaseClass(driver);
		base.SelectSecurityGrp(Input.rmu1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.addANewProduction(productionname);
		page.fillingDATSection();

		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

		driver.waitForPageToBeReady();
		String currentURL = driver.getWebDriver().getCurrentUrl();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		page = new ProductionPage(driver);
		page.addANewProduction(productionname1);
		page.fillingDATSection();

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		if (page.getBeginningBates().isDisplayed()) {
			base.passedStep("Navigated to the previous  production URL as expected");
		} else {
			base.failedStep(" Not Navigated to the previous production URL as expected");
		}

		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base = new BaseClass(driver);
		base.SelectDefaultSecurityGrp(Input.rmu1userName);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55943
	 * @Description:Verify that Production should be generated successfully if PDF
	 *                     documents are ICE processed with Upload set
	 */
	@Test(groups = { "regression" }, priority = 99)
	public void generateTheProdcutionForPDFFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55943 -Production Sprint 10");
		base.stepInfo(
				"Verify that Production should be generated successfully if PDF documents are ICE processed with Upload set");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(6);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		doc.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55918
	 * @Description:To verify that when Tech Issue placeholdering is enabled, the
	 *                 text file should exported with the placeholder text.
	 */
	@Test(groups = { "regression" }, priority = 100)
	public void GenerateTheProductionForTechIssuePlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55918-from Production");
		base.stepInfo(
				"To verify that when Tech Issue placeholdering is enabled, the text file should exported with the placeholder text.");
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
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
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
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47175
	 * @Description:Verify that produced PDF/TIFF files should not be split when
	 *                     'Split Sub Folders' is ON with split count as 10 and
	 *                     selected documents <= 10
	 */
	@Test(groups = { "regression" }, priority = 101)
	public void verifyTheSubFolderAfterGenration() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47175 -Production Sprint 10");
		base.stepInfo(
				"Verify that produced PDF/TIFF files should not be split when 'Split Sub Folders' is ON with split count as 10 and selected documents <= 10");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// document for pdf section
		ProductionPage page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// document for tiff section
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		String Searchstring = "teleca";
		sessionSearch.basicContentSearch(Searchstring);
		sessionSearch.bulkTagExisting(tagname1);

		// document for pdf section
		page = new ProductionPage(driver);
		String productionname2 = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname1, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// document for tiff section
		page = new ProductionPage(driver);
		String productionname3 = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname3);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname3);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"Verify that produced PDF/TIFF files should not be split when 'Split Sub Folders' is ON with split count as 10 and selected documents <= 10");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-55956
	 * @Description Verify that if 'Volume Included' toggle is ON then "Archive for
	 *              FTP" should archive everything in the Production Directory
	 *              including ''Volume Included'' subfolder
	 * 
	 */
	@Test(groups = { "regression" }, priority = 102)
	public void verifyingTheProductionOnVolumeIncludedToggle() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-55956 -Production Sprint 10");
		base.stepInfo(
				"Verify that if 'Volume Included' toggle is ON then 'Archive for FTP' should archive everything in the Production Directory including ''Volume Included'' subfolder");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.verifyVolumeIncludedToggleInProductionSelection();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49901
	 * @Description Verify and generate Production with Search as source
	 * 
	 */
	@Test(groups = { "regression" }, priority = 103)
	public void verifyProductionGenerationWithSearches() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-49901 -Production Sprint 10");
		base.stepInfo("Verify and generate Production with Search as source");

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String SearchName1 = "Search" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);
		sessionSearch.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchPA);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(SearchName, SearchName1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48577
	 * @Description To verify that Export Bates option is available on
	 *              Production-Generate tab
	 * 
	 */
	@Test(groups = { "regression" }, priority = 104)
	public void verifyExportBatesOptionInGenerateTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48577 - from Production");
		base.stepInfo("To verify that Export Bates option is available on Production-Generate tab");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
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
		page.getExportBatesButton().ScrollTo();
		if (page.getExportBatesButton().isDisplayed()) {
			base.passedStep("Export Bates Option is visible in Generate tab as Expected");
		} else {
			base.failedStep("Export Bates Option is not visible in Generate tab as Expected");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48601
	 * @Description To verify that Export bates is disabled if pre gen check is not
	 *              completed
	 * 
	 */
	@Test(groups = { "regression" }, priority = 105)
	public void verifyExportBatesOptionDisabledInGenerateTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48601 - from Production");
		base.stepInfo("To verify that Export bates is disabled if pre gen check is not completed");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
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
		page.getbtnProductionGenerate().isDisplayed();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");
		String color = page.getExportBatesButton().GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#3276b1";
		base.textCompareEquals(ActualColor, ExpectedColor,
				"Export Bates option is disabled before pre gen check as Expected",
				"Export Bates option is not disabled before pre gen check as Expected");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48642
	 * @Description:To verify that on Tiff OR PDF section, 'Do Not Produce TIFFs for
	 *                 Natively Produced Docs' option is disabled by default
	 */
	@Test(groups = { "regression" }, priority = 106)
	public void verifyingNativelyProducedDocsToggleIsDisAbledInTiffAndPDFSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48642 - from Production");
		base.stepInfo(
				"To verify that on Tiff OR PDF section, 'Do Not Produce TIFFs for Natively Produced Docs' option is disabled by default");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);

		base.stepInfo("Verifying 'Do Not Produce TIFFs for Natively Produced Docs' in TIFF section");
		page.verifyingNativelyProducedToggle();

		base.stepInfo("Verifying 'Do Not Produce TIFFs for Natively Produced Docs' in PDF section");
		page.getPDFGenerateRadioButton().waitAndClick(10);

		String color = page.getDoNotProduceFullContentTiff().GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#e54036";
		base.textCompareEquals(ActualColor, ExpectedColor,
				"Do Not Produce TIFFs for Natively Produced Docs' option is disabled by default  as Expected",
				" Do Not Produce TIFFs for Natively Produced Docs' option is not  disabled by default as Expected");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48604
	 * @Description Verify the exported CSV data
	 * 
	 */
	@Test(groups = { "regression" }, priority = 1)
	public void verifyExportBatesGeneratedFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48604 - from Production-sprint 11");
		base.stepInfo("Verify the exported CSV data");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
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
		page.getbtnProductionGenerate().isDisplayed();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getExportBatesButton().isDisplayed();
		page.getExportBatesButton().waitAndClick(5);
		BaseClass base = new BaseClass(driver);
		base.VerifySuccessMessage(
				"Export bates range has been added to background services. You will get notification once completed");
		driver.waitForPageToBeReady();
		page.getNotificationLink().waitAndClick(5);
		page.getViewAll().waitAndClick(10);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
		}
		page.getDownloadLinkforExport().Click();
		base.csvFileVerification();
		base.passedStep("Verified the exported CSV data");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48603
	 * @DescriptionTo verify that user can download the CSV file once
	 *                Production-Generate-Export is completed
	 * 
	 */
	@Test(groups = { "regression" }, priority = 1)
	public void verifyExportBatesGeneratedFileInNotification() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48603 - from Production sprint 11");
		base.stepInfo("To verify that user can download the CSV file once Production-Generate-Export is completed");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
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
		page.getbtnProductionGenerate().isDisplayed();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");

		page.getExportBatesButton().waitAndClick(5);
		BaseClass base = new BaseClass(driver);
		base.VerifySuccessMessage(
				"Export bates range has been added to background services. You will get notification once completed");
		page.getNotificationLink().waitAndClick(5);
		page.verifyExportedCSVFile();
		base = new BaseClass(driver);
		base.csvFileVerification();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48654
	 * @Description To verify that if "Do Not Produce PDFs for Natively Produced
	 *              Docs" is Enabled, then only Native should be produced
	 * 
	 */
	@Test(groups = { "regression" }, priority = 107)
	public void verifyingTheProductionOnVolumeIncludedToggl() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48654-Production Sprint 11");
		base.stepInfo(
				"To verify that if 'Do Not Produce PDFs for Natively Produced Docs' is Enabled, then only Native should be produced");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.telecaSearchString, Input.docFile);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTIFFSection(tagname, Input.searchString4);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().isDisplayed();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.verifyVolumeIncludedToggleInProductionSelection();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48307
	 * @Description To verify that the order of docs in OPT is matching the order of
	 *              docs in DAT.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 108)
	public void verifyOrderOfDATAndOPTInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48307-Production Sprint 11");
		base.stepInfo("To verify that the order of docs in OPT is matching the order of docs in DAT.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.fillingAdvancedInTiffSection();
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
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48530
	 * @Description:To verify that by Blank Page Removal option is OFF by default in
	 *                 Tiff/PDF section and should display confirmation message when
	 *                 it is enable
	 * 
	 */
	@Test(groups = { "regression" }, priority = 109)
	public void verifyBlankPageRemovalToggle() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48530 -Production Sprint 11");
		base.stepInfo(
				"To verify that by Blank Page Removal option is OFF by default in Tiff/PDF section and should display confirmation message when it is enable.");

		String productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.verifyBlankPageRemovalMeassage();
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47173
	 * @Description:Verify that TIFF files should be copied to folder when 'Split
	 *                     Sub Folders' is ON with split count as 10
	 */
	@Test(groups = { "regression" }, priority = 110)
	public void verifyTheSubFolderInProductionGeneration() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47173 -Production Sprint 11");
		base.stepInfo(
				"Verify that TIFF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.verifySubFolderToggle();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48196
	 * @Description:To Verify In Productions, production template Should retain the
	 *                 redaction tags configured in the production.
	 */
	@Test(groups = { "regression" }, priority = 111)
	public void verifySaveTemplateAndRetainedValueInBurnRedaction() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48196 -Production Sprint 11");
		base.stepInfo(
				"To Verify In Productions, production template Should retain the redaction tags configured in the production.");

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingMP3FileWithBurnRedaction(Redactiontag1);
		page.navigateToNextSection();

		ProductionPage Page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		driver.Navigate().refresh();
		Page.savedTemplateAndNewProdcution(productionname, Templatename);
		Page.baseInfoLoadTemplate(productionname1, Templatename);
		page.verifyingMP3FileBurnRedaction(Redactiontag1);
		page.getCheckBoxCheckedVerification(page.getMP3FileSelectRedactionTags());
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48285
	 * @Description:To Verify Production Using Template Should be able to load the
	 *                 configuration of Rotation in PDF and TIFF.
	 */
	@Test(groups = { "regression" }, priority = 112)
	public void verifySaveTemplateAndRetainedValueInRotationConfiguration() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48285 -Production Sprint 11");
		base.stepInfo(
				"To Verify Production Using Template Should be able to load the configuration of Rotation in PDF and TIFF");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		driver.scrollPageToTop();
		page.getRotationLandScapeDropdown().Click();
		page.navigateToNextSection();

		ProductionPage Page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		driver.Navigate().refresh();
		Page.savedTemplateAndNewProdcution(productionname, Templatename);
		Page.baseInfoLoadTemplate(productionname1, Templatename);
		page.verifyRotationInComponentTab("pdf");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47976
	 * @Description: To Verify Keep Docs w/ No Master Date on Numbering and Sorting
	 *               Page.
	 */
	@Test(groups = { "regression" }, priority = 113)
	public void verifyTheMasterDateInGeneratedProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47976 -Production Sprint 11");
		base.stepInfo("To Verify Keep Docs w/ No Master Date on Numbering and Sorting Page.");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(testData1, Input.newNearDupeDocId);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.verifyingComponentTabOnMarkComplete();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.verifyMasterDateRetainedOnMarkComplete();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-46895
	 * @Description:Verify the Production for Audio Files for International Language
	 *                     Package.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 114)
	public void ProductionGenerateForAudioFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-46895 -Production Sprint 11");
		base.stepInfo("Verify the Production for Audio Files for International Language Package.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.SelectMP3FileAndVerifyLstFile();
		driver.scrollPageToTop();
		page.getSaveBtn().Click();
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

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48301
	 * @Description:To verify that in Production, the order of Documents is
	 *                 maintained even when the user has not "checked" the option to
	 *                 "club family members together".
	 */
	@Test(groups = { "regression" }, priority = 115)
	public void verifyOrderOfDocumentsInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48301 -Production Sprint 11");
		base.stepInfo(
				"To verify that in Production, the order of Documents is maintained even when the user has not 'checked' the option to 'club family members together'.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "335ID00000005");
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.selectingAllDocuments();
		driver.waitForPageToBeReady();
		doc.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingWithoutFamilyMember(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47966
	 * @DescriptionTo Verify The OPT/LOG file generated in a production have all
	 *                required information
	 * 
	 */
	@Test(groups = { "regression" }, priority = 116)
	public void verifyOPTInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47966-Production component");
		base.stepInfo("To Verify The OPT/LOG file generated in a production have all required information.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(4);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.fillingAdvancedInTiffSection();
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

		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String home = System.getProperty("user.home");

		page.unzipping(home + "/Downloads/" + name + ".zip", home + "/Downloads");
		System.out.println("Unzipped the downloaded files");

		driver.waitForPageToBeReady();
		for (String line : Files
				.readAllLines(Paths.get(home + "/Downloads/" + "VOL0001/Load Files/" + name + "_TIFF.OPT"))) {
			for (String part : line.split("\\s+")) {

				System.out.println("the value is" + part);

				if (part.contains("Z:\\VOL0001\\Images") && part.contains("tiff")) {
					System.out.println("Text is displayed as expected");
				} else {
					base.failedStep("the text is not displayed as expected");
				}
			}
		}
		base.passedStep("Text is displayed as expected");

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-56054
	 * @Description: Verify that after Post Geneation is completed, it will displays
	 *               status on Production Grid View page as 'Post Generation QC
	 *               Check Complete'
	 */
	@Test(groups = { "regression" }, priority = 117)
	public void verifyPostGenCompleteStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56054 - from Production component");
		base.stepInfo(
				"Verify that after Post Geneation is completed, it will displays status on Production Grid View page as 'Post Generation QC Check Complete'");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
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
		page.clickGenarateWithoutWait();
		page.getbtnContinueGeneration().isElementAvailable(150);
		page.getbtnContinueGeneration().isDisplayed();
		base.waitForElement(page.getbtnContinueGeneration());
		page.getbtnContinueGeneration().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Post-Gen QC Checks Complete", productionname);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48876
	 * @Description:To verify that belly band message is displays if user map the
	 *                 one source field to multiple DAT fields
	 */
	@Test(groups = { "regression" }, priority = 118)
	public void verifyPreGenStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48876 - from Production component");
		base.stepInfo(
				"To verify that belly band message is displays if user map the one source field to multiple DAT fields");

		String Batesnumber = "B" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, Input.batesNumber, Batesnumber);

		base.stepInfo("Verifying belly band message in production component tab");
		page.verifyingBellyBandMessageInDATSection(Batesnumber);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-46870
	 * @Description:To Verify generation of redacted TIFFs.
	 */
	@Test(groups = { "regression" }, priority = 119)
	public void verifyGenerationInRedactedTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-46870 - from Production component");
		base.stepInfo("To Verify generation of redacted TIFFs.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
         // doc1
		docViewRedactions.selectDoc1();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

        //create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

        // Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTags(Input.defaultRedactionTag, Redactiontag1);
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
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47931
	 * @Description:To Verify, when Include Family Members is selected, should not
	 *                 give error message on click of Mark Complete
	 */
	@Test(groups = { "regression" }, priority = 120)
	public void verifyingIncludeFamiliesToggleInDocumentSelectionPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47931 - from Production component");
		base.stepInfo(
				"To Verify, when Include Family Members is selected, should not give error message on click of Mark Complete");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		driver.waitForPageToBeReady();
		page.visibleCheck("Production Components");
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.visibleCheck("Numbering and Sorting");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSlectionTab(foldername);
		page.verifyingIncludeFamiliesToggleInDocumentSelectionPage();
		loginPage.logout();
	}
	
	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-47750
	 * @Description:Admin able to view and enter production guard information on the self production wizard.
	 */
	@Test(groups = { "regression" }, priority = 121)
	public void verifyProductionGuardInformationinProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47750-Production component");

		base.stepInfo("Admin able to view and enter production guard information on the self production wizard.");
		String testData1 = Input.testData1;
		String tagname = Input.randomText + Utility.dynamicNameAppender();

		
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for Tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getTIFFTab().waitAndClick(10);
		page.fillingPrivledgedDocForPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID,suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true,tagname);
		String DocCount = page.getDocumentSelectionLink().getText();
		base.digitCompareEquals(purehit, Integer.parseInt(DocCount), "Document count is displayed correctly as expected",
				"Document count is not displayed correctly as expected");
		loginPage.logout();
	}
	
	
	
	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48206
	 * @Description:To Verify In Productions, the validation of specific file types when entering placeholder text for TIFF/PDF.
	 */
	@Test(groups = { "regression" }, priority =122)
	public void verifyingWarningMessageInProductionComponentTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("RPMXCON-48206-Production component");
		base.stepInfo("To Verify In Productions, the validation of specific file types when entering placeholder text for TIFF/PDF.");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithNativelyProducedDocsFileType(Input.randomText);
		loginPage.logout();
		
	}
	
	

	/**
	 * @author Brundha Test case id-RPMXCON-48632
	 * @Description To verify that exported CSV should be sorted by BegBates
	 * 
	 */
	@Test(groups = { "regression" }, priority = 123)
	public void verifyExportCSVFileSorted() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48632 - from Production-sprint 11");
		base.stepInfo("To verify that exported CSV should be sorted by BegBates");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID,suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Pre-Generation Checks Completed");
		base.waitTillElemetToBeClickable(page.getExportBatesButton());
		page.verifyExportBatesButtonIsEnabled();
		page.getExportBatesButton().Click();
		BaseClass base = new BaseClass(driver);
		driver.waitForPageToBeReady();
		page.getNotificationLink().isDisplayed();
		page.getNotificationLink().waitAndClick(5);
		page.getViewAll().waitAndClick(10);
		for (int i = 0; i < 10; i++) {
				if (page.getDownloadLinkforExport().isDisplayed()) {
					page.getDownloadLinkforExport().Click();
					break;
				}else {
					driver.Navigate().refresh();}}
		
		base.VerifyingCSVFileDownloadedAndSorted();
		base.passedStep("verified the exported CSV is sorted by BegBates");
		loginPage.logout();
	}
	
	
	
	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48167
	 * @Description:To Verify All MP3 Files info should get loaded when using (Saved
	 *                 )Template for Production.
	 */
	@Test(groups = { "regression" }, priority = 124)
	public void verifySaveTemplateAndRetainedValueInComponentTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48167 -Production component");
		base.stepInfo("To Verify All MP3 Files info should get loaded when using (Saved )Template for Production.");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.fillingMP3FileWithBurnRedaction();
		page.navigateToNextSection();

		ProductionPage Page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		driver.Navigate().refresh();
		Page.savedTemplateAndNewProdcution(productionname, Templatename);
		Page.baseInfoLoadTemplate(productionname1, Templatename);

		// Method to verify the retained value in Mp3 File
		page.verifyingMP3FileBurnRedaction(Input.defaultRedactionTag);
		page.getCheckBoxCheckedVerification(page.getMP3FileSelectRedactionTags());
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48006
	 * @Description To Verify Produced PDFs are being presented in the DocView for
	 *              the document
	 * 
	 */
	@Test(groups = { "regression" }, priority = 125)
	public void PDFDocumentDisplayedInDocViewPage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48006 -Production Component");
		base.stepInfo("To Verify Produced PDFs are being presented in the DocView for the document");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		base.stepInfo("Navigate to docview");
		sessionsearch.ViewInDocView();

		driver.waitForPageToBeReady();
		DocViewPage doc = new DocViewPage(driver);
		doc.clickOnImageTab();
		driver.waitForPageToBeReady();
		doc.verifyProductionNameForPDFFileInDocView(productionname);
		driver.waitForPageToBeReady();
		doc.getSelectedAreaElement().isElementAvailable(10);
		if (doc.getSelectedAreaElement().isDisplayed()) {
			base.passedStep("Produced PDFs  is presented in the DocView for the document");
		} else {
			base.failedStep("Produced PDFs is not presented in the DocView for the document");
		}
		loginPage.logout();
	}

	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
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
