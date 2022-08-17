package sightline.cloningProject;

import java.awt.AWTException;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CloningProject_Regression3 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
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
	 * @author Vijaya.Rani ModifyDate:30/06/2022 RPMXCON-54797
	 * @throws InterruptedException 
	 * @Description Verify that when User creates a new Domain Project Using template project then corresponding Coding Form are copied from the source template project to the newly created Project.
	 */
	@Test(description = "RPMXCON-54797",enabled = true, groups = { "regression" })
    public void userCreateNewDomainUsingCodingForm() throws InterruptedException {

        baseClass.stepInfo("Test case Id: RPMXCON-54797");
        baseClass.stepInfo("Verify that when User creates a new Domain Project Using template project then corresponding Coding Form are copied from the source template project to the newly created Project.");
        String projectName = "CodingFormCloneProject"+Utility.dynamicNameAppender();

        loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
        UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
        CodingForm codingForm = new CodingForm(driver);
        codingForm.navigateToCodingFormPage();
        codingForm.validateTotalShowingCountInCF();
        loginPage.logout();

        loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
        UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
        projectPage.navigateToProductionPage();
        projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName02,"0");
        DataSets data = new DataSets(driver);
        data.getNotificationMessage(0,projectName);

        UserManagement users = new UserManagement(driver);
        users.navigateToUsersPAge();
        users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", "Default Security Group", false, true);
        loginPage.logout();

        loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, projectName);
        codingForm.navigateToCodingFormPage();
        codingForm.finalVerificationForCodingForm();
        loginPage.logout();

    }
	
	/**
	 * @author Vijaya.Rani ModifyDate:30/06/2022 RPMXCON-54796
	 * @Description Verify that when User creates a new Domain Project Using template project then corresponding Security Groups 
	 * are copied from the source template project to the newly created Project.
	 */
	@Test(description = "RPMXCON-54796",enabled = true, groups = { "regression" })
	public void userCreatenewDomainUsingSecurityGroups()  {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54796");
		baseClass.stepInfo("Verify that when User creates a new Domain Project Using template project then corresponding Security Groups are copied from the source template project to the newly created Project.");
		String projectName = "SecurityCloneProject"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName02,"0");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0,projectName);
		
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SecurityGroupsPage securityGroup = new SecurityGroupsPage(driver);
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.validateSecurityGroupsCount();

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
