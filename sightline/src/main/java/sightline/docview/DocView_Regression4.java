package sightline.docview;

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
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Test
	 * Case Id:RPMXCON-51763 Non-Audio Documents assigned from Basic search- Verify
	 * that when documents are re-assigned to same/other reviewer in an assignment,
	 * any previously saved persistent search hits in the assignment should be
	 * displayed in the assignment Stabilization done
	 */
	@Test(description = "RPMXCON-51763", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
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

	@Test(description = "RPMXCON-51762", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		savedSearch.savedSearch_Searchandclick(searchName);

		baseClass.waitForElement(savedSearch.getSavedSearchToBulkAssign());
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
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
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

	@Test(description = "RPMXCON-51399", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		baseClass.waitTime(5);
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
//		Added on 10_04
		docViewRedact.verifyHighLightingTextInDocView();
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
	 * Id:RPMXCON-51402 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searched with work product
	 * STABILIZATION DONE
	 */

	@Test(description = "RPMXCON-51402", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyHighlightedKeywordsForDocsSearchWithWorkProduct() throws Exception {

		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51402");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assinged after searched with work product");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
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
//		Added on 10_04
		docViewRedact.verifyHighLightingTextInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
		baseClass.impersonateReviewertoRMU();
		driver.waitForPageToBeReady();
		assignmentsPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51400 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with comment/Reviewer Remarks
	 */

	@Test(description = "RPMXCON-51400", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Creating Prerequisite");
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);

		sessionSearch.viewInDocView();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
//		docView.addRemarkByText("Remark by Rmu");
		docView.addRemarkToNonAudioDocument(5, 20, "Remark by Rmu");
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.getCommentsOrRemarksCount("Remark", "\"Remark by Rmu\"");
		sessionSearch.bulkAssign();
		baseClass.waitTime(5);
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		baseClass.waitTime(5);
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
//		Added 10_04
//		docViewRedact.verifyHighlightedTextsAreDisplayed();
		docViewRedact.verifyHighLightingTextInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitTime(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArrayPT);
		baseClass.impersonateReviewertoRMU();
//		Added 10_04
		baseClass.waitTime(5);
		assignmentsPage.deleteAssgnmntUsingPagination(assignmentName);
		baseClass.stepInfo("Deleting Prerequisite");
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		baseClass.waitTime(5);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.clickingRemarksIcon();
		docView.deleteReamark("Remark by Rmu");
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51401 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when assinged documents are searched with metadata
	 */

	@Test(description = "RPMXCON-51401", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.navigateToAssignmentsPage();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
//		Added 10_04
//		docViewRedact.verifyHighlightedTextsAreDisplayed();
		docViewRedact.verifyHighLightingTextInDocView();
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
	 * Id:RPMXCON-51771 Verify that previously saved Persistent hits displayed on
	 * the doc view when documents assigned to same/another reviewer are completed
	 * from edit assignment stabilization done
	 */

	@Test(description = "RPMXCON-51771", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitsAfterCompleteDocumentsSavedSearchGroup() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51771");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits displayed on the doc view when documents assigned to same/another reviewer are completed from edit assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		SavedSearch savedSearch = new SavedSearch(driver);
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 5);

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

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		assignmentsPage.selectAssignmentfromRMUDashborad(assname);

		driver.waitForPageToBeReady();

		driver.waitForPageToBeReady();
		docViewRedact.performGeerIcon();
		docView.scrollUntilloadingTextDisplay(false);

//		Added 14
		driver.Navigate().refresh();
		baseClass.waitTime(4);
		baseClass.waitForElement(docView.getCompletedDocs());
		docView.getCompletedDocs().waitAndClick(5);
//		docViewRedact.selectMiniDocListAndViewInDocView(1);

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after completing docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
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
	 * searched with work product Stabilization done
	 */

	@Test(description = "RPMXCON-51408", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("SourceDocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
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
		assignmentsPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51853 Verify that persistent hits panel should not retain
	 * previously viewed hits for the document on completing the document from
	 * coding form child window stabilization done
	 */

	@Test(description = "RPMXCON-51853", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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

	@Test(description = "RPMXCON-51944", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
		baseClass.selectproject(Input.additionalDataProject);
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

	@Test(description = "RPMXCON-51941", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocInMiniDocListAfterScrollingDownTillLoadingTextDisplayed() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51941");
		baseClass.stepInfo(
				"Verify that when user is selecting a document to view after scrolling down/up the mini doc list and in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList (to the user)");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
		baseClass.selectproject(Input.additionalDataProject);
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
	 * Author :Vijaya.Rani date: 18/02/2022 Modified date: NA Modified by: NA
	 * Description :Verify to ensure that multiple terms submitted, one with a space
	 * and other without are also handled. 'RPMXCON-51613' Sprint-12
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51613", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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
	 * Id:RPMXCON-51405 Verify all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from Advanced
	 * Search > doc list to doc view stabilization done
	 */
	@Test(description = "RPMXCON-51405", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyHighlightedKeywordsForDocsAreDisplayedSearchWithAdvancedSearch() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
//			AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		DocListPage docList = new DocListPage(driver);
		loginPage = new LoginPage(driver);
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		baseClass.stepInfo("Test case id : RPMXCON-51405");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from Advanced Search > doc list to doc view");

//			
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.advancedMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.ViewInDocList();

//			new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();		
		docList.selectAllDocs();
		docList.docListToDocView();
		driver.waitForPageToBeReady();
//			Added on 10_04

		docViewRedact.verifyHighLightingTextInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.advancedMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.ViewInDocList();

//			new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();		
		docList.selectAllDocs();
		docList.docListToDocView();

		driver.waitForPageToBeReady();
//			Added on 10_04
		docViewRedact.verifyHighLightingTextInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.advancedMetaDataSearch("SourceDocID", null, Input.sourceDocId1, null);
		sessionSearch.ViewInDocList();

//			new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		docList.selectAllDocs();
		docList.docListToDocView();
		driver.waitForPageToBeReady();
//			Added on 10_04
		docViewRedact.verifyHighLightingTextInDocView();
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
	@Test(description = "RPMXCON-51396", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);
		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		baseClass.impersonateRMUtoReviewer();

//		Added on 10_04
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
//		Added on 10_04

		docViewRedact.verifyHighLightingTextInDocView();
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

	@Test(description = "RPMXCON-51773", enabled = true, alwaysRun = false, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify search term, assigned keywords should be highlighted and
	 * should be displayed on click of the eye icon when redirected to doc view from
	 * assignment. 'RPMXCON-51397' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51397", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);

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
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);

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
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);
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
	 * @Author Raghuram A date:01/02/2022 Modified date: NA Modified by:N/A
	 * @Description : To verify that once user complete the document, count should
	 *              increased on the Edit Assignment->Manage Reviewers..
	 *              [RPMXCON-50987]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-50987", enabled = true, groups = { "regression" })
	public void VerifyCompleteDocCountViaRevTab() throws Exception {

		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		String assignmentNametoCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		List<String> docIDlist = new ArrayList<>();
		String userName = Input.rmu1userName;
		int iteration = 1;

		baseClass.stepInfo("Test case Id:RPMXCON-50987 Sprint 11");
		baseClass.stepInfo(
				"To verify that once user complete the document, count should increased on the Edit Assignment->Manage Reviewers.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
//		// Assignment Creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentNametoCreate, Input.codingFormName);
		driver.waitForPageToBeReady();
		assignmentPage.assignmentDistributingToReviewerManager();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// distributedCOunt pick
		driver.waitForPageToBeReady();
		String distributedCOunt = assignmentPage.getDistibuteDocsCount(userName);
		baseClass.stepInfo("Distributed count to the user : " + distributedCOunt);

		// Assignment selection from Dashboard
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getDashBoardLink());
		miniDocListpage.getDashBoardLink().waitAndClick(5);
		driver.waitForPageToBeReady();
		miniDocListpage.chooseAnAssignmentFromDashBoard(assignmentNametoCreate);

		// Complete document
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Complete doc iteration count : " + iteration);
		docIDlist = miniDocListpage.getDocListDatas();
		miniDocListpage.verifyCompleteCheckMarkIconandDocHighlight(docIDlist, iteration, false);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

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
	@Test(description = "RPMXCON-50782", enabled = true, alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	@Test(description = "RPMXCON-51981", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Step 1: Prerequisites: Keyword groups should be created   with different Keywords");

		keywordPage.navigateToKeywordPage();
		keywordPage.addKeywordWithColor(keyword, color);
//
		baseClass.stepInfo("Step 2:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 3: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		driver.waitForPageToBeReady();
		docView.persistenHitWithSearchString(keyword);
		baseClass.waitForElement(docView.getDocView_PersistanceHit_PanelTextNew(keyword));
		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelTextNew(keyword).isDisplayed());
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.persistenHitWithSearchString(keyword);
		baseClass.waitForElement(docView.getDocView_PersistanceHit_PanelTextNew(keyword));
		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelTextNew(keyword).isDisplayed());
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
		driver.waitForPageToBeReady();
		docView.persistenHitWithSearchString(keyword);
		baseClass.waitForElement(docView.getDocView_PersistanceHit_PanelTextNew(keyword));
		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelTextNew(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 03/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51028 To verify that after impersonation user can see remarks for
	 * selected document
	 * 
	 * @Stabilization - done
	 */

	@Test(description = "RPMXCON-51028", enabled = true, groups = { "regression" })
	public void verifyRemarksForSelectedDocsAfterImpersonating() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51028");
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
		String docId = docView.getDocumentWithoutRedaction();
		System.out.println(docId);
		docView.selectDocInMiniDocList(docId);
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
		docView.selectDocInMiniDocList(docId);
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
		docView.selectDocInMiniDocList(docId);
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
		docView.selectDocInMiniDocList(docId);
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
	@Test(description = "RPMXCON-52170", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyToolTipMouseOverCodeSameAsLast() throws Exception {
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		// Selecting Document from Session search
		DocViewPage docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52170");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
		String docId = docView.getDocumentWithoutRedaction();

		driver.Navigate().refresh();
		baseClass.stepInfo("Doc is Selected from MiniDocList successfully");
		docView.selectDocInMiniDocList(docId);
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
	@Test(description = "RPMXCON-51441", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyArrowsRedirdctedToDocViewFromBasicSearch() throws Exception {

		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51441");
		baseClass.stepInfo(
				"Verify that > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.performTheEyeIconHighLighting();

		docViewRedact.verifyHighLightingTextInDocView();

		baseClass.stepInfo(
				"That > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search is Successfully");
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.performTheEyeIconHighLighting();
		baseClass.stepInfo(
				"That > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search is Successfully");
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 7/12/21 NA Modified date: NA Modified by:NA
	 * Description :Needs to verify with out adding remarks should not
	 * save'RPMXCON-51625' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51625", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyWithOutAddingRemarkShouldNotSave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51625");
		baseClass.stepInfo("Needs to verify with out adding remarks should not save");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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

	@Test(description = "RPMXCON-51871", enabled = true, alwaysRun = true, groups = { "regression" })
	public void persistentHitCheckNonAudioDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51871");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
		baseClass.waitForElement(docViewRedact.forwardToLastDoc());
		docViewRedact.forwardToLastDoc().waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewRedact.VerifyKeywordHitsinDoc();
		baseClass.waitForElement(docViewRedact.backwardToFirstDoc());
		docViewRedact.backwardToFirstDoc().waitAndClick(5);
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
	@Test(description = "RPMXCON-51355", enabled = true, groups = { "regression" })
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsAanalytics() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51355");
		baseClass.stepInfo(
				"Verify when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and views document from analytics panel previously selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	@Test(description = "RPMXCON-51354", enabled = true, groups = { "regression" })
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51354");
		baseClass.stepInfo(
				"Verify after impersonation when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and navigates to other doc from mini doc list child window previously selected selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);

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
		baseClass.selectproject(Input.additionalDataProject);
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
		baseClass.selectproject(Input.additionalDataProject);
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
	@Test(description = "RPMXCON-51553", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Step1: Add Keyword to the project");
		keywordPage.navigateToKeywordPage();
		keywordPage.addKeyword(saveName, panelText, "Blue");

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		driver.waitForPageToBeReady();
		docView.persistenHitWithSearchString(panelText);
//		Added on 10_04
		baseClass.waitForElement(docView.getDocView_PersistanceHit_PanelTextNew(panelText));
		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelTextNew(panelText).isDisplayed());
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
		driver.waitForPageToBeReady();
		docView.persistenHitWithSearchString(panelText);
		baseClass.waitForElement(docView.getDocView_PersistanceHit_PanelTextNew(panelText));
		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelTextNew(panelText).isDisplayed());
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
		driver.waitForPageToBeReady();
		docView.persistenHitWithSearchString(panelText);
		baseClass.waitForElement(docView.getDocView_PersistanceHit_PanelTextNew(panelText));
		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelTextNew(panelText).isDisplayed());
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
	@Test(description = "RPMXCON-51775", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Author : Vijaya.Rani date: 26/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify waning message is prompted to the user after
	 * impersonation when user navigates away from the page without saving action
	 * from doc view.'RPMXCON-50923' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-50923", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationNavigatePageSavingFromDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50923");
		baseClass.stepInfo(
				"Verify waning message is prompted to the user after impersonation when user navigates away from the page without saving action from doc view.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.threadDocId);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Search with text input for docs completed");
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add3ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.threadDocId);
		sessionSearch.viewInDocView();
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
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.threadDocId);
		sessionSearch.viewInDocView();
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
		driver.waitForPageToBeReady();
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.threadDocId);
		sessionSearch.viewInDocView();
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
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.threadDocId);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.editCodingForm("Sample");
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
	@Test(description = "RPMXCON-51271", enabled = true, groups = { "regression" })
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

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.additionalDataProject);
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
		page.fillingPDFSection(tagname, Input.testData1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
//		page.fillingProductionLocationPage(productionname);
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		driver.waitForPageToBeReady();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
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
	@Test(description = "RPMXCON-51270", enabled = true, groups = { "regression" })
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
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);
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
		page.fillingPDFSection(tagname, Input.testData1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
//		page.fillingProductionLocationPage(productionname);
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		driver.waitForPageToBeReady();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo(" Impersonating SA to PA");
		baseClass.impersonateSAtoPA();
		baseClass.selectproject(Input.additionalDataProject);
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
	@Test(description = "RPMXCON-51272", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		driver.waitForPageToBeReady();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		driver.waitForPageToBeReady();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		// perform MiniDocList CodeSame As
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(5);

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
		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(5);

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
	@Test(description = "RPMXCON-50820", enabled = true, groups = { "regression" })
	public void veriyUserCanSeeTheFlodersSecurityGroup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50820");
		baseClass.stepInfo("To verify that user can view the folders which is associated to the security group only.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String folderName = "Folder" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.additionalDataProject);
		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
//		
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		tag.navigateToTagsAndFolderPage();
		tag.CreateFolderInRMU(folderName);

		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList(folderName);
		driver.waitForPageToBeReady();
		if (docView.getFolderSelection(folderName).Displayed()) {
			baseClass.passedStep("All existing folder under that security group is displayed");
		} else {
			baseClass.failedStep("All existing folder under that security group is not displayed");
		}
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
//		Added on 10_04
		driver.waitForPageToBeReady();
		driver.Manage().window().maximize();
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		docView.performFloderMiniDocListForReviewer(folderName);
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		driver.Manage().window().maximize();
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);
		String folderNamePA = "FolderPA" + Utility.dynamicNameAppender();
		tag.navigateToTagsAndFolderPage();
		tag.CreateFolder(folderNamePA, Input.securityGroup);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList(folderNamePA);
		driver.waitForPageToBeReady();
		if (docView.getFolderSelection(folderNamePA).Displayed()) {
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
	@Test(description = "RPMXCON-51031", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		int purehit = sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, purehit);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		System.out.println(assname);
		baseClass
				.stepInfo("Doc is Assigned from basic Search and Assignment '" + assname + "' is created Successfully");

		driver.waitForPageToBeReady();
		System.out.println(assname);
		docViewRedact.selectAssignmentfromDashborad(assname);
		driver.waitForPageToBeReady();
		docViewRedact.performCompleteToDocs();

		docViewRedact.performGeerIcon();
		docViewRedact.performUnCompleteToDocs();
		baseClass.waitForElement(docViewRedact.clickManage());
		docViewRedact.clickManage().waitAndClick(30);
		baseClass.waitForElement(docViewRedact.manageAssignments());
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
	@Test(description = "RPMXCON-51501", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(" Prerequisites: Keyword groups should be created   with different Keywords");

		keywordPage.navigateToKeywordPage();
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
	@Test(description = "RPMXCON-51279", enabled = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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
	@Test(description = "RPMXCON-59583", alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
//		baseClass.selectproject();
		// creating And Distributing the Assignment
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.waitTime(10);
		String expectedCount = assignmentsPage.getDistibuteDocsCount(Input.rmu1userName);
		System.out.println(expectedCount);
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate as Reviewer
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentfromRMUDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		baseClass.stepInfo("***Verification for Manual Sort Order**");
		// Configuring mini doc list in manual mode
		driver.waitForPageToBeReady();

//		miniDocListpage.verifyDefaultWebfieldsInManualSortOrder();
		// performing Add and Remove action on Selected Web Fields and validating the
		// same.
		miniDocListpage.afterImpersonateWebFieldsSelectionManualMode();
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
		System.out.println(ActualCount1);
		if (miniDocListpage.getDocumentCompleteButton().isDisplayed()) {
			softAssertion.assertEquals(codingFormName, Input.codeFormName);
			softAssertion.assertEquals(ActualCount1, expectedCount);
			baseClass.passedStep(
					"RMU user not viewed Completed icon and assigned coding form is viewed in mini doc list in context of manage assignment.");
			baseClass.passedStep("Document Count displayed in doc view  is same as assigned to the assignment.");
		} else {
			baseClass.failedStep("RMU user viewed Completed icon in mini doc list in context of manage assignment.");
		}
		softAssertion.assertAll();
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
