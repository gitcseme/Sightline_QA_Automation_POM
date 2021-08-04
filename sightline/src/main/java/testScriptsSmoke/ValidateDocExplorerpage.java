package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ValidateDocExplorerpage {

	Driver driver;
	LoginPage lp;
	BaseClass bc;
	DocExplorerPage docexp;
	SessionSearch search;

	String assignmentName1 = "Assgn1" + Utility.dynamicNameAppender();
	String assignmentName2 = "Assgn2" + Utility.dynamicNameAppender();
	String commentname = "C" + Utility.dynamicNameAppender();

	/*
	 * Author : Shilpi Mangal Created date: 29/01/2020 Modified date: Modified
	 * by: Description : Login as PA user
	 */
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		///Input in = new Input();
		///in.loadEnvConfig();
		
		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		docexp = new DocExplorerPage(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		// search.bulkFolderExisting("Confidential");
		search.bulkTagExisting("Confidential");
		bc.selectproject();
		search.basicContentSearch("*");
		// search.bulkFolderExisting("Attorney_Client");
		search.bulkTagExisting("Attorney_Client");
	}

	/*
	 * Author : Shilpi Mangal Created date: Modified date: Modified by:
	 * Description : Verify that Column header Filter is working correctly on
	 * Doc Explorer screen
	 */
	@Test(groups = { "regression,smoke" }, dataProvider = "Filters", priority = 1)
	public void HeaderFiltersVerification(String Filtername) throws InterruptedException, ParseException {

		docexp.HeaderFilter(Filtername);
	}

	/*
	 * Author : Shilpi Mangal Created date: Modified date: Modified by:
	 * Description : Verify that multiple Filters (Tags and MasterDate) with
	 * "Exclude" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups = { "regression,smoke" }, priority = 2)
	public void TagMasterDateFilter() throws InterruptedException {

		docexp.TagWithMasterDateFilter("Confidential");

	}

	/*
	 * Author : Shilpi Mangal Created date: Modified date: Modified by:
	 * Description : Verify that CustodianName Filter with Include
	 * functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups = { "regression,smoke" }, priority = 3)
	public void CustodianNameFilter() throws InterruptedException {

		docexp.CustodianFilter("P Allen", "Andrew", "include");
		docexp.CustodianFilter("P Allen", "Andrew", "exclude");
	}

	/*
	 * Author : Shilpi Mangal Created date: Modified date: Modified by:
	 * Description : Verify that view in doclist functionality is working
	 * correctly on Doc Explorer.
	 */
	@Test(groups = { "regression,smoke" }, priority = 3)
	public void ViewinDoclist() throws InterruptedException {

		docexp.DocExplorertodoclist();
	}

	@DataProvider(name = "Filters")
	public static Object[][] getfiltername() {
		return new Object[][] { { "DocID" }, { "Custodian" }, { "MasterDate" } };
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.INFO, this.getClass().getSimpleName()+"/"+testMethod.getName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
			lp.clearBrowserCache();
		}
	}

}
