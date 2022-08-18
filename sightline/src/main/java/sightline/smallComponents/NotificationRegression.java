package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class NotificationRegression {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	DocListPage docList;
	AssignmentsPage asgmt;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify, As an Project admin user login, I will get an
	 *              notification when I will execute any folder under My Search in
	 *              saved search[RPMXCON-53873]
	 */

	@Test(description = "RPMXCON-53873", enabled = true, groups = { "regression" })
	public void verifyNotificationForNode() throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String headerName = "Search Name";

		base.stepInfo("Test case Id: RPMXCON-53873 Notification");
		base.stepInfo(
				"To verify, As an Project admin user login, I will get an notification when I will execute any folder under My Search in saved search");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// create the node
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// save query in node
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNodeUnderGroup(searchName, newNode, Input.mySavedSearch);

		// Select Node
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);

		// get count of search in node
		int initialBg = base.initialBgCount();
		int sizeoflist = saveSearch.getListFromSavedSearchTable(headerName).size();

		// Execute Node
		saveSearch.performExecute();

		// verify Notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, sizeoflist);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify, As an Project admin user login, I will get an
	 *              notification when I will execute any saved query in saved search
	 *              page [RPMXCON-53870]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53870", enabled = true, groups = { "regression" })
	public void verifyNotifyForSearch() throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-53870 Notification");
		base.stepInfo(
				"To verify, As an Project admin user login, I will get an notification when I will execute any saved query in saved search page");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// configure query & Save
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		// select search
		saveSearch.savedSearch_Searchandclick(searchName);

		// execute Search
		int initialBg = base.initialBgCount();
		saveSearch.savedSearchExecute_Draft(searchName, initialBg);

		// verify Notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, Input.yesButton);

		// logout
		login.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description : To verify, as an Project admin user, I will get an
	 *              notification when I will perform Bulk Unrelease from saved
	 *              search [RPMXCON-53865]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53865", enabled = true, groups = { "regression" })
	public void verifyNotifyForReleasingSearch() throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String securityGroup = "Security" + UtilityLog.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53865 Notification");
		base.stepInfo(
				"To verify, as an Project admin user, I will get an notification when I will perform Bulk Unrelease from saved search");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// add security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// configure query and save
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		// select search
		saveSearch.savedSearch_Searchandclick(searchName);

		// bulk release the doc
		saveSearch.performReleaseAction(securityGroup, true);

		// unrelease  to SG 
		int initialBg = base.initialBgCount();
		saveSearch.performReleaseAction(securityGroup, true);
		
		// verify notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
		
		//delete search & SG
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, Input.yesButton);
		security.deleteSecurityGroups(securityGroup);
		
		// logout
		login.logout();
	}

	/**
	 * @Author : Sowndarya.velraj
	 * @Description :To verify, As an RM user login, I will get an notification when
	 *              I will perform Bulk Unassign from Doc List page.[RPMXCON-53866]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53866", enabled = true, groups = { "regression" })
	public void verifyBulkUnassignFromDocList() throws InterruptedException, Exception {
		DocListPage docList = new DocListPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-53866 Notification Component - Sprint 17");
		base.stepInfo(
				"To verify, As an RM user login, I will get an notification when I will perform Bulk Unassign from Doc List page");
		String assignmentName = "Assignment" + UtilityLog.dynamicNameAppender();
		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in as : " + Input.rmu1FullName);

		// perform search
		session.basicContentSearch(Input.testData1);
		session.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();

		// create assignment
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		base.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		base.stepInfo(assignmentName + " assignment opened in edit mode");
		agnmt.assignmentDistributingToReviewerManager();

		// navigate to doclist and perform bulk unassign
		session.basicContentSearch(Input.testData1);
		session.ViewInDocList();
		driver.waitForPageToBeReady();
		int initialBg = base.initialBgCount();
		docList.documentSelection(2);
		docList.bulkassignAndUnAssignTheDoc(assignmentName);

//		verify notification for bulk unassign
		base.checkNotificationCount(initialBg, 1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// logout
		login.logout();

	}

	/**
	 * @Author : Sowndarya.velraj
	 * @Description :To verify, As an Project admin user login, I will get an
	 *              notification when I will perform Bulk UnRelease from Doc List
	 *              page[RPMXCON-53867]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53867", enabled = true, groups = { "regression" })
	public void verifyBulkUnreleaseNotification_FromSecurityGroup() throws InterruptedException, Exception {
		String securityGroup = "Security" + Utility.dynamicNameAppender();

		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		base.stepInfo("Test case Id: RPMXCON-53867 Notification Component - Sprint 17");
		base.stepInfo(
				"To verify, As an Project admin user login, I will get an notification when I will perform Bulk UnRelease from Doc List page");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// add security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// perform search and bulk release to security group
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGroup);

		// navigate to doclist and perform bulk unrelease
		session.ViewInDocList();
		int initialBg = base.initialBgCount();
		docList.documentSelection(4);
		docList.bulkUnRelease(securityGroup);

		// verify notification for bulk unassign
		base.checkNotificationCount(initialBg, 1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// delete security group
		security.deleteSecurityGroups(securityGroup);

		// logout
		login.logout();

	}

	/**
	 * @Author : Sowndarya.velraj
	 * @Description :To verify, as an Project admin user, I will get an notification
	 *              when I will perform Bulk Unrelease from current
	 *              search[RPMXCON-53864]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53864", enabled = true, groups = { "regression" })
	public void verifyBulkUnreleaseFromCurrentSearch() throws InterruptedException, Exception {
		String securityGroup = "Security" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		base.stepInfo("Test case Id: RPMXCON-53864 Notification Component - Sprint 17");
		base.stepInfo(
				"To verify, as an Project admin user, I will get an notification when I will perform Bulk Unrelease from current search");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// add security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// perform search and bulk release to security group
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGroup);
		System.out.println("release done");

		// //perform bulk unrelease from current search
		base.selectproject();
		session.basicContentSearch(Input.testData1);
		int initialBg = base.initialBgCount();
		session.unReleaseDocsFromSecuritygroup(securityGroup);

		// verify notification for bulk unassign
		base.checkNotificationCount(initialBg, 1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// delete security group
		security.deleteSecurityGroups(securityGroup);

		// logout
		login.logout();

	}

	/**
	 * @Author : Sowndarya.velraj
	 * @Description :To Verify, As a Admin user login, After seeing the
	 *              Notification, it should not show the same notification after
	 *              re-login the session[RPMXCON-53587]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53587", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterReLogin() throws InterruptedException, Exception {
		base.stepInfo("Test case Id: RPMXCON-53587 Notification Component - Sprint 17");
		base.stepInfo(
				"To Verify, As a Admin user login, After seeing the Notification, it should not show the same notification after re-login the session");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// perform search and bulk release to security group
		String tagName = "Tag" + Utility.dynamicNameAppender();
		int initialBg = base.initialBgCount();
		session.basicContentSearch(Input.testData1);
		session.bulkTag(tagName);
		System.out.println("Bulk Tag done");

		// verify notification for bulk tag
		base.checkNotificationCount(initialBg, 1);
		List<String> notifyId_1 = saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// Re-login

		login.logout();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		List<String> notifyId_2 = saveSearch.verifyExecuteAndReleaseNotify(initialBg, 0);

		// check for same notification
		if (notifyId_1 == notifyId_2) {
			base.failedStep("shows same notification");

		} else {
			base.passedStep("Doesn't show same notification after re-login the session");
		}

		// logout
		login.logout();

	}

	/**
	 * @Author : Sowndarya.velraj
	 * @Description :To Verify, As a RM user login, After seeing the Notification,
	 *              it should not show the same notification after re-login the
	 *              session[RPMXCON-53588]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53588", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterReLogin_RMU() throws InterruptedException, Exception {
		base.stepInfo("Test case Id: RPMXCON-53588 Notification Component - Sprint 17");
		base.stepInfo(
				"To Verify, As a RM user login, After seeing the Notification, it should not show the same notification after re-login the session");

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in as : " + Input.rmu1FullName);

		// perform search and bulk release to security group
		String tagName = "Tag" + Utility.dynamicNameAppender();
		int initialBg = base.initialBgCount();
		session.basicContentSearch(Input.testData1);
		session.bulkTag(tagName);
		System.out.println("Bulk Tag done");

		// verify notification for bulk tag
		base.checkNotificationCount(initialBg, 1);
		List<String> notifyId_1 = saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// Re-login

		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		List<String> notifyId_2 = saveSearch.verifyExecuteAndReleaseNotify(initialBg, 0);

		// check for same notification
		if (notifyId_1 == notifyId_2) {
			base.failedStep("shows same notification");

		} else {
			base.passedStep("Doesn't show same notification after re-login the session");
		}

		// logout
		login.logout();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
