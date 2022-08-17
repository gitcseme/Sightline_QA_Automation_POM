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

public class BulkAction_IndiumRegressionPhase2 {
	
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
		// Login as a RMU
		
//		loginPage = new LoginPage(driver);
//	    assignmentPage = new AssignmentsPage(driver);
//	    sessionSearch = new SessionSearch(driver);
//	    softAssertion = new SoftAssert();
//		codingForm = new CodingForm(driver);
//		reusableDocView = new ReusableDocViewPage(driver);	
//		docViewPage = new DocViewPage(driver);
//		miniDocList = new MiniDocListPage(driver);
//		userManagementPage = new UserManagement(driver);
	}
	
	
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that As a RMU I will be able to assign document
	 */
	@Test(description = "RPMXCON-53555",enabled = true, groups = { "regression" })
	public void verifyRmuCanAssignDocs() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-53555");
	    baseClass.stepInfo("Verify that As a RMU I will be able to assign document");
	    String savedsSarch = "Assign"+Utility.dynamicNameAppender();
	    String assignment = "Assignment"+Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
		assignmentPage = new AssignmentsPage(driver);
	    sessionSearch = new SessionSearch(driver);
	    savedSearch=new SavedSearch(driver);
	    
	    //Login as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		// saving the search as per pre-requistes
		int pureHit =sessionSearch.basicContentSearch(Input.searchString1)  ;
		sessionSearch.saveSearch(savedsSarch);
		savedSearch.savedSearch_Searchandclick(savedsSarch);
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assignmentPage.assignmentCreationAsPerCf(assignment, Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.assignmentNameValidation(assignment);
		
	    // logout
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify the "Bulk Assign" functionality in selected results as a RMU login
	 */
	@Test(description = "RPMXCON-48684",enabled = true, groups = { "regression" })
	public void verifyRmuCanBulkAssignMultipleDrag() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-48684");
	    baseClass.stepInfo("Verify the \"Bulk Assign\" functionality in selected results as a RMU login");
	    String assignmentName = "Assignment"+Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
	    sessionSearch = new SessionSearch(driver);
	    assignmentPage = new AssignmentsPage(driver);
	    
	    //Login as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		// searching for multiple times
		int pureHit =sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.getPureHitAddButton().isElementAvailable(2);
		sessionSearch.getPureHitAddButton().waitAndClick(5);
		baseClass.stepInfo("Draged first purehit count");
		sessionSearch.addNewSearch();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch. getEnterSearchString_UsingPosition(5).Visible();
			}
		}), Input.wait60);
		sessionSearch.getEnterSearchString_UsingPosition(5).SendKeys(Input.searchString2);
		
		// Click on Search button
		sessionSearch.getSecondSearchBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getPureHit_UsingLast().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		sessionSearch.getPureHit_UsingLast().waitAndClick(20);
		int pureHits = Integer.parseInt(sessionSearch.getPureHit_UsingLast().getText());
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.assignmentNameValidation(assignmentName);
		baseClass.passedStep("Rmu can able to assign docs from session serach using multiple query");
	    // logout
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : Verify that As a Project Admin login I will be able 
	 *                to Release document from saved search
	 */
	@Test(description = "RPMXCON-48685",enabled = true, groups = { "regression" })
	public void verifyPaUserCanReleaseDocsFromSavedSearch() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-48685");
	    baseClass.stepInfo("Verify that As a Project Admin login I will be able "
	    		+ "to Release document from saved search");
	    String savedsSarch = "Assign"+Utility.dynamicNameAppender();
	    String securitygroupname1 = "securitygroupname" + Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
	    sessionSearch = new SessionSearch(driver);
	    savedSearch=new SavedSearch(driver);
	    securityGroupPage=new SecurityGroupsPage(driver);
	    
	    //Login as pa
	    loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		
		// Creating a new sg
		securityGroupPage.navigateToSecurityGropusPageURL();
		securityGroupPage.AddSecurityGroup(securitygroupname1);
	    
		// saving the search as per pre-requistes
		int pureHit =sessionSearch.basicContentSearch(Input.searchString1)  ;
		sessionSearch.saveSearch(savedsSarch);
		
		// from save search releasing docs to sg
		savedSearch.savedSearch_Searchandclick(savedsSarch);
		savedSearch.preRequestCreation(savedsSarch, securitygroupname1);
		baseClass.VerifySuccessMessage("Records saved successfully");
		securityGroupPage.deleteSecurityGroups(securitygroupname1);
		baseClass.passedStep("As a Pa user document released to new sg from saved search");
		
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}

}
