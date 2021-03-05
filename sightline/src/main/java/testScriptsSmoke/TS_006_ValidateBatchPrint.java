package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BatchPrintPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import pageFactory.WorkflowPage;

public class TS_006_ValidateBatchPrint {
	Driver driver;
	LoginPage lp;
	
	 
	 /*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as a RMU, search for docs and save the search. 
	 * Perform batch print with the saved search and validated the documents order!
	 *   
	 */	 
	@Test(groups={"smoke","regression"})
	public void BatchPrintWithNative() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		UtilityLog.info("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		driver = new Driver();
		
		String searchname= "BP"+Utility.dynamicNameAppender();
		String orderCriteria = "DocID";
		String orderType = "Asc";
				
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch("morning"+Keys.ENTER+"AND"+Keys.ENTER+"test");
		search.saveSearch(searchname); 
		
	    BatchPrintPage page1 = new BatchPrintPage(driver);
     	page1.BatchPrintWithNative("search",searchname,orderCriteria, orderType);
		page1.checkForOrderInPDF("DocID:", orderType);
		
		
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
