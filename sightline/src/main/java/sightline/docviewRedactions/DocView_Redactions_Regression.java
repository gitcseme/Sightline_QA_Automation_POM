package sightline.docviewRedactions;

import pageFactory.DocViewPage;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Redactions_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
	

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		//Input in = new Input();
		//in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		loginPage.quitBrowser();

	}
	
	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-52214 Verify that multiple Rectangle Redaction does not remain
	 * selected on DocView Screen
	 * Stabilization done
	 */
	@Test(description = "RPMXCON-52214",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void verifyMultiRecRedactionNotRemainSelected() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		String docId = Input.searchDocId;

		baseClass.stepInfo("Test case Id: RPMXCON-52214");
		baseClass.stepInfo("Verify that multiple Rectangle Redaction does not remain selected on DocView Screen");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Select the doc from mini doc list for redactions");
		docViewPage.selectDocIdInMiniDocList(docId);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Creation of Rectangle Redaction on First Page");
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 200);

		docViewRedact.selectingRectangleRedactionTag();

		baseClass.waitForElement(docViewRedact.docViewNextPage());
		docViewRedact.docViewNextPage().waitAndClick(10);

		baseClass.waitForElement(docViewRedact.redactionIcon());
		docViewRedact.redactionIcon().waitAndClick(20);

		baseClass.stepInfo("Creation of Rectangle Redaction on Next Page");
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 200);
		docViewRedact.selectingRectangleRedactionTag();

		baseClass.stepInfo("Prerequisites created successfully");

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Verify whether the multiple rectangle redaction does not remain selected for RMU user");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.redactionIcon());
		baseClass.stepInfo("Select the doc from mini doc list for redactions");
		docViewPage.selectDocIdInMiniDocList(docId);

		baseClass.stepInfo("Verify whether the redaction popup is displayed on select of redacted page");
		docViewRedact.verifyWhetherRedactionIsSaved(true);

		driver.scrollPageToTop();
		baseClass.waitForElement(docViewRedact.docViewNextPage());
		docViewRedact.docViewNextPage().Click();

		baseClass.stepInfo("Verify whether the redaction popup is displayed on select of next redacted page");
		docViewRedact.verifyWhetherRedactionIsSaved(true);

		if (docViewRedact.getRedactionPopups().size() == 1) {
			softAssert.assertEquals(docViewRedact.getRedactionPopups().size(), 1);
			baseClass.passedStep("Multiple rectangle redaction does not remain selected, Only one is selected");
		}

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as User: " + Input.rev1userName);

		baseClass
				.stepInfo("Verify whether the multiple rectangle redaction does not remain selected for Reviewer user");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.redactionIcon());
		baseClass.stepInfo("Select the doc from mini doc list for redactions");
		docViewPage.selectDocIdInMiniDocList(docId);

		baseClass.stepInfo("Verify whether the redaction popup is displayed on select of redacted page");
		docViewRedact.verifyWhetherRedactionIsSaved(true);

		driver.scrollPageToTop();
		baseClass.waitForElement(docViewRedact.docViewNextPage());
		docViewRedact.docViewNextPage().Click();

		baseClass.stepInfo("Verify whether the redaction popup is displayed on select of next redacted page");
		docViewRedact.verifyWhetherRedactionIsSaved(true);

		baseClass.stepInfo(
				"Verify whether the multiple rectangle redaction does not remain selected, Only one is selected");
		if (docViewRedact.getRedactionPopups().size() == 1) {
			softAssert.assertEquals(docViewRedact.getRedactionPopups().size(), 1);
			baseClass.passedStep("Multiple rectangle redaction does not remain selected, Only one is selected");
		}

		softAssert.assertAll();
		loginPage.logout();

	}
	
	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49372 Verify that when 'This page' redaction selected to delete with
	 * 'Delete' key from keyboard should be disabled keyboard action
	 * 
	 */
	@Test(description ="RPMXCON-49372",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void verifyKeyBoardDelActionDisabledForThisRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SoftAssert softAssert = new SoftAssert();
		// Login As RMU
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON-49372 ");
		baseClass.stepInfo(
				"Verify that when 'This Page' redaction selected to delete with 'Delete' key from keyboard should be disabled keyboard action");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("Adding Text redaction");
		docViewRedact.RedactTextInDocView(1, 1, 10, 10);
		docViewRedact.selectingRectangleRedactionTag();

		baseClass.passedStep("Text redaction is added successfully");

		baseClass.stepInfo("Adding the this page redaction");
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("This page redaction has been performed");

		String getRedactedDocid = docViewRedact.activeDocId().getText();

		baseClass.stepInfo("Navigate to another document and come back to redacted document");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().Click();
		driver.waitForPageToBeReady();

		String nextDoc = docViewRedact.activeDocId().getText();

		baseClass.stepInfo("Verify that user is navigated to next document");
		softAssert.assertNotEquals(getRedactedDocid, nextDoc);

		baseClass.waitForElement(docViewRedact.previousDocViewBtn());
		docViewRedact.previousDocViewBtn().Click();
		driver.waitForPageToBeReady();
		nextDoc = docViewRedact.activeDocId().getText();
		softAssert.assertEquals(getRedactedDocid, nextDoc);

		baseClass.stepInfo("Select the rectangular redaction and Press Delete key");
		docViewRedact.verifyWhetherRedactionIsSaved(true);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DELETE);
		robot.keyRelease(KeyEvent.VK_DELETE);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Move to next page to add this page redaction");
		baseClass.waitForElement(docViewRedact.docViewNextPage());
		docViewRedact.docViewNextPage().Click();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Adding the this page redaction");
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("This page redaction has been performed");

		baseClass.stepInfo(
				"Move to previous page to verify this page redaction is still present after del key is pressed");
		baseClass.waitForElement(docViewRedact.docViewPreviousPage());
		docViewRedact.docViewPreviousPage().Click();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Navigate to another document and come back to redacted document");
		baseClass.waitForElement(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().Click();
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docViewRedact.previousDocViewBtn());
		docViewRedact.previousDocViewBtn().Click();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify still the this redaction is displayed even after  del key is pressed");
		docViewRedact.verifyWhetherRedactionIsSaved(true);

		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49371 Verify that when 'Rectangle' redaction selected to delete with
	 * 'Delete' key from keyboard should be disabled keyboard action
	 * 
	 */
	@Test(description ="RPMXCON-49371",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void verifyKeyBoardDelActionDisabledForRectangleRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SoftAssert softAssert = new SoftAssert();
		// Login As RMU
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON-49371 ");
		baseClass.stepInfo(
				"Verify that when 'Rectangle' redaction selected to delete with 'Delete' key from keyboard should be disabled keyboard action");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("Adding Text redaction");
		docViewRedact.RedactTextInDocView(1, 1, 10, 10);
		docViewRedact.selectingRectangleRedactionTag();

		baseClass.passedStep("Text redaction is added successfully");

		baseClass.stepInfo("Adding Rectangle redaction");
		docViewRedact.redactionIcon().waitAndClick(20);
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.passedStep("Rect redaction is added successfully");

		String getRedactedDocid = docViewRedact.activeDocId().getText();

		baseClass.stepInfo("Navigate to another document and come back to redacted document");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().Click();
		driver.waitForPageToBeReady();

		String nextDoc = docViewRedact.activeDocId().getText();

		baseClass.stepInfo("Verify that user is navigated to next document");
		softAssert.assertNotEquals(getRedactedDocid, nextDoc);

		baseClass.waitForElement(docViewRedact.previousDocViewBtn());
		docViewRedact.previousDocViewBtn().Click();
		driver.waitForPageToBeReady();
		nextDoc = docViewRedact.activeDocId().getText();
		softAssert.assertEquals(getRedactedDocid, nextDoc);

		baseClass.stepInfo("Select the rectangular redaction and Press Delete key");
		docViewRedact.verifyWhetherRedactionIsSaved(true);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_DELETE);
		robot.keyRelease(KeyEvent.VK_DELETE);

		baseClass.stepInfo("Adding the third redaction");
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("This page redaction has been performed");

		baseClass.stepInfo("Navigate to another document and come back to redacted document");
		baseClass.waitForElement(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().Click();
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docViewRedact.previousDocViewBtn());
		docViewRedact.previousDocViewBtn().Click();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify still the rectangle redaction is displayed even after  del key is pressed");
		docViewRedact.verifyWhetherRedactionIsSaved(true);

		softAssert.assertAll();
		loginPage.logout();
	}
	
	/**
	 * Author : Vijaya.Rani date: 03/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that user should be able to edit an applied redaction and
	 * change the redaction tag that was applied automatically. 'RPMXCON-46958'
	 * Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-46958",enabled = true, groups = { "regression" })
	public void verifyEditAndApplyRedactionTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-46958");
		baseClass.stepInfo(
				"Verify that user should be able to edit an applied redaction and change the redaction tag that was applied automatically.");
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		Actions actions = new Actions(driver.getWebDriver());
		sessionSearch.basicContentSearch(Input.randomText);
		sessionSearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(20);
		actions.moveToElement(docViewRedact.thisPageRedaction().getWebElement());
		actions.click().build().perform();
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag Saved successfully");
		loginPage.logout();

	}

	/**
	 *
	 * @Author : Brundha
	 * @Testcase id : 49988 - Verify that after deletion of the last saved
	 *           redaction, last saved redaction tag should be selected
	 *           automatically from redaction pop up
	 */
	@Test(description ="RPMXCON-49988",groups = { "regression" } )
	public void verifyRedactionTagInPopUpDropDown() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49988 from Docview/redaction Component");
		baseClass.stepInfo(
				"Verify that after deletion of the last saved redaction, last saved redaction tag should be selected automatically from redaction pop up");

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String Redactiontag2 = "SecondRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		baseClass.stepInfo("Creating a redaction tag");
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigate to doc view page");
		sessionSearch.ViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 40, 40);
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);
		driver.scrollPageToTop();
		docViewRedactions.selectDoc1();

		baseClass.waitTime(1);
		driver.scrollPageToTop();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);
		baseClass.waitTime(1);
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10,80, 80);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag2);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		redactionpage = new RedactionPage(driver);
		baseClass.stepInfo("Deleting Redaction tag");
		redactionpage.DeleteRedaction(Redactiontag2);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.selectFirstDoc().isElementAvailable(10);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 30, 30);
		docViewRedactions.rectangleRedactionTagSelect().isDisplayed();
		Select select = new Select(docViewRedactions.rectangleRedactionTagSelect().getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the value is " + option);
		if (option.equals(Redactiontag1)) {
			baseClass.passedStep("Last saved redaction tag is selected automatically from redaction pop up");
		} else {
			baseClass.failedStep("Last saved redaction tag is not  selected automatically from redaction pop up");
		}
		loginPage.logout();

	}
	
	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: NA.
	 *         ////@Testcase_Id : 47029 - Verify that pages which are included for
	 *         multi page redaction should be redacted successfully with changed
	 *         redaction tag from what is presented in pop up.
	 * @Description : Verify that pages which are included for multi page redaction
	 *              should be redacted successfully with changed redaction tag from
	 *              what is presented in pop up.
	 */
	 @Test(description ="RPMXCON-47029",groups = { "regression" })
	public void verifyMultiplePageRedactionsIncludeWithDiffRedTag() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47029 DocView Sprint 06");
		String Redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		baseClass.stepInfo(
				"#### Verify that pages which are included for multi page redaction should be redacted successfully with changed redaction tag from what is presented in pop up. ####");
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Navigate to session search");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Bulk Release");
		sessionSearch.bulkRelease(Input.securityGroup);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Navigate to session search");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addDocsMetCriteriaToActionBoard();

		DocViewRedactions redact = new DocViewRedactions(driver);

		baseClass.stepInfo("Perform exclude Multiple Page Redaction by page range");
		redact.performMultiplePageRedactionForInclude(Redactiontag1, Input.pageRange);
		loginPage.logout();
	}
	 
		/**
		 * @Author : Gopinath Created date: NA Modified date: NA Modified by: NA.
		 *         ////@Testcase_Id : 47028 - Verify that on click of Save from
		 *         'Multi-Page Redactions' pop up, all pages other than pages which are
		 *         excluded should be redacted successfully.
		 * @Description : Verify that on click of Save from 'Multi-Page Redactions' pop
		 *              up, all pages other than pages which are excluded should be
		 *              redacted successfully.
		 */
		@Test(description ="RPMXCON-47028",groups = { "regression" })
		public void verifyMultiplePageRedactionsByExcludePages() throws InterruptedException {
			baseClass = new BaseClass(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-47028 DocView Sprint 06");

			utility = new Utility(driver);
			docViewMetaDataPage = new DocViewMetaDataPage(driver);
			baseClass.stepInfo(
					"#### Verify that on click of Save from 'Multi-Page Redactions' pop up, all pages other than pages which are excluded should be redacted successfully. ####");
			loginPage = new LoginPage(driver);

			baseClass.stepInfo("Login with project administrator");
			loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
			Reporter.log("Logged in as User: " + Input.pa2userName);
			SessionSearch sessionSearch = new SessionSearch(driver);

			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();

			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.testData1);

			baseClass.stepInfo("Bulk Release");
			sessionSearch.bulkRelease(Input.securityGroup);

			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

			sessionSearch = new SessionSearch(driver);

			baseClass.stepInfo("Navigate to session search");
			sessionSearch.navigateToSessionSearchPageURL();

			baseClass.stepInfo("Basic content search");
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.addDocsMetCriteriaToActionBoard();

			DocViewRedactions redact = new DocViewRedactions(driver);

			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();

			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page range");
			redact.performMultiplePageRedactionForExclude(Input.defaultRedactionTag, Input.pageRange);

			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();

			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page number");
			redact.performMultiplePageRedactionForExclude(Input.defaultRedactionTag, Input.pageNum);

			baseClass.stepInfo("Refresh page");
			driver.Navigate().refresh();

			baseClass.stepInfo("Perform exclude Multiple Page Redaction by page number and page range");
			redact.performMultiplePageRedactionForExclude(Input.defaultRedactionTag,
					Input.pageNum + "," + Input.pageRange);
			loginPage.logout();
		}
	
}
