package testScriptsSmoke;

import java.io.IOException;
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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.Utility;


public class TS_013_ValidateBatchUpload {
	Driver driver;
	LoginPage lp;
	SavedSearch saveSearch;
	BaseClass bc;
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		Input in = new Input();
		in.loadEnvConfig();
		
		//Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		lp=new LoginPage(driver);
		
	}
	@AfterMethod(alwaysRun = true)
	private void user() {
		 lp.logout();

	}
	@Test(groups={"smoke","regression"})
	   public void batchUploadByPA() throws InterruptedException {
		//Login as a PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch = new SavedSearch(driver);
		
		saveSearch.uploadBatchFile(saveSearch.renameFile());
	
		
	}
	@Test(groups={"smoke","regression"})
	   public void batchUploadByRMU() throws InterruptedException {
		//Login as a PA
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		saveSearch = new SavedSearch(driver);
		saveSearch.uploadBatchFile(saveSearch.renameFile());
	
		
	}
	@Test(groups={"smoke","regression"})
	   public void batchUploadByReviewer() throws InterruptedException {
		//Login as a PA
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		saveSearch = new SavedSearch(driver);
		saveSearch.uploadBatchFile(saveSearch.renameFile());
	
		
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
			   driver.scrollPageToTop();
				lp.logout();
			     //lp.quitBrowser();	
				}finally {
					lp.quitBrowser();
					LoginPage.clearBrowserCache();
				}
		}
}
