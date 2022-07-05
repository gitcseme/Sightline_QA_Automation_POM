package testScriptsRegressionSprint16;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

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
import pageFactory.ConceptExplorerPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ConceptExplorer_Regression_2_1 {

	Driver driver;
	LoginPage login;
	BaseClass base;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	SessionSearch sessionSearch;

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
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		sessionSearch = new SessionSearch(driver);
	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @Date: 07/04/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Concept Explorer report is working correctly and
	 *              properly. RPMXCON-48759
	 */
	@Test(description = "RPMXCON-48759", enabled = true, groups = { "regression" })
	public void verifyConceptExplorerAction() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-48759 - Concept Explorer");
		base.stepInfo("Verify that Concept Explorer report is working correctly and properly");

		String tagName = "TagTT" + UtilityLog.dynamicNameAppender();
		String sourceToSelect = "Security Groups";
		String sgToSelect = Input.securityGroup;
		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;

		// Login as user
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Navigate to Concept Explorer page
		base.stepInfo(
				"**Step-1 Go to Concept Explorer  Select source as Security Group  Select any filters  Click on Apply, report is generated**");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources and Apply filter
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, sgToSelect);
		base.stepInfo("Selected : " + sgToSelect + "and Saved selection");
		conceptExplorer.getApplyFilterBtn().waitAndClick(3);
		driver.waitForPageToBeReady();

		// Select data to 'Add to cart'
		base.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();

		// Verify Analyze at 2nd Level button is disabled
		base.ValidateElement_Absence(conceptExplorer.getAnalyseActionBtn(analyze), analyze
				+ " button for conceptual map is disabled in the concept explorer page before user clicks a conceptual map.");
		base.stepInfo("**Step-2 Click on Create and add keywords and click on OK**");

		// Create heat keyword
		conceptExplorer.addKeyWordHighlighting(Input.searchString1, true);
		driver.waitForPageToBeReady();
		base.waitForElementCollection(conceptExplorer.getKeywordHighlightedNodesPlusBtns());
		int resultToAddInCartHighlight = conceptExplorer.getDataToAddInCart().size();
		base.verifyElementCollectionIsNotEmpty(conceptExplorer.getKeywordHighlightedNodesPlusBtns(),
				"Cluster highlighted in Purple color", "Cluster not highlighted in Purple color");

		// Add and perform bulk tag
		base.stepInfo("**Step-3 Select any cluster from Tiles.And perform Actions \"BulkAssign\"/Bulk Tag.**");
		conceptExplorer.getKeywordHighlightedNodesPlusBtn(resultToAddInCartHighlight).waitAndClick(3);
		driver.waitForPageToBeReady();
		conceptExplorer.performActions("Bulk Tag");
		String bulkCount = sessionSearch.BulkActions_Tag(tagName);
		base.stepInfo("Bulk Tag Action done successfully");

		// Choose a map
		base.stepInfo("**Step-4 Select any cluster and click on '2nd Level**");
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		String clusterID = conceptExplorer.tileSelctionBasedOnChildCountReturnClusterID(resultToAddInCart, 3);

		// Verify Analyze at 2nd Level button is enabled
		base.printResutInReport(base.ValidateElement_PresenceReturn(conceptExplorer.getAnalyseActionBtn(analyze)),
				analyze + " button for conceptual map is enabled in the concept explorer page after user clicks a conceptual map.",
				analyze + " button not enabled", "Pass");

		// Go to 2nd level
		base.stepInfo("Navigate to Second Level");
		conceptExplorer.analyzeAction(analyze);
		base.printResutInReport(base.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level", "Page didn't navigate to second level", "Pass");
		base.stepInfo("Pre-requesties completed");

		// Select a cluster from Level 2
		base.stepInfo("**Step-5 Select any cluster and click on 3rd level**");
		base.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 2);

		// Verify Analyze at 3nd Level button is enabled
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(conceptExplorer.getAnalyseActionBtn(analyze3)), analyze3
						+ " button for conceptual map is enabled in the concept explorer page after user clicks a conceptual map.",
				analyze3 + " button not enabled", "Pass");

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		base.printResutInReport(base.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to Third level", "Page didn't navigate to second level", "Pass");

		// Logout Application
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
