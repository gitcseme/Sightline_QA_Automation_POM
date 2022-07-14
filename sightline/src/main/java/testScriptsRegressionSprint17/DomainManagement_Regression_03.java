package testScriptsRegressionSprint17;

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
		userManage.deleteUser(Input.randomText);
		
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
		
		base.stepInfo("Test case Id: RPMXCON-52901");
		base.stepInfo("Verify when Domain Admin selects role as 'Project Administrator' from Change Role pop up");
		
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
		base.selectImpersonateDomain(Input.domainName);
		base.selectImpersonateProject(Input.projectName);
		base.stepInfo("'Project Administrator' role was selected and Domain,");
		if(base.getSelectRole(Input.ProjectAdministrator).isDisplayed()) {
			base.passedStep("Project drop down was displayed  ");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verify when Domain Admin selects role as 'Project Administrator' from Change Role pop up");
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