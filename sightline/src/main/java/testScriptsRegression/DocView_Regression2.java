package testScriptsRegression;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
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
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression2 {
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

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Input in = new Input();
		// in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	}

	@Test(groups = { "regression" }, priority = 0)
	public void printRedactedDocsAfterImpersonation() throws Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47736");
		baseClass.stepInfo(
				"Verify user after impersonation can download the file without redaction on click of the print icon from default view");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
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
		assertEquals(urlBackgroundfromdocview, "https://sightlinept.consilio.com/Background/BackgroundTask");
		baseClass.passedStep("Navigated to document download page");
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51009 Verify thumbnails icon click enables the thumbnails panel
	 * with impersonation PA to RMU
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyThumbnailsInDocView() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51009");
		baseClass.stepInfo(
				"Verify after impersonation user can view the thumbnails of each page of document in thumbnail panel");
		loginPage = new LoginPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.impersonatePAtoRMU();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.docIdThumbnails);
		sessionsearch.ViewInDocView();
		docViewRedact.clickingThumbnailIcon();
		if (docViewRedact.thumbNailsPanel().isElementPresent() == true) {
			baseClass.passedStep("The thumbnails panel is clicked and menu is visible");
		} else {
			baseClass.failedStep("The thumbnail Panel menu is NOT displayed");
		}
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.clickPageNumber(2).Visible() && docViewRedact.clickPageNumber(2).Enabled();
			}
		}), Input.wait30);
		docViewRedact.clickPageNumber(2).waitAndClick(10);
		String text = docViewRedact.clickPageNumber(2).getText();
		System.out.println(text);
		if (text.equalsIgnoreCase("2")) {
			baseClass.passedStep("The doc view has page loaded as per the click on thumbnail ");
		} else {
			baseClass.failedStep("The doc View page had not been loaded as per thumbnail");
		}

	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51562 Verify when user enters some text (e.g. Hello) in the text
	 * box and hits Enter, then the app looks for the corresponding text and
	 * highlights them in the document
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 3)
	public void verifyHighlightedTextInDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51562");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.searchDocId);
		baseClass.stepInfo("Search with text input DocId completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Purehits viewed in DocView");
		docViewRedact.verifyHighlightedText();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51563 Verify app will present information about the hits (e.g. 1
	 * of 30) and the option to traverse through these hits using the forward and
	 * backward arrows.
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 4)
	public void verifyTraverseForwardAndBackwardOnHits(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51563");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input - TEST completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Purehits viewed in DocView");
		docViewRedact.TraverseForwardAndBackwardOnHits();
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51564 Verify when there are no hits, then it will show 0 of 0
	 * with the forward and backward arrows grayed out.
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 11)
	public void verifyThereAreNoHits(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51563");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input - TEST completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Purehits viewed in DocView");
		docViewRedact.thereAreNoHitsVerify();

	}

	/**
	 * Author : Sai Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51745 Verify persistent hit panel remains selected when navigation
	 * from child window
	 */

	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 5)
	public void verifyPersistentHitPanelChildWindow(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51745");
		baseClass.stepInfo(
				"Verify that on document navigation from mini doc list when hits panel is open then enable/disable should be retained");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		if (docViewRedact.persistantHitToggle().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep(
					"The persistent hit panel is visible for audio documents After navigating from child window");
		} else {
			assertTrue(false);
		}

	}

	/**
	 * Author : Sai Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51425 Verify Conceptually similar tab remains selected when
	 * navigation from child window
	 */

	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 6)
	public void verifyConceptuallySimilarTabChildWindow(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51425");
		baseClass.stepInfo(
				"To verify that user has selected an Analytics Panel tab and navigates to another document from mini doc list child window, the Analytics Panel Tab previously selected must remain.");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingConceptuallySimilarTab();
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		String getAttribute = docViewRedact.getConceptuallySimilarTab().GetAttribute("class");
		if (getAttribute.equalsIgnoreCase("text-center active")) {
			baseClass.passedStep(
					"The Conceptually similar tab remains selected after navigating from Mini DocList Child Window");
		} else {
			assertTrue(false);
		}
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51909
	 * Verify Images tab when moved to last Doc
	 * PA to RMU
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyImagesIconWhenMovedToLastDoc() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case id : RPMXCON-51909");
		baseClass.stepInfo("Verify that when user in on Images tab and navigates to last document then should be on Images tab for last document");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingImagesTab();
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardToLastDoc());
			actions.moveToElement(docViewRedact.forwardToLastDoc().getWebElement()).click();
			actions.click().build().perform();	
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the last document");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		
		
		
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51910
	 * Verify that when user in on Images tab and navigates to first document then should be on Images tab for first document
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 8)
	public void verifyImagesIconWhenMovedToFirstDoc(String fullName, String userName, String password) throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		baseClass.stepInfo("Test case id : RPMXCON-51910");
		baseClass.stepInfo("Verify that when user in on Images tab and navigates to first document then should be on Images tab for first document");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingImagesTab();
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardToLastDoc());
			actions.moveToElement(docViewRedact.forwardToLastDoc().getWebElement()).click();
			actions.click().build().perform();
			baseClass.waitTillElemetToBeClickable(docViewRedact.backwardToFirstDoc());
			actions.moveToElement(docViewRedact.backwardToFirstDoc().getWebElement()).click();
			actions.click().build().perform();
		
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the last document");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51911
	 * Verify that when user in on Images tab when navigated to doc by entering DocNumber
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 9)
	public void verifyImagesIconWhenMovedtoDocByEnteringNumber(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		baseClass.stepInfo("Test case id : RPMXCON-51911");
		baseClass.stepInfo("Verify that when user in on Images tab and navigates to document after entering the document number then should be on Images tab for navigated document");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingImagesTab();
		driver.scrollPageToTop();
		docViewRedact.pageNumberTextBox().waitAndClick(10);
		docViewRedact.pageNumberTextBox().getWebElement().clear();
		docViewRedact.pageNumberTextBox().getWebElement().sendKeys(Input.pageNumber);
		baseClass.stepInfo("Clicked on images tab and moved to other doc by entering page number");
		driver.waitForPageToBeReady();
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the document using doucument number");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
	}

	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51908
	 * Verify that when user in on Images tab when navigated to doc by navigating previous Doc
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 10)
	public void verifyImagesIconWhenNavigatingPriviousDoc(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		baseClass.stepInfo("Test case id : RPMXCON-51908");
		baseClass.stepInfo("Verify that when user in on Images tab and navigates to previous document then should be on Images tab for previous document");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingImagesTab();
		driver.scrollPageToTop();
		docViewRedact.forwardToLastDoc().waitAndClick(5);
		docViewRedact.backwardPriviousDocBtn().waitAndClick(5);
		baseClass.stepInfo("Clicked on images tab");
		driver.waitForPageToBeReady();
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the previous document ");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
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
		System.out.println("******TEST CASES FOR DOCVIEW/REDACTIONS EXECUTED******");

	}

}
