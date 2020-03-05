package testScriptsSmoke;

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

import automationLibrary.Driver;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;


public class TS_006_Production {
	Driver driver;
	LoginPage lp;
	
	
	String productionname= "P"+Utility.dynamicNameAppender();
	String PrefixID = "A_"+Utility.dynamicNameAppender();;
	String SuffixID = "_P"+Utility.dynamicNameAppender();;
    String foldername = "FolderProd"+Utility.dynamicNameAppender();;
	String Tagname = "Tag"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		driver = new Driver();
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	
		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		tp.CreateFolder(foldername,"Default Security Group");
		SessionSearch ss = new SessionSearch(driver);
		ss.basicContentSearch("crammer");
		ss.bulkFolderExisting(foldername);
		
		tp.CreateTagwithClassification(Tagname,"Privileged"); 
	}
		
	@Test(groups={"smoke","regression"})
	public void  AddNewProduction() throws ParseException, InterruptedException, NoSuchMethodException, SecurityException {
		
		ProductionPage page1 = new ProductionPage(driver);
		page1.CreateProduction(productionname, PrefixID, SuffixID, foldername,Tagname);

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
