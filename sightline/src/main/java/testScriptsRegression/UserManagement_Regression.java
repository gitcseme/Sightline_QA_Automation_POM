package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserManagement_Regression {
	Driver driver;
	LoginPage lp;
	UserManagement um;
	BaseClass bc;
	static String firstName;
	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
		driver = new Driver();
		lp=new LoginPage(driver);
		bc = new BaseClass(driver);
		//lp.loginToSightLine(Input.sa1userName, Input.sa1password);
	
	}
	
    @Test(groups={"regression"})
    public void AddRMUwithAttorney() throws InterruptedException {
	   //user'gmail account
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
		//lp.logout(); 
		
		//Edit rights
		//um.selectUserToEdit(firstName);
    	um = new UserManagement(driver);
		um.selectUserToEdit("11");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				um.getFunctionalityTab().Visible() ;}}), Input.wait30);
		um.getFunctionalityTab().waitAndClick(5);
		um.checkUserRights("Review Manager");
		
	
	}
	
    @Test(priority =2,groups={"smoke","regression"})
	public void LockAccountCheck() throws InterruptedException {
    	String expectedErrorMsg ="20001000020 : Your account has been locked. Please contact your administrator or support";
    	lp.loginToSightLine(Input.pa1userName, Input.pa1password);   
		this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
		final UserManagement um= new UserManagement(driver);
		um.lockAccount("RMU","Review Manager","active");
		lp.logout();
		try{
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
		}
		catch(Exception e)
		{
		String getTxt = driver.getWebDriver().findElement(By.xpath("//div[@class='validation-summary-errors']//ul//li")).getText();
		Assert.assertEquals(getTxt,expectedErrorMsg );
		System.out.println("Your Account is Locked");
		//lp.quitBrowser();
		}
	}
    
    @Test(priority =3,groups={"smoke","regression"})
   	public void UnlockAccountCheck() throws InterruptedException {
       	
       	lp.loginToSightLine(Input.pa1userName, Input.pa1password);   
   		this.driver.getWebDriver().get(Input.url+ "User/UserListView#");
   		final UserManagement um= new UserManagement(driver);
   		um.UnlockAccount("RMU","Review Manager","active");
   		lp.logout();
   		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
   		System.out.println("Now we can login");
   	}
    
    @Test(priority =4,groups={"smoke","regression"})
   	public void ImpersonateSAUtoREVUtoSAU() throws InterruptedException {
       	    //Login as SA
			lp.loginToSightLine(Input.sa1userName, Input.sa1password);
			//Impersonate as Reviewer
			bc.impersonateSAtoReviewertoSA();
    }
   
    @Test(priority =5,groups={"smoke","regression"})
   	public void ImpersonateSAUtoPAUtoSAU() throws InterruptedException {
       	    //Login as SA
			lp.loginToSightLine(Input.sa1userName, Input.sa1password);
			//Impersonate as SA
			bc.impersonateSAtoPAtoSA();
    }
    
	 @Test(priority =6,groups={"smoke","regression"})
	 public void asDAaddNewRMU() throws InterruptedException {
	   		String newUserid= "a.u.t.o.mation.consilio@gmail.com";
	   		lp.loginToSightLine(Input.da1userName, Input.da1password);
	   		//create RMU
	   		um = new UserManagement(driver);
	   		firstName = "00DA"+Utility.dynamicNameAppender();
	   		um.RMUUserwithAttorneyProfile(firstName, "RMU", "Review Manager", newUserid, " ", Input.projectName);
	   		
	   	}
	 
	 @Test(priority =7,groups={"smoke","regression"})
	 public void asPAaddNewRMU() throws InterruptedException {
	   		String newUserid= "a.u.t.o.m.a.tion.consilio@gmail.com";
	   		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	   		//create RMU
	   		um = new UserManagement(driver);
	   		firstName = "00PA"+Utility.dynamicNameAppender();
	   		um.RMUUserAttorneyasPA(firstName, "RMU", "Review Manager", newUserid, " ");
	   		
	   	}
	 
	 @Test(priority =8,groups={"smoke","regression"})
	 public void asRMUaddNewRMU() throws InterruptedException {
	   		String newUserid= "a.u.t.o.m.a.t.i.on.consilio@gmail.com";
	   		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	   		//create RMU
	   		um = new UserManagement(driver);
	   		firstName = "00RMU"+Utility.dynamicNameAppender();
	   		um.RMUUserAttorneyasRMU(firstName, "RMU", "Review Manager", newUserid, " ");
	   		
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
