package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;


public class BatchPrint_Regression {
	Driver driver;
	LoginPage lp;
	String searchText;
	String searchname= "BP"+Utility.dynamicNameAppender();
	String searchnameMP3= "BP"+Utility.dynamicNameAppender();
	String searchnameExcep= "BP"+Utility.dynamicNameAppender();
	String orderCriteria = "DocID";
	String orderType = "Asc";
	String SearchNameIngestion;
	BaseClass bc;
	
	BatchPrintPage bp;

	
	    @BeforeClass(alwaysRun = true)
	    public void preConditions() throws ParseException, InterruptedException, IOException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		
			
		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	
	    }
		
		
	     @Test(groups={"regression"})
		 public void BatchPrintWitExceptionalFile() throws InterruptedException {
			  
			 bc.stepInfo("Test Case id:RPMXCON-47464-BatchPrintWitExceptionalFile");
			//  lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			   final IngestionPage ingest = new IngestionPage(driver);
			//  driver.getWebDriver().get(Input.url+ "Ingestion/Home");
			 Thread.sleep(2000);   	
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 ingest.getFilterByButton().Visible();}}), Input.wait30); 
			 ingest.getFilterByButton().waitAndClick(Input.wait30);
		    	
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		ingest.getFilterByPUBLISHED().Visible()  ;}}), Input.wait30); 
		    ingest.getFilterByPUBLISHED().waitAndClick(Input.wait30);		 
		    
		    ingest.getFilterByButton().waitAndClick(Input.wait30);
		    Thread.sleep(2000);   	
		  
		   
		    
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			ingest.getIngestionName().Visible()  ;}}),Input.wait30); 
		 
		    String SearchNameIngestion1 =  ingest.getIngestionName().GetAttribute("title");
		    
		    Thread.sleep(3000);
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionName1().Visible()  ;}}),Input.wait30);    
		    String SearchNameIngestion2= ingest.getIngestionName1().GetAttribute("title");
		    
		    Thread.sleep(3000);
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			ingest.getIngestionName2().Visible()  ;}}),Input.wait30); 
		    String SearchNameIngestion3= ingest.getIngestionName2().GetAttribute("title");
		    
		    
	    
		if(SearchNameIngestion1.contains("Automation_AllSources")) {
			
			SearchNameIngestion=SearchNameIngestion1;
		}
			else if(SearchNameIngestion2.contains("Automation_AllSources"))
					{
				
				SearchNameIngestion=SearchNameIngestion2;
					
			}
			else {
				
				
				SearchNameIngestion=SearchNameIngestion3;
			
		}
	    
			Thread.sleep(2000);
			System.out.println(SearchNameIngestion);
			
			SessionSearch search = new SessionSearch(driver);
			driver.getWebDriver().get(Input.url+ "Search/Searches");
			search.basicMetaDataSearch("IngestionName", null, SearchNameIngestion, null);
			search.saveSearch(searchnameExcep); 
			
			bp = new BatchPrintPage(driver);
			bp.BatchPrintWitExceptionalFile(searchnameExcep);
			bc.passedStep("verified user can view details in Exceptional files");
			
		   }
		
		 @Test(groups={"regression"})
		 public void BatchPrintWitMP3() throws InterruptedException {
			
	     bc.stepInfo("Test Case id:RPMXCON-47481-BatchPrintWitMP3");
			 
		 SessionSearch search = new SessionSearch(driver);
		 driver.getWebDriver().get(Input.url+ "Search/Searches");
		 search.audioSearch("morning", "North American English");
		 search.saveSearchAdvanced(searchnameMP3); 
		
		 
		 bp = new BatchPrintPage(driver);
		 
		 bp.BatchPrintWitMP3(searchnameMP3);
		 bc.passedStep("verified error info is displayed");
		
		   }
		 
		 @Test(groups={"regression"})
		 public void BatchPrintWithProduction() throws InterruptedException {
			 
		 SessionSearch search = new SessionSearch(driver);
		 searchText =Input.searchString1;
		 search.basicContentSearch(searchText);
		 search.saveSearch(searchname);
		 
		 bp = new BatchPrintPage(driver);
		 bp.BatchPrintWithProduction(searchname, orderCriteria, orderType);
		   }
		
		
	@AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
 	 if(ITestResult.FAILURE==result.getStatus()){
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 	}
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
