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
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
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

public class SecurityGroup_Regression1 {

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
	 * @author Vijaya.Rani ModifyDate:25/07/2022 RPMXCON-54832
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to DSG and select the project from header 
	 * dropdown in which DAU is RMU assigned to other than default security group, then application should take RMU in his assigned Security group.
	 * 
	 */
	 @Test(description = "RPMXCON-54832", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsRMUHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54832");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to DSG and select the project from header dropdown in which DAU is RMU assigned to other than default security group, then application should take RMU in his assigned Security group.");

		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		
		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		
		baseClass.stepInfo("Click on any project");
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		baseClass.waitForElement(domainDash.getprojectnamelink(Input.projectName));
		domainDash.getprojectnamelink(Input.projectName).waitAndClick(5);
		
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
		if(domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer Manager home page is displayed successfully");
		}else {
			baseClass.failedStep("Reviewer Manager home page is not displayed ");
		}
		loginPage.logout();
		
	 }

	   /**
		 * @author Vijaya.Rani ModifyDate:25/07/2022 RPMXCON-54819
		 * @throws Exception
		 * @Description Verify that if SAU impersonate as DAU,clicking on project in Domain Dashboard, it should take to Default
		 * SG and changing the project from header drop down should take to Default SG in the selected project.
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
			
			baseClass.stepInfo("Click on any project");
			driver.waitForPageToBeReady();
			baseClass.waitTime(10);
			baseClass.waitForElement(domainDash.getprojectnamelink(Input.projectName));
			domainDash.getprojectnamelink(Input.projectName).waitAndClick(5);
			
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
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-47083
	 * @throws Exception
	 * @Description Verify that default annotation layer should be displayed though
	 *              the System Admin user who created the project is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47083", enabled = true, groups = { "regression" })
	public void verifyDefaultAnnotationLayerInSAProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47083");
		baseClass.stepInfo(
				"Verify that default annotation layer should be displayed though the System Admin user who created the project is in-active.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA(projectName);

		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("After Impersonate RMU Verify Default AnnotationLayer Display");
		driver.waitForPageToBeReady();
		annotation.navigateToAnnotationLayerPage();
		if(annotation.getDefaultAnnotation_Layers().Displayed()) {
			baseClass.passedStep("'Default Annotation Layer' is displayed successfully");
		}else {
			baseClass.failedStep("'Default Annotation Layer' is not displayed ");
		}
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser();
		baseClass.passedStep("User is de-activated/deleted successfully");
		
		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA(projectName);
		
		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");
		loginPage.logout();
	}
	
	
	/**
	 * @author Vijaya.Rani ModifyDate:20/07/2022 RPMXCON-47084
	 * @throws Exception
	 * @Description Verify that default annotation layer should be displayed though
	 *              the Domain Admin user who created the project is in-active.
	 * 
	 */
    @Test(description = "RPMXCON-47084", enabled = true, groups = { "regression" })
	public void verifyDefaultAnnotationLayerDisplayInDA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47084");
		baseClass.stepInfo(
				"Verify that default annotation layer should be displayed though the Domain Admin user who created the project is in-active.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		String projectName = "DAProject" + Utility.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		driver.waitForPageToBeReady();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopiedInDA(projectName, Input.projectName, "4");
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate DA to PA");
		baseClass.impersonateDAtoPA();
		
		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();
		
		baseClass.stepInfo("After Impersonate RMU Verify Default AnnotationLayer Display");
		driver.waitForPageToBeReady();
		annotation.navigateToAnnotationLayerPage();
		if(annotation.getDefaultAnnotation_Layers().Displayed()) {
			baseClass.passedStep("'Default Annotation Layer' is displayed successfully");
		}else {
			baseClass.failedStep("'Default Annotation Layer' is not displayed ");
		}
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.createUser(firstname, lastname, "Project Administrator", mailId, null, projectName);
		baseClass.stepInfo("Delete other SA Account In User Page");
		userManage.navigateToUsersPAge();
		userManage.passingUserName(firstname);
		userManage.applyFilter();
		userManage.deleteUser();
		baseClass.passedStep("User is de-activated/deleted successfully");
		
		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA(projectName);
		
		baseClass.stepInfo("Go to Manage Annotation Layer Page Check default Annotation layer");
		annotation.verifyAnnotationLayerInDSG(Input.securityGroup);
		baseClass.passedStep("'Default Annotation Layer' is displayed under 'Default Security Group' successfully");
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
