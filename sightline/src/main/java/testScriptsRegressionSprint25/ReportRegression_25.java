package testScriptsRegressionSprint25;

import java.awt.AWTException;
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
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportRegression_25 {

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
	SearchTermReportPage searchterm;

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
		searchterm = new SearchTermReportPage(driver);
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that in Search Term Report page Searches criteria is
	 *              mandatory. [RPMXCON-56365]
	 */
	@Test(description = "RPMXCON-56365", groups = { "regression" }, enabled = true)
	public void verifySTRPageSearchIsMandatory() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56365 Reports/Search Term");
		baseClass.stepInfo("To verify that in Search Term Report page Searches criteria is mandatory.");

		// Login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Click Apply button without selecting any search
		reports.navigateToReportsPage("Search Term Report");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(searchterm.mySavedSearchCheckbox());
		baseClass.waitTillElemetToBeClickable(searchterm.getApplyBtn());
		searchterm.getApplyBtn().waitAndClick(10);

		// verify Error message
		baseClass.VerifyErrorMessage("Please select at least one search.");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that if there is no data is STR then message is
	 *              displayed to User. [RPMXCON-56366]
	 */
	@Test(description = "RPMXCON-56366", groups = { "regression" }, enabled = true)
	public void verifyNoDatainSTRThenMsgDisplayed() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56366 Reports/Search Term");
		baseClass.stepInfo("To verify that if there is no data is STR then message is displayed to User.");

		// Login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		//create empty search group
		String searchGrp = savedSearch.createNewSearchGrp(Input.mySavedSearch);
		String[] selectSearchGrp = { searchGrp};

		// Select empty search group and Click Apply button
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(selectSearchGrp, false);

		// verify Error message
		baseClass.VerifyErrorMessage("Please select valid search. Report will not generate for empty group");

		// logout
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
		System.out.println("**Executed Communication explorer sprint 21**");
	}
}
