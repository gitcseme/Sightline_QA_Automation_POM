package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	@Test(enabled = false, groups = { "regression" }, priority = 1)
	public void miniDocListConfigurations() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51804");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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

	}

	/**
	 * Author : Raghuram A date: 8/15/21 NA Modified date: 8/24/21 Modified by:
	 * Raghuram A Description : Verify that when assignment saved with a manual sort
	 * order, then the optimized sort order should be presented with the default
	 * fields in mini doc list child window when navigating through any other
	 * assignment Id:RPMXCON-51805 Sprint : 1
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 2)
	public void miniDocListConfigurationsViaChildWindow() throws InterruptedException, Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51805");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

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

	}

	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar Description :
	 * Verify that when mini doc list is configured then should refresh the
	 * persistent hits panel immediately Component:Docview_MiniDocList RPMXCON-51988
	 * Sprint01
	 * 
	 * @throws InterruptedException
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 3)
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
	}

	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar Description :
	 * Verify when loading is displayed in mini doc list after entering the document
	 * number then persistent hits should be displayed Component:Docview_MiniDocList
	 * RPMXCON-51865 Sprint01
	 * 
	 * @throws InterruptedException
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 4)
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
	}

	/**
	 * @Author Mohan Created date: 8/23/21 Description : Verify that when mini doc
	 *         list scrolled down and 'Loading' is displayed then mini doc list
	 *         should be loaded with the audio documents RPMXCON-51836 Modified
	 *         date: 8/31/21 Modified by: Raghuram
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 5)
	public void verifyLoadingIsDisplayedWhenMiniDocListIsScrolled()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51836");
		int documentIdNum = Input.documentIdNum;

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	}

	/**
	 * @Author Mohan Created date: NA Modified date: 11/15/21 Modified by: Mohan
	 * @description Verify that "select all" check-box must no longer appear for any
	 *              user in the mini-docList 'RPMXCON-51872'
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 6)
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

	}

	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify documents with mini document list should be equals when
	 *              it will be pop out
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 7)
	public void verifyingDocumentCountWithMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51608");
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		// Click GearIcon in DocViewPage
		docViewPage.documentCountShouldBeSame(Input.searchString2);

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify check mark icon should be displayed when document is
	 *              completed after selecting 'Code same as' action' from mini doc
	 *              list
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 8)
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

	}

	/**
	 * @Author : Baskar date: 24/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify scrolling from mini doc list child window when document
	 *              is selected from mini doc list after scrolling down with more
	 *              number of documents
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 9)
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

	}

	/**
	 * @Author : Baskar date: 25/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Selected document from history document is not loaded in
	 *              default view.
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 10)
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

	}

	/**
	 * @Author : Baskar date: 26/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that Principal document should not hide under the header
	 *              from mini doc list child window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 11)
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

	}

	/**
	 * @Author : Baskar date: 26/8/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify the document count when mini document list pop out and
	 *              minimize and maximize
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 12)
	public void verifyingDocumentCountWhenMinimizeAndMaximizeMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51609");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Session search to doc view Coding Form
		// Click GearIcon in DocViewPage
		docViewPage.minimizeAndMaximizeDocumentCountShouldBeSame(Input.searchString2);

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.pa1userName + "'");

	}

	/**
	 * Author : Raghuram A date: 8/17/21 NA Modified date: 8/28/21 Modified by:
	 * Raghuram A Description : Verify that when assignment saved with a manual sort
	 * order configuration, then the optimized sort order should be presented with
	 * the default fields from mini doc list
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 13)
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

	}

	/**
	 * Author : Raghuram A date: 8/17/21 Modified date: 8/28/21 Modified by:
	 * Raghuram A Description :Verify that when assignment saved with a manual sort
	 * order configuration, then the optimized sort order should be presented with
	 * the default fields when navigating through same assignment
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 14)
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

	}

	/**
	 * Author : Raghuram A date: 8/18/21 Modified date:8/28/21 Modified by: Raghuram
	 * A Description : Verify that when assignment saved with a manual sort order
	 * configuration, then the optimized sort order should be presented with the
	 * default fields from mini doc list child window
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 15)
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
	}

	/**
	 * Author : Raghuram A date: 8/18/21 Modified date:N/A Modified by: Raghuram A
	 * Description : Verify that in DocView: Assignment data should be displayed by
	 * original sorting then by DocID in the Mini Doc List child window
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 16)
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

	}

	/**
	 * Verify that on document navigation from mini doc list child window when hits
	 * panel is open then enable/disable should be retained Author : Raghuram A
	 * date: 8/18/21 Modified date:N/A Modified by: Raghuram A Description :
	 * RPMXCON-51752
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 17)
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

	}

	/**
	 * Author : Raghuram A date: 8/25/21 Modified date:N/A Modified by: Raghuram A
	 * Description : Verify that when mini doclist reloads by adding additional docs
	 * then for completed documents checkmark with light blue highlighting should be
	 * displayed
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 18)
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

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description :Completes documents by performing 'code same as' action
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 19)
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

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that when document is completed from child window by
	 *              completing document same as last then principal document being
	 *              viewing must always be the first document
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 20)
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

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that when document is completed from child window after
	 *              scrolling down then principal document being viewing must always
	 *              be the first document
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 21)
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

	}

	/**
	 * @Author : Baskar date: 07/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message should be displayed when completed
	 *              documents are selected for code same action from mini doc list
	 *              child window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 22)
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

	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when document is completed from child window by
	 *              applying coding stamp then principal document being viewing must
	 *              always be the first document
	 * 
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 23)
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

	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when document is completed from parent window then
	 *              principal document being viewing from mini doc list child window
	 *              must be the first
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 24)
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

	}

	/**
	 * Author : Mohan date: 9/07/21 Modified date:N/A Modified by: Mohan Description
	 * : Verify Configure Mini DocList modal window should be launched on click of
	 * the small gear icon from Mini DocList child window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 25)
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

	}

	/**
	 * Author : Mohan date: 9/07/21 Modified date:N/A Modified by: Mohan Description
	 * : Verify document selection when document exists in mini doc list is viewed
	 * from analytics panel
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 26)
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

	}

	/**
	 * Author : Mohan date: 9/07/21 Modified date:N/A Modified by: Mohan Description
	 * : Verify when document is selected from mini doc list child window document
	 * should be selected from mini doc list parent window
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 27)
	public void verifyDocsFromMiniDoclistAndChildWindow() throws InterruptedException {
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

	}

	/**
	 * Author : Raghuram A date: 9/08/21 Modified date:N/A Modified by: N/A
	 * Description : Verify on selecting document from history drop down, icon to
	 * indicate that document is selected should not be of completed document
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 28)
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
	}

	/**
	 * @Author : Baskar date: 08/09/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list and
	 *              Save/Complete clicked from coding form parent window
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 29)
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

	}

	/**
	 * Author : Raghuram A date: 9/13/21 NA Modified date: N/A Modified by: Raghuram
	 * A Description : Configure Mini DocList modal window should be launched on
	 * click of the small gear icon in the Mini DocList panel in context of an
	 * assignment Id:RPMXCON-51337 Sprint : 02
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 30)
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

	}

	/**
	 * @Author : Baskar date: 06/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list child
	 *              window and Save/Complete clicked from coding form child window
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 31)
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

	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list child
	 *              window and Save/Complete clicked from coding form parent window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 32)
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

	}

	/**
	 * @Author : Baskar date: 07/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify when Code same action selected from mini doc list and
	 *              Save/Complete clicked from coding form child window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 33)
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

	}

	/**
	 * @Author : Baskar date: 08/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify user is allowed to select up to 4 webfields from a
	 *              preselected list to display in the panel of mini doclist in the
	 *              optimized mode [RPMXCON-15074, RPMXCON-13255]
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 34)
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

	}

	/**
	 * @Author : Baskar date: 12/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify user is allowed to select 4 webfields from a
	 *              preselected list to display in the panel of mini doc list in the
	 *              optimized mode
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 35)
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

	}

	/**
	 * @Author : Baskar date: 12/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify when user selects more than 4 webfields from a
	 *              preselected list to display in the panel of mini doc list in the
	 *              optimized mode
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 36)
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

	}

	/**
	 * @Author : Baskar date: 13/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify document should be complete on selecting 'Remove code
	 *              same' action
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 37)
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

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message should be displayed when completed
	 *              documents are selected for code same action from mini doc list
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 38)
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

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify document should not be selected in mini doc list when
	 *              document viewed from history does not exists in mini doc list
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 39)
	public void verifyDocsShouldNotSelectFromMiniDocListInHistrotyDropDown() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51394");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		miniDocListpage.docsShouldNotOfMiniDocList(0);

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify on click of the document navigation focus should set in
	 *              mini doc list child window when prior document is viewed from
	 *              history drop down of mini doc list child window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 40)
	public void childWindowHistoryDropDownFocus() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51392");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.focusOnChildWindowHistoryDrpDw();

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify on click of the document navigation document should be
	 *              selected in mini doc list child window when prior document is
	 *              viewed from history drop down of mini doc list child window
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 41)
	public void historyDropDownMiniDicListChild() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51391");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		miniDocListpage.selectDocFromHistoryDropDown();

	}

	/**
	 * @Author : Baskar date: 22/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document is selected to view from history drop
	 *              down selection when hits panel is open then enable/disable
	 *              should be retained
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 42)
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

	}

	/**
	 * @Author : Baskar date: 19/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify documents folder action is done successfully from
	 *              mini doclist
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 43)
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
		boolean flag = docViewPage.geDocView_MiniList_CodeSameAsIcon().isElementPresent();
		Assert.assertFalse(flag);
		baseClass.passedStep("Code same as icon not displayed in minidoclist after performing folder action");
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when document is completed from child window then
	 *              principal document being viewing must always be the first
	 *              document
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 44)
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
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify check mark icon should be displayed in the mini doc
	 *              list for completed document after applying coding stamp
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 45)
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
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message is prompted when navigates to other
	 *              page without completing or saving 'Code same as this' action
	 *              from Dov View > mini doc list redirected from Edit Assignment >
	 *              Manage Reviewers
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 46)
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
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify warning message is prompted to the user when user
	 *              clicks browser refresh button without completing or saving from
	 *              mini doc list panel
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 47)
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

	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify that sorting sequence of documents should not save
	 *              for all users of assignment
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 48)
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
	}

	/**
	 * @Author : Baskar date: 17/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : To verify mini doc list form doc view page when user redirects
	 *              from Basic search and Save Search.
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 49)
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

	}

	/**
	 * @Author : Baskar date: 16/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that updated metadata should displayed on Mini doc list
	 */

	@Test(enabled = false, groups = { "regression" }, priority = 50)
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

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");
		try {
			loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
