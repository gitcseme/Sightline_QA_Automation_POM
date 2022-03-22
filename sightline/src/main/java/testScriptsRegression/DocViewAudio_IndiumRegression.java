package testScriptsRegression;

import java.awt.AWTException;
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
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
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
	SavedSearch savedSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AnnotationLayer annatationLayer;
	RedactionPage redact;
	MiniDocListPage miniDocListpage;
	String keywordsArrayPT[] = { "test" };
	String nodeName = null;
	String savedSearch1 = "Search1" + Utility.dynamicNameAppender();
	String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
	String searchString1 = "interview";
	String searchString2 = "six";
	List<String> DocIdsWithBothSearchTerm = new ArrayList<String>();
	List<String> UniqueSearchString1 = new ArrayList<String>();
	List<String> UniqueSearchString2 = new ArrayList<String>();
	List<String> audioSearchTerms = new ArrayList<String>();

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

	@DataProvider(name = "RMUandREV")
	public Object[][] RMUandREV() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
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
				metaDataType, metaDataIp, "", true);
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
				metaDataType, metaDataIp, "", true);
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
				"", combinations, Input.docName, metaDataIp, "min", true);

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
				"", combinations, Input.docName, metaDataIp, "min", true);

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
	 * Author : Baskar date: NA Modified date: 07/02/2022 Modified by: Baskar
	 * Description:Verify that when mini doc list child window scrolled down and
	 * 'Loading' is displayed then mini doc list child window should be loaded with
	 * the documents
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void validatePlayIconInMiniDocListChild() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51839");
		baseClass.stepInfo("Verify that when mini doc list child window scrolled down and 'Loading' "
				+ "is displayed then mini doc list child window should be loaded with the documents");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		String miniDocListChild = Input.url + "DocumentViewer/DocViewChild";

		// search to Assignment creation
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		// Opening minidoclist child window
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		if (driver.getUrl().equalsIgnoreCase(miniDocListChild)) {
			baseClass.passedStep("User can able to popup out the minidcolist child window");
		}
		docViewPage.switchToNewWindow(1);
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		docViewPage.switchToNewWindow(2);

		// scrolling the minidoclist
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		// Before scroll time
		Long beforeScroll = (Long) jse
				.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		baseClass.stepInfo("Before scroll position:" + beforeScroll + " at minidoclist child");
		long start = System.currentTimeMillis();
		jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,15000)");

		// After scroll time
		Long afterScroll = (Long) jse
				.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		baseClass.stepInfo("Before scroll position:" + afterScroll + " at minidoclist child");
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
		System.out.println(timeSeconds);

		// Should not take much time to load document(Test step time not mentioned)
		if (timeSeconds <= 2) {
			baseClass.passedStep("Document not taking much time to load the document when scrolling the minidoclist");
		} else {
			baseClass.failedStep("Failed to load document in minidoclist");
		}
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		softAssertion.assertAll();
		baseClass.stepInfo("While scrolling the minidcolist audio player is playing");
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 07/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify that error in console should not be displayed as
	 *                     'Uncaught Type Error: _ChildMiniDoc. $ is not a function'
	 *                     when mini doc list child window opened after completing
	 *                     audio document same as last prior document is completed
	 *                     after applying stamp
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void validatingConsoleErrorMiniDocListUsingStamp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51830");
		baseClass.stepInfo("Verify that error in console should not be displayed as 'Uncaught Type Error: "
				+ "_ChildMiniDoc. $ is not a function' when mini doc list child window opened after completing "
				+ "audio document same as last prior document is completed after applying stamp");

		String assignment = "AssAudio" + Utility.dynamicNameAppender();
		String comment = "comments" + Utility.dynamicNameAppender();
		String stampName = "stamp" + Utility.dynamicNameAppender();

		String miniDocListChild = Input.url + "DocumentViewer/DocViewChild";
		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

//	    searching document for assignment creation and new coding form created (existing)
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codeFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		System.out.println(assignment);

		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation for console error
		// edit coding form and save the stamp
		String prnDocs = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(stampName, Input.stampSelection);
		driver.waitForPageToBeReady();

		// applying the save stamp
		docViewPage.lastAppliedStamp(Input.stampSelection);
		driver.waitForPageToBeReady();
		String prnSecDocs = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDocs, prnSecDocs);
		baseClass.stepInfo("Document navigated and selected next docs from minidcolist");

		// click code same as last
		docViewPage.getCodeSameAsLast().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Coded as per the coding form for the previous document");
		docViewPage.getDociD(prnSecDocs).waitAndClick(5);
		driver.waitForPageToBeReady();

		// validating coding from filled as per previous
		docViewPage.verifyingComments(comment);

		// Switching to minidoclist child window
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		if (driver.getUrl().equalsIgnoreCase(miniDocListChild)) {
			baseClass.passedStep("User can able to popup out the minidcolist child window");
		}

		// validating in minidcolist document loaded completely or not in console
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		boolean readyStateComplete = (boolean) jse.executeScript("return document.readyState").toString()
				.equals("complete");
		System.out.println(readyStateComplete);
		softAssertion.assertTrue(readyStateComplete);
		baseClass.passedStep(
				"Minidoclist document loaded completely after performing the stamp code same as last action");
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 07/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify that error in console should not be displayed as
	 *                     'Uncaught Type Error: _ChildMiniDoc. $ is not a function'
	 *                     when mini doc list child window opened after completing
	 *                     audio document same as last prior document is completed
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void validatingConsoleErrorMiniDocListUsingComplete() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51829");
		baseClass.stepInfo("Verify that error in console should not be displayed "
				+ "as 'Uncaught Type Error: _ChildMiniDoc. $ is not a function' when "
				+ "mini doc list child window opened after completing audio document "
				+ "same as last prior document is completed");

		String assignment = "AssAudio" + Utility.dynamicNameAppender();
		String comment = "comments" + Utility.dynamicNameAppender();
		String miniDocListChild = Input.url + "DocumentViewer/DocViewChild";

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

