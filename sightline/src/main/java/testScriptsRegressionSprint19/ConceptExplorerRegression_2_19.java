package testScriptsRegressionSprint19;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ConceptExplorerRegression_2_19 {
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
	//	in = new Input();
	//	in.loadEnvConfig();
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
		savedSearch = new SavedSearch(driver);
	}

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rev1userName, Input.rev1password, "REV" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Raghuram A
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate Masterdate range on Concept Explorer report.
	 *              RPMXCON-56887
	 */
	@Test(description = "RPMXCON-56887", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyIncludeExcludeFiltersFunctionality_ConceptExp(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56887");
		baseClass.stepInfo("Validate Masterdate range on Concept Explorer report");

		String searchText = "Auto";
		String sourceToSelect = "Searches";
		String sgToSelect = Input.mySavedSearch;
		String savedSearchName = "SavedSearch-" + UtilityLog.dynamicNameAppender();
		String[] selectSourceList = { "Security Groups", "Searches", "Project", "Folders" };
		String[] conditionsToCheck = { "Before", "After", "On", "Between" };
		String expectedDateInput = Input.expectedDateInput;
		String expectedToDateInput = Input.expectedToDateInput;

		// Login as PA
		baseClass.stepInfo("**Step-1 Login as Project Admin**");
		loginPage.loginToSightLine(userName, password);

		// Pre-requesties
		baseClass.stepInfo("Pre-requesties search creation");
		sessionSearch.basicContentSearch(searchText);
		sessionSearch.saveSearchAtAnyRootGroup(savedSearchName, sgToSelect);

		// masterDateVerifications via Concept explorer - DocList
		conceptExplorer.masterDateVerifications(selectSourceList, sourceToSelect, sgToSelect, savedSearchName,
				expectedDateInput, expectedToDateInput, conditionsToCheck);

		// Delete search
		savedSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param User
	 * @param pwd
	 * @throws Exception
	 */

	@Test(description="RPMXCON-56908",enabled = true, dataProvider = "paRmu", groups = { "regression" })
	public void verifyConceptExpFiltersFunctionality(String User, String pwd) throws Exception {
		baseClass.stepInfo("Test case Id :RPMXCON-56908 ");
        baseClass.stepInfo("Validate onpage filter for EmailRecipientNames  and EmailAllDomain on "
                + "Concept Explorer Report");
        String analyze = Input.analyzeAt2;
        String analyze3 = Input.analyzeAt3;
        String sourceToSelect = "Security Groups";
        String[] columnsToSelect = { "EmailAllDomains", "EmailRecipientNames" };        
        String emailrecep1 = Input.emailRecipientName1;
        String emailrecep2 = Input.emailReciepientName2;
        String doaminNAme = Input.domainNameConsilio;   
        baseClass.stepInfo("**Login to sightline and Select Project**");
        loginPage.loginToSightLine(User, pwd);
        // Navigate to Concept Explorer page
        baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
        reports.navigateToReportsPage("Concept Explorer Report");
       // Select Sources
        conceptExplorer.clickSelectSources();
        baseClass.stepInfo("** Select Security group as source and save selection");
        conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);
       // Apply filter
        baseClass.stepInfo("** Set the filter criteria and click “Apply filter”");
        
        conceptExplorer.filterAction(emailrecep1, "EmailRecipientNames", emailrecep2, true);
        conceptExplorer.filterAction(doaminNAme, "EmailAllDomains", null, true);
        conceptExplorer.applyFilter("Yes", 10);
       baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
       // Get Doc count consolidated
        String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
        String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
        baseClass.passedStep("Report Generated with doc count : " + aggregatedDocCount + " which is expected");
       // tiles add to cart
        int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
        conceptExplorer.addMultipleTilesToCart(resultToAddInCart);
       // view in doc list to verify filters applied returns the docs correctly.
        conceptExplorer.performDocActions("View", "View in DocList");
        baseClass.waitTime(4);
        baseClass.verifyPageNavigation("en-us/Document/DocList");
       // validation for inlcude filters
        DocListPage dlPage = new DocListPage(driver);
        dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
        List<String> emailAllDomain = dlPage.getColumnValue("EmailAllDomains", false);
        List<String> emailRecipients = dlPage.getColumnValue("EmailRecipientNames", false);
        conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailRecipients, emailAllDomain, emailrecep1,
                emailrecep2, doaminNAme);
       // remove filters
        baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
        reports.navigateToReportsPage("Concept Explorer Report");
        // Select Sources
        conceptExplorer.clickSelectSources();
        conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);
       // Apply filter
        baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
        conceptExplorer.filterAction(emailrecep1, "EmailRecipientNames", emailrecep2, false);
        conceptExplorer.filterAction(doaminNAme, "EmailAllDomains", null, false);
        conceptExplorer.applyFilter("Yes", 10);
       baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
        baseClass.stepInfo("Report Generated.");
       // adding tiles to cart
        conceptExplorer.addMultipleTilesToCart(2);
       // Perform View in DocView List Action
        conceptExplorer.performDocActions("View", "View in DocList");
        baseClass.waitTime(4);
        baseClass.verifyPageNavigation("en-us/Document/DocList");
       // validation of exclude filter functionality
        dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
        List<String> emailAllDomain_Excl = dlPage.getColumnValue("EmailAllDomains", false);
        List<String> emailRecipients_Excl = dlPage.getColumnValue("EmailRecipientNames", false);
        conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailRecipients_Excl, emailAllDomain_Excl,
                emailrecep1, emailrecep2, doaminNAme);
        conceptExplorer.getBackToSourceBtn().Click();
        driver.waitForPageToBeReady();
        // Select tile to analyze at second level
        baseClass.stepInfo("**Select tile to analyze at second level**");
        baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
        resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
        conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 3);
        // Go to 2nd level
        List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
        conceptExplorer.analyzeAction(analyze);
        baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
                "Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
        conceptExplorer.verifyActiveFilters(expActiveFilters);
        // Select tile to analyze at third level
        baseClass.stepInfo("**Select tile to analyze at third level**");
        baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
        resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
        conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 1);
       // Go to 3rd level
        conceptExplorer.analyzeAction(analyze3);
        baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
                "Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
        conceptExplorer.verifyActiveFilters(expActiveFilters);
       // Logout
        loginPage.logout();
	}
	/**
	 * @author Jayanthi.Ganesan
	 * @param User
	 * @param pwd
	 * @throws Exception
	 */
	

	@Test(description="RPMXCON-56906",enabled = true, dataProvider = "paRmu", groups = { "regression" })
	public void verifyFilters_EmailAllDomains_DocFileType(String User, String pwd) throws Exception {

		baseClass.stepInfo("Test case Id :RPMXCON-56906");
		baseClass.stepInfo("Validate onpage filter for EmailAuthorName  and DocFileType on Concept Explorer Report");

		String analyze = Input.analyzeAt2;
		String analyze3 = Input.analyzeAt3;
		String sourceToSelect = "Security Groups";
		String emailDomain= "gmail.com";
		String emailD0main_1 ="symphonyteleca.com";
		String fileType= "MS Outlook Message";
		
		String[] columnsToSelect = { Input.emailAllDomain,Input.ingDocFileType };

		baseClass.stepInfo("**Login to sightline and Select Project**");
		loginPage.loginToSightLine(User, pwd);

		// Navigate to Concept Explorer page
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the filter criteria and click “Apply filter”");
		
		conceptExplorer.filterAction(emailDomain, Input.emailAllDomain,emailD0main_1, true);
		conceptExplorer.filterAction(fileType, Input.docFileType, null, true);
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());

		// Get Doc count consolidated
		String totalSelectedDocCount = conceptExplorer.getDocCOuntFromHeader().getText();
		String aggregatedDocCount = conceptExplorer.extractStringFromPosition(totalSelectedDocCount, 2);
		baseClass.passedStep("Report Generated with doc count : " + aggregatedDocCount + " which is expected");

		// tiles add to cart
		int resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.addMultipleTilesToCart(resultToAddInCart);

		// view in doc list to verify filters applied returns the docs correctly.
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(4);
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain = dlPage.getColumnValue(Input.emailAllDomain, false);
		List<String> docFileType = dlPage.getColumnValue(Input.docFileType, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailAllDomain,docFileType, emailD0main_1,
				emailDomain, fileType);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click concept explorer report button");
		reports.navigateToReportsPage("Concept Explorer Report");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailDomain, Input.emailAllDomain,emailD0main_1, false);
		conceptExplorer.filterAction(fileType, Input.docFileType, null, false);
		conceptExplorer.applyFilter("Yes", 10);

		baseClass.waitForElement(conceptExplorer.getDocCOuntFromHeader());
		baseClass.stepInfo("Report Generated.");

		// adding tiles to cart
		conceptExplorer.addMultipleTilesToCart(2);

		// Perform View in DocView List Action
		conceptExplorer.performDocActions("View", "View in DocList");
		baseClass.waitTime(4);
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// validation of exclude filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain_Excl = dlPage.getColumnValue(Input.emailAllDomain, false);
		List<String> docFileType_Excl = dlPage.getColumnValue(Input.docFileType, false);
		conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailAllDomain_Excl,docFileType_Excl, emailD0main_1,
				emailDomain, fileType);
		
		conceptExplorer.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();

		// Select tile to analyze at second level
		baseClass.stepInfo("**Select tile to analyze at second level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 3);
		// Go to 2nd level
		List<String> expActiveFilters = conceptExplorer.verifyActiveFilters(null);
		conceptExplorer.analyzeAction(analyze);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("2nd")),
				"Page navigated to second level and report generated", "Page didn't navigate to second level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Select tile to analyze at third level
		baseClass.stepInfo("**Select tile to analyze at third level**");
		baseClass.waitForElementCollection(conceptExplorer.getDataToAddInCart());
		resultToAddInCart = conceptExplorer.getDataToAddInCart().size();
		conceptExplorer.tileSelctionBasedOnChildCount(resultToAddInCart, 1);

		// Go to 3rd level
		conceptExplorer.analyzeAction(analyze3);
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(conceptExplorer.getPageLevel("3rd")),
				"Page navigated to third level and report generated", "Page didn't navigate to third level", "Pass");
		conceptExplorer.verifyActiveFilters(expActiveFilters);

		// Logout
		loginPage.logout();

	}

	@DataProvider(name = "paRmu")
	public Object[][] paRmu() {
		Object[][] users = { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password} };
		return users;
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
