package testScriptsRegressionSprint27;

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

public class AssignmentsRegression_27 {

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
	 * @Description :New Assignment - Keywords created by PAU and RMU part of the
	 *              security group should be selected for the assignment.
	 *              RPMXCON-54752
	 */
	//@Test(description = "RPMXCON-54752", enabled = true, groups = { "regression" })
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
