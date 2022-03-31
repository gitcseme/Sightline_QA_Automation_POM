package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression4 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
	String keywordsArray[] = { "test", "hi", "Than8617167" };
	String keywordsArrayPT[] = { "test" };

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51567 Verify when the user clicks on the X after the text search in a
	 * document
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyTextSearchAfterUserClicksX() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51567");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.redactionIcon());
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		docViewRedact.verifySearchUsingMagnifyingIcon(false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		docViewRedact.verifySearchUsingMagnifyingIcon(false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		docViewRedact.verifySearchUsingMagnifyingIcon(false);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51568 Verify when user navigates to other document while viewing the
	 * search hits and comes back to same document
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 16)
	public void verifyTextSearchAfterUserNavigatesToAnotherDoc() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docViewPage = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51568");
		baseClass.stepInfo(
				"Verify when user navigates to other document while viewing the search hits and comes back to same document");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);

		baseClass.stepInfo("Navigate to another document");
		docViewPage.selectDocIdInMiniDocList(Input.searchDocId);

		baseClass.stepInfo(
				"Verify whether search is cleared,Magnifying icon is displayed when navigates to other doc and come back to same doc where the user did search");
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		softAssert.assertEquals(docViewRedact.getMagnifyingIcon().Displayed().booleanValue(), true);
		Element element = docViewRedact.getHitIcon();
		try {
			if (!element.Stale()) {
				baseClass.failedStep("Search is not cleared");
			} else {
				baseClass.passedStep("Search is cleared successfully");
			}
		} catch (Exception e) {
			baseClass.passedStep("Search is cleared successfully");

		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		baseClass.stepInfo("Navigate to another document");
		docViewPage.selectDocIdInMiniDocList(Input.searchDocId);
		baseClass.stepInfo(
				"Verify whether search is cleared,Magnifying icon is displayed when navigates to other doc and come back to same doc where the user did search");
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		softAssert.assertEquals(docViewRedact.getMagnifyingIcon().Displayed().booleanValue(), true);
		element = docViewRedact.getHitIcon();
		try {
			if (!element.Stale()) {
				baseClass.failedStep("Search is not cleared");
			} else {
				baseClass.passedStep("Search is cleared successfully");
			}
		} catch (Exception e) {
			baseClass.passedStep("Search is cleared successfully");

		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		baseClass.stepInfo("Navigate to another document");
		docViewPage.selectDocIdInMiniDocList(Input.searchDocId);
		baseClass.stepInfo(
				"Verify whether search is cleared,Magnifying icon is displayed when navigates to other doc and come back to same doc where the user did search");
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		softAssert.assertEquals(docViewRedact.getMagnifyingIcon().Displayed().booleanValue(), true);
		element = docViewRedact.getHitIcon();
		try {
			if (!element.Stale()) {
				baseClass.failedStep("Search is not cleared");
			} else {
				baseClass.passedStep("Search is cleared successfully");
			}
		} catch (Exception e) {
			baseClass.passedStep("Search is cleared successfully");

		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51746 Verify that on document navigation options when hits panel
	 * is open then enable/disable should be retained
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 17)
	public void verifyPersistentHitPanelIsRetained() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51746");
		baseClass.stepInfo(
				"Verify that on document navigation options when hits panel is open then enable/disable should be retained");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardNextDocBtn());
		docViewRedact.forwardNextDocBtn().Click();
		baseClass.waitTillElemetToBeClickable(docViewRedact.persistantHitToggle());
		docViewRedact.persistantHitToggle().Click();
		if (docViewRedact.persistantHitToggle().Enabled() == true) {
			baseClass.passedStep("Persistent hit panel is open when navigated to first Doc");
		} else {
			baseClass.failedStep("Persistent hit panel is NOT open when navigated to first Doc");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardToLastDoc());
		docViewRedact.forwardToLastDoc().Click();
		baseClass.waitForElement(docViewRedact.persistantHitToggle());
		String getAttribute = docViewRedact.persistantHitToggle().GetAttribute("data-class");
		System.out.println(getAttribute);
		if (getAttribute.equalsIgnoreCase("true")) {
			baseClass.stepInfo("The persistent hit is ON while document is navigated to last");

		} else {
			baseClass.failedStep("The persistent hit toggle is OFF while document is navigated to last");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51444 Check persistent hits when navigated from assignments to
	 * DocView as RMU and REV
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 18)
	public void verifyPersistentHitNavigation2() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51444");
		baseClass.stepInfo(
				"Verify that > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view in context of an assignment");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.assignmentDistributingToReviewer();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);

// Checking persistent hits and keywords in DocView		
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color2).asHex();
		System.out.println(hex2);
		if (hex2.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next button clickable");
		} else {
			baseClass.failedStep("next button not clickable");
		}
		loginPage.logout();

// Login as Rev and checking	

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentspage.SelectAssignmentByReviewer(assignmentName);

		// Checking persistent hits and keywords in DocView
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color3 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex3 = Color.fromString(color3).asHex();
		System.out.println(hex3);
		if (hex3.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next button clickable");
		} else {
			baseClass.failedStep("next button not clickable");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51445 Verify colour for highlited text Verify Navigation option
	 * from persistent hit panel from basic search
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 19)
	public void verifyPersistentHitNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51445");
		baseClass.stepInfo(
				"Verify that after impersonation > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search");
		loginPage = new LoginPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		if (hex.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next btn clickable");
		} else {
			baseClass.failedStep("next btn not clickable");
		}

		// Impersonation as Rev and checking the above

		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color2).asHex();
		System.out.println(hex);
		if (hex2.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next btn clickable");
		} else {
			baseClass.failedStep("next btn not clickable");
		}
		loginPage.logout();
	}

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 20)
	public void verifyPersistentHitsAfterReassignDocuments() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51763");
		baseClass.stepInfo(
				"Non-Audio Documents assigned from Basic search- Verify that when documents are re-assigned to same/other reviewer in an assignment, any previously saved persistent search hits in the assignment should be displayed in the assignment");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);

		sessionSearch.bulkAssign();

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		try {
			if (assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue()) {
				baseClass.passedStep("User is in doc view in context of an assignment");
				softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("User is not in doc view in context of an assignment");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to remove docs and reassign docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");

		assignmentsPage.removeDocs(Input.rev1userName);

		assignmentsPage.reassignDocs(Input.rev1userName);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to vreify persistent hits are displayed even after removed and reasigned docs");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after remove and reassign docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to delete the created assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		assignmentsPage.deleteAssgnmntUsingPagination(assname);

		softAssert.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51762 Non-Audio Documents assigned from Saved search- Verify that
	 * when documents are re-assigned to same/other reviewer in an assignment, any
	 * previously saved persistent search hits in the assignment should be displayed
	 * in the assignment
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 21)
	public void verifyPersistentHitsAfterReassignDocumentsSavedSearch() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51762");
		baseClass.stepInfo(
				"Non-Audio Documents assigned from Basic search- Verify that when documents are re-assigned to same/other reviewer in an assignment, any previously saved persistent search hits in the assignment should be displayed in the assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		savedSearch.savedSearch_Searchandclick(searchName);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		try {
			if (assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue()) {
				baseClass.passedStep("User is in doc view in context of an assignment");
				softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("User is not in doc view in context of an assignment");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to remove docs and reassign docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");

		assignmentsPage.removeDocs(Input.rev1userName);

		assignmentsPage.reassignDocs(Input.rev1userName);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to vreify persistent hits are displayed even after removed and reasigned docs");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after remove and reassign docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to delete the created assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		assignmentsPage.deleteAssgnmntUsingPagination(assname);

		softAssert.assertAll();
		loginPage.logout();

	}



	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51399 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with metadata [Need to add step to create and use that keyword]
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 23)
	public void verifyHighlightedKeywordsForDocsSearchWithMetadata() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51399");
		baseClass.stepInfo(
				"Verify highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search when documents searched with metadata");

		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51403 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searching with reviewer
	 * remarks/comments
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 24)
	public void verifyHighlightedKeywordsForDocsSearchWithCommentsRemarks() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51403");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assinged after searching with reviewer remarks/comments");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.getCommentsOrRemarksCount("Remark", "\"Remark by Rmu\"");
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51402 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searched with work product
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 25)
	public void verifyHighlightedKeywordsForDocsSearchWithWorkProduct() throws Exception {

		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51402");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assinged after searched with work product");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectAssignmentInWPS(assignmentName);
		sessionSearch.serarchWP();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51400 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with comment/Reviewer Remarks
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 26)
	public void verifyHighlightedKeywordsForDocSearchWithCommentsRemarks() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51400");
		baseClass.stepInfo(
				"Verify highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search when documents searched with comment/Reviewer Remarks");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.getCommentsOrRemarksCount("Remark", "\"Remark by Rmu\"");
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		assignmentsPage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51401 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when assinged documents are searched with metadata
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 27)
	public void verifyHighlightedKeywordsForDocSearchWithMetadata() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51401");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when assinged documents are searched with metadata");

		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		assignmentsPage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	
	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51942 Verify when user is selecting a document to view after
	 * scrolling down the mini doc list child window when 'Loading..' displays in
	 * DocView, the entry for the same document must always present fully in the
	 * visible area of the mini-DocList child window
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 29)
	public void verifyDocInMiniDocListChildWindowAfterScrollingDownTillLoadingTextDisplayed() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51942");
		baseClass.stepInfo(
				"Verify when user is selecting a document to view after scrolling down the mini doc list child window when 'Loading..' displays in DocView, the entry for the same document must always present fully in the visible area of the mini-DocList child window");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(true);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(true);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(true);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		loginPage.logout();

	}


	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51771 Verify that previously saved Persistent hits displayed on
	 * the doc view when documents assigned to same/another reviewer are completed
	 * from edit assignment
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 32)
	public void verifyPersistentHitsAfterCompleteDocumentsSavedSearchGroup() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51771");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits displayed on the doc view when documents assigned to same/another reviewer are completed from edit assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		SavedSearch savedSearch = new SavedSearch(driver);
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		try {
			if (assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue()) {
				baseClass.passedStep("User is in doc view in context of an assignment");
				softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("User is not in doc view in context of an assignment");
		}

		baseClass.stepInfo("Verify whether the panels are displayed in doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to complete docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");

		assignmentsPage.completeDocs(Input.rev1userName);

		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);

		driver.waitForPageToBeReady();

		miniDocList.configureMiniDocListToShowCompletedDocs();

		docView.scrollUntilloadingTextDisplay(false);

		baseClass.waitForElement(docView.getCompletedDocs());

		docView.getCompletedDocs().Click();

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after completing docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to delete the created assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		assignmentsPage.deleteAssgnmntUsingPagination(assname);

		softAssert.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51408 Verify on click of the "eye" icon, terms should be
	 * highlighted those that are set from Manage > Keywords when documents are
	 * searched with work product
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 33)
	public void verifyHighlightedKeywordsForDocSearchWithWorkProduct() throws Exception {

		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51408");
		baseClass.stepInfo(
				"Verify on click of the \"eye\" icon, terms should be highlighted those that are set from Manage > Keywords when documents are searched with work product");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectAssignmentInWPS(assignmentName);
		sessionSearch.serarchWP();
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentsPage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51853 Verify that persistent hits panel should not retain
	 * previously viewed hits for the document on completing the document from
	 * coding form child window
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 34)
	public void verifyPersistentHitsAfterCompletingDocumentsSavedSearchGroup() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51853");
		baseClass.stepInfo(
				"Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document from coding form child window");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		if (assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue()) {
			baseClass.passedStep("User is in doc view in context of an assignment");
			softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue(), true);
		}

		baseClass.stepInfo("Verify whether the panels are displayed in doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		if (docView.getHitPanel().isDisplayed()) {
			baseClass.passedStep("Persistent hit panels are displayed");
			softAssert.assertEquals(docView.getHitPanel().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		String beforeComplete = docView.getHitPanelCount().getText();
		docView.popOutCodingFormAndCompleteDocument();
		String afterComplete = docView.getHitPanelCount().getText();

		softAssert.assertNotEquals(beforeComplete, afterComplete);
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51944 Verify when user enters document number to view when
	 * 'Loading..' displays in mini doc list, the entry for the same document in
	 * mini-DocList must always present fully in the visible area of the
	 * mini-DocList
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 35)
	public void verifyDocInMiniDocsAfterScrollingDownTillLoadingTextDisplayedWhenDocIsFilteredUsingDocId()
			throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51944");
		baseClass.stepInfo(
				"Verify when user enters document number to view when 'Loading..' displays in mini doc list child window, the entry for the same document must always present fully in the visible area of the mini-DocList child window");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51941 Verify when user is selecting a document to view after
	 * scrolling down the mini doc list when 'Loading..' displays in DocView, the
	 * entry for the same document in mini-DocList must always present fully in the
	 * visible area of the mini-DocList
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 36)
	public void verifyDocInMiniDocListAfterScrollingDownTillLoadingTextDisplayed() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51941");
		baseClass.stepInfo(
				"Verify that when user is selecting a document to view after scrolling down/up the mini doc list and in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user)");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(true);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(true);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");
		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(true);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51939 Verify that when user is selecting a document to view after
	 * scrolling down/up the mini doc list and in DocView, the entry for the same
	 * document in mini-DocList must always present fully in the visible area of the
	 * mini-DocList (to the user)
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 37)
	public void verifyDocInMiniDocListAfterScrollingDown() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51939");
		baseClass.stepInfo(
				"Verify that when user is selecting a document to view after scrolling down/up the mini doc list and in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user)");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(false);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.waitForPageToBeReady();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(false);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");
		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(false);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}
		docView.getSelectedDocIdInMiniDocList().ScrollTo();
		baseClass.waitTillElemetToBeClickable(docView.getSelectedDocIdInMiniDocList());
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51936 Verify that when performing doc-to-doc navigation after
	 * navigating to document on entering the document number the same document in
	 * mini-DocList must always present fully in the visible area of the
	 * mini-DocList
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 38)
	public void verifyDocInMiniDocListAfterPerformDocNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51936");
		baseClass.stepInfo(
				"Verify that when performing doc-to-doc navigation after navigating to document on entering the document number the same document in mini-DocList must always present fully in the visible area of the mini-DocList");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			System.err.println(docViewId);
			System.err.println(miniDocListId);
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitTillElemetToBeClickable(docView.getDocumetId());
		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51937 Verify that when performing doc-to-doc navigation after
	 * navigating to document on entering the document number the same document in
	 * mini-DocList child window must present fully in the visible area of the
	 * mini-DocList child window
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 39)
	public void verifyDocInMiniDocListChildWindowsAfterPerformNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51937");
		baseClass.stepInfo(
				"Verify that when performing doc-to-doc navigation after navigating to document on entering the document number the same document in mini-DocList child window must present fully in the visible area of the mini-DocList child window");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.switchTo().window(parentWindowID);

		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.switchTo().window(parentWindowID);

		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.switchTo().window(parentWindowID);

		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51934 Verify that when completing the documents same as last the
	 * entry for the navigated document in mini-DocList child window must always
	 * present fully in the visible area of mini doc list child window
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 40)
	public void verifyDocInMiniDocListChildWindowsAfterPerformCodeSameAsLast() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51934");
		baseClass.stepInfo(
				"Verify that when completing the documents same as last the entry for the navigated document in mini-DocList child window must always present fully in the visible area of mini doc list child window");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.waitForElement(docView.getSaveAndNextButton());
		docView.getSaveAndNextButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		docView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.waitForElement(docView.getSaveAndNextButton());
		docView.getSaveAndNextButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		docView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51405 Verify all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from Advanced
	 * Search > doc list to doc view
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 41)
	public void verifyHighlightedKeywordsForDocsAreDisplayedSearchWithAdvancedSearch() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case id : RPMXCON-51405");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from Advanced Search > doc list to doc view");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectAssignmentInWPS(assignmentName);
		sessionSearch.serarchWP();
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.switchToWorkproduct();
		sessionSearch.getSavedSearchBtn1().Click();
		sessionSearch.selectSavedsearchesInTree("My Saved Search");
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(5);
		sessionSearch.serarchWP();
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.switchToWorkproduct();
		sessionSearch.getSavedSearchBtn1().Click();
		sessionSearch.selectSavedsearchesInTree("Shared With Project Administrator");
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(5);
		sessionSearch.serarchWP();
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51404 Verify all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from Saved
	 * Search > doc list
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 42)
	public void verifyHighlightedKeywordsForDocsAreDisplayedSavedSearch() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		baseClass.stepInfo("Test case id : RPMXCON-51404");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from Saved Search > doc list");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
	}




	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51332 Verify text from review mode in context of an assignment
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 46)
	public void verifyReviewModeTextContextOfAssignment() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case id : RPMXCON-51332");
		baseClass.stepInfo("Verify text from review mode in context of an assignment");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.assignmentDistributingToReviewer();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docView.verifyReviewModeText();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		driver.waitForPageToBeReady();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		docView.verifyReviewModeText();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		driver.waitForPageToBeReady();
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		docView.verifyReviewModeText();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentspage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : STEFFY date: 13/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify search term, assigned keywords should be highlighted and
	 * should be displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assigned after searching with term.
	 * 'RPMXCON-51396' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifySearchTermHighlightedInEyeIconFromAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51396");
		baseClass.stepInfo(
				"Verify search term, assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assigned after searching with term");

		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		String hitTerms = "Test" + Utility.dynamicNameAppender();
		String codingForm = Input.codeFormName;

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentsPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51773 Verify that previously saved Persistent hits should be
	 * displayed on the doc view when same/different reviewer is unassigned from
	 * assignment and documents are distributed again
	 */

	@Test(enabled = true, alwaysRun = false, groups = { "regression" }, priority = 51)
	public void verifySavedPersistedHitsDisplayedDocDistributedAgainInDocView() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51773");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits should be displayed on the doc view when same/different reviewer is unassigned from assignment and documents are distributed again");
		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		SavedSearch savedSearch = new SavedSearch(driver);
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		try {
			if (assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue()) {
				baseClass.passedStep("User is in doc view in context of an assignment");
				softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("User is not in doc view in context of an assignment");
		}

		baseClass.stepInfo("Verify whether the panels are displayed in doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}
		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to complete docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");
		assignmentsPage.UnassignedUser(Input.rev1userName);
		assignmentsPage.addReviewerAndDistributeDocs();
		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after completing docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				System.out.println("");
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51330 Verify after impersonation all hits of the document should
	 * be highlighted without clicking the eye icon when user redirects to doc view
	 * from doc list
	 */

	@Test(enabled = true, alwaysRun = false, groups = { "regression" }, priority = 52)
	public void verifyImpersonationHitsOfDocWithoutClickingEyeIconToDocViewFromDocList() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		baseClass.stepInfo("Test case id : RPMXCON-51330");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from doc list");

		// Login as a RMU
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("RMU has been impersonated as REV");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("SavedSearch the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("SA has been impersonated as PA");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("SavedSearch the non audio documents and go to docview");
		driver.waitForPageToBeReady();
		sessionSearch.switchToWorkproduct();
		sessionSearch.getSavedSearchBtn1().Click();
		sessionSearch.selectSavedsearchesInTree("Shared With Project Administrator");
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(5);
		sessionSearch.serarchWP();
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("SA has been impersonated as RMU");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("SA has been impersonated as REV");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("PA has been impersonated as RMU");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("PA has been impersonated as REV");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).documentSelection(1);
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("SavedSearch on non audio documents and go to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
	}

	/**
	 * Author : Sai Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51923 Verify that when user in on Images tab and view document from
	 * analytics panel child window then should be on Images tab of the viewed
	 * document
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 53)
	public void verifyImageTabViewDocAnalyticalPanelChildWindow() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		// Login As RMU
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51923");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and view document from analytics panel child window then should be on Images tab of the viewed document");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		// Method for viewing doc view images.
		docViewRedact.verifyViewDocAnalyticalPanelIsNotPartInMiniDocList();
		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {

		}
		driver.waitForPageToBeReady();
		docViewRedact.verifyViewDocAnalyticalPanelPartInMiniDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in Reviewer account");
		SessionSearch sessionsearch1 = new SessionSearch(driver);
		sessionsearch1.basicContentSearch(Input.searchString1);
		sessionsearch1.ViewInDocView();

		// Method for viewing doc view images.
		docViewRedact.verifyViewDocAnalyticalPanelIsNotPartInMiniDocList();
		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {

		}
		driver.waitForPageToBeReady();
		docViewRedact.verifyViewDocAnalyticalPanelPartInMiniDocList();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in Pa account");
		SessionSearch sessionsearch11 = new SessionSearch(driver);
		sessionsearch11.basicContentSearch(Input.searchString1);
		sessionsearch11.ViewInDocView();

		// Method for viewing doc view images.
		docViewRedact.verifyViewDocAnalyticalPanelIsNotPartInMiniDocList();
		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {

		}
		driver.waitForPageToBeReady();
		docViewRedact.verifyViewDocAnalyticalPanelPartInMiniDocList();
		loginPage.logout();
	}

	/**
	 * Author : Sai Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51044 Verify after impersonation user can maximize the middle panel
	 * of the doc view
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 54)
	public void verifyAfterImpersonationMiddlePanelInDocView() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		// Login As RMU
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51044");
		baseClass.stepInfo("Verify after impersonation user can maximize the middle panel of the doc view");

		baseClass.impersonateRMUtoReviewer();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("SA has been impersonated as PA");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		SessionSearch sessionsearch1 = new SessionSearch(driver);
		sessionsearch1.basicContentSearch(Input.searchString1);
		sessionsearch1.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("SA has been impersonated as RMU");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		SessionSearch sessionsearch2 = new SessionSearch(driver);
		sessionsearch2.basicContentSearch(Input.searchString1);
		sessionsearch2.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("SA has been impersonated as RMU");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("PA has been impersonated as RMU");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("PA has been impersonated as Rev");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when page highlighting added for different
	 *         file types (e.g. XLS, XLSX, CSV, etc...) then highlighting should not
	 *         be shifted when visiting these documents : RPMXCON-52309 Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 55)
	public void verifyHighlightedThisPage() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList;
		String firnstDocname, secondDocname, docName;

		baseClass.stepInfo("Test case Id: RPMXCON-52309 Sprint 10");
		baseClass.stepInfo(
				"Verify that when page highlighting added for different file types (e.g. XLS, XLSX, CSV, etc...) then highlighting should not be shifted when visiting these documents");

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// check And DO ThisPage Redact
		docViewRedact.checkANdDOThisPageHighlight();

		// Switch to a different document
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Iniital Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
//		baseClass.waitForElement(miniDocListpage.getDociD(firnstDocname));
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// verify ThisPage Redacted Maintained
		docViewRedact.verifyThisPageHighlightMaintained(true);
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when rectangle redaction added for
	 *         different file types (e.g. XLS, XLSX, CSV, etc...) then redaction
	 *         should not be shifted when visiting these documents : RPMXCON-52310
	 *         Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void verifyRectangleHighlightingPosition() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList, x = 20, y = 10;
		int xOffset = 20, yOffset = 10;
		String firnstDocname, secondDocname, docName, xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		baseClass.stepInfo("Test case Id: RPMXCON-52310 Sprint 10");
		baseClass.stepInfo(
				"Verify that when highlighting added for different file types (e.g. XLS, XLSX, CSV, etc...) on different pages then highlighting should not be shifted when visiting these documents");

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Check for this page highlight and remove
		docViewRedact.removeThisPageHighlight();

		// do Rectangle Redact With XY Points
		xyMap = docViewRedact.doRectangleHighlightWithXYPoints(x, y, xOffset, yOffset);
		xAxis = xyMap.get("xAxis");
		yAxis = xyMap.get("yAxis");

		// Switch to a different document
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
//		baseClass.waitForElement(miniDocListpage.getDociD(firnstDocname));
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Verify Position Retained
		docViewRedact.verifyRectangleHighlightPositionRetained(xAxis, yAxis, true);
		loginPage.logout();

	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when rectangle redaction added for
	 *         different file types (e.g. XLS, XLSX, CSV, etc...) then redaction
	 *         should not be shifted when visiting these documents : RPMXCON-52301
	 *         Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void verifyRectangleRedactionPosition() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList, x = 20, y = 10;
		int xOffset = 20, yOffset = 10;
		String firnstDocname, secondDocname, docName, xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		baseClass.stepInfo("Test case Id: RPMXCON-52301 Sprint 10");
		baseClass.stepInfo(
				"Verify that when rectangle redaction added for different file types (e.g. XLS, XLSX, CSV, etc...) then redaction should not be shifted when visiting these documents");

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Check for this page highlighted and remove
		docViewRedact.removeThisPageHighlight();

		// do Rectangle Redact With XY Points
		xyMap = docViewRedact.doRectangleRedactWithXYPoints(x, y, xOffset, yOffset);
		xAxis = xyMap.get("xAxis");
		yAxis = xyMap.get("yAxis");

		// Switch to a different document
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
//		baseClass.waitForElement(miniDocListpage.getDociD(firnstDocname));
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Verify Position Retained
		docViewRedact.verifyRectangleRedactionPositionRetained(xAxis, yAxis, true);
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when text redaction added for different
	 *         file types (e.g. XLS, XLSX, CSV, etc...) then redaction should not be
	 *         shifted when visiting these documents : RPMXCON-52302 Sprint : 11
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void verifyTextRedactionPosition() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList;
		String firnstDocname, secondDocname, docName, xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		baseClass.stepInfo("Test case Id: RPMXCON-52302 Sprint 11");
		baseClass.stepInfo(
				"Verify that when text redaction added for different file types (e.g. XLS, XLSX, CSV, etc...) then redaction should not be shifted when visiting these documents");

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Check for this page highlighted and remove
		docViewRedact.removeThisPageHighlight();

		// do Rectangle Redact With XY Points
		xyMap = docViewRedact.doTextRedactWithXYPoints();
		xAxis = xyMap.get("xAxis");
		yAxis = xyMap.get("yAxis");

		// Switch to a different document
		driver.waitForPageToBeReady();
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
//		baseClass.waitForElement(miniDocListpage.getDociD(firnstDocname));
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Verify Position Retained
		docViewRedact.verifyTextRedactionPositionRetained(xAxis, yAxis, true);
		loginPage.logout();

	}

	/**
	 * Author : Sakthivel date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51043 Verify user can maximize the middle panel of the doc view
	 * redirecting from Basic Search/Saved Search/Doc List/My Assignment/Manage
	 * Assignment/Manage Reviewers.
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 59)
	public void verifyMaximizeTheMiddlePanelInDocView() throws Exception {
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);

		// Login As RMU
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51043");
		baseClass.stepInfo("Doc view redirecting from BasicSearch and go to docview");

		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		// Login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		docViewRedact.verifyMaximizetheMiddlePanel();
		loginPage.logout();
	}

	/**
	 * @Author Raghuram A date:01/02/2022 Modified date: NA Modified by:N/A
	 * @Description : To verify that once user complete the document, count should
	 *              increased on the Edit Assignment->Manage Reviewers..
	 *              [RPMXCON-50987]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 60)
	public void VerifyCompleteDocCountViaRevTab() throws Exception {

		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		String assignmentNametoCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		List<String> docIDlist = new ArrayList<>();
		String userName = Input.rmu1userName;
		int iteration = 3;

		baseClass.stepInfo("Test case Id:RPMXCON-50987 Sprint 11");
		baseClass.stepInfo(
				"To verify that once user complete the document, count should increased on the Edit Assignment->Manage Reviewers.");

		// Assignment Creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentNametoCreate, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewerManager();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// distributedCOunt pick
		String distributedCOunt = assignmentPage.getDistibuteDocsCount(userName);
		baseClass.stepInfo("Distributed count to the user : " + distributedCOunt);

		// Assignment selection from Dashboard
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getDashBoardLink());
		miniDocListpage.getDashBoardLink().waitAndClick(5);
		miniDocListpage.chooseAnAssignmentFromDashBoard(assignmentNametoCreate);

		// Complete document
		baseClass.stepInfo("Complete doc iteration count : " + iteration);
		docIDlist = miniDocListpage.getDocListDatas();
		miniDocListpage.verifyCompleteCheckMarkIconandDocHighlight(docIDlist, iteration, false);

		// Assignment Edit View
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentNametoCreate);
		driver.waitForPageToBeReady();
		baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
		baseClass.waitForElement(assignmentPage.getAssignment_ManageReviewersTab());
		assignmentPage.getAssignment_ManageReviewersTab().waitAndClick(5);
		driver.waitForPageToBeReady();

		// Data Verification
		assignmentPage.verifyCountMatches(userName, iteration, distributedCOunt, true, true, true, false);

		// Delete Assignment
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignmentPage.deleteAssgnmntUsingPagination(assignmentNametoCreate);

		loginPage.logout();

	}

	/**
	 * @Author Raghuram A date:2/8/2022 Modified date: NA Modified by:N/A
	 * @Description : To verify document view panel from doc view page
	 *              [RPMXCON-50777]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 61)
	public void verifyDocViewPanel() throws InterruptedException {
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		String cascadeSettings_yes = "Yes";
		List<String> docIDlist = new ArrayList<>();
		String sortBy = "DocID";

		loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50777 Assignments Sprint-12");
		baseClass.stepInfo("To verify document view panel from doc view page");

		// Assignment group creation
		agnmt.navigateToAssignmentsPage();
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(cascadeAsgnGrpName, cascadeSettings_yes);
		agnmt.Assgnwithdocumentsequence(sortBy, Input.sortType);

		// Assignment creation under group via search -> bulk assign
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignWithNewAssgn(cascadeAsgnGrpName);
		agnmt.quickAssignmentCreation(assignment, Input.codeFormName);
		agnmt.saveAssignment(assignment, Input.codeFormName);
		baseClass.stepInfo("Created Assignment name : " + assignment);

		// Distribute to RMU
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.getSelectAssignment(assignment).waitAndClick(5);
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.assignmentDistributingToReviewer();

		// Selecting the assignment from group
		agnmt.navigateToAssignmentsPage();
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.selectAssignmentToViewinDocView(assignment);

		// Verify default doc details
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());
		Collections.sort(docIDlist);
		String defaultDocName = docIDlist.get(0);
		baseClass.stepInfo("First document from the list : " + defaultDocName);

		// Result
		baseClass.printResutInReport(miniDocListpage.verifySelectedDocHighlight(defaultDocName),
				"Default Document is selected : " + defaultDocName,
				"Failed to select default document" + defaultDocName, "Pass");

		String docName = miniDocListpage.getMainWindowActiveDocID().getText();
		baseClass.stepInfo("Current Document Viewed : " + docName);
		baseClass.textCompareEquals(docName, defaultDocName,
				"Document View panel contains detail/content view of selected document - In default first document of mini doc list is selected",
				"Failed in Default document view");

		// Delete Assign group and assign
		agnmt.deleteAssignmentFromSingleAssgnGrp(cascadeAsgnGrpName, assignment);
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName);
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Case
	 * has a larger flow - using 2 different users and different projects
	 * Id:RPMXCON-50782 To verify assignment progress bar should be displayed on doc
	 * view page as per the selected assignment
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 62)
	public void verifyAssignmentProgressBarDisplayedOnDocView() throws Exception {
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String assnameonproject1 = "assgnment" + Utility.dynamicNameAppender();
		String assnameonproject2 = "assgnment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case id : RPMXCON-50782");
		baseClass.stepInfo(
				"To verify assignment progress bar should be displayed on doc view page as per the selected assigment");

		// verify assignment ProgressBar on creating assignment
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add2ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assName = docView.getDocView_AssigmentName().getText();
		System.out.println(assName);
		baseClass.passedStep(assName
				+ "Assignment progress bar is displayed on doc view page the selected assignment completion status");
		baseClass.stepInfo("Assignment1 name is also displayed above the assignment progress bar successfully");

		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assname, Input.codeFormName);
		assignmentspage.add2ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assname);
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assName1 = docView.getDocView_AssigmentName().getText();
		System.out.println(assName1);
		baseClass.passedStep(assName1
				+ "Assignment progress bar is displayed on doc view page the selected assignment completion status.");
		baseClass.stepInfo(
				assName1 + "Assignment2 name is also displayed above the assignment progress bar successfully");

		// verify Assignment ProgressBar on different project
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assnameonproject1, Input.codeFormName);
		assignmentspage.add2ReviewerAndDistribute();
		baseClass.waitTime(10);
		baseClass.selectproject("AutomationAdditionalDataProject");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assnameonproject2, Input.codeFormName);
		assignmentspage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentspage.selectAssignmentToViewinDocView(assnameonproject2);
		baseClass.stepInfo("Rmu user assigned to different project on creating assignment");
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assName2 = docView.getDocView_AssigmentName().getText();
		System.out.println(assName2);
		baseClass.passedStep(assName2
				+ "Assignment progress bar is displayed on doc view page the selected assignment completion status.");
		baseClass.stepInfo(
				assName2 + "Assignment name is also displayed above the assignment progress bar successfully");

		driver.waitForPageToBeReady();
		assignmentspage.selectAssignmentToViewinDocview(assnameonproject1);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Rmu user assigned to different project on creating assignment");
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assNameRmuProject1 = docView.getDocView_AssigmentName().getText();
		System.out.println(assNameRmuProject1);
		baseClass.passedStep(assNameRmuProject1
				+ "Assignment progress bar is displayed on doc view page the selected assignment completion status.");
		baseClass.stepInfo(assNameRmuProject1
				+ "Assignment name is also displayed above the assignment progress bar successfully");
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment as reviewer
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assNameRev1 = docView.getDocView_AssigmentName().getText();
		System.out.println(assNameRev1);
		baseClass.passedStep(assNameRev1
				+ "Assignment1 progress bar is displayed on doc view page elected assignment completion status");
		baseClass.stepInfo(
				assNameRev1 + "Assignment name is also displayed above the assignment progress bar successfully");

		assignmentspage.SelectAssignmentByReviewer(assname);
		baseClass.passedStep("User on the doc view after selecting the assignment");
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assNameRev2 = docView.getDocView_AssigmentName().getText();
		System.out.println(assNameRev2);
		baseClass.passedStep(assNameRev2
				+ "Assignment2 progress bar is displayed on doc view page selected assignment completion status..");
		baseClass.stepInfo(
				assNameRev2 + "Assignment name is also displayed above the assignment progress bar successfully");

		// verify creating assignment progressBar on different project
		driver.waitForPageToBeReady();
		assignmentspage.SelectAssignmentByReviewer(assnameonproject1);
		baseClass.stepInfo("Rev user assigned to different project on creating assignment");
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assNameRevProject1 = docView.getDocView_AssigmentName().getText();
		System.out.println(assNameRevProject1);
		baseClass.passedStep(assNameRevProject1
				+ "Assignment progress bar is displayed on doc view page the selected assignment completion status..");
		baseClass.stepInfo(assNameRevProject1
				+ "Assignment name is also displayed above the assignment progress bar successfully");

		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.selectproject("AutomationAdditionalDataProject");
		baseClass.stepInfo("Rev user assigned to different project on creating assignment");
		assignmentspage.SelectAssignmentByReviewer(assnameonproject2);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		softAssertion.assertTrue(docView.getDocView_AssignmentProgressBar().isDisplayed());
		softAssertion.assertTrue(docView.getDocView_AssigmentName().isDisplayed());
		softAssertion.assertAll();
		String assNameRevProject2 = docView.getDocView_AssigmentName().getText();
		System.out.println(assNameRevProject2);
		baseClass.passedStep(assNameRevProject2
				+ "Assignment progress bar is displayed on doc view page  selected assignment completion status..");
		baseClass.stepInfo(assNameRevProject2
				+ "Assignment name is also displayed above the assignment progress bar successfully");
	}

	/**
	 * @author krishna TestCase Id:51874 Verify that Action > Remove Code Same As
	 *         works fine when all records in the reviewers batch are in an
	 *         Uncompleted state, and the user selects only some/select records
	 * @throws InterruptedException
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 63)
	public void verifyCodeSameAsMiniDocListAndChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51874");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"Verify that Action > Remove Code Same As works fine when all records in the reviewers batch are in an Uncompleted state, and the user selects only some/select records");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// searching document for assignmnet creation
		baseClass.stepInfo("bascic contant search");
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("performing bulk assign");
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Create assignment WIth allow user to save with out complete option");

		// create new assignment with allow user to save without complete
		assignmentPage.createAssignmentWithAllowUserToSave(AssignStamp, Input.codingFormName);
		baseClass.stepInfo("editing assignment");
		assignmentPage.editAssignment(AssignStamp);
		baseClass.stepInfo("Reviewers added and distributed to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();
		loginPage.logout();
		baseClass.stepInfo("Successfully logout RUM '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		// verify CodeSameAs icon is displayed in selected doc
		baseClass.stepInfo("performing Code sameas for min doc list documents");
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSame();
		driver.waitForPageToBeReady();
		softAssert.assertFalse(docView.geDocView_MiniList_CodeSameAsIcon().isDisplayed());
		if (docView.geDocView_MiniList_CodeSameAsIcon().isElementAvailable(1)) {
			baseClass.failedStep("CodeSameAs icon is displayed for the selected docs ");
		} else {
			baseClass.passedStep("CodeSameAs icon is not displayed for the selected docs");
		}

		baseClass.stepInfo("save the configuration");
		docView.saveConfigFromChildWindow();
		docView.popOutMiniDocList();

		// verify CodeSameAs icon is displayed in selected doc in childWindow
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 2; i++) {
			baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(i));
			docView.getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
		}
		docView.clickCodeSameAs();
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSameInChildWindow();
		docView.closeWindow(1);
		softAssert.assertAll();
	}

	/**
	 * @author Krishna TestCase Id:51881 C3B: Verify that Action > Folder works fine
	 *         when all records in the reviewers batch are in mixed state but
	 *         records that are in Completed state are also present along with
	 *         records in an Uncompleted state in Mini DocList
	 * @throws InterruptedException
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 64)
	public void verifyFolderWorksFineInCompleteAndUnCompleteSateInMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51881");
		baseClass.stepInfo(
				"C3B: Verify that Action > Folder works fine when all records in the reviewers batch are in mixed state but records that are in Completed state are also present along with records in an Uncompleted state in Mini DocList");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);

		// searching document for assignmnet creation
		baseClass.stepInfo("bascic contant search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Create assignment With allow user to save with complete option");
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		miniDocList.configureMiniDocListToShowCompletedDocs();

		// To perform Folder MiniDocList
		docView.performFloderMiniDocListForReviewer();
	}

	/**
	 * @author Krishna TestCase Id:51880 C2B: Verify that Action > Remove Code Same
	 *         works fine when all records in the reviewer's batch are in mixed
	 *         state but records that are in Completed state are also present along
	 *         with records in an Uncompleted state in Mini DocList
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 65)
	public void verifyRemoveCodeSameInCompleteandUncompleteStateInMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51880");
		baseClass.stepInfo(
				"C2B: Verify that Action > Remove Code Same works fine when all records in the reviewer's batch are in mixed state but records that are in Completed state are also present along with records in an Uncompleted state in Mini DocList");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// searching document for assignmnet creation
		baseClass.stepInfo("bascic contant search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Create assignment With allow user to save with complete option");
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		miniDocList.configureMiniDocListToShowCompletedDocs();
		baseClass.stepInfo("Mini doc list is configured to show completed documents");

		// verify to select docs and CodeSameAs
		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 2; i++) {

			docView.getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(10);
		}
		miniDocList.configureMiniDocListToShowCompletedDocs();
		baseClass.stepInfo("Mini doc list is not be configured to show completed documents");
		baseClass.handleAlert();

		// verify to select docs and remove code as same
		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSame();
		driver.waitForPageToBeReady();
		softAssert.assertFalse(docView.geDocView_MiniList_CodeSameAsIcon().isDisplayed());
		baseClass.passedStep("chain link icon removed for the documents after Remove code same");

	}

	/**
	 * @author Krishna TestCase Id:51006 Verify user can see the thumbnail image of
	 *         each page of the document being viewed on doc view page in thumbnail
	 *         panel when redirecting from other than assignment page
	 * @throws InterruptedException
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 66)
	public void verifyEachPageDocViewedOnDocViewInThumbNailPanel() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51006");
		baseClass.stepInfo(
				"Verify user can see the thumbnail image of each page of the document being viewed on doc view page in thumbnail panel when redirecting from other than assignment page");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Logged in using RMU account");
		docViewRedact.verifyDifferentTypesOfDocsInThumbNailsPanel(Input.pdfDocId, Input.xlsExcelDocId, Input.tiffDocId1,
				Input.pptDocId, Input.messageDocId);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using REV account");
		docViewRedact.verifyDifferentTypesOfDocsInThumbNailsPanel(Input.pdfDocId, Input.xlsExcelDocId, Input.tiffDocId1,
				Input.pptDocId, Input.messageDocId);
		loginPage.logout();

		// Login As PA.
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		docViewRedact.verifyDifferentTypesOfDocsInThumbNailsPanel(Input.pdfDocId, Input.xlsExcelDocId, Input.tiffDocId1,
				Input.pptDocId, Input.messageDocId);
	}

	/**
	 * Author : Mohan date: 03/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51028 To verify that after impersonation user can see remarks for
	 * selected document
	 * 
	 * @Stabilization - done
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 67)
	public void verifyRemarksForSelectedDocsAfterImpersonating() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51008");
		baseClass.stepInfo("To verify that after impersonation user can see remarks for selected document");

		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click to see Reviewer Remarks");
		docViewRedact.clickingRemarksIcon();
		docViewRedact.verifyReviewerRemarksIsPresent();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Select document and click to see Reviewer Remarks");
		docViewRedact.clickingRemarksIcon();
		docViewRedact.verifyReviewerRemarksIsPresent();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer,select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Select document and click to see Reviewer Remarks");
		docViewRedact.clickingRemarksIcon();
		docViewRedact.verifyReviewerRemarksIsPresent();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer,select assignment and go to Docview");
		baseClass.impersonateRMUtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Select document and click to see Reviewer Remarks");
		docViewRedact.clickingRemarksIcon();
		docViewRedact.verifyReviewerRemarksIsPresent();
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: NA Modified date: NA Modified by: Krishna D Test Case
	 * Id: RPMXCON-52170 Description : Verify that tool tip displayed on mouse over
	 * of 'Code same as last' when document having all page redaction in context of
	 * assignment
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52170", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 11)
	public void verifyToolTipMouseOverCodeSameAsLast() throws Exception {
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		int pureHitCount = 201;
		// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52170");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search

		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");

		// Add reviewer and distribute docs and Select Assign and View in DocView
		assignmentPage.addReviewerAndDistributeDocs(assignmentName, pureHitCount);
		assignmentPage.editAssignment(assignmentName);
		docViewRedact.toggleCompletedRedactionTagEnabled();
		assignmentPage.SelectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Reviewer are added and doc distributed and Viewed in Docview successfully");

		// select docs from MiniDoclist
		docViewRedact.selectMiniDocListAndViewInDocView();
		baseClass.stepInfo("Doc is Selected from MiniDocList successfully");

		// Mini doc list having all page redaction
		docViewRedact.selectRedactionIconAndRedactWholePage();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// MouseHover to CodeAsLastDoc Icon
		docViewRedact.mouseOverToCodeSameAsLastIcon();

		// pop-out child window
		docViewRedact.popOutCodingFormChildWindow();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// MouseHover to CodeAsLastDoc Icon
		docViewRedact.mouseOverToCodeSameAsLastIcon();
		baseClass.passedStep(
				"'Code this document the same as the last coded document' is displayed when mouserhover to Code Same As Last doc successfully");
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify that > and < arrows should work when the hit in the
	 * document is due to Keyword Group Highlights when redirected to doc view from
	 * basic search'RPMXCON-51441' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 22)
	public void verifyArrowsRedirdctedToDocViewFromBasicSearch() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51441");
		baseClass.stepInfo(
				"Verify that > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		docViewRedact.performTheEyeIconHighLighting();

		baseClass.stepInfo(
				"That > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search is Successfully");
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		docViewRedact.performTheEyeIconHighLighting();

		baseClass.stepInfo(
				"That > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search is Successfully");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify the Images tab retain on document navigation when 'Show Default Tab'
	 * toggle is OFF at assignment level.'RPMXCON-51925' Sprint : 9
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyImageTabRetainOnDocsNavigation() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51925");
		baseClass.stepInfo(
				"Verify the Images tab retain on document navigation when 'Show Default Tab' toggle is OFF at assignment level");
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Step 2: Assignment should be created with  'Show Default Tab' toggle as OFF");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();

		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleDisableShowDefaultViewTab();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 3: Go to doc view in context of an assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo("Step 4: Go to Images tab");
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		baseClass.stepInfo("Step 5: Click on the document navigation options");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.nearDupeDocId1);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(3);

		baseClass.waitForElement(docViewPage.getDocView_ImageTab_LastPageButton());
		docViewPage.getDocView_ImageTab_LastPageButton().waitAndClick(5);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep(
					"Image/production document for the navigated document is loaded on the Images tab and Images/production tab is retained as per the navigation");

		} else {
			baseClass.failedStep("Image tab is not retained");
		}

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 4: Go to Images tab");
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		baseClass.stepInfo("Step 5: Click on the document navigation options");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.nearDupeImageDoc);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(3);

		baseClass.waitForElement(docViewPage.getDocView_ImageTab_LastPageButton());
		docViewPage.getDocView_ImageTab_LastPageButton().waitAndClick(5);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep(
					"Image/production document for the navigated document is loaded on the Images tab and Images/production tab is retained as per the navigation");

		} else {
			baseClass.failedStep("Image tab is not retained");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 7/12/21 NA Modified date: NA Modified by:NA
	 * Description :Needs to verify with out adding remarks should not
	 * save'RPMXCON-51625' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 24)
	public void verifyWithOutAddingRemarkShouldNotSave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51625");
		baseClass.stepInfo("Needs to verify with out adding remarks should not save");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search with text input completed");
		sessionsearch.ViewInDocView();

		docViewRedact.performTheRemarkIconNotSave();
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(4000);
		// Thread sleep added for the page to adjust resolution
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(200, 100).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());

		actions.click();
		driver.waitForPageToBeReady();
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);
		baseClass.waitForElement(docViewRedact.getRemarkXBtn());

		docViewRedact.getRemarkXBtn().waitAndClick(30);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(30);
		driver.waitForPageToBeReady();
		try {
			String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex2 = Color.fromString(color2).asHex();
			System.out.println(hex2);
			if (hex1.equalsIgnoreCase(hex2)) {
				baseClass.failedStep("The color for the Highlighted text is same");
			}
		} catch (Exception e) {
			baseClass.passedStep("The Highlighted text is not Same");
		}
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify after impersonation all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from basic
	 * search.'RPMXCON-51326' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyAfterImpersonationHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51326");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from basic search");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when Persistent Hit panel, Reviewer Remarks panel,
	 * Redactions menu, Highlights menu is selected and views document from
	 * analytics panel previously selected panels/menus should remain..
	 * 'RPMXCON-51355' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsAanalytics() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51355");
		baseClass.stepInfo(
				"Verify when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and views document from analytics panel previously selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();

		// perform AnalyticalPanel After earlier documents section
		docView.performDisplayIconReviewerHighlightAnalyticalPanel();

		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();

		// perform AnalyticalPanel After earlier documents section
		docView.performDisplayIconReviewerHighlightAnalyticalPanel();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation when Persistent Hit panel, Reviewer
	 * Remarks panel, Redactios menu, Highlights menu is selected and navigates to
	 * other doc from mini doc list child window previously selected selected
	 * panels/menus should remain. 'RPMXCON-51354' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51354");
		baseClass.stepInfo(
				"Verify after impersonation when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and navigates to other doc from mini doc list child window previously selected selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer, search docs and Search for docs");
		baseClass.impersonateRMUtoReviewer();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonate SA to Reviewer, search docs and Search for docs");
		baseClass.impersonateSAtoReviewer();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer, select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 26/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify Persistent KW Groups as well as Saved Searching on doc
	 * view.'RPMXCON-51553'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifyKWGroupAndSavedSeacrhOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51553");
		baseClass.stepInfo("Verify Persistent KW Groups as well as Saved Searching on doc view");
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		String panelText = "basis)";
		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(panelText).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Hit count for the matching keywords is displayed against the keywords on persistent hit panel");

		loginPage.logout();

		// login As PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(panelText).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Hit count for the matching keywords is displayed against the keywords on persistent hit panel");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(panelText).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Hit count for the matching keywords is displayed against the keywords on persistent hit panel");
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that previously saved Persistent hits should be displayed
	 * on the doc view when documents are uncompleted from edit assignment.
	 * 'RPMXCON-51775' Sprint: 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void verifyPersistentHitDisplayInUncompleteDocEditAssign() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51775");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits should be displayed on the doc view when documents are uncompleted from edit assignment.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);

		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String BasicSearchName = "Savebtn" + Utility.dynamicNameAppender();
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.SaveSearchToBulkAssign(BasicSearchName, assname, codingForm, SessionSearch.pureHit);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		// eye Icon
		docViewRedact.getDocView_MiniDoc_Selectdoc(1).waitAndClick(20);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.passedStep("EyeIcon Clicked Successfully");
		// edit coding form
		docView.editCodingFormComplete();
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		assignmentsPage.VerifyUnCompleteDoc(assname);
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		// eye Icon
		docViewRedact.getDocView_MiniDoc_Selectdoc(1).waitAndClick(20);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		loginPage.logout();
	}

	/**
	 * @Author : Aathith Modified date: NA Modified by: Aathith RPMXCON-50786
	 * @Description : To verify document should be displayed in doc view panel as
	 *              per the entered document number in the inputbox.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 63)
	public void verifyDocumentAsPerEnteredDocument() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50786");
		baseClass.stepInfo(
				"To verify document should be displayed in doc view panel as per the entered document number in the inputbox.");

		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Select the Assigment go to Docview");
		assignmentPage.selectAssignmentToViewinDocview(assname);
		baseClass.stepInfo("Doc view page is selected from assigment page");

		driver.waitForPageToBeReady();
		docViewPage.document_Navigation_verification(15);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();
		docViewPage.document_Navigation_verification(25);

		int lastdoc = docViewPage.verifyingDocCount();

		docViewPage.document_Navigation_verification(lastdoc);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc view page is selected from sessionsearch page");

		driver.waitForPageToBeReady();
		docViewPage.document_Navigation_verification(15);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();
		docViewPage.document_Navigation_verification(25);

		int lastdoc1 = docViewPage.verifyingDocCount();

		docViewPage.document_Navigation_verification(lastdoc1);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();

		baseClass.passedStep(
				"verified document should be displayed in doc view panel as per the entered document number in the inputbox.");
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 26/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation when Highlighting, Persistent Hit
	 * panel, Reviewer Remarks panel, Redactios menu is selected from doc view and
	 * views other document from analytics panel previously selected panels/menus
	 * should remain.'RPMXCON-51356' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 51)
	public void verifyAfterImpersonationAllMenusInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51356");
		baseClass.stepInfo(
				"Verify after impersonation when Highlighting, Persistent Hit panel, Reviewer Remarks panel, Redactios menu is selected from doc view and views other document from analytics panel previously selected panels/menus should remain.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 26/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify waning message is prompted to the user after
	 * impersonation when user navigates away from the page without saving action
	 * from doc view.'RPMXCON-50923' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 52)
	public void verifyAfterImpersonationNavigatePageSavingFromDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50923");
		baseClass.stepInfo(
				"Verify waning message is prompted to the user after impersonation when user navigates away from the page without saving action from doc view.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Search with text input for docs completed");
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add3ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.performCodeSameForFamilyMembersDocuments();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation user can load the produced document
	 * by clicking the drop down selection.'RPMXCON-51271' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 54)
	public void verifyAfterImpersonationProducedDocsByDropDownSelection() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51271");
		baseClass.stepInfo(
				"Verify after impersonation user can load the produced document by clicking the drop down selection.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		UtilityLog.info(Input.prodPath);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionsearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		driver.waitForPageToBeReady();
		if (docView.getDocView_MiniDocList_Docs().Displayed()) {
			baseClass.passedStep("Completed Proceded Document is Displayed Successfully");
		} else {
			baseClass.failedStep("Completed Proceded Document is Not Displayed");

		}
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation user can see the production option in
	 * the drop down selection of Images tab when document generated as part of
	 * production.'RPMXCON-51270' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 55)
	public void verifyAfterImpersonationProducedDocsInImageTabDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51270");
		baseClass.stepInfo(
				"Verify after impersonation user can see the production option in the drop down selection of Images tab when document generated as part of production.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		UtilityLog.info(Input.prodPath);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo(" Impersonating SA to PA");
		baseClass.impersonateSAtoPA();
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		driver.waitForPageToBeReady();
		if (docView.getDocView_MiniDocList_Docs().Displayed()) {
			baseClass.passedStep("Completed Proceded Document is Displayed Successfully");
		} else {
			baseClass.failedStep("Completed Proceded Document is Not Displayed");

		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document. 'RPMXCON-51272' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void verifyAssignmentProgressBarCompleteDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51272");
		baseClass.stepInfo("Verify assignment progress bar refreshesh after completing the document.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);

		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can view the folders which is associated to
	 * the security group only. 'RPMXCON-50820' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void veriyUserCanSeeTheFlodersSecurityGroup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50820");
		baseClass.stepInfo("To verify that user can view the folders which is associated to the security group only.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList();
		driver.waitForPageToBeReady();
		if (docView.getDocView_AnalyticsExitingFolderName().Displayed()) {
			baseClass.passedStep("All existing folder under that security group is displayed");
		} else {
			baseClass.failedStep("All existing folder under that security group is not displayed");
		}
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocListForReviewer();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList();
		driver.waitForPageToBeReady();
		if (docView.getDocView_AnalyticsExitingFolderName().Displayed()) {
			baseClass.passedStep("All existing folder under that security group is displayed");
		} else {
			baseClass.failedStep("All existing folder under that security group is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 1/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document on selecting code same as this action. 'RPMXCON-51274' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void verifyAssignmentProgressBarCodeSameAsAction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51274");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document on selecting code same as this action.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		// perform MiniDocList CodeSame As
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);

		// perform MiniDocList CodeSame As
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 1/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after applying coding
	 * stamp. 'RPMXCON-51276' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 59)
	public void verifyAssignmentProgressBarCompleteTheCodingStamp() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51276");
		baseClass.stepInfo("Verify assignment progress bar refreshesh after applying coding stamp.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 3: Search the documents with search term from basic search and go to doc view");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 02/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document same as last prior documents should be completed same as last on
	 * selecting code same as this action. 'RPMXCON-51279' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 60)
	public void verifyAssignmentProgressBarCompleteDocsCodeSameAs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51279");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document same as last prior documents should be completed same as last on selecting code same as this action.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		docView.completeButton();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		docView.completeButton();
		docView.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		for (int i = 4; i <= 5; i++) {
			baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(i));
			docView.getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
		}
		baseClass.waitForElement(docView.getDocView_Mini_ActionButton());
		docView.getDocView_Mini_ActionButton().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView__ChildWindow_Mini_CodeSameAs());
		docView.getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(10);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(5);
		docView.completeButton();
		driver.waitForPageToBeReady();
		docView.clickCodeSameAsLast();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		docView.completeButton();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		docView.completeButton();
		docView.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		baseClass.stepInfo("Step 5: Search the documents with search term from basic search and go to doc view");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		for (int i = 4; i <= 5; i++) {
			baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(i));
			docView.getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
		}
		baseClass.waitForElement(docView.getDocView_Mini_ActionButton());
		docView.getDocView_Mini_ActionButton().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView__ChildWindow_Mini_CodeSameAs());
		docView.getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(10);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(5);
		docView.completeButton();
		driver.waitForPageToBeReady();
		docView.clickCodeSameAsLast();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		loginPage.quitBrowser();
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");

	}

}
