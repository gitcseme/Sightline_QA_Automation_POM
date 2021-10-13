package testScriptsRegression;

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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ABMReport {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	int purehits;
	BaseClass bc;
	AssignmentsPage asgnmp; 
	String saveSearchName = "ABM"+Utility.dynamicNameAppender();
	String assignmentName= "assignmentABM"+Utility.dynamicNameAppender();
 
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
	//	Input in = new Input(); in.loadEnvConfig();	
		
		//Open browser
		driver = new Driver();
		
		//Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Search and save it

		bc = new BaseClass(driver);
		asgnmp = new AssignmentsPage(driver);
		DocListPage dp = new DocListPage(driver);
		SessionSearch search = new SessionSearch(driver);
		purehits = search.basicContentSearch(Input.searchString2);
		search.saveSearch(saveSearchName);
		search.ViewInDocList();

		dp.DoclisttobulkAssign(assignmentName,"10");

		asgnmp.assignDocstoNewAssgn(assignmentName, "Default Project Coding Form", 10);

	}

	/**
	 * @throws InterruptedException
	 * @description RPMXCON-48789- Validate data on Advanced batch management report
	 *              when Domain and Project Admin associated to Assignments of a
	 *              project
	 */

	@Test(groups = { "smoke", "regression" })
	public void ValidateABMreport() throws InterruptedException {

		bc.stepInfo("Validate data on Advanced batch management report\r\n"
				+ "when Domain and Project Admin associated to Assignments of a project");
		bc.stepInfo("TestCase ID-RPMXCON-48789----ABM Report");
		bc.stepInfo("Validating ABM report with RMU user role");
		ABMReportPage abm = new ABMReportPage(driver);
		abm.ValidateABMreport("saveSearchName", "assignmentName");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
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
			}
		LoginPage.clearBrowserCache();
	}
}

