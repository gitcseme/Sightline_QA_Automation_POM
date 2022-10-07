package testScriptsRegressionSprint22;

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
import pageFactory.AnnotationLayer;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression_22 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that batch redaction history should be security group
	 *              specific and entries should be vertically centered
	 *              [RPMXCON-53337]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53337", enabled = true, groups = { "regression" })
	public void verifybatchRedactionHistory() throws Exception {
		String otherSGR1 = "Search1" + Utility.dynamicNameAppender();
		String defSGR1 = "Search2" + Utility.dynamicNameAppender();
		String otherSGR2 = "Search3" + Utility.dynamicNameAppender();
		String ReadctionTag = "Redaction_Tag" + Utility.dynamicNameAppender();
		String Annotation = "AnnotationLayer" + Utility.dynamicNameAppender();

		String securityGrp = "Security" + Utility.dynamicNameAppender();
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement user = new UserManagement(driver);
		RedactionPage redact = new RedactionPage(driver);
		AnnotationLayer layer = new AnnotationLayer(driver);

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Craete Redaction Tag & annotation layer
		redact.navigateToRedactionsPageURL();
		redact.manageRedactionTagsPage(ReadctionTag);
		layer.navigateToAnnotationLayerPage();
		layer.AddAnnotation(Annotation);

		// Add SG & assign Redaction tag & assign annoattion layer
		sg.navigateToSecurityGropusPageURL();
		sg.AddSecurityGroup(securityGrp);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.waitTime(2);	
		sg.selectSecurityGroup(securityGrp);
		driver.waitForPageToBeReady();
		sg.assignRedactionTagtoSG(ReadctionTag);
		sg.assignAnnotationToSG(Annotation);

		// assign access to users
		user.navigateToUsersPAge();
		user.assignAccessToSecurityGroups(securityGrp, Input.rmu1userName);
		base.waitTime(2);
		user.assignAccessToSecurityGroups(securityGrp, Input.rmu2userName);

		// bulk release doc's to SG
		session.basicContentSearch(Input.testData1);
		session.bulkRelease(securityGrp);
		
		// logout
		login.logout();

		// Login as a User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");
		base.selectsecuritygroup(securityGrp);

		base.stepInfo("Test case Id:RPMXCON-53337 Batch Redaction");
		base.stepInfo(
				"Verify that batch redaction history should be security group specific and entries should be vertically centered");

		// Configure Audio Search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(otherSGR1);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(otherSGR1, true);
		driver.waitForPageToBeReady();
		batch.viewAnalysisAndBatchReport(ReadctionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(otherSGR1);

		// Default SG
		base.selectsecuritygroup(Input.securityGroup);
		session.basicContentSearch(Input.testData1);
		session.saveSearch(defSGR1);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(defSGR1, true);
		driver.waitForPageToBeReady();
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(defSGR1);

		// logout
		login.logout();

		// Login as a RMU2
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Logged In As : RMU2");
		base.selectsecuritygroup(securityGrp);

		// Configure Audio Search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(otherSGR2);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(otherSGR2, true);
		driver.waitForPageToBeReady();
		batch.viewAnalysisAndBatchReport(ReadctionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(otherSGR2);

		// verify history contains only Other Security grup batch redactions of all
		// users
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(batch.getColorStatusMsg(otherSGR2), otherSGR2 + " Of Other SG of RMU2");
		base.ValidateElement_Presence(batch.getColorStatusMsg(otherSGR1), otherSGR1 + " Of Other SG Of RMU1");
		driver.waitForPageToBeReady();
		base.ValidateElement_Absence(batch.getColorStatusMsg(defSGR1), defSGR1 + " of Default SG is not present ");

		base.passedStep(
				"Batch Redaction history display as per logged in users security group i.e here selected is Default SG");
		base.passedStep("Batch redaction performed by other users in same Sg is displayed");

		// verify allignment of enteries
		batch.verifyAllignmentOfHistoryEnteries();

		base.selectsecuritygroup(Input.securityGroup);

		// logout
		login.logout();

		// Delete Security Group
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		sg.deleteSecurityGroups(securityGrp);

		// logout
		login.logout();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		batch = new BatchRedactionPage(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//							login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
