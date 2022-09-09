package testScriptsRegressionSprint18;

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

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroup_Regression2_Sprint18 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

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
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:25/07/2022 RPMXCON-54819
	 * @throws Exception
	 * @Description Verify that if SAU impersonate as DAU,clicking on project in
	 *              Domain Dashboard, it should take to Default SG and changing the
	 *              project from header drop down should take to Default SG in the
	 *              selected project.
	 * 
	 */
	@Test(description = "RPMXCON-54819", enabled = true, groups = { "regression" })
	public void verifySAImpersonateDASelectProjectDefaultSG() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54819");
		baseClass.stepInfo(
				"Verify that if SAU impersonate as DAU,clicking on project in Domain Dashboard, it should take to Default SG and changing the project from header drop down should take to Default SG in the selected project.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		baseClass.stepInfo("Impersonate SA to DA");
		baseClass.impersonateSAtoDA();

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		baseClass.stepInfo("Select project");
		baseClass.selectproject();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("Default SG in the selected project is displayed successfully");
		} else {
			baseClass.failedStep("Default SG in the selected project is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/08/2022 RPMXCON-54830
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is Reviewer, ‘back to dashboard’
	 *              should not be available.
	 * 
	 */
	@Test(description = "RPMXCON-54830", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsReviewerHomePageNotAvailableDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54830");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is Reviewer, ‘back to dashboard’ should not be available.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);

		baseClass.stepInfo("Verify last access for domain dashboard Project not avaliable");
		if (!baseClass.text("Project :").isDisplayed()) {
			baseClass.passedStep(" 'back to dashboard' project header drop down is Not Available ");
		} else {
			baseClass.failedStep(" 'back to dashboard' near project header drop down is available ");
		}
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/08/2022 RPMXCON-54823
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is RMU, clicking on Back to
	 *              dashboard, it should redirect to last access Domain Dashboard
	 *              page.
	 * 
	 */
	@Test(description = "RPMXCON-54823", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsRMUHomePageBackToDomainDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54823");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is RMU, clicking on Back to dashboard, it should redirect to last access Domain Dashboard page.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify RMU Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		// validation for back button
		baseClass.stepInfo("Click on back Button");
		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}

		loginPage.logout();

	}

	/**
	 * @Author Vijaya.Rani ModifyDate:01/08/2022 RPMXCON-54825
	 * @Description :Verify that as DA user, clicking on project should redirect to
	 *              default security group and select other domain project from
	 *              header drop down, clicking on Back to Dashboard, it should
	 *              redirect to other Domain Dashboard page
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54825", enabled = true, groups = { "regression" })
	public void verifyDAClickingOtherDomainProjectWhichRmuBackToDashBoard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54825");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select other domain project from header drop down, clicking on Back to Dashboard, it should redirect to other Domain Dashboard page");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();
		
		baseClass.stepInfo("verify default security group in selected project");
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.projectName);
		driver.waitForPageToBeReady();
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}

		baseClass.waitTillElemetToBeClickable(sgpage.backToDomain());
		sgpage.backToDomain().waitAndClick(5);
		baseClass.waitTime(5);
		if (sgpage.daFirstBlock().isDisplayed()) {
			baseClass.passedStep("Navigated back to domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
		loginPage.logout();

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
