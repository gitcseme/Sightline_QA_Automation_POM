package sightline.securityGroups;


import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SecurityGroup_Consilio {


	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	
	SecurityGroupsPage securityGroupsPage;
	

	
	  @BeforeClass(alwaysRun = true)
	   public void beforeClass() throws ParseException, InterruptedException, IOException{
		  
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		//Input in = new Input();
			//in.loadEnvConfig();
		
		driver = new Driver();
		baseClass = new BaseClass(driver);
		
		loginPage = new LoginPage(driver);
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
	 		loginPage.logout();
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	     }	   
	
	@Test(description ="RPMXCON-69082",groups={"regression"})

	
	public void CreateAndEditSGwithSpecialChars( ) throws Exception {

	baseClass.stepInfo("RPMXCON-69082 Verify that error message  display and application does NOT accepts - when user creates "
			+ "new name & renames security group details with special characters < > & ‘ ");
	
	SecurityGroupsPage sgpage = new SecurityGroupsPage(driver);
	String SGname = "Security Group_" + UtilityLog.dynamicNameAppender();
	String Sgnameedit="<Renamed Security Group_>&'" + UtilityLog.dynamicNameAppender();

	// Login as PA
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	baseClass.stepInfo("Login as in  " + Input.pa1FullName);
	baseClass.stepInfo("navigated to security group as expected");
	sgpage.createSecurityGroups(SGname);
	baseClass.stepInfo("new Security group is created" +SGname);
	baseClass.waitTime(5);
	baseClass.waitForElement(sgpage.selectSGFromDropdown(SGname));
	sgpage.selectSGFromDropdown(SGname).waitAndClick(10);
    sgpage.getRenameBtn().waitAndClick(10);
    sgpage.getSecurityGroupNameUpdate().SendKeys(Sgnameedit);
    sgpage.getSecurityGroupUpdateButton().waitAndClick(10);
	baseClass.stepInfo("Security group name is updated" +Sgnameedit);

    baseClass.waitTime(5);
    String errorMsg=sgpage.getEditMsg().getText();
	System.out.println("errorMsg"+errorMsg);
	  Assert.assertEquals(errorMsg,"The security group name is invalid.");   
	  baseClass.passedStep("Error Message is Displayed");
}
	
	}