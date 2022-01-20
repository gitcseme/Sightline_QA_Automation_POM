package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_NewRegression02 {
	
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
	 * Author : Mohan date: 31/12/2021 Modified date: NA Modified by: NA Test CasId:RPMXCON-50867
	 * @description: To verify that user can select document in the Threaded map panel and view in the doc list when redirects from Manage Assignment > Doc View.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyUserCanSelectDocsFromThreadMapAndViewInDocList() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50867");
		baseClass.stepInfo(
				"To verify that user can select document in the Threaded map panel and view in the doc list when redirects from Manage Assignment > Doc View.");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 3: View the document from mini doc list having documents on thread map tab  Check the check box for the document from the Thread Map tab and select action as View in Doc list.");
		docView.selectDocsFromThreadMapAndViewInDocList();
		
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);

		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: View the document from mini doc list having documents on thread map tab  Check the check box for the document from the Thread Map tab and select action as View in Doc list.");
		docView.selectDocsFromThreadMapAndViewInDocList();
		
		
		loginPage.logout();
		
	}
	
	
	/**
	 * Author : Mohan date: 31/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50917 
	 * @description: Verify warning message is prompted when navigates to other page without completing 
	 * or saving 'Code same as this' action from analytics panel > Thread map 'RPMXCON-50917' Sprint 9
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 2) 
	public void verifyWarningMsgWhenUserClicksLeftMenuForThreadMap() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50917");
		baseClass.stepInfo("Verify warning message is prompted when navigates to other page without completing or saving 'Code same as this' action from analytics panel > Thread map");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_YesButton());
		docView.getDocView_Navigate_YesButton().waitAndClick(3);
		
		if (assignmentsPage.dashBoardPageTitle().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();	
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		String text = docView.getDocView_Navigate_ButtonText().getText();
		System.out.println(text);
		baseClass.passedStep("On navigation user action will not be save do you want to continue  - Yes and No buttons is displayed.");
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_YesButton());
		docView.getDocView_Navigate_YesButton().waitAndClick(3);
		
		
		if (assignmentsPage.getAssignmentsInreviewerPg().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();	
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		String text1 = docView.getDocView_Navigate_ButtonText().getText();
		System.out.println(text1);
		baseClass.passedStep("On navigation user action will not be save do you want to continue  - Yes and No buttons is displayed.");
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		loginPage.logout();
		
		
		
	}
	
	
	/**
	 * Author : Mohan date: 31/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50918 
	 * @description: Verify warning message is prompted when navigates to other page without completing or 
	 * saving 'Code same as this' action from analytics panel > Family Members 'RPMXCON-50918' Sprint 9
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 3) 
	public void verifyWarningMsgWhenUserClicksLeftMenuForFamilyMember() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50918");
		baseClass.stepInfo("Verify warning message is prompted when navigates to other page without completing or saving 'Code same as this' action from analytics panel > Family Members");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docToBeSelected = Input.familyDocument;
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_YesButton());
		docView.getDocView_Navigate_YesButton().waitAndClick(3);
		
		if (assignmentsPage.dashBoardPageTitle().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();	
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		String text = docView.getDocView_Navigate_ButtonText().getText();
		System.out.println(text);
		baseClass.passedStep("On navigation user action will not be save do you want to continue  - Yes and No buttons is displayed.");
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_YesButton());
		docView.getDocView_Navigate_YesButton().waitAndClick(3);
		
		
		if (assignmentsPage.getAssignmentsInreviewerPg().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();	
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		String text1 = docView.getDocView_Navigate_ButtonText().getText();
		System.out.println(text1);
		baseClass.passedStep("On navigation user action will not be save do you want to continue  - Yes and No buttons is displayed.");
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		loginPage.logout();
		
		
		
	}
	
	/**
	 * Author : Mohan date: 31/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50919 
	 * @description:Verify warning message is prompted when navigates to other page without completing or 
	 * saving 'Code same as this' action from analytics panel >Conceptual 'RPMXCON-50919' Sprint 9
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 4) 
	public void verifyWarningMsgWhenUserClicksLeftMenuForConceptual() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-50919");
		baseClass.stepInfo("Verify warning message is prompted when navigates to other page without completing or saving 'Code same as this' action from analytics panel >Conceptual");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docToBeSelected = Input.conceptualDocumentReviewer;
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_YesButton());
		docView.getDocView_Navigate_YesButton().waitAndClick(3);
		
		if (assignmentsPage.dashBoardPageTitle().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();	
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		String text = docView.getDocView_Navigate_ButtonText().getText();
		System.out.println(text);
		baseClass.passedStep("On navigation user action will not be save do you want to continue  - Yes and No buttons is displayed.");
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
			baseClass.failedStep("User is not In DocView");
			System.out.println("User is not In DocView");
		}
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		baseClass.waitForElement(docView.getDocView_Navigate_YesButton());
		docView.getDocView_Navigate_YesButton().waitAndClick(3);
		
		
		if (assignmentsPage.getAssignmentsInreviewerPg().isElementAvailable(1)) {
			baseClass.passedStep("Users actions is not saved and user is redirect to the clicked page.");
			System.out.println("User is on Assignment selection Page");
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.selectDocsFromConceptualTabAndActionCodeSame();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().waitAndClick(3);
		
		String text1 = docView.getDocView_Navigate_ButtonText().getText();
		System.out.println(text1);
		baseClass.passedStep("On navigation user action will not be save do you want to continue  - Yes and No buttons is displayed.");
		baseClass.waitForElement(docView.getDocView_Navigate_NoButton());
		docView.getDocView_Navigate_NoButton().waitAndClick(3);
		
		loginPage.logout();
		
		
		
	}
	
	/**
	 * Author : Vijaya.Rani date: 19/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can not view the analytics panel on Doc
	 * View, if preferences set OFF from the assignent. 'RPMXCON-50859' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyNotViewAnalyticalPanelDocViewOffFromAssigment() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50859");
		baseClass.stepInfo(
				"To verify that user can not view the analytics panel on Doc View, if preferences set OFF from the assignent.");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableAnalyticalPanelSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Navigating Doc view from Assignment Page");
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		if (!docView.getDocView_AnalyticalPanelTab().isDisplayed()) {
			baseClass.passedStep("User cannot view the AnalyticalPanel On DocView.");
		} else {
			baseClass.failedStep("User can view the AnalyticalPanel On DocView.");
		}
	}

	/**
	 * Author : Vijaya.Rani date: 19/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify Sys Admin after impersonating to Reviewer should be
	 * able to see all the family members in the analytics panel of the main
	 * selected document in the Doc view. 'RPMXCON-50813' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyAfterImpersonatingSelectDocInFamilyMember() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50813");
		baseClass.stepInfo(
				"To verify Sys Admin after impersonating to Reviewer should be able to see all the family members in the analytics panel of the main selected document in the Doc view.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		// login as SA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonate SA to Reviewer,select assignment and go to Docview");
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Step 2: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDocument);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(10);

		softAssertion.assertTrue(docView.getDocView_Analytics_FamilyTab().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("AnalyticalPanel Family Member Docs Is Displayed Successfully");

	}

	/**
	 * Author : Vijaya.Rani date: 20/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify for RMU Family Members tab when no family members and parent child relationship. 'RPMXCON-50814' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
    @Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyProjectNoFamilyMemberTab() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-50814");
		baseClass.stepInfo(
				"To verify for RMU Family Members tab when no family members and parent child relationship.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Navigating Doc view from Assignment Page");
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.miniConceptualNoDoc);
		
        driver.waitForPageToBeReady();
        baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
        docView.getDocView_Analytics_FamilyTab().waitAndClick(10);
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnalyticalPanel_NoData());
		String familyData=docView.getDocView_AnalyticalPanel_NoData().getText();
		System.out.println(familyData);
		softAssertion.assertTrue(docView.getDocView_AnalyticalPanel_NoData().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("AnalyticalPanel FamilyMember Docs Is " + familyData );

		loginPage.logout();
		
	}
	
    /**
	 * Author : Vijaya.Rani date: 20/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can select documents from the Conceptually Similar and folder them when 
	 * redirects from basic search.'RPMXCON-50821' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 8)
	public void verifySelectDocsInFolderFromConceptalSimilarTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-50821");
		baseClass.stepInfo(
				"To verify that user can select documents from the Conceptually Similar and folder them when redirects from basic search.");

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
		
		//Conceptual new Folder 
		docView.performExitingFolderForConceptualDocuments();
		
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
				loginPage.logout();
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
