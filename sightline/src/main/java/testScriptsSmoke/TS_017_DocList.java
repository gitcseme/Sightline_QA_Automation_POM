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
	public static int purehits;
	
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
	
	/*
    To validate bulk tag and bulk folder in doclist page	
	*/
	@Test(groups={"smoke","regression"},priority=1)
	public void bulkTagAndBulkFolder() throws InterruptedException {
		String bulkTagName= "A_DocListBulkTag"+Utility.dynamicNameAppender();
		String bulkFolderName= "A_DocListBulkFolder"+Utility.dynamicNameAppender();
		
		bc.selectproject();
		//search for string
		ss.basicContentSearch(Input.searchString1);
    	
    	//view in doclist
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	
    	//Select all docs and perform bulk tag and bulk folder
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
		
    	//dl.getYesAllPageDocs().waitAndClick(10);
    	dl.getPopUpOkBtn().waitAndClick(10);
        
    	
    	ss.bulkTag(bulkTagName);
    	
    	
    	//Select all docs and perform bulk tag and bulk folder
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
		
    	dl.getPopUpOkBtn().waitAndClick(10);
        
    	
    	ss.bulkFolder(bulkFolderName);
    	
    	//Validate tag and folder in workproduct search
    	bc.selectproject();
    	ss.switchToWorkproduct();
    	ss.selectTagInASwp(bulkTagName);
		//Assert.assertEquals(Input.pureHitSeachString1,ss.serarchWP());
    	Assert.assertEquals(10,ss.serarchWP());

    	
    	bc.selectproject();
    	ss.switchToWorkproduct();
    	ss.selectFolderInASwp(bulkFolderName);
		//Assert.assertEquals(Input.pureHitSeachString1, ss.serarchWP());
    	Assert.assertEquals(10,ss.serarchWP());
   }
	
	
	/*
	To verify navigation from doclist to docview
	*/
	@Test(groups={"smoke","regression"})
	public void doclistToDocView() throws InterruptedException {
		//search for string
		bc.selectproject();
		purehits=	ss.basicContentSearch(Input.searchString1);
    	
    	//view in doclist
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	
    	//Select all docs and view in docView
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
	
		dl.getPopUpOkBtn().waitAndClick(10);
	    
    	ss.ViewInDocView();
    	
    	//validate count
        DocViewPage dv= new DocViewPage(driver);
        dv.getDocView_info().WaitUntilPresent();
        Assert.assertEquals("of "+10+" Docs",dv.getDocView_info().getText().toString());
        System.out.println("Expected docs("+purehits+") are shown in docView");

    	
	}
/*
Create one security group and release docs to it, 
Validate docs in SG through work product search 	
*/
	@Test(groups={"smoke","regression"})
	public void doclistBulkRelease() throws InterruptedException {
		
		//Create Security group
		String securitygroupname= "SG"+Utility.dynamicNameAppender();
		SecurityGroupsPage scpage = new SecurityGroupsPage(driver);
		scpage.AddSecurityGroup(securitygroupname);
		System.out.println("Security Group Successful");  
	
		//search for string
		bc.selectproject();
		purehits=ss.basicContentSearch(Input.searchString1);
		
		//view in doclist
    	ss.ViewInDocList();
    	final DocListPage dl= new DocListPage(driver);
    	
    	//Select all docs and do bulk release
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			dl.getSelectAll().Visible()  ;}}), Input.wait30);
		dl.getSelectAll().waitAndClick(10);
    //	dl.getYesAllPageDocs().waitAndClick(10);
    	dl.getPopUpOkBtn().waitAndClick(10);
    	
    	//bulk release to SG
		ss.bulkRelease(securitygroupname);
    	    	
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
