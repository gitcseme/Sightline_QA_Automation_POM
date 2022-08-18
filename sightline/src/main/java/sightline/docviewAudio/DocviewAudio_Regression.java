package sightline.docviewAudio;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
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

public class DocviewAudio_Regression {

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
	RedactionPage redactionPage;
	MiniDocListPage miniDocListpage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
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
	
	/**
	 * @author Baskar
	 * @Description Verify that audio hits should be displayed when documents searched with common terms and different/same 
	 *              threshold navigated to doc view from session search > DocList
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51786", enabled = true,dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyPersistentHits_CommonTerms(String username, String password, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51786");
		baseClass.stepInfo(
				"Verify that audio hits should be displayed when documents searched with common term and different/same "
						+ "threshold navigated to doc view from session search > DocList");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Successfully login as '" + username);

		String searchString = Input.audioSearchString2;
		String audioSearchInput1 = "interview six";

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docListPage = new DocListPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		// First audio Search
		sessionSearch.audioSearch(searchString, Input.language);
		sessionSearch.getPureHitAddButton().waitAndClick(10);
		baseClass.stepInfo("Doing first search tems");

		// second audio search
		sessionSearch.clickOnNewSearch();
		baseClass.stepInfo("Doing second search tems");
		sessionSearch.audioSearchTwoTimesandAddingTwoPureHits(audioSearchInput1, Input.language);
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);
		baseClass.stepInfo("Navigating to doclist page");
		

		// view All audio Docs in DocList
		sessionSearch.ViewInDocListWithOutPureHit();

		// selecting all documents in DocList
		docListPage.selectingAllDocuments();
		baseClass.stepInfo("Selecting all document from doclist to docview page");

		// view selected documents in DocList
		docListPage.viewSelectedDocumentsInDocView();
		baseClass.stepInfo("Verify that audio hits should be displayed when documents searched with common term and "
				+ "same threshold navigated to doc view from session search > DocList");
		
		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().Click();
		docViewPage.verifyingThePresenceOfPersistentHit(true, searchString);

		// removing the pure Hits in Selected Result
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.removePureHitsFromSelectedResult();

		// First audio search term with max threshold value
		baseClass.stepInfo("Doing first search tems with maximum thersold");
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(searchString, Input.language, "max");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// second audio search with same term and min threshold value
		baseClass.stepInfo("Doing first search tems with minimum thersold");
		sessionSearch.clickOnNewSearch();
		sessionSearch.newAudioSearchThreshold(audioSearchInput1, Input.language, "min");
		sessionSearch.getCurrentPureHitAddBtn().waitAndClick(10);

		// view All audio Docs in DocList
		sessionSearch.ViewInDocList();

		// selecting all documents in DocList
		docListPage.selectingAllDocuments();
		baseClass.stepInfo("Selecting all document from doclist to docview page from different thersold");

		baseClass.stepInfo("Verifying audio hits  displayed when documents searched with common term and different"
				+ "threshold navigated to doc view from session search > DocList");
		// view selected documents in DocList
		docListPage.viewSelectedDocumentsInDocView();

		// verifying the audio hits and triangular arrow Icon
		baseClass.waitTillElemetToBeClickable(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		docViewPage.verifyingThePresenceOfPersistentHit(true, audioSearchInput1);
		loginPage.logout();

	}
	
	/**
	 * @author Baskar date: 19/07/22 Modified date: NA Modified by: NA 
	 * @Description : Verify the automatically selected audio redaction tag when shared annotation layer 
	 *                with un-shared redactation tags in security groups and all propogated documents are 
	 *                not released to security groups
	 * @throws Exception
	 * 
	 */
	@Test(description = "RPMXCON-52024", enabled = true, groups = { "regression" })
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

		baseClass.stepInfo("Test case id :RPMXCON-52024");
		baseClass.stepInfo(
				"Verify the automatically selected audio redaction tag when shared annotation "
				+ "layer with un-shared redactation tags in security groups and all propogated "
				+ "documents are not released to security groups");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);

		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkRelease(securityGroup);

		annotationLayer.AddAnnotation(addName);
		redact.AddRedaction(RedactName, Input.rev1FullName);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		baseClass.waitForElement(securityGroupsPage.selectSGFromDropdown(securityGroup));
		securityGroupsPage.selectSGFromDropdown(securityGroup).waitAndClick(10);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(addName);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(RedactName);
		loginPage.logout();

		// Login as USER
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as : " + Input.rev1FullName);
		baseClass.selectsecuritygroup(securityGroup);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		System.out.println(firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(RedactName);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, RedactName,
				"After Save : Customized Redaction Tag is displayed", "After Save : invalid redaction tag selected");

		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();

		// Login as USER
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as : " + Input.rev1FullName);
		baseClass.selectsecuritygroup(Input.securityGroup);

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);
		System.out.println(firnstDocname);

		// adding redactions
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab());
		docViewPage.getDocview_RedactionsTab().waitAndClick(10);
		baseClass.waitForElement(docViewPage.getDocview_RedactionsTab_Add());
		docViewPage.getDocview_RedactionsTab_Add().waitAndClick(10);

		// Check Default Selection
		String defautTagSelections = baseClass.getCurrentDropdownValue(docViewPage.getDocview_AudioRedactions());
		baseClass.textCompareEquals(defautTagSelections, Input.defaultRedactionTag,
				"In default : Application automatically selected the ‘Default Redaction Tag’",
				"In default : invalid redaction tag selected");
		
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();

		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 19/07/2022 Modified by: Baskar
	 * Description:Verify that when document present in different save searches 
	 *             with common term then, should not display repetitive search term 
	 *             on persistent hits panel on completing the document after applying stamp
	 * 
	 */
	@Test(description = "RPMXCON-51809", enabled = true, groups = { "regression" })
	public void verifyPersistentFromSaveSearchToAssgn() throws InterruptedException, ParseException {
		baseClass = new BaseClass(driver);
		docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		assignmentPage=new AssignmentsPage(driver);
		String commonDocName = "";
		Set<String> hash_Set = new HashSet<String>();
		List<String> docIDlist = new ArrayList<>();
		List<String> docIDlistT = new ArrayList<>();
		String audioSearchInput = "interview";
		String audioSearchInput1 = "interview six";
		String[] stringDatas = { audioSearchInput, audioSearchInput1 };
		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String SearchName1 = "SearchName" + Utility.dynamicNameAppender();
		String assignmentName = "Assign" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-51809");
		baseClass.stepInfo(
				"Verify that when document present in different save searches with common term then, "
				+ "should not display repetitive search term on persistent hits panel on "
				+ "completing the document after applying stamp");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

		// Audio Search
		int purehit=sessionSearch.audioSearch(audioSearchInput, Input.language);
		sessionSearch.saveSearch(SearchName);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();

		// Main method
		docIDlist = miniDocListpage.getDocListDatas();
		hash_Set = baseClass.addListIntoSet(docIDlist);
		baseClass.selectproject();

		// Audio Search
		sessionSearch.audioSearch(audioSearchInput1, Input.language);
		sessionSearch.saveSearch(SearchName1);


		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();

		// Main method
		docIDlistT = miniDocListpage.getDocListDatas();
		baseClass.stepInfo("Select a document present in both the searches ");
		commonDocName = docViewPage.pickFirstDuplicate(docIDlistT, hash_Set);

		// Launch DocVia via Saved Search
		saveSearch.savedSearch_Searchandclick(SearchName);
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.assignmentDistributingToReviewerManager();
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_Searchandclick(SearchName1);
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assignmentPage.assignDocstoExisting(assignmentName);
		driver.waitForPageToBeReady();
		
		//impersonating to reviewer
		baseClass.impersonateRMUtoReviewer();
		
		// selecting assignment reviewer dashboard
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		
		// Persistant data to check
		miniDocListpage.getDocListDatas();
		baseClass.waitForElement(miniDocListpage.getDociD(commonDocName));
		miniDocListpage.getDociD(commonDocName).waitAndClick(10);
		driver.waitForPageToBeReady();
		docViewPage.clickClockIconMiniDocList();
		baseClass.waitForElement(docViewPage.getDocView_CurrentDocId());
		String docID = docViewPage.getDocView_CurrentDocId().getText();
		baseClass.stepInfo("Current viewed document : " + docID);
		baseClass.textCompareEquals(commonDocName, docID,
				"User on audio doc view and selected document is same as on audio doc view", "Audio DocView failed");
		baseClass.waitForElement(docViewPage.getAudioPersistantHitEyeIcon());
		docViewPage.getAudioPersistantHitEyeIcon().waitAndClick(10);
		driver.waitForPageToBeReady();

		// verifySearchTermlist
		docViewPage.verifySearchTermlist(stringDatas, "equalsP", 1,
				"search term not been displayed repetitively on persistent hit panel",
				"search term displayed repetitively on persistent hit panel");
		
		// complete the document
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String secondID=docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(secondID, docID);
		baseClass.passedStep("Document navigated to next docs from minidoclist");
		softAssertion.assertAll();

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
		System.out.println("******TEST CASES FOR DocView_Audio EXECUTED******");
	}
	}

