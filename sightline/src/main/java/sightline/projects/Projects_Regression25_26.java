package sightline.projects;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

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
import pageFactory.Dashboard;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression25_26 {

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

	/**
	 * @author NA Testcase No:RPMXCON-53058
	 * @Description:Verify Domain creation with long domain name(less than 255 char)
	 *                     for Non domain client
	 **/
	@Test(description = "RPMXCON-53058", enabled = true, groups = { "regression" })
	public void verifyLongDomainNameForNonDomainClient() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		base = new BaseClass(driver);
		ClientsPage client = new ClientsPage(driver);

		String nameSpChar = "Client " + "-" + " " + "_" + Utility.randomCharacterAppender(100);
		String shortName = "C" + Utility.randomCharacterAppender(10);
		String type = "Not a Domain";

		base.stepInfo("RPMXCON-53058");
		base.stepInfo("To Verify Domain creation with long domain name(less than 255 char) for Non domain client");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient_NonDomainProject(nameSpChar, shortName, type);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		client.filterClient(nameSpChar);

		base.waitForElement(client.getClientEditBtn(nameSpChar));
		if (client.getClientEditBtn(nameSpChar).isElementAvailable(5)) {
			base.passedStep("Domain Saved Successfully..");
		} else {
			base.failedStep("Domain Not Saved Successfully...");
		}
		base.passedStep("Verified - Domain creation with long domain name(less than 255 char) for Non domain client");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-53059
	 * @Description:Verify Project creation with domain which is having more than 20
	 *                     char for domain client
	 **/
	@Test(description = "RPMXCON-53059", enabled = true, groups = { "regression" })
	public void verifyProjCreationWithDomainClient20Char() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		base = new BaseClass(driver);

		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.randomCharacterAppender(25);
		String shrtName = "C" + Utility.randomCharacterAppender(10);
		String type = "Domain";

		base.stepInfo("RPMXCON-53059");
		base.stepInfo("Verify Project creation with domain which is having more than 20 char for domain client");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient(clientName, shrtName, type);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddDomainProject(projectName, clientName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		project.filterTheProject(projectName);

		base.waitForElement(project.getEditProject(projectName));
		if (project.getEditProject(projectName).isElementAvailable(10)) {
			base.passedStep("Project Created Successfully...");
		} else {
			base.failedStep("Project Not Created...");
		}
		base.passedStep("Verified - Project creation with domain which is having more than 20 char for domain client");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56173
	 * @Description:Verify Processing Engine Type when user create a domain client
	 *                     on Create Client.
	 **/
	@Test(description = "RPMXCON-56173", enabled = true, groups = { "regression" })
	public void verifyProccEngineClientWhenCrtDomain() throws Exception {

		ProjectPage project = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);
		base = new BaseClass(driver);

		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);
		String type = "Domain";
		String expResult = "Default ICE Instance";

		base.stepInfo("RPMXCON-56173");
		base.stepInfo("To Verify 'Processing Engine Type' when user create a domain client on 'Create Client'");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		base.waitForElement(project.getAddNewClient());
		project.getAddNewClient().ScrollTo();
		project.getAddNewClient().waitAndClick(10);

		base.waitForElement(project.getClientName());
		project.getClientName().SendKeys(clientName);

		base.waitForElement(project.getClientShortName());
		project.getClientShortName().SendKeys(shortName);

		base.waitForElement(project.getSelectEntity());
		project.getSelectEntity().waitAndClick(10);
		project.getSelectEntity().selectFromDropdown().selectByVisibleText(type);

		base.waitForElement(client.getProcessingEngineType());
		String actualResult = client.getProcessingEngineType().selectFromDropdown().getFirstSelectedOption().getText();
		System.out.println(actualResult);
		base.stepInfo(actualResult + " Selected in ProccessingEngineType Drop Down");
		if (actualResult.equals(expResult)) {
			base.passedStep("Default Selection in processingEngineType As Expected..");
		} else {
			base.failedStep("Default Selection in processingEngineType Not As Expected..");
		}
		base.passedStep("Verified - 'Processing Engine Type' when user create a domain client on 'Create Client'");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56178
	 * @Description:Verify if Client Type is edited from Non-Domain to Domain then
	 *                     Processing Engine section
	 **/
	@Test(description = "RPMXCON-56178", enabled = true, groups = { "regression" })
	public void verifyProccEngineAfterEditClient() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(10);
		String domainName = "D" + Utility.dynamicRandomNumberAppender();
		String type1 = "Not a Domain";
		String type2 = "Domain";

		base.stepInfo("RPMXCON-56178");
		base.stepInfo("To Verify if Client Type is edited from Non-Domain to Domain then Processing Engine section"
				+ " should displays");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		project.addNewClient_NonDomainProject(clientName, shortName, type1);

		driver.Navigate().refresh();
		client.filterClient(clientName);
		base.waitForElement(client.getClientEditBtn(clientName));
		client.getClientEditBtn(clientName).waitAndClick(5);
		base.stepInfo("Edit Client PopUp Opened for " + clientName);

		base.waitForElement(project.getSelectEntity());
		project.getSelectEntity().waitAndClick(10);
		project.getSelectEntity().selectFromDropdown().selectByVisibleText(type2);

		driver.waitForPageToBeReady();
		base.waitForElement(project.getDomainName());
		project.getDomainName().SendKeys(domainName);
		driver.scrollingToBottomofAPage();

		base.waitForElement(project.getProjectDBServerDropdown());
		project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);

		base.waitForElement(project.getProjectServerPath());
		project.getProjectServerPath().waitAndClick(10);

		base.waitForElement(project.getIngestionserverpath());
		project.getIngestionserverpath().waitAndClick(10);

		base.waitForElement(project.getProductionserverpath());
		project.getProductionserverpath().waitAndClick(10);

		base.waitForElement(project.getClientNameSaveBtn());
		project.getClientNameSaveBtn().waitAndClick(10);

		base.waitTime(2);
		base.VerifySuccessMessage("The client details were updated successfully");
		base.CloseSuccessMsgpopup();

		project.verifyProcessingEngineSection(projectName, clientName);
		base.passedStep("Verified - if Client Type is edited from Non-Domain to Domain then Processing Engine section "
				+ "should displays");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-55869
	 * @Description:Verify "Ingestion Folder" option is present for ingestion on
	 *                     Create Project" input screen
	 **/
	@Test(description = "RPMXCON-55869", enabled = true, groups = { "regression" })
	public void verifyIngestionFolder() throws Exception {

		base.stepInfo("RPMXCON-56178");
		base.stepInfo("Verify Ingestion Folder option is present for ingestion on Create Project\" input screen");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		ClientsPage client = new ClientsPage(driver);
		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);
		String expected = clientName + "\\" + projectName;

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		base.stepInfo("Domain project is created");

		projects.editProject(projectName);
		base.stepInfo("created Domain project is filtered and opened");

		if (projects.getIngestionFolder().isElementAvailable(5)) {
			base.waitForElement(projects.getIngestionFolder());
			String actual = projects.getIngestionFolder().GetAttribute("value");
			softAssertion.assertEquals(actual, expected);
			softAssertion.assertAll();
			System.out.println("Ingestion Folder has" + actual);
			base.passedStep("Ingestion folder is present");
		}

	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-55871
	 * @Description:Verify After click on "Ingestion Folder" option for ingestion a
	 *                     text box appear prompting for path label , actual path
	 **/
	@Test(description = "RPMXCON-55871", enabled = true, groups = { "regression" })
	public void verifyEditIngestionFolder() throws Exception {

		base.stepInfo("RPMXCON-56178");
		base.stepInfo(
				"Verify After click on \"Ingestion Folder\" option for ingestion a text box appear prompting for path label , actual path");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		base.stepInfo("Domain project is created");

		projects.editProject(projectName);
		base.stepInfo("created Domain project is filtered and opened");

		base.waitForElement(projects.getIngestionFolder());
		projects.getIngestionFolder().Clear();
		projects.getIngestionFolder().SendKeys("Automation");
		base.stepInfo("Edited the path and saved");

		driver.scrollingToBottomofAPage();
		base.waitForElement(projects.getButtonSaveProject());
		projects.getButtonSaveProject().waitAndClick(10);

		base.passedStep("Edited path saved successfully");

	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-55875
	 * @Description:Add new path using "Ingestion Folder" option for ingestion while
	 *                  editng the existing project
	 **/
	@Test(description = "RPMXCON-55875", enabled = true, groups = { "regression" })
	public void verifyNewPathInIngestionFolder() throws Exception {

		base.stepInfo("RPMXCON-56178");
		base.stepInfo("Add new path using Ingestion Folder option for ingestion while editng the existing project");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);
		String newPath = "Automation";

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		base.stepInfo("Domain project is created");

		projects.editProject(projectName);
		base.stepInfo("created Domain project is filtered and opened");

		base.waitForElement(projects.getIngestionFolder());
		projects.getIngestionFolder().Clear();
		projects.getIngestionFolder().SendKeys(newPath);
		base.stepInfo("Edited the path and saved");

		driver.scrollingToBottomofAPage();
		base.waitForElement(projects.getButtonSaveProject());
		projects.getButtonSaveProject().waitAndClick(10);

		base.passedStep("Edited path saved successfully");

		projects.editProject(projectName);
		base.stepInfo("Edited project is filtered and opened again");

		base.waitForElement(projects.getIngestionFolder());
		String actual = projects.getIngestionFolder().GetAttribute("value");

		softAssertion.assertEquals(actual, newPath);
		softAssertion.assertAll();
		base.passedStep("Newly added path is available ");

	}

	/**
	 * @author NA Testcase No:RPMXCON-56192
	 * @Description:Verify when editing a non-domain project, the whole section
	 *                     'Processing Setting' will present as read-only if 'NUIX'
	 *                     as the processing engine
	 **/
	@Test(description = "RPMXCON-56192", enabled = true, groups = { "regression" })
	public void verifyEditiNonDomProjProcTypeReadOnlyNUIX() throws Exception {
		ProjectPage project = new ProjectPage(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String hcode = "hcode" + Utility.dynamicNameAppender();
		String Clienttype = "Not a Domain";
		String engineType = "NUIX";

		base.stepInfo("RPMXCON - 56192");
		base.stepInfo("Verify when editing a non-domain project, the whole section 'Processing Setting'"
				+ " will present as read-only if 'NUIX' as the processing engine");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		project.addNewClient_NonDomainProject(clientName, shortName, Clienttype);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddNonDomainProjWithEngineType(projectName, clientName, hcode, engineType);
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);

		base.waitForElement(project.getEngineTypeNUIXRadio());
		String actResult1 = project.getEngineTypeNUIXRadio().GetAttribute("class");
		String actResult2 = project.getEngineTypeICERadio().GetAttribute("class");
		if (actResult1.contains("Disabled") && actResult2.contains("Disabled")) {
			base.passedStep("The whole section 'Processing Setting' Present in Read only Mode..");
		} else {
			base.failedStep("The whole section 'Processing Setting' Not Present in Read only Mode..");
		}
		base.passedStep("Verify when editing a non-domain project, the whole section 'Processing Setting' "
				+ "will present as read-only if 'NUIX' as the processing engine");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55968
	 * @Description:Verify that for SA - while editing 'Initial Size of Project
	 *                     Database'field appears in database section on Create
	 *                     Project page.
	 **/
	@Test(description = "RPMXCON-55968", enabled = true, groups = { "regression" })
	public void verifyInitialSizeofProjSA() throws Exception {
		ProjectPage project = new ProjectPage(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String dbSize = "Small (less than 1000 documents)";

		base.stepInfo("RPMXCON - 55968");
		base.stepInfo("Verify that for SA - while editing 'Initial Size of Project Database' "
				+ "field appears in database section on Create Project page.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		project.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddDomainProject(projectName, clientName);

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);

		base.waitForElement(project.getProjDBDropDown());
		String dbDDStatus = project.getProjDBDropDown().GetAttribute("disabled");
		SoftAssert asserts = new SoftAssert();
		asserts.assertNotNull(dbDDStatus);
		asserts.assertAll();
		base.waitForElement(project.getProjDBDropDown());
		String selectedOpt = project.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			base.passedStep(selectedOpt + "Selected in Initial Size of Project Database DropDown As Expected");
		} else {
			base.failedStep(selectedOpt + "Not Selected in Initial Size of Project Database DropDown");
		}
		base.passedStep("Verify that for SA - while editing 'Initial Size of Project Database'"
				+ " field appears in database section on Create Project page.");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55967
	 * @Description:Verify that for DA - while editing 'Initial Size of Project
	 *                     Database'field appears in database section on Create
	 *                     Project page.
	 **/
	@Test(description = "RPMXCON-55967", enabled = true, groups = { "regression" })
	public void verifyInitialSizeofProjDA() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		Dashboard dashboard = new Dashboard(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String dbSize = "Small (less than 1000 documents)";

		base.stepInfo("RPMXCON - 55967");
		base.stepInfo("Verify that for DA - while editing 'Initial Size of Project Database' "
				+ "field appears in database section on Create Project page.");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged in As DA");
		String daUsername=loginPage.GetDaCurrentUserName();
		driver.waitForPageToBeReady();
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddDomainProject(projectName, clientName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		users.AssignUserToDomain(clientName, daUsername);
		loginPage.logout();

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged in As " + Input.da1userName);
		base = new BaseClass(driver);
		base.selectdomain(clientName);
		driver.waitForPageToBeReady();
		base.waitForElement(dashboard.selectProjectName(clientName));
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);

		base.waitForElement(project.getProjDBDropDown());
		String dbDDStatus = project.getProjDBDropDown().GetAttribute("disabled");
		SoftAssert asserts = new SoftAssert();
		asserts.assertNotNull(dbDDStatus);
		asserts.assertAll();
		base.waitForElement(project.getProjDBDropDown());
		String selectedOpt = project.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			base.passedStep(selectedOpt + "Selected in Initial Size of Project Database DropDown As Expected");
		} else {
			base.failedStep(selectedOpt + "Not Selected in Initial Size of Project Database DropDown");
		}
		base.passedStep("Verify that for DA - while editing 'Initial Size of Project Database'"
				+ " field appears in database section on Create Project page.");
		
		base.selectdomain(Input.domainName);
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56191
	 * @Description:Verify when editing a non-domain project, the whole section
	 *                     'Processing Setting'" + " will present as read-only if
	 *                     'ICE' as the processing engine and 'ICE-Standalone' as
	 *                     Processing Engine Type
	 **/
	@Test(description = "RPMXCON-56191", enabled = true, groups = { "regression" })
	public void verifyEditiNonDomProjProcTypeReadOnlyICE() throws Exception {
		ProjectPage project = new ProjectPage(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String hcode = "hcode" + Utility.dynamicNameAppender();
		String Clienttype = "Not a Domain";
		String engineType = "ICE";

		base.stepInfo("RPMXCON - 56191");
		base.stepInfo("Verify when editing a non-domain project, the whole section 'Processing Setting'"
				+ " will present as read-only if 'ICE' as the processing engine and 'ICE-Standalone' as Processing Engine Type");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		project.addNewClient_NonDomainProject(clientName, shortName, Clienttype);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddNonDomainProjWithEngineType(projectName, clientName, hcode, engineType);
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);

		base.waitForElement(project.getEngineTypeNUIXRadio());
		String actResult1 = project.getEngineTypeNUIXRadio().GetAttribute("class");
		String actResult2 = project.getEngineTypeICERadio().GetAttribute("class");
		if (actResult1.contains("Disabled") && actResult2.contains("Disabled")) {
			base.passedStep("The whole section 'Processing Setting' Present in Read only Mode..");
		} else {
			base.failedStep("The whole section 'Processing Setting' Not Present in Read only Mode..");
		}
		base.passedStep("Verified - when editing a non-domain project, the whole section 'Processing Setting'"
				+ " will present as read-only if 'ICE' as the processing engine and 'ICE-Standalone' as Processing Engine Type");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55969
	 * @Description:Verify that a project is created in a given domain with" +
	 *                     "Initial Size of Project mentioned on Create Project
	 *                     screen
	 **/
	@Test(description = "RPMXCON-55969", enabled = true, groups = { "regression" })
	public void verifyDomainWithIniSizeOfProj() throws Exception {
		ProjectPage project = new ProjectPage(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String dbSize = "Small (less than 1000 documents)";

		base.stepInfo("RPMXCON - 55969");
		base.stepInfo("Verify that a project is created in a given domain with"
				+ "Initial Size of Project mentioned on Create Project screen");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		project.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddDomainProject(projectName, clientName);

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);

		base.waitForElement(project.getProjDBDropDown());
		String selectedOpt = project.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			base.passedStep("Project Successfully Created In a Given Domain With Initial Size");
		} else {
			base.failedStep("Project Not Successfully Created In a Given Domain WIth Initial Size");
		}
		base.passedStep("Verify that a project is created in a given domain with"
				+ " Initial Size of Project mentioned on Create Project screen");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55996
	 * @Description:Verify that a Big size project is created in a given domain
	 *                     with" + "Initial Size of Project mentioned on Create
	 *                     Project screen
	 **/
	@Test(description = "RPMXCON-55996", enabled = true, groups = { "regression" })
	public void verifyDomainWithBIGIniSizeOfProj() throws Exception {
		ProjectPage project = new ProjectPage(driver);

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String dbSize = "Big (more than 25000 documents)";

		base.stepInfo("RPMXCON - 55996");
		base.stepInfo("Verify that a Big size project is created in a given domain with"
				+ "Initial Size of Project mentioned on Create Project screen");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		project.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.AddDomainProject(projectName, clientName);

		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.editProject(projectName);

		base.waitForElement(project.getProjDBDropDown());
		String selectedOpt = project.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			base.passedStep("Project Successfully Created In a Given Domain With Initial SIze");
		} else {
			base.failedStep("Project Not Successfully Created In a Given Domain With Initial SIze");
		}
		base.passedStep("Verify that a Big size project is created in a given domain with "
				+ "Initial Size of Project mentioned on Create Project screen.");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-55586
	 * @Description:To verify if Deduping is editable, then only user can select the
	 *                 one of the level.
	 **/
	@Test(description = "RPMXCON-55586", enabled = true, groups = { "regression" })
	public void verifyingDedupingIsEditable() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		base = new BaseClass(driver);
		base.stepInfo("TestCase id: RPMXCON-55586");
		base.stepInfo("To verify if Deduping is editable, then only user can select the one of the level.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		base.stepInfo("Navigating to Project page");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.getAddProjectBtn().Click();
		base.stepInfo("Select settings option and verifying the selected level in deduping checkbox");
		project.getAddProject_SettingsTab().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.waitForElement(project.getDedupingCheckbox());
		project.getDedupingCheckbox().waitAndClick(10);

		if (!project.getProjectLevelRadioBtn().Selected() && project.getInstanceLevelRadioBtn().Selected()) {
			base.passedStep("User can select only one level as expected");
		} else {
			base.failedStep("User is selected with both level");
		}
		base.waitForElement(project.getSelectProjectRadioBtn());
		project.getSelectProjectRadioBtn().waitAndClick(2);

		if (project.getProjectLevelRadioBtn().Selected() && !project.getInstanceLevelRadioBtn().Selected()) {
			base.passedStep("User can select only one level as expected");
		} else {
			base.failedStep("User is selected with both level");
		}
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-47010
	 * @Description:Verify that Analytics section should be displayed on Add Project
	 *                     screen
	 **/
	@Test(description = "RPMXCON-47010", enabled = true, groups = { "regression" })
	public void verifyingAnalyticsSection() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		base = new BaseClass(driver);
		base.stepInfo("TestCase id: RPMXCON-47010");
		base.stepInfo("Verify that Analytics section should be displayed on Add Project screen");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As " + Input.sa1userName);

		base.stepInfo("Navigating to Project page");
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();

		base.stepInfo("Selecting Add project button");
		project.getAddProjectBtn().Click();
		driver.scrollingToBottomofAPage();

		base.stepInfo("verifying Analytics Toggle is Enabled by default");
		String AnalyticsToggle = project.getAnalyticsToggle().GetAttribute("class");
		if (AnalyticsToggle.contains("activeC")) {
			base.passedStep("Analytics Toggle is enabled by default");
		} else {
			base.failedStep("Analytics toggle is off");
		}

		base.stepInfo("Verifying Analytics Section");
		List<String> AnalyticsClassification = base.availableListofElements(project.getAnalyticsClassification());
		System.out.println("AnalyticsClassification" + AnalyticsClassification);
		base.waitTime(3);
		String[] ComapreString = { "Components", "Automation" };
		if (AnalyticsClassification.equals(Arrays.asList(ComapreString))) {
			System.out.println("ComapreString" + ComapreString);
			base.passedStep("Analytics panel is with Automation and Component");
		} else {
			base.failedStep("Analytics panel is not with Automation and Component");
		}

		base.stepInfo("verifying Component section checkbox");
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(project.getComponentCheckBox(), "Component Textual Analytics CheckBox");

		base.stepInfo("verifying Automation section checkbox");
		int Size = project.getAutomationClassification().size();
		base.ValidateElementCollection_Presence(project.getAutomationClassification(),
				"Kicoff Analytics and Incremental Analytics Checkbox");
		System.out.println(Size);
		if (Size == 2) {
			base.passedStep("KickOff Analytics and Incremental analytics moves under Automation");
		} else {
			base.failedStep("KickOff Analytics and Incremental analytics is not moves under Automation");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-56185
	 * @Description:Verify when editing a domain project, the whole section
	 *                     'Processing Setting' will not present.
	 **/
	@Test(description = "RPMXCON-56185", enabled = true, groups = { "regression" })
	public void verifyingGeneralTabInEditProject() throws Exception {

		base.stepInfo("RPMXCON-56185");
		base.stepInfo("Verify when editing a domain project, the whole section 'Processing Setting' will not present.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as " + Input.sa1userName);

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
		if (!projects.getEngineTypeNUIXRadio().isDisplayed()) {
			base.passedStep("Processing Settings section is not displayed on General tab when editing domain project");
		} else {
			base.failedStep("Proccessing settings section is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-56182
	 * @Description:Verify when creating a domain project, the 'General' tab in
	 *                     Create Project should not present the 'Processing
	 *                     Settings' section.
	 **/
	@Test(description = "RPMXCON-56182", enabled = true, groups = { "regression" })
	public void verifyingGeneralTabInCreateProject() throws Exception {

		base.stepInfo("RPMXCON-56182");
		base.stepInfo(
				"Verify when creating a domain project, the 'General' tab in Create Project should not present the 'Processing Settings' section.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as " + Input.sa1userName);

		String projectname = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectname);

		base.stepInfo("Creating new domain project");
		projects.navigateToProductionPage();
		projects.getAddProjectBtn().waitAndClick(5);
		base.waitForElement(projects.getProjectName());
		projects.getProjectName().SendKeys(projectname);
		base.waitForElement(projects.getSelectEntityType());
		projects.getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
		base.stepInfo("Client type is selected as Domain");

		driver.waitForPageToBeReady();
		if (!projects.getEngineTypeNUIXRadio().isDisplayed()) {
			base.passedStep("Processing Settings is not displayed on General tab when creating domain project");
		} else {
			base.failedStep("Proccessing settings section is displayed");
		}
		loginPage.logout();
	}
}
