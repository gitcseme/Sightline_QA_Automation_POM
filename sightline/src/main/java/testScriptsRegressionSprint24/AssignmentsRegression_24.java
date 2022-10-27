package testScriptsRegressionSprint24;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AssignmentsRegression_24 {

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

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that label of the textbox is changed where RMU enters
	 *              numbers for Sample method.RPMXCON-53637
	 */

	@Test(description = "RPMXCON-53637", enabled = true, groups = { "regression" })
	public void verifyLableOfTextboxChangedWhereRMUEnterNumberOfSampleMethod() throws InterruptedException {
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String listOfSampleMethodOptions[] = { "Percent of Selected Docs", "Parent Level Docs Only",
				"Count of Selected Docs", "Inclusive Email" };

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53637 Assignments");
		baseClass
				.stepInfo("To verify that label of the textbox is changed where RMU enters numbers for Sample method.");

		// create assignment
		assignment.createAssignment(assignmentName, Input.codeFormName);

		// perform basic content search
		sessionSearch.basicContentSearch(Input.searchString1);

		// perform bulk assign
		sessionSearch.bulkAssign();

		// Select Sample method as "Percent of Selected Docs" and Verify that Label is
		// changed to "Percentage to Assign"
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[0], true, true);

		// Select Sample method as "Parent Level Docs Only" and Verify that label is
		// removed.
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[1], true, false);

		// Select Sample method as "Inclusive Email" and Verify that label is removed.
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[2], true, false);

		// Select sample method as "Count of Selected Docs" and Verify that label is
		// changed to "Number to Assign"
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[3], true, false);

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/10/2022 TestCase Id:RPMXCON-54327
	 * Description :Verify that the Show Document Counts toggle is OFF by default
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54327",enabled = true, groups = { "regression" })
	public void verifyShowDocsCountToggleStatus() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54327");
		baseClass.stepInfo("Verify that the Show Document Counts toggle is OFF by default.");
		String assignName = "Aassign"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Pre-requisite: assigning docs to assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignName);
		baseClass.stepInfo("Search for documents and action as bulk assign");
		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Select unassign documents radio button");
		baseClass.waitForElement(assignment.getUnassignbutton());
		assignment.getUnassignbutton().waitAndClick(10);
		//verify
		if(assignment.getSelectAssignmentToBulkAssign(assignName).isElementAvailable(10)
				&& assignment.getExisitingAssignTab().isElementAvailable(10)) {
			baseClass.passedStep("Existing assignment,group and tab present in the assign/unassign popup");
			String toggleStatus = assignment.showDocCountToggle().GetAttribute("class");
			baseClass.stepInfo("Toggle off status :"+toggleStatus);
			if(toggleStatus.equalsIgnoreCase("true")) {
				baseClass.failedStep("Show docscount toggle enabled by default");
			}
			else {
				baseClass.passedStep("Show Document Count toggle is OFF by default");
			}
		}
		else {
			baseClass.failedStep("Existing tab and assignment not present in the assign/unassign popup");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/10/2022 TestCase Id:RPMXCON-54326
	 * Description :Verify that the Starting Count of Docs is consistent with the selection on the prior screen
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54326",enabled = true, groups = { "regression" })
	public void verifyStartingCountOfDocsStatus() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54326");
		baseClass.stepInfo("Verify that the Starting Count of Docs is consistent with selection.");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Search for docs and action as bulk assign");
		int count = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify the starting count of docs");
		baseClass.waitForElement(assignment.getStartingCountDoc());
		int docCount = Integer.parseInt(assignment.getStartingCountDoc().getText());
		baseClass.stepInfo("count in shopping cart for pure hit :"+count);
		baseClass.stepInfo("starting count of docs in assign/unassign popup :"+docCount);
		if(count==docCount) {
			baseClass.passedStep("Starting count of docs same as selection in prior screen");
		}
		else {
			baseClass.failedStep("Starting count of docs not same as selection in prior screen");
		}
		loginPage.logout();
	}
	
	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :New Assignment - Keywords created by PAU and RMU part of the
	 *              security group should be selected for the assignment.
	 *              RPMXCON-54752
	 */
	@Test(description = "RPMXCON-54752", enabled = true, groups = { "regression" })
	public void verifyNewAssignmentKeywordsCreatedByPAUandRMUPartOfSecurityGrpSelectedForAssignment()
			throws AWTException, InterruptedException {
		keyPage = new KeywordPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		List<String> keywordName01 = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e"));
		List<String> keywordName02 = new ArrayList<String>(Arrays.asList("f", "g", "h", "i", "j"));
		List<String> color01 = new ArrayList<String>(Arrays.asList("Aqua", "Blue", "Gold", "Pink", "Aqua"));

		// login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54752 Assignments");
		baseClass.stepInfo(
				"New Assignment - Keywords created by PAU and RMU part of the security group should be selected for the assignment.");

		// creating keywords as PAU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keywords as PAU");
		List<String> listOfKeywordGroup01 = keyPage.addMultipleKeywords(keywordName01, color01, true,
				Input.securityGroup);
		loginPage.logout();

		// creating keywords as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keywords as RMU");
		List<String> listOfKeywordGroup02 = keyPage.addMultipleKeywords(keywordName02, color01, false,
				Input.securityGroup);

		// Adding into list
		keywordName01.addAll(keywordName02);
		listOfKeywordGroup01.addAll(listOfKeywordGroup02);
		String[] allKeywordsGroup = new String[listOfKeywordGroup01.size()];
		listOfKeywordGroup01.toArray(allKeywordsGroup);

		// creating assignment with keywords
		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);

		// verify that Add Keyword window should pop-up with all keywords associated the
		// security group (keywords created by both PAU and RMU) selected
		assignment.verifyKeywordsAvailabilityInAssignment(allKeywordsGroup);
		assignment.verifyAddedKeywordsChecked();
		baseClass.passedStep(
				"verified that Add Keyword window pop-up with all keywords associated the security group (keywords created by both PAU and RMU) selected.");
		assignment.getSaveBtn().waitAndClick(5);
		baseClass.stepInfo("assignment : '" + assignmentName + " created.");

		// performing bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// adding reviewers and distributing documents to reviewers
		baseClass.stepInfo("adding reviewers and distributing documents to reviewers");
		assignment.add2ReviewerAndDistribute();

		// logOut
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from DashBoard and navigating to DocView
		baseClass.stepInfo("selecting assignment from DashBoard and navigating to DocView.");
		assignment.SelectAssignmentByReviewer(assignmentName);

		// Keyword should be highlighted on the DocView
		docView.verifyKeywordsOnDocView(keywordName01);
		baseClass.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		baseClass.passedStep("verified that Keywords are highlighted on the DocView.");

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete all keywords
		keyPage.navigateToKeywordPage();
		keyPage.deleteMultipleKeywords(listOfKeywordGroup01);

		// delete assignment
		assignment.deleteAssignment(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Existing Assignment - All Keywords created by PAU and RMU part
	 *              of the security group should be selected for existing
	 *              assignment.. RPMXCON-54754
	 */
	@Test(description = "RPMXCON-54754", enabled = true, groups = { "regression" })
	public void verifyExistingAssignmentAllKeywordsCreadtedByPAUandRMUPartOfSecurityGroupSelectedForExistingAssignment()
			throws AWTException, InterruptedException {
		keyPage = new KeywordPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String existingKeyword = "t";
		String existingKeywordGroup = "t" + Utility.dynamicNameAppender();
		String PauKeyword = "u";
		String RmuKeyword = "v";
		String RmuKeywordGroup = "v" + Utility.dynamicNameAppender();
		List<String> allKeywords = new ArrayList<String>();
		List<String> colors = new ArrayList<String>();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54754 Assignments");
		baseClass.stepInfo(
				"Existing Assignment - All Keywords created by PAU and RMU part of the security group should be selected for existing assignment.");

		// creating keyword for pre-requisite
		keyPage.navigateToKeywordPage();

		// keyPage.addKeyword(existingKeyword,Input.KeyWordColour);
		keyPage.addKeyword(existingKeywordGroup, existingKeyword, Input.KeyWordColour);
		assignment.createAssignment(assignmentName, Input.codingFormName);

		// performing bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// adding reviewers and distributing documents to reviewers
		baseClass.stepInfo("adding reviewers and distributing documents to reviewers");
		assignment.add2ReviewerAndDistribute();

		// logOut
		loginPage.logout();

		// login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// creating keyword as PAU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keyword as PAU");
		allKeywords.add(PauKeyword);
		colors.add(Input.KeyWordColour);
		List<String> listOfKeywordGroup = keyPage.addMultipleKeywords(allKeywords, colors, true, Input.securityGroup);
		listOfKeywordGroup.add(existingKeywordGroup);
		listOfKeywordGroup.add(RmuKeywordGroup);

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating keyword as RMU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keyword as RMU");

		// keyPage.addKeyword(RmuKeyword,Input.KeyWordColour);
		keyPage.addKeyword(RmuKeywordGroup, RmuKeyword, Input.KeyWordColour);
		allKeywords.add(existingKeyword);
		allKeywords.add(RmuKeyword);
		String[] keywords = new String[listOfKeywordGroup.size()];
		listOfKeywordGroup.toArray(keywords);

		// verify that Add Keyword window pop-up with all keywords selected (added by
		// PAU and RMU as well)
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.verifyKeywordsAvailabilityInAssignment(keywords);
		assignment.verifyAddedKeywordsChecked();
		baseClass.passedStep(
				"verified that Add Keyword window pop-up with all keywords selected (added by PAU and RMU as well).");
		assignment.getSaveBtn().waitAndClick(5);

		// logOut
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from DashBoard and navigating to DocView
		baseClass.stepInfo("selecting assignment from DashBoard and navigating to DocView.");
		assignment.SelectAssignmentByReviewer(assignmentName);

		// Keyword should be highlighted on the DocView
		docView.verifyKeywordsOnDocView(allKeywords);
		baseClass.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		baseClass.passedStep("verified that Keywords are highlighted on the DocView.");

		// logOut
		driver.waitForPageToBeReady();
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete all keywords
		keyPage.navigateToKeywordPage();
		keyPage.deleteMultipleKeywords(listOfKeywordGroup);

		// delete assignment
		assignment.deleteAssignment(assignmentName);

		// logOut
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
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
