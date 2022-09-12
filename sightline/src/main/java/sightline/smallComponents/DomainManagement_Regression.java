package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	UserManagement userManage;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
	DomainDashboard doamain;
	ProjectPage projectPage;
	DataSets data;

	String newProject = Input.randomText + Utility.dynamicNameAppender();
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

	@DataProvider(name = "role")
	public Object[][] role() {
		Object[][] role = { { Input.sa1userName, Input.sa1password, Input.pa1userName, "Project Administrator" },
				{ Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.ReviewManager },
				{ Input.sa1userName, Input.sa1password, Input.rev1userName, Input.Reviewer } };
		return role;
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :To verify change role should not happen when clicking on cancel
	 *              in Edit pop up for any user
	 */
	@Test(description = "RPMXCON-52898", dataProvider = "role", enabled = true, groups = { "regression" })
	public void verifyChangeRoleNotHappenWhenCancel(String userName, String password, String roleName, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52898");
		baseClass.stepInfo(
				"To verify change role should not happen when clicking on cancel in Edit pop up for any user");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		softAssertion = new SoftAssert();

		// login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Login as a sa user :" + userName);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(roleName);
		userManage.applyFilter();

		// verify role not change
		baseClass.waitTime(4);
		driver.scrollingToBottomofAPage();
		int count = ((userManage.getAssgnPaginationCount().size()) - 2);
		for (int i = 0; i < count; i++) {
			Boolean status = userManage.getSelectUserToEdit(Input.projectName).isElementAvailable(5);
			if (status == true) {
				userManage.editFunctionality(Input.projectName);
				break;
			}
			 else {
				userManage.getUserListNextButton().isElementAvailable(5);
				userManage.getUserListNextButton().waitAndClick(5);
			}
		}
		
		String currentRole = baseClass.getCurrentDropdownValue(userManage.getSelctRole());
		softAssertion.assertEquals(role, currentRole);
		baseClass.passedStep("Popup window opened for " + role + "");
		userManage.getSelctRole().selectFromDropdown().selectByIndex(5);
		baseClass.waitForElement(userManage.getbellyBandMsg());
		String warningmsg = userManage.getbellyBandMsg().getText().trim();
		softAssertion.assertEquals(warningmsg,
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		baseClass.passedStep("warning message dispalyed as expect");
		baseClass.waitForElement(baseClass.getNOBtn());
		baseClass.getNOBtn().waitAndClick(10);
		baseClass.waitForElement(userManage.getPopUpCloseBtn());
		userManage.getPopUpCloseBtn().waitAndClick(10);

		// validating role can't be changed
		baseClass.waitForElement(userManage.getRoleName());
		String actual = userManage.getRoleName().getText().trim();
		softAssertion.assertEquals(actual, role);
		baseClass.passedStep("User role should not be changed");

		softAssertion.assertAll();
		baseClass.passedStep(
				"verified change role should not happen when clicking on cancel in Edit pop up for any user");
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Validate Project Name field value for a non-domain project in
	 *              edit mode by System Admin
	 */
    @Test(description = "RPMXCON-52962", enabled = true, groups = { "regression" })
	public void verifyNonDomainProjectNameModify() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52962");
		baseClass.stepInfo("Validate Project Name field value for a non-domain project in edit mode by System Admin");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();

		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// passing non-domain project name
		this.driver.getWebDriver().get(Input.url + "Project/Project");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projectPage.getSearchProjectName());
		projectPage.getSearchProjectName().SendKeys(Input.NonDomainProject);
		baseClass.waitForElement(projectPage.getProjectFilterButton());
		projectPage.getProjectFilterButton().waitAndClick(5);
		baseClass.waitTime(2);
		baseClass.waitForElement(projectPage.getProjectEdits());
		projectPage.getProjectEdits().waitAndClick(5);

		// verify edit project window displays
		baseClass.waitForElement(projectPage.getEditProjectWindow());
		boolean flag = projectPage.getEditProjectWindow().isElementAvailable(3);
		softAssertion.assertTrue(flag);

		// passing 260 character inside text box
		String charValue = baseClass.passingCharacterBasedOnSize(260);
		baseClass.waitForElement(projectPage.getProjectName());
		projectPage.getProjectName().SendKeys(charValue);
		baseClass.waitForElement(projectPage.getProjectName());
		String typedValue = projectPage.getProjectName().GetAttribute("value");
		int size = typedValue.length();
		if (size == 255) {
			baseClass.passedStep("More than 255 character not able to pass the value for projectname");
		} else {
			baseClass.failedStep("Values passing more that 255 character");
		}
		String specialCharacter = "NonDomain@#";
		baseClass.waitForElement(projectPage.getProjectName());
		projectPage.getProjectName().SendKeys(specialCharacter);
		baseClass.waitForElement(projectPage.getEditProjectWindow());
		projectPage.getEditProjectWindow().waitAndClick(5);
		baseClass.waitTime(3);
		boolean errorFlag = projectPage.getErrorMsgForProjectName().isElementAvailable(2);
		softAssertion.assertTrue(errorFlag);
		baseClass.stepInfo("Error message displaying for special character name");
		String output = baseClass.passingCharacterUsingCombination(255);
		baseClass.waitForElement(projectPage.getProjectName());
		projectPage.getProjectName().SendKeys(output);
		baseClass.waitTime(3);
		baseClass.waitForElement(projectPage.getButtonSaveProject());
		projectPage.getButtonSaveProject().waitAndClick(5);
		baseClass.waitTime(5);
		this.driver.getWebDriver().get(Input.url + "Project/Project");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projectPage.getSearchProjectName());
		projectPage.getSearchProjectName().SendKeys(output);
		baseClass.waitTime(3);
		baseClass.waitForElement(projectPage.getProjectFilterButton());
		projectPage.getProjectFilterButton().waitAndClick(10);
		baseClass.waitForElement(projectPage.getModifyValidateName(output));
		boolean modifyName = projectPage.getModifyValidateName(output).isElementAvailable(3);
		softAssertion.assertTrue(modifyName);
		baseClass.waitForElement(projectPage.getProjectEdits());
		projectPage.getProjectEdits().waitAndClick(5);
		baseClass.waitForElement(projectPage.getProjectName());
		projectPage.getProjectName().SendKeys(Input.NonDomainProject);
		baseClass.waitForElement(projectPage.getButtonSaveProject());
		projectPage.getButtonSaveProject().waitAndClick(5);

		softAssertion.assertAll();
		baseClass.passedStep("Validate Project Name field value for a non-domain project in edit mode by System Admin");
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify that notification count should be updated to reflect the
	 *              completed notifications
	 */
	@Test(description = "RPMXCON-53006", enabled = true, groups = { "regression" })
	public void verifyBullNotificationWhenProjectCreate() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53006");
		baseClass.stepInfo("Verify that notification count should be updated to reflect the completed notifications");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String newProject = "newProject" + Utility.dynamicNameAppender();
		String newProjectTwo = "newProject" + Utility.dynamicNameAppender();

		// login as
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// creating a new project
		String count = projectPage.getIntialBullCount().getText();
		System.out.println(count);
		projectPage.AddDomainProjectViaDaUser(newProject);
		projectPage.waitTillProjectCreated();
		baseClass.waitTime(500);
		baseClass.stepInfo("Creating first project");

		// validating the bull icon count for first project creation
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return baseClass.initialBgCount() == Integer.parseInt(count) + 1;
			}
		}), Input.wait120);
		final int bgCountAfter = baseClass.initialBgCount();
		softAssertion.assertEquals(bgCountAfter,Integer.parseInt(count) + 1);

		// creating new project again
		projectPage.AddDomainProjectViaDaUser(newProjectTwo);
		projectPage.waitTillProjectCreated();
		baseClass.waitTime(500);
		baseClass.stepInfo("Creating second project");

		// validating the second project creation counts
		int countTwo = data.getNotificationMessage(bgCountAfter, newProjectTwo);
		softAssertion.assertEquals(bgCountAfter + 1, countTwo);
		softAssertion.assertAll();
		baseClass.passedStep("Verify that notification count should be updated to reflect the completed notifications");
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify that - After Impersonation (SA to DA) notification
	 *              should be received upon completion of the project successfully
	 *              with DA User
	 */
	@Test(description = "RPMXCON-53012", enabled = true, groups = { "regression" })
	public void verifyBullNotificationImpFromSaToDa() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53012");
		baseClass.stepInfo(
				"Verify that - After Impersonation (SA to DA) notification should be received upon completion of the project successfully with DA User");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String newProject = "newProject" + Utility.dynamicNameAppender();

		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// impersonate from sa to da
		baseClass.impersonateSAtoDA();

		// creating a new project
		String count = projectPage.getIntialBullCount().getText();
		System.out.println(count);
		projectPage.AddDomainProjectViaDaUser(newProject);
		projectPage.waitTillProjectCreated();
		baseClass.waitTime(500);
		baseClass.stepInfo("Creating  project from da user");

		// validating the project creation counts with project name
		int countTwo = data.getNotificationMessage(Integer.parseInt(count), newProject);
		softAssertion.assertEquals(Integer.parseInt(count)+1, countTwo);
		System.out.println(Integer.parseInt(count)+1);
		System.out.println(countTwo);
		softAssertion.assertAll();
		baseClass.passedStep("Project  created successfully after impersonate from sa to da");
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify that when System Admin creates domain admin, domain
	 *              should be single select from Add User pop up
	 */
	@Test(description = "RPMXCON-53021", enabled = true, groups = { "regression" })
	public void verifyBullNotificationFromnMultipleDomain() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53021");
		baseClass.stepInfo(
				"Verify that when System Admin creates domain admin, domain should be single select from Add User pop up");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = "first" + Utility.dynamicNameAppender();
		String lastName = "last" + Utility.dynamicNameAppender();

		// login as
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a sa user :" + Input.da1userName);
		baseClass.selectdomain(Input.domainName);
		driver.waitForPageToBeReady();

		// adding the user
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getAddUserBtn());
		userManage.getAddUserBtn().Click();
		baseClass.waitForElement(userManage.getFirstName());
		userManage.getFirstName().SendKeys(firstName);
		userManage.getLastName().SendKeys(lastName);
		baseClass.waitForElement(userManage.getSelectRole());
		userManage.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.waitForElement(userManage.getSelectDomain());

		// verify domain dropdown displays
		boolean flag = userManage.getSelectDomain().isElementAvailable(4);
		softAssertion.assertTrue(flag);
		boolean singleFlag = userManage.getsingleSelectDomain().isElementAvailable(4);
		softAssertion.assertTrue(singleFlag);
		baseClass.stepInfo(
				"for domain selection dropdown displayed , when role selected as Domain Administrator single selected only showed");

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :To verify that the Assign user popup should present only
	 *              Project tab for Domain Admin
	 */
	@Test(description = "RPMXCON-53029", enabled = true, groups = { "regression" })
	public void verifyOnlyProjectTabOnDAUser() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53029");
		baseClass.stepInfo("To verify that the Assign user popup should present only Project tab for Domain Admin");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);

		// login as
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a sa user :" + Input.da1userName);
		baseClass.selectdomain(Input.domainName);
		driver.waitForPageToBeReady();

		// validating project tab only presence in da user
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getAssignUserButton());
		userManage.getAssignUserButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getProjectTab());
		boolean projectFlag = userManage.getProjectTab().isElementAvailable(3);
		softAssertion.assertTrue(projectFlag);
		baseClass.waitForElement(userManage.gettDomainBtn());
		boolean doaminFlag = userManage.gettDomainBtn().isElementAvailable(3);
		softAssertion.assertFalse(doaminFlag);
		baseClass.passedStep("For Da user only project tab get displayed in assign user");
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :To verify that the Assign user popup should present only
	 *              Project tab for Domain Admin
	 */
	@Test(description = "RPMXCON-53032", enabled = true, groups = { "regression" })
	public void verifyDaUsercanAssignProjects() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53032");
		baseClass.stepInfo("To verify that the Assign user popup should present only Project tab for Domain Admin");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);

		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab only presence in da user
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		boolean flag = userManage.ProjectSelectionForUser(Input.projectName, Input.pa1FullName, "Project Administrator",
				"", false, false);
		softAssertion.assertTrue(flag);
		baseClass.passedStep("System admin can assigned project successfully");
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :To verify that if System Admin Assign users in Project tab and
	 *              tries to navigate to the Domain tab without saving the changes,
	 *              it should display the confirmation message
	 */
	@Test(description = "RPMXCON-53035", enabled = true, groups = { "regression" })
	public void verifyConfirmMsgWithoutSave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53035");
		baseClass.stepInfo(
				"To verify that if System Admin Assign users in Project tab and tries to navigate to the Domain tab without saving the changes, "
						+ "it should display the confirmation message");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = "first" + Utility.dynamicNameAppender();
		String lastName = "last" + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String fullName = firstName + ' ' + lastName;
		System.out.println(fullName);
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a sa user :" + Input.da1userName);
		driver.waitForPageToBeReady();
		String count = projectPage.getIntialBullCount().getText();
		System.out.println(count);
		projectPage.AddDomainProjectViaDaUser(newProject);
		projectPage.waitTillProjectCreated();
		baseClass.waitTime(350);
		loginPage.logout();

		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab only presence in da user
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.createNewUser(firstName, lastName, role, emailId, Input.domainName,newProject);
		userManage.UnAssignUserToProject(newProject, role, fullName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getAssignUserButton());
		userManage.getAssignUserButton().waitAndClick(5);
		baseClass.waitForElement(userManage.getProjectTab());
		userManage.getProjectTab().waitAndClick(5);
		baseClass.waitForElement(userManage.getAssignUserProjectDrp_Dwn());
		userManage.getAssignUserProjectDrp_Dwn().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectDropProject(newProject));
		userManage.getSelectDropProject(newProject).waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getUnAssignedDomainUser());
		userManage.getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(fullName);
		baseClass.waitForElement(userManage.getDomainRole());
		userManage.getDomainRole().selectFromDropdown().selectByVisibleText(role);
		baseClass.waitForElement(userManage.getDomainUserRightArrow());
		userManage.getDomainUserRightArrow().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getConfirmMsg());
		String confirmMsg=userManage.getConfirmMsg().getText();
		softAssertion.assertEquals(confirmMsg, "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?");
		baseClass.waitForElement(baseClass.getYesBtn());
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean userPresent=userManage.UnAssignUserToProject(newProject, role, fullName);
		softAssertion.assertTrue(userPresent);
		baseClass.stepInfo("When user clicked Yes button get saved in userlists");
		
		// validation for No buttons
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getAssignUserButton());
		userManage.getAssignUserButton().waitAndClick(5);
		baseClass.waitForElement(userManage.getProjectTab());
		userManage.getProjectTab().waitAndClick(5);
		baseClass.waitForElement(userManage.getAssignUserProjectDrp_Dwn());
		userManage.getAssignUserProjectDrp_Dwn().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectDropProject(newProject));
		userManage.getSelectDropProject(newProject).waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getUnAssignedDomainUser());
		userManage.getUnAssignedDomainUser().selectFromDropdown().selectByVisibleText(fullName);
		baseClass.waitForElement(userManage.getDomainRole());
		userManage.getDomainRole().selectFromDropdown().selectByVisibleText(role);
		baseClass.waitForElement(userManage.getDomainUserRightArrow());
		userManage.getDomainUserRightArrow().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		String confirmMsgNo=userManage.getConfirmMsg().getText();
		softAssertion.assertEquals(confirmMsgNo, "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?");
		baseClass.waitForElement(baseClass.getNOBtn());
		baseClass.getNOBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(newProject);
		userManage.selectRoleInAssignUser(role);
		boolean projectNotPresent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertFalse(projectNotPresent);
		baseClass.passedStep("When user pressed no button , not get saved the edit values");
		softAssertion.assertAll();
		loginPage.logout();
	}

	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :To verify that if System Admin UnAssign users in Project tab and tries 
	 *               to navigate to the Domain tab without saving the changes, 
	 *               it should display the confirmation message
	 */
    @Test(description = "RPMXCON-53036", enabled = true, groups = { "regression" })
	public void verifyUnAssignConfirmMsgWithoutSave() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53036");
		baseClass.stepInfo(
				"To verify that if System Admin UnAssign users in Project tab and tries to navigate"
				+ " to the Domain tab without saving the changes, it should display the confirmation message");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String fullName = firstName + ' ' + lastName;
		System.out.println(fullName);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab for confirmation message
		baseClass.stepInfo("Validation for Yes button confirmation");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.createNewUser(firstName, lastName, role, emailId, Input.domainName,newProject);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(newProject);
		userManage.selectRoleInAssignUser(role);
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean projectNotPresent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		userManage.getCheckingAssignedUserSG(fullName).waitAndClick(5);
		baseClass.waitForElement(userManage.getLeftArrowForProject());
		userManage.getLeftArrowForProject().waitAndClick(10);
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getConfirmMsg());
		String confirmMsg=userManage.getConfirmMsg().getText();
		softAssertion.assertEquals(confirmMsg, "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?");
		baseClass.waitForElement(baseClass.getYesBtn());
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(newProject);
		userManage.selectRoleInAssignUser(role);
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean userAbsent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertFalse(userAbsent);
		baseClass.stepInfo("When user clicked Yes button its get removed from assigned user");
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		
		// validation for No buttons
		baseClass.stepInfo("Validation for no button confirmation");
		userManage.AssignUserToProject(newProject, role, fullName);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(newProject);
		userManage.selectRoleInAssignUser(role);
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean projectNotPresents=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		userManage.getCheckingAssignedUserSG(fullName).waitAndClick(5);
		baseClass.waitForElement(userManage.getLeftArrowForProject());
		userManage.getLeftArrowForProject().waitAndClick(10);
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		String confirmMsgNo=userManage.getConfirmMsg().getText();
		softAssertion.assertEquals(confirmMsgNo, "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?");
		baseClass.waitForElement(baseClass.getNOBtn());
		baseClass.getNOBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(newProject);
		userManage.selectRoleInAssignUser(role);
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean noButton=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertTrue(noButton);
		baseClass.passedStep("When user pressed no button , assigned user listed not ge removed from assigned list");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify that Project Admin user(s) should be listed 
	 *               under the non-domain project user list page
	 */
	@Test(description = "RPMXCON-53037", enabled = true, groups = { "regression" })
	public void verifyNonDomainProjectAccessForPAFromPAUser() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53037");
		baseClass.stepInfo(
				"Verify that Project Admin user(s) should be listed under "
				+ "the non-domain project user list page");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating the project access has how many user
		baseClass.stepInfo("Validation for Yes button confirmation");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(Input.NonDomainProject);
		List<WebElement> data=userManage.getAssignedUserListPA().FindWebElements();
		List<String> splitValue=new ArrayList<String>();
		String[] data1=null;
		for (WebElement webElement : data) {
			String assignUser= webElement.getText();
			System.out.println(assignUser);
			String[] split=assignUser.split("\\|\\|");
			data1=split[0].split(" ");
			splitValue.add(data1[0]);
			
		}
		System.out.println(splitValue);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		baseClass.stepInfo("Taking user list from sa user for non-domain project");
		loginPage.logout();
		
		// login as
		// verify from pa user , user name listed
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.pa1userName);
		baseClass.selectproject(Input.NonDomainProject);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.stepInfo("From pa user, taking user list for project admin");
		List<String> firstValue=new ArrayList<String>();
		baseClass.waitTime(2);
		driver.scrollingToBottomofAPage();
		String count=userManage.getPaginationLastNumber().getText().toString();	
		int foo = Integer.parseInt(count);
		for (int i = 0; i<foo; i++) {
			boolean status=userManage.getPAUserName(1).isElementAvailable(4);
			if (status == true) {
				baseClass.waitTime(3);
				List<WebElement> firstNames =userManage.getPAUserName(1).FindWebElements();
				for (WebElement webElement : firstNames) {
					String assignUser= webElement.getText();
					System.out.println(assignUser);
					String[] split=assignUser.split("\\|\\|");
					String[] nameSplit=split[0].split(" ");
					firstValue.add(nameSplit[0]);
					System.out.println(firstValue);
				}
					if (userManage.getUserPaginationNextButton().isElementAvailable(5)) {
						userManage.getUserPaginationNextButton().waitAndClick(5);
						
					}
				}
		
			}
		System.out.println(firstValue);
		softAssertion.assertEquals(splitValue.toString(), firstValue.toString());
		baseClass.passedStep("Who have Non-domain project access displayed in Pa user");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify that Project Admin user(s) should be listed under the domain project user list page
	 */
	@Test(description = "RPMXCON-53038", enabled = true, groups = { "regression" })
	public void verifyDomainProjectAccessForPAFromPAUser() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53038");
		baseClass.stepInfo(
				"Verify that Project Admin user(s) should be listed under the domain project user list page");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating the project access has how many user
		baseClass.stepInfo("Validation for Yes button confirmation");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(Input.projectName);
		baseClass.waitTime(3);
		List<WebElement> data=userManage.getAssignedUserListPA().FindWebElements();
		List<String> splitValue=new ArrayList<String>();
		String[] data1=null;
		for (WebElement webElement : data) {
			String assignUser= webElement.getText();
			System.out.println(assignUser);
			String[] split=assignUser.split("\\|\\|");
			data1=split[0].split(" ");
			splitValue.add(data1[0]);
		}
		System.out.println(splitValue);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		baseClass.stepInfo("Taking user list from sa user for domain project");
		loginPage.logout();
		
		// login as
		// verify from pa user , user name listed
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.pa1userName);
		baseClass.selectproject(Input.projectName);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.stepInfo("From pa user, taking user list for project admin");
		List<String> firstValue=new ArrayList<String>();
		
		baseClass.waitTime(4);
		driver.scrollingToBottomofAPage();
		String count=userManage.getPaginationLastNumber().getText().toString();	
		int foo = Integer.parseInt(count);
		for (int i = 0; i<foo; i++) {
			boolean status=userManage.getPAUserName(1).isElementAvailable(4);
			if (status == true) {
				baseClass.waitTime(3);
				List<WebElement> firstNames =userManage.getPAUserName(1).FindWebElements();
				for (WebElement webElement : firstNames) {
					String assignUser= webElement.getText();
					System.out.println(assignUser);
					String[] split=assignUser.split("\\|\\|");
					String[] nameSplit=split[0].split(" ");
					firstValue.add(nameSplit[0]);
					System.out.println(firstValue);
				}
					if (userManage.getUserPaginationNextButton().isElementAvailable(5)) {
						userManage.getUserPaginationNextButton().waitAndClick(5);
						
					}
				}
		
			}
		System.out.println(splitValue);
		System.out.println(firstValue);
		softAssertion.assertEquals(splitValue.toString().toLowerCase(), firstValue.toString().toLowerCase());
		baseClass.passedStep("Who have domain project access displayed in Pa user fot project admin");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify Unassigned users list on selection of domain when PA/RMU/Reviewer 
	 *               user is unassigned from non-domain project and saved
	 */
	@Test(description = "RPMXCON-53053", enabled = true, groups = { "regression" })
	public void verifyUnAssignuserListForNonDomain() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53053");
		baseClass.stepInfo(
				"Verify Unassigned users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from non-domain project and saved");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String fullName = firstName + ' ' + lastName;
		System.out.println(fullName);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab for confirmation message
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.createNewUser(firstName, lastName, role, emailId, Input.domainName,Input.NonDomainProject);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(Input.NonDomainProject);
		userManage.selectRoleInAssignUser(role);
		
		// validating assigned user list for non-domain project
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean projectNotPresent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertTrue(projectNotPresent);
		System.out.println(projectNotPresent);
		baseClass.stepInfo("When non-domain project selected, assigned user displayed in assigned userlist");
		userManage.getCheckingAssignedUserSG(fullName).waitAndClick(5);
		baseClass.waitForElement(userManage.getLeftArrowForProject());
		userManage.getLeftArrowForProject().waitAndClick(10);
		userManage.getsavedomainuser().waitAndClick(10);
		
		// again validating assign user from domain tab
		baseClass.stepInfo("validating from domain tab");
		userManage.openAssignUser();
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flag=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flag);
		
		// selecting domain from dropdown
		baseClass.stepInfo("validating from domain tab after selecting dropdown domain name");
		baseClass.waitForElement(userManage.getSelectDomainname());
		userManage.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flagOne=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flagOne);
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify Unassinged users list on selection of domain when PA/RMU/Reviewer 
	 *               user is unassigned from domain project and saved
	 */
	@Test(description = "RPMXCON-53054", enabled = true, groups = { "regression" })
	public void verifyUnAssignuserListForDomain() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53054");
		baseClass.stepInfo(
				"Verify Unassinged users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from domain project and saved");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String fullName = firstName + ' ' + lastName;
		System.out.println(fullName);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab for confirmation message
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.createNewUser(firstName, lastName, role, emailId, Input.domainName,Input.projectName);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(Input.projectName);
		userManage.selectRoleInAssignUser(role);
		
		// validating assigned user list for non-domain project
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean projectNotPresent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertTrue(projectNotPresent);
		System.out.println(projectNotPresent);
		baseClass.stepInfo("When domain project selected, assigned user displayed in assigned userlist");
		userManage.getCheckingAssignedUserSG(fullName).waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getLeftArrowForProject());
		userManage.getLeftArrowForProject().waitAndClick(10);
		userManage.getsavedomainuser().waitAndClick(10);
		
		// again validating assign user from domain tab
		baseClass.stepInfo("validating from domain tab");
		userManage.openAssignUser();
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flag=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flag);
		
		// selecting domain from dropdown
		baseClass.stepInfo("validating from domain tab after selecting dropdown domain name");
		baseClass.waitForElement(userManage.getSelectDomainname());
		userManage.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flagOne=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flagOne);
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify Unassinged users list on selection of domain when PA/RMU/Reviewer 
	 *               user is unassigned from non-domain project moved to Domains tab
	 */
	@Test(description = "RPMXCON-53055", enabled = true, groups = { "regression" })
	public void verifyUnAssignuserListForNonDomainConfirmMsg() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53055");
		baseClass.stepInfo(
				"Verify Unassinged users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from non-domain project moved to Domains tab");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String fullName = firstName + ' ' + lastName;
		System.out.println(fullName);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab for confirmation message
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.createNewUser(firstName, lastName, role, emailId, Input.domainName,Input.NonDomainProject);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(Input.NonDomainProject);
		userManage.selectRoleInAssignUser(role);
		
		// validating assigned user list for non-domain project
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean projectNotPresent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertTrue(projectNotPresent);
		System.out.println(projectNotPresent);
		baseClass.stepInfo("When non-domain project selected, assigned user displayed in assigned userlist");
		userManage.getCheckingAssignedUserSG(fullName).waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getLeftArrowForProject());
		userManage.getLeftArrowForProject().waitAndClick(10);
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getConfirmMsg());
		String confirmMsg=userManage.getConfirmMsg().getText();
		softAssertion.assertEquals(confirmMsg, "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?");
		baseClass.waitForElement(baseClass.getYesBtn());
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		
		// again validating assign user from domain tab
		baseClass.stepInfo("validating from domain tab");
		userManage.openAssignUser();
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flag=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flag);
		
		// selecting domain from dropdown
		baseClass.stepInfo("validating from domain tab after selecting dropdown domain name");
		baseClass.waitForElement(userManage.getSelectDomainname());
		userManage.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flagOne=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flagOne);
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @throws Exception
	 * @Author :Baskar date: NA Modified date:NA Modified by:
	 * @Description :Verify Unassinged users list on selection of domain when PA/RMU/Reviewer 
	 *               user is unassigned from domain project moved to Domains tab
	 */
	@Test(description = "RPMXCON-53056", enabled = true, groups = { "regression" })
	public void verifyUnAssignuserListForDomainConfirmMsg() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53056");
		baseClass.stepInfo(
				"Verify Unassinged users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from domain project moved to Domains tab");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		doamain = new DomainDashboard(driver);
		projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		data = new DataSets(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String fullName = firstName + ' ' + lastName;
		System.out.println(fullName);
		
		// login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		driver.waitForPageToBeReady();

		// validating project tab for confirmation message
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.createNewUser(firstName, lastName, role, emailId, Input.domainName,Input.projectName);
		userManage.openAssignUser();
		userManage.goToProjectTabInAssignUser();
		userManage.selectProjectInAssignUser(Input.projectName);
		userManage.selectRoleInAssignUser(role);
		
		// validating assigned user list for non-domain project
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(fullName));
		boolean projectNotPresent=userManage.getCheckingAssignedUserSG(fullName).isElementAvailable(3);
		softAssertion.assertTrue(projectNotPresent);
		System.out.println(projectNotPresent);
		baseClass.stepInfo("When domain project selected, assigned user displayed in assigned userlist");
		userManage.getCheckingAssignedUserSG(fullName).waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getLeftArrowForProject());
		userManage.getLeftArrowForProject().waitAndClick(10);
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		
		// validation for yes button
		baseClass.stepInfo("Validation for Yes button confirmation");
		baseClass.waitForElement(userManage.getConfirmMsg());
		String confirmMsg=userManage.getConfirmMsg().getText();
		softAssertion.assertEquals(confirmMsg, "You have not saved your edits. If you do not save, you will lose your changes. Do you want to save your changes?");
		baseClass.waitForElement(baseClass.getYesBtn());
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getDomainUserCancelButton());
		userManage.getDomainUserCancelButton().waitAndClick(5);
		
		// again validating assign user from domain tab
		baseClass.stepInfo("validating from domain tab");
		userManage.openAssignUser();
		baseClass.waitForElement(userManage.gettDomainBtn());
		userManage.gettDomainBtn().waitAndClick(5);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flag=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flag);
		
		// selecting domain from dropdown
		baseClass.stepInfo("validating from domain tab after selecting dropdown domain name");
		baseClass.waitForElement(userManage.getSelectDomainname());
		userManage.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
		boolean flagOne=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
		softAssertion.assertTrue(flagOne);
		softAssertion.assertAll();
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
