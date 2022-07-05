package testScriptsRegressionSprint16;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression_02 {
	
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
	ProjectPage project;
	
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
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :When System Admin impersonate as RMU should be able to impersonate as Reviewer under different project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53122",enabled = true, groups = {"regression" })
	public void verifySaImpersonateRMuToRev() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53122");
		base.stepInfo("When System Admin impersonate as RMU should be able to impersonate as Reviewer under different project");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		base.impersonateSAtoRMU();
		driver.waitForPageToBeReady();
		if(base.text("Review Manager Dashboard for").isDisplayed()) {
			base.passedStep("System Admin should impersonate as Review Manager");
		}else {
			base.failedStep("verification failed");
		}
		base.impersonateRMUtoReviewer(Input.domainName,Input.largeVolDataProject,Input.securityGroup);
		driver.waitForPageToBeReady();
		
		base.passedStep("When System Admin impersonate as RMU is able to impersonate as Reviewer under different project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify while clicking on Project name "Go to Project" link will impersonated user as PAU into that project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53124",enabled = true, groups = {"regression" })
	public void verifyHyperLink() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53124");
		base.stepInfo("Verify while clicking on Project name \"Go to Project\" link will impersonated user as PAU into that project");
		
		//login as sa
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		driver.waitForPageToBeReady();
		dash.getFirstHyperLink().waitAndClick(10);
		dash.getFirstGoToProject().waitAndClick(10);
		base.stepInfo("clicked first hyper link and click go to project");
		if(base.text("Datasets").isDisplayed()) {
			base.passedStep("Clicking on hyperlink automatically impersonated user as project admin into the clicked project");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verified while clicking on Project name \"Go to Project\" link will impersonated user as PAU into that project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Sys Admin adds existing domain admin user as PA/RMU/Reviewer under same domain project
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53123",enabled = true, groups = {"regression" })
	public void verifySysAdminAddDaCredForPaAsSameDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53124");
		base.stepInfo("Verify when Sys Admin adds existing domain admin user as PA/RMU/Reviewer under same domain project");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		driver.waitForPageToBeReady();
		userManage.addNewUserWithoutVerifySuccesMsg(Input.randomText, Input.randomText, "Project Administrator", Input.da1userName, Input.domainName, Input.projectName);
		base.VerifyErrorMessage("20001000033 : This user cannot be added with the specified role since the user is already a domain administrator in the selected domain");
		
		base.passedStep("Verified when Sys Admin adds existing domain admin user as PA/RMU/Reviewer under same domain project");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: NA 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify when Sys Admin selects 'Domain Admin' as Impersonate To
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-52860",enabled = true, groups = {"regression" })
	public void verifySysAdminImporsonate() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-52860");
		base.stepInfo("Verify when Sys Admin selects 'Domain Admin' as Impersonate To");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		userManage = new UserManagement(driver);
		
		base.openImpersonateTab();
		if(base.getSelectRole().isDisplayed()) {
			base.passedStep("Impersonate To' pop up is open");
		}else {
			base.failedStep("verification failed");
		}
		base.selectImpersonateRole("Domain Administrator");
		base.selectImpersonateDomain(Input.domainName);
		base.getSaveChangeRole().waitAndClick(10);
		base.stepInfo("Domain Admin role is selected  Make sure that on selecting the domain admin role drop down should be displayed to select the domain");
				
		base.passedStep("Verified when Sys Admin selects 'Domain Admin' as Impersonate To");
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
