package sightline.docview;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression9 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	KeywordPage keywordPage;
	SavedSearch savedSearch;
	ReusableDocViewPage reusableDocView;

	String keywordsArray[] = { "test", "hi", "Than8617167" };
	String keywordsArrayPT[] = { "test" };
	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Test Case
	 *         Id: RPMXCON-52030 Description : login as RMU and impersonate as
	 *         reviewer use search term to get document and redact
	 */
	@Test(description = "RPMXCON-52030", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionasReviewer() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON 52030");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
// Impersonating as Reviewer and searching with text input		
		baseClass.impersonateRMUtoReviewer();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
// Redacting using rectangular redaction
		docViewRedact.redactRectangleUsingOffset(0, 0, 50, 100);
		baseClass.stepInfo("A rectangle redaction has been applied");
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("A rectangle redaction has been saved under Default Redaction Tag");
		if (docViewRedact.redactionIcon().getWebElement().isDisplayed()
				&& docViewRedact.redactionIcon().getWebElement().isEnabled()) {
			assertTrue(true);
			baseClass.passedStep("The Rectangular redaction has been applied and saved as Reviewer");
		} else {
			assertFalse(false);
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 *         Test Case Id: RPMXCON-50779 Description : Verify that user can
	 *         navigate through documents Get document list using search input and
	 *         view in DocView Navigate to first and last document
	 */
	@Test(description = "RPMXCON-50779", enabled = true, alwaysRun = true, groups = { "regression" })

	public void verifyDocNavigationRMU() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 50779");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.forwardNextDocBtn().Visible() && docViewRedact.forwardNextDocBtn().Enabled();
			}
		}), Input.wait30);
		docViewRedact.forwardNextDocBtn().waitAndClick(20);
		baseClass.stepInfo("Moved to next document using naviagtion option");
		docViewRedact.forwardNextDocBtn().waitAndClick(2);
		actions.moveToElement(docViewRedact.forwardToLastDoc().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Moved to last document in the list using naviagtion option");
		actions.moveToElement(docViewRedact.backwardPriviousDocBtn().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Moved to previous document using naviagtion option");
		actions.moveToElement(docViewRedact.backwardToFirstDoc().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Moved to first document in the list document using naviagtion option");
		if (docViewRedact.backwardToFirstDoc().getWebElement().isDisplayed()
				&& docViewRedact.backwardToFirstDoc().getWebElement().isEnabled()) {
			assertTrue(true);
		} else {
			assertFalse(false);
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna D D date: NA Modified date: NA Modified by: Krishna D Krishna
	 *         Test Case Id: RPMXCON-51863 Description : Verify text Remarks as RMU
	 *         through documents Get document list using search input and view in
	 *         DocView
	 */

	@Test(description = "RPMXCON-51863", enabled = true, alwaysRun = true, groups = { "regression" })

	public void verifyTextRemarks() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 51863");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.remarksIcon().Visible() && docViewRedact.remarksIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.remarksIcon().waitAndClick(25);
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(4000);
//Thread sleep added for the page to adjust resolution		
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(100, 20).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.addRemarksTextArea());
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		if (docViewRedact.deleteRemarksBtn().Displayed() && docViewRedact.deleteRemarksBtn().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The Remark has been saved by RMU");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna D D date: NA Modified date: 27/08/2021 Modified by: Krishna D
	 *         Krishna Test Case Id: RPMXCON-52232, 52230 & 52229 Description :
	 *         Verify printing redacted doc as RMU through session search Get
	 *         document list using search input and view in DocView print document
	 *         and verify bullhorn notification & background tasks
	 */
	@Test(description = "RPMXCON-52232,RPMXCON-52230,RPMXCON-52229", enabled = true, alwaysRun = true, groups = {"regression" })

	public void verifyBullhornNotifications() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());

		baseClass.stepInfo("Test case Id: RPMXCON-52232, RPMXCON-52230, RPMXCON-52229");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docViewRedact.printIcon());
		docViewRedact.printIcon().waitAndClick(30);

		baseClass.VerifySuccessMessage(
				"Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
		baseClass.stepInfo("Success message has been verified");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.bullhornIconRedColour().Visible()
						&& docViewRedact.bullhornIconRedColour().Enabled();
			}
		}), Input.wait30);
		docViewRedact.bullhornIconRedColour().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.viewAllBtn().Visible() && docViewRedact.bullhornIconRedColour().Enabled();
			}
		}), Input.wait30);
		docViewRedact.viewAllBtn().waitAndClick(15);
		actions.click().build().perform();
		Thread.sleep(4000);
// Thread sleep has been added to validate the next page url with expected
		String urlBackground = driver.getUrl();
		assertEquals(urlBackground, "https://sightlineuat.consiliotest.com/Background/BackgroundTask");
		baseClass.passedStep("Navigated to document download page");
		loginPage.logout();
	}

	/**
	 * @author Krishna D date: NA Modified date: 25/08/2021 by: Krishna D Krishna
	 *         Test Case Id: 51758 Description : Verify persitent hit panel As RMU,
	 *         go to DocView from assignments with key word associated
	 */
	@Test(description = "RPMXCON-51758", enabled = true, alwaysRun = true, groups = { "regression" })

	public void verifyPersistantHitPanal() throws Exception {

// manage assignments and click test
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51758");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		docViewRedact.manageBtn().Click();
		actions.moveToElement(docViewRedact.manageAssignments().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Successfully navigated to assignments page");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.AssignmentDocCountTgl().Visible()
						&& docViewRedact.AssignmentDocCountTgl().Enabled();
			}
		}), Input.wait30);
		docViewRedact.AssignmentDocCountTgl().waitAndClick(12);
		Thread.sleep(2000);
		actions.moveToElement(docViewRedact.testTable().getWebElement());
		actions.click().build().perform();
		baseClass.stepInfo("Successfully selected the test assignment");
