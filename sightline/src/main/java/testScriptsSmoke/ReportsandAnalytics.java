package testScriptsSmoke;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import pageFactory.WorkflowPage;

public class ReportsandAnalytics {
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage  tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	Utility utility;
	DocExplorerPage docexp;
	WorkflowPage workflow;
	DocViewPage docView;
	DocViewMetaDataPage docViewMetaDataPage;
	String hitsCount;
	
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		
	}
	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
}
	
	
	@Test(description ="RPMXCON-54947",groups = { "regression" })
	public void verifyTagExcludeFilterOnDocExplorer() throws InterruptedException {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54947 Docexplorer of Sprint-4 ");
		baseClass.stepInfo(
				"####  Verify that Exclude filter functionality works properly when TAG name contains word between on Doc Explorer screen. ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagsAndFoldersPage.CreateTag(random1, Input.securityGroup);

		docexp = new DocExplorerPage(driver);
		
		baseClass.stepInfo("Select some documets from doc explorer table and bulk tag selected documents");
		docexp.selectDocumentsAndBulkTag(3,random1);
		
		baseClass.stepInfo("Get total number of documents.");
		int totalDocCount = docexp.totalDocumentsCount();
		
		baseClass.stepInfo("Perform exclude filter by tag");
		docexp.performExculdeTagFilter(random1);
		
		baseClass.stepInfo("Verify documents after applying exclude functionality by tag");
		docexp.verifyExcludeFunctionlityForTag(totalDocCount, 3);
		loginPage.logout();
	}
	
	@Test(description ="RPMXCON-54946",groups = { "regression" })
	public void verifyTagIncludeFilterOnDocExplorer() throws InterruptedException {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54946 Docexplorer of Sprint-4 ");
		baseClass.stepInfo(
				"####  Verify that Include filter functionality works properly when TAG name contains word between on Doc Explorer screen.. ####");
		utility = new Utility(driver);
		String random = Input.betweenTagName + Utility.dynamicNameAppender();
		final String random1 = random;
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Create a new tag contains word between");
		tagsAndFoldersPage.CreateTag(random1, Input.securityGroup);

		docexp = new DocExplorerPage(driver);
		
		baseClass.stepInfo("Select some documets from doc explorer table and bulk tag selected documents");
		ArrayList<String> selectedDocs = docexp.selectDocumentsAndBulkTag(3,random1);
		
		baseClass.stepInfo("Refresh page");
		docexp.refreshPage();
		
		baseClass.stepInfo("Perform exclude filter by tag");
		docexp.performInculdeTagFilter(random1);
		
		baseClass.stepInfo("Verify documents after applying include functionality by tag");
		docexp.verifyIncludeTagFilterWorksProperly(selectedDocs);
		loginPage.logout();
	}
	
	 @Test(description ="RPMXCON-55001",groups={"regression"})
	 public void verifyUserNavigateToDocListWithFilters(){
		baseClass=new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	    baseClass.stepInfo("Test case Id: RPMXCON-55001- DocExplorer Sprint 08");
		baseClass.stepInfo("#### Verify that when a user configures MasterDate and EmailSubject filters and  selects check-boxes manually and navigates Doc-Explorer to DocList. ####");	
		
		DocExplorerPage docExplorer=new DocExplorerPage(driver);
		
		baseClass.stepInfo("Navigate To Doc Explorer Page");
		docExplorer.navigateToDocExplorerPage();
		
		baseClass.stepInfo("Selecting the document in docExplorer page");
		docExplorer.masterDateWithEmailSubject(Input.proximity, Input.masterDate);
		
		baseClass.stepInfo("Selecting the document in docExplorer page");
		docExplorer.selectDocument(1);
		docExplorer.getDocumentsCheckBoxbyRowNum(1).waitAndClick(10);
		
		
		baseClass.stepInfo("View document in doc view on doc explorer");
		docExplorer.navigateToDoclistFromDocExplorer();
		
		baseClass.stepInfo("Verify Doc List Header");
		docExplorer.verifyDocListHeader();
		loginPage.logout();
	
	 }
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
		
	 @Test(description = "RPMXCON-70307", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
		public void verifyApplyFilterInDocList(String username, String password, String role) throws InterruptedException {
	        String custodianexp= Input.custodianName_Andrew;
			baseClass.stepInfo("Test case Id: RPMXCON-70307: Verify that to apply the filters, user can either hit the ‘Apply Filter’ button or hit the ‘enter’ key on the keyboard. ");
			DocListPage docList = new DocListPage(driver);
			//Login As user
			loginPage.loginToSightLine(username, password);
			baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
			docexp = new DocExplorerPage(driver);
			docexp.navigateToDocExplorerPage();
			baseClass.stepInfo("Navigated to Doc Explorer page");
			driver.waitForPageToBeReady();
			docexp.SelectingAllDocuments("yes");
			docexp.docExpViewInDocList();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return docList.getTileView().Visible();
				}
			}), Input.wait60);
			baseClass.stepInfo("Navigated to DocList page");
			driver.waitForPageToBeReady();
			docList.custodianFilter(Input.custodianName_Andrew);
			docList.getApplyFilter().waitAndClick(3);;
			driver.waitForPageToBeReady();
			String custodianactual = docList.AfterFilterverifyCustodianName(Input.custodianName_Andrew);
			softAssertion.assertEquals(custodianexp,custodianactual);
			System.out.println(custodianactual);
			baseClass.passedStep("Filter is performed with Custodian Name : "+custodianexp);
			baseClass.passedStep("Filter is applied and Verified");
			softAssertion.assertAll();
			loginPage.logout();
		}
	 @Test(description = "RPMXCON-70308", dataProvider = "Users_PARMU", enabled = true, groups = { "regression" })
		public void verifyAfterApplyFilterInDocListandClearField(String username, String password, String role) throws InterruptedException {
	        String custodianexp= Input.custodianName_Andrew;
			baseClass.stepInfo("Test case Id: RPMXCON-70308: Verify that When user deletes text from field and hits apply/enter, the DocList grid should refresh and load the default view of the page/List");
			DocListPage docList = new DocListPage(driver);
			//Login As user
			loginPage.loginToSightLine(username, password);
			baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");
			docexp = new DocExplorerPage(driver);
			docexp.navigateToDocExplorerPage();
			baseClass.stepInfo("Navigated to Doc Explorer page");
			driver.waitForPageToBeReady();
			docexp.SelectingAllDocuments("yes");
			docexp.docExpViewInDocList();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return docList.getTileView().Visible();
				}
			}), Input.wait60);
			baseClass.stepInfo("Navigated to DocList page");
			driver.waitForPageToBeReady();
			docList.custodianFilter(Input.custodianName_Andrew);
			docList.getApplyFilter().waitAndClick(3);
			String custodianactual = docList.AfterFilterverifyCustodianName(Input.custodianName_Andrew);
			softAssertion.assertEquals(custodianexp,custodianactual);
			System.out.println(custodianactual);
			baseClass.passedStep("Filter is performed with Custodian Name : "+custodianexp);
			baseClass.passedStep("Filter is applied and Verified");
			softAssertion.assertAll();
			
			driver.waitForPageToBeReady();
			docList.CustodianNameFilterFileds().Clear();
			docList.getApplyFilter().waitAndClick(3);
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(docList.getDocListDefaultTable().isDisplayed(),true);
			baseClass.passedStep("Removed applied text from the filter fields and Verified the Default view of the page/List");
			softAssertion.assertAll();
			loginPage.logout();
		}
	 
	 @Test(description = "RPMXCON-51863", enabled = true, alwaysRun = true, groups = { "regression" })
		public void verifyTextRemarks() throws Exception {
	// Selecting Document from Session search
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
			Actions actions = new Actions(driver.getWebDriver());
			baseClass.stepInfo("Test case Id: RPMXCON 51863");
			SessionSearch sessionsearch = new SessionSearch(driver);
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			UtilityLog.info("Logged in as User: " + Input.rmu1userName);
			sessionsearch.basicContentSearch(Input.randomText);
			baseClass.stepInfo("Search with text input --test-- completed");
			sessionsearch.ViewInDocView();
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			docView = new DocViewPage(driver);
			String docId = docView.getDocumentWithoutRedaction();
			driver.waitForPageToBeReady();
			
			docView.selectDocInMiniDocList(docId);
			baseClass.waitForElement(docViewRedact.getDocView_Redactrec_textarea());
			
//			Added on 11_04
			docView = new DocViewPage(driver);
			docView.addRemarkToNonAudioDocument(1, 20, "Remark by RMU");
			if (docViewRedact.deleteRemarksBtn().Displayed() && docViewRedact.deleteRemarksBtn().Enabled()) {
				assertTrue(true);
				baseClass.passedStep("The Remark has been saved by RMU");
			} else {
				assertTrue(false);
			}
			loginPage.logout();
		}
	 @Test(description = "RPMXCON-52030" ,enabled = true, alwaysRun = true, groups = { "smoke", "regression" })
		public void verifyRedactionasReviewer() throws Exception {
			
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			baseClass.stepInfo("Test case Id: RPMXCON 52030");
			loginPage = new LoginPage(driver);
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	// Impersonating as Reviewer and searching with text input		
			baseClass.impersonateRMUtoReviewer();
			SessionSearch sessionsearch = new SessionSearch(driver);
			sessionsearch.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Search with text input --test-- completed");
			sessionsearch.ViewInDocView();
	// Redacting using rectangular redaction
			docViewRedact.redactRectangleUsingOffset(100, 100, 50, 20);
			baseClass.stepInfo("A rectangle redaction has been applied");
			docViewRedact.selectingRectangleRedactionTag();
			baseClass.stepInfo("A rectangle redaction has been saved under Default Redaction Tag");
			if (docViewRedact.redactionIcon().getWebElement().isDisplayed()
					&& docViewRedact.redactionIcon().getWebElement().isEnabled()) {
				assertTrue(true);
				baseClass.passedStep("The Rectangular redaction has been applied and saved as Reviewer");
			} else {
				assertFalse(false);
			}
			loginPage.logout();
		}
	 @Test(description = "RPMXCON-53892", enabled = true, groups = { "regression" })
		public void verifyAsReviewerPerformAllActinsFromDocLsitPage() throws Exception {

			baseClass.stepInfo("Test case Id: RPMXCON-53892");
			baseClass.stepInfo(
					"To verify, As an Reviewer user login, I will be able to perform all actions from Doc List page, when I will go from Saved search to Doc List.");
			SoftAssert softAssert=new SoftAssert();
			SessionSearch sessionsearch = new SessionSearch(driver);
			sessionsearch = new SessionSearch(driver);
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
			sessionsearch.basicContentSearch(Input.searchStringStar);
			// Save searched content
			sessionsearch.saveSearch(searchName1);

			// Open Doc list from Saved search page
			savedSearch.savedSearchToDocList(searchName1);

			// select multiple Documents and bulktag
			docList.documentSelection(3);
			sessionsearch.bulkTagExisting(tagName);
			baseClass.stepInfo("DocListpage Action Bulktag is created");
			// select multiple Documents and bulkfolder
			driver.waitForPageToBeReady();
			docList.documentSelection(3);
			docList.bulkFolderExisting(folderName);
			baseClass.stepInfo("DocListpage Action Bulkfolder is created");
			// filter action
			driver.waitForPageToBeReady();
			docList.applyCustodianNameFilter("P Allen");
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
	 @Test(description ="RPMXCON-52658",enabled = true, groups = { "regression" })
		public void verifyDocviewFunctionalityThroughAssignments() throws InterruptedException {
			baseClass = new BaseClass(driver);
			SessionSearch sessionsearch = new SessionSearch(driver);
			docView = new DocViewPage(driver);
			assignmentPage = new AssignmentsPage(driver);

			String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
			String comment = "comment" + Utility.dynamicNameAppender();

			// Pre-requisite
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Test case Id: RPMXCON-51461");
			baseClass.stepInfo("Verify that a  DocView functionality is working properly through assignments");
			assignmentPage.createAssignment(assignmentName, Input.codeFormName);
			sessionsearch.basicContentSearch(Input.testData1);
			sessionsearch.bulkAssignExisting(assignmentName);
			assignmentPage.editAssignmentUsingPaginationConcept(assignmentName);
			driver.waitForPageToBeReady();
			assignmentPage.addReviewerAndDistributeDocs();
			driver.waitForPageToBeReady();
			baseClass.waitForElement(assignmentPage.getAssignmentSaveButton());
			assignmentPage.getAssignmentSaveButton().waitAndClick(5);
			loginPage.logout();
			// Login as reviewer and verify
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("Logined as Reviewer");
			assignmentPage.SelectAssignmentByReviewer(assignmentName);
			baseClass.stepInfo("Select assigned assignment and navigated to docview");
			driver.waitForPageToBeReady();
			docView.popOutCodingFormPanel();
			docView.editCodingFormComplete();
			docView.verifyCheckMarkIconFromMiniDocListChildWindow();
			int checkMarkIconCount = docView.getCheckMarkIcon().size();
			baseClass.waitForElement(docView.getDocView_Last());
			docView.getDocView_Last().Click();
			driver.waitForPageToBeReady();
			docView.verifyCodingFormPanelIsLoadedAfterDocsAreViewed();
			docView.editCodingFormComplete();
			baseClass.waitForElementCollection(docView.getCheckMarkIcon());
			int checkMarkIconsAfterEditingCF = docView.getCheckMarkIcon().size();
			if (checkMarkIconsAfterEditingCF > checkMarkIconCount) {
				baseClass.passedStep("Check mark icon displayed for completed document");
			}
			loginPage.logout();
		}
	 @Test(description = "RPMXCON-51092", enabled = true, groups = { "regression" })
		public void validatePlayAudioInsideDocView() throws Exception {
			docViewPage = new DocViewPage(driver);
			sessionSearch = new SessionSearch(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51092");
			baseClass.stepInfo("Verify that audio files play functionality is working properly inside docview screen");

			// Login As
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("User is logged in as Review Manager");

			// Searching audio document with different term
			baseClass.stepInfo("Searching Content documents based on search audio string");
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
			sessionSearch.ViewInDocView();
			baseClass.stepInfo("Open the searched documents in doc view");

			docViewPage.playAudioOnly();

			// logout
			loginPage.logout();

			// Login As
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("User is logged in as PA");

			// Searching audio document with different term
			baseClass.stepInfo("Searching Content documents based on search audio string");
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
			sessionSearch.ViewInDocView();
			baseClass.stepInfo("Open the searched documents in doc view");

			docViewPage.playAudioOnly();

			// logout
			loginPage.logout();

			// Login As
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("User is logged in as Reviewer");

			// Searching audio document with different term
			baseClass.stepInfo("Searching Content documents based on search audio string");
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
			sessionSearch.ViewInDocView();
			baseClass.stepInfo("Open the searched documents in doc view");

			docViewPage.playAudioOnly();

			// logout
			loginPage.logout();

		}
	 @Test(description = "RPMXCON-52009", enabled = true, groups = { "regression" })
		public void audioRedactionDefaultTagSelection() throws InterruptedException, ParseException {
			baseClass = new BaseClass(driver);
			docViewPage = new DocViewPage(driver);
			SessionSearch sessionSearch = new SessionSearch(driver);
			MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
			String headerName = "RedactionTags";
			int index;

			List<String> docIDlist = new ArrayList<>();
			String firnstDocname;
			String audioSearchInput = Input.audioSearch;

			baseClass.stepInfo("Test case id :RPMXCON-52009 Sprint 12");
			baseClass.stepInfo(
					"Verify that when applies audio redaction for the first time then application should automatically select the â€˜Default Redaction Tagâ€™");

			// Login as USER
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Logged in as : " + Input.rmu1FullName);

			// Audio Search
			sessionSearch.audioSearch(audioSearchInput, Input.language);

			// Launch DocVia via Search
			sessionSearch.ViewInDocViews();
			baseClass.passedStep("launched DocVIew via Search");

			// Main method
			docIDlist = miniDocListpage.getDocListDatas();
			firnstDocname = miniDocListpage.docToCHoose(docIDlist.size(), docIDlist);
			baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

			// Validate audio docs eye icon with persistent hits
			docViewPage.audioReduction(Input.defaultRedactionTag);

			index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);
			

			// AfterSave Default Selection
			String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
			System.out.println(defautTagSelection);
			baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
					"After Save : â€˜Default Redaction Tagâ€™ is displayed", "After Save : invalid redaction tag selected");

			// Audio Redaction Tag deletion
			docViewPage.deleteAudioRedactionTag();

			loginPage.logout();

		}
	 @Test(description = "RPMXCON-46865", enabled = true , groups = { "regression" })
		public void VerifyAddedReviewerRemarkForAudioDocInBasicSearch()
				throws Exception {

			SessionSearch sessionSearch = new SessionSearch(driver);
			DocViewPage docviewpage = new DocViewPage(driver);
			SavedSearch saveSearch = new SavedSearch(driver);

			Map<String, String> datas = new HashMap<String, String>();
			String remark = "Remark" + Utility.dynamicNameAppender();
			String searchName = "SS" + Utility.dynamicNameAppender();
			int iteration = 1;

			baseClass.stepInfo("Test case Id:RPMXCON-46865 Sprint 11");
			baseClass.stepInfo("Verify that user can edit the reviewer remarks in audio doc view");

			// login as RMU/reviewer
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			

			// adding remark to audio documents
			sessionSearch.audioSearch(Input.audioSearch, Input.language);
			sessionSearch.saveSearch(searchName);
			sessionSearch.ViewInDocView();
			datas = docviewpage.addRemarkToDocumentsT(iteration, remark, true, "Success");

			loginPage.logout();

			// login as RMU/reviewer
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			

			// SavedSearch to DocVIew
			saveSearch.savedSearchToDocView(searchName);
			driver.waitForPageToBeReady();

			// Verify Existing remarks + Edit File
			docviewpage.verifyExistingRemarks(iteration, datas, true, true);

			// Delete Search
			baseClass.stepInfo("Initiating Delete Search");
			saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

			loginPage.logout();

		}
	 @Test(description = "RPMXCON-46924", enabled = true, groups = { "regression" })
		public void validatingCodeSameAsLastForAudioDocs() throws InterruptedException {
			baseClass.stepInfo("Test case Id: RPMXCON-46924");
			baseClass.stepInfo("Verify the reviewer apply 'Code same as last' action then request "
					+ "should be complete immediately");
			String Asssignment = "Assignment" + Utility.dynamicNameAppender();
			String comment = "comment" + Utility.dynamicNameAppender();
			softAssertion = new SoftAssert();
			sessionSearch = new SessionSearch(driver);
			assignmentPage = new AssignmentsPage(driver);
			docViewPage = new DocViewPage(driver);

			// Login as Reviewer Manager
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

			// search to Assignment creation
			sessionSearch.audioSearch(Input.audioSearch, Input.language);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(Asssignment, Input.codingFormName);
			assignmentPage.assignmentDistributingToReviewer();

			// logout
			loginPage.logout();

			// Login as Reviewer Manager
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

			docViewPage.selectAssignmentfromDashborad(Asssignment);
			baseClass.stepInfo("User on the doc view after selecting the assignment");

			// Code same as
			docViewPage.clickCheckBoxDocListActionCodeSameAs(2);
			// editing coding form using complete action
			docViewPage.editCodingForm(comment);
			docViewPage.completeButton();
			String docviewSec = docViewPage.getClickDocviewID(2).getText();
			String activeDocId = docViewPage.getDocId().getText();
			softAssertion.assertEquals(docviewSec, activeDocId);
			baseClass.stepInfo("Next docId displayed in docview panel as active docId");
			for (int i = 1; i <= 2; i++) {
				docViewPage.getClickDocviewID(i).javascriptclick(docViewPage.getClickDocviewID(i));
				driver.waitForPageToBeReady();
				boolean unCompleteBtn = docViewPage.getUnCompleteButton().Displayed();
				System.out.println(unCompleteBtn);
				softAssertion.assertTrue(unCompleteBtn);
			}
			baseClass.stepInfo("Document completed successfully");
			docViewPage.getClickDocviewID(3).javascriptclick(docViewPage.getClickDocviewID(3));
			driver.waitForPageToBeReady();
			docViewPage.clickCodeSameAsLast();
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getClickDocviewID(3));
			docViewPage.getClickDocviewID(3).javascriptclick(docViewPage.getClickDocviewID(3));
			// validating code same as last as per the previous completed docs
			docViewPage.verifyComments(comment);
			loginPage.logout();
		}
	 @Test(description = "RPMXCON-54003",enabled = true, groups = { "regression" })
		public void verifyEditCFRemoveTag() throws Exception {
			
		    baseClass.stepInfo("Test case Id: RPMXCON-54003");
		    baseClass.stepInfo("To verify, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing");
		    
		    CodingForm cf = new CodingForm(driver);
			
			String cfName = "codingForm"+Utility.dynamicNameAppender();
			
			// login as RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

			//validation
		    cf.createCodingform(cfName);
		    cf.editCodingForm(cfName);
		    cf.removeNthCodingForm(0);
		    cf.updateCodingForm();
		    baseClass.passedStep("Page is saved successfully after removing");
		    
		    //delete created coding form
		    cf.deleteCodingForm(cfName,cfName);
		    
		    baseClass.passedStep("verified, As an RMU login, When I will edit an existing coding form, In edit page I can remove any of tags, comments or Metadata that associated with this coding form, & save it after removing");
		    loginPage.logout();
		}
	 
	 @Test(description ="RPMXCON-50788",alwaysRun = true,groups={"regression"} )
		public void verifyMetadataTabFromDocViewPage() throws Exception {
			baseClass=new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-50788 DocView/MetaData Sprint 03");
			utility= new Utility(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo("#### Verify Metadata tab from Doc view page for Reviewer user ####");
			loginPage = new LoginPage(driver);
			
			
			baseClass.stepInfo("Login with Reviewer");
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			Reporter.log("Logged in as User: " + Input.rev1userName);
			
			SessionSearch sessionSearch = new SessionSearch(driver);
			
			baseClass.stepInfo("Basic meta data search");
			sessionSearch.basicMetaDataSearch(Input.metaDataName,null,Input.metaDataCN,null);
			sessionSearch.addDocsMetCriteriaToActionBoard();
			
			baseClass.stepInfo("Verify Metadata tab should be displayed with default metadata fields");
			docViewMetaDataPage.verifyMetaDataColumns();
		
			baseClass.stepInfo("Verify Metadata tab from Doc View page.");
			docViewMetaDataPage.verifyMetadataTabFromDocViewPage();
			
			baseClass.stepInfo("Close meta data popup");
			docViewMetaDataPage.closeMetaDataPopup();
			
			baseClass.stepInfo("Edit coding form on Doc view");
			docViewMetaDataPage.editCodingFormOnDocView(Input.comment);
			
			baseClass.stepInfo("Open Doc view meta data pop up on doc view page.");
			docViewMetaDataPage.openDocViewMetaDataPopup();
			
			baseClass.stepInfo("Verify Metadata tab pop up");
			docViewMetaDataPage.verifyMetaDataPopup();
			
			baseClass.stepInfo("Close meta data popup");
			docViewMetaDataPage.closeMetaDataPopup();
			
			baseClass.stepInfo("Save edited coding form");
			docViewMetaDataPage.saveEditedCodingForm();
			
			baseClass.stepInfo("Edit coding form on Doc view");
			docViewMetaDataPage.editCodingFormOnDocView(Input.comment);
			
			baseClass.stepInfo("Open Doc view meta data pop up on doc view page.");
			docViewMetaDataPage.openDocViewMetaDataPopup();
			
			baseClass.stepInfo("Verify Metadata tab pop up");
			docViewMetaDataPage.verifyMetaDataPopup();
			
			baseClass.stepInfo("Close meta data popup");
			docViewMetaDataPage.closeMetaDataPopup();
			
			baseClass.stepInfo("Save complete edited coding form and verify user navigated to present document to next document");
			docViewMetaDataPage.saveCompleteEditedCodingFormAndVerifyNavigatedToNextDoc();
			
			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();
			
			baseClass.stepInfo("Edit coding form on Doc view");
			docViewMetaDataPage.editCodingFormOnDocView(Input.comment);
			
			baseClass.stepInfo("Open Doc view meta data pop up on doc view page.");
			docViewMetaDataPage.openDocViewMetaDataPopup();
			
			baseClass.stepInfo("Verify Metadata tab pop up");
			docViewMetaDataPage.verifyMetaDataPopup();
			
			baseClass.stepInfo("Close meta data popup");
			docViewMetaDataPage.closeMetaDataPopup();
			
			baseClass.stepInfo("Save complete edited coding form and verify user navigated to present document to next document");
			docViewMetaDataPage.saveCompleteEditedCodingFormAndVerifyNavigatedToNextDoc();
			
			loginPage.logout();		
		}
	 @Test(description = "RPMXCON-46855", enabled = true, groups = { "regression" })
		public void verifyingOnClickAnyDocumentFromMiniDocListAndViewInDocViewSection() throws InterruptedException {

		 baseClass.stepInfo("Test case Id: RPMXCON-46855");
		 baseClass.stepInfo(
					"To verify on click of any document from mini doc list, RMU or Reviewer should see the document in doc view section");

			int rowNumber = 2;
			docexp = new DocExplorerPage(driver);
			docView = new DocViewPage(driver);
			// login as Users
			baseClass.stepInfo(
					"**Step-1 Logged in as RMU/Reviewer user in same project and security group as per pre-requisites **");
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

			// navigating to DocExplorer and selecting Documents
			baseClass.stepInfo("**Step-2 From Doc Explorer, select few documents and select action as 'View in Docview'");
			docexp.navigateToDocExplorerURL();
			docexp.selectAllDocumentsFromCurrentPage();
			baseClass.stepInfo("selecting all documents in current page of DocExplorer.");

			// viewing the selected Documents in DocView
			docexp.docExpViewInDocView();
			baseClass.stepInfo("performing view In DocView action.");

			// selecting the document from miniDocList and verifying whether it is viewed in
			// Default view
			baseClass.stepInfo("**Step-3 Click the document from mini doc list by RMU");
			docView.selectDocumentFromMiniDocList(rowNumber);

			// logout
			loginPage.logout();
		}
	 @Test(description ="RPMXCON-56969",dataProvider = "Users_PARMU", groups = { "regression" },alwaysRun = true )
		public void verifyReportGeneration_searches(String username, String password, String role)
				throws InterruptedException {
			baseClass.stepInfo("Test case Id: RPMXCON-56969");
			baseClass.stepInfo("Verify and generate Communications Explorer with source as Search");
			loginPage.loginToSightLine(username, password);
			SoftAssert softAssertion = new SoftAssert();
			
			String saveSearchName = "ST" + Utility.dynamicNameAppender();
			baseClass.stepInfo("Logged in as -" + role);
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(Input.testData1);
			hitsCount = sessionSearch.verifyPureHitsCount();
			sessionSearch.saveSearch(saveSearchName);
			this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			driver.waitForPageToBeReady();
			CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
			comExpPage.generateReportusingSearch();
			baseClass.waitForElement(comExpPage.getCommunicationExplorer_ApplyResult());
			if (comExpPage.getCommunicationExplorer_ApplyResult().Displayed()) {
				softAssertion.assertTrue(true);
				baseClass.passedStep("After Selecting the My searches in select sources tab Report is generated.");

			} else {
				softAssertion.assertTrue(false);
				baseClass.failedStep("After Selecting the My searches in select sources tab Report is not generated.");

			}
			softAssertion.assertAll();
			 SavedSearch savedSearch = new SavedSearch(driver);
			 savedSearch.SaveSearchDelete(saveSearchName);
			loginPage.logout();

		}
	 @Test(description ="RPMXCON-56909",groups = {  "regression" })
		public void conceptExplorer() throws InterruptedException {
		 loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			ConceptExplorerPage conceptreport = new ConceptExplorerPage(driver);
			conceptreport.ValidateConceptExplorerreport();
			loginPage.logout();
		}
	 
	 @Test(description ="RPMXCON-52633",enabled = true, groups = { "regression" })
		public void validationDocsCountSaveSearchAndFolder() throws InterruptedException, ParseException {
			baseClass.stepInfo("Test case Id: RPMXCON-52633");
			baseClass.stepInfo("To verify that workflow should run and displays doc count, if Source is 'Save "
					+ "Search Selector' and action is'Folder Selector'.");
			int Id;
			String tagName = "tag" + Utility.dynamicNameAppender();
			String folderName = "folder" + Utility.dynamicNameAppender();
			String SearchName = "WF" + Utility.dynamicNameAppender();
			String wfName = "work" + Utility.dynamicNameAppender();
			String wfDesc = "Desc" + Utility.dynamicNameAppender();
			String assgn = "Assgn" + Utility.dynamicNameAppender();

			// Login as Reviewer Manager
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

			tagsAndFoldersPage = new TagsAndFoldersPage(driver);
			tagsAndFoldersPage.CreateTag(tagName, "Default Security Group");
			tagsAndFoldersPage.CreateFolder(folderName, "Default Security Group");

			// Search for any string
			sessionSearch = new SessionSearch(driver);
			int count = sessionSearch.basicContentSearch(Input.searchString1);

			// Save the search
			sessionSearch.saveSearch(SearchName);
			SavedSearch ss = new SavedSearch(driver);
			ss.getSaveSearchID(SearchName);
			Id = Integer.parseInt(ss.getSavedSearchID().getText());
			System.out.println(Id);
			UtilityLog.info(Id);

			// creating new work flow
			workflow = new WorkflowPage(driver);
			workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false, 1);
			workflow.selectWorkFlowUsingPagination(wfName);

			// Running workflow
			int workFlowId = workflow.gettingWorkFlowId(wfName);
			workflow.actionToRunWorkFlow();
			// Page refresh
			workflow.refreshingThePage();

			// validating count in workflow list
			workflow.workFlowIdPassing(workFlowId);
			workflow.applyFilter();
			int docCount = workflow.gettingWorkFlowListDocCount(wfName);
			softAssertion.assertEquals(count, docCount);
			baseClass.passedStep("saved search count " + count + " and run workflow count " + docCount
					+ "are equal and displayed in workflow list");

			// validating count in action
			workflow.actionToHistory(wfName);
			baseClass.stepInfo("Validating in action block");
			int actionDocCount = workflow.gettingActionDocCount(wfName);
			softAssertion.assertEquals(count, actionDocCount);
			baseClass.passedStep("saved search count " + count + " and run workflow count " + actionDocCount
					+ "are equal and displayed in action block");
			workflow.closeHistoryPopUpWindow();
			softAssertion.assertAll();

			// logout
			loginPage.logout();
		}
	 @DataProvider(name = "Users_PARMU")
		public Object[][] PA_RMU() {
			Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
					{ Input.pa1userName, Input.pa1password, "PA" } };
			return users;
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
