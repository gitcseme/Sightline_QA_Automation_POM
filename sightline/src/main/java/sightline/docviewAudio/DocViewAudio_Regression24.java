package sightline.docviewAudio;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pageFactory.Dashboard;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
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

public class DocViewAudio_Regression24 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	AssignmentsPage assignmentPage;
	DocViewPage docViewPage;

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
		loginPage = new LoginPage(driver);

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

	@DataProvider(name = "Allusers")
	public Object[][] AlluserLoginDetails() {
		return new Object[][] {{ Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "RMUandREV")
	public Object[][] RMUandREV() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.rev1userName, Input.rev1password } };
		return users;
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
		assignmentPage = new AssignmentsPage(driver);
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
		assignmentPage = new AssignmentsPage(driver);
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
		assignmentPage = new AssignmentsPage(driver);
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
	@Test(description = "RPMXCON-51177", enabled = true, dataProvider = "RMUandREV", groups = { "regression" })
	public void verifyRemarks1000chars(String username, String password) throws InterruptedException, Exception {
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
	@Test(description = "RPMXCON-51527", enabled = true, dataProvider = "Allusers", groups = { "regression" })
	public void verifyAudioDocBringUp4Sec(String username, String password) throws Exception {
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
	@Test(description = "RPMXCON-51468", enabled = true, dataProvider = "RMUandREV", groups = { "regression" })
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
	@Test(description = "RPMXCON-51467", enabled = true, dataProvider = "RMUandREV", groups = { "regression" })
	public void verifyAppNotHangtheAudioInPause(String username, String password) throws Exception {
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
	@Test(description = "RPMXCON-51469", enabled = true, dataProvider = "RMUandREV", groups = { "regression" })
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
		String audioSearch = Input.audioSearchString2 + Input.audioSearchString3;

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		int purehit = sessionSearch.audioSearch(Input.audioSearchString2, Input.language);
		sessionSearch.viewInDocView();
		baseClass.waitForElementCollection(docviewPage.getMiniDocListDocIdText());
		List<String> DocIDInMiniDocList = baseClass.availableListofElements(docviewPage.getMiniDocListDocIdText());
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
		docviewPage.selectDocIdInMiniDocList(DocIDInMiniDocList.get(purehit - 5));
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docviewPage.getAudioPersistantHitEyeIcon());
		docviewPage.getAudioPersistantHitEyeIcon().Click();
		docviewPage.verifyAudioPersistanHitNotDisplayed(Input.audioSearchString2);
		int searchterm = docviewPage.getSearchTermList(Input.audioSearchString3).size();
		if (searchterm <= 1) {
			baseClass.passedStep("repetitive search term is not displayed");
		} else {
			baseClass.failedStep("repetitive search term is displayed");
		}

		// logout
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
		DocViewPage docviewPage = new DocViewPage(driver);
		String remark = "Remark" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Audio search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		// adding remarks and verifying success message
		docviewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);

		docviewPage.getAdvancedSearchAudioRemarkPlusIcon().waitAndClick(5);
		docviewPage.audioRemarkDataInput(remark, "Success");
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docviewPage.getDeleteRedaction(remark));
		docviewPage.getDeleteRedaction(remark).waitAndClick(5);
		baseClass.waitForElement(docviewPage.getDocview_ButtonNO());
		docviewPage.getDocview_ButtonNO().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean redactionTrue = docviewPage.getDeleteRedaction(remark).isElementAvailable(4);
		System.out.println(redactionTrue);
		softAssertion.assertTrue(redactionTrue);
		baseClass.passedStep("All the settings remain as expected");
		driver.waitForPageToBeReady();
		docviewPage.deleteRemark(remark);
		softAssertion.assertAll();
		loginPage.logout();

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		// click on remarks button
		baseClass.waitForElement(docviewPage.getAdvancedSearchAudioRemarkIcon());
		docviewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionFalse = docviewPage.getDeleteRedaction(remark).isElementAvailable(4);
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
		DocViewPage docviewPage = new DocViewPage(driver);
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
		softAssertion.assertTrue(docviewPage.getAdvancedSearchAudioRemarkIcon().isElementPresent());
		baseClass.passedStep("Remarks tab is displayed as expected");
		baseClass.waitForElement(docviewPage.getAdvancedSearchAudioRemarkIcon());
		docviewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		baseClass.waitForElement(docviewPage.getAdvancedSearchAudioRemarkPlusIcon());
		docviewPage.getAdvancedSearchAudioRemarkPlusIcon().waitAndClick(5);
		docviewPage.audioRemarkDataInput(remark, "Success");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// verify reviewer remark
		baseClass.waitForElement(docviewPage.getAdvancedSearchAudioRemarkIcon());
		docviewPage.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		boolean radctionTrue = docviewPage.getDeleteRedaction(remark).isElementAvailable(4);
		softAssertion.assertTrue(radctionTrue);
		baseClass.passedStep("Existing reviewer remark is displayed as expected");
		baseClass.waitForElement(docviewPage.getAdvancedSearchAudioRemarkPlusIcon());
		softAssertion.assertTrue(docviewPage.getAdvancedSearchAudioRemarkPlusIcon().isElementPresent());
		baseClass.passedStep(" + sign is displayed successfully");
		docviewPage.getAdvancedSearchAudioRemarkPlusIcon().waitAndClick(5);
		docviewPage.audioRemarkDataInput(remark1, "Success");
		baseClass.passedStep("Record added Successfully");
		softAssertion.assertAll();
		driver.waitForPageToBeReady();
		docviewPage.deleteRemark(remark);
		docviewPage.deleteRemark(remark1);
	}

}