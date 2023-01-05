package sightline.batchRedaction;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedaction_Phase2_Regression {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "USERS")
	public Object[][] USERS() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.sa1userName, Input.sa1password, "SA" }, { Input.da1userName, Input.da1password, "DA" } };
		return users;
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : Verify that on click of “Analyze Search for Redaction”, batch
	 *              redaction run should be initiated to find out the list of
	 *              “redaction keyword/term occurrences” followed the list of
	 *              documents for list of occurrences [RPMXCON-48805]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48805", enabled = true, groups = { "regression" })
	public void verifyAnalyeSearchSuccessMsg() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-48805 Batch Redaction");
		base.stepInfo(
				"Verify that on click of “Analyze Search for Redaction”, batch redaction run should be initiated to find out the list of “redaction keyword/term occurrences” followed the list of documents for list of occurrences");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// Perform Analysis of search
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);

		// verify Success Message
		base.VerifySuccessMessageB(
				"Your Request to Analyze corpus for redaction term hits has been added to the background.  Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		login.logout();

	}


	/**
	 * @Author Jeevitha
	 * @Description : Verify that only RMU user can have access to "Batch
	 *              Redactions" page [RPMXCON-53326]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53326", enabled = true, groups = { "regression" })
	public void verifyBRPageIsAccessible() throws Exception {

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53326 Batch Redaction");
		base.stepInfo("Verify that only RMU user can have access to \"Batch Redactions\" page");

		// verify navigation to Batch Redaction is Accessible
		batch.navigateToBRPage();

		// disable Docview-Redaction
		batch.assignRedactionRights(Input.rmu1userName, false);
		base.selectproject();

		// verify navigation to Batch Redaction is Accessible after Disable
		batch.navigateToBRPage();
		base.passedStep("Batch Redaction is Accessible Even after Disabling Docview-Redaction Control");

		// enable Docview-Redaction
		batch.assignRedactionRights(Input.rmu1userName, true);

		// logout
		login.logout();

		// Login as a DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged In As : DA");

		// click active Project and verify Impersonation to RMU
		DomainDashboard domain = new DomainDashboard(driver);
		domain.clickActiveProject(Input.projectName);

		// verify navigation to Batch Redaction is Accessible after Impersonation
		driver.waitForPageToBeReady();
		batch.navigateToBRPage();

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that SA/DA/PA after impersonating as RMU can have
	 *              access to "Batch Redactions" page [RPMXCON-53328]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53328", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyBRPageIsAccessibleAfterImpersonate(String username, String password, String userRole)
			throws Exception {

		// Login as a User
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + userRole);

		base.stepInfo("Test case Id:RPMXCON-53328 Batch Redaction");
		base.stepInfo("Verify that SA/DA/PA after impersonating as RMU can have access to \"Batch Redactions\" page");

		// Impersonate to RMU
		base.rolesToImp(userRole, "RMU");

		// verify navigation to Batch Redaction is Accessible after Impersonate to rmu
		batch.navigateToBRPage();

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'Analyze Search for Redaction' button from Batch
	 *              Redactions page is enabled for the Saved Search(es)/Search
	 *              Group(s) [RPMXCON-53329]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53329", enabled = true, groups = { "regression" })
	public void verifyNavigatioNAndAnalyzeBtn() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53329 Batch Redaction");
		base.stepInfo(
				"Verify that 'Analyze Search for Redaction' button from Batch Redactions page is enabled for the Saved Search(es)/Search Group(s)");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// verify navigation to Batch Redaction is Accessible after Impersonate to rmu
		batch.navigateToBRPage();

		// verify Analyze Btn enable
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);
		base.passedStep("'Analyze Search for Redaction' button is enabled");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the "Batch Redactions" home page for RMU user
	 *              [RPMXCON-53327]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53327", enabled = true, groups = { "regression" })
	public void verifyBatchRedactionHomePage() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String[] actualSectionList = { "Batch Redactions", "Batch Redaction History" };
		String[] actualHistoryheader = { "BATCHID", "SAVED SEARCH", "REDACTION TAG", "# DOCS", "STATUS", "REPORT",
				"ACTION" };

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53327 Batch Redaction");
		base.stepInfo("Verify the \"Batch Redations\" home page for RMU user");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		session.saveSearchAtAnyRootGroup(searchName, Input.shareSearchDefaultSG);

		// verify navigation to Batch Redaction
		batch.navigateToBRPage();

		// verify batch redaction page sections Headers
		List<String> sectionList = base.availableListofElements(batch.HeaderTabsPresentInBR());
		String passMsg = "Batch Redactions home page is Displayed with : " + sectionList;
		String failMsg = "Batch Redactions home page is Headers is not as Expected";
		base.compareArraywithDataList(actualSectionList, sectionList, true, passMsg, failMsg);

		// 1st section header and icon verification
		base.ValidateElement_Presence(batch.getSelectSearchHeader(), "Select a Search / Search Group");
		batch.SearchGroupHelpIcon();

		// verify tabs present
		batch.verifySelectSearchSection(Input.shareSearchDefaultSG);
		batch.verifySelectSearchSection(Input.mySavedSearch);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(searchName);

		// verify history table header & verify help icon
		batch.BatchRedactionHistoryHelpIcon();
		
		List<String> historyHeaderList = base.availableListofElements(batch.batchRedactionHistoryHeader());
		String passMsg2 = "Batch Redactions History Table is Displayed with : " + historyHeaderList;
		String failMsg2 = "Batch Redactions History Table Headers is not as Expected";
		base.compareArraywithDataList(actualHistoryheader, historyHeaderList, true, passMsg2, failMsg2);

		// verify rollback & click here for report link
		base.ValidateElement_Presence(batch.getRollbackbtn(searchName), "Rollback Link ");
		base.ValidateElement_Presence(batch.getClickHereReportbtn(searchName), "Click here for Report Link");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName, Input.shareSearchDefaultSG, "Yes");

		// logout
		login.logout();
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : Verify when user selects the saved search containing audio
	 *              documents for batch redaction [RPMXCON-53389]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53389", enabled = true, groups = { "regression" })
	public void verifyRedactBtnIsDisabledForAudio() throws Exception {
		String audioSearch = "Search1" + Utility.dynamicNameAppender();
		String wpSearch = "Search2" + Utility.dynamicNameAppender();

		String[] searchList = { audioSearch, wpSearch };

		// Login as a User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53389 Batch Redaction");
		base.stepInfo("Verify when user selects the saved search containing audio documents for batch redaction");

		// Configure Audio Search
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.saveSearch(audioSearch);

		// configure workProduct search
		base.selectproject();
		session.switchToWorkproduct();
		session.workProductSearch("redactions", Input.defaultRedactionTag, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearch(wpSearch);

		for (int i = 0; i < searchList.length; i++) {
			// Navigate to Batch redaction & verify Analyze Button
			batch.performAnalysisGroupForRedcation(searchList[i], null, 0, false, true);

			// verify search hit count , Readact doc
			batch.verifySearchTree(searchList[i]);

			// verify readct btn disabled
			batch.verifyRedactBtnDisabled(searchList[i], true);

			saveSearch.deleteSearch(searchList[i], Input.mySavedSearch, "Yes");
		}

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify when user selects the saved search with work
	 *              product/comment/remark document count for batch redaction
	 *              [RPMXCON-53388]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53388", enabled = true, groups = { "regression" })
	public void verifyRedactBtnIsDisabledForWpComment() throws Exception {
		String wpFolder = "Search1" + Utility.dynamicNameAppender();
		String wpTag = "Search2" + Utility.dynamicNameAppender();
		String wpRedactTag = "Search3" + Utility.dynamicNameAppender();
		String commentSearch = "Comment" + Utility.dynamicNameAppender();

		String folder = "Folder" + Utility.dynamicNameAppender();
		String tag = "Tag" + Utility.dynamicNameAppender();
		String docComment1 = "Comment" + Utility.dynamicNameAppender();

		DocViewPage docview = new DocViewPage(driver);
		String[] searchList = { wpFolder, wpTag, wpRedactTag, commentSearch };

		// Login as a User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id: RPMXCON-53388 Batch Redaction");
		base.stepInfo(
				"Verify when user selects the saved search with work product/comment/remark document count for batch redactionF");

		//Add redacion tag, bulk tag, bulk folder, And Add comment 
		session.basicContentSearch(Input.testData1);
		session.bulkTag(tag);
		session.bulkFolderWithOutHitADD(folder);
		session.ViewInDocViewWithoutPureHit();
		docview.addCommentAndSave(docComment1, true, 1);

		//configure search with comment
		base.selectproject();
		session.getCommentsOrRemarksCount(Input.documentComments, docComment1);
		session.saveSearch(commentSearch);

		// configure workProduct Folder
		base.selectproject();
		session.switchToWorkproduct();
		session.workProductSearch("folder", folder, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearch(wpFolder);

		// configure workProduct Tag
		base.selectproject();
		session.switchToWorkproduct();
		session.workProductSearch("tag", tag, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearch(wpTag);

		// configure workProduct Redaction
		base.selectproject();
		session.switchToWorkproduct();
		session.workProductSearch("redactions", Input.defaultRedactionTag, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearch(wpRedactTag);

		for (int i = 0; i < searchList.length; i++) {
			// Navigate to Batch redaction & verify Analyze Button
			batch.performAnalysisGroupForRedcation(searchList[i], null, 0, false, true);

			// verify search hit count , Readact doc
			batch.verifySearchTree(searchList[i]);

			// verify readct btn disabled
			batch.verifyRedactBtnDisabled(searchList[i], true);

			saveSearch.deleteSearch(searchList[i], Input.mySavedSearch, "Yes");
		}

		// logout
		login.logout();
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : Verify that batch redaction history should be security group
	 *              specific and entries should be vertically centered
	 *              [RPMXCON-53337]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53337", enabled = true, groups = { "regression" })
	public void verifybatchRedactionHistory() throws Exception {
		String otherSGR1 = "Search1" + Utility.dynamicNameAppender();
		String defSGR1 = "Search2" + Utility.dynamicNameAppender();
		String otherSGR2 = "Search3" + Utility.dynamicNameAppender();
		String ReadctionTag = "Redaction_Tag" + Utility.dynamicNameAppender();
		String Annotation = "AnnotationLayer" + Utility.dynamicNameAppender();

		String securityGrp = "Security" + Utility.dynamicNameAppender();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement user = new UserManagement(driver);
		RedactionPage redact = new RedactionPage(driver);
		AnnotationLayer layer = new AnnotationLayer(driver);

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Craete Redaction Tag & annotation layer
		redact.navigateToRedactionsPageURL();
		redact.manageRedactionTagsPage(ReadctionTag);
		layer.navigateToAnnotationLayerPage();
		layer.AddAnnotation(Annotation);

		// Add SG & assign Redaction tag & assign annoattion layer
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(securityGrp);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.waitTime(2);	
		sg.selectSecurityGroup(securityGrp);
		driver.waitForPageToBeReady();
		sg.assignRedactionTagtoSG(ReadctionTag);
		sg.assignAnnotationToSG(Annotation);

		// assign access to users
		user.navigateToUsersPAge();
		user.assignAccessToSecurityGroups(securityGrp, Input.rmu1userName);
		base.waitTime(2);
		user.assignAccessToSecurityGroups(securityGrp, Input.rmu2userName);

		// bulk release doc's to SG
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGrp);
		
		// logout
		login.logout();

		// Login as a User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");
		base.selectsecuritygroup(securityGrp);

		base.stepInfo("Test case Id:RPMXCON-53337 Batch Redaction");
		base.stepInfo(
				"Verify that batch redaction history should be security group specific and entries should be vertically centered");

		// Configure Audio Search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(otherSGR1);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(otherSGR1, true);
		driver.waitForPageToBeReady();
		batch.viewAnalysisAndBatchReport(ReadctionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(otherSGR1);

		// Default SG
		base.selectsecuritygroup(Input.securityGroup);
		session.basicContentSearch(Input.testData1);
		session.saveSearch(defSGR1);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(defSGR1, true);
		driver.waitForPageToBeReady();
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(defSGR1);

		// logout
		login.logout();

		// Login as a RMU2
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Logged In As : RMU2");
		base.selectsecuritygroup(securityGrp);

		// Configure Audio Search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(otherSGR2);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(otherSGR2, true);
		driver.waitForPageToBeReady();
		batch.viewAnalysisAndBatchReport(ReadctionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(otherSGR2);

		// verify history contains only Other Security grup batch redactions of all
		// users
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(batch.getColorStatusMsg(otherSGR2), otherSGR2 + " Of Other SG of RMU2");
		base.ValidateElement_Presence(batch.getColorStatusMsg(otherSGR1), otherSGR1 + " Of Other SG Of RMU1");
		driver.waitForPageToBeReady();
		base.ValidateElement_Absence(batch.getColorStatusMsg(defSGR1), defSGR1 + " of Default SG is not present ");

		base.passedStep(
				"Batch Redaction history display as per logged in users security group i.e here selected is Default SG");
		base.passedStep("Batch redaction performed by other users in same Sg is displayed");

		// verify allignment of enteries
		batch.verifyAllignmentOfHistoryEnteries();

		base.selectsecuritygroup(Input.securityGroup);

		// logout
		login.logout();

		// Delete Security Group
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		sg.deleteSecurityGroups(securityGrp);

		// logout
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
		batch = new BatchRedactionPage(driver);
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
//			login.switchProjectToEnglish();
			try {
				login.logout();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
			login.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//					login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
