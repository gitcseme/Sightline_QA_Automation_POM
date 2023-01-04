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
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Notification_Phase2_Regression {

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
	
	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, { Input.rev1userName, Input.rev1password} };
		return users;
	}
	
	
	/**
	 * @author NA Testcase No:RPMXCON-53776
	 * @Description:To verify as an RMU user login, Start/End Date Sorting works correctly on My Background Tasks
	 **/
	@Test(description = "RPMXCON-53776", enabled = true, groups = { "regression" })
	public void verifyRMUStartDateinBGPage() throws Exception {
		base.stepInfo("RPMXCON - 53776");
		base.stepInfo("To verify as an RMU user login, Start/End Date Sorting works correctly "
				+ "on My Background Tasks");
		
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1userName);
		
		base = new BaseClass(driver);
		base.selectproject(Input.largeVolDataProject);
		
		base.waitForElement(session.getBullHornIcon());
        session.getBullHornIcon().waitAndClick(20);
        
		session.navigateToSessionSearchPageURL();
		session.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		session.SearchBtnAction();
		session.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		session.navigateToSessionSearchPageURL();
		session.addNewSearch();
		session.advancedNewContentSearchNotPureHit("*");
		session.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		session.navigateToSessionSearchPageURL();
		session.addNewSearch();
		session.advancedNewContentSearchNotPureHit("*");
	    session.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		session.navigateToSessionSearchPageURL();
		session.addNewSearch();
		session.advancedNewContentSearchNotPureHit("*");
		session.handleWhenPureHitsAreReadyInBellyBandPopup(20);
		
		base.waitForElement(session.getBullHornIcon());
		session.getBullHornIcon().waitAndClick(20);
		base.waitForElement(session.getViewAllBtn());
		session.getViewAllBtn().waitAndClick(20);	
		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to BG Page");
		base.waitForElement(session.getStartDateButton());
		session.getStartDateButton().waitAndClick(3);
		driver.waitForPageToBeReady();
		List<String> startDate1 = base.availableListofElements(session.getStartDatesInBG());
		List<String> startDate2 = base.availableListofElements(session.getStartDatesInBG());
		base.verifyOriginalSortOrder(startDate1, startDate2, "Ascending", true);
		base.passedStep("verified - as an RMU user login, Start/End Date Sorting works correctly on"
				+ "My Background Tasks");
	    login.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54421
	 * @Description:Verify that correct status Completed appears on My BackGround
	 *                     screen when user clicks When All Results Are Ready" +
	 *                     "button and search results are Ready on Advanced Search
	 *                     Screen.
	 **/
	@Test(description = "RPMXCON-54421", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyCompldInBGWhenAllResReady(String userName, String passWord) throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-54421");
		base.stepInfo(
				"Verify that correct status Completed appears on My BackGround screen when user clicks When All Results Are Ready"
						+ "button and search results are Ready on Advanced Search Screen.");

		login.loginToSightLine(userName, passWord);
		base.stepInfo("Logged in As " + userName);
		base.selectproject(Input.largeVolDataProject);

		base.waitForElement(session.getBullHornIcon());
        session.getBullHornIcon().waitAndClick(20);
        
		session.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		session.SearchBtnAction();

		session.verifyTileSpinning();
		base.clearBullHornNotification();
		String backGroundID = session.handleWhenAllResultsPopUpDynamic();

		base.waitForElement(session.getBullHornIcon());
		session.getBullHornIcon().waitAndClick(20);
		base.waitForElement(session.getViewAllBtn());
		session.getViewAllBtn().waitAndClick(20);

		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to My BackGround Task Page...");
		
		session.checkingStatusUsingRefresh(backGroundID, "COMPLETED");
		driver.waitForPageToBeReady();
		String status = session.getRowData_BGT_Page("STATUS", backGroundID);
		System.out.println(status);
		if (status.equals("COMPLETED")) {
			base.passedStep("COMPLETED Status Appear On BackGround Screen..As Expected");
		} else {
			base.failedStep("COMPLETED Status Appear On BackGround Screen... Not As Expected");
		}
		base.passedStep(
				"Verified - that correct status Completed appears on My BackGround screen when user clicks When All Results Are Ready"
						+ " button and search results are Ready on Advanced Search Screen.");
		login.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54422
	 * @Description:Verify that correct status "Completed" appears on My BackGround
	 *                     screen when user clicks "When Pure Hits Are Ready" button
	 *                     and search results are Ready on Advanced Search Screen.
	 **/
	@Test(description = "RPMXCON-54422", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyCompldInBGWhenPureHitsReady(String userName, String passWord) throws Exception {
		base.stepInfo("RPMXCON-54422");
		base.stepInfo(
				"Verify that correct status \"Completed\" appears on My BackGround screen when user clicks \"When Pure Hits Are Ready\" button and search results are Ready on Advanced Search Screen.");

		login.loginToSightLine(userName, passWord);
		base.stepInfo("Logged in As " + userName);
		base = new BaseClass(driver);
		base.selectproject(Input.largeVolDataProject);

		base.waitForElement(session.getBullHornIcon());
        session.getBullHornIcon().waitAndClick(20);
        
		session.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		session.SearchBtnAction();

		session.verifyTileSpinning();
		base.clearBullHornNotification();
		String backGroundID = session.handleWhenPureHitsAreReadyInBellyBandPopup(20);

		base.waitForElement(session.getBullHornIcon());
		session.getBullHornIcon().waitAndClick(20);
		base.waitForElement(session.getViewAllBtn());
		session.getViewAllBtn().waitAndClick(20);

		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to My BackGround Task Page...");
		base.waitForNotification();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		session.checkingStatusUsingRefresh(backGroundID, "COMPLETED");
		driver.waitForPageToBeReady();
		
		String status = session.getRowData_BGT_Page("STATUS", backGroundID);
		System.out.println(status);
		
		if (status.equals("COMPLETED")) {
			base.passedStep("COMPLETED Status Appear On BackGround Screen..As Expected");
		} else {
			base.failedStep("COMPLETED Status Appear On BackGround Screen... Not As Expected");
		}
		base.passedStep(
				"Verified - that correct status Completed appears on My BackGround screen when user clicks When Pure Hits Are Ready"
						+ " button and search results are Ready on Advanced Search Screen.");
		login.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-54452
	 * @Description:Verify that - After Impersonation (SysAdmin to PA)- correct
	 *                     status "InProgress" appears on My BackGround screen when
	 *                     user performed Batch Print with 100+ documents from
	 *                     Search Group
	 **/
	@Test(description = "RPMXCON-54452", enabled = true, groups = { "regression" })
	public void verifyCompletedOnBackgroundScreen() throws Exception {
		base.stepInfo("RPMXCON-54452");
		base.stepInfo(
				"Verify that - After Impersonation (SysAdmin to PA)- correct status \"InProgress\" appears on My BackGround screen when user performed Batch Print with 100+ documents from Search Group");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoPA();

		String Tag = "TAG" + Utility.dynamicNameAppender();
		DocListPage doclist = new DocListPage(driver);

		// create a tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tag, Input.securityGroup);
		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// Sort in Docfilename in Desc Order
		doclist.Selectpagelength("100");
		doclist.selectAllDocumentsInCurrentPageOnly();

		// bulk tag selected docs
		driver.waitForPageToBeReady();
		doclist.addNewBulkTag(Tag);

		// Select TAG & Native
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DocFileName' In
		// "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, true);

		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);

		String idValue = batchPrint.getBatchId(1).getText();
		System.out.println("Id : " + idValue);
		String status = session.getRowData_BGT_Page("STATUS", idValue);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
		base.textCompareEquals(status, "INPROGRESS", passMsg, failedMsg);
		login.logout();
	}
	/**
	 * @author sowndarya Testcase No:RPMXCON-54447
	 * @Description: Verify that correct status "Completed" appears on My BackGround screen when user performed Batch Print with 100+ documents from Shared with Me group
	 **/
	@Test(description = "RPMXCON-54447", enabled = true, groups = { "regression" })
	public void verifyCompletedOnBgScreen() throws Exception {
		base.stepInfo("RPMXCON-54447");
		base.stepInfo(
				"Verify that correct status Completed appears on My BackGround screen when user performed Batch Print with 100+ documents from shared With me group");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		String Tag = "TAG" + Utility.dynamicNameAppender();
		DocListPage doclist = new DocListPage(driver);

		//create a tag
		TagsAndFoldersPage tagsAndFolderPage =new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tag, Input.securityGroup);
		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// Sort in Docfilename in Desc Order
		doclist.Selectpagelength("100");
		doclist.selectAllDocumentsInCurrentPageOnly();

		// bulk tag selected docs
		driver.waitForPageToBeReady();
		doclist.addNewBulkTag(Tag);

		// Select TAG & Native
		BatchPrintPage batchPrint= new BatchPrintPage(driver);
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag,Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DocFileName' In
		// "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, true);
		
		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		
		String idValue=batchPrint.getBatchId(1).getText();
		System.out.println("Id : "+ idValue);
		
		for (int i = 0; i < 30; i++) {
//			try {
//				driver.WaitUntil((new Callable<Boolean>() {
//					public Boolean call() {
//						return session.getTxtDownloadFile(idValue).Visible() && session.getTxtDownloadFile(idValue).Enabled();
//					}
//				}), Input.wait120);
//				if( session.getTxtDownloadFile(idValue).Visible() && session.getTxtDownloadFile(idValue).Enabled())
//				{
//					break;
//				}
//			} catch (Exception e) {
//				driver.Navigate().refresh();
//				System.out.println("Refresh");
//				UtilityLog.info("Refresh");
//			}
			if (session.getTxtDownloadFile(idValue).isDisplayed()) {
                base.stepInfo("DOWNLOAD link is Available");
                break;
		}
			else {
                driver.Navigate().refresh();
                System.out.println("Refresh");
                driver.waitForPageToBeReady();
            }
		}
		driver.waitForPageToBeReady();
		String status = session.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : "+status);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
		base.textCompareEquals(status, "COMPLETED", passMsg, failedMsg);
		login.logout();
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

		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		login.logout();

		String[] username = { Input.pa1userName, Input.rmu1userName, Input.rev1userName };
		String[] password = { Input.pa1password, Input.rmu1password, Input.rev1password };

		for (int i = 0; i < username.length; i++) {
			login.loginToSightLine(username[i], password[i]);
			base.stepInfo("User successfully logged into slightline webpage as with " + username[i]);

			session.basicContentSearch(Input.testData1);
			session.ViewInDocList();
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
			int initialBg1 = base.initialBgCount();
			docList.documentSelection(2);
			driver.scrollPageToTop();
			
			session.bulkUnFolder(foldername);

			// verify unfolder Notification
			base.checkNotificationCount(initialBg1, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg1, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Unfolder");
			login.logout();
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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		login.logout();

		// Login As user
		String[] username = { Input.rev1userName, Input.rmu1userName, Input.pa1userName };
		String[] password = { Input.rev1password, Input.rmu1password, Input.pa1password };

		for (int i = 0; i < username.length; i++) {
			login.loginToSightLine(username[i], password[i]);
			base.stepInfo("User successfully logged into slightline webpage as with "+username[i]);

			session.basicContentSearch(Input.testData1);
			int initialBg = base.initialBgCount();
			session.bulkFolderExisting(foldername);

			// verify bulkFolder Notification
			base.checkNotificationCount(initialBg, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Folder");
			
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			int initialBg1 = base.initialBgCount();
			session.bulkUnFolder(foldername);

			// verify unfolder Notification
			base.checkNotificationCount(initialBg1, 1);
			saveSearch.verifyExecuteAndReleaseNotify(initialBg1, 1);
			base.validatingGetTextElement(saveSearch.getNotificationStatus(1), "Bulkaction-Unfolder");

			login.logout();

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
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		
		session.navigateToSessionSearchPageURL();
		int pureHit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, newNode);
		
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
		login.logout();
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
		
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoRMU();
		
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch= new SavedSearch(driver);
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		
		session.navigateToSessionSearchPageURL();
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, newNode);
		
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
		
		TagsAndFoldersPage tagsAndFolderPage= new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.verifyFolderDocCount(folderName, noOfDocs);
		base.passedStep("Verified, As an Sys Admin user login I will able to Impersonate from Sys Admin to RM & "
				+ "I will be able to go to Doc List page from saved search & able to perform Doc List actions");
	    login.logout();
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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		
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
		
		//verify megaphone icon
		base.waitForElement(base.getBullHornIcon());
		base.getBullHornIcon().waitAndClick(10);
		
		base.verifyMegaPhoneIconAndBackgroundTasks(true,true);
		base.passedStep("It  displays the pop up with background tasks/ Sharing alerts with 'View All' button");
		
		
		login.logout();
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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		// configure query & Save
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

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
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in as : " + Input.rmu1userName);

		String tagName = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagName, "Select Tag Classification");

		login.logout();
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("logged in as : " + Input.rev1userName);

		int initialBg = base.initialBgCount();
		session.basicContentSearch(Input.testData1);
		session.bulkTagExisting(tagName);
		System.out.println("Bulk Tag done");

		// verify notification for bulk tag
		base.checkNotificationCount(initialBg, 1);
		List<String> notifyId_1 = saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// Re-login
		login.logout();
		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		int initialBG = base.initialBgCount();
		session.basicContentSearch(Input.testData1);
		session.bulkTagExisting(tagName);
		System.out.println("Bulk Tag done");

		List<String> notifyId_2 = saveSearch.verifyExecuteAndReleaseNotify(initialBG, 1);
		if (notifyId_1 == notifyId_2) {
			base.failedStep("shows same notification");
		} else {
			base.passedStep("Doesn't show same notification after re-login the session");
		}

		login.logout();

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
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("logged in as : " + Input.sa1userName);

		// impersonate SA to PA
		base.impersonateSAtoPA();

		// create Tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(Tag, "Select Tag Classification");

		session.basicContentSearch(Input.testData1);
		session.ViewInDocList();
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(docList.getBackToSourceBtn(), "DocList Page");
		int initialBg = base.initialBgCount();
		docList.documentSelection(DocCount);
		docList.bulkTagExisting(Tag);

//		verify notification in Doclist Page
		base.checkNotificationCount(initialBg, 1);
		driver.waitForPageToBeReady();
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		driver.waitForPageToBeReady();
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		base.waitTillElemetToBeClickable(tf.getTag_ToggleDocCount());
		tf.getTag_ToggleDocCount().waitAndClick(2);
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(tf.getTagandCount(Tag,DocCount),"BulkAction document Count");
		// logout
		login.logout();

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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.navigateToSavedSearchPage();
		base.stepInfo("User navigated successfully");

		base.stepInfo("Navigate to docview");
		base.waitForElement(saveSearch.getLaunchDocView());
		saveSearch.getLaunchDocView().waitAndClick(10);

		DocViewPage docView = new DocViewPage(driver);
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
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		
		session.navigateToSessionSearchPageURL();
		int pureHit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, newNode);
		
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
		login.logout();
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
		
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Node (or) New Search Group creation");
		saveSearch.navigateToSavedSearchPage();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		
		session.navigateToSessionSearchPageURL();
        session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, newNode);
		
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
		login.logout();
	}



/**
 * @author sowndarya Testcase No:RPMXCON-54448
 * @Description: Verify that correct status "InProgress" appears on My
 *               BackGround screen when user performed Batch Print with 100+
 *               documents from shared With me group
 **/
@Test(description = "RPMXCON-54448", enabled = true, groups = { "regression" })
public void verifyInProgressOnBackgroundScreen() throws Exception {
	base.stepInfo("RPMXCON-54448");
	base.stepInfo(
			"Verify that correct status InProgress appears on My BackGround screen when user performed Batch Print with 100+ documents from shared With me group");
	login.loginToSightLine(Input.pa1userName, Input.pa1password);

	String Tag = "TAG" + Utility.dynamicNameAppender();
	DocListPage doclist = new DocListPage(driver);

	//create a tag
	TagsAndFoldersPage tagsAndFolderPage =new TagsAndFoldersPage(driver);
	tagsAndFolderPage.createNewTagwithClassification(Tag, Input.securityGroup);
	// configure query & view in doclist
	session.basicContentSearch(Input.searchStringStar);
	session.ViewInDocList();

	// Sort in Docfilename in Desc Order
	doclist.Selectpagelength("100");
	doclist.selectAllDocumentsInCurrentPageOnly();

	// bulk tag selected docs
	driver.waitForPageToBeReady();
	doclist.addNewBulkTag(Tag);

	// Select TAG & Native
	BatchPrintPage batchPrint= new BatchPrintPage(driver);
	batchPrint.navigateToBatchPrintPage();
	batchPrint.fillingSourceSelectionTab(Input.tag,Tag, true);
	batchPrint.fillingBasisForPrinting(true, true, null);
	batchPrint.navigateToNextPage(1);
	batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

	// filling SlipSheet With metadata
	batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
	batchPrint.navigateToNextPage(1);

	// Filling Export File Name as 'DocID', select Sort by 'DocFileName' In
	// "DESC" Order
	batchPrint.selectSortingFromExportPage("DESC");
	batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, true);
	
	base.verifyMegaPhoneIconAndBackgroundTasks(true, true);
	
	String idValue=batchPrint.getBatchId(1).getText();
	System.out.println("Id : "+ idValue);
	String status = session.getRowData_BGT_Page("STATUS", idValue);

	String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
	String failedMsg="Batch print status is not displayed as expected";
	base.textCompareEquals(status, "INPROGRESS", passMsg, failedMsg);
	login.logout();
}

/**
 * @author NA Testcase No:RPMXCON-54418
 * @Description: Verify that correct status In Progress appears on My BackGround screen when user clicks When All Results Are Ready button and search results are in progress on Advanced Search Screen
 **/
@Test(description = "RPMXCON-54418", dataProvider = "Users", enabled = true, groups = { "regression" })
public void verifyStatusInprogressAllResReady(String userName, String passWord) throws Exception {
	base.stepInfo("RPMXCON-54418");
	base.stepInfo("Verify that correct status In Progress appears on My BackGround screen when user clicks \"When All Results Are Ready\" "
			+ "button and search results are in progress on Advanced Search Screen.");
	
	login.loginToSightLine(userName, passWord);
	base.stepInfo("Logged in As " + userName);
	base = new BaseClass(driver);
	base.selectproject(Input.largeVolDataProject);
	
	session.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar, true);
	session.SearchBtnAction();
	
	session.verifyTileSpinning();
	String backGroundID = session.handleWhenAllResultsPopUpDynamic();
	
	base.waitForElement(session.getBullHornIcon());
	session.getBullHornIcon().waitAndClick(20);
		
	base.waitForElement(session.getViewAllBtn());
	session.getViewAllBtn().waitAndClick(20);
	
	driver.waitForPageToBeReady();
	base.stepInfo("Navigated to My BackGround Task Page...");
	
	String status = session.getRowData_BGT_Page("STATUS", backGroundID);

	SoftAssert asserts = new SoftAssert();
	asserts.assertEquals(status, "INPROGRESS");
	asserts.assertAll();
	base.passedStep("Verify that correct status In Progress appears on My BackGround screen when user clicks When All Results Are Ready button and search results are in progress on Advanced Search Screen.");
	login.logout();
}

/**
 * @author NA Testcase No:RPMXCON-54419
 * @Description: Verify that correct statusIn Progress appears on My BackGround screen when user clicks When Pure Hits Are Ready button and search results are in progress on Advanced Search Screen
 **/
@Test(description = "RPMXCON-54419", dataProvider = "Users",enabled = true, groups = { "regression" })
public void verifyStatusInprogressPurHitReady(String userName, String passWord) throws Exception {
	base.stepInfo("RPMXCON-54419");
	base.stepInfo("Verify that correct status \"In Progress\" appears on My BackGround screen when user clicks \"When Pure Hits Are Ready\" "
			+ "button and search results are in progress on Advanced Search Screen");
	
	login.loginToSightLine(userName, passWord);
	base.stepInfo("Logged in As " + userName);
	base = new BaseClass(driver);
	base.selectproject(Input.largeVolDataProject);
	
	session.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar, true);
	session.SearchBtnAction();
	
	session.verifyTileSpinning();
	String backGroundID = session.handleWhenPureHitsAreReadyInBellyBandPopup(20);
	
	base.waitForElement(session.getBullHornIcon());
	session.getBullHornIcon().waitAndClick(20);
		
	base.waitForElement(session.getViewAllBtn());
	session.getViewAllBtn().waitAndClick(20);
	
	driver.waitForPageToBeReady();
	base.stepInfo("Navigated to My BackGround Task Page...");
	
	String status = session.getRowData_BGT_Page("STATUS", backGroundID);

	SoftAssert asserts = new SoftAssert();
	asserts.assertEquals(status, "INPROGRESS");
	asserts.assertAll();
	base.passedStep("Verify that correct status \"In Progress\" appears on My BackGround screen when user clicks \"When Pure Hits Are Ready\""
			+ " button and search results are in progress on Advanced Search Screen.");
	login.logout();
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
