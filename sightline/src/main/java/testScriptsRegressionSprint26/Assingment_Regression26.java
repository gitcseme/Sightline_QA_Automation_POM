package testScriptsRegressionSprint26;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assingment_Regression26 {

	
	
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;

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
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

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
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
	
	
	
	/**
	 * @author sowndarya
	 * @Modified by: N/A
	 * @Description : User able to change Set as Default coding form in Add/Remove Coding Forms from Assignment using radio button in Set as.[RPMXCON-65551]
	 */
	@Test(description = "RPMXCON-65551", enabled = true, groups = { "regression" })
	public void verifySeDiffCodingFormToAssignment() throws InterruptedException {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-65551");
		baseClass.stepInfo("User able to change Set as Default coding form in Add/Remove Coding Forms from Assignment using radio button in Set as");

		String cfName="C"+ Utility.dynamicNameAppender();
		
		//create a new coding form
		CodingForm form = new CodingForm(driver);
		form.navigateToCodingFormPage();
		form.createCodingform(cfName);
		
		// creating Assignment
		assignment.navigateToAssignmentsPage();
		assignment.createAssignment(assignmentName, Input.codingFormName);
		// performing bulk Assign

		//edit the assignment with new coding form as default.
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.EditCodingformInSelectedAssignment(cfName);
		loginPage.logout();
		
	}
	
	/**
	 * @author N/A
	 * @Modified by: N/A
	 * @Description :  Verify In Add/Edit Assignment toggle 'Allow users to save without completing' is enabled,In Doc view context of an Assignment we can save document without completing it.[RPMXCON-65575]
	 */
	@Test(description = "RPMXCON-65575", enabled = true, groups = { "regression" })
	public void verifyToggleAllowUserSaveWithoutComplete() throws Exception {
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		Dashboard dash = new Dashboard(driver);
		DocViewPage docView = new DocViewPage(driver);
		
		String codingForm = Input.codingFormName;
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			
		baseClass.stepInfo("RPMXCON-65575");
		baseClass.stepInfo("To Verify In Add/Edit Assignment toggle 'Allow users to save without completing' is enabled, "
				+ "In Doc view context of an Assignment we can save document without completing it");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
	    driver.waitForPageToBeReady();
		assignmentsPage.assignmentCreation(assignmentName, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.addReviewerAndDistributeDocs();
		driver.waitForPageToBeReady();
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		driver.waitForPageToBeReady();
		
		baseClass.waitForElement(docView.getSaveDoc());
		if(docView.getSaveDoc().isElementAvailable(5)) {
			baseClass.passedStep("Save Coding Button Displaying As Expected");
		} else {
			baseClass.failedStep("Save Coding Button Displaying Not As Expected");
		}
		baseClass.passedStep("Verified - In Add/Edit Assignment toggle 'Allow users to save without completing' is enabled,"
				+ " In Doc view context of an Assignment we can save document without completing it");
		loginPage.logout();
		
	}


	/**
	 * @author Vijaya.Rani ModifyDate:28/11/2022 RPMXCON-54216
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is equal to the total docs assigned (i.e. 20).
	 */
	@Test(description = "RPMXCON-54216", enabled = true, groups = { "regression" })
	public void verifyDrawFromPoolIsNotDisplayedInReviewerPage() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54216");
		baseClass.stepInfo(
				"Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is equal to the total docs assigned (i.e. 20).");

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");

		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// distributing docs
		agnmt.distributeTheGivenDocCountToReviewer("20");
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		
		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		if (!agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(3)) {
			baseClass.passedStep("Draw pool link is Not displayed");
		} else {
			baseClass.failedStep("Draw pool link is displayed");
		}
		loginPage.logout();
	}
	
	
	/**
	 * @author Vijaya.Rani ModifyDate:28/11/2022 RPMXCON-54217
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is less than the total docs assigned (i.e. 40)
	 */
	@Test(description = "RPMXCON-54217", enabled = true, groups = { "regression" })
	public void verifyDrawFromPoolIsNotDisplayedInReviewerPageFromAsssignPage() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54217");
		baseClass.stepInfo(
				"Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is less than the total docs assigned (i.e. 40)");

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		agnmt.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		agnmt.getAssignmentActionDropdown().waitAndClick(10);
		agnmt.getAssignmentAction_NewAssignment().waitAndClick(10);
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");

		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// distributing docs
		agnmt.distributeTheGivenDocCountToReviewer("20");
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		
		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		if (!agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(3)) {
			baseClass.passedStep("Draw pool link is Not displayed");
		} else {
			baseClass.failedStep("Draw pool link is displayed");
		}
		loginPage.logout();
	}
	
}
