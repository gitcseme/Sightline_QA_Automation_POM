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
		boolean flagTans = docViewPage.getTranscriptsTab().isDisplayed();
		softAssertion.assertTrue(flagTans);
		baseClass.passedStep("Transcript tab not displayed for audio docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify user after impersonation can see the persistent search on 
	 *              audio doc view in context of an assignment
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
	 * Description:Verify triangular arrow to highlight the position should be displayed at the
	 *              position where the search term occurs with the score
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
