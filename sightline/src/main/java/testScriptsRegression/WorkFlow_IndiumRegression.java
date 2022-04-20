package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
	
	String actionQuery="Define What Actions To Take On Candidate Documents That Meet Your Family Options";
    String pageQuery="On this page you can define what action the application will take on eac h candidate "
    		        + "document that also meets the selected Family Option rules. When the Workflow runs, "
    		        + "it will evaluate whether each document in the Source Universe meets the Filter "
    		        + "Criteria. Those that do are considered \"candidate documents\". "
    		        + "If you have Family Option rules set up, the applicaiton will further evaluate"
    		        + " whether all selected Family Option rules are met. If they are met, the Action "
    		        + "specified below will be taken on the family unit if Family Option rules are specified,"
    		        + " or on the candidate document if the document is not part of a family unit, or if no Family "
    		        + "Options are configured. You can either action documents to an Assignment or into a Folder.";
	
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
	 * Description:To verify that workflow should run and displays doc count, if
	 * Source is 'Save Search Selector' and action is'Folder Selector'.
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
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false, 1);
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
	 * Description:To verify that workflow should run and displays doc count, if
	 * Source is 'Save Search Selector' and action is'Assignment' and select 'First
	 * family option'.
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
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// assignment creation
		search.bulkAssignFamilyMemberDocuments();
		int familyCount = assignmentPage.assignmentCreationFamilyCount(assgn, Input.codingFormName);
		int totalCount = count - familyCount;
		System.out.println(totalCount);

		// creating new work flow
		workflow = new WorkflowPage(driver);
		baseClass.stepInfo("Creating workflow using save search,assignmnet and first family options");
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, true, folderName, false, assgn, true, 1);
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
		baseClass.passedStep("First family count " + totalCount + " and run workflow count " + actionDocCount
				+ "are equal and displayed in action block");
		workflow.closeHistoryPopUpWindow();
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that confirmation message is displayed if user select
	 * action as Delete
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
		workflow.descriptionTab(wfName, wfDesc);
		workflow.saveButton();
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.selectWorkFlowUsingPagination(wfName);
		int workFlowId = workflow.gettingWorkFlowId(wfName);
		workflow.workFlowIdPassing(workFlowId);
		workflow.applyFilter();

		// validation for delete
		String popUpDeleteActual = workflow.actionToDelete(wfName, workFlowId);
		String popUpDeleteExpected = "Are you Sure want to delete Workflow :" + workFlowId + "?";
		softAssertion.assertEquals(popUpDeleteExpected, popUpDeleteActual);
		softAssertion.assertAll();
		baseClass.passedStep("Popup message successfully displayed as:" + popUpDeleteActual + "");

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
		boolean afterFilter = (boolean) ((JavascriptExecutor) driver.getWebDriver())
				.executeScript("return document.querySelector('#WorkFlowStatus').selectedIndex==1;");
		softAssertion.assertTrue(afterFilter);
		baseClass.passedStep("After applying filter complete option only displayed in workflow list");
		workflow.getResetButton().waitAndClick(5);
		boolean afterReset = (boolean) ((JavascriptExecutor) driver.getWebDriver())
				.executeScript("return document.querySelector('#WorkFlowStatus').selectedIndex==1;");
		softAssertion.assertFalse(afterReset);
		baseClass.passedStep("After resert all option are displayed in workflow list");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that COMPLETE status is displayed in the list if
	 * workflow process is completed.
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
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false, 1);
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
		String expectedComplete = "Completed";
		String actualCompleted = workflow.getTableValue("Status", wfName);
		System.out.println(actualCompleted);
		softAssertion.assertEquals(expectedComplete, actualCompleted);
		baseClass.passedStep("After runworkflow done complete status displayed for " + wfName + "");
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
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, true, folderName, false, assgn, true, 1);
		workflow.selectWorkFlowUsingPagination(wfName);
		// Running workflow
		int workFlowId = workflow.gettingWorkFlowId(wfName);

		// Selecet DropDown And Delete
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

		// verify Deleted Docs
		boolean workFlowid = workflow.getWorkFlowIdPassing().Displayed();
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
		workflow = new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName, wfDesc);
		baseClass.stepInfo("Draft Work Flow Created with name " + wfName);
		workflow.selectWorkFlowUsingPagination(wfName);
		int wfID = workflow.gettingWorkFlowId(wfName);
		String WF_id = String.valueOf(wfID);
		workflow.filterByWF_ID(WF_id);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.stepInfo("Work flow Filter for WorkflowID " + wfID + " is apllied.");
		List<String> WfID_AfterFilter = workflow.getTableCoumnValue("Workflow ID");
		if (WfID_AfterFilter.size() == 1 && WfID_AfterFilter.get(0).equals(wfID)) {
			baseClass.passedStep("Work flow filter functionality working properly "
					+ "if RMU apllied Work flow filter for WorkflowID.");
		} else {
			baseClass.failedStep("Work flow filter/Work flow ID functionality not  working properly.");
		}

		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date: 24/02/2022 Modified by: Baskar
	 * Description:To verify that CONFIGURED status is displayed in the list if user
	 * save the workflow.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void validateConfiguredStatus() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52620");
		baseClass.stepInfo("To verify that CONFIGURED status is displayed in the list " + "if user save the workflow.");
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
		workflow.newWorkFlowCreation(wfName, wfDesc, 2, false, folderName, true, assgn, false, 3);
		workflow.selectWorkFlowUsingPagination(wfName);

		// validation configured status after saving the workflow
		driver.waitForPageToBeReady();
		String expectedComplete = "Configured";
		String actualCompleted = workflow.getTableValue("STATUS", wfName);
		System.out.println(actualCompleted);
		softAssertion.assertEquals(expectedComplete, actualCompleted);
		baseClass.passedStep("After saving the workflow Configured status displayed for " + wfName + "");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyHistoryBtn() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52645");
		baseClass.stepInfo("To verify that RMU can view the action 'View History'.");

		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "work" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		workflow = new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName, wfDesc);
		baseClass.stepInfo("Draft Work Flow Created with name " + wfName);
		workflow.selectWorkFlowUsingPagination(wfName);
		workflow.getWorkFlow_ActionDropdown().waitAndClick(5);
		baseClass.stepInfo("Clicked on Action button.");
		if (workflow.getEnabledHistoryBtn().isDisplayed()) {
			baseClass.passedStep("Sucessfully verified that RMU can view the action 'View History' Button.");
		} else {
			baseClass.failedStep("View History Button not displayed.");
		}

		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyFilter_CreatedUser() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52657");
		baseClass.stepInfo("To verify that Workflow details should be filtered as per the selected 'Created By' user.");

		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "work flow description " + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		workflow = new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName, wfDesc);
		baseClass.stepInfo("Draft Work Flow Created with name " + wfName);
		workflow.filterByCreatedByUSer(Input.rmu1FullName);
		baseClass.stepInfo("Work flow Filter for created by user name " + Input.rmu1userName + " is apllied.");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		List<String> WfCreatedUSer_AfterFilter = workflow.getTableCoumnValue("Created By");
		System.out.println(WfCreatedUSer_AfterFilter);
		if (WfCreatedUSer_AfterFilter.size() != 0) {
			for (int i = 0; i < WfCreatedUSer_AfterFilter.size(); i++) {
				if (WfCreatedUSer_AfterFilter.get(i).equals(Input.rmu1FullName)) {
					if (i == (WfCreatedUSer_AfterFilter.size() - 1))
						baseClass.passedStep("Work flow filter for 'created  by user' functionality working properly");
				} else {
					baseClass.failedStep("Work flow filter displayed different user name "
							+ WfCreatedUSer_AfterFilter.get(i) + " which is not applied in filter");
				}
			}
		} else {
			baseClass.failedStep("Work flow filter functionality not  working properly");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 28/02/2022 Modified date: NA Modified by: NA
	 * Description:To verify that RMU can save the record. 'RPMXCON-52591' Sprint-13
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
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
		workflow.newWorkFlowCreationAssignUser(wfName, wfDesc, Id, true, folderName, false, assgn, true, 1);

		softAssertion.assertTrue(workflow.getWorkFlow_AssignedUsers().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("All existing users is displayed in the list and record saved Successfully");

		loginPage.logout();
	}

	
	/**
	 * Author : Baskar date: NA Modified date: 28/02/2022 Modified by: Baskar
	 * Description:To verify that all details displayed on history.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void validationActionHederValues() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52647");
		baseClass.stepInfo("To verify that all details displayed on history.");
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();

		// creating new work flow
		workflow = new WorkflowPage(driver);
		workflow.createNewWorkFlow();
		workflow.descriptionTab(wfName,wfDesc);
		baseClass.waitForElement(workflow.getSaveLinkButton());
		workflow.getSaveLinkButton().waitAndClick(10);
		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.selectWorkFlowUsingPagination(wfName);
		
		// action to viewhistory
		baseClass.waitForElement(workflow.getWorkFlow_ActionDropdown());
		workflow.getWorkFlow_ActionDropdown().waitAndClick(10);
		baseClass.waitForElement(workflow.getHistoryButton());
		workflow.getHistoryButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		
		// validating header count
		String expectedHeader="Workflow ID,Workflow Name,Run Start,Run End,Action,Status,Total Object Promoted";
		List<String> allHeader = new ArrayList<String>();
		List<WebElement> persistantNames =workflow.getActionHeader().FindWebElements();
		for (int i = 0; i < persistantNames.size(); i++) {
			allHeader.add(persistantNames.get(i).getText());
		}
		String actualHeaderActual = String.join(",", allHeader);
		softAssertion.assertEquals(expectedHeader.toUpperCase(), actualHeaderActual.toUpperCase());
		baseClass.passedStep("Header value as per the expected onces in action history popup window");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void validatestatus() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52655");
		baseClass.stepInfo("To verify that Workflow details should be filtered as per the selected Status.");
		int Id;
		int Id1;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		String assgn = "Assgn" + Utility.dynamicNameAppender();

		String wfDesc_Draft = "Desc" + Utility.dynamicNameAppender();
		String wfName_Draft  = "work" + Utility.dynamicNameAppender();


		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		 
	    page = new TagsAndFoldersPage(driver);
		page.CreateFolder(folderName,Input.securityGroup);

		// Search for any string
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id);
		UtilityLog.info(Id);

		// creating new work flow
		workflow = new WorkflowPage(driver); 
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false,3);
		
		// creating new work flow in draft state
		workflow.getWorkFlowPage();
		workflow.workFlow_Draft(wfName_Draft, wfDesc_Draft);
		
		String folderName_Complete = "WF" + Utility.dynamicNameAppender();
		String SearchName_Complete = "WF" + Utility.dynamicNameAppender();
		String wfName_Complete = "work" + Utility.dynamicNameAppender();
		String wfDesc_Complete = "Desc" + Utility.dynamicNameAppender();
		String assgn_Complete = "WF" + Utility.dynamicNameAppender();

		baseClass.selectproject();	
		page.CreateFolder(folderName_Complete, Input.securityGroup);
	     search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName_Complete);
		ss.getSaveSearchID(SearchName_Complete);
		Id1 = Integer.parseInt(ss.getSavedSearchID().getText());
		System.out.println(Id1);
		UtilityLog.info(Id1);

		// creating new work flow Completed status
		workflow.getWorkFlowPage();
		workflow.newWorkFlowCreation(wfName_Complete, wfDesc_Complete, Id1, false, folderName_Complete, true, assgn_Complete, false,3);
		workflow.selectWorkFlowUsingPagination(wfName_Complete);
		
		// Running workflow
		workflow.actionToRunWorkFlow();
		// Page refresh
		workflow.refreshingThePage();
		baseClass.waitTime(10);
		String[] status= {"Completed","Configured","Draft"};
		
		  this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		  List<String> listStatus = workflow.getTableHeaderValuesPagination("STATUS", false);
			int occurence_Comp = baseClass.findNoOfOccurences(listStatus, "Completed");
			int occurence_Conf= baseClass.findNoOfOccurences(listStatus, "Configured");
			int occurence_Draft= baseClass.findNoOfOccurences(listStatus, "Draft");
			int[] statusCount= {occurence_Comp,occurence_Conf,occurence_Draft};
			
		for(int i=0;i<status.length;i++) {	
			this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
			workflow.filterByStatus(status[i]);
			baseClass.stepInfo("Work flow Filter for status "+status[i]+" is apllied.");
			driver.waitForPageToBeReady();
			baseClass.waitTime(2);
			List<String> WfStatus_AfterFilter=workflow.getTableHeaderValuesPagination("STATUS", false);
			System.out.println(WfStatus_AfterFilter);
			if(WfStatus_AfterFilter.size()==statusCount[i]) {
				for(int j=0;j<WfStatus_AfterFilter.size();j++) {
				if( WfStatus_AfterFilter.get(j).equals(status[i])) {
					if(j==(WfStatus_AfterFilter.size()-1))
				baseClass.passedStep("Work flow filter for 'status' functionality working properly when we apply status as"+status[i]);
				}else {
					baseClass.failedStep("Work flow filter displayed different statud "+WfStatus_AfterFilter.get(i)+" which is not applied in filter");
				}
				}
			}
			else {
				baseClass.failedStep("work flow filter for status not working as expected.");
			}
		}

		// logout
		loginPage.logout();
	}
/**
 * @author Jayanthi.ganesan
 * @throws InterruptedException
 */
@Test(enabled = true, groups = { "regression" }, priority = 15)
public void verifyHistoryBtnEnabled() throws InterruptedException {
	baseClass.stepInfo("Test case Id: RPMXCON-52646");
	baseClass.stepInfo("To verify that 'View History' action should be enabled only if Workflow is selected.");
	
	String wfName = "work" + Utility.dynamicNameAppender();
	String wfDesc = "work" + Utility.dynamicNameAppender();
	// Login as Reviewer Manager
	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	workflow=new WorkflowPage(driver);
	workflow.workFlow_Draft(wfName,wfDesc);
	baseClass.stepInfo("Draft Work Flow Created with name "+wfName);
	//selecting workflow
	workflow.selectWorkFlowUsingPagination(wfName);
	workflow.getWorkFlow_ActionDropdown().waitAndClick(5);
	baseClass.stepInfo("Clicked on Action button.");
	//validation for display of enabled History button
	if(workflow.getEnabledHistoryBtn().isDisplayed()) {
		baseClass.passedStep("Sucessfully verified that RMU can view the action Enabled "
				+ "'View History' Button if we select any work flow.");
	}
	else {
		baseClass.failedStep("Enabled View History Button not displayed.");
	}
	//validation for display of disabled history button if we dint select any work flow.
	
	workflow.getWorkFlow_ActionDropdown().waitAndClick(5);
	baseClass.stepInfo("Clicked on Action button.");
	//here we are validating display of disabled History button with absence of enabled history button.
	if(!workflow.getEnabledHistoryBtn().isDisplayed()) {
		baseClass.passedStep("History button is in disabled state if we dint select any work flow");
	}
	else {
		baseClass.failedStep("History button is in ensabled state if we dint select any work flow which is not expected.");
	}
	loginPage.logout();
}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyAllColumnSorting() throws InterruptedException, ParseException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52618");
		baseClass.stepInfo("To verify that RMU can sort by ascending and descending on all columns");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		workflow = new WorkflowPage(driver);
		
		//verifying Enabled state column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listEnabled = workflow.getTableHeaderValuesPagination("ENABLED STATE", true);
		workflow.applySorting(true, false, true, "ENABLED STATE");
		workflow.verifyHeaderSort("ENABLED STATE", true, listEnabled, "Ascending");
		workflow.applySorting(false, true, true, "ENABLED STATE");
		workflow.verifyHeaderSort("ENABLED STATE", true, listEnabled, "Descending");
		
		//verifying status column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listStatus = workflow.getTableHeaderValuesPagination("STATUS", false);
		workflow.applySorting(true, false, true, "STATUS");
		workflow.verifyHeaderSort("STATUS", false, listStatus, "Ascending");
		workflow.applySorting(false, true, true, "STATUS");
		workflow.verifyHeaderSort("STATUS", false, listStatus, "Descending");
		
		//verifying  Workflow ID column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listWFID = workflow.getTableHeaderValuesPagination("Workflow ID", false);
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.verifyHeaderSort("Workflow ID", false, listWFID, "Ascending");
		workflow.applySorting(false, true, true, "Workflow ID");
		workflow.verifyHeaderSort("Workflow ID", false, listWFID, "Descending");
		
		//verifying  Last Modified Date column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listLMF = workflow.getTableHeaderValuesPagination("LAST MODIFIED DATE", false);
		workflow.applySorting(true, false, true, "LAST MODIFIED DATE");
		workflow.verifyHeaderSort("LAST MODIFIED DATE", false, listLMF, "Ascending");
		workflow.applySorting(false, true, true, "LAST MODIFIED DATE");
		workflow.verifyHeaderSort("LAST MODIFIED DATE", false, listLMF, "Descending");
		
		//verifying  Workflow Name column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listWFName = workflow.getTableHeaderValuesPagination("Workflow Name", false);
		workflow.applySorting(true, false, true, "Workflow Name");
		workflow.verifyHeaderSort("Workflow Name", false, listWFName, "Ascending");
		workflow.applySorting(false, true, true, "Workflow Name");
		workflow.verifyHeaderSort("Workflow Name", false, listWFName, "Descending");
		
		//verifying Next Triggered Date column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listTrigger = workflow.getTableHeaderValuesPagination("NEXT TRIGGER DATE", false);
		workflow.applySorting(true, false, true, "NEXT TRIGGER DATE");
		workflow.verifyHeaderSort("NEXT TRIGGER DATE", false, listTrigger, "Ascending");
		workflow.applySorting(false, true, true, "NEXT TRIGGER DATE");
		workflow.verifyHeaderSort("NEXT TRIGGER DATE", false, listTrigger, "Descending");
		
		//verifying Last run actioned doc count  column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listRunCount = workflow.getTableHeaderValuesPagination("LAST RUN ACTIONED DOC COUNT", false);
		workflow.applySorting(true, false, true, "LAST RUN ACTIONED DOC COUNT");
		workflow.verifyHeaderSort("LAST RUN ACTIONED DOC COUNT", false, listRunCount, "Ascending");
		workflow.applySorting(false, true, true, "LAST RUN ACTIONED DOC COUNT");
		workflow.verifyHeaderSort("LAST RUN ACTIONED DOC COUNT", false, listRunCount, "Descending");
		
		//verifying  Created by user column sorting
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		List<String> listCreatedBy = workflow.getTableHeaderValuesPagination("Created By", false);
		workflow.applySorting(true, false, true, "Created By");
		workflow.verifyHeaderSort("Created By", false, listCreatedBy, "Ascending");
		workflow.applySorting(false, true, true, "Created By");
		workflow.verifyHeaderSort("Created By", false, listCreatedBy, "Descending");

		// logout
		loginPage.logout();
	}
/**
 * @author Jayanthi.Ganesan
 * @throws InterruptedException
 * @throws ParseException
 * @throws AWTException
 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyEnabledStateFilter() throws InterruptedException, ParseException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52656");
		baseClass.stepInfo("To verify that Workflow details should be filtered as per the selected 'Enabled State'.");
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
		page.CreateFolder(folderName, Input.securityGroup);

		// Search for any string
		search = new SessionSearch(driver);
	    search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		

		// creating new work flow wirh enabled state.
		workflow = new WorkflowPage(driver);
		workflow.newWorkFlowCreation(wfName, wfDesc, Id, false, folderName, true, assgn, false, 1);
		workflow.selectWorkFlowUsingPagination(wfName);

		// Running workflow
		workflow.actionToRunWorkFlow();
		// Page refresh
		workflow.refreshingThePage();
		baseClass.waitTime(10);
		
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		//getting List Of Enabled state column before applying filter using Pagination.
		List<String> listEnabled = workflow.getTableHeaderValuesPagination("ENABLED STATE", true);
		//taking count of Enabled state work flows
		int occurence = baseClass.findNoOfOccurences(listEnabled, "ENABLED");
		//applying filter for  EnabledState.
		driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.filterByState("Enabled");
		//taking count of Enabled state work flows after applying enabled state filter.
		List<String> listEnabledAfterFilter = workflow.getTableHeaderValuesPagination("ENABLED STATE", true);
		SoftAssert assertion=new SoftAssert();
		
		//Validation part 
		//Verifying filtered list Count
		if (listEnabledAfterFilter.size() == occurence) {
			//Verifying filtered list contains  only enabled state
			for(int i=0;i<listEnabledAfterFilter.size();i++) {
				assertion.assertTrue( listEnabledAfterFilter.get(i).equalsIgnoreCase("ENABLED")) ;
			}
			assertion.assertAll();
			baseClass.passedStep("Sucessfully verified that Workflow details  filtered as per the selected 'Enabled State'.");
		} else {
			baseClass.failedStep(" Workflow details not filtered as per the selected 'Enabled State'.");
		}

		// logout
		loginPage.logout();
		}
	
	/**
	 * Author :Vijaya.Rani date: 24/03/2022 Modified date: NA Modified by: NA
	 * Description:To verify that Status list should be displayed as Complete or
	 * Assigned. 'RPMXCON-52605' Sprint-14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyRMUStatusListCompleteOptionDisplay() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52605");
		baseClass.stepInfo("To verify that Status list should be displayed as Complete or Assigned.");

		workflow = new WorkflowPage(driver);
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		this.driver.getWebDriver().get(Input.url + "WorkFlow/Details");
		workflow.verifyStatusListDisplay();

		// logout
		loginPage.logout();

	}

	/**
	 * Author :Vijaya.Rani date: 24/03/2022 Modified date: NA Modified by: NA
	 * Description:To verify that Summary tab will displays all the details.
	 * 'RPMXCON-52611' Sprint-14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifySummaryTabWillDisplayAllDetails() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id: RPMXCON-52611");
		baseClass.stepInfo("To verify that Summary tab will displays all the details.");

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
		workflow.newWorkFlowCreationSummaryDisplay(wfName, wfDesc, Id, true, folderName, false, assgn, true, 1);
		baseClass.stepInfo("Created WorkFlow Details is Display in Summary Tab Successfully");

		// logout
		loginPage.logout();

	}
	/**
	 * Author :jayanthi
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifySummaryTab() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id: RPMXCON-52612");
		baseClass.stepInfo("To verify that saved Workflow Name and description should be displayed in Summary tab");
		int Id;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		page = new TagsAndFoldersPage(driver);
		page.CreateFolder(folderName, Input.securityGroup);
		// Search for any string
		search = new SessionSearch(driver);
		 search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Thread.sleep(2000);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		UtilityLog.info(Id);	

		// creating new work flow
		workflow = new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName, wfDesc);
		driver.waitForPageToBeReady();
		workflow.editWorkFlow(wfName);
		workflow.nextButton();
		workflow.sourcesTab(Id);
		workflow.nextButton();
		workflow.nextButton();
		workflow.familyOptions(true);
		workflow.nextButton();
		workflow.actionTabToSelectFolder(folderName, true);
		workflow.nextButton();
		workflow.schedulesTab(1);
		workflow.nextButton();
		workflow.notificationTab();
		workflow.nextButton();
		softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		baseClass.stepInfo(" Entered all mandatory fields and Gone to summary tab  to verify WF name and description");
		baseClass.waitForElement(workflow.getSummaryTab_WFName());
		softAssertion.assertEquals(workflow.getSummaryTab_WFName().getText(),wfName);
		softAssertion.assertEquals(workflow.getSummaryTab_WFDesc().getText(),wfDesc);
		softAssertion.assertAll();
		
		baseClass.passedStep(" WF Name and Description is Displayed as expected in SummaryTab WFName- "
		+ ""+workflow.getSummaryTab_WFName().getText()+" WFDescription"+workflow.getSummaryTab_WFDesc().getText()+"");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		page.deleteAllFolders(folderName);
	
	}
	
	/**
	 * Author :Vijaya.Rani date: 14/04/2022 Modified date: NA Modified by: NA
	 * Description:To verify that selected Source details is displayed in Summary tab.
	 * 'RPMXCON-52613' Sprint-14
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifySourceInSummaryTab() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id: RPMXCON-52613");
		baseClass.stepInfo("To verify that selected Source details is displayed in Summary tab.");
		softAssertion = new SoftAssert();
		int Id;
		String folderName = "folder" + Utility.dynamicNameAppender();
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		page = new TagsAndFoldersPage(driver);
		page.CreateFolder(folderName, Input.securityGroup);
		// Search for any string
		search = new SessionSearch(driver);
		 search.basicContentSearch(Input.searchString1);

		// Save the search
		search.saveSearch(SearchName);
		SavedSearch ss = new SavedSearch(driver);
		ss.getSaveSearchID(SearchName);
		Thread.sleep(2000);
		Id = Integer.parseInt(ss.getSavedSearchID().getText());
		UtilityLog.info(Id);	

		// creating new work flow
		workflow = new WorkflowPage(driver);
		workflow.workFlow_Draft(wfName, wfDesc);
		driver.waitForPageToBeReady();
		workflow.editWorkFlow(wfName);
		workflow.nextButton();
		workflow.sourcesTab(Id);
		workflow.nextButton();
		workflow.nextButton();
		workflow.familyOptions(true);
		workflow.nextButton();
		workflow.actionTabToSelectFolder(folderName, true);
		workflow.nextButton();
		workflow.schedulesTab(1);
		workflow.nextButton();
		workflow.notificationTab();
		workflow.nextButton();
		softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		baseClass.stepInfo(" Entered all mandatory fields and Gone to summary tab  to verify WF Source and description");
		baseClass.waitForElement(workflow.getSummeryTabSource());
		workflow.getSummeryTabSource().waitAndClick(5);
		baseClass.waitForElement(workflow.getSummaryTab_SourceAssignment());
		softAssertion.assertTrue(workflow.getSummaryTab_SourceAssignment().Displayed());
		String SSName =workflow.getSummaryTab_SourceSavedSearch().getText();
		if(SSName.equals(Id)){
			baseClass.stepInfo("Source Saved Search Name is Displayed");
		}
		softAssertion.assertAll();
		
		baseClass.passedStep(" SourceSavedSearch and Assignment is Displayed as expected in SummaryTab SourceSavedSearch- "
		+ ""+workflow.getSummaryTab_SourceSavedSearch().getText()+" Source Assignment"+workflow.getSummaryTab_SourceAssignment().getText()+"");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		page.deleteAllFolders(folderName);
	
	}
	
	/**
	 * Author : Baskar date: NA Modified date: /04/2022 Modified by: Baskar
	 * Description:To verify that RMU can veiw the Action tab details on Workflow.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void validatingActionTabMessage() throws InterruptedException, ParseException {
		baseClass.stepInfo("Test case Id: RPMXCON-52554");
		baseClass.stepInfo("To verify that RMU can veiw the Action tab details on Workflow.");
		int Id;
		String SearchName = "WF" + Utility.dynamicNameAppender();
		String wfName = "work" + Utility.dynamicNameAppender();
		String wfDesc = "Desc" + Utility.dynamicNameAppender();
	    search = new SessionSearch(driver);

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

		// Creating workflow upto action tab
		workflow = new WorkflowPage(driver);
		baseClass.stepInfo("validating action tab query message");
		workflow.createNewWorkFlow();
		workflow.descriptionTab(wfName, wfDesc);
		workflow.nextButton();
		workflow.sourcesTab(Id);
		workflow.nextButton();
		workflow.nextButton();
		workflow.familyOptions(true);
		workflow.nextButton();
		
		// validating action tab message
		String actualActionQuery=workflow.getActionTab_QueryMessage().getText();
		softAssertion.assertEquals(actualActionQuery, actionQuery);
		
		String actualPageQuery=workflow.getActionTab_PageMessage().getText();
		softAssertion.assertEquals(actualPageQuery, pageQuery);
		softAssertion.assertAll();
		baseClass.passedStep("Action tab help query as per the expected one");

		// logout
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
