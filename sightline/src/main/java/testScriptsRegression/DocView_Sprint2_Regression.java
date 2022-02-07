package testScriptsRegression;

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
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Sprint2_Regression {
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

		// Login as a RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	}

	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Test Case
	 *         Id: RPMXCON-52030 Description : login as RMU and impersonate as
	 *         reviewer use search term to get document and redact
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 7)
	public void verifyRedactionasReviewer() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON 52030");
// Impersonating as Reviewer and searching with text input		
		baseClass.impersonateRMUtoReviewer();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
// Redacting using rectangular redaction
		docViewRedact.redactRectangleUsingOffset(-8, 10, 150, 200);
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
	 *         Test Case Id: RPMXCON-52222 Component: DocView/Redactions Description
	 *         : Verify that user should be able to apply rectangle redaction
	 *         Redaction tag with uncommon click path (F5)
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 2)

	public void verifyRectangularRedactionRMU() throws Exception {
// Selecting Document from DocExplorer
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52222");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.selectingFirstdataFromTable().Visible()
						&& docViewRedact.selectingFirstdataFromTable().Enabled();
			}
		}), Input.wait30);
		docViewRedact.selectingFirstdataFromTable().waitAndClick(10);
		baseClass.stepInfo("The First document from Doc Explorer tab is selected");
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.docExplorerActions().getWebElement()));
		actions.moveToElement(docViewRedact.docExplorerActions().getWebElement());
		actions.click().build().perform();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		baseClass.stepInfo("The First document from Doc Explorer is viewed in Doc View");

// Clicking Redaction icon and adding Redaction
		docViewRedact.redactRectangleUsingOffset(-12, 20, 50, 200);
		baseClass.stepInfo("A rectangle redaction has been applied");

