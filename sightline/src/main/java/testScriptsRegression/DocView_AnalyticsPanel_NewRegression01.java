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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_NewRegression01 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewRedactions docViewRedact;
	SavedSearch savedSearch;

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
	 * Author : Mohan date: 24/11/21 NA Modified date: NA Modified by: N/A
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
	 * Author : Mohan date: 17/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51081
	 * 
	 * @description: Verify warning message should be displayed when user selects
	 *               the document which is outside of the assignment when allow
	 *               coding outside reviewer batch is enabled in assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyWarningMsgForDocsWhichAreNotPresentInAssignment() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51081");
		baseClass.stepInfo(
				"Verify warning message should be displayed when user selects the document which is outside of the assignment when allow coding outside reviewer batch is enabled in assignment");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docToBeSelected = Input.conceptDoc1;
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo(
				"Step 3: Select the documents from analytics panel outside of the assignment and select action as 'Code same as this'");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.verifyErrorMsgForActionCodeSameAsInConceptual();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		docView.selectDocIdInMiniDocList("H16135-0188-003836");
		baseClass.stepInfo(
				"Step 3: Select the documents from analytics panel outside of the assignment and select action as 'Code same as this'");
		docView.verifyErrorMsgForActionCodeSameAsInConceptual();

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 23/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50921
	 * 
	 * @description: Verify warning message is prompted to the user when user clicks
	 *               browser back button without completing or saving from analytics
	 *               panel 'RPMXCON-50921' Sprint 8
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyWarningMsgWHenUserClicksBackButton() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50921");
		baseClass.stepInfo(
				"Verify warning message is prompted to the user when user clicks browser back button without completing or saving from analytics panel");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo(
				"Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocsFromMiniDocsAndCodeSameAs();

		driver.waitForPageToBeReady();
		driver.Navigate().back();

		driver.switchTo().alert().dismiss();

		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");

		} else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}

		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		driver.Navigate().back();
		driver.switchTo().alert().accept();

		if (assignmentsPage.getManageAssignmnets().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		} else {
			baseClass.failedStep("User is not on the clicked Page");
		}

		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo(
				"Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocsFromConceptualTabAndActionCodeSame();

		driver.Navigate().back();

		String text = driver.switchTo().alert().getText();
		System.out.println(text);
		baseClass.passedStep(
				"on navigation user action will not be save do you want to continue  - Leave and Cancel buttons is displayed.");
		baseClass.waitTime(3);
		driver.switchTo().alert().dismiss();

		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 24/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50902
	 * 
	 * @description: To verify Threaded Map tab when no document to display for
	 *               logged in user 'RPMXCON-50902' Sprint 8
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyThreadedMapTabWhenNoDocsAreDisplayed() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50902");
		baseClass.stepInfo("To verify Threaded Map tab when no document to display for logged in user");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		driver.waitForPageToBeReady();
		
		docView.selectDocIdInMiniDocList(Input.familyDocIdForReviewer02);

		baseClass.stepInfo("Step 3: Verify threaded map tab when no document to display");
		docView.verifyNoDocsInThreadMap();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Step 3: Verify threaded map tab when no document to display");
		docView.verifyNoDocsInThreadMap();

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 24/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50905
	 * 
	 * @description: To verify user can select Multiple documents in Analytic
	 *               Panel-> Family Members tab and select Action as 'Code Same as
	 *               this' 'RPMXCON-50902' Sprint 9
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyFamilyMemberTabWhenMultiDocsAreSelectedAndActionCodeSameAs() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50905");
		baseClass.stepInfo(
				" To verify user can select Multiple documents in Analytic Panel-> Family Members tab and select Action as 'Code Same as this'");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		int id = 31;
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		docView.selectFamilyMemberPureHit();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_DefaultViewTab());
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.getDocView_NumTextBox().Clear();
		docView.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		docView.getDocView_NumTextBox().Enter();

		baseClass.stepInfo(
				"Step 3: Select the documents from family member which are assigned to the user  Select multiple documents from Family Members and action as 'Code same as this'  ");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		baseClass.stepInfo("Step 4: Edit the coding form of the principle document and save/complete the document");
		docView.editCodingFormComplete();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo(
				"Step 3: Select the documents from family member which are assigned to the user  Select multiple documents from Family Members and action as 'Code same as this'  ");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		baseClass.stepInfo("Step 4: Edit the coding form of the principle document and save/complete the document");
		docView.editCodingFormComplete();

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 26/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50907
	 * 
	 * @description: To verify coding form should get overwritten if document from
	 *               family members is already having assigned coding form
	 *               'RPMXCON-50907' Sprint 9 stabilization done
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyFamilyMemberTabWhenCodingFormOverWritten() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50907");
		baseClass.stepInfo(
				"To verify coding form should get overwritten if document from family members is already having assigned coding form");

		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		int id = 4;
		String docsToBeSelected = Input.threadDocId;

		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Navigating Doc view from Assignment Page");
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo(
				"Step 3: Select documents from family members tab which are part of the assignment and should be saved with coding form of security group  Select documents from family members and action as code same as this");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_DefaultViewTab());
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.getDocView_NumTextBox().Clear();
		docView.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		docView.getDocView_NumTextBox().Enter();

		docView.selectDocsFromFamilyMemberAndViewTheDocument();
		docView.editCodingFormSave();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		baseClass.stepInfo("Step 4: Edit the coding form and save");
		docView.selectDocsFromFamilyMemberAndViewTheDocument();
		docView.editCodingFormSave();
		baseClass.passedStep("Coding form is updated for the documents selected from family member tab successfully");

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo(
				"Step 3: Select documents from family members tab which are part of the assignment and should be saved with coding form of security group  Select documents from family members and action as code same as this");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_DefaultViewTab());
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.getDocView_NumTextBox().Clear();
		docView.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		docView.getDocView_NumTextBox().Enter();

		docView.selectDocsFromFamilyMemberAndViewTheDocument();
		docView.editCodingFormSave();
		docView.selectDocIdInMiniDocList(Input.threadDocId);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		baseClass.stepInfo("Step 4: Edit the coding form and save");
		docView.selectDocsFromFamilyMemberAndViewTheDocument();
		docView.editCodingFormSave();
		baseClass.passedStep("Coding form is updated for the documents selected from family member tab successfully");

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 26/12/2021 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50943
	 * 
	 * @description: To verify that user can select multiple documents from the
	 *               'Conceptually similar' tab from analytics panel and marked it
	 *               as 'Code Same as This'. 'RPMXCON-50943' Sprint 9
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyUserCanSelectMultiDocsFromConceptualTab() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50943");
		baseClass.stepInfo(
				"To verify that user can select multiple documents from the 'Conceptually similar' tab from analytics panel and marked it as 'Code Same as This'.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		baseClass.stepInfo("Step 3: User can view the Principal document along with Coding Form.");

		softAssertion.assertTrue(docView.getDocView_Analytics_CodingFormPanel().isElementAvailable(5));
		softAssertion.assertAll();
		baseClass.passedStep("User can view the Principal document along with Coding Form.");

		baseClass.stepInfo(
				"Step 4: Click on Conceptually Similar in analytics Panel and select multiple documents.  Select the Action as ' Code same as this'.");
		docView.selectDocsFromConceptualTabAndActionCodeSame();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		baseClass.stepInfo("Step 3: User can view the Principal document along with Coding Form.");

		softAssertion.assertTrue(docView.getDocView_Analytics_CodingFormPanel().isElementAvailable(5));
		softAssertion.assertAll();
		baseClass.passedStep("User can view the Principal document along with Coding Form.");

		baseClass.stepInfo(
				"Step 4: Click on Conceptually Similar in analytics Panel and select multiple documents.  Select the Action as ' Code same as this'.");
		docView.selectDocsFromConceptualTabAndActionCodeSame();

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 27/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the save
	 * search, then user can view the documents in the doc list from Doc
	 * View->Conceptual Similar docuemnts panel.'RPMXCON-50878' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyDocViewFromSaveSearchDocViewConceptual() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50878");
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
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Savebtn" + Utility.dynamicNameAppender();

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.savedSearchToDocView(BasicSearchName);

		// threadmap tab View in DocList
		driver.waitForPageToBeReady();
		docView.performConceptualViewInDocView();

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

		// threadmap tab View in DocList
		driver.waitForPageToBeReady();
		docView.performConceptualViewInDocView();
	}

	/**
	 * Author : Vijaya.Rani date: 28/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify thread map panel for default number of
	 * documents.'RPMXCON-50901' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception    stabilization done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyThredMapDefaultNumberOfDocuments() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50901");
		baseClass.stepInfo("To verify thread map panel for default number of documents.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();
		;

		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsListAndCheckTheThreadedDocuments();

		baseClass.stepInfo("verifyMiniDocListIsDisplayedThreaedDocumentsSuccessfully");
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();
		;

		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsListAndCheckTheThreadedDocuments();

		baseClass.stepInfo("verifyMiniDocListIsDisplayedThreaedDocumentsSuccessfully");
	}

	/**
	 * Author : Vijaya.Rani date: 28/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify user after impersonation should able to 'Code same as
	 * this' on document selection from Family Members panel.'RPMXCON-50908' Sprint
	 * : 9
	 * 
	 * @throws AWTException
	 * @throws Exception    stabilization done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyCodeSameAsDocsSelectionForFamilyMember() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50908");
		baseClass.stepInfo(
				"To verify user after impersonation should able to 'Code same as this' on document selection from Family Members panel.");
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		String docsToBeSelected = Input.threadDocId;

		// login as SA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);

		docView = new DocViewPage(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer,select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDoc1);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer,select assignment and go to Docview");
		baseClass.impersonateRMUtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 29/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can view the documents in the doc list from
	 * Doc View->Thread Map tab.'RPMXCON-48700' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 10)
	public void verifyViewTheDocsFromDocViewThreadMap(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-48700");
		baseClass.stepInfo("To verify that user can view the documents in the doc list from Doc View->Thread Map tab.");

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
		sessionSearch.ViewThreadedDocsInDocViews();

		// threadmap tab View in DocList
		driver.waitForPageToBeReady();
		docView.performThreadMapViewInDocList();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 29/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify check mark icon should be displayed when document is
	 * completed after selecting 'Code same as this' action' from Analytics Panel >
	 * Family Member.'RPMXCON-48716' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception    stabilization done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyCheckMarkIconAndCodeSameAsFamilyMember() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48716");
		baseClass.stepInfo(
				"Verify check mark icon should be displayed when document is completed after selecting 'Code same as this' action' from Analytics Panel > Family Member.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String docsToBeSelected = Input.familyDocumentForReviewer;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
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

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// verify Check Mark Icon
		docView.verifyCheckMark();

		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
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

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// verify Check Mark Icon
		docView.verifyCheckMark();

	}

	/**
	 * Author : Vijaya.Rani date: 30/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify when user selects view in doc list action from Near
	 * Dupe panel in doc view without selecting single document.'RPMXCON-50872'
	 * Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyViewInDocsFromNearDupeWithOutSelectDocs()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50872");
		baseClass.stepInfo(
				"To verify when user selects view in doc list action from Near Dupe panel in doc view without selecting single document.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// LOGIN AS RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get NearDupe documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();

		// create Assignment and disturbute docs
		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// NearDupe ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performNearDupeWithOutSelectDocActionViewInDocList();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// NearDupe ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performNearDupeWithOutSelectDocActionViewInDocList();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 30/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify user can select a set of documents in the Near Dupe
	 * panel in doc view and view them in doc list after
	 * impersonation.'RPMXCON-50873' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyViewAllInDocListInNearDupe() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50873");
		baseClass.stepInfo(
				"To verify user can select a set of documents in the Near Dupe panel in doc view and view them in doc list after impersonation.");
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

		// login as SA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);

		docView = new DocViewPage(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performNearDupeSelectDocsActionViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performNearDupeSelectDocsActionViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer,select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performNearDupeSelectDocsActionViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer,select assignment and go to Docview");
		baseClass.impersonateRMUtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performNearDupeSelectDocsActionViewInDocList();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 24/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the Doc List,
	 * then he can view the documents back in the doc list from Doc View->Thread
	 * Map.'RPMXCON-50874' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyViewInDocListFromThreadMapTab() throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50874");
		baseClass.stepInfo(
				"To verify that if user navigates to doc view from the Doc List, then he can view the documents back in the doc list from Doc View->Thread Map.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);

		// LOGIN AS RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get NearDupe documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Thread Map View in Doc List
		docView.performThreadMapViewInDocList();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get NearDupe documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Thread Map View in Doc List
		docView.performThreadMapViewInDocList();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 31/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that after impersonating user can view the document in
	 * the doc list from Doc View->Thread Map.'RPMXCON-50871' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception    stabilization done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyViewAllInDocListInAnalyticalThreadMap() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50871");
		baseClass.stepInfo(
				"To verify that after impersonating user can view the document in the doc list from Doc View->Thread Map.");
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

		// login as SA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);

		docView = new DocViewPage(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssignThreadedDocs();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.selectDocIdInMiniDocList(Input.threadDocId);
		docView.performThreadMapViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.selectDocIdInMiniDocList(Input.threadDocId);
		docView.performThreadMapViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer,select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.selectDocIdInMiniDocList(Input.threadDocId);
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performThreadMapViewInDocList();
		loginPage.logout();

		// This below code is commented because there is no doc with thread map is found
		// after impersonating RMU to reviewer

		/*
		 * loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 * UtilityLog.info("Logged in as User: " + Input.rmu1userName); baseClass.
		 * stepInfo("Step 1: Impersonate RMU to Reviewer,select assignment and go to Docview"
		 * ); baseClass.impersonateRMUtoReviewer();
		 * assignmentspage.SelectAssignmentByReviewer(assignmentName);
		 * driver.waitForPageToBeReady();
		 * baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		 * docView.selectDocIdInMiniDocList(Input.threadDocId);
		 * docView.performThreadMapViewInDocList(); loginPage.logout();
		 */
	}

	/**
	 * Author : Vijaya.Rani date: 31/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify user can select single document in the Near Dupe panel
	 * in doc view and view in doc list.'RPMXCON-50870 Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyViewInDocListSelectSingleDocInNearDupe()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50870");
		baseClass.stepInfo(
				"To verify user can select single document in the Near Dupe panel in doc view and view in doc list.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// LOGIN AS RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get NearDupe documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();

		// create Assignment and disturbute docs
		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// NearDupe ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performNearDupeSelectDocsActionViewInDocList();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// NearDupe ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performNearDupeSelectDocsActionViewInDocList();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 03/1/22 NA Modified date: NA Modified by:NA
	 * Description :To verify user can select a set of documents in the conceptually
	 * similar documents panel in doc view and view them in doc list.'RPMXCON-50860
	 * Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyViewInDocListInSetOfDocsInConceptualTab()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50860");
		baseClass.stepInfo(
				"To verify user can select a set of documents in the conceptually similar documents panel in doc view and view them in doc list.");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignConceptualDocuments();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Conceptual ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.nearDupeDocId);
		docView.performConceptualSelectSetOfDocsActionViewInDocList();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// NearDupe ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performConceptualSelectSetOfDocsActionViewInDocList();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 03/1/22 NA Modified date: NA Modified by:NA
	 * Description :To verify user can select single document in the conceptually
	 * similar documents panel in doc view and view in doc list.'RPMXCON-50861
	 * Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyViewInDocListInSelectSignleDocInConceptualTab()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50861");
		baseClass.stepInfo(
				"To verify user can select single document in the conceptually similar documents panel in doc view and view in doc list.");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssignConceptualDocuments();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Conceptual ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performViewInDocListConceputualSignledocs();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// NearDupe ViewIn DocList without Select Docs
		driver.waitForPageToBeReady();
		docView.performViewInDocListConceputualSignledocs();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 03/1/22 NA Modified date: NA Modified by:NA
	 * Description :To verify user can select a set of documents in the conceptually
	 * similar documents panel in doc view and view them in doc list after
	 * impersonation.'RPMXCON-50862' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyViewInDocListInAnalyticalPanelConceptualTab() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50862");
		baseClass.stepInfo(
				"To verify user can select a set of documents in the conceptually similar documents panel in doc view and view them in doc list after impersonation.");
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

		// login as SA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);

		docView = new DocViewPage(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignConceptualDocuments();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performConceptualSelectSetOfDocsActionViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.selectDocIdInMiniDocList(Input.nearDupeDocId);
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performConceptualSelectSetOfDocsActionViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer,select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.nearDupeDocId);
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performConceptualSelectSetOfDocsActionViewInDocList();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer,select assignment and go to Docview");
		baseClass.impersonateRMUtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.conceptualDocId1);
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performConceptualSelectSetOfDocsActionViewInDocList();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 03/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the Save
	 * search, then he can view the documents in the doc list from Doc View->Family
	 * Member.'RPMXCON-50864' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception    stabilization done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyDocViewFromSaveSearchDocViewThreadMap() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50864");
		baseClass.stepInfo(
				"To verify that if user navigates to doc view from the Save search, then he can view the documents in the doc list from Doc View->Family Member.");

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

		// familyMember tab View in DocList
		driver.waitForPageToBeReady();
		docView.performViewInDocListInFamilyMemberdocs();

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

		// familyMember tab View in DocList
		driver.waitForPageToBeReady();
		docView.performViewInDocListInFamilyMemberdocs();

	}

	/**
	 * Author : Vijaya.Rani date: 04/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that on selecting View Document action from thread map
	 * tab document should be displayed on doc view panel.'RPMXCON-48737' Sprint : 9
	 * 
	 * 
	 * @throws Exception stabilization done
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 21)
	public void verifyViewDocumentActionInThreadMap(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48737");
		baseClass.stepInfo(
				"Verify that on selecting View Document action from thread map tab document should be displayed on doc view panel");

		// Login as a Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		docView.selectDocIdInMiniDocList(Input.threadDocId);

		// Threadmap View Document
		docView.performThreadMapViewDocument();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.performThreadMapViewDocumentInChildWindow();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docId2 = docView.getDocView_CurrentDocId().getText();

		baseClass.passedStep("Selected document is display in Doc View");

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 06/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that after impersonating user can view the document in
	 * the doc list from Doc View->Family Member.'RPMXCON-50863' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyViewInDocListInAnalyticalPanelFamilyMember() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50863");
		baseClass.stepInfo(
				"To verify that after impersonating user can view the document in the doc list from Doc View->Family Member.");
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

		// login as SA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);

		docView = new DocViewPage(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performViewInDocListInFamilyMemberdocs();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performViewInDocListInFamilyMemberdocs();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer,select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performViewInDocListInFamilyMemberdocs();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer,select assignment and go to Docview");
		baseClass.impersonateRMUtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click View All In Doc List");
		docView.performViewInDocListInFamilyMemberdocs();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 06/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify on click of 'View All Documents' all the documents should
	 * be displayed on Analytics panel > family member child window.'RPMXCON-48732'
	 * Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 23)
	public void verifyViewAllDocumentsInFamilyMember(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-48732");
		baseClass.stepInfo(
				"Verify on click of 'View All Documents' all the documents should be displayed on Analytics panel > family member child window.");

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

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDocument);

		// FamilyMember tab View All Documents
		driver.waitForPageToBeReady();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.performFamilyMemberViewAllDocumentInChildWindow();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 07/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can view the documents in the doc list from
	 * from Doc View->Conceptual Similar tab.'RPMXCON-50865' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 24)
	public void verifyViewInDocListConceptualSimilarTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50865");
		baseClass.stepInfo(
				"To verify that user can view the documents in the doc list from from Doc View->Conceptual Similar tab.");

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

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.threadDocId);

		// FamilyMember tab View All Documents
		driver.waitForPageToBeReady();
		docView.performConceptualSelectSetOfDocsActionViewInDocList();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 07/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the Save
	 * search, then he can view the documents in the doc list from Doc View->Family
	 * Member.'RPMXCON-50866' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 25)
	public void verifySaveSearchViewInDocListConceptualTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50866");
		baseClass.stepInfo(
				"To verify that user can view the documents in the doc list from from Doc View->Conceptual Similar tab.");

		loginPage = new LoginPage(driver);

		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

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
		docView.selectDocIdInMiniDocList(Input.sourceDocId1);

		// FamilyMember tab View All Documents
		driver.waitForPageToBeReady();
		docView.performConceptualSelectSetOfDocsActionViewInDocList();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 10/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the saved
	 * search, then user can view the documents in the doc list from Doc View->Near
	 * Dupe panel.'RPMXCON-50877' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 26)
	public void verifySaveSearchViewInDocListNearDupeTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50877");
		baseClass.stepInfo(
				"To verify that if user navigates to doc view from the saved search, then user can view the documents in the doc list from Doc View->Near Dupe panel.");

		loginPage = new LoginPage(driver);

		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Savebtn" + Utility.dynamicNameAppender();

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.savedSearchToDocView(BasicSearchName);

		// FamilyMember tab View All Documents
		driver.waitForPageToBeReady();
		docView.performNearDupeSelectDocsActionViewInDocList();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 10/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that if user navigates to doc view from the Basic
	 * search, then user can view the documents in the doc list from Doc View->Near
	 * Dupe panel.'RPMXCON-50876' Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 27)
	public void verifyViewInDocListNearDupeTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50876");
		baseClass.stepInfo(
				"To verify that if user navigates to doc view from the saved search, then user can view the documents in the doc list from Doc View->Near Dupe panel.");

		loginPage = new LoginPage(driver);

		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.nearDupeCompletedDocId);

		// FamilyMember tab View All Documents
		driver.waitForPageToBeReady();
		docView.performNearDupeSelectDocsActionViewInDocList();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 18/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify 'View All Documents' button should be displayed on
	 * Analytics Panel Child Window > Conceptual Similar tab when more than 20
	 * documents exists.'RPMXCON-51319' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 28)
	public void verifyViewAllDocumentsDisplayInConceptualTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51319");
		baseClass.stepInfo(
				"Verify 'View All Documents' button should be displayed on Analytics Panel Child Window > Conceptual Similar tab when more than 20 documents exists.");

		loginPage = new LoginPage(driver);

		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsListAndCheckTheConceptualSimilarDocsSize();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.selectDocsFromMiniDocsListAndCheckTheConceptualSimilarDocsSize();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 18/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify 'View All Documents' button should be displayed on
	 * Analytics Panel Child Window > Family Members tab when more than 20 documents
	 * exists.'RPMXCON-51317' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 29)
	public void verifyViewAllDocumentsDisplayInFamilyMemberTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51317");
		baseClass.stepInfo(
				"Verify 'View All Documents' button should be displayed on Analytics Panel Child Window > Family Members tab when more than 20 documents exists.");

		loginPage = new LoginPage(driver);

		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDocument);

		driver.waitForPageToBeReady();
		docView.selectDocsFromMiniDocsListAndCheckTheFamilyMemberDocsSize();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.selectDocsFromMiniDocsListAndCheckTheFamilyMemberDocsSize();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
	}

	/**
	 * @Author : Mohan date: 18/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify Sys Admin/Project Admin after impersonating-
	 *              Conceptual tab if there are no conceptual similar documents
	 *              'RPMXCON-50832' stabilization done
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyAfterImpersonatingConceptualTabNoDocuments() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50832");
		baseClass.stepInfo(
				"To verify Sys Admin/Project Admin after impersonating- Conceptual tab if there are no conceptual similar documents");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docId1 = "T2445D";
		String docId2 = "H16135-0188-001143";

		// Login as SA
		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Step 2: Impersonate From SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 3: Create an Assignment and View in DocView");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, Input.codeFormName);
		assignmentsPage.add3ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo("Step 4: Verify 'Conceptually Similar Documents' tab in the analytics panel");
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docId1);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Step 2: Impersonate From PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("Step 3: Select an Assignment and go to View in DocView");

		assignmentsPage.selectAssignmentToViewinDocview(assname);

		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 4: Verify 'Conceptually Similar Documents' tab in the analytics panel");
		docView.selectDocIdInMiniDocList(docId1);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData());
		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Step 2: Impersonate From PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		baseClass.stepInfo("Step 3: Select an Assignment and go to View in DocView");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 4: Verify 'Conceptually Similar Documents' tab in the analytics panel");
		docView.selectDocIdInMiniDocList(docId1);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Step 2: Impersonate From RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo("Step 3: Select an Assignment and go to View in DocView");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 4: Verify 'Conceptually Similar Documents' tab in the analytics panel");
		docView.selectDocIdInMiniDocList(docId2);
		driver.waitForPageToBeReady();

		baseClass.waitTillElemetToBeClickable(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData());
		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();
	}

	/**
	 * @Author : Mohan date: 18/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify for Project Admin Conceptual tab when there are no
	 *              conceptually similar documents 'RPMXCON-50831' stabilization
	 *              done
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyProjectAdminConceptualTabNoDocuments() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		String docId1 = "T2445D";

		baseClass.stepInfo("Test case Id: RPMXCON-50831");
		baseClass.stepInfo(
				"To verify for Project Admin Conceptual tab when there are no conceptually similar documents");

		// login as PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 2: Basic search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 3: Verify 'Conceptually Similar Documents' tab in the analytics panel");
		docView.selectDocIdInMiniDocList(docId1);

		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

	}

	/**
	 * @Author : Mohan date: 21/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify conceptual similar documents should be displayed as
	 *              per the clicked document from mini doc list panel and from
	 *              document navigation 'RPMXCON-50833'
	 * 
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 32)
	public void verifyConceptualSimilarDocsDisplayedAsPerDocsSelectedFromMiniDocListPanel(String fullName,
			String userName, String password) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50833");
		baseClass.stepInfo(
				"To verify conceptual similar documents should be displayed as per the clicked document from mini doc list panel and from document navigation");

		// login as PA
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo("Step 1: Basic search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		baseClass.stepInfo("Step 2: Click the document one by one from mini doc list panel");
		for (int i = 1; i <= 5; i++) {
			baseClass.waitTillElemetToBeClickable(miniDocListpage.getClickDocviewID(i));
			miniDocListpage.getClickDocviewID(i).waitAndClick(5);
			String docId = docView.getDocView_CurrentDocId().getText();
			System.out.println(docId);
			baseClass.passedStep("User is able see the document one by one in doc view section successfully");
			driver.waitForPageToBeReady();

		}

		baseClass.stepInfo("Step 3: Verify the conceptual similar documents from analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Conceptual similar documents is displayed as per the clicked document from mini doc list panel and from document navigation.");

		loginPage.logout();

	}

	/**
	 * @Author : Mohan date: 21/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify User will be able to see all the conceptually similar
	 *              documents in the analytics panel of the main selected document
	 *              in the Doc view 'RPMXCON-50827'
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyUserIsAbleToSeeAllConceptualDocsWenMainDocsIsSelected() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		String assname = "assgnment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-50827");
		baseClass.stepInfo(
				"To verify User will be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");

		// login as RMU
		baseClass.stepInfo("Step 1: Login As RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 2: Create Assignment and Navigate to DocView");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignConceptualDocuments();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codeFormName, 0);

		assignmentsPage.selectAssignmentToViewinDocview(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 3:Verify 'Conceptually Similar Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"RMU is be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 2: Select Assignment and Navigate to Docview");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 3:Verify 'Conceptually Similar Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"RMU is be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");

		loginPage.logout();

	}

	/**
	 * @Author : Mohan date: 21/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify for RMU Near Dupe tab if there are no near duplicate
	 *              documents 'RPMXCON-50825'
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyRMUNearDupeTabWithMsgAsNoDocuments() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String docId1 = "T2445D";
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-50825");
		baseClass.stepInfo("To verify for RMU Near Dupe tab if there are no near duplicate documents");

		// login as PA
		baseClass.stepInfo("Step 1: Login As RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa1userName + "");
		baseClass.stepInfo(
				"Step 2: Select Assignment and action as 'View All Docs in Doc View' from Action drop down of manage assignment page     ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 3: Verify 'Near Duplicate Documents' tab in the analytics panel");
		docView.selectDocIdInMiniDocList(docId1);

		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_NearDupeTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

	}

	/**
	 * @Author : Mohan date: 18/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify Sys Admin/Project Admin/RMU after impersonating-
	 *              Family Members tab when no documents 'RPMXCON-50817'
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyAfterImpersonatingFamilyMemberTabNoDocuments() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50817");
		baseClass.stepInfo(
				"To verify Sys Admin/Project Admin/RMU after impersonating- Family Members tab when no documents");

		// Login as SA
		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Step 2: Impersonate From SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Verify 'Family Members' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Step 2: Impersonate From SA to Reviewer");
		baseClass.impersonateSAtoReviewer();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Verify 'Family Members' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Step 2: Impersonate From SA to PA");
		baseClass.impersonateSAtoPA();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Verify 'Family Members' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Step 2: Impersonate From PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Verify 'Family Members' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Step 2: Impersonate From PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Verify 'Family Members' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();

		baseClass.stepInfo("Step 1: Login As RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Step 2: Impersonate From RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Verify 'Family Members' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		driver.waitForPageToBeReady();

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberTabQueryNoData().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Message is displayed as 'Your query returned no data.'");

		loginPage.logout();
	}

	/**
	 * @Author : Mohan date: 25/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify Near Duplicate documents should be displayed as per
	 *              the clicked document from mini doc list panel and from document
	 *              navigation 'RPMXCON-50826'
	 * 
	 */

	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" },
	priority = 36)
	public void verifyNearDupeDocsDisplayedAsPerDocsSelectedFromMiniDocListPanel(String fullName, String userName,
			String password) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50826");
		baseClass.stepInfo(
				"To verify Near Duplicate documents should be displayed as per the clicked document from mini doc list panel and from document navigation");

		// Login
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo("Step 1: Basic search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		baseClass.stepInfo("Step 2: Click the document one by one from mini doc list panel");
		for (int i = 1; i <= 3; i++) {
			baseClass.waitTillElemetToBeClickable(miniDocListpage.getClickDocviewID(i));
			miniDocListpage.getClickDocviewID(i).waitAndClick(5);
			String docId = docView.getDocView_CurrentDocId().getText();
			System.out.println(docId);
			baseClass.passedStep("User is able see the document one by one in doc view section successfully");
			driver.waitForPageToBeReady();

		}

		baseClass.stepInfo("Step 3: Verify the Near Duplicate documents from analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_NearDupeWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Near duplicate documents is be displayed as per the clicked document from mini doc list panel and from document navigation.");

		loginPage.logout();

	}

	/**
	 * @Author : Mohan date: 18/01/2022 Modified date: NA Modified by: NA
	 * @Description :To verify user after impersonation should view threaded
	 *              document for the selected document from mini doc list
	 *              'RPMXCON-50950' Require DA credentials
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyAfterImpersonatingThreadedDocumentsForSelectedDocsInMiniDocList() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-50950");
		baseClass.stepInfo(
				"To verify user after impersonation should view threaded document for the selected document from mini doc list");

		// Login as SA
		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Step 2: Impersonate From SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo(
				"Step 3: Select Assignment and action as 'View All Docs in Doc View' from Action drop down of manage assignment page");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		assignmentsPage.assignmentCreation(assname, Input.codeFormName);
		assignmentsPage.add3ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 4: Verify 'Thread Map' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);

		baseClass.waitTime(1);
		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ThreadMapFirstRow().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Threaded documents for the selected document from mini doc list is displayed.LoadEmailThreadings_A order by DocumentID asc");

		driver.waitForPageToBeReady();
		loginPage.logout();

		// Login as DA
		baseClass.stepInfo("Step 1: Login As DA");
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);

		baseClass.stepInfo("Step 2: Impersonate From DA to RMU");
		baseClass.impersonateDAtoRMU();

		assignmentsPage.selectAssignmentToViewinDocview(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 3: Verify 'Thread Map' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ThreadMapFirstRow().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Threaded documents for the selected document from mini doc list is displayed.LoadEmailThreadings_A order by DocumentID asc");

		driver.waitForPageToBeReady();
		loginPage.logout();

		// Login as PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Step 2: Impersonate From PA to RMU");
		baseClass.impersonatePAtoRMU();

		assignmentsPage.selectAssignmentToViewinDocview(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 3: Verify 'Thread Map' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ThreadMapFirstRow().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Threaded documents for the selected document from mini doc list is displayed.LoadEmailThreadings_A order by DocumentID asc");

		driver.waitForPageToBeReady();
		loginPage.logout();

		// Login as PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Step 2: Impersonate From PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo("Step 3: Verify 'Thread Map' tab in the analytics panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentThreadMap());
		docView.getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ThreadMapFirstRow().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Threaded documents for the selected document from mini doc list is displayed.LoadEmailThreadings_A order by DocumentID asc");

		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description To verify that user can select documents from the Conceptually
	 *              Similar and folder them when redirects from manage assignment.
	 *              'RPMXCON-48693'
	 * @Stabilization - done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyUserCanSelectDocumentFromAnalyticsPanelConceputuallySimilarTab()
			throws ParseException, InterruptedException, IOException {
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48693");
		baseClass.stepInfo(
				"To verify that user can select documents from the Conceptually Similar and folder them when redirects from manage assignment.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		String foldName = "FolderName" + Utility.dynamicNameAppender();
		String conceptualFolder = "ConceptualFolder" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		sessionSearch.bulkFolderConceptualDocs(foldName);
		sessionSearch.performBulkAssignDocsAction();

		baseClass.stepInfo("Step 3: Go to Assignment > DocView");
		assignmentsPage.assignmentCreation(assname, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo(
				"Step 4: Select document from conceptual similar tab and perform folder action from action drop-down ");
		docView.selectDocsAndActionAsFolder(1, conceptualFolder);

		baseClass.stepInfo(
				"Step 5: Select the same document from Conceptually Similar tab Select action as View Document Select Folder tab");
		docView.selectDocsFromConceptualTabAndViewTheDocs();

		baseClass.waitTime(1);
		String docID = docView.getDocView_CurrentDocId().getText();
		System.out.println(docID);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(docView.getDocView_FolderTab());
		docView.getDocView_FolderTab().waitAndClick(5);

		baseClass.waitForElement(docView.getDocView_FolderTab_Expand());
		docView.getDocView_FolderTab_Expand().waitAndClick(5);

		docView.getDocView_MetaData_FolderName(conceptualFolder).ScrollTo();
		softAssertion.assertTrue(docView.getDocView_MetaData_FolderName(conceptualFolder).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Folderis displayed under Folder tab in Metadata section successfully");

		baseClass.stepInfo("Step 6: Navigate to search page and switch to advanced search");
		docView.selectAdvancedSearch(conceptualFolder);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		baseClass.waitForElement(sessionSearch.getPureHitsCountNumText());
		softAssertion.assertTrue(sessionSearch.getPureHitsCountNumText().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("the number of documents added into the folder and its doc-id is verified successfully");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.passedStep("Application is redirect to the Doc View page successfully");

		baseClass.stepInfo(
				"Step 5: Select the same document from Conceptually Similar tab Select action as View Document Select Folder tab");
		docView.selectDocsFromConceptualTabAndViewTheDocs();

		baseClass.waitTime(1);
		String docID1 = docView.getDocView_CurrentDocId().getText();
		System.out.println(docID1);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(docView.getDocView_FolderTab());
		docView.getDocView_FolderTab().waitAndClick(5);

		baseClass.waitForElement(docView.getDocView_FolderTab_Expand());
		docView.getDocView_FolderTab_Expand().waitAndClick(5);

		docView.getDocView_MetaData_FolderName(conceptualFolder).ScrollTo();
		softAssertion.assertTrue(docView.getDocView_MetaData_FolderName(conceptualFolder).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Folderis displayed under Folder tab in Metadata section successfully");

		baseClass.stepInfo("Step 6: Navigate to search page and switch to advanced search");
		docView.selectAdvancedSearch(conceptualFolder);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		baseClass.waitForElement(sessionSearch.getPureHitsCountNumText());
		softAssertion.assertTrue(sessionSearch.getPureHitsCountNumText().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("the number of documents added into the folder and its doc-id is verified successfully");

		loginPage.logout();

	}

	/**
	 * @throws InterruptedException
	 * @Author Krishna Created date: NA Modified date: NA Modified by:NA
	 * @Description Verify that on thread map tab when the principal document is F1,
	 *              the thread map should not present any emails. 'RPMXCON-51521'
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyThreadMapPrincipalDocIsF1NotPresentAnyEmails() throws InterruptedException {
		loginPage = new LoginPage(driver);
		docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51521");
		String docId = "T445D";
		baseClass.stepInfo(
				"Verify that on thread map tab when the principal document is F1, the thread map should not present any emails.");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as PA with " + Input.pa1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocId7, Input.sourceDocIdSearch);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocView();
		baseClass.stepInfo(Input.sourceDocId7 + "..Document searched in metadata sourcedocid with ingestion name");
		driver.waitForPageToBeReady();
		miniDocListpage.selectSourceDocIdInAvailableField();

		// view the doc From MiniDocList and ThreadMap Tab
		docViewAnalytics.selectTextBoxInDocView(docId, docId);

		// verify Thread map should not present any other emails
		docViewAnalytics.verifyThreadMapWithNoEmailDocs();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 15/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify when RMU/Reviewer clicks Complete Same as Last Doc, when
	 * preceding document is completed by selecting 'Code same as this' action from
	 * analytics panel > conceptual.'RPMXCON-51071' Sprint : 8
	 * 
	 * @Stabilization - done
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void verifyCompleteLastDocsAndCodeSameAsActionConceptual() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51071");
		baseClass.stepInfo(
				"Verify when RMU/Reviewer clicks Complete Same as Last Doc, when preceding document is completed by selecting 'Code same as this' action from analytics panel > conceptual");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String documentToBeSelected = Input.MetaDataId;
		String unSelectedDoc=Input.nearDupeCompletedDocIdReviewer;
		String unSelectedDoc1=Input.analyticsConceptualDocId2;
		String codingForm =Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as Conceptual Documents
		docView.performCodeSameForConceptualDocuments();

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();
		
		
		//Select unselected Docs and perform Code same as last
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDoc);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		baseClass.waitForElement(docView.getLastCodeSameAsIcon());
		docView.getLastCodeSameAsIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");

		// perform code same as Conceptual Documents
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDoc1);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docView.performCodeSameAsForConceptualDocumentsForThirdDoc();

		// Coding Stamp Selection And code Same As Verify
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampColour);

		driver.waitForPageToBeReady();
		docView.perfromLastCodeSameAsIcon();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		String docsToBeSelected =  Input.NewDocId;
		String unSelectedDocs2 =  Input.MiniDocId;
		String unSelectedDocs3 = Input.analyticsConceptualDocId1;
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// perform code same as Conceptual Documents
		docView.performCodeSameForConceptualDocuments();

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// Select unselected Docs and perform Code same as last
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs2);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		baseClass.waitForElement(docView.getLastCodeSameAsIcon());
		docView.getLastCodeSameAsIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");

		// perform code same as Conceptual Documents
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs3);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docView.performCodeSameAsForConceptualDocumentsForThirdDoc();

		// Coding Stamp Selection And code Same As Verify
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampColour);

		driver.waitForPageToBeReady();
		docView.perfromLastCodeSameAsIcon();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 20/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify when RMU/Reviewer clicks Complete Same as Last Doc, when
	 * preceding document is completed by selecting 'Code same as this' action from
	 * analytics panel > family member.'RPMXCON-51070' Sprint : 8
	 * 
	 * @Stabilization - not done [code same as last icon is not clickable]
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyCompleteLastDocsAndCodeSameAsActionFamilyMember() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51070");
		baseClass.stepInfo(
				"Verify when RMU/Reviewer clicks Complete Same as Last Doc, when preceding document is completed by selecting 'Code same as this' action from analytics panel > family member");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String documentToBeSelected = Input.familyDocId01;
		String unSelectedDocs=Input.familyDocId02;
		String unSelectedDocs1=Input.familyDocId03;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// select docs from family member and action as code same as
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// Select unselected Docs and perform Code same as last
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		baseClass.waitForElement(docView.getLastCodeSameAsIcon());
		docView.getLastCodeSameAsIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");

		// select docs from family member and action as code same as
		
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs1);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSameAsThirdDocs();


		// Coding Stamp Selection And code Same As Verify
		driver.waitForPageToBeReady();
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampColour);

		driver.waitForPageToBeReady();
		docView.perfromLastCodeSameAsIcon();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docid from MiniDocList
		String docsToBeSelected=Input.familyDocIdForReviewer01;
		String unSelectedDocs2=Input.familyDocIdForReviewer02;
		String unSelectedDocs3=Input.familyDocIdForReviewer03;;
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// select docs from family member and action as code same as
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// Select unselected Docs and perform Code same as last
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs2);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		baseClass.waitForElement(docView.getLastCodeSameAsIcon());
		docView.getLastCodeSameAsIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");

		// select docs from family member and action as code same as

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs3);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSameAsThirdDocs();

		// Coding Stamp Selection And code Same As Verify
		driver.waitForPageToBeReady();
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampColour);

		driver.waitForPageToBeReady();
		docView.perfromLastCodeSameAsIcon();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 24/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify when RMU/Reviewer clicks Complete Same as Last Doc, when
	 * preceding document is completed by selecting 'Code same as this' action from
	 * analytics panel > Near Dupe.'RPMXCON-51072' Sprint : 8
	 * 
	 * @Stabilization - done [code same as last icon is disabled]
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyCompleteLastDocsAndCodeSameAsActionNearDupe() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51072");
		baseClass.stepInfo(
				"Verify when RMU/Reviewer clicks Complete Same as Last Doc, when preceding document is completed by selecting 'Code same as this' action from analytics panel > NearDupe");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		String documentToBeSelected = Input.nearDupeDocId;
		String unSelectedDocs=Input.nearDupeDocId02;
		String unSelectedDocs1=Input.sourceDocId5;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		docView.selectNearDupePureHit();
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(documentToBeSelected);

		// select docs from NearDupe and action as code same as
		docView.performCodeSameForNearDupeDocuments(1);

		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// Select unselected Docs and perform Code same as last
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		baseClass.waitForElement(docView.getLastCodeSameAsIcon());
		docView.getLastCodeSameAsIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs1);

		// select docs from family member and action as code same as
		docView.performCodeSameForNearDupeDocuments(3);

		// Coding Stamp Selection And code Same As Verify
		driver.waitForPageToBeReady();
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampColour);

		driver.waitForPageToBeReady();
		docView.perfromLastCodeSameAsIcon();

		// logout
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// select Doc In MiniDoc List
		String docsToBeSelected=Input.nearDupeDocIdForReviewer01;
		String unSelectedDocs2=Input.nearDupeDocIdForReviewer02;
		String unSelectedDocs3=Input.nearDupeDocIdForReviewer03;
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docsToBeSelected);

		// select docs from NearDupe and action as code same as
		docView.performCodeSameForNearDupeDocuments(1);
		
		// Edit coding Form and complete Action
		docView.editCodingFormComplete();

		// Select unselected Docs and perform Code same as last
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs2);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		baseClass.waitForElement(docView.getLastCodeSameAsIcon());
		docView.getLastCodeSameAsIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(unSelectedDocs3);

		// select docs from family member and action as code same as
		docView.performCodeSameForNearDupeDocuments(1);

		// Coding Stamp Selection And code Same As Verify
		driver.waitForPageToBeReady();
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampColour);

		driver.waitForPageToBeReady();
		docView.perfromLastCodeSameAsIcon();

		// logout
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
			loginPage.quitBrowser();
		} finally {
			// loginPage.clearBrowserCache();
		}

	}

}
