package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.Color;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_NewRegression03 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewRedactions docViewRedact;
	SavedSearch savedSearch;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
	}

	/**
	 * Author : Mohan date: 24/11/21 NA Modified date: NA Modified by: N/A
	 * Description : DataProvider for Different User Login
	 */
	@DataProvider(name = "userDetailss")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "rev" },
				{ "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "specificUsers")
	public Object[][] userLoginWithSpecificCredentials() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password }, };
	}

	@DataProvider(name = "multiUsers")
	public Object[][] userLoginWithMultiCredentials() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author : Vijaya.Rani date: 07/04/2022 Modified date: NA Modified by: NA
	 * 
	 * @description: Verify that thread map presents only emails when threaded
	 *               document contains email attachment. 'RPMXCON-51509' Sprint-13
	 * 
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyThreadMapEmailsInDocViewEmailAttachment()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51509");
		baseClass.stepInfo(
				"Verify that thread map presents only emails when threaded document contains email attachment.");

		// login as PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
		sessionSearch.SearchMetaData("IngestionName", "ZipDocviewNativesAttachCount");
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Configure Mini DocList Add AttachCount
		baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
		docView.selectAttachCountDocIdInAvailableField();

		baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(2));
		docView.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		baseClass.stepInfo("Successfully Selected Email Attach Count Document");

		// MetaData AttachIds Display
		String id = docView.selectAttachCountDocIdDisplayMetaData();

		// AttachIds Not DisPlay AnaltyticsPanel
		driver.waitForPageToBeReady();
		docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 07/04/2022 Modified date: NA Modified by: NA
	 * 
	 * @description: Verify that thread map presents only threaded emails when
	 *               threaded document contains non email attachment.
	 *               'RPMXCON-51510' Sprint-13
	 * 
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyThreadedMapEmailsPresentsContainsEmailAttachment()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51510");
		baseClass.stepInfo(
				"Verify that thread map presents only emails when threaded document contains email attachment.");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 2 : Search for Meta Data Docs and go to Docview");
		sessionSearch.SearchMetaData("IngestionName", "ZipDocviewNativesAttachCount");
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Configure Mini DocList Add AttachCount
		baseClass.stepInfo("Step 3 :Select Configure MiniDocList AttachCount Fields");
		docView.selectAttachCountDocIdInAvailableField();

		baseClass.stepInfo("Step 4 :Select Email Attach Docs in MiniDocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDoc_Selectdoc(4));
		docView.getDocView_MiniDoc_Selectdoc(4).waitAndClick(5);
		baseClass.stepInfo("Successfully Selected Email Attach Count Document");

		// MetaData AttachIds Display
		String id = docView.selectAttachCountDocIdDisplayMetaData();

		// AttachIds Not DisPlay AnaltyticsPanel
		driver.waitForPageToBeReady();
		docView.selectAttachEmailIdDisplayInAnalyticsPanel(id);

		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
