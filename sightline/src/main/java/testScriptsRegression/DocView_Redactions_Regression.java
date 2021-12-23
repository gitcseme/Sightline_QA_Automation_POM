package testScriptsRegression;

import static org.testng.Assert.assertFalse;
import pageFactory.DocViewPage;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
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

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
	String keywordsArray[] = { "test", "hi", "Than8617167" };

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
	 * Author : Krishna D date: NA Modified date: 24/08/2021 by: Krishna D Krishna
	 * Test Case Id: RPMXCON-52255 D Description : DocView Redactions Panel Checking
	 * if Redaction menu is avilable
	 */

	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 2)
	public void verifyDocViewRedactionPanel() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
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
	}

	/**
	 * Author : Krishna D date: NA Modified date: 24/08/2021 Modified by: Krishna D
	 * Test Case Id: RPMXCON-52321 & RPMXCON-52322 Verifying blank Redaction Login
	 * as RMU Go to Search Search any term drag the result to shopping cart and
	 * select action as 'View in DocView' user to view in DocView from "Actions"
	 * Click on redaction and multipage tab
	 */

	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 1)

	public void verifyBlankRedactionTag() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
// Adding rectangular redaction and saving-modified on 24/08/2021
		baseClass.stepInfo("Test case Id: RPMXCON-52321, RPMXCON-52322");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchBulletDocId);
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(15);
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
	}

	/**
	 * Author : Krishna D date: NA Modified date: 24/08/21 Modified by: Krishna D
	 * Krishna Test Case Id RPMXCON-52318 Description Saving Multipage Redaction
	 * Login as RMU Go to Search Search any term drag the result to shopping cart
	 * and select action as 'View in DocView' user to view in DocView from "Actions"
	 * Click on redaction and multipage tab
	 */

	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 3)
	public void saveMultiPageRedaction() throws Exception {
		baseClass = new BaseClass(driver);
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
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id RPMXCON-52320 Description : Verify documents for Redaction and
	 * Multipage when user Selects "documents" from DocExplorer user to view in
	 * DocView from "Actions" Click on redaction and multipage tab Multipage pop up
	 * enabled
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 4)
	public void testShareSteps() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");

		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-52320");

// Fetching file from DocExplorer and View in DocView

		docViewRedact.docExplorerIcon().getWebElement().click();
		baseClass.waitTillElemetToBeClickable(docViewRedact.selectingFirstdataFromTable());
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.selectingFirstdataFromTable().getWebElement()));
		docViewRedact.selectingFirstdataFromTable().getWebElement().click();
		baseClass.stepInfo("The First document from Doc Explorer tab is selected");
		wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.docExplorerActions().getWebElement()));
		actions.moveToElement(docViewRedact.docExplorerActions().getWebElement());
		actions.click().build().perform();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

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

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Krishna D Krishna
	 * Test Case Id RPMXCON-52254 Description: checking for forward and backward
	 * navigation of docs while redacting
	 */

	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 5)
	public void verifyRedactionPanel() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case Id: RPMXCON 52254");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
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
	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Test Case Id
	 * RPMXCON-51995 & 51996 - sprint 3- DocView Description : Verify redaction for
	 * page range and all pages in a doc
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 6)
	public void RedactPageRange() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51995, RPMXCON-51996");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchBulletDocId);
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

	}

	/**
	 * Author : Krishna D date: NA Modified date: NA Modified by: Test Case Id
	 * RPMXCON-51998 & 51997 - sprint 3- DocView Description : Verify redaction for
	 * page range and all pages in a doc after impersonating user
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 7)
	public void RedactPageRangeImpersonatingUser() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51998, RPMXCON-51997");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("PA has been impersonated as RMU");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchBulletDocId);
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

	}

	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52150
	 * Verifying blank Redaction Login as RMU Go to Search Search any term and
	 * DocView
	 */

	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 8)

	public void verifyRedactionTagBlank() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
// search for document
		baseClass.stepInfo("Test case Id: RPMXCON-52150");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Seach completed with document Id and viewed in DocView");
