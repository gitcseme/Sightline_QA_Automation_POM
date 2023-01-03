package sightline.doclist;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.Categorization;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

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
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}
	
	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @author: TestCase id:RPMXCON-66462
	 * @Description Verify that if user switches to thumbnail view after applying
	 *              filter should show same docs
	 */
	@Test(description = "RPMXCON-66462", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyUserSwitchesThumbnailViewShowSameDocs(String userName, String password, String fullName)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-66462 DocList Component");
		baseClass
				.stepInfo("Verify that if user switches to thumbnail view after applying filter should show same docs");
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged In as : " + fullName);

		// search and go to docList page
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docList.getDocIds());
		List<String> docIdsList = baseClass.availableListofElements(docList.getDocIds());
		System.out.println(docIdsList);

		baseClass.stepInfo("applying include filter and verifying the applied filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		baseClass.waitTillElemetToBeClickable(docList.getApplyFilters());
		docList.getApplyFilters().Click();
		int index = baseClass.getIndex(docList.getTableRowHeaderInDocList(), "CUSTODIANNAME");
		System.out.println(index);
		baseClass.waitTime(3);
		List<WebElement> element = docList.getColumValues(index).FindWebElements();
		for (WebElement ele : element) {
			driver.waitForPageToBeReady();
			String text = ele.getText().trim();
			if (text.contains(Input.metaDataCN)) {
				System.out.println("Applied filter for" + Input.metaDataCN + " is displayed in list view");
			} else {
				baseClass.failedStep("Applied filter for" + Input.metaDataCN + " is not displayed in list view");
			}
		}
		driver.Navigate().refresh();
		baseClass.waitTime(3);
		baseClass.stepInfo("Navigating to Tile view page and selecting checkboxes");
		docList.verifySelectAllInTileView();
		baseClass.stepInfo("applying include filter in Thumbnail view and verifying the applied filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		baseClass.waitTillElemetToBeClickable(docList.getApplyFilters());
		docList.getApplyFilters().Click();
		baseClass.waitTime(5);
		docList.verifyingThumbnailBoxesValues(Input.metaDataCN);
		for (int i = 1; i < docList.getInfoBtn().size(); i++) {
			driver.waitForPageToBeReady();
			docList.getInfoBtnInThumbnailBoxes(i).waitAndClick(2);
			String ActualString = docList.getDocIdTileView().getText();
			System.out.println(ActualString);
			baseClass.compareListWithOnlyOneString(docIdsList, ActualString,
					"Filter are applied for thumbnail boxes same docids" + ActualString + "is displayed",
					"Same docids is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:24/11/2022 RPMXCON-54236
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify that if child document is presented in the filter
	 *              criteria then the child will be presented as a main row in the
	 *              grid as well as presented as a child of the parent when
	 *              expanded..
	 */
	@Test(description = "RPMXCON-54236", enabled = true, groups = { "regression" })
	public void verifyChildWindowInDocListpage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54236");
		baseClass.stepInfo(
				"To verify that if child document is presented in the filter criteria then the child will be presented as a main row in the grid as well as presented as a child of the parent when expanded.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed");

		driver.waitForPageToBeReady();
		docList.addDocFileSizeColumn();
		baseClass.waitTime(3);
		docList.Selectpagelength(Input.pageLength);
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		docList.getDocListDocFileSizeValue().ScrollTo();
		String docFileSize = docList.getDocListDocFileSizeValue().getText();
		System.out.println(docFileSize);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		docList.docFileSizeFilter("at", docFileSize, null);
		baseClass.stepInfo("docfilesize filter successfully");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getDocList_PlusIcon());
		docList.getDocList_PlusIcon().waitAndClick(5);
		List<WebElement> lastPage = docList.getChildDocumentRowCount().FindWebElements();
		for (int j = 1; j <= lastPage.size(); j++) {
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docList.getChildDocumentCheckBox(j));
			baseClass.waitTillElemetToBeClickable(docList.getChildDocumentCheckBox(j));
			if (!docList.getChildDocumentCheckBox(j).isDisplayed()) {
				baseClass.failedStep("The child documents in the doclist page is not displayed under parent document");
			}
		}
		baseClass.passedStep("The child documents in the doclist page is displayed under parent document");
		baseClass.passedStep("Child document is presented in the filter criteria then the child will be presented"
				+ "as a main row in the grid as well as presented as a child of the parent expanded.");
		softAssert.assertAll();
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:24/11/2022 RPMXCON-66460
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that options “Select Column” and “Save to Profile” should
	 *              be applicable to Thumbnails View and should be viewed in
	 *              Metadata hover Box.
	 */
	@Test(description = "RPMXCON-66460", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyThumbnailsViewInDocListPageMetaDataBox(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66460");
		baseClass.stepInfo(
				"Verify that options “Select Column” and “Save to Profile” should be applicable to Thumbnails View and should be viewed in Metadata hover Box.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed");

		driver.waitForPageToBeReady();
		softAssert.assertTrue(docList.getTileView().isDisplayed());
		softAssert.assertTrue(docList.getGridViewIcon().isDisplayed());
		softAssert.assertTrue(docList.SelectColumnBtn().isDisplayed());
		softAssert.assertTrue(docList.getSaveToProfileBtn().isDisplayed());
		baseClass.passedStep(
				" \"List View\" and \"Tile View\" icon is displayed the same row of \"Select Column\" , \"Save to Profile\" button.");
		softAssert.assertAll();
		docList.getTileView().waitAndClick(5);
		baseClass.stepInfo("Verify that thumbnail generation is attempted and Viewed");
		driver.waitForPageToBeReady();
		docList.SelectColumnDisplay(docList.getSelectEmailAuthorName());
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docList.getTileView().waitAndClick(5);
		for (int i = 1; i < docList.getInfoBtn().size(); i++) {
			driver.waitForPageToBeReady();
			docList.getInfoBtnInThumbnailBoxes(i).waitAndClick(2);
			baseClass.waitForElement(docList.getMetaDataAuthorName());
			driver.waitForPageToBeReady();
			if (docList.getMetaDataAuthorName().isDisplayed()) {
				baseClass.passedStep("Thumbnails View is seen for this view in Metadata hover box");
			} else {
				baseClass.failedStep("No such Metadataboxs");
			}
			loginPage.logout();
		}
	}
	
	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-54275
	 * @throws Exception
	 * @Description To verify that if any criteria is "Excluded" in the filters, the
	 *              results should NOT include the docs that match/have the
	 *              specified criteria
	 */
	@Test(description = "RPMXCON-54275", enabled = true, groups = { "regression" })
	public void verifyExcludedFiltersResultDocsNotSpecifiedCrit() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54275");
		baseClass.stepInfo(
				"To verify that if any criteria is \"Excluded\" in the filters, the results should NOT include the docs that match/have the specified criteria");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();

		// verify selecting exclude is not equals to author name .
		docList.SelectColumnDisplay(docList.getSelectAvailMetadata(Input.MetaDataEAName));
		driver.waitForPageToBeReady();
		docList.EmailAuthorNameInExcludeVerificationInDoc();
	}
	/**
	 * @author Vijaya.Rani ModifyDate:21/10/2022 RPMXCON-54519
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorName with any special
	 *              charatcers (,/"/-/_ /) on DocList page.
	 */
	@Test(description = "RPMXCON-54519", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterForEmailAuthorNameWithAnySpecialCharatersInDocList(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54519");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorName with any special charatcers (,/\"/-/_ /) on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String domain1 = "(#NOS OCRM FKNMS-ALL);";
		String domain2 = "Amol.Gawande/,@consilio.com";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// EmailRecipientnames Include
		baseClass.stepInfo("EmailAuthorName Include");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.include(domain1);
		driver.waitForPageToBeReady();
		if (baseClass.text(domain1).isDisplayed()) {
			baseClass.passedStep("Documents containing only the selected email IDs only filtered");
		} else {
			baseClass.failedStep("Documents containing selected email IDs not filtered");
		}

		docList.getClearAllBtn().waitAndClick(5);
		baseClass.stepInfo("ClearAll Button Is clicked");
		// EmailRecipientnames Exclude
		baseClass.stepInfo("EmailAuthorName Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain2);
		softAssertion.assertTrue(docList.getEmailAuthorNameNoRestultData().isDisplayed());
		baseClass.passedStep("Documents containing the selected email IDs should not be filtered");
		softAssertion.assertAll();

		loginPage.logout();
	}

	
	/**
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-53852
	 * @throws Exception
	 * @Description To verify, as an RM user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.
	 */
	@Test(description = "RPMXCON-53852", enabled = true, groups = { "regression" })
	public void verifyAsRMUGotoDocViewFromDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53852");
		baseClass.stepInfo(
				"To verify, as an RM user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();

		// select All Documents
		driver.waitForPageToBeReady();
		docList.selectAllDocs();
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will land in Doc view page with all the selected documents as a view in Doc view page Successfully");
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:03/11/2022 RPMXCON-54291
	 * @throws Exception
	 * @Description To verify that Audio file should play in Doc List irrespective of DocFileExtension.
	 */
	@Test(description = "RPMXCON-54291", enabled = true, groups = { "regression" })
	public void verifyAudioFilePlayInDocFileExtension() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54291");
		baseClass.stepInfo(
				"To verify that Audio file should play in Doc List irrespective of DocFileExtension.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
	
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		// previewDocument and play
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify preview Doc list of audio document");
		docList.DoclistPreviewAudio();
		baseClass.passedStep("Audio file is played in Preview window successfully.");
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T ModifyDate:4/11/2022 RPMXCON-54347
	 * @throws Exception
	 * @Description Verify from Doc View- mini doc list navigation and presentation
	 *              to DocList with selected documents, should complete in less than
	 *              8 seconds
	 */
	@Test(description = "RPMXCON-54347", enabled = true, groups = { "regression" })
	public void verifyingLoadingOfDocListPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54347");
		baseClass.stepInfo(
				"Verify from Doc View- mini doc list navigation and presentation to DocList with selected documents, should complete in less than 8 seconds");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage doc=new DocViewPage(driver);
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		// Searching Content document go to docview
		int Purehit=sessionSearch.basicContentSearch(Input.testData1);
		baseClass.waitTime(5);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		long start = System.currentTimeMillis();
		
		baseClass.stepInfo("Navigating to doclist page");
		baseClass.waitForElement(doc.getViewDocAllList());
		doc.getViewDocAllList().waitAndClick(2);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getTableFooterDocListCount());
		String DocListCount = docList.getTableFooterDocListCount().getText();
		
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		System.out.println(totalTime);

		String[] doccount = DocListCount.split(" ");
		String Document = doccount[5];
		baseClass.digitCompareEquals(Integer.valueOf(Document), Purehit, "Documents are loaded Successfully", "Documents are not loaded");
		
		baseClass.stepInfo("verifying the document loaded within 8 secs");
		if (totalTime < 8000) {
			baseClass.passedStep("Selected Documents are loaded in doclist page within 8 secs");
		}else {
			baseClass.failedStep("Selected Documents are not loaded in doclist page within 8 secs");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Krishna Date:NA ModifyDate:NA RPMXCON-54274
	 * @throws Exception
	 * @Description To verify that if any criteria is "Included" in the filters, the
	 *              results should include the docs that match/have the specified
	 *              criteria
	 */
	@Test(description = "RPMXCON-54274", enabled = true, groups = { "regression" })
	public void verifyIncludedFiltersResultDocsSpecifiedCriteria() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54274");
		baseClass.stepInfo(
				"To verify that if any criteria is \"Included\" in the filters, the results should include the docs that match/have the specified criteria");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();

		// verify applied include custodian filter is added.
		docList.applyCustodianNameFilter(Input.metaDataCN);
		driver.waitForPageToBeReady();
		docList.verifyAppliedIncludeCustodianNameFilterIsAdded(Input.metaDataCN);
	}

	
	/**
	 * @author sowndarya.velraj
	 * @Description :Creation and assigning documents in Folders for Categorization.[RPMXCON-54250]
	 */
	@Test(description = "RPMXCON-54250", enabled = true, groups = { "regression" })
	public void createandCategorizeFolders() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54250");
		baseClass.stepInfo("Creation and assigning documents in Folders for Categorization");
		
		DocListPage docList = new DocListPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
//		SavedSearch savedSearch=new SavedSearch(driver);
		Categorization categorize= new Categorization(driver);
		String folder= "Folder"+ Utility.dynamicNameAppender();
		String searchName="search"+ Utility.dynamicNameAppender();
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tags.CreateFolder(folder, Input.securityGroup);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		sessionSearch.bulkFolderExisting(folder);
		

		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Folder", folder, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("search", searchName, Input.mySavedSearch, true);

		// verify Run categorization
		categorize.runCategorization("YES");
		
		categorize.ViewInDocLIst();
		
		driver.waitForPageToBeReady();
		String docCount = docList.verifyingDocCount();
		
		softAssert.assertEquals(purehit,Integer.parseInt(docCount));
		softAssert.assertAll();
		baseClass.passedStep("Same number of documents moved in DocList Screen");
		
		}

	/**
	 * @author sowndarya.velraj
	 * @Description :Creation and assigning documents in Tags for Categorization.[RPMXCON-54249]
	 */
	@Test(description = "RPMXCON-54249", enabled = true, groups = { "regression" })
	public void createandCategorizeTags() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54249");
		baseClass.stepInfo("Creation and assigning documents in Tags for Categorization");
		
		DocListPage docList = new DocListPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		Categorization categorize= new Categorization(driver);
		String tag= "Tag"+ Utility.dynamicNameAppender();
		String searchName="search"+ Utility.dynamicNameAppender();
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tags.CreateTag(tag, Input.securityGroup);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.saveSearch(searchName);
		sessionSearch.bulkTagExisting(tag);
		
		// Navigate to CATEGORIZATION
		categorize.navigateToCategorizePage();

		// Select Folder In Analyse Section
		categorize.fillingTrainingSetSection("Tag", tag, null, null);

		// select Folder in Corpus Section
		categorize.fillingStep2CorpusTab("search", searchName, Input.mySavedSearch, true);

		// verify Run categorization
		categorize.runCategorization("YES");
		
		categorize.ViewInDocLIst();
		
		driver.waitForPageToBeReady();
		String docCount = docList.verifyingDocCount();
		
		softAssert.assertEquals(purehit,Integer.parseInt(docCount));
		softAssert.assertAll();
		baseClass.passedStep("Same number of documents moved in DocList Screen");
		
		}

	/**
	 * @author Vijaya.Rani ModifyDate:09/11/2022 RPMXCON-54365
	 * @throws Exception
	 * @Description Validate doc List limitation from Sub Tally report - View less than or equal to 500K documents.
	 */
	@Test(description = "RPMXCON-54365", enabled = true, groups = { "regression" })
	public void verifyDocListFromSubtallyInLessDocuments() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54365");
		baseClass.stepInfo(
				"Validate doc List limitation from Sub Tally report - View less than or equal to 500K documents");
		Utility utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		TagsAndFoldersPage tagAndFol = new TagsAndFoldersPage(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		baseClass.stepInfo("Create a new Folder contains word between");
		tagAndFol.CreateFolder(random1, Input.securityGroup);

		baseClass.stepInfo("Verify created folder is added to folder structure");
		driver.Navigate().refresh();
		tagAndFol.verifyFolderNameIsAddedToStructure(random1);

		baseClass.stepInfo("Select documets from doc explorer table and bulk folder selected documents");
		docexp.selectDocumentsAndBulkFolder(10, random1);

		SecurityGroupsPage securityGroup = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Navigate to security group");
		securityGroup.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("Add Folder to security group");
		securityGroup.addFolderToSecurityGroup(Input.securityGroup, random1);

		TallyPage tally = new TallyPage(driver);

		baseClass.stepInfo("Navigate to tally page");
		tally.navigateTo_Tallypage();

		baseClass.stepInfo("Select project as souce for tally");
		tally.selectSourceByProject();

		baseClass.stepInfo("Select Tally by Meta Data Field");
		tally.selectTallyByMetaDataField(Input.metaDataName);

		baseClass.stepInfo("Select documents and click on action drop down");
		tally.tallyActions();

		baseClass.stepInfo("Selecting sub tally from drop down");
		tally.selectSubTallyFromActionDropDown();

		baseClass.stepInfo("Applying subtally from sub tally field");
		tally.applyingSubTallyFolderField(random1);

		baseClass.stepInfo("Verify documents count and go to doclistpage");
		tally.tallyToDocListPage();
		baseClass.passedStep("User is not be prompted with any message and navigate successfully to DocList screen with selected number of documents.");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:09/11/2022 RPMXCON-54287
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that using the Download option, any of the file variant associated with this document including the MP3 file variant from preview document of doc list.
	 */
	@Test(description = "RPMXCON-54287", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyAudioDocumentPerviewDocsAndDownload(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54287");
		baseClass.stepInfo(
				"Verify that using the Download option, any of the file variant associated with this document including the MP3 file variant from preview document of doc list.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocList();
		
		baseClass.waitForElement(docList.getDocListPerviewBtn());
		docList.getDocListPerviewBtn().waitAndClick(5);
		baseClass.waitForElement(docList.getAudioDownloadIcon());
		baseClass.waitTillElemetToBeClickable(docList.getAudioDownloadIcon());
		docList.getAudioDownloadIcon().Click();
		baseClass.waitForElement(docList.getAudioOptionToDownload());
		baseClass.waitTillElemetToBeClickable(docList.getAudioOptionToDownload());
		docList.getAudioOptionToDownload().Click();
		docList. getPreviewAudioDocCloseButton().waitAndClick(5);;
		baseClass.waitUntilFileDownload();
        String fileName = baseClass.GetFileName();
        System.out.println(fileName);
        if(fileName != null) {
        	baseClass.passedStep("Audio document is download successfully from preview document of doc list");
        }else {
        	baseClass.failedStep("No such Download file");
        }
		loginPage.logout();
	}
	/**
	 * @author  Date:NA ModifyDate:NA RPMXCON-66481
	 * @throws Exception
	 * @Description Verify that from tile/thumbnail view on unchecking single
	 *              checkbox the top-right button "Unselect All" becomes "Select
	 *              All"
	 */
	@Test(description = "RPMXCON-66481", enabled = true, groups = { "regression" })
	public void verifyThumbnailViewUnCheckingAndUnSelect() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-66481");
		baseClass.stepInfo(
				"Verify that from tile/thumbnail view on unchecking single checkbox the top-right button \"Unselect All\" becomes \"Select All\"");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docList.verifySelectAllInTileView();
		baseClass.waitForElement(docList.getUnSelectAllCheckBox());
		baseClass.waitTillElemetToBeClickable(docList.getUnSelectAllCheckBox());
		docList.getUnSelectAllCheckBox().waitAndClick(5);
		baseClass.waitForElement(docList.getSelectAllOk());
		baseClass.waitTillElemetToBeClickable(docList.getSelectAllOk());
		docList.getSelectAllOk().waitAndClick(5);
		baseClass.stepInfo("Unselect all documents in tile view");

		baseClass.waitForElement(docList.getSelectAllCheckBox());
		baseClass.waitTillElemetToBeClickable(docList.getSelectAllCheckBox());
		docList.getSelectAllCheckBox().waitAndClick(5);
		baseClass.waitForElement(docList.getSelectAllOk());
		baseClass.waitTillElemetToBeClickable(docList.getSelectAllOk());
		docList.getSelectAllOk().waitAndClick(5);
		baseClass.waitForElement(docList.getFirstCheckBox());
		baseClass.waitTillElemetToBeClickable(docList.getFirstCheckBox());
		docList.getFirstCheckBox().waitAndClick(5);
		baseClass.stepInfo("single checkbox is unchecked");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getTileViewSelectAll());
		if (docList.getTileViewSelectAll().isElementPresent()) {
			baseClass.passedStep("Unselect all checkbox gets removed and displayed select all as expected");

		} else {
			baseClass.failedStep("selectall checkbox is not displayed ");
		}
		loginPage.logout();

	}

	/**
     * @author  Date:NA ModifyDate:NA RPMXCON-66461
     * @throws Exception
     * @Description Verify that user should be able to apply filter to the docs in
     *              the Thumbnail View
     */
    @Test(description = "RPMXCON-66461", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
    public void verifyAbleApplyFilterInThumbnail(String userName, String password, String fullName) throws Exception {
    	baseClass.stepInfo("Test case Id: RPMXCON-66461");
        baseClass.stepInfo("Verify that user should be able to apply filter to the docs in the Thumbnail View ");
        sessionSearch = new SessionSearch(driver);
        DocListPage docList = new DocListPage(driver);
        SoftAssert softassert = new SoftAssert();
        String actualCount = "2"; // metadata field doc
        // Login As User
        loginPage.loginToSightLine(userName, password);
        baseClass.stepInfo("LoggedIn as : " + fullName);
        // Searching Content document go to doclist
        sessionSearch.basicContentSearch(Input.searchString1);
        sessionSearch.ViewInDocList();
       driver.waitForPageToBeReady();
        docList.getTileView().waitAndClick(5);
        baseClass.waitForElement(docList.getThumbnailbar());
        softassert.assertTrue(docList.getThumbnailbar().isElementPresent());
        baseClass.passedStep("thumbnails view is displayed as expected");
        docList.getIncludeFilterEmailAllDomain(Input.MetaDataDomainName);
        baseClass.stepInfo("available documents is filtered as expected");
        String expectedCount = docList.verifyingDocCount();
        softassert.assertEquals(expectedCount,actualCount);
        System.out.println(expectedCount);
        baseClass.passedStep("User has been  able to apply filter to the docs in the Thumbnail View as expected");
        softassert.assertAll();
        loginPage.logout();
    }
    /**
	 * @authorBrundha TestCase id:RPMXCON-66483
	 * @Description Verify that when some/all checkboxes are checked, and applied
	 *              "Filter Documents By", then the documents showsed should be
	 *              filtered
	 */
	@Test(description = "RPMXCON-66483", enabled = true, groups = { "regression" })
	public void verifyingIncludedFilterInDocListPage2() throws Exception {


		baseClass.stepInfo("Test case Id: RPMXCON-66483 DocList Component");
		baseClass.stepInfo(
				"Verify that when some/all checkboxes are checked, and applied 'Filter Documents By', then the documents showsed should be filtered");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.testData1);

		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("applying include filter and verifying the applied filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		baseClass.waitTillElemetToBeClickable(docList.getApplyFilters());
		docList.getApplyFilters().Click();
		int index = baseClass.getIndex(docList.getTableRowHeaderInDocList(),"CUSTODIANNAME");
		System.out.println(index);
		baseClass.waitTime(3);
		List<WebElement> element = docList.getColumValues(index).FindWebElements();
		for (WebElement ele : element) {
			driver.waitForPageToBeReady();
			String text = ele.getText().trim();
			if (text.contains(Input.metaDataCN)) {
				System.out.println("Applied filter for" + Input.metaDataCN + " is displayed in list view");
			} else {
				baseClass.failedStep("Applied filter for" + Input.metaDataCN + " is not displayed in list view");
			}
		}
		
		driver.Navigate().refresh();
		baseClass.waitTime(3);

		baseClass.stepInfo("Navigating to Tile view page and selecting checkboxes");
		docList.verifySelectAllInTileView();

		baseClass.stepInfo("applying include filter in Thumbnail view and verifying the applied filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		baseClass.waitTillElemetToBeClickable(docList.getApplyFilters());
		docList.getApplyFilters().Click();
		docList.verifyingThumbnailBoxesValues(Input.metaDataCN);

		loginPage.logout();
	}

	/**
	 * @authorBrundha TestCase id:RPMXCON-66464
	 * @Description Verify that after adding/sorting columns if view changed to
	 *              thumbnail, docs should show in same order
	 */
	@Test(description = "RPMXCON-66464", enabled = true, groups = { "regression" })
	public void verifyingSortingInTileView() throws Exception {

		
		baseClass.stepInfo("Test case Id: RPMXCON-66464 DocList Component");
		baseClass.stepInfo(
				"Verify that after adding/sorting columns if view changed to thumbnail, docs should show in same order");
		String[]username= {Input.pa1userName,Input.rmu1userName,Input.rev1userName};
		String[]password= {Input.pa1password,Input.rmu1password,Input.rev1password};
		for(int j=0;j<username.length;j++) {
			
		loginPage.loginToSightLine(username[j],password[j]);

		DocListPage doc = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		doc.getDocID().waitAndClick(2);
		doc.getDocID().waitAndClick(2);
		baseClass.waitTime(1);
		int index = baseClass.getIndex(doc.getTableRowHeaderInDocList(), "DOCID");
		List<String> ColVal = baseClass.availableListofElements(doc.getColumValues(index));
		System.out.println("print col header hole value"+ColVal);
		
		ArrayList<String> Values=new ArrayList<>();
		ArrayList<String> TileViewValues=new ArrayList<>();
		for (String a : ColVal) {
			Values.add(a);
		}
		System.out.println("Array list value"+Values);
		baseClass.stepInfo("Verifying order in List view");
		baseClass.verifyOriginalSortOrder(ColVal, Values,"Descending", true);
		
		baseClass.waitTillElemetToBeClickable(doc.getTileView());
		doc.getTileView().waitAndClick(5);
		
		baseClass.stepInfo("verifying sorting order reflecting in Tile view");
		baseClass.waitTime(5);
		for(int i=1;i<=doc.getInfoBtn().size();i++) {
			driver.waitForPageToBeReady();
			baseClass.waitTime(5);
			doc.getInfoBtnInThumbnailBoxes(i).waitAndClick(2);
			baseClass.waitTime(2);
			String ActualString=doc.getDocIdTileView().getText();
			TileViewValues.add(ActualString);
		}
		System.out.println(TileViewValues);
		if(TileViewValues.equals(Values)) {
			baseClass.passedStep("Sorting order is maintained in Tile view");
		}else {
			baseClass.failedStep("Sorting order is not maintained after changing to tile view");
		}
		loginPage.logout();
		}
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:14/11/2022 RPMXCON-66459
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that option to view more docs (thumbnails) per page same
	 *              options exist now.
	 */
	@Test(description = "RPMXCON-66459", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyThumbnailsViewInDocListPageExistNow(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66459");
		baseClass.stepInfo("Verify that option to view more docs (thumbnails) per page same options exist now.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed in List View with page view as 10");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getTileView());
		docList.getTileView().waitAndClick(5);
		softAssert.assertTrue(docList.getThumbnailsView().Displayed());
		baseClass.passedStep("thumbnails view is displayed");
		softAssert.assertAll();
		String[] tag= {"10","50","100","500"};
		for(int i=0;i<tag.length;i++) {
			driver.waitForPageToBeReady();
			docList.getTileView().waitAndClick(5);
			docList.selectPageLengthInDocList(tag[i]);
			baseClass.stepInfo("thumbnails view is displayed with pagination of"+tag[i]);
		}
		
		baseClass.passedStep(
				"Verify that option to view more docs by (10/50/100/500) option (thumbnails) per page same options exist now same in drop down format ");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/11/2022 RPMXCON-66463
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that if user applies the View by 10/50/100/500 option in
	 *              one view and switches to the other view, the same setting should
	 *              be retained and applied.
	 */
	@Test(description = "RPMXCON-66463", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifypageLengthInListViewAndTileViewPage(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66463");
		baseClass.stepInfo(
				"Verify that if user applies the View by 10/50/100/500 option in one view and switches to the other view, the same setting should be retained and applied.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed in List View with page view as 10");

		String[] pageLen= {"10","50","100","500"};
		for(int i=0;i<pageLen.length;i++) {
			driver.waitForPageToBeReady();
			baseClass.waitTime(5);
			docList.getTileView().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[i]);
			baseClass.stepInfo("thumbnails view is displayed with pagination of"+pageLen[i]);
			docList.getGridViewIcon().waitAndClick(5);
			baseClass.waitTime(5);
			docList.selectPageLengthInDocList(pageLen[i]);
			baseClass.stepInfo("List view is displayed with pagination of"+pageLen[i]);
		}
		baseClass.passedStep("thumbnails view is displayed with pagination of 50,100,500");
		// one view and switches to the other view for PageLength
		for(int j=0;j<pageLen.length;j++) {
			driver.waitForPageToBeReady();
			baseClass.waitTime(5);
			docList.getGridViewIcon().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[j]);
			baseClass.stepInfo("List view is displayed with pagination of"+pageLen[j]);
			baseClass.waitTime(5);
			docList.getTileView().waitAndClick(5);
			docList.selectPageLengthInDocList(pageLen[j]);
			baseClass.stepInfo("thumbnails view is displayed with pagination of"+pageLen[j]);
		}
		baseClass.passedStep(
				"Verify that if user applies the View by 10/50/100/500 option in one view and switches to the other view, the same setting");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:14/11/2022 RPMXCON-66471
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that after performing bulk action, check box checked
	 *              should be unchecked from thumbnail view.
	 */
	@Test(description = "RPMXCON-66471",enabled = true, groups = { "regression" })
	public void verifyAfterBulkActionChechBoxUnCheckedFromTaumbnailview()
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-66471");
		baseClass.stepInfo(
				"Verify that after performing bulk action, check box checked should be unchecked from thumbnail view.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);

		loginPage.logout();
		
		String[]username= {Input.pa1userName,Input.rmu1userName,Input.rev1userName};
        String[]password= {Input.pa1password,Input.rmu1password,Input.rev1password};
        
        for(int j=0;j<username.length;j++) {
        loginPage.loginToSightLine(username[j],password[j]);
        
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		softAssert.assertTrue(docList.getDocListViewsInRow().isDisplayed());
		baseClass.passedStep(
				" \"List View\" and \"Tile View\" icon is displayed the same row of \"Select Column\" , \"Save to Profile\" button.");
		softAssert.assertAll();      
		docList.getTileView().waitAndClick(5);
		baseClass.stepInfo("Verify that thumbnail generation is attempted and Viewed");
		// Select all thumbnail box
		docList.verifySelectAllInTileView();
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		docList.getTileView().waitAndClick(5);
		baseClass.waitTime(10);
		baseClass.waitForElement(docList.getSelectAllCheckBox());
		if(! docList.getSelectAllCheckBox().Selected()) {
		baseClass.passedStep("Verify that after performing bulk action, check boxs are unchecked from thumbnail view");
		}else {
			baseClass.failedStep("Checkboxs are checked");
		}
		loginPage.logout();
        }
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:21/10/2022 RPMXCON-54519
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorName with any special
	 *              charatcers (,/"/-/_ /) on DocList page.
	 */
	@Test(description = "RPMXCON-53853", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterForEmailAuthorNameWithAnySpecialCharatersInDocList2(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54519");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorName with any special charatcers (,/\"/-/_ /) on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String domain1 = "(#NOS OCRM FKNMS-ALL);";
		String domain2 = "Amol.Gawande/,@consilio.com";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// EmailRecipientnames Include
		baseClass.stepInfo("EmailAuthorName Include");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.include(domain1);
		driver.waitForPageToBeReady();
		if (baseClass.text(domain1).isDisplayed()) {
			baseClass.passedStep("Documents containing only the selected email IDs only filtered");
		} else {
			baseClass.failedStep("Documents containing selected email IDs not filtered");
		}

		docList.getClearAllBtn().waitAndClick(5);
		baseClass.stepInfo("ClearAll Button Is clicked");
		// EmailRecipientnames Exclude
		baseClass.stepInfo("EmailAuthorName Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailAuthNameFilter());
		docList.getEmailAuthNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain2);
		softAssertion.assertTrue(docList.getEmailAuthorNameNoRestultData().isDisplayed());
		baseClass.passedStep("Documents containing the selected email IDs should not be filtered");
		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/10/2022 RPMXCON-53853
	 * @throws Exception
	 * @Description To verify, as an Reviewer user login, When I will select all the
	 *              documents in Doc List, I will be able to see this documents on
	 *              Doc View page, from View Document action button.
	 */
	@Test(description = "RPMXCON-53853", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectAllDocsInDocListPageGotoDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53853");
		baseClass.stepInfo(
				"To verify, as an Reviewer user login, When I will select all the documents in Doc List, I will be able to see this documents on Doc View page, from View Document action button.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docView = new DocViewPage(driver);

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select All Documents
		docList.selectingAllDocuments();
		driver.scrollingToBottomofAPage();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String DoclistDocCount = doccount[3];
		System.out.println("doclist page document count is" + DoclistDocCount);

		// DocView Form Doclist
		docList.docListToDocView();
		softAssert.assertTrue(docView.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		// verify Select docs display
		baseClass.waitForElementCollection(docView.getDocumetCountMiniDocList());
		int miniDocListCount = docView.getDocumetCountMiniDocList().WaitUntilPresent().size();
		System.out.println(miniDocListCount);
		baseClass.digitCompareEquals(Integer.valueOf(DoclistDocCount), Integer.valueOf(miniDocListCount),
				"Document count is displayed As expected from DocListPage", "DocCount is Not Displayed as expected");
		softAssert.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/10/2022 RPMXCON-53671
	 * @throws Exception
	 * @Description To verify as a Reviewer user login, I will be able to sort (Both
	 *              Ascending & Descending) a column from grid of Doc List page.
	 */
	@Test(description = "RPMXCON-53671", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectColumnAscendingAndDescending() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53671");
		baseClass.stepInfo(
				"To verify as a Reviewer user login, I will be able to sort (Both Ascending & Descending) a column from grid of Doc List page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();

		// sort in Ascending Order
		docList.sufflingColumnValueInDocListPage();

		// sort in Descending Order
		docList.verifyingDescendingOrderSortingInColumn();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/10/2022 RPMXCON-53657
	 * @throws Exception
	 * @Description To Verify, As a Admin user login, In Doc List page maximum 500
	 *              Documents will show per page.
	 */
	@Test(description = "RPMXCON-53657", enabled = true, groups = { "regression" })
	public void verifyAdminUserDocListPageDocsCount() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53657 DocList Component");
		baseClass.stepInfo(
				"To Verify, As a Admin user login, In Doc List page maximum 500 Documents will show per page");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting page length in doclist page");
		docList.getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(Input.pageLength);
		baseClass.waitTime(8);
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docList.getTableFooterDocListCount());
		driver.waitForPageToBeReady();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String Document = doccount[3];
		System.out.println("doclist page document count is" + Document);
		baseClass.textCompareEquals(Input.pageLength, Document, Input.pageLength + "is displayedas expected",
				Input.pageLength + "is not displayed as expected");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:31/10/2022 RPMXCON-53772
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              release multiple documents to some security group from DocList
	 *              page from Saved search.
	 */
	@Test(description = "RPMXCON-53772", enabled = true, groups = { "regression" })
	public void verifyPAUserReleaseMultipleDocsSomeSG() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53772 DocList Component");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to release multiple documents to some security group from DocList page from Saved search.");

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();
		SavedSearch savedSearch = new SavedSearch(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		// Save searched content
		sessionSearch.saveSearch(searchName1);
		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		// select multiple Documents and bulkRelease
		docList.documentSelection(5);
		docList.docListToBulkRelease(SG);
		baseClass.passedStep("Selected Documents will release in the selected security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:31/10/2022 RPMXCON-53773
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              release multiple documents to some security group from DocList
	 *              page from Saved search.
	 */
	@Test(description = "RPMXCON-53773", enabled = true, groups = { "regression" })
	public void verifyPAUserUnReleaseMultipleDocsSomeSGFromSavedSearch() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53773 DocList Component");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to release multiple documents to some security group from DocList page from Saved search.");

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();
		SavedSearch savedSearch = new SavedSearch(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		// Save searched content
		sessionSearch.saveSearch(searchName1);
		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		// select multiple Documents and bulkRelease
		docList.documentSelection(5);
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("Selected Documents will Un-release from the selected security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:31/10/2022 RPMXCON-53835
	 * @throws Exception
	 * @Description To verify, As an RM user login, I can be able to go to Doc List
	 *              page from saved search page after selecting any saved query from
	 *              My Search & apply action Document List from View Group.
	 */
	@Test(description = "RPMXCON-53835", enabled = true, groups = { "regression" })
	public void verifyAsRMUGotoDocListPageFromSavedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53835");
		baseClass.stepInfo(
				"To verify, As an RM user login, I can be able to go to Doc List page from saved search page after selecting any saved query from My Search & apply action Document List from View Group.");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);
		// verify doclist page
		if (docList.getDocList_info().isDisplayed()) {
			baseClass.passedStep("User will land in Document List page from saved search page Successsfully");
		} else {
			baseClass.failedStep("User will not land in Document List page from saved search page");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:31/10/2022 RPMXCON-53836
	 * @throws Exception
	 * @Description To verify, As an Reviewer user login, I can be able to go to Doc
	 *              List page from saved search page after selecting any saved query
	 *              from My Search & apply action Document List from View Group.
	 */
	@Test(description = "RPMXCON-53836", enabled = true, groups = { "regression" })
	public void verifyAsReviewerGoToDocListFromSavedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53836");
		baseClass.stepInfo(
				"To verify, As an Reviewer user login, I can be able to go to Doc List page from saved search page after selecting any saved query from My Search & apply action Document List from View Group.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);
		// verify doclist page
		if (docList.getDocList_info().isDisplayed()) {
			baseClass.passedStep("User will land in Document List page from saved search page Successsfully");
		} else {
			baseClass.failedStep("User will not land in Document List page from saved search page");
		}
		loginPage.logout();
	}


	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53767
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              Un-release multiple documents from some security group from
	 *              DocList page from Current search.
	 */
	@Test(description = "RPMXCON-53767", enabled = true, groups = { "regression" })
	public void verifyAsPAInDocListPageUnreleaseDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53767");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to Un-release multiple documents from some security group from DocList page from Current search.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.documentSelection(5);
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("Documents will Un-release from security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53769
	 * @throws Exception
	 * @Description To Verify, As an Project Admin user login, I will be able to
	 *              Un-release all documents from security group from DocList page
	 *              from Current search.
	 */
	@Test(description = "RPMXCON-53769", enabled = true, groups = { "regression" })
	public void verifyAsPAInDocListPageSelectAllDocsUnreleaseDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53769");
		baseClass.stepInfo(
				"To Verify, As an Project Admin user login, I will be able to Un-release all documents from security group from DocList page from Current search.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		String SG = "Security Group_" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Create SG
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(SG);

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.selectAllDocs();
		// BulkUnrelease
		docList.bulkUnRelease(SG);
		baseClass.passedStep("All Documents will Un-release from security group successfully");
		// deleteSG
		sg.deleteSecurityGroups(SG);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/10/2022 RPMXCON-53786
	 * @throws Exception
	 * @Description To verify, as an Reviewer user login, I can select multiple
	 *              document from Doc list & I can Tag all the selected documents.
	 */
	@Test(description = "RPMXCON-53786", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSelectMultipleDocInDocListInTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53786");
		baseClass.stepInfo(
				"To verify, as an Reviewer user login, I can select multiple document from Doc list & I can Tag all the selected documents.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String tagName = "tag" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Select Tag Classification");
		loginPage.logout();
		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");

		// Searching Content document go to doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// select multiple Documents
		docList.documentSelection(5);
		// BulkTag
		sessionSearch.bulkTagExisting(tagName);
		baseClass.passedStep("Tag will apply on the selected documents Successfully");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/10/2022 RPMXCON-53892
	 * @throws Exception
	 * @Description To verify, As an Reviewer user login, I will be able to perform
	 *              all actions from Doc List page, when I will go from Saved search
	 *              to Doc List.
	 */
	@Test(description = "RPMXCON-53892", enabled = true, groups = { "regression" })
	public void verifyAsReviewerPerformAllActinsFromDocLsitPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53892");
		baseClass.stepInfo(
				"To verify, As an Reviewer user login, I will be able to perform all actions from Doc List page, when I will go from Saved search to Doc List.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocViewPage docview=new DocViewPage(driver);
		String tagName = "tag" + UtilityLog.dynamicNameAppender();
		String folderName = "folder" + UtilityLog.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		loginPage.logout();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		// Save searched content
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		// select multiple Documents and bulktag
		docList.documentSelection(3);
		sessionSearch.bulkTagExisting(tagName);
		baseClass.stepInfo("DocListpage Action Bulktag is created");
		// select multiple Documents and bulkfolder
		driver.waitForPageToBeReady();
		docList.documentSelection(3);
		docList.bulkFolderExisting(folderName);
		baseClass.stepInfo("DocListpage Action Bulkfolder is created");
		// filter action
		driver.waitForPageToBeReady();
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		baseClass.stepInfo("DocListpage Action filters is performed");
		// select multiple Documents and bulkfolder
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will be able to perform all Doc List actions Successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:07/10/2022 RPMXCON-54283
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the waveform from audio player for the audio files less
	 *              than 1 hour from preview document of doc list.
	 */

	@Test(description = "RPMXCON-54283", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyWaveFormAudioPlayerInperviewDocumentInDocList(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54283");
		baseClass.stepInfo(
				"Verify the waveform from audio player for the audio files less than 1 hour from preview document of doc list");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocListPage docList = new DocListPage(driver);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocList();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docList.getDocListPerviewBtn());
		docList.getDocListPerviewBtn().waitAndClick(5);

		// verifying more than one hour audio docs
		driver.waitForPageToBeReady();
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) >= 01) {
			baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
		} else {
			baseClass.failedMessage("Lesser than one hour");
		}

		// checking zoom in function working for more than one hour audio docs
		driver.waitForPageToBeReady();
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// verifying waveform after zoom
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		baseClass.waitTime(1);
		baseClass.waitTillElemetToBeClickable(docViewPage.audioPlayPauseIcon());
		docViewPage.audioPlayPauseIcon().waitAndClick(10);
		boolean waveforms = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");

		// validating audio is still playing after zoom
		driver.waitForPageToBeReady();
		boolean audioPlays = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");

		// logout
		loginPage.logout();
	}
	/**
	 * @authorBrundha TestCase id:RPMXCON-53785
	 * @Description To verify, As a Reviewer user login, I am able to remove all
	 *              documents from Folder in Doc list page
	 */
	@Test(description = "RPMXCON-53785", enabled = true, groups = { "regression" })
	public void verifyingTheUnFolderCountInDocListPage() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String foldername = "Folder" + UtilityLog.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-53785 DocList Component");
		baseClass.stepInfo(
				"To verify, As a Reviewer user login, I am able to remove all documents from Folder in Doc list page");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.CreateFolder(foldername, Input.securityGroup);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		baseClass.ValidateElement_Presence(docList.getBackToSourceBtn(), "DocList Page");
		baseClass.waitTime(5);
		docList.documentSelection(3);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Unfoldering the Document");
		baseClass.waitTime(5);
		sessionSearch.bulkUnFolder(foldername);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// verifying document removed from folder
		tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		tf.verifyFolderDocCount(foldername, 0);

		loginPage.logout();

	}

	/**
	 * @authorBrundha TestCase id:RPMXCON-53659
	 * @Description To Verify, As a Reviewer user login, In Doc List page maximum
	 *              500 Documents will show per page
	 */
	@Test(description = "RPMXCON-53659", enabled = true, groups = { "regression" })
	public void verifyingTheDocumentCountInDocListPage() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53659 DocList Component");
		baseClass.stepInfo(
				"To Verify, As a Reviewer user login, In Doc List page maximum 500 Documents will show per page");

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting page length in doclist page");
		docList.getDocList_SelectLenthtobeshown().selectFromDropdown().selectByVisibleText(Input.pageLength);
		baseClass.waitTime(5);
		driver.scrollingToBottomofAPage();
		driver.scrollingToElementofAPage(docList.getTableFooterDocListCount());
		driver.waitForPageToBeReady();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		baseClass.waitTime(5);
		String[] doccount = DocListCount.split(" ");
		driver.waitForPageToBeReady();
		String Document = doccount[3];
		System.out.println("doclist page document count is" + Document);
		baseClass.textCompareEquals(Input.pageLength, Document, Input.pageLength + "is displayedas expected",
				Input.pageLength + "is not displayed as expected");
		loginPage.logout();
	}

	/**
	 * @authorBrundha TestCase id:RPMXCON-54276
	 * @Description To verify that if the filters include both "Include" and
	 *              "Exclude", then the results should include the docs that match
	 *              the "Include" criteria but does not match the "Exclude"
	 *              criteria.
	 */
	@Test(description = "RPMXCON-54276", enabled = true, groups = { "regression" })
	public void verifyingIncludedFilterInDocListPage() throws Exception {

		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54276 DocList Component");
		baseClass.stepInfo(
				"To verify that if the filters include both 'Include' and 'Exclude', then the results should include the docs that match the 'Include' criteria but does not match the 'Exclude' criteria.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Navigating to search page and search for documents");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		
		baseClass.stepInfo("selecting the column");
		docList.SelectColumnDisplay(docList.getSelectAvailMetadata(Input.MetaDataEAName));
		
		baseClass.stepInfo("applying include filter");
		docList.applyCustodianNameFilter(Input.metaDataCN);
		driver.waitForPageToBeReady();
		
		baseClass.stepInfo("applying Exclude filter and verifying");
		docList.EmailAuthorNameInExcludeVerificationInDoc();
		
		baseClass.stepInfo("verifying include filter");
		docList.verifyAppliedIncludeCustodianNameFilterIsAdded(Input.metaDataCN);
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53881
	 * @throws Exception
	 * @Description To verify, As an RM user login, When I will apply some filter on
	 *              Doc List and then select some documents after filtration, I will
	 *              be able to go to Doc View page.
	 */
	@Test(description = "RPMXCON-53881", enabled = true, groups = { "regression" })
	public void verifyAsRMUSomeFilterInDocListGotoDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53881");
		baseClass.stepInfo(
				"To verify, As an RM user login, When I will apply some filter on Doc List and then select some documents after filtration, I will be able to go to Doc View page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// filter action
		driver.waitForPageToBeReady();
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		baseClass.stepInfo("DocListpage Action filters is performed");

		// select multiple Documents
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will land in Doc View page with selected documents Successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53880
	 * @throws Exception
	 * @Description To verify, As an Project admin user login, When I will apply
	 *              some filter on Doc List and then select some documents after
	 *              filtration, I will be able to go to Doc View page.
	 */
	@Test(description = "RPMXCON-53880", enabled = true, groups = { "regression" })
	public void verifyAsPASomeFilterInDocListGotoDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53880");
		baseClass.stepInfo(
				"To verify, As an Project admin user login, When I will apply some filter on Doc List and then select some documents after filtration, I will be able to go to Doc View page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// filter action
		driver.waitForPageToBeReady();
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		baseClass.stepInfo("DocListpage Action filters is performed");

		// select multiple Documents
		driver.waitForPageToBeReady();
		docList.documentSelection(5);
		docList.docListToDocView();
		softAssert.assertTrue(docview.getDocView_DefaultViewTab().Displayed());
		baseClass.passedStep("Navigate to DocViewPage Successfully");
		softAssert.assertAll();
		baseClass.stepInfo("DocListpage Action viewInDocview ");
		baseClass.passedStep("User will land in Doc View page with selected documents Successfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53839
	 * @throws Exception
	 * @Description To verify, As an Reviewer user login, I can be able to go to Doc
	 *              List page from saved search page after selecting any saved query
	 *              from Shared with me & apply action Document List from View
	 *              Group.
	 */
	@Test(description = "RPMXCON-53839", enabled = true, groups = { "regression" })
	public void verifyAsReviewerSavedSearchGoToDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53839");
		baseClass.stepInfo(
				"To verify, As an Reviewer user login, I can be able to go to Doc List page from saved search page after selecting any saved query from Shared with me & apply action Document List from View Group.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage REV as with " + Input.rev1userName + "");
		
		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);
		//verify doclist page
		if(docList.getDocList_info().isDisplayed()) {
			baseClass.passedStep("User will land in Document List page from saved search page Successsfully");
		}
		else {
			baseClass.failedStep("User will not land in Document List page from saved search page");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/10/2022 RPMXCON-53840
	 * @throws Exception
	 * @Description To verify, As an PAU user login, able to go to Doc List page from saved search page from <Shared With ProjectAdministrator>/<Shared with Security Group Name> & apply action Document List from View Group.
	 */
	@Test(description = "RPMXCON-53840", enabled = true, groups = { "regression" })
	public void verifyAsPASavedSearchGoToDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53840");
		baseClass.stepInfo(
				"To verify, As an PAU user login, able to go to Doc List page from saved search page from <Shared With ProjectAdministrator>/<Shared with Security Group Name> & apply action Document List from View Group.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PA as with " + Input.pa1userName + "");
		
		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.saveSearchSharedWithPA(searchName1);

		// Open Doc list from Saved search page
		savedSearch.saveSearchSharedWithPAToDocList(searchName1);
		//verify doclist page
		if(docList.getDocList_info().isDisplayed()) {
			baseClass.passedStep("User will land in Document List page from saved search page Successsfully");
		}
		else {
			baseClass.failedStep("User will not land in Document List page from saved search page");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:12/10/2022 RPMXCON-54521
	 * @throws Exception
	 * @Description Validate onpage filter for EmailRecipientNames with any special
	 *              charatcers (,/"/-/_ /) on DocList page.
	 */
	@Test(description = "RPMXCON-54521", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterForEmailRecipientNameWithAnySpecialCharaters(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54521");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames with any special charatcers (,/\"/-/_ /) on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String domain1 = "(#NOS OCRM All OCRM Staff);Jeff Smith";
		String domain2="Amol.Gawande/,@symp";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// EmailRecipientnames Include
		baseClass.stepInfo("EmailRecipientnames Include");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailRecNameFilter());
		docList.getEmailRecNameFilter().waitAndClick(5);
		docList.include(domain1);
		driver.waitForPageToBeReady();
		if(baseClass.text(domain1).isDisplayed()) {
		baseClass.passedStep("Documents containing only the selected email IDs only filtered");
		}else {
			baseClass.failedStep("Documents containing selected email IDs not filtered");
		}

		docList.getClearAllBtn().waitAndClick(5);
		baseClass.stepInfo("ClearAll Button Is clicked");
		// EmailRecipientnames Exclude
		baseClass.stepInfo("EmailRecipientnames Exclude");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docList.getEmailRecNameFilter());
		docList.getEmailRecNameFilter().waitAndClick(5);
		docList.excludeDoclist(domain2);
		softAssertion.assertTrue(docList.getDocListNoRestultData().isDisplayed());
		baseClass.passedStep("Documents containing the selected email IDs should not be filtered");
		softAssertion.assertAll();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:12/10/2022 RPMXCON-54383
	 * @throws Exception
	 * @Description Validate doc List limitation from Saved Search - View less than
	 *              or equal to 500K documents.
	 */
	@Test(description = "RPMXCON-54383", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyDoclistLimitationSavedSearchLessThanDocuments(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54383");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames with any special charatcers (,/\"/-/_ /) on DocList pageValidate doc List limitation from Saved Search - View less than or equal to 500K documents.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		int pureHit = 0;

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Search String and save the content
		sessionSearch.basicContentSearch(Input.searchStringStar);
		pureHit = Integer.parseInt(sessionSearch.getPureHitsCount2ndSearch().getText());
		sessionSearch.saveSearch(searchName1);

		// Open Doc list from Saved search page
		savedSearch.savedSearchToDocList(searchName1);

		/// Go to doclist verify doc count
		driver.waitForPageToBeReady();
		String DocListCount = docList.getTableFooterDocListCount().getText();
		System.out.println(DocListCount);
		String[] doccount = DocListCount.split(" ");
		String Document = doccount[3];
		System.out.println("doclist page document count is" + Document);
		baseClass.textCompareEquals(Document, Document, pureHit + "is displayed as expected",
				pureHit + "is not displayed as expected");
		baseClass.passedStep(
				"User not be prompted with any message and navigate successfully to DocList screen with selected number of documents.");

		loginPage.logout();
	}
	/**
	 * @author Vijaya.Rani ModifyDate:02/08/2022 RPMXCON-54284
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the waveform from audio player for the audio files
	 *              greater than 1 hour from preview document of doc list.
	 */

	@Test(description = "RPMXCON-54284", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyWaveFormAudioPlayerInperviewDocument(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54284");
		baseClass.stepInfo(
				"Verify the waveform from audio player for the audio files greater than 1 hour from preview document of doc list]");

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		DocListPage docList = new DocListPage(driver);

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.oneHourAudio);
		sessionSearch.ViewInDocList();

		baseClass.waitForElement(docList.getDocListPerviewBtn());
		docList.getDocListPerviewBtn().waitAndClick(5);

		// verifying more than one hour audio docs
		String overAllAudioTime = docViewPage.getDocview_Audio_EndTime().getText();
		String[] splitData = overAllAudioTime.split(":");
		String data = splitData[0].toString();
		System.out.println(data);
		if (Integer.parseInt(data) >= 01) {
			baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
		} else {
			baseClass.failedMessage("Lesser than one hour");
		}

		// playing audio file
		docViewPage.audioPlayPauseIcon().waitAndClick(5);

		// verifying waveform
		boolean waveform = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveform);
		baseClass.passedStep("Waveform is displayed for same document");

		// validating audio is still playing
		boolean audioPlay = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlay);
		baseClass.stepInfo("Audio button docs are in play mode");

		// checking zoom in function working for more than one hour audio docs
		docViewPage.getAudioDocZoom().waitAndClick(5);
		boolean zoomBar = docViewPage.getAudioZoomBar().Displayed();
		softAssertion.assertTrue(zoomBar);
		baseClass.passedStep("Zoom functionality working for more than one hour of document");

		// verifying waveform after zoom
		boolean waveforms = docViewPage.getAudioWaveForm().GetAttribute("style").contains("hidden");
		softAssertion.assertTrue(waveforms);
		baseClass.passedStep("Waveform is displayed for same document after zoom in");

		// validating audio is still playing after zoom
		boolean audioPlays = docViewPage.audioPlayPauseIcon().GetAttribute("title").contains("Pause");
		softAssertion.assertTrue(audioPlays);
		baseClass.stepInfo("Audio button docs are in play mode after zoom in");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54963
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that email metadata should present with single quote on DocList.
	 */
	@Test(description = "RPMXCON-54963", enabled = true, groups = { "regression" })
	public void verifyEmailMetaDataPresentWithSingleQuote() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54963");
		baseClass.stepInfo(
				"Verify that email metadata should present with single quote on DocList.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String[] emailValue = { "EmailAuthorNameAndAddress", "EmailAuthorName", "EmailAuthorAddress" };
		String[] emailToValue = { "EmailToNamesAndAddresses", "EmailToNames", "EmailToAddresses" };
		String[] emailCCValue = { "EmailCCNamesAndAddresses", "EmailCCNames", "EmailCCAddresses" };
		String[] emailBCCValue = { "EmailBCCNamesAndAddresses", "EmailBCCNames", "EmailBCCAddresses" };
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
	
		baseClass.stepInfo("Select colunm In doclist EmailAuthorNames and EmailAuthorAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailValue);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select colunm In doclist EmailToNames and EmailToAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailToValue);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select colunm In doclist EmailCCNames and EmailCCAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailCCValue);
		
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select colunm In doclist EmailBCCNames and EmailBCCAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailBCCValue);
		
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54534
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorDomains And DocFileType on DocList page.
	 */
	@Test(description = "RPMXCON-54534", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEamilAuthorNameAndDocFileTypeInDocList(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54534");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAuthorDomains And DocFileType on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String emailAuthor="EmailAuthorDomain";
		String docFileType="DocFileType";
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("verify DocList EmailAuthorDomain Include and DocFileType Exclude Filter");
		docList.emailValueFirstIncludeAndEmailValueSecondExcludeVerifyInDocList(emailAuthor,docFileType);
		
		// Clear Applied filter
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();
		
		baseClass.stepInfo("verify DocList DocFileType Include  and EmailAuthorDomain Exclude Filter");
		docList.emailValueFirstExcludeAndEmailValueSecondIncludeVerifyInDocList(docFileType,emailAuthor);
		loginPage.logout();
	}
	/**
	 * @author Vijaya.Rani ModifyDate:17/12/2022 RPMXCON-66469
	 * @throws AWTException
	 * @Description Verify that on mouse hover of the small list icon on the bottom
	 *              left of the thumbnail should present metadata and if the list of
	 *              metadata is more than 150px, then a vertical scrollbar should
	 *              appear..
	 */
	@Test(description = "RPMXCON-66469", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyThumbnailsViewMetadataListOfMetaDataInpx(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66469");
		baseClass.stepInfo(
				"Verify that on mouse hover of the small list icon on the bottom left of the thumbnail should present metadata and if the list of metadata is more than 150px, then a vertical scrollbar should appear.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed");

		driver.waitForPageToBeReady();
		softAssert.assertTrue(docList.getTileView().isDisplayed());
		softAssert.assertTrue(docList.getGridViewIcon().isDisplayed());
		softAssert.assertTrue(docList.SelectColumnBtn().isDisplayed());
		softAssert.assertTrue(docList.getSaveToProfileBtn().isDisplayed());
		baseClass.passedStep(
				" \"List View\" and \"Tile View\" icon is displayed the same row of \"Select Column\" , \"Save to Profile\" button.");
		softAssert.assertAll();
		docList.getTileView().waitAndClick(5);
		baseClass.stepInfo("Verify that thumbnail generation is attempted and Viewed");
		driver.waitForPageToBeReady();
		docList.SelectColumnDisplay(docList.getSelectEmailAuthorName());
		docList.SelectColumnDisplay(docList.getSelectEmailAuthorDomain());
		driver.waitForPageToBeReady();
		docList.getTileView().waitAndClick(5);
		for (int i = 1; i < docList.getInfoBtn().size(); i++) {
			driver.waitForPageToBeReady();
			docList.getInfoBtnInThumbnailBoxes(i).waitAndClick(2);
			baseClass.waitForElement(docList.getMetaDataBoxInDocList());
			String metadataDimat = docList.getMetaDataBoxInDocList().GetAttribute("style").trim();
			System.out.println(metadataDimat);
			String[] metadataBox = metadataDimat.split(" ");
			String BoxDim = metadataBox[1];
			String[] metadataBoxSplit = BoxDim.split("p");
			String boxDimens = metadataBoxSplit[0];
			int metadataDimentions = Integer.parseInt(boxDimens);
			System.out.println(metadataDimentions);
			driver.waitForPageToBeReady();
			if (metadataDimentions > 150) {
				baseClass.passedStep("metadata and if the list of metadata is more than 150px");
			} else {
				baseClass.failedStep("No such Metadataboxs");
			}
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(docList.getMetaDataAuthorName());
			if (docList.getMetaDataAuthorName().isDisplayed()) {
				baseClass.passedStep("vertical scrollbar is appear");
			} else {
				baseClass.failedStep("No such scrollbar");
			}
		}
		loginPage.logout();

	}
	/**
	 * @author sowndarya RPMXCON-61219
	 * @throws Exception
	 * @Description Verify that in "Doc Explorer" Pages/Export Data Popup - Provide
	 *              a Sticky Add-to-Selected btn while Scrolling Down.
	 */
	@Test(description = "RPMXCON-61219", enabled = true, groups = { "regression" })
	public void verifyExportDataPopup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-61219");
		baseClass.stepInfo(
				"Verify that in Doc Explorer Pages/Export Data Popup - Provide a Sticky Add-to-Selected btn while Scrolling Down.");
		DocListPage docList = new DocListPage(driver);
		DataSets data = new DataSets(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		String folderName = "Folder" + Utility.dynamicNameAppender();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// create folder and add save search in folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);

		// navigate to doc explorer
		docExp.navigateToDocExplorerPage();

		// select documents and navigate to export data
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		baseClass.waitForElement(docExp.actionDropdown());
		docExp.actionDropdown().waitAndClick(10);
		baseClass.waitForElement(docExp.exportDataFromActionDropdown());
		docExp.exportDataFromActionDropdown().waitAndClick(10);
		baseClass.stepInfo("selected documents and navigated to export data");

		// verify sticky notes 'Add to selected' position - Before scrolling
		baseClass.waitTime(8);
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String beforeScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		baseClass.waitTime(8);
		if (beforeScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}
		baseClass.waitTime(8);
		docExp.SelectExportDataWithMetadataAndWorkproduct(Input.sortDataBy, folderName);

		driver.scrollingToBottomofAPage();
		// verify sticky notes 'Add to selected' position - After scrolling
		baseClass.waitTime(8);
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String afterScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		if (afterScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}
		baseClass.waitTime(8);
		baseClass.textCompareEquals(beforeScrolling, afterScrolling, "success", "Failure");

		// run report
		driver.scrollPageToTop();
		baseClass.waitForElement(docExp.exportWindow_RunExport());
		baseClass.waitTime(8);
		docExp.exportWindow_RunExport().waitAndClick(10);

		baseClass.waitForElement(docExp.exportWindow_closeButton());
		baseClass.waitTime(8);
		docExp.exportWindow_closeButton().waitAndClick(10);

		driver.waitForPageToBeReady();
		baseClass.waitTime(8);
		data.getBullHornIcon().waitAndClick(10);
		String downloadMsg = data.getNotificationMsg().getText();
		String expected = "Your export is ready please click here to download";
		baseClass.waitTime(8);
		softAssert.assertEquals(downloadMsg, expected);
		baseClass.passedStep(
				"Notification is displayed and on click of the same download the file  Make sure that in downloaded file is selected all documents from all pages");

		loginPage.logout();

	}

	/**
	 * @author sowndarya RPMXCON-61237
	 * @throws Exception
	 * @Description Verify that in "Doc List" Pages/Export Data Popup - Provide a
	 *              Sticky Add-to-Selected btn while Scrolling Down.
	 */
	@Test(description = "RPMXCON-61237", enabled = true, groups = { "regression" })
	public void verifyDocListPopup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-61237");
		baseClass.stepInfo(
				"Verify that in Doc List Pages/Export Data Popup - Provide a Sticky Add-to-Selected btn while Scrolling Down.");
		DocListPage docList = new DocListPage(driver);
		DataSets data = new DataSets(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		String folderName = "Folder" + Utility.dynamicNameAppender();
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// create folder and add save search in folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);
		// navigate to doc explorer
		docExp.navigateToDocExplorerPage();

		// select documents and navigate to export data
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(5);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");

		baseClass.waitForElement(docList.getPopUpOkBtn());
		docList.getPopUpOkBtn().waitAndClick(5);
		baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");

		docExp.docExpViewInDocList();
		docList.documentSelection(5);
		baseClass.stepInfo("selected documents and navigated to Doclist");

		baseClass.waitForElement(docList.getActionDropDownButton());
		docList.getActionDropDownButton().waitAndClick(10);
		baseClass.waitForElement(docList.getExportData());
		docList.getExportData().waitAndClick(10);
		baseClass.stepInfo("selected documents and navigated to export data");

		// verify sticky notes 'Add to selected' position - Before scrolling
		baseClass.waitTime(8);
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String beforeScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		baseClass.waitTime(8);
		if (beforeScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}

		baseClass.waitTime(8);
		docExp.SelectExportDataWithMetadataAndWorkproduct(Input.sortDataBy, folderName);

		driver.scrollingToBottomofAPage();
		// verify sticky notes 'Add to selected' position - After scrolling
		baseClass.waitTime(8);
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		String afterScrolling = docExp.exportWindow_AddToSelectedButton().GetAttribute("style");
		if (afterScrolling.contains("text-align: center")) {
			baseClass.passedStep("“Add to Selected” btn  of Available Objects appears with a fixed position");
		}
		baseClass.waitTime(8);
		baseClass.textCompareEquals(beforeScrolling, afterScrolling, "success", "Failure");

		// run report
		driver.scrollPageToTop();
		baseClass.waitTime(8);
		baseClass.waitForElement(docExp.exportWindow_RunExport());
		docExp.exportWindow_RunExport().waitAndClick(10);

		baseClass.waitForElement(docExp.getBtnCloseWindow());
		baseClass.waitTime(8);
		docExp.getBtnCloseWindow().waitAndClick(10);

		baseClass.waitForElement(data.getBullHornIcon());
		baseClass.waitTime(8);
		data.getBullHornIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String downloadMsg = data.getNotificationMsg().getText();
		String expected = "Your export is ready please click here to download";
		baseClass.waitTime(8);
		softAssert.assertEquals(downloadMsg, expected);
		baseClass.passedStep(
				"Notification is displayed and on click of the same download the file  Make sure that in downloaded file is selected all documents from all pages");

		loginPage.logout();

	}

	/**
	 * @author sowndarya RPMXCON-65049
	 * @throws Exception
	 * @Description Verify that error message should be displayed when 'Share by
	 *              email' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer >
	 *              Export > Share Report
	 */
	@Test(description = "RPMXCON-65049", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInSharedReport() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-65049");
		baseClass.stepInfo(
				"Verify that error message should be displayed when 'Share by email' entered with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Share Report");
		DocListPage docList = new DocListPage(driver);
		DataSets data = new DataSets(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		ReportsPage report = new ReportsPage(driver);
		String emailname = "< > * ; ‘ / ( ) # & ";
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// navigate to doc explorer
		docExp.navigateToDocExplorerPage();

		// select documents and navigate to export data
		baseClass.waitForElement(docExplorer.getDocExp_SelectAllDocs());
		docExplorer.getDocExp_SelectAllDocs().waitAndClick(10);
		baseClass.passedStep(
				"Message is displayed to select the documents from current page or from all pages 'Cancel' and 'Ok' buttons is displayed");
		driver.waitForPageToBeReady();
		if (docList.getPopUpOkBtn().isElementAvailable(10)) {

			baseClass.waitForElement(docList.getPopUpOkBtn());
			docList.getPopUpOkBtn().waitAndClick(10);
			baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");
		} else {
			baseClass.waitForElement(docList.getPopUpOkBtn());
			docList.getPopUpOkBtn().waitAndClick(10);
			baseClass.stepInfo("Documents from current page of List view is selected with checkmark for the checkbox");
		}
		driver.scrollPageToTop();
		baseClass.waitForElement(docExp.actionDropdown());
		docExp.actionDropdown().waitAndClick(10);
		baseClass.waitForElement(docExp.exportDataFromActionDropdown());
		docExp.exportDataFromActionDropdown().waitAndClick(10);
		baseClass.stepInfo("selected documents and navigated to export data");

		baseClass.waitForElement(report.getMetaDataChoose(Input.sortDataBy));
		report.getMetaDataChoose(Input.sortDataBy).Click();

		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(docExp.exportWindow_AddToSelectedButton());
		docExp.exportWindow_AddToSelectedButton().waitAndClick(10);

		baseClass.waitForElement(docExp.getExportSchedulerButton());
		docExp.getExportSchedulerButton().waitAndClick(5);
		baseClass.stepInfo("Schedule Report pop up is open Successfully");

		baseClass.waitForElement(docExp.getExportShareEmail());
		docExp.getExportShareEmail().SendKeys(emailname);
		baseClass.passedStep(
				"'Share by email'accepted with < > * ; ‘ / ( ) # & ” from Doc Explorer > Export > Share Report");
	}
	/**
	 * @author Vijaya.Rani ModifyDate:04/07/2022 RPMXCON-54280
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify that When a audio file is being played via Preview
	 *              Document, clicking on close of the preview pop-up should also
	 *              stop the audio file.
	 */
	@Test(description = "RPMXCON-54280", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyPerviewDocAudioFile(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54280");
		baseClass.stepInfo(
				"To verify that When a audio file is being played via Preview Document, clicking on close of the preview pop-up should also stop the audio file.");

		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		// Login As PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		baseClass.stepInfo("Verify preview Doc list of audio document");
		docList.DoclistPreviewAudio();
		baseClass.passedStep("Click on 'X' icon audio file is stoped and close perviewdocument popup");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/07/2022 RPMXCON-54567
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that Audio Document Preview functionality is working
	 *              proper in Saved Search >> DocList screen.
	 */

	@Test(description = "RPMXCON-54567", enabled = true, groups = { "regression" })
	public void verifyPerviewDocAudioFileFunctionalityWorkProper() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54567");
		baseClass.stepInfo(
				"Verify that Audio Document Preview functionality is working proper in Saved Search >> DocList screen.");

		sessionSearch = new SessionSearch(driver);
		SavedSearch savedsearch = new SavedSearch(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Searching audio document with different term
		baseClass.stepInfo("Searching Content documents based on search audio string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocList();

		baseClass.stepInfo("Verify preview Doc list of audio document");
		driver.waitForPageToBeReady();
		docList.DoclistPreviewAudio();
		baseClass.passedStep("Audio Document Preview functionality are working proper in DocList screen.");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:05/07/2022 RPMXCON-54526
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Validate onpage filter for EmailAllDomains with any special
	 *              charatcers (,/"/-/_ /) on Communication Explorer Report.
	 */
	@Test(description = "RPMXCON-54526", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEmailAllDomainWithSpicalCharatcers(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54526");
		baseClass.stepInfo(
				"Validate onpage filter for EmailAllDomains with any special charatcers (,/\"/-/_ /) on Communication Explorer Report.");

		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		// Login As PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		driver.waitForPageToBeReady();
		baseClass.stepInfo(
				"Select Author name  From Filter and Applying Filter And Verifying The Name in Email All Domain");
		docList.EmailAllDomainsNameVerificationInDoc();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select Include and Applying filter In Email All Domain DocList Page");
		docList.EmailAllDomainsNameIncludeVerificationInDoc();
		// Clear Applied filter
		driver.waitForPageToBeReady();
		docList.clearAllAppliedFilters();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select Exclude and Applying filter In Email All Domain DocList Page");
		docList.EmailAllDomainsNameInExcludeVerificationInDoc();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/07/2022 RPMXCON-54528
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Validate Doclist sorting.
	 */
	@Test(description = "RPMXCON-54528", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyDocExplorerUsingSpecialCharacter(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54528");
		baseClass.stepInfo("Validate Doclist sorting.");

		sessionSearch = new SessionSearch(driver);
		SavedSearch savedsearch = new SavedSearch(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();

		docList.verifyingDescendingOrderSortingInColumn();

		baseClass.stepInfo("removing the column in doclist page");
		docList.removeColumn();

		baseClass.stepInfo("Adding column in doclist page");
		docList.addColumn();

		baseClass.stepInfo("Verify Doclist sorting after shuffling the columns");
		docList.sufflingColumnValueInDocListPage();

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:06/07/2022 RPMXCON-54983
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Doc Explorer: Verify that results should be displayed if
	 *              filtering columns (metadata) value contain special characters
	 *              (angular brackets < > and also other special character).
	 */
	@Test(description = "RPMXCON-54983", enabled = true, groups = { "regression" })
	public void verifyResultDisplayFilteringCloumnsSpecialCharaters() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54983");
		baseClass.stepInfo(
				"Doc Explorer: Verify that results should be displayed if filtering columns (metadata) value contain special characters (angular brackets < > and also other special character).");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedsearch = new SavedSearch(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// EmailAuthorNameInclude
		baseClass.stepInfo("Select Include and Applying filter In Email Author Name DocExplorer Page");
		docList.EmailAuthorNameVerificationInDocexplorer();

		baseClass.stepInfo("Select Exclude and Applying filter In Email Author Name DocExplorer Page");
		docList.EmailAuthorNameInExcludeVerificationInDoc();
		// Clear Applied filter
		docList.clearAllAppliedFilters();

		baseClass.stepInfo("Select Include and Applying filter In EmailRecipientsName DocExplorer Page");
		docList.EmailRecipientsNameVerificationInDocexplorer();

		baseClass.stepInfo("Select Exclude and Applying filter In EmailRecipientsName DocExplorer Page");
		docList.EmailRecipientsNameVerificationInDocexplorerExlude();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54979
	 * @throws Exception
	 * @Description Doc List: Preview- Verify that on click of the print icon
	 *              success message should be displayed to inform the user that it
	 *              is processed in background task.
	 */

	@Test(description = "RPMXCON-54979", enabled = true, groups = { "regression" })
	public void verifyDocListPerviewAndProcessedInBackgroundTask() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54979");
		baseClass.stepInfo(
				"Doc List: Preview- Verify that on click of the print icon success message should be displayed to inform the user that it is processed in background task.");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedsearch = new SavedSearch(driver);
		DocExplorerPage docexp = new DocExplorerPage(driver);
		DocListPage docList = new DocListPage(driver);
		DocViewPage docView = new DocViewPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");

		baseClass.stepInfo(" Search meta data DocFile Type");
		sessionSearch.basicSearchWithMetaDataQuery(Input.searchDocFileType, "DocFileType");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Go to DocList Page");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getViewAllInDocListBtn());
		docView.getViewAllInDocListBtn().waitAndClick(5);
		baseClass.stepInfo("Successfully navigate to DocList Page");

		baseClass.stepInfo("Verify preview Doc list of non audio document");
		docList.DoclistPreviewNonAudio();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify downloded file name consists Previwed doc id as file name");
		baseClass.waitTime(5);
		docList.VerifyPerviewDocumentAndPrintAndDownload();

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:11/07/2022 RPMXCON-54531
	 * @throws Exception
	 * @Description Validate onpage filter for EmailAuthorName and EmailAllDomains
	 *              on DocList page.
	 */
	@Test(description = "RPMXCON-54531", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEamilAuthorNameAndAllDomainsInDocList(String username, String password, String role)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54531");
		baseClass.stepInfo("Validate onpage filter for EmailAuthorName and EmailAllDomains on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String emailAuthor = "EmailAuthorName";
		String emailAllDomain = "EmailAllDomains";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify DocList EmailAuthorName Include and EmailAllDomain Exclude Filter");
		docList.emailValueFirstIncludeAndEmailValueSecondExcludeVerifyInDocList(emailAuthor, emailAllDomain);

		// Clear Applied filter
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify DocList EmailAuthorName Exclude  and EmailAllDomain Include Filter");
		docList.emailValueFirstExcludeAndEmailValueSecondIncludeVerifyInDocList(emailAllDomain, emailAuthor);
		loginPage.logout();
	}
	/**
	 * @author Vijaya.Rani ModifyDate:12/07/2022 RPMXCON-54532
	 * @throws Exception
	 * @Description Validate onpage filter for CustodianName and MasterDate on DocList page.
	 */
	@Test(description = "RPMXCON-54532", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterCustodianNameAndMasterDateInDocList(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54532");
		baseClass.stepInfo(
				"Validate onpage filter for CustodianName and MasterDate on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String custodianName="CustodianName";
		
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("verify DocList CustodianName Include and MasterDate Between Filter");
		docList.emailCustodianNameIncludeAndMasterDate(custodianName);
		
		// Clear Applied filter
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();
		
		baseClass.stepInfo("verify DocList MasterDate Before and CustodianName Exclude Filter");
		docList.emailCustodianNameExcludeAndMasterDateBefore(custodianName);
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:12/07/2022 RPMXCON-54533
	 * @throws Exception
	 * @Description Validate onpage filter for EmailRecipientNames and DocFileSize on DocList page.
	 */
	@Test(description = "RPMXCON-54533", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
	public void verifyFilterEmailRecipientNamesAndDocFileSizeInDocList(String username, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54533");
		baseClass.stepInfo(
				"Validate onpage filter for EmailRecipientNames and DocFileSize on DocList page.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String emailRecipientNames="EmailRecipientNames";
		
		//Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		
		baseClass.stepInfo("verify DocList EmailRecipientNames Include and DocFileSize Filter");
		docList.emailRecipientNamesIncludeAndDocFileSize(emailRecipientNames);
		
		// Clear Applied filter
		baseClass.stepInfo("Clear the Applied Filters");
		docList.clearAllAppliedFilters();
		
		baseClass.stepInfo("verify DocList DocFileSize and EmailRecipientNames Include Filter");
		docList.emailRecipientNamesExcludeAndDocFileSize(emailRecipientNames);
		loginPage.logout();
	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-54224
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify that multi value fields should be displayed on Custom column configuration.
	 */
	@Test(description = "RPMXCON-54224", enabled = true, groups = { "regression" })
	public void verifyMutliValueFieldaDisplayCustomColumn() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54224");
		baseClass.stepInfo(
				"To verify that multi value fields should be displayed on Custom column configuration.");
		sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		String[] emailValue = { "EmailAuthorNameAndAddress", "EmailAuthorName", "EmailAuthorAddress" ,"EmailToNamesAndAddresses", "EmailToNames", "EmailToAddresses" ,"EmailCCNamesAndAddresses", "EmailCCNames", "EmailCCAddresses" };
		
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");
		
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
	
		baseClass.stepInfo("Select colunm In doclist EmailAuthorNames and EmailAuthorAddress");
		docList.SelectColumnDisplayByRemovingAddNewValues(emailValue);
		
		baseClass.passedStep("Multi value fields is displayed.user select any multivalued field the in grid it is displayed proper with \";\" delimiter Successfully.");
		
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
	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}
	


	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}

}
