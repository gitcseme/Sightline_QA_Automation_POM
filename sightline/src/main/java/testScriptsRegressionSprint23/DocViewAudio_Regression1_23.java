package testScriptsRegressionSprint23;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
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

public class DocViewAudio_Regression1_23 {

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

//		Input in = new Input();
//		in.loadEnvConfig();
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
	 * @author Iyappan ModifyDate: RPMXCON-51343
	 * @throws Exception
	 * @Description Verify >| icon and |< in the DocView Audio Player controls
	 *              should move to the next & previous phonetic hit position when
	 *              mini doc list child window is open
	 */
	@Test(description = "RPMXCON-51343", alwaysRun = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyIconInDocViewAudioPlayerMoveNextPhoneticHitIsOpenChildWindow(String username, String password,
			String fullname) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51343");
		baseClass.stepInfo(
				"Verify >| icon and |< in the DocView Audio Player controls should move to the next & previous phonetic hit position when mini doc list child window is open");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullname);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		docviewPage.clickGearIconOpenMiniDocList();
		baseClass.passedStep(" Clicked gear icon mini doc list child window is opened");
		docviewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docviewPage.getClickDocviewID(2);
		baseClass.stepInfo("Clicked audio doc file is loaded");
		docviewPage.switchToNewWindow(1);
		baseClass.waitTime(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String nexticon = docviewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docviewPage.getDocView_IconNext());
		docviewPage.getDocView_IconNext().waitAndClick(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String nexticon1 = docviewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (nexticon.equals(nexticon1)) {
			baseClass.failedStep("Cursor is not moved");
			
		} else {
			baseClass.passedStep("Cursor is moved to the next phonetic hit position successfully");
		}
		docviewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String previousicon = docviewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docviewPage.getDocView_IconPrev());
		docviewPage.getDocView_IconPrev().waitAndClick(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String previousicon1 = docviewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (previousicon.equals(previousicon1)) {
			baseClass.failedStep("Cursor is not moved");
		} else {
			baseClass.passedStep("Cursor is moved to the previous phonetic hit position successfully");

		}
	
	}
	/**
	 * @author Iyappan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *         RPMXCON-51487
	 * @throws Exception
	 * @Description Verify 3 play counter readouts are displayed on audio doc view
	 */
	@Test(description = "RPMXCON-51487", alwaysRun = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyPlaycounterReadoutsDisplayedOnDocView(String username, String password, String fullname)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51487");
		baseClass.stepInfo("Verify 3 play counter readouts are displayed on audio doc view");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullname);
		sessionSearch.basicContentSearch(Input.oneHourAudio);

		// Navigate to DocView Page audio
		baseClass.stepInfo("Navigate to DocView Page audio");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();

		// Get verify Audio counter readouts duration
		String Audiostarttime = docviewPage.getDocview_Audio_StartTime().getText();
		if (docviewPage.getDocview_Audio_StartTime().isDisplayed()) {
			System.out.println(Audiostarttime);
			baseClass.passedStep(
					Audiostarttime + "..Starttime play counter readouts is  successfully displayed on docview audio");
		} else {
			baseClass.failedStep("Start play counter readouts is not displayed");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.audioPlayPauseIcon());
		docviewPage.audioPlayPauseIcon().waitAndClick(10);
		docviewPage.audioPlayPauseIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String runningTime = docviewPage.getDocView_RunningTime().getText();
		if (docviewPage.getDocView_RunningTime().isDisplayed()) {
			System.out.println(runningTime);
			baseClass.passedStep(runningTime
					+ ".. In Runningtime play counter readouts is  successfully displayed on docview audio");
		} else {
			baseClass.failedStep("Runningtime play counter readouts is not displayed");

		}
		String Audioendtime = docviewPage.getDocview_Audio_EndTime().getText();
		if (docviewPage.getDocview_Audio_EndTime().isDisplayed()) {
			baseClass.passedStep(
					Audioendtime + "..Endtime play counter readouts is  successfully displayed on docview audio");

		} else {
			baseClass.failedStep("Endtime play counter readouts is not displayed");
		}

	}
	
	

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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
