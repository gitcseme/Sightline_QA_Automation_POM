package sightline.smallComponents;

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
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression_04 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard dash;
	ProjectPage project;
	ClientsPage client;
	UserManagement user;
	
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
		loginPage = new LoginPage(driver);
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify after domain user impersonated down as a project admin into the "clicked project",he should be able to impersonate down to Reviewer
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53125",enabled = true, groups = {"regression" })
	public void verifyImpersonateReviewer() throws InterruptedException  {
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53125");
		base.stepInfo("Verify after domain user impersonated down as a project admin into the \"clicked project\",he should be able to impersonate down to Reviewer");
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		dash.goToFirstProject();
		base.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		if(base.text("Assignments within Assignment Group >>").isElementAvailable(8)) {
			base.passedStep("User should be successfully  impersonated down to Reviewer role");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verified after domain user impersonated down as a project admin into the \"clicked project\",he should be able to impersonate down to Reviewer");
		loginPage.logout();
		
	}
	

	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin 
	 * [assigned to single domain/non-domain project] user to Project Admin
	 */
	@Test(description = "RPMXCON-53061",enabled = true, groups = {"regression" })
	public void verifyUserRightsDomainToProjectAdmin() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53061");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin [assigned to single domain/non-domain project] user to Project Admin");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.da1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Project :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		base.getYesBtn().waitAndClick(5);
		
		if(base.text("Project :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 1);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin [assigned to single domain/non-domain project] user to Project Admin");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin
	 * [assigned to single domain/non-domain project] user to RMU
	 */
	@Test(description = "RPMXCON-53062",enabled = true, groups = {"regression" })
	public void verifyUserRightsDomainToRMU() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53062");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to RMU");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.da1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ReviewManager);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ReviewManager);
		base.getYesBtn().waitAndClick(5);
		
		if(base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 2);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab "
				+ "when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to RMU");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to Reviewer
	 */
	@Test(description = "RPMXCON-53063",enabled = true, groups = {"regression" })
	public void verifyUserRightsDomainToReviewer() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53063");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to Reviewer");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.da1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.Reviewer);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.Reviewer);
		base.getYesBtn().waitAndClick(5);
		
		if(base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 9);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to Reviewer");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Project Admin[assigned to single domain/non-domain project] user to Domain Admin
	 */
	@Test(description = "RPMXCON-53064",enabled = true, groups = {"regression" })
	public void verifyUserRightsPaToDa() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53064");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Project Admin[assigned to single domain/non-domain project] user to Domain Admin");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.pa1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(base.text("Project :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 1);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getYesBtn().waitAndClick(5);
		
		if(!base.text("Project :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab when Sys Admin changes role of Project Admin[assigned to single domain/non-domain project] user to Domain Admin");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify after domain user impersonated down as a project admin into the "clicked project",all existing menu items should display as usual
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53126",enabled = true, groups = {"regression" })
	public void verifyMenuDisplayInPA() throws InterruptedException  {
		
		dash = new DomainDashboard(driver);
		SessionSearch search = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53126");
		base.stepInfo("Verify after domain user impersonated down as a project admin into the \"clicked project\",all existing menu items should display as usual");
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		//go to project
		dash.filterProject(Input.projectName);
		dash.goToFirstProject();
		
		//verify menu
		softAssertion.assertEquals((boolean)base.text("Manage").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Search").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Reports").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Productions").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Batch Print").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Categorize").isElementAvailable(3), true);
		base.passedStep("Menu links should be visible as:-   Manage Search Reports production Batch Print Categorize");
		
		//verify search result
		search.navigateToSessionSearchPageURL();
		softAssertion.assertNotEquals(search.basicContentSearch(Input.testData1), 0);
		base.passedStep("Search results is display properly.");
		
		softAssertion.assertAll();
		base.passedStep("Verified after domain user impersonated down as a project admin into the \"clicked project\",all existing menu items should display as usual");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/02/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that error message should be displayed when domain is not selected while creating domain user by System Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52789",enabled = true, groups = {"regression" })
	public void verifyErrorMsgSelectDomain() throws InterruptedException  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-52789");
		base.stepInfo("Verify that error message should be displayed when domain is not selected while creating domain user by System Admin");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		
		//add new user popup open
		user.openAddNewUserPopUp();
		softAssertion.assertTrue((boolean)user.getFirstName().isDisplayed());
		base.passedStep("Add New User pop up was open");
		
		//select add details
		user.getFirstName().SendKeys(Input.randomText);
		user.getLastName().SendKeys(Input.randomText);
		user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		user.getEmail().SendKeys(Input.da1userName);
		user.getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		user.getSave().waitAndClick(5);
		
		//verify error message
		softAssertion.assertTrue((boolean)base.text("You must specify a domain").isDisplayed());
		base.passedStep("Error message was displayed to select domain");
		
		softAssertion.assertAll();
		base.passedStep("Verified that error message should be displayed when domain is not selected while creating domain user by System Admin");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/02/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that unassigned User List, should contain all other users in the system which are not associated to selected Domain
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53019",enabled = true, groups = {"regression" })
	public void verifyUnAssignedUser() throws InterruptedException  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53019");
		base.stepInfo("To verify that unassigned User List, should contain all other users in the system which are not associated to selected Domain");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//open assing tab
		user.openAssignUser();
		user.getSelectDomainname().selectFromDropdown().selectByVisibleText(Input.domainName);
		driver.waitForPageToBeReady();
		
		//get unassigned userNmae
		String[] username = user.getNthUnAssignedUser(1).getText().trim().split(" ");
		user.getPopUpCloseBtn().waitAndClick(5);
		
		//verify that user not assigned in that domain
		user.filterByName(username[0]);
		softAssertion.assertFalse((boolean)base.text(Input.domainName).isDisplayed());
		base.passedStep("List of users displays the users who are not assigned with any role to Selected Domain");
		
		softAssertion.assertAll();
		base.passedStep("veriiedy that unassigned User List, should contain all other users in the system which are not associated to selected Domain");
		loginPage.logout();
		
	}
	/**
	 * @Author :Aathith 
	 * date: 08/04/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that manage user page should list each domain-user combination in a separate row
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53022",enabled = true, groups = {"regression" })
	public void verifyDomainUserdInSeperateRow() throws InterruptedException  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53022");
		base.stepInfo("Verify that manage user page should list each domain-user combination in a separate row");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		user.filterByName(Input.da1userName);
		List<String> domainName = user.getTableCoumnValue("DOMAIN");
		if(domainName.size()>1) {
		softAssertion.assertNotEquals(domainName.get(0), domainName.get(1));
		base.passedStep("Manage user page was list each domain-user combination in a separate row");
		}else {
			base.failedStep("please use multiDomain DA credential");
		}
		
		softAssertion.assertAll();
		base.passedStep("Veriiedy that manage user page should list each domain-user combination in a separate row");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/04/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that System Admin can Unassigned users from the selected Domain
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53026",enabled = true, groups = {"regression" })
	public void verifyUnAssigenuserSelectDomain() throws InterruptedException  {
		
		user = new UserManagement(driver);
		client = new ClientsPage(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53026");
		base.stepInfo("To verify that System Admin can Unassigned users from the selected Domain");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//get domain name
		client.navigateToClientPage();
		driver.waitForPageToBeReady();
		client.filterClientByType("Domain");
		String DomainName = client.getClientTableValue(1, base.getIndex(client.getClientTableHeaders(), "NAME")).getText().trim();
		
		user.navigateToUsersPAge();
		user.filterByName(Input.da1userName);
		String userName = user.getfirstUserName();
		boolean flag = base.text(DomainName).isElementAvailable(2);
		if(!flag) {
			user.AssignUserToDomain(DomainName, userName);
		}
		
		//unAssign user
		user.unAssignUserToDomain(DomainName, userName);
		
		//verify
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		softAssertion.assertFalse((boolean)base.getSelectProject(DomainName).isElementAvailable(1));
		base.passedStep("It was displayed the selected Domain in that dropdown if user is assigned for multiple domains. If user is assigned only for one domain then he should not be login to application");
		
		softAssertion.assertAll();
		base.passedStep("verified that System Admin can Unassigned users from the selected Domain");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/04/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that Client Name and Domain ID should not accepts any other special character except Dash,Underscore and space
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52772",enabled = true, groups = {"regression" })
	public void VerifyClientAndDomainIDSpecialChar() throws InterruptedException  {
		
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-52772");
		base.stepInfo("To verify that Client Name and Domain ID should not accepts any other special character except Dash,Underscore and space");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String DomainNameId = "!@#$%^&*";
		String errorMsg = "You cannot specify any special characters in the field value";
		
		//add special character
		client = new ClientsPage(driver);
		client.addNewClientWithDomainType();
		client.getEntityName().SendKeys(DomainNameId);
		client.getDomainID().SendKeys(DomainNameId);
		client.getSaveBtn().waitAndClick(3);
		base.stepInfo("special character ade in the filed of domain name and domain id");
		
		softAssertion.assertEquals(client.getEntityNameErrorMsg().getText().trim(), errorMsg);
		softAssertion.assertEquals(client.getEntityIdErrorMsg().getText().trim(), errorMsg);
		base.passedStep("Inline message should be displayed as 'Special characters are not allowed' for Name And DomainId");
		
		softAssertion.assertAll();
		base.passedStep("To verify that Client Name and Domain ID should not accepts any other special character except Dash,Underscore and space");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/04/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify error message should be displayed when system admin do not select domain when creating domain user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52775",enabled = true, groups = {"regression" })
	public void verifyErrorMsgDoNotSelectDomain() throws InterruptedException  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-52775");
		base.stepInfo("Verify error message should be displayed when system admin do not select domain when creating domain user");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		
		//add new user popup open
		user.openAddNewUserPopUp();
		softAssertion.assertTrue((boolean)user.getFirstName().isDisplayed());
		base.passedStep("Add New User pop up was open");
		
		//select add details
		user.getFirstName().SendKeys(Input.randomText);
		user.getLastName().SendKeys(Input.randomText);
		user.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		softAssertion.assertTrue((boolean)base.text(Input.DomainAdministrator).isDisplayed());
		base.passedStep("Domain Admin role was displayed in the Role drop down   ");
		
		user.getEmail().SendKeys(Input.da1userName);
		user.getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		user.getSave().waitAndClick(5);
		
		//verify error message
		softAssertion.assertTrue((boolean)base.text("You must specify a domain").isDisplayed());
		base.passedStep("Error message was displayed to select domain");
		
		softAssertion.assertAll();
		base.passedStep("Verify error message should be displayed when system admin do not select domain when creating domain user");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/04/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when system admin adds existing user as domain user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52776",enabled = true, groups = {"regression" })
	public void verifySysadminAddExitingUser() throws InterruptedException  {
		
		user = new UserManagement(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52776");
		base.stepInfo("Verify when system admin adds existing user as domain user");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String email = Utility.randomCharacterAppender(6)+"@consilio.com";
		
		//create a user
		user.createNewUser(Input.randomText, Input.randomText, Input.Reviewer, email, Input.domainName, Input.projectName);
		
		//parem for all user
		user.createNewUser(Input.randomText, Input.randomText, Input.DomainAdministrator, email, Input.domainName, Input.projectName);
		user.createNewUser(Input.randomText, Input.randomText, Input.ProjectAdministrator, email, Input.domainName, Input.projectName01);
		user.createNewUser(Input.randomText, Input.randomText, Input.ReviewManager, email, Input.domainName, Input.projectName02);
		user.createNewUser(Input.randomText, Input.randomText, Input.Reviewer, email, Input.domainName, Input.ingestionProjectName);
		base.passedStep("User should be added successfully and success message should be displayed  for All the roles ");
		
		//remove added user
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.deleteUser();
		
		base.passedStep("Verified when system admin adds existing user as domain user");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/04/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that when domain user is created entry will be shown in Pending Activation list
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52783",enabled = true, groups = {"regression" })
	public void verifyDomainAdminEntryPendingActivation() throws InterruptedException  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-52783");
		base.stepInfo("Verify that when domain user is created entry will be shown in Pending Activation list");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String email = Utility.randomCharacterAppender(6)+"@consilio.com";
		
		//create a user
		user.createNewUser(Input.randomText, Input.randomText, Input.DomainAdministrator, email, Input.domainName, Input.projectName);
		
		//verification
		user.openUsersNotYetLoggedInPopUp();
		softAssertion.assertTrue((boolean)base.text("First Name").isElementAvailable(3));
		softAssertion.assertTrue((boolean)base.text("Last Name").isElementAvailable(3));
		softAssertion.assertTrue((boolean)base.text("Email Address").isElementAvailable(3));
		softAssertion.assertTrue((boolean)base.text("Resend").isElementAvailable(3));
		base.passedStep("When domain user is created entry was displayed as follows->First Name,Last Name, Email Address, Resend button");
		
		//delete added user
		user.getPopUpCloseBtn().waitAndClick(5);
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.deleteUser();
		
		softAssertion.assertAll();
		base.passedStep("Verified that when domain user is created entry will be shown in Pending Activation list");
		loginPage.logout();
		
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR DomainManagement EXECUTED******");

	}

}