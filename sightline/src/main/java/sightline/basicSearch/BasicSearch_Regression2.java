package sightline.basicSearch;

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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearch_Regression2 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
	//	Input in = new Input();
	//	in.loadEnvConfig();

	}

	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "“ProximitySearch (Truthful OR Recall)”~9" },
				{ "\"ProximitySearch (Truthful OR Recall)\"~9" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity having 2 words with
	 *              OR in Basic Search Query Screen.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57303", enabled = true, dataProvider = "data", groups = { "regression" })
	public void verifyResultForTwoWords(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57303 Basic Search");
		base.stepInfo("Verify that result appears for proximity having 2 words with OR in Basic Search Query Screen.");

		// Verify Expanded Query
		session.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		base.waitForElement(session.getYesQueryAlert());
		session.getYesQueryAlert().waitAndClick(10);

		// verify session search Page
		session.returnPurehitCount();
		session.verifySessionSearchPageUrl();

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that UnFolder works properly using Bulk Folder Action
	 *              in Basic Search Screen
	 * @param data1
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57186", enabled = true, groups = { "regression" })
	public void verifyPhraseForSearch1() throws InterruptedException {
		String folder = "FOLDER" + Utility.dynamicNameAppender();

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57186 Basic Search");
		base.stepInfo("Verify that UnFolder works properly using Bulk Folder Action in Basic Search Screen");

		// perform bulk folder
		int purehit = session.basicContentSearch(Input.searchString5);
		session.bulkFolder(folder);

		// verify doc count after bulk folder
		TagsAndFoldersPage tagsAndFolder = new TagsAndFoldersPage(driver);
		tagsAndFolder.navigateToTagsAndFolderPage();
		base.stepInfo("navigated to Tags & folder page");
		tagsAndFolder.selectingFolderAndVerifyingTheDocCount(folder, purehit);

		// unfolder doc in session search page
		session.navigateToSessionSearchPageURL();
		base.stepInfo("navigated to session search page");
		session.bulkUnFolder(folder);

		// verify doc count after bulk unfolder
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.selectingFolderAndVerifyingTheDocCount(folder, 0);

		// delet folder
		tagsAndFolder.deleteAllFolders(folder);

		softAssertion.assertAll();
		login.logout();
	}

	@DataProvider(name = "dataSearch")
	public Object[][] dataSearch() {
		return new Object[][] { { "Precision AND (ProximitySearch Truthful~5)" },
				{ "Precision AND (ProximitySearch Truthful ~5)" }, { "Precision AND (ProximitySearch Truthful~5 )" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message when user configured Proximity
	 *              without (proper) double quotes and combined with other criteria
	 *              in Basic Search Query Screen.
	 * @param data
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57296", enabled = true, dataProvider = "dataSearch", groups = { "regression" })
	public void verifyBandMsgWithoutDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57296 Basic Search");
		base.stepInfo(
				"Verify that belly band message when user configured Proximity without (proper) double quotes and combined with other criteria in Basic Search Query Screen.");

		// Verify belly band message Query
		session.wrongQueryAlertBasicSaerch(data, 13, "non fielded", null);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that basic search for Comment is working properly
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56997", enabled = true, groups = { "regression" })
	public void verifyBasicSearchComment() throws InterruptedException {
		String docComment1 = "Reviewed" + Utility.dynamicNameAppender();
		String docComment2 = "Reviewed on 09-20-2009 " + Utility.dynamicNameAppender();
		String docComment3 = "Morning" + Utility.dynamicNameAppender();
		String regularExp = "\"##Reviewed on [0-9]{2}-[0-9]{2}-[0-9]{4}\"";
		int count = 1;
		DocViewPage docview = new DocViewPage(driver);

		// login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-56997 Basic Search");
		base.stepInfo("Verify that basic search for Comment is working properly");

		// configure metadata query and add comment
		session.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		session.viewInDocView();
		docview.addCommentAndSave(docComment1, true, count);

		// verify comment in session search page
		base.selectproject();
		int PureHit = session.getCommentsOrRemarksCount(Input.documentComments, docComment1);
		softAssertion.assertEquals(count, PureHit);

		// configure query and add comment
		base.selectproject();
		session.basicContentSearch(Input.searchString5);
		session.viewInDocView();
		docview.addCommentAndSave(docComment2, true, count);

		// verify comment for regular exp
		base.selectproject();
		int PureHit2 = session.getCommentsOrRemarksCount(Input.documentComments, regularExp);

		// configure audio doc and add comment
		base.selectproject();
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.viewInDocView();
		docview.addCommentAndSave(docComment3, true, count);

		// verify comment
		base.selectproject();
		int PureHit3 = session.getCommentsOrRemarksCount(Input.documentComments, docComment3);
		softAssertion.assertEquals(count, PureHit3);

		// verify comment in advance search page
		base.selectproject();
		session.getCommentsOrRemarksCount_AdvancedSearch(Input.documentComments, docComment1);
		base.selectproject();
		session.getCommentsOrRemarksCount_AdvancedSearch(Input.documentComments, regularExp);
		base.selectproject();
		session.getCommentsOrRemarksCount_AdvancedSearch(Input.documentComments, docComment3);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that added reviewer remarks for Non-audio documents is
	 *              working correctly in Basic Search. [RPMXCON-46879]
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-46879", enabled = true, groups = { "regression" })
	public void addRemarkForNonAudio() throws ParseException, Exception {
		DocViewPage docView = new DocViewPage(driver);
		String remark = "Reviewed" + Utility.dynamicNameAppender();
		int count = 1;

		// login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-46879 Basic Search");
		base.stepInfo(
				"Verify that added reviewer remarks for Non-audio documents is working correctly in Basic Search.");

		// configure metadata query and add remark
		session.basicContentSearch(Input.searchString2);
		session.viewInDocView();
		docView.addRemarkByText(remark);

		// verify Remark in session search page
		base.selectproject();
		int PureHit = session.getCommentsOrRemarksCount("Remark", remark);
		softAssertion.assertEquals(count, PureHit);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Modified reviewer remarks for Non-audio documents
	 *              is working correctly in Advanced Search. [RPMXCON-48230]
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48230", enabled = true, groups = { "regression" })
	public void modifyExistingRemarkOnAdv() throws ParseException, Exception {
		String remark = "Reviewed" + Utility.dynamicNameAppender();
		String ModifiedRemark = "Remark" + Utility.dynamicNameAppender();
		DocViewPage docview = new DocViewPage(driver);

		// login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-48230 BasicSearch");
		base.stepInfo(
				"Verify that Modified reviewer remarks for Non-audio documents is working correctly in Advanced Search.");

		// configure metadata query
		session.basicContentSearch(Input.searchString2);
		session.viewInDocView();

		// add remark
		session.createRemarks(remark);

		// verify Remark in session search page
		base.selectproject();
		int PureHit = session.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);

		// configure metadata query and modify remark
		base.selectproject();
		session.basicContentSearch(Input.searchString2);
		session.viewInDocView();
		docview.editRemarkForNonAudioDoc(remark, ModifiedRemark);

		// verify Remark in session search page
		base.selectproject();
		int PureHit2 = session.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);

		// configure metadata query and modify remark
		base.selectproject();
		session.basicContentSearch(Input.searchString2);
		session.viewInDocView();
		docview.deleteReamark(ModifiedRemark);

		softAssertion.assertNotEquals(PureHit2, PureHit);
		softAssertion.assertAll();

		login.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
