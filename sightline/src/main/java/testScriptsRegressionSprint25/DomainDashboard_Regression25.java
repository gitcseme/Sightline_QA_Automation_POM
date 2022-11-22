package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainDashboard_Regression25 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	UserManagement userManage;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
	DomainDashboard domainDashboard;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-53118 Description
	 *         Verify Active users widget should not be visible for RMU/Reviewer
	 *         user
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53118", enabled = true, groups = { "regression" })
	public void verifyAddingExistingUserUnderSameProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53118");
		baseClass.stepInfo("Verify Active users widget should not be visible for RMU/Reviewer user");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as " + Input.rmu1userName);

		DomainDashboard dash = new DomainDashboard(driver);

		driver.waitForPageToBeReady();
		dash.getEditIcon().waitAndClick(5);
		baseClass.waitForElement(dash.getSelectAddWidget());
		dash.getSelectAddWidget().waitAndClick(5);
		
		baseClass.stepInfo("verifying Active user invisiblity in Widget");
		if (!dash.getWidget().isElementAvailable(2)) {
			baseClass.passedStep(
					"Active user widget is not visible in " + Input.rmu1userName + " dashboard as expected");
		} else {
			baseClass.failedStep("Active user widget is  visible in " + Input.rmu1userName + " dashboard as expected");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-53481 Description
	 *         Validate CustodianNames value
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53481", enabled = true, groups = { "regression" })
	public void validatingCustodiansNamesValue() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53481");
		baseClass.stepInfo("Validate CustodianNames value");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as " + Input.da1userName);
		baseClass.selectproject(Input.domainName);
		
		DomainDashboard dash = new DomainDashboard(driver);
		String FilterPrjt= Input.projectName;
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Selecting the project");
		dash.filterProject(FilterPrjt);
		
		baseClass.waitTime(3);
		baseClass.stepInfo("Verifying the presence of custodians for prject in beehive table");
		List<WebElement> element = dash.getColumValue(baseClass.getIndex(dash.getTableHeader(), "CUSTODIANS (#)"))
				.FindWebElements();
		String Custodians = element.get(0).getText().trim();
		System.out.println(Custodians);
		if (Integer.valueOf(Custodians)>=1 && Integer.valueOf(Custodians) != null) {
			baseClass.passedStep("custodians is displayed for selected project");
		} else {
			baseClass.failedStep("custodians is not displayed for selected project");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-53146 
	 * Description To verify if user customize the column list but does
	 *  not save the changes,then previous list should be displayed when 
	 *  user login for the same domain
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53146", enabled = true, groups = { "regression" })
	public void validatingProjectFilter() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53146");
		baseClass.stepInfo("To verify if user customize the column list but does not save the changes,then previous list should be displayed when user login for the same domain");
		
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as " + Input.da1userName);
		
		baseClass.selectdomain(Input.domainName);
		userManage = new UserManagement(driver);
		
		DomainDashboard dash = new DomainDashboard(driver);
		String[] columns = {"NoOfSecurityGroup","NoOfIngestion"};
		driver.waitForPageToBeReady();

		baseClass.stepInfo("adding the columns in beehive table and save");
		dash.AddOrRemoveColum(columns);
		dash.getSavebtn().waitAndClick(10);

		baseClass.stepInfo("Removing the column without saving it");
		driver.waitForPageToBeReady();
		dash.AddOrRemoveColum(columns);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Relogin as " + Input.da1userName);
		
		baseClass.waitTime(3);
		baseClass.stepInfo("Verifying the presence of selected column in beehive table");
		List<String> tableheadervalues = dash.getColumValues(dash.getTableHeader());
		System.out.println(tableheadervalues);
		
		baseClass.waitTime(2);
		if(tableheadervalues.contains("SECURITY GROUPS (#)")&& tableheadervalues.contains("INGESTIONS (#)")) {
			baseClass.passedStep("User is presented with previous existing column list");
		}else {
			baseClass.failedStep("User is not presented with previous existing column list");
		}
		
		driver.waitForPageToBeReady();
		dash.AddOrRemoveColum(columns);
		dash.getSavebtn().waitAndClick(10);
		
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
		try {
			// LoginPage.clearBrowserCache();
		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
