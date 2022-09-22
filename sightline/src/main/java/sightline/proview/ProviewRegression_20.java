package sightline.proview;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProviewRegression_20 {
	Driver driver;
	LoginPage login;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssert;
	Categorization categorize;

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
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
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
