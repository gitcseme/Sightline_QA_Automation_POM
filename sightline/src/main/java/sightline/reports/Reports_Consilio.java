package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Consilio {
	Driver driver;
	LoginPage lp;
	HomePage home;
	ReportsPage report; 
	BaseClass bc;
	   @BeforeClass(alwaysRun = true)
	   public void beforeClass() throws ParseException, InterruptedException, IOException{
		  
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		 Input in = new Input();
			in.loadEnvConfig();
		driver = new Driver();
	    lp = new LoginPage(driver);
	    bc=new BaseClass(driver);
		report = new ReportsPage(driver);
	
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
	 	try{
	 		lp.logout();
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	     }	   
	
	@Test(description ="RPMXCON-69059",dataProvider = "SpecialChars",groups={"regression"})
	public void BulkTagWithSpecialChars(String bulkName) throws InterruptedException {
		
     lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-69059-Verify that error message display and application does NOT accepts - when "
				+ "user does Bulk Folder from tally and enter Folder name with special characters < > & ‘ ");
		driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		report.TallyReportButton();
		
		TallyPage tally = new TallyPage(driver);
		
		//Bulk Tag
		tally.validateTally();
		tally.tallyActions();
		tally.bulkTagSpecialChars(bulkName,1);
		
	
	}
	@Test(description ="RPMXCON-69056",dataProvider = "SpecialChars",groups={"regression"})
	public void BulkFolderWithSpecialChars(String bulkName) throws InterruptedException {
		
     lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-69056-Verify that error message display and application does NOT accept - when "
				+ "user does Bulk Tag from tally and enter tag name with special characters < > & ‘ ");

		driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		report.TallyReportButton();
		
		TallyPage tally = new TallyPage(driver);
		
		//Bulk Folder
		tally.validateTally();
		tally.tallyActions();
		tally.bulkFolderSpecialChars(bulkName,1);
	
		
	}
	
	@DataProvider(name = "SpecialChars")
	public Object[][] BulkfolderAndTagSpecialChars() {
		return new Object[][] { 
			{ "<samplename>"}, 
			{ "@samplename" },
			{ "samplename?"},
			{"samplename&sample"},
			{"sample'"}

		};
	}

}