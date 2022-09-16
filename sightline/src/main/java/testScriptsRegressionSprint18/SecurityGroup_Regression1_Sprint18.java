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
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroup_Regression1_Sprint18 {

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
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              DSG and select the project from header dropdown in which DAU is
	 *              RMU assigned to other than default security group, then
	 *              application should take RMU in his assigned Security group.
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
		if (annotation.getDefaultAnnotation_Layers().Displayed()) {
			baseClass.passedStep("'Default Annotation Layer' is displayed successfully");
		} else {
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
		if (annotation.getDefaultAnnotation_Layers().Displayed()) {
			baseClass.passedStep("'Default Annotation Layer' is displayed successfully");
		} else {
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
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54831
	 * @throws Exception
	 * @Description Verify that as DA user,clicking on project should redirect to
	 *              default security group and select the other project from hedaer
	 *              drop down in which DAU is Reviewer,it should take the user to
	 *              Reviewer home page.
	 * 
	 */
	@Test(description = "RPMXCON-54831", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsReviewerHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54831");
		baseClass.stepInfo(
				"Verify that as DA user,clicking on project should redirect to default security group and select the other project from hedaer drop down in which DAU is Reviewer,it should take the user to Reviewer home page.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

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
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54868
	 * @throws Exception
	 * @Description After impersonated as PAU in domain project where DAU is PAU and
	 *              select the other domain project from header drop down having DAU
	 *              role, should redirect to the PAU home page for the selected
	 *              project.
	 * 
	 */
	@Test(description = "RPMXCON-54868", enabled = true, groups = { "regression" })
	public void verifyDAAfterImpersonatedPAHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54868");
		baseClass.stepInfo(
				"After impersonated as PAU in domain project where DAU is PAU and select the other domain project from header drop down having DAU role, should redirect to the PAU home page for the selected project.");
		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		baseClass.stepInfo("Impersonate DA to PA");
		driver.waitForPageToBeReady();
		baseClass.impersonateDAtoPA();

		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to PAU home page Successfully ");
		} else {
			baseClass.failedStep("verification failed");
		}

		baseClass.stepInfo("Select other Domain project");
		baseClass.selectproject(projectName);

		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to the PAU home page for the selected project Successfully");
		} else {
			baseClass.failedStep("verification failed");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54835
	 * @throws Exception
	 * @DescriptionVerify that after clicking on project,selecting the other project
	 *                    from header in which DAU is Reviewer,it should redirect to
	 *                    Reviewer homepage ,again if selects any project from
	 *                    header ,then it should redirect to reviewer for selected
	 *                    project.
	 * 
	 */
	@Test(description = "RPMXCON-54835", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsPAAsReviewerHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54835");
		baseClass.stepInfo(
				"Verify that after clicking on project,selecting the other project from header in which DAU is Reviewer,it should redirect to Reviewer homepage ,again if selects any project from header ,then it should redirect to reviewer for selected project.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		DataSets data = new DataSets(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

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
		baseClass.stepInfo("Select other Domain project");
		baseClass.selectproject(projectName);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify Reviewer Home Page");
		if (domainDash.getUserHomePage().Displayed()) {
			baseClass.passedStep(" Reviewer home page is displayed successfully");
		} else {
			baseClass.failedStep("Reviewer home page is not displayed ");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/07/2022 RPMXCON-54829
	 * @throws Exception
	 * @Description Verify on clicking on ay Project, DAU should take to Default SG
	 *              in the selected project and select Project (Domain/non-domain)
	 *              from drop down in which DAU is PAU, it should take the Default
	 *              SG of the selected project.
	 * 
	 */
	@Test(description = "RPMXCON-54829", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsPAHomePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54829");
		baseClass.stepInfo(
				"Verify on clicking on ay Project, DAU should take to Default SG in the selected project and select Project (Domain/non-domain) from drop down in which DAU is PAU, it should take the Default SG of the selected project.");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		DataSets data = new DataSets(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
		baseClass.waitTime(5);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

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

		baseClass.stepInfo("Select other Domain project");
		baseClass.selectproject(projectName);

		driver.waitForPageToBeReady();
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		loginPage.logout();
	}

	/**
	 * @Author Krishna Date: 3/08/2022
	 * @Description : In Manage Security Group- 'Available in Project' list is
	 *              getting populated for WorkProducts and ProjectFields
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54977", enabled = true, groups = { "regression" })
	public void verifySgAvaibleProjectInListProjectFields() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54977");
		baseClass.stepInfo(
				"In Manage Security Group- 'Available in Project' list is getting populated for WorkProducts and ProjectFields");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		ProjectFieldsPage projectFields = new ProjectFieldsPage(driver);
		String random = Input.randomText + String.valueOf(utility.dynamicNameAppender());
		final String random1 = random;
		final String random2 = random;

		baseClass.stepInfo("Navigate To Project Field sPage");
		projectFields.navigateToProjectFieldsPage();
		baseClass.stepInfo("Add a new project field as project administrator");
		projectFields.addProjectField(random1, random2, Input.fldClassification, Input.fldDescription, Input.fieldType,
				Input.fieldLength);
		sgpage.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("navigated to security group as expected");

		sgpage.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		sgpage.verifySelectedProjectField(random1);
		if (sgpage.getSG_GenerateEmailRadioButton(1).isDisplayed()) {
			baseClass.passedStep(
					"Use Project-level Email Inclusive and Email Duplicate Data option button is checked as per the config as expected.");
		} else {
			baseClass.failedStep("Data option button is not expected");
		}
	}

	/**
	 * @Author Krishna Date: 3/08/2022
	 * @Description :Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is RMU, on clicking on Back to
	 *              Dashboard shall take the user to the last accessed domain
	 *              dashboard
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54826", enabled = true, groups = { "regression" })
	public void verifyDAClickingProjectWhichRmuBackToDashBoard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54826");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is RMU, on clicking on Back to Dashboard shall take the user to the last accessed domain dashboard");
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
			baseClass.passedStep("Navigated back to user to the last accessed domain dashboard page");
		} else {
			baseClass.failedStep("Not Navigated back to domain home page");
		}
	}

	/**
	 * @Author Krishna Date: 26/07/2022
	 * @Description :Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the non-domain project from
	 *              header drop down where DAU is a RMU , clicking on Back to
	 *              Dashboard user is redirecting to last access Domain Dashboard
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54824", enabled = true, groups = { "regression" })
	public void verifyDAClickingNonDomainProjectWhichRmuBackToDashBoard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54824");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the non-domain project from header drop down where DAU is a RMU , clicking on Back to Dashboard user is redirecting to last access Domain Dashboard");
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.stepInfo("Click on any project");
		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project in Rmu");
		driver.waitForPageToBeReady();
	    baseClass.waitTime(5);
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
			baseClass.passedStep("Navigated back to last access domain Dashboard home page");
		} else {
			baseClass.failedStep("Not Navigated back to domain admin home page");
		}
		loginPage.logout();

	}

	/**
	 * @author Krishna ModifyDate:05/08/2022 RPMXCON-54822
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the other domain project from
	 *              header drop down in which DAU is PAU, clicking on Back to
	 *              Dashboard user is redirecting to last access Domain Dashboard.
	 * 
	 */
	@Test(description = "RPMXCON-54822", enabled = true, groups = { "regression" })
	public void verifyDAClickingAnyProjectIsPAHomePageBackToDomainDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54822");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the other domain project from header drop down in which DAU is PAU, clicking on Back to Dashboard user is redirecting to last access Domain Dashboard");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DataSets data = new DataSets(driver);

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
		baseClass.stepInfo("verify PA Home Page");
		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to PAU home page Successfully ");
		} else {
			baseClass.failedStep("verification failed");
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
	 * @author Krishna ModifyDate:05/08/2022 RPMXCON-54821
	 * @throws Exception
	 * @Description Verify that as DA user, clicking on project should redirect to
	 *              default security group and select the non-domain project from
	 *              header drop down where DAU is a PAU , clicking on Back to
	 *              Dashboard user is redirecting to last access Domain Dashboard
	 * 
	 */
	@Test(description = "RPMXCON-54821", enabled = true, groups = { "regression" })
	public void verifyDAClickingNonDomainProjectIsPAHomePageBackToDomainDashboard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54821");
		baseClass.stepInfo(
				"Verify that as DA user, clicking on project should redirect to default security group and select the non-domain project from header drop down where DAU is a PAU , clicking on Back to Dashboard user is redirecting to last access Domain Dashboard");
		userManage = new UserManagement(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DataSets data = new DataSets(driver);

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify Default Security Group display");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It redirect to Security Gruop");
		} else {
			baseClass.failedStep("It is not Security Gruop");
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify PA Home Page");
		driver.waitForPageToBeReady();
		if (data.getDatasetBtn().isDisplayed()) {
			baseClass.passedStep("It is redirect to PAU home page Successfully ");
		} else {
			baseClass.failedStep("verification failed");
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
