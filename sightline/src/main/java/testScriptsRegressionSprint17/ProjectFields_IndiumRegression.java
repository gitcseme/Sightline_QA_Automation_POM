package testScriptsRegressionSprint17;

import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.model.Author;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DataSets;

public class ProjectFields_IndiumRegression {
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
	TagsAndFoldersPage tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	DataSets data;
	IngestionPage_Indium ingestionPage;

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
	 * @Author :Baskar Date:14/07/2022
	 * @Description : To verify that Project Field Listing page is displayed.
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55594", enabled = true, groups = { "regression" })
	public void verifyProjectFieldHomePage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55594");
		baseClass.stepInfo(
				"To verify that Project Field Listing page is displayed.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName);
		this.driver.getWebDriver().get(Input.url + "ProjectFields/ProjectFieldsList");
		baseClass.stepInfo("User landed on the  ProjectFields page");
		
		// validating all tabs
	    boolean flagManage=driver.getPageSource().contains("Manage Project Fields");
	    softAssertion.assertTrue(flagManage);
	    boolean flagAdd=driver.getPageSource().contains("Add Project Field");
	    softAssertion.assertTrue(flagAdd);
	    boolean flagGroup=driver.getPageSource().contains("Add Project Field Group");
	    softAssertion.assertTrue(flagGroup);
	    List<WebElement> headerData=fields.getProjectFieldHeader().FindWebElements();
	    List<String> find=new LinkedList<String>();
	    List<String> actual =Arrays.asList("FIELD NAME", "DATA TYPE","IS TALLYABLE","IS SEARCHABLE","IS ACTIVE","ACTION");
	    for (WebElement webElement : headerData) {
	    	String value=webElement.getText().toString();
	    	find.add(value);
		}
	    for (int i = 0; i < find.size(); i++) {
	    	 for (int j = 0; j < actual.size(); j++) {
	    	softAssertion.assertEquals(actual.get(j), find.get(i));
	    	i++;
		}  
	    	 } 
	    boolean flagEdit=driver.getPageSource().contains("Edit");
	    softAssertion.assertTrue(flagEdit);
	    boolean flagInactive=driver.getPageSource().contains("Inactive");
	    softAssertion.assertTrue(flagInactive);
	    boolean flagPagination=fields.getPagination().isElementAvailable(2);
	    softAssertion.assertTrue(flagPagination);
	    softAssertion.assertAll();
	    
	    baseClass.passedStep("All functionality tab are displed in projectfieldpages");
	    loginPage.logout();
		
		
	}
	
	/**
	 * @Author :Baskar Date:14/07/2022
	 * @Description : To verify Edit functionality for 'Project Field'.
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55607", enabled = true, groups = { "regression" })
	public void validatingEditFunctionalInProjectField() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55607");
		baseClass.stepInfo(
				"To verify Edit functionality for 'Project Field'.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
		String editDesc = "edit" + Utility.dynamicNameAppender();
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		baseClass.stepInfo("Adding new project fields");
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");
		fields.applyFilterByFilterName(projectFieldINT);
		
		// validating the edit functionality
		fields.editprojectFieldFieldDescription(projectFieldINT,editDesc);
		baseClass.passedStep("Edit functionality for project fields, working properly");
		
		// verifying the updated value
		fields.applyFilterByFilterName(projectFieldINT);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(fields.getFieldNameEdititButton(projectFieldINT));
		fields.getFieldNameEdititButton(projectFieldINT).waitAndClick(10);
		baseClass.waitForElement(fields.getFieldDescriptionTextArea());
		String updateValue=fields.getFieldDescriptionTextArea().getText();
		softAssertion.assertEquals(updateValue, editDesc);
		softAssertion.assertAll();
		baseClass.stepInfo("updated value saved successfully");
		
	    loginPage.logout();
		
		
	}
	
	/**
	 * @Author :Baskar Date:14/07/2022
	 * @Description : To verify when Sys Admin Impersonate as Project Admin and adds Project Fields
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55610", enabled = true, groups = { "regression" })
	public void validatingSAImpPAImp() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55610");
		baseClass.stepInfo(
				"To verify when Sys Admin Impersonate as Project Admin and adds Project Fields");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
		
		// Login as a PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.sa1userName + "'");
		
		baseClass.impersonateSAtoPA();

		// Custom Field created with INT DataType
		baseClass.stepInfo("Adding new project fields");
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");
		
		// validating project fields added in display
		fields.applyFilterByFilterName(projectFieldINT);
		fields.verifyAppliedFieldNameIsAddedInProjectFieldsTable(projectFieldINT);
		
	    loginPage.logout();
		
		
	}
	
	/**
	 * @Author :Baskar Date:15/07/2022
	 * @Description : To verify functionality of 'Cancel' button, from 'Project Field Group'.
	 * @throws : Exception
	 */
	@Test(description = "RPMXCON-55611", enabled = true, groups = { "regression" })
	public void verifyCancelBtnForProjectGroup() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55611");
		baseClass.stepInfo(
				"To verify functionality of 'Cancel' button, from 'Project Field Group'.");
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		projectPage = new ProjectPage(driver);
		ProjectFieldsPage fields=new ProjectFieldsPage(driver);
		String groupName = "GroupName" + Utility.dynamicNameAppender();
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
		
		// validating function of cancel button for project group
		baseClass.stepInfo("Clicking add project field group");
		this.driver.getWebDriver().get(Input.url + "ProjectFields/ProjectFieldsList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(fields.getAddProjectFieldGroup());
		fields.getAddProjectFieldGroup().waitAndClick(5);
		fields.getAddFieldGroupName().SendKeys(groupName);
		fields.getGroupCancelBtn().waitAndClick(5);
		
		// verify after cancel, text should not present in drop down
		baseClass.waitForElement(projectPage.getAddProjectButton());
		projectPage.getAddProjectButton().waitAndClick(5);
		fields.getFieldGroupNameDp_Dwn().waitAndClick(5);
		List<WebElement> allValue=fields.getFieldGroupNameDp_DwnValue().FindWebElements();
		for (WebElement webElement : allValue) {
			boolean flag=webElement.getText().equals(groupName);
			if (!flag) {
				baseClass.passedStep("Group name not displyed in drop down");
				baseClass.stepInfo("Cancel button functionality its wotking fine");
			}
			else {
				baseClass.failedStep("Cancel button functionality not as expected");
			}
		}
		
		
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
		try {
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