//Drawing redaction and checking for tag
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(15);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Visible() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		docViewRedact.redactionIcon().waitAndClick(5);
		docViewRedact.redactRectangleUsingOffset(-8, 10, 200, 100);
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

	}


	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 52189
	 * Verifying persistent hit for audio docs DocView- sprint 3
	 */

	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 12)
	public void persistentHitAudioDocs() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52189");
		SessionSearch sessionsearch = new SessionSearch(driver);
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

	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51992 DocView and Doc Explorer_Performance_Navigate through
	 * documents one by one for 15 mins_RMU/Rev
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 13)
	public void DocViewAndDocExplorerPerformanceNavigate() throws Exception {
		baseClass = new BaseClass(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case Id: RPMXCON-51992");
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		docexp = new DocExplorerPage(driver);
		docexp.exportData();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51731 Verify that text highlighting should not be present on doc
	 * view
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 14)
	public void VerifyTextHightingNotPresent() throws Exception {
		baseClass = new BaseClass(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case Id: RPMXCON-51731");
		docViewRedact = new DocViewRedactions(driver);

		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.TextHightingNotPresentVerify();

	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51755 Verify that when the toggle is ON will hide the 0 hit terms
	 * on hits panel
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 15)
	public void VerifyToggleONhideZeroHits() throws Exception {
		baseClass = new BaseClass(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case Id: RPMXCON-51755");
		docViewRedact = new DocViewRedactions(driver);

		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.ToggleONhideZeroHits();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51756 Verify that when the toggle is OFF will show the 0 hit
	 * terms on hits panel
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 16)
	public void VerifyToggleOFFshowZeroHits() throws Exception {
		baseClass = new BaseClass(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case Id: RPMXCON-51756");
		docViewRedact = new DocViewRedactions(driver);

		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Logged-in as RMU User");
		System.out.println("Logged-in as RMU User");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		docViewRedact.ToggleOFFshowZeroHits();
		System.out.println("*** Test Completed ***");
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51989 Verify colour is same when remarks edited by different RMU user
	 */
//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 17)
	public void VerifyColourRemarksEditing() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 100);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case Id: RPMXCON-51989");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdRemarks);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(Input.wait3);
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
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		sessionsearch.basicContentSearch(Input.docIdRemarks);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRemarksIcon();
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51558 Verify if X icon is visible after clicking on the
	 * magnfifying/search icon in docView page
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 18)
	public void VerifyMagnifyingIconInDocViewForAllUsers() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51558");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51316 Verify if color changes for any annotation icon when clicked on
	 * DocView Page
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 19)
	public void VerifyOnOffColourChangeInDocViewPage() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51316");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-50848 Verify if the persistent hit icon is present in DocView from
	 * Basic Search for non-audio Docs
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 20)
	public void verifyPersistantHitIconFromBasicSearch() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50848");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51346 Verify if highlights panel once open is not closed while
	 * navigating docs from mini doc list
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 21)
	public void verifyHighlitesPanelisOpenWhileNavigatingFromMiniDocList() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51346");
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51345 Verify that reviewer remarks panel remains selected while
	 * navigating through docs
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 23)
	public void VerifyReviewerRemarksPanel() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51345");
		SessionSearch sessionsearch = new SessionSearch(driver);
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51344 Verify that reviewer Persistent hits panel remains selected
	 * while navigating through docs
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 24)
	public void VerifyReviewerPersistentHitPanel() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51344");
		SessionSearch sessionsearch = new SessionSearch(driver);
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47034 Verify that error message is present when invalid page range is
	 * given as input for redaction
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 25)
	public void VerifyMultiPagePopUpInput() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47033 Verify the error message present when invalid page range is
	 * given as input for redaction
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 26)
	public void VerifyMultiPagePopUpInputMessage() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-52223 Verify entire page redaction should not be saved as orphan on
	 * uncommon click path (F5) before clicking the 'Save' from Redaction Tag Save
	 * Confirmation pop up
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 27)
	public void VerifyThisPageRedactionAfterRefresh() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
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
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(false);
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
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(false);

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51567 Verify when the user clicks on the X after the text search in a
	 * document
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 28)
	public void verifyTextSearchAfterUserClicksX() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51567");
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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-52214 Verify that multiple Rectangle Redaction does not remain
	 * selected on DocView Screen
	 */
//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 29)
	public void verifyMultiRecRedactionNotRemainSelected() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
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
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(true);

		driver.scrollPageToTop();
		baseClass.waitForElement(docViewRedact.docViewNextPage());
		docViewRedact.docViewNextPage().Click();

		baseClass.stepInfo("Verify whether the redaction popup is displayed on select of next redacted page");
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(true);

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
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(true);

		driver.scrollPageToTop();
		baseClass.waitForElement(docViewRedact.docViewNextPage());
		docViewRedact.docViewNextPage().Click();

		baseClass.stepInfo("Verify whether the redaction popup is displayed on select of next redacted page");
		docViewRedact.verifyWhetherThisPageRedactionIsSaved(true);

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
	 * RPMXCON-51568 Verify when user navigates to other document while viewing the
	 * search hits and comes back to same document
	 */
//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 30)
	public void verifyTextSearchAfterUserNavigatesToAnotherDoc() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage = new LoginPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docViewPage = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51568");
		baseClass.stepInfo(
				"Verify when user navigates to other document while viewing the search hits and comes back to same document");
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47031 Verify multipage redaction pop up closed when cancel button is
	 * clicked
	 * 
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 31)
	public void verifyMultiPageRedactionPopup() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51746 Verify that on document navigation options when hits panel
	 * is open then enable/disable should be retained
	 */
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 32)
	public void verifyPersistentHitPanelIsRetained() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51746");
		baseClass.stepInfo(
				"Verify that on document navigation options when hits panel is open then enable/disable should be retained");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
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

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51444 Check persistent hits when navigated from assignments to
	 * DocView as RMU and REV
	 * 
	 */
//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 34)
	public void verifyPersistentHitNavigation2() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51444");
		baseClass.stepInfo(
				"Verify that > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view in context of an assignment");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.bulkAssign();
		assignmentspage.createAssignmentNew(assignmentName, Input.codeFormName);
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);

// Checking persistent hits and keywords in DocView		
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color2).asHex();
		System.out.println(hex2);
		if (hex2.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next button clickable");
		} else {
			baseClass.failedStep("next button not clickable");
		}
		loginPage.logout();

// Login as Rev and checking	

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentspage.SelectAssignmentByReviewer(assignmentName);

		// Checking persistent hits and keywords in DocView
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color3 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex3 = Color.fromString(color3).asHex();
		System.out.println(hex3);
		if (hex3.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next button clickable");
		} else {
			baseClass.failedStep("next button not clickable");
		}

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51445 Verify colour for highlited text Verify Navigation option
	 * from persistent hit panel from basic search
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 33)
	public void verifyPersistentHitNavigation() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51445");
		baseClass.stepInfo(
				"Verify that after impersonation > and < arrows should work when the hit in the document is due to Keyword Group Highlights when redirected to doc view from basic search");
		loginPage = new LoginPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		if (hex.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next btn clickable");
		} else {
			baseClass.failedStep("next btn not clickable");
		}

		// Impersonation as Rev and checking the above

		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.docIdKeyWordTest);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitForElement(docViewRedact.get_textHighlightedColor());
		String color2 = docViewRedact.get_textHighlightedColor().getWebElement().getCssValue("fill");
		String hex2 = Color.fromString(color2).asHex();
		System.out.println(hex);
		if (hex2.equalsIgnoreCase(Input.keyWordHexCode)) {
			baseClass.passedStep("The color for the Highlighted text is verfied- Successfully");
		} else {
			baseClass.failedStep("The color for the Highlighted text is not-verfied - Failed");

		}
		baseClass.waitForElement(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().Click();
		if (docViewRedact.nextKeywordTest().Enabled() == true) {
			baseClass.passedStep("next btn clickable");
		} else {
			baseClass.failedStep("next btn not clickable");
		}

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-47723 From Audio search go to DocView Create Redaction in Audio
	 * Docs
	 */
//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 35)
	public void verifyRedactionInAudioDocs() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-47723");
		baseClass.stepInfo(
				"Verify user after impersonating as RMU/Reviewer can see the redaction and can redact in a audio file");
		loginPage = new LoginPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.audioSearch("Morning", Input.language);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.addAudioRedaction(Input.startTime, Input.endTime, Input.defaultRedactionTag);
	}

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 36)
	public void verifyPersistentHitsAfterReassignDocuments() throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51763");
		baseClass.stepInfo(
				"Non-Audio Documents assigned from Basic search- Verify that when documents are re-assigned to same/other reviewer in an assignment, any previously saved persistent search hits in the assignment should be displayed in the assignment");

		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);

		sessionSearch.bulkAssign();

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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51762 Non-Audio Documents assigned from Saved search- Verify that
	 * when documents are re-assigned to same/other reviewer in an assignment, any
	 * previously saved persistent search hits in the assignment should be displayed
	 * in the assignment
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 37)
	public void verifyPersistentHitsAfterReassignDocumentsSavedSearch() throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51762");
		baseClass.stepInfo(
				"Non-Audio Documents assigned from Basic search- Verify that when documents are re-assigned to same/other reviewer in an assignment, any previously saved persistent search hits in the assignment should be displayed in the assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		savedSearch.savedSearch_Searchandclick(searchName);

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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51765 Non-Audio Documents assigned from Saved Search group- Verify
	 * that when documents are re-assigned to same/other reviewer in an assignment,
	 * any previously saved persistent search hits in the assignment should be
	 * displayed in the assignment
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 38)
	public void verifyPersistentHitsAfterReassignDocumentsSavedSearchGroup() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51399 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with metadata
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 39)
	public void verifyHighlightedKeywordsForDocsSearchWithMetadata() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51399");
		baseClass.stepInfo(
				"Verify highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search when documents searched with metadata");

		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51403 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searching with reviewer
	 * remarks/comments
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 40)
	public void verifyHighlightedKeywordsForDocsSearchWithCommentsRemarks() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51403");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assinged after searching with reviewer remarks/comments");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.getCommentsOrRemarksCount("Remark", "\"Remark by Rmu\"");
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.impersonateRMUtoReviewer();
		assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51402 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when documents are assinged after searched with work product
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 41)
	public void verifyHighlightedKeywordsForDocsSearchWithWorkProduct() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51402");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when documents are assinged after searched with work product");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectAssignmentInWPS(assignmentName);
		sessionSearch.serarchWP();
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		baseClass.impersonateReviewertoRMU();
		assignmentsPage.deleteAssignment(assignmentName);
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51400 Verify highlighted keywords should be displayed on click of
	 * the eye icon when redirected to doc view from session search when documents
	 * searched with comment/Reviewer Remarks
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 42)
	public void verifyHighlightedKeywordsForDocSearchWithCommentsRemarks() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51400");
		baseClass.stepInfo(
				"Verify highlighted keywords should be displayed on click of the eye icon when redirected to doc view from session search when documents searched with comment/Reviewer Remarks");

		String codingForm = Input.codeFormName;
		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.getCommentsOrRemarksCount("Remark", "\"Remark by Rmu\"");
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		assignmentsPage.deleteAssignment(assignmentName);
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51401 Verify assigned keywords should be highlighted and should be
	 * displayed on click of the eye icon when redirected to doc view from
	 * assignment when assinged documents are searched with metadata
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 43)
	public void verifyHighlightedKeywordsForDocSearchWithMetadata() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51401");
		baseClass.stepInfo(
				"Verify assigned keywords should be highlighted and should be displayed on click of the eye icon when redirected to doc view from assignment when assinged documents are searched with metadata");

		String codingForm = Input.codeFormName;

		baseClass.stepInfo("Create new assignment");
		assignmentsPage.createAssignment(assignmentName, codingForm);
		sessionSearch.basicMetaDataSearch("DocID", null, Input.MiniDocId, null);
		sessionSearch.bulkAssign();
		assignmentsPage.assignDocstoExisting(assignmentName);
		assignmentsPage.editAssignment(assignmentName);
		baseClass.stepInfo("Distributing docs to RMU");
		assignmentsPage.assignmentDistributingToReviewerManager();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docViewRedact.verifyHighlightedTextsAreDisplayed();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify whether the panels are displayed in doc view along with terms and its counts");
		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_HitsTogglePanel());
		docView.verifyKeywordsAreDisplayed(keywordsArray);
		assignmentsPage.deleteAssignment(assignmentName);

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51940 Verify that when user is selecting a document to view after
	 * scrolling down/up the mini doc list child window and in DocView, the entry
	 * for the same document in mini-DocList must always present fully in the
	 * visible area of the mini-DocList child window
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 44)
	public void verifyDocInMiniDocListChildWindowAfterScrollingDown() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51940");
		baseClass.stepInfo(
				"Verify that when user is selecting a document to view after scrolling down/up the mini doc list child window and in DocView, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList child window");

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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51942 Verify when user is selecting a document to view after
	 * scrolling down the mini doc list child window when 'Loading..' displays in
	 * DocView, the entry for the same document must always present fully in the
	 * visible area of the mini-DocList child window
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 45)
	public void verifyDocInMiniDocListChildWindowAfterScrollingDownTillLoadingTextDisplayed() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51942");
		baseClass.stepInfo(
				"Verify when user is selecting a document to view after scrolling down the mini doc list child window when 'Loading..' displays in DocView, the entry for the same document must always present fully in the visible area of the mini-DocList child window");

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
		docView.scrollUntilloadingTextDisplay(true);

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
		docView.scrollUntilloadingTextDisplay(true);

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
		docView.scrollUntilloadingTextDisplay(true);

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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51943 Verify when user enters document number to view when
	 * 'Loading..' displays in mini doc list, the entry for the same document in
	 * mini-DocList must always present fully in the visible area of the
	 * mini-DocList
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 46)
	public void verifyDocInMiniDocsAfterScrollingDownTillLoadingTextDisplayedWhenDocIsSearchedUsingDocId()
			throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		int docToBeSearched = Input.totalNumberOfDocsincludeadvoption;
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51943");
		baseClass.stepInfo(
				"Verify when user enters document number to view when 'Loading..' displays in mini doc list, the entry for the same document in mini-DocList must always present fully in the visible area of the mini-DocList");

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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51770 Verify when remove non audio docs from a user/reviewer in an
	 * assignment, displays persistent search hits in the assignment, when
	 * reassigning these non audio docs to another/same reviewer in the assignment
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 47)
	public void verifyPersistentHitsAfterRemoveAndReassignDocumentsSavedSearchGroup() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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

	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51771 Verify that previously saved Persistent hits displayed on
	 * the doc view when documents assigned to same/another reviewer are completed
	 * from edit assignment
	 */

//	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 48)
	public void verifyPersistentHitsAfterCompleteDocumentsSavedSearchGroup() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		SoftAssert softAssert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		MiniDocListPage miniDocList = new MiniDocListPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51771");
		baseClass.stepInfo(
				"Verify that previously saved Persistent hits displayed on the doc view when documents assigned to same/another reviewer are completed from edit assignment");

		String codingForm = Input.codeFormName;
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String assname = "assgnment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(searchName);

		// Share Search via Saved Search
		SavedSearch savedSearch = new SavedSearch(driver);
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

		baseClass.stepInfo("Logging in to RMU user to complete docs");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentsPage.selectAssignmentToView(assname);

		assignmentsPage.assignmentActions("Edit");

		assignmentsPage.completeDocs(Input.rev1userName);

		assignmentsPage.selectAssignmentToViewinDocviewThreadMap(assname);

		driver.waitForPageToBeReady();

		miniDocList.configureMiniDocListToShowCompletedDocs();

		docView.scrollUntilloadingTextDisplay(false);

		baseClass.waitForElement(docView.getCompletedDocs());

		docView.getCompletedDocs().Click();

		driver.waitForPageToBeReady();

		baseClass.stepInfo("Verify whether the panels are displayed in doc view even after completing docs");
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
