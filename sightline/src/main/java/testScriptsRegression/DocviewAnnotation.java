package testScriptsRegression;

import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class VerifyingAnnotationLayerInSG {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	ProductionPage page;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;

	String namesg2 = "Default_5678" + Utility.dynamicNameAppender();
	String namesg3 = "Default_9581" + Utility.dynamicNameAppender();
	String AnnotationLayerNew = "AnnotationLayerNew1234" + Utility.dynamicNameAppender();


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
		loginPage = new LoginPage(driver);
	}

	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Test Case
	 *         Id: RPMXCON-52259 Description: Creating 2 SGs and assigning new
	 *         annotation layer Checking the redactions applied once the annotation
	 *         layer is deleted In @AfterClass- the method for deleting created
	 *         security groups after resetting the RMU to default
	 */

	@Test(groups = { "regression" }, priority = 1)
	public void verifyAnnotationLayer() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		baseClass.stepInfo("Test case id: RPMXCON 52259");
// creating two new security groups and adding annotation layer
		SecurityGroupsPage securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		baseClass.CloseSuccessMsgpopup();
//Creating annotation layer and assigning to newly created SGs
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction("Default Redaction Tag");
		baseClass.CloseSuccessMsgpopup();
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.clickOnReductionTagAndSelectReduction("Default Redaction Tag");
		baseClass.CloseSuccessMsgpopup();
// Releasing Docs to SGs
		SessionSearch sessionsearch = new SessionSearch(driver);
		
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch("crammer");

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
			docViewRedact.selectsecuritygroup(namesg2);
			docViewRedact.documentSelectionDocExplorer();
			docExp.docExpViewInDocView();
			docViewRedact.clickingRedactionIcon();
			docViewRedact.performThisPageRedaction("Default Redaction Tag");
		docViewRedact.selectsecuritygroup("Default Security Group");
		loginPage.logout();
// deleting newly created annotation layer
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		docViewRedact.deleteAnnotationLayer(AnnotationLayerNew);
		loginPage.logout();
// checking for redaction after deleting annotation layer
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			docViewRedact.selectsecuritygroup(namesg2);
			docViewRedact.documentSelectionDocExplorer();
			docExp.docExpViewInDocView();
			docViewRedact.checkingForRedaction();
			baseClass.stepInfo("Select security group for RMU");
			docViewRedact.selectsecuritygroup(Input.securityGroup);
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			baseClass.stepInfo("Select security group for RMU");
			docViewRedact.selectsecuritygroup("Default Security Group");
			baseClass.stepInfo("RMU2 Assigned to Default Security Group");
			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			securityGroupsPage = new SecurityGroupsPage(driver);
			if (!(namesg2== null)) {
			securityGroupsPage.deleteSecurityGroups(namesg2);
			}
			if (!(namesg3== null)) {
				securityGroupsPage.deleteSecurityGroups(namesg3);
			}
			driver.Navigate().refresh();
			driver.scrollPageToTop();
			loginPage.logout();

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		
			loginPage.quitBrowser();
		
	}

	@AfterClass(alwaysRun = true)
	public void close() throws Exception {
		
	}

}
