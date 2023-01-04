package sightline.docviewAudio;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Phase2_Regression1 {

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
	 * @author Vijaya.Rani ModifyDate:23/08/2022 RPMXCON-51798
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel from saved search > Doc List> Doc View.
	 */
	@Test(description = "RPMXCON-51798", enabled = true, groups = { "regression" })
	public void verifyAudioDocsNotDisplayPersistentHitDocListToDocViewPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51798");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel from saved search > Doc List> Doc View.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		DocListPage docListPage = new DocListPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to doclist page
		sessionSearch.ViewInDocListWithOutPureHit();
		baseClass.stepInfo("Navigate to the Doclist page Successfully");
		// Select all docs and view as docview
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docListPage.getSelectAll().Visible() && docListPage.getSelectAll().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docListPage.getSelectAll());
		docListPage.getSelectAll().waitAndClick(10);
		docListPage.getPopUpOkBtn().Click();
		docListPage.docListToDocView();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		baseClass.passedStep(
				"eye icon audio hits is displayed and the repetitive search term on persistent hits panel from session search as term is not displayed");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:23/08/2022 RPMXCON-51795
	 * @throws Exception
	 * @Description Verify that when document present in different session searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel from session search.
	 */
	@Test(description = "RPMXCON-51795", enabled = true, groups = { "regression" })
	public void verifyAudioDocsNotDisplayPersistentHitInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51795");
		baseClass.stepInfo(
				"Verify that when document present in different session searches with common term then, should not display repetitive search term on persistent hits panel from session search.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to doclist page
		sessionSearch.ViewInDocViewWithoutPureHit();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		baseClass.passedStep(
				"eye icon audio hits is displayed and the repetitive search term on persistent hits panel from session search as term is not displayed,search term threshold and language is same for the searches");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:23/08/2022 RPMXCON-51672
	 * @throws Exception
	 * @Description Add multiple remarks to the Audio documents.
	 */
	@Test(description = "RPMXCON-51672", enabled = true, groups = { "regression" })
	public void addMultipleRemarksInAudioDocument() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51672");
		baseClass.stepInfo("Add multiple remarks to the Audio documents.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		Map<String, String> datas = new HashMap<String, String>();
		String remark = "Remark" + Utility.dynamicNameAppender();
		int iteration = 1;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		datas = docviewPage.addRemarkToDocumentsT(iteration, remark, true, "Success");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		// Verify Existing remarks + Edit File
		driver.waitForPageToBeReady();
		docviewPage.verifyExistingRemarks(iteration, datas, true, true);

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:23/08/2022 RPMXCON-51556
	 * @throws Exception
	 * @Description Verify downloading and zoom action in Doc view for International
	 *              English Language indexed audio files.
	 */
	@Test(description = "RPMXCON-51556", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentDownloadandZoomAction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51556");
		baseClass.stepInfo(
				"Verify downloading and zoom action in Doc view for International English Language indexed audio files.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocViews();

		docviewPage.audioDownload();
		baseClass.passedStep("The user able to download audio files successfully.");

		// checking zoom in function working for more than one hour audio docs
		docviewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docviewPage.getAudioZoomBar().Displayed();
		softAssert.assertTrue(zoomBar);
		baseClass.passedStep("The zoom action is work as expected in Doc view.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/08/2022 RPMXCON-51698
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document viewed from mini doc list from Translations tab of
	 *              previous document.
	 */

	@Test(description = "RPMXCON-51698", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentTranslationsTabInMiniDocList(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51698");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document viewed from mini doc list from Translations tab of previous document.");

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
			baseClass.passedStep("In text tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_Selectdoc(2));
		docViewPage.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
	 * @author Vijaya.Rani ModifyDate:25/08/2022 RPMXCON-51783
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with common terms and different/same threshold are
	 *              saved.
	 */
	@Test(description = "RPMXCON-51783", enabled = true, groups = { "regression" })
	public void verifyAudioDocsHitsDisplayedDifferentThershold() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51783");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common terms and different/same threshold are saved.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to docview page
		sessionSearch.ViewInDocViewWithoutPureHit();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docviewPage.trianglurArrowIconPositionVerification();
		baseClass.passedStep("eye icon audio hits should be displayed and triangular arrow icon should be displayed.");

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// First audio search term with max threshold value
		baseClass.stepInfo("Doing first search tems with maximum thersold");
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.language, "max");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// second audio search with same term and min threshold value
		baseClass.stepInfo("Doing first search tems with minimum thersold");
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString3, Input.language, "min");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);
		// go to docview page
		sessionSearch.ViewInDocViewWithoutPureHit();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docviewPage.trianglurArrowIconPositionVerification();
		baseClass.passedStep("eye icon audio hits should be displayed and triangular arrow icon should be displayed.");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/08/2022 RPMXCON-51109
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify user can stop the audio play when play started from
	 *              redactions.
	 */

	@Test(description = "RPMXCON-51109", enabled = true, groups = { "regression" })
	public void verifyAudioPlayStartedFromRedaction() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51109");
		baseClass.stepInfo("Verify user can stop the audio play when play started from redactions.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  REV as with " + Input.rev1userName + "");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Audio redaction tab display
		driver.waitForPageToBeReady();
		docViewPage.getDocview_RedactionsTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(5);
		baseClass.passedStep(
				"Redactions tab is displayed on audio doc view, all redactions in audio file is listed in tabular format");
		docViewPage.playAudio();
		baseClass.passedStep(
				"Audio file starting from redaction start time and Play of the audio file stop successfully");
		// logout
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/08/2022 RPMXCON-51108
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify user can play the redaction.
	 */

	@Test(description = "RPMXCON-51108", enabled = true, groups = { "regression" })
	public void verifyuserCanPlayRedaction() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51108");
		baseClass.stepInfo("Verify user can play the redaction.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  REV as with " + Input.rev1userName + "");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Audio redaction tab display
		driver.waitForPageToBeReady();
		docViewPage.getDocview_RedactionsTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(5);
		baseClass.passedStep(
				"Redactions tab is displayed on audio doc view, all redactions in audio file is listed in tabular format");
		docViewPage.playAudio();
		baseClass.passedStep(
				"Audio file play starting from redaction start time and end at the redaction end time successfully");
		// logout
		loginPage.logout();

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
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
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
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
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
		action.moveToElement(docViewPage.getAudioSilderPoint().getWebElement()).build().perform();
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
		DocViewPage docView = new DocViewPage(driver);
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
		driver.waitForPageToBeReady();
		savedSearch.savedSearchToDocView(saveName);

		driver.waitForPageToBeReady();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.stepInfo("persistent hit panel displayed in docview panel");
		driver.waitForPageToBeReady();
		docView.getPersistantHitEyeIcon().Click();
		docView.VerifyKeywordHit(hitTerms);

		// hit is displayed with < and >
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		docViewRedact.TraverseForwardAndBackwardOnHits();
		loginPage.logout();

	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Description:Verify that 3
	 * GMT readouts are displayed if the TrimmedAudioDuration is null/blank on
	 * preview document from doc list
	 * 
	 */
	@Test(description = "RPMXCON-54286", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verify3GmtReadoutsDisplayedTrimmAudioDurationNullInDocList(String username, String password,
			String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54286- DocView Audio");
		baseClass.stepInfo(
				"Verify that 3 GMT readouts are displayed if the TrimmedAudioDuration is null/blank on preview document from doc list");
		DocViewPage docViewPage = new DocViewPage(driver);
		DocListPage doclist = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String audioReady = "AudioPlayerReady";
		String audioTrimmed = "AudioTrimmedDuration";
		String[] columnsToSelect = { "AudioTrimmedDuration" };

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		if (role.equalsIgnoreCase(Input.pa1FullName)) {
			sg.addProjectFieldtoSG(audioReady);
			sg.addProjectFieldtoSG(audioTrimmed);
		}
		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocList();
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		doclist.SelectColumnDisplayByRemovingExistingOnes(columnsToSelect);
		driver.waitForPageToBeReady();
		String audioTrimmedduration = doclist.getDataInDoclist(1, 4).GetAttribute("td");
		System.out.println(audioTrimmedduration);
		softAssert.assertEquals(audioTrimmedduration, null);
		softAssert.assertAll();
		baseClass.waitForElement(doclist.getDocListPerviewBtn());
		doclist.getDocListPerviewBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		String Audiostarttime = docViewPage.getDocview_Audio_StartTime().getText();
		softAssert.assertTrue(docViewPage.getDocview_Audio_StartTime().isDisplayed());
		if (Audiostarttime.contains("00")) {
			baseClass.passedStep(Audiostarttime + " Start time is displayed on audio on the left side as expected");

		} else {
			baseClass.failedStep("Start time is not displayed on audio ");

		}
		String Audioendtime = docViewPage.getDocview_Audio_EndTime().getText();
		softAssert.assertTrue(docViewPage.getDocview_Audio_StartTime().isDisplayed());
		if (Audiostarttime.equals(Audioendtime)) {
			baseClass.failedStep("End time is not displayed on audio on the left side");

		} else {
			baseClass.passedStep(Audioendtime + "End time is displayed on audio on the left side as expected");

		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		softAssert.assertTrue(docViewPage.getDocview_Audio_StartTime().isDisplayed());
		baseClass.waitTime(5);
		String Audiocurrenttime = docViewPage.getDocviewAudio_StartTime().getText();
		if (Audiostarttime.equals(Audiocurrenttime)) {
			baseClass.failedStep("Start Time GMT that is not counting up from Start");

		} else {
			baseClass.passedStep(Audiocurrenttime + " Start Time GMT that is counting up from Start as expected");

		}
		loginPage.logout();
	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Description:Verify that 3
	 * GMT readouts are displayed if the TrimmedAudioDuration is null/blank on audio
	 * doc view
	 * 
	 */
	@Test(description = "RPMXCON-51488", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verify3GmtReadoutsDisplayedTrimmAudioDurationNull(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51488- DocView Audio");
		baseClass.stepInfo(
				"Verify that 3 GMT readouts are displayed if the TrimmedAudioDuration is null/blank on audio doc view");
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String audioReady = "AudioPlayerReady";
		String audioTrimmedDuration = "AudioTrimmedDuration";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		if (role.equalsIgnoreCase(Input.pa1FullName)) {
			sg.addProjectFieldtoSG(audioReady);
			sg.addProjectFieldtoSG(audioTrimmedDuration);
		}
		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocView();
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		docViewPage.selectSourceDocIdInAvailableField("AudioTrimmedDuration");
		driver.waitForPageToBeReady();
		String audioTrimmed = docViewPage.getDocView_MiniDoc_SelectdocAsText(1, 4).GetAttribute("td");
		System.out.println(audioTrimmed);
		softAssert.assertEquals(audioTrimmed, null);
		softAssert.assertAll();
		driver.waitForPageToBeReady();
		String Audiostarttime = docViewPage.getDocview_Audio_StartTime().getText();
		softAssert.assertTrue(docViewPage.getDocview_Audio_StartTime().isDisplayed());
		if (Audiostarttime.contains("00")) {
			baseClass.passedStep(Audiostarttime + " Start time is displayed on audio on the left side as expected");

		} else {
			baseClass.failedStep("Start time is not displayed on audio ");

		}
		String Audioendtime = docViewPage.getDocview_Audio_EndTime().getText();
		softAssert.assertTrue(docViewPage.getDocview_Audio_StartTime().isDisplayed());
		if (Audiostarttime.equals(Audioendtime)) {
			baseClass.failedStep("End time is not displayed on audio on the left side");

		} else {
			baseClass.passedStep(Audioendtime + "End time is displayed on audio on the left side as expected");

		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		softAssert.assertTrue(docViewPage.getDocview_Audio_StartTime().isDisplayed());
		baseClass.waitTime(5);
		String Audiocurrenttime = docViewPage.getDocviewAudio_StartTime().getText();
		if (Audiostarttime.equals(Audiocurrenttime)) {
			baseClass.failedStep("Start Time GMT that is not counting up from Start");

		} else {
			baseClass.passedStep(Audiocurrenttime + " Start Time GMT that is counting up from Start as expected");

		}
		loginPage.logout();
	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Description:Verify the
	 * Redaction for Audio files for International English language pack.
	 * 
	 * @throws Throwable
	 */
	@Test(description = "RPMXCON-51555", enabled = true, groups = { "regression" })
	public void verifyRedactionAudioFileInternationalEngLanguage() throws InterruptedException, Throwable {

		baseClass.stepInfo("Test case Id: RPMXCON-51555- DocView Audio");
		baseClass.stepInfo("Verify the Redaction for Audio files for International English language pack.");
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String remarkText1 = Input.randomText + Utility.dynamicNameAppender();
		String doccomment = "comment" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();

		// Login As RMU user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		baseClass.stepInfo("User select and  searched  in " + Input.audioLanguage);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Add redaction in audio doc");
		docViewPage.audioRedactionUsingAudioRangeWithEditRedactionTag(1, 1, 2);
		baseClass.passedStep("After add redaction has been able to edit as expected");

		baseClass.stepInfo("Add Remarks in audio doc");
		baseClass.waitTime(5);
		docViewPage.audioRemark(remarkText1);
		baseClass.stepInfo("Add comment in audio doc");
		driver.Navigate().refresh();
		baseClass.waitTime(5);
		docViewPage.editCodingForm(doccomment);
		baseClass.waitForElement(docViewPage.getCodingFormSaveBtn());
		docViewPage.getCodingFormSaveBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		System.out.println(doccomment);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		baseClass.CloseSuccessMsgpopup();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		String comment = docViewPage.getAudioComment().getText();
		System.out.println(comment);
		softassert.assertEquals(doccomment, comment);
		baseClass.passedStep("User has been able to add remarks and comments in audio documents as expected");
		softassert.assertAll();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/12/2022 RPMXCON-51791
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with same term and different/same threshold are
	 *              assigned to assignment from session search > Doc List.
	 */

	@Test(description = "RPMXCON-51791", enabled = true, groups = { "regression" })
	public void verifyAudioHitIsDisplayedWithAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51791");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with same term and different/same threshold are assigned to assignment from session search > Doc List.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		DocListPage docListPage = new DocListPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String assign = "Assignment" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		// audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		docListPage.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreationWithPersistantHitDocList(assign, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assignmentPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();

		// Login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rev1userName + "'");

		// Select the Assignment from dashboard
		assignment.SelectAssignmentByReviewer(assign);

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docView.getAudioPersistantHitEyeIcon());
		docView.getAudioPersistantHitEyeIcon().Click();
		docView.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		sessionSearch.newAudioSearchThreshold(Input.audioSearchString1, Input.language, "max");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// second audio search with same term and min threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString1, Input.language, "min");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// view All audio Docs in DocList
		sessionSearch.ViewInDocList();

		docListPage.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Assignment Creation");
		assignmentPage.assignmentCreationWithPersistantHitDocList(assign, Input.codingFormName);

		baseClass.stepInfo("Toggle Coding Stamp Enabled");
		assignmentPage.toggleCodingStampEnabled();

		baseClass.stepInfo("Assignment Distributing To Reviewer");
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();

		// Login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rev1userName + "'");

		// Select the Assignment from dashboard
		assignment.SelectAssignmentByReviewer(assign);

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docView.getAudioPersistantHitEyeIcon());
		docView.getAudioPersistantHitEyeIcon().Click();
		docView.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:29/08/2022 RPMXCON-51111
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify user should be able to download the audio file which is
	 *              viewing in doc view outside of an assignment.
	 */

	@Test(description = "RPMXCON-51111", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyDownloadAudioFileInDocView(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51111");
		baseClass.stepInfo(
				"Verify user should be able to download the audio file which is viewing in doc view outside of an assignment.");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocViews();
		docViewPage.audioDownload();
		baseClass.passedStep("The user able to download audio files successfully.");

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File audioFile = new File(home + "/Downloads/.mp3");
		baseClass.passedStep("Audio file is downloaded with the original format.");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:29/08/2022 RPMXCON-51810
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel when selecting document from history
	 *              drop down.
	 */
	@Test(description = "RPMXCON-51810", enabled = true, groups = { "regression" })
	public void verifyAudioDocsRepetitiveSearchTermInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51810");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel when selecting document from history drop down.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		DocListPage docListPage = new DocListPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to doclist page
		sessionSearch.ViewInDocListWithOutPureHit();
		baseClass.stepInfo("Navigate to the Doclist page Successfully");
		// Select all docs and view as docview
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docListPage.getSelectAll().Visible() && docListPage.getSelectAll().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docListPage.getSelectAll());
		docListPage.getSelectAll().waitAndClick(10);
		docListPage.getPopUpOkBtn().Click();
		docListPage.docListToDocView();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		baseClass.passedStep(
				"eye icon audio hits is displayed and repetitive search term is not Display on persistent hits panel from saved search as term,threshold and language is same for the searches");
		baseClass.stepInfo("Audio Document Select from MiniDocList History DropDown");
		docviewPage.audiodocthruhistorydropdown();
		baseClass.passedStep(
				"Document selected from the history drop down is loaded and repetitive search term on persistent hits panel is not display");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:30/08/2022 RPMXCON-51811
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel when mini doc list is configured.
	 */
	@Test(description = "RPMXCON-51811", enabled = true, groups = { "regression" })
	public void verifyAudioDocsConfigureMiniDoclistRepetitiveSearchTermInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51811");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel when mini doc list is configured.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		DocListPage docListPage = new DocListPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to doclist page
		sessionSearch.ViewInDocListWithOutPureHit();
		baseClass.stepInfo("Navigate to the Doclist page Successfully");
		// Select all docs and view as docview
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docListPage.getSelectAll().Visible() && docListPage.getSelectAll().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docListPage.getSelectAll());
		docListPage.getSelectAll().waitAndClick(10);
		docListPage.getPopUpOkBtn().Click();
		docListPage.docListToDocView();
		baseClass.stepInfo("Navigate to the DocView page Successfully");

		// Check Display persistanthit
		docviewPage.audioPersistantHitDisplayCheck();
		baseClass.passedStep(
				"eye icon audio hits is displayed and repetitive search term is not Display on persistent hits panel from saved search as term,threshold and language is same for the searches");
		baseClass.stepInfo(" Select fields from Configure MiniDocList ");
		docviewPage.configureMiniDocList();
		baseClass.passedStep(
				"Mini doc list is configured as per the selected fields and repetitive search term on persistent hits panel is not display");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:30/08/2022 RPMXCON-51813
	 * @throws Exception
	 * @Description Verify that when navigates audio documents then "Assignment
	 *              Audit Log" should not appear in console log continuously.
	 * 
	 */
	@Test(description = "RPMXCON-51813", enabled = true, groups = { "regression" })
	public void verifyAudioDocsAssignmentAuditLogNotAppear() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51813");
		baseClass.stepInfo(
				"Verify that when navigates audio documents then \"Assignment Audit Log\" should not appear in console log continuously.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		DocListPage docListPage = new DocListPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search And Save
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// go to doclist page
		sessionSearch.ViewInDocListWithOutPureHit();
		baseClass.stepInfo("Navigate to the Doclist page Successfully");
		// Select all docs and view as docview
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docListPage.getSelectAll().Visible() && docListPage.getSelectAll().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docListPage.getSelectAll());
		docListPage.getSelectAll().waitAndClick(10);
		docListPage.getPopUpOkBtn().Click();
		docListPage.docListToDocView();
		baseClass.stepInfo("Navigate to the DocView page Successfully");
		// Click >> Btn
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.getDocView_Last());
		docviewPage.getDocView_Last().waitAndClick(5);
		docviewPage.audiodocthruhistorydropdown();
		driver.waitForPageToBeReady();
		docviewPage.editCodingFormSave();
		driver.waitForPageToBeReady();
		docviewPage.perfromLastCodeSameAsIcon();
		baseClass.stepInfo("Last Code Same Icon Clicked successfully");
		baseClass.passedStep(
				"navigate audio documents then \"Assignment Audit Log\" is not appear in console log continuously, the SP is not call frequently");
		// logout
		loginPage.logout();
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
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
	 * @author Vijaya.Rani ModifyDate:09/08/2022 RPMXCON-52021
	 * @throws Exception
	 * @Description Verify the automatically selected audio redaction tag when
	 *              shared annotation layer with shared redactation tags in security
	 *              groups and all documents are released to security groups.
	 */
	@Test(description = "RPMXCON-52021", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionTagReleasedToSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52021");
		baseClass.stepInfo(
				"Verify the automatically selected audio redaction tag when shared annotation layer with shared redactation tags in security groups and all documents are released to security groups.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(securityGroup);
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep("Audio redaction is saved with the selected redaction tag");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep("Application is automatically select the ‘Default Redaction Tag’");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:18/08/2022 RPMXCON-51699
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document navigation done from Text tab of previous document.
	 */

	@Test(description = "RPMXCON-51699", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInTextTab(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51699");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document navigation done from Text tab of previous document.");

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
		baseClass.waitForElement(docViewPage.getDocView_Last());
		docViewPage.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:18/08/2022 RPMXCON-51692
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document navigation done from Translations tab of previous
	 *              document.
	 */

	@Test(description = "RPMXCON-51692", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInTranslationsTab(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51692");
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
			baseClass.passedStep("In text tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Last());
		docViewPage.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
	 * @author Vijaya.Rani ModifyDate:18/08/2022 RPMXCON-51690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document navigation done from Text tab of previous document.
	 */

	@Test(description = "RPMXCON-51690", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInTextTabPreviousDocs(String username, String password,
			String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51690");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document navigation done from Text tab of previous document.");

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
		baseClass.waitForElement(docViewPage.getDocView_Last());
		docViewPage.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
	 * @author Vijaya.Rani ModifyDate:18/08/2022 RPMXCON-51686
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio document should be loaded successfully when
	 *              document viewed from mini doc list from Translations tab of
	 *              previous document.
	 */

	@Test(description = "RPMXCON-51686", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentLoadingSuccessfullyInTranslationsTabInMiniDocList(String username, String password,
			String role) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51686");
		baseClass.stepInfo(
				"Verify that audio document should be loaded successfully when document viewed from mini doc list from Translations tab of previous document.");

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
			baseClass.passedStep("In text tab it displayed");
		} else {
			baseClass.failedStep("There is no such message");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_Selectdoc(2));
		docViewPage.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep("Mp3 version in Default tab displayed for video and player docs");
		softAssert.assertAll();
		// logout
		loginPage.logout();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep("Mp3 version in Default tab displayed for video and player docs");
		softAssert.assertAll();
		// logout
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rev1userName + "");

		sessionSearch.basicMetaDataSearch("AudioPlayerReady", null, "1", "");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep("Mp3 version in Default tab displayed for video and player docs");
		softAssert.assertAll();
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
		softAssert.assertTrue(zoomBar);
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
			softAssert.assertNotEquals(start, end);
			baseClass.passedStep("Cursor is move to the next phonetic hit position");
			baseClass.stepInfo("clicked on Back hit");
			docView.getDocView_IconBack().waitAndClick(10);
			String privousHit = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			softAssert.assertNotEquals(end, privousHit);
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
			softAssert.assertNotEquals(start, end);
			baseClass.passedStep("Cursor is move to the next phonetic hit position");
			baseClass.stepInfo("clicked on Back hit");
			docView.getDocView_IconBack().waitAndClick(10);
			String privousHit = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
			softAssert.assertNotEquals(end, privousHit);
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
		softAssert.assertNotEquals(start, end);
		baseClass.passedStep("Cursor is move to the next phonetic hit position");
		baseClass.stepInfo("clicked on Back hit");
		docView.getDocView_IconBack().waitAndClick(10);
		String privousHit = docView.getAudioPlayerCurrentState().GetAttribute("style").trim();
		softAssert.assertNotEquals(end, privousHit);
		baseClass.passedStep("Cursor is move to the previous phonetic hit position");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-52014
	 * @throws Exception
	 * @Description Verify that when applies audio redaction, the redaction popup
	 *              should automatically select the redaction tag that was last
	 *              applied across user session(s).
	 */
	@Test(description = "RPMXCON-52014", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionPopupAutomaticallySave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52014");
		baseClass.stepInfo(
				"Verify that when applies audio redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s).");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		baseClass.stepInfo(
				"The redaction popup is automatically select the redaction tag that was last applied across user session(s)");
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep("Redaction tag be saved for the audio redaction Successfully");

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:04/08/2022 RPMXCON-52015
	 * @throws Exception
	 * @Description Verify that user should be able to edit an applied audio
	 *              redaction and change the redaction tag that was applied
	 *              automatically.
	 */
	@Test(description = "RPMXCON-52015", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionPopupAutomaticallySaveAndEditTheRedaction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52015");
		baseClass.stepInfo(
				" Verify that user should be able to edit an applied audio redaction and change the redaction tag that was applied automatically.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		RedactionPage redactionPage = new RedactionPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String RedactName = "Redact" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Add Redaction tag
		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docviewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docviewPage.getDocview_AudioRedactions());
		docviewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// click on save button
		docviewPage.getSaveButton().waitAndClick(5);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Edited redaction tag is saved  Successfullt for the audio redaction");

		// Audio Redaction Tag deletion
		docviewPage.deleteAudioRedactionTag();
		redactionPage.DeleteRedaction(RedactName);
		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/08/2022 RPMXCON-52018
	 * @throws Exception
	 * @Description Verify that after deletion of the last saved audio redaction,
	 *              last saved redaction tag should be selected automatically from
	 *              redaction list.
	 */
	@Test(description = "RPMXCON-52018", enabled = true, groups = { "regression" })
	public void verifyAfterDeletionAudioRedactionSelectDefault() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52018");
		baseClass.stepInfo(
				"Verify that after deletion of the last saved audio redaction, last saved redaction tag should be selected automatically from redaction list.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		RedactionPage redactionPage = new RedactionPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String RedactName = "Redact" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Add Redaction tag
		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docviewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docviewPage.getDocview_AudioRedactions());
		docviewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// click on save button
		docviewPage.getSaveButton().waitAndClick(5);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Edited redaction tag is saved  Successfullt for the audio redaction");

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Audio Redaction Tag deletion
		redactionPage.DeleteRedaction(RedactName);
		softAssertion.assertAll();

		baseClass.passedStep("Last saved redaction tag is selected automatically from redaction list");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/08/2022 RPMXCON-52017
	 * @throws Exception
	 * @Description Verify that when audio redaction tag is shared in different
	 *              security group then latest tag should be considered as per users
	 *              security group and session.
	 */
	@Test(description = "RPMXCON-52017", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionTagIsDifferentSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52017");
		baseClass.stepInfo(
				"Verify that when audio redaction tag is shared in different security group then latest tag should be considered as per users security group and session.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(securityGroup);
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.stepInfo(
				"Audio file as per selected start and end point redacted is only one redaction tag is allowed selected redaction.");
		baseClass.passedStep("Redaction tag is saved for the audio redaction");

		baseClass.waitForElement(docviewPage.getDocView_MiniDoc_SelectRow(2));
		docviewPage.getDocView_MiniDoc_SelectRow(2).waitAndClick(5);

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep(
				"Latest applied redaction tag is automatically selected from the list as per the logged in users session in SG1");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep(
				"Latest applied redaction tag is automatically selected from the list as per the logged in users session in SG2");
		loginPage.logout();
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/08/2022 RPMXCON-52016
	 * @throws Exception
	 * @Description Verify that after editing audio redaction and applies a
	 *              redaction tag to a audio redaction, then this applied redaction
	 *              tag should be considered as the latest redaction tag.
	 */
	@Test(description = "RPMXCON-52016", enabled = true, groups = { "regression" })
	public void verifyAfterEditingAudioRedactionConsiderLatestRedactionTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52016");
		baseClass.stepInfo(
				"Verify that after editing audio redaction and applies a redaction tag to a audio redaction, then this applied redaction tag should be considered as the latest redaction tag.");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Navigate to Advance Search Page and search for audio docs
		baseClass.stepInfo("Navigate to Advance Search Page and search for audio docs");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docviewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docviewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docviewPage.getDocview_AudioRedactions());
		docviewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();

		// click on save button
		docviewPage.getSaveButton().waitAndClick(5);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Edited redaction tag is saved  Successfullt for the audio redaction");
		baseClass.passedStep(
				"Redaction tag selected after editing a audio redaction is considered as the latest redaction tag and saved for the audio redaction");

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:29/06/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify that for Project Admin icon to see the audio search
	 *              hits should be displayed on audio doc view
	 */

	@Test(description = "RPMXCON-51137", enabled = true, groups = { "regression" })
	public void validatePAUserForEyeIcon() throws Exception {
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51137");
		baseClass.stepInfo("Verify that for Project Admin icon to see "
				+ "the audio search hits should be displayed on audio doc view");

		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User is logged in as PA");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearchString1);
		baseClass.passedStep("As a PA user eye icon with persistent hits are displaying in docview panel");

		// logout
		loginPage.logout();

	}

	/**
	 * @author Baskar date: 30/06/22 Modified date: NA Modified by: NA Description :
	 *         Verify that when adding redactions to the end of recording should not
	 *         display horizontal scroll bar under the jplayer if file is less than
	 *         1 hour
	 * 
	 */
	@Test(description = "RPMXCON-52316", enabled = true, groups = { "regression" })
	public void audioRedactionForLessThanHourToEnd() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		String headerName = "RedactionTags";
		int index;

		baseClass.stepInfo("Test case id :RPMXCON-52316");
		baseClass.stepInfo("Verify that when adding redactions to the end of recording should "
				+ "not display horizontal scroll bar under the jplayer if file is less than 1 hour");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate audio docs eye icon with persistent hits
		docViewPage.fullAudioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		boolean readctionPresent = docViewPage.getAudioBytes().isElementAvailable(1);
		softAssert.assertTrue(readctionPresent);
		baseClass.stepInfo("Reaction tag added to fully in document");
		boolean scrollBar = docViewPage.getAudioZoomBar().isElementAvailable(1);
		softAssert.assertTrue(scrollBar);
		baseClass.passedStep("Horizontal scroll bar not present in less than one hour documents");

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : â€˜Default Redaction Tagâ€™ is displayed", "After Save : invalid redaction tag selected");
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(5);
		docViewPage.audioRedactionUsingDocTime();

		// adding select redaction tags to validate till end
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);
		baseClass.VerifyErrorMessage("Redaction range can not overlap.");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Redaction tag added fully without unreadction");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 1/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that when adding redactions to the end of recording should not
	 *         leave some audio unredacted
	 * 
	 */
	@Test(description = "RPMXCON-52315", enabled = true, groups = { "regression" })
	public void audioRedactionToEnd() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		String headerName = "RedactionTags";
		int index;

		baseClass.stepInfo("Test case id :RPMXCON-52315");
		baseClass.stepInfo("Verify that when adding redactions to the end of recording "
				+ "should not leave some audio unredacted");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate audio docs eye icon with persistent hits
		docViewPage.fullAudioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : â€˜Default Redaction Tagâ€™ is displayed", "After Save : invalid redaction tag selected");
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(5);
		docViewPage.audioRedactionUsingDocTime();

		// adding select redaction tags to validate till end
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(Input.defaultRedactionTag);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);
		baseClass.VerifyErrorMessage("Redaction range can not overlap.");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Redaction tag added fully without unreadction");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 1/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that after deletion of the last saved redaction tag, 'Default
	 *         Redaction Tag' should be selected automatically from audio redaction
	 *         list
	 * 
	 */
	@Test(description = "RPMXCON-52019", enabled = true, groups = { "regression" })
	public void verifyRedactionAfterDeletion() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		RedactionPage redactionPage = new RedactionPage(driver);
		String RedactName = "Redact" + Utility.dynamicNameAppender();
		String RedactNameTwo = "Redact" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52019");
		baseClass.stepInfo("Verify that after deletion of the last saved redaction tag, 'Default Redaction Tag' "
				+ "should be selected automatically from audio redaction list");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);
		redactionPage.AddRedaction(RedactNameTwo, Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection
		String defautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"In default : Application automatically selected the ‘Default Redaction Tag’",
				"In default : invalid redaction tag selected");

		// first redaction
		docViewPage.audioRedactionUsingAudioRange(RedactName, 0, 1);
		baseClass.stepInfo("Adding the first redaction");

		// adding second redaction
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);
		docViewPage.audioRedactionBasesOnTime(2, 3);
		baseClass.stepInfo("Adding the second redaction");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactNameTwo);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record added Successfully");
		baseClass.CloseSuccessMsgpopup();

		// logout as rmu
		loginPage.logout();

		// login as pa user
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as : " + Input.pa1FullName);

		// Deleting Redaction
		redactionPage.DeleteRedaction(RedactNameTwo);
		baseClass.stepInfo("Second added Redaction tag deleted successfully");

		// logout as pa
		loginPage.logout();

		// Login as Rmu user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection after deletion
		String newdefautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(newdefautTagSelection, RedactName,
				"In default : Application automatically selected the deleted one before added",
				"In default : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		baseClass.waitTime(5);
		docViewPage.deleteAudioRedactionTag();
		baseClass.passedStep("Application automatically selecting the first redaction tag which "
				+ "added after deletion of second redaction");

		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that when ‘Default Redaction Tag’ doesn’t exist then on
	 *         applying audio redaction, the application must automatically select
	 *         the first in the list of redaction tags
	 * 
	 *         Script commented as it results in deletion of Default redaction Tag
	 * 
	 */
//	@Test(description = "RPMXCON-52010", enabled = true, groups = { "regression" })
	public void verifyDFRedactionNotExistFirstRedactionShouldSelect() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		RedactionPage redactionPage = new RedactionPage(driver);
		String RedactName = "AA" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52010");
		baseClass.stepInfo("Verify that when ‘Default Redaction Tag’ doesn’t exist then on applying audio redaction, "
				+ "the application must automatically select the first in the list of redaction tags");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection
		String defautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"In default : Application automatically selected the ‘Default Redaction Tag’",
				"In default : invalid redaction tag selected");

		// Deleting the default redaction
		redactionPage.DeleteRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Deleting the default redaction tag for validation");

		// Launch DocVia via Search
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection
		String firstDefaultTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(firstDefaultTagSelection, RedactName,
				"In default : Application automatically selecting the first readction in dropdown after deleting DF’",
				"In default : first redaction tag not selected");

		driver.waitForPageToBeReady();
		docViewPage.audioRedactionBasesOnTime(3, 4);
		baseClass.stepInfo("Adding the redaction");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record added Successfully");
		baseClass.CloseSuccessMsgpopup();

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		redactionPage.AddRedaction(Input.defaultRedactionTag, Input.rmu1FullName);
		redactionPage.DeleteRedaction(RedactName);

		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that for PA user after impersonation as RMU/Reviewer should
	 *         allow to select only one redaction tag while editing the redaction
	 * 
	 */
	@Test(description = "RPMXCON-52008", enabled = true, groups = { "regression" })
	public void verifyPaUserImpAndEditRedaction() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		RedactionPage redactionPage = new RedactionPage(driver);
		String RedactName = "Readc" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52008");
		baseClass.stepInfo("Verify that for PA user after impersonation as RMU/Reviewer "
				+ "should allow to select only one redaction tag while editing the redaction");

		// Login as USER
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as : " + Input.pa1userName);

		// impersonating to rmu
		baseClass.impersonatePAtoRMU();

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection
		String defautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"In default : Application automatically selected the ‘Default Redaction Tag’",
				"In default : invalid redaction tag selected");

		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docViewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// Check Default Selection
		String modifyReadction = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(modifyReadction, RedactName, "In default : Modified 'Redaction Tag’ are displaying",
				"In default : invalid redaction tag selected");

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		redactionPage.DeleteRedaction(RedactName);
		baseClass.passedStep("Pa User can impersonate and edit the redaction tag");

		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 4/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that for DA user after impersonation as RMU/Reviewer should
	 *         allow to select only one redaction tag while editing the redaction
	 * 
	 */
	@Test(description = "RPMXCON-52006", enabled = true, groups = { "regression" })
	public void verifyDaUserImpAndEditRedaction() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		RedactionPage redactionPage = new RedactionPage(driver);
		String RedactName = "Readc" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-52006");
		baseClass.stepInfo("Verify that for DA user after impersonation as RMU/Reviewer "
				+ "should allow to select only one redaction tag while editing the redaction");

		// Login as USER
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as : " + Input.da1userName);

		// impersonating to rmu
		baseClass.impersonateDAtoRMU();

		redactionPage.AddRedaction(RedactName, Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Validate the default redaction tag
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(20);

		// Check Default Selection
		String defautTagSelection = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"In default : Application automatically selected the ‘Default Redaction Tag’",
				"In default : invalid redaction tag selected");

		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// Modififying the readction
		docViewPage.getRedactionModify().waitAndClick(5);
		baseClass.stepInfo("Editing the readction tag");

		// select redaction tags
		baseClass.waitForElement(docViewPage.getDocview_AudioRedactions());
		docViewPage.getDocview_AudioRedactions().selectFromDropdown().selectByVisibleText(RedactName);
		driver.waitForPageToBeReady();

		// Check Default Selection
		String modifyReadction = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(modifyReadction, RedactName, "In default : Modified 'Redaction Tag’ are displaying",
				"In default : invalid redaction tag selected");

		// click on save button
		docViewPage.getSaveButton().waitAndClick(20);

		// verify success message
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.CloseSuccessMsgpopup();

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		redactionPage.DeleteRedaction(RedactName);
		baseClass.passedStep("Da User can impersonate and edit the redaction tag");

		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 5/07/22 Modified date: NA Modified by: NA Description :
	 *         Verify that persistent hits should be displayed on audio doc view
	 *         when searched with terms using "within" and work product
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51887", enabled = true, groups = { "regression" })
	public void verifyPersistentHitUsingWithinAndWorkProduct() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String saveSearchName = "search" + Utility.dynamicNameAppender();
		List<String> hitPanel = Arrays.asList(Input.audioSearch, Input.audioSearchString1);

		baseClass.stepInfo("Test case id :RPMXCON-51887");
		baseClass.stepInfo(
				"Saved search > Doc View when search is with Metadata & Content search first and then Audio search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.saveSearch(saveSearchName);
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// combination search using audio and workproduct
		sessionSearch.configureAudioSearchBlock(Input.audioSearchString1, Input.audioSearch, Input.language, 65, "ALL",
				"Within:", "10");
		sessionSearch.getWorkproductBtnC().waitAndClick(5);
		sessionSearch.getSavedSearchResult().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(sessionSearch.getSelectWorkProductSSResults(saveSearchName));
		sessionSearch.getSelectWorkProductSSResults(saveSearchName).waitAndClick(5);
		sessionSearch.getInsertInToQueryBtn().waitAndClick(10);
		driver.scrollPageToTop();
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		if (sessionSearch.getYesQueryAlert().isElementAvailable(8))
			try {
				sessionSearch.getYesQueryAlert().waitAndClick(8);
			} catch (Exception e) {
				// TODO: handle exception
			}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		sessionSearch.getPureHitsCount().isElementAvailable(10);
		baseClass.waitTime(5);
		int pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
		baseClass.stepInfo("Navigating to docview page with combination of audio and workproduct");
		sessionSearch.ViewInDocView();

		// validating persistent hit presence using combination
		docViewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(hitPanel);
		baseClass.passedStep("Persistent hit are displaying using combination search");

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 5/07/22 Modified date: NA Modified by: NA
	 * @Description : Verify the after adding 'Reviewer Remark' on Audio document ,
	 *              request 'Add Reviewer Remark' should be complete immediately
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51826", enabled = true, groups = { "regression" })
	public void verifyAddReviewerRemark() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		Map<String, String> datas = new HashMap<String, String>();
		String remark = "Remark" + Utility.dynamicNameAppender();
		int iteration = 1;

		baseClass.stepInfo("Test case id :RPMXCON-51826");
		baseClass.stepInfo("Verify the after adding 'Reviewer Remark' on Audio document , "
				+ "request 'Add Reviewer Remark' should be complete immediately");

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docviiew page");

		// adding remarks and verifying success message
		datas = docViewPage.addRemarkToDocumentsT(iteration, remark, false, "Success");
		baseClass.stepInfo("Record added Successfully");
		docViewPage.deleteExistingRemark();

		// validating request
		boolean reviewerTab = docViewPage.getAdvancedSearchAudioRemarkPlusIcon().isElementAvailable(2);
		softAssert.assertTrue(reviewerTab);
		baseClass.passedStep("Add reviewer remarks request completed immediately");
		softAssert.assertAll();

		loginPage.logout();

	}

	/**
	 * Author : Baskar date: 7/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to apply the
	 * stamp, then waveform should be loaded [Greater than 1 hr audio file]
	 */

	@Test(description = "RPMXCON-51823", enabled = true, groups = { "regression" })
	public void validationStampForOneHourDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51823");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to apply the stamp, "
				+ "then waveform should be loaded [Greater than 1 hr audio file]");
		// Login as Reviewer Manager

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "colour" + Utility.dynamicNameAppender();

		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		driver.waitForPageToBeReady();

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

		// Edit coding form and saving the stamp as pre-requisties
		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(stampName, Input.stampColour);
		baseClass.stepInfo("Document saved successfully");

		// clicking the saved stamp
		docViewPage.lastAppliedStamp(Input.stampColour);
		docViewPage.codingFormSaveButton();

		// verifying waveform
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssert.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssert.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssert.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// validating the after zoom in the document
		// clicking the saved stamp
		docViewPage.lastAppliedStamp(Input.stampColour);
		docViewPage.codingFormSaveButton();

		// verifying waveform after zoom
		boolean waveforms = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssert.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");

		// validating audio is still playing after zoom
		boolean audioPlays = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssert.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");
		softAssert.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Baskar date: 07/07/22 Modified date: NA Modified by: NA
	 * @Description : Delete any one Remark from the multiple remarks added for
	 *              audio documents
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51674", enabled = true, groups = { "regression" })
	public void verifyDeletedRemarks() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		Map<String, String> datas = new HashMap<String, String>();
		String remarkOne = "RemarkOne" + Utility.dynamicNameAppender();
		String remarkTwo = "RemarkTwo" + Utility.dynamicNameAppender();
		int iteration = 1;
		int iterationOne = 1;

		baseClass.stepInfo("Test case id :RPMXCON-51674");
		baseClass.stepInfo("Delete any one Remark from the multiple remarks added for audio documents");

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docviiew page");

		// adding remarks and verifying success message
		datas = docViewPage.addRemarkToDocumentsT(iteration, remarkOne, false, "Success");
		datas = docViewPage.addContinuousRemark(iterationOne, remarkTwo, "Success");
		baseClass.stepInfo("Record added Successfully");

		// deleting the remarks
		docViewPage.deleteRemark(remarkOne + 0);
		loginPage.logout();

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docview page to verify reamrks text");

		// click on remarks button
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionFalse = docViewPage.getDeleteRedaction(remarkOne + 0).isElementAvailable(4);
		softAssert.assertFalse(radctionFalse);
		docViewPage.deleteExistingAudioRedactions();
		softAssert.assertAll();
		baseClass.passedStep("Deleted reamrks not displaying  in docview page.when user login ");

		loginPage.logout();

	}

	/**
	 * @author Baskar date: 07/07/22 Modified date: NA Modified by: NA
	 * @Description : Update any one Remarks from the multiple remarks to an Audio
	 *              document
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-51673", enabled = true, groups = { "regression" })
	public void verifyUpdateRemarks() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		Map<String, String> datas = new HashMap<String, String>();
		String remarkOne = "RemarkOne" + Utility.dynamicNameAppender();
		String remarkTwo = "RemarkTwo" + Utility.dynamicNameAppender();
		String modify = "modify" + Utility.dynamicNameAppender();
		int iteration = 1;
		int iterationOne = 1;

		baseClass.stepInfo("Test case id :RPMXCON-51673");
		baseClass.stepInfo("Update any one Remarks from the multiple remarks to an Audio document");

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docviiew page");

		// adding remarks and verifying success message
		datas = docViewPage.addRemarkToDocumentsT(iteration, remarkOne, false, "Success");
		datas = docViewPage.addContinuousRemark(iterationOne, remarkTwo, "Success");
		baseClass.stepInfo("Record added Successfully");

		// modifying the remarks
		baseClass.waitForElement(docViewPage.getModifyRedaction(remarkOne + 0));
		docViewPage.getModifyRedaction(remarkOne + 0).waitAndClick(5);
		baseClass.waitForElement(docViewPage.getRemarkTextArea());
		docViewPage.getRemarkTextArea().Clear();
		baseClass.waitForElement(docViewPage.getRemarkTextArea());
		docViewPage.getRemarkTextArea().SendKeys(modify);
		baseClass.waitForElement(docViewPage.getSaveEditRemarks());
		docViewPage.getSaveEditRemarks().waitAndClick(5);
		baseClass.VerifySuccessMessage("Record Updated Successfully");
		baseClass.stepInfo("updating the existing remarks");
		loginPage.logout();

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User landed on the docview page to verify reamrks text after update");

		// click on remarks button
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkIcon());
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionTrue = docViewPage.getDeleteRedaction(modify).isElementAvailable(4);
		softAssert.assertTrue(radctionTrue);
		baseClass.stepInfo("Updated remarks shows correctlly");
		boolean radctionFalse = docViewPage.getDeleteRedaction(remarkTwo).isElementAvailable(4);
		softAssert.assertTrue(radctionFalse);
		baseClass.stepInfo("un-Updated remarks displayed as previous one");

		// deleting the remarks
		docViewPage.deleteRemark(remarkTwo);
		docViewPage.deleteRemark(modify);
		softAssert.assertAll();
		baseClass.passedStep("Updated reamrks displaying in docview page.when user login ");

		loginPage.logout();

	}

	/**
	 * Author : Baskar date: 7/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify RMU/Reviewer can listen the audio files in Doc View when
	 * redirecting in context of an assignment
	 */

	@Test(description = "RPMXCON-51064", enabled = true, groups = { "regression" })
	public void validatingAudioDocsFromAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51064");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to apply the stamp, "
				+ "then waveform should be loaded [Greater than 1 hr audio file]");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();

		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();

		// impersonating rmu to rev
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validating the audio file using assignments
		// playing audio file
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.stepInfo("User playing the audio file");

		// verifying audio file can listenable
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssert.assertTrue(waveform);

		// validating audio is still playing
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssert.assertTrue(audioPlay);
		baseClass.passedStep("Audio file playing and user can listenable");
		softAssert.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: 8/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify RMU/Reviewer can save coding form, save coding stamp on
	 * doc view in an assignment when reviewing the audio file
	 */

	@Test(description = "RPMXCON-48713", enabled = true, groups = { "regression" })
	public void verifyAfterImpAudioFile() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48713");
		baseClass.stepInfo("Verify RMU/Reviewer can save coding form, save coding stamp "
				+ "on doc view in an assignment when reviewing the audio file");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "colour" + Utility.dynamicNameAppender();

		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();

		// impersonating to Rmu to Rev
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Edit coding form and save using save button
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		baseClass.stepInfo("Coding form saved successfully for audio file docs ");

		// save using stamp colour label
		docViewPage.stampColourSelection(stampName, Input.stampColour);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		baseClass.stepInfo("Document saved successfully using stamp colour");

		// validating the floppy icon for tool tips should be displayed
		baseClass.waitForElement(docViewPage.getCodingStampToolTipIcon(Input.stampColour));
		if (docViewPage.getCodingStampToolTipIcon(Input.stampColour).Displayed()) {
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(docViewPage.getCodingStampToolTipIcon(Input.stampColour).getWebElement()).build()
					.perform();
			baseClass.passedStep("While doing mouse over tool tip get displayed");
		} else {
			baseClass.failedStep("Tool tip name not displyed");
			System.out.println("Save this coding form as a new stamp not displayed");
		}
		docViewPage.verifySavedStampTooltip(stampName, Input.stampColour);
		softAssert.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: 8/07/2022 Modified date: Modified by: Baskar
	 * Description:Verify RMU/Reviewer can complete the audio file in an assignment
	 */

	@Test(description = "RPMXCON-46858", enabled = true, groups = { "regression" })
	public void verifyAfterImpAudioFileUsingAllOptionInCf() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46858");
		baseClass.stepInfo("Verify RMU/Reviewer can complete the audio file in an assignment");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "colour" + Utility.dynamicNameAppender();
		int size = 5;

		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		List<String> docId = new LinkedList<String>();

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewerManager();

		// impersonating to Rmu to Rev
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Edit coding form and save using save button
		for (int i = 1; i <= size; i++) {
			driver.waitForPageToBeReady();
			docViewPage.getDocView_Select_MiniDocList_Docs(i).waitAndClick(10);
			String docIdPresent = docViewPage.getVerifyPrincipalDocument().getText();
			docId.add(docIdPresent);
		}

		// saving the coding form using complete button
		String firstId = docId.get(0);
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_SelectDOcId(firstId));
		docViewPage.getDocView_MiniDoc_SelectDOcId(firstId).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Document completed successfully");
		baseClass.waitTime(5);

		// validating cursor navigating next docs from using complete buton
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String prnComplete = docViewPage.getVerifyPrincipalDocument().getText();
		softAssert.assertNotEquals(firstId, prnComplete);
		baseClass.stepInfo("Cursor navigated to next document from minidoclist using complete button");

		// validating using coding stamp button
		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(stampName, Input.stampColour);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Coding Stamp saved successfully");
		docViewPage.lastAppliedStamp(Input.stampColour);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.stepInfo("Coding Stamp applied successfully");

		// verifying cursor navigate to next docs
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String prnStampLast = docViewPage.getVerifyPrincipalDocument().getText();
		softAssert.assertNotEquals(prnComplete, prnStampLast);
		baseClass.stepInfo("Cursor navigated to next document from minidoclist using stamp last button");

		// validating using code same as last button
		docViewPage.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String prnCodeSameLast = docViewPage.getVerifyPrincipalDocument().getText();
		softAssert.assertNotEquals(prnCodeSameLast, prnStampLast);
		baseClass.stepInfo("Cursor navigated to next document from minidoclist using code same as last button");

		// verifying as per preceeding document
		baseClass.waitForElement(docViewPage.getDocView_MiniDoc_SelectDOcId(prnStampLast));
		docViewPage.getDocView_MiniDoc_SelectDOcId(prnStampLast).waitAndClick(5);
		docViewPage.verifyingComments(comment);
		baseClass.passedStep("As per all validation its working successfully");
		softAssert.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Aathith Date: 08/23/2022
	 * @Description Verify that application should not hang when the user tries to
	 *              complete the audio document when audio is playing
	 */
	@Test(description = "RPMXCON-51479", dataProvider = "RmuRev", enabled = true, groups = { "regression" })
	public void verifyAudioDocCompleteWithoutHang(String username, String password) throws Exception {

		sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51479");
		baseClass.stepInfo(
				"Verify that application should not hang when the user tries to complete the audio document when audio is playing");

		// inputs
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();

		// playing audio file
		docview.audioPlayPauseIcon().waitAndClick(5);

		// Edit coding form in parent window
		docview.editCodingForm(comment);
		docview.codingFormSaveButton();
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		baseClass.stepInfo(
				"Audio document is completed successfully and application is not hang when user completes the document when audio is playing  ");

		baseClass.passedStep(
				"Verified that application should not hang when the user tries to complete the audio document when audio is playing");
		loginPage.logout();

	}

	/**
	 * @author Aathith Date: 08/23/2022
	 * @Description Verify that application should not hang when the user tries to
	 *              complete the audio document by clicking complete same as last
	 *              when audio is playing
	 */
	@Test(description = "RPMXCON-51481", dataProvider = "RmuRev", enabled = true, groups = { "regression" })
	public void verifyAudioDocCompleteWithSaveAsLast(String username, String password) throws Exception {

		sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51481");
		baseClass.stepInfo(
				"Verify that application should not hang when the user tries to complete the audio document by clicking complete same as last when audio is playing");

		// inputs
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();

		// playing audio file
		docview.audioPlayPauseIcon().waitAndClick(5);

		// Edit coding form in parent window
		docview.editCodingForm(comment);
		docview.codingFormSaveButton();
		baseClass.CloseSuccessMsgpopup();

		driver.waitForPageToBeReady();
		docview.getCodeSameAsLast().waitAndClick(5);
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");
		baseClass.stepInfo(
				"Audio document was completed successfully.Make sure that application wasn't hang when audio document is completed same as last when audio is in playing mode  ");

		baseClass.passedStep(
				"Verified that application should not hang when the user tries to complete the audio document by clicking complete same as last when audio is playing");
		loginPage.logout();

	}

	/**
	 * @author Aathith Date: 08/23/2022
	 * @Description When a user submits an audio search with spaces, Sightline
	 *              treats the space as a keyword separator and returns results for
	 *              a multi-keyword audio search.
	 */
	@Test(description = "RPMXCON-51612", enabled = true, groups = { "regression" })
	public void verifyKeyWordsWithSpaces() throws Exception {

		sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert soft = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51612");
		baseClass.stepInfo(
				"When a user submits an audio search with spaces, Sightline treats the space as a keyword separator and returns results for a multi-keyword audio search.");

		boolean market = false;
		boolean supply = false;
		boolean demand = false;

		// Login As user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1FullName + "");

		// view in docview
		sessionSearch.audioSearch("market" + " " + Keys.ENTER + "supply" + " " + Keys.ENTER + "demand",
				Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();

		// eye icon
		docview.getEyeIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		for (int i = 0; i < 10; i++) {
			if (!market && baseClass.text("MARKET").isElementAvailable(3)) {
				market = true;
			}
			if (!supply && baseClass.text("SUPPLY").isElementAvailable(3)) {
				supply = true;
			}
			if (!demand && baseClass.text("DEMAND").isElementAvailable(3)) {
				demand = true;
			}
			if (market && supply && demand) {
				break;
			}
			docview.getDocView_Next().waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		soft.assertTrue(market, "market");
		soft.assertTrue(supply, "supply");
		soft.assertTrue(demand, "demand");

		soft.assertAll();
		baseClass.passedStep(
				"When a user submits an audio search with spaces, Sightline treats the space as a keyword separator and returns results for a multi-keyword audio search.");
		loginPage.logout();

	}

	/**
	 * @author Aathith Date: 08/23/2022
	 * @Description IE11: Verify when mini doc list child window is open, user
	 *              enters the document number hit the enter key, then corresponding
	 *              audio doc should load in the default view prior to that document
	 *              navigation is done with <<, <, > and >>
	 */
	@Test(description = "RPMXCON-51631", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void verifyDocumentKeysWithCorrespondingAudioDocFromMiniDocList(String username, String password)
			throws Exception {

		sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert soft = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51631");
		baseClass.stepInfo("IE11: Verify when mini doc list child window is open, user enters the document number "
				+ "hit the enter key, then corresponding audio doc should load in the default view prior to that document "
				+ "navigation is done with <<, <, > and >>");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();

		// verify last doc from child window
		docview.clickGearIconOpenMiniDocList();
		docview.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();
		String currentdocid = docview.getDocViewSelectedDocId().getText().trim();
		docview.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String lastdocid = docview.getlastDocinMiniDocView().getText().trim();
		driver.waitForPageToBeReady();
		soft.assertEquals(currentdocid, lastdocid);
		baseClass.passedStep(
				"Audio document was loaded as per the clicked document navigation option i.e.  and >> and same document should be selected from mini doc list child window");

		docview.switchToNewWindow(1);
		docview.getDocView_NumTextBox().SendKeys("3" + Keys.ENTER);
		driver.waitForPageToBeReady();
		currentdocid = docview.getDocViewSelectedDocId().getText().trim();
		docview.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String idOnMiniDoc = docview.getMiniDocListData(3, 2).getText().trim();
		soft.assertEquals(currentdocid, idOnMiniDoc);
		baseClass.passedStep(
				"Corresponding audio document was load in the default view and same document is selected from mini doc list child windowwas");
		driver.close();
		docview.switchToNewWindow(1);

		soft.assertAll();
		baseClass.passedStep(
				"Verified when mini doc list child window is open, user enters the document number hit the enter key, then "
						+ "corresponding audio doc should load in the default view prior to that document navigation is done with <<, <, > and >>");
		loginPage.logout();

	}

	@DataProvider(name = "RmuRev")
	public Object[][] userDetails() {
		return new Object[][] { { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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
	public void verifyAudioDocumentTranslationsTabInMiniDocList2(String username, String password, String role)
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
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
	 * @Description Verify that audio document should be loaded successfully when
	 *              document viewed from mini doc list from Images tab of previous
	 *              document.
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
		softAssert.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.passedStep(
				"Document is selected from mini doc list as per the clicked document navigation option and same audio document loaded in default view");
		softAssert.assertAll();
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
		String color = docViewPage.getDocView_MiniDoc_SelectdocAsText(1, 2).GetCssValue("background-color");
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
		String color1 = docViewPage.getDocView_MiniDoc_SelectdocAsText(5, 2).GetCssValue("background-color");
		System.out.println(color1);
		String ExpectedColor1 = Color.fromString(color1).asHex();
		System.out.println(ExpectedColor1);
		String ActualColor1 = "#3e65ac";
		baseClass.waitTime(5);
		String defaultView = docViewPage.getDefaultViewTab().GetAttribute("class");
		baseClass.compareTextViaContains(defaultView, "active", "audio document is loaded in the default view",
				"audio document is not loaded in the default view");
		if (ActualColor1.equals(ExpectedColor1)) {
			baseClass.passedStep("same document is selected from mini doc list");
		} else {
			baseClass.failedStep("same document is not selected from mini doc list");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/12/2022 RPMXCON-51628
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Edge: Verify when user enters the document id in the "doc id
	 *              input box" and hits the enter key, then corresponding audio doc
	 *              should load in the default view prior to that document
	 *              navigation is done with <<, <, > and >>.
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
		String color = docViewPage.getDocView_MiniDoc_SelectdocAsText(1, 2).GetCssValue("background-color");
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
		String docId = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId);
		docViewPage.getDocView_Last().waitAndClick(5);
		String AfterClickdocId = docViewPage.getDocView_CurrentDocId().getText();
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
		String color1 = docViewPage.getDocView_MiniDoc_SelectdocAsText(10, 2).GetCssValue("background-color");
		System.out.println(color1);
		String ExpectedColor1 = Color.fromString(color1).asHex();
		System.out.println(ExpectedColor1);
		String ActualColor1 = "#3e65ac";
		baseClass.waitTime(5);
		String defaultView = docViewPage.getDefaultViewTab().GetAttribute("class");
		baseClass.compareTextViaContains(defaultView, "active", "audio document is loaded in the default view",
				"audio document is not loaded in the default view");
		if (ActualColor1.equals(ExpectedColor1)) {
			baseClass.passedStep("same document is selected from mini doc list");
		} else {
			baseClass.failedStep("same document is not selected from mini doc list");
		}
		loginPage.logout();
	}

	/**
	 * Author :date: 12/12/22 Modified date: NA Modified by: NA Description:Verify
	 * user can see the applied folder to the audio files on doc view
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
	 * Author : date: 12/12/22 Modified date: NA Modified by: NA Description:To
	 * verify that remarks can be edited if document is marked as Completed in audio
	 * doc view. view
	 * 
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
