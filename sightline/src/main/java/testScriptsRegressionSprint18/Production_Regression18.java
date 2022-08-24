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
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression18 {
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
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-47939 
	 * Description :To Verify in the tree structure of tags, disable and grey out the tags that are not of type Privileged.
	 * 
	 */
	@Test(description = "RPMXCON-47939", enabled = true, groups = { "regression" })
	public void verifyPrivilegedSelectedAndOtherTagDisabled() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47939 -Production Component");
		base.stepInfo("To Verify in the tree structure of tags, disable and grey out the tags that are not of type Privileged.");

		String Tagname = "Tag" + Utility.dynamicNameAppender();
		String Tagname1 = "Tag" + Utility.dynamicNameAppender();
		String Tagname2 = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(Tagname1,"Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(Tagname2,"Select Tag Classification");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyPrivTagSelectionAndDisableOfOtherTag(Tagname,Tagname1,Tagname2);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname1, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(Tagname2, Input.securityGroup);
		loginPage.logout();
	}

	
	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-47891
	 *  Description :To Verify Manage Template Tab
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
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		page.verifyingComponentTabSection();
		

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
		
	}
	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49240
	 *  Description :To verify that Project Admin can enter the information for the bates number fields.
	 * 
	 */
	@Test(description = "RPMXCON-49240", enabled = true, groups = { "regression" })
	public void verifyingAdminCanEnterBatesNumber() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base=new BaseClass(driver);
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
		String BatesNumber =page. gettxtBeginningBatesIDPrefix().GetAttribute("value");
		String BatesNumbers =page. gettxtBeginningBatesIDSuffix().GetAttribute("value");
		String[] BatesNumberInSortingTab = { BatesNumber,BatesNumbers};
		
		for (int i = 0; i < BatesNumberInSortingTab.length; i++) {
			System.out.println(BatesNumberInSortingTab[i]);
			if(BatesNumberInSortingTab[i] !=null) {
				base.passedStep("Project admin can enter the bates number field");
			}else {
				base.failedStep("Project admin cannot enter the bates number field");
			}
		}
		loginPage.logout();
		
			}
	
	
	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49249
	 *  Description :To verify that if user click on 'X' icon then Next Bates Number should not be populated into the bates number fields
	 * 
	 */
	@Test(description = "RPMXCON-49249", enabled = true, groups = { "regression" })
	public void validatingNextBatesNumberNotPopulatedInSortingTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49249 -Production Component");
		base.stepInfo("To verify that if user click on 'X' icon then Next Bates Number should not be populated into the bates number fields");
		
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
		base.ValidateElement_Presence(page.getNextBatesNumber(),"Next Bates Number");
        page.getCloseIconInManageTemplate().waitAndClick(10);
        String actualText = page.getBeginningBates().GetAttribute("value");
		System.out.println("The actual txt" + actualText);
		base.digitCompareEquals(Integer.valueOf(actualText), 0, "Bates Number are not auto populated as expected","Bates Number are auto populated");
        
		// delete folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/26/2022 TestCase Id :RPMXCON-49964
	 *  Description :Verify that if Redaction placeholder text is blank then error should be displayed
	 * 
	 */
	@Test(description = "RPMXCON-49964", enabled = true, groups = { "regression" })
	public void verifyingBurnRedactionPlaceholderErrorMsg() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base=new BaseClass(driver);
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
		base.VerifyErrorMessage("You must specify the redaction text that you want to see in TIFF/PDF burned redactions.");
		
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
	 *   Verify that RED text should be displayed in Abbreviated Text box in TIFF/PDF
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
		String BurnRedactionText=page.getBurnRedactionText().GetAttribute("value");
		base.textCompareEquals(BurnRedactionText, Input.stampRed,"BurnRedaction abbreviated text is displayed ","Text is not displayed");
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-47869 Description
	 *  To Verify in production, the DAT check box selection is mandatory to Save the Production Component Section
	 * 
	 */
	@Test(description = "RPMXCON-47869", enabled = true, groups = { "regression" })
	public void verifyingDATSectionIsMandatoryForProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47869 -Production Component");
		base.stepInfo("To Verify in production, the DAT check box selection is mandatory to Save the Production Component Section");


		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(),"Production Page");
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
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-47874 Description
	 *  To Verify Redaction section of TIFF and PDF components, Insert Metadata field options availability.
	 * 
	 */
	@Test(description = "RPMXCON-47874", enabled = true, groups = { "regression" })
	public void verifyingInsertMetaDataInRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47874 -Production Component");
		base.stepInfo("To Verify Redaction section of TIFF and PDF components, Insert Metadata field options availability.");


		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		page. gettextRedactionPlaceHolder().Clear();
		page.getBurnRedaction_InsertMetaData().waitAndClick(3);
		page.getNativeMetaDataFieldDropdown().selectFromDropdown().selectByVisibleText(Input.batesNumber);
		page.getPopUpOkButtonInserMetaData().waitAndClick(3);
		String PaceholderText=page. gettextRedactionPlaceHolder().getText();
		base.compareTextViaContains(PaceholderText,Input.batesNumber,"Placeholder text is with Inserted meta data", "Placeholder is not updated with metadata");
		loginPage.logout();

	}
	
	
	/**
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-48862 Description
	 * To verify that user can add "space" in DAT fields
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
		base.ValidateElement_Presence(page.getAddNewProductionbutton(),"Production Page");
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
	 * @author Brundha.T Date:7/29/2022 TestCase Id :RPMXCON-48863 Description
	 * To verify that user can add "underscore" in DAT fields
	 * 
	 */
	@Test(description = "RPMXCON-48863", enabled = true, groups = { "regression" })
	public void verifyingDatFieldInComponentTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass baseClass = new BaseClass(driver);
		base.stepInfo("RPMXCON-48863 -Production Component");
		base.stepInfo("To verify that user can add 'underscore' in DAT fields");
		String BatesNumber = "A_Author" + Utility.dynamicNameAppender();
		

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		base.ValidateElement_Presence(page.getAddNewProductionbutton(),"Production Page");
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




