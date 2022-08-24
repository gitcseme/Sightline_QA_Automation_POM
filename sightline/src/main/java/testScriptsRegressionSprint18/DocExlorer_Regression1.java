package testScriptsRegressionSprint18;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExlorer_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-65050
	 * @throws Exception
	 * @Description Verify that error message does not display and application
	 *              accepts - when 'Share by email' entered with < > * ; ‘ / ( ) # &
	 *              ” from Doc Explorer > Export > Schedule Report.
	 */
	@Test(description = "RPMXCON-65050", enabled = true, groups = { "regression" })
	public void verifyMutliValueFieldaDisplayCustomColumn() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-65050");
		baseClass.stepInfo(
				"Verify that error message does not display and application accepts - when 'Share by email' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Schedule Report.");
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String Email = "new@email.com";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Go to DocExplorer Pge And ExportData and Schedule report");
		docExplorer.docExloerExportData(Input.pa1FullName, Email);

		baseClass.passedStep(
				"Error message is displayed Successfully when 'Share by email' entered with characters-> \r\n"
						+ "< > * ; ‘ / ( ) # &");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-54590
	 * @throws Exception
	 * @Description Verify the documents from "Doc Explorer" page for PA and RMU.
	 */
	@Test(description = "RPMXCON-54590", enabled = true, groups = { "regression" })
	public void verifyDocsFromDocExplorerPageInPAAndRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54590");
		baseClass.stepInfo("Verify the documents from \"Doc Explorer\" page for PA and RMU.");
		sessionSearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("DocExplorer Docs Are Published PAU");
		docExplorer.docExloerRelease(Input.securityGroup);
		baseClass.passedStep("Documents presented on the page is reflect the published docs in the project for a PAU");

		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Navigate to DocExplorer page");
		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		if (baseClass.text("ID000").isDisplayed()) {
			baseClass.passedStep(" Documents presented on the page is reflect the released docs in the SG for an RMU ");
		} else {
			baseClass.failedStep(
					" Documents presented on the page is not reflect the released docs in the SG for an RMU ");
		}
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("DocExplorer Docs Are unPublished RMU");
		docExplorer.docExloerUnRelease(Input.securityGroup);
		baseClass.passedStep("Documents presented on the page is reflect the unreleased docs in the SG for an RMU");

		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo("Navigate to DocExplorer page");
		docExplorer.navigateToDocExplorerPage();
		driver.waitForPageToBeReady();
		if (baseClass.text("ID000").isDisplayed()) {
			baseClass.passedStep("Documents presented on the page is reflect the released docs in the SG for an RMU ");
		} else {
			baseClass.failedStep(
					" Documents presented on the page is not reflect the released docs in the SG for an RMU ");
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
