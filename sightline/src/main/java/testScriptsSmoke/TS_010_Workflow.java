package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import pageFactory.WorkflowPage;

public class TS_010_Workflow {
	Driver driver;
	LoginPage lp;

	@Test(groups = { "smoke", "regression" })
	public void Test() throws ParseException, InterruptedException, Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		//Input in = new Input();
		//in.loadEnvConfig();
	
		driver = new Driver();
		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String tagName = "tag" + Utility.dynamicNameAppender();
		String folderName = "folder" + Utility.dynamicNameAppender();
		String assignmentName = "assg" + Utility.dynamicNameAppender();
		String workflowName = "WF" + Utility.dynamicNameAppender();
		int Id;

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(tagName, "Default Security Group");
		page.CreateFolder(folderName, "Default Security Group");

		// Search for any string
		SessionSearch search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);

		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Thread.sleep(2000);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		agnmt.createAssignment(assignmentName, codingfrom);
		System.out.println("Workflow Name : " + workflowName);
		UtilityLog.info("Workflow Name : " + workflowName);
		WorkflowPage wf = new WorkflowPage(driver);
		wf.CreateWFwithAssignments(Id, workflowName, assignmentName, assignmentName, count);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	/*
	 * @AfterMethod(alwaysRun = true) public void takeScreenShot(ITestResult result,
	 * Method testMethod) { Reporter.setCurrentTestResult(result);
	 * UtilityLog.logafter(testMethod.getName()); if (ITestResult.FAILURE ==
	 * result.getStatus()) { Utility bc = new Utility(driver);
	 * bc.screenShot(result);
	 * 
	 * } System.out.println("Executed :" + result.getMethod().getMethodName());
	 * ExtentTestManager.getTest().log(Status.INFO,
	 * this.getClass().getSimpleName()+"/"+testMethod.getName()); }
	 */

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}