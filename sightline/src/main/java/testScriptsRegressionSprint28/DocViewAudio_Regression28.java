package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression28 {

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
	 * @author Vijaya.Rani ModifyDate:14/12/2022 RPMXCON-51670
	 * @throws Exception
	 * @Description Update the Remarks for the existing audio documents.
	 */
	@Test(description = "RPMXCON-51670", enabled = true, groups = { "regression" })
	public void verifyAudioReviwerRemarkEditAndUpdate() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51670");
		baseClass.stepInfo("Update the Remarks for the existing audio documents.");

		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		String remarkText = Input.randomText + Utility.dynamicNameAppender();
		String editedRemark = Input.randomText + Utility.dynamicNameAppender();
		Map<String, String> updateDatas = new HashMap<String, String>();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.viewInDocView();

		docView.audioRemark(remarkText);
		baseClass.stepInfo("Reviewer Remarks field with date and comments should be shown.");
		docView.editAndVerifyData(remarkText, updateDatas, editedRemark);
		baseClass.passedStep("Remarks is updated successfully and a success message should be shown.");
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();

		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		SoftAssert asserts = new SoftAssert();
		asserts.assertTrue(docView.getRemarkText(editedRemark).isElementAvailable(5));
		baseClass.passedStep("The updated remarks are reflecting as it docviewpage successfully.");
		asserts.assertAll();

		loginPage.logout();

	}

	

	/**
	 * @author Vijaya.Rani ModifyDate:14/12/2022 RPMXCON-51473
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that selected speed is applied in the player from audio
	 *              doc view.
	 */
	@Test(description = "RPMXCON-51473", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentSpeedPlayerInDocView(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51473");
		baseClass.stepInfo("Verify that selected speed is applied in the player from audio doc view.");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		Actions action = new Actions(driver.getWebDriver());

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		String defaultSpeed = docViewPage.getAudioSilderValue().getText();
		System.out.println(defaultSpeed);
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.waitTime(10);
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		String defaultRunTime = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(defaultRunTime);
		driver.getWebDriver().navigate().refresh();
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_ConfigMinidoclist());
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		WebElement slider = docViewPage.getAudioSilderPoint().getWebElement();
		action.dragAndDropBy(slider, 200, 250).perform();
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		String modifySpeed = docViewPage.getAudioSilderValue().getText();
		System.out.println(modifySpeed);
		if (defaultSpeed != modifySpeed) {
			baseClass.passedStep("Speed control is clickable from audio doc view");
		} else {
			baseClass.failedStep("Speed control is not clickable from audio doc view");
		}

		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.waitTime(10);
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		String changedRunTime = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(changedRunTime);
		if (defaultRunTime != changedRunTime) {
			baseClass.stepInfo("Default speed slider runtime in 10secs is : " + defaultRunTime);
			baseClass.stepInfo("changed speed slider runtime in 10secs is : " + changedRunTime);
			baseClass.passedStep("Audio file is played with the selected speed from the control");
		} else {
			baseClass.failedStep("Audio file is not played with the selected speed from the control");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/12/2022 RPMXCON-51471
	 * @throws Exception
	 * @Description Verify that application should not hang when the user tries to
	 *              edit/delete a reviewer remark, when the audio is in playing
	 *              mode.
	 */
	@Test(description = "RPMXCON-51471", dataProvider = "Users_RMUREV", enabled = true, groups = { "regression" })
	public void verifyAppsNotHangAudioAddedReviwerRemark(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51471");
		baseClass.stepInfo(
				"Verify that application should not hang when the user tries to edit/delete a reviewer remark, when the audio is in playing mode.");

		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert asserts = new SoftAssert();
		String remarkText = Input.randomText + Utility.dynamicNameAppender();
		String editedRemark = Input.randomText + Utility.dynamicNameAppender();
		Map<String, String> updateDatas = new HashMap<String, String>();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.viewInDocView();

		docView.audioRemark(remarkText);
		baseClass.waitForElement(docView.audioPlayPauseIcon());
		docView.audioPlayPauseIcon().waitAndClick(10);
		baseClass.stepInfo("Audio document is played");
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		asserts.assertTrue(docView.getRemarkText(remarkText).isElementAvailable(5));
		baseClass.passedStep("Reviewer Remark panel is open and added remarks is displayed on the panel");
		asserts.assertAll();

		docView.getAudioPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		docView.editAndVerifyData(remarkText, updateDatas, editedRemark);
		docView.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep(
				"Remark is edited and its saved successfully and application is not hang after editing the remark when audio is in playing mode");
		docView.deleteExistingRemark();
		docView.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep(
				"Remark is deleted successfully. application is not hang after deleting the remark when audio is in playing mode");

		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:14/12/2022 RPMXCON-51097
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio files Speed slider bar functionality is
	 *              working properly inside Doc view screen.
	 */
	@Test(description = "RPMXCON-51097", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentSpeedSliderBarFunctionalityWorkingProperly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51097");
		baseClass.stepInfo(
				"Verify that audio files Speed slider bar functionality is working properly inside Doc view screen.");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		Actions action = new Actions(driver.getWebDriver());

		// Login As PA and speed 1x
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.pa1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_ConfigMinidoclist());
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		String defaultRunTime = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(defaultRunTime);
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_ConfigMinidoclist());
		String defaultSpeed = docViewPage.getAudioSilderValue().getText();
		System.out.println(defaultSpeed);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitTime(5);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		String changedRunTime = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(changedRunTime);
		if (defaultRunTime != changedRunTime) {
			baseClass.passedStep("Audio file is played with the selected speed 1x the control");
		} else {
			baseClass.failedStep("Audio file is not palyed selected speed");
		}
		loginPage.logout();

		// Login As RMU and speed 1.5x
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String defaultSpeed1 = docViewPage.getAudioSilderValue().getText();
		System.out.println(defaultSpeed1);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitTime(5);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		String defaultRunTime1 = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(defaultRunTime1);
		driver.getWebDriver().navigate().refresh();
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_ConfigMinidoclist());
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		WebElement slider = docViewPage.getAudioSilderPoint().getWebElement();
		action.dragAndDropBy(slider, 200, 50).perform();
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		String modifySpeed = docViewPage.getAudioSilderValue().getText();
		System.out.println(modifySpeed);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitTime(5);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		String changedRunTime1 = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(changedRunTime1);
		if (defaultRunTime1 != changedRunTime1) {
			baseClass.stepInfo("Default speed slider runtime in 5secs is : " + defaultRunTime1);
			baseClass.stepInfo("changed speed slider runtime in 5secs is : " + changedRunTime1);
			baseClass.passedStep("Audio file is played with the selected speed 1.5x the control");
		} else {
			baseClass.failedStep("Audio file is not played with the selected speed from the control");
		}
		loginPage.logout();

		// Login As REV and speed 2.5x
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rev1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitTime(5);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		String defaultRunTime2 = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(defaultRunTime2);
		driver.getWebDriver().navigate().refresh();
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_ConfigMinidoclist());
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		WebElement sliderREV = docViewPage.getAudioSilderPoint().getWebElement();
		action.dragAndDropBy(sliderREV, 200, 250).perform();
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		String modifySpeed2 = docViewPage.getAudioSilderValue().getText();
		System.out.println(modifySpeed2);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitTime(5);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		String changedRunTime2 = docViewPage.getDocviewAudio_StartTime().getText();
		System.out.println(changedRunTime2);
		if (defaultRunTime2 != changedRunTime2) {
			baseClass.stepInfo("Default speed slider runtime in 5secs is : " + defaultRunTime2);
			baseClass.stepInfo("changed speed slider runtime in 5secs is : " + changedRunTime2);
			baseClass.passedStep("Audio file is played with the selected speed 2.5x the control");
		} else {
			baseClass.failedStep("Audio file is not played with the selected speed from the control");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:15/12/2022 RPMXCON-51443
	 * @throws Exception 
	 * @Description Verify that > and < arrows should work when the hit in the
	 *              document is due to Keyword Group Highlights when redirected to
	 *              doc view from saved search.
	 */

	@Test(description = "RPMXCON-51443", enabled = true, groups = { "regression" })
	public void verifyArrowsKeywordGroupDocViewFromSavedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51443");
		baseClass.stepInfo(
				"Verify that > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from saved search.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docView =new DocViewPage(driver);
		KeywordPage keyWord = new KeywordPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String hitTerms = Input.randomText + Utility.dynamicNameAppender();
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		keyWord.navigateToKeywordPage();
		keyWord.AddKeyword(hitTerms, hitTerms);

		sessionSearch.basicContentSearch(Input.randomText);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);

		docViewRedact.checkingPersistentHitPanel();
		baseClass.stepInfo("persistent hit panel displayed in docview panel");
		docView.getPersistantHitEyeIcon().Click();
		docView.VerifyKeywordHit(hitTerms);
		
		//hit is displayed with < and >
		docViewRedact.TraverseForwardAndBackwardOnHits();
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
