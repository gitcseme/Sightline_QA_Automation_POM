package sightline.batchRedaction;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression2 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
	SoftAssert softAssertion;
	RedactionPage redact;
	DocViewPage docview;
	DocViewMetaDataPage docviewMetadata;
	DocViewRedactions DCRedactions;

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
	 * @Dsecription : [Details for the saved search each row]Verify the 'Select a
	 *              search' section from batch redactions for saved searches row
	 *              details [RPMXCON-53393]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-53393", enabled = true, groups = { "regression" })
	public void verifyBatch() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID : RPMXCON-53393  Batch Redaction");
		base.stepInfo(
				"[Details for the saved search each row]Verify the 'Select a search' section from batch redactions for saved searches row details");

		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		batch.loadBatchRedactionPage(searchName);
		batch.searchToolTipMsg(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	@DataProvider(name = "SpecialCharsSearchName")
	public Object[][] SpecialsChars() {
		return new Object[][] { 
			{ "quality than quanti*", "RPMXCON-53507" },
				{ "qualit? than quanti?y", "RPMXCON-53506" },
				{ "'quality than quantity'", "RPMXCON-53505" } };
	}

	/**
	 * @throws InterruptedException
	 * @created By jayanthi
	 * @description Verify that "View Report & Apply Redactions" pop up appears when
	 *              User tries to Perform Batch Redaction on Saved Query Name
	 *              containing question mark or Single quote or astriek special
	 *              character.
	 */
	@Test(description = "RPMXCON-53507,RPMXCON-53506,RPMXCON-53505", enabled = true, dataProvider = "SpecialCharsSearchName", groups = {
			"regression" })
	public void verifyApplyRedactPopUp_Astriek(String name, String testCaseId) throws InterruptedException {
		String searchName = name + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID :" + testCaseId);

		// Test Case Description
		if (testCaseId.contentEquals("RPMXCON-53507")) {
			base.stepInfo(
					"Verify that \"View Report & Apply Redactions\" pop up appears when User tries to Perform Batch"
							+ " Redaction on Saved Query Name containing asterisk special character.");
		} else if (testCaseId.contentEquals("RPMXCON-53506")) {
			base.stepInfo(
					"Verify that \"View Report & Apply Redactions\" pop up appears when User tries to Perform Batch "
							+ "Redaction on Saved Query Name containing question mark special character.");
		} else if (testCaseId.contentEquals("RPMXCON-53505")) {
			base.stepInfo(
					"Verify that \"View Report & Apply Redactions\" pop up appears when User tries to Perform Batch"
							+ " Redaction on Saved Query Name containing Single quote special character.");
		}

		System.out.println("*************************"+searchName);
		// Search The Query and save search with special charachters
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);

		// Delete Search
		if (testCaseId.contentEquals("RPMXCON-53507") || testCaseId.contentEquals("RPMXCON-53506")) {
			saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		}
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify that Relevant message appears on
	 *         \"Batch Redaction\" screen when User tries to Perform Batch Redaction
	 *         on Saved Query. (RPMXCON-53514)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53514", enabled = true, groups = { "regression" })
	public void verifyRelevantMessageAppear() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login to the application by entering pa credentials
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53514 Saved Search ");
		base.stepInfo(
				"Verify that Relevant message appears on \"Batch Redaction\" screen when User tries to Perform Batch Redaction on Saved Query.");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(Input.testData1);

		// will save the query on created node under my saved search
		session.saveSearchInNodewithChildNode(searchName, newNode);

		batch.verifyNodeAnalyseAndViewBtn(newNode, searchName, null, null);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify when user selects search group with
	 *         multiple searches for batch redactions from saved search page
	 *         (RPMXCON-53391)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53391", enabled = true, groups = { "regression" })
	public void verifySearchGroupWithMultipleSearches() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String searchName1 = "Search" + Utility.dynamicNameAppender();

		// will login to the application by entering pa credentials
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53391 Saved Search ");
		base.stepInfo(
				"Verify when user selects search group with multiple searches for batch redactions from saved search page");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(Input.testData1);
		session.saveSearchInNodewithChildNode(searchName, newNode);

		session.getNewSearchButton().waitAndClick(20);
		session.basicContentSearchWithSaveChanges("prabu", "Yes", "Third");
		session.saveSearchInNewNode(searchName1, newNode);

		batch.verifyNodeAnalyseAndViewBtn(newNode, searchName, null, null);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		base.waitForElement(batch.getSavedSearch(searchName));
		String savedSearch = batch.getSavedSearch(searchName).getText();
		batch.verifyHistoryStatus(savedSearch);

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		login.logout();

	}

	@DataProvider(name = "cjkAndRegular")
	public Object[][] cjkAndRegular() {
		return new Object[][] { { "\"##th[eo]se\"", "RPMXCON-53380" }, { "CJK characters", "RPMXCON-53361" }, };
	}

	/**
	 * @author Jeevitha Description :Verify that Batch Redactions when selected
	 *         Saved search is with search criteria with CJK characters Saved Search
	 *         page > My Saved Search (RPMXCON-53361) Description :Verify batch
	 *         redaction when saved search query contents either spelling syntax
	 *         with regular expression (RPMXCON- 53380)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53361,RPMXCON-53380", enabled = true, dataProvider = "cjkAndRegular", groups = {
			"regression" })
	public void verifyBatchRedactWithCJKAndSpelling(String data, String testCaseID) throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID : " + testCaseID + "  Bacth Redaction");

		// Test Case Description
		if (testCaseID.contentEquals("RPMXCON-53361")) {
			base.stepInfo(
					"Verify that Batch Redactions when selected Saved search is with search criteria with CJK characters Saved Search page > My Saved Search");
		} else if (testCaseID.contentEquals("RPMXCON-53380")) {
			base.stepInfo(
					"Verify batch redaction when saved search query contents either spelling syntax with regular expression");
		}

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearchInNewNode(searchName, null);

		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify that message font-size displays 19px on
	 *         \"Batch Redaction\" screen when User tries to Perform Batch Redaction
	 *         on Saved Query. (RPMXCON-53515)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53515", enabled = true, groups = { "regression" })
	public void verifyMessageFontSize() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login to the application by entering pa credentials
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53515 Saved Search ");
		base.stepInfo(
				"Verify that message font-size displays 19px on \"Batch Redaction\" screen when User tries to Perform Batch Redaction on Saved Query.");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(Input.testData1);

		// will save the query on created node under my saved search
		session.saveSearchInNodewithChildNode(searchName, newNode);

		batch.verifyNodeAnalyseAndViewBtn(newNode, searchName, null, null);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "No");
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// Delete Search
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify when user clicks 'View and Redact ' for
	 *         the search group with multiple search groups with saved seaches for
	 *         batch redactions from batch redactions home paage (RPMXCON-53390)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53390", enabled = true, groups = { "regression" })
	public void verifyBathcRedactionWithMultipleSearchGroups() throws InterruptedException {
		String savedSearch1 = "Search" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search" + Utility.dynamicNameAppender();
		int noOfNodesToCreate = 2;
		List<String> newNodeList = new ArrayList<>();

		// will login to the application by entering pa credentials
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53390 Saved Search ");
		base.stepInfo(
				"Verify when user clicks 'View and Redact ' for the search group with multiple search groups with saved seaches for batch redactions from batch redactions home paage");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);

		String parentNode = newNodeList.get(0);
		String childNode = newNodeList.get(1);

		int pureHit1 = session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(savedSearch1, parentNode);
		session.saveSearchInNodewithChildNode(savedSearch2, childNode);

		batch.verifyNodeAnalyseAndViewBtn(parentNode, savedSearch1, childNode, savedSearch2);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);

		// verify History status
		base.waitForElement(batch.getSavedSearch(savedSearch1));
		batch.getSavedSearch(savedSearch1).waitAndClick(20);
		String savedSearch = batch.getSavedSearch(savedSearch1).getText();
		batch.verifyHistoryStatus(savedSearch);

		// Delete Search
		saveSearch.deleteNode(Input.mySavedSearch, parentNode);
		login.logout();
	}

	/**
	 * @throws InterruptedException
	 * @created By jayanthi
	 * @description Verify that no action on click of 'No' button from belly band
	 *              message displays" on click of delete icon from doc view
	 *              redaction
	 */
	@Test(description = "RPMXCON-53405", enabled = true, groups = { "regression" })
	public void verifyDeletePopUp() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID :RPMXCON-53405");

		// Test Case Description
		base.stepInfo("Verify that no action on click of 'No' button from belly band message displays"
				+ " on click of delete icon from doc view redaction");

		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);

		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.docViewFromSS("View in DocView");
		driver.waitForPageToBeReady();
		docview.verifyRedactionPanel();
		DCRedactions.verifyRedactionsSubMenu();
		DCRedactions.VerifyDeletePopUp_BatchRedact(false);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @throws InterruptedException
	 * @created By jayanthi
	 * @description Verify once batch redaction is complete for the selected saved
	 *              search then on doc view with same saved search it should display
	 *              the redactions panel
	 * 
	 */

	@Test(description = "RPMXCON-53394", enabled = true, groups = { "regression" })
	public void verifyRedactionHistoryInj() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID :RPMXCON-53394");

		// Test Case Description
		base.stepInfo("Verify once batch redaction is complete for the selected saved search then on doc view with same"
				+ " saved search it should display the redactions panel");

		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		redact.AddRedaction(tagName, "RMU");
		batch.savedSearchBatchRedaction1(tagName, searchName);
		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.docViewFromSS("View in DocView");
		driver.waitForPageToBeReady();
		docview.verifyRedactionPanel();
		DCRedactions.verifyRedactionsSubMenu();
		docviewMetadata.verifyBrowseAllHistory_RedactionAddedStatus(true);
		redact.DeleteRedaction(tagName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @author Jeevitha Description : Verify that 'Download the Pre-Redaction
	 *         Report' link from Pre-Redaction Report pop up (RPMXCON-53363)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53363", enabled = true, groups = { "regression" })
	public void verifyPreRedactionReport() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53363  Bacth Redaction ");
		base.stepInfo("Verify that 'Download the Pre-Redaction Report' link from Pre-Redaction Report pop up");

		// Search The Query
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(searchName, null);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.verifyPreRedactionReport();

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify column sorting from Batch Redaction
	 *         History of Batch Redactions page (RPMXCON-53371)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53371", enabled = true, groups = { "regression" })
	public void verifyColumnSorting() throws InterruptedException {
		String searchName = "test" + Utility.dynamicNameAppender();
		List<String> originalOrderedList;
		List<String> afterSortList;
		String sortType = "Descending";

		// will login to the application by entering rmu credentials
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID : RPMXCON-53371 Batch Redaction");
		base.stepInfo("Verify column sorting from Batch Redaction History of Batch Redactions page");

		// will load the session search page and search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// will perform the batchRedaction action
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getPopupYesBtn().Click();

		// get the batch id which created after batchRedaction
		driver.waitForPageToBeReady();
		String createdBatchId = batch.getBatchId(searchName).getText();

		// verify Sorting Order
		originalOrderedList = base.availableListofElements(batch.getBatchIDs());
		afterSortList = base.availableListofElements(batch.getBatchIDs());

		System.out.println(originalOrderedList);
		base.passedStep(originalOrderedList + " original order");

		if (sortType.equals("Ascending")) {
			Collections.sort(afterSortList);
		} else if (sortType.equals("Descending")) {
			Collections.sort(afterSortList, Collections.reverseOrder());
		}
		System.out.println(afterSortList);
		base.stepInfo(afterSortList + " order after Sorting");

		softAssertion.assertEquals(afterSortList, originalOrderedList);

		if (afterSortList.equals(originalOrderedList)) {
			System.out.println("Sorting order maintained");
			base.passedStep(originalOrderedList + " Sorting order maintained");
		} else {
			System.out.println("Sorting order failed");
			base.failedStep("Sorting order failed");
		}

		if (createdBatchId.equals(afterSortList.get(0))) {
			softAssertion.assertEquals(createdBatchId, afterSortList.get(0));
			base.passedStep(createdBatchId + " : iS the most Recent BatchID");
		}

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @author Jeevitha Description : Verify that batch redaction anaysis report
	 *         should not overcount the expected redactions (RPMXCON-53512)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53512", enabled = true, groups = { "regression" })
	public void verifyAnalysisReport() throws InterruptedException, IOException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53512  Bacth Redaction ");
		base.stepInfo("Verify that batch redaction anaysis report should not overcount the expected redactions");

		// Search The Query
		int purehit = session.basicContentSearch("wild*");
		session.saveSearch(searchName);

		batch.loadBatchRedactionPage(searchName);
		batch.performAnalysisGroupForRedcation(searchName, null, purehit, true, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify when user clicks 'Analyze Group for
	 *         Redaction' for the saved search group under 'My Saved Searches'
	 *         (RPMXCON-53366)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53366", enabled = true, groups = { "regression" })
	public void verifyWhenUserClickAnalyzeGroup() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53366 Saved Search ");
		base.stepInfo(
				"Verify when user clicks 'Analyze Group for Redaction' for the saved search group under 'My Saved Searches'");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");

		// will load the session search page and search
		int purehit = session.basicContentSearch(Input.testData1);

		// will save the query on created node under my saved search
		session.saveSearchInNewNode(searchName, newNode);

		batch.loadBatchRedactionPage(searchName);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false, true);

		// Delete Search
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @author Jeevitha Description : Verify that redactions added with batch
	 *         redactions should be displayed for document on doc view when user do
	 *         not have the 'Redactions' rights (RPMXCON-53430)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53430", enabled = true, groups = { "regression" })
	public void verifyRedactionRight() throws InterruptedException, IOException {

		// will login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Tescase ID :RPMXCON-53430 Saved Search ");
		base.stepInfo(
				"Verify that redactions added with batch redactions should be displayed for document on doc view when user do not have the 'Redactions' rights");

		batch.assignRedactionRights(Input.rmu1userName, false);
		login.logout();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.ViewInDocView();

		// verify Redaction Panel
		docview.verifyPanel();

		login.logout();

		// will login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		batch.assignRedactionRights(Input.rmu1userName, true);
		login.logout();
	}

	/**
	 * @author Jeevitha Description :Verify that after editing the saved search with
	 *         regular expression then batch redaction should be successful with
	 *         same saved search (RPMXCON-53378)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53378", enabled = true, groups = { "regression" })
	public void verifyBatchRedactionAfterSearchEdit() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String searchName1 = "Search" + Utility.dynamicNameAppender();
		String data = "\"##[0-9]{5}-[0-9]{4}\"";

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53378 Saved Search ");
		base.stepInfo(
				"Verify that after editing the saved search with regular expression then batch redaction should be successful with same saved search");

		// Search The Query and save search
		session.basicContentSearch(data);
		session.saveSearch(searchName);

		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		// Overwrite Search / Modify
		session.modifySearchTextArea(1, data, Input.testData1, "Save");
		driver.waitForPageToBeReady();
		session.searchAndReturnPureHit_BS();
		session.saveSearchAtAnyRootGroup(searchName1, Input.mySavedSearch);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName1, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyHistoryStatus(searchName1);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jayanthi Description : Verify that once batch redaction is successful
	 *         user should be able to view the report on click of 'Click here for
	 *         report' link for same from batch redaction history
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48807", enabled = true, groups = { "regression" })
	public void verifyReport() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-48807");
		base.stepInfo(
				"Verify that once batch redaction is successful user should be able to view the report on click of 'Click here for report'"
						+ " link for same from batch redaction history");
		String Filename1 = base.GetLastModifiedFileName();
		base.stepInfo(Filename1 + "Last Modified File name before Downloading the report");
		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		// will perform the batchRedaction action
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getPopupYesBtn().Click();
		batch.verifyBatchHistoryStatus(searchName);
		batch.VerifyBGMessageForReportDownload(searchName);
		Thread.sleep(10000); // added to wait until downnload completed and updated in the directory.
		String Filename2 = base.GetLastModifiedFileName();
		base.stepInfo(Filename2 + "Last Modified File name after Downloading the report");
		if ((Filename2 != Filename1)) {
			String actualfileName = FilenameUtils.getBaseName(Filename2);
			String fileFormat = FilenameUtils.getExtension(Filename2);
			String expectedFormat = "xlsx";
			base.stepInfo("Downloaded file name is" + actualfileName);
			base.stepInfo("Downloaded fileFormat is" + fileFormat);
			Assert.assertEquals(fileFormat, expectedFormat);
			base.passedStep("Sucessfully verified whether the File Exported in excel format");
		} else {
			base.failedStep("File not downloaded");
		}

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Modified :
	 * @Description :Verify that Batch Redaction should be successful when selected
	 *              Saved search is with phrase(s) from Batch Redaction Home
	 *              page(RPMXCON-53344)
	 * @Description :"Verify that Rollback should be successful when batch redaction
	 *              is successful for the Saved search with
	 *              phrase(s)"(RPMXCON-53345)
	 * @Description :Verify that Batch Redaction should be successful when selected
	 *              Saved search is with sentence from Batch Redaction Home
	 *              page(RPMXCON-53346)
	 * @Description :Verify that Rollback should be successful when selected Saved
	 *              search is with sentence(RPMXCON-53347)
	 * @throws InterruptedException
	 */
	@DataProvider(name = "phraseSentenceAndReg")
	public Object[][] phraseSentenceAndReg() {
		return new Object[][] { { "\"the price of reliability\"", "RPMXCON-53344", "RPMXCON-53345" },
				{ "\"the region cannot be known without understanding the entire North American Grid\"",
						"RPMXCON-53346", "RPMXCON-53347" }, };
	}

	@Test(description = "RPMXCON-53344", enabled = true, dataProvider = "phraseSentenceAndReg", groups = {
			"regression" })
	public void verifyBatchRedactWithPhraseAndSentence(String data, String testCaseId1, String testCaseId2)
			throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID : " + testCaseId1 + "  Bacth Redaction");
		base.stepInfo("Testcase ID : " + testCaseId2 + "  Bacth Redaction");

		// Test Case Description
		if (testCaseId1.contentEquals("RPMXCON-53344")) {
			base.stepInfo(
					"Verify that Batch Redaction should be successful when selected Saved search is with phrase(s) from Batch Redaction Home page");
			base.stepInfo(
					"Verify that Rollback should be successful when batch redaction is successful for the Saved search with phrase(s)");
		} else if (testCaseId1.contentEquals("RPMXCON-53346")) {
			base.stepInfo(
					"Verify that Batch Redaction should be successful when selected Saved search is with sentence from Batch Redaction Home page");
			base.stepInfo("Verify that Rollback should be successful when selected Saved search is with sentence");
		}

		// Search The Query
		int purehit = session.basicContentSearch(data);
		session.saveSearch(searchName);

		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		base.stepInfo("Clicked Yes Button");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// perform RollBack
		batch.verifyRollback(searchName, "No");
		batch.verifyRollback(searchName, "Yes");

		// verify Rollback Notification
		batch.rollbackBatchRedactionReport();

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		driver.waitForPageToBeReady();
		docviewMetadata.verifyBrowseAllHistory_RedactionAddedStatus(false);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jayanthi
	 * @Description :Verify that result should be displayed on ''My BackgroundTask
	 *              Page' for the selected Filter type 'Batch redaction' / 'Batch
	 *              unredaction'.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53341", enabled = true, groups = { "regression" })
	public void verifyBGTask() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53341");
		base.stepInfo("Verify that result should be displayed on ''My BackgroundTask Page' for the selected Filter"
				+ " type 'Batch redaction' / 'Batch unredaction'");

		// Search The Query
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		// will perform the batchRedaction action
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getPopupYesBtn().Click();
		batch.verifyHistoryStatus(searchName);

		final int Bgcount = base.initialBgCount();
		System.out.println(Bgcount);
		base.waitForElement(batch.getRollbackbtn(searchName));
		batch.getRollbackbtn(searchName).Click();
		batch.getPopupYesBtn().Click();
		String ExpectedMsg = "Your request to Roll Back this Batch Redaction has been added to the background. Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.";
		base.VerifySuccessMessageB(ExpectedMsg);
		batch.verifyStatusInBackGroundTaskPg(Bgcount);
		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that 'Analyze Group for Redaction' should be displayed
	 *              for search group on batch redaction page(RPMXCON-53357)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53357", enabled = true, groups = { "regression" })
	public void verifyTabsInBR() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String searchName1 = "Search2" + Utility.dynamicNameAppender();
		String searchName2 = "Search1" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53357 Saved Search ");
		base.stepInfo(
				"Verify that 'Analyze Group for Redaction' should be displayed for search group on batch redaction page");

		// create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.shareSearchDefaultSG, "RMU", "Yes");

		// pre -req
		session.basicContentSearch(Input.testData1);
		session.saveSearchAtAnyRootGroup(searchName2, Input.shareSearchDefaultSG);

		// check Analyse before search is Saved
		batch.loadBatchRedactionPage(newNode);
		batch.verifyAnalyzeBtn(null, newNode);

		// check Analyse from default tab before search
		batch.loadBatchRedactionPage(Input.shareSearchDefaultSG);
		batch.verifyAnalyzeBtn(null, newNode1);

		// search query and save in node
		base.selectproject();
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearchAtAnyRootGroup(searchName1, Input.shareSearchDefaultSG);
		session.saveSearchInNewNode(searchName, newNode);

		// Check Analyse btn After search is saved
		batch.loadBatchRedactionPage(newNode);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false, true);
		batch.performViewReportAndApplyRedactions(newNode);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// check Analyse from default tab after search
		batch.performAnalysisFromDefaultTab(searchName1, null);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName1);

		// Check Analyse btn after performing Batch redaction
		batch.loadBatchRedactionPage(newNode);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false, true);

		// check Analyse from default tab after performing Batch redaction
		batch.performAnalysisFromDefaultTab(searchName1, null);

		// Delete Search
		saveSearch.deleteSearch(searchName1, Input.shareSearchDefaultSG, "Yes");
		saveSearch.deleteSearch(searchName2, Input.shareSearchDefaultSG, "Yes");

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNode1);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify Batch Redactions search group section after moving the
	 *              saved searches under 'My saved searches'(RPMXCON-53356)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53356", enabled = true, groups = { "regression" })
	public void verifyMoveAction() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53356 Saved Search ");
		base.stepInfo(
				"Verify Batch Redactions search group section after moving the saved searches under 'My saved searches'");

		// create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");

		// search query and save in node
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(searchName, newNode);

		// Move Search from one folder to another folder
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.moveSavedSearchToNode(searchName, Input.mySavedSearch);

		// Check Analyse btn After search is saved
		batch.loadBatchRedactionPage(newNode);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false, true);

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that Rollback should be successful when selected Saved
	 *              search is used with search crietria with OR operator
	 *              (RPMXCON-53355)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53355", enabled = true, groups = { "regression" })
	public void verifyBatchRedactWithOR_operator() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID : RPMXCON-53355  Bacth Redaction");

		// Test Case Description
		base.stepInfo(
				"Verify that Rollback should be successful when selected Saved search is used with search crietria with OR operator");

		// Create saved search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.testData1, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("OR");
		session.basicContentSearchWithSaveChanges(Input.searchString5, "Yes", "Third");

		session.saveSearch(searchName);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// perform RollBack
		batch.verifyRollback(searchName, "No");
		batch.verifyRollback(searchName, "Yes");

		// verify Rollback Notification
		batch.rollbackBatchRedactionReport();

		// To make sure we are in basic search page
		driver.getWebDriver().get(Input.url + "Search/Searches");

		session.ViewInDocView();
		driver.waitForPageToBeReady();
		docviewMetadata.verifyBrowseAllHistory_RedactionAddedStatus(false);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify that DateTime format remains consistent between "Search
	 *              Group" hierarchy and "Batch Redaction History" sections on Batch
	 *              Redaction Home Screen. (RPMXCON-53504)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53504", enabled = true, groups = { "regression" })
	public void verifyDateAndTimeFormat() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53504 Saved Search ");
		base.stepInfo(
				"Verify that DateTime format remains consistent between \"Search Group\" hierarchy and \"Batch Redaction History\" sections on Batch Redaction Home Screen.");

		// Search The Query and save search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// verify DateTime Format of Search Group And BAtch Redaction History Status
		batch.verifyDateTimeFormat(searchName);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify when user selects the saved search with zero document
	 *                     count for batch redactions(RPMXCON-53387)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53387", enabled = true, groups = { "regression" })
	public void verifyAnalysisBtnForZeroDoc() throws InterruptedException, IOException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53387  Bacth Redaction ");
		base.stepInfo("Verify when user selects the saved search with zero document count for batch redactions");

		// Search The Query
		int purehit = session.basicContentSearch("abcd");
		session.saveSearch(searchName);

		batch.loadBatchRedactionPage(searchName);
		batch.performAnalysisGroupForRedcation(searchName, null, purehit, false, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description :Verify when user deletes the redaction from document default
	 *              view which is added with batch redactions(RPMXCON-53408)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53408", enabled = true, groups = { "regression" })
	public void deleteRedactionAndVerifyCount() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53408 Saved Search ");
		base.stepInfo(
				"Verify when user deletes the redaction from document default view which is added with batch redactions");

		// Search batch redacted query and travel to doc view Page
		session.basicContentSearch(Input.testData1);
		session.ViewInDocView();

		// verify count before deletion
		docview.verifyRedactionPanel();

		DCRedactions.VerifyDeletePopUp_BatchRedact(true);

		// verify count after deletion
		docview.verifyRedactionPanel();
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify that for selected Saved Search and then Redaction Tag and
	 *                     clicking on 'View Report and Apply Redaction' should
	 *                     start the batch redaction execution(RPMXCON-53331)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53331", enabled = true, groups = { "regression" })
	public void verifyCancelAndRollback() throws InterruptedException, IOException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53331  Bacth Redaction ");
		base.stepInfo(
				"Verify that for selected Saved Search and then Redaction Tag and clicking on 'View Report and Apply Redaction' should start the batch redaction execution");

		// Search The Query
		int purehit = session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// clicked no btn in Cancel redaction popup
		batch.verifyCancelAndRollBack(searchName, false);

		// clicked Yes btn in Cancel redaction popup
		batch.verifyCancelAndRollBack(searchName, true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @description :Verify that batch redaction should be successful on click of
	 *              'View and Redact' for the saved search[10/100 Batch Redacting]
	 *              under the search group which is 'Analyzed Group for Redaction'
	 *              (RPMXCON-53334)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53334", enabled = true, groups = { "regression" })
	public void verifyBROnClickOfViewRedact() throws InterruptedException, IOException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53334 Saved Search ");
		base.stepInfo(
				"Verify that batch redaction should be successful on click of 'View and Redact' for the saved search[10/100 Batch Redacting] under the search group which is 'Analyzed Group for Redaction'");

//		login.editProfile("English - United States");

		// create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");

		// search query and save in node
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearchInNewNode(searchName, newNode);

		// Check Analyse btn Before batch readction
		batch.loadBatchRedactionPage(newNode);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false, true);
		batch.performViewReportAndApplyRedactions(newNode);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		// Check Analyse btn After batch readction
		driver.Navigate().refresh();
		batch.loadBatchRedactionPage(newNode);
		batch.performAnalysisGroupForRedcation(searchName, newNode, purehit, false, true);

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @description : [Covered localization for message]Verify that bull horn
	 *              notification should be displayed once background task for
	 *              Analyze Search for Redaction is completed and on click of the
	 *              message should be able to download the report (RPMXCON-53332)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53332", enabled = false, groups = { "regression" })
	public void verifyAnalysisReportInGerman() throws InterruptedException, IOException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// will login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Tescase ID :RPMXCON-53332  Bacth Redaction ");
		base.stepInfo(
				"[Covered localization for message]Verify that bull horn notification should be displayed once background task for Analyze Search for Redaction is completed and on click of the message should be able to download the report");

		// Search The Query
		int purehit = session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		batch.loadBatchRedactionPage(searchName);
		login.editProfile("German - Germany");
		batch.loadBatchRedactionPage(searchName);
		batch.performAnalysisGroupForRedcation(searchName, null, purehit, false, true);

		login.editProfile("English - United States");
		batch.verifyBatchRedactionFileDownload2(true);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @description : Verify that belly band message should be displayed on click of
	 *              'Rollback' button and message on confirming from the belly band
	 *              message (RPMXCON-53339)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53339", enabled = false, groups = { "regression" })
	public void verifyBatchRedactRollbackInGerman() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Testcase ID : RPMXCON-53339  Bacth Redaction");

		// Test Case Description
		base.stepInfo(
				"Verify that belly band message should be displayed on click of 'Rollback' button and message on confirming from the belly band message");

		login.editProfile("English - United States");

		// Search The Query
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, "Yes");

		// verify History status
		batch.verifyBatchHistoryStatus(searchName);

		login.editProfile("German - Germany");
		// perform RollBack
		batch.verifyRollback(searchName, "Yes");

		// verify Rollback Notification
		batch.rollbackBatchRedactionReport();

		login.editProfile("English - United States");
		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
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
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
		batch = new BatchRedactionPage(driver);
		softAssertion = new SoftAssert();
		docview = new DocViewPage(driver);
		redact = new RedactionPage(driver);
		docviewMetadata = new DocViewMetaDataPage(driver);
		DCRedactions = new DocViewRedactions(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());

		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
//			login.switchProjectToEnglish();
			try {
				login.logout();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

//		try {
//			login.quitBrowser();
//		} finally {
//			// login.clearBrowserCache();
//		}
	}

}
