package testScriptsRegressionSprint25;

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
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_8 {

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
	 * Author :Arunkumar date: 04/11/2022 TestCase Id:RPMXCON-47411
	 * Description :To verify that pagination is provide to Error details pop 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47411",enabled = true, groups = { "regression" })
	public void TCA1verifyPaginationOptionInErrorDetailPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47411");
		baseClass.stepInfo("To verify that pagination is provide to Error details pop");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.HiddenPropertiesFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.DAT_MMDDYYYY, Input.sourceDocIdSearch);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		//verify pagination
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		baseClass.waitForElement(ingestionPage.errorCountCatalogingStage());
		ingestionPage.errorCountCatalogingStage().waitAndClick(10);
		baseClass.waitForElement(ingestionPage.getErrorPagination());
		if(ingestionPage.getErrorPagination().isElementAvailable(10)) {
			baseClass.passedStep("Error popup displayed with pagination option");
		}
		else {
			baseClass.failedStep("pagination option not provided in error popup");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 04/11/2022 TestCase Id:RPMXCON-60821
	 * Description :Verify that if LST file contain the Absolute path then Ingestion Overlay 
	 * should be completed successfully  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60821",enabled = true, groups = { "regression" })
	public void TCA2verifyAbsolutePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60821");
		baseClass.stepInfo("verify the ingestion status if DAT file contain the Absolute path.");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("select lst only for native");
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
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
		baseClass.stepInfo("select lst only for native");
		ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 07/11/2022 TestCase Id:RPMXCON-60826
	 * Description :Verify if Ingestion is completed with Absolute path in DAT then Ingestion Overlay 
	 * should completed with DAT having Relative path 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60826",enabled = true, groups = { "regression" })
	public void TCA3verifyAbsoluteRelativePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60826");
		baseClass.stepInfo("verify the ingestion status of overlay with Dat relative path");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("select 'is path in dat' for native");
			ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
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
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.relativeOverlayDat, Input.documentKey);
		baseClass.stepInfo("select 'is path in dat' for native");
		ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 07/11/2022 TestCase Id:RPMXCON-60825
	 * Description :Verify if Ingestion is completed with Relative path in DAT then Ingestion Overlay 
	 * should completed with DAT having absolute path
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60825",enabled = true, groups = { "regression" })
	public void TCA4verifyRelativeAbsolutePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60825");
		baseClass.stepInfo("verify the ingestion status of overlay with Dat absolute path");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncRelativeDat, Input.documentKey);
			baseClass.stepInfo("select 'is path in dat' for native");
			ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
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
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.absoluteOverlayDat, Input.documentKey);
		baseClass.stepInfo("select 'is path in dat' for native");
		ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/11/2022 TestCase Id:RPMXCON-47390
	 * Description :To verify that on Ingestion Home page displays default 10 tiles.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47390",enabled = true, groups = { "regression" })
	public void TCA5verifyIngestionHomePageTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47390");
		baseClass.stepInfo("verify that on Ingestion Home page displays default 10 tiles");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Select all filters");
		ingestionPage.applyFilters();
		//verify default tiles count
		int tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.passedStep("Number of tiles present for all filters selection: "+tilesCount);
		if(tilesCount<=10) {
			baseClass.passedStep("ingestion home page displays default limit of 10 tiles");
		}
		else {
			baseClass.failedStep("Ingestion home page not displayed default 10 tiles");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-48078
	 * Description :To verify Tally report should be generated with Metadata 'ExceptionResolution'
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48078",enabled = true, groups = { "regression" })
	public void TCA6verifyTallyReportForExceptionResolution() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48078");
		baseClass.stepInfo("verify Tally report should be generated with Metadata 'ExceptionResolution'");
		String metadata ="ExceptionResolution";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-48081
	 * Description :To Verify AllCustodians in Tally and Search
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48081",enabled = true, groups = { "regression" })
	public void TCA7verifyTallyReportForAllCustodians() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48081");
		baseClass.stepInfo("To Verify AllCustodians in Tally and Search");
		String metadata ="AllCustodians";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		baseClass.stepInfo("perform tally and search for 'AllCustodians' metadata");
		tally.performTallyAndSearchForAllCustodians();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 10/11/2022 TestCase Id:RPMXCON-60822
	 * Description :Verify that Ingestion Overlay with text should be completed successfully 
	 * if the text LST file contains the Absolute path.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60822",enabled = true, groups = { "regression" })
	public void TCB1verifyAbsolutePathTextFileIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60822");
		baseClass.stepInfo("verify the ingestion status of overlay with text file contain absolute path");
		String ingestionName = null;
		String searchName = "search"+ Utility.dynamicNameAppender();
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("select Lst for native");
			ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
			baseClass.stepInfo("select Lst for text");
			ingestionPage.selectTextSource(Input.uncAbsoluteText, false);
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
		//go to docexplorer and view in docview
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//unpublish docs
		baseClass.stepInfo("search and unpublish text files");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.saveSearch(searchName);
		ingestionPage.unpublish(searchName);
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.absoluteOverlayDat, Input.documentKey);
		baseClass.stepInfo("select lst only for text");
		ingestionPage.selectTextSource(Input.absoluteOverlayText, false);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/11/2022 TestCase Id:RPMXCON-48082
	 * Description :To Verify ReviewExportID in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48082",enabled = true, groups = { "regression" })
	public void TCA8verifyTallyReportForReviewExportID() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48082");
		baseClass.stepInfo("To Verify ReviewExportID in Tally and Search.");
		String metadata ="ReviewExportID";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation and search for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		tally.performTallyAndSearchForMetadata(metadata);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/11/2022 TestCase Id:RPMXCON-48270
	 * Description :To Verify NUIX created DATA, Ingestion should not failed In Approve for 
	 * DAT Delimiters "New Line" ASCII 174
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48270",enabled = true, groups = { "regression" })
	public void TCA9verifyNuixCreatedDataIngestion() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48270");
		baseClass.stepInfo("verify that Ingestion should not failed In Approve stage");
		String ingestionName = null;
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		boolean status = ingestionPage.verifyIngestionpublish(Input.H13696smallSetFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix,
					Input.sourceLocation, Input.H13696smallSetFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.smallSetDat, Input.sourceDocIdSearch);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.runFullAnalysisAndPublish();
			baseClass.stepInfo("Nuix data Ingestion Name :"+ingestionName);
			baseClass.passedStep("Ingestion not failed in any stage and published successfully");
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.H13696smallSetFolder);
			baseClass.failedMessage("Ingestion already present in the project-"+ingestionName);
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 15/11/2022 TestCase Id:RPMXCON-54550
	 * Description :To verify that if docs have 'Require PDF Generation' field is blank then it will 
	 * not be considered for OCRing and searchable PDF creation.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54550",enabled = true, groups = { "regression" })
	public void TCB2verifySearchablePdfCountInCopyingStage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54550");
		baseClass.stepInfo("verify that if docs have 'Require PDF Generation' field is blank");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.iceSourceSystem, Input.datLoadFile2,
					Input.nativeLoadFile2, Input.textLoadFile2);
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
			ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
			ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			ingestionPage.getIngestionDetailFromGrid(Input.GD994NativeTextForProductionFolder);
			ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
			baseClass.passedStep("searchable pdf not considered when field is blank");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/11/2022 TestCase Id:RPMXCON-54549
	 * Description :To verify that if docs have 'Require PDF Generation' field set to FALSE will not 
	 * be considered for OCRing and searchable PDF creation.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54549",enabled = true, groups = { "regression" })
	public void TCB3verifyRequiredPdfGenerationWithFalse() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54549");
		baseClass.stepInfo("verify that if docs have 'Require PDF Generation' field set to FALSE");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
		ingestionPage.performGD_994NativeFolderIngestion(Input.iceSourceSystem, Input.datLoadFile2,
				Input.nativeLoadFile2, Input.textLoadFile2);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
		ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			//if add-only ingestion already present ,will get the data from ingestion grid
			ingestionPage.getIngestionDetailFromGrid(Input.GD994NativeTextForProductionFolder);
			ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
			baseClass.passedStep("searchable pdf not considered when field is false");
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
