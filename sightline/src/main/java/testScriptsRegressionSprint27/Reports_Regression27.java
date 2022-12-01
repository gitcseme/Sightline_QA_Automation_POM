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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.CommunicationExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReportsPage;
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
	 * @Description: To verify that any row/pair may be deleted by clicking the red "x" icon on export report->Scrub export of special characters pop up
	 **/
	@Test(description = "RPMXCON-56574", enabled = true, groups = { "regression" })
	public void verifyRowDeletedInSCrubReport() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56574");
		base.stepInfo("To verify that any row/pair may be deleted by clicking the red \"x\" icon on export report->Scrub export of special characters pop up");
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
		float beforeCustName = System.currentTimeMillis();
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.metaDataName));
		tally.metaDataFilterForTallyBy(Input.metaDataName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		base.waitForElementCollection(tally.getAllValueinCustNameFilter());
		float afterCustName = System.currentTimeMillis();
		float totalSecCustName = afterCustName - beforeCustName;
		asserts.assertTrue(totalSecCustName < 4000);
		asserts.assertAll();

		tally.applyFilterToTallyBy(Input.metaDataName, "exclude", Input.custodianName_Andrew);

		driver.waitForPageToBeReady();
		float beforeEmailAuthor = System.currentTimeMillis();
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		base.waitForElementCollection(tally.getAllValueinEmailAuthorFilter());
		float afterEmailAuthor = System.currentTimeMillis();
		float totalSecEmailAuthor = afterEmailAuthor - beforeEmailAuthor;
		asserts.assertTrue(totalSecEmailAuthor < 4000);
		asserts.assertAll();

		tally.applyFilterToTallyBy(Input.MetaDataEAName, "exclude", Input.EmailAuthourName);

		base.waitForElement(tally.getTally_btnTallyApply());
		base.waitTillElemetToBeClickable(tally.getTally_btnTallyApply());
		tally.getTally_btnTallyApply().Click();
		tally.verifyTallyChart();
		base.passedStep("Executed - the tally report with CustodianName & EmailAuthorName filters selected");
		loginPage.logout();
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
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		commExpl.analyzeInTallyAction();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();
		float beforeEmailAuthor = System.currentTimeMillis();
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		base.waitTillElemetToBeClickable(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		base.waitForElementCollection(tally.getAllValueinEmailAuthorFilter());
		float afterEmailAuthor = System.currentTimeMillis();
		float totalSecEmailAuthor = afterEmailAuthor - beforeEmailAuthor;
		asserts.assertTrue(totalSecEmailAuthor < 4000);
		asserts.assertAll();

		tally.applyFilterToTallyBy(Input.MetaDataEAName, "exclude", Input.EmailAuthourName);

		base.waitForElement(tally.getTally_btnTallyApply());
		base.waitTillElemetToBeClickable(tally.getTally_btnTallyApply());
		tally.getTally_btnTallyApply().Click();
		tally.verifyTallyChart();

		base.passedStep("Executed - the tally report with EmailAuthorName filters selected");
		loginPage.logout();
	}

}
