package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import automationLibrary.Driver;
import pageFactory.AnnotationLayer;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SystemTemplate_Regression {
	Driver driver;
	LoginPage lp;
		
	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PA user
	 */
	
	 @BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
		Input in = new Input();
		in.loadEnvConfig();
		
		//Open browser
		this.driver = new Driver();
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);   
		
    }
	
	   /*
		 * Author : Shilpi Mangal
		 * Created date: April 2020
		 * Modified date: 
		 * Modified by:
		 * Description : Tc- 8660/Verify that When a Domain project is created then provisioned Annotation Layer is available in the Project- PA/RMU
		 */	
	  // @Test(groups={"smoke","regression"},priority=1)
	   public void AddComments() throws InterruptedException {
		   
			AnnotationLayer al = new AnnotationLayer(driver);
			
		  }
	   
	   /*
		 * Author : Shilpi Mangal
		 * Created date: April 2020
		 * Modified date: 
		 * Modified by:
		 * Description : Test Case 8663:Verify that When a Domain project is created then provisioned Redaction Tags are available in the Project- PA/RMU
 
		 */	
	   @Test(groups={"smoke","regression"},priority=2)
	   public void DeleteComments() throws InterruptedException {
		   
		RedactionPage rp = new RedactionPage(driver);
		rp.findredaction();  
		
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
