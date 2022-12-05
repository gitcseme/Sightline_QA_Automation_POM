package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression_Consilio {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	SessionSearch search;
	SavedSearch savedSearch;
	DocListPage docList;
	TagsAndFoldersPage tagsAndFolderPage;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManagement;
	AssignmentsPage assignmentsPage;
	SoftAssert softAssertion;
	DocExplorerPage docExp;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass=new BaseClass(driver);
		softAssertion = new SoftAssert();
		assignmentsPage = new AssignmentsPage(driver);
		docList = new DocListPage(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		userManagement = new UserManagement(driver);
		docExp=new DocExplorerPage(driver);
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}
	
	
	@Test(description ="RPMXCON-66607",groups = { "regression" })
	public void verifyThumbNailFeatureInDocList() throws InterruptedException {
		baseClass.stepInfo("Thumbnail feature in DocList should in no way affect the performance when docs are being viewed in the list view (not Thumbnails view)");
		baseClass.stepInfo("Test case Id:RPMXCON-66607");	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		docExp.navigateToDocExplorerPage();
		docExp.SelectingAllDocuments("yes");
		docExp.docExpViewInDocList();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docList.getTileView().Visible();
			}
		}), Input.wait60);
		docList.getTileView().waitAndClick(5);
		baseClass.stepInfo("Navigated to Tile view");
		
		baseClass.waitForElement(docList.getGridViewIcon());
		docList.getGridViewIcon().waitAndClick(10);
		baseClass.ValidateElement_Absence(docList.getThumbNailOfFirstDocInDocList(), "ThumbNailofFirstDoc");	
		baseClass.passedStep("Grid View of DocList is displayed");
		baseClass.stepInfo("Navigated to Grid view");
		
		baseClass.waitForElement(docList.getTileView());
		docList.getTileView().waitAndClick(10);
		baseClass.ValidateElement_Presence(docList.getThumbNailOfFirstDocInDocList(), "ThumbNailofFirstDoc");	
		baseClass.passedStep("Tile View of DocList is displayed");
		baseClass.stepInfo("Again Navigated back to Tile view");
	}
	
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

  	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
}
