package sightline.docview;

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
import pageFactory.AnnotationLayer;
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

public class Docview_Annotation_Regression10 {
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

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	}

	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Test Case
	 *         Id: RPMXCON-52259 Description: Creating 2 SGs and assigning new
	 *         annotation layer Checking the redactions applied once the annotation
	 *         layer is deleted In @AfterClass- the method for deleting created
	 *         security groups after resetting the RMU to default
	 */

	@Test(description = "RPMXCON-52259", enabled =true,groups = { "regression" })
	public void verifyAnnotationLayer() throws Exception {
		AnnotationLayer annotation = new AnnotationLayer(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		baseClass.stepInfo("Test case id: RPMXCON 52259");

		// creating two new security groups and adding annotation layer
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		if (baseClass.getCloseSucessmsg().isElementAvailable(5)) {
			baseClass.CloseSuccessMsgpopup();
		}

		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);
		if (baseClass.getCloseSucessmsg().isElementAvailable(5)) {
			baseClass.CloseSuccessMsgpopup();
		}

		// Creating annotation layer and assigning to newly created SGs
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);

		if (baseClass.getCloseSucessmsg().isElementAvailable(5)) {
			baseClass.CloseSuccessMsgpopup();
		}

		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.assignAnnotationToSG(AnnotationLayerNew);

		if (baseClass.getCloseSucessmsg().isElementAvailable(5)) {
			baseClass.CloseSuccessMsgpopup();
		}

		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		if (baseClass.getCloseSucessmsg().isElementAvailable(5)) {
			baseClass.CloseSuccessMsgpopup();
		}

		securityGroupsPage.selectSecurityGroup(namesg3);
		securityGroupsPage.assignRedactionTagtoSG(Input.defaultRedactionTag);
		if (baseClass.getCloseSucessmsg().isElementAvailable(5)) {
			baseClass.CloseSuccessMsgpopup();
		}

		// Releasing Docs to SGs
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkRelease(namesg2);

		baseClass.selectproject();
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkRelease(namesg3);
		docViewRedact.assignAccesstoSGs(namesg2, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg3, Input.rmu1userName);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		try {
			docViewRedact.selectsecuritygroup(namesg2);
			docViewRedact.documentSelectionDocExplorer();
			docExp.docExpViewInDocView();
			docViewRedact.clickingRedactionIcon();
			docViewRedact.performThisPageRedaction("Default Redaction Tag");
		} catch (InterruptedException e1) {
			driver.waitForPageToBeReady();
			docViewRedact.selectsecuritygroup(namesg3);
			docViewRedact.documentSelectionDocExplorer();
			docExp.docExpViewInDocView();
			docViewRedact.clickingRedactionIcon();
			docViewRedact.performThisPageRedaction("Default Redaction Tag");
		}

		docViewRedact.selectsecuritygroup("Default Security Group");
		loginPage.logout();

		// deleting newly created annotation layer
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		annotation.deleteAnnotationByPagination(AnnotationLayerNew);
		loginPage.logout();

		// checking for redaction after deleting annotation layer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		try {
			docViewRedact.selectsecuritygroup(namesg2);
			docViewRedact.documentSelectionDocExplorer();
			docExp.docExpViewInDocView();
			docViewRedact.checkingForRedaction();
		} catch (InterruptedException e) {
			driver.waitForPageToBeReady();
			docViewRedact.selectsecuritygroup(namesg3);
			docViewRedact.documentSelectionDocExplorer();
			docExp.docExpViewInDocView();
			docViewRedact.checkingForRedaction();

		}
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} finally {
			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() throws Exception {
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.deleteSecurityGroups(namesg2);
		securityGroupsPage.deleteSecurityGroups(namesg3);
		loginPage.logout();
		loginPage.quitBrowser();
	}

}
