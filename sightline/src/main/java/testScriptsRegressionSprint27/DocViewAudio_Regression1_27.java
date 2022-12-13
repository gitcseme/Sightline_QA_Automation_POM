package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression1_27 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/12/2022 RPMXCON-51485
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the word from default tab of the audio doc view for
	 *              documents with AudioPlayerReady as 1.
	 */

	@Test(description = "RPMXCON-51485", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentDefaultTabWithAudioPlayerReady() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51485");
		baseClass.stepInfo(
				"Verify the word from default tab of the audio doc view for documents with AudioPlayerReady as 1.");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String audioReady = "AudioPlayerReady";

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.pa1userName + "");

		sg.addProjectFieldtoSG(audioReady);
		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep("Mp3 version in Default tab displayed for video and player docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");
		
		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep("Mp3 version in Default tab displayed for video and player docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rev1userName + "");
		
		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep("Mp3 version in Default tab displayed for video and player docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/12/2022 RPMXCON-51477
	 * @throws Exception
	 * @Description Verify that application should not hang when the user tries to
	 *              edit/delete a reviewer remark, after the user performs a Zoom
	 *              In/Zoom Out operation.
	 */
	@Test(description = "RPMXCON-51477", dataProvider = "Users_RMUREV", enabled = true, groups = { "regression" })
	public void verifyApplicationNotHangAudioReviwerRemark(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51477");
		baseClass.stepInfo(
				"Verify that application should not hang when the user tries to edit/delete a reviewer remark, after the user performs a Zoom In/Zoom Out operation.");

		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String remarkText = Input.randomText + Utility.dynamicNameAppender();
		String editedRemark = Input.randomText + Utility.dynamicNameAppender();
		Map<String, String> updateDatas = new HashMap<String, String>();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.viewInDocView();

		docView.audioRemark(remarkText);

		// checking zoom in function working for more than one hour audio docs
		driver.waitForPageToBeReady();
		docView.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docView.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		docView.getAudioPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		docView.editAndVerifyData(remarkText, updateDatas, editedRemark);
		docView.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep(
				"Remark is edited successfully and application is not hang when user tries to update reviewer remark, after the user performs a Zoom In/Zoom Out operation");
		docView.deleteExistingRemark();
		docView.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep(
				"Remark is deleted successfully and application is not hang when user tries to delete the remark, after the user performs a Zoom In/Zoom Out operation");

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.rani date: 12/12/22 RPMXCON-51342 Description:Verify after
	 * impersonation >| icon and |< in the DocView Audio Player controls should move
	 * to the next & previous phonetic hit position.
	 */
	@Test(description = "RPMXCON-51342", enabled = true, groups = { "regression" })
	public void verifyUsersImpersonateDocViewPlayerControls() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51342");
		baseClass.stepInfo(
				"Verify after impersonation >| icon and |< in the DocView Audio Player controls should move to the next & previous phonetic hit position");

		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		UserManagement user = new UserManagement(driver);
		String[] role = { "PA", "RMU", "REV" };
		String[] PArole = { "RMU", "REV" };

		// Login as SA impersonate PA,RMU,REV
		for (int i = 0; i < role.length; i++) {
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			baseClass.stepInfo("Successfully logged in as '" + Input.sa1userName + "'");
			user.userRoleImpersonate("SA", role[i]);
			// Go to audioSearch
			session.audioSearch(Input.audioSearchString1, Input.language);
			session.viewInDocView();

			driver.waitForPageToBeReady();
			String start = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			System.out.println(start);
			docView.getDocView_IconNext().waitAndClick(5);
			baseClass.waitTime(1);
			docView.getDocView_IconNext().waitAndClick(5);
			String end = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			System.out.println(end);
			softAssertion.assertNotEquals(start, end);
			baseClass.passedStep("Cursor is move to the next phonetic hit position");
			baseClass.stepInfo("clicked on Back hit");
			docView.getDocView_IconBack().waitAndClick(10);
			String privousHit = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			softAssertion.assertNotEquals(end, privousHit);
			baseClass.passedStep("Cursor is move to the previous phonetic hit position");
			loginPage.logout();
		}

		// Login as PA impersonate RMU,REV
		for (int i = 0; i < PArole.length; i++) {
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Successfully logged in as '" + Input.pa1userName + "'");
			user.userRoleImpersonate("PA", PArole[i]);
			// Go to audioSearch
			session.audioSearch(Input.audioSearchString1, Input.language);
			session.viewInDocView();

			driver.waitForPageToBeReady();
			String start = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			System.out.println(start);
			docView.getDocView_IconNext().waitAndClick(5);
			baseClass.waitTime(1);
			docView.getDocView_IconNext().waitAndClick(5);
			String end = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			System.out.println(end);
			softAssertion.assertNotEquals(start, end);
			baseClass.passedStep("Cursor is move to the next phonetic hit position");
			baseClass.stepInfo("clicked on Back hit");
			docView.getDocView_IconBack().waitAndClick(10);
			String privousHit = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			softAssertion.assertNotEquals(end, privousHit);
			baseClass.passedStep("Cursor is move to the previous phonetic hit position");
			loginPage.logout();
		}

		// Login as RMU impersonate REV
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");
		baseClass.impersonateRMUtoReviewer();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();

		driver.waitForPageToBeReady();
		String start = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
		System.out.println(start);
		docView.getDocView_IconNext().waitAndClick(5);
		baseClass.waitTime(1);
		docView.getDocView_IconNext().waitAndClick(5);
		String end = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
		System.out.println(end);
		softAssertion.assertNotEquals(start, end);
		baseClass.passedStep("Cursor is move to the next phonetic hit position");
		baseClass.stepInfo("clicked on Back hit");
		docView.getDocView_IconBack().waitAndClick(10);
		String privousHit = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
		softAssertion.assertNotEquals(end, privousHit);
		baseClass.passedStep("Cursor is move to the previous phonetic hit position");
		loginPage.logout();

	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "Users_RMUREV")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocList_Regression26.**");
	}

}
