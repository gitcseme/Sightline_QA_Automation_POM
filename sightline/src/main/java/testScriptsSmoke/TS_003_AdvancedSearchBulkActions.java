package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;

public class TS_003_AdvancedSearchBulkActions {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;	
	int pureHit;
	String searchText ="test";
	
	String tagName = "tagName"+Utility.dynamicNameAppender();
	String folderName = "folderName1"+Utility.dynamicNameAppender();
	
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PAU and keep one search ready!, from here all the scripts will run. 
	 */	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
		//Open browser
		
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
    	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
    	   		
    	//Search for any content on basic search screen
     	sessionSearch.advancedContentSearch(searchText);
    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
    	        

	}
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate bulk tag in advance search  
	 */	
	@Test(groups={"smoke","regression"})
	   public void bulkTagInAdvancedSearch() throws InterruptedException {
		
		//Create Bulk Tag   
		   sessionSearch.bulkTag(tagName);
	       final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	
	       tf.getTag_ToggleDocCount().waitAndClick(8);
	       //tf.getTagandCount(tagName, pureHit).WaitUntilPresent();
	       Thread.sleep(3000);
	       Assert.assertTrue(tf.getTagandCount(tagName, pureHit).Displayed());
	       System.out.println(tagName+" could be seen under tags and folder page");
	   
	}
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate bulk folder in advance search  
	 */	
	@Test(groups={"smoke","regression"})
    public void bulkFolderInAdvancedSearch() throws InterruptedException {
		
		//Create Bulk Folder   
		
		sessionSearch.bulkFolder(folderName);
        //home.exportData();
        
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
     
}
