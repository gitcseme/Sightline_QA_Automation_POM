/*
 *	public void saveSearchToDocList()
 *		Author : Srinivas Anand
 * 		Created date: 14-06-2021
 * 		Test Case ID:
 * 		Description : Verify saved searches is working correctly With Doclist
 * 		Modified by: Srinivas Anand
 * 		Modified date: 14-06-2021
 * 		Description: Modified Search and Filter functionality which was missing
 * 
 *	public void saveSearchToDocView()
 *		Author : Srinivas Anand
 * 		Created date: 14-06-2021
 * 		Test Case ID:
 * 		Description : Verify saved searches is working correctly With DocView
 * 		Modified by: Srinivas Anand
 * 		Modified date: 14-06-2021
 * 		Description: Modified Search and Filter functionalitites which was missing
 * 
 *	public void scheduleSavedSearch()
 *		Author : Srinivas Anand
 * 		Created date: 14-06-2021
 * 		Test Case ID:
 * 		Description : Verify Schedule saved searches is working correctly
 * 		Modified by: Srinivas Anand
 * 		Modified date: 14-06-2021
 * 		Description: Modified Search and Filter functionalitites which was missing
 *  
 *  public void shareSavedSearch()  
 * 		Author : Shilpi Mangal
 * 		Created date: 08-01-2020
 * 		Test Case ID
 * 		Description : Verify sharing of saved searches is working correctly
 * 		Modified by: Srinivas Anand
 * 		Modified date: 14-06-2021
 * 		Description: Modified Search and Filter functionalitites which was missing
 * 
 * 
 */
package testScriptsSmoke;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_007_SavedSearchShareSchedule {
	Driver driver;
	LoginPage loginPage;
	SavedSearch sessionSearch;
	SessionSearch search;
	BaseClass baseClass;
	public static int purehits;
	
	//String searchText = "test";
	String saveSearchName = "test013"+Utility.dynamicNameAppender();
	String SearchNameRMU = "RMU"+Utility.dynamicNameAppender();
	String SearchNamePA = "PA"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		///Input in = new Input(); in.loadEnvConfig();
		
		//Open browser
		driver = new Driver();
		
		//Login as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		//Search and save it
		sessionSearch= new SavedSearch(driver);		
		
		search = new SessionSearch(driver);
		purehits=search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);
		search.saveSearch(SearchNamePA);
		baseClass = new BaseClass(driver);
	}
	
	
	  @Test(groups={"smoke","regression"},priority=1) public void saveSearchToDocList() throws
	  ParseException, InterruptedException {
	  
	  sessionSearch.savedSearchToDocList(saveSearchName); final DocListPage dp =
	  new DocListPage(driver); dp.getDocList_info().WaitUntilPresent();
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
	  !dp.getDocList_info().getText().isEmpty() ;}}),Input.wait60);
	  System.out.println("Found "+dp.getDocList_info().getText().toString().
	  replaceAll(",", "")+" docs in doclist");
	  Assert.assertTrue(dp.getDocList_info().Displayed());
	  System.out.println("Expected docs("+purehits+") are shown in doclist");
	  UtilityLog.info("Expected docs("+purehits+") are shown in doclist");
	  
	  }
	 
	  @Test(groups={"smoke","regression"},priority=2) public void saveSearchToDocView() throws
	  ParseException, InterruptedException {
	  
	  sessionSearch.savedSearchToDocView(saveSearchName); DocViewPage dv= new
	  DocViewPage(driver); driver.WaitUntil((new Callable<Boolean>() {public
	  Boolean call(){return
	  !dv.getDocView_info().getText().isEmpty();}}),Input.wait60);
	  Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "
	  +purehits+" Docs");
	  System.out.println("Expected docs("+purehits+") are shown in docView");
	  UtilityLog.info("Expected docs("+purehits+") are shown in docView");
	  
	  }
	 
	  @Test(groups={"smoke","regression"},priority=3) public void scheduleSavedSearch() throws
	  ParseException, InterruptedException {
	  
	  //Schedule the saved search
	  sessionSearch.scheduleSavedSearch(saveSearchName); SchedulesPage sp = new
	  SchedulesPage(driver); sp.checkStatusComplete(saveSearchName); }
	  		 
	
	@Test(groups={"smoke","regression"},priority=4)
	  public void shareSavedSearch() throws ParseException, InterruptedException {
		
		sessionSearch.shareSavedSearchPA(SearchNamePA,"Default Security Group");
	  	loginPage.logout();
	  	
	  	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	  	baseClass.selectsecuritygroup("Default Security Group");
	  	search.basicContentSearch(Input.searchString1);
	  	search.saveSearch(SearchNameRMU);
	  	sessionSearch.shareSavedSearchRMU(SearchNameRMU,"Default Security Group");
	  	
	  	}
	
	@Test(groups={"smoke","regression"},priority=5)
	  public void shareSavedSearch() throws ParseException, InterruptedException {
		
		sessionSearch.shareSavedSearchPA(SearchNamePA,"Default Security Group");
	  	loginPage.logout();
	  	
	  	loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
	  	baseClass.selectsecuritygroup("Default Security Group");
	  	search.basicContentSearch(Input.searchString1);
	  	search.saveSearch(SearchNameRMU);
	  	
	  	
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
				Utility baseClass = new Utility(driver);
				baseClass.screenShot(result);
			}
			System.out.println("Executed :" + result.getMethod().getMethodName());			
		}
		
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			loginPage.logout();
		     //loginPage.quitBrowser();	
			}finally {
				loginPage.quitBrowser();
				loginPage.clearBrowserCache();
			}
	}
}
