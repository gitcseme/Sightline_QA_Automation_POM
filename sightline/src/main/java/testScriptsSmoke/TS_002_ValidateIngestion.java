package testScriptsSmoke;

import java.io.IOException;
import java.text.ParseException;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;


public class TS_002_ValidateIngestion {
	Driver driver;
	LoginPage lp;
	 IngestionPage page;
	
	@BeforeSuite(alwaysRun = true) 
	public void AddIngestion() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
			driver = new Driver();
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
		page = new IngestionPage(driver);
}
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Validate Add only Ingestion is working correctly
	 */
	//@Test(groups={"regression,smoke"},priority=3)
	public void AddOnlyIngestion(String dataset) throws InterruptedException {
		
		System.out.println(dataset);
		  
		
	     page.AddOnlyNewIngestion(dataset);
	     	
	 	 SessionSearch search = new SessionSearch(driver);
	     search.basicContentSearch(page.IngestionName);
	     search.bulkRelease("Default Security Group");    	
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Validate Overlay Ingestion is working correctly
	 */
	//@Test(groups={"regression,smoke"},priority=3)
	public void OverlayIngestionFiles(String dataset) throws InterruptedException {
		
		System.out.println(dataset);
		page.ReIngestionofNativeWithOverlay(dataset);
	 }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Validate Overlay Ingestion with metadata is working correctly
	 */
	@Test(groups={"regression,smoke"},priority=3)
	public void MetadataOverlayIngestion() throws InterruptedException {
		
    //		System.out.println(dataset);
//		page.MetadataOverlay("Automation_AllSources");
		}
	
	
	 @DataProvider(name = "Datasets")
	 public static Object[][] credentials() {
	 
		
	  return new Object[][] { { "Automation_AllSources"}};
	
	 
	  }
	
}
