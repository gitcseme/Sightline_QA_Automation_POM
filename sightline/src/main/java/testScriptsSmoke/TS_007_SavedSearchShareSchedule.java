package testScriptsSmoke;

import org.testng.annotations.Test;
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
	
	//String searchText = "test";
	String saveSearchName = "test013"+Utility.dynamicNameAppender();
	String SearchNameRMU = "RMU"+Utility.dynamicNameAppender();
	String SearchNamePA = "PA"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
	
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	//Open browser
		driver = new Driver();
		//Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		//Search and save it
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);
		ss= new SavedSearch(driver);		
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
	    Assert.assertTrue(dp.getDocList_info().getText().toString().replaceAll(",", "").contains(String.valueOf(Input.pureHitSeachString1)));
	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in doclist");

	}
	
	@Test(groups={"smoke","regression"})
	public void  saveSearchToDocView() throws ParseException, InterruptedException {
		
		
		
		ss.savedSearchToDocView(saveSearchName);
	    DocViewPage dv= new DocViewPage(driver);
	    dv.getDocView_info().WaitUntilPresent();
	    Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "+Input.pureHitSeachString1+" Docs");
	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in docView");
	}
	
	@Test(groups={"smoke","regression"})
	public void  scheduleSavedSearch() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		ss.scheduleSavedSearch(saveSearchName);
		SchedulesPage sp = new SchedulesPage(driver);
		sp.checkStatusComplete(saveSearchName);
	}
	
	 @Test(groups={"smoke","regression"},priority=13)
	  public void shareSavedSearch() throws ParseException, InterruptedException {

	  	//Share the saved search
	  	
	  	/*	 
	  	 * SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
	  	 * sgpage.AddSecurityGroup(securitygroupname);
	  	 
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
	 public void beforeTestMethod(Method testMethod){
		System.out.println("------------------------------------------");
	    System.out.println("Executing method : " + testMethod.getName());       
	 }
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
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
