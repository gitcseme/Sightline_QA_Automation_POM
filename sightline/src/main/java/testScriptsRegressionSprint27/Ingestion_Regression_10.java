package testScriptsRegressionSprint27;

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

public class Ingestion_Regression_10 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
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
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 05/12/2022 TestCase Id:RPMXCON-47400
	 * Description :To verify Preview Records pop up display.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47400",enabled = true, groups = { "regression" })
	public void verifyPreviewRecordIngestionPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47400");
		baseClass.stepInfo("verify Preview Records pop up display.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);
		baseClass.stepInfo("perform mapping and verify mapping section");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		ingestionPage.verifyAutoPopulatedSourceFieldInMappingSection();
		baseClass.passedStep("Source field gets auto populated as per the fields available in the DAT file.");
		//preview record popup verification
		baseClass.stepInfo("verify values present in preview record popup and click on back");
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		baseClass.elementNotdisplayed(ingestionPage.previewRecordPopup(), "preview record popup");
		baseClass.passedStep("Preview record popup closed successfully without any errors");
		baseClass.stepInfo("Run ingestion and verify URL");
		String UrlBeforeRun = driver.getUrl();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		String UrlAfterRun = driver.getUrl();
		baseClass.textCompareNotEquals(UrlBeforeRun, UrlAfterRun, "URL changed after running ingestion", 
				"Url not changed after run ingestion");
		baseClass.verifyUrlLanding(Input.url + "en-us/Ingestion/Home", 
				"Navigated to Ingestion home page from wizard page successfully", "not in ingestion home page");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/12/2022 TestCase Id:RPMXCON-47567
	 * Description :To Verify Ingestion Status in Ingestion Detail Page .
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47567",enabled = true, groups = { "regression" })
	public void verifyIngestionStatusInDetailPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47567");
		baseClass.stepInfo("To Verify Ingestion Status in Ingestion Detail Page .");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		//selecting ingestion project
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with dat and text file");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.attachDocFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datFile7, Input.sourceDocIdSearch);
		baseClass.stepInfo("Selecting Text file");
		ingestionPage.selectTextSource("Text.lst", false);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("verify ingestion status in detail popup");
		ingestionPage.ingestionAtCatlogState(Input.attachDocFolder);
		ingestionPage.verifyFailedIngestionStatusInPopup();
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
