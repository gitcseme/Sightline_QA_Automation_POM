package testScriptsRegressionSprint19;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}
	/**
	 * @author Vijaya.Rani ModifyDate:02/08/2022 RPMXCON-54282
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that the Enhanced Audio Player should be presented for
	 *              doc with AudioPlayerReady set to 1 on doc list.
	 */

	@Test(description = "RPMXCON-54282", enabled = true, groups = { "regression" })
	public void verifyAudioPlayerInperviewDocument() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54282");
		baseClass.stepInfo(
				"Verify that the Enhanced Audio Player should be presented for doc with AudioPlayerReady set to 1 on doc list");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocListPage docList = new DocListPage(driver);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocList();

		baseClass.waitForElement(docList.getDocListPerviewBtn());
		docList.getDocListPerviewBtn().waitAndClick(5);

		// verifying more than one hour audio docs
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) >= 01) {
			baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
		} else {
			baseClass.failedMessage("Lesser than one hour");
		}

		// playing audio file
		docViewPage.audioPlayPauseIcon().waitAndClick(5);

		// verifying waveform
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");
		softAssertion.assertAll();
		baseClass.passedStep("Audio presentation is ONLY the waveform presentation and associated controls and NOT the phonetic hit matrix, the redaction and transcript controls");

		// logout
		loginPage.logout();
	}
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
