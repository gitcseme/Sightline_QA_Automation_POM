package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.omg.PortableInterceptor.AdapterNameHelper;
import org.openqa.selenium.Keys;
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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_017_DocList {

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
	
	//To validate masterdate for all image files in doclist
	@Test(groups={"regression"})
	public void masterDateForImageDocs() throws InterruptedException {
		//driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	Assert.assertTrue(ss.basicMetaDataSearch("DocFileExtension", null, ".jpg", null)>=2);
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
		bc.selectproject();
    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getGetDocFIleTypeFilter().Visible()  ;}}), Input.wait30);
		
    	dl.getGetDocFIleTypeFilter().waitAndClick(10);
    	dl.include("HyperText Markup Language");
    	dl.getApplyFilter().Click();
    	dl.validateCount("Showing 1 to 1 of 1 entries");
    
    	//cancel previous filter
    	dl.removeRpeviousFilters();
    
    	dl.getGetDocFIleTypeFilter().waitAndClick(10);
    	dl.exclude("HyperText Markup Language");
    	dl.getApplyFilter().Click();
    	dl.validateCount("Showing 1 to 10 of 1,201 entries");

    	
    	
	}
	/*
    To validate bulk tag and bulk folder in doclist page	
	*/
	@Test(groups={"smoke","regression"})
	public void bulkTagAndBulkFolder() throws InterruptedException {
		String bulkTagName= "A_DocListBulkTag"+Utility.dynamicNameAppender();
		String bulkFolderName= "A_DocListBulkFolder"+Utility.dynamicNameAppender();
		
		//search for string
		bc.selectproject();
    	Assert.assertTrue(ss.basicContentSearch(Input.searchString1)==Input.pureHitSeachString1);
    	
    	//view in doclist
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	
    	//Select all docs and perform bulk tag and bulk folder
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
		
    	dl.getYesAllPageDocs().waitAndClick(10);
    	dl.getPopUpOkBtn().Click();
    	
    	ss.bulkTag(bulkTagName);
    	
    	
    	//Select all docs and perform bulk tag and bulk folder
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
		
    	dl.getYesAllPageDocs().waitAndClick(10);
    	dl.getPopUpOkBtn().Click();
    	
    	ss.bulkFolder(bulkFolderName);
    	
    	//Validate tag and folder in workproduct search
    	bc.selectproject();
    	ss.switchToWorkproduct();
    	ss.selectTagInASwp(bulkTagName);
		Assert.assertEquals(Input.pureHitSeachString1,ss.serarchWP());

    	
    	bc.selectproject();
    	ss.switchToWorkproduct();
    	ss.selectFolderInASwp(bulkFolderName);
		Assert.assertEquals(Input.pureHitSeachString1, ss.serarchWP());
    	
    	
	}
	/*
	To verify navigation from doclist to docview
	*/
	@Test(groups={"smoke","regression"})
	public void doclistToDocView() throws InterruptedException {
		//search for string
		bc.selectproject();
    	Assert.assertTrue(ss.basicContentSearch(Input.searchString1)==Input.pureHitSeachString1);
    	
    	//view in doclist
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	
    	//Select all docs and view in docView
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
		try {
    	dl.getYesAllPageDocs().waitAndClick(10);
		}catch (Exception e) {
			// No child docs
		}
    	dl.getPopUpOkBtn().Click();
    	
    	ss.ViewInDocView();
    	
    	//validate count
        DocViewPage dv= new DocViewPage(driver);
        dv.getDocView_info().WaitUntilPresent();
        Assert.assertEquals("of "+Input.pureHitSeachString1+" Docs",dv.getDocView_info().getText().toString());
        System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in docView");

    	
	}
/*
Create one security group and release docs to it, 
Validate docs in SG through work product search 	
*/	@Test(groups={"smoke","regression"})
	public void doclistBulkRelease() throws InterruptedException {
		
		//Create Security group
		String securitygroupname= "SG"+Utility.dynamicNameAppender();
		SecurityGroupsPage scpage = new SecurityGroupsPage(driver);
		scpage.AddSecurityGroup(securitygroupname);
		System.out.println("Security Group Successful");  
	
		//search for string
		bc.selectproject();
		Assert.assertTrue(ss.basicContentSearch(Input.searchString1)==Input.pureHitSeachString1);
		
		//view in doclist
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	
    	//Select all docs and do bulk release
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
    	dl.getYesAllPageDocs().waitAndClick(10);
    	dl.getPopUpOkBtn().Click();
    	
    	//bulk release to SG
		ss.bulkRelease(securitygroupname);
    	    	
	}

	 	@Test(groups={"regression"})
		public void custodianFiltersInDocList() throws InterruptedException {
			bc.selectproject();
	    	Assert.assertTrue(ss.basicContentSearch("*")==Input.totalNumberOfDocs);
	    	ss.ViewInDocList();
	    	final DocListPage dl= new DocListPage(driver);
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			dl.getCustodianFilter().Visible()  ;}}), Input.wait30);
			//include 
	    	dl.getCustodianFilter().waitAndClick(10);
	    	dl.include("P Allen");
	    	dl.getApplyFilter().Click();
	    	dl.validateCount("Showing 1 to 10 of 1,134 entries");
	    	
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
