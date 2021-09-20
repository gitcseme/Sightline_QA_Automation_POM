package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression {

	Driver driver; 
	LoginPage lp;
	SessionSearch ss;
	BaseClass bc;
	
	String tagName = "tagSearch"+Utility.dynamicNameAppender();
	String folderName = "folderSearch"+Utility.dynamicNameAppender();
	String saveSearchName = "savedSearch"+Utility.dynamicNameAppender();
	String assignmentName = "assignmentSearch"+Utility.dynamicNameAppender();
	
	
	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	 	
	   //Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		//Login as a PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	}
	
	//@Test(groups={"regression"})
	public void PreviewDocNonAudio() throws InterruptedException {
		
		bc.stepInfo("Test case Id: RPMXCON-54566 - PreviewDocNonAudio");
		bc.stepInfo("****session search*****");
		ss.basicContentSearch(Input.searchString2);
    	ss.ViewInDocList();
   
    	bc.stepInfo("****Verify Non-Audio Document Preview functionality*****");
    	final DocListPage dl= new DocListPage(driver);
    	dl.DoclistPreviewNonAudio();
    	bc.passedStep("****Non-Audio Document Preview is done successfully*****");
    }
	
		/*
		 * Author : Shilpi Mangal
		 * Created date: 3/24/2020
		 * Modified date: 
		 * Modified by:
		 * Description : Verify that audio Preview Document is working correctly
		 */
		@Test(groups={"regression"})
		public void PreviewDocAudio() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-54565 - PreviewDocNonAudio");
			bc.stepInfo("****Audio search*****");
			ss.audioSearch("morning", "North American English");
			ss.ViewInDocList();
			bc.stepInfo("****Verify Audio Document Preview functionality*****");	
	    	final DocListPage dl= new DocListPage(driver);
	    	dl.DoclistPreviewAudio();
	    	bc.passedStep("****Audio Document Preview is done successfully*****");
		}

		//To validate masterdate for all image files in doclist
		@Test(groups={"regression"})
		public void masterDateForImageDocs() throws InterruptedException {
			//driver.getWebDriver().get(Input.url+ "Search/Searches");
	    
	    	Assert.assertTrue(ss.basicMetaDataSearch("DocFileExtension", null, ".jpg", null)>=4);
	    	ss.ViewInDocList();
	    	final DocListPage DL= new DocListPage(driver);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			DL.getColumnText(1,6).Visible()  ;}}), Input.wait30);
			 
	    	for (int i = 0; i < DL.getDocListRows().size(); i++) {
	    			Assert.assertEquals( DL.getColumnText(i,6).getText(),"JPEG File Interchange File");
			}
	    	
		}
		

		//To validate custodian filter
		@Test(groups={"regression"})
		public void masterDateFiltersInDocList() throws InterruptedException {
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	ss.ViewInDocList();
	    	
	    	final DocListPage dl= new DocListPage(driver);
	    	dl.dateFilter("between", "1980/01/01", "1990/02/15");
	    	dl.validateCount("Showing 1 to 10 of 121 entries");
	    	
	    	driver.scrollPageToTop();
	    	dl.removeRpeviousFilters();
	    	
	    	dl.dateFilter("on", "2000/02/22", null);
	    	dl.validateCount("Showing 1 to 10 of 70 entries");
	  	}
		
		@Test(groups={"regression"})
		public void docFileTypeInDocList() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-53664 - docFileTypeInDocList");
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	ss.ViewInDocList();
	    	final DocListPage dl= new DocListPage(driver);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			dl.getGetDocFIleTypeFilter().Visible()  ;}}), Input.wait30);
	    	bc.stepInfo("*****verify documents using FileType filter*****");
	    	dl.getGetDocFIleTypeFilter().waitAndClick(10);
	    	dl.include("HyperText Markup Language");
	    	dl.getApplyFilter().waitAndClick(10);
	    	dl.validateCount("Showing 1 to 1 of 1 entries");
	    	bc.stepInfo("*****cancel previous filter*****");
	    	//cancel previous filter
	    	dl.removeRpeviousFilters();
	    
	    	dl.getGetDocFIleTypeFilter().waitAndClick(10);
	    	dl.exclude("HyperText Markup Language");
	    	dl.getApplyFilter().Click();
	    	dl.validateCount("Showing 1 to 10 of 1,201 entries");
	    	bc.passedStep("*****Documents count Validation sucessfully*****");
}
		
		@Test(groups={"regression"})
		public void custodianFiltersInDocList() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-53664 - custodianFiltersInDocList");
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	ss.ViewInDocList();
	    	final DocListPage dl= new DocListPage(driver);
	    	bc.stepInfo("*****verify documents using custodian name filter*****");
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			dl.getCustodianFilter().Visible()  ;}}), Input.wait30);
			//include 
	    	dl.getCustodianFilter().waitAndClick(10);
	    	dl.include("P Allen");
	    	dl.getApplyFilter().Click();
	    	dl.validateCount("Showing 1 to 10 of 1,134 entries");
	    	bc.passedStep("*****Documents count Validation sucessfully*****");
	    	
	    	//cancel previous filter
	    	dl.removeRpeviousFilters();
	    	
	    	//exclude
	    	dl.getCustodianFilter().waitAndClick(10);
	    	dl.exclude("P Allen");
	    	dl.getApplyFilter().Click();
	    	//dl.validateCount("Showing 1 to 4 of 4 entries");
	    	
	    	
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
		@AfterClass(alwaysRun=true)
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
