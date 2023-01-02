package sightline.docviewMiniDocList;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewMiniDocList_Phase2_Regression {

	Driver driver;
	LoginPage login;
	BaseClass base;
	DocExplorerPage docExplorer;
	MiniDocListPage miniDocListPage;
	DocViewPage docView;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		docView = new DocViewPage(driver);
		docExplorer = new DocExplorerPage(driver);
	}

	/**
	 * @author
	 * @Date: 07/07/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify on click of any document from mini doc list, RMU or
	 *              Reviewer should see the document in doc view section.
	 *              RPMXCON-48738
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-46855", enabled = true, groups = { "regression" })
	public void verifyingOnClickAnyDocumentFromMiniDocListAndViewInDocViewSection() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-46855");
		base.stepInfo(
				"To verify on click of any document from mini doc list, RMU or Reviewer should see the document in doc view section");

		int rowNumber = 2;

		// login as Users
		base.stepInfo(
				"**Step-1 Logged in as RMU/Reviewer user in same project and security group as per pre-requisites **");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// navigating to DocExplorer and selecting Documents
		base.stepInfo("**Step-2 From Doc Explorer, select few documents and select action as 'View in Docview'");
		docExplorer.navigateToDocExplorerURL();
		docExplorer.selectAllDocumentsFromCurrentPage();
		base.stepInfo("selecting all documents in current page of DocExplorer.");

		// viewing the selected Documents in DocView
		docExplorer.docExpViewInDocView();
		base.stepInfo("performing view In DocView action.");

		// selecting the document from miniDocList and verifying whether it is viewed in
		// Default view
		base.stepInfo("**Step-3 Click the document from mini doc list by RMU");
		docView.selectDocumentFromMiniDocList(rowNumber);

		// logout
		login.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}
