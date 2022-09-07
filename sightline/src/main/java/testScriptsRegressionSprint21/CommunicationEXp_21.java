package testScriptsRegressionSprint21;

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

public class CommunicationEXp_21 {
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
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

		/**
	 * @author Jayanthi
	 * @Description :Validate onpage filter for EmailAuthorName and EmailRecipientNames on Communication Explorer Report
	 */
	
	@Test(description = "RPMXCON-56896", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_EANAme_ERNAme(String userName, String password, String role)
			throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-56896");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorName and EmailRecipientNames on Communication Explorer Report");
		String emailreciepient1 = Input.masterDateOption;
		String emailreciepient2 = Input.emailAllDomainOption;

		String emailAuthourName = Input.emailRecipientName1;
		String sourceToSelect = "Security Groups";

		// Login
		baseClass.stepInfo("**Step-1 Login as " + role + " **");
		loginPage.loginToSightLine(userName, password);
		String[] columnsToSelect = { Input.emailRecipientName, Input.MetaDataEAName };

		// Select Sources
		reports.navigateToReportsPage("Communications Explorer");
		conceptExplorer.clickSelectSources();
		baseClass.stepInfo("** Select Security group as source and save selection");
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply filter
		baseClass.stepInfo("** Set the Include filter criteria and click “Apply filter”");

		conceptExplorer.filterAction(emailreciepient1, Input.emailRecipientName, emailreciepient2, true);
		conceptExplorer.filterAction(emailAuthourName, Input.MetaDataEAName, null, true);

		communicationExpPage.clickApplyBtn();
		
		// Select nodes to view in doc list
		communicationExpPage.selectAllListedDatas(communicationExpPage.getSmallerNode(),
				communicationExpPage.getSmallerNodesList(), 2);
		communicationExpPage.selectAllListedDatas(communicationExpPage.getNormalNode(),
				communicationExpPage.getNormalNodesList(), 3);
		
		// Perform View in DocView List Action
		communicationExpPage.viewinDoclist();
		
		// validation for inlcude filters
		DocListPage dlPage = new DocListPage(driver);

		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailAuthourNAme = dlPage.getColumnValue(Input.MetaDataEAName, false);
		List<String> emailRecipientName_Incl = dlPage.getColumnValue(Input.emailRecipientName, false);
		conceptExplorer.verifyIcludeFiltersLikeOR_Operator(emailRecipientName_Incl, emailAuthourNAme, emailreciepient1, emailreciepient2,
				emailAuthourName);

		// remove filters
		baseClass.stepInfo("** navigate from Reports - Click Communications explorer report button");
		reports.navigateToReportsPage("Communications Explorer");

		// Select Sources
		conceptExplorer.clickSelectSources();
		conceptExplorer.selectSGsource(sourceToSelect, Input.securityGroup);

		// Apply Exclude filter
		baseClass.stepInfo("** Set the Exclude/Iclude filter criteria and click “Apply filter”");
		conceptExplorer.filterAction(emailreciepient1, Input.emailRecipientName, emailreciepient2, false);
		conceptExplorer.filterAction(emailAuthourName, Input.MetaDataEAName, null, true);
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

		// validation of exclude/Include filter functionality
		dlPage.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		List<String> emailReciepients_Excl = dlPage.getColumnValue(Input.emailRecipientName, false);
		List<String> emailAuthourName_Incl= dlPage.getColumnValue(Input.MetaDataEAName, false);
		conceptExplorer.verifyExcludeIcludeFiltersLikeOR_Operator(emailReciepients_Excl, emailAuthourName_Incl, emailreciepient1, emailreciepient2,
				emailAuthourName);
		
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
		System.out.println("**Executed Communication explorer sprint 21**");
	}
}
