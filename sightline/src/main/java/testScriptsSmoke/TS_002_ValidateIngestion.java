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
	
	@BeforeSuite(alwaysRun = true) 
	public void AddIngestion(String dataset) throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		driver = new Driver();
		
		System.out.println(dataset);
	  	LoginPage lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
		
	    IngestionPage page1 = new IngestionPage(driver);
     	page1.AddOnlyNewIngestion(dataset);
     	
 	    SessionSearch search = new SessionSearch(driver);
     	search.basicContentSearch(page1.IngestionName);
     	search.bulkRelease("Default Security Group");    	
    
}
	
	@DataProvider(name = "Datasets")
	 public static Object[][] credentials() {
	 
		
	  return new Object[][] { { "Automation_Collection1K_Tally"},{ "Automation_AllSources"},{ "Automation_20Family_20Threaded"}};
	
	 
	  }
	
}
