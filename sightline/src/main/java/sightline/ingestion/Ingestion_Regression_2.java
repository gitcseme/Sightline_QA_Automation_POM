package sightline.ingestion;

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
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_2 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 05/07/2022 TestCase Id:RPMXCON-49338 
	 * Description :Verify value of metadata field "DocPrimaryLanguage" should be derived from CA for Add Only Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49338",enabled = true, groups = { "regression" })
	public void verifyValueOfDocPrimaryLanguageMetadata() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49338");
		baseClass.stepInfo("Verify value of metadata field 'DocPrimaryLanguage' should be derived from CA for Add Only Ingestion");
		dataSets = new DataSets(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion and navigate to doclist");
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			String ingestionName= ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		dataSets.selectDataSetWithName(Input.HiddenPropertiesFolder);
		baseClass.stepInfo("Verify the value of metadata");
		ingestionPage.addMetadatAndVerifyValue(Input.docPrimaryLanguage, Input.english);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/07/2022 TestCase Id:RPMXCON-49265 
	 * Description :To verify that option "ICE" is available in the Source System dropdown in Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49265",enabled = true, groups = { "regression" })
	public void verifyIceOptionInSourceSystemDropdown() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49265");
		baseClass.stepInfo("To verify that option 'ICE' is available in the Source System dropdown in Ingestion");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("verify option 'ICE' available under source system");
		ingestionPage.verifyOptionAvailableInSourceSystem();
		loginPage.logout();
	
	}
	
	/**
	 * Author :Arunkumar date: 08/07/2022 TestCase Id:RPMXCON-47376 
	 * Description :To verify that Delete button is available on Tile.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47376",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionForSameFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47376");
		baseClass.stepInfo("verify that Delete button is available on Tile.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add new ingestion and save as draft");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		baseClass.stepInfo("Verify delete option available in settings option");
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
		ingestionPage.getIngestionSettingGearIcon().waitAndClick(5);
		baseClass.stepInfo("Verify ingestion detail popup display and action dropdown options");
		ingestionPage.verifyIngestionDetailPopupAndActionDropDown();
		baseClass.stepInfo("Delete ingestion");
		ingestionPage.deleteIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/07/2022 TestCase Id:RPMXCON-47399 
	 * Description :To verify Back button functionality for ingestion wizard
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47399",enabled = true, groups = { "regression" })
	public void verifyBackButtonFunctionality() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47399");
		baseClass.stepInfo("verify Back button functionality for ingestion wizard");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion and Enter mandatory field values");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		baseClass.stepInfo("verify source and mapping section status after clicking Next button");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify source and mapping section status after clicking back button");
		ingestionPage.verifySourceAndMappingSectionStatusAfterClickingBackButton();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/07/2022 TestCase Id:RPMXCON-47398
	 * Description :To verify the mandatory field validations in Ingestion Wizard.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47398",enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldInWizard() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47398");
		baseClass.stepInfo("verify the mandatory field validations in Ingestion Wizard");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add ingestion and verify mandatory field warning message");
		ingestionPage.validateIngestionWizardMandatoryFieldWarningMessage();
		baseClass.stepInfo("Verify source and mapping section status after filling mandatory field");
		ingestionPage.navigateToIngestionPage();
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify mandatory field error in mapping section");
		ingestionPage.validateMappingSectionMandatoryFieldWarningMessage();
		baseClass.stepInfo("Map mandatory field and verify preview record popup displayed");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.documentKey,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.passedStep("Verified the mandatory field validations in Ingestion Wizard");
		loginPage.logout();
		
	}
	

	/**
	 * Author :Arunkumar date: 11/07/2022 TestCase Id:RPMXCON-47397
	 * Description :To verify the Source Selection in Ingestion Wizard Page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47397",enabled = true, groups = { "regression" })
	public void verifySourceSelectionInWizard() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47397");
		baseClass.stepInfo("verify the Source Selection in Ingestion Wizard Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Check the file types availability under source selection");
		ingestionPage.verifyDifferentFileTypesAvailability();
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType,Input.sourceSystem, Input.sourceLocation, Input.UniCodeFilesFolder);
		baseClass.stepInfo("verify dat and remainingfile field options and their availability");
		ingestionPage.verifyDatAndRemainingFileFieldOptionsAvailability(Input.datLoadFile1);
		baseClass.stepInfo("verify Other file field availability and link type dropdown values");
		ingestionPage.verifyOtherFileFieldOptionAndDropdownValueAvailability();
		loginPage.logout();
		
	}
	

	/**
	 * Author :Arunkumar date: 13/07/2022 TestCase Id:RPMXCON-47584 
	 * Description :To verify that once Configure Mapping is done Admin is able to go on Preview Mapping section.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47584",enabled = true, groups = { "regression" })
	public void verifyConfigureMappingSection() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47584");
		baseClass.stepInfo("verify that once Configure Mapping is done Admin is able to go on Preview Mapping section.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion and click on next button");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		baseClass.stepInfo("verify source and mapping section status after clicking Next button");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("Configure mapping and run ingestion");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.documentKey,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("verify tile in ingestion homepage");
		ingestionPage.verifyDetailsAfterStartedIngestion();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 13/07/2022 TestCase Id:RPMXCON-47582
	 * Description :To verify mandatory field validation for mapping fields before Preview&Run.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47582",enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldValidationInMappingSection() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47582");
		baseClass.stepInfo("verify mandatory field validation for mapping fields before Preview&Run.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add ingestion and click on next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify mandatory field error in mapping section");
		ingestionPage.validateMappingSectionMandatoryFieldWarningMessage();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 13/07/2022 TestCase Id:RPMXCON-48146
	 * Description :To Verify selected audio and transcript file types are retained on opening of Draft Ingestion in Wizard.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48146",enabled = true, groups = { "regression" })
	public void verifyAudioAndTranscriptFilesRetainedStatus() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48146");
		baseClass.stepInfo("Verify selected audio and transcript file types are retained on opening of Draft Ingestion in Wizard.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion with dat,mp3 and transcript");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType,Input.sourceSystem, 
				Input.sourceLocation, Input.AK_NativeFolder);
		baseClass.waitForElement(ingestionPage.getDATDelimitersNewLine());
		ingestionPage.getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		baseClass.waitForElement(ingestionPage.getSourceSelectionDATLoadFile());
		ingestionPage.selectDATSource(Input.DATFile1, Input.prodBeg);
		ingestionPage.selectMP3VarientSource(Input.MP3File, false);
		ingestionPage.selectAudioTranscriptSource(Input.TranscriptFile, false);
		baseClass.waitForElement(ingestionPage.getDateFormat());
		ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
		baseClass.stepInfo("Save ingestion as draft");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		baseClass.stepInfo("select ingestion from draft and open in wizard");
		driver.waitForPageToBeReady();
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionDropdownArrow());
		ingestionPage.getActionDropdownArrow().waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionOpenWizard());
		ingestionPage.getActionOpenWizard().waitAndClick(5);
		baseClass.stepInfo("verify retained value of audio and transcript file");
		driver.waitForPageToBeReady();
		ingestionPage.validateDetailsAfterOpeningIngestionFromDraft(Input.ingestionType, Input.AK_NativeFolder);
		String retainedMp3 =ingestionPage.getMP3LST().selectFromDropdown().getFirstSelectedOption().getText();
		String retainedTranscript = ingestionPage.getAudioTranscriptLST().selectFromDropdown().getFirstSelectedOption().getText();
		if(retainedMp3.equalsIgnoreCase(Input.MP3File) && retainedTranscript.equalsIgnoreCase(Input.TranscriptFile)) {
			baseClass.passedStep("Both audio and transcript files retained after opening ingestion in wizard from draft");
		}
		else {
			baseClass.failedStep("Both audio and transcript files not retained after opening ingestion in wizard from draft");
		}	
		loginPage.logout();
	}
	
	

	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}
}
