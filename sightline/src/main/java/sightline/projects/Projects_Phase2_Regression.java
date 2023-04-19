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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.Dashboard;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	TallyPage tally;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;
	ProjectPage projects;
	ProductionPage page;
	ClientsPage client;
	DomainDashboard dash;

	String projectName;
	String clientName;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);
		tally = new TallyPage(driver);
	    projects = new ProjectPage(driver);
	    page = new ProductionPage(driver);
	}

	/**
	 * @author NA Testcase No:RPMXCON-56177
	 * @Description:Verify if Client Type is edited from Domain to Non-Domain then
	 *                     Processing Engine section does not displays
	 **/
	@Test(description = "RPMXCON-56177", enabled = true, groups = { "regression" })
	public void verifyClientEditDomainToNonDomain() throws Exception {

		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "c" + Utility.dynamicNameAppender();
		String shrtName = Utility.randomCharacterAppender(5);
		String type = "Domain";
		String hcode = "hcode" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON - 56177");
		baseClass.stepInfo(
				"To Verify if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		
		projects.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		projects.addNewClient(clientName, shrtName, type);

		ClientsPage client = new ClientsPage(driver);
		client.navigateToClientPage();
		driver.waitForPageToBeReady();
		client.filterClient(clientName);
		baseClass.waitForElement(client.getClientEditBtn(clientName));
		client.getClientEditBtn(clientName).waitAndClick(5);
		baseClass.waitForElement(projects.getSelectEntity());
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText("Not a Domain");
		baseClass.waitForElement(projects.getClientNameSaveBtn());
		projects.getClientNameSaveBtn().waitAndClick(10);

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		projects.verifyProcessingEngineSectionCtrProj(projectName, clientName, hcode);

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.filterTheProject(projectName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projects.getEditProject(projectName));
		if (projects.getEditProject(projectName).isElementAvailable(10)) {
			baseClass.passedStep("Project Created Successfully As Expected ");
		} else {
			baseClass.failedStep("Project Not Created Successfully");
		}
		baseClass.passedStep(
				"Verified - if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays");
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

		baseClass.stepInfo("Test case Id: RPMXCON-55959");
		baseClass.stepInfo(
				"Verify default value appears in 'Initial Size of Project Database' field on Create Client page");

		// Login as SA and verify
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Navigate to manage-clients");
		projects.navigateToClientFromHomePage();
		baseClass.stepInfo("click on add new client");
		baseClass.waitForElement(projects.getAddNewClient());
		projects.getAddNewClient().waitAndClick(10);
		baseClass.stepInfo("select client type as 'domain'");
		baseClass.waitForElement(projects.getSelectEntity());
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");
		ClientsPage client = new ClientsPage(driver);
		client.verifyDefaultSizeAndAvailableOptions("Medium");
		baseClass.passedStep("All the expected values available in project database field");
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

		baseClass.stepInfo("Test case Id: RPMXCON-55591");
		baseClass.stepInfo("To verify that only Sys Admin can create a New Project_PA");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("verify add new project option as PA user");
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

		baseClass.stepInfo("Test case Id: RPMXCON-56193");
		baseClass.stepInfo("Verify functionality if user cancel the Domain Project creation");
		String projectName = "QaProject" + Utility.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Navigate to manage-project section");
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		baseClass.verifyUrlLanding(Input.url + "Project/Project", "user on project page", "not on project page");
		baseClass.stepInfo("click add project button and enter details");
		baseClass.waitForElement(projects.getAddProjectBtn());
		projects.getAddProjectBtn().waitAndClick(10);
		baseClass.waitForElement(projects.getProjectName());
		projects.getProjectName().SendKeys(projectName);
		baseClass.waitForElement(projects.getSelectEntityType());
		projects.getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
		baseClass.waitForElement(projects.getSelectClientName());
		projects.getSelectClientName().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.stepInfo("click on cancel button");
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(projects.getCancelButton());
		projects.getCancelButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projects.getSearchProjectName());
		// verify project creation and redirected url
		baseClass.verifyUrlLanding(Input.url + "Project/Project", "Redirected to project page",
				"not redirected to project page");
		baseClass.waitForElement(projects.getSearchProjectName());
		projects.getSearchProjectName().SendKeys(projectName);
		baseClass.waitForElement(projects.getProjectFilterButton());
		baseClass.waitTillElemetToBeClickable(projects.getProjectFilterButton());
		projects.getProjectFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (!(projects.getEditProject(projectName).isElementAvailable(10))) {
			baseClass.passedStep("Project not created after clicking cancel button");
		} else {
			baseClass.failedStep("project created even after clicking the cancel button");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.rani date: 24/11/2022 TestCase Id:RPMXCON-55590 Description
	 * :To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab
	 * while editing existing projects.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55590", enabled = true, groups = { "regression" })
	public void verifySADedupingCheckBoxInSettingsTabExistingProject() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-55590");
		baseClass.stepInfo(
				"To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab while editing existing projects.");
		ProjectPage projectPage = new ProjectPage(driver);
		DataSets data = new DataSets(driver);
		String projectName = "ExistingProject" + Utility.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "1");
		data.getNotificationMessage(0, projectName);

		// check the Deduping checkbox
		driver.waitForPageToBeReady();
		projectPage.filterTheProject(projectName);
		baseClass.waitForElement(projectPage.getEditProject(projectName));
		projectPage.getEditProject(projectName).Click();
		driver.waitForPageToBeReady();
		projectPage.getAddProject_SettingsTab().waitAndClick(5);
		baseClass.waitForElement(projectPage.getDedupingCheckbox());
		projectPage.getDedupingCheckbox().waitAndClick(10);
		if (projectPage.getInstanceLevelRadioBtn().Selected()) {
			baseClass.passedStep("Deduping Checkbox is selected");
		} else {
			baseClass.failedStep("Deduping Checkbox is not selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		baseClass.VerifySuccessMessage("Project updated successfully");
		baseClass.passedStep("Deduping checkbox on for the projects.  Make sure that project is saved.");

		// Do not Check Deduping checkbox
		projectPage.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projectPage.filterTheProject(projectName);
		baseClass.waitForElement(projectPage.getEditProject(projectName));
		projectPage.getEditProject(projectName).Click();
		driver.waitForPageToBeReady();
		projectPage.getAddProject_SettingsTab().waitAndClick(5);
		baseClass.waitForElement(projectPage.getDedupingCheckbox());
		projectPage.getDedupingCheckbox().waitAndClick(10);
		if (!projectPage.getInstanceLevelRadioBtn().Selected()) {
			baseClass.passedStep("Deduping Checkbox is not selected");
		} else {
			baseClass.failedStep("Deduping Checkbox is selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		baseClass.VerifySuccessMessage("Project updated successfully");
		baseClass.passedStep("Deduping checkbox off for the projects.  Make sure that project is saved.");
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.rani date: 24/11/2022 TestCase Id:RPMXCON-55589 Description
	 * :To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab
	 * while creating new projects.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55589", enabled = true, groups = { "regression" })
	public void verifySADedupingCheckBoxInSettingsTabNewProject() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-55589");
		baseClass.stepInfo(
				"To verify when Sys Admin checks the 'Deduping' checkbox from Settings tab while creating new projects.");
		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "NewProject" + Utility.dynamicNameAppender();
		String domainName = "Domain";

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.sa1userName + "");

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
		baseClass.waitForElement(projectPage.getDedupingCheckbox());
		projectPage.getDedupingCheckbox().waitAndClick(10);
		if (projectPage.getInstanceLevelRadioBtn().Selected()) {
			baseClass.passedStep("Deduping Checkbox is selected");
		} else {
			baseClass.failedStep("Deduping Checkbox is not selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		baseClass.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");
		baseClass.passedStep("Deduping checkbox on for the projects.  Make sure that project is saved.");

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
			baseClass.passedStep("Deduping Checkbox is not selected");
		} else {
			baseClass.failedStep("Deduping Checkbox is selected");
		}
		driver.scrollingToBottomofAPage();
		projectPage.getButtonSaveProject().Click();
		baseClass.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");
		baseClass.passedStep("Deduping checkbox off for the projects.  Make sure that project is saved.");
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

		baseClass.stepInfo("RPMXCON-53060");
		baseClass.stepInfo(
				"Verify Project creation with domain which is having more than 20 char for Non domain client");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		projects.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		projects.addNewClient_NonDomainProject(client, shrt, "Not a Domain");
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddNonDomainProjWithEngineType(projectName, client, shrt, engineType);
		driver.waitForPageToBeReady();
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.filterTheProject(projectName);
		baseClass.waitForElement(projects.getEditProject(projectName));
		driver.waitForPageToBeReady();
		if (projects.getEditProject(projectName).isElementAvailable(10)) {
			baseClass.passedStep("Project Created Successfully");
		} else {
			baseClass.failedStep("Project Not Created");
		}
		baseClass.passedStep(
				"Verified - Project creation with domain which is having more than 20 char for Non domain client");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55917
	 * @Description:Validate minimum length value while creating a Non-Domain
	 *                       Project
	 **/
	@Test(description = "RPMXCON-55917", enabled = true, groups = { "regression" })
	public void validateMinLengthValue() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		String minLengthWithSP = "@#$%&";
		String minLengAlphaNum = "ABCDE";
		String minLenMorethan12 = "100";
		String minLength = "10";
		String expMsg = "The specified minimum length cannot be greater than 12.";
		String projectName = "Project" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-55917");
		baseClass.stepInfo("To Validate minimum length value while creating a Non-Domain Project");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddDomainProjectDetailsWithoutSave(projectName, Input.domainName);
		driver.scrollPageToTop();
		baseClass.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(5);
		baseClass.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().SendKeys(minLengthWithSP);
		baseClass.waitForElement(projects.getMinLengthValue());
		String value1 = projects.getMinLengthValue().Value();
		baseClass.textCompareEquals(value1, "", "Appropriate not allowed to enter special characters",
				"Appropriate allowed to enter special characters");

		baseClass.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().SendKeys(minLengAlphaNum);
		baseClass.waitForElement(projects.getMinLengthValue());
		String value2 = projects.getMinLengthValue().Value();
		baseClass.textCompareEquals(value2, "", "Appropriate not allowed to enter AlphaNum characters",
				"Appropriate allowed to enter AlphaNum characters");

		baseClass.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().SendKeys(minLenMorethan12);
		baseClass.waitForElement(projects.getButtonSaveProject());
		projects.getButtonSaveProject().waitAndClick(10);
		baseClass.waitForElement(baseClass.getSuccessMsgHeader());
		baseClass.VerifyErrorMessage(expMsg);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.AddDomainProjectDetailsWithoutSave(projectName, Input.domainName);
		driver.scrollPageToTop();
		baseClass.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(5);
		baseClass.waitForElement(projects.getNoOfDocuments());
		projects.getNoOfDocuments().SendKeys("10000");
		baseClass.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().SendKeys(minLength);
		projects.saveProjectAndVerify();
		baseClass.passedStep("Validated - minimum length value while creating a Non-Domain Project");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-55872
	 * @Description:Verify After click on "Production Folder" option for production
	 *                     a text box prompting for path label , actual path
	 **/
	@Test(description = "RPMXCON-55872", enabled = true, groups = { "regression" })
	public void verifyProdFolderPath() throws Exception {
		baseClass.stepInfo("RPMXCON-55872");
		baseClass.stepInfo(
				"Verify After click on Production Folder option for production a text box prompting for path label , actual path");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		baseClass.stepInfo("navigating to projects page");
		projects.navigateToProductionPage();

		baseClass.stepInfo("navigating to client page");
		projects.navigateToClientFromHomePage();

		baseClass.stepInfo("Adding new client");
		projects.addNewClient(clientName, shortName, "Domain");

		baseClass.stepInfo("Creating new domain project");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		projects.editProject(projectName);

		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(projects.getProductionFolder());
		String actual = projects.getProductionFolder().GetAttribute("value");
		System.out.println(actual);
		String expected = clientName + "\\" + projectName;
		softAssert.assertEquals(actual, expected);
		softAssert.assertAll();

		projects.getProductionFolder().Clear();
		projects.getProductionFolder().SendKeys("Automation");

		String editedPath = projects.getProductionFolder().getText();
		System.out.println(editedPath);

		projects.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifySuccessMessage("Project updated successfully");
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-55575
	 * @Description:To verify the Project-Setting TAB details.
	 **/
	@Test(description = "RPMXCON-55575", enabled = true, groups = { "regression" })
	public void verifyProjectSettingTab() throws Exception {
		baseClass.stepInfo("RPMXCON-55575");
		baseClass.stepInfo("To verify the Project-Setting TAB details.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		baseClass.stepInfo("navigating to projects page");
		projects.navigateToProductionPage();

		if (!projects.getDomainEditBtn().isDisplayed()) {

			baseClass.stepInfo("navigating to client page");
			projects.navigateToClientFromHomePage();

			baseClass.stepInfo("Adding new client");
			projects.addNewClient(clientName, shortName, "Domain");

			baseClass.stepInfo("Creating new domain project");
			projects.navigateToProductionPage();
			projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
			projects.editProject(projectName);
		} else {
			baseClass.stepInfo("Edit existing domain project");
			driver.waitForPageToBeReady();
			projects.getDomainEditBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(10);

		baseClass.stepInfo("verify Setting tab.");
		projects.getNoOfDocuments().isDisplayed();
		projects.getDocIdPrefix().isDisplayed();
		projects.getDocIdSuffi().isDisplayed();
		projects.getMinLengthValue().isDisplayed();
		projects.getTxtDedupinglevel().isDisplayed();
		projects.getTxtAnalyticsEngine().isDisplayed();
		baseClass.passedStep("Following details are displayed->\r\n" + "Max. Number of Documents in Project: * \r\n"
				+ "DocID Format: \r\n" + "Deduping Performed in Processing: \r\n" + "Analytics Engine: ");
	}

	/**
	 * @author NA Testcase No:RPMXCON-53058
	 * @Description:Verify Domain creation with long domain name(less than 255 char)
	 *                     for Non domain client
	 **/
	@Test(description = "RPMXCON-53058", enabled = true, groups = { "regression" })
	public void verifyLongDomainNameForNonDomainClient() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		baseClass = new BaseClass(driver);
		ClientsPage client = new ClientsPage(driver);

		String nameSpChar = "Client " + "-" + " " + "_" + Utility.randomCharacterAppender(100);
		String shortName = "C" + Utility.randomCharacterAppender(10);
		String type = "Not a Domain";

		baseClass.stepInfo("RPMXCON-53058");
		baseClass.stepInfo("To Verify Domain creation with long domain name(less than 255 char) for Non domain client");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);
		projects.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		projects.addNewClient_NonDomainProject(nameSpChar, shortName, type);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		client.filterClient(nameSpChar);

		baseClass.waitForElement(client.getClientEditBtn(nameSpChar));
		if (client.getClientEditBtn(nameSpChar).isElementAvailable(5)) {
			baseClass.passedStep("Domain Saved Successfully..");
		} else {
			baseClass.failedStep("Domain Not Saved Successfully...");
		}
		baseClass.passedStep(
				"Verified - Domain creation with long domain name(less than 255 char) for Non domain client");
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
		baseClass = new BaseClass(driver);

		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.randomCharacterAppender(25);
		String shrtName = "C" + Utility.randomCharacterAppender(10);
		String type = "Domain";

		baseClass.stepInfo("RPMXCON-53059");
		baseClass.stepInfo("Verify Project creation with domain which is having more than 20 char for domain client");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);
		projects.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		projects.addNewClient(clientName, shrtName, type);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddDomainProject(projectName, clientName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		projects.filterTheProject(projectName);

		baseClass.waitForElement(projects.getEditProject(projectName));
		if (projects.getEditProject(projectName).isElementAvailable(10)) {
			baseClass.passedStep("Project Created Successfully...");
		} else {
			baseClass.failedStep("Project Not Created...");
		}
		baseClass.passedStep(
				"Verified - Project creation with domain which is having more than 20 char for domain client");
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
		baseClass = new BaseClass(driver);

		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);
		String type = "Domain";
		String expResult = "Default ICE Instance";

		baseClass.stepInfo("RPMXCON-56173");
		baseClass.stepInfo("To Verify 'Processing Engine Type' when user create a domain client on 'Create Client'");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projects.getAddNewClient());
		projects.getAddNewClient().ScrollTo();
		projects.getAddNewClient().waitAndClick(10);

		baseClass.waitForElement(projects.getClientName());
		projects.getClientName().SendKeys(clientName);

		baseClass.waitForElement(projects.getClientShortName());
		projects.getClientShortName().SendKeys(shortName);

		baseClass.waitForElement(projects.getSelectEntity());
		projects.getSelectEntity().waitAndClick(10);
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText(type);

		baseClass.waitForElement(client.getProcessingEngineType());
		String actualResult = client.getProcessingEngineType().selectFromDropdown().getFirstSelectedOption().getText();
		System.out.println(actualResult);
		baseClass.stepInfo(actualResult + " Selected in ProccessingEngineType Drop Down");
		if (actualResult.equals(expResult)) {
			baseClass.passedStep("Default Selection in processingEngineType As Expected..");
		} else {
			baseClass.failedStep("Default Selection in processingEngineType Not As Expected..");
		}
		baseClass.passedStep("Verified - 'Processing Engine Type' when user create a domain client on 'Create Client'");
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

		baseClass.stepInfo("RPMXCON-56178");
		baseClass.stepInfo("To Verify if Client Type is edited from Non-Domain to Domain then Processing Engine section"
				+ " should displays");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, type1);

		driver.Navigate().refresh();
		client.filterClient(clientName);
		baseClass.waitForElement(client.getClientEditBtn(clientName));
		client.getClientEditBtn(clientName).waitAndClick(5);
		baseClass.stepInfo("Edit Client PopUp Opened for " + clientName);

		baseClass.waitForElement(projects.getSelectEntity());
		projects.getSelectEntity().waitAndClick(10);
		projects.getSelectEntity().selectFromDropdown().selectByVisibleText(type2);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(projects.getDomainName());
		projects.getDomainName().SendKeys(domainName);
		driver.scrollingToBottomofAPage();

		baseClass.waitForElement(projects.getProjectDBServerDropdown());
		projects.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);

		baseClass.waitForElement(projects.getProjectServerPath());
		projects.getProjectServerPath().waitAndClick(10);

		baseClass.waitForElement(projects.getIngestionserverpath());
		projects.getIngestionserverpath().waitAndClick(10);

		baseClass.waitForElement(projects.getProductionserverpath());
		projects.getProductionserverpath().waitAndClick(10);

		baseClass.waitForElement(projects.getClientNameSaveBtn());
		projects.getClientNameSaveBtn().waitAndClick(10);

		baseClass.waitTime(2);
		baseClass.VerifySuccessMessage("Client update was successful.");
		baseClass.CloseSuccessMsgpopup();

		projects.verifyProcessingEngineSection(projectName, clientName);
		baseClass.passedStep(
				"Verified - if Client Type is edited from Non-Domain to Domain then Processing Engine section "
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

		baseClass.stepInfo("RPMXCON-56178");
		baseClass.stepInfo("Verify Ingestion Folder option is present for ingestion on Create Project\" input screen");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

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
		baseClass.stepInfo("Domain project is created");

		projects.editProject(projectName);
		baseClass.stepInfo("created Domain project is filtered and opened");

		if (projects.getIngestionFolder().isElementAvailable(5)) {
			baseClass.waitForElement(projects.getIngestionFolder());
			String actual = projects.getIngestionFolder().GetAttribute("value");
			softAssert.assertEquals(actual, expected);
			softAssert.assertAll();
			System.out.println("Ingestion Folder has" + actual);
			baseClass.passedStep("Ingestion folder is present");
		}

	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-55871
	 * @Description:Verify After click on "Ingestion Folder" option for ingestion a
	 *                     text box appear prompting for path label , actual path
	 **/
	@Test(description = "RPMXCON-55871", enabled = true, groups = { "regression" })
	public void verifyEditIngestionFolder() throws Exception {

		baseClass.stepInfo("RPMXCON-56178");
		baseClass.stepInfo(
				"Verify After click on \"Ingestion Folder\" option for ingestion a text box appear prompting for path label , actual path");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		baseClass.stepInfo("Domain project is created");

		projects.editProject(projectName);
		baseClass.stepInfo("created Domain project is filtered and opened");

		baseClass.waitForElement(projects.getIngestionFolder());
		projects.getIngestionFolder().Clear();
		projects.getIngestionFolder().SendKeys("Automation");
		baseClass.stepInfo("Edited the path and saved");

		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(projects.getButtonSaveProject());
		projects.getButtonSaveProject().waitAndClick(10);

		baseClass.passedStep("Edited path saved successfully");

	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-55875
	 * @Description:Add new path using "Ingestion Folder" option for ingestion while
	 *                  editng the existing project
	 **/
	@Test(description = "RPMXCON-55875", enabled = true, groups = { "regression" })
	public void verifyNewPathInIngestionFolder() throws Exception {

		baseClass.stepInfo("RPMXCON-56178");
		baseClass
				.stepInfo("Add new path using Ingestion Folder option for ingestion while editng the existing project");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);
		String newPath = "Automation";

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		projects.navigateToProductionPage();
		projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
		baseClass.stepInfo("Domain project is created");

		projects.editProject(projectName);
		baseClass.stepInfo("created Domain project is filtered and opened");

		baseClass.waitForElement(projects.getIngestionFolder());
		projects.getIngestionFolder().Clear();
		projects.getIngestionFolder().SendKeys(newPath);
		baseClass.stepInfo("Edited the path and saved");

		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(projects.getButtonSaveProject());
		projects.getButtonSaveProject().waitAndClick(10);

		baseClass.passedStep("Edited path saved successfully");

		projects.editProject(projectName);
		baseClass.stepInfo("Edited project is filtered and opened again");

		baseClass.waitForElement(projects.getIngestionFolder());
		String actual = projects.getIngestionFolder().GetAttribute("value");

		softAssert.assertEquals(actual, newPath);
		softAssert.assertAll();
		baseClass.passedStep("Newly added path is available ");

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

		baseClass.stepInfo("RPMXCON - 56192");
		baseClass.stepInfo("Verify when editing a non-domain project, the whole section 'Processing Setting'"
				+ " will present as read-only if 'NUIX' as the processing engine");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, Clienttype);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddNonDomainProjWithEngineType(projectName, clientName, hcode, engineType);
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.editProject(projectName);

		baseClass.waitForElement(projects.getEngineTypeNUIXRadio());
		String actResult1 = projects.getEngineTypeNUIXRadio().GetAttribute("class");
		String actResult2 = projects.getEngineTypeICERadio().GetAttribute("class");
		if (actResult1.contains("Disabled") && actResult2.contains("Disabled")) {
			baseClass.passedStep("The whole section 'Processing Setting' Present in Read only Mode..");
		} else {
			baseClass.failedStep("The whole section 'Processing Setting' Not Present in Read only Mode..");
		}
		baseClass.passedStep("Verify when editing a non-domain project, the whole section 'Processing Setting' "
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

		baseClass.stepInfo("RPMXCON - 55968");
		baseClass.stepInfo("Verify that for SA - while editing 'Initial Size of Project Database' "
				+ "field appears in database section on Create Project page.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddDomainProject(projectName, clientName);

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.editProject(projectName);

		baseClass.waitForElement(projects.getProjDBDropDown());
		String dbDDStatus = projects.getProjDBDropDown().GetAttribute("disabled");
		SoftAssert asserts = new SoftAssert();
		asserts.assertNotNull(dbDDStatus);
		asserts.assertAll();
		baseClass.waitForElement(projects.getProjDBDropDown());
		String selectedOpt = projects.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			baseClass.passedStep(selectedOpt + "Selected in Initial Size of Project Database DropDown As Expected");
		} else {
			baseClass.failedStep(selectedOpt + "Not Selected in Initial Size of Project Database DropDown");
		}
		baseClass.passedStep("Verify that for SA - while editing 'Initial Size of Project Database'"
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

		baseClass.stepInfo("RPMXCON - 55967");
		baseClass.stepInfo("Verify that for DA - while editing 'Initial Size of Project Database' "
				+ "field appears in database section on Create Project page.");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As DA");
		String daUsername = loginPage.GetDaCurrentUserName();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		projects.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddDomainProject(projectName, clientName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		users.AssignUserToDomain(clientName, daUsername);
		loginPage.logout();

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in As " + Input.da1userName);
		baseClass = new BaseClass(driver);
		baseClass.selectdomain(clientName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(dashboard.selectProjectName(clientName));
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.editProject(projectName);

		baseClass.waitForElement(projects.getProjDBDropDown());
		String dbDDStatus = projects.getProjDBDropDown().GetAttribute("disabled");
		SoftAssert asserts = new SoftAssert();
		asserts.assertNotNull(dbDDStatus);
		asserts.assertAll();
		baseClass.waitForElement(projects.getProjDBDropDown());
		String selectedOpt = projects.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			baseClass.passedStep(selectedOpt + "Selected in Initial Size of Project Database DropDown As Expected");
		} else {
			baseClass.failedStep(selectedOpt + "Not Selected in Initial Size of Project Database DropDown");
		}
		baseClass.passedStep("Verify that for DA - while editing 'Initial Size of Project Database'"
				+ " field appears in database section on Create Project page.");

		baseClass.selectdomain(Input.domainName);
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

		baseClass.stepInfo("RPMXCON - 56191");
		baseClass.stepInfo("Verify when editing a non-domain project, the whole section 'Processing Setting'"
				+ " will present as read-only if 'ICE' as the processing engine and 'ICE-Standalone' as Processing Engine Type");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, Clienttype);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddNonDomainProjWithEngineType(projectName, clientName, hcode, engineType);
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.editProject(projectName);

		baseClass.waitForElement(projects.getEngineTypeNUIXRadio());
		String actResult1 = projects.getEngineTypeNUIXRadio().GetAttribute("class");
		String actResult2 = projects.getEngineTypeICERadio().GetAttribute("class");
		if (actResult1.contains("Disabled") && actResult2.contains("Disabled")) {
			baseClass.passedStep("The whole section 'Processing Setting' Present in Read only Mode..");
		} else {
			baseClass.failedStep("The whole section 'Processing Setting' Not Present in Read only Mode..");
		}
		baseClass.passedStep("Verified - when editing a non-domain project, the whole section 'Processing Setting'"
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

		baseClass.stepInfo("RPMXCON - 55969");
		baseClass.stepInfo("Verify that a project is created in a given domain with"
				+ "Initial Size of Project mentioned on Create Project screen");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddDomainProject(projectName, clientName);

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.editProject(projectName);

		baseClass.waitForElement(projects.getProjDBDropDown());
		String selectedOpt = projects.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			baseClass.passedStep("Project Successfully Created In a Given Domain With Initial Size");
		} else {
			baseClass.failedStep("Project Not Successfully Created In a Given Domain WIth Initial Size");
		}
		baseClass.passedStep("Verify that a project is created in a given domain with"
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

		baseClass.stepInfo("RPMXCON - 55996");
		baseClass.stepInfo("Verify that a Big size project is created in a given domain with"
				+ "Initial Size of Project mentioned on Create Project screen");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClientWithDBSize(clientName, shortName, "Domain", dbSize);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.AddDomainProject(projectName, clientName);

		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.editProject(projectName);

		baseClass.waitForElement(projects.getProjDBDropDown());
		String selectedOpt = projects.getProjDBDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if (selectedOpt.equals(dbSize)) {
			baseClass.passedStep("Project Successfully Created In a Given Domain With Initial SIze");
		} else {
			baseClass.failedStep("Project Not Successfully Created In a Given Domain With Initial SIze");
		}
		baseClass.passedStep("Verify that a Big size project is created in a given domain with "
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
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("TestCase id: RPMXCON-55586");
		baseClass.stepInfo("To verify if Deduping is editable, then only user can select the one of the level.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		baseClass.stepInfo("Navigating to Project page");
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		projects.getAddProjectBtn().Click();
		baseClass.stepInfo("Select settings option and verifying the selected level in deduping checkbox");
		projects.getAddProject_SettingsTab().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(projects.getDedupingCheckbox());
		projects.getDedupingCheckbox().waitAndClick(10);

		if (!projects.getProjectLevelRadioBtn().Selected() && projects.getInstanceLevelRadioBtn().Selected()) {
			baseClass.passedStep("User can select only one level as expected");
		} else {
			baseClass.failedStep("User is selected with both level");
		}
		baseClass.waitForElement(projects.getSelectProjectRadioBtn());
		projects.getSelectProjectRadioBtn().waitAndClick(2);

		if (projects.getProjectLevelRadioBtn().Selected() && !projects.getInstanceLevelRadioBtn().Selected()) {
			baseClass.passedStep("User can select only one level as expected");
		} else {
			baseClass.failedStep("User is selected with both level");
		}
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 02/12/2022 TestCase Id:RPMXCON-56184
	 * Description:Verify functionality if user cancel the Non-Domain Project
	 * creation
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56184", enabled = true, groups = { "regression" })
	public void verifyIfUserCancelsNonDomainProjectCreation() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56184");
		baseClass.stepInfo("Verify functionality if user cancel the Non-Domain Project creation");
		String projectName = "QaProject" + Utility.dynamicNameAppender();
		String hCode = "H" + Utility.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Navigate to manage-project section and verify redirected url");
		projects.navigateToProductionPage();
		baseClass.waitForElement(projects.getAddProjectBtn());
		projects.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.verifyUrlLanding(Input.url + "en-us/Project/CreateProject", "Redirected to project creation page",
				"not redirected to project creation page");
		baseClass.stepInfo("enter project creation details");
		baseClass.waitForElement(projects.getProjectName());
		projects.getProjectName().SendKeys(projectName);
		baseClass.waitForElement(projects.getSelectEntityType());
		projects.getSelectEntityType().selectFromDropdown().selectByVisibleText("Not a Domain");
		baseClass.waitForElement(projects.getHCode());
		projects.getHCode().SendKeys(hCode);
		baseClass.stepInfo("click on cancel button");
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(projects.getCancelButton());
		projects.getCancelButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projects.getAddProjectBtn());
		// verify redirected url
		baseClass.verifyUrlLanding(Input.url + "Project/Project", "Redirected to project page",
				"not redirected to project page");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-55916
	 * @Description:Validate minimum length value while creating a Domain Project
	 **/
	@Test(description = "RPMXCON-55916", enabled = true, groups = { "regression" })
	public void validatingMinlengthInDomainPrjt() throws Exception {

		baseClass.stepInfo("RPMXCON - 55916");
		baseClass.stepInfo("Validate minimum length value while creating a Domain Project");

		String projectName = "Project" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String MinSpecialChar = "@#$9";
		String MinAlphaNum = "AB10";
		String MinWithMaxNum = page.getRandomNumber(3);
		String MiniLength = "11";

		String[] MinLength = { MinSpecialChar, MinAlphaNum };
		String[] Char = { "Special Character", "Alphanumeric character" };

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		ProjectPage project = new ProjectPage(driver);

		baseClass.stepInfo("Creating new domain project");
		projects.navigateToProductionPage();
		projects.AddDomainProject(projectName, Input.domainName);

		for (int i = 0; i < MinLength.length; i++) {
			baseClass.stepInfo("Entering and verifying  min. length with " + Char[i]);
			projects.editProject(projectName);
			baseClass.waitForElement(projects.getAddProject_SettingsTab());
			projects.getAddProject_SettingsTab().waitAndClick(5);
			driver.waitForPageToBeReady();
			projects.getMinLengthValue().SendKeys(MinLength[i]);
			projects.getButtonSaveProject().waitAndClick(20);
			if (baseClass.getYesBtn().isElementAvailable(1)) {
				baseClass.getYesBtn().waitAndClick(2);
			}
			projects.editProject(projectName);
			baseClass.waitForElement(projects.getAddProject_SettingsTab());
			projects.getAddProject_SettingsTab().waitAndClick(5);

			String ActualString = projects.getMinLengthValue().Value();
			baseClass.textCompareNotEquals(MinLength[i], ActualString, "Appropriate is not allowed to enter" + Char[i],
					" Appropriate is allowed to enter" + Char);
		}
		baseClass.stepInfo("Entering more numeric value and verifying error message");
		driver.waitForPageToBeReady();
		projects.getMinLengthValue().SendKeys(MinWithMaxNum);
		projects.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifyErrorMessage("The specified minimum length cannot be greater than 12.");

		driver.waitForPageToBeReady();
		projects.getMinLengthValue().SendKeys(MiniLength);
		projects.getButtonSaveProject().waitAndClick(20);
		if (baseClass.getYesBtn().isElementAvailable(1)) {
			baseClass.getYesBtn().waitAndClick(2);
		}
		projects.editProject(projectName);

		baseClass.stepInfo("Entering min. length and verifying the updated value");
		baseClass.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(5);
		String ActualString = projects.getMinLengthValue().Value();
		baseClass.textCompareEquals(MiniLength, ActualString,
				"Project is created successfully with Min. numeric character",
				"project is not created with entered value");

		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-55570
	 * @Description:To verify that user can delete the Client.
	 **/
	@Test(description = "RPMXCON-55570", enabled = true, groups = { "regression" })
	public void verifyUserCanDeleteClient() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);
		SoftAssert asserts = new SoftAssert();

		String clientname = "" + Utility.dynamicNameAppender();
		String shrtType = "" + Utility.randomCharacterAppender(4);

		baseClass.stepInfo("RPMXCON-55570");
		baseClass.stepInfo("To verify that user can delete the Client.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientname, shrtType, "Domain");

		client.filterClient(clientname);
		baseClass.waitForElement(client.getClientDeleteBtn(clientname));
		asserts.assertTrue(client.getClientDeleteBtn(clientname).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo(clientname + " Client Created Successfully");
		client.deleteClinet(clientname);
		client.filterClient(clientname);
		baseClass.waitForElement(client.getClientDeleteBtn(clientname));
		asserts.assertFalse(client.getClientDeleteBtn(clientname).isElementAvailable(2));
		asserts.assertAll();
		baseClass.stepInfo(clientname + " Client Deleted Successfully");
		baseClass.passedStep("Verified - that user can delete the Client.");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/09/2022 Modified date:NA Modified by:
	 * @Description :Verify that the notification should be received upon completion
	 *              of the project creation
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48768", enabled = true, groups = { "regression" })
	public void verifyNotificationAfterprojectCompletion() throws InterruptedException {

		baseClass = new BaseClass(driver);
		projects = new ProjectPage(driver);
		dash = new DomainDashboard(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48768");
		baseClass.stepInfo("Verify that the notification should be received upon completion of the project creation");

		String name = Input.randomText + Utility.dynamicNameAppender();

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		projects.navigateToProductionPage();
		baseClass.clearBullHornNotification();
		projects.AddDomainProject(name, Input.domainName);
		baseClass.waitForNotification();
		dash.getNotificationMessage(0, name);

		baseClass.passedStep(
				"Verified that the notification should be received upon completion of the project creation");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/09/2022 Modified date:NA Modified by:
	 * @Description :To verify the 'Cancel' and 'Save' buttons
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55587", enabled = true, groups = { "regression" })
	public void verifyCancelSaveButton() throws InterruptedException {

		projects = new ProjectPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		dash = new DomainDashboard(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-55587");
		baseClass.stepInfo("To verify the 'Cancel' and 'Save' buttons");

		String name = Input.randomText + Utility.dynamicNameAppender();
		String[] errormsg = { "You must specify the project name", "You must specify the client entity",
				"You must specify a project Hcode", "You must specify a value for the project db server.",
				"Project Workspace is required", "Ingestion Server Path is required",
				"Production Server Path is required", "You must specify the project folder",
				"You must specify the ingestion folder.", "You must specify the production folder" };

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// click cancel
		projects.navigateToProductionPage();
		baseClass.clearBullHornNotification();
		projects.AddDomainProjectDetailsWithoutSave(name, Input.domainName);
		projects.getCancelButton().waitAndClick(5);

		// click save without details
		projects.getAddProjectBtn().waitAndClick(5);
		projects.getButtonSaveProject().waitAndClick(5);
		for (String err : errormsg) {
			softAssertion.assertTrue((boolean) baseClass.text(err).isElementAvailable(3), err);
		}
		baseClass.passedStep("Error message was displayed for all mandatory fields");

		// save with details
		projects.navigateToProductionPage();
		projects.AddDomainProject(name, Input.domainName);
		dash.getNotificationMessage(0, name);
		baseClass.clickFirstBackRoundTask();
		String url = driver.getUrl();
		softAssertion.assertEquals(url, Input.url + "Project/Project");
		softAssertion.assertAll();

		baseClass.passedStep("To verify the 'Cancel' and 'Save' buttons");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/09/2022 Modified date:NA Modified by:
	 * @Description :To verify user can search
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55593", enabled = true, groups = { "regression" })
	public void verifyUserCanSearch() throws InterruptedException {

		projects = new ProjectPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55593");
		baseClass.stepInfo("To verify user can search");

		String randomName = Utility.randomCharacterAppender(10);

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// verify
		projects.filterTheProject(Input.projectName);
		softAssertion.assertTrue((boolean) baseClass.text(Input.projectName).isElementAvailable(3));
		baseClass.passedStep("Project details was displayed.");
		projects.filterTByClientName(Input.domainName);
		softAssertion.assertTrue((boolean) baseClass.text(Input.domainName).isElementAvailable(3));
		baseClass.passedStep("Record/s was displayed for selected Client.");

		projects.filterTheProject(Input.projectName);
		projects.filterTByClientName(Input.domainName);
		softAssertion.assertTrue((boolean) baseClass.text(Input.projectName).isElementAvailable(3));
		softAssertion.assertTrue((boolean) baseClass.text(Input.domainName).isElementAvailable(3));
		baseClass.passedStep("Record was displayed for selected Project Name and Client.");

		projects.filterTheProject(randomName);
		softAssertion.assertTrue((boolean) baseClass.text("Your query returned no data").isElementAvailable(3));
		projects.filterTByClientName(randomName);
		softAssertion.assertTrue((boolean) baseClass.text("Your query returned no data").isElementAvailable(3));

		projects.filterTheProject(Input.projectName);
		projects.filterTByClientName(randomName);
		softAssertion.assertTrue((boolean) baseClass.text("Your query returned no data").isElementAvailable(3));

		projects.filterTheProject(randomName);
		projects.filterTByClientName(Input.domainName);
		softAssertion.assertTrue((boolean) baseClass.text("Your query returned no data").isElementAvailable(3));

		projects.clearFilter();
		softAssertion.assertFalse((boolean) baseClass.text("Your query returned no data").isDisplayed());
		baseClass.passedStep("Message wsas displayed ‘Your query returned no data");

		softAssertion.assertAll();
		baseClass.passedStep("verified user can search");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/09/2022 Modified date:NA Modified by:
	 * @Description :To verify that on Manage -Projects page, label should be
	 *              display as 'Project'
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55908", enabled = true, groups = { "regression" })
	public void verifyPageTitle() throws InterruptedException {

		projects = new ProjectPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55908");
		baseClass.stepInfo("To verify that on Manage -Projects page, label should be display as 'Project'");

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// verify
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(projects.getPageTitle().getText().trim(), "Project");
		baseClass.passedStep("Label was display as 'Project'");

		softAssertion.assertAll();
		baseClass.passedStep("verified that on Manage -Projects page, label should be display as 'Project'");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/11/2022 Modified date:NA Modified by:
	 * @Description :Verify that User can create a domain with Small Size of
	 *              DatabaseClass.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55960", enabled = true, groups = { "regression" })
	public void VerifyUserCreateSmalDatabaseDomain() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55960");
		baseClass.stepInfo("Verify that User can create a domain with Small Size of DatabaseClass.");

		String domianName = Utility.randomCharacterAppender(4) + Utility.dynamicNameAppender();

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// verify
		client = new ClientsPage(driver);
		client.AddDomainClient(domianName, domianName, "Small (less than 1000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue(baseClass.text("Small (less than 1000 documents)").isDisplayed());
		baseClass.passedStep("Small value was displayed in \"Initial Size of Project Database\" field");
		client.getCancelBtn().waitAndClick(5);

		client.deleteClinet(domianName);

		softAssertion.assertAll();
		baseClass.passedStep("Verified that User can create a domain with Small Size of DatabaseClass.");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/11/2022 Modified date:NA Modified by:
	 * @Description :Verify that User can create a domain with Medium Size of
	 *              DatabaseClass.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55961", enabled = true, groups = { "regression" })
	public void VerifyUserCreateMediumDatabaseDomain() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55961");
		baseClass.stepInfo("Verify that User can create a domain with Medium Size of DatabaseClass.");

		String domianName = Utility.randomCharacterAppender(4) + Utility.dynamicNameAppender();

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// verify
		client = new ClientsPage(driver);
		client.AddDomainClient(domianName, domianName, "Medium (less than 25000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue(baseClass.text("Medium (less than 25000 documents)").isDisplayed());
		baseClass.passedStep("Medium value should displayed in \"Initial Size of Project Database\" field");
		client.getCancelBtn().waitAndClick(5);

		client.deleteClinet(domianName);

		softAssertion.assertAll();
		baseClass.passedStep("Verified that User can create a domain with Medium Size of DatabaseClass.");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/11/2022 Modified date:NA Modified by:
	 * @Description :Verify that User can create a domain with Big Size of
	 *              DatabaseClass.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55962", enabled = true, groups = { "regression" })
	public void VerifyUserCreateBugDatabaseDomain() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55962");
		baseClass.stepInfo("Verify that User can create a domain with Big Size of DatabaseClass.");

		String domianName = Utility.randomCharacterAppender(4) + Utility.dynamicNameAppender();

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// verify
		client = new ClientsPage(driver);
		client.AddDomainClient(domianName, domianName, "Big (more than 25000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue((boolean) baseClass.text("Big (more than 25000 documents)").isDisplayed());
		baseClass.passedStep("Big value should displayed in \"Initial Size of Project Database\" field");
		client.getCancelBtn().waitAndClick(5);

		client.deleteClinet(domianName);

		softAssertion.assertAll();
		baseClass.passedStep("Verify that User can create a domain with Big Size of DatabaseClass.");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/11/2022 Modified date:NA Modified by:
	 * @Description :Verify that User can Edit a value from "Size of Database" field
	 *              for Domain Project
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55963", enabled = true, groups = { "regression" })
	public void erifyUserEditTheSizeOfDataBase() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55963");
		baseClass.stepInfo("Verify that User can Edit a value from \"Size of Database\" field for Domain Project");

		String domianName = Utility.randomCharacterAppender(4) + Utility.dynamicNameAppender();

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// pre-req
		client = new ClientsPage(driver);
		client.AddDomainClient(domianName, domianName, "Small (less than 1000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();

		// edit and update
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue((boolean) baseClass.text("Small (less than 1000 documents)").isDisplayed());
		baseClass.passedStep("Small value should displayed in \"Initial Size of Project Database\" field ");
		softAssertion.assertTrue((boolean) client.getDBSizeOption().Enabled());
		baseClass.passedStep("Initial Size of Project Database field should be enabled");
		client.getDBSizeOption().selectFromDropdown().selectByVisibleText("Big (more than 25000 documents)");
		baseClass.passedStep("Big value should get selected from \"Initial Size of Project Database\" field");
		client.getSaveBtn().waitAndClick(10);
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("Client update was successful.");
		baseClass.CloseSuccessMsgpopup();

		// verify
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue((boolean) baseClass.text("Big (more than 25000 documents)").isDisplayed());
		baseClass.passedStep("Big value should displayed in \"Initial Size of Project Database\" field");
		client.getCancelBtn().waitAndClick(5);

		// delete created client
		client.deleteClinet(domianName);

		softAssertion.assertAll();
		baseClass.passedStep("Verify that User can Edit a value from \"Size of Database\" field for Domain Project");
		loginPage.logout();
	}

	/**
	 * @Author :Aathith date: 08/11/2022 Modified date:NA Modified by:
	 * @Description :Verify that User can Edit a value from "Size of Database" field
	 *              and applicable to only new projects created in this domain
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-55964", enabled = true, groups = { "regression" })
	public void erifyUserEditTheSizeSmallToMediumDataBase() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-55964");
		baseClass.stepInfo(
				"Verify that User can Edit a value from \"Size of Database\" field and applicable to only new projects created in this domain");

		String domianName = Utility.randomCharacterAppender(4) + Utility.dynamicNameAppender();

		// login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);

		// pre-req
		client = new ClientsPage(driver);
		client.AddDomainClient(domianName, domianName, "Small (less than 1000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();

		// edit and update
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue((boolean) baseClass.text("Small (less than 1000 documents)").isDisplayed());
		baseClass.passedStep("Small value should displayed in \"Initial Size of Project Database\" field ");
		softAssertion.assertTrue((boolean) client.getDBSizeOption().Enabled());
		baseClass.passedStep("Initial Size of Project Database field should be enabled");
		client.getDBSizeOption().selectFromDropdown().selectByVisibleText("Medium (less than 25000 documents)");
		baseClass.passedStep("Medium value should displayed in \"Initial Size of Project Database\" field");
		client.getSaveBtn().waitAndClick(10);
		baseClass.getSuccessMsgHeader().isElementAvailable(60);
		baseClass.VerifySuccessMessage("Client update was successful.");
		baseClass.CloseSuccessMsgpopup();

		// verify
		client.filterClient(domianName);
		client.getClientEditBtn(domianName).waitAndClick(5);
		softAssertion.assertTrue((boolean) baseClass.text("Big (more than 25000 documents)").isDisplayed());
		baseClass.passedStep("Big value should displayed in \"Initial Size of Project Database\" field");
		client.getCancelBtn().waitAndClick(5);

		// delete created client
		client.deleteClinet(domianName);

		softAssertion.assertAll();
		baseClass.passedStep(
				"Verified that User can Edit a value from \"Size of Database\" field and applicable to only new projects created in this domain");
		loginPage.logout();
	}

	/**
	 * @author TestCase id:55585 DATE:NA
	 * @Description:UI_To verify that error message is displayed if user enters the
	 *                    invalid value for No of Documents.
	 */
	@Test(description = "RPMXCON-55585", enabled = true, groups = { "regression" })
	public void verifyErrorMsgDisplayedInvalidValue() throws Exception {
		ProjectPage project = new ProjectPage(driver);

		baseClass.stepInfo("Testcase -RPMXCON-55585  project");
		String DocNumber = "Abc";
		String DocNumber1 = "-234";
		String DocNumber2 = "34#";

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"UI_To verify that error message is displayed if user enters the invalid value for No of Documents.");
		projects.navigateToProjectsPage();

		// verify no of doc error Message
		baseClass.waitForElement(projects.getAddProjectBtn());
		projects.getAddProjectBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		projects.verifyDocumentNumberErrorMessage(DocNumber);
		driver.waitForPageToBeReady();
		projects.verifyDocumentNumberErrorMessage(DocNumber1);
		driver.waitForPageToBeReady();
		projects.verifyDocumentNumberErrorMessage(DocNumber2);
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-47010
	 * @Description:Verify that Analytics section should be displayed on Add Project
	 *                     screen
	 **/
	@Test(description = "RPMXCON-47010", enabled = true, groups = { "regression" })
	public void verifyingAnalyticsSection() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("TestCase id: RPMXCON-47010");
		baseClass.stepInfo("Verify that Analytics section should be displayed on Add Project screen");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		baseClass.stepInfo("Navigating to Project page");
		projects.navigateToProductionPage();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Selecting Add project button");
		projects.getAddProjectBtn().Click();
		driver.scrollingToBottomofAPage();

		baseClass.stepInfo("verifying Analytics Toggle is Enabled by default");
		String AnalyticsToggle = projects.getAnalyticsToggle().GetAttribute("class");
		if (AnalyticsToggle.contains("activeC")) {
			baseClass.passedStep("Analytics Toggle is enabled by default");
		} else {
			baseClass.failedStep("Analytics toggle is off");
		}

		baseClass.stepInfo("Verifying Analytics Section");
		List<String> AnalyticsClassification = baseClass.availableListofElements(projects.getAnalyticsClassification());
		System.out.println("AnalyticsClassification" + AnalyticsClassification);
		baseClass.waitTime(3);
		String[] ComapreString = { "Components", "Automation" };
		if (AnalyticsClassification.equals(Arrays.asList(ComapreString))) {
			System.out.println("ComapreString" + ComapreString);
			baseClass.passedStep("Analytics panel is with Automation and Component");
		} else {
			baseClass.failedStep("Analytics panel is not with Automation and Component");
		}

		baseClass.stepInfo("verifying Component section checkbox");
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(projects.getComponentCheckBox(), "Component Textual Analytics CheckBox");

		baseClass.stepInfo("verifying Automation section checkbox");
		int Size = projects.getAutomationClassification().size();
		baseClass.ValidateElementCollection_Presence(projects.getAutomationClassification(),
				"Kicoff Analytics and Incremental Analytics Checkbox");
		System.out.println(Size);
		if (Size == 2) {
			baseClass.passedStep("KickOff Analytics and Incremental analytics moves under Automation");
		} else {
			baseClass.failedStep("KickOff Analytics and Incremental analytics is not moves under Automation");
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

		baseClass.stepInfo("RPMXCON-56185");
		baseClass.stepInfo(
				"Verify when editing a domain project, the whole section 'Processing Setting' will not present.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as " + Input.sa1userName);

		String projectName = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectName);
		String clientName = "Client " + Utility.dynamicNameAppender();
		String shortName = "C" + Utility.randomCharacterAppender(4);

		baseClass.stepInfo("navigating to projects page");
		projects.navigateToProductionPage();

		if (!projects.getDomainEditBtn().isDisplayed()) {

			baseClass.stepInfo("navigating to client page");
			projects.navigateToClientFromHomePage();

			baseClass.stepInfo("Adding new client");
			projects.addNewClient(clientName, shortName, "Domain");

			baseClass.stepInfo("Creating new domain project");
			projects.navigateToProductionPage();
			projects.AddDomainProjectWithDefaultSetting(projectName, clientName);
			projects.editProject(projectName);
		} else {
			baseClass.stepInfo("Edit existing domain project");
			driver.waitForPageToBeReady();
			projects.getDomainEditBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		if (!projects.getEngineTypeNUIXRadio().isDisplayed()) {
			baseClass.passedStep(
					"Processing Settings section is not displayed on General tab when editing domain project");
		} else {
			baseClass.failedStep("Proccessing settings section is displayed");
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

		baseClass.stepInfo("RPMXCON-56182");
		baseClass.stepInfo(
				"Verify when creating a domain project, the 'General' tab in Create Project should not present the 'Processing Settings' section.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as " + Input.sa1userName);

		String projectname = "Project" + Utility.dynamicNameAppender();
		System.out.println(projectname);

		baseClass.stepInfo("Creating new domain project");
		projects.navigateToProductionPage();
		projects.getAddProjectBtn().waitAndClick(5);
		baseClass.waitForElement(projects.getProjectName());
		projects.getProjectName().SendKeys(projectname);
		baseClass.waitForElement(projects.getSelectEntityType());
		projects.getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
		baseClass.stepInfo("Client type is selected as Domain");

		driver.waitForPageToBeReady();
		if (!projects.getEngineTypeNUIXRadio().isDisplayed()) {
			baseClass.passedStep("Processing Settings is not displayed on General tab when creating domain project");
		} else {
			baseClass.failedStep("Proccessing settings section is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-62851
	 * @Description:To Verify that When SAU/DAU changes the setting for
	 *                 Enable/Disable Project level Analytics,the changes should be
	 *                 saved.
	 **/
	@Test(description = "RPMXCON-62851", enabled = true, groups = { "regression" })
	public void verifySADAUChangeSettingProLvlAnal() throws Exception {
		baseClass.stepInfo("RPMXCON-62851");
		baseClass
				.stepInfo("To Verify that When SAU/DAU changes the setting for Enable/Disable Project level Analytics ,"
						+ " the changes should be saved.");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		baseClass = new BaseClass(driver);
		baseClass.openImpersonateTab();
		baseClass.selectImpersonateRole(Input.DomainAdministrator);
		baseClass.selectImpersonateDomain(Input.domainName);
		baseClass.waitForElement(baseClass.getSaveChangeRole());
		baseClass.getSaveChangeRole().waitAndClick(10);

		ProjectPage projPage = new ProjectPage(driver);
		projPage.navigateToProductionPage();
		projPage.editProject(Input.projectName);

		if (!projPage.getComponentLabel().isElementAvailable(5)) {
			baseClass.waitForElement(projPage.getEnableAnalyticsToogle());
			projPage.getEnableAnalyticsToogle().waitAndClick(4);
			baseClass.waitForElement(projPage.getButtonSaveProject());
			projPage.getButtonSaveProject().waitAndClick(5);
			driver.waitForPageToBeReady();
			projPage.editProject(Input.projectName);
		}

		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(projPage.getEnableAnalyticsToogle());
		driver.Navigate().refresh();
		baseClass.waitForElement(projPage.getEnableAnalyticsToogle());
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		baseClass.waitForElement(projPage.getButtonSaveProject());
		projPage.getButtonSaveProject().waitAndClick(5);
		projPage.editProject(Input.projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOffCheck(projPage.getEnableAnalyticsToogle());
		baseClass.waitForElement(projPage.getEnableAnalyticsToogle());
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		baseClass.passedStep(
				"Verified - that When SAU/DAU changes the setting for Enable/Disable Project level Analytics ,"
						+ " the changes should be saved.");
		
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56179
	 * @Description:To Verify when creating a non-domain project, the 'General' tab
	 *                 in Create Project should present 'Processing Settings'
	 *                 section
	 **/
	@Test(description = "RPMXCON-56179", enabled = true, groups = { "regression" })
	public void verifyProcessSettingCrtNonDomain() throws Exception {
		baseClass.stepInfo("RPMXCON-56179");
		baseClass.stepInfo("To Verify when creating a non-domain project, the 'General' tab in Create Project "
				+ "should present 'Processing Settings' section");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		ProjectPage projPage = new ProjectPage(driver);
		projPage.navigateToProductionPage();
		baseClass.waitForElement(projPage.getAddProjectBtn());
		projPage.getAddProjectBtn().waitAndClick(5);
		baseClass.stepInfo("Successfully Clicked Add Project Button..");
		baseClass.waitForElement(projPage.getSelectEntityType());
		projPage.getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
		baseClass.stepInfo("Domain Selected in Client Type DropDown");
		softAssert.assertFalse(projPage.getProcessEngineTypeICE().isElementAvailable(10));
		softAssert.assertFalse(projPage.getProcessEngineTypeNUIX().isElementAvailable(10));
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(projPage.getSelectEntityType());
		projPage.getSelectEntityType().selectFromDropdown().selectByVisibleText("Not a Domain");
		baseClass.stepInfo("Not a Domain Selected in Client Type DropDown");
		softAssert.assertTrue(projPage.getProcessEngineTypeICE().isElementAvailable(10));
		softAssert.assertTrue(projPage.getProcessEngineTypeNUIX().isElementAvailable(10));
		softAssert.assertAll();
		baseClass.passedStep("Verified - when creating a non-domain project, the 'General' tab in Create Project "
				+ "should present 'Processing Settings' section");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-47013
	 * @Description: Verify that while editing the project, should be able to
	 *               enable/disable analytics
	 **/

	@Test(description = "RPMXCON-47013", enabled = true, groups = { "regression" })
	public void verifyEnabDisabToogleEditProj() throws Exception {
		String projectName = Input.randomText + Utility.dynamicNameAppender();

		baseClass = new BaseClass(driver);
		ProjectPage projPage = new ProjectPage(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProductionPage page = new ProductionPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-47013");
		baseClass.stepInfo("To Verify that while editing the project, should be able to enable/disable analytics");

		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Login as a DA user :" + Input.da1userName);

		projPage.navigateToProductionPage();
		baseClass.clearBullHornNotification();
		projPage.AddDomainProjectViaDaUser(projectName);
		baseClass.waitForNotification();
		dash.getNotificationMessage(0, projectName);

		projPage.navigateToProductionPage();
		projPage.editProject(projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(projPage.getEnableAnalyticsToogle());
		baseClass.stepInfo("Enable Analytics Enabled");

		driver.Navigate().refresh();
		baseClass.waitForElement(projPage.getEnableAnalyticsToogle());
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		baseClass.waitForElement(projPage.getButtonSaveProject());
		projPage.getButtonSaveProject().waitAndClick(5);
		projPage.editProject(projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOffCheck(projPage.getEnableAnalyticsToogle());
		baseClass.stepInfo("After Click Enable Analytic Toogle and Save Toogle Disabled as Expected");

		driver.Navigate().refresh();
		baseClass.waitForElement(projPage.getEnableAnalyticsToogle());
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		baseClass.waitForElement(projPage.getButtonSaveProject());
		projPage.getButtonSaveProject().waitAndClick(5);
		projPage.editProject(projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(projPage.getEnableAnalyticsToogle());
		baseClass.stepInfo("After Click Enable Analytic Toogle and Save Toogle Enabled as Expected");
		baseClass.passedStep("Verified -  that while editing the project, should be able to enable/disable analytics");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56175
	 * @Description:Verify that when creating a non-domain client, the processing
	 *                     engine section doesn't present on 'Create Client' page
	 **/

	@Test(description = "RPMXCON-56175", enabled = true, groups = { "regression" })
	public void verifyNonDomainClient() throws Exception {
		String clientName = "C" + Utility.dynamicNameAppender();
		String shortName = "S" + Utility.dynamicNameAppender();

		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-56175");
		baseClass.stepInfo(
				"Verify that when creating a non-domain client, the processing engine section doesn't present on 'Create Client' page");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a SA user :" + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, "Not a Domain");

		String pass = "The processing engine section not present on 'Create Client' page";
		String fail = "The processing engine section is present on 'Create Client' page";

		Boolean result = projects.getProcessingEngineTxt().isDisplayed();
		baseClass.printResutInReport(result, pass, fail, "Fail");
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-55913
	 * @Description:Validate minimum length value for DocID Format for Domain
	 *                       project
	 **/

	@Test(description = "RPMXCON-55913", enabled = true, groups = { "regression" })
	public void verifyMinimumLengthValue_DomainProject() throws Exception {

		String projectName = "P" + Utility.dynamicNameAppender();
		String clientName = "CN" + Utility.dynamicNameAppender();
		System.out.println(clientName);
		String shortName = "S" + Utility.dynamicRandomNumberAppender();
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-55913");
		baseClass.stepInfo("Validate minimum length value for DocID Format for Domain project");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a SA user :" + Input.sa1userName);

		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		System.out.println("client is created" + clientName);

		projects.navigateToProductionPage();

		// hovering action is performed temporarily to make the panel disappear

//		baseClass.mouseHoverOnElement(projects.getManageProjectBtn());
//		driver.waitForPageToBeReady();
//		baseClass.mouseHoverOnElement(projects.getAddProjectBtn());

		projects.AddDomainProject(projectName, clientName);
		System.out.println("project is created" + projectName);
		projects.navigateToProductionPage();

		// hovering action is performed temporarily to make the panel disappear
//		baseClass.mouseHoverOnElement(projects.getAddProjectBtn());
//		driver.waitForPageToBeReady();
//		baseClass.mouseHoverOnElement(projects.getAddProjectBtn());

		projects.editProject(projectName);
		driver.waitForPageToBeReady();

		// hovering action is performed temporarily to make the panel disappear
//		baseClass.mouseHoverOnElement(projects.getManageProjectBtn());
//		driver.waitForPageToBeReady();
//		baseClass.mouseHoverOnElement(projects.getProjectFolder());

		baseClass.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(10);

		baseClass.stepInfo("Modify minimum length value with special characters and Save changes");
		baseClass.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().waitAndClick(10);
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("*&");
		String text = projects.getMinLengthValue().getText();
		baseClass.printResutInReport(text.isEmpty(), "Special characters should not be accepted",
				"Special characters got accepted", "Pass");

		baseClass.stepInfo("Enter minimum value with alphanumeric characters and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("A123");
		String text1 = projects.getMinLengthValue().getText();
		baseClass.printResutInReport(text1.isEmpty(), "AlphaNumeric characters should not be accepted",
				"AlphaNumeric characters got accepted", "Pass");

		baseClass.stepInfo("Enter minimum value with more than 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("1234567890123");
		projects.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifyErrorMessage("The specified minimum length cannot be greater than 12.");

		baseClass.stepInfo("Enter minimum value with less than or equal to 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("8");
		projects.getButtonSaveProject().waitAndClick(10);
		if (projects.getBgTaskPopup().isElementAvailable(5)) {
			projects.getBtnOK().waitAndClick(10);
		}
		baseClass.VerifySuccessMessage("Project updated successfully");

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-55912
	 * @Description:Validate minimum length value for DocID Format for Non-Domain
	 *                       project
	 **/

	@Test(description = "RPMXCON-55912", enabled = true, groups = { "regression" })
	public void verifyMinimumLengthValue_NonDomainProject() throws Exception {

		String projectName = "P" + Utility.dynamicNameAppender();
		System.out.println(clientName);
		String hCode = "H" + Utility.dynamicRandomNumberAppender();
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-55912");
		baseClass.stepInfo("Validate minimum length value for DocID Format for Non-Domain project");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a SA user :" + Input.sa1userName);

		projects.navigateToProductionPage();

		projectName = projects.addNonDomainProjectBasedOnAvailablitity(projectName, hCode);
		System.out.println("project is created" + projectName);
		projects.editProject(projectName);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(10);

		baseClass.stepInfo("Modify minimum length value with special characters and Save changes");
		baseClass.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().waitAndClick(10);
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("*&");
		String text = projects.getMinLengthValue().getText();
		baseClass.printResutInReport(text.isEmpty(), "Special characters should not be accepted",
				"Special characters got accepted", "Pass");

		baseClass.stepInfo("Enter minimum value with alphanumeric characters and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("A123");
		String text1 = projects.getMinLengthValue().getText();
		baseClass.printResutInReport(text1.isEmpty(), "AlphaNumeric characters should not be accepted",
				"AlphaNumeric characters got accepted", "Pass");

		baseClass.stepInfo("Enter minimum value with more than 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("1234567890123");
		projects.getButtonSaveProject().waitAndClick(10);
		baseClass.VerifyErrorMessage("The specified minimum length cannot be greater than 12.");

		baseClass.stepInfo("Enter minimum value with less than or equal to 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("8");
		projects.getButtonSaveProject().waitAndClick(10);
		if (projects.getBgTaskPopup().isElementAvailable(5)) {
			projects.getBtnOK().waitAndClick(10);
		}
		baseClass.VerifySuccessMessage("Project updated successfully");

	}

	/**
	 * @author Krishna ModifyDate:NA RPMXCON-55958
	 * @throws Exception
	 * @Description Verify that "Initial Size of Project Database" field appears in
	 *              database section on Create Client page.
	 */
	@Test(description = "RPMXCON-55958", enabled = true, groups = { "regression" })
	public void verifyInitialSizeOfProjectDataBaseAppearsDataBaseSection() throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-55958");
		baseClass.stepInfo(
				"Verify that \"Initial Size of Project Database\"  field  appears in database section on Create Client page.");

		ClientsPage client = new ClientsPage(driver);

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");

		baseClass.stepInfo("Create client screen is opened");
		client.addNewClientWithDomainType();
		baseClass.stepInfo("Domain value is selected From clientType box");

		baseClass.waitForElement(client.getInitialSizeOfProjectDatabase());
		if (client.getInitialSizeOfProjectDatabase().isElementPresent()) {
			baseClass.passedStep(
					"Initial Size of Project Database  field is displayed in database section on Create Client page as expected");

		} else {
			baseClass.failedStep(" Initial Size of Project Database  field  is not displayed");

		}
		loginPage.logout();

	}

	/**
	 * @author Krishna TestCase id:55574 DATE:NA
	 * @Description: To verify the Project-General TAB details.
	 */
	@Test(description = "RPMXCON-55574", enabled = true, groups = { "regression" })
	public void verifyProjectGeneralTabDetails() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("To verify the Project-General TAB details.");
		ProjectPage project = new ProjectPage(driver);
		SoftAssert softassert = new SoftAssert();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		baseClass.stepInfo("Navigating to project field page");
		projects.navigateToProductionPage();
		baseClass.waitForElement(projects.getAddProjectBtn());
		projects.getAddProjectBtn().waitAndClick(10);
		baseClass.stepInfo("Clicked general tab go to project page");
		driver.waitForPageToBeReady();
		String Database = projects.getProjectSection(4).getText();
		softassert.assertTrue(projects.getProjectSection(4).isElementPresent());
		baseClass.passedStep(Database + " is displayed successfully");
		driver.waitForPageToBeReady();
		softassert.assertTrue(projects.getProjectDBServer().isElementPresent());
		baseClass.passedStep("Projectdbserver is displayed successfully");
		driver.waitForPageToBeReady();
		String Workspace = projects.getProjectSection(5).getText();
		driver.waitForPageToBeReady();
		softassert.assertTrue(projects.getProjectSection(5).isElementPresent());
		baseClass.passedStep(Workspace + " is displayed successfully");
		driver.waitForPageToBeReady();
		softassert.assertTrue(projects.getProjectServerpath().isElementPresent());
		baseClass.passedStep("Projectseverpath is displayed successfully");
		driver.waitForPageToBeReady();
		softassert.assertTrue(projects.getProductionServerpath().isElementPresent());
		baseClass.passedStep("Productionserverpath is displayed successfully");
		driver.waitForPageToBeReady();
		softassert.assertTrue(projects.getProjectActive().isElementPresent());
		baseClass.passedStep("ProjectActive is displayed successfully");
		softassert.assertAll();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/10/2022 RPMXCON-55729
	 * @throws Exception
	 * @Description Verify that notification has a clear message about the project
	 *              creation or failure.
	 */
	@Test(description = "RPMXCON-55729", enabled = true, groups = { "regression" })
	public void verifyNotificationClearMsgInProjectCreation() throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-55729");
		baseClass.stepInfo("Verify that notification has a clear message about the project creation or failure.");

		ProjectPage projectPage = new ProjectPage(driver);
		String projectName = "Project" + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");

		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");
		DataSets data = new DataSets(driver);
		baseClass.waitTime(2);
		data.getNotificationMessage(0, projectName);

		baseClass.passedStep("Verify that notification has a clear message about the project creation Successfully");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:13/10/2022 RPMXCON-56174
	 * @throws Exception
	 * @Description Verify SAU can create a new domain client by selecting
	 *              'Processing Engine Type' as 'ICE-NUIX'.
	 */
	@Test(description = "RPMXCON-56174", enabled = true, groups = { "regression" })
	public void verifySACreateNewDomainClientSelectProcessingEngineType() throws InterruptedException {

		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-56174");
		baseClass.stepInfo(
				"Verify SAU can create a new domain client by selecting 'Processing Engine Type' as 'ICE-NUIX'.");

		ClientsPage client = new ClientsPage(driver);
		String domianName = Utility.randomCharacterAppender(4) + Utility.dynamicNameAppender();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");

		// Add Domain client
		client.navigateToClientPage();
		driver.waitForPageToBeReady();
		client.AddDomainClientProcessingEngine(domianName, domianName, "Small (less than 1000 documents)");
		baseClass.getSuccessMsgHeader().isElementAvailable(10);
		baseClass.VerifySuccessMessage("The new client was added successfully");
		baseClass.CloseSuccessMsgpopup();
		client.filterClient(domianName);
		driver.waitForPageToBeReady();
		if (baseClass.text(domianName).isDisplayed()) {
			baseClass.passedStep("Newly created Domain client details is displayed in the Grid view");
		} else {
			baseClass.failedStep("Newly created Domain client details is not displays in the Grid view");
		}
		// delete client
		client.deleteClinet(domianName);
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression_22.**");
	}
}
