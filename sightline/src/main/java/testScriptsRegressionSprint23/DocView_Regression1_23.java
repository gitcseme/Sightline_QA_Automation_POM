package testScriptsRegressionSprint23;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression1_23 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

//		Input in = new Input();
//		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * Author :Iyappan date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63747 Verify user can apply the saved coding stamp which includes
	 * Comments saved with the Copy menu
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-63747", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 69)
	public void verifyUserCanApplySavedStampcommentsSavedCopyMenu() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63747");
		baseClass.stepInfo(
				"Verify user can apply the saved coding stamp which includes Comments saved with the Copy menu ");
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String docid = "T2541D";
		String docid1 = "ID00000158";
		docexp = new DocExplorerPage(driver);
		DocListPage doclist = new DocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");

		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		docexp.navigateToDocExplorerPage();
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
		doclist.getApplyFilter().waitAndClick(10);

		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		if (doclist.getYesAllPageDocs().isDisplayed()) {
			doclist.getYesAllPageDocs().waitAndClick(5);
			doclist.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(5);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(docid);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.scrollPageToTop();
		docView.perfromCodingStampSelection(Input.stampColours);
		baseClass.waitForElement(docView.getMiniDocId(docid));
		docView.getMiniDocId(docid).waitAndClick(2);
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampSelection);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getCodingFormSaveThisForm());
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.passedStep("Applied coding saved successfully");
		baseClass.waitForElement(docView.getDocView_MiniDocListIds(3));
		docView.getDocView_MiniDocListIds(3).waitAndClick(2);
		docView.editCodingForm(comment);
		docView.clickGearIconOpenCodingFormChildWindow();
		docView.switchTochildWindow();
		docView.codingStampButton();
		docView.switchToNewWindow(1);
		docView.popUpAction(fieldText, Input.stampColour);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		docView.switchTochildWindow();
		String getAttribute = docView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		if (getAttribute.equals(comment)) {
			baseClass.passedStep("Comment is displayed on codingform panel successfully");
		} else {
			baseClass.failedStep("not displayed");
		}
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		docView.closeWindow(1);
		docView.switchToNewWindow(1);
		baseClass.passedStep("Applied coding saved successfully");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.deleteStampColour(Input.stampColours);
		docView.deleteStampColour(Input.stampSelection);
		docView.deleteStampColour(Input.stampColour);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		docexp.navigateToDocExplorerPage();
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Message");
		doclist.getApplyFilter().waitAndClick(10);

		docexp.getDocExp_SelectAllDocs().isElementAvailable(5);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		if (doclist.getYesAllPageDocs().isDisplayed()) {
			doclist.getYesAllPageDocs().waitAndClick(5);
			doclist.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(5);
		docexp.docExp_BulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid1);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.scrollPageToTop();
		docView.perfromCodingStampSelection(Input.stampColours);
		baseClass.waitForElement(docView.getMiniDocId(docid1));
		docView.getMiniDocId(docid1).waitAndClick(2);
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampSelection);
		driver.waitForPageToBeReady();
		docView.completeButton();
		docView.editCodingForm(comment);
		docView.codingStampButton();
		docView.popUpAction(fieldText, Input.stampColour);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		String getAttribute1 = docView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		if (getAttribute1.equals(comment)) {
			baseClass.passedStep("Comment is displayed on codingform panel successfully");
		} else {
			baseClass.failedStep("Not displayed");
		}
		docView.codingStampButton();
		baseClass.passedStep("Applied coding saved successfully");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.deleteStampColour(Input.stampColours);
		driver.waitForPageToBeReady();
		docView.deleteStampColour(Input.stampSelection);
		driver.waitForPageToBeReady();
		docView.deleteStampColour(Input.stampColour);
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
