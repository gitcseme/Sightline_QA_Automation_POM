package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression23 {

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
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53767
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              Un-release multiple documents from some security group from
	 *              DocList page from Current search.
	 */
	@Test(description = "RPMXCON-53767", enabled = true, groups = { "regression" })
	public void verifyAsPAInDocListPageUnreleaseDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53767");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to Un-release multiple documents from some security group from DocList page from Current search.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.documentSelection(5);
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("Documents will Un-release from security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53769
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              Un-release all documents from security group from DocList page
	 *              from Current search.
	 */
	@Test(description = "RPMXCON-53769", enabled = true, groups = { "regression" })
	public void verifyAsPAInDocListPageSelectAllDocsUnreleaseDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53769");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to Un-release all documents from security group from DocList page from Current search.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.selectAllDocs();
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("All Documents will Un-release from security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53786
	 * @throws Exception
	 * @Description To verify, as an Reviewer user login, I can select multiple
	 *              document from Doc list & I can Tag all the selected documents.
	 */
	@Test(description = "RPMXCON-53786", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectMultipleDocInDocListInTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53786");
		baseClass.stepInfo(
				"To verify, as an Reviewer user login, I can select multiple document from Doc list & I can Tag all the selected documents.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String tagName = "tag" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Select Tag Classification");
		loginPage.logout();
		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.documentSelection(5);
		// BulkTag
		sessionSearch.bulkTagExisting(tagName);
		baseClass.passedStep("Tag will apply on the selected documents Successfully");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/10/2022 RPMXCON-53892
	 * @throws Exception
	 * @Description To verify, As an Reviewer user login, I will be able to perform
	 *              all actions from Doc List page, when I will go from Saved search
	 *              to Doc List.
	 */
	@Test(description = "RPMXCON-53892", enabled = true, groups = { "regression" })
	public void verifyAsReviewerPerformAllActinsFromDocLsitPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53892");
		baseClass.stepInfo(
				"To verify, As an Reviewer user login, I will be able to perform all actions from Doc List page, when I will go from Saved search to Doc List.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocViewPage docview=new DocViewPage(driver);
		String tagName = "tag" + UtilityLog.dynamicNameAppender();
		String folderName = "folder" + UtilityLog.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		// Save searched content
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		// select multiple Documents and bulktag
		docList.documentSelection(3);
		sessionSearch.bulkTagExisting(tagName);
		baseClass.stepInfo("DocListpage Action Bulktag is created");
		// select multiple Documents and bulkfolder
		driver.waitForPageToBeReady();
		docList.documentSelection(3);
		docList.bulkFolderExisting(folderName);
		baseClass.stepInfo("DocListpage Action Bulkfolder is created");
		// filter action
		driver.waitForPageToBeReady();
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		baseClass.stepInfo("DocListpage Action filters is performed");
		// select multiple Documents and bulkfolder
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will be able to perform all Doc List actions Successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/10/2022 RPMXCON-54283
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the waveform from audio player for the audio files less
	 *              than 1 hour from preview document of doc list.
	 */

	@Test(description = "RPMXCON-54283", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyWaveFormAudioPlayerInperviewDocumentInDocList(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54283");
		baseClass.stepInfo(
				"Verify the waveform from audio player for the audio files less than 1 hour from preview document of doc list");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocListPage docList = new DocListPage(driver);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocList();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getDocListPerviewBtn());
		docList.getDocListPerviewBtn().waitAndClick(5);

		// verifying more than one hour audio docs
		driver.waitForPageToBeReady();
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) >= 01) {
			baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
		} else {
			baseClass.failedMessage("Lesser than one hour");
		}

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
	 * @authorBrundha TestCase id:RPMXCON-53785
	 * @Description To verify, As a Reviewer user login, I am able to remove all
	 *              documents from Folder in Doc list page
	 */
	@Test(description = "RPMXCON-53785", enabled = true, groups = { "regression" })
	public void verifyingTheUnFolderCountInDocListPage() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String foldername = "Folder" + UtilityLog.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-53785 DocList Component");
		baseClass.stepInfo(
				"To verify, As a Reviewer user login, I am able to remove all documents from Folder in Doc list page");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.CreateFolder(foldername, Input.securityGroup);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		baseClass.ValidateElement_Presence(docList.getBackToSourceBtn(), "DocList Page");
		docList.documentSelection(3);

		baseClass.stepInfo("Unfoldering the Document");
		sessionSearch.bulkUnFolder(foldername);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// verifying document removed from folder
		tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		tf.verifyFolderDocCount(foldername, 0);

		loginPage.logout();

	}

	/**
	 * @authorBrundha TestCase id:RPMXCON-53659
	 * @Description To Verify, As a Reviewer user login, In Doc List page maximum
	 *              500 Documents will show per page
	 */
	@Test(description = "RPMXCON-53659", enabled = true, groups = { "regression" })
	public void verifyingTheDocumentCountInDocListPage() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53659 DocList Component");
		baseClass.stepInfo(
				"To Verify, As a Reviewer user login, In Doc List page maximum 500 Documents will show per page");

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting page length in doclist page");
		docList.getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(Input.pageLength);
		driver.scrollingToBottomofAPage();
		driver.scrollingToElementofAPage(docList.getTableFooterDocListCount());
		driver.waitForPageToBeReady();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String Document = doccount[3];
		System.out.println("doclist page document count is" + Document);
		baseClass.textCompareEquals(Input.pageLength, Document, Input.pageLength + "is displayedas expected",
				Input.pageLength + "is not displayed as expected");
		loginPage.logout();
	}

	/**
	 * @authorBrundha TestCase id:RPMXCON-54276
	 * @Description To verify that if the filters include both "Include" and
	 *              "Exclude", then the results should include the docs that match
	 *              the "Include" criteria but does not match the "Exclude"
	 *              criteria.
	 */
	@Test(description = "RPMXCON-54276", enabled = true, groups = { "regression" })
	public void verifyingIncludedFilterInDocListPage() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54276 DocList Component");
		baseClass.stepInfo(
				"To verify that if the filters include both 'Include' and 'Exclude', then the results should include the docs that match the 'Include' criteria but does not match the 'Exclude' criteria.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		
		baseClass.stepInfo("selecting the column");
		docList.SelectColumnDisplay(docList.getSelectAvailMetadata(Input.MetaDataEAName));
		
		baseClass.stepInfo("applying include filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		driver.waitForPageToBeReady();
		
		baseClass.stepInfo("applying Exclude filter and verifying");
		docList.EmailAuthorNameInExcludeVerificationInDoc();
		
		baseClass.stepInfo("verifying include filter");
		docList.verifyAppliedIncludeCustodianNameFilterIsAdded(Input.metaDataCN);
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53881
	 * @throws Exception
	 * @Description To verify, As an RM user login, When I will apply some filter on
	 *              Doc List and then select some documents after filtration, I will
	 *              be able to go to Doc View page.
	 */
	@Test(description = "RPMXCON-53881", enabled = true, groups = { "regression" })
	public void verifyAsRMUSomeFilterInDocListGotoDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53881");
		baseClass.stepInfo(
				"To verify, As an RM user login, When I will apply some filter on Doc List and then select some documents after filtration, I will be able to go to Doc View page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// filter action
		driver.waitForPageToBeReady();
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		baseClass.stepInfo("DocListpage Action filters is performed");

		// select multiple Documents
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will land in Doc View page with selected documents Successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53880
	 * @throws Exception
	 * @Description To verify, As an Project admin user login, When I will apply
	 *              some filter on Doc List and then select some documents after
	 *              filtration, I will be able to go to Doc View page.
	 */
	@Test(description = "RPMXCON-53880", enabled = true, groups = { "regression" })
	public void verifyAsPASomeFilterInDocListGotoDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53880");
		baseClass.stepInfo(
				"To verify, As an Project admin user login, When I will apply some filter on Doc List and then select some documents after filtration, I will be able to go to Doc View page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// filter action
		driver.waitForPageToBeReady();
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		baseClass.stepInfo("DocListpage Action filters is performed");

		// select multiple Documents
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will land in Doc View page with selected documents Successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53839
	 * @throws Exception
	 * @Description To verify, As an Reviewer user login, I can be able to go to Doc
	 *              List page from saved search page after selecting any saved query
	 *              from Shared with me & apply action Document List from View
	 *              Group.
	 */
	@Test(description = "RPMXCON-53839", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSavedSearchGoToDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53839");
		baseClass.stepInfo(
				"To verify, As an Reviewer user login, I can be able to go to Doc List page from saved search page after selecting any saved query from Shared with me & apply action Document List from View Group.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");
		
		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);
		//verify doclist page
		if(docList.getDocList_info().isDisplayed()) {
			baseClass.passedStep("User will land in Document List page from saved search page Successsfully");
		}
		else {
			baseClass.failedStep("User will not land in Document List page from saved search page");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53840
	 * @throws Exception
	 * @Description To verify, As an PAU user login, able to go to Doc List page from saved search page from <Shared With ProjectAdministrator>/<Shared with Security Group Name> & apply action Document List from View Group.
	 */
	@Test(description = "RPMXCON-53840", enabled = true, groups = { "regression" })
	public void verifyAsPASavedSearchGoToDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53840");
		baseClass.stepInfo(
				"To verify, As an PAU user login, able to go to Doc List page from saved search page from <Shared With ProjectAdministrator>/<Shared with Security Group Name> & apply action Document List from View Group.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");
		
		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.saveSearchSharedWithPA(searchName1);

		// Open Doc list from Saved search page
		savedSearch.saveSearchSharedWithPAToDocList(searchName1);
		//verify doclist page
		if(docList.getDocList_info().isDisplayed()) {
			baseClass.passedStep("User will land in Document List page from saved search page Successsfully");
		}
		else {
			baseClass.failedStep("User will not land in Document List page from saved search page");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:12/10/2022 RPMXCON-54521
	 * @throws Exception
	 * @Description Validate onpage filter for EmailRecipientNames with any special
	 *              charatcers (,/"/-/_ /) on DocList page.
	 */
	@Test(description = "RPMXCON-54521", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterForEmailRecipientNameWithAnySpecialCharaters(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54521");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames with any special charatcers (,/\"/-/_ /) on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String domain1 = "(#NOS OCRM All OCRM Staff);Jeff Smith";
		String domain2="Amol.Gawande/,@symp";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// EmailRecipientnames Include
		baseClass.stepInfo("EmailRecipientnames Include");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailRecNameFilter());
		docList.getEmailRecNameFilter().waitAndClick(5);
		docList.include(domain1);
		driver.waitForPageToBeReady();
		if(baseClass.text(domain1).isDisplayed()) {
		baseClass.passedStep("Documents containing only the selected email IDs only filtered");
		}else {
			baseClass.failedStep("Documents containing selected email IDs not filtered");
		}

		docList.getClearAllBtn().waitAndClick(5);
		baseClass.stepInfo("ClearAll Button Is clicked");
		// EmailRecipientnames Exclude
		baseClass.stepInfo("EmailRecipientnames Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailRecNameFilter());
		docList.getEmailRecNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain2);
		softAssertion.assertTrue(docList.getDocListNoRestultData().isDisplayed());
		baseClass.passedStep("Documents containing the selected email IDs should not be filtered");
		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/10/2022 RPMXCON-54383
	 * @throws Exception
	 * @Description Validate doc List limitation from Saved Search - View less than
	 *              or equal to 500K documents.
	 */
	@Test(description = "RPMXCON-54383", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyDoclistLimitationSavedSearchLessThanDocuments(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54383");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames with any special charatcers (,/\"/-/_ /) on DocList pageValidate doc List limitation from Saved Search - View less than or equal to 500K documents.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		int pureHit = 0;

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		pureHit = Integer.parseInt(sessionSearch.getPureHitsCount2ndSearch().getText());
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		/// Go to doclist verify doc count
		driver.waitForPageToBeReady();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String Document = doccount[3];
		System.out.println("doclist page document count is" + Document);
		baseClass.textCompareEquals(Document, Document, pureHit + "is displayed as expected",
				pureHit + "is not displayed as expected");
		baseClass.passedStep(
				"User not be prompted with any message and navigate successfully to DocList screen with selected number of documents.");

		loginPage.logout();
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
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}

}
