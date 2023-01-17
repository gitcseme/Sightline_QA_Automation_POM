
package sightline.smallComponents;

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
import pageFactory.WorkflowPage;
import testScriptsSmoke.Input;

public class WorkflowAutomation_Phase2_Regression {
	
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
	WorkflowPage workflow;
	
	
	
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
	 * @Description : To verify that RMU can save the 'Save Search ID' as source.
	 */
	@Test(description = "RPMXCON-52568",enabled = true, groups = { "regression" })
	public void verifyUserCanSaveWorkflowUsingSearchID() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-52568");
	    baseClass.stepInfo("To verify that RMU can save the 'Save Search ID' as source.");
	    int Id;
	    String SearchName = "Search"+Utility.dynamicNameAppender();
	    String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
	    sessionSearch = new SessionSearch(driver);
	    savedSearch=new SavedSearch(driver);
	    workflow = new WorkflowPage(driver);
	    
	    //Login as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		// Search for any string
		sessionSearch = new SessionSearch(driver);
		int count = sessionSearch.basicContentSearch(Input.searchString1);

		// Save the search
		sessionSearch.saveSearch(SearchName);
		savedSearch.getSaveSearchID(SearchName);
		Id = Integer.parseInt(savedSearch.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);
	    workflow.createNewWorkFlow();
		workflow.descriptionTab(wfName, wfDesc);
		driver.waitForPageToBeReady();
		workflow.nextButton();
		workflow.sourcesTab(Id);
		workflow.saveButton();
		baseClass.VerifySuccessMessage("Record saved successfully");
		baseClass.passedStep("user can able to save the workflow using save search ID");
		
	    // logout
	    loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author :Indium-Baskar
	 * @Description : To verify that selected user email ids are displayed in Summary tab.
	 */
	@Test(description = "RPMXCON-52616",enabled = true, groups = { "regression" })
	public void verifySelectedEmailInSummaryTab() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-52616");
	    baseClass.stepInfo("To verify that selected user email ids are displayed in Summary tab.");
	    int Id;
	    String SearchName = "Search"+Utility.dynamicNameAppender();
	    String folderName = "folder"+Utility.dynamicNameAppender();
	    String assgn = "Assgn"+Utility.dynamicNameAppender();
	    String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
	    softAssertion = new SoftAssert();
	    sessionSearch = new SessionSearch(driver);
	    savedSearch=new SavedSearch(driver);
	    workflow = new WorkflowPage(driver);
	    tagsAndFoldersPage = new TagsAndFoldersPage(driver);
	    
	    //Login as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		
		tagsAndFoldersPage.CreateFolder(folderName, "Default Security Group");
		
		// Search for any string
		sessionSearch = new SessionSearch(driver);
		int count = sessionSearch.basicContentSearch(Input.searchString1);

		// Save the search
		sessionSearch.saveSearch(SearchName);
		savedSearch.getSaveSearchID(SearchName);
		Id = Integer.parseInt(savedSearch.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);
		
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.newWorkFlowCreationWithOneEmail(wfName, wfDesc, Id, false, folderName, true, assgn, false, 1);
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
