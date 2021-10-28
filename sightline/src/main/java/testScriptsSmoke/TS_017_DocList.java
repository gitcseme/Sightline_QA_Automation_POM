package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_017_DocList {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	BaseClass baseClass;
	public static int purehits;

	String tagName = "tagSearch" + Utility.dynamicNameAppender();
	String folderName = "folderSearch" + Utility.dynamicNameAppender();
	String saveSearchName = "savedSearch" + Utility.dynamicNameAppender();
	String assignmentName = "assignmentSearch" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
       
		Input in = new Input();
		in.loadEnvConfig();
		
		driver = new Driver();
		
		// loginPage as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
	}

	/*
	 * To validate bulk tag and bulk folder in doclist page
	 */
	@Test(groups = { "smoke", "regression" }, priority = 1)
	public void bulkTagAndBulkFolder() throws InterruptedException {
		String bulkTagName = "A_DocListBulkTag" + Utility.dynamicNameAppender();
		String bulkFolderName = "A_DocListBulkFolder" + Utility.dynamicNameAppender();

		baseClass.selectproject();
		// search for string
		sessionSearch.basicContentSearch(Input.searchString1);

		// view in doclist
		sessionSearch.ViewInDocList();
		final DocListPage dl = new DocListPage(driver);

		// Select all docs and perform bulk tag and bulk folder
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {
				return dl.getSelectAll().Visible();}}), Input.wait30);
		dl.getSelectAll().waitAndClick(3);		
		dl.getPopUpOkBtn().waitAndClick(3);

		sessionSearch.bulkTag(bulkTagName);

		// Select all docs and perform bulk tag and bulk folder
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {return dl.getSelectAll().Visible();}}), Input.wait30);
		dl.getSelectAll().waitAndClick(5);
		dl.getPopUpOkBtn().waitAndClick(5);

		sessionSearch.bulkFolder(bulkFolderName);

		// Validate tag and folder in workproduct search
		baseClass.selectproject();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectTagInASwp(bulkTagName);
		Assert.assertTrue(sessionSearch.serarchWP()>=1);

		baseClass.selectproject();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectFolderInASwp(bulkFolderName);
		Assert.assertTrue(sessionSearch.serarchWP()>=1);
	}

	/*
	 * To verify navigation from doclist to docview
	 */
		
	@Test(groups = { "smoke", "regression" }, priority = 2)
	public void doclistToDocView() throws InterruptedException {
		// search for string
		baseClass.selectproject();
		purehits = sessionSearch.basicContentSearch(Input.searchString1);

		// view in doclist
		sessionSearch.ViewInDocList();
		final DocListPage dl = new DocListPage(driver);

		// Select all docs and view in docView
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return dl.getSelectAll().Visible();}}), Input.wait30);
		dl.getSelectAll().waitAndClick(3);

		dl.getPopUpOkBtn().waitAndClick(3);

		sessionSearch.ViewInDocView();

		// validate count
		DocViewPage dv = new DocViewPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return dv.getDocView_info().Visible();}}), Input.wait30);

		Assert.assertTrue(dv.getDocView_info().Displayed());
		System.out.println("Expected docs(" + purehits + ") are shown in docView");
		UtilityLog.info("Expected docs(" + purehits + ") are shown in docView");

	} 

	/*
	 * Create one security group and release docs to it, Validate docs in SG
	 * through work product search
	 */
	
	@Test(groups = { "smoke", "regression" }, priority = 3)
	public void doclistBulkRelease() throws InterruptedException {

		// Create Security group
		String securitygroupname = "SG" + Utility.dynamicNameAppender();
		SecurityGroupsPage scpage = new SecurityGroupsPage(driver);
		scpage.AddSecurityGroup(securitygroupname);
		System.out.println("Security Group Successful");
		UtilityLog.info("Security Group Successful");

		// search for string
		baseClass.selectproject();
		purehits = sessionSearch.basicContentSearch(Input.searchString1);

		// view in doclist
		sessionSearch.ViewInDocList();
		final DocListPage dl = new DocListPage(driver);

		// Select all docs and do bulk release
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return dl.getSelectAll().Visible();
			}
		}), Input.wait30);
		dl.getSelectAll().waitAndClick(3);
		dl.getPopUpOkBtn().waitAndClick(3);

		// bulk release to Security Group
		sessionSearch.bulkRelease(securitygroupname);
	} 

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility basicClass = new Utility(driver);
			basicClass.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());		
	}
	
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//driver.scrollPageToTop();
			loginPage.logout();	
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}
