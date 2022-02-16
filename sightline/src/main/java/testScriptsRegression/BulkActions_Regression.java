package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SecurityGroupsPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	DocExplorerPage docexp;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
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
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 48818 - Verify Total Email Threaded Count when bulk foldering.
	 * @Description : Verify Total Email Threaded Count when bulk foldering.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 1)
	public void verifyTotalEmailThreadedCountbulkFolderingFromDocView() throws Exception {		
		baseClass=new BaseClass(driver);
		String emailSubject = "MID C Question";
		String folderName = Input.randomText+Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-48818 Sprint 12");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("#### Verify Total Email Threaded Count when bulk foldering ####");
	
		docexp = new DocExplorerPage(driver);
		
		baseClass.selectproject("AutomationAdditionalDataProject");
		
		baseClass.stepInfo("Navigating to DocExplorer page");
		docexp.navigateToDocExplorerPage();
		
		docexp.enterFileNameInFileNameFilter(emailSubject);
		
		baseClass.stepInfo("Selecting the document in docExplorer page");
		docexp.selectAllDocumentsFromCurrentPage();
		
		baseClass.stepInfo("View document in doc view on doc explorer");
		driver.waitForPageToBeReady();
		docexp.docExpViewInDocView();
		
		baseClass.stepInfo("Select all documents from mini doc list.");
		int totalDoc = docView.selectAllDocumentsInMiniDocList();
		
		baseClass.stepInfo("Create new folder and add selected document to it");
		docViewMetaDataPage.createNewFolderAndAddSelectedDocument(folderName);
	
		TagsAndFoldersPage folder = new TagsAndFoldersPage(driver);
		
		baseClass.stepInfo("Navigate to tags and folder page.");
		folder.navigateToTagsAndFolderPage();
		
		baseClass.stepInfo("Verify Folder Doc Count");
		folder.verifyFolderDocCount(folderName, totalDoc);
		
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//loginPage.quitBrowser();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
	
}
