
package sightline.reports;

import java.awt.AWTException;
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
import pageFactory.BaseClass;
import pageFactory.DocumentAuditReportPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocumentAuditReport_Regression {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	DocumentAuditReportPage docaudit;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-56594",dataProvider = "Users_PARMU", groups = { "regression" })
	public void DocAuditPageDisplay(String username, String password, String role) throws InterruptedException {
		LoginPage lp = new LoginPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-56594");
		bc.stepInfo("To verify that Users are able to View Document Audit Report");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		docaudit = new DocumentAuditReportPage(driver);
		docaudit.navigateTODocAuditReportPage();
		bc.waitTime(5);
		docaudit.verifyReportDisplay(Input.docIDs+"0001");
		lp.logout();
		
	}
	@Test(description ="RPMXCON-48781",dataProvider = "Users_PARMU", groups = { "regression" })
	  public void validateDocAuditBulkTag(String username, String password, String role) throws InterruptedException {
			bc = new BaseClass(driver);
			lp = new LoginPage(driver);
			String tagName="DA"+Utility.dynamicNameAppender();
			bc.stepInfo("Test case Id: RPMXCON-48781");
			bc.stepInfo("To verify that by performing some actions on Documents is displayed in Document Audit Report");
			lp.loginToSightLine(username, password);
			SessionSearch search = new SessionSearch(driver);
			search.advancedMetaDataSearch("DocID", null, Input.docIDs+"0001", null);	
			bc.stepInfo("Add a new Tag for doc id --" +Input.docIDs+"0001");
			search.bulkTag(tagName);
			docaudit = new DocumentAuditReportPage(driver);
			docaudit.navigateTODocAuditReportPage();
			bc.waitTime(5);
			docaudit.verifyReportDisplay(Input.docIDs+"0001");
			docaudit.validateDocumentAuditActionColumn("Tagged - Bulk");
			lp.logout();
		}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		bc = new BaseClass(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		bc = new BaseClass(driver);
		lp = new LoginPage(driver);
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
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("Executed :DocAuditReport_Regression ");

	}
}

