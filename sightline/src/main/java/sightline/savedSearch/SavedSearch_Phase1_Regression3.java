package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.Dimension;
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
import pageFactory.BatchRedactionPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Phase1_Regression3 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	MiniDocListPage miniDocListPage;
	SearchTermReportPage searchTerm;
	SchedulesPage schedule;
	public static int purehits;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@DataProvider(name = "UserAndSearchGroup")
	public Object[][] UserAndSearchGroup() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.mySavedSearch },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.mySavedSearch },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "SwitchUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.da1userName, Input.da1password, "DA" }, { Input.pa1userName, Input.pa1password, "PA" } };
		return users;
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
	 * Sprint 1
	 * 
	 * @author Jeevitha Description - Creates the node and uploads the batch
	 *         file.(RPMXCON-57473) Description - Selects the node and uploads the
	 *         batch file.(RPMXCON-57483)
	 * @modified by : Raghuram - changes of new sg creation method
	 */
	@Test(description = "RPMXCON-57473,RPMXCON-57483", groups = { "regression" })
	public void createSearchGroupAndbatchUploadByPA() throws InterruptedException {
		String SearchNamePA = "PA" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case id: RPMXCON-57473, RPMXCON-57483");
		// create new search group
		// saveSearch.createNewSearchGrp(SearchNamePA);
		String new_Node = saveSearch.createSearchGroupAndReturn(SearchNamePA, "PA", "No");
		driver.getWebDriver().navigate().refresh();
		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));

		// Select Node
		driver.getWebDriver().navigate().refresh();
		saveSearch.selectNode1(new_Node);
		System.out.println("Selected Node");
		base.stepInfo("Successufully Selected the Node");

		// upload batch template
		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));
		base.stepInfo("Successfully uploaded the BAtch file");

		System.out.println("Successfully ran for PA user...");
		base.stepInfo("Successfully ran for PA user...");

		// Delete Node
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteNode(Input.mySavedSearch, new_Node, true, true);

		login.logout();

	}

	/**
	 * Sprint 1
	 * 
	 * @author Jeevitha Description - Creates the node and uploads the batch
	 *         file.(RPMXCON-57473) Description - Selects the node and uploads the
	 *         batch file.(RPMXCON-57483)
	 * @modified by : Raghuram - changes of new sg creation method
	 */
	@Test(description = "RPMXCON-57473,RPMXCON-57483", groups = { "regression" })
	public void createSearchGroupAndbatchUploadByRmu() throws InterruptedException {
		String SearchNameRMU = "RMU" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57473, RPMXCON-57483");

		// create new search group
		// saveSearch.createNewSearchGrp(SearchNameRMU);
		String new_Node = saveSearch.createSearchGroupAndReturn(SearchNameRMU, "RMU", "No");
		driver.getWebDriver().navigate().refresh();
		System.out.println("Selected Node");
		base.stepInfo("Created New Search Group");

		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));

		// Select Node
		driver.getWebDriver().navigate().refresh();
		saveSearch.selectNode1(new_Node);

		// upload batch template
		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));
		base.stepInfo("Succefully selected Node and uploaded the batch file");

		System.out.println("Successfully ran for Rmu user...");
		UtilityLog.info("Successfully ran for Rmu user...");

		// Delete Node
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteNode(Input.mySavedSearch, new_Node, true, true);

		login.logout();

	}

	/**
	 * Sprint 1
	 * 
	 * @author Jeevitha
	 * 
	 *         Description - Creates the node and uploads the batch
	 *         file.(RPMXCON-57473) Description - Selects the node and uploads the
	 *         batch file.(RPMXCON-57483)
	 * @modified by : Raghuram - changes of new sg creation method
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57473,RPMXCON-57483", groups = { "regression" })
	public void createSearchGroupAndbatchUploadByRev() throws InterruptedException {
		String saveSearchName = "Rev" + Utility.dynamicNameAppender();

		// Login as a Rev
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Test case id: RPMXCON-57473, RPMXCON-57483");

		// create new search group
		// saveSearch.createNewSearchGrp(saveSearchName);
		String new_Node = saveSearch.createSearchGroupAndReturn(saveSearchName, "Rev", "No");
		driver.getWebDriver().navigate().refresh();

		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));

		// Select Node
		driver.getWebDriver().navigate().refresh();
		saveSearch.selectNode1(new_Node);

		// upload batch template
		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));
		base.stepInfo("Successfully Selected node and Uploaded BAtch file");

		System.out.println("Successfully ran for Rev user...");
		base.stepInfo("Successfully ran for Rev user...");

		// Delete Node
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteNode(Input.mySavedSearch, new_Node, true, true);

		login.logout();
	}

	/**
	 * Sprint-1
	 * 
	 * @author Jeevitha
	 * @throws ParseException
	 * @throws InterruptedException Description:Verify that after modification of
	 *                              any search query under "Shared With Default
	 *                              Security group" from Saved Search Scheduler-
	 *                              Modified Schedule functionality is working
	 *                              proper in Saved searches(RPMXCON-57407 ) As PA
	 */
	@Test(description = "RPMXCON-57407", groups = { "regression" })
	public void verifyScheduleSearchAsPA() throws ParseException, InterruptedException {
		String saveSearchName = "SearchPA" + Utility.dynamicNameAppender();
		SchedulesPage schedule=new SchedulesPage(driver);
		// login as pa
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id: RPMXCON-57407");

		purehits = session.basicContentSearch(Input.searchString1);
		session.saveSearchDefaultTab(saveSearchName);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return session.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		session.getSavedSearch_DefaultSgTab().Click();
		saveSearch.scheduleSavedSearchInMinute(saveSearchName, 2);
		schedule.editScheduledSaveSearch(saveSearchName, 3);

		schedule.verifyScheduledTime(saveSearchName);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteSearch(saveSearchName, Input.shareSearchDefaultSG, "Yes");

		login.logout();

	}

	/**
	 * Sprint-1
	 * 
	 * @author Jeevitha
	 * @throws ParseException
	 * @throws InterruptedException Description:Verify that after modification of
	 *                              any search query under "Shared With Deafult
	 *                              Security group" from Saved Search Scheduler-
	 *                              Modified Schedule functionality is working
	 *                              proper in Saved searches(RPMXCON-57407 ) As RMU
	 */
	@Test(description = "RPMXCON-57407", groups = { "regression" })
	public void verifyScheduleSearchAsRMU() throws ParseException, InterruptedException {
		String saveSearchName1 = "SearchRMU" + Utility.dynamicNameAppender();
		SchedulesPage schedule=new SchedulesPage(driver);
		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57407");

		purehits = session.basicContentSearch(Input.searchString1);
		session.saveSearchDefaultTab(saveSearchName1);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return session.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait60);
		session.getSavedSearch_DefaultSgTab().Click();
		saveSearch.scheduleSavedSearchInMinute(saveSearchName1, 2);
		schedule.editScheduledSaveSearch(saveSearchName1, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(saveSearchName1);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName1);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteSearch(saveSearchName1, Input.shareSearchDefaultSG, "Yes");
		login.logout();

	}

	/**
	 * Sprint 2
	 * 
	 * @author Jeevitha
	 * @throws ParseException
	 * @throws InterruptedException Description:Verify that after modification of
	 *                              any search query under "My Searches" from Saved
	 *                              Search Scheduler - Modified Schedule
	 *                              functionality is working proper in Saved
	 *                              searches (RPMXCON-57411 )
	 */
	@Test(description = "RPMXCON-57411", groups = { "regression" })
	public void verifyScheduleSearchPA() throws ParseException, InterruptedException {
		String saveSearchName = "SearchPA" + Utility.dynamicNameAppender();
		SchedulesPage schedule=new SchedulesPage(driver);
		// login as pa
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id: RPMXCON-57411");

		purehits = session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearchName);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.scheduleSavedSearchInMinute(saveSearchName, 2);
		saveSearch.scheduleSavedSearchInMinute(saveSearchName, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(saveSearchName);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteSearch(saveSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * Sprint 2
	 * 
	 * @author Jeevitha
	 * @throws ParseException
	 * @throws InterruptedException Description:Verify that after modification of
	 *                              any search query under "My Searches" from Saved
	 *                              Search Scheduler - Modified Schedule
	 *                              functionality is working proper in Saved
	 *                              searches (RPMXCON-57411 ) as RMU
	 */
	@Test(description = "RPMXCON-57411", groups = { "regression" })
	public void verifyScheduleSearchRMU() throws ParseException, InterruptedException {
		String saveSearchName = "SearchPA" + Utility.dynamicNameAppender();
		SchedulesPage schedule=new SchedulesPage(driver);
		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57411");

		purehits = session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearchName);

		// Schedule Save search in munutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.scheduleSavedSearchInMinute(saveSearchName, 2);
		saveSearch.scheduleSavedSearchInMinute(saveSearchName, 3);

		schedule.verifyScheduledTime(saveSearchName);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteSearch(saveSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * Sprint-2
	 * 
	 * @author Jeevitha
	 * @throws ParseException
	 * @throws InterruptedException Description:Verify that after modification of
	 *                              any search query under "Shared With Deafult
	 *                              Security group" from Saved Search Scheduler-
	 *                              Modified Schedule functionality as PA is working
	 *                              proper in Saved searches(RPMXCON-57412 )
	 */
	@Test(description = "RPMXCON-57412", groups = { "regression" })
	public void verifyScheduleSearchForDefaultTabPA() throws ParseException, InterruptedException {
		String SearchNamePA = "SearchPA" + Utility.dynamicNameAppender();
		SchedulesPage schedule=new SchedulesPage(driver);
		// login as pa
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id: RPMXCON-57412");

		purehits = session.basicContentSearch(Input.searchString1);
		session.saveSearchDefaultTab(SearchNamePA);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return session.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		session.getSavedSearch_DefaultSgTab().Click();
		saveSearch.scheduleSavedSearchInMinute(SearchNamePA, 2);
		saveSearch.scheduleSavedSearchInMinute(SearchNamePA, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(SearchNamePA);

		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(SearchNamePA);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		saveSearch.deleteSearch(SearchNamePA, Input.shareSearchDefaultSG, "Yes");

		login.logout();

	}

	/**
	 * sprint-2
	 * 
	 * @author Jeevitha
	 * @throws ParseException
	 * @throws InterruptedException
	 * @Description:Verify that after modification of any search query under "Shared
	 *                     With Deafult Security group" from Saved Search Scheduler-
	 *                     Modified Schedule functionality as RMU is working proper
	 *                     in Saved searches(RPMXCON-57412 )
	 */
	@Test(description = "RPMXCON-57412", groups = { "regression" })
	public void verifyScheduleSearchForDefaultTabRMU() throws ParseException, InterruptedException {
		String SearchNameRMU = "SearchRMU" + Utility.dynamicNameAppender();
		SchedulesPage schedule=new SchedulesPage(driver);
		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57412");

		// Search Query and save it in Default Tab
		purehits = session.basicContentSearch(Input.searchString1);
		session.saveSearchDefaultTab(SearchNameRMU);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return session.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		session.getSavedSearch_DefaultSgTab().Click();
		saveSearch.scheduleSavedSearchInMinute(SearchNameRMU, 2);
		saveSearch.scheduleSavedSearchInMinute(SearchNameRMU, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(SearchNameRMU);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(SearchNameRMU);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		saveSearch.deleteSearch(SearchNameRMU, Input.shareSearchDefaultSG, "Yes");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that While editing saved search which has conceptual
	 *              search, slider value(Precision Sensitivity) and Precision
	 *              Sensitivity text box value should display its actual value
	 *              (value set during the searches) [RPMXCON-63580]
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-63580", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyingPrecisionSensitivityValueWhileConfiguringWithrecisionSensitivityValueWhileEditing(
			String username, String password, String fullName) throws Exception {

		String conceptSearch = "search" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-63580 Saved Search");
		base.stepInfo(
				"Verify that While editing Saved serach which has conceptual search, slider value (precision sensitivity) and precision sensitivity text box value should display its actual value (Value set during the searches)");

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);

		// Create New Search Group
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// Conceptual Search
		session.navigateToAdvancedSearchPage();
		String precisionValueBefore = session.verifyingPrecisionValue(true, Input.conceptualSearchString1, true, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(conceptSearch, newNode);

		// selecting the savedSearch and click edit button
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		saveSearch.savesearch_Edit(conceptSearch);

		driver.waitForPageToBeReady();
		session.getModifyASearch().waitAndClick(10);
		String precisionValueAfterEdit = session.verifyingPrecisionValue(false, null, false, false);

		String passMsg = precisionValueBefore
				+ " is the Precision value Before  and After traverse from savedsaerch page is "
				+ precisionValueAfterEdit;
		String failMsg = "Precision value befor and after is not Same";
		base.textCompareEquals(precisionValueAfterEdit, precisionValueBefore, passMsg, failMsg);

		// performing the search and verifying the Search Result Displayed
		session.saveAndReturnPureHitCount();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Nexidia Upgrade - Validate editing/executing non-audio
	 *              searches on . an exising project with having audio documents
	 *              [RPMXCON-47154]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47154", enabled = true, dataProvider = "UserAndSearchGroup", groups = { "regression" })
	public void validateEditingOrExecutingNonaudioSearches(String username, String password, String fullname,
			String rootGroup) throws Exception {
		String search1 = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();

		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullname);

		base.stepInfo("Test case Id: RPMXCON-47154");
		base.stepInfo(
				"Nexidia Upgrade - Validate editing/executing non-audio searches on an exising project with having audio documents");

		String newNode = saveSearch.createSearchGroupAndReturn(rootGroup, "", Input.yesButton);
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNodeUnderGroup(search2, newNode, rootGroup);

		base.selectproject();
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(search1, rootGroup);

		saveSearch.selectSavedSearchTAb(search1, rootGroup, "Yes");
		saveSearch.getSavedSearchEditButton().waitAndClick(3);

		base.waitForElement(session.getNewSearchButton());
		session.verifySessionSearchPage();
		driver.waitForPageToBeReady();

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearchTAb(search1, rootGroup, "Yes");
		base.waitForElement(saveSearch.getCountofDocs());
		String countBeforeExecuting = saveSearch.getCountofDocs().getText();
		String countAfterExecuting = saveSearch.savedSearchExecute_Draft(search1, purehit);

		String passMSg = " Count Before And after execution is same : " + countAfterExecuting;
		String failMsg = " Count Before And after execution is not same : ";
		base.textCompareEquals(countAfterExecuting, countBeforeExecuting, passMSg, failMsg);

		saveSearch.selectNodeUnderSpecificSearchGroup(rootGroup, newNode);
		base.waitTime(1);
		base.waitForElement(saveSearch.getCountofDocs());
		countBeforeExecuting = saveSearch.getCountofDocs().getText();
		saveSearch.performExecute();
		driver.waitForPageToBeReady();
		saveSearch.savedSearch_SearchandSelect(search2, "Yes");
		base.waitForElement(saveSearch.getCountofDocs());
		countAfterExecuting = saveSearch.getCountofDocs().getText();

		base.textCompareEquals(countAfterExecuting, countBeforeExecuting, passMSg, failMsg);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : SA/DA/PA user impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the Tally report
	 *              against My saved searches in PAU role [RPMXCON-57405]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57405", enabled = true, dataProvider = "SwitchUsers", groups = { "regression" })
	public void verifyTAllyReportForSearch(String username, String password, String role) throws InterruptedException {
		String search1PA = "Search" + Utility.dynamicNameAppender();
		String search2PA = "Search" + Utility.dynamicNameAppender();
		String searc3RMU = "Search" + Utility.dynamicNameAppender();
		String search4RMU = "Search" + Utility.dynamicNameAppender();
		String search5REV = "Search" + Utility.dynamicNameAppender();
		String search6REV = "Search" + Utility.dynamicNameAppender();

		TallyPage tally = new TallyPage(driver);
		ReportsPage report = new ReportsPage(driver);

		base.stepInfo("Test case Id: RPMXCON-57405 Savedsearch");
		base.stepInfo(
				"SA/DA/PA user impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Tally report against My saved searches in PAU role");

		// Login as user
		login.loginToSightLine(username, password);
		base.rolesToImp(role, "PA");

		String newNodePA = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search1PA);
		session.saveSearchInNewNode(search2PA, newNodePA);

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searc3RMU);
		session.saveSearchInNewNode(search4RMU, newNodeRMU);

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search5REV);
		session.saveSearchInNewNode(search6REV, newNodeREV);

		// impersonate from REV to RMU
		base.rolesToImp("REV", "RMU");

		// go to tally page check search groups and searches for RMU
		tally.navigateTo_Tallypage();
		tally.getTally_SelectSource().waitAndClick(10);
		tally.getTally_Searches().waitAndClick(10);
		report.checkList(newNodePA, newNodeRMU, newNodeREV, searc3RMU, search5REV, "RMU", null);

		// impersonate from RMU to user
		base.rolesToImp("RMU", "PA");

		// go to tally page check search groups and searches for PA
		tally.navigateTo_Tallypage();
		tally.getTally_SelectSource().waitAndClick(10);
		tally.getTally_Searches().waitAndClick(10);
		report.checkList(newNodePA, newNodeRMU, newNodeREV, searc3RMU, search5REV, "PA", null);
		report.checkdataPresent(search1PA, "PA");

		// getting document count from tally page
		driver.Navigate().refresh();

		String[] searchList = { newNodePA, search1PA };
		tally.SelectSource_MultipleSavedSearch(searchList);
		tally.selectTallyByMetaDataField(Input.metaDataName);
		tally.validateMetaDataFieldName(Input.metaDataName);
		tally.verifyTallyChart();

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : TC05_Verify that When PAU runs a RMU created search
	 *              (WorkProduct(Tags/Folder/Assignment)) in PAU role then search
	 *              returns documents Under \"Count of Results\" column on \"Saved
	 *              Search Screen\" [RPMXCON-49836]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49836", enabled = true, groups = { "regression" })
	public void verifySearchDocOfRmu() throws Exception {
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String tagName = "TagName" + Utility.dynamicNameAppender();
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String savedsearcTaghName = "SavedSearchTag" + Utility.dynamicNameAppender();
		String savedSearchFolderName = "SavedSearchFolder" + Utility.dynamicNameAppender();
		String savedSearchAssignmentName = "SavedSearchAssignment" + Utility.dynamicNameAppender();

		AssignmentsPage assign = new AssignmentsPage(driver);

		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id: RPMXCON-49836 Saved Search");
		base.stepInfo(
				"TC05_Verify that When PAU runs a RMU created search (WorkProduct(Tags/Folder/Assignment)) in PAU role then search returns documents Under \"Count of Results\" column on \"Saved Search Screen\" ");

		// Create Search Group
		String newNodeRMU = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// creating tag ,folder
		session.basicContentSearch(Input.searchString1);
		session.bulkTag(tagName);
		session.bulkFolderWithOutHitADD(folderName);

		// creating the assignment
		session.Removedocsfromresults();
		session.addNewSearch();
		session.multipleBasicContentSearch(Input.TallySearch);
		session.bulkAssign();
		assign.assignmentCreation(assignmentName, Input.codeFormName);

		// creating the savedSearch containing tag
		base.selectproject();
		session.switchToWorkproduct();
		session.selectTagInASwp(tagName);
		int tagPureHitCount = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedsearcTaghName, newNodeRMU);

		// creating the savedSerach containing folder
		base.selectproject();
		session.switchToWorkproduct();
		session.selectFolderInASwp(folderName);
		int folderPureHitCount = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearchFolderName, newNodeRMU);

		// creating the savedSerach containing assignment
		base.selectproject();
		session.switchToWorkproduct();
		int assignmentPureHitCount = session.selectAssignmentInWPSWs(assignmentName);
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearchAssignmentName, newNodeRMU);

		// sharing the savedSearch with security group
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNodeRMU);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeRMU, Input.shareSearchDefaultSG);

		// logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// verifying the count of results for savedSearch containing the tag
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.shareSearchDefaultSG, newNodeRMU);
		saveSearch.performExecute();

		// Verify Search Status And Count in all nodes
		List<String> newNodeList = new ArrayList<>();
		newNodeList.add(newNodeRMU);
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		nodeSearchpair.put(newNodeRMU, savedsearcTaghName);

		saveSearch.verifyStatusAndCountInAllChildNode(Input.shareSearchDefaultSG, newNodeList, 0, nodeSearchpair);

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Batch Upload : Verify that conceptually similar counts match
	 *              with expected counts post modify and run. [RPMXCON-49264]
	 * @param UserName
	 * @param PassWord
	 * @param fullName
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49264", enabled = true, groups = { "regression" }, dataProvider = "AllTheUsers")
	public void batchUploadAndVerifyConceptCount(String UserName, String PassWord, String fullName)
			throws InterruptedException {

		String headername_1 = "Search Name";
		String headername_2 = "Conceptually Similar Count";
		String file = saveSearch.renameFile(Input.SearchBatchFile);

		// login as user
		login.loginToSightLine(UserName, PassWord);
		base.stepInfo("Loggedin As : " + fullName);

		base.stepInfo("Test case id: RPMXCON-49264 Savedsearch");
		base.stepInfo(
				"Batch Upload : Verify that conceptually similar counts match with expected counts post modify and run.");

		// performing batch upload functionality using attached excel file
		saveSearch.navigateToSSPage();
		saveSearch.uploadWPBatchFile_New(file, Input.SearchBatchFile);
		base.stepInfo("Successfully uploaded the BAtch file");

		// collecting list of search name from SavedSearch DataTable
		List<String> SearchNamelist = saveSearch.getListFromSavedSearchTable(headername_1);

		// enabling the conceptually similar count in savedsearch datatable
		saveSearch.checkHideandShowFunction(headername_2);
		saveSearch.getFieldHeader(headername_2).ScrollTo();
		driver.waitForPageToBeReady();

		// collecting list of conceptually similar count from SavedSearch DataTable
		List<String> conceptuallySimilarCountList = saveSearch.getListFromSavedSearchTable(headername_2);
		driver.waitForPageToBeReady();

		// verifying Conceptually Similar Count In SessionSearche With Count In
		// SavedSearch for all the SavedSearch Terms
		session.verifyingConceptuallySimilarCountInSessionSearcheWithCountInSavedSearch(SearchNamelist,
				conceptuallySimilarCountList);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 2/22/22 Modified date:N/A Modified by:N/A
	 * @Description: Verify that relevant error message appears when user modifies
	 *               (Rename with xyz)- batch Search "column header" and tries to
	 *               upload same file in Saved Search Screen. [RPMXCON-48535]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48535", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-48540", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-48541", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-48123", enabled = true, dataProvider = "rmuAndRev", groups = { "regression" })
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
	@Test(description = "RPMXCON-48124", enabled = true, dataProvider = "rmuAndRev", groups = { "regression" })
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
	@Test(description = "RPMXCON-48122", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-48543", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-48542", enabled = true, groups = { "regression" })
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
	 *         Query) onto an existing saved search that is progresaveSearch.
	 *         RPMXCON-48914 Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48914", enabled = true, groups = { "regression" })
	public void executionErrorInProgress() throws InterruptedException, ParseException {

		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String highVolumeProject = Input.highVolumeProject;

		base.stepInfo("Test case Id: RPMXCON-48914 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that user is not allowed to save a session search(Complete Query) onto an existing saved search that is progresaveSearch.");
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
	@Test(description = "RPMXCON-48135", enabled = true, groups = { "regression" })
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
		userManagement.saveSecurityGroup();

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
	@Test(description = "RPMXCON-48129", enabled = true, groups = { "regression" })
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
		userManagement.saveSecurityGroup();

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
	@Test(description = "RPMXCON-48131", enabled = true, groups = { "regression" })
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
		userManagement.saveSecurityGroup();

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
	@Test(description = "RPMXCON-48488", enabled = true, groups = { "regression" })
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
	 *         onto an existing saved search that is progresaveSearch. RPMXCON-48912
	 *         Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48912", enabled = true, groups = { "regression" })
	public void executionErrorInProgressDraft() throws InterruptedException, ParseException {

		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String highVolumeProject = Input.highVolumeProject;

		base.stepInfo("Test case Id: RPMXCON-48912 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that user is not able to save a session search (Draft Query) onto an existing saved search that is progresaveSearch.");
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
	@Test(description = "RPMXCON-48611", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
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
		base.stepInfo("Clicked Execute button");
		driver.waitForPageToBeReady();
		base.CloseSuccessMsgpopup();

		// DocVIew
		saveSearch.savedSearch_SearchandSelect(savedSearchName, "Yes");
		base.waitForElement(saveSearch.getSearchStatus(savedSearchName, statusToCheck));
		base.stepInfo("Search is in " + statusToCheck + " status before clicking DocView");
		saveSearch.docViewFromSS("View in DocView");
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
	@Test(description = "RPMXCON-48612", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
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
		saveSearch.docViewFromSS("View in DocView");
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
	@Test(description = "RPMXCON-48132", enabled = true, groups = { "regression" })
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
		userManagement.saveSecurityGroup();

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
	@Test(description = "RPMXCON-48130", enabled = true, groups = { "regression" })
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
		userManagement.saveSecurityGroup();

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
	@Test(description = "RPMXCON-48590", enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-47564", enabled = true, groups = { "regression" })
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
		saveSearch.getSavedSearchExecuteButton().javascriptclick(saveSearch.getSavedSearchExecuteButton());
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
	@Test(description = "RPMXCON-47565", enabled = true, groups = { "regression" })
	public void searchBasedOnStatusAsRev() throws InterruptedException, ParseException {

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

	/**
	 * @Author Raghuram @Date: 03/01/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify status on Saved Search Screen when user saves an Basic
	 *              search query[RPMXCON-48475] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48475", enabled = true, groups = { "regression" })
	public void saveSearchScreenOnBasicQueryWithBulkDatas() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.bulkSearchSting1;
		int Bgcount;

		base.stepInfo("Test case Id: RPMXCON-48475  Saved Search Sprint 13");
		base.stepInfo("Verify status on Saved Search Screen when user saves an Basic search query");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(searchString, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Save Search
		session.saveSearch(searchName);

		// Verify Status based on Count
		base.stepInfo("Verifying status to be In Progress until all counts are  available for the Basic search.");
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.verifyStatusBasedOnCount(searchName, "flow-1", 0);

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);

		// Verify SavedSearch Status once notification arises
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.docResultCOuntCHeck(searchName);
		base.stepInfo(
				"Verifing the status on Saved Search Screen for the above saved Search after receiving task completion notification");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/01/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify status on Saved Search Screen when user saves an Basic
	 *              search query[RPMXCON-48452] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48452", enabled = true, groups = { "regression" })
	public void verifyInprogressStatusWithTileSpinningForBasicSearch() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "INPROGRESS";
		String searchString = Input.bulkSearchSting1;
		int Bgcount;

		base.stepInfo("Test case Id: RPMXCON-48452  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that In Progress status appears in Saved Search Screen when user saved a Basic search, for which only pure hits are available on the Basic search.");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(searchString, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Save Search
		session.saveSearch(searchName);

		// Verify Status based on Count
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");

		base.stepInfo(
				"Verifing that In Progress status appears in Saved Search Screen when user saved a Basic search, for which only pure hits are available on the Basic search.");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Jayanthi
	 * @Description :Verify that "Count" display as BLANK in conceptual column in
	 *              Saved Search Screen when user is not triggered Conceptually
	 *              Similar count but the Basic search is saved as a saved search.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48901", enabled = true, groups = { "regression" })
	public void verifyConceptuallYColumn_BasicSearch() throws Exception {
		String Search1 = "Search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";
		String passMsg = "Conceptual Column Count is BLANK which is expected.";
		String failMsg = "Conceptual Column Count is Not BLANK";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(Input.highVolumeProject);
		base.stepInfo("Test case Id: RPMXCON-48901 Saved Search");
		base.stepInfo(
				"Verify that \"Count\" display as BLANK in conceptual column in Saved Search Screen when user is not triggered Conceptually "
						+ "Similar count but the Basic search is saved as a saved search.");

		// Basic Search
		session.basicContentDraftSearch(Input.searchString9);
		session.SearchBtnAction();
		// Handling when Search goes background
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.returnPurehitCount();
		session.saveSearch(Search1);

		// Verify Conceptually Column
		saveSearch.navigateToSavedSearchPage();
		saveSearch.verifyStatusByReSearch(Search1, "COMPLETED", 2);
		base.stepInfo("Verifying whehter conceptually similar column in saved search result table"
				+ " is displayed and if not displayed Adding the column using Show And Hide filter.");
		String count = saveSearch.ApplyShowAndHideFilter(conceptually, Search1);
		base.textCompareEquals(count, Input.TextEmpty, passMsg, failMsg);

		// Delete Search
		saveSearch.deleteSearch(Search1, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that spreadsheets uploaded to the project database (even
	 *              in different Security Groups) with the same name will throw an
	 *              error message in the UI[RPMXCON-48297]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48297", enabled = true, groups = { "regression" })
	public void verifyUploadingSameBatchFileWillThrowError() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String file = saveSearch.renameFile(Input.WPbatchFile);
		String fileName = file.substring(file.indexOf(""), file.indexOf("."));
		String batchNodeToCheck = fileName + "_" + 1 + "_Sheet" + 1;
		String fileFormat = ".xlsx";

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48297 Saved Search");
		base.stepInfo(
				"Verify that spreadsheets uploaded to the project database (even in different Security Groups) with the same name will throw an error message in the UIVerify that spreadsheets uploaded to the project database (even in different Security Groups) with the same name will throw an error message in the UI");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected SG : " + Input.securityGroup);

		// Upload batch File succesfully
		saveSearch.navigateToSavedSearchPage();
		saveSearch.uploadWPBatchFile_New(file, Input.WPbatchFile);

		softAssertion.assertTrue(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Error when you try uploading same file
		saveSearch.uploadBatchFile_D(Input.WPbatchFile, file, false);
		saveSearch.getSubmitToUpload().waitAndClick(10);
		saveSearch.verifyBatchUploadMessage("SameFile", false);

		// Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected SG : " + securityGroup);

		// Error when you try uploading same file in Other SG
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.uploadBatchFile_D(Input.WPbatchFile, file, false);
		saveSearch.getSubmitToUpload().waitAndClick(10);
		saveSearch.verifyBatchUploadMessage("SameFile", false);

		// Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected SG : " + Input.securityGroup);

		// Delete uploaded batch file
		saveSearch.navigateToSavedSearchPage();
		saveSearch.deleteUplodedBatchFile(file);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : RMU User - Verify that User can renames existing search Group
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48126]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48126", enabled = true, groups = { "regression" })
	public void verifyRenameExistingSearchGroupAsRmu() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48126 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can renames existing search Group under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create Node In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// create Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// Rename Node In Default SG
		String renamedNode = saveSearch.renameSearchGroup(node2);

		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String passMsg = renamedNode + " : is Not Available";

		// verify Renamed Node In Other SG
		saveSearch.navigateToSavedSearchPage();
		base.waitTillElemetToBeClickable(saveSearch.getSavedSearchGroupName(Input.mySavedSearch));
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.selectNode1(node1);

		Element RenamedNodeInDef = saveSearch.getSavedSearchNodeWithRespectiveSG(securityTab, renamedNode);
		base.ValidateElement_Absence(RenamedNodeInDef, passMsg);

		// verify Renamed Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, renamedNode);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, renamedNode);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that after the spreadsheet is parsed and search groups
	 *              are created per the spec, that all newly provisioned search
	 *              groups are executed (all new saved searches will be run)
	 *              [RPMXCON-48300]
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48300", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyAllSearchGroupsAreExecuted(String username, String password, String fullname) throws Exception {
		String file = saveSearch.renameFile(Input.WPbatchFile);
		String fileName = file.substring(file.indexOf(""), file.indexOf("."));
		String batchNodeToCheck = fileName + "_" + 1 + "_Sheet" + 1;
		String statusToCheck = "COMPLETED";

		// Login
		login.loginToSightLine(username, password);

		base.stepInfo("Test case Id: RPMXCON-48300 Saved Search");
		base.stepInfo(
				"Verify that after the spreadsheet is parsed and search groups are created per the spec, that all newly provisioned search groups are executed (all new saved searches will be run)");

		// Upload batch File succesfully
		saveSearch.navigateToSavedSearchPage();
		saveSearch.uploadWPBatchFile_New(file, Input.WPbatchFile);

		// verify FileName
		softAssertion.assertTrue(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches uploaded in Saved search screen.");
		softAssertion.assertAll();

		// verify search execution
		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", true);

		// Delete uploaded batch file
		saveSearch.navigateToSavedSearchPage();
		saveSearch.deleteUplodedBatchFile(file);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : TC07_Verify that application does not display any warnings
	 *              when a user executes a search group under "Shared with <Security
	 *              Group>" which contains only Basic Search(es) in Draft state on
	 *              Saved Search Screen.[RPMXCON-49827]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49827", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verifyAppDoesnotDisplayWarning(String username, String password, String fullname) throws Exception {

		String Search1 = "search" + Utility.dynamicNameAppender();
		String failMsg = "Last Status of Search is Not as Expected";

		// Login as PA
		login.loginToSightLine(username, password);

		base.stepInfo("Test case Id: RPMXCON-49827  Saved Search");
		base.stepInfo(
				"TC07_Verify that application does not display any warnings when a user executes a search group under \"Shared with <Security Group>\" which contains only Basic Search(es) in Draft state on Saved Search Screen.");

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// Draft Query
		session.navigateToSessionSearchPageURL();
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.saveSearchInNewNode(Search1, newNode);

		// Share Search group to Default SG
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(newNode);
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, Input.shareSearchDefaultSG);

		// verify Search Status In Default
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.shareSearchDefaultSG, newNode);
		base.waitForElement(saveSearch.getSavedSearchStatus(Search1));
		String searchStatus = saveSearch.getSavedSearchStatus(Search1).getText();

		String passMsg = "Last Status of Search is : " + searchStatus;
		base.textCompareEquals(searchStatus, "DRAFT", passMsg, failMsg);

		// execute Search group from default TAb
		saveSearch.savedSearchExecute_SearchGRoup(Search1, purehit);
		base.waitForElement(saveSearch.getSavedSearchStatus(Search1));
		String searchStatusafter = saveSearch.getSavedSearchStatus(Search1).getText();

		// verify status After execute
		String passMsgAfter = "Last Status of Search is : " + searchStatusafter;
		base.textCompareEquals(searchStatusafter, "COMPLETED", searchStatusafter, failMsg);

		// deleting the searches
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNode);

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/02/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify status on Saved Search Screen when user saves an
	 *              Advanced search query[RPMXCON-48476] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48476", enabled = true, groups = { "regression" })
	public void saveSearchScreenOnAdvancedQueryWithBulkDatas() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.bulkSearchSting1;
		int Bgcount;

		base.stepInfo("Test case Id: RPMXCON-48476  Saved Search Sprint 13");
		base.stepInfo("Verify status on Saved Search Screen when user saves an Advanced search query");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.advancedContentSearchWithSearchChanges(searchString, "No");
		session.SearchBtnAction();
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Save Search
		session.saveSearch(searchName);

		// Verify Status based on Count
		base.stepInfo("Verifying status to be In Progress until all counts are  available for the Advanced search.");
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.verifyStatusBasedOnCount(searchName, "flow-1", 0);

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);

		// Verify SavedSearch Status once notification arises
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.docResultCOuntCHeck(searchName);
		base.stepInfo(
				"Verifing the status on Saved Search Screen for the above saved Search after receiving task completion notification");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/02/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that "In Progress" status appears in Saved Search
	 *              Screen when user saved a Advanced search, for which only pure
	 *              hits are available on the Advanced search.[RPMXCON-48453] sprint
	 *              13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48453", enabled = true, groups = { "regression" })
	public void verifyInprogressStatusWithTileSpinningForAdvancedSearch() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "INPROGRESS";
		String searchString = Input.bulkSearchSting1;
		int Bgcount;

		base.stepInfo("Test case Id: RPMXCON-48453  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that In Progress status appears in Saved Search Screen when user saved a Advanced search, for which only pure hits are available on the Advanced search.");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.advancedContentSearchWithSearchChanges(searchString, "No");
		session.SearchBtnAction();
		session.handleWhenAllResultsBtnInUncertainPopup();

		// Save Search
		session.saveSearch(searchName);
		session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Verify Status based on Count
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");

		base.stepInfo(
				"Verifing that In Progress status appears in Saved Search Screen when user saved a Advanced search, for which only pure hits are available on the Advanced search.");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/03/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Overwriting saved searches - User should not be allowed to
	 *              overwrite the search in In-Progress status.[RPMXCON-48948]
	 *              sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48948", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void overWriteNotAllowedInProgressState(String username, String password, String fullName)
			throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "INPROGRESS";
		String searchString = Input.searchString9;

		base.stepInfo("Test case Id: RPMXCON-48948  Saved Search Sprint 13");
		base.stepInfo(
				"Overwriting saved searches - User should not be allowed to overwrite the search in In-Progress status");
		base.stepInfo("Flow can only be done for inputs/projects with bulk data");

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);
		base.selectproject(highVolumeProject);

		// Perform Search and SaveSearch
		session.basicContentSearch(searchString);
		session.saveSearch(searchName);

		// Execute
		base.stepInfo("Select an existing saved search that is progress and try to Save it");
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.verifyStatusByReSearch(searchName, "COMPLETED", 5);
		saveSearch.getSavedSearchExecuteButton().Click();

		// Verify Overwrite
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		saveSearch.getSavedSearchEditButton().waitAndClick(2);
		session.saveAsOverwrittenSearch(Input.mySavedSearch, searchName, "First", "ExecutionErrorInProgress", "", null);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Raghuram A Description : Verify that application displays all
	 *         documents that are in the aggregate results set of all child search
	 *         groups "My Saved Search" and searches when User performs Export with
	 *         Child Search groups(RPMXCON-48920)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48920", enabled = true, groups = { "regression" })
	public void verifyExportDocsWithHierarchialNodes() throws InterruptedException {

		ReportsPage report = new ReportsPage(driver);

		int noOfNodesToCreate = 6, nodeIndex = 0;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		String nodeToSelect;
		int Bgcount;

		base.stepInfo("Test case Id: RPMXCON-48920 - Saved Search Sprint 13");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User performs Export with Child Search groups");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		nodeToSelect= newNodeList.get(nodeIndex);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// To Pick Expected Aggregate count
		session.selectSavedsearchInASWp(nodeToSelect);
		session.SearchBtnAction();
		int purehit = session.returnPurehitCount();

		base.stepInfo("-------Pre-requesties completed--------");
		base.stepInfo("Expected aggregate purehit count from Export : " + purehit);

		// Select Parent Node
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeToSelect);
		base.stepInfo("Parent Node Selected : " + nodeToSelect);

		// Verify Export
		base.stepInfo("Initiate Export");
		saveSearch.getSavedSearchExportButton().Click();
		base.waitForElement(saveSearch.getExportPopup());
		report.customDataReportMethodExport("", false);
		driver.waitForPageToBeReady();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);

		// Download report
		base.stepInfo("Initiate File Download");
		report.downLoadReport();
		base.stepInfo("File Downloaded");

		base.stepInfo(
				"Should show all documents that are in the aggregate results set of all child search groups and searches in Exported file");
		int countToCompare = saveSearch.fileVerificationSpecificMethod();
		base.stepInfo("Document count from the export : " + countToCompare);
		base.digitCompareEquals(purehit, countToCompare,
				"Exported file lists all tall documents that are in the aggregate results set of all child search groups and searches",
				"Purehit and File count doesn't match");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/03/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Overwriting an existing Saved Search with a new query in
	 *              In-Progress status[RPMXCON-49052] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49052", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void overWriteNewSSInProgressState(String username, String password, String fullName)
			throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.bulkSearchSting1;
		String searchStringC = Input.testData1;

		base.stepInfo("Test case Id: RPMXCON-49052  Saved Search Sprint 13");
		base.stepInfo("Overwriting an existing Saved Search with a new query in In-Progress status");
		base.stepInfo("Flow can only be done for inputs/projects with bulk data");

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);
		base.selectproject(highVolumeProject);

		// Perform Search and SaveSearch
		session.basicContentSearch(searchStringC);
		session.saveSearch(searchName);

		// Verify SavedSearch in in COmpleted State
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		String initialDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Initial Count : " + initialDocCount);
		base.selectproject(highVolumeProject);

		// Search and Overwrite SavedSearch
		session.basicContentDraftSearch(searchString);
		session.SearchBtnAction();

		// Handling when Search goes background
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, searchName, "First", "Success", "", null);

		// Verify Tile Spinning
		base.stepInfo(
				"Verifying for one or more related tiles are still spinning - to ensure saved search while it's in inprogress state");
		session.verifyTileSpinning();

		int overWrittenPureHit = session.returnPurehitCount();
		base.stepInfo("Overwritten Count from Search : " + overWrittenPureHit);

		// Overwritten verification
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "No");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 7);
		String updatedDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Overwritten Count from SavedSearch : " + updatedDocCount);

		base.textCompareNotEquals(initialDocCount, updatedDocCount, "SavedSearch Overwritten and updated",
				"Overwritten failed");

		base.textCompareEquals(Integer.toString(overWrittenPureHit), updatedDocCount,
				"SavedSearch Overwritten and count is updated", "Overwritten failed");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 02/22/22 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of all child search groups "My Saved Search"
	 *         and searches when User Navigate Child Search groups to DocView.-
	 *         RPMXCON-48915 Sprint 12
	 */
	@Test(description = "RPMXCON-48915", enabled = true, groups = { "regression" })
	public void verifyDocsDisplayAndNavigatingToDocviewP() throws Exception {

		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		int noOfNodesToCreate = 6, nodeIndex = 0;
		String executionCountSuccessMsg = "Aggregate result matches with the count.";
		String executionCountFailureMsg = "Count Mismatches";
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		String nodeToSelect;

		base.stepInfo("Test case Id: RPMXCON-48915 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Navigate Child Search groups to DocView.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		// Calculate the unique doc count for the respective searches
		int aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		base.stepInfo("Aggregate count : " + aggregateHitCount);
		base.selectproject();

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		nodeToSelect = newNodeList.get(nodeIndex);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();
		base.stepInfo("Root node selected : " + nodeIndex);
		saveSearch.selectNode1(nodeToSelect);
		saveSearch.docViewFromSS("View in DocView");

		// Load latency Verification
		Element loadingElement = session.getspinningWheel();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
		base.stepInfo("Navigated to DocView Page : " + currentUrl);

		// Main method
		miniDocListPage = new MiniDocListPage(driver);
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		System.out.println("Size : " + documentSize);
		base.stepInfo("Available documents in DocView page : " + sizeofList);

		base.digitCompareEquals(aggregateHitCount, Integer.parseInt(documentSize), executionCountSuccessMsg,
				executionCountFailureMsg);

		// Delete Search Group
		base.stepInfo("Initiating Delete SearchGroup");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @Author Jeevitha @Date: 03/03/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that status and all counts gets updated in saved search
	 *              if a session search is saved when one or more related tiles are
	 *              still loading[RPMXCON-48909] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48909", enabled = true, groups = { "regression" })
	public void statusAndCountBasedonSessionSavedSearch() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.bulkSearchSting1;
		String nearDupe = "Near Duplicate Count";

		base.stepInfo("Test case Id: RPMXCON-48909  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that status and all counts gets updated in saved search if a session search is saved when one or more related tiles are still loading");
		base.stepInfo("Flow can only be done for inputs/projects with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Search and Overwrite SavedSearch
		session.basicContentDraftSearch(searchString);
		session.SearchBtnAction();

		// Handling when Search goes background
		session.handleWhenAllResultsBtnInUncertainPopup();
		int pureHit = session.returnPurehitCount();
		base.stepInfo("Purehit : " + pureHit);

		// Verify Tile Spinning and Save Search
		session.verifyTileSpinning();
		session.saveSearch(searchName);

		// logout
		login.logout();
		base.stepInfo("Logged out from current user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("re-Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Verify Status and Count
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		base.stepInfo("Verify that status and count the saved search");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		String updatedDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Updated Count from SavedSearch : " + updatedDocCount);
		base.textCompareEquals(Integer.toString(pureHit), updatedDocCount, "SavedSearch count updated", "count failed");
		saveSearch.getDocCountAndStatusOfBatch(searchName, nearDupe, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Jeevitha @Date: 03/03/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that status and count gets updated in saved search When
	 *              User Saved the In Progress Search execution and User log out and
	 *              relogin to Saved Search Screen [RPMXCON-48906] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48906", enabled = true, groups = { "regression" })
	public void statusAndCountBasedonSessionAdvSavedSearch() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.bulkSearchSting1;
		String nearDupe = "Near Duplicate Count";

		base.stepInfo("Test case Id: RPMXCON-48906  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that status and count gets updated in saved search When User Saved the In Progress Search execution and User log out and relogin to Saved Search Screen");
		base.stepInfo("Flow can only be done for inputs/projects with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Search and Overwrite SavedSearch
		session.navigateToSessionSearchPageURL();
		session.advancedContentSearchWithSearchChanges(searchString, "No");
		session.SearchBtnAction();

		// Handling when Search goes background
		session.handleWhenAllResultsBtnInUncertainPopup();
		int pureHit = session.returnPurehitCount();
		base.stepInfo("Purehit : " + pureHit);

		// Verify Tile Spinning and Save Search
		session.verifyTileSpinning();
		session.saveSearch(searchName);

		// logout
		login.logout();
		base.stepInfo("Logged out from current user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("re-Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Verify Status and Count
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		base.stepInfo("Verify that status and count the saved search");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		String updatedDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Updated Count from SavedSearch : " + updatedDocCount);
		base.textCompareEquals(Integer.toString(pureHit), updatedDocCount, "SavedSearch count updated", "count failed");
		saveSearch.getDocCountAndStatusOfBatch(searchName, nearDupe, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of all child search groups "My Saved
	 *              Search" and searches when User performs Execute option with
	 *              Child Search groups[RPMXCON-48919]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48919", enabled = true, groups = { "regression" })
	public void verifyApplicationDisplaysAllDocAfterExecute() throws Exception {
		int noOfNodesToCreate = 6, nodeIndex = 0;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		String nodeToSelect;

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48919  Saved Search");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User performs Execute option with Child Search groups");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		nodeToSelect = newNodeList.get(nodeIndex);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		base.stepInfo("-------Pre-requesties completed--------");

		// execute
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(newNodeList.get(0));
		Element executeBtnStatus = saveSearch.getSavedSearchExecuteButton();
		saveSearch.checkButtonEnabled(executeBtnStatus, "Should be Enabled", "Execute");
		saveSearch.performExecute();

		// Verify Search Status And Count in all nodes
		saveSearch.verifyStatusAndCountInAllChildNode(Input.mySavedSearch, newNodeList, 0, nodeSearchpair);

		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that After adding Saved Query - application displays
	 *              all documents that are in the aggregate results - When User
	 *              performs Execute option with Child Search groups[RPMXCON-49068]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49068", enabled = true, groups = { "regression" })
	public void verifyAfterAddingSavedQuery() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		int noOfNodesToCreate = 6, nodeIndex = 0;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		String nodeToSelect;

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-49068  Saved Search");
		base.stepInfo(
				"Verify that After adding Saved Query - application displays all documents that are in the aggregate results - When User performs Execute option with Child Search groups");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		nodeToSelect = newNodeList.get(nodeIndex);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in nodes
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		base.stepInfo("-------Pre-requesties completed--------");

		// Save query again in child node
		session.multipleBasicContentSearch(Input.searchString2);
		session.saveSearchInNodewithChildNode(searchName, newNodeList.get(1));

		// execute parent node
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(newNodeList.get(0));
		Element executeBtnStatus = saveSearch.getSavedSearchExecuteButton();
		saveSearch.checkButtonEnabled(executeBtnStatus, "Should be Enabled", "Execute");
		saveSearch.performExecute();

		// Verify Search Status And Count in all nodes
		saveSearch.verifyStatusAndCountInAllChildNode(Input.mySavedSearch, newNodeList, 0, nodeSearchpair);

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/08/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that status and count gets updated in saved search
	 *              where a session search is still spinning for one or more related
	 *              tiles, then the user saves it, and logs out before the queries
	 *              are complete. [RPMXCON-48908] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48908", enabled = true, groups = { "regression" })
	public void statusAndCountBasedonAdvSessionSavedSearch() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "searchAdv" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String nearDupe = "Near Duplicate Count";
		String searchString = Input.bulkSearchSting1;

		base.stepInfo("Test case Id: RPMXCON-48908  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that status and count gets updated in saved search where a session search is still spinning for one or more related tiles, then the user saves it, and logs out before the queries are complete.");
		base.stepInfo("Flow can only be done for inputs/projects with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Search and Overwrite SavedSearch
		session.navigateToSessionSearchPageURL();
		session.advancedContentSearchWithSearchChanges(searchString, "No");
		session.SearchBtnAction();

		// Handling when Search goes background
		session.handleWhenAllResultsBtnInUncertainPopup();
		int pureHit = session.returnPurehitCount();
		base.stepInfo("Purehit : " + pureHit);

		// Verify Tile Spinning and Save Search
		session.verifyTileSpinning();
		session.saveSearchadvanced(searchName);

		// logout
		login.logout();
		base.stepInfo("Logged out from current user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("re-Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Verify Status and Count
		saveSearch.navigateToSavedSearchPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		base.stepInfo("Verify that status and count the saved search");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		String updatedDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Updated Count from SavedSearch : " + updatedDocCount);
		base.textCompareEquals(Integer.toString(pureHit), updatedDocCount, "SavedSearch count updated", "count failed");
		saveSearch.getDocCountAndStatusOfBatch(searchName, nearDupe, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram A @Date: 03/08/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that status and count gets updated(Modified) in saved
	 *              search When User Saved the In Progress Search execution and User
	 *              log out and relogin to Saved Search Screen[RPMXCON-48907] sprint
	 *              13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48907", enabled = true, groups = { "regression" })
	public void statusAndCountBasedonEditSessionSavedSearch() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.searchString1;
		String modifyString = Input.bulkSearchSting1;
		String nearDupe = "Near Duplicate Count";

		base.stepInfo("Test case Id: RPMXCON-48907  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that status and count gets updated(Modified) in saved search When User Saved the In Progress Search execution and User log out and relogin to Saved Search Screen");
		base.stepInfo("Flow can only be done for inputs/projects with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Search and Save Search
		session.basicContentSearch(searchString);
		session.saveSearch(searchName);

		// Verify Status and Count
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.modifySearchTextArea(1, searchString, modifyString, "Save");
		session.SearchBtnAction();

		// Handling when Search goes background
		session.handleWhenAllResultsBtnInUncertainPopup();
		int pureHit = session.returnPurehitCount();
		base.stepInfo("Purehit : " + pureHit);

		// Verify Tile Spinning and Save Search
		session.verifyTileSpinning("Search", 3);
		session.saveAsOverwrittenSearch(Input.mySavedSearch, searchName, "First", "Success", "", null);

		// logout
		login.logout();
		base.stepInfo("Logged out from current user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("re-Logged in as : " + Input.pa1FullName);
		base.selectproject(highVolumeProject);

		// Verify Status and Count
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		base.stepInfo("Verify that status and count the saved search");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		String updatedDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Updated Count from SavedSearch : " + updatedDocCount);
		base.textCompareEquals(Integer.toString(pureHit), updatedDocCount, "SavedSearch count updated", "count failed");
		saveSearch.getDocCountAndStatusOfBatch(searchName, nearDupe, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/09/22 @Modified date:N/A @Modified by:N/A
	 * @Description :Verify that user can save a Basic search, for which only pure
	 *              hits are available and the wheels are still spinning for one or
	 *              more related tiles on the Basic search. [RPMXCON-48450] sprint
	 *              13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48450", enabled = true, groups = { "regression" })
	public void saveSearchScreenOnBasicQueryWithBulkDatasSave() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String searchString = Input.bulkSearchSting1;

		base.stepInfo("Test case Id: RPMXCON-48450  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that user can save a Basic search, for which only pure hits are available and the wheels are still spinning for one or more related tiles on the Basic search.");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// Basic Search
		session.navigateToSessionSearchPageURL();
		base.stepInfo("Perform Basic Search");
		session.basicContentSearchWithSaveChanges(searchString, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Save Search
		session.saveSearch(searchName);

		// Verify Status based on Count
		base.stepInfo(
				"Verify that user can save a Basic search, for which only pure hits are available and the wheels are still spinning for one or more related tiles on the Basic search.");
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		base.passedStep(
				"User able to save a Basic search, for which only pure hits are available and the wheels are still spinning for one or more related tiles on the Basic search.  ");

		// Delete Search
		base.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/08/22 @Modified date:N/A @Modified by:N/A
	 * @Description :Verify that user can save a Advanced search, for which only
	 *              pure hits are available and the wheels are still spinning for
	 *              one or more related tiles on the Advanced search.
	 *              [RPMXCON-48451] sprint 13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48451", enabled = true, groups = { "regression" })
	public void saveSearchScreenOnAdvQueryWithBulkDatasSave() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String searchString = Input.bulkSearchSting1;

		base.stepInfo("Test case Id: RPMXCON-48451  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that user can save a Advanced search, for which only pure hits are available and the wheels are still spinning for one or more related tiles on the Advanced search.");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// AdvanceSearch
		session.navigateToSessionSearchPageURL();
		base.stepInfo("Perform Advance Search");
		session.advancedContentSearchWithSearchChanges(searchString, "No");
		session.SearchBtnAction();
		session.handleWhenAllResultsBtnInUncertainPopup();
		session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Save Search
		session.saveSearch(searchName);

		// Verify Status based on Count
		base.stepInfo(
				"Verify that user can save a Advanced search, for which only pure hits are available and the wheels are still spinning for one or more related tiles on the Advanced search.");
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		base.passedStep(
				"User able to save a Advanced search, for which only pure hits are available and the wheels are still spinning for one or more related tiles on the Advanced search.  ");

		// Delete Search
		base.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * Author : Jeevitha
	 * 
	 * @TestCase id : RPMXCON-48631
	 * @Description : Verify on selecting saved search with Pending status and Doc
	 *              View option user should not navigate to Doc View screen
	 */
	@Test(description = "RPMXCON-48631", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyInvalidQueryBatchUploadSavedSearch(String username, String password, String fullName)
			throws Exception {
		String fileName = saveSearch.renameFile(Input.errorQueryFileLocation);
		String search = "Basic Invalid WP";
		String expectedMsg = "The selected search is not yet completed successfully. Please select a valid completed search.";

		// Login as PA
		login.loginToSightLine(username, password);

		base.stepInfo("Test case Id: RPMXCON-48631");
		base.stepInfo(
				"Verify on selecting saved search with Pending status and Doc View option user should not navigate to Doc View screen");

		// Upload Error Query Through Batch File
		base.stepInfo("Upload Batch File");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.uploadWPBatchFile_New(fileName, Input.errorQueryFileLocation);

		// verify status
		saveSearch.selectSavedSearch(search);
		base.waitForElement(saveSearch.getSavedSearchStatus(search));
		String status = saveSearch.getSavedSearchStatus(search).getText();

		String passMsg = "Search Status is : " + status;
		String failMsg = "Saerch Status is not As Expected";
		base.textCompareEquals(status, "ERROR", passMsg, failMsg);

		// verify warning msg
		saveSearch.docViewFromSS("View in DocView");
		base.VerifyWarningMessage(expectedMsg);

		// Delete Uploaded File
		saveSearch.deleteUploadedBatchFile(fileName, Input.mySavedSearch, false, null);
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description: Verify that Search upload functionality is working proper for
	 *               multi sheets in Saved searches(RPMXCON-48791)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-48791", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyMultipleTabWorkSheets(String userName, String password, String fullanme)
			throws InterruptedException, IOException {

		String fileName = Input.BatchFileWithMultiplesheetFile;
		String fileFormat = ".xlsx";
		String fileLocation = System.getProperty("user.dir") + Input.validBatchFileLocation;
		List<String> sheetList = new ArrayList<>();

		base.stepInfo("Test case Id: RPMXCON-48791 - Saved Search");
		base.stepInfo("Verify that Search upload functionality is working proper for multi sheets in Saved searches");

		// Login as User
		login.loginToSightLine(userName, password);
		base.stepInfo("Logged in as : " + userName);

		int number_of_sheets = base.getTotalSheetCount(fileLocation, fileName + fileFormat);
		base.stepInfo("Total no.of sheets available in the workbook : " + number_of_sheets);

		saveSearch.navigateToSavedSearchPage();

		String fileToSelect = base.renameFile(true, fileLocation, fileName, fileFormat, false, "");
		System.out.println(fileToSelect);

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.validBatchFileLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("Success", false);

		sheetList = saveSearch.verifyListOfNodes(sheetList, null, true, number_of_sheets, fileToSelect, null, null,
				true, Input.mySavedSearch);

		base.passedStep("Search groups created as per the naming convention  {Spreadsheet File Name} TabID{Tab Name}");

		String resetName = base.renameFile(false, fileLocation, fileToSelect, fileFormat, true, fileName);
		System.out.println(resetName);

		// Delete node
		saveSearch.deleteListofNode(Input.mySavedSearch, sheetList, true, true);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description: Verify that after resize browser Save Search grid does not
	 *               resize(RPMXCON-48857)
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48857", enabled = true, groups = { "regression" })
	public void verifyGridAfterResize() throws Exception {
		// Minimize browser
		Dimension n = new Dimension(800, 800);
		driver.Manage().window().setSize(n);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48857");
		base.stepInfo("Verify that after resize browser Save Search grid does not resize");

		// Verify Grid position
		saveSearch.navigateToSavedSearchPage();
		saveSearch.verifyGridPosition();

		login.logout();
	}

	/**
	 * @Author Raghuram @Date: 03/14/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that After adding Saved Query - application displays
	 *              all documents that are in the aggregate results - When User
	 *              performs Export with Child Search group [RPMXCON-49069] sprint
	 *              13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49069", enabled = true, groups = { "regression" })
	public void aggregateResultDocExportSGandSearchesUpdated() throws Exception {

		ReportsPage report = new ReportsPage(driver);

		List<String> newNodeList = new ArrayList<>();
		Boolean inputValue = true;
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";
		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String savedSearchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String nodeToSelect;

		base.stepInfo("Test case Id: RPMXCON-49069  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that  After adding Saved Query -  application displays all documents that are in the aggregate results - When User performs Export with Child Search groups");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 3);
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		nodeToSelect = newNodeList.get(0);

		// Initial Notification count
		int Bgcount = base.initialBgCount();

		// Saving additional search under child node
		session.multipleBasicContentSearch(Input.searchString1);
		session.saveSearchInNodewithChildNode(savedSearchName, newNodeList.get(1));
		session.getNewSearchButton().waitAndClick(5);
		session.multipleBasicContentSearch(Input.searchString2);
		session.saveSearchInNodewithChildNode(savedSearchName1, newNodeList.get(2));

		// To Pick Expected Aggregate count
		session.selectSavedsearchInASWp(nodeToSelect);
		driver.waitForPageToBeReady();
		session.SearchBtnAction();
		int purehit = session.returnPurehitCount();
		base.stepInfo("Expected aggregate purehit count from Export : " + purehit);

		// selecting the parent node
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeToSelect);
		base.stepInfo("Parent Node Selected : " + nodeToSelect);

		// Verify Export
		base.stepInfo("Initiate Export");
		saveSearch.getSavedSearchExportButton().Click();
		base.waitForElement(saveSearch.getExportPopup());
		report.customDataReportMethodExport("", false);
		driver.waitForPageToBeReady();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);

		// Download report
		base.stepInfo("Initiate File Download");
		report.downLoadReport();
		base.stepInfo("File Downloaded");

		base.stepInfo(
				"Should show all documents that are in the aggregate results set of all child search groups and searches in Exported file");
		int countToCompare = saveSearch.fileVerificationSpecificMethod();
		base.stepInfo("Document count from the export : " + countToCompare);
		base.digitCompareEquals(purehit, countToCompare,
				"Shows all documents that are in the aggregate results set of all child search groups and searches.      ",
				"Purehit and File count doesn't match");

		// Delete Search Group
		base.stepInfo("Initiating Delete SearchGroup");
		saveSearch.deleteNode(Input.mySavedSearch, nodeToSelect);

		// logout
		login.logout();
	}

	/**
	 * @Author Raghuram @Date: 03/14/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that "Count" gets update in Saved Search Screen when
	 *              user saved a Background Basic search Query[RPMXCON-48477] sprint
	 *              13
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48477", enabled = true, groups = { "regression" })
	public void saveSearchScreenOnBasicQueryCountWithBulkDatas() throws InterruptedException {
		String highVolumeProject = Input.highVolumeProject;
		String searchName = "search" + Utility.dynamicNameAppender();
		String expectedStatus = "COMPLETED";
		String searchString = Input.bulkSearchSting1;
		int Bgcount;
		String nearDupe = "Near Duplicate Count";

		base.stepInfo("Test case Id: RPMXCON-48477  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that \"Count\" gets update  in Saved Search Screen when user saved a Background Basic search Query");
		base.stepInfo("Flow can only be done for inputs/projects with 6L - 7L bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Switch to HighVolume Project
		base.selectproject(highVolumeProject);

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(searchString, "No", "First");
		session.SearchBtnAction();
		session.handleWhenAllResultsBtnInUncertainPopup();
		int pureHit = session.returnPurehitCount();

		// Verify Tile Spinning
		base.stepInfo("Verifying for one or more related tiles are still spinning .");
		session.verifyTileSpinning();

		// Save Search
		session.saveSearch(searchName);

		// Verify Status based on Count
		base.stepInfo("Before Execution get completed");
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.verifyStatusBasedOnCount(searchName, "flow-1", 0);

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);

		// Verify SavedSearch Status once notification arises
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.docResultCOuntCHeck(searchName);
		base.stepInfo(
				"Verifing the count on Saved Search Screen for the above saved Search search execution is completed");
		saveSearch.verifyStatusByReSearch(searchName, expectedStatus, 5);
		String updatedDocCount = session.getSavedSearchCount(searchName).getText();
		base.stepInfo("Updated Count from SavedSearch : " + updatedDocCount);
		base.textCompareEquals(Integer.toString(pureHit), updatedDocCount, "SavedSearch count updated", "count failed");
		saveSearch.getDocCountAndStatusOfBatch(searchName, nearDupe, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/15/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that count of Results from saved search after batch
	 *              upload should match with the actual docs from background tasks
	 *              page with large data[RPMXCON-49075] sprint 13
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-49075", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyActualDocsCountFromBackgroundPage(String username, String password, String fullname)
			throws InterruptedException, IOException {
		BatchRedactionPage batch = new BatchRedactionPage(driver);
		List<String> searchIDlist = new ArrayList<>();
		HashMap<String, String> mapPair = new HashMap<String, String>();
		String fileLocation = Input.validBatchFileLocation;
		String fileLocationWithDir = System.getProperty("user.dir") + Input.validBatchFileLocation;
		String fileName = Input.validBatchFile;
		String fileFormat = ".xlsx";
		String batchNodeToCheck, fileToSelect;
		String expectedStatus = "INPROGRESS";
		String expBGURL = Input.url + "Background/BackgroundTask";
		int Bgcount, rowCOuntFromExcel, bgHeaderIndex, searchIDindex, countIDindex;

		base.stepInfo("Test case Id: RPMXCON-49075  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that count of Results from saved search after batch upload should match with the actual docs from background tasks page with large data");

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullname);

		// Navigate to saved search page
		saveSearch.navigateToSSPage();

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Rename as dynamic fileName and store data respectively
		fileToSelect = base.renameFile(true, fileLocationWithDir, fileName, fileFormat, false, "");
		batchNodeToCheck = fileToSelect + "_" + 1 + "_Sheet" + 1;

		// Total no.of rows(searches) in sheet
		rowCOuntFromExcel = base.getTotalNumOfRowsInExcel(fileLocationWithDir + "\\" + fileToSelect + fileFormat, 0, "",
				0);
		System.out.println(rowCOuntFromExcel);

		// Upload batch file
		saveSearch.uploadBatchFile_D(fileLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("Success", false);
		driver.waitForPageToBeReady();

		// Wait for Searches to complete and count get updated
		base.stepInfo("Wait till all the search status changes to 'Completed' from 'InProgress'");
		base.checkNotificationCount(Bgcount, rowCOuntFromExcel);
		saveSearch.sgExpansion();
		softAssertion.assertTrue(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Fetch datas
		searchIDindex = base.getIndex(saveSearch.gettableHeaders(), "SEARCH ID");
		countIDindex = base.getIndex(saveSearch.gettableHeaders(), "COUNT OF RESULTS");
		saveSearch.selectNode1(batchNodeToCheck);
		saveSearch.getCreatedNode(batchNodeToCheck).waitAndClick(10);
		driver.waitForPageToBeReady();
		searchIDlist = base.availableListofElements(saveSearch.getGridDataList(searchIDindex));
		
		// Mapping Search id and count
		mapPair = saveSearch.collectionOfSearchIdAndItsCount(searchIDlist, countIDindex);
		
		// Click the notifications to launch
		base.waitForElement(batch.getBullHornIcon());
		batch.getBullHornIcon().waitAndClick(10);
		base.waitForElement(batch.getViewAllBtn());
		batch.getViewAllBtn().waitAndClick(10);

		// verify Background Task page
		base.verifyUrlLanding(expBGURL, "Navigated to My backgroud task page.", "Navigation Failed");
		bgHeaderIndex = base.getIndex(saveSearch.getBGgridDataList(), "ACTUAL DOCS");
		
		// SearchID data comparision
		saveSearch.SearchIdAndDataToCompare(searchIDlist, mapPair, bgHeaderIndex);

		// Delete uploaded batch file
		base.stepInfo("Initiated Batch upload delete");
		saveSearch.deleteUploadedBatchFile(fileToSelect, Input.mySavedSearch, false, null);

		// Reset FIleName
		base.renameFile(false, System.getProperty("user.dir") + fileLocation, fileToSelect, fileFormat, true, fileName);

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/15/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that Saved Search >> Uploaded Batch Search query works
	 *              properly for MasterDate date/time field with
	 *              "Is"operator[RPMXCON-48774] sprint 13
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-48774", enabled = true, groups = { "regression" })
	public void masterDateISoperator() throws InterruptedException, IOException {
		String expSSURL = Input.url + "Search/Searches";
		String fileLocation = Input.validBatchFileLocation;
		String fileLocationWithDir = System.getProperty("user.dir") + Input.validBatchFileLocation;
		String fileName = Input.masterDateBatchFile;
		String fileFormat = ".xlsx";
		String batchNodeToCheck, fileToSelect;
		String searchName = "MasterDate IS operator Basic Search";
		int Bgcount, pureHit, rowCOuntFromExcel;
		String docCount;

		base.stepInfo("Test case Id: RPMXCON-48774  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that Saved Search >> Uploaded Batch Search query works properly for MasterDate date/time field with \"Is\"operator");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to saved search page
		saveSearch.navigateToSSPage();

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Rename as dynamic fileName and store data respectively
		fileToSelect = base.renameFile(true, fileLocationWithDir, fileName, fileFormat, false, "");
		batchNodeToCheck = fileToSelect + "_" + 1 + "_Sheet" + 1;

		// Total no.of rows(searches) in sheet
		rowCOuntFromExcel = base.getTotalNumOfRowsInExcel(fileLocationWithDir + "\\" + fileToSelect + fileFormat, 0, "",
				0);
		System.out.println(rowCOuntFromExcel);

		// Upload batch file
		saveSearch.uploadBatchFile_D(fileLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("Success", false);
		driver.waitForPageToBeReady();

		// Reset FIleName
		base.renameFile(false, System.getProperty("user.dir") + fileLocation, fileToSelect, fileFormat, true, fileName);

		// Wait for Searches to complete and count get updated
		base.stepInfo("Wait till all the search status changes to 'Completed' from 'InProgress'");
		base.checkNotificationCount(Bgcount, rowCOuntFromExcel);
		saveSearch.sgExpansion();
		softAssertion.assertTrue(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Search and Edit
		saveSearch.selectNode1(batchNodeToCheck);
		saveSearch.getCreatedNode(batchNodeToCheck).waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("Search for : " + searchName);
		saveSearch.selectSavedSearch(searchName);
		docCount = saveSearch.getCount(searchName).getText();
		base.stepInfo("Doc count for the search from pureHit page : " + docCount);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(expSSURL, "Clicked on Edit and Landed on Session Search page",
				"Failed to click edit - not landed in Session Search Page");

		// Search and get Purehit
		session.getModifyASearch_Last().waitAndClick(5);
		session.SearchBtnAction();
		base.stepInfo("Performed Search");
		pureHit = session.returnPurehitCount();
		base.stepInfo("PureHit count : " + pureHit);

		// Validation Doc count
		base.digitCompareEquals(pureHit, Integer.parseInt(docCount),
				"MasterDate date/time field search result returns documents which satisfied above configured query. ",
				"Doc count failed");

		// Delete uploaded batch file
		base.stepInfo("Initiated Batch upload delete");
		saveSearch.deleteUploadedBatchFile(fileToSelect, Input.mySavedSearch, false, null);

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 03/16s/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that Saved Search >> Uploaded Batch Search query works
	 *              properly for MasterDate date/time field with
	 *              "Range"operator[RPMXCON-48775] sprint 13
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-48775", enabled = true, groups = { "regression" })
	public void masterDateRangeSoperator() throws InterruptedException, IOException {
		String expSSURL = Input.url + "Search/Searches";
		String fileLocation = Input.validBatchFileLocation;
		String fileLocationWithDir = System.getProperty("user.dir") + Input.validBatchFileLocation;
		String fileName = Input.masterDateBatchFile;
		String fileFormat = ".xlsx";
		String batchNodeToCheck, fileToSelect;
		String searchName = "MasterDate RANGE operator Basic Search";
		int Bgcount, pureHit, rowCOuntFromExcel;
		String docCount;

		base.stepInfo("Test case Id: RPMXCON-48775  Saved Search Sprint 13");
		base.stepInfo(
				"Verify that Saved Search >> Uploaded Batch Search query works properly for MasterDate  date/time field with \"Range\"operator");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to saved search page
		saveSearch.navigateToSSPage();

		// Initial Notification count
		Bgcount = base.initialBgCount();

		// Rename as dynamic fileName and store data respectively
		fileToSelect = base.renameFile(true, fileLocationWithDir, fileName, fileFormat, false, "");
		batchNodeToCheck = fileToSelect + "_" + 1 + "_Sheet" + 1;

		// Total no.of rows(searches) in sheet
		rowCOuntFromExcel = base.getTotalNumOfRowsInExcel(fileLocationWithDir + "\\" + fileToSelect + fileFormat, 0, "",
				0);
		System.out.println(rowCOuntFromExcel);

		// Upload batch file
		saveSearch.uploadBatchFile_D(fileLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("Success", false);
		driver.waitForPageToBeReady();

		// Reset FIleName
		base.renameFile(false, System.getProperty("user.dir") + fileLocation, fileToSelect, fileFormat, true, fileName);

		// Wait for Searches to complete and count get updated
		base.stepInfo("Wait till all the search status changes to 'Completed' from 'InProgress'");
		base.checkNotificationCount(Bgcount, rowCOuntFromExcel);
		saveSearch.sgExpansion();
		softAssertion.assertTrue(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches uploaded in Saved search screen.");
		softAssertion.assertAll();

		// Search and Edit
		saveSearch.selectNode1(batchNodeToCheck);
		saveSearch.getCreatedNode(batchNodeToCheck).waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("Search for : " + searchName);
		saveSearch.selectSavedSearch(searchName);
		docCount = saveSearch.getCount(searchName).getText();
		base.stepInfo("Doc count for the search from pureHit page : " + docCount);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(expSSURL, "Clicked on Edit and Landed on Session Search page",
				"Failed to click edit - not landed in Session Search Page");

		// Search and get Purehit
		session.getModifyASearch_Last().waitAndClick(5);
		session.SearchBtnAction();
		base.stepInfo("Performed Search");
		pureHit = session.returnPurehitCount();
		base.stepInfo("PureHit count : " + pureHit);

		// Validation Doc count
		base.digitCompareEquals(pureHit, Integer.parseInt(docCount),
				"MasterDate date/time field search result returns documents which satisfied above configured query. ",
				"Doc count failed");

		// Delete uploaded batch file
		base.stepInfo("Initiated Batch upload delete");
		saveSearch.deleteUploadedBatchFile(fileToSelect, Input.mySavedSearch, false, null);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :RMU User - Verify that User can move appropriate Search to
	 *              respective Search Group on Saved Search Screen. [RPMXCON-48127]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48127", enabled = true, groups = { "regression" })
	public void verifyMovedSearchGroupInSgAsRmu() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48127 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can move appropriate Search to respective Search Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create Node In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// create Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		String defNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);
		String defNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName, defNode2);

		// Rename Node In Default SG
		saveSearch.verifyMoveActionSSMethod(searchName, defNode, defNode2, true, false, false);

		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String passMsg = defNode + " : is Not Available";

		// verify Renamed Node In Other SG
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.selectNode1(node1);
		Element MovedNodeInDef = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, defNode);
		base.ValidateElement_Absence(MovedNodeInDef, passMsg);

		// verify Renamed Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, defNode);
		saveSearch.savedSearch_SearchandSelect(searchName, Input.yesButton);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, defNode);
		saveSearch.deleteNode(Input.mySavedSearch, defNode2);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Reviewer User - Verify that User can move appropriate Search to
	 *              respective Security Group on Saved Search Screen.[RPMXCON-48136]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48136", enabled = true, groups = { "regression" })
	public void verifyMovedSearchGroupInSgAsRev() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48136 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can move appropriate Search to respective Search Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);
		userManagement.saveSecurityGroup();

		login.logout();

		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		// create Node In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		// create Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		String defNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);
		String defNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName, defNode2);

		// Rename Node In Default SG
		saveSearch.verifyMoveActionSSMethod(searchName, defNode, defNode2, true, false, false);

		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String passMsg = defNode + " : is Not Available & " + searchName + " : Not Available";

		// verify Renamed Node In Other SG
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.selectNode1(node1);
		Element MovedNodeInDef = saveSearch.getSavedSearchNodeWithRespectiveSG(Input.mySavedSearch, defNode);
		base.ValidateElement_Absence(MovedNodeInDef, passMsg);

		// verify Renamed Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, defNode);
		saveSearch.savedSearch_SearchandSelect(searchName, Input.yesButton);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, defNode);
		saveSearch.deleteNode(Input.mySavedSearch, defNode2);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : RMU User - Verify that user can edit Existing Search under the
	 *              respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48125]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48125", enabled = true, groups = { "regression" })
	public void verifyEditSearchGroupInSgAsRmu() throws Exception {
		String defSgSearch = "Search" + Utility.dynamicNameAppender();
		String otherSgSearch = "Search" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48125 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can move appropriate Search to respective Search Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		// Release docs To SG
		session.basicContentSearch(Input.searchString1);
		session.bulkRelease(securityGroup);
		base.selectproject();
		session.basicContentSearch(Input.searchString2);
		session.bulkRelease(securityGroup);

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create search in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(defSgSearch);

		// Edit Search As Default SG
		saveSearch.savedSearch_Searchandclick(defSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(defSgSearch));
		String docCountsOfDef = saveSearch.getSavedSearchCount(defSgSearch).getText();

		saveSearch.savedSearchEdit(defSgSearch);
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		session.searchAndReturnPureHit_BS();

		// Overwriting the the same saved search
		driver.waitForPageToBeReady();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, defSgSearch, "first", "Success", "", null);

		// create search In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(otherSgSearch);

		// Modify Search In Other SG
		saveSearch.savedSearch_Searchandclick(otherSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(otherSgSearch));
		String countBeforEdit = saveSearch.getSavedSearchCount(otherSgSearch).getText();

		saveSearch.savedSearchEdit(otherSgSearch);
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		session.searchAndReturnPureHit_BS();

		// Overwriting the the same saved search
		driver.waitForPageToBeReady();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, otherSgSearch, "first", "Success", "", null);

		// Verify Edited query in Other SG
		saveSearch.savedSearch_Searchandclick(otherSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(otherSgSearch));
		String updatedCountOthSg = saveSearch.getSavedSearchCount(otherSgSearch).getText();

		String passMsg = "Edited query is reflected in security group : " + securityGroup;
		String failMsg = "Edited query is Not Reflected ";

		base.textCompareNotEquals(updatedCountOthSg, countBeforEdit, passMsg, failMsg);

		// Verify Edited query in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);

		saveSearch.savedSearch_Searchandclick(defSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(defSgSearch));
		String updatedCountDefSg = saveSearch.getSavedSearchCount(defSgSearch).getText();

		String passMsgOfDef = "Edited query is reflected in security group : " + Input.securityGroup;
		base.textCompareNotEquals(updatedCountOthSg, docCountsOfDef, passMsgOfDef, failMsg);

		// Delete Search
		saveSearch.deleteSearch(defSgSearch, Input.mySavedSearch, Input.yesButton);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : RMU User - Verify that User can perform the Tally from search
	 *              Group under the respective Security Group on Saved Search
	 *              Screen. [RPMXCON-48133]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48133", enabled = true, groups = { "regression" })
	public void verifyTallySearchGroupInSg() throws Exception {
		String defSgSearch = "Search" + Utility.dynamicNameAppender();
		String otherSgSearch = "Search" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48133 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can perform the Tally from search Group under the respective Security Group on Saved Search Screen.");

		base.failedMessage(
				"Expected Failure : Tally Button should be Enabled But It is Disabled when Searchgroup is selected");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		// Release docs To SG
		session.basicContentSearch(Input.searchString2);
		session.bulkRelease(securityGroup);

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create search in Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String othNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(otherSgSearch, othNode);

		// create search in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		String defNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);

		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(defSgSearch, defNode);

		// perform tally from Search Group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.selectNode1(defNode);

//		 Element tallyBtnStatus = saveSearch.getSavedSearchToTally();
//		 saveSearch.checkButtonEnabled(tallyBtnStatus, "Should be Enabled", "Tally");

		saveSearch.getSavedSearchSelectRadioButton().waitAndClick(10);
		saveSearch.getSavedSearchToTally().waitAndClick(10);

		// Have to Continue Scripting Once the Tally Button is Enabled.

	}

	/**
	 * @Author Jeevitha
	 * @Description : Reviewer User - Verify that user can edit Existing Search
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48134]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48134", enabled = true, groups = { "regression" })
	public void verifyEditSearchGroupInSgAsRev() throws Exception {
		String defSgSearch = "Search" + Utility.dynamicNameAppender();
		String otherSgSearch = "Search" + Utility.dynamicNameAppender();
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48134 Saved Search");
		base.stepInfo(
				"Reviewer User - Verify that user can edit Existing Search under the respective Security Group on Saved Search Screen.");

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);
		userManagement.saveSecurityGroup();

		// Release docs To SG
		session.basicContentSearch(Input.searchString1);
		session.bulkRelease(securityGroup);
		base.selectproject();
		session.basicContentSearch(Input.searchString2);
		session.bulkRelease(securityGroup);

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		// create search in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(defSgSearch);

		// Edit Search As Default SG
		saveSearch.savedSearch_Searchandclick(defSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(defSgSearch));
		String docCountsOfDef = saveSearch.getSavedSearchCount(defSgSearch).getText();

		saveSearch.savedSearchEdit(defSgSearch);
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		session.searchAndReturnPureHit_BS();

		// Overwriting the the same saved search
		driver.waitForPageToBeReady();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, defSgSearch, "first", "Success", "", null);

		// create search In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(otherSgSearch);

		// Modify Search In Other SG
		saveSearch.savedSearch_Searchandclick(otherSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(otherSgSearch));
		String countBeforEdit = saveSearch.getSavedSearchCount(otherSgSearch).getText();

		saveSearch.savedSearchEdit(otherSgSearch);
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		session.searchAndReturnPureHit_BS();

		// Overwriting the the same saved search
		driver.waitForPageToBeReady();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, otherSgSearch, "first", "Success", "", null);

		// Verify Edited query in Other SG
		saveSearch.savedSearch_Searchandclick(otherSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(otherSgSearch));
		String updatedCountOthSg = saveSearch.getSavedSearchCount(otherSgSearch).getText();

		String passMsg = "Edited query is reflected in security group : " + securityGroup;
		String failMsg = "Edited query is Not Reflected ";

		base.textCompareNotEquals(updatedCountOthSg, countBeforEdit, passMsg, failMsg);

		// Verify Edited query in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);

		saveSearch.savedSearch_Searchandclick(defSgSearch);
		base.waitForElement(saveSearch.getSavedSearchCount(defSgSearch));
		String updatedCountDefSg = saveSearch.getSavedSearchCount(defSgSearch).getText();

		String passMsgOfDef = "Edited query is reflected in security group : " + Input.securityGroup;
		base.textCompareNotEquals(updatedCountOthSg, docCountsOfDef, passMsgOfDef, failMsg);

		// Delete Search
		saveSearch.deleteSearch(defSgSearch, Input.mySavedSearch, Input.yesButton);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
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
		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}