// Selecting Default Redaction Tag for Applied Redaction and Refresh
		docViewRedact.selectingRectangleRedactionTag();
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);

		baseClass.passedStep("The Redaction has been performed and saved to Default Redaction Tag");
		baseClass.waitTillElemetToBeClickable(docViewRedact.redactionIcon());
		if (docViewRedact.redactionIcon().getWebElement().isDisplayed()
				&& docViewRedact.redactionIcon().getWebElement().isEnabled()) {
			assertTrue(true);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 3)

	public void verifyDocNavigationRMU() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 50779");
		SessionSearch sessionsearch = new SessionSearch(driver);
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 4)

	public void verifyTextRemarks() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON 51863");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 1)

	public void verifyBullhornNotifications() throws Exception {
// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());

		baseClass.stepInfo("Test case Id: RPMXCON-52232, RPMXCON-52230, RPMXCON-52229");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 0)

	public void verifyPersistantHitPanal() throws Exception {

// manage assignments and click test
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51758");
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 8)
	public void textRedactionAsRMU() throws Exception {
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52220, RPMXCON-52194");
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 9)
	public void verifyRedactionStabilityinAllDocTypes() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52303, RPMXCON-52304");
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 6)
	public void printRedactedPdfasReviewer() throws Exception {

// Selecting Document from Session search	
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id:RPMXCON 52231");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	 * Test Case Id: RPMXCON-52218 & RPMXCON-52219 Description : Redact first 2
	 * pages of a doc Navigate using page navigation and redaction menu does not
	 * appear while clicking on page, Redaction appears
	 * 
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 10)
	public void chekingRedactionDeleteButton() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52218, RPMXCON-52219");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input for pdf doc completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(20);
		baseClass.stepInfo("The Redaction Menu is Visible");
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.stepInfo("Redacted page 1 and page 2 successfully");
		docViewRedact.navigatingThroughPages();
		if (docViewRedact.redactionIcon().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep("The delete Redaction icon is not displayed while navigating from page navigation");
		} else {
			assertTrue(false);

		}
		Thread.sleep(4000);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).click();
		actions.moveToElement(docViewRedact.redactionForwardNavigate().getWebElement());
		actions.click().build().perform();
		Thread.sleep(4000);
		if (docViewRedact.deleteClick().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep("The delete Redaction icon is displayed while clicking on redacted area");
		} else {
			baseClass.failedStep("Delete redaction Icon is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52221 Description : Redact document as RMU and verify
	 * redaction appears on page after refresh
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 11)
	public void checkingRectangleRedactionWhileRefreshingPage() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		baseClass.stepInfo("Test case Id: RPMXCON-52221");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(-8, 10, 100, 200);
		baseClass.stepInfo("The Redaction Menu is Visible");
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("The rectangular redaction has been performed");
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		baseClass.passedStep("Page has been refreshed after applying redaction and Redaction is visible");
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52217 Description : Verifying remarks application on
	 * redacted area/page without deleting redaction
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 12)
	public void thisPageRedactionAlongWithRemark() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52217");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(20);
		actions.moveToElement(docViewRedact.thisPageRedaction().getWebElement());
		actions.click().build().perform();
		docViewRedact.selectingRectangleRedactionTag();
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
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		baseClass.passedStep("Verified Remarks and edited using RMU for redacted page");
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		if (docViewRedact.deleteRemarksBtn().isDisplayed()) {
			wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.deleteRemarksBtn().getWebElement()));
			docViewRedact.deleteRemarksBtn().Click();
			docViewRedact.confirmDeleteRemarksBtn().Click();
			baseClass.passedStep("Verified Remarks and delete using RMU without deleting the page redaction");
		} else {
			baseClass.passedStep("Verified Remarks");
		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52202 Description : Verifying that text redaction
	 * button is not avilable for audio docs
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 13)
	public void textRedactionWithAudioDocs() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52202");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 14)
	public void textRedactionCancleButtonVerification() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		baseClass.stepInfo("Test case Id: RPMXCON-52192, RPMXCON-52201");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	 * Author : Mohan date: NA Modified date: NA Modified by: Krishna D Test Case
	 * Id: RPMXCON-52170 Description : Verify that tool tip displayed on mouse over
	 * of 'Code same as last' when document having all page redaction in context of
	 * assignment
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyToolTipMouseOverCodeSameAsLast() throws Exception {
		String assignmentName = "assignmentA1" + Utility.dynamicNameAppender();
		int pureHitCount = 201;
		// Selecting Document from Session search
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52170");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search

		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");

		// Add reviewer and distribute docs and Select Assign and View in DocView
		assignmentPage.addReviewerAndDistributeDocs(assignmentName, pureHitCount);
		assignmentPage.editAssignment(assignmentName);
		docViewRedact.toggleCompletedRedactionTagEnabled();
		assignmentPage.SelectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Reviewer are added and doc distributed and Viewed in Docview successfully");

		// select docs from MiniDoclist
		docViewRedact.selectMiniDocListAndViewInDocView();
		baseClass.stepInfo("Doc is Selected from MiniDocList successfully");

		// Mini doc list having all page redaction
		docViewRedact.selectRedactionIconAndRedactWholePage();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		// MouseHover to CodeAsLastDoc Icon
		docViewRedact.mouseOverToCodeSameAsLastIcon();

		// pop-out child window
		docViewRedact.popOutCodingFormChildWindow();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		// MouseHover to CodeAsLastDoc Icon
		docViewRedact.mouseOverToCodeSameAsLastIcon();
		baseClass.passedStep(
				"'Code this document the same as the last coded document' is displayed when mouserhover to Code Same As Last doc successfully");
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52228 Description : Printing Redacted Doc Doc has
	 * bullet points and redaction applied at bullet points
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 16)
	public void printingDocWithBulletPoints() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52228");
		SessionSearch sessionsearch = new SessionSearch(driver);
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

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 17)
	public void persistentHitCheckAudioDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52188");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51871
	 * Verifying persistent hit for docs from assignment DocView- sprint 3
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 18)
	public void persistentHitCheckNonAudioDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51871");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.toggleEnableAnalyticsPanel();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewRedact.checkingPersistentHitPanel();
		docViewRedact.VerifyKeywordHitsinDoc();
		docViewRedact.forwardToLastDoc().Click();
		driver.waitForPageToBeReady();
		docViewRedact.VerifyKeywordHitsinDoc();
		docViewRedact.backwardToFirstDoc().Click();
		driver.waitForPageToBeReady();
		docViewRedact.VerifyKeywordHitsinDoc();
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51350
	 * Verifying persistent hit for audio docs as RMU when navigated from child
	 * window DocView- sprint 3
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 19)
	public void persistentHitCheckAudioDocsChildWindow() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51350");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanelAudio();
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.selectingDocFromMiniDocListChildWindow();
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51349
	 * Verifying persistent hit for audio docs as RMU when navigated from child
	 * window As PA DocView- sprint 3
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 19)
	public void persistentHitCheckAudioDocsChildWindowAsPA() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51349");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 20)
	public void VerifySavedTextRemark() throws Exception {
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51626 ");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
		Thread.sleep(Input.wait3);
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
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 21)
	public void persistentHitCheckNonAudioAsRev() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51764");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
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
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52216
	 * Verifying remarks without text is not possible DocView- sprint 3
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 22)
	public void textRemarksWithoutText() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52216");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch("ID00000012");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).clickAndHold()
				.moveByOffset(20, 10).release().build().perform();
		baseClass.stepInfo("blank area on the document has been selected");
		if (docViewRedact.addRemarksBtn().isElementAvailable(5)) {

			baseClass.passedStep("Remarks as RMU Can not be selected as text area is not selected");
		} else {
			baseClass.failedStep("Created Remarks as RMU");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52215
	 * Verifying only delete icon is not present when clicking on multipage rectange
	 * redaction DocView- sprint 3
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 23)
	public void verifyDeleteIconRectangleRedaction() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52215");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch("ID00000012");
		sessionsearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 150);
		docViewRedact.selectingRedactionTag("Default Redaction Tag");
		actions.moveToElement(docViewRedact.docViewNextPage().getWebElement());
		actions.click();
		actions.build().perform();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigaated to next page Successfully");
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(100, 150).release().build().perform();
		docViewRedact.selectingRedactionTag("Default Redaction Tag");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleClick().Visible() && docViewRedact.rectangleClick().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleClick().waitAndClick(3);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).click();
		actions.build().perform();
		if (docViewRedact.deleteClick().Displayed() && docViewRedact.deleteClick().Enabled()) {
			baseClass.passedStep("The delete Icon appears for the clicked rectangle redaction");
		}
		if (docViewRedact.RedactionTagSelectWhileNavigating().Displayed()
				&& docViewRedact.RedactionTagSelectWhileNavigating().Enabled()) {
			baseClass.passedStep("The redaction tag selection tab also appears along with the delete redaction option");
		} else {
			baseClass.failedStep("The redaction has not been selected or pop up did not appear");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 53421
	 * Verifying keyword is redacted Batch Redactions - sprint 3
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 24)
	public void verifyKeywordHighlitingAfterBatchRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53421");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String search = "Name1" + Utility.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		int purehit = sessionsearch.basicContentSearch("crammer");
		sessionsearch.saveSearch(search);
		BatchRedactionPage batch = new BatchRedactionPage(driver);
		// Verify Analyze Report and View Report
		driver.waitForPageToBeReady();
		batch.savedSearchBatchRedaction(search);
		// verify Popup Yes Button
		batch.getPopupYesBtn().Click();
		baseClass.stepInfo("Clicked Yes Button");
		// verify History status
		batch.verifyHistoryStatus2(search, purehit);
		SavedSearch savedsearch = new SavedSearch(driver);
		savedsearch.savedSearchToDocView(search);
		docViewRedact.checkinHighlitedText();
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52190
	 * Verifying while navigating from mini doclist child window, persistent hit
	 * panel stays open DocView-Sprint 4
	 */
	//@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 25)
	public void verifyingPersistenthitAudioPanel() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52190");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanelAudio();
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.selectingDocFromMiniDocListChildWindow();
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52191
	 * Verifying if text redaction option is present under RMU and Rev DocView-
	 * sprint 4
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 26)
	public void verifyTextRedactionOption() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52191");
