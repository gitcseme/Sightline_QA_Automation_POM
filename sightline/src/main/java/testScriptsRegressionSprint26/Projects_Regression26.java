package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

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
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression26 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PROJECTS EXECUTED******");

	}

	/**
	 * @author NA Testcase No:RPMXCON-56177
	 * @Description:Verify if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays
	 **/
	@Test(description = "RPMXCON-56177", enabled = true, groups = { "regression" })
	public void verifyClientEditDomainToNonDomain() throws Exception {
		
		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "c"+ Utility.dynamicNameAppender();
		String shrtName = Utility.randomCharacterAppender(5);
		String type ="Domain";
		String hcode = "hcode" + Utility.dynamicNameAppender();
		
		base.stepInfo("RPMXCON - 56177");
		base.stepInfo("To Verify if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " + Input.sa1userName);
		
		ProjectPage project = new ProjectPage(driver);
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient(clientName, shrtName, type);
		
		ClientsPage client = new ClientsPage(driver);
		client.navigateToClientPage();
		driver.waitForPageToBeReady();
		client.filterClient(clientName);	
		base.waitForElement(client.getClientEditBtn(clientName));
		client.getClientEditBtn(clientName).waitAndClick(5);	
		base.waitForElement(project.getSelectEntity());
		project.getSelectEntity().selectFromDropdown().selectByVisibleText("Not a Domain");	
		base.waitForElement(project.getClientNameSaveBtn());
		project.getClientNameSaveBtn().waitAndClick(10);
		
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();		
		project.verifyProcessingEngineSectionCtrProj(projectName, clientName, hcode);
		
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.filterTheProject(projectName);
		driver.waitForPageToBeReady();
		base.waitForElement(project.getEditProject(projectName));
		if(project.getEditProject(projectName).isElementAvailable(10)){
			base.passedStep("Project Created Successfully As Expected ");
		} else {
			base.failedStep("Project Not Created Successfully");
		}
		base.passedStep("Verified - if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays");
		loginPage.logout();
	}


	/**
	 * Author :Arunkumar date: 17/11/2022 TestCase Id:RPMXCON-55959 Description
	 * :Verify that default value appears in "Initial Size of Project Database"
	 * field on Create Client page.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55959", enabled = true, groups = { "regression" })
	public void verifyInitialSizeFieldOnClientPage() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-55959");
		base.stepInfo("Verify default value appears in 'Initial Size of Project Database' field on Create Client page");

		// Login as SA and verify
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as SA");
		base.stepInfo("Navigate to manage-clients");
		projects.navigateToClientFromHomePage();
		base.stepInfo("click on add new client");
		base.waitForElement(projects.getAddNewClient());
		projects.getAddNewClient().waitAndClick(10);
		base.stepInfo("select client type as 'domain'");
		base.waitForElement(projects.getSelectEntity());
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");
		ClientsPage client = new ClientsPage(driver);
		client.verifyDefaultSizeAndAvailableOptions("Medium");
		base.passedStep("All the expected values available in project database field");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 17/11/2022 TestCase Id:RPMXCON-55591 Description :To
	 * verify that only Sys Admin can create a New Project_PA
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55591", enabled = true, groups = { "regression" })
	public void verifyProjectCreationAccessForPA() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-55591");
		base.stepInfo("To verify that only Sys Admin can create a New Project_PA");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as PA");
		base.stepInfo("verify add new project option as PA user");
		projects.verifyNavigatingToProjectCreationPageAsPA();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 17/11/2022 TestCase Id:RPMXCON-56193 Description
	 * :Verify functionality if user cancel the Domain Project creation
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56193", enabled = true, groups = { "regression" })
	public void verifyIfUserCancelsProjectCreation() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-56193");
		base.stepInfo("Verify functionality if user cancel the Domain Project creation");
		String projectName = "QaProject" + Utility.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as SA");
		base.stepInfo("Navigate to manage-project section");
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "Project/Project", "user on project page", "not on project page");
		base.stepInfo("click add project button and enter details");
		base.waitForElement(projects.getAddProjectBtn());
		projects.getAddProjectBtn().waitAndClick(10);
		base.waitForElement(projects.getProjectName());
		projects.getProjectName().SendKeys(projectName);
		base.waitForElement(projects.getSelectEntityType());
		projects.getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
		base.waitForElement(projects.getSelectClientName());
		projects.getSelectClientName().selectFromDropdown().selectByVisibleText(Input.domainName);
		base.stepInfo("click on cancel button");
		driver.scrollingToBottomofAPage();
		base.waitForElement(projects.getCancelButton());
		projects.getCancelButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(projects.getSearchProjectName());
		// verify project creation and redirected url
		base.verifyUrlLanding(Input.url + "Project/Project", "Redirected to project page",
				"not redirected to project page");
		base.waitForElement(projects.getSearchProjectName());
		projects.getSearchProjectName().SendKeys(projectName);
		base.waitForElement(projects.getProjectFilterButton());
		base.waitTillElemetToBeClickable(projects.getProjectFilterButton());
		projects.getProjectFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (!(projects.getEditProject(projectName).isElementAvailable(10))) {
			base.passedStep("Project not created after clicking cancel button");
		} else {
			base.failedStep("project created even after clicking the cancel button");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.rani date: 24/11/2022 TestCase Id:RPMXCON-55590 Description
	 * :To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab
	 * while editing existing project.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55590", enabled = true, groups = { "regression" })
	public void verifySADedupingCheckBoxInSettingsTabExistingProject() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-55590");
		base.stepInfo(
				"To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab while editing existing project.");
		ProjectPage projectPage = new ProjectPage(driver);
		DataSets data = new DataSets(driver);
		String projectName = "ExistingProject" + Utility.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		data.getNotificationMessage(0, projectName);

		// check the Deduping checkbox
		driver.waitForPageToBeReady();
		projectPage.filterTheProject(projectName);
		base.waitForElement(projectPage.getEditProject(projectName));
		projectPage.getEditProject(projectName).Click();
		driver.waitForPageToBeReady();
		projectPage.getAddProject_SettingsTab().waitAndClick(5);
		base.waitForElement(projectPage.getDedupingCheckbox());
		projectPage.getDedupingCheckbox().waitAndClick(10);
		if (projectPage.getInstanceLevelRadioBtn().Selected()) {
			base.passedStep("Deduping Checkbox is selected");
		} else {
			base.failedStep("Deduping Checkbox is not selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		base.VerifySuccessMessage("Project updated successfully");
		base.passedStep("Deduping checkbox on for the project.  Make sure that project is saved.");

		// Do not Check Deduping checkbox
		projectPage.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projectPage.filterTheProject(projectName);
		base.waitForElement(projectPage.getEditProject(projectName));
		projectPage.getEditProject(projectName).Click();
		driver.waitForPageToBeReady();
		projectPage.getAddProject_SettingsTab().waitAndClick(5);
		base.waitForElement(projectPage.getDedupingCheckbox());
		projectPage.getDedupingCheckbox().waitAndClick(10);
		if (!projectPage.getInstanceLevelRadioBtn().Selected()) {
			base.passedStep("Deduping Checkbox is not selected");
		} else {
			base.failedStep("Deduping Checkbox is selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		base.VerifySuccessMessage("Project updated successfully");
		base.passedStep("Deduping checkbox off for the project.  Make sure that project is saved.");
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.rani date: 24/11/2022 TestCase Id:RPMXCON-55589 Description
	 * :To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab
	 * while creating new project.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55589", enabled = true, groups = { "regression" })
	public void verifySADedupingCheckBoxInSettingsTabNewProject() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-55589");
		base.stepInfo(
				"To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab while creating new project.");
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "NewProject" + Utility.dynamicNameAppender();
		String domainName = "Domain";

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.getAddProjectBtn().Click();
		projectPage.getProjectName().SendKeys(projectName);
		projectPage.getSelectEntityType().selectFromDropdown().selectByVisibleText(domainName);
		projectPage.getSelectEntity().selectFromDropdown().selectByVisibleText(Input.domainName);
		projectPage.getCopyProjectName().selectFromDropdown().selectByVisibleText(Input.projectName);
		// check the Deduping checkbox
		driver.waitForPageToBeReady();
		projectPage.getAddProject_SettingsTab().waitAndClick(5);
		projectPage.getNoOfDocuments().SendKeys("999");
		base.waitForElement(projectPage.getDedupingCheckbox());
		projectPage.getDedupingCheckbox().waitAndClick(10);
		if (projectPage.getInstanceLevelRadioBtn().Selected()) {
			base.passedStep("Deduping Checkbox is selected");
		} else {
			base.failedStep("Deduping Checkbox is not selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		base.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");
		base.passedStep("Deduping checkbox on for the project.  Make sure that project is saved.");

		// Do not Check Deduping checkbox
		projectPage.navigateToProductionPage();
		projectPage.getAddProjectBtn().Click();
		projectPage.getProjectName().SendKeys(projectName);
		projectPage.getSelectEntityType().selectFromDropdown().selectByVisibleText(domainName);
		projectPage.getSelectEntity().selectFromDropdown().selectByVisibleText(Input.domainName);
		projectPage.getCopyProjectName().selectFromDropdown().selectByVisibleText(Input.projectName);
		// check the Deduping checkbox
		driver.waitForPageToBeReady();
		projectPage.getAddProject_SettingsTab().waitAndClick(5);
		projectPage.getNoOfDocuments().SendKeys("999");
		if (!projectPage.getInstanceLevelRadioBtn().Selected()) {
			base.passedStep("Deduping Checkbox is not selected");
		} else {
			base.failedStep("Deduping Checkbox is selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		base.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");
		base.passedStep("Deduping checkbox off for the project.  Make sure that project is saved.");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-53060
	 * @Description: Verify Project creation with domain which is having more than
	 *               20 char for Non domain client
	 **/
	@Test(description = "RPMXCON-53060", enabled = true, groups = { "regression" })
	public void VerifyProjCreation20CharNonDomain() throws Exception {
		String client = "" + Utility.randomCharacterAppender(20);
		String shrt = "" + Utility.randomCharacterAppender(4);
		String projectName = "Project" + Utility.dynamicNameAppender();
		String engineType = "ICE";
		ProjectPage project = new ProjectPage(driver);

		base.stepInfo("RPMXCON-53060");
		base.stepInfo("Verify Project creation with domain which is having more than 20 char for Non domain client");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient_NonDomainProject(client, shrt, "Not a Domain");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddNonDomainProjWithEngineType(projectName, client, shrt, engineType);
		driver.waitForPageToBeReady();
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.filterTheProject(projectName);
		base.waitForElement(project.getEditProject(projectName));
		driver.waitForPageToBeReady();
        if (project.getEditProject(projectName).isElementAvailable(10)) {
            base.passedStep("Project Created Successfully");
        } else {
            base.failedStep("Project Not Created");
        }
		base.passedStep(
				"Verified - Project creation with domain which is having more than 20 char for Non domain client");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55917
	 * @Description:Validate minimum length value while creating a Non-Domain Project
	 **/
	@Test(description ="RPMXCON-55917",enabled = true, groups = { "regression" })
	public void validateMinLengthValue() throws Exception {		
		ProjectPage project = new ProjectPage(driver);
		String minLengthWithSP = "@#$%&";
		String minLengAlphaNum = "ABCDE";
		String minLenMorethan12 = "100";
		String minLength = "10";
		String expMsg = "The specified minimum length cannot be greater than 12.";
		String projectName = "Project" + Utility.dynamicNameAppender();
		
		base.stepInfo("RPMXCON-55917");
		base.stepInfo("To Validate minimum length value while creating a Non-Domain Project");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " + Input.sa1userName);
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddDomainProjectDetailsWithoutSave(projectName, Input.domainName);
		driver.scrollPageToTop();
		base.waitForElement(project.getAddProject_SettingsTab());
		project.getAddProject_SettingsTab().waitAndClick(5);
		base.waitForElement(project.getMinLengthValue());
		project.getMinLengthValue().SendKeys(minLengthWithSP);
		base.waitForElement(project.getMinLengthValue());
		String value1 = project.getMinLengthValue().Value();
		base.textCompareEquals(value1, "", "Appropriate not allowed to enter special characters", "Appropriate allowed to enter special characters");
	    
		base.waitForElement(project.getMinLengthValue());
		project.getMinLengthValue().SendKeys(minLengAlphaNum);
		base.waitForElement(project.getMinLengthValue());
		String value2 = project.getMinLengthValue().Value();
		base.textCompareEquals(value2, "", "Appropriate not allowed to enter AlphaNum characters", "Appropriate allowed to enter AlphaNum characters");
	    
		base.waitForElement(project.getMinLengthValue());
		project.getMinLengthValue().SendKeys(minLenMorethan12);
		base.waitForElement(project.getButtonSaveProject());
		project.getButtonSaveProject().waitAndClick(10);
		base.waitForElement(base.getSuccessMsgHeader());
		base.VerifyErrorMessage(expMsg);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
	
		project.AddDomainProjectDetailsWithoutSave(projectName, Input.domainName);
		driver.scrollPageToTop();
		base.waitForElement(project.getAddProject_SettingsTab());
		project.getAddProject_SettingsTab().waitAndClick(5);
		base.waitForElement(project.getNoOfDocuments());
		project.getNoOfDocuments().SendKeys("10000");
		base.waitForElement(project.getMinLengthValue());
		project.getMinLengthValue().SendKeys(minLength);
		project.saveProjectAndVerify();
		base.passedStep("Validated - minimum length value while creating a Non-Domain Project");
		loginPage.logout();
	}
	
	

	/**
	 * @author sowndarya Testcase No:RPMXCON-55872
	 * @Description:Verify After click on "Production Folder" option for production
	 *                     a text box prompting for path label , actual path
	 **/
	@Test(description = "RPMXCON-55872", enabled = true, groups = { "regression" })
	public void verifyProdFolderPath() throws Exception {
		base.stepInfo("RPMXCON-55872");
		base.stepInfo(
				"Verify After click on Production Folder option for production a text box prompting for path label , actual path");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		base.stepInfo("navigating to projects page");
		projects.navigateToProductionPage();

		base.stepInfo("navigating to client page");
		projects.navigateToClientFromHomePage();

		base.stepInfo("Adding new client");
		projects.addNewClient(clientName, shortName, "Domain");

		base.stepInfo("Creating new domain project");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		projects.editProject(projectName);

		driver.scrollingToBottomofAPage();
		base.waitForElement(projects.getProductionFolder());
		String actual = projects.getProductionFolder().GetAttribute("value");
		System.out.println(actual);
		String expected = clientName + "\\" + projectName;
		softAssertion.assertEquals(actual, expected);
		softAssertion.assertAll();
		
		projects.getProductionFolder().Clear();
		projects.getProductionFolder().SendKeys("Automation");

		String editedPath = projects.getProductionFolder().getText();
		System.out.println(editedPath);

		projects.getButtonSaveProject().waitAndClick(10);
		base.VerifySuccessMessage("Project updated successfully");
	}
	
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-55575
	 * @Description:To verify the Project-Setting TAB details.
	 **/
	@Test(description = "RPMXCON-55575", enabled = true, groups = { "regression" })
	public void verifyProjectSettingTab() throws Exception {
		base.stepInfo("RPMXCON-55575");
		base.stepInfo("To verify the Project-Setting TAB details.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		base.stepInfo("navigating to projects page");
		projects.navigateToProductionPage();

		if (!projects.getDomainEditBtn().isDisplayed()) {

			base.stepInfo("navigating to client page");
			projects.navigateToClientFromHomePage();

			base.stepInfo("Adding new client");
			projects.addNewClient(clientName, shortName, "Domain");

			base.stepInfo("Creating new domain project");
			projects.navigateToProductionPage();
			projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
			projects.editProject(projectName);
		} else {
			base.stepInfo("Edit existing domain project");
			driver.waitForPageToBeReady();
			projects.getDomainEditBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		base.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(10);
		
		base.stepInfo("verify Setting tab.");
		projects.getNoOfDocuments().isDisplayed();
		projects.getDocIdPrefix().isDisplayed();
		projects.getDocIdSuffi().isDisplayed();
		projects.getMinLengthValue().isDisplayed();
		projects.getTxtDedupinglevel().isDisplayed();
		projects.getTxtAnalyticsEngine().isDisplayed();
		base.passedStep("Following details are displayed->\r\n"
				+ "Max. Number of Documents in Project: * \r\n"
				+ "DocID Format: \r\n"
				+ "Deduping Performed in Processing: \r\n"
				+ "Analytics Engine: ");
	}
}
