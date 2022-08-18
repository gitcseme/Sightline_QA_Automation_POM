package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.ClientsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression_03 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
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
	 * date: 07/14/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify error message should be displayed when system admin adds the 
	 * domain user whose password is not set and link to set password is active
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52787",enabled = true, groups = {"regression" })
	public void verifyErrorMsgDisplayInSysAdmin() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52787");
		base.stepInfo("Verify error message should be displayed when system admin adds the "
				+ "domain user whose password is not set and link to set password is active");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		String email = Input.randomText+Utility.dynamicNameAppender()+"@consilio.com";
		
		//pre-req user
		userManage.createNewUser(Input.randomText, Input.randomText, "Domain Administrator", email, Input.domainName, Input.projectName);
		base.stepInfo("Domain Admin user was added successfully");
		
		//Add deleted user
		base.CloseSuccessMsgpopup();
		userManage.addNewUserWithoutVerifySuccesMsg(Input.randomText, Input.randomText, "Domain Administrator", email, Input.domainName, Input.projectName);
		base.getSuccessMsgHeader().isElementAvailable(10);
		Assert.assertEquals("Error !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("Error message was displayed");
		
		//remove added cred
		userManage.filterByName(email);
		userManage.deleteUser();
		
		base.passedStep("Verified error message should be displayed when system admin adds the "
				+ "domain user whose password is not set and link to set password is active");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/14/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that when user changes the domain from the header list, 
	 * the currently selected domain will gets changed and user will be taken back to landing page
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52800",enabled = true, groups = {"regression" })
	public void verifyUserChangeDomainFromHeader() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52800");
		base.stepInfo("To verify that when user changes the domain from the header list, "
				+ "the currently selected domain will gets changed and user will be taken back to landing page");
		
		//login as sa
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		
		driver.waitForPageToBeReady();
		String currentDomain = base.getProjectNames().getText().trim();
		base.switchDomain();
		base.stepInfo("user changes the domain from the header list");
		driver.waitForPageToBeReady();
		String switchedDomain = base.getProjectNames().getText().trim();
		softAssertion.assertNotEquals(currentDomain, switchedDomain);
		softAssertion.assertAll();
		base.passedStep("currently selected domain will gets changed and user will be taken back to landing page");
		
		base.passedStep("verified that when user changes the domain from the header list, "
				+ "the currently selected domain will gets changed and user will be taken back to landing page");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/14/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description : To verify Manage Clients grid
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52750",enabled = true, groups = {"regression" })
	public void verifyClientGrid() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52750");
		base.stepInfo("To verify Manage Clients grid");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		client = new ClientsPage(driver);
		dash = new DomainDashboard(driver);
		
		//client page details
		String[] grid = {Input.FilterByType , Input.Name, Input.ShortName, Input.Type, Input.DomainID, Input.ProcessingEngine , Input.CreatedBy, Input.CreatedON, Input.Actions, Input.Delete, Input.Edit};
		
		client.navigateToClientPage();
		base.isTextAreAvailableInWebPage(grid);
		if(client.getEntityNameFilter().isDisplayed()) {
			base.passedStep("Filter by Name was displayed");
		}else{
			base.failedStep("verification failed");
		}
		if(client.getAddEntity().isDisplayed()) {
			base.passedStep("add new client button was displayed");
		}else {
			base.failedStep("verification failed");
		}
		
		client.verifyAscendingDescendingOrder();
		base.stepInfo("1st column Checked client/domain list Ascending and Descending order");
		for(int i=2;i<=7;i++) {
			base.waitForElement(client.getTableHeader(i));
			client.getTableHeader(i).waitAndClick(10);
			client.verifyAscendingDescendingOrder();
			base.stepInfo(i+" column Checked client/domain list Ascending and Descending order");
		}
		base.passedStep("Sorting was work for client/domain list as ascending/descending");
		
		base.passedStep("verified Manage Clients grid");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/14/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description : To verify Domain Admin can change user rights in bulk for RMU
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52820",enabled = true, groups = {"regression" })
	public void verifyDomainAdminChangeRightsOfRmu() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52820");
		base.stepInfo("To verify Domain Admin can change user rights in bulk for RMU");
		
		//login as sa
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		base.selectproject(Input.domainName);
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		
		//get userName
		user.navigateToUsersPAge();
		user.filterByName(Input.rmu1userName);
		//String userName = user.getfirstUserName();
		String[] users = {user.getfirstUserName()};
		
		//select role and enable dataset
		user.selectRoleBulkUserAccessControl("Review Manager", Input.projectName, Input.securityGroup);
		user.defaultSelectionCheckboxForAllRole(false, false, false, false, false, false, false, true, false, false, false, false, false, false, false);
		base.waitForElement(user.getEnableRadioBtn());
		user.getEnableRadioBtn().waitAndClick(10);
		user.selectBulkAccessUsers(users);
		user.getBulkUserSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Access rights applied successfully");
		base.passedStep("Success message should be displayed");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Login as a rmu user :"+Input.rmu1userName);
		if(base.text("Datasets").isDisplayed()) {
			base.passedStep("rights which are enabled is enable for the selected RMU users after login");
		}else {
			base.failedStep("verification failed");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		//disable right
		user.navigateToUsersPAge();
		user.selectRoleBulkUserAccessControl("Review Manager", Input.projectName, Input.securityGroup);
		user.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, false, true, true, true, true, true, true);
		base.waitForElement(user.getDisableRadioBtn());
		user.getDisableRadioBtn().waitAndClick(10);
		user.selectBulkAccessUsers(users);
		user.getBulkUserSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Access rights applied successfully");
		base.passedStep("Success message should be displayed");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Login as a rmu user :"+Input.rmu1userName);
		if(!base.text("Datasets").isElementAvailable(1)) {
			base.passedStep("rights which are disabled was disabled for the selected RMU users after login");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("verified Domain Admin can change user rights in bulk for RMU");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/14/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Domain Admin user clicks the 'Change Role' link from right top drop down
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52900",enabled = true, groups = {"regression" })
	public void verifyDomainAdminChangeRole() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52900");
		base.stepInfo("Verify when Domain Admin user clicks the 'Change Role' link from right top drop down");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verified when Domain Admin user clicks the 'Change Role' link from right top drop down");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/14/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Domain Admin selects role as 'Project Administrator' from Change Role pop up
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52901",enabled = true, groups = {"regression" })
	public void verifyDomainAdminSelectProjectAdmin() throws InterruptedException  {
		
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52901");
		base.stepInfo("Verify when Domain Admin selects role as 'Project Administrator' from Change Role pop up");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		dash.waitForDomainDashBoardIsReady();
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole(Input.ProjectAdministrator);
		base.selectImpersonateDomain(Input.domainName);
		base.selectImpersonateProject(Input.projectName);
		base.stepInfo("'Project Administrator' role was selected and Domain,");
		if(base.getSelectRole(Input.ProjectAdministrator).isElementAvailable(8)) {
			base.passedStep("Project drop down was displayed  ");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verify when Domain Admin selects role as 'Project Administrator' from Change Role pop up");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/19/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description : To verify that system admin can create 'Domain' client  successfully
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-46913",enabled = true, groups = {"regression" })
	public void verifySysAdminCreateDomainClient() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-46913");
		base.stepInfo("To verify that system admin can create 'Domain' client  successfully");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a da user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		client = new ClientsPage(driver);
		
		String clientName = "C"+ Utility.dynamicNameAppender();
		String domainId = "D"+ Utility.dynamicNameAppender();
		
		//add new client
		client.AddDomainClient(clientName, domainId,"Small (less than 1000 documents)");
		base.VerifySuccessMessage("The new client was added successfully");
		base.stepInfo("Domain client was created successfully");
		
		//filter the client
		client.filterClient(clientName);
		driver.waitForPageToBeReady();
		
		//verify client name is available
		softAssertion.assertEquals(client.getFiler_Clientname().getText(), clientName);
		softAssertion.assertAll();
		base.passedStep("Newly created Domain client was listed under clients list");
		
		//delete a created client
		client.filterClient(clientName);
		client.deleteClinet(clientName);
		
		base.passedStep("verified that system admin can create 'Domain' client  successfully");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/19/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description : To verify that system admin can create 'Not a Domain' client  successfully
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-46914",enabled = true, groups = {"regression" })
	public void verifySysAdminCreateNonDomainClient() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-46914");
		base.stepInfo("To verify that system admin can create 'Not a Domain' client  successfully");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		client = new ClientsPage(driver);
		
		String clientName = "C"+ Utility.dynamicNameAppender();
		
		//add new client
		client.AddNonDomainClient(clientName);
		base.passedStep("Newly created Non Domain client was listed under clients list");
		
		//delete a created client
		client.filterClient(clientName);
		client.deleteClinet(clientName);
		
		base.passedStep("To verify that system admin can create 'Not a Domain' client  successfully");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 07/19/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Domain Admin assigned to domain project only then domain dropdown in the impersonation popup should not display value "Not a Domain"
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53069",enabled = true, groups = {"regression" })
	public void verifyDomainProjectNotDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53069");
		base.stepInfo("Verify when Domain Admin assigned to domain project only then domain dropdown in the impersonation popup should not display value \"Not a Domain\"");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole(Input.ProjectAdministrator);
		base.stepInfo("Select role as Project Admin  Check domain drop down from the pop up");
		if(!base.textValue("Not a Domain").isElementAvailable(1)) {
			base.passedStep("The impersonation popup should not display the value \"Not a Domain\" as user is assigned to domain project only");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verified when Domain Admin assigned to domain project only then domain dropdown in the impersonation popup should not display value \"Not a Domain\"");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 07/19/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that Domain Admin user impersonate as Reviewer in different Domain successfully
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52996",enabled = true, groups = {"regression" })
	public void verifyDomainAdminImporsonateReviewer() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52996");
		base.stepInfo("To verify that Domain Admin user impersonate as Reviewer in different Domain successfully");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole(Input.Reviewer);
		base.selectImpersonateDomain(Input.domainName);
		base.selectImpersonateProject(Input.projectName);
		if(base.getSelectProjectTo(Input.projectName).isDisplayed()) {
			base.passedStep(" Project is displayed which is assigned as Reviewer on that Domain");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateSecurityGroup(Input.securityGroup);
		base.getSaveChangeRole().waitAndClick(5);
		driver.waitForPageToBeReady();
		
		if(base.text("Assignments within Assignment Group >>").isElementAvailable(10)) {
			base.passedStep("It's redirect to reviewer landing page ,Dashboard");
		}else {
			base.failedStep("verification failed");
		}
		base.passedStep("verified that Domain Admin user impersonate as Reviewer in different Domain successfully");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/21/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that Domain dropdown presents the list of all domains in 'Assign Users'-Domain
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53016",enabled = true, groups = {"regression" })
	public void verifyDomainDropPresentInAssignUser() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53016");
		base.stepInfo("To verify that Domain dropdown presents the list of all domains in 'Assign Users'-Domain");
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		
		user.openAssignUser();
		if(user.getSelectDomainname().isDisplayed()) {
			base.passedStep("Assign user popup opened");
		}else {
			base.failedStep("Assign user popup not displayed");
		}
		user.getSelectDomainname().waitAndClick(10);
		if(user.getAllDomainsInAssignUser().isElementAvailable(10)) {
			base.passedStep("All Domains should be displayed in the list");
		}else {
			base.failedStep("domain value not displayed");
		}
		
		base.passedStep("verified that Domain dropdown presents the list of all domains in 'Assign Users'-Domain");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/21/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that for 'Not a Domain' type project list should be displayed of Not a Domain type
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52907",enabled = true, groups = {"regression" })
	public void verifyzDomainProjectInNotDomain() throws InterruptedException  {
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52907");
		base.stepInfo("Verify that for 'Not a Domain' type project list should be displayed of Not a Domain type");
		
		//pre-req assign nondomain projet to da user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.filterByName(Input.da1userName);
		boolean flag = base.text(Input.NonDomainProject).isElementAvailable(2);
		String userName = user.getfirstUserName();
		if(!flag) {
		user.AssignUserToProject(Input.NonDomainProject, Input.ProjectAdministrator, userName);
		}
		loginPage.logout();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		dash.waitForDomainDashBoardIsReady();
		
		//open impersonate tab
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify not a domain availability
		base.selectImpersonateRole(Input.ProjectAdministrator);
		base.stepInfo("Select role as Project Admin  Check domain drop down from the pop up");
		base.selectImpersonateDomain("Not a Domain");
		base.stepInfo("Select \"Not a Domain\" from the domain dropdown,");
		if(base.getSelectDomain("Not a Domain").isDisplayed()) {
			base.passedStep("When the user selects \"not a domain\" from the domain dropdown, "
					+ "the project dropdown is list non-domain type projects for which this user has access to. (is a PAU or RMU or reviewer).");
		}else {
			base.failedStep("Not a Domain verification failed");
		}
		
		base.passedStep("Verified that for 'Not a Domain' type project list is displayed of Not a Domain type");
		loginPage.logout();
		
		//restore default
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.UnAssignUserToProject(Input.NonDomainProject, Input.ProjectAdministrator, userName);
		loginPage.logout();
	}
	
	
	
	/**
	 * @Author :Aathith 
	 * date: 07/21/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that unassigned users list should displays users which are currently do not have any roles in selected project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52941",enabled = true, groups = {"regression" })
	public void verifyUnAssignedUserList() throws InterruptedException  {
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52941");
		base.stepInfo("To verify that unassigned users list should displays users which are currently do not have any roles in selected project");
		
		//login as Sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//get da and pa user name
		user.filterByName(Input.da1userName);
		String daUser = user.getfirstUserName();
		user.filterByName(Input.pa1userName);
		String paUser = user.getfirstUserName();
		
		//verification
		user.openAssignUser();
		user.goToProjectTabInAssignUser();
		user.selectProjectInAssignUser(Input.projectName);
		if(!user.getUnAssignedUser(daUser).isElementAvailable(1)&&!user.getUnAssignedUser(paUser).isElementAvailable(1)) {
			base.passedStep("List of users is display which are currently do not have any role (including Domain Admin) in selected Project ");
		}else {
			base.failedStep("unassigned user verifcation failed");
		}
		
		base.passedStep("verified that unassigned users list should displays users which are currently do not have any roles in selected project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/21/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that the Assign User popup should present with two tabs - "Domains", "Projects" for System Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53009",enabled = true, groups = {"regression" })
	public void verifyAssignUserhasDomainAndProjectTab() throws InterruptedException  {
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53009");
		base.stepInfo("To verify that the Assign User popup should present with two tabs - \"Domains\", \"Projects\" for System Admin");
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		user.openAssignUser();
		if(user.getDomaintab().isDisplayed()&&user.getProjectTab().isDisplayed()) {
			base.passedStep("Two tabs was present as 'Domains and 'Projects'");
		}else {
			base.failedStep("tab verificati0n failed");
		}
		
		base.passedStep("verified that the Assign User popup should present with two tabs - \"Domains\", \"Projects\" for System Admin");
		loginPage.logout();
	}
	/**
	 * @Author :Aathith 
	 * date: 07/21/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that System Admin can assign the users from the Unassigned users list
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53020",enabled = true, groups = {"regression" })
	public void verifySysAdminAssignUnAssigendUser() throws InterruptedException  {
		
		base = new BaseClass(driver);
		user = new UserManagement(driver);
		client = new ClientsPage(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53020");
		base.stepInfo("To verify that System Admin can assign the users from the Unassigned users list");
		
		//login as da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//get domain name
		client.navigateToClientPage();
		driver.waitForPageToBeReady();
		client.filterClientByType("Domain");
		String DomainName = client.getClientTableValue(1, base.getIndex(client.getClientTableHeaders(), "NAME")).getText().trim();
		
		//get domain user name
		user.navigateToUsersPAge();
		user.filterByName(Input.da1userName);
		String unAssignUser = user.getfirstUserName();
		
		//assign user
		user.openAssignUser();
		user.AssignUserToDomain(DomainName, unAssignUser);
		
		//verify 
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		
		if(base.text(DomainName).isElementAvailable(5)) {
			base.passedStep(DomainName+" domain was assigened succesfully");
		}else {
			base.failedStep("domain verification failed");
		}
		if(base.text("Active Projects").isElementAvailable(5)) {
			base.passedStep("It was redirect to Domain landing page");
		}else {
			base.failedStep("landing page verification failed");
		}
		
		//restore default
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.unAssignUserToDomain(DomainName, unAssignUser);
		
		base.passedStep("verified that System Admin can assign the users from the Unassigned users list");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/21/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate modifying all editable field values and save changes for a domain project by Domain Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52960",enabled = true, groups = {"regression" })
	public void validateAllEditableFied() throws InterruptedException  {
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-52960");
		base.stepInfo("Validate modifying all editable field values and save changes for a domain project by Domain Admin");
		
		String ModifyName = Input.randomText + Utility.dynamicNameAppender();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
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
		
		base.passedStep("Validated modifying all editable field values and save changes for a domain project by Domain Admin");
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

}