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
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 04/11/2022 TestCase Id:RPMXCON-47411
	 * Description :To verify that pagination is provide to Error details pop 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47411",enabled = true, groups = { "regression" })
	public void verifyPaginationOptionInErrorDetailPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47411");
		baseClass.stepInfo("To verify that pagination is provide to Error details pop");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
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
	public void verifyAbsolutePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60821");
		baseClass.stepInfo("verify the ingestion status if DAT file contain the Absolute path.");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
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
	public void verifyAbsoluteRelativePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60826");
		baseClass.stepInfo("verify the ingestion status of overlay with Dat relative path");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
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
	public void verifyRelativeAbsolutePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60825");
		baseClass.stepInfo("verify the ingestion status of overlay with Dat absolute path");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
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
	public void verifyIngestionHomePageTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47390");
		baseClass.stepInfo("verify that on Ingestion Home page displays default 10 tiles");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
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
