package sightline.productions;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
	 * @author : Gopinath created on:NA modified by:NA @Testcase_ID : RPMXCON_56134
	 *         - Verify that for the saved template under TIFF/PDF component.
	 * @Description : Verify that for the saved template under TIFF/PDF component
	 *              and Available Fields in Slip Sheets should be disabled.
	 */
	@Test(description="RPMXCON-56134",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase_ID : RPMXCON_56102 -
	 *         Verify the error message for TIFF/PDF component when 'Enable Tech
	 *         issue doc without tag or text'
	 * @Description: Verify the error message for TIFF/PDF component when 'Enable
	 *               Tech issue doc without tag or text'.
	 */
	@Test(description="RPMXCON-56102",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase_ID : RPMXCON_56107 -
	 *         Verify the error message for TIFF/PDF component when 'don't add any
	 *         text in specify redaction text section'.
	 * @Description : Verify the error message for TIFF/PDF component when 'don't
	 *              add any text in specify redaction text section'.
	 */
	@Test(description="RPMXCON-56107",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase_ID : RPMXCON_56106 -
	 *         Verify the error message for TIFF/PDF component when 'Enable
	 *         redaction without selecting redaction tag'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Enable
	 *              redaction without selecting redaction tag'.
	 */
	@Test(description="RPMXCON-56106",enabled=true,groups = { "regression" })
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
	 * @author : Gopinath created on:NA modified by:NA @TestCase ID : RPMXCON_56105
	 *         - Verify the error message for TIFF/PDF component when 'Specify
	 *         branding without tag and with text'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Specify
	 *              branding without tag and with text' .
	 */
	@Test(description="RPMXCON-56105",enabled=true,groups = { "regression" } )
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
	 * @author Gopinath created on:NA modified by:NA @Testcase ID : RPMXCON_56104 -
	 *         Verify the error message for TIFF/PDF component when 'Specify
	 *         branding with tag and without text'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Specify
	 *              branding with tag and without text'.
	 */
	@Test(description="RPMXCON-56104",enabled=true,groups = { "regression" } )
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
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @TestCase Id : RPMXCON_56103 -
	 *         Verify the error message for TIFF/PDF component when 'Specify
	 *         branding without tag and text'.
	 * @Description : Verify the error message for TIFF/PDF component when 'Specify
	 *              branding without tag and text'.
	 */
	@Test(description="RPMXCON-56103",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase ID : RPMXCON_56131 -
	 *         Verify that if Tag is already specified with 'Natively Produced
	 *         Documents' placeholder then that Tag will available again to select
	 *         for branding.
	 * @Description : Verify that if Tag is already specified with 'Natively
	 *              Produced Documents' placeholder then that Tag will available
	 *              again to select for branding.
	 */
	@Test(description="RPMXCON-56131",enabled=true,groups = { "regression" } )
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
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID : RPMXCON_56127 -
	 *         Verify that if Tag is already specified with Placeholder then that
	 *         Tag will available again to select for branding.
	 * @Description : Verify that if Tag is already specified with Placeholder then
	 *              that Tag will available again to select for branding
	 */
	@Test(description="RPMXCON-56127",enabled=true,groups = { "regression" })
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
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName);
		
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID : RPMXCON_56108 -
	 *         Verify the error message for MP3 component when 'Enable burn
	 *         redaction without selecting redaction tag'.
	 * @Description : Verify the error message for MP3 component when 'Enable burn
	 *              redaction without selecting redaction tag'.
	 */
	@Test(description="RPMXCON-56108",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON_48500 :
	 *         To verify that if annotation layer option is selected in Tiff section
	 *         and document is redacted then selected Metadata should not be
	 *         displayed on DAT.
	 * @Description : Verify that if annotation layer option is selected in Tiff
	 *              section and document is redacted then selected Metadata should
	 *              not be displayed on DAT.
	 */
	@Test(description="RPMXCON-48500",enabled=true,groups = { "regression" } )
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Generated production successfully");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON_48491 :
	 *         To verify that if annotation layer option is selected and non audio
	 *         document is redacted then native should not copied.
	 * @Description : Verify that if annotation layer option is selected and non
	 *              audio document is redacted then native should not copied.
	 */
	@Test(description="RPMXCON-48491",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Generated production successfully");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-48312 :
	 *         To verify that redaction text should be printed on burned redaction
	 *         if user selects Tiff.
	 * @Description : Verify that redaction text should be printed on burned
	 *              redaction if user selects Tiff.
	 */
	@Test(description="RPMXCON-48312",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("verified  the reduction text printed on selecting TIFF ");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-48310 :
	 *         To verify that redaction text should be printed on burned redaction
	 *         if user selects only PDF.
	 * @Description : Verify that redaction text should be printed on burned
	 *              redaction if user selects only PDF.
	 */
	@Test(description="RPMXCON-48310",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("verified  the reduction text printed on selecting PDF");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-48600 :
	 *         To verify that user can export only after pre-gen check is completed.
	 * @Description :Verify that user can export only after pre-gen check is
	 *              completed.
	 */
	@Test(description="RPMXCON-48600",enabled=true,groups = { "regression" })
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

		
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as User: " + Input.pa1FullName);

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
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.stepInfo("Click on navigate back");
		page.navigateBack();

		base.stepInfo("Verify export bates button is enabled");
		page.verifyExportBatesButtonIsEnabled();
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername,Input.securityGroup);
		
		
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-49218 :
	 *         To verify that in Advanced Search, search against uncommit Production
	 *         should not display any results.
	 * @Description : Verify that in Advanced Search, search against uncommit
	 *              Production should not display any results.
	 */
	//@Test(description="RPMXCON-49218",enabled=true,groups = { "regression" })
	public void verifyCommitAndUnCommitProductionResults() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49218 Production-sprint:04");
		base.stepInfo(
				"#### Verify that in Advanced Search, search against uncommit Production should not display any results. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		
		loginPage.logout();
		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as User: " + Input.pa1FullName);

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
		page.fillingGeneratePageWithContinueGenerationPopup();

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

		driver.waitForPageToBeReady();
		base.stepInfo("Select row by production name and open in wizard.");
		page.selectRowByProductionNameAndOpenWizard(productionName);

		base.stepInfo("Click on uncommit bates");
		page.uncommitBates();

		page = new ProductionPage(driver);

		base.stepInfo("Perform new search by new work product");
		search.performNewSearchByNewWorkProduct(productionName);

		base.stepInfo("Verify pure hit count with production count after uncommit");
		search.verifyPureHitsCountWithManyProductionCount(0);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername,Input.securityGroup);
		
		
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-49998 :
	 *         Verify if user included only default branding text then branding text
	 *         should display on 'Preview' document and on produced documents also.
	 * @Description : Verify if user included only default branding text then
	 *              branding text should display on 'Preview' document and on
	 *              produced documents also.
	 */
	@Test(description="RPMXCON-49998",enabled=true,groups = { "regression" })
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
		
		
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);
		base.stepInfo("Select default security group");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	
	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-50016 :
	 *         Verify that if 'AllProductionBatesRanges' is non-searchable for
	 *         existing project, user cannot make it as Searchable field.
	 * @Description :Verify that if 'AllProductionBatesRanges' is non-searchable for
	 *              existing project, user cannot make it as Searchable field.
	 */
	@Test(description="RPMXCON-50016",enabled=true,groups = { "regression" })
	public void verifyingTheSearchableFieldInAllProductionBatesRanges() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50016 Production-sprint:04");
		base.stepInfo(
				"#### Verify that if 'AllProductionBatesRanges' is non-searchable for existing project, user cannot make it as Searchable field. ####");

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);
		ProjectFieldsPage projectFieldsPage = new ProjectFieldsPage(driver);

		base.stepInfo("Verifying Is Searchable Bates Range Is Selected");
		projectFieldsPage.verifyingIsSearchableBatesRangeIsSelected();
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON-49242 :
	 *         To verify that Project Admin can modify the populated the bates
	 *         number fields and production should generated with modified bates
	 *         number.
	 * @Description : To verify that Project Admin can modify the populated the
	 *              bates number fields and production should generated with
	 *              modified bates number.
	 */
	@Test(description="RPMXCON-49242",enabled=true,groups = { "regression" })
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
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);
		
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
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

	@Test(description="RPMXCON-48021",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-48022 :
	 *         To Verify In Productions, TIFF/ PDF generation is failing for the
	 *         SAME document while it is working fine sometimes.
	 * @Description : Verify In Productions, TIFF/ PDF generation is failing for the
	 *              SAME document while it is working fine sometimes
	 */

	@Test(description="RPMXCON-48022",enabled=true,groups = { "regression" })
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
		sessionSearch.bulkFolderExisting(foldername);

		for (int i = 0; i < 2; i++) {

			

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
			page.fillingGeneratePageWithContinueGenerationPopup();

		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase ID : RPMXCON-49114 -
	 *         To verify that the value of 'Number of MP3 Files' on
	 *         Production-Summary tab if MP3 Files component is selected.
	 * @Description : verify that the value of 'Number of MP3 Files' on
	 *              Production-Summary tab if MP3 Files component is selected.
	 */

	@Test(description="RPMXCON-49114",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-49227 :
	 *         Verify that in Production, if sorting option is Sort by MetaData and
	 *         'Keep Families Together' check box is selected.
	 * @Description : verify that in Production, if sorting option is Sort by
	 *              MetaData and 'Keep Families Together' check box is selected.
	 */
	@Test(description="RPMXCON-49227",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-55700 :
	 *         Verify file group type (.mdb/.mdf) option on selection in
	 *         Translations section
	 * @Description : Verify file group type (.mdb/.mdf) option on selection in
	 *              Translations section.
	 */

	@Test(description="RPMXCON-55700",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-55679 :
	 *         To Verify On the Summary step of a production, the counts under the
	 *         OCR/TIFF should Display Count.
	 * @Description : Verify On the Summary step of a production, the counts under
	 *              the OCR/TIFF should Display Count.
	 */
	@Test(description="RPMXCON-55679",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		

		for (int i = 0; i < 2; i++) {
			base.stepInfo("Click on navigate back");
			page.navigateBack();
		}

		base.stepInfo("OCR And TIFF Count In Summary And Preview");
		page.OCRAndTIFFCountInSummaryAndPreview();
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-49237 :
	 *         To verify that link should be available on Numbering and Sorting tab
	 * @Description : Verify that link should be available on Numbering and Sorting
	 *              tab
	 */
	@Test(description="RPMXCON-49237",enabled=true,groups = { "regression" })
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
	 * @author : Gopinath created on:NA modified by:NA @Testcase id : RPMXCON_49221
	 *         : To verify that after uncommit if user regenerate the production ,
	 *         it should generate successfully
	 * @Description : Verify that after uncommit if user regenerate the production ,
	 *              it should generate successfully.
	 */
	//@Test(description="RPMXCON-49221",enabled=true,groups = { "regression" } )
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON_50033 :
	 *         To verify that PDF should burn multiple redactions and display the
	 *         correct text for each redaction.
	 * @Description : To verify that PDF should burn multiple redactions and display
	 *              the correct text for each redaction.
	 */
	@Test(description="RPMXCON-50033",enabled=true,groups = { "regression" } )
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA. @Testcase ID : RPMXCON_56007 :
	 *         Verify that user can download the production by using the Shareable
	 *         link for 'All Files'.
	 * @Description :Verify that user can download the production by using the
	 *              Shareable link for 'All Files'.
	 */
	@Test(description="RPMXCON-56007",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Generated production successfully");

		base.stepInfo("Verify download production using sharable link.");
		page.verifyDownloadProductionUsingSharableLink();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-49224 :
	 *         To verify that after uncommit, if user change the source in Document
	 *         Selection tab, it should regenerate and commit Production
	 *         successfully.
	 * @Description : Verify that after uncommit, if user change the source in
	 *              Document Selection tab, it should regenerate and commit
	 *              Production successfully
	 */
	//@Test(description="RPMXCON-49224",enabled=true,groups = { "regression" })
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
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		sessionSearch.basicContentSearch(testData1);

		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.stepInfo("Filling Generate Pageand Uncommit TheProduction ");
		page.fillingGeneratePageandUncommitTheProduction();

		base.stepInfo("Modifying Document Selection And Mark complete");
		page.modifyingDocumentSelectionAndMarkcomplete(tagname);

		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56010 :
	 *         Verify that even after Uncommit the producion user can download the
	 *         Production.
	 * @Description : Verify that even after Uncommit the producion user can
	 *              download the Production.
	 */
	//@Test(description="RPMXCON-56010",enabled=true,groups = { "regression" })
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-56085 :
	 *         Verify that Production should generate successfully by selecting only
	 *         DAT and 'Generate TIFF' option with Priv Placholder.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate TIFF' option with Priv
	 *              Placholder.
	 */
	@Test(description="RPMXCON-56085",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("Production is generated successfully on Selecting Priv docs placeholder");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56084 :
	 *         Verify that Production should generate successfully by selecting only
	 *         DAT and 'Generate PDF' option.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate PDF' option.
	 */
	@Test(description="RPMXCON-56084",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("Production is generated successfully on filling DAT and PDF section");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA TESTCASE @Testcase No :
	 *         RPMXCON-56128 : Verify that if Redaction Tag/s is already specified
	 *         with Redaction Text then that Redaction Tag.
	 * @Description : Verify that if Redaction Tag/s is already specified with
	 *              Redaction Text then that Redaction Tag.
	 */

	@Test(description="RPMXCON-56128",enabled=true,groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56062 :
	 *         Verify Production should generate successfully with page counting if
	 *         user selects PDF and TEXT.
	 * @Description : Verify Production should generate successfully with page
	 *              counting if user selects PDF and TEXT.
	 */

	@Test(description="RPMXCON-56062",enabled=true,groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.stepInfo("Production is generated successfully on filling DAT,PDF and TEXT section");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}
	
	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON-55717
	 *         : To Verify in Productions, for a document with AudioPlayReady, only
	 *         the MP3 file variant is produced as MP3 files.
	 * @Description : To Verify in Productions, for a document with AudioPlayReady,
	 *              only the MP3 file variant is produced as MP3 files.
	 */

	@Test(description="RPMXCON-55717",enabled=true, groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56126
	 *         : Verify that if Tag is already specified with Privileged Placeholder
	 *         then that Tag should not be available.
	 * @Description : Verify that if Tag is already specified with Privileged
	 *              Placeholder then that Tag should not be available.
	 */
	@Test(description="RPMXCON-56126",enabled=true, groups = { "regression" })
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
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56125 :
	 *         Verify that if Tag is already specified with Right Branding then that
	 *         Tag should not be available.
	 * @Description : Verify that if Tag is already specified with Right Branding
	 *              then that Tag should not be available.
	 */
	@Test(description="RPMXCON-56125",enabled=true, groups = { "regression" })
	public void tiffSectionRightBranding() throws Exception {
		base.stepInfo("Test case Id: No:RPMXCON_56125 -Production");
		base.stepInfo(
				"#### Verify that if Tag is already specified with Right Branding then that Tag should not be available. ####");

		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		base.stepInfo("verifying tag selected in the specify branding at right Header branding");
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_id : RPMXCON-55923
	 *         : Verify that in Production, DocFileExtensionCorrected should be used
	 *         in the file name as Native, when DocFileExtension and
	 *         DocFileExtensionCorrected is having different value.
	 * @Description : Verify that in Production, DocFileExtensionCorrected should be
	 *              used in the file name as Native, when DocFileExtension and
	 *              DocFileExtensionCorrected is having different value.
	 */
	@Test(description="RPMXCON-55923",enabled=true, groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-55924
	 *         : Verify that in Production Native file will have no extension, when
	 *         file extension and file extension corrected as blank values.
	 * @Description : Verify that in Production Native file will have no extension,
	 *              when file extension and file extension corrected as blank
	 *              values.
	 */
	@Test(description="RPMXCON-55924",enabled=true, groups = { "regression" })
	public void generatingProductionWithoutExtesionFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-55924 -Production Sprint 05");
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-49138
	 *         : Verify that when text is exported for file group type then it
	 *         should export the actual text file and not the placeholder.
	 * @Description : Verify that when text is exported for file group type then it
	 *              should export the actual text file and not the placeholder.
	 */
	@Test(description="RPMXCON-49138",enabled=true, groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-55775
	 *         : To verify that If user select PrivTag and if translations document
	 *         is associated to that tag then Native should not produced.
	 * @Description : Verify that If user select PrivTag and if translations
	 *              document is associated to that tag then Native should not
	 *              produced
	 */
	@Test(description="RPMXCON-55775",enabled=true, groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
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
	@Test(description="RPMXCON-55941",enabled=true, groups = { "regression" })
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
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewPage docViewPage = new DocViewPage(driver);

		base.stepInfo("Verify Doc View Images");
		docViewPage.verifyDocViewImages();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56088
	 *         : Verify that Production should generate successfully by selecting
	 *         only DAT and 'Generate PDF' option with TechIssue Placholder.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate PDF' option with TechIssue
	 *              Placholder.
	 */
	@Test(description="RPMXCON-56088",enabled=true, groups = { "regression" })
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("production generated successfully on filling DAT and PDF section TechissueDocs");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase Id : RPMXCON_56090
	 *         : Verify that Production should generate successfully by selecting
	 *         only DAT and 'Generate PDF' option with Natively Produced Documents
	 *         Placholder.
	 * @Description : Verify that Production should generate successfully by
	 *              selecting only DAT and 'Generate PDF' option with Natively
	 *              Produced Documents Placholder.
	 */
	@Test(description="RPMXCON-56090",enabled=true, groups = { "regression" })
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

		
		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.stepInfo("production generated successfully on filling DAT and PDF section NativelyProducedDocs");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-49222
	 *         : To verify that after uncommit the production, Action "Production
	 *         Deleted" should be displayed in 'Document Audit Report'.
	 * @Description : Verify that after uncommit the production, Action "Production
	 *              Deleted" should be displayed in 'Document Audit Report'.
	 */
	 //@Test(description="RPMXCON-49222",enabled=true,groups = { "regression" })
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
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);

		base.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk folder existing");
		sessionSearch.bulkFolderExisting(foldername);

		docView = new DocViewPage(driver);
		
		docView.selectViewInDocView();
		
		String firstDocID = docView.getFirstDocumentIdFromMiniDocList();
		
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
		reportPage.selectDocumentAuditReport(firstDocID, Input.produced);

		page = new ProductionPage(driver);

		driver.waitForPageToBeReady();

		base.stepInfo("Select row by production name and open in wizard.");
		page.selectRowByProductionNameAndOpenWizard(productionname);

		base.stepInfo("Click on uncommit bates");
		page.uncommitBates();

		reportPage = new ReportsPage(driver);

		base.stepInfo("Select Document Audit Report");
		reportPage.selectDocumentAuditReport(firstDocID, Input.producedDeleted);
		loginPage.logout();

	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA @TestCase_id : RPMXCON-56082
	 *         : Verify that in Production components, TIFF/PDF section displays
	 *         options for Generating TIFF or Generating PDF
	 * @Description: Verify that in Production components, TIFF/PDF section displays
	 *               options for Generating TIFF or Generating PDF.
	 */
	 @Test(description="RPMXCON-56082",enabled=true,groups = { "regression" })
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
		 * @Testcase_Id:RPMXCON-55986 : Verify that after Post Geneation is completed,
		 *                            it will displays status on Production Generation
		 *                            page as 'Post Generation QC Check Complete'
		 * @Description : Verify that after Post Geneation is completed, it will
		 *              displays status on Production Generation page.
		 */
		@Test(description="RPMXCON-55986",enabled=true,groups = { "regression" })
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

			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

			base.stepInfo("Production status in generate page is verified successfully");
			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-48874 :
		 *         To verify that Production should generate successfully if user map
		 *         the one source field to multiple DAT fields.
		 * @Description : To verify that Production should generate successfully if user
		 *              map the one source field to multiple DAT fields.
		 */
		@Test(description="RPMXCON-48874",enabled=true,groups = { "regression" })
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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

			base.stepInfo("DAT section with different DAT field is generated successfully");
			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-49970 :
		 *         Verify that after updating the placeholder text on uncompleting the
		 *         production, production should generate with the updated redaction
		 *         text.
		 * @Description : Verify that after updating the placeholder text on
		 *              uncompleting the production, production should generate with the
		 *              updated redaction text.
		 */
		@Test(description="RPMXCON-49970",enabled=true,groups = { "regression" })
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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			
			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-48951 :
		 *         To verify that user can load the template in New Production and
		 *         Generate for TIFF file.
		 * @Description : Verify that user can load the template in New Production and
		 *              Generate for TIFF file.
		 */
	@Test(description="RPMXCON-48951",enabled=true,groups = { "regression" })
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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			

			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-48953 :
		 *         To verify that after loading the template PA can change the
		 *         configuration in any of the steps.
		 * @Description : Verify that after loading the template PA can change the
		 *              configuration in any of the steps.
		 */
		@Test(description="RPMXCON-48953",enabled=true,groups = { "regression" })
		public void verifyingPDFSectionInManageTemplate() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("Test case Id: RPMXCON-48953 -Production Sprint 06");
			base.stepInfo(
					"#### Verify that after loading the template PA can change the configuration in any of the steps.. ####");

			loginPage = new LoginPage(driver);
			loginPage.logout();

			base.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			base.stepInfo("Logged in as User: " + Input.pa1FullName);

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
			tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
			tagsAndFolderPage.DeleteTagWithClassification(tagName, "Default Security Group");
			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-48952 :
		 *         To verify that user can load the template in New Production and
		 *         Generate PDF files.
		 * @Description : Verify that user can load the template in New Production and
		 *              Generate PDF files
		 */
	@Test(description="RPMXCON-48952",enabled=true,groups = { "regression" })
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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			

			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-48000 :
		 *         Verify Branding Bates number
		 * @Description: Verify Branding Bates number
		 */
		@Test(description="RPMXCON-48000",enabled=true,groups = { "regression" })
		public void fillingTIFFSectionWithBatesNumberAndGenerateProduction() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("Test case Id: RPMXCON-48000 -Production Sprint 06");
			base.stepInfo("#### Verify Branding Bates number.. ####");
			
			String tagName = Input.randomText + Utility.dynamicNameAppender();
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			String prefixID = "A_" + Utility.dynamicNameAppender();
			String suffixID = "_P" + Utility.dynamicNameAppender();
			
			base.stepInfo("Create tag with classification");
			tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

			SessionSearch sessionSearch = new SessionSearch(driver);

			base.stepInfo("Basic Content Search");
			sessionSearch.basicContentSearch(Input.batesDocumentId);
			sessionSearch.ViewInDocList();
			DocListPage doc = new DocListPage(driver);
			
			doc.documentSelection(4);
			doc.bulkTagExistingFromDoclist(tagName);

			ProductionPage page = new ProductionPage(driver);

			String productionname = Input.randomText + Utility.dynamicNameAppender();
			String beginningBates = page.getRandomNumber(2);
			String beginningBates1 = page.getRandomNumber(2);
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
			page.fillingNumberingAndSortingDocumentPage(beginningBates, prefixID, suffixID);
			page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
			
			base.stepInfo("Navigate To Next Section");
			page.navigateToNextSection();

			base.stepInfo("Filling Document Selection Page");
			page.fillingDocumentSelectionWithTag(tagName);

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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName);

			
			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-49999 :
		 *         Verify if multiple tag-based brandings are specified then first
		 *         matching tab-based branding should be display on 'Preview' document
		 *         and on produced documents also.
		 * @Description : Verify if multiple tag-based brandings are specified then
		 *              first matching tab-based branding should be display.
		 */
		@Test(description="RPMXCON-49999",enabled=true,groups = { "regression" })
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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			
			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-55957 :
		 *         Verify that if 'Volume Included' toggle is OFF then "Archive for FTP"
		 *         should archive everything in the Production Directory.
		 * @Description : Verify that if 'Volume Included' toggle is OFF then "Archive
		 *              for FTP" should archive everything in the Production Directory.
		 */
		@Test(description="RPMXCON-55957",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName);
			
			loginPage.logout();
		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-48496 :
		 *         To verify that the selected metadata is not displayed in DAT.
		 * @Description : Verify that the selected metadata is not displayed in DAT if
		 *              the document has at least one of the selected PRIV tags in PRIV
		 *              placeholdering for PDF.
		 */
		@Test(description="RPMXCON-48496",enabled=true,groups = { "regression" })
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
			page.fillingGeneratePageWithContinueGenerationPopup();
			base.stepInfo("Production is generated successfully on Selecting PDF Priv tag");
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName);
			
			loginPage.logout();
		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-56138 :
		 *         Verify that Count displays correctly on Priv Guard if few of the
		 *         parents and few child of other parents marked as Priv document.
		 * @Description : Verify that Count displays correctly on Priv Guard if few of
		 *              the parents and few child of other parents marked as Priv
		 *              document.
		 */
		@Test(description="RPMXCON-56138",enabled=true,groups = { "regression" })
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
			sessionSearch.basicContentSearch(Input.searchString2);

			base.stepInfo("View In Doc List");
			sessionSearch.ViewInDocList();

			DocListPage docPage = new DocListPage(driver);

			base.stepInfo("Selecting Parent Document");
			docPage.selectingParentDocument();

			
			base.stepInfo("Bulk Tag Existing");
			docPage.bulkTagExistingFromDoclist(tagName1);

			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			
			base.stepInfo("Selecting Child Document");
			docPage.selectingChildDocument();

			base.stepInfo("Bulk Tag Existing");
			docPage.bulkTagExistingFromDoclist(tagName2);

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
			page.fillingGeneratePageWithContinueGenerationPopup();
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName2);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagName1);

			loginPage.logout();

		}

		/**
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-56074 :
		 *         Verify the static text in Production-Text component section.
		 * @Description : Verify the static text in Production-Text component section.
		 */
		@Test(description="RPMXCON-56074",enabled=true,groups = { "regression" })
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
		 * @Testcase_Id:RPMXCON-56068 :Verify if TIFF selecting for Priv Placeholdering
		 *                            file then PageCount is always 1 and it will skip
		 *                            the 'DOCPGCOUNTUPDT'
		 * @Description : Verify that after selecting tiffpagecount in DAT section, it
		 *              will generate the production.
		 */
		@Test(description="RPMXCON-56068",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-56069 :Verify if TIFF selecting for Tech Placeholdering
		 *                            file then PageCount is always 1 and it will skip
		 *                            the 'DOCPGCOUNTUPDT'
		 * @Description : Verify that after selecting tiffpagecount in DAT section, it
		 *              will generate the production.
		 */
		@Test(description="RPMXCON-56069",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-56067 :Verify page counting is not skipped if Production
		 *                            exports only Natives,text(not ingested)
		 * @Description :generate the production on filling native and text section.
		 */
		@Test(description="RPMXCON-56067",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-56065 :Verify page counting is skipped if Production
		 *                            exports only Text (If text ingested)
		 * @Description :generate the production on filling text section.
		 */
		@Test(description="RPMXCON-56065",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-56059 :Verify page counting is skipped if Production
		 *                            exports only Natives,text (Text is ingested) with
		 *                            DAT
		 * @Description :generate the production on filling text section.
		 */
		@Test(description="RPMXCON-56059",enabled=true,groups = { "regression" })
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
		 * @author Gopinath created on:NA modified by:NA @Testcase_Id : RPMXCON-55942
		 *         :Verify that Production should generated successfully and PDF/TIFF
		 *         should produced with Comments/Signautre (Documents processed through
		 *         ICE)
		 * @Description : Verify that after loading the template PA can change the
		 *              configuration in any of the steps.
		 */
		@Test(description="RPMXCON-55942",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
			loginPage.logout();
			
			
		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-56057 : Verify page counting is skipped if Production
		 *                            exports only Natives.
		 * @Description : Verify page counting is skipped if Production exports only
		 *              Natives.
		 */
		@Test(description="RPMXCON-56057",enabled=true,groups = { "regression" })
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
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			Reporter.log("Logged in as User: " + Input.pa1userName);

			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

			base.stepInfo("Navigate To Tags And Folder Page");
			tagsAndFolderPage.navigateToTagsAndFolderPage();

			base.stepInfo("Create folder");
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername,Input.securityGroup);
			tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.tagNamePrev);
			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-56053 : Verify that once Post Geneation is in progress,
		 *                            it will displays status on ProductionGrid View as
		 *                            'Post-Gen QC Checks In Progress'.
		 * @Description : Verify that once Post Geneation is in progress, it will
		 *              displays status on ProductionGrid View as 'Post-Gen QC Checks In
		 *              Progress'.
		 */
		@Test(description="RPMXCON-56053",enabled=true,groups = { "regression" })
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
			page.getbtnProductionGenerate().isElementAvailable(5);
			page.getbtnProductionGenerate().Click();
			page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
			if (page.getbtnContinueGeneration().isDisplayed()) {
				base.waitForElement(page.getbtnContinueGeneration());
				page.getbtnContinueGeneration().waitAndClick(10);
			}

			// Go To Production Home Page
			base.stepInfo("Navigate To Production Page");
			page.navigateToProductionPage();

			base.stepInfo("Refresh page");
			driver.Navigate().refresh();

			base.stepInfo("Verify Production Status In HomePage");
			page.verifyProductionStatusInHomePage(Input.postGenQcChecks, productionname);
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			loginPage.logout();
	}


		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-55974 : Verify that after Pre-gen checks is in progress,
		 *                            it will displays status on Production Generation
		 *                            page
		 * @Description : Verify that after Pre-gen checks is in progress, it will
		 *              displays status on Production Generation page.
		 */
		@Test(description="RPMXCON-55974",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			loginPage.logout();
		}

		

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-49814 :To verify that Production should generate
		 *                            successfully if Suffix is up to 50 characters
		 * @Description :verify the generation of production when suffix with 50
		 *              character.
		 */
		@Test(description="RPMXCON-49814",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			
			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-49813 :To verify that Production should generate
		 *                            successfully if prefix and Suffix is less than 50
		 *                            characters
		 * @Description :verify the generation of production when prefix and suffix is
		 *              less than 50 character.
		 */
		@Test(description="RPMXCON-49813",enabled=true,groups = { "regression" })
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA
		 * @Testcase_Id:RPMXCON-49812 :To verify that Production using template should
		 *                            generate with Prefix and Suffix having 50
		 *                            characters
		 * @Description :verify the generation of production when prefix and suffix 50
		 *              character with template.
		 */
		@Test(description="RPMXCON-49812",enabled=true,groups = { "regression" })
		public void generationOfProductionWithPrefixAndSuffixWithTemplate() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("Test case Id: RPMXCON-49812 -Production Sprint 07");
			base.stepInfo("#### verify the production generation  on filling prefix and suffix with template. ####");

			String foldername = Input.randomText + Utility.dynamicNameAppender();
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

			

			base.stepInfo("refreshing the page");
			driver.Navigate().refresh();

			
			ProductionPage Page = new ProductionPage(driver);

			String Templatename = Input.randomText + Utility.dynamicNameAppender();

			driver.waitForPageToBeReady();
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			
			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-55336
		 * @Description: To verify the DAT check box is selectable and show/hide works.
		 */
		@Test(description="RPMXCON-55336",enabled=true,groups = { "regression" })
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
		@Test(description="RPMXCON-56021",enabled=true,groups = { "regression" })
		public void completedStatusVerificationOnTileView() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-56021 -Production Sprint 08");

			String testData1 = Input.testData1;
			String foldername = Input.randomText + Utility.dynamicNameAppender();

			String productionname = Input.randomText + Utility.dynamicNameAppender();
			String prefixID = "A_" + Utility.dynamicNameAppender();
			String suffixID = "_P" + Utility.dynamicNameAppender();
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
			driver.waitForPageToBeReady();
			page.verifyingProductionStatusInHomePage("Completed", productionname);
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			
			loginPage.logout();

		}

		/**
		 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48650
		 * @Description: To verify that if "Do not produce full content TIFF / PDFs or
		 *               placeholder TIFF / PDFs for Natively Produced Docs" is disabled
		 *               , then PDFs is generated with Placeholder
		 */
		@Test(description="RPMXCON-48650",enabled=true,groups = { "regression" })
		public void verifyThatNativelyProducedDocsWithoutPlaceholder() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-48650 -Production Sprint 08");

			String testData1 = Input.testData1;
			String foldername = Input.randomText + Utility.dynamicNameAppender();
			String tagname = Input.randomText + Utility.dynamicNameAppender();
			String productionname = Input.randomText + Utility.dynamicNameAppender();
			String tagNamePrev = Input.tagNamePrev;
			String prefixID = "A_" + Utility.dynamicNameAppender();
			String suffixID = "_P" + Utility.dynamicNameAppender();
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
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
			loginPage.logout();
		}

		/**
		 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48643
		 * @Description:To verify that on PDF section, 'Do Not Produce PDFs for Natively
		 *                 Produced Docs' option is disabled by default
		 */
		@Test(description="RPMXCON-48643",enabled=true,groups = { "regression" })
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

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
//			loginPage.logoutWithoutAssert();
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
