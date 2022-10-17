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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		if (docviewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
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
		if (docviewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
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
		if (docviewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
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
		if (docviewPage.getAdvancedSearchAudioRemarkIcon().Displayed()) {
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
		doclist.selectingAllDocFromAllPagesAndAllChildren();
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
		doclist.selectingAllDocFromAllPagesAndAllChildren();
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
	 * Description:Verify that when audio file is playing and clicked to Save the document, 
	 * then waveform should be loaded [Less than 1 hr audio file] from coding form child window
	 */

	@Test(description = "RPMXCON-51820", enabled = true, groups = { "regression" })
	public void validatePlayIconInCfChild() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51820");
		baseClass.stepInfo("Verify that when audio file is playing and clicked to Save the document, "
				+ "then waveform should be loaded [Less than 1 hr audio file] from coding form child window");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		DocViewPage docViewPage = new DocViewPage(driver);
		SoftAssert softAssertion=new SoftAssert();
		sessionSearch = new SessionSearch(driver);

		String comment = "comments" + Utility.dynamicNameAppender();

		// search to Assignment creation
		int audioPurehit = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview with audio docs: " + audioPurehit + " document");

		driver.waitForPageToBeReady();
		String oneHourDocs=docViewPage.getVerifyPrincipalDocument().getText();

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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		baseClass.waitForElementCollection(docviewPage.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docviewPage.getMiniDocListDocIdText());

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
		docviewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().Click();
		docviewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString3);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.bulkAssign();

		// Select Assignment goto docview
		assignmentPage.assignmentCreation(Assignname, Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(Assignname);

		// Check Display persistant hit - notrepetative
		docviewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().Click();
		docviewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString3);

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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		baseClass.waitForElementCollection(docviewPage.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docviewPage.getMiniDocListDocIdText());

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

		baseClass.stepInfo("Impersnated from RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		// Assignment Selection and Reviewer
		assignmentPage.SelectAssignmentByReviewer(Asssignment);

		// Check Display persistant hit - notrepetative
		docviewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().Click();
		docviewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString1);

		// Complete the document And SameAs Last
		docviewPage.editingCodingFormWithCompleteButton();

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

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}
	@DataProvider(name = "AllTheUsers")
    public Object[][] AllTheUsers() {
        Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
                { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
                { Input.rev1userName, Input.rev1password, Input.rev1FullName } };
        return users;
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
