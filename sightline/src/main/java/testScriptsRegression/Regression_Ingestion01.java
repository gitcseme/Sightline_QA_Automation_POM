package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.Dimension;
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
	@Test(enabled = false, groups = { "regression" }, priority = 1)
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
	@Test(enabled = false, groups = { "regression" }, priority = 2)
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
	@Test(enabled = false, groups = { "regression" }, priority = 3)
	public void verifyDateFormateForIngestionWithSameDATFileAndRollBack() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49548");
		baseClass.stepInfo("Verify if Ingestion is rollbacked and open in the wizard,On Ingestion page, it should display the default Date & Time format");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Login as Project Admin Go to Ingestion and click to Add new ingestion  Prerequisites: Date & time format in DAT file should be 'YYYY/DD/MM' Select Source System  Select Ingestion Type: Add Only  Select 'Date & Time Format as 'YYYY/DD/MM'  and click on Next  Configure field mapping and click 'Preview & Run'");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS",Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst );
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.rollBackIngestion();
		ingestionPage.verifyDateFormateInCatalogeAndDraft();
		
	}
	
	/** 
     *Author :Arunkumar date: 24/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49702
	 * Description :Verify that if document has multipage TIFF then in Copying stage of Ingestion ,it should displays the 'Stitched TIFFs' under the Copy column.
	 */
	@Test(enabled = false,  groups = {"regression" },priority = 4)
	public void verifyStitchedTiffTermUnderCopyColumn() throws InterruptedException  {
		ingestionPage = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49702");
		baseClass.stepInfo("Verify that if document has multipage TIFF then in Copying stage of Ingestion ,it should displays the 'Stitched TIFFs' under the Copy column");
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
		ingestionPage.verifyDataPresentInCopyColumn(Input.StitchedTIFF);
		
		}
	
	
	/**
	 * Author : Mohan date:25/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD HH:MI:SS')'RPMXCON-49537' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyCatalogingErrorSelectedDateFormateIsDifferentThanInDAT() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49537");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD HH:MI:SS')");
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogigErrorForDatSelectDateFormate();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/**
	 * Author : Mohan date:25/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'YYYY/DD/MM' for ingestion which is same as in the DAT file'RPMXCON-49518' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyDateFormateYYYYDDMMWithSlashInIngestionSameAsInDATFile() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49518");
		baseClass.stepInfo("Verify when user selects date & time format 'YYYY/DD/MM' for ingestion which is same as in the DAT file");
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/DD/MM",Input.DAT_YYYYDDMM_Slash,Input.Natives_YYYYDDMM_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'MMDDYYYY' for ingestion which is same as in the DAT file'RPMXCON-49515' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyDateFormateMMDDYYYYInIngestionSameAsInDATFile() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49515");
		baseClass.stepInfo("Verify when user selects date & time format 'MMDDYYYY' for ingestion which is same as in the DAT file");
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MMDDYYYY",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'DD/MM/YYYY' for ingestion which is same as in the DAT file'RPMXCON-49514' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyDateFormateDDMMYYYYWithSlashInIngestionSameAsInDATFile() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49514");
		baseClass.stepInfo("Verify when user selects date & time format 'DD/MM/YYYY' for ingestion which is same as in the DAT file");
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DD/MM/YYYY",Input.DAT_DDMMYYYY_Slash,Input.Natives_DDMMYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'MM/DD/YYYY' for ingestion which is same as in the DAT file'RPMXCON-49513' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyDateFormateMMDDYYYYWithSlashInIngestionSameAsInDATFile() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49513");
		baseClass.stepInfo("Verify when user selects date & time format 'MM/DD/YYYY' for ingestion which is same as in the DAT file");
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'YYYY/MM/DD' for ingestion which is same as in the DAT file'RPMXCON-49517' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyDateFormateYYYYMMDDWithSlashInIngestionSameAsInDATFile() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49517");
		baseClass.stepInfo("Verify when user selects date & time format 'YYYY/MM/DD' for ingestion which is same as in the DAT file");
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_YYYYMMDD_Slash,Input.Natives_YYYYMMDD_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/**
	 * Author : Arunkumar date:16/03/22 NA Modified date: NA Modified by:NA Test Case Id:RPMXCON-49506
	 * Description :Verify the 'Next' button enable/disable from 'Ingestion Wizard' screen when 'Date & Time Format' selected/not-selected
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyNextButtonFromIngestionWizard() throws Exception{
		
		baseClass.stepInfo("Test case Id: RPMXCON-49506");
		baseClass.stepInfo("Verify the 'Next' button enable/disable from 'Ingestion Wizard' screen when 'Date & Time Format' selected/not-selected");
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.VerifyNextButtonStatusBasedOnDateTimeFormatSelection();
		
	}
	

    /** 
     *Author :Arunkumar date: 16/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49733
	 * Description :Verify 'Missed Docs' count is display for 'Stitching TIFF' in Ingestion detail pop up
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 12)
	public void verifyStitchedTiffCountUnderCopyColumn() throws InterruptedException   {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49733");
		baseClass.stepInfo("Verify 'Missed Docs' count is display for 'Stitching TIFF' in Ingestion detail pop up");
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.IngestionCatlogtoCopying(Input.TiffImagesFolder);
		ingestionPage.verifyMissedDocValuePresentInCopyTableColumn(Input.StitchedTIFF);
		//rollback
		ingestionPage.rollBackIngestion();
	
		}
	
	/** 
     *Author :Arunkumar date: 17/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49522
	 * Description :Verify when user selects date & time format 'MM/DD/YYYY HH:MI' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 13)
	public void verifyDateFormatMMDDYYYYHHMIInIngestionSameAsInDATFile() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49522");
		baseClass.stepInfo("Verify when user selects date & time format 'MM/DD/YYYY HH:MI' for ingestion which is same as in the DAT file");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY HH:MI",Input.DAT_MMDDYYYY_HHMI,Input.Natives_MMDDYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	 /** 
     *Author :Arunkumar date: 17/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49523
	 * Description :Verify when user selects date & time format 'DD/MM/YYYY HH:MI' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 14)
	public void verifyDateFormatDDMMYYYYHHMIInIngestionSameAsInDATFile() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49523");
		baseClass.stepInfo("Verify when user selects date & time format 'DD/MM/YYYY HH:MI' for ingestion which is same as in the DAT file");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DD/MM/YYYY HH:MI",Input.DAT_DDMMYYYY_HHMI,Input.Natives_DDMMYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 17/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49525
	 * Description :Verify when user selects date & time format 'DD/MM/YYYY HH:MI:SS' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 15)
	public void verifyDateFormatDDMMYYYYHHMISSInIngestionSameAsInDATFile() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49525");
		baseClass.stepInfo("Verify when user selects date & time format 'DD/MM/YYYY HH:MI:SS' for ingestion which is same as in the DAT file");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DD/MM/YYYY HH:MI:SS",Input.DAT_DDMMYYYY_HHMISS,Input.Natives_DDMMYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49527
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 16)
	public void verifyCatalogingErrorDisplayforMMDDYYYYWithSlashFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49527");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	

	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49528
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 17)
	public void verifyCatalogingErrorDisplayforDDMMYYYYWithSlashFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49528");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_DDMMYYYY_Slash,Input.Natives_DDMMYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49529
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MMDDYYYY')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 18)
	public void verifyCatalogingErrorDisplayforMMDDYYYYFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49529");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MMDDYYYY')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49530
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DDMMYYYY')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 19)
	public void verifyCatalogingErrorDisplayforDDMMYYYYFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49530");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DDMMYYYY')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_DDMMYYYY,Input.Natives_DDMMYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49531
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 20)
	public void verifyCatalogingErrorDisplayforYYYYMMDDWithSlashFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49531");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_YYYYMMDD_Slash,Input.Natives_YYYYMMDD_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49532
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/DD/MM')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 21)
	public void verifyCatalogingErrorDisplayforYYYYDDMMWithSlashFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49532");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/DD/MM')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_YYYYDDMM_Slash,Input.Natives_YYYYDDMM_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49533
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 22)
	public void verifyCatalogingErrorDisplayforMMDDYYYYHHMIFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49533");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_HHMI,Input.Natives_MMDDYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49534
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 23)
	public void verifyCatalogingErrorDisplayforDDMMYYYYHHMIFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49534");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_DDMMYYYY_HHMI,Input.Natives_DDMMYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49535
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI:SS')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 24)
	public void verifyCatalogingErrorDisplayforMMDDYYYYHHMISSFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49535");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI:SS')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_HHMISS,Input.Natives_MMDDYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49536
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI:SS')
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 25)
	public void verifyCatalogingErrorDisplayforDDMMYYYYHHMISSFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49536");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI:SS')");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_DDMMYYYY_HHMISS,Input.Natives_DDMMYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49516
	 * Description :Verify when user selects date & time format 'DDMMYYYY' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 26)
	public void verifyDateFormatDDMMYYYYInIngestionSameAsInDATFile() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49516");
		baseClass.stepInfo("Verify when user selects date & time format 'DDMMYYYY' for ingestion which is same as in the DAT file");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DDMMYYYY",Input.DAT_DDMMYYYY,Input.Natives_DDMMYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 22/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49540
	 * Description :Verify that cataloging error should be displayed for the document if the date & time format for few documents in DAT is different than the selected date format for ingestion
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 27)
	public void verifyCatalogingErrorForDiferentDateFormat() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49540");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, Input.dateFormat,Input.DAT_DDMMYYYY,Input.Natives_DDMMYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 22/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49541
	 * Description :Verify that Ingestion should be continued if cataloging date time error is displayed for few documents
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 28)
	public void verifyIgnoringCatalogingErrorAndContinueIngestion() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49541");
		baseClass.stepInfo("Verify that Ingestion should be continued if cataloging date time error is displayed for few documents");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//verify ingestion status after ignoring errors
		ingestionPage.verifyIgnoringErrorsAndContinueIngestion();
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 23/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47403
	 * Description :To verify the headers display in Source field on mapping page.
	 * @throws Exception 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 29)
	public void verifyHeadersDisplayOnMappingPage() throws Exception  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47403");
		baseClass.stepInfo("To verify the headers display in Source field on mapping page.");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		// verify header section count in popup matched with configure mapping section
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		
	}
	
	/** 
     *Author :Arunkumar date: 24/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48176
	 * Description :To Verify Rollback in Ingestion after Copy is completed.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 30)
	public void verifyRollbackAfterCopyCompleted() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48176");
		baseClass.stepInfo("To Verify Rollback in Ingestion after Copy is completed");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		// Rollback
		ingestionPage.rollBackIngestion();
		
		}
	
	/** 
     *Author :Arunkumar date: 25/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48174
	 * Description :To Verify Ingestion details information after rollback
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 31)
	public void verifyIngestionDetailsAfterRollback() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48174");
		baseClass.stepInfo("To Verify Ingestion details information after rollback");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
		// Rollback and verify ingestion details
		ingestionPage.rollBackIngestion();
		ingestionPage.verifyIngestionDetailsTillIndexingAfterRollback();
		}
	
	/** 
     *Author :Arunkumar date: 25/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-46932
	 * Description :To verify that Admin is able to Rollback the ongoing Ingestion process.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 32)
	public void verifyAdminAbleToRollbackIngestion() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-46932");
		baseClass.stepInfo("To verify that Admin is able to Rollback the ongoing Ingestion process.");
		//Catalogging and rollback
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.ingestionCatalogging();
		ingestionPage.rollBackIngestion();
		//Copying and rollback
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.rollBackIngestion();
		//indexing and rollback
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
		ingestionPage.rollBackIngestion();
		//save as draft and delete
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifyIngestionSaveAsDraftAndDelete();
		
		}
	
	/** 
     *Author :Arunkumar date: 28/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47300
	 * Description :If 'Add Only' option is selected, SourceDocID,SourceParentDocID,CustodianName & Datasource fields
	 * must be selected in the mapping and  Verify the navigation upon selection of 'Go Back' option
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 33)
	public void verifyMappingFieldsWithGoBackOption() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47300");
		baseClass.stepInfo("If 'Add Only' option is selected,Verify Mapping and navigation upon selection of 'Go Back' option");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);	
		ingestionPage.verifyMappingFiledPriorSelection(Input.docId,Input.dataSource,Input.custodian);
		
	}
	
	/** 
     *Author :Arunkumar date: 29/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47595
	 * Description :After Rollback, ingestion should go back to the Draft state. Rollback will just take the ingestion in Draft mode.
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 34)
	public void verifyIngestionStatusAfterRollback() throws InterruptedException   {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47595");
		baseClass.stepInfo("After Rollback, ingestion should go back to the Draft state. Rollback will just take the ingestion in Draft mode.");
		// verify ingestion Draft status after saving as draft
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// Verify ingestion Draft status after Catalogging
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyDraftModeStatusAfterRollbackIngestion();
		//Verify ingestion Draft status after Copying
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDraftModeStatusAfterRollbackIngestion();
		// Verify ingestion Draft status after indexing
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDraftModeStatusAfterRollbackIngestion();		
		
	}
	
	/** 
     *Author :Arunkumar date: 30/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49020
	 * Description :Verify two ingestions with step ( Cataloging, copying, indexing) having ingestion type add only  must run simultaneously
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 35)
	public void verifyTwoIngestionFromCatalogToIndexingSimultaneously() throws InterruptedException   {
		
		String[] dataset= {Input.TiffImagesFolder,Input.HiddenPropertiesFolder};
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49020");
		baseClass.stepInfo("Verify two ingestions with step ( Cataloging, copying, indexing) having ingestion type add only  must run simultaneously");
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		// Perform catalog,copy and indexing for two ingestion
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		
	}
	
	/** 
     *Author :Arunkumar date: 30/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49021
	 * Description :Verify two ingestions with step ( copying, indexing) having ingestion type add only  must run simultaneously
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 36)
	public void verifyTwoIngestionRunSimultaneously() throws InterruptedException   {
		
		String[] dataset= {Input.TiffImagesFolder,Input.HiddenPropertiesFolder};
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49021");
		baseClass.stepInfo("Verify two ingestions with step ( copying, indexing) having ingestion type add only  must run simultaneously");
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		// Perform Copying and indexing for two ingestion
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		
	}
	
	/** 
     *Author :Arunkumar date: 31/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48861
	 * Description :Validate warning message is not prompted when rolling back an add only ingestion(copying/cataloging/indexing step)
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 37)
	public void verifyWarningMessageNotPromptedWhenRollingbackIngestion() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48861");
		baseClass.stepInfo("Validate warning message is not prompted when rolling back an add only ingestion(copying/cataloging/indexing step)");
		//Catalogging and rollback
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyWarningMessageAndRollbackAddOnlyIngestion();
		//Copying and rollback
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.verifyWarningMessageAndRollbackAddOnlyIngestion();
		//indexing and rollback
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
		ingestionPage.verifyWarningMessageAndRollbackAddOnlyIngestion();

		}
	
	/** 
     *Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47369
	 * Description :To verify 'Ingestion Details' pop up display
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 38)
	public void verifyIngestionDetailsPopupDisplay() {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47369");
		baseClass.stepInfo("To verify 'Ingestion Details' pop up display");
		ingestionPage.verifyIngestionDetails();
	}
	
	/** 
     *Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47593
	 * Description :As a project admin I will be able to rollback an ingestion - new approach
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 39)
	public void verifyRollbackOptionAtDifferentIngestionStages() throws InterruptedException   {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47593");
		baseClass.stepInfo("As a project admin I will be able to rollback an ingestion - new approach");
		// verify rollback option after saving ingestion as draft
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// Verify rollback option status after Catalogging stage
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyRollbackOptionStatus();
		ingestionPage.rollBackIngestion();
		//Verify rollback option status after Copying stage
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.verifyRollbackOptionStatus();
		ingestionPage.rollBackIngestion();
		// Verify rollback option status after indexing stage
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
		ingestionPage.verifyRollbackOptionStatus();	
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47362
	 * Description :To verify that on Ingestion Home page, user is able to access all page by navigation controls.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 40)
	public void verifyIngestionHomePageNavigation() {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47362");
		baseClass.stepInfo("To verify that on Ingestion Home page, user is able to access all page by navigation controls.");
		ingestionPage.verifyHomePageNavigationControl();
	}
	
	/** 
     *Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47258
	 * Description :To Verify Contents of Ingestion Tiles On Ingestions Home.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 41)
	public void verifyIngestionTileContentInHomePage() {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47258");
		baseClass.stepInfo("To Verify Contents of Ingestion Tiles On Ingestions Home.");
		ingestionPage.verifyContentOnIngestionTiles();
		
	}
	
	/** 
     *Author :Arunkumar date: 06/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47589
	 * Description :Ingestion error list changes for  ignore fields in UI
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 42)
	public void verifyIgnoreOptionInErrorList() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47589");
		baseClass.stepInfo("Ingestion error list changes for  ignore fields in UI");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.DAT_DDMMYYYY_HHMISS,Input.Natives_DDMMYYYY_HHMISS);
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyIgnoringErrorsAndContinueIngestion();
		ingestionPage.ingestionCopying();
		ingestionPage.verifyIgnoreOptionAndCheckbox();
		//rollback
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 06/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49526
	 * Description :Verify when user selects date & time format 'YYYY/MM/DD HH:MI:SS' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 43)
	public void verifyDateFormatYYYYMMDDHHMISSInIngestionSameAsInDATFile() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49526");
		baseClass.stepInfo("Verify when user selects date & time format 'YYYY/MM/DD HH:MI:SS' for ingestion which is same as in the DAT file");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49022
	 * Description :Verify two ingestions with step (Indexing  , Approval ) having ingestion type add only  must run simultaneously
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 44)
	public void verifyTwoIngestionRunTillApprovingSimultaneously() throws InterruptedException   {
		
		String[] dataset= {Input.AllSourcesFolder,Input.TiffImagesFolder};
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49022");
		baseClass.stepInfo("Verify two ingestions with step (Indexing  , Approval ) having ingestion type add only  must run simultaneously");
		// Verify two add only type ingestion run simultaneously
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		ingestionPage.approveIngestion(2);
	}
	
	/** 
     *Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47594
	 * Description :To Verify for Approved ingestions, there should not have any option for Rollback. 
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 45)
	public void verifyRollbackStatusForApprovedIngestion() throws InterruptedException {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47594");
		baseClass.stepInfo("To Verify for Approved ingestions, there should not have any option for Rollback.");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			ingestionPage.getFilterByButton().Visible()  ;}}), Input.wait30); 
		ingestionPage.getFilterByButton().waitAndClick(10);
    	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			ingestionPage.getFilterByAPPROVED().Visible()  ;}}), Input.wait30); 
		ingestionPage.getFilterByAPPROVED().waitAndClick(10);
		ingestionPage.getRefreshButton().waitAndClick(10);
		// Verify rollback option for approved ingestion
		if(ingestionPage.getIngestionDetailPopup(1).isElementAvailable(5)) {
			baseClass.stepInfo("Ingestion already present in approved stage");
			ingestionPage.verifyRollbackOptionForApprovedIngestion();
		}
		else {
			baseClass.stepInfo("Need to perform new ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.verifyRollbackOptionForApprovedIngestion();

		}
	}
	
	/** 
     *Author :Arunkumar date: 13/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50764
	 * Description :Verify that after resize browser Ingestion grid does not resize
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 46)
	public void verifyIngestionGridAfterResizeBrowser()  {
		loginPage.logout();
		// Resize browser
		int width = 1200;
		int height =900;
		Dimension dimension = new Dimension(width,height);
		driver.Manage().window().setSize(dimension);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50764");
		baseClass.stepInfo("Verify that after resize browser Ingestion grid does not resize");
		// verify size of the grid after resize browser
		ingestionPage.verifySizeOfIngestionGrid();
	}
	
	/** 
     *Author :Arunkumar date: 19/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49732
	 * Description :Verify 'Stitching TIFFs' count in Ingestion details popup after rollback the ingestion
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 47)
	public void verifyStitchedTiffDocCountAfterRollback() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49732");
		baseClass.stepInfo("Verify 'Stitching TIFFs' count in Ingestion details popup after rollback the ingestion");
		ingestionPage.tiffImagesIngestion(Input.DATFile2,Input.tiffLoadFile,"false");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.StitchedTIFF, "source");
		ingestionPage.verifyDraftModeStatusAfterRollbackIngestion();
		ingestionPage.verifyValueInCopyingSectionAfterRollback(Input.StitchedTIFF);
		}
	
	/** 
     *Author :Arunkumar date: 19/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49497
	 * Description :Verify that in Copying stage, error count should be display for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 48)
	public void verifyGeneratedSearchablePDFsErrorCountForTiff() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49497");
		baseClass.stepInfo("Verify that in Copying stage, error count should be display for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up");
		ingestionPage.tiffImagesIngestion(Input.DATFile3,Input.tiffFile2,"true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.generateSearchablePDF, "error");
		// Rollback ingestion
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 19/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49496
	 * Description :Verify that in Copying stage, document count should be displayed for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 49)
	public void verifyGeneratedSearchablePDFsDocumentCountForTiff() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49496");
		baseClass.stepInfo("Verify that in Copying stage, document count should be displayed for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up");
		ingestionPage.tiffImagesIngestion(Input.DATFile3,Input.tiffFile2,"true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.generateSearchablePDF, "source");
		// Rollback ingestion
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-59385
	 * Description :Verify that error should not be displayed while saving Ingestion when PDF option is selected with Is Path in DAT option
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 50)
	public void verifyErrorStatusWhenPdfPathInDatOption() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-59385");
		baseClass.stepInfo("Verify that error should not be displayed while saving Ingestion when PDF option is selected with Is Path in DAT option");
		ingestionPage.selectPdfInPathFileAndSaveAsDraft(Input.AK_NativeFolder,Input.DATFile1,Input.documentKey,Input.prodBeg);
	}
	
	/** 
     *Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47293
	 * Description :Ingesting Duplicate files.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 51)
	public void verifyErrorForIngestingDuplicateFiles() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47293");
		baseClass.stepInfo("Ingesting Duplicate files.");
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
		// verify duplicate ingestion error
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 21/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47296
	 * Description :Ingestion with draft mode.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 52)
	public void verifyIngestionWithDraftMode() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47296");
		baseClass.stepInfo("Ingestion with draft mode.");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// verify options available for ingestion in draft state
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 21/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47299
	 * Description :Verify Successful ingestion with ingestion details display with the help of Valid data and DAT file.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 53)
	public void verifyIngestionDetailWithValidData() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47299");
		baseClass.stepInfo("Verify Successful ingestion with ingestion details display with the help of Valid data and DAT file.");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		// verify ingestion details after ingestion started
		ingestionPage.verifyDetailsAfterStartedIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47406
	 * Description :To verify indexing for the Text file from Ingestion details pop up.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 54)
	public void verifyIndexingForTextFile() throws InterruptedException  {
		
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47406");
		baseClass.stepInfo("To verify indexing for the Text file from Ingestion details pop up.");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1,Input.textFile1, Input.documentKey);
		// verify copy,index and approve status during the ingestion in inprogress state
		ingestionPage.verifyStatusDuringInProgress();
		ingestionPage.ingestionCatalogging();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
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
