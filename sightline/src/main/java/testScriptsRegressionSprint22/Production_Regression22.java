package testScriptsRegressionSprint22;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression22 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		base = new BaseClass(driver);
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);

	}
	/**
	 * @author Brundha RPMXCON-47742 Date:9/22/2022
	 * @Description To Verify the pagination for multiple productions for grid view.
	 */
	@Test(description = "RPMXCON-47742", enabled = true, groups = { "regression" })
	public void verifyingPaginationInGridView() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47742 -Production component");
		base.stepInfo("To Verify the pagination for multiple productions for grid view.");

		int Count = 11;
		ProductionPage page = new ProductionPage(driver);
		String totalProduction = page.getProductionItemsTileItems().getText();
		if (Integer.valueOf(totalProduction)>=Count) {
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
		}
		else {
			base.stepInfo("Creating Multiple Production");
			for(int i=0;i<Count;i++) {
			String productionname = "P" + Utility.dynamicNameAppender();
			page.navigatingToProductionHomePage();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			driver.waitForPageToBeReady();
			}
			page.navigatingToProductionHomePage();
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
			
		}
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
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}
}
