package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
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
import automationLibrary.ElementCollection;
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
 * Author :Vijaya.Rani
 */

public class DocView_MiniDocList_Regression2 {
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
	 * Author : Vijaya.Rani date: 16/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify on click of 'Cancel' button configure mini doc list pop
	 * up should be closed.'RPMXCON-51333' Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyToClickGearIconInMiniDocListPopupClosed() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51333");
		baseClass.stepInfo("Verify on click of 'Cancel' button configure mini doc list pop up should be closed");

		// login as PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");
		sessionSearch.basicContentSearch(Input.searchString1);
		miniDocListpage.viewInDocView();

		// Click gear icon open popup window and cancel
		miniDocListpage.performGesrIconCancelBtn();
		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		// Click gear icon open popup window and cancel
		miniDocListpage.performGesrIconCancelBtn();
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		// Click gear icon open popup window and cancel
		miniDocListpage.performGesrIconCancelBtn();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 16/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify sorting from mini doc list when redirected to doc view
	 * outside of an assignment.'RPMXCON-51431' Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifySortingFromMiniDocListRedirectedToDocView() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51431");
		baseClass.stepInfo(
				"Verify user can apply coding stamp for the document once marked as un-complete in an assignment");

		// login as PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.performSortDocIdMiniDocList();
		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.performSortDocIdMiniDocList();
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.performSortDocIdMiniDocList();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 17/12/21 NA Modified date: NA Modified by:NA
	 * Description Verify on click of 'Save Configuration' mini doc list should be
	 * displayed with the selected webfields for Optimized Sort
	 * Order.'RPMXCON-51334' Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyToClickGearIconInMiniListDisplayOptimizedSort() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51334");
		baseClass.stepInfo(
				"Verify on click of 'Save Configuration' mini doc list should be displayed with the selected webfields for Optimized Sort Order");

		// login as PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		miniDocListpage.viewInDocView();

		miniDocListpage.selectSourceDocIdInAvailableField();
		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.selectSourceDocIdInAvailableField();
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.selectSourceDocIdInAvailableField();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 17/12/21 NA Modified date: NA Modified by:NA
	 * Description Verify that EmailAuthorNameAndAddress field should not be
	 * displayed on the configure mini doc list optimized sort tab.'RPMXCON-51524'
	 * Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyEmailAuthorAndAddressNotDisplayInOptimizedSortTab() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51524");
		baseClass.stepInfo(
				"Verify that EmailAuthorNameAndAddress field should not be displayed on the configure mini doc list optimized sort tab");

		// login as PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		miniDocListpage.viewInDocView();

		miniDocListpage.performReviewModeGearIconCheckEmailAuthorAndAddress();
		loginPage.logout();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.performReviewModeGearIconCheckEmailAuthorAndAddress();
		loginPage.logout();

		// LOGIN AS REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");

		// View in DocView
		miniDocListpage.viewInDocView();
		baseClass.stepInfo("Doc are viewed in Docview successfully");

