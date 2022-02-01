package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanelVerifyCodeAsSame {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SoftAssert softAssertion;
	AssignmentsPage assignmentPage;

	@BeforeClass(alwaysRun = true)
	public void condition1() throws ParseException, InterruptedException, IOException {

		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		System.out.println("*******Loading Files********");

	}

	@BeforeMethod(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		baseClass = new BaseClass(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		// Open browser
		//Input in = new Input();
		//in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully Logged into slightline webpage as Project Admin with "
				+ Input.pa1userName + "");

	}

	
	/**
	 *  @throws Exception 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To Verify code same icon should not display for the documents selected for folder action from mini doc list after selecting folder action from analytics panel.'RPMXCON-51735
'
	 */
	@Test(priority = 1)
	public void verifyCodeAsSameFromFamilyMembersAnalyticsPanel() throws Exception {
		String miniFolderName = "miniFolderName" + Utility.dynamicNameAppender();
		String analyticsFolderName = "analyticsFolderName" + Utility.dynamicNameAppender();
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		
		//Basic Search
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Basic Search is done successfully");
		
		//Get Purehit Count
		docViewPage.getFamilyMemberPurehit();
		baseClass.stepInfo("Doc are selected and viewed in the DocView successfully");
		
		//View From MiniDoc
		docViewPage.MiniDoclistNewFolderCreation();
		sessionSearch.BulkActions_Folder(miniFolderName);
		baseClass.stepInfo("Doc is selected form Mini Doclist and Folder is created successfully");
		
		//View from Analytics Panel
		docViewPage.Analytics_FamilyActionsFolderCreation();
		sessionSearch.BulkActions_Folder(analyticsFolderName);
		baseClass.stepInfo("Doc is selected from Family Member and Folder is created successfully");
		
		//Verify Code As Same
		docViewPage.verifyCodeAsSameInMiniDocList();
		baseClass.stepInfo("Doc is selected from Mini Doclist and Code As Same is verified successfully");
		
		loginPage.logout();
	
	}
	
	
	/**
	 *  @throws Exception 
	 * @Author: Mohan Created date: NA Modified date: NA Modified by: Mohan
	 * @Description : To verify documents folder action is done successfully from analytics panel.'RPMXCON-51733'
	 */
	@Test(priority = 2)
	public void verifyDocFolderAction() throws Exception {

		String analyticsFolderName = "analyticsFolderName" + Utility.dynamicNameAppender();
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		
		//Basic search 
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Basic Search is done successfully");
		
		//Select Purehit from search result
		docViewPage.getFamilyMemberPurehit();
		baseClass.stepInfo("Doc are selected and viewed in the DocView successfully");
		
		//Create a folder in Doc Analytics
		docViewPage.Analytics_FamilyActionsFolderCreation();
		sessionSearch.BulkActions_Folder(analyticsFolderName);
		baseClass.stepInfo("Doc is selected from Family Member and Folder is created successfully");
		
		//Verifying Code as Same Icon
		docViewPage.verifyCodeAsSameInMiniDocList();
		baseClass.stepInfo("Doc is selected from Mini Doclist and Code As Same is verified successfully");
		
		loginPage.logout();
		
	
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

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
		//	LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}
}
