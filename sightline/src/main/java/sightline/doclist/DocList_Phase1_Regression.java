package sightline.doclist;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class DocList_Phase1_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	SessionSearch search;
	SavedSearch savedSearch;
	DocListPage docList;
	TagsAndFoldersPage tagsAndFolderPage;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AssignmentsPage assignmentsPage;
	SoftAssert softAssertion;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		//Input input = new Input();
		//input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		assignmentsPage = new AssignmentsPage(driver);
		docList = new DocListPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		userManagement = new UserManagement(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54566 - Verify that Non-Audio Document Preview functionality is
	 * working proper in Saved Search >> DocList screen. Description : Verify that
	 * non audio Preview Document is working correctly.
	 */
	@Test(description = "RPMXCON-54566", groups = { "regression" })
	public void previewDocNonAudioDocBySavedSearch() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54566");
		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify that Non-Audio Document Preview functionality is working proper in Saved Search >> DocList screen. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Verify preview Doc list of non audio document");
		docList.DoclistPreviewNonAudio();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54086 - To verify as an user login, When user tries to select
	 * top check box, user can select a NO option to select all document for the
	 * current page only. Description : Verify user can get selected all documents
	 * from present document only by selecing No option on Doc View.
	 */
	@Test(description = "RPMXCON-54086", groups = { "regression" })
	public void verifyAllDocumentsSelectedForCurrentPageOnly() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54086");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify user can get selected all documents from present document only by selecing No option on Doc View ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Selecting all documents in current page");
		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Selecting Document check boxes in current page");
		docList.verifyAllDocumentsAreSelectedInCurrentPageOnly();

		baseClass.stepInfo("All documents are not selected other than current page");
		docList.verifyAllDocumentsAreNotSelectedOtherThanCurrentPage();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54085 - To verify as an user login, When user tries to select
	 * top check box it will ask the user you want selection for current page or
	 * across all the pages Description : Verify user can get select all documents
	 * from all pages and select all children of the selected parents are displayed
	 * in popup.
	 */
	@Test(description = "RPMXCON-54086", groups = { "regression" })
	public void verifyAllDocumentsAndAllChildrenOfParentsAreDisplayed() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54086");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify user can get select all documents from all pages and select all children of the selected parents are displayed in popup ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Verify selecting All Documents on all pages And All Children Of Parents Are Displayed");
		docList.verifyAllDocumentsAndAllChildrenOfParentsRadioAreDisplayed();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53849 - To verify, as an Project admin user login, When I will
	 * select multiple documents in Doc List, I will be able to see this documents
	 * on Doc View page, from View Document action button Description : Verify
	 * multiple documents selected in doclist page is able to see same documents in
	 * docview.
	 */
	@Test(description = "RPMXCON-53849", groups = { "regression" })
	public void verifyMultipleDocsSelectedInDocListReflectedToDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53849");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify multiple documents selected in doclist page is able to see same documents in docview. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.randomText);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Selecting multiple docs from DocList");
		ArrayList<String> selectedDocs = docList.selectMultipleDocsFromDocList(6);

		baseClass.stepInfo("Verify selected documents on Doc list reflected to docview");
		docList.verifySelectedDocumentsReflectedToDocView(selectedDocs);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53846 - To verify, as an Project admin user login, When I will
	 * select multiple documents in Doc List, I will be able to see this documents
	 * on Doc View page, from View Document action button Description : Verify
	 * document selected in doclist page is able to see same document in docview.
	 */
	@Test(description = "RPMXCON-53846", groups = { "regression" })
	public void verifySelectedDocInDocListReflectedToDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53846");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify single document selected in doclist page is able to see same document in docview. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Selecting doc from DocList");
		ArrayList<String> selectedDocs = docList.selectMultipleDocsFromDocList(1);

		baseClass.stepInfo("Verify selected document on Doc list reflected to docview");
		docList.verifySelectedDocumentsReflectedToDocView(selectedDocs);

		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53843 - To verify, As an Project admin user login, I will be
	 * able to go back to saved search page from DocList page from "Back to source"
	 * button. Description : Verify user navigate to Saved search by clicking back
	 * to source button on DocList page.
	 */
	@Test(description = "RPMXCON-53846", groups = { "regression" })
	public void verifyUserNaviagtedFromDocListPageToSavedSearchPage() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53846");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify single document selected in doclist page is able to see same document in docview. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Verify user navigate to Saved search by clicking back to source button on DocList page.");
		docList.verifyUserNaviagtedFromDocListPageToSavedSearchPage(searchname, Input.savedPageTitle);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54564 - Verify that Non-Audio Document Preview functionality is
	 * working proper in Basic Search >> DocList screen. Description : Verify that
	 * non audio Preview Document preview functionality is working correctly by
	 * basic search.
	 */
	@Test(description = "RPMXCON-54564", groups = { "regression" })
	public void verifyNonAudioDocumentPreviewFunctionalityByBasicSearch() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54564");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify that Non-Audio Document Preview functionality is working proper in Basic Search >> DocList screen ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Verify preview Doc list of non audio document");
		docList.DoclistPreviewNonAudio();

		baseClass.stepInfo("Verify options avaliable on document preview page");
		docList.verifyOptionsAvaliableOnDocPreview();

		baseClass.stepInfo("Close previed document");
		docList.closePreviewedDocument();

		baseClass.stepInfo("Verify selected document ID to previewed Doc id");
		docList.verifySelectedDocumetIdToPreviewedDocId(1,4);

		baseClass.stepInfo("Verify downloded file name consists Previwed doc id as file name");
		docList.verifyDownlodedFileNameConsistsPreviwedDocFileName();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54563 - Verify if parent has child document and that child
	 * again has one more child then it should show properly. Description : Verify
	 * that parent has child document and child again has another child document.
	 */
	@Test(description = "RPMXCON-54563", groups = { "regression" })
	public void verifyParentDocContainsChildDocAndChildDocContainsChildDoc() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54563");

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify if parent has child document and that child again has one more child then it should show properly. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Select page length");
		docList.Selectpagelength(Input.pageLength);

		baseClass.stepInfo("Verify Non of child documents are repeated");
		docList.verifyNonOfChildDocumentsAreRepeated();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54270 - To verify that Bulk folder is working proper in
	 * DocList. Description : To verify that Bulk folder is working proper in
	 * DocList.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54270", groups = { "regression" })
	public void verifyBulkFolderIsWorkingProperInDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54270");
		baseClass.stepInfo("#### To verify that Bulk folder is working proper in DocList ####");

		String foldernam = "FolderProd" + Utility.dynamicNameAppender();
		final String folderName = foldernam;
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		baseClass.stepInfo("Create a folder");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();

		docList = new DocListPage(driver);

		baseClass.stepInfo("Select all documents from all pages and all children of selected parents");
		docList.selectingAllDocFromAllPagesAndAllChildren();

		baseClass.stepInfo("Perfom bulk folder action for already existing folder");
		docList.bulkFolderExisting(folderName);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54237 - To verify that user should be able to expand multiple
	 * parents one after the other on a page without closing the collapsing the
	 * previous parent. Description : To verify that already opened parent is not
	 * closed by opening new parent.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54237", groups = { "regression" })
	public void verifyAlreadyOpenedParentNotClosedByOpeningNewParent() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54237");
		baseClass.stepInfo(
				"#### To verify that user should be able to expand multiple parents one after the other on a page without closing the collapsing the previous parent. ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();

		docList = new DocListPage(driver);

		baseClass.stepInfo("Select page length");
		docList.Selectpagelength(Input.pageLength);
		
		baseClass.stepInfo("Verify that already opened parent is not closed by opening new parent");
		docList.verifyAlreadyOpenedParentNotClosedByOpeningNewParent();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54223 -To verify that user can view the Child Document on doc
	 * list. Description : Verify that user can view the Child Document on doc list.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54223", groups = { "regression" })
	public void verifyUserViewChildDocumentOnDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54223");
		baseClass.stepInfo("####  Verify that user can view the Child Document on doc list ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();

		docList = new DocListPage(driver);

		baseClass.stepInfo("Select page length");
		docList.Selectpagelength(Input.pageLength);

		baseClass.stepInfo("Verify that user can view the Child Document on doc list");
		docList.verifyUserCanViewChildDocumetOnDocList();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53834 -To verify, As an Project Admin user login, I can be able
	 * to go to Doc List page from saved search page after selecting any saved query
	 * from Shared with me & apply action Document List from View Group Description
	 * : Verify that user navigated to Doc list form saved search page.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53834", groups = { "regression" })
	public void verifyUserNavigateToDocListFromSavedSearch() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;
		baseClass.stepInfo("Test case Id: RPMXCON-53834");
		baseClass.stepInfo(
				"####  To verify, As an Project Admin user login, I can be able to go to Doc List page from saved search page after selecting any saved query from Shared with me & apply action Document List from View Group ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:29/9/2021 Modified by: Baskar
	 * Description : To Verify, As an Project Admin user login, I will be able to
	 * release all documents to some security group from DocList page from Current
	 * search
	 */

	@Test(description = "RPMXCON-53768", enabled = true, groups = { "regression" })
	public void releaseAllDocsToSG() throws Exception {
		String securityGroup = "DSecurityGroup" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53768");
		loginPage.logout();

//		Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

//		Creating security group
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(securityGroup);
		driver.Navigate().refresh();
		securityGroupsPage.selectSecurityGroup(securityGroup);
		securityGroupsPage.addlayertosg();

//      session search to doclist
		search = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search to doclist");
		int pureHitCountBasic = search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();

//		selecting all document in dcolist

		baseClass.stepInfo("Select all documents from all pages and all children of selected parents");
		docList.selectingAllDocFromAllPagesAndAllChildren();

//		assign doclist document to security group
		docList.docListToBulkRelease(securityGroup);

//		access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

//		logout as Pa
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

//		login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		select security group after login
		baseClass.selectsecuritygroup(securityGroup);
		baseClass.stepInfo("Basic meta data search after assign docs to SG");
		search = new SessionSearch(driver);
		int pureHitCountBasicNew = search.basicContentSearch(Input.searchString1);
		if (pureHitCountBasic == pureHitCountBasicNew) {
			baseClass.passedStep("Selected document from doclist : " + pureHitCountBasic
					+ "After assigned to security group to Rmu : " + pureHitCountBasicNew + "");
			baseClass.stepInfo("Selected documnet from doclist and assigned document to security group are equal");
		} else {
			baseClass.stepInfo("Document not displaying in security group");
		}

		baseClass.selectsecuritygroup("Default Security Group");
//		logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

//		Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
//		Delete security group
		securityGroupsPage.deleteSecurityGroups(securityGroup);
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:30/9/2021 Modified by: Baskar
	 * Description : To verify, As an RMU login, I will be able to select documents
	 * and assign it to some assignment from DocList Page
	 */

	@Test(description = "RPMXCON-53749", enabled = true, groups = { "regression" })
	public void selectDocsFromDocListAndAssignToAssignment() throws Exception {
		String assignment = "NewAssignment" + Utility.dynamicNameAppender();
		String count = "10";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53749");

//      session search to doclist
		search = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search to doclist");
		int pureHitCountBasic = search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();

//		assign doclist document to assignment to reviewer
		docList.DoclisttobulkAssign(assignment, count);
		baseClass.stepInfo("Selected document from doclist to assignment count : " + count);
		assignmentsPage.assignmentCreation(assignment, Input.codingFormName);
		baseClass.stepInfo("Assignment created : " + assignment);
		assignmentsPage.assignmentDistributingToReviewer();

//		logout as Rmu
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

//		verify the assigned dcoument from doclist
		assignmentsPage.getAssignmentText(assignment).ScrollTo();
		softAssertion.assertEquals((boolean) assignmentsPage.getAssignmentText(assignment).Displayed(), true);
		String text = assignmentsPage.getAssignmentText(assignment).getText().trim();
		baseClass.stepInfo("Assign document are displyed in assignmnet :" + text);
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar Created date: NA Modified date: NA Modified by: Baskar
	 * @Description : To verify, user is able to remove multiple document from Tag
	 *              in Doc list page
	 */

	@Test(description = "RPMXCON-53739", groups = { "regression" })
	public void removeTagsInDocsInDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		String tagNam = "ATag" + Utility.dynamicNameAppender();
		final String tagName = tagNam;
		baseClass.stepInfo("Test case Id: RPMXCON-53739");
		baseClass.stepInfo("#### To verify, user is able to remove all documents from Tag in Doc list page ####");

		search = new SessionSearch(driver);
		baseClass.stepInfo("Basic Search to doclist to view documents");
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();

		docList = new DocListPage(driver);
		baseClass.stepInfo("Selecting document in doclist page");
		docList.selectAllDocumentsInCurrentPageOnly();
		docList.addNewBulkTag(tagName);
		// docList.addBulkTagAcceptRequestOnPopup(true);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select all documents in current page for removing tags");
		docList.selectAllDocumentsInCurrentPageOnly();
		baseClass.stepInfo("Untag documents");
		docList.unTagDocmuments(tagName);

		// Click on default tags
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.refreshPage();
		tagsAndFolderPage.clickOnDefaultTag();

		baseClass.stepInfo("Verify documents unassigned for a tag");
		tagsAndFolderPage.verifyTagContainedDocumentsCount(tagName, 0);

		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53788 -To verify, user is able to remove all documents from Tag
	 * in Doc list page Description : Verify user able to tag and untag documents
	 * from doclist and verify count in Tags and folder page.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53788", groups = { "regression" })
	public void verifyUserRemoveAllDocumentsFromTagInDocLlistPage() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		String tagNam = "ATag" + Utility.dynamicNameAppender();
		final String tagName = tagNam;
		baseClass.stepInfo("Test case Id: RPMXCON-53788");
		baseClass.stepInfo("#### To verify, user is able to remove all documents from Tag in Doc list page ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("View in Doclist");
		search.ViewInDocList();

		docList = new DocListPage(driver);

		baseClass.stepInfo("Select all documents in current page");
		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Add new bulk tag of selected documents");
		docList.addNewBulkTag(tagName);

		baseClass.stepInfo("Accept add bulk tag request");
		// docList.addBulkTagAcceptRequestOnPopup(true);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Refresh page");
		tagsAndFolderPage.refreshPage();

		baseClass.stepInfo("Navigate to tags and folders page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Click on defalut tag");
		tagsAndFolderPage.clickOnDefaultTag();

		baseClass.stepInfo("Verify documents count assigned for a tag");
		tagsAndFolderPage.verifyTagContainedDocumentsCount(tagName, 10);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Navigate to DocList page");
		driver.getWebDriver().get(Input.url + "Document/DocList");

		baseClass.stepInfo("Select all documents in current page");
		docList.selectAllDocumentsInCurrentPageOnly();

		baseClass.stepInfo("Untag documents");
		docList.unTagDocmuments(tagName);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("Refresh page");
		tagsAndFolderPage.refreshPage();

		baseClass.stepInfo("Navigate to tags and folders page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Click on defalut tag");
		tagsAndFolderPage.clickOnDefaultTag();

		baseClass.stepInfo("Verify documents unassigned for a tag");
		tagsAndFolderPage.verifyTagContainedDocumentsCount(tagName, 0);

		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: NA Testcase
	 * id : 54292 - To verify that In DocList, export data action should work
	 * successfully for all documents Description : Verify export data action work
	 * successfully for all documents in doc list page.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54292", groups = { "regression" })
	public void verifyExportActionInDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54292");
		baseClass.stepInfo(
				"#### To verify that In DocList, export data action should work successfully for all documents ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("View in doc list from session search");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Selecting all DocId");
		docPage.SelectIngAllDocuments();

		baseClass.stepInfo("Selecting all File and run report");
		docPage.handlingExportDataPopUp();

		baseClass.stepInfo("download the document and verify");
		docPage.DownloadingTheFile();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: NA Testcase
	 * id : 53750 - To verify, As an RMU login, I will be able to select documents
	 * and Remove it from some assignment from DocList page Description : Verify
	 * select documents and remove it from some assignment from DocList page.
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53750", groups = { "regression" })
	public void verifySelectDocsAndRemoveDocsFromAssignent() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53750");
		baseClass.stepInfo(
				"#### To verify, As an RMU login, I will be able to select documents and Remove it from some assignment from DocList page ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Select The Document in Doclistpage");
		docPage.documentSelection(3);

		baseClass.stepInfo("Bulk Assign the selected Document");
		docPage.bulkassign();

		String testing = "test" + Utility.dynamicNameAppender();
		AssignmentsPage AssignmentsPage = new AssignmentsPage(driver);

//		baseClass.stepInfo("Navigate back for two times");
//		AssignmentsPage.navigateBack(2);

		baseClass.stepInfo("Verified The Bulk assigned Document in Assignmentpage");
		AssignmentsPage.verifyingTheDocCount(testing, 3, Input.codeFormName);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		baseClass.stepInfo("Select The Document in Doclistpage");
		docPage.documentSelection(3);

		baseClass.stepInfo("Bulk assign and unassign docs");
		docPage.bulkassignAndUnAssignTheDoc(testing);

baseClass.stepInfo("verified the BulkUnassign Document");
		AssignmentsPage.countOfDocAfterUnAssigning(testing);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53676 - To Verify, As a Admin user login, I will be able to
	 * filter the doc List grid by date. Description :Verify filter the doc List
	 * grid by date.
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-53676", groups = { "regression" })
	public void verificationOfApplyFilterDateInDoclistPage() throws InterruptedException, AWTException {

		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53676");
		baseClass.stepInfo(
				"#### To Verify, As a Admin user login, I will be able to filter the doc List grid by date. ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Select Date From Filter and Applying Filter And Verifying The date");
		docPage.dateVerificationForDocuments(Input.date);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53684 -To verify EmailAuthorDomain filter on doc list grid.
	 * Description : Verify EmailAuthorDomain filter on doc list grid
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53684", groups = { "regression" })
	public void verificationOfEmailDomainNameInDocListPage() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53684");
		baseClass.stepInfo("#### To verify EmailAuthorDomain filter on doc list grid ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Select Author name  From Filter and Applying Filter And Verifying The Name");
		docPage.emailAuthorDomainNameVerificationInDoc();

		baseClass.stepInfo("Select Exclude and Applying filter InDocList Page");
		docPage.emailAuthorDomainNameInExcludeVerificationInDoc();
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53708 -To Verify "Clear All" functionality to clear applied
	 * filters. Description : Verify "Clear All" functionality to clear applied
	 * filters
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53708", groups = { "regression" })
	public void verifyClearAllFunctionalityClearAppliedFilters() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53708");
		baseClass.stepInfo("#### To Verify clear all functionality to clear applied filters. ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Apply custodian filter");
		docPage.applyCustodianNameFilter(Input.metaDataCN);

		baseClass.stepInfo("Verify applied include custodian filter is added");
		docPage.verifyAppliedIncludeCustodianNameFilterIsAdded(Input.metaDataCN);

		baseClass.stepInfo("Clear all applied filters");
		docPage.clearAllAppliedFilters();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: NA Testcase
	 * Testcase id : 53747 - To verify, As an RMU user login, I am able to assign
	 * individual document to Assignment from Doc List Page. Description : Verify
	 * assign single document in Doclist
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53747", groups = { "regression" })
	public void verifyAssignSingleDocumentOnDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53747");
		baseClass.stepInfo(
				"#### To verify, As an RMU user login, I am able to assign individual document to Assignment from Doc List Page ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Selected Doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Select The Document in Doclistpage");
		docPage.documentSelection(1);

		baseClass.stepInfo("Bulk Assign the selected Document");
		docPage.bulkassign();

		String testing = "test" + Utility.dynamicNameAppender();
		final String test = testing;
		AssignmentsPage AssignmentsPage = new AssignmentsPage(driver);

//		baseClass.stepInfo("Navigate back for two times");
//		AssignmentsPage.navigateBack(2);
//		baseClass.stepInfo("Navigate to assignment page");
//		AssignmentsPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Verified The Bulk assign single Document in Assignmentpage");
		AssignmentsPage.verifyingTheDocCount(test, 1, Input.codeFormName);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53683 -To verify filtering doc list grid by EmailAlldomain.
	 * Description : Verify filtering doc list grid by EmailAlldomain
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-53683", groups = { "regression" })
	public void verificationOfEmailAllDomainInDocListPage() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53683");
		baseClass.stepInfo("#### To verify filtering doc list grid by EmailAlldomain ####");
		SessionSearch search = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);
		search.ViewInDocList();
		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo(
				"Select Author name  From Filter and Applying Filter And Verifying The Name in Email All Domain");
		docPage.EmailAllDomainsNameVerificationInDoc();

		baseClass.stepInfo("Select Exclude and Applying filter In Email All Domain DocList Page");
		docPage.EmailAllDomainsNameInExcludeVerificationInDoc();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53680 - Verify filtering doc List grid by Author Name.
	 * Description : Verify filtering doc List grid by Author Name
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53680", groups = { "regression" })
	public void verificationOfEmailAuthorNameInDocListPage() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53680");
		baseClass.stepInfo("#### Verify filtering doc List grid by Author Name ####");
		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Searched data view in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo(
				"Select Email Author Name  From Filter and Applying Filter And Verifying The Name in Email Author Name");
		docPage.EmailAuthorNameVerificationInDoc();

		baseClass.stepInfo("Select Exclude and Applying filter In Email Author Name in DocList Page");
		docPage.EmailAuthorNameInExcludeVerificationInDoc();
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: NA Testcase
	 * id : 53783 - To verify, as the user is able to remove all documents from
	 * Folder in Doc list page. Description : Verify user is able to remove all
	 * documents from Folder in Doc list page
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53783", groups = { "regression" })
	public void verifyRemoveMultipleDocumentsFromFolderDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		String test = "Folder" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-53783");
		baseClass.stepInfo(
				"#### To verify, as the user is able to remove all documents from Folder in Doc list page ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Search data to viw in doclist");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Selecting all the documents");
		docPage.selectingAllDocuments();

		baseClass.stepInfo("filling bulkfolder in doclistpage");
		docPage.bulkFolderInDocListPage(test);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		baseClass.stepInfo("Verifying The Count of document as expected count given in doclist");
		tagsAndFolderPage.selectingFolderAndVerifyingTheDocCount(test, 10);

		baseClass.stepInfo("Search data to viw in doclist");
		search.ViewInDocList();

		baseClass.stepInfo("Once again selecting all the documents");
		docPage.selectingAllDocuments();

		baseClass.stepInfo("Selecting unfolder structure in bulkfolder and selecting the folder ");
		docPage.selectingUnFoldersAndVerifyingTheDocCount(test);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		baseClass.stepInfo("Verifying The Count of document as expected count given in doclist");
		tagsAndFolderPage.selectingFolderAndVerifyingTheDocCount(test, 0);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53728-To verify user can unfold single document from Folder in
	 * Doc list page. Description :Verify unfold single document from Folder in Doc
	 * list page
	 * 
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53728", groups = { "regression" })
	public void verifyRemoveSingleDocumentFromFolderDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		String testData1 = Input.testData1;
		String test = "Folder" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-53728");
		baseClass.stepInfo("#### To verify user can unfold single document from Folder in Doc list page ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Selecting one document");
		docPage.documentSelection(1);

		baseClass.stepInfo("filling bulkfolder in doclistpage");
		docPage.bulkFolderInDocListPage(test);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		baseClass.stepInfo("Verifying The Count of document as expected count given in doclist");
		tagsAndFolderPage.selectingFolderAndVerifyingTheDocCount(test, 1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		baseClass.stepInfo("Once again selecting document");
		docPage.documentSelection(1);

		baseClass.stepInfo("Selecting unfolder structure in bulkfolder and selecting the folder ");
		docPage.selectingUnFoldersAndVerifyingTheDocCount(test);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		baseClass.stepInfo("Verifying The Count of document as expected count given in doclist");
		tagsAndFolderPage.selectingFolderAndVerifyingTheDocCount(test, 0);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53665- To verify user is be able to filter doc list grid by
	 * 'File Size'. Description : Verify user is be able to filter doc list grid by
	 * 'File Size'
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-53665", groups = { "regression" })
	public void verifyingTheCountOfDocFileSize() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		String testData1 = Input.testData1;
		baseClass.stepInfo("Test case Id: RPMXCON-53665");
		baseClass.stepInfo("#### Verify user is be able to filter doc list grid by 'File Size' ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Verifying th docfilesize count is lesser in row doc data file size");
		docPage.selectDocFileSize(Input.docFileSize, Input.docFileName);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54269- To verify that filter is working properly in DocList.
	 * Description : Verify that filter is working properly in DocList
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-54269", groups = { "regression" })
	public void verifyFilterWorkingProperlyOrNotInDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		String testData1 = Input.testData1;
		baseClass.stepInfo("Test case Id: RPMXCON-54269");
		baseClass.stepInfo("#### Verify that filter is working properly in DocList ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Verifying th docfilesize count is lesser in row doc data file size");
		docPage.selectDocFileSize(Input.docFileSize, Input.docFileName);
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53770 - release a single document to some security group from
	 * DocList page from Saved search. Description : Verify PA release a single
	 * document to some security group from DocList page from Saved search.
	 */
	@Test(description = "RPMXCON-53770", groups = { "regression" })
	public void verifyAdminReleaseSingleDocToSecurityGroup() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53770");
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify PA release a single document to some security group from DocList page from Saved search. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53771 - Un-release a single document from security group from
	 * DocList page from Saved search. Description : Verify PA Un-release a single
	 * document from security group from DocList page from Saved search.
	 */
	@Test(description = "RPMXCON-53771", groups = { "regression" })
	public void verifyAdminUnReleaseSingleDocToSecurityGroup() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53771");
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		SessionSearch search = new SessionSearch(driver);
		String searchname1 = Input.randomText + Utility.dynamicNameAppender();
		final String searchname = searchname1;

		baseClass.stepInfo(
				"#### Verify PA unrelease a single document to some security group from DocList page from Saved search. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString2);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Open Doc list from Saved search page");
		savedSearch.savedSearchToDocList(searchname);

		docList = new DocListPage(driver);

		baseClass.stepInfo("Select The Document in Doclistpage");
		docList.documentSelection(1);

		baseClass.stepInfo("Bulk unrelease to security group of selected documents");
		docList.bulkUnRelease(Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 53666- To validate user is able to shuffle the column position
	 * on doclist Grid. Description : Validate user is able to shuffle the column
	 * position on doclist Grid
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-53666", groups = { "regression" })
	public void verifySufflingOfColumnInDocListPage() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		String testData1 = Input.testData1;
		baseClass.stepInfo("Test case Id: RPMXCON-53666");
		baseClass.stepInfo("#### To validate user is able to shuffle the column position on doclist Grid ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Suffling  the column in doclist page");
		docPage.suffleTheColumnInDocListPage(Input.metaDataName, Input.docFileType);
		loginPage.logout();

	}

	/**
	 * Author : Gopinath Created date: 09/10/2021 Modified date: NA Modified by:
	 * Gopinath Testcase id : 53663- To verify user will be able to hide a column on
	 * the doc list. Description : Verify user will be able to hide a column on the
	 * doc list
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-53663", groups = { "regression" })
	public void verifyUserHideColumn() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53663- DocList Sprint 04");
		baseClass.stepInfo("#### To verify user will be able to hide a column on the doc list ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.randomText.toLowerCase());

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Hiding The document column and verifying it");
		docPage.verifyingColumnInDocListPage(Input.metaDataName.toUpperCase());
		loginPage.logout();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54241-To verify that if the user checks 'Select All' check box
	 * on the grid, a confirmation message will show up with OK/Cancel options.
	 * Description : To verify that if the user checks 'Select All' check box on the
	 * grid, a confirmation message will show up with OK/Cancel options.
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-54241", groups = { "regression" })
	public void verifyUnreleaseSingleDocFromSecurityGroupOnDocList() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54241- DocList Sprint 04");
		baseClass.stepInfo(
				"#### verify that if the user checks 'Select All' check box on the grid, a confirmation message will show up with OK/Cancel options ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(Input.randomText.toLowerCase());

		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Selecting all document and verifying the selected documents");
		docPage.selectingCheckboxAndVerifyingTheDocSelection();

		baseClass.stepInfo("Select the document and verifying whether it is selected");
		docPage.DeselectTheRadioBtnAndVerifyingTheDocCheckbox();

		baseClass.stepInfo("Selecting Yes and verifying the child documents is selected");
		docPage.SelectDropDownAndVerifyDocCheckbox();

		baseClass.stepInfo("Selecting No and verifying the child documents is selected");
		docPage.SelectDropDownAndCancelItVerifyDocCheckbox();
		loginPage.logout();
	}

	/**
	 * Author : Brundha Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 54529-Validate Doclist sorting after shuffling the columns in
	 * the list Description :Verify Doclist sorting after shuffling the columns.
	 * 
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-54529", groups = { "regression" })
	public void verfyingTheAscendingAndDescendingOrderInColumn() throws InterruptedException, AWTException {
		baseClass = new BaseClass(driver);
		String testData1 = Input.testData1;
		baseClass.stepInfo("Test case Id: RPMXCON-54529");
		baseClass.stepInfo("#### Verify Doclist sorting after shuffling the columns ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Verify Doclist sorting after shuffling the columns");
		docPage.sufflingColumnValueInDocListPage();

		docPage.verifyingDescendingOrderSortingInColumn();

		baseClass.stepInfo("removing the column in doclist page");
		docPage.removeColumn();

		baseClass.stepInfo("Adding column in doclist page");
		docPage.addColumn();

		baseClass.stepInfo("Verify Doclist sorting after shuffling the columns");
		docPage.sufflingColumnValueInDocListPage();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch search1 = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search1.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search1.ViewInDocList();

		DocListPage doc = new DocListPage(driver);

		baseClass.stepInfo("Verify Doclist sorting after shuffling the columns");
		doc.sufflingColumnValueInDocListPage();

		doc.verifyingDescendingOrderSortingInColumn();

		baseClass.stepInfo("removing the column in doclist page");
		doc.removeColumn();

		baseClass.stepInfo("Adding column in doclist page");
		doc.addColumn();

		baseClass.stepInfo("Verify Doclist sorting after shuffling the columns");
		doc.sufflingColumnValueInDocListPage();
		loginPage.logout();

	}

	/**
	 * @author Gopinath
	 * @TestCase Id:54456 To verify that parent child hierarchical presentation in
	 *           DocList is shown properly
	 * @Description To To verify that parent child hierarchical presentation in
	 *              DocList is shown properly
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54456", groups = { "regression" })
	public void verifychildHierarchicalPresentationDocList() throws InterruptedException {

		String folderName = "Folder" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-54456");
		baseClass.stepInfo(
				"####  To verify that parent child hierarchical presentation in DocList is shown properly####");

		docList = new DocListPage(driver);
		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("perform bulk folder");
		search.bulkFolder(folderName);

		TagsAndFoldersPage tagAndFol = new TagsAndFoldersPage(driver);

		baseClass.stepInfo("select folder and view in DocList");
		tagAndFol.ViewinDocListthrFolder(folderName);

		baseClass.stepInfo("Select page length");
		docList.Selectpagelength(Input.pageLength);
		
		baseClass.stepInfo("Verify that user can view the Child Document on doc list");
		docList.verifyUserCanViewChildDocumetOnDocList();

		driver.Navigate().refresh();
		baseClass.stepInfo("Select page length");
		docList.Selectpagelength(Input.pageLength);

		baseClass.stepInfo("Verify sequence of parent and Child Document on doc list");
		docList.verifySequenceOrderOfchildDocsInDocList();

	}

	/**
	 * @author Gopinath
	 * @TestCase Id:54460 To Verify that on selecting 'Tally' action from DocList no
	 *           javascript pop up gets displayed
	 * @Description:To To Verify that on selecting 'Tally' action from DocList no
	 *                 javascript pop up gets displayed
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54460", groups = { "regression" })
	public void verifyDocListTallyActionNoPopUP() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String testData1 = Input.testData1;
		baseClass.stepInfo("Test case Id: RPMXCON-54460");
		baseClass.stepInfo(
				"#### To Verify that on selecting 'Tally' action from DocList no javascript pop up gets displayed ####");

		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Basic meta data search");
		search.basicContentSearch(testData1);

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();

		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("select documents");
		docPage.selectAllDocs();

		baseClass.stepInfo("perform tally");
		docPage.performTallyAction();

	}

	/**
	 * @author Gopinath
	 * @Testcase Id:54285 Verify 3 play counter readouts are displayed on preview
	 *           document from doc list
	 * @Description:To Verify 3 play counter readouts are displayed on preview
	 *                 document from doc list
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54285", alwaysRun = true, groups = { "regression" })
	public void verifyPlayCounterReadOutsDisplayed() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54285");
		baseClass.stepInfo("Verify 3 play counter readouts are displayed on preview document from doc list");

		SessionSearch search = new SessionSearch(driver);

		loginPage.logout();
		baseClass.stepInfo("login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Basic meta data search");
		search.audioDocumentMetaData();

		baseClass.stepInfo("View search data in doc list");
		search.ViewInDocList();
		DocListPage docPage = new DocListPage(driver);

		baseClass.stepInfo("Verify 3 play counters are displayed in preview document");
		docPage.verifyPlayCountersDisplay();
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
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
}
