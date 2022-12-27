package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.Dimension;
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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Phase1_Class_1 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
	TallyPage tally;
	DocExplorerPage docExplorer;
	ProjectPage project;
	UserManagement userManage;
	SecurityGroupsPage securityGroup;

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
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);	
	}
	
	/*This class contains 57 testCases.*/

	/**
	 * Author : Mohan date:24/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify if Ingestion is saved as draft then on Ingestion page, it should retain the selected Date & Time format'RPMXCON-49549' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49549",enabled = true, groups = { "regression" })
	public void verifyDateFormateForIngestionWithSameDATFile() throws Exception {
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49549");
		baseClass.stepInfo("Verify if Ingestion is saved as draft then on Ingestion page, it should retain the selected Date & Time format");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Login as Project Admin Go to Ingestion and click to Add new ingestion  Prerequisites: Date & time format in DAT file should be 'YYYY/DD/MM' Select Source System  Select Ingestion Type: Add Only  Select 'Date & Time Format as 'YYYY/DD/MM'  and click on Next  Configure field mapping and click 'Preview & Run'");
		ingestionPage.validateDateAndTimeFormateWhenIngestionIsSaveAsDraft(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS");
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.verifyDateFormateInCatalogeAndDraft();
		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date:24/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify if Ingestion is rollbacked and open in the wizard,On Ingestion page, it should display the default Date & Time format'RPMXCON-49548' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49548",enabled = true, groups = { "regression" })
	public void verifyDateFormateForIngestionWithSameDATFileAndRollBack() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49548");
		baseClass.stepInfo("Verify if Ingestion is rollbacked and open in the wizard,On Ingestion page, it should display the default Date & Time format");
		baseClass.stepInfo("Login as Project Admin Go to Ingestion and click to Add new ingestion  Prerequisites: Date & time format in DAT file should be 'YYYY/DD/MM' Select Source System  Select Ingestion Type: Add Only  Select 'Date & Time Format as 'YYYY/DD/MM'  and click on Next  Configure field mapping and click 'Preview & Run'");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS",Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst );
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.rollBackIngestion();
		ingestionPage.verifyDateFormateInCatalogeAndDraft();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 24/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49702
	 * Description :Verify that if document has multipage TIFF then in Copying stage of Ingestion ,it should displays the 'Stitched TIFFs' under the Copy column.
	 */
	@Test(description ="RPMXCON-49702",enabled = true,  groups = {"regression" } )
	public void verifyStitchedTiffTermUnderCopyColumn() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49702");
		baseClass.stepInfo("Verify that if document has multipage TIFF then in Copying stage of Ingestion ,it should displays the 'Stitched TIFFs' under the Copy column");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
		ingestionPage.verifyDataPresentInCopyColumn(Input.StitchedTIFF);
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
		}
	
	/**
	 * Author : Mohan date:25/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD HH:MI:SS')'RPMXCON-49537' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49537",enabled = true, groups = { "regression" })
	public void verifyCatalogingErrorSelectedDateFormateIsDifferentThanInDAT() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49537");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD HH:MI:SS')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogigErrorForDatSelectDateFormate();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date:25/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'YYYY/DD/MM' for ingestion which is same as in the DAT file'RPMXCON-49518' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49518",enabled = true, groups = { "regression" })
	public void verifyDateFormateYYYYDDMMWithSlashInIngestionSameAsInDATFile() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49518");
		baseClass.stepInfo("Verify when user selects date & time format 'YYYY/DD/MM' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/DD/MM",Input.DAT_YYYYDDMM_Slash,Input.Natives_YYYYDDMM_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'MMDDYYYY' for ingestion which is same as in the DAT file'RPMXCON-49515' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49515",enabled = true, groups = { "regression" })
	public void verifyDateFormateMMDDYYYYInIngestionSameAsInDATFile() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49515");
		baseClass.stepInfo("Verify when user selects date & time format 'MMDDYYYY' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MMDDYYYY",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'DD/MM/YYYY' for ingestion which is same as in the DAT file'RPMXCON-49514' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49514",enabled = true, groups = { "regression" })
	public void verifyDateFormateDDMMYYYYWithSlashInIngestionSameAsInDATFile() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49514");
		baseClass.stepInfo("Verify when user selects date & time format 'DD/MM/YYYY' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DD/MM/YYYY",Input.DAT_DDMMYYYY_Slash,Input.Natives_DDMMYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'MM/DD/YYYY' for ingestion which is same as in the DAT file'RPMXCON-49513' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49513",enabled = true, groups = { "regression" })
	public void verifyDateFormateMMDDYYYYWithSlashInIngestionSameAsInDATFile() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49513");
		baseClass.stepInfo("Verify when user selects date & time format 'MM/DD/YYYY' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyDateFormateInIngestionField();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author : Mohan date:28/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when user selects date & time format 'YYYY/MM/DD' for ingestion which is same as in the DAT file'RPMXCON-49517' 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49517",enabled = true, groups = { "regression" })
	public void verifyDateFormateYYYYMMDDWithSlashInIngestionSameAsInDATFile() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49517");
		baseClass.stepInfo("Verify when user selects date & time format 'YYYY/MM/DD' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_YYYYMMDD_Slash,Input.Natives_YYYYMMDD_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/**
	 * Author : Arunkumar date:16/03/22 NA Modified date: NA Modified by:NA Test Case Id:RPMXCON-49506
	 * Description :Verify the 'Next' button enable/disable from 'Ingestion Wizard' screen when 'Date & Time Format' selected/not-selected
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49506",enabled = true, groups = { "regression" })
	public void verifyNextButtonFromIngestionWizard() throws Exception{
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49506");
		baseClass.stepInfo("Verify the 'Next' button enable/disable from 'Ingestion Wizard' screen when 'Date & Time Format' selected/not-selected");
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.VerifyNextButtonStatusBasedOnDateTimeFormatSelection();
		loginPage.logout();
		
	}
	
    /** 
     *Author :Arunkumar date: 16/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49733
	 * Description :Verify 'Missed Docs' count is display for 'Stitching TIFF' in Ingestion detail pop up
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49733",enabled = true,  groups = {"regression" } )
	public void verifyStitchedTiffCountUnderCopyColumn() throws InterruptedException   {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49733");
		baseClass.stepInfo("Verify 'Missed Docs' count is display for 'Stitching TIFF' in Ingestion detail pop up");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.IngestionCatlogtoCopying(Input.TiffImagesFolder);
		ingestionPage.verifyMissedDocValuePresentInCopyTableColumn(Input.StitchedTIFF);
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	
		}
	
	/** 
     *Author :Arunkumar date: 17/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49522
	 * Description :Verify when user selects date & time format 'MM/DD/YYYY HH:MI' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49522",enabled = true,  groups = {"regression" } )
	public void verifyDateFormatMMDDYYYYHHMIInIngestionSameAsInDATFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49522");
		baseClass.stepInfo("Verify when user selects date & time format 'MM/DD/YYYY HH:MI' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY HH:MI",Input.DAT_MMDDYYYY_HHMI,Input.Natives_MMDDYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	 /** 
     *Author :Arunkumar date: 17/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49523
	 * Description :Verify when user selects date & time format 'DD/MM/YYYY HH:MI' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49523",enabled = true,  groups = {"regression" } )
	public void verifyDateFormatDDMMYYYYHHMIInIngestionSameAsInDATFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49523");
		baseClass.stepInfo("Verify when user selects date & time format 'DD/MM/YYYY HH:MI' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DD/MM/YYYY HH:MI",Input.DAT_DDMMYYYY_HHMI,Input.Natives_DDMMYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 17/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49525
	 * Description :Verify when user selects date & time format 'DD/MM/YYYY HH:MI:SS' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49525",enabled = true,  groups = {"regression" } )
	public void verifyDateFormatDDMMYYYYHHMISSInIngestionSameAsInDATFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49525");
		baseClass.stepInfo("Verify when user selects date & time format 'DD/MM/YYYY HH:MI:SS' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DD/MM/YYYY HH:MI:SS",Input.DAT_DDMMYYYY_HHMISS,Input.Natives_DDMMYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49527
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49527",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforMMDDYYYYWithSlashFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49527");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	

	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49528
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49528",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforDDMMYYYYWithSlashFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49528");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_DDMMYYYY_Slash,Input.Natives_DDMMYYYY_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49529
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MMDDYYYY')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49529",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforMMDDYYYYFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49529");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MMDDYYYY')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49530
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DDMMYYYY')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49530",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforDDMMYYYYFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49530");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DDMMYYYY')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_DDMMYYYY,Input.Natives_DDMMYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49531
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49531",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforYYYYMMDDWithSlashFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49531");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/MM/DD')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_YYYYMMDD_Slash,Input.Natives_YYYYMMDD_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49532
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/DD/MM')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49532",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforYYYYDDMMWithSlashFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49532");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('YYYY/DD/MM')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_YYYYDDMM_Slash,Input.Natives_YYYYDDMM_Slash);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49533
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49533",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforMMDDYYYYHHMIFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49533");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_HHMI,Input.Natives_MMDDYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	
	/** 
     *Author :Arunkumar date: 18/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49534
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49534",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforDDMMYYYYHHMIFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49534");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_DDMMYYYY_HHMI,Input.Natives_DDMMYYYY_HHMI);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49535
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI:SS')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49535",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforMMDDYYYYHHMISSFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49535");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('MM/DD/YYYY HH:MI:SS')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_HHMISS,Input.Natives_MMDDYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49536
	 * Description :Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI:SS')
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49536",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorDisplayforDDMMYYYYHHMISSFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49536");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT ('DD/MM/YYYY HH:MI:SS')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD",Input.DAT_DDMMYYYY_HHMISS,Input.Natives_DDMMYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49516
	 * Description :Verify when user selects date & time format 'DDMMYYYY' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49516",enabled = true,  groups = {"regression" } )
	public void verifyDateFormatDDMMYYYYInIngestionSameAsInDATFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49516");
		baseClass.stepInfo("Verify when user selects date & time format 'DDMMYYYY' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "DDMMYYYY",Input.DAT_DDMMYYYY,Input.Natives_DDMMYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 22/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49540
	 * Description :Verify that cataloging error should be displayed for the document if the date & time format for few documents in DAT is different than the selected date format for ingestion
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49540",enabled = true,  groups = {"regression" } )
	public void verifyCatalogingErrorForDiferentDateFormat() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49540");
		baseClass.stepInfo("Verify that cataloging error should be displayed if selected date format in Ingestion is different than in DAT");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, Input.dateFormat,Input.DAT_DDMMYYYY,Input.Natives_DDMMYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 22/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49541
	 * Description :Verify that Ingestion should be continued if cataloging date time error is displayed for few documents
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49541",enabled = true,  groups = {"regression" } )
	public void verifyIgnoringCatalogingErrorAndContinueIngestion() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49541");
		baseClass.stepInfo("Verify that Ingestion should be continued if cataloging date time error is displayed for few documents");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyCatalogingErrorIfDateFormatIsDifferentThanDAT();
		//verify ingestion status after ignoring errors
		ingestionPage.verifyIgnoringErrorsAndContinueIngestion();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 23/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47403
	 * Description :To verify the headers display in Source field on mapping page.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-47403",enabled = true,  groups = {"regression" } )
	public void verifyHeadersDisplayOnMappingPage() throws Exception  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47403");
		baseClass.stepInfo("To verify the headers display in Source field on mapping page.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		// verify header section count in popup matched with configure mapping section
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		
	}
	
	/** 
     *Author :Arunkumar date: 24/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48176
	 * Description :To Verify Rollback in Ingestion after Copy is completed.
	 */
	@Test(description ="RPMXCON-48176",enabled = true,  groups = {"regression" } )
	public void verifyRollbackAfterCopyCompleted() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-48176");
		baseClass.stepInfo("To Verify Rollback in Ingestion after Copy is completed");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
		}
	
	/** 
     *Author :Arunkumar date: 25/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48174
	 * Description :To Verify Ingestion details information after rollback
	 */
	@Test(description ="RPMXCON-48174",enabled = true,  groups = {"regression" } )
	public void verifyIngestionDetailsAfterRollback() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-48174");
		baseClass.stepInfo("To Verify Ingestion details information after rollback");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.YYYYMMDDHHMISSDat,Input.YYYYMMDDHHMISSLst);
		ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
		ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
		// Rollback and verify ingestion details
		ingestionPage.rollBackIngestion();
		ingestionPage.verifyIngestionDetailsTillIndexingAfterRollback();
		loginPage.logout();
		}
	
	/** 
     *Author :Arunkumar date: 25/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-46932
	 * Description :To verify that Admin is able to Rollback the ongoing Ingestion process.
	 */
	@Test(description ="RPMXCON-46932",enabled = false,  groups = {"regression" } )
	public void verifyAdminAbleToRollbackIngestion() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-46932");
		baseClass.stepInfo("To verify that Admin is able to Rollback the ongoing Ingestion process.");
		//Catalogging and rollback
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
		}
	
	/** 
     *Author :Arunkumar date: 28/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47300
	 * Description :If 'Add Only' option is selected, SourceDocID,SourceParentDocID,CustodianName & Datasource fields
	 * must be selected in the mapping and  Verify the navigation upon selection of 'Go Back' option
	 */
	@Test(description ="RPMXCON-47300",enabled = true,  groups = {"regression" } )
	public void verifyMappingFieldsWithGoBackOption() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47300");
		baseClass.stepInfo("If 'Add Only' option is selected,Verify Mapping and navigation upon selection of 'Go Back' option");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);	
		ingestionPage.verifyMappingFiledPriorSelection(Input.docId,Input.dataSource,Input.custodian);
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 29/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47595
	 * Description :After Rollback, ingestion should go back to the Draft state. Rollback will just take the ingestion in Draft mode.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47595",enabled = true,  groups = {"regression" } )
	public void verifyIngestionStatusAfterRollback() throws InterruptedException   {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47595");
		baseClass.stepInfo("After Rollback, ingestion should go back to the Draft state. Rollback will just take the ingestion in Draft mode.");
		// verify ingestion Draft status after saving as draft
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 30/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49020
	 * Description :Verify two ingestions with step ( Cataloging, copying, indexing) having ingestion type add only  must run simultaneously
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49020",enabled = true,  groups = {"regression" } )
	public void verifyTwoIngestionFromCatalogToIndexingSimultaneously() throws InterruptedException   {
		
		String[] dataset= {Input.TiffImagesFolder,Input.HiddenPropertiesFolder};
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49020");
		baseClass.stepInfo("Verify two ingestions with step ( Cataloging, copying, indexing) having ingestion type add only  must run simultaneously");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		// Perform catalog,copy and indexing for two ingestion
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 30/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49021
	 * Description :Verify two ingestions with step ( copying, indexing) having ingestion type add only  must run simultaneously
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49021",enabled = true,  groups = {"regression" } )
	public void verifyTwoIngestionRunSimultaneously() throws InterruptedException   {
		
		String[] dataset= {Input.TiffImagesFolder,Input.HiddenPropertiesFolder};
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49021");
		baseClass.stepInfo("Verify two ingestions with step ( copying, indexing) having ingestion type add only  must run simultaneously");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder,Input.DATFile3);
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		// Perform Copying and indexing for two ingestion
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 31/03/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48861
	 * Description :Validate warning message is not prompted when rolling back an add only ingestion(copying/cataloging/indexing step)
	 */
	@Test(description ="RPMXCON-48861",enabled = true,  groups = {"regression" } )
	public void verifyWarningMessageNotPromptedWhenRollingbackIngestion() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-48861");
		baseClass.stepInfo("Validate warning message is not prompted when rolling back an add only ingestion(copying/cataloging/indexing step)");
		//Catalogging and rollback
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();

		}
	
	/** 
     *Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47593
	 * Description :As a project admin I will be able to rollback an ingestion - new approach
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47593",enabled = true,  groups = {"regression" } )
	public void verifyRollbackOptionAtDifferentIngestionStages() throws InterruptedException   {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47593");
		baseClass.stepInfo("As a project admin I will be able to rollback an ingestion - new approach");
		// verify rollback option after saving ingestion as draft
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47258
	 * Description :To Verify Contents of Ingestion Tiles On Ingestions Home.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47258",enabled = true,  groups = {"regression" } )
	public void verifyIngestionTileContentInHomePage() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47258");
		baseClass.stepInfo("To Verify Contents of Ingestion Tiles On Ingestions Home.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyContentOnIngestionTiles();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 06/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47589
	 * Description :Ingestion error list changes for  ignore fields in UI
	 */
	@Test(description ="RPMXCON-47589",enabled = true,  groups = {"regression" } )
	public void verifyIgnoreOptionInErrorList() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47589");
		baseClass.stepInfo("Ingestion error list changes for  ignore fields in UI");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder,Input.dateFormat,Input.DAT_DDMMYYYY_HHMISS,Input.Natives_DDMMYYYY_HHMISS);
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyIgnoringErrorsAndContinueIngestion();
		ingestionPage.ingestionCopying();
		ingestionPage.verifyIgnoreOptionAndCheckbox();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 06/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49526
	 * Description :Verify when user selects date & time format 'YYYY/MM/DD HH:MI:SS' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49526",enabled = true,  groups = {"regression" } )
	public void verifyDateFormatYYYYMMDDHHMISSInIngestionSameAsInDATFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49526");
		baseClass.stepInfo("Verify when user selects date & time format 'YYYY/MM/DD HH:MI:SS' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 13/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50764
	 * Description :Verify that after resize browser Ingestion grid does not resize
	 */
	@Test(description ="RPMXCON-50764",enabled = true,  groups = {"regression" } )
	public void verifyIngestionGridAfterResizeBrowser()  {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		loginPage.logout();
		// Resize browser
		int width = 1200;
		int height =900;
		Dimension dimension = new Dimension(width,height);
		driver.Manage().window().setSize(dimension);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-50764");
		baseClass.stepInfo("Verify that after resize browser Ingestion grid does not resize");
		// verify size of the grid after resize browser
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.verifySizeOfIngestionGrid();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 19/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49732
	 * Description :Verify 'Stitching TIFFs' count in Ingestion details popup after rollback the ingestion
	 */
	@Test(description ="RPMXCON-49732",enabled = true,  groups = {"regression" } )
	public void verifyStitchedTiffDocCountAfterRollback() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49732");
		baseClass.stepInfo("Verify 'Stitching TIFFs' count in Ingestion details popup after rollback the ingestion");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.tiffImagesIngestion(Input.DATFile2,Input.tiffLoadFile,"false");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.StitchedTIFF, "source");
		ingestionPage.verifyDraftModeStatusAfterRollbackIngestion();
		ingestionPage.verifyValueInCopyingSectionAfterRollback(Input.StitchedTIFF);
		loginPage.logout();
		}
	
	/** 
     *Author :Arunkumar date: 19/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49497
	 * Description :Verify that in Copying stage, error count should be display for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up
	 */
	@Test(description ="RPMXCON-49497",enabled = true,  groups = {"regression" } )
	public void verifyGeneratedSearchablePDFsErrorCountForTiff() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49497");
		baseClass.stepInfo("Verify that in Copying stage, error count should be display for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.tiffImagesIngestion(Input.DATFile3,Input.tiffFile2,"true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.generateSearchablePDF, "error");
		// Rollback ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 19/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49496
	 * Description :Verify that in Copying stage, document count should be displayed for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up
	 */
	@Test(description ="RPMXCON-49496",enabled = true,  groups = {"regression" } )
	public void verifyGeneratedSearchablePDFsDocumentCountForTiff() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49496");
		baseClass.stepInfo("Verify that in Copying stage, document count should be displayed for Generated Searchable PDFs for TIFFs on Ingestion Execution details pop up");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.tiffImagesIngestion(Input.DATFile3,Input.tiffFile2,"true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.generateSearchablePDF, "source");
		// Rollback ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-59385
	 * Description :Verify that error should not be displayed while saving Ingestion when PDF option is selected with Is Path in DAT option
	 */
	@Test(description ="RPMXCON-59385",enabled = true,  groups = {"regression" } )
	public void verifyErrorStatusWhenPdfPathInDatOption() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-59385");
		baseClass.stepInfo("Verify that error should not be displayed while saving Ingestion when PDF option is selected with Is Path in DAT option");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.selectPdfInPathFileAndSaveAsDraft(Input.AK_NativeFolder,Input.DATFile1,Input.documentKey,Input.prodBeg);
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 21/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47296
	 * Description :Ingestion with draft mode.
	 */
	@Test(description ="RPMXCON-47296",enabled = true,  groups = {"regression" } )
	public void verifyIngestionWithDraftMode() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47296");
		baseClass.stepInfo("Ingestion with draft mode.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder, Input.DAT_MMDDYYYY_HHMI);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// verify options available for ingestion in draft state
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
		loginPage.logout();
	}
	
	/** 
     *Author :Arunkumar date: 21/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47299
	 * Description :Verify Successful ingestion with ingestion details display with the help of Valid data and DAT file.
	 */
	@Test(description ="RPMXCON-47299",enabled = true,  groups = {"regression" } )
	public void verifyIngestionDetailWithValidData() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47299");
		baseClass.stepInfo("Verify Successful ingestion with ingestion details display with the help of Valid data and DAT file.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
		// verify ingestion details after ingestion started
		ingestionPage.verifyDetailsAfterStartedIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 21/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47406
	 * Description :To verify indexing for the Text file from Ingestion details pop up.
	 */
	@Test(description ="RPMXCON-47406",enabled = true,  groups = {"regression" } )
	public void verifyIndexingForTextFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47406");
		baseClass.stepInfo("To verify indexing for the Text file from Ingestion details pop up.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1,Input.textFile1, Input.documentKey);
		// verify copy,index and approve status during the ingestion in inprogress state
		ingestionPage.verifyStatusDuringInProgress();
		ingestionPage.ingestionCatalogging();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	
	/** 
     *Author: Mohan date: 27/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49524
	 * Description :Verify when user selects date & time format 'MM/DD/YYYY HH:MI:SS' for ingestion which is same as in the DAT file
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49524",enabled = true,  groups = {"regression" } )
	public void verifyDateFormatMMDDYYYYHHMISSInIngestionSameAsInDATFile() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49524");
		baseClass.stepInfo("Verify when user selects date & time format 'MM/DD/YYYY HH:MI:SS' for ingestion which is same as in the DAT file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY HH:MI:SS",Input.DAT_MMDDYYYY_HHMISS,Input.Natives_MMDDYYYY_HHMISS);
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		ingestionPage.verifyExpectedDateFormatAfterCatalogingStage();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/** 
     *Author :Arunkumar date: 27/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47590
	 * Description :Verify Back, Done and Save buttons along with ignore option for the error listed ingested records.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47590",enabled = true,  groups = {"regression" } )
	public void verifyOptionsForErrorListedIngestion() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47590");
		baseClass.stepInfo("Verify Back, Done and Save buttons along with ignore option for the error listed ingested records.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_HHMI,Input.Natives_MMDDYYYY_HHMI);
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);	
		//verify the options in error list ingestion details popup
		ingestionPage.verifyOptionsInErrorDetailsPopup();
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47362 Description :To verify that on Ingestion Home page,
	 * user is able to access all page by navigation controls.
	 */
	@Test(description ="RPMXCON-47362",enabled = true, groups = { "regression" } )
	public void verifyIngestionHomePageNavigation() {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47362");
		baseClass.stepInfo(
				"To verify that on Ingestion Home page, user is able to access all page by navigation controls.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.verifyHomePageNavigationControl();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47566 Description :To Verify status update in cataloging,
	 * copying, and Indexing Section in Ingestion Details Page
	 */
	@Test(description ="RPMXCON-47566",enabled = true, groups = { "regression" } )
	public void verifyStatusUpdateInPopup() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-47566");
		baseClass.stepInfo(
				"To Verify status update in cataloging, copying, and Indexing Section in Ingestion Details Page");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Catalogingblock");
		ingestionPage.ingestionCopying();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Copyingblock");
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Indexingblock");
		// rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}

	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47404 Description :To verify that if configure mapping is not
	 * matched the warning message is displayed and as per selection, admin can
	 * proceed.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47404",enabled = true, groups = { "regression" } )
	public void verifyConfigureMappingWarningMessage() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-47404");
		baseClass.stepInfo(
				"verify that if configure mapping is not matched the warning message is displayed and as per selection, admin can proceed.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyWarningMessageForConfigureMappingSection(Input.UniCodeFilesFolder, Input.datLoadFile1,
				Input.textFile1, Input.prodBeg, Input.documentKey);
		loginPage.logout();
		}

	}
	
	/** 
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48175 Description :To Verify Rollback In Ingestion for Files
	 * having Read Only Attributes.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-48175",enabled = true, groups = { "regression" } )
	public void verifyRollbackReadOnlyAttribute() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-48175");
		baseClass.stepInfo("To Verify Rollback In Ingestion for Files having Read Only Attributes.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}

	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49703 Description :Verify if 'Generate Searchable PDFs' is
	 * True for TIFF image and document has multi-page TIFF's then in the Copying
	 * section, Stitching TIFFs details should display before the "Generate
	 * Searchable PDFs" row on Ingestion details pop up
	 */
	@Test(description ="RPMXCON-49703",enabled = true, groups = { "regression" } )
	public void verifyStitchedTiffBeforeGeneratedSearchablePDFs() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-49703");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True Stitching TIFFs details should display before the Generate Searchable PDFs row on Ingestion details pop up");
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.tiffImagesIngestion(Input.DATFile4, Input.tiffLoadFile, "true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyTermPositionInCopyColumn(Input.StitchedTIFF);
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}
	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48194 Description :To Verify In Ingestions the pop-up Details
	 * Window for media ;audio & Transcript Count.
	 */
	@Test(description ="RPMXCON-48194",enabled = true, groups = { "regression" } )
	public void verifyMediaAndTranscriptDetailInPopup() throws InterruptedException {

		String[] selectedTerm = { "Native", "Text", "MP3 Variant", "Audio Transcript" };
		String[] unselectedTerm = { "PDF", Input.StitchedTIFF, Input.generateSearchablePDF };

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-48194");
		baseClass.stepInfo("To Verify In Ingestions the pop-up Details Window for media ;audio & Transcript Count.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.mediaAndTranscriptIngestion(Input.AllSourcesFolder, Input.DATFile1);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		for (int i = 0; i <= selectedTerm.length - 1; i++) {
			ingestionPage.verifyDataPresentInCopyTableColumn(selectedTerm[i], "source");
		}
		for (int i = 0; i <= unselectedTerm.length - 1; i++) {
			ingestionPage.verifyUnselectedSourceCountInCopySection(unselectedTerm[i]);
		}
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}
	}
	
	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47402 Description :To verify "Copy Ingestion" with ingestion
	 * wizard (Using link "Add Ingestion(New approach)")
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47402",enabled = true, groups = { "regression" } )
	public void verifyCopyIngestionUsingNewApproach() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47402");
		baseClass.stepInfo(
				"To verify 'Copy Ingestion' with ingestion wizard (Using link 'Add Ingestion(New approach)')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.performNewAddOnlyIngestionUsingCopyOption("Mapped Data", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		loginPage.logout();
		}
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
