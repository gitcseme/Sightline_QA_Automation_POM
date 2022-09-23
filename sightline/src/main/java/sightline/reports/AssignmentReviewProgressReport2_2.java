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
import pageFactory.ReportsPage;
import pageFactory.ReviewerReviewProgressReport;
import pageFactory.SessionSearch;
import pageFactory.UserLoginActivityReport;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AssignmentReviewProgressReport2_2 {
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
	UserLoginActivityReport userLoginActivityRptPg;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To Verify User will be able to export data in an excel format from Review Progress by Assignment Report
	 */
	@Test(description = "RPMXCON-56228", enabled = true, groups = { "regression" })
	public void verifyExportDataARPR() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56228"); 
		bc.stepInfo("To Verify User will be able to export data in an excel format from Review Progress by Assignment Report"); 
		ReportsPage report = new ReportsPage(driver);
		SoftAssert sa = new SoftAssert();
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		userLoginActivityRptPg = new UserLoginActivityReport(driver);
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
		arpr.generateARPreport(assignmentGrpName, "1LR");
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully");
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");
		sa.assertAll();		
		bc.passedStep("User login activity report is downloaded successfully");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assignmentName);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
		lp.logout();		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To Verify User will be able to export data in an excel format from Review Progress by Reviewer Report
	 */
	@Test(description = "RPMXCON-56229", enabled = true, groups = { "regression" })
	public void verifyExportDataRRPR() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56229"); 
		bc.stepInfo("To Verify User will be able to export data in an excel format from Review Progress by Reviewer Report"); 
		ReportsPage report = new ReportsPage(driver);
		SoftAssert sa = new SoftAssert();
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		arpr = new AssignmentReviewProgressReport(driver);
		rrpr = new ReviewerReviewProgressReport(driver);
		userLoginActivityRptPg = new UserLoginActivityReport(driver);
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
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully");
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");
		sa.assertAll();		
		bc.passedStep("User login activity report is downloaded successfully");
		assgnmntPg.deleteAssignmentFromSingleAssgnGrp(assignmentGrpName, assignmentName);
		assgnmntPg.DeleteAssgnGroup(assignmentGrpName);
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
