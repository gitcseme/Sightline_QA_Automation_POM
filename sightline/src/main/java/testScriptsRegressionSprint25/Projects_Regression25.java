package testScriptsRegressionSprint25;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression25 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;
	ProductionPage page;

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
		page = new ProductionPage(driver);

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
	 * @Description:Add new path using "Ingestion Folder" option for ingestion while editng the existing project
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
	 * @Description:Verify when editing a non-domain project, the whole section 'Processing Setting' will present as read-only if 'NUIX' as the processing engine
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
		if(actResult1.contains("Disabled") && actResult2.contains("Disabled")) {
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
	 * @Description:Verify that for SA - while editing 'Initial Size of Project Database'field appears in database section on Create Project page.
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
		if(selectedOpt.equals(dbSize)) {
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
	 * @Description:Verify that for DA - while editing 'Initial Size of Project Database'field appears in database section on Create Project page.
	 **/
	@Test(description = "RPMXCON-55967", enabled = true, groups = { "regression" })
	public void verifyInitialSizeofProjDA() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		
		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "Client" + Utility.dynamicNameAppender();
		String shortName = Utility.randomCharacterAppender(5);
		String dbSize = "Small (less than 1000 documents)";
		
		base.stepInfo("RPMXCON - 55967");
		base.stepInfo("Verify that for DA - while editing 'Initial Size of Project Database' "
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
		
		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.AssignUserToDomain(clientName, Input.da1FullName);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged in As " + Input.da1userName);
		base = new BaseClass(driver);
		base.selectdomain(clientName);
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
		if(selectedOpt.equals(dbSize)) {
			base.passedStep(selectedOpt + "Selected in Initial Size of Project Database DropDown As Expected");
		} else {
			base.failedStep(selectedOpt + "Not Selected in Initial Size of Project Database DropDown");
		}
		base.passedStep("Verify that for DA - while editing 'Initial Size of Project Database'"
				+ " field appears in database section on Create Project page.");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56191
	 * @Description:Verify when editing a non-domain project, the whole section 'Processing Setting'"
				+ " will present as read-only if 'ICE' as the processing engine and 'ICE-Standalone' as Processing Engine Type
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
		if(actResult1.contains("Disabled") && actResult2.contains("Disabled")) {
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
	 * @Description:Verify that a project is created in a given domain with"
				             + "Initial Size of Project mentioned on Create Project screen
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
		if(selectedOpt.equals(dbSize)) {
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
	 * @Description:Verify that a Big size project is created in a given domain with"
				+ "Initial Size of Project mentioned on Create Project screen
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
		if(selectedOpt.equals(dbSize)) {
			base.passedStep("Project Successfully Created In a Given Domain With Initial SIze");
		} else {
			base.failedStep("Project Not Successfully Created In a Given Domain With Initial SIze");
		}
		base.passedStep("Verify that a Big size project is created in a given domain with "
				+ "Initial Size of Project mentioned on Create Project screen.");
		loginPage.logout();
	}	
	
	
	
	
	
	
	
}
