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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentReviewProgressReport;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
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
	 * @Description:To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings
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
		base.stepInfo("To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings");
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
		base.passedStep("To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings");
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
		String assignmentGrpName = "AssignmentGrp"+Utility.dynamicNameAppender();
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
}
