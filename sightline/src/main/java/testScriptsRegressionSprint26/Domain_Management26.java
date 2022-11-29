package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Domain_Management26 {
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
		user.deleteUser();

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
		String LastName = "Framework";
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

			baseClass.stepInfo("Verify user details in Active users popup");
			user.verifyUserDetailsOnUserNotLoggedInPopup(UserInActivePopup, MailID);

			// delete the created user
			user.filterTodayCreatedUser();
			user.filterByName(MailID);
			user.deleteUser();
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

			baseClass.stepInfo("Verify user details in Active users popup");
			user.verifyUserDetailsOnUserNotLoggedInPopup(UserInActivePopup, MailID);

			// delete the created user
			user.filterTodayCreatedUser();
			user.filterByName(MailID);
			user.deleteUser();
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
		user.deleteUser();

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
		String FirstName = "DA";
		String LastName = "automation";
		String MailID = "test" + Utility.dynamicNameAppender() + "@consilio.com";
		String UserName = FirstName + " " + LastName;

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");

		user.navigateToUsersPAge();
		baseClass.stepInfo("Creating new project Administrator user");
		user.createUser(FirstName, LastName, Input.DomainAdministrator, MailID, null, null);

		baseClass.stepInfo("Assigning project to user");
		user.openAssignUser();
		user.getDomaintab().waitAndClick(5);
		baseClass.waitForElement(user.getSelectDomainname());
		user.getSelectDomainname().selectFromDropdown().selectByIndex(1);
		user.getSelectusertoassignindomain().selectFromDropdown().selectByVisibleText(UserName);
		baseClass.waitForElement(user.getrightBtndomainuser());
		user.getrightBtndomainuser().waitAndClick(5);
		user.getsavedomainuser().waitAndClick(5);
		baseClass.VerifySuccessMessage("User Mapping Successful");
		baseClass.stepInfo("Domain user Assiged succesfully");
		baseClass.passedStep("Success message is displayed.");

		// delete the created user
		driver.waitForPageToBeReady();
		user.filterTodayCreatedUser();
		driver.waitForPageToBeReady();
		user.filterByName(MailID);
		user.deleteUser();

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
