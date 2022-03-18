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
	@Test(enabled = true,  groups = {"regression" },priority = 21)
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
	@Test(enabled = true,  groups = {"regression" },priority = 22)
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
