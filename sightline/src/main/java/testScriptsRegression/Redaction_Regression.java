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
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Redaction_Regression {
	Driver driver;
	LoginPage lp;
	RedactionPage redact;
	
	String redactname = "Redaction"+Utility.dynamicNameAppender();
	String editredactname = "RedactEdit"+Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	

	Input input = new Input();
	input.loadEnvConfig();
	
	driver = new Driver();
	lp = new LoginPage(driver);
	redact = new RedactionPage(driver);
	}
	
	@Test(priority =1,groups={"regression"})
	public void AddRedactionwithRMU() throws ParseException, InterruptedException {
		
		lp.loginToSightLine(Input.rmu1userName,Input.rmu1password);
		redact.AddRedaction(redactname);
		redact.AddRedaction(editredactname);
		redact.EditRedaction(editredactname);
		redact.DeleteRedaction(redactname);
	}
	
	@Test(priority =1,groups={"regression"})
	public void AddRedactionwithPA() throws ParseException, InterruptedException {
		
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
		redact.AddRedaction(redactname);
		redact.AddRedaction(editredactname);
		redact.EditRedaction(editredactname);
		redact.DeleteRedaction(redactname);
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
