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

public class DocViewAudio_Regression1_23 {

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
	 * @author Iyappan ModifyDate: RPMXCON-51343
	 * @throws Exception
	 * @Description Verify >| icon and |< in the DocView Audio Player controls
	 *              should move to the next & previous phonetic hit position when
	 *              mini doc list child window is open
	 */
	@Test(description = "RPMXCON-51343", alwaysRun = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyIconInDocViewAudioPlayerMoveNextPhoneticHitIsOpenChildWindow(String username, String password,
			String fullname) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51343");
		baseClass.stepInfo(
				"Verify >| icon and |< in the DocView Audio Player controls should move to the next & previous phonetic hit position when mini doc list child window is open");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullname);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Navigate to DocView Page
		baseClass.stepInfo("Navigate to DocView Page");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		docviewPage.clickGearIconOpenMiniDocList();
		baseClass.passedStep(" Clicked gear icon mini doc list child window is opened");
		docviewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docviewPage.getClickDocviewID(2);
		baseClass.stepInfo("Clicked audio doc file is loaded");
		docviewPage.switchToNewWindow(1);
		baseClass.waitTime(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String nexticon = docviewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docviewPage.getDocView_IconNext());
		docviewPage.getDocView_IconNext().waitAndClick(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String nexticon1 = docviewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (nexticon.equals(nexticon1)) {
			baseClass.failedStep("Cursor is not moved");
			
		} else {
			baseClass.passedStep("Cursor is moved to the next phonetic hit position successfully");
		}
		docviewPage.audioPlayPauseIcon().waitAndClick(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String previousicon = docviewPage.getDocView_RunningTime().getText();
		baseClass.waitForElement(docviewPage.getDocView_IconPrev());
		docviewPage.getDocView_IconPrev().waitAndClick(5);
		baseClass.waitForElement( docviewPage.getDocView_RunningTime());
		String previousicon1 = docviewPage.getDocView_RunningTime().getText();
		driver.waitForPageToBeReady();
		if (previousicon.equals(previousicon1)) {
			baseClass.failedStep("Cursor is not moved");
		} else {
			baseClass.passedStep("Cursor is moved to the previous phonetic hit position successfully");

		}
	
	}
	/**
	 * @author Iyappan date: NA Modified date: NA Modified by: NA TestCase ID:
	 *         RPMXCON-51487
	 * @throws Exception
	 * @Description Verify 3 play counter readouts are displayed on audio doc view
	 */
	@Test(description = "RPMXCON-51487", alwaysRun = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyPlaycounterReadoutsDisplayedOnDocView(String username, String password, String fullname)
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51487");
		baseClass.stepInfo("Verify 3 play counter readouts are displayed on audio doc view");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docviewPage = new DocViewPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as : " + fullname);
		sessionSearch.basicContentSearch(Input.oneHourAudio);

		// Navigate to DocView Page audio
		baseClass.stepInfo("Navigate to DocView Page audio");
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();

		// Get verify Audio counter readouts duration
		String Audiostarttime = docviewPage.getDocview_Audio_StartTime().getText();
		if (docviewPage.getDocview_Audio_StartTime().isDisplayed()) {
			System.out.println(Audiostarttime);
			baseClass.passedStep(
					Audiostarttime + "..Starttime play counter readouts is  successfully displayed on docview audio");
		} else {
			baseClass.failedStep("Start play counter readouts is not displayed");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docviewPage.audioPlayPauseIcon());
		docviewPage.audioPlayPauseIcon().waitAndClick(10);
		docviewPage.audioPlayPauseIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		String runningTime = docviewPage.getDocView_RunningTime().getText();
		if (docviewPage.getDocView_RunningTime().isDisplayed()) {
			System.out.println(runningTime);
			baseClass.passedStep(runningTime
					+ ".. In Runningtime play counter readouts is  successfully displayed on docview audio");
		} else {
			baseClass.failedStep("Runningtime play counter readouts is not displayed");

		}
		String Audioendtime = docviewPage.getDocview_Audio_EndTime().getText();
		if (docviewPage.getDocview_Audio_EndTime().isDisplayed()) {
			baseClass.passedStep(
					Audioendtime + "..Endtime play counter readouts is  successfully displayed on docview audio");

		} else {
			baseClass.failedStep("Endtime play counter readouts is not displayed");
		}

	}
	
	
	/**
	 * Author :Iyappan date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63780 Verify that when Copy menu is selected from doc view and
	 * 'View Document' action selects from Analytics Panel then selected
	 * panels/menus should be retain
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-63780",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyCopyMenuSelectedDocViewViewDocSelectFromAnalyticalPanel() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63780");
		baseClass.stepInfo("Verify that when Copy menu is selected from doc view and 'View Document' action selects from Analytics Panel then selected panels/menus should be retain");
		DocViewPage docView = new DocViewPage(driver);
		String docid = "T2541D";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		docexp = new DocExplorerPage(driver);
		DocListPage doclist = new DocListPage(driver);

		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
		doclist.getApplyFilter().waitAndClick(10);
		
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(docid);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.waitForPageToBeReady();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		
		//verify Doc Selected in analytical panel view doc
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupeTab());
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_Analytics_NearDupe_Doc(1));
		docView.getDocView_Analytics_NearDupe_Doc(1).waitAndClick(5);
		baseClass.stepInfo("Document is selected in analytical panel");
		baseClass.waitForElement(docView.getDocView_ChildWindow_ActionButton());
		docView.getDocView_ChildWindow_ActionButton().waitAndClick(15);
		baseClass.waitForElement(docView.getViewDocumentNearDupe());
		docView.getViewDocumentNearDupe().waitAndClick(10);
		baseClass.stepInfo("Select action as View Document in docview page");
		driver.waitForPageToBeReady();
		if (docView.getCopyPasteIcon().Enabled()) {
			baseClass.passedStep("'Copy' menu is retained in selected document");
			
		}else {
			baseClass.failedStep("not retained");
		}
		
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.waitForPageToBeReady();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		
	}
	

/**
	 * Author :Iyappan date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63747 Verify user can apply the saved coding stamp which includes
	 * Comments saved with the Copy menu
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-63747",enabled = true, alwaysRun = true, groups = { "regression" }, priority = 69)
	public void verifyUserCanApplySavedStampcommentsSavedCopyMenu() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63747");
		baseClass.stepInfo(
				"Verify user can apply the saved coding stamp which includes Comments saved with the Copy menu ");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String docid = "ID00000152";
		String docid1 = Input.DocIdCopyPaste1;
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		docexp = new DocExplorerPage(driver);
		DocListPage doclist = new DocListPage(driver);
		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
		doclist.getApplyFilter().waitAndClick(10);
		
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
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
		loginPage.logout();

		// Create assignment and go to docview
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
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
		docView.deleteStampColour(Input.stampSelection);
		docView.deleteStampColour(Input.stampColour);
	}

	@DataProvider(name = "PaRmuRev")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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
