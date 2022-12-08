package testScriptsRegressionSprint27;

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
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportEngineRegression_27 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SoftAssert soft;
	UserManagement user;
	SessionSearch session;
	CommunicationExplorerPage communication;

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
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		soft = new SoftAssert();
		user = new UserManagement(driver);
		session = new SessionSearch(driver);
		communication = new CommunicationExplorerPage(driver);
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify Communication report should work for folders created in
	 *              this session as well as for pre-existing folders.
	 *              [RPMXCON-56923]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56923", enabled = true, groups = { "regression" })
	public void verifyCommunicationReportWorkForFolder() throws Exception {
		String folderName = "FOLDER" + utility.dynamicNameAppender();

		// Login as a PA User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged In As : PA");

		base.stepInfo("Test case Id:RPMXCON-56923 Report Engine");
		base.stepInfo(
				"Verify Communication report should work for folders created in this session as well as for pre-existing folders.");

		//Configure EmailDomain query & bulk folder 
		int purehit = session.basicMetaDataSearch(Input.emailAllDomain, null, Input.domainNameConsilio, null);
		session.bulkFolder(folderName);

		//Navigate to Communication Page & select source as FOLDER
		communication.navigateToCommunicationExpPage();
		communication.SelectSource_Folder(folderName, null);
		
		//verify report give same document count as present in selected folders.
		communication.verifyDocCountDisplayed(true, purehit);

		// logout
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR Reports Communication EXECUTED******");

	}
}
