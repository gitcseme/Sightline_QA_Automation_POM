package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_7 {

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
	 * Author :Arunkumar date: 17/10/2022 TestCase Id:RPMXCON-48073
	 * Description :To Verify EmailDateSentTimeOnly in tally and in Search- Both fields should be false by default
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48073",enabled = true, groups = { "regression" })
	public void verifyEmailDateSentTimeOnlyInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48073");
		baseClass.stepInfo("Verify EmailDateSentTimeOnly in tally and in Search.");
		String field = "EmailDateSentTimeOnly";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("go to search and check field availability in metadata");
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.waitForElement(sessionSearch.getBasicSearch_MetadataBtn());
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(10);
		if(sessionSearch.getMetaDataInDropdown(field).isElementAvailable(10)) {
			baseClass.failedStep(field+"is available in basic search metadata dropdown");
		}
		else {
			baseClass.passedStep(field+"is not available in basic search metadata dropdown");
		}
		baseClass.stepInfo("go to report-tally and check field availability in metadata");
		tally = new TallyPage(driver);
		tally.verifyMetaDataUnAvailabilityInTallyReport(field);
		baseClass.stepInfo("Verify the field value of 'EmailDateSentTimeOnly'");
		ProjectFieldsPage projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.applyFilterByFilterName(field);
		projectFieldPage.verifyExpectedFieldStatus(field,"false","false","active");
		baseClass.passedStep(field+"is tallyable and is searchable false by default");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 18/10/2022 TestCase Id:RPMXCON-48080
	 * Description :To Verify ExcelProtectedWorkbook in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48080",enabled = true, groups = { "regression" })
	public void verifyExcelProtectedWorkbookInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48080");
		baseClass.stepInfo("Verify ExcelProtectedWorkbook in Tally and Search.");
		String field = "ExcelProtectedWorkbook";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.HiddenPropertiesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.hiddenPropDat, Input.sourceDocIdSearch);
			baseClass.stepInfo("Selecting Native file");
			ingestionPage.selectNativeSource(Input.hiddenPropNative, false);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeForamt(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Navigate to report-tally and select metadata");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		tally.selectSourceByProject();
		tally.selectTallyByMetaDataField(field);
		baseClass.waitForElement(tally.getTally_btnTallyAll());
		tally.getTally_btnTallyAll().waitAndClick(10);
		HashMap<String, Integer> map = tally.getDocsCountFortallyReport();
		int yesCount = map.get("Yes");
		baseClass.stepInfo("ExcelProtectedWorkbook-yes status count"+yesCount);
		int noCount = map.get("No");
		baseClass.stepInfo("ExcelProtectedWorkbook-No status count"+noCount );
		baseClass.stepInfo("perform search with field and verify purehit count with report");
		int excelprotectCount =sessionSearch.MetaDataSearchInBasicSearch("ExcelProtectedWorkbook", "Yes");
		baseClass.selectproject();
		int excelNotProtectCount =sessionSearch.MetaDataSearchInBasicSearch("ExcelProtectedWorkbook", "No");
		if(excelprotectCount==yesCount && excelNotProtectCount==noCount) {
			baseClass.passedStep(field + "Counts matched for search result and tally report");
		}
		else {
			baseClass.failedStep(field + "Counts not matched for search result and tally report");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 18/10/2022 TestCase Id:RPMXCON-48074
	 * Description :To Verify FileDescription in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48074",enabled = true, groups = { "regression" })
	public void verifyFileDescriptionInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48074");
		baseClass.stepInfo("Verify FileDescription in Tally and Search.");
		String field = "FileDescription";
		String fileType= "\""+"Microsoft Word Document"+"\"";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally and select metadata");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		tally.selectSourceByProject();
		tally.selectTallyByMetaDataField(field);
		baseClass.waitForElement(tally.getTally_btnTallyAll());
		tally.getTally_btnTallyAll().waitAndClick(10);
		HashMap<String, Integer> map = tally.getDocsCountFortallyReport();
		int tallyCount = map.get("Microsoft Word Document");
		baseClass.stepInfo("FileDescription-"+fileType+ "count"+tallyCount);
		baseClass.stepInfo("perform search with field and verify purehit count with report");
		int purehitCount =sessionSearch.MetaDataSearchInBasicSearch(field, fileType);
		if(purehitCount==tallyCount) {
			baseClass.passedStep(field+"Counts matched for search result and tally report");
		}
		else {
			baseClass.failedStep(field+"Counts not matched for search result and tally report");
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
