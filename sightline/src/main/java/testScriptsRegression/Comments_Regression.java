package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.text.ParseException;

import org.testng.ITestResult;
import automationLibrary.Driver;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Comments_Regression {
	Driver driver;
	LoginPage lp;
	CommentsPage cp;
	String commentname = "C"+Utility.dynamicNameAppender();
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PA user
	 */
	
	 @BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		Input input = new Input();
		input.loadEnvConfig();
		
		
		//Open browser
		this.driver = new Driver();
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);   
		 this.driver.getWebDriver().get(Input.url+ "/Comments/CommentsList");
		cp = new CommentsPage(driver);
    }
	
	   /*
		 * Author : Shilpi Mangal
		 * Created date: April 2019
		 * Modified date: 
		 * Modified by:
		 * Description : As a PA user, create new comment  
		 */	
	   @Test(groups={"smoke","regression"},priority=1)
	   public void AddComments() throws InterruptedException {
		   
			cp.AddComments(commentname);
		  }
	   
	   /*
		 * Author : Shilpi Mangal
		 * Created date: April 2019
		 * Modified date: 
		 * Modified by:
		 * Description : As a PA user, delete existing comment  
		 */	
	   @Test(groups={"smoke","regression"},priority=2)
	   public void DeleteComments() throws InterruptedException {
		   
		cp.DeleteComments(commentname);
		
	   }
		   
	@AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
   	 if(ITestResult.FAILURE==result.getStatus()){
   		Utility bc = new Utility(driver);
   		bc.screenShot(result);
   	}
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
