package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainDashboardRegression_25_1 {

	Driver driver;
	LoginPage login;
	BaseClass base;
	SoftAssert softAssertion;
	Dashboard dashBoard;
	SessionSearch session;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		dashBoard = new Dashboard(driver);
		session = new SessionSearch(driver);

	}

	/**
	 * @author Sakthivel RPMXCON-53161
	 * @Description :Validate Project filter
	 */
	@Test(description = "RPMXCON-53161", enabled = true, groups = { "regression" })
    public void verifyingProjectFilter() throws Exception {
 
        base.stepInfo("Test case Id: RPMXCON-53161");
        base.stepInfo("Validate Project filter");
        DomainDashboard domainDash = new DomainDashboard(driver);
        // login as DAU
        login.loginToSightLine(Input.da1userName, Input.da1password);
        base.stepInfo("Successfully login as da user'" + Input.da1userName + "'");
        base.stepInfo("Domain dashboard  is loaded by default as expected");
        driver.waitForPageToBeReady();
        base.selectdomain(Input.domainName);
        base.waitTime(5);
        domainDash.filterProject(Input.projectName);
        base.waitForElement(domainDash.projectStatusFirstValue());
        String actualActiveStatus = domainDash.projectStatusFirstValue().getText();
        if(actualActiveStatus.equalsIgnoreCase("active")) {
            base.passedStep("The staus of active project is displayed ACTIVE as expected");
        }else {
            base.failedStep("The staus of active project is not displayed as ACTIVE");
        }    
        domainDash.clearProjectSearchFilter();
        driver.scrollingToElementofAPage(domainDash.getInactiveProjectToggle());
        domainDash.getInactiveProjectToggle().waitAndClick(10);
        driver.waitForPageToBeReady();
        domainDash.projectStatusTab().waitAndClick(10);
        base.waitTime(5);
        String status = domainDash.projectStatusFirstValue().getText();
        System.out.println(status);
        if (status.equalsIgnoreCase("inactive")) {
            base.waitTime(5);
            String projectname = domainDash.projectNameFirstValue().getText();
            System.out.println(projectname);
            base.waitTime(10);
            domainDash.filterProject(projectname);
            base.waitForElement(domainDash.projectStatusFirstValue());
            base.waitTime(10);
            String actualInActiveStatus = domainDash.projectStatusFirstValue().getText();
            System.out.println(actualInActiveStatus);
            if(actualInActiveStatus.equalsIgnoreCase("inactive")) {
                base.passedStep("The staus of inactive project is displayed INACTIVE as expected");
            }else {
                base.failedStep("The staus of inactive project is not displayed as INACTIVE");
            }    
        } else {
            base.passedStep("No inactive projects in Domain");
        }
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
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");
	}

}
