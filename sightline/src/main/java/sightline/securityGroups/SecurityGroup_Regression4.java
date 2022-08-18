package sightline.securityGroups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
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

public class SecurityGroup_Regression4 {

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
	 * @author Vijaya.Rani ModifyDate:19/07/2022 RPMXCON-54828
	 * @throws Exception
	 * @Description Verify if SAU impersonate as DAU, clicking on project should
	 *              redirect to default security group and select the domain project
	 *              from header drop down, clicking on Back to dashboard , it should
	 *              redirect to last access Domain Dashboard page.
	 * 
	 */
	@Test(description = "RPMXCON-54828", enabled = true, groups = { "regression" })
	public void verifySAImpersonateAsDAAccessDomainDashBoardPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54828");
		baseClass.stepInfo(
				"Verify if SAU impersonate as DAU, clicking on project should redirect to default security group and select the domain project from header drop down, clicking on Back to dashboard , it should redirect to last access Domain Dashboard page.");

		userManage = new UserManagement(driver);
		DataSets data = new DataSets(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		String projectName = "DAProject" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		driver.waitForPageToBeReady();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", "Default Security Group",
				false, true);

		baseClass.stepInfo("Impersonate SA to DA");
		baseClass.impersonateSAtoDA();

		baseClass.stepInfo("Verify it is domain dashboard page");
		if (domainDash.getDataRefresh_info().isDisplayed()) {
			baseClass.passedStep("Application is navigate to Domain Dashboard page Successfully");
		} else {
			baseClass.failedStep("Application is not navigate to Domain Dashboard page   ");
		}

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It is redirect to default SG in selected project successfully ");
		} else {
			baseClass.failedStep("It is not redirect to default SG in selected project ");
		}

		baseClass.stepInfo("Select Another project");
		baseClass.selectproject(projectName);

		baseClass.stepInfo("verify default security group in selected project");
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("It is redirect to default SG in selected project successfully ");
		} else {
			baseClass.failedStep("It is not redirect to default SG in selected project ");
		}

		// validation for back button
		baseClass.stepInfo("Click on back Button");
		driver.Navigate().back();

		baseClass.stepInfo("Verify last access for domain dashboard ");
		if (domainDash.getDataRefresh_info().isDisplayed()) {
			baseClass.passedStep("It is redirect to last access domain dashboard ");
		} else {
			baseClass.failedStep("It is not redirect to last access domain dashboard");

		}
	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/07/2022 RPMXCON-54774
	 * @throws Exception
	 * @Description Verify that when DAU Changes the SG in SG Dropdown then it
	 *              should take the corresponding SG in the same project.
	 * 
	 */
	@Test(description = "RPMXCON-54774", enabled = true, groups = { "regression" })
	public void verifyRmuChangeSgFromDropDownTheSameProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54774");
		baseClass.stepInfo(
				"Verify that when DAU Changes the SG in SG Dropdown then it should take the corresponding SG in the same project.");
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		DomainDashboard domainDash = new DomainDashboard(driver);
		SoftAssert softassert = new SoftAssert();
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Create security group
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("Added new Security group");
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");

		// go to project
		baseClass.stepInfo("Click on any project");
		domainDash.filterProject(Input.projectName);
		domainDash.goToFirstProject();

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(SGname));
		softassert.assertTrue(docViewMetaData.getSecurityGroup(SGname).isElementAvailable(5));
		if (docViewMetaData.getSecurityGroup(SGname).isElementAvailable(5)) {
			baseClass.passedStep("corressponding " + SGname + "on the same project is displayed successfully");

		} else {
			baseClass.failedStep("SG not displayed");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-47098
	 * @throws Exception
	 * @Description Verify that redaction/annotations/remarks icons should be
	 *              displayed on doc view though the Project Admin/RMU user who
	 *              created new annotation layer is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47098", enabled = true, groups = { "regression" })
	public void verifyRedactionAnnotationRemarksIconDisplayInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47098");
		baseClass.stepInfo(
				"Verify that redaction/annotations/remarks icons should be displayed on doc view though the Project Admin/RMU user who created new annotation layer is in-active.");

		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassert = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.rmu1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// verify redaction icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.redactionIcon());
		softassert.assertTrue(docView.redactionIcon().Displayed());
		softassert.assertAll();
		baseClass.passedStep("Redaction Icon is Displayed Succesfully");

		// verify annotation icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnnotateIcon());
		softassert.assertTrue(docView.getDocView_AnnotateIcon().Displayed());
		softassert.assertAll();
		baseClass.passedStep("DocView Annotation Icon is Displayed Succesfully");

		// verify Remark icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AddRemarkIcon());
		softassert.assertTrue(docView.getDocView_AddRemarkIcon().Displayed());
		softassert.assertAll();
		baseClass.passedStep("DocView Reviewer Remark Icon is Displayed Succesfully");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/07/2022 RPMXCON-47085
	 * @throws Exception
	 * @Description Verify that newly created annotation layer should be displayed
	 *              though the System Admin user who created the project, annotation
	 *              layer is in-active.
	 * 
	 */
	@Test(description = "RPMXCON-47085", enabled = true, groups = { "regression" })
	public void verifyCreatAnnotationLayerInSA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47085");
		baseClass.stepInfo(
				"Verify that newly created annotation layer should be displayed though the System Admin user who created the project, annotation layer is in-active.");

		userManage = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		DataSets data = new DataSets(driver);
		AnnotationLayer annotation = new AnnotationLayer(driver);
		SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
		String projectName = "PAProject" + Utility.dynamicNameAppender();
		String annoName = "newAnnotation" + Utility.dynamicNameAppender();
		String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
		String firstname = "tempcar";
		String lastname = "consilio";
		String mailId = "tempcar@conilio.com";

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SA as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		driver.waitForPageToBeReady();
		baseClass.waitTime(10);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);

		baseClass.stepInfo("Impersonate SA to PA");
		baseClass.impersonateSAtoPA();

		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		annotation.AddAnnotation(annoName);
		System.out.println(annoName);
		baseClass.passedStep("Annotation layer created successfull under the 'AllSecurityGroups'");

		// Create security group
		baseClass.stepInfo("Go to Manage securityGroup Page and Add new securityGroup");
		sgpage.navigateToSecurityGropusPageURL();
		sgpage.AddSecurityGroup(SGname);
		baseClass.stepInfo("new Security group is created");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("seclect created SecurityGroup and Annotationlayer save it");
		sgpage.addAnnotationlayertosg(SGname, annoName);
		baseClass.passedStep("Newly created annotation layer is mapped to the newly created security group");
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

		baseClass.stepInfo("Impersonate SA to RMU");
		baseClass.impersonateSAtoRMU(projectName);

		baseClass.stepInfo("Go to AnnotationLayer Page Check ");
		annotation.navigateToAnnotationLayerPage();
		baseClass.stepInfo("Go to Manage Annotation Layer Page and Add new Annotation");
		softassert.assertTrue(annotation.getAnnotation_Layers().Displayed());
		softassert.assertAll();
		baseClass.passedStep(
				"Newly created annotation layer is displayed though the System Admin user who created the project, annotation layer is in-active");
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgpage.deleteSecurityGroups(SGname);
		baseClass.stepInfo("Added Security group has been deleted");
		annotation.deleteAnnotationByPagination(annoName);
		baseClass.stepInfo("Added AnnotationLayer has been deleted");
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
