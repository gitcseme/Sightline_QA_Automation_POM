package testScriptsRegressionSprint24;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProviewRegression_24 {
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
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
