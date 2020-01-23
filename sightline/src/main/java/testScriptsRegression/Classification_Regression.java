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
import pageFactory.ClassificationPage;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Classification_Regression {
	Driver driver;
	LoginPage lp;
	ClassificationPage clssp;
	
	
	 @BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		
		//Open browser
		this.driver = new Driver();
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);   
		clssp = new ClassificationPage(driver);
    }
	

	   @Test(groups={"regression"},priority=1)
	   public void VerifyClassification() throws InterruptedException {
		   
		   clssp.VerifyClassification();
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
