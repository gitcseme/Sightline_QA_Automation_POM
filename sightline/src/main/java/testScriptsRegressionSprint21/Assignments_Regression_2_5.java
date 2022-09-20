package testScriptsRegressionSprint21;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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

public class Assignments_Regression_2_5 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage agnmt;
	BaseClass baseClass;
	Input in;

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
		agnmt = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that if documents are less than the value in "Allowance of drawn document to:" then RU can draw all the documents.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53885", enabled = true, groups = { "regression" })
	public void verifyAllowanceOfDrawDocuments() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String count = "8";
		SessionSearch search = new SessionSearch(driver);
		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53885");
		baseClass.stepInfo("To verify that if documents are less than the value in \"Allowance of drawn document to:\" then RU can draw all the documents.");
		agnmt.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		int purehit =search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.distributeTheGivenDocCountToReviewer(count);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		agnmt.verifyDrawPoolToggledisplay(assignmentName, "enabled");
		String DocsBeforeClickDrawAction = agnmt.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotalDocsBeforeClickDrawAction = DocsBeforeClickDrawAction.substring(6,9).trim();
		sa.assertEquals(TotalDocsBeforeClickDrawAction, count);
		sa.assertAll();
		baseClass.passedStep("The expected value "+TotalDocsBeforeClickDrawAction+" is displayed in reviewer page before draw action exactly");
		baseClass.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).Click();		
		baseClass.waitForElementToBeGone(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName), 5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(agnmt.getTotalDocsCountInReviewerDashboard(assignmentName));
		driver.scrollingToElementofAPage(agnmt.getTotalDocsCountInReviewerDashboard(assignmentName));
		String DocsAfterClickDrawAction = agnmt.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotalDocsAfterClickDrawAction = DocsAfterClickDrawAction.substring(6,9).trim();
		sa.assertEquals(Integer.toString(purehit), TotalDocsAfterClickDrawAction);
		sa.assertAll();
		baseClass.passedStep("The expected value of remaining docs "+TotalDocsAfterClickDrawAction+" is displayed in reviewer page after draw action exactly");
		baseClass.passedStep("Successfully verified that if documents are less than the value in \"Allowance of drawn document to:\" then RU can draw all the documents.");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);		
		loginPage.logout();
	}
	
	/**
	 * @author Jayanthi.ganesan
	 *  Verify that 3 tabs appear as defined when the user has elected to Assign documents
	 */
	@Test(description = "RPMXCON-54335", groups = { "regression" }, enabled = true)
	public void verifyAssignOptions() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54335");
		baseClass.stepInfo("Verify that 3 tabs appear as defined when the user has elected to Assign documents");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		baseClass.stepInfo("Performing basic content search and dragging pure hit to cart.");
		sessionSearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Clicking on bulk assign button");
		sessionSearch.bulkAssign();
		//verification of tab under assign radio button
		sessionSearch.verifyBulkAssignOptions();
		
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 19/09/2022 TestCase Id:RPMXCON-54751
	 * Description :New Assignment - By default all keywords part of the security group 
	 * should be selected for the assignment 
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54751",enabled = true, groups = { "regression" })
	public void verifyKeywordSelectionInAssignment() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54751");
		baseClass.stepInfo(
				"By default all keywords part of the security group should be selected for the assignment ");
		String assignmentName = "assign"+Utility.dynamicNameAppender();
		String[] keywords= {"test","the","and","in","of","to","at","are","for","on"};
		List<String> keys =  Arrays.asList(keywords);
		String color= "Red";
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Create keywords");
		KeywordPage keyPage = new KeywordPage(driver);
		for(int i=0;i<keywords.length;i++) {
			keyPage.addKeyword(keywords[i], color);
		}
		baseClass.stepInfo("Create new assignment");
		agnmt.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Check keyword popup and save assignment details");
		agnmt.verifyKeywordsAvailabilityInAssignment(keywords);
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(10);
		baseClass.stepInfo("Assign document , add reviewer and distribute documents");
		sessionSearch.MetaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.sourceKeyDocId);
		sessionSearch.bulkAssignExisting(assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		loginPage.logout();
		baseClass.stepInfo("Login as reviewer and verify keyword highlight in docview");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(agnmt.getSelectAssignmentAsReviewer(assignmentName));
		agnmt.getSelectAssignmentAsReviewer(assignmentName).waitAndClick(10);;
		driver.waitForPageToBeReady();
		DocViewPage docView = new DocViewPage(driver);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(10);
		docView.verifyPersistantHitsWithDocView(keys);
		baseClass.passedStep("All keywords related to assignment highlighted");
		loginPage.logout();
	}
	@Test(description = "RPMXCON-53716", enabled = true, groups = { "regression" })
	public void verifyUncompleteAllDocs() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String count ="2";

		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53716");
		baseClass.stepInfo("To verify that RMU is able to mark as Incompleted for the remaining documents in the Assignments");
		
		baseClass.stepInfo("***Creating assignment and bulk assign docs and distributing docs to user*");
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		int purehit =sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssignExisting(assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.distributeTheGivenDocCountToReviewer(count);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Logged in as reviewer user");
		baseClass.stepInfo("*Completing "+count+" docs from distributed documents for assignment-"+assignmentName+" **");
		// navigating from Dashboard to DocView
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		// Completing the  documents
		driver.waitForPageToBeReady();
	    docViewPage.CompleteTheDocumentInMiniDocList(Integer.parseInt(count));
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("** uncompleting All docs **");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		agnmt.uncompleteAllDocs(assignmentName);
		
		// Taking count of Left To Do /Total Docs In Assignment/ Distributed to User/
		// Complete columns from manage assignments page .
		baseClass.stepInfo("**After uncompleting All docs verifying Various counts of docs coulumn in asignemnt**");
		String leftToDo = agnmt.getRowValueFromAssignmentTable("Left To Do", assignmentName);
		String totalDocsInAsign = agnmt.getRowValueFromAssignmentTable("Total Docs In Assignment", assignmentName);
		String distributesDocsCount = agnmt.getRowValueFromAssignmentTable("Distributed to User", assignmentName);
		String completedDocsCount = agnmt.getRowValueFromAssignmentTable("Complete", assignmentName);
		
		sa.assertEquals(leftToDo, count);
		sa.assertEquals(totalDocsInAsign, String.valueOf(purehit));
		sa.assertEquals(distributesDocsCount, count);
		sa.assertEquals(completedDocsCount,"0");
		sa.assertAll();
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
		System.out.println("**Executed  Assignments_Regression2_5 .**");
	}

}
