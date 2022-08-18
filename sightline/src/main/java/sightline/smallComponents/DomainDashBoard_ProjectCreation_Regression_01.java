package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebElement;
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

public class DomainDashBoard_ProjectCreation_Regression_01 {
	
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
	 * date: 06-07-22
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate for Display inactive Project toggle on Dashboard screen 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53145",enabled = true, groups = {"regression" })
	public void vaildateDisplayInactiveToggle() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53145");
		base.stepInfo("Validate for Display inactive Project toggle on Dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		dash.createAndDeactivateProject();
		dash.isActiveInactiveListed();
		dash.clickInactiveProjectName();
		dash.getInactiveProjectToggle().waitAndClick(10);
		dash.verifyOnlyActiveProjectListed();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		dash.isActiveInactiveListed();
		dash.clickInactiveProjectName();
		dash.getInactiveProjectToggle().waitAndClick(10);
		dash.verifyOnlyActiveProjectListed();
		
		base.passedStep("Validated for Display inactive Project toggle on Dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 06-07-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate deactivating a project from Dashboard summary grid
	 */
	@Test(description = "RPMXCON-53160",enabled = true, groups = {"regression" })
	public void validateDeactivateProjectFromDD() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53160");
		base.stepInfo("Validate deactivating a project from Dashboard summary grid");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		softAssertion = new SoftAssert();
		
		String projectName = Input.randomText+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		base.clearBullHornNotification();
		dash.create_a_project_From_Domain(projectName);
		base.waitForNotification();
		dash.naviageToDomainDashBoardPage();
		
		base.stepInfo("project was created");
		
		//cancel the inActivate
		dash.doYouDeactivateProject(projectName);
		base.waitForElement(dash.getPopupMsg());
		String text = dash.getPopupMsg().getText().trim();
		softAssertion.assertEquals(text, "Do you really want to deactivate this project?");
		dash.getNoBtn().waitAndClick(10);
		base.stepInfo("cancel inActivate a project");
		
		
		//deactivate a project
		dash.doYouDeactivateProject(projectName);
		dash.getYesBtn().waitAndClick(10);
		base.stepInfo("deactivate a project");
		
		//verify ascending descending order
		dash.clearProjectSearchFilter();
		dash.verifyAscendingDescendingOrder();
		
		base.passedStep("Validated deactivating a project from Dashboard summary grid");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 06-07-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Domain Dashboard - Deactivating project from grid list
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 */
	@Test(description = "RPMXCON-53128",enabled = true, groups = {"regression" })
	public void verifyDeactivateProject() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53128");
		base.stepInfo("Domain Dashboard - Deactivating project from grid list"
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		softAssertion = new SoftAssert();
		
		String projectName = Input.randomText+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		base.clearBullHornNotification();
		dash.create_a_project_From_Domain(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		dash.naviageToDomainDashBoardPage();
		
		//cancel the inActivate
		dash.doYouDeactivateProject(projectName);
		dash.getNoBtn().waitAndClick(10);
		base.stepInfo("Action was cancelled and user should be on Domain Dashboard screen");
		
		//deactivate a project
		dash.doYouDeactivateProject(projectName);
		dash.getYesBtn().waitAndClick(10);
		base.stepInfo("project was deactivated");
		
		
		//verify
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.enableInActiveProject();
		base.waitTime(5);
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		List<WebElement> element = dash.getColumValue(base.getIndex(dash.getTableHeader(), "STATUS")).FindWebElements();
		String status = element.get(0).getText().trim();
		softAssertion.assertEquals(status, "Inactive");
		System.out.println(status);
		base.passedStep("Project is in Inactive status");
		
		//login as sa
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		projectName = Input.randomText+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		base.clearBullHornNotification();
		dash.create_a_project_From_Domain(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		dash.naviageToDomainDashBoardPage();
		
		//cancel the inActivate
		dash.doYouDeactivateProject(projectName);
		dash.getNoBtn().waitAndClick(10);
		base.stepInfo("Action was cancelled and user should be on Domain Dashboard screen");
				
		//deactivate a project
		dash.doYouDeactivateProject(projectName);
		dash.getYesBtn().waitAndClick(10);
		base.stepInfo("project was deactivated");
	
		//filter
		driver.waitForPageToBeReady();
		dash.enableInActiveProject();
		base.waitTime(5);
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		
		//verify
		dash.waitForDomainDashBoardIsReady();
		List<WebElement> ele = dash.getColumValue(base.getIndex(dash.getTableHeader(), "STATUS")).FindWebElements();
		String status1 = ele.get(0).getText().trim();
		softAssertion.assertEquals(status1, "Inactive");
		System.out.println(status1);
		base.passedStep("Project is in Inactive status");
		softAssertion.assertAll();
		
		base.passedStep("Domain Dashboard - Deactivating project from grid list"
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 06-07-22 
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Validate creating new Project from Domain dashboard screen 
	 * (Domain Admin login and System Admin impersonate as Domain Admin)
	 */
	@Test(description = "RPMXCON-53139",enabled = true, groups = {"regression" })
	public void validateNewlyCreatedProject() throws InterruptedException  {
		
		base.stepInfo("Test case Id: RPMXCON-53139");
		base.stepInfo("Validate creating new Project from Domain dashboard screen (Domain Admin login and System Admin impersonate as Domain Admin)");
		
		//login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		project = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		
		String projectName = "AAA"+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(projectName);
		base.waitForNotification();
		dash.naviageToDomainDashBoardPage();
		dash.getNotificationMessage(0, projectName);
		base.stepInfo(projectName+" project was created");
		
		//verify is available
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.enableInActiveProject();
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		List<WebElement> element = dash.getColumValue(base.getIndex(dash.getTableHeader(), "PROJECT NAME")).FindWebElements();
		String status = element.get(0).getText().trim();
		System.out.println(status);
		System.out.println(projectName);
		softAssertion.assertEquals(status, projectName);
		base.passedStep("Newly created project is available in the list");
		
		//login as sa
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		base.impersonateSAtoDA(Input.domainName);
		
		projectName = "AAA"+Utility.dynamicNameAppender();
		
		//create a project and filter the project
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		base.clearBullHornNotification();
		project.AddDomainProjectViaDaUser(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		dash.naviageToDomainDashBoardPage();
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.filterProject(projectName);
		
		//verify project is availavle
		driver.waitForPageToBeReady();
		dash.waitForDomainDashBoardIsReady();
		dash.enableInActiveProject();
		dash.filterProject(projectName);
		driver.waitForPageToBeReady();
		List<WebElement> ele = dash.getColumValue(base.getIndex(dash.getTableHeader(), "PROJECT NAME")).FindWebElements();
		String status1 = ele.get(0).getText().trim();
		softAssertion.assertEquals(status1, projectName);
		base.passedStep("Newly created project is available in the list");
		softAssertion.assertAll();
		
		base.passedStep("Validated creating new Project from Domain dashboard screen "
				+ "(Domain Admin login and System Admin impersonate as Domain Admin)");
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
