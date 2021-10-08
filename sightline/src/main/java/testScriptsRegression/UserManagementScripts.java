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
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserManagementScripts {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	
	 @BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		//Open browser
		this.driver = new Driver();
		lp=new LoginPage(driver);
		 bc=new BaseClass(driver);
		
    }
	

	   @Test(groups={"regression"},priority=1)
	   public void searchUsersusingUserFilter() throws InterruptedException {
		   bc.stepInfo("Test CaseId : RPMXCON-53176, Verify when user enters part of First name OR Last Name or Email Address to search from Manage Users page and clicks to Apply");
		    lp.loginToSightLine(Input.pa1userName, Input.pa1password); 
		    bc.passedStep("*********logged in as a PA user********");
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			final UserManagement um= new UserManagement(driver);
			um.findUsers("PA1","Project Administrator","active");
			 bc.passedStep("*********Project Administrator active users verified********");
			um.findUsers("0QA","Review Manager","active");
			 bc.passedStep("*********active Review Manager users verified********");
			um.findUsers("","Reviewer","inactive");
			 bc.passedStep("*********active Reviewer  users verified********");
			lp.logout();
			bc.passedStep("*********logged out of the application successfully********");
			
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
			bc.passedStep("*********logged in as a PA user********");
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			um.findUsers("0QA","Review Manager","active");
			bc.passedStep("*********active Review Manager users verified********");
			um.findUsers("","Reviewer","inactive");
			 bc.passedStep("*********active Reviewer  users verified********");
			lp.logout();
			bc.passedStep("*********logged out of the application successfully********");
			
			lp.loginToSightLine(Input.sa1userName, Input.sa1password);   
			bc.passedStep("*********logged in as a system user********");
			this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
			um.findUsers("0QA","Review Manager","active");
			bc.passedStep("*********active Reviewer  users verified********");
			
			lp.logout();
			bc.passedStep("*********logged out of the application successfully********");
		  }
	   
	  /* @DataProvider(name = "users")
	    public Object[][] dataProviderMethod1() {
	        return new Object[][] { {Input.pa1userName,Input.pa1password},
	        	{Input.rmu1userName,Input.rmu1password}
	        	
	    };
	    }
	  */// @Test(groups={"regression"},priority=1)
	   public void userFilterCombinations() {
		   bc.stepInfo("Test CaseId : RPMXCON-53180, Verify when user enters Last Name, select the role to search in 'Filter by user name' text box and hits enter key");
		   lp.loginToSightLine(Input.pa1FullName, Input.pa1password);
		   bc.passedStep("*********logged in as a PA user********");
		   UserManagement um = new UserManagement(driver);
		   um.getUserNameFilter().SendKeys("PA");
		   bc.passedStep("*********PA users filtered********");
		   um.getSelectRole().selectFromDropdown().selectByVisibleText("Review Manager");
		   bc.passedStep("*********Reviewe manager role selected********");
			
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
