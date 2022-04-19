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
	KeywordPage keywordPage;

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
		keywordPage = new KeywordPage(driver);
		// Login as a RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51444 Check persistent hits when navigated from assignments to
	 * DocView as RMU and REV
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 3)
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 4)
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

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Test
	 * Case Id:RPMXCON-51763 Non-Audio Documents assigned from Basic search- Verify
	 * that when documents are re-assigned to same/other reviewer in an assignment,
	 * any previously saved persistent search hits in the assignment should be
	 * displayed in the assignment Stabilization done
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 5)
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
	 * in the assignment Stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 6)
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
	 * stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 7)
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
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
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
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51403 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searching with reviewer
	 * remarks/comments
	 * stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 8)
	public void verifyHighlightedKeywordsForDocsSearchWithCommentsRemarks() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51403");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assinged after searching with reviewer remarks/comments");

		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Creating Prerequisite");
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.addRemarkByText("Remark by Rmu");
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
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
		baseClass.stepInfo("Deleting Prerequisite");
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.deleteReamark("Remark by Rmu");
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51402 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searched with work product
	 * STABILIZATION DONE
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 9)
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
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.MiniDocId, null);
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
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 10)
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
		baseClass.stepInfo("Creating Prerequisite");
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.addRemarkByText("Remark by Rmu");
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
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
		baseClass.stepInfo("Deleting Prerequisite");
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.deleteReamark("Remark by Rmu");
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51401 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when assinged documents are searched with metadata
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 11)
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
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.MiniDocId, null);
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
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
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

	 @Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority =
	 12)
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
	 * from edit assignment stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 13)
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
	 * Stabilization done
	 */

	 @Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority =
	 14)
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
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.MiniDocId, null);
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
	 * coding form child window stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 15)
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
	 * mini-DocList stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 16)
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 17)
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 18)
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
	 * Author :Vijaya.Rani date: 18/02/2022 Modified date: NA Modified by: NA
	 * Description :Verify to ensure that multiple terms submitted, one with a space
	 * and other without are also handled. 'RPMXCON-51613' Sprint-12
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyMultipleTermsSubmittedAndHandled() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51613  sprint 12");
		SessionSearch search = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo(
				"Verify to ensure that multiple terms submitted, one with a space and other without are also handled");

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// First audio Search
		search.audioSearch(Input.audioString1, Input.language);
		String firstStringCount = search.getPureHitsCount().getText();
		System.out.println(firstStringCount);
		baseClass.stepInfo("First String persistent hit count is : " + firstStringCount);
		loginPage.logout();

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// second audio search
		search.audioSearch(Input.audioString1, Input.language);
		String secondStringCount = search.getPureHitsCount().getText();
		System.out.println(secondStringCount);
		baseClass.stepInfo("Second String persistent hit count is : " + secondStringCount);

		softAssertion.assertEquals(firstStringCount, secondStringCount);
		softAssertion.assertAll();
		baseClass.passedStep("persistent hit count is matched successfully");
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51398 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with work product
	 * Stabilization done
	 */
	 @Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority =
	 20)
	public void validatePersistentPanelHitCountAgainstDocHighlightedCount() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case id : RPMXCON-51398");
		baseClass.stepInfo(
				"Verify highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search when documents searched with work product");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.switchToWorkproduct();
		sessionSearch.getSavedSearchBtn1().Click();
		sessionSearch.selectSavedsearchesInTree("My Saved Search");
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(5);
		sessionSearch.serarchWP();
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		docViewRedact.validatePersistentPanelHitCountAgainstDocHighlightedCount(keywordsArrayPT[0]);
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51934 Verify that when completing the documents same as last the
	 * entry for the navigated document in mini-DocList child window must always
	 * present fully in the visible area of mini doc list child window stabilization
	 * done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 21)
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
	 * Id:RPMXCON-51404 Verify all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from Saved
	 * Search > doc list
	 * Stabilization done
	 */

	 @Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority =
	 22)
	public void verifyHighlightedKeywordsForDocsAreDisplayedSavedSearch() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		baseClass.stepInfo("Test case id : RPMXCON-51404");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from Saved Search > doc list");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
	}
	 
	 /**
		 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
		 * Id:RPMXCON-51405 Verify all hits of the document should be highlighted
		 * without clicking the eye icon when user redirects to doc view from Advanced
		 * Search > doc list to doc view
		 * stabilization done
		 */
		@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 23)
		public void verifyHighlightedKeywordsForDocsAreDisplayedSearchWithAdvancedSearch() throws Exception {
			baseClass = new BaseClass(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
			docViewRedact = new DocViewRedactions(driver);
		    docView = new DocViewPage(driver);
			loginPage = new LoginPage(driver);
			String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

			baseClass.stepInfo("Test case id : RPMXCON-51405");
			baseClass.stepInfo(
					"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from Advanced Search > doc list to doc view");

			String codingForm = Input.codeFormName;
			baseClass.stepInfo("Create new assignment");
			assignmentsPage.createAssignment(assignmentName, codingForm);
			sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
			sessionSearch.saveSearch(searchName);
//			sessionSearch.saveSearchAdvanced_New(searchName,"Shared With Project Administrator");
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
			new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
			sessionSearch.viewInDocView_redactions();
			driver.waitForPageToBeReady();
			docView.selectDocIdInMiniDocList(Input.sourceDocId1);
			driver.waitForPageToBeReady();
			docViewRedact.verifyHighlightedTextsAreDisplayed();
			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo(
					"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");
			driver.waitForPageToBeReady();
			sessionSearch.switchToWorkproduct();
			sessionSearch.getSavedSearchBtn1().Click();
			sessionSearch.selectSavedsearchesInTree("My Saved Search");
			baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
			sessionSearch.getMetaDataInserQuery().waitAndClick(5);
			sessionSearch.serarchWP();
			sessionSearch.ViewInDocList();
			new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
			sessionSearch.viewInDocView_redactions();
			driver.waitForPageToBeReady();
			docView.selectDocIdInMiniDocList(Input.sourceDocId1);
			driver.waitForPageToBeReady();
			docViewRedact.verifyHighlightedTextsAreDisplayed();
			loginPage.logout();
		}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51332 Verify text from review mode in context of an assignment
	 * stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 24)
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
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifySearchTermHighlightedInEyeIconFromAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51396");
		baseClass.stepInfo(
				"Verify search term, assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assigned after searching with term");

		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
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
	 * assignment and documents are distributed again Stabilization done
	 */

	@Test(enabled = true, alwaysRun = false, groups = { "regression" }, priority = 26)
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

	 @Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority =
	 27)
	public void verifyImpersonationHitsOfDocWithoutClickingEyeIconToDocViewFromDocList() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("SavedSearch the non audio documents and go to docview");
		driver.waitForPageToBeReady();
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);
		savedSearch.savedSearchToDocList(searchName);
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("AdvancedSearch the non audio documents and go to docview");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 28)
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 29)
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
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify search term, assigned keywords should be highlighted and
	 * should be displayed on click of the eye icon when redirected to doc view from
	 * assignment. 'RPMXCON-51397' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyCreateAssignSearchTermHighlightedInEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51397");
		baseClass.stepInfo(
				"Verify search term, assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		String hitTerms = "Test" + Utility.dynamicNameAppender();

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// color highlighting
		softAssertion.assertTrue(docViewRedact.get_textHighlightedColor().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// color highlighting
		softAssertion.assertTrue(docViewRedact.get_textHighlightedColor().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();
	}

	/**
	 * Author : Sakthivel date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51043 Verify user can maximize the middle panel of the doc view
	 * redirecting from Basic Search/Saved Search/Doc List/My Assignment/Manage
	 * Assignment/Manage Reviewers.
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 34)
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
	@Test(enabled = true, groups = { "regression" }, priority = 35)
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
	 * Author : Sakthivel date: NA Modified date: NA Modified by: NA Test Case Case
	 * has a larger flow - using 2 different users and different projects
	 * Id:RPMXCON-50782 To verify assignment progress bar should be displayed on doc
	 * view page as per the selected assignment
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 37)
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
	 * Author : Mohan date: 27/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify Keyword highlighting is working for Searchable PDF (with Mapped
	 * dataset having RequiredPDFGenartion is TRUE)'RPMXCON-51982'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyKeywordHighlightingWorkingForSearchablePDF() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51981");
		baseClass.stepInfo(
				"Verify Search Term highlighting is working for Searchable PDF (with Mapped dataset having RequiredPDFGenartion is TRUE)");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String keyword = "to" + Utility.dynamicNameAppender();
		String color = "Gold";

		baseClass.stepInfo("Step 1: Prerequisites: Keyword groups should be created   with different Keywords");

		keywordPage.addKeywordWithColor(keyword, color);

		baseClass.stepInfo("Step 2:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 3: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");

		loginPage.logout();

		// login As PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 3: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 3:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 4: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");
		loginPage.logout();
	}

	/**
	 * @author Sakthivel TestCase Id:51881 C3B: Verify that Action > Folder works
	 *         fine when all records in the reviewers batch are in mixed state but
	 *         records that are in Completed state are also present along with
	 *         records in an Uncompleted state in Mini DocList
	 * @throws InterruptedException
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 39)
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
	 * @author Sakthivel TestCase Id:51880 C2B: Verify that Action > Remove Code
	 *         Same works fine when all records in the reviewer's batch are in mixed
	 *         state but records that are in Completed state are also present along
	 *         with records in an Uncompleted state in Mini DocList
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 40)
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
	 * @author Sakthivel TestCase Id:51006 Verify user can see the thumbnail image
	 *         of each page of the document being viewed on doc view page in
	 *         thumbnail panel when redirecting from other than assignment page
	 * @throws InterruptedException
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 41)
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

	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyRemarksForSelectedDocsAfterImpersonating() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.logout();
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
		docView = new DocViewPage(driver);
		docViewRedact.selectMiniDocListAndViewInDocView(2);
		docView.addRemarkToNonAudioDocument(1, 20, remark);
		docViewRedact.verifyReviewerRemarksIsPresent();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Select document and click to see Reviewer Remarks");
		docViewRedact.selectMiniDocListAndViewInDocView(2);
		docView.addRemarkToNonAudioDocument(1, 20, remark);
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
		docViewRedact.selectMiniDocListAndViewInDocView(2);
		docView.addRemarkToNonAudioDocument(1, 20, remark);
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
		docViewRedact.selectMiniDocListAndViewInDocView(2);
		docView.addRemarkToNonAudioDocument(1, 20, remark);
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
	@Test(description = "RPMXCON-52170", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 43)
	public void verifyToolTipMouseOverCodeSameAsLast() throws Exception {
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
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
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Reviewer are added and doc distributed and Viewed in Docview successfully");

		// select docs from MiniDoclist
		docViewRedact.selectMiniDocListAndViewInDocView(3);
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
		driver.driver.switchTo().window(parentWindowID);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 44)
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
	@Test(enabled = true, groups = { "regression" }, priority = 45)
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
		docViewPage.selectDocIdInMiniDocList(Input.StitchedTiffSourceDocID);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 46)
	public void verifyWithOutAddingRemarkShouldNotSave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51625");
		baseClass.stepInfo("Needs to verify with out adding remarks should not save");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search with text input completed");
		sessionsearch.ViewInDocView();

		// Reviewer remarks status check
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_AddRemarkIcon());
		docViewPage.getDocView_AddRemarkIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		docViewPage.getDocView_Next().Click();
		driver.waitForPageToBeReady();
		if (docViewPage.getAddRemarkbtn().isElementAvailable(1)) {
			baseClass.passedStep("Reviewer remarks menu remains displayed after moving to the next document");
		} else {
			baseClass.failedStep("Reviewer remarks menu not displayed after moving to the next document");
		}

		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(30);
		driver.waitForPageToBeReady();
		// Reviewer remarks status check
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_AddRemarkIcon());
		docViewPage.getDocView_AddRemarkIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		docViewPage.getDocView_Next().Click();
		driver.waitForPageToBeReady();
		if (docViewPage.getAddRemarkbtn().isElementAvailable(1)) {
			baseClass.passedStep("Reviewer remarks menu remains displayed after moving to the next document");
		} else {
			baseClass.failedStep("Reviewer remarks menu not displayed after moving to the next document");
		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51871
	 * Verifying persistent hit for docs from assignment DocView- sprint 3
	 */

	@Test(description = "RPMXCON-51871", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 47)
	public void persistentHitCheckNonAudioDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51871");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.toggleEnableAnalyticsPanel();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewRedact.checkingPersistentHitPanel();
		docViewRedact.VerifyKeywordHitsinDoc();
		docViewRedact.forwardToLastDoc().Click();
		driver.waitForPageToBeReady();
		docViewRedact.VerifyKeywordHitsinDoc();
		docViewRedact.backwardToFirstDoc().Click();
		driver.waitForPageToBeReady();
		docViewRedact.VerifyKeywordHitsinDoc();
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
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsAanalytics() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51355");
		baseClass.stepInfo(
				"Verify when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and views document from analytics panel previously selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();

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
	@Test(enabled = true, groups = { "regression" }, priority = 49)
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
	@Test(enabled = true, groups = { "regression" }, priority = 50)
	public void verifyKWGroupAndSavedSeacrhOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51553");
		baseClass.stepInfo("Verify Persistent KW Groups as well as Saved Searching on doc view");
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		String panelText = "basis)" + Utility.dynamicNameAppender();

		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Step1: Add Keyword to the project");
		keywordPage.navigateToKeywordPage();
		keywordPage.addKeyword(saveName, panelText, "Blue");

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
	@Test(enabled = true, groups = { "regression" }, priority = 51)
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
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		// eye Icon
		docViewRedact.getDocView_MiniDoc_Selectdoc(1).waitAndClick(20);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51008 Verify thumbnails in Doc View From assignments page
	 */

	@Test(description = "RPMXCON-51008", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 53)
	public void checkingThumbnailsIconFromAssignment() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51008");
		baseClass.stepInfo(
				"Verify user can see the thumbnail image of each page of the document being viewed on doc view page in thumbnail panel when redirecting from assignment page");

		baseClass.stepInfo("Create new assignment");
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.docIdThumbnails);
		sessionSearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingThumbnailIcon();
		if (docViewRedact.thumbNailsPanel().isElementPresent() == true) {
			baseClass.passedStep("The thumbnails panel is clicked and menu is visible");
		} else {
			baseClass.failedStep("The thumbnail Panel menu is NOT displayed");
		}
		assignmentspage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51851 Verify that persistent hits panel should not retain
	 * previously viewed hits for the document on completing the document after
	 * applying the coding stamp.
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 53)
	public void verifyPersistentHitsCompletingDocumentsAfterCodingStamp() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51851");
		baseClass.stepInfo(
				"Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document after applying the coding stamp");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);
		baseClass.stepInfo("Sharing the saved search with security group");

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Logout as ReviewManager
		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");

		// Verify AssignmentName is Displayed
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		if (assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue()) {
			baseClass.passedStep("User is in doc view in context of an assignment");
			softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("User is in doc view in context of an assignment is not assigned");
		}

		// verify PeristantHitEyeIcon is Displayed
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

		baseClass.waitForElement(docView.getHitPanelCount());
		String beforeComplete = docView.getHitPanelCount().getText();
		System.out.println(beforeComplete);
		docView.editCodingFormAndSaveWithStamp(filedText, Input.stampColour);
		String getAttribute = docView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		docView.lastAppliedStamp(Input.stampColour);
		softAssert.assertEquals("Saving with Stamp", getAttribute);
		if (getAttribute.equals("Saving with Stamp")) {
			baseClass.passedStep("Expected Message -StamplastIcon is clicked scuessfully..");
		} else {
			baseClass.failedStep("Expected Message - StamplastIcon is not clicked");
		}
		baseClass.waitForElement(docView.getCompleteDocBtn());
		docView.getCompleteDocBtn().waitAndClick(10);
		baseClass.stepInfo("Document completed successfully");
		baseClass.waitForElement(docView.getHitPanelCount());
		String afterComplete = docView.getHitPanelCount().WaitUntilPresent().getText();
		System.out.println(afterComplete);
		baseClass.stepInfo("persistent hits panel is not retain previously viewed hits");

		softAssert.assertNotEquals(beforeComplete, afterComplete);
		softAssert.assertAll();
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
	@Test(enabled = true, groups = { "regression" }, priority = 54)
	public void verifyAfterImpersonationNavigatePageSavingFromDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50923");
		baseClass.stepInfo(
				"Verify waning message is prompted to the user after impersonation when user navigates away from the page without saving action from doc view.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
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
		docView.performCodeSameForFamilyMembersDocs();
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
		docView.performCodeSameForFamilyMembersDocs();
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
		docView.performCodeSameForFamilyMembersDocs();
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
		docView.performCodeSameForFamilyMembersDocs();
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
	@Test(enabled = true, groups = { "regression" }, priority = 55)
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
		baseClass.passedStep("Completed Proceded Document is Displayed Successfully");
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
	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void verifyAfterImpersonationProducedDocsInImageTabDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51270");
		baseClass.stepInfo(
				"Verify after impersonation user can see the production option in the drop down selection of Images tab when document generated as part of production.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
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
		baseClass.passedStep("Completed Proceded Document is Displayed Successfully");
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
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void verifyAssignmentProgressBarCompleteDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51272");
		baseClass.stepInfo("Verify assignment progress bar refreshesh after completing the document.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

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

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

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
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can view the folders which is associated to
	 * the security group only. 'RPMXCON-50820' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void veriyUserCanSeeTheFlodersSecurityGroup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50820");
		baseClass.stepInfo("To verify that user can view the folders which is associated to the security group only.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList();
		driver.waitForPageToBeReady();
		if (docView.getDocView_AnalyticsExitingFolderName1().Displayed()) {
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
		if (docView.getDocView_AnalyticsExitingFolderName1().Displayed()) {
			baseClass.passedStep("All existing folder under that security group is displayed");
		} else {
			baseClass.failedStep("All existing folder under that security group is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 10/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that count should be updated if document is marked is
	 * Uncompleted 'RPMXCON-51031' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51031", enabled = true, groups = { "regression" }, priority = 59)
	public void verifyCountShouldBeUpdatedDocumentIsMarkedIsUncompleted() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51031");
		baseClass
				.stepInfo("To verify that once document is marked as 'Un-Completed'assignment progress bar is updated");
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		driver.waitForPageToBeReady();
		System.out.println(assname);
		baseClass
				.stepInfo("Doc is Assigned from basic Search and Assignment '" + assname + "' is created Successfully");

		driver.waitForPageToBeReady();
		System.out.println(assname);
		docViewRedact.getHomeDashBoard();
		docViewRedact.selectAssignmentfromDashborad(assname);
		docViewRedact.performCompleteToDocs();
		docViewRedact.performGeerIcon();
		docViewRedact.performUnCompleteToDocs();
		docViewRedact.clickManage().waitAndClick(30);
		docViewRedact.manageAssignments().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.passedStep("The Documents is marked Uncompleted successfully");
		loginPage.logout();

	}

	/**
	 * Author : sowndarya.velraj date: 07/02/22 Description:In the Persistent Search
	 * Hits Panel, in the upper right of each card is a date should be removed for
	 * non-audio documents (for Platinum) 'RPMXCON-51501'.sprint 12
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 60)
	public void verifyDateRemovalInPersistentSearchPanel() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51501");
		baseClass.stepInfo(
				"In the Persistent Search Hits Panel, in the upper right of each card is a date should be removed for non-audio documents (for Platinum)");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String keyword = "to" + Utility.dynamicNameAppender();
		String color = "Gold";

		baseClass.stepInfo(" Prerequisites: Keyword groups should be created   with different Keywords");

		keywordPage.addKeywordWithColor(keyword, color);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				" Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		String upperCard = docView.getTermCardInPersistentPanel().getText();
		System.out.println(upperCard);

		if (upperCard.contains("2022")) {
			baseClass.failedStep("Date is displayed");
		} else {
			baseClass.passedStep("Date is not displayed");
		}
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
	@Test(enabled = true, groups = { "regression" }, priority = 61)
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

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
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
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		docView.editCodingFormComplete();
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
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		docView.clickCodeSameAsLast();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-59583", alwaysRun = true, groups = { "regression" }, priority = 62)
	public void verifyConfigureManualMode_RMUDashboard() throws Exception {
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-59583");
		baseClass.stepInfo("Verify the context on navigating to doc view from RMU dashboard "
				+ "after configuring the mini doc list should be assignment");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// creating And Distributing the Assignment
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		String expectedCount = assignmentsPage.getDistibuteDocsCount(Input.rmu1userName);
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate as Reviewer
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentfromRMUDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		baseClass.stepInfo("***Verification for Manual Sort Order**");
		// Configuring mini doc list in manual mode
		driver.waitForPageToBeReady();

		miniDocListpage.verifyDefaultWebfieldsInManualSortOrder();
		// performing Add and Remove action on Selected Web Fields and validating the
		// same.
		miniDocListpage.verifyManualModeSortingConfigure(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentsPage.selectAssignmentfromRMUDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		DocViewPage dc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		String ActualCount = dc.verifyDocCountDisplayed_DocView();
		System.out.println(ActualCount);
		String codingFormName = miniDocListpage.getDocView_CodingFromName().getText();
		if (miniDocListpage.getDocumentCompleteButton().isDisplayed()) {
			softAssertion.assertEquals(codingFormName, Input.codeFormName);
			softAssertion.assertEquals(ActualCount, expectedCount);
			softAssertion.assertAll();
			baseClass.passedStep(
					"RMU user not viewed Completed icon and assigned coding form is viewed in mini doc list in context of RMU dashboard.");
			baseClass.passedStep("Document Count displayed in doc view  is same as assigned to the assignment.");
		} else {
			baseClass.failedStep("RMU user viewed Completed icon in mini doc list in context of RMU dashboard.");
		}
		reusableDocViewPage.defaultHeaderValue();
		baseClass.stepInfo("***Verification for Optimized Sort Order**");
		miniDocListpage.sortingVerifyAfterSelectedWebFields();
		String ActualCount1 = dc.verifyDocCountDisplayed_DocView();
		if (miniDocListpage.getDocumentCompleteButton().isDisplayed()) {
			softAssertion.assertEquals(codingFormName, Input.codeFormName);
			softAssertion.assertEquals(ActualCount1, expectedCount);
			softAssertion.assertAll();
			baseClass.passedStep(
					"RMU user not viewed Completed icon and assigned coding form is viewed in mini doc list in context of manage assignment.");
			baseClass.passedStep("Document Count displayed in doc view  is same as assigned to the assignment.");
		} else {
			baseClass.failedStep("RMU user viewed Completed icon in mini doc list in context of manage assignment.");
		}
		loginPage.logout();
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
