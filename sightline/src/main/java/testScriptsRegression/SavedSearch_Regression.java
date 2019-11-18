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
	
	//String searchText = "test";
	String saveSearchName = "test20"+Utility.dynamicNameAppender();
	String SearchNameRMU = "RMU"+Utility.dynamicNameAppender();
	String SearchNamePA = "PA"+Utility.dynamicNameAppender();
	String TagName = "Tag"+Utility.dynamicNameAppender();
	String FolderName = "Folder"+Utility.dynamicNameAppender();
	String codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String assignmentName= "assignmentA1"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	//Open browser
		driver = new Driver();
		//Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		//Search and save it
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(saveSearchName);
		Thread.sleep(5000);
		ss = new SavedSearch(driver);
	}
	
	
	@Test(groups={"regression"},priority=1)
	public void  saveSearchToDocList() throws ParseException, InterruptedException, NoSuchMethodException, SecurityException {
		
		System.out.println("****************DocList Started*************************"); 
		ss.savedSearchToDocList(saveSearchName);
		final DocListPage dp = new DocListPage(driver);
	    dp.getDocList_info().WaitUntilPresent();
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 10 of "+Input.pureHitSeachString1+" entries");
	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in doclist");

	}
	
	@Test(groups={"regression"},priority=2)
	public void  saveSearchToDocView() throws ParseException, InterruptedException {
		
		ss.savedSearchToDocView(saveSearchName);
	    DocViewPage dv= new DocViewPage(driver);
	    dv.getDocView_info().WaitUntilPresent();
	    Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "+Input.pureHitSeachString1+" Docs");
	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in docView");
	}
	
	@Test(groups={"regression"},priority=3)
	public void  SavedSearchBulkTag() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		 ss.SaveSearchToBulkTag(saveSearchName, TagName);
		 final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	
	       tf.getTag_ToggleDocCount().waitAndClick(10);
	       tf.getTagandCount(TagName,Input.pureHitSeachString1).WaitUntilPresent();
	       Assert.assertTrue(tf.getTagandCount(TagName,Input.pureHitSeachString1).Displayed());
	       System.out.println(TagName+" could be seen under tags and folder page");
	}
	
	@Test(groups={"regression"},priority=4)
	public void  SavedSearchBulkFolder() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		 ss.SaveSearchToBulkFolder(saveSearchName, FolderName);
		 final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	   tf.getFoldersTab().waitAndClick(10);
	       tf.getFolder_ToggleDocCount().waitAndClick(10);
	       tf.getFolderandCount(FolderName, Input.pureHitSeachString1).WaitUntilPresent();
	       Assert.assertTrue(tf.getFolderandCount(FolderName, Input.pureHitSeachString1).Displayed());
	       System.out.println(FolderName+" could be seen under tags and folder page");
	}
	
	@Test(groups={"regression"},priority=5)
	public void  SaveSearchExport() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		ss.SaveSearchExport(saveSearchName);
	}
	
	@Test(groups={"regression"},priority=14)
	public void  SaveSearchDelete() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		ss.SaveSearchDelete(saveSearchName);
	}
	
	
	@Test(groups={"regression"},priority=6)
	public void  scheduleSavedSearch() throws ParseException, InterruptedException {
		
		//Schedule the saved search
		
		ss.scheduleSavedSearch(saveSearchName);
		SchedulesPage sp = new SchedulesPage(driver);
		sp.checkStatusComplete(saveSearchName);
	}
	
	@Test(groups={"smoke","regression"},priority=7)
	public void shareSavedSearch() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.shareSavedSearch(SearchNameRMU,"RMU");
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		ss.sharewithUsers(SearchNameRMU,"RMU");
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	    ss.shareSavedSearch(SearchNamePA,"Project Admin");
		lp.logout();
		lp.loginToSightLine(Input.pa2userName, Input.pa2password);
		ss.sharewithUsers(SearchNamePA,"Project Admin");
  }
	
	@Test(groups={"regression"},priority=8)
	public void savedSearchToConcepts() throws ParseException, InterruptedException {

			//Share the saved search
			
			ss.savedSearchToConcepts(saveSearchName);
	  }
	
	@Test(groups={"regression"},priority=9)
	public void savedSearchToTally() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.savedSearchToTally(saveSearchName);
  }
	@Test(groups={"regression"},priority=10)
		public void savedSearchToTermReport() throws ParseException, InterruptedException {

			//Share the saved search
			
			ss.savedSearchToTermReport(saveSearchName);
	  }
	
	@Test(groups={"regression"},priority=11)
	public void savedSearchNewSearchGrp() throws ParseException, InterruptedException {

		//Share the saved search
		BaseClass base = new BaseClass(driver);
		base.selectproject();
		ss.savedSearchNewSearchGrp(saveSearchName);
  }
	@Test(groups={"regression"},priority=12)
	public void SaveSearchToBulkAssign() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.SaveSearchToBulkAssign(saveSearchName,assignmentName,codingfrom);
  }
	
	@Test(groups={"regression"},priority=13)
	public void savedSearchExecute() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.savedSearchExecute(saveSearchName, Input.pureHitSeachString1);
  }
	
	@Test(groups={"regression"})
	public void savedSearchEdit() throws ParseException, InterruptedException {

		//Share the saved search
		
		ss.savedSearchEdit(saveSearchName,SearchNameRMU);
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
