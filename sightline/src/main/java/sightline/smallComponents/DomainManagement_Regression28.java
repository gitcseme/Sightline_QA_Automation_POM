package sightline.smallComponents;

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
import pageFactory.BaseClass;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression28 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard domainDashboard;

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
		domainDashboard = new DomainDashboard(driver);

	}
	
	/**
	 * @author  Date:NA ModifyDate:NA Testcase No:RPMXCON-52862
	 * @Description:Verify that after impersonation domain drop down in the header
	 *                     should be updated to reflect selected domain
	 **/
	@Test(description = "RPMXCON-52862", groups = { "regression" })
	public void verifyingImpersonatingSAToDAHeaderUpdatedToReflect() throws InterruptedException {

		DomainDashboard dash = new DomainDashboard(driver);
		baseClass.stepInfo("RPMXCON-52862");
		baseClass.stepInfo(
				"Verify that after impersonation domain drop down in the header should be updated to reflect selected domain");

		// login as SAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		baseClass.stepInfo("Selecting domain administrator and select domain");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getChangeRole());
		baseClass.getChangeRole().waitAndClick(5);
		baseClass.waitForElement(baseClass.getSelectRole());
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.waitForElement(baseClass.getAvlDomain());

		baseClass.stepInfo("verifying domain dropdown is displayed");
		baseClass.elementDisplayCheck(baseClass.getAvlDomain());

		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.stepInfo(Input.domainName + " domain selected in dropdown");
		System.out.println(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(baseClass.getSaveChangeRole());
		baseClass.getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersonated from SA to DA");

		baseClass.waitTime(5);
		baseClass.stepInfo("verifying Selected Domain ");
		if (dash.getCurrentDomainValue(Input.domainName).isDisplayed()) {
			baseClass.passedStep("User navigates domain drop down " + Input.domainName
					+ "  in the header has been updated to reflect selected domain as expected");
		} else {
			baseClass.failedStep("SA is not impersonated as DA and not navigates to domain  page");
		}

		loginPage.logout();

	}

	/**
	 * @author  Date:NA ModifyDate:NA Testcase No:RPMXCON-52984
	 * @Description:To verify that System Admin can select the users and unassigned
	 *                 for a selected project successfully.
	 **/
	@Test(description = "RPMXCON-52984", groups = { "regression" })
	public void verifySAUserUnAssignedSelectedProject() throws InterruptedException {

		UserManagement user = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		String firstname = "PA";
		String lastname = "consilio";
		baseClass.stepInfo("RPMXCON-52984");
		baseClass.stepInfo(
				"To verify that System Admin can select the users and  unassigned for a selected project successfully.");

		// login as SAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		user.UnAssignUserToProject(Input.largeVolDataProject, Input.ProjectAdministrator, Input.pa2FullName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("Login as Pa");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getProjectNames());
		baseClass.getProjectNames().waitAndClick(3);
		baseClass.waitTime(5);
		softassert.assertFalse(baseClass.getSelectProject(Input.largeVolDataProject).isElementAvailable(10));
		baseClass.passedStep(Input.largeVolDataProject + "  Unassigned project is not displayed");
		softassert.assertAll();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.navigateToUsersPAge();
		user.createUser(firstname, lastname, "Project Administrator", Input.pa2userName, null,
				Input.largeVolDataProject);
		loginPage.logout();
	}
	
	/**
	 * Author :  TestCase Id:RPMXCON-52958 Description: Validate modifying
	 * all editable field values and save changes for a non-domain project by System
	 * Admin
	 */
	@Test(description = "RPMXCON-52958", enabled = true, groups = { "regression" })
	public void verifyAllModifiedValueNonDomainProject() throws Exception {

		ProjectPage project = new ProjectPage(driver);
		String projectName = "Project" + Utility.dynamicNameAppender();
		String hcode = "hcode" + Utility.dynamicNameAppender();
		String engineType = "NUIX";
		String newProjectName = "NewProject" + Utility.dynamicNameAppender();
		String newFirm = "New" + Utility.dynamicNameAppender();
		String newCorpClient = "NewCrp" + Utility.dynamicNameAppender();
		String newhcode = "newhcode" + Utility.dynamicNameAppender();
		String projFolder = "NewAutomation";
		String ingFolder = "NewAutomation";
		String productionFolder = "NewAutomation";
		String uptdNoOfDocs = "10000";

		baseClass.stepInfo("RPMXCON-52958");
		baseClass.stepInfo(
				"Validate modifying all editable field values and save changes for a non-domain project by System Admin");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		// Add project
		project.navigateToProductionPage();
		project.AddNonDomainProject(projectName, hcode, engineType);
		driver.scrollPageToTop();

		// update project
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(project.getProjectName());
		project.getProjectName().SendKeys(newProjectName);
		baseClass.waitForElement(project.getFirmTextBox());
		project.getFirmTextBox().SendKeys(newFirm);
		baseClass.waitForElement(project.getCorpClientTextBox());
		project.getCorpClientTextBox().SendKeys(newCorpClient);
		baseClass.waitForElement(project.getHCode());
		project.getHCode().SendKeys(newhcode);
		baseClass.waitForElement(project.getProjectFolder());
		project.getProjectFolder().Clear();
		project.getProjectFolder().SendKeys(projFolder);
		baseClass.waitForElement(project.getIngestionFolder());
		project.getIngestionFolder().Clear();
		project.getIngestionFolder().SendKeys(ingFolder);
		baseClass.waitForElement(project.getProductionFolder());
		project.getProductionFolder().Clear();
		project.getProductionFolder().SendKeys(productionFolder);
		driver.scrollPageToTop();
		baseClass.waitForElement(project.getAddProject_SettingsTab());
		project.getAddProject_SettingsTab().waitAndClick(5);
		baseClass.waitForElement(project.getNoOfDocuments());
		project.getNoOfDocuments().SendKeys(uptdNoOfDocs);
		baseClass.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifySuccessMessage("Project updated successfully");

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(newProjectName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(project.getProjectName());
		String act1 = project.getProjectName().Value();
		baseClass.waitForElement(project.getFirmTextBox());
		String act2 = project.getFirmTextBox().Value();
		baseClass.waitForElement(project.getCorpClientTextBox());
		baseClass.waitForElement(project.getHCode());
		String act8 = project.getHCode().Value();
		String act3 = project.getCorpClientTextBox().Value();
		baseClass.waitForElement(project.getProjectFolder());
		String act4 = project.getProjectFolder().Value();
		baseClass.waitForElement(project.getIngestionFolder());
		String act5 = project.getIngestionFolder().Value();
		baseClass.waitForElement(project.getProductionFolder());
		String act6 = project.getProductionFolder().Value();
		driver.scrollPageToTop();
		baseClass.waitForElement(project.getAddProject_SettingsTab());
		project.getAddProject_SettingsTab().waitAndClick(5);
		baseClass.waitForElement(project.getNoOfDocuments());
		String act7 = project.getNoOfDocuments().Value();

		// verify update project
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(act1, newProjectName);
		asserts.assertEquals(act2, newFirm);
		asserts.assertEquals(act3, newCorpClient);
		asserts.assertEquals(act4, projFolder);
		asserts.assertEquals(act5, ingFolder);
		asserts.assertEquals(act6, productionFolder);
		asserts.assertEquals(act7, uptdNoOfDocs);
		asserts.assertEquals(act8, newhcode);
		asserts.assertAll();
		baseClass.passedStep(
				"Validated - modifying all editable updated field values and save changes folder names has been updated to reflect the change successfully ");
		baseClass.passedStep(" non domain project by System Admin update changes has been loaded as expected");
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
		System.out.println("******TEST CASES EXECUTED******");

	}

}
