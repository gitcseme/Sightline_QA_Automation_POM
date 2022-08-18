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

public class DomainManagement_Regression_02 {
	
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
		driver.waitForPageToBeReady();
		userManage.deleteUser(Input.randomText);
		base.stepInfo("Existing user with role as Domain Admin is deleted");
		
		//Add deleted user
		base.CloseSuccessMsgpopup();
		userManage.createNewUser(Input.randomText, Input.randomText, "Domain Administrator", email, Input.domainName, Input.projectName);
		base.stepInfo("All the details was entered/selected  Success message should be displayed ");
		
		//remove added cred
		driver.waitForPageToBeReady();
		userManage.deleteUser(Input.randomText);
		
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
