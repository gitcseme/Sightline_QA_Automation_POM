package sightline.domainManagement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
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
import pageFactory.BatchPrintPage;
import pageFactory.ClientsPage;
import pageFactory.Dashboard;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Phase2_Regression {
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
	/**
	 * @author Brundha.T Testcase No:RPMXCON-53048
	 * @Description:Verify that when SA creates a new DA, details should be listed
	 *                     in "View Pending activation Users" window.
	 **/
	@Test(description = "RPMXCON-53048", enabled = true, groups = { "regression" })
	public void verifyCreatedDAinActivationUsers() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53048");
		baseClass.stepInfo(
				"Verify that when SA creates a new DA, details should be listed in 'View Pending activation Users' window.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);
		String FirstName = "QA"+Utility.dynamicNameAppender();
		String LastName = "consilio";
		String MailID = "testing"+Utility.dynamicNameAppender() + "@consilio.com";
		UserManagement user = new UserManagement(driver);
		String[] usertoActivate = { FirstName, LastName, MailID };
		user.navigateToUsersPAge();

		baseClass.stepInfo("Creating new Domain Administrator user");
		user.createUser(FirstName, LastName, Input.DomainAdministrator, MailID, Input.domainName, null);

		baseClass.stepInfo("Verify user details in Active users popup");
		user.verifyUserDetailsOnUserNotLoggedInPopup(usertoActivate, MailID);

		// delete the created user
		user.filterTodayCreatedUser();
		user.filterByName(MailID);
		user.RemoveUser();

		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53067
	 * @Description:Verify domain admin can change domains from drop down after
	 *                     clicking on cancel button on assign user pop up
	 **/
	@Test(description = "RPMXCON-53067", enabled = true, groups = { "regression" })
	public void verifyingSwithOfDomain() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53067");
		baseClass.stepInfo(
				"Verify domain admin can change domains from drop down after clicking on cancel button on assign user pop up");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);

		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		user.passingUserName(Input.pa1userName);
		user.applyFilter();
		baseClass.waitTime(2);
		String firstName = user.getTableData("FIRST NAME", 1);
		String lastName = user.getTableData("LAST NAME", 1);
		String userName = firstName + " " + lastName;

		baseClass.stepInfo("Assigning the user and close the popup window");
		baseClass.waitForElement(user.getAssignUserButton());
		user.getAssignUserButton().waitAndClick(2);
		user.goToProjectTabInAssignUser();
		user.selectProjectInAssignUser(Input.projectName);
		user.selectRoleInAssignUser(Input.ProjectAdministrator);
		baseClass.waitTillElemetToBeClickable(user.getCheckingAssignedUserSG(userName));
		driver.scrollingToElementofAPage(user.getCheckingAssignedUserSG(userName));
		user.getCheckingAssignedUserSG(userName).waitAndClick(2);
		user.getLeftArrowForProject().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(user.getUnAssignedDomainUser());
		user.getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(userName);
		user.getDomainUserRightArrow().waitAndClick(10);
		user.getPopUpCloseBtn().waitAndClick(5);

		baseClass.stepInfo("swicthing to another domain apart from current domain");
		baseClass.switchDomain();
		baseClass.waitTime(3);
		String CurrentDomain = baseClass.getProjectNames().getText();
		baseClass.textCompareNotEquals(CurrentDomain, Input.domainName,
				"User should be able change any domain from drop down", "Not switched to another domain");

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53030
	 * @Description:To verify that Domain Admin can view the list of projects in the
	 *                 currently logged in domain.
	 **/
	@Test(description = "RPMXCON-53030", enabled = true, groups = { "regression" })
	public void verifyingProjectDropdownInAssignUsers() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53030");
		baseClass.stepInfo(
				"To verify that Domain Admin can view the list of projects in the currently logged in domain.");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);

		ArrayList<String> Values = new ArrayList<>();
		ArrayList<String> DropDownValues = new ArrayList<>();
		ProjectPage projects = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Navigating to Projects Page");
		projects.navigateToProductionPage();

		baseClass.waitTime(1);
		baseClass.stepInfo("getting the projects in current domain");
		String Count = projects.getLastPageNavigation().getText();
		int TotalCount = Integer.valueOf(Count);
		System.out.println(TotalCount);

		for (int i = 0; i <= TotalCount; i++) {
			baseClass.waitTime(2);
			int size = user.getProjectNameCol().size();
			System.out.println(size);
			for (int j = 1; j <= size; j++) {
				driver.waitForPageToBeReady();
				String PrjtName = user.getProjectNameColValue(j).getText();
				Values.add(PrjtName);
			}
			System.out.println(Values);
			String NextBtn = user.getNextBtn().GetAttribute("Class");
			if (NextBtn.contains("disabled")) {
				break;
			} else {
				driver.waitForPageToBeReady();
				user.getUserListNextButton().waitAndClick(5);
			}
		}
		user.navigateToUsersPAge();
		baseClass.stepInfo("Selecting Assign user and validating the project dropdown");
		baseClass.waitForElement(user.getAssignUserButton());
		user.getAssignUserButton().waitAndClick(5);
		baseClass.waitForElement(user.getProjectTab());
		user.getProjectTab().waitAndClick(5);
		baseClass.waitForElement(user.getAssignUserProjectDrp_Dwn());
		int Size = user.getAssignUserProjectDrp_Dwn().selectFromDropdown().getOptions().size();
		System.out.println(Size);
		for (int i = 2; i <= Size; i++) {
			String DropText = user.getProjectDropdownList(i).GetAttribute("title");
			DropDownValues.add(DropText);
		}
		System.out.println(DropDownValues);
		if (Values.equals(DropDownValues)) {
			baseClass.passedStep("Domain Admin can view the list of projects in the currently logged in domain");
		} else {
			baseClass.failedStep(" Domain Admin can not view the list of projects in the currently logged in domain");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53049
	 * @Description:To verify Role field values when clicking on cancel after
	 *                 changing role from DA to PA/RMU/Reviewer/SA in Edit user pop
	 *                 up
	 **/
	@Test(description = "RPMXCON-53049", enabled = true, groups = { "regression" })
	public void verifyingEditPopupWindowForDA() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53049");
		baseClass.stepInfo(
				"To verify Role field values when clicking on cancel after changing role from DA to PA/RMU/Reviewer/SA in Edit user pop up");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		UserManagement user = new UserManagement(driver);

		String CompareString = "The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?";
		String[] Users = { Input.ProjectAdministrator, Input.ReviewManager, Input.Reviewer, Input.SystemAdministrator };

		baseClass.stepInfo("Applying filter for DA user");
		user.passingUserName(Input.da1userName);
		user.applyFilter();
		for (int i = 0; i < Users.length; i++) {
			baseClass.stepInfo("Editing filtered DA user");
			user.editLoginUser();
			driver.waitForPageToBeReady();
			user.getSelctRole().selectFromDropdown().selectByVisibleText(Users[i]);
			baseClass.stepInfo("Verifying the alert message when Role changed as" + Users[i]);
			baseClass.waitForElement(user.getAlertMsgBox());
			String ActualString = user.getAlertMsgBox().getText();
			baseClass.textCompareEquals(ActualString, CompareString, "Alert Message is displayed as expected",
					"Alert message not displayed");
			baseClass.getNOBtn().waitAndClick(5);

			driver.Navigate().refresh();
			baseClass.waitTime(2);
			baseClass.stepInfo("Validating the role remains the same");
			user.passingUserName(Input.da1userName);
			user.applyFilter();
			String Role = user.getTableData("ROLE", 1);
			System.out.println(Role);
			if (Role.equals(Input.DomainAdministrator)) {
				baseClass.passedStep("The role is not changed and remains DA");
			} else {
				baseClass.failedStep("The role is changed to"+ Users[i]);
			}
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53047
	 * @Description:Verify that when System Admin impersonate to DA and creates a
	 *                     new DA/PAU/RMU/Reviwer, details should be listed in "View
	 *                     Pending activation Users" window.
	 **/
	@Test(description = "RPMXCON-53047", enabled = true, groups = { "regression" })
	public void verifyCreatedUsersinActivationUsersAfterImpersonating() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53047");
		baseClass.stepInfo(
				"Verify that when System Admin impersonate to DA and creates a new DA/PAU/RMU/Reviwer, details should be listed in \"View Pending activation Users\" window.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String FirstName = "QA1" + Utility.dynamicNameAppender();
		String LastName = "Framework" + Utility.dynamicNameAppender();
		String MailID = "testing"+Utility.dynamicNameAppender() + "@consilio.com";

		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Impersonating SA as DA");
		baseClass.impersonateSAtoDA(Input.domainName);

		String[] Users = { Input.DomainAdministrator, Input.ProjectAdministrator, Input.ReviewManager, Input.Reviewer };
		String[] UserInActivePopup = { FirstName, LastName, MailID };

		for (int i = 0; i < Users.length; i++) {
			user.navigateToUsersPAge();

			baseClass.stepInfo("Creating new User for " + Users[i]);
			if (Users[i].equals(Input.DomainAdministrator)) {
				user.openAddNewUserPopUp();
				user.getFirstName().SendKeys(FirstName);
				user.getLastName().SendKeys(LastName);
				driver.waitForPageToBeReady();
				user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
				user.getEmail().SendKeys(MailID);
				user.getSave().waitAndClick(10);
			} else {
				user.createUser(FirstName, LastName, Users[i], MailID, " ", Input.projectName);
			}
			baseClass.waitTime(5);
			baseClass.getCloseSucessmsg();
			baseClass.stepInfo("Verify user details in Active users popup");
			user.verifyUserDetailsOnUserNotLoggedInPopup(UserInActivePopup, MailID);

			// delete the created user
			user.filterTodayCreatedUser();
			user.filterByName(MailID);
			user.RemoveUser();
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53046
	 * @Description:Verify that when Domain Admin creates a new DA/PAU/RMU/Reviwer,
	 *                     details should be listed in "View Pending activation
	 *                     Users" window.
	 **/
	@Test(description = "RPMXCON-53046", enabled = true, groups = { "regression" })
	public void verifyCreatedUsersinActivationUsersFromDA() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53046");
		baseClass.stepInfo(
				"Verify that when Domain Admin creates a new DA/PAU/RMU/Reviwer, details should be listed in \"View Pending activation Users\" window.");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.stepInfo("selecting domain");
		baseClass.selectdomain(Input.domainName);

		String FirstName = "QA1" + Utility.dynamicNameAppender();
		String LastName = "Framework";
		String MailID = "testing"+Utility.dynamicNameAppender() + "@consilio.com";

		UserManagement user = new UserManagement(driver);
		String[] Users = { Input.DomainAdministrator, Input.ProjectAdministrator, Input.ReviewManager, Input.Reviewer };
		String[] UserInActivePopup = { FirstName, LastName, MailID };

		for (int i = 0; i < Users.length; i++) {
			user.navigateToUsersPAge();

			baseClass.stepInfo("Creating new User for " + Users[i]);
			if (Users[i].equals(Input.DomainAdministrator)) {
				user.openAddNewUserPopUp();
				user.getFirstName().SendKeys(FirstName);
				user.getLastName().SendKeys(LastName);
				driver.waitForPageToBeReady();
				user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
				user.getEmail().SendKeys(MailID);
				user.getSave().waitAndClick(10);
			} else {
				user.createUser(FirstName, LastName, Users[i], MailID, " ", Input.projectName);
			}
			baseClass.waitTime(5);
			baseClass.stepInfo("Verify user details in Active users popup");
			user.verifyUserDetailsOnUserNotLoggedInPopup(UserInActivePopup, MailID);

			// delete the created user
			user.filterTodayCreatedUser();
			user.filterByName(MailID);
			user.RemoveUser();
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53043
	 * @Description:To verify that error message should not be retained if System
	 *                 Admin navigates from tab to tab
	 **/
	@Test(description = "RPMXCON-53043", enabled = true, groups = { "regression" })
	public void verifyErrorMsgAbsenceInTabNavigation() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53043");
		baseClass.stepInfo(
				"To verify that error message should not be retained if System Admin navigates from tab to tab");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Selecting Domain and click on Arrow");
		baseClass.waitForElement(user.getAssignUserButton());
		user.getAssignUserButton().waitAndClick(2);
		user.getDomaintab().waitAndClick(10);
		baseClass.waitForElement(user.getSelectDomainname());
		user.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		user.getrightBtndomainuser().waitAndClick(10);

		baseClass.stepInfo("verifying error message in domain tab");
		baseClass.elementDisplayCheck(user.getErrorMsgInDomainTab());

		baseClass.stepInfo("switching to Project tab and verifying invisiblity of error message");
		user.goToProjectTabInAssignUser();
		if (!user.getErrorMsgInDomainTab().isDisplayed()) {
			baseClass.passedStep("Error msg is not displayed when switching from domain to Project tab");
		} else {
			baseClass.failedStep("Error msg is displayed");
		}

		baseClass.stepInfo("Selecting Project and click on Arrow");
		driver.waitForPageToBeReady();
		user.selectProjectInAssignUser(Input.projectName);
		user.selectRoleInAssignUser(Input.ProjectAdministrator);
		user.getDomainUserRightArrow().waitAndClick(10);

		baseClass.stepInfo("verifying error message in Project tab");
		baseClass.elementDisplayCheck(user.getErrorMsgInProjectTab());
		user.getDomaintab().waitAndClick(10);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("switching to Domain tab and verifying invisiblity of error message");
		if (!user.getErrorMsgInProjectTab().isDisplayed()) {
			baseClass.passedStep("Error msg is not displayed when switching from project to domain");
		} else {
			baseClass.failedStep("Error msg is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53042
	 * @Description:To verify that if user clicks on Cancel popup should be closed
	 *                 and user should not be Assigned/Unassigned for Selected
	 *                 Domain
	 **/
	@Test(description = "RPMXCON-53042", enabled = true, groups = { "regression" })
	public void verifyingUserNotUnAssignedFromDomain() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53042");
		baseClass.stepInfo(
				"To verify that if user clicks on Cancel popup should be closed and user should not be Assigned/Unassigned for Selected Domain");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		user.passingUserName(Input.da1userName);
		user.applyFilter();
		baseClass.waitTime(2);
		String firstName = user.getTableData("FIRST NAME", 1);
		String lastName = user.getTableData("LAST NAME", 1);
		String userName = firstName + " " + lastName;

		baseClass.stepInfo("Select Domain and Users Click on Left Arrow");
		user.openAssignUser();
		user.getDomaintab().waitAndClick(10);
		baseClass.waitForElement(user.getSelectDomainname());
		user.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		driver.scrollingToElementofAPage(user.getAssignedDomain(userName));
		user.getAssignedDomain(userName).waitAndClick(10);
		user.getLeftArrow().waitAndClick(10);
		
		baseClass.stepInfo("Selecting cancel buttton");
		user.getDomainUserCancelButton().waitAndClick(10);
		
		baseClass.stepInfo("verifying the user  assigned for Selected Domain");
		user.openAssignUser();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(user.getSelectDomainname());
		user.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		if (user.getAssignedDomain(userName).isElementAvailable(3)) {
			baseClass.passedStep("user is not unassigned and remains the same");
		} else {
			baseClass.failedStep("User is unassigned to the selected domain");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53050
	 * @Description:To verify Role field values when clicking on cancel after
	 *                 changing role from PA to DA/RMU/Reviewer/SA in Edit user pop
	 *                 up
	 **/
	@Test(description = "RPMXCON-53050", enabled = true, groups = { "regression" })
	public void verifyingEditPopupWindowForPA() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53050");
		baseClass.stepInfo(
				"To verify Role field values when clicking on cancel after changing role from PA to DA/RMU/Reviewer/SA in Edit user pop up");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		UserManagement user = new UserManagement(driver);

		String CompareString = "The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?";
		String[] Users = { Input.DomainAdministrator, Input.ReviewManager, Input.Reviewer, Input.SystemAdministrator };

		baseClass.stepInfo("Applying filter for PA user");
		user.passingUserName(Input.pa1userName);
		user.applyFilter();

		for (int i = 0; i < Users.length; i++) {
			baseClass.stepInfo("Editing filtered PA user");
			user.editLoginUser();
			driver.waitForPageToBeReady();
			user.getSelctRole().selectFromDropdown().selectByVisibleText(Users[i]);

			baseClass.stepInfo("Verifying the alert message when Role changed as" + Users[i]);
			baseClass.waitForElement(user.getAlertMsgBox());
			String ActualString = user.getAlertMsgBox().getText();
			baseClass.textCompareEquals(ActualString, CompareString, "Alert Message is displayed as expected",
					"Alert message not displayed");
			baseClass.getNOBtn().waitAndClick(5);
			driver.Navigate().refresh();
			baseClass.waitTime(2);
			baseClass.stepInfo("Validating the role remains the same");
			user.passingUserName(Input.pa1userName);
			user.applyFilter();
			String Role = user.getTableData("ROLE", 1);
			System.out.println(Role);
			if (Role.equals(Input.ProjectAdministrator)) {
				baseClass.passedStep("The role is not changed and remains PA");
			} else {
				baseClass.failedStep("The role is changed");
			}
		}
		loginPage.logout();
	}
	/**
	 * @author Brundha.T Testcase No:RPMXCON-53066
	 * @Description:To verify user rights from Edit User > Functionality tab when
	 *                 Sys Admin changes role of Reviewer[assigned to single
	 *                 domain/non-domain project] user to Domain Admin
	 **/
	@Test(description = "RPMXCON-53066", enabled = true, groups = { "regression" })
	public void verifyingFunctionalityTabFromRevToDA() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53066");
		baseClass.stepInfo(
				"To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Reviewer[assigned to single domain/non-domain project] user to Domain Admin");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		UserManagement user = new UserManagement(driver);
		String CompareString = "The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?";

		baseClass.stepInfo("Applying filter for reviewer user");
		user.passingUserName(Input.rev1userName);
		user.applyFilter();

		baseClass.stepInfo("Edit the applied filter");
		user.editLoginUser();
		driver.waitForPageToBeReady();
		user.getSelctRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);

		baseClass.stepInfo("Verifying the Alert message");
		baseClass.waitForElement(user.getAlertMsgBox());
		String ActualString = user.getAlertMsgBox().getText();
		System.out.println("The actual string is" + ActualString);
		baseClass.textCompareEquals(ActualString, CompareString, "Alert Message is displayed as expected",
				"Alert message not displayed");
		baseClass.getNOBtn().waitAndClick(5);

		driver.Navigate().refresh();
		baseClass.waitTime(2);
		user.passingUserName(Input.rev1userName);
		user.applyFilter();
		String Role = user.getTableData("ROLE", 1);
		System.out.println(Role);
		baseClass.textCompareEquals(Role, Input.Reviewer, "User role remains same when selecting Cancel button",
				"user role not remains same");

		baseClass.stepInfo("selecting OK button and verifying the functionality tab changed as per the role");
		user.editLoginUser();
		driver.waitForPageToBeReady();
		user.getSelctRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(user.getFunctionalityButton());
		user.getFunctionalityButton().waitAndClick(5);

		if (user.getEditUserProduction().Enabled() && user.getEditUserIngestion().Enabled()) {
			baseClass.passedStep("User role is changed into Selected role in functionality tab");
		} else {
			baseClass.failedStep("User role is not changed");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53065
	 * @Description:To verify user rights from Edit User > Functionality tab when
	 *                 Sys Admin changes role of RMU[assigned to single
	 *                 domain/non-domain project] user to Domain Admin
	 **/
	@Test(description = "RPMXCON-53065", enabled = true, groups = { "regression" })
	public void verifyingFunctionalityTabFromRMUToDA() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53065");
		baseClass.stepInfo(
				"To verify user rights from Edit User > Functionality tab when Sys Admin changes role of RMU[assigned to single domain/non-domain project] user to Domain Admin");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		UserManagement user = new UserManagement(driver);

		String CompareString = "The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?";

		baseClass.stepInfo("Applying filter for RMU user");
		user.passingUserName(Input.rmu1userName);
		user.applyFilter();

		baseClass.stepInfo("Edit the applied filter");
		user.editLoginUser();
		driver.waitForPageToBeReady();
		user.getSelctRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);

		baseClass.stepInfo("Verifying the Alert message");
		baseClass.waitForElement(user.getAlertMsgBox());
		String ActualString = user.getAlertMsgBox().getText();
		System.out.println("The actual string is" + ActualString);
		baseClass.textCompareEquals(ActualString, CompareString, "Alert Message is displayed as expected",
				"Alert message not displayed");
		baseClass.getNOBtn().waitAndClick(5);
		driver.Navigate().refresh();
		baseClass.waitTime(2);
		user.passingUserName(Input.rmu1userName);
		user.applyFilter();
		String Role = user.getTableData("ROLE", 1);
		System.out.println(Role);
		baseClass.textCompareEquals(Role, Input.ReviewManager, "User role remains same when selecting Cancel button",
				"user role not remains same");

		baseClass.stepInfo("selecting Ok button and verifying the functionality tab changed as per the role");
		user.editLoginUser();
		driver.waitForPageToBeReady();
		user.getSelctRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(user.getFunctionalityButton());
		user.getFunctionalityButton().waitAndClick(5);

		if (user.getEditUserIngestion().Enabled()) {
			baseClass.passedStep("User role is changed into Selected role in functionality tab");
		} else {
			baseClass.failedStep("User role is not changed");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53068
	 * @Description:Verify domain admin can change domains from drop down after
	 *                     saving data on assign user pop up
	 **/
	@Test(description = "RPMXCON-53068", enabled = true, groups = { "regression" })
	public void verifyAssigningUserAndSwitchDomain() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53068");
		baseClass.stepInfo(
				"Verify domain admin can change domains from drop down after saving data on assign user pop up");
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.stepInfo("selecting domain");
		baseClass.selectdomain(Input.domainName);

		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		user.passingUserName(Input.pa1userName);
		user.applyFilter();
		baseClass.waitTime(2);
		String firstName = user.getTableData("FIRST NAME", 1);
		String lastName = user.getTableData("LAST NAME", 1);
		String userName = firstName + " " + lastName;

		baseClass.stepInfo("Assigning the user and save");
		baseClass.waitForElement(user.getAssignUserButton());
		user.getAssignUserButton().waitAndClick(2);
		user.goToProjectTabInAssignUser();
		user.selectProjectInAssignUser(Input.projectName);
		user.selectRoleInAssignUser(Input.ProjectAdministrator);
		baseClass.waitTillElemetToBeClickable(user.getCheckingAssignedUserSG(userName));
		driver.scrollingToElementofAPage(user.getCheckingAssignedUserSG(userName));
		user.getCheckingAssignedUserSG(userName).waitAndClick(2);
		user.getLeftArrowForProject().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(user.getUnAssignedDomainUser());
		user.getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(userName);
		user.getDomainUserRightArrow().waitAndClick(10);
		baseClass.waitForElement(user.getsavedomainuser());
		user.getsavedomainuser().waitAndClick(5);
		baseClass.VerifySuccessMessage("User Mapping Successful");

		baseClass.stepInfo("Switching to another domain apart from current domain");
		baseClass.switchDomain();
		baseClass.waitTime(1);
		
		baseClass.stepInfo("verifying whether the domain is switched to another domain");
		String CurrentDomain = baseClass.getProjectNames().getText();
		baseClass.textCompareNotEquals(CurrentDomain, Input.domainName,
				"User can able to change any domain from drop down", "Not switched to another domain");

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53031
	 * @Description:To verify that the Domain Admin should be able to unassign users
	 *                 from projects in the domain
	 **/
	@Test(description = "RPMXCON-53031", enabled = true, groups = { "regression" })
	public void verifyDAUserCanUnassignedUsers() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53031");
		baseClass.stepInfo(
				"To verify that the Domain Admin should be able to unassign users from projects in the domain");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);
		
		UserManagement user = new UserManagement(driver);
		String FirstName = "QA"+Utility.dynamicNameAppender();
		String LastName = "automation";
		String MailID = "test" +Utility.dynamicNameAppender()+ "@consilio.com";
		String UserName=FirstName+" "+LastName;
		
		user.navigateToUsersPAge();
		baseClass.stepInfo("Creating new project Administrator user");
		user.createUser(FirstName, LastName, Input.ProjectAdministrator, MailID, null,Input.projectName);
		
		baseClass.stepInfo("Assigning project to user");
		user.openAssignUser();
		user.goToProjectTabInAssignUser();
		String Project=user.getProjectDropdownList(2).getText();
		System.out.println(Project);
		if(!Project.equals(Input.projectName)) {
		baseClass.waitForElement(user.getAssignUserProjectDrp_Dwn());
		user.getAssignUserProjectDrp_Dwn().selectFromDropdown().selectByIndex(2);
		}else {
			baseClass.waitForElement(user.getAssignUserProjectDrp_Dwn());
			user.getAssignUserProjectDrp_Dwn().selectFromDropdown().selectByIndex(3);
		}
		driver.waitForPageToBeReady();
		user.selectRoleInAssignUser(Input.ProjectAdministrator);
		driver.scrollingToElementofAPage(user.getUnAssignedDomainUser());
		user.getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(UserName);
		user.getDomainUserRightArrow().waitAndClick(10);
		baseClass.waitForElement(user.getsavedomainuser());
		user.getsavedomainuser().waitAndClick(5);
		baseClass.VerifySuccessMessage("User Mapping Successful");
		
		
		baseClass.stepInfo("verifying Domain Admin able to unassign users from projects in the domain");
		baseClass.waitForElement(user.getAssignUserButton());
		user.getAssignUserButton().waitAndClick(2);
		baseClass.waitForElement(user.getAssignUserProjectDrp_Dwn());
		user.getAssignUserProjectDrp_Dwn().selectFromDropdown().selectByIndex(2);
		if(user.getCheckingAssignedUserSG(UserName).isElementAvailable(3)) {
			baseClass.passedStep("User is unassigned from the selected project");
		}else {
			baseClass.failedStep("user is not unassigned from the selected project");
		}
		user.getPopUpCloseBtn().waitAndClick(10);
		
		
		// delete the created user
		user.filterTodayCreatedUser();
		user.filterByName(MailID);
		user.RemoveUser();

		loginPage.logout();

	}
	/**
	 * @author Brundha.T Testcase No:RPMXCON-53041
	 * @Description:Verify that logged in user with Project Admin role should be
	 *                     displayed on user list page under non domain project
	 **/
	@Test(description = "RPMXCON-53041", enabled = true, groups = { "regression" })
	public void verifyPARoleUnderNonDomanPrjt() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53041");
		baseClass.stepInfo(
				"Verify that logged in user with Project Admin role should be displayed on user list page under non domain project");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As " + Input.pa1userName);
		
		baseClass.stepInfo("Selecting non domain project");
		baseClass.selectproject(Input.NonDomainProject);
		
		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		
		baseClass.stepInfo("Applying filter for logged in PA user");
		user.passingUserName(Input.pa1userName);
		user.applyFilter();
		driver.waitForPageToBeReady();
		String getRole = user.getTableData("ROLE", 1);
		
		if(getRole.equals(Input.ProjectAdministrator)) {
			baseClass.passedStep(" Project Admin role is displayed on user list page under non domain project");
		}else {
			baseClass.failedStep(" Project Admin role is not displayed on user list page under non domain project");
			
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53040
	 * @Description:Verify that logged in user with Project Admin role should be
	 *                     displayed on user list page under domain project
	 **/
	@Test(description = "RPMXCON-53040", enabled = true, groups = { "regression" })
	public void verifyPARoleUnderDomanPrjt() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53040");
		baseClass.stepInfo(
				"Verify that logged in user with Project Admin role should be displayed on user list page under domain project");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As " + Input.pa1userName);
		
		UserManagement user = new UserManagement(driver);
		user.navigateToUsersPAge();
		baseClass.stepInfo("Applying filter for logged in PA user");
		user.passingUserName(Input.pa1userName);
		user.applyFilter();
		
		driver.waitForPageToBeReady();
		String getRole = user.getTableData("ROLE", 1);
		if(getRole.equals(Input.ProjectAdministrator)) {
			baseClass.passedStep(" Project Admin role is displayed on user list page under domain project");
		}else {
			baseClass.failedStep(" Project Admin role is not displayed on user list page under domain project");
			
		}
		
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.rani date: 24/11/2022 TestCase Id:RPMXCON-53051 Description
	 * :Verify that as a SA one should be able to assign DA of one domain to other domains as well.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53051", enabled = true, groups = { "regression" })
	public void verifySAAbleToAssignDADomains() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53051");
		baseClass.stepInfo(
				"Verify that as a SA one should be able to assign DA of one domain to other domains as well.");

		UserManagement user = new UserManagement(driver);
		String FirstName = "DAuser" + Utility.dynamicNameAppender();
		String LastName = "automation";
		String MailID = "test" + Utility.dynamicNameAppender() + "@consilio.com";
		String UserName = FirstName + " " + LastName;

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");

		user.navigateToUsersPAge();
		baseClass.stepInfo("Creating new project Administrator user");
		user.createUser(FirstName, LastName, Input.DomainAdministrator, MailID, null, null);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Assigning project to user");
		user.openAssignUser();
		driver.waitForPageToBeReady();
		user.getDomaintab().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(user.getSelectDomainname());
		driver.waitForPageToBeReady();
		user.getSelectDomainname().selectFromDropdown().selectByIndex(3);
     	driver.waitForPageToBeReady();
		user.getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(UserName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(user.getrightBtndomainuser());
		driver.waitForPageToBeReady();
		user.getrightBtndomainuser().waitAndClick(5);
		driver.waitForPageToBeReady();
		user.getsavedomainuser().waitAndClick(5);
		baseClass.VerifySuccessMessage("User Mapping Successful");
		baseClass.stepInfo("Domain user Assiged succesfully");
		baseClass.passedStep("Success message is displayed.");

		// delete the created user
		driver.waitForPageToBeReady();
		user.filterTodayCreatedUser();
		driver.waitForPageToBeReady();
		user.filterByName(MailID);
		user.RemoveUser();

		loginPage.logout();
	}
	/**
     * Author :NA TestCase Id:RPMXCON-52954
     * Description: Validate modifying all editable field values and save changes for a domain project by System Admin
     */
    @Test(description = "RPMXCON-52954", enabled = true, groups = { "regression" })
    public void validateAllModifiedValue() throws Exception {
        ProjectPage project = new ProjectPage(driver);
        
        String projectName = "Project" + Utility.dynamicNameAppender();
        String firm = "F" + Utility.dynamicNameAppender();
        String corpClient = "CrpClient" + Utility.dynamicNameAppender();
        String noOfDocs = "20000";
        
        String newProjectName = "NewProject" + Utility.dynamicNameAppender();
        String newFirm = "New" + Utility.dynamicNameAppender();
        String newCorpClient = "NewCrp" + Utility.dynamicNameAppender();
        String projFolder = "NewAutomation";
        String ingFolder = "NewAutomation";
        String productionFolder = "NewAutomation";
        String uptdNoOfDocs = "10000";



       baseClass.stepInfo("RPMXCON-52954");
        baseClass.stepInfo("Validate modifying all editable field values and save changes for a domain project by System Admin");
        loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
        baseClass.stepInfo("Logged in As : " + Input.sa1userName);
        
        project.navigateToProductionPage();
        project.AddDomainProjectDetailsWithoutSave(projectName, "Indium");
        driver.scrollPageToTop();
        baseClass.waitForElement(project.getFirmTextBox());
        project.getFirmTextBox().SendKeys(firm);
        baseClass.waitForElement(project.getCorpClientTextBox());
        project.getCorpClientTextBox().SendKeys(corpClient);
        driver.scrollPageToTop();    
        baseClass.waitForElement(project.getAddProject_SettingsTab());
        project.getAddProject_SettingsTab().waitAndClick(5);
        baseClass.waitForElement(project.getNoOfDocuments());
        project.getNoOfDocuments().SendKeys(noOfDocs);
        project.saveProjectAndVerify();
        
        project.navigateToProductionPage();
        driver.waitForPageToBeReady();
        project.editProject(projectName);
        driver.waitForPageToBeReady();
        
        baseClass.waitForElement(project.getProjectName());
        project.getProjectName().SendKeys(newProjectName);
        baseClass.waitForElement(project.getFirmTextBox());
        project.getFirmTextBox().SendKeys(newFirm);
        baseClass.waitForElement(project.getCorpClientTextBox());
        project.getCorpClientTextBox().SendKeys(newCorpClient);    
        baseClass.waitForElement(project.getProjectFolder());
        project.getProjectFolder().Clear();
        project.getProjectFolder().SendKeys(projFolder);
        baseClass.waitForElement(project.getIngestionFolder());
        project.getIngestionFolder().Clear();
        project.getIngestionFolder().SendKeys(ingFolder);
        baseClass.waitForElement(project.getProductionFolder());
        project.getProductionFolder().Clear();
        project.getProductionFolder().SendKeys(productionFolder);
        driver.scrollPageToTop();    
        baseClass.waitForElement(project.getAddProject_SettingsTab());
        project.getAddProject_SettingsTab().waitAndClick(5);
        baseClass.waitForElement(project.getNoOfDocuments());
        project.getNoOfDocuments().SendKeys(uptdNoOfDocs);             
        baseClass.waitForElement(project.getButtonSaveProject());
        project.getButtonSaveProject().waitAndClick(10);
        baseClass.VerifySuccessMessage("Project updated successfully");
       
        project.navigateToProductionPage();
        driver.waitForPageToBeReady();
        project.editProject(newProjectName);
        driver.waitForPageToBeReady();
        
        baseClass.waitForElement(project.getProjectName());
        String act1 = project.getProjectName().Value();
        baseClass.waitForElement(project.getFirmTextBox());
        String act2 = project.getFirmTextBox().Value();
        baseClass.waitForElement(project.getCorpClientTextBox());
        String act3 = project.getCorpClientTextBox().Value();
        baseClass.waitForElement(project.getProjectFolder());
        String act4 = project.getProjectFolder().Value();
        baseClass.waitForElement(project.getIngestionFolder());
        String act5 = project.getIngestionFolder().Value();
        baseClass.waitForElement(project.getProductionFolder());
        String act6 = project.getProductionFolder().Value();
        driver.scrollPageToTop();    
        baseClass.waitForElement(project.getAddProject_SettingsTab());
        project.getAddProject_SettingsTab().waitAndClick(5);
        baseClass.waitForElement(project.getNoOfDocuments());
        String act7 = project.getNoOfDocuments().Value();        
        
        SoftAssert asserts = new SoftAssert();    
        asserts.assertEquals(act1, newProjectName);
        asserts.assertEquals(act2, newFirm);
        asserts.assertEquals(act3, newCorpClient);
        asserts.assertEquals(act4, projFolder);
        asserts.assertEquals(act5, ingFolder);
        asserts.assertEquals(act6, productionFolder);
        asserts.assertEquals(act7, uptdNoOfDocs);
        asserts.assertAll();
        baseClass.passedStep("Validated - modifying all editable field values and save changes for a domain project by System Admin");
        loginPage.logout();
    }

    /**
     * @author Brundha.T Testcase No:RPMXCON-53039
     * @Description:Verify that users with RMU/Reviewer role should be displayed on
     *                     user list page for non-domain project
     **/
   @Test(description = "RPMXCON-53039", enabled = true, groups = { "regression" })
    public void verifyRMUAndREVRoleUnderNonDomanPrjt() throws Exception {

       baseClass.stepInfo("TestCase id : RPMXCON-53039");
        baseClass.stepInfo(
                "Verify that users with RMU/Reviewer role should be displayed on user list page for non-domain project");

       loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        baseClass.stepInfo("Logged in As " + Input.pa1userName);

       baseClass.stepInfo("Selecting non domain project");
        baseClass.selectproject(Input.NonDomainProject);
        
        UserManagement user = new UserManagement(driver);
        user.navigateToUsersPAge();
        
        baseClass.stepInfo("getting the users in the non-domain project");
        ArrayList<String> Values = new ArrayList<>();
        
        for (int i = 0; i <50; i++) {
            baseClass.waitTime(2);
            List<String>Role=user.getTableCoumnValue("ROLE");
            Values.addAll(Role);
            System.out.println("the values inside user list"+Values);
            
            String NextBtn = user.getNextBtn().GetAttribute("Class");
            if (NextBtn.contains("disabled")) {
                break;
            } else {
                driver.waitForPageToBeReady();
                user.getUserListNextButton().waitAndClick(5);
            }
        }
        baseClass.stepInfo("verifying RMU/Reviewer assigned to the project is listed on manage users page");
        
            int ReviewManager=Collections.frequency(Values,Input.ReviewManager);
            System.out.println(" ReviewManager count"+ReviewManager);
            if(ReviewManager>1) {
                baseClass.passedStep("Review manager assigned to the project is listed on manage users page");
            }else {
                baseClass.failedStep("Review manager users are not displayed in list");
            }
            int Reviewer=Collections.frequency(Values,Input.Reviewer);
            System.out.println("Reviewer count"+Reviewer);
        
            if(Reviewer>1) {
                baseClass.passedStep("Reviewer assigned to the project is listed on manage users page");
            }else {
                baseClass.failedStep("Reviewer users are not displayed in list");
            }
        loginPage.logout();
    }
    
    /**
	 * @author Brundha.T TestCase Id:RPMXCON-53052 Description :Verify Domain
	 *         Admin's functionalites in newly assigned domain
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53052", enabled = true, groups = { "regression" })
	public void verifyingFunctionalityInNewDomain() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53052");
		baseClass.stepInfo("Verify Domain Admin's functionalites in newly assigned domain");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As" + Input.sa1userName + "");

		String FirstName = "QA" + Utility.dynamicNameAppender();
		String LastName = "automation";
		String Email = "testing" + Utility.dynamicNameAppender() + "@consilio.com";

		UserManagement user = new UserManagement(driver);
		BaseClass base = new BaseClass(driver);
		ProjectPage project = new ProjectPage(driver);

		String[] usertoActivate = { FirstName, LastName, Email };
		String clientName = "C" + Utility.dynamicNameAppender();
		String ProjectName = "P" + Utility.dynamicNameAppender();
		String shortName = "D" + Utility.dynamicNameAppender();

		base.stepInfo("Navigated to client/Domain Page");
		project.navigateToClientFromHomePage();

		base.stepInfo("Creating new domain");
		project.addNewClient(clientName, shortName, "Domain");
		base.waitTime(2);

		user.navigateToUsersPAge();
		user.passingUserName(Input.da1userName);
		user.applyFilter();

		String firstName = user.getTableData("FIRST NAME", 1);
		String lastName = user.getTableData("LAST NAME", 1);
		String userName = firstName + " " + lastName;

		baseClass.stepInfo("Assigning newly created  Domain to  DA user");
		user.openAssignUser();
		user.getDomaintab().waitAndClick(5);
		baseClass.waitForElement(user.getSelectDomainname());
		user.getSelectDomainname().selectFromDropdown().selectByVisibleText(clientName);
		driver.waitForPageToBeReady();
		user.getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(userName);
		baseClass.waitForElement(user.getrightBtndomainuser());
		user.getrightBtndomainuser().waitAndClick(5);
		user.getsavedomainuser().waitAndClick(5);
		baseClass.VerifySuccessMessage("User Mapping Successful");

		loginPage.logout();

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName + "");

		baseClass.stepInfo("Creating new project");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.CreatProjectInDA(ProjectName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.filterTheProject(ProjectName);
		project.editProject(ProjectName);
		base.stepInfo("created Domain project is filtered and opened");

		project.getCorpClientTextBox().SendKeys(Input.searchString1);
		driver.scrollingToBottomofAPage();
		project.getButtonSaveProject().waitAndClick(10);

		base.waitTime(2);
		base.stepInfo("Edit the created Project");
		project.editProject(ProjectName);
		String EditedText = project.getCorpClientTextBox().GetAttribute("value");
		base.textCompareEquals(EditedText, Input.searchString1, "project value is edited successfully",
				"Project value is not edited");

		user.navigateToUsersPAge();
		baseClass.stepInfo("Creating new user");
		user.createUser(FirstName, LastName, Input.ReviewManager, Email, " ", ProjectName);

		baseClass.stepInfo("Verifying new user created");
		user.verifyUserDetailsOnUserNotLoggedInPopup(usertoActivate, Email);

		baseClass.stepInfo("Editing new user created");
		user.passingUserName(Email);
		user.applyFilter();
		user.editLoginUser();
		driver.waitForPageToBeReady();
		user.getSelctRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(user.getFunctionalityButton());
		user.getFunctionalityButton().waitAndClick(5);

		if (user.getEditUserIngestion().Enabled()) {
			baseClass.passedStep("User role is changed into Selected role in functionality tab");
		} else {
			baseClass.failedStep("User role is not changed");
		}

		base.waitForElement(user.EditUserClosePopupBtn());
		user.EditUserClosePopupBtn().waitAndClick(10);

		// delete the created user
		user.filterTodayCreatedUser();
		user.filterByName(Email);
		user.RemoveUser();
		loginPage.logout();
	}
	/**
	 * @author Brundha.T Testcase No:RPMXCON-52956
	 * @Description:Validate modifying HCode for a non-domain project by System
	 *                       Admin
	 **/
	@Test(description = "RPMXCON-52956", enabled = true, groups = { "regression" })
	public void verifyingHCodeInProject() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52956");
		baseClass.stepInfo("Validate modifying HCode for a non-domain project by System Admin");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		ProjectPage project = new ProjectPage(driver);
		ProductionPage page = new ProductionPage(driver);

		String ProjectName = "Proj" + Utility.dynamicNameAppender();
		String HCode = "H" + Utility.dynamicNameAppender();
		String ProjectName1 = "Proj" + Utility.dynamicNameAppender();
		String HCode1 = "H" + Utility.dynamicNameAppender();
		String HCode20 = page.getRandomNumber(20);
		String HCodeWithSpecialChar = "*&^" + page.getRandomNumber(6);
		String HCodeWithAlphaNumeric = "A_" + page.getRandomNumber(6);
		String Errormsg = "You cannot specify any special characters in the field value";

		baseClass.stepInfo("Creating new non-domain project");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.CreateNewNonDomainProject(ProjectName, HCode);
		project.CreateNewNonDomainProject(ProjectName1, HCode1);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("verifying Duplication Error Msg in HCode");
		project.editProject(ProjectName);
		baseClass.waitTime(2);
		project.getHCode().SendKeys(HCode1);
		driver.scrollingToBottomofAPage();
		project.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifyErrorMessage("The Hcode specified is already used for another project");

		baseClass.stepInfo("verifying HCode Allows only 16 Characters");
		driver.scrollPageToTop();
		project.getHCode().SendKeys(HCode20);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);

		project.editProject(ProjectName);
		String HCodeIn = project.getHcodeValue().GetAttribute("value");
		System.out.println(HCodeIn);
		baseClass.textCompareNotEquals(HCodeIn, HCode20, "Hcode Allowing only 16 character",
				"Hcode Allowing more than 16 character");

		baseClass.stepInfo("verifying HCode will not allow special character");
		project.getHCode().SendKeys(HCodeWithSpecialChar);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);

		String HCodeSpcialCharMsg = project.getHCodeError().getText();
		System.out.println(HCodeSpcialCharMsg);
		baseClass.textCompareEquals(Errormsg, HCodeSpcialCharMsg, "Special Characters are not allowed in HCode ",
				"Special Characters are  allowed ");

		baseClass.stepInfo("verifying modified hCode value with AlphaNumeric characters are Edited");
		project.getHCode().SendKeys(HCodeWithAlphaNumeric);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);

		project.editProject(ProjectName);

		String AlphaNumericHcode = project.getHcodeValue().GetAttribute("value");
		System.out.println(AlphaNumericHcode);
		baseClass.textCompareEquals(AlphaNumericHcode, HCodeWithAlphaNumeric, "Modified Hcode Value is displayed",
				"Modified Hcode Value is not displayed");

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-53045
	 * @Description:To verify system admin impersonate as Domain Admin and assign
	 *                 users <TBD>
	 **/
	@Test(description = "RPMXCON-53045", enabled = true, groups = { "regression" })
	public void verifyingSAAsDACanAssignUser() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-53045");
		baseClass.stepInfo("To verify system admin impersonate as Domain Admin and assign users <TBD>");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String FirstName = "QA1" + Utility.dynamicNameAppender();
		String LastName = "Automation";
		String MailID = "testing" + Utility.dynamicNameAppender() + "@consilio.com";
		String UserName = FirstName + " " + LastName;

		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Impersonating SA as DA");
		baseClass.impersonateSAtoDA(Input.domainName);

		String[] Users = { Input.ReviewManager, Input.ProjectAdministrator };

		baseClass.stepInfo("Navigating to Users Page");
		user.navigateToUsersPAge();
		for (int i = 0; i < Users.length; i++) {

			user.createUser(FirstName, LastName, Users[i], MailID, " ", Input.projectName);

			baseClass.stepInfo("Assigning project to"+Users[i]);
			user.openAssignUser();
			user.goToProjectTabInAssignUser();

			baseClass.waitForElement(user.getAssignUserProjectDrp_Dwn());
			user.getAssignUserProjectDrp_Dwn().selectFromDropdown().selectByIndex(2);

			driver.waitForPageToBeReady();
			user.selectRoleInAssignUser(Users[i]);
			if (Users[i].equals(Input.ReviewManager)) {
				user.getDomainSG().selectFromDropdown().selectByVisibleText(Input.securityGroup);
			}
			driver.scrollingToElementofAPage(user.getUnAssignedDomainUser());
			user.getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(UserName);

			user.getDomainUserRightArrow().waitAndClick(10);
			baseClass.waitForElement(user.getsavedomainuser());
			user.getsavedomainuser().waitAndClick(5);
			baseClass.VerifySuccessMessage("User Mapping Successful");

			baseClass.stepInfo("verifying Selected user is displayed in Assigned users list");
			baseClass.waitForElement(user.getAssignUserButton());
			user.getAssignUserButton().waitAndClick(2);
			baseClass.waitForElement(user.getAssignUserProjectDrp_Dwn());
			user.getAssignUserProjectDrp_Dwn().selectFromDropdown().selectByIndex(2);
			baseClass.waitTime(2);
			if (user.getCheckingAssignedUserSG(UserName).isElementAvailable(3)) {
				baseClass.passedStep("User is displayed in Assigned users list");
			} else {
				baseClass.failedStep("user is not displayed in Assigned users list");
			}
			user.getPopUpCloseBtn().waitAndClick(10);
            baseClass.waitTime(2);
			// delete the created user
			user.filterTodayCreatedUser();
			user.filterByName(MailID);
			user.RemoveUser();
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52886
	 * @Description:Validate new project creation of Domain client type by Domain
	 *                       Admin
	 **/
	@Test(description = "RPMXCON-52886", enabled = true, groups = { "regression" })
	public void verifyingDomainClient() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52886");
		baseClass.stepInfo("Validate new project creation of Domain client type by Domain Admin");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);
		ProjectPage project = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);

		String ProjectName = "P" + Utility.dynamicNameAppender();
		String CorporateClient = "P" + Utility.dynamicNameAppender();
		String Firm = "P" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Creating new domain project");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.getAddProjectBtn().waitAndClick(10);

		baseClass.stepInfo("verifying HCode and Settings tab is not displayed");
		baseClass.elementNotdisplayed(project.getHCode(), "HCode TextBox");
		baseClass.elementNotdisplayed(project.getAddProject_SettingsTab(), "Settings Tab");

		baseClass.stepInfo("verifying Clientname and clienttype is auto populated and disabled");
		baseClass.elementDisplayCheck(project.getClientTypeDisableCheck());
		String option = baseClass.getCurrentDropdownValue(project.getSelectEntityType());
		baseClass.textCompareEquals(option, "Domain", "Client type is auto populated","Client type is not auto populated");

		baseClass.elementDisplayCheck(project.getClientNameDisable());
		String ClientName = baseClass.getCurrentDropdownValue(project.getSelectEntity());
		baseClass.textCompareNotEquals(ClientName, "--Select--", "Client name is auto populated","Clientname is not auto populated");
				

		baseClass.stepInfo("verifying Database and workspace is autopopulated for domain");
		baseClass.elementDisplayCheck(project.getDBServerDisable());
		String DBServer = baseClass.getCurrentDropdownValue(project.getProjectDBServerDropdown());
		baseClass.textCompareNotEquals(DBServer, "--Select--", "Client name is auto populated","Clientname is not auto populated");
		baseClass.elementDisplayCheck(project.sizeOfProjectDataBaseDisable());

		baseClass.stepInfo("verifying ingestion,project and project folders are autopopulated with '\'");
		project.verifyingFolderName(project.getIngestionFolder());
		String ProjectFolder = project.verifyingFolderName(project.getProjectFolder());
		project.verifyingFolderName(project.getProductionFolder());
		user.navigateToUsersPAge();
		
		baseClass.stepInfo("Switching to another domain");
		baseClass.switchDomain();

		project.navigateToProductionPage();
		project.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		String ClientNameInSwitchedDomain = baseClass.getCurrentDropdownValue(project.getSelectEntity());
		baseClass.textCompareNotEquals(ClientName, ClientNameInSwitchedDomain, "Clientname is refreshed for switched domain",
				"Clientname is not refreshed");
		String ProjectFolderInSwitchedDomain = project.getProjectFolder().GetAttribute("value");
		baseClass.textCompareNotEquals(ProjectFolderInSwitchedDomain, ProjectFolder,
				"Database and Workspace is changed as per the selected domain",
				" Database and Workspace is not refreshed for selected domain");
		
        baseClass.stepInfo("Save the project and verifying the notification");
		project.navigateToProductionPage();
		project.CreatProjectInDA(ProjectName);
		project.filterTheProject(ProjectName);
		baseClass.waitTime(3);
		int n = baseClass.getIndex(project.getProjectTableHeader(), "NAME");
		String Project = project.getColumValue(n).getText().trim();
		baseClass.waitTime(2);
		baseClass.textCompareEquals(Project, ProjectName, "Newly created project is available under selected domain",
				"newly created project is not displayed");

		project.getEditBtn().waitAndClick(10);
		project.getCorpClientTextBox().SendKeys(CorporateClient);
		project.getFirmTextBox().SendKeys(Firm);
		baseClass.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifySuccessMessage("Project updated successfully");

		project.editProject(ProjectName);
		String CorpName = project.getCorpClientTextBox().GetAttribute("value");
		baseClass.textCompareEquals(CorpName, CorporateClient, "Corp name is updated with alpha numeric"," Corpname is not updated");

		String FirmName = project.getFirmTextBox().GetAttribute("value");
		baseClass.textCompareEquals(Firm, FirmName, "Firm name is updated with alpha numeric"," Firm Name is not updated");

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52813
	 * @Description:To verify that on clicking on ‘New Client’ button, Create New
	 *                 Client popup should be open.
	 **/
	@Test(description = "RPMXCON-52813", enabled = true, groups = { "regression" })
	public void verifyingClientPopUpInSA() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52813");
		baseClass
				.stepInfo("To verify that on clicking on ‘New Client’ button, Create New Client popup should be open.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		ClientsPage Client = new ClientsPage(driver);

		baseClass.stepInfo("Navigating to client page");
		Client.navigateToClientPage();
		
		baseClass.stepInfo("verifying new client popup is displayed");
		baseClass.waitForElement(Client.getAddEntity());
		Client.getAddEntity().waitAndClick(3);
		baseClass.waitTime(2);
		if (Client.getClientPopUp().isDisplayed()) {
			baseClass.passedStep("Create new client popup is displayed as expected");
		} else {
			baseClass.failedStep("Create new client popup is not displayed");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52812
	 * @Description:To verify that When a user applies a Filter by Name and then
	 *                 clicks on "Filter", the grid should present the
	 *                 clients/domains respecting the input value.
	 **/
	@Test(description = "RPMXCON-52812", enabled = true, groups = { "regression" })
	public void verifyingFilterdClientInGrid() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52812");
		baseClass.stepInfo(
				"To verify that When a user applies a Filter by Name and then clicks on 'Filter', the grid should present the clients/domains respecting the input value.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		ClientsPage Client = new ClientsPage(driver);

		String ClientName = "ABC" + Utility.dynamicNameAppender();
		String Shortname = ClientName.substring(0, 4);
		
		baseClass.stepInfo("Navigating to client page");
		Client.navigateToClientPage();
		
		baseClass.stepInfo("Creating new client and appliying filter for client");
		Client.AddNonDomainClient(ClientName);
		
		baseClass.stepInfo("verifying selected client details displaying in the Grid");
		driver.waitForPageToBeReady();
		String CLientnameInGrid = Client.getTableData("Name", 1);
		baseClass.textCompareEquals(CLientnameInGrid, ClientName, "Client Name is displayed in Grid",
				"Client Name is  not displayed in Grid");

		String ClientShortnameInGrid = Client.getTableData("Short Name", 1);
		baseClass.compareTextViaContains(ClientShortnameInGrid, Shortname, "Client ShortName is displayed in Grid","Client ShortName is  not displayed in Grid");
				

		String ClientType = Client.getTableData("Type", 1);
		baseClass.textCompareEquals(ClientType, "Not a Domain", "Client type is displayed in Grid",
				"Client type is  not displayed in Grid");

		//delete client
		Client.deleteClinet(ClientName);

		loginPage.logout();

	}
	/**
	 * @author  RPMXCON-52805
	 * @Description :Verify that for DA user, project drop down should show the
	 *              projects in the instances
	 */
	@Test(description = "RPMXCON-52805", enabled = true, groups = { "regression" })
	public void verifyDAUProjectDropDownShowProjectInstance() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52805");
		baseClass.stepInfo("Verify that for DA user, project drop down should show the projects in the instances");
		UserManagement user = new UserManagement(driver);
		ArrayList<String> DropDownValues = new ArrayList<>();
		SoftAssert softassert = new SoftAssert();

		// login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as DA user'" + Input.da1userName + "'");

		baseClass.stepInfo("Navigating to Projects Page");
		user.navigateToUsersPAge();
		baseClass.waitTime(5);
        baseClass.stepInfo("Selecting Assign user and validating the project dropdown");
        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return user.getAssignUserButton().Visible();
            }
        }), Input.wait120);
        user.getAssignUserButton().waitAndClick(10);
        baseClass.waitTime(5);
        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return user.getProjectTab().Visible();
            }
        }), Input.wait120);
        user.getProjectTab().waitAndClick(10);
        driver.waitForPageToBeReady();
		int Size = user.getAssignUserProjectDrp_Dwn().selectFromDropdown().getOptions().size();
		System.out.println(Size);
		for (int i = 2; i <= Size; i++) {
			String DropText = user.getProjectDropdownList(i).getText();
			DropDownValues.add(DropText);
		}
		baseClass.stepInfo(DropDownValues + " Existing project");
		System.out.println(DropDownValues);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// projects show in drop down
		user.verifySelectedRoleSGAccessControl("Review Manager");
		user.verifyAllProjectPresentInAccessControl(DropDownValues);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		user.verifySelectedRoleSGAccessControl("Reviewer");
		user.verifyAllProjectPresentInAccessControl(DropDownValues);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		user.verifySelectedRoleSGAccessControl(Input.ProjectAdministrator);
		user.verifyAllProjectPresentInAccessControl(DropDownValues);
		baseClass.waitTime(5);
		baseClass.waitForElement(user.getPaSecurityGroupDisabled());
		boolean sgDisabled = user.getPaSecurityGroupDisabled().isElementAvailable(2);
		softassert.assertTrue(sgDisabled);
		baseClass.passedStep("Security group drop down is read only as expected");
		softassert.assertAll();
		loginPage.logout();
	}

	/**
	 * @author NA  Testcase No:RPMXCON-52990
	 * @Description:To verify that Domain Admin user impersonate as Project Admin in different Domain successfully
	 **/
	@Test(description = "RPMXCON-52990",enabled=true, groups = { "regression" })
	public void verifyDAImpersonatePA() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);
		
		String ClientName = "" + Utility.dynamicNameAppender();
		String shrtType = "" + Utility.randomCharacterAppender(4); 
		
		baseClass.stepInfo("RPMXCON-52990");
		baseClass.stepInfo("To verify that Domain Admin user impersonate as Project Admin in different Domain successfully");
		loginPage.loginToSightLine(Input.sa1userName,  Input.sa1password);	
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		
		user.navigateToUsersPAge();
        user.passingUserName(Input.da1userName);
        user.applyFilter();
        String firstName = user.getTableData("FIRST NAME", 1);
        String lastName = user.getTableData("LAST NAME", 1);
        String userName = firstName + " " + lastName;
        
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient(ClientName, shrtType, "Domain");
		user.navigateToUsersPAge();
		user.Assignusertodomain(ClientName, userName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		driver.waitForPageToBeReady();
		baseClass.selectdomain(ClientName);
		driver.waitForPageToBeReady();
		
		baseClass.impersonateDAtoPA();
		driver.waitForPageToBeReady();
		
		baseClass.verifyCurrentRole(Input.ProjectAdministrator);
		baseClass.passedStep("To verify that Domain Admin user impersonate as Project Admin in different Domain successfully");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52909
	 * @Description:To verify that 'Short Name' is disabled on Edit Clients/Domains
	 *                 pop up
	 **/
	@Test(description = "RPMXCON-52909", groups = { "regression" })
	public void verifyShortNameDisableInClient() throws InterruptedException {
		ProjectPage project = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);

		String ClientName = "C" + Utility.dynamicNameAppender();
		String shrtType = Utility.randomCharacterAppender(4);

		baseClass.stepInfo("RPMXCON-52909");
		baseClass.stepInfo("To verify that 'Short Name' is disabled on Edit Clients/Domains pop up");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		baseClass.stepInfo("Navigating to clients/Domains page");
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		
		project.addNewClient(ClientName, shrtType, "Domain");
		baseClass.waitTime(2);
		
		 baseClass.stepInfo("verifying Short name is disabled");
		client.getEntityNameFilter().SendKeys(ClientName);
		client.getFilterButton().waitAndClick(10);
		client.getClientEditBtn(ClientName).waitAndClick(10);
		if (client.getShortNameDisable().isDisplayed()) {
			baseClass.passedStep("Short name is disabled as expected");
		} else {
			baseClass.failedStep("Short name is not disabled");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52831
	 * @Description:Verify that domain admin user cannot create System Admin user or
	 *                     Domain admin users for other domains (other than the
	 *                     current domain)
	 **/
	@Test(description = "RPMXCON-52831", groups = { "regression" })
	public void verifyingAddnewUsersDropDownInDA() throws InterruptedException {

		baseClass.stepInfo("RPMXCON-52831");
		baseClass.stepInfo(
				"Verify that domain admin user cannot create System Admin user or Domain admin users for other domains (other than the current domain)");
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As : " + Input.da1userName);

		UserManagement user = new UserManagement(driver);

		baseClass.selectdomain(Input.domainName);
		
		baseClass.stepInfo("Navigating to user page");
		user.navigateToUsersPAge();
		
		baseClass.stepInfo("selecting Add new user button");
		user.openAddNewUserPopUp();
		baseClass.availableListofElements(user.getUserDropdown());
		
		baseClass.stepInfo("verifying SA role is not displaying in dropdown");
		List<String> Users = baseClass.availableListofElements(user.getUserDropdown());
		System.out.println("user list" + Users);
		if (!Users.contains(Input.SystemAdministrator)) {
			baseClass.passedStep("System admin role is not displayed in Domain admin user");
		} else {
			baseClass.failedStep("System admin is displayed");
		}
		
		baseClass.stepInfo("verifying domain is displayed and read only");
		baseClass.waitForElement(user.getSelectRole());
		user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		if (user.getDomainName(Input.domainName).isDisplayed()) {
			baseClass.passedStep("Domain is ready only and non-editable");
		} else {
			baseClass.failedStep("domain is not read only");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52859
	 * @Description:Verify that Sys Admin should be able to impersonate as Domain
	 *                     Admin
	 **/
	@Test(description = "RPMXCON-52859", groups = { "regression" })
	public void verifyingImpersonatingSAToDA() throws InterruptedException {
		Dashboard dash = new Dashboard(driver);

		baseClass.stepInfo("RPMXCON-52859");
		baseClass.stepInfo("Verify that Sys Admin should be able to impersonate as Domain Admin");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);


		baseClass.stepInfo("Selecting domain administrator and select domain");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getChangeRole());
		baseClass.getChangeRole().waitAndClick(5);
		baseClass.waitForElement(baseClass.getSelectRole());
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.waitForElement(baseClass.getAvlDomain());
		
		baseClass.stepInfo("verifying domain dropdown is displayed");
		baseClass.elementDisplayCheck(baseClass.getAvlDomain());
		
		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitTime(1);
		baseClass.waitForElement(baseClass.getSaveChangeRole());
		baseClass.getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersonated from SA to DA");

		baseClass.waitTime(2);
		baseClass.stepInfo("verifying Domain landing page");
		if (dash.getDomainDashboardPage().isDisplayed()) {
			baseClass.passedStep("SA is impersonated as DA and user navigates to domain landing page");
		} else {
			baseClass.failedStep("SA is not impersonated as DA and not navigates to domain landing page");
		}
		
		loginPage.logout();

	}
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-52816
	 * Description :verify that Created By and Created On date column should be displayed on the Grid
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-52816",enabled = true, groups = { "regression" })
	public void verifyColumnDisplayedInGrid() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52816");
		baseClass.stepInfo("verify that Created By and Created On date column should be displayed on the Grid");
		
		//Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("go to manage clients section");
		domainDashboard.navigateToManageClientSection();
		baseClass.passedStep("Manage clients page displayed");
		baseClass.stepInfo("Verify columns displayed on grid");
		domainDashboard.verifyColumnPresentInClientGridTable();
		loginPage.logout();
	}
	/**
	 * @author Brundha.T Testcase No:RPMXCON-52921
	 * @Description:To verify that System Admin can change the Type from Non-Domain
	 *                 to Domain from Edit popup window
	 **/
	@Test(description = "RPMXCON-52921", enabled = true, groups = { "regression" })
	public void verifyNewPathInIngestionFolder() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52921");
		baseClass.stepInfo(
				"To verify that System Admin can change the Type from Non-Domain to Domain from Edit popup window");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		ProjectPage projects = new ProjectPage(driver);
		ClientsPage Client = new ClientsPage(driver);

		baseClass.stepInfo("Navigating to client page");
		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, "Not a Domain");

		baseClass.stepInfo("Filter the not a domain client");
		Client.filterClient(clientName);

		baseClass.stepInfo("Editing existing not a domain client to domain");
		Client.getClientEditBtn(clientName).waitAndClick(10);
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");
		driver.waitForPageToBeReady();

		String domainName = "D" + Utility.dynamicRandomNumberAppender();
		baseClass.waitForElement(projects.getDomainName());
		projects.getDomainName().SendKeys(domainName);
		driver.scrollingToBottomofAPage();

		baseClass.waitForElement(projects.getProjectDBServerDropdown());
		if (projects.getProjectDBServerDropdown().isElementPresent()) {
			baseClass.passedStep("Servers are displayed as expected");
		} else {
			baseClass.failedStep("Servers are not displayed");
		}
		projects.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
		projects.getProjectServerPath().waitAndClick(10);
		baseClass.waitForElement(projects.getIngestionserverpath());
		projects.getIngestionserverpath().waitAndClick(2);
		baseClass.waitForElement(projects.getProductionserverpath());
		projects.getProductionserverpath().waitAndClick(2);
		projects.getClientNameSaveBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("The client details were updated successfully");

		baseClass.stepInfo("verifying updated domain type in grid page");
		Client.filterClient(clientName);
		int ColHeader = baseClass.getIndex(Client.getClientTableHeaders(), "TYPE");
		String Domain = Client.getColumnValueinDomainClient(ColHeader).getText();
		if (Domain.equals("Domain")) {
			baseClass.stepInfo("Non-domin project is updated as domain");
		} else {
			baseClass.failedStep("Non-domain Project is not updated as domain");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52858
	 * @Description:Validate “In Domain” column value sorting on Project list screen
	 *                       for System Admin user
	 **/
	@Test(description = "RPMXCON-52858", enabled = true, groups = { "regression" })
	public void verifyingSortingOfColumnInProjects() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52858");
		baseClass.stepInfo("Validate “In Domain” column value sorting on Project list screen for System Admin user");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		ProjectPage projects = new ProjectPage(driver);

		baseClass.stepInfo("Navigating to Projects Page");
		projects.navigateToProductionPage();

		baseClass.stepInfo("verifying Ascending and descending sorting in IN DOMAIN column");
		for (int i = 1; i <= 2; i++) {
			driver.waitForPageToBeReady();
			baseClass.text("In Domain").waitAndClick(5);

			if (i == 1) {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Ascending");
			} else {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Descending");
			}
			baseClass.stepInfo(
					"Selecting navigation Button and verifying the sorting order is maintained in Successive pages");
			driver.waitForPageToBeReady();
			projects.getLastPageNavigation().waitAndClick(5);

			if (i == 1) {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Ascending");
			} else {
				projects.verifyingSortingOrderInColumn("IN DOMAIN", true, "Descending");
			}
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-52833
	 * @Description:Verify that from Add User pop up the Project drop-down should
	 *                     show only projects belonging to the current domain
	 **/
	@Test(description = "RPMXCON-52833", enabled = true, groups = { "regression" })
	public void verifyingProjectDropdownInAddNewUser() throws Exception {

		baseClass.stepInfo("TestCase id : RPMXCON-52833");
		baseClass.stepInfo(
				"Verify that from Add User pop up the Project drop-down should show only projects belonging to the current domain");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);

		baseClass.selectdomain(Input.domainName);

		baseClass.waitTime(3);
		ArrayList<String> Values = new ArrayList<>();
		ArrayList<String> DropDownValues = new ArrayList<>();
		ProjectPage projects = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Navigating to Projects Page");
		projects.navigateToProductionPage();

		baseClass.waitTime(3);
		baseClass.stepInfo("getting the projects in current domain");
		String Count = projects.getLastPageNavigation().getText();
		int TotalCount = Integer.valueOf(Count);
		System.out.println(TotalCount);

		for (int i = 0; i <= TotalCount; i++) {
			baseClass.waitTime(1);
			int size = user.getProjectNameCol().size();
			for (int j = 1; j <=size; j++) {
				driver.waitForPageToBeReady();
				String PrjtName = user.getProjectNameColValue(j).getText();
				Values.add(PrjtName);
			}
			System.out.println(Values);
			driver.waitForPageToBeReady();
			String NextBtn = user.getNextBtn().GetAttribute("Class");
			if (NextBtn.contains("disabled")) {
				break;
			} else {
				driver.waitForPageToBeReady();
				user.getUserListNextButton().waitAndClick(5);
			}
		}
		user.navigateToUsersPAge();
		baseClass.stepInfo("Selecting Add new user and validating the project dropdown");
		baseClass.waitForElement(user.getAddUserBtn());
		user.getAddUserBtn().Click();
		driver.waitForPageToBeReady();
		user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		int Size = user.getProjectDropdown().selectFromDropdown().getOptions().size();
		baseClass.waitTime(2);
		for (int i = 2; i <= Size; i++) {
			String DropText = user.getDomainProjectDropdown(i).GetAttribute("title");
			DropDownValues.add(DropText);
		}
		System.out.println(DropDownValues);
		if (Values.equals(DropDownValues)) {
			baseClass.passedStep("Project drop-down shows projects belonging to the current domain of domain admin");
		} else {
			baseClass.failedStep("Project drop-down not showing projects belonging to the current domain of domain admin");
		}
		loginPage.logout();
	}
	

	/**
	 * @Author :Indium-Baskar date: NA Modified date:23/09/2022 Modified by:23/09/2022
	 * @Description :To verify that if user is DA for one or more domains then it should 
	 *                displayed in different rows for that user
	 */
	@Test(description ="RPMXCON-52796",alwaysRun = true, groups = { "regression" })
	public void verifyingDomainNameInDiffRow() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52796");
		baseClass.stepInfo(
				"To verify that if user is DA for one or more domains then it "
				+ "should displayed in different rows for that user");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		// login as Da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		baseClass.waitTime(3);
		
		// verifying the grid for domain header
		int count=userManage.getTableColumnData(7).size();
		List<WebElement> data=userManage.getTableColumnData(7).FindWebElements();
		List<String> domainInt=new ArrayList<String>();
		for (WebElement webElement : data) {
			String domain=webElement.getText().toString();
			domainInt.add(domain);
		}
		if (count==domainInt.size()&&count<=2) {
			baseClass.passedStep("Mutiple rows displayed for domain header, when different domain access for same username");
		}
		else {
			baseClass.failedStep("All domain username displed in single row");
		}
	
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:23/09/2022 Modified by:23/09/2022
	 * @Description :To verify that the Currently logged in Domain will be shown in header 
	 *                at top as selected value in Dropdown
	 */
	@Test(description ="RPMXCON-52799",alwaysRun = true, groups = { "regression" })
	public void verifyingDomainDrpDwnValue() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52799");
		baseClass.stepInfo(
				"To verify that the Currently logged in Domain will be "
				+ "shown in header at top as selected value in Dropdown");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		domainDashboard =new DomainDashboard(driver);
		
		// login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		
		// validation for dropdown and current value
		boolean drpdwn=domainDashboard.getDomainDrpDwn().isElementAvailable(2);
		softAssertion.assertTrue(drpdwn);
		baseClass.stepInfo("Domain dropdwon displayed in Da user");
		boolean domain=domainDashboard.getCurrentDomainValue(Input.domainName).isElementAvailable(2);
		softAssertion.assertTrue(domain);
		baseClass.stepInfo("Current domain value displayed in dropdown");
		softAssertion.assertAll();
		baseClass.passedStep("To verify that the Currently logged in Domain will be"
				+ "shown in header at top as selected value in Dropdown");
		
		// logout
		loginPage.logout();
	}
	

	@DataProvider(name = "sada")
	public Object[][] sada() {
		Object[][] users = {
				{ Input.sa1userName, Input.sa1password ,"sa"  },
				{ Input.da1userName, Input.da1password,"da"} };
		return users;
	}

	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:26/09/2022 Modified by:26/09/2022
	 * @Description :Verify that 'Bulk User Access Control' is accessible to SA and DA
	 */
	@Test(description ="RPMXCON-52802",alwaysRun = true, dataProvider = "sada", groups = { "regression" })
	public void verifyingBulkUserTab(String userName,String password,String role) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52802");
		baseClass.stepInfo(
				"Verify that 'Bulk User Access Control' is accessible to SA and DA");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		// login as 
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		boolean bulktab=userManage.getBulkUserAccessTab().isElementAvailable(2);
		softAssertion.assertTrue(bulktab);
		baseClass.stepInfo("Bulk user tab icon displayed in"+role+" user");
		userManage.getBulkUserAccessTab().waitAndClick(5);
		boolean popup=userManage.getBulkUserPopUp().isDisplayed();
		softAssertion.assertTrue(popup);
		baseClass.stepInfo("Bulk user tab accessible for"+role+" user");
		softAssertion.assertAll();
		baseClass.passedStep("Verified that 'Bulk User Access Control' is accessible to"+role+"");
		
		// logout
		loginPage.logout();
	}
	

	/**
	 * @Author :Indium-Baskar date: NA Modified date:26/09/2022 Modified by:26/09/2022
	 * @Description :Verify that for System Admin, project drop down should show all the projects in the instances
	 */
	@Test(description ="RPMXCON-52804",alwaysRun = true,groups = { "regression" })
	public void verifyProjectDropDown() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52804");
		baseClass.stepInfo(
				"Verify that for System Admin, project drop down should show all the projects in the instances");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		// login as 
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "Project/Project");
		List<String> firstValue=new ArrayList<String>();
		String count=userManage.getProjectPageLastNumber().getText().toString();	
		int foo = Integer.parseInt(count);
		for (int i = 0; i<foo; i++) {
			boolean status=userManage.getProjectName(1).isElementAvailable(4);
			if (status == true) {
				baseClass.waitTime(3);
				List<WebElement> firstNames =userManage.getProjectName(1).FindWebElements();
				for (WebElement webElement : firstNames) {
					String assignUser= webElement.getText();
					System.out.println(assignUser);
					firstValue.add(assignUser);
				}
					if (userManage.getUserPaginationNextButton().isElementAvailable(5)) {
						userManage.getUserPaginationNextButton().waitAndClick(5);
						
					}
				}
		
			}
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.waitForElement(userManage.getBulkUserAccessTab());
		userManage.getBulkUserAccessTab().waitAndClick(5);
		String dataSet[][] = { {"Project Administrator","pa"}, {"Review Manager","rmu"},
				{ "Reviewer","rev"}};
		String role;
		String login;
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			login = dataSet[i][j];
			j++;
			role = dataSet[i][j];
			if (role=="pa"||role=="rmu"||role=="rev") {
				baseClass.waitForElement(userManage.getSelectRollId());
				userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(login);
				baseClass.stepInfo("Selecting the role as "+login+"");
				baseClass.waitForElement(userManage.getSelectingProject());
				userManage.getSelectingProject().waitAndClick(5);
				baseClass.waitForElement(userManage.getSelectingProject());
				List<WebElement> data=userManage.getSelectingProject().selectFromDropdown().getOptions();
				baseClass.passedStep("All project are displayed in dropdown in bulk user control popup");
				if (role=="pa") {
					baseClass.waitForElement(userManage.getPaSecurityGroupDisabled());
					boolean sgDisabled=userManage.getPaSecurityGroupDisabled().isElementAvailable(2);
					softAssertion.assertTrue(sgDisabled);
					baseClass.passedStep("Security group drop down disabled for"+login+"");
				}
				if (role=="rmu"||role=="rev") {
					baseClass.waitForElement(userManage.getPaSecurityGroupDisabled());
					boolean enabled=userManage.getPaSecurityGroupDisabled().isElementAvailable(2);
					softAssertion.assertFalse(enabled);
					baseClass.passedStep("Security group drop down enabled for"+login+"");
				}
				
			}
		}
		softAssertion.assertAll();
		
		// logout
		loginPage.logout();
	}
	

	/**
	 * @Author : Aathith
	 * @Description : To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.
	 */
	@Test(description = "RPMXCON-52792",enabled = true, groups = { "regression" })
	public void verifyDomainNameEmpty() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-52792");
	    baseClass.stepInfo("To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
		
	    UserManagement user = new UserManagement(driver);
	    SoftAssert soft = new SoftAssert();
				
		String email = Input.randomText+Utility.dynamicNameAppender()+"@consilio.com";
		
		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as sa user'" + Input.sa1userName + "'");
		
		user.createNewUser(Input.randomText, Input.randomText, Input.ProjectAdministrator, email, null, Input.NonDomainProject);
		user.filterByName(email);
		driver.waitForPageToBeReady();
		soft.assertEquals(user.getTableData("DOMAIN",1),"");
		baseClass.passedStep("Domain column value is blank.");
		
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.RemoveUser();
	    
	    soft.assertAll();
	    baseClass.passedStep("verified that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
	    loginPage.logout();
	}
	
	/**
	 * @Author : Aathith
	 * @Description : To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.
	 */
	@Test(description = "RPMXCON-52793",enabled = true, groups = { "regression" })
	public void verifyDomainDisplayCrctly() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-52793");
	    baseClass.stepInfo("To verify that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
		
	    UserManagement user = new UserManagement(driver);
	    SoftAssert soft = new SoftAssert();
				
		String email = Input.randomText+Utility.dynamicNameAppender()+"@consilio.com";
		
		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as sa user'" + Input.sa1userName + "'");
		
		user.createNewUser(Input.randomText, Input.randomText, Input.ProjectAdministrator, email, Input.domainName, Input.projectName);
		user.filterByName(email);
		driver.waitForPageToBeReady();
		soft.assertEquals(user.getTableData("DOMAIN",1), Input.domainName);
		baseClass.passedStep("Domain column is populated with a correct value ");
		
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.RemoveUser();
	    
	    soft.assertAll();
	    baseClass.passedStep("verified that if user is a part of non-domain Projects, then ‘Domain’ column should be blank.");
	    loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:27/09/2021 Modified by: Baskar
	 * Description :Verify that error /validation message should be displayed when 
	 *              domain admin user adds system admin as domain admin user
	 */
	@Test(description ="RPMXCON-52838",alwaysRun = true, groups = { "regression" })
	public void createNewUserForDomain() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.sa1userName;
		baseClass.stepInfo("Test case Id: RPMXCON-52838");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that error /validation message should be displayed "
				+ "when domain admin user adds system admin as domain admin user");
		userManage = new UserManagement(driver);
		softAssertion =new SoftAssert();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1userName);

		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.stepInfo("Create new user for domain administration, validating error message");
		userManage.validateErrorMsgForNewUser(firstName, lastName, role, emailId, " ", Input.projectName,Input.securityGroup,true);
		baseClass.passedStep("Error validation message displayed when creating a new domain user with sa emailid");
		loginPage.logout();

	}

	/**
	* Author : Baskar date: NA Modified date:28/09/2021 Modified by: Baskar
	 * @Description : Verify that for DA user, rights should be greyed out
	 *                 as per the selected role from drop down
	 */
	@Test(description = "RPMXCON-52806",enabled = true, groups = { "regression" })
	public void validateRightInGrayForSelectedRole() throws Exception {
		
	    baseClass.stepInfo("Test case Id: RPMXCON-52806");
	    baseClass.stepInfo("Verify that for DA user, rights should be greyed out "
	    		+ "as per the selected role from drop down");
		
	    userManage = new UserManagement(driver);
		softAssertion =new SoftAssert();
		DomainDashboard dash =  new DomainDashboard(driver);		
		
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");
		
		baseClass.selectproject(Input.domainName);
		dash.waitForDomainDashBoardIsReady();
		
		//check for rmu
		userManage.navigateToUsersPAge();
		userManage.selectRoleBulkUserAccessControl(Input.ReviewManager, Input.projectName, Input.securityGroup);
		driver.waitForPageToBeReady();
		softAssertion.assertTrue(userManage.getBulkIngestion().GetAttribute("style").contains("grey"));
		baseClass.passedStep("For RMU 'Ingestion' should be greyed out.");
		
		//check with rev
		userManage.navigateToUsersPAge();
		userManage.selectRoleBulkUserAccessControl(Input.Reviewer, Input.projectName, Input.securityGroup);
		driver.waitForPageToBeReady();
		softAssertion.assertTrue(userManage.getBulkManage().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkIngestion().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkProduction().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkCatagories().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkDataSet().GetAttribute("style").contains("grey"));
		softAssertion.assertTrue(userManage.getBulkReport().GetAttribute("style").contains("grey"));
		baseClass.passedStep("For Reviewer below rights greyed out  - Manage  - Ingestion  - Productions  - Categorize  - DataSets  - All Reports");
		softAssertion.assertAll();
	    baseClass.passedStep("Verified that for DA user, rights should be greyed out as per the selected role from drop down");
	    loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate new project creation of Domain client type by Domain Admin(Impersonate from System Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52897",enabled = true, groups = {"regression" })
	public void validateNewlyCreatedProjectInDomainAdmin() throws InterruptedException  {
		BaseClass base = new BaseClass(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProjectPage project = new ProjectPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52897");
		base.stepInfo("Validate new project creation of Domain client type by Domain Admin(Impersonate from System Admin)");
		
		
		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String alfaNumeric = "Aa006";
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		//navigated to project page
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		base.stepInfo("navigatd to Project Page");
		
		//add new project button clicked
		driver.waitForPageToBeReady();
		base.waitForElement(project.getAddProjectBtn());
		project.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("add new project button was clicked");
		base.waitForElement(project.getProjectName());
		project.getProjectName().SendKeys(projectName);
		
		//verificataion
		if(base.text(Input.domainName).isDisplayed()) {
			base.passedStep("Client name was list all client names that are part of the selected \"Domain\"");
		}else {
			base.failedStep("client name display failed");
		}
		if(base.text("Database").isElementPresent()&&base.text("Workspace").isElementPresent()) {
			base.passedStep("Database and workspace paths was populate of the selected Domain");
		}else {
			base.failedStep("workspace and database display failed");
		}
		if(!base.text("HCode").isDisplayed()) {
			base.passedStep("\"HCode\" field is not present ");
		}else {
			base.failedStep("Hcode was present");
		}
		project.getProductionFolder().ScrollTo();
		if( project.getProjectFolder().isDisplayed() && project.getIngestionFolder().isDisplayed() 
				&& project.getProductionFolder().isDisplayed() &&base.text("/").isElementPresent()) {
			base.passedStep("The project folder, ingestion folder and production folder was autopopulate with '/' ");
		}else {
			base.failedStep("'/' verification failed");
		}
		project.getFirmTextBox().SendKeys(alfaNumeric);
		project.getCorpClientTextBox().SendKeys(alfaNumeric);
		base.passedStep("User should be able to enter any alphanumeric string in the \"Firm\" and \"Corp Client\" text box.");
		if(!base.text("Settings").isDisplayed()) {
			base.stepInfo("\"Settings\" tab is not present");
		}else {
			base.failedStep("setting tab verification failed");
		}
		
		//save the project
		base.clearBullHornNotification();
		project.getButtonSaveProject().waitAndClick(10);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName); 
		
		//verify project available in project list
		project.filterTheProject(projectName);
		if(base.text(projectName).isDisplayed()) {
			base.passedStep("Newly created project should be available in project list");
		}else {
			base.failedStep("Project not dipalyed");
		}
		
		base.passedStep("Validate new project creation of Domain client type by Domain Admin(Impersonate from System Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate HCode value while creating new project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52899",enabled = true, groups = {"regression" })
	public void validateHCodeValue() throws InterruptedException  {
		
		BaseClass base = new BaseClass(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProjectPage project = new ProjectPage(driver);
		base.stepInfo("Test case Id: RPMXCON-52899");
		base.stepInfo("Validate HCode value while creating new project");
		
		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String alfaNumeric = "Aa"+Utility.dynamicNameAppender();
		String hcode = "#!&%$hi123_"+Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//navigated to project page
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		base.stepInfo("navigatd to Project Page");
		
		//add new project button clicked
		driver.waitForPageToBeReady();
		base.waitForElement(project.getAddProjectBtn());
		project.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("add new project button was clicked");
		base.waitForElement(project.getProjectName());
		project.getProjectName().SendKeys(projectName);
		
		//verificataion
		if(base.text("Not a Domain").isDisplayed()) {
			base.passedStep("By default Client type is \"Not a Domain\" selected ");
		}else {
			base.failedStep("Not a domain verification failed");
		}
		if(project.getSelectEntity().Visible()) {
			base.passedStep("Client name is list all client names that are of type \"Not a Domain\"");
		}else {
			base.failedStep("client name verification failed");
		}
		project.getSelectEntity().selectFromDropdown().selectByIndex(1);
		if(base.text("Database").isElementPresent()&&base.text("Workspace").isElementPresent()) {
			base.passedStep("Database and workspace paths was populate of the selected Domain");
		}else {
			base.failedStep("workspace and database display failed");
		}
		project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
		if(base.text("HCode").isDisplayed()) {
			base.passedStep("\"HCode\" field should be available for user entry");
		}else {
			base.failedStep("Hcode was not present");
		}
		project.getProductionFolder().ScrollTo();
		if( project.getProjectFolder().isDisplayed() && project.getIngestionFolder().isDisplayed() 
				&& project.getProductionFolder().isDisplayed()) {
			base.passedStep("The project folder, ingestion folder and production folder was autopopulate");
		}else {
			base.failedStep("verification failed");
		}
		project.getProjectServerPath().waitAndClick(10);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(0);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(1);
		project.getProductionserverpath().waitAndClick(10);
		project.getProjectFolder().SendKeys(Input.randomText);
		project.getIngestionFolder().SendKeys(Input.randomText);
		project.getProductionFolder().SendKeys(Input.randomText);
		project.getFirmTextBox().SendKeys(alfaNumeric);
		project.getCorpClientTextBox().SendKeys(alfaNumeric);
		base.passedStep("User should be able to enter any alphanumeric string in the \"Firm\" and \"Corp Client\" text box.");
		if(base.text("Settings").isDisplayed()) {
			base.passedStep("\"Settings\" tab was enabled and user should be able select/edit required values.");
		}else {
			base.failedStep("setting tab verification failed");
		}
		project.getAddProject_SettingsTab().waitAndClick(10);
		project.getNoOfDocuments().SendKeys("20000");
		project.getHCode().SendKeys(hcode+Keys.ENTER);
		if(base.text("You cannot specify any special characters in the field value").isDisplayed()) {
			base.passedStep("Appropriate validation message should be displayed indicating that it cannot containing special characters");
		}else {
			base.failedStep("validation message verification failed");
		}
		project.getHCode().SendKeys(alfaNumeric);
		
		
		//save the project
		base.clearBullHornNotification();
		project.getButtonSaveProject().waitAndClick(10);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName); 
		
		//verify project available in project list
		project.filterTheProject(projectName);
		if(base.text(projectName).isDisplayed()) {
			base.passedStep("Newly created project should be available in project list");
		}else {
			base.failedStep("Project not dipalyed");
		}
		int column = base.getIndex(project.getProjectTableHeader(), "IN DOMAIN");
		if(project.getColumValue(column).getText().trim().equals("No")) {
			base.passedStep("project was listed as Non-Domain project.");
		}else {
			base.failedStep("non domain verification failed");
		}
		
		base.passedStep("Validated HCode value while creating new project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description : To verify that System Admin can change the Type from Domain to 'Not a Domain' from Edit popup window
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52923",enabled = true, groups = {"regression" })
	public void verifySysAdminChangeTypeDomainToNonDomain() throws InterruptedException  {
		BaseClass base = new BaseClass(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProjectPage project = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		base.stepInfo("Test case Id: RPMXCON-52923");
		base.stepInfo("To verify that System Admin can change the Type from Domain to 'Not a Domain' from Edit popup window");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String clientName = "C"+ Utility.dynamicNameAppender();
		String domainid = "D"+ Utility.dynamicNameAppender();
		
		//add new client
		client.navigateToClientPage();
		base.stepInfo("Navigated to clientPage");
		client.AddDomainClient(clientName,domainid);
		
		//edit client
		client.filterClient(clientName);
		client.getClientEditBtn(clientName).waitAndClick(10);
		project.getSelectEntity().selectFromDropdown().selectByVisibleText("Not a Domain");
		driver.waitForPageToBeReady();
		if(!base.textValue("Database").isElementAvailable(1)) {
			base.passedStep("It should hide all the settings which was required for Domain Client.");
		}else {
			base.failedStep("setting hide verification failed");
		}
		if(base.text("Name").isElementPresent()&&base.text("Short Name").isElementPresent()&&base.text("Type").isElementPresent()) {
			base.passedStep("Only Client Name, Short Name and Type drop down is displayed");
		}else {
			base.failedStep("label verification failed");
		}
		client.getSaveBtn().waitAndClick(10);
		base.VerifySuccessMessage("The client details were updated successfully");
		base.CloseSuccessMsgpopup();
		//verify client
		client.filterClient(clientName);
		int column = base.getIndex(client.getClientTableHeaders(), "TYPE");
		if(client.getClientTableValue(clientName, column).getText().trim().equals("Not a Domain")) {
			base.passedStep("It is update the Type. Also there should not be any impact for already created projects.");
		}else {
			base.failedStep("type vetification failed");
		}
		client.deleteClinet(clientName);
		
		base.passedStep("verified that System Admin can change the Type from Domain to 'Not a Domain' from Edit popup window");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/26/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate modifying all editable field values and save changes for a domain project by System Admin(Impersonate to Domain Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52965",enabled = true, groups = {"regression" })
	public void validateAllEditableFiedSysAdminImperSonate() throws InterruptedException  {
		
		BaseClass base = new BaseClass(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProjectPage project = new ProjectPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-52965");
		base.stepInfo("Validate modifying all editable field values and save changes for a domain project by Domain Admin");
		
		String ModifyName = Input.randomText + Utility.dynamicNameAppender();
		
		//login as Sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		//action edit
		project.navigateToProductionPage();
		String projectName = project.checkTempDomainProjectIsAvailable();
		project.editProject(projectName);
		
		//modification
		project.getProjectName().SendKeys(ModifyName);
		project.getFirmTextBox().SendKeys(ModifyName);
		project.getCorpClientTextBox().SendKeys(ModifyName);
		if(!project.getVerifyInputValues(ModifyName).isElementAvailable(1)) {
			base.passedStep("The folder names will not be updated to reflect the change, and will remain the same");
		}else {
			base.failedStep("not update verification failed");
		}
		project.getButtonSaveProject().waitAndClick(10);
		base.VerifySuccessMessage("Project updated successfully");
		
		//verify
		project.editProject(ModifyName);
		if(project.getVerifyInputValues(ModifyName).isElementPresent()) {
			base.passedStep("Updated changes was reflect");
		}else {
			base.failedStep("updated the verification failed");
		}
		
		//restore default name
		project.getProjectName().SendKeys(projectName);
		project.getButtonSaveProject().waitAndClick(10);
		
		base.passedStep("Validated modifying all editable field values and save changes for a domain project by System Admin(Impersonate to Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/02/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate modifying all editable field values and save changes for a domain project by Domain Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53000",enabled = true, groups = {"regression" })
	public void verifyNotificationForProjectCompletionInDa() throws InterruptedException  {
		
		BaseClass base = new BaseClass(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProjectPage project = new ProjectPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53000");
		base.stepInfo("Verify that notification should be received upon completion of the project successfully with DA User");
		
		String name = Input.randomText + Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(name);
		base.waitForNotification();
		dash.getNotificationMessage(0, name);
		
		base.passedStep("Verify that notification should be received upon completion of the project successfully with DA User");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/02/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify user must be able to click on Notification once background task get completed.
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53005",enabled = true, groups = {"regression" })
	public void verifyUserAbleToClickNotification() throws InterruptedException  {
		
		BaseClass base = new BaseClass(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProjectPage project = new ProjectPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53005");
		base.stepInfo("Verify user must be able to click on Notification once background task get completed.");
		
		String ProjectName = Input.randomText + Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(ProjectName);
		dash.naviageToDomainDashBoardPage();
		base.waitForNotification();
		String currentURL = driver.getWebDriver().getCurrentUrl();
		dash.getNotificationMessage(0, ProjectName);
		base.clickFirstBackRoundTask();
		driver.waitForPageToBeReady();
		base.passedStep("Any entry in the Background Tasks Page are clickable");
		String currentURL1 = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertNotEquals(currentURL, currentURL1);
		softAssertion.assertAll();
		base.passedStep("and will take the user to the respective page.");
		
		base.passedStep("Verify user must be able to click on Notification once background task get completed.");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 24-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate new project creation of non-Domain client by System Admin user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-46915",enabled = true, groups = {"regression" })
	public void validatedNewlyCreatedNonDomain() throws InterruptedException  {
		BaseClass base = new BaseClass(driver);
		ProjectPage project = new ProjectPage(driver);
		base.stepInfo("Test case Id: RPMXCON-46915");
		base.stepInfo("Validate new project creation of non-Domain client by System Admin user");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String[] validationMessage = {
				"You must specify the project name",
				"You must specify the client entity",
				"You must specify a project Hcode",
				"You must specify a value for the project db server.",
				"Project Workspace is required",
				"Ingestion Server Path is required",
				"Production Server Path is required",
				"You must specify the project folder",
				"You must specify the ingestion folder.",
				"You must specify the production folder",
		};
		String projectnamenondomain = "AutomationScriptCreatedNonDomain"+Utility.dynamicNameAppender();
		String hcode = "hcode"+Utility.dynamicNameAppender();
		
		base.clearBullHornNotification();
		project.navigateToProductionPage();
		project.getAddProjectBtn().waitAndClick(10);
		project.getButtonSaveProject().waitAndClick(10);
		driver.scrollPageToTop();
		base.isTextAreAvailableInWebPage(validationMessage);
		
		project.navigateToProductionPage();
		project.AddNonDomainProject(projectnamenondomain, hcode);
		
		base.getRedBullHornIcon().isElementAvailable(300);
		project.filterTheProject(projectnamenondomain);
		int n = base.getIndex(project.getProjectTableHeader(), "IN DOMAIN");
		String inDomain = project.getColumValue(n).getText().trim();
		
		if(inDomain.equals("No")) {
			base.passedStep("Newly created project is available in project list and is listed as Non-Domain project.");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Validated new project creation of non-Domain client by System Admin user");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 24-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate new project creation of Domain client by System Admin user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-46916",enabled = true, groups = {"regression" })
	public void validatedNewlyCreatedDomain() throws InterruptedException  {
		BaseClass base = new BaseClass(driver);
		ProjectPage project = new ProjectPage(driver);
		base.stepInfo("Test case Id: RPMXCON-46916");
		base.stepInfo("Validate new project creation of Domain client by System Admin user");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String[] validationMessage = {
				"You must specify the project name",
				"You must specify the client entity",
				"You must specify a project Hcode",
				"You must specify a value for the project db server.",
				"Project Workspace is required",
				"Ingestion Server Path is required",
				"Production Server Path is required",
				"You must specify the project folder",
				"You must specify the ingestion folder.",
				"You must specify the production folder",
		};
		String projectnamenondomain = "AutomationScriptCreatedDomain"+Utility.dynamicNameAppender();
		
		base.clearBullHornNotification();
		project.navigateToProductionPage();
		project.getAddProjectBtn().waitAndClick(10);
		project.getButtonSaveProject().waitAndClick(10);
		driver.scrollPageToTop();
		base.isTextAreAvailableInWebPage(validationMessage);
		
		project.navigateToProductionPage();
		project.AddDomainProject(projectnamenondomain, Input.domainName);
		
		base.getRedBullHornIcon().isElementAvailable(300);
		project.filterTheProject(projectnamenondomain);
		int n = base.getIndex(project.getProjectTableHeader(), "IN DOMAIN");
		String inDomain = project.getColumValue(n).getText().trim();
		
		if(inDomain.equals("Yes")) {
			base.passedStep("Newly created project is available in project list and is listed as Domain project.");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Validated new project creation of Domain client by System Admin user");
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
