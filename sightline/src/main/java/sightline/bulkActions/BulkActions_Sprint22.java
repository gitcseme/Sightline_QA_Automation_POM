package sightline.bulkActions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Sprint22 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
	}

	/**
	 * @author Jayanthi.Ganesan
	 */

	@Test(description = "RPMXCON-53164", enabled = true, groups = { "regression" })
	public void verifyBulkAssign_PAimpersonateAsRMU() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53614");
		baseClass.stepInfo("RMU impersonated from PAU should be able assign assignments to PAU user");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as PA'" + Input.pa1userName + "'");
		assignment = new AssignmentsPage(driver);

		// impersonating to Rmu
		baseClass.impersonatePAtoRMU();

		String assign = "Assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Successfully impersonated as RMU");

		List<String> userToAssign = Arrays.asList(Input.pa1userName, Input.pa2userName);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssign();
		assignment.assignmentCreation(assign, Input.codingFormName);

		// docs assigning to PA user impersonated RMU.
		assignment.addReviewersUsingList(userToAssign, "PAU");

		baseClass.passedStep(" PAU users impersonated as RMU can able to assign doc to assignment.");

		assignment.deleteAssgnmntUsingPagination(assign);

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53165", enabled = true, groups = { "regression" })
	public void verifyBulkAssign_DAimpersonateAsRMU() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53165");
		baseClass.stepInfo("RUM impersonated from DA should be able assign assignments to DA users");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as DA'" + Input.da1userName + "'");
		assignment = new AssignmentsPage(driver);
		baseClass.waitTime(2); //to avoid page loading issue for DA user login
		// impersonating to Rmu
		baseClass.impersonateDAtoRMU();

		String assign = "Assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Successfully impersonated as RMU");

		List<String> userToAssign = Arrays.asList(Input.da1userName);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssign();
		assignment.assignmentCreation(assign, Input.codingFormName);

		// docs assigning to DA user impersonated RMU.
		assignment.addReviewersUsingList(userToAssign, "DA");

		baseClass.passedStep(" DAU users impersonated as RMU can able to assign doc to assignment.");

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53557", enabled = true, groups = { "regression" })
	public void verifyBulktag() throws Exception {
		SavedSearch saveSearch = new SavedSearch(driver);

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String tagName = "tagName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-53557 ");
		baseClass.stepInfo("Verify that As a RMU  I will be able to assign Bulk Tag from saved search");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Loggedin As : " + Input.rmu1userName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.searchString1);
		int hitCount = Integer.parseInt(sessionSearch.verifyPureHitsCount());
		sessionSearch.saveSearch(searchName1);

		// selecting the savedSearch
		saveSearch.navigateToSSPage();
		saveSearch.SaveSearchToBulkTag(searchName1, tagName);
		baseClass.stepInfo("Bulk tag done from saved search screen " + tagName);

		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		// verifying for Tag Completed dcos count.
		tf.verifyTagDocCount(tagName, hitCount);

		// delete tags
		tf.navigateToTagsAndFolderPage();
		tf.deleteAllTags(tagName);

		// Deleting the SavedSearch
		saveSearch.navigateToSSPage();
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		loginPage.logout();

	}

	/**
	 * @author Raghuram.A
	 * @Date: 10/03/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that in Bulk Assign, the Email inclusive sampling
	 *              method working as expected. RPMXCON-54464
	 */
	@Test(description = "RPMXCON-54464", groups = { "regression" }, enabled = true)
	public void verifyParentDocCountDisplayedAsExpected() throws InterruptedException {

		AssignmentsPage assign = new AssignmentsPage(driver);
		DocListPage docList = new DocListPage(driver);

		String finalCount;
		String docCount;
		String assignName = "assignName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-54464 Bulk Actions");
		baseClass.stepInfo("To verify that in Bulk Assign, the Email inclusive sampling method working as expected");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Assignment creation
		baseClass.stepInfo("**Step-2 Create new assignment from assignments**");
		assign.createAssignment(assignName, Input.codingFormName);

		// performing searching and saving it in newly created node
		baseClass.stepInfo("**Step-3 Search for inclusive email  and click on assign to bulk assign**");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// Bulk Assign via SessionSearch
		baseClass.stepInfo("**Step-4 Select assignment name and sampling method as inclusive email  and finalize**");
		finalCount = assign.assignwithSamplemethod(assignName, "Inclusive Email", null);

		baseClass.stepInfo("**Step-5 Go to assignments and select the assignment  created**");
		assign.Viewindoclistfromassgn(assignName);
		driver.waitForPageToBeReady();

		// Parent doc count verification
		baseClass.stepInfo(
				"**Step-6 Navigate to doc list of assignment and check if the parent level docs are assigned**");
		docCount = docList.verifyingDocCount();

		// Doc count comparision
		baseClass.stepInfo(
				"Verify that count displayed in the \"Total\" are the count of inclusive email Documents only.");
		baseClass.textCompareEquals(finalCount, docCount,
				"Only Inclusive Email Documents are listed in the doclist page",
				"Other than parent level docs are listed - Count mismatches");

		// logout
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  BulkActions_Sprint22.**");
	}
}
