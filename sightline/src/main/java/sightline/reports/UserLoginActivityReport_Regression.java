package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.UserLoginActivityReport;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserLoginActivityReport_Regression {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	UserLoginActivityReport userLoginActivityRptPg;
	UserReviewActivityReport userActivityRptPg;
	AssignmentsPage agnmt;
	SessionSearch search;
	DocViewPage docview;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify the Actions in the User Review Activity report and in
	 *               Document Audit report
	 */
	@Test(description = "RPMXCON-56475", groups = { "regression" }, enabled = true)
	public void verifyActionTypesInReports() {
		bc.stepInfo("Test case Id: RPMXCON-56475");
		bc.stepInfo("Verify the Actions in the User Review Activity report and in Document Audit report");
		SoftAssert sa = new SoftAssert();
		List<String> actualActionsListInUserReviewActivityRpt = new ArrayList<>();
		List<String> actualActionsListInDocumentAuditRpt = new ArrayList<>();
		ArrayList<String> expectedList = new ArrayList<String>(
				Arrays.asList("Added to Production", "Assigned to Assignment", "Batch Printed", "Comment Added",
						"Commented Removed", "Completed", "Download Native", "FOLDERBULK", "FOLDERCODESAME", "Foldered",
						"FOLDEREXPLICIT", "FOLDERPROPAGATED", "Highlighting Added", "Highlighting Removed", "Ingested",
						"Produced", "Production Deleted", "Published", "Redaction Added", "Redaction Removed",
						"Released to Security Group", "Remarks Added", "Remarks Removed", "Removed from Production",
						"Save Custom Field", "Saved", "Tagged - Bulk", "Tagged - Code Same", "Tagged - Explicit",
						"Tagged - Propagate", "Removed from Assignment", "UnCompleted", "UNFOLDERBULK",
						"UNFOLDERCODESAME", "UnFoldered", "UNFOLDEREXPLICIT", "UNFOLDERPROPAGATED", "UnPublished",
						"Removed from Assignment", "Untagged - Bulk", "Untagged - Code Same", "Untagged - Explicit",
						"Untagged - Propagate", "Viewed", "Viewed Images", "Viewed Translations"));
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userActivityRptPg.navigateToUserReviewActivityReport();
		bc.waitTillElemetToBeClickable(userActivityRptPg.getActionTypeExpandIcon());
		userActivityRptPg.getActionTypeExpandIcon().waitAndClick(10);
		bc.waitTime(3);
		actualActionsListInUserReviewActivityRpt = bc.getAvailableListofElements(userActivityRptPg.actionsList());
		sa.assertEquals(expectedList, actualActionsListInUserReviewActivityRpt,
				"Actions list are not displayed as expected");
		sa.assertAll();
		bc.passedStep("The expected actions list are present in user review activity report page");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		userActivityRptPg.navigateToDocumentAuditReport();
		bc.waitTillElemetToBeClickable(userActivityRptPg.getActionTypeExpandIcon());
		userActivityRptPg.getActionTypeExpandIcon().waitAndClick(10);
		bc.waitTime(3);
		actualActionsListInDocumentAuditRpt = bc.getAvailableListofElements(userActivityRptPg.actionsList());
		sa.assertEquals(expectedList, actualActionsListInDocumentAuditRpt,
				"Actions list are not displayed as expected");
		sa.assertAll();
		bc.passedStep("The expected actions list are present in document audit report page");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify that report shows RMUs and Reviewers login and log
	 *               out details when RMU runs the report
	 */
	@Test(description = "RPMXCON-48741", groups = { "regression" }, enabled = true)
	public void verifyLoginDetailsWhenLoginAsRMU() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48741");
		bc.stepInfo(
				"To verify that report shows RMUs and Reviewers login and log out details when RMU runs the report");
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Login as RMU successfully");
		String expectedRmuLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as RMU successfully");
		String expectedRmuLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Login as REV successfully");
		String expectedRevLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as REV successfully");
		String expectedRevLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.verifyPAuserNotPresent(Input.pa1FullName);
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.sortLoginTimeColumn();
		String actualRmuRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.rmu1userName);
		String actualRmuLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.rmu1userName);
		String actualRmuLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.rmu1userName);
		String actualRevRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.rev1userName);
		String actualRevLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.rev1userName);
		String actualRevLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.rev1userName);
		userLoginActivityRptPg.validateDate(expectedRmuLoginTime, actualRmuLoginTime, actualRmuRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedRmuLogoutTime, actualRmuLogoutTime, actualRmuRole, "logged-out");
		userLoginActivityRptPg.validateDate(expectedRevLoginTime, actualRevLoginTime, actualRevRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedRevLogoutTime, actualRevLogoutTime, actualRevRole, "logged-out");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify that report shows Project Admin, RMUs and Reviewers
	 *               login and log out details when Project Admin runs the report
	 */
	@Test(description = "RPMXCON-48742", groups = { "regression" }, enabled = true)
	public void verifyLoginDetailsWhenLoginAsPA() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48742");
		bc.stepInfo(
				"To verify that report shows Project Admin, RMUs and Reviewers login and log out details when Project Admin runs the report");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Login as PA successfully");
		String expectedPALoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as PA successfully");
		String expectedPALogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Login as RMU successfully");
		String expectedRmuLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as RMU successfully");
		String expectedRmuLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Login as REV successfully");
		String expectedRevLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as REV successfully");
		String expectedRevLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Login as PA successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.sortLoginTimeColumn();
		String actualPaRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.pa1userName);
		String actualPaLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.pa1userName);
		String actualPaLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.pa1userName);
		String actualRmuRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.rmu1userName);
		String actualRmuLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.rmu1userName);
		String actualRmuLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.rmu1userName);
		String actualRevRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.rev1userName);
		String actualRevLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.rev1userName);
		String actualRevLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.rev1userName);
		userLoginActivityRptPg.validateDate(expectedPALoginTime, actualPaLoginTime, actualPaRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedPALogoutTime, actualPaLogoutTime, actualPaRole, "logged-out");
		userLoginActivityRptPg.validateDate(expectedRmuLoginTime, actualRmuLoginTime, actualRmuRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedRmuLogoutTime, actualRmuLogoutTime, actualRmuRole, "logged-out");
		userLoginActivityRptPg.validateDate(expectedRevLoginTime, actualRevLoginTime, actualRevRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedRevLogoutTime, actualRevLogoutTime, actualRevRole, "logged-out");

		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify that user can save the current logged-in users, user
	 *               login activity report
	 */
	@Test(description = "RPMXCON-58639", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUserSaveCurrentLoggedInUserReport(String username, String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-58639");
		bc.stepInfo("Verify that user can save the current logged-in users, user login activity report");
		ReportsPage report = new ReportsPage(driver);
		String reportName = "ULAR" + Utility.dynamicNameAppender();
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.saveUserloginActivityReport(reportName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		userLoginActivityRptPg.verifyReportSavedSuccessfully(reportName);
		bc.waitTillElemetToBeClickable(userLoginActivityRptPg.customReports(reportName));
		userLoginActivityRptPg.customReports(reportName).waitAndClick(10);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		report.deleteCustomReport(reportName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that user can Export with all deatils from User Login
	 *               Activity report
	 */
	@Test(description = "RPMXCON-56521", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUserExportUserLoginActivityReport(String username, String paasword, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56521");
		bc.stepInfo("To verify that user can Export with all deatils from User Login Activity report");
		ReportsPage report = new ReportsPage(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(username, paasword);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully for " + role);
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading + 1, "File is not downloaded");
		sa.assertAll();
		bc.passedStep("User login activity report is downloaded successfully");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify that saved custom report of current logged-in users,
	 *               should displays correctly
	 */
	@Test(description = "RPMXCON-58641", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUserSaveCurrentLoggedInUserReport2(String username, String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-58641");
		bc.stepInfo("Verify that saved custom report of current logged-in users, should displays correctly");
		ReportsPage report = new ReportsPage(driver);
		String reportName = "ULAR" + Utility.dynamicNameAppender();
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.saveUserloginActivityReport(reportName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		userLoginActivityRptPg.verifyReportSavedSuccessfully(reportName);
		bc.waitTillElemetToBeClickable(userLoginActivityRptPg.customReports(reportName));
		userLoginActivityRptPg.customReports(reportName).waitAndClick(10);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		report.deleteCustomReport(reportName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @throws ParseException
	 * @description: Verify that user should not be displayed in report, when user
	 *               sign outs from application
	 */
	@Test(description = "RPMXCON-58568", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUserNotDisplayedWhenLoggedOut(String username, String paasword, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58568");
		bc.stepInfo("Verify that user should not be displayed in report, when user sign outs from application");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		lp.logout();
		lp.loginToSightLine(username, paasword);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.verifyLoggedOutUserNotPresent(Input.rev1userName);
		if (role == "PA") {
			userLoginActivityRptPg.verifyLoggedOutUserNotPresent(Input.rmu1userName);
		} else {
			userLoginActivityRptPg.verifyLoggedOutUserNotPresent(Input.pa1userName);
		}
		bc.passedStep("Successfully verifed the user is not displayed when user logged out from application");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @throws ParseException
	 * @description: Verify that for PA user, by default user login activity report
	 *               should present currently logged in PAUs, RMUs and reviewers in
	 *               project
	 */
	@Test(description = "RPMXCON-58564", groups = { "regression" }, enabled = true)
	public void verifyLoggedInUserPresentByPA() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58564");
		bc.stepInfo(
				"Verify that for PA user, by default user login activity report should present currently logged in PAUs, RMUs and reviewers in project");
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.pa1userName);
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rmu1userName);
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rev1userName);
		if (userLoginActivityRptPg.getOnlyShowActivity("Current Logged-in Users").isElementAvailable(5)) {
			bc.passedStep("Logged in users alone show in current logged in activity report");
		} else {
			bc.failedStep("Logged in users not show in current logged in activity report");
		}
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @throws ParseException
	 * @description: Verify that for RMU user, by default user login activity report
	 *               should present currently logged in RMUs and reviewers in the
	 *               assigned security group
	 */
	@Test(description = "RPMXCON-58565", groups = { "regression" }, enabled = true)
	public void verifyLoggedInUserPresentByRmu() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58565");
		bc.stepInfo(
				"Verify that for RMU user, by default user login activity report should present currently logged in RMUs and reviewers in the assigned security group");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		if (userLoginActivityRptPg.getCurrentLoggedInUser(Input.pa1userName).isElementAvailable(5) == false) {
			bc.passedStep("PA user is not displayed in the report when loggen in as RMU");
		} else {
			bc.failedStep("PA user is displayed in the report when loggen in as RMU");
		}
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rmu1userName);
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rev1userName);
		if (userLoginActivityRptPg.getOnlyShowActivity("Current Logged-in Users").isElementAvailable(5)) {
			bc.passedStep("Logged in users alone show in current logged in activity report");
		} else {
			bc.failedStep("Logged in users not show in current logged in activity report");
		}
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify that on selecting 'Login History' option from only show-
	 *               drop down, should be able to see the users history
	 */
	@Test(description = "RPMXCON-58566", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUersLoginHistory(String username, String password, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58566");
		bc.stepInfo(
				"Verify that on selecting 'Login History' option from only show- drop down, should be able to see the users history");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Login as PA successfully");
		String expectedPALoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as PA successfully");
		String expectedPALogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Login as RMU successfully");
		String expectedRmuLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as RMU successfully");
		String expectedRmuLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Login as REV successfully");
		String expectedRevLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as REV successfully");
		String expectedRevLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Login as PA successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		bc.waitTime(3);
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.sortLoginTimeColumn();
		String actualPaRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.pa1userName);
		String actualPaLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.pa1userName);
		String actualPaLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.pa1userName);
		String actualRmuRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.rmu1userName);
		String actualRmuLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.rmu1userName);
		String actualRmuLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.rmu1userName);
		String actualRevRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.rev1userName);
		String actualRevLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Login Date/Time (UTC)", Input.rev1userName);
		String actualRevLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(),
				"Logout Date/Time (UTC)", Input.rev1userName);
		userLoginActivityRptPg.validateDate(expectedPALoginTime, actualPaLoginTime, actualPaRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedPALogoutTime, actualPaLogoutTime, actualPaRole, "logged-out");
		userLoginActivityRptPg.validateDate(expectedRmuLoginTime, actualRmuLoginTime, actualRmuRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedRmuLogoutTime, actualRmuLogoutTime, actualRmuRole, "logged-out");
		userLoginActivityRptPg.validateDate(expectedRevLoginTime, actualRevLoginTime, actualRevRole, "logged-in");
		userLoginActivityRptPg.validateDate(expectedRevLogoutTime, actualRevLogoutTime, actualRevRole, "logged-out");
		bc.passedStep("The user can able to see logged in users history with the selcted date range as expected");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify that on selecting 'Current Logged-in users' option from
	 *               only show- drop down, should be able to see the current logged
	 *               in users on click of 'Apply Changes
	 */
	@Test(description = "RPMXCON-58567", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyReportGeneratedAfterApplyChanges(String username, String password, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58567");
		bc.stepInfo(
				"Verify that on selecting 'Current Logged-in users' option from only show- drop down, should be able to see the current logged in users on click of 'Apply Changes'");

		lp.loginToSightLine(username, password);
		bc.stepInfo("Login as PA successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.selectLoginActivities("Current Logged-in Users");
		if (userLoginActivityRptPg.getRerunReportText().isElementAvailable(5)) {
			bc.passedStep("Without apply changes the report is not generated");
		} else {
			bc.failedStep("Without apply changes the report is generated");
		}
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		bc.passedStep("Report is generated based");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify that user can export the current logged-in users, user
	 *               login activity report
	 */
	@Test(description = "RPMXCON-58638", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyExportForUserLoginActivityReport(String username, String password, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58638");
		bc.stepInfo("Verify that user can export the current logged-in users, user login activity report");
		ReportsPage report = new ReportsPage(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Login as PA successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		bc.passedStep("The user can able to see logged in users history with the selcted date range as expected");
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully for " + role);
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading + 1, "File is not downloaded");
		sa.assertAll();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.pa1userName);
		int filesInDirBeforeDownloading1 = bc.getDirFilesCount();
		int Bgcount1 = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully for " + role);
		report.downLoadReport(Bgcount1);
		int filesInDirAfterDownloading1 = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading1, filesInDirBeforeDownloading1 + 1, "File is not downloaded");
		sa.assertAll();
		bc.passedStep("User login activity report is downloaded successfully");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @throws ParseException
	 * @description: Verify that user can change the settings for the current
	 *               logged-in users, user login activity report
	 */
	@Test(description = "RPMXCON-58640", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyChangeSettingsUserLoginActivityReport(String username, String paasword, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58640");
		bc.stepInfo(
				"Verify that user can change the settings for the current logged-in users, user login activity report");
		lp.loginToSightLine(username, paasword);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.deselectColumn("Role");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.deselectColumn("First Name");
		userLoginActivityRptPg.deselectColumn("Project");
		bc.passedStep(
				"After deselected from the cloumn in settings icon, coloumn values are not displayed as expected");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @throws ParseException
	 * @description: Verify after impersonating as PA/RMU, by default user login
	 *               activity report should present currently logged in PAUs, RMUs
	 *               and reviewers in project
	 */
	@Test(description = "RPMXCON-58635", groups = { "regression" }, enabled = true)
	public void verifyCurrentLoggedInUserAfterImpersonation() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58635");
		bc.stepInfo(
				"Verify after impersonating as PA/RMU, by default user login activity report should present currently logged in PAUs, RMUs and reviewers in project");
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.impersonateSAtoPA();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.sa1userName);
		String actualPARole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.sa1userName);
		sa.assertEquals("Project Administrator", actualPARole);
		sa.assertAll();
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		bc.passedStep(
				"After impersonating from SA to PA, able to view the SA user in currently logged in user as PA user");
		lp.logout();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.impersonateSAtoRMU();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.sa1userName);
		String actualRMURole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role",
				Input.sa1userName);
		sa.assertEquals("Review Manager", actualRMURole);
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		sa.assertAll();
		bc.passedStep(
				"After impersonating from SA to RMU, able to view the SA user in currently logged in user as RMU user");
		lp.logout();
	}

	@Test(description = "RPMXCON-56369", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUserReviewReportActivityLinkIsPresent(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56369");
		bc.stepInfo("To verify that User Review Activity Report link is provided on Report Landing Page.");
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		bc.waitForElement(userActivityRptPg.getReports_UserReviewActivity());
		if (userActivityRptPg.getReports_UserReviewActivity().isDisplayed()) {
			bc.passedStep(
					"User activity report link is displayed in reports landing page on other section when login as "
							+ role);
		} else {
			bc.failedStep("User activity report link is not displayed in reports landing page when login as " + role);
		}
		lp.logout();
	}

	@Test(description = "RPMXCON-48724", dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void verifyUserReviewReportActivityPageIsDisplayed(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-48724");
		bc.stepInfo("To verify that on selecting User Activity Report link User Activity Report page is displayed.");
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		if (userActivityRptPg.getReports_UserReviewActivity().isDisplayed()) {
			bc.passedStep(
					"User activity report link is displayed in reports landing page on other section when login as "
							+ role);
		} else {
			bc.failedStep("User activity report link is not displayed in reports landing page when login as " + role);
		}
		userActivityRptPg.navigateToUserReviewActivityReport();
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify User Review Activity Report
	 */
	@Test(description = "RPMXCON-56941", groups = { "regression" }, enabled = true)
	public void verifyUserReviewActivityReport() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56941");
		bc.stepInfo("Verify User Review Activity Report");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String initialUtcTime = userActivityRptPg.getCurrentUtcTime();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.assignmentDistributingToReviewer();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		agnmt.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		bc.waitForElement(docview.getDocView_CurrentDocId());
		String completedDocId = docview.getDocView_CurrentDocId().getText();
		docview.editCodingForm("test");
		bc.waitTillElemetToBeClickable(agnmt.completeBtn());
		agnmt.completeBtn().waitAndClick(10);
		String actualCompltedTime = userActivityRptPg.getCurrentUtcTime();
		bc.waitForElement(docview.getDocView_CurrentDocId());
		bc.waitTime(3);
		String viewedDocId = docview.getDocView_CurrentDocId().getText();
		String expectedViewedDocTime = userActivityRptPg.getCurrentUtcTime();
		lp.logout();
		String finalUtcTime = userActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userActivityRptPg.navigateToUserReviewActivityReport();
		userActivityRptPg.generateUserReviewActivityReport(Input.rev1FullName, initialUtcTime, finalUtcTime);
		userActivityRptPg.validateStatusOfUser(completedDocId, " Completed");
		String expectedCompletedTime = userActivityRptPg.getDateFromReportsPage(completedDocId, " Completed");
		userActivityRptPg.validateDate(actualCompltedTime, expectedCompletedTime);
		userActivityRptPg.validateStatusOfUser(viewedDocId, "Viewed");
		String actualViewedDocTime = userActivityRptPg.getDateFromReportsPage(viewedDocId, "Viewed");
		userActivityRptPg.validateDate(actualViewedDocTime, expectedViewedDocTime);
		lp.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		userLoginActivityRptPg = new UserLoginActivityReport(driver);
		userActivityRptPg=new UserReviewActivityReport(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		bc.stepInfo("***Executed UserLoginActivityReport_Regression2_1****");

	}
}
