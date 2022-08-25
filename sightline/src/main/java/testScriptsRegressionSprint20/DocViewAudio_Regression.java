package testScriptsRegressionSprint20;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

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
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression {

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

		datas = docviewPage.addRemarkToDocumentsT(iteration, remark, true, "Success");
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		// Verify Existing remarks + Edit File
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
		softAssertion.assertTrue(zoomBar);
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
	public void verifyAudioPlayStartedFromRedaction()
			throws InterruptedException {

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
	public void verifyuserCanPlayRedaction()
			throws InterruptedException {

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

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

}
