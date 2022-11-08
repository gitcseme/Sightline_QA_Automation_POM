package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;

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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserAndRoleManagement_Regression25 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewPage docView;
	Utility utility;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	UserManagement userManage;
	SessionSearch sessionSearch;
	DocViewPage docViewPage;
	SoftAssert softAssertion;

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
		userManage = new UserManagement(driver);

	}

	@DataProvider(name = "users")
	public Object[][] Users() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
	}
	

	@DataProvider(name = "SaAndPaUser")
	public Object[][] SaAndPaUser() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
	}

	/**
	 * Author :Mohan date: 02/11/2022 TestCase Id:RPMXCON-52914 Description :To
	 * verify project and domain drop down values when user change role to
	 * DA/PA/Reviewer/SA in Edit pop up as RMU user
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52914", enabled = true, groups = { "regression" })
	public void verifyProjectAndDomainDropValuesWhenUserChageRoleToDASARMUReviewerFromPA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52914");
		baseClass.stepInfo(
				"To verify project and domain drop down values when user change role to DA/PA/Reviewer/SA in Edit pop up as RMU user");

		// login as sys admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		// change role from DA to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.da1userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to DA");
		userManage.changeRoleToDAFromAnyUser();

		// change role from RMU to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from RMU to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to RMU");
		userManage.changeRoleToAnyUser(Input.ReviewManager, Input.projectName, Input.securityGroup);

		// change role from Reviewer to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from Reviewer to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to Reviewer");
		userManage.changeRoleToAnyUser(Input.Reviewer, Input.projectName, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * Author :Mohan date: 02/11/2022 TestCase Id:RPMXCON-52521 Description :To
	 * verify the user can access functionality as per the updated user rights.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52521", enabled = true, groups = { "regression" })
	public void verifyUserAccessFunctionalityAsPerUpdatedUserRights() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52521");
		baseClass.stepInfo("To verify the user can access functionality as per the updated user rights.");
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		// login as project admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.ValidateElement_Presence(baseClass.text("Datasets"), "Datasets");
		baseClass.ValidateElement_Presence(baseClass.text("Categorize"), "Categorize");
		baseClass.passedStep("Access of functionality after login is default");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		String userFirstName = userManage.getUserTableValuesFromManageUserTable("First Name", 1);
		String userLastName = userManage.getUserTableValuesFromManageUserTable("Last Name", 2);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		userManage.editFirstAndLastNameInEditUserPopup(userFirstName, userLastName);

		// Login as User and verify Module Access
		userManage.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);
		baseClass.passedStep("User is able to access the functionality as per the rights and logged in role.");
		loginPage.logout();
	}

	/**
	 * Author :Mohan date: 03/11/2022 TestCase Id:RPMXCON-52708 Description :To
	 * verify when 'Analytics Panels' is Checked/Unchecked from Edit User ->
	 * functionality tab
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52708", enabled = true, groups = { "regression" })
	public void verifyAnalyticsPanelIsCheckedUncheckedFromEditFunctionalities() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52708");
		baseClass
				.stepInfo("To verify when 'Analytics Panels' is Checked/Unchecked from Edit User -> functionality tab");

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Analytics Panels"),
				"Analytics Panels", false);
		loginPage.logout();

		// Login As Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getDocView_Analytics_liDocumentThreadMap(), "Thread Map");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Analytics Panels"),
				"Analytics Panels", true);
		loginPage.logout();

		// Login As Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getDocView_Analytics_liDocumentThreadMap(), "Thread Map");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Analytics Panels"),
				"Analytics Panels", false);
		loginPage.logout();

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getDocView_Analytics_liDocumentThreadMap(), "Thread Map");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Analytics Panels"),
				"Analytics Panels", true);
		loginPage.logout();

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getDocView_Analytics_liDocumentThreadMap(), "Thread Map");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Analytics Panels"),
				"Analytics Panels", false);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getDocView_Analytics_liDocumentThreadMap(), "Thread Map");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Analytics Panels"),
				"Analytics Panels", true);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getDocView_Analytics_liDocumentThreadMap(), "Thread Map");
		loginPage.logout();
	}

	/**
	 * Author :Mohan date: 03/11/2022 TestCase Id:RPMXCON-52709 Description :To
	 * verify for user when 'Reviewer Remarks' is Checked/Unchecked from Edit User >
	 * functionality tab
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52709", enabled = true, groups = { "regression" })
	public void verifyReviewerRemarkslIsCheckedUncheckedFromEditFunctionalities() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52709");
		baseClass.stepInfo(
				"To verify for user when 'Reviewer Remarks' is Checked/Unchecked from Edit User > functionality tab");

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Reviewer Remarks"),
				"Reviewer Remarks", false);
		loginPage.logout();

		// Login As Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.getNonAudioRemarkBtn(), "Reviewer Remarks");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Reviewer Remarks"),
				"Reviewer Remarks", true);
		loginPage.logout();

		// Login As Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.getNonAudioRemarkBtn(), "Reviewer Remarks");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Reviewer Remarks"),
				"Reviewer Remarks", false);
		loginPage.logout();

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.getNonAudioRemarkBtn(), "Reviewer Remarks");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Reviewer Remarks"),
				"Reviewer Remarks", true);
		loginPage.logout();

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.getNonAudioRemarkBtn(), "Reviewer Remarks");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Reviewer Remarks"),
				"Reviewer Remarks", false);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.getNonAudioRemarkBtn(), "Reviewer Remarks");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Reviewer Remarks"),
				"Reviewer Remarks", true);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.getNonAudioRemarkBtn(), "Reviewer Remarks");
		loginPage.logout();
	}

	/**
	 * Author :Mohan date: 03/11/2022 TestCase Id:RPMXCON-52710 Description :To
	 * verify for user when 'Redaction' is Checked/Unchecked from Edit
	 * User->Functionality tab
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52710", enabled = true, groups = { "regression" })
	public void verifyRedactionIsCheckedUncheckedFromEditFunctionalities() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52710");
		baseClass
				.stepInfo("To verify for user when 'Redaction' is Checked/Unchecked from Edit User->Functionality tab");

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Redactions"), "Redactions", false);
		loginPage.logout();

		// Login As Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Navigate to docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.redactionIcon(), "Redactions");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Redactions"), "Redactions", true);
		loginPage.logout();

		// Login As Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.redactionIcon(), "Redactions");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Redactions"), "Redactions", false);
		loginPage.logout();

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.redactionIcon(), "Redactions");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Redactions"), "Redactions", true);
		loginPage.logout();

		// Login As Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.redactionIcon(), "Redactions");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Redactions"), "Redactions", false);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView.verifyAnalyticsPanel(docView.redactionIcon(), "Redactions");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.waitForElement(userManage.getFunctionalityTab());
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusForComponents(userManage.getComponentCheckBoxStatus("Redactions"), "Redactions", true);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");

		// Navigate to docview
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();

		// verify Analytics Panel
		docView = new DocViewPage(driver);
		docView.verifyAnalyticsPanel(docView.redactionIcon(), "Redactions");
		loginPage.logout();
	}

	/**
	 * Author :Mohan date: 08/11/2022 TestCase Id:RPMXCON-52891 Description :To
	 * verify project and domain drop down values when user change role to
	 * PA/SA/RMU/Reviewer in Edit pop up as DA user
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52891", enabled = true, groups = { "regression" })
	public void verifyProjectAndDomainDropValuesWhenUserChageRoleToDASARMUReviewerFromDA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52891");
		baseClass.stepInfo(
				"To verify project and domain drop down values when user change role to PA/SA/RMU/Reviewer in Edit pop up as DA user");

		// login as sys admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		// change role from DA to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.da1userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to DA");
		userManage.changeRoleToDAFromAnyUser();

		// change role from DA to RMU
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.da1userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to RMU");
		userManage.changeRoleToAnyUser(Input.ReviewManager, Input.projectName, Input.securityGroup);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from RMU to DA");
		userManage.changeRoleToDAFromAnyUser();

		// change role from DA to Reviewer
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.da1userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to Reviewer");
		userManage.changeRoleToAnyUser(Input.Reviewer, Input.projectName, Input.securityGroup);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from Reviewer to DA");
		userManage.changeRoleToDAFromAnyUser();

		loginPage.logout();

	}
	
	/**
	 * Author :Mohan date: 08/11/2022 TestCase Id:RPMXCON-52478 
	 * Description :To verify when Sys Admin edits the users rights for all roles and 'Save' those rights.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52478", enabled = true, groups = { "regression" })
	public void verifySysAdminEditsUsersRightsForAllRolesAndSave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52478");
		baseClass.stepInfo("To verify when Sys Admin edits the users rights for all roles and 'Save' those rights.");

		// login as sys admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		
		String[] checkedCbPa = { Input.Searching, Input.Highlighting, Input.Redactions, Input.ReviewerRemarks,
				Input.AnalyticsPanels, Input.Manage, Input.Productions, Input.AllReports, Input.ConceptExplorer,
				Input.CommunicationsExplorer, Input.Categorize, Input.Datasets };
		String[] disabledCBPa = { Input.ManageDomainProjects };
		String[] uncheckedCBPa = { Input.Ingestions };

		String[] checkedCb = { Input.Searching, Input.Highlighting, Input.Redactions, Input.ReviewerRemarks,
				Input.AnalyticsPanels, Input.Manage, Input.Productions, Input.AllReports, Input.ConceptExplorer,
				Input.CommunicationsExplorer, Input.Categorize };
		String[] disabledCB = { Input.ManageDomainProjects, Input.Ingestions };
		String[] uncheckedCB = { Input.Datasets };

		String[] checkedCbRev = { Input.Searching, Input.Highlighting, Input.Redactions, Input.ReviewerRemarks,
				Input.AnalyticsPanels };
		String[] disabledCBRev = { Input.Manage, Input.ManageDomainProjects, Input.Ingestions, Input.Productions,
				Input.Datasets, Input.AllReports };
		String[] uncheckedCBRev = { Input.ConceptExplorer, Input.CommunicationsExplorer };


		// navigate to userpage
		userManage.navigateToUsersPAge();

		String[][] usersToCheck = { { Input.ProjectAdministrator, Input.pa1userName, "PA" },
				{ Input.ReviewManager, Input.rmu1userName, "PA" }, { Input.Reviewer, Input.rev1userName, "PA" } };

		// verify Checked, unchecked, & Disabled checkbox of users
		// And verification for Check or uncheck Component and save
		userManage.verifyFunctionalityCheckbox(usersToCheck, checkedCbPa, disabledCBPa, uncheckedCBPa, checkedCb,
				disabledCB, uncheckedCB, checkedCbRev, disabledCBRev, uncheckedCBRev, true, Input.DownloadNative);

		// logout
		loginPage.logout();
		
		
	}
	
	/**
	 * Author :Mohan date: 08/11/2022 TestCase Id:RPMXCON-52408 
	 * Description :Add Existing User: To verify mandatory fields validation when fields are blank
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52408",dataProvider = "users", enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldsValidationWhenFieldsAreBlank(String username, String password, String userRole) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52408");
		baseClass.stepInfo("Add Existing User: To verify mandatory fields validation when fields are blank");

		// login as Users
		loginPage.loginToSightLine(username,password);
		baseClass.stepInfo("Logged in as "+userRole+"");
		
		//clickOn Add New User button
		userManage.navigateToUsersPAge();
		baseClass.waitForElement(userManage.getAddUserBtn());
		userManage.getAddUserBtn().waitAndClick(5);
		
		//verify Mandatory fields errors
		userManage.verifyErrorMessageInMandatoryFields();
		
		//logout
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
			// loginPage.logout();
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
