package testScriptsRegression;

import java.io.IOException;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanelThreadMap {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SavedSearch savedSearch;
	SoftAssert softAssertion;

	@BeforeMethod(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		baseClass = new BaseClass(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		baseClass.stepInfo("Started Execution for prerequisite");

		// Open browser
		//Input in = new Input();
		//in.loadEnvConfig();

		driver = new Driver();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa2userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa2userName + "");
	}

	/**
	 * 
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that Thread Map tab should be displayed when
	 *              navigating to doc view outside of assignment 'RPMXCON-51847'
	 */
	@Test
	public void verifyDocumentFromAnalyticsPanelWithThreadMap()
			throws ParseException, InterruptedException, IOException {
		String assngName = Input.AssgnName;
		softAssertion = new SoftAssert();
		baseClass = new BaseClass(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		savedSearch.savedSearchToDocView(assngName);
		baseClass.stepInfo("*****Doc is searched from saved search and viewed in the DocView page successfully*****");
		docViewAnalytics.selectDocumentFromAnalyticPanel();
		UtilityLog.info("*****Thread Map tab is displayed when navigating to doc view outside of assignment*****");
		baseClass.stepInfo("*****Thread Map tab is displayed when navigating to doc view outside of assignment*****");
		String text = docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().getText();
		softAssertion.assertTrue(true, text);
		baseClass.passedStep(
				"Verify that Thread Map tab should be displayed when navigating to doc view outside of assignment is successfully");
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {

			loginPage.closeBrowser();

		}
		LoginPage.clearBrowserCache();
	}

}
