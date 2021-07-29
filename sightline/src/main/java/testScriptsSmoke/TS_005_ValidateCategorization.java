/*
 * @BeforeClass(alwaysRun = true)
 * 	 * Author : Suresh Bavihalli
	 * Created date: May 2019
	 * Test Case ID:
	 * Description : Login as a PAU and create one bulk tag and folder
	 * Modified date: 15-Jun-2021 
	 * Modified by:	Srinivas Anand
	 * Description : valid object Initialization name added was lp made it loginPage.
	 * 
* public void validateCategorization()
* 	 * Author : Suresh Bavihalli
	 * Created date: May 2019
	 * Test Case ID:
	 * Description: Validate categorization with created tag and folder
	 * Modified date: 
	 * Modified by:
	 * Description: 
* @AfterMethod(alwaysRun = true)	 
*	 * Author : Srinivas Anand
	 * Created date: June 2021
	 * Test Case ID:
	 * Description: Execute once the test method is completed.
	 * Modified date: June 2021
	 * Modified by: Srinivas Anand
	 * Description : 
 */
package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_005_ValidateCategorization {
	Driver driver;
	LoginPage login;
	SessionSearch sessionSearch;
	int pureHit;
	
	String tagName = "CatTag2"+Utility.dynamicNameAppender();
	String folderName="CatFolder2"+Utility.dynamicNameAppender();
		
	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		// Need to enable this Input class to load the environment data while executing the class individually
		///Input in = new Input();
		///in.loadEnvConfig();
		
		String searchString = Input.searchString2;	
			
		//Open browser
		driver = new Driver();
		
		//Login as a PA	
		login=new LoginPage(driver);
		login.loginToSightLine(Input.pa1userName, Input.pa1password);   		
    	
		//Search for any content on basic search screen    	
		sessionSearch =new SessionSearch(driver);
		System.out.println(searchString);
    	
		sessionSearch.basicContentSearch(searchString);
    	System.out.println(searchString);
    	
    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
    	
	}
		
	@Test(groups={"smoke","regression"})
	   public void validateCategorization() throws InterruptedException {	
		
		 ExtentTestManager.getTest().log(Status.INFO, "Test Started");
		  ExtentTestManager.getTest().log(Status.INFO, "Logged in as :"+Input.pa1userName);
		  ExtentTestManager.getTest().log(Status.INFO, "Class Name: "+this.getClass().getSimpleName()+"/ Method Name: proximityAndRegExInBasicSearch");
		  ExtentTestManager.getTest().log(Status.INFO, "Search/Parameter Key Word Is TagName: "+ tagName + "/Folder Name: "+folderName);
		  ExtentTestManager.getTest().log(Status.INFO, "Test Case ID : RPMXCON Need to Check");
		//Create Bulk Tag   
		sessionSearch.bulkTag(tagName);
		//Create bulk folder
		sessionSearch.bulkFolder(folderName);
		
		  Categorization cat = new Categorization(driver);
		  if(Input.numberOfDataSets == 1){
			 Assert.assertTrue(cat.runCatWithTagsAndFolders(tagName,folderName)==3);
		  }else{
			 Assert.assertTrue(cat.runCatWithTagsAndFolders(tagName,folderName)>=8);
		  }
		  
		  Reporter.log("Expected documents count shown in categorization result",true);
		  UtilityLog.info("Expected documents count shown in categorization result");
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
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.INFO, this.getClass().getSimpleName()+"/"+testMethod.getName());
	}
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			login.logout();
		     //lp.quitBrowser();	
			}finally {
				login.closeBrowser();
				login.clearBrowserCache();
			}
	}
}
