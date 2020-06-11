package testScriptsRegression;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Regression  {
	Driver driver;
	LoginPage lp;
	SavedSearch ss;
	SessionSearch search;
    BaseClass base;
    public static int purehits;
	
	//String searchText = "test";
	String saveSearchName = "test20"+Utility.dynamicNameAppender();
	String SearchNameRMU = "RMU"+Utility.dynamicNameAppender();
	String SearchNamePA = "PA"+Utility.dynamicNameAppender();
	String TagName = "Tag"+Utility.dynamicNameAppender();
	String FolderName = "Folder"+Utility.dynamicNameAppender();
	String codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String assignmentName= "assignmentA1"+Utility.dynamicNameAppender();
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PA user,search for a string and save it as new saved search
	 */		
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
		purehits=search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);
        search.saveSearch(SearchNamePA);
		Thread.sleep(5000);
		ss = new SavedSearch(driver);
        base = new BaseClass(driver);
	}
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify navigation to doclist screen from saved search is displaying docs correctly
	 */
	@Test(groups={"regression"},priority=1)
	public void  saveSearchToDocList() throws ParseException, InterruptedException, NoSuchMethodException, SecurityException {
		
		System.out.println("****************DocList Started*************************"); 
		ss.savedSearchToDocList(saveSearchName);
		final DocListPage dp = new DocListPage(driver);
	    dp.getDocList_info().WaitUntilPresent();
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 10 of "+purehits+" entries");
	    System.out.println("Expected docs("+purehits+") are shown in doclist");

	}
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify navigation to docview screen from saved search is displaying docs correctly
	 */
	@Test(groups={"regression"},priority=2)
	public void  saveSearchToDocView() throws ParseException, InterruptedException {
		System.out.println("****************DocView Started*************************"); 
		ss.savedSearchToDocView(saveSearchName);
	    DocViewPage dv= new DocViewPage(driver);
	    dv.getDocView_info().WaitUntilPresent();
	    Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "+purehits+" Docs");
	    System.out.println("Expected docs("+purehits+") are shown in docView");
	}
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify bulk tag is working correctly from saved search 
	 */
	@Test(groups={"regression"},priority=3)
	public void  SavedSearchBulkTag() throws ParseException, InterruptedException {
		
		System.out.println("****************Bulk Tag Started*************************"); 
		//Schedule the saved search
		
		 ss.SaveSearchToBulkTag(saveSearchName, TagName);
		 final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	
	       tf.getTag_ToggleDocCount().waitAndClick(10);
	       tf.getTagandCount(TagName,purehits).WaitUntilPresent();
	       Assert.assertTrue(tf.getTagandCount(TagName,purehits).Displayed());
	       System.out.println(TagName+" could be seen under tags and folder page");
	}
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify bulk folder is working correctly from saved search 
	 */
	@Test(groups={"regression"},priority=4)
	public void  SavedSearchBulkFolder() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		System.out.println("****************Bulk Folder Started*************************"); 
		 ss.SaveSearchToBulkFolder(saveSearchName, FolderName);
		 final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	   tf.getFoldersTab().waitAndClick(10);
	       tf.getFolder_ToggleDocCount().waitAndClick(10);
	       tf.getFolderandCount(FolderName, purehits).WaitUntilPresent();
	       Assert.assertTrue(tf.getFolderandCount(FolderName, purehits).Displayed());
	       System.out.println(FolderName+" could be seen under tags and folder page");
	}
	
         /*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify bulk export is working correctly from saved search 
	 */
	@Test(groups={"regression"},priority=5)
	public void  SaveSearchExport() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		ss.SaveSearchExport(saveSearchName);
	}
	
    
	
		/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify schedule saved search  is working correctly and status is showing as completed from saved search 
	 */
	@Test(groups={"regression"},priority=6)
	public void  scheduleSavedSearch() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		ss.scheduleSavedSearch(saveSearchName);
		SchedulesPage sp = new SchedulesPage(driver);
		sp.checkStatusComplete(saveSearchName);
	}
	
	

	 /*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify conceptual report  is working correctly from saved search 
	 */	
	@Test(groups={"regression"},priority=7)
	public void savedSearchToConcepts() throws ParseException, InterruptedException {

			//Share the saved search
			
			ss.savedSearchToConcepts(saveSearchName);
	  }
	
          /*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify Tally report  is working correctly from saved search 
	 */
	@Test(groups={"regression"},priority=8)
	public void savedSearchToTally() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.savedSearchToTally(saveSearchName);
  }
       
        /*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify Search Term report  is working correctly from saved search 
	 */
	@Test(groups={"regression"},priority=9)
		public void savedSearchToTermReport() throws ParseException, InterruptedException {

			//Share the saved search
			
			ss.savedSearchToTermReport(saveSearchName);
	  }

	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify new search group is created successfully and search is saved under that group 
	 */
	@Test(groups={"regression"},priority=10)
	public void savedSearchNewSearchGrp() throws ParseException, InterruptedException {

		//Share the saved search
		ss.savedSearchNewSearchGrp(saveSearchName);
  }
	
	 /*
		 * Author : Shilpi Mangal
		 * Created date: 
		 * Modified date: 
		 * Modified by:
		 * Description : Verify execution of existing saved search is working correctly
		 */
	@Test(groups={"regression"},priority=11)
	public void savedSearchExecute() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.savedSearchExecute(saveSearchName, purehits);
  }
	
	@Test(groups={"regression"},priority=12)
	public void savedSearchEdit() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.savedSearchEdit(saveSearchName,SearchNameRMU);
  }

	
	
	 /*
		 * Author : Shilpi Mangal
		 * Created date: 08-01-2020
		 * Modified date: 
		 * Modified by:
		 * Description : Verify sharing of saved searches is working correctly
		 */
	  
	 
	  
@Test(groups={"regression"},priority=14)
	public void SaveSearchToBulkAssign() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.SaveSearchToBulkAssign(saveSearchName,assignmentName,codingfrom);
  }
	
  
/*
 * Author : Shilpi Mangal
 * Created date: 
 * Modified date: 
 * Modified by:
 * Description : Verify deletion of saved searches is working correctly from saved search 
 */
@Test(groups={"regression"},priority=15)
public void  SaveSearchDelete() throws ParseException, InterruptedException {
	
	//Schedule the saved search
	
	ss.SaveSearchDelete(saveSearchName);
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
