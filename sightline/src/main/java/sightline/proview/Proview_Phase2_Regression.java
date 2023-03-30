package sightline.proview;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Proview_Phase2_Regression {
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
	 * @Author Jeevitha
	 * @Description :Categorization - Verify Search as source[RPMXCON-54714]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54714", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyCategorizeForSearch(String username, String password, String userRole)
			throws InterruptedException {
		String searchName1 = "Search" + Utility.dynamicNameAppender();
		String searchName2 = "Search2" + Utility.dynamicNameAppender();
		String folder = "Folder" + Utility.dynamicNameAppender();
		SavedSearch saveSearch = new SavedSearch(driver);

		// Login as User
		login.loginToSightLine(username, password);

		base.stepInfo("RPMXC0N-54714  Proview");
		base.stepInfo("Categorization - Verify Search as source");

		// Configure query and save Search & Bulk Folder
		session.basicContentSearch(Input.searchString4);
		session.saveSearch(searchName1);
		base.CloseSuccessMsgpopup();
		session.saveSearchAtAnyRootGroup(searchName2, Input.shareSearchDefaultSG);
		base.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		session.bulkFolder(folder);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// verify Search Tabs Present In Saved Search Tab
		categorize.selectTrainingSet("Identify by Saved Search");
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(categorize.getSelectCheckBoxWithoutSpace(Input.mySavedSearch),
				Input.mySavedSearch + " Tab");

		if (username.equals(Input.pa1userName)) {
			base.ValidateElement_Presence(categorize.getSelectCheckBoxWithoutSpace(Input.shareSearchPA),
					Input.shareSearchPA + "Tab");
		}

		base.ValidateElement_Presence(categorize.getSelectCheckBoxWithoutSpace(Input.shareSearchDefaultSG),
				Input.shareSearchDefaultSG + "Tab");

		// Select Search In Analyse Section
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Search", searchName1, Input.mySavedSearch, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("folder", folder, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		// select Search in Corpus Section
		driver.waitForPageToBeReady();
		categorize.fillingStep2CorpusTab("Search", searchName2, Input.shareSearchDefaultSG, true);
		categorize.runCategorization("YES");

		// Delete Search
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName2, Input.shareSearchDefaultSG, "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that categorized result displays if training set
	 *              select as Folder and documents to be analyzed from
	 *              Folder.[RPMXCON-54145]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54145", enabled = true, groups = { "regression" })
	public void verifyCategorizeForFolder() throws InterruptedException {
		String folder = "Folder" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXC0N-54145  Proview");
		base.stepInfo(
				"To verify that categorized result displays if training set select as Folder and documents to be analyzed from Folder.");

		// Configure query and save Search & Bulk Folder
		session.basicContentSearch(Input.searchString2);
		session.bulkFolder(folder);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Folder", folder, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("folder", folder, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that categorized result displays if training set
	 *              select as Security Group and documents to be analyzed from
	 *              Folder.[RPMXCON-54144]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54144", enabled = true, groups = { "regression" })
	public void verifyCategorizeForSG() throws InterruptedException {
		String folder = "Folder" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXC0N-54144  Proview");
		base.stepInfo(
				"To verify that categorized result displays if training set select as Security Group and documents to be analyzed from Folder.");

		// Configure query and save Search & Bulk Folder
		session.basicContentSearch(Input.searchString2);
		session.bulkFolder(folder);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Security group In Analyse Section
		categorize.fillingTrainingSetSection("SG", Input.securityGroup, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("folder", folder, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that categorized result displays if training set
	 *              select as Folder and documents to be analyzed from Saved
	 *              Search.[RPMXCON-54147]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54147", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyCategorizeForFolderAndSearch(String username, String password, String userRole)
			throws InterruptedException {
		String folder = "Folder" + Utility.dynamicNameAppender();
		String SearchName = "Search" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(username, password);

		base.stepInfo("RPMXC0N-54147  Proview");
		base.stepInfo(
				"To verify that categorized result displays if training set select as Folder and documents to be analyzed from Saved Search.");

		// Configure query and save Search & Bulk Folder
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(SearchName);
		session.bulkFolder(folder);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Folder", folder, null, null);

		// select Search in Corpus Section
		categorize.fillingStep2CorpusTab("Search", SearchName, Input.mySavedSearch, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that categorized result displays if training set
	 *              select as Folder and documents to be analyzed from
	 *              Assignment.[RPMXCON-54146]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54146", enabled = true, groups = { "regression" })
	public void verifyCategorizeForFolderAndAssign() throws InterruptedException {
		String folder = "Folder" + Utility.dynamicNameAppender();
		String AssignmentName = "Assign" + Utility.dynamicNameAppender();
		AssignmentsPage assign = new AssignmentsPage(driver);

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXC0N-54146  Proview");
		base.stepInfo(
				"To verify that categorized result displays if training set select as Folder and documents to be analyzed from Assignment.");

		// Configure query & Bulk Folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolder(folder);

		// Create Assignment
		session.bulkAssignWithOutPureHit();
		assign.assignDocstoNewAssgn(AssignmentName);
		assign.quickAssignmentCreation(AssignmentName, Input.codeFormName);
		assign.saveAssignment(AssignmentName, Input.codeFormName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Folder", folder, null, null);

		// select Assignment in Corpus Section
		categorize.fillingStep2CorpusTab("Assignment", AssignmentName, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that categorized result displays if training set
	 *              select as Security Group and documents to be analyzed from
	 *              Assignment.[RPMXCON-54162]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54162", enabled = true, groups = { "regression" })
	public void verifyCategorizeForSgAndAssign() throws InterruptedException {
		String AssignmentName = "Assign" + Utility.dynamicNameAppender();
		AssignmentsPage assign = new AssignmentsPage(driver);

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXC0N-54162  Proview");
		base.stepInfo(
				"To verify that categorized result displays if training set select as Security Group and documents to be analyzed from Assignment.");

		// configure query & Bulk assign
		session.basicContentSearch(Input.testData1);
		session.bulkAssign();

		// Create Assignment
		assign.assignDocstoNewAssgn(AssignmentName);
		assign.quickAssignmentCreation(AssignmentName, Input.codeFormName);
		assign.saveAssignment(AssignmentName, Input.codeFormName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select security Group In Analyse Section
		categorize.fillingTrainingSetSection("SG", Input.securityGroup, null, null);

		// select Assignment in Corpus Section
		categorize.fillingStep2CorpusTab("Assignment", AssignmentName, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that categorized result displays if training set
	 *              select as Assignment and documents to be analyzed from
	 *              Folder.[RPMXCON-54148]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54148", enabled = true, groups = { "regression" })
	public void verifyCategorizeForAssignAndFold() throws InterruptedException {
		String AssignmentName = "Assign" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		AssignmentsPage assign = new AssignmentsPage(driver);

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXC0N-54148  Proview");
		base.stepInfo(
				"To verify that categorized result displays if training set select as Assignment and documents to be analyzed from Folder.");

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

		// Select AssignmentName In Analyse Section
		categorize.fillingTrainingSetSection("Assignment", AssignmentName, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("Folder", folderName, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :o verify that categorized result displays if training set
	 *              select as Assignment and documents to be analyzed from
	 *              Assignment.[RPMXCON-54149]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54149", enabled = true, groups = { "regression" })
	public void verifyCategorizeForAssignAndAssign() throws InterruptedException {
		String AssignmentName = "Assign" + Utility.dynamicNameAppender();
		AssignmentsPage assign = new AssignmentsPage(driver);

		// Login as User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXC0N-54149  Proview");
		base.stepInfo(
				"o verify that categorized result displays if training set select as Assignment and documents to be analyzed from Assignment.");

		// configure query & Bulk assign
		session.basicContentSearch(Input.testData1);
		session.bulkAssign();

		// Create Assignment
		assign.assignDocstoNewAssgn(AssignmentName);
		assign.quickAssignmentCreation(AssignmentName, Input.codeFormName);
		assign.saveAssignment(AssignmentName, Input.codeFormName);

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select AssignmentName In Analyse Section
		categorize.fillingTrainingSetSection("Assignment", AssignmentName, null, null);

		// select Assignment in Corpus Section
		categorize.fillingStep2CorpusTab("Assignment", AssignmentName, null, true);

		// verify Run categorization
		categorize.runCategorization("YES");

		login.logout();
	}

	/**
	 * @author Brundha.T TestCaseId : RPMXCON-54136
	 * @throws Exception
	 * @DescriptionTo verify that documents found should be zero if user clicks on
	 *                Manage->Categorize.
	 */
	@Test(description = "RPMXCON-54136", enabled = true, groups = { "regression" })
	public void verifyingZeroInProviewPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-54136");
		base.stepInfo("To verify that documents found should be zero if user clicks on Manage->Categorize.");

		String[] Username = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };

		for (int i = 0; i < Username.length; i++) {
			login.loginToSightLine(Username[i], Password[i]);
			base.stepInfo("Logged in As " + Username[i]);

			Categorization Categorization = new Categorization(driver);

			base.stepInfo("Navigating to Categorize page");
			Categorization.navigateToCategorizePage();

			base.stepInfo("verifying document count is zero");
			String DocCount = Categorization.getResultCount().getText();
			String[] Document = DocCount.split(" ");
			String ResultCoount = Document[1];
			System.out.println(ResultCoount);
			if (Integer.valueOf(ResultCoount) == 0) {
				base.passedStep("Documents Found is zero as expected");
			} else {
				base.failedStep("Document count is not Zero");
			}
			login.logout();
		}
	}

	/**
	 * @author Brundha.T TestCaseId : RPMXCON-54124
	 * @throws Exception
	 * @DescriptionTo verify that project Admin can view all the existing tags.
	 */
	@Test(description = "RPMXCON-54124", enabled = true, groups = { "regression" })
	public void verifyingExistingTags() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-54124");
		base.stepInfo("To verify that project Admin can view all the existing tags.");

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		Categorization Categorization = new Categorization(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigating to Tags and folders page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		if (base.getDefaultTag().isDisplayed()) {
			base.getDefaultTag().waitAndClick(5);
		}
		base.waitForElementCollection(base.getAllTags());
		List<String> ListOfTagInTagsPage = base.getAvailableListofElements(base.getAllTags());
		base.stepInfo("All the tags in Tags and folders page" + ListOfTagInTagsPage);
		System.out.println(ListOfTagInTagsPage);

		base.stepInfo("Navigating to Categorize page");
		Categorization.navigateToCategorizePage();
		base.waitForElement(Categorization.getSelectIdentifyByTags());
		Categorization.getSelectIdentifyByTags().waitAndClick(2);

		base.waitForElementCollection(base.getAllTags());
		List<String> ListOfTagInProviewPage = base.availableListofElements(base.getAllTags());
		System.out.println(ListOfTagInProviewPage);
		base.stepInfo("All the tags in proview page" + ListOfTagInProviewPage);

		if (ListOfTagInTagsPage.equals(ListOfTagInProviewPage)) {
			base.passedStep("all existing tags is displayed.");
		} else {
			base.failedStep("all existing tags is not displayed.");
		}

		login.logout();
	}

	/**
	 * @author Brundha.T TestCaseId : RPMXCON-54122
	 * @throws Exception
	 * @DescriptionTo verify that RMU can remove the Assignment.
	 */
	@Test(description = "RPMXCON-54122", enabled = true, groups = { "regression" })
	public void verifyAssignmentIsRemoved() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-54122");
		base.stepInfo("To verify that RMU can remove the Assignment.");

		// Login As PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As " + Input.rmu1userName + "");

		String assignmentName = "assignmentName" + Utility.dynamicNameAppender();

		Categorization Categorization = new Categorization(driver);
		SessionSearch search = new SessionSearch(driver);
		AssignmentsPage Assign = new AssignmentsPage(driver);

		int PureHit = search.basicContentSearch(Input.testData1);
		search.bulkAssign();
		Assign.assignDocstoNewAssgn(assignmentName, Input.codeFormName, PureHit);

		base.stepInfo("Navigating to Proview page");
		Categorization.navigateToCategorizePage();

		base.stepInfo("filling Training set");
		Categorization.fillingTrainingSetSection("Assignment", assignmentName, null, null);

		Categorization.fillingStep2CorpusTab("Assignment", assignmentName, null, true);
		base.ValidateElement_Presence(Categorization.getAssignedName(assignmentName), "Assignment name");
		base.waitForElement(Categorization.getSelectedAnalyzedXBtn());
		Categorization.getSelectedAnalyzedXBtn().waitAndClick(5);
		base.stepInfo("Clicled x icon in selected Assignment");

		driver.waitForPageToBeReady();
		if (!Categorization.getAssignedName(assignmentName).isElementAvailable(2)) {
			base.passedStep("Assignment is removed");
		} else {
			base.failedStep("Assignment is not removed");
		}

		login.logout();
	}

	/**
	 * @author Brundha.T Test case id : RPMXCON-54129
	 * @Description :To verify that after impersonating as Project Admin, system
	 *              admin can run the Proview.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54129", enabled = true, groups = { "regression" })
	public void verifyUserCanRemoveFolder() throws InterruptedException {

		base.stepInfo("RPMXC0N-54129 Proview");
		base.stepInfo("To verify that after impersonating as Project Admin, system admin can run the Proview.");

		String folderName = "Folder" + Utility.dynamicNameAppender();

		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName + "");

		base.stepInfo("Impersonating SA as PA");
		base.impersonateSAtoPA();

		int Purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolder(folderName);

		base.stepInfo("Selecting training set and doc to be analyzed");
		categorize.navigateToCategorizePage();
		categorize.fillingTrainingSetSection("Folder", folderName, null, null);
		categorize.fillingStep2CorpusTab("folder", folderName, null, true);
		categorize.runCategorization("Yes");
		String ResultCount = categorize.getDocCount().getText();
		System.out.println(ResultCount);

		base.stepInfo("verifying result in proview page is displaying correctly");
		base.digitCompareEquals(Purehit, Integer.valueOf(ResultCount),
				"Result is displayed correctly with docCount as" + ResultCount, "Selected Docs are not Displayed");

		login.logout();
	}

	/**
	 * Author :Arunkumar date: 01/12/2022 TestCase Id:RPMXCON-54118 Description :To
	 * verify that RMU can view the selected Assignments.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54118", enabled = true, groups = { "regression" })
	public void verifyRmuViewAssignments() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-54118");
		base.stepInfo("verify that RMU can view the selected Assignments");
		String assignmentName = "assign" + Utility.dynamicNameAppender();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in as RMU");
		base.stepInfo("perform search and action as bulk assign");
		int purehit = session.basicContentSearch(Input.testData1);
		session.bulkAssign();
		base.stepInfo("create assignment,finalize and save");
		AssignmentsPage assignPage = new AssignmentsPage(driver);
		assignPage.FinalizeAssignmentAfterBulkAssign();
		assignPage.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		assignPage.getAssignmentSaveButton().waitAndClick(10);
		base.waitForElement(assignPage.getNumberOfAssignmentsToBeShown());
		driver.waitForPageToBeReady();
		base.stepInfo("verify redirected url after saving assignment");
		base.verifyUrlLanding(Input.url + "en-us/Assignment/ManageAssignment", "Redirected to manage-assignment page",
				"Not redirected to assignment page");
		base.stepInfo("Edit assignment and verify details available");
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		base.waitForElement(assignPage.getAssignmentName());
		String actual = assignPage.getAssignmentName().getText();
		base.compareTextViaContains(assignmentName, actual, "Added assignment name displayed",
				"Added assignment name detail mismatched");
		base.waitForElement(assignPage.getDistributeTab());
		assignPage.getDistributeTab().waitAndClick(10);
		base.waitForElement(assignPage.getEditAggn_Distribute_Totaldoc());
		int actualCount = Integer.parseInt(assignPage.getEditAggn_Distribute_Totaldoc().getText());
		assignPage.getAssignmentSaveButton().waitAndClick(10);
		base.digitCompareEquals(purehit, actualCount, "assigned docs available in assignment",
				"assigned docs not available in assignment");
		base.passedStep("All assignment details displayed");
		login.logout();
	}

	/**
	 * Author :Arunkumar date: 01/12/2022 TestCase Id:RPMXCON-54119 Description :To
	 * verify that RMU can view the selected Save Search Result.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54119", enabled = true, groups = { "regression" })
	public void verifyRmuViewSavedSearch() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-54119");
		base.stepInfo("verify that RMU can view the selected Save Search Result.");
		String SearchName = "search" + Utility.dynamicNameAppender();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in as RMU");
		base.stepInfo("perform search");
		int purehit = session.basicContentSearch(Input.testData1);
		base.stepInfo("Enter search details and save");
		session.saveSearch(SearchName);
		base.stepInfo("navigate to saved search page");
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.navigateToSavedSearchPage();
		base.waitForElement(savedSearch.getSavedSearch_ApplyFilterButton());
		base.ValidateElement_Presence(savedSearch.getSelectSavedSearch(SearchName), "saved search");
		base.passedStep("last saved search displayed");
		login.logout();
	}

	/**
	 * Author :Arunkumar date: 01/12/2022 TestCase Id:RPMXCON-54120 Description :To
	 * verify that on clicking on 'Cancel' button, folders should not be displayed.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54120", enabled = true, groups = { "regression" })
	public void verifyCancelBtnOnFolderCreation() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-54120");
		base.stepInfo("verify that on clicking on 'Cancel' button, folders should not be displayed.");
		String folderName = "folder" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as PA");
		base.stepInfo("Navigate to manage-Tags and folder");
		tagsAndFolder = new TagsAndFoldersPage(driver);
		tagsAndFolder.navigateToTagsAndFolderPage();
		base.stepInfo("Click on folder tab and verify");
		tagsAndFolder.verifyExistingFoldersAvailabilityUnderRoot();
		base.stepInfo("select new folder option from action dropdown and verify");
		tagsAndFolder.verifyNewFolderCreationPopup();
		base.stepInfo("Enter folder name details and click on cancel");
		base.waitForElement(tagsAndFolder.getFolderName());
		tagsAndFolder.getFolderName().SendKeys(folderName);
		base.waitForElement(tagsAndFolder.getbtnFolderCancel());
		tagsAndFolder.getbtnFolderCancel().Click();
		base.stepInfo("verify cancelled folder availability");
		base.ValidateElement_Absence(tagsAndFolder.SelectFolderCheckbox(folderName), "cancelled folder");
		base.passedStep("new folder not displayed on folder tree");
		login.logout();
	}

	/**
	 * @author Brundha.T Test case id : RPMXCON-54257
	 * @Description :Verify that Categorization (Training Set : Folder) is working
	 *              correctly and properly
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54257", enabled = true, groups = { "regression" })
	public void verifyingFolderSetInProview() throws InterruptedException {

		base.stepInfo("RPMXC0N-54257 Proview");
		base.stepInfo("Verify that Categorization (Training Set : Folder) is working correctly and properly");

		Categorization categorize = new Categorization(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		String[] UserName = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };

		for (int i = 0; i < UserName.length; i++) {
			String folderName = "Folder" + Utility.dynamicNameAppender();
			// Login as User
			login.loginToSightLine(UserName[i], Password[i]);
			base.stepInfo("Logged in As " + UserName[i]);

			base.stepInfo("Creating new bulkfolder");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolder(folderName);

			base.stepInfo("Navigating to categorize page");
			categorize.navigateToCategorizePage();

			base.stepInfo("Selecting training set and doc to be analyzed");
			categorize.fillingTrainingSetSection("Folder", folderName, null, null);
			categorize.fillingStep2CorpusTab("folder", folderName, null, true);

			final BaseClass bc = new BaseClass(driver);
			final int Bgcount = bc.initialBgCount();
			System.out.println(Bgcount);

			driver.waitForPageToBeReady();
			base.stepInfo("Set cohesion level as 50%");
			categorize.getCohesionLevel().SendKeys("50");

			base.stepInfo("Run Categorize and click on 'NO'");
			categorize.runCategorization("No");
			bc.checkNotificationCount(Bgcount, 1);
			bc.getBackGroudTaskIcon().waitAndClick(10);
			categorize.getProviewNotification().waitAndClick(10);
			base.waitTime(3);
			String DocCount = categorize.getResultCount().getText();
			String[] Document = DocCount.split(" ");
			String ResultCoount = Document[1];
			System.out.println(ResultCoount);

			String ResultCount = categorize.getDocCount().getText();
			System.out.println(ResultCount);
			base.stepInfo("verifying cohesion level");
			if (Integer.valueOf(ResultCoount) == 50) {
				base.passedStep("Categorize page is with selected cohesion level");
			} else {
				base.failedStep("Categorize page not with selected cohesion level");
			}
			categorize.ViewInDocLIst();

			base.waitTime(3);
			base.stepInfo("verifying document count in doclist page");
			DocListPage doc = new DocListPage(driver);
			base.waitForElement(doc.getTableFooterDocListCount());
			String DocListCount = doc.getTableFooterDocListCount().getText();
			String[] doccount = DocListCount.split(" ");
			String DocumentCount = doccount[5];

			base.digitCompareEquals(Integer.valueOf(ResultCount), Integer.valueOf(DocumentCount),
					"Document count is displayed as expected", "Document count is not displayed");

			login.logout();
		}
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
		SoftAssert softassert = new SoftAssert();

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
		for (int i = 0; i < totalProdSets; i++) {
			softassert.assertEquals(categorize.getProdSet(result.get(i)).isDisplayed(), true);
			if (categorize.getProdSet(result.get(i)).isDisplayed()) {
				base.stepInfo(result.get(i) + " is displayed as expected in proview page ");
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
	 * @Author Date:NA ModifyDate:NA RPMXCON-54135
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
	 * @Author Date:NA ModifyDate:NA RPMXCON-54131
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
		System.out.println(searchlist);

		// navigate to categorize page
		categorize.navigateToCategorizePage();

		// Fetch displayed Searches in Categorization page
		categorize.selectTrainingSet("Identify by Saved Search");
		categorize.PBMclickArrow().Click();
		driver.scrollingToBottomofAPage();

		base.waitForElementCollection(categorize.getAvailableSearches());
		List<String> searchPresentInCategorize = base.availableListofElements(categorize.getAvailableSearches());
		System.out.println(searchPresentInCategorize);

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
		base.waitTime(3);
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