		miniDocListpage.performReviewModeGearIconCheckEmailAuthorAndAddress();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 22/12/21 NA Modified date: NA Modified by:NA
	 * Description : Verify that when mini doclist child window reloads by adding
	 * additional docs then for completed documents checkmark with light blue
	 * highlighting should be displayed.'RPMXCON-51643' Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyMiniDocListChkCheckMarkAndBlueHighlighting() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51643");
		baseClass.stepInfo(
				"Verify that when mini doclist child window reloads by adding additional docs then for completed documents checkmark with light blue highlighting should be displayed");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		docViewPage.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.get1stDocinMiniDoc_ChildDocs());
		docViewPage.get1stDocinMiniDoc_ChildDocs().waitAndClick(10);

		driver.switchTo().window(parentWindowID);

		docViewPage.editCodingFormComplete();

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		docViewPage.scrollingDocumentInMiniDocList();
		driver.waitForPageToBeReady();
		docViewPage.verifyCheckMarkIconFromMiniDocListChildWindow();

		driver.switchTo().window(parentWindowID);

		docViewPage.editCodingFormComplete();

		String parentWindowID2 = driver.getWebDriver().getWindowHandle();

		Set<String> allWindowsId2 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		docViewPage.verifyCheckMarkIconFromMiniDocListChildWindow();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// Click Gear Icon Check Child windoe Symbol
		docViewPage.performGearIconChildWindowSymbol();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 22/12/21 NA Modified date: NA Modified by:NA
	 * Description : Verify sorting from mini doc list when redirected to doc view
	 * in context of an assignment.'RPMXCON-51430' Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifySortFromMiniDocListRedirectedToDocViewAnAssignment() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51430");
		baseClass.stepInfo("Verify sorting from mini doc list when redirected to doc view in context of an assignment");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();

		assignmentsPage.FinalizeAssignmentAfterBulkAssign();
		assignmentsPage.createAssignment_fromAssignUnassignPopup(assignmentName, "Default Project Coding Form");
		driver.scrollingToElementofAPage(assignmentsPage.getAssgn_DocSequence_SortbyMetadata());
		assignmentsPage.Assgnwithdocumentsequence("CustodianName", "Descending");
		baseClass.passedStep("Assignment is created with sort by metadata as descending order--" + assignmentName);
		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
		driver.scrollingToElementofAPage(assignmentsPage.getAssignment_ManageReviewersTab());
		assignmentsPage.addReviewerAndDistributeDocs();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		try {
			assignmentsPage.verifyDescendingMetaDataSorting_DocList(assignmentName, "CustodianName");
			softAssertion.assertAll();
			baseClass.passedStep(
					"sucessfully verified that whether RMU can create Assignments from assign/unassign documents"
							+ "with descending meta data sorting");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep(
					"Exception occured,while verifying  whether RMU can create Assignments from assign/unassign documents"
							+ "with descending meta data sorting");

		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 22/12/21 NA Modified date: NA Modified by:NA
	 * Description : Verify that Principal document should not hide under the header
	 * from mini doc list.'RPMXCON-51640' Sprint : 8
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyPrincipalDocsNotHideUnderMiniDocList() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51640");
		baseClass.stepInfo("Verify that Principal document should not hide under the header from mini doc list");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Select Docs and complete
		docViewPage.verifyPrincipalDocNotHide();

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();

		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		if (docViewPage.getVerifyPrincipalDocument().Displayed()) {
			softAssertion.assertTrue(docViewPage.getVerifyPrincipalDocument().getWebElement().isDisplayed());
			baseClass.passedStep(
					" Mini doc list Completed  Principal document is displayed under the header successfully");
		} else {
			baseClass.failedStep(
					"Mini doc list Completed  Principal document is not displayed under the header successfully");

		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 05/01/22 NA Modified date: NA Modified by:NA
	 * Description: To verify that user can select multiple documents from the 'Mini
	 * Doc List' and marked it as 'Code Same as' and then save.'RPMXCON-50945'
	 * Sprint : 9
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifySelectmultipleDocsMiniDocListCodeSameAndSave() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-50945");
		baseClass.stepInfo(
				"To verify that user can select multiple documents from the 'Mini Doc List' and marked it as 'Code Same as' and then save.");
		docViewPage = new DocViewPage(driver);

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");
		miniDocListpage.viewInDocView();

		docViewPage.docViewMiniDocListCodeSameAs();

		loginPage.logout();

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Basic Search is done successfully");
		miniDocListpage.viewInDocView();

		docViewPage.docViewMiniDocListCodeSameAs();
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 05/01/22 NA Modified date: NA Modified by:NA
	 * Description:Verify when RMU/Reviewer clicks Complete Same as Last Doc, when
	 * preceding document is completed by selecting 'Code same as this' action from
	 * mini doc list.'RPMXCON-51069' Sprint : 9
	 * 
	 * @param fieldValue
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyCodeSameAsThisInPrecedingDocMiniDocList() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-51069");
		baseClass.stepInfo(
				"Verify when RMU/Reviewer clicks Complete Same as Last Doc, when preceding document is completed by selecting 'Code same as this' action from mini doc list.");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		String colourName = "colourName" + Utility.dynamicNameAppender();

		// Login as a Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		docViewPage.docViewMiniDocListCodeSameAsAndComplete(colourName, colour);

		loginPage.logout();

		// Login as a Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		docViewPage.docViewMiniDocListCodeSameAsAndCompleteForReviewer();
		loginPage.logout();
	}

	/**
	 * @Jeevitha
	 * @description : To verify Mini DocList Panel from doc view page for
	 *              RMU/Reviewer when redirects from dashboard-assignment page [RPMXCON-48691]
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
	public void verifyMiniDocListPanelAndDocViewPanel() throws Exception {

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48691");
		baseClass.stepInfo(
				"To verify Mini DocList Panel from doc view page for RMU/Reviewer when redirects from dashboard-assignment page");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// assignment Creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// impersonate from RMU to Rev
		baseClass.impersonateRMUtoReviewer();

		// selecting the assignment
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// verifying the Document Showed in Document Viewing Panel and Selected Document
		// in MiniDoc List
		docViewPage.verifyingTheDocSelectedInMiniDocListAndDocInDocumentViewingPanel();
		String lastDocIdBefore = docViewPage.getlastDocinMiniDocView().getText().trim();
		baseClass.stepInfo(lastDocIdBefore + " : Last Doc Id before Scrolling");

		// Scrolling the MiniDoc List
		docViewPage.scrollingDocumentInMiniDocList();
		String lastDocIdAfter = docViewPage.getlastDocinMiniDocView().getText().trim();
		baseClass.stepInfo(lastDocIdAfter + " : Last Doc Id after Scrolling");

		softAssertion.assertNotEquals(lastDocIdBefore, lastDocIdAfter);

		loginPage.logout();

		// login As reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Loggedin As : " + Input.rev1FullName);

		// selecting the assignment
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// verifying the Document Showed in Document Viewing Panel and Selected Document
		// in MiniDoc List
		docViewPage.verifyingTheDocSelectedInMiniDocListAndDocInDocumentViewingPanel();
		String lastDocIdBFRev = docViewPage.getlastDocinMiniDocView().getText().trim();
		baseClass.stepInfo(lastDocIdAfter + " : Last Doc Id after Scrolling");

		// Scrolling the MiniDoc List
		docViewPage.scrollingDocumentInMiniDocList();
		String lastDocIdAFRev = docViewPage.getlastDocinMiniDocView().getText().trim();
		baseClass.stepInfo(lastDocIdAfter + " : Last Doc Id after Scrolling");

		softAssertion.assertNotEquals(lastDocIdBFRev, lastDocIdAFRev);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Raghuram A date: NA Modified date: 01/18/21 Modified by: Raghuram A Description
	 * : To verify user is able to alter the sort sequence on the mini doc list in
	 * the manual mode when redirects from manage Assignment pageTest Case
	 * Id:RPMXCON-50890 Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 11)
	public void verifyUserAbleToAlterSortSequence() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50890 Sprint 10");
		baseClass.stepInfo(
				"To verify user is able to alter the sort sequence on the mini doc list in the manual mode when redirects from manage Assignment page");

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// creating the Assignment
		String assignmentName = miniDocListpage.assignmentCreation();

		// Selecting the assignment
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		assignmentPage.selectAssignmentToViewinDocView(assignmentName);

		// sorting the default sequence order of webfields and verifying it
		miniDocListpage.savedSearchToSortSequence();

		loginPage.logout();

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Selecting the assignment
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		assignmentPage.selectAssignmentToViewinDocView(assignmentName);

		// verifying the order of web fields
		miniDocListpage.verifyDefaultValueInOptimizedSort();
		baseClass.passedStep("The altered Sort order in previous session is not reflected in this session");

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Loggedin As : " + Input.rev1userName);

		// selecting the assignment
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// sorting the default sequence order of webfields and verifying it
		miniDocListpage.savedSearchToSortSequence();

		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Loggedin As : " + Input.rev1userName);

		// selecting the assignment
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");

		// verifying the order of web fields
		miniDocListpage.verifyDefaultValueInOptimizedSort();
		baseClass.passedStep("The altered Sort order in previous session is not reflected in this session");
		loginPage.logout();
	}
	
	/**
	 * @Author : Vijaya.Rani date: 11/02/2022 Modified date: NA Modified by: NA
	 * @Description : Verify warning message is prompted to the user when user
	 *              clicks browser back button without completing or saving action
	 *              from mini doc list panel.'RPMXCON-50922' Sprint-12
	 * 
	 * 
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyNavigatesToOtherPageUsingYesNoBtn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50922");
		baseClass.stepInfo(
				"Verify warning message is prompted to the user when user clicks browser back button without completing or saving action from mini doc list panel");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// Select Assignment in Manage Assignment to DocViewPage
		driver.getWebDriver().navigate().back();
		assignmentPage.manageAssignmentToDocViewAsRmu(Asssignment);

		// MiniDocList CodeSame As and Brower back btn
		docViewPage.performBrowerBackButton();
		if(assignmentPage.ActionBtn().Displayed()) {
			baseClass.passedStep("User Can View The SessionSearch Page Successfully");
		} else {
			baseClass.failedStep("User Can not View the SessionSearch Page");
		}
		
		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// MiniDocList CodeSame As and Brower back btn
		docViewPage.performBrowerBackButton();
		if(assignmentPage.getAssignmentsInreviewerPg().Displayed()) {
			baseClass.passedStep("User Can View The DashBoard Page Successfully");
		} else {
			baseClass.failedStep("User Can not View the DashBoard Page");
		}
	}
	
	/**
	 * @Author : Vijaya.Rani date: 16/02/2022 Modified date: NA Modified by: NA
	 * @Description : To verify sorting criteria of manual mode is for that session of user only
	 * and for that assignment only.'RPMXCON-50891' Sprint-12
	 * 
	 * 
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifySortingCriteriaMnualModeForAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50891");
		baseClass.stepInfo(
				"To verify sorting criteria of manual mode is for that session of user only and for that assignment only");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String Assname = "Assigname" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);

		// Select Assignment in Manage Assignment to DocViewPage
		driver.getWebDriver().navigate().back();
		assignmentPage.manageAssignmentToDocViewAsRmu(Asssignment);
		
		//MiniDocList Manual Sort order
		docViewPage.verifyReviewModeSortOrder();
		
		//Rearrange the web fields
		miniDocListpage.sortingVerifyAfterSelectedWebFields();
		
		sessionSearch.bulkAssignWithOutPureHit();
		assignmentPage.assignmentCreation(Assname, Input.codingFormName);

		// Select Assignment in Manage Assignment to DocViewPage
		driver.getWebDriver().navigate().back();
		assignmentPage.manageAssignmentToDocViewAsRmu(Assname);
		ElementCollection docID = miniDocListpage.getListofDocIDinCW();
		List<String> sortOrderAscending = miniDocListpage.availableListofElements(docID);
		ElementCollection docIDs = miniDocListpage.getListofDocIDAfterInterchange();
		List<String> sortOrderAscendings =miniDocListpage.availableListofElements(docIDs);
		if (sortOrderAscending.equals(sortOrderAscendings)) {
			baseClass.failedStep("Values are not in ascending order");
			
		} else {
			baseClass.passedStep("Verified Before Assignment is manual mode sort sequence is not retain the new Assignment.");
		}
		
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
//			loginPage.logout();
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
