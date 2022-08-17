package sightline.reports;

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
import pageFactory.AssignmentReviewProgressReport;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReviewerReviewProgressReport;
import pageFactory.SessionSearch;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AssignmentReviewProgressReport2_1 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	UserReviewActivityReport userActivityRptPg;
	AssignmentsPage assgnmntPg;
	SessionSearch sessionSearch;
	DocViewPage docview;
	AssignmentReviewProgressReport arpr;
	ReviewerReviewProgressReport rrpr;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@Test(description = "RPMXCON-56466", groups = {"regression" },enabled = true)
	public void verifyProgressReportIsPresentInAssignmentsPage() throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56466");
		bc.stepInfo("To verify that Progress Report option is provided on Manage Assignment->Assignment Group Page.");
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assgnmntPg = new AssignmentsPage(driver);
		assgnmntPg.verifyProgressReportIsPresent();		
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that 'Total Audio Docs Completed' count should be display correct 
	 *               in 'Review Progress Report'/'Assignment Progress Report'. RPMXCON-56540
	 */
	@Test(description = "RPMXCON-56540", enabled = true, groups = { "regression" })
	public void validateTotalAudioDocsInARPRandRRPR() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56540"); 
		bc.stepInfo("To verify that 'Total Audio Docs Completed' count should be display correct in 'Review Progress Report'/'Assignment Progress Report'"); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		arpr = new AssignmentReviewProgressReport(driver);
		rrpr = new ReviewerReviewProgressReport(driver);
		String assign1 =  "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp"+Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		assgnmntPg.createAssignmentFromAssgnGroup(assign1, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.docAudioId1);	
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		bc.waitForElement(docview.getAudioDocEndDuration());
		String durationForFirstDoc = docview.getAudioDocEndDuration().getText();
		driver.Navigate().back();
		sessionSearch.bulkAssignExisting(assign1);
		bc.selectproject();		
		sessionSearch.basicContentSearch(Input.docAudioId2);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		bc.waitForElement(docview.getAudioDocEndDuration());
		String durationForSecondDoc = docview.getAudioDocEndDuration().getText();
		driver.Navigate().back();
		sessionSearch.bulkAssignExisting(assign1);		
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		Boolean status = assgnmntPg.getSelectAssignment(assign1).isElementAvailable(5);
		if (status == true) {
			driver.scrollingToElementofAPage(assgnmntPg.getSelectAssignment(assign1));
			if (!assgnmntPg.getSelectAssignmentHighlightCheck(assign1).isElementAvailable(5)) {
				assgnmntPg.getSelectAssignment(assign1).waitAndClick(5);
			}
			driver.scrollPageToTop();
			assgnmntPg.getAssignmentActionDropdown().waitAndClick(3);
			bc.waitForElement(assgnmntPg.getAssignmentAction_EditAssignment());
			assgnmntPg.getAssignmentAction_EditAssignment().waitAndClick(3);
		}
		assgnmntPg.assignmentDistributingToReviewer();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		assgnmntPg.SelectAssignmentByReviewer(assign1);
		assgnmntPg.completeAllDocsByReviewer(assign1);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String expectedTotalTimeDuration = arpr.addTwoTimeDuration(durationForFirstDoc, durationForSecondDoc);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		arpr.navigateToAssgnmntReviewProgressReport();
		arpr.generateARPreport(assignmentGrpName,"1LR" );
		driver.waitForPageToBeReady();
		String completedDocsInARP=arpr.getColoumnValue(arpr.agnmtColumnNameHeader(), "Total Audio Docs Completed",assign1);
		String actualTotalAudioHoursInARP=arpr.getColoumnValue(arpr.agnmtColumnNameHeader(), "Total Audio Hours Completed",assign1);
		softAssertion.assertEquals(completedDocsInARP, "2");
		softAssertion.assertEquals(actualTotalAudioHoursInARP, expectedTotalTimeDuration);
		softAssertion.assertAll();
		bc.passedStep("Total completes docs are "+completedDocsInARP+" displayed in report as expected");
		bc.passedStep("Total audio hours completed are "+actualTotalAudioHoursInARP+" displayed in report as expected");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		rrpr.navigateToReviewerReviewProgressReport();
		rrpr.generateRRPreport(assignmentGrpName, Input.rev1FullName);
		driver.waitForPageToBeReady();
		String completedDocsInRRP=rrpr.getColoumnValue(rrpr.reviewerColumnNameHeader(), "Total Audio Docs Completed",assign1);
		String actualTotalAudioHoursInRRP=rrpr.getColoumnValue(rrpr.reviewerColumnNameHeader(), "Total Audio Hours Completed",assign1);
		softAssertion.assertEquals(completedDocsInRRP, "2");
		softAssertion.assertEquals(actualTotalAudioHoursInRRP, expectedTotalTimeDuration);
		softAssertion.assertAll();
		bc.passedStep("Total completes docs displayed in report as expected");
		bc.passedStep("Total audio hours completed are "+actualTotalAudioHoursInRRP+" displayed in report as expected");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assign1);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
		// logout
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To Verify RMU can able to generate 'Review Progress Assignment Report' 
	 *               using following Options \"Assignment\",Assignment Level,Assigment Group. RPMXCON-48701
	 */
	@Test(description = "RPMXCON-48701", enabled = true, groups = { "regression" })
	public void verifyRMUuserAbleToGenerateReport() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48701"); 
		bc.stepInfo("To Verify RMU can able to generate 'Review Progress Assignment Report' using following Options \"Assignment\",Assignment Level,Assigment Group."); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		arpr = new AssignmentReviewProgressReport(driver);
		rrpr = new ReviewerReviewProgressReport(driver);		
		String assignmentName =  "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp"+Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		assgnmntPg.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		arpr.navigateToAssgnmntReviewProgressReport();
		arpr.applyChanges();
		bc.VerifyErrorMessage("Please select assignment group");
		bc.passedStep("Without selecting assignment group and assignment level error message getting successfully as expected");
		arpr.generateARPreport(assignmentGrpName, "1LR");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assignmentName);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
		lp.logout();		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that in reviewer progress report,\"My Batch Docs Completed By Me\" column should contain the number of documents 
	 *               that were distributed and completed by that reviewer. RPMXCON-48769
	 */
	@Test(description = "RPMXCON-48769", enabled = true, groups = { "regression" })
	public void verifyMyBatchDocsCompletedByMeInRRP() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48769"); 
		bc.stepInfo("To verify that in reviewer progress report,\"My Batch Docs Completed By Me\" column should contain the number of documents that were distributed and completed by that reviewer."); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		arpr = new AssignmentReviewProgressReport(driver);
		rrpr = new ReviewerReviewProgressReport(driver);		
		String assignmentName =  "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp"+Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		assgnmntPg.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		Boolean status = assgnmntPg.getSelectAssignment(assignmentName).isElementAvailable(5);
		if (status == true) {
			driver.scrollingToElementofAPage(assgnmntPg.getSelectAssignment(assignmentName));
			if (!assgnmntPg.getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
				assgnmntPg.getSelectAssignment(assignmentName).waitAndClick(5);
			}
			driver.scrollPageToTop();
			assgnmntPg.getAssignmentActionDropdown().waitAndClick(3);
			bc.waitForElement(assgnmntPg.getAssignmentAction_EditAssignment());
			assgnmntPg.getAssignmentAction_EditAssignment().waitAndClick(3);
		}
		assgnmntPg.assignmentDistributingToReviewer();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		assgnmntPg.SelectAssignmentByReviewer(assignmentName);
		assgnmntPg.completeAllDocsByReviewer(assignmentName);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		rrpr.navigateToReviewerReviewProgressReport();
		rrpr.generateRRPreport(assignmentGrpName, Input.rev1FullName);
		driver.waitForPageToBeReady();
		String batchDocsCompletedInRRP=rrpr.getColoumnValue(rrpr.reviewerColumnNameHeader(), "My Batch Docs Completed by Me",assignmentName);
		softAssertion.assertEquals(batchDocsCompletedInRRP, "16");
		softAssertion.assertAll();
		bc.passedStep("My Batch Docs Completed by Me are displayed in report as expected");
		bc.passedStep("Total docs completed are "+batchDocsCompletedInRRP+" displayed in report as expected");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assignmentName);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
		lp.logout();		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: Validate data on Assignment Review Progress report when Domain and Project Admin users are associated to Assignments of a project
	 */
	@Test(description = "RPMXCON-56945", enabled = true, groups = { "regression" })
	public void verifyARPRwithDaAndPaUsers() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56945"); 
		bc.stepInfo("Validate data on Assignment Review Progress report when Domain and Project Admin users are associated to Assignments of a project"); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		arpr = new AssignmentReviewProgressReport(driver);
		rrpr = new ReviewerReviewProgressReport(driver);		
		String assignmentName =  "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp"+Utility.dynamicNameAppender();
		System.out.println(assignmentName);
		System.out.println(assignmentGrpName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		assgnmntPg.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		Boolean status = assgnmntPg.getSelectAssignment(assignmentName).isElementAvailable(5);
		if (status == true) {
			driver.scrollingToElementofAPage(assgnmntPg.getSelectAssignment(assignmentName));
			if (!assgnmntPg.getSelectAssignmentHighlightCheck(assignmentName).isElementAvailable(5)) {
				assgnmntPg.getSelectAssignment(assignmentName).waitAndClick(5);
			}
			driver.scrollPageToTop();
			assgnmntPg.getAssignmentActionDropdown().waitAndClick(3);
			bc.waitForElement(assgnmntPg.getAssignmentAction_EditAssignment());
			assgnmntPg.getAssignmentAction_EditAssignment().waitAndClick(3);
		}
		assgnmntPg.addReviewerAndDistributeDaPa();
		bc.passedStep("Documents aredistributed to DA and PA user successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		arpr.navigateToAssgnmntReviewProgressReport();
		arpr.generateARPreport(assignmentGrpName, "1LR");
		driver.waitForPageToBeReady();
		String completedDocsInARP=arpr.getColoumnValue(arpr.agnmtColumnNameHeader(), "ASSIGNED DOCS",assignmentName);
		softAssertion.assertEquals(completedDocsInARP, "16");
		softAssertion.assertAll();
		bc.passedStep("Validated successfully assignment review progress report when Domain and Project Admin users are associated to assignments of a project");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assignmentName);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
		lp.logout();		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that 'Total Audio Distributed Hours' count should be display correct in 'Review Progress Report'
	 */
	@Test(description = "RPMXCON-56539", enabled = true, groups = { "regression" })
	public void verifyTotalAudioDistributedHoursInRRPR() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56539"); 
		bc.stepInfo("To verify that 'Total Audio Distributed Hours' count should be display correct in 'Review Progress Report'"); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		arpr = new AssignmentReviewProgressReport(driver);
		rrpr = new ReviewerReviewProgressReport(driver);
		String assign1 =  "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp"+Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		assgnmntPg.createAssignmentFromAssgnGroup(assign1, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.docAudioId1);	
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		bc.waitForElement(docview.getAudioDocEndDuration());
		String durationForFirstDoc = docview.getAudioDocEndDuration().getText();
		driver.Navigate().back();
		sessionSearch.bulkAssignExisting(assign1);
		bc.selectproject();		
		sessionSearch.basicContentSearch(Input.docAudioId2);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		bc.waitForElement(docview.getAudioDocEndDuration());
		String durationForSecondDoc = docview.getAudioDocEndDuration().getText();
		driver.Navigate().back();
		sessionSearch.bulkAssignExisting(assign1);		
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.selectAssignmentGroup(assignmentGrpName);
		Boolean status = assgnmntPg.getSelectAssignment(assign1).isElementAvailable(5);
		if (status == true) {
			driver.scrollingToElementofAPage(assgnmntPg.getSelectAssignment(assign1));
			if (!assgnmntPg.getSelectAssignmentHighlightCheck(assign1).isElementAvailable(5)) {
				assgnmntPg.getSelectAssignment(assign1).waitAndClick(5);
			}
			driver.scrollPageToTop();
			assgnmntPg.getAssignmentActionDropdown().waitAndClick(3);
			bc.waitForElement(assgnmntPg.getAssignmentAction_EditAssignment());
			assgnmntPg.getAssignmentAction_EditAssignment().waitAndClick(3);
		}
		assgnmntPg.assignmentDistributingToReviewer();
		bc.stepInfo("Audio docs distributed to user successfully");
		String expectedTotalTimeDuration = arpr.addTwoTimeDuration(durationForFirstDoc, durationForSecondDoc);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		rrpr.navigateToReviewerReviewProgressReport();
		rrpr.generateRRPreport(assignmentGrpName, Input.rev1FullName);
		driver.waitForPageToBeReady();
		String actualTotalAudioHoursInRRP=rrpr.getColoumnValue(rrpr.reviewerColumnNameHeader(), "Total Audio Hours Distributed",assign1);
		softAssertion.assertEquals(actualTotalAudioHoursInRRP, expectedTotalTimeDuration);
		bc.passedStep("Total audio hours distributed are "+actualTotalAudioHoursInRRP+" displayed in report as expected");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assign1);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
		softAssertion.assertAll();
		// logout
		lp.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		userActivityRptPg = new UserReviewActivityReport(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
