package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.Color;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
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

public class DocView_Regression5 {

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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 1)
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
		docView.verifyHighlightedKeywordInDocView();
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
		docView.verifyHighlightedKeywordInDocView();
		baseClass.waitForElement(docViewRedact.getKeywordInPersistentHitPanel());
		docViewRedact.getKeywordInPersistentHitPanel().Click();
		if (docViewRedact.getKeywordInPersistentHitPanel().Enabled() == true) {
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 2)
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
		docView.verifyHighlightedKeywordInDocView();
		baseClass.waitForElement(docViewRedact.getKeywordInPersistentHitPanel());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.getKeywordInPersistentHitPanel().Enabled() == true) {
			baseClass.passedStep("next btn clickable");
		} else {
			baseClass.failedStep("next btn not clickable");
		}

		// Impersonation as Rev and checking the above

		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		docView.verifyHighlightedKeywordInDocView();
		baseClass.waitForElement(docViewRedact.getKeywordInPersistentHitPanel());
		docViewRedact.getKeywordInPersistentHitPanel().Click();
		if (docViewRedact.getKeywordInPersistentHitPanel().Enabled() == true) {
			baseClass.passedStep("next btn clickable");
		} else {
			baseClass.failedStep("next btn not clickable");
		}
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51403 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searching with reviewer
	 * remarks/comments stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 3)
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
	 * Id:RPMXCON-51942 Verify when user is selecting a document to view after
	 * scrolling down the mini doc list child window when 'Loading..' displays in
	 * DocView, the entry for the same document must always present fully in the
	 * visible area of the mini-DocList child window
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 4)
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
		sessionSearch.ViewInDocView();

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
		sessionSearch.ViewInDocView();
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
		sessionSearch.ViewInDocView();

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
	 * Id:RPMXCON-51939 Verify that when user is selecting a document to view after
	 * scrolling down/up the mini doc list and in DocView, the entry for the same
	 * document in mini-DocList must always present fully in the visible area of the
	 * mini-DocList (to the user)
	 * 
	 * stabilization - done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 5)
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
		sessionSearch.ViewInDocView();

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
		sessionSearch.ViewInDocView();

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
		sessionSearch.ViewInDocView();

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
	 * Id:RPMXCON-51404 Verify all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from Saved
	 * Search > doc list Stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 6)
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51398 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with work product Stabilization done
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 7)
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
		driver.waitForPageToBeReady();
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 8)
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
		sessionSearch.ViewInDocView();

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
		sessionSearch.ViewInDocView();

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
	 * Id:RPMXCON-51332 Verify text from review mode in context of an assignment
	 * stabilization done
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 9)
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
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51330 Verify after impersonation all hits of the document should
	 * be highlighted without clicking the eye icon when user redirects to doc view
	 * from doc list
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 10)
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		new DocListPage(driver).selectingAllDocFromAllPagesAndAllChildren();
		sessionSearch.viewInDocView_redactions();
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
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
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 11)
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 12)
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
		driver.waitForPageToBeReady();
		docViewRedact.verifyMaximizetheMiddlePanel();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("SA has been impersonated as PA");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		SessionSearch sessionsearch1 = new SessionSearch(driver);
		sessionsearch1.basicContentSearch(Input.searchString1);
		sessionsearch1.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.verifyMaximizetheMiddlePanel();
		driver.waitForPageToBeReady();
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
		driver.waitForPageToBeReady();
		docViewRedact.verifyMaximizetheMiddlePanel();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("PA has been impersonated as RMU");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.verifyMaximizetheMiddlePanel();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("PA has been impersonated as Rev");
		baseClass.stepInfo("Search the non audio documents and go to docview");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.verifyMaximizetheMiddlePanel();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * Author : Sakthivel date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51043 Verify user can maximize the middle panel of the doc view
	 * redirecting from Basic Search/Saved Search/Doc List/My Assignment/Manage
	 * Assignment/Manage Reviewers.
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 13)
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
	 * @author Sakthivel TestCase Id:51881 C3B: Verify that Action > Folder works
	 *         fine when all records in the reviewers batch are in mixed state but
	 *         records that are in Completed state are also present along with
	 *         records in an Uncompleted state in Mini DocList
	 * @throws InterruptedException
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 14)
	public void verifyFolderWorksFineInCompleteAndUnCompleteSateInMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51881");
		baseClass.stepInfo(
				"C3B: Verify that Action > Folder works fine when all records in the reviewers batch are in mixed state but records that are in Completed state are also present along with records in an Uncompleted state in Mini DocList");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		String folderName = "AnalyticalPanel" + Utility.dynamicNameAppender();

		// searching document for assignmnet creation
		baseClass.stepInfo("bascic contant search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Create assignment With allow user to save with complete option");
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assignmentName);
		miniDocList.configureMiniDocListToShowCompletedDocs();

		// To perform Folder MiniDocList
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 2; i++) {
			docView.getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(10);
		}
		baseClass.waitForElement(docView.getDocView_Mini_ActionButton());
		docView.getDocView_Mini_ActionButton().waitAndClick(5);
		docView.getDocView__ChildWindow_Mini_FolderAction().waitAndClick(10);
		docView.createNewFolderInAnalytical(folderName);
	}

	/**
	 * @author Sakthivel TestCase Id:51880 C2B: Verify that Action > Remove Code
	 *         Same works fine when all records in the reviewer's batch are in mixed
	 *         state but records that are in Completed state are also present along
	 *         with records in an Uncompleted state in Mini DocList
	 *
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 15)
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
		driver.Navigate().refresh();
		baseClass.handleAlert();
		baseClass.stepInfo("Mini doc list is not be configured to show completed documents");

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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 16)
	public void verifyEachPageDocViewedOnDocViewInThumbNailPanel() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51006");
		baseClass.stepInfo(
				"Verify user can see the thumbnail image of each page of the document being viewed on doc view page in thumbnail panel when redirecting from other than assignment page");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String pdfDocId = "ID00001283";
		String TIFFDocId = "ID00000945";
		String pptDocID = "ID00001144";
		String messageDocId = "ID00000290";

		baseClass.stepInfo("Logged in using RMU account");
		docViewRedact.verifyDifferentTypesOfDocsInThumbNailsPanel(pdfDocId, Input.xlsExcelDocId, TIFFDocId, pptDocID,
				messageDocId);
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using REV account");
		docViewRedact.verifyDifferentTypesOfDocsInThumbNailsPanel(pdfDocId, Input.xlsExcelDocId, TIFFDocId, pptDocID,
				messageDocId);
		loginPage.logout();

		// Login As PA.
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		docViewRedact.verifyDifferentTypesOfDocsInThumbNailsPanel(pdfDocId, Input.xlsExcelDocId, TIFFDocId, pptDocID,
				messageDocId);
	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify the Images tab retain on document navigation when 'Show Default Tab'
	 * toggle is OFF at assignment level.'RPMXCON-51925' Sprint : 9
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
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
		docViewPage.selectDocIdInMiniDocList(Input.nearDupeDocId);
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
		docViewPage.selectDocIdInMiniDocList(Input.nearDupeDocId);
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
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51008 Verify thumbnails in Doc View From assignments page
	 */

	@Test(description = "RPMXCON-51008", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 18)
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 19)
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
		String StampText = "Newcolor" + Utility.dynamicNameAppender();

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
		docView.getPersistantHitEyeIcon().waitAndClick(5);;
		docView.getPersistantHitEyeIcon().waitAndClick(5);;
		docView.getPersistantHitEyeIcon().waitAndClick(5);;
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
		docView.editCodingForm(filedText);
		driver.scrollPageToTop();
		docView.stampColourSelection(StampText, Input.stampColour);
		String getAttribute = docView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		docView.lastAppliedStamp(Input.stampColour);
		if (getAttribute.equals(filedText)) {
			baseClass.passedStep("Expected Message -StamplastIcon is clicked scuessfully..");
		} else {
			baseClass.failedStep("Expected Message - StamplastIcon is not clicked");
		}
		baseClass.waitForElement(docView.getCompleteDocBtn());
		docView.getCompleteDocBtn().waitAndClick(10);
		baseClass.stepInfo("Document completed successfully");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getHitPanelCount());
		driver.getPageSource();
		baseClass.waitTime(10); //Adding to handle loading issue
		baseClass.waitTillElemetToBeClickable(docView.getHitPanelCount());
		String afterComplete = docView.getHitPanelCount().WaitUntilPresent().getText();
		System.out.println(afterComplete);
		baseClass.stepInfo("persistent hits panel is not retain previously viewed hits");

		softAssert.assertNotEquals(beforeComplete, afterComplete);
		softAssert.assertAll();
		loginPage.logout();
		
	}
	/**
	 * Author :Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51977 Verify that DocView should be able to load the stitched TIFF
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 20)
	public void verifyDocViewStichedTIFFIsLoad() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51977");
		baseClass.stepInfo("Verify that DocView should be able to load the stitched TIFF");
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		DataSets dataset = new DataSets(driver);
		String datasets = "Only Mapped Sets";
		String AllSourceData = "Automation_AllSources";

		// login as PA
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PA");
		dataset.navigateToDataSetsPage();
		baseClass.stepInfo("Navigating to dataset page");

		// verify selected a automationallSourcesData and go to docview.
		dataset.SelectingDataSets(datasets);
		baseClass.passedStep("Dataset is successfully published");
		dataset.selectDataSetWithNameInDocView(AllSourceData);
		baseClass.passedStep(AllSourceData + "..data is selected and go to docviewpage");
		baseClass.waitTime(2);
		docView.verifyDisplaysTheDefaultPdfInDocView();

		// verify click on image tag.
		docView.clickOnImageTab();
		softassertion.assertTrue((docView.getImageTabAllSourcesDataOnDocView(AllSourceData).isDisplayed()));
		softassertion.assertAll();
		baseClass.waitForElement(docView.getImageTabAllSourcesDataOnDocView(AllSourceData));
		docView.getImageTabAllSourcesDataOnDocView(AllSourceData).waitAndClick(2);
		softassertion.assertTrue((docView.getAllSourcesDatasetImage().isDisplayed()));
		baseClass.stepInfo("AutomationAllsources dataset view in imagetab");
		if (docView.getAllSourcesDatasetImage().isDisplayed()) {
			baseClass.passedStep(AllSourceData + "...Multipage stitched TIFF is loaded on Doc View is successfully");
		} else {
			baseClass.failedStep("loaded failed");
		}
		softassertion.assertAll();
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
