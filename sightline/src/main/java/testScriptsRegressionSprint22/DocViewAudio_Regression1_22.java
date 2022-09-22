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
		List<String> searchTerm = new ArrayList<String>();

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
		docviewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm);

		//Advanced  Audio search1
		sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
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
		assignmentPage.assignmentCreation(Assignname, Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.selectAssignmentToViewinDocView(Assignname);

		// Check Display persistant hit - notrepetative
		docviewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		docviewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm);

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
		List<String> searchTerm = new ArrayList<String>();

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
		docviewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm);

		// Complete the document And SameAs Last
		docviewPage.editingCodingFormWithCompleteButton();
		docviewPage.perfromLastCodeSameAsIcon();

		// Check Display persistant hit - notrepetative
		docviewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 1));
		driver.waitForPageToBeReady();
		docviewPage.verifyingAudioPersistantHitPanelWithMoreThanOneSearcTerm(searchTerm);
		docviewPage.editingCodingFormWithCompleteButton();

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
