package executionMaintenance;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;

import testScriptsSmoke.Input;


public class TestListener implements ITestListener {


	public void onStart(ITestContext context) {
		System.out.println("*** Test Suite " + context.getName() + " started ***");
	}

	public void onFinish(ITestContext context) {
		System.out.println(("*** Test Suite " + context.getName() + " ending ***"));
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		System.out.println(("*** Running test method " + result.getMethod().getMethodName() + "..."));
		ExtentTestManager.startTest(result.getMethod().getMethodName());		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("*** Executed " + result.getMethod().getMethodName() + " test successfully...");
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");		
		
	}
   
	
	public void onTestFailure(ITestResult result) {
					
			System.out.println("*** Test execution " + result.getMethod().getMethodName() + " failed...");
			ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
			ExtentTestManager.getTest().log(Status.FAIL,"TEST CASE FAILED IS " + result.getThrowable().getMessage());
					
		/*
		JiraPolicy executionPolicy = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraPolicy.class);
		boolean isBugTrue = executionPolicy.logJiraTicket();
		if(isBugTrue) {
			System.out.println("Verify whether this is a bug: "+ isBugTrue);
			JiraServiceProvider jiraSp = new JiraServiceProvider("https://seenuanand.atlassian.net","srinivas.anand1@gmail.com","MpnMm0VjOPwu5LBEzArG535E","PERFAUTO");
			String issueSummary = result.getMethod().getConstructorOrMethod().getMethod() + "Got failed due to some automation assertion or exception";
			String issueDescription =result.getThrowable().getMessage()+"\n";
			issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
			
			jiraSp.createJiraTicket("Bug", issueSummary, issueDescription, "Srinivas Anand");
			
		}	*/	
	}
	
	public void onTestSkipped(ITestResult result) {
		
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}
	
}
