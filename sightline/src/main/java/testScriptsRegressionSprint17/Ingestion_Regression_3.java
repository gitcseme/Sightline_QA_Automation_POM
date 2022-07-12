package testScriptsRegressionSprint17;

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
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_3 {

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
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-48291
	 * Description :To verify In Ingestion, Copy ,ASCII(59) should be default New Line delimiter.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48291",enabled = true, groups = { "regression" })
	public void verifyDefaultNewLineDelimiter() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48291");
		baseClass.stepInfo("verify In Ingestion, Copy ,ASCII(59) should be default New Line delimiter.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion with value ASCII(59)");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.UniCodeFilesFolder);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile1, Input.documentKey);
		baseClass.waitForElement(ingestionPage.getDateFormat());
		ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.documentKey,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("Perform catalog,copy and indexing");
		ingestionPage.ingestionCatalogging();
		ingestionPage.ingestionCopying();
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		baseClass.stepInfo("Copy the ingestion and check default value in ingestion wizard");
		driver.waitForPageToBeReady();
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionDropdownArrow());	
		ingestionPage.getActionDropdownArrow().waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionCopy());
		ingestionPage.getActionCopy().waitAndClick(5);
		ingestionPage.verifyDefaultValueOfDelimiter();
		}
		loginPage.logout();
	}
	
	/** 
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-48626
	 * Description :To verify that after the publish is been done,user can view the search result for overlaid text
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48626",enabled = true, groups = { "regression" })
	public void verifyResultOfOverlaidText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48626");
		baseClass.stepInfo("verify that after the publish is been done,user can view the search result for overlaid text");
		docList= new DocListPage(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Search text file and save the result");
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.UniCodeFilesFolder);
		String docId=docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, Input.docId);
		sessionSearch.saveSearch(BasicSearchName);
		baseClass.stepInfo("Unpublish the text file");
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("perform overlay ingestion with unpublished text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey,
								"text", Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("search for overlaid text file");
		baseClass.selectproject();
		sessionSearch.basicSearchWithMetaDataQuery(docId, Input.docId);
		int Count =Integer.parseInt(sessionSearch.getPureHitsCount().getText());
		if(Count==1) {
			baseClass.passedStep("user can view the search result of text file after publish is done");
		}
		else {
			baseClass.failedStep("user unable to view the search result of text file after publish is done");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-47824
	 * Description :Verify overlay of the same files, which are already ingested and available in Production DB.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47824",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionOfSameFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47824");
		baseClass.stepInfo("verify overlay ingestion of the same files, which are already ingested");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAutomationAllsourcesIngestion(Input.DATFile1, Input.prodBeg);
		ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion for same files");
		ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,Input.overlayOnly,null,Input.DATFile1,
				Input.NativeFile,null,Input.PDFFile,Input.TIFFFile,null,null,null,null);
		baseClass.passedStep("Ingestion overlay done successfully for the same files");
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
