/*
 * Verify that the Bulk Action From Basic Search Performs as expected
  	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: June 2021
	 * Modified by: Srinivas Anand
	 	* public void bulkFolderInBasicSearch()
	 	* public void bulkTagInBasicSearch()
	 	* public void viewInDocViewInBasicSearch()
	 	* public void viewInTallyResultsInBasicSearch()
 */
package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.Utility;


public class TS_003_BulkActionsFromBasicSearch {
	Driver driver;
	LoginPage loginPage;
	UtilityLog log;
	SessionSearch sessionSearch;	
	static int pureHit;	
	SoftAssert softAssertion;
	BaseClass bc;
	TagsAndFoldersPage tagsAndFolderPage;
	String tagName = "tagName"+Utility.dynamicNameAppender();
	String folderName = "folderName1"+Utility.dynamicNameAppender();
	
	/*
	 * Description : Login as Project Admin User and keep search ready, from here all the scripts will run! 
	 */	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		driver = new Driver();
		//Login as Project Admin
		loginPage=new LoginPage(driver);
	
    	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
				sessionSearch = new SessionSearch(driver);
		 		bc= new BaseClass(driver);
    	 tagsAndFolderPage = new TagsAndFoldersPage(driver);
    	//Search for any content on basic search screen
     	sessionSearch.basicContentSearch(Input.searchString2);
    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());   	        
          softAssertion= new SoftAssert();
	
	
	}
	
	/*
	 * Description : As a Project Admin user validate bulk folder in baisc search  
	 */	
	@Test(groups={"smoke","regression"})
    public void bulkFolderInBasicSearch() throws InterruptedException {
		
		bc.stepInfo("RPMXCON-57210	- Verify that Bulk Folder functionality is working correctly through Advanced Search Screen");
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		sessionSearch.bulkFolder(folderName);
 	}

	/*
	 * Description : As a Project Admin user validate bulk tag in baisc search  
	 */	
	@Test(groups={"smoke","regression"})
   public void bulkTagInBasicSearch() throws InterruptedException {
	   bc.stepInfo("RPMXCON-57209	Verify that Bulk Tag functionality is working correctly through Advanced Search Screen");   
	   driver.getWebDriver().get(Input.url+ "Search/Searches");
	   sessionSearch.bulkTag(tagName);
      
	   //Verify tag in manage tags page!
	   this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       		tagsAndFolderPage.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
  	
       tagsAndFolderPage.getTag_ToggleDocCount().waitAndClick(8);
       Thread.sleep(4000);
       
       Assert.assertTrue(tagsAndFolderPage.getTagandCount(tagName, pureHit).Present());
       
       //System.out.println(tagName+" could be seen under tags and folder page");
       UtilityLog.info(tagName+" could be seen under tags and folder page");
       Reporter.log(tagName+" could be seen under tags and folder page",true);
   
	}
	
	/*	 
	 * Description : As a Project Admin user validate navigation to doclist from basic search  
	 */	
	@Test(groups={"smoke","regression"})
	public void viewInDoclistInBasicSearch() throws InterruptedException {
		
		bc.stepInfo("RPMXCON-47947 -Verify that correct number of documents appears when user Selects \"View In DocList\" action from Basic Search Screen");
	   //navigate to Doclist
       driver.getWebDriver().get(Input.url+"Search/Searches");
       sessionSearch.ViewInDocList();
       final DocListPage dp = new DocListPage(driver);
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	   //Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""), String.valueOf(pureHit));
       Assert.assertTrue(dp.getDocList_info().getText().toString().replaceAll(",", "").contains(String.valueOf(pureHit)));
       Reporter.log("Expected docs("+pureHit+") are shown in doclist",true);
	   UtilityLog.info("Expected docs("+pureHit+") are shown in doclist");
	}
	/*	 
	 * Description : As a Project Admin user validate navigation to docview from basic search  
	 */
	@Test(groups={"smoke","regression"})
   public void viewInDocViewInBasicSearch() throws InterruptedException {

       bc.stepInfo("RPMXCON-47948 - Verify that correct number of documents appears when user Selects \"View In DocView\" action from Basic Search Screen");
       driver.getWebDriver().get(Input.url+"Search/Searches");
       sessionSearch.ViewInDocView();
       DocViewPage dv= new DocViewPage(driver);
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   dv.getDocView_info().Displayed()  ;}}),Input.wait60);
       
       Assert.assertTrue(dv.getDocView_info().getText().toString().replaceAll(",", "").contains(String.valueOf(pureHit)));
   
       Reporter.log("Expected docs("+pureHit+") are shown in docView",true);
	   UtilityLog.info("Expected docs("+pureHit+") are shown in docView");
	}
	
	/*	 
	 * Description : As a Project Admin user validate navigation to tally report from basic search  
	 */
	@Test(groups={"smoke","regression"})
   public void viewInTallyResultsInBasicSearch() throws InterruptedException {
		
	 bc.stepInfo("RPMXCON-47949	Verify that correct number of documents appears when user Selects \"Tally\" action from Basic Search Screen");
		TallyPage tp = new TallyPage(driver);
	   //Tally Results
       sessionSearch.tallyResults();
     
       Assert.assertTrue(tp.getAutoSelectedSearchSource().WaitUntilPresent().Displayed());
     
       Reporter.log("Expected docs("+pureHit+") are shown in tally",true);
	   UtilityLog.info("Expected docs("+pureHit+") are shown in tally");
}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	/*
	 * @AfterMethod(alwaysRun = true) public void takeScreenShot(ITestResult result,
	 * Method testMethod) { Reporter.setCurrentTestResult(result);
	 * UtilityLog.logafter(testMethod.getName()); if (ITestResult.FAILURE ==
	 * result.getStatus()) { Utility bc = new Utility(driver);
	 * bc.screenShot(result);
	 * 
	 * } System.out.println("Executed :" + result.getMethod().getMethodName());
	 * ExtentTestManager.getTest().log(Status.INFO,
	 * this.getClass().getSimpleName()+"/"+testMethod.getName()); }
	 */
	
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
		loginPage.logout();	    
		}finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}
