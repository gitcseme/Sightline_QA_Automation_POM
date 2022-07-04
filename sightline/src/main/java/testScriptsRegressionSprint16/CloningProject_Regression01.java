package testScriptsRegressionSprint16;
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


public class CloningProject_Regression01 {
		
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
		 * @author Mohan.Venugopal Created on : 01/07/2022 Modified On:NA
		 * @description: Verify that when User creates a new Domain Project Using template project then corresponding Custom Project Fields are copied from the source template project to the newly created Project.
		 * @throws AWTException
		 */
		@Test(description = "RPMXCON-54795",enabled = true, groups = { "regression" })
		public void userCreateNewDomainUsingCustomProjectFields() throws AWTException, InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-54795");
			baseClass.stepInfo("Verify that when User creates a new Domain Project Using template project then corresponding Custom Project Fields are copied from the source template project to the newly created Project.");
			String projectName = "CustomProjectsFiledFormCloneProject"+Utility.dynamicNameAppender();
			
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
			ProjectFieldsPage projectFieldsPage = new ProjectFieldsPage(driver);
			projectFieldsPage.navigateToProjectFieldsPage();
			projectFieldsPage.verifyCustomFieldsInProjectFieldsList();
			loginPage.logout();
			
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
			ProjectPage projectPage = new ProjectPage(driver);
			projectPage.navigateToProductionPage();
			projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName02,"0");
			DataSets data = new DataSets(driver);
			data.getNotificationMessage(0,projectName);
			
			UserManagement users = new UserManagement(driver);
			users.navigateToUsersPAge();
			users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
			loginPage.logout();
			
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
			projectFieldsPage.navigateToProjectFieldsPage();
			projectFieldsPage.verifyCustomFieldsInProjectFieldsList();
			loginPage.logout();


		}
		
		/**
		 * @author Mohan.Venugopal Created on : 01/07/2022 Modified On:NA
		 * @description: Verify that when User creates a new Domain Project Using template project then corresponding Released Project Fields are copied from the source template project to the newly created Project.
		 * @throws AWTException
		 */
		@Test(description = "RPMXCON-54794",enabled = true, groups = { "regression" })
		public void userCreateNewDomainUsingUnAssignedProjectFields() throws AWTException, InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-54794");
			baseClass.stepInfo("Verify that when User creates a new Domain Project Using template project then corresponding Released Project Fields are copied from the source template project to the newly created Project.");
			String projectName = "UnAssignedProjectsFiledFormCloneProject"+Utility.dynamicNameAppender();
			String fieldName = "Done"+Utility.dynamicNameAppender();
			String fieldsValue = "Not"+Utility.dynamicNameAppender();
			String fieldType = "INT";
			
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
			// Custom Field created with INT DataType
			
	        projectPage.addCustomFieldProjectDataType(fieldName, fieldType);
	        driver.Navigate().refresh();
	        projectPage.addCustomFieldProjectDataType(fieldsValue, fieldType);
	 
	        // Custom Field Assign to SecurityGroup
	        SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
	        securityGroupPage.addProjectFieldtoSG(fieldName);
	        baseClass.stepInfo("Custom meta data field assign to security group");
			loginPage.logout();
			
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
			ProjectPage projectPage = new ProjectPage(driver);
			projectPage.navigateToProductionPage();
			projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName02,"0");
			DataSets data = new DataSets(driver);
			data.getNotificationMessage(0,projectName);
			
			UserManagement users = new UserManagement(driver);
			users.navigateToUsersPAge();
			users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
			loginPage.logout();
			
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
			securityGroupPage.navigateToSecurityGropusPageURL();
			securityGroupPage.verifyUnAssignedInSecurityGroup(fieldsValue);
			securityGroupPage.verifyAssignedInSecurityGroup(fieldName);
			loginPage.logout();


		}
		
		/**
		 * @author Mohan.Venugopal Created on : 01/07/2022 Modified On:NA
		 * @description: Verify that when User creates a new Domain Project Using template project then corresponding Active Project Fields are copied from the source template project to the newly created Project.
		 * @throws AWTException
		 */
		@Test(description = "RPMXCON-54793",enabled = true, groups = { "regression" })
		public void userCreateNewDomainUsingActiveProjectFields() throws AWTException, InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-54793");
			baseClass.stepInfo("Verify that when User creates a new Domain Project Using template project then corresponding Active Project Fields are copied from the source template project to the newly created Project.");
			String projectName = "ActiveProjectFields"+Utility.dynamicNameAppender();
			
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
			ProjectFieldsPage projectFieldsPage = new ProjectFieldsPage(driver);
			projectFieldsPage.navigateToProjectFieldsPage();
			projectFieldsPage.verifyCustomFieldsInProjectFieldsList();
			loginPage.logout();
			
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
			ProjectPage projectPage = new ProjectPage(driver);
			projectPage.navigateToProductionPage();
			projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName02,"0");
			DataSets data = new DataSets(driver);
			data.getNotificationMessage(0,projectName);
			
			UserManagement users = new UserManagement(driver);
			users.navigateToUsersPAge();
			users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
			loginPage.logout();
			
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
			projectFieldsPage.navigateToProjectFieldsPage();
			projectFieldsPage.verifyCustomFieldsInProjectFieldsList();
			loginPage.logout();


		}
		/**
		 * @author Mohan.Venugopal Created on : 01/07/2022 Modified On:NA
		 * @description: Verify that when User creates a new Domain  Project Using template project then corresponding Active Users are automatically active in the newly created Project.
		 * @throws AWTException
		 */
		@Test(description = "RPMXCON-54842",enabled = true, groups = { "regression" })
		public void userCreateNewDomainUsingNewUserCreated() throws AWTException, InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-54842");
			baseClass.stepInfo("Verify that when User creates a new Domain  Project Using template project then corresponding Active Users are automatically active in the newly created Project.");
			String projectName = "NewUserCloning"+Utility.dynamicNameAppender();
			
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
			projectPage.navigateToProductionPage();
			projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName02,"1");
			DataSets data = new DataSets(driver);
			data.getNotificationMessage(0,projectName);
			UserManagement users = new UserManagement(driver);
			users.navigateToUsersPAge();
			users.projectIsPresentForAllUsers(Input.pa1userName, "Project Administrator", projectName);
			users.projectIsPresentForAllUsers(Input.rmu1userName, "Review Manager", projectName);
			users.projectIsPresentForAllUsers(Input.rev1userName, "Reviewer", projectName);
			loginPage.logout();


		}
		
		/**
		 * @author Mohan.Venugopal Created on : 01/07/2022 Modified On:NA
		 * @description:Verify that when User creates a new Non-domain Project Using template project then corresponding Active Users are automatically active in the newly created Project.
		 * @throws AWTException
		 */	
			@Test(description = "RPMXCON-54925",enabled = true, groups = { "regression" })
			public void userCreateNewNonDomainUsingNewUserCreated() throws AWTException, InterruptedException {
				
				baseClass.stepInfo("Test case Id: RPMXCON-54925");
				baseClass.stepInfo("Verify that when User creates a new Non-domain Project Using template project then corresponding Active Users are automatically active in the newly created Project.");
				String projectName = "NewUserCloning"+Utility.dynamicNameAppender();
				
				loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
				UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
				projectPage.navigateToProductionPage();
				projectPage.selectProjectToBeCopiedNonDomain(projectName, "Indium_NonDomain","Non_Domain1","86423","1");
				DataSets data = new DataSets(driver);
				data.getNotificationMessage(0,projectName);
				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.projectIsPresentForAllUsers(Input.pa1userName, "Project Administrator", projectName);
				users.projectIsPresentForAllUsers(Input.rmu1userName, "Review Manager", projectName);
				users.projectIsPresentForAllUsers(Input.rev1userName, "Reviewer", projectName);
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
