package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression3 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions docViewRedact;
	Input ip;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	SessionSearch sessionsearch;
	DocExplorerPage docExp;
	KeywordPage keywordPage;
	SavedSearch savedSearch;
	BatchRedactionPage bacth;
	ReusableDocViewPage reusableDocView;
	SoftAssert softAssertion;

	String namesg2 = null;
	String namesg3 = null;
	String AnnotationLayerNew = null;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52245 - Verify RMU prints the document with redaction considering the
	 * security group which is released to two different security groups and
	 * redaction added in both security groups with different annotation layer.
	 * Description : Verify the security group which is released to two different
	 * security groups and redaction added in both security groups with different
	 * annotation layer.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyDocumentPrintsRedactionBySecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52245");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document with redaction considering the security group which is released to two different security groups and redaction added in both security groups with different annotation layer. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);

		// Releasing Docs to SGs
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52244 - Verify RMU prints the document without redaction which is
	 * released to two different security groups and redaction added in first
	 * security group only with different annotation layer and shared redaction
	 * tags. Description : Verify RMU prints the document without redaction which is
	 * released to two different security groups and redaction added in first
	 * security group only with different annotation layer and shared redaction
	 * tags.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyDocumentPrintsDifferentRedactionsBySecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52244");
		utility = new Utility(driver);
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag2 = Input.randomText + Utility.dynamicNameAppender();
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		docViewRedact.performThisPageRedaction(Redactiontag1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag2);

		// Releasing Docs to SGs
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52246 -Verify RMU prints the document with redaction which is released
	 * to two different security groups and redaction added in first security group
	 * only with shared annotation layer. Description : Verify RMU prints the
	 * document with redaction which is released to two different security groups
	 * and redaction added in first security group only with shared annotation
	 * layer.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
	public void verifyDocumentPrintsRedactionsReleasedToSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52246");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document with redaction which is released to two different security groups and redaction added in first security group only with shared annotation layer. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		// Print is not working properly
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52247 - Verify RMU prints the document with redaction which is released
	 * to two different security groups and redaction added in both security groups
	 * with shared annotation layer and redaction tags. Description : Verify RMU
	 * prints the document with redaction which is released to two different
	 * security groups and redaction added in both security groups with shared
	 * annotation layer and redaction tags.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 4)
	public void verifyPrintsRedactionsReleasedToDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52247");
		utility = new Utility(driver);
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag2 = Input.randomText + Utility.dynamicNameAppender();
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag2);

		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		driver.waitForPageToBeReady();
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52237 - Verify that Project Admin prints the document without redaction
	 * which is released to two different security groups and redaction added in
	 * both security groups with different annotation layer. Description : Verify
	 * that Project Admin prints the document without redaction which is released to
	 * two different security groups and redaction added in both security groups
	 * with different annotation layer.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 5)
	public void verifPrintDocumentWithoutRedactionAsPA() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52237");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in both security groups with different annotation layer. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew2);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		docViewRedact.selectsecuritygroup(Input.securityGroup);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52236 - Verify that Project Admin prints the document without redaction
	 * which is released to two different security groups and redaction added in
	 * first security group only with different annotation layer and shared
	 * redaction tags. Description : Verify that Project Admin prints the document
	 * without redaction which is released to two different security groups and
	 * redaction added in first security group only with different annotation layer
	 * and shared redaction tags.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 6)
	public void verifPrintDocumentPARedactionForFirstSecurityGroup() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52236");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew2);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag1);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewMetaDataPage.verifyPrintOnDocView();

		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51999 - Verify the automatically selected redaction tag when shared
	 * annotation layer with shared redaction tags in security groups and all
	 * propagated documents are not released to security groups. Description :
	 * Verify the automatically selected redaction tag when shared annotation layer
	 * with shared redaction tags in security groups and all propagated documents
	 * are not released to security groups.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 7)
	public void verifyRedactionForTwoSecurityGroupsWithDupDocuments() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51999");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the automatically selected redaction tag when shared annotation layer with shared redaction tags in security groups and all propagated documents are not released to security groups. ####");

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Perform Redaction in SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag1);

		// Perform Redaction in SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51450 - Verify that after editing the remark should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after editing the remark should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
	public void verifyRemarkAddedInSGRefectedToOtherSGWithSameAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51450");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after editing the remark should be saved for the document when same annotation layer is mapped to different security groups. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		// Add Remark
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		docView.addRemarkToNonAudioDocument(10, 20, remark);
		docView.verifyRemarkIsAdded(remark);

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);
		docView.editRemark(remark);

		// Switch To SG1 for verifying edited remark is added
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51546 - Verify that when two different users under different security
	 * group with different annotation layer adds/edit/delete reviewer remark to the
	 * same record successfully. Description : Verify that when two different users
	 * under different security group with different annotation layer
	 * adds/edit/delete reviewer remark to the same record successfully.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 9)
	public void verifyRemarkAddedInSGRefectedToOtherSGWithDiffAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51546");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that when two different users under different security group with different annotation layer adds reviewer remark to the same record successfully ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew2);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		// Add Remark
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		docView.addRemarkToNonAudioDocument(10, 20, remark);
		docView.verifyRemarkIsAdded(remark);

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);

		// Switch To SG1 for verifying edited remark is added
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51545 - Verify that when two different users under different security
	 * group with different annotation layer adds highlighting to the same record
	 * successfully. Description : Verify that when two different users under
	 * different security group with different annotation layer adds highlighting to
	 * the same record successfully.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
	public void verifyHighlightAddedInSGsGWithDiffAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51545");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that when two different users under different security group with different annotation layer adds highlighting to the same record successfully ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew2);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew2);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		// Add Highlight
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		baseClass.stepInfo("Highlighting documnet text");
		docViewRedact.clickingHighlitingIcon();

		// Switch To SG1 for verifying edited remark is added
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		baseClass.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51447 - Verify that annotation should be saved for the document when
	 * same annotation layer is mapped to different security groups. Description :
	 * Verify that annotation should be saved for the document when same annotation
	 * layer is mapped to different security groups.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 11)
	public void verifyAnnotationSavedWithDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51447");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that annotation should be saved for the document when same annotation layer is mapped to different security groups. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Get Annotation Layer Count On Current Document");
		int layers = docView.getAnnotationLayerCountOnCurrentDocument();

		baseClass.stepInfo("Perform non audio annotation");
		docView.performNonAudioAnnotation();

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Verify Annotation Added To Document");
		docView.verifyAnnotationAddedToDocument(layers);

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51449 - Verify that after editing the annotation should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after editing the annotation should be saved for
	 * the document when same annotation layer is mapped to different security
	 * groups.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 12)
	public void verifyEditAnnotationSavedWithDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51449");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after editing the annotation should be saved for the document when same annotation layer is mapped to different security groups ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Highlighting document text");
		docViewRedact.clickingHighlitingIcon();

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Editing annotation layer of current document.");
		docView.editAnnotationLayerOfCurrentDocument();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 51451 -Verify that after moving the annotation should be saved for the
	 * document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after moving the annotation should be saved for the
	 * document when same annotation layer is mapped to different security groups
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 12)
	public void verifyAnnotationbyMovingByDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51451");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after moving the annotation should be saved for the document when same annotation layer is mapped to different security groups ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.familyMembersDocId);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.familyMembersDocId);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Perform Annotation By Rectangle");
		docViewMetaDataPage.performAnnotationByRectangle(10, 15);

		// Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.familyMembersDocId);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Move Annotation By Rectangle");
		docViewMetaDataPage.moveAnnotationByRectangle();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA TestCase
	 * id : 52256 -Verify that after deleting the annotation layer by PA user under
	 * 'AllSecurityGroups' should not present respective
	 * redactions/annotations/remarks on documents. Description : Verify that after
	 * deleting the annotation layer by PA user under 'AllSecurityGroups' should not
	 * present respective redactions/annotations/remarks on documents.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 13)
	public void verifyRemarkOnSecucityGroupByAnnotationlayerDeleted() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52256");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after deleting the annotation layer by PA user under 'AllSecurityGroups' should not present respective redactions/annotations/remarks on documents ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Perfrompage redaction");
		docView.nonAudioPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotation(AnnotationLayerNew);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		baseClass.stepInfo("Verify redaction,annotation and remark buttons are not displayed.");
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

		baseClass.stepInfo("Verify applied redaction and annotation is not displayed.");
		docView.verifyAppliedRedactionAndAnnotationIsNotDisplayed();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Steffy Created date: NA Modified date: NA Modified by:NA TestCase id
	 * : 48251 - Same doc in different security groups: Verify without shared
	 * annotation layer and shared redaction tags, no redaction info (coordinates,
	 * tags and history) shown in the doc in 2nd sec group
	 * stabilization done
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 14)
	public void verifyRedactionInfoAcrossSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew1 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48251");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"Same doc in different security groups: Verify without shared annotation layer and shared redaction tags, no redaction info (coordinates, tags and history) shown in the doc in 2nd sec group");

		// creating two new security groups and adding annotation layer
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew1);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew1);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Perform Redaction in SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.verifyWhetherRedactionIsSaved(false);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew1);
		loginPage.logout();
	}

	/**
	 * Author : Steffy Created date: NA Modified date: NA Modified by:NA TestCase id
	 * : 47724 - Verify user can not see annotation, redaction, reviewer remarks
	 * icons on doc view after deleting the annotation layer
	 * stabilization done
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyRedactionInfoAfterDletingAnnotationLayer() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		String addComment = "addomment" + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		String codingForm = Input.randomText + Utility.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		agnmt = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47724");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"Verify user can not see annotation, redaction, reviewer remarks icons on doc view after deleting the annotation layer");

		// creating two new security groups and adding annotation layer
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		docViewRedact.assignAccesstoSGs(namesg2, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, Input.rev1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Perfrom non audio remark");
		docView.addRemarkToNonAudioDocument(10, 20, remark);

		baseClass.stepInfo("Perfrompage redaction");
		docView.nonAudioPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		new CommentsPage(driver).AddComments(addComment);
		new CodingForm(driver).commentRequired(codingForm);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssign();
		agnmt.assignmentCreation(assname, codingForm);
		agnmt.assignmentDistributingToReviewer();
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);

		loginPage.logout();
		baseClass.stepInfo("Verifying the redaction , annotation and remark outside of an assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify redaction,annotation and remark buttons are not displayed.");
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

		baseClass.stepInfo("Verify applied redaction and annotation is not displayed.");
		docView.verifyAppliedRedactionAndAnnotationIsNotDisplayed();

		baseClass.stepInfo("Verify added remark is not displayed");
		if (docView.getRemarkPanelItems(remark).isElementAvailable(1)) {
			baseClass.failedStep("Added remark is displayed");
		} else {
			baseClass.passedStep("Added remark is not displayed");
		}

		loginPage.logout();
		baseClass.stepInfo("Verifying the redaction , annotation and remark from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg2);
		agnmt.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify redaction,annotation and remark buttons are not displayed.");
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

		baseClass.stepInfo("Verify applied redaction and annotation is not displayed.");
		docView.verifyAppliedRedactionAndAnnotationIsNotDisplayed();

		baseClass.stepInfo("Verify added remark is not displayed");
		if (docView.getRemarkPanelItems(remark).isElementAvailable(1)) {
			baseClass.failedStep("Added remark is displayed");
		} else {
			baseClass.passedStep("Added remark is not displayed");
		}

		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @TestCase_id : 52257 - Verify that after deleting the annotation layer by PA
	 *              user under specific SecurityGroup should not present respective
	 *              redactions/annotations/remarks.
	 * @Description : Verify that after deleting the annotation layer by PA user
	 *              under specific SecurityGroup should not present respective
	 *              redactions/annotations/remarks.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 16)
	public void verifyRedactionAnnoationtRemarkByAnnotationlayerDeleted() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52257");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after deleting the annotation layer by PA user under specific SecurityGroup should not present respective redactions/annotations/remarks ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Perfrompage redaction");
		docView.nonAudioPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();

		baseClass.stepInfo("Perform Remark with save operation");
		docViewMetaDataPage.performRemarkWithSaveOperation(10, 15, remark);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		docViewRedact.selectsecuritygroup(namesg2);
		baseClass.stepInfo("Navigating to docview from session search");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Verify redaction,annotation and remark buttons are not displayed.");
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

		baseClass.stepInfo("Verify applied redaction and annotation is not displayed.");
		docView.verifyAppliedRedactionAndAnnotationIsNotDisplayed();

		baseClass.stepInfo("Verify highlighted text for already added remark is deleted from document on doc view. ");
		docViewMetaDataPage.verifyHighlightedTextRemarkNotPresentOnDoc();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @TestCase_id : 52258 - Verify that after deleting the annotation layer by RMU
	 *              user should not present respective
	 *              redactions/annotations/remarks.
	 * @Description : Verify that after deleting the annotation layer by RMU user
	 *              should not present respective redactions/annotations/remarks.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 17)
	public void verifyRedactionAnnoationtRemarkByAnnotlayerDeletedByRMU() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52258");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that after deleting the annotation layer by RMU user should not present respective redactions/annotations/remarks. ####");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);

		docView.navigateToDocViewPageURL();

		baseClass.stepInfo("Perfrompage redaction");
		docView.nonAudioPageRedaction(Input.defaultRedactionTag);

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();

		baseClass.stepInfo("Perform Remark with save operation");
		docViewMetaDataPage.performRemarkWithSaveOperation(10, 15, remark);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);

		docViewRedact.selectsecuritygroup(namesg2);
		baseClass.stepInfo("Navigating to docview from session search");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Verify redaction,annotation and remark buttons are not displayed.");
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

		baseClass.stepInfo("Verify applied redaction and annotation is not displayed.");
		docView.verifyAppliedRedactionAndAnnotationIsNotDisplayed();

		baseClass.stepInfo("Verify highlighted text for already added remark is deleted from document on doc view. ");
		docViewMetaDataPage.verifyHighlightedTextRemarkNotPresentOnDoc();

		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51078 Description :Verify user can not redact/annotate in a
	 * document when annotation layer is not added
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyFunctionAvailabilityWhenAnnotationLayerNotAdded() throws Exception {

		String securityGroupName = "Security Group" + Utility.dynamicNameAppender();

		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		// Pre-requisites-Login as PA
		baseClass.stepInfo("Logined as project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51078");
		baseClass.stepInfo("Verify user can not redact/annotate in a document when annotation layer is not added");
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroupName);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(securityGroupName);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(securityGroupName);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(securityGroupName, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(securityGroupName, Input.rev2userName);
		loginPage.logout();
		// Login as RMU and verify
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		baseClass.stepInfo("logged in as RMU");
		docViewRedact.selectsecuritygroup(securityGroupName);
		driver.waitForPageToBeReady();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docView");
		driver.waitForPageToBeReady();
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();
		loginPage.logout();
		// Login as Reviewer and verify
		loginPage.loginToSightLine(Input.rev2userName, Input.rev2password);
		baseClass.stepInfo("logged in as reviewer");
		docViewRedact.selectsecuritygroup(securityGroupName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docView");
		driver.waitForPageToBeReady();
		docView.verifyRedactionAnnotaionAndRemarkButtonsAreDisabled();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @TestCase id: 51053 - Verify RMU/Reviewer can not see the annotations of
	 *           document on doc view page in different security group when
	 *           different annotation layer is mapped to different security groups.
	 * @Descrption : Verify RMU/Reviewer can not see the annotations of document on
	 *             doc view page in different security group when different
	 *             annotation layer is mapped to different security groups
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 19)
	public void verifyAnnatationAcrossDifferentSecurityGroupSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew1 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51053 of Sprint 11");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#####  Verify RMU/Reviewer can not see the annotations of document on doc view page in different security group when different annotation layer is mapped to different security groups ######");

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew1);

		// creating two new security groups and adding annotation layer
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		securityGroupsPage.AddSecurityGroup(namesg3);

		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew1);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Perform Redaction in SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);
		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();
		driver.Navigate().refresh();
		baseClass.stepInfo("Verify annotation layer is added");
		docView.verifyAddedAnnotationToDocument(1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Verify annotation layer is not present");
		docView.verifyAddedAnnotationToDocument(0);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew1);
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 07/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that multi-page redactions should be applied on top of
	 * the individual redactions in the page of document. 'RPMXCON-47032' Sprint :
	 * 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyMultiPageRedationApplyRedationPageDocument() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47032");
		baseClass.stepInfo(
				"Verify that multi-page redactions should be applied on top of the individual redactions in the page of document.");
		baseClass = new BaseClass(driver);
		bacth = new BatchRedactionPage(driver);
		sessionsearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String searchName = "Test" + Utility.dynamicNameAppender();
		String redactiontag = "Tag" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Create saved search
		sessionsearch.basicContentSearch(Input.TallySearch);
		sessionsearch.saveSearch(searchName);

		// perform Batch redaction
		bacth.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		bacth.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);

		// verify History status
		bacth.verifyBatchHistoryStatus(searchName);
		loginPage.logout();

		baseClass.stepInfo("Login with Reviewer Manager");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		Reporter.log("Logged in as User: " + Input.rmu1userName);

		sessionsearch.ViewInDocView();

		// click the Rectangleredaction icon
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		docViewRedact.selectingRectangleRedactionTag();

		// click the text redaction icon
		docViewRedact.doTextRedactWithXYPoints();

		// click the this page Redaction icon
		docViewRedact.performThisPageRedaction(redactiontag);

		String beforeCount = docViewRedact.getDocViewAllRedation().getText();
		System.out.println(beforeCount);
		// click the multipage redaction icon
		docViewRedact.performAllPagesMultiPageRedaction(redactiontag);

		String afterCount = docViewRedact.getDocViewAllRedation().getText();
		System.out.println(afterCount);
		softAssertion.assertNotEquals(beforeCount, afterCount);
		baseClass.passedStep("count of 'All Redactions'are increased");
	}

	/**
	 * Author : Steffy Created date: NA Modified date: NA Modified by:NA TestCase id
	 * : 51052 - Verify RMU/Reviewer can see the annotations of document on doc view
	 * page in different security group if the same annotation layer is mapped to
	 * different security groups
	 * stabilization done
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyAnnotationAcrossSecurityGroupsWhnSameAnnotationLayerIsMapped() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51052");
		baseClass.stepInfo(
				"Verify RMU/Reviewer can see the annotations of document on doc view page in different security group if the same annotation layer is mapped to different security groups");
		utility = new Utility(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User : " + Input.pa1userName + " to create prerequisite SG's and annotation layer");

		baseClass.stepInfo("Creation of Prerequisites");
		baseClass.stepInfo("Creation of two different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(namesg2);
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		baseClass.stepInfo("Creation of annotation layer");
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		baseClass.stepInfo("Sharing same annotation layer to different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Docs are released to both security groups");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		baseClass.stepInfo("Access has been given to RMU and Rev to these security groups");
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);

		loginPage.logout();

		baseClass.stepInfo("Adding annotation to document by RMU user as a prequisite");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();
		driver.waitForPageToBeReady();

		loginPage.logout();
		baseClass.stepInfo("Prerequisite creation is completed successfully");

		baseClass.stepInfo(
				"Login as RMU user , Selecting the first SG and verify whether the annotation is displayed in the document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAnnotationAddedToDocument(0);
		loginPage.logout();

		baseClass.stepInfo(
				"Login as RMU user , Selecting the second SG and verify whether the annotation is displayed in the same document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAnnotationAddedToDocument(0);
		loginPage.logout();

		baseClass.stepInfo(
				"Login as REV user , Selecting the first SG and verify whether the annotation is displayed in the document");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAnnotationAddedToDocument(0);
		loginPage.logout();

		baseClass.stepInfo(
				"Login as REV user , Selecting the second SG and verify whether the annotation is displayed in the same document");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyAnnotationAddedToDocument(0);
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @TestCase id: 51037 - Verify keyword higlighting should be displayed on doc
	 *           view when keyword groups are released to security group and mapped
	 *           to the security group.
	 * @Descrption : Verify keyword higlighting should be displayed on doc view when
	 *             keyword groups are released to security group and mapped to the
	 *             security group.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 22)
	public void verifyKeywordHighlightingReleasedToDifferentSecurityGroup() throws Exception {
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String codingForm = Input.randomText + Utility.dynamicNameAppender();
		String assignName1 = Input.randomText + Utility.dynamicNameAppender();
		String keywordName1 = "key" + Utility.dynamicNameAppender();
		String keyword = "es";
		String rgbCode1 = "rgb(255, 215, 0)";
		String HaxCode1 = "#ffd700";
		String colour1 = "Gold";
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51037 of Sprint 12");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#####  Verify keyword higlighting should be displayed on doc view when keyword groups are released to security group and mapped to the security group. ######");

		// creating two new security groups and adding annotation layer
		baseClass.stepInfo("Navigate to security group URL");
		securityGroupsPage.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("Add security groups");
		securityGroupsPage.AddSecurityGroup(namesg2);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		securityGroupsPage.AddSecurityGroup(namesg3);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Navigate to session search");
		sessionsearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic content search");
		sessionsearch.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Bulk release");
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		baseClass.stepInfo("Assign Access to Security groups");
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Perform Redaction in SG1
		baseClass.stepInfo("Select security group");
		docViewRedact.selectsecuritygroup(namesg2);

		KeywordPage keywordPage = new KeywordPage(driver);

		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Add keyword group");
		keywordPage.addKeywordWithoutFullScreen(keywordName1, keyword, colour1);

		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		baseClass.stepInfo("Navigate to security group URL");
		securityGroupsPage.navigateToSecurityGropusPageURL();

		baseClass.stepInfo("add Keyword To Security Group ");
		securityGroupsPage.addKeywordToSecurityGroup(namesg3, keywordName1);
		baseClass.CloseSuccessMsgpopup();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Select security group");
		docViewRedact.selectsecuritygroup(namesg3);

		sessionsearch = new SessionSearch(driver);
		DocExplorerPage docExplorer = new DocExplorerPage(driver);

		baseClass.stepInfo("Navigate To Doc Explorer Page");
		docExplorer.navigateToDocExplorerPage();

		baseClass.stepInfo("Navigate To Doc list");
		docExplorer.docExplorerToDocList();

		DocListPage docList = new DocListPage(driver);

		baseClass.stepInfo("Select The Document in Doclistpage");
		docList.documentSelection(4);

		baseClass.stepInfo("View in doc view");
		baseClass.waitTime(3);
		docList.docListToDocView();

		docView = new DocViewPage(driver);

		baseClass.stepInfo("Verify persistant hit for keyword of first assignment");
		docView.persistenHitWithSearchString(keyword);

		baseClass.stepInfo("verify highlight keyword in document for first assignment");
		docView.verifyKeywordHighlightedOnDocViewwithKeywordColour(rgbCode1, HaxCode1);

		baseClass.stepInfo("Select security group");
		docViewRedact.selectsecuritygroup(namesg3);

		CodingForm coding = new CodingForm(driver);

		baseClass.stepInfo("Navigate to coding form page");
		coding.navigateToCodingFormPage();

		baseClass.stepInfo("Add coding form");
		coding.createCodingFormWithoutObjects(codingForm);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assignmentPage.createAssignment(assignName1, codingForm);

		baseClass.stepInfo("unmapping all keywords from first assignment");
		assignmentPage.unmappingKeywordsFromEditedAssignment(assignName1);

		docExplorer.docExplorerToDocList();

		baseClass.stepInfo("Select The Document in Doclistpage");
		docList.documentSelection(4);

		baseClass.stepInfo("Bulk Assign the selected Document");
		docList.bulkAssignWithPersistantHit(assignName1);

		baseClass.stepInfo("Navigate To Assignments Page");
		assignmentPage.navigateToAssignmentsPage();

		baseClass.stepInfo("Select assignment to view in Doc view");
		assignmentPage.viewSelectedAssgnUsingPagination(assignName1);
		assignmentPage.Checkclickedstatus(assignName1);
		assignmentPage.assgnViewInAllDocView();

		baseClass.stepInfo("Verify the search string is not displayed on persistent hit panal");
		docView.persistenHitNotContainsWithSearchString(keyword);

		baseClass.stepInfo("Verify keywordis not highlighted on doc view.");
		docView.verifyKeywordNotHighlightOnDocViewKeywordColour(rgbCode1, HaxCode1);

		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @TestCase id : 51544 -Verify that when two different users under different
	 *           security group sharing annotation layer adds/edit/delete reviewer
	 *           remarks to the same record successfully.
	 * @Description : Verify that when two different users under different security
	 *              group sharing annotation layer adds/edit/delete reviewer remarks
	 *              to the same record successfully.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 22)
	public void verifyRemarkDiffSecurityGroupsWhnSameAnnotationLayer() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		String editRemark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51544 Sprint 12");
		baseClass.stepInfo(
				"Verify that when two different users under different security group sharing annotation layer adds/edit/delete reviewer remarks to the same record successfully");
		utility = new Utility(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User : " + Input.pa1userName + " to create prerequisite SG's and annotation layer");

		baseClass.stepInfo("Creation of Prerequisites");
		baseClass.stepInfo("Creation of two different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(namesg2);
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		baseClass.stepInfo("Creation of annotation layer");
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		baseClass.stepInfo("Sharing same annotation layer to different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Docs are released to both security groups");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		baseClass.stepInfo("Access has been given to RMU and Rev to these security groups");
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);

		loginPage.logout();

		baseClass.stepInfo("Adding annotation to document by RMU user as a prequisite");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();

		String currentUrl = driver.getUrl();

		DocViewMetaDataPage docVIewMetaData = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Opening second tab");
		docVIewMetaData.openDuplicateTabOfAlreadyOpenedTab();

		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);

		baseClass.stepInfo("Switching to second tab");
		String parentWindowHandle = reusableDocView.switchTochildWindow();

		baseClass.stepInfo("Impersonate RMU to Reviewer");
		docVIewMetaData.impersonateRMUtoReviewer(namesg2);

		baseClass.stepInfo(" Basic content search");
		sessionsearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("view in docview");
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("Getting : " + currentUrl + " url in second tab");
		driver.getWebDriver().get(currentUrl);

		baseClass.stepInfo("Adding remark to document");
		docView.addRemarkToNonAudioDocument(56, 134, remark);

		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("Edit already added remark");
		docView.editRemark(editRemark);

		baseClass.stepInfo("Switch to parent window from child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindowHandle);

		baseClass.stepInfo("Click on redaction icon");
		docView.redactionIcon().Click();

		baseClass.stepInfo("Verify Disable Remark Warning Message");
		docView.verifyDisableRemarkWarningMessage();

		baseClass.stepInfo("Verify weather delete and edit fields are not enabled.");
		docView.verifyDeleteAndEditFieldsAreNotEnabled();

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		baseClass.stepInfo("verify visibility of edited remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(editRemark);

		baseClass.stepInfo("Log out");
		loginPage.logout();

	}

	/**
	 * @Author : Steffy Created date: NA Modified date: NA Modified by:NA
	 * @TestCase id : RPMXCON- 51543 -Verify that when two different users under
	 *           different security group sharing annotation layer adds/edit/delete
	 *           highlighting to the same record successfully, and confirm that the
	 *           XML nodes are all properly created/reflected in the XML
	 *           stabilization done
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 23)
	public void verifyRemarkPanelRemarkDetailsAfterAddingHighlightingBetweenTwoUsers() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51543");
		baseClass.stepInfo(
				"Verify that when two different users under different security group sharing annotation layer adds/edit/delete highlighting to the same record successfully, and confirm that the XML nodes are all properly created/reflected in the XML");
		utility = new Utility(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User : " + Input.pa1userName + " to create prerequisite SG's and annotation layer");

		baseClass.stepInfo("Creation of Prerequisites");
		baseClass.stepInfo("Creation of two different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(namesg2);
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		baseClass.stepInfo("Creation of annotation layer");
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		baseClass.stepInfo("Sharing same annotation layer to different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Docs are released to both security groups");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		baseClass.stepInfo("Access has been given to RMU and Rev to these security groups");
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();

		baseClass.stepInfo("Adding annotation to document by RMU user as a prequisite");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Adding new remark to document as a prerequisite one");
		baseClass.stepInfo("Perfrom non audio remark");
		docView.addRemarkToNonAudioDocument(10, 20, remark);
		loginPage.logout();

		baseClass.stepInfo("Login in to sightline using first user " + Input.rmu1userName);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		baseClass.stepInfo("logged in as" + Input.rmu1userName);
		baseClass.stepInfo("Searching the docs using basic search and viewing in doc view");
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String firstUserWindow = driver.CurrentWindowHandle();
		baseClass.stepInfo("Opening a new tab");
		baseClass.openNewTab();
		driver.switchToChildWindow();
		baseClass.stepInfo("Navigating to Sighline URL");
		driver.Navigate().to(Input.url);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Login in to sightline using second user " + Input.rmu2userName);
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewRedact.selectsecuritygroup(namesg3);
		baseClass.stepInfo("logged in as" + Input.rmu2userName);
		baseClass.stepInfo("Searching the docs using basic search and viewing in doc view");
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String secondUserWindow = driver.CurrentWindowHandle();
		baseClass.stepInfo("Switching back to first window to delete remark");
		driver.switchToWindow(firstUserWindow);
		baseClass.stepInfo("Perfrom non audio annotation");
		docView.performNonAudioAnnotation();
		baseClass.stepInfo("Switching back to second window to warning message displayed in all 3 panels");
		driver.switchToWindow(secondUserWindow);
		docView.verifyWarningMessage("Annotation");
		docView.verifyAppliedAnnotationSubMenusAreDisabled();
		docView.verifyWarningMessage("Redaction");
		docView.verifyAppliedRedactionSubMenusAreDisabled();
		docView.verifyWarningMessage("Remark");
		baseClass.stepInfo("Verify remark edit is not displayed for existing remarks");
		docView.verifyReviewRemarkActionPanel("Edit", remark);
		baseClass.stepInfo("Verify remark delete is not displayed for existing remarks");
		docView.verifyReviewRemarkActionPanel("Delete", remark);
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @TestCase_id : 52000 - Verify the automatically selected redaction tag when
	 *              shared annotation layer with unshared redaction tags in security
	 *              groups and all propagated documents are not released to security
	 *              groups.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 23)
	public void verifySharedAnnotationLayerWithUnsharedRedactionTag() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		docView = new DocViewPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52000");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify the automatically selected redaction tag when shared annotation layer with unshared redaction tags in security groups and all propagated documents are not released to security groups ####");

		RedactionPage redactionpage = new RedactionPage(driver);

		baseClass.stepInfo("Navigate to redaction tag page");
		redactionpage.navigateToRedactionsPageURL();

		baseClass.stepInfo("Create redaction tag");
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);

		baseClass.stepInfo("Refresh page");
		driver.Navigate().refresh();

		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		securityGroupsPage.selectSecurityGroup(namesg2);

		securityGroupsPage.clickOnReductionTagAndSelectReduction(Redactiontag1);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Click on Redaction Icon");
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Perform This Page Redaction");
		docViewRedact.performThisPageRedaction(Redactiontag1);

		docViewRedact.selectsecuritygroup(namesg3);

		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Click on Redaction Icon");
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Click on page redaction");
		docViewRedact.thisPageRedaction().isElementAvailable(10);
		docViewRedact.thisPageRedaction().Click();

		baseClass.stepInfo("Verify First Option Of Redaction From Dropdown");
		docView.verifyFirstOptionOfRedactionFromDropdown(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}
	/** 
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA 
	 * @TestCase id : 51448 -Verify that remark should be saved for the document when same annotation layer is mapped to different security groups.
	 * @Description : Verify that remark should be saved for the document when same annotation layer is mapped to different security groups.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 22)
	public void verifyRemarkDiffSecurityGroupsSameAnnotationLayer() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass = new BaseClass(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		sessionsearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51448 Sprint 13");
		baseClass.stepInfo(
				"### Verify that remark should be saved for the document when same annotation layer is mapped to different security groups ###");
		utility = new Utility(driver);
		loginPage.logout();

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User : " + Input.pa1userName + " to create prerequisite SG's and annotation layer");

		baseClass.stepInfo("Creation of Prerequisites");
		baseClass.stepInfo("Creation of two different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.AddSecurityGroup(namesg2);
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		baseClass.stepInfo("Creation of annotation layer");
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		baseClass.stepInfo("Sharing same annotation layer to different security groups");
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Docs are released to both security groups");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);

		baseClass.stepInfo("Access has been given to RMU and Rev to these security groups");
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);
		loginPage.logout();

		baseClass.stepInfo("Adding annotation to document by RMU user as a prequisite");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();
		
		baseClass.stepInfo("Adding remark to document");
		docView.addRemarkByText(remark);
		
		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);
		
		loginPage.logout();
		
		baseClass.stepInfo("Adding annotation to document by Rev user as a prequisite");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		driver.waitForPageToBeReady();
	
		baseClass.stepInfo("verify visibility of added remark after reload the document in first tab");
		docView.verifyRemarkIsAdded(remark);
		
		
		baseClass.stepInfo("Log out");
		loginPage.logout();
		loginPage.quitBrowser();
		
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void close() throws ParseException, InterruptedException, IOException {
		try {
			Input in = new Input();
			in.loadEnvConfig();
			baseClass = new BaseClass(driver);
			driver = new Driver();
			driver.Navigate().refresh();
			driver.scrollPageToTop();
			loginPage = new LoginPage(driver);
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			baseClass.stepInfo("Select security group for RMU");
			docViewRedact.selectsecuritygroup("Default Security Group");
			baseClass.stepInfo("RMU2 Assigned to Default Security Group");
			loginPage.logout();
			loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseClass.stepInfo("Select security group for REVIEWER");
			docViewRedact.selectsecuritygroup("Default Security Group");
			baseClass.stepInfo("Reviewer Assigned to Default Security Group");
			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			securityGroupsPage = new SecurityGroupsPage(driver);
			securityGroupsPage.deleteSecurityGroups(namesg2);
			if (!(namesg3== null)) {
				securityGroupsPage.deleteSecurityGroups(namesg3);
			}
			driver.Navigate().refresh();
			driver.scrollPageToTop();
			loginPage.logout();
		} finally {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();
		}

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logout();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
}
