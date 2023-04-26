package sightline.docviewAnalyticsPanel;

import java.io.IOException;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.support.Color;
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
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAnalyticsPanel_Regression2 {
	
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
	TagsAndFoldersPage tagsAndFolderPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
//		Input in =new Input();
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
		return new Object[][] { 
			{ Input.pa1FullName, Input.pa1userName, Input.pa1password },
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

	@Test(description="RPMXCON-50867",enabled = true, groups = { "regression" } )
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

	@Test(description="RPMXCON-50917",enabled = true, groups = { "regression" } ) 
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

	@Test(description="RPMXCON-50918",enabled = true, groups = { "regression" } ) 
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
		assignmentsPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		
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
		docView.selectDocIdInMiniDocList(Input.familyDocIdForReviewer01);
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
		docView.selectDocIdInMiniDocList(Input.familyDocIdForReviewer01);
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
		docView.selectDocIdInMiniDocList(Input.familyDocIdForReviewer01);
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

	@Test(description="RPMXCON-50919",enabled = true, groups = { "regression" } ) 
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
		docView.selectDocIdInMiniDocList(Input.MiniDocId);
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
		docView.selectDocIdInMiniDocList(Input.MiniDocId);
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
		docView.selectDocIdInMiniDocList(Input.MiniDocId);
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
	@Test(description="RPMXCON-50859",enabled = true, groups = { "regression" })
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
	@Test(description="RPMXCON-50813",enabled = true, groups = { "regression" })
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
		baseClass.stepInfo("Step 2: Searching documents based on search string and Navigate to DocView");
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
    @Test(description="RPMXCON-50814",enabled = true, groups = { "regression" })
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
//		MiniDocListPage docList=new MiniDocListPage(driver);
//		List<String> NonuniqueDoc=new ArrayList<String>();
//		docList.checkFamilyMemberForNonUniqueDoc(NonuniqueDoc);
		docView.getFirstDocIdOnMiniDocList().waitAndClick(10);
		
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
	@Test(description="RPMXCON-50821",enabled = true, dataProvider = "userDetails", groups = { "regression" })
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
	

	/**
	 * Author : Vijaya.Rani date: 21/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify user can select Multiple documents in Thread
	 * Map->Analytic Panel from dockout screens and Select Action as 'Code Same as
	 * this'. 'RPMXCON-51127' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51127",enabled = true, groups = { "regression" } )
	public void verifySelectMultipleDocsCodeSameAsThreadMapTab() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51127");
		baseClass.stepInfo(
				"To verify user can select Multiple documents in Thread Map->Analytic Panel from dockout screens and Select Action as 'Code Same as this'.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1:Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.performCodeSameForThreadDocuments(parentWindowID);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();

		// login As REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID1.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.performCodeSameForThreadDocuments(parentWindowID1);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
	}

	/**
	 * Author : Vijaya.Rani date: 21/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify user can assign documents to selected folder from
	 * Dockout screen->Analytics panel- Near Dupe tab.'RPMXCON-51129' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51129",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifySelectDocsInFolderFromNearDupeTab(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51129");
		baseClass.stepInfo(
				"To verify user can assign documents to selected folder from Dockout screen->Analytics panel-Near Dupe tab.");

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
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.nearDupeDocId01);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// view docs DocView
		docView.performFloderNearDupeDocsInChildWindow(parentWindowID);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
	}
	
	/**
	 * Author : Vijaya.Rani date: 24/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify RMU can select documents from Family member and view
	 * the existing folders. 'RPMXCON-50818' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50818",enabled = true, groups = { "regression" } )
	public void verifySelectMultipleDocsFolderFamilyMemberTab() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50818");
		baseClass.stepInfo("To verify RMU can select documents from Family member and view the existing folders.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		String text = "SaveFolder" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(Input.familyDocument);

		// FamilyMember Folder Action
		docView.Analytics_FamilyActionsFolderMultipleDocument(text);
		tagsAndFolderPage.getFolderName(text).ScrollTo();
		softAssertion.assertTrue(tagsAndFolderPage.getFolderName(text).isDisplayed());
		baseClass.passedStep("All existing folder under that security group is " + text + "displayed");

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 24/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify for RMU Conceptual tab when there are no conceptually
	 * similar documents. 'RPMXCON-50830' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50830",enabled = true, groups = { "regression" })
	public void verifyThereAreNoDocsInConceptualSimilartab() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50830");
		baseClass.stepInfo("To verify for RMU Conceptual tab when there are no conceptually similar documents.");

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
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Navigating Doc view from Assignment Page");
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		// select Doc In MiniDoc List
		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList("ID00000903");

		//No Data For Conceptual Tab
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnalyticalPanel_NoData());
		String familyData = docView.getDocView_AnalyticalPanel_NoData().getText();
		System.out.println(familyData);
		softAssertion.assertTrue(docView.getDocView_AnalyticalPanel_NoData().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("AnalyticalPanel ConceptualSimilar Docs Is " + familyData);

		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 25/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify Sys Admin/Domain Admin/Project Admin after
	 * impersonating to RMU should be able to see all the conceptually similar
	 * documents in the analytics panel of the main selected document in the Doc
	 * view. 'RPMXCON-50829' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50829",enabled = true, groups = { "regression" })
	public void verifyAfterImpersonatingSelectDocsViewConceptualSimilarTab() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50829");
		baseClass.stepInfo(
				"To verify Sys Admin/Domain Admin/Project Admin after impersonating to RMU should be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);

		// Login as SA
		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Admin with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 2: Impersonate From SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		baseClass.stepInfo("Step 3:Verify 'Conceptually Similar Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
		
		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("RMU is be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");

		loginPage.logout();

		// Login as PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2: Impersonate From SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		baseClass.stepInfo("Step 3:Verify 'Conceptually Similar Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
		
		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("RMU is be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");
		
		loginPage.logout();

		 //Login as DA
		baseClass.stepInfo("Step 1: Login As DA");
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Domain Admin with " + Input.da1userName + "");

		baseClass.stepInfo("Step 2: Impersonate From DA to RMU");
		baseClass.impersonateDAtoRMU();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewConceptualDocsInDocViews();

		baseClass.stepInfo("Step 3:Verify 'Conceptually Similar Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_liDocumentConceptualSimilarab());
		docView.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
		
		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_ConceptualWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("RMU is be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");
		
		loginPage.logout();

	}
	
	/**
	 * Author : Vijaya.Rani date: 03/02/22 NA Modified date: NA Modified by:NA
	 * Description :To verify Sys Admin/Project Admin after impersonating to RMU
	 * should be able to see all the family members in the analytics panel of the
	 * main selected document in the Doc view. 'RPMXCON-50812' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-50812",enabled = true, groups = { "regression" })
	public void verifyAfterImpersonatingSelectDocsViewFamilyMemberTab() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50812");
		baseClass.stepInfo(
				"To verify Sys Admin/Project Admin after impersonating to RMU should be able to see all the family members in the analytics panel of the main selected document in the Doc view.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as SA
		baseClass.stepInfo("Step 1: Login As SA");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Admin with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 2: Impersonate From SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 3: Basic Search and Navigate to Docview");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignFamilyMemberDocuments();

		baseClass.stepInfo("Step 4: Create new assignment and distribute docs to reviewer");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo("Step 5:Verify 'Family Member Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("RMU is be able to see all the family member documents in the same family.");
		loginPage.logout();

		// Login as PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2: Impersonate From PA to RMU");
		baseClass.impersonatePAtoRMU();
		
		baseClass.stepInfo("Step 3: Select the Assigment go to Docview");
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 4:Verify 'Family Member Documents' tab in the analytics panel");
		baseClass.waitForElement(docView.getDocView_Analytics_FamilyTab());
		docView.getDocView_Analytics_FamilyTab().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_AnalyticsPanel_FamilyMemberWholeTabel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("RMU is be able to see all the family member documents in the same family.");
		loginPage.logout();

	}
	
	/**
	 * Author : Vijaya.Rani date: 04/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify pagination is working from near dupe child window.'RPMXCON-51255' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description="RPMXCON-51255",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyPaginationInNearDupeChildWindow(String fullName, String userName, String password)
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51255");
		baseClass.stepInfo(
				"Verify pagination is working from near dupe child window.");

		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Menager with " + Input.pa1userName + "");

	
		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(Input.highlightedDocsQuery);
		sessionSearch.ViewNearDupeDocumentsInDocView();
		
		//NearDupe Child Window Pagination
		docView.openNearDupeComparisonWindowForDocumentPagination();
		
		loginPage.logout();
	}
	

	/**
	 * Author : Mohan date: 14/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48778 
	 * @description:Verify that the text difference in the Near Dupe window must always highlight differences from the original. 'RPMXCON-48778' Sprint 12
	 */

	@Test(description="RPMXCON-48778",enabled = true,dataProvider = "userDetails", groups = { "regression" }) 
	public void verifyHighlightingTextInNearDupeComparisonWindowWithBasicSearch(String fullName, String userName, String password) throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		
		// login 
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage  with " + fullName + "");
		baseClass.stepInfo("Test case id : RPMXCON-48778");
		baseClass.stepInfo("Verify that the text difference in the Near Dupe window must always highlight differences from the original.");
		
		String docId =Input.highlightDocId;
		
		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 3: Search for documents to get the near dupe documents and drag the result to shopping cart, select action as View in Doc View");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("Auto");
		sessionSearch.ViewInDocView();
		
		
		baseClass.stepInfo("Step 4: View the document from mini doc list having near dupe documents");
		docView = new DocViewPage(driver);
		MiniDocListPage DocList=new MiniDocListPage(driver);
		DocList.checkNearDupeDoc();
//		docView.selectDocIdInMiniDocList(docId);
		String parentWindowID  =driver.getWebDriver().getWindowHandle();
		
		
		baseClass.stepInfo("Step 5: From Analytics panel > Near Dupe window click the icon");
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NearDupeIcon());
		docView.getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		
			
			for (int i = 1; i <=3; i++) {
				if (docView.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
					System.out.println("Comparison Window is Ready to perform next steps");
					break;
				}
				else {
					driver.Navigate().refresh();
				}
			}
				
		baseClass.stepInfo("Step 6: Verify the different highlighting from Original and Near Dupe panel of near dupe comparison window");;
		docView.getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);
		baseClass.waitForElement(docView.get_textHighlightedColor());
		String color = docView.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color).asHex();
		System.out.println(hex2);
		baseClass.passedStep("Near Dupe comparison window is opened and the differences highlighted on the near dupe comparison window");
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
		
	}
	
	/**
	 * Author : Mohan date: 14/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51102 
	 * @description:Verify user will be able to see the differences in two documents in the near dupe window outside of an assignment 'RPMXCON-51102' Sprint 12
	 */

	@Test(description="RPMXCON-51102",enabled = true,dataProvider = "userDetails", groups = { "regression" }) 
	public void verifyHighlightingTextInNearDupeComparisonWindowWithSavedSearch(String fullName, String userName, String password) throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		
		
		// login
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		baseClass.stepInfo("User successfully logged into slightline webpage  with " + fullName + "");
		baseClass.stepInfo("Test case id : RPMXCON-51102");
		baseClass.stepInfo(
				"Verify user will be able to see the differences in two documents in the near dupe window outside of an assignment");
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		String docId =Input.highlightDocId;
		
		//Saved Search and select the pure hit count
		baseClass.stepInfo(
				"Step 2: Go to doc view from basic search/saved search/doc list   Note: Search for the document to get the near dupe result");
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("Auto");
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch = new SavedSearch(driver);
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");
		
		baseClass.stepInfo("Step 3: View the document from mini doc list having near dupe documents");
		docView = new DocViewPage(driver);
		MiniDocListPage docList=new MiniDocListPage(driver);
		docList.checkNearDupeDoc();
