package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
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
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;

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
	 * Author : Aathith date: NA Modified date: Modified by: Description :Verify
	 * when Domain Admin impersonates as PA in domain project and then changes the
	 * project from header drop down
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyDaImpersateAndChangeProject() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53266");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Verify when Domain Admin impersonates as PA in domain project and then changes the project from header drop down");
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

		baseClass.passedStep(
				"Verified when Domain Admin impersonates as PA in domain project and then changes the project from header drop down");

		loginPage.logout();

	}

	/**
	 * Author : Aathith date: NA Modified date: Modified by: Description :Verify
	 * when Domain Admin impersonates as RMU in domain project and then changes the
	 * project from header drop down
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
	public void verifyDaImpersateRmuAndChangeProject() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53265");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Verify when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down");
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

		baseClass.passedStep(
				"Verified when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down");

		loginPage.logout();

	}

	/**
	 * Author : Aathith date: NA Modified date: Modified by: Description :Verify
	 * when user impersonate from SA to DA,Project name hyperlink should be
	 * displayed on dashboard page
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 4)
	public void verifySaImporsonateDAtoPa() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53127");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Verify when user impersonate from SA to DA,Project name hyperlink should be displayed on dashboard page");
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
		baseClass.stepInfo(
				"Clickied on hyperlink that automatically impersonated user as project admin into the clicked project.");
		baseClass.passedStep(
				"Verify when user impersonate from SA to DA,Project name hyperlink should be displayed on dashboard page");

		loginPage.logout();

	}

	/**
	 * Author : Aathith date: NA Modified date: Modified by: Description :To verify
	 * that For Domain, Unassigned user list should not contain any user who is a
	 * System Admin in the system.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 5)
	public void verifyUnassignUsernotContainSaUser() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53120");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"verify that For Domain, Unassigned user list should not contain any user who is a System Admin in the system.");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1userName);

		userManage.passingUserName(Input.sa1userName);
		userManage.applyFilter();
		driver.waitForPageToBeReady();
		String firstName = userManage.getUserFirstName(1).getText();
		String lastName = userManage.getUserLastName(1).getText();
		String userName = firstName + " " + lastName;
		userManage.verifySaUserNotInUnAssigneduser(userName);

		baseClass.passedStep(
				"verified that For Domain, Unassigned user list should not contain any user who is a System Admin in the system.");

		loginPage.logout();

	}

	/**
	 * Author : Aathith date: NA Modified date: Modified by: Description :When a
	 * user is a domain admin in multiple domains, and when the user is edited to
	 * modify the rights for one domain, the modified rights should be saved and
	 * persisted accordingly for the domain selected
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
		baseClass.passedStep(
				"Verified When a user is a domain admin in multiple domains, and when the user is edited to modify the rights for one domain,"
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
	 * Author :Aathith date: NA Modified date: Modified by: Description :Validate
	 * notification alert for bulk actions(Folder/Tag/Export) as
	 * Reviewer(impersonate from DAU)
	 * 
	 * @Hint : this cases is run under uat environment need "Automation_NonDomain"
	 *       project allocation the users pa and reviewer
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 7)
	public void verifyingBulkActionsInNonDomainProject() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53095");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Validate notification alert for bulk actions(Folder/Tag/Export) as Reviewer(impersonate from DAU)");
		userManage = new UserManagement(driver);
		String TagName = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(TagName, "Select Tag Classification");

		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		Reporter.log("Logged in as User: " + Input.da1userName);
		baseClass.impersonateDAtoReviewer();
		baseClass.stepInfo("Impersonated as Reviewer in same domain project");

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		search.verifyingBackGrounTaskInBullHornIcon();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.NonDomainProject);
		tagsAndFolderPage.createNewTagwithClassification(TagName, "Select Tag Classification");
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

		baseClass.passedStep(
				"Validated notification alert for bulk actions(Folder/Tag/Export) as Reviewer(impersonate from DAU)");

		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 20/04/2022 Modified date: Modified by: Description
	 * :Verify that after saving the rights for the user for one domain should not
	 * alter the rights for any other domain in which the user is a domain
	 * admin.'RPMXCON-53149' Sprint-14
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
	public void verifyAfterSavingRightsForUserDomainAdmin() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53149");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Verify that after saving the rights for the user for one domain should not alter the rights for any other domain in which the user is a domain admin.");
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
		baseClass.passedStep(
				"Verified When a user is a domain admin in multiple domains, and when the user is edited to modify the rights for one domain,"
						+ " the modified rights should be saved and persisted accordingly for the domain selected");
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		userManage.editFunctionality(Input.domainName);
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("false");
		loginPage.logout();

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
		baseClass.verifyCurrentProject(Input.additionalDataProject);
		baseClass.visibleCheck("Ingestions");
		baseClass.stepInfo("Modified rights is saved for the user for the selected domain and is persisted");
		baseClass.passedStep(
				"Verified When a user is a domain admin in multiple domains, and when the user is edited to modify the rights for one domain,"
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
	 * Author :Vijaya.Rani date: 20/04/2022 Modified date: Modified by: Description
	 * :Validate notification alert and clickable link for Doc View and DocList for
	 * Reviewer(impersonate from DAU). 'RPMXCON-53094' Sprint-14
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 9)
	public void verifyAlertClickableLinkDocViewForReviewer() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53094");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Validate notification alert and clickable link for Doc View and DocList for Reviewer(impersonate from DAU)");
		userManage = new UserManagement(driver);
		String TagName = "Tag" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(TagName, "Select Tag Classification");
		loginPage.logout();

		// Login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.impersonateDAtoReviewer();
		baseClass.stepInfo("Impersonated as Reviewer in same domain project");
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		doc.bulkTagExisting(TagName);
		search.verifyingBackGrounTaskInBullHornIcon();
		loginPage.logout();

	}

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Validate
	 * notification alert for Search/Batch Upload as Reviewer(impersonate from DAU)
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
	public void verifyingBackGroungTaskInBullIcon() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53093");
		utility = new Utility(driver);
		baseClass.stepInfo("Validate notification alert for Search/Batch Upload as Reviewer(impersonate from DAU)");
		userManage = new UserManagement(driver);
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String TagName1 = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(TagName, "Select Tag Classification");

		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.impersonateDAtoReviewer();
		baseClass.stepInfo("Impersonated as Reviewer in same domain project");

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		search.verifyingBackGrounTaskInBullHornIcon();

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject("Automation_NonDomain");
		tagsAndFolderPage.createNewTagwithClassification(TagName1, "Select Tag Classification");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		Reporter.log("Logged in as User: " + Input.rev1userName);
		baseClass.selectproject("Automation_NonDomain");
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName1);
		search.verifyingBackGrounTaskInBullHornIcon();

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 21/04/2022 Modified date:NA Modified by: NA
	 * Description :Verify when domain admin adds the domain user whose password is
	 * not set and link to set password is expired and active. 'RPMXCON-52839'
	 * Sprint-14
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 11)
	public void createNewUserForDomainAdmin() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = "Test";
		String lastName = "Test";
		String role = "Domain Administrator";
		String emailId = "Test" + "@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52839");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"Verify when domain admin adds the domain user whose password is not set and link to set password is expired and active.");
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", Input.projectName);

		baseClass.stepInfo("Domain admin role displayed in dropdown field");
		baseClass.passedStep("Created new user with Domain admin rule");

		baseClass.stepInfo("Create Exiting user for domain administration");
		userManage.createExitingUser(firstName, lastName, role, emailId, " ", Input.projectName);

		baseClass.passedStep("Created Exiting user with Domain admin rule Error Message Is Displayed");

		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);

		loginPage.logout();

	}

	/**
	 * 
	 * @Author :Aathith date: NA Modified date: Modified by:
	 * @Description :To verify that System Admin can assign Domain Admin to Domain
	 *              successfully
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 12)
	public void verifySaCanAssignDaToDomain() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53044");
		utility = new Utility(driver);
		baseClass.stepInfo("To verify that System Admin can assign Domain Admin to Domain successfully");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		Reporter.log("Logged in as User: " + Input.sa1userName);

		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		String firstName = userManage.getTableData("FIRST NAME", 1);
		String lastName = userManage.getTableData("LAST NAME", 1);
		String dominName = userManage.getTableData("DOMAIN", 1);
		String userName = firstName + " " + lastName;
		String isBillable = " || IsBillable: false";

		userManage.unAssignUserToDomain(dominName, userName + isBillable);
		baseClass.CloseSuccessMsgpopup();
		baseClass.stepInfo("Select Domains  Select User from Unassigned User list  Click on arrow ");
		userManage.AssignUserToDomain(dominName, userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		Reporter.log("Logged in as User: " + Input.da1userName);
		DomainDashboard domain = new DomainDashboard(driver);
		if (domain.availableDomains(dominName).isElementPresent()) {
			baseClass.passedStep(domain + " domain is available");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}

		baseClass.passedStep("verified that System Admin can assign Domain Admin to Domain successfully");

		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: NA Modified date: Modified by:
	 * @Description :To verify that if System Admin has made changes in Domain tab
	 *              and tries to navigate to the Project tab without saving the
	 *              changes, it should display the confirmation message
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 13)
	public void verifyBellyBandMessageOnDomainTab() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53033");
		utility = new Utility(driver);
		baseClass.stepInfo("To verify that if System Admin has made changes in Domain tab and"
				+ " tries to navigate to the Project tab without saving the changes, it should display the  confirmation message");
		userManage = new UserManagement(driver);

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		Reporter.log("Logged in as User: " + Input.sa1userName);

		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		String firstName = userManage.getTableData("FIRST NAME", 1);
		String lastName = userManage.getTableData("LAST NAME", 1);
		String dominName = userManage.getTableData("DOMAIN", 1);
		String userName = firstName + " " + lastName;
		String isBillable = " || IsBillable: false";

		userManage.unAssignUserToDomain(dominName, userName + isBillable);
		baseClass.CloseSuccessMsgpopup();
		baseClass.stepInfo("Select Domains  Select User from Unassigned User list  Click on arrow ");
		userManage.verifyUnAssignUserToAssignUserBellyBandYes(dominName, userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		Reporter.log("Logged in as User: " + Input.da1userName);
		DomainDashboard domain = new DomainDashboard(driver);
		if (domain.availableDomains(dominName).isElementPresent()) {
			baseClass.passedStep(domain + " domain is available");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.unAssignUserToDomain(dominName, userName + isBillable);
		baseClass.CloseSuccessMsgpopup();
		userManage.verifyUnAssignUserToAssignUserBellyBandNo(dominName, userName);
		userManage.getPopUpCloseBtn().waitAndClick(10);
		driver.waitForPageToBeReady();

		userManage.AssignUserToDomain(dominName, userName);

		baseClass.passedStep("verified that if System Admin has made changes in Domain tab and"
				+ " tries to navigate to the Project tab without saving the changes, it should display the  confirmation message");

		loginPage.logout();
	}

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :To verify
	 * that if System Admin UnAssign users in Domain tab and tries to navigate to
	 * the Project tab without saving the changes, it should display the
	 * confirmation message
	 */

	@Test(alwaysRun = true, groups = { "regression" }, priority = 14)
	public void verifyBellyBandMessageInSAUser() throws Exception {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53034");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"To verify that if System Admin UnAssign users in Domain tab and tries to navigate to the Project tab without saving the changes, it should display the confirmation message");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		Reporter.log("Logged in as User: " + Input.sa1userName);
		userManage = new UserManagement(driver);
		userManage.passingUserName(Input.da1userName);
		userManage.applyFilter();
		String firstName = userManage.getTableData("FIRST NAME", 1);
		String lastName = userManage.getTableData("LAST NAME", 1);
		String Billable = " || IsBillable: false";
		String userName = firstName + " " + lastName + Billable;
		String userName1 = firstName + " " + lastName;

		userManage.verifyingBellyBandMessageInAssignUser(Input.domainName, userName, userName1);
		userManage.selectingConfirmButtonToUnAssignTheAssignedUser(Input.domainName, userName);
		loginPage.logout();

	}
	

	/**
	 * @Author :Indium-Baskar date: NA Modified date:29/04/2022 Modified by:
	 * @Description :Validate notification alert for bulk
	 *              actions(Folder/Tag/Assign/Export) as RMU(impersonate from DAU)
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyingBulkActionImpToRmu() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53092");
		utility = new Utility(driver);
		baseClass.stepInfo("Validate notification alert for bulk actions(Folder/Tag/Assign/Export) "
				+ "as RMU(impersonate from DAU)");
		userManage = new UserManagement(driver);
		SessionSearch search = new SessionSearch(driver);
		String TagName = "Tag" + Utility.dynamicNameAppender();

		// login as pa and creating tag
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(TagName, "Select Tag Classification");

		loginPage.logout();
		// login as Da user
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		Reporter.log("Logged in as User: " + Input.da1userName);
		baseClass.impersonateBasedOnCondition("Review Manager", Input.domainName, Input.projectName);
		baseClass.stepInfo("Impersonated as Reviewer Manager in domain project");

		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		search.verifyingBackGrounTaskInBullHornIcon();
		loginPage.logout();

		// login as non-domain project
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		Reporter.log("Logged in as User: " + Input.rmu1userName);
		baseClass.selectproject(Input.NonDomainProject);
		baseClass.stepInfo("Performing action for non-domain project");
		tagsAndFolderPage.createNewTagwithClassification(TagName, "Select Tag Classification");
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		search.verifyingBackGrounTaskInBullHornIcon();
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar date: NA Modified date:29/04/2022 Modified by:29/04/2022
	 * @Description :To verify that Domain Admin can assign Project Admin
	 *              ((non-domain Admin user) to Domain Project
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 16)
	public void verifyingDomainAdminToPa() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52951");
		baseClass.stepInfo(
				"To verify that Domain Admin can assign Project Admin " + "((non-domain Admin user) to Domain Project");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.domainProjectuser(Input.projectName, Input.pa1FullName, "Project Administration", "", false, false);

		// logout
		loginPage.logout();

		// login as Pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.waitForElement(baseClass.getProjectNames());
		baseClass.getProjectNames().waitAndClick(10);
		List<String> projectName = baseClass.availableListofElements(userManage.getProjectCollection());
		if (projectName.contains(Input.projectName)) {
			baseClass.passedStep("Assigned user project displayed in project administration");
		} else {
			baseClass.failedStep("Assigned project not displayed");
		}
		baseClass.getSignoutMenu().waitAndClick(10);
		String rollName = userManage.getRoleAccess("Project Administrator").getText();
		softAssertion.assertEquals(rollName, "Project Administrator");
		baseClass.passedStep("Project admin role displayed as expected for assigned project");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:29/04/2022 Modified by:29/04/2022
	 * @Description :Validate client name filter is not available for Domain Admin impersonate from System A
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 17)
	public void verifyingClientFilterNameSAToDA() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53078");
		baseClass.stepInfo(
				"Validate client name filter is not available for Domain Admin impersonate from System A");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// login as Da
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Impersonating to Sa to Da user");
		baseClass.impersonateSAtoDA(Input.domainName);
		this.driver.getWebDriver().get(Input.url + "Project/Project");
		boolean filterStatus=userManage.getClientNameTextBox().Displayed();
		softAssertion.assertFalse(filterStatus);
		baseClass.passedStep("Client filter name tab not available when Sa impersonate to Da");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:29/04/2022 Modified by:29/04/2022
	 * @Description :Validate client name filter is not available when logged in as Domain Admin
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 18)
	public void verifyingClientFilterNameDA() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53077");
		baseClass.stepInfo(
				"Validate client name filter is not available when logged in as Domain Admin");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Navigating to project page");
		this.driver.getWebDriver().get(Input.url + "Project/Project");
		boolean filterStatus=userManage.getClientNameTextBox().Displayed();
		softAssertion.assertFalse(filterStatus);
		baseClass.passedStep("Client filter name tab not available for Da user");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author :Indium-Baskar date: NA Modified date:29/4/2022 Modified by:
	 * @Description :Validate notification alert for BatchPrint as RMU(impersonate from DAU)
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 19)
	public void verifyingBatchPrintImpToRmu() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53091");
		utility = new Utility(driver);
		baseClass.stepInfo("Validate notification alert for BatchPrint as RMU(impersonate from DAU)");
		userManage = new UserManagement(driver);
		SessionSearch search = new SessionSearch(driver);
		batchPrint=new BatchPrintPage(driver);
		String tagName = "Tag" + Utility.dynamicNameAppender();


		// login as Da user
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		Reporter.log("Logged in as User: " + Input.da1userName);
		baseClass.impersonateBasedOnCondition("Review Manager", Input.domainName, Input.projectName);
		baseClass.stepInfo("Impersonated as Reviewer Manager in domain project");

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "crammer", null);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'DocID'
		batchPrint.generateBatchPrint(Input.docFileName, Input.documentKey, true);
		
		int idValue=search.verifyingBullIconAndGetingIDValue();
		
		// Download ABtch Print File
		String fileName = batchPrint.DownloadBatchPrintFileBG(idValue);
		baseClass.stepInfo("Batch Print file downloaded filename : " +fileName+"");
		
		loginPage.logout();
		
		// login as rmu user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("performing task for non-domain project");
		baseClass.selectproject(Input.NonDomainProject);

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "crammer", null);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'DocID'
		batchPrint.generateBatchPrint(Input.docFileName, Input.documentKey, true);

		int idValueTwo = search.verifyingBullIconAndGetingIDValue();

		// Download ABtch Print File
		String fileNameNon = batchPrint.DownloadBatchPrintFileBG(idValueTwo);
		baseClass.stepInfo("Batch Print file downloaded filename : " + fileNameNon + "");
		
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
