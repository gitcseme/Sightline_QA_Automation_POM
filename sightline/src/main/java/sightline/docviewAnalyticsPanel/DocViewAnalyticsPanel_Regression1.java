package sightline.docviewAnalyticsPanel;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

public class DocViewAnalyticsPanel_Regression1 {
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
//		Input in = new Input();
//		in.loadEnvConfig();
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

	@Test(description="RPMXCON-51081",enabled = true, groups = { "regression" } )
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

	@Test(description="RPMXCON-50921",enabled = true, groups = { "regression" } )
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

	@Test(description="RPMXCON-50902",enabled = true, groups = { "regression" } )
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

	@Test(description="RPMXCON-50905",enabled = true, groups = { "regression" } )
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
		baseClass.impersonateRMUtoReviewer();//assname
		assignmentsPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_DefaultViewTab());
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.getDocView_NumTextBox().Clear();
		docView.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		baseClass.waitTime(2);
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
		List<String> NonuniqueDoc=new ArrayList<String>();
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

	@Test(description="RPMXCON-50907",enabled = true, groups = { "regression" } )
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

	@Test(description="RPMXCON-50943",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50878",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50901",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50908",enabled = true, groups = { "regression" } )
	public void verifyCodeSameAsDocsSelectionForFamilyMember() throws InterruptedException {
		MiniDocListPage docList=new MiniDocListPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50908");
		baseClass.stepInfo(
				"To verify user after impersonation should able to 'Code same as this' on document selection from Family Members panel.");
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		List<String> NonuniqueDocFromMinidocList = new ArrayList<>();

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
		sessionSearch.basicContentSearch("Auto");//Input.searchString1
		sessionSearch.bulkAssignFamilyMemberDocuments();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 2: Create new assignment and distribute docs to reviewer");
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.add3ReviewerAndDistribute();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docList.checkFamilyMemberForNonUniqueDoc(NonuniqueDocFromMinidocList);
		
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
		docList.checkFamilyMemberForNonUniqueDoc(NonuniqueDocFromMinidocList);

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
		docList.checkFamilyMemberForNonUniqueDoc(NonuniqueDocFromMinidocList);

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
		docList.checkFamilyMemberForNonUniqueDoc(NonuniqueDocFromMinidocList);

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
	@Test(description="RPMXCON-48700",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
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
	@Test(description="RPMXCON-48716",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50872",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50873",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50874",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50871",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50870",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50860",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50861",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50862",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-50864",enabled = true, groups = { "regression" } )
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
	@Test(description="RPMXCON-48737",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
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
	@Test(description="RPMXCON-50863",enabled = true, groups = { "regression" } )
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
	 * Author : Vijaya.Rani date: 20/04/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that On Zoom-in, Zoom-out from 'Near Dupe'/'Original'
	 * document panel, zoom bar should change from respective panel of near dupe
	 * comparison window.'RPMXCON-51706' Sprint : 13
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51706",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyZoomInZoomOutInNearDupePanel(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51706");
		baseClass.stepInfo(
				"Verify that On Zoom-in, Zoom-out from 'Near Dupe'/'Original' document panel, zoom bar should change from respective panel of near dupe comparison window.");

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
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// NeaeDupe Comparision Window Zoomin ZoomOut
		docView.verifyNearNupeComparisionWindow();

		//Logoff
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
