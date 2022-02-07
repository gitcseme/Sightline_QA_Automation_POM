package testScriptsRegression;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
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
		loginPage.logout();
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
		loginPage.logout();

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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();	
		
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
		
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
	loginPage.logout();
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
		loginPage.logout();
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
		loginPage.logout();
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
	    loginPage.logout();	    
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
	    loginPage.logout();
	    
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
		loginPage.logout();
		
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
		loginPage.logout();
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
	    loginPage.logout();	    
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
		loginPage.logout();
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
		loginPage.logout();	    
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
		loginPage.logout();
				
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
		loginPage.logout();
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
		loginPage.logout();	    
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
			loginPage.logout();
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
		loginPage.logout();
	}
	
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51115
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 27)
	public void verifyPrintIconAfterDisablingToggle() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51115");
		baseClass.stepInfo("Verify user after impersonation can go to next/prvious highlight in doc view when there are multiple hits for each term in context of an assignment");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		docViewRedact.DisablePrintToggleinAssignment();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);	
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		if (docViewRedact.get_PrintIcon().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep("The print Icon is not present in DoocView as expected");
		} else {
			assertTrue(false);
			baseClass.failedStep("The print Icon is present in DoocView - failed");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		if (docViewRedact.get_PrintIcon().isDisplayed()) {
			assertTrue(true);
			baseClass.passedStep("The print Icon is not present in DoocView as expected");
		} else {
			assertTrue(false);
			baseClass.failedStep("The print Icon is present in DoocView - failed");
		}
		loginPage.logout();

	}

	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-48810
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" }, priority =28)
	public void verifyTextRedactionFunctionDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-48811");
		baseClass.stepInfo("Verify that \"Text Redaction\" functionality is working proper on DocView Screen.");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.duplicateDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		docViewRedact.redactTextUsingOffset();
		baseClass.stepInfo("Text Redaction applied");
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("Default Redaction tag selected successfully");
		baseClass.passedStep("Text redaction has been performed by RMU user and Redaction Tag saved successfully");
		baseClass.waitForElement(docViewRedact.btnTypeOfRedaction("Text Redaction"));
		String color = docViewRedact.btnTypeOfRedaction("Text Redaction").getWebElement().getCssValue("color");
		String hex = Color.fromString(color).asHex();
		System.out.println(hex);
		if (hex.equalsIgnoreCase(Input.iconColor)) {
			baseClass.passedStep("The icon turned to red colour as expected- Successfully");
		} else {
			baseClass.failedStep("The icon NOT turned to red colour as expected");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50774
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyAssignmentDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50774");
		baseClass.stepInfo("To verify that 'View All Doc in Doc View' option is disabled if assignment is not added in the list");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		String verifydocsCountInAssgnPage = assignmentspage.verifydocsCountInAssgnPage(assignmentName);
	
		if(verifydocsCountInAssgnPage.equalsIgnoreCase("201")) {
			baseClass.passedStep("The doc count has been verified");
		} else {
			baseClass.failedStep("The doc count is not verified");
		}
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.passedStep("Assignment viewed in DocView Successfully");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50776
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 14)
	public void verifyAssignmentPageViewInDocView() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50776");
		baseClass.stepInfo("To verify that 'View All Doc in Doc View' option is disabled if assignment is not added in the list");
		docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		assignmentspage.navigateToAssignmentsPage();
		driver.scrollPageToTop();
		assignmentspage.getAssignmentActionDropdown().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return assignmentspage.getAssignmentAction_ViewinDocView().Visible();
			}
		}), Input.wait60);
		Assert.assertTrue(assignmentspage.getAssgnAction_Export().isDisplayed());
		String getAttribute = assignmentspage.getAssignmentAction_ViewinDocView().GetAttribute("disabled");
		System.out.println(getAttribute);
		if(getAttribute == null) {
			assignmentspage.getAssignmentAction_ViewinDocView().waitAndClick(3);
			baseClass.VerifyWarningMessage("Please select at least one assignment from the displayed assignment list");
			
		} else {
			baseClass.passedStep("No assignments pre esist for the SG. The view in doc view Icon is disabled");
		}	
		loginPage.logout();
	}
	/**
	 * Author : Iyappan.Kasinathan
	 * Description : Verify on click of the reviewer remark respecive page should be scrolled in doc view when redirecting from basic search/saved search/doc list
	 */
	@Test(enabled = true, dataProvider = "UsersWithoutPA",alwaysRun = true, groups = { "regression" }, priority = 29)
	public void VerifyReviewerRemarksPageFromSearchPg(String userName, String password, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51083");
		baseClass.stepInfo(" Verify on click of the reviewer remark respecive page should be scrolled in doc view when redirecting from basic search/saved search/doc list");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		String remarksName = "remarks"+Utility.dynamicNameAppender();
		loginPage.loginToSightLine(userName, password);
		sessionsearch.basicContentSearch("null");
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_PageNumber());
		docViewRedact.getDocView_PageNumber().SendKeys("4");
		docViewRedact.getSearchIcon().waitAndClick(5);
		docViewRedact.clickingRemarksIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.getDocView_Redactrec_textarea());
		Thread.sleep(4000);
		//Thread sleep added for the page to adjust resolution
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
		.moveByOffset(200, 90).release().build().perform();
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys(remarksName);
		actions.build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.saveRemarksBtn());
		docViewRedact.saveRemarksBtn().waitAndClick(10);	
		baseClass.stepInfo("Remarks added successfully");
		baseClass.waitForElement(docView.getDocView_SelectRemarks(remarksName));
		docView.getDocView_SelectRemarks(remarksName).waitAndClick(10);
		String id= docView.getRemarksId(remarksName).GetAttribute("id");
		if(docView.getRemarksInPg(id).isElementAvailable(5)) {
			baseClass.passedStep("The page is loaded sucessfully where the remarks is added");
		}else {
			baseClass.failedStep("The page is not loaded where the remarks is added");
		}
		docViewRedact.clickingRemarksIcon();
		docView.deleteReamark(remarksName);
		loginPage.logout();
	}
	/**
	 * Author : Iyappan.Kasinathan
	 * Description : Verify on click of the reviewer remark respective page should be scrolled in doc view when redirecting from my assignment
	 */
	@Test(enabled = true, dataProvider = "UsersWithoutPA",alwaysRun = true, groups = { "regression" }, priority = 30)
	public void VerifyReviewerRemarksPageFromAssignments(String userName, String password, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51082");
		baseClass.stepInfo("Verify on click of the reviewer remark respective page should be scrolled in doc view when redirecting from my assignment");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String remarksName = "remarks"+Utility.dynamicNameAppender();
		loginPage.loginToSightLine(userName, password);
		//create assignment
		if(fullName.contains("RMU")) {
		sessionsearch.basicContentSearch("null");
		sessionsearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate to reviewer
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		assignmentPage.assgnViewInAllDocView();
		baseClass.stepInfo("Navigated to docview from assignment page successfully");
		driver.waitForPageToBeReady();
		}else {
			assignmentPage.SelectAssignmentByReviewer(assignmentName);
			baseClass.stepInfo("User on the doc view after selecting the assignment");
		}
		baseClass.waitForElement(docViewRedact.getDocView_PageNumber());
		docViewRedact.getDocView_PageNumber().SendKeys("4");
		docViewRedact.getSearchIcon().waitAndClick(5);
		docViewRedact.clickingRemarksIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.getDocView_Redactrec_textarea());
		Thread.sleep(4000);
		//Thread sleep added for the page to adjust resolution
		Actions actions = new Actions(driver.getWebDriver());
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).clickAndHold()
		.moveByOffset(200, 90).release().build().perform();
		actions.moveToElement(docViewRedact.addRemarksBtn().getWebElement());
		actions.click().build().perform();
		actions.moveToElement(docViewRedact.addRemarksTextArea().getWebElement());
		actions.click();
		actions.sendKeys(remarksName);
		actions.build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.saveRemarksBtn());
		docViewRedact.saveRemarksBtn().waitAndClick(10);	
		baseClass.stepInfo("Remarks added successfully");
		baseClass.waitForElement(docView.getDocView_SelectRemarks(remarksName));
		docView.getDocView_SelectRemarks(remarksName).waitAndClick(10);
		String id= docView.getRemarksId(remarksName).GetAttribute("id");
		if(docView.getRemarksInPg(id).isElementAvailable(5)) {
			baseClass.passedStep("The page is loaded sucessfully where the remarks is added");
		}else {
			baseClass.failedStep("The page is not loaded where the remarks is added");
		}
		docViewRedact.clickingRemarksIcon();
		docView.deleteReamark(remarksName);
		if(fullName.contains("REV")) {
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		}
		loginPage.logout();
	}
	@DataProvider(name = "UsersWithoutPA")
	public Object[][] UsersWithoutPA() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName }, { Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}	
	
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51046
	 * Description :Verify user can not see the keywords highlighted outside of an assignment when keyword groups assigned to the assignment only
	 * @throws InterruptedException 
	 * @throws AWTException 
	 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 27)
	public void verifyKeywordsHighlightingWhenMappedToAssignment() throws InterruptedException, AWTException {
		
		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);
		
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		
		//Pre-requisites
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51046");
		baseClass.stepInfo("Verify keywords highlighting after keywords mapped to the assignment");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		assignPage.getAssgn_Keywordsbutton().ScrollTo();
		assignPage.getAssgn_Keywordsbutton().isElementAvailable(10);
		assignPage.getAssgn_Keywordsbutton().waitAndClick(10);
		baseClass.waitForElement(assignPage.getAssgn_Keywordspopup());
		driver.waitForPageToBeReady();
		List<String> values =baseClass.availableListofElements(assignPage.getAssgn_AllKeywords());
		baseClass.stepInfo("Mapped keywords for the assignment are " +values);
		assignPage.getAssgn_Keywordokbutton().ScrollTo();
		assignPage.getAssgn_Keywordokbutton().isElementAvailable(10);
		assignPage.getAssgn_Keywordokbutton().Click();
		keywordPage.getYesButton().Click();
		driver.waitForPageToBeReady();
		assignPage.addReviewerAndDistributeDocs();
		baseClass.stepInfo("Added reviewer and distributed docs");
		loginPage.logout();
		
		//Login as RMU and verify keyword Highlighting
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined as RMU");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		// click eye icon and verify the highlighting of search term
		docView.getPersistentHit(Input.testData1);
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();
		
		//login as reviewer and verify keyword highlighting
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logined as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		// click eye icon and verify the highlighting of search term
		docView.getPersistentHit(Input.testData1);
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51045
	 * Description :Verify user can not see the keywords highlighted in context of an assignment when keyword group assigned to the security group only
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 28)
	public void verifyKeywordsHighlightingWhenUnMappedFromAssignment() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);
		
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		
		//Pre-requisites
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51045");
		baseClass.stepInfo("Verify keywords highlighting when keywords are not mapped to the assignment");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.unmappingKeywordsFromAssignment(assignmentName);
		assignPage.addReviewerAndDistributeDocs();
		baseClass.waitForElement(assignPage.getAssignmentSaveButton());
		assignPage.getAssignmentSaveButton().Click();
		driver.waitForPageToBeReady();
		loginPage.logout();
			
		//Login as RMU and verify keyword Highlighting
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined as RMU");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		// click eye icon and verify the highlighting of search term
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getPersistantHitEyeIcon().Displayed();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		
		if(docView.getPersistentToogle().isDisplayed()) {
			baseClass.failedStep("Keywprds are highlighted while checking as RMU");
		}
		else
		{
			baseClass.passedStep("Keywords are not highlighted while checking as RMU");
		}	
		loginPage.logout();
				
		//login as reviewer and verify keyword highlighting
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logined as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();

		// click eye icon and verify the highlighting of search term
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getPersistantHitEyeIcon().Displayed();
			}
		}), Input.wait30);		
		baseClass.waitTillElemetToBeClickable(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		if(docView.getPersistentToogle().isDisplayed()) {
			baseClass.failedStep("Keywprds are highlighted while checking as reviewer");
		}
		else
		{
			baseClass.passedStep("Keywords are not highlighted while checking as reviewer");
		}
		loginPage.logout();
		
	}
