package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentReviewProgressReport;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.CommunicationExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReportsPage;
import pageFactory.ReviewerCountsReportPage;
import pageFactory.ReviewerReviewProgressReport;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Regression28 {

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
		System.out.println("******TEST CASES FOR REPORTS EXECUTED******");

	}

	/**
	 * @author NA Testcase No:RPMXCON-56579
	 * @Description:To verify that custom export repot should be shared to user or
	 *                 by email with the \"Scrub export of special characters\"
	 *                 settings
	 **/
	@Test(description = "RPMXCON-56579", enabled = true, groups = { "regression" })
	public void verifyScrubExportSplChShareByUser() throws Exception {
		SoftAssert asserts = new SoftAssert();
		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };

		base.stepInfo("RPMXCON - 56579");
		base.stepInfo(
				"To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Delete
		base.waitForElement(custom.getSpecialCharDD(0));
		String befSpcOptDel = custom.getSpecialCharDD(0).selectFromDropdown().getFirstSelectedOption().getText();
		String befReplOptDel = custom.getReplaceCharDD(0).selectFromDropdown().getFirstSelectedOption().getText();
		System.out.println(befSpcOptDel);
		System.out.println(befReplOptDel);
		base.waitForElement(custom.getRedXIcon(0));
		custom.getRedXIcon(0).waitAndClick(5);
		asserts.assertFalse(custom.getSpecialCharDD(0).isElementAvailable(5));
		asserts.assertFalse(custom.getReplaceCharDD(0).isElementAvailable(5));
		asserts.assertAll();
		base.waitForElement(custom.getCloseBtn_ScrubPopup());
		custom.getCloseBtn_ScrubPopup().waitAndClick(5);

		// Newly Added
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(custom.getAddNewBtn_ScrubLink());
		custom.getAddNewBtn_ScrubLink().ScrollTo();
		custom.getAddNewBtn_ScrubLink().waitAndClick(10);
		base.waitForElement(custom.getSpecialCharDDLast());
		String expSpcOptNew = custom.getSpecialCharDDLast().selectFromDropdown().getFirstSelectedOption().getText();
		String expRplcOptNew = custom.getReplaceCharLast().selectFromDropdown().getFirstSelectedOption().getText();
		System.out.println(expSpcOptNew);
		System.out.println(expRplcOptNew);
		base.waitForElement(custom.getCloseBtn_ScrubPopup());
		custom.getCloseBtn_ScrubPopup().waitAndClick(5);

		// Modify
		driver.waitForPageToBeReady();
		String expSpcOptMod = custom.selectSpecialORReplaceCharByIndex("Special Character", 2, 3);
		String expRplcOptMod = custom.selectSpecialORReplaceCharByIndex("Replacement Character", 2, 3);
		System.out.println(expSpcOptMod);
		System.out.println(expRplcOptMod);

		custom.runReportandVerifyFileDownloaded();
		base.waitForElement(base.getBackgroundTask_Button());
		base.getBackgroundTask_Button().waitAndClick(10);
		driver.waitForPageToBeReady();
		custom.performSharingAction(Input.pa2FullName, Input.pa2userName);
		base.verifyMegaPhoneIconAndBackgroundTasks(true, false);
		driver.waitForPageToBeReady();
		String Id = custom.SharedIDFromNotification(1);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		driver.waitForPageToBeReady();
		base.verifyMegaPhoneIconAndBackgroundTasks(true, false);
		custom.openSharedNotification(Id);
		driver.waitForPageToBeReady();
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);

		base.waitForElement(custom.getSpecialCharDDLast());
		String actSpcOptNew = custom.getSpecialCharDDLast().selectFromDropdown().getFirstSelectedOption().getText();
		String actRplcOptNew = custom.getReplaceCharLast().selectFromDropdown().getFirstSelectedOption().getText();
		asserts.assertEquals(expSpcOptNew, actSpcOptNew);
		asserts.assertEquals(expRplcOptNew, actRplcOptNew);
		asserts.assertAll();

		driver.waitForPageToBeReady();
		String actSpcOptMod = custom.getSpecialCharDD(1).selectFromDropdown().getFirstSelectedOption().getText();
		String actRplcOptMod = custom.getReplaceCharDD(1).selectFromDropdown().getFirstSelectedOption().getText();
		asserts.assertEquals(expSpcOptMod, actSpcOptMod);
		asserts.assertEquals(expRplcOptMod, actRplcOptMod);
		asserts.assertAll();
		base.passedStep(
				"To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56702
	 * @Description:Verify Advanced Batch Management Report layout
	 **/
	@Test(description = "RPMXCON-56702", enabled = true, groups = { "regression" })
	public void verifyABMreportLayout() throws InterruptedException, Exception {
		ABMReportPage abmReport = new ABMReportPage(driver);
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();

		String searchName = "Search" + Utility.dynamicNameAppender();
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String expPageTitle = "Advanced Batch Management";

		base.stepInfo("RPMXCON-56702");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Verify Advanced Batch Management Report layout");

//		create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.bulkAssign();

//		create Assignment and disturbute docs
		assign.assignmentCreation(assignmentName, Input.codeFormName);
		assign.add2ReviewerAndDistribute();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(abmReport.getReport_ABM());
		abmReport.getReport_ABM().waitAndClick(5);

//		Verify Report Title
		base.waitForElement(abmReport.getPageTitle());
		String pageTitle = abmReport.getPageTitle().getText().trim();
		asserts.assertEquals(expPageTitle, pageTitle);
		asserts.assertTrue(abmReport.getHelpButton().isElementAvailable(6));
		asserts.assertAll();

//		Verify Report Options
		base.waitForElement(abmReport.getManageBatchAt());
		asserts.assertTrue(abmReport.getManageBatchAt().isElementAvailable(5));
		asserts.assertAll();

//		Verify Report Source
		base.waitForElement(abmReport.getABM_SelectSource());
		abmReport.getABM_SelectSource().waitAndClick(5);
		base.waitForElement(abmReport.getABM_SearchButton());
		asserts.assertTrue(abmReport.getABM_SearchButton().isElementAvailable(5));
		asserts.assertAll();

//		Verify Other Features..
		abmReport.generateABM_Report(searchName, assignmentName, false);
		String ReportTime = abmReport.getDateTimeinReport().getText();
		String serverTime = base.getcurrentdateinUTC();
		System.out.println(ReportTime);
		System.out.println(serverTime);
		asserts.assertTrue(ReportTime.contains(serverTime));
		asserts.assertTrue(abmReport.getShareButton().isElementAvailable(5));
		asserts.assertAll();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.impersonatePAtoRMU();
		base.passedStep("Verified Advanced Batch Management Report layout");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56701
	 * @Description:To Verify Advanced Batch Management report option
	 **/
	@Test(description = "RPMXCON-56701", enabled = true, groups = { "regression" })
	public void verifyABMReportOption() throws InterruptedException, Exception {
		ABMReportPage abmReport = new ABMReportPage(driver);
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		AssignmentReviewProgressReport arpr = new AssignmentReviewProgressReport(driver);
		ReportsPage report = new ReportsPage(driver);
		SoftAssert aserts = new SoftAssert();

		String searchName = "Search" + Utility.dynamicNameAppender();
		String assignmentName1 = "Assignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp" + Utility.dynamicNameAppender();
		String reportABM = "ABMReport" + Utility.dynamicNameAppender();
		String reportARP = "ARPReport" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-56701");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("To Verify Advanced Batch Management report option");

		assign.navigateToAssignmentsPage();
		assign.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assign.selectAssignmentGroup(assignmentGrpName);
		assign.createAssignmentFromAssgnGroup(assignmentName2, Input.codeFormName);

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.bulkAssignExisting(assignmentName2);
		session.bulkAssign();

//		create Assignment and disturbute docs
		assign.assignmentCreation(assignmentName1, Input.codeFormName);
		assign.add2ReviewerAndDistribute();
		abmReport.generateABM_Report(searchName, assignmentName1, false);
		abmReport.SaveReport(reportABM);

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		arpr.navigateToAssgnmntReviewProgressReport();
		arpr.generateARPreport(assignmentGrpName, "1LR");
		arpr.SaveReport(reportARP);

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElementCollection(report.getCustomReportList());
		List<String> reportList = base.availableListofElements(report.getCustomReportList());
		int indexOfABM = reportList.indexOf(reportABM);
		System.out.println(indexOfABM);
		int indexOfARP = reportList.indexOf(reportARP);
		System.out.println(indexOfARP);
		aserts.assertTrue(indexOfABM < indexOfARP);
		aserts.assertTrue(report.getReportReviewCustom(reportABM).isElementAvailable(5));
		aserts.assertAll();

		base.passedStep("Verify Advanced Batch Management report option");
		loginPage.logout();
	}

	@DataProvider(name = "PA & RMU")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	/**
	 * @author NA Testcase No:RPMXCON-56255
	 * @Description:To verify that User is able to select Date and Time from date
	 *                 selection criteria on Reviewer Counts by Day/Hour Report
	 **/
	@Test(description = "RPMXCON-56255", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyUserAbleToSelectDateTime(String username, String password) throws Exception {
		ReviewerCountsReportPage report = new ReviewerCountsReportPage(driver);
		SoftAssert asserts = new SoftAssert();

		base.stepInfo("RPMXCON - 56255");
		loginPage.loginToSightLine(username, password);
		base.stepInfo(
				"To verify that User is able to select Date and Time from date selection criteria on Reviewer Counts by Day/Hour Report");
		base.stepInfo("Logged in As : " + username);
		report.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		base.waitForElement(report.getHourlyRadio());
		report.getHourlyRadio().waitAndClick(5);
		base.waitForElement(report.getExpandDateRange());
		report.getExpandDateRange().waitAndClick(5);
		driver.waitForPageToBeReady();
		
		String actFromDate = report.getSelectFromDateTime().Value();
		base.stepInfo("By Default Date and Time From Range : " + actFromDate);
		String actToDate = report.getSelectToDateTime().Value();
		base.stepInfo("By Default Date and Time To Range : " + actToDate);

		String expDateTime = base.getcurrentdateinUTC();
		report.selectFromDate(report.getSelectFromDateTime(), "From");
		report.selectFromDate(report.getSelectToDateTime(), "To");

		String afterFrmChange = report.getSelectFromDateTime().Value();
		base.stepInfo("After Change From Range : " + afterFrmChange);
		String afterToChange = report.getSelectToDateTime().Value();
		base.stepInfo("After Change To Range : " + afterFrmChange);
		asserts.assertFalse(afterFrmChange.contains(expDateTime));
		asserts.assertFalse(afterToChange.contains(expDateTime));
		asserts.assertAll();
		base.passedStep("User Can Select Date Time From and To Range");

		asserts.assertTrue(actFromDate.contains(expDateTime));
		asserts.assertTrue(actToDate.contains(expDateTime));
		asserts.assertAll();
		base.passedStep("Date Selection criteria is displayed with current date and time by default selected.");
		base.passedStep("Verified - that User is able to select Date and Time from date selection "
				+ "criteria on Reviewer Counts by Day/Hour Report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56568
	 * @Description:To verify that 'Docs Distributed' should appear as 'My Batch Docs' in 'reviewer progress report'
	 **/
	@Test(description = "RPMXCON-56568", enabled = true, groups = { "regression" })
	public void verifyDocDistriMyBatchDocs() throws Exception {
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ReviewerReviewProgressReport rrpr = new ReviewerReviewProgressReport(driver);
		UserManagement user = new UserManagement(driver);
		
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp" + Utility.dynamicNameAppender();
		String expMsg = "Total no of Docs distributed should be displayed on 'My Batch Docs' column";
		String failMsg = "Total no of Docs distributed should be displayed on 'My Batch Docs' column";

		base.stepInfo("RPMXCON - 56568");
		base.stepInfo(
				"To verify that 'Docs Distributed' should appear as 'My Batch Docs' in 'reviewer progress report'");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1userName);

		user.navigateToUsersPAge();
		driver.waitForPageToBeReady();
        user.passingUserName(Input.rev1userName);
        user.applyFilter();
        String firstName = user.getTableData("FIRST NAME", 1);
        String lastName = user.getTableData("LAST NAME", 1);
        String userName = firstName + " " + lastName;
        
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignment.selectAssignmentGroup(assignmentGrpName);
		assignment.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// search to Assignment creation
		sessionSearch.navigateToSessionSearchPageURL();
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssignmentInAssignGroup(assignmentGrpName, assignmentName);
		assignment.addReviewerInManageReviewerTab();
		assignment.distributeGivenDocCountToReviewers(String.valueOf(purehit));

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		rrpr.navigateToReviewerReviewProgressReport();
		driver.waitForPageToBeReady();
		rrpr.generateRRPreport(assignmentGrpName, userName);
		driver.waitForPageToBeReady();
		String actMyBatchDogs = rrpr.getColoumnValue(rrpr.reviewerColumnNameHeader(), "My Batch Docs", assignmentName);
		base.textCompareEquals(actMyBatchDogs, String.valueOf(6), expMsg, failMsg);
		base.passedStep(
				"Verified - that 'Docs Distributed' should appear as 'My Batch Docs' in 'reviewer progress report'");
		loginPage.logout();
	}
	/**
	 * @author NA Testcase No:RPMXCON-56722
	 * @Description: Executing the tally report with CustodianName & EmailAuthorName
	 *               filters selected
	 **/
	@Test(description = "RPMXCON-56722", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void validateTallyReportWithCNandEA(String username, String password) throws Exception {
		CommunicationExplorerPage commExpl = new CommunicationExplorerPage(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();

		base.stepInfo("RPMXCON-56722");
		base.stepInfo("To Executing the tally report with CustodianName & EmailAuthorName filters selected");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As : " + username);

		commExpl.navigateToCommunicationExpPage();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		commExpl.analyzeInTallyAction();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.metaDataName));
		tally.metaDataFilterForTallyBy(Input.metaDataName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		float beforeCustName = System.currentTimeMillis();
		tally.FilterInputTextBoxTallyBy().waitAndClick(10);
		base.waitForElementCollection(tally.getAllValueinCustNameFilter());
		float afterCustName = System.currentTimeMillis();
		base.waitForElement(tally.getCloseByCNFilterPopUp());
		tally.getCloseByCNFilterPopUp().waitAndClick(5);
		float totalSecCustName = afterCustName - beforeCustName;
		System.out.println(totalSecCustName);
		asserts.assertTrue(totalSecCustName < 4000);
		driver.waitForPageToBeReady();
		tally.applyFilterToTallyByIndex(Input.metaDataName, "exclude", 1);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).ScrollTo();
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		float beforeEmailAuthor = System.currentTimeMillis();
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		base.waitForElementCollection(tally.getAllValueinEmailAuthorFilter());
		float afterEmailAuthor = System.currentTimeMillis();
		base.waitForElement(tally.getCloseByEAFilterPopUp());
		tally.getCloseByEAFilterPopUp().waitAndClick(5);
		float totalSecEmailAuthor = afterEmailAuthor - beforeEmailAuthor;
		asserts.assertTrue(totalSecEmailAuthor < 4000);
		driver.waitForPageToBeReady();
		tally.applyFilterToTallyByIndex(Input.MetaDataEAName, "exclude", 1);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.getTally_btnTallyApply());
		base.waitTillElemetToBeClickable(tally.getTally_btnTallyApply());
		tally.getTally_btnTallyApply().waitAndClick(5);;
		if(tally.getPopupYesBtn().isElementAvailable(30)) {
			tally.getPopupYesBtn().waitAndClick(5);
		}
		tally.verifyTallyChart();
		base.passedStep("Executed - the tally report with CustodianName & EmailAuthorName filters selected");
		loginPage.logout();
	}
}
