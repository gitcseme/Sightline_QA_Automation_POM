package sightline.advancedSearch;

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
import pageFactory.DocListPage;
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
	DocListPage docList;

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
		docList = new DocListPage(driver);
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

	/**
	 * @author
	 * @Date: 08/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that 2 Search criteria gets combined with AND/OR/NOT
	 *              condition. RPMXCON-47612
	 */
	@Test(description = "RPMXCON-47612", enabled = true, groups = { "regression" })
	public void verifyThatTwoSearchCriteriaCombinedWithAND_OR_NOTCondition() throws Exception {

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47612");
		baseClass.stepInfo("Verify that 2 Search criteria gets combined with AND/OR/NOT condition");

		// getting the metaData pureHit Count
		int metaDataPureHitCount = sessionSearch.MetaDataSearchInAdvancedSearch(Input.metaDataName,
				Input.metaDataCustodianNameInput);

		// getting the Work Product Security Group pureHit Count
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("performing the Work Product Security Group.");
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		int workProductSGPureHitCount = sessionSearch.serarchWP();

		// Work Product Security Group pureHit Count Excluding the metaData pureHit
		// Count
		int expectedSGNotMetaPureHitCount = workProductSGPureHitCount - metaDataPureHitCount;

		// performing Combined Search of metaData with Work Product Security Group with
		// AND Operator
		baseClass.selectproject();
		baseClass
				.stepInfo("performing Combined Search of metaData with Work Product Security Group with AND Operator.");
		sessionSearch.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		sessionSearch.selectOperator("AND", 1);
		int metaAndSGPureHitCount = sessionSearch.serarchWP();
		// Verifying that Search results appears which satisfied both condition
		// 'metaData AND Work Product Security Group'
		baseClass.digitCompareEquals(metaAndSGPureHitCount, metaDataPureHitCount,
				"Verified that Search results appears which satisfied both condition 'metaData AND Work Product Security Group'.",
				"Search results Doesn't match with the Expected PureHit count");

		// performing Combined Search of metaData with Work Product Security Group with
		// OR Operator
		baseClass.selectproject();
		baseClass.stepInfo("performing Combined Search of metaData with Work Product Security Group with OR Operator.");
		sessionSearch.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		sessionSearch.selectOperator("OR", 1);
		int metaOrSGPureHitCount = sessionSearch.serarchWP();
		// Verified that Search results appears which satisfied anyone condition
		// 'metaData OR Work Product Security Group'
		baseClass.digitCompareEquals(metaOrSGPureHitCount, workProductSGPureHitCount,
				"Verified that Search results appears which satisfied anyone condition 'metaData OR Work Product Security Group'.",
				"Search results Doesn't match with the Expected PureHit count");

		// performing Combined Search of Work Product Security Group with metaData with
		// NOT Operator
		baseClass.selectproject();
		baseClass.stepInfo(
				"performing Combined Search of  Work Product Security Group with metaData with NOT Operator.");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		sessionSearch.advMetaDataSearchQueryInsertTest(Input.metaDataName, Input.metaDataCustodianNameInput);
		sessionSearch.selectOperator("NOT", 1);
		int SGNotMetaPureHitCount = sessionSearch.serarchWP();
		// "Verified that Search results appears NOT having metaData Document Count
		// 'Work Product Security Group NOT metaData'
		baseClass.digitCompareEquals(SGNotMetaPureHitCount, expectedSGNotMetaPureHitCount,
				"Verified that Search results appears NOT having metaData Document Count 'Work Product Security Group NOT metaData'.",
				"Search results Doesn't match with the Expected PureHit count");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 08/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Advanced Search is working properly for Datasource
	 *              Metadata. RPMXCON-57058
	 */
	@Test(description = "RPMXCON-57058", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorkingProperlyForDatasourceMetadata(String userName, String password)
			throws Exception {

		String dataSource;
		String[] columnToSelect = { Input.dataSourceHeader };
		DocListPage docList = new DocListPage(driver);
		List<String> dataSourceColumnValues = new ArrayList<String>();

		// login as User
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("Test case Id: RPMXCON-57058");
		baseClass.stepInfo("Verify that Advanced Search is working properly for Datasource Metadata.");

		// getting metaData DataSource SearchString
		baseClass.stepInfo("getting metaData DataSource SearchString");
		sessionSearch.advancedContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		docList.SelectColumnDisplayByRemovingExistingOnes(columnToSelect);
		dataSourceColumnValues = docList.getColumnValue(columnToSelect[0], false);
		dataSource = dataSourceColumnValues.get(0);
		baseClass.stepInfo("DataSource searchString : '" + dataSource + "'");

		// performing metaData search with DataSource
		baseClass.selectproject();
		baseClass.stepInfo("Performing metaData Search With DataSource");
		sessionSearch.advancedMetaDataSearch("DataSource", null, dataSource, null);

		// viewing the documents in docList
		sessionSearch.ViewInDocList();

		// adding the DataSource metaData column in DocList
		baseClass.stepInfo("adding DataSource metaData column in DocList.");
		docList.SelectColumnDisplayByRemovingExistingOnes(columnToSelect);
		// getting all the values from DataSource column
		baseClass.stepInfo("getting all the values from DataSource column.");
		dataSourceColumnValues = docList.getColumnValue(columnToSelect[0], false);

		// verifying that Advanced Search is working properly for Datasource Metadata by
		// comparing dataSource searchString with DataSource column values in DocList
		baseClass.compareListWithString(dataSourceColumnValues, dataSource,
				"Verified that Advanced Search is working properly for Datasource Metadata.",
				"Advanced Search is Not working properly for Datasource Metadata.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 08/16/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Advanced Search is working properly for
	 *              DocumentFileTypeName Metadata. RPMXCON-57059
	 */
	@Test(description = "RPMXCON-57059", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyAdvancedSearchWorkingProperlyForDocumentFileTypeNameMetadat(String userName, String password)
			throws Exception {

		String[] columnToSelect = { Input.docFileType };
		String docFileTypeValue = "TIFF image";
		DocListPage docList = new DocListPage(driver);
		List<String> docFileTypeColumnValues = new ArrayList<String>();

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57059.");
		baseClass.stepInfo("Verify that Advanced Search is working properly for  DocumentFileTypeName Metadata.");

		// performing metaData search with DocFileType
		baseClass.stepInfo("Performing metaData Search With DocFileType");
		sessionSearch.MetaDataSearchInAdvancedSearch(Input.docFileType, Input.searchDocFileType);
		sessionSearch.ViewInDocList();

		// adding the DocFileType metaData column in DocList
		baseClass.stepInfo("adding DocFileType metaData column in DocList.");
		docList.SelectColumnDisplayByRemovingExistingOnes(columnToSelect);
		// getting all the values from DocFileType column
		baseClass.stepInfo("getting all the values from DocFileType column.");
		docFileTypeColumnValues = docList.getColumnValue(columnToSelect[0], false);

		// Verifying that Advanced Search is working properly for DocumentFileTypeName
		// Metadata.
		baseClass.compareListWithString(docFileTypeColumnValues, docFileTypeValue,
				"verified that  Advanced Search is working properly for DocumentFileTypeName Metadata ",
				"Advanced Search is Not working properly for DocumentFileTypeName Metadata");

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
