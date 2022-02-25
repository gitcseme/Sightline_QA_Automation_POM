package testScriptsRegression;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

/*
 * Author : Raghuram A
 */

public class DocView_MiniDocList_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	MiniDocListPage miniDocListpage;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	ReusableDocViewPage reusableDocViewPage;
	SavedSearch savedSearch;
	KeywordPage keywordPage;
	CodingForm codingForm;

	String assignmentNew = "Assignment06" + Utility.dynamicNameAppender();
	String assignmentComplete = "Assignment" + Utility.dynamicNameAppender();

	@BeforeMethod(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		miniDocListpage = new MiniDocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		keywordPage = new KeywordPage(driver);
		codingForm = new CodingForm(driver);
		softAssertion = new SoftAssert();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
	}

	/**
	 * Author : Raghuram A date: NA Modified date: 8/24/21 Modified by: Raghuram A
	 * Description : Verify that when assignment saved with a manual sort order,
	 * then the optimized sort order should be presented with the default fields in
	 * mini doc list when navigating through any other assignment Test Case
	 * Id:RPMXCON-51804 Sprint : 1
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void miniDocListConfigurations() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51804");
		baseClass.stepInfo(
				"Verify that when assignment saved with a manual sort order, then the optimized sort order should be presented with the default fields in mini doc list when navigating through any other assignment");

		// Login as a Revierer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Verify User logged in as reviewer
		miniDocListpage.checkReviewerDashboardLandingPage();
		baseClass.stepInfo("Logged in as Reviewer Manager");
		System.out.println("Logged in as Reviewer Manager");
		miniDocListpage.verifyDocMiniListConfiguration();
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 8/15/21 NA Modified date: 8/24/21 Modified by:
	 * Raghuram A Description : Verify that when assignment saved with a manual sort
	 * order, then the optimized sort order should be presented with the default
	 * fields in mini doc list child window when navigating through any other
	 * assignment Id:RPMXCON-51805 Sprint : 1
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void miniDocListConfigurationsViaChildWindow() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51805");
		baseClass.stepInfo(
				"Verify that when assignment saved with a manual sort order, then the optimized sort order should be presented with the default fields in mini doc list child window when navigating through any other assignment");

		// Login as a Revierer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Verify User logged in as reviewer
		miniDocListpage.checkReviewerDashboardLandingPage();
		baseClass.stepInfo("Logged in as Reviewer Manager");
		System.out.println("Logged in as Reviewer Manager");
		miniDocListpage.verifyDocMiniListMethodChildWindow();
		baseClass.stepInfo("verify DocMiniList Method via ChildWindow");
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar Description :
	 * Verify that when mini doc list is configured then should refresh the
	 * persistent hits panel immediately Component:Docview_MiniDocList RPMXCON-51988
	 * Sprint01
	 * 
	 * @throws InterruptedException
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void PersistentHitsPanelRefreshedImmediately() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51988");
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// Click the eye icon to see the persistent hits
		docViewPage.getPersistentHit(Input.searchString1);
		baseClass.stepInfo("On click of the eye icon hits displayed related to the document and highlighted");
		docViewPage.configureMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.pa1userName + "'");
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar Description :
	 * Verify when loading is displayed in mini doc list after entering the document
	 * number then persistent hits should be displayed Component:Docview_MiniDocList
	 * RPMXCON-51865 Sprint01
	 * 
	 * @throws InterruptedException
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyLoadingDisplayedAfterEnteringDocumentNumber() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51865");

		String searchStringAudio = Input.audioSearchString1;
		String searchStringDocuments = Input.searchStringStar;
		String language = Input.language;

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		baseClass.stepInfo(
				"To verify whether the Loading is displayed when the mini doc list is scrolled down while Audio is playing");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		// Searching audio document
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(searchStringAudio, language);

		docViewPage.selectPureHits();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(searchStringDocuments);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// docview persistent highlighting and document number entering
		docViewPage.getPersistentHit(Input.searchString2);
		baseClass.stepInfo("Eye icon clicked to see the persistent hit panel");
		docViewPage.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
		loginPage.logout();
	}

	/**
	 * @Author Mohan Created date: 8/23/21 Description : Verify that when mini doc
	 *         list scrolled down and 'Loading' is displayed then mini doc list
	 *         should be loaded with the audio documents RPMXCON-51836 Modified
	 *         date: 8/31/21 Modified by: Raghuram
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyLoadingIsDisplayedWhenMiniDocListIsScrolled()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51836");
		int documentIdNum = Input.documentIdNum;

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		baseClass.stepInfo(
				"To verify whether the Loading is displayed when the mini doc list is scrolled down while Audio is playing");

		// Searching audio document
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioDocumentMetaData();

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// searching for document based on Id
		baseClass.stepInfo("Search the audio document based on its id");
		docViewPage.searchDocumentBasedOnId(documentIdNum);

		baseClass.stepInfo(
				"Verify whether the Loading is displayed when the mini doc list is scrolled down while Audio is playing");
		docViewPage.playAudioOnly();
		docViewPage.scrollUntilLoadingElementIsDisplayed();
		baseClass.passedStep("Loading displayed while scrolling the document based on ID");
		loginPage.logout();
	}

	/**
	 * @Author Mohan Created date: NA Modified date: 11/15/21 Modified by: Mohan
	 * @description Verify that "select all" check-box must no longer appear for any
	 *              user in the mini-docList 'RPMXCON-51872'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifySelectAllCheckBox() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51872");

		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass = new BaseClass(driver);

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		String searchString1 = Input.audioSearchString1;
		String searchString2 = Input.searchString1;
		String lanuguage = "North American English";
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		String text = Input.docName;

		// Audio search
		sessionSearch.audioSearch(searchString1, lanuguage);
		baseClass.stepInfo("Audio search is done Successfully");
		docViewPage.selectPureHit();

		// Content&MetaData Search
		sessionSearch.advancedNewContentSearch(searchString2);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Non Audio files are search and pureHits are added");

		// bulk assign and New Assignment creation
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Bulk Assign is made for new Assignment");
		assignmentsPage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo("New Assignment is created successfully");

		// edit assignment and add reviewers in the assignment
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("Reviewers are added to the assignment successfully");

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// View In docView
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// Select all check-box in parent window
		docViewPage.verifySelectAllCheckbox(text);
		baseClass.passedStep("Select All check-box is not visible in the parent window of MiniDoc successfully");

		// Select all check-box in child window
		docViewPage.selectMiniDocListChildWindow(text);
		baseClass.passedStep("Select All check-box is not visible in the child window of MiniDoc successfully");
		baseClass.stepInfo(
				"Select all check-box must no longer appear for any user in the mini doc list is successfully");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify documents with mini document list should be equals when
	 *              it will be pop out
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyingDocumentCountWithMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51608");
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		// Click GearIcon in DocViewPage
		docViewPage.documentCountShouldBeSame(Input.searchString2);

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify check mark icon should be displayed when document is
	 *              completed after selecting 'Code same as' action' from mini doc
	 *              list
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void checkMarkIconShouldDispalyed() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48714- DocView/MiniDocList Sprint 02");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);

		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentNew, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewerManager();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("User '" + Input.rmu1userName + "' impersonate as Reviewer");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentNew);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verifying check mark icon
		docViewPage.verifyingCheckMarkIconInMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify scrolling from mini doc list child window when document
	 *              is selected from mini doc list after scrolling down with more
	 *              number of documents
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyingScrollingFromMiniDocListChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51611");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Session search to Docview MiniList
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verifying check mark icon
		docViewPage.clickGearIconOpenMiniDocList();

		// switching to child window mini doclist
		String parentWindow = docViewPage.miniDocListChildWindowopening();

		// scroll document
		docViewPage.scrollingDocumentInMiniDocList();

		// control to parent window
		docViewPage.childWindowToParentWindowSwitching(parentWindow);

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.pa1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 25/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Selected document from history document is not loaded in
	 *              default view.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void selectDocumentFromHistoryDropDown() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51634");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Session search to Docview MiniList
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// Click the clock icon in minidoclist
		docViewPage.clickClockIconMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.pa1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 26/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that Principal document should not hide under the header
	 *              from mini doc list child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void principalDocumentShouldNotHideFromMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51641");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentComplete, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignmentComplete);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyPrincipalDocumentMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 26/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify the document count when mini document list pop out and
	 *              minimize and maximize
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyingDocumentCountWhenMinimizeAndMaximizeMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51609");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Session search to doc view Coding Form
		// Click GearIcon in DocViewPage
		docViewPage.minimizeAndMaximizeDocumentCountShouldBeSame(Input.searchString2);

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.pa1userName + "'");
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 8/17/21 NA Modified date: 8/28/21 Modified by:
	 * Raghuram A Description : Verify that when assignment saved with a manual sort
	 * order configuration, then the optimized sort order should be presented with
	 * the default fields from mini doc list
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyOptimizedSortOrder() throws InterruptedException {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-51801");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Verify User logged in as Reviewer Manager
		miniDocListpage.checkReviewerDashboardLandingPage();
		baseClass.stepInfo("Step 1 : Logged in as Reviewer Manager");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		miniDocListpage.verifyOptimizedSortOrderCase();
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 8/17/21 Modified date: 8/28/21 Modified by:
	 * Raghuram A Description :Verify that when assignment saved with a manual sort
	 * order configuration, then the optimized sort order should be presented with
	 * the default fields when navigating through same assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyOptimizedSortOrderSameAssignment() throws InterruptedException {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-51802");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Verify User logged in as Reviewer Manager
		miniDocListpage.checkReviewerDashboardLandingPage();
		baseClass.stepInfo("Step 1 : Logged in as Reviewer Manager");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		miniDocListpage.verifyOptimizedSortOrderCaseforSameAssignment();
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 8/18/21 Modified date:8/28/21 Modified by: Raghuram
	 * A Description : Verify that when assignment saved with a manual sort order
	 * configuration, then the optimized sort order should be presented with the
	 * default fields from mini doc list child window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyOptimizedSortOrderViaChildWindow() throws InterruptedException, Exception {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-51803");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Verify User logged in as Reviewer Manager
		miniDocListpage.checkReviewerDashboardLandingPage();
		baseClass.stepInfo("Logged in as Reviewer Manager");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		miniDocListpage.verifyOptimizedSortOrderViaChildwindow();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * Author : Raghuram A date: 8/18/21 Modified date:N/A Modified by: Raghuram A
	 * Description : Verify that in DocView: Assignment data should be displayed by
	 * original sorting then by DocID in the Mini Doc List child window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyOriginalSortOrderinChildWindow() throws InterruptedException, Exception {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-52187");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		String assignmentNameToCreate = miniDocListpage.assignmentCreationwithMetaDataSequence();
		baseClass.stepInfo("Assignment Created with MetaData Sequence");

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		miniDocListpage.verifyOriginalSortOrderInChildWindow(assignmentNameToCreate);
		baseClass.stepInfo("Verified Original SortOrder In ChildWindow");
		loginPage.logout();

	}

	/**
	 * Verify that on document navigation from mini doc list child window when hits
	 * panel is open then enable/disable should be retained Author : Raghuram A
	 * date: 8/18/21 Modified date:N/A Modified by: Raghuram A Description :
	 * RPMXCON-51752
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void enableOrdisableToretain() throws InterruptedException, Exception {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-51752");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.enableOrdisableToRetain();
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 8/25/21 Modified date:N/A Modified by: Raghuram A
	 * Description : Verify that when mini doclist reloads by adding additional docs
	 * then for completed documents checkmark with light blue highlighting should be
	 * displayed
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void checkMarkVerification() throws InterruptedException, Exception {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-51642");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		String assignmentNameToCreate = miniDocListpage.assignmentCreation();
		loginPage.logout();

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		miniDocListpage.checkmarkVerification(assignmentNameToCreate);
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description :Completes documents by performing 'code same as' action
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void performingCodeSameAsAction() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51606");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.uncompleteButtonShouldDisplay();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that when document is completed from child window by
	 *              completing document same as last then principal document being
	 *              viewing must always be the first document
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void principalDocumentVisibleAtFirst() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51605");
		baseClass.stepInfo("");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyingRightArrowInMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that when document is completed from child window after
	 *              scrolling down then principal document being viewing must always
	 *              be the first document
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyingPrincipalDocumentUsingLastDocBtn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51597");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.lastDocumentVerifyRightArrow();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message should be displayed when completed
	 *              documents are selected for code same action from mini doc list
	 *              child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyingWarningMessageForCodeSameAS() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51410");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyWarningMessage();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when document is completed from child window by
	 *              applying coding stamp then principal document being viewing must
	 *              always be the first document
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void verifyingPrincipalDocsFirstCodingStamp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51596");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.codingStampIconToVerifyPrincipalDocument(assgnColour, Input.stampColours, Input.stampColours);

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when document is completed from parent window then
	 *              principal document being viewing from mini doc list child window
	 *              must be the first
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyingBothParentAndChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51595");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyprincipalDocumentMiniDocList();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 9/07/21 Modified date:N/A Modified by: Mohan Description
	 * : Verify Configure Mini DocList modal window should be launched on click of
	 * the small gear icon from Mini DocList child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyConfigureMiniDocListChildWindow() {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass.stepInfo("Test case Id: RPMXCON-51338");
		String searchString = Input.searchString1;

		// Login as a Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		// Click Gear Icon and Configure MiniDoclist Popup
		miniDocListpage.popOutChildWindowAndConfigureMiniDocList();
		baseClass.stepInfo(
				"To Verify Configure Mini DocList modal window should be launched on click of the small gear icon from Mini DocList child window is done successfully");

		// logout PA
		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		// Click Gear Icon and Configure MiniDoclist Popup
		miniDocListpage.popOutChildWindowAndConfigureMiniDocList();
		baseClass.stepInfo(
				"To Verify Configure Mini DocList modal window should be launched on click of the small gear icon from Mini DocList child window is done successfully");

		// logout RMU
		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		// Click Gear Icon and Configure MiniDoclist Popup
		miniDocListpage.popOutChildWindowAndConfigureMiniDocList();
		baseClass.stepInfo(
				"To Verify Configure Mini DocList modal window should be launched on click of the small gear icon from Mini DocList child window is done successfully");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 9/07/21 Modified date:N/A Modified by: Mohan Description
	 * : Verify document selection when document exists in mini doc list is viewed
	 * from analytics panel
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyDocSelectionMiniDoclist() {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass.stepInfo("Test case Id: RPMXCON-51393");
		String searchString = Input.searchString1;
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();

		// Login as a RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Basic Search and bulk Assign is done successfully");

		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.toggleEnableAnalyticsPanel();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("Reviewers are added and the doc are distributed successfully");

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);

		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Assignment is selected by the Reviewer and Viewed in the Dcoview succesfully");

		docViewPage.selectAnalyticsPanelDocsAndViewDocs();
		baseClass.stepInfo("Doc are selected from Analytics Panel and viewed in the Default View successfully");

		miniDocListpage.verifyArrowMarkInMiniDocList();
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 9/07/21 Modified date:N/A Modified by: Mohan Description
	 * : Verify when document is selected from mini doc list child window document
	 * should be selected from mini doc list parent window
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyDocsFromMiniDoclistAndChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51390");
		String searchString = Input.searchString1;

		// Login as a RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc with non-audio files are selected and viewed in the DocView successfully");

		// Edit Coding form and click Save Button
		reusableDocViewPage.editingCodingFormWithSaveButton();
		baseClass.stepInfo("Coding form is filled and Save Button is clicked successfully");

		// Select the Gear icon and an doc from the child window
		miniDocListpage.selectDocFromMiniDocListChildWindow();
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 9/08/21 Modified date:N/A Modified by: N/A
	 * Description : Verify on selecting document from history drop down, icon to
	 * indicate that document is selected should not be of completed document
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void documentHistoryDD() throws InterruptedException, Exception {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Test case Id: RPMXCON-51389");

		// Login as a Review Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		String assignmentNameToCreate = miniDocListpage.assignmentCreation();
		loginPage.logout();

		// Login as a Review Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// Assignment Selection
		docViewPage.selectAssignmentfromDashborad(assignmentNameToCreate);
		baseClass.stepInfo("Selected Assignment name : " + assignmentNameToCreate);
		miniDocListpage.VerifyArrow();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list and
	 *              Save/Complete clicked from coding form parent window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyingCompleteTickMarkParentWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51366");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.saveAndCompleteButtonPerformCodeSameAs();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * Author : Raghuram A date: 9/13/21 NA Modified date: N/A Modified by: Raghuram
	 * A Description : Configure Mini DocList modal window should be launched on
	 * click of the small gear icon in the Mini DocList panel in context of an
	 * assignment Id:RPMXCON-51337 Sprint : 02
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void configureMiniDocListmodalWindow() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51337");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo(
				"Configure Mini DocList modal window should be launched on click of the small gear icon in the Mini DocList panel in context of an assignment");

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);

		baseClass.stepInfo("Step 1 : Route for Doc View mini list");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 1 : Click Gear icon");
		miniDocListpage.clickingGearIcon();

		baseClass.stepInfo("Step 2 : Verify Mini docList popup");
		// Mini doc list tab
		miniDocListpage.miniDoclistTabHeader();

		// Close minidoclist popup
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getMiniDocListcloseButton());
		miniDocListpage.getMiniDocListcloseButton().Click();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 06/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list child
	 *              window and Save/Complete clicked from coding form child window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyingCompleteTickMarkChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51369");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the context of an assignment after selecting the assignment");

		docViewPage.verifyCheckMarkFromMiniDocListChildWindow();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list child
	 *              window and Save/Complete clicked from coding form parent window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifyCodeSameAsMiniDocListChildWindowCodingParentWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51368");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyUncompletebuttonFromParentWindow();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list and
	 *              Save/Complete clicked from coding form child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyUncomplteBtnChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51367");
		String assignment = "Assignment07" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.miniDocListCodeSameAsUncomplteBtnChildWindow();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify user is allowed to select up to 4 webfields from a
	 *              preselected list to display in the panel of mini doclist in the
	 *              optimized mode [RPMXCON-15074, RPMXCON-13255]
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyAscendingOrderInMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50883");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		String assignmentNameToChoose = miniDocListpage.assignmentCreationWithOptimizedSortForDisToRMU();
		System.out.println("assignmentNameToChoose");
		baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignmentNameToChoose);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		miniDocListpage.sortingVerifyAfterSelectedWebFields();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 12/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify user is allowed to select 4 webfields from a
	 *              preselected list to display in the panel of mini doc list in the
	 *              optimized mode
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyOptimizedModeInMinidocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50884");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		String assignmentNameToChoose = miniDocListpage.assignmentCreationWithOptimizedSortForDisToRMU();
		System.out.println("assignmentNameToChoose");
		baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignmentNameToChoose);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		miniDocListpage.optimizedSortToSelcetWebFields();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 12/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify when user selects more than 4 webfields from a
	 *              preselected list to display in the panel of mini doc list in the
	 *              optimized mode
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void shouldNotSelectMoreThanFourWebFields() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50885");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		String assignmentNameToChoose = miniDocListpage.assignmentCreationWithOptimizedSortForDisToRMU();
		System.out.println("assignmentNameToChoose");
		baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignmentNameToChoose);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		miniDocListpage.moreThanFourWebFieldNotDraggable();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 13/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify document should be complete on selecting 'Remove code
	 *              same' action
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void documentShouldCompleteWhenRemoveCodeSameAs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51233");
		String assgnColour = "AssignColour" + Utility.dynamicNameAppender();
		String Asssignment = "AssignmentRemove" + Utility.dynamicNameAppender();
		System.out.println(Asssignment);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		miniDocListpage.removeCodeSameAsAndCompleteDocs(assgnColour, Input.stampSelection);
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message should be displayed when completed
	 *              documents are selected for code same action from mini doc list
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void warningMsgForCompletedCodeSameAs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51409");
		String Asssignment = "AssignmentWarningMsg" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		miniDocListpage.warningMsgForCompletedDocsCodeSameAs();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify document should not be selected in mini doc list when
	 *              document viewed from history does not exists in mini doc list
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyDocsShouldNotSelectFromMiniDocListInHistrotyDropDown() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51394");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.docsShouldNotOfMiniDocList(0);
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify on click of the document navigation focus should set in
	 *              mini doc list child window when prior document is viewed from
	 *              history drop down of mini doc list child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void childWindowHistoryDropDownFocus() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51392");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.focusOnChildWindowHistoryDrpDw();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify on click of the document navigation document should be
	 *              selected in mini doc list child window when prior document is
	 *              viewed from history drop down of mini doc list child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void historyDropDownMiniDicListChild() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51391");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.selectDocFromHistoryDropDown();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document is selected to view from history drop
	 *              down selection when hits panel is open then enable/disable
	 *              should be retained
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyHitPanel() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51751");
		String hitTerms = "Than" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

//		Go to docview via advanced search
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.getPersistentHit(hitTerms);
		docViewPage.verifyPersistentHitPanel(hitTerms);

		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.getDeleteButton(hitTerms).waitAndClick(5);
		keywordPage.getYesButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify documents folder action is done successfully from
	 *              mini doclist
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void verifyFolderActionForMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51734");
		String folderName = "AFolder" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.performFolderAction(folderName, 1);
//		verify code same as icon should not present in minidoclist for folder performed
		boolean flag = docViewPage.geDocView_MiniList_CodeSameAsIcon().isElementAvailable(5);
		Assert.assertFalse(flag);
		baseClass.passedStep("Code same as icon not displayed in minidoclist after performing folder action");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when document is completed from child window then
	 *              principal document being viewing must always be the first
	 *              document
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void principalDocsAlwaysBeFirst() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51569");
		baseClass.stepInfo("Verify that when document is completed from child window then "
				+ "principal document being viewing must always be the first document");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.rev1userName + "'");

		// Selecting the assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyPrincipaDocumentBeFirst();

		// logout
		driver.waitForPageToBeReady();
		loginPage.logout();

//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		assignmentPage.deleteAssgnmntUsingPagination(Asssignment);
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify check mark icon should be displayed in the mini doc
	 *              list for completed document after applying coding stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void verifyCheckMarkIcon() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51042");
		baseClass.stepInfo("Verify check mark icon should be displayed in "
				+ "the mini doc list for completed document after applying coding stamp");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String stampText = "Text" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewerManager();

		baseClass.impersonateRMUtoReviewer();

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyCheckMarkIcon(stampText, Input.stampSelection);
		reusableDocViewPage.deleteStampColour(Input.stampSelection);
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message is prompted when navigates to other
	 *              page without completing or saving 'Code same as this' action
	 *              from Dov View > mini doc list redirected from Edit Assignment >
	 *              Manage Reviewers
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void verifyNavigatesToOtherPageUsingYesNoBtn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50949");
		baseClass.stepInfo("Verify warning message is prompted when navigates to "
				+ "other page without completing or saving 'Code same as this' "
				+ "action from Dov View > mini doc list redirected from Edit Assignment > Manage Reviewers");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyOtherPageNavUsingYesAndNoBtn();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message is prompted to the user when user
	 *              clicks browser refresh button without completing or saving from
	 *              mini doc list panel
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 47)
	public void verifyNavigateToRefreshInReload() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50920");
		baseClass.stepInfo("Verify warning message is prompted to the user when "
				+ "user clicks browser refresh button without completing or saving from mini doc list panel");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		driver.getWebDriver().navigate().back();
		assignmentPage.manageAssignmentToDocViewAsRmu(Asssignment);
		// verify from manage assignment
		docViewPage.verifyOtherPageNavUsingReloadBtn();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyOtherPageNavUsingReloadBtn();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify that sorting sequence of documents should not save
	 *              for all users of assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifySortSequenceForOtherUser() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50898");
		baseClass.stepInfo(
				"To verify that sorting sequence of documents should not " + "save for all users of assignment");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToTwoReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		String userOne = miniDocListpage.currentSortOrderShouldBeDisplayed();
		System.out.println(userOne);
		baseClass.stepInfo("Sort sequence done for user one in manual mode");
		driver.waitForPageToBeReady();
		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev2userName, Input.rev2password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev2userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verifying sort sequence in second user");
		String userTwo = miniDocListpage.againDocviewToViewNewChanges();
		System.out.println(userTwo);
		softAssertion.assertNotEquals(userOne, userTwo);
		baseClass.passedStep("Sort Sequence not displayed for second user for same assignment");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify mini doc list form doc view page when user redirects
	 *              from Basic search and Save Search.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 49)
	public void savedSearchAndBasicToDocView() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50896");
		baseClass.stepInfo("To verify mini doc list form doc view page when "
				+ "user redirects from Basic search and Save Search.");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// Saved search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);

//		sessionSearch=new SessionSearch(driver);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User navigated to docview page from basic search page");

		docViewPage.verifyMiniDocListPanel();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 16/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that updated metadata should displayed on Mini doc list
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 50)
	public void verifyUpadteMetaDataValueOnMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51143");
		baseClass.stepInfo("Verify that updated metadata should displayed on Mini doc list");
		String codingForms = "Cf" + Utility.dynamicNameAppender();
		String text = "Text" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Creating New Coding Form
		String textMeta = codingForm.createWithOneMetaData(codingForms);
		codingForm.assignCodingFormToSG(codingForms);

		// search to docview panel
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify metadata fields in minidoclist
		docViewPage.metaDataTextInMiniDocList(textMeta, text);
		miniDocListpage.metaDataValueToBeDisplayed(textMeta, text);
		codingForm = new CodingForm(driver);
		codingForm.assignCodingFormToSG(Input.codeFormName);
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify warning message is displayed when user do not select
	 *              any web fields from preselected list to display in the panel of
	 *              mini doc list in the manual mode [RPMXCON-50879]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 51)
	public void verifyWarningMsgForManualMode() throws InterruptedException {
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Test case Id: RPMXCON-50879 DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"To verify warning message is displayed when user do not select any web fields from preselected list to display in the panel of mini doc list in the manual mode");

		// search to Assignment creation And distribute to Rev
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codeFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// navigate to docview and Perform Manual sort
		driver.getWebDriver().navigate().back();
		assignmentPage.manageAssignmentToDocViewAsRmu(Asssignment);
		miniDocListpage.clickManualSortButton();

		// Remove All the fields and save
		miniDocListpage.removingSelectedWebFieldInConfigureList();
		baseClass.passedStep("Removed All the fields");
		baseClass.waitForElement(miniDocListpage.getMiniDocListConfirmationButton("Save"));
		miniDocListpage.getMiniDocListConfirmationButton("Save").Click();

		// verify Error Msg
		baseClass.VerifyErrorMessage("Please select columns to display");

		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		miniDocListpage.clickManualSortButton();

		// Remove All the fields and save
		miniDocListpage.removingSelectedWebFieldInConfigureList();
		baseClass.passedStep("Removed All the fields");
		baseClass.waitForElement(miniDocListpage.getMiniDocListConfirmationButton("Save"));
		miniDocListpage.getMiniDocListConfirmationButton("Save").Click();

		// verify Error Msg
		baseClass.VerifyErrorMessage("Please select columns to display");

		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// delete Created Assignment
		assignmentPage.deleteAssgnmntUsingPagination(Asssignment);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify error message is displayed when user do not select
	 *              any web fields from preselected list to display in the panel of
	 *              mini doc list in the optimizedmode[RPMXCON-50886]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 52)
	public void verifyWarningMsgForOptimizedMode() throws InterruptedException {
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Test case Id: RPMXCON-50886 DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"To verify error message is displayed when user do not select any web fields from preselected list to display in the panel of mini doc list in the optimizedmode");

		// search to Assignment creation And distribute to Rev
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codeFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// navigate to docview and Perform Manual sort
		driver.getWebDriver().navigate().back();
		assignmentPage.manageAssignmentToDocViewAsRmu(Asssignment);
		miniDocListpage.configureMiniDocListPopupOpen();

		// Remove All the fields and save
		miniDocListpage.removingSelectedWebFieldInConfigureList();
		baseClass.passedStep("Removed All the fields");
		baseClass.waitForElement(miniDocListpage.getMiniDocListConfirmationButton("Save"));
		miniDocListpage.getMiniDocListConfirmationButton("Save").Click();

		// verify Error Msg
		baseClass.VerifyErrorMessage("Please select columns to display");
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		miniDocListpage.configureMiniDocListPopupOpen();

		// Remove All the fields and save
		miniDocListpage.removingSelectedWebFieldInConfigureList();
		baseClass.passedStep("Removed All the fields");
		baseClass.waitForElement(miniDocListpage.getMiniDocListConfirmationButton("Save"));
		miniDocListpage.getMiniDocListConfirmationButton("Save").Click();

		// verify Error Msg
		baseClass.VerifyErrorMessage("Please select columns to display");

		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// delete Created Assignment
		assignmentPage.deleteAssgnmntUsingPagination(Asssignment);
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A Date: 1/3/21 Modified date:N/A Modified by: N/A
	 * @Description : To verify that warning message is displayed if user select
	 *              action without selecting any document in Mini doc list.
	 *              [RPMXCON-50899]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 53)
	public void verifyWarningMsgWhenNoDocSelected() throws InterruptedException {

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);

		baseClass.stepInfo("Test case Id: RPMXCON-50899 DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"To verify that warning message is displayed if user select action without selecting any document in Mini doc list.");

		// basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// click folder in action DD.
		docViewPage.getDocView_Mini_ActionButton().waitAndClick(5);
		docViewPage.getDocView_Mini_FolderAction().waitAndClick(5);

		// verify warning msg
		baseClass.VerifyWarningMessage("Please select at least one document");
		loginPage.logout();

	}

	/**
	 * @author Raghuram A Date: 1/3/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate sharing modified search/groups to Security
	 *         Group which are already shared-RPMXCON-48711 Sprint 09
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 54)
	public void validateCheckMarkIconForComplete() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48711 - DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"Verify check mark icon should be displayed in the mini doc list for completed document after clicking the 'Code same as last doc'");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		// selecting the assignment
		driver.waitForPageToBeReady();
		miniDocListpage.getDashBoardLink().waitAndClick(2);
		driver.waitForPageToBeReady();
		miniDocListpage.chooseAnAssignmentFromDashBoard(assignName);

		// validate checkmark icon and code same as last button
		docViewPage.validateCodeSameAsLatAction(comment);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validate checkmark icon and code same as last button
		docViewPage.validateCodeSameAsLatAction(comment);

		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// delete Created Assignment
		assignmentPage.deleteAssgnmntUsingPagination(assignName);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify warning message is prompted when navigates to other
	 *              page without completing or saving 'Code same as this' action
	 *              from Dov View > mini doc list [RPMXCON-50916]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 55)
	public void verifyNavigationPopup() throws InterruptedException {
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		List<String> docIDlist = new ArrayList<>();
		int docLimit = 2;

		baseClass.stepInfo("Test case Id: RPMXCON-50916 DocView/MiniDocList Sprint 09");
		baseClass.stepInfo(
				"Verify warning message is prompted when navigates to other page without completing or saving 'Code same as this' action from Dov View > mini doc list");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		// selecting the assignment
		driver.waitForPageToBeReady();
		miniDocListpage.getDashBoardLink().waitAndClick(2);
		driver.waitForPageToBeReady();
		miniDocListpage.chooseAnAssignmentFromDashBoard(assignName);

		// verify navigation popup
		miniDocListpage.actionOnDocsFromHistoryDropDown(docLimit, 0, docIDlist, false, "", false, false, true, null);
		docViewPage.clickCodeSameAs();
		sessionSearch.getSearchBtn().waitAndClick(10);
		docViewPage.verifyConfirmNavigationPopup(false, true);
		sessionSearch.getSearchBtn().waitAndClick(10);
		docViewPage.verifyConfirmNavigationPopup(true, true);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verify navigation popup
		miniDocListpage.actionOnDocsFromHistoryDropDown(docLimit, 0, docIDlist, false, "", false, false, true, null);
		docViewPage.clickCodeSameAs();
		sessionSearch.getSearchBtn().waitAndClick(10);
		docViewPage.verifyConfirmNavigationPopup(false, true);
		sessionSearch.getSearchBtn().waitAndClick(10);
		docViewPage.verifyConfirmNavigationPopup(true, true);

		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete Created Assignment
		assignmentPage.deleteAssgnmntUsingPagination(assignName);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi A Date: 1/10/21 Modified date:N/A Modified by: N/A
	 *         Description :To verify that user can view the 'Mini Doc List' on Doc
	 *         View, if preferences set as 'Enabled' from the assignment.
	 *         -RPMXCON-50851 Sprint 10
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void verifyMiniDocListDisplay() throws InterruptedException {
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50851 - DocView/MiniDocList Sprint 10");
		baseClass.stepInfo("To verify that user can view the 'Mini Doc List' on Doc View, if"
				+ " preferences set as 'Enabled' from the assignment.");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.FinalizeAssignmentAfterBulkAssign();
		assignmentPage.createAssignment_fromAssignUnassignPopup(assignName, Input.codeFormName);

		// checking for Display Mini DocList toggle disabled state and enabling if
		// required
		assignmentPage.toggleEnable_Disable(assignmentPage.getDispalyMinidocListToggle(), true,
				"'Display Mini DocList'");
		baseClass.stepInfo("Created a assignment " + assignName);

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignName);
		assignmentPage.Checkclickedstatus(assignName);
		assignmentPage.assgnViewInAllDocView();
		driver.waitForPageToBeReady();
		if (miniDocListpage.miniDocListDisplay().isElementAvailable(2)) {
			baseClass.passedStep(
					"Mini doc list displayed if 'Display Mini DocList' toggle under preferences set as 'Enabled' from the assignment.");

		} else {
			baseClass.failedStep("Mini doc list not displayed if toggle is enabled");
		}
		assignmentPage.deleteAssgnmntUsingPagination(assignName);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi A Date: 1/10/21 Modified date:N/A Modified by: N/A
	 *         Description :To verify that user can not view the 'Display Mini
	 *         DocList' tab on Doc View, if preferences set as 'Disabled' from the
	 *         assignment. -RPMXCON-50852 Sprint 10
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void verifyMiniDocListDisplay_Disabled() throws InterruptedException {
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50852 - DocView/MiniDocList Sprint 10");
		baseClass.stepInfo("To verify that user can not view the 'Display Mini DocList' tab on Doc View, "
				+ "if preferences set as 'Disabled' from the assignment.");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document and assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.FinalizeAssignmentAfterBulkAssign();
		assignmentPage.createAssignment_fromAssignUnassignPopup(assignName, Input.codeFormName);

		// checking for Display Mini DocList toggle enabled state and disabling if
		// required as per test case.
		assignmentPage.toggleEnable_Disable(assignmentPage.getDispalyMinidocListToggle(), false,
				"'Display Mini DocList'");
		baseClass.stepInfo("Created a assignment " + assignName);

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignName);
		assignmentPage.Checkclickedstatus(assignName);
		assignmentPage.assgnViewInAllDocView();
		driver.waitForPageToBeReady();
		if (miniDocListpage.miniDocListDisplay().isElementAvailable(2)) {
			baseClass.passedStep("Mini doc list not displayed if 'Display Mini DocList' toggle under preferences "
					+ "set as 'Disabled' from the assignment.");

		} else {
			baseClass.failedStep("Mini doc list displayed if toggle is disabled which is not expected");
		}
		assignmentPage.deleteAssgnmntUsingPagination(assignName);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi A Date: 1/10/21 Modified date:N/A Modified by: N/A
	 *         Description :RPMXCON-51244 Sprint 10
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void VerifyRemoveCodeSameDisplay_ForPA() throws InterruptedException {
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51244 - DocView/MiniDocList Sprint 10");
		baseClass.stepInfo("Verify for Project Admin 'Remove Code Same' should not be displayed in "
				+ "mini doc list child window");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Admin " + Input.pa1userName + "'");

		// searching document and view in doc view
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to doc view page in context of search.");
		driver.waitForPageToBeReady();

		// switching to child window-Mini doc list
		reusableDocViewPage.clickGearIconOpenMiniDocList();
		String miniDocListPrent = reusableDocViewPage.switchTochildWindow();
		baseClass.stepInfo("Mini DocList child window opened.");
		baseClass.waitForElement(reusableDocViewPage.getDocView_Mini_ActionButton());
		reusableDocViewPage.getDocView_Mini_ActionButton().waitAndClick(5);

		// validation for the test case
		boolean status = miniDocListpage.getDocView__ChildWindow_Mini_RemoveCodeSameAs().Displayed();
		// switching from child to parent window.
		reusableDocViewPage.childWindowToParentWindowSwitching(miniDocListPrent);
		if (status) {
			baseClass.failedStep("'Remove Code Same' is   displayed in mini doc list child window for"
					+ " Project Admin user which is not expected.");
		} else {
			baseClass.passedStep(
					"'Remove Code Same' is not  displayed in mini doc list child window for Project Admin user. ");
		}
		loginPage.logout();
	}

	/**
	 * @Author : Jayanthi
	 * @Description : To verify user is allowed to select up to 4 webfields from a
	 *              preselected list to display in the panel of mini doclist in the
	 *              manual mode[RPMXCON-15074, RPMXCON-13255]
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 59)
	public void DocViewToSelect4WebFields() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48702");
		baseClass.stepInfo("To verify user is allowed to select up to 4 webfields from a preselected "
				+ "list to display in the panel of mini doclist in the manual mode[RPMXCON-15074, RPMXCON-13255]");
		String assignName = "Assgn" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as RMU User");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.addReviewerAndDistributeDocs();
		baseClass.stepInfo("Created a assignment " + assignName);

		// Navigating to Doc view Page in context of manage assignment
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignName);
		assignmentPage.Checkclickedstatus(assignName);
		assignmentPage.assgnViewInAllDocView();
		driver.waitForPageToBeReady();

		// Validation part
		miniDocListpage.fromSavedSearchToSelectWebField();
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer " + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		// Validation part
		miniDocListpage.fromSavedSearchToSelectWebField();
		loginPage.logout();
	}

	@Test(alwaysRun = true, groups = { "regression" }, priority = 60)
	public void verifyDocIDSortOrder() throws Exception {

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		String sortBy = "DocID";

		baseClass.stepInfo("Test case Id: RPMXCON-48803");
		baseClass.stepInfo(
				"Verify that in DocView: Assignment data Should be Dispalyed by Original sorting then by DocID in the Mini Doc List");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// assignment Creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		assignmentPage.Assgnwithdocumentsequence(sortBy, Input.sortType);
		assignmentPage.assignmentDistributingToReviewerManager();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate from RMU to Rev
		baseClass.impersonateRMUtoReviewer();

		// Selecting the assignment and validating the sort order in mini doc list
		miniDocListpage.verifyOriginalSortOrderInChildWindow(assignmentName);

		loginPage.logout();
	}

	@Test(alwaysRun = true, groups = { "regression" }, priority = 61)
	public void verifyCompletedIcon_PA() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51026");
		baseClass.stepInfo("To verify that Project admin cannot view the completed icon on mini doc list");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Admin " + Input.pa1userName + "'");

		// searching document and view in doc view
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to doc view page in context of search.");
		driver.waitForPageToBeReady();
		if (reusableDocViewPage.getverifyCodeSameAsLast().isDisplayed()) {
			baseClass.failedStep("Project admin viewed Completed icon in mini doc list.");
		} else {
			baseClass.passedStep("Project admin is not able to view the completed icon on mini doc list.");
		}
		loginPage.logout();
	}

	/**
	 * @author Sakthivel RPMXCON-51644 date:10/01/2022 Modified date:NA
	 * @Description :Verify that when mini doclist reloads by adding additional docs
	 *              then for completed documents checkmark with light blue
	 *              highlighting should be displayed when show completed docs is ON
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 61)
	public void verifyDisplayedCompletedMiniDocListDocsCheckmarkandlightBlue()
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51644");
		baseClass.stepInfo("Verify on click of 'Save' button coding form should be validated when coding form"
				+ "customized for all objects along with all condition and Check Item");
		String assignName = "CFAssignment" + Utility.dynamicNameAppender();
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		miniDocListpage = new MiniDocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// Logout as Review Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer manager '" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// MiniDocList toggle is on
		miniDocListpage.verifyDocToggleisOn();
		miniDocListpage.verifyCheckMarkIconandDocHighlight();
		miniDocListpage.verifyAfterLoadingCheckmarkAndClr();

		// logout as reviewer
		loginPage.logout();
		baseClass.stepInfo("Successfully logout as Reviewer'" + Input.rev1userName + "'");
		baseClass.passedStep("Verify on click of 'Save' button coding form should be validated when coding form+"
				+ "customized for all objects along with all condition and Check Item");
	}

	/**
	 * @author Sakthivel RPMXCON-51645 date:10/01/2022 Modified date:NA
	 * @Description :Verify that when mini doclist reloads by adding additional docs
	 *              then for completed documents checkmark with light blue
	 *              highlighting should be displayed when show completed docs is ON.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 62)
	public void verifyDisplayedMiniDocListDocsCheckmarkandlightBlueInChildWindow()
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51645");
		baseClass.stepInfo(
				"Verify that when mini doclist child window reloads by adding additional docs then for completed documents"
						+ " checkmark with light blue highlighting should be displayed when Show Completed Docs is ON");
		String assignName = "CFAssignment" + Utility.dynamicNameAppender();
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		miniDocListpage = new MiniDocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Review manager '" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Launching Mini doc list Child WIndow
		miniDocListpage.launchingMindoclistChildWindow();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getDocView_MiniDoclist_GearIcon());
		miniDocListpage.getDocView_MiniDoclist_GearIcon().waitAndClick(10);
		docViewPage.switchToNewWindow(1);
		baseClass.waitForElement(miniDocListpage.getShowCompleteDocsButton());
		miniDocListpage.getShowCompleteDocsButton().waitAndClick(10);
		baseClass.waitForElement(miniDocListpage.getMiniDocListConfirmationButton("Save"));
		miniDocListpage.getMiniDocListConfirmationButton("Save").Click();
		baseClass.stepInfo("Doc Toggle is on");
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Handled Alert");
		driver.getWebDriver().navigate().refresh();
		try {
			driver.getWebDriver().switchTo().alert().accept();
		} catch (Exception e) {
			System.out.println("No Alerts");
		}

		// Launching Mini doc list Child WIndow
		miniDocListpage.launchingMindoclistChildWindow();
		miniDocListpage.verifyCheckMarkIconandDocHighlightInChildWindow();
		miniDocListpage.verifyAfterLoadingCheckmarkAndClrInChildWindow();
		driver.waitForPageToBeReady();
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);

		// logout as reviewer
		loginPage.logout();
		baseClass.stepInfo("Successfully logout as Reviewer'" + Input.rev1userName + "'");
		baseClass.passedStep(
				"Verify that when mini doclist child window reloads by adding additional docs then for completed documents"
						+ " checkmark with light blue highlighting should be displayed when Show Completed Docs is ON");

	}

	@Test(alwaysRun = true, groups = { "regression" }, priority = 62)
	public void verifyUserAbleToConfigMiniDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50889");
		baseClass.stepInfo("To Verify User Shall able to Configure the Mini DocList to Show Completed Documents");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// creating the Assignment and distributing to users
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();

		baseClass.impersonateRMUtoReviewer();
		// validation for RMU user
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		// Collecting selected Fields
		List<String> selectedFields = docViewPage.CollectingSelectedFiledsFromConfigMiniDocList();
		driver.Navigate().refresh();
		if (reusableDocViewPage.getverifyCodeSameAsLast().isDisplayed()) {
			baseClass.passedStep(
					"Viewed Completed documents in mini doc list after configuring the mini doc lsit by enabling the 'show completed docs' Toggle.");
		} else {
			baseClass.failedStep("Not able to view the completed docs after configuring the mini doc list.");
		}
		// Collecting MiniDocList Header
		List<String> miniDocListHeaders = docViewPage.availableListofElements(docViewPage.getMiniDocListHeaderValue());

		// Comparing selected Fields and MiniDocList Header
		docViewPage.ComparingSelectedFieldsWithMiniDocListHeaderValue(selectedFields, miniDocListHeaders);

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Loggedin As : " + Input.rev1userName);
		// Validation part for Reviwer user
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		reusableDocViewPage.editTextBoxInCodingFormWithCompleteButton("Completing and editing");
		driver.Navigate().refresh();
		// Collecting selected Fields
		List<String> selectedFields_1 = docViewPage.CollectingSelectedFiledsFromConfigMiniDocList();
		if (reusableDocViewPage.getverifyCodeSameAsLast().isDisplayed()) {
			baseClass.passedStep(
					"Viewed Completed documents in mini doc list after configuring the mini doc lsit by enabling the 'show completed docs' Toggle.");
		} else {
			baseClass.failedStep("Not able to view the completed docs after configuring the mini doc list");
		}
		// Collecting MiniDocList Header
		List<String> miniDocListHeaders_1 = docViewPage
				.availableListofElements(docViewPage.getMiniDocListHeaderValue());

		// Comparing selected Fields and MiniDocList Header
		docViewPage.ComparingSelectedFieldsWithMiniDocListHeaderValue(selectedFields_1, miniDocListHeaders_1);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 63)
	public void verifyCheckMarkIconInCompletedDcoumentsInMiniDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51041");
		baseClass.stepInfo(
				"Verify check mark icon should be Dispalyed in the mini doc list for Completed document by clicking the Complete button");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// creating And Distributing the Assignment
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate as Reviewer
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// Completing the Document in MiniDocList-Validation part for RMU user
		driver.waitForPageToBeReady();
		docViewPage.CompleteTheDocumentInMiniDocList(3);
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Loggedin As : " + Input.rev1userName);
		// Validation part for Reviwer user
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		// Completing the Document in MiniDocList
		driver.waitForPageToBeReady();
		docViewPage.CompleteTheDocumentInMiniDocList(3);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 64)
	public void verifyingWebfieldsInOptimizedSortOrderAndManualSortOrder() throws Exception {

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		boolean SetDocumentSorting = true;

		baseClass.stepInfo("Test case Id: RPMXCON-48782");
		baseClass.stepInfo(
				"Verify that when stwitch from optimized to Custom, the displayed fields after adding/removing few webfields set in the optimized sort are displayed for custom sort");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// assignment Craeation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		assignmentPage.add3ReviewerAndDistribute();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate from RMU to Rev
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Loggedin As RMU and Impersonated as Reviewer ");
		// Selecting the assignment
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// performing Add and Remove action on Selected Web Fields
		driver.waitForPageToBeReady();
		miniDocListpage.configureMiniDocListPopupOpen();
		miniDocListpage.methodforPickColumndisplay();

		miniDocListpage.saveConfigureMiniDocList();

		// verifying the Selected Web Fields
		driver.waitForPageToBeReady();
		miniDocListpage.configureMiniDocListPopupOpen();
		miniDocListpage.comparingWebfieldsInOptimizedSortOrderAndManualSortOrder(SetDocumentSorting);

		loginPage.logout();

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Loggedin As : " + Input.pa1FullName);

		// impersonate from PA to Rev
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Loggedin As PA and Impersonated as Reviewer ");
		// Selecting the assignment
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// performing Add and Remove action on Selected Web Fields
		driver.waitForPageToBeReady();
		miniDocListpage.configureMiniDocListPopupOpen();
		miniDocListpage.methodforPickColumndisplay();

		miniDocListpage.saveConfigureMiniDocList();

		// verifying the Selected Web Fields
		driver.waitForPageToBeReady();
		miniDocListpage.configureMiniDocListPopupOpen();
		miniDocListpage.comparingWebfieldsInOptimizedSortOrderAndManualSortOrder(SetDocumentSorting);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 65)
	public void verifyConfigureManualMode() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-59582");
		baseClass.stepInfo("Verify the context on navigating to doc view from manage assignment after configuring "
				+ "the mini doc list");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// creating And Distributing the Assignment
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		;
		sessionSearch.basicContentSearch(Input.testData1);
		String expectedCount = sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// view in doc view in context of manage assignment

		// Configuring mini doc list in configure pop up-manual mode
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		assignmentPage.Checkclickedstatus(assignmentName);
		assignmentPage.assgnViewInAllDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		miniDocListpage.verifyDefaultWebfieldsInManualSortOrder();
		// performing Add and Remove action on Selected Web Fields and validating the
		// same.
		miniDocListpage.verifyManualModeSortingConfigure(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// select the Assignment from Manage Assignment
		baseClass.stepInfo("Selecting the assignment from Manage Assignments Page. ");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		assignmentPage.Checkclickedstatus(assignmentName);
		assignmentPage.assgnViewInAllDocView();
		DocViewPage dc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		String ActualCount = dc.verifyDocCountDisplayed_DocView();
		System.out.println(ActualCount);
		String codingFormName = miniDocListpage.getDocView_CodingFromName().getText();
		if (miniDocListpage.getDocumentCompleteButton().isDisplayed()) {
			baseClass.failedStep("RMU user viewed Completed icon in mini doc list in context of manage assignment.");
		} else {
			softAssertion.assertEquals(codingFormName, Input.codeFormName);
			softAssertion.assertEquals(ActualCount, expectedCount);
			softAssertion.assertAll();
			baseClass.passedStep(
					"RMU user not viewed Completed icon and assigned coding form is viewed in mini doc list in context of manage assignment.");
		}
		baseClass.passedStep("Document Count displayed in doc view  is same as assigned to the assignment.");
		miniDocListpage.verifyDefaultWebfieldsInManualSortOrder();
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 66)
	public void verifyConfigureManualMode_RMUDashboard() throws Exception {

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
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();
		String expectedCount = assignmentPage.getDistibuteDocsCount(Input.rmu1userName);
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate as Reviewer
		driver.waitForPageToBeReady();
		assignmentPage.selectAssignmentfromRMUDashborad(assignmentName);
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
		assignmentPage.selectAssignmentfromRMUDashborad(assignmentName);
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

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 67)
	public void manageReviewerToDocView_PanelVerify() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50810");
		baseClass.stepInfo("To verify Mini DocList Panel from doc view page for RMU when redirects from"
				+ " Edit Assignmnet > Manage Reviewers >Doc View");

		String expectedURL = Input.url + "DocumentViewer/DocView";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as RMU");
		// creating And Distributing the Assignment
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);
		String assignedCount = assignmentPage.manageRevTabToViewInDocView(Input.rmu1userName);
		driver.waitForPageToBeReady();

		if (driver.getUrl().equals(expectedURL)) {
			baseClass.passedStep("User navigated to docview page from Manage Reviewer Tab/EditAssignemnt  page");
			List<String> listOfData = new ArrayList<>();
			listOfData = reusableDocViewPage.miniDocList();
			List<String> listOfDataAfterSort = new ArrayList<>();
			listOfDataAfterSort = reusableDocViewPage.miniDocList();
			Collections.sort(listOfDataAfterSort);
			baseClass.stepInfo("Assigned doc Count--" + assignedCount);
			baseClass.stepInfo("Docs Count in DocView Page --" + listOfData.size());
			softAssertion.assertEquals(listOfData, listOfDataAfterSort);
			softAssertion.assertEquals(listOfData.size(), Integer.parseInt(assignedCount));
			softAssertion.assertAll();
			baseClass.passedStep("Reviewer Assigned Documents  listed  and documents  sorted as per the document ID  ");
		} else {
			baseClass.failedStep("Application not redirected to the  doc view page ");
		}
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}


	/**
	 * Author : Raghuram A date: 01/31/22 Modified date:N/A Modified by: Raghuram A
	 * Description : Verify document sequence from mini doc list panel for
	 * RMU/Reviewer RPMXCON-50834
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 69)
	public void verifyOriginalSortOrderinMinidoclistPanel() throws InterruptedException, Exception {

		List<String> docIDlist = new ArrayList<>();
		List<String> originalOrderedList;
		List<String> afterSortList;
		String assignmentNameToCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		String sortBy = Input.sortDataBy;
		String sortType = Input.sortType;

		baseClass.stepInfo("Test case Id: RPMXCON-50834 Sprint 11");
		baseClass.stepInfo("Verify document sequence from mini doc list panel for RMU/Reviewer");

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentNameToCreate, Input.codeFormName);
		assignmentPage.Assgnwithdocumentsequence(sortBy, sortType);
		baseClass.stepInfo("Sort by : " + sortBy + " and Sort type : " + sortType);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("Created Assignment name : " + assignmentNameToCreate);

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// View In docView
		docViewPage.selectAssignmentfromDashborad(assignmentNameToCreate);
		baseClass.stepInfo("Assignment Selected : " + assignmentNameToCreate);

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		originalOrderedList = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());
		afterSortList = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		// Verify Sort order
		baseClass.verifyOriginalSortOrder(originalOrderedList, afterSortList, sortType, true);
		baseClass.stepInfo("Verified Original SortOrder In MinidocList panel");

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentNameToCreate);
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 27/01/2022 Modified date: NA Modified by: Baskar
	 * @Description :To verify user can select Multiple documents in Mini Doc List
	 *              from dockout screens and Select Action as 'Code Same as this'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 68)
	public void validateCodeSameIconForSelectedDocs() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51131");
		baseClass.stepInfo("To verify user can select Multiple documents in Mini Doc List from dockout screens "
				+ "and Select Action as 'Code Same as this'");

		String expectedURL = Input.url + "DocumentViewer/DocViewChild";

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);

		// Session search to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// validate code same as icon
		docViewPage.clickGearIconOpenMiniDocList();
		String parent = docViewPage.switchTochildWindow();
		driver.waitForPageToBeReady();
		if (driver.getUrl().equalsIgnoreCase(expectedURL)) {
//			minidoclist child window opened
			baseClass.passedStep("MiniDocList Child window opened");
			String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
			docViewPage.clickCheckBoxMiniDocListActionCodeSameAs(2);
			driver.getWebDriver().navigate().refresh();
//			validation for codesame as icon
			boolean flag = docViewPage.getMiniDocList_CodeSameIcon(prnDoc).Displayed();
			softAssertion.assertTrue(flag);
			docViewPage.childWindowToParentWindowSwitching(parent);
			baseClass.passedStep("Chain link displayed for selected document in minidoclist child window");
		} else {
			baseClass.failedMessage("Minidoclist child window not opened yet");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 01/02/2022 Modified date: NA Modified by: Baskar
	 * @Description :To verify that small icon should be displayed if document is
	 *              tagged as 'Code Same as this'.
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 68)
	public void validateCodeSameAsSmallIcon() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-50946");
		baseClass.stepInfo("To verify that small icon should be displayed " + "if document is tagged as 'Code Same as this'.");

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);

		// Session search to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// validate code same as icon
		driver.waitForPageToBeReady();
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.clickCheckBoxMiniDocListActionCodeSameAs(2);
		driver.getWebDriver().navigate().refresh();
		baseClass.handleAlert();
		
//		validation for codesame as icon
		driver.waitForPageToBeReady();
		boolean flag = docViewPage.getMiniDocList_CodeSameIcon(prnDoc).Displayed();
		softAssertion.assertTrue(flag);
		baseClass.passedStep("Small icon displayed for selected document in minidoclist for code same as");
		softAssertion.assertAll();
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: 02/02/2022 Modified date: NA Modified by: Baskar
	 * @Description :Verify when loading is displayed in mini doc list after selecting 
	 *               the audio document from history drop down then persistent hits 
	 *               should be displayed
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 69)
	public void validateAudioDocsEyeIcon() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51869");
		baseClass.stepInfo("Verify when loading is displayed in mini doc list after "
				+ "selecting the audio document from history drop down then persistent hits should be displayed");

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);

		// Session search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		// validate audio docs eye icon with persistent hits
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		String audioEyePersistent=docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(audioEyePersistent.toLowerCase().contains(Input.audioSearchString1));
		baseClass.stepInfo("Persistent hit panel opened and displaying"+audioEyePersistent+"");
		
		// keep on viewing the document in minidoclist through docview
		for (int i = 1; i <= 5; i++) {
			docViewPage.getMiniDocList_IterationDocs(i).waitAndClick(5);
			driver.waitForPageToBeReady();
			softAssertion.assertTrue(docViewPage.getDocView_TextFileType().Enabled());
			softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
			String prnDocs = docViewPage.getVerifyPrincipalDocument().getText();
			String activeDocId = docViewPage.getDocView_CurrentDocId().getText();
			softAssertion.assertEquals(prnDocs, activeDocId);
			String audioPersistent=docViewPage.getDocView_Audio_Hit().getText().toString();
			softAssertion.assertTrue(audioPersistent.toLowerCase().contains(Input.audioSearchString1));
		}
		baseClass.stepInfo("Navigating one by one document");
		baseClass.passedStep("Persistent hit displayed when navigated to all document from minidoclist");
		
		// validating from histroy dropdown
		docViewPage.clickClockIconMiniDocList();
		String audioClockPersistent=docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(audioClockPersistent.toLowerCase().contains(Input.audioSearchString1));
		baseClass.passedStep("Persistent hit panel displaying when clicked from history drop down");

		// overall assertion
		softAssertion.assertAll();
		
		// logout
		loginPage.logout();
	}
	

	/**
	 * @Author : Baskar date: 02/02/2022 Modified date: NA Modified by: Baskar
	 * @Description :Verify when loading is displayed in mini doc list after scrolling 
	 *               mini doc list then persistent hits should be displayed for the audio document
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 70)
	public void validateAudioDocsEyeIconAfterScrollAndNavOptions() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51867");
		baseClass.stepInfo("Verify when loading is displayed in mini doc list after "
				+ "scrolling mini doc list then persistent hits should be displayed for the audio document");

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);

		// Session search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		// validate audio docs eye icon with persistent hits
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		String audioEyePersistent=docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(audioEyePersistent.toLowerCase().contains(Input.audioSearchString1));
		baseClass.stepInfo("Persistent hit panel opened and displaying"+audioEyePersistent+"");
		
		// Before scroll position
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		Long value = (Long) jse.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		System.out.println(value);
		
		// Scrolling minidoclist
		jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,4000)");
		driver.waitForPageToBeReady();
		
		// After scroll position
		Long value1 = (Long) jse.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		System.out.println(value1);
		softAssertion.assertNotEquals(value, value1);
		baseClass.stepInfo("Minidoclist scrolled with more number of document");
		
		// After scrolling validating persistent hit panel
		String audioPersistent=docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(audioPersistent.toLowerCase().contains(Input.audioSearchString1));
		baseClass.passedStep("Persistent hit panel displaying after scrolling the document in minidoclist");
		
		// validation through navigation option
		docViewPage.verifyThatIsLastDoc();
		String navPersistent=docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(navPersistent.toLowerCase().contains(Input.audioSearchString1));
		baseClass.passedStep("Persistent hit panel displaying when navigation option done");
		
		// overall assertion
		softAssertion.assertAll();
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 63)
	public void verifyCheckMarkDisappearence() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51025");
		baseClass.stepInfo("To Verify icon should not be displayed for uncompleted "
				+ "documents in assignment.");
		
		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);
		
		// creating And Distributing the Assignment
		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);
		
		// navigating from Dashboard to DocView
		driver.waitForPageToBeReady();
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		
		// Completing the documents
		driver.waitForPageToBeReady();
		List<String>DocID = docViewPage.CompleteTheDocumentInMiniDocList(3);
		
		// uncompleting the completed documents
		docViewPage.UncompleteTheCompletedDocsandVerifyCheckMarkIcon(DocID);
		
		// logout
		loginPage.logout();
		
		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Loggedin As Reviewer: " + Input.rev1userName);
		
		// navigating from Dashboard to DocView
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		
		// Completing the documents
		driver.waitForPageToBeReady();
		List<String>DocID1 = docViewPage.CompleteTheDocumentInMiniDocList(3);
		
		// uncompleting the completed documents
		docViewPage.UncompleteTheCompletedDocsandVerifyCheckMarkIcon(DocID1);
		
		// logout
		loginPage.logout();
	}
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
//			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");
		try {
//			loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
