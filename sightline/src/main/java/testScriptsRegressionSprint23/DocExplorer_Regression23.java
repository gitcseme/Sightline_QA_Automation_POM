package testScriptsRegressionSprint23;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression23 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-54697
	 * @throws Exception
	 * @Description Verify that “IngestionName” (Only For PA) Filter with "Exclude"
	 *              functionality is working correctly on Doc Explorer list.
	 */
	@Test(description = "RPMXCON-54697", enabled = true, groups = { "regression" })
	public void verifyIngestionNameWithExcludeInDocExplorerList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54697");
		baseClass.stepInfo(
				"Verify that “IngestionName” (Only For PA) Filter with \"Exclude\" functionality is working correctly on Doc Explorer list.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		String random = Input.ingestionAutomationAllSource;
		String random1 = Input.JanMultiPTIFF;

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeIngestionNameFilter(random);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForIngestionName();

		baseClass.stepInfo("Refresh page");
		docExplorer.refreshPage();

		baseClass.stepInfo("Perform exclude filter by DocFileType");
		docExplorer.performExculdeIngestionNameFilter(random);
		docExplorer.performUpdateExculdeIngestionNameFilter(random1);

		baseClass.stepInfo("Verify documents after applying exclude functionality by DocFileType");
		docExplorer.verifyExcludeFunctionlityForIngestionName();

		loginPage.logout();

	}

	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}
