package testScriptsRegressionSprint16;

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

public class Ingestion_Regression_2 {

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
	 * Author :Arunkumar date: 05/07/2022 TestCase Id:RPMXCON-49338 
	 * Description :Verify value of metadata field "DocPrimaryLanguage" should be derived from CA for Add Only Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49338",enabled = true, groups = { "regression" })
	public void verifyValueOfDocPrimaryLanguageMetadata() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49338");
		baseClass.stepInfo("Verify value of metadata field 'DocPrimaryLanguage' should be derived from CA for Add Only Ingestion");
		dataSets = new DataSets(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion and navigate to doclist");
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			String ingestionName= ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
			sessionSearch.basicSearchWithMetaDataQuery(ingestionName,Input.metadataIngestion);
			sessionSearch.ViewInDocList();
		}
		else {
			dataSets.selectDataSetWithName(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Verify the value of metadata");
		ingestionPage.addMetadatAndVerifyValue(Input.docPrimaryLanguage, Input.english);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/07/2022 TestCase Id:RPMXCON-49265 
	 * Description :To verify that option "ICE" is available in the Source System dropdown in Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49265",enabled = true, groups = { "regression" })
	public void verifyIceOptionInSourceSystemDropdown() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49265");
		baseClass.stepInfo("To verify that option 'ICE' is available in the Source System dropdown in Ingestion");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("verify option 'ICE' available under source system");
		ingestionPage.verifyOptionAvailableInSourceSystem();
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
