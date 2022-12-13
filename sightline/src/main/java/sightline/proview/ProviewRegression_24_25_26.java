package sightline.proview;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProviewRegression_24_25_26 {
	Driver driver;
	LoginPage login;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssert;
	Categorization categorize;
	TagsAndFoldersPage tagsAndFolder;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}


	
	@DataProvider(name = "USERS")
	public Object[][] USERS() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	/**
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54117
	 * @Description : To verify that RMU can view the selected folders.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54117", enabled = true, groups = { "regression" })
	public void verifyRmuCanViewSelectedFolder() throws InterruptedException {

		base.stepInfo("RPMXC0N-54117  Proview");
		base.stepInfo("TTo verify that RMU can view the selected folders.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);

		// select Folder in Corpus Section
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);
		categorize.fillingStep2CorpusTab("folder", folderName, null, true);
		driver.waitForPageToBeReady();
		String selectedFolder = categorize.getSelectedAnalyzedSub().getText();
		softassert.assertEquals(folderName, selectedFolder);
		base.stepInfo("Selected folder is displayed successfully");

		login.logout();
	}

	/**
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54121
	 * @Description : To verify that user can remove the folder.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54121", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyUserCanRemoveFolder(String username, String password, String userRole)
			throws InterruptedException {

		base.stepInfo("RPMXC0N-54121  Proview");
		base.stepInfo("To verify that user can remove the folder.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		SoftAssert softassert = new SoftAssert();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Logged in as : " + userRole);

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("folder", folderName, null, true);
		driver.waitForPageToBeReady();
		String selectedFolder = categorize.getSelectedAnalyzedSub().getText();
		softassert.assertEquals(folderName, selectedFolder);
		base.stepInfo("Folder is selected in analyzed as expected");

		// verify selected data is removed
		base.waitForElement(categorize.getSelectedAnalyzedXBtn());
		categorize.getSelectedAnalyzedXBtn().waitAndClick(5);
		base.stepInfo("Clicled x icon in selected folder");
		driver.waitForPageToBeReady();
		base.ValidateElement_AbsenceReturn(categorize.getSelectedAnalyzedSub());
		base.passedStep("Data is removed successfully as expected");
		softassert.assertAll();
		login.logout();
	}
	
	/**
	 * @throws AWTException
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54116
	 * @Description : To verify that on clicking on 'Production Set' icon, all
	 *              existing sets should be displayed.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54116", enabled = true, groups = { "regression" })
	public void verifyProductionSetExistingSetDisplayed() throws InterruptedException, AWTException {

		base.stepInfo("RPMXC0N-54116  Proview");
		base.stepInfo("To verify that on clicking on 'Production Set' icon, all existing sets should be displayed.");
		Categorization categorize = new Categorization(driver);
		SoftAssert softassert=new SoftAssert();

		// Login As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

        ProductionPage page = new ProductionPage(driver);
        driver.waitForPageToBeReady();
        this.driver.getWebDriver().get(Input.url + "Production/Home");
        base.waitForElementCollection(page.getProductionSets());
        List<String> productionSets = base.availableListofElements(page.getProductionSets());
        int totalProdSets = page.getProductionSets().size();
        System.out.println(totalProdSets);
        List<String> result = new ArrayList<>();
        for (String s : productionSets) {
          result.add(s.replace(" (Production Set)", ""));
        }
        base.stepInfo(result.toString());
        categorize.navigateToCategorizePage();
        categorize.fillingTrainingSetSection("SG", Input.securityGroup, null, null);
 
        // select production sets
        categorize.selectTrainingSet("Analyze Select Production Sets");
        base.stepInfo("Analyze Select Production Sets  Results Sets Expanded");
        base.waitForElement(categorize.getProductionSelectionPopUp());
        categorize.getProductionSelectionPopUp().waitAndClick(5);
        for(int i=0;i<totalProdSets;i++) {
        	softassert.assertEquals(categorize.getProdSet(result.get(i)).isDisplayed(),true);
        	if(categorize.getProdSet(result.get(i)).isDisplayed()) {
        	base.stepInfo(result.get(i)+" is displayed as expected in proview page ");
        	}
        }
        softassert.assertAll();
        base.passedStep("All existing production sets are dislayed in proview page afterclicking production set icon");

	}

	/**
	 * @Author Krishna Date:NA ModifyDate:NA RPMXCON-54133
	 * @Description : To verify that if system admin remove the 'Categorize' rights,
	 *              user cannot view the proview page.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54133", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyUserCanRemove(String username, String password, String userRole) throws Exception {

		base.stepInfo("RPMXC0N-54133  Proview");
		base.stepInfo(
				"To verify that if system admin remove the 'Categorize' rights, user cannot view the proview page.");
		Categorization categorize = new Categorization(driver);
		UserManagement user = new UserManagement(driver);
		String categorizetab = "Categorize";

		// Login As SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");
		user.getUserSelectedFunctionalyTabIsUncheck(username, Input.projectName, categorizetab);
		login.logout();

		// Login as
		login.loginToSightLine(username, password);
		base.stepInfo("Login as " + userRole);
		driver.waitForPageToBeReady();
		if (categorize.getCategorizeMenu().isDisplayed()) {
			base.failedStep("Categorize option is visible for the user.");
		} else {
			base.passedStep("Categorize option is not visible for the user as expected.");
		}
		login.logout();

		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.getUserSelectedFunctionalyTabIsUncheck(username, Input.projectName, categorizetab);
		login.logout();
	}
	
	/**
	 * @Author  Date:NA ModifyDate:NA RPMXCON-54135
	 * @Description : To verify that “Select Docs to be Analyzed“ frame enabled once
	 *              any data selected “Identify the Training Set” frame.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54135", enabled = true, groups = { "regression" })
	public void verifySelectDoxAnalyzedFrameEnabled() throws InterruptedException {

		base.stepInfo("RPMXC0N-54135  Proview");
		base.stepInfo(
				"To verify that “Select Docs to be Analyzed“ frame  enabled once any data selected “Identify the Training Set” frame.");
		String folderName = "FOLDER" + Utility.dynamicNameAppender();
		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softassert = new SoftAssert();
		String assign = "btnAssign";
		String prod = "btnProduction";
		String folder = "btnFolder";
		String savedsearch = "btnSavedSearch";

		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// basic Content search
		sessionSearch.basicContentSearch(Input.testData1);

		// perform Bulk Folder
		sessionSearch.bulkFolder(folderName);
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);
		driver.waitForPageToBeReady();

		// select corpus to analyzed sets
		base.waitTime(5);
		categorize.getAnalyzeSelectFolders().waitAndClick(5);
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(folder).isElementAvailable(5));
		categorize.selectTrainingSet("Analyze Select Assignments");
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(assign).isElementAvailable(5));
		categorize.selectTrainingSet("Analyze Select Production Sets");
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(prod).isElementAvailable(5));
		categorize.selectTrainingSet("Analyze Select Saved Search Results Sets");
		softassert.assertFalse(categorize.getAnalyzeSelectBtnDisabled(savedsearch).isElementAvailable(5));
		softassert.assertFalse(categorize.getRunBtnDisabled().isElementAvailable(5));
		base.passedStep("Selected Docs to be Analyzed frame has been enabled as expected");
		softassert.assertAll();

	}
	
	
	/**
	 * @Author  Date:NA ModifyDate:NA RPMXCON-54131
	 * @Description : To verify that 'Doc to be analyzed' should be display warning
	 *              message if training set is not selected.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54131", enabled = true, groups = { "regression" })
	public void verifyDocsToBeAnalyzedWarningMsg() throws InterruptedException {

		base.stepInfo("RPMXC0N-54131  Proview");
		base.stepInfo(
				"To verify that 'Doc to be analyzed' should be display warning message if training set is not selected.");
		Categorization categorize = new Categorization(driver);

		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		categorize.navigateToCategorizePage();

		driver.scrollingToBottomofAPage();
		categorize.getGotoStep2().waitAndClick(5);
		base.stepInfo("To be analyzed is clicked as expected");
		boolean warningStatus = base.getWarningsMsgHeader().isElementAvailable(3);
		System.out.println(warningStatus);
		if (warningStatus == true) {
			base.VerifyWarningMessage("Please select a training set");
			base.passedStep("Warning message is displayed successfully");
			
		} else {
			base.failedStep("warning msg is not displayed");
		}
	}

	
	/**
	 * @Author Jeevitha
	 * @Description : To verify that project admin can view the save searched result
	 *              on proview page. [RPMXCON-54126]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54126", enabled = true, groups = { "regression" })
	public void verifyAllTagDisplayed2() throws Exception {

		base.stepInfo("RPMXC0N-54126  Proview");
		base.stepInfo("To verify that project admin can view the save searched result on proview page.");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in as RMU User");

		// fetch All Available Searches in project
		session.navigateToAdvancedSearchPage();
		session.switchToWorkproduct();
		session.getSavedSearchResult().waitAndClick(10);

		base.waitForElementCollection(session.getTree());
		List<String> searchlist = base.availableListofElements(session.getTree());

		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Fetch displayed Searches in Categorization page
		categorize.selectTrainingSet("Identify by Saved Search");
		driver.scrollingToBottomofAPage();

		base.waitForElementCollection(categorize.getAvailableSearches());
		List<String> searchPresentInCategorize = base.availableListofElements(categorize.getAvailableSearches());

		// Verify All existing Searches is Displayed

		List<String> expectedSearchList = base.sortTheList(searchlist, true);
		driver.waitForPageToBeReady();
		List<String> actualSearchList = base.sortTheList(searchPresentInCategorize, true);
		boolean flag = base.compareListViaContains(expectedSearchList, actualSearchList);
		base.printResutInReport(flag, "All existing Searches are Displayed.", "All existing Searches is not Displayed.",
				"Pass");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that if training set does not contain any documents,
	 *              displays ERROR in 'My Background Tasks'. [RPMXCON-54137]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54137", enabled = true, groups = { "regression" })
	public void verifyErrorMsgDisplayed() throws InterruptedException {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String Tagname = "Tag" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXC0N-54137  Proview");
		base.stepInfo(
				"To verify that if training set does not contain any documents, displays ERROR in 'My Background Tasks'.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as PA User");

		// create folder in Default SG
		tagsAndFolder.CreateFolder(Folder, Input.securityGroup);
		tagsAndFolder.CreateTag(Tagname, Input.securityGroup);

		// Open CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select TAG & FOLDER without performing bulk action
		categorize.fillingTrainingSetSection("Tag", Tagname, "", "");
		categorize.fillingStep2CorpusTab("Folder", Folder, "", true);

		// Run and verify notification recieved & verify Navigation to Background task
		// page
		int actualNotification = base.initialBgCount();
		categorize.getRun().ScrollTo();
		categorize.getRun().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.checkNotificationCount(actualNotification, 1);
		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);

		// verify Error Status Displayed in background task page
		driver.waitForPageToBeReady();
		int indexValue = base.getIndex(categorize.getBGTaskHeader(), "Status");
		String statusCategorize = categorize.getCategoreStatusBG(indexValue).getText();
		base.textCompareEquals(statusCategorize, "ERROR", "Expected Error Status is displayed",
				"Expected Error Status is Not displayed");

		// Delete Tag & Folder
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.deleteAllTags(Tagname);
		tagsAndFolder.deleteAllFolders(Folder);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Project Admin can view all the security group
	 *              at Project level. [RPMXCON-54128]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54128", enabled = true, groups = { "regression" })
	public void verifyAllSgDisplayed() throws Exception {
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		base.stepInfo("RPMXC0N-54128  Proview");
		base.stepInfo("To verify that Project Admin can view all the security group at Project level.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as PA User");

		// fetch Available security group
		security.navigateToSecurityGropusPageURL();
		base.waitForElementCollection(security.getTolSecurityGroupCount());
		List<String> actualSGPresent = base.availableListofElements(security.getTolSecurityGroupCount());

		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Fetch displayed security groups in Categorization page
		categorize.selectTrainingSet("Identify by Security Group");
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();

		base.waitForElementCollection(categorize.getAvailableSecurityGroups());
		List<String> sgPresentInCategorize = base.availableListofElements(categorize.getAvailableSecurityGroups());

		// Verify All existing security group is Displayed
		base.sortAndCompareList(actualSGPresent, sgPresentInCategorize, true, "Ascending",
				"All existing security group is displayed.", "All existing security group is not displayed.");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that Proview result displays if training set select
	 *              as Assignment and documents to be analyzed from Folder.
	 *              [RPMXCON-54143]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54143", enabled = true, groups = { "regression" })
	public void verifyResultForTrainingSetAsAssignment() throws InterruptedException {
		String AssignmentName = "Assign" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		AssignmentsPage assign = new AssignmentsPage(driver);

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXC0N-54143  Proview");
		base.stepInfo(
				"To verify that Proview result displays if training set select as Assignment and documents to be analyzed from Folder.");

		// configure query & Bulk assign
		session.basicContentSearch(Input.testData1);
		session.bulkFolder(folderName);

		// Create Assignment
		session.bulkAssignWithOutPureHit();
		assign.assignDocstoNewAssgn(AssignmentName);
		assign.quickAssignmentCreation(AssignmentName, Input.codeFormName);
		assign.saveAssignment(AssignmentName, Input.codeFormName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Assignment in Training Set
		categorize.fillingTrainingSetSection("Assignment", AssignmentName, null, null);

		// select doc to be analyzed from Folder in Corpus Section
		categorize.fillingStep2CorpusTab("Folder", folderName, null, true);

		// Run & verify result Displayed
		categorize.runCategorization("YES");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Proview result displays if training set select
	 *              as Save Search and documents to be analyzed from Folder.
	 *              [RPMXCON-54142]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54142", enabled = true, groups = { "regression" })
	public void verifyResultForTrainingSetAsSearch() throws InterruptedException {
		String savedSearch = "Search" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXC0N-54142  Proview");
		base.stepInfo(
				"To verify that Proview result displays if training set select as Save Search and documents to be analyzed from Folder.");

		// configure query & Bulk assign
		session.basicContentSearch(Input.testData1);
		session.saveSearch(savedSearch);
		session.bulkFolder(folderName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Search in Training Set
		categorize.fillingTrainingSetSection("Search", savedSearch, Input.mySavedSearch, null);

		// select doc to be analyzed from Folder in Corpus Section
		categorize.fillingStep2CorpusTab("Folder", folderName, null, true);

		// Run & verify result Displayed
		categorize.runCategorization("YES");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Proview result displays if training set select
	 *              as Tag and documents to be analyzed as Saved Search Result.
	 *              [RPMXCON-54141]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54141", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyResultForTrainingSetAsTag(String username, String password, String userRole)
			throws InterruptedException {
		String savedSearch = "Search" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Logged in as : " + userRole);

		base.stepInfo("RPMXC0N-54141  Proview");
		base.stepInfo(
				"To verify that Proview result displays if training set select as Tag and documents to be analyzed as Saved Search Result.");

		// configure query & Bulk assign
		session.basicContentSearch(Input.testData1);
		session.saveSearch(savedSearch);
		session.bulkTag(tagName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Tag in Training Set
		categorize.fillingTrainingSetSection("Tag", tagName, null, null);

		// select doc to be analyzed from Search in Corpus Section
		categorize.fillingStep2CorpusTab("Search", savedSearch, Input.mySavedSearch, true);

		// Run & verify result Displayed
		categorize.runCategorization("YES");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Proview result displays if training set select
	 *              as Tag and documents to be analyzed as Folder. [RPMXCON-54140]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54140", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyResultForTrainingSetAsTagAndFold(String username, String password, String userRole)
			throws InterruptedException {
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Logged in as : " + userRole);

		base.stepInfo("RPMXC0N-54140  Proview");
		base.stepInfo(
				"To verify that Proview result displays if training set select as Tag and documents to be analyzed as Folder.");

		// configure query & Bulk assign
		session.basicContentSearch(Input.testData1);
		session.bulkTag(tagName);
		session.bulkFolderWithOutHitADD(folderName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Tag in Training Set
		categorize.fillingTrainingSetSection("Tag", tagName, null, null);

		// select doc to be analyzed from Folder in Corpus Section
		categorize.fillingStep2CorpusTab("Folder", folderName, null, true);

		// Run & verify result Displayed
		categorize.runCategorization("YES");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that RMU can view all the existing tags which is
	 *              assosicated to the security group. [RPMXCON-54104]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54104", enabled = true, groups = { "regression" })
	public void verifyAllTagDisplayed() throws Exception {
		TagsAndFoldersPage tagsandfolder = new TagsAndFoldersPage(driver);

		base.stepInfo("RPMXC0N-54104  Proview");
		base.stepInfo("To verify that RMU can view all the existing tags which is assosicated to the security group.");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in as RMU User");

		// fetch All Available Tags
		tagsandfolder.navigateToTagsAndFolderPage();
		tagsandfolder.expandAllClosedArrow();
		base.waitForElementCollection(tagsandfolder.getAvailableTagList());
		List<String> actualTagPresent = base.availableListofElements(tagsandfolder.getAvailableTagList());

		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Fetch displayed Tags in Categorization page
		base.waitForElement(categorize.getSelectIdentifyByTags());
		categorize.getSelectIdentifyByTags().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();

		base.waitForElementCollection(categorize.getAvailableTags());
		List<String> tagPresentInCategorize = base.availableListofElements(categorize.getAvailableTags());

		// Verify All existing Tags is Displayed
		base.sortAndCompareList(actualTagPresent, tagPresentInCategorize, true, "Ascending",
				"All existing Tags is displayed.", "All existing Tags is not displayed.");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Project Admin can view all the existing folders
	 *              at Project level [RPMXCON-54125]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54125", enabled = true, groups = { "regression" })
	public void verifyAllFolderDisplayed() throws Exception {
		TagsAndFoldersPage tagsandfolder = new TagsAndFoldersPage(driver);

		base.stepInfo("RPMXC0N-54125  Proview");
		base.stepInfo("To verify that Project Admin can view all the existing folders at Project level");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as PA User");

		// fetch All Available Folders
		tagsandfolder.selectallFolderRoot();
		tagsandfolder.expandAllClosedArrow();
		base.waitForElementCollection(tagsandfolder.getAvailableFolderList());
		List<String> actualFolderPresent = base.availableListofElements(tagsandfolder.getAvailableFolderList());

		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Fetch displayed Folders in Categorization page
		categorize.selectTrainingSet("Identify by Folder");
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();

		base.waitForElementCollection(categorize.getAvailableFoldersInTraining());
		List<String> folderPresentInCategorize = base
				.availableListofElements(categorize.getAvailableFoldersInTraining());

		// Verify All existing Folders is Displayed
		base.sortAndCompareList(actualFolderPresent, folderPresentInCategorize, true, "Ascending",
				"All existing Folders is displayed.", "All existing Folders is not displayed.");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that on clicking on 'Folder' icon, all existing
	 *              folders should be displayed at Project level. [RPMXCON-54127]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54127", enabled = true, groups = { "regression" })
	public void verifyAllFolderDisplayedAtAnalyzePart() throws Exception {
		String tagName = "TAG" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsandfolder = new TagsAndFoldersPage(driver);

		base.stepInfo("RPMXC0N-54127  Proview");
		base.stepInfo(
				"To verify that on clicking on 'Folder' icon, all existing folders should be displayed at Project level.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as PA User");

		// fetch All Available Folders
		tagsandfolder.selectallFolderRoot();
		tagsandfolder.expandAllClosedArrow();
		base.waitForElementCollection(tagsandfolder.getAvailableFolderList());
		List<String> actualFolderPresent = base.availableListofElements(tagsandfolder.getAvailableFolderList());

		// create a folder
		tagsandfolder.CreateTag(tagName, Input.securityGroup);
		
		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Select Tag in Training Set & navigate to Analyze Section 
		categorize.fillingTrainingSetSection("Tag", tagName, null, null);

		// Fetch Folders Displayed in Analyze section
		categorize.getAnalyzeSelectFolders().ScrollTo();
		categorize.getAnalyzeSelectFolders().waitAndClick(5);
		base.waitForElement(categorize.getFolderSelectionPopUp());
		categorize.getFolderSelectionPopUp().waitAndClick(5);

		base.waitForElementCollection(categorize.getAvailableFoldersInAnalyzed());
		List<String> folderPresentInCategorize = base
				.availableListofElements(categorize.getAvailableFoldersInAnalyzed());

		// Verify All existing Folders is Displayed
		base.sortAndCompareList(actualFolderPresent, folderPresentInCategorize, true, "Ascending",
				"All existing Folders is displayed in Analyze section.", "All existing Folders is not displayed.");

		// Logout
		login.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssert = new SoftAssert();
		categorize = new Categorization(driver);
		session = new SessionSearch(driver);
		tagsAndFolder = new TagsAndFoldersPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
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

		System.out.println("Executed :Reviewer Result Report Regression ");
	}
}
