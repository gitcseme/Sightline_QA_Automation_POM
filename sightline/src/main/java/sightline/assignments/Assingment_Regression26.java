package sightline.assignments;

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
	    assignmentsPage.createAssignmentWithAllowUserToSave(assignmentName, codingForm);
	        
	    assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
	    driver.waitForPageToBeReady();
		assignmentsPage.addReviewerAndDistributeDocs();
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
	/**
	 * @author 
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Validate editing Keywords associated to security group by PAU
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54757", enabled = true, groups = { "regression" })
	public void verifyEditKeyWordAssociateToSg() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordname = "AAkeyword" + Utility.dynamicNameAppender();
		String Modifywordkeyname = "AAkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Validate editing Keywords associated to security group by PAU");
		baseClass.stepInfo("Test case Id: RPMXCON-54757");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordname, color);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU" + Input.rmu1userName);
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordname);
		baseClass.stepInfo(keywordname + " created keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU" + Input.pa1userName);
		keyword.navigateToKeywordPage();
		keyword.editExistigKeyword(keywordname, Modifywordkeyname);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU" + Input.rmu1userName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), Modifywordkeyname);
		baseClass.stepInfo(Modifywordkeyname + " modified keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		keyword.deleteKeywordByName(Modifywordkeyname);
		loginPage.logout();

	}

	/**
	 * @author 
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Validate editing Keywords created within security group by RMU
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54758", enabled = true, groups = { "regression" })
	public void verifyEditKeyWordSgByRmu() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordname = "AAkeyword" + Utility.dynamicNameAppender();
		String Modifywordkeyname = "AAModifiedkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Validate editing Keywords created within security group by RMU");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU " + Input.rmu1userName);
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordname, color);
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordname);
		baseClass.stepInfo(keywordname + " created keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keyword.navigateToKeywordPage();
		keyword.editExistigKeyword(keywordname, Modifywordkeyname);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), Modifywordkeyname);
		baseClass.stepInfo(Modifywordkeyname + " modified keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		keyword.deleteKeywordByName(Modifywordkeyname);
		loginPage.logout();

	}

	/**
	 * @author 
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Delete Keyword - Validate deleting Keywords associated to
	 *              security group
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54759", enabled = true, groups = { "regression" })
	public void verifyDeletingKeyWordAssociateToSg() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordName = "AAkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Delete Keyword - Validate deleting Keywords associated to security group");
		baseClass.stepInfo("Test case Id: RPMXCON-54759");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordName, color);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo("Validate editing Keywords associated to security group by PAU");
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordName);
		baseClass.stepInfo(keywordName + " created keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU");
		keyword.navigateToKeywordPage();
		keyword.deleteKeywordByName(keywordName);
		baseClass.stepInfo(keywordName + "Key word deleted sucessfully.");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as " + Input.rmu1userName);
		List<String> KeywordsListInKEywordPageAfterdelete = new ArrayList<String>();
		KeywordsListInKEywordPageAfterdelete = baseClass.getAvailableListofElements(keyword.getKeywordsList());
		baseClass.stepInfo("Checking for the Keyword Abscence After deleting the keyword as expected.");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyKeywordsBeforeAndAfterDelete(keywordName, KeywordsListInKEywordPageAfterdelete, false);
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();

	}

	/**
	 * @author 
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Validate removing and adding new keywords by PAU or RMU
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54760", enabled = true, groups = { "regression" })
	public void verifyRemovingAndAddingNewKeyWords() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordNamePA = "AAkeyword" + Utility.dynamicNameAppender();
		String keywordName1PA = "AAkeyword" + Utility.dynamicNameAppender();
		String ModifiedkeywordNamePA = "AAModifiedkeyword" + Utility.dynamicNameAppender();
		String keywordNameRMU = "AAkeyword" + Utility.dynamicNameAppender();
		String keywordName1RMU = "AAkeyword" + Utility.dynamicNameAppender();
		String ModifiedkeywordNameRMU = "AAModifiedkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Validate removing and adding new keywords by PAU or RMU");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo("Test case Id: RPMXCON-54760");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordNamePA, color);
		baseClass.stepInfo(keywordNamePA + "Keyword added");
		keyword.addKeywordWithSG(Input.securityGroup, keywordName1PA, color);
		keyword.editExistigKeyword(keywordName1PA, ModifiedkeywordNamePA);
		baseClass.stepInfo(ModifiedkeywordNamePA + "Modified Keyword added");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Validate editing Keywords created within security group by RMU");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordNameRMU, color);
		baseClass.stepInfo(keywordNameRMU + "Keyword added");
		keyword.addKeywordWithSG(Input.securityGroup, keywordName1RMU, color);
		keyword.editExistigKeyword(keywordName1RMU, ModifiedkeywordNameRMU);
		baseClass.stepInfo(ModifiedkeywordNameRMU + "Modified Keyword added");
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordNameRMU);
		baseClass.stepInfo(keywordNameRMU + " Added Rmu keyword is present in assignmant page as expected");
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordNamePA);
		baseClass.stepInfo(keywordNameRMU + " Added PA keyword is present in assignmant page as expected");
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), ModifiedkeywordNamePA);
		baseClass.stepInfo(
				ModifiedkeywordNamePA + " Added Modified Rmu keyword is present in assignmant page as expected");
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), ModifiedkeywordNamePA);
		baseClass.stepInfo(
				ModifiedkeywordNamePA + " Added Modified PA keyword is present in assignmant page as expected");
		driver.waitForPageToBeReady();
		keyword.deleteKeywordByName(keywordNameRMU);
		keyword.deleteKeywordByName(keywordNamePA);
	}
	
}
