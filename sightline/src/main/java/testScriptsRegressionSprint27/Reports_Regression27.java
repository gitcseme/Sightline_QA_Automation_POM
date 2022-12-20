package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReportsPage;
import pageFactory.ReviewerCountsReportPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Regression27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PROJECTS EXECUTED******");

	}

	@DataProvider(name = "PA & RMU")
	public Object[][] users() {
		Object[][] users = { 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.pa1userName, Input.pa1password }
				 };
		return users;
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56576
	 * @Description:To verify that if “Scrub export of special characters" option is
	 *                 toggled OFF in Export Report
	 **/
	@Test(description = "RPMXCON-56576", enabled = true, groups = { "regression" })
	public void verifyScrubExportSplChToggle() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		ReportsPage report = new ReportsPage(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56576");
		base.stepInfo("verify that if Scrub export of special characters option is toggled OFF in Export Report");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);

		base.stepInfo("Toggle Scrub Export of special characters to Off and run report");
		base.waitForElement(custom.getToggle_ScrubSpecChar());
		custom.getToggle_ScrubSpecChar().waitAndClick(10);
		String fileName = custom.runReportandVerifyFileDownloaded();
		String actualValue = custom.csvfileVerification("", fileName);
		base.stepInfo(actualValue);
		System.out.println(actualValue);
		SoftAssert assets = new SoftAssert();
		assets.assertTrue(actualValue.contains("_"));
		assets.assertAll();
		base.passedStep("Special characters are not be replaced.");
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56574
	 * @Description: To verify that any row/pair may be deleted by clicking the red
	 *               "x" icon on export report->Scrub export of special characters
	 *               pop up
	 **/
	@Test(description = "RPMXCON-56574", enabled = true, groups = { "regression" })
	public void verifyRowDeletedInSCrubReport() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56574");
		base.stepInfo(
				"To verify that any row/pair may be deleted by clicking the red \"x\" icon on export report->Scrub export of special characters pop up");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);

		base.stepInfo("Toggle Scrub Export of special characters and delete existing character set");
		base.waitForElement(custom.getToggle_ScrubSpecChar());
		custom.getToggle_ScrubSpecChar().waitAndClick(10);
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		base.waitForElement(custom.getRedXIcon());
		custom.getRedXIcon().waitAndClick(10);
		base.passedStep("Deleted  existing replacement character set on clicking on 'x' icon");

	}

	

	/**
	 * @author NA Testcase No:RPMXCON-56721
	 * @Description: Executing the tally report with EmailAuthorName filters
	 *               selected
	 **/
	@Test(description = "RPMXCON-56721", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void validateTallyReportWithEA(String username, String password) throws Exception {
		CommunicationExplorerPage commExpl = new CommunicationExplorerPage(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();

		base.stepInfo("RPMXCON - 56721");
		base.stepInfo("To Executing the tally report with EmailAuthorName filters selected");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As : " + username);

		commExpl.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		commExpl.analyzeInTallyAction();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		float beforeEmailAuthor = System.currentTimeMillis();
		base.waitForElementCollection(tally.getAllValueinEmailAuthorFilter());
		float afterEmailAuthor = System.currentTimeMillis();
		base.waitForElement(tally.getCloseByEAFilterPopUp());
		tally.getCloseByEAFilterPopUp().waitAndClick(5);
		float totalSecEmailAuthor = afterEmailAuthor - beforeEmailAuthor;
		System.out.println(totalSecEmailAuthor);
		asserts.assertTrue(totalSecEmailAuthor < 4000);
		asserts.assertAll();

		driver.waitForPageToBeReady();
		tally.applyFilterToTallyByIndex(Input.MetaDataEAName, "exclude", 1);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.getTally_btnTallyApply());
		base.waitTillElemetToBeClickable(tally.getTally_btnTallyApply());
		tally.getTally_btnTallyApply().Click();
		tally.verifyTallyChart();

		base.passedStep("Executed - the tally report with EmailAuthorName filters selected");
		loginPage.logout();
	}


	/**
	 * @author NA Testcase No:RPMXCON-56716
	 * @Description: Validate Navigate to Tally report from ABM Report
	 **/
	@Test(description = "RPMXCON-56716", enabled = true, groups = { "regression" })
	public void verifyNavigationTallyFromABM() throws Exception {
		ABMReportPage abmReportPage = new ABMReportPage(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();
		String SaveSaerchName = "ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		String expSource = "Selected Documents from AdvBatchMangementRpt";

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		base.stepInfo("Test case Id:RPMXCON-56716");
		base.stepInfo("Validate Navigate to Tally report from ABM Report");

		// Basic Search and Bulk Assign
		sessionsearch.basicContentSearch(Input.testData1);
		base.stepInfo("Search for text input completed");
		sessionsearch.verifyPureHitsCount();
		sessionsearch.saveSearch(SaveSaerchName);
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		assignmentsPage.assignmentCreation(assignmentName1, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		base.stepInfo(assignmentName1 + "  Assignment Created and distributed ");

		abmReportPage.validateRevListAndgenerateABM_Report(SaveSaerchName, assignmentName1, false, false);
		driver.waitForPageToBeReady();
		abmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);
		driver.waitForPageToBeReady();
		abmReportPage.performTallyResult();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.getTally_btnTallyAll());
		tally.getTally_btnTallyAll().waitAndClick(5);
		driver.waitForPageToBeReady();
		tally.runBgReportandVerifyFileDownloaded();

		base.waitForElement(tally.getTally_SelectedSource());
		String selectedSource = tally.getTally_SelectedSource().getText();
		asserts.assertTrue(selectedSource.contains(expSource));
		asserts.assertAll();
		base.passedStep("Verified - Navigate to Tally report from ABM Report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56947
	 * @Description: To Validate Distributed document count for Review Count report
	 *               by hourly (Project Admin login)
	 **/
	@Test(description = "RPMXCON-56947", enabled = true, groups = { "regression" })
	public void verifyDisCountFrRevCountRep() throws Exception {
		ReviewerCountsReportPage count = new ReviewerCountsReportPage(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch session = new SessionSearch(driver);

		String assignment1 = "Assignment" + Utility.dynamicNameAppender();
		String[] reviewers = { Input.rev1FullName, Input.rmu1FullName };

		base.stepInfo("RPMXCON-56947");
		base.stepInfo("To Validate Distributed document count for Review Count report by hourly (Project Admin login)");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1FullName);

		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignment1, Input.codeFormName);
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.searchString1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignment1);
		assign.editAssignment(assignment1);
		driver.waitForPageToBeReady();
		assign.add2ReviewerAndDistribute();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As : " + Input.pa1FullName);
		count.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		count.generateReportMultiRev(assignment1, reviewers);
		driver.waitForPageToBeReady();
		String completedDocsInReportPage = count.verifyColumnValueDisplay(count.getTableHeaders(), "Distributed Docs");
		System.out.println(completedDocsInReportPage);
		asserts.assertTrue(completedDocsInReportPage.equals("100"));
		asserts.assertAll();
		base.passedStep(
				"Validated - Distributed document count for Review Count report by hourly (Project Admin login)");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56948
	 * @Description: Validate Distributed document count for Review Count report by
	 *               hourly (RMU login)
	 **/
	@Test(description = "RPMXCON-56948", enabled = true, groups = { "regression" })
	public void verifyDisCountFrRevCountRepRMULgin() throws Exception {
		ReviewerCountsReportPage count = new ReviewerCountsReportPage(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch session = new SessionSearch(driver);

		String assignment1 = "Assignment" + Utility.dynamicNameAppender();
		String[] reviewers = { Input.rev1FullName, Input.rmu1FullName };

		base.stepInfo("RPMXCON-56948");
		base.stepInfo("Validate Distributed document count for Review Count report by hourly (RMU login)");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1FullName);

		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignment1, Input.codeFormName);
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.searchString1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignment1);
		assign.editAssignment(assignment1);
		driver.waitForPageToBeReady();
		assign.add2ReviewerAndDistribute();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		count.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		count.generateReportMultiRev(assignment1, reviewers);
		driver.waitForPageToBeReady();
		String completedDocsInReportPage = count.verifyColumnValueDisplay(count.getTableHeaders(), "Distributed Docs");
		System.out.println(completedDocsInReportPage);
		asserts.assertTrue(completedDocsInReportPage.equals("100"));
		asserts.assertAll();
		base.passedStep("Validated - Distributed document count for Review Count report by hourly (RMU login)");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56581
	 * @Description:To verify export report if ‘Export Object Name’ and “Scrub
	 *                 export of special characters" option is toggled ‘OFF’
	 **/
	@Test(description = "RPMXCON-56581", enabled = true, groups = { "regression" })
	public void verifyScrubExportSplChAndExportObjNameToggle() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56581");
		base.stepInfo(
				"To verify export report if ‘Export Object Name’ and “Scrub export of special characters\" option is toggled ‘OFF’");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);

		base.waitForElement(custom.getToggle_ScrubSpecChar());
		custom.getToggle_ScrubSpecChar().waitAndClick(10);
		base.stepInfo("Toggle Scrub Export of special characters is turned Off ");

		base.waitForElement(custom.getToggle_ObjectName());
		custom.getToggle_ObjectName().waitAndClick(10);
		base.stepInfo("Toggle Export object name is turned Off ");

		custom.verifyScrubToggleAddDeleteAndModify();
		base.stepInfo("verified scrub Toggle Add/Delete/Modify ");

		String fileName = custom.runReportandVerifyFileDownloaded();
		String actualValue = custom.csvfileVerification("", fileName);
		base.stepInfo(actualValue);
		System.out.println(actualValue);
		SoftAssert assets = new SoftAssert();
		assets.assertTrue(actualValue.contains("_"));
		assets.assertAll();
		base.passedStep("Report generated successfully");
	}

	/**
	 * @author NA TESTCASE No:RPMXCON-56773
	 * @Description: Verified - Conceptual Report generation based on Clusters
	 **/
	@Test(description = "RPMXCON-56773", enabled = true, groups = { "regression" })
	public void verifyConceptualReportBasedOnCLuster() throws Exception {
		ConceptExplorerPage concept = new ConceptExplorerPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);

		String sourceToSelect = "Security Groups";
		String folderName = "Folder" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-56773");
		base.stepInfo("Verified - Conceptual Report generation based on Clusters");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As  : " + Input.pa1userName);

		concept.navigateToConceptExplorerPage();
		driver.waitForPageToBeReady();
		concept.clickSelectSources();
		concept.selectSGsource(sourceToSelect, Input.securityGroup);
		driver.waitForPageToBeReady();
		concept.filterAction(Input.custodianName_Andrew, Input.metaDataName, null, true);
		concept.applyFilter("Yes", 10);
		driver.waitForPageToBeReady();
		base.waitForElementCollection(concept.getDataToAddInCart());
		asserts.assertTrue(concept.getDataToAddInCart().isElementAvailable(5));
		asserts.assertAll();
		base.stepInfo("Clusters are displayed at the bottom of the page ");
		String docCount = concept.addMultipleTilesToCart(1);
		driver.waitForPageToBeReady();
		concept.performActions("Bulk Folder");
		String bulkFolderCount = sessionSearch.BulkActions_Folder_returnCount(folderName);
		base.textCompareEquals(docCount, bulkFolderCount, "Folder Document count matches as expected",
				"Mis-match in document count");
		base.stepInfo("Bulk Folder Action done successfully");
		base.passedStep("To Verify Conceptual Report generation based on Clusters");
		loginPage.logout();
	}
}