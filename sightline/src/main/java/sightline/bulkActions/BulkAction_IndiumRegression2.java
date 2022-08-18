package sightline.bulkActions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkAction_IndiumRegression2 {
	
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage  tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	
	
	
	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
	    in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	
	
	
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify the "Bulk Folder" functionality in selected results 
	 *                as a Review Manager login
	 */
	@Test(description = "RPMXCON-48682",enabled = true, groups = { "regression" })
	public void verifyRmuCanBulkAssignMultipleDrag() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-48682");
	    baseClass.stepInfo("Verify the \"Bulk Folder\" functionality in selected "
	    		+ "results as a Review Manager login");
	    String folder = "folder"+Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
	    sessionSearch = new SessionSearch(driver);
	    tagsAndFoldersPage=new TagsAndFoldersPage(driver);
	    docViewPage = new DocViewPage(driver);
	    
	    //Login as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		// session search to bulk folder
		int pureHit =sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolder(folder);
		baseClass.stepInfo("Making folder  for all purehit document");
		
		// validating the folder(select folder to docview page)
		tagsAndFoldersPage.selectFolderViewInDocView(folder);
		baseClass.stepInfo("Selecting folder and navigating to docview page");
		
		// verifying the doc count
		int docviewCount=docViewPage.verifyingDocCount();
		softAssertion.assertEquals(pureHit, docviewCount);
		softAssertion.assertAll();
		baseClass.passedStep("Assigned document as folder are displayed in docview page");
		baseClass.stepInfo("Rmu user can able to folder the documents");
	    // logout
	    loginPage.logout();
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {;
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR BULK ACTION EXECUTED******");
	}

}