/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51557
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority =28)
	public void verifySearchIconGreyedForTiff(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51557");
		baseClass.stepInfo("Verify that by default, the document is simply shows the search icon [magnifying]");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("DocID", null, Input.TiffDocId, null);
		sessionsearch.ViewInDocView();
		baseClass.waitTillElemetToBeClickable(docViewRedact.getSearchIconDisabled());
		String SearchIcon = docViewRedact.getSearchIconDisabled().GetAttribute("class");
		if (SearchIcon.contains("disabled")) {
			baseClass.passedStep("The search text icon is disabled for Tiff images - as expected");
		} else {
			baseClass.failedStep("Search Icon not disabled for Tiff images");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51267
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 29)
	public void verifyUniCodeFilesViewInDocView() throws Exception {
		baseClass = new BaseClass(driver);
//	 checking hits as SA to PA		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Test case Id: RPMXCON-51267");
		baseClass.stepInfo("Verify after impersonation user view unicode files in near native view of doc view");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Sucsessfully Viewed Uni code file - SA to PA");
		loginPage.logout();

//	 checking hits as SA to RMU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Sucsessfully Viewed Uni code file - SA to RMU");
		loginPage.logout();

//	checking hits as SA to Rev
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoReviewer();
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Sucsessfully Viewed Uni code file - SA to RMU");
		loginPage.logout();

//		 checking hits as PA to Rev
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Sucsessfully Viewed Uni code file - SA to RMU");
		loginPage.logout();

// 	checking hits as PA to RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Sucsessfully Viewed Uni code file - SA to RMU");
		loginPage.logout();

//		 checking hits as RMU to Rev
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.impersonateRMUtoReviewer();
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Sucsessfully Viewed Uni code file - SA to RMU");
		loginPage.logout();

	}/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50787
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true,dataProvider = "userDetails2", alwaysRun = true , groups = { "regression" }, priority = 30)
	public void verifyDocViewOptions(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-50787");
		baseClass.stepInfo("To verify options available on menu bar from document view panel of doc view page");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		sessionSearch.basicContentSearch(Input.searchString1);
	    sessionSearch.ViewInDocView();
	    docViewRedact = new DocViewRedactions(driver);
	    driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.thumbNailsIcon().Visible() && docViewRedact.thumbNailsIcon().Enabled();}
		}), Input.wait30);
	    baseClass.waitTillElemetToBeClickable(docViewRedact.thumbNailsIcon());
	    docViewRedact.HighliteIcon().waitAndFind(30);
	    if(docViewRedact.HighliteIcon().isDisplayed()) {
	    	baseClass.passedStep("Thumbnails icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Thumbnails Icon not visible in DocView");  }    
	    if(docViewRedact.zoomInDocView().isDisplayed()) {
	    	baseClass.passedStep("Zoom in icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Zoom in Icon not visible in DocView");  }    
	    if(docViewRedact.zoomOutDocView().isDisplayed()) {
	    	baseClass.passedStep("Zoom out visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Zoom out Icon not visible in DocView"); }    
	    if(docViewRedact.zoomFitToScreenDocView().isDisplayed()) {
	    	baseClass.passedStep("Fit to screen icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Fit to screen forwaard not visible in DocView");} 
	    if(docViewRedact.rotateClockWise().isDisplayed()) {
	    	baseClass.passedStep("Rotate Clock wise visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Rotate clock wise not visible in DocView");} 
	    if(docViewRedact.rotateAntiClockWise().isDisplayed()) {
	    	baseClass.passedStep("Rotate AntiClock visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Rotate AntiClock not visible in DocView"); }
	    loginPage.logout();
	    
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50787
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	
	@Test(enabled = true, alwaysRun = true , groups = { "regression" }, priority = 31)
	public void verifyDocViewOptions() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-50787");
		baseClass.stepInfo("To verify options available on menu bar from document view panel of doc view page");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SessionSearch sessionSearch = new SessionSearch(driver);	
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionSearch.basicContentSearch(Input.randomText);
		sessionSearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.passedStep("Assignment viewed in DocView Successfully");
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
	    docViewRedact = new DocViewRedactions(driver);
	    driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.thumbNailsIcon().Visible() && docViewRedact.thumbNailsIcon().Enabled();
			}
		}), Input.wait30);
	    baseClass.waitTillElemetToBeClickable(docViewRedact.thumbNailsIcon());
	    docViewRedact.thumbNailsIcon().waitAndFind(30);
	    if(docViewRedact.HighliteIcon().isDisplayed()) {
	    	baseClass.passedStep("Thumbnails icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Thumbnails Icon not visible in DocView");  }    
	    if(docViewRedact.zoomInDocView().isDisplayed()) {
	    	baseClass.passedStep("Zoom in icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Zoom in Icon not visible in DocView");  }    
	    if(docViewRedact.zoomOutDocView().isDisplayed()) {
	    	baseClass.passedStep("Zoom out visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Zoom out Icon not visible in DocView"); }    
	    if(docViewRedact.zoomFitToScreenDocView().isDisplayed()) {
	    	baseClass.passedStep("Fit to screen icon visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Fit to screen forwaard not visible in DocView");} 
	    if(docViewRedact.rotateClockWise().isDisplayed()) {
	    	baseClass.passedStep("Rotate Clock wise visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Rotate clock wise not visible in DocView");} 
	    if(docViewRedact.rotateAntiClockWise().isDisplayed()) {
	    	baseClass.passedStep("Rotate AntiClock visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Rotate AntiClock not visible in DocView"); }   
		
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
		
		 if(docViewRedact.remarksIcon().isDisplayed()) {
	    	baseClass.passedStep("Remarks iocn visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Remarks icon not visible in DocView"); }
	    
	    if(docViewRedact.persistantHitBtn().isDisplayed()) {
	    	baseClass.passedStep("Persistent hit button visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("persistent hit button not visible in DocView"); }
	    
	    if(docViewRedact.downloadIcon().isDisplayed()) {
	    	baseClass.passedStep("Download iocn visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("Download icon not visible in DocView"); }
		
		 if(docViewRedact.printIcon().isDisplayed()) {
	    	baseClass.passedStep("print iocn visible in DocView"); 	
	    } else {
	    	baseClass.failedStep("print icon not visible in DocView"); }
		 loginPage.logout();
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50785
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 32)
	public void verifyAssignmentViewInDocViewZero() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50785");
		baseClass.stepInfo("To verify that on selecting 'View All Doc in Doc View' option when documents are not mapped to the assignment");
		docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		assignmentspage.createAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		String verifydocsCountInAssgnPage = assignmentspage.verifydocsCountInAssgnPage(assignmentName);
		if(verifydocsCountInAssgnPage.equalsIgnoreCase("0")) {
			baseClass.passedStep("The doc count has been verified");
		} else {
			baseClass.failedStep("The doc count is not verified");
		}
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.VerifyWarningMessage("Please select an assignment which contains documents assigned to it.");	
		assignmentspage.deleteAssgnmntUsingPagination(assignmentName);	
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51305
	 * Description :Verify persistent Hit panel of DocView should present only content terms, not metadata terms when navigating from advance search 
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = {"regression" },priority = 30)
	public void verifyMetaDataTermOnPersistentPanel(String fullName, String userName, String password) throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51305");
		baseClass.stepInfo("Verify metadata terms on persistent panel when navigating from advance search");
		sessionsearch.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.metaDataCustodianNameInput);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docView");
		docView.verifyMetaDataTermDisplayingOnPersistentPanel(Input.metaDataCustodianNameInput);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51324
	 * Description :Verify all hits of the document should be highlighted without clicking the eye icon when user redirects to doc view from advance search
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = {"regression" },priority = 31)
	public void verifyHighlightingWhenNavigatingFromAdvancedSearch(String fullName, String userName, String password) throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(userName,password);
		baseClass.stepInfo("Test case Id: RPMXCON-51324");
		baseClass.stepInfo("Verify keywords highlighting when navigating from advanced search");
		sessionsearch.advancedContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docView");
		docView.verifyTermHitsHighlightingInDocumentWithoutClickingEyeIcon(Input.searchString1);
		loginPage.logout();		
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51012
	 * Description :To verify that persistent search should be displayed on doc view if user navigates from Saved Search-Doc View.
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 32)
	public void verifyHighlightingWhenNavigatingFromSavedSearch() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		savedsearch = new SavedSearch(driver);
		String searchName = "Atestsearch" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51012");
		baseClass.stepInfo("verify that persistent search should be displayed on doc view if user navigates from Saved Search-Doc View");
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.saveSearch(searchName);
		savedsearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to docView");
		docView.verifyTermHitsHighlightingInDocumentAfterClickingEyeIcon(Input.testData1);
		int availableTermwithCount = docView.getPersistantNames().size();
		if(availableTermwithCount>0) {
			baseClass.passedStep("Hit terms with count displayed on the persistent panel");
		}
		else {
			baseClass.failedStep("Hit terms with count not displayed on the persistent panel");
		}
		loginPage.logout();		
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47878 To verify that when redaction control in red "on" state, if the icon is clicked again by the user, it must revert to an "off" state
	 * DocView Page
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 33)
	public void VerifyOnColourChangeInRedactionMenu() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		Actions actions = new Actions(driver.getWebDriver());
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47878");
		baseClass.stepInfo("To verify that when redaction control in red \"on\" state, if the icon is clicked again by the user, it must revert to an \"off\" state");
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
			baseClass.passedStep("The multipage icon turned back to normal colour on unselecting as expected- Successfully");
		} else {
			baseClass.failedStep("The multipage icon NOT turned to normal  colour as expected");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49974 
	 * Part of 7.1: Verify that when enters only ‘Page Range’ from multi-page redactions pop up then for entered page range redaction should be applied
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 34)
	public void VerifyMultiPageRedaction() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49974");
		baseClass.stepInfo("Part of 7.1: Verify that when enters only ‘Page Range’ from multi-page redactions pop up then for entered page range redaction should be applied");
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
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51047
	 * Description :Verify keyword highlighting color from doc view when mapped keywords are having same word with different color
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 35)
	public void verifyHighlightingWhenSameKeywordsHaveDifferentColor() throws AWTException, InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		savedsearch = new SavedSearch(driver);
		keywordPage = new KeywordPage(driver);
		
		String keywordGroupName1 = "firstGroup"+utility.dynamicNameAppender();
		String keywordGroupName2 = "secondGroup"+utility.dynamicNameAppender();
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
	    String[] Color= {Input.keywordColor1,Input.KeyWordColour};
		String[] keywordGroupName= {keywordGroupName1,keywordGroupName2};
		
		//Pre-requisite
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51047");
		baseClass.stepInfo("Verify keyword highlighting color from doc view when mapped keywords are having same word with different color");
		keywordPage.addTwoSameKeywordWithDifferentColor(keywordGroupName, Input.testData1, Color);
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.unmappingKeywordsFromAssignment(assignmentName);
		assignPage.mappingKeywordToAssignment(keywordGroupName1);
		driver.waitForPageToBeReady();
		assignPage.mappingKeywordToAssignment(keywordGroupName2);
		driver.waitForPageToBeReady();
		assignPage.addReviewerAndDistributeDocs();
		baseClass.waitForElement(assignPage.getAssignmentSaveButton());
		assignPage.getAssignmentSaveButton().Click();
		loginPage.logout();
		//Login as RMU and verify keyword color Highlighting
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined as RMU");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();
		//Login as Reviewer and verify keyword color Highlighting
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logined as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docView.getPersistantHitEyeIcon());
		docView.getPersistantHitEyeIcon().waitAndClick(30);
		driver.waitForPageToBeReady();
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51461
	 * Description :Verify that a  DocView functionality is working properly through assignments. like   - All Panels Verifications with child window / Complete
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 36)
	public void verifyDocviewFunctionalityThroughAssignments() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
	    
		//Pre-requisite
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51461");
		baseClass.stepInfo("Verify that a  DocView functionality is working properly through assignments");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		assignPage.addReviewerAndDistributeDocs();
		baseClass.waitForElement(assignPage.getAssignmentSaveButton());
		assignPage.getAssignmentSaveButton().Click();
		loginPage.logout();
		//Login as reviewer and verify
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logined as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		docView.popOutCodingFormPanel();
		docView.editCodingFormComplete();
		docView.verifyCheckMarkIconFromMiniDocListChildWindow();
		int checkMarkIconCount = docView.getCheckMarkIcon().size();
		baseClass.waitForElement(docView.getDocView_Last());
		docView.getDocView_Last().Click();
		driver.waitForPageToBeReady();
		docView.verifyCodingFormPanelIsLoadedAfterDocsAreViewed();
		docView.editCodingFormComplete();
		baseClass.waitForElementCollection(docView.getCheckMarkIcon());
		int checkMarkIconsAfterEditingCF = docView.getCheckMarkIcon().size();
		if(checkMarkIconsAfterEditingCF>checkMarkIconCount) {
			baseClass.passedStep("Check mark icon displayed for completed document");
		}
		loginPage.logout();		
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49971
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 37)
	public void VerifyRectangleRedactionTagSelection() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49971");
		baseClass.stepInfo("Verify that when applies ‘Rectangle’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.redactRectangleUsingOffset(0, 0, 100, 50);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleRedactionTagSelect().Visible() && docViewRedact.rectangleRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(docViewRedact.rectangleRedactionTagSelect().getWebElement());
		String attribute = redactionTag.getFirstSelectedOption().getAttribute("text");
		System.out.println(attribute);
		if(attribute.equalsIgnoreCase("Default Redaction Tag")) {
			baseClass.passedStep("The first selected redaction tag is Default Redaction Tag");
		} else {
			baseClass.failedStep("The first selected redaction tag is NOT Default Redaction Tag");
		}
		loginPage.logout();
	
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49973
	 *
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 38)
	public void VerifyMultiPageRedactionTagSelection() throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49973");
		baseClass.stepInfo("Part of 7.1: Verify that when applies ‘Multi Page’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
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
		
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-49972 
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 39)
	public void VerifyThisPageRedactionTagSelection() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49972");
		baseClass.stepInfo("Verify that when applies ‘This Page’ redaction for the first time then application should automatically select the ‘Default Redaction Tag’");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search with text input is completed");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingRedactionIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.multiPageIcon());
		docViewRedact.thisPageRedaction().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.rectangleRedactionTagSelect().Visible() && docViewRedact.rectangleRedactionTagSelect().Enabled();
			}
		}), Input.wait30);
		docViewRedact.rectangleRedactionTagSelect().waitAndFind(10);
		Select redactionTag = new Select(docViewRedact.rectangleRedactionTagSelect().getWebElement());
		String attribute = redactionTag.getFirstSelectedOption().getAttribute("text");
		System.out.println(attribute);
		if(attribute.equalsIgnoreCase("Default Redaction Tag")) {
			baseClass.passedStep("The first selected redaction tag is Default Redaction Tag");
		} else {
			baseClass.failedStep("The first selected redaction tag is NOT Default Redaction Tag");
		}}
		
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-47725
	 * 
	 */
	@Test(enabled = true, alwaysRun = true, groups = { "regression" }, priority = 40)
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
		docViewRedact.redactRectangleUsingOffset(0, 0, 200, 100);
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
	 * @Author : Krishna date: 31/01/2021 Modified date: NA Modified by:
	 * @Description:Verify assignment progress bar refresh after completing the
	 *                     document same as last prior documents should be completed
	 *                     by applying coding stamp after selecting code same as
	 *                     this action
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyAssignmentProgressBarRefreshApplying() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51278");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document same as last prior documents "
						+ "should be completed by applying coding stamp after selecting code same as this action");

		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String filedText = "Stamp" + Utility.dynamicNameAppender();
		String colour = "RED";

		// Login as Reviewer Manager
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("StSearching documents based on search string and Navigate to DocView");
		sessionsearch.basicContentSearch(searchString);
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		baseClass.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		System.out.println(assname);

		// Assignment selected by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);
		docView.getSelectedDocIdInMiniDocList();
		baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(1));
		docView.getDocView_MiniDoc_SelectRow(1).waitAndClick(10);
		docView.stampColourSelection(filedText, Input.stampColour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);

		driver.waitForPageToBeReady();
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_MiniDoc_Selectdoc(3));
		docViewRedact.getDocView_MiniDoc_Selectdoc(3).waitAndClick(5);
		baseClass.stepInfo("Selected document in minidoclist");

		// click to apply coding in selected doc
		docView.stampColourSelection(filedText, colour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(1));
		docView.getDocView_MiniDoc_SelectRow(1).waitAndClick(10);
		baseClass.stepInfo("Selected document in minidoclist");

		// click to apply coding in selected doc
		docView.stampColourSelection(filedText, Input.stampColour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);
		driver.waitForPageToBeReady();

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_MiniDoc_Selectdoc(3));
		docViewRedact.getDocView_MiniDoc_Selectdoc(3).waitAndClick(5);
		docView.stampColourSelection(filedText, colour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);
	}
	
	/**
	 * @Author : Sai Krishna date: 02/02/2021 Modified date: NA Modified by:
	 * @Description:Verify assignment progress bar refreshesh after completing the
	 *                     document on applying coding stamp after selecting code
	 *                     same as this action
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyAssignmentProgressBarRefreshApplyingCodingStamp() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51277");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document on applying coding stamp after selecting code same as this action");

		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("StSearching documents based on search string and Navigate to DocView");
		sessionsearch.basicContentSearch(searchString);
		sessionsearch.bulkAssign();

		// create Assignment and distribute doc
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		baseClass.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		System.out.println(assname);

		// Assignment selected by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);
		docView.getSelectedDocIdInMiniDocList();
		baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(1));
		docView.getDocView_MiniDoc_SelectRow(1).waitAndClick(10);
		docView.stampColourSelection(filedText, Input.stampColour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in using Reviewer account");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		baseClass.waitForElement(docView.getDocView_MiniDoc_SelectRow(1));
		docView.getDocView_MiniDoc_SelectRow(1).waitAndClick(10);
		baseClass.stepInfo("Selected document in minidoclist");

		// click to apply coding in selected doc
		docView.stampColourSelection(filedText, Input.stampColour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);

	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51961
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority =43)
	public void verifyDocsWithHiddenProperties(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51961");
		baseClass.stepInfo("Verify that document having any of the field value \"Hidden Properties\" \"ExcelProtectedSheets\" ExcelProtectedWorkbook should display alert message");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("DocID", null, Input.DocIdWithHiddenContent, null);
		sessionsearch.ViewInDocView();
	    baseClass.VerifyWarningMessage("The document has the following hidden information that is presented in the Viewer.");
	}
	
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51959
	 * 
	 */
	@Test(enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" }, priority =44)
	public void verifyHiddenInfoIcon(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51959");
		baseClass.stepInfo("Verify that a Hidden Info icon should not be presented in the action icon section of Doc View if the document being viewed has not any Hidden Content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.TextHidden);
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.doclistTable(2).Visible() && docViewRedact.doclistTable(2).Enabled();
			}
		}), Input.wait30);
		docViewRedact.doclistTable(2).waitAndFind(10);
		docViewRedact.doclistTable(2).Click();
		if(docViewRedact.hiddenInfoIcon().isDisplayed() == false) {
			baseClass.passedStep("Hidden info icon is not visible for document without hidden content");
		} else {
			baseClass.failedStep("Hidden info Icon is visible for document with hidden content");
		}
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.doclistTable(3).Visible() && docViewRedact.doclistTable(3).Enabled();
			}
		}), Input.wait30);
		docViewRedact.doclistTable(3).waitAndFind(10);
		docViewRedact.doclistTable(3).Click();
		if(docViewRedact.hiddenInfoIcon().isDisplayed() == true) {
			baseClass.passedStep("Hidden info Icon is visible for document with hidden content");
		} else {
			
			baseClass.passedStep("Hidden info icon is not visible for document with hidden content");
		}


	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify when navigation to the document with hidden content is done after completing the document, 
	 *               should display the warning message to indicate that document is having hidden content
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void verifyWarningMsgOfHiddenDocs() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51957");
		baseClass.stepInfo("Verify when navigation to the document with hidden content is done after completing the document, should display the warning message to indicate that document is having hidden content");

		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SoftAssert softassertion = new SoftAssert();
		docView = new DocViewPage(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();
		System.out.println(assname);
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.closeAdpopUp();
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.closeAdpopUp();
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Basic Search and select the pure hit count
		sessionsearch.basicContentSearch(Input.TextHidden);
		sessionsearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, Input.codingFormName);
		assignmentsPage.toggleCodingStampEnabled();
		assignmentsPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("Assignment created with required documents and distributed to reviewer successfully");
		loginPage.logout();
		// Login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.selectproject(Input.additionalDataProject);
		// create Assignment and distribute doc
		// Assignment selected by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeCompleteAction=docView.getDocView_CurrentDocId().getText();
		docView.editCodingForm(Input.searchString1);
		baseClass.stepInfo("Coding form is edited successfully");
		docView.stampColourSelection(filedText, Input.stampColour);
		baseClass.CloseSuccessMsgpopup();
		//validation after complete action
		docView.completeButton();
		baseClass.stepInfo("After editing the coding form complete action is performed");
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCompleteAction=docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCompleteAction, docIdAfterCompleteAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		//Validation after hidden info icon
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().waitAndClick(5);
		baseClass.stepInfo("Hidden info action is performed");
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.passedStep("Warning message is displayed as expected");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeCodeSameAsLastAction=docView.getDocView_CurrentDocId().getText();
		//Validation after code same as last action
		docView.clickCodeSameAsLast();
		baseClass.stepInfo("Code same as last action is performed");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCodeSameAsLastAction=docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCodeSameAsLastAction, docIdAfterCodeSameAsLastAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeSavedStampAction=docView.getDocView_CurrentDocId().getText();
		//Validation after saved stamp action
		docView.lastAppliedStamp(Input.stampColour);
		baseClass.stepInfo("Saved stamp is applied succesfully");
		baseClass.waitTime(3);
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterSavedStampAction=docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeSavedStampAction, docIdAfterSavedStampAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		softassertion.assertAll();
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description Verify when navigation to the document with hidden content is done after completing the document from coding form child window, 
	 *                    should display the warning message to indicate that document is having hidden content
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void verifyWarningMsgOfHiddenDocsInChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51958");
		baseClass.stepInfo("Verify when navigation to the document with hidden content is done after completing the document from coding form child window, should display the warning message to indicate that document is having hidden content");
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SoftAssert softassertion = new SoftAssert();
		docView = new DocViewPage(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();
		System.out.println(assname);
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.closeAdpopUp();
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.closeAdpopUp();
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Basic Search and select the pure hit count
		sessionsearch.basicContentSearch(Input.TextHidden);
		sessionsearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, Input.codingFormName);
		assignmentsPage.toggleCodingStampEnabled();
		assignmentsPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("Assignment created with required documents and distributed to reviewer successfully");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.selectproject(Input.additionalDataProject);
		// Assignment selected by reviewer
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeCompleteAction=docView.getDocView_CurrentDocId().getText();		
		docView.editCodingForm(Input.searchString1);
		baseClass.stepInfo("Coding form is edited successfully");
		docView.stampColourSelection(filedText, Input.stampColour);
		baseClass.CloseSuccessMsgpopup();
		docView.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Child window of coding form is opened");
		docView.switchToNewWindow(2);
		//Validation after complete button
		docView.completeButton();
		docView.switchToNewWindow(1);
		baseClass.stepInfo("After editing the coding form complete action is performed in child window");
		baseClass.handleAlert();
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCompleteAction=docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCompleteAction, docIdAfterCompleteAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		//Validation after hidden info icon
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().waitAndClick(5);
		baseClass.stepInfo("Hidden info action is performed");
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.passedStep("Warning message is displayed as expected");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeCodeSameAsLastAction=docView.getDocView_CurrentDocId().getText();
		docView.switchToNewWindow(2);
		//Validation after code same as last
		docView.clickCodeSameAsLast();
		docView.switchToNewWindow(1);
		baseClass.handleAlert();
		baseClass.stepInfo("Code same as last action is performed in child window");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCodeSameAsLastAction=docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCodeSameAsLastAction, docIdAfterCodeSameAsLastAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");		
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeSavedStampAction=docView.getDocView_CurrentDocId().getText();
		docView.switchToNewWindow(2);
		//validation after saved stamp
		docView.lastAppliedStamp(Input.stampColour);
		baseClass.handleAlert();
		baseClass.stepInfo("Saved stamp is applied succesfully in child window");
		docView.switchToNewWindow(1);
		baseClass.handleAlert();
		baseClass.VerifyWarningMessage("The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterSavedStampAction=docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeSavedStampAction, docIdAfterSavedStampAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		docView.closeWindow(1);
		softassertion.assertAll();
		loginPage.logout();
	}
	
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50783
	 * Description :To verify when user selects 'View All in Doc View' from Action drop down of Manage Assignments page when System Admin impersonates to RMU role
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 47)
	public void verifyDocViewActionWhenSAImpersonateToRMU() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		
		//Impersonate from SA to RMU and verify view all in doc view action
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50783");
		baseClass.stepInfo("To verify when user selects 'View All in Doc View' from Action drop down of Manage Assignments page when System Admin impersonates to RMU role");
		baseClass.impersonateSAtoRMU();
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Selected assignment and navigated to docview");
		int documentsAvailable =docView.getDocumetCountMiniDocList().size();
		if(documentsAvailable>0) {
			baseClass.passedStep("List of documents present in the minidoclist panel");
		}
		else {
			baseClass.failedStep("List of documents not present in the minidoclist panel");
		}
		
	}
	
	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50784
	 * Description :To verify when user selects 'View All in Doc View' from Action drop down of Manage Assignments page when Project Admin impersonated to RMU role
	 * @throws InterruptedException 
	 */
	@Test(enabled = true, groups = {"regression" },priority = 48)
	public void verifyDocViewActionWhenPAImpersonateToRMU() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		
		//Impersonate from PA to RMU and verify view all in doc view action
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50784");
		baseClass.stepInfo("To verify when user selects 'View All in Doc View' from Action drop down of Manage Assignments page when Project Admin impersonated to RMU role");
		baseClass.impersonateSAtoRMU();
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Selected assignment and navigated to docview");
		int documentsAvailable =docView.getDocumetCountMiniDocList().size();
		if(documentsAvailable>0) {
			baseClass.passedStep("List of documents present in the minidoclist panel");
		}
		else {
			baseClass.failedStep("List of documents not present in the minidoclist panel");
		}
		
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
		System.out.println("******TEST CASES FOR DOCVIEW/REDACTIONS EXECUTED******");

	}

}
