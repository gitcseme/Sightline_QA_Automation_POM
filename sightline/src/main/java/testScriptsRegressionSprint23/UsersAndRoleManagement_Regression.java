package testScriptsRegressionSprint23;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.MiniDocListPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class UsersAndRoleManagement_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	UserManagement userManage;
	SessionSearch sessionSearch;
	DocViewPage docViewPage;
	SoftAssert softAssertion;
	MiniDocListPage miniDocListPage;

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

	@DataProvider(name = "sapa")
	public Object[][] SaPa() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "sa" },
				{ Input.pa1userName, Input.pa1password, "pa" } };
	}

	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :To verify user can have 'Enable', 'Disable' options on bulk user
	 * rights
	 */

	@Test(description = "RPMXCON-52552", dataProvider = "sapa", alwaysRun = true, groups = { "regression" })
	public void validateBulkUserRadioOptions(String userName, String password, String role) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52552");
		baseClass.stepInfo("To verify user can have 'Enable', 'Disable' options on bulk user rights");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As sa
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Login as : " + role + "");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);

		// verifying Enable and Disable option
		driver.scrollingToElementofAPage(userManage.getEnableRole());
		boolean roleEnable = userManage.getEnableRole().GetAttribute("role").contains("radio");
		softAssertion.assertTrue(roleEnable);
		baseClass.stepInfo("Enable Radio button options available in bulk user popup");
		driver.scrollingToElementofAPage(userManage.getDisableRole());
		boolean roleDisable = userManage.getDisableRole().GetAttribute("role").contains("radio");
		softAssertion.assertTrue(roleDisable);
		baseClass.stepInfo("Disable Radio button options available in bulk user popup");
		softAssertion.assertAll();
		baseClass.passedStep("Both Enable and disable radio button are available in bulk user popup window");
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify when user changes the security group from the header drop
	 * down
	 */

	@Test(description = "RPMXCON-52676", alwaysRun = true, groups = { "regression" })
	public void validateBulkUserRadioOptions() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52676");
		baseClass.stepInfo("Verify when user changes the security group from the header drop down");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		security = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV and rmu
		userManage.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManage.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);
		int count = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		System.out.println(count);
		sessionSearch.bulkRelease(securityGroup);
		// logout
		loginPage.logout();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// selecting sg
		baseClass.selectsecuritygroup(securityGroup);
		baseClass.stepInfo("Changing the security group");

		// validation
		String releasedCount = userManage.getReleasedCount().getText();
		softAssertion.assertEquals(count, Integer.parseInt(releasedCount));
		baseClass.passedStep("user under the new created sg which selected at top header for rmu user");
		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting sg
		baseClass.selectsecuritygroup(securityGroup);
		baseClass.stepInfo("Changing the security group");

		// validation
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getReleasedCountRev());
		String releasedCountRev = userManage.getReleasedCountRev().getText();
		int actualRev = Integer.parseInt(releasedCountRev.replaceAll("[\\D]", ""));
		softAssertion.assertEquals(count, actualRev);
		baseClass.passedStep("user under the new created sg which selected at top header for rev user");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify security group from change role pop up when user changes
	 * security group from header
	 */

	@Test(description = "RPMXCON-52677", alwaysRun = true, groups = { "regression" })
	public void validateSGFromChangeRolePopUp() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52677");
		baseClass.stepInfo(
				"Verify security group from change role pop up when user " + "changes security group from header");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		security = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV and rmu
		userManage.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// selecting sg
		baseClass.selectsecuritygroup(securityGroup);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Changing the security group");

		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getChangeRole());
		baseClass.getChangeRole().waitAndClick(10);
		baseClass.waitForElement(baseClass.getSelectRole());
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		baseClass.waitForElement(baseClass.getAvlDomain());
		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitForElement(baseClass.getAvlProject());
		baseClass.getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		
		// validation in change role sg
		boolean flag = baseClass.dropDownValueCheck(baseClass.getSelectSecurityGroup(), securityGroup);
		softAssertion.assertTrue(flag);
		baseClass.passedStep("Changed Sg in header top value are displayed in change role popup window");
		softAssertion.assertAll();
		
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
			// loginPage.logout();
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
