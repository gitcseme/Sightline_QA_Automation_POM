package sightline.docviewAnalyticsPanel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAnalyticsPanel_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewRedactions docViewRedact;
	ReusableDocViewPage reusableDocViewPage;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagsAndFolderPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
	}

	/**
	 * Author : Mohan A date: 24/11/21 NA Modified date: NA Modified by: N/A
	 * Description : DataProvider for Different User Login
	 */
	@DataProvider(name = "userDetailss")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "rev" },
				{ "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "specificUsers")
	public Object[][] userLoginWithSpecificCredentials() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password }, };
	}

	@DataProvider(name = "multiUsers")
	public Object[][] userLoginWithMultiCredentials() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author : Mohan date: 24/11/21 NA Modified date: NA Modified by:NA Description
	 * :Verify tool tip should be displayed for each column on thread
	 * map'RPMXCON-51358' Sprint : 6 Stabilized - done
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(description="RPMXCON-51358",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifyToolTipDocsTextInThreadMapTab(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51358");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo("Verify tool tip should be displayed for each column on thread map");

		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String docId = "DocID:";
		String author = "Author:";
		String subject = "Subject:";
		String totalRecepitent = "Total Recipient Count:";
		String sentDate = "Sent Date:";
		String inclusiveMail = "Inclusive Email:";
		String sentType = "Sent Type:";
		String threadSequence = "Thread Sequence ID:";
		String docsToBeSelected = Input.threadDocId;

		docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);

		docView.verifyThreadDocsDisplayDocsInToolTips(docId);
		docView.verifyThreadDocsDisplayDocsInToolTips(author);
		docView.verifyThreadDocsDisplayDocsInToolTips(subject);
		docView.verifyThreadDocsDisplayDocsInToolTips(totalRecepitent);
		docView.verifyThreadDocsDisplayDocsInToolTips(sentDate);
		docView.verifyThreadDocsDisplayDocsInToolTips(inclusiveMail);
		docView.verifyThreadDocsDisplayDocsInToolTips(sentType);
		docView.verifyThreadDocsDisplayDocsInToolTips(threadSequence);

		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 24/11/2021 Modified date: NA Modified by: NA
	 * @Description : Verify system doc id is displayed in near dupe child window
	 *              RPMXCON-51257 Stabilized - done
	 */
	@Test(description="RPMXCON-51257",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifySystemDocIdDisplayedInNearDupeChildWindow(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51257");
		baseClass.stepInfo("Verify system doc id is displayed in near dupe child window");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docView.selectNearDupePureHit();

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docView.popOutAnalyticsPanel();

		// open Comparison window in NearDupes Tab

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		docView.openNearDupeComparisonWindow();
		docView.getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
		baseClass.passedStep("" + docidinchildwinodw + "is present in NearDupe window");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani date: 24/11/21 NA Modified date: NA Modified by:NA
	 *         Description :Verify the inclusive email docs should be identified on
	 *         thread map panel'RPMXCON-51357' Sprint : 6
	 * @throws InterruptedException Stabilized - done
	 */
	@Test(description="RPMXCON-51357",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void VerifyInclusiveEmailDocsOnThreadMapPanel(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51357");
		baseClass.stepInfo("Verify the inclusive email docs should be identified on thread map panel");
		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String documentToBeSelected = Input.threadDocId;
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Docid from MiniDocList

		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		baseClass.stepInfo("For inclusive email docs -I should be displayed on thread map tab of analytics panel");
		docView.verifyThreadDocsDisplayDocsIrrespectiveOfInclusiveEmailValue();

		String docId = docView.getdocIdText().getText();
		if (docId.contains("-I")) {
			baseClass.passedStep(
					"For inclusive email docs -I is displayed on thread map tab of analytics panel successfully");
		} else {
			baseClass.failedStep("-I is not displayed on thread map tab of analytics panel");
		}

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("For inclusive email docs -I should be displayed on thread map tab of analytics panel");
		docView.verifyThreadDocsDisplayDocsIrrespectiveOfInclusiveEmailValue();

		if (docId.contains("-I")) {
			baseClass.passedStep(
					"For inclusive email docs -I is displayed on thread map tab of analytics panel successfully");
		} else {
			baseClass.failedStep("-I is not displayed on thread map tab of analytics panel");
		}
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani date: 24/11/21 NA Modified date: NA Modified by:NA
	 *         Description :Verify "More data exists" message should be displayed
	 *         when more than 20 records to display on thread map'RPMXCON-51363'
	 *         Sprint : 6
	 * @throws InterruptedException Stabilized - not done [We need thread docs more than 20 records]
	 */
//	@Test(description="RPMXCON-51363",enabled = true, dataProvider = "userDetails", groups = { "regression" } 4)
	public void VerifyMoreDataExistsRecordsToDisplayOnThreadMap(String fullName, String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51363");
		baseClass.stepInfo(
				"Verify More data exists message should be displayed when more than 20 records to display on thread map");
		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String documentToBeSelected = Input.conceptualDocumentReviewer;
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		docView.selectDocsFromMiniDocsListAndCheckTheThreadedDocuments();

		baseClass.stepInfo("verifyMiniDocListIsDisplayedThreaedDocumentsSuccessfully");
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 24/11/2021 Modified date: NA Modified by: NA
	 * @Description : To verify when user select action as 'Remove code same' for
	 *              documents which are not marked as code same as this from
	 *              conceptual RPMXCON-51225
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51225",enabled = true, dataProvider = "multiUsers", groups = { "regression" })
	public void verifyConceputallyTabWhenUserSelectsDocsWhicAreNotMarkedAsCodeSameAs(String fullName, String userName,
			String password) throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51225");
		baseClass.stepInfo(
				"To verify when user select action as 'Remove code same' for documents which are not marked as code same as this from conceptual");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.conceptualDocs1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewConceptualDocsInDocViews();

		// select docs and perform code same as
		baseClass.stepInfo("Step 2: Select multiple documents from conceptual and action as 'Code same as this'");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();

		// select docs which are not marked as code same as
		baseClass.stepInfo(
				"Step 3: Select documents from conceptual which are not marked as code same and action as 'Remove code same'");
		docView.selectConceptualDocsWhichAreNotPerformedCodeAsSame();

		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 25/11/2021 Modified date: NA Modified by: NA
	 * @Description : To verify user can select Multiple documents in Analytics
	 *              panel > Family Members and Select Action as 'Remove Code Same as
	 *              this' RPMXCON-51216
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51216",enabled = true, groups = { "regression" } )
	public void verifyWhenUserSelectsMulitiDocsAndPerformRemoveCodeSameAs() throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51216");
		baseClass.stepInfo(
				"To verify user can select Multiple documents in Analytics panel > Family Members and Select Action as 'Remove Code Same as this'");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.familyDocumentForReviewer;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignFamilyMemberDocuments();

		// create Assignment and disturbute docs

		assignmentsPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select docs from Mini docs List and perform action
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);

		// select docs from family member and action as code same as
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		// select docs from family member and acrion as remove code same as
		docView.selectDocsFromFamilyMemberTabAndActionRemoveCodeSame();

		// logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select docs from Mini docs List and perform action
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.threadDocId);

		// select docs from family member and action as code same as
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		// select docs from family member and acrion as remove code same as
		docView.selectDocsFromFamilyMemberTabAndActionRemoveCodeSame();

		// logout RMU
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 24/11/2021 Modified date: NA Modified by: NA
	 * @Description : To verify when user select action as 'Remove code same' for
	 *              documents which are not marked as code same as this from family
	 *              member RPMXCON-51224
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51224",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyFamilyMemberTabWhenUserSelectsDocsWhicAreNotMarkedAsCodeSameAs(String fullName, String userName,
			String password) throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51224");
		baseClass.stepInfo(
				"To verify when user select action as 'Remove code same' for documents which are not marked as code same as this from family member");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.familyDocument;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		// select docs and perform code same as
		baseClass.stepInfo("Step 2: Select multiple documents from family member and action as 'Code same as this'");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		// select docs which are not marked as code same as
		baseClass.stepInfo(
				"Step 3: Select documents from family member which are not marked as code same and action as 'Remove code same'    ");
		docView.selectFamilyMemberDocsWhichAreNotPerformedCodeAsSame();

		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 26/11/2021 Modified date: NA Modified by: NA
	 * @Description : To verify user can select Multiple documents in Analytics
	 *              panel > Thread map and Select Action as 'Remove Code Same as
	 *              this' RPMXCON-51218
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51218",enabled = true, groups = { "regression" } )
	public void verifyWhenUserSelectsThreadMapMulitiDocsAndPerformRemoveCodeSameAs()
			throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51218");
		baseClass.stepInfo(
				"To verify user can select Multiple documents in Analytics panel > Thread map and Select Action as 'Remove Code Same as this'");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.threadDocId;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignThreadedDocs();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select docs from Mini docs List and perform action
		baseClass.stepInfo(
				"Step 3: Select docs having thread docs from Mini Doc list and select docs from ThreadMap Tab and perform action CodeSamAs");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		// select docs from family member and action as code same as
		docView.selectDocsFromThreadMapTabAndActionCodeSame();

		// select docs from family member and acrion as remove code same as
		baseClass.stepInfo("Step 4: Select Docs from ThreadMap Tab and RemoveCodeAsSame");
		docView.selectDocsFromThreadMapTabAndActionRemoveCodeSameAs();

		// logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select docs from Mini docs List and perform action
		baseClass.stepInfo(
				"Step 3: Select docs having thread docs from Mini Doc list and select docs from ThreadMap Tab and perform action CodeSamAs");
		driver.waitForPageToBeReady();
		// select docs from ThreadMap and action as code same as
		docView.selectDocsFromThreadMapTabAndActionCodeSame();

		// select docs from ThreadMap and acrion as remove code same as
		baseClass.stepInfo("Step 4: Select Docs from ThreadMap Tab and RemoveCodeAsSame");
		docView.selectDocsFromThreadMapTabAndActionRemoveCodeSameAs();

		// logout RMU
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 26/11/2021 Modified date: NA Modified by: NA
	 * @Description : To verify user can select Multiple documents in mini doc list
	 *              and Select Action as 'Remove Code Same as this' RPMXCON-51217
	 *              stabilized - done
	 */
	@Test(description="RPMXCON-51217",enabled = true, groups = { "regression" } )
	public void verifyWhenUserSelectsMiniDocListMulitiDocsAndPerformRemoveCodeSameAs()
			throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51217");
		baseClass.stepInfo(
				"To verify user can select Multiple documents in mini doc list and Select Action as 'Remove Code Same as this'");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
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

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select docs from Mini docs List and perform action
		baseClass.stepInfo("Step 3: Select docs from Mini Doc list and perform action CodeSamAs");
		driver.waitForPageToBeReady();
		// select docs from MiniDocList and action as code same as
		docView.selectDocsFromMiniDocsAndCodeSameAs();

		// select docs from MiniDocList and acrion as remove code same as
		baseClass.stepInfo("Step 4: Select Docs from MiniDocList and RemoveCodeAsSame");
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSame();

		// edit Coding Form and complete the principle doc
		baseClass.stepInfo("Step 5: Edit Coding form for the principal doc and complete the docs");
		docView.editCodingFormComplete();

		// logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select docs from Mini docs List and perform action
		baseClass.stepInfo("Step 3: Select docs from Mini Doc list and perform action CodeSamAs");
		driver.waitForPageToBeReady();
		// select docs from MiniDocList and action as code same as
		docView.selectDocsFromMiniDocsAndCodeSameAs();

		// select docs from MiniDocList and acrion as remove code same as
		baseClass.stepInfo("Step 4: Select Docs from MiniDocList and RemoveCodeAsSame");
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSame();

		// edit Coding Form and complete the principle doc
		baseClass.stepInfo("Step 5: Edit Coding form for the principal doc and complete the docs");
		docView.editCodingFormComplete();

		// logout RMU
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 26/11/21 NA Modified date: NA Modified by:NA
	 * Description : Verify Zoom in/ Zoom out is working from near dupe child
	 * window.'RPMXCON-51256' Sprint : 6
	 * 
	 * @Stabilization - done
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51256",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifyZoomInZoomOutForNearDupeDocuments(String fullName, String userName, String password)
			throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51256");
		baseClass.stepInfo("Verify Zoom in/ Zoom out is working from near dupe child window");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String docId = Input.nearDupeCompletedDocId;

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Step 3 : Docs are selected and viewed In MiniDocList successfully");
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocIdInMiniDocList(docId);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Step 4 : From Analytics panel > Near Dupe window click the icon");
		// Open Comparison Window in NearDupes Tab
		docViewAnalytics.openNearDupeComparisonWindow();
		baseClass.passedStep("Comparison Window is opened Successfully");

		baseClass.stepInfo("Step 5 : Click Zoom-in/Zoom-out from Near Dupe View");
		// click zoom-in/zoom-out and reset button in Original View
		docViewAnalytics.verifyChildWindowZoominZoomOutNearDupeDocs();
		baseClass.passedStep("Zoom-in/Zoom-out from Near Dupe view has been clicked successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 29/11/2021 Modified date: NA Modified by: NA
	 * @Description : Verify that on navigating to thread map tab from Near Dupe tab
	 *              link to view more data should not be displayed RPMXCON-51571
	 * 
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51571",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyWhenNavigatingFromThreadMapToNearDupeTabLoadMoreDocsLinkToBeNotDisplayed(String fullName,
			String userName, String password) throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51571");
		baseClass.stepInfo(
				"Verify that on navigating to thread map tab from Near Dupe tab link to view more data should not be displayed");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer with " + fullName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.threadDocId;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 2: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewThreadedDocsInDocViews();
		driver.waitForPageToBeReady();

		// Select docs from Mini docs List and perform action
		baseClass.stepInfo("Step 3: View the document from mini doc list having threaded documents");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);

		// verify navigate to other tab link not to be present
		baseClass.stepInfo("Step 4: Navigate to Near Dupe tab from thread map tab and again click the thread map tab");
		softAssertion = new SoftAssert();

		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep("Load More link is not visible when Navigated to NearDupe Tab");

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep(
				"Link to view more data is not be displayed when there are less than 20 rows or columns on analytics panel parent window");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		baseClass.stepInfo(
				"Step 5: Open the Analytics panel child window and Navigate to Near Dupe tab from thread map tab and again click the thread map tab");
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(5);

		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep("Load More link is not visible when Navigated to NearDupe Tab");

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep(
				"Link to view more data is not be displayed when there are less than 20 rows or columns on analytics panel child window");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// logout RMU
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 29/11/2021 Modified date: NA Modified by: NA
	 * @Description : Verify that on navigating to thread map tab from Conceptual
	 *              Similar tab link to view more data should not be displayed
	 *              RPMXCON-51572
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51572",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyWhenNavigatingFromThreadMapToConceptualTabLoadMoreDocsLinkToBeNotDisplayed(String fullName,
			String userName, String password) throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51572");
		baseClass.stepInfo(
				"Verify that on navigating to thread map tab from Conceptual Similar tab link to view more data should not be displayed");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer with " + fullName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.threadDocId;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 2: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewThreadedDocsInDocViews();
		driver.waitForPageToBeReady();

		// Select docs from Mini docs List and perform action
		baseClass.stepInfo("Step 3: View the document from mini doc list having threaded documents");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);

		// verify navigate to other tab link not to be present
		baseClass.stepInfo("Step 4: Navigate to Conceptual tab from thread map tab and again click the thread map tab");
		softAssertion = new SoftAssert();

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep("Load More link is not visible when Navigated to Conceptual Tab");

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep(
				"Link to view more data is not be displayed when there are less than 20 rows or columns on analytics panel parent window");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		baseClass.stepInfo(
				"Step 5: Open the Analytics panel child window and Navigate to Conceptual Similar tab from thread map tab and again click the thread map tab");
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep("Load More link is not visible when Navigated to Conceptual Tab");

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep(
				"Link to view more data is not be displayed when there are less than 20 rows or columns on analytics panel child window");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// logout RMU
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 29/11/21 NA Modified date: NA Modified by:NA
	 * Description : Verify that on navigating to thread map tab from Family member
	 * tab link to view more data should not be displayed.'RPMXCON-51570' Sprint : 7
	 * 
	 * @Stabilization - done
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51570",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyNavigatingToThreadMapTabFamilyMemberTabNotBeDisplayed(String fullName, String userName,
			String password) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51570");
		baseClass.stepInfo(
				"Verify that on navigating to thread map tab from Family member tab link to view more data should not be displayed");

		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String documentToBeSelected = Input.threadDocId;

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep("Load More link is not visible when Navigated to Family Member Tab");

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep(
				"Link to view more data is not be displayed when there are less than 20 rows or columns on analytics panel parent window");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		baseClass.stepInfo(
				"Step 5: Open the Analytics panel child window and Navigate to Family Member tab from thread map tab and again click the thread map tab");
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep("Load More link is not visible when Navigated to Conceptual Tab");

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		driver.waitForPageToBeReady();
		docView.verifyLoadMoreLinkNaviagtedToOtherTab();
		baseClass.passedStep(
				"Link to view more data is not be displayed when there are less than 20 rows or columns on analytics panel child window");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 30/11/21 NA Modified date: NA Modified by:NA
	 * Description : Verify check mark icon should be displayed when document is
	 * completed after selecting 'Code same as this' action' from Analytics Panel >
	 * Near Dupe.'RPMXCON-51058' Sprint : 7 [Near dupe]
	 * 
	 * @throws Exception
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51058",enabled = true, groups = { "regression" } )
	public void verifyCheckMarkIconDisplayedTheNearDupe() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51058");
		baseClass.stepInfo(
				" Verify check mark icon should be displayed when document is completed after selecting 'Code same as this' action' from Analytics Panel > Near Dupe");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		String documentToBeSelected = Input.nearDoc1;
		String revDocToBeSelected = Input.nearDupeCompletedDocId;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();

		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as NearDupe Documents
		docView.performCodeSameForNearDupeDocuments(1);

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// verify Check Mark Icon
		docView.verifyCheckMark();

		// verify Near Dupe Check Mark
		softAssertion = new SoftAssert();
		softAssertion.assertTrue(docView.getDocView_NearDupeCheckMark().isElementPresent());

		baseClass.passedStep("check Mark Icon Is Displayed In Near Dupe Tab");
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(revDocToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as NearDupe Documents
		docView.performCodeSameForNearDupeDocuments(1);

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// verify Check Mark Icon
		docView.verifyCheckMark();

		// verify Near Dupe Check Mark
		if(docView.getDocView_NearDupeCheckMark().isElementAvailable(5))
		softAssertion.assertTrue(docView.getDocView_NearDupeCheckMark().isElementAvailable(5));
		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 30/11/21 NA Modified date: NA Modified by:NA
	 * Description : Verify check mark icon should be displayed when document is
	 * completed after selecting 'Code same as this' action' from Analytics Panel >
	 * Conceptual'RPMXCON-51059' Sprint : 7
	 * 
	 * @Stabilization - not done
	 * @throws Exception
	 */
	 @Test(description="RPMXCON-51059",enabled = true, groups = { "regression" } )
	public void verifyCheckMarkIconDisplayedTheConceptuallySimilar() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51059");
		baseClass.stepInfo(
				" Verify check mark icon should be displayed when document is completed after selecting 'Code same as this' action' from Analytics Panel > Conceptual");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		String documentToBeSelected = Input.conceptualDocId01;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		MiniDocListPage docList=new MiniDocListPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignConceptualDocuments();

		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docList.checkConceptuallySimilarDoc();
//		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as NearDupe Documents
		docView.performCodeSameForConceptualDocuments();

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// verify Check Mark Icon
		docView.verifyCheckMark();

		// verify Near Dupe Check Mark
		softAssertion = new SoftAssert();
		Boolean actualFlag = docView.getDocView_ConceptuallySimilarCheckMark().isElementAvailable(5);
		softAssertion.assertTrue(actualFlag);

		baseClass.passedStep("check Mark Icon Is Displayed In ConceptuallySimilar Tab");
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1FullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		docList.checkConceptuallySimilarDoc();
//		docView.selectDocIdInMiniDocList(Input.conceptualDocIdForReviewer01);
		// perform code same as NearDupe Documents
		docView.selectDocsFromConceptualTabAndActionCodeSame();

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// verify Check Mark Icon
		docView.verifyCheckMark();

		// verify Near Dupe Check Mark
		softAssertion = new SoftAssert();
		if(docView.getDocView_ConceptuallySimilarCheckMark().isElementAvailable(5))
		actualFlag = docView.getDocView_ConceptuallySimilarCheckMark().isElementAvailable(5);
		softAssertion.assertTrue(actualFlag);

		baseClass.passedStep("check Mark Icon Is Displayed In ConceptuallySimilar Tab");
		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 1/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify user can select 'Remove Code Same' from analytics panel
	 * child window'RPMXCON-51232' Sprint : 7
	 * 
	 * @Stabilization - done
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51232",enabled = true, dataProvider = "multiUsers", groups = { "regression" })
	public void verifyUserCanSelectRemoveCodeSameAs(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51232");
		baseClass.stepInfo("Verify user can select 'Remove Code Same' from analytics panel child window");

		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String docToBeSelected = Input.threadDocId;

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		// Search for the docs and navigate to docs view
		baseClass.stepInfo("Step 1: Search should be performed and navigate to docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		docView.selectFamilyMemberPureHit();
		docView.selectThreadMapPureHit();
		sessionSearch.ViewConceptualDocsInDocViews();

		// select docs from mini doc list
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docToBeSelected);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		baseClass.stepInfo(
				"Step 2 & 3: Open the Analytics panel child window and Navigate to Family Member tab and action CodeSameAs");
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID = driver.getWebDriver().getWindowHandle();

		docView.performCodeSameForFamilyMembersDocuments(parentWindowID);

		driver.switchTo().window(analyticsWindowID);
		baseClass.stepInfo("Step 4: Select the documents having code same icon and action as 'Remove Code Same'");
		docView.performRemoveCodeSameForFamilyMembersDocuments(parentWindowID);
		driver.waitForPageToBeReady();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo(
				"Step 5: Save the coding form for the principle document being viewed and click to 'Save' the coding form");
		docView.editCodingFormSave();

		driver.scrollPageToTop();
		docView.getSaveIcon().Click();
		baseClass.waitTime(3);
		docView.selectDocIdInMiniDocList(docToBeSelected);

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		baseClass.stepInfo("Step 6: Select documents from thread map child window and action as 'code same as this'");
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID1 = driver.getWebDriver().getWindowHandle();

		docView.performCodeSameForThreadDocuments(parentWindowID1);

		driver.switchTo().window(analyticsWindowID1);
		baseClass.stepInfo("Step 7: Select the documents having code same icon and action as 'Remove Code Same'");
		docView.performRemoveCodeSameForThreadDocuments(parentWindowID1);
		driver.waitForPageToBeReady();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID1);

		baseClass.stepInfo(
				"Step 8: Save the coding form for the principle document being viewed and click to 'Save' the coding form");
		docView.editCodingFormSave();

		driver.scrollPageToTop();
		docView.getSaveIcon().Click();
		baseClass.waitTime(3);
		docView.selectDocIdInMiniDocList(docToBeSelected);

		String parentWindowID2 = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		baseClass.stepInfo("Step 9: Select documents from conceptual child window and action as 'code same as this'");
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId2 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId2) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID2 = driver.getWebDriver().getWindowHandle();

		driver.waitForPageToBeReady();
		docView.performCodeSameForConceptualDocuments(parentWindowID2);

		driver.switchTo().window(analyticsWindowID2);
		baseClass.stepInfo("Step 10: Select the documents having code same icon and action as 'Remove Code Same'");
		docView.performRemoveCodeSameForConceptualDocuments(parentWindowID2);
		driver.waitForPageToBeReady();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID2);

		baseClass.stepInfo(
				"Step 11: Save the coding form for the principle document being viewed and click to 'Save' the coding form");
		docView.editCodingFormSave();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 02/12/2021 Modified date: NA Modified by: NA
	 * @Description : To verify when user edits the coding form of main document and
	 *              after saving selects action 'Code same as this' from family
	 *              members on same documents then selects 'Remove code same as
	 *              this' RPMXCON-51222
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-51222",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyUserEditCodingFormMainDocAndActionCodeSameAs(String fullName, String userName, String password)
			throws InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51222");
		baseClass.stepInfo(
				"To verify when user edits the coding form of main document and after saving selects action 'Code same as this' from family members on same documents then selects 'Remove code same as this'");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer with " + fullName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.familyDocument;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 2: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		driver.waitForPageToBeReady();

		// Edit coding form and save for currently viewing docs
		baseClass.stepInfo("Step 3: Save the coding form for the document being viewed");
		docView.editCodingFormSave();

		// Select Family Member docs from mini doclist and the select docs from
		// FamilyMember and perform code same as
		baseClass.stepInfo("Step 4: select documents from family member and action as 'Code Same as this'");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		// Select Docs from family member and action As Remove Code Same As
		baseClass.stepInfo("Step 5:Select the documents having code same icon and action as 'Remove Code Same'");
		docView.selectDocsFromFamilyMemberTabAndActionRemoveCodeSame();

		// Edit Coding Form and Save the priciple document being viewed
		baseClass.stepInfo(
				"Step 6: Save the coding form for the principle document being viewed and click to 'Save' the coding form");
		docView.editCodingFormSave();

		// logout
		loginPage.logout();

	}

	

	/**
	 * Author : Mohan date: 13/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50973 To verify that action should be disable 'View Document'
	 * without selecting document.
	 * 
	 * @Stabilization - done
	 */

	@Test(description="RPMXCON-50973",enabled = true, groups = { "regression" } )
	public void verifyViewDocumnetDisableWithoutSelectingDocument() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50973");
		baseClass.stepInfo("To verify that action should be disable 'View Document' without selecting document.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		baseClass.stepInfo("Step 1: Search for the doc and Go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo(
				"Step 2: Do not select any document from analytical panela and select action as View Document");
		docViewPage.verifyViewDocumentWithoutSelectingDocs();

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 13/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51364
	 * 
	 * @description: To Verify coding form, metadata from parent and child window
	 *               when document is viewed from analytics panel
	 * @Stabilization - done
	 */

	@Test(description="RPMXCON-51364",enabled = true, groups = { "regression" } )
	public void verifyCodingFormAndMetaDataFromParentAndChildWindow() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51364");
		baseClass.stepInfo(
				"To Verify coding form, metadata from parent and child window when document is viewed from analytics panel");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		MiniDocListPage docList=new MiniDocListPage(driver);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created successfully");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view in context of an assignment");
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Step 3: View the document from analytics panel which is part of assignment");
		docList.checkThreadMapDoc();
		docViewPage.selectDocIdInMiniDocList(Input.threadDocId);
		docViewPage.performViewDocumentFromThreadMapTab();

		baseClass.stepInfo("Step 4: Once document is loaded check the Coding Form/ metadata from respective panels");
		docViewPage.verifyCodingFormPanelIsLoadedAfterDocsAreViewed();
		docViewPage.verifyMetaDataPanelIsLoadedAfterDocsAreViewed();

		baseClass.stepInfo(
				"Step 5: Click the gear icon to pop out the panels and pop out coding form and metadata panel in child window  Check the coding form/metadata for the viewed document from child window");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docViewPage.popOutMetaDataPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		docViewPage.verifyMetaDataPanelIsLoadedAfterDocsAreViewed();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		docViewPage.popOutCodingFormPanel();
		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		docViewPage.verifyCodingFormPanelIsLoadedAfterDocsAreViewed();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		// login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 2: Go to doc view in context of an assignment");
		assignmentsPage.SelectAssignmentByReviewer("assgnment7083398");

		baseClass.stepInfo("Step 3: View the document from analytics panel which is part of assignment");
		docViewPage.performViewDocumentFromThreadMapTab();

		baseClass.stepInfo("Step 4: Once document is loaded check the Coding Form/ metadata from respective panels");
		docViewPage.verifyCodingFormPanelIsLoadedAfterDocsAreViewed();
		docViewPage.verifyMetaDataPanelIsLoadedAfterDocsAreViewed();

		baseClass.stepInfo(
				"Step 5: Click the gear icon to pop out the panels and pop out coding form and metadata panel in child window  Check the coding form/metadata for the viewed document from child window");

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		docViewPage.popOutMetaDataPanel();

		Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		docViewPage.verifyMetaDataPanelIsLoadedAfterDocsAreViewed();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID1);

		docViewPage.popOutCodingFormPanel();
		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		docViewPage.verifyCodingFormPanelIsLoadedAfterDocsAreViewed();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID1);

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 13/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that Project Admin cannot view the action 'Code Same
	 * as This' in Analytics panel.'RPMXCON-50941' Sprint : 8
	 * 
	 * @Stabilization - done
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50941",enabled = true, groups = { "regression" } )
	public void verifyCannotViewTheActionCodeSameAsNearDupes() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50941");
		baseClass.stepInfo("To verify that Project Admin cannot view the action 'Code Same as This in Analytics panel");

		// login as PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		docView.checkCodeSameAsIsPresentInNearDupe();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docView.popOutAnalyticsPanel();

		// open Comparison window in NearDupes Tab

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		docView.checkCodeSameAsIsPresentInNearDupe();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 13/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify 'Code same as this' action without selecting document
	 * from family member should be disable.'RPMXCON-50910' Sprint : 8
	 * 
	 * @Stabilization - done
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50910",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyCodeSameAsActionWithOutSelectingDocsFromFamilyMember(String fullName, String userName,
			String password) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50910");
		baseClass.stepInfo(
				"To verify 'Code same as this' action without selecting document from family member should be disable");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer with " + fullName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = Input.familyDocument;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		// select docs and perform code same as
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);

		docView.checkCodeSameAsIsDisableFamilyMember();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 14/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify user will be able to see all the threaded documents in
	 * the analytics panel of the main selected document in the Doc
	 * view.'RPMXCON-50951' Sprint : 8 Stabilized
	 * 
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50951",enabled = true, dataProvider = "userDetails", groups = { "regression"	} )
	public void verifySeeAllTheThreadedDocsInAnalyticsPanelSelectedDocInDocView(String fullName, String userName,
			String password) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50951");
		baseClass.stepInfo(
				"To verify user will be able to see all the threaded documents in the analytics panel of the main selected document in the Doc view");

		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		String documentToBeSelected = Input.MetaDataId;
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.ThreadQuery);
		sessionSearch.ViewInDocView();

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		docView.selectDocsFromMiniDocsListAndCheckTheThreadedDocsSize();

		baseClass.stepInfo("verifyMiniDocListIsDisplayedThreaedDocumentsSuccessfully");

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 14/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify when user edits the coding form of main document and
	 * after saving selects action 'Code same as this' from family members on same
	 * documents.'RPMXCON-50909' Sprint : 8
	 * 
	 * @Stabilization - done
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50909",enabled = true, dataProvider = "multiUsers", groups = { "regression" } )
	public void verifyEditsCodingFormSelectActionCodeSameAsFromFamilyMember(String fullName, String userName,
			String password) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50909");
		baseClass.stepInfo(
				"To verify when user edits the coding form of main document and after saving selects action 'Code same as this' from family members on same documents");

		// login as Admins
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		driver.waitForPageToBeReady();
		docView.selectDocsMiniDocListAndCodingFormSaveButton();

		driver.waitForPageToBeReady();
		docView.performCodeSameForFamilyMembersDocuments();

		docView.selectDocsMiniDocListAndCodingFormSaveButton();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author: Mohan Created date: 14/12/2021 Modified date: NA Modified by: NA
	 * @Description : To verify after impersonation document opened in default
	 *              viewer should be displayed in ''Compare Similar Documents',if
	 *              clicks on the icon in Analytical Panel->Near Dupes.
	 *              RPMXCON-50956
	 * @Stabilization - done
	 */
	@Test(description="RPMXCON-50956",enabled = true, dataProvider = "userDetailss", groups = { "regression" } )
	public void verifyCompareSimilarDocumentIsDisplayedOnceTheIconIsClicked(String roll, String userName,
			String password, String impersonate) throws InterruptedException {

		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu") || roll.equalsIgnoreCase("rmu")) {

			sessionSearch = new SessionSearch(driver);
			docView = new DocViewPage(driver);
			softAssertion = new SoftAssert();
			String searchString = Input.searchString1;
			baseClass.stepInfo("Test case Id: RPMXCON-50956");
			baseClass.stepInfo(
					"To verify after impersonation document opened in default viewer should be displayed in ''Compare Similar Documents',if clicks on the icon in Analytical Panel->Near Dupes.");

			// Basic Search and select the pure hit count
			baseClass.stepInfo("Step 2: Searching documents based on search string and Navigate to DocView");
			sessionSearch.basicContentSearch(searchString);
			sessionSearch.ViewNearDupeDocumentsInDocView();

			// Select docs from mini doc list having Neardupe docs
			baseClass.stepInfo("Step 3: Select Docs from Mini DocList having Neardupe docs");
			driver.waitForPageToBeReady();

			// Open Comparison Window in NearDupes Tab
			String parentWindowID = driver.getWebDriver().getWindowHandle();
			docView.openNearDupeComparisonWindow();
			baseClass.passedStep(
					" ''Compare Similar Documents',is displayed after clicking on the icon in Analytical Panel->Near Dupes.");

			driver.getWebDriver().close();

			driver.switchTo().window(parentWindowID);
			softAssertion.assertAll();

		}
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 14/12/2021 Modified date: NA Modified by: NA
	 * 
	 * @description: Verify check mark icon should be displayed when coding stamp
	 *               applied after selecting 'Code same as this' action' from
	 *               Analytics Panel > Conceptual RPMXCON-51062
	 * @Stabilization - done
	 */

	@Test(description="RPMXCON-51062",enabled = true, groups = { "regression" } )
	public void verifyCheckMarkIconCodingStampAppliedToSelectedDocs() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51062");
		baseClass.stepInfo(
				"Verify check mark icon should be displayed when coding stamp applied after selecting 'Code same as this' action' from Analytics Panel > Conceptual");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		String colourName = "colourName" + Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 3: Select docs from Analytics Panel > Conceptual and action as 'Code same as this'");
		docView.performCodeSameForConceptualDocuments();

		baseClass.stepInfo("Step 4: Click to apply coding stamp of the main viewing document");
		driver.scrollPageToTop();
		docView.editCodingFormComplete();
		docView.stampColourSelection(colourName, colour);

		baseClass.stepInfo(
				"Step 5: Verify the check mark icon from the mini doc list by refreshing or revisiting the doc view page through the same assignment for the selected document from mini doc list and from conceptual");
		docView.verifyCheckMark();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 3: Select docs from Analytics Panel > Conceptual and action as 'Code same as this'");
		driver.waitForPageToBeReady();
		// perform code same for conceptual documents
		docView.performCodeSameForConceptualDocuments();

		baseClass.stepInfo("Step 4: Click to apply coding stamp of the main viewing document");
		driver.scrollPageToTop();
		docView.editCodingFormComplete();
		docView.stampColourSelection(colourName, colour);

		baseClass.stepInfo(
				"Step 5: Verify the check mark icon from the mini doc list by refreshing or revisiting the doc view page through the same assignment for the selected document from mini doc list and from conceptual");
		docView.verifyCheckMark();

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 10/26/21 NA Modified date: NA Modified by:NA Description
	 * : Verify that sender of each email doc should be identified with the
	 * designated dot marker from thread map Test CaseId: 'RPMXCON-51359' Sprint : 8
	 * Stabilized
	 * 
	 * @throws InterruptedException
	 */
	@Test(description="RPMXCON-51359",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifyDesignatedDotMarkerInThreadMapTab(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51359");

		baseClass.stepInfo(
				"Verify that sender of each email doc should be identified with the designated dot marker from thread map");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		MiniDocListPage docList=new MiniDocListPage(driver);	
		baseClass.stepInfo(
				"Step 2 & 3 : Search for documents to get threaded documents and Drag the result to shopping cart and go to doc view  and view the document from mini doc list with threaded documents");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();
		docList.checkThreadMapDoc();
//		docView.selectDocIdInMiniDocList(Input.threadDocWithToolTip);

		baseClass.stepInfo("Step 4 : Check for the sender from thread map tab");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(3);

		baseClass.waitForElement(docView.getDocView_Analytics_ThreadMap_DesginationMarker());
		driver.scrollingToElementofAPage(docView.getDocView_Analytics_ThreadMap_DesginationMarker());
		softAssertion.assertTrue(docView.getDocView_Analytics_ThreadMap_DesginationMarker().isElementAvailable(1));
		softAssertion.assertAll();
		baseClass.passedStep(
				"Sender of each email doc is identified with the designated marker from thread map successfully");

		loginPage.logout();

	}

	
	/**
	 * Author : Vijaya.Rani date: 24/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can view documents in ''Compare Similar
	 * Documents',if clicks on the icon in Analytical Panel->Near
	 * Dupes..'RPMXCON-50900' Sprint : 9
	 * 
	 * @Stabilization - done
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50900",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyCompareSimilarDocsInNearDupe(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50900");
		baseClass.stepInfo(
				"To verify that user can view documents in ''Compare Similar Documents',if clicks on the icon in Analytical Panel->Near Dupes");

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		driver.waitForPageToBeReady();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Step 3 : From Analytics panel > Near Dupe window click the icon");
		// Open Comparison Window in NearDupes Tab
		docViewAnalytics.openNearDupeComparisonWindow();
		baseClass.passedStep("Comparison Window is opened Successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 23/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the Save
	 * search, then he can view the documents in the doc list from Doc View->Thread
	 * Map..'RPMXCON-50875' Sprint : 9
	 * 
	 * @Stabilization - done [Thread doc issue]
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50875",enabled = true, groups = { "regression" } )
	public void verifyDocViewFromSaveSearchDocViewThreadMap() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50875");
		baseClass.stepInfo(
				"To verify that if user navigates to doc view from the Save search, then he can view the documents in the doc list from Doc View->Thread Map.");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Savebtn" + Utility.dynamicNameAppender();

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.savedSearchToDocView(BasicSearchName);

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDocumentForReviewer);

		// threadmap tab View in DocList
		driver.scrollPageToTop();
		docView.performThreadMapViewInDocList();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.savedSearchToDocView(BasicSearchName);

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDocumentForReviewer);

		// threadmap tab View in DocList
		driver.scrollPageToTop();
		docView.performThreadMapViewInDocList();

	}

	/**
	 * Author : Vijaya.Rani date: 24/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user select any document on Child Window then
	 * same document will displayed on Doc View page.'RPMXCON-50947' Sprint : 9
	 * 
	 * @Stabilization - done
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50947",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifyDocChilsWindowThenSameDocWillDisplayInDocView(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50947");
		baseClass.stepInfo(
				"To verify that if user select any document on Child Window then same document will displayed on Doc View page.");

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		String fisrtId = docView.getDocView_CurrentDocId().getText();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.selectDocsFromFamilyMemberAndViewTheDocument();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		String secondId = docView.getDocView_CurrentDocId().getText();

		softAssertion.assertNotEquals(fisrtId, secondId);
		softAssertion.assertAll();
		baseClass.passedStep("Selected document are displayed on Doc View page");

		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51133 To verify user can remove assigned folder from Dockout
	 * screen->Analytics-Near Dupes
	 * 
	 * stabilization done
	 */
	@Test(description="RPMXCON-51133",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void verifyRemoveAssignedFolderDockoutAnalyticalNearDupe() throws Exception {
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String folderName = "AnalyticalPanel" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		// Login As RMU
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51133");
		baseClass.stepInfo("To verify user can remove assigned folder from Dockout" + "screen->Analytics-Near Dupes");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.comments);
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// To view the NearDupe Doc in the DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		docView.selectDocIdInMiniDocList(Input.nearDupeDocument);
		driver.waitForPageToBeReady();
		docView.popOutAnalyticsPanel();
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(2);

		// Selected Documents in NearDupe folder
		docView.selectTwoDocInNearDupeFolder();
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(1);

		// Creating a new folder in selected Documents.
		docView.createNewFolderInAnalytical(folderName);
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(2);

		// Selected Documents in NearDupe folder
		docView.selectTwoDocInNearDupeFolder();
		driver.waitForPageToBeReady();
		docView.closeWindow(1);
		docView.switchToNewWindow(1);

		// UnFoldered in selected Documents.
		docView.selectingUnFoldersAndVerifyingTheDocCount(folderName);
		driver.getWebDriver().navigate().refresh();
		baseClass.handleAlert();
		driver.waitForPageToBeReady();

		// navigating to tags and folder page.
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.ViewinDocViewthrFolder(folderName);
		baseClass.VerifyWarningMessage("There are NO documents in the tags or folders that you have selected");
		baseClass.passedStep("Documents removed from the selected folder same folder is not displayed for documents");
		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description : Verify Analytics Panel -> Family Member tab for mouse over row
	 *              color treatment for completed, code same, currently viewed
	 *              document Test Case Id: RPMXCON-51388
	 * @throws InterruptedException
	 * 
	 * stabilization done
	 */
	@Test(description="RPMXCON-51388",enabled = true, groups = { "regression" })
	public void verifyBGColorOnMousehoverInFamilyMemBerTabAnalyticalPanel() throws InterruptedException {

		String assname = "assgnment" + Utility.dynamicNameAppender();

		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-51388");
		baseClass.stepInfo(
				"Verify Analytics Panel -> Family Member tab for mouse over row color treatment for completed, code same, currently viewed document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with documents and Distributing to reviewer");
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, Input.codingFormName);
		assignmentsPage.toggleCodingStampEnabled();
		assignmentsPage.assignmentDistributingToReviewerManager();

		baseClass.stepInfo("Impersonate the RMU as a reviewer");
		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo("Selecting assignment from dashboard to view in doc view");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Select the document from mini doc list which has family member documents");
		docView.selectDocIdInMiniDocList(Input.familyDocumentForReviewer);

		baseClass
				.stepInfo("Verify the background color of the code same docment from family member tab on mouse hover");
		docView.performCodeSameForFamilyMembersDocuments();
		docView.getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().ScrollTo();
		baseClass.verifyBackGroundColor(
				docView.getDocView_AnalyticsDocIdFamilyTabBG(
						docView.getDocView_Analytics_ChildWindow_FamilyTab_Istdoc().getText()),
				Input.bgColorOnMouseHover);

		baseClass.stepInfo(
				"Verify the background color of the completed document from family member tab on mouse hover");
		docView.editCodingFormComplete();
		docView.getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().ScrollTo();
		baseClass.verifyBackGroundColor(
				docView.getDocView_AnalyticsDocIdFamilyTabBG(
						docView.getDocView_Analytics_ChildWindow_FamilyTab_Istdoc().getText()),
				Input.bgColorOnMouseHover);

		baseClass.stepInfo(
				"Verify the background color of the viewed document on mouse hover from family member tab which is not part of mini doc list ");
		docView.selectDocsFromFamilyTabUsingDocIdAndViewInDocview(Input.familyDocWhichIsNotInMiniDoc);
		docView.getDocView_AnalyticsDocIdFamilyTab(Input.familyDocWhichIsNotInMiniDoc).ScrollTo();
		baseClass.verifyBackGroundColor(
				docView.getDocView_AnalyticsDocIdFamilyTabBG(Input.familyDocWhichIsNotInMiniDoc),
				Input.bgColorOnMouseHover);

		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.closeBrowser();
		} catch (Exception e) {
			UtilityLog.info("Failed due to this exception"+e);
		} finally {
			// loginPage.clearBrowserCache();
		}

	}

}
