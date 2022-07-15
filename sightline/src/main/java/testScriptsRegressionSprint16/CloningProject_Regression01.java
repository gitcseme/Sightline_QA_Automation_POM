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
import pageFactory.SessionSearch;
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
		DataSets data;

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
			data = new DataSets(driver);

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
			projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName,"1");
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
				data.getNotificationMessage(0,projectName);
				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.projectIsPresentForAllUsers(Input.pa1userName, "Project Administrator", projectName);
				users.projectIsPresentForAllUsers(Input.rmu1userName, "Review Manager", projectName);
				users.projectIsPresentForAllUsers(Input.rev1userName, "Reviewer", projectName);
				loginPage.logout();


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
		        projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName,"0");
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
				projectPage.selectProjectToBeCopied(projectName, Input.domainName,Input.projectName,"0");
				data.getNotificationMessage(0,projectName);
				
				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
				SecurityGroupsPage securityGroup = new SecurityGroupsPage(driver);
				securityGroup.navigateToSecurityGropusPageURL();
				driver.waitForPageToBeReady();
				securityGroup.validateSecurityGroupsCount();

				loginPage.logout();

			}
			
			/**
			 * @author Mohan.Venugopal Created on : 05/07/2022 Modified On:NA
			 * @description:Verify that when User creates a new Domain Project Using
			 *                     template project without "Assigned Reviewers" then Only
			 *                     assignments with configurations and without Users
			 *                     assigned to assignment are copied to the newly created
			 *                     Project.
			 * @throws AWTException
			 */
			@Test(description = "RPMXCON-54886", enabled = true, groups = { "regression" })
			public void userCreateNewNonDomainUsingAssignmentCreation() throws AWTException, InterruptedException {

				baseClass.stepInfo("Test case Id: RPMXCON-54886");
				baseClass.stepInfo(
						"Verify that when User creates a new Domain Project Using template project without  \"Assigned Reviewers\" then Only assignments with configurations and without Users assigned to assignment are copied to the newly created Project.");
				String projectName = "AssignmentCloning" + Utility.dynamicNameAppender();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

				SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch.basicContentSearch(Input.searchString1);
				sessionSearch.bulkAssign();

				AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
				assignmentsPage.assignmentCreation(projectName, Input.codeFormName);
				assignmentsPage.toggleDisableForAnalyticsPanelMiniDoclistReviewerToApplyRedation();
				loginPage.logout();

				loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
				UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
				ProjectPage projectPage = new ProjectPage(driver);
				projectPage.navigateToProductionPage();
				projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "3");
				data.getNotificationMessage(0, projectName);

				
				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", Input.securityGroup, false, true);
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
				assignmentsPage.navigateToAssignmentsPage();
				assignmentsPage.verifyAssignmentsPageWithOnlyAssignments(projectName);
				loginPage.logout();
				
				

			}
			
			/**
			 * @author Mohan.Venugopal Created on : 05/07/2022 Modified On:NA
			 * @description:Verify that when User creates a new Domain  Project Using template project then corresponding 
			 * Assignments - properties are copied from the source template project to the newly created Project.
			 * @throws AWTException
			 */
			@Test(description = "RPMXCON-54845", enabled = true, groups = { "regression" })
			public void userCreateNewNonDomainUsingAssignmentOnlyCreation() throws AWTException, InterruptedException {

				baseClass.stepInfo("Test case Id: RPMXCON-54845");
				baseClass.stepInfo(
						"Verify that when User creates a new Domain  Project Using template project then corresponding Assignments - properties are copied from the source template project to the newly created Project.");
				String projectName = "AssignmentCloning" + Utility.dynamicNameAppender();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");

				SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch.basicContentSearch(Input.searchString1);
				sessionSearch.bulkAssign();

				AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
				assignmentsPage.assignmentCreation(projectName, Input.codeFormName);
				assignmentsPage.toggleDisableForAnalyticsPanelMiniDoclistReviewerToApplyRedation();
				loginPage.logout();

				loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
				UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
				ProjectPage projectPage = new ProjectPage(driver);
				projectPage.navigateToProductionPage();
				projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "3");
				data.getNotificationMessage(0, projectName);

				
				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.ProjectSelectionForUser(projectName, Input.rmu1FullName, "Review Manager", Input.securityGroup, false, true);
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
				assignmentsPage.navigateToAssignmentsPage();
				assignmentsPage.verifyAssignmentsPageWithOnlyAssignments(projectName);
				loginPage.logout();
				
				

			}
			
			/**
			 * @author Mohan.Venugopal Created on : 05/07/2022 Modified On:NA
			 * @description:Verify that when User creates a new Domain Project Using
			 *                     template project without "Assigned Reviewers" then Only
			 *                     assignments with configurations and without Users
			 *                     assigned to assignment are copied to the newly created
			 *                     Project.
			 * @throws AWTException
			 */
			@Test(description = "RPMXCON-54866", enabled = true, groups = { "regression" })
			public void userCreateNewNonDomainUsingSecurityGroupCreation() throws AWTException, InterruptedException {

				baseClass.stepInfo("Test case Id: RPMXCON-54866");
				baseClass.stepInfo(
						"Verify that when User creates a new Domain Project Using template project then corresponding All security groups and their associated workproduct objects (and project fields)  are copied from the source template project to the newly created Project.");
				String projectName = "SecurityGroupsCloning" + Utility.dynamicNameAppender();

				loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
				UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
				projectPage.navigateToProductionPage();
				projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
				data.getNotificationMessage(0, projectName);

				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,projectName);
				UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
				SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
				securityPage.navigateToSecurityGropusPageURL();
				securityPage.verifyAllFieldsArePresentInSecurityHomePage();
				loginPage.logout();
				
				

			}
			
			/**
			 * @author Mohan.Venugopal Created on : 05/07/2022 Modified On:NA
			 * @description: Verify that when User creates a new Domain Project Using template 
			 * project then corresponding Active Users are copied from the source template project to the newly created Project.
			 * @throws AWTException
			 */
			@Test(description = "RPMXCON-54834", enabled = true, groups = { "regression" })
			public void userCreateNewDomainUsingNewUserCreatedAndCheckThem() throws AWTException, InterruptedException {

				baseClass.stepInfo("Test case Id: RPMXCON-54834");
				baseClass.stepInfo(
						"Verify that when User creates a new Domain Project Using template project then corresponding Active Users are copied from the source template project to the newly created Project.");
				String projectName = "NewUserCheckCloning" + Utility.dynamicNameAppender();

				loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
				UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
				projectPage.navigateToProductionPage();
				projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
				data.getNotificationMessage(0, projectName);
				UserManagement users = new UserManagement(driver);
				users.navigateToUsersPAge();
				users.projectIsPresentForAllUsers(Input.pa1userName, "Project Administrator", projectName);
				users.projectIsPresentForAllUsers(Input.rmu1userName, "Review Manager", projectName);
				users.projectIsPresentForAllUsers(Input.rev1userName, "Reviewer", projectName);
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,projectName);
				UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
				
				baseClass.passedStep("The User PA is mapped with "+projectName+" successfully. User PA is copied with the same role that they have in the template project");
				
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password,projectName);
				UtilityLog.info("User successfully logged into slightline webpage as RMU with " + Input.rmu1userName + "");
				
				baseClass.passedStep("The User RMU is mapped with "+projectName+" successfully. User RMU is copied with the same role that they have in the template project");
				
				loginPage.logout();
				
				loginPage.loginToSightLine(Input.rev1userName, Input.rev1password,projectName);
				UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
				
				baseClass.passedStep("The User Reviewer is mapped with "+projectName+" successfully. User Reviewer is copied with the same role that they have in the template project");
				
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
