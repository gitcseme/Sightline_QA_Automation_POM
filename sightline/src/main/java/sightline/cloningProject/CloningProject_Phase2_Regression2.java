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

public class CloningProject_Phase2_Regression2 {

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
	AnnotationLayer annotation;
	CommentsPage commentsPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

		// Initialization
		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);
		data = new DataSets(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		keywordPage = new KeywordPage(driver);
		keywordPage.validateKeywordHighlightingIsPresent();
		annotation = new AnnotationLayer(driver);
		annotation.verifyAnnotationTable();
		commentsPage = new CommentsPage(driver);
		commentsPage.validateCommentsTable();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectName = "DemoCloneProject" + Utility.dynamicNameAppender();
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName, Input.domainName, Input.projectName, "0");

		data.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();
		loginPage.quitBrowser();
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
	 * @description: Verify that when User creates a new Domain Project Using
	 *               template project and no export template exists then no template
	 *               gets copied from the source template project to the newly
	 *               created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54903", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingExportTemplate() {

		baseClass.stepInfo("Test case Id: RPMXCON-54903");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project and no export template  exists then no template gets copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		ProductionPage prodPage = new ProductionPage(driver);
		prodPage.navigateToProductionPage();

		prodPage.verifyProductionAndExportHomePage("Export");

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 21/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using
	 *               template project and no Productions template exists then no
	 *               template gets copied from the source template project to the
	 *               newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54902", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingProductionTemplate() {

		baseClass.stepInfo("Test case Id: RPMXCON-54902");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project and no Productions template  exists then no template gets copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		ProductionPage prodPage = new ProductionPage(driver);
		prodPage.navigateToProductionPage();

		prodPage.verifyProductionAndExportHomePage("Production");

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 22/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using
	 *               template project then corresponding \"Shared With Project
	 *               Administrator\" are copied from the source template project to
	 *               the newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54837", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingSavedSearchOnly() {

		baseClass.stepInfo("Test case Id: RPMXCON-54837");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding \"Shared With Project Administrator\" are copied from the source template project to the newly created Project.");
		String projectName1 = "DemoCloneProject" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		projectPage.navigateToProductionPage();
		projectPage.selectProjectToBeCopied(projectName1, Input.domainName, Input.projectName, "2");
		data.getNotificationMessage(0, projectName1);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName1, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName1);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.navigateToSSPage();
		saveSearch.verifySavedSearchDetailsForCloningProject("Shared With Project");

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 24/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using
	 *               template project then corresponding Keywords highlighting
	 *               Layers are copied from the source template project to the newly
	 *               created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54803", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingKeywordHighlight() throws AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54803");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Keywords highlighting Layers are copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		keywordPage = new KeywordPage(driver);
		keywordPage.navigateToKeywordPage();
		keywordPage.verifyKeywordHighlight();

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 24/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using
	 *               template project then corresponding Annotation Layers are
	 *               copied from the source template project to the newly created
	 *               Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54802", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingAnnotationLayer() throws AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54802");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Annotation Layers are copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		annotation = new AnnotationLayer(driver);
		annotation.navigateToAnnotationLayerPage();
		annotation.verifyAnnotationLayerTable();

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 24/06/2022 Modified On:NA
	 * @description: Verify that when User creates a new Domain Project Using
	 *               template project then corresponding Redaction Tags are copied
	 *               from the source template project to the newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54801", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingRedactionTag() throws AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54801");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Redaction Tags are copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		RedactionPage redactionPage = new RedactionPage(driver);
		redactionPage.navigateToRedactionsPageURL();
		redactionPage.validateReactionTagPageTree();

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 24/06/2022 Modified On:NA
	 * @description:Verify that when User creates a new Domain Project Using
	 *                     template project then corresponding comments are copied
	 *                     from the source template project to the newly created
	 *                     Project.
	 * @throws AWTException
	 */

	@Test(description = "RPMXCON-54800", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingComments() {

		baseClass.stepInfo("Test case Id: RPMXCON-54800");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain  Project Using template project then corresponding comments are copied from the source template project to the newly created Project.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		commentsPage = new CommentsPage(driver);
		commentsPage.navigateToCommentsPage();
		commentsPage.verifyCommentsPage();

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 28/06/2022 Modified On:NA
	 * @description:Verify that when User creates a new Domain Project Using
	 *                     template project then corresponding Folders are copied
	 *                     from the source template project to the newly created
	 *                     Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54799", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingFolder() {

		baseClass.stepInfo("Test case Id: RPMXCON-54799");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Folders are copied from the source template project to the newly created Project.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		TagsAndFoldersPage folder = new TagsAndFoldersPage(driver);
		folder.navigateToTagsAndFolderPage();
		folder.validateFolderPageCount();

		loginPage.logout();

	}

	/**
	 * @author Mohan.Venugopal Created on : 28/06/2022 Modified On:NA
	 * @description:Verify that when User creates a new Domain Project Using
	 *                     template project then corresponding Tags are copied from
	 *                     the source template project to the newly created Project.
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54798", enabled = true, groups = { "regression" })
	public void userCreateNewDomainUsingTags() {

		baseClass.stepInfo("Test case Id: RPMXCON-54798");
		baseClass.stepInfo(
				"Verify that when User creates a new Domain Project Using template project then corresponding Tags are copied from the source template project to the newly created Project.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, projectName);
		TagsAndFoldersPage folder = new TagsAndFoldersPage(driver);
		folder.navigateToTagsAndFolderPage();
		folder.validateTagsPageCount();

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
