package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ManageIPRestrictions;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ManageIPRestrictions_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;
	DocViewPage docview;
	SoftAssert soft;
	ManageIPRestrictions manageIp;

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
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	
	/**
	 * @author Vijaya.Rani ModifyDate:05/09/2022 RPMXCON-52450
	 * @throws Exception
	 * @Description To verify when Sys Admin/Domain Admin Impersonate as Project
	 *              Admin and deletes IP settings.
	 */
	@Test(description = "RPMXCON-52450", enabled = true, groups = { "regression" })
	public void verifySAImpersonatePADeletesIP() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52450");
		base.stepInfo(" To verify when Sys Admin/Domain Admin Impersonate as Project Admin and deletes IP settings.");
		manageIp = new ManageIPRestrictions(driver);
		String from = "10.55.79.25";
		String to = "10.55.79.50";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		base.stepInfo("Impersonate SA to PA");
		base.impersonateSAtoPA();
		
		base.stepInfo("Add IP Range");
		manageIp.createIPrange(from, to);
		base.passedStep("IP range is added into the grid");
		
		base.stepInfo("Delete IP Range");
		manageIp.deleteIPrange(from);
		base.passedStep("IP range is deleted ");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/09/2022 RPMXCON-52445
	 * @throws Exception
	 * @Description To verify after clicks on 'Cancel' button, IP Range record should not be saved.
	 */
	@Test(description = "RPMXCON-52445", enabled = true, groups = { "regression" })
	public void verifyAddIPandCancelButton() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52445");
		base.stepInfo(" To verify after clicks on 'Cancel' button, IP Range record should not be saved.");
		manageIp = new ManageIPRestrictions(driver);
		String from = "10.55.79.75";
		String to = "10.55.79.90";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		base.stepInfo("Add IP Range");
		manageIp.createIPrange(from, to);
		base.passedStep("IP range is added into the grid");
		
		base.stepInfo("Click the cancel Btn ");
		driver.waitForPageToBeReady();
		base.waitForElement(manageIp.getCancelBtn());
		manageIp.getCancelBtn().waitAndClick(5);
		driver.Navigate().refresh();
		if(!manageIp.getIPValue(from).isDisplayed()) {
		    base.passedStep("Record not saved. Page reload and record is not displayed on the Grid.");
		}else {
			base.failedStep("Record Displayed on the Grid");
		}
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:05/09/2022 RPMXCON-52444
	 * @throws Exception
	 * @Description To verify error message is displayed if user add duplicate IP range.
	 */
	@Test(description = "RPMXCON-52444", enabled = true, groups = { "regression" })
	public void verifyErrorMsgAddDuplicateIPRange() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-52444");
		base.stepInfo(" To verify error message is displayed if user add duplicate IP range.");
		manageIp = new ManageIPRestrictions(driver);
		String from = "10.55.79.30";
		String to = "10.55.79.80";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		base.stepInfo("Add IP Range");
		manageIp.createIPrange(from, to);
		base.passedStep("IP range is added into the grid");
		
		driver.waitForPageToBeReady();
		manageIp.createIPrangeErrorMsgduplicate(from, to);
		base.passedStep("Error message is displayed as IPRange already exists");
		
		base.stepInfo("Delete IP Range");
		manageIp.deleteIPrange(from);
		
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
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
		System.out.println("******TEST CASES FOR MANAGEIPRESTRICTIONS EXECUTED******");

	}

}
