package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.Alert;
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
	 * Author : Mohan  date: 24/11/21 NA Modified date: NA Modified by: N/A
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
	 * Author : Mohan date: 17/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51081 
	 * @description: Verify warning message should be displayed when user selects the document which 
	 * is outside of the assignment when allow coding outside reviewer batch is enabled in assignment 
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
		baseClass.stepInfo("Verify warning message should be displayed when user selects the document which is outside of the assignment when allow coding outside reviewer batch is enabled in assignment");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docToBeSelected =Input.warningDocId;
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
		
		baseClass.stepInfo("Step 3: Select the documents from analytics panel outside of the assignment and select action as 'Code same as this'");
		docView.selectDocIdInMiniDocList(docToBeSelected);
		docView.verifyErrorMsgForActionCodeSameAsInConceptual();
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: Select the documents from analytics panel outside of the assignment and select action as 'Code same as this'");
		docView.verifyErrorMsgForActionCodeSameAsInConceptual();
		
		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date: 23/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51081 
	 * @description: Verify warning message is prompted to the user when user clicks browser back button
	 *  without completing or saving from analytics panel 'RPMXCON-50921' Sprint 8
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
		baseClass.stepInfo("Verify warning message is prompted to the user when user clicks browser back button without completing or saving from analytics panel");
		
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
		
		baseClass.stepInfo("Step 3: Select documents from mini doc list and action as 'Code same as this'  Do not click on 'Complete' or 'Save'");
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		
		driver.waitForPageToBeReady();
		driver.Navigate().back();
		
		driver.switchTo().alert().dismiss();
		
		
		if (docView.getDocId().isElementAvailable(1)) {
			baseClass.passedStep("User is on DocView");
			System.out.println("User is on DocView");
			
		}else {
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
		}else {
			baseClass.failedStep("User is not on the clicked Page");
		}
		
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 4: Select documents from analytics panel and action as 'Code same as this'  Do not click on 'Complete' or 'Save' and click browser back button");
		docView.selectDocsFromConceptualTabAndActionCodeSame();	
		
		driver.Navigate().back();
		
		String text = driver.switchTo().alert().getText();
		System.out.println(text);
		baseClass.passedStep("on navigation user action will not be save do you want to continue  - Leave and Cancel buttons is displayed.");
		baseClass.waitTime(3);
		driver.switchTo().alert().dismiss();
		
		loginPage.logout();
	}
	
	/**
	 * Author : Mohan date: 24/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50902 
	 * @description: To verify Threaded Map tab when no document to display for logged in user 'RPMXCON-50902' Sprint 8
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
		
		baseClass.stepInfo("Step 3: Verify threaded map tab when no document to display");
		docView.verifyThreadMapWithNoDocs();
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: Verify threaded map tab when no document to display");
		docView.verifyThreadMapWithNoDocs();
		
		loginPage.logout();
		
		
		
		
	}
	
	
	/**
	 * Author : Mohan date: 24/12/2021 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50905 
	 * @description: To verify user can select Multiple documents in Analytic Panel-> Family Members tab and select Action as 'Code Same as this' 'RPMXCON-50902' Sprint 9
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
		baseClass.stepInfo(" To verify user can select Multiple documents in Analytic Panel-> Family Members tab and select Action as 'Code Same as this'");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		int id=31;
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
		
		baseClass.stepInfo("Step 3: Select the documents from family member which are assigned to the user  Select multiple documents from Family Members and action as 'Code same as this'  ");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		baseClass.stepInfo("Step 4: Edit the coding form of the principle document and save/complete the document");
		docView.editCodingFormComplete();
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rev1userName);
		
		baseClass.stepInfo("Step 2: Go to doc view from my assignment");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 3: Select the documents from family member which are assigned to the user  Select multiple documents from Family Members and action as 'Code same as this'  ");
		docView.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		baseClass.stepInfo("Step 4: Edit the coding form of the principle document and save/complete the document");
		docView.editCodingFormComplete();
		
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
		//	loginPage.clearBrowserCache();
		}

	}

}
