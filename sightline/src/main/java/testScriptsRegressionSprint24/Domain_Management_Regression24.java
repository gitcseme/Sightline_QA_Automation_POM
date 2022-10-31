package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

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
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Domain_Management_Regression24 {
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
	 * @author Krishna RPMXCON-52865
	 * @Description :Verify that when sys admin impersonate as domain admin, logged
	 *              in session should behave as in the impersonated domain
	 */
	@Test(description = "RPMXCON-52865", enabled = true, groups = { "regression" })
	public void verifySAImpersonateDALoggedSessionBehave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52865");
		baseClass.stepInfo(
				"Verify that when sys admin impersonate as domain admin, logged in session should behave as in the impersonated domain");
		DomainDashboard dash = new DomainDashboard(driver);

		// login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as SA user'" + Input.sa1userName + "'");
		baseClass.impersonateSAtoDA();
		baseClass.stepInfo("SAU impersonate to DAU successfully");
		driver.waitForPageToBeReady();

		baseClass.waitForElement(dash.getdashboardtitle());
		if (dash.getdashboardtitle().isDisplayed()) {
			baseClass.passedStep(
					" SA impersonate as dA, logged in session behave as in the impersonated domain as expected ");

		} else {
			baseClass.failedStep("Logged session is failed");

		}
	}


	/**
	 * @author Brundha.T RPMXCON-52995
	 * @Description :To verify that Domain Admin user impersonate as Reviewer in
	 *              current logged in Domain successfully
	 */
	@Test(description = "RPMXCON-52995", enabled = true, groups = { "regression" })
	public void verifyingImpersonatingFromDAToReviewer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52995");
		baseClass.stepInfo(
				"To verify that Domain Admin user impersonate as Reviewer in current logged in Domain successfully");
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");
		baseClass.selectproject(Input.domainName);
		baseClass.stepInfo("Impersonating DA as Reviewer");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(5);
		baseClass.getChangeRole().waitAndClick(10);
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText(Input.Reviewer);
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
		baseClass.waitTime(1);
		baseClass.stepInfo("Verifying Reviewer Landing page");
		if (baseClass.text("Assignment Group").isDisplayed()) {
			baseClass.passedStep("Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T RPMXCON-52866
	 * @Description :Verify that sys admin after impersonating as Domain Admin
	 *              should be able to impesonate as Project admin
	 */
	@Test(description = "RPMXCON-52866", enabled = true, groups = { "regression" })
	public void verifyingImpersonatingFromSAtoDAandDatoPA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52866");
		baseClass.stepInfo(
				"Verify that sys admin after impersonating as Domain Admin should be able to impesonate as Project admin");
		// login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.sa1userName + "'");
		DomainDashboard dash = new DomainDashboard(driver);
		baseClass.stepInfo("Impersonating SA as DA");
		baseClass.impersonateSAtoDA(Input.domainName);

		baseClass.stepInfo("Verifying Domain Admin Landing page");
		baseClass.waitForElement(dash.getdashboardtitle());
		if (dash.getdashboardtitle().isDisplayed()) {
			baseClass.passedStep(
					" SA impersonate as dA, logged in session behave as in the impersonated domain as expected ");

		} else {
			baseClass.failedStep("Logged session is failed");

		}
		baseClass.stepInfo("Impersonating DA as PA");
		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(5);
		baseClass.getChangeRole().waitAndClick(10);
		baseClass.ValidateElement_Presence(baseClass.text(Input.DomainAdministrator), Input.DomainAdministrator);
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText("Project Administrator");
		baseClass.waitForElement(baseClass.getAvlDomain());
		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitForElement(baseClass.getSelectProjectTo());
		
		driver.waitForPageToBeReady();
		int ProjectList=baseClass.getSelectProjectTo().selectFromDropdown().getOptions().size();
		if(ProjectList>2) {
			baseClass.passedStep("List of Project available under project dropdown");
		}
		else {
			baseClass.failedStep("list of project is not available in project dropdown");
		}
		baseClass.getSelectProjectTo().selectFromDropdown().selectByVisibleText(Input.projectName);
		baseClass.getSaveChangeRole().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verifying ProjectAdministrator Landing page");

		if (baseClass.text("Doc Explorer").isDisplayed()) {
			baseClass.passedStep("Project Admin home page is displayed successfully");
		} else {
			baseClass.failedStep("Project Admin home page is not displayed ");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T,RPMXCON-52819
	 * @Description : To verify Domain Admin can change user rights in bulk for
	 *              Project Admin
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-52819", enabled = true, groups = { "regression" })
	public void verifyDomainAdminChangeRightsOfRmu() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52819");
		baseClass.stepInfo("To verify Domain Admin can change user rights in bulk for Project Admin");

		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a da user :" + Input.da1userName);
		baseClass.selectproject(Input.domainName);

		baseClass = new BaseClass(driver);
		UserManagement user = new UserManagement(driver);
		// get users
		user.navigateToUsersPAge();
		user.filterByName(Input.pa1userName);
		String PA1 = user.getfirstUserName();
		driver.Navigate().refresh();
		user.filterByName(Input.pa2userName);
		String PA2 = user.getfirstUserName();
		String[] users = { PA1, PA2 };

		// select role and enable
		baseClass.stepInfo("Enable users rights");
		user.enableOrDisableUsersRights("Enable", users);
		loginPage.logout();

		String[] username = { Input.pa1userName, Input.pa2userName };
		String[] password = { Input.pa1password, Input.pa2password };

		baseClass.stepInfo("verifying Enabled users rights");
		for (int i = 0; i < username.length; i++) {
			loginPage.loginToSightLine(username[i], password[i]);

			baseClass.stepInfo("Login as a pa user :" + username[i]);
			baseClass.ValidateElement_Presence(baseClass.text("Datasets"), "Datasets");
			baseClass.ValidateElement_Presence(baseClass.text("Categorize"), "Categorize");
			loginPage.logout();
		}
		
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a da user :" + Input.da1userName);
		// disable rights
		user.navigateToUsersPAge();
		baseClass.stepInfo("Disable users rights");
		user.enableOrDisableUsersRights("disable", users);
		loginPage.logout();

		baseClass.stepInfo("verifying disabled users rights");
		for (int j = 0; j < username.length; j++) {
			loginPage.loginToSightLine(username[j], password[j]);

			baseClass.stepInfo("Login as a pa user :" + username[j]);
			if(!baseClass.text("Datasets").isElementAvailable(2)) {
				baseClass.passedStep("Dataset is not available");
			}
			else {baseClass.failedStep("Dataset is available");}
			
			if(!baseClass.text("Categorize").isElementAvailable(2)) {
				baseClass.passedStep("Categorize is not available");
			}
			else {baseClass.failedStep("Categorize is available");}
			
			loginPage.logout();
		}
		baseClass.passedStep("verified Domain Admin can change user rights in bulk for PA");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		user.navigateToUsersPAge();
		user.enableOrDisableUsersRights("Enable", users);

		loginPage.logout();
	}
	/**
	 * @author Brundha.T RPMXCON-52852
	 * @Description :Validate new column “In Domain” on Project list screen for System Admin user
	 */
	@Test(description = "RPMXCON-52852", enabled = true, groups = { "regression" })
	public void verifyingSortingInColumn() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52852");
		baseClass.stepInfo("Validate new column “In Domain” on Project list screen for System Admin user");
		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.sa1userName + "'");

		ProjectPage prjt = new ProjectPage(driver);
		prjt.navigateToProductionPage();
		driver.waitForPageToBeReady();
		
		baseClass.stepInfo("verifying the column header in projects");
		prjt.VerifyingColumnValuesInProjects("IN DOMAIN");
		

		loginPage.logout();
	}

	/**
	 * @author Brundha.T,RPMXCON-52807
	 * @Description :Verify that error/validation message should be displayed when
	 *              Role, Project and Security Group is not selected on click of
	 *              'Save'.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-52807", enabled = true, groups = { "regression" })
	public void validatingErrorMsgInBulkAccessControl() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52807");
		baseClass.stepInfo(
				"Verify that error/validation message should be displayed when Role, Project and Security Group is not selected on click of 'Save'.");

		String UserErrorMsg = "Please select at least one user to apply access rights";
		String AccessRightsMsg = "You have not chosen to either enable or disable access rights. Please select at least one of the two options.";
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a da user :" + Input.da1userName);
		baseClass.selectproject(Input.domainName);

		baseClass = new BaseClass(driver);
		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		baseClass.stepInfo("verifying Error Msg for Role");
		user.selectRole(null, null, null, false, null);
		user.saveAndVerifyingErrorMsg("Please select the role to proceed");

		baseClass.stepInfo("verifying Error Msg for Project");
		user.selectRole(Input.ProjectAdministrator, null, null, false, null);
		user.saveAndVerifyingErrorMsg("Please select the project to proceed");

		baseClass.stepInfo("verifying Error Msg for Access Rights");
		user.selectRole(Input.ProjectAdministrator, Input.projectName, null, true, null);
		user.saveAndVerifyingErrorMsg(AccessRightsMsg);

		baseClass.stepInfo("verifying Error Msg for SecurityGroup");
		user.selectRole(Input.ReviewManager, Input.projectName, null, false, null);
		user.saveAndVerifyingErrorMsg("Please select the security group to proceed");

		baseClass.stepInfo("verifying Error Msg for users in PA");
		user.selectRole(Input.ProjectAdministrator, Input.projectName, null, true, "Enable");
		user.saveAndVerifyingErrorMsg(UserErrorMsg);

		baseClass.stepInfo("verifying Error Msg for Access rights in RMU ");
		user.selectRole(Input.ReviewManager, Input.projectName, Input.securityGroup, false, null);
		user.saveAndVerifyingErrorMsg(AccessRightsMsg);

		baseClass.stepInfo("verifying Error Msg for Users in RMU ");
		user.selectRole(Input.ReviewManager, Input.projectName, Input.securityGroup, false, "Enable");
		user.saveAndVerifyingErrorMsg(UserErrorMsg);

		baseClass.passedStep("Verified Error Msg in Bulkaccess control popup Window");
		loginPage.logout();
	}

	/**
     * @author Brundha.T date: 28/10/2022 TestCase Id:RPMXCON-52835 Description
     *         :Verify that error /validation message should be displayed when
     *         domain admin user adds existing domain admin user of currently logged
     *         in users domain
     * @throws Exception
     */
    @Test(description = "RPMXCON-52835", enabled = true, groups = { "regression" })
    public void verifyAddingExistingUserUnderSameProject() throws Exception {

       baseClass.stepInfo("Test case Id: RPMXCON-52835");
        baseClass.stepInfo(
                "Verify that error /validation message should be displayed when domain admin user adds existing domain admin user of currently logged in users domain");
        userManage = new UserManagement(driver);

       loginPage.loginToSightLine(Input.da1userName, Input.da1password);
        baseClass.stepInfo("Logged in as " + Input.da1userName);
        baseClass.selectproject(Input.domainName);
        userManage.navigateToUsersPAge();
        baseClass.stepInfo("Add existing user under to the same Domain and verifying error message");
        userManage.verifyErrorMsgForCreatingExistedUser("QA", "user", Input.DomainAdministrator, Input.da1userName,
                null, Input.projectName);
        baseClass.passedStep("Error message displayed when adding existing user as user:" + Input.da1userName);
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
