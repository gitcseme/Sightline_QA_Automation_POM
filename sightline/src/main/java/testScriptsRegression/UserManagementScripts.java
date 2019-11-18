package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import automationLibrary.Driver;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserManagementScripts {
	Driver driver;
	LoginPage lp;
	
	
	 @BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		//Open browser
		this.driver = new Driver();
		lp=new LoginPage(driver);
		
    }
	

	   @Test(groups={"regression"},priority=1)
	   public void searchUsersusingUserFilter() throws InterruptedException {
		    lp.loginToSightLine(Input.pa1userName, Input.pa1password);   
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			final UserManagement um= new UserManagement(driver);
			um.findUsers("PA1","Project Administrator","active");
			um.findUsers("0QA","Review Manager","active");
			um.findUsers("","Reviewer","inactive");
			lp.logout();
			
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);   
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			um.findUsers("0QA","Review Manager","active");
			um.findUsers("","Reviewer","inactive");
			lp.logout();
			
			lp.loginToSightLine(Input.sa1userName, Input.sa1password);   
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			um.findUsers("0QA","Review Manager","active");
			
			lp.logout();
		  }
	   
	  /* @DataProvider(name = "users")
	    public Object[][] dataProviderMethod1() {
	        return new Object[][] { {Input.pa1userName,Input.pa1password},
	        	{Input.rmu1userName,Input.rmu1password}
	        	
	    };
	    }
	  */// @Test(groups={"regression"},priority=1)
	   public void userFilterCombinations() {
		   lp.loginToSightLine(Input.pa1FullName, Input.pa1password);
		   
		   UserManagement um = new UserManagement(driver);
		   um.getUserNameFilter().SendKeys("PA");
		   um.getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
			
		   //lp.logout();
			
	}
	   
	   /*@Test(groups={"smoke","regression"},priority=1)
	   public void validateShowInactiveUsersBtn() throws InterruptedException {
		   lp.loginToSightLine(Input.sa1userName, Input.sa1password);   
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			final UserManagement um= new UserManagement(driver);
			um.getActiveInactive().Click();
			
		  }*/
	   
	   
	@AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
   	 if(ITestResult.FAILURE==result.getStatus()){
   		Utility bc = new Utility(driver);
   		bc.screenShot(result);
   		try{
   			lp.logout();
   		}catch (Exception e) {
			// TODO: handle exception
		}
   	}
    }
	
	//@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			lp.logout();
		     //lp.quitBrowser();	
			}finally {
				lp.quitBrowser();
				lp.clearBrowserCache();
			}
		LoginPage.clearBrowserCache();
	}
}
