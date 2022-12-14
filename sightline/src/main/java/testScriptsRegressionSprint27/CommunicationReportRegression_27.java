package testScriptsRegressionSprint27;

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

public class CommunicationReportRegression_27 {
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

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } 
		};
		return users;
	}

	/**
	 * @author Jeevitha
	 * @Description : Validate onpage filter for EmailAuthorDomains and
	 *              EmailAllDomain on Communication Explorer Report [RPMXCON-56903]
	 */
	@Test(description = "RPMXCON-56903", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_EmailAuthor(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String sourceToSelect = "Security Groups";
		String[] columnsToSelect = { Input.MetaDataEAName, Input.emailAllDomain };

		baseClass.stepInfo("Test case Id: RPMXCON-56903 Report/Communication Map");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorDomains and EmailAllDomain on Communication Explorer Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter As EmailAuthorName: Exclude EmailAllDomain: Include
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.emailAllDomainOption, Input.MetaDataEAName, Input.emailReciepientName2,
				false);
		conceptExplorer.filterAction(Input.domainNameConsilio, Input.emailAllDomain, null, true);
		communicationExpPage.clickApplyBtn();

		// Select All nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for exclude & include filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("ExcludeAndInclude", Input.MetaDataEAName, Input.emailAllDomain,
				Input.emailAllDomainOption, Input.emailReciepientName2, Input.domainNameConsilio, false, "");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter as EmailAuthorName: Include EmailAllDomain: Exclude
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.emailAllDomainOption, Input.MetaDataEAName, Input.EmailAuthourName, true);
		conceptExplorer.filterAction(Input.MetaDataDomainName, Input.emailAllDomain, null, false);

		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select All node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude & include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("IncludeAndExclude", Input.MetaDataEAName, Input.emailAllDomain,
				Input.emailAllDomainOption, Input.EmailAuthourName, Input.MetaDataDomainName, false, "");

		// Logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Validate on page filter for EmailAllDomains and MasterDate on
	 *              Communication Explorer Report [RPMXCON-56905]
	 */
	@Test(description = "RPMXCON-56905", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_Masterdate(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String sourceToSelect = "Security Groups";
		String[] columnsToSelect = { Input.masterDateText, Input.emailAllDomain };
		String masterDate = "1980/01/01";

		baseClass.stepInfo("Test case Id: RPMXCON-56905 Report/Communication Map");
		baseClass
				.stepInfo("Validate onpage filter for EmailAllDomains and MasterDate on Communication Explorer Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter As MasterDate : ON EmailAllDomain: Include
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.domainNameConsilio, Input.emailAllDomain, Input.domainNameSymphony, true);

		// Masterdate selection
		communicationExpPage.masterDateSelection(masterDate, "", "On");
		communicationExpPage.clickApplyBtn();

		// Select All nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();

		// validation for include & include filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("IncludeAndInclude", Input.emailAllDomain, Input.masterDateText,
				Input.domainNameConsilio, Input.domainNameSymphony, masterDate, false, "");

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		driver.waitForPageToBeReady();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter as MasterDate : ON EmailAllDomain: Exclude
		baseClass.stepInfo("** Set the Exclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(Input.MetaDataDomainName, Input.emailAllDomain, Input.filterDataInput3, false);

		// Masterdate selection
		communicationExpPage.masterDateSelection(masterDate, "", "On");

		communicationExpPage.clickApplyBtn();
		baseClass.stepInfo("Report Generated.");

		// Select All node to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), null);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), null);

		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		baseClass.waitTime(3);
		baseClass.verifyPageNavigation("en-us/Document/DocList");
		driver.waitForPageToBeReady();

		// validation of exclude & include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		conceptExplorer.filterValidation("ExcludeAndInclude", Input.emailAllDomain, Input.masterDateText,
				Input.MetaDataDomainName, Input.filterDataInput3, masterDate, false, "");

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
