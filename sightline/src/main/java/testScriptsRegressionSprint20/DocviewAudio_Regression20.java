package testScriptsRegressionSprint20;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import akka.japi.Util;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class DocviewAudio_Regression20 {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	KeywordPage keywordPage;
	DocListPage docListPage;
	SavedSearch savedSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AnnotationLayer annatationLayer;
	RedactionPage redact;
	MiniDocListPage miniDocListpage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws ParseException, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();

	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify that audio files increase volume functionality is working
	 * properly inside Doc view screen
	 * 
	 */

	@Test(description = "RPMXCON-51099", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void validatePauseFunctionInVideoDocs(String userName, String password, String fullName)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51099");
		baseClass.stepInfo("Verify that audio files increase volume functionality is working "
				+ "properly inside Doc view screen");

		String volumeActual = "width: 100%;";
		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// playing the audio
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.stepInfo("Audio file is played successfully");

		// increase the volume
		baseClass.waitForElement(docViewPage.getVolumeMaxButton());
		docViewPage.getVolumeMaxButton().waitAndClick(5);
		baseClass.stepInfo("Audio file max volume button is clicked");
		baseClass.waitForElement(docViewPage.getVolumeLevel());
		String volumeExpected = docViewPage.getVolumeLevel().GetAttribute("style");
		softAssertion.assertEquals(volumeActual, volumeExpected);
		baseClass.passedStep("user can increase the volume button inside docview screen for audio documents");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	@DataProvider(name = "Impersonate")
	public Object[][] impersonate() {
		Object[][] users = { { "sa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password, "pa" },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rev1userName, Input.rev1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rev1userName, Input.rev1password, "rev" },
				{ "rmu", Input.rmu1userName, Input.rmu1password, Input.rev1userName, Input.rev1password, "rev" } };
		return users;
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify audio doc view after impersonation when transcript is not
	 * ingested for audio file
	 * 
	 */

	@Test(description = "RPMXCON-51135", dataProvider = "Impersonate", enabled = true, groups = { "regression" })
	public void validateTranscriptTab(String role, String userName, String password, String impuserName,
			String imppassword, String impersonate) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51135");
		baseClass.stepInfo("Verify audio doc view after impersonation when transcript is not ingested for audio file");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		// impersonate @user2
		baseClass.credentialsToImpersonateAsPARMUREV(role, impersonate);

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("User navigated to audio docview page");

		// verify transcript tab
		boolean flagTans = docViewPage.getTranscriptsTab().isElementAvailable(15);
		softAssertion.assertTrue(flagTans);
		baseClass.passedStep("Transcript tab not displayed for audio docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify user after impersonation can see the persistent search on
	 * audio doc view in context of an assignment
	 * 
	 */

	@Test(description = "RPMXCON-51138", enabled = true, groups = { "regression" })
	public void verifyTriangularArrowOnAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51138");
		baseClass.stepInfo("Verify user after impersonation can see the persistent search on "
				+ "audio doc view in context of an assignment");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		String assgn = "assign" + Utility.dynamicNameAppender();

		// Login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as '" + Input.rmu1userName + "'");

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgn, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();

		// Login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgn);

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);

		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);
		baseClass.stepInfo("clicked on hit forward");

		baseClass.elementDisplayCheck(docViewPage.getTriangularIcon());
		baseClass.stepInfo("Audio triangular arrow is displayed");
		String start = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		docViewPage.audioPersistentBackwardNavigate().waitAndClick(10);
		baseClass.stepInfo("clicked on hit Back");
		String end = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		softAssertion.assertNotEquals(start, end);
		baseClass.stepInfo("Audio triangular arrow position verified");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify triangular arrow to highlight the position should be
	 * displayed at the position where the search term occurs with the score
	 * 
	 */

	@Test(description = "RPMXCON-51136", enabled = true, groups = { "regression" })
	public void verifyTriangularArrow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51136");
		baseClass.stepInfo("Verify triangular arrow to highlight the position should be displayed at the "
				+ "position where the search term occurs with the score");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		String assgn = "assign" + Utility.dynamicNameAppender();

		// Login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as '" + Input.rmu1userName + "'");

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgn, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignmentPage.assignmentPagination(assgn);
		baseClass.waitForElement(assignmentPage.getAssignmentAction_ViewinDocView());
		assignmentPage.getAssignmentAction_ViewinDocView().waitAndClick(10);
		baseClass.stepInfo("Assignment selected and viewAllDocs in docview");

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);

		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("clicked on hit forward");

		baseClass.elementDisplayCheck(docViewPage.getTriangularIcon());
		baseClass.stepInfo("Audio triangular arrow is displayed");
		String start = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		docViewPage.audioPersistentBackwardNavigate().waitAndClick(10);
		baseClass.stepInfo("clicked on hit Back");
		String end = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		softAssertion.assertNotEquals(start, end);
		baseClass.stepInfo("Audio triangular arrow position verified");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author:Baskar date: 26/08/2022 Modified date: NA Modified by:
	 * @Description IE11: Verify when user enters the document id in the "doc id
	 *              input box" and hits the enter key, then corresponding audio doc
	 *              should load in the default view prior to that document
	 *              navigation is done from mini doc list child window
	 */
	@Test(description = "RPMXCON-51632", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void verifyDocumentLoadedFromMiniDocList(String username, String password) throws Exception {

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51632");
		baseClass.stepInfo(
				"IE11: Verify when user enters the document id in the \"doc id input box\" and hits the enter key, then "
						+ "corresponding audio doc should load in the default view prior to that document navigation is done from mini doc list child window");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		// view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();

		// verify doc id from child window
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docViewPage.selectRowFromMiniDocList(5);
		driver.waitForPageToBeReady();
		String minidocid = docViewPage.getMiniDocListData(5, 2).getText().trim();
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		String currentdocid = docViewPage.getDocViewSelectedDocId().getText().trim();
		softAssertion.assertEquals(currentdocid, minidocid);
		baseClass.passedStep(
				"Clicked audio document was load in the default view and same document is selected from mini doc list child window");

		// verify from docid default view prior
		docViewPage.getDocView_NumTextBox().SendKeys("3" + Keys.ENTER);
		driver.waitForPageToBeReady();
		currentdocid = docViewPage.getDocViewSelectedDocId().getText().trim();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String idOnMiniDoc = docViewPage.getMiniDocListData(3, 2).getText().trim();
		softAssertion.assertEquals(currentdocid, idOnMiniDoc);
		baseClass.passedStep(
				"Corresponding audio document was load in the default view and same document is selected from mini doc list child window");
		driver.close();
		docViewPage.switchToNewWindow(1);

		softAssertion.assertAll();
		baseClass.passedStep(
				"Verified when user enters the document id in the \"doc id input box\" and hits the enter key, then corresponding audio doc "
						+ "should load in the default view prior to that document navigation is done from mini doc list child window");
		loginPage.logout();

	}

	/**
	 * Author :Baskar date: 26/08/2022 Modified date: NA Modified by:
	 * 
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with different term and different/same threshold are
	 *              assigned to assignment at different time from session search
	 * @throws InterruptedException
	 * 
	 */
	@Test(description = "RPMXCON-51799", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdInSessionSearchBulkAssign() throws InterruptedException {

		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case id :RPMXCON-51799");
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with different term and "
				+ "different/same threshold are assigned to assignment at different time from session search");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as RMU");

		String searchString1 = Input.audioSearchString3;
		String searchString2 = Input.audioSearchString4;
		String assignmentName = "assgnAudio" + Utility.dynamicNameAppender();
		String assignmentName2 = "assgnAudio" + Utility.dynamicNameAppender();
		// First audio Search
		sessionSearch.audioSearch(searchString1, Input.language);
		sessionSearch.getPureHitAddButton().waitAndClick(10);

		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName);
		driver.waitForPageToBeReady();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString1);

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// second audio search
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearch(searchString2, Input.language);
		sessionSearch.addPureHit();

		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName2);
		assignmentPage.quickAssignmentCreation(assignmentName2, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName2, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName2);
		driver.waitForPageToBeReady();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString2.toLowerCase());

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// First audio search term with max threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString1, Input.language, "max");
		sessionSearch.addPureHit();

		// second audio search with same term and min threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString2, Input.language, "min");
		sessionSearch.addPureHit();

		String assignmentName1 = "assgnAudio" + Utility.dynamicNameAppender();
		// view All audio Docs in DocList
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName1);
		assignmentPage.quickAssignmentCreation(assignmentName1, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName1, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName1);

		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigating to Docview Page");

		baseClass.stepInfo("Verifying audio hits  displayed when documents searched with same term and different"
				+ "threshold navigated to doc view from session search > Assignments Page>Docview");

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString2.toLowerCase());
		loginPage.logout();

	}

	/**
	 * @author Krishna ModifyDate: RPMXCON-51824
	 * @throws Exception
	 * @Description Verify that when audio file is playing and clicked to apply the
	 *              stamp, then waveform should be loaded [Less than 1 hr audio
	 *              file] from coding form child window
	 */
	@Test(description = "RPMXCON-51824", enabled = true, groups = { "regression" })
	public void verifyAudioPlayerClickedApplyStampFromChildWindow() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51824");
		baseClass.stepInfo(
				"Verify that when audio file is playing and clicked to apply the stamp, then waveform should be loaded [Less than 1 hr audio file] from coding form child window");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		String assign = "Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);

		driver.waitForPageToBeReady();
		docviewPage.clickGearIconOpenMiniDocList();
		docviewPage.closeWindow(1);
		docviewPage.switchToNewWindow(1);
		driver.Navigate().refresh();
		baseClass.handleAlert();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.audioPlayPauseIcon());
		docviewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.passedStep("Audio file is played successfully");
		docviewPage.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Coding form and mini doc list and codingform child window is opened");

		docviewPage.switchToNewWindow(2);
		baseClass.waitTime(4);
		docviewPage.editCodingForm();
		baseClass.waitForElement(docviewPage.getCodingFormStampButton());
		docviewPage.getCodingFormStampButton().waitAndClick(10);
		docviewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		docviewPage.popUpAction(comment, Input.stampSelection);
		docviewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.getCodingStampLastIcon(Input.stampSelection));
		docviewPage.getCodingStampLastIcon(Input.stampSelection).waitAndClick(10);
		docviewPage.getCodingFormSaveThisForm().waitAndClick(5);
		baseClass.stepInfo("Coding form saved with stamp and document saved successfully");
		docviewPage.closeWindow(1);
		docviewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		if (docviewPage.getDocView_AudioPlay().isElementPresent()) {
			baseClass.passedStep("Document in play mode");

		} else {
			baseClass.failedStep("failed to play");
		}
		driver.Navigate().refresh();
		docviewPage.deleteStampColour(Input.stampSelection);

	}

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
		}
		try {

//			 loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
