package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.TimelineReportPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TimelineAndGap_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	TimelineReportPage timelineRpt;
	AssignmentsPage agnmt;
	SessionSearch search;
	DocViewPage docview;
	DocListPage dlpage;
	String fromDate;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		fromDate="2019/01/01";   //Masterdate 

	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @throws ParseException 
	 * @throws InterruptedException 
	 * @description: To verify that User can view Gap Report for Yearly
	 */
	@Test(description = "RPMXCON-48721",groups = {"regression" },enabled = true)
	public void verifyYearlyGapReportDownloadedOrNot() throws ParseException, InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-48721");
		bc.stepInfo("To verify that User can view Gap Report for Yearly");	
		SoftAssert sa = new SoftAssert();
		ReportsPage report = new ReportsPage(driver);
		String headerList[] = { "Master Date Yearly", "Document Count", "Months with 0 Documents", "Gap Start",
                "Gap End", "Gap Months" };
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);		
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timelineRpt.navigateToTimelineAndGapsReport();
		timelineRpt.selectSource();
		timelineRpt.selectTimeline("MasterDate");		
		String toDate = timelineRpt.getCurrentDate();
		timelineRpt.selectDateRange(fromDate, toDate);
		timelineRpt.applyChanges();
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		timelineRpt.selectActions("Generate Gaps","yearlyActions");
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");
		sa.assertAll();	
		bc.passedStep("Timeline gaps report for yearly is downloaded successfully");
		bc.waitTime(5);
		timelineRpt.verifyTheReportColoumnValuesInExcel(0,"Expected timeline gap report is downloaded","Expected timeline gap report is not downloaded",headerList);
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws ParseException 
	 * @throws InterruptedException 
	 * @description: To verify that User can view Gap Report for Monthly from Timeline and Gaps report
	 */
	@Test(description = "RPMXCON-48722",groups = {"regression" },enabled = true)
	public void verifyMonthlyReportDownloadedOrNot() throws ParseException, InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-48722");
		bc.stepInfo("To verify that User can view Gap Report for Monthly from Timeline and Gaps report");	
		SoftAssert sa = new SoftAssert();
		ReportsPage report = new ReportsPage(driver);
		String headerList[] = { "Master Date Monthly", "Document Count", "Days with 0 Documents", "Gap Start",
                "Gap End", "Gap Days" };
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);		
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timelineRpt.navigateToTimelineAndGapsReport();
		timelineRpt.selectSource();
		timelineRpt.selectTimeline("MasterDate");		
		String toDate = timelineRpt.getCurrentDate();
		timelineRpt.selectDateRange(fromDate, toDate);
		timelineRpt.applyChanges();
		timelineRpt.selectBarChart();
		timelineRpt.selectActions("Monthly Timeline","yearlyActions");
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		timelineRpt.selectActions("Generate Gaps","monthlyActions");
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");		
		sa.assertAll();	
		bc.passedStep("Timeline gaps report for monthly is downloaded successfully");
		bc.waitTime(5);
		timelineRpt.verifyTheReportColoumnValuesInExcel(0,"Expected timeline gap report is downloaded","Expected timeline gap report is not downloaded",headerList);
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws ParseException 
	 * @description: To verify that User can do bulk action in Timeline Report
	 */
	@Test(description = "RPMXCON-48723",groups = {"regression" },enabled = true)
	public void verifyBulkActionFunctionInTimelineReport() throws ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48723");
		bc.stepInfo("To verify that User can do bulk action in Timeline Report");	
		SoftAssert sa = new SoftAssert();
		dlpage = new DocListPage(driver);		
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);		
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timelineRpt.navigateToTimelineAndGapsReport();
		timelineRpt.selectSource();
		timelineRpt.selectTimeline("MasterDate");		
		String toDate = timelineRpt.getCurrentDate();
		timelineRpt.selectDateRange(fromDate, toDate);
		timelineRpt.applyChanges();
		timelineRpt.selectBarChart();
		timelineRpt.viewInDocviewOrDoclist("View In DocList","yearlyActions");
		String expectedDocCount = dlpage.verifyingDocCount();
		timelineRpt.selectBarChart();
		timelineRpt.selectActions("Tag","yearlyActions");
		bc.waitForElement(timelineRpt.totalDocsInTag());
		String actualDocCount = timelineRpt.totalDocsInTag().getText();
		sa.assertEquals(expectedDocCount, actualDocCount,"Total number of documents shown is chart is not reflected exactly in tag modal window");
		sa.assertAll();
		bc.passedStep("Total number of documents shown is chart is reflected exactly in tag modal window successfully");
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws ParseException 
	 * @throws InterruptedException 
	 * @description: To verify that User is displayed with correct information in Yearly Gap Report.
	 */
	@Test(description = "RPMXCON-56359",groups = {"regression" },enabled = true)
	public void verifyColumnHeadersInExcel() throws ParseException, InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56359");
		bc.stepInfo("To verify that User is displayed with correct information in Yearly Gap Report.");	
		SoftAssert sa = new SoftAssert();		
		ReportsPage report = new ReportsPage(driver);
		String headerList[] = { "Master Date Yearly", "Document Count", "Months with 0 Documents", "Gap Start",
                "Gap End", "Gap Months" };
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);		
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timelineRpt.navigateToTimelineAndGapsReport();
		timelineRpt.selectSource();
		timelineRpt.selectTimeline("MasterDate");		
		String toDate = timelineRpt.getCurrentDate();
		timelineRpt.selectDateRange(fromDate, toDate);
		timelineRpt.applyChanges();
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		timelineRpt.selectActions("Generate Gaps","yearlyActions");
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");
		sa.assertAll();	
		bc.passedStep("Timeline gaps report is downloaded successfully");
		bc.waitTime(5);
		timelineRpt.verifyTheReportColoumnValuesInExcel(0,"Column Values in the report are displayed as expected","Column Values in the report are not displayed as expected",headerList);
		lp.logout();
	}


	

	

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		timelineRpt = new TimelineReportPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
