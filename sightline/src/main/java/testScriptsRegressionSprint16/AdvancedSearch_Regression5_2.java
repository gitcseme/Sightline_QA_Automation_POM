package testScriptsRegressionSprint16;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Regression5_2 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	BaseClass baseClass;
	Input in;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 01/07/2022  TestCase Id:RPMXCON-57158 
	 * Description :Verify that - Application returns all the documents which are available under selected group 
	 * with OR operator and production optional filters - status  in search result.
	 * 
	 */
	@Test(description ="RPMXCON-57158",groups = { "regression" })
	public void verifyDocumentWithORProdOptFilters() throws InterruptedException{
		
		baseClass.stepInfo("Test case Id: RPMXCON-57158 ");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - status  in search result");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with security group OR production");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "OR",false);
		baseClass.stepInfo("Search query and verify search result");
		sessionSearch.verifyDocsCountAvailableInSgWithSearchResult();
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 01/07/2022  TestCase Id:RPMXCON-57159 
	 * Description :Verify that - Application returns all the documents which are available under selected group
	 *  with OR operator and production optional filters - Date Range in search result. 
	 * 
	 */
	@Test(description ="RPMXCON-57159",groups = { "regression" })
	public void verifyDocumentWithORProdOptFiltersDateRange() throws InterruptedException{
		baseClass.stepInfo("Test case Id: RPMXCON-57159 ");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - Date Range in search result");
		//login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with security group OR production status with date");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "OR",true);
		baseClass.stepInfo("Search query and verify search result");
		sessionSearch.verifyDocsCountAvailableInSgWithSearchResult();
		loginPage.logout();		
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Advanced search Regression5**");
	}
}
