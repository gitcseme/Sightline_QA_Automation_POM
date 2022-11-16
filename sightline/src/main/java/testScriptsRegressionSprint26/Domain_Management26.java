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
		String FirstName = "QA";
		String LastName = "consilio";
		String MailID = "testing" + "@consilio.com";
		UserManagement user = new UserManagement(driver);
		baseClass.selectdomain(Input.domainName);
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
