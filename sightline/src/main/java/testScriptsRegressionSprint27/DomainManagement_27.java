package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.support.ui.Select;
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
import pageFactory.ClientsPage;
import pageFactory.Dashboard;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard domainDashboard;

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
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		domainDashboard = new DomainDashboard(driver);

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

			// delete the created user
			user.filterTodayCreatedUser();
			user.filterByName(MailID);
			user.deleteUser();
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
		int n = baseClass.getIndex(project.getProjectTableHeader(), "NAME");
		String Project = project.getColumValue(n).getText().trim();
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
	@Test(description = "RPMXCON-52990", groups = { "regression" })
	public void verifyDAImpersonatePA() throws InterruptedException {
		ProjectPage project = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);
		
		String ClientName = "" + Utility.dynamicNameAppender();
		String shrtType = "" + Utility.randomCharacterAppender(4); 
		
		baseClass.stepInfo("RPMXCON-52990");
		baseClass.stepInfo("To verify that Domain Admin user impersonate as Project Admin in different Domain successfully");
		loginPage.loginToSightLine(Input.sa1userName,  Input.sa1password);	
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient(ClientName, shrtType, "Domain");
		user.navigateToUsersPAge();
		user.Assignusertodomain(ClientName, Input.da1FullName);
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

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
		System.out.println("******TEST CASES EXECUTED******");

	}
}
