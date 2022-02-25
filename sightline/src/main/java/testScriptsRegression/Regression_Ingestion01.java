package testScriptsRegression;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.IngestionPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Regression_Ingestion01 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	Input ip;
	SoftAssert softAssertion;
	
	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip= new Input();
		ip.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	
	
	
	/**
	 * Author : Mohan date: 17/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify 'Source System' is disabled if user select Ingestion-Overlay on Ingestion Wizard page'RPMXCON-58508' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifySourceSystemIsDisabled() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-58508");
		baseClass.stepInfo("Verify 'Source System' is disabled if user select Ingestion-Overlay on Ingestion Wizard page");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Step 2: Create an new Ingestion");
		ingestionPage.IngestionRegression(Input.AllSourcesFolder);
		baseClass.stepInfo("Step 3&4 : Go to Ingestion Add New Ingestion and Ingestion Type as 'Overlay Only' and Ingestion Type as 'Overlay Only'");
		ingestionPage.verifySourceSystemDisabledWhenOverylayIsAddedAsIngestionType();
		
		}
	
	
	/**
	 * Author : Mohan date:24/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify if Ingestion is saved as draft then on Ingestion page, it should retain the selected Date & Time format'RPMXCON-49549' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyDateFormateForIngestionWithSameDATFile() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49549");
		baseClass.stepInfo("Verify if Ingestion is saved as draft then on Ingestion page, it should retain the selected Date & Time format");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Login as Project Admin Go to Ingestion and click to Add new ingestion  Prerequisites: Date & time format in DAT file should be 'YYYY/DD/MM' Select Source System  Select Ingestion Type: Add Only  Select 'Date & Time Format as 'YYYY/DD/MM'  and click on Next  Configure field mapping and click 'Preview & Run'");
		ingestionPage.validateDateAndTimeFormateWhenIngestionIsSaveAsDraft(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS");
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.verifyDateFormateInCatalogeAndDraft();
		
	}
	
	
	/**
	 * Author : Mohan date:24/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify if Ingestion is rollbacked and open in the wizard,On Ingestion page, it should display the default Date & Time format'RPMXCON-49548' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyDateFormateForIngestionWithSameDATFileAndRollBack() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49548");
		baseClass.stepInfo("Verify if Ingestion is rollbacked and open in the wizard,On Ingestion page, it should display the default Date & Time format");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Login as Project Admin Go to Ingestion and click to Add new ingestion  Prerequisites: Date & time format in DAT file should be 'YYYY/DD/MM' Select Source System  Select Ingestion Type: Add Only  Select 'Date & Time Format as 'YYYY/DD/MM'  and click on Next  Configure field mapping and click 'Preview & Run'");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS");
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.rollBackIngestion();
		ingestionPage.verifyDateFormateInCatalogeAndDraft();
		
	}
	
	/** 
     *Author :Arunkumar date: 24/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49702
	 * Description :Verify that if document has multipage TIFF then in Copying stage of Ingestion ,it should displays the 'Stitched TIFFs' under the Copy column.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 4)
	public void verifyStitchedTiffTermUnderCopyColumn() throws InterruptedException  {
		ingestionPage = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49702");
		baseClass.stepInfo("Verify that if document has multipage TIFF then in Copying stage of Ingestion ,it should displays the 'Stitched TIFFs' under the Copy column");
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
		ingestionPage.verifyDataPresentInCopyColumn(Input.StitchedTIFF);
		
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
			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");

	}

}
