package testScriptsRegressionSprint19;

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

public class Ingestion_Regression_4 {

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
	 * Author :Arunkumar date: 01/08/2022 TestCase Id:RPMXCON-49661
	 * Description :Validate Add Only ingestion with DAT file in ASCII format
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49661",enabled = true, groups = { "regression" })
	public void verifyAddOnlyIngestionInASCII() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49661");
		baseClass.stepInfo("Validate Add Only ingestion with DAT file in ASCII format.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			baseClass.stepInfo("Perform add only ingestion with dat file in ASCII format");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
					Input.sourceLocation, Input.AllSourcesFolder);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.datFileASCII, Input.prodBeg);
			baseClass.waitForElement(ingestionPage.getDateFormat());
			ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.configureMandatoryMappingAndRunIngestion(Input.prodBeg,Input.prodBeg,Input.custodian);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
			baseClass.passedStep("Add only ingestion published successfully with dat file in ASCII format");
		}
		else {
			baseClass.failedMessage("Add only ingestion already present in this project");
		}
		
	}
	
	/**
	 * Author :Arunkumar date: 01/08/2022 TestCase Id:RPMXCON-47291
	 * Description :Single/Multiple DAT file is Available.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47291",enabled = true, groups = { "regression" })
	public void verifyValuesInIngestedDataset() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47291");
		baseClass.stepInfo("Single/Multiple DAT file is Available.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			baseClass.stepInfo("Add new ingestion with dat file");
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.ingestionCatalogging();
			baseClass.passedStep("Ingestion executed successfully");
		}
	}
	
	/**
	 * Author :Arunkumar date: 02/08/2022 TestCase Id:RPMXCON-47580
	 * Description :To Verify: No of Headers in DAT and No. of headers in Destination field in Configure mapping page. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47580",enabled = true, groups = { "regression" })
	public void verifyNumberOfHeaderInDatAndDestinationField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47580");
		baseClass.stepInfo("Verify: No of Headers in DAT and No. of headers in Destination field in Configure mapping page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion details and click on Next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton(); 
		baseClass.stepInfo("verify number of headers in dat and destination field");  
		int numberOfHeaders = ingestionPage.configureMappingRows().size();
		if(ingestionPage.getMappingDestinationField(numberOfHeaders).isElementAvailable(10)) {
			baseClass.passedStep("Number of headers in dat file matches the destination field in configure mapping page");
		}
		else {
			baseClass.failedStep("Number of headers in dat file not matched with destination field");
		}
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 03/08/2022 TestCase Id:RPMXCON-47302
	 * Description :Verify the Preview of Ingestion display for first 50 records with valid inputs. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47302",enabled = true, groups = { "regression" })
	public void verifyIngestionRecordPreviewDetails() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47302");
		baseClass.stepInfo("Verify the Preview of Ingestion display for first 50 records with valid inputs.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion details and click on Next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton(); 
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		baseClass.stepInfo("verify 50 record preview details in popup");
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		ingestionPage.validateRecordsCountInIngestionPreviewScreen();
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
