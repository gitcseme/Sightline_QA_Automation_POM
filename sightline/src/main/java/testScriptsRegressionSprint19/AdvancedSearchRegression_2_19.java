package testScriptsRegressionSprint19;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_2_19 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	AssignmentsPage assignmentPage;
	DocViewPage docView;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "assignmentHelperWindoStatus")
	public Object[][] assignmentHelperWindoStatus() {
		Object[][] users = { { "Completed" }, { "Assigned" } };
		return users;
	}

	/**
	 * @author Raghuram A
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify an an user login, I will be able to select multiple
	 *              node from Saved search results column under Work Product tab &
	 *              set that as a search criteria for advanced search. RPMXCON-57049
	 */
	@Test(description = "RPMXCON-57049", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyUserAbleToSelectMultipleNodeFromSavedSearchResultsInAdvancedSearch(String userName,
			String password) throws Exception {

		String searchName1 = "savedSearch" + Utility.dynamicNameAppender();
		String searchName2 = "savedSearch" + Utility.dynamicNameAppender();
		List<String> nodeList = new ArrayList<String>();
		HashMap<String, String> nodeAndSavedSearchPair = new HashMap<String, String>();
		HashMap<String, String> savedSearchAndSearchIdPair = new HashMap<String, String>();

		// login as PA
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("RPMXCON-57049 Advanced Search");
		baseClass.stepInfo(
				"To verify an an user login, I will be able to select multiple node from Saved search results column under Work Product tab & set that as a search criteria for advanced search");

		// creating Node in savedSearch page
		nodeList.add(savedSearch.createNewSearchGrp(Input.mySavedSearch));
		nodeList.add(savedSearch.createNewSearchGrp(Input.mySavedSearch));

		// performing search and saving it in created Node
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.saveAdvanceSearchInNode(searchName1, nodeList.get(0));
		nodeAndSavedSearchPair.put(nodeList.get(0), searchName1);
		sessionSearch.saveAdvanceSearchInNode(searchName2, nodeList.get(1));
		nodeAndSavedSearchPair.put(nodeList.get(1), searchName2);

		// getting search ID of the search from savedSearch.
		savedSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, nodeList.get(0));
		savedSearchAndSearchIdPair.put(nodeAndSavedSearchPair.get(nodeList.get(0)),
				savedSearch.getSelectSearchWithID(nodeAndSavedSearchPair.get(nodeList.get(0))).getText());
		savedSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, nodeList.get(1));
		savedSearchAndSearchIdPair.put(nodeAndSavedSearchPair.get(nodeList.get(1)),
				savedSearch.getSelectSearchWithID(nodeAndSavedSearchPair.get(nodeList.get(1))).getText());

		// configuring the workProduct search Query by inserting multiple savedSearch
		// Node.
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("configuring the workProduct search Query by inserting multiple SavedSearch Results.");
		sessionSearch.insertMultipleSavedSearchResultsInWp(nodeList);

		// getting the actual Configure Search Query
		String actualConfiguredQuery = sessionSearch.getSavedSearchQueryAS().getText();

		// verify that Selected Node inserted as a search criteria for advanced search.
		assertion.assertEquals(actualConfiguredQuery
				.contains(savedSearchAndSearchIdPair.get(nodeAndSavedSearchPair.get(nodeList.get(0)))), true);
		assertion.assertEquals(actualConfiguredQuery
				.contains(savedSearchAndSearchIdPair.get(nodeAndSavedSearchPair.get(nodeList.get(1)))), true);
		assertion.assertAll();
		baseClass.passedStep("verified that Selected Node inserted as a search criteria for advanced search.");

		// delete node from savedSearch
		savedSearch.deleteNode(Input.mySavedSearch, nodeList.get(0));
		savedSearch.deleteNode(Input.mySavedSearch, nodeList.get(1));

		// logOut
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that workproduct - Assignments helper window does not
	 *              truncate "Insert into Query" button at the bottom of the page.
	 *              RPMXCON-48208
	 */
	@Test(description = "RPMXCON-48208", dataProvider = "assignmentHelperWindoStatus", enabled = true, groups = {
			"regression" })
	public void verifyWorkProductAssignmentHelperWindowDoesNotTruncateInsetIntoQueryButton(String status)
			throws Exception {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-48208");
		baseClass.stepInfo(
				"Verify that workproduct - Assignments helper window does not truncate \"Insert into Query\" button at the bottom of the page");

		// create Assignment
		assignmentPage.createAssignment(assignmentName, Input.codeFormName);

		// configure Work Product Search with Assignment and verifying the 'Insert into
		// Query' button
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.waitForElement(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().Click();
		baseClass.stepInfo("Configuring the Assignment WorkProduct Search Query with '" + status + "' Status.");
		sessionSearch.selectAssignmentAndReviewers_Status_dateRange(assignmentName, Input.rev1userName, null, null,
				status, true);
		baseClass.stepInfo(
				"Verified that workproduct - Assignments helper window does not truncate \"Insert into Query\" button at the bottom of the page");

		// delete Assignment
		assignmentPage.deleteAssignment(assignmentName);

		// logOut
		loginPage.logout();

	}

	/**
	 * @author S
	 * @Date: 08/10/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Modified reviewer remarks for audio documents is
	 *              working correctly in Advanced Search.. RPMXCON-46884
	 */
	@Test(description = "RPMXCON-46884", enabled = true, groups = { "regression" })
	public void verifyModifiedReviewerRemarkForAudioDocumentsWorkingCorrectlyInAdvSearch()
			throws InterruptedException, Exception {

		String remark = "remark" + Utility.dynamicNameAppender();
		String modifiedRemark = "modified";
		int noOfAudioRemarkAdd = 1;
		int ExpectedRemarkDocCountAfterModification = 0;
		Map<String, String> updateDatas = new HashMap<String, String>();

		// login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-46884");
		baseClass.stepInfo(
				"Verify that Modified reviewer remarks for audio documents is working correctly in  Advanced Search.");

		// performing audio search and navigating to DocView
		baseClass.stepInfo("Performing Audio Search.");
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		baseClass.stepInfo("adding pureHit to Shopping cart and viewing the Documents in docView page.");
		sessionSearch.ViewInDocViews();

		// adding reamrk to audio file
		docView.audioRemark(remark);

		// performing remark search and viewing the remark Documents in docView page.
		baseClass.selectproject();
		int remarkDocCountBeforeModification = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);
		assertion.assertEquals(remarkDocCountBeforeModification, noOfAudioRemarkAdd);
		baseClass.stepInfo("adding pureHit to Shopping cart and viewing the Documents in docView page.");
		sessionSearch.ViewInDocViews();

		// modifying the remark
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		docView.editAndVerifyData(remark, updateDatas, modifiedRemark);

		// verifying whether that Application displaying the latest count excluding the
		// documents for which remarks was modified.
		baseClass.selectproject();
		int remarkDocCountAfterModification = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);
		assertion.assertEquals(remarkDocCountAfterModification, ExpectedRemarkDocCountAfterModification);
		assertion.assertNotEquals(remarkDocCountBeforeModification, remarkDocCountAfterModification);
		assertion.assertAll();
		baseClass.passedStep(
				"Verified that Application displaying the latest count excluding the documents for which remarks was modified.");

		// deleting the remark
		baseClass.selectproject();
		sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", modifiedRemark);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("adding pureHit to Shopping cart and viewing the Documents in docView page.");
		docView.deleteAudioRemark();

		// logOut
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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
