package testScriptsRegressionPhase2;

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
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Regression_Ingestion02 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionsearch;
	DocListPage docList;
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
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49320 
	 * Description :ICE: Verify the help content for "Kick Off Analytics Automatically" option
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49320",enabled = true, groups = { "regression" }, priority = 1)
	public void verifyHelpContentForKickOffAnalyticsAutomatically() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49320");
		baseClass.stepInfo("ICE: Verify the help content for Kick Off Analytics Automatically option");
		ingestionPage = new IngestionPage_Indium(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Navigating to manage project page");
		ingestionPage.navigateToManageProjectPage();
		baseClass.stepInfo("Verifying the help content for Kick Off Analytics Automatically option");
		ingestionPage.verifyHelpContentOnProjectCreationPage(Input.kickOffAnalytics, Input.kickOffHelpContent);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49321 
	 * Description :ICE: Verify the help content for "Run Incremental Analytics for New Small Data (<20%)" option
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49321",enabled = true, groups = { "regression" }, priority = 2)
	public void verifyHelpContentForIncrementalAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49321");
		baseClass.stepInfo("ICE: Verify the help content for Run Incremental Analytics for New Small Data (<20%) option");
		ingestionPage = new IngestionPage_Indium(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Navigating to manage project page");
		ingestionPage.navigateToManageProjectPage();
		baseClass.stepInfo("Verifying the help content for run incremental analytics option");
		ingestionPage.verifyHelpContentOnProjectCreationPage(Input.incrementalAnalytics, Input.incrementalHelpContent);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49319 
	 * Description :ICE: Verify that the options "Kick Off Analytics Automatically" and "Run Incremental Analytics
	 *  for New Small Data (<20%)" available in project settings
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49319",enabled = true, groups = { "regression" }, priority = 3)
	public void verifyOptionsAvailableInProjectSetting() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49319");
		baseClass.stepInfo("ICE: Verify the options available in project settings");
		ingestionPage = new IngestionPage_Indium(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Navigating to manage project page");
		ingestionPage.navigateToManageProjectPage();
		baseClass.stepInfo("Verifying the the options available in project settings");
		ingestionPage.verifyoptionsAvailability(Input.kickOffAnalytics, Input.incrementalAnalytics);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49490 
	 * Description :Verify that in Ingestion Wizard page, in the TIFF section
	 *  "Generate Searchable PDF for TIFFs" options should be displayed
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49490",enabled = true, groups = { "regression" }, priority = 4)
	public void verifyGenerateSearchablePdfOption() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49490");
		baseClass.stepInfo("Verify that in Ingestion Wizard page, in the TIFF section'Generate Searchable PDF for TIFFs' options should be displayed");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Verify Generate Searchable PDF option displayed in Tiff section");
		ingestionPage.verifyGeneratePdfOptionInTiffSection();
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