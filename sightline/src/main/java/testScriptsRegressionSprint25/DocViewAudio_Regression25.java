package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression25 {

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
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-51251
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify user can zoom in/zoom out the audio waveform by moving
	 *              the slider.
	 */

	@Test(description = "RPMXCON-51251", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioWaveformSliderZoomInZoomOut(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51251");
		baseClass.stepInfo("Verify user can zoom in/zoom out the audio waveform by moving the slider.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocViews();

		// checking zoom in function working for more than one hour audio docs
		driver.waitForPageToBeReady();
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// verifying waveform after zoom
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.waitTime(1);
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		boolean waveforms = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");

		// validating audio is still playing after zoom
		driver.waitForPageToBeReady();
		boolean audioPlays = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");

		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-51684
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document viewed from mini doc list from Text tab of previous
	 *              document.
	 */

	@Test(description = "RPMXCON-51684", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInTextTabPreviousDocsInMiniDocList(String username,
			String password, String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51684");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document viewed from mini doc list from Text tab of previous document.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		if (baseClass.text("TEXT").isDisplayed()) {
			baseClass.passedStep("In text tab it displayed");
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
			baseClass.passedStep("In text tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-51685
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document viewed from mini doc list from Images tab of previous
	 *              document.
	 */

	@Test(description = "RPMXCON-51685", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInImageTabInMiniDocList(String username, String password,
			String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51685");
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
			baseClass.passedStep("In text tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:07/11/2022 RPMXCON-51700
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document navigation done from Images tab of previous document.
	 */

	@Test(description = "RPMXCON-51700", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentImagesTabInMiniDocList(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51700");
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
	 * @author Vijaya.Rani ModifyDate:07/11/2022 RPMXCON-51696
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document viewed from mini doc list from Text tab of previous
	 *              document.
	 */

	@Test(description = "RPMXCON-51696", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInTextTabInMiniDocList(String username, String password,
			String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51696");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document viewed from mini doc list from Text tab of previous document.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (baseClass.text("TEXT").isDisplayed()) {
			baseClass.passedStep("In text tab it displayed");
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
		// logout
		loginPage.logout();
	}
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-51777
	 * @throws Exception
	 * @Description Verify when audio document present in two different session
	 *              searches with common term and assigned to new assignment, then
	 *              it should not display repetitive search term on persistent hits
	 *              panel
	 */
	@Test(description = "RPMXCON-51777", enabled = true, groups = { "regression" })
	public void verifyAudioDocsDifferentSearchsNotDisplayPersistentHit() throws Exception {

		DocViewPage docview = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);

		baseClass.stepInfo("Test case Id: RPMXCON-51777 Docview/Audio");
		baseClass.stepInfo(
				"Verify when audio document present in two different session searches with common term and assigned to new assignment, then it should not display repetitive search term on persistent hits panel");
		// Audio search
		int purehit = sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docview.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docview.getMiniDocListDocIdText());
		System.out.println(DocIDInMiniDocList);

		// Audio search
		baseClass.selectproject();
		sessionSearch.verifyaudioSearchWarning(audioSearch, Input.language);
		sessionSearch.addPureHit();
		// Added another Audio search and add
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.bulkAssignWithOutPureHit();

		// Select Assignment goto docview
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		baseClass.passedStep("Assignment created succcessfully as expected");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(Asssignment);

		// Check Display persistant hit - notrepetative
		docview.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 5));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docview.getAudioPersistantHitEyeIcon());
		docview.getAudioPersistantHitEyeIcon().Click();
		docview.verifyAudioPersistanHitNotDisplayed(Input.audioSearchString2);
		int searchterm = docview.getSearchTermList(Input.audioSearchString3).size();		
		if (searchterm <= 1) {
			baseClass.passedStep("repetitive search term is not displayed");
		}else {
			baseClass.failedStep("repetitive search term is displayed");
		}
		
		// logout
		loginPage.logout();
	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-51778
	 * @throws Exception
	 * @Description Verify when audio document present in two different session
	 *              searches with common term, then it should not display repetitive
	 *              search term on persistent hits panel
	 */
	@Test(description = "RPMXCON-51778", enabled = true, groups = { "regression" })
	public void verifyAudioDocsPresentInDifferentSearchsNotDisplay() throws Exception {

		DocViewPage docview = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);

		baseClass.stepInfo("Test case Id: RPMXCON-51778 Docview/Audio");
		baseClass.stepInfo(
				"Verify when audio document present in two different session searches with common term, then it should not display repetitive search term on persistent hits panel");
		// Audio search
		int purehit = sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docview.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docview.getMiniDocListDocIdText());
		System.out.println(DocIDInMiniDocList);

		// Audio search
		baseClass.selectproject();
		sessionSearch.verifyaudioSearchWarning(audioSearch, Input.language);
		sessionSearch.addPureHit();

		// Added another Audio search and add
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.viewInDocView();

		// Check Display persistant hit - notrepetative
		docview.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 5));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docview.getAudioPersistantHitEyeIcon());
		docview.getAudioPersistantHitEyeIcon().Click();
		docview.verifyAudioPersistanHitNotDisplayed(Input.audioSearchString2);
		int searchterm = docview.getSearchTermList(Input.audioSearchString3).size();		
		if (searchterm <= 1) {
			baseClass.passedStep("repetitive search term is not displayed");
		}else {
			baseClass.failedStep("repetitive search term is displayed");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-51341
	 * @throws Exception
	 * @Description Verify >| icon and |< in the DocView Audio Player controls
	 *              should move to the next & previous phonetic hit position when
	 *              file is playing
	 */
	@Test(description = "RPMXCON-51341", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioPlayerControlsPhoneticHitIsPlayMode(String userName, String password, String fullName)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51341");
		baseClass.stepInfo(
				"Verify >| icon and |< in the DocView Audio Player controls should move to the next & previous phonetic hit position when file is playing");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("LoggedIn as : " + fullName);

		// Audio search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.waitForElement(docviewPage.getDocView_RunningTime());
		String nexticon = docviewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docviewPage.getDocView_IconNext());
		docviewPage.getDocView_IconNext().waitAndClick(5);
		baseClass.waitForElement(docviewPage.getDocView_RunningTime());
		String nexticon1 = docviewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (nexticon.equals(nexticon1)) {
			baseClass.failedStep("Cursor is not moved");

		} else {
			baseClass.passedStep("Cursor is moved to the next phonetic hit position successfully");
		}
		baseClass.waitTime(5);
		softAssertion.assertTrue(docviewPage.getDocView_AudioPlay().isElementPresent());
		baseClass.passedStep("Documentfile has been  in play mode as expected");

		baseClass.waitForElement(docviewPage.getDocView_RunningTime());
		String previousicon = docviewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docviewPage.getDocView_IconPrev());
		docviewPage.getDocView_IconPrev().waitAndClick(5);
		baseClass.waitForElement(docviewPage.getDocView_RunningTime());
		String previousicon1 = docviewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (previousicon.equals(previousicon1)) {
			baseClass.failedStep("Cursor is not moved");
		} else {
			baseClass.passedStep("Cursor is moved to the previous phonetic hit position successfully");

		}
		softAssertion.assertTrue(docviewPage.getDocView_AudioPlay().isElementPresent());
		baseClass.passedStep("Documentfile has been  in play mode as expected");
		softAssertion.assertAll();

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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	} 
	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}
