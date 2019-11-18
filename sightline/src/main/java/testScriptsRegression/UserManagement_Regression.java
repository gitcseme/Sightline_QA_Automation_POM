package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserManagement_Regression {
	Driver driver;
	LoginPage lp;
	UserManagement um;
	static String firstName;
	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
		driver = new Driver();
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
	
	}
		
    @Test(groups={"regression"})
    public void AddRMUwithAttorney() throws InterruptedException {
	/*	//user'gmail account
		String newUserid= "r.muserconsilio@gmai.com";
		String newUserPwd="consilio@123";
		//sightline password
		String sightlinepwd = "consilio@123";
	
		//Login as SA
		
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		//create rmu
		um = new UserManagement(driver);
		firstName = "00RMU"+Utility.dynamicNameAppender();
		um.RMUUserwithAttorneyProfile(firstName, "AutoRMU", "Review Manager", newUserid, " ", Input.projectName);
		//activate account
		driver.Navigate().to(LoginPage.readGmailMail("Welcome ",firstName,"ActivationLink",newUserid,newUserPwd));
		driver.waitForPageToBeReady();
		
		//set password and login as a new user
		um.setPassword(sightlinepwd);
		//lp.logout(); */
		
		//Edit rights
		//um.selectUserToEdit(firstName);
    	um = new UserManagement(driver);
		um.selectUserToEdit("11");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				um.getFunctionalityTab().Visible() ;}}), Input.wait30);
		um.getFunctionalityTab().waitAndClick(5);
		um.checkUserRights("Review Manager");
		
	
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
		LoginPage.clearBrowserCache();
	}
}
