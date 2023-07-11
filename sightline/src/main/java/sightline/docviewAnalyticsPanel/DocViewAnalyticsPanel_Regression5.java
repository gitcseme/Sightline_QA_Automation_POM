package sightline.docviewAnalyticsPanel;

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
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAnalyticsPanel_Regression5 {

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
	IngestionPage_Indium ingestionPage;
	DataSets dataset;

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
		ingestionPage = new IngestionPage_Indium(driver);
	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the thread map toggle is enabled, the
	 *              length of the email thread should not be a constraint and with
	 *              email threads such as the above (23 mails and 140 participants)
	 *              from Analytics panel child window 'RPMXCON-51846'
	 * @Stabilization - not done[PT Env]
	 */
	@Test(enabled = true,description ="RPMXCON-51846", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that when document is viewed from analytics panel when
	 *              hits panel is open then enable/disable should be retained
	 *              'RPMXCON-51753'
	 *  @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51753", groups = { "regression" })
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
        sessionSearch.viewInDocView();
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
		loginPage.logout();

	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that when document is viewed from analytics panel
	 *              child window when hits panel is open then enable/disable should
	 *              be retained
	 * //@TestCase 'RPMXCON-51754' Document is viewed from 'analytics panel' when hits
	 *           panel is open with pop-out child window
	 *  @Stabilization - done         
	 */
	@Test(enabled = true,description ="RPMXCON-51754", groups = { "regression" })
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
        sessionSearch.viewInDocView();
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
		loginPage.logout();
	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from Analytics
	 *              panel parent window on click of the link to load more data
	 *              'RPMXCON-51723'
	 *   @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51723", groups = { "regression" })
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

		String text = Input.MetaDataId;

		// Search the query
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc is searched and navigated to the DocView successfully");

		// Doc Viewed in Analytics Panel
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
		docViewAnalytics.verifyHorizontalTab(text);
		baseClass.stepInfo("Doc is selected and viewed in the DocView Analytics Panel successfully");
		loginPage.logout();
	}

	/**
	 * 
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To Verify that Thread Map tab should be displayed when
	 *              navigating to doc view outside of assignment 'RPMXCON-51847'
	 *  @Stabilization - done             
	 */
	@Test(enabled = true,description ="RPMXCON-51847", groups = { "regression" })
	public void verifyDocumentFromAnalyticsPanelWithThreadMap()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51847");
		loginPage = new LoginPage(driver);
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Review Manager with " + Input.rmu1userName + "");
		String text = Input.MetaDataId;
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("*****Doc is searched from basic search and viewed in the DocView page successfully*****");
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
		docViewAnalytics.selectDocumentFromAnalyticPanel(text);
		UtilityLog.info("*****Thread Map tab is displayed when navigating to doc view outside of assignment*****");
		baseClass.stepInfo("*****Thread Map tab is displayed when navigating to doc view outside of assignment*****");
		String doc = docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().getText();
		softAssertion.assertTrue(true, doc);
		baseClass.passedStep(
				"Verify that Thread Map tab should be displayed when navigating to doc view outside of assignment is successfully");
		loginPage.logout();
	}


	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To Verify that when the threadmap toggle is enabled, the length
	 *              of the email thread should not be a constraint and with email
	 *              threads such as the above (23 mails and 140 participants) from
	 *              Analytics panel 'RPMXCON-51845'
	 *  @Stabilization - done
	 * 
	 */
	@Test(enabled = true,description ="RPMXCON-51845", groups = { "regression" })
	public void verifyAnalyticsPanelWithEmailThreadMapEnable()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51845");
		String assignmentName = "assignmentB1" + Utility.dynamicNameAppender();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as Review Manager Admin with "
				+ Input.rmu1userName + "");

		docViewAnalytics = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentName, Input.codeFormName,
				SessionSearch.pureHit);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");

		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewAnalytics.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		if (docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().isDisplayed()) {
			baseClass.passedStep("Thread Map toggled is Enable and viewed from Analytic Child window successfully");
		}else {
			baseClass.failedStep("Thread Map toggled is Enable and viewed from Analytic Child window successfully");
		}
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in successfully with Reviewer credentials");

		docViewAnalytics.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewAnalytics.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		if (docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().isDisplayed()) {
			baseClass.passedStep("Thread Map toggled is Enable and viewed from Analytic Child window successfully");
		}else {
			baseClass.failedStep("Thread Map toggled is Enable and viewed from Analytic Child window successfully");
		}
		baseClass.passedStep(
				"To Verify that when the threadmap toggle is enabled, the length of the email thread should not be a constraint and with email threads such as the above (23 mails and 140 participants) from Analytics panel is succesfully verifed");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the threadmap toggle is disabled, the
	 *              application should not try to pull the thread information on doc
	 *              view'RPMXCON-51843'
	 *  @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51843", groups = { "regression" })
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
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify that when the threadmap toggle is disabled, the
	 *              application should not try to pull the thread information on doc
	 *              view from Analytics panel child window'RPMXCON-51844'
	 *  @Stabilization - done
     */

	@Test(enabled = true,description ="RPMXCON-51844", groups = { "regression" })
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
		loginPage.logout();
	}

	
	/**
	 * @throws Exception
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify code same icon should not display for the documents
	 *              selected for folder action from mini doc list after selecting
	 *              folder action from analytics panel.'RPMXCON-51735 '
	 *   @Stabilization - done            
	 */
	@Test(enabled = true,description ="RPMXCON-51735", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify documents folder action is done successfully from
	 *              analytics panel.'RPMXCON-51733'
	 *  @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51733", groups = { "regression" })
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
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Doc are selected and viewed in the DocView successfully");
		// Create a folder in Doc Analytics
		docViewAnalytics.Analytics_FamilyActionsFolderCreation();
		sessionSearch.BulkActions_Folder(analyticsFolderName);
		baseClass.stepInfo("Doc is selected from Family Member and Folder is created successfully");

		// Verifying Code as Same Icon
		docViewAnalytics.verifyCodeAsSameInMiniDocList();
		baseClass.stepInfo("Doc is selected from Mini Doclist and Code As Same is verified successfully");
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify Thread Map tab when no documents/thread to
	 *              display.'RPMXCON-50952'
	 * @throws InterruptedException
	 *  @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-50952", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify 'View All' should be displayed on Thread Map tab
	 *              when more than 20 documents exists.'RPMXCON-50953'
	 * @throws InterruptedException
	 *  @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-50953", groups = { "regression" })
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

		String text = Input.MetaDataId;

		// verify 20 plus docs in the thread map tab
		docViewAnalytics = new DocViewPage(driver);
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
		docViewAnalytics.verifyThreadMapWith20Docs(text);
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify user should be able to view threaded documents when
	 *              redirects from saved search page.'RPMXCON-50954'
	 * @throws InterruptedException
	 *  @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-50954", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify user should be able to view threaded documents when
	 *              redirects from doc list page.'RPMXCON-50955'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-50955", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : Verify option to view document from thread map tab of
	 *              analytics panel.'RPMXCON-51413'
	 * @throws InterruptedException
	 * @Stabilization - not done
	 */
	@Test(enabled = true,description ="RPMXCON-51413", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Near Dupe
	 *              tab and navigates to another document, the Analytics Panel Tab
	 *              previously selected must remain..'RPMXCON-51414'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51414", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that thread map presents only emails when threaded
	 *              document contains non email attachment'RPMXCON-51508'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51508", groups = { "regression" })
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

		String text = Input.nearDupeBulkAssign;

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
		loginPage.logout();
	}
	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right after changing
	 *              the Analytics panel position'RPMXCON-51721'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51721", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from child window
	 *              after changing the Analytics panel position'RPMXCON-51722'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51722", groups = { "regression" })
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
		//docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from Analytics
	 *              panel parent window'RPMXCON-51719'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51719", groups = { "regression" })
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
		//docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");
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
		//docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");
		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);

		// Logout RMU
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic search to Docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// select doc from mini dockist and verify Horizontal tab
		docViewAnalytics.checkingDocsAndVerifyHorizontalScrollBar(documentToBeScrolled);
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify the horizontal scroll bar should not reset when document
	 *              is selected from thread map on moving to right from Analytics
	 *              panel child window'RPMXCON-51720'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51720", groups = { "regression" })
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
		loginPage.logout();
	}


	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify warning message should be displayed when completed
	 *              documents are selected for code same action from Analytics
	 *              panel'RPMXCON-51411'
	 * @throws InterruptedException
	 * @Stabilization - not done
	 */
	@Test(enabled = true,description ="RPMXCON-51411", groups = { "regression" })
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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
		docViewAnalytics.selectDocsFromMiniDocListCompletedDoc();

		// verify warning message when select the same doc
		docViewAnalytics.selectDocInMiniDocList(Input.warning01);
		docViewAnalytics.verifyWarningMessageCodeAsSameIsSelectedCompletedDocs();
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify warning message should be displayed when completed
	 *              documents are selected for code same action from Analytics panel
	 *              child window'RPMXCON-51412'
	 * @throws InterruptedException
	 * @Stabilization - not done
	 */
	@Test(enabled = true,description ="RPMXCON-51412", groups = { "regression" })
	public void verifyWarningMessageCodeAsSameInAnalyticsPanelWithChildWindow() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51412");
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		// Login RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Basic search and bulk assign
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");
		docViewAnalytics.selectDocsFromMiniDocListCompletedDoc();

		// Select Gear Icon
		docViewAnalytics.selectGearIcon();

		// verify warning message when select the same doc
		docViewAnalytics.selectDocInMiniDocList(Input.warning01);
		docViewAnalytics.verifyWarningMessageWithChildWindow();
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user can select any document and details is
	 *              displayed in Analytical and Meta data Panel.'RPMXCON-50960'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-50960", groups = { "regression" })
	public void verifyMetaDataPanelIsDisplayed() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50960");

		// Login As PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify Meta Data Panel is displayed in DocView
		baseClass.stepInfo("To verify MetaData Panel in DocView");
		docViewAnalytics.verifyMetaDataPanelInDocView();
		driver.waitForPageToBeReady();
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
		driver.waitForPageToBeReady();
		
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
		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Conceptually Similar child window and Save/Complete clicked from
	 *              coding form child window'RPMXCON-51385'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51385", groups = { "regression" })
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

		String documnetToBeSelected = Input.conceptualDocumentReviewer;
		String documentToBeSelectedForReviewer = Input.conceptualDocs1;

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

		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");

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
		docViewAnalytics.selectDocInMiniDocList(documnetToBeSelected);
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

		if (docViewAnalytics.getCodeSameIconMiniDocList().isDisplayed()) {

			baseClass.waitForElement(docViewAnalytics.getCodeSameIconMiniDocList());
			softAssertion.assertEquals(docViewAnalytics.getCodeSameIconMiniDocList().Displayed().booleanValue(), true,

					"Scenario 1");
		}

		driver.scrollPageToTop();
		docViewAnalytics.getSaveIcon().Click();

		// popout Coding Form and complete the docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from MiniDocList
		if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {

			softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
					true, "Scenario 2");
			baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		}

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
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");

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
		docViewAnalytics.selectDocInMiniDocList(documentToBeSelectedForReviewer);
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

		if (docViewAnalytics.getCodeSameIconMiniDocList().isDisplayed()) {
			baseClass.waitForElement(docViewAnalytics.getCodeSameIconMiniDocList());
			softAssertion.assertEquals(docViewAnalytics.getCodeSameIconMiniDocList().Displayed().booleanValue(), true);
		}

		driver.scrollPageToTop();
		docViewAnalytics.getSaveIcon().Click();

		// popout Coding Form and complete Docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from miniDoclist
		if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {

			softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
					true);
			baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		}

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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Conceptually Similar and Save/Complete clicked from coding form
	 *              child window'RPMXCON-51383'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51383", groups = { "regression" })
	public void verifyCodeAsSameWithCodingFormChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51383");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();

		String documnetToBeSelected = Input.conceptualDocumentReviewer;
		String documentToBeSelectedForReviewer = Input.conceptualDocs1;

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
		docViewAnalytics.selectDocIdInMiniDocList(documnetToBeSelected);
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
		docViewAnalytics.selectDocIdInMiniDocList(documnetToBeSelected);
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Conceptually Similar and Save/Complete clicked from coding form
	 *              parent window'RPMXCON-51382'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51382", groups = { "regression" })
	public void verifyCodeAsSameConceptuallySimilarParentWindow() throws InterruptedException {

		loginPage = new LoginPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51382");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Conceptually Similar and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocumentReviewer;
		String documentToBeSelectedForReviewer = Input.conceptualDocs1;

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
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that on thread map tab when the principal document is
	 *              F2, the thread map should not present any emails.'RPMXCON-51522'
	 * @throws InterruptedException
	 * @Stabilization - not done
	 */
	@Test(enabled = true,description ="RPMXCON-51522", groups = { "regression" })
	public void verifyThreadMapTabWithPrincipalDocs() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		DataSets dataset = new DataSets(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51522");

		String sourceId = Input.sourceDocId;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		dataset.navigateToDataSetsPage();
		String ingestionDataName=ingestionPage.getPublishedIngestionName(Input.EmailConcatenatedDataFolder);
		
		dataset.selectDataSetWithNameInDocView(ingestionDataName);
		// Basic search to Docview
		//sessionSearch.basicSearchWithMetaDataQuery(Input.ingestionQuery01, "IngestionName");
		//sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		docViewAnalytics.selectDocIdInMiniDocList(sourceId);

		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs();

		baseClass.passedStep(
				"To Verify that on thread map tab when the principal document is F2, the thread map should not present any emails is verified successfully");
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that on thread map tab when the principal document is
	 *              E2, the thread map presents E1, E2, E3'RPMXCON-51518'
	 * @throws InterruptedException
	 * @Stabilization - not done
	 */
	@Test(enabled = true,description ="RPMXCON-51518", groups = { "regression" })
	public void verifyThreadMapPrincipalDocsE2() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		DataSets dataset = new DataSets(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51518");

		String sourceId = Input.sourceDocId3;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		dataset.navigateToDataSetsPage();
		String ingestionDataName=ingestionPage.getPublishedIngestionName(Input.EmailConcatenatedDataFolder);
		dataset.selectDataSetWithNameInDocView(ingestionDataName);
		//sessionSearch.basicSearchWithMetaDataQuery(Input.ingestionQuery01, "IngestionName");
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");
		//docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");

		docViewAnalytics.selectDocIdInMiniDocList(sourceId);


		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs();

		baseClass.passedStep(
				"To Verify that on thread map tab when the principal document is E2, the thread map presents E1, E2, E3 is verified successfully");
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that on thread map tab when the principal document is
	 *              E3, the thread map presents E1, E2, E3'RPMXCON-51519'
	 * @throws InterruptedException
	 * @Stabilization - not done
	 */
	@Test(enabled = true,description ="RPMXCON-51519", groups = { "regression" })
	public void verifyThreadMapPrincipalDocsE3() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		DataSets dataset = new DataSets(driver);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51519");

		String sourceId = Input.sourceDocId1;
		String sourceId2 = Input.sourceDocId2;

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully Logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic search to Docview
		dataset.navigateToDataSetsPage();
		String ingestionDataName=ingestionPage.getPublishedIngestionName(Input.EmailConcatenatedDataFolder);
		dataset.selectDataSetWithNameInDocView(ingestionDataName);
		//sessionSearch.basicSearchWithMetaDataQuery(Input.ingestionQuery01, "IngestionName");
		//sessionSearch.ViewInDocView();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		

		// view the doc From MiniDocList and ThreadMap Tab
		//docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");
		docViewAnalytics.selectDocIdInMiniDocList(sourceId);
		baseClass.stepInfo("Docs Selected from Mini doclist with succesfully");
		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs();

		driver.scrollPageToTop();
		// view the doc From MiniDocList and ThreadMap Tab
		//docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");

		docViewAnalytics.selectDocInMiniDocList(sourceId2);
		baseClass.stepInfo("Docs Selected from Mini doclist succesfully");
		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithDocs();

		baseClass.passedStep(
				"To Verify that on thread map tab when the principal document is E3, the thread map presents E1, E2, E3 is verified successfully");
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when 'View Document' action is selected from thread map
	 *              when multiple documents are selected'RPMXCON-51428'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51428", groups = { "regression" })
	public void verifyViewDocumentInThreadMapMutiDocsAreSelected() throws InterruptedException {

		loginPage = new LoginPage(driver);
		docViewAnalytics = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51428");

		String docId = Input.threadDocWithToolTip;

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
		docViewAnalytics.selectDocIdInMiniDocList(docId);

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
		docViewAnalytics.selectDocIdInMiniDocList(docId);

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
		docViewAnalytics.selectDocIdInMiniDocList(docId);

		// verify Error Msg when multi docs are selected from Thread map tab
		docViewAnalytics.verifyErrorMsgOnMultiSelectedDocsInThreadMap();
		baseClass.passedStep(
				"To Verify when 'View Document' action is selected from thread map when multiple documents are selected is verified successfully");
		loginPage.logout();

	}


	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify user select Multiple documents in Thread Map->Analytic
	 *              Panel and Select Action as 'Code Same as this' and code same
	 *              icon should be displayed'RPMXCON-51239'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51239", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when user select 'Folder' action from thread map without
	 *              selecting document'RPMXCON-51241'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51241", groups = { "regression" })
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
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when no document to display on thread map all actions
	 *              should be disable'RPMXCON-51242'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51242", groups = { "regression" })
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
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify user select multiple documents in Thread Map->Analytic
	 *              Panel and Select Action as 'Folder''RPMXCON-51240'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51240", groups = { "regression" })
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
		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              thread map and Save/Complete clicked from coding form parent
	 *              window'RPMXCON-51370'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51370", groups = { "regression" })
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
		docViewAnalytics.selectThreadMapPureHit();
		sessionSearch.bulkAssign();
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
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("Conceptually");
		// Select Docid from MiniDocList
		//docViewAnalytics.selectDocIdInMiniDocList(Input.conceptualDocumentReviewer);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForThreadedDocumentsForReviewer();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// edit coding form and Save
		docViewAnalytics.editCodingFormCompleteForThreadDocsForReviewer();
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that completed icon should be displayed for documents on
	 *              thread map tab when RMU redirect to doc view from manage
	 *              assignment'RPMXCON-51453'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51453", groups = { "regression" })
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
		String documentToBeSelected = Input.threadDocWithToolTip;
		int rowNo = 4;

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
		docViewAnalytics.selectSourceDocIdInCompletedDocs();
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		docViewAnalytics.verifyCompletedCheckMarkForThreadMapTabDocs(rowNo);
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that for uncompleted documents on thread map tab
	 *              completed icon should not be displayed'RPMXCON-51454'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51454", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that completed icon should not be displayed on thread
	 *              map tab of analytics panel child window which are completed from
	 *              assignment'RPMXCON-51455'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51455", groups = { "regression" })
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
		String selectDocsMiniDocList = Input.threadDocId;

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
		docViewAnalytics.selectDocIdInMiniDocList(selectDocsMiniDocList);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as action
		docViewAnalytics.selectDocsFromThreadMapTabAndActionCodeSame();

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.selectCompleteButton();
		docViewAnalytics.selectDocIdInMiniDocList(selectDocsMiniDocList);
		baseClass.stepInfo("Docs are completed successfully");

		// verify completed docs completed checkmark Icon
		docViewAnalytics.verifyCompleteCheckMarkForThreadMapTabDocs(2);

		// Impersonate to RMU
		baseClass.impersonateReviewertoRMU();

		// basic Search with metadata query
		driver.waitForPageToBeReady();
		sessionSearch.basicSearchWithMetaDataQueryUsingSourceDOCID(selectDocsMiniDocList);

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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Family
	 *              Member tab and navigates to another document, the Analytics
	 *              Panel Tab previously selected must remain.'RPMXCON-51286'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51286", groups = { "regression" })
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

		String selectDocsMiniDocList = Input.sourceDocId1;

		driver.waitForPageToBeReady();

		// navigate to other docs in MiniDocList
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocIdInMiniDocList(selectDocsMiniDocList);

		// Select Family Member Analytics Panel
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_FamilyTab());
		// docViewAnalytics.getDocView_Analytics_FamilyTab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.scrollPageToTop();

		selectDocsMiniDocList = Input.familyDocumentForReviewer;

		// navigate to other docs in MiniDocList
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocInMiniDocList(selectDocsMiniDocList);

		// verify Family member tab
		docViewAnalytics.verifyFamilyMemberRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To Verify that user has selected an Analytics Panel > Family Member tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel >
	 *              Conceptual tab and navigates to another document, the Analytics
	 *              Panel Tab previously selected must remain.'RPMXCON-51415'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51415", groups = { "regression" })
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

		selectDocsMiniDocList = Input.sourceDocId1;

		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocIdInMiniDocList(selectDocsMiniDocList);

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		// docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.scrollPageToTop();
		selectDocsMiniDocList = Input.familyDocumentForReviewer;
		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocInMiniDocList(selectDocsMiniDocList);

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

		selectDocsMiniDocList = Input.sourceDocId1;

		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocIdInMiniDocList(selectDocsMiniDocList);

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		// docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.scrollPageToTop();

		selectDocsMiniDocList = Input.familyDocumentForReviewer;

		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocInMiniDocList(selectDocsMiniDocList);

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

		selectDocsMiniDocList = Input.sourceDocId1;

		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocIdInMiniDocList(selectDocsMiniDocList);

		// Select Conceptually Similar Tab
		baseClass.waitForElement(docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab());
		// docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.scrollPageToTop();

		selectDocsMiniDocList = Input.familyDocumentForReviewer;

		// Navigate to other docs in MiniDoclist
		baseClass.stepInfo("Navigates to other document from Mini doc list.");
		docViewAnalytics.selectDocInMiniDocList(selectDocsMiniDocList);

		// verify the tab which is to remains in the same position
		docViewAnalytics.verifyConceputalTabRemainsSameNavigatedAlso();
		baseClass.passedStep(
				"To Verify that user has selected an Analytics Panel > Conceptual Similar tab and navigates to another document, the Analytics Panel Tab previously selected must remain has been verified successfully");
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Thread
	 *              Map tab and navigates to another document, the Analytics Panel
	 *              Tab previously selected must remain.'RPMXCON-51416'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51416", groups = { "regression" })
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
		// docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
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
		// docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
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
		// docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify when user select action as 'Remove code same' for
	 *              documents which are not marked as code same as this from thread
	 *              map'RPMXCON-51226'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51226", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify for Project Admin 'Remove Code same as' action should
	 *              not be displayed in mini doc list, analytics
	 *              panel'RPMXCON-51227'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51227", groups = { "regression" })
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
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Thread
	 *              Map tab and completed document, the Analytics Panel Tab
	 *              previously selected must remain.'RPMXCON-51417'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51417", groups = { "regression" })
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
		String documentToBeSelected = Input.threadDocId;

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
		// docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");
		driver.waitForPageToBeReady();

		// verify ThreadMap remains in same position
		if (docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
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
		// docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("ThreadMap");
		//docViewAnalytics.selectDocIdInMiniDocList(Input.nearDupeBulkAssignReviewId);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.scrollPageToTop();
		// complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Docs are completed successfully");

		driver.waitForPageToBeReady();
		// verify ThreadMap remains in same position
		if (docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}

		loginPage.logout();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Family
	 *              Member tab and completed document, the Analytics Panel Tab
	 *              previously selected must remain.'RPMXCON-51418'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51418", groups = { "regression" })
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

		if (docViewAnalytics.getDocView_Analytics_FamilyTab().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
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

		if (docViewAnalytics.getDocView_Analytics_FamilyTab().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Thread
	 *              Map tab from child window and completed document, the Analytics
	 *              Panel Tab previously selected must remain.'RPMXCON-51421'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51421", groups = { "regression" })
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
		String documentToBeSelected = Input.threadDocId;
		String revDocsToBeSelected = Input.nearDupeBulkAssignReviewId;

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

		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");

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
		docViewAnalytics.selectDocInMiniDocList(documentToBeSelected);
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
		if (docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
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
		
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");

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
		docViewAnalytics.selectDocInMiniDocList(revDocsToBeSelected);
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
		if (docViewAnalytics.getDocView_Analytics_liDocumentThreadMap().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :To verify that user has selected an Analytics Panel > Family
	 *              Member tab from child window and completed document, the
	 *              Analytics Panel Tab previously selected must
	 *              remain.'RPMXCON-51422'
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true,description ="RPMXCON-51422", groups = { "regression" })
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
		
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");

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
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("FamilyMember");
		//docViewAnalytics.selectDocInMiniDocList(documentToBeSelected);
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
		
		if (docViewAnalytics.getDocView_Analytics_FamilyTab().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
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
		
		docViewAnalytics.selectSourceDocIdInAvailableField("SourceDocID");

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
		docViewAnalytics.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel("NearDupe");
		//docViewAnalytics.selectDocInMiniDocList(revDocsToBeSelected);
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
		if (docViewAnalytics.getDocView_Analytics_FamilyTab().isDisplayed()) {
			baseClass.passedStep("Analytical tab remains same after completing document");
		} else {
			baseClass.failedStep("Analytical Panel tab is changed after completing document");
		}
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			// LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close2() {
		try {
			// LoginPage.clearBrowserCache();
		} catch (Exception e) {
			// no such session
		}

	}

}