package testScriptsSmoke;


import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_005_ValidateCategorization {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;
	int pureHit;
	
	

	String tagName = "CatTag2"+Utility.dynamicNameAppender();
	String folderName="CatFolder2"+Utility.dynamicNameAppender();
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: May 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as a PAU and create one bulk tag and folder
	 */	
	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		/*
		 * Input in = new Input(); in.loadEnvConfig();
		 */
		
		String serachString = Input.searchString2;
		//Open browser
		driver = new Driver();
		//Login as a PA	
    	lp=new LoginPage(driver);
    	lp.loginToSightLine(Input.pa1userName, Input.pa1password);   		
    	//Search for any content on basic search screen
		sessionSearch =new SessionSearch(driver);
		System.out.println(serachString);
    	sessionSearch.basicContentSearch(serachString);
    	System.out.println(serachString);
    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
 
    	
    	//Create Bulk Tag   
		sessionSearch.bulkTag(tagName);
	    //Create bulk folder
	    sessionSearch.bulkFolder(folderName);
	
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: May 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Validate categorization with created tag and folder
	 */	
	@Test(groups={"smoke","regression"})
	   public void validateCategorization() throws InterruptedException {
		  
		  Categorization cat = new Categorization(driver);
		  Assert.assertTrue(cat.runCatWithTagsAndFolders(tagName,folderName)>0);
		  System.out.println("Expected documents count shown in categorization result");
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
