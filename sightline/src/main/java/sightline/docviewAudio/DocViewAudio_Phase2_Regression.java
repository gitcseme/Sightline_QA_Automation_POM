package sightline.docviewAudio;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Dashboard;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Phase2_Regression {

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
	DocViewPage docViewPage;
	SoftAssert softAssertion;

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
		softAssertion=new SoftAssert();
		sessionSearch=new SessionSearch(driver);
		docViewPage=new DocViewPage(driver);
	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "RmuRev")
	public Object[][] RmuRev() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
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
		docViewPage = new DocViewPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullname);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		docViewPage.clickGearIconOpenMiniDocList();
		baseClass.passedStep(" Clicked gear icon mini doc list child window is opened");
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docViewPage.getClickDocviewID(2);
		baseClass.stepInfo("Clicked audio doc file is loaded");
		docViewPage.switchToNewWindow(1);
		baseClass.waitTime(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String nexticon = docViewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docViewPage.getDocView_IconNext());
		docViewPage.getDocView_IconNext().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String nexticon1 = docViewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (nexticon.equals(nexticon1)) {
			baseClass.failedStep("Cursor is not moved");

		} else {
			baseClass.passedStep("Cursor is moved to the next phonetic hit position successfully");
		}
		docViewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String previousicon = docViewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docViewPage.getDocView_IconPrev());
		docViewPage.getDocView_IconPrev().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String previousicon1 = docViewPage.getDocView_RunningTime().getText();
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
	  docViewPage = new DocViewPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullname);
		sessionSearch.basicContentSearch(Input.oneHourAudio);

		// Navigate to DocView Page audio
		baseClass.stepInfo("Navigate to DocView Page audio");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();

		// Get verify Audio counter readouts duration
		String Audiostarttime = docViewPage.getDocview_Audio_StartTime().getText();
		if (docViewPage.getDocview_Audio_StartTime().isDisplayed()) {
			System.out.println(Audiostarttime);
			baseClass.passedStep(
					Audiostarttime + "..Starttime play counter readouts is  successfully displayed on docview audio");
		} else {
			baseClass.failedStep("Start play counter readouts is not displayed");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String runningTime = docViewPage.getDocView_RunningTime().getText();
		if (docViewPage.getDocView_RunningTime().isDisplayed()) {
			System.out.println(runningTime);
			baseClass.passedStep(runningTime
					+ ".. In Runningtime play counter readouts is  successfully displayed on docview audio");
		} else {
			baseClass.failedStep("Runningtime play counter readouts is not displayed");

		}
		String Audioendtime = docViewPage.getDocview_Audio_EndTime().getText();
		if (docViewPage.getDocview_Audio_EndTime().isDisplayed()) {
			baseClass.passedStep(
					Audioendtime + "..Endtime play counter readouts is  successfully displayed on docview audio");

		} else {
			baseClass.failedStep("Endtime play counter readouts is not displayed");
		}

	}

	/**
	 * @author Krishna ModifyDate: RPMXCON-51073
	 * @throws Exception
	 * @Description Verify user can see the redaction, redaction tags of audio file
	 *              on doc view page in different security group if the same
	 *              annotation layer, redaction tag is mapped to different security
	 *              groups.
	 */
	@Test(description = "RPMXCON-51073", enabled = true, groups = { "regression" })
	public void verifyRedactionTagAudioFileIsMappedDifferentSecurity() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51073");
		baseClass.stepInfo(
				"Verify user can see the redaction, redaction tags of audio file on doc view page in different security group if the same annotation layer, redaction tag is mapped to different security groups.");
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		String annoName = "newAnnotation" + Utility.dynamicNameAppender();
		String sgName1 = "SecurityGroup1_" + UtilityLog.dynamicNameAppender();
		RedactionPage redactTag = new RedactionPage(driver);
		String redactnam = "ATest" + utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(sgName1);
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.bulkRelease(sgName1);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rev1userName);
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as user RMU   " + Input.securityGroup);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		if (docViewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
			baseClass.passedStep("Redaction tag is successfully displayed in audio file");

		} else {
			baseClass.failedStep("Redaction tag is not displayed");
		}
		loginPage.logout();
		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as user RMU   " + sgName1);
		baseClass.selectsecuritygroup(sgName1);
		driver.waitForPageToBeReady();
		annotation.navigateToAnnotationLayerPage();
		driver.waitForPageToBeReady();
		annotation.addAnnotationLayer(annoName, annoName);
		redactTag.navigateToRedactionsPageURL();
		driver.waitForPageToBeReady();
		redactTag.manageRedactionTagsPage(redactnam);
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		if (docViewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
			baseClass.passedStep("Redaction tag is successfully displayed in audio file");

		} else {
			baseClass.failedStep("Redaction tag is not displayed");
		}
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

		// Login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login as Rev   " + Input.securityGroup);
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		if (docViewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
			baseClass.passedStep("Redaction tag is successfully displayed in audio file");

		} else {
			baseClass.failedStep("Redaction tag is not displayed");
		}
		loginPage.logout();

		// Login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login as Rev   " + sgName1);
		baseClass.selectsecuritygroup(sgName1);
		driver.waitForPageToBeReady();
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		if (docViewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
			baseClass.passedStep("Redaction tag is successfully displayed in audio file");

		} else {
			baseClass.failedStep("Redaction tag is not displayed");
		}
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

		// Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage.deleteSecurityGroups(sgName1);

	}

	/**
	 * @author Raghuram.A
	 * @Description : Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel from saved search [RPMXCON-51796]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51796", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocsNotDisplayPersistentHitInDocViewViaSS(String userName, String password, String fullName)
			throws Exception {

		DocViewPage docview = new DocViewPage(driver);
		DocListPage doclist = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);

		String audioSearch = Input.audioSearchString2 + " AND " + Input.audioSearchString3;
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search Name" + UtilityLog.dynamicNameAppender();
		List<String> searchTerm = new ArrayList<String>();

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("LoggedIn as : " + fullName);

		baseClass.stepInfo("Test case Id: RPMXCON-51796 Docview/Audio");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel from saved search");

		// Create Node
		baseClass.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "Yes");

		// Audio search
		int purehit = sessionSearch.audioSearch(audioSearch, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docview.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docview.getMiniDocListDocIdText());

		// Audio search
		baseClass.selectproject();
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.saveSearchInNewNode(searchName1, newNode);

		// Added another Audio search and add
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.saveSearchInNewNode(searchName2, newNode);
		searchTerm.add(Input.audioSearchString2.toLowerCase());
		searchTerm.add(Input.audioSearchString3.toLowerCase());

		// View in DocView via SS
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(newNode);
		saveSearch.getToDocList().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to DocList page vis SavedSearch page");

		// view in docview
		doclist.selectingAllDocuments();
		baseClass.stepInfo("View selected docs in Doc View");
		doclist.viewSelectedDocumentsInDocView();

		// Check Display persistant hit - notrepetative
		docview.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		docview.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm);

		// Initiate delete search
		saveSearch.deleteNode(Input.mySavedSearch, newNode, true, true);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A
	 * @Description : Verify that when document present in different session
	 *              searches with common term then, should not display repetitive
	 *              search term on persistent hits panel from session search > Doc
	 *              List> Doc View. [RPMXCON-51797]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51797", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocsNotDisplayPersistentHitInDocView(String userName, String password, String fullName)
			throws Exception {

		DocViewPage docview = new DocViewPage(driver);
		DocListPage doclist = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		String audioSearch = Input.audioSearchString2 + " AND " + Input.audioSearchString3;
		List<String> searchTerm = new ArrayList<String>();

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("LoggedIn as : " + fullName);

		baseClass.stepInfo("Test case Id: RPMXCON-51797 Docview/Audio");
		baseClass.stepInfo(
				"Verify that when document present in different session searches with common term then, should not display repetitive search term on persistent hits panel from session search > Doc List> Doc View");

		// Audio search
		int purehit = sessionSearch.audioSearch(audioSearch, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docview.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docview.getMiniDocListDocIdText());

		// Audio search
		baseClass.selectproject();
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();

		// Added another Audio search and add
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		searchTerm.add(Input.audioSearchString2.toLowerCase());
		searchTerm.add(Input.audioSearchString3.toLowerCase());

		// Go to doclist page
		baseClass.stepInfo("Navigating to DocList page via Session Search");
		sessionSearch.ViewInDocListWithOutPureHit();
		driver.waitForPageToBeReady();

		// View in docview
		doclist.selectingAllDocuments();
		baseClass.stepInfo("View selected docs in Doc View");
		doclist.viewSelectedDocumentsInDocView();

		// Check Display persistant hit - notrepetative
		docview.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		docview.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm);

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 09/22/2022 Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to Save the
	 * document, then waveform should be loaded [Less than 1 hr audio file] from
	 * coding form child window
	 */

	@Test(description = "RPMXCON-51820", enabled = true, groups = { "regression" })
	public void validatePlayIconInCfChild() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51820");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to Save the document, "
				+ "then waveform should be loaded [Less than 1 hr audio file] from coding form child window");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		docViewPage= new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

		String comment = "comments" + Utility.dynamicNameAppender();

		// search to Assignment creation
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		driver.waitForPageToBeReady();
		String oneHourDocs = docViewPage.getVerifyPrincipalDocument().getText();

		// verifying more than one hour audio docs
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) <= 01) {
			baseClass.stepInfo("Audio document duration is lesser than one hour");
		} else {
			baseClass.failedMessage("More than one hour");
		}

		// playing audio file
		docViewPage.audioPlayPauseIcon().waitAndClick(5);

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
		docViewPage.closeWindow(1);

		// validating waveform displaying for same document
		docViewPage.switchToNewWindow(1);
		String childactiveId = docViewPage.getDocView_CurrentDocId().getText();
		softAssertion.assertEquals(oneHourDocs, childactiveId);
		boolean childwaveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(childwaveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean childaudioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(childaudioPlay);
		baseClass.stepInfo("Audio  docs are in play mode");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/09/2022 RPMXCON-51800
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with common term, different term and different/same
	 *              threshold are assigned to assignment at different time from
	 *              session search.
	 */
	@Test(description = "RPMXCON-51800", enabled = true, groups = { "regression" })
	public void verifyAudioDocsHitsDisplayedCommonTermAndDifferent() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51800");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common term, different term and different/same threshold are assigned to assignment at different time from session search");
		sessionSearch = new SessionSearch(driver);
		 docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String Assignname = "Assignment" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		int purehit = sessionSearch.audioSearch(audioSearch, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docViewPage.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docViewPage.getMiniDocListDocIdText());

		// Audio search1
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();

		// Audio search2
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();

		// Audio search3 And bulkassign
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();

		// Select Assignment goto docview
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(Asssignment);

		// Check Display persistant hit - notrepetative
		docViewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString3);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.bulkAssign();

		// Select Assignment goto docview
		assignmentPage.assignmentCreation(Assignname, Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(Assignname);

		// Check Display persistant hit - notrepetative
		docViewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString3);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/09/2022 RPMXCON-51808
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel on completing the document same as
	 *              last.
	 */

	@Test(description = "RPMXCON-51808", enabled = true, groups = { "regression" })
	public void verifyAudioDocsHitSaveSeachesWithCompleteSameAsLast() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51808");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel on completing the document same as last.");
		sessionSearch = new SessionSearch(driver);
		 docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		docViewPage=new DocViewPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		int purehit = sessionSearch.audioSearch(audioSearch, Input.language);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		// Audio search And Save
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString2, Input.language);
		driver.waitForPageToBeReady();
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		driver.waitForPageToBeReady();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		driver.waitForPageToBeReady();
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// Click on bulkAssign
		sessionSearch.bulkAssignWithOutPureHit();

		// Create Assigment
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Impersnated from RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection and Reviewer
		driver.waitForPageToBeReady();
		assignmentPage.SelectAssignmentByReviewer(Asssignment);
		baseClass.waitTime(3);
		baseClass.waitForElementCollection(docViewPage.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList2 = baseClass.availableListofElements(docViewPage.getMiniDocListDocIdText());
		// Check Display persistant hit - notrepetative
		docViewPage.selectDocIdInMiniDocList(DocIDInMiniDocList2.get(purehit - 1));
		driver.waitForPageToBeReady();

		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString3);

		// Complete the document And SameAs Last
		driver.waitForPageToBeReady();
		docViewPage.editingCodingFormWithCompleteButton();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/10/2022 RPMXCON-51779
	 * @throws Exception
	 * @Description Verify when audio document present in two different save
	 *              searches with common term and assigned to existing assignment,
	 *              then it should not display repetitive search term on persistent
	 *              hits panel.
	 */
	@Test(description = "RPMXCON-51779", enabled = true, groups = { "regression" })
	public void verifyAudioDocsDifferentSearchsNotDisplayPersistentHitInDocView() throws Exception {

		DocViewPage docview = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;
		List<String> searchTerm = new ArrayList<String>();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);

		baseClass.stepInfo("Test case Id: RPMXCON-51779 Docview/Audio");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel from saved search");
		// Audio search
		int purehit = sessionSearch.audioSearch(audioSearch, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docview.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docview.getMiniDocListDocIdText());

		// Audio search
		baseClass.selectproject();
		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString2, Input.language);
		sessionSearch.addPureHit();
		// Added another Audio search and add
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.bulkAssignWithOutPureHit();

		// Select Assignment goto docview
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(Asssignment);

		// Check Display persistant hit - notrepetative
		docview.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docview.getAudioPersistantHitEyeIcon());
		docview.getAudioPersistantHitEyeIcon().Click();
		docview.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString3);

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify that audio files increase volume functionality is working
	 * properly inside Doc view screen
	 * 
	 */

	@Test(description = "RPMXCON-51099", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void validatePauseFunctionInVideoDocs(String userName, String password, String fullName)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51099");
		baseClass.stepInfo("Verify that audio files increase volume functionality is working "
				+ "properly inside Doc view screen");

		String volumeActual = "width: 100%;";
		System.out.println(volumeActual);
		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// playing the audio
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.stepInfo("Audio file is played successfully");

		// increase the volume
		baseClass.waitForElement(docViewPage.getVolumeMaxButton());
		docViewPage.getVolumeMaxButton().waitAndClick(5);
		baseClass.stepInfo("Audio file max volume button is clicked");
		baseClass.waitForElement(docViewPage.getVolumeLevel());
		String volumeExpected = docViewPage.getVolumeLevel().GetAttribute("style");
		System.out.println(volumeExpected);
		softAssertion.assertEquals(volumeActual,volumeExpected);
		baseClass.passedStep("user can increase the volume button inside docview screen for audio documents");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	@DataProvider(name = "Impersonate")
	public Object[][] impersonate() {
		Object[][] users = { { "sa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password, "pa" },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rev1userName, Input.rev1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rev1userName, Input.rev1password, "rev" },
				{ "rmu", Input.rmu1userName, Input.rmu1password, Input.rev1userName, Input.rev1password, "rev" } };
		return users;
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify audio doc view after impersonation when transcript is not
	 * ingested for audio file
	 * 
	 */

	@Test(description = "RPMXCON-51135", dataProvider = "Impersonate", enabled = true, groups = { "regression" })
	public void validateTranscriptTab(String role, String userName, String password, String impuserName,
			String imppassword, String impersonate) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51135");
		baseClass.stepInfo("Verify audio doc view after impersonation when transcript is not ingested for audio file");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		// impersonate @user2
		baseClass.credentialsToImpersonateAsPARMUREV(role, impersonate);

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("User navigated to audio docview page");

		// verify transcript tab
		boolean flagTans = docViewPage.getTranscriptsTab().isElementAvailable(15);
		softAssertion.assertTrue(flagTans);
		baseClass.passedStep("Transcript tab not displayed for audio docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify user after impersonation can see the persistent search on
	 * audio doc view in context of an assignment
	 * 
	 */

	@Test(description = "RPMXCON-51138", enabled = true, groups = { "regression" })
	public void verifyTriangularArrowOnAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51138");
		baseClass.stepInfo("Verify user after impersonation can see the persistent search on "
				+ "audio doc view in context of an assignment");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String assgn = "assign" + Utility.dynamicNameAppender();

		// Login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as '" + Input.rmu1userName + "'");

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgn, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();

		// Login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgn);

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);

		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);
		baseClass.stepInfo("clicked on hit forward");

		baseClass.elementDisplayCheck(docViewPage.getTriangularIcon());
		baseClass.stepInfo("Audio triangular arrow is displayed");
		String start = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		docViewPage.audioPersistentBackwardNavigate().waitAndClick(10);
		baseClass.stepInfo("clicked on hit Back");
		String end = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		softAssertion.assertNotEquals(start, end);
		baseClass.stepInfo("Audio triangular arrow position verified");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 25/08/2022 Modified by: Baskar
	 * Description:Verify triangular arrow to highlight the position should be
	 * displayed at the position where the search term occurs with the score
	 * 
	 */

	@Test(description = "RPMXCON-51136", enabled = true, groups = { "regression" })
	public void verifyTriangularArrow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51136");
		baseClass.stepInfo("Verify triangular arrow to highlight the position should be displayed at the "
				+ "position where the search term occurs with the score");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String assgn = "assign" + Utility.dynamicNameAppender();

		// Login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as '" + Input.rmu1userName + "'");

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgn, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignmentPage.assignmentPagination(assgn);
		baseClass.waitForElement(assignmentPage.getAssignmentAction_ViewinDocView());
		assignmentPage.getAssignmentAction_ViewinDocView().waitAndClick(10);
		baseClass.stepInfo("Assignment selected and viewAllDocs in docview");

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);

		baseClass.waitForElement(docViewPage.audioPersistentForwardNavigate());
		docViewPage.audioPersistentForwardNavigate().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("clicked on hit forward");

		baseClass.elementDisplayCheck(docViewPage.getTriangularIcon());
		baseClass.stepInfo("Audio triangular arrow is displayed");
		String start = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		docViewPage.audioPersistentBackwardNavigate().waitAndClick(10);
		baseClass.stepInfo("clicked on hit Back");
		String end = docViewPage.getAudioPlayerCurrentState().GetAttribute("style").trim();
		softAssertion.assertNotEquals(start, end);
		baseClass.stepInfo("Audio triangular arrow position verified");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author:Baskar date: 26/08/2022 Modified date: NA Modified by:
	 * @Description IE11: Verify when user enters the document id in the "doc id
	 *              input box" and hits the enter key, then corresponding audio doc
	 *              should load in the default view prior to that document
	 *              navigation is done from mini doc list child window
	 */
	@Test(description = "RPMXCON-51632", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void verifyDocumentLoadedFromMiniDocList(String username, String password) throws Exception {

		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51632");
		baseClass.stepInfo(
				"IE11: Verify when user enters the document id in the \"doc id input box\" and hits the enter key, then "
						+ "corresponding audio doc should load in the default view prior to that document navigation is done from mini doc list child window");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		// view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();

		// verify doc id from child window
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docViewPage.selectRowFromMiniDocList(5);
		driver.waitForPageToBeReady();
		String minidocid = docViewPage.getMiniDocListData(5, 2).getText().trim();
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		String currentdocid = docViewPage.getDocViewSelectedDocId().getText().trim();
		softAssertion.assertEquals(currentdocid, minidocid);
		baseClass.passedStep(
				"Clicked audio document was load in the default view and same document is selected from mini doc list child window");

		// verify from docid default view prior
		docViewPage.getDocView_NumTextBox().SendKeys("3" + Keys.ENTER);
		driver.waitForPageToBeReady();
		currentdocid = docViewPage.getDocViewSelectedDocId().getText().trim();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String idOnMiniDoc = docViewPage.getMiniDocListData(3, 2).getText().trim();
		softAssertion.assertEquals(currentdocid, idOnMiniDoc);
		baseClass.passedStep(
				"Corresponding audio document was load in the default view and same document is selected from mini doc list child window");
		driver.close();
		docViewPage.switchToNewWindow(1);

		softAssertion.assertAll();
		baseClass.passedStep(
				"Verified when user enters the document id in the \"doc id input box\" and hits the enter key, then corresponding audio doc "
						+ "should load in the default view prior to that document navigation is done from mini doc list child window");
		loginPage.logout();

	}

	/**
	 * Author :Baskar date: 26/08/2022 Modified date: NA Modified by:
	 * 
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with different term and different/same threshold are
	 *              assigned to assignment at different time from session search
	 * @throws InterruptedException
	 * 
	 */
	@Test(description = "RPMXCON-51799", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdInSessionSearchBulkAssign() throws InterruptedException {

		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case id :RPMXCON-51799");
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with different term and "
				+ "different/same threshold are assigned to assignment at different time from session search");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as RMU");

		String searchString1 = Input.audioSearchString3;
		String searchString2 = Input.audioSearchString4;
		String assignmentName = "assgnAudio" + Utility.dynamicNameAppender();
		String assignmentName2 = "assgnAudio" + Utility.dynamicNameAppender();
		// First audio Search
		sessionSearch.audioSearch(searchString1, Input.language);
		sessionSearch.getPureHitAddButton().waitAndClick(10);

		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName);
		driver.waitForPageToBeReady();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString1);

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// second audio search
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearch(searchString2, Input.language);
		sessionSearch.addPureHit();

		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName2);
		assignmentPage.quickAssignmentCreation(assignmentName2, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName2, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName2);
		driver.waitForPageToBeReady();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString2.toLowerCase());

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// First audio search term with max threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString1, Input.language, "max");
		sessionSearch.addPureHit();

		// second audio search with same term and min threshold value
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString2, Input.language, "min");
		sessionSearch.addPureHit();

		String assignmentName1 = "assgnAudio" + Utility.dynamicNameAppender();
		// view All audio Docs in DocList
		sessionSearch.bulkAssign();
		assignmentPage.assignDocstoNewAssgn(assignmentName1);
		assignmentPage.quickAssignmentCreation(assignmentName1, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName1, Input.codeFormName);

		assignmentPage.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		assignmentPage.manageAssignmentToDocViewAsRmu(assignmentName1);

		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigating to Docview Page");

		baseClass.stepInfo("Verifying audio hits  displayed when documents searched with same term and different"
				+ "threshold navigated to doc view from session search > Assignments Page>Docview");

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString2.toLowerCase());
		loginPage.logout();

	}

	/**
	 * @author Krishna ModifyDate: RPMXCON-51824
	 * @throws Exception
	 * @Description Verify that when audio file is playing and clicked to apply the
	 *              stamp, then waveform should be loaded [Less than 1 hr audio
	 *              file] from coding form child window
	 */
	@Test(description = "RPMXCON-51824", enabled = true, groups = { "regression" })
	public void verifyAudioPlayerClickedApplyStampFromChildWindow() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51824");
		baseClass.stepInfo(
				"Verify that when audio file is playing and clicked to apply the stamp, then waveform should be loaded [Less than 1 hr audio file] from coding form child window");
		sessionSearch = new SessionSearch(driver);
	 docViewPage = new DocViewPage(driver);
		String assign = "Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);

		driver.waitForPageToBeReady();
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.Navigate().refresh();
		baseClass.handleAlert();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.passedStep("Audio file is played successfully");
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Coding form and mini doc list and codingform child window is opened");

		docViewPage.switchToNewWindow(2);
		baseClass.waitTime(4);
		docViewPage.editCodingForm();
		baseClass.waitForElement(docViewPage.getCodingFormStampButton());
		docViewPage.getCodingFormStampButton().waitAndClick(10);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		docViewPage.popUpAction(comment, Input.stampSelection);
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCodingStampLastIcon(Input.stampSelection));
		docViewPage.getCodingStampLastIcon(Input.stampSelection).waitAndClick(10);
		docViewPage.getCodingFormSaveThisForm().waitAndClick(5);
		baseClass.stepInfo("Coding form saved with stamp and document saved successfully");
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		if (docViewPage.getDocView_AudioPlay().isElementPresent()) {
			baseClass.passedStep("Document in play mode");

		} else {
			baseClass.failedStep("failed to play");
		}
		driver.Navigate().refresh();
		docViewPage.deleteStampColour(Input.stampSelection);

	}

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author : Mohan date: 11/15/22 Modified date: NA Modified by: NA
	 * Description:Verify navigating to the audio document should bring up the
	 * document in 4 sec and ready for the user to act up on when navigating from
	 * Tally/Sub Tally report
	 */
	@Test(description = "RPMXCON-51528", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentNavigationToDocViewIn4Secs() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51528");
		baseClass.stepInfo(
				"Verify navigating to the audio document should bring up the document in 4 sec and ready for the user to act up on when navigating from Tally/Sub Tally report");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.pa1userName + "'");

		// search for audio docs
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.tallyResults();

		// select tally field and clickon apply button
		TallyPage tallyPage = new TallyPage(driver);
		tallyPage.selectTallyByMetaDataField(Input.dataSourceHeader);
		tallyPage.selectBarChartAndNavigateTo("View in DocView");

		// verify the latency of application
		docViewPage= new DocViewPage(driver);
		docViewPage.loadingCountVerify(4, docViewPage.getDocView_CurrentDocId());

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 11/15/22 Modified date: NA Modified by: NA
	 * Description:Verify that when mini doc list is configured then should display
	 * search term on persistent hits panel
	 */
	@Test(description = "RPMXCON-51852", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyMiniDocListConfiguredDisplaysSearchTermPersistentHitPanel(String username, String password,
			String role) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51852");
		baseClass.stepInfo(
				"Verify that when mini doc list is configured then should display search term on persistent hits panel");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		String searchName1 = "AudioSearch" + Utility.dynamicNameAppender();

		// create new search group
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.NavigateToSavedSearch();
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, role, "No");

		// save audio search under created node
		SessionSearch session = new SessionSearch(driver);
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.saveSearchInNewNode(searchName1, newNode);

		baseClass.waitForElement(session.getNewSearchButton());
		session.getNewSearchButton().waitAndClick(5);

		// Saving additional search under child node
		session.advancedContentBGSearch(Input.searchString1, 1, false);
		session.saveSearchInNodewithChildNode(searchName1, newNode);

		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		saveSearch.docViewFromSS("View in DocView");

		MiniDocListPage miniDocListPage = new MiniDocListPage(driver);
		miniDocListPage.fromSavedSearchToSelectWebField();

		loginPage.logout();

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

		docViewPage= new DocViewPage(driver);
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

		docViewPage= new DocViewPage(driver);
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

		docViewPage= new DocViewPage(driver);
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

		docViewPage= new DocViewPage(driver);
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

		docViewPage= new DocViewPage(driver);
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

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-51777
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
		baseClass.waitTime(5);
		baseClass.waitTillElemetToBeClickable(docview.getAudioPersistantHitEyeIcon());
		docview.getAudioPersistantHitEyeIcon().Click();
		docview.verifyAudioPersistanHitNotDisplayed(Input.audioSearchString2);
		int searchterm = docview.getSearchTermList(Input.audioSearchString3).size();
		if (searchterm <= 1) {
			baseClass.passedStep("repetitive search term is not displayed");
		} else {
			baseClass.failedStep("repetitive search term is displayed");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-51778
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
		} else {
			baseClass.failedStep("repetitive search term is displayed");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-51341
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
		docViewPage= new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("LoggedIn as : " + fullName);

		// Audio search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String nexticon = docViewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docViewPage.getDocView_IconNext());
		docViewPage.getDocView_IconNext().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String nexticon1 = docViewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (nexticon.equals(nexticon1)) {
			baseClass.failedStep("Cursor is not moved");

		} else {
			baseClass.passedStep("Cursor is moved to the next phonetic hit position successfully");
		}
		baseClass.waitTime(5);
		softAssertion.assertTrue(docViewPage.getDocView_AudioPlay().isElementPresent());
		baseClass.passedStep("Documentfile has been  in play mode as expected");

		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String previousicon = docViewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docViewPage.getDocView_IconPrev());
		docViewPage.getDocView_IconPrev().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_RunningTime());
		String previousicon1 = docViewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (previousicon.equals(previousicon1)) {
			baseClass.failedStep("Cursor is not moved");
		} else {
			baseClass.passedStep("Cursor is moved to the previous phonetic hit position successfully");

		}
		softAssertion.assertTrue(docViewPage.getDocView_AudioPlay().isElementPresent());
		baseClass.passedStep("Documentfile has been  in play mode as expected");
		softAssertion.assertAll();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/11/2022 RPMXCON-51096
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that audio files move to Next hit functionality is
	 *              working properly inside Doc view screen.
	 */

	@Test(description = "RPMXCON-51096", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioClickMoveToNextInDocViewScreen(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51096");
		baseClass.stepInfo(
				"Verify that audio files move to Next hit functionality is working properly inside Doc view screen.");

		docViewPage= new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocListPage docList = new DocListPage(driver);

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocViews();

		// Click play btn
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.waitTime(5);
		String playtime1 = docList.getDocList_Preview_AudioDuration().getText();
		System.out.println(playtime1);
		baseClass.stepInfo(playtime1);

		// click the next hit Btn
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.getDocView_IconNext());
		docViewPage.getDocView_IconNext().waitAndClick(10);
		baseClass.stepInfo("Audio Next Hit Icon Clicked Successfully");
		driver.waitForPageToBeReady();
		String afterNextPlayTime = docList.getDocList_Preview_AudioDuration().getText();
		System.out.println(afterNextPlayTime);
		baseClass.stepInfo(afterNextPlayTime);

		// verify Next hit btn working in docview screen
		softAssertion.assertNotEquals(playtime1, afterNextPlayTime);
		baseClass.passedStep("Audio files move to Next hit functionality is work properly inside Doc view screen");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/11/2022 RPMXCON-51480
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that application should not hang when the user tries to
	 *              complete the audio document by applying coding stamp when audio
	 *              is playing.
	 */

	@Test(description = "RPMXCON-51480", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentApplyCodingForm() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-51480");
		baseClass.stepInfo(
				"Verify that application should not hang when the user tries to complete the audio document by applying coding stamp when audio is playing.");

		docViewPage= new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assign = "Assignment" + Utility.dynamicNameAppender();
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Create assignment and distribute the docs");
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assign, Input.codeFormName, SessionSearch.pureHit);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);

		// edit codingform and apply stamp
		driver.waitForPageToBeReady();
		docViewPage.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(Input.codingFormName);
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColours);
		// play Audiofile
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		docViewPage.stampColourSelection(filedText, Input.stampColours);
		docViewPage.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep("application is not hang on applying the stamp when audio is playing");
		loginPage.logout();
		// Login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rev1userName + "");
		// Assignment Selection and Reviewer
		assignmentPage.SelectAssignmentByReviewer(assign);
		// edit codingform and apply stamp
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColours);
		// play Audiofile
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		docViewPage.stampColourSelection(filedText, Input.stampColours);
		docViewPage.docviewPageLoadPerformanceForStamp();
		baseClass.passedStep("application is not hang on applying the stamp when audio is playing");
		loginPage.logout();
	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Description:Verify after
	 * impersonation user can see the transcript in audio doc view in context of an
	 * assignment
	 * 
	 */

	@Test(description = "RPMXCON-51125", enabled = true, groups = { "regression" })
	public void validateAfterImpersonateTranscriptTabContextAssgn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51125");
		baseClass.stepInfo(
				"Verify after impersonation user can see the transcript in audio doc view in context of an assignment");
		docViewPage= new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignPage = new AssignmentsPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as '" + Input.rmu1userName + "'");

		// search to docview
		sessionSearch.MetaDataSearchInBasicSearch("SourceDocID", Input.transcriptId);
		sessionSearch.bulkAssign();
		assignPage.assignmentCreation(Asssignment, Input.codingFormName);
		System.out.println(Asssignment);
		assignPage.assignmentDistributingToReviewerManager();
		baseClass.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verify transcript tab display
		baseClass.waitForElement(docViewPage.getTranscriptsTab());
		boolean flagTans = docViewPage.getTranscriptsTab().isDisplayed();
		softAssertion.assertTrue(flagTans);
		baseClass.passedStep("Transcript tab displayed for audio docs as expected");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author N/A
	 * @Description :Audio Documents assigned from Saved Search- Verify that when
	 *              documents are re-assigned to same/other reviewer in an
	 *              assignment," + " any previously saved persistent search hits in
	 *              the assignment should be displayed in the
	 *              assignment[RPMXCON-51766]
	 */
	@Test(description = "RPMXCON-51766", enabled = true, groups = { "regression" })
	public void verifyPersistentForAudioReAssignSameRev() throws InterruptedException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearch.toUpperCase();

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SavedSearch search = new SavedSearch(driver);
		baseClass.stepInfo("RPMXCON-51766");
		baseClass.stepInfo(
				"Audio Documents assigned from Saved Search- Verify that when documents are re-assigned to same/other reviewer in an assignment,"
						+ " any previously saved persistent search hits in the assignment should be displayed in the assignment");

		// Login as RMU (Pre - Req)
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		String searchName = "Search " + Utility.dynamicNameAppender();
		String newNode = search.createSearchGroupAndReturn(searchName, "RMU", "Yes");
		sessionSearch.navigateToSessionSearchPageURL();
		int pureHit = sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.saveSearchInNewNode(searchName, newNode);
		search.bulkAssignPersistencSS(searchName, true);
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignmentPage.addReviewerInManageReviewerTab();
		assignmentPage.distributeGivenDocCountToReviewers(Integer.toString(pureHit));
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		driver.waitForPageToBeReady();
		Dashboard dash = new Dashboard(driver);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		String hitscount = docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignmentPage.removeDocs(Input.rev1userName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignmentPage.reassignDocs(Input.rev1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");
		baseClass.passedStep(
				"Verified - Audio Documents assigned from Saved Search- Verify that when documents are re-assigned to same/other reviewer in an assignment,"
						+ " any previously saved persistent search hits in the assignment should be displayed in the assignment");
		loginPage.logout();

	}

	/**
	 * @author N/A
	 * @Description :To Verify Audio Documents assigned from Advanced search- Verify
	 *              that when documents are re-assigned to same/other reviewer in an
	 *              assignment," + " any previously saved persistent search hits in
	 *              the assignment should be displayed in the assignment[51767]
	 */
	@Test(description = "RPMXCON-51767", enabled = true, groups = { "regression" })
	public void verifyPersisForAudioReAssignSameRevAdvSrc() throws InterruptedException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearch.toUpperCase();

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("RPMXCON-51767");
		baseClass.stepInfo(
				"To Verify Audio Documents assigned from Advanced search- Verify that when documents are re-assigned to same/other reviewer in an assignment,"
						+ " any previously saved persistent search hits in the assignment should be displayed in the assignment");

		// Login as RMU (Pre - Req)
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		sessionSearch.navigateToSessionSearchPageURL();
		int pureHit = sessionSearch.audioSearch(Input.audioSearch, Input.language);
		baseClass.waitForElement(sessionSearch.getDocsMetYourCriteriaLabel());
		baseClass.dragAndDrop(sessionSearch.getDocsMetYourCriteriaLabel(), sessionSearch.getActionPad());
		sessionSearch.bulkAssign_Persistant(true);
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignmentPage.addReviewerInManageReviewerTab();
		assignmentPage.distributeGivenDocCountToReviewers(Integer.toString(pureHit));
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		driver.waitForPageToBeReady();
		Dashboard dash = new Dashboard(driver);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		String hitscount = docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignmentPage.removeDocs(Input.rev1userName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignmentPage.reassignDocs(Input.rev1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");
		baseClass.passedStep(
				"Verified - Audio Documents assigned from Advanced search- Verify that when documents are re-assigned to same/other reviewer in an assignment, "
						+ "any previously saved persistent search hits in the assignment should be displayed in the assignment");
		loginPage.logout();

	}

	/**
	 * @author N/A
	 * @Description :To verify Audio Documents assigned from Saved Search group-
	 *              Verify that when documents are re-assigned to same/other
	 *              reviewer in an assignment," + " any previously saved persistent
	 *              search hits in the assignment should be displayed in the
	 *              assignment {RPMXCON-51768]
	 */
	@Test(description = "RPMXCON-51768", enabled = true, groups = { "regression" })
	public void verifyPersisForAudioReAssignSameRevSrcGrp() throws InterruptedException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String audioSearch = Input.audioSearch.toUpperCase();

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SavedSearch search = new SavedSearch(driver);
		baseClass.stepInfo("RPMXCON-51768");
		baseClass.stepInfo(
				"To verify Audio Documents assigned from Saved Search group- Verify that when documents are re-assigned to same/other reviewer in an assignment,"
						+ " any previously saved persistent search hits in the assignment should be displayed in the assignment");

		// Login as RMU (Pre - Req)
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		String searchName = "Search " + Utility.dynamicNameAppender();
		String newNode = search.createSearchGroupAndReturn(searchName, "RMU", "Yes");
		sessionSearch.navigateToSessionSearchPageURL();
		int pureHit = sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.saveSearchInNewNode(searchName, newNode);
		search.bulkAssignPersisSrcGrpSS(Input.mySavedSearch, true);
		assignmentPage.assignDocstoNewAssgn(assignmentName);
		assignmentPage.quickAssignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.saveAssignment(assignmentName, Input.codeFormName);
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignmentPage.addReviewerInManageReviewerTab();
		assignmentPage.distributeGivenDocCountToReviewers(Integer.toString(pureHit));
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		driver.waitForPageToBeReady();
		Dashboard dash = new Dashboard(driver);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		String hitscount = docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
		assignmentPage.removeDocs(Input.rev1userName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignmentPage.reassignDocs(Input.rev1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		docViewPage.getAudioPersistentHit(audioSearch);
		baseClass.compareTextViaContains(hitscount, audioSearch, "persistent search term is present in panel",
				"persistent search terms is Removed");
		baseClass.passedStep(
				"Verified - Audio Documents assigned from Saved Search group- Verify that when documents are re-assigned to same/other reviewer in an assignment, "
						+ "any previously saved persistent search hits in the assignment should be displayed in the assignment");
		loginPage.logout();
	}

	/**
	 * @author N/A
	 * @Description :To verify that Remark can be update by other reviewers in same
	 *              security group in audio doc view.[RPMXCON-51178]
	 */
	@Test(description = "RPMXCON-51178", enabled = true, groups = { "regression" })
	public void verifyRemarksUpdtByOthrRev() throws InterruptedException, Exception {

		DocViewPage docView = new DocViewPage(driver);

		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		Map<String, String> updateDatas = new HashMap<String, String>();

		String remarkText1 = Input.randomText + Utility.dynamicNameAppender();
		String remarkText2 = Input.randomText + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"To verify that Remark can be update by other reviewers in same security group in audio doc view.");
		baseClass.stepInfo("RPMXCON-51178");
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();
		docView.audioRemark(remarkText1);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev2userName, Input.rev2password);
		baseClass.stepInfo("Logged in As : " + Input.rev2userName);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getAudioReMarkEdit(remarkText1));
		docView.editAndVerifyData(remarkText1, updateDatas, remarkText2);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		SoftAssert asserts = new SoftAssert();
		asserts.assertTrue(docView.getRemarkText(remarkText2).isElementAvailable(5));
		asserts.assertAll();

		baseClass.waitForElement(docView.getDocView_AudioRemark_DeleteButton());
		docView.getDocView_AudioRemark_DeleteButton().waitAndClick(5);

		baseClass.waitForElement(docView.getDocview_ButtonYes());
		asserts.assertTrue(docView.getDocview_ButtonYes().isElementAvailable(5));
		asserts.assertAll();
		docView.getDocview_ButtonNO().waitAndClick(5);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		asserts.assertTrue(docView.getRemarkText(remarkText2).isElementAvailable(5));
		asserts.assertAll();

		baseClass.waitForElement(docView.getDocView_AudioRemark_DeleteButton());
		docView.getDocView_AudioRemark_DeleteButton().waitAndClick(5);

		baseClass.waitForElement(docView.getDocview_ButtonYes());
		docView.getDocview_ButtonYes().waitAndClick(5);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		asserts.assertFalse(docView.getRemarkText(remarkText2).isElementAvailable(5));
		asserts.assertAll();
		baseClass.passedStep(
				"To verify that Remark can be update by other reviewers in same security group in audio doc view.");
		loginPage.logout();
	}

	/**
	 * @author N/A
	 * @Description :To Verify that user can add remarks upto 1000 characters in
	 *              audio doc view..[RPMXCON-51177]
	 */
	@Test(description = "RPMXCON-51177", enabled = true, dataProvider = "RmuRev", groups = { "regression" })
	public void verifyRemarks1000chars(String username, String password, String fullname) throws InterruptedException, Exception {
		DocViewPage docView = new DocViewPage(driver);

		SoftAssert asserts = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);

		String remarkText1 = Input.randomText + Utility.dynamicNameAppender();
		String remarkText2 = Input.randomText + Utility.randomCharacterAppender(1000);

		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("To Verify that user can add remarks upto 1000 characters in audio doc view.");
		baseClass.stepInfo("RPMXCON-51177");
		baseClass.stepInfo("Logged in As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();
		docView.audioRemark(remarkText1);
		asserts.assertTrue(docView.getRemarkText(remarkText1).isElementAvailable(5));
		asserts.assertAll();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.audioRemark(remarkText2);
		baseClass.passedStep("Verified that user can add remarks upto 1000 characters in audio doc view.");
		loginPage.logout();

	}

	/**
	 * @author N/A
	 * @Description :Verify navigating to the audio document should bring up the
	 *              document in 4 sec " + "and ready for the user to act up on when
	 *              navigating from DocList")[RPMXCON-51527]
	 */
	@Test(description = "RPMXCON-51527", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyAudioDocBringUp4Sec(String username, String password, String fullname) throws Exception {
		DocListPage doclist = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("RPMXCON-51527");
		baseClass.stepInfo("Verify navigating to the audio document should bring up the document in 4 sec "
				+ "and ready for the user to act up on when navigating from DocList");

		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		baseClass.waitForElement(sessionSearch.getDocsMetYourCriteriaLabel());
		baseClass.dragAndDrop(sessionSearch.getDocsMetYourCriteriaLabel(), sessionSearch.getActionPad());
		sessionSearch.ViewInDocList();
		doclist.selectAllDocs();
		driver.scrollPageToTop();
		baseClass.waitForElement(doclist.getDocList_actionButton());
		doclist.getDocList_actionButton().waitAndClick(5);

		baseClass.waitForElement(doclist.getViewInDocView());
		doclist.getViewInDocView().waitAndClick(5);
		long start = System.currentTimeMillis();

		driver.waitForPageToBeReady();
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;

		if (totalTime < 4000) {
			baseClass.passedStep("Audio doc view bring up the document in 4 sec when navigating from doclist");
		} else {
			baseClass.failedStep("Audio doc view NOT bring up the document in 4 sec when navigating from doclist");
		}
		baseClass.passedStep("Verified - navigating to the audio document should bring up the document in 4 sec "
				+ "and ready for the user to act up on when navigating from DocList");
		loginPage.logout();
	}

	/**
	 * @author N/A
	 * @Description :Verify that application should not hang when the user tries to
	 *              " + "add a new reviewer remark, when the audio is in stopped
	 *              state.[RPMXCON-51468]
	 */
	@Test(description = "RPMXCON-51468", enabled = true, dataProvider = "RmuRev", groups = { "regression" })
	public void verifyAppNotHangNewReviewWhenStoppedState(String username, String password) throws Exception {
		String remarkText = Input.randomText + Utility.dynamicNameAppender();

		DocViewPage docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("RPMXCON-51468");
		baseClass.stepInfo("Verify that application should not hang when the user tries to "
				+ "add a new reviewer remark, when the audio is in stopped state");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_AudioPause());
		docView.getDocView_AudioPause().waitAndClick(3);

		baseClass.waitForElement(docView.getDocView_IconStop());
		docView.getDocView_IconStop().waitAndClick(3);

		docView.audioRemark(remarkText);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		SoftAssert asserts = new SoftAssert();
		asserts.assertTrue(docView.RevRemarkNotchinPlayer().isElementAvailable(5));
		asserts.assertTrue(docView.getRemarkText(remarkText).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo("Notch Displaying As Expected After Added Remark..");
		baseClass.stepInfo("Remark Successfully Added in Remarks Panel..");
		baseClass.waitForElement(docView.getDocView_AudioPause());
		if (docView.getDocView_AudioPause().isElementAvailable(5)) {
			baseClass.passedStep("Audio Player Is In Stopped State..");
		} else {
			baseClass.failedStep("Audio Player Not In Stopped State..");
		}
		baseClass.passedStep("Verify that application should not hang when the user tries to "
				+ "add a new reviewer remark, when the audio is in stopped state");
		loginPage.logout();
	}

	/**
	 * @author N/A
	 * @Description :Verify that application should not hang when the user tries to
	 *              " + "edit/delete a reviewer remark, when the audio is in paused
	 *              state.[RPMXCON-51467]
	 */
	@Test(description = "RPMXCON-51467", enabled = true, dataProvider = "RmuRev", groups = { "regression" })
	public void verifyAppNotHangtheAudioInPause(String username, String password, String fullname) throws Exception {
		String remarkText = Input.randomText + Utility.dynamicNameAppender();
		String editedRemark = Input.randomText + Utility.dynamicNameAppender();
		Map<String, String> updateDatas = new HashMap<String, String>();

		DocViewPage docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("RPMXCON-51467");
		baseClass.stepInfo("To Verify that application should not hang when the user tries to "
				+ "edit/delete a reviewer remark, when the audio is in paused state.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();
		docView.audioRemark(remarkText);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_AudioPause());
		docView.getDocView_AudioPause().waitAndClick(3);

		baseClass.waitForElement(docView.getDocView_AudioPlay());
		docView.getDocView_AudioPlay().waitAndClick(3);

		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getAudioReMarkEdit(remarkText));
		docView.editAndVerifyData(remarkText, updateDatas, editedRemark);
		docView.deleteExistingRemark();
		SoftAssert asserts = new SoftAssert();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		asserts.assertFalse(docView.RevRemarkNotchinPlayer().isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo("After deleted Remarks Notch Removed From the Player as Expected...");
		baseClass.waitForElement(docView.getDocView_AudioPause());
		if (docView.getDocView_AudioPause().isElementAvailable(5)) {
			baseClass.passedStep("Audio Player Is In Pause State..");
		} else {
			baseClass.failedStep("Audio Player Not In Pause State..");
		}
		baseClass.passedStep("Verified - that application should not hang when the user tries to "
				+ "edit/delete a reviewer remark, when the audio is in paused state.");
		loginPage.logout();
	}

	/**
	 * @author N/A
	 * @Description :Verify that application should not hang when the user tries to
	 *              " + "edit/delete a reviewer remark, when the audio is in Stopped
	 *              state.[RPMXCON-51469]
	 */
	@Test(description = "RPMXCON-51469", enabled = true, dataProvider = "RmuRev", groups = { "regression" })
	public void verifyAppNotHangWhenStoppedState(String username, String password) throws Exception {
		String remarkText = Input.randomText + Utility.dynamicNameAppender();
		String editedRemark = Input.randomText + Utility.dynamicNameAppender();
		Map<String, String> updateDatas = new HashMap<String, String>();

		DocViewPage docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("RPMXCON-51469");
		baseClass.stepInfo("To Verify that application should not hang when the user tries to "
				+ "edit/delete a reviewer remark, when the audio is in Stopped state.");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();
		docView.audioRemark(remarkText);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getDocView_AudioPause());
		docView.getDocView_AudioPause().waitAndClick(3);

		baseClass.waitForElement(docView.getDocView_IconStop());
		docView.getDocView_IconStop().waitAndClick(3);

		baseClass.waitForElement(docView.getAdvancedSearchAudioRemarkIcon());
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getAudioReMarkEdit(remarkText));
		docView.editAndVerifyData(remarkText, updateDatas, editedRemark);
		docView.deleteExistingRemark();

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		SoftAssert asserts = new SoftAssert();
		asserts.assertFalse(docView.RevRemarkNotchinPlayer().isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo("After deleted Remarks Notch Removed From the Player as Expected...");
		baseClass.waitForElement(docView.getDocView_AudioPause());
		if (docView.getDocView_AudioPause().isElementAvailable(5)) {
			baseClass.passedStep("Audio Player Is In Stopped State..");
		} else {
			baseClass.failedStep("Audio Player Not In Stopped State..");
		}
		baseClass.passedStep("Verified - that application should not hang when the user tries to "
				+ "edit/delete a reviewer remark, when the audio is in Stopped state.");
		loginPage.logout();
	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-51788
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with same term and different/same threshold are
	 *              assigned to assignment from saved search with search group > Doc
	 *              List
	 */
	@Test(description = "RPMXCON-51788", enabled = true, groups = { "regression" })
	public void verifyAudioHitsDocsSearchedAssignmentSavedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51788");
		baseClass.stepInfo(

				"Verify that audio hits should be displayed when documents searched with same term and different/same threshold are assigned to assignment from saved search with search group > Doc List");
		SessionSearch sessionsearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewPage= new DocViewPage(driver);

		String[] assignmentname = { "assgnment1" + Utility.dynamicNameAppender(),
				"assgnment2" + Utility.dynamicNameAppender() };
		String[] searchname = { "search1" + Utility.dynamicNameAppender(), "search2" + Utility.dynamicNameAppender() };

		for (int i = 0; i < searchname.length; i++) {
			// Login as RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
			sessionsearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
			sessionsearch.addPureHit();
			sessionsearch.saveSearch(searchname[i]);
			saveSearch.SaveSearchToBulkAssign(searchname[i], assignmentname[i], Input.codeFormName,
					SessionSearch.pureHit);
			loginPage.logout();

			// Login as REVU
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer Manager with "
					+ Input.rev1userName + "");

			// Select the Assignment from dashboard
			assignmentsPage.SelectAssignmentByReviewer(assignmentname[i]);
			baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

			// verifying the audio hits and triangular arrow Icon
			baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
			docViewPage.getAudioPersistantHitEyeIcon().Click();
			docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);
			baseClass.passedStep("Triangular arrow icon is displayed in jplayer at the position.");
			loginPage.logout();

		}

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-51789
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with common terms and different/same threshold are
	 *              assigned to assignment from saved search with search group
	 */
	@Test(description = "RPMXCON-51789", enabled = true, groups = { "regression" })
	public void verifyAudioHitsDocsAssignmentSavedSearchWithSearchGroup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51789");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common terms and different/same threshold are assigned to assignment from saved search with search group");
		SessionSearch sessionsearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewPage= new DocViewPage(driver);

		String[] assignmentname = { "assgnment1" + Utility.dynamicNameAppender(),
				"assgnment2" + Utility.dynamicNameAppender() };
		String[] searchname = { "search1" + Utility.dynamicNameAppender(), "search2" + Utility.dynamicNameAppender() };

		for (int i = 0; i < searchname.length; i++) {
			// Login as RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
			sessionsearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
			sessionsearch.addPureHit();
			sessionsearch.saveSearch(searchname[i]);
			saveSearch.SaveSearchToBulkAssign(searchname[i], assignmentname[i], Input.codeFormName,
					SessionSearch.pureHit);
			loginPage.logout();

			// Login as REVU
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer Manager with "
					+ Input.rev1userName + "");

			// Select the Assignment from dashboard
			assignmentsPage.SelectAssignmentByReviewer(assignmentname[i]);
			baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

			// verifying the audio hits and triangular arrow Icon
			baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
			docViewPage.getAudioPersistantHitEyeIcon().Click();
			docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);
			baseClass.passedStep("Triangular arrow icon is displayed in jplayer at the position.");
			driver.Navigate().refresh();
			baseClass.waitTime(5);
			docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
			baseClass.passedStep(
					"Documents common in both searches term is displayed on the hits panel and icon on the jplayer");
			loginPage.logout();
		}

	}

	/**
	 * @author:Krishna date: NA Modified date: NA Modified by: NA TestCase ID:
	 *                 RPMXCON-51790
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with common terms and different/same threshold are
	 *              assigned to assignment from saved search with search group > Doc
	 *              List
	 */
	@Test(description = "RPMXCON-51790", enabled = true, groups = { "regression" })
	public void verifyAudioHitsDocsSearchedAssignmentSavedSearchDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51790");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common terms and different/same threshold are assigned to assignment from saved search with search group > Doc List");
		SessionSearch sessionsearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewPage= new DocViewPage(driver);

		String[] assignmentname = { "assgnment1" + Utility.dynamicNameAppender(),
				"assgnment2" + Utility.dynamicNameAppender() };
		String[] searchname = { "search1" + Utility.dynamicNameAppender(), "search2" + Utility.dynamicNameAppender() };

		for (int i = 0; i < searchname.length; i++) {
			// Login as RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
			sessionsearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
			sessionsearch.addPureHit();
			sessionsearch.saveSearch(searchname[i]);
			saveSearch.SaveSearchToBulkAssign(searchname[i], assignmentname[i], Input.codeFormName,
					SessionSearch.pureHit);
			loginPage.logout();

			// Login as REVU
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("User successfully logged into slightline webpage as Reviewer Manager with "
					+ Input.rev1userName + "");

			// Select the Assignment from dashboard
			assignmentsPage.SelectAssignmentByReviewer(assignmentname[i]);
			baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

			// verifying the audio hits and triangular arrow Icon
			baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
			docViewPage.getAudioPersistantHitEyeIcon().Click();
			docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);
			baseClass.passedStep(
					"Searched in common terms in docview Triangular arrow icon is displayed in jplayer at the position.");
			loginPage.logout();

		}

	}

	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-51807
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel on completing the document.
	 */
	@Test(description = "RPMXCON-51807", enabled = true, groups = { "regression" })
	public void verifyAudioDocsHitSaveSeachesWithCommonTerm() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51807");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel on completing the document.");
		sessionSearch = new SessionSearch(driver);
		docViewPage= new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		int purehit = sessionSearch.audioSearch(audioSearch, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docViewPage.getMiniDocListDocIdText());

		// Audio search And Save
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);

		// Added another Audio search and save
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString3, Input.language);
		sessionSearch.addPureHit();
		sessionSearch.saveSearch(searchName1);
		// Click on bulkAssign
		sessionSearch.bulkAssignWithOutPureHit();

		// Create Assigment
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  REV as with " + Input.rev1userName + "");

		// Assignment Selection and Reviewer
		assignmentPage.SelectAssignmentByReviewer(Asssignment);

		// Check Display persistant hit - notrepetative
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();

		// Complete the document And Navigate >>
		docViewPage.editingCodingFormWithCompleteButton();
		baseClass.waitForElement(docViewPage.getDocView_Last());
		docViewPage.getDocView_Last().waitAndClick(5);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify the automatically selection of the redaction tag for
	 *              audio redaction when Project Admin impersonates as RMU/Reviewer
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52013", enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTagForAudioDocsAsPA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52013 - DocViewAudio");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag for audio redaction when Project Admin impersonates as RMU/Reviewer");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonatePAtoRMU();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// logout
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonatePAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify the automatically selection of the redaction tag for
	 *              audio redaction when Domain Admin impersonates as RMU/Reviewer
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52012", enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTagForAudioDocsAsDA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52012 - DocViewAudio");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag for audio redaction when Domain Admin impersonates as RMU/Reviewer");

		// Login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.da1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateDAtoRMU();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep("'Default Redaction Tag' is saved for the redaction successfully");

		// logout
		loginPage.logout();

		// Login as DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.da1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateDAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);
		baseClass.passedStep("'Default Redaction Tag' is saved for the redaction successfully");
		// logout
		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify the automatically selection of the redaction tag for
	 *              audio redaction when System Admin impersonates as RMU/Reviewer
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52011", enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTagForAudioDocsAsSA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52011 - DocViewAudio");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag for audio redaction when System Admin impersonates as RMU/Reviewer");

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateDAtoRMU();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// logout
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateDAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRange(Input.defaultRedactionTag, 1, 2);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for SA user after impersonation as RMU/Reviewer
	 *              should allow to select only one redaction tag for audio
	 *              redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52003", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsAsSA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52003 - DocViewAudio");
		baseClass.stepInfo(
				"Verify that for SA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		String annotationLayerName = "AudioRedaction" + Utility.randomCharacterAppender(3);

		// Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);

		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateSAtoRMU();

		// Navigate to Advance Search Page and search for audio docs

		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for SA user after impersonation as RMU/Reviewer
	 *              should allow to select only one redaction tag for audio
	 *              redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52004", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsWithEditAsSA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52004 - DocViewAudio");
		baseClass.stepInfo(
				"Verify that for SA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		String annotationLayerName = "AudioRedaction" + Utility.randomCharacterAppender(3);

		// Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);

		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateSAtoRMU();

		// Navigate to Advance Search Page and search for audio docs

		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithEditRedactionTag(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.sa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithEditRedactionTag(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for DA user after impersonation as RMU/Reviewer
	 *              should allow to select only one redaction tag for audio
	 *              redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52005", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsAsDA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52005 - DocViewAudio");
		baseClass.stepInfo(
				"Verify that for DA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		String annotationLayerName = "AudioRedaction" + Utility.randomCharacterAppender(3);

		// Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);

		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.da1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateDAtoRMU();

		// Navigate to Advance Search Page and search for audio docs

		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.da1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		loginPage.logout();

	}

	/**
	 * @Author Mohan Venugopal
	 * @Description : Verify that for PA user after impersonation as RMU/Reviewer
	 *              should allow to select only one redaction tag for audio
	 *              redaction
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52007", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationUserShouldAllowOneRedactionTagForAudioDocsAsPA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52007 - DocViewAudio");
		baseClass.stepInfo(
				"Verify that for PA user after impersonation as RMU/Reviewer should allow to select only one redaction tag for audio redaction");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		String annotationLayerName = "AudioRedaction" + Utility.randomCharacterAppender(3);

		// Pre-requsite.. Add an AnnotatioLayer under SG
		AnnotationLayer annotationLayer = new AnnotationLayer(driver);
		annotationLayer.AddAnnotation(annotationLayerName);

		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateDAtoRMU();

		// Navigate to Advance Search Page and search for audio docs

		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Impersonate from PA to RMU and Reviewer
		baseClass.impersonateSAtoReviewer();

		// Navigate to Advance Search Page and search for audio docs
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		sessionSearch.ViewInDocViews();

		// Click On redaction tag for the selected audio docs
		docViewPage.audioRedactionUsingAudioRangeWithIndex(1, 1, 2);
		baseClass.passedStep("Only one redaction tag is selected for the redaction");

		// logout
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// Pre-requsite.. Add an AnnotatioLayer under SG
		annotationLayer.deleteAnnotationByPagination(annotationLayerName);

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:27/09/2022 RPMXCON-51794
	 * @throws Exception
	 * @Description Verify that when document present in different save searches
	 *              with common term then, should not display repetitive search term
	 *              on persistent hits panel from assignment.
	 */
	@Test(description = "RPMXCON-51794", enabled = true, groups = { "regression" })
	public void verifyAudioDocsDifferentSaveSearchWithCommonTerm() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51794");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, should not display repetitive search term on persistent hits panel from assignment.");
		SessionSearch sessionsearch = new SessionSearch(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewPage= new DocViewPage(driver);

		String assignmentname = "assgnment1" + Utility.dynamicNameAppender();
		String searchname = "search1" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.rmu1FullName);
		sessionsearch.verifyaudioSearchWarning(Input.audioSearchString1, Input.language);
		sessionsearch.addPureHit();
		sessionsearch.saveSearch(searchname);
		saveSearch.SaveSearchToBulkAssign(searchname, assignmentname, Input.codeFormName, SessionSearch.pureHit);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assignmentname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// verifying the audio hits
		driver.waitForPageToBeReady();
		docViewPage.verifyingAudioPersistantHitPanel(Input.audioSearchString1);
		baseClass.passedStep(
				"Documents common in both searches term is displayed on the hits panel and icon on the jplayer");
		loginPage.logout();

	}

	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-51671
	 * @throws Exception
	 * @Description Delete the remarks for the audio documents in Docview
	 */
	@Test(description = "RPMXCON-51671", enabled = true, groups = { "regression" })
	public void verifyDeleteRemarksAudioDocDocview() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51671");
		baseClass.stepInfo("Update the Remarks for the existing audio documents");
		sessionSearch = new SessionSearch(driver);
		docViewPage= new DocViewPage(driver);
		String remark = "Remark" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		// adding remarks and verifying success message
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);

		docViewPage.getAdvancedSearchAudioRemarkPlusIcon().waitAndClick(5);
		docViewPage.audioRemarkDataInput(remark, "Success");
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docViewPage.getDeleteRedaction(remark));
		docViewPage.getDeleteRedaction(remark).waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocview_ButtonNO());
		docViewPage.getDocview_ButtonNO().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean redactionTrue = docViewPage.getDeleteRedaction(remark).isElementAvailable(4);
		System.out.println(redactionTrue);
		softAssertion.assertTrue(redactionTrue);
		baseClass.passedStep("All the settings remain as expected");
		driver.waitForPageToBeReady();
		docViewPage.deleteRemark(remark);
		softAssertion.assertAll();
		loginPage.logout();

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		// click on remarks button
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkIcon());
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionFalse = docViewPage.getDeleteRedaction(remark).isElementAvailable(4);
		System.out.println(radctionFalse);
		softAssertion.assertFalse(radctionFalse);
		softAssertion.assertAll();

	}

	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-51176
	 * @throws Exception
	 * @Description Verfiy that RMU can view the reviewer remarks in audio doc view.
	 */
	@Test(description = "RPMXCON-51176", enabled = true, groups = { "regression" })
	public void verifyRmuViewReviewerRemarksInAudio() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51176");
		baseClass.stepInfo("Update the Remarks for the existing audio documents");
		sessionSearch = new SessionSearch(driver);
		docViewPage= new DocViewPage(driver);
		String remark = "Remark" + Utility.dynamicNameAppender();
		String remark1 = "Remark" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		// verify adding remarks
		driver.waitForPageToBeReady();
		softAssertion.assertTrue(docViewPage.getAdvancedSearchAudioRemarkIcon().isElementPresent());
		baseClass.passedStep("Remarks tab is displayed as expected");
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkIcon());
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkPlusIcon());
		docViewPage.getAdvancedSearchAudioRemarkPlusIcon().waitAndClick(5);
		docViewPage.audioRemarkDataInput(remark, "Success");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// verify reviewer remark
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkIcon());
		docViewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionTrue = docViewPage.getDeleteRedaction(remark).isElementAvailable(4);
		softAssertion.assertTrue(radctionTrue);
		baseClass.passedStep("Existing reviewer remark is displayed as expected");
		baseClass.waitForElement(docViewPage.getAdvancedSearchAudioRemarkPlusIcon());
		softAssertion.assertTrue(docViewPage.getAdvancedSearchAudioRemarkPlusIcon().isElementPresent());
		baseClass.passedStep(" + sign is displayed successfully");
		docViewPage.getAdvancedSearchAudioRemarkPlusIcon().waitAndClick(5);
		docViewPage.audioRemarkDataInput(remark1, "Success");
		baseClass.passedStep("Record added Successfully");
		softAssertion.assertAll();
		driver.waitForPageToBeReady();
		docViewPage.deleteRemark(remark);
		docViewPage.deleteRemark(remark1);
	}

	/**
	 * Author : Baskar date: NA Modified date: 20/09/2022 Modified by: Baskar
	 * Description:Verify the Audio document is Saved, request should be complete
	 * immediately
	 * 
	 */

	@Test(description = "RPMXCON-51827", dataProvider = "RmuRev", enabled = true, groups = { "regression" })
	public void validateAudioDocsSavedRequest(String userName, String password, String fullName)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51827");
		baseClass.stepInfo("Verify the Audio document is Saved, request should be complete immediately");
		String codingform = "cf" + Utility.dynamicNameAppender();
		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		// search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		docViewPage.editCodingForm(codingform);
		docViewPage.codingFormSaveButton();
		String actual = docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(actual, codingform);
		baseClass.stepInfo("Saving the codingform");
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		boolean readyStateComplete = (boolean) jse.executeScript("return document.readyState").toString()
				.equals("complete");
		softAssertion.assertTrue(readyStateComplete);
		baseClass.passedStep("Coding form get saved and page loaded immediateley");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 09//2022 Modified by: Baskar
	 * Description:Verify that when audio file is playing and clicked to apply the
	 * stamp, then waveform should be loaded [Less than 1 hr audio file]
	 * 
	 */

	@Test(description = "RPMXCON-51822", enabled = true, groups = { "regression" })
	public void validatePlayIconCF() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51822");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to apply the stamp, "
				+ "then waveform should be loaded [Less than 1 hr audio file]");

		String Asssignment = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stamp" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// pre-requisties saving the stamp
		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(stamp, Input.stampSelection);

		// verifying more than one hour audio docs
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) <= 01) {
			baseClass.stepInfo("Audio document duration is lesser than one hour");
		} else {
			baseClass.failedMessage("More than one hour");
		}
		// playing audio file
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);

		// click on the saved stamp
		docViewPage.lastAppliedStamp(Input.stampSelection);
		baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
		String actual = docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(actual, comment);
		baseClass.passedStep("Saved value in stamp are retained");
		docViewPage.codingFormSaveButton();
		baseClass.stepInfo("Document saved successfully");

		// validation for waveform
		baseClass.waitForElement(docViewPage.getAudioWaveForm());
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @author Krishna ModifyDate: RPMXCON-51503
	 * @throws Exception
	 * @Description Verify after saving the coding stamp should not add any
	 *              additional controls for speed up/speed down.
	 */
	@Test(description = "RPMXCON-51503", enabled = true, groups = { "regression" })
	public void verifyAfterSavingCodingStampNotAddAdditionalControl() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51503");
		baseClass.stepInfo(
				"Verify after saving the coding stamp should not add any additional controls for speed up/speed down.");
		sessionSearch = new SessionSearch(driver);
		docViewPage= new DocViewPage(driver);
		String assign = "Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// search to Assignment creation
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm();
		docViewPage.perfromCodingStampSelection(Input.stampColours);
		if (docViewPage.getDocView_AudioPause().isElementPresent()) {
			baseClass.passedStep("After saving the coding stamp speed up/speed down control not be added ");

		} else {
			baseClass.failedStep("Control is added");
		}
		docViewPage.deleteStampColour(Input.stampColours);
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm();
		docViewPage.perfromCodingStampSelection(Input.stampColours);
		if (docViewPage.getDocView_AudioPause().isElementPresent()) {
			baseClass.passedStep("After saving the coding stamp speed up/speed down control not be added ");

		} else {
			baseClass.failedStep("Control is added");
		}
		docViewPage.deleteStampColour(Input.stampColours);

	}

	/**
	 * @author Krishna ModifyDate: RPMXCON-51784
	 * @throws Exception
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with same term and different/same threshold are
	 *              assigned to assignment from session search
	 */
	@Test(description = "RPMXCON-51784", enabled = true, groups = { "regression" })
	public void verifyAudioHitsDisplayedDiffSameThresholdToAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51784");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with same term and different/same threshold are assigned to assignment from session search");
		sessionSearch = new SessionSearch(driver);
		docViewPage= new DocViewPage(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String assignmentName = "assgnAudio" + Utility.dynamicNameAppender();
		String assgName = "assgnaudio" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as RMU");

		// First audio Search
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.toggleEnableAnalyticsPanel();
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigate to Docview Page");

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.stepInfo("Eyeicon is successfully clicked");
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString2);
		baseClass.passedStep(
				"Triangular arrow icon is displayed in jplayer at the position where the search term occurs.");
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as RMU");

		// audio Search
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgName, Input.codeFormName);
		assignmentPage.toggleEnableAnalyticsPanel();
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgName);
		driver.waitForPageToBeReady();
		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigate to Docview Page");

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.stepInfo("Eyeicon is successfully clicked");
		docViewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString2);
		baseClass.passedStep(
				"Triangular arrow icon is displayed in jplayer at the position where the search term occurs.");

	}

	/**
	 * @author Krishna ModifyDate: RPMXCON-51825
	 * @throws Exception
	 * @Description Verify that when audio file is playing and clicked to apply the
	 *              stamp, then waveform should be loaded [Greater than 1 hr audio
	 *              file] from coding form child window
	 */
	@Test(description = "RPMXCON-51825", enabled = true, groups = { "regression" })
	public void verifyAudioFilePlayingApplyStampGreaterThan1hrChildWindow() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51825");
		baseClass.stepInfo(
				"Verify that when audio file is playing and clicked to apply the stamp, then waveform should be loaded [Greater than 1 hr audio file] from coding form child window");
		sessionSearch = new SessionSearch(driver);
		docViewPage= new DocViewPage(driver);
		String assign = "Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String comment = "comment" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assign, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(assign);

		driver.waitForPageToBeReady();
		docViewPage.clickGearIconOpenMiniDocList();
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.Navigate().refresh();
		baseClass.handleAlert();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.passedStep("Audio file is played successfully");
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Coding form and mini doc list and codingform child window is opened");

		docViewPage.switchToNewWindow(2);
		baseClass.waitTime(4);
		docViewPage.editCodingForm();
		baseClass.waitForElement(docViewPage.getCodingFormStampButton());
		docViewPage.getCodingFormStampButton().waitAndClick(10);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		docViewPage.popUpAction(comment, Input.stampSelection);
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCodingStampLastIcon(Input.stampSelection));
		docViewPage.getCodingStampLastIcon(Input.stampSelection).waitAndClick(10);
		docViewPage.getCodingFormSaveThisForm().waitAndClick(5);
		baseClass.stepInfo("Coding form saved with stamp and document saved successfully");
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		if (docViewPage.getDocView_AudioPlay().isElementPresent()) {
			baseClass.passedStep("Document in play mode");

		} else {
			baseClass.failedStep("failed to play");
		}
		// checking zoom in function working for more than one hour audio docs
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// verifying waveform after zoom
		boolean waveforms = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");
		docViewPage.switchToNewWindow(2);

		// clicking the saved stamp
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCodingStampLastIcon(Input.stampSelection));
		docViewPage.getCodingStampLastIcon(Input.stampSelection).waitAndClick(10);
		docViewPage.getCodingFormSaveThisForm().waitAndClick(5);
		baseClass.stepInfo("Coding form saved with stamp and document saved successfully");
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);

		// validating audio is still playing after zoom
		boolean audioPlays = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");
		softAssertion.assertAll();
	}

	/**
	 * @author Aathith Date: 08/17/2022
	 * @Description Verify user can see the metada for the audio files
	 */
	@Test(description = "RPMXCON-51075", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void verifyAudioRedactionTagReleasedToSG(String username, String password) throws Exception {

		sessionSearch = new SessionSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert soft = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51075");
		baseClass.stepInfo("Verify user can see the metada for the audio files");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// view in docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		baseClass.stepInfo(Input.audioSearchString1 + " audio document is searched");
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("page is in DocView");

		// verify metadata is dispalyed
		soft.assertTrue(docview.getDocView_Analytics_MetaDataPanel().isDisplayed());
		baseClass.passedStep("Metadata was displayed for audio files on audio doc view");

		soft.assertAll();
		baseClass.passedStep("Verified user can see the metada for the audio files");
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * 
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with same term and different/same threshold are
	 *              assigned to assignment at different time from session search
	 * @throws InterruptedException
	 * 
	 */
	@Test(description = "RPMXCON-51782", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdInSessionSearchBulkAssign2() throws InterruptedException {

		docViewPage= new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51782");
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with same term and "
				+ "different/same threshold are assigned to assignment at different time from session search");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as RMU");

		String searchString1 = Input.audioSearchString3;

		String searchName = "Audio" + Utility.dynamicNameAppender();
		String searchName1 = "Audio" + Utility.dynamicNameAppender();

		String searchName2 = "Audio" + Utility.dynamicNameAppender();
		String searchName3 = "Audio" + Utility.dynamicNameAppender();

		baseClass.stepInfo(
				"**Search Node creation for saving two  audio serches with same term/same threshold scenario**");
		// Create Node
		baseClass.stepInfo("Node (or) New Search Group creation");
		String newNode = savedsearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		// First audio Search
		sessionSearch.audioSearch(searchString1, Input.language);
		sessionSearch.saveSearchInNewNode(searchName, newNode);
		baseClass.stepInfo("**first search creation same term/same threshold[deafult-70]**");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.stepInfo("**second  search creation same as first search  term/ threshold[deafult-70] **");
		// second audio search
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearch(searchString1, Input.language);
		sessionSearch.saveSearchInNewNode(searchName1, newNode);

		baseClass.stepInfo("**Navigating to Saved saerch>select node>view in doc view**");
		savedsearch.navigateToSSPage();
		savedsearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
		savedsearch.selectNode1(newNode);
		driver.scrollPageToTop();
		savedsearch.getToDocViewoption().ScrollTo();
		savedsearch.getToDocView().ScrollTo();
		savedsearch.getviewindocview().Click();
		
	
		
		baseClass.stepInfo(
				"** verifying the audio hits and triangular arrow Icon for two searches with same serach term/same threshold saved in a node**");
		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString1);

		baseClass.stepInfo(
				"**search node creation saving two  audio serches with same term/different threshold scenario** **");
		// Create Node
		baseClass.stepInfo("Node (or) New Search Group creation");
		String newNode_differentThreshld = savedsearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "Yes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.clickOnNewSearch();
		baseClass.stepInfo("**first  search creation same  search  term/ different threshold[max] **");

		// First audio Search
		sessionSearch.newAudioSearchThreshold(searchString1, Input.language, "max");
		sessionSearch.saveSearchInNewNode(searchName3, newNode_differentThreshld);
		driver.getWebDriver().get(Input.url + "Search/Searches");

		baseClass.stepInfo("**second  search creation same as first search  term/ different thresholdmin] **");
		// second audio search
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString1, Input.language, "min");
		sessionSearch.saveSearchInNewNode(searchName2, newNode_differentThreshld);

		baseClass.stepInfo("**Navigating to Saved search>select node>view in doc view**");
		savedsearch.navigateToSSPage();
		savedsearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
		savedsearch.selectNode1(newNode_differentThreshld);

		savedsearch.getToDocViewoption().ScrollTo();
		savedsearch.getToDocView().ScrollTo();
		savedsearch.getviewindocview().Click();
		baseClass.stepInfo(
				"** verifying the audio hits and triangular arrow Icon fro two searches with same serach term/different threshold saved in a node**");
		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString1.toLowerCase());

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51785", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdAndSearchTerms() throws InterruptedException {
		docViewPage= new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51785");
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with common terms and "
				+ "different/same threshold are assigned to assignment from session search");

		String assignmentName = "assgnAudio" + Utility.dynamicNameAppender();
		String assgName = "assgnaudio" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// Performing advanced search with audio common terms/same threshold
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		baseClass.elementDisplayCheck(sessionSearch.getCurrentPureHitAddBtn());
		baseClass.stepInfo("Search result is displayed");

		// add to shop cart
		sessionSearch.addPureHit();

		// perform new search
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString2, Input.audioSearchString3, "", Input.language);

		// add to shop cart
		sessionSearch.addPureHit();

		// creating Assignment with Audio Document
		sessionSearch.bulkAssign_Persistant(true);
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();

		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();

		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigate to Docview Page");

		// click eye icon and triangular arrow /search term hits display check
		docViewPage.verifyingAudioPersistantHitTerms(5, Input.audioSearchString2, Input.audioSearchString3);

		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// add new search with different threshold/common terms
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.language, "max");
		sessionSearch.addPureHit();

		// add new search with different threshold
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.audioSearchString3, "", Input.language,
				"min");
		sessionSearch.addPureHit();

		// creating Assignment with Audio Document
		sessionSearch.bulkAssign_Persistant(true);
		assignmentPage.assignmentCreation(assgName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgName);
		driver.waitForPageToBeReady();

		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigate to Docview Page");

		// click eye icon and triangular arrow display and persistent hits check
		docViewPage.verifyingAudioPersistantHitTerms(5, Input.audioSearchString2, Input.audioSearchString3);

		loginPage.logout();
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51792", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdAndSearchTerms_viaDocList() throws InterruptedException {
		docViewPage= new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		DocListPage docListPage = new DocListPage(driver);

		baseClass.stepInfo("Test case id :RPMXCON-51792");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common term and different/same"
						+ " threshold are assigned to assignment from session search > Doc List");

		String assignmentName = "assgnAudio" + Utility.dynamicNameAppender();
		String assgName = "assgnaudio" + Utility.dynamicNameAppender();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// Performing advanced search with audio common terms/same threshold
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		baseClass.elementDisplayCheck(sessionSearch.getCurrentPureHitAddBtn());
		baseClass.stepInfo("Search result is displayed");

		// add to shop cart
		sessionSearch.addPureHit();

		// perform new search
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearch(Input.audioSearchString2, Input.audioSearchString3, "", Input.language);

		// add to shop cart
		sessionSearch.addPureHit();
		// Session search>DocList>bulk assign
		sessionSearch.ViewInDocListWithOutPureHit();
		docListPage.DoclisttobulkAssign(null, "100");

		// creating Assignment with Audio Document
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();

		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();

		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigate to Docview Page");

		// click eye icon and triangular arrow /search term hits display check
		docViewPage.verifyingAudioPersistantHitTerms(5, Input.audioSearchString2, Input.audioSearchString3);

		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined user : " + Input.rmu1userName);

		// add new search with different threshold/common terms
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.language, "max");
		sessionSearch.addPureHit();

		// add new search with different threshold
		sessionSearch.addNewSearch();
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.audioSearchString3, "", Input.language,
				"min");
		sessionSearch.addPureHit();

		// Session serach>DocList>bulk assign
		sessionSearch.ViewInDocListWithOutPureHit();
		docListPage.DoclisttobulkAssign(null, "100");

		// creating Assignment with Audio Document
		assignmentPage.assignmentCreation(assgName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgName);
		driver.waitForPageToBeReady();

		// view selected documents from assignment in Docview
		baseClass.stepInfo("After selected  Assignment user navigate to Docview Page");

		// click eye icon and triangular arrow display and persistent hits check
		docViewPage.verifyingAudioPersistantHitTerms(5, Input.audioSearchString2, Input.audioSearchString3);

		loginPage.logout();
	}


	/**
	 * Author : Baskar date: NA Modified date: 29/09/2022 Modified by: Baskar
	 * Description:Verify user can see the transcript in audio doc view outside of
	 * an assignment
	 * 
	 */

	@Test(description = "RPMXCON-51123", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void validateTranscriptTabOutSideAssgn(String userName, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51123");
		baseClass.stepInfo("Verify user can see the transcript in audio doc view outside of an assignment");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		// Login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as '" + userName + "'");

		// search to docview
		sessionSearch.MetaDataSearchInBasicSearch("SourceDocID", Input.transcriptId);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("User navigated to audio docview page outside of assignment");

		// verify transcript tab display
		baseClass.waitForElement(docViewPage.getTranscriptsTab());
		boolean flagTans = docViewPage.getTranscriptsTab().isDisplayed();
		softAssertion.assertTrue(flagTans);
		baseClass.passedStep("Transcript tab displayed for audio docs");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 29/09/2022 Modified by: Baskar
	 * Description:Verify user can see the transcript in audio doc view in context
	 * of an assignment
	 * 
	 */

	@Test(description = "RPMXCON-51124", enabled = true, groups = { "regression" })
	public void validateTranscriptTabContextAssgn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51124");
		baseClass.stepInfo("Verify user can see the transcript in audio doc view in context of an assignment");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		String Asssignment = "Assignment" + Utility.dynamicNameAppender();

		// Login as
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as '" + Input.rmu1userName + "'");

		// search to docview
		sessionSearch.MetaDataSearchInBasicSearch("SourceDocID", Input.transcriptId);
		sessionSearch.bulkAssign();
		assignPage.assignmentCreation(Asssignment, Input.codingFormName);
		assignPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		docViewPage.selectAssignmentfromDashborad(Asssignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verify transcript tab display
		baseClass.waitForElement(docViewPage.getTranscriptsTab());
		boolean flagTans = docViewPage.getTranscriptsTab().isDisplayed();
		softAssertion.assertTrue(flagTans);
		baseClass.passedStep("Transcript tab displayed for audio docs");
		softAssertion.assertAll();
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
