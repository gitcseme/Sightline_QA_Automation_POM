package testScriptsRegression;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Regression_Set_01 {
	Driver driver;
	LoginPage lp;
	SavedSearch ss;
	SessionSearch search;
	BaseClass base;
	SoftAssert softassert;
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

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		lp = new LoginPage(driver);
		ss = new SavedSearch(driver);
		search = new SessionSearch(driver);
		schedule = new SchedulesPage(driver);
		base = new BaseClass(driver);
	}

	/**
	 * Sprint 1
	 * 
	 * @author Jeevitha Description - Creates the node and uploads the batch
	 *         file.(RPMXCON-57473) Description - Selects the node and uploads the
	 *         batch file.(RPMXCON-57483)
	 * @modified by : Raghuram - changes of new sg creation method
	 */
	@Test(description ="RPMXCON-57473,RPMXCON-57483",groups = { "regression" } )
	public void createSearchGroupAndbatchUploadByPA() throws InterruptedException {
		String SearchNamePA = "PA" + Utility.dynamicNameAppender();

		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case id: RPMXCON-57473, RPMXCON-57483");
		// create new search group
		// ss.createNewSearchGrp(SearchNamePA);
		String new_Node = ss.createSearchGroupAndReturn(SearchNamePA, "PA", "No");
		driver.getWebDriver().navigate().refresh();
		ss.uploadBatchFile_New(ss.renameFile(Input.batchFileNewLocation));

		// Select Node
		driver.getWebDriver().navigate().refresh();
		ss.selectNode1(new_Node);
		System.out.println("Selected Node");
		base.stepInfo("Successufully Selected the Node");

		// upload batch template
		ss.uploadBatchFile_New(ss.renameFile(Input.batchFileNewLocation));
		base.stepInfo("Successfully uploaded the BAtch file");

		System.out.println("Successfully ran for PA user...");
		base.stepInfo("Successfully ran for PA user...");

		// Delete Node
		driver.getWebDriver().navigate().refresh();
		ss.deleteNode(Input.mySavedSearch, new_Node, true, true);

		lp.logout();

	}

	/**
	 * Sprint 1
	 * 
	 * @author Jeevitha Description - Creates the node and uploads the batch
	 *         file.(RPMXCON-57473) Description - Selects the node and uploads the
	 *         batch file.(RPMXCON-57483)
	 * @modified by : Raghuram - changes of new sg creation method
	 */
	@Test(description ="RPMXCON-57473,RPMXCON-57483",groups = { "regression" } )
	public void createSearchGroupAndbatchUploadByRmu() throws InterruptedException {
		String SearchNameRMU = "RMU" + Utility.dynamicNameAppender();

		// Login as a RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57473, RPMXCON-57483");

		// create new search group
		// ss.createNewSearchGrp(SearchNameRMU);
		String new_Node = ss.createSearchGroupAndReturn(SearchNameRMU, "RMU", "No");
		driver.getWebDriver().navigate().refresh();
		System.out.println("Selected Node");
		base.stepInfo("Created New Search Group");

		ss.uploadBatchFile_New(ss.renameFile(Input.batchFileNewLocation));

		// Select Node
		driver.getWebDriver().navigate().refresh();
		ss.selectNode1(new_Node);

		// upload batch template
		ss.uploadBatchFile_New(ss.renameFile(Input.batchFileNewLocation));
		base.stepInfo("Succefully selected Node and uploaded the batch file");

		System.out.println("Successfully ran for Rmu user...");
		UtilityLog.info("Successfully ran for Rmu user...");

		// Delete Node
		driver.getWebDriver().navigate().refresh();
		ss.deleteNode(Input.mySavedSearch, new_Node, true, true);

		lp.logout();

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
	@Test(description ="RPMXCON-57473,RPMXCON-57483",groups = { "regression" } )
	public void createSearchGroupAndbatchUploadByRev() throws InterruptedException {
		String saveSearchName = "Rev" + Utility.dynamicNameAppender();

		// Login as a Rev
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Test case id: RPMXCON-57473, RPMXCON-57483");

		// create new search group
		// ss.createNewSearchGrp(saveSearchName);
		String new_Node = ss.createSearchGroupAndReturn(saveSearchName, "Rev", "No");
		driver.getWebDriver().navigate().refresh();

		ss.uploadBatchFile_New(ss.renameFile(Input.batchFileNewLocation));

		// Select Node
		driver.getWebDriver().navigate().refresh();
		ss.selectNode1(new_Node);

		// upload batch template
		ss.uploadBatchFile_New(ss.renameFile(Input.batchFileNewLocation));
		base.stepInfo("Successfully Selected node and Uploaded BAtch file");

		System.out.println("Successfully ran for Rev user...");
		base.stepInfo("Successfully ran for Rev user...");

		// Delete Node
		driver.getWebDriver().navigate().refresh();
		ss.deleteNode(Input.mySavedSearch, new_Node, true, true);

		lp.logout();
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
	@Test(description ="RPMXCON-57407",groups = { "regression" } )
	public void verifyScheduleSearchAsPA() throws ParseException, InterruptedException {
		String saveSearchName = "SearchPA" + Utility.dynamicNameAppender();

		// login as pa
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id: RPMXCON-57407");

		purehits = search.basicContentSearch(Input.searchString1);
		search.saveSearchDefaultTab(saveSearchName);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		search.getSavedSearch_DefaultSgTab().Click();
		ss.scheduleSavedSearchInMinute(saveSearchName, 2);
		schedule.editScheduledSaveSearch(saveSearchName, 3);

		schedule.verifyScheduledTime(saveSearchName);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		ss.deleteSearch(saveSearchName, Input.shareSearchDefaultSG, "Yes");

		lp.logout();

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
	@Test(description ="RPMXCON-57407",groups = { "regression" } )
	public void verifyScheduleSearchAsRMU() throws ParseException, InterruptedException {
		String saveSearchName1 = "SearchRMU" + Utility.dynamicNameAppender();

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57407");

		purehits = search.basicContentSearch(Input.searchString1);
		search.saveSearchDefaultTab(saveSearchName1);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait60);
		search.getSavedSearch_DefaultSgTab().Click();
		ss.scheduleSavedSearchInMinute(saveSearchName1, 2);
		schedule.editScheduledSaveSearch(saveSearchName1, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(saveSearchName1);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName1);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		ss.deleteSearch(saveSearchName1, Input.shareSearchDefaultSG, "Yes");
		lp.logout();

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
	@Test(description ="RPMXCON-57411",groups = { "regression" } )
	public void verifyScheduleSearchPA() throws ParseException, InterruptedException {
		String saveSearchName = "SearchPA" + Utility.dynamicNameAppender();

		// login as pa
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id: RPMXCON-57411");

		purehits = search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		ss.scheduleSavedSearchInMinute(saveSearchName, 2);
		ss.scheduleSavedSearchInMinute(saveSearchName, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(saveSearchName);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		ss.deleteSearch(saveSearchName, Input.mySavedSearch, "Yes");

		lp.logout();

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
	@Test(description ="RPMXCON-57411",groups = { "regression" } )
	public void verifyScheduleSearchRMU() throws ParseException, InterruptedException {
		String saveSearchName = "SearchPA" + Utility.dynamicNameAppender();

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57411");

		purehits = search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);

		// Schedule Save search in munutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		ss.scheduleSavedSearchInMinute(saveSearchName, 2);
		ss.scheduleSavedSearchInMinute(saveSearchName, 3);

		schedule.verifyScheduledTime(saveSearchName);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(saveSearchName);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		ss.deleteSearch(saveSearchName, Input.mySavedSearch, "Yes");

		lp.logout();

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
	@Test(description ="RPMXCON-57412",groups = { "regression" } )
	public void verifyScheduleSearchForDefaultTabPA() throws ParseException, InterruptedException {
		String SearchNamePA = "SearchPA" + Utility.dynamicNameAppender();

		// login as pa
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case id: RPMXCON-57412");

		purehits = search.basicContentSearch(Input.searchString1);
		search.saveSearchDefaultTab(SearchNamePA);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		search.getSavedSearch_DefaultSgTab().Click();
		ss.scheduleSavedSearchInMinute(SearchNamePA, 2);
		ss.scheduleSavedSearchInMinute(SearchNamePA, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(SearchNamePA);

		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(SearchNamePA);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		ss.deleteSearch(SearchNamePA, Input.shareSearchDefaultSG, "Yes");

		lp.logout();

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
	@Test(description ="RPMXCON-57412",groups = { "regression" } )
	public void verifyScheduleSearchForDefaultTabRMU() throws ParseException, InterruptedException {
		String SearchNameRMU = "SearchRMU" + Utility.dynamicNameAppender();

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case id: RPMXCON-57412");

		// Search Query and save it in Default Tab
		purehits = search.basicContentSearch(Input.searchString1);
		search.saveSearchDefaultTab(SearchNameRMU);

		// Schedule Save search in minutes
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getSavedSearch_MySearchesTab().Visible();
			}
		}), Input.wait30);
		search.getSavedSearch_DefaultSgTab().Click();
		ss.scheduleSavedSearchInMinute(SearchNameRMU, 2);
		ss.scheduleSavedSearchInMinute(SearchNameRMU, 3);

		driver.waitForPageToBeReady();
		schedule.verifyScheduledTime(SearchNameRMU);
		// check the Status Once Scheduler gets Executed
		schedule.checkStatusComplete(SearchNameRMU);

		// Delete Search
		driver.getWebDriver().navigate().refresh();
		ss.deleteSearch(SearchNameRMU, Input.shareSearchDefaultSG, "Yes");

		lp.logout();

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				lp.logoutWithoutAssert();
			} catch (Exception e) {
//							 TODO: handle exception
			}
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
			lp.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			lp.clearBrowserCache();
		} finally {
			lp.clearBrowserCache();
		}
	}
}
