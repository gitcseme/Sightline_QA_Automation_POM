package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52245 - Verify RMU prints the document with redaction considering the security group which is released to two different security groups and redaction added in both security groups with different annotation layer.
	 * Description : Verify the security group which is released to two different security groups and redaction added in both security groups with different annotation layer.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 1)
	public void verifyDocumentPrintsRedactionBySecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52245");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify RMU prints the document with redaction considering the security group which is released to two different security groups and redaction added in both security groups with different annotation layer. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52244 - Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags.
	 * Description : Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 2)
	public void verifyDocumentPrintsDifferentRedactionsBySecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
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
		baseClass.stepInfo("#### Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		RedactionPage redactionpage=new RedactionPage(driver);
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52246 -Verify RMU prints the document with redaction which is released to two different security groups and redaction added in first security group only with shared annotation layer.
	 * Description : Verify RMU prints the document with redaction which is released to two different security groups and redaction added in first security group only with shared annotation layer.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 3)
	public void verifyDocumentPrintsRedactionsReleasedToSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52246");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify RMU prints the document with redaction which is released to two different security groups and redaction added in first security group only with shared annotation layer. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
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
		//Print is not working properly
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52247 - Verify RMU prints the document with redaction which is released to two different security groups and redaction added in both security groups with shared annotation layer and redaction tags.
	 * Description : Verify RMU prints the document with redaction which is released to two different security groups and redaction added in both security groups with shared annotation layer and redaction tags.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 4)
	public void verifyPrintsRedactionsReleasedToDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
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
		baseClass.stepInfo("#### Verify RMU prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		RedactionPage redactionpage=new RedactionPage(driver);
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52237 - Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in both security groups with different annotation layer.
	 * Description : Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in both security groups with different annotation layer.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 5)
	public void verifPrintDocumentWithoutRedactionAsPA() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52237");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in both security groups with different annotation layer. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52236 - Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags.
	 * Description : Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 6)
	public void verifPrintDocumentPARedactionForFirstSecurityGroup() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52236");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that Project Admin prints the document without redaction which is released to two different security groups and redaction added in first security group only with different annotation layer and shared redaction tags. ####");
		
		RedactionPage redactionpage=new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51999 - Verify the automatically selected redaction tag when shared annotation layer with shared redaction tags in security groups and all propagated documents are not released to security groups.
	 * Description : Verify the automatically selected redaction tag when shared annotation layer with shared redaction tags in security groups and all propagated documents are not released to security groups.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 7)
	public void verifyRedactionForTwoSecurityGroupsWithDupDocuments() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51999");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify the automatically selected redaction tag when shared annotation layer with shared redaction tags in security groups and all propagated documents are not released to security groups. ####");
		
		RedactionPage redactionpage=new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Perform Redaction in SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Redactiontag1);
		
		//Perform Redaction in SG2
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51450 - Verify that after editing the remark should be saved for the document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after editing the remark should be saved for the document when same annotation layer is mapped to different security groups.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 8)
	public void verifyRemarkAddedInSGRefectedToOtherSGWithSameAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51450");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that after editing the remark should be saved for the document when same annotation layer is mapped to different security groups. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		
		//Add Remark
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		docView.addRemarkToNonAudioDocument(10, 20,remark);
		docView.verifyRemarkIsAdded(remark);
		
		//Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);
		docView.editRemark(remark);
		
		//Switch To SG1 for verifying edited remark is added
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51546 - Verify that when two different users under different security group with different annotation layer adds/edit/delete reviewer remark to the same record successfully.
	 * Description : Verify that when two different users under different security group with different annotation layer adds/edit/delete reviewer remark to the same record successfully.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 9)
	public void verifyRemarkAddedInSGRefectedToOtherSGWithDiffAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String remark = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51546");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that when two different users under different security group with different annotation layer adds reviewer remark to the same record successfully ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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

		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		
		//Add Remark
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		docView.addRemarkToNonAudioDocument(10, 20,remark);
		docView.verifyRemarkIsAdded(remark);
		
		//Switch To SG2
		docViewRedact.selectsecuritygroup(namesg3);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView.verifyRemarkIsAdded(remark);
		
		
		//Switch To SG1 for verifying edited remark is added
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
		docViewMetaDataPage.redactbyrectangle(10, 15,Input.defaultRedactionTag);
		
		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51545 - Verify that when two different users under different security group with different annotation layer adds highlighting to the same record successfully.
	 * Description : Verify that when two different users under different security group with different annotation layer adds highlighting to the same record successfully.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 10)
	public void verifyHighlightAddedInSGsGWithDiffAnnotation() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew2 = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51545");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that when two different users under different security group with different annotation layer adds highlighting to the same record successfully ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		
		//Add Highlight
		docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		baseClass.stepInfo("Highlighting documnet text");
		docViewRedact.clickingHighlitingIcon();
		
		
		//Switch To SG1 for verifying edited remark is added
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
		docViewMetaDataPage.redactbyrectangle(10, 15,Input.defaultRedactionTag);
		
		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}
	
	
	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51447 - Verify that annotation should be saved for the document when same annotation layer is mapped to different security groups.
	 * Description : Verify that annotation should be saved for the document when same annotation layer is mapped to different security groups.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 11)
	public void verifyAnnotationSavedWithDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51447");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that annotation should be saved for the document when same annotation layer is mapped to different security groups. ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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


		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Get Annotation Layer Count On Current Document");
		int layers = docView.getAnnotationLayerCountOnCurrentDocument();
		
		baseClass.stepInfo("Perform non audio annotation");
		docView.performNonAudioAnnotation();
		
		
		//Switch To SG2
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51449 - Verify that after editing the annotation should be saved for the document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after editing the annotation should be saved for the document when same annotation layer is mapped to different security groups.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 12)
	public void verifyEditAnnotationSavedWithDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51449");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that after editing the annotation should be saved for the document when same annotation layer is mapped to different security groups ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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
		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Highlighting document text");
		docViewRedact.clickingHighlitingIcon();
		
		
		//Switch To SG2
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 51451 -Verify that after moving the annotation should be saved for the document when same annotation layer is mapped to different security groups.
	 * Description : Verify that after moving the annotation should be saved for the document when same annotation layer is mapped to different security groups
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 12)
	public void verifyAnnotationbyMovingByDifferentSecurityGroups() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51451");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that after moving the annotation should be saved for the document when same annotation layer is mapped to different security groups ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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

		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.familyMembersDocId);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Perform Annotation By Rectangle");
		docViewMetaDataPage.performAnnotationByRectangle(10,15);
		
		
		//Switch To SG2
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
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * TestCase id : 52256 -Verify that after deleting the annotation layer by PA user under 'AllSecurityGroups' should not present respective redactions/annotations/remarks on documents.
	 * Description : Verify that after deleting the annotation layer by PA user under 'AllSecurityGroups' should not present respective redactions/annotations/remarks on documents.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 13)
	public void verifyRemarkOnSecucityGroupByAnnotationlayerDeleted() throws Exception {
		AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		namesg2 = Input.randomText + Utility.dynamicNameAppender();
		namesg3 = Input.randomText + Utility.dynamicNameAppender();
		docExp = new DocExplorerPage(driver);
		baseClass=new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52256");
		utility = new Utility(driver);
		loginPage = new LoginPage(driver);
		loginPage.logout();
		
		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo("#### Verify that after deleting the annotation layer by PA user under 'AllSecurityGroups' should not present respective redactions/annotations/remarks on documents ####");
		
		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		
		//Creating annotation layer and assigning to newly created SGs
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

		docViewRedact.assignAccesstoSGs(namesg2, namesg3,Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Switch To SG1
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
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(false);
		loginPage.logout();
		

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		AnnotationLayer annotation = new AnnotationLayer(driver);
		annotation.deleteAnnotation(AnnotationLayerNew);
		annotation.deleteAnnotation(AnnotationLayerNew1);
		loginPage.logout();
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
			securityGroupsPage.deleteSecurityGroups(namesg3);
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
 	 if(ITestResult.FAILURE==result.getStatus()){
 		 
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 		try{ //if any tc failed and dint logout!
 		loginPage.logout();
 		}catch (Exception e) {
			// TODO: handle exception
		}
 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
}
