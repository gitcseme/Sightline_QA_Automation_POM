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
	DocListPage dl;
	
	String tagName = "tagSearch"+Utility.dynamicNameAppender();
	String folderName = "folderSearch"+Utility.dynamicNameAppender();
	String saveSearchName = "savedSearch"+Utility.dynamicNameAppender();
	String assignmentName = "assignmentSearch"+Utility.dynamicNameAppender();
	
	
	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
	   //Open browser
		driver = new Driver();
		
		//Login as a PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		dl= new DocListPage(driver);
	}
	
	@Test(priority=1,groups={"regression"})
	public void PreviewDocNonAudio() throws InterruptedException {
		
		bc.stepInfo("Test case Id: RPMXCON-54566 - Preview Document NonAudio");
		bc.stepInfo("****session search*****");
		//final DocListPage dl= new DocListPage(driver);
		ss.basicContentSearch(Input.searchString2);
    	ss.ViewInDocList();
   
    	bc.stepInfo("****validate Non-Audio Document Preview functionality*****");
    	dl.DoclistPreviewNonAudio();
    	bc.passedStep("****Non-Audio Document Preview is done successfully*****");
    }
	
		/*
		 * Author : Shilpi Mangal
		 * Created date: 3/24/2020
		 * Modified date: 
		 * Modified by:
		 * Description : validate that audio Preview Document is working correctly
		 */
		@Test(priority=2,groups={"regression"})
		public void PreviewDocAudio() throws InterruptedException {
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.stepInfo("Test case Id: RPMXCON-54565 - Preview Document Audio");
			bc.stepInfo("****Audio search*****");
			//final DocListPage dl= new DocListPage(driver);
			//driver.getWebDriver().get(Input.url+ "Search/Searches");
			ss.audioSearch("morning", "North American English");
			ss.ViewInDocList();
			bc.stepInfo("****validate Audio Document Preview functionality*****");	
	    	
	    	dl.DoclistPreviewAudio();
	    	bc.passedStep("****Audio Document Preview is done successfully*****");
		}

		//To validate masterdate for all image files in doclist
		@Test(priority=3,groups={"regression"})
		public void masterDateForImageDocs() throws InterruptedException {
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			//final DocListPage DL= new DocListPage(driver);
			bc.stepInfo("*****validate documents using masterDateForImageDocs filter*****");
			driver.getWebDriver().get(Input.url+ "Search/Searches");
	    	Assert.assertTrue(ss.basicMetaDataSearch("DocFileExtension", null, ".jpg", null)>=4);
	    	ss.ViewInDocList();
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			dl.getColumnText(1,6).Visible()  ;}}), Input.wait30);
			 
	    	for (int i = 0; i < dl.getDocListRows().size(); i++) {
	    			Assert.assertEquals( dl.getColumnText(i,6).getText(),"JPEG File Interchange File");
			}
	    	bc.passedStep("*****MasterDate for Image Docs Validation sucessfully*****");
		}
		

		//To validate custodian filter
		@Test(priority=4,groups={"regression"})
		public void masterDateFiltersInDocList() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-54530 - Validate masterDateFilters InDocList");
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	
	    	ss.ViewInDocList();
	    	//final DocListPage dl= new DocListPage(driver);
	    	bc.stepInfo("*****validate documents using date filter1*****");
	    	dl.dateFilter("between", "1980/01/01", "1990/02/15");
	    	dl.validateCount("Showing 1 to 10 of 121 entries");
	    	bc.passedStep("*****Documents count Validation sucessfully*****");
	    	driver.scrollPageToTop();
	    	dl.removeRpeviousFilters();
	    	bc.stepInfo("*****validate documents using date filter2*****");
	    	dl.dateFilter("on", "2000/02/22", null);
	    	dl.validateCount("Showing 1 to 10 of 70 entries");
	    	bc.passedStep("*****Documents count Validation sucessfully*****");
	  	}
		
		@Test(priority=5,groups={"regression"})
		public void docFileTypeInDocList() throws InterruptedException {
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.stepInfo("Test case Id: RPMXCON-53664 - Validate docFileType filter InDocList");
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	ss.ViewInDocList();
	    	//final DocListPage dl= new DocListPage(driver);
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			dl.getGetDocFIleTypeFilter().Visible()  ;}}), Input.wait30);
	    	bc.stepInfo("*****validate documents using FileType filter*****");
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
		
		@Test(priority=6,groups={"regression"})
		public void custodianFiltersInDocList() throws InterruptedException {
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.stepInfo("Test case Id: RPMXCON-53664 - Validate custodianFilters InDocList");
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	ss.ViewInDocList();
	    	//final DocListPage dl= new DocListPage(driver);
	    	bc.stepInfo("*****validate documents using custodian name filter*****");
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			dl.getCustodianFilter().Visible()  ;}}), Input.wait30);
	    	bc.stepInfo("*****Include custodian name filter*****");
			//include 
	    	dl.getCustodianFilter().waitAndClick(10);
	    	dl.include("P Allen");
	    	dl.getApplyFilter().Click();
	    	dl.validateCount("Showing 1 to 10 of 1,134 entries");
	    	bc.passedStep("*****Documents count Validation sucessfully*****");
	    	
	    	//cancel previous filter
	    	dl.removeRpeviousFilters();
	    	bc.stepInfo("*****Exclude custodian name filter*****");
	    	//exclude
	    	dl.getCustodianFilter().waitAndClick(10);
	    	dl.exclude("P Allen");
	    	dl.getApplyFilter().Click();
	    	dl.validateCount("Showing 1 to 10 of 68 entries");
	    	bc.passedStep("*****Documents count Validation sucessfully*****");
	    	
	    	
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
	 	lp.logout();
	     }
		@AfterClass(alwaysRun=true)
		public void close(){
			try{ 
				lp.logout();
			     //lp.quitBrowser();
				}catch(Exception e) {}
			finally {
					lp.quitBrowser();
					lp.clearBrowserCache();
				}
		}

}