//	    searching document for assignment creation and new coding form created (existing)
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codeFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		System.out.println(assignment);

		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation for console error
		// edit coding form and complete
		String prnDocs = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		driver.waitForPageToBeReady();
		String prnSecDocs = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDocs, prnSecDocs);
		baseClass.stepInfo("Document navigated and selected next docs from minidcolist");

		// click code same as last
		docViewPage.getCodeSameAsLast().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Coded as per the coding form for the previous document");
		docViewPage.getDociD(prnSecDocs).waitAndClick(5);
		driver.waitForPageToBeReady();

		// validating coding from filled as per previous
		docViewPage.verifyingComments(comment);

		// Switching to minidoclist child window
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		if (driver.getUrl().equalsIgnoreCase(miniDocListChild)) {
			baseClass.passedStep("User can able to popup out the minidcolist child window");
		}

		// validating in minidcolist document loaded completely or not in console
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		boolean readyStateComplete = (boolean) jse.executeScript("return document.readyState").toString()
				.equals("complete");
		System.out.println(readyStateComplete);
		softAssertion.assertTrue(readyStateComplete);
		baseClass.passedStep("Minidoclist document loaded completely after completing code same as last action");
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author :Aathith date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51862 Description : When a user tries to navigate to Audio DocView
	 * with some documents, the first document must present completely and be ready
	 * to be acted upon fully
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyAudioDocumentState() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51862");
		baseClass.stepInfo(
				"When a user tries to navigate to Audio DocView with some documents, the first document must present completely and be ready to be acted upon fully");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// Performing advanced search with audio
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		// view in docview
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		boolean flag = ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState")
				.equals("complete");
		if (flag) {
			softAssertion.assertTrue(flag);
			baseClass.passedStep("DocView loaded properly");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
		}
		boolean flag1 = docViewPage.getAudioBlock().isElementPresent();
		if (flag1) {
			softAssertion.assertTrue(flag1);
			baseClass.passedStep("DocView audio panel Presented completly");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
		}
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);

		boolean flag2 = docViewPage.getAudioPlayState().GetAttribute("class").contains("playing");
		if (flag2) {
			softAssertion.assertTrue(flag2);
			baseClass.passedStep("DocView audio ready to be acted upon fully");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}

		baseClass.passedStep(
				"Verified When a user tries to navigate to Audio DocView with some documents, the first document must present completely and be ready to be acted upon fully");
		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51818 Description : Verify that when audio file is playing and
	 * clicked to Save the document, then waveform should be loaded [Less than 1 hr
	 * audio file]
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyAudioDocumentPlayEvenCfisSave() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51818");
		baseClass.stepInfo(
				"Verify that when audio file is playing and clicked to Save the document, then waveform should be loaded [Less than 1 hr audio file]");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// Performing advanced search with audio
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		// view in docview
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);

		String docId = docViewPage.getAudioDocId().GetAttribute("style").trim();

		docViewPage.editCodingFormSave();

		String docId1 = docViewPage.getAudioDocId().GetAttribute("style").trim();
		if (docId.equalsIgnoreCase(docId1)) {
			softAssertion.assertEquals(docId, docId1);
			baseClass.passedStep("waveform is displayed for the same document");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}

		boolean flag2 = docViewPage.getAudioPlayState().GetAttribute("class").contains("playing");
		if (flag2) {
			softAssertion.assertTrue(flag2);
			baseClass.passedStep("audio is also in play mode after coding form saved");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}

		baseClass.passedStep(
				"Verified that when audio file is playing and clicked to Save the document, then waveform should be loaded [Less than 1 hr audio file]");
		loginPage.logout();

	}

	/**
	 * @Author Raghuram A date:01/02/2022 Modified date: NA Modified by:N/A
	 * @Description : Verify that user can edit the reviewer remarks in audio doc
	 *              view [RPMXCON-46865]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(enabled = true, dataProvider = "RMUandREV", groups = { "regression" }, priority = 16)
	public void VerifyAddedReviewerRemarkForAudioDocInBasicSearch(String username, String password, String fullName)
			throws Exception {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docviewpage = new DocViewPage(driver);
		SavedSearch saveSearch = new SavedSearch(driver);

		Map<String, String> datas = new HashMap<String, String>();
		String remark = "Remark" + Utility.dynamicNameAppender();
		String searchName = "SS" + Utility.dynamicNameAppender();
		int iteration = 1;

		baseClass.stepInfo("Test case Id:RPMXCON-46865 Sprint 11");
		baseClass.stepInfo("Verify that user can edit the reviewer remarks in audio doc view");

		// login as RMU/reviewer
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Loggedin As : " + fullName);

		// adding remark to audio documents
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.saveSearch(searchName);
		sessionSearch.ViewInDocView();
		datas = docviewpage.addRemarkToDocumentsT(iteration, remark, true, "Success");

		loginPage.logout();

		// login as RMU/reviewer
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Loggedin As : " + fullName);

		// SavedSearch to DocVIew
		saveSearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();

		// Verify Existing remarks + Edit File
		docviewpage.verifyExistingRemarks(iteration, datas, true, true);

		// Delete Search
		baseClass.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		loginPage.logout();

	}

	/**
	 * @author Raghuram.A date: 02/03/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-46994 Description : Search > Doc View Verify that audio
	 *         hits should be highlighted when search is set up with the Audio
	 *         search first then Metadata & Content search Sprint 12
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, dataProvider = "AllTheUsers", groups = { "regression" }, priority = 17)
	public void hitsCheckWithDvAudioMViaSearch(String username, String password, String fullName)
			throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		String firnstDocname;
		String metaDataType = "DocID", metaDataIp = "ID00*";
		String[] combinations = { "Audio + Metadata" };
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-46994 Sprint 12");
		baseClass.stepInfo(
				"Search > Doc View Verify that audio hits should be highlighted when search is set up with the Audio search first then Metadata & Content search");

		// Login as USER
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullName);

		// Combinational Search
		baseClass.stepInfo("Audio Input : " + audioSearchInput);
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(Input.searchString1, Input.searchString1,
				audioSearchInput, Input.language, "", combinations, metaDataType, metaDataIp, "", false);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		sessionSearch.getQuerySearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(audioSearchInput);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		loginPage.logout();

	}

	/**
	 * @author Raghuram.A date: 02/03/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-46995 Description : Search > Doc View Verify that audio
	 *         hits should be highlighted when search is set up with the Metadata &
	 *         Content search first and then Audio search Sprint 12
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, dataProvider = "AllTheUsers", groups = { "regression" }, priority = 18)
	public void hitsCheckWithMetaDataAudioViaSearch(String username, String password, String fullName)
			throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		String firnstDocname;
		String metaDataType = "DocID", metaDataIp = "ID00*";
		String[] combinations = { "Metadata + Audio" };
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-46995 Sprint 12");
		baseClass.stepInfo(
				"Search > Doc View Verify that audio hits should be highlighted when search is set up with the Metadata & Content search first and then Audio search");

		// Login as USER
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullName);

		// Combinational Search
		baseClass.stepInfo("Audio Input : " + audioSearchInput);
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(Input.searchString1, Input.searchString1,
				audioSearchInput, Input.language, "", combinations, metaDataType, metaDataIp, "", false);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		sessionSearch.getQuerySearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(audioSearchInput);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the player", "Audio hits not highlighted on the player");

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 05/02/22 NA Modified date: NA Modified by:NA
	 * Description:Verify that Reviewer can View reviewers remark in audio doc
	 * view.'RPMXCON-51174' Sprint : 12
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyReviewerRemarksTabInDocViewPageForReviewer() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51174");
		baseClass.stepInfo("Verify that Reviewer can View reviewers remark in audio doc view.");
		baseClass = new BaseClass(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);

		// login Reviewer
		baseClass.stepInfo(
				"Step 1: Login as Reviewer   Project: Enron  Security Group: SG1, Annotation layer should be created under the security group");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Step 2:Search for audio documents and go to doc view  Check for the icon to see the reviewer remarks");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLink());
		sessionSearch.getAdvancedSearchLink().waitAndClick(3);
		sessionSearch.advancedSearchAudio(Input.audioSearch, Input.language);
		baseClass.waitForElement(sessionSearch.getQuerySearchButton());
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigated to DocView Sucessfully");
		softAssertion.assertTrue(docViewPage.getAdvancedSearchAudioRemarkIcon().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("User is on audio doc view and Reviewers Remark icon is displayed successfully");

		baseClass.stepInfo("Step 3: Click the Remarks icon to see Reviewers remark.");
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkIcon());
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (docViewPage.getDocView_AudioReviewerRemarks().isDisplayed()
				&& docViewPage.getAdvancedSearchAudioRemarkPlusIcon().isDisplayed()) {
			baseClass.passedStep(
					"Existing reviewers remark is displayed along with date time.  Also '+' sign is displayed to add the remarks.");
		} else {
			baseClass.failedStep("Reviewer remarks and + sign is not displayed");

		}

		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 05/02/22 NA Modified date: NA Modified by:NA
	 * Description:To verify that Project Admin cannot view reviewer remarks in
	 * audio doc view'RPMXCON-51175' Sprint : 12
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyReviewerRemarksTabInDocViewPageForProjectAdmin() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51175");
		baseClass.stepInfo("To verify that Project Admin cannot view reviewer remarks in audio doc view");
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);

		// Login as PA
		baseClass.stepInfo("Step 1: Login as Project Admin");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");
		UtilityLog.info(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"Step 2:Search for audio documents and go to doc view  Check for the icon to see the reviewer remarks");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLink());
		sessionSearch.getAdvancedSearchLink().waitAndClick(3);
		sessionSearch.advancedSearchAudio(Input.audioSearch, Input.language);
		baseClass.waitForElement(sessionSearch.getQuerySearchButton());
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigated to DocView Sucessfully");

		baseClass.stepInfo("Step 3: View the audio doc view page for Remarks icon");
		driver.waitForPageToBeReady();
		if (docViewPage.getAdvancedSearchAudioRemarkIcon().isDisplayed()) {
			baseClass.failedStep("Reviewer remarks icon is displayed on audio doc view for Project Admin");
		} else {
			baseClass.passedStep("Remarks icon is not displayed on audio doc view for Project Admin");
		}

		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51780 Description : Verify that audio hits should be displayed
	 * when documents searched with same term and different/same threshold from
	 * session search
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifySameDifferentThresholdInSessionSearch() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51780");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with same term and different/same threshold from session search");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// Performing advanced search with audio
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		baseClass.elementDisplayCheck(sessionSearch.getPureHitsCount());

		// add to shop cart
		baseClass.waitForElement(sessionSearch.getPureHitAddBtn());
		sessionSearch.getPureHitAddBtn().waitAndClick(10);
		baseClass.stepInfo("purehit added to shop cart");
		driver.waitForPageToBeReady();

		// perform new search
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString2, Input.language);
		baseClass.elementDisplayCheck(sessionSearch.getPureHitsCount());

		// add to shop cart
		baseClass.waitForElement(sessionSearch.getPureHitAddBtn());
		sessionSearch.getPureHitAddBtn().waitAndClick(10);
		baseClass.stepInfo("purehit added to shop cart");

		// view in docview
		sessionSearch.ViewInDocViewWithoutPureHit();

		// click eye icon and triangular arrow display check
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.stepInfo("Audio eye icon is clicked");
		baseClass.elementDisplayCheck(docViewPage.getAudioPersistantHitEyeIcon());
		baseClass.stepInfo("Audio eye icon is displayed in docview");

		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);

		baseClass.elementDisplayCheck(docViewPage.getTriangularIcon());
		baseClass.stepInfo("Audio triangular arrow is displayed");
		baseClass.visibleCheck(Input.audioSearchString2);
		baseClass.stepInfo("Audio search ,search term is visible in docview");
		driver.waitForPageToBeReady();

		// remove purehit from shop cart
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.Removedocsfromresults();
		baseClass.stepInfo("purehit removed from shop cart");
		driver.waitForPageToBeReady();
		sessionSearch.Removedocsfromresults();
		baseClass.stepInfo("purehit removed from shop cart");

		// add new search with different threshold
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.language, "min");

		// add to shop cart
		baseClass.waitForElement(sessionSearch.getPureHitAddBtn());
		sessionSearch.getPureHitAddBtn().waitAndClick(10);
		driver.waitForPageToBeReady();

		// add new search with different threshold
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.language, "max");

		// add to shop cart
		baseClass.waitForElement(sessionSearch.getPureHitAddBtn());
		sessionSearch.getPureHitAddBtn().waitAndClick(10);
		baseClass.stepInfo("purehit added to shop cart");

		// view in docview
		sessionSearch.ViewInDocViewWithoutPureHit();

		// click eye icon and triangular arrow display check
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.stepInfo("Audio eye icon is clicked");
		baseClass.elementDisplayCheck(docViewPage.getAudioPersistantHitEyeIcon());
		baseClass.stepInfo("Audio eye icon is displayed in docview");

		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);

		baseClass.elementDisplayCheck(docViewPage.getTriangularIcon());
		baseClass.stepInfo("Audio triangular arrow is displayed");
		baseClass.visibleCheck(Input.audioSearchString2);
		baseClass.stepInfo("Audio search ,search term is visible in docview");

		baseClass.passedStep(
				"Verified that audio hits should be displayed when documents searched with same term and different/same threshold from session search");

		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date: 08/02/2022 Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to Save the
	 * document, then waveform should be loaded [Greater than 1 hr audio file] from
	 * coding form child window
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void validatePlayIconInCfChild() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51821");
		baseClass.stepInfo("Verify that when audio file is playing and clicked "
				+ "to Save the document, then waveform should be loaded [Greater than "
				+ "1 hr audio file] from coding form child window");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		String comment = "comments" + Utility.dynamicNameAppender();

		// search to Assignment creation
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		driver.waitForPageToBeReady();
		docViewPage.getDociD(Input.oneHourAudio).waitAndClick(5);
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

		// Edit coding form in parent window
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		baseClass.stepInfo("Document saved successfully");

		String activeId = docViewPage.getDocView_CurrentDocId().getText();
		softAssertion.assertEquals(Input.oneHourAudio, activeId);
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// opening Coding form child and minidcolist child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		baseClass.waitForElement(docViewPage.getDocView_HdrMinDoc());
		docViewPage.getDocView_HdrMinDoc().waitAndClick(5);
		baseClass.stepInfo("Coding form and minidoclist child window opened");

		// switching to coding form child window
		docViewPage.switchToNewWindow(3);
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		baseClass.stepInfo("Document saved successfully");
		docViewPage.closeWindow(2);

		// validating waveform displaying for same document
		docViewPage.switchToNewWindow(1);
		String childactiveId = docViewPage.getDocView_CurrentDocId().getText();
		softAssertion.assertEquals(Input.oneHourAudio, childactiveId);
		boolean childwaveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(childwaveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean childaudioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(childaudioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean childzoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(childzoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 08/02/2022 Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to Save the
	 * document, then waveform should be loaded [Greater than 1 hr audio file]
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void validatePlayIconCF() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51819");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to "
				+ "Save the document, then waveform should be loaded [Greater than 1 hr audio file]");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		String comment = "comments" + Utility.dynamicNameAppender();

		// search to Assignment creation
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		driver.waitForPageToBeReady();
		docViewPage.getDociD(Input.oneHourAudio).waitAndClick(5);
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
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);

		// Edit coding form in parent window
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		baseClass.stepInfo("Document saved successfully");

		String activeId = docViewPage.getDocView_CurrentDocId().getText();
		Assert.assertEquals(Input.oneHourAudio, activeId);
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		Assert.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		Assert.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		baseClass.waitForElement(docViewPage.getAudioDocZoom());
		docViewPage.getAudioDocZoom().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getAudioZoomBar());
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		Assert.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// edit coding form again
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		baseClass.stepInfo("Document saved successfully");

		String childactiveId = docViewPage.getDocView_CurrentDocId().getText();
		Assert.assertEquals(Input.oneHourAudio, childactiveId);
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean childwaveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		Assert.assertTrue(childwaveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		boolean childaudioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		Assert.assertTrue(childaudioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		baseClass.waitForElement(docViewPage.getAudioDocZoom());
		docViewPage.getAudioDocZoom().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getAudioZoomBar());
		boolean childzoomBar = docViewPage.getAudioZoomBar().Displayed();
		Assert.assertTrue(childzoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 05/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that when audio document is selected after scrolling down
	 * the mini doc list then played then browser page should not scroll down
	 * automatically.'RPMXCON-51816' Sprint: 12
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyAudioDocsScrollingMiniDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51816");
		baseClass.stepInfo(
				"Verify that when audio document is selected after scrolling down the mini doc list then played then browser page should not scroll down automatically.");
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Performing advanced search with audio
		baseClass.stepInfo("Advanced audio search");
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);

		// view in docview
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		Long value = (Long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return window.pageYOffset;");
		System.out.println(value);

		// perform Eye icon
		docViewPage.performEyeIconHighLighting();

		// Checking page Scroll
		driver.waitForPageToBeReady();
		Long value1 = (Long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return window.pageYOffset;");
		System.out.println(value1);

		softAssertion.assertEquals(value, value1);
		baseClass.passedStep(" browser page is not scroll down automatically");

		// logout
		loginPage.logout();

		// Login REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User is logged in as Reviewer");

		// Performing advanced search with audio
		baseClass.stepInfo("Advanced audio search");
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);

		// view in docview
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		Long value2 = (Long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return window.pageYOffset;");
		System.out.println(value2);

		// perform Eye icon
		docViewPage.performEyeIconHighLighting();

		// Checking page Scroll
		driver.waitForPageToBeReady();
		Long value3 = (Long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return window.pageYOffset;");
		System.out.println(value3);

		softAssertion.assertEquals(value2, value3);
		baseClass.passedStep(" browser page is not scroll down automatically");

		// logout
		loginPage.logout();

		// Login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User is logged in as Project Assient ");

		// Performing advanced search with audio
		baseClass.stepInfo("Advanced audio search");
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		// view in docview
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		Long value4 = (Long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return window.pageYOffset;");
		System.out.println(value4);

		// perform Eye icon
		docViewPage.performEyeIconHighLighting();

		// Checking page Scroll
		driver.waitForPageToBeReady();
		Long value5 = (Long) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return window.pageYOffset;");
		System.out.println(value5);

		softAssertion.assertEquals(value4, value5);
		baseClass.passedStep(" browser page is not scroll down automatically");

		// perform Eye icon
		docViewPage.performEyeIconHighLighting();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Doc View->If a audio document is added to an assignment with
	 *              certain persistent search terms, those shouldn’t be removed
	 *              unless the document is removed from the assignment
	 *              [RPMXCON-51857]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyPersistentForAudio() throws InterruptedException {
		String assignmentName = "Assign" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearch.toUpperCase();
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		assignmentPage = new AssignmentsPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case id :RPMXCON-51857");
		baseClass.stepInfo(
				"Doc View->If a audio document is added to an assignment with certain persistent search terms, those shouldn’t be removed unless the document is removed from the assignment");
		System.out.println(audioSearch);

		// audio search 1
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkAssign_Persistant(true);
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);

		// audio search 2
		baseClass.selectproject();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkAssignExisting(assignmentName);

		// persistent
		assignmentPage.selectAssignmentToViewinDocview(assignmentName);
		String hitscount = docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");

		loginPage.logout();
	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by:NA TestCase
	 * id :RPMXCON- 51858 Description:Audio only - If user modified Search Term and
	 * re-execute the same query then modified Term does not appear in Persistent
	 * Search panel in DocView screen.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 26)
	public void verifyModifiedStringInPersistPanel() throws Exception {

		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("RPMXCON-51858 from docview/Audio Component");
		baseClass.stepInfo(
				"#### Audio only - If user modified Search Term and re-execute the same query then modified Term does not appear in Persistent Search panel in DocView screen. ####");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		baseClass.stepInfo("Advanced audio search");
		sessionSearch.audioSearch(Input.audioSearch, Input.language);

		baseClass.stepInfo("Navigate to docview page");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Verifying the Persistent panel search string and Visiblity");
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearch);

		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Modifying the search string");
		sessionSearch.modifyAdvanceSearch("Yes", Input.audioSearchString1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getRemoveAddBtn());
		sessionSearch.getRemoveAddBtn().waitAndClick(20);
		baseClass.waitForElement(sessionSearch.getPureHitAddBtn());
		sessionSearch.getPureHitAddBtn().waitAndClick(10);
		sessionSearch.getBulkActionButton().waitAndClick(5);
		baseClass.waitTime(3); // added for stabilization
		sessionSearch.getDocViewAction().waitAndClick(10);
		baseClass.stepInfo("Navigate to docview page");
		baseClass.stepInfo("Verifying the Persistent panel search string and Visiblity");
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);

		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51781 Description : Verify that audio hits should be displayed
	 * when documents searched with common terms and different/same threshold from
	 * session search
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifySameDifferentThresholdAndSearchTerms() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case id :RPMXCON-51781");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with same term and different/same threshold from session search");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// Performing advanced search with audio
		int count1 = sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		baseClass.elementDisplayCheck(sessionSearch.getCurrentPureHitAddBtn());
		baseClass.stepInfo("Search result is displayed");

		// add to shop cart
		sessionSearch.addPureHit();

		// perform new search
		sessionSearch.addNewSearch();
		int count2 = sessionSearch.newAudioSearch(Input.audioSearchString2, Input.audioSearchString3,
				Input.audioSearchString4, Input.language);

		// add to shop cart
		sessionSearch.addPureHit();

		// view in docview
		sessionSearch.ViewInDocViewWithoutPureHit();
		int sum = count1 + count2;
		// click eye icon and triangular arrow display check
		docViewPage.trianglurArrowIconPositionVerification();
		docViewPage.termDisplayCheck(Input.audioSearchString2, Input.audioSearchString3, sum);

		// remove purehit from shop cart
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.Removedocsfromresults();
		driver.waitForPageToBeReady();
		sessionSearch.Removedocsfromresults();

		// add new search with different threshold
		sessionSearch.addNewSearch();
		int count3 = sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.language, "max");
		sessionSearch.addPureHit();

		// add new search with different threshold
		sessionSearch.addNewSearch();
		int count4 = sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.audioSearchString3,
				Input.audioSearchString4, Input.language, "min");
		sessionSearch.addPureHit();

		// view in docview
		sessionSearch.ViewInDocViewWithoutPureHit();

		int sum1 = count3 + count4;
		// click eye icon and triangular arrow display check
		docViewPage.trianglurArrowIconPositionVerification();
		docViewPage.termDisplayCheck(Input.audioSearchString2, Input.audioSearchString3, sum1);

		// remove purehit from shop cart
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.Removedocsfromresults();
		driver.waitForPageToBeReady();
		sessionSearch.Removedocsfromresults();

		sessionSearch.addNewSearch();
		int searchCount1 = sessionSearch.newAudioSearchThreshold(Input.audioSearchString5, Input.audioSearchString6,
				Input.audioSearchString7, Input.language, "80");
		sessionSearch.addPureHit();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString6, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString7, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.addNewSearch();
		int searchCount4 = sessionSearch.newAudioSearch(Input.audioSearchString5, Input.audioSearchString6,
				Input.audioSearchString7, Input.language);
		sessionSearch.addPureHit();

		driver.waitForPageToBeReady();
		boolean flag = searchCount1 < searchCount4;
		if (searchCount1 < searchCount4) {
			softAssertion.assertTrue(flag);
			baseClass.passedStep("1st Search result count is less than the 4th search result count");
			System.out.println("passed");
		} else {
			baseClass.failedStep("verification failed");
			System.out.println("failed");
		}
		driver.scrollPageToTop();
		// view in docview
		sessionSearch.ViewInDocViewWithoutPureHit();

		baseClass.passedStep(
				"Verified that audio hits should be displayed when documents searched with common terms and different/same threshold from session search");

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 09/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify that when last audio document is selected and played then
	 *                     browser page should not scroll down automatically
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 28)
	public void validatePullButtonAndWebPage(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51817");
		baseClass.stepInfo("Verify that when last audio document "
				+ "is selected and played then browser page should not scroll down automatically");

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// login as
		loginPage.loginToSightLine(userName, password);

		// session seach to docview
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		// clicking eye icon and validating
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.waitForElement(docViewPage.getDocView_Audio_Hit());
		String audioEyePersistent = docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(audioEyePersistent.toLowerCase().contains(Input.audioSearchString1));

		// navigation to last docs using >>
		docViewPage.verifyThatIsLastDoc();

		// validaing by using persistent hit pull button
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);

		// validating when clicking the pull button audio file is playing
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);

		// verifying webpage page should not scroll automatically
		Long notToBeScroll = (Long) ((JavascriptExecutor) driver.getWebDriver())
				.executeScript("return window.pageYOffset;");
		softAssertion.assertEquals(Long.toString(notToBeScroll), "0");
		baseClass.passedStep("Webpage not scrolled automatically when previous action done");

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 09/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify that when last audio document is selected and played then
	 *                     mini doc list should not scroll up automatically
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 29)
	public void validatePlayAudioWebPageNotScrollUp(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51815");
		baseClass.stepInfo("Verify that when last audio document is selected and "
				+ "played then mini doc list should not scroll up automatically");

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// login as
		loginPage.loginToSightLine(userName, password);

		// session seach to docview
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		// clicking eye icon and validating
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.waitForElement(docViewPage.getDocView_Audio_Hit());
		String audioEyePersistent = docViewPage.getDocView_Audio_Hit().getText().toString();
		softAssertion.assertTrue(audioEyePersistent.toLowerCase().contains(Input.audioSearchString1));

		// navigation to last docs using >>
		docViewPage.verifyThatIsLastDoc();

		// validaing by using play audio button
		driver.waitForPageToBeReady();
		Long lastScrollEnd = (Long) ((JavascriptExecutor) driver.getWebDriver())
				.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(5);

		// validating when clicking the pull button audio file is playing
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);

		// verifying minidoclist page should not scroll up automatically
		Long afterAction = (Long) ((JavascriptExecutor) driver.getWebDriver())
				.executeScript("return document.querySelector('.dataTables_scrollBody').scrollTop;");
		softAssertion.assertEquals(Long.toString(lastScrollEnd), Long.toString(afterAction));
		baseClass.passedStep("Minidoclist not scrolled automatically when previous action done");

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 * @Description:Verify that previously saved Persistent hits should be displayed
	 *                     on the audio doc view when same/different reviewer is
	 *                     unassigned from assignment and documents are distributed
	 *                     again
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyPersistentHits() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51774");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits should be displayed on the audio doc view when same/different reviewer "
						+ "is unassigned from assignment and documents are distributed again");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		String assign = "Assignment" + Utility.dynamicNameAppender();

		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		// login as RMU //Step 1 and step-2
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// creating Assignment with Audio Document
		sessionSearch.bulkAssign_Persistant(true);
		assignmentPage.FinalizeAssignmentAfterBulkAssign();
		assignmentPage.createAssignmentByBulkAssignOperation(assign, Input.codeFormName);
		// Assigning the Assignment to Reviewer
		assignmentPage.editAssignment(assign);
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo(assign + " Assignment is Successfully assigned to the " + Input.rev1userName);

		// Verification of Persistent Hits Step-3
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		// Unassigning the Reviewer Step-4
		assignmentPage.editAssignment(assign);
		assignmentPage.UnassignedUser(Input.rev1userName);

		// again assign the same Reviewer Step-5
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo(assign + " Assignment is Successfully assigned to the " + Input.rev1userName);
		loginPage.logout();

		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer '" + Input.rmu1userName + "'");

		// navigating from Dashboard to DocView
		baseClass.waitForElement(assignmentPage.dashBoardPageTitle());
		assignmentPage.assgnInDashBoardPg(assign).waitAndClick(10);

		// verifying the Persistent Hits Step-6
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "AllTheUsers", groups = { "regression" }, priority = 31)
	public void verifyPersistentHits_sameTerm(String username, String password, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-48788");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with same term and different/same "
						+ "threshold navigated to doc view from session search > DocList");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Successfully login as '" + fullName);

		String searchString = Input.audioSearchString2;

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docListPage = new DocListPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		// First audio Search
		sessionSearch.audioSearch(searchString, Input.language);
		sessionSearch.getPureHitAddButton().waitAndClick(10);

		// second audio search
		sessionSearch.clickOnNewSearch();
		sessionSearch.audioSearchTwoTimesandAddingTwoPureHits(searchString, Input.language);
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// view All audio Docs in DocList
		sessionSearch.ViewInDocListWithOutPureHit();

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();

		// view selected documents in DocList
		docListPage.viewSelectedDocumentsInDocView();
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with same term and "
				+ "same threshold navigated to doc view from session search > DocList");
		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString);

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// First audio search term with max threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString, Input.language, "max");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// second audio search with same term and min threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString, Input.language, "min");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// view All audio Docs in DocList
		sessionSearch.ViewInDocList();

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();

		baseClass.stepInfo("Verifying audio hits  displayed when documents searched with same term and different"
				+ "threshold navigated to doc view from session search > DocList");
		// view selected documents in DocList
		docListPage.viewSelectedDocumentsInDocView();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString);
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 09/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when loading is displayed in mini doc list after audio
	 * doc-to-doc navigation then persistent hits should be
	 * displayed.'RPMXCON-51866' Sprint: 12
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifyLoadingDisplayInMiniDocListPersistentHit() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51866");
		baseClass.stepInfo(
				"Verify when loading is displayed in mini doc list after audio doc-to-doc navigation then persistent hits should be displayed.");
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Performing advanced search with audio
		baseClass.stepInfo("Advanced audio search");
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		// view in docview
		sessionSearch.ViewInDocView();
		// loading display
		driver.waitForPageToBeReady();
		docViewPage.scrollingDocumentInMiniDocList();

		// doc to doc navigate after persistent hit display
		docViewPage.performEyeIconHighLightingNavDocToDoc();
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 09/02/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that after scrolling the mini doc list, when ‘Loading’
	 * is displayed then document list should be loaded immediately..'RPMXCON-51812'
	 * Sprint: 12
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyLoadingDisplayInMiniDocListImmediately() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51812");
		baseClass.stepInfo(
				"To verify that after scrolling the mini doc list, when ‘Loading’ is displayed then document list should be loaded immediately.");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Savebtn" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");

		// Performing advanced search with audio
		baseClass.stepInfo("Advanced audio search");
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		// view in docview
		sessionSearch.ViewInDocView();

		// loading display
		driver.waitForPageToBeReady();
		docViewPage.scrollingDocumentInMiniDocList();

		// Basic Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.savedSearchToDocView(BasicSearchName);

		// loading display
		driver.waitForPageToBeReady();
		docViewPage.scrollingDocumentInMiniDocList();
		baseClass.passedStep("MiniDocList document list is successfully loaded immediately");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: 02/03/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-52009 Description : Verify that when applies audio
	 *         redaction for the first time then application should automatically
	 *         select the ‘Default Redaction Tag’ Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void audioRedactionDefaultTagSelection() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		String headerName = "RedactionTags";
		int index;

		List<String> docIDlist = new ArrayList<>();
		String firnstDocname;
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-52009 Sprint 12");
		baseClass.stepInfo(
				"Verify that when applies audio redaction for the first time then application should automatically select the ‘Default Redaction Tag’");

		// Login as USER
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		sessionSearch.audioSearch(audioSearchInput, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : ‘Default Redaction Tag’ is displayed", "After Save : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Search > Doc list > Bulk assign > New/existing assignment when
	 *              search is set up with the Audio search first then Metadata &
	 *              Content search, hits should be highlighted [RPMXCON-46999]
	 * @throws InterruptedException
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyBulkAssignFromDoclist() throws InterruptedException {
		baseClass = new BaseClass(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		docListPage = new DocListPage(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		String[] combinations = { "Audio + Metadata" };
		String metaDataIp = "ID0*";
		List<String> docIDlist = new ArrayList<>();

		baseClass.stepInfo("Test case id :RPMXCON-46999");
		baseClass.stepInfo(
				"Search > Doc list > Bulk assign > New/existing assignment when search is set up with the Audio search first then Metadata & Content search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search for audio + Metadata
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(null, null, Input.audioSearch, Input.language,
				"", combinations, Input.docName, metaDataIp, "min", true);

		driver.scrollPageToTop();
		sessionSearch.ViewInDocList();
		docListPage.DoclisttobulkAssign(null, "100");
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
		sessionSearch.ViewInDocList();
		docListPage.DoclisttobulkAssign(null, "100");
		docListPage.bulkAssignWithPersistantHit(assignmentName);

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
	 * @Description : Search > Doc list > Bulk assign > New/existing assignment when
	 *              search with Metadata & Content search first and then Audio
	 *              search, hits should be highlighted [RPMXCON-46998]
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void verifyBulkAssignWithAudioAndMetadata() throws InterruptedException {
		baseClass = new BaseClass(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		docListPage = new DocListPage(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String assignmentName = "TestAssignmentNo" + Utility.dynamicNameAppender();
		String[] combinations = { "Metadata + Audio" };
		String metaDataIp = "ID0*";
		List<String> docIDlist = new ArrayList<>();

		baseClass.stepInfo("Test case id :RPMXCON-46998");
		baseClass.stepInfo(
				"Search > Doc list > Bulk assign > New/existing assignment when search with Metadata & Content search first and then Audio search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search for metadata and audio
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(null, null, Input.audioSearch, Input.language,
				"", combinations, Input.docName, metaDataIp, "min", true);

		driver.scrollPageToTop();
		sessionSearch.ViewInDocList();
		docListPage.DoclisttobulkAssign(null, "100");
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
		sessionSearch.ViewInDocList();
		docListPage.DoclisttobulkAssign(null, "100");
		docListPage.getSelectAssignmentExisting(assignmentName).Click();
		docListPage.getPersistantHitCheckBox().isElementAvailable(15);
		docListPage.getPersistantHitCheckBox().Click();
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docListPage.getContinueButton());
		docListPage.getContinueButton().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docListPage.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait30);
		docListPage.getFinalizeButton().isElementAvailable(15);
		docListPage.getFinalizeButton().Click();

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
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyPersistentHits_editAndComplete() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51772");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits displayed on the audio doc view when documents assigned to"
						+ " same/another reviewer are completed from edit assignment.");
		// Login as Reviewer Manager step 1 and Step 2
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String savedSearch = "Search1" + Utility.dynamicNameAppender();

		// creating new node
		String nodeName = this.savedSearch.createSearchGroupAndReturn(savedSearch, Input.rmu1userName, "yes");

		// configuring and saving the audio search in credated node
		int pureHit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.saveAdvanceSearchInNode(savedSearch, nodeName);

		// selecting the saved search group step-3
		this.savedSearch.navigateToSSPage();
		this.savedSearch.selectNode1(nodeName);
		this.savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		// creating assignment step-4
		assignmentPage.FinalizeAssignmentAfterBulkAssign();
		assignmentPage.createAssignmentByBulkAssignOperation(assign, Input.codeFormName);

		// adding Reviewer and distributing the documents to reviewer step-5
		assignmentPage.editAssignment(assign);
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logedout as  Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// navigating from Dashboard to DocView step-6
		baseClass.waitForElement(assignmentPage.dashBoardPageTitle());
		assignmentPage.assgnInDashBoardPg(assign).waitAndClick(10);

		// verifying the Persistent Hits Step-7
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as Reviewer Manager'" + Input.rmu1userName + "'");

		// completing the Documents in the assignement step-8
		assignmentPage.editAssignment(assign);
		assignmentPage.completeDocs(Input.rev1userName);

		// verifying the Persistent Hits as RMU Step-9
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		baseClass.stepInfo("Previously saved Persistent hits  displayed on "
				+ "the doc view when documents are completed from edit assignment.   ");
		// logout
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: 02/03/22 Modified date: NA Modified by: NA Test Case
	 *         Id:RPMXCON-46922 Description : Verify when audio document present in
	 *         two different save searches with common term, then on navigating to
	 *         doc view with selection of search group it should not display
	 *         repetitive search term on persistent hits panel Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void audioMulti() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		String commonDocName = "";
		Set<String> hash_Set = new HashSet<String>();
		List<String> docIDlist = new ArrayList<>();
		List<String> docIDlistT = new ArrayList<>();
		String audioSearchInput = Input.audioSearch;
		String audioSearchInput1 = Input.audioSearchString1;
		String[] stringDatas = { audioSearchInput, audioSearchInput1 };
		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String SearchName1 = "SearchName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case id :RPMXCON-46922 Sprint 12");
		baseClass.stepInfo(
				"Verify when audio document present in two different save searches with common term, then on navigating to doc view with selection of search group it should not display repetitive search term on persistent hits panel");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Perform create node - Search - SaveSearch in nodes
		String nodeName = saveSearch.createSearchGroupAndReturn("", Input.rmu1FullName, "No");

		// Audio Search
		sessionSearch.audioSearch(audioSearchInput, Input.language);
		sessionSearch.saveSearchInNewNode(SearchName, nodeName);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		hash_Set = baseClass.addListIntoSet(docIDlist);
		baseClass.selectproject();

		// Audio Search
		sessionSearch.audioSearch(audioSearchInput1, Input.language);
		sessionSearch.saveSearchInNewNode(SearchName1, nodeName);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();

		// Main method
		docIDlistT = miniDocListpage.getDocListDatas();
		baseClass.stepInfo("Select a document present in both the searches ");
		commonDocName = docViewPage.pickFirstDuplicate(docIDlistT, hash_Set);

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeName);
		driver.waitForPageToBeReady();
		saveSearch.getToDocView().waitAndClick(5);

		// Persistant data to check
		miniDocListpage.getDocListDatas();
		miniDocListpage.getDociD(commonDocName).waitAndClick(10);
		driver.waitForPageToBeReady();
		String docID = docViewPage.getDocView_CurrentDocId().getText();
		baseClass.stepInfo("Current viewed document : " + docID);
		baseClass.textCompareEquals(commonDocName, docID,
				"User on audio doc view and selected document is same as on audio doc view", "Audio DocView failed");
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		driver.waitForPageToBeReady();

		// verifySearchTermlist
		docViewPage.verifySearchTermlist(stringDatas, "equalsP", 1,
				"earch term not been displayed repetitively on persistent hit panel",
				"search term displayed repetitively on persistent hit panel");

		// Delete created Node
		baseClass.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeName);

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyPersistentHits_unCompleteDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46921");
		baseClass.stepInfo("Verify that previously saved Persistent hits should be displayed on the audio doc view "
				+ "when documents are uncompleted from edit assignment. ");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String savedSearch = "Search1" + Utility.dynamicNameAppender();

		// creating new node
		String nodeName = this.savedSearch.createSearchGroupAndReturn(savedSearch, Input.rmu1userName, "yes");

		// configuring and saving the audio search in credated node
		int pureHit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.saveAdvanceSearchInNode(savedSearch, nodeName);

		// selecting the saved search group
		this.savedSearch.navigateToSSPage();
		this.savedSearch.selectNode1(nodeName);
		this.savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		// creating assignment
		assignmentPage.FinalizeAssignmentAfterBulkAssign();
		assignmentPage.createAssignmentByBulkAssignOperation(assign, Input.codeFormName);

		// adding Reviewer and distributing the documents to reviewer
		assignmentPage.editAssignment(assign);
		assignmentPage.add2ReviewerAndDistribute();

		// completing the Documents in the assignement
		assignmentPage.editAssignmentUsingPaginationConcept(assign);
		assignmentPage.completeDocs(Input.rev1userName);

		// verifying the Persistent Hits as RMU
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		baseClass.stepInfo("Previously saved Persistent hits  displayed on "
				+ "the doc view when documents are completed from edit assignment.");

		// Un-completing the Documents in the assignement
		assignmentPage.editAssignmentUsingPaginationConcept(assign);
		assignmentPage.UnCompleteDocs(Input.rmu1userName);

		//// verifying the Persistent Hits as RMU
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);

		assignmentPage.deleteAssgnmntUsingPagination(assign);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void verifyPersistentHits_RemoveAudioDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51769");
		baseClass.stepInfo(
				"Verify When remove audio docs from a user/reviewer in an assignment, displays persistent search hits in the assignment, when reassigning these audio docs to another/same reviewer in the assignment");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		String assign = "Assignment" + Utility.dynamicNameAppender();
		String savedSearch = "Search1" + Utility.dynamicNameAppender();

		// creating new node
		String nodeName = this.savedSearch.createSearchGroupAndReturn(savedSearch, Input.rmu1userName, "yes");

		// configuring and saving the audio search in credated node
		int pureHit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.saveAdvanceSearchInNode(savedSearch, nodeName);

		// selecting the saved search group
		this.savedSearch.navigateToSSPage();
		this.savedSearch.selectNode1(nodeName);
		this.savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		// creating assignment
		assignmentPage.FinalizeAssignmentAfterBulkAssign();
		assignmentPage.createAssignmentByBulkAssignOperation(assign, Input.codeFormName);

		// adding Reviewer and distributing the documents to reviewer
		assignmentPage.editAssignment(assign);
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logedout as  Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// navigating from Dashboard to DocView
		baseClass.waitForElement(assignmentPage.dashBoardPageTitle());
		assignmentPage.assgnInDashBoardPg(assign).waitAndClick(10);

		// verifying the Persistent Hits
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as Reviewer Manager'" + Input.rmu1userName + "'");

		// removing and reassigning the Documents in the assignement
		assignmentPage.editAssignment(assign);
		assignmentPage.removeDocs(Input.rev1userName);
		assignmentPage.reassignDocs(Input.rev1userName);

		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// navigating from Dashboard to DocView
		baseClass.waitForElement(assignmentPage.dashBoardPageTitle());
		assignmentPage.assgnInDashBoardPg(assign).waitAndClick(10);

		// verifying the Persistent Hits
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);

		// logout
		loginPage.logout();
	}

	@DataProvider(name = "userDetailsAndRole")
	public Object[][] userLoginDetailsAndRole() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password, "REV" } };
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param fullName
	 * @param userName
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "userDetailsAndRole", groups = { "regression" }, priority = 41)
	public void verifyPersistentHit_DifferentSearch(String fullName, String userName, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51848");
		baseClass.stepInfo(
				"Verify that when document present in different searches with term then, should display search term on persistent hits panel on"
						+ " document navigation options [<<, <, >, >>, enter document number]");

		// Login as PA/RMU/REV
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as " + role + " " + fullName + "'");

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docListPage = new DocListPage(driver);

		if (role.equals("PA")) {
			audioSearchTerms.add(searchString1);
			audioSearchTerms.add(searchString2);
			// creating new node as PA
			nodeName = savedSearch.createSearchGroupAndReturn(Input.shareSearchDefaultSG, "PA", Input.yesButton);

			// configuring and saving the audio search term 1 in credated node
			sessionSearch.audioSearch(searchString1, Input.language);
			sessionSearch.saveSearchInNodeUnderGroup(savedSearch1, nodeName, Input.shareSearchDefaultSG);
			driver.scrollPageToTop();
			sessionSearch.ViewInDocList();
			List<String> DocIdsWithSearchString1 = docListPage.gettingAllDocIDs();

			// configuring and saving the audio search term 2 in credated node
			baseClass.selectproject();
			sessionSearch.audioSearch(searchString2, Input.language);
			sessionSearch.saveSearchInNodeUnderGroup(savedSearch2, nodeName, Input.shareSearchDefaultSG);
			driver.scrollPageToTop();
			sessionSearch.ViewInDocList();
			List<String> DocIdsWithSearchString2 = docListPage.gettingAllDocIDs();

			// collecting the DocId common to both audio searchString1
			baseClass.selectproject();
			sessionSearch.audioSearchWithOperator(searchString1, searchString2, Input.language, 0, "ALL");
			sessionSearch.ViewInDocList();
			DocIdsWithBothSearchTerm = docListPage.gettingAllDocIDs();

			// collecting the DocId having only searchString1
			UniqueSearchString1 = baseClass.getUniqueValues_FromTwoList(DocIdsWithBothSearchTerm,
					DocIdsWithSearchString1);

			// collecting the DocId having only searchString2
			UniqueSearchString2 = baseClass.getUniqueValues_FromTwoList(DocIdsWithBothSearchTerm,
					DocIdsWithSearchString2);
		}
		// viewing the Documents in DocView from SavedSearch page
		savedSearch.selectNodeUnderSpecificSearchGroup(Input.securityGroup, nodeName);
		driver.scrollPageToTop();
		savedSearch.getToDocView().waitAndClick(5);

		// verifying the AudioPersistantHitPanel for document having only searchString1
		baseClass.stepInfo(
				"verifying the AudioPersistantHitPanel for document having audio search term '" + searchString1 + "'");
		docViewPage.getDociD(UniqueSearchString1.get(1)).waitAndClick(10);
		String DocIDSearchString1 = docViewPage.getSelectedDocIdMiniDocList().getText();
		softAssertion.assertEquals(DocIDSearchString1, UniqueSearchString1.get(1));
		baseClass.stepInfo(
				"Document with DocID '" + DocIDSearchString1 + "' is selected in MiniDocList panel in DocView Page");
		docViewPage.verifyingAudioPersistantHitPanel(searchString1);

		// verifying the AudioPersistantHitPanel for document having only searchString2
		driver.Navigate().refresh();
		baseClass.stepInfo(
				"verifying the AudioPersistantHitPanel for document having audio search term '" + searchString2 + "'");
		docViewPage.getDociD(UniqueSearchString2.get(1)).waitAndClick(10);
		String DocIDSearchString2 = docViewPage.getSelectedDocIdMiniDocList().getText();
		softAssertion.assertEquals(DocIDSearchString2, UniqueSearchString2.get(1));
		baseClass.stepInfo(
				"Document with DocID '" + DocIDSearchString2 + "' is selected in MiniDocList panel in DocView Page");
		docViewPage.verifyingAudioPersistantHitPanel(searchString2);

		// verifying the AudioPersistantHitPanel for document having both audio
		// searchString
		driver.Navigate().refresh();
		baseClass.stepInfo("verifying the AudioPersistantHitPanel for document having both audio search term '"
				+ searchString1 + "' and '" + searchString2 + "'");
		docViewPage.getDociD(DocIdsWithBothSearchTerm.get(1)).waitAndClick(10);
		String DocIdsWithBothSearchTerm1 = docViewPage.getSelectedDocIdMiniDocList().getText();
		softAssertion.assertEquals(DocIdsWithBothSearchTerm1, DocIdsWithBothSearchTerm.get(1));
		baseClass.stepInfo("Document with DocID '" + DocIdsWithBothSearchTerm1
				+ "' is selected in MiniDocList panel in DocView Page");
		docViewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(audioSearchTerms);

		// verifying the AudioPersistantHitPanel for random document selected using the
		// NumTextBox
		driver.Navigate().refresh();
		baseClass.stepInfo("verifying the AudioPersistantHitPanel for random document selected using the NumTextBox");
		docViewPage.searchDocumentBasedOnId(5);
		String DocId1 = docViewPage.getSelectedDocIdMiniDocList().getText();
		baseClass.stepInfo("Document with DocID '" + DocId1 + "' is selected in MiniDocList panel in DocView Page");
		List<String> searchTerm1 = docViewPage.selectingTheAudioSearchTermBasedOnDocId(UniqueSearchString1,
				UniqueSearchString2, DocIdsWithBothSearchTerm, searchString1, searchString2, DocId1);
		docViewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm1);

		// verifying the AudioPersistantHitPanel for random document selected using the
		// navigation option
		driver.Navigate().refresh();
		baseClass.stepInfo(
				"verifying the AudioPersistantHitPanel for random document selected by using the navigation Arrow symbol");
		docViewPage.performNextNavigation(2);
		String DocId2 = docViewPage.getSelectedDocIdMiniDocList().getText();
		baseClass.stepInfo("Document with DocID '" + DocId2 + "' is selected in MiniDocList panel in DocView Page");
		List<String> searchTerm2 = docViewPage.selectingTheAudioSearchTermBasedOnDocId(UniqueSearchString1,
				UniqueSearchString2, DocIdsWithBothSearchTerm, searchString1, searchString2, DocId1);
		docViewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm2);

		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @Author date: 08/02/2022 Modified date: NA Modified by:
	 * @Description:Verify that when document is viewed from history drop down
	 *                     having multiple terms when presently viewed document is
	 *                     with single term then persistent hits panel should
	 *                     display all the hits present in the document.
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyMultipleTermsPresentlyViewedDocInPersistentHits() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51870");
		baseClass.stepInfo(
				"Verify that when document is viewed from history drop down having multiple terms when presently viewed document is with single term then persistent hits panel should display all the hits present in the document.");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewPage = new DocViewPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		String audioSearchString2 = "left";
		sessionsearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		driver.waitForPageToBeReady();
		sessionsearch.modifyAudioSearch(audioSearchString2, Input.language, null);
		docViewPage.selectPureHit();
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Searching multiterms in audioDoc view in docview");
		// click PersistentHits icon on select Docs
		docViewRedact.checkingPersistentHitPanelAudio();
		miniDocList.verifyViewDocInPersistentHitPanel(Input.audioSearchString1, audioSearchString2);
		miniDocList.verifySelectedDocsInClockIcon(audioSearchString2, Input.audioSearchString1);
	}

	/**
	 * @author Vijaya.Rani date: 17/03/22 Modified date: NA Modified by: NA Test
	 *         Case Description : Verify the automatically selected audio redaction
	 *         tag when shared annotation layer with un-shared redactation tags in
	 *         security groups and all documents are released to security groups.
	 *         'RPMXCON-52022' Sprint 14
	 * @throws Exception
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void audioRedactionTagAnnotationLayerSecurityGroups() throws Exception {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		RedactionPage redact = new RedactionPage(driver);
		userManagement = new UserManagement(driver);
		String headerName = "RedactionTags";
		int index;
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();
		String addName = "test" + Utility.dynamicNameAppender();
		String RedactName = "new" + Utility.dynamicNameAppender();

		List<String> docIDlist = new ArrayList<>();
		String firnstDocname;
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-52022 Sprint 14");
		baseClass.stepInfo(
				"Verify the automatically selected audio redaction tag when shared annotation layer with un-shared redactation tags in security groups and all documents are released to security groups.");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.bulkRelease(securityGroup);

		annotationLayer.AddAnnotation(addName);
		redact.AddRedaction(RedactName, Input.rev1FullName);
		loginPage.logout();

		// Login as USER
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as : " + Input.rev1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : ‘Default Redaction Tag’ is displayed", "After Save : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();

		// Login as USER
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as : " + Input.rev1FullName);

		// Audio Search
		sessionSearch.audioSearch(audioSearchInput, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection1 = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection1, Input.defaultRedactionTag,
				"After Save : ‘Default Redaction Tag’ is displayed", "After Save : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani date: 17/03/22 Modified date: NA Modified by: NA Test
	 *         Case Description :Verify the automatically selected audio redaction
	 *         tag when shared annotation layer with shared redactation tags in
	 *         security groups and all propogated documents are not released to
	 *         security groups. 'RPMXCON-52023' Sprint 14
	 * @throws Exception
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void verifySelectedAudioRedactionTagAnnotationlayerPropogatededDocs() throws Exception {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		RedactionPage redact = new RedactionPage(driver);
		userManagement = new UserManagement(driver);
		String headerName = "RedactionTags";
		int index;
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();
		String addName = "test" + Utility.dynamicNameAppender();
		String RedactName = "new" + Utility.dynamicNameAppender();

		List<String> docIDlist = new ArrayList<>();
		String firnstDocname;
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-52023 Sprint 14");
		baseClass.stepInfo(
				"Verify the automatically selected audio redaction tag when shared annotation layer with shared redactation tags in security groups and all propogated documents are not released to security groups");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.bulkRelease(securityGroup);

		redact.AddRedaction(RedactName, Input.rev1FullName);
		annotationLayer.AddAnnotation(addName);
		
		loginPage.logout();

		// Login as USER
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as : " + Input.rev1FullName);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : ‘Default Redaction Tag’ is displayed", "After Save : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();

		// Login as USER
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as : " + Input.rev1FullName);

		// Audio Search
		sessionSearch.audioSearch(audioSearchInput, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection1 = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection1, Input.defaultRedactionTag,
				"After Save : ‘Default Redaction Tag’ is displayed", "After Save : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: 22/03/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify the reviewer apply 'Code same as last' action then
	 *              request should be complete immediately
	 */

	@Test(description = "RPMXCON-46924", enabled = true, groups = { "regression" }, priority = 45)
	public void validatingCodeSameAsLastForAudioDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46924");
		baseClass.stepInfo("Verify the reviewer apply 'Code same as last' action then request "
				+ "should be complete immediately");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Code same as
		docViewPage.clickCheckBoxDocListActionCodeSameAs(2);
		// editing coding form using complete action
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		String docviewSec = docViewPage.getClickDocviewID(2).getText();
		String activeDocId = docViewPage.getDocId().getText();
		softAssertion.assertEquals(docviewSec, activeDocId);
		baseClass.stepInfo("Next docId displayed in docview panel as active docId");
		for (int i = 1; i <= 2; i++) {
			docViewPage.getClickDocviewID(i).waitAndClick(5);
			driver.waitForPageToBeReady();
			boolean unCompleteBtn = docViewPage.getUnCompleteButton().Displayed();
			System.out.println(unCompleteBtn);
			softAssertion.assertTrue(unCompleteBtn);
		}
		baseClass.stepInfo("Document completed successfully");
		docViewPage.getClickDocviewID(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.clickCodeSameAsLast();
		docViewPage.getClickDocviewID(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		// validating code same as last as per the previous completed docs
		docViewPage.verifyComments(comment);
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/03/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify the RMU/Reviewer, apply the 'Coding Stamp' action on
	 *              Audio document, 'Apply Stamp' request should be complete
	 *              immediately
	 */

	@Test(description = "RPMXCON-46925", enabled = true, groups = { "regression" }, priority = 46)
	public void validatingCodingStampForAudioDocs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46925");
		baseClass.stepInfo("Verify the RMU/Reviewer, apply the 'Coding Stamp' action on Audio document, "
				+ "'Apply Stamp' request should be complete immediately");
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "stamp" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// editing coding form using stamp
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(stampName, Input.stampSelection);
		long start = System.currentTimeMillis();
		docViewPage.lastAppliedStamp(Input.stampSelection);
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
		System.out.println(timeSeconds);
		// verifying stamp request should complete immediately
		if (timeSeconds <= 2) {
			baseClass.passedStep("Document completed immediately while request from stamp to complete docs");
		} else {
			baseClass.failedStep("Document taken much time to complete docs using stamp");
		}
		String docviewSec = docViewPage.getClickDocviewID(2).getText();
		String activeDocId = docViewPage.getDocId().getText();
		softAssertion.assertEquals(docviewSec, activeDocId);
		baseClass.stepInfo("Document navigated to next docs from minidoclist");
		baseClass.passedStep("Next docs from minidoclist :" + docviewSec + "displayed as active docID");
		docViewPage.getDociD(prnDoc).waitAndClick(5);
		driver.waitForPageToBeReady();
		// validating coding stamp as per saved colour
		docViewPage.verifyComments(comment);
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/03/2021 Modified date: NA Modified by: Baskar
	 * @Description : Search > Doc List > Doc view when search with Metadata &
	 *              Content search first and then Audio search, hits should be
	 *              highlighted
	 */

	@Test(description = "RPMXCON-47000", enabled = true, groups = { "regression" }, priority = 47)
	public void verifyDocListToDocViewFirstMetaData() throws InterruptedException {
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		docListPage = new DocListPage(driver);
		softAssertion = new SoftAssert();

		String[] combinations = { "Audio + Metadata" };
		String metaDataIp = "ID0*";

		baseClass.stepInfo("Test case id :RPMXCON-47000");
		baseClass.stepInfo("Search > Doc List > Doc view when search with Metadata & Content search "
				+ "first and then Audio search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search for audio + Metadata
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(null, null, Input.audioSearch, Input.language,
				"", combinations, Input.docName, metaDataIp, "min", true);

		driver.scrollPageToTop();
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Session search to doclist");

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();

		// view selected documents in Docview
		baseClass.stepInfo("After selecting docs from DocList to Docview");
		docListPage.viewSelectedDocumentsInDocView();
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.stepInfo("User on the audio document on docview page");

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearch);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the Jplayer", "Audio hits not highlighted on the Jplayer");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/03/2021 Modified date: NA Modified by: Baskar
	 * @Description : Search > Doc List > Doc view when search is set up with the
	 *              Audio search first then Metadata & Content search, hits should
	 *              be highlighted
	 */

	@Test(description = "RPMXCON-47001", enabled = true, groups = { "regression" }, priority = 48)
	public void verifyDocListToDocViewFirstAudio() throws InterruptedException {
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		docListPage = new DocListPage(driver);
		softAssertion = new SoftAssert();

		String[] combinations = { "Metadata + Audio" };
		String metaDataIp = "ID0*";

		baseClass.stepInfo("Test case id :RPMXCON-47001");
		baseClass.stepInfo("Search > Doc List > Doc view when search is set up with the Audio search "
				+ "first then Metadata & Content search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Search for audio + Metadata
		sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(null, null, Input.audioSearch, Input.language,
				"", combinations, Input.docName, metaDataIp, "min", true);

		driver.scrollPageToTop();
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Session search to doclist");

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();

		// view selected documents in Docview
		baseClass.stepInfo("After selecting docs from DocList to Docview");
		docListPage.viewSelectedDocumentsInDocView();
		baseClass.waitForElement(docViewPage.getDocView_TextFileType());
		softAssertion.assertEquals(docViewPage.getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		baseClass.stepInfo("User on the audio document on docview page");

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(Input.audioSearch);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the Jplayer", "Audio hits not highlighted on the Jplayer");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/03/2021 Modified date: NA Modified by: Baskar
	 * @Description : Saved Search > Doc List > Doc view when search is with Metadata & Content search 
	 *                first and then Audio search, hits should be highlighted
	 */

	@Test(description = "RPMXCON-47006", enabled = true, groups = { "regression" }, priority = 49)
	public void verifyHitFromSavedToDocListToDocviewFirstMetaData() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		miniDocListpage = new MiniDocListPage(driver);
		docListPage = new DocListPage(driver);

		List<String> searchList = new ArrayList<>();
		Set<String> purehitKeySet = new HashSet<String>();
		List<String> docIDlist = new ArrayList<>();
		int pH = 0;
		String firnstDocname;
		String searchName = "", metaDataType = "DocID", metaDataIp = "ID00*";
		String[] combinations = { "Metadata + Audio" };
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-47006");
		baseClass.stepInfo("Saved Search > Doc List > Doc view when search is with Metadata & Content search first "
				+ "and then Audio search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Combinational Search
		baseClass.stepInfo("Audio Input : " + audioSearchInput);
		Map<String, Integer> purehit = sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(
				Input.searchString1, Input.searchString1, audioSearchInput, Input.language, "", combinations,
				metaDataType, metaDataIp, "", true);
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
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		savedSearch.savedSearch_SearchandSelect(searchName, "Yes");
		savedSearch.getToDocList().waitAndClick(5);
		baseClass.stepInfo("Saved Search to doclist page");

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();

		// view selected documents in Docview
		baseClass.stepInfo("After selected docs from DocListpage user navigate to Docview Page");
		docListPage.viewSelectedDocumentsInDocView();

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(audioSearchInput);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the jplayer", "Audio hits not highlighted on the jplayer");

		// Delete Search
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: 22/03/2021 Modified date: NA Modified by: Baskar
	 * @Description : Search > Doc List > Doc view when search is set up with the
	 *              Audio search first then Metadata & Content search, hits should
	 *              be highlighted
	 */

    @Test(description = "RPMXCON-47007", enabled = true, groups = { "regression" }, priority = 50)
	public void verifyHitFromSavedToDocListToDocviewFirstAudio() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		miniDocListpage = new MiniDocListPage(driver);
		docListPage = new DocListPage(driver);

		List<String> searchList = new ArrayList<>();
		Set<String> purehitKeySet = new HashSet<String>();
		List<String> docIDlist = new ArrayList<>();
		int pH = 0;
		String firnstDocname;
		String searchName = "", metaDataType = "DocID", metaDataIp = "ID00*";
		String[] combinations = { "Audio + Metadata" };
		String audioSearchInput = Input.audioSearch;

		baseClass.stepInfo("Test case id :RPMXCON-47007");
		baseClass.stepInfo("Saved Search > Doc List > Doc view when search is with Audio search first and then "
				+ "Metadata & Content search, hits should be highlighted");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Combinational Search
		baseClass.stepInfo("Audio Input : " + audioSearchInput);
		Map<String, Integer> purehit = sessionSearch.advancedSearchWithCombinationsSaveUnderMySearches(
				Input.searchString1, Input.searchString1, audioSearchInput, Input.language, "", combinations,
				metaDataType, metaDataIp, "", true);
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
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		savedSearch.savedSearch_SearchandSelect(searchName, "Yes");
		savedSearch.getToDocList().waitAndClick(5);
		baseClass.stepInfo("Saved Search to doclist page");

		// selecting all documents in DocList
		docListPage.selectingAllDocFromAllPagesAndAllChildren();

		// view selected documents in Docview
		baseClass.stepInfo("After selected docs from DocListpage user navigate to Docview Page");
		docListPage.viewSelectedDocumentsInDocView();

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.verifyPersistantDataPresent(audioSearchInput);

		// Hits Notch On Jplayer check
		baseClass.verifyElementCollectionIsNotEmpty(docViewPage.getHitsNotchOnJplayer(),
				"Audio hits highlighted on the jplayer", "Audio hits not highlighted on the jplayer");

		// Delete Search
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logout
		loginPage.logout();

	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
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
