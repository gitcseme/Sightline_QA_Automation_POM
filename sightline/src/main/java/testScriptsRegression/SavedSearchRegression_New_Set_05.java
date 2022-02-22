package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression_New_Set_05 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	MiniDocListPage miniDocListPage;
	SearchTermReportPage searchTerm;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@DataProvider(name = "SavedSearchwithRMUandREV")
	public Object[][] SavedSearchwithRMUandREV() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "UseraAndShareOprtions")
	public Object[][] UseraAndShareOprtions() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "UserAndShare")
	public Object[][] UserAndShare() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "UserPaAndSaAndDa")
	public Object[][] UserPaAndSaAndDa() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.sa1userName, Input.sa1password },
				{ Input.da1userName, Input.da1password } };
		return users;
	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	@DataProvider(name = "verifyOverwrittingSavedSearch")
	public Object[][] SavedSearchwithPAandRMUwithRole() {
		Object[][] users = { { "Yes", "COMPLETED" }, { "No", "DRAFT" } };
		return users;
	}

	@DataProvider(name = "verifyOverwrittingSavedSearchAsUser")
	public Object[][] verifyOverwrittingSavedSearchASUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "Yes", "COMPLETED", 1 },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "No", "DRAFT", 1 },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "Yes", "COMPLETED", 1 },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "No", "DRAFT", 1 },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "Yes", "COMPLETED", 1 },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "No", "DRAFT", 1 } };

		return users;
	}

	@DataProvider(name = "PaAndRmuUsers")
	public Object[][] PaAndRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.pa2userName, Input.pa2password, Input.pa1FullName },
				{ Input.rmu2userName, Input.rmu2password, Input.rmu1FullName } };

		return users;
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		saveSearch = new SavedSearch(driver);
		session = new SessionSearch(driver);
		softAssertion = new SoftAssert();
	}

	/**
	 * @author Raghuram A Date: 2/22/21 Modified date:N/A Modified by:N/A
	 * @Description: Verify that relevant error message appears when user modifies
	 *               (Rename with xyz)- batch Search "column header" and tries to
	 *               upload same file in Saved Search Screen. [RPMXCON-48535]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyBatchFileRenamedHeader() throws InterruptedException {

		String fileName = Input.BatchFileWithMultiplesheetFile;
		String fileFormat = ".xlsx";
		String batchNodeToCheck = fileName + "_" + 1 + "_Sheet" + 1;

		base.stepInfo("Test case Id: RPMXCON-48535 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that relevant error message appears when user modifies (Rename with xyz)- batch Search \"column header\" and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileName + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.sgExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 2/22/21 Modified date:N/A Modified by:N/A
	 *         Description: Verify that relevant error message appears when user
	 *         deletes- batch Search "column header from another Sheet 2" and tries
	 *         to upload same file in Saved Search Screen.(RPMXCON-48540)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void saveSearchBatchUploadInvalidHeaderDataMS() throws InterruptedException {

		String fileName = Input.batchFileWithMultiSheetColumnMissing;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck = fileName + "_" + sheetNum + "_Sheet" + sheetNum;
		String deletedColumHeaderBatchSheet = fileName + "_" + 2 + "_Sheet" + 2;

		base.stepInfo("Test case Id: RPMXCON-48540 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that relevant error message appears when user deletes- batch Search \"column header from another Sheet 2\" and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileName + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.sgExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		softAssertion.assertFalse(saveSearch.verifyNodePresent(deletedColumHeaderBatchSheet),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 2/22/21 Modified date:N/A Modified by:N/A
	 *         Description: Verify that relevant error message appears when user
	 *         duplicates (Repeats) - batch Search "column header in Sheet 2" and
	 *         tries to upload same file in Saved Search Screen.(RPMXCON-48541)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void saveSearchBatchUploadInvalidHeaderDataMSDuplicateHeader() throws InterruptedException {

		String fileName = Input.batchFileWithMultiSheetColumnDuplicate;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck = fileName + "_" + sheetNum + "_Sheet" + sheetNum;
		String duplicateColumHeaderBatchSheet = fileName + "_" + 2 + "_Sheet" + 2;

		base.stepInfo("Test case Id: RPMXCON-48541 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that relevant error message appears when user duplicates (Repeats) - batch Search \"column header in Sheet 2\" and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileName + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.sgExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		softAssertion.assertFalse(saveSearch.verifyNodePresent(duplicateColumHeaderBatchSheet),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		login.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				login.logoutWithoutAssert();
			} catch (Exception e) {
//							 TODO: handle exception
			}
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
			login.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			login.clearBrowserCache();
		} finally {
			login.clearBrowserCache();
		}
	}

}
