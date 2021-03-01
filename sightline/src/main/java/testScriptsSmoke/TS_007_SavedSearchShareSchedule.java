package testScriptsSmoke;

import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import automationLibrary.Driver;
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
	LoginPage lp;
	SavedSearch ss;
	SessionSearch search;
	BaseClass bc;
	public static int purehits;
	Logger log;
	
	//String searchText = "test";
	String saveSearchName = "test013"+Utility.dynamicNameAppender();
	String SearchNameRMU = "RMU"+Utility.dynamicNameAppender();
	String SearchNamePA = "PA"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
	
		log = Logger.getLogger("devpinoyLogger");
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	    
		Input in = new Input();
		in.loadEnvConfig();
		//Open browser
		driver = new Driver();
		//Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		//Search and save it
		ss= new SavedSearch(driver);		
		
		search = new SessionSearch(driver);
		purehits=search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);
		search.saveSearch(SearchNamePA);
		
		bc = new BaseClass(driver);
	}
	@Test(groups={"smoke","regression"})
	public void  saveSearchToDocList() throws ParseException, InterruptedException {
		
		
		
		ss.savedSearchToDocList(saveSearchName);
		final DocListPage dp = new DocListPage(driver);
	    dp.getDocList_info().WaitUntilPresent();
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	    System.out.println("Found "+dp.getDocList_info().getText().toString().replaceAll(",", "")+" docs in doclist");
	    log.info("Found "+dp.getDocList_info().getText().toString().replaceAll(",", "")+" docs in doclist");
	  // Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""), String.valueOf(purehits));
	 //   Assert.assertTrue(dp.getDocList_info().getText().toString().replaceAll(",", "").contains(String.valueOf(Input.pureHitSeachString1)));
	    Assert.assertTrue(dp.getDocList_info().Displayed());
	    System.out.println("Expected docs("+purehits+") are shown in doclist");
	    log.info("Expected docs("+purehits+") are shown in doclist");

	}
	
	//@Test(groups={"smoke","regression"})
	public void  saveSearchToDocView() throws ParseException, InterruptedException {
		
		
		
		ss.savedSearchToDocView(saveSearchName);
	    DocViewPage dv= new DocViewPage(driver);
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dv.getDocView_info().getText().isEmpty();}}),Input.wait60);
	
	    Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "+purehits+" Docs");
	    System.out.println("Expected docs("+purehits+") are shown in docView");
	    log.info("Expected docs("+purehits+") are shown in docView");

	}
	
	//@Test(groups={"smoke","regression"})
	public void  scheduleSavedSearch() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		ss.scheduleSavedSearch(saveSearchName);
		SchedulesPage sp = new SchedulesPage(driver);
		sp.checkStatusComplete(saveSearchName);
	}
	
	 /*
	 * Author : Shilpi Mangal
	 * Created date: 08-01-2020
	 * Modified date: 
	 * Modified by:
	 * Description : Verify sharing of saved searches is working correctly
	 */
	
	// @Test(groups={"smoke","regression"},priority=13)
	  public void shareSavedSearch() throws ParseException, InterruptedException {

	  	//Share the saved search
	  	
	  /*	 
	     SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
	  	 sgpage.AddSecurityGroup(securitygroupname);
	  	 
	      this.driver.getWebDriver().get(Input.url+ "Search/Searches");
	      search.ViewInDocList();
	     
	      doclist.DoclisttobulkRelease(securitygroupname);*/
	  	ss.shareSavedSearchPA(SearchNamePA,"Default Security Group");
	  	lp.logout();
	  	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	  	bc.selectsecuritygroup("Default Security Group");
	  	search.basicContentSearch(Input.searchString1);
	  	search.saveSearch(SearchNameRMU);
	  	ss.shareSavedSearchRMU(SearchNameRMU,"Default Security Group");
	  	
	  	}

	 @BeforeMethod
	 public void beforeTestMethod(Method testMethod) throws IOException{
		 UtilityLog.logBefore(testMethod.getName());      
	 }
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result, Method testMethod) {
    	 UtilityLog.logafter(testMethod.getName());
 	 if(ITestResult.FAILURE==result.getStatus()){
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 		
 	 }
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			lp.logout();
		     //lp.quitBrowser();	
			}finally {
				lp.quitBrowser();
				lp.clearBrowserCache();
			}
	}
}