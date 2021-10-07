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
	lp.loginToSightLine(Input.rmu1userName,Input.rmu1password);
	bc = new BaseClass(driver);
	redact = new RedactionPage(driver);
	}
	
	@Test(priority =1,groups={"regression"})
	public void AddRedactionwithRMU() throws ParseException, InterruptedException {
		bc.stepInfo("RPMXCON-53622-To verify user with Manage privileges can create Redaction Tag");
		redact.AddRedaction(redactname,"RMU");
		bc.passedStep(redactname+"added successfully");
		redact.AddRedaction(editredactname,"RMU");
		bc.stepInfo("RPMXCON-53623-To verify user with Manage Privileges can edit redaction tag");
		redact.EditRedaction(editredactname);
		bc.passedStep(editredactname+"edited successfully");
		bc.stepInfo("RPMXCON-53624-To verify user with Manage Privileges can remove redaction tag");
		redact.DeleteRedaction(redactname);
		bc.passedStep(redactname+"deleted successfully");
		driver.Navigate().refresh();
		lp.logout();
	}
	
	@Test(priority =2,groups={"regression"})
	public void AddRedactionwithPA() throws ParseException, InterruptedException {
		
		lp.logout();
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
		bc.stepInfo("RPMXCON-53622-To verify user with Manage privileges can create Redaction Tag");
		redact.AddRedaction(redactname,"PA");
		bc.passedStep(redactname+"added successfully");
		redact.AddRedaction(editredactname,"PA");
		bc.stepInfo("RPMXCON-53623-To verify user with Manage Privileges can edit redaction tag");
		redact.EditRedaction(editredactname);
		bc.passedStep(editredactname+"edited successfully");
		bc.stepInfo("RPMXCON-53624-To verify user with Manage Privileges can remove redaction tag");
		redact.DeleteRedaction(redactname);
		bc.passedStep(redactname+"deleted successfully");
	}
	
	@Test(priority =3,groups={"regression"})
	public void AddRedactionImpersonate() throws ParseException, InterruptedException {
		
		 //Impersonate as PA 
		bc.stepInfo("RPMXCON-53688-To verify user can create redaction tags after impersonating the role");
		bc.impersonatePAtoRMU();
		redact.AddRedaction(redactname,"RMU");
		bc.passedStep(redactname+"added successfully");
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
