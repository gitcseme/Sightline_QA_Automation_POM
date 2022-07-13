package testScriptsRegressionSprint16;

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
		ip = new Input();
		ip.loadEnvConfig();

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
	@Test(description ="RPMXCON- 47376",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON- 47399",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON- 47398",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON- 47397",enabled = true, groups = { "regression" })
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
