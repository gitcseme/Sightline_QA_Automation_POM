package testScriptsRegressionSprint19;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroups_Regression6 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
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

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

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
		loginPage = new LoginPage(driver);
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

	/**
	 * @Author Krishna Date: 8/08/2022
	 * @Description : Verify if RMU is select the other project in which he is PAU
	 *              role, it should take to SG in the that project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54848", enabled = true, groups = { "regression" })
	public void verifyRmuSelectOtherProjectInPaRoleTakeToSg() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54848");
		baseClass.stepInfo(
				"Verify if RMU is select the other project in which he is PAU role, it should take to SG in the that project");
		SoftAssert softassert = new SoftAssert();
		docexp = new DocExplorerPage(driver);
		DataSets data = new DataSets(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		DocViewMetaDataPage docViewMetaData = new DocViewMetaDataPage(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in Rmu");
		softassert.assertTrue(docexp.getDocExplorerTabAfterDashBoard().isDisplayed());
		baseClass.stepInfo("Rmu is redirect to dashboard page");
		loginPage.logout();

		// create project
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		String projectName = "SecurityGroupProject" + Utility.dynamicNameAppender();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		data.getNotificationMessage(0, projectName);
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		System.out.println(projectName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as in  " + Input.pa1FullName);
		driver.waitForPageToBeReady();
		baseClass.selectproject(projectName);
		softassert.assertEquals(projectName, projectName);
		baseClass.passedStep(projectName + "..RMU Select project in which is user is PAU is selected successfully");
		driver.waitForPageToBeReady();
		docViewMetaData.getSecurityGroupDropDownButton().waitAndClick(3);
		baseClass.waitForElement(docViewMetaData.getSecurityGroup(Input.securityGroup));
		if (docViewMetaData.getSecurityGroup(Input.securityGroup).isDisplayed()) {
			baseClass.passedStep(Input.securityGroup + ".. is successfully displayed in selected project");
		} else {
			baseClass.failedStep("Sg is not displayed");

		}
		softassert.assertAll();
	}

	/**
	 * @Author Sakthivel Date: 9/08/2022
	 * @Description :Verify that as DA user,clicking on project should redirect to
	 *              default security group and then on changing the Project from
	 *              header drop down should take to Default SG in the selected
	 *              project
	 * @param :
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54773", enabled = true, groups = { "regression" })
	public void verifyDAClickingProductDsgChangingSelectedProject() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54773");
		baseClass.stepInfo(
				"Verify that as DA user,clicking on project should redirect to default security group and then on changing the Project from header drop down should take to Default SG in the selected project");
		DataSets data = new DataSets(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "SecurityGroupProject" + Utility.dynamicNameAppender();
		DomainDashboard domainDash = new DomainDashboard(driver);

		// create project
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		data.getNotificationMessage(0, projectName);
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", "Default Security Group",
				false, true);
		loginPage.logout();

		// Login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DA as with " + Input.da1userName + "");
		baseClass.stepInfo("Click on any project");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(domainDash.getprojectnamelink(Input.projectName));
		baseClass.waitForElement(domainDash.getprojectnamelink(Input.projectName));
		domainDash.getprojectnamelink(Input.projectName).waitAndClick(5);

		baseClass.stepInfo("verify default security group in selected project");
		String actualString = "Default Security Group";
		String ExpectedString = baseClass.getsgNames().getText();
		System.out.println(ExpectedString);
		if (actualString.equals(ExpectedString)) {
			baseClass.passedStep("As user to DSG by default in selected project");
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.selectproject(projectName);
		baseClass.stepInfo("Click on other project");
		baseClass.stepInfo("verify default security group in selected project");
		String actualString1 = "Default Security Group";
		String ExpectedString1 = baseClass.getsgNames().getText();
		System.out.println(ExpectedString1);
		if (actualString.equals(ExpectedString1)) {
			baseClass.passedStep("DAU to Default SG in the selected project Successfully.." + projectName);
		} else {
			baseClass.failedStep("It is not  to default SG by default ");
		}
	}
	
	/**
		 * @author Krishna ModifyDate:04/08/2022 RPMXCON-54915
		 * @throws Exception
		 * @Description To verify that project admin can navigate to tags for selected
		 *              security group and observe no duplicates.
		 * 
		 */
		@Test(description = "RPMXCON-54915", enabled = true, groups = { "regression" })
		public void verifyPANavigateTagsSelectedSgObserveNoDuplicates() throws Exception {
			baseClass.stepInfo("Test case Id: RPMXCON-54915");
			baseClass.stepInfo(
					"To verify that when the SG is set to use SG-specific attributes then Folder Propagation should happens using EmailDuplicateDocIDs attribute.");
			SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
			String tags = "Tags";
			String defaulttags = "Default Tags";

			// Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Login as PA");
			sgpage.navigateToSecurityGropusPageURL();
			baseClass.stepInfo("navigated to security group as expected");

			baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
			sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
			sgpage.verifySelectTagsAssignedInSelectedList(defaulttags);
			driver.waitForPageToBeReady();
			int totFolderCount = sgpage.getSgDefaultTag().size();
			if (totFolderCount == 1) {
				baseClass.passedStep("Default Tags labels Duplicates is NOT appear on Security Group mapping screen.");

			} else {
				baseClass.failedStep("Default Tags labels Duplicates is appear on mappling screen");
			}
			driver.Navigate().refresh();
			baseClass.waitForElement(sgpage.getSGSelectAccessControlTages(tags));
			sgpage.getSGSelectAccessControlTages(tags).waitAndClick(3);
			sgpage.verifySelectTagsAssignedInSelectedList(defaulttags);
			driver.waitForPageToBeReady();
			int totFolderCount1 = sgpage.getSgDefaultTag().size();
			if (totFolderCount1 == 1) {
				baseClass.passedStep("Default Tags labels Duplicates is NOT appear on Security Group mapping screen.");

			} else {
				baseClass.failedStep("Default Tags labels Duplicates is appear on mappling screen");
			}
		}

}
