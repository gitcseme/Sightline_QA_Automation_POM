package sightline.assignments;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ClassificationPage;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression_2_2 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssertion;
	SecurityGroupsPage securityGroupsPage;
	TagsAndFoldersPage tagsAndFolderPage;
	DocViewRedactions docViewRedact;

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
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @Description :To verify that if Documents are assigned to Reviwer then
	 *              "Unassigned Documents" is displayed as "0".
	 */
	@Test(description = "RPMXCON-53650", enabled = true, groups = { "regression" })
	public void verifyUnassignedDocsCount_DistribTab() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53650");
		baseClass.stepInfo("To verify that if Documents are assigned to Reviwer then "
				+ "\"Unassigned Documents\" is displayed as \"0\".");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// creating assignment
		assignPage.createAssignment(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment with name  -" + assgnName);
		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		String countToAssing = sessionSearch.verifyPureHitsCount();
		// performing bulk assign action and verifying doc count in assignment page.
		sessionSearch.bulkAssign();
		assignPage.assignwithSamplemethod(assgnName, "Count of Selected Docs", countToAssing);
		baseClass.waitTillElemetToBeClickable(assignPage.getAssignmentActionDropdown());
		driver.scrollPageToTop();
		assignPage.getAssignmentActionDropdown().waitAndClick(10);
		assignPage.assignmentActions("Edit");
		// adding reviewer and distributing docs
		assignPage.addReviewerAndDistributeDocs();
		// taking unassigned docs count
		driver.Navigate().refresh();
		assignPage.getDistributeTab().Click();
		String uassignedDocCount = assignPage.getEditAggn_Distribute_Unassgndoc().getText();
		baseClass.compareTextViaContains(uassignedDocCount, "0",
				"Count of UnAssigned docs under distribute tab  Displayed 0.",
				"Count of UnAssigned docs in distribute  Displayed is " + uassignedDocCount);

		assignPage.deleteAssgnmntUsingPagination(assgnName);

		// logout
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @Description :To verify that created Coding Form is displayed on Coding Form field for Assignment.
	 */
	@Test(description = "RPMXCON-53970", enabled = true, groups = { "regression" })
	public void verifyCreatedCfDisplayedInAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53970");
		baseClass.stepInfo("To verify that created Coding Form is displayed on Coding Form field for Assignment.");
		String assignmentName = "Assgn" + Utility.dynamicNameAppender();
		String cfName = "cf"+Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		CodingForm codingForm = new CodingForm(driver);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(cfName);
		codingForm.CreateCodingFormWithParameter(cfName,null,"Document_Comments",null,"comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.saveCodingForm();
	    assignPage.createAssignment_withoutSave(assignmentName, cfName);
	    baseClass.passedStep("Newly created codingform displayed in assignment successfully");
	    codingForm.deleteCodingForm(cfName, cfName);
	    loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @Description :To verify that Coding form field is displayed for Assignment only
	 */
	@Test(description = "RPMXCON-53964", enabled = true, groups = { "regression" })
	public void verifyCfFieldDisplayedInAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53964");
		baseClass.stepInfo("To verify that Coding form field is displayed for Assignment only");
		String assignmentName = "Assgn" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssgnGrp" + Utility.dynamicNameAppender();
		System.out.println(assignmentGrpName);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGrpName, "Yes");
		if(assignPage.getSelectSortCodingForm_Tab().isDisplayed()==false) {
			baseClass.passedStep("Coding form field is not displayed in new assignment group page");
		}else {
			baseClass.failedStep("Coding form field is displayed in new assignment group page");
		}
		baseClass.waitTillElemetToBeClickable(assignPage.SaveButton());
		assignPage.SaveButton().waitAndClick(10);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.editAssgnGrp(assignmentGrpName,"No");
		if(assignPage.getSelectSortCodingForm_Tab().isDisplayed()==false) {
			baseClass.passedStep("Coding form field is not displayed in edit assignment group page");
		}else {
			baseClass.failedStep("Coding form field is displayed in edit assignment group page");
		}
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.DeleteAssgnGroup(assignmentGrpName);		
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		baseClass.passedStep("Coding form fiels is displayed in new assignment page");
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		if(assignPage.getSelectSortCodingForm_Tab().isDisplayed()) {
			baseClass.passedStep("Coding form field is displayed in edit assignment page");
		}else {
			baseClass.failedStep("Coding form field is not displayed in edit assignment page");
		}
		assignPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
		
	}
	/**
	 * @author jayanthi
	 * @throws InterruptedException
	 * @Description :To verify that RMU can assign the documents from Saved Search.
	 */
	@Test(description = "RPMXCON-53643", enabled = true, groups = { "regression" })
	public void verifyassignedDocsCount_savedsearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53643");
		baseClass.stepInfo("To verify that RMU can assign the documents from Saved Search.");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		String searchName = "searchAssgn" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("**Pre REq-Creating Assignment**");
		// creating assignment
		assignPage.createAssignment(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment with name  -" + assgnName);
		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		String countToAssing = sessionSearch.verifyPureHitsCount();
		sessionSearch.saveSearch(searchName);
		baseClass.stepInfo("Saved the search with name "+searchName);
		// performing bulk assign action and verifying doc count in assignment page.
		sessionSearch.bulkAssign();
		assignPage.assignwithSamplemethod(assgnName, "Count of Selected Docs", countToAssing);

		// logout
		loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 *  Verify that when the radio button selected is to Unassign documents,
	 *  only the Existing Assignment(s) tab appears
	 */
	@Test(description = "RPMXCON-54321", groups = { "regression" }, enabled = true)
	public void verifyUnAssignOptions() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54321");
		baseClass.stepInfo("Verify that when the radio button selected is to Unassign documents, only the"
				+ " Existing Assignment(s) tab appears");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		baseClass.stepInfo("Performing basic content search and dragging pure hit to cart.");
		sessionSearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Clicking on bulk assign button");
		sessionSearch.bulkAssign();
		sessionSearch.verifyUnAssignOptions();
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that numbers set in \"Allowance of drawn document to:\" by RMU for Assignment, "
				+ "that number of documents can be drawn from General Pool by RU on clicking \"Draw from Pool\"
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53884", enabled = true, groups = { "regression" })
	public void verifyAllowOfDrawnGeneralPool() throws Exception {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String rev[] = { Input.rev1userName };
		String expDocCount = "20";

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		SoftAssert sa = new SoftAssert();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53884 Assignments");
		baseClass.stepInfo("To verify that numbers set in \"Allowance of drawn document to:\" by RMU for Assignment, "
				+ "that number of documents can be drawn from General Pool by RU on clicking \"Draw from Pool\"");

		agnmt.createAssignment_withoutSave(assignmentName, Input.codingFormName);
//		Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		baseClass.stepInfo("numbers set in Allowance of drawn document is 20");
		search.basicContentSearch(Input.searchStringStar);
		search.bulkAssign();
		agnmt.assignwithSamplemethod(assignmentName, "Count of Selected Docs", "1000");
		baseClass.stepInfo("Successfully 1000 documents are assigned to the Assignment");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.assignReviewers(rev);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		agnmt.verifyDrawPoolToggledisplay(assignmentName, "enabled");
		baseClass.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).Click();		
		baseClass.waitForElementToBeGone(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName), 5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(agnmt.getTotalDocsCountInReviewerDashboard(assignmentName));
		driver.scrollingToElementofAPage(agnmt.getTotalDocsCountInReviewerDashboard(assignmentName));
		String ActualDocs_value = agnmt.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotslDocs = ActualDocs_value.substring(6,9).trim();
		sa.assertEquals(expDocCount, TotslDocs);
		sa.assertAll();
		baseClass.passedStep(
				"Successfully Verified That - numbers set in \"Allowance of drawn document to:\" by RMU for Assignment, "
						+ "that number of documents can be drawn from General Pool by RU on clicking \"Draw from Pool");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		
		loginPage.logout();

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that draw from pool link will be dispalyed after the documents are completed
	 *               for the assignment which was distributed manually.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53888", enabled = true, groups = { "regression" })
	public void verifyDrawnPoolLinkafterCompleted() throws Exception {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53888 Assignments");
		baseClass.stepInfo("To verify that draw from pool link will be dispalyed after the documents are completed "
				+ "for the assignment which was distributed manually.");

		agnmt.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		search.basicContentSearch(Input.searchString1);
		search.bulkAssignExisting(assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		String docDistriCount = agnmt.distributeTheGivenDocCountToReviewer("2");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		agnmt.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= Integer.parseInt(docDistriCount); i++) {
			docView.completeDocument(Input.randomText);
		}
		driver.waitForPageToBeReady();
		agnmt.navigateBack(1);
		driver.waitForPageToBeReady();
		boolean drawLink = agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isDisplayed();
		sa.assertTrue(drawLink);
		sa.assertAll();
		baseClass.stepInfo(
				"The Draw link is visible to the RU after marking the manually distributed documents as Completed.");
		baseClass.passedStep(
				"verified - that draw from pool link will be dispalyed after the documents are completed for the assignment which was distributed manually.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description verify that Total Documents displayed on Distribute Documents are correct if one/multiple assignments are selected while assigning the Assignments
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53645", enabled = true, groups = { "regression" })
	public void verifyTotalDocDisonDDMultAssign() throws Exception {
		String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "AR2Assignment" + Utility.dynamicNameAppender();
		String noToAssign = "1000";

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		BaseClass baseClass = new BaseClass(driver);
		SoftAssert sa = new SoftAssert();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53645 Assignments");
		baseClass.stepInfo("verify that Total Documents displayed on Distribute Documents are correct"
				+ " if one/multiple assignments are selected while assigning the Assignments");
		agnmt.createAssignment(assignmentName1, Input.codeFormName);
		agnmt.createAssignment(assignmentName2, Input.codeFormName);

		int purehit = search.basicContentSearch(Input.searchStringStar);
		baseClass.waitForElement(search.getDocsMetYourCriteriaLabel());
		baseClass.dragAndDrop(search.getDocsMetYourCriteriaLabel(), search.getActionPad());
		driver.waitForPageToBeReady();
		search.bulkAssign();
		baseClass.stepInfo("Assign/Unassign Docs pop up is displayed");
		baseClass.waitForElement(search.getSelectAssignmentExisting(assignmentName1));
		search.getSelectAssignmentExisting(assignmentName1).Click();
		baseClass.stepInfo(assignmentName1 + "Selected in Assign Popup");
		baseClass.waitForElement(search.getSelectAssignmentExisting(assignmentName2));
		search.getSelectAssignmentExisting(assignmentName2).Click();
		baseClass.stepInfo(assignmentName2 + "Selected in Assign Popup");

		agnmt.VerifySampleMethodDDOptions();
		baseClass.waitTillTextToPresent(agnmt.getStartingCount(), String.valueOf(purehit));
		baseClass.waitForElement(agnmt.getsampleMethod());
		agnmt.getsampleMethod().selectFromDropdown().selectByVisibleText("Count of Selected Docs");
		baseClass.stepInfo("Count of Selected Docs Option Selected in SampleMethod DD");
		agnmt.getCountToAssign().SendKeys(noToAssign);
		baseClass.stepInfo(noToAssign + "Entered in Number to Assign tab Successfully");
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		baseClass.waitTillElemetToBeClickable(search.getContinueButton());
		baseClass.waitForElement(search.getContinueButton());
		search.getContinueButton().waitAndClick(30);
		baseClass.waitForElement(search.getFinalizeButton());
		search.getFinalizeButton().waitAndClick(30);

		agnmt.editAssignmentUsingPaginationConcept(assignmentName1);
		baseClass.waitForElement(agnmt.getDistributeTab());
		agnmt.getDistributeTab().Click();
		baseClass.waitForElement(agnmt.getEditAggn_Distribute_Totaldoc());
		sa.assertEquals(noToAssign, agnmt.getEditAggn_Distribute_Totaldoc().getText());
		baseClass.stepInfo("Distribute Documents is displayed and Total Documents is displayed as "
				+ agnmt.getEditAggn_Distribute_Totaldoc().getText());

		baseClass.waitForElement(agnmt.getAssignment_BackToManageButton());
		agnmt.getAssignment_BackToManageButton().Click();

		agnmt.editAssignmentUsingPaginationConcept(assignmentName2);
		baseClass.waitForElement(agnmt.getDistributeTab());
		agnmt.getDistributeTab().Click();
		baseClass.waitForElement(agnmt.getEditAggn_Distribute_Totaldoc());
		sa.assertEquals(noToAssign, agnmt.getEditAggn_Distribute_Totaldoc().getText());
		baseClass.stepInfo("Distribute Documents is displayed and Total Documents is displayed as 1000"
				+ agnmt.getEditAggn_Distribute_Totaldoc().getText());
		sa.assertAll();
		baseClass.passedStep("verified - that Total Documents displayed on Distribute Documents are correct "
				+ "if one/multiple assignments are selected while assigning the Assignments");
		agnmt.deleteAssgnmntUsingPagination(assignmentName1);
		agnmt.deleteAssgnmntUsingPagination(assignmentName2);
		loginPage.logout();

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description Verified that validations are displayed for fields Name, RateValue, RateType as Expected
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53613", enabled = true, groups = { "regression" })
	public void verifyRateDDinClassPage() throws Exception {
		LoginPage lp = new LoginPage(driver);
		ClassificationPage clssp = new ClassificationPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53613");
		baseClass.stepInfo("To Verify Rate Dropdown");

		clssp.navigateClassificationPage();
		clssp.VerifyRateDDOptions();
		clssp.verifyClassifactionFieldsErrorAlerts();
		baseClass.stepInfo("Verified that validations are displayed for fields Name, RateValue, RateType as Expected");

		// Verifying Name Field Error Message
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		clssp.getMaxQualified().selectFromDropdown().selectByVisibleText("--Select--");
		baseClass.waitForElement(clssp.getClassificationNameQC());
		clssp.updateQCClassificDetailsNotSave("!@#$%^&*()_+", "$ - AUD", "1");
		sa.assertEquals(clssp.getClassificationNameQCErrormsg().getText(),
				"You cannot specify any special characters in the field value");
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "1");
		sa.assertFalse(clssp.getClassificationNameQCErrormsg().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("1234565", "$ - AUD", "1");
		sa.assertFalse(clssp.getClassificationNameQCErrormsg().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("Minor", "$ - AUD", "1");
		sa.assertFalse(clssp.getClassificationNameQCErrormsg().isElementAvailable(5));
		baseClass.stepInfo("Verified that validations are displayed for Name Field as Expected");

		// Verifying Rate Value Field Error Message
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		clssp.getMaxQualified().selectFromDropdown().selectByVisibleText("--Select--");
		baseClass.waitForElement(clssp.getClassificationRatevalueQC());
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "!@#$%^&*()_+");
		sa.assertEquals(clssp.getClassificationRatevalueErrormsgQC().getText(),
				"The currency value specified is invalid.");
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "1RS");
		sa.assertEquals(clssp.getClassificationRatevalueErrormsgQC().getText(),
				"The currency value specified is invalid.");
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "1234565");
		sa.assertFalse(clssp.getClassificationRatevalueErrormsgQC().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "65.65");
		sa.assertFalse(clssp.getClassificationRatevalueErrormsgQC().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "Dollar");
		sa.assertEquals(clssp.getClassificationRatevalueErrormsgQC().getText(),
				"The currency value specified is invalid.");
		baseClass.stepInfo("Verified that validations are displayed for Rate Value Field as Expected");

		// Verifying maxQualifiedFunctionality Working Properly
		clssp.verifyMaxQualifiedFunctionality(1);
		clssp.verifyMaxQualifiedFunctionality(2);
		clssp.verifyMaxQualifiedFunctionality(3);
		clssp.verifyMaxQualifiedFunctionality(4);
		clssp.verifyMaxQualifiedFunctionality(5);

		// Save Classification For Project And Verify that in New Assignment Page
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		clssp.getMaxQualified().selectFromDropdown().selectByValue(Integer.toString(2));
		clssp.updateLevelClassificDetailsNotSave(1, "1LR", "$ - USD", "5");
		clssp.updateLevelClassificDetailsNotSave(2, "2LR", "$ - USD", "10");
		clssp.updateQCClassificDetailsNotSave("QC", "$ - USD", "15");
		baseClass.waitForElement(clssp.getSaveBtn());
		clssp.getSaveBtn().Click();
		agnmt.navigateToAssignmentsPage();
		agnmt.NavigateToNewEditAssignmentPage("new");

		List<String> expOptions = new ArrayList<>(Arrays.asList("1LR", "2LR", "QC"));
		List<String> actOptions = agnmt.verifyClassificationDDOptions();
		baseClass.stepInfo(actOptions + " Options are available in classification DD");
		sa.assertEquals(actOptions, expOptions);
		baseClass.stepInfo("Verified that it displays following classifications:      a. 1LR      b. 2LR      c. QC");
		sa.assertAll();
		baseClass.passedStep("Successfully Verified Rate dropdown");
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description Verified that validations are displayed for fields Name, RateValue, RateType as Expected
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53616", enabled = true, groups = { "regression" })
	public void verifyRMUCanDefineClassforProject() throws Exception {
		String maxQualified = "2";
		List<String> expOptions = new ArrayList<>(Arrays.asList("1LR", "2LR", "3LR"));

		LoginPage lp = new LoginPage(driver);
		ClassificationPage clssp = new ClassificationPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53616");
		baseClass.stepInfo("To verify that if RMU define the Classification for project "
				+ "then it is displayed while creating the assignment for that project.");
		baseClass.selectproject();
		clssp.navigateClassificationPage();
		clssp.getMaxQualified().selectFromDropdown().selectByValue(maxQualified);
		baseClass.stepInfo(maxQualified + " Selected in Max Qualified DropDown");
		clssp.updateLevelClassificDetailsNotSave(1, "1LR", "$ - USD", "5");
		clssp.updateLevelClassificDetailsNotSave(2, "2LR", "$ - USD", "10");
		clssp.updateQCClassificDetailsNotSave("3LR", "$ - USD", "15");
		baseClass.waitForElement(clssp.getSaveBtn());
		clssp.getSaveBtn().Click();
		baseClass.VerifySuccessMessage("Cancel");
		baseClass.stepInfo("Classification for the project is saved Successfully");
		agnmt.navigateToAssignmentsPage();
		agnmt.NavigateToNewEditAssignmentPage("new");
		baseClass.stepInfo("New AssignmentPage Displaying");

		List<String> actOptions = agnmt.verifyClassificationDDOptions();
		baseClass.stepInfo(actOptions + " Options are available in classification DD");
		sa.assertEquals(actOptions, expOptions);
		baseClass.stepInfo("Verified that it displays following classifications:      a. 1LR      b. 2LR      c. 3LR");
		baseClass.passedStep("verified - that if RMU define the Classification for project then it is displayed "
				+ "while creating the assignment for that project.");
		sa.assertAll();
		lp.logout();
	}
	
	/**
	 * Author :Arunkumar date: 04/08/2022 TestCase Id:RPMXCON-54033
	 * Description :To verify that Tags displayed in Tags pop up are security group specific
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54033",enabled = true, groups = { "regression" })
	public void verifyTagsAreSecurityGroupSpecific() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54033");
		baseClass.stepInfo("Verify that Tags displayed in Tags pop up are security group specific.");
		String sgName1 = "SecurityGroup1_"+ UtilityLog.dynamicNameAppender();
		String sgName2 = "SecurityGroup2_"+ UtilityLog.dynamicNameAppender();
		String tagName1 = "securityGroup1_Tag" + UtilityLog.dynamicNameAppender();
		String tagName2 = "securityGroup2_Tag" + UtilityLog.dynamicNameAppender();
		String assignmentName1 ="assignment1" + UtilityLog.dynamicNameAppender();
		String assignmentName2 ="assignment2" + UtilityLog.dynamicNameAppender();
		String cfName1 = "codingForm1"+ UtilityLog.dynamicNameAppender();
		String cfName2 = "codingForm2"+ UtilityLog.dynamicNameAppender();
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(sgName1);
		securityGroupsPage.AddSecurityGroup(sgName2);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkReleaseToMultipleSecurityGroups(sgName1, sgName2);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rev1userName);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(sgName2, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(sgName2, Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("create assignment and tag for security group 1");
		assignPage.createTagOrFolderCodingFormAssignment(sgName1, cfName1, assignmentName1, Input.testData1, "tag", tagName1);
		baseClass.stepInfo("get tags in popup available in security group 1");
		assignPage.verifyTagOrFolderAvailabilityInAssignment(sgName1, assignmentName1, Input.rev1userName, "tag");
		if(!sessionSearch.getExistingTagSelectionCheckBox(tagName2).isElementAvailable(10) && 
				sessionSearch.getExistingTagSelectionCheckBox(tagName1).isElementAvailable(10)) {
			baseClass.passedStep("Tags are securityGroup specific");
		}
		else {
			baseClass.failedStep("Tags are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		assignPage.createTagOrFolderCodingFormAssignment(sgName2, cfName2, assignmentName2, Input.testData1, "tag", tagName2);
		assignPage.verifyTagOrFolderAvailabilityInAssignment(sgName2, assignmentName2, Input.rev1userName, "tag");
		if(!sessionSearch.getExistingTagSelectionCheckBox(tagName1).isElementAvailable(10) &&
				sessionSearch.getExistingTagSelectionCheckBox(tagName2).isElementAvailable(10)) {
			baseClass.passedStep("Tags are securityGroup specific");
		}
		else {
			baseClass.failedStep("Tags are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		//Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if (!sgName1.equalsIgnoreCase(Input.securityGroup) && !sgName2.equalsIgnoreCase(Input.securityGroup)) {
		securityGroupsPage.deleteSecurityGroups(sgName1);
		driver.waitForPageToBeReady();
		securityGroupsPage.deleteSecurityGroups(sgName2);
		}
		loginPage.logout();
				
	}
	
	/**
	 * Author :Arunkumar date: 05/08/2022 TestCase Id:RPMXCON-54034
	 * Description :To verify that Folders displayed in Folders pop up are security group specific
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-54034",enabled = true, groups = { "regression" })
	public void verifyFoldersAreSecurityGroupSpecific() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54034");
		baseClass.stepInfo("To verify that Folders displayed in Folders pop up are security group specific.");
		String sgName1 = "Security Group1_"+ UtilityLog.dynamicNameAppender();
		String sgName2 = "Security Group2_"+ UtilityLog.dynamicNameAppender();
		String folderName1 = "securityGroup1_Folder" + UtilityLog.dynamicNameAppender();
		String folderName2 = "securityGroup2_Folder" + UtilityLog.dynamicNameAppender();
		String assignmentName1 ="assignment1" + UtilityLog.dynamicNameAppender();
		String assignmentName2 ="assignment2" + UtilityLog.dynamicNameAppender();
		String cfName1 = "codingForm1"+ UtilityLog.dynamicNameAppender();
		String cfName2 = "codingForm2"+ UtilityLog.dynamicNameAppender();
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(sgName1);
		securityGroupsPage.AddSecurityGroup(sgName2);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkReleaseToMultipleSecurityGroups(sgName1, sgName2);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rev1userName);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(sgName2, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(sgName2, Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("create assignment and tag for security group 1");
		assignPage.createTagOrFolderCodingFormAssignment(sgName1, cfName1, assignmentName1, Input.testData1, "folder", folderName1);
		baseClass.stepInfo("get tags in popup available in security group 1");
		assignPage.verifyTagOrFolderAvailabilityInAssignment(sgName1, assignmentName1, Input.rev1userName, "folder");
		if(!sessionSearch.getExistingFolderSelectionCheckBox(folderName2).isElementAvailable(5) && 
				sessionSearch.getExistingFolderSelectionCheckBox(folderName1).isElementAvailable(5)) {
			baseClass.passedStep("Folders are securityGroup specific");
		}
		else {
			baseClass.failedStep("Folders are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		assignPage.createTagOrFolderCodingFormAssignment(sgName2, cfName2, assignmentName2, Input.testData1, "folder", folderName2);
		assignPage.verifyTagOrFolderAvailabilityInAssignment(sgName2, assignmentName2, Input.rev1userName, "folder");
		if(!sessionSearch.getExistingFolderSelectionCheckBox(folderName1).isElementAvailable(5) &&
				sessionSearch.getExistingFolderSelectionCheckBox(folderName2).isElementAvailable(5)) {
			baseClass.passedStep("Folders are securityGroup specific");
		}
		else {
			baseClass.failedStep("Folders are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		//Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if (!sgName1.equalsIgnoreCase(Input.securityGroup) && !sgName2.equalsIgnoreCase(Input.securityGroup)) {
		securityGroupsPage.deleteSecurityGroups(sgName1);
		driver.waitForPageToBeReady();
		securityGroupsPage.deleteSecurityGroups(sgName2);
		}
		loginPage.logout();
				
	}
		
	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
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
		System.out.println("**Executed  Assignments_Regression2_2 .**");
	}

}