//		docView.selectDocIdInMiniDocList(docId);
		
		String parentWindowID  =driver.getWebDriver().getWindowHandle();
		
		
		baseClass.stepInfo("Step 4&5: Click Near Dupe tab from analytical panel and Click the page icon to open the near dupe window");
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NearDupeIcon());
		docView.getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		
			
			for (int i = 1; i <=3; i++) {
				if (docView.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
					System.out.println("Comparison Window is Ready to perform next steps");
					break;
				}
				else {
					driver.Navigate().refresh();
				}
			}
				
		baseClass.stepInfo("Step 6: Verify the near dupe window");;
		docView.getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);
		baseClass.waitForElement(docView.get_textHighlightedColor());
		String color = docView.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color).asHex();
		System.out.println(hex2);
		baseClass.passedStep("Differences in two documents is highlighted in the near dupe window with yellow color");
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
		
	
	
	}
	
	/**
	 * @Author : Mohan date: 14/02/2022 Modified date: NA Modified by: NA
	 * @Description : Verify user after impersonation will be able to see the differences in two documents in the near dupe window outside of an assignment 'RPMXCON-51121'
	 * @Stabilization - done
	 */
	
	@Test(description="RPMXCON-51121",enabled = true,dataProvider="userDetailss", groups = { "regression" })
	public void verifyUserAfterImpersonationDifferenceInTwoDocs(String roll,String userName, String password,String impersonate) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51121");
		baseClass.stepInfo("Verify user after impersonation will be able to see the differences in two documents in the near dupe window outside of an assignment");
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			} 
				
		case "rev":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa")&& impersonate.equalsIgnoreCase("rev")) {
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
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")||roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			String docId =Input.highlightDocId;
			// Basic Search and select the pure hit count
			baseClass.stepInfo("Step 3: Go to doc view from basic search/saved search/doc list   Note: Search for the document to get the near dupe result");
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(Input.highlightedDocsQuery);
			sessionSearch.ViewInDocView();
			
			
			baseClass.stepInfo("Step 4: View the document from mini doc list having near dupe documents");
			docView = new DocViewPage(driver);
			docView.selectDocIdInMiniDocList(docId);
			String parentWindowID  =driver.getWebDriver().getWindowHandle();
			
			
			baseClass.stepInfo("Step 5: Click Near Dupe tab from analytical panel");
			Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}
			
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
			docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docView.getDocView_NearDupeIcon());
			docView.getDocView_NearDupeIcon().waitAndClick(10);

			for (String winHandle : driver.getWebDriver().getWindowHandles()) {
				driver.switchTo().window(winHandle);
				driver.waitForPageToBeReady();
			}

			
				
				for (int i = 1; i <=3; i++) {
					if (docView.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
						System.out.println("Comparison Window is Ready to perform next steps");
						break;
					}
					else {
						driver.Navigate().refresh();
					}
				}
					
			baseClass.stepInfo("Step 6: Verify the near dupe window");;
			docView.getDocView_NearDupe_DocID().WaitUntilPresent();
			String docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
			System.out.println(docidinchildwinodw);
			
			String color = docView.get_textHighlightedYellowColor().getWebElement().getCssValue("fill");
			String hex2 = Color.fromString(color).asHex();
			System.out.println(hex2);
			baseClass.passedStep("Differences in two documents is highlighted in the near dupe window with yellow color");
			
			driver.getWebDriver().close();
			driver.switchTo().window(parentWindowID);
			
			
		}

		loginPage.logout();
	}
	
	/**
	 * Author : Mohan date: 14/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51122 
	 * @description: Verify user will be able to see the differences in two documents in the near dupe window in context of an assignment 'RPMXCON-51122' Sprint 12
	 */

	@Test(description="RPMXCON-51122",enabled = true, groups = { "regression" }) 
	public void verifyDifferenceInTwoDocsNearDupeWindowInContextAnAssignment() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51122");
		baseClass.stepInfo("Verify user will be able to see the differences in two documents in the near dupe window in context of an assignment");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docId =Input.highlightDocId;
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		sessionSearch.basicContentSearch(Input.highlightedDocsQuery);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.assignmentDistributingToReviewer();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		
		baseClass.stepInfo("Step 4: View the document from mini doc list having near dupe documents");
		docView = new DocViewPage(driver);
		docView.selectDocIdInMiniDocList(docId);
		String parentWindowID  =driver.getWebDriver().getWindowHandle();
		
		
		baseClass.stepInfo("Step 5: Click Near Dupe tab from analytical panel");
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NearDupeIcon());
		docView.getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		
			
			for (int i = 1; i <=3; i++) {
				if (docView.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
					System.out.println("Comparison Window is Ready to perform next steps");
					break;
				}
				else {
					driver.Navigate().refresh();
				}
			}
				
		baseClass.stepInfo("Step 6: Verify the near dupe window");;
		docView.getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);
		
		String color = docView.get_textHighlightedYellowColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color).asHex();
		System.out.println(hex2);
		baseClass.passedStep("Differences in two documents is highlighted in the near dupe window with yellow color");
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 4: View the document from mini doc list having near dupe documents");
		docView = new DocViewPage(driver);
		docView.selectDocIdInMiniDocList(docId);
		parentWindowID  =driver.getWebDriver().getWindowHandle();
		
		
		baseClass.stepInfo("Step 5: Click Near Dupe tab from analytical panel");
		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NearDupeIcon());
		docView.getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		
			
			for (int i = 1; i <=3; i++) {
				if (docView.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
					System.out.println("Comparison Window is Ready to perform next steps");
					break;
				}
				else {
					driver.Navigate().refresh();
				}
			}
				
		baseClass.stepInfo("Step 6: Verify the near dupe window");;
		docView.getDocView_NearDupe_DocID().WaitUntilPresent();
		docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);
		
		color = docView.get_textHighlightedYellowColor().getWebElement().getCssValue("fill");
		hex2 = Color.fromString(color).asHex();
		System.out.println(hex2);
		baseClass.passedStep("Differences in two documents is highlighted in the near dupe window with yellow color");
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();

		
		
	}
	
	
	/**
	 * Author : Mohan date: 14/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51173 
	 * @description: Verify RMU after impersonation will be able to see the differences in two documents in the near dupe window in context of an assignment 'RPMXCON-51173' Sprint 12
	 */

	@Test(description="RPMXCON-51173",enabled = true, groups = { "regression" }) 
	public void verifyDifferenceInTwoDocsNearDupeWindowInContextAnAssignmentAndImpersonate() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case id : RPMXCON-51173");
		baseClass.stepInfo("Verify RMU after impersonation will be able to see the differences in two documents in the near dupe window in context of an assignment");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docId =Input.highlightDocId;
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		sessionSearch.basicContentSearch(Input.highlightedDocsQuery);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add2ReviewerAndDistribute();
		
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		
		baseClass.stepInfo("Step 4: View the document from mini doc list having near dupe documents");
		docView = new DocViewPage(driver);
		docView.selectDocIdInMiniDocList(docId);
		String parentWindowID  =driver.getWebDriver().getWindowHandle();
		
		
		baseClass.stepInfo("Step 5: Click Near Dupe tab from analytical panel");
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NearDupeIcon());
		docView.getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		
			
			for (int i = 1; i <=3; i++) {
				if (docView.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
					System.out.println("Comparison Window is Ready to perform next steps");
					break;
				}
				else {
					driver.Navigate().refresh();
				}
			}
				
		baseClass.stepInfo("Step 6: Verify the near dupe window");;
		docView.getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = docView.getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);
		
		String color = docView.get_textHighlightedYellowColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color).asHex();
		System.out.println(hex2);
		baseClass.passedStep("Differences in two documents is highlighted in the near dupe window with yellow color");
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
		
		
	}
	
	/**
	 * Author : Mohan date: 17/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51322 
	 * @description: Verify after impersonation on click of 'View All Documents' all the documents should be 
	 * displayed on Analytics panel > family member, Near Dupe, Conceptual child window 'RPMXCON-51322' Sprint 12
	 * 
	 * Stabilization- Not done
	 * Reason: DA useris not working in this buid 8.0
	 */

	//@Test(description="RPMXCON-51322",enabled = true, groups = { "regression" } 21) 
	public void verifyViewAllDocsEnabledInAnFamilyNearDupeConceptualChildWindow() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case id : RPMXCON-51322");
		baseClass.stepInfo("Verify after impersonation on click of 'View All Documents' all the documents should be displayed on Analytics panel > family member, Near Dupe, Conceptual child window");
		
		
		// Login as USER
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
		baseClass.stepInfo("Loggedin As : " + Input.pa1FullName);
		
		
		String saveSearch = "SaveSearch" + Utility.dynamicNameAppender();
		String folderName = "FolderName"+ Utility.dynamicNameAppender();
		String docId= Input.nearDupeDocId01;
		String docId1= Input.ingestionDocIdNearDupe;
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		sessionSearch.basicSearchWithMetaDataQuery(Input.ingestionQuery, "IngestionName");
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch, Input.shareSearchDefaultSG);

		loginPage.logout();
		// Login as USER
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		savedSearch = new SavedSearch(driver);
		savedSearch.selectWithDefaultSecurityGroupAndFolder(saveSearch, folderName);
		loginPage.logout();
		
		 //login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("Impersonate from SA to PA");
		
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");
		docView.selectDocIdInMiniDocList(docId);
		
		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		String childWindow= driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		
		driver.switchTo().window(parentWindowID);
		
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);
		
		driver.switchTo().window(childWindow);
		
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();

		// login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Impersonate from SA to RMU");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");
		
		docView.selectDocIdInMiniDocList(docId);
		
		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		childWindow= driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		
		driver.switchTo().window(parentWindowID);
		
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);
		
		driver.switchTo().window(childWindow);
		
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
		// login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Impersonate from SA to Reveiwer");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");
		
		docView.selectDocIdInMiniDocList(docId);
		
		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		childWindow= driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		
		driver.switchTo().window(parentWindowID);
		
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);
		
		driver.switchTo().window(childWindow);
		
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
		
		// login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		baseClass.stepInfo("Logged in as User: " + Input.da1userName);

		baseClass.impersonateDAtoPA();
		baseClass.stepInfo("Impersonate from DA to PA");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		childWindow = driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		driver.switchTo().window(parentWindowID);

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);

		driver.switchTo().window(childWindow);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
		
		// login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		baseClass.stepInfo("Logged in as User: " + Input.da1userName);

		baseClass.impersonateDAtoRMU();
		baseClass.stepInfo("Impersonate from DA to RMU");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		childWindow = driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		driver.switchTo().window(parentWindowID);

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);

		driver.switchTo().window(childWindow);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
		
		// login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		baseClass.stepInfo("Logged in as User: " + Input.da1userName);

		baseClass.impersonateDAtoReviewer();
		baseClass.stepInfo("Impersonate from DA to Reviewer");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		childWindow = driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		driver.switchTo().window(parentWindowID);

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);

		driver.switchTo().window(childWindow);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
		
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.pa1userName);

		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonate from PA to RMU");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		childWindow = driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		driver.switchTo().window(parentWindowID);

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);

		driver.switchTo().window(childWindow);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
		
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.pa1userName);

		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Impersonate from PA to Reviewer");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		childWindow = driver.getWebDriver().getWindowHandle();
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		driver.switchTo().window(parentWindowID);

		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);

		driver.switchTo().window(childWindow);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date: 17/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51320 
	 * @description:Verify user after impersonation can see the 'View All Documents' as enabled from Family Members, 
	 * Near Dupes, Conceptually Similar when more than 20 documents exist'RPMXCON-51320' Sprint 12
	 */

	@Test(description="RPMXCON-51320",enabled = true, groups = { "regression" }) 
	public void verifyViewAllDocsEnabledInAnFamilyNearDupeConceptual() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case id : RPMXCON-51320");
		baseClass.stepInfo("Verify user after impersonation can see the 'View All Documents' as enabled from Family Members, Near Dupes, Conceptually Similar when more than 20 documents exist");
		
		
		// Login as USER
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
		baseClass.stepInfo("Loggedin As : " + Input.pa1FullName);
		
		
		String saveSearch = "SaveSearch" + Utility.dynamicNameAppender();
		String folderName = "FolderName"+ Utility.dynamicNameAppender();
		String docId=Input.nearDupeDocId01;
		String docId1=Input.ingestionDocIdNearDupe;
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		sessionSearch.basicSearchWithMetaDataQuery(Input.ingestionQuery, "IngestionName");
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch, Input.shareSearchDefaultSG);

		loginPage.logout();
		// Login as USER
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		savedSearch = new SavedSearch(driver);
		savedSearch.selectWithDefaultSecurityGroupAndFolder(saveSearch, folderName);
		loginPage.logout();
		
		 //login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("Impersonate from SA to PA");
		
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");
		docView.selectDocIdInMiniDocList(docId);
		
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		
		driver.waitForPageToBeReady();
		docView.selectDocInMiniDocList(docId1);
		
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();
		
		loginPage.logout();

		// login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Impersonate from SA to RMU");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");
		
		docView.selectDocIdInMiniDocList(docId);
		
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.selectDocInMiniDocList(docId1);
		
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();
		
		loginPage.logout();
		
		// login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.sa1userName);
		
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Impersonate from SA to Reveiwer");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");
		
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");
		
		docView.selectDocIdInMiniDocList(docId);
		
		
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.selectDocInMiniDocList(docId1);
		
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();
		
		loginPage.logout();
		
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.pa1userName);

		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonate from PA to RMU");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);
		docView.performFamilyMemberDocsCheckAndViewAllDocuments();

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.selectDocInMiniDocList(docId1);
		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		loginPage.logout();

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Logged in as User: " + Input.pa1userName);

		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Impersonate from PA to Reviewer");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.selectDocInMiniDocList(docId1);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1userName);

		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonate from RMU to Reviewer");
		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		docView.selectDocIdInMiniDocList(docId);

		docView.performFamilyMemberDocsCheckAndViewAllDocuments();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.selectDocInMiniDocList(docId1);

		docView.verifyNearDupeTabWithMoreDocs();
		docView.verifyConceptualTabWithMoreDocs();

		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date: 17/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51318 
	 * @description: Verify 'View All Documents' button should be displayed on Analytics Panel Child Window > Near Dupe tab 
	 * when more than 20 documents exists 'RPMXCON-51318' Sprint 12
	 */

	@Test(description="RPMXCON-51318",enabled = true, groups = { "regression" }) 
	public void verifyViewAllDocsEnabledInNearDupeParentAndChildWindow() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case id : RPMXCON-51318");
		baseClass.stepInfo("Verify 'View All Documents' button should be displayed on Analytics Panel Child Window > Near Dupe tab when more than 20 documents exists");
		
		
		// Login as USER
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
		baseClass.stepInfo("Loggedin As : " + Input.pa1FullName);
		
		
		String saveSearch = "SaveSearch" + Utility.dynamicNameAppender();
		String folderName = "FolderName"+ Utility.dynamicNameAppender();
		String docId1=Input.ingestionDocIdNearDupe;
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		
		sessionSearch.basicSearchWithMetaDataQuery(Input.ingestionQuery, "IngestionName");
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch, Input.shareSearchDefaultSG);

		loginPage.logout();
		// Login as USER
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		savedSearch = new SavedSearch(driver);
		savedSearch.selectWithDefaultSecurityGroupAndFolder(saveSearch, folderName);
		loginPage.logout();
		
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Loggedin As : " + Input.pa1FullName);

		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docId1);
		docView.verifyNearDupeTabWithMoreDocs();
		
		driver.waitForPageToBeReady();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docView.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		docView.verifyNearDupeTabWithMoreDocs();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docId1);
		docView.verifyNearDupeTabWithMoreDocs();

		driver.waitForPageToBeReady();
		parentWindowID = driver.getWebDriver().getWindowHandle();
		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		docView.verifyNearDupeTabWithMoreDocs();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
		
		
		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo("Loggedin As : " + Input.rev1FullName);

		docView.selectAdvancedSearch(folderName);
		baseClass.stepInfo("Doc is searched by Advanced Search");

		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Navigated to DocView successfully");

		driver.waitForPageToBeReady();
		docView.selectDocIdInMiniDocList(docId1);
		docView.verifyNearDupeTabWithMoreDocs();

		driver.waitForPageToBeReady();
		parentWindowID = driver.getWebDriver().getWindowHandle();
		docView.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		docView.verifyNearDupeTabWithMoreDocs();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
		
		
		
	}
	
	
	/**
     *Author :Arunkumar date: 28/02/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-51515
	 * Description :Verify that documents on thread map should be in chronological order (oldest email first) by SentDate.
	 * @throws Exception 
	 */
	@Test(description="RPMXCON-51515",enabled = true, groups = {"regression" })
	public void verifyChronologicalOrderBySentDate() throws Exception  {
		String ingestionDataName = Input.IngestionName_PT;
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		UserManagement userManagement=new UserManagement(driver);
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String savedSearchName ="AChronosearch"+ Utility.dynamicNameAppender();

		//Login as PA and verify
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51515");
		baseClass.stepInfo("Verify that documents on thread map should be in chronological order (oldest email first) by SentDate");
		
		
		baseClass.stepInfo("Ingestion Access Verification");
		userManagement.verifyIngestionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);
		ingestionPage.navigateToIngestionPage();
		boolean status = ingestionPage.verifyIngestionpublish(Input.EmailConcatenatedDataFolder);
		 if (status == true) {
	            ingestionDataName=ingestionPage.getPublishedIngestionName(Input.EmailConcatenatedDataFolder);
	        }
		 System.out.println(ingestionDataName);
		
		sessionsearch.basicSearchWithMetaDataQuery(ingestionDataName, "IngestionName");
		sessionsearch.saveSearchAtAnyRootGroup(savedSearchName, Input.shareSearchDefaultSG);
		docView.selectSavedSearchInDefaultSecurityGroupAndGotoDocview(savedSearchName);
		driver.waitForPageToBeReady();
		docView.verifyThreadDocsOnChronologicalOrder();
		loginPage.logout();
		//Login as RMU and verify chronological order by sentdate
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		docView.selectSavedSearchInDefaultSecurityGroupAndGotoDocview(savedSearchName);
		driver.waitForPageToBeReady();
		docView.verifyThreadDocsOnChronologicalOrder();
		loginPage.logout();
		//Login as reviewer and verify chronological order by sentdate
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");
		docView.selectSavedSearchInDefaultSecurityGroupAndGotoDocview(savedSearchName);
		driver.waitForPageToBeReady();
		docView.verifyThreadDocsOnChronologicalOrder();
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
