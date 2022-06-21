package testScriptsRegressionPhase2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainDashBoard_Regression_01 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
	DomainDashboard dash;
	
	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}
	
	/**
	 * @Author :Aathith 
	 * date: 21-06-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify Active users widget should not be removed from widget
	 */
	@Test(description = "RPMXCON-53119",enabled = true, groups = {"regression" },priority = 1)
	public void verifyActiveUserNotRemovedByWidget() {
		
		base.stepInfo("Test case Id: RPMXCON-53119");
		base.stepInfo("Verify Active users widget should not be removed from widget");
		
		//login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		dash.getGearbtn().ScrollTo();
		dash.getGearbtn().waitAndClick(10);
		if(!base.textValue("add").isElementAvailable(1)) {
			base.passedStep("Widget add is not be available");
		}else {
			base.failedStep("Widget add is be available");
		}
		if(!base.textValue("remove").isElementAvailable(1)) {
			base.passedStep("Widget remove is not be available");
		}else {
			base.failedStep("Widget remove is be available");
		}
		
		base.passedStep("Verified Active users widget should not be removed from widget");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 21-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate exported values for project grid details from Domain Dashboard for Domain Admin Login
	 */
	@Test(description = "RPMXCON-53131",enabled = true, groups = {"regression" },priority = 2)
	public void validateExportDetails() throws IOException {
		
		base.stepInfo("Test case Id: RPMXCON-53131");
		base.stepInfo("Validate exported values for project grid details from Domain Dashboard for Domain Admin Login");
		
		//login as Da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a Da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		String[] colums = {"NoOfCustodian","NoOfIngestion"};
		
		//verify export
		driver.scrollingToElementofAPage(dash.getExportbtn());
		dash.getExportbtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		
		//verify nofication, backgroud task
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		int n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		//perfore colum add remove
		dash.AddOrRemoveColum(colums);
		
		//verify notification, backgroud task
		dash.getBackGround().waitAndClick(10);
		dash.getExportbtn().waitAndClick(10);
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		base.passedStep("Validated exported values for project grid details from Domain Dashboard for Domain Admin Login");
		loginPage.logout();
	}
	
	/** 
	 * @Author :Aathith 
	 * date: 21-06-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate exported values for project grid details from Domain Dashboard for Domain Admin Login(Impersonate from System Admin)
	 */
	@Test(description = "RPMXCON-53132",enabled = true, groups = {"regression" },priority = 3)
	public void validateExportDetailsAsSA() throws IOException {
		
		base.stepInfo("Test case Id: RPMXCON-53132");
		base.stepInfo("Validate exported values for project grid details from Domain Dashboard for Domain Admin Login(Impersonate from System Admin)");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a Da user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		String[] colums = {"NoOfCustodian","NoOfIngestion"};
		
		//verify export
		base.impersonateSAtoDA(Input.domainName);
		driver.scrollingToElementofAPage(dash.getExportbtn());
		dash.getExportbtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		
		//verify nofication, backgroud task
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		int n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		//perfore colum add remove
		dash.AddOrRemoveColum(colums);
		
		//verify notification, backgroud task
		dash.getBackGround().waitAndClick(10);
		dash.getExportbtn().waitAndClick(10);
		base.waitForElement(base.getSuccessMsgHeader());
		Assert.assertEquals("Success !", base.getSuccessMsgHeader().getText().toString());
		base.passedStep("succes message is displayed");
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();
		
		//verify file type
		dash.waitForFileDownload();
		if(base.GetFileName().contains("xlsx")) {
			base.passedStep("xlsx file downloaded");
		}else {
			base.failedStep("file not contains xlsx");
		}
		
		//verify export values
		dash.verifyProjectName(dash.getColumValues(dash.getprojectName()));
		n = base.getIndex(dash.getTableHeader(), "CUSTODIANS (#)");
		dash.verifyCustodianValues(dash.getColumValues(dash.getColumValue(n)));
		
		base.passedStep("Validated exported values for project grid details from Domain Dashboard for Domain Admin Login(Impersonate from System Admin)");
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
	
	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

}
