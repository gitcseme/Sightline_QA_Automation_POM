package sightline.reports;

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
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CommunicationExplorer_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	String hitsCount;
	CommunicationExplorerPage comExpPage;
	DocViewPage docview;
	int pureHit;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@Test(description ="RPMXCON-64257",dataProvider = "Users_PARMU", groups = { "regression" },enabled = true)
	public void verifyViewInDocviewInNewtabFromCommunicationExplorerReportPage(String username, String password, String role)
			throws InterruptedException {
		bc = new BaseClass(driver);
		bc.stepInfo("Test case Id: RPMXCON-64257");
		bc.stepInfo("Verify that on selecting action as 'View in DocView in new tab' from communication explorer should redirect to"+
		" DocView in new tab with correct document count");
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		docview = new DocViewPage(driver);
		comExpPage.generateReportusingDefaultSG();
		comExpPage.clickReport();
		for (int i = 1; i <= 4; i++) {
			String docCount = comExpPage.selectedDocsCount();
			comExpPage.viewinDocViewInNewTab();
			driver.waitForPageToBeReady();
			bc.switchTab(i);
			driver.waitForPageToBeReady();
			bc.verifyPageNavigation("DocumentViewer/DocView");
			docview.verifyingDocCount(docCount);
			bc.switchTab(0);
			if ((bc.getWarningsMsgHeader().isElementAvailable(1))
					|| (bc.getWarningsMsgHeader().isElementAvailable(1)) == false) {
				bc.passedStep("No warning message or error message occurs in communication explorer tab");
			} else {
				bc.failedStep(bc.getWarningMsg().getText() + " is occurred");
			}
			if (i == 4) {
				bc.passedStep(
						"Successfully verified four times selecting action as 'View in DocView in new tab' from communication explorer "+
				"should redirect to DocView in new tab with correct document count as "+role);
			}
		}
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
		comExpPage = new CommunicationExplorerPage(driver);
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
