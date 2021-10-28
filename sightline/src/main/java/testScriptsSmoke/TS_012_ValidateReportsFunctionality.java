package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.ReviewProgressByReviewerReportPage;
import pageFactory.TallyPage;
import pageFactory.Utility;

public class TS_012_ValidateReportsFunctionality {
	Driver driver;
	LoginPage lp;
	HomePage home;
	ReportsPage report;
	// String usertosharewith = "smangal@consilio.com";

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : Login as rmu and land on reports page, from
	 * here all other scripts will run
	 * 
	 */
	@BeforeClass(alwaysRun = true)
	public void beforeClass() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
        
	
		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		report = new ReportsPage(driver);

	}

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : Login as rmu user run tally and sub tally
	 * report, validate the count in doclist!
	 * 
	 */
	@Test(groups = { "smoke", "regression" },priority=1)
	public void tallySubTally() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		report.TallyReportButton();
		TallyPage tally = new TallyPage(driver);
		tally.ValidateTallySubTally();

	}
	
	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description :
	 * 
	 */
	@Test(groups = { "smoke", "regression" },priority=2)
	public void reviewProgressByRevReport() throws InterruptedException, ParseException {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		ReviewProgressByReviewerReportPage reviewreport = new ReviewProgressByReviewerReportPage(driver);
		System.out.println("Sharing with " + Input.rev1userName);
		UtilityLog.info("Sharing with " + Input.rev1userName);
		reviewreport.ValidateReviewProgressReportShareandSchdule(Input.rev1userName);

	}

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : Login as rmu user run communication explorer
	 * report, validate the count in doclist!
	 * 
	 */
	@Test(groups = { "regression" },priority=3)
	public void communicationExplorer() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		CommunicationExplorerPage commreport = new CommunicationExplorerPage(driver);
		commreport.ValidateCommExplorerreport();

	}

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : Login as rmu user run concept explorer report,
	 * validate the count in doclist!
	 * 
	 */
	@Test(groups = { "smoke", "regression" },priority=4)
	public void conceptExplorer() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		ConceptExplorerPage conceptreport = new ConceptExplorerPage(driver);
		conceptreport.ValidateConceptExplorerreport();

	}
   
	@Test(groups = { "regression" },priority=5)
	public void customDDRExportAfterSwap() throws InterruptedException, ParseException, IOException {
		// Columns to be selected
		String[] metaDataFields = { "AllCustodians", "CreateDate", "DocDate", "EmailAllDomains", "FamilyID",
				"SourceDocID" };
		// driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		cddr.selectSource("Security Groups", "Default Security Group");
		cddr.selectMeataDatFields(metaDataFields);

		// Swap 1 to 6th,3 times!
		cddr.swapMetaDataFields(0, 5);
		Thread.sleep(1000);
		cddr.swapMetaDataFields(1, 5);
		Thread.sleep(1000);
		cddr.swapMetaDataFields(2, 5);
		Thread.sleep(1000);

		// Expected order in report
		ArrayList<String> expected = new ArrayList<String>();
		expected.add("EmailAllDomains");
		expected.add("FamilyID");
		expected.add("AllCustodians");
		expected.add("CreateDate");
		expected.add("DocDate");
		expected.add("SourceDocID");

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		// run report
		cddr.getRunReport().Click();
		cddr.reportRunSuccessMsg();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		// Download file and validated
		cddr.downloadExport();
		cddr.validateColumnsInExport(expected);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
		//ExtentTestManager.getTest().log(Status.INFO, this.getClass().getSimpleName()+"/"+testMethod.getName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}

}