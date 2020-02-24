package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ManageRedaction_Regression {
	Driver driver;
	LoginPage lp;
	RedactionPage redact;
	BaseClass bc;
	
	String redactname = "Redaction"+Utility.dynamicNameAppender();
	String editredactname = "RedactEdit"+Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	

	driver = new Driver();
	lp = new LoginPage(driver);
	bc = new BaseClass(driver);
	redact = new RedactionPage(driver);
	}
	
	@Test(priority =1,groups={"regression"})
	public void AddRedactionwithRMU() throws ParseException, InterruptedException {
		
		lp.loginToSightLine(Input.rmu1userName,Input.rmu1password);
		redact.AddRedaction(redactname,"RMU");
		redact.AddRedaction(editredactname,"RMU");
		redact.EditRedaction(editredactname);
		redact.DeleteRedaction(redactname);
	}
	
	@Test(priority =1,groups={"regression"})
	public void AddRedactionwithPA() throws ParseException, InterruptedException {
		
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
		redact.AddRedaction(redactname,"PA");
		redact.AddRedaction(editredactname,"PA");
		redact.EditRedaction(editredactname);
		redact.DeleteRedaction(redactname);
	}
	
	@Test(priority =1,groups={"regression"})
	public void AddRedactionImpersonate() throws ParseException, InterruptedException {
		
		 lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		 //Impersonate as PA 
		bc.impersonatePAtoRMU();
		redact.AddRedaction(redactname,"RMU");
	}
	
	
	@AfterMethod(alwaysRun = true)
	private void user() {
		 lp.logout();

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
			}
	}

}
