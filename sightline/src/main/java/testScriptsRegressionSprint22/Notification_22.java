package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Notification_22 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage= new TagsAndFoldersPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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

	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @author NA Testcase No:RPMXCON-53874
	 * @Description:To Verify As an RM user login, I will get an notification when I
	 *                 will execute any folder under My Search in saved search
	 **/
	@Test(description = "RPMXCON-53874", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterExecute() throws Exception {
		base.stepInfo("RPMXCON-53874");
		base.stepInfo("To Verify As an RM user login, I will get an notification "
				+ "when I will execute any folder under My Search in saved search");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");

		sessionSearch.navigateToSessionSearchPageURL();
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, newNode);

		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		String searchID = saveSearch.getSavedSearchID().getText();
		saveSearch.savedSearchExecute(searchName, pureHit);

		base.waitForElement(base.getBullHornIcon());
		base.getBullHornIcon().waitAndClick(10);

		String actStatus = saveSearch.getNotificationStatus(1).getText();
		String expStatus = "Your Save Search with Save Search Id " + searchID + " is COMPLETED";
		if (expStatus.equalsIgnoreCase(actStatus)) {
			base.passedStep("After completion the execution, notification id created in notify with status compledted");
		} else {
			base.failedStep(
					"After completion the execution, notification id Not created in notify with status compledted");
		}
		base.passedStep(
				"Verified - that, As an RM user login, I will get an notification when I will execute any folder under My Search in saved search");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-53903
	 * @Description:To verify, As an Sys Admin user login I will able to Impersonate
	 *                 from Sys Admin to RM & I will be able to go to Doc List page
	 *                 from saved search & able to perform Doc List actions
	 **/
	@Test(description = "RPMXCON-53903", enabled = true, groups = { "regression" })
	public void verifySAableToNavDocListPage() throws Exception {
		base.stepInfo("RPMXCON-53903");
		base.stepInfo("To verify, As an Sys Admin user login I will able to Impersonate from Sys Admin to RM & "
				+ "I will be able to go to Doc List page from saved search & able to perform Doc List actions");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String folderName = "Folder" + UtilityLog.dynamicNameAppender();
		int noOfDocs = 10;

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoRMU();

		base.stepInfo("Node (or) New Search Group creation");
		saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, newNode);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearch(searchName);
		saveSearch.saveSearchToDoclist();

		driver.waitForPageToBeReady();

		DocListPage docList = new DocListPage(driver);
		docList.documentSelection(noOfDocs);
		driver.scrollPageToTop();
		docList.bulkFolderInDocListPage(folderName);
		docList.viewSelectedDocumentsInDocView();

		DocViewPage docView = new DocViewPage(driver);
		docView.verifyNdDocumentsPureHits(noOfDocs);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyFolderDocCount(folderName, noOfDocs);
		base.passedStep("Verified, As an Sys Admin user login I will able to Impersonate from Sys Admin to RM & "
				+ "I will be able to go to Doc List page from saved search & able to perform Doc List actions");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-54459
	 * @Description:Verify that on click of 'View All' displays in the pop up on
	 *                     click of megaphone 'My Background Tasks' page should be
	 *                     displayed with all tasks
	 **/
	@Test(description = "RPMXCON-54459", enabled = true, groups = { "regression" })
	public void verifyMegaPhoneIcon() throws Exception {
		base.stepInfo("RPMXCON-54459");
		base.stepInfo(
				"Verify that on click of 'View All' displays in the pop up on click of megaphone 'My Background Tasks' page should be displayed with all tasks");
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// configure query & Save
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.saveSearch(searchName);

		// select search
		saveSearch.savedSearch_Searchandclick(searchName);

		// execute Search
		int initialBg = base.initialBgCount();
		saveSearch.savedSearchExecute_Draft(searchName, initialBg);

		// verify Notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// verify megaphone icon
		base.waitForElement(base.getBullHornIcon());
		base.getBullHornIcon().waitAndClick(10);

		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		base.passedStep("It  displays the pop up with background tasks/ Sharing alerts with 'View All' button");

		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-54157
	 * @Description: To verify as an PA user login into the Application, When user
	 *               applying Bulk Release from Saved Search, user will be able to
	 *               see the background task in notification window
	 **/
	@Test(description = "RPMXCON-54157", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterBulkRelease() throws Exception {
		base.stepInfo("RPMXCON-54157");
		base.stepInfo(
				"To verify as an PA user login into the Application, When user applying Bulk Release from Saved Search, user will be able to see the background task in notification window");
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// configure query & Save
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.saveSearch(searchName);

		// select search
		saveSearch.savedSearch_Searchandclick(searchName);

		// perform release action
		int initialBg = base.initialBgCount();
		saveSearch.performReleaseAction(Input.securityGroup, true);

		// verify Notification
		base.checkNotificationCount(initialBg, 1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
		base.verifyMegaPhoneIconAndBackgroundTasks(false, true);
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-54430
	 * @Description: Saved Search to DocView navigation - Verify user must be able
	 *               to click on Notification once background task get completed.
	 **/
	@Test(description = "RPMXCON-54430", enabled = true, groups = { "regression" })
	public void verifyNavigationToDocview() throws Exception {
		base.stepInfo("RPMXCON-54157");
		base.stepInfo(
				"Saved Search to DocView navigation - Verify user must be able to click on Notification once background task get completed.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.navigateToSavedSearchPage();
		base.stepInfo("User navigated successfully");

		base.stepInfo("Navigate to docview");
		base.waitForElement(saveSearch.getLaunchDocView());
		saveSearch.getLaunchDocView().waitAndClick(10);

		docView = new DocViewPage(driver);
		if (docView.getActionButton().isElementAvailable(8)) {
			base.passedStep("Navigate from Saved Search to Doc view is  completed in 8 SECONDS.");
		} else {
			base.passedStep(
					"Entry in the Background Tasks Page are clickable, and will take the user to Doc View for the completed action, and that action will complete within 8 seconds");
		}
	}

	/**
	 * @author NA testcase No:RPMXCON-53875
	 * @Description: To Verify As an REV user login, I will get an notification when I will execute any folder under My Search in saved search
	 **/
	@Test(description = "RPMXCON-53875", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterExecuteRev() throws Exception {
		base.stepInfo("RPMXCON-53875");
		base.stepInfo("To Verify As an REV user login, I will get an notification "
				+ "when I will execute any folder under My Search in saved search");
		
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		
		sessionSearch.navigateToSessionSearchPageURL();
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, newNode);
		
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		String searchID = saveSearch.getSavedSearchID().getText();
		saveSearch.savedSearchExecute(searchName, pureHit);
		
		base = new BaseClass(driver);
	    base.waitForElement(base.getBullHornIcon());
		base.getBullHornIcon().waitAndClick(10);
		
		String actStatus = saveSearch.getNotificationStatus(1).getText();
		String expStatus = "Your Save Search with Save Search Id "+ searchID +" is COMPLETED";		
		if(expStatus.equalsIgnoreCase(actStatus)) {
			base.passedStep("After completion the execution, notification id created in notify with status compledted");
		} else {
			base.failedStep("After completion the execution, notification id Not created in notify with status compledted");
		}
		base.passedStep("Verified - that, As an REV user login, I will get an notification when I will execute any folder under My Search in saved search");
		loginPage.logout();
	}
	

	/**
	 * @author NA testcase No:RPMXCON-54161
	 * @Description: o verify as an RMU login into the Application, When user applying Bulk Assign from Saved Search, user will be able to see the background task in notification window"
	 **/
	@Test(description = "RPMXCON-54161", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterBulkAssignRMU() throws Exception {
		base.stepInfo("RPMXCON-54161");
		base.stepInfo("To verify as an RMU login into the Application, When user applying Bulk Assign from Saved Search,"
				+ " user will be able to see the background task in notification window");
		
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();	
		String assigname = "assgnment" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		
		sessionSearch.navigateToSessionSearchPageURL();
        sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, newNode);
		
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
	
		base.waitForElement(saveSearch.getSavedSearchToBulkAssign());
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignDocstoNewAssgn(assigname);
		assignmentPage.quickAssignCreation(assigname, Input.codeFormName);
		
		base = new BaseClass(driver);	
		int initialBg = base.initialBgCount();

		// verify Notification 
		base.checkNotificationCount(initialBg,1);
		
	    base.waitForElement(base.getBullHornIcon());
		base.getBullHornIcon().waitAndClick(10);
		
		String actStatus = saveSearch.getNotificationStatus(1).getText();
		String expStatus = "Your Bulkaction-Assign with Bulkaction-Assign Id";		
		
		if(actStatus.contains(expStatus) && actStatus.contains("COMPLETED")) {
			base.passedStep("User able to see background in the notification window");
		} else {
			base.failedStep("User Not able to see background in the notification window");
		}
		base.passedStep("Verified - as an RMU login into the Application, When user applying Bulk Assign from Saved Search,"
				+ " user will be able to see the background task in notification window");
		loginPage.logout();
	}

}
