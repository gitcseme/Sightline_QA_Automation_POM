package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import pageFactory.WorkflowPage;
import testScriptsSmoke.Input;

public class WorkFlow_IndiumRegression {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	BaseClass baseClass;
	WorkflowPage workflow;
	TagsAndFoldersPage page;
	SessionSearch search;
	AssignmentsPage assignmentPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws ParseException, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();

	}

	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that workflow should run and displays doc count, if Source 
	 *             is 'Save Search Selector' and action is'Folder Selector'.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 01)
	public void validationDocsCountSaveSearchAndFolder() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52633");
		baseClass.stepInfo("To verify that workflow should run and displays doc count, if Source is 'Save "
				+ "Search Selector' and action is'Folder Selector'.");
		int Id;
		String tagName = "tag" + Utility.dynamicNameAppender();
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		page = new TagsAndFoldersPage(driver);
		page.CreateTag(tagName, "Default Security Group");
		page.CreateFolder(folderName, "Default Security Group");

		// Search for any string
		search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// creating new work flow
		workflow = new WorkflowPage(driver);
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false,1);
		workflow.selectWorkFlowUsingPagination(wfName);
		
		// Running workflow
		int workFlowId = workflow.gettingWorkFlowId(wfName);
		workflow.actionToRunWorkFlow();
		// Page refresh
		workflow.refreshingThePage();
		
		// validating count in workflow list
		workflow.workFlowIdPassing(workFlowId);
		workflow.applyFilter();
		int docCount = workflow.gettingWorkFlowListDocCount(wfName);
		softAssertion.assertEquals(count, docCount);
		baseClass.passedStep("saved search count " + count + " and run workflow count " + docCount
				+ "are equal and displayed in workflow list");

		// validating count in action
		workflow.actionToHistory(wfName);
		baseClass.stepInfo("Validating in action block");
		int actionDocCount = workflow.gettingActionDocCount(wfName);
		softAssertion.assertEquals(count, actionDocCount);
		baseClass.passedStep("saved search count " + count + " and run workflow count " + actionDocCount
				+ "are equal and displayed in action block");
		workflow.closeHistoryPopUpWindow();
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that workflow should run and displays doc count, if Source is 
	 *            'Save Search Selector' and action is'Assignment' and select 'First family option'.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 02)
	public void validationDocsCountSaveAssgnAndFirstFamily() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52632");
		baseClass.stepInfo("To verify that workflow should run and displays doc count, if Source "
				+ "is 'Save Search Selector' and action is'Assignment' and select 'First family option'.");
		int Id;
		String tagName = "tag" + Utility.dynamicNameAppender();
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();
		assignmentPage=new AssignmentsPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Search for any string
		search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);
		
		// assignment creation
		search.bulkAssignFamilyMemberDocuments();
		int familyCount=assignmentPage.assignmentCreationFamilyCount(assgn, Input.codingFormName);
		int totalCount=count-familyCount;
		System.out.println(totalCount);
		
		// creating new work flow
		workflow = new WorkflowPage(driver);
		baseClass.stepInfo("Creating workflow using save search,assignmnet and first family options");
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, true, folderName, false, assgn, true,1);
		workflow.selectWorkFlowUsingPagination(wfName);
		
		// Running workflow
		int workFlowId = workflow.gettingWorkFlowId(wfName);
		workflow.actionToRunWorkFlow();
		// Page refresh
		workflow.refreshingThePage();
		
		// validating count in workflow list
		workflow.workFlowIdPassing(workFlowId);
		workflow.applyFilter();
		int docCount = workflow.gettingWorkFlowListDocCount(wfName);
		softAssertion.assertEquals(totalCount, docCount);
		baseClass.passedStep("First family count " + totalCount + " and run workflow count " + docCount
				+ "are equal and displayed in workflow list");

		// validating count in action
		workflow.actionToHistory(wfName);
		baseClass.stepInfo("Validating in action block");
		int actionDocCount = workflow.gettingActionDocCount(wfName);
		softAssertion.assertEquals(totalCount, actionDocCount);
		baseClass.passedStep("First family count " + totalCount  + " and run workflow count " + actionDocCount
				+ "are equal and displayed in action block");
		workflow.closeHistoryPopUpWindow();
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that confirmation message is displayed if user select action as Delete
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 03)
	public void validatingPopUpMessageForDelete() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52629");
		baseClass.stepInfo("To verify that confirmation message is displayed if user select action as Delete");
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// creating new work flow to delete validation popup message
		workflow = new WorkflowPage(driver);
		baseClass.stepInfo("Creating workflow for validaing delete popup message");
		workflow.createNewWorkFlow();
		workflow.descriptionTab(wfName,wfDesc);
		workflow.saveButton();
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.selectWorkFlowUsingPagination(wfName);
		int workFlowId = workflow.gettingWorkFlowId(wfName);
		workflow.workFlowIdPassing(workFlowId);
		workflow.applyFilter();
		
		// validation for delete
		String popUpDeleteActual=workflow.actionToDelete(wfName, workFlowId);
        String popUpDeleteExpected="Are you Sure want to delete Workflow :"+workFlowId+"?";
		softAssertion.assertEquals(popUpDeleteExpected, popUpDeleteActual);
		softAssertion.assertAll();
		baseClass.passedStep("Popup message successfully displayed as:"+popUpDeleteActual+"");

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that Reset will displayed all the workflow details.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 04)
	public void validatingResetOption() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52658");
		baseClass.stepInfo("To verify that Reset will displayed all the workflow details.");
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// validating the reset options
		workflow = new WorkflowPage(driver);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("User on the workflow list page");
		workflow.getStatusDropDown().selectFromDropdown().selectByIndex(1);
		boolean afterFilter = (boolean) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.querySelector('#WorkFlowStatus').selectedIndex==1;");
        softAssertion.assertTrue(afterFilter);
        baseClass.passedStep("After applying filter complete option only displayed in workflow list");
        workflow.getResetButton().waitAndClick(5);
        boolean afterReset = (boolean) ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.querySelector('#WorkFlowStatus').selectedIndex==1;");
        softAssertion.assertFalse(afterReset);
        baseClass.passedStep("After resert all option are displayed in workflow list");
        softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that COMPLETE status is displayed in the list if workflow
	 *             process is completed.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 05)
	public void validationDocsCountFolderAction() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52622");
		baseClass.stepInfo("To verify that COMPLETE status is displayed in the list if workflow process is completed.");
		int Id;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		page = new TagsAndFoldersPage(driver);
		page.CreateFolder(folderName, "Default Security Group");

		// Search for any string
		search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// creating new work flow
		workflow = new WorkflowPage(driver);
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false,1);
		workflow.selectWorkFlowUsingPagination(wfName);
		
		// Running workflow
		int workFlowId = workflow.gettingWorkFlowId(wfName);
		workflow.actionToRunWorkFlow();
		// Page refresh
		workflow.refreshingThePage();
		baseClass.waitTime(10);
		
		// validating count in workflow list
		workflow.workFlowIdPassing(workFlowId);
		workflow.applyFilter();
		String expectedComplete="Completed";
		String actualCompleted=workflow.getTableValue("Status", wfName);
		System.out.println(actualCompleted);
		softAssertion.assertEquals(expectedComplete, actualCompleted);
		baseClass.passedStep("After runworkflow done complete status displayed for "+wfName+"");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	

	/**
	 * Author :Vijaya.Rani date: 25/02/2022 Modified date: NA Modified by: NA
	 * Description:To verify that workflow should be deleted if clicks on Yes.
	 * 'RPMXCON-52630' Sprint-13
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 06)
	public void verifyWorkFlowDeleteClickOnYes() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52630");
		baseClass.stepInfo("To verify that workflow should be deleted if clicks on Yes.");

		workflow = new WorkflowPage(driver);
		int Id;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();
		assignmentPage = new AssignmentsPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// Search for any string
		search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Thread.sleep(2000);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// assignment creation
		search.bulkAssign();
		assignmentPage.assignmentCreation(assgn, Input.codingFormName);

		// creating new work flow
		workflow = new WorkflowPage(driver);
		baseClass.stepInfo("Creating workflow using save search,assignmnet and first family options");
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, true, folderName, false, assgn, true,1);
		workflow.selectWorkFlowUsingPagination(wfName);
		// Running workflow
		int workFlowId = workflow.gettingWorkFlowId(wfName);

		//Selecet DropDown And Delete
		baseClass.waitForElement(workflow.getWorkFlow_ActionDropdown());
		workflow.getWorkFlow_ActionDropdown().waitAndClick(10);
		baseClass.stepInfo("Action DropDown is selected");

		baseClass.waitForElement(workflow.getWorkFlow_ActionDeleteWorkFlow());
		workflow.getWorkFlow_ActionDeleteWorkFlow().waitAndClick(10);
		baseClass.stepInfo("Action DropDown Delete Button is Clicked");
		baseClass.waitForElement(workflow.getWorkFlow_RunWorkflowNow_YesButton());
		workflow.getWorkFlow_RunWorkflowNow_YesButton().waitAndClick(10);
		baseClass.stepInfo("workFlow Docs Deleted Successfully");
		
		// validating count in workflow list
		workflow.workFlowIdPassing(workFlowId);
		workflow.applyFilter();

		//verify Deleted Docs
		boolean workFlowid =workflow.getWorkFlowIdPassing().Displayed();
		softAssertion.assertFalse(workFlowid);
		baseClass.passedStep("Deleted Work Flow Document is Not Displayed");
		// logout
		loginPage.logout();

	}

	/**
	 * Author :Vijaya.Rani date: 25/02/2022 Modified date: NA Modified by: NA
	 * Description:To verify that RMU can sort all coulmn. 'RPMXCON-52648' Sprint-13
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 07)
	public void verifyRMUCanSortAllCoulmn() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52648");
		baseClass.stepInfo("To verify that RMU can sort all coulmn.");

		workflow = new WorkflowPage(driver);
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.applyAscandingorder();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyFilter_WorkflowID() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52650");
		baseClass.stepInfo("To verify that RMU can filter by Workflow ID");
		
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "work" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		workflow=new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName,wfDesc);
		baseClass.stepInfo("Draft Work Flow Created with name "+wfName);
		workflow.selectWorkFlowUsingPagination(wfName);
		int wfID=workflow.gettingWorkFlowId(wfName);
		String WF_id=String.valueOf(wfID);
		workflow.filterByWF_ID(WF_id);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.stepInfo("Work flow Filter for WorkflowID "+wfID+" is apllied.");
		List<String> WfID_AfterFilter=workflow.getTableCoumnValue("Workflow ID");
		if(WfID_AfterFilter.size()==1 && WfID_AfterFilter.get(0).equals(wfID)) {
			baseClass.passedStep("Work flow filter functionality working properly "
					+ "if RMU apllied Work flow filter for WorkflowID.");
		}
		else {
			baseClass.failedStep("Work flow filter/Work flow ID functionality not  working properly.");
		}
		
		loginPage.logout();
	}

	
	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that CONFIGURED status is displayed in the list if 
	 *             user save the workflow.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void validateConfiguredStatus() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52620");
		baseClass.stepInfo("To verify that CONFIGURED status is displayed in the list "
				+ "if user save the workflow.");
		int Id;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		page = new TagsAndFoldersPage(driver);
		page.CreateFolder(folderName, "Default Security Group");

		// Search for any string
		search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// creating new work flow
		workflow = new WorkflowPage(driver);
		workflow.newWorkFlowCreation(wfName, wfDesc, 2, false, folderName, true, assgn, false,3);
		workflow.selectWorkFlowUsingPagination(wfName);
		
		// validation configured status after saving the workflow
		driver.waitForPageToBeReady();
		String expectedComplete="Configured";
		String actualCompleted=workflow.getTableValue("STATUS", wfName);
		System.out.println(actualCompleted);
		softAssertion.assertEquals(expectedComplete, actualCompleted);
		baseClass.passedStep("After saving the workflow Configured status displayed for "+wfName+"");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyHistoryBtn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52645");
		baseClass.stepInfo("To verify that RMU can view the action 'View History'.");
		
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "work" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		workflow=new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName,wfDesc);
		baseClass.stepInfo("Draft Work Flow Created with name "+wfName);
		workflow.selectWorkFlowUsingPagination(wfName);
		workflow.getWorkFlow_ActionDropdown().waitAndClick(5);
		baseClass.stepInfo("Clicked on Action button.");
		if(workflow.getEnabledHistoryBtn().isDisplayed()) {
			baseClass.passedStep("Sucessfully verified that RMU can view the action 'View History' Button.");
		}
		else {
			baseClass.failedStep("View History Button not displayed.");
		}
		
		loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyFilter_CreatedUser() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52657");
		baseClass.stepInfo("To verify that Workflow details should be filtered as per the selected 'Created By' user.");
		
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "work flow description " + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		workflow=new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName,wfDesc);
		baseClass.stepInfo("Draft Work Flow Created with name "+wfName);
		workflow.filterByCreatedByUSer(Input.rmu1FullName);
		baseClass.stepInfo("Work flow Filter for created by user name "+Input.rmu1userName+" is apllied.");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		List<String> WfCreatedUSer_AfterFilter=workflow.getTableCoumnValue("Created By");
		System.out.println(WfCreatedUSer_AfterFilter);
		if(WfCreatedUSer_AfterFilter.size()!=0 ) {
			for(int i=0;i<WfCreatedUSer_AfterFilter.size();i++) {
			if( WfCreatedUSer_AfterFilter.get(i).equals(Input.rmu1FullName)) {
				if(i==(WfCreatedUSer_AfterFilter.size()-1))
			baseClass.passedStep("Work flow filter for 'created  by user' functionality working properly");
			}else {
				baseClass.failedStep("Work flow filter displayed different user name "+WfCreatedUSer_AfterFilter.get(i)+" which is not applied in filter");
			}
			}
		}
		else {
			baseClass.failedStep("Work flow filter functionality not  working properly");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani date: 28/02/2022 Modified date: NA Modified by: NA
	 * Description:To verify that RMU can save the record.
	 * 'RPMXCON-52591' Sprint-13
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyRMUCanSaveTheReport() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52591");
		baseClass.stepInfo("To verify that RMU can save the record.");

		workflow = new WorkflowPage(driver);
		int Id;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();
		assignmentPage = new AssignmentsPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// Search for any string
		search = new SessionSearch(driver);
		int count = search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Thread.sleep(2000);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// assignment creation
		search.bulkAssign();
		assignmentPage.assignmentCreation(assgn, Input.codingFormName);

		// creating new work flow
		workflow = new WorkflowPage(driver);
		baseClass.stepInfo("Creating workflow using save search,assignmnet and first family options");
		workflow.newWorkFlowCreationAssignUser(wfName, wfDesc, Id, true, folderName, false, assgn, true,1);
	    
		softAssertion.assertTrue(workflow.getWorkFlow_AssignedUsers().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("All existing users is displayed in the list and record saved Successfully");
	
		loginPage.logout();
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
		}
		try {
			// loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}
}
