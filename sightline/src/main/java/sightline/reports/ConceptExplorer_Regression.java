package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ConceptExplorer_Regression {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		//in = new Input();
		//in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.shareSearchPA },
				{ Input.rmu1userName, Input.rmu1password, Input.shareSearchDefaultSG } };
		return users;
	}

	/**
	 * @author Jayanthi.Ganesan 
	 * Verify and generate Concept Explorer Report with
	 * source as Search
	 * @param User
	 * @param pwd
	 * @param SearchGroup
	 * @throws Exception
	 */

	@Test(description = "RPMXCON-56962", enabled = true, dataProvider = "paRmuUsers", groups = { "regression" })
	public void verifyConceptExp(String User, String pwd, String SearchGroup) throws Exception {

		baseClass.stepInfo("Test case Id : RPMXCON-56962: ");
		baseClass.stepInfo("Verify and generate Concept Explorer Report with source as Search");

		String savedSearchName = "SavedSearch-" + Utility.dynamicNameAppender();
		String sourceToSelect = "Searches";

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;

		// Login as RMU user
		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(User, pwd);

		// Pre-requesties
		baseClass.stepInfo("Pre-requesties two saved searches  creation");
		
		sessionSearch.basicContentSearch(Input.searchString1);
		String hitCount = sessionSearch.verifyPureHitsCount();
		
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, SearchGroup);
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, Input.mySavedSearch);
		
		baseClass.stepInfo("Pre-requestes Creation  completed");

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources-search 1
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("**Step-5 Select any one of the source and save selection");
		conceptExplorer.selectSearchessource(sourceToSelect, Input.mySavedSearch, savedSearchName, "", false, "");

		// Select Sources-search 2
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSearchessource(sourceToSelect, SearchGroup, savedSearchName, "", false, "");

		// Apply filter
		baseClass.stepInfo("**Step-6 Set the filter criteria and click “Apply filter”");
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		baseClass.stepInfo("Report Generated.");

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		String passMSg = "Report Generated with doc count : " + aggregatedDocCount + " which is expected";
		String failMSg = "Report Generated with doc count : " + aggregatedDocCount + " which is not  expected";
		// Select data to 'Add to cart'
		baseClass.textCompareEquals(hitCount, aggregatedDocCount, passMSg, failMSg);

		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();

		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionAnalyze_BasedChildCount(resultToAddInCart, 1,2);

		// Go to 2nd level
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		// Select data to 'Add to cart'
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionAnalyze_BasedChildCount(resultToAddInCart, 1,2);
		
		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");

		// Delete Search
		baseClass.stepInfo("Initiating Delete search input");
		savedSearch = new SavedSearch(driver);
		savedSearch.deleteSearch(savedSearchName, SearchGroup, "Yes");
		savedSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		// Logout
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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