// Verifying As RMU
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52212
	 * Verifying text remarks can not be added when no text is present in existing
	 * rectangular redaction DocView- sprint 4
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 27)
	public void textRemarksWithoutTextInRectangleRedaction() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52212");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdRemarks2);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 80);
		docViewRedact.selectingRedactionTag("Default Redaction Tag");
		baseClass.stepInfo("Rectangle redaction drawn in selected document without text and redaction Tag selected");

		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 50, 40).click();
		actions.build().perform();
		baseClass.stepInfo("The drawn redaction has been selected");
		docViewRedact.clickingRemarksIcon();
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		if (docViewRedact.addRemarksTextArea().Enabled() == true) {
			actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
			actions.click();
			actions.sendKeys("Remark by RMU");
			actions.build().perform();
			baseClass.failedStep("Created Remarks as RMU");
		} else {
			baseClass.passedStep("Remarks as RMU Can not be selected as text area is not selected");

		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51655
	 * Verifying doc is highlighted using this page and highlight is deleted
	 * successfully while refreshing page DocView- sprint 4
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 28)
	public void verifyHighlitingInDocView() throws Exception {
		Robot robot = new Robot();
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51655");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.clickingHighlitingIcon();
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		driver.waitForPageToBeReady();
		Thread.sleep(2000);
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
		if (docViewRedact.highliteDeleteBtn().isElementAvailable(5) == false) {
			docViewRedact.highliteDeleteBtn().Click();
			baseClass.failedStep("the highlite has not been deleted after refresh");
		} else {
			baseClass.passedStep("The highlite is not present on the page after deleting and refreshing");

		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52213
	 * Verifying remarks alone is deleted when remarks associated with Rectangular
	 * Redaction DocView/Redactions- sprint 4
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 29)
	public void textRemarksWithTextInRectangleRedaction() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52213");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.redactRectangleUsingOffset(0, 0, 50, 40);
		docViewRedact.selectingRedactionTag("Default Redaction Tag");
		baseClass.stepInfo("Rectangle redaction drawn in selected document without text and redaction Tag selected");
		docViewRedact.clickingRemarksIcon();
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 25, 20).click();
		actions.build().perform();
		baseClass.stepInfo("The drawn redaction has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.deleteRemarksBtn());
		docViewRedact.deleteRemarksBtn().Click();
		docViewRedact.confirmDeleteRemarksBtn().Click();
		baseClass.passedStep("Verified Remarks and delete using RMU without deleting the Rectangular redaction");
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51007 Verify thumbnails icon and check for doc view click
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 30)
	public void verifyThumbnailsPageNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51007");
		baseClass.stepInfo(
				"Verify on click of any of the thumbnail respective page should be displayed in doc view panel");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51008 Verify thumbnails in Doc View From assignments page
	 */

	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 31)
	public void checkingThumbnailsIconFromAssignment() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51008");
		baseClass.stepInfo(
				"Verify user can see the thumbnail image of each page of the document being viewed on doc view page in thumbnail panel when redirecting from assignment page");

		baseClass.stepInfo("Create new assignment");
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.docIdThumbnails);
		sessionSearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingThumbnailIcon();
		if (docViewRedact.thumbNailsPanel().isElementPresent() == true) {
			baseClass.passedStep("The thumbnails panel is clicked and menu is visible");
		} else {
			baseClass.failedStep("The thumbnail Panel menu is NOT displayed");
		}
		assignmentspage.deleteAssignment(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify that > and < arrows should work when the hit in the
	 * document is due to Keyword Group Highlights when redirected to doc view from
	 * basic search'RPMXCON-51441' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 32)
	public void verifyArrowsRedirdctedToDocViewFromBasicSearch() throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51441");
		baseClass.stepInfo(
				"Verify that > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		docViewRedact.performTheEyeIconHighLighting();

		baseClass.stepInfo(
				"That > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search is Successfully");
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		docViewRedact.performTheEyeIconHighLighting();

		baseClass.stepInfo(
				"That > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search is Successfully");
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify that the user can click on X at any state and bring it
	 * back to the original search icon (only) state'RPMXCON-51560' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 33)
	public void verifyClickXAtAnyStateAndBringItBackToOriginalSearchIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51560");
		baseClass.stepInfo(
				"Verify that the user can click on X at any state and bring it back to the original search icon (only) state");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	 * Author : Vijaya.Rani date: 7/12/21 NA Modified date: NA Modified by:NA
	 * Description :Needs to verify with out adding remarks should not
	 * save'RPMXCON-51625' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 34)
	public void verifyWithOutAddingRemarkShouldNotSave() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51625");
		baseClass.stepInfo("Needs to verify with out adding remarks should not save");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		Actions actions = new Actions(driver.getWebDriver());
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search with text input completed");
		sessionsearch.ViewInDocView();

		docViewRedact.performTheRemarkIconNotSave();
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(4000);
		// Thread sleep added for the page to adjust resolution
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
				.moveByOffset(200, 100).release().build().perform();
		baseClass.stepInfo("text for remarks has been selected");
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());

		actions.click();
		driver.waitForPageToBeReady();
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex1 = Color.fromString(color).asHex();
		System.out.println(hex1);
		baseClass.waitForElement(docViewRedact.getRemarkXBtn());

		docViewRedact.getRemarkXBtn().waitAndClick(30);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(30);
		driver.waitForPageToBeReady();
		try {
			String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex2 = Color.fromString(color2).asHex();
			System.out.println(hex2);
			if (hex1.equalsIgnoreCase(hex2)) {
				baseClass.failedStep("The color for the Highlighted text is same");
			}
		} catch (Exception e) {
			baseClass.passedStep("The Highlighted text is not Same");
		}
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 10/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that count should be updated if document is marked is
	 * Uncompleted 'RPMXCON-51031' Sprint : 7
	 * 
	 * @throws Exception
	 */
    @Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyCountShouldBeUpdatedDocumentIsMarkedIsUncompleted() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51031");
		baseClass
				.stepInfo("To verify that once document is marked as 'Un-Completed'assignment progress bar is updated");
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		driver.waitForPageToBeReady();
		System.out.println(assname);
		baseClass
				.stepInfo("Doc is Assigned from basic Search and Assignment '" + assname + "' is created Successfully");

		driver.waitForPageToBeReady();
		System.out.println(assname);
		docViewRedact.getHomeDashBoard();
		docViewRedact.selectAssignmentfromDashborad(assname);
		docViewRedact.performCompleteToDocs();
		docViewRedact.performGeerIcon();
		docViewRedact.performUnCompleteToDocs();
		docViewRedact.clickManage().waitAndClick(30);
		docViewRedact.manageAssignments().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.passedStep("The Documents is marked Uncompleted successfully");
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 10/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that once document is marked as 'Un-Completed',
	 * assignment progress bar is updated.'RPMXCON-51030' Sprint : 7
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
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
	 * :Verify the Images tab retain on document navigation when 'Show Default Tab'
	 * toggle is OFF at assignment level.'RPMXCON-51925' Sprint : 9
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyImageTabRetainOnDocsNavigation() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51925");
		baseClass.stepInfo(
				"Verify the Images tab retain on document navigation when 'Show Default Tab' toggle is OFF at assignment level");
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Step 2: Assignment should be created with  'Show Default Tab' toggle as OFF");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignNearDupeDocuments();

		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleDisableShowDefaultViewTab();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 3: Go to doc view in context of an assignment");
		assignmentsPage.selectAssignmentToViewinDocview(assname);

		baseClass.stepInfo("Step 4: Go to Images tab");
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		baseClass.stepInfo("Step 5: Click on the document navigation options");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.nearDupeDocId1);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(3);

		baseClass.waitForElement(docViewPage.getDocView_ImageTab_LastPageButton());
		docViewPage.getDocView_ImageTab_LastPageButton().waitAndClick(5);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep(
					"Image/production document for the navigated document is loaded on the Images tab and Images/production tab is retained as per the navigation");

		} else {
			baseClass.failedStep("Image tab is not retained");
		}

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

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 4: Go to Images tab");
		docViewPage.getDocView_ImagesTab().waitAndClick(5);

		baseClass.stepInfo("Step 5: Click on the document navigation options");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.nearDupeImageDoc);
		baseClass.waitForElement(docViewPage.getDocView_ImagesTab());
		docViewPage.getDocView_ImagesTab().waitAndClick(3);

		baseClass.waitForElement(docViewPage.getDocView_ImageTab_LastPageButton());
		docViewPage.getDocView_ImageTab_LastPageButton().waitAndClick(5);

		if (docViewPage.getDocView_ImagesTab().isDisplayed()) {
			baseClass.passedStep(
					"Image/production document for the navigated document is loaded on the Images tab and Images/production tab is retained as per the navigation");

		} else {
			baseClass.failedStep("Image tab is not retained");
		}
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
	@Test(enabled = true, groups = { "regression" }, priority = 38)
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
	@Test(enabled = true, groups = { "regression" }, priority = 39)
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
	@Test(enabled = true, groups = { "regression" }, priority = 40)
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
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyAllRelevantHitTermsOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-48783");
		baseClass.stepInfo(
				"Verify that all relevant hits should be displayed on persistent hits panel when navigated to doc view with saved search and keyword highlighting");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();

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
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyImageTabOnViewDocsFromAnalyticsPanel() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51917");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and view document from analytics panel then should be on Images tab of the viewed document");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

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
	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void verifyCompleteIconOnThreadMapOutsideOfAnAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51452");
		baseClass.stepInfo(
				"Verify that completed icon should not be displayed on thread map outside of an assignment which are completed from assignment");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

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
	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void verifyUserCanEnterTextAndSearchText() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51559");
		baseClass.stepInfo("Verify that the user can enter text (including long text) and search for the text");
		sessionSearch = new SessionSearch(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);

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
	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void verifyHitsOfDocsHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51325");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from saved search");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();

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
	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void verifyHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51323");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from basic search");
		sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);

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
	 * Author : Mohan date: 11/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify after impersonation all hits of the document should be highlighted
	 * without clicking the eye icon when user redirects to doc view from basic
	 * search.'RPMXCON-51326' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 47)
	public void verifyAfterImpersonationHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51326");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from basic search");
		sessionSearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
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
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation when Persistent Hit panel, Reviewer
	 * Remarks panel, Redactios menu, Highlights menu is selected and navigates to
	 * other doc from mini doc list child window previously selected selected
	 * panels/menus should remain. 'RPMXCON-51354' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51354");
		baseClass.stepInfo(
				"Verify after impersonation when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and navigates to other doc from mini doc list child window previously selected selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Impersonate RMU to Reviewer, search docs and Search for docs");
		baseClass.impersonateRMUtoReviewer();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonate SA to RMU, search docs and Search for docs");
		baseClass.impersonateSAtoRMU();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonate SA to Reviewer, search docs and Search for docs");
		baseClass.impersonateSAtoReviewer();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonate PAU to RMU, select assignment and go to Docview");
		baseClass.impersonatePAtoRMU();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonate PAU to Reviewer, select assignment and go to Docview");
		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify when Persistent Hit panel, Reviewer Remarks panel,
	 * Redactios menu, Highlights menu is selected and views document from analytics
	 * panel previously selected panels/menus should remain.. 'RPMXCON-51355' Sprint
	 * : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 49)
	public void verifyPersistentHitReviewerRemarksRedactiosHighlightsAanalytics() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51355");
		baseClass.stepInfo(
				"Verify when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected and views document from analytics panel previously selected panels/menus should remain.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();

		// perform AnalyticalPanel After earlier documents section
		docView.performDisplayIconReviewerHighlightAnalyticalPanel();

		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();

		// perform DocView page
		docViewRedact.performDisplayIconReviewerHighlight();

		// perform AnalyticalPanel After earlier documents section
		docView.performDisplayIconReviewerHighlightAnalyticalPanel();
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify search term, highlighted keywords should be displayed on
	 * click of the eye icon when redirected to doc view from session search.
	 * 'RPMXCON-51395' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 50)
	public void verifySearchTermHighlightedInEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51395");
		baseClass.stepInfo(
				"Verify search term, highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search");

		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);
		String hitTerms = "Test" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		// Go to docview via advanced search
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Manager with " + Input.pa1userName + "");

		// Go to docview via advanced search
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Go to docview via advanced search
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify search term, assigned keywords should be highlighted and
	 * should be displayed on click of the eye icon when redirected to doc view from
	 * assignment. 'RPMXCON-51397' Sprint : 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 51)
	public void verifyCreateAssignSearchTermHighlightedInEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51397");
		baseClass.stepInfo(
				"Verify search term, assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);
		String hitTerms = "Test" + Utility.dynamicNameAppender();

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		sessionsearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// color highlighting
		softAssertion.assertTrue(docViewRedact.get_textHighlightedColor().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// color highlighting
		softAssertion.assertTrue(docViewRedact.get_textHighlightedColor().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");

		docView.getPersistentHit(hitTerms);
		docView.verifyPersistentHitPanelAndCount(hitTerms);
		loginPage.logout();
	}

	@Test(enabled = true, groups = { "regression" }, priority = 52)

	public void VerifyRMUToDocView() throws Exception {
		String expectedURL = Input.url + "DocumentViewer/DocView";
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();

		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);
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
	 * Author : Vijaya.Rani date: 12/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that previously saved Persistent hits should be displayed
	 * on the doc view when documents are uncompleted from edit assignment.
	 * 'RPMXCON-51775' Sprint: 10
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 53)
	public void verifyPersistentHitDisplayInUncompleteDocEditAssign() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51775");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits should be displayed on the doc view when documents are uncompleted from edit assignment.");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);

		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String BasicSearchName = "Savebtn" + Utility.dynamicNameAppender();
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		// Basic Search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(BasicSearchName);
		savedSearch.SaveSearchToBulkAssign(BasicSearchName, assname, codingForm, SessionSearch.pureHit);
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		// Select the Assignment from dashboard
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		// eye Icon
		docViewRedact.getDocView_MiniDoc_Selectdoc(1).waitAndClick(20);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.passedStep("EyeIcon Clicked Successfully");
		// edit coding form
		docView.editCodingFormComplete();
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		assignmentsPage.VerifyUnCompleteDoc(assname);
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		// eye Icon
		docViewRedact.getDocView_MiniDoc_Selectdoc(1).waitAndClick(20);
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 54)
	public void verifyDocCount_ABM() throws Exception {
		String SaveSaerchName = "ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();

		UtilityLog.info("Logged in as RMU User: " + Input.rmu1userName);
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
	@Test(enabled = true, groups = { "regression" }, priority = 55)
	public void verifyAfterImpersonationHitsOfDocsWithoutSaveQueryHighlightedWithoutClickingEyeIconFromAdvanceSearch()
			throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51327");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from advance search");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

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
	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void verifyAfterImpersonateHitsOfDocsHighlightedWithoutClickingEyeIcon() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51328");
		baseClass.stepInfo(
				"Verify after impersonation all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from saved search");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();

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
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void verifyHitsOfDocsHighlightedWithoutClickingEyeIconBasicSearchDocList() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51329");
		baseClass.stepInfo(
				"Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from saved search");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		DocListPage docListPage = new DocListPage(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();

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
	 * Author : Mohan date: 26/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify Persistent KW Groups as well as Saved Searching on doc
	 * view.'RPMXCON-51553'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void verifyKWGroupAndSavedSeacrhOnDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51553");
		baseClass.stepInfo("Verify Persistent KW Groups as well as Saved Searching on doc view");
		sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		docView = new DocViewPage(driver);
		String saveName = "savedSearch0101" + Utility.dynamicNameAppender();
		String panelText = "basis)";
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(panelText).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Hit count for the matching keywords is displayed against the keywords on persistent hit panel");

		loginPage.logout();

		// login As PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(panelText).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Hit count for the matching keywords is displayed against the keywords on persistent hit panel");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 2: Search for documents with search term and save the search");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchQuery(saveName);

		baseClass.stepInfo("Step 3: From Saved Search select the search and action as Doc View   ");
		savedSearch.savedSearchToDocView(saveName);

		baseClass.stepInfo("Step 4: Verify the persistent hit panel from doc view");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(panelText).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep(
				"Hit count for the matching keywords is displayed against the keywords on persistent hit panel");
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 27/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify Search Term highlighting is working for Searchable PDF (with Mapped
	 * dataset having RequiredPDFGenartion is TRUE)'RPMXCON-51981'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 59)
	public void verifySearchTermHighlightingWorkingForSearchablePDF() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51981");
		baseClass.stepInfo(
				"Verify Search Term highlighting is working for Searchable PDF (with Mapped dataset having RequiredPDFGenartion is TRUE)");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();

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
	 * Author : Mohan date: 27/01/22 NA Modified date: NA Modified by:NA Description
	 * :Verify Keyword highlighting is working for Searchable PDF (with Mapped
	 * dataset having RequiredPDFGenartion is TRUE)'RPMXCON-51982'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 60)
	public void verifyKeywordHighlightingWorkingForSearchablePDF() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51981");
		baseClass.stepInfo(
				"Verify Search Term highlighting is working for Searchable PDF (with Mapped dataset having RequiredPDFGenartion is TRUE)");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);
		softAssertion = new SoftAssert();
		String keyword = "to" + Utility.dynamicNameAppender();
		String color = "Gold";

		baseClass.stepInfo("Step 1: Prerequisites: Keyword groups should be created   with different Keywords");

		keywordPage.addKeywordWithColor(keyword, color);

		baseClass.stepInfo("Step 2:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 3: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");

		loginPage.logout();

		// login As PA
		baseClass.stepInfo("Step 1: Login As PA");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");

		baseClass.stepInfo("Step 2:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 3: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");

		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo(
				"User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");

		baseClass.stepInfo("Step 3:  Go to Basic/Advanced Search Search by term   Go to Doc View and ");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo(
				"Step 4: Click the eye icon to see the persistent hits and verify the keyword and persistent hit highlighting");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);

		softAssertion.assertTrue(docView.getDocView_PersistanceHit_PanelText(keyword).isDisplayed());
		softAssertion.assertAll();
		baseClass.passedStep("Keyword is highlighted with specified color in the Doc View successfully");
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
	@Test(enabled = true, groups = { "regression" }, priority = 61)
	public void verifyAfterImpersonationAllMenusInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51356");
		baseClass.stepInfo(
				"Verify after impersonation when Highlighting, Persistent Hit panel, Reviewer Remarks panel, Redactios menu is selected from doc view and views other document from analytics panel previously selected panels/menus should remain.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);

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
	 * Author : Vijaya.Rani date: 26/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify waning message is prompted to the user after
	 * impersonation when user navigates away from the page without saving action
	 * from doc view.'RPMXCON-50923' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 62)
	public void verifyAfterImpersonationNavigatePageSavingFromDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50923");
		baseClass.stepInfo(
				"Verify waning message is prompted to the user after impersonation when user navigates away from the page without saving action from doc view.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.logout();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignThreadedDocs();
		baseClass.stepInfo("Search with text input for docs completed");
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.add3ReviewerAndDistribute();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.selectDocsFromThreadMapTabAndActionCodeSame();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rmu1userName + "");
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Step 3: Select document and click code Same As");
		docView.performCodeSameForFamilyMembersDocuments();
		docView.performConfirmNavigationDisplay();
		loginPage.logout();
		}
	

	/**
	 * Author : Mohan date: 28/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify user can see the associated tiff images on clicking of the images tab'RPMXCON-51105' 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 63)
	public void verifyAssociatedTiffImage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51105");
		baseClass.stepInfo(
				"Verify user can see the associated tiff images on clicking of the images tab");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		String dropDownValue = "DocFileExtension";
		
		
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
		baseClass.passedStep("Associated images is displayed on click of Images tab.Pagination is loaded for the multiple images");
		
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
		baseClass.passedStep("Associated images is displayed on click of Images tab.Pagination is loaded for the multiple images");
		
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
		baseClass.passedStep("Associated images is displayed on click of Images tab.Pagination is loaded for the multiple images");
		loginPage.logout();
		
	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation user can load the produced document
	 * by clicking the drop down selection.'RPMXCON-51271' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 64)
	public void verifyAfterImpersonationProducedDocsByDropDownSelection() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51271");
		baseClass.stepInfo(
				"Verify after impersonation user can load the produced document by clicking the drop down selection.");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		UtilityLog.info(Input.prodPath);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionsearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		driver.waitForPageToBeReady();
		if(docView.getDocView_MiniDocList_Docs().Displayed()) {
			baseClass.passedStep("Completed Proceded Document is Displayed Successfully");
		}
		else {
			baseClass.failedStep("Completed Proceded Document is Not Displayed");
			
		}
		loginPage.logout();
		
	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify after impersonation user can see the production option in
	 * the drop down selection of Images tab when document generated as part of
	 * production.'RPMXCON-51270' Sprint: 11
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 65)
	public void verifyAfterImpersonationProducedDocsInImageTabDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51270");
		baseClass.stepInfo(
				"Verify after impersonation user can see the production option in the drop down selection of Images tab when document generated as part of production.");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		UtilityLog.info(Input.prodPath);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as System Assisent with " + Input.sa1userName + "");
		baseClass.stepInfo(" Impersonating SA to PA");
		baseClass.impersonateSAtoPA();
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		driver.waitForPageToBeReady();
		if(docView.getDocView_MiniDocList_Docs().Displayed()) {
			baseClass.passedStep("Completed Proceded Document is Displayed Successfully");
		}
		else {
			baseClass.failedStep("Completed Proceded Document is Not Displayed");
			
		}
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document. 'RPMXCON-51272' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 66)
	public void verifyAssignmentProgressBarCompleteDocs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51272");
		baseClass.stepInfo("Verify assignment progress bar refreshesh after completing the document.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.toggleEnableSaveWithoutCompletion();
		assignmentsPage.add2ReviewerAndDistribute();

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

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

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		assignmentsPage.SelectAssignmentByReviewer(assname);

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
	 * Author : Vijaya.Rani date: 31/01/22 NA Modified date: NA Modified by:NA
	 * Description :To verify that user can view the folders which is associated to
	 * the security group only. 'RPMXCON-50820' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 67)
	public void veriyUserCanSeeTheFlodersSecurityGroup() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-50820");
		baseClass.stepInfo("To verify that user can view the folders which is associated to the security group only.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList();
		driver.waitForPageToBeReady();
		if (docView.getDocView_AnalyticsExitingFolderName().Displayed()) {
			baseClass.passedStep("All existing folder under that security group is displayed");
		} else {
			baseClass.failedStep("All existing folder under that security group is not displayed");
		}
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");
		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocListForReviewer();
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");
		baseClass.stepInfo("Step 1: Search for the doc and View In Doc View");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docView.performFloderMiniDocList();
		driver.waitForPageToBeReady();
		if (docView.getDocView_AnalyticsExitingFolderName().Displayed()) {
			baseClass.passedStep("All existing folder under that security group is displayed");
		} else {
			baseClass.failedStep("All existing folder under that security group is not displayed");
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
	@Test(enabled = true, groups = { "regression" }, priority = 68)
	public void verifyAssignmentProgressBarCodeSameAsAction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51274");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document on selecting code same as this action.");

		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
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
	 * Author : Vijaya.Rani date: 1/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after applying coding
	 * stamp. 'RPMXCON-51276' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 69)
	public void verifyAssignmentProgressBarCompleteTheCodingStamp() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51276");
		baseClass.stepInfo("Verify assignment progress bar refreshesh after applying coding stamp.");

		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		docView = new DocViewPage(driver);
		softAssertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

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
	 * Author : Vijaya.Rani date: 02/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify assignment progress bar refreshesh after completing the
	 * document same as last prior documents should be completed same as last on
	 * selecting code same as this action. 'RPMXCON-51279' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 70)
	public void verifyAssignmentProgressBarCompleteDocsCodeSameAs() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51279");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document same as last prior documents should be completed same as last on selecting code same as this action.");

		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1: Search for the doc and assignment is created");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);

		baseClass.stepInfo("Step 2: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();

		assignmentsPage.SelectAssignmentByReviewer(assname);

		docView.completeButton();
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
		docViewRedact.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		docView.completeButton();
		docView.clickCodeSameAsLast();
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
		for (int i = 4; i <= 5; i++) {
			baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(i));
			docView.getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
		}
		baseClass.waitForElement(docView.getDocView_Mini_ActionButton());
		docView.getDocView_Mini_ActionButton().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView__ChildWindow_Mini_CodeSameAs());
		docView.getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(10);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(5);
		docView.completeButton();
		driver.waitForPageToBeReady();
		docView.clickCodeSameAsLast();
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
		docView.completeButton();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(2).waitAndClick(5);
		docView.completeButton();
		docView.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");

		baseClass.stepInfo("Step 5: Search the documents with search term from basic search and go to doc view");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		for (int i = 4; i <= 5; i++) {
			baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(i));
			docView.getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
		}
		baseClass.waitForElement(docView.getDocView_Mini_ActionButton());
		docView.getDocView_Mini_ActionButton().waitAndClick(10);
		baseClass.waitForElement(docView.getDocView__ChildWindow_Mini_CodeSameAs());
		docView.getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(10);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		driver.waitForPageToBeReady();
		docViewRedact.getDocView_MiniDoc_Selectdoc(6).waitAndClick(5);
		docView.completeButton();
		driver.waitForPageToBeReady();
		docView.clickCodeSameAsLast();
		driver.scrollPageToTop();
		baseClass.waitForElement(docView.getDashboardButton());
		docView.getDashboardButton().Click();
		softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());
		baseClass.passedStep("Assignment progress bar refreshed on completed doc");
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
	@Test(enabled = true, groups = { "regression" }, priority = 71)
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
	 * Author : Vijaya.Rani date: 03/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that user should be able to edit an applied redaction and change the redaction 
	 * tag that was applied automatically. 'RPMXCON-46958' Sprint : 11
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 72)
	public void verifyEditAndApplyRedactionTag() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-46958");
		baseClass.stepInfo(
				"Verify that user should be able to edit an applied redaction and change the redaction tag that was applied automatically.");
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
	
		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		baseClass.stepInfo("Step 1:Search and Go to docView");
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search with text input is completed");
		sessionSearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		docViewRedact.selectingRectangleRedactionTag();
		sessionSearch.ViewInDocView();
		docView.redactionIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleClick().Visible() && docViewRedact.rectangleClick().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleClick().waitAndClick(8);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).click();
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveClick().getWebElement());
		actions.click();
		actions.build().perform();
		baseClass.VerifySuccessMessage("Redaction tags saved successfully");
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag Saved successfully");
		loginPage.logout();

		// login As Reviewer
		baseClass.stepInfo("Step 1: Login As Reviewer");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");
		baseClass.stepInfo("Step 1:Search and Go to docView");
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Search with text input is completed");
		sessionSearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		docViewRedact.selectingRectangleRedactionTag();
		sessionSearch.ViewInDocView();
		docView.redactionIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleClick().Visible() && docViewRedact.rectangleClick().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleClick().waitAndClick(8);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).click();
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveClick().getWebElement());
		actions.click();
		actions.build().perform();
		baseClass.VerifySuccessMessage("Redaction tags saved successfully");
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag Saved successfully");
	}
	/**
	 * Author : Aathith date: 04/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify that large Excels are loading properly without any issues in DocView.. '
	 * 				RPMXCON-52203' Sprint : 11
	 * @throws AWTException
	 * @throws Exception
	 * 
	 * testcase hint : used project :AutomationAdditionalDataProject , Runs On : PT env
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 73)
	public void verifyLargeExcelInDocView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52203");
		baseClass.stepInfo("Verify that large Excels are loading properly without any issues in DocView.");

		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
		
		sessionSearch.MetaDataSearchInAdvancedSearch("DocID", "ID00000006");
		baseClass.stepInfo("'.xls' large file is searched in section search");
		
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Document is viewed in Doc view action performed");
		
		driver.waitForPageToBeReady();
		boolean flag =((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState").equals("complete");
		if (flag)
        { 
			softAssertion.assertTrue(flag);
			baseClass.passedStep("Large Excels should load properly without any issues in DocView.");
        }else {
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
		boolean flag1 =((JavascriptExecutor) driver.getWebDriver()).executeScript("return document.readyState").equals("complete");
		if (flag1)
        { 
			softAssertion.assertTrue(flag1);
			baseClass.passedStep("Large Excels should load properly without any issues in DocView.");
        }else {
        	baseClass.failedStep("verification failed");
        }
		baseClass.passedStep("REV user can able to view Docview large excel file without any issue.");
		
		loginPage.logout();
		
		baseClass.passedStep("Verified that large Excels are loading properly without any issues in DocView.");
	}
	/**
	 * @Author : Aathith Modified date: NA Modified by: Aathith
	 *         RPMXCON-50786
	 * @Description : To verify document should be displayed in doc view panel as per the entered document number in the inputbox.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 74)
	public void verifyDocumentAsPerEnteredDocument() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50786");
		baseClass.stepInfo("To verify document should be displayed in doc view panel as per the entered document number in the inputbox.");

		
		DocViewPage docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		
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
		
		baseClass.passedStep("verified document should be displayed in doc view panel as per the entered document number in the inputbox.");
		loginPage.logout();
	}
	
	/**
	 *
	 * @Author : Brundha
	 * @Testcase id : 49988 - Verify that after deletion of the last saved redaction, last saved redaction tag should be selected automatically from redaction pop up
	 */
	@Test(groups = { "regression" },priority = 75)
	public void verifyRedactionTagInPopUpDropDown() throws Exception {
		 BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49988 from Docview/redaction Component");
		baseClass.stepInfo("Verify that after deletion of the last saved redaction, last saved redaction tag should be selected automatically from redaction pop up");

		
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
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);
		driver.scrollPageToTop();
		docViewRedactions.selectDoc1();
		
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10, 100, 120);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10, 100, 130);
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
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		docViewRedactions.rectangleRedactionTagSelect().isDisplayed();
		Select select = new Select(docViewRedactions.rectangleRedactionTagSelect().getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the value is "+option);
		if(option.equals(Redactiontag1)) {
			baseClass.passedStep("Last saved redaction tag is selected automatically from redaction pop up");
		}else{
			baseClass.failedStep("Last saved redaction tag is not  selected automatically from redaction pop up");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author : Vijaya.Rani date: 05/02/22 NA Modified date: NA Modified by:NA
	 * Description :User can load the produced document by clicking the drop down
	 * selection.'RPMXCON-51269' Sprint: 12
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 76)
	public void verifyProducedDocumentClickingDropDownSelection() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-51269");
		baseClass.stepInfo("User can load the produced document by clicking the drop down selection.");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		savedSearch = new SavedSearch(driver);
		UtilityLog.info(Input.prodPath);

		// Login as RMU
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		baseClass.passedStep("Document produced the clicked production is loaded successfully");
		loginPage.logout();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Project Assisent with " + Input.pa1userName + "");

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		baseClass.passedStep("Document produced the clicked production is loaded successfully");
		loginPage.logout();

		// Login as REVU
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer Manager with " + Input.rev1userName + "");

		baseClass.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		docView.clickOnImageTab();
		driver.waitForPageToBeReady();
		docView.verifyProductionNameForPDFFileInDocView(productionname);
		baseClass.passedStep("Document produced the clicked production is loaded successfully");
		
	}

	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEV & DOCVIEW/REDACTIONS EXECUTED******");

	}

}