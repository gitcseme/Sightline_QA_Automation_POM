package testScriptsRegression;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

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

//	@Test(groups = { "regression" }, priority = 0)
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
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51009");
		baseClass.stepInfo(
				"Verify after impersonation user can view the thumbnails of each page of document in thumbnail panel");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.impersonatePAtoRMU();
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
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
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 5)
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

	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 6)
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
		baseClass.stepInfo("Search for non audio docs completed");
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

	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority = 7)
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
		baseClass.stepInfo("Search for non audio docs completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingConceptuallySimilarTab();
		driver.scrollPageToTop();
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		driver.waitForPageToBeReady();
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
	
	@Test(enabled = true, groups = { "regression" }, priority = 8)
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
	
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 9)
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
	
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 10)
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
	
	@Test(enabled = true,dataProvider = "userDetails", groups = { "regression" }, priority = 11)
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

	/**
	 * Author :Jayanthi date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51566
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority =12)
	public void verifyDistinguishedHighlightedTextInDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51566");
		baseClass.stepInfo("Verify user can distinguish this on-demand search highlights with the highlights from the keyword"
				+ " group and persistent search hit highlights");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.docIdThumbnails);
		baseClass.stepInfo("Search with text 'test' completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Purehits viewed in DocView");
		baseClass.waitTime(2);
//		docViewRedact.verifyHighlightedTextsAreDisplayed();
		baseClass.stepInfo("DocView screen  displayed and keywords are highlighted on\r\n"
				+ "	doc view as per the assigned color for the keyword group");
		docViewRedact.verifyHighlightedText_withclick();
		baseClass.stepInfo("On demand search text searched and application highlight the text  and highlights are"
				+ " light red in color as exepcted.");
	}

	@DataProvider(name = "userDetails2")
	public Object[][] userLoginDetails2() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51918
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" }, priority =13)
	public void verifyImagesTabRetainedWhileSaving(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51918");
		baseClass.stepInfo("Verify that when user in on Images tab and save the document then should be on Images tab for the same document");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		docViewRedact.clickingImagesTab();
		DocViewPage docviewpage = new DocViewPage(driver);
		docviewpage.editingCodingFormWithSaveAndNextButton();
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained when document selected from Mini Doc List");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
			
		
	}
	
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51919
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" }, priority =14)
	public void verifyImagesTabRetainedFromMiniDocList(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51919");
		baseClass.stepInfo("Verify that when user in on Images tab and clicks the document to load from mini doc list then should be on Images tab for the same document");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		docViewRedact.clickingImagesTab();
		DocViewPage docviewpage = new DocViewPage(driver);
		docviewpage.selectDocIdInMiniDocList("ID00001186");
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained when document selected from Mini Doc List");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51407
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority =15)
	public void verifyKeywordHighliteIsdisplayedOnHitofPersistentHitIcon(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51407");
		baseClass.stepInfo("Verify on click of the \"eye\" icon, ALL highlighted terms - including those that are set from Manage > Keywords configured to the security group");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		docViewRedact.checkingPersistentHitPanel();
		if(docViewRedact.getKeywordInPersistentHitPanel_test().isDisplayed()) {
			baseClass.passedStep("Keyword assigned to security group is present in the persistent hit panel");
		} else {
			baseClass.failedStep("Keyword assigned to security group is not present in the panel");
		}
	}


/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51750
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority =16)
	public void verifyPersistentHitPanelRetainedWhenDocNumberEntered(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51750");
		baseClass.stepInfo("Verify that on entering the document number when hits panel is open then enable/disable should be retained after loading the document");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		docViewRedact.checkingPersistentHitPanel();
		driver.scrollPageToTop();
		docViewRedact.pageNumberTextBox().waitAndClick(10);
		docViewRedact.pageNumberTextBox().getWebElement().clear();
		docViewRedact.pageNumberTextBox().getWebElement().sendKeys(Input.pageNumber);
		baseClass.stepInfo("Clicked on Analytics panel button and moved to other doc by entering page number");
		driver.waitForPageToBeReady();
		if(docViewRedact.persistantHitToggle().isDisplayed()) {
			baseClass.passedStep("Persistent hit panel is retained after loding the new page by entering page number");
		}
		else {
			baseClass.failedStep("Persistent hit panel is NOT retained after loding the new page by entering page number");
		}
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51114
	 * @throws InterruptedException 
	 * 
	 */
	
	@Test(enabled = true,alwaysRun = true, groups = { "regression" }, priority =15)
	public void verifyDownloadDropdownDocViewAsSA() throws InterruptedException{
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("Test case Id: RPMXCON-51114");
		baseClass.stepInfo("Verify user after impersonation can download the associated files from default view");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.redactionIcon().Displayed() && docViewRedact.redactionIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docViewRedact.downloadIcon());
		docViewRedact.downloadIcon().waitAndClick(5);
		docViewRedact.dropDownDownloadIcon().waitAndClick(5);
		String getAttribute1 = docViewRedact.dropDownDownloadIcon().GetAttribute("onclick");
		System.out.println(getAttribute1);
		if(getAttribute1.contains("GetFilePathAndDownload")) {
			baseClass.passedStep("The download dropdown is clicked and doc is downloaded");
		} else {
			baseClass.failedStep("The dropdown for downloading is not present");
		}
		DocViewPage docviewpage = new DocViewPage(driver);	
		docviewpage.selectDocIdInMiniDocList("ID00001069");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewRedact.downloadIcon());
		docViewRedact.downloadIcon().waitAndClick(10);
		docViewRedact.dropDownDownloadIcon().waitAndClick(5);
		String getAttribute2 = docViewRedact.dropDownDownloadIcon().GetAttribute("onclick");
		System.out.println(getAttribute2);
		if(getAttribute1.contains("GetFilePathAndDownload")) {
			baseClass.passedStep("The download dropdown is clicked and doc is downloaded");
		} else {
			baseClass.failedStep("The dropdown for downloading is not present");
		}	
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50915
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,alwaysRun = true, groups = { "regression" }, priority =16)
	public void verifyNavigationToDocViewAsSAimpersonation() throws InterruptedException, AWTException{
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("Test case Id: RPMXCON-50915");
		baseClass.stepInfo("To verify user can navigate to document for from text view after impersonation");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.textTab().Displayed() && docViewRedact.textTab().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docViewRedact.textTab());
		docViewRedact.textTab().waitAndClick(30);	
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardNextDocBtn());
		docViewRedact.forwardNextDocBtn().waitAndClick(10);	
        docViewRedact.verifyingActiveDocIdInDocView(Input.testSecondDocId);
		baseClass.passedStep("Navigated to next doc in the list while on text tab");	
		driver.scrollPageToTop();
		docViewRedact.pageNumberTextBox().waitAndClick(10);
		docViewRedact.pageNumberTextBox().getWebElement().clear();
		docViewRedact.pageNumberTextBox().getWebElement().sendKeys(Input.pageNumber);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		driver.waitForPageToBeReady();
		docViewRedact.verifyingActiveDocIdInDocView(Input.testTenthDocId);
		baseClass.passedStep("Navigated to expected doc in the list while on text tab");
		String status = docViewRedact.textTab().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("false")) {
			baseClass.passedStep("The text tab is Not retained");
		}
		else {
			baseClass.failedStep("The images tab is retained");
		}
	}
		
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51013
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 17)
	public void verifyImagesTabFromAssignmentAsSA() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		SessionSearch sessionSearch = new SessionSearch(driver);	
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51013");
		baseClass.stepInfo("To verify that after impersonating, system admin can view persistent search on doc view.");
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Search the non audio documents and Create new assignment");
		assignmentsPage.createAssignment(assname, codingForm);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assname);
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.checkingPersistentHitPanel();
		if(docViewRedact.getKeywordInPersistentHitPanel_test().isDisplayed()) {
			baseClass.passedStep("Keyword assigned to security group is present in the persistent hit panel");
		} else {
			baseClass.failedStep("Keyword assigned to security group is not present in the panel");
		}
	
	assignmentsPage.deleteAssgnmntUsingPagination(assname);
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51113
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 17)
	public void verifyImagesTabDropDownAsSA() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51113");
		baseClass.stepInfo("Verify user after impersonation can select any of the menu option from Images tab- Zoom in, Zoom out, rotation, Slider, Fit to Width");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingImagesTab();
	    DocViewPage docviewpage = new DocViewPage(driver);	
		docviewpage.selectDocIdInMiniDocList(Input.testTenthDocId);	
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesIconDocView());
		if(docViewRedact.imagesTabDropDown().isDisplayed()) {
			baseClass.passedStep("images Tab drop down is Visible");
		} else {
			baseClass.failedStep("images Tab drop down is not visible");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesTabZoomOut());
		docViewRedact.imagesTabZoomOut().Click();
		if(docViewRedact.imagesTabZoomOut().isDisplayed()) {
			baseClass.passedStep("images Tab zomm out is Visible");
		} else {
			baseClass.failedStep("images Tab zoom out is not visible");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesTabZoomIn());
		docViewRedact.imagesTabZoomIn().Click();
		if(docViewRedact.imagesTabZoomIn().isDisplayed()) {
			baseClass.passedStep("images Tab zoom in is Visible");
		} else {
			baseClass.failedStep("images Tab zoom in is not visible");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesTabZoomFitToScreen());
		docViewRedact.imagesTabZoomFitToScreen().Click();
		if(docViewRedact.imagesTabZoomIn().isDisplayed()) {
			baseClass.passedStep("images Tab fit to screen option is avilablee");
		} else {
			baseClass.failedStep("images Tab fit to screen option is not avilable");
		}
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-59994
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 18)
	public void verifyRedactionTagSelectionAsSA() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-49994");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when System Admin impersonates as RMU/Reviewer");
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
	}
	
	
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51432
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails", alwaysRun = true , groups = { "regression" }, priority = 19)
	public void verifySearchBoxNotPresent(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51432");
		baseClass.stepInfo("Verify that search box is eliminated from presentation in DocView with no visible remnant");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.getSearchIcon().Visible() && docViewRedact.HighliteIcon().Enabled();
			}
		}), Input.wait30);
	    baseClass.waitTillElemetToBeClickable(docViewRedact.getSearchIcon());
	    docViewRedact.getSearchIcon().waitAndFind(30);
	    if(docViewRedact.getInputSearchBox().Visible()) {
	    	baseClass.failedStep("The search box is present in the default view of DocView");
	    } else {
	    	baseClass.passedStep("The search box is not present in the default view of docview");
	    }    
	    
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50849
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails2", alwaysRun = true , groups = { "regression" }, priority = 20)
	public void verifyAllObjectsDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-50849");
		baseClass.stepInfo("To verify that when user navigates to doc view from the Basic search view, then all the objects on doc view are displayed.");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.HighliteIcon().Visible() && docViewRedact.HighliteIcon().Enabled();
			}
		}), Input.wait30);
	    baseClass.waitTillElemetToBeClickable(docViewRedact.HighliteIcon());
	    docViewRedact.HighliteIcon().waitAndFind(30);
	    if(docViewRedact.HighliteIcon().isDisplayed()) {
	    	baseClass.passedStep("Highlite icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Highlite Icon not visible in DocView");
	    }
	    
	    if(docViewRedact.redactionIcon().isDisplayed()) {
	    	baseClass.passedStep("Redaction icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Redaction Icon not visible in DocView");
	    }
	    
	    if(docViewRedact.getSearchIcon().isDisplayed()) {
	    	baseClass.passedStep("Search icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Search Icon not visible in DocView");
	    }
	    
	    if(docViewRedact.forwardNextDocBtn().isDisplayed()) {
	    	baseClass.passedStep("doc navigation forward icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("doc navigation forwaard not visible in DocView");
	    }
	    
	    if(docViewRedact.backwardPriviousDocBtn().isDisplayed()) {
	    	baseClass.passedStep("Doc Navigation backward visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Doc Navigation Backward not visible in DocView");
	    }
	    
	    if(docViewRedact.docViewNextPage().isDisplayed()) {
	    	baseClass.passedStep("page Navigation forward visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Doc Navigation Backward not visible in DocView");
	    }
	    
	    if(docViewRedact.docViewPreviousPage().isDisplayed()) {
	    	baseClass.passedStep("page Navigation backward visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Doc Navigation Backward not visible in DocView");
	    }
	    
	    if(docViewRedact.pageNumberTextBox().isDisplayed()) {
	    	baseClass.passedStep("page Number box visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("page number box not visible in DocView");
	    }
	    
	}

	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51254
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 21)
	public void verifyTraverseForwardOnHitsAfterImpersonation() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "AAassignment2" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Test case Id: RPMXCON-51254");
		baseClass.stepInfo("Verify user after impersonation can go to next/prvious highlight in doc view when there are multiple hits for each term in context of an assignment");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitTillElemetToBeClickable(docViewRedact.persistantHitToggle());
		docViewRedact.persistantHitToggle().waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().waitAndClick(3);
		String getAttribute = docViewRedact.nextKeywordTest().GetAttribute("position");
		System.out.println(getAttribute);
		if(getAttribute.equalsIgnoreCase("1")) {
			baseClass.passedStep("Sucsessfully moved forward on hits");
		} else {
			baseClass.failedStep("moving forward on hits unsuccessful");
		}
		loginPage.logout();
		
// 	checking hits as PA to RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName2, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName2 + "' is created Successfully");
		assignmentspage.selectAssignmentToViewinDocview(assignmentName2);
		baseClass.stepInfo("Assignment '" + assignmentName2 + "' is successfully viewed on DocView");
		docViewRedact.checkingPersistentHitPanel();
		baseClass.waitTillElemetToBeClickable(docViewRedact.persistantHitToggle());
		docViewRedact.persistantHitToggle().waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(docViewRedact.nextKeywordTest());
		docViewRedact.nextKeywordTest().waitAndClick(3);
		String getAttribute2 = docViewRedact.nextKeywordTest().GetAttribute("position");
		System.out.println(getAttribute2);
		if(getAttribute2.equalsIgnoreCase("1")) {
			baseClass.passedStep("Sucsessfully moved forward on hits");
		} else {
			baseClass.failedStep("moving forward on hits unsuccessful");
		}	
		
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51079
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails2", alwaysRun = true , groups = { "regression" }, priority = 22)
	public void verifyAnnotationDelete(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo("Test case id : RPMXCON-51079");
		baseClass.stepInfo("Verify that by default, the document is simply shows the search icon [magnifying]");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.testTenthDocId);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    docViewRedact.clickingRedactionIcon();
	    docViewRedact.thisPageRedaction().waitAndClick(3);
	    docViewRedact.selectingRectangleRedactionTag();
	    Thread.sleep(3000);
	    actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), -10, -10);
	    actions.click();
		actions.build().perform();
		if (docViewRedact.deleteRedactioBtn().Displayed()) {
			docViewRedact.deleteRedactioBtn().waitAndClick(5);
			baseClass.VerifySuccessMessage("Redaction Removed successfully.");
			baseClass.passedStep("The applied annotation has been successfully removed");
		} else {
			baseClass.failedStep("The applied annotation was not removed");
		}
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51057
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 23)
	public void verifyRedactionOnImpersonationRMUtoREV() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51057");
		baseClass.stepInfo("Verify user after impersonation can view highlighting and can annotate in a document");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionSearch.basicContentSearch(Input.randomText);
		sessionSearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentspage.addReviewerAndDistributeDocsT(assignmentName);
		baseClass.impersonateRMUtoReviewer();
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingRedactionIcon();
		docViewRedact.thisPageRedaction().waitAndClick(3);
	    docViewRedact.selectingRectangleRedactionTag();
	    baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
	    
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51912
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails", alwaysRun = true , groups = { "regression" }, priority = 24)
	public void verifyImagesTabRetainedFromHistoryDropdown(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51912");
		baseClass.stepInfo("Verify that when user in on Images tab and completes the document then should be on Images tab for next navigated document");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    docViewRedact.clickingImagesTab();
	    docViewRedact.miniDoclistTable(4);
	    driver.scrollPageToTop();
	    baseClass.waitTillElemetToBeClickable(docViewRedact.historyDrowDownBtn());
	    docViewRedact.historyDrowDownBtn().waitAndClick(3);
	    docViewRedact.historyDropDownDocSelect(Input.testSecondDocId).waitAndClick(3);
	    String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if(status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained when document selected from history DropDown");
		}
		else {
			baseClass.failedStep("The images tab is NOT retained");
		}
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51348
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails2", alwaysRun = true , groups = { "regression" }, priority = 25)
	public void verifyRemarksPanelRetainedOnDocNavigation(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51348");
		baseClass.stepInfo("Verify when Reviewer Remarks panel is selected from doc view and navigates to another document from mini doc list child window the selected panels/menus previously selected should remain.");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    docViewRedact.clickingRemarksIcon();
	    ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		if (docViewRedact.addRemarksBtn().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep(
					"The remarks panel is visible for non audio documents After navigating from child window is retained");
		} else {
			assertTrue(false);
		}
	    
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51927
	 * Description :Verify that when performing doc-to-doc navigation the entry for the same document in mini-DocList
	 *  must always present fully in the visible area of the mini-DocList (to the user).
	 * @throws InterruptedException 
	 * 
	 */
	@Test(enabled = true,dataProvider = "userDetails", groups = {"regression" },priority = 24)
	public void verifyDocumentPresentAfterDocToDocNavigation(String fullName, String userName, String password) throws InterruptedException {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51927");
		baseClass.stepInfo("Verify the document in minidoclist after performing doc-to-doc navigation");
		
		// Login to the application
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		// Performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		
		// Adding search results and view in docview
		sessionSearch.ViewInDocView();
		
		// Performing doc-to-doc navigation
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_Last().Visible() && docView.getDocView_Last().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_Last());
		docView.getDocView_Last().waitAndClick(30);
		
		driver.waitForPageToBeReady();
		// getting the first available docid
		String selectedId = docView.getDocView_SelectedDocID().getText();
				
		// getting doc id in minidoclist
		
		String activeDocumentId = docViewRedact.activeDocId().getText();
		//Verifying the Docids
		if (selectedId.equalsIgnoreCase(activeDocumentId)) {
			baseClass.passedStep("Presently Viewed Document is present in visible area of the mini-doclist");
		}
		else {
			baseClass.failedStep("Presently Viewed Document is not present in visible area of the mini-doclist");
			}
				
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51353
	 * Description :Verify after impersonation when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected
	 * @throws InterruptedException 
	 * 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 26)
	public void verifyMenuStatusAfterMovingToNextDoc() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51353");
		baseClass.stepInfo("Verify when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected after moving to next document");
		
		// Login to the application
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		
		//Impersonate from SA to RMU and verifying status
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Impersonated from SA to RMU");
		docView.statusCheckAfterImpersonation();
		
		//Login back to the application as SA
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
				
		//Impersonate from SA to Reviewer and verifying status
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Impersonated from SA to Reviewer");
		docView.statusCheckAfterImpersonation();
		
		//Login back to the application as PA
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		//Impersonate from PA to RMU and verifying status
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonated from PA to RMU");
		docView.statusCheckAfterImpersonation();
		
		//Login back to the application as PA
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		//Impersonate from PA to Reviewer and verifying status
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Impersonated from PA to Reviewer");
		docView.statusCheckAfterImpersonation();
		
		//Login back to the application as RMU
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		//Impersonate from RMU to Reviewer and verifying status
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated from RMU to Reviewer");
		docView.statusCheckAfterImpersonation();
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51347
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails", alwaysRun = true , groups = { "regression" }, priority = 24)
	public void verifyPersistentPanelRetainedOnDocNavigation(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51347");
		baseClass.stepInfo("Verify Persistent Hit panel is selected from doc view and navigates to another document from mini doc list child window the selected panels/menus previously selected should remain.");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    docViewRedact.checkingPersistentHitPanel();
	    ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
		reusabledocviewpage.clickGearIconOpenMiniDocList();
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		if (docViewRedact.persistantHitToggle().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep(
					"The persistent hits panel is visible for non audio documents After navigating from child window is retained");
		} else {
			assertTrue(false);
		}
	    
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51351
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" }, priority = 25)
		public void verifyRemarksPanelRetained(String fullName, String userName, String password)throws Exception {
			baseClass = new BaseClass(driver);
			loginPage.loginToSightLine(userName, password);
			baseClass.stepInfo("login as" + fullName);
			DocViewRedactions docViewRedact = new DocViewRedactions(driver);
			ReusableDocViewPage reusabledocviewpage = new ReusableDocViewPage(driver);
			baseClass.stepInfo("Test case Id: RPMXCON-51351");
			baseClass.stepInfo(
					"Verify when Reviewer Remarks panel is selected from audio doc view and navigates to another document from mini doc list child window the selected panels/menus previously selected should remain.");
			SessionSearch sessionsearch = new SessionSearch(driver);
			sessionsearch.audioSearch("Morning", Input.language);
			baseClass.stepInfo("Search for audio docs completed");
			sessionsearch.ViewInDocView();
			docViewRedact.clickingAudioRemarksIcon();
			reusabledocviewpage.clickGearIconOpenMiniDocList();
			docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
			if (docViewRedact.addAudioRemarks().isDisplayed()) {
				assertTrue(true);
				baseClass.passedStep(
						"The remarks panel is visible for audio documents After navigating from child window");
			} else {
				assertTrue(false);
			}

		}


	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51859
	 * Description :Doc View: When users locale is German then on navigating to Doc View should not display warning message.
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 26)
	public void verifyWarningMessageDisplay() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case id :RPMXCON-51859");
		baseClass.stepInfo("When users locale is German then on navigating to Doc View should not display warning message");
		
		// Login to the application
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		
		//Changing local to german as pre-requisite
		loginPage.editProfile("German - Germany");
		baseClass.stepInfo("Successfully selected German Language");
		
		// Performing basic search
		sessionSearch.basicContentSearch(Input.testData1);
		
		// Adding search results and view in docview
		sessionSearch.ViewInDocViewGerman();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to DocView");
		
		//verifying whether warning message displayed		
		if(baseClass.getSuccessMsgHeader().isDisplayed()) {
			String warningMessage = baseClass.getSuccessMsgHeader().getText().toString();
			baseClass.stepInfo(warningMessage);
			baseClass.failedStep("Warning Message Displayed");
		}
		else {
			baseClass.passedStep("Warning Message Not Displayed");
		}
		
		//setting back to default locale
		loginPage.editProfile("English - United States");
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
