package testScriptsRegressionSprint26;

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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_9 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	SecurityGroupsPage securityGroup;
	Input ip;
	ProjectPage project;
	TallyPage tally;
	DocExplorerPage docExplorer;
	DocViewPage docView;
	ProjectFieldsPage projectFieldPage;

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
	 * Author :Arunkumar date: 18/11/2022 TestCase Id:RPMXCON-48633
	 * Description :To verify that after text overlay PA can rerun the analytics at security group successfully
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48633",enabled = true, groups = { "regression" })
	public void TCB1verifyRerunAnalyticsAtSecurityGroup() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48633");
		baseClass.stepInfo("verify that after text overlay PA can rerun the analytics at security group successfully");
		String ingestionName = null;
		String BasicSearchName = "Search"+ Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Release docs to security group");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.stepInfo("unrelease docs and unpublish the text file");
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay with unpublised text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1,
				Input.documentKey, "text", Input.textFile1);
		String ingestionName1 =ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("Release documents to security group");
		baseClass.selectproject();
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName1);
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.stepInfo("navigate to manage-security group");
		securityGroup = new SecurityGroupsPage(driver);
		securityGroup.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("Regenerate the run anaytics at SG level");
		securityGroup.regenerateAnalyticsAtSgLevel();
		loginPage.logout();			
	}
	
	/**
	 * Author :Arunkumar date: 21/11/2022 TestCase Id:RPMXCON-54551
	 * Description :To verify that if document is set to TRUE for ‘Require PDF Generation’ metadata 
	 * and if it is fails then Error count should be displayed
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54551",enabled = true, groups = { "regression" })
	public void TCB2verifyErrorDisplayedOnCopyingStage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54551");
		baseClass.stepInfo("verify error if document is set to TRUE for ‘Require PDF Generation’.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with ice source system");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if (status == false) {
			ingestionPage.performGNonSearchableFolderIngestion(Input.ingestionType, Input.iceSourceSystem,
					Input.nonSearchablePdfLoadFile, Input.selectNativeFile, Input.selectTextFile);
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
			ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
			//verify details under copy section
			ingestionPage.verifyGenerateSearchablePdfValueInCopy(Input.generateSearchablePDF);
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
		//if add only ingestion already present,then will get the data from grid table
		ingestionPage.getIngestionDetailFromGrid(Input.GNon_searchable_PDF_Load_file);
		ingestionPage.verifyGenerateSearchablePdfValueInCopy(Input.generateSearchablePDF);
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/11/2022 TestCase Id:RPMXCON-54560
	 * Description :To verify that PA can Rollback the Ingestion once copy is completed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54560",enabled = true, groups = { "regression" })
	public void TCB3verifyRollbackOnceCopyCompleted() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54560");
		baseClass.stepInfo("To verify that PA can Rollback the Ingestion once copy is completed");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if (status == false) {
		ingestionPage.performGNonSearchableFolderIngestion(Input.ingestionType, Input.sourceSystem,
				Input.nonSearchablePdfLoadFile, Input.selectNativeFile, Input.selectTextFile);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		baseClass.stepInfo("verify searchable pdf data after copy completed");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		driver.waitForPageToBeReady();
		ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "true");
		//perform rollback once copy is completed
		ingestionPage.rollBackIngestionUsingActionMenu();
		}
		else {
			baseClass.failedMessage("unable to perform rollback as add only ingestion for this "
					+ "source folder already present in the project");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 23/11/2022 TestCase Id:RPMXCON-60815
	 * Description :Verify that if the Absolute path is present in the PDF LST, then Ingestion 
	 * should be successful.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60815",enabled = true, groups = { "regression" })
	public void TCB5verifyAbsolutePathInPdf() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60815");
		baseClass.stepInfo("To verify that if the Absolute path is present in the PDF LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with PDF");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting Pdf file");
			ingestionPage.selectPDFSource(Input.uncAbsolutePdf, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 23/11/2022 TestCase Id:RPMXCON-60814
	 * Description :Verify that if the Absolute path is present in the Native LST, then Ingestion
	 * should be successful.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60814",enabled = true, groups = { "regression" })
	public void TCB4verifyAbsolutePathInNative() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60814");
		baseClass.stepInfo("To verify that if the Absolute path is present in the Native LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with Native");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting Native file");
			ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 23/11/2022 TestCase Id:RPMXCON-60820
	 * Description :Verify that Ingestion should be successful if the PDF LST contains the Absolute 
	 * path and the Native LST contains the Relative path. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60820",enabled = true, groups = { "regression" })
	public void TCB6verifyAbsoluteAndRelativePathFile() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60820");
		baseClass.stepInfo("To verify that if the Absolute path in Native and Relative in Native LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with Pdf and Native");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting relative Native file");
			ingestionPage.selectNativeSource(Input.uncRelativeNative, false);
			baseClass.stepInfo("Selecting absolute Pdf file");
			ingestionPage.selectPDFSource(Input.uncAbsolutePdf, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-49553
	 * Description :Verify that Ingestion Email Metadata 'EmailAuthorNameAndAddresses' is available.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49553",enabled = true, groups = { "regression" })
	public void TCA5verifyEmailAuthorMetadataAvailability() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49553");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailAuthorNameAndAddresses' is available");
		String data ="EmailAuthorNameAndAddress";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		//add dataset details and click next
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Adding ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		//Verify destination field 'EmailAuthorNameAndAddresses' metadata
		baseClass.stepInfo("Select 'EMAIL' in field category");
		baseClass.waitForElement(ingestionPage.getMappingCategoryField(5));
		ingestionPage.getMappingCategoryField(5).selectFromDropdown().selectByVisibleText(Input.email);
		baseClass.stepInfo("verify metadata");
		ingestionPage.verifyMetadataAvailability(ingestionPage.getMappingDestinationField(5), data);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-47427
	 * Description :To Verify Add New Ingestion in Ingestion Wizard.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47427",enabled = true, groups = { "regression" })
	public void TCA6verifySectionAvailableInIngestionWizard() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47427");
		baseClass.stepInfo("To Verify Add New Ingestion in Ingestion Wizard.");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Click on add new ingestion in home page");
		ingestionPage.ClickOnAddNewIngestionLink();
		ingestionPage.verifySourceAndMappingSectionStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-49505
	 * Description :Verify the default value for the 'Date & Time Format' field
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49505",enabled = true, groups = { "regression" })
	public void TCA7verifyDefaultSelectionForDateFormat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49505");
		baseClass.stepInfo("Verify the default value for the 'Date & Time Format' field");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		//verify default selection value for date-time format
		ingestionPage.verifyDateFormatDropDownValidations();
		baseClass.passedStep("No specific date format selected in date and time format field");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-63250
	 * Description :Validate whether user do not get any error message during "Add only" ingestion 
	 * type when similar source dat field are mapped twice with different destination dat field.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63250",enabled = true, groups = { "regression" })
	public void TCA8verifySimilarSourceFieldErrorWhenAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63250");
		baseClass.stepInfo("Verify saving ingestion when repeated source dat field mapping during overlay only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, 
				Input.sourceLocation, Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource("AllFieldsMapping_DAT_SourceDocID.dat", Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform similar mapping on source field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("sourceField");
		baseClass.passedStep("User able to save ingestion successfully as draft when mapping similar source field");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 25/11/2022 TestCase Id:RPMXCON-63245
	 * Description :Validate whether all the DAT fields from DAT File selected during "Add only" ingestion 
	 * is displayed under Source DAT fields in Configure Field Mapping of Ingestion Wizard Page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63245",enabled = true, groups = { "regression" })
	public void TCA1verifyDatFieldsInMappingWhenAddOnly() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-63245");
		baseClass.stepInfo("Verify DAT fields in configure mapping page.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with type as 'add only'");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, 
				Input.sourceLocation, Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource("AllFieldsMapping_DAT_ProdBeg.dat", Input.prodBeg);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify dat fields available in source field mapping in configuring page" );
		ingestionPage.verifyDatFieldsAvailableInMappingSection();
		baseClass.passedStep("All the DAT fields available in configure mapping page");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/11/2022 TestCase Id:RPMXCON-63249
	 * Description :Validate whether user gets error message during ingestion when similar Destination 
	 * field is mapped twice with different source dat field 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63249",enabled = true, groups = { "regression" })
	public void TCA2verifySimilarDestinationFieldErrorWhenAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63249");
		baseClass.stepInfo("Verify error message when similar destination field mapped twice.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, 
				Input.sourceLocation, "RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource("AllFieldsMapping_DAT_SourceDocID.dat", Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform similar mapping on destination field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("destinationField");
		baseClass.passedStep("User gets the error message when mapping similar destination field for Add only ingestion");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-48169
	 * Description :Verify the type for the "AudioPlayerReady" is of Bit (values 1 or 0) and field is 
	 * Active by default, and IsSet and IsSearchable are enabled by default
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48169",enabled = true, groups = { "regression" })
	public void TCA3verifyAudioPlayerReadyField() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48169");
		baseClass.stepInfo("Verify 'AudioPlayerReady' field default status.");
		String field = "AudioPlayerReady";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		baseClass.stepInfo("Navigate to manage-project fields");
		projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.navigateToProjectFieldsPage();
		baseClass.stepInfo("check the default value for 'AudioPlayerReady'");
		projectFieldPage.applyFilterByFilterName(field);
		projectFieldPage.verifyFieldDataType(field, "Bit");
		projectFieldPage.verifyExpectedFieldStatus(field,"true","true","active");
		baseClass.passedStep(field+"is tallyable and is searchable by default");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-48930
	 * Description :Validate new metadata field DocLanguages on DocList  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48930",enabled = true, groups = { "regression" })
	public void TCB7validateDocLanguagesFieldOnDoclist() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48930");
		baseClass.stepInfo("Validate new metadata field DocLanguages on DocList");
		docExplorer = new DocExplorerPage(driver);
		String ingestionName = null;
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//publishing docs as pre-requisite
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, 
					Input.prodBeg);
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(Input.securityGroup);
		//login as PA and verify
		baseClass.stepInfo("Navigate to doclist and verify");
		docExplorer.navigateToDocExplorerPage();
		int docsCount=docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "yes", "doclist");
		ingestionPage.verifyDocLanguagesMetadata("doclist",docsCount);
		loginPage.logout();
		//login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Navigate to doclist and verify");
		int count =sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		ingestionPage.verifyDocLanguagesMetadata("doclist",count);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-48929
	 * Description :Validate new metadata field DocLanguages on DocView  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48929",enabled = true, groups = { "regression" })
	public void TCB8validateDocLanguagesFieldOnDocview() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48929");
		baseClass.stepInfo("Validate new metadata field DocLanguages on DocView");
		docExplorer = new DocExplorerPage(driver);
		String ingestionName = null;
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//publishing docs as pre-requisite
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, 
					Input.prodBeg);
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(Input.securityGroup);
		//login as PA and verify
		baseClass.stepInfo("Navigate to docview and verify");
		docExplorer.navigateToDocExplorerPage();
		int docsCount = docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "yes", "docview");
		ingestionPage.verifyDocLanguagesMetadata("docview",docsCount);
		loginPage.logout();
		//login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Navigate to docview and verify");
		int count =sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		ingestionPage.verifyDocLanguagesMetadata("docview",count);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-47412
	 * Description :To verify that Admin is able to browser all the Errors using navigation control
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47412",enabled = true, groups = { "regression" })
	public void TCA9verifyAdminBrowseAllErrorsUsingNavigation() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47412");
		baseClass.stepInfo("To verify that Admin is able to browser all the Errors using navigation control");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.attachDocFolder, Input.datFile7);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.verifybrowseErrorNavigationControl();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/11/2022 TestCase Id:RPMXCON-48927
	 * Description :Validate new metadata field DocLanguages on Tally report  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48927",enabled = true, groups = { "regression" })
	public void TCB9validateDocLanguagesFieldOnTally() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48927");
		baseClass.stepInfo("Validate new metadata field DocLanguages on Tally report");
		String metadata="DocLanguages";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//publishing docs as pre-requisite
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, 
					Input.prodBeg);
			baseClass.stepInfo("Publish add only ingestion");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkRelease(Input.securityGroup);
		//login as PA and verify
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyMetaDataAvailabilityInTallyReport(metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		loginPage.logout();
		//login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyMetaDataAvailabilityInTallyReport(metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"security Group");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/11/2022 TestCase Id:RPMXCON-47306
	 * Description :To verify that Counts displayed on Tiles on Ingestion home page are correct.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47306",enabled = true, groups = { "regression" })
	public void TCA4verifyCountsDisplayedOnTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47306");
		baseClass.stepInfo("verify Counts displayed on Tiles on Ingestion home page are correct.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("add new ingestion details");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		baseClass.stepInfo("verify details present in ingestion tile");
		ingestionPage.verifyContentOnIngestionTiles();  
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
