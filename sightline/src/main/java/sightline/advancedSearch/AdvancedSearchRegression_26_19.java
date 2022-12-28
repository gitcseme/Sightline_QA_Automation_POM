package sightline.advancedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_26_19 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	SecurityGroupsPage securityGroupsPage;
	DocViewRedactions docViewRedact;
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
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
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


	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              EmailSentDate date/time field with "Range"operator.RPMXCON-57235
	 */

	@Test(description = "RPMXCON-57235", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForEmailSentDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "EmailSentDate";
		String operator = "Range";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57235 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for EmailSentDate date/time field with \"Range\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for EmailSentDate
		// date/time field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savedSearch_SearchandSelect(searchName, "Yes");
		savedSearch.savedSearchExecute_Draft(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for EmailSentDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "EmailSentDate date/time" field search result return documents
		// which satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              EmailSentDate date/time field with \"Is\"operator.RPMXCON-57234
	 */

	@Test(description = "RPMXCON-57234", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForEmailSentDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "EmailSentDate";
		String operator = "IS";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57234 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for EmailSentDate date/time field with \"Is\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for EmailSentDate
		// date/time field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for EmailSentDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "EmailSentDate date/time" field search result return documents
		// which satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              CreateDate date/time field with \"Range\"operator.RPMXCON-57233
	 */

	@Test(description = "RPMXCON-57233", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForCreateDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "CreateDate";
		String operator = "Range";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57233 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for CreateDate date/time field with \"Range\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for CreateDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for CreateDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "CreateDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that CreateDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Saved Search >> Execute works properly for
	 *              CreateDate date/time field with "Is"operator[RPMXCON-57232]
	 */

	@Test(description = "RPMXCON-57232", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForCreateDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "CreateDate";
		String operator = "IS";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57232 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for CreateDate date/time field with \"Is\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for CreateDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for CreateDate  date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "CreateDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that CreateDate date/time field search result should return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that all Search options get displayed inside "Content &
	 *              Metadata" block on "Advanced Search" screen.RPMXCON-57236
	 */

	@Test(description = "RPMXCON-57037", enabled = true, groups = { "regression" })
	public void verifySearchOptionsDisplayedContentMetaDataBlock() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-57037 Advanced Search");
		baseClass.stepInfo(
				"Verify that all  Search options get displayed inside \"Content & Metadata\" block on \"Advanced Search\" screen");
		SoftAssert softassert = new SoftAssert();
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("configure and perform MasterDate metadata search query in Advanced Search page");
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.getAdvancedSearchLink().waitAndClick(5);
		baseClass.waitForElement(sessionSearch.getContentAndMetaDatabtn());
		sessionSearch.getContentAndMetaDatabtn().waitAndClick(5);
		baseClass.stepInfo("Content & Metadata block is clicked as expected");

		// content metadata options displayed
		baseClass.waitForElement(sessionSearch.getNewSearch_MetadataBtn());
		softassert.assertTrue(sessionSearch.getNewSearch_MetadataBtn().Displayed());
		baseClass.passedStep("Metadata option is displayed successfully");
		baseClass.waitForElement(sessionSearch.getCommentsFieldAndRemarksSec());
		softassert.assertTrue(sessionSearch.getCommentsFieldAndRemarksSec().Displayed());
		baseClass.passedStep("Comments/Remarks option is displayed successfully");
		baseClass.waitForElement(sessionSearch.getOperatorDD());
		softassert.assertTrue(sessionSearch.getOperatorDD().Displayed());
		sessionSearch.getOperatorDD().waitAndClick(5);
		baseClass.passedStep("operator option is displayed successfully");

		baseClass.waitForElement(sessionSearch.getOperatorAND());
		softassert.assertTrue(sessionSearch.getOperatorAND().Displayed());
		baseClass.passedStep("AND operator option is displayed successfully");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getOperatorOR());
		softassert.assertTrue(sessionSearch.getOperatorOR().Displayed());
		baseClass.passedStep("OR operator option is displayed successfully");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getOperatorNOT());
		softassert.assertTrue(sessionSearch.getOperatorNOT().Displayed());
		baseClass.waitForElement(sessionSearch.getOperatorNOT());
		baseClass.passedStep("NOT operator option is displayed successfully");
		softassert.assertTrue(sessionSearch.getContentMetaDataBtnDisabled().isElementPresent());
		baseClass.passedStep("Content & Metadata block has been disabled as expected");
		softassert.assertAll();

		// logOut
		loginPage.logout();
	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify an an PA user login, I will be able to select all the
	 *              Security Groups from Security Group column under Work Product
	 *              tab & set that as a search criteria for advanced
	 *              search.RPMXCON-57047
	 */

	@Test(description = "RPMXCON-57047", enabled = true, groups = { "regression" })
	public void verifySgWpTabSearchCriteriaAdvancedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-57047 Advanced Search");
		// login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo(
				"To verify an an PA user login, I will be able to select all the Security Groups from Security Group column under Work Product tab & set that as a search criteria for advanced search");
		sessionSearch.switchToWorkproduct();
		baseClass.waitForElement(sessionSearch.getSecurityGrpBtn());
		sessionSearch.getSecurityGrpBtn().waitAndClick(5);
		baseClass.waitTime(5);
		baseClass.waitForElement(sessionSearch.getSelectAllSecurityGroupBtn());
		sessionSearch.getSelectAllSecurityGroupBtn().waitAndClick(5);
		baseClass.stepInfo("All Security Groups is selected from Security Group list as expected");
		driver.waitForPageToBeReady();
		List<String> Securitygroups = baseClass.availableListofElements(sessionSearch.getSecurityNamesTree());
		baseClass.stepInfo(Securitygroups + "  Present in workproduct SG list");
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(5);
		baseClass.stepInfo("Insert Into Query is Clicked");
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		String Searchbox = sessionSearch.getEnterSearchBox().getText();
		for (int i = 0; i < Securitygroups.size(); i++) {
			if (Searchbox.contains(Securitygroups.get(i))) {
				baseClass.passedStep(Securitygroups.get(i)
						+ "Selected Security Group has been  inserted in search criteria for advanced search  as expected");
			} else {
				baseClass.failedStep(
						"Selected Security Group is did not inserted in search criteria for advanced search ");
			}
		}
		loginPage.logout();

	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              MasterDate date/time field with \"Range\"operator.RPMXCON-57231
	 */
	@Test(description = "RPMXCON-57231", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForMasterDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "MasterDate";
		String operator = "Range";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-57231 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for MasterDate date/time field with \"Range\"operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for MasterDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for MasterDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that MasterDate date/time field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that MasterDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              MasterDate date/time field with \"Is\"operator.RPMXCON-57230
	 */

	@Test(description = "RPMXCON-57230", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForMasterDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "MasterDate";
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-57230 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for MasterDate date/time field with \"Is\"operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for MasterDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for MasterDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "MasterDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that MasterDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Advanced Search works properly for EmailSentDate
	 *              date/time field with \"Range\" operator.RPMXCON-57227
	 */

	@Test(description = "RPMXCON-57227", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorksProperlyForEmailSentDateFieldWithRangeOperator() {

		String metaDataField = "EmailSentDate";
		String operator = "Range";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57227 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for EmailSentDate  date/time field  with \"Range\" operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Configure MetaData Query in Advanced Search with MetaData as 'EmailSentDate'
		// and Operator as 'Range'
		baseClass.stepInfo(
				"Configure  MetaData Query in Advanced Search with MetaData as 'EmailSentDate' and Operator as 'Range'.");
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));

		// perform search action and Verify that "EmailSentDate date/time" field search
		// result return documents which satisfied above configured query.
		baseClass.stepInfo("perform search action");
		sessionSearch.serarchWP();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Advanced Search works properly for EmailSentDate
	 *              date/time field with \"Is\" operator.RPMXCON-57226
	 */

	@Test(description = "RPMXCON-57226", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorksProperlyForEmailSentDateFieldWithISOperator() {

		String metaDataField = "EmailSentDate";
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57226 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for EmailSentDate date/time field with \"Is\" operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Configure MetaData Query in Advanced Search with MetaData as 'EmailSentDate'
		// and Operator as 'IS'
		baseClass.stepInfo(
				"Configure  MetaData Query in Advanced Search with MetaData as 'EmailSentDate' and Operator as 'IS'.");
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, dateFormat.format(date), null);

		// perform search action and Verify that "EmailSentDate date/time" field search
		// result return documents which satisfied above configured query.
		baseClass.stepInfo("perform search action");
		sessionSearch.serarchWP();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that configured query with MasterDate get inserted
	 *              properly into Advanced Search Query builder screen.RPMXCON-57054
	 */
	@Test(description = "RPMXCON-57054", enabled = true, groups = { "regression" })
	public void verifyConfiguredQueryWithMasterDateInsertedProperlyIntoAdvancedSearchQueryBuilderScreen() {

		String[] metaDataField = { "MasterDate", "CreateDate" };
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String searchString = dateFormat.format(date);

		baseClass.stepInfo("Test case Id: RPMXCON-57054 Advanced Search.");
		baseClass.stepInfo(
				"Verify that configured query with MasterDate get inserted properly into Advanced Search Query builder screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"Configure  MetaData Query in Advanced Search with MetaData as 'MasterDate' and Operator as 'IS'.");

		// Configure MetaData Query in Advanced Search with MetaData as 'MasterDate' &
		// 'CraeteDate'
		// and Operator as 'IS'
		for (int i = 0; i < metaDataField.length; i++) {

			baseClass.selectproject();
			sessionSearch.advancedMetaDataForDraft(metaDataField[i], operator, searchString, null);

			// Verify that configured query with MasterDate get inserted properly into
			// Advanced Search Query builder screen
			String configuredQuery = sessionSearch.getQueryTextArea().getText();
			baseClass.compareTextViaContains(configuredQuery, metaDataField[i],
					"metaData : '" + metaDataField[i] + "' is present in the Configured Query : '" + configuredQuery
							+ "' in Query Bulider Screen.",
					"metaData : '" + metaDataField[i] + "' is Not present in the Configured Query : '" + configuredQuery
							+ "' in Query Bulider Screen");
			baseClass.compareTextViaContains(configuredQuery, searchString,
					"Expected Search Query : '" + searchString + "' is present in the Configured Query : '"
							+ configuredQuery + "' in Query Bulider Screen.",
					"Expected Search Query : '" + metaDataField[i] + "' is Not present in the Configured Query : '"
							+ configuredQuery + "' in Query Bulider Screen.");
			baseClass.passedStep(
					"Verified that Configured query with Metadata should get inserted properly into Advanced Search Query builder screen.");
		}

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Advanced Search works properly for MasterDate
	 *              field with \"Is\" operator and NOT having time
	 *              components.[RPMXCON-49169]
	 */

	@Test(description = "RPMXCON-49169", enabled = true, groups = { "regression" })
	public void verifiedAdancedSearchWorkProperlyForMasterDatewithISOperatorAndNotHavingTimeComponents() {

		String metaDataField = "MasterDate";
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-49169 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for 'MasterDate' field with \"Is\" operator and NOT having time components.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configuring MetaData Search Query with metaData as MasterDate and Operator as
		// 'IS'.
		baseClass.stepInfo("configuring MetaData Search Query with metaData as MasterDate and Operator as 'IS'.");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, dateFormat.format(date), null);

		// Click on Search and Verify that "MasterDate" field search result return
		// documents which satisfied above configured query.
		baseClass.stepInfo("Click on 'Search' button");
		sessionSearch.serarchWP();
		baseClass.passedStep(
				"Verified that \"MasterDate\" field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : verify that Proximity Queries along with Regular Expression
	 *              search in Korean should return the result correctly in Advanced
	 *              Search.[RPMXCON-62495]
	 */

	@Test(description = "RPMXCON-62495", enabled = true, groups = { "regression" })
	public void verifyProximityQueriesWithRegularExpressionSearchInKoreanReturnResultCorrectlyInAdvancedSearch()
			throws InterruptedException {

		String searchString = "\"(\"##[0-9]{4}\") 월\"~4";

		baseClass.stepInfo("Test case Id: RPMXCON-62495 Advanced Search.");
		baseClass.stepInfo(
				"verify that Proximity Queries along with Regular Expression search in Korean should return the result correctly in Advanced Search.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(false, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button and Verify that correct result appears when combine
		// Proximity Queries and Regular Expression in Advanced Search Query Screen.
		sessionSearch.tallyContinue(5);
		sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"verified that correct result appears when combine Proximity Queries and Regular Expression in Advanced Search Query Screen.");

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

		System.out.println("**Executed Advanced search Regression2_21**");
	}

}
