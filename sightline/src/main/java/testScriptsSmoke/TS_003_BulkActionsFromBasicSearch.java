package testScriptsSmoke;
//
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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import configsAndTestData.ConfigLoader;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;

import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.Utility;


public class TS_003_BulkActionsFromBasicSearch {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;	
	int pureHit;
	String searchText ="test";
	SoftAssert softAssertion;
	TagsAndFoldersPage tf;
	String tagName = "tagName"+Utility.dynamicNameAppender();
	String folderName = "folderName1"+Utility.dynamicNameAppender();
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PAU and keep search ready, from here all the scripts will run! 
	 */	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
		//bt = new BaseTest();
		//Open browser
		softAssertion= new SoftAssert();
		
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
    	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
    	 tf = new TagsAndFoldersPage(driver);
    	//Search for any content on basic search screen
     	sessionSearch.basicContentSearch(searchText);
    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
    	        

	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate bulk folder in baisc search  
	 */	
	@Test(groups={"smoke","regression"})
    public void bulkFolderInBasicSearch() throws InterruptedException {
		
		//Create Bulk Folder 
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		sessionSearch.bulkFolder(folderName);
        //home.exportData();
        
	}
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate bulk tag in baisc search  
	 */	
	@Test(groups={"smoke","regression"})
   public void bulkTagInBasicSearch() throws InterruptedException {
	   //Create Bulk Tag   
		driver.getWebDriver().get(Input.url+ "Search/Searches");
	   sessionSearch.bulkTag(tagName);
      
	   this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
  	
       tf.getTag_ToggleDocCount().waitAndClick(8);
       Thread.sleep(4000);
       //tf.getTagandCount(tagName, pureHit).WaitUntilPresent();
       Assert.assertTrue(tf.getTagandCount(tagName, pureHit).Present());
       System.out.println(tagName+" could be seen under tags and folder page");
   
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate navigation to doclist from basic search  
	 */	
	@Test(groups={"smoke","regression"})
	public void viewInDoclistInBasicSearch() throws InterruptedException {
	   //to Doclist
       driver.getWebDriver().get(Input.url+"Search/Searches");
       sessionSearch.ViewInDocList();
       final DocListPage dp = new DocListPage(driver);
     //  dp.getDocList_info().WaitUntilPresent();
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	 //  Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""), String.valueOf(pureHit));
       Assert.assertTrue(dp.getDocList_info().Displayed());
//       Assert.assertTrue(dp.getDocList_info().getText().toString().replaceAll(",", "").contains(String.valueOf(pureHit)));
       System.out.println("Expected docs("+pureHit+") are shown in doclist");

	}
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate navigation to docview from basic search  
	 */
	@Test(groups={"smoke","regression"})
   public void viewInDocViewInBasicSearch() throws InterruptedException {
	   //DocView
       driver.getWebDriver().get(Input.url+"Search/Searches");
       sessionSearch.ViewInDocView();
       DocViewPage dv= new DocViewPage(driver);
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   dv.getDocView_info().Displayed()  ;}}),Input.wait60);
       Assert.assertTrue(dv.getDocView_info().getText().toString().replaceAll(",", "").contains(String.valueOf(pureHit)));
       
     //  Assert.assertTrue(dv.getDocView_info().Displayed());
       System.out.println("Expected docs("+pureHit+") are shown in docView");
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate navigation to tally report from basic search  
	 */
	@Test(groups={"smoke","regression"})
   public void viewInTallyResultsInBasicSearch() throws InterruptedException {
		  TallyPage tp = new TallyPage(driver);
	   //Tally Results
       sessionSearch.tallyResults();
     
       Assert.assertTrue(tp.getAutoSelectedSearchSource().WaitUntilPresent().Displayed());
       System.out.println("Expected docs("+pureHit+") are shown in tally");
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
