package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Domain_Management23 {
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
	 * @author Brundha.T RPMXCON-52902
	 * @Description :Verify when Domain Admin selects role as 'Review
	 *              Manage'/'Reviewer' from Change Role pop up
	 */
	@Test(description = "RPMXCON-52902", enabled = true, groups = { "regression" })
	public void verifyRightInGrayForSelectedRole() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52902");
		baseClass.stepInfo(
				"Verify when Domain Admin selects role as 'Review Manage'/'Reviewer' from Change Role pop up");
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");

		DomainDashboard dash = new DomainDashboard(driver);
		baseClass.waitTillElemetToBeClickable(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().Click();
		baseClass.waitTillElemetToBeClickable(baseClass.getChangeRole());
		baseClass.ValidateElement_Presence(baseClass.getChangeRole(), "Impersonate popup");
		baseClass.getChangeRole().Click();
		dash.validatingChangeRoleOptionInRMUAndReviewer(Input.Reviewer);
		dash.validatingChangeRoleOptionInRMUAndReviewer(Input.ReviewManager);
		loginPage.logout();
	}
	/**
	 * @author Brundha.T RPMXCON-52810
	 * @Description :To verify that When a user applies a Filter by Type as
	 *              "Domain", the grid list displays all clients that are of type
	 *              "domain"
	 */
	@Test(description = "RPMXCON-52810", enabled = true, groups = { "regression" })
	public void ApplyFilterForDomain() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52810");
		baseClass.stepInfo(
				"To verify that When a user applies a Filter by Type as 'Domain', the grid list displays all clients that are of type 'domain'");
		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.sa1userName + "'");
		String DropDownValue = "Domain";

		UserManagement User = new UserManagement(driver);

		baseClass.stepInfo("Applying filter for Domain");
		User.applyingFilterInClient(DropDownValue, false);

		baseClass.stepInfo("verifying Applied filter In Domain");
		int Domain = baseClass.getIndex(User.getTableHeaderInDomainClient(), "TYPE");
		System.out.println(Domain);
		List<WebElement> DominValues = User.getColumnValueinDomainClient(Domain).FindWebElements();
		for (WebElement webElement : DominValues) {
			String DomainType = webElement.getText();
			System.out.println(DomainType);
			if (DomainType.equals(DropDownValue)) {
				baseClass.passedStep("" + DropDownValue + " is displayed in grid view");
			} else {
				baseClass.failedStep("" + DropDownValue + " not displayed");
			}
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-52834
	 * @Description :Verify error/validation message should be displayed when all
	 *              required fields are blank
	 */
	@Test(description = "RPMXCON-52834", enabled = true, groups = { "regression" })
	public void ValidatingErrorMsgInAddNewUsers() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52834");
		baseClass.stepInfo("Verify error/validation message should be displayed when all required fields are blank");
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");

		UserManagement user = new UserManagement(driver);
		baseClass.stepInfo("Navigating to Domain page");
		user.navigateToUsersPAge();
		
		baseClass.stepInfo("selecting Add new User button and save");
		baseClass.waitTillElemetToBeClickable(user.getAddUserBtn());
		user.getAddUserBtn().Click();
		baseClass.waitTillElemetToBeClickable(user.getSave());
		user.getSave().waitAndClick(10);
		
		baseClass.stepInfo("verifying Error message");
		baseClass.validatingGetTextElement(user.getFirstNameError(),Input.FirstNameError);
		baseClass.validatingGetTextElement(user.getLastNameError(), Input.LastNameError);
		baseClass.validatingGetTextElement(user.getRoleError(),Input.RoleError);
		baseClass.validatingGetTextElement(user.getEmailAddressError(),Input.EmailAddressError);

		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-52803
	 * @Description :Verify that Role drop down shows roles as Project
	 *              Administrator, Review Manager and Reviewer
	 */
	@Test(description = "RPMXCON-52803", enabled = true, groups = { "regression" })
	public void verifyingThePresenceOfUsers() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52803");
		baseClass.stepInfo(
				"Verify that Role drop down shows roles as Project Administrator, Review Manager and Reviewer");
		String[] username = { Input.da1userName, Input.sa1userName, Input.pa1userName };
		String[] password = { Input.da1password, Input.sa1password, Input.pa1password };

		for (int i = 0; i < username.length; i++) {
			loginPage.loginToSightLine(username[i], password[i]);
			baseClass.stepInfo("User successfully logged into slightline webpage as with " + username[i]);

			String[] Users = { Input.ProjectAdministrator, Input.ReviewManager, Input.Reviewer };
			UserManagement user = new UserManagement(driver);
			ArrayList<String> DropDownValues = user.ValidatingBulkUserAccessControl();
			driver.waitForPageToBeReady();
			if (DropDownValues.equals(Arrays.asList(Users))) {
				baseClass.passedStep(Arrays.asList(Users) + " displayed as expected");
			} else {
				baseClass.failedStep(Arrays.asList(Users) + " is not displayed");
			}

			if (username[i].equals(Input.pa1userName)) {
				String BulkUserPrjt = user.getPrjtField().GetAttribute("readonly");
				baseClass.textCompareEquals(BulkUserPrjt, "true", "Bulk user project is disabled ",
						"Bulk user project  not disabled");
				String BulkUserSecurityGroup = user.getBulkUserSecurityGroup().GetAttribute("disabled");
				baseClass.textCompareEquals(BulkUserSecurityGroup, "true", "Security Group is disabled",
						"Security Group not disabled");
				System.out.println("verified for " + username[i]);
			} else {
				user.verifyingReadonlyInProjectAndSecurityGrp();
			}

			loginPage.logout();

		}
	}

	/**
	 * @author Brundha.T RPMXCON-52993
	 * @Description :To verify that Domain Admin user impersonate as RMU in current
	 *              logged in Domain successfully
	 */
	@Test(description = "RPMXCON-52993", enabled = true, groups = { "regression" })
	public void verifyingImpersonatingFromDAToRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52993");
		baseClass.stepInfo(
				"To verify that Domain Admin user impersonate as RMU in current logged in Domain successfully");
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");
		DomainDashboard dash = new DomainDashboard(driver);

		baseClass.stepInfo("Impersonating DA as RMU");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(5);
		baseClass.getChangeRole().waitAndClick(10);
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText(Input.ReviewManager);
		baseClass.waitForElement(baseClass.getAvlDomain());
		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		driver.waitForPageToBeReady();
		int NoOfPrjt = baseClass.getAvlProject().selectFromDropdown().getOptions().size();
		System.out.println("Prjt no" + NoOfPrjt);
		if (NoOfPrjt >= 2) {
			baseClass.passedStep("Projects are available as expected");
		} else {
			baseClass.failedStep("Projects are not available");
		}
		driver.waitForPageToBeReady();
		baseClass.getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		baseClass.waitForElement(baseClass.getSelectSecurityGroup());
		baseClass.getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(Input.securityGroup);
		baseClass.getSaveChangeRole().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verifying RMU Landing page");
		if (dash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
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
