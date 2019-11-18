package testScriptsRegression;

import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
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
	public void preCondition() throws ParseException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
    	//Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		//Login as a PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	}
	
	@Test(groups={"regression"})
	public void PreviewDocNonAudio() throws InterruptedException {
		
		ss.basicContentSearch(Input.searchString2);
    	ss.ViewInDocList();
    	
    	final DocListPage dl= new DocListPage(driver);
    }
	
	@Test(groups={"regression"})
	public void PreviewDocAudio() throws InterruptedException {
		
		ss.audioSearch("morning", "North American English");
		ss.ViewInDocList();
			
    	final DocListPage dl= new DocListPage(driver);
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
