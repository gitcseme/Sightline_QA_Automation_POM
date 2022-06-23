package testScriptsRegressionPhase2;

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
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CloningProject_Regression1 {

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

	@Test(description = "RPMXCON-54905", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingSavedSearchAndUser() {

		baseClass.stepInfo("Test case Id: RPMXCON-54905");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding 'Shared With Project Administrator' are copied from the source template project to the newly created Project.");
		String projectName = "DemoCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("Shared With Project");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-54903", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingExportTemplate() {

		baseClass.stepInfo("Test case Id: RPMXCON-54903");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project and no export template  exists then no template gets copied from the source template project to the newly created Project.");
		String projectName = "CloneProjectExport" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "0");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		ProductionPage prodPage = new ProductionPage(driver);
		prodPage.navigateToProductionPage();

		prodPage.verifyProductionAndExportHomePage("Export");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-54902", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingProductionTemplate() {

		baseClass.stepInfo("Test case Id: RPMXCON-54902");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project and no Productions template  exists then no template gets copied from the source template project to the newly created Project.");
		String projectName = "ProdCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "0");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		ProductionPage prodPage = new ProductionPage(driver);
		prodPage.navigateToProductionPage();

		prodPage.verifyProductionAndExportHomePage("Production");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-54843", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingMySavedSearch() {

		baseClass.stepInfo("Test case Id: RPMXCON-54843");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding 'My Saved Search' hierarchy copied from the source template project to the newly created Project.");
		String projectName = "DemoCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"Pre-Requsite:Make sure that at least 3 level Search Group  hierarchy should create and have at least 2 searches Under 'My Saved Search' search group.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.navigateToSSPage();
		sessionSearch.validateSavedSearchNode();
		sessionSearch.validateMySavedSearchTerms();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("My Saved");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-54836", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingMySavedSearchWithoutHierarchy() {

		baseClass.stepInfo("Test case Id: RPMXCON-54836");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding \"My Saved Search\" are copied from the source template project to the newly created Project.");
		String projectName = "DemoCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"Pre-Requsite : Make sure that at least 3 Searches are Saved Under 'My Saved Search' search group.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		savedSearch.navigateToSSPage();
		sessionSearch.validateMySavedSearchTerms();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("My Saved");

		loginPage.logout();

	}

	@Test(description = "RPMXCON-54837", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingSavedSearchOnly() {

		baseClass.stepInfo("Test case Id: RPMXCON-54837");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding \"Shared With Project Administrator\" are copied from the source template project to the newly created Project.");
		String projectName = "DemoCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "2");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("Shared With Project");

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/06/2022 RPMXCON-54838
	 * @Description Verify that when User creates a new Domain Project Using
	 *              template project then corresponding "Shared with Default
	 *              Security Group" are copied from the source template project to
	 *              the newly created Project.
	 */

	@Test(description = "RPMXCON-54838", enabled = true, groups = { "regression" })
	public void userCreateDomainUsingSavedSearchAndUserDefaultSecurityGroup() {

		baseClass.stepInfo("Test case Id: RPMXCON-54838");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding \"Shared with Default Security Group\" are copied from the source template project to the newly created Project.");
		String projectName = "CloneProject1" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("Shared with Default");

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:22/06/2022 RPMXCON-54839
	 * @Description Verify that when User creates a new Domain Project Using
	 *              template project then corresponding "Shared with New Security
	 *              Group" are copied from the source template project to the newly
	 *              created Project.
	 */

	@Test(description = "RPMXCON-54839", enabled = true, groups = { "regression" })
	public void userCreateDomainUsingSavedSearchAndUserNewSecurityGroup() {

		baseClass.stepInfo("Test case Id: RPMXCON-54839");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding \"Shared with New Security Group\" are copied from the source template project to the newly created Project.");
		String projectName = "Demosaved02" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("Shared with securityGroup5531457");

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:23/06/2022 RPMXCON-54805
	 * @throws InterruptedException 
	 * @Description Verify that when User creates a new Domain Project Using template project then corresponding Export template are copied from the source 
	 * template project to the newly created Project.
	 */
	@Test(description = "RPMXCON-54805", enabled = true, groups = { "regression" })
	public void userCreateNewDomainProjectUsingProduction() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54805");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Export template are copied from the source template project to the newly created Project.");
		String projectName = "CloneProject" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String TempName = "Clone1" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		ProductionPage prodPage = new ProductionPage(driver);
		prodPage.navigateToProductionPage();
		
		prodPage.selectingDefaultSecurityGroup();
		prodPage.addANewProduction(productionname);
		prodPage.fillingDATSection();
		prodPage.fillingNativeSection();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		prodPage.savedTemplateAndNewProdcution(productionname, TempName);

		prodPage.verifyProductionTemplateAndExportTemplateHomePage("Production");

		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:23/06/2022 RPMXCON-54804
	 * @throws InterruptedException 
	 * @Description Verify that when User creates a new Domain Project Using template project then corresponding Productions template are copied from the source template project to
	 * the newly created Project.
	 */
	@Test(description = "RPMXCON-54804", enabled = true, groups = { "regression" })
	public void userCreateNewDomainProjectUsingExportTemplate() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54804");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Productions template are copied from the source template project to the newly created Project.");
		String projectName = "TempProject" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String TempName = "Clone1" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName02, "4");
		DataSets data = new DataSets(driver);
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		ProductionPage prodPage = new ProductionPage(driver);
		prodPage.navigateToProductionPage();
		
		prodPage.selectingDefaultSecurityGroup();
		prodPage.addANewProduction(productionname);
		prodPage.fillingDATSection();
		prodPage.fillingNativeSection();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		prodPage.savedTemplateAndNewProdcution(productionname, TempName);

		prodPage.verifyProductionTemplateAndExportTemplateHomePage("Export");

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
