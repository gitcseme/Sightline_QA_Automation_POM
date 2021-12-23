package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	BatchPrintPage batchPrint;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 47841 - Verify PDF file should be generated for the selected
	 * production set. Description : To Verify PDF file should be generated for the
	 * selected production set.
	 */
	@Test(groups = { "regression" })
	public void verifyPdfFileGeneratedBySelectedProductionSet() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47841");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### Verify PDF file should be generated for the selected production set ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Verify PDF file should be generated for the selected production set");
		batchPrint.BatchPrintWithProduction(searchname, Input.orderCriteria, Input.orderType);

	}

	@AfterMethod(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logout();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
}
