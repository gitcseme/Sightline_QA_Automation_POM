package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Set;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_NearDupes {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, IOException, ParseException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws Exception, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify that Search text box should be REMOVED from both the
	 *              Original and NearDupe windows of near dupe comparison window
	 *              RPMXCON-51707
	 * 
	 */
	@Test(priority = 1, enabled = true, groups = { "regression" })
	public void verifySearchTextboxIsDisplayedInNearDupeComparisonWindow()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51707");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		int ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Verify NearDupe Doc purehit

		docViewPage.verifyNdDocumentsPureHits(ndPureHit);
		baseClass.passedStep("Verify whether the near dupe documents are getting listed in Mini Doc List");

		// Open Comparison Window in NearDupes Tab
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep(
				"Verified that the Search text box is REMOVED from both the Original and NearDupe windows of near dupe comparison window");

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();

		// logout RMU user
		loginPage.logout();

		// Login as Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Project Admin with " + Input.pa1userName + "");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Verify NearDupe Doc purehit

		docViewPage.verifyNdDocumentsPureHits(ndPureHit);
		baseClass.passedStep("Verify whether the near dedupe documents are getting listed in Mini Doc List");

		// Open Comparison Window in NearDupes Tab
		parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep(
				"Verified that the Search text box is REMOVED from both the Original and NearDupe windows of near dupe comparison window");

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();
		// Logout PA User
		loginPage.logout();

		// Login as Reviewer User
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Verify NearDupe Doc purehit

		docViewPage.verifyNdDocumentsPureHits(ndPureHit);
		baseClass.passedStep("Verify whether the near dedupe documents are getting listed in Mini Doc List");

		// Open Comparison Window in NearDupes Tab
		parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep(
				"Verified that the Search text box should be REMOVED from both the Original and NearDupe windows of near dupe comparison window");

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify whether the DocID is getting displayed in the near
	 *              dupe comparison window. RPMXCON-51710
	 * 
	 */
	@Test(priority = 2, enabled = true, groups = { "regression" })
	public void verifyDocIdIsDisplayedInNearDupeComparisonWindow()
			throws ParseException, InterruptedException, IOException {

		baseClass.stepInfo("Test case Id: RPMXCON-51710");
		// Login as RMU
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		baseClass.stepInfo("To verify whether the DocID is getting displayed in the near dupe comparison window");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		int ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Verify NearDupe Doc purehit

		docViewPage.verifyNdDocumentsPureHits(ndPureHit);
		baseClass.passedStep("Verify whether the near dedupe documents are getting listed in Mini Doc List");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Comparison Window in NearDupes Tab

		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep(
				"Verify whether the Doc Id is displayed in near dupe comparison window on both original and near dupe sections");

		// Asserting the NearDupesDocId nad OriginalDocId
		softAssertion.assertEquals(docViewPage.getNearDupeDocId().Displayed().booleanValue(), true);
		softAssertion.assertEquals(docViewPage.getOriginalDocId().Displayed().booleanValue(), true);

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();

		// Logout Rmu user
		loginPage.logout();

		// Login As Reviewer
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Verify NearDupe Doc purehit

		docViewPage.verifyNdDocumentsPureHits(ndPureHit);
		baseClass.passedStep("Verify whether the near dedupe documents are getting listed in Mini Doc List");

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Comparison Window in NearDupes Tab

		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep(
				"Verify whether the Doc Id is displayed in near dupe comparison window on both original and near dupe sections");

		// Asserting the NearDupesDocId nad OriginalDocId
		softAssertion.assertEquals(docViewPage.getNearDupeDocId().Displayed().booleanValue(), true);
		softAssertion.assertEquals(docViewPage.getOriginalDocId().Displayed().booleanValue(), true);

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();

		// Logout Reviewer user
		loginPage.logout();

		// Login As Project Admin
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as Project Administrator with "
				+ Input.rmu1userName + "");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Verify NearDupe Doc purehit

		docViewPage.verifyNdDocumentsPureHits(ndPureHit);
		baseClass.passedStep("Verify whether the near dedupe documents are getting listed in Mini Doc List");

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Comparison Window in NearDupes Tab

		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep(
				"Verify whether the Doc Id is displayed in near dupe comparison window on both original and near dupe sections");

		// Asserting the NearDupesDocId nad OriginalDocId
		softAssertion.assertEquals(docViewPage.getNearDupeDocId().Displayed().booleanValue(), true);
		softAssertion.assertEquals(docViewPage.getOriginalDocId().Displayed().booleanValue(), true);

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify that 'Apply Coding to this Near Dupe' is not be
	 *              visible for Project Admin user. RPMXCON-51715
	 * 
	 */
	@Test(priority = 3, enabled = true, groups = { "regression" })
	public void verifyApplyCodingNearDupeNotvisibleForProjectAdminUser()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51715");
		baseClass.stepInfo("To verify that 'Apply Coding to this Near Dupe' is not be visible for Project Admin user");
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Comparison Window in NearDupes Tab

		docViewPage.openNearDupeComparisonWindow();

		// Asserting Apply Coding NearDupe Button
		softAssertion.assertEquals(docViewPage.getApplyCodingNearDedupeBtn().Displayed().booleanValue(), false);

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		softAssertion.assertAll();
		baseClass.passedStep("verify that 'Apply Coding to this Near Dupe' is not be visible for Project Admin user");

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : Verify that 'Apply Coding to this Near Dupe' should be
	 *              functional from near dupe comparison window opened from
	 *              analytics panel child window RPMXCON-51711
	 * 
	 */
	// @Test(priority = 4, enabled = true, groups = { "regression" })
	public void verifyApplyCodingNearDupeDocIdIsDisplayedInNearDupeComparisonWindow()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51711");
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window opened from analytics panel child window");

		// Login As RMU
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		String searchString = Input.searchString1;

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// PopOut Analytics Panel
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window opened from analytics panel child window");
		docViewPage.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID = driver.getWebDriver().getWindowHandle();

		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);

		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// Edit CodingForm and Save
		docViewPage.editCodingFormSave();
		softAssertion.assertAll();

		// logout RMU
		loginPage.logout();

		// Login As Reviewer
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic Search and select the pure hit count
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window opened from analytics panel child window");

		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// PopOut Analytics Panel
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window opened from analytics panel child window");
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// Edit CodingForm and Save
		docViewPage.editCodingFormSave();

		softAssertion.assertAll();

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify 'View in doc list' action when no document in
	 *              Threaded Map panel. RPMXCON-50868
	 * 
	 */
	@Test(priority = 5, enabled = true, groups = { "regression" })
	public void verifyViewInDoclistWhenNoDocumentSelected() {

		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-50868");
		baseClass.stepInfo(
				"Verify when document is completed when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel parent window");
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu2userName + "");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		// Search for doc and bulkassign the docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string and Bulk Assign has been done successfully");

		// Create Assignment and view in docview in Analytics Panel
		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);
		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);
		driver.waitForPageToBeReady();
		docViewPage.viewThreadMapViewInDocList();
		baseClass
				.stepInfo("'View in doc list' action is disabled when no document to display and verified succesfully");

		// logout RMU
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu2userName + "");
		// select an assignment from dashboard and view in doc view
		assignmentsPage.SelectAssignmentByReviewer(assname);
		// Verify 'View in doc list' action is disabled
		driver.waitForPageToBeReady();
		docViewPage.viewThreadMapViewInDocList();
		baseClass
				.stepInfo("'View in doc list' action is disabled when no document to display and verified succesfully");

	}

	/**
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : Verify when document is completed when 'Apply Coding to this
	 *              Near Dupe' is clicked from near dupe comparison window opened
	 *              from analytics panel parent window 'RPMXCON-51712'
	 * 
	 */
	// @Test(priority = 6, enabled = true, groups = { "regression" })
	public void verifyDocCompletedAndIsDisplayedInNearDupeComparisonWindow()
			throws ParseException, InterruptedException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-51712");
		baseClass.stepInfo(
				"Verify when document is completed when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel parent window");
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Near Dupe Comparison window and click Apply coding button
		docViewPage.openNearDupeComparisonWindow();
		docViewPage.clickApplyCodingBtn();
		baseClass.stepInfo(
				"Doc is selected viewed in NearDUpe comparison window and clicked Apply coding button successfully");

		driver.switchTo().window(parentWindowID);

		baseClass.VerifySuccessMessage("Code same performed successfully.");

		// verify Chain link after coding is done and edit coding form
		docViewPage.verifyChainLinkAfterCoding();
		docViewPage.editCodingFormComplete();
		baseClass.stepInfo("Doc is verified with chain link and coding form is completed successfully");

		softAssertion.assertAll();
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		// open neardupe comparison window as reviewer
		docViewPage.openNearDupeComparisonWindow();
		docViewPage.clickApplyCodingBtn();
		baseClass.stepInfo(
				"Doc is selected viewed in NearDUpe comparison window and clicked Apply coding button as Reviewer successfully");

		driver.switchTo().window(parentWindowID1);
		baseClass.VerifySuccessMessage("Code same performed successfully.");

		// verify Chain link after coding is done and edit coding form
		docViewPage.verifyChainLinkAfterCoding();
		docViewPage.editCodingFormComplete();
		baseClass.stepInfo("Doc is verified with chain link and coding form is completed successfully");

		softAssertion.assertAll();

	}

	/**
	 * @throws InterruptedException
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : Verify that 'Apply Coding to this Near Dupe' should be
	 *              functional from near dupe comparison window and document is
	 *              saved 'RPMXCON-51708'
	 * 
	 */
	// @Test(priority = 7, enabled = true, groups = { "regression" })
	public void verifyNearDupeDocIsSaved() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51708");
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window and document is saved");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String docId = "NearDupe";
		// Login As RMU
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();
		// docViewPage.selectThreadMapPureHit();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Selected docs is viewed in the Docview successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// select doc from MiniDoclist
		// docViewPage.selectDocIdInMiniDocList(docId);
		driver.waitForPageToBeReady();
		docViewPage.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// PopOut Analytics Panel
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window opened from analytics panel child window");
		docViewPage.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// Edit CodingForm and Save
		docViewPage.editCodingFormSave();

		softAssertion.assertAll();

		// logout RMU
		loginPage.logout();

		// Login As Reviewer
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		// Basic Search and select the pure hit count
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();
		// docViewPage.selectThreadMapPureHit();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// View NearDupe Doc in DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Selected docs is viewed in the Docview successfully");

		// select doc from MiniDoclist
		// docViewPage.selectDocIdInMiniDocList(docId);
		driver.waitForPageToBeReady();
		docViewPage.selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(docId);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// PopOut Analytics Panel
		baseClass.stepInfo(
				"Verify that 'Apply Coding to this Near Dupe' should be functional from near dupe comparison window opened from analytics panel child window");
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// Edit CodingForm and Save
		docViewPage.editCodingFormSave();

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Near Dupe child window and Save/Complete clicked from coding
	 *              form child window 'RPMXCON-51381'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyCodeAsSameNearDupeSimilarChildWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51381");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Near Dupe child window and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeCompletedDocId;
		String revDocsToBeSelected = Input.nearDupeDocId;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignNearDupeDocstoNewAssgn(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewPage.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Save Icon
		docViewPage.getSaveIcon().Click();

		// popout Analytics Panel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same From Conceptual tab
		docViewPage.performCodeSameForNearDupeDocuments(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// Verify Code As Same Icon
		docViewPage.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		if (docViewPage.getDocView_MiniDocListExpand().isDisplayed()) {
			docViewPage.getDocView_MiniDocListExpand().ScrollTo();
			baseClass.waitForElement(docViewPage.getDocView_MiniDocListExpand());
			docViewPage.getDocView_MiniDocListExpand().waitAndClick(10);
		}

		if (docViewPage.getCodeSameIconMiniDocList().isDisplayed()) {
			baseClass.waitForElement(docViewPage.getCodeSameIconMiniDocList());
			softAssertion.assertEquals(docViewPage.getCodeSameIconMiniDocList().isDisplayed().booleanValue(), true,
					"Scenario 1");
		}

		driver.scrollPageToTop();
		docViewPage.getSaveIcon().Click();

		// popout Coding Form and complete the docs
		docViewPage.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from MiniDocList
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);

		if (docViewPage.getCodeCompleteIconMiniDocList().isDisplayed()) {
			baseClass.waitForElement(docViewPage.getCodeCompleteIconMiniDocList());
			softAssertion.assertEquals(docViewPage.getCodeCompleteIconMiniDocList().isDisplayed().booleanValue(), true,
					"Scenario 2");
		}

		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Expand Analytics Panel

		try {
			if (docViewPage.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewPage.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_AnalyticalPanelExpand());
				docViewPage.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify checkmark in other Tabs
		docViewPage.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
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

		// popout MiniDocList
		docViewPage.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select MiniDocList Docs
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		docViewPage.getSaveIcon().Click();

		// Popout AnalyticsPanel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same In Conceptullay Similar tab
		docViewPage.performCodeSameForNearDupeDocumentsForReviewer(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// Verify Code Same Icon is displayed
		docViewPage.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewPage.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewPage.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_MiniDocListExpand());
				docViewPage.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		try {
			if (docViewPage.getCodeSameIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeSameIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeSameIconMiniDocList().isDisplayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		driver.scrollPageToTop();
		docViewPage.getSaveIcon().Click();

		// popout Coding Form and complete Docs
		docViewPage.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from miniDoclist
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSelected);
		try {
			if (docViewPage.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeCompleteIconMiniDocList().isDisplayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Expand Analytics Panel
		try {
			if (docViewPage.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewPage.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_AnalyticalPanelExpand());
				docViewPage.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify Completed Checkmark is displayed
		docViewPage.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");
		baseClass.passedStep(
				"Verify when Code same action selected from Analytics Panel > Near Dupe child window and Save/Complete clicked from coding form child window");

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Near Dupe and Save/Complete clicked from coding form child
	 *              window'RPMXCON-51379'
	 * @throws InterruptedException
	 */

	// Doc issue
	 @Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyCodeAsSameWithCodingFormChildWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51379");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Near Dupe and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeCompletedDocId;
		String revDocsToBeSelected = Input.nearDupeDocId;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignNearDupeDocstoNewAssgn(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForNearDupeDocuments(1);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();
		baseClass.stepInfo("Pop-out Coding form and Docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForNearDupeDocuments(3);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();
		baseClass.stepInfo("Pop-out Coding form and Docs are completed successfully");

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify when Code same action selected from Analytics Panel > Near Dupe and Save/Complete clicked from coding form child window");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Near Dupe child window and Save/Complete clicked from coding
	 *              form parent window'RPMXCON-51380'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyCodeAsSameNearDupeSimilarParentWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51380");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Near Dupe child window and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeCompletedDocId;
		String revDocsToBeSelected = Input.nearDupeDocId;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignNearDupeDocstoNewAssgn(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout AnalyticsPanel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same In Conceptullay Similar tab
		docViewAnalytics.performCodeSameForNearDupeDocuments(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout AnalyticsPanel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForNearDupeDocumentsForReviewer(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
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

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify when Code same action selected from Analytics Panel > Near Dupe child window and Save/Complete clicked from coding form parent window");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when document is completed after applying stamp when
	 *              'Apply Coding to this Near Dupe' is clicked from near dupe
	 *              comparison window opened from analytics panel
	 *              window'RPMXCON-51713'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyDocsCompletedApplyingStamp() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51713");
		baseClass.stepInfo(
				"Verify when document is completed after applying stamp when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel window");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		String colourName = "colourName" + Utility.dynamicNameAppender();

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignDocstoNewAssgnEnableCodingStamp(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Near Dupe Comparison window and click Apply coding button
		docViewPage.openNearDupeComparisonWindow();
		docViewPage.clickApplyCodingBtn();
		baseClass.stepInfo(
				"Doc is selected viewed in NearDUpe comparison window and clicked Apply coding button successfully");
		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");

		// verify Chain link after coding is done and edit coding form
		docViewPage.verifyChainLinkAfterCoding();
		baseClass.stepInfo("Doc is verified with chain link and coding form is completed successfully");

		// Edit Coding Stamp and Coding Form
		reusableDocViewPage.stampColourSelection(colourName, colour);
		docViewPage.completeDocsAndVerifyCheckMark();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");

		softAssertion.assertAll();
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		// open neardupe comparison window as reviewer
		docViewPage.openNearDupeComparisonWindow();
		docViewPage.clickApplyCodingBtn();
		baseClass.stepInfo(
				"Doc is selected viewed in NearDUpe comparison window and clicked Apply coding button as Reviewer successfully");

		driver.switchTo().window(parentWindowID1);
		baseClass.VerifySuccessMessage("Code same performed successfully.");

		// verify Chain link after coding is done and edit coding form
		docViewPage.verifyChainLinkAfterCoding();
		baseClass.stepInfo("Doc is verified with chain link and coding form is completed successfully");

		// Edit Coding Stamp and Coding Form
		reusableDocViewPage.stampColourSelection(colourName, colour);
		docViewPage.completeDocsAndVerifyCheckMark();

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when document is completed same as last when 'Apply
	 *              Coding to this Near Dupe' is clicked from near dupe comparison
	 *              window opened from analytics panel window'RPMXCON-51714'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	// @Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyDocsCompletedApplyingStampAndCodeSameAsLast() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51714");
		baseClass.stepInfo(
				"Verify when document is completed same as last when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel window");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignDocstoNewAssgnEnableCodingStamp(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// complete the docs
		docViewPage.completeDocsAndVerifyCheckMark();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Open Near Dupe Comparison window and click Apply coding button
		docViewPage.openNearDupeComparisonWindow();
		docViewPage.clickApplyCodingBtn();
		baseClass.stepInfo(
				"Doc is selected viewed in NearDUpe comparison window and clicked Apply coding button successfully");
		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");

		// verify Chain link after coding is done and edit coding form
		docViewPage.verifyChainLinkAfterCoding();
		baseClass.stepInfo("Doc is verified with chain link and coding form is completed successfully");

		// Complete with Code Same As Last
		docViewPage.completeDocsWithCodeSameAsLastAndVerifyCheckMark();

		softAssertion.assertAll();
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// complete the docs
		docViewPage.completeDocsAndVerifyCheckMark();

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		// Open Near Dupe Comparison window and click Apply coding button
		docViewPage.openNearDupeComparisonWindow();
		docViewPage.clickApplyCodingBtn();
		baseClass.stepInfo(
				"Doc is selected viewed in NearDUpe comparison window and clicked Apply coding button successfully");
		driver.switchTo().window(parentWindowID1);
		baseClass.VerifySuccessMessage("Code same performed successfully.");

		// verify Chain link after coding is done and edit coding form
		docViewPage.verifyChainLinkAfterCoding();
		baseClass.stepInfo("Doc is verified with chain link and coding form is completed successfully");

		// Complete with Code Same As Last
		docViewPage.completeDocsWithCodeSameAsLastAndVerifyCheckMark();

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when document is completed when 'Apply Coding to this
	 *              Near Dupe' is clicked from near dupe comparison window opened
	 *              from analytics panel child window'RPMXCON-51716'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	// @Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyDocsInAnalyticsPanelChildWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51716");
		baseClass.stepInfo(
				"Verify when document is completed after applying stamp when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel window");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignDocstoNewAssgnEnableCodingStamp(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics panel
		docViewPage.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// Edit CodingForm and Save
		docViewPage.editCodingFormComplete();
		baseClass.stepInfo("Coding form is edited and docs are verified with the check mark successfully");
		softAssertion.assertAll();

		// logout RMU
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics panel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		// Edit CodingForm and Save
		docViewPage.editCodingFormComplete();
		baseClass.stepInfo("Coding form is edited and docs are verified with the check mark successfully");

		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify when document is completed when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel child window are successfully verified");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when document is completed after applying stamp when
	 *              'Apply Coding to this Near Dupe' is clicked from near dupe
	 *              comparison window opened from analytics panel child
	 *              window'RPMXCON-51717'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	// @Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyDocsByApplyingStampAnalyticsPanelChildWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51717");
		baseClass.stepInfo(
				"Verify when document is completed after applying stamp when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel window");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		String colourName = "colourName" + Utility.dynamicNameAppender();

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignDocstoNewAssgnEnableCodingStamp(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics panel
		docViewPage.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Edit Coding Stamp and Coding Form
		reusableDocViewPage.stampColourSelection(colourName, colour);
		baseClass.stepInfo("Coding Stamp is saved for the assignment successfully");

		driver.waitForPageToBeReady();
		docViewPage.selectStampGearIcon();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");

		softAssertion.assertAll();
		// logout RMU
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics panel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();

		driver.switchTo().window(parentWindowID);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Edit Coding Stamp and Coding Form
		reusableDocViewPage.stampColourSelection(colourName, colour);
		baseClass.stepInfo("Coding Stamp is saved for the assignment successfully");

		docViewPage.selectStampGearIcon();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");
		baseClass.passedStep(
				"To Verify when document is completed after applying stamp when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel child window are successfully verified");

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :To Verify that document pagination should work from near dupe
	 *              comparison window'RPMXCON-51702
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	// @Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyPageNavigationInNearDupeDocumentInComparisonWindow() throws InterruptedException {
		baseClass.stepInfo("Test Case Id: RPMXCON-51702");
		String searchString = Input.searchString1;
		String documentToBeSelected = Input.nearDupeDocumentForReviewer;

		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("To Verify that document pagination should work from near dupe comparison window");

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		int ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());

		sessionSearch.ViewNearDupeDocumentsInDocView();

		baseClass.stepInfo("Verify whether the near dedupe documents are getting listed in Mini Doc List");
		docViewPage.verifyNdDocumentsPureHits(ndPureHit);

		baseClass.stepInfo("To Verify that document pagination should work from near dupe comparison window");

		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.openNearDupeComparisonWindowForDocumentWhichHasMorePages(documentToBeSelected);
		docViewPage.verifyPaginationFromNearDupeWindow();

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		sessionSearch.ViewNearDupeDocumentsInDocView();

		baseClass.stepInfo("Verify whether the near dedupe documents are getting listed in Mini Doc List");
		docViewPage.verifyNdDocumentsPureHits(ndPureHit);

		baseClass.stepInfo(
				"Verify that Near dupe window to see the differences should open, on click of the icon from Analytics Panel > Near Dupe child window");

		parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.openNearDupeComparisonWindowForDocumentWhichHasMorePages(documentToBeSelected);
		docViewPage.verifyPaginationFromNearDupeWindow();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo("Searching documents based on search string");
		sessionSearch.basicContentSearch(searchString);
		docViewPage.selectNearDupePureHit();

		ndPureHit = Integer.parseInt(sessionSearch.getNDHitsCount().getText());
		System.err.println(ndPureHit);

		sessionSearch.ViewNearDupeDocumentsInDocView();

		baseClass.stepInfo("Verify whether the near dedupe documents are getting listed in Mini Doc List");
		docViewPage.verifyNdDocumentsPureHits(ndPureHit);

		baseClass.stepInfo(
				"Verify that Near dupe window to see the differences should open, on click of the icon from Analytics Panel > Near Dupe child window");

		parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.openNearDupeComparisonWindowForDocumentWhichHasMorePages(documentToBeSelected);
		docViewPage.verifyPaginationFromNearDupeWindow();

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify code same chain link icon should be displayed for the
	 *              document selected for code same on thread map'RPMXCON-51361'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyCodeAsSameThreadMapDocuments() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-51361");
		baseClass.stepInfo(
				"Verify code same chain link icon should be displayed for the document selected for code same on thread map");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String documentToBeSelected = Input.conceptualDocs1;

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		// Select Code Same In Thread Map tab
		baseClass.stepInfo("CodeAsSame is performed for the selected docs");
		docViewAnalytics.performCodeSameForThreadedDocuments();

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Docid from MiniDocLis
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		// Perform Code Same for Threaded Docs
		baseClass.stepInfo("CodeAsSame is performed for the selected docs");
		docViewAnalytics.performCodeSameForThreadedDocuments();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :The default columns viewable in the Family Members Tab of the
	 *              Analytics Panel'RPMXCON-51288'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyColumsInFamilyMemBerTabAnalyticalPanel() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-51288");
		baseClass.stepInfo("The default columns viewable in the Family Members Tab of the Analytics Panel");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get family members documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with family meber documents");
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		baseClass.stepInfo("Selecting assignement to view in doc view");
		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);

		baseClass.stepInfo("Verify columns in Family tab in Analytical columns");
		driver.waitForPageToBeReady();
		docViewAnalytics.verifyFamilyMembersTabColumns();

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("Assignment is selected from dashboard and viewed in DocView successfully");
		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Verify columns in Family tab in Analytical columns");
		driver.waitForPageToBeReady();
		docViewAnalytics.verifyFamilyMembersTabColumns();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify default columns viewable in the Near Dupe Tab of the
	 *              Analytics Panel'RPMXCON-51289'
	 * @throws InterruptedException
	 */
   // @Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyColumsInNearDupeTabAnalyticalPanel() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeCompletedDocId;
		String revDocsToBeSelected = Input.nearDupeDocId;

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		
		baseClass.stepInfo("Test case Id: RPMXCON-51289");
		baseClass.stepInfo("Verify default columns viewable in the Near Dupe Tab of the Analytics Panel");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get NEAR DUPE documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with near dupe documents");
		sessionSearch.bulkAssignNearDupeDocuments();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		baseClass.stepInfo("Selecting assignement to view in doc view");
		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);

		baseClass.stepInfo("Selecting Documents which has near dupe documents");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		baseClass.stepInfo("Verify columns in Near Dupe tab in Analytical columns");
		docViewAnalytics.verifyNearDupeTabColumns();

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("Assignment is selected from dashboard and viewed in DocView successfully");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Selecting Documents which has near dupe documents");
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);

		baseClass.stepInfo("Verify columns in Near Dupe tab in Analytical columns");
		docViewAnalytics.verifyNearDupeTabColumns();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify default columns viewable in the Conceptual Tab of the
	 *              Analytics Panel'RPMXCON-51290'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyColumsInConceptualTabAnalyticalPanel() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocument;

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51290");
		baseClass.stepInfo("Verify default columns viewable in the Conceptual Tab of the Analytics Panel");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get Conceptual documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with conceptual documents");
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		baseClass.stepInfo("Selecting assignement to view in doc view");
		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);

		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		baseClass.stepInfo("Verify columns in Conceptual tab in Analytical columns");
		docViewAnalytics.verifyConceptualTabColumns();

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		baseClass.stepInfo("Assignment is selected from dashboard and viewed in DocView successfully");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		baseClass.stepInfo("Verify columns in conceptual tab in Analytical columns");
		docViewAnalytics.verifyConceptualTabColumns();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              thread map and Save/Complete clicked from coding form child
	 *              window'RPMXCON-51371'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyCodeAsSameWithCodingFormChildWindowForThreadedDocs() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51371");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > thread map and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.threadDocWithToolTip;
		String revDocsToBeSeletced = Input.newNearDupeDocId;

		baseClass
				.stepInfo("Searching threaded documents based on search string and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();

		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");
		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		// Perform Code Same for Threaded Docs
		baseClass.stepInfo("CodeAsSame is performed for the selected docs");
		docViewAnalytics.performCodeSameForThreadedDocuments();

		// verify Code As Same Icon Displayed
		baseClass.stepInfo("Verify code same icon is displayed for the selected documents");
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();

		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Pop-out Coding form and Docs are completed");
		docViewAnalytics.popOutCodingFormAndCompleteDocument();

		// Selected docs From MiniDocsList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		// Verify CheckMark is present
		baseClass.stepInfo("Verify whether the Complete CheckMark is displayed or not");
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();

		// logout RMU
		loginPage.logout();

		// Login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocLis
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSeletced);

		// Perform Code Same for Conceptual Docs
		baseClass.stepInfo("CodeAsSame is performed for the selected docs");
		docViewAnalytics.performCodeSameForThreadedDocumentsForReviewer();

		// verify Code As Same Icon Displayed
		baseClass.stepInfo("Verify code same icon is displayed for the selected documents");
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Pop-out Coding form and Docs are completed");
		docViewAnalytics.popOutCodingFormAndCompleteDocument();

		// Select Docid from MiniDocLis
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSeletced);

		// Verify CheckMark is present
		baseClass.stepInfo("Verify whether the Complete CheckMark is displayed or not");
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              thread map child window and Save/Complete clicked from coding
	 *              form parent window'RPMXCON-51372'
	 * @throws InterruptedException
	 */
    //@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyCodeAsSameThreadDocumentsParentWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51372");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > thread map and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocs1;
		String revDocsToBeSeletced = Input.newNearDupeDocId;

		baseClass
				.stepInfo("Searching threaded documents based on search string and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();

		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");
		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout AnalyticsPanel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same In Conceptullay Similar tab
		docViewAnalytics.performCodeSameForThreadDocuments(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

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

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSeletced);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout AnalyticsPanel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForThreadDocumentsForReviewer(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSeletced);
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

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              thread map child window and Save/Complete clicked from coding
	 *              form child window 'RPMXCON-51373'
	 * @throws InterruptedException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyCodeAsSameThreadMapSimilarChildWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51373");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > thread map child window and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocs1;
		String revDocsToBeSeletced = Input.newNearDupeDocId;

		baseClass
				.stepInfo("Searching threaded documents based on search string and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();

		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");
		// bulk Assign and create assignment
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewPage.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Save Icon
		docViewPage.getSaveIcon().Click();

		// popout Analytics Panel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same From Conceptual tab
		docViewPage.performCodeSameForThreadDocuments(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// Verify Code As Same Icon
		docViewPage.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewPage.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewPage.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_MiniDocListExpand());
				docViewPage.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		try {
			if (docViewPage.getCodeSameIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeSameIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeSameIconMiniDocList().isDisplayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		driver.scrollPageToTop();
		docViewPage.getSaveIcon().Click();

		// popout Coding Form and complete the docs
		docViewPage.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from MiniDocList
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		try {
			if (docViewPage.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeCompleteIconMiniDocList().isDisplayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		// Expand Analytics Panel

		try {
			if (docViewPage.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewPage.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_AnalyticalPanelExpand());
				docViewPage.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify checkmark in other Tabs
		docViewPage.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
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

		// popout MiniDocList
		docViewPage.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select MiniDocList Docs
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSeletced);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		docViewPage.getSaveIcon().Click();

		// Popout AnalyticsPanel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same In Conceptullay Similar tab
		docViewPage.performCodeSameForThreadDocumentsForReviewer(parentWindowID);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// Verify Code Same Icon is displayed
		docViewPage.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewPage.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewPage.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_MiniDocListExpand());
				docViewPage.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		driver.scrollPageToTop();
		docViewPage.getSaveIcon().Click();

		// popout Coding Form and complete Docs
		docViewPage.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from miniDoclist
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSeletced);
		try {
			if (docViewPage.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeCompleteIconMiniDocList().isDisplayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Expand Analytics Panel
		try {
			if (docViewPage.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewPage.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_AnalyticalPanelExpand());
				docViewPage.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify Completed Checkmark is displayed
		docViewPage.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Family Member and Save/Complete clicked from coding form parent
	 *              window'RPMXCON-51374'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void verifyCodeAsSameFamilyMemberParentWindow() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.familyDocument;
		String revDocsToBeSelected = Input.familyDocumentForReviewer;

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51374");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Family Member and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get family members documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with family meber documents");
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentsPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		docViewAnalytics.selectAssignmentfromDashborad(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForFamilyMembersDocuments();
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
		assignmentsPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForFamilyMembersDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Family Member and Save/Complete clicked from coding form child
	 *              window'RPMXCON-51375'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyCodeSameFamilyMemberCodingFormChildWindow() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.familyDocument;
		String revDocsToBeSelected = Input.familyDocumentForReviewer;

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51375");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Family Member and Save/Complete clicked from coding form child window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get family members documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with family member documents");
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentsPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Impersonate to Reviewer	
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		docViewAnalytics.selectAssignmentfromDashborad(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForFamilyMembersDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// popout Coding Form and complete Docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();
		driver.scrollPageToTop();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
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

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForFamilyMembersDocuments();
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// popout Coding Form and complete Docs
		docViewAnalytics.popOutCodingFormAndCompleteDocument();
		driver.scrollPageToTop();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
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

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		loginPage.logout();
	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Family Member child window and Save/Complete clicked from coding
	 *              form parent window'RPMXCON-51376'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyCodeAsSameFamilyMemberChildWindowCodingFormParentWindow() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.familyDocument;
		String revDocsToBeSelected = Input.familyDocumentForReviewer;
		softAssertion = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51376");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Family Member child window and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get family members documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with family meber documents");
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentsPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		docViewAnalytics.selectAssignmentfromDashborad(assname);

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
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Save Icon
		docViewAnalytics.getSaveIcon().Click();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same From Conceptual tab
		docViewAnalytics.performCodeSameForFamilyMembersDocuments(parentWindowID);
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

		try {
			if (docViewAnalytics.getCodeSameIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeSameIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeSameIconMiniDocList().Displayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		driver.scrollPageToTop();

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewAnalytics.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Save Icon
		docViewAnalytics.getSaveIcon().Click();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same From Conceptual tab
		docViewAnalytics.performCodeSameForFamilyMembersDocuments(parentWindowID);
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

		try {
			if (docViewAnalytics.getCodeSameIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeSameIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeSameIconMiniDocList().Displayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		driver.scrollPageToTop();

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Edit Coding form and docs are completed successfully");

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
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

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Near Dupe and Save/Complete clicked from coding form parent
	 *              window'RPMXCON-51378'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyCodeAsSameWithCodingFormParentWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51378");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Near Dupe and Save/Complete clicked from coding form parent window");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeDocId1;
		String revDocsToBeSelected = Input.nearDupeDocId;

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string and added to shopping cart successfuly");

		// bulk Assign and create assignment
		assignmentPage.assignNearDupeDocstoNewAssgn(assname, Input.codingFormName, SessionSearch.pureHit);
		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForNearDupeDocuments(1);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Coding form is edited and Docs are completed successfully");

		// Select docs Form MiniDocsList
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
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

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Perform Code Same for Conceptual Docs
		docViewAnalytics.performCodeSameForNearDupeDocuments(1);
		baseClass.stepInfo("CodeAsSame is performed for the selected docs successfully");

		// verify Code As Same Icon Displayed
		docViewAnalytics.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();
		baseClass.stepInfo("CodeAsSame Icon Is Displayed successfully");

		// Edit Coding form and Complete the docs
		docViewAnalytics.editCodingFormComplete();
		baseClass.stepInfo("Coding form is edited and Docs are completed successfully");

		// Select Docid from MiniDocLis
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		// Verify CheckMark is present
		docViewAnalytics.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();
		baseClass.stepInfo("Complete CheckMark is verified Successfully");

		softAssertion.assertAll();
		baseClass.passedStep(
				"To Verify when Code same action selected from Analytics Panel > Near Dupe and Save/Complete clicked from coding form parent window has been succesfully verified");

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify when Code same action selected from Analytics Panel >
	 *              Family Member child window and Save/Complete clicked from coding
	 *              form child window 'RPMXCON-51377'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyCodeAsSameFamilyMemberSimilarChildWindow() throws InterruptedException {

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.familyDocument;
		String revDocsToBeSelected = Input.familyDocumentForReviewer;

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51377");
		baseClass.stepInfo(
				"Verify when Code same action selected from Analytics Panel > Family Member child window and Save/Complete clicked from coding form child window");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo("Searching documents based on search string to get family members documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with family member documents");
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewPage.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		// Save Icon
		docViewPage.getSaveIcon().Click();

		// popout Analytics Panel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same From Conceptual tab
		baseClass.stepInfo("CodeAsSame is performed for the selected docs");
		docViewPage.performCodeSameForFamilyMembersDocuments(parentWindowID);

		// Verify Code As Same Icon
		baseClass.stepInfo("Verify whether the code same icon is displayed");
		docViewPage.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewPage.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewPage.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_MiniDocListExpand());
				docViewPage.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		baseClass.waitForElement(docViewPage.getCodeSameIconMiniDocList());
		softAssertion.assertEquals(docViewPage.getCodeSameIconMiniDocList().Displayed().booleanValue(), true,
				"Scenario 1");

		driver.scrollPageToTop();
		docViewPage.getSaveIcon().Click();

		// popout Coding Form and complete the docs
		docViewPage.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		try {
			if (docViewPage.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		// Expand Analytics Panel

		try {
			if (docViewPage.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewPage.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_AnalyticalPanelExpand());
				docViewPage.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify checkmark in other Tabs
		baseClass.stepInfo("Verify whether the complete checkmark is displayed");
		docViewPage.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();

		// logout RMU
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();
		parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout MiniDocList
		docViewPage.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select MiniDocList Docs
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSelected);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.scrollPageToTop();

		docViewPage.getSaveIcon().Click();

		// Popout AnalyticsPanel
		docViewPage.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select Code Same In Conceptullay Similar tab
		baseClass.stepInfo("CodeAsSame is performed for the selected docs");
		docViewPage.performCodeSameForFamilyMembersDocumentsForReviewer(parentWindowID);

		// Verify Code Same Icon is displayed
		baseClass.stepInfo("Verify whether the code same icon is displayed or not");
		docViewPage.verifyWhetherCodeSameIconIsDisplayedInOtherTabs();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		try {
			if (docViewPage.getDocView_MiniDocListExpand().isDisplayed()) {
				docViewPage.getDocView_MiniDocListExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_MiniDocListExpand());
				docViewPage.getDocView_MiniDocListExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("MiniDoclist Expand is performed successfully");
		}

		try {
			if (docViewPage.getCodeSameIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeSameIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeSameIconMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		driver.scrollPageToTop();
		docViewPage.getSaveIcon().Click();

		// popout Coding Form and complete Docs
		docViewPage.popOutCodingFormAndCompleteDocument();

		driver.scrollPageToTop();

		// Select docs from miniDoclist
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSelected);
		try {
			if (docViewPage.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewPage.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewPage.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		// Expand Analytics Panel
		try {
			if (docViewPage.getDocView_AnalyticalPanelExpand().isDisplayed()) {
				docViewPage.getDocView_AnalyticalPanelExpand().ScrollTo();
				baseClass.waitForElement(docViewPage.getDocView_AnalyticalPanelExpand());
				docViewPage.getDocView_AnalyticalPanelExpand().waitAndClick(10);
			}
		} catch (Exception e) {
			System.out.println("Analytics Panel Expand is performed successfully");
		}

		// Verify Completed Checkmark is displayed
		baseClass.stepInfo("Verify whether the complete checkmark is displayed or not");
		docViewPage.verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs();

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :Verify completed icon should be displayed for the completed
	 *              documents on thread map tab'RPMXCON-51362'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void verifyCodeCompleteThreadMapDocuments() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocs1;
		String revDocToBeSelected = Input.newNearDupeDocId;

		baseClass.stepInfo("Test case Id: RPMXCON-51362");
		baseClass.stepInfo("Verify completed icon should be displayed for the completed documents on thread map tab");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		baseClass
				.stepInfo("Searching threaded documents based on search string and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();

		baseClass
				.stepInfo("Doc is Assigned from Basic Search and Assignment '" + assname + "' is created Successfully");
		// bulk Assign and create assignment
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select assgn by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormCompleteForThreadDocs();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		baseClass.stepInfo("Verify completed check mark on thread map tab");
		docViewAnalytics.verifyCompleteCheckMarkForThreadMapTabDocs();

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select assgn by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);

		// Select Docid from MiniDocLis
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocToBeSelected);

		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormCompleteForThreadDocsForReviewer();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocToBeSelected);

		baseClass.stepInfo("Verify completed check mark on thread map tab");
		docViewAnalytics.verifyCompleteCheckMarkForThreadMapTabDocs();

	}

	/**
	 * 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify when document is completed same as last when 'Apply
	 *              Coding to this Near Dupe' is clicked from near dupe comparison
	 *              window opened from analytics panel child window'RPMXCON-51718'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	//@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyDocsCompletedSameAslastInAnalyticsPanelChildWindow() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51718");
		baseClass.stepInfo(
				"Verify when document is completed same as last when 'Apply Coding to this Near Dupe' is clicked from near dupe comparison window opened from analytics panel child window");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeDocId1;
		String revDocsToBeSelected = Input.nearDupeDocId;

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignDocstoNewAssgnEnableCodingStamp(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		docViewPage.selectCompleteButton();
		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewPage.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		
		// popout Analytics Panel
		docViewPage.popOutAnalyticsPanelForNearDupe();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();
		driver.switchTo().window(parentWindowID);

		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		
		baseClass.waitForElement(docViewPage.getDocView_MiniDocList_SecDoc());
		docViewPage.getDocView_MiniDocList_SecDoc().waitAndClick(5);
		
		docViewPage.popOutCodingFormChildWindowForNearDupe();
		// verify Completed checkmark
		docViewPage.verifyCheckMark();
		baseClass.stepInfo("Complete CheckMark is verified successfully");

		// logout RMU
		loginPage.logout();

		// Login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();

		docViewPage.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		docViewPage.selectCompleteButton();

		String parentWindowID1 = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docViewPage.popOutMiniDocList();
		Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID1.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// Select DocId In MiniDocList
		docViewPage.selectDocIdInMiniDocList(revDocsToBeSelected);
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID1);

		driver.waitForPageToBeReady();
		// popout Analytics Panel
		docViewPage.popOutAnalyticsPanelForNearDupe();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		String analyticsWindowID1 = driver.getWebDriver().getWindowHandle();
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindowForDocumentWhichHasMorePages(revDocsToBeSelected);
		// Click Apply Coding Button
		docViewPage.clickApplyCodingBtn();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID1);

		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.switchTo().window(analyticsWindowID1);
		// To Verify Chain link After Coding
		docViewPage.verifyChainLinkAfterCoding();
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID1);
		docViewPage.popOutCodingFormChildWindowForNearDupe();

		// verify Completed checkmark
		docViewPage.verifyCheckMark();
		baseClass.stepInfo("Complete CheckMark is verified successfully");

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :To verify that user has selected an Analytics Panel > Near Dupe
	 *              tab and completed document, the Analytics Panel Tab previously
	 *              selected must remain.b'RPMXCON-51419'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifySelectedTabStilRemainInAnalyticalPanelAfterCompletingNearDupeDoc() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected =Input.nearDupeDocId1;
		String revDocsToBeSelected = Input.nearDupeDocId;
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Test case Id: RPMXCON-51419");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Near Dupe tab and completed document, the Analytics Panel Tab previously selected must remain.");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		DocViewPage.codeSameDocumentid = documentToBeSelected;

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		baseClass.stepInfo("Verify completed check mark on near dupe tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconNearDupeTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconNearDupeTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on near dupe tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under Near Dupe tabss");
		}

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		System.err
				.println("############" + docViewAnalytics.getDocView_NumTextBox().GetAttribute("value") + "#########");
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		String getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("dupe")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("dupe"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}
		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		DocViewPage.codeSameDocumentid = revDocsToBeSelected;

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		baseClass.stepInfo("Verify completed check mark on near dupe tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconNearDupeTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconNearDupeTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on near dupe tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under Near Dupe tabss");
		}

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		System.err.println(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"));
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("dupe")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("dupe"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}
		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :To verify that user has selected an Analytics Panel >
	 *              Conceptual tab and completed document, the Analytics Panel Tab
	 *              previously selected must remain..b'RPMXCON-51420'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifySelectedTabStilRemainInAnalyticalPanelAfterCompletingCoceptualDoc() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.NewDocId;
		String revDocToBeSelected= Input.conceptualDocument;
		baseClass.stepInfo("Test case Id: RPMXCON-51420");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Conceptual tab and completed document, the Analytics Panel Tab previously selected must remain.");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		// Search the doc and bulk Assign the particular docs
		baseClass.stepInfo("Searching documents based on search string to get Conceptual documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with conceptual documents");
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		DocViewPage.codeSameDocumentid = documentToBeSelected;

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		baseClass.stepInfo("Verify completed check mark on conceptual tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconConceptTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconConceptTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on conceptual tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under conceptual tabss");
		}

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		System.err
				.println("############" + docViewAnalytics.getDocView_NumTextBox().GetAttribute("value") + "#########");
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		String getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("conceptual")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("conceptual"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}
		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocToBeSelected);
		DocViewPage.codeSameDocumentid = revDocToBeSelected;

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		baseClass.stepInfo("Verify completed check mark on conceptual tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconConceptTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconConceptTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on conceptual tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under conceptual tabss");
		}

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		System.err.println(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"));
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("conceptual")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("conceptual"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}
		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :To verify that user has selected an Analytics Panel > Near Dupe
	 *              tab from child window and completed document, the Analytics
	 *              Panel Tab previously selected must remain.b'RPMXCON-51423'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifySelectedTabStilRemainInAnalyticalPanelChildWindowAfterCompletingNearDupeDoc()
			throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.nearDupeDocId1;
		String revDocsToBeSelected = Input.nearDupeDocId;
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Test case Id: RPMXCON-51423");
		baseClass.stepInfo(
				"Verify that user has selected an Analytics Panel > Near Dupe tab from child window and completed document, the Analytics Panel Tab previously selected must remain");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		// Search the doc and bulk Assign the particular docs
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssignNearDupeDocuments();
		baseClass.stepInfo("Searching documents based on search string");

		// create Assignment and distribute the docs to the users
		assignmentsPage.assignNearDupeDocstoNewAssgn(assname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		DocViewPage.codeSameDocumentid = documentToBeSelected;

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		driver.switchTo().window(parentWindowID);

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify completed check mark on near dupe tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconNearDupeTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconNearDupeTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on near dupe tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under Near Dupe tabss");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		String getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("dupe")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("dupe"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}

		driver.close();
		driver.switchTo().window(parentWindowID);

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		DocViewPage.codeSameDocumentid = revDocsToBeSelected;

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		driver.switchTo().window(parentWindowID);

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify completed check mark on near dupe tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconNearDupeTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconNearDupeTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on near dupe tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under Near Dupe tabss");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("dupe")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("dupe"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}

		driver.close();
		driver.switchTo().window(parentWindowID);

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: NA
	 * @Description :To verify that user has selected an Analytics Panel >
	 *              Conceptual tab from child window and completed document, the
	 *              Analytics Panel Tab previously selected must
	 *              remain.'RPMXCON-51424'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifySelectedTabStilRemainInAnalyticalPanelChildWindowAfterCompletingCoceptualDoc()
			throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.NewDocId;
		String revDocsToBeSelected = Input.conceptualDocument;

		baseClass.stepInfo("Test case Id: RPMXCON-51424");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel > Conceptual tab from child window and completed document, the Analytics Panel Tab previously selected must remain.");

		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

		// Search the doc and bulk Assign the particular docs
		baseClass.stepInfo("Searching documents based on search string to get Conceptual documents ");
		sessionSearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Creating assignments with conceptual documents");
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);
		DocViewPage.codeSameDocumentid = documentToBeSelected;

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		driver.waitForPageToBeReady();

		driver.switchTo().window(parentWindowID);

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			driver.scrollingToElementofAPage(docViewAnalytics.getCodeCompleteIconMiniDocList());
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify completed check mark on conceptual tab");
		try {
			driver.scrollingToElementofAPage(docViewAnalytics.getCodeCompleteIconConceptTab());
			if (docViewAnalytics.getCodeCompleteIconConceptTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconConceptTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on conceptual tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under conceptual tabss");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		System.err
				.println("############" + docViewAnalytics.getDocView_NumTextBox().GetAttribute("value") + "#########");
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		String getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("conceptual")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("conceptual"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}

		driver.close();
		driver.switchTo().window(parentWindowID);

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		driver.waitForPageToBeReady();
		docViewAnalytics.selectDocIdInMiniDocList(revDocsToBeSelected);
		DocViewPage.codeSameDocumentid = revDocsToBeSelected;

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// popout Analytics Panel
		docViewAnalytics.popOutAnalyticsPanel();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		docViewAnalytics.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		driver.waitForPageToBeReady();

		driver.switchTo().window(parentWindowID);

		// Select Code Same In Thread Map tab
		// Edit Coding form and Complete the docs
		baseClass.stepInfo("Coding form is edited and Docs are completed");
		docViewAnalytics.editCodingFormComplete();

		baseClass.stepInfo("Verify completed check mark on mini doc list");
		try {
			if (docViewAnalytics.getCodeCompleteIconMiniDocList().isDisplayed()) {
				baseClass.waitForElement(docViewAnalytics.getCodeCompleteIconMiniDocList());
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconMiniDocList().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on mini doc list");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available in mini doclist");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify completed check mark on conceptual tab");
		try {
			if (docViewAnalytics.getCodeCompleteIconConceptTab().isDisplayed()) {
				softAssertion.assertEquals(docViewAnalytics.getCodeCompleteIconConceptTab().Displayed().booleanValue(),
						true);
				baseClass.passedStep("Code complete checkmark icon is displayed on conceptual tab");
			}
		} catch (Exception e) {
			baseClass.stepInfo("Selected Document is not available under conceptual tabss");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the next document from mini doc list is loaded or not");
		System.err.println(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"));
		if (docViewAnalytics.getDocView_NumTextBox().GetAttribute("value").equals("2")) {
			softAssertion.assertEquals(docViewAnalytics.getDocView_NumTextBox().GetAttribute("value"), "2");
			baseClass.passedStep("The next document from mini doc list is loaded successfully");
		} else {
			baseClass.failedStep("Next document is not loaded");
		}

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the tab selected on analytical panel tab still remain same");
		getIdValueFromActiveTab = docViewAnalytics.getActiveTabInAnalyticalPanel().GetAttribute("id");
		if (getIdValueFromActiveTab.contains("conceptual")) {
			softAssertion.assertEquals(getIdValueFromActiveTab.contains("conceptual"), true);
			baseClass.passedStep("The tab selected on analytical panel still remain same after completing document");
		} else {
			baseClass.failedStep("The tab selected on analytical panel changed after completing document");
		}

		driver.close();
		driver.switchTo().window(parentWindowID);

		softAssertion.assertAll();

	}

	/**
	 * 
	 * @Author: Steffy Created date: NA Modified date: NA Modified by: Mohan
	 * @Description :Verify that for email threaded documents should be displayed
	 *              though Inclusive Email is either Yes or NO'RPMXCON-51514'
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyInclusiveEmailThreadMapDocuments() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewAnalytics = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51514");
		baseClass.stepInfo(
				"Verify that for email threaded documents should be displayed though Inclusive Email is either Yes or NO");
		// Login RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		String documentToBeSelected = Input.conceptualDocs1;

		baseClass.stepInfo(
				"Searching documents based on search string to get threaded documents and added to shopping cart successfuly");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList successfully");
		docViewAnalytics.selectDocIdInMiniDocList(documentToBeSelected);

		baseClass.stepInfo(
				"Verify whether the threaded documents has documents which has Inclusive emails is either Yes or No");
		docViewAnalytics.verifyThreadDocsDisplayDocsIrrespectiveOfInclusiveEmailValue();
		
		

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
			//Indium-Stabilized
//			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close2() {
		try {
			//Indium-Stabilized
//			LoginPage.clearBrowserCache();
		} catch (Exception e) {
			// no such session
		}

	}
}
