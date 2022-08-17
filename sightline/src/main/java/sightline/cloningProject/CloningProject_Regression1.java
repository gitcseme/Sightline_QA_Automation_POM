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
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
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
	DataSets data;
	String projectName;
	int treeNodeCount;
	String savedSearchTreeNode;
	String securityName;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
		
		//Initialization
		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);
		data = new DataSets(driver);
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SessionSearch sessionSearch = new SessionSearch(driver);
		savedsearch = new SavedSearch(driver);
		SecurityGroupsPage securityGroupsPage = new SecurityGroupsPage(driver);
		securityName = "Security_Group"+ Utility.dynamicNameAppender();
		savedsearch.navigateToSSPage();
		treeNodeCount = savedsearch.verifyTreeNodeCount();
		if (treeNodeCount>4) {
			if (sessionSearch.getSavedSearchTreeNode().isElementAvailable(5)) {
				savedSearchTreeNode = sessionSearch.getSavedSearchTreeNode().getText();
				System.out.println(savedSearchTreeNode);
				driver.waitForPageToBeReady();
				sessionSearch.verifySavedSearchTermsForCloningProject(savedSearchTreeNode);
			}else {
				baseClass.failedStep("Count is miss match");
			}
		}
		else if (treeNodeCount<4) {
			
		
			securityGroupsPage.navigateToSecurityGropusPageURL();
			securityGroupsPage.AddSecurityGroup(securityName);
			driver.waitForPageToBeReady();
			
			savedsearch.navigateToSSPage();
			sessionSearch.validateSavedSearchNode();
			sessionSearch.verifySavedSearchTermsForCloningProject(securityName);
			
		}
		
		baseClass.selectproject();
		savedsearch.navigateToSSPage();
		sessionSearch.validateSavedSearchNode();
		sessionSearch.verifySavedSearchTermsForCloningProject(Input.shareSearchPA);
		driver.waitForPageToBeReady();
		baseClass.selectproject();
		savedsearch.navigateToSSPage();
		sessionSearch.validateSavedSearchNode();
		sessionSearch.verifySavedSearchTermsForCloningProject(Input.mySavedSearch);
		driver.waitForPageToBeReady();
		baseClass.selectproject();
		savedsearch.navigateToSSPage();
		sessionSearch.validateSavedSearchNode();
		sessionSearch.verifySavedSearchTermsForCloningProject(Input.shareSearchDefaultSG);
		
		
		loginPage.logout();
		
		projectName = "DemoCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "4");
	
		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();
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
	 * @author Mohan.Venugopal Created on : 21/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using template project then corresponding 'Shared With Project Administrator' are copied from the source template project to the newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54905", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingSavedSearchAndUser() {

		baseClass.stepInfo("Test case Id: RPMXCON-54905");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding 'Shared With Project Administrator' are copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject(Input.shareSearchPA);

		loginPage.logout();

	}

	

	/**
	 * @author Mohan.Venugopal Created on : 22/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using template project then corresponding 'My Saved Search' hierarchy copied from the source template project to the newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54843", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingMySavedSearch() {

		baseClass.stepInfo("Test case Id: RPMXCON-54843");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding 'My Saved Search' hierarchy copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject(Input.mySavedSearch);

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 22/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using template project then corresponding \"My Saved Search\" are copied from the source template project to the newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54836", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingMySavedSearchWithoutHierarchy() {

		baseClass.stepInfo("Test case Id: RPMXCON-54836");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding \"My Saved Search\" are copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject(Input.mySavedSearch);

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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject(Input.shareSearchDefaultSG);

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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		SavedSearch saveSearch = new SavedSearch(driver);
		
		if (treeNodeCount>4) {
			saveSearch.navigateToSSPage();
			saveSearch.verifySavedSearchDetailsForCloningProject(savedSearchTreeNode);
		}else if (treeNodeCount<4) {
			saveSearch.navigateToSSPage();
			saveSearch.verifySavedSearchDetailsForCloningProject(securityName);
		}
		

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
		String productionname = "p" + Utility.dynamicNameAppender();
		String TempName = "Clone1" + Utility.dynamicNameAppender();

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
		String productionname = "p" + Utility.dynamicNameAppender();
		String TempName = "Clone1" + Utility.dynamicNameAppender();
		

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
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}
