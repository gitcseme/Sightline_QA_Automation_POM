package testScriptsRegressionPhase2;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
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

public class AdvancedSearch_Regression5 {

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
	 * Author :Arunkumar date: 29/06/2022 TestCase Id:RPMXCON-57148 
	 * Description :Verify that application displays correct options 
	 * for "DocDateDateOnly" field on Advanced Search screen.
	 */
	@Test(description ="RPMXCON-57148",groups = { "regression" })
	public void verifyDocDateOnlyFieldOnAdvancedSearch() throws InterruptedException{
		
		baseClass.stepInfo("Test case Id: RPMXCON-57148");
		baseClass.stepInfo("Verify 'DocDateDateOnly' field on advanced search");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.navigateToAdvancedMetaDataSearch();
		//Verify the options available
		sessionSearch.verifyOptionsDisplayForDocDateDateOnlyField();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 29/06/2022  TestCase Id:RPMXCON-57043 
	 * Description :To verify an an PA user login, I will be able to select all Folder
	 * from Folder column under Work Product tab & set that as a search criteria for advanced search 
	 * 
	 */
	@Test(description ="RPMXCON-57043",groups = { "regression" })
	public void verifyAllFolderSelectionUnderWorkProduct() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-57043");
		baseClass.stepInfo("Verify all folder selection under work product and set as search criteria");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Navigate to advanced search from search page");
		sessionSearch.advanceSearchByFolder("All Folders");
		if(sessionSearch.getSearchCriteriaValue().isElementAvailable(10)) {
			baseClass.passedStep("Able to select all folder under work product and set as search criteria");
		}
		else {
			baseClass.failedStep("unable to set all folder as search criteria");
		}
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
