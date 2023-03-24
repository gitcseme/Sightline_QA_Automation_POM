package sightline.domainManagement;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pageFactory.DataSets;
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
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Phase2_Regression1 {
	
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
	String newProject = Input.randomText + Utility.dynamicNameAppender();
	
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
		client = new ClientsPage(driver);
		dash = new DomainDashboard(driver);
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :When System Admin impersonate as RMU should be able to impersonate as Reviewer under different project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53122",enabled = true, groups = {"regression" })
	public void verifySaImpersonateRMuToRev() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53122");
		base.stepInfo("When System Admin impersonate as RMU should be able to impersonate as Reviewer under different project");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		base.impersonateSAtoRMU();
		driver.waitForPageToBeReady();
		if(base.text("Review Manager Dashboard for").isDisplayed()) {
			base.passedStep("System Admin should impersonate as Review Manager");
		}else {
			base.failedStep("verification failed");
		}
		base.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		
		base.passedStep("When System Admin impersonate as RMU is able to impersonate as Reviewer under different project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify while clicking on Project name "Go to Project" link will impersonated user as PAU into that project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53124",enabled = true, groups = {"regression" })
	public void verifyHyperLink() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53124");
		base.stepInfo("Verify while clicking on Project name \"Go to Project\" link will impersonated user as PAU into that project");
		
		//login as sa
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		driver.waitForPageToBeReady();
		dash.getFirstHyperLink().waitAndClick(10);
		dash.getFirstGoToProject().waitAndClick(10);
		base.stepInfo("clicked first hyper link and click go to project");
		driver.waitForPageToBeReady();
		if(base.text("Project").isElementAvailable(10)) {
			base.passedStep("Clicking on hyperlink automatically impersonated user as project admin into the clicked project");
		}else {
			base.failedStep("verification failed");
		}
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(loginPage.getSignoutMenu());
		
		base.passedStep("Verified while clicking on Project name \"Go to Project\" link will impersonated user as PAU into that project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Sys Admin adds existing domain admin user as PA/RMU/Reviewer under same domain project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53123",enabled = true, groups = {"regression" })
	public void verifySysAdminAddDaCredForPaAsSameDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53124");
		base.stepInfo("Verify when Sys Admin adds existing domain admin user as PA/RMU/Reviewer under same domain project");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		driver.waitForPageToBeReady();
		userManage.addNewUserWithoutVerifySuccesMsg(Input.randomText, Input.randomText, "Project Administrator", Input.da1userName, Input.domainName, Input.projectName);
		base.VerifyErrorMessage("20001000033 : This user cannot be added with the specified role since the user is already a domain administrator in the selected domain");
		
		base.passedStep("Verified when Sys Admin adds existing domain admin user as PA/RMU/Reviewer under same domain project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Sys Admin selects 'Domain Admin' as Impersonate To
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52860",enabled = true, groups = {"regression" })
	public void verifySysAdminImporsonate() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52860");
		base.stepInfo("Verify when Sys Admin selects 'Domain Admin' as Impersonate To");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole("Domain Administrator");
		base.selectImpersonateDomain(Input.domainName);
		base.stepInfo("Domain Admin role is selected  Make sure that on selecting the domain admin role drop down should be displayed to select the domain");
				
		base.passedStep("Verified when Sys Admin selects 'Domain Admin' as Impersonate To");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07-07-2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that if System admin apply the Filter User by selecting ‘Domain Administrator’ role then grid should show only DA users
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52795",enabled = true, groups = {"regression" })
	public void verifySysAdminFilterDomainAdmin() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52795");
		base.stepInfo("To verify that if System admin apply the Filter User by selecting ‘Domain Administrator’ role then grid should show only DA users");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		dash = new DomainDashboard(driver);
		softAssertion = new SoftAssert();
		
		//filter the role
		userManage.filterTheRole("Domain Administrator");
		
		//get colum values
		driver.waitForPageToBeReady();
		int colum = base.getIndex(userManage.userDetailsTableHeader(), "ROLE");
		List<String> roles = dash.getColumValues(userManage.getTableColumnData(colum));
		
		//verify colum values
		for(String role:roles) {
			softAssertion.assertEquals(role, "Domain Administrator");
		}
		softAssertion.assertAll();
		base.passedStep("It's show only Domain Admin users");
				
		base.passedStep("verified that if System admin apply the Filter User by selecting ‘Domain Administrator’ role then grid should show only DA users");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07-07-2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that 'Domain' value should not be displayed in Role drop down when Project Admin/RMU adds new user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52774",dataProvider = "PaRmu",enabled = true, groups = {"regression" })
	public void verifyDomainValueNotDiaplayed(String username, String password) throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52774");
		base.stepInfo("Verify that 'Domain' value should not be displayed in Role drop down when Project Admin/RMU adds new user");
		
		//login 
		loginPage.loginToSightLine(username, password);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		//user popup open
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		base.waitForElement(userManage.getAddUserBtn());
		userManage.getAddUserBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("Add User pop up was opened ");
		
		if(!userManage.getUserRole("Domain Administrator").isElementAvailable(1)) {
			base.passedStep("'Domain' value was not be displayed in Role drop down in Add User pop up for Project Admin and RMU user");
		}else {
			base.failedStep("verification failed");
		}
				
		base.passedStep("Verified that 'Domain' value should not be displayed in Role drop down when Project Admin/RMU adds new user");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07-07-2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that error message should be displayed when system admin adds existing system admin as domain user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52780",enabled = true, groups = {"regression" })
	public void verifySysAdminAddExitSaForDomainRole() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52780");
		base.stepInfo("Verify that error message should be displayed when system admin adds existing system admin as domain user");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		driver.waitForPageToBeReady();
		userManage.addNewUserWithoutVerifySuccesMsg(Input.randomText, Input.randomText, "Domain Administrator", Input.sa1userName, Input.domainName, Input.projectName);
		base.VerifyErrorMessage("20001000014 : The given user is already a system administrator and cannot be assigned another role.");
		
		base.passedStep("Verified that error message should be displayed when system admin adds existing system admin as domain user");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07-07-2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when system admin adds domain user same as deleted domain admin/project admin/RMU/Reviewer
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52781",enabled = true, groups = {"regression" })
	public void verifySysAdminAddDomainSameAsDeletedUser() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52781");
		base.stepInfo("Verify when system admin adds domain user same as deleted domain admin/project admin/RMU/Reviewer");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		String email = Input.randomText+Utility.dynamicNameAppender()+"@consilio.com";
		
		//pre-req delete exiting user
		userManage.createNewUser(Input.randomText, Input.randomText, "Domain Administrator", email, Input.domainName, Input.projectName);
		base.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		userManage.filterTodayCreatedUser();
		userManage.filterByName(email);
		userManage.deleteUser();
		base.stepInfo("Existing user with role as Domain Admin is deleted");
		
		//Add deleted user
		userManage.createNewUser(Input.randomText, Input.randomText, "Domain Administrator", email, Input.domainName, Input.projectName);
		base.stepInfo("All the details was entered/selected  Success message should be displayed ");
		base.CloseSuccessMsgpopup();
		//remove added cred
		driver.waitForPageToBeReady();
		userManage.filterTodayCreatedUser();
		userManage.filterByName(email);
		userManage.deleteUser();
		
		base.passedStep("Verified when system admin adds domain user same as deleted domain admin/project admin/RMU/Reviewer");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/12/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that Sys Admin can select only one domain during impersonation as Domain Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52861",enabled = true, groups = {"regression" })
	public void verifySysAdminSelectOnlyONeDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52861");
		base.stepInfo("Verify that Sys Admin can select only one domain during impersonation as Domain Admin");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole(Input.DomainAdministrator);
		base.selectImpersonateDomain(Input.domainName);
		
		base.getSelectDomain().selectFromDropdown().selectByIndex(1);
		base.stepInfo("Try to select more than one Domain from drop down  ");
		base.getSaveChangeRole().waitAndClick(10);
		driver.waitForPageToBeReady();
		String domain = base.getProjectNames().getText().trim();
		softAssertion.assertNotEquals(domain, Input.domainName);
		softAssertion.assertAll();
		base.passedStep("System Admin is select only one domain during impersonation as Domain Admin");
		
		base.passedStep("Verified that Sys Admin can select only one domain during impersonation as Domain Admin");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/12/2022 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify Role drop down from ' Impersonate To' when Sys Admin impersonates as 'Domain Admin'
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52867",enabled = true, groups = {"regression" })
	public void verifySysAdminImpersonateRoles() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52867");
		base.stepInfo("Verify Role drop down from ' Impersonate To' when Sys Admin impersonates as 'Domain Admin'");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		String[] roles = {Input.DomainAdministrator, Input.ProjectAdministrator, Input.ReviewManager, Input.Reviewer};
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		
		for(String role: roles) {
		base.selectImpersonateRole(role);
		driver.waitForPageToBeReady();
		if(base.getSelectRole(role).isDisplayed()) {
			base.passedStep(role+" role is displayed");
		}else {
			base.failedStep("verification failed");
		}
		}
		
		base.passedStep("Verified Role drop down from ' Impersonate To' when Sys Admin impersonates as 'Domain Admin'");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/12/2022 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify the fields from 'Impersonate to' when sys admin impersonate as Project Admin
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52868",enabled = true, groups = {"regression" })
	public void verifySysAdminToPa() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52868");
		base.stepInfo("Verify the fields from 'Impersonate to' when sys admin impersonate as Project Admin");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
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
		
		base.getSaveChangeRole().waitAndClick(5);
		driver.waitForPageToBeReady();
		if(base.text("Project").isElementAvailable(10)) {
			base.passedStep("sa Impersonated to pa");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verified the fields from 'Impersonate to' when sys admin impersonate as Project Admin");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/12/2022 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify Role drop down from ' Impersonate To' when Sys Admin impersonates as 'Review Manager'
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52872",enabled = true, groups = {"regression" })
	public void verifySysAdminToRmuRmuAvailRoles() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52872");
		base.stepInfo("Verify Role drop down from ' Impersonate To' when Sys Admin impersonates as 'Review Manager'");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		
		String[] roles = {Input.SystemAdministrator,Input.DomainAdministrator,Input.ProjectAdministrator,Input.Reviewer};
		
		base.impersonateSAtoRMU();
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		
		for(String role:roles) {
		base.selectImpersonateRole(role);
		driver.waitForPageToBeReady();
		if(base.getSelectRole(role).isDisplayed()) {
			base.passedStep(role+" role is displayed");
		}else {
			base.failedStep("verification failed");
		}
		}
		
		
		base.passedStep("Verify Role drop down from ' Impersonate To' when Sys Admin impersonates as 'Review Manager'");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/12/2022 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that error message should be displayed when Domain is not selected during Sys Admin > Domain Admin impersonation
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52873",enabled = true, groups = {"regression" })
	public void verifyErrorMsgForDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52873");
		base.stepInfo("Verify that error message should be displayed when Domain is not selected during Sys Admin > Domain Admin impersonation");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole(Input.DomainAdministrator);
		base.getSaveChangeRole().waitAndClick(5);
		driver.waitForPageToBeReady();
		if(base.text("You must specify a domain").isDisplayed()) {
			base.passedStep("Error message should be displayed to select domain  ");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verify that error message should be displayed when Domain is not selected during Sys Admin > Domain Admin impersonation");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/12/2022 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify fields from 'Impersonate To' on selecting role as RMU/Reviewer
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52874",enabled = true, groups = {"regression" })
	public void verifyFieldWhenImpersonate() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52874");
		base.stepInfo("Verify fields from 'Impersonate To' on selecting role as RMU/Reviewer");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		
		base.selectImpersonateRole("Review Manager");
		driver.waitForPageToBeReady();
		if(base.getAvlDomain().isDisplayed()) {
			base.passedStep("domain field is displayed");
		}else {
			base.failedStep("verification failed");
		}
		
		if(base.getAvlProject().isDisplayed()) {
			base.passedStep("project field is displayed");
		}else {
			base.failedStep("verification failed");
		}
		
		if(base.getSelectSecurityGroup().isDisplayed()) {
			base.passedStep("ecurity group field is displayed");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verify fields from 'Impersonate To' on selecting role as RMU/Reviewer");
		loginPage.logout();
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
		base.CloseSuccessMsgpopup();
		
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
		ClientsPage client = new ClientsPage(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		
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
		UserManagement user = new UserManagement(driver);
		
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
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(user.getPopupWindowHeader().getWebElement());
		Act.moveToElement(user.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		user.selectBulkAccessUsers(users);
		user.getBulkUserSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Access rights applied successfully");
		base.passedStep("Success message should be displayed");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Login as a rmu user :"+Input.rmu1userName);
		if(base.text("Datasets").isElementAvailable(1)) {
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
		Actions Act1 = new Actions(driver.getWebDriver());
		Act1.clickAndHold(user.getPopupWindowHeader().getWebElement());
		Act1.moveToElement(user.getPopupWindowHeader().getWebElement(), -10, 10);
		Act1.release().build().perform();
		user.selectBulkAccessUsers(users);
		user.getBulkUserSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Access rights applied successfully");
		base.passedStep("Success message should be displayed");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Login as a rmu user :"+Input.rmu1userName);
		if(!base.text("Datasets").isElementAvailable(0)) {
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
		UserManagement user = new UserManagement(driver);
		
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
		UserManagement user = new UserManagement(driver);
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
		UserManagement user = new UserManagement(driver);
		
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
		UserManagement user = new UserManagement(driver);
		
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
		UserManagement user = new UserManagement(driver);
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
		
		UserManagement user = new UserManagement(driver);
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
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), true);
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
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 2);
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
		
		UserManagement user = new UserManagement(driver);
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
		softAssertion.assertAll();
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), true);
		softAssertion.assertAll();
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
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 3);
		softAssertion.assertAll();
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		
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
		
		UserManagement user = new UserManagement(driver);
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
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), true);
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
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(),11);
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
		
		UserManagement user = new UserManagement(driver);
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
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 2);
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
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), true);
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
		
		UserManagement user = new UserManagement(driver);
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
//		user.getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
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
		
		UserManagement user = new UserManagement(driver);
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
		
		UserManagement user = new UserManagement(driver);
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
		
		UserManagement user = new UserManagement(driver);
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
		
		UserManagement user = new UserManagement(driver);
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
//		user.getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
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
		
		UserManagement user = new UserManagement(driver);
		
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
		user.createNewUser(Input.randomText, Input.randomText, Input.ReviewManager, email, Input.domainName, "AutomationIngestionProject");
		user.createNewUser(Input.randomText, Input.randomText, Input.Reviewer, email, Input.domainName, "AutomationHighVolumeProject");
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
		
		UserManagement user = new UserManagement(driver);
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
	/**
	 * @Author :Aathith 
	 * date: 08/11/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate Updating RMU to Domain Admin
	 */
	@Test(description = "RPMXCON-53168",enabled = true, groups = {"regression" })
	public void validatedRmuToDA() throws Exception  {
		
		UserManagement user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53168");
		base.stepInfo("Validate Updating RMU to Domain Admin");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		String email = Utility.randomCharacterAppender(6)+"@consilio.com";
		
		//create a user
		driver.waitForPageToBeReady();
		user.createNewUser(Input.randomText, Input.randomText, Input.ReviewManager, email, Input.domainName, Input.projectName);
		base.CloseSuccessMsgpopup();
		
		//change role rmu to da
		user.filterByName(email);
		user.editLoginUser();
		base.waitForElement(user.getUserChangeDropDown());
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getYesBtn().waitAndClick(5);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		base.CloseSuccessMsgpopup();
		
		//verify
		driver.waitForPageToBeReady();
		String getRole = user.getTableData("ROLE", 1);
		softAssertion.assertEquals(getRole, Input.DomainAdministrator);
		base.passedStep("User should be updated to Domain Admin");
		
		//eit funality
		user.editLoginUser();
		user.clickFunctionnalityTab();
		user.getIngestion().waitAndClick(5);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		
		//change role for another record
		base.stepInfo("performe action for same user another record");
		user.editLoginUser();
		base.waitForElement(user.getUserChangeDropDown());
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		base.getYesBtn().waitAndClick(5);
		user.selectProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		base.CloseSuccessMsgpopup();
		
		driver.waitForPageToBeReady();
		getRole = user.getTableData("ROLE", 1);
		softAssertion.assertEquals(getRole, Input.ProjectAdministrator);
		base.passedStep("User should be updated to Domain Admin");
		
		//edit funality
		user.editLoginUser();
		user.clickFunctionnalityTab();
		user.getIngestion().waitAndClick(5);
		user.saveButtonOfFunctionTab();
		base.VerifySuccessMessage("User profile was successfully modified");
		base.CloseSuccessMsgpopup();
		
		//delete added user
		user.filterTodayCreatedUser();
		user.filterByName(email);
		user.deleteUser();
		
		softAssertion.assertAll();
		base.passedStep("Validated Updating RMU to Domain Admin");
		loginPage.logout();
		
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/17/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Domain Admin assigned to 1 or more non-domain project then domain dropdown in the impersonation popup should display value "Not a Domain"
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53070",enabled = true, groups = {"regression" })
	public void verifyNOnDomainProjectInNotDomain() throws InterruptedException  {
		
		base = new BaseClass(driver);
		UserManagement user = new UserManagement(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53070");
		base.stepInfo("Verify when Domain Admin assigned to 1 or more non-domain project then domain dropdown in the impersonation popup should display value \"Not a Domain\"");
		
		//pre-req assign nondomain projet to da user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.filterByName(Input.da1userName);
		String userName = user.getfirstUserName();
		if(!base.text(Input.NonDomainProject).isElementAvailable(2)) {
		user.AssignUserToProject(Input.NonDomainProject, Input.ProjectAdministrator, userName);
		}
		loginPage.logout();
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		dash.waitForDomainDashBoardIsReady();
		base.openImpersonateTab();
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
		
		base.passedStep("Verified when Domain Admin assigned to 1 or more non-domain project then domain dropdown in the impersonation popup should display value \"Not a Domain\"");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.UnAssignUserToProject(Input.NonDomainProject, Input.ProjectAdministrator, userName);
		loginPage.logout();
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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52898");
		baseClass.stepInfo(
				"To verify change role should not happen when clicking on cancel in Edit pop up for any user");		
		userManage = new UserManagement(driver);
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
    	BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52962");
		baseClass.stepInfo("Validate Project Name field value for a non-domain project in edit mode by System Admin");
		
		userManage = new UserManagement(driver);
		ProjectPage projectPage = new ProjectPage(driver);
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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53006");
		baseClass.stepInfo("Verify that notification count should be updated to reflect the completed notifications");
		
		userManage = new UserManagement(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		DataSets data = new DataSets(driver);
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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53012");
		baseClass.stepInfo(
				"Verify that - After Impersonation (SA to DA) notification should be received upon completion of the project successfully with DA User");
		
		userManage = new UserManagement(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		DataSets data = new DataSets(driver);
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
		
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53021");
		baseClass.stepInfo(
				"Verify that when System Admin creates domain admin, domain should be single select from Add User pop up");
		
		userManage = new UserManagement(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		DataSets data = new DataSets(driver);
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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53029");
		baseClass.stepInfo("To verify that the Assign user popup should present only Project tab for Domain Admin");		
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

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
		
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53032");
		baseClass.stepInfo("To verify that the Assign user popup should present only Project tab for Domain Admin");		
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53035");
		baseClass.stepInfo(
				"To verify that if System Admin Assign users in Project tab and tries to navigate to the Domain tab without saving the changes, "
						+ "it should display the confirmation message");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		softAssertion = new SoftAssert();
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
    	BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53036");
		baseClass.stepInfo(
				"To verify that if System Admin UnAssign users in Project tab and tries to navigate"
				+ " to the Domain tab without saving the changes, it should display the confirmation message");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53037");
		baseClass.stepInfo(
				"Verify that Project Admin user(s) should be listed under "
				+ "the non-domain project user list page");
		
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();		
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
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53038");
		baseClass.stepInfo(
				"Verify that Project Admin user(s) should be listed under the domain project user list page");
		
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();		
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
		BaseClass baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-53053");
		baseClass.stepInfo(
				"Verify Unassigned users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from non-domain project and saved");
		
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
//		baseClass.waitForElement(userManage.getSelectusertoassignindomain());
//		boolean flag=baseClass.dropDownValueCheck(userManage.getSelectusertoassignindomain(), fullName);
//		softAssertion.assertTrue(flag);
		
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
		BaseClass baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-53054");
		baseClass.stepInfo(
				"Verify Unassinged users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from domain project and saved");
		
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
		BaseClass baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-53055");
		baseClass.stepInfo(
				"Verify Unassinged users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from non-domain project moved to Domains tab");
		baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
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
		BaseClass baseClass = new BaseClass(driver);
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-53056");
		baseClass.stepInfo(
				"Verify Unassinged users list on selection of domain when PA/RMU/Reviewer "
				+ "user is unassigned from domain project moved to Domains tab");
		
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
	/**
	 * @author  Date:NA ModifyDate:NA Testcase No:RPMXCON-52862
	 * @Description:Verify that after impersonation domain drop down in the header
	 *                     should be updated to reflect selected domain
	 **/
	@Test(description = "RPMXCON-52862", groups = { "regression" })
	public void verifyingImpersonatingSAToDAHeaderUpdatedToReflect() throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);

		DomainDashboard dash = new DomainDashboard(driver);
		baseClass.stepInfo("RPMXCON-52862");
		baseClass.stepInfo(
				"Verify that after impersonation domain drop down in the header should be updated to reflect selected domain");

		// login as SAU
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
		baseClass.stepInfo(Input.domainName + " domain selected in dropdown");
		System.out.println(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(baseClass.getSaveChangeRole());
		baseClass.getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersonated from SA to DA");

		baseClass.waitTime(5);
		baseClass.stepInfo("verifying Selected Domain ");
		if (dash.getCurrentDomainValue(Input.domainName).isDisplayed()) {
			baseClass.passedStep("User navigates domain drop down " + Input.domainName
					+ "  in the header has been updated to reflect selected domain as expected");
		} else {
			baseClass.failedStep("SA is not impersonated as DA and not navigates to domain  page");
		}

		loginPage.logout();

	}

	/**
	 * @author  Date:NA ModifyDate:NA Testcase No:RPMXCON-52984
	 * @Description:To verify that System Admin can select the users and unassigned
	 *                 for a selected project successfully.
	 **/
	@Test(description = "RPMXCON-52984", groups = { "regression" })
	public void verifySAUserUnAssignedSelectedProject() throws InterruptedException {

		UserManagement user = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		BaseClass baseClass = new BaseClass(driver);
		String firstname = "PA";
		String lastname = "consilio";
		baseClass.stepInfo("RPMXCON-52984");
		baseClass.stepInfo(
				"To verify that System Admin can select the users and  unassigned for a selected project successfully.");

		// login as SAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		user.UnAssignUserToProject(Input.largeVolDataProject, Input.ProjectAdministrator, Input.pa2FullName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("Login as Pa");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getProjectNames());
		baseClass.getProjectNames().waitAndClick(3);
		baseClass.waitTime(5);
		softassert.assertFalse(baseClass.getSelectProject(Input.largeVolDataProject).isElementAvailable(10));
		baseClass.passedStep(Input.largeVolDataProject + "  Unassigned project is not displayed");
		softassert.assertAll();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.navigateToUsersPAge();
		user.createUser(firstname, lastname, "Project Administrator", Input.pa2userName, null,
				Input.largeVolDataProject);
		loginPage.logout();
	}
	
	/**
	 * Author :  TestCase Id:RPMXCON-52958 Description: Validate modifying
	 * all editable field values and save changes for a non-domain project by System
	 * Admin
	 */
	@Test(description = "RPMXCON-52958", enabled = true, groups = { "regression" })
	public void verifyAllModifiedValueNonDomainProject() throws Exception {

		ProjectPage project = new ProjectPage(driver);
		String projectName = "Project" + Utility.dynamicNameAppender();
		String hcode = "hcode" + Utility.dynamicNameAppender();
		String engineType = "NUIX";
		String newProjectName = "NewProject" + Utility.dynamicNameAppender();
		String newFirm = "New" + Utility.dynamicNameAppender();
		String newCorpClient = "NewCrp" + Utility.dynamicNameAppender();
		String newhcode = "newhcode" + Utility.dynamicNameAppender();
		String projFolder = "NewAutomation";
		String ingFolder = "NewAutomation";
		String productionFolder = "NewAutomation";
		String uptdNoOfDocs = "10000";
		BaseClass baseClass = new BaseClass(driver);

		baseClass.stepInfo("RPMXCON-52958");
		baseClass.stepInfo(
				"Validate modifying all editable field values and save changes for a non-domain project by System Admin");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		// Add project
		project.navigateToProductionPage();
		project.AddNonDomainProject(projectName, hcode, engineType);
		driver.scrollPageToTop();

		// update project
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
		baseClass.waitForElement(project.getHCode());
		project.getHCode().SendKeys(newhcode);
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
		baseClass.waitForElement(project.getHCode());
		String act8 = project.getHCode().Value();
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

		// verify update project
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(act1, newProjectName);
		asserts.assertEquals(act2, newFirm);
		asserts.assertEquals(act3, newCorpClient);
		asserts.assertEquals(act4, projFolder);
		asserts.assertEquals(act5, ingFolder);
		asserts.assertEquals(act6, productionFolder);
		asserts.assertEquals(act7, uptdNoOfDocs);
		asserts.assertEquals(act8, newhcode);
		asserts.assertAll();
		baseClass.passedStep(
				"Validated - modifying all editable updated field values and save changes folder names has been updated to reflect the change successfully ");
		baseClass.passedStep(" non domain project by System Admin update changes has been loaded as expected");
		loginPage.logout();
	}
	
	@DataProvider(name= "PaRmu" )
	public Object[][] PaRmu(){
		return new Object[][] {{Input.pa1userName,Input.pa1password},{Input.rmu1userName,Input.rmu1password}};
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
