package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression01 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewPage docView;
	SessionSearch sessionSearch;
	Utility utility;
	DataSets dataSets;
	IngestionPage_Indium ingestionPage;
	Input ip;
	SavedSearch savedSearch;

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

		
	}
	
	
	
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password ,Input.projectName02},
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password ,Input.projectName02 },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password, Input.projectName02 } };
	}

	
	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that documents ingested with the Date Format than the sightline standard format should be searchable by date fields'RPMXCON-49542' 
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 1)
	public void verifyDateFormateInTheMeatadataFieldAndDocList(String fullName,String userName, String password,String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49542");
		baseClass.stepInfo("Verify that documents ingested with the Date Format than the sightline standard format should be searchable by date fields");
		String fieldName = Input.masterDateText;
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);
		
		baseClass.stepInfo("Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();
		
		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);
		
		baseClass.stepInfo("Step 5 : Select all documents   Click on View in Doc List   Verify the Date fields from the metadata");
		baseClass.selectproject(Input.projectName02);
		sessionSearch.basicContentSearch(fieldName);
		sessionSearch.ViewInDocList();
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();
		
		
		}
	
	
	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc View with metadata'RPMXCON-49543' 
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 2)
	public void verifyDateFormateInTheMeatadataField(String fullName,String userName, String password,String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49543");
		baseClass.stepInfo("Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc View with metadata");
		String fieldName = Input.masterDateText;
		
		//Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);
		
		baseClass.stepInfo("Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();
		
		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);
		
		}
	
	
	
	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc List with metadata'RPMXCON-49544' 
	 * @throws Exception
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 3)
	public void verifyDateFormateInTheMeatadataFieldInDocList(String fullName,String userName, String password,String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49544");
		baseClass.stepInfo("Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc List with metadata");
		String fieldName = Input.masterDateText;
		
		//Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);
		
		baseClass.stepInfo("Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocList'");
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();
		
		}
	
	/**
	 * Author : Mohan date: 17/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify 'Source System' is disabled if user select Ingestion-Overlay on Ingestion Wizard page'RPMXCON-58508' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifySourceSystemIsDisabled() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
	
		baseClass.stepInfo("Test case Id: RPMXCON-58508");
		baseClass.stepInfo("Verify 'Source System' is disabled if user select Ingestion-Overlay on Ingestion Wizard page");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Step 2: Create an new Ingestion");
		ingestionPage.IngestionRegression(Input.AllSourcesFolder);
		baseClass.stepInfo("Step 3&4 : Go to Ingestion Add New Ingestion and Ingestion Type as 'Overlay Only' and Ingestion Type as 'Overlay Only'");
		ingestionPage.verifySourceSystemDisabledWhenOverylayIsAddedAsIngestionType();
		
		}
	
	/** 
     *Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49022
	 * Description :Verify two ingestions with step (Indexing  , Approval ) having ingestion type add only  must run simultaneously
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 5)
	public void verifyTwoIngestionRunTillApprovingSimultaneously() throws InterruptedException   {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		String[] dataset= {Input.AllSourcesFolder,Input.TiffImagesFolder};
		
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
	@Test(enabled = true,  groups = {"regression" },priority = 6)
	public void verifyRollbackStatusForApprovedIngestion() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
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
     *Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47598
	 * Description :Edit of saved Overlay ingestion with out mapping field selection.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 7)
	public void verifyEditOverlayIngestionWithoutMapping() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47598");
		baseClass.stepInfo("Edit of saved Overlay ingestion with out mapping field selection.");
		ingestionPage.OverlayIngestionForDATWithoutMappingFieldSection(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft without clicking next button and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		//click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
		
	}
	
	/** 
     *Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47597
	 * Description :Edit of Overlay saved ingestion with mapping field selection
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 8)
	public void verifyEditOverlayIngestionWithMapping() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47597");
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		//click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
		
	}
	
	/** 
     *Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47293
	 * Description :Ingesting Duplicate files.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 9)
	public void verifyErrorForIngestingDuplicateFiles() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
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
     *Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47600
	 * Description :Edit of saved Overlay with out ingestion with out mapping field selection.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 10)
	public void verifyEditOverlayIngestionWithoutDatAndIngestionMapping() {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47600");
		baseClass.stepInfo("Edit of saved Overlay with out DAT ingestion with out mapping field selection.");
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		//click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
	}
	
	/** 
     *Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47596
	 * Description :Copy of Overlay saved ingestion with mapping field selection
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 11)
	public void verifyCopyOverlayIngestionWithMapping()  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47596");
		baseClass.stepInfo("Copy of Overlay saved ingestion with mapping field selection");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		//click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("DAT",Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst,Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();
		
	}
	
	/** 
     *Author :Arunkumar date: 27/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47599
	 * Description :Copy of saved Overlay without DAT ingestion with out mapping field selection.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 12)
	public void verifyCopyOverlayIngestionWithoutDatAndMapping()  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47599");
		baseClass.stepInfo("Copy of saved Overlay without DAT ingestion with out mapping field selection.");
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		//click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("Native",Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst,Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();
		
	}
	
	/** 
     *Author :Vijaya.Rani date: 27/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49902
	 * Description :Unpublish documents - Verify Search as source.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 13)
	public void verifyUnpublishDocumentsSource() throws InterruptedException  {
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
	
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49902");
		baseClass.stepInfo("Unpublish documents - Verify Search as source.");
		
		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchSharedWithPA(BasicSearchName);
		
		//Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
	}
	
	/** 
     *Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47369
	 * Description :To verify 'Ingestion Details' pop up display
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 14)
	public void verifyIngestionDetailsPopupDisplay() {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47369");
		baseClass.stepInfo("To verify 'Ingestion Details' pop up display");
		ingestionPage.verifyIngestionDetails();
	}
	
	/** 
     *Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47362
	 * Description :To verify that on Ingestion Home page, user is able to access all page by navigation controls.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 15)
	public void verifyIngestionHomePageNavigation() {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47362");
		baseClass.stepInfo("To verify that on Ingestion Home page, user is able to access all page by navigation controls.");
		ingestionPage.verifyHomePageNavigationControl();
	}
	
	/** 
     *Author :Vijaya.Rani date: 29/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49545
	 * Description :Verify that value for all the metadata fields having DATETIME/DATE data type.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 16)
	public void verifyMetaDataFieldsDateTimeInDataType() throws InterruptedException  {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49545");
		baseClass.stepInfo("Verify that value for all the metadata fields having DATETIME/DATE data type.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList=new DocListPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status= ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if(status==false) {
		baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
		ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS",Input.DAT_MMDDYYYY,Input.Natives_MMDDYYYY);
		ingestionPage.IngestionCatlogtoIndexing(Input.HiddenPropertiesFolder);
		ingestionPage.approveAndPublishIngestion(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		//Remove selected Colunm Add new Column
		docList.SelectColumnDisplayByRemovingExistingOnesAddMultiipleColumns();
		}
	
	/** 
     *Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48175
	 * Description :To Verify Rollback In Ingestion for Files having Read Only Attributes.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 17)
	public void verifyRollbackReadOnlyAttribute()  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48175");
		baseClass.stepInfo("To Verify Rollback In Ingestion for Files having Read Only Attributes.");
		ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		// Rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47566
	 * Description :To Verify status update in cataloging, copying, and Indexing Section in Ingestion Details Page
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 18)
	public void verifyStatusUpdateInPopup() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47566");
		baseClass.stepInfo("To Verify status update in cataloging, copying, and Indexing Section in Ingestion Details Page");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1,Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Catalogingblock");
		ingestionPage.ingestionCopying();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Copyingblock");
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Indexingblock");
		//rollback
		ingestionPage.rollBackIngestion();
		
	}
	
	/** 
     *Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49703
	 * Description :Verify if 'Generate Searchable PDFs' is True for TIFF image and document has multi-page TIFF's then in the Copying section,
	 *  Stitching TIFFs details should display before the "Generate Searchable PDFs" row on Ingestion details pop up
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 19)
	public void verifyStitchedTiffBeforeGeneratedSearchablePDFs() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49703");
		baseClass.stepInfo("Verify if 'Generate Searchable PDFs' is True Stitching TIFFs details should display before the Generate Searchable PDFs row on Ingestion details pop up");
		ingestionPage.tiffImagesIngestion(Input.DATFile4,Input.tiffLoadFile,"true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyTermPositionInCopyColumn(Input.StitchedTIFF);
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48194
	 * Description :To Verify In Ingestions the pop-up Details Window for media ;audio & Transcript Count.
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 20)
	public void verifyMediaAndTranscriptDetailInPopup() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		String[] selectedTerm= {"Native","Text","MP3 Variant","Audio Transcript"};
		String[] unselectedTerm = {"PDF",Input.StitchedTIFF,Input.generateSearchablePDF};

		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48194");
		baseClass.stepInfo("To Verify In Ingestions the pop-up Details Window for media ;audio & Transcript Count.");
		ingestionPage.mediaAndTranscriptIngestion(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		for(int i=0;i<=selectedTerm.length-1;i++) {
			ingestionPage.verifyDataPresentInCopyTableColumn(selectedTerm[i],"source");
		}
		for(int i=0;i<=unselectedTerm.length-1;i++) {
			ingestionPage.verifyUnselectedSourceCountInCopySection(unselectedTerm[i]);
		}
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}
	
	/** 
     *Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-47404
	 * Description :To verify that if configure mapping is not matched the warning message is displayed and as per selection, admin can proceed.
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 21)
	public void verifyConfigureMappingWarningMessage() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47404");
		baseClass.stepInfo("verify that if configure mapping is not matched the warning message is displayed and as per selection, admin can proceed.");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1,Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyWarningMessageForConfigureMappingSection(Input.UniCodeFilesFolder,Input.datLoadFile1,Input.textFile1,Input.prodBeg,Input.documentKey);
		
	}
	
	/** 
     *Author :Brundha Test Case Id:RPMXCON-50083
	 * Description :verify Ingestion should published successfully if Email metadata is having only Address
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 19)
	public void verifyEmailAddressInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50083");
		baseClass.stepInfo("verify Ingestion should published successfully if Email metadata is having only Address");
		
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		baseClass.selectproject(Input.ingestionPrjt);
		
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status= ingestionPage.verifyIngestionpublish(Input.nativeFileName);
		System.out.println(status);
		if(status==false) {
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,Input.sourceSystem,Input.datFormatFile,"DAT4_STC_NativesEmailData NEWID.lst","DAT4_STC_TextEmailData NEWID.lst",null,null,null,null,null);
		}
		String[] addEmailColumn = {"EmailAuthorName", "EmailAuthorAddress"};
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.metadataIngestion,Input.nativeFileName);
		sessionSearch.ViewInDocList();
		
		DocListPage doc= new DocListPage(driver);
		baseClass.stepInfo("Verifying Email Metadata in DocList page");
		doc.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		for(String metadata : addEmailColumn) {
			baseClass.visibleCheck(metadata);
		}
		int EmailData = baseClass.getIndex(doc.getHeaderText(),Input.MetaDataEAName);
		doc.GetColumnData(EmailData);
		baseClass.stepInfo("verifying field value in Email Metadata");
		ArrayList<String> selectedDocs = doc.verifyingEmailMetaData(EmailData);
		if(selectedDocs.contains("")) {
			baseClass.passedStep("email author name is blank as expected");
		}else {
			baseClass.failedStep("email author name is not blank as expected");
		}
		int EmailMetaData = baseClass.getIndex(doc.getHeaderText(), "EmailAuthorAddress");
		doc.GetColumnData(EmailMetaData);
		ArrayList<String> selectedDoc = doc.verifyingEmailMetaData(EmailMetaData);
		if(selectedDoc.contains(Input.validationData)) {
			baseClass.passedStep("email author address is displayed with the expected text");
		}else {
			baseClass.failedStep("email author address is not displayed with expected text");
		}
		loginPage.logout();
		
		
		
		
	}
	


/** 
     *Author :Brundha Test Case Id:RPMXCON-49804
	 * Description :To verify that if Email data contained space after the '@' sign , it should not calculate two distinct values
	 *
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 21)
	public void verifyEmailAllDomainDistinctData() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch	sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49804");
		baseClass.stepInfo("To verify that if Email data contained space after the '@' sign , it should not calculate two distinct values");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status= ingestionPage.verifyIngestionpublish(Input.nativeFileName);
		System.out.println(status);
		if(status==false) {
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,Input.sourceSystem,Input.datFormatFile,"DAT4_STC_NativesEmailData NEWID.lst","DAT4_STC_TextEmailData NEWID.lst",null,null,null,null,null);
		}
			
			int count = sessionsearch.MetaDataSearchInBasicSearch(Input.emailAllDomain,"hotmail.com");
			sessionsearch.addNewSearch();
			int count1 = sessionsearch.newMetaDataSearchInBasicSearch(Input.emailAllDomain,"aol.com");
			int DocCount=Integer. parseInt(Input.DocCount);
			if(count!=0 && count==DocCount) {
				baseClass.passedStep("Email MetaData count is displayed as expected");
		}else {
			baseClass.failedStep("The count is not displayed as expected");
		}
			int DocumentCount=Integer. parseInt(Input.DocCount);
			int doc=DocumentCount+1;
					
			if(count1!=0 && count1==doc) {
				baseClass.passedStep("Email MetaData count is displayed as expected");
		}else {
			baseClass.failedStep("The count is not displayed as expected");
		}
		baseClass.passedStep("verified that if Email data contained space before the '@' sign , it should not calculate two distinct values");
		loginPage.logout();
	}
	
	
	
	/** 
     *Author :Brundha Test Case Id:RPMXCON-48173
	 * Description :To Verify for AudioPlayer ready are Nexidia indexed.
	 * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 20)
	public void verifyDocumentInAudioPlayerReady() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48173");
		baseClass.stepInfo("To Verify for AudioPlayer ready are Nexidia indexed.");
		
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		baseClass.selectproject(Input.ingestionPrjt);
		
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status= ingestionPage.verifyIngestionpublish(Input.nativeMp3FileFormat);
		System.out.println(status);
		if(status==false) {
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,Input.sourceSystem,Input.DATFile1,null, null,null,null,Input.MP3File,null,null);
		}
		SessionSearch	sessionsearch = new SessionSearch(driver);
		sessionsearch.SearchMetaData(Input.audioPlayerReady,Input.pageCount);
		sessionsearch.verifyDocCountForAudioPlayerReady();
		loginPage.logout();
		
		
	}
	/**
	*Author :Brundha Test Case Id:RPMXCON-48171
	* Description :Verify that 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with .MP3 files
	* @throws InterruptedException
	*/
	@Test(enabled = true,  groups = {"regression" },priority =21)
	public void verifyDocumentInAudioPlayerReadyValue() throws InterruptedException  {
		baseClass.stepInfo("Test case Id: RPMXCON-48171");
		baseClass.stepInfo("Verify that 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with .MP3 files"); loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status= ingestionPage.verifyIngestionpublish(Input.nativeMp3FileFormat);
		System.out.println(status);
		
		System.out.println(status);
		if(status==false) {
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,Input.sourceSystem,Input.DATFile1,null, null,null,null,Input.MP3File,null,null);
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic content search");
		sessionsearch.basicContentSearch(Input.nativeMp3FileFormat);
		baseClass.stepInfo("Navigating to doclist page");
		sessionsearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		baseClass.waitForElement(doc.getSelectDropDown());
		doc.getSelectDropDown().waitAndClick(10);
		doc.selectingSingleValueInCoumnAndRemovingExistingOne(Input.audioPlayerReady);
		doc.verifyingTheMp3FileAndOtherFile(0);
		int count=Integer.valueOf(Input.pageCount);
		doc.verifyingTheMp3FileAndOtherFile(count);
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			// LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close2() {
		try {
			// LoginPage.clearBrowserCache();
		} catch (Exception e) {
			// no such session
		}

	}
}
