package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BatchPrintPage;
import pageFactory.Categorization;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import pageFactory.WorkflowPage;
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
	
	BatchPrintPage bp;

	
	    @BeforeClass(alwaysRun = true)
	    public void preConditions() throws ParseException, InterruptedException, IOException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		Input in = new Input();
		in.loadEnvConfig();
	 
		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	
	    }
		
		
	     @Test(groups={"regression"})
		 public void BatchPrintWitExceptionalFile() throws InterruptedException {
			  
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
		    ingest.getIngestionName().Click();
		    
		    Thread.sleep(2000);   	    
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		ingest.getIngestionNameText().Visible()  ;}}),Input.wait60); 
		     String SearchNameIngestion = ingest.getIngestionNameText().getText();
			 Thread.sleep(2000);
			System.out.println(SearchNameIngestion);
			
			SessionSearch search = new SessionSearch(driver);
			driver.getWebDriver().get(Input.url+ "Search/Searches");
			search.basicMetaDataSearch("IngestionName", null, SearchNameIngestion, null);
			search.saveSearch(searchnameExcep); 
			
			bp = new BatchPrintPage(driver);
			bp.BatchPrintWitExceptionalFile(searchnameExcep);
		   }
		
		 @Test(groups={"regression"})
		 public void BatchPrintWitMP3() throws InterruptedException {
			  
		 SessionSearch search = new SessionSearch(driver);
		 driver.getWebDriver().get(Input.url+ "Search/Searches");
		 search.audioSearch("morning", "North American English");
		 search.saveSearchAdvanced(searchnameMP3); 
			
		 bp = new BatchPrintPage(driver);
		 bp.BatchPrintWitMP3(searchnameMP3);
		   }
		 
		 //@Test(groups={"regression"})
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
