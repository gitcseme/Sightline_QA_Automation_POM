package sightline.docviewAudio;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression27 {

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
	 * @author Vijaya.Rani ModifyDate:08/12/2022 RPMXCON-51691
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document navigation done from Images tab of previous document.
	 */

	@Test(description = "RPMXCON-51691", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentImagesTabInMiniDocList(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51691");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document navigation done from Images tab of previous document.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);
		if (baseClass.text("Images").isDisplayed()) {
			baseClass.passedStep("In image tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Last());
		docViewPage.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssertion.assertAll();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		if (baseClass.text("TEXT").isDisplayed()) {
			baseClass.passedStep("Text document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);
		if (baseClass.text("Images").isDisplayed()) {
			baseClass.passedStep("Images document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TranslationTab());
		docViewPage.getDocView_TranslationTab().waitAndClick(5);
		if (baseClass.text("Translations").isDisplayed()) {
			baseClass.passedStep("Translations document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:08/12/2022 RPMXCON-51701
	 * @throws InterruptedException
	 * @throws AWTException
	 * @DescriptionVerify that audio document should be loaded successfully when
	 *                    document navigation done from Translations tab of previous
	 *                    document
	 */

	@Test(description = "RPMXCON-51701", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentTranslationsTabInMiniDocList(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51701");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document navigation done from Translations tab of previous document.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TranslationTab());
		docViewPage.getDocView_TranslationTab().waitAndClick(5);
		if (baseClass.text("Translations").isDisplayed()) {
			baseClass.passedStep("In translations tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Last());
		docViewPage.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssertion.assertAll();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		if (baseClass.text("TEXT").isDisplayed()) {
			baseClass.passedStep("Text document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);
		if (baseClass.text("Images").isDisplayed()) {
			baseClass.passedStep("Images document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TranslationTab());
		docViewPage.getDocView_TranslationTab().waitAndClick(5);
		if (baseClass.text("Translations").isDisplayed()) {
			baseClass.passedStep("Translations document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.rani date: 8/12/22 RPMXCON-51112 Description:Verify user
	 * after impersonation should be able to download the audio file which is
	 * viewing in doc view.
	 */
	@Test(description = "RPMXCON-51112", enabled = true, groups = { "regression" })
	public void verifyUsersImpersonateDownloadAudioFile() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51112");
		baseClass.stepInfo(
				"Verify user after impersonation should be able to download the audio file which is viewing in doc view");

		SessionSearch session = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.sa1userName + "'");
		// impersonate SA to PA
		baseClass.impersonateSAtoPA();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		driver.waitForPageToBeReady();
		baseClass.waitUntilFileDownload();
		baseClass.waitTime(5);
		String CompareString = "mp3";
		String fileName = baseClass.GetFileName();
		System.out.println(fileName);
		baseClass.stepInfo(fileName);
		driver.waitForPageToBeReady();
		if (fileName.contains(CompareString)) {
			baseClass.passedStep("Audio file is downloaded with the original format");
		} else {
			baseClass.failedStep("docs is not displayed as expected");
		}
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.sa1userName + "'");
		// impersonate SA to RMU
		baseClass.impersonateSAtoRMU();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		driver.waitForPageToBeReady();
		baseClass.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		baseClass.stepInfo(fileName);
		if (fileName.contains(CompareString)) {
			baseClass.passedStep("Audio file is downloaded with the original format");
		} else {
			baseClass.failedStep("docs is not displayed as expected");
		}
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.sa1userName + "'");
		// impersonate SA to REV
		baseClass.impersonateSAtoReviewer();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		driver.waitForPageToBeReady();
		baseClass.waitUntilFileDownload();
		baseClass.stepInfo(fileName);
		driver.waitForPageToBeReady();
		if (fileName.contains(CompareString)) {
			baseClass.passedStep("Audio file is downloaded with the original format");
		} else {
			baseClass.failedStep("docs is not displayed as expected");
		}
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.pa1userName + "'");
		// impersonate PA to RMU
		baseClass.impersonatePAtoRMU();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		driver.waitForPageToBeReady();
		baseClass.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		baseClass.stepInfo(fileName);
		if (fileName.contains(CompareString)) {
			baseClass.passedStep("Audio file is downloaded with the original format");
		} else {
			baseClass.failedStep("docs is not displayed as expected");
		}
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.pa1userName + "'");
		// impersonate PA to REV
		baseClass.impersonatePAtoReviewer();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		baseClass.waitUntilFileDownload();
		baseClass.waitTime(5);
		baseClass.stepInfo(fileName);
		driver.waitForPageToBeReady();
		if (fileName.contains(CompareString)) {
			baseClass.passedStep("Audio file is downloaded with the original format");
		} else {
			baseClass.failedStep("docs is not displayed as expected");
		}
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");
		// impersonate RMU to REV
		baseClass.impersonateRMUtoReviewer();
		// Go to audioSearch
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		baseClass.waitUntilFileDownload();
		baseClass.stepInfo(fileName);
		driver.waitForPageToBeReady();
		if (fileName.contains(CompareString)) {
			baseClass.passedStep("Audio file is downloaded with the original format");
		} else {
			baseClass.failedStep("docs is not displayed as expected");
		}
		loginPage.logout();

	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:08/12/2022 RPMXCON-51697
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when document viewed from mini doc list from Images tab of previous document.
	 */

	@Test(description = "RPMXCON-51697", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentImagesTabInMiniDocListDifferentView(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51697");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document viewed from mini doc list from Images tab of previous document.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);
		if (baseClass.text("Images").isDisplayed()) {
			baseClass.passedStep("In image tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_Selectdoc(2));
		docViewPage.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssertion.assertAll();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		if (baseClass.text("TEXT").isDisplayed()) {
			baseClass.passedStep("Text document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);
		if (baseClass.text("Images").isDisplayed()) {
			baseClass.passedStep("Images document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TranslationTab());
		docViewPage.getDocView_TranslationTab().waitAndClick(5);
		if (baseClass.text("Translations").isDisplayed()) {
			baseClass.passedStep("Translations document is be loaded on the respective tab Succesfully");
		} else {
			baseClass.failedStep("There is no such message");
		}
		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:9/12/2022 RPMXCON-51093
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio files Stop functionality is working properly
	 *              inside Doc view screen.
	 */

	@Test(description = "RPMXCON-51093", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentPlayAndStopBtnWokingproperly(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51093");
		baseClass.stepInfo("Verify that audio files Stop functionality is working properly inside Doc view screen.");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitTime(10);
		String strTime = docViewPage.getDocView_RunningTime().getText();
		System.out.println(strTime);
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_IconStop());
		docViewPage.getDocView_IconStop().waitAndClick(5);
		String endTime = docViewPage.getDocView_RunningTime().getText();
		System.out.println(endTime);
		if (!strTime.equals(endTime)) {
			baseClass.passedStep("Audio files Stop functionality is work  properly inside Doc view screen");
		} else {
			baseClass.failedStep("Audio files Stop functionality is not work  properly inside Doc view screen");
		}

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/12/2022 RPMXCON-51472
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that the speed control should be properly clickable from
	 *              audio doc view and can select each of the speed settings.
	 */

	@Test(description = "RPMXCON-51472", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentSpeedPlayerClickableInDocView(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51472");
		baseClass.stepInfo(
				"Verify that the speed control should be properly clickable from audio doc view and can select each of the speed settings.");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		Actions action = new Actions(driver.getWebDriver());

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_ConfigMinidoclist());
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
		String defaultSpeed = docViewPage.getAudioSilderValue().getText();
		System.out.println(defaultSpeed);
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
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:9/12/2022 RPMXCON-51478
	 * @throws Exception
	 * @Description Verify that application should not hang when the user tries to
	 *              save the audio document when audio is playing.
	 */
	@Test(description = "RPMXCON-51478", dataProvider = "Users_RMUREV", enabled = true, groups = { "regression" })
	public void verifyApplicationNotHangAudioIsPlaying(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51478");
		baseClass.stepInfo(
				"Verify that application should not hang when the user tries to save the audio document when audio is playing.");

		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.viewInDocView();

		driver.waitForPageToBeReady();
		docView.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(Input.codingFormName);
		docView.editCodingFormSave();
		docView.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep("Audio document is saved and make the application is not hang");
		
		loginPage.logout();

	}


	/**
	 * @author Vijaya.Rani ModifyDate:09/12/2022 RPMXCON-51627
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Edge: Verify when user enters the document id in the "doc id
	 *              input box" and hits the enter key, then corresponding audio
	 *              document should load in the default view.
	 */

	@Test(description = "RPMXCON-51627", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentNoEnterInDocIdInputBox(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51627");
		baseClass.stepInfo(
				"Edge: Verify when user enters the document id in the \"doc id input box\" and hits the enter key, then corresponding audio document should load in the default view.");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String color =  docViewPage.getDocView_MiniDoc_SelectdocAsText(1,2).GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#3e65ac";
		if (ActualColor.equals(ExpectedColor)) {
			baseClass.passedStep("User redirect to the audio doc view and first audio document is loaded successfully");
		} else {
			baseClass.failedStep("User redirect to the audio doc view and first audio document is not loaded");
		}
		driver.waitForPageToBeReady();
		docViewPage.getDocView_NumTextBox().SendKeys("5");
		docViewPage.getDocView_NumTextBox().Enter();
		driver.waitForPageToBeReady();
		String color1 = docViewPage.getDocView_MiniDoc_SelectdocAsText(5,2).GetCssValue("background-color");
		System.out.println(color1);
		String ExpectedColor1 = Color.fromString(color1).asHex();
		System.out.println(ExpectedColor1);
		String ActualColor1 = "#3e65ac";
		baseClass.waitTime(5);
		String defaultView=docViewPage.getDefaultViewTab().GetAttribute("class");
		baseClass.compareTextViaContains(defaultView, "active", "audio document is loaded in the default view", "audio document is not loaded in the default view");
		if ( ActualColor1.equals(ExpectedColor1)) {
			baseClass.passedStep(
					"same document is selected from mini doc list");
		} else {
			baseClass.failedStep("same document is not selected from mini doc list");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/12/2022 RPMXCON-51628
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Edge: Verify when user enters the document id in the "doc id input box" and hits the enter key, then corresponding audio doc should load in the default view prior to that document navigation is done with <<, <, > and >>.
	 */

	@Test(description = "RPMXCON-51628", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocsIdInInputBoxLoadedDefaultView(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51628");
		baseClass.stepInfo(
				"Edge: Verify when user enters the document id in the \"doc id input box\" and hits the enter key, then corresponding audio doc should load in the default view prior to that document navigation is done with <<, <, > and >>");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String color =  docViewPage.getDocView_MiniDoc_SelectdocAsText(1,2).GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#3e65ac";
		if (ActualColor.equals(ExpectedColor)) {
			baseClass.passedStep("User redirect to the audio doc view and first audio document is loaded successfully");
		} else {
			baseClass.failedStep("User redirect to the audio doc view and first audio document is not loaded");
		}
		driver.waitForPageToBeReady();
		String docId=docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId);
		docViewPage.getDocView_Last().waitAndClick(5);
		String AfterClickdocId=docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(AfterClickdocId);
		if (!docId.equals(AfterClickdocId)) {
			baseClass.passedStep("Audio document is loaded as per the clicked document navigation option ");
		} else {
			baseClass.failedStep("Audio document is not loaded as per the clicked document navigation option ");
		}
		driver.waitForPageToBeReady();
		docViewPage.getDocView_NumTextBox().SendKeys("10");
		docViewPage.getDocView_NumTextBox().Enter();
		driver.waitForPageToBeReady();
		String color1 = docViewPage.getDocView_MiniDoc_SelectdocAsText(10,2).GetCssValue("background-color");
		System.out.println(color1);
		String ExpectedColor1 = Color.fromString(color1).asHex();
		System.out.println(ExpectedColor1);
		String ActualColor1 = "#3e65ac";
		baseClass.waitTime(5);
		String defaultView=docViewPage.getDefaultViewTab().GetAttribute("class");
		baseClass.compareTextViaContains(defaultView, "active", "audio document is loaded in the default view", "audio document is not loaded in the default view");
		if ( ActualColor1.equals(ExpectedColor1)) {
			baseClass.passedStep(
					"same document is selected from mini doc list");
		} else {
			baseClass.failedStep("same document is not selected from mini doc list");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :date: 12/12/22 Modified date: NA Modified by: NA
	 * Description:Verify user can see the applied folder to the audio files on doc
	 * view
	 */
	@Test(description = "RPMXCON-51076", enabled = true, groups = { "regression" })
	public void verifyUserSeeAppliedFolderAudioFilesOnDoc() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51076- DocView Audio");
		baseClass.stepInfo("Verify user can see the applied folder to the audio files on doc view");
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String Folder = "AAFolder" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolder = new TagsAndFoldersPage(driver);

		// Login As RMU user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");
		tagsAndFolder.CreateFolderInRMU(Folder);

		// search for audio docs
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.viewInDocView();

		// verify selected folder
		driver.waitForPageToBeReady();
		docViewPage.VerifySelectedFolderTab(Folder, 1);
		baseClass.passedStep(Folder
				+ " is Selected folder audio documents  applied folders is displayed on Folders tab as expected  ");
		loginPage.logout();
	}

	/**
	 * Author : date: 12/12/22 Modified date: NA Modified by: NA
	 * Description:To verify that remarks can be edited if document is marked as
	 * Completed in audio doc view. view
	 * @throws Throwable 
	 */
	@Test(description = "RPMXCON-51183", enabled = true, groups = { "regression" })
	public void verifyRemarksEditedDocMarkedCompletedInAudioDocView() throws InterruptedException, Throwable {
		baseClass.stepInfo("Test case Id: RPMXCON-51183- DocView Audio");
		baseClass
				.stepInfo("To verify that remarks can be edited if document is marked as Completed in audio doc view.");
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String remarkText1 = Input.randomText + Utility.dynamicNameAppender();
		String remarkText2 = Input.randomText + Utility.dynamicNameAppender();
		Map<String, String> updateDatas = new HashMap<String, String>();
		SoftAssert softassert = new SoftAssert();

		// Login As RMU user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create Assignment");
		assignment.assignDocstoNewAssgnEnableAnalyticalPanel(Asssignment, Input.codingFormName, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();
		// Select the Assignment from dashboard
		assignment.SelectAssignmentByReviewer(Asssignment);
        
		// reviewer remark edited 
		driver.waitForPageToBeReady();
		docViewPage.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(Input.codingFormName);
		docViewPage.editCodingFormComplete();
		docViewPage.getDocView_MiniDoc_Selectdoc(1).Click();
		driver.waitForPageToBeReady();
		softassert.assertTrue(docViewPage.getAdvancedSearchAudioRemarkIcon().isElementAvailable(8));
		baseClass.passedStep("Reviewer Remark Icon is Displayed successfully");
		docViewPage.audioRemark(remarkText1);
		driver.waitForPageToBeReady();
		docViewPage.editAndVerifyData(remarkText1, updateDatas, remarkText2);
		baseClass.passedStep("Audio document  Remark is editable and it is updated  ");
		softassert.assertAll();
		loginPage.logout();
	}
	
	/**
	 * Author : Sakthivel date: 12/09/22 Modified date: NA Modified by: NA
	 * Description:Verify audio doc view when transcript is not ingested for audio
	 * file
	 */
	@Test(description = "RPMXCON-51134", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyTranscriptIsNotIngestedAudioFile(String username, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51134- DocView Audio");
		baseClass.stepInfo("Verify audio doc view when transcript is not ingested for audio file");
		DocViewPage docViewPage = new DocViewPage(driver);

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// search for audio docs
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.viewInDocView();
		baseClass.stepInfo("User navigated Audio docs to docview page");
		driver.waitForPageToBeReady();

		// verify transcript tab is not display
		baseClass.waitTime(5);
		baseClass.waitForElement(docViewPage.getTranscriptsTab());
		baseClass.elementNotdisplayed(docViewPage.getTranscriptsTab(),
				"Transcript tab is not displayed for audio docs as expected");

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Sakthivel date: 12/09/22 Modified date: NA Modified by: NA
	 * Description:To verify that if Reviewer Remark is off at Assignment level then
	 * it should not displayed if user naivgates from Assignment-Audio Doc View.
	 */
	@Test(description = "RPMXCON-51179", enabled = true, groups = { "regression" })
	public void verifyReviewerRemarkOffAtAssignmentRemarkNotDisplayed() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51179- DocView Audio");
		baseClass.stepInfo(
				"To verify that if Reviewer Remark is off at Assignment level then it should not displayed if user naivgates from Assignment-Audio Doc View.");
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login As RMU user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		// search for audio docs
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.bulkAssign();
		assignment.assignmentCreationReviewerRemarkOff(Asssignment, Input.codingFormName);
		System.out.println(Asssignment);
		assignment.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.selectAssignmentToViewinDocView(Asssignment);
		driver.waitForPageToBeReady();
		baseClass.elementNotdisplayed(docViewPage.getAdvancedSearchAudioRemarkIcon(),
				"Remarks tab  is not displayed on audio doc view when it is off at an assignment level as expected  ");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rev1userName + "");
		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		driver.waitForPageToBeReady();
		baseClass.elementNotdisplayed(docViewPage.getAdvancedSearchAudioRemarkIcon(),
				"Remarks tab  is not displayed on audio doc view when it is off at an assignment level as expected  ");
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
