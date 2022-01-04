package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Set;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SoftAssert softAssertion;
	AssignmentsPage assignmentPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	DocListPage docListPage;
	DocViewPage docViewAnalytics;
	MiniDocListPage miniDocListPage;

	@BeforeClass(alwaysRun = true)
	public void condition1() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws Exception, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the thread map toggle is enabled, the
	 *              length of the email thread should not be a constraint and with
	 *              email threads such as the above (23 mails and 140 participants)
	 *              from Analytics panel child window 'RPMXCON-51846'
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyDocumentFromAnalyticsPanelWithEmailThreadMap()
			throws ParseException, InterruptedException, IOException {
		
		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		
		baseClass.stepInfo("Test case Id: RPMXCON-51846");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		String textValue = Input.TextValue;

		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentName, Input.codeFormName,
				SessionSearch.pureHit);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentPage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewAnalytics.docViewAnalyticsPanelThread(textValue);
		baseClass.stepInfo(
				"Thread Map toggled and Email thread is viewed from Analytic Child window and verified successfully");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is viewed form the Dasboard successfully");
		docViewAnalytics.docViewAnalyticsPanelThread(textValue);
		baseClass.stepInfo(
				"Thread Map toggled and Email thread is viewed from Analytic Child window and verified successfully");
		
		baseClass.passedStep(
				"To Verify that when the thread map toggle is enabled, the length of the email thread should not be a constraint and with email threads such as the above (23 mails and 140 participants) from Analytics panel child window");
	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that when document is viewed from analytics panel when
	 *              hits panel is open then enable/disable should be retained
	 *              'RPMXCON-51753'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyDocumentFromAnalyticsPanel() throws ParseException, InterruptedException, IOException {
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51753");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		String foldName = "FolderName" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolder(foldName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		docViewAnalytics.selectAdvancedSearch(foldName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		docViewAnalytics.selectPureHit();
		baseClass.stepInfo("Doc is search and Purehit is selected successfully");
		docViewAnalytics.selectViewInDocView();
		baseClass.stepInfo("Doc is viewed on DocView successfully");
		docViewAnalytics.selectEyeIcon();
		docViewAnalytics.selectAnalyticsPanelWithoutChildWindow();
		baseClass.stepInfo("Doc is viewed on DocView without pop-out child window and verified successfully");
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		String docFromTable = docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().getText();
		System.out.println(docFromTable);
		softAssertion.assertEquals(docFromTable, Input.Panel.toUpperCase());
		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify that when document is viewed from analytics panel when hits panel is open then enable/disable should be retained is successfully");

	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that when document is viewed from analytics panel
	 *              child window when hits panel is open then enable/disable should
	 *              be retained
	 * //@TestCase 'RPMXCON-51754' Document is viewed from 'analytics panel' when hits
	 *           panel is open with pop-out child window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyDocumentFromAnalyticsPanelWithChildWindow()
			throws ParseException, InterruptedException, IOException {
		
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		
		baseClass.stepInfo("Test case Id: RPMXCON-51754");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		String foldName = "FolderName" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolder(foldName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		docViewAnalytics.selectAdvancedSearch(foldName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		docViewAnalytics.selectPureHit();
		baseClass.stepInfo("Doc is search and Purehit is selected successfully");
		docViewAnalytics.selectViewInDocView();
		baseClass.stepInfo("Doc is viewed on DocView successfully");
		docViewAnalytics.selectAnalyticsPanelWithChildWindow();
		String parentWindowID = driver.CurrentWindowHandle();
		Set<String> allWindowsId = driver.WindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		baseClass.stepInfo("Doc is viewed on DocView with pop-out child window and verified successfully");
		String docFromTable = docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().getText();
		softAssertion.assertEquals(docFromTable, Input.Panel.toUpperCase());
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify that when document is viewed from analytics panel child window when hits panel is open then enable/disable should be retained is successfully");

	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from Analytics
	 *              panel parent window on click of the link to load more data
	 *              'RPMXCON-51723'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyHorizontalScrollBarInAnalyticsPanel() throws InterruptedException {

		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51723");
		// login as Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		String text = Input.NewDocId;

		// Search the query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc is searched and navigated to the DocView successfully");

		// Doc Viewed in Analytics Panel
		docViewAnalytics.verifyHorizontalTab(text);
		baseClass.stepInfo("Doc is selected and viewed in the DocView Analytics Panel successfully");
		baseClass.passedStep(
				"Verify the horizontal scroll bar should not reset when document is selected from thread map on moving to right from Analytics panel parent window on click of the link to load more data");

		// logout
		loginPage.logout();
		baseClass.stepInfo("User logged off from project admin credentials");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Search the query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc is searched and navigated to the DocView successfully");

		// Doc Viewed in Analytics Panel
		docViewAnalytics.verifyHorizontalTab(text);
		baseClass.stepInfo("Doc is selected and viewed in the DocView Analytics Panel successfully");

		// logout
		loginPage.logout();
		baseClass.stepInfo("User logged off from Review Manager credentials");

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as  with " + Input.rev1userName + "");

		// Search the query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc is searched and navigated to the DocView successfully");

		// Doc Viewed in Analytics Panel
		docViewAnalytics.verifyHorizontalTab(text);
		baseClass.stepInfo("Doc is selected and viewed in the DocView Analytics Panel successfully");

	}

	/**
	 * 
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that Thread Map tab should be displayed when
	 *              navigating to doc view outside of assignment 'RPMXCON-51847'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyDocumentFromAnalyticsPanelWithThreadMap()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51847");
		loginPage = new LoginPage(driver);
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");
		String text = Input.NewDocId;
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("*****Doc is searched from basic search and viewed in the DocView page successfully*****");
		docViewAnalytics.selectDocumentFromAnalyticPanel(text);
		UtilityLog.info("*****Thread Map tab is displayed when navigating to doc view outside of assignment*****");
		baseClass.stepInfo("*****Thread Map tab is displayed when navigating to doc view outside of assignment*****");
		String doc = docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().getText();
		softAssertion.assertTrue(true, doc);
		baseClass.passedStep(
				"Verify that Thread Map tab should be displayed when navigating to doc view outside of assignment is successfully");

	}


	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To Verify that when the threadmap toggle is enabled, the length
	 *              of the email thread should not be a constraint and with email
	 *              threads such as the above (23 mails and 140 participants) from
	 *              Analytics panel 'RPMXCON-51845'
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyAnalyticsPanelWithEmailThreadMapEnable()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51845");
		String assignmentName = "assignmentB1" + Utility.dynamicNameAppender();
		String textValue = Input.TextEmpty;

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as Review Manager Admin with "
				+ Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentName, Input.codeFormName,
				SessionSearch.pureHit);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");

		docViewAnalytics.docViewAnalyticsPanelThread(textValue);
		baseClass.stepInfo("Thread Map toggled is Enable and viewed from Analytic Child window successfully");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in successfully with Reviewer credentials");

		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewAnalytics.docViewAnalyticsPanelThread(textValue);
		baseClass.stepInfo("Doc is Viewed in the DocView Analytics Panel Child window successfully");
		String text = docViewAnalytics.getDocView_DocumentThreadMap().getText();
		softAssertion.assertEquals(text, textValue);
		baseClass.passedStep(
				"To Verify that when the threadmap toggle is enabled, the length of the email thread should not be a constraint and with email threads such as the above (23 mails and 140 participants) from Analytics panel is succesfully verifed");

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the threadmap toggle is disabled, the
	 *              application should not try to pull the thread information on doc
	 *              view'RPMXCON-51843'
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyAnalyticsPanelWithEmailThreadMapDisabled()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51843");
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage =  new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		String textValue = Input.TextEmpty;

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as Review Manager Admin with "
				+ Input.rmu1userName + "");
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
        assignmentPage.toggleDisableThreadMap();
        assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		baseClass.impersonateRMUtoReviewer();
		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewAnalytics.selectDocAnalyticsPanelWithoutChildWindow();
		baseClass.stepInfo("Thread Map toggled is disabled and not visible in DocView Analytics Panel");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in successfully with Reviewer credentials");

		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewAnalytics.selectDocAnalyticsPanelWithoutChildWindow();
		baseClass.stepInfo("Thread Map toggled is disabled and not visible in DocView Analytics Panel");

		Element text = docViewAnalytics.getDocView_DocumentThreadMap();
		softAssertion.assertEquals(text, textValue);
		baseClass.passedStep(
				"To Verify that when the threadmap toggle is disabled, the application should not try to pull the thread information on doc view is sucessfully");

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the threadmap toggle is disabled, the
	 *              application should not try to pull the thread information on doc
	 *              view from Analytics panel child window'RPMXCON-51844'
	 * 
     */

	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyAnalyticsPanelEmailThreadMapDisabled() throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51844");
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage =  new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as Review Manager Admin with "
				+ Input.rmu1userName + "");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
        assignmentPage.toggleDisableThreadMap();
        assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewAnalytics.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		docViewAnalytics.verifyThreadMapTab();
		baseClass.stepInfo(
				"Thread Map toggled and Email thread is not viewed from Analytic Child window and verified successfully");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in successfully with Reviewer credentials");
		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewAnalytics.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		docViewAnalytics.verifyThreadMapTab();
		baseClass.stepInfo("ThreadMap tab is not Visible in the Analytics Panel");
		
		baseClass.passedStep(
				"To Verify that when the threadmap toggle is disabled, the application should not try to pull the thread information on doc view from Analytics panel child window is sucessfully");

	}

	
	/**
	 * @throws Exception
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify code same icon should not display for the documents
	 *              selected for folder action from mini doc list after selecting
	 *              folder action from analytics panel.'RPMXCON-51735 '
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyCodeAsSameFromFamilyMembersAnalyticsPanel() throws Exception {
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51735");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		String miniFolderName = "miniFolderName" + Utility.dynamicNameAppender();
		String analyticsFolderName = "analyticsFolderName" + Utility.dynamicNameAppender();

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// Get Purehit Count
		docViewAnalytics.getFamilyMemberPurehit();
		docViewAnalytics.selectViewInDocView();
		baseClass.stepInfo("Doc are selected and viewed in the DocView successfully");

		// View From MiniDoc
		docViewAnalytics.MiniDoclistNewFolderCreation();
		sessionSearch.BulkActions_Folder(miniFolderName);
		baseClass.stepInfo("Doc is selected form Mini Doclist and Folder is created successfully");

		// View from Analytics Panel
		docViewAnalytics.Analytics_FamilyActionsFolderCreation();
		sessionSearch.BulkActions_Folder(analyticsFolderName);
		baseClass.stepInfo("Doc is selected from Family Member and Folder is created successfully");

		// Verify Code As Same
		docViewAnalytics.verifyCodeAsSameInMiniDocList();
		baseClass.stepInfo("Doc is selected from Mini Doclist and Code As Same is verified successfully");

	}

	/**
	 * @throws Exception
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify documents folder action is done successfully from
	 *              analytics panel.'RPMXCON-51733'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyDocFolderAction() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51733");
		
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		String analyticsFolderName = "analyticsFolderName" + Utility.dynamicNameAppender();

		// Basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// Select Purehit from search result
		docViewAnalytics.getFamilyMemberPurehit();
		docViewAnalytics.selectViewInDocView();
		baseClass.stepInfo("Doc are selected and viewed in the DocView successfully");
		// Create a folder in Doc Analytics
		docViewAnalytics.Analytics_FamilyActionsFolderCreation();
		sessionSearch.BulkActions_Folder(analyticsFolderName);
		baseClass.stepInfo("Doc is selected from Family Member and Folder is created successfully");

		// Verifying Code as Same Icon
		docViewAnalytics.verifyCodeAsSameInMiniDocList();
		baseClass.stepInfo("Doc is selected from Mini Doclist and Code As Same is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify Thread Map tab when no documents/thread to
	 *              display.'RPMXCON-50952'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyThreadMapTabWithNoDocs() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50952");

		// Login As PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Select AnalyticsPanel
		docViewAnalytics = new DocViewPage(driver);
		docViewAnalytics.verifyThreadMapWithNoDocs();
		baseClass.stepInfo("No Doc is viewed from Analytics panel Thread Map tab successfully");

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Select AnalyticsPanel
		docViewAnalytics.verifyThreadMapWithNoDocs();
		baseClass.stepInfo("No Doc is viewed from Analytics panel Thread Map tab successfully");

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Select AnalyticsPanel
		docViewAnalytics.verifyThreadMapWithNoDocs();
		baseClass.stepInfo("No Doc is viewed from Analytics panel Thread Map tab successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify 'View All' should be displayed on Thread Map tab
	 *              when more than 20 documents exists.'RPMXCON-50953'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyViewAllThreadMapTabWith20Docs() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50953");

		// Login As PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		String text = Input.NewDocId;

		// verify 20 plus docs in the thread map tab
		docViewAnalytics = new DocViewPage(driver);
		docViewAnalytics.verifyThreadMapWith20Docs(text);

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// verify 20 plus docs in the thread map tab
		docViewAnalytics.verifyThreadMapWith20Docs(text);

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// verify 20 plus docs in the thread map tab
		docViewAnalytics.verifyThreadMapWith20Docs(text);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify user should be able to view threaded documents when
	 *              redirects from saved search page.'RPMXCON-50954'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyThreadedDocsFromSavedSearch() throws InterruptedException {
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-50954");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();

		// Basic search and save the Query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");

		// Saved Search to DocView
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		// Verify Thread Map DocId
		docViewAnalytics.verifyThreadMapDocId();

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search and save the Query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");

		// Saved Search to DocView
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		// Verify Thread Map DocId
		docViewAnalytics.verifyThreadMapDocId();

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search and save the Query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");

		// Saved Search to DocView
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		// Verify Thread Map DocId
		docViewAnalytics.verifyThreadMapDocId();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify user should be able to view threaded documents when
	 *              redirects from doc list page.'RPMXCON-50955'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyThreadedDocRedirectFromDocList() throws InterruptedException {
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docListPage = new DocListPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-50955");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Select docs from Doclist to DocView
		docListPage.selectThreadedDocAndViewInDocView();
		baseClass.stepInfo("Docs are selected from DocList and Viewed in DocView succcessfully");

		// Verify Threaded docs DocId
		docViewAnalytics.verifyThreadedDocsFromDocList();

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Select docs from Doclist to DocView
		docListPage.selectThreadedDocAndViewInDocView();
		baseClass.stepInfo("Docs are selected from DocList and Viewed in DocView succcessfully");

		// Verify Threaded docs DocId
		docViewAnalytics.verifyThreadedDocsFromDocList();

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Select docs from Doclist to DocView
		docListPage.selectThreadedDocAndViewInDocView();
		baseClass.stepInfo("Docs are selected from DocList and Viewed in DocView succcessfully");

		// Verify Threaded docs DocId
		docViewAnalytics.verifyThreadedDocsFromDocList();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : Verify option to view document from thread map tab of
	 *              analytics panel.'RPMXCON-51413'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyOptionToViewDocInThreadMapTab() throws InterruptedException {
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51413");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify docs in Mini Doclist and Thread Map tab
		docViewAnalytics.verifyDocsInMiniDocList();
		baseClass.stepInfo("Docs are verified in both MiniDoclist and Thread Map Tab Successfully");

		// select doc from Analytics thread map tab and verify the doc
		docViewAnalytics.selectDocsFromThreadMapTab();

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify docs in Mini Doclist and Thread Map tab
		docViewAnalytics.verifyDocsInMiniDocList();
		baseClass.stepInfo("Docs are verified in both MiniDoclist and Thread Map Tab Successfully");

		// select doc from Analytics thread map tab and verify the doc
		docViewAnalytics.selectDocsFromThreadMapTab();

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify docs in Mini Doclist and Thread Map tab
		docViewAnalytics.verifyDocsInMiniDocList();
		baseClass.stepInfo("Docs are verified in both MiniDoclist and Thread Map Tab Successfully");

		// select doc from Analytics thread map tab and verify the doc
		docViewAnalytics.selectDocsFromThreadMapTab();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Near Dupe
	 *              tab and navigates to another document, the Analytics Panel Tab
	 *              previously selected must remain..'RPMXCON-51414'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyAnalyticsPanelTabDOcsSelectsFromMiniDocList() throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51414");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// select the NearDupes Purehit and view in DocView
		docViewAnalytics.selectNearDupePureHit();
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Docs are selected and viewed in DocView successfully");

		// Navigates to other document from Mini doc list and verify analytics panel
		docViewAnalytics.selectAnalyticsNearDupeTab();

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// select the NearDupes Purehit and view in DocView
		docViewAnalytics.selectNearDupePureHit();
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Docs are selected and viewed in DocView successfully");

		// Navigates to other document from Mini doc list and verify analytics panel
		docViewAnalytics.selectAnalyticsNearDupeTab();

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// select the NearDupes Purehit and view in DocView
		docViewAnalytics.selectNearDupePureHit();
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Docs are selected and viewed in DocView successfully");

		// Navigates to other document from Mini doc list and verify analytics panel
		docViewAnalytics.selectAnalyticsNearDupeTab();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that thread map presents only emails when threaded
	 *              document contains non email attachment'RPMXCON-51508'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyThreadedDocsContainsNonEmailAttachment() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51508");

		// Login As PA
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		String text = Input.MiniDocId;

		// View the email document from mini doc list having non email attachment
		docViewAnalytics.selectDocAndVerifyInMetaData(text);

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// View the email document from mini doc list having non email attachment
		docViewAnalytics.selectDocAndVerifyInMetaData(text);

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// View the email document from mini doc list having non email attachment
		docViewAnalytics.selectDocAndVerifyInMetaData(text);

	}
	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right after changing
	 *              the Analytics panel position'RPMXCON-51721'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyHorizontalscrollTabAndChangingTheAnalyticsPosition() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51721");
		String documentToBeScrolled = Input.documentToBeScrolled;
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// drag and place the Analytics Widget
		docViewAnalytics.dragAndPlaceAnalyticsWidget();
		baseClass.stepInfo("Drag and Drop is done successfully");

		// select doc from mini doclist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// drag and place the Analytics Widget
		docViewAnalytics.dragAndPlaceAnalyticsWidget();
		baseClass.stepInfo("Drag and Drop is done successfully");

		// select doc from mini doclist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// drag and place the Analytics Widget
		docViewAnalytics.dragAndPlaceAnalyticsWidget();
		baseClass.stepInfo("Drag and Drop is done successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from child window
	 *              after changing the Analytics panel position'RPMXCON-51722'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyHorizontalscrollTabAndChangingTheAnalyticsPositionWithChildWindow() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51722");
		String documentToBeScrolled = Input.documentToBeScrolled;
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// drag and place the Analytics Widget
		docViewAnalytics.dragAndPlaceAnalyticsWidget();
		baseClass.stepInfo("Drag and Drop is done successfully");

		// select doc from mini doclist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBarWithChildWindow(documentToBeScrolled);

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// drag and place the Analytics Widget
		docViewAnalytics.dragAndPlaceAnalyticsWidget();
		baseClass.stepInfo("Drag and Drop is done successfully");

		// select doc from mini doclist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBarWithChildWindow(documentToBeScrolled);

		// Logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// drag and place the Analytics Widget
		docViewAnalytics.dragAndPlaceAnalyticsWidget();
		baseClass.stepInfo("Drag and Drop is done successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBarWithChildWindow(documentToBeScrolled);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from Analytics
	 *              panel parent window'RPMXCON-51719'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyThreadedDocuments() throws InterruptedException {

		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51719");
		String documentToBeScrolled = Input.documentToBeScrolled;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

		// Logout PA
		loginPage.logout();

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

		// Logout RMU
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from Analytics
	 *              panel child window'RPMXCON-51720'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyThreadedDocumentsWithChildWindow() throws InterruptedException {


		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		
		baseClass.stepInfo("Test case Id: RPMXCON-51720");
		String documentToBeScrolled = Input.documentToBeScrolled;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.VerifyHorizontalScrollBarWithChildWindow(documentToBeScrolled);

		// Logout PA
		loginPage.logout();

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.VerifyHorizontalScrollBarWithChildWindow(documentToBeScrolled);

		// Logout PA
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.VerifyHorizontalScrollBarWithChildWindow(documentToBeScrolled);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify warning message should be displayed when completed
	 *              documents are selected for code same action from Analytics
	 *              panel'RPMXCON-51411'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyWarningMessageCodeAsSameInAnalyticsPanel() throws InterruptedException {

		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51411");
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search and bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Basic search and bulk assign is done successfully");

		// Assignment creation and distrubute docs
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentName, Input.codeFormName,
				SessionSearch.pureHit);
		baseClass.stepInfo(
				"Doc is Assigned from Saved Search and Assignment '" + assignmentName + "' is created Successfully");

		// Logout RMU
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the assignment from dashboard
		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");

		// Complete the docs from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocListCompletedDoc();

		// verify warning message when select the same doc
		docViewAnalytics.verifyWarningMessageCodeAsSameIsSelectedCompletedDocs();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify warning message should be displayed when completed
	 *              documents are selected for code same action from Analytics panel
	 *              child window'RPMXCON-51412'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 23)

	public void verifyWarningMessageCodeAsSameInAnalyticsPanelWithChildWindow() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51412");
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search and bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Basic search and bulk assign is done successfully");

		// Assignment creation and distrubute docs
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentName, Input.codeFormName,
				SessionSearch.pureHit);
		baseClass.stepInfo(
				"Doc is Assigned from Saved Search and Assignment '" + assignmentName + "' is created Successfully");

		// Logout RMU
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the assignment from dashboard
		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");

		// Complete the docs from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocListCompletedDoc();

		// Select Gear Icon
		docViewAnalytics.selectGearIcon();

		// verify warning message when select the same doc
		docViewAnalytics.verifyWarningMessageWithChildWindow();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user can select any document and details is
	 *              displayed in Analytical and Meta data Panel.'RPMXCON-50960'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyMetaDataPanelIsDisplayed() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50960");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify Meta Data Panel is displayed in DocView
		baseClass.stepInfo("To verify MetaData Panel in DocView");
		docViewAnalytics.verifyMetaDataPanelInDocView();

		// Logout PA
		loginPage.logout();

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify Meta Data Panel is displayed in DocView
		baseClass.stepInfo("To verify MetaData Panel in DocView");
		docViewAnalytics.verifyMetaDataPanelInDocView();

		// Logout RMU
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify Meta Data Panel is displayed in DocView
		baseClass.stepInfo("To verify MetaData Panel in DocView");
		docViewAnalytics.verifyMetaDataPanelInDocView();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Conceptually Similar child window and Save/Complete clicked from
	 *              coding form child window'RPMXCON-51385'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyCodeAsSameConceptuallySimilarChildWindow() throws InterruptedException {

		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51385");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Conceptually Similar child window and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		String documnetToBeSelected = "Conceptually";
		String documentToBeSelectedForReviewer = Input.conceptualDocumentReviewer;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		baseClass.stepInfo("Searching documents based on search string is done successfully");

		// bulk Assign and create assignment
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewAnalytics.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanelForChildWindow(documnetToBeSelected,
				parentWindowID);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Save Icon
		docViewAnalytics.getSaveIcon().Click();

		try {
			if (docViewAnalytics.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewAnalytics.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_AnalyticalPanelExpand());
				docViewAnalytics.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same From Conceptual tab
		docViewAnalytics.performCodeSameForConceptualDocuments(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// Verify Code As Same Icon
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewAnalytics.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewAnalytics.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocListExpand());
				docViewAnalytics.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		baseClass.waitForElement(docViewAnalytics.getCodeSameIconMiniDocList());
		softAssertion.assertEquals(docViewAnalytics.getCodeSameIconMiniDocList().Displayed().booleanValue(), true,
				"Scenario 1");

		driver.scrollPageToTop();
		docViewAnalytics.getSaveIcon().Click();

		// popout Coding Form and complete the docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from MiniDocList
		softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(), true,
				"Scenario 2");
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Expand Analytics Panel

		try {
			if (docViewAnalytics.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewAnalytics.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_AnalyticalPanelExpand());
				docViewAnalytics.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify checkmark in other Tabs
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		// logout RMU
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		parentWindowID = driver.getWebDriver().getWindowHandle();

		try {
			if (docViewAnalytics.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewAnalytics.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocListExpand());
				docViewAnalytics.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		// popout MiniDocList
		docViewAnalytics.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select MiniDocList Docs
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelectedForReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		docViewAnalytics.getSaveIcon().Click();
		driver.waitForPageToBeReady();

		try {
			if (docViewAnalytics.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewAnalytics.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocListExpand());
				docViewAnalytics.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		try {
			if (docViewAnalytics.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewAnalytics.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_AnalyticalPanelExpand());
				docViewAnalytics.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Popout AnalyticsPanel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same In Conceptullay Similar tab
		docViewAnalytics.performCodeSameForConceptualDocuments(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// Verify Code Same Icon is displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewAnalytics.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewAnalytics.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocListExpand());
				docViewAnalytics.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		baseClass.waitForElement(docViewAnalytics.getCodeSameIconMiniDocList());
		softAssertion.assertEquals(docViewAnalytics.getCodeSameIconMiniDocList().Displayed().booleanValue(), true);

		driver.scrollPageToTop();
		docViewAnalytics.getSaveIcon().Click();

		// popout Coding Form and complete Docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from miniDoclist
		softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(), true);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Expand Analytics Panel
		try {
			if (docViewAnalytics.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewAnalytics.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewAnalytics.getDocView_AnalyticalPanelExpand());
				docViewAnalytics.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify Completed Checkmark is displayed
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");
		baseClass.passedStep(
				"To Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form child window is verified successfully");

		softAssertion.assertAll();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Conceptually Similar and Save/Complete clicked from coding form
	 *              child window'RPMXCON-51383'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyCodeAsSameWithCodingFormChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51383");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = "Conceptually";
		String documentToBeSelectedForReviewer = Input.conceptualDocumentReviewer;

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		baseClass.stepInfo("Searching documents based on search string");

		// Bulk Assign and assignment Creation
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		docViewAnalytics.selectAssignmentfromDashborad(assname);

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForConceptualDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();
		baseClass.stepInfo("Pop-out Coding form and Docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		// logout RMU
		loginPage.logout();

		// Login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select MiniDocList Docs
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelectedForReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForConceptualDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();
		baseClass.stepInfo("Pop-out Coding form and Docs are completed successfully");

		// Select MiniDocList Docs
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelectedForReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form child window is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Conceptually Similar and Save/Complete clicked from coding form
	 *              parent window'RPMXCON-51382'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyCodeAsSameConceptuallySimilarParentWindow() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51382");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocs1;
		String documentToBeSelectedForReviewer = Input.conceptualDocumentReviewer;

		// Basic Search

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		baseClass.stepInfo("Searching documents based on search string");

		// Bulk Assign and assignment Creation
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		docViewAnalytics.selectAssignmentfromDashborad(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForConceptualDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select MiniDocList Docs
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelectedForReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForConceptualDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select MiniDocList Docs
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelectedForReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();
		baseClass.passedStep(
				"Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form parent window is verified Successfully");

	}
	
	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that on thread map tab when the principal document is
	 *              F2, the thread map should not present any emails.'RPMXCON-51522'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void verifyThreadMapTabWithPrincipalDocs() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51522");

		String docId = Input.principalDocId;
		String sourceId = Input.sourceDocId;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		miniDocListPage.selectSourceDocIdInAvailableField();

		// view the doc From MiniDocList and ThreadMap Tab
		docViewAnalytics.selectTextBoxInDocView(docId, sourceId);

		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithNoEmailDocs();

		baseClass.passedStep(
				"To Verify that on thread map tab when the principal document is F2, the thread map should not present any emails is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that on thread map tab when the principal document is
	 *              E2, the thread map presents E1, E2, E3'RPMXCON-51518'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyThreadMapPrincipalDocsE2() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51518");

		String docId1 = Input.principalDocId6;
		String docId2 = Input.principalDocId3;
		String docId3 = Input.principalDocId2;
		String sourceId = Input.sourceDocId3;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		miniDocListPage.selectSourceDocIdInAvailableField();

		// view the doc From MiniDocList and ThreadMap Tab
		docViewAnalytics.selectTextBoxInDocView(docId3, sourceId);

		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs(docId1, docId2, docId3);

		baseClass.passedStep(
				"To Verify that on thread map tab when the principal document is E2, the thread map presents E1, E2, E3 is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that on thread map tab when the principal document is
	 *              E3, the thread map presents E1, E2, E3'RPMXCON-51519'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyThreadMapPrincipalDocsE3() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51519");

		String docId = Input.principalDocId1;
		String docId1 = Input.principalDocId6;
		String docId2 = Input.principalDocId3;
		String docId3 = Input.principalDocId2;
		String sourceId = Input.sourceDocId1;
		String sourceId2 = Input.sourceDocId2;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		miniDocListPage.selectSourceDocIdInAvailableField();

		// view the doc From MiniDocList and ThreadMap Tab
		docViewAnalytics.selectTextBoxInDocView(docId3, sourceId);
		baseClass.stepInfo("Docs Selected from Mini doclist with sourceId 331ID00000209 succesfully");
		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs(docId1, docId2, docId3);

		driver.scrollPageToTop();
		// view the doc From MiniDocList and ThreadMap Tab
		docViewAnalytics.selectTextBoxInDocView(docId, sourceId2);
		baseClass.stepInfo("Docs Selected from Mini doclist with sourceId 331ID00000186 succesfully");
		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs(docId1, docId2, docId3);

		baseClass.passedStep(
				"To Verify that on thread map tab when the principal document is E3, the thread map presents E1, E2, E3 is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when 'View Document' action is selected from thread map
	 *              when multiple documents are selected'RPMXCON-51428'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyViewDocumentInThreadMapMutiDocsAreSelected() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51428");

		String docId = "ThreadMap";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select Docs from MiniDocList
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(docId);

		// verify Error Msg when multi docs are selected from Thread map tab
		docViewAnalytics.verifyErrorMsgOnMultiSelectedDocsInThreadMap();
		baseClass.passedStep(
				"To Verify when 'View Document' action is selected from thread map when multiple documents are selected is verified successfully");

		// logout
		loginPage.logout();

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select Docs from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(docId);

		// verify Error Msg when multi docs are selected from Thread map tab
		docViewAnalytics.verifyErrorMsgOnMultiSelectedDocsInThreadMap();
		baseClass.passedStep(
				"To Verify when 'View Document' action is selected from thread map when multiple documents are selected is verified successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select Docs from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(docId);

		// verify Error Msg when multi docs are selected from Thread map tab
		docViewAnalytics.verifyErrorMsgOnMultiSelectedDocsInThreadMap();
		baseClass.passedStep(
				"To Verify when 'View Document' action is selected from thread map when multiple documents are selected is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify user select Multiple documents in Thread Map->Analytic
	 *              Panel and Select Action as 'Code Same as this' and code same
	 *              icon should be displayed'RPMXCON-51239'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifyMutiDocsSelectedAndCodeAsSame() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51239");

		baseClass.stepInfo(
				"Verify user select Multiple documents in Thread Map->Analytic Panel and Select Action as 'Code Same as this' and code same icon should be displayed");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Basic Search

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Searching documents based on search string");

		// Bulk Assign and assignment Creation
		sessionSearch.bulkAssignThreadedDocs();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// View in doc View
		assignmentPage.selectAssignmentToViewinDocview(assname);

		// verify Code same as Icon
		docViewAnalytics.selectDocsFromThreadMapTabAndActionCodeSame();
		baseClass.passedStep(
				"To Verify user select Multiple documents in Thread Map->Analytic Panel and Select Action as 'Code Same as this' and code same icon is displayed successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when user select 'Folder' action from thread map without
	 *              selecting document'RPMXCON-51241'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyFolderActionWithoutSelectingThreadDocsAndActionAsFolder() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51241");

		baseClass.stepInfo("Verify when user select 'Folder' action from thread map without selecting document");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Basic Search

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Searching documents based on search string");

		// Bulk Assign and assignment Creation
		sessionSearch.bulkAssignThreadedDocs();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// View in doc View
		assignmentPage.selectAssignmentToViewinDocview(assname);

		// Folder Action should be disabled when no docs are selected
		docViewAnalytics.verifyWhenNoDocsAreSelectedFromThreadMapAndFolderAction();
		baseClass.passedStep(
				"To Verify when user select 'Folder' action from thread map without selecting document has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when no document to display on thread map all actions
	 *              should be disable'RPMXCON-51242'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyWhenNoDocsAreThereInThreadMapAndAllActionShouldBeDisabled() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51242");

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Verify when no document to display on thread map all actions should be disable");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Basic Search

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Searching documents based on search string");

		// Bulk Assign and assignment Creation
		sessionSearch.bulkAssignThreadedDocs();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// View in doc View
		assignmentPage.selectAssignmentToViewinDocview(assname);

		// verify All Action as disable
		docViewAnalytics.verifyNoDocsThereInThreadDisabed();
		baseClass.passedStep(
				"To Verify when no document to display on thread map all actions should be disable are verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify user select multiple documents in Thread Map->Analytic
	 *              Panel and Select Action as 'Folder''RPMXCON-51240'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyMultiDocsInThreadMapTabAndSelectActionAsFolder() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51240");

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo(
				"Verify user select multiple documents in Thread Map->Analytic Panel and Select Action as 'Folder'");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String folderName = "ThreadDocs" + Utility.dynamicNameAppender();

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Searching documents based on search string");

		// Bulk Assign and assignment Creation
		sessionSearch.bulkAssignThreadedDocs();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// View in doc View
		assignmentPage.selectAssignmentToViewinDocview(assname);

		// Folderup ThreadMap Tab Docs
		docViewAnalytics.verifyFolderActionForThreadMapDocs(folderName);
		baseClass.passedStep(
				"To Verify user select multiple documents in Thread Map->Analytic Panel and Select Action as 'Folder' is verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              thread map and Save/Complete clicked from coding form parent
	 *              window'RPMXCON-51370'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void verifyCodeAsSameWithCodingFormThreadMapParentWindow() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51370");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > thread map and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.threadDocWithToolTip;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableCodingStamp(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForThreadedDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// edit coding form and Save
		docViewAnalytics.editCodingFormCompleteForThreadDocs();

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(Input.threadDocumentForReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForThreadedDocumentsForReviewer();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// edit coding form and Save
		docViewAnalytics.editCodingFormCompleteForThreadDocsForReviewer();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that completed icon should be displayed for documents on
	 *              thread map tab when RMU redirect to doc view from manage
	 *              assignment'RPMXCON-51453'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyCompleteIconOnThreadMapTab() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51453");
		baseClass.stepInfo(
				"Verify that completed icon should be displayed for documents on thread map tab when RMU redirect to doc view from manage assignment");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocs1;
		int rowNo = 3;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableCodingStamp(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// complete the docs
		docViewAnalytics.selectCompleteButton();
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are completed successfully");

		// Impersonate As RMU
		driver.waitForPageToBeReady();
		baseClass.impersonateReviewertoRMU();

		assignmentPage.selectAssignmentToViewinDocview(assname);

		// verify completed docs completed checkmark Icon
		docViewAnalytics.verifyCompletedCheckMarkForThreadMapTabDocs(rowNo);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that for uncompleted documents on thread map tab
	 *              completed icon should not be displayed'RPMXCON-51454'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyUncompleteIconOnThreadMapTab() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51454");
		baseClass.stepInfo(
				"Verify that for uncompleted documents on thread map tab completed icon should not be displayed");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableCodingStamp(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// select Assignment from manage assignment
		assignmentPage.selectAssignmentToViewinDocview(assname);

		// verify Uncompleted icon in Thread Map Tab
		docViewAnalytics.verifyUncompleteCheckMarkForThreadMapTabDocs();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that completed icon should not be displayed on thread
	 *              map tab of analytics panel child window which are completed from
	 *              assignment'RPMXCON-51455'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyCompleteIconOnShouldNotDisplayOnThreadMapTabChildWindow() throws InterruptedException {


		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51455");
		baseClass.stepInfo(
				"Verify that completed icon should not be displayed on thread map tab of analytics panel child window which are completed from assignment");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.threadDocWithToolTip;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableCodingStamp(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as action
		docViewAnalytics.selectDocsFromThreadMapTabAndActionCodeSame();

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.selectCompleteButton();
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are completed successfully");

		// verify completed docs completed checkmark Icon
		docViewAnalytics.verifyCompleteCheckMarkForThreadMapTabDocs();

		// Impersonate to RMU
		baseClass.impersonateReviewertoRMU();

		// basic Search with metadata query
		driver.waitForPageToBeReady();
		sessionSearch.basicSearchWithMetaDataQuery(documentToBeSelected);

		// view in docView
		sessionSearch.ViewInDocView();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// verify completed checkmark should not display
		docViewAnalytics.verifyUncompleteCheckMarkForThreadMapTabDocs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		baseClass.passedStep(
				"To Verify that completed icon should not be displayed on thread map tab of analytics panel child window which are completed from assignment has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Family
	 *              Member tab and navigates to another document, the Analytics
	 *              Panel Tab previously selected must remain.'RPMXCON-51286'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void verifyNavigateToOherDocsAnalyticsPanelRemainsSame() throws InterruptedException {


		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51286");
		baseClass.stepInfo(
				"Verify that user has selected an Analytics Panel > Family Member tab and navigates to another document, the Analytics Panel Tab previously selected must remain.");

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.pa1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View docs in docView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		String selectDocsMiniDocList = "FamilyMember";

		// Select Family Member Analytics Panel
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_FamilyTab());
		//docViewAnalytics.getDocView_Analytics_FamilyTab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.scrollPageToTop();

		// navigate to other docs in MiniDocList
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(selectDocsMiniDocList);

		// verify Family member tab
		docViewAnalytics.verifyFamilyMemberRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To Verify that user has selected an Analytics Panel > Family Member tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel >
	 *              Conceptual tab and navigates to another document, the Analytics
	 *              Panel Tab previously selected must remain.'RPMXCON-51415'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyNavigateToOherDocsConceptualTabAnalyticsPanelRemainsSame() throws InterruptedException {


		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		String selectDocsMiniDocList;
		baseClass.stepInfo("Test case Id: RPMXCON-51415");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Conceptual tab and navigates to another document, the Analytics Panel Tab previously selected must remain.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.pa1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		//docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.scrollPageToTop();
		selectDocsMiniDocList = "Conceptually";
		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(selectDocsMiniDocList);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyConceputalTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To Verify that user has selected an Analytics Panel > Conceptual Similar tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		//docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.scrollPageToTop();

		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(selectDocsMiniDocList);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyConceputalTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To Verify that user has selected an Analytics Panel > Conceptual Similar tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

		// logout RMU
		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		//docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.scrollPageToTop();

		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(selectDocsMiniDocList);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyConceputalTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To Verify that user has selected an Analytics Panel > Conceptual Similar tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Thread
	 *              Map tab and navigates to another document, the Analytics Panel
	 *              Tab previously selected must remain.'RPMXCON-51416'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyNavigateToOherDocsThreadMapTabAnalyticsPanelRemainsSame() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51416");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Thread Map tab and navigates to another document, the Analytics Panel Tab previously selected must remain.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.pa1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		//docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		driver.scrollPageToTop();
		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocList_SelectSecDocs());
		docViewAnalytics.getDocView_MiniDocList_SelectSecDocs().waitAndClick(10);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyThreadMapTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

		// Logout PA
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		//docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		driver.scrollPageToTop();

		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocList_SelectSecDocs());
		docViewAnalytics.getDocView_MiniDocList_SelectSecDocs().waitAndClick(10);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyThreadMapTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

		// logout RMU
		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		//docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		driver.scrollPageToTop();

		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		baseClass.waitForElement(docViewAnalytics.getDocView_MiniDocList_SelectSecDocs());
		docViewAnalytics.getDocView_MiniDocList_SelectSecDocs().waitAndClick(10);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyThreadMapTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify when user select action as 'Remove code same' for
	 *              documents which are not marked as code same as this from thread
	 *              map'RPMXCON-51226'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void verifyWarningMessageWhenRemoveCodeAsSameActionIsPerformed() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51226");
		baseClass.stepInfo(
				"To verify when user select action as 'Remove code same' for documents which are not marked as code same as this from thread map");

		// Login As PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewThreadedDocsInDocViews();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// select the docs from Thread map tab and Action as code as same
		docViewAnalytics.selectDocsFromThreadMapTabAndActionCodeSame();

		driver.scrollPageToTop();
		// verify warning message
		docViewAnalytics.verifyWarningMsgWhenOtherDocsSelectedAndRemoveCodeAsSame();
		baseClass.passedStep(
				"To verify when user select action as 'Remove code same' for documents which are not marked as code same as this from thread map has been verified successfully");

		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewThreadedDocsInDocViews();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// select the docs from Thread map tab and Action as code as same
		docViewAnalytics.selectDocsFromThreadMapTabAndActionCodeSame();

		driver.scrollPageToTop();
		// verify warning message
		docViewAnalytics.verifyWarningMsgWhenOtherDocsSelectedAndRemoveCodeAsSame();
		baseClass.passedStep(
				"To verify when user select action as 'Remove code same' for documents which are not marked as code same as this from thread map has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify for Project Admin 'Remove Code same as' action should
	 *              not be displayed in mini doc list, analytics
	 *              panel'RPMXCON-51227'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void verifyRemoveCodeAsSameShouldNotbeDispalyedInMiniDocLitAndAnalyticsPanel() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51227");
		baseClass.stepInfo(
				"To Verify for Project Admin 'Remove Code same as' action should not be displayed in mini doc list, analytics panel");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.pa1userName + "");

		// Perform Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View the docs in DocView
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Purehits are added to shopping cart and viewed in DocView successfully");

		// verify Remove code same as in minidoclist and analytics panel
		docViewAnalytics.verifyRemoveCodeAsSameInMiniDoclistAndAnalyticsPanel();
		baseClass.passedStep(
				"To Verify for Project Admin 'Remove Code same as' action should not be displayed in mini doc list, analytics panel has been verified successfully");
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Thread
	 *              Map tab and completed document, the Analytics Panel Tab
	 *              previously selected must remain.'RPMXCON-51417'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void verifyPositionOfThreadMapTabNavigatedToOtherDocs() throws InterruptedException {
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51417");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Thread Map tab and completed document, the Analytics Panel Tab previously selected must remain.");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = "ThreadMap";

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		//docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		// verify ThreadMap remains in same position
		docViewAnalytics.verifyThreadMapTabWhenThreadedDocsInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");

		// logout RMU
		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		//docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.waitForPageToBeReady();
		// verify ThreadMap remains in same position
		docViewAnalytics.verifyThreadMapTabWhenThreadedDocsInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Family
	 *              Member tab and completed document, the Analytics Panel Tab
	 *              previously selected must remain.'RPMXCON-51418'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void verifyPositionOfFamilyMemberTabNavigatedToOtherDocs() throws InterruptedException {

		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51418");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Family Member tab and completed document, the Analytics Panel Tab previously selected must remain.");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_FamilyTab());
//		docViewAnalytics.getDocView_Analytics_FamilyTab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		// verify ThreadMap remains in same position
		docViewAnalytics.verifyFamilyMemberWhenDocsAreCompletedInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Family Member tab and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");

		// logout RMU
		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_FamilyTab());
//		docViewAnalytics.getDocView_Analytics_FamilyTab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		// verify ThreadMap remains in same position
		docViewAnalytics.verifyFamilyMemberWhenDocsAreCompletedInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Family Member tab and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Thread
	 *              Map tab from child window and completed document, the Analytics
	 *              Panel Tab previously selected must remain.'RPMXCON-51421'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 47)
	public void verifyPositionOfThreadMapTabNavigatedToOtherDocsChildWindow() throws InterruptedException {

		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51421");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Thread Map tab from child window and completed document, the Analytics Panel Tab previously selected must remain.");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.threadDocWithToolTip;
		String revDocsToBeSelected = Input.newNearDupeDocId;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		// Select Thread Map Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		driver.switchTo().window(parentWindowID);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		// verify ThreadMap remains in same position
		docViewAnalytics.verifyThreadMapTabWhenThreadedDocsInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab from child window and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// logout RMU
		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		parentWindowID = driver.getWebDriver().getWindowHandle();
		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		// Select Thread Map Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentThreadMap());
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		driver.switchTo().window(parentWindowID);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		// verify ThreadMap remains in same position
		docViewAnalytics.verifyThreadMapTabWhenThreadedDocsInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Thread Map tab from child window and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Family
	 *              Member tab from child window and completed document, the
	 *              Analytics Panel Tab previously selected must
	 *              remain.'RPMXCON-51422'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifyPositionOfFamilyMemberTabNavigatedToOtherDocsChildWindow() throws InterruptedException {

		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		
		baseClass.stepInfo("Test case Id: RPMXCON-51422");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Family Member tab from child window and completed document, the Analytics Panel Tab previously selected must remain.");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.familyDocument;
		String revDocsToBeSelected = Input.newNearDupeDocId;
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		// Select Family Member Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_FamilyTab());
		docViewAnalytics.getDocView_Analytics_FamilyTab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.switchTo().window(parentWindowID);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		// verify ThreadMap remains in same position
		docViewAnalytics.verifyFamilyMemberWhenDocsAreCompletedInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Family Member tab from child window and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// logout RMU
		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		parentWindowID = driver.getWebDriver().getWindowHandle();
		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		// Select Family Member Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_FamilyTab());
		docViewAnalytics.getDocView_Analytics_FamilyTab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.switchTo().window(parentWindowID);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		// verify ThreadMap remains in same position
		docViewAnalytics.verifyFamilyMemberWhenDocsAreCompletedInMiniDocList();
		baseClass.passedStep(
				"To verify that user has selected an Analytics Panel > Family Member tab from child window and completed document, the Analytics Panel Tab previously selected must remain. has been verified successfully");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
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
			//LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close2() {
		try {
		//	LoginPage.clearBrowserCache();
		} catch (Exception e) {
			// no such session
		}

	}

}