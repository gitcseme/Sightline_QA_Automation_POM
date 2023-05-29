package testScriptsSmoke;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.testng.Assert;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;

public class DocViewandAccusoftPrizm {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	SecurityGroupsPage securityGroupsPage;
	DocViewRedactions docViewRedact;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignmentPage;
	DocViewPage docView;
	BatchRedactionPage batch;
	KeywordPage keywordPage;
	BatchPrintPage batchPrint;
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
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
		tagPage = new TagsAndFoldersPage(driver);
		docView = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver); 
		batch = new BatchRedactionPage(driver); 
		batchPrint = new BatchPrintPage(driver);

	}
	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu2userName, Input.rmu2password }, { Input.pa2userName, Input.pa2password },
				 };
		return users;
	}
	
	@Test(description = "RPMXCON-57227", enabled = true, groups = { "Smoke" })
	public void verifyAdvancedSearchWorksProperlyForEmailSentDateFieldWithRangeOperator() {

		String metaDataField = "EmailSentDate";
		String operator = "Range";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57227 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for EmailSentDate  date/time field  with \"Range\" operator.");

		// login
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

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
	@Test(description = "RPMXCON-47612", enabled = true, groups = { "regression" })
	public void verifyThatTwoSearchCriteriaCombinedWithAND_OR_NOTCondition() throws Exception {

		// login as PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);

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
	
	@Test(description = "RPMXCON-57335", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForProximityHavingTwoPhrasesWithinBracketsInAdvancedSearch() {

		String searchString = "\"(\"development methodology\") (\"money related\")\"~5";
		String exampleSearchString = "\"(\"development methodology\") (\"money related\")\"~5";

		baseClass.stepInfo("Test case Id: RPMXCON-57335 Advanced Search.");
		baseClass.stepInfo(
				"Verify that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		baseClass.waitTime(2);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		// Verify that result appears for proximity having 2 Phrases within brackets in
		// Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// verify that pureHit appear for proximity having 2 Phrases within brackets
		// match with pureHit appear for given example proximity Search Query.
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for proximity having 2 Phrases within brackets match with pureHit appear for given example proximity Search Query.");

		// logOut
		loginPage.logout();

	}
	
	@Test(description = "RPMXCON-63566", groups = {"regression" })
	public void verifyConceptualDefaultPS() throws InterruptedException {
		String saveSearchName = "resubmit" + Utility.dynamicNameAppender();

		 sessionSearch = new SessionSearch(driver);

		 loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);

		baseClass.stepInfo("Test case Id: RPMXCON-63566 Advanced Search");
		baseClass.stepInfo(
				"Verify that while performing conceptual search, slider value(Precision Sensitivity) and Precision Sensitivity text box value is same in its default value as well as when we change its slider value.");

		sessionSearch.navigateToAdvancedSearchPage();
		String precisionValueBeforeSearch = sessionSearch.verifyingPrecisionValue(true, Input.conceptualSearchString1 , true , true);
		sessionSearch.saveAndReturnPureHitCount();

		loginPage.logout();
	}
	@Test(description ="RPMXCON-57453", groups = { "regression" })
	public void verifyBasicSearch2() throws ParseException, InterruptedException, IOException {
		// login as PA

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("57453 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U|C\"~5 OR \"U|C\" OR ( \"##U|C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		baseClass.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		loginPage.logout();

	}
	@Test(description = "RPMXCON-48807", enabled = true, groups = { "regression" })
	public void verifyReport() throws InterruptedException {
		String searchName = "SearchName" + Utility.dynamicNameAppender();
		// will login as RMU
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Tescase ID :RPMXCON-48807");
		baseClass.stepInfo(
				"Verify that once batch redaction is successful user should be able to view the report on click of 'Click here for report'"
						+ " link for same from batch redaction history");
		String Filename1 = baseClass.GetLastModifiedFileName();
		baseClass.stepInfo(Filename1 + "Last Modified File name before Downloading the report");
		// Search The Query
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		// will perform the batchRedaction action
		batch.savedSearchBatchRedaction1(Input.defaultRedactionTag, searchName);
		batch.getPopupYesBtn().Click();
		batch.verifyBatchHistoryStatus(searchName);
		batch.VerifyBGMessageForReportDownload(searchName);
		Thread.sleep(10000); // added to wait until downnload completed and updated in the directory.
		String Filename2 = baseClass.GetLastModifiedFileName();
		baseClass.stepInfo(Filename2 + "Last Modified File name after Downloading the report");
		if ((Filename2 != Filename1)) {
			String actualfileName = FilenameUtils.getBaseName(Filename2);
			String fileFormat = FilenameUtils.getExtension(Filename2);
			String expectedFormat = "xlsx";
			baseClass.stepInfo("Downloaded file name is" + actualfileName);
			baseClass.stepInfo("Downloaded fileFormat is" + fileFormat);
			Assert.assertEquals(fileFormat, expectedFormat);
			baseClass.passedStep("Sucessfully verified whether the File Exported in excel format");
		} else {
			baseClass.failedStep("File not downloaded");
		}

		// Delete Search
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		loginPage.logout();
	}
	@Test(description = "RPMXCON-52529", enabled = true, groups = { "regression" })
	public void verifyPACanDeleteKeywords() throws InterruptedException, AWTException {
		KeywordPage keyWord = new KeywordPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52529");
		baseClass.stepInfo("To verify when Project Admin user deletes keyword group and keywords");
		String color = "Blue";
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa2userName + "");
		String keywordName = "deleteKeyword01"+Utility.dynamicNameAppender();
		
		
		// Navigate to Keywords Page and create keyword with Wildcard
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordName, color);
		keyWord.deleteKeywordByNameAndCancel(keywordName);
		baseClass.passedStep("Keyword group, keywords is not deleted successfully.");
		
		keyWord.deleteKeywordByName(keywordName);
		baseClass.passedStep("Keyword group, keywords is deleted and success message is displayed.");
		
		loginPage.logout();
		
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("User successfully logged into slightline webpage as RMU with " + Input.rmu2userName + "");
		
		// Navigate to Keywords Page and create keyword with Wildcard
		keyWord.navigateToKeywordPage();
		keyWord.addKeyword(keywordName, color);
		keyWord.deleteKeywordByNameAndCancel(keywordName);
		baseClass.passedStep("Keyword group, keywords is not deleted successfully.");
		
		keyWord.deleteKeywordByName(keywordName);
		baseClass.passedStep("Keyword group, keywords is deleted and success message is displayed.");
		
		loginPage.logout();
	}
	@Test(description ="RPMXCON-54934",enabled = true, groups = { "regression" })
	public void VerifyMetaList_QuickBatch() throws InterruptedException, ParseException, IOException {
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("Test case Id: RPMXCON-54934");
		baseClass.stepInfo("Quick Batch assignment - Default Metadata list is retained even after modifying Quick batch");
		// Creating quick batch assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.quickbatch();
		String assignmentQB1 = "AR2Assignment" + Utility.dynamicNameAppender();
		assignmentPage.createNewquickBatchWithoutReviewer(assignmentQB1, Input.codeFormName);
		baseClass.stepInfo("Created Quick batch Assignment --" + assignmentQB1);
		// opening the quick batch assignment in edit mode
		assignmentPage.editAssignmentUsingPaginationConcept(assignmentQB1);
		assignmentPage.getParentAssignmentGroupName().Displayed();
		assignmentPage.getSelectedClassification().selectFromDropdown().selectByVisibleText("2LR");
		assignmentPage.addReviewerAndDistributeDocs();
		baseClass.stepInfo("Modified the assignment and distributed docs to the reviewer");
		// verifying the metadata list by clicking select metadata button in assignment
		// page and from doc view page.
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		assignmentPage.NavigateToNewEditAssignmentPage("edit");
		try {
			assignmentPage.ValidateRetainedMetaDataList(assignmentQB1);
			baseClass.passedStep(
					"Sucessfully verified that Default Metadata list is retained even after modifying bulk assignment");
		} catch (Exception e) {
			baseClass.stepInfo(e.getMessage());
			baseClass.failedStep("Exception occurred");

		}
		loginPage.logout();
	}
	
	@Test(description = "RPMXCON-53673", enabled = true, groups = { "regression" })
	public void verifyDeleteSGCannotAccessInRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53673");
		baseClass.stepInfo(
				"To verify that after deleting the Security Group, RMU or Reviewer cannot access the details.");
		sessionSearch = new SessionSearch(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa2userName + "");

		baseClass.stepInfo("navigate To SecurityGroupPage Create and assign user");
		sgpage.createSecurityGroups(SGname);
		docViewRedact.assignAccesstoSGs(SGname, Input.rmu2userName);
		sgpage.deleteSecurityGroups(SGname);
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu2userName + "");
		baseClass.waitForElement(baseClass.getsgNames());
		baseClass.getsgNames().waitAndClick(5);
		if (!baseClass.getSelectsg(SGname).isDisplayed()) {
			baseClass.passedStep("User cannot able to access the security group");
		} else {
			baseClass.failedStep("User can able to access the security group");
		}
		loginPage.logout();

	}
	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa2userName, Input.pa2password, "Project Administrator" },
				{ Input.rmu2userName, Input.rmu2password, "Review Manager" } };
		return users;
	}
	@Test(description = "RPMXCON-52934", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void tagFoderGroupDocToggleAction(String userName, String password, String role) throws Exception {

		String tagGroupName = "aTagGroupR" + Utility.dynamicNameAppender();
		String TagName = "aTagR" + Utility.dynamicNameAppender();
		String TagName2 = "aTagR" + Utility.dynamicNameAppender();

		String folderGroupName = "aFolderGroupR" + Utility.dynamicNameAppender();
		String FolderName = "aFolderR" + Utility.dynamicNameAppender();
		String FolderName2 = "aFolderR" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-52934 - TagsAndFolder Sprint 24");
		baseClass.stepInfo(
				"Verify the count is displayed correctly instead of number of documents in tag group /folder group.");

		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as : " + userName);

		// Tags And Folder
		tagPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagPage.selectallTagRoot();
		tagPage.createTagGroup(Input.securityGroup, tagGroupName, "Success", null);
		baseClass.waitForElement(tagPage.getTagNameDataCon(tagGroupName));
		tagPage.getTagNameDataCon(tagGroupName).waitAndClick(20);
		tagPage.CreateTagCC(TagName, Input.securityGroup);
		baseClass.waitForElement(tagPage.getTagNameDataCon(tagGroupName));
		tagPage.getTagNameDataCon(tagGroupName).waitAndClick(20);
		tagPage.CreateTagCC(TagName2, Input.securityGroup);

		// Tags And Folder
		tagPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagPage.selectallFolderRoot();
		tagPage.createFolderGroup(Input.securityGroup, folderGroupName, "Success", null);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tagPage.getTagNameDataCon(folderGroupName));
		tagPage.getTagNameDataCon(folderGroupName).waitAndClick(20);
		tagPage.CreateFolderCC(FolderName, Input.securityGroup);
		baseClass.waitForElement(tagPage.getTagNameDataCon(folderGroupName));
		tagPage.getTagNameDataCon(folderGroupName).waitAndClick(20);
		tagPage.CreateFolderCC(FolderName2, Input.securityGroup);

		// Calculate the unique doc count for the respective searches
		sessionSearch.basicContentSearch(Input.searchString9);
		sessionSearch.addPureHit();
		sessionSearch.getNewSearchButton().waitAndClick(10);
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocListWithOutPureHit();
		
		int aggregateHitCount = savedSearch.docListPageFooterCountCheck();
		System.out.println(aggregateHitCount);
		baseClass.selectproject();

		// Perform Search and release docs to the tags and folders
		sessionSearch.basicContentSearch(Input.searchString9);
		sessionSearch.bulkTagExisting(TagName);
		sessionSearch.bulkFolderExisting(FolderName);
		baseClass.selectproject();

		// Perform Search and release docs to the tags and folders
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkTagExisting(TagName2);
		sessionSearch.bulkFolderExisting(FolderName2);

		// Navigate to Tags And Folder and verify Tag group Doc count
		tagPage.navigateToTagsAndFolderPage();
		tagPage.verifyTagDocCount(tagGroupName, aggregateHitCount);

		// Turn off toggle
		tagPage.turnOffToggle(tagPage.getTag_ToggleDocCount());
		baseClass.printResutInReport(
				baseClass.ValidateElement_AbsenceReturn(tagPage.getTagandCount(tagGroupName, aggregateHitCount)),
				"Tag group doc count not displayed after toggle turned off",
				"Tag group doc count still displays after toggle turned off", "Pass");

		// verify Folder group Doc count
		tagPage.verifyFolderDocCount(folderGroupName, aggregateHitCount);

		// Turn off toggle
		tagPage.turnOffToggle(tagPage.getFolder_ToggleDocCount());
		baseClass.printResutInReport(
				baseClass.ValidateElement_AbsenceReturn(
						tagPage.getTagandCount(folderGroupName, aggregateHitCount)),
				"Folder group doc count not displayed after toggle turned off",
				"Folder group doc count still displays after toggle turned off", "Pass");

		loginPage.logout();

	}
	@Test(description = "RPMXCON-47833", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedAsDesc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47833 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for each document' with Doc ID as selected in desc sort");

		// configure query & perform Bulk Tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");

		// Select Export File Name as 'DocID', select Sort by 'DocID' & verify Batch
		// print is completed & check file download link in background task page
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
}
