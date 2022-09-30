package testScriptsRegressionSprint22;

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

public class DocViewAudio_Regression22 {
	
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

	}
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		docviewPage.editCodingForm();
		docviewPage.perfromCodingStampSelection(Input.stampColours);
		if (docviewPage.getDocView_AudioPause().isElementPresent()) {
			baseClass.passedStep("After saving the coding stamp speed up/speed down control not be added ");

		} else {
			baseClass.failedStep("Control is added");
		}
		docviewPage.deleteStampColour(Input.stampColours);
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login As Rev");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assign);
		driver.waitForPageToBeReady();
		docviewPage.editCodingForm();
		docviewPage.perfromCodingStampSelection(Input.stampColours);
		if (docviewPage.getDocView_AudioPause().isElementPresent()) {
			baseClass.passedStep("After saving the coding stamp speed up/speed down control not be added ");

		} else {
			baseClass.failedStep("Control is added");
		}
		docviewPage.deleteStampColour(Input.stampColours);

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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		baseClass.waitForElement(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.stepInfo("Eyeicon is successfully clicked");
		docviewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString2);
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
		baseClass.waitForElement(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		baseClass.stepInfo("Eyeicon is successfully clicked");
		docviewPage.verifyingThePresenceOfPersistentHit(true, Input.audioSearchString2);
		baseClass.passedStep(
				"Triangular arrow icon is displayed in jplayer at the position where the search term occurs.");

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
		this.driver.getWebDriver().get(Input.url + "Annotations/Annotations");
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
		loginPage.logout();

		// Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage.deleteSecurityGroups(sgName1);

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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		docviewPage.clickGearIconOpenMiniDocList();
		docviewPage.closeWindow(1);
		docviewPage.switchToNewWindow(1);
		driver.Navigate().refresh();
		baseClass.handleAlert();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.audioPlayPauseIcon());
		docviewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.passedStep("Audio file is played successfully");
		docviewPage.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Coding form and mini doc list and codingform child window is opened");

		docviewPage.switchToNewWindow(2);
		baseClass.waitTime(4);
		docviewPage.editCodingForm();
		baseClass.waitForElement(docviewPage.getCodingFormStampButton());
		docviewPage.getCodingFormStampButton().waitAndClick(10);
		docviewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		docviewPage.popUpAction(comment, Input.stampSelection);
		docviewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.getCodingStampLastIcon(Input.stampSelection));
		docviewPage.getCodingStampLastIcon(Input.stampSelection).waitAndClick(10);
		docviewPage.getCodingFormSaveThisForm().waitAndClick(5);
		baseClass.stepInfo("Coding form saved with stamp and document saved successfully");
		docviewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		if (docviewPage.getDocView_AudioPlay().isElementPresent()) {
			baseClass.passedStep("Document in play mode");

		} else {
			baseClass.failedStep("failed to play");
		}
		// checking zoom in function working for more than one hour audio docs
		docviewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docviewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// verifying waveform after zoom
		boolean waveforms = docviewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");
		docviewPage.switchToNewWindow(2);

		// clicking the saved stamp
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.getCodingStampLastIcon(Input.stampSelection));
		docviewPage.getCodingStampLastIcon(Input.stampSelection).waitAndClick(10);
		docviewPage.getCodingFormSaveThisForm().waitAndClick(5);
		baseClass.stepInfo("Coding form saved with stamp and document saved successfully");
		docviewPage.closeWindow(1);
		docviewPage.switchToNewWindow(1);

		// validating audio is still playing after zoom
		boolean audioPlays = docviewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");
		softAssertion.assertAll();
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
	 * @author Jayanthi.Ganesan
	 * 
	 * @Description Verify that audio hits should be displayed when documents
	 *              searched with same term and different/same threshold are
	 *              assigned to assignment at different time from session search
	 * @throws InterruptedException
	 * 
	 */
	@Test(description = "RPMXCON-51782", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdInSessionSearchBulkAssign() throws InterruptedException {

		DocViewPage docViewPage = new DocViewPage(driver);
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
		savedsearch.getToDocView().Click();
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
		savedsearch.getToDocView().Click();
		baseClass.stepInfo(
				"** verifying the audio hits and triangular arrow Icon fro two searches with same serach term/different threshold saved in a node**");
		// verifying the audio hits and triangular arrow Icon
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString1.toLowerCase());

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

		docViewPage = new DocViewPage(driver);
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
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51785", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdAndSearchTerms() throws InterruptedException {
		DocViewPage	docViewPage = new DocViewPage(driver);
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
		 sessionSearch.newAudioSearch(Input.audioSearchString2, Input.audioSearchString3,"", Input.language);

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
		docViewPage.verifyingAudioPersistantHitTerms(5,Input.audioSearchString2, Input.audioSearchString3);

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
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.audioSearchString3,
				"", Input.language, "min");
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


		// click eye icon and triangular arrow display  and persistent hits check
		docViewPage.verifyingAudioPersistantHitTerms(5,Input.audioSearchString2, Input.audioSearchString3);

		loginPage.logout();
	}
	
	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51792", enabled = true, groups = { "regression" })
	public void verifySameDifferentThresholdAndSearchTerms_viaDocList() throws InterruptedException {
		DocViewPage	docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		DocListPage docListPage= new DocListPage(driver);
		
		baseClass.stepInfo("Test case id :RPMXCON-51792");
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with common term and different/same"
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
		 sessionSearch.newAudioSearch(Input.audioSearchString2, Input.audioSearchString3,"", Input.language);

		// add to shop cart
		sessionSearch.addPureHit();
		//Session search>DocList>bulk assign
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
		docViewPage.verifyingAudioPersistantHitTerms(5,Input.audioSearchString2, Input.audioSearchString3);

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
		sessionSearch.newAudioSearchThreshold(Input.audioSearchString2, Input.audioSearchString3,
				"", Input.language, "min");
		sessionSearch.addPureHit();
		
		//Session serach>DocList>bulk assign
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


		// click eye icon and triangular arrow display  and persistent hits check
		docViewPage.verifyingAudioPersistantHitTerms(5,Input.audioSearchString2, Input.audioSearchString3);

		loginPage.logout();
	}
	

	@DataProvider(name = "PaRmuRev")
	public Object[][] PaRmuRev() {
		Object[][] users = {
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 29/09/2022 Modified by: Baskar
	 * Description:Verify user can see the transcript in audio doc view outside of an assignment
	 * 
	 */

	@Test(description = "RPMXCON-51123", dataProvider = "PaRmuRev", enabled = true, groups = { "regression" })
	public void validateTranscriptTabOutSideAssgn(String userName, String password,String fullName) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51123");
		baseClass.stepInfo("Verify user can see the transcript in audio doc view outside of an assignment");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion=new SoftAssert();

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
	 * Description:Verify user can see the transcript in audio doc view in context of an assignment
	 * 
	 */

	@Test(description = "RPMXCON-51124", enabled = true, groups = { "regression" })
	public void validateTranscriptTabContextAssgn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51124");
		baseClass.stepInfo("Verify user can see the transcript in audio doc view in context of an assignment");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignPage = new AssignmentsPage(driver);
		softAssertion=new SoftAssert();
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
