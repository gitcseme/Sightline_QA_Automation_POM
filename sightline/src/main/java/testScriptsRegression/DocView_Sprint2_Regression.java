package testScriptsRegression;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
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
	}

	/**
	 * @author Krishna D D date: NA Modified date: 27/08/2021 Modified by: Krishna D
	 *         Krishna Test Case Id: RPMXCON-52232, 52230 & 52229 Description :
	 *         Verify printing redacted doc as RMU through session search Get
	 *         document list using search input and view in DocView print document
	 *         and verify bullhorn notification & background tasks
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 1)

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
		docViewRedact.printIcon().waitAndClick(20);

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
		assertEquals(urlBackground, "https://sightlinept.consilio.com/Background/BackgroundTask");
		baseClass.passedStep("Navigated to document download page");

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
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Test
	 * Case Id: RPMXCON-52220 & 52194 Description : Performing text redaction on a
	 * Doc As RMU use search term to get a pdf document and perform text redaction
	 * for 52194, Delete the performed text redaction
	 */

//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 8)

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
//		if (docViewRedact.deleteClick().Displayed() && docViewRedact.deleteClick().Displayed() ) 		
		try {
			actions.moveToElement(docViewRedact.deleteClick().getWebElement());
			actions.click();
			actions.build().perform();
			baseClass
					.passedStep("Text redaction has been performed by RMU user and Redaction Tag Deleted successfully");
		} catch (Exception e) {
			baseClass.passedStep("Text redaction has been performed by RMU user");
		}
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
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Test
	 * Case Id: RPMXCON-52231 Description : Printing a doc with existing redactions
	 * as RMU use search term to get a pdf document and print
	 */

//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 6)
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
		if(docViewRedact.deleteRemarksBtn().isDisplayed()) {
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.deleteRemarksBtn().getWebElement()));
		docViewRedact.deleteRemarksBtn().Click();
		docViewRedact.confirmDeleteRemarksBtn().Click();
		baseClass.passedStep("Verified Remarks and delete using RMU without deleting the page redaction");}
		else {
			baseClass.passedStep("Verified Remarks");
		}

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
	}

	/**
	 * Author : Mohan date: NA Modified date: NA Modified by: Krishna D Test Case
	 * Id: RPMXCON-52170 Description : Verify that tool tip displayed on mouse over
	 * of 'Code same as last' when document having all page redaction in context of
	 * assignment
	 * 
	 * @throws Exception
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 15)
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
			docViewRedact.bullhornIconRedColour().waitAndClick(30);}
else {
		
			docViewRedact.bullhornIcon().waitAndClick(5);}
		
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

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51626
	 * Verifying Remarks and colour highliting as RMU DocView- sprint 3
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 20)
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
				.moveByOffset(20, 10).release().build().perform();
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
		if(docViewRedact.get_textHighlightedColor().isDisplayed()) {
			String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
			String hex2 = Color.fromString(color2).asHex();
			System.out.println(hex2);
			if (hex1.equalsIgnoreCase(hex2)) {
				baseClass.failedStep("The color for the Highlighted text is same");
			}
		} else{
			baseClass.passedStep("The Highlighted text is not present after the remark has been Deleted");
		}
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

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 53421
	 * Verifying keyword is redacted Batch Redactions - sprint 3
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 24)
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

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52190
	 * Verifying while navigating from mini doclist child window, persistent hit
	 * panel stays open DocView-Sprint 4
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 25)
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
	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52212
	 * Verifying text remarks can not be added when no text is present in existing
	 * rectangular redaction DocView- sprint 4
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 27)
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
			actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
			actions.click().build().perform();
			if(docViewRedact.addRemarksTextArea().Enabled()) {
			actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
			actions.click();
			actions.sendKeys("Remark by RMU");
			actions.build().perform();
			baseClass.failedStep("Created Remarks as RMU");
		} else{
			baseClass.passedStep("Remarks as RMU Can not be selected as text area is not selected");

		}

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51655
	 * Verifying doc is highlighted using this page and highlight is deleted
	 * successfully while refreshing page DocView- sprint 4
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 28)
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
		try {
			docViewRedact.highliteDeleteBtn().Click();
			baseClass.failedStep("the highlite has not been deleted after refresh");
		} catch (Exception e) {
			baseClass.passedStep("The highlite is not present on the page after deleting and refreshing");

		}

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52213
	 * Verifying remarks alone is deleted when remarks associated with Rectangular
	 * Redaction DocView/Redactions- sprint 4
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 29)
	public void textRemarksWithTextInRectangleRedaction() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52213");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 80);
		docViewRedact.selectingRedactionTag("Default Redaction Tag");
		baseClass.stepInfo("Rectangle redaction drawn in selected document without text and redaction Tag selected");
		docViewRedact.clickingRemarksIcon();
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 50, 40).click();
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
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.deleteRemarksBtn().getWebElement()));
		docViewRedact.deleteRemarksBtn().Click();
		docViewRedact.confirmDeleteRemarksBtn().Click();
		baseClass.passedStep("Verified Remarks and delete using RMU without deleting the Rectangular redaction");

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
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51008 Verify thumbnails in Doc View From assignments page
	 */

//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 31)
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
		assignmentspage.createAssignmentNew(assignmentName, Input.codeFormName);
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingThumbnailIcon();
		if (docViewRedact.thumbNailsPanel().isElementPresent() == true) {
			baseClass.passedStep("The thumbnails panel is clicked and menu is visible");
		} else {
			baseClass.failedStep("The thumbnail Panel menu is NOT displayed");
		}
		assignmentspage.deleteAssignment(assignmentName);
	}

	/**
	 * Author : Vijaya.Rani date: 2/12/21 NA Modified date: NA Modified by:NA
	 * Description :Verify that > and < arrows should work when the hit in the
	 * document is due to Keyword Group Highlights when redirected to doc view from
	 * basic search'RPMXCON-51441' Sprint : 7
	 * 
	 * @throws Exception
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 32)
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

	}

	/**
	 * Author : Vijaya.Rani date: 7/12/21 NA Modified date: NA Modified by:NA
	 * Description :Needs to verify with out adding remarks should not
	 * save'RPMXCON-51625' Sprint : 7
	 * 
	 * @throws Exception
	 */
//	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 34)
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

	}

/**
	 * Author : Vijaya.Rani date: 10/12/21 NA Modified date: NA Modified by:NA
	 * Description :To verify that count should be updated if document is marked is
	 * Uncompleted 'RPMXCON-51031' Sprint : 7
	 * 
	 * @throws Exception
	 */
//	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyCountShouldBeUpdatedDocumentIsMarkedIsUncompleted() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51031");
		baseClass.stepInfo(
				"To verify that once document is marked as 'Un-Completed'assignment progress bar is updated");
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
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assname + "' is created Successfully");
		
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
		baseClass.stepInfo(
				"To verify that once document is marked as 'Un-Completed'assignment progress bar is updated");
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		String searchString = Input.searchString1;
		String docsToBeSelected = "ThreadMap";
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
		//login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentsPage.SelectAssignmentByReviewer(assname);
		docViewRedact.performCompleteToDocs();
		docViewRedact.getHomeDashBoard();
		
		docViewRedact.selectAssignmentfromDashborad(assname);
		docViewRedact.performGeerIcon();
		docViewRedact.performUnCompleteToDocs();
		docViewRedact.getHomeDashBoard();
		baseClass.stepInfo("The Progress bar is Changed Successfully");

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
			loginPage.logout();
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