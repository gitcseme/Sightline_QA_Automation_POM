package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_IndiumRegression {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	KeywordPage keywordPage;
	DocListPage docListPage;
	String keywordsArrayPT[] = { "test" };

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
//		docViewPage = new DocViewPage(driver);
//		assignmentPage = new AssignmentsPage(driver);
//		sessionSearch = new SessionSearch(driver);
//		keywordPage = new KeywordPage(driver);
//		docListPage=new DocListPage(driver);

	}

	/**
	 * Author : Baskar date: NA Modified date: 13/01/2022 Modified by: Baskar
	 * Description:Verify user can select and apply code same as this for the audio
	 * files
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 01)
	public void validationCodeSameAsAudioDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51077");
		baseClass.stepInfo("Verify user can select and apply code same as this for the audio files");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
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

		// CodingStamp popup verify
		docViewPage.validationAudioDocsCheckMark(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:13/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document navigation should work from audio doc
	 *              view
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 02)
	public void validateLastNavigationOption() throws Exception {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51482");
		baseClass.stepInfo("Verify that document navigation should work from audio doc view");

		// Login As
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// validation navigation option
		docViewPage.verifyThatIsLastDoc();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Steffy date:17/01/22 Modified date: NA Modified by: Steffy
	 * @Description : RPMXCON-551318 Verify that audio files pause functionality is
	 *              working properly inside docview screen
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 03)
	public void validatePauseAudioInsideDocView() throws Exception {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-55318");
		baseClass.stepInfo("Verify that audio files pause functionality is working properly inside docview screen");

		// Login As
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User is logged in as Review Manager");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		docViewPage.playAudioOnly();

		softAssertion.assertFalse(docViewPage.getDocView_IconPlaying().isDisplayed());
		baseClass.passedStep("Audio pause functionality is working properly");

		// logout
		loginPage.logout();

		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User is logged in as PA");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		docViewPage.playAudioOnly();

		softAssertion.assertFalse(docViewPage.getDocView_IconPlaying().isDisplayed());
		baseClass.passedStep("Audio pause functionality is working properly");

		// logout
		loginPage.logout();

		// Login As
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User is logged in as Reviewer");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		docViewPage.playAudioOnly();

		softAssertion.assertFalse(docViewPage.getDocView_IconPlaying().isDisplayed());
		baseClass.passedStep("Audio pause functionality is working properly");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Steffy date:17/01/22 Modified date: NA Modified by: Steffy
	 * @Description : Verify that audio files play functionality is working properly
	 *              inside docview screen
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 04)
	public void validatePlayAudioInsideDocView() throws Exception {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51092");
		baseClass.stepInfo("Verify that audio files play functionality is working properly inside docview screen");

		// Login As
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User is logged in as Review Manager");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		docViewPage.playAudioOnly();

		// logout
		loginPage.logout();

		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User is logged in as PA");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		docViewPage.playAudioOnly();

		// logout
		loginPage.logout();

		// Login As
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User is logged in as Reviewer");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Open the searched documents in doc view");

		docViewPage.playAudioOnly();

		// logout
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51502 Description : To verify that Persistent hits are working for
	 * audio files if user redirect to doc view from Doc list
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 05)
	public void verifyPersistentHitsForAudioFiles() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docListPage = new DocListPage(driver);
		baseClass.stepInfo("Test case id :RPMXCON-51502");
		baseClass.stepInfo(
				"verify that Persistent hits are working for audio files if user redirect to doc view from Doc list");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Performing advanced search
		sessionSearch.advancedContentSearch(Input.testData1);

		// view in doclist
		sessionSearch.ViewInDocList();

		// Select all docs and view as docview
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docListPage.getSelectAll().Visible() && docListPage.getSelectAll().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docListPage.getSelectAll());
		docListPage.getSelectAll().waitAndClick(30);
		docListPage.getPopUpOkBtn().Click();

		docListPage.docListToDocView();
		baseClass.passedStep("Selected all documents and perform action view in docview");

		// click eye icon and verify the availability of search term
		String searchTerm = docViewPage.getPersistentHit(Input.testData1);

		int searchTermPanelCount = docViewPage.getHitPanelCollection().size();

		if (searchTerm.contains(Input.testData1) && searchTermPanelCount > 0) {
			baseClass.passedStep("Search term displayed");

		} else {
			baseClass.failedStep("Search term not displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-46926 Description : Verify Persistent hits navigation should work
	 * when audio documents searched with the phrase with quotes
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 06)
	public void verifyPersistentHitNavigation() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-46926");
		baseClass.stepInfo(
				"Verify Persistent hits navigation should work when audio documents searched with the phrase with quotes");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Performing advanced search with audio
		sessionSearch.verifyaudioSearchWarning(Input.searchPhraseWithQuote, Input.language);

		// view in docview
		sessionSearch.ViewInDocView();

		// click eye icon and click on navigation in panel

		String availableSearchTerm = docViewPage.getAudioPersistentHit(Input.searchPhraseWithQuote);

		if (availableSearchTerm.contains(Input.searchPhraseWithQuote)) {
			baseClass.passedStep("persistent panel opened and searched phrase displayed");

		}
		driver.scrollingToElementofAPage(docViewPage.audioPersistentForwardNavigate());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewPage.audioPersistentForwardNavigate().Visible()
						&& docViewPage.audioPersistentForwardNavigate().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);

		String audioPlayerStatus = docViewPage.audioPlayPauseIcon().GetAttribute("title");

		if (audioPlayerStatus.equalsIgnoreCase("pause")) {
			baseClass.passedStep("Persistent hit navigation works when searched with Phrase and quote");

		} else {
			baseClass.failedMessage("persistent hit navigation not worked when searched with phrase and quote");

		}
		loginPage.logout();

	}

	/**
	 * @author Raghuram.A date: 02/03/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-47002 Description : Saved search > Doc View when search is
	 *         with Metadata & Content search first and then Audio search, hits
	 *         should be highlighted
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void hitsCheckWithDvAudio() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> searchList = new ArrayList<>();
		Set<String> purehitKeySet = new HashSet<String>();
		List<String> docIDlist = new ArrayList<>();
		int pH = 0;
		String firnstDocname;
		String searchName = "", metaDataType = "DocID", metaDataIp = "ID00*";
		String[] combinations = { "Metadata + Audio" };
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-47002");
		baseClass.stepInfo(
				"Saved search > Doc View when search is with Metadata & Content search first and then Audio search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Combinational Search
		baseClass.stepInfo("Audio Input : " + audioSearchInput);
		Map<String, Integer> purehit = sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(
				Input.searchString1, Input.searchString1, audioSearchInput, Input.language, "", combinations,
				metaDataType, metaDataIp, "");
		driver.waitForPageToBeReady();

		// Data Collection
		purehitKeySet = purehit.keySet();
		Iterator<String> iterator = purehitKeySet.iterator();
		while (iterator.hasNext()) {
			searchName = iterator.next();
			searchList.add(searchName);
			pH = purehit.get(searchName);
		}

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.getToDocView().waitAndClick(5);

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(audioSearchInput);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		loginPage.logout();

	}

	/**
	 * @author Raghuram.A date: 02/03/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-47003 Description : Saved search > Doc View when, search
	 *         is with Audio search first and then Metadata & Content hits should be
	 *         highlighted
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void hitsCheckWithDvAudioM() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> searchList = new ArrayList<>();
		Set<String> purehitKeySet = new HashSet<String>();
		List<String> docIDlist = new ArrayList<>();
		int pH = 0;
		String firnstDocname;
		String searchName = "", metaDataType = "DocID", metaDataIp = "ID00*";
		String[] combinations = { "Audio + Metadata" };
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-47003");
		baseClass.stepInfo(
				"Saved search > Doc View when, search is with Audio search first and then Metadata & Content hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Combinational Search
		baseClass.stepInfo("Audio Input : " + audioSearchInput);
		Map<String, Integer> purehit = sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(
				Input.searchString1, Input.searchString1, audioSearchInput, Input.language, "", combinations,
				metaDataType, metaDataIp, "");
		driver.waitForPageToBeReady();

		// Data Collection
		purehitKeySet = purehit.keySet();
		Iterator<String> iterator = purehitKeySet.iterator();
		while (iterator.hasNext()) {
			searchName = iterator.next();
			searchList.add(searchName);
			pH = purehit.get(searchName);
		}

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.getToDocView().waitAndClick(5);

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(audioSearchInput);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		// Delete Search
		baseClass.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Search > Bulk Assign Verify that audio hits should be
	 *              highlighted in context of an existing/new assignment when search
	 *              is set up with the Metadata & Content search first and then
	 *              Audio search [RPMXCON-46997]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyBulkAssign() throws InterruptedException {
		baseClass = new BaseClass(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		String[] combinations = { "Metadata + Audio" };
		String metaDataIp = "ID0*";
		List<String> docIDlist = new ArrayList<>();

		baseClass.stepInfo("Test case id :RPMXCON-46997");
		baseClass.stepInfo(
				"Search > Bulk Assign Verify that audio hits should be highlighted in context of an existing/new assignment when search is set up with the Metadata & Content search first and then Audio search");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search for metadata and audio
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(null, null, Input.audioSearch, Input.language,
				"", combinations, Input.docName, metaDataIp, "min");

		driver.scrollPageToTop();
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName);

		// verify eye icon
		docIDlist = miniDocListpage.getDocListDatas();
		String firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearch);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		// existing assignmnet creation
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.bulkAssign_Persistant(true);
		sessionSearch.bulkAssign_withoutshoppingCartAdd(assignmentName);

		assignmentPage.navigateToAssignmentsPage();
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName);
		driver.waitForPageToBeReady();

		// verify eye icon
		docIDlist = miniDocListpage.getDocListDatas();
		String secondDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + secondDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearch);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Search > Bulk Assign Verify that audio hits should be
	 *              highlighted in context of an existing/new assignment when search
	 *              is set up with the Audio search first then Metadata & Content
	 *              search [RPMXCON-46996]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyBulkAssignFOrAudioAndMetadata() throws InterruptedException {
		baseClass = new BaseClass(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		String[] combinations = { "Audio + Metadata" };
		String metaDataIp = "ID0*";
		List<String> docIDlist = new ArrayList<>();

		baseClass.stepInfo("Test case id :RPMXCON-46996");
		baseClass.stepInfo(
				"Search > Bulk Assign Verify that audio hits should be highlighted in context of an existing/new assignment when search is set up with the Audio search first then Metadata & Content search");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search for metadata and audio
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(null, null, Input.audioSearch, Input.language,
				"", combinations, Input.docName, metaDataIp, "min");

		driver.scrollPageToTop();
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName);

		// verify eye icon
		docIDlist = miniDocListpage.getDocListDatas();
		String firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearch);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		// existing assignmnet creation
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.bulkAssign_Persistant(true);
		sessionSearch.bulkAssign_withoutshoppingCartAdd(assignmentName);

		assignmentPage.navigateToAssignmentsPage();
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName);
		driver.waitForPageToBeReady();

		// verify eye icon
		docIDlist = miniDocListpage.getDocListDatas();
		String secondDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + secondDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearch);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		loginPage.logout();
	}
	/**
	 * Author :Aathith  date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51862 Description : When a user tries to navigate to Audio DocView with some documents, the first document must present completely and be ready to be acted upon fully
	 * @throws InterruptedException 
	 * 
	 */
	@Test(enabled = true ,groups = { "regression" }, priority = 11)
	public void verifyAudioDocumentState() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51862");
		baseClass.stepInfo("When a user tries to navigate to Audio DocView with some documents, the first document must present completely and be ready to be acted upon fully");
		
		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : "+Input.rmu1userName);
		
		// Performing advanced search with audio
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		//view in docview
    	sessionSearch.ViewInDocView();

        driver.waitForPageToBeReady();
        boolean flag =((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState").equals("complete");
		if (flag)
        { 
			softAssertion.assertTrue(flag);
			baseClass.passedStep("DocView loaded properly");
			System.out.println("passed");
        }else {
        	baseClass.failedStep("verification failed");
        }
		boolean flag1 = docViewPage.getAudioBlock().isElementPresent();
		if(flag1)
		{
			softAssertion.assertTrue(flag1);
			baseClass.passedStep("DocView audio panel Presented completly");
			System.out.println("passed");
		}else {
			baseClass.failedStep("verification failed");
		}
        baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
        docViewPage.audioPlayPauseIcon().waitAndClick(10);

        
        boolean flag2 = docViewPage.getAudioPlayState().GetAttribute("class").contains("playing");
		if(flag2)
		{
			softAssertion.assertTrue(flag2);
			baseClass.passedStep("DocView audio ready to be acted upon fully");
			System.out.println("passed");
		}else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}
		
		baseClass.passedStep("Verified When a user tries to navigate to Audio DocView with some documents, the first document must present completely and be ready to be acted upon fully");
        loginPage.logout();
        
        
	}
	/**
	 * Author :Aathith  date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51818 Description : Verify that when audio file is playing and clicked to Save the document, then waveform should be loaded [Less than 1 hr audio file]
	 * @throws InterruptedException 
	 * 
	 */
	@Test(enabled = true ,groups = { "regression" }, priority = 12 )
	public void verifyAudioDocumentPlayEvenCfisSave() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51818");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to Save the document, then waveform should be loaded [Less than 1 hr audio file]");
		
		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : "+Input.rmu1userName);
		
		// Performing advanced search with audio
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);		
		//view in docview
    	sessionSearch.ViewInDocView();

        driver.waitForPageToBeReady();
        baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
        docViewPage.audioPlayPauseIcon().waitAndClick(10);
        
        String docId = docViewPage.getAudioDocId().GetAttribute("style").trim();
        
        docViewPage.editCodingFormSave();
        
        String docId1 = docViewPage.getAudioDocId().GetAttribute("style").trim();
		if(docId.equalsIgnoreCase(docId1))
		{
			softAssertion.assertEquals(docId,docId1);
			baseClass.passedStep("waveform is displayed for the same document");
			System.out.println("passed");
		}else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}
        
        boolean flag2 = docViewPage.getAudioPlayState().GetAttribute("class").contains("playing");
		if(flag2)
		{
			softAssertion.assertTrue(flag2);
			baseClass.passedStep("audio is also in play mode after coding form saved");
			System.out.println("passed");
		}else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}
		
		baseClass.passedStep("Verified that when audio file is playing and clicked to Save the document, then waveform should be loaded [Less than 1 hr audio file]");
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
			// loginPage.logout();
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
