package sightline.docviewRedactions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Redactions_Regression1 {

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
	 * Author : Krishna D date: NA Modified date: 24/08/2021 Modified by: Krishna D
	 * Test Case Id: RPMXCON-52321 & RPMXCON-52322 Verifying blank Redaction Login
	 * as RMU Go to Search Search any term drag the result to shopping cart and
	 * select action as 'View in DocView' user to view in DocView from "Actions"
	 * Click on redaction and multipage tab
	 */

	@Test(description = "RPMXCON-52321,RPMXCON-52322", enabled = true, alwaysRun = true, groups = {
			"regression" })

	public void verifyBlankRedactionTag() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
// Adding rectangular redaction and saving-modified on 24/08/2021
		baseClass.stepInfo("Test case Id: RPMXCON-52321, RPMXCON-52322");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageIcon().Visible() && docViewRedact.multiPageIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageIcon().waitAndClick(20);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		docViewRedact.selectingMultiplePagesForRedaction();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
//modified on 24/08/21
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(20);
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("The rectangular redaction has been performed");

// deleting redactions While navigating forward and backward
		docViewRedact.navigatingThroughRedactions();
		if (docViewRedact.redactionIcon().Displayed()) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: 24/08/21 Modified by: Krishna D
	 * Krishna Test Case Id RPMXCON-52318 Description Saving Multipage Redaction
	 * Login as RMU Go to Search Search any term drag the result to shopping cart
	 * and select action as 'View in DocView' user to view in DocView from "Actions"
	 * Click on redaction and multipage tab
	 */

	@Test(description = "RPMXCON-52318", enabled = true, alwaysRun = true, groups = { "regression" })
	public void saveMultiPageRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52318");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in Doc View");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);

// Clicking Redaction and Multipage icon

		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		if (docViewRedact.multiPageIcon().Displayed()) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		baseClass.passedStep("The redaction icon and multipage icon are sucessfully operated");
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id RPMXCON-52320 Description : Verify documents for Redaction and
	 * Multipage when user Selects "documents" from DocExplorer user to view in
	 * DocView from "Actions" Click on redaction and multipage tab Multipage pop up
	 * enabled
	 */
	@Test(description = "RPMXCON-52320", enabled = true, alwaysRun = true, groups = { "regression" })
	public void testShareSteps() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52320");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in Doc View");
// Clicking Redaction and Multipage icons

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);
		baseClass.stepInfo("The Redaction Icon is clicked successfully");
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.multiPageIcon().getWebElement()));
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement());
		actions.click().build().perform();
		baseClass.passedStep("The multipage icon is clicked and multi page pop up appears");

// Cheking if multipage popup is enabled

		if (docViewRedact.multiPagePopUp().getWebElement().isDisplayed()) {
			assertTrue(true);
		} else {
			assertFalse(false);
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna D date: NA Modified date: 24/08/2021 by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52255 D Description : DocView Redactions Panel Checking
	 * if Redaction menu is avilable
	 */

	@Test(description = "RPMXCON-52255",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocViewRedactionPanel() throws Exception {

		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
//Modified by Krishna for optimising and in allignment with new project
		baseClass.stepInfo("Test case Id: RPMXCON-52255");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();

// Verifying in DocView page- modified 24/8/21

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(20);
		actions.click().build().perform();
		baseClass.stepInfo("The Redaction Menu is Visible");
		baseClass.passedStep("******The Redaction Menu is Visible******");
		actions.moveToElement(docViewRedact.redactionIcon().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions
				.elementToBeClickable(docViewRedact.selectingSeconddataFromMiniDocList().getWebElement()));
		docViewRedact.selectingSeconddataFromMiniDocList().getWebElement().click();
		driver.waitForPageToBeReady();
		docViewRedact.doubleClickRedactionBtn();
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.nextDocViewBtn().getWebElement()));
		docViewRedact.nextDocViewBtn().waitAndClick(5);
		docViewRedact.doubleClickRedactionBtn();
		baseClass.waitTillElemetToBeClickable(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().waitAndClick(5);
		baseClass.waitTillElemetToBeClickable(docViewRedact.redactionIcon());
		docViewRedact.doubleClickRedactionBtn();
		baseClass.waitTillElemetToBeClickable(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().waitAndClick(5);
		baseClass.waitTillElemetToBeClickable(docViewRedact.redactionIcon());
		docViewRedact.doubleClickRedactionBtn();
		baseClass.waitTillElemetToBeClickable(docViewRedact.previousDocViewBtn());
		docViewRedact.previousDocViewBtn().waitAndClick(5);
// Modified for new project-24/08/21
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.pageNumberTextBox().Visible() && docViewRedact.pageNumberTextBox().Enabled();
			}
		}), Input.wait30);
		docViewRedact.pageNumberTextBox().waitAndClick(10);
		docViewRedact.pageNumberTextBox().getWebElement().clear();
		docViewRedact.pageNumberTextBox().getWebElement().sendKeys(Input.pageNumber);
		driver.waitForPageToBeReady();
		docViewRedact.doubleClickRedactionBtn();
		baseClass.stepInfo("The enabling and disabling of Redaction Icon");
		docViewRedact.historyDrowDownBtn().getWebElement().click();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		baseClass.passedStep("Document has been viewed from history dropdown");
		if (docViewRedact.historyDrowDownBtn().Displayed()) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id RPMXCON-52254 Description: checking for forward and backward
	 * navigation of docs while redacting
	 */

	@Test(description = "RPMXCON-52254",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionPanel() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON 52254");

		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());

// Search with text input	
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchText);
		baseClass.stepInfo("Search with text input --test-- completed");
		sessionsearch.ViewInDocView();

// Verifying in DocView page
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(30);
		baseClass.stepInfo("The Redaction Menu is Visible");
		baseClass.passedStep("The Redaction Menu is Visible");
		actions.moveToElement(docViewRedact.redactionIcon().getWebElement());
		actions.click().build().perform();
		wait.until(ExpectedConditions
				.elementToBeClickable(docViewRedact.selectingSeconddataFromMiniDocList().getWebElement()));
		docViewRedact.selectingSeconddataFromMiniDocList().getWebElement().click();
		driver.waitForPageToBeReady();
		docViewRedact.doubleClickRedactionBtn();
		driver.scrollPageToTop();
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.nextDocViewBtn().getWebElement()));
		docViewRedact.nextDocViewBtn().getWebElement().click();

// Activating and deactivating the redaction button & Navigating to next and previous Documents

		docViewRedact.doubleClickRedactionBtn();
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardNextDocBtn());
		actions.moveToElement(docViewRedact.forwardNextDocBtn().getWebElement());
		actions.click().build().perform();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigaated to next document Successfully");
		baseClass.waitTillElemetToBeClickable(docViewRedact.nextDocViewBtn());
		docViewRedact.nextDocViewBtn().getWebElement().click();
		driver.waitForPageToBeReady();
		docViewRedact.doubleClickRedactionBtn();
		docViewRedact.nextDocViewBtn().getWebElement().click();
		driver.waitForPageToBeReady();
		docViewRedact.doubleClickRedactionBtn();
		driver.waitForPageToBeReady();
		docViewRedact.previousDocViewBtn().getWebElement().click();
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(docViewRedact.pageNumberTextBox());
		docViewRedact.pageNumberTextBox().getWebElement().click();
		docViewRedact.pageNumberTextBox().getWebElement().clear();
		docViewRedact.pageNumberTextBox().getWebElement().sendKeys(Input.pageNumber);
		driver.waitForPageToBeReady();
		docViewRedact.doubleClickRedactionBtn();
		baseClass.stepInfo("The enabling and disabling of Redaction Icon is complete");
		docViewRedact.historyDrowDownBtn().getWebElement().click();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		if (docViewRedact.historyDrowDownBtn().Displayed() && docViewRedact.historyDrowDownBtn().Enabled()) {
			assertTrue(true);
			baseClass.passedStep("Document has been viewed from history dropdown");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * @author Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 *         Test Case Id: RPMXCON-52222 Component: DocView/Redactions Description
	 *         : Verify that user should be able to apply rectangle redaction
	 *         Redaction tag with uncommon click path (F5)
	 */
	@Test(description = "RPMXCON-52222",enabled = true, alwaysRun = true, groups = { "regression" })

	public void verifyRectangularRedactionRMU() throws Exception {
// Selecting Document from DocExplorer
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();

		baseClass.stepInfo("Test case id : RPMXCON-52222");
		baseClass.stepInfo(
				"Verify the page syntax user should be able to enter against Pages in multi page redactions pop up");

		// Performing basic search  ID00000230
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("ID00000230");

		// Adding search results and view in docview
		sessionSearch.ViewInDocView();

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
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52221 Description : Redact document as RMU and verify
	 * redaction appears on page after refresh
	 */

	@Test(description = "RPMXCON-52221",enabled = true, alwaysRun = true, groups = { "regression" })
	public void checkingRectangleRedactionWhileRefreshingPage() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Test Case Id: RPMXCON-52218 & RPMXCON-52219 Description : Redact first 2
	 * pages of a doc Navigate using page navigation and redaction menu does not
	 * appear while clicking on page, Redaction appears
	 * 
	 */

	@Test(description = "RPMXCON-52219",enabled = true, alwaysRun = true, groups = { "regression" })
	public void chekingRedactionDeleteButton() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Test Case Id: RPMXCON-52217 Description : Verifying remarks application on
	 * redacted area/page without deleting redaction
	 */

	@Test(description = "RPMXCON-52217",enabled = true, alwaysRun = true, groups = { "regression" })
	public void thisPageRedactionAlongWithRemark() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52216
	 * Verifying remarks without text is not possible DocView- sprint 3
	 */
	@Test(description = "RPMXCON-52216",enabled = true, alwaysRun = true, groups = { "regression" })
	public void textRemarksWithoutText() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-52216");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch("T2390D");
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
	@Test(description = "RPMXCON-52215",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDeleteIconRectangleRedaction() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52213
	 * Verifying remarks alone is deleted when remarks associated with Rectangular
	 * Redaction DocView/Redactions- sprint 4
	 */
	@Test(description = "RPMXCON-52213",enabled = true, alwaysRun = true, groups = { "regression" })
	public void textRemarksWithTextInRectangleRedaction() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52212
	 * Verifying text remarks can not be added when no text is present in existing
	 * rectangular redaction DocView- sprint 4
	 */
	@Test(description = "RPMXCON-52212",enabled = true, alwaysRun = true, groups = { "regression" })
	public void textRemarksWithoutTextInRectangleRedaction() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-52212");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
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
		if (baseClass.getSuccessMsgHeader().isDisplayed() == true) {
			baseClass.passedStep("Remarks as RMU Can not be selected as text area is not selected");
			
		} else {
			
			actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
			actions.click();
			actions.sendKeys("Remark by RMU");
			actions.build().perform();
			baseClass.failedStep("Created Remarks as RMU");

		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52190
	 * Verifying while navigating from mini doclist child window, persistent hit
	 * panel stays open DocView-Sprint 4
	 */
	@Test(description = "RPMXCON-52190",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void verifyingPersistenthitAudioPanel() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-52223 Verify entire page redaction should not be saved as orphan on
	 * uncommon click path (F5) before clicking the 'Save' from Redaction Tag Save
	 * Confirmation pop up
	 */
	@Test(description = "RPMXCON-52223",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyThisPageRedactionAfterRefresh() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Robot robot = new Robot();
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52223");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.redactionIcon());
		docViewRedact.redactionIcon().waitAndClick(30);
		docViewRedact.performThisPageRedactionWithoutSaving("Default Redaction Tag");
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		baseClass.stepInfo("Verifying that redaction is not saved since page is refreshed");
		docViewRedact.verifyWhetherRedactionIsSaved(false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		baseClass.waitForElement(docViewRedact.redactionIcon());
		docViewRedact.redactionIcon().waitAndClick(20);
		docViewRedact.performThisPageRedactionWithoutSaving("Default Redaction Tag");
		robot.keyPress(KeyEvent.VK_F5);
		robot.keyRelease(KeyEvent.VK_F5);
		baseClass.stepInfo("Verifying that redaction is not saved since page is refreshed");
		docViewRedact.verifyWhetherRedactionIsSaved(false);
		loginPage.logout();
	}



	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-47723 From Audio search go to DocView Create Redaction in Audio
	 * Docs
	 */
	@Test(description = "RPMXCON-47723",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionInAudioDocs() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-47723");
		baseClass.stepInfo(
				"Verify user after impersonating as RMU/Reviewer can see the redaction and can redact in a audio file");
		loginPage = new LoginPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.addAudioRedaction(Input.startTime, Input.endTime, Input.defaultRedactionTag);
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47034 Verify that error message is present when invalid page range is
	 * given as input for redaction
	 */
	@Test(description = "RPMXCON-47034",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyMultiPagePopUpInput() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47034");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageIcon().Visible() && docViewRedact.multiPageIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageIcon().waitAndClick(10);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		docViewRedact.selectingMultiplePagesForRedaction();
		docViewRedact.enteringPagesInMultipageTextBox(Input.fullPageRange);
		if (docViewRedact.getMultipageRedactionError().isElementPresent()) {
			baseClass.passedStep("Error message is visible if wrong page range is entered");
		} else {
			baseClass.failedStep("Error message is NOT visible if wrong page range is entered");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47033 Verify the error message present when invalid page range is
	 * given as input for redaction
	 */
	@Test(description = "RPMXCON-47033",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyMultiPagePopUpInputMessage() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47033");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageIcon().Visible() && docViewRedact.multiPageIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageIcon().waitAndClick(10);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		docViewRedact.selectingMultiplePagesForRedaction();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageNumber);
		String text = docViewRedact.getMultipageRedactionError().getText();
		System.out.println(text);
		if (text.equalsIgnoreCase("Please enter a valid page or page range to redact.")) {
			baseClass.passedStep("Error message is visible if wrong page range is entered");
		} else {
			baseClass.failedStep("Error message is NOT visible if wrong page range is entered");
		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47031 Verify multipage redaction pop up closed when cancel button is
	 * clicked
	 * 
	 */
	@Test(description = "RPMXCON-47031",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyMultiPageRedactionPopup() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case id : RPMXCON-47031");
		baseClass.stepInfo(
				"Verify that on click of 'Cancel' from Multi-page redactions, no redaction should be applied/retained as part of this user operation and pop up should be closed");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.duplicateDocId);
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageIcon().Visible() && docViewRedact.multiPageIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageIcon().waitAndClick(10);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		baseClass.waitTillElemetToBeClickable(docViewRedact.getMultipageCancleBtn());
		docViewRedact.getMultipageCancleBtn().Click();
		driver.waitForPageToBeReady();
		if (docViewRedact.getMultipageCancleBtn().isElementPresent() == false) {
			baseClass.failedStep("The multipage pop up is NOT closed after clicking the cancle button");
		} else {

			baseClass.passedStep("The multipage pop up is closed after clicking the cancle button");
		}
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-49990 Description :Verify that when two different users applies
	 * redaction to same document then default selection of redaction tag should be
	 * as per users session.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(description ="RPMXCON-49990",enabled = true, groups = { "regression" })
	public void verifyRedactionTagSelectionAfterChangingUser() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id : RPMXCON-49990");
		baseClass.stepInfo("Verify default selection of redaction tag for same document with different users");

		// Login as RMU and Performing basic search
		sessionSearch.basicContentSearch(Input.testData1);

		// Adding search results and view in docview
		sessionSearch.ViewInDocView();

		// Clicking redact icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();

		// verifying the default selection of redaction tag
		docViewRedact.verifyDefaultRedactionTagSelectionInMultiPageRedact();

		// getting one of the available redaction tag
		List<String> availableTags = baseClass.availableListofElements(docViewRedact.availableTagsInMultiPageRedact());
		String redactionTag = availableTags.get(2);

		// performing multi page redaction include with different tag
		docViewRedact.selectingMultiplePagesForRedactionInclude(redactionTag);
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);

		baseClass.passedStep("Redaction done for Tag Name : " + redactionTag);

		// Logout and login as different user Reviewer
		loginPage.logout();
		baseClass.passedStep("Logged out as RMU");

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.passedStep("Logged in as other user- Reviewer");

		// Adding search results and perform action view in docview
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		// Clicking redact icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();

		// verifying the default selection of redaction tag
		docViewRedact.verifyDefaultRedactionTagSelectionInMultiPageRedact();
		loginPage.logout();
	}

	

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-47030 Description :Verify that all pages other than excluded ones
	 * from multi page redaction should be redacted successfully with changed
	 * redaction tag from what is presented in pop up.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(description ="RPMXCON-47030",enabled = true, groups = { "regression" })
	public void verifyRedactionTagReflectionAfterChanged() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id : RPMXCON-47030");
		baseClass.stepInfo("Verify all pages other than excluded ones are redacted");

		// Performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);

		// Adding search results and view in docview
		sessionSearch.ViewInDocView();

		// Clicking redact icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		String[] count = docView.getDocView_AllRedactionCount().getText().split("/ ");
		int beforeRedactedCount = Integer.parseInt(count[1]);

		// verifying the default selection of redaction tag
		docViewRedact.verifyDefaultRedactionTagSelectionInMultiPageRedact();

		// getting one of the available redaction tag
		List<String> availableTags = baseClass.availableListofElements(docViewRedact.availableTagsInMultiPageRedact());
		String redactionTag = availableTags.get(2);

		// performing multi page redaction exclude
		docViewRedact.selectingMultiplePagesForRedactionExclude(redactionTag);
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange4);

		// verifying all redaction count status after performing redaction
		docViewRedact.verifyAllRedactionCountStatusAfterRedaction(beforeRedactedCount);
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-47026 Description :Verify the page syntax user should be able to
	 * enter against Pages in multi page redactions pop up.
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(description ="RPMXCON-47026",enabled = true, groups = { "regression" })
	public void VerifyPageSyntaxAgainstMultiPageRedactionsPopup() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Test case id : RPMXCON-47026");
		baseClass.stepInfo(
				"Verify the page syntax user should be able to enter against Pages in multi page redactions pop up");

		// Performing basic search
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("ID00000230");

		// Adding search results and view in docview
		sessionSearch.ViewInDocView();

		// Verifying multiple multiPage redactions

		docViewRedact.multipleMultiPageRedactions(Input.pageRange1, Input.pageRange2, Input.pageRange3);
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-46957 Description :Verify that when applies 'Multi Page'
	 * redaction, the redaction popup should automatically select the redaction tag
	 * that was last applied across user session(s)
	 * 
	 * @throws Exception
	 * 
	 */
	@Test(description ="RPMXCON-46957",enabled = true, groups = { "regression" })
	public void verifyAutomaticSelectionOfRedactionTag() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id : RPMXCON-46957");
		baseClass.stepInfo("Verify automatic selection of redaction tag that last applied across user session");

		// Pre-requisite - getting the last saved redaction tag name

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		String lastSavedRedactionTag = docViewRedact.gettingLastSavedRedactionTagName();
		baseClass.passedStep("Last saved Redaction Tagname :" + lastSavedRedactionTag);
		docViewRedact.redactionTagCancleBtn().Click();

		// Loggedout and login back as RMU
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Adding search results and view in docview
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		// Clicking redact icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();

		// performing multi page redaction include with pagerange
		String redactionTagSelected = docViewRedact.gettingLastSavedRedactionTagName();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.passedStep("Automatically selected redaction tag name :" + redactionTagSelected);

		if (lastSavedRedactionTag.equalsIgnoreCase(redactionTagSelected)) {
			baseClass.passedStep(
					"Redaction tag selected automatically which applied for last redaction as per user session");
		} else {
			baseClass.failedStep(
					"Redaction tag selected automatically which is not applied for last redaction as per user session");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-46955 Description :Part of 7.1: Verify that when enters only ‘Page
	 * Range’ from multi-page redactions pop up then for entered page range
	 * redaction should be applied
	 * 
	 */
	@Test(description ="RPMXCON-46955",enabled = true, groups = { "regression" })
	public void verifyOnlyPageRangeMultipageRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case id : RPMXCON-46955");
		baseClass.stepInfo("Verify docview multipage redaction only entering page range");

		// Login as RMU and performing basic search
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();
		baseClass.passedStep("Navigated to DocView");

		// clicking docview redact icon
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().waitAndClick(30);
		driver.waitForPageToBeReady();

		// performing redact by entering only page range
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return baseClass.getSuccessMsgHeader().Visible();
			}
		}), Input.wait60);
		baseClass.passedStep("Redaction done for entered page Range :" + Input.pageRange);
		String successMessage = baseClass.getSuccessMsgHeader().getText().toString();

		if (successMessage.contains("Success !")) {
			baseClass.passedStep("Redaction done when entering only page range with default selected redaction tag");
		} else {
			baseClass
					.failedStep("Redaction not done when entering only page range with default selected redaction tag");
		}
		loginPage.logout();
	}


	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47878 To verify that when redaction control in red "on" state, if the
	 * icon is clicked again by the user, it must revert to an "off" state DocView
	 * Page
	 */
	@Test(description ="RPMXCON-47878",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyOnColourChangeInRedactionMenu() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		Actions actions = new Actions(driver.getWebDriver());
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47878");
		baseClass.stepInfo(
				"To verify that when redaction control in red \"on\" state, if the icon is clicked again by the user, it must revert to an \"off\" state");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchBulletDocId);
		baseClass.stepInfo("Search for document completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document viewed in DocView");
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		docViewRedact.multiPageIcon().Click();
		docViewRedact.verifyingMultipageIconColour(Input.iconColor);
		docViewRedact.getMultipageCancleBtn().Click();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.build().perform();
		String color1 = docViewRedact.multiPageIcon().getWebElement().getCssValue("color");
		String hex1 = Color.fromString(color1).asHex();
		System.out.println(hex1);
		if (hex1.equalsIgnoreCase(Input.IconOriginalColour)) {
			baseClass.passedStep(
					"The multipage icon turned back to normal colour on unselecting as expected- Successfully");
		} else {
			baseClass.failedStep("The multipage icon NOT turned to normal  colour as expected");
		}
		loginPage.logout();

	}
	
	/*
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-46860
	 */
	@Test(description ="RPMXCON-46860",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void VerifyRectangleRedactionDeletionFromDocExlorer() throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-46860");
		baseClass.stepInfo("Verify user can delete the redaction in a document");
		DocExplorerPage docexp = new DocExplorerPage(driver);
		baseClass.stepInfo("Navigating to DocExp page");
		docexp.navigateToDocExplorerPage();
		docexp.documentSelectionIteration();
		docexp.docExpViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 50, 100);
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleClick().Visible() && docViewRedact.rectangleClick().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleClick().waitAndClick(20);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).click();
		actions.build().perform();
		if(docViewRedact.deleteClick().isDisplayed() == true) {
		actions.moveToElement(docViewRedact.deleteClick().getWebElement());
		actions.click();
		actions.build().perform();
		baseClass.VerifySuccessMessage("Redaction Removed successfully.");
		}
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag Deleted successfully");

	
	}

	
	/**
	 *
	 * @Author : Krishna
	 * @Testcase id : 47736 DocView/Redactions
	 *         
	 */
	
	@Test(description ="RPMXCON-47736",groups = { "regression" })
	public void printRedactedDocsAfterImpersonation() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47736");
		baseClass.stepInfo(
				"Verify user after impersonation can download the file without redaction on click of the print icon from default view");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();

// printing from session search
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with DocId input completed");
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
		docViewRedact.bullhornIconRedColour().waitAndClick(30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.viewAllBtn().Visible() && docViewRedact.viewAllBtn().Enabled();
			}
		}), Input.wait30);
		docViewRedact.viewAllBtn().waitAndClick(15);
		// Thread sleep added for the session to move to next page to extract url
		Thread.sleep(4000);
		String urlBackgroundfromdocview = driver.getUrl();
		assertEquals(urlBackgroundfromdocview, "https://sightlineuat.consiliotest.com/Background/BackgroundTask");
		baseClass.passedStep("Navigated to document download page");
		loginPage.logout();
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-59994
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-49994",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionTagSelectionAsSA() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-49994");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when System Admin impersonates as RMU/Reviewer");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingRedactionIcon();
		baseClass.waitForElement(docViewRedact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(docViewRedact.thisPageRedaction());
		docViewRedact.thisPageRedaction().Click();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.stepInfo("First redaction tag on the list saved successfully");
		loginPage.logout();
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49974 Part of 7.1: Verify that when enters only ‘Page Range’ from
	 * multi-page redactions pop up then for entered page range redaction should be
	 * applied
	 * 
	 */
	@Test(description ="RPMXCON-49974",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyMultiPageRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49974");
		baseClass.stepInfo(
				"Part of 7.1: Verify that when enters only ‘Page Range’ from multi-page redactions pop up then for entered page range redaction should be applied");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageIcon().Visible() && docViewRedact.multiPageIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageIcon().waitAndClick(10);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		docViewRedact.selectingMultiplePagesForRedaction();
		docViewRedact.enteringPagesInMultipageTextBox(Input.pageRange);
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		loginPage.logout();
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47725
	 * 
	 */
	@Test(description ="RPMXCON-47725",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyRectangleRedactionDeletion() throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47725");
		baseClass.stepInfo("Verify user can delete the redaction in a document");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		docViewRedact.selectingRectangleRedactionTag();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleClick().Visible() && docViewRedact.rectangleClick().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleClick().waitAndClick(8);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 10, 10).click();
		actions.build().perform();
		actions.moveToElement(docViewRedact.deleteClick().getWebElement());
		actions.click();
		actions.build().perform();
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag Deleted successfully");

	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49973
	 *
	 * 
	 */
	@Test(description ="RPMXCON-49973",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyMultiPageRedactionTagSelection() throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49973");
		baseClass.stepInfo(
				"Part of 7.1: Verify that when applies ‘Multi Page’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageIcon().Visible() && docViewRedact.multiPageIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageIcon().waitAndClick(10);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.multiPageInputTextbox().waitAndClick(5);
		docViewRedact.multiPageInputTextbox().Clear();
		docViewRedact.multiPageInputTextbox().SendKeys(Input.pageRange);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageRedactionTagSelect().Visible()
						&& docViewRedact.multiPageRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(docViewRedact.multiPageRedactionTagSelect().getWebElement());
		String attribute = redactionTag.getFirstSelectedOption().getAttribute("text");
		System.out.println(attribute);
		if (attribute.equalsIgnoreCase("Default Redaction Tag")) {
			baseClass.passedStep("The first selected redaction tag is Default Redaction Tag");
		} else {
			baseClass.failedStep("The first selected redaction tag is NOT Default Redaction Tag");
		}
		loginPage.logout();
	}

	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49972
	 * 
	 */
	@Test(description ="RPMXCON-49972",enabled = true, alwaysRun = true, groups = { "regression" })
	public void VerifyThisPageRedactionTagSelection() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49972");
		baseClass.stepInfo(
				"Verify that when applies ‘This Page’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		docViewRedact.thisPageRedaction().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleRedactionTagSelect().Visible()
						&& docViewRedact.rectangleRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(docViewRedact.rectangleRedactionTagSelect().getWebElement());
		String attribute = redactionTag.getFirstSelectedOption().getAttribute("text");
		System.out.println(attribute);
		if (attribute.equalsIgnoreCase("Default Redaction Tag")) {
			baseClass.passedStep("The first selected redaction tag is Default Redaction Tag");
		} else {
			baseClass.failedStep("The first selected redaction tag is NOT Default Redaction Tag");
		}
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49971
	 * 
	 */
	@Test(description ="RPMXCON-49971",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void VerifyRectangleRedactionTagSelection() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49971");
		baseClass.stepInfo(
				"Verify that when applies ‘Rectangle’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleRedactionTagSelect().Visible()
						&& docViewRedact.rectangleRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(docViewRedact.rectangleRedactionTagSelect().getWebElement());
		String attribute = redactionTag.getFirstSelectedOption().getAttribute("text");
		System.out.println(attribute);
		if (attribute.equalsIgnoreCase("Default Redaction Tag")) {
			baseClass.passedStep("The first selected redaction tag is Default Redaction Tag");
		} else {
			baseClass.failedStep("The first selected redaction tag is NOT Default Redaction Tag");
		}
		loginPage.logout();

	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-46954
	 */
	@Test(description ="RPMXCON-46954",enabled = true, alwaysRun = true, groups = { "regression" } )
	public void VerifyMultiPageRedactionTagDefaultSelection() throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-46954");
		baseClass.stepInfo("Part of 7.1: Verify that when applies ‘Multi Page’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		docViewRedact.multiPageIcon().waitAndClick(10);
		baseClass.stepInfo("The Multipage icon is clicked Menu is Visible");
		actions.moveToElement(docViewRedact.multiPageIcon().getWebElement()).click();
		actions.click().build().perform();
		docViewRedact.multiPageInputTextbox().waitAndClick(5);
		docViewRedact.multiPageInputTextbox().Clear();
		docViewRedact.multiPageInputTextbox().SendKeys(Input.pageRange);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.multiPageRedactionTagSelect().Visible() && docViewRedact.multiPageRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		docViewRedact.multiPageRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(docViewRedact.multiPageRedactionTagSelect().getWebElement());
		String attribute = redactionTag.getFirstSelectedOption().getAttribute("text");
		System.out.println(attribute);
		if(attribute.equalsIgnoreCase("Default Redaction Tag")) {
			baseClass.passedStep("The first selected redaction tag is Default Redaction Tag");
		} else {
			baseClass.failedStep("The first selected redaction tag is NOT Default Redaction Tag");
		}
		loginPage.logout();
		}
	

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW/REDACTIONS EXECUTED******");

	}

}