//Selecting actions from assignments and clicking the persistent hit icon
		actions.moveToElement(docViewRedact.actionDropDownAssignments().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.actionDropDownAssignmentsViewInDocView().getWebElement());
		actions.click().build().perform();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.persistantHitBtn().Visible() && docViewRedact.persistantHitBtn().Enabled();
			}
		}), Input.wait30);
		docViewRedact.persistantHitBtn().waitAndClick(30);
		if (docViewRedact.persistantHitToggle().Displayed() && docViewRedact.persistantHitToggle().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The persistent hit panel is visible");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Test
	 * Case Id: RPMXCON-52220 & 52194 Description : Performing text redaction on a
	 * Doc As RMU use search term to get a pdf document and perform text redaction
	 * for 52194, Delete the performed text redaction
	 */

	@Test(description = "RPMXCON-52220,RPMXCON-52194", enabled = true, alwaysRun = true, groups = {"regression" })
	public void textRedactionAsRMU() throws Exception {
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52220, RPMXCON-52194");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.duplicateDocId);
		baseClass.stepInfo("Search for document completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		docViewRedact.redactTextUsingOffset();
		baseClass.stepInfo("Text Redaction applied");
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("Default Redaction tag selected successfully");
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag saved successfully");
		Thread.sleep(2000);
		baseClass.waitTillElemetToBeClickable(docViewRedact.redactionForwardNavigate());
		actions.moveToElement(docViewRedact.redactionForwardNavigate().getWebElement());
		actions.click();
		actions.build().perform();
		baseClass.stepInfo("Navigated to Next Redaction Successfully");
		if (docViewRedact.deleteClick().isDisplayed()) {
			actions.moveToElement(docViewRedact.deleteClick().getWebElement());
			actions.click();
			actions.build().perform();
			baseClass
					.passedStep("Text redaction has been performed by RMU user and Redaction Tag Deleted successfully");
		} else {
			baseClass.passedStep("Text redaction has been performed by RMU user");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52303 & RPMXCON-52304 Description : Varifying Redaction
	 * on Various document types Search Metadata- View in Doc View - Check
	 * Redactions
	 */

	@Test(description = "RPMXCON-52303,RPMXCON-52304", enabled = true, alwaysRun = true, groups = {"regression" })
	public void verifyRedactionStabilityinAllDocTypes() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52303, RPMXCON-52304");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicMetaDataSearch("DocFileType", null, "PDF OR TEXT OR IMAGE OR TIFF", null);
		baseClass.stepInfo("Searched different files types using meta data");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Searched docs Viewed in DocView");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Thread.sleep(5000);
		docViewRedact.docListTableItration();
		baseClass.passedStep("The redacted documents are checked while navigating from Mini Doc List");
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Test
	 * Case Id: RPMXCON-52231 Description : Printing a doc with existing redactions
	 * as RMU use search term to get a pdf document and print
	 */

	@Test(description = "RPMXCON-52231", enabled = true, alwaysRun = true, groups = { "regression" })
	public void printRedactedPdfasReviewer() throws Exception {

// Selecting Document from Session search	
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id:RPMXCON 52231");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input for pdf doc completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.forwardNextDocBtn().Visible() && docViewRedact.forwardNextDocBtn().Enabled();
			}
		}), Input.wait30);
		docViewRedact.forwardNextDocBtn().waitAndClick(20);
		baseClass.stepInfo("Moved to next document(pdf) using naviagtion option");
		Thread.sleep(2000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.printIcon().waitAndClick(20);
		try {
			baseClass.VerifySuccessMessage(
					"Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
			baseClass.stepInfo("Success message has been verified");
		} catch (Exception e) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
				}
			}), Input.wait30);
			docViewRedact.printIcon().waitAndClick(10);
			baseClass.VerifySuccessMessage(
					"Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
			baseClass.stepInfo("Success message has been verified");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.bullhornIconRedColour().Visible()
						&& docViewRedact.bullhornIconRedColour().Enabled();
			}
		}), Input.wait30);
		docViewRedact.bullhornIconRedColour().waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.firstTaskInList().Visible() && docViewRedact.firstTaskInList().Enabled();
			}
		}), Input.wait30);
		docViewRedact.viewAllBtn().waitAndClick(15);
		baseClass.passedStep("Navigated to document download page");
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52202 Description : Verifying that text redaction
	 * button is not avilable for audio docs
	 */

	@Test(description = "RPMXCON-52202", enabled = true, alwaysRun = true, groups = { "regression" })
	public void textRedactionWithAudioDocs() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52202");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.audioDocRedactionadd().Visible() && docViewRedact.audioDocRedactionadd().Enabled();
			}
		}), Input.wait30);
		docViewRedact.audioDocRedactionadd().waitAndClick(25);
		if (docViewRedact.textSelectionRedactionIcon().isDisplayed()) {
			baseClass.failedStep("The text redaction button is avilable for audio docs");
		} else

		{
			baseClass.passedStep("The text redaction button is not avilable for audio docs");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52192 & RPMXCON-52201 Description :Redacting using New
	 * Redaction tag Checking if new redaction tag is displayed automatically for
	 * next redaction
	 */
	@Test(description ="RPMXCON-52192,RPMXCON-52201",enabled = true, alwaysRun = true, groups = { "regression" })
	public void textRedactionCancleButtonVerification() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		baseClass.stepInfo("Test case Id: RPMXCON-52192, RPMXCON-52201");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.duplicateDocId);
		baseClass.stepInfo("Search for document completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		docViewRedact.redactRectangleUsingOffset(-5, 10, 100, 50);
		docViewRedact.selectingRedactionTag("Default Redaction Tag");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Displayed() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(15);
		docViewRedact.redactTextUsingOffset();
		baseClass.passedStep("The Redaction Tag selection Pop up appears");
		docViewRedact.canclingRedactionTag();
		baseClass.stepInfo("The text redaction tag selection has been canclled");
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		baseClass.passedStep("The selected text area is unselected after refreshing");
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52228 Description : Printing Redacted Doc Doc has
	 * bullet points and redaction applied at bullet points
	 */

	@Test(description = "RPMXCON-52228", enabled = true, alwaysRun = true, groups = { "regression" })
	public void printingDocWithBulletPoints() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52228");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchBulletDocId);
		baseClass.stepInfo("Search for document with bullet points completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.printIcon().Visible() && docViewRedact.printIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.printIcon().waitAndClick(25);
		baseClass.VerifySuccessMessage(
				"Your document is being printed. Once it is complete, the \"bullhorn\" icon in the upper right-hand corner will turn red, and will increment forward.");
		baseClass.stepInfo("Success message has been verified");

		baseClass.waitForElement(docViewRedact.bullhornIconRedColour());
		if (docViewRedact.bullhornIconRedColour().isDisplayed()) {
			docViewRedact.bullhornIconRedColour().waitAndClick(30);
		} else {

			docViewRedact.bullhornIcon().waitAndClick(5);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.firstTaskInList().Visible() && docViewRedact.firstTaskInList().Enabled();
			}
		}), Input.wait30);
		docViewRedact.viewAllBtn().waitAndClick(15);
		baseClass.passedStep("Navigated to document download page");
		String childWindowHandle = driver.getWebDriver().getWindowHandle();
		driver.getWebDriver().switchTo().window(childWindowHandle);
		String urlDocPrint = driver.getUrl();
		baseClass.stepInfo(urlDocPrint);
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52188
	 * Verifying persistent hit for audio docs as RMU DocView- sprint 3
	 */

	@Test(description = "RPMXCON-52188", enabled = true, alwaysRun = true, groups = { "regression" })
	public void persistentHitCheckAudioDocs() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52188");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.quickbatch();
		baseClass.stepInfo("Assignment created using quickbatch");
		assignmentspage.createNewquickBatchWithoutReviewer(assignmentName, Input.codingFormName);
		assignmentspage.ViewinDocviewFromAssignments(assignmentName);
		baseClass.stepInfo("Assignment created in quick batch and viewed in DocView");
		docViewRedact.checkingPersistentHitPanelAudio();
		if (docViewRedact.persistantHitRightNavigate().Displayed()
				&& docViewRedact.persistantHitRightNavigate().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The persistent hit panel is visible for audio documents");
		} else {
			assertTrue(false);
		}
		assignmentspage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify after impersonation all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from basic
	 * search.'RPMXCON-51326' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51326",enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51326");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from basic search");
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51350
	 * Verifying persistent hit for audio docs as RMU when navigated from child
	 * window DocView- sprint 3
	 */

	@Test(description = "RPMXCON-51350", enabled = true, alwaysRun = true, groups = { "regression" })
	public void persistentHitCheckAudioDocsChildWindow() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51350");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanelAudio();
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.selectingDocFromMiniDocListChildWindow();
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51626
	 * Verifying Remarks and colour highliting as RMU DocView- sprint 3
	 */
	@Test(description = "RPMXCON-51626", enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifySavedTextRemark() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51626 ");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.docIdRemarks);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(4000);
//Thread sleep added for the page to adjust resolution		
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(30, 20).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		baseClass.passedStep("Created Remarks as RMU");
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		driver.waitForPageToBeReady();
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		docViewMetaDataPage.selectSecurityGroup("Default Security Group");
		sessionsearch.basicContentSearch(Input.docIdRemarks);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.deleteRemarksBtn().getWebElement()));
		docViewRedact.deleteRemarksBtn().waitAndClick(5);
		docViewRedact.confirmDeleteRemarksBtn().waitAndClick(5);
		baseClass.passedStep("Remarks have been deleted");
		driver.waitForPageToBeReady();
		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex2 = Color.fromString(color2).asHex();
			System.out.println(hex2);
			if (hex1.equalsIgnoreCase(hex2)) {
				baseClass.failedStep("The color for the Highlighted text is same");
			}
		} else {
			baseClass.passedStep("The Highlighted text is not present after the remark has been Deleted");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51764
	 * Verifying persistent hit for non audio docs as RMU and Rev from assignments
	 * DocView- sprint 3
	 */
	@Test(description = "RPMXCON-51764", enabled = true, alwaysRun = true, groups = { "regression" })
	public void persistentHitCheckNonAudioAsRev() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51764");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.toggleEnableAnalyticsPanel();
		assignmentspage.add2ReviewerAndDistribute();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		docViewRedact.checkingPersistentHitPanel();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		docViewRedact.checkingPersistentHitPanel();
		assignmentspage.deleteAssgnmntUsingPagination(assignmentName);		
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52191
	 * Verifying if text redaction option is present under RMU and Rev DocView-
	 * sprint 4
	 */
	@Test(description = "RPMXCON-52191", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyTextRedactionOption() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52191");
// Verifying As RMU
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.textSelectionRedactionIcon());
		if (docViewRedact.textSelectionRedactionIcon().Displayed()
				&& docViewRedact.textSelectionRedactionIcon().Displayed()) {
			assertTrue(true);
			baseClass.passedStep("The text Redaction Option is present in DocView for RMU user");
		} else {
			assertTrue(false);
		}

		loginPage.logout();

// Verifying as Rev		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.clickingRedactionIcon();
		if (docViewRedact.textSelectionRedactionIcon().Displayed()
				&& docViewRedact.textSelectionRedactionIcon().Displayed()) {
			assertTrue(true);
			baseClass.passedStep("The text Redaction Option is present in DocView for Rev user");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51655
	 * Verifying doc is highlighted using this page and highlight is deleted
	 * successfully while refreshing page DocView- sprint 4
	 */
	@Test(description = "RPMXCON-51655", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyHighlitingInDocView() throws Exception {
		Robot robot = new Robot();
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51655");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.clickingHighlitingIcon();
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		driver.waitForPageToBeReady();
		Thread.sleep(3000);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).click();
		actions.build().perform();
		if (docViewRedact.highliteDeleteBtn().Displayed() && docViewRedact.highliteDeleteBtn().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The page has been highlited and highlite has been saved after the page refresh");
		} else {
			assertTrue(false);
		}
		docViewRedact.highliteDeleteBtn().Click();
		baseClass.passedStep("The highlite has been deleted successfully");
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		driver.waitForPageToBeReady();
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).click();
		actions.build().perform();
		if (docViewRedact.highliteDeleteBtn().isElementAvailable(5) == true) {
			docViewRedact.highliteDeleteBtn().Click();
			baseClass.failedStep("the highlite has not been deleted after refresh");
		} else {
			baseClass.passedStep("The highlite is not present on the page after deleting and refreshing");

		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51007 Verify thumbnails icon and check for doc view click
	 */

	@Test(description = "RPMXCON-51007", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyThumbnailsPageNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51007");
		baseClass.stepInfo(
				"Verify on click of any of the thumbnail respective page should be displayed in doc view panel");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.clickingThumbnailIcon();
		if (docViewRedact.thumbNailsPanel().isElementPresent() == true) {
			baseClass.passedStep("The thumbnails panel is clicked and menu is visible");
		} else {
			baseClass.failedStep("The thumbnail Panel menu is NOT displayed");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.clickPageNumber(3));
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.clickPageNumber(3).Visible() && docViewRedact.clickPageNumber(3).Enabled();
			}
		}), Input.wait30);
		docViewRedact.clickPageNumber(3).waitAndClick(10);
		String text = docViewRedact.clickPageNumber(3).getText();
		System.out.println(text);
		if (text.equalsIgnoreCase("3")) {
			baseClass.passedStep("case passed");
		} else {
			baseClass.failedStep("failed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 26/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation when Highlighting, Persistent Hit
	 * panel, Reviewer Remarks panel, Redactios menu is selected from doc view and
	 * views other document from analytics panel previously selected panels/menus
	 * should remain.'RPMXCON-51356' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51356", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationAllMenusInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51356");
		baseClass.stepInfo(
				"Verify after impersonation when Highlighting, Persistent Hit panel, Reviewer Remarks panel, Redactios menu is selected from doc view and views other document from analytics panel previously selected panels/menus should remain.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");
		docView.performDisplayIconReviewerHighlightingMenus();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify that the user can click on X at any state and bring it
	 * back to the original search icon (only) state'RPMXCON-51560' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51560", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyClickXAtAnyStateAndBringItBackToOriginalSearchIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51560");
		baseClass.stepInfo(
				"Verify that the user can click on X at any state and bring it back to the original search icon (only) state");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		docViewRedact.performClickSearchIconAndX();

		if (docViewRedact.getSearchIcon().isElementPresent() == true) {
			baseClass.passedStep("Textbox and the magnifying glass, X is back to magnifying glass");
		} else {
			baseClass.failedStep("The Search Icon is not Displayed");

		}

		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		docViewRedact.performClickSearchIconAndX();

		if (docViewRedact.getSearchIcon().isElementPresent() == true) {
			baseClass.passedStep("Textbox and the magnifying glass, X is back to magnifying glass");
		} else {
			baseClass.failedStep("The Search Icon is not Displayed");

		}
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assistant with " + Input.pa1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		docViewRedact.performClickSearchIconAndX();

		if (docViewRedact.getSearchIcon().isElementPresent() == true) {
			baseClass.passedStep("Textbox and the magnifying glass, X is back to magnifying glass");
		} else {
			baseClass.failedStep("The Search Icon is not Displayed");

		}
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 1/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document on selecting code same as this action. 'RPMXCON-51274' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51274", enabled = true, groups = { "regression" })
	public void verifyAssignmentProgressBarCodeSameAsAction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51274");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document on selecting code same as this action.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch("test");
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		// perform MiniDocList CodeSame As
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);

		// perform MiniDocList CodeSame As
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 10/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that once document is marked as 'Un-Completed',
	 * assignment progress bar is updated.'RPMXCON-51030' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51030", enabled = true, groups = { "regression" })
	public void verifyOnceDocumentIsMarkedAsUnCompleteUpdateProgressBar() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51030");
		baseClass
				.stepInfo("To verify that once document is marked as 'Un-Completed'assignment progress bar is updated");
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		driver.waitForPageToBeReady();
		System.out.println(assname);
		loginPage.logout();
		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentsPage.SelectAssignmentByReviewer(assname);
		docViewRedact.performCompleteToDocs();
		docViewRedact.getHomeDashBoard();

		docViewRedact.selectAssignmentfromDashborad(assname);
		docViewRedact.performGeerIcon();
		docViewRedact.performUnCompleteToDocs();
		docViewRedact.getHomeDashBoard();
		baseClass.stepInfo("The Progress bar is Changed Successfully");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that when user in on Images tab and completes the document then
	 * should be on Images tab for next navigated document.'RPMXCON-51914' Sprint :
	 * 9
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51914", enabled = true, groups = { "regression" })
	public void verifyImageTabOnCompleteDocsShouldNavigateToNextDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51914");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and completes the document then should be on Images tab for next navigated document");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 2: Go to doc view in context of an assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 3: Click the Images tab of the document and complete the document");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId1 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId1);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId2 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId2);

		softAssertion.assertNotEquals(docId1, docId2);
		softAssertion.assertAll();
		baseClass.passedStep("The next document from mini doc list is loaded successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		baseClass.stepInfo("Step 3: Click the Images tab of the document and complete the document");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId3 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId3);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId4 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId4);

		softAssertion.assertNotEquals(docId3, docId4);
		softAssertion.assertAll();
		baseClass.passedStep("The next document from mini doc list is loaded successfully");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that when user in on Images tab and completes the document on
	 * applying stamp then should be on Images tab for next navigated
	 * document.'RPMXCON-51915' Sprint : 9
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51915", enabled = true, groups = { "regression" })
	public void verifyImageTabOnCompleteDocsAndApplyCodingStampShouldNavigateToNextDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51915");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and completes the document on applying stamp then should be on Images tab for next navigated document");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 2: Go to doc view in context of an assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 3: Click the Images tab of the document and complete the document");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId1 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId1);

		ReusableDocViewPage reusableDocViewPage = new ReusableDocViewPage(driver);
		reusableDocViewPage.stampColourSelection(assname, colour);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId2 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId2);

		softAssertion.assertNotEquals(docId1, docId2);
		softAssertion.assertAll();
		baseClass.passedStep("The next document from mini doc list is loaded successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		baseClass.stepInfo("Step 3: Click the Images tab of the document and complete the document");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId3 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId3);

		reusableDocViewPage.stampColourSelection(assname, colour);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId4 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId4);

		softAssertion.assertNotEquals(docId3, docId4);
		softAssertion.assertAll();
		baseClass.passedStep("The next document from mini doc list is loaded successfully");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that when user in on Images tab and completes the document same as
	 * last then should be on Images tab for next navigated document.'RPMXCON-51916'
	 * Sprint : 9
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51916", enabled = true, groups = { "regression" })
	public void verifyImageTabOnCompleteDocsAndCodeSameAsLastShouldNavigateToNextDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51916");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and completes the document same as last then should be on Images tab for next navigated document");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 2: Go to doc view in context of an assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 3: Click the Images tab of the document and complete the document");

		driver.waitForPageToBeReady();
		baseClass.waitTime(1);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId1 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId1);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getLastCodeIconWhitePencil());
		docViewPage.getLastCodeIconWhitePencil().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId2 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId2);

		softAssertion.assertNotEquals(docId1, docId2);
		softAssertion.assertAll();
		baseClass.passedStep("The next document from mini doc list is loaded successfully");

		// logout
		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		baseClass.stepInfo("Step 3: Click the Images tab of the document and complete the document");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId3 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId3);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getLastCodeIconWhitePencil());
		docViewPage.getLastCodeIconWhitePencil().waitAndClick(5);

		driver.waitForPageToBeReady();
		String docId4 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId4);

		softAssertion.assertNotEquals(docId3, docId4);
		softAssertion.assertAll();
		baseClass.passedStep("The next document from mini doc list is loaded successfully");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that all relevant hits should be displayed on persistent hits panel
	 * when navigated to doc view with saved search and keyword
	 * highlighting.'RPMXCON-48783' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48783", enabled = true, groups = { "regression" })
	public void verifyAllRelevantHitTermsOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-48783");
		baseClass.stepInfo(
				"Verify that all relevant hits should be displayed on persistent hits panel when navigated to doc view with saved search and keyword highlighting");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 2: Keywords should be added  Documents searched with the terms should be saved");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");

		// Saved Search to DocView
		baseClass.stepInfo("Step 3: Select the saved search and action to go to doc view");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 4: Click the eye icon to open the persistent hit panel");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getPersistantHitEyeIcon());
		docViewPage.getPersistantHitEyeIcon().waitAndClick(5);

		driver.waitForPageToBeReady();
		softAssertion.assertTrue(docViewPage.getPersistentPanel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Persistent hits panel is opened and all search hit terms saved for the search and keywords are displayed on the panel with the count");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that when user in on Images tab and view document from analytics
	 * panel then should be on Images tab of the viewed document.'RPMXCON-51917'
	 * Sprint : 9
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-51917", enabled = true, groups = { "regression" })
	public void verifyImageTabOnViewDocsFromAnalyticsPanel() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51917");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and view document from analytics panel then should be on Images tab of the viewed document");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo(
				"Step 2: Search for documents and go to doc view OR Go to doc view in context of an assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo("Step 3:Click the Images tab of the document");

		driver.waitForPageToBeReady();
		baseClass.waitTime(1);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		docViewPage.selectDocsFromThreadMapAndViewInDocView(2);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep("Images tab is load with respective documents Image/production doc successfully");

		} else {
			baseClass.failedStep("Image Tab is not loaded with respective docs");
		}

		driver.waitForPageToBeReady();
		docViewPage.selectDocsFromThreadMapAndViewInDocView(3);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep("Images tab is load with respective documents Image/production doc successfully");

		} else {
			baseClass.failedStep("Image Tab is not loaded with respective docs");
		}

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Step 2: Search for documents and go to doc view OR Go to doc view in context of an assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo("Step 3:Click the Images tab of the document");

		driver.waitForPageToBeReady();
		baseClass.waitTime(1);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		driver.waitForPageToBeReady();
		docViewPage.selectDocsFromThreadMapAndViewInDocView(2);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep("Images tab is load with respective documents Image/production doc successfully");

		} else {
			baseClass.failedStep("Image Tab is not loaded with respective docs");
		}

		driver.waitForPageToBeReady();
		docViewPage.selectDocsFromThreadMapAndViewInDocView(3);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep("Images tab is load with respective documents Image/production doc successfully");

		} else {
			baseClass.failedStep("Image Tab is not loaded with respective docs");
		}
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that completed icon should not be displayed on thread map outside of
	 * an assignment which are completed from assignment.'RPMXCON-51452' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51452",enabled = true, groups = { "regression" })
	public void verifyCompleteIconOnThreadMapOutsideOfAnAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51452");
		baseClass.stepInfo(
				"Verify that completed icon should not be displayed on thread map outside of an assignment which are completed from assignment");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 2: Go to doc view in context of an assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);

		baseClass.stepInfo("Step 2: Complete the document which are present on thread map tab");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		String docId1 = docViewPage.getDocView_CurrentDocId().getText();
		System.out.println(docId1);

		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(5);
		docViewPage.verifyCheckMark();

		sessionSearch.basicSearchWithMetaDataQuery(docId1);
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		sessionSearch.ViewInDocView();

		// verify completed checkmark should not display
		docViewPage.verifyUncompleteCheckMarkForThreadMapTabDocs();
		baseClass.passedStep(
				"Complete icon is not displayed for the documents on thread map which are completed in context of an assignment");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 10/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that the user can enter text (including long text) and search for the
	 * text.'RPMXCON-51559' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51559",enabled = true, groups = { "regression" })
	public void verifyUserCanEnterTextAndSearchText() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51559");
		baseClass.stepInfo("Verify that the user can enter text (including long text) and search for the text");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"Step 2: Search for non-audio documents, drag the result to shopping card and select action as 'View in DocView'");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo("Step 3: Click on the magnifying icon from default view of doc view");

		driver.waitForPageToBeReady();
		baseClass.waitTime(1);
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		docViewRedact.getMagnifyingIcon().waitAndClick(5);

		baseClass.stepInfo("Step 3: Verify when user enter the text to search");
		baseClass.waitForElement(docViewRedact.getInputSearchBox());
		docViewRedact.getInputSearchBox().Click();
		docViewRedact.getInputSearchBox().SendKeys("do");
		docViewRedact.getInputSearchBox().Enter();

		baseClass.waitTime(2);
		String hitCount = docViewPage.getDocView_SearchTextBox_HitCount().getText();
		System.out.println("The hit count is:" + hitCount);

		if (docViewPage.getDocView_SearchTextBox_HitCount().isDisplayed()) {
			baseClass.passedStep("The user can view and enter the longer text successfully");

		} else {
			baseClass.failedStep("The user is not able to view and enter the longer text");
		}

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Step 2: Search for non-audio documents, drag the result to shopping card and select action as 'View in DocView'");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo("Step 3: Click on the magnifying icon from default view of doc view");

		driver.waitForPageToBeReady();
		baseClass.waitTime(1);
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		docViewRedact.getMagnifyingIcon().waitAndClick(5);

		baseClass.stepInfo("Step 3: Verify when user enter the text to search");
		baseClass.waitForElement(docViewRedact.getInputSearchBox());
		docViewRedact.getInputSearchBox().Click();
		docViewRedact.getInputSearchBox().SendKeys("do");
		docViewRedact.getInputSearchBox().Enter();

		baseClass.waitTime(2);
		hitCount = docViewPage.getDocView_SearchTextBox_HitCount().getText();
		System.out.println("The hit count is:" + hitCount);

		if (docViewPage.getDocView_SearchTextBox_HitCount().isDisplayed()) {
			baseClass.passedStep("The user can view and enter the longer text successfully");

		} else {
			baseClass.failedStep("The user is not able to view and enter the longer text");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"Step 2: Search for non-audio documents, drag the result to shopping card and select action as 'View in DocView'");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewThreadedDocsInDocViews();

		baseClass.stepInfo("Step 3: Click on the magnifying icon from default view of doc view");

		driver.waitForPageToBeReady();
		baseClass.waitTime(1);
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		docViewRedact.getMagnifyingIcon().waitAndClick(5);

		baseClass.stepInfo("Step 3: Verify when user enter the text to search");
		baseClass.waitForElement(docViewRedact.getInputSearchBox());
		docViewRedact.getInputSearchBox().Click();
		docViewRedact.getInputSearchBox().SendKeys("do");
		docViewRedact.getInputSearchBox().Enter();

		baseClass.waitTime(2);
		hitCount = docViewPage.getDocView_SearchTextBox_HitCount().getText();
		System.out.println("The hit count is:" + hitCount);

		if (docViewPage.getDocView_SearchTextBox_HitCount().isDisplayed()) {
			baseClass.passedStep("The user can view and enter the longer text successfully");

		} else {
			baseClass.failedStep("The user is not able to view and enter the longer text");
		}
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify all hits of the document should be highlighted without clicking the
	 * eye icon when user redirects to doc view from saved search.'RPMXCON-51325'
	 * Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51325",enabled = true, groups = { "regression" })
	public void verifyHitsOfDocsHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51325");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from saved search");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify all hits of the document should be highlighted without clicking the
	 * eye icon when user redirects to doc view from basic search.'RPMXCON-51323'
	 * Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51323",enabled = true, groups = { "regression" })
	public void verifyHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51323");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from basic search");
		sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);

		if (docViewRedact.get_textHighlightedColor().isDisplayed()) {
			baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		} else {
			baseClass.failedStep("Search term hits is not highlighted in the document without clicking the eye icon");
		}
		loginPage.logout();

	}



	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when text redaction added for different
	 *         file types (e.g. XLS, XLSX, CSV, etc...) then redaction should not be
	 *         shifted when visiting these documents : RPMXCON-52302 Sprint : 11
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-52302",enabled = true, groups = { "regression" })
	public void verifyTextRedactionPosition() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList;
		String firnstDocname, secondDocname, docName, xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		baseClass.stepInfo("Test case Id: RPMXCON-52302 Sprint 11");
		baseClass.stepInfo(
				"Verify that when text redaction added for different file types (e.g. XLS, XLSX, CSV, etc...) then redaction should not be shifted when visiting these documents");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Check for this page highlighted and remove
		docViewRedact.removeThisPageHighlight();

		// do Rectangle Redact With XY Points
		xyMap = docViewRedact.doTextRedactWithXYPoints();
		xAxis = xyMap.get("xAxis");
		yAxis = xyMap.get("yAxis");

		// Switch to a different document
		driver.waitForPageToBeReady();
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Verify Position Retained
		docViewRedact.verifyTextRedactionPositionRetained(xAxis, yAxis, true);
		loginPage.logout();

	}

	@Test(description ="RPMXCON-50773",enabled = true, groups = { "regression" })

	public void VerifyRMUToDocView() throws Exception {
		String expectedURL = Input.url + "DocumentViewer/DocView";
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-50773");
		baseClass.stepInfo("To verify RMU can view the Doc View page from Assignment.");	

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		assignmentsPage.assignmentCreation(assignmentName1, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.stepInfo(assignmentName1 + "  Assignment Created and distributed ");
		// select the Assignment from Manage Assignment
		baseClass.stepInfo("Selecting the assignment from Manage Assignments Page. ");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentsPage.viewSelectedAssgnUsingPagination(assignmentName1);
		assignmentsPage.Checkclickedstatus(assignmentName1);
		assignmentsPage.assgnViewInAllDocView();
		System.out.println();
		driver.waitForPageToBeReady();
		if (driver.getUrl().equals(expectedURL)) {
			baseClass.passedStep("Application  redirected to the doc view page which is as expected");
		} else {
			baseClass.failedStep("Application not redirected to the doc view page ");
		}
		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();
		// Select the Assignment from dashboard
		baseClass.stepInfo("Selecting the assignment from dashboard after impersonating as Reviewer from RMu user. ");
		assignmentsPage.SelectAssignmentByReviewer(assignmentName1);
		driver.waitForPageToBeReady();
		if (driver.getUrl().equals(expectedURL)) {
			baseClass.passedStep("Application  redirected to the doc view page which is as expected");
		} else {
			baseClass.failedStep("Application not redirected to the doc view page ");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51662",enabled = true, groups = { "regression" })
	public void verifyDocCount_ABM() throws Exception {
		String SaveSaerchName = "ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-51662");
		baseClass.stepInfo("Validate document count on DocView from ABM report");

		// Basic Search and Bulk Assign
		sessionsearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Search for text input completed");
		String Hits = sessionsearch.verifyPureHitsCount();
		sessionsearch.saveSearch(SaveSaerchName);
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		assignmentsPage.assignmentCreation(assignmentName1, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.stepInfo(assignmentName1 + "  Assignment Created and distributed ");
		ABMReportPage AbmReportPage = new ABMReportPage(driver);
		String DocCount_ABM = AbmReportPage.generateABM_Report(SaveSaerchName, assignmentName1, true);
		System.out.println(DocCount_ABM);
		AbmReportPage.ViewInDocView();
		driver.waitForPageToBeReady();
		DocViewPage dc = new DocViewPage(driver);
		String ActualCount = dc.verifyDocCountDisplayed_DocView();
		System.out.println(ActualCount);
		softAssertion.assertEquals(Hits, ActualCount);
		assignmentsPage.deleteAssgnmntUsingPagination(assignmentName1);
		softAssertion.assertAll();
		baseClass.passedStep("Document count  matched with the filtered documents on DocView screen.");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 12/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify after impersonation all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from advance
	 * search.'RPMXCON-51327' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51327",enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIconFromAdvanceSearch()
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51327");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from advance search");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login Reviewer
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to PA");
		baseClass.impersonateSAtoPA();

		baseClass.stepInfo("Step 2: Search the documents with search term from basic search and go to doc view");
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Basic Search is done and navigated to docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify after impersonation all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from saved
	 * search.'RPMXCON-51328' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51328",enabled = true, groups = { "regression" })
	public void verifyAfterImpersonateHitsOfDocsHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51328");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from saved search");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to PA");
		baseClass.impersonateSAtoPA();

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.sa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic/advance search and save the search result and go to doc view");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocView(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the Docview successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify all hits of the document should be highlighted without clicking the
	 * eye icon when user redirects to doc view from Basic Search > doc
	 * list.'RPMXCON-51329' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51329",enabled = true, groups = { "regression" })
	public void verifyHitsOfDocsHighlightedWithoutClickingEyeIconBasicSearchDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51329");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from saved search");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocListPage docListPage = new DocListPage(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic search and go to doc list  Go to doc view from doc list");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("is");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		baseClass.selectproject();
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("is");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		driver.waitForPageToBeReady();
		savedSearch.savedSearchToDocList(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the DocList successfully");
		driver.waitForPageToBeReady();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("is");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic search and go to doc list  Go to doc view from doc list");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		baseClass.selectproject();
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocList(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the DocList successfully");
		driver.waitForPageToBeReady();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("test");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		loginPage.logout();

		// login PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.pa1userName + "");

		baseClass.stepInfo(
				"Step 2: Search the documents with search term from basic search and go to doc list  Go to doc view from doc list");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("is");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		baseClass.selectproject();
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("is");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");

		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);
		baseClass.stepInfo("Basic Search is done and query saved successfully");
		savedSearch.savedSearchToDocList(saveName);
		baseClass.stepInfo("Saved query is selected and viewed in the DocList successfully");
		driver.waitForPageToBeReady();
		docListPage.selectingAllDocuments();
		docListPage.docListToDocView();
		baseClass.stepInfo("Basic Search is done and navigated to DocView successfully");

		baseClass.stepInfo("Step 3: Verify the highlighting from the document without clicking the eye icon");
		driver.waitForPageToBeReady();
		docView.getPersistentHitWithoutClickingEyeIcon("is");
		baseClass.passedStep("Search term hits is highlighted in the document without clicking the eye icon");
		loginPage.logout();

	}

	/**
	 * Author : Mohan date: 27/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify Search Term highlighting is working for Searchable PDF (with Mapped
	 * dataset having RequiredPDFGenartion is TRUE)'RPMXCON-51981'
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51981",enabled = true, groups = { "regression" })
	public void verifySearchTermHighlightingWorkingForSearchablePDF() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51981");
		baseClass.stepInfo(
				"Verify Search Term highlighting is working for Searchable PDF (with Mapped dataset having RequiredPDFGenartion is TRUE)");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 3: Go to Basic/Advanced Search   Search by term   Go to Doc View ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4: Click on the eye icon to see the persistent hits panel");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		softAssertion.assertTrue(docView.getPersistentPanel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("All search hit terms is displayed on the panel successfully");

		loginPage.logout();

		// login As PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2: Go to Basic/Advanced Search   Search by term   Go to Doc View ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 3: Click on the eye icon to see the persistent hits panel");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		softAssertion.assertTrue(docView.getPersistentPanel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("All search hit terms is displayed on the panel successfully");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 2: Go to Basic/Advanced Search   Search by term   Go to Doc View ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 3: Click on the eye icon to see the persistent hits panel");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		softAssertion.assertTrue(docView.getPersistentPanel().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("All search hit terms is displayed on the panel successfully");
		loginPage.logout();

	}

	/**
	 * @author Sakthivel TestCase Id:51874 Verify that Action > Remove Code Same As
	 *         works fine when all records in the reviewers batch are in an
	 *         Uncompleted state, and the user selects only some/select records
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51874",alwaysRun = true, groups = { "regression" })
	public void verifyCodeSameAsMiniDocListAndChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51874");
		String AssignStamp = Input.randomText + Utility.dynamicNameAppender();
		baseClass.stepInfo(
				"Verify that Action > Remove Code Same As works fine when all records in the reviewers batch are in an Uncompleted state, and the user selects only some/select records");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// searching document for assignmnet creation
		baseClass.stepInfo("bascic contant search");
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("performing bulk assign");
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Create assignment WIth allow user to save with out complete option");

		// create new assignment with allow user to save without complete
		assignmentPage.createAssignmentWithAllowUserToSave(AssignStamp, Input.codingFormName);
		baseClass.stepInfo("editing assignment");
		assignmentPage.editAssignment(AssignStamp);
		baseClass.stepInfo("Reviewers added and distributed to Reviewer");
		assignmentPage.assignmentDistributingToReviewer();
		loginPage.logout();
		baseClass.stepInfo("Successfully logout RUM '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment
		baseClass.stepInfo("select the assignment and view in docview");
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);

		// verify CodeSameAs icon is displayed in selected doc
		baseClass.stepInfo("performing Code sameas for min doc list documents");
		docView.selectDocsFromMiniDocsAndCodeSameAs();
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSame();
		driver.waitForPageToBeReady();
		softAssert.assertFalse(docView.geDocView_MiniList_CodeSameAsIcon().isDisplayed());
		if (docView.geDocView_MiniList_CodeSameAsIcon().isElementAvailable(1)) {
			baseClass.failedStep("CodeSameAs icon is displayed for the selected docs ");
		} else {
			baseClass.passedStep("CodeSameAs icon is not displayed for the selected docs");
		}

		baseClass.stepInfo("save the configuration");
		docView.saveConfigFromChildWindow();
		docView.popOutMiniDocList();

		// verify CodeSameAs icon is displayed in selected doc in childWindow
		driver.waitForPageToBeReady();
		docView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 2; i++) {
			baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(i));
			docView.getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
		}
		docView.clickCodeSameAs();
		docView.selectDocsFromMiniDocsAndRemoveCodeAsSameInChildWindow();
		docView.closeWindow(1);
		softAssert.assertAll();
	}

	/**
	 * Author : Mohan date: 28/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify user can see the associated tiff images on clicking of the images
	 * tab'RPMXCON-51105'
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51105",enabled = true, groups = { "regression" })
	public void verifyAssociatedTiffImage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51105");
		baseClass.stepInfo("Verify user can see the associated tiff images on clicking of the images tab");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		String dropDownValue = "DocFileExtension";
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 2:  Go to doc view in context of an assignment/outside of an assignment ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.tiffSearchQuery, dropDownValue);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 3:  Click Images tab from doc view");
		baseClass.waitForElement(docView.getDocView_Select_MiniDocList_Docs(2));
		docView.getDocView_Select_MiniDocList_Docs(2).waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_ImagesTab());
		docView.getDocView_ImagesTab().waitAndClick(5);

		baseClass.waitForElement(docView.getDocView_Production_Navigation());
		docView.getDocView_Production_Navigation().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_Production_Image().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Associated images is displayed on click of Images tab.Pagination is loaded for the multiple images");

		loginPage.logout();

		// Login as PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2:  Go to doc view in context of an assignment/outside of an assignment ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.tiffSearchQuery, dropDownValue);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 3:  Click Images tab from doc view");
		baseClass.waitForElement(docView.getDocView_Select_MiniDocList_Docs(2));
		docView.getDocView_Select_MiniDocList_Docs(2).waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_ImagesTab());
		docView.getDocView_ImagesTab().waitAndClick(5);

		baseClass.waitForElement(docView.getDocView_Production_Navigation());
		docView.getDocView_Production_Navigation().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_Production_Image().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Associated images is displayed on click of Images tab.Pagination is loaded for the multiple images");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 2:  Go to doc view in context of an assignment/outside of an assignment ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.tiffSearchQuery, dropDownValue);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 3:  Click Images tab from doc view");
		baseClass.waitForElement(docView.getDocView_Select_MiniDocList_Docs(2));
		docView.getDocView_Select_MiniDocList_Docs(2).waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_ImagesTab());
		docView.getDocView_ImagesTab().waitAndClick(5);

		baseClass.waitForElement(docView.getDocView_Production_Navigation());
		docView.getDocView_Production_Navigation().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_Production_Image().isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Associated images is displayed on click of Images tab.Pagination is loaded for the multiple images");
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 02/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document same as last prior document should be completed by clicking the
	 * complete button. 'RPMXCON-51273' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51273",enabled = true, groups = { "regression" })
	public void verifyAssignmentProgressBarSameAsLastBtnAndCopmlete() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51273");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document same as last prior document should be completed by clicking the complete button.");

		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docTextbox = "assignment click";

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.waitForElement(docView.getDocument_CommentsTextBox());
		docView.getDocument_CommentsTextBox().SendKeys(docTextbox);
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getCompleteDocBtn());
		docView.getCompleteDocBtn().waitAndClick(20);
		baseClass.VerifySuccessMessage("Document completed successfully");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_MiniDoc_Selectdoc(3));
		docViewRedact.getDocView_MiniDoc_Selectdoc(3).waitAndClick(5);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.getHomeDashBoard().waitAndClick(10);

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");
			softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		softAssertion.assertAll();
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.waitForElement(docView.getDocument_CommentsTextBox());
		docView.getDocument_CommentsTextBox().SendKeys(docTextbox);
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getCompleteDocBtn());
		docView.getCompleteDocBtn().waitAndClick(20);
		baseClass.VerifySuccessMessage("Document completed successfully");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_MiniDoc_Selectdoc(3));
		docViewRedact.getDocView_MiniDoc_Selectdoc(3).waitAndClick(5);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.getHomeDashBoard().waitAndClick(10);
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

	}

	/**
	 * Author : Aathith date: 04/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that large Excels are loading properly without any issues
	 * in DocView.. ' RPMXCON-52203' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 * 
	 *                      testcase hint : used project
	 *                      :AutomationAdditionalDataProject , Runs On : PT env
	 */
	@Test(description ="RPMXCON-52203",enabled = true, groups = { "regression" })
	public void verifyLargeExcelInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52203");
		baseClass.stepInfo("Verify that large Excels are loading properly without any issues in DocView.");

		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		sessionSearch.MetaDataSearchInAdvancedSearch("DocID", "ID00000006");
		baseClass.stepInfo("'.xls' large file is searched in section search");

		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Document is viewed in Doc view action performed");

		driver.waitForPageToBeReady();
		boolean flag = ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState")
				.equals("complete");
		if (flag) {
			softAssertion.assertTrue(flag);
			baseClass.passedStep("Large Excels should load properly without any issues in DocView.");
		} else {
			baseClass.failedStep("verification failed");
		}
		baseClass.passedStep("RMU user can able to view Docview large excel file without any issue.");

		loginPage.logout();

		// Login as a rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("Logged in as Rev: " + Input.rev1userName);

		sessionSearch.MetaDataSearchInAdvancedSearch("DocID", "ID00000006");
		baseClass.stepInfo("'.xls' large file is searched in section search");

		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Document is viewed in Doc view action performed");

		driver.waitForPageToBeReady();
		boolean flag1 = ((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState")
				.equals("complete");
		if (flag1) {
			softAssertion.assertTrue(flag1);
			baseClass.passedStep("Large Excels should load properly without any issues in DocView.");
		} else {
			baseClass.failedStep("verification failed");
		}
		baseClass.passedStep("REV user can able to view Docview large excel file without any issue.");

		loginPage.logout();

		baseClass.passedStep("Verified that large Excels are loading properly without any issues in DocView.");
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51568 Verify when user navigates to other document while viewing the
	 * search hits and comes back to same document
	 */
	@Test(description ="RPMXCON-51568",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyTextSearchAfterUserNavigatesToAnotherDoc() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docViewPage = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51568");
		baseClass.stepInfo(
				"Verify when user navigates to other document while viewing the search hits and comes back to same document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);

		baseClass.stepInfo("Navigate to another document");
		docViewPage.selectDocIdInMiniDocList(Input.searchDocId);

		baseClass.stepInfo(
				"Verify whether search is cleared,Magnifying icon is displayed when navigates to other doc and come back to same doc where the user did search");
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		softAssert.assertEquals(docViewRedact.getMagnifyingIcon().Displayed().booleanValue(), true);
		Element element = docViewRedact.getHitIcon();
		try {
			if (!element.Stale()) {
				baseClass.failedStep("Search is not cleared");
			} else {
				baseClass.passedStep("Search is cleared successfully");
			}
		} catch (Exception e) {
			baseClass.passedStep("Search is cleared successfully");

		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		baseClass.stepInfo("Navigate to another document");
		docViewPage.selectDocIdInMiniDocList(Input.searchDocId);
		baseClass.stepInfo(
				"Verify whether search is cleared,Magnifying icon is displayed when navigates to other doc and come back to same doc where the user did search");
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		softAssert.assertEquals(docViewRedact.getMagnifyingIcon().Displayed().booleanValue(), true);
		element = docViewRedact.getHitIcon();
		try {
			if (!element.Stale()) {
				baseClass.failedStep("Search is not cleared");
			} else {
				baseClass.passedStep("Search is cleared successfully");
			}
		} catch (Exception e) {
			baseClass.passedStep("Search is cleared successfully");

		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		baseClass.stepInfo("Navigate to another document");
		docViewPage.selectDocIdInMiniDocList(Input.searchDocId);
		baseClass.stepInfo(
				"Verify whether search is cleared,Magnifying icon is displayed when navigates to other doc and come back to same doc where the user did search");
		baseClass.waitForElement(docViewRedact.getMagnifyingIcon());
		softAssert.assertEquals(docViewRedact.getMagnifyingIcon().Displayed().booleanValue(), true);
		element = docViewRedact.getHitIcon();
		try {
			if (!element.Stale()) {
				baseClass.failedStep("Search is not cleared");
			} else {
				baseClass.passedStep("Search is cleared successfully");
			}
		} catch (Exception e) {
			baseClass.passedStep("Search is cleared successfully");

		}
		loginPage.logout();
	}

	/**
	 * @Author Raghuram A date:2/8/2022 Modified date: NA Modified by:N/A
	 * @Description : To verify document view panel from doc view page
	 *              [RPMXCON-50777]
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50777",enabled = true, groups = { "regression" })
	public void verifyDocViewPanel() throws InterruptedException {
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		String cascadeSettings_yes = "Yes";
		List<String> docIDlist = new ArrayList<>();
		String sortBy = "DocID";

		loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50777 Assignments Sprint-12");
		baseClass.stepInfo("To verify document view panel from doc view page");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Assignment group creation
		agnmt.navigateToAssignmentsPage();
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(cascadeAsgnGrpName, cascadeSettings_yes);
		agnmt.Assgnwithdocumentsequence(sortBy, Input.sortType);

		// Assignment creation under group via search -> bulk assign
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignWithNewAssgn(cascadeAsgnGrpName);
		agnmt.quickAssignmentCreation(assignment, Input.codeFormName);
		agnmt.saveAssignment(assignment, Input.codeFormName);
		baseClass.stepInfo("Created Assignment name : " + assignment);

		// Distribute to RMU
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.getSelectAssignment(assignment).waitAndClick(5);
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.assignmentDistributingToReviewer();

		// Selecting the assignment from group
		agnmt.navigateToAssignmentsPage();
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.selectAssignmentToViewinDocView(assignment);

		// Verify default doc details
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());
		Collections.sort(docIDlist);
		String defaultDocName = docIDlist.get(0);
		baseClass.stepInfo("First document from the list : " + defaultDocName);

		// Result
		baseClass.printResutInReport(miniDocListpage.verifySelectedDocHighlight(defaultDocName),
				"Default Document is selected : " + defaultDocName,
				"Failed to select default document" + defaultDocName, "Pass");

		String docName = miniDocListpage.getMainWindowActiveDocID().getText();
		baseClass.stepInfo("Current Document Viewed : " + docName);
		baseClass.textCompareEquals(docName, defaultDocName,
				"Document View panel contains detail/content view of selected document - In default first document of mini doc list is selected",
				"Failed in Default document view");

		// Delete Assign group and assign
		agnmt.deleteAssignmentFromSingleAssgnGrp(cascadeAsgnGrpName, assignment);
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51936 Verify that when performing doc-to-doc navigation after
	 * navigating to document on entering the document number the same document in
	 * mini-DocList must always present fully in the visible area of the
	 * mini-DocList
	 * 
	 * stabilization - done
	 */

	@Test(description ="RPMXCON-51936",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocInMiniDocListAfterPerformDocNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51936");
		baseClass.stepInfo(
				"Verify that when performing doc-to-doc navigation after navigating to document on entering the document number the same document in mini-DocList must always present fully in the visible area of the mini-DocList");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			System.err.println(docViewId);
			System.err.println(miniDocListId);
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitTillElemetToBeClickable(docView.getDocumetId());
		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.verifyThatIsLastDoc();
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 1/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after applying coding
	 * stamp. 'RPMXCON-51276' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-51276",enabled = true, groups = { "regression" })
	public void verifyAssignmentProgressBarCompleteTheCodingStamp() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51276");
		baseClass.stepInfo("Verify assignment progress bar refreshesh after applying coding stamp.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		ReusableDocViewPage reusableDocView = new ReusableDocViewPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 3: Search the documents with search term from basic search and go to doc view");
		assignmentsPage.SelectAssignmentByReviewer(assname);

		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		// edit coding form
		docView.editCodingFormComplete();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");
		loginPage.logout();

	}

	/**
	 * Author :Vijaya.Rani date: 18/02/2022 Modified date: NA Modified by: NA
	 * Description :Verify- Docview for threaded map with 25 emails, 50
	 * participants. 'RPMXCON-51638' Sprint-12
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51638",enabled = true, groups = { "regression" })
	public void verifyDocViewThreadMapEmailsParticipants() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51638  sprint 12");
		SessionSearch search = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Verify- Docview for threaded map with 25 emails, 50 participants");

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Search for the doc and go to DocView");
		search.basicContentSearch(Input.ThreadQuery);
		search.ViewInDocView();

		// size of emails and participant before clicking the More data btn
		docView.selectDocsFromMiniDocsListChechThreadMapEmails();
		int beforeEmailCount = docView.getDocView_Analytics_ThreadedDocs().size();
		int beforePartcipantCount = docView.getDocView_Analytics_ThreadedMapParticipantDocs().size();

		baseClass.waitForElement(docView.getDocView_Analytics_LoadMoreButton());
		docView.getDocView_Analytics_LoadMoreButton().waitAndClick(10);
		baseClass.stepInfo("More data exists than displayed all data link is clicked successfully");

		// size of emails and participant After clicking the More data btn
		docView.selectDocsFromMiniDocsListChechThreadMapEmails();
		int afterEmailCount = docView.getDocView_Analytics_ThreadedDocs().size();
		int afterPartcipantCount = docView.getDocView_Analytics_ThreadedMapParticipantDocs().size();

		softAssertion.assertNotEquals(beforeEmailCount, afterEmailCount);
		softAssertion.assertAll();
		baseClass.passedStep("After Click Read More Button the Eamils Are increased successfully");

		softAssertion.assertNotEquals(beforePartcipantCount, afterPartcipantCount);
		softAssertion.assertAll();
		baseClass.passedStep("After Click Read More Button the Partcipant Are increased successfully");

	}

	/**
	 * Author :Vijaya.Rani date: 22/02/2022 Modified date: NA Modified by: NA
	 * Description :Verify that redaction/annotations/remarks icons should be
	 * displayed on doc view though the System Admin user who created project is
	 * in-activeVerify that redaction/annotations/remarks icons should be displayed
	 * on doc view though the System Admin user who created project is in-active.
	 * 'RPMXCON-57088' Sprint-12
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51349",enabled = true, groups = { "regression" })
	public void verifyDocViewRedactionAnnotationsRemarksIconDisplay() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57088  sprint 12");
		SessionSearch search = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Verify that redaction/annotations/remarks icons should be displayed on doc view though the System Admin user who created project is in-active");

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Step 1: Search for the doc and go to DocView");
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocView();

		// verify redaction icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.redactionIcon());
		softAssertion.assertTrue(docView.redactionIcon().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("Redaction Icon is Displayed Succesfully");

		// verify annotation icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AnnotateIcon());
		softAssertion.assertTrue(docView.getDocView_AnnotateIcon().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("DocView Annotation Icon is Displayed Succesfully");

		// verify Remark icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AddRemarkIcon());
		softAssertion.assertTrue(docView.getDocView_AddRemarkIcon().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("DocView Reviewer Remark Icon is Displayed Succesfully");
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Test Case Id
	 * RPMXCON-51995 & 51996 - sprint 3- DocView Description : Verify redaction for
	 * page range and all pages in a doc
	 */
	@Test(description ="RPMXCON-51995,RPMXCON-51996",enabled = true, alwaysRun = true, groups = { "regression" })
	public void RedactPageRange() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51995, RPMXCON-51996");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch("ID00000230");
		baseClass.stepInfo("Search for document completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.build().perform();
		docViewRedact.verifyingMultipageIconColour(Input.iconColor);
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.stepInfo("Success message has been verified");
		baseClass.CloseSuccessMsgpopup();
		baseClass.passedStep("Redaction using page range option is successfully executed");
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.fullPageRange);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.stepInfo("Success message has been verified");
		baseClass
				.passedStep("Redaction using page range option for all pages in the document is successfully executed");
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Test Case Id
	 * RPMXCON-51998 & 51997 - sprint 3- DocView Description : Verify redaction for
	 * page range and all pages in a doc after impersonating user
	 */
	@Test(description ="RPMXCON-51997,RPMXCON-51998",enabled = true, alwaysRun = true, groups = { "regression" })
	public void RedactPageRangeImpersonatingUser() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51998, RPMXCON-51997");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("PA has been impersonated as RMU");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch("ID00000230");
		baseClass.stepInfo("Search for document completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.stepInfo("Success message has been verified");
		baseClass.passedStep(
				"Redaction using page range option is successfully executed after impersonating from PA to RMU");
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.fullPageRange);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.stepInfo("Success message has been verified");
		baseClass
				.passedStep("Redaction using page range option for all pages in the document is successfully executed");
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52150
	 * Verifying blank Redaction Login as RMU Go to Search Search any term and
	 * DocView
	 */

	@Test(description ="RPMXCON-52150",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionTagBlank() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
// search for document
		baseClass.stepInfo("Test case Id: RPMXCON-52150");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Seach completed with document Id and viewed in DocView");
//Drawing redaction and checking for tag
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(5);
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		Select redactionTag = new Select(docViewRedact.rectangleRedactionTagSelect().getWebElement());
		List<WebElement> options = redactionTag.getOptions();
		WebElement webElement = options.get(0);
		String text = webElement.getText();
		System.out.println(text);
		if (!(text == null)) {
			assertTrue(true);
			baseClass.passedStep("The Redaction name is not empty");
		} else {
			baseClass.failedStep("The Redaction name is empty as the previously used redaction tag was deleted");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52189
	 * Verifying persistent hit for audio docs DocView- sprint 3
	 */

	@Test(description ="RPMXCON-52189",enabled = true, alwaysRun = true, groups = { "regression" })
	public void persistentHitAudioDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52189");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.quickbatch();
		baseClass.stepInfo("Created Assignment using Quick Batch");
		assignmentspage.createNewquickBatchWithoutReviewer(assignmentName, Input.codingFormName);
		assignmentspage.ViewinDocviewFromAssignments(assignmentName);
		baseClass.stepInfo("Assignment created in quick batch and viewed in DocView");
		docViewRedact.checkingPersistentHitPanelAudio();
		if (docViewRedact.persistantHitRightNavigate().Displayed()
				&& docViewRedact.persistantHitRightNavigate().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("The persistent hit panel is visible for audio documents");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51937 Verify that when performing doc-to-doc navigation after
	 * navigating to document on entering the document number the same document in
	 * mini-DocList child window must present fully in the visible area of the
	 * mini-DocList child window
	 * 
	 * stabilization - done
	 */

	@Test(description ="RPMXCON-51937",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocInMiniDocListChildWindowsAfterPerformNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51937");
		baseClass.stepInfo(
				"Verify that when performing doc-to-doc navigation after navigating to document on entering the document number the same document in mini-DocList child window must present fully in the visible area of the mini-DocList child window");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.switchTo().window(parentWindowID);

		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.switchTo().window(parentWindowID);

		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.waitForElement(docView.getDocumetId());
		docView.searchDocumentBasedOnId(13);
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.switchTo().window(parentWindowID);

		driver.getWebDriver().navigate().refresh();
		try {
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		docView.enterDocumentNumberTillLoading();
		driver.waitForPageToBeReady();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after scrolling down to document");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		driver.waitForPageToBeReady();
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();
		System.err.println(miniDocListId);
		System.err.println(docViewId);
		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify document loaded in mini doc list and docview is same after Doc navigation");
		docView.getDocView_Last().waitAndClick(5);
		driver.waitForPageToBeReady();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		if (docView.getSelectedDocIdInMiniDocList().isDisplayed()) {
			baseClass.passedStep("Doc is getting displayed after performing doc navigation");
			softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not getting displated after performing doc navigation");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		if (docView.getDocView_CurrentDocId().isDisplayed()) {
			baseClass.passedStep("Doc is getting loaded in DocView");
			softAssertion.assertEquals(docView.getDocView_CurrentDocId().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView after performing doc navigation");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView after performing doc navigation");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : STEFFY date: 13/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that when mini doc list child window is open after
	 * completing the documents and toggle to show completed documents is OFF/ON.
	 * 'RPMXCON-51938' Sprint : 10
	 * 
	 * @throws Exception
	 * 
	 *                   stabilization done
	 */
	@Test(description ="RPMXCON-51938",enabled = true, groups = { "regression" })
	public void verifyMiniDocListChildWindow() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51938");
		baseClass.stepInfo(
				"Verify that when mini doc list child window is open after completing the documents and toggle to show completed documents is OFF/ON");

		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		SoftAssert softAssert = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		String codingForm = Input.codeFormName;
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		docView.editCodingFormComplete();
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docView.popOutMiniDocList();
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssert.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}
		new MiniDocListPage(driver).configureMiniDocListToShowCompletedDocs(parentWindowID);
		baseClass.handleAlert();
		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();
		docView.popOutMiniDocList();
		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssert.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentsPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51992 DocView and Doc Explorer_Performance_Navigate through
	 * documents one by one for 15 mins_RMU/Rev
	 */
	@Test(description ="RPMXCON-51992",enabled = true, alwaysRun = true, groups = { "regression" })
	public void DocViewAndDocExplorerPerformanceNavigate() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51992");
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		DocExplorerPage docexp = new DocExplorerPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		docexp.exportData();
		loginPage.logout();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51731 Verify that text highlighting should not be present on doc
	 * view
	 */
	@Test(description ="RPMXCON-51731",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyTextHightingNotPresent() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51731");
		docViewRedact = new DocViewRedactions(driver);

		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.TextHightingNotPresentVerify();
		loginPage.logout();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51755 Verify that when the toggle is ON will hide the 0 hit terms
	 * on hits panel
	 */
	@Test(description ="RPMXCON-51755",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyToggleONhideZeroHits() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51755");
		docViewRedact = new DocViewRedactions(driver);

		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.ToggleONhideZeroHits();
		loginPage.logout();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51756 Verify that when the toggle is OFF will show the 0 hit
	 * terms on hits panel
	 */
	@Test(description ="RPMXCON-51756",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyToggleOFFshowZeroHits() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51756");
		docViewRedact = new DocViewRedactions(driver);

		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.ToggleOFFshowZeroHits();
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51989 Verify colour is same when remarks edited by different RMU user
	 */
	@Test(description ="RPMXCON-51989",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyColourRemarksEditing() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51989");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.docIdRemarks);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(3000);
//Thread sleep added for the page to adjust resolution		
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(200, 100).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		baseClass.waitForElement(docViewRedact.addRemarksBtn());
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		if (baseClass.getSuccessMsgHeader().isDisplayed()) {
			baseClass.VerifyErrorMessage("Please select content from a document to place remark on");
			baseClass.stepInfo("The document does not contain text in the selected area to add remark");
		} else {
			actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
			actions.click();
			actions.sendKeys("Remark by RMU");
			actions.build().perform();
			baseClass.passedStep("Created Remarks as RMU");
			actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
			actions.click().build().perform();
			driver.waitForPageToBeReady();
			String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex1 = Color.fromString(color).asHex();
			System.out.println(hex1);
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
			sessionsearch.basicContentSearch(Input.docIdRemarks);
			baseClass.stepInfo("Search with text input is completed");
			sessionsearch.ViewInDocView();
			docViewRedact.clickingRemarksIcon();
			wait.until(ExpectedConditions
					.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
			Thread.sleep(4000);
//Thread sleep added for the page to adjust resolution

			docViewRedact.editRemarksIcon().waitAndClick(5);
			baseClass.stepInfo("Edit remarks icon has been clicked");
			docViewRedact.editRemarksTextArea().waitAndClick(5);
			docViewRedact.editRemarksTextArea().getWebElement().clear();
			actions.sendKeys("Remark edited by RMU");
			actions.build().perform();
			String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex2 = Color.fromString(color2).asHex();
			System.out.println(hex2);
			if (hex1.equalsIgnoreCase(hex2)) {
				baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
			} else {
				baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");
			}
			loginPage.logout();
		}
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51558 Verify if X icon is visible after clicking on the
	 * magnfifying/search icon in docView page
	 */
	@Test(description ="RPMXCON-51558",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyMagnifyingIconInDocViewForAllUsers() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51558");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.searchTextIcon());
		docViewRedact.searchTextIcon().waitAndClick(10);
		if (docViewRedact.crossxIcon().Displayed() == true) {
			baseClass.passedStep("The X Icon is visible after clicking the magnifying tab");
		} else {
			baseClass.failedStep("The X Icon is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51316 Verify if color changes for any annotation icon when clicked on
	 * DocView Page
	 */
	@Test(description ="RPMXCON-51316",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyOnOffColourChangeInDocViewPage() throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51316");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchBulletDocId);
		baseClass.stepInfo("Search for document completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		docViewRedact.clickingRedactionIcon();
		baseClass.waitForElement(docViewRedact.multiPageIcon());
		try {
			actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
			actions.build().perform();
		} catch (Exception e) {
			docViewRedact.multiPageIcon().waitAndClick(5);
		}
		docViewRedact.verifyingMultipageIconColour(Input.iconColor);
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.build().perform();
		docViewRedact.verifyingMultipageIconColour(Input.iconColor);
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-50848 Verify if the persistent hit icon is present in DocView from
	 * Basic Search for non-audio Docs
	 */
	@Test(description ="RPMXCON-50848",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPersistantHitIconFromBasicSearch() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50848");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.persistantHitBtn());
		Boolean status = docViewRedact.persistantHitBtn().isElementPresent();
		System.out.println(status);
		if (status == true) {
			baseClass.passedStep(
					"The persistent hit button for non-audio docs from basic search is avilable in DocView as RMU");
		} else {
			baseClass.failedStep(
					"The persistent hit button for non-audio docs from basic search is NOT avilable in DocView as RMU");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51346 Verify if highlights panel once open is not closed while
	 * navigating docs from mini doc list
	 */
	@Test(description ="RPMXCON-51346",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyHighlitesPanelisOpenWhileNavigatingFromMiniDocList() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51346");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingHighlitingIcon();
		docViewRedact.docListTableItration();
		Boolean status = docViewRedact.thisPageHighlite().isElementPresent();
		System.out.println(status);
		if (status == true) {
			baseClass.passedStep("The highlite menu is open while navigating docs from mini Doc List");
		} else {
			baseClass.failedStep("The highlite menu is NOT open while navigating docs from mini Doc List");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51345 Verify that reviewer remarks panel remains selected while
	 * navigating through docs
	 */
	@Test(description ="RPMXCON-51345",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyReviewerRemarksPanel() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51345");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		docViewRedact.docListTableItration();
		if (docViewRedact.addRemarksBtn().isElementPresent()) {
			baseClass.passedStep("Remarks menu is visble after navigating through docs from mini doc list");
		} else {
			baseClass.failedStep("Remarks menu is NOT visble after navigating through docs from mini doc list");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51344 Verify that reviewer Persistent hits panel remains selected
	 * while navigating through docs
	 */
	@Test(description ="RPMXCON-51344",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyReviewerPersistentHitPanel() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51344");
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		docViewRedact.docListTableItration();
		if (docViewRedact.persistantHitToggle().isElementPresent()) {
			baseClass.passedStep("Persistent hit menu is visble after navigating through docs from mini doc list");
		} else {
			baseClass.failedStep("Persistent hit menu is NOT visble after navigating through docs from mini doc list");
		}
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51849 Verify that persistent hits panel should not retain
	 * previously viewed hits for the document on completing the document
	 * 
	 * stabilization done
	 */

	@Test(description ="RPMXCON-51849",enabled = true, alwaysRun = false, groups = { "regression" })
	public void verifyPersistentHitsAfterCompletingDocumentsNotRetain() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51849");
		baseClass.stepInfo(
				"Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document from coding form child window");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Logout As ReviewManager
		loginPage.logout();
		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");

		// Login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");

		// verify Assignment name
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		if (assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue()) {
			baseClass.passedStep("User is in doc view in context of an assignment");
			softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue(), true);
		} else {
			baseClass.stepInfo("User is in doc view in context of an assignment is not assigned");
		}

		baseClass.stepInfo("Verify whether the panels are displayed in doc view");
		baseClass.waitTillElemetToBeClickable(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		driver.waitForPageToBeReady();
		if (docView.getHitPanel().isDisplayed()) {
			baseClass.passedStep("Persistent hit panels are displayed");
			softAssert.assertEquals(docView.getHitPanel().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		// verify HitPanetCount is not retained
		baseClass.waitForElement(docView.getHitPanelCount());
		String beforeComplete = docView.getHitPanelCount().getText();
		System.out.println(beforeComplete);
		docView.editCodingFormComplete();
		String afterComplete = docView.getHitPanelCount().getText();
		System.out.println(afterComplete);
		baseClass.stepInfo("persistent hits panel is not retain previously viewed hits");
		softAssert.assertNotEquals(beforeComplete, afterComplete);
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Aathith Modified date: NA Modified by: Aathith RPMXCON-50786
	 * @Description : To verify document should be displayed in doc view panel as
	 *              per the entered document number in the inputbox.
	 */
	@Test(description ="RPMXCON-50786",enabled = true, groups = { "regression" })
	public void verifyDocumentAsPerEnteredDocument() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50786");
		baseClass.stepInfo(
				"To verify document should be displayed in doc view panel as per the entered document number in the inputbox.");

		DocViewPage docViewPage = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);

		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Select the Assigment go to Docview");
		assignmentPage.selectAssignmentToViewinDocview(assname);
		baseClass.stepInfo("Doc view page is selected from assigment page");

		driver.waitForPageToBeReady();
		docViewPage.document_Navigation_verification(15);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();
		docViewPage.document_Navigation_verification(25);

		int lastdoc = docViewPage.verifyingDocCount();

		docViewPage.document_Navigation_verification(lastdoc);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Doc view page is selected from sessionsearch page");

		driver.waitForPageToBeReady();
		docViewPage.document_Navigation_verification(15);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();
		docViewPage.document_Navigation_verification(25);

		int lastdoc1 = docViewPage.verifyingDocCount();

		docViewPage.document_Navigation_verification(lastdoc1);
		docViewPage.navigation_Bar_EnableDisableCheck();
		docViewPage.lastDoc_Navigation_Bar_EnableDisableCheck();

		baseClass.passedStep(
				"verified document should be displayed in doc view panel as per the entered document number in the inputbox.");
		loginPage.logout();
	}

	/**
	 * Author : Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51854 Verify that persistent hits panel should not retain
	 * previously viewed hits for the document on completing the document same as
	 * last from coding form child window.
	 */
	@Test(description ="RPMXCON-51854",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitsCompletingDocumentsCodeSameAsLastChildWindow() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51854");
		baseClass.stepInfo(
				"Verify that persistent hits panel should not retain previously viewed hits for the document on completing the document same as last from coding form child window");
		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		// Logout as ReviewManager
		loginPage.logout();
		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");

		// Verify AssignmentName is Displayed
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		if (assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue()) {
			baseClass.passedStep("User is in doc view in context of an assignment");
			softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("User is in doc view in context of an assignment is not assigned");
		}

		// verify PeristantHitEyeIcon is Displayed
		baseClass.stepInfo("Verify whether the panels are displayed in doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		if (docView.getHitPanel().isDisplayed()) {
			baseClass.passedStep("Persistent hit panels are displayed");
			softAssert.assertEquals(docView.getHitPanel().isDisplayed().booleanValue(), true);
		} else {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		// verify Edit and complete CodingForm childWindow and LastDocument
		baseClass.waitForElement(docView.getHitPanelCount());
		String beforeComplete = docView.getHitPanelCount().getText();
		System.out.println(beforeComplete);
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return docView.getGearIcon().Visible() && docView.getGearIcon().Enabled();
			}
		}, Input.wait30);
		docView.getGearIcon().waitAndClick(10);
		docView.getDocument_CommentsTextBox().waitAndClick(5);
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		driver.scrollPageToTop();
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_CodingFormPopOut().Visible()
						&& docView.getDocView_CodingFormPopOut().Enabled();
			}
		}, Input.wait30);
		docView.getDocView_CodingFormPopOut().waitAndClick(5);
		docView.switchToNewWindow(2);
		docView.editCodingFormCompleteChildWindow();
		docView.switchToNewWindow(2);
		softAssert.assertTrue(docView.getCodeSameAsLast().Displayed() && docView.getCodeSameAsLast().Enabled());
		if (docView.getCodeSameAsLast().Displayed() && docView.getCodeSameAsLast().Enabled()) {
			baseClass.passedStep("document same as last from coding form child window");
		} else {
			baseClass.failedStep("document same as last from is not Enabled");
		}
		driver.waitForPageToBeReady();
		docView.closeWindow(1);
		docView.switchToNewWindow(1);
		baseClass.waitForElement(docView.getHitPanelCount());
		String afterComplete = docView.getHitPanelCount().WaitUntilPresent().getText();
		System.out.println(afterComplete);
		baseClass.stepInfo("persistent hits panel is not retain previously viewed hits");

		softAssert.assertNotEquals(beforeComplete, afterComplete);
		softAssert.assertAll();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51331 Verify text from review mode outside of an assignment
	 */

	@Test(description ="RPMXCON-51331",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyReviewModeTextOutsideAssignment() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case id : RPMXCON-51331");
		baseClass.stepInfo("Verify text from review mode outside of an assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docView.verifyReviewModeText();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		docView.verifyReviewModeText();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.pa1userName + "");
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		docView.verifyReviewModeText();
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51940 Verify that when user is selecting a document to view after
	 * scrolling down/up the mini doc list child window and in DocView, the entry
	 * for the same document in mini-DocList must always present fully in the
	 * visible area of the mini-DocList child window stabilization -done
	 */

	@Test(description ="RPMXCON-51940",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocInMiniDocListChildWindowAfterScrollingDown() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51940");
		baseClass.stepInfo(
				"Verify that when user is selecting a document to view after scrolling down/up the mini doc list child window and in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList child window");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(false);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(false);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();

		parentWindowID = driver.getWebDriver().getWindowHandle();

		// Popout MiniDocList
		docView.popOutMiniDocList();

		allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.waitForElement(docView.getDocumetId());
		docView.scrollUntilloadingTextDisplay(false);

		baseClass.waitForElement(docView.getDocView_MiniDocListIds(1));
		docView.getDocView_MiniDocListIds(1).waitAndClick(5);

		driver.switchTo().window(parentWindowID);

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displayed after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51943 Verify when user enters document number to view when
	 * 'Loading..' displays in mini doc list, the entry for the same document in
	 * mini-DocList must always present fully in the visible area of the
	 * mini-DocList
	 * 
	 * stabilization - done
	 */

	@Test(description ="RPMXCON-51943",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocInMiniDocsAfterScrollingDownTillLoadingTextDisplayedWhenDocIsSearchedUsingDocId()
			throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		int docToBeSearched = Input.totalNumberOfDocsincludeadvoption;
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51943");
		baseClass.stepInfo(
				"Verify when user enters document number to view when 'Loading..' displays in mini doc list, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		String miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rev1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}

		loginPage.logout();

		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.ViewInDocViews();
		baseClass.waitForElement(docView.getDocView_NumTextBox());
		docView.enterDocumentNumberTillLoading();

		baseClass.stepInfo("Verify whether the doc is getting displayed in DocView");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());

		try {
			if (docView.getDocView_CurrentDocId().Displayed()) {
				baseClass.passedStep("Doc is getting loaded in DocView");
				softAssertion.assertEquals(docView.getDocView_CurrentDocId().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not loaded in DocView");
		}

		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docViewId = docView.getDocView_CurrentDocId().getText();

		baseClass.stepInfo("Verify whether the doc is getting displayed in Mini Doc List Child window");
		baseClass.waitForElement(docView.getSelectedDocIdInMiniDocList());

		try {
			if (docView.getSelectedDocIdInMiniDocList().Displayed()) {
				baseClass.passedStep("Doc is getting displayed after scrolling down to document");
				softAssertion.assertEquals(docView.getSelectedDocIdInMiniDocList().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Doc is not getting displated after scrolling down to document");

		}

		miniDocListId = docView.getSelectedDocIdInMiniDocList().getText();

		baseClass.stepInfo(
				"Verify whether the Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
		if (docViewId.equals(miniDocListId)) {
			baseClass.passedStep(
					"Doc selected in Mini Doc List is same as the document which is getting displayed in DocView");
			softAssertion.assertEquals(docViewId, miniDocListId);
		} else {
			baseClass.failedStep(
					"Doc selected in Mini Doc List is not same as the document which is getting displayed in DocView");
		}
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51770 Verify when remove non audio docs from a user/reviewer in an
	 * assignment, displays persistent search hits in the assignment, when
	 * reassigning these non audio docs to another/same reviewer in the assignment
	 * 
	 * stabilization - done
	 */

	@Test(description ="RPMXCON-51770",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitsAfterRemoveAndReassignDocumentsSavedSearchGroup() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51770");
		baseClass.stepInfo(
				"Verify when remove non audio docs from a user/reviewer in an assignment, displays persistent search hits in the assignment, when reassigning these non audio docs to another/same reviewer in the assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		try {
			if (assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue()) {
				baseClass.passedStep("User is in doc view in context of an assignment");
				softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("User is not in doc view in context of an assignment");
		}

		baseClass.stepInfo("Verify whether the panels are displayed in doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to remove docs and reassign docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");

		assignmentsPage.removeDocs(Input.rev1userName);

		assignmentsPage.reassignDocs(Input.rev1userName);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to vreify persistent hits are displayed even after removed and reasigned docs");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after remove and reassign docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to delete the created assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		assignmentsPage.deleteAssgnmntUsingPagination(assname);

		softAssert.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51765 Non-Audio Documents assigned from Saved Search group- Verify
	 * that when documents are re-assigned to same/other reviewer in an assignment,
	 * any previously saved persistent search hits in the assignment should be
	 * displayed in the assignment
	 * 
	 * stabilization - done
	 */

	@Test(description ="RPMXCON-51765",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitsAfterReassignDocumentsSavedSearchGroup() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51765");
		baseClass.stepInfo(
				"Non-Audio Documents assigned from Saved Search group- Verify that when documents are re-assigned to same/other reviewer in an assignment, any previously saved persistent search hits in the assignment should be displayed in the assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		baseClass.stepInfo("Sharing the saved search with security group");
		savedSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);

		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to verify whether reviewer can view docs in doc view from assignment");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the user is in doc view in context of an assignment");
		baseClass.waitForElement(assignmentsPage.getAssignmentNameInDocView());
		try {
			if (assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue()) {
				baseClass.passedStep("User is in doc view in context of an assignment");
				softAssert.assertEquals(assignmentsPage.getAssignmentNameInDocView().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("User is not in doc view in context of an assignment");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to remove docs and reassign docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");

		assignmentsPage.removeDocs(Input.rev1userName);

		assignmentsPage.reassignDocs(Input.rev1userName);

		loginPage.logout();

		baseClass.stepInfo(
				"Logging in to reviewer account to vreify persistent hits are displayed even after removed and reasigned docs");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after remove and reassign docs");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().Click();
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		try {
			if (docView.getHitPanel().Displayed()) {
				baseClass.passedStep("Persistent hit panels are displayed");
				softAssert.assertEquals(docView.getHitPanel().Displayed().booleanValue(), true);
			}
		} catch (Exception e) {
			baseClass.failedStep("Persistent hit panels are not displayed");
		}

		loginPage.logout();

		baseClass.stepInfo("Logging in to RMU user to delete the created assignment");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		assignmentsPage.deleteAssgnmntUsingPagination(assname);

		softAssert.assertAll();
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51746 Verify that on document navigation options when hits panel
	 * is open then enable/disable should be retained
	 */
	@Test(description ="RPMXCON-51746",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPersistentHitPanelIsRetained() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51746");
		baseClass.stepInfo(
				"Verify that on document navigation options when hits panel is open then enable/disable should be retained");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardNextDocBtn());
		docViewRedact.forwardNextDocBtn().Click();
		baseClass.waitTillElemetToBeClickable(docViewRedact.persistantHitToggle());
		docViewRedact.persistantHitToggle().Click();
		if (docViewRedact.persistantHitToggle().Enabled() == true) {
			baseClass.passedStep("Persistent hit panel is open when navigated to first Doc");
		} else {
			baseClass.failedStep("Persistent hit panel is NOT open when navigated to first Doc");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardToLastDoc());
		docViewRedact.forwardToLastDoc().Click();
		baseClass.waitForElement(docViewRedact.persistantHitToggle());
		String getAttribute = docViewRedact.persistantHitToggle().GetAttribute("data-class");
		System.out.println(getAttribute);
		if (getAttribute.equalsIgnoreCase("true")) {
			baseClass.stepInfo("The persistent hit is ON while document is navigated to last");

		} else {
			baseClass.failedStep("The persistent hit toggle is OFF while document is navigated to last");
		}
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when page highlighting added for different
	 *         file types (e.g. XLS, XLSX, CSV, etc...) then highlighting should not
	 *         be shifted when visiting these documents : RPMXCON-52309 Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-52309",enabled = true, groups = { "regression" })
	public void verifyHighlightedThisPage() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList;
		String firnstDocname, secondDocname, docName;

		baseClass.stepInfo("Test case Id: RPMXCON-52309 Sprint 10");
		baseClass.stepInfo(
				"Verify that when page highlighting added for different file types (e.g. XLS, XLSX, CSV, etc...) then highlighting should not be shifted when visiting these documents");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// check And DO ThisPage Redact
		docViewRedact.checkANdDOThisPageHighlight();

		// Switch to a different document
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Iniital Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// verify ThisPage Redacted Maintained
		docViewRedact.verifyThisPageHighlightMaintained(true);
		loginPage.logout();
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51567 Verify when the user clicks on the X after the text search in a
	 * document
	 */
	@Test(description ="RPMXCON-51567",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyTextSearchAfterUserClicksX() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51567");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.redactionIcon());
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		docViewRedact.verifySearchUsingMagnifyingIcon(false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		docViewRedact.verifySearchUsingMagnifyingIcon(false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in using PA account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.verifySearchUsingMagnifyingIcon(true);
		docViewRedact.verifySearchUsingMagnifyingIcon(false);
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when rectangle redaction added for
	 *         different file types (e.g. XLS, XLSX, CSV, etc...) then redaction
	 *         should not be shifted when visiting these documents : RPMXCON-52301
	 *         Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-52301",enabled = true, groups = { "regression" })
	public void verifyRectangleRedactionPosition() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList, x = 20, y = 10;
		int xOffset = 20, yOffset = 10;
		String firnstDocname, secondDocname, docName, xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		baseClass.stepInfo("Test case Id: RPMXCON-52301 Sprint 10");
		baseClass.stepInfo(
				"Verify that when rectangle redaction added for different file types (e.g. XLS, XLSX, CSV, etc...) then redaction should not be shifted when visiting these documents");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Check for this page highlighted and remove
		docViewRedact.removeThisPageHighlight();

		// do Rectangle Redact With XY Points
		xyMap = docViewRedact.doRectangleRedactWithXYPoints(x, y, xOffset, yOffset);
		xAxis = xyMap.get("xAxis");
		yAxis = xyMap.get("yAxis");

		// Switch to a different document
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
//		baseClass.waitForElement(miniDocListpage.getDociD(firnstDocname));
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Verify Position Retained
		docViewRedact.verifyRectangleRedactionPositionRetained(xAxis, yAxis, true);
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A date: NA Modified date: 01/18/21 Modified by: Raghuram A
	 *         Description : Verify that when rectangle redaction added for
	 *         different file types (e.g. XLS, XLSX, CSV, etc...) then redaction
	 *         should not be shifted when visiting these documents : RPMXCON-52310
	 *         Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-52310",enabled = true, groups = { "regression" })
	public void verifyRectangleHighlightingPosition() throws Exception {

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList, x = 20, y = 10;
		int xOffset = 20, yOffset = 10;
		String firnstDocname, secondDocname, docName, xAxis, yAxis;
		HashMap<String, String> xyMap = new HashMap<String, String>();

		baseClass.stepInfo("Test case Id: RPMXCON-52310 Sprint 10");
		baseClass.stepInfo(
				"Verify that when highlighting added for different file types (e.g. XLS, XLSX, CSV, etc...) on different pages then highlighting should not be shifted when visiting these documents");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString8);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Check for this page highlight and remove
		docViewRedact.removeThisPageHighlight();

		// do Rectangle Redact With XY Points
		xyMap = docViewRedact.doRectangleHighlightWithXYPoints(x, y, xOffset, yOffset);
		xAxis = xyMap.get("xAxis");
		yAxis = xyMap.get("yAxis");

		// Switch to a different document
		secondDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
//		baseClass.waitForElement(miniDocListpage.getDociD(firnstDocname));
		miniDocListpage.getDociD(firnstDocname).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Verify Position Retained
		docViewRedact.verifyRectangleHighlightPositionRetained(xAxis, yAxis, true);
		loginPage.logout();

	}

	/**
	 * Author :Vijaya.Rani date: 05/04/2022 Modified date: NA Modified by: NA
	 * Description:Verify that message to reload the document should not be
	 * displayed when two users under different project views the document with same
	 * doc id, with different mapped annotation layer id and adds redaction
	 * 'RPMXCON-52210' Sprint-14
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-52210",enabled = true, groups = { "regression" })
	public void verifySameDocIdAndDifferentAnnotationAddRedaction() throws Exception {

		baseClass = new BaseClass(driver);
		String AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String AnnotationLayerNew1 = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-52210 sprint 14");

		baseClass.stepInfo(
				"Verify that message to reload the document should not be displayed when two users under different project views the document with same doc id, with different mapped annotation layer id and adds redaction");

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);

		// Creating annotation layer
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		baseClass.passedStep("Annation Layer is Created successully");
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew1);
		baseClass.passedStep("Annation Layer1 is Created successully");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		Reporter.log("Logged in as User: " + Input.rmu1userName);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Viewed the Doc View Page");
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Redaction Is Added Succesully " + Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		Reporter.log("Logged in as User: " + Input.rmu2userName);
		baseClass.selectproject("Indium_Regressionrun");
		baseClass.stepInfo("Succesfully Selected Project1");
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Viewed the Doc View Page");
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Redaction Is Added Succesully " + Input.rmu2userName);
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 07/04/2022 Modified date: NA Modified by: NA
	 * Description:Verify that message to reload the document should not be
	 * displayed when two users under different project views the document with same
	 * doc id, with same mapped annotation layer id and adds redaction
	 * 'RPMXCON-52209' Sprint-14
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-52209",enabled = true, groups = { "regression" })
	public void verifySameDocIdAndSameAnnotationAddRedaction() throws Exception {

		baseClass = new BaseClass(driver);
		String AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-52209 sprint 14");

		baseClass.stepInfo(
				"Verify that message to reload the document should not be displayed when two users under different project views the document with same doc id, with same mapped annotation layer id and adds redaction.");

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);
		
		// Creating annotation layer
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);
		baseClass.passedStep("Annation Layer is Created successully");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		Reporter.log("Logged in as User: " + Input.rmu1userName);
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Viewed the Doc View Page");
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Redaction Is Added Succesully " + Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		Reporter.log("Logged in as User: " + Input.rmu2userName);
		baseClass.selectproject("Indium_Regressionrun");
		baseClass.stepInfo("Succesfully Selected Project1");
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Viewed the Doc View Page");
		docViewRedact.clickingRedactionIcon();
		driver.waitForPageToBeReady();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Redaction Is Added Succesully " + Input.rmu2userName);
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

		loginPage.quitBrowser();

	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");

	}

}