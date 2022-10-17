package sightline.docviewAudio;

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

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
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

public class DocViewAudio_Regression1_22 {

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
		DocViewPage docViewPage = new DocViewPage(driver);

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
		DocViewPage docViewPage = new DocViewPage(driver);

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
		DocViewPage docViewPage = new DocViewPage(driver);

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
		baseClass.waitTillElemetToBeClickable(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().Click();

		// Complete the document And Navigate >>
		docviewPage.editingCodingFormWithCompleteButton();
		baseClass.waitForElement(docviewPage.getDocView_Last());
		docviewPage.getDocView_Last().waitAndClick(5);

		// logout
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
		DocViewPage docViewPage = new DocViewPage(driver);

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

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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
