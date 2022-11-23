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
	public void verifyRerunAnalyticsAtSecurityGroup() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48633");
		baseClass.stepInfo("verify that after text overlay PA can rerun the analytics at security group successfully");
		String ingestionName = null;
		String BasicSearchName = "Search"+ Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void verifyErrorDisplayedOnCopyingStage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54551");
		baseClass.stepInfo("verify error if document is set to TRUE for ‘Require PDF Generation’.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
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
	public void verifyRollbackOnceCopyCompleted() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54560");
		baseClass.stepInfo("To verify that PA can Rollback the Ingestion once copy is completed");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
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
	public void verifyAbsolutePathInPdf() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60815");
		baseClass.stepInfo("To verify that if the Absolute path is present in the PDF LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
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
	public void verifyAbsolutePathInNative() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60814");
		baseClass.stepInfo("To verify that if the Absolute path is present in the Native LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
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
	public void verifyAbsoluteAndRelativePathFile() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60820");
		baseClass.stepInfo("To verify that if the Absolute path in Native and Relative in Native LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
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
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
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
