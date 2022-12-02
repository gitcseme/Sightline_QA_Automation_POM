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
import pageFactory.ProjectFieldsPage;
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
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 01/08/2022 TestCase Id:RPMXCON-47291
	 * Description :Single/Multiple DAT file is Available.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47291",enabled = true, groups = { "regression" })
	public void TCA1verifyValuesInIngestedDataset() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47291");
		baseClass.stepInfo("Single/Multiple DAT file is Available.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			baseClass.stepInfo("Add new ingestion with dat file");
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.ingestionCatalogging();
			baseClass.passedStep("Ingestion executed successfully");
			ingestionPage.rollBackIngestion();
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 02/08/2022 TestCase Id:RPMXCON-47580
	 * Description :To Verify: No of Headers in DAT and No. of headers in Destination field in Configure mapping page. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47580",enabled = true, groups = { "regression" })
	public void TCA2verifyNumberOfHeaderInDatAndDestinationField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47580");
		baseClass.stepInfo("Verify: No of Headers in DAT and No. of headers in Destination field in Configure mapping page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	public void TCA3verifyIngestionRecordPreviewDetails() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47302");
		baseClass.stepInfo("Verify the Preview of Ingestion display for first 50 records with valid inputs.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on Next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton(); 
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		baseClass.stepInfo("verify 50 record preview details in popup");
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		ingestionPage.validateRecordsCountInIngestionPreviewScreen();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-63254
	 * Description :Validate whether the user gets the error message during "Overlay" ingestion 
	 * that contains Destination field name's that are mapped repeatedly. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63254",enabled = true, groups = { "regression" })
	public void TCA4verifyRepeatedDestinationFieldErrorWhenOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63254");
		baseClass.stepInfo("Verify repeated destination field error during overlay only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation, 
				"RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile5, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform repeated mapping on destination field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("destinationField");
		baseClass.passedStep("User gets the error message when mapping same destination field repeatedly for overlay only ingestion");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-63255
	 * Description :Validate whether user do not get any error message during "Overlay" ingestion type when 
	 * similar source dat field are mapped twice with different destination dat field.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63255",enabled = true, groups = { "regression" })
	public void TCA5verifyRepeatedSourceFieldErrorWhenOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63255");
		baseClass.stepInfo("Verify saving ingestion when repeated source dat field mapping during overlay only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation, "RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile5, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform repeated mapping on source field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("sourceField");
		baseClass.passedStep("User able to save ingestion successfully as draft when mapping same source field repeatedly");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-63256
	 * Description :Validate whether the user gets the error message during "Add ONLY" ingestion type that 
	 * contains Destination field name's that are mapped repeatedly.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63256",enabled = true, groups = { "regression" })
	public void TCA6verifyRepeatedDestinationFieldErrorWhenAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63256");
		baseClass.stepInfo("Verify repeated destination field error during add only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, Input.sourceLocation, "RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile5, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform repeated mapping on destination field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("destinationField");
		baseClass.passedStep("User gets the error message when mapping same destination field repeatedly for Add only ingestion");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 16/08/2022 TestCase Id:RPMXCON-47344
	 * Description :To verify that ingestion which is Rolled back can be deleted once it is in Draft mode. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47344",enabled = true, groups = { "regression" })
	public void TCA7verifyDeletingIngestionInDraftMode() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47344");
		baseClass.stepInfo("verify that ingestion which is Rolled back can be deleted once it is in Draft mode");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
		baseClass.stepInfo("perform add only ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.ignoreErrorsAndCatlogging();
		baseClass.stepInfo("Click on settings and perform Rollback option");
		ingestionPage.rollBackIngestion();
		ingestionPage.VerifyDraftIngestionStatusAfterRollback();
		baseClass.stepInfo("verify available settings option");
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
		baseClass.stepInfo("Delete ingestion");
		ingestionPage.getRefreshButton().waitAndClick(5);
		ingestionPage.deleteIngestion();
		baseClass.passedStep("Verified that user can delete the ingestion which is in draft mode");
		loginPage.logout();
		}
	}
	
	
	
	/**
	 * Author :Arunkumar date: 11/08/2022  TestCase Id:RPMXCON-48958 
	 * Description :Verify user should be able to run couple of new ingestion simultaneously and make sure no Indexing is failed
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48958",enabled = true, groups = { "regression" } )
	public void TCA8verifyCoupleOfIngestionsRunSimultaneously() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48958");
		baseClass.stepInfo("Verify user should be able to run couple of new ingestion simultaneously");
		String[] dataset = { Input.UniCodeFilesFolder, Input.HiddenPropertiesFolder };

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status1 = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		ingestionPage.navigateToIngestionPage();
		boolean status2 = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status1 == false && status2 == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			driver.waitForPageToBeReady();
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.multipleIngestionCopying(2);
			ingestionPage.multipleIngestionIndexing(dataset, 2);
			ingestionPage.approveMultipleIngestion(2);
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			baseClass.failedMessage("Required Dataset already ingested in this project,unable to ingest again simultaneously");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 17/08/2022 TestCase Id:RPMXCON-48071
	 * Description :To Verify Field ParentDocID ,HeadOfHouseholdDocID and FamilyID in Ingested Data Set.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48071",enabled = true, groups = { "regression" })
	public void TCA9verifyFieldValuesInIngestedDataset() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48071");
		baseClass.stepInfo("Verify fields in ingested dataset.");
		String[] values = {"ParentDocID","HeadOfHouseholdDocID","FamilyID"};
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Perform search and go to doclist");
		int docsCount=sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Verify values displayed in selected column");
		ingestionPage.verifyValuesDisplayedInSelectedColumnsDoclist(docsCount,values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/08/2022 TestCase Id:RPMXCON-49661
	 * Description :Validate Add Only ingestion with DAT file in ASCII format
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49661",enabled = true, groups = { "regression" })
	public void TCB1verifyAddOnlyIngestionInASCII() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49661");
		baseClass.stepInfo("Validate Add Only ingestion with DAT file in ASCII format.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-48003
	 * Description :To Verify email metadata field is populated correctly for ingested data 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48003",enabled = true, groups = { "regression" })
	public void TCB2verifyEmailMetaDataField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48003");
		baseClass.stepInfo("To Verify email metadata field is populated correctly for ingested data");
		String ingestionName = null;
		String[] values = {"EmailAllDomainCount","EmailAllDomains","EmailAuthorDomain","EmailRecipientNames",
				"EmailToAddresses","EmailToNames" , "EmailRecipientDomainCount"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile2,Input.nativeLoadFile2,Input.textLoadFile2);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Search document and go to doclist");
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		baseClass.stepInfo("Verify values displayed on the selected Coulumns");
		docList.SelectColumnDisplayByRemovingAddNewValues(values);
		for(int j =4;j<=values.length+4;j++) {
			String data =docList.getDataInDoclist(1,j).getText();
			if(docList.getDataInDoclist(1,j).isDisplayed() && !data.isEmpty()) {
				baseClass.passedStep("values displayed in selected column");
			}
			else {
				for(int i=2;i<=values.length;i++) {
					String datavalue =docList.getDataInDoclist(i,j).getText();
					if(docList.getDataInDoclist(i,j).isDisplayed() && !datavalue.isEmpty()) {
						baseClass.passedStep("values displayed in selected column");
						break;
					}
				}
			}
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 17/08/2022 TestCase Id:RPMXCON-48002
	 * Description :To Verify EmailDuplicateDocID field is populated correctly for ingested data
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48002",enabled = true, groups = { "regression" })
	public void TCB3verifyEmailMetaDataFieldPopulatedForIngestedData() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48002");
		baseClass.stepInfo("To Verify EmailDuplicateDocID field is populated correctly for ingested data");
		String ingestionName = null;
		String[] values = {"EmailDuplicateDocIDs", "EmailIsDuplicate"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		if (status == false) {
			ingestionPage.performAutomationCollection1kIngestion(Input.sourceSystem,Input.DATFile2,Input.textFile1);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.Collection1KFolder);
		}
		baseClass.stepInfo("Search document and go to doclist");
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Verify values displayed in selected column");
		ingestionPage.verifyValuesDisplayedInSelectedColumnsDoclist(docsCount,values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/08/2022 TestCase Id:RPMXCON-47370
	 * Description :To verify that user is not able to search the Ingested data if Ingestion is in Draft Mode.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47370",enabled = true, groups = { "regression" })
	public void TCB4verifySearchIngestionWhichIsInDraftMode() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47370");
		baseClass.stepInfo("verify user is not able to search the data if Ingestion is in Draft Mode.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.HiddenPropertiesFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("save ingestion as draft");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		String ingestionName =ingestionPage.getIngestionNameFromPopup();
		baseClass.stepInfo("search ingestion data which is in draft mode");
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		if(docsCount==0) {
			baseClass.passedStep("User unable to search the ingestion data which is in draft mode");
		}
		else {
			baseClass.failedStep("user able to search/view the ingestion data in draft mode");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/08/2022 TestCase Id:RPMXCON-48079
	 * Description :To Verify HiddenProperties in Tally and Search
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48079",enabled = true, groups = { "regression" })
	public void TCB5verifyHiddenPropertiesInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48079");
		baseClass.stepInfo("Verify HiddenProperties in Tally and Search.");
		String field = "HiddenProperties";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			baseClass.stepInfo("perform add only ingestion for hiddenproperties");
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Verify the default field value of 'HiddenProperties'");
		ProjectFieldsPage projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.applyFilterByFilterName(field);
		projectFieldPage.verifyFieldStatus(field);
		projectFieldPage.verifyFieldClassification(field, Input.docBasic);
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
