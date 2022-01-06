package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.DocumentAuditReportPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchAudio_Regresssion {
	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SecurityGroupsPage sg;
	SoftAssert softAssertion;
	ReportsPage report;
	TagsAndFoldersPage tagsAndFolderPage;
	Categorization categorize;
	AssignmentsPage assign;
	ProductionPage page;
	BatchPrintPage batch;
	DocListPage dcPage;
	MiniDocListPage miniDocListpage;

	public static int purehits;
	String folderName = "Folder" + Utility.dynamicNameAppender();
	String searchName = "Search Name" + Utility.dynamicNameAppender();
	String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
	String searchNameRMU = "RMU" + Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
		sg = new SecurityGroupsPage(driver);
		softAssertion = new SoftAssert();
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

	}

	/**
	 * @author Jeevitha Search Audio File And Shares With Project
	 *         Administrator.(RPMXCON-57419) Search Audio File And Share with
	 *         Default Security Group.(RPMXCON-57420) search audio file and Share
	 *         with security group.(RPMXCON-57421) schedule Save search
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(groups = { "regression" }, priority = 1)
	public void searchAudioAndSharePA() throws InterruptedException, ParseException {
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57419,RPMXCON-57420,RPMXCON-57421  SavedSearch ");

		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveSearchadvanced(searchName);

//		Create security group
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(securitygroupname);

		// Shared with project Administrator and Default Security group
		saveSearch.shareSavedSearchAsPA(searchName, "Default");
		base.stepInfo("Shared Successfuly with  Project Administartor");
		base.stepInfo("Shared Successfuly with  Default Security Group");

		// Shared with Security group
		saveSearch.shareSavedSearchAsPA(searchName, securitygroupname);
		base.stepInfo("Shared Successfuly with  Security group");

		// Schedule save search
		saveSearch.scheduleSavedSearch(searchName);

		// Delete Security group
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.deleteSecurityGroups(securitygroupname);

		System.out.println("Successfully ran for PA user");
		base.stepInfo("Successfully ran for PA user");
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Search Audio File And Shares With Default
	 *         Security group. and schedule the save search(RPMXCON-57420)
	 * @Stabilization - done - session.saveSearchadvanced(searchName);
	 */
	@Test(groups = { "regression" }, priority = 2)
	public void searchAudioAndShareToDefaultsgRmu() throws InterruptedException, ParseException {
		// Login as a Rmu
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-57420  SavedSearch ");

		base.selectsecuritygroup("Default Security Group");

		// Search audio file and save the file
		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveSearchadvanced(searchName);
		saveSearch = new SavedSearch(driver);

		// Shared with Default Security group
		saveSearch.shareSavedSearchRMU(searchName, "Default");
		base.stepInfo("Shared Successfuly with  Default Security Group");

		Thread.sleep(2000); // Added for consolidated execution
		// Schedule save search)
		saveSearch.scheduleSavedSearch(searchName);

		System.out.println("Successfully ran for RMU user");
		base.stepInfo("Successfully ran for RMU user");

		login.logout();
	}

	/**
	 * @author Jeevitha Description : Search Audio File And Shares With Default
	 *         Security group. and schedule the save search(RPMXCON-57420)
	 * @Stabilization - done - session.saveSearchadvanced(searchName);
	 */
	@Test(groups = { "regression" }, priority = 3)
	public void searchAudioAndShareToDefaultsgRev() throws InterruptedException, ParseException {
		// Login as a Rev
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("RPMXCON-57420  SavedSearch ");

		base.selectsecuritygroup("Default Security Group");

		// search audio file and save
		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveSearchadvanced(searchName);
		saveSearch = new SavedSearch(driver);

		// Shared with Default Security group
		saveSearch.shareSavedSearchRMU(searchName, "Default");
		base.stepInfo(" shared Successfuly with  Default Security Group");

		// Schedule save search
		saveSearch.scheduleSavedSearch(searchName);

		System.out.println("Successfully ran for REV user");
		base.stepInfo("Successfully ran for REV user");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/07/21 Modified date:N/A Modified by:N/A
	 *         Description : Pre-requisites: 1. Project with 1K documents has
	 *         security groups SG1 and SG2 2. SG1 and SG2 has different sub-set of
	 *         project documents, around 100 in each
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 1)
	public void preRequesties() throws InterruptedException {

		List<String> list = new ArrayList<String>();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");

		sg = new SecurityGroupsPage(driver);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		String securitygroupname1 = "securitygroupname" + Utility.dynamicNameAppender();
		String securitygroupname2 = "securitygroupname" + Utility.dynamicNameAppender();

		sg.AddSecurityGroup(securitygroupname1);
		System.out.println(securitygroupname1 + " : Added");
		sg.AddSecurityGroup(securitygroupname2);
		System.out.println(securitygroupname2 + " : Added");

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		login.logout();
		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");

		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		list.add(searchName);
		list.add(searchName1);
		for (String a : list) {
			System.out.println(a);
			saveSearch.savedSearch_Searchandclick(a);
			saveSearch.preRequestCreation(a, securitygroupname1);
		}

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/01/21 Modified date:N/A Modified by: Raghuram A
	 *         Description : For Reviewer Login - Validate available options for
	 *         user while saving a search query from session search page
	 * @param testMethod
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyAvailableOptionsFromSessionSearch() {

		base.stepInfo("Test case Id: RPMXCON-49952 - Saved Search Sprint 02");
		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		session.basicContentSearch(Input.searchString2);
		base.waitForElement(session.getSaveSearch_Button());
		session.getSaveSearch_Button().Click();

		session.saveSearchPopupVerification();
		base.stepInfo("Pop-up verification completed");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/01/21 Modified date:9/06/21 Modified by: Raghuram
	 *         A Description : SA/DA/PA user impersonate down as RMU/RU role, create
	 *         Searchgroups and Searches, and then runs the Custom Document Data
	 *         Report against My saved searches in PAU role
	 * @param testMethod
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void customDataReportAgainstPA() throws InterruptedException {

		String folderName = "Folder" + Utility.dynamicNameAppender();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		report = new ReportsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57409 - Saved Search Sprint 02");

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);

		// Get Count
		session.searchSavedSearchResult(Input.mySavedSearch);
		int aggregateHitCount = session.saveAndReturnPureHitCount();

		// Impersonate As RMU via PA and create new searchgroup
		base.impersonatePAtoRMU();
		base.stepInfo("Impersonated As RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.saveSearchInNode(searchName);

		// impersonate As REV and create new searchgroup
		base.impersonateRMUtoReviewer();
		base.stepInfo("Impersonated As Reviewer");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNode(searchName1);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.impersonateReviewertoRMU();
		base.stepInfo("Impersonated As RMU");

		report.VerificationAndreportGenerator(newNodeFromPA, newNodeFromRMU, newNodeFromRev, folderName, searchName,
				searchName1, aggregateHitCount);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/03/21 Modified date:N/A Modified by: Raghuram A
	 *         Description : SAU/DAU/PAU impersonate down as RMU/RU role, create
	 *         Searchgroups and Searches, and then runs Categorization against My
	 *         saved searches in PAU role
	 * @param testMethod
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void categorizationAgainstPAsavedSearch() throws InterruptedException {

		String folderName = "Folder" + Utility.dynamicNameAppender();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		report = new ReportsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57414 - Saved Search Sprint 02");

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");

		// impersonate As RMU via PA and create new searchgroup
		base.impersonatePAtoRMU();
		base.stepInfo("Impersonated As RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.saveSearchInNode(searchName);

		// impersonate As REV and create new searchgroup
		base.impersonateRMUtoReviewer();
		base.stepInfo("Impersonated As Reviewer");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNode(searchName1);

		driver.waitForPageToBeReady();
		// impersonate As Reviewer to RMU
		base.impersonateReviewertoRMU();
		base.stepInfo("Impersonated As RMU");

		categorize = new Categorization(driver);
		categorize.categorizationFlow(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, "RMU");

		// impersonate As PA via RMU
		base.impersonateDAtoPA();
		base.stepInfo("Back As PA");

		categorize.categorizationFlow(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, "PA");

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description:PAU impersonate down as RMU/RU role,
	 *                              create Searchgroups and Searches, and then runs
	 *                              the Advanced Batch Management Report against My
	 *                              saved searches in RMU role (RPMXCON-57417)
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void advanceBatchManagementReport() throws InterruptedException {
		String searchName1 = "Search01" + Utility.dynamicNameAppender();
		String folderName = "Folder01" + Utility.dynamicNameAppender();
		String assignmentName = "Assignmenet01" + Utility.dynamicNameAppender();
		String codingform = "CodingForm01" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("(RPMXCON-57417 Saved Search");

		report = new ReportsPage(driver);
		base.impersonatePAtoRMU();

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchName1);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNode = saveSearch.getSavedSearchNewNode().getText();

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNode(searchName1);
		session.bulkFolderExisting(folderName);
		assign = new AssignmentsPage(driver);
		assign.createAssignment(assignmentName, codingform);

		// impersonate As REV
		base.impersonateRMUtoReviewer();

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchName1);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNode1 = saveSearch.getSavedSearchNewNode().getText();

		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNode(searchName1);

		session.bulkFolderExisting(folderName);

		// impersonate As RMu
		driver.Manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonateReviewertoRMU();

		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		int totalDocs = report.advanceBatchManagementReport(folderName, assignmentName);
		System.out.println("Purehit = " + purehit + "," + "total docs = " + totalDocs);
		softAssertion.assertEquals(purehit, totalDocs);
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Jeevitha R
	 * @throws InterruptedException Description: PAU impersonate down as RMU/RU
	 *                              role, create Searchgroups and Searches, and then
	 *                              runs the Review Results Report against My saved
	 *                              searches in PAU role RPMXCON-57413
	 * @Stabilization - done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void reviewResultReport() throws InterruptedException {
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String TagName1 = "Tag1" + Utility.dynamicNameAppender();
		String folderName1 = "Folder1" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57413 Saved Search");

		// Pre-reqiuisite
		base.impersonatePAtoRMU();

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		tagsAndFolderPage.CreateTag(TagName, "Default Security Group");
		int purehitRMU = session.basicContentSearch(Input.searchString1);
		session.bulkFolderExisting(folderName);
		session.bulkTagExisting(TagName);
		System.out.println(purehitRMU);

//		 impersonate As REV
		base.waitForElement(base.getSignoutMenu());
		base.impersonateRMUtoReviewer();

		int purehitReviewer = session.basicContentSearch(Input.searchString1);
		session.bulkFolderExisting(folderName);
		session.bulkTagExisting(TagName);
		System.out.println(purehitReviewer);

//		impersonate as RMU
		base.waitForElement(base.getSignoutMenu());
		base.impersonatePAtoRMU();
		report = new ReportsPage(driver);
		report.reviewResultReport();
		report.selectFoldersInReviewResult(folderName);
		report.selectTagsInReviewResult(TagName);
		report.applyChangesButton().waitAndClick(10);

		// impersonate As PA
		driver.waitForPageToBeReady();
		base.waitForElement(report.getPageHeader());
		base.impersonateDAtoPA();

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		report.reviewResultReport();
		report.verifyFolderNotPresent(folderName, TagName);

		// create folder and add save search in folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName1, "Default Security Group");
		tagsAndFolderPage.CreateTag(TagName1, "Default Security Group");
		int purehitPA = session.basicContentSearch(Input.searchString1);
		session.bulkFolderExisting(folderName1);
		session.bulkTagExisting(TagName1);
		System.out.println(purehitPA);

		report.reviewResultReport();
		report.selectFoldersInReviewResult(folderName1);
		report.selectTagsInReviewResult(TagName1);
		report.applyChangesButton().Click();
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description: PAU impersonate down as RMU/RU
	 *                              role, create Searchgroups and Searches, and then
	 *                              runs Production against My saved searches in PAU
	 *                              role successfully RPMXCON-57416
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 9)
	public void productionManagementReport() throws InterruptedException {
		String searchGroup = "Group2" + Utility.dynamicNameAppender();
		String saveSearch1 = "search2" + Utility.dynamicNameAppender();
		String saveSearch2 = "search3" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p1" + Utility.dynamicNameAppender();
		String productionname2 = "p2" + Utility.dynamicNameAppender();
		String SearchNodeNamePA = "PA1" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57416 Saved Search");
		base.impersonatePAtoRMU();

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String Node1 = saveSearch.getSavedSearchNewNode().getText();

		// add save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNode(saveSearch2);

		// impersonate As REV
		driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonateRMUtoReviewer();

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String Node2 = saveSearch.getSavedSearchNewNode().getText();

		// add save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNode(saveSearch2);

		// impersonate As RMU
		driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonatePAtoRMU();

		// To Select & check mySavedSearch and savedNodeSearch
		ProductionPage page = new ProductionPage(driver);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(saveSearch1, saveSearch2);
		page.navigateToNextSection();
		System.out.println("Search groups And Search Of Rmu& Rev Selected Successfully ");
		base.stepInfo("Search groups And Search Of Rmu& Rev Selected Successfully ");

		// impersonate As PA
		driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		base.impersonateDAtoPA();

		// create new searchgroup
		saveSearch.createNewSearchGrp(saveSearch2);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String nw_node = saveSearch.getSavedSearchNewNode().getText();

		// To check searches Not present in PA
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(searchGroup, saveSearch2);
		System.out.println("Search groups and search Of RMU is not available");
		base.stepInfo("Search groups and search Of RMU is not available");

		// add save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch2);
		session.saveSearchInNode(SearchNodeNamePA);

		// To Select & check mySavedSearch and savedNodeSearch
		driver.getWebDriver().get(Input.url + "Production/Home");
		page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(saveSearch2, SearchNodeNamePA);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
//	page.fillingGeneratePage();
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description : PA impersonate down as RMU/RU
	 *                              role, create Searchgroups and Searches, and then
	 *                              runs Batch Print against My saved searches in
	 *                              PAU role RPMXCON-57415
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void batchPrint() throws InterruptedException {
		String searchGroup = "Group2" + Utility.dynamicNameAppender();
		String saveSearch1 = "search2" + Utility.dynamicNameAppender();
		String saveSearch2 = "search3" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57415 Saved search");
		base.impersonatePAtoRMU();

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNode = saveSearch.getSavedSearchNewNode().getText();

		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNode(saveSearch2);

		// impersonate As REV
		base.impersonateRMUtoReviewer();

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNode1 = saveSearch.getSavedSearchNewNode().getText();
		System.out.println(newNode1);
		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNode(saveSearch2);

		// impersonate As RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		// To Select & check mySavedSearch and savedNodeSearch
		BatchPrintPage batch = new BatchPrintPage(driver);
		batch.saveSearchRadiobutton(saveSearch1);

		// impersonate As PA
		driver.Manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonateDAtoPA();

		batch = new BatchPrintPage(driver);
		batch.saveSearchRadiobutton(saveSearch1);

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchName);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String new_node = saveSearch.getSavedSearchNewNode().getText();
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		driver.getWebDriver().get(Input.url + "BatchPrint/");
		batch.saveSearchRadiobutton(searchName);
		login.logout();

	}

	/**
	 * Date: 9/13/21 Modified date:1/5/2022 Modified by: Description : Schedule
	 * saved searches(with audio and non-audio docs) under <My Saved Search> and
	 * verify documents - RPMXCON-57418 Sprint 03
	 */
	@Test(groups = { "regression" }, priority = 11)
	public void searchAndShareAsPa() throws InterruptedException, ParseException {
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57418 SavedSearch ");

		saveSearch.createNewSearchGrp(searchName);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNode = saveSearch.getSavedSearchNewNode().getText();
		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveAdvanceSearchInNode(searchName, newNode);

		// Shared with Security group
		saveSearch.shareSavedSearchFromNode(searchName, "Default");
		base.stepInfo("Shared Successfuly with  Security group");

		// Schedule save search
		driver.getWebDriver().navigate().refresh();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return saveSearch.getCollapsedSharedWithDefaultSecurityGroup().Visible();
			}
		}), Input.wait60);
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return saveSearch.getSharedGroupName(newNode).Visible();
			}
		}), Input.wait60);
		saveSearch.getSharedGroupName(newNode).waitAndClick(10);
		saveSearch.scheduleSavedSearch(searchName);

		System.out.println("Successfully ran for PA user");
		base.stepInfo("Successfully ran for PA user");
		login.logout();
	}

	/**
	 * Date: 9/13/21 Modified date:1/5/2022 Modified by: Description : SA/DA/PA
	 * impersonate down as RMU/RU role, create Searchgroups and Searches, and then
	 * runs the Document Audit Report against My saved searches in PAU role -
	 * RPMXCON-57410 Sprint 03
	 * 
	 * @Stabilzation - done
	 */
	@Test(groups = { "regression" }, priority = 12)
	public void documentAuditReport() throws InterruptedException {
		String searchGroup = "Group1" + Utility.dynamicNameAppender();
		String saveSearch1 = "search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "search2" + Utility.dynamicNameAppender();
		String assignment = "Assign" + Utility.dynamicNameAppender();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// impersonate As RMU
		base.impersonatePAtoRMU();
		base.stepInfo("RPMXCON-57410 Saved Search");

		// create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNodeRMU = saveSearch.getSavedSearchNewNode().getText();
		System.out.println(newNodeRMU);

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNode(saveSearch2);

//		impersonate As REV
		base.impersonateRMUtoReviewer();

//		create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNodeREV = saveSearch.getSavedSearchNewNode().getText();
		System.out.println(newNodeREV);

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNode(saveSearch2);

//		impersonate as RMU
		Thread.sleep(2000);
		base.impersonatePAtoRMU();
		DocumentAuditReportPage documentAuditReport = new DocumentAuditReportPage(driver);
		documentAuditReport.verifySource(saveSearch1, saveSearch2);

//		impersonate As PA
		Thread.sleep(5000);
		base.impersonateSAtoPA();

//		create new searchgroup
		saveSearch.createNewSearchGrp(searchGroup);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNodePA = saveSearch.getSavedSearchNewNode().getText();
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch2);
		session.saveSearchInNode(newNodePA);

//		impersonate As PA
		Thread.sleep(3000);
		base.impersonateSAtoPA();

		documentAuditReport.verifySource(saveSearch1, saveSearch2);

		documentAuditReport.verifySource(newNodePA, saveSearch2);
		login.logout();
	}

	/**
	 * Date: 9/13/21 Modified date:1/5/2022 Modified by: Description : For RMU -
	 * Validate modifying searches/groups from the shared with <Security Group Name>
	 * by any other PAU user - RPMXCON-49885 Sprint 03
	 */
	@Test(groups = { "regression" }, priority = 13)
	public void verifySharedNode() throws InterruptedException {
		String SearchNamePA = "Search1" + Utility.dynamicNameAppender();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49885 Saved Search");

		// create new searchgroup
		saveSearch.createNewSearchGrp(SearchNamePA);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNodePA = saveSearch.getSavedSearchNewNode().getText();
		System.out.println(newNodePA);
		saveSearch.shareSavedNodePA(newNodePA);
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNodePA, "", false, "", false);

		// impersonate As RMU
		base.impersonatePAtoRMU();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNodePA, "", false, "", false);
		saveSearch.deleteSharedNode(newNodePA);

		// impersonate As RMU
		driver.waitForPageToBeReady();
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNodePA, "", false, "", false);
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/13/21 Modified date:N/A Modified by: Description :
	 *         As a RM user login, to check the back button functionality when user
	 *         selected any saved search query from saved search page and apply edit
	 *         on that, and will modify the search query and execute the same after
	 *         modification - RPMXCON-47381 Sprint 03
	 * @Stabilization - done
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void checkBackButton() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47381 - Saved Search Sprint 03");
		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Modify Search via Saved Search
		saveSearch.savedSearchEdit(searchName1);
		session.modifySearchTextArea(1, Input.searchString2, Input.searchString1, "Save");
		base.stepInfo("Modified search query, & executed the same  ");

		// Navigate back and Verify landing page
		driver.Navigate().back();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "SavedSearch/SavedSearches", currentUrl);
		base.stepInfo("Navigated back : " + currentUrl);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/15/21 Modified date:9/16/21 Modified by: Raghuram
	 *         A Description : To verify, As an Reviewer user login, When I will
	 *         share a search from saved search, In share search pop up window I
	 *         will be able to search all the Users belong to same project
	 *         RPMXCON-47459- Sprint 03
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void shareSearchPopup() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47459 - Saved Search Sprint 03");
		// Login via Reviewer
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Choose search
		saveSearch.savedSearch_Searchandclick(searchName1);
		saveSearch.sharePoupVerificationOfAvailableSharedSG("NotClicked", "Close");

		login.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				login.logout();
			} catch (Exception e) {
//						 TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			driver.scrollPageToTop();

			login.closeBrowser();
		} finally {
			login.clearBrowserCache();
//			LoginPage.clearBrowserCache();
		}
	}
}
