package sightline.smallComponents;

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

public class DomainManagement_Regression_01 {
	
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
	 * date: 24-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate new project creation of non-Domain client by System Admin user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-46915",enabled = true, groups = {"regression" })
	public void validatedNewlyCreatedNonDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-46915");
		base.stepInfo("Validate new project creation of non-Domain client by System Admin user");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		
		String[] validationMessage = {
				"You must specify the project name",
				"You must specify the client entity",
				"You must specify a project Hcode",
				"You must specify a value for the project db server.",
				"Project Workspace is required",
				"Ingestion Server Path is required",
				"Production Server Path is required",
				"You must specify the project folder",
				"You must specify the ingestion folder.",
				"You must specify the production folder",
		};
		String projectnamenondomain = "AutomationScriptCreatedNonDomain"+Utility.dynamicNameAppender();
		String hcode = "hcode"+Utility.dynamicNameAppender();
		
		base.clearBullHornNotification();
		project.navigateToProductionPage();
		project.getAddProjectBtn().waitAndClick(10);
		project.getButtonSaveProject().waitAndClick(10);
		driver.scrollPageToTop();
		base.isTextAreAvailableInWebPage(validationMessage);
		
		project.navigateToProductionPage();
		project.AddNonDomainProject(projectnamenondomain, hcode);
		
		base.getRedBullHornIcon().isElementAvailable(300);
		project.filterTheProject(projectnamenondomain);
		int n = base.getIndex(project.getProjectTableHeader(), "IN DOMAIN");
		String inDomain = project.getColumValue(n).getText().trim();
		
		if(inDomain.equals("No")) {
			base.passedStep("Newly created project is available in project list and is listed as Non-Domain project.");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Validated new project creation of non-Domain client by System Admin user");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 24-06-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate new project creation of Domain client by System Admin user
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-46916",enabled = true, groups = {"regression" })
	public void validatedNewlyCreatedDomain() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-46916");
		base.stepInfo("Validate new project creation of Domain client by System Admin user");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		
		String[] validationMessage = {
				"You must specify the project name",
				"You must specify the client entity",
				"You must specify a project Hcode",
				"You must specify a value for the project db server.",
				"Project Workspace is required",
				"Ingestion Server Path is required",
				"Production Server Path is required",
				"You must specify the project folder",
				"You must specify the ingestion folder.",
				"You must specify the production folder",
		};
		String projectnamenondomain = "AutomationScriptCreatedDomain"+Utility.dynamicNameAppender();
		
		base.clearBullHornNotification();
		project.navigateToProductionPage();
		project.getAddProjectBtn().waitAndClick(10);
		project.getButtonSaveProject().waitAndClick(10);
		driver.scrollPageToTop();
		base.isTextAreAvailableInWebPage(validationMessage);
		
		project.navigateToProductionPage();
		project.AddDomainProject(projectnamenondomain, Input.domainName);
		
		base.getRedBullHornIcon().isElementAvailable(300);
		project.filterTheProject(projectnamenondomain);
		int n = base.getIndex(project.getProjectTableHeader(), "IN DOMAIN");
		String inDomain = project.getColumValue(n).getText().trim();
		
		if(inDomain.equals("Yes")) {
			base.passedStep("Newly created project is available in project list and is listed as Domain project.");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Validated new project creation of Domain client by System Admin user");
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
