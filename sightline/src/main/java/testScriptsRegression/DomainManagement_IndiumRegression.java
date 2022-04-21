package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_IndiumRegression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	UserManagement userManage;
	
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
	 * Author : Baskar date: NA Modified date:21/01/2021 Modified by: Baskar
	 * Description :Verify that System Admin can create user with Domain Admin role
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void createNewUserForDomain() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52773");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that System Admin can create user with Domain Admin role");
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", Input.projectName);

		baseClass.stepInfo("Domain admin role displayed in dropdown field");
		baseClass.passedStep("Created new user with Domain admin rule");

		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :Verify when Domain Admin impersonates as PA in domain project and then changes the project from header drop down
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyDaImpersateAndChangeProject() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53266");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify when Domain Admin impersonates as PA in domain project and then changes the project from header drop down");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1password);

		baseClass.impersonateDAtoPAforMultiDominUser();
		baseClass.stepInfo("Impersonated as PA in same domain project");
		
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Changed the project from header drop down");
		driver.waitForPageToBeReady();
		baseClass.verifyCurrentProject(Input.additionalDataProject);
		
		baseClass.passedStep("Verified when Domain Admin impersonates as PA in domain project and then changes the project from header drop down");

		
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :Verify when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
	public void verifyDaImpersateRmuAndChangeProject() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53265");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1password);

		baseClass.impersonateDAtoRMU();
		baseClass.stepInfo("Impersonated as RMU in same domain project");
		
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Changed the project from header drop down");
		driver.waitForPageToBeReady();
		baseClass.verifyCurrentProject(Input.additionalDataProject);
		
		baseClass.passedStep("Verified when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down");

		
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :Verify when user impersonate from SA to DA,Project name hyperlink should be displayed on dashboard page
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 4)
	public void verifySaImporsonateDAtoPa() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53127");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify when user impersonate from SA to DA,Project name hyperlink should be displayed on dashboard page");
		userManage = new UserManagement(driver);
		DomainDashboard domain = new DomainDashboard(driver);
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1userName);

		baseClass.impersonateSAtoDA(Input.domainName);
		baseClass.stepInfo("Login as SA user and impersonate to DA");
		
		baseClass.waitForElement(domain.getprojectnamelink(Input.projectName));
		baseClass.elementDisplayCheck(domain.getprojectnamelink(Input.projectName));
		domain.getprojectnamelink(Input.projectName).waitAndClick(10);
		
		driver.waitForPageToBeReady();
		baseClass.verifyCurrentProject(Input.projectName);
		baseClass.visibleCheck("Datasets");
		baseClass.stepInfo("Clickied on hyperlink that automatically impersonated user as project admin into the clicked project.");
		baseClass.passedStep("Verify when user impersonate from SA to DA,Project name hyperlink should be displayed on dashboard page");
		
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :To verify that For Domain, Unassigned user list should not contain any user who is a System Admin in the system.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 5)
	public void verifyUnassignUsernotContainSaUser() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53120");
		utility = new Utility(driver);
		baseClass.stepInfo("verify that For Domain, Unassigned user list should not contain any user who is a System Admin in the system.");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1userName);

		userManage.passingUserName(Input.sa1userName);
		userManage.applyFilter();
		String firstName = userManage.getUserFirstName(1).getText();
		String lastName = userManage.getUserLastName(1).getText();
		String userName = firstName+" "+lastName;
		userManage.verifySaUserNotInUnAssigneduser(userName);
		
		baseClass.passedStep("verified that For Domain, Unassigned user list should not contain any user who is a System Admin in the system.");
		
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :When a user is a domain admin in multiple domains,
	 *  and when the user is edited to modify the rights for one domain, the modified rights should be saved 
	 *  and persisted accordingly for the domain selected
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 6)
	public void verifyModifiedUserRightPersistAccordingly() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53148");
		utility = new Utility(driver);
		baseClass.stepInfo("When a user is a domain admin in multiple domains, and when the user is edited"
				+ " to modify the rights for one domain, the modified rights should be saved and persisted accordingly for the domain selected");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1userName);

		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		userManage.editFunctionality(Input.domainName);
		
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("true");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1userName);
		
		baseClass.impersonateDAtoPAforMultiDominUser();
		
		driver.waitForPageToBeReady();
		baseClass.verifyCurrentProject(Input.projectName);
		baseClass.visibleCheck("Ingestions");
		baseClass.stepInfo("Modified rights is saved for the user for the selected domain and is persisted");
		baseClass.passedStep("Verified When a user is a domain admin in multiple domains, and when the user is edited to modify the rights for one domain,"
				+ " the modified rights should be saved and persisted accordingly for the domain selected");
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		userManage.editFunctionality(Input.domainName);
		
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("false");
		loginPage.logout();
	}
	/**
	* Author :Aathith date: NA Modified date: Modified by:
	* Description :Validate notification alert for bulk actions(Folder/Tag/Export) as Reviewer(impersonate from DAU)
	*  @Hint : this cases is run under uat environment need "Automation_NonDomain" project allocation the users pa and reviewer
	*/
	@Test(alwaysRun = true, groups = { "regression" }, priority = 7)
	public void verifyingBackGroungTaskInBullIcon() throws Exception {
	baseClass = new BaseClass(driver);

	baseClass.stepInfo("Test case Id: RPMXCON-53095");
	utility = new Utility(driver);
	baseClass.stepInfo("Validate notification alert for bulk actions(Folder/Tag/Export) as Reviewer(impersonate from DAU)");
	userManage = new UserManagement(driver);
	String TagName="Tag"+Utility.dynamicNameAppender();

	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.createNewTagwithClassification(TagName,"Select Tag Classification");

	loginPage.logout();
	loginPage.loginToSightLine(Input.da1userName, Input.da1password);
	Reporter.log("Logged in as User: " + Input.da1userName);
	baseClass.impersonateDAtoReviewer();
	baseClass.stepInfo("Impersonated as Reviewer in same domain project");

	SessionSearch search=new SessionSearch(driver);
	search.basicContentSearch(Input.testData1);
	search.bulkTagExisting(TagName);
	search.verifyingBackGrounTaskInBullHornIcon();
	loginPage.logout();
	
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	baseClass.selectproject(Input.NonDomainProject);
	tagsAndFolderPage.createNewTagwithClassification(TagName,"Select Tag Classification");
	loginPage.logout();
	
	baseClass.stepInfo("perform task for non domain project");
	loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
	baseClass.selectproject(Input.NonDomainProject);
	baseClass.stepInfo("Non domin project is selected");
	UtilityLog.info("Logged in as User: " + Input.rev1userName);
	Reporter.log("Logged in as User: " + Input.rev1userName);
	
	search.basicContentSearch(Input.testData1);
	search.bulkTagExisting(TagName);
	search.verifyingBackGrounTaskInBullHornIcon();
	
	baseClass.passedStep("Validated notification alert for bulk actions(Folder/Tag/Export) as Reviewer(impersonate from DAU)");
	
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
