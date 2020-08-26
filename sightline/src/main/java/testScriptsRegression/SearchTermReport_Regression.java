package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SearchTermReport_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	
	String saveSearchName = "ST"+Utility.dynamicNameAppender();
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
		//Open browser
		driver = new Driver();
		
		//Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		//Search and save it
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);
		Thread.sleep(5000);
		
		  SearchTermReportPage st = new SearchTermReportPage(driver);
		}	
	
	  @Test(groups={"regression"})
	  public void ValidateSearchTermreport() throws InterruptedException {
		  
		  SearchTermReportPage st = new SearchTermReportPage(driver);
		  st.ValidateSearchTermreport(saveSearchName);			
		
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
			}
		LoginPage.clearBrowserCache();
	}
}

