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
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
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

	@DataProvider(name = "rmuAndRev")
	public Object[][] rmuAndRev() {
		Object[][] users = { { "RMU", "" }, { "REV", "RMU" } };
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
	 * @author Raghuram A Date: 2/22/22 Modified date:N/A Modified by:N/A
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
	 * @author Raghuram A Date: 2/22/22 Modified date:N/A Modified by:N/A
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
	 * @author Raghuram A Date: 2/22/22 Modified date:2/23/22 Modified by:Raghuram
	 *         Description: Verify that relevant error message appears when user
	 *         duplicates (Repeats) - batch Search "column header in Sheet 2" and
	 *         tries to upload same file in Saved Search Screen.(RPMXCON-48541)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void saveSearchBatchUploadInvalidHeaderDataMSDuplicateHeader() throws InterruptedException {

		String fileLocation = System.getProperty("user.dir") + Input.invalidBatchFileNewLocation;
		String fileName = Input.batchFileWithMultiSheetColumnDuplicate;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck, duplicateColumHeaderBatchSheet;

		base.stepInfo("Test case Id: RPMXCON-48541 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that relevant error message appears when user duplicates (Repeats) - batch Search \"column header in Sheet 2\" and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// Rename as dynamic fileName and store data respectively
		String fileToSelect = base.renameFile(true, fileLocation, fileName, fileFormat, false, "");
		System.out.println(fileToSelect);
		batchNodeToCheck = fileToSelect + "_" + sheetNum + "_Sheet" + sheetNum;
		duplicateColumHeaderBatchSheet = fileToSelect + "_" + 2 + "_Sheet" + 2;

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.sgExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		softAssertion.assertFalse(saveSearch.verifyNodePresent(duplicateColumHeaderBatchSheet),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Reset FIleName
		base.renameFile(false, fileLocation, fileToSelect, fileFormat, true, fileName);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : PAU impersonates down to the RMU and Reviewer level - Verify
	 *              that appropriate Search Group appears under the respective
	 *              Security Group on Saved Search Screen. [RPMXCON-48123]
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "rmuAndRev", groups = { "regression" }, priority = 4)
	public void verifyAppropriateSearchGroupAppears(String user, String addImp) throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48123 Saved Search");
		base.stepInfo(
				"PAU impersonates down to the RMU and Reviewer level - Verify that appropriate Search Group appears under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// impersonate pa to rmu
		base.rolesToImp("PA", user);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, user, "");

		base.rolesToImp(user, addImp);

		// select default SG and create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// verify Other SG ABsence in Default SG
		String passMsg = node1 + " : is Not Available As RMU in Default SG";
		Element otherSGNode = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, node1);
		base.ValidateElement_Absence(otherSGNode, passMsg);

		// impersonate rmu to pa
		base.rolesToImp("RMU", "PA");
		String node3 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", Input.yesButton);

		// verify default SG search Group Absence in PA
		String passMsgOfPa = node2 + " : is Not Available As PA ";
		Element nodeOfRmu = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, node2);
		base.ValidateElement_Absence(nodeOfRmu, passMsgOfPa);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node3);

		// Delete SG
		security.deleteSecurityGroups(securityGroup);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : SAU impersonates down to the RMU and Reviewer level - Verify
	 *              that appropriate Search Group appears under the respective
	 *              Security Group on Saved Search Screen. [RPMXCON-48124]
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "rmuAndRev", groups = { "regression" }, priority = 5)
	public void verifySearchGroupAsSA(String user, String addImp) throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("Test case Id: RPMXCON-48124 Saved Search");
		base.stepInfo(
				"SAU impersonates down to the RMU and Reviewer level - Verify that appropriate Search Group appears under the respective Security Group on Saved Search Screen.");

		base.rolesToImp("SA", "PA");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// impersonate pa to rmu
		base.rolesToImp("PA", user);

		// select default SG and create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, user, Input.yesButton);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, user, "");

		// verify default SG search Group Absence in Other SG
		String passMsgOfOtherSG = node1 + " : is Not Available In Other Security Group ";
		Element nodeOfOtherSG = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, node2);
		base.ValidateElement_Absence(nodeOfOtherSG, passMsgOfOtherSG);

		// verify Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, node1);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node1);

		// Delete SG
		base.rolesToImp(user, "PA");
		security.deleteSecurityGroups(securityGroup);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Reviewer User - Verify that appropriate Search Group appears
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48122]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifySearchGroupAsRev() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48122 Saved Search");
		base.stepInfo(
				"Reviewer User - Verify that appropriate Search Group appears under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);

		login.logout();

		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		// select default SG and create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", Input.yesButton);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "");

		// verify default SG search Group Absence in Other SG
		String passMsgOfOtherSG = node1 + " : is Not Available in Other Security Group";
		Element nodeOfOtherSG = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, node2);
		base.ValidateElement_Absence(nodeOfOtherSG, passMsgOfOtherSG);

		// verify Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, node1);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node1);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 2/23/22 Modified date:N/A Modified by:N/A
	 *         Description: Verify that relevant error message appears when user
	 *         modifies- batch Search "column header order changed another Sheet 2"
	 *         and tries to upload same file in Saved Search Screen.(RPMXCON-48543)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void saveSearchBatchUploadInvalidHeaderDataMScolumnInterChange() throws InterruptedException {

		String fileLocation = System.getProperty("user.dir") + Input.invalidBatchFileNewLocation;
		String fileName = Input.batchFileWithMultiSheetColumnOrderChange;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck, duplicateColumHeaderBatchSheet;

		base.stepInfo("Test case Id: RPMXCON-48543 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that relevant error message appears when user modifies- batch Search \"column header order changed another Sheet 2\" and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// Rename as dynamic fileName and store data respectively
		String fileToSelect = base.renameFile(true, fileLocation, fileName, fileFormat, false, "");
		System.out.println(fileToSelect);
		batchNodeToCheck = fileToSelect + "_" + sheetNum + "_Sheet" + sheetNum;
		duplicateColumHeaderBatchSheet = fileToSelect + "_" + 2 + "_Sheet" + 2;

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.sgExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		softAssertion.assertFalse(saveSearch.verifyNodePresent(duplicateColumHeaderBatchSheet),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Reset FIleName
		base.renameFile(false, fileLocation, fileToSelect, fileFormat, true, fileName);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 2/23/22 Modified date:N/A Modified by:N/A
	 *         Description: Verify that relevant error message appears when user
	 *         modifies - batch Search "column header" order changed and tries to
	 *         upload same file in Saved Search Screen.(RPMXCON-48542)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void saveSearchBatchUploadInvalidHeaderDatacolumnInterChange() throws InterruptedException {

		String fileLocation = System.getProperty("user.dir") + Input.invalidBatchFileNewLocation;
		String fileName = Input.batchFileWithColumnOrderChange;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck;

		base.stepInfo("Test case Id: RPMXCON-48542 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that relevant error message appears when user modifies - batch Search \"column header\" order changed and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// Rename as dynamic fileName and store data respectively
		String fileToSelect = base.renameFile(true, fileLocation, fileName, fileFormat, false, "");
		System.out.println(fileToSelect);
		batchNodeToCheck = fileToSelect + "_" + sheetNum + "_Sheet" + sheetNum;

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.sgExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Reset FIleName
		base.renameFile(false, fileLocation, fileToSelect, fileFormat, true, fileName);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/27/22 Modified date:N/A Modified by: Description
	 *         : Verify that user is not allowed to save a session search(Complete
	 *         Query) onto an existing saved search that is progress. RPMXCON-48914
	 *         Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void executionErrorInProgress() throws InterruptedException, ParseException {

		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String highVolumeProject = Input.highVolumeProject;

		base.stepInfo("Test case Id: RPMXCON-48914 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that user is not allowed to save a session search(Complete Query) onto an existing saved search that is progress.");
		base.stepInfo("Flow can only be done for inputs with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Perform Search and SaveSearch
		session.basicContentSearch(Input.searchString9);
		session.saveSearch(savedSearchName);
		session.getNewSearch().waitAndClick(5);
		session.multipleBasicContentSearch(Input.searchString1);

		// Execute
		base.stepInfo("Select an existing saved search that is progress and try to Save it");
		saveSearch.savedSearch_Searchandclick(savedSearchName);
		saveSearch.getSavedSearchExecuteButton().Click();

		// Verify Overwrite
		session.navigateToSessionSearchPageURL();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, savedSearchName, "First", "ExecutionErrorInProgress", "",
				null);

		// Delete Search
		saveSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Reviewer User - Verify that User can renames existing search
	 *              Group under the respective Security Group on Saved Search
	 *              Screen. [RPMXCON-48135]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyRenamedAsRev() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48135 Saved Search");
		base.stepInfo(
				"Reviewer User - Verify that User can renames existing search Group under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);

		login.logout();

		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		// select default SG and create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", Input.yesButton);

		String renamedNode = saveSearch.renameSearchGroup(node1);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "");

		// verify default SG search Group Absence in Other SG
		String passMsgOfOtherSG = renamedNode + " : is Not Available in Other Security Group";
		Element nodeOfDefSG = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, renamedNode);
		base.ValidateElement_Absence(nodeOfDefSG, passMsgOfOtherSG);

		// verify Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, renamedNode);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, renamedNode);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :RMU User - Verify that executed search groups appears under the
	 *              respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48129]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyExecutedSearchGroup() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String searchName = "Search" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA And assign SG access
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48129 Saved Search");
		base.stepInfo(
				"RMU User - Verify that executed search groups appears under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		login.logout();

		// Login AS RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// select default SG and create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, node1);

		// execute search group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.savedSearchExecute_SearchGRoup(searchName, purehit);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "");

		// verify default SG search Group Absence in Other SG
		String passMsgOfOtherSG = node1 + " : is Not Available in Other Security Group";
		Element nodeOfDefSG = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, node1);
		base.ValidateElement_Absence(nodeOfDefSG, passMsgOfOtherSG);

		// delete Node
		base.selectsecuritygroup(Input.securityGroup);
		saveSearch.deleteNode(Input.mySavedSearch, node1);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :RMU User - Verify that User can Share Query under the
	 *              respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48131]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifySharedQueryInSG() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;
		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48131 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can Share Query under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to RMU
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// select default SG and create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		saveSearch.shareSavedNodeWithDesiredGroup(node1, Input.shareSearchDefaultSG);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(securityTab, "RMU", "");

		// verify default SG search Group Absence in Other SG
		String passMsgOfOtherSG = node1 + " : is Not Available in Other Security Group";
		Element nodeOfDefSG = saveSearch.getSavedSearchNodeWithRespectiveSG(securityTab, node1);
		base.ValidateElement_Absence(nodeOfDefSG, passMsgOfOtherSG);

		// verify Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, node1);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node1);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, node1);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that BLANK \"Count\" gets display in conceptual Search
	 *              in Saved Search Column when user saved a Background Advanced
	 *              search Query [RPMXCON-48488]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyConceptuallYColumn() throws Exception {
		String Search1 = "Search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";
		String nearDupe = "Near Duplicate Count";
		String passMsg = "Conceptual Column Count is BLANK";
		String failMsg = "Conceptual Column Count is Not BLANK";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48488 Saved Search");
		base.stepInfo(
				"Verify that BLANK \"Count\" gets display in conceptual Search in Saved Search Column when user saved a Background Advanced search Query");

		// Basic Search
		session.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCN, null);
		session.SearchBtnAction();
		session.handleWhenAllResultsBtnInUncertainPopup();
		int purehit = session.returnPurehitCount();
		session.saveSearchadvanced(Search1);

		// Verify Conceptually Column
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getDocCountAndStatusOfBatch(Search1, nearDupe, true);
		String count = saveSearch.ApplyShowAndHideFilter(conceptually, Search1);
		base.textCompareEquals(count, Input.TextEmpty, passMsg, failMsg);

		// Delete Search
		saveSearch.deleteSearch(Search1, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 02/25/22 Modified date:N/A Modified by: Description
	 *         : Verify that user is not able to save a session search (Draft Query)
	 *         onto an existing saved search that is progress. RPMXCON-48912 Sprint
	 *         12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void executionErrorInProgressDraft() throws InterruptedException, ParseException {

		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String highVolumeProject = Input.highVolumeProject;

		base.stepInfo("Test case Id: RPMXCON-48912 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that user is not able to save a session search (Draft Query) onto an existing saved search that is progress.");
		base.stepInfo("Flow can only be done for inputs with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Perform Search and SaveSearch
		session.basicContentSearch(Input.searchString9);
		session.saveSearch(savedSearchName);

		// Execute
		base.stepInfo("Select an existing saved search that is progress and try to Save it");
		saveSearch.savedSearch_Searchandclick(savedSearchName);
		base.waitForElement(saveSearch.getSearchStatus(savedSearchName, "COMPLETED"));
		saveSearch.getSavedSearchExecuteButton().Click();

		// Verify Overwrite
		session.navigateToSessionSearchPageURL();
		session.getNewSearch().waitAndClick(5);
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "Third");

		session.saveAsOverwrittenSearch(Input.mySavedSearch, savedSearchName, "First", "ExecutionErrorInProgress", "",
				null);

		// Delete Search
		saveSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 2/25/22 Modified date:N/A Modified by: Description :
	 *         Verify on selecting saved search with In Progress status and Doc View
	 *         option warning message should be displayed RPMXCON-48611 Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, dataProvider = "AllTheUsers", groups = { "regression" }, priority = 15)
	public void verifyWarningMessageForInProgressSStoDocView(String username, String password, String fullName)
			throws InterruptedException, ParseException {

		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String highVolumeProject = Input.highVolumeProject;
		String statusToCheck = "INPROGRESS";
		String warningMessage = "The selected search is not yet completed successfully. Please select a valid completed search.";

		base.stepInfo("Test case Id: RPMXCON-48611 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify on selecting saved search with In Progress status and Doc View option warning message should be displayed");
		base.stepInfo("Flow can only be done for inputs with bulk data");

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);
		base.selectproject(highVolumeProject);

		// Perform Search and SaveSearch
		session.basicContentSearch(Input.searchString9);
		session.saveSearch(savedSearchName);

		// Execute
		base.stepInfo("Select an existing saved search that is progress and try to Save it");
		saveSearch.savedSearch_Searchandclick(savedSearchName);
		saveSearch.verifyStatusByReSearch(savedSearchName, "COMPLETED", 5);
		saveSearch.getSavedSearchExecuteButton().Click();
		driver.waitForPageToBeReady();
		base.CloseSuccessMsgpopup();

		// DocVIew
		saveSearch.savedSearch_SearchandSelect(savedSearchName, "Yes");
		base.waitForElement(saveSearch.getSearchStatus(savedSearchName, statusToCheck));
		base.stepInfo("Search is in " + statusToCheck + " status before clicking DocView");
		saveSearch.getToDocView().waitAndClick(5);
		base.stepInfo("Clicked DocView button");
		driver.waitForPageToBeReady();
		base.VerifyWarningMessage(warningMessage);

		// Delete Search
		saveSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 2/25/22 Modified date:N/A Modified by: Description :
	 *         Verify on selecting saved search with Error status and Doc View
	 *         option warning message should be displayed RPMXCON-48612 Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, dataProvider = "AllTheUsers", groups = { "regression" }, priority = 16)
	public void validateErrorSearchViaBatchUpload(String username, String password, String fullName) throws Exception {
		String file = saveSearch.renameFile(Input.errorQueryFileLocation);
		String statusToCheck = "ERROR";
		String warningMessage = "The selected search is not yet completed successfully. Please select a valid completed search.";
		int Bgcount;

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);
		base.stepInfo("Test case Id: RPMXCON-48612 Sprint 12");
		base.stepInfo(
				"Verify on selecting saved search with Error status and Doc View option warning message should be displayed");

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Upload Error Query Through Batch File
		saveSearch.navigateToSSPage();
		saveSearch.uploadBatchFile_D(Input.errorQueryFileLocation, file, true);
		saveSearch.getSubmitToUpload().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.CloseSuccessMsgpopup();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);

		// Select Batch file uploaded
		driver.waitForPageToBeReady();
		saveSearch.sgExpansion();
		saveSearch.getSavedSearchNewNode().waitAndClick(5);

		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", false);
		base.stepInfo("Search is in " + statusToCheck + " status before clicking DocView");
		saveSearch.getLastStatusSelectionFromGrid(statusToCheck).waitAndClick(5);
		saveSearch.getToDocView().waitAndClick(5);
		base.stepInfo("Clicked DocView button");
		driver.waitForPageToBeReady();
		base.VerifyWarningMessage(warningMessage);

		// Delete Uploaded File
		saveSearch.deleteUploadedBatchFile(file, Input.mySavedSearch, false, null);
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :RMU User - Verify that User can Exports Search Group under the
	 *              respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48132]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifySearchGroupAfterExport() throws Exception {
		String savedSearchName = "SEARCH" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48132 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can Exports Search Group under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to RMU
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// Basic Search
		session.basicContentSearch(Input.searchString4);
		session.saveSearchInNewNode(savedSearchName, node1);

		// Export Search Group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.verifyExportpopup(StyletoChoose, fieldTypeToChoose);

		// select Other SG and create Search group
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "");

		// verify default SG search Group Absence in Other SG
		String passMsgOfOtherSG = node1 + " : is Not Available in Other Security Group";
		Element nodeOfDefSG = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, node1);
		base.ValidateElement_Absence(nodeOfDefSG, passMsgOfOtherSG);

		// verify Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(node1);
		base.ValidateElement_Presence(nodeOfDefSG, node1);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node1);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :RMU User - Verify that documents are Assigned correctly under
	 *              the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48130]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyAssignmnetInOtherSG() throws Exception {
		String savedSearchName = "SEARCH" + Utility.dynamicNameAppender();
		String assignment = "Assign" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);
		AssignmentsPage assgnpage = new AssignmentsPage(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48130 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can Exports Search Group under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to RMU
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create Search group
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// Basic Search
		int pureHit = session.basicContentSearch(Input.searchString4);
		session.saveSearchInNewNode(savedSearchName, node1);

		// Perform Bulk Assign
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(node1);
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assgnpage.FinalizeAssignmentAfterBulkAssign();
		assgnpage.createAssignment_fromAssignUnassignPopup(assignment, Input.codeFormName);
		assgnpage.getAssignmentSaveButton().waitAndClick(5);
		base.stepInfo("Created a assignment " + assignment);

		// validate doc count assigned
		String docCount = assgnpage.verifydocsCountInAssgnPage(assignment);

		String passMsg = "The number of Documents assigned to Assignments Is  : " + docCount;
		String failMsg = "The Document Count is Incorrect";
		base.digitCompareEquals(Integer.parseInt(docCount), pureHit, passMsg, failMsg);

		// validate assignment absence in other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Select Security Group : " + securityGroup);
		assgnpage.navigateToAssignmentsPage();
		Element createdAssign = assgnpage.getSelectAssignment(assignment);

		String passMsgOfAssign = assignment + " : Created Assignment is not present";
		base.ValidateElement_Absence(createdAssign, passMsgOfAssign);

		// delete Created Assignment
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Select Security Group : " + Input.securityGroup);
		assgnpage.deleteAssgnmntUsingPagination(assignment);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that - After impersonation (SysAdmin to RMU) - logged
	 *              User Information gets updated in \"Last Submitted By\" column in
	 *              Saved Search screen [RPMXCON-48590]
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyLastSubmittedAfterExecute() throws Exception {
		String searchName = "SEARCH" + Utility.dynamicNameAppender();
		String headername = "Last Submitted By";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48590 Saved Search");
		base.stepInfo(
				"Verify that - After impersonation (SysAdmin to RMU) - logged User Information gets updated in \"Last Submitted By\" column in Saved Search screen");

		// Basic Search
		int pureHit = session.basicContentSearch(Input.searchString4);
		session.saveSearchAtAnyRootGroup(searchName, Input.shareSearchDefaultSG);

		login.logout();

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		// impersonate SA to RMU
		base.rolesToImp("SA", "RMU");

		String username = login.getCurrentUserName();
		String passMsg = username + " : is the Last Submiited Name Displayed";
		String failMsg = "The Last Submitted name is Incorrect";
		base.stepInfo("Logged in username : " + username);

		// verify Last Submitted Status For the search
		saveSearch.selectSavedSearchTAb(searchName, Input.shareSearchDefaultSG, Input.yesButton);
		saveSearch.savedSearchExecute_Draft(searchName, 0);
		String actualName = saveSearch.getListFromSavedSearchTable1(headername, searchName);

		base.textCompareEquals(actualName, username, passMsg, failMsg);

		// delete Search
		saveSearch.deleteSearch(searchName, Input.shareSearchDefaultSG, Input.yesButton);
		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 02/28/22 @Modified date:N/A @Modified by:N/A
	 * @Description : To verify as a RM user login, I will be able to search a saved
	 *              query based on search status 'In Progress' under My Saved Search
	 *              folder [RPMXCON-47564] sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void searchFilterBasedOnStatus() throws InterruptedException, ParseException {

		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String SearchName1 = "SearchName" + Utility.dynamicNameAppender();
		String statusToCheck = "INPROGRESS";
		String highVolumeProject = Input.highVolumeProject;

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		base.selectproject(highVolumeProject);

		base.stepInfo("Test case Id: RPMXCON-47564 Saved Search - Sprint 12");
		base.stepInfo(
				"To verify as a RM user login, I will be able to search a saved query based on search status 'In Progress' under My Saved Search folder");

		// Perform create node - Search - SaveSearch in nodes
		String nodeName = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, Input.rmu1FullName,
				Input.yesButton);

		session.basicContentSearch(Input.searchString9);
		session.saveSearchInNewNode(SearchName, nodeName);
		session.saveSearchInNewNode(SearchName1, nodeName);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeName);
		saveSearch.getSavedSearchExecuteButton().waitAndClick(10);
		saveSearch.getExecuteContinueBtn().waitAndClick(10);
		saveSearch.verifyStatusByReSearch(SearchName, statusToCheck, 5);
		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", false);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeName);

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 02/28/22 @Modified date:N/A @Modified by:N/A
	 * @Description : To verify as a Reviewer user login, I will be able to search a
	 *              saved query based on search status 'In Progress' under My Saved
	 *              Search folder[RPMXCON-47565] sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void searchFilterBasedOnStatusAsRev() throws InterruptedException, ParseException {

		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String SearchName1 = "SearchName" + Utility.dynamicNameAppender();
		String statusToCheck = "INPROGRESS";
		String highVolumeProject = Input.highVolumeProject;

		// Login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);
		base.selectproject(highVolumeProject);

		base.stepInfo("Test case Id: RPMXCON-47565 Saved Search - Sprint 12");
		base.stepInfo(
				"To verify as a Reviewer user login, I will be able to search a saved query based on search status 'In Progress' under My Saved Search folder");

		// Perform create node - Search - SaveSearch in nodes
		String nodeName = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, Input.rev1FullName,
				Input.yesButton);

		session.basicContentSearch(Input.searchString9);
		session.saveSearchInNewNode(SearchName, nodeName);
		session.saveSearchInNewNode(SearchName1, nodeName);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeName);
		saveSearch.getSavedSearchExecuteButton().waitAndClick(10);
		saveSearch.getExecuteContinueBtn().waitAndClick(10);
		saveSearch.verifyStatusByReSearch(SearchName, statusToCheck, 5);
		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", false);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeName);

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
