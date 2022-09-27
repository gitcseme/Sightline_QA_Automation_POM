package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.BaseClass;
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
		saveSearch= new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
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
	
	/**
	 * @author NA Testcase No:RPMXCON-53874
	 * @Description:To Verify As an RM user login, I will get an notification when I will execute any folder under My Search in saved search
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
		
		String actStatus =saveSearch.getNotificationStatus(1).getText();
		String expStatus = "Your Save Search with Save Search Id "+ searchID +" is COMPLETED";		
		if(expStatus.equalsIgnoreCase(actStatus)) {
			base.passedStep("After completion the execution, notification id created in notify with status compledted");
		} else {
			base.failedStep("After completion the execution, notification id Not created in notify with status compledted");
		}
		base.passedStep("Verified - that, As an RM user login, I will get an notification when I will execute any folder under My Search in saved search");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-53903
	 * @Description:To verify, As an Sys Admin user login I will able to Impersonate from Sys Admin to RM & I will be able to go to Doc List page from saved search & able to perform Doc List actions
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
		saveSearch= new SavedSearch(driver);
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
		
		tagsAndFolderPage= new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyFolderDocCount(folderName, noOfDocs);
		base.passedStep("Verified, As an Sys Admin user login I will able to Impersonate from Sys Admin to RM & "
				+ "I will be able to go to Doc List page from saved search & able to perform Doc List actions");
	    loginPage.logout();
	}
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-54459
	 * @Description:Verify that on click of 'View All' displays in the pop up on click of megaphone 'My Background Tasks' page should be displayed with all tasks
	 **/
	@Test(description = "RPMXCON-54459", enabled = true, groups = { "regression" })
	public void verifyMegaPhoneIcon() throws Exception {
		base.stepInfo("RPMXCON-54459");
		base.stepInfo("Verify that on click of 'View All' displays in the pop up on click of megaphone 'My Background Tasks' page should be displayed with all tasks");
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
		
		//verify megaphone icon
		base.waitForElement(base.getBullHornIcon());
		base.getBullHornIcon().waitAndClick(10);
		
		base.verifyMegaPhoneIconAndBackgroundTasks(true,true);
		base.passedStep("It  displays the pop up with background tasks/ Sharing alerts with 'View All' button");
		
		
		loginPage.logout();
	}
	

	/**
	 * @author sowndarya Testcase No:RPMXCON-54157
	 * @Description: To verify as an PA user login into the Application, When user applying Bulk Release from Saved Search, user will be able to see the background task in notification window
	 **/
	@Test(description = "RPMXCON-54157", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterBulkRelease() throws Exception {
		base.stepInfo("RPMXCON-54157");
		base.stepInfo("To verify as an PA user login into the Application, When user applying Bulk Release from Saved Search, user will be able to see the background task in notification window");
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
		base.checkNotificationCount(initialBg,1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
		base.verifyMegaPhoneIconAndBackgroundTasks(false,true);
	}
	
	/**
	 * @author Brundha.T TestCase Id:53589 Date:27/9/2022
	 * @Description :To Verify, As a Reviewer user login, After seeing the
	 *              Notification, it should not show the same notification after
	 *              re-login the session
	 */
	@Test(description = "RPMXCON-53589", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterReLogin_Reviewer() throws InterruptedException, Exception {

		base.stepInfo("Test case Id: RPMXCON-53589 Notification Component ");
		base.stepInfo(
				"To Verify, As a Reviewer user login, After seeing the Notification, it should not show the same notification after re-login the session");

		// Login as User
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in as : " + Input.rmu1userName);

		String tagName = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagName, "Select Tag Classification");

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("logged in as : " + Input.rev1userName);

		int initialBg = base.initialBgCount();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);
		System.out.println("Bulk Tag done");

		// verify notification for bulk tag
		base.checkNotificationCount(initialBg, 1);
		List<String> notifyId_1 = saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// Re-login
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		int initialBG = base.initialBgCount();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);
		System.out.println("Bulk Tag done");

		List<String> notifyId_2 = saveSearch.verifyExecuteAndReleaseNotify(initialBG, 1);
		if (notifyId_1 == notifyId_2) {
			base.failedStep("shows same notification");
		} else {
			base.passedStep("Doesn't show same notification after re-login the session");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TestCase Id:53895 Date:27/9/2022
	 * @Description :To verify, As an Sys Admin user login I will able to
	 *              Impersonate from Sys Admin to Project Admin & I will be able to
	 *              go to Doc List page from current search & able to perform Doc
	 *              List actions
	 */
	@Test(description = "RPMXCON-53895", enabled = true, groups = { "regression" })
	public void verifyingNotificationInDocListPage() throws InterruptedException, Exception {
		DocListPage docList = new DocListPage(driver);
		base.stepInfo("Test case Id: RPMXCON-53895 Notification Component");
		base.stepInfo(
				"To verify, As an Sys Admin user login I will able to Impersonate from Sys Admin to Project Admin & I will be able to go to Doc List page from current search & able to perform Doc List actions");

		String Tag = "Tag" + Utility.dynamicNameAppender();
		int DocCount = 2;
		// Login as User
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("logged in as : " + Input.sa1userName);

		// impersonate SA to PA
		base.impersonateSAtoPA();

		// create Tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(Tag, "Select Tag Classification");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(docList.getBackToSourceBtn(), "DocList Page");
		int initialBg = base.initialBgCount();
		docList.documentSelection(DocCount);
		docList.bulkTagExisting(Tag);

//		verify notification in Doclist Page
		base.checkNotificationCount(initialBg, 1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		base.waitTillElemetToBeClickable(tf.getTag_ToggleDocCount());
		tf.getTag_ToggleDocCount().waitAndClick(2);
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(tf.getTagandCount(Tag,DocCount),"BulkAction document Count");
		// logout
		loginPage.logout();

	}
	
	
	
	
	/**
	 * @authorBrundha TestCase id:RPMXCON-54154 Date:27/09/2022
	 * @Description To verify as an user login into the Application, When user
	 *              applying/unapplying Bulk Folder from Doc List, user will be able
	 *              to see the background task in notification window
	 */
	@Test(description = "RPMXCON-54154", enabled = true, groups = { "regression" })
	public void verifyingBulkfolder_UnFolderAllUsersNotification() throws Exception {
		DocListPage docList = new DocListPage(driver);
		String foldername = "Folder" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-54154 Notification Component");
		base.stepInfo(
				"To verify as an user login into the Application, When user applying/unapplying Bulk Folder from Doc List, user will be able to see the background task in notification window");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		loginPage.logout();

		String[] username = { Input.pa1userName, Input.rmu1userName, Input.rev1userName };
		String[] password = { Input.pa1password, Input.rmu1password, Input.rev1password };

		for (int i = 0; i < username.length; i++) {
			loginPage.loginToSightLine(username[i], password[i]);
			base.stepInfo("User successfully logged into slightline webpage as with " + username[i]);

			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.ViewInDocList();
			base.ValidateElement_Presence(docList.getBackToSourceBtn(), "DocList Page");
			int initialBg = base.initialBgCount();
			docList.documentSelection(2);
			docList.bulkFolderExisting(foldername);

			// verify bulkFolder Notification
			base.checkNotificationCount(initialBg, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Folder");
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			docList.documentSelection(2);
			driver.scrollPageToTop();
			sessionSearch.bulkUnFolder(foldername);

			// verify unfolder Notification
			base.checkNotificationCount(initialBg, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Unfolder");
			loginPage.logout();
		}

	}

	/**
	 * @authorBrundha TestCase id:RPMXCON-54153 Date:27/09/2022
	 * @Description To verify as an user login into the Application, When user
	 *              applying/unapplying Bulk Folder from Basic search, user will be
	 *              able to see the background task in notification window
	 */
	@Test(description = "RPMXCON-54153", enabled = true, groups = { "regression" })
	public void verifyingBulkfolder_UnFolderInSearchPage() throws Exception {

		String foldername = "Folder" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-54153 Notification component");
		base.stepInfo(
				"To verify as an user login into the Application, When user applying/unapplying Bulk Folder from Basic search, user will be able to see the background task in notification window");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		loginPage.logout();

		// Login As user
		String[] username = { Input.pa1userName, Input.rmu1userName, Input.rev1userName };
		String[] password = { Input.pa1password, Input.rmu1password, Input.rev1password };

		for (int i = 0; i < username.length; i++) {
			loginPage.loginToSightLine(username[i], password[i]);
			base.stepInfo("User successfully logged into slightline webpage as with "+username[i]);

			sessionSearch.basicContentSearch(Input.testData1);
			int initialBg = base.initialBgCount();
			sessionSearch.bulkFolderExisting(foldername);

			// verify bulkFolder Notification
			base.checkNotificationCount(initialBg, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Folder");
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			sessionSearch.bulkUnFolder(foldername);

			// verify unfolder Notification
			base.checkNotificationCount(initialBg, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Unfolder");

			loginPage.logout();

		}

	}
}
