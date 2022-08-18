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
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CommunicationExplorerRegression_2_19 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	CommunicationExplorerPage communicationExpPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
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
		communicationExpPage = new CommunicationExplorerPage(driver);
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
	 * @Description : Validate Masterdate range on Communication Explorer report.
	 *              RPMXCON-56886
	 */
	@Test(description = "RPMXCON-56886", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyMasterDateFiltersFunctionality_CommunicationExplorer(String userName, String password,
			String role) throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56886");
		baseClass.stepInfo("Validate Masterdate range on Communication Explorer report");

		String searchText = Input.searchString10;
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
		communicationExpPage.masterDateVerifications(selectSourceList, sourceToSelect, sgToSelect, savedSearchName,
				expectedDateInput, expectedToDateInput, conditionsToCheck);

		// Delete search
		savedSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @Description : Validate onpage filter for EmailRecipientNames and
	 *              EmailAllDomains on Communication Explorer Report
	 */
	@Test(description = "RPMXCON-56898", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_CommunicationExplorer(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56898");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames  and EmailAllDomains on Communication Explorer Report");

		String sourceToSelect = "Security Groups";

		// Login 
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { "EmailAllDomains", "EmailRecipientNames" };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");

		conceptExplorer.filterAction("Gouri Dhavalikar", "EmailRecipientNames", "swapnal sonawane", true);
		conceptExplorer.filterAction("consilio.com", "EmailAllDomains", null, true);

		communicationExpPage.clickApplyBtn();
		// Select nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);
		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain = dlPage.getColumnValue("EmailAllDomains", false);
		List<String> emailRecipients = dlPage.getColumnValue("EmailRecipientNames", false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailRecipients, emailAllDomain, "Gouri Dhavalikar",
				"swapnal sonawane", "consilio.com");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction("Gouri Dhavalikar", "EmailRecipientNames", "swapnal sonawane", false);
		conceptExplorer.filterAction("consilio.com", "EmailAllDomains", null, false);
		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 2);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAllDomain_Excl = dlPage.getColumnValue("EmailAllDomains", false);
		List<String> emailRecipients_Excl = dlPage.getColumnValue("EmailRecipientNames", false);
		conceptExplorer.verifyExcludeFiltersLikeOR_Operator(emailRecipients_Excl, emailAllDomain_Excl,
				"Gouri Dhavalikar", "swapnal sonawane", "consilio.com");
		conceptExplorer.getBackToSourceBtn().Click();
		driver.waitForPageToBeReady();

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
