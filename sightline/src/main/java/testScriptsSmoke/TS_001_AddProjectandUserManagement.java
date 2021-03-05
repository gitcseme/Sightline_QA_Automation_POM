package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.ProjectPage;
import pageFactory.Utility;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;

public class TS_001_AddProjectandUserManagement {
	Driver driver;
    LoginPage lp;
    
    
    @BeforeClass(alwaysRun = true)
     public void before() throws Exception{
    	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
    	Input in = new Input(); 	
		in.loadEnvConfig();
	
		
    	//	String securitygroup= "Default Security Group";
    		driver = new Driver();
    	    lp = new LoginPage(driver);
    		lp.loginToSightLine(Input.sa1userName, Input.sa1password);

	}
	@Test(groups={"smoke","regression"})	
	public void CreateProjectAndAssignUsers() throws ParseException, InterruptedException {
		
		String hcode = "HC"+Utility.dynamicNameAppender();
		ProjectPage page = new ProjectPage(driver);
		page.AddNonDomainProject(Input.projectName, hcode);
	 	ManageUsersPage userpage = new ManageUsersPage(driver);
	 	userpage.AddUserstoProject(Input.projectName);
		userpage.BulkUserAccess(Input.projectName);
		
		
	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
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
	}
}
