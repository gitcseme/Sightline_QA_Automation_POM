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
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import automationLibrary.CustomWebDriverWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
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


	/**
	 * Author : Krishna D date: NA Modified date:NA Modified by: Test Case Id: 51349
	 * Verifying persistent hit for audio docs as RMU when navigated from child
	 * window As PA DocView- sprint 3
	 */
	@Test(description ="RPMXCON-51349",enabled = true, alwaysRun = true, groups = { "regression" })
	public void persistentHitCheckAudioDocsChildWindowAsPA() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
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
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51009 Verify thumbnails icon click enables the thumbnails panel
	 * with impersonation PA to RMU
	 */

	@Test(description ="RPMXCON-51009",enabled = true, groups = { "regression" })
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
		return new Object[][] { 
			{ Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } 
				};
	}

	/**
	 * Author : Jagadeesh.Jana date: NA Modified date: NA Modified by: NA Test Case
	 * Id: RPMXCON-51562 Verify when user enters some text (e.g. Hello) in the text
	 * box and hits Enter, then the app looks for the corresponding text and
	 * highlights them in the document
	 */
	@Test(description ="RPMXCON-51562",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
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
	@Test(description ="RPMXCON-51563",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyTraverseForwardAndBackwardOnHits()
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51563");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
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
	@Test(description ="RPMXCON-51564",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyThereAreNoHits(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51564");
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
	 * RPMXCON-51425 Verify Conceptually similar tab remains selected when
	 * navigation from child window
	 */

	@Test(description ="RPMXCON-51425",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
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
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51909 Verify Images tab when moved to last Doc PA to RMU
	 */

	@Test(description ="RPMXCON-51909",enabled = true, groups = { "regression" })
	public void verifyImagesIconWhenMovedToLastDoc() throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case id : RPMXCON-51909");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and navigates to last document then should be on Images tab for last document");
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
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the last document");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51910 Verify that when user in on Images tab and navigates to
	 * first document then should be on Images tab for first document
	 * 
	 */

	@Test(description ="RPMXCON-51910",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyImagesIconWhenMovedToFirstDoc(String fullName, String userName, String password)
			throws Exception {
		Actions actions = new Actions(driver.getWebDriver());
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		baseClass.stepInfo("Test case id : RPMXCON-51910");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and navigates to first document then should be on Images tab for first document");
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
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the last document");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51911 Verify that when user in on Images tab when navigated to doc
	 * by entering DocNumber
	 * 
	 */

	@Test(description ="RPMXCON-51911",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyImagesIconWhenMovedtoDocByEnteringNumber(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		baseClass.stepInfo("Test case id : RPMXCON-51911");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and navigates to document after entering the document number then should be on Images tab for navigated document");
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
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the document using doucument number");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();
	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51908 Verify that when user in on Images tab when navigated to doc
	 * by navigating previous Doc
	 * 
	 */

	@Test(description ="RPMXCON-51908",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyImagesIconWhenNavigatingPriviousDoc(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);
		baseClass.stepInfo("Test case id : RPMXCON-51908");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and navigates to previous document then should be on Images tab for previous document");
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
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained after moving to the previous document ");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();
	}

	/**
	 * Author :Jayanthi date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51566
	 * 
	 */
	@Test(description ="RPMXCON-51566",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyDistinguishedHighlightedTextInDocView(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51566");
		baseClass.stepInfo(
				"Verify user can distinguish this on-demand search highlights with the highlights from the keyword"
						+ " group and persistent search hit highlights");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch("enron");
		baseClass.stepInfo("Search with text 'test' completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Purehits viewed in DocView");
		baseClass.waitTime(2);
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
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51918
	 * 
	 */
	@Test(description ="RPMXCON-51918",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyImagesTabRetainedWhileSaving(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51918");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and save the document then should be on Images tab for the same document");
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
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained when document selected from Mini Doc List");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51919
	 * 
	 */
	@Test(description ="RPMXCON-51919",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyImagesTabRetainedFromMiniDocList(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51919");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and clicks the document to load from mini doc list then should be on Images tab for the same document");
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
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained when document selected from Mini Doc List");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51407
	 * 
	 */
	@Test(description ="RPMXCON-51407",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyKeywordHighliteIsdisplayedOnHitofPersistentHitIcon(String fullName, String userName,
			String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51407");
		baseClass.stepInfo(
				"Verify on click of the \"eye\" icon, ALL highlighted terms - including those that are set from Manage > Keywords configured to the security group");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("login as" + fullName);
		sessionsearch.basicContentSearch(Input.randomText);
		baseClass.stepInfo("Search for text input completed");
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		docViewRedact.checkingPersistentHitPanel();
		if (docViewRedact.getKeywordInPersistentHitPanel_test().isDisplayed()) {
			baseClass.passedStep("Keyword assigned to security group is present in the persistent hit panel");
		} else {
			baseClass.failedStep("Keyword assigned to security group is not present in the panel");
		}
		loginPage.logout();
	}


	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51114
	 * 
	 * @throws InterruptedException
	 * 
	 */

	@Test(description ="RPMXCON-51114",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDownloadDropdownDocViewAsSA() throws InterruptedException {
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
		if (getAttribute1.contains("GetFilePathAndDownload")) {
			baseClass.passedStep("The download dropdown is clicked and doc is downloaded");
		} else {
			baseClass.failedStep("The dropdown for downloading is not present");
		}
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docViewRedact.downloadIcon());
		docViewRedact.downloadIcon().waitAndClick(10);
		docViewRedact.dropDownDownloadIcon().waitAndClick(5);
		String getAttribute2 = docViewRedact.dropDownDownloadIcon().GetAttribute("onclick");
		System.out.println(getAttribute2);
		if (getAttribute1.contains("GetFilePathAndDownload")) {
			baseClass.passedStep("The download dropdown is clicked and doc is downloaded");
		} else {
			baseClass.failedStep("The dropdown for downloading is not present");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50915
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-50915",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyNavigationToDocViewAsSAimpersonation() throws InterruptedException, AWTException {
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
		if (status.equalsIgnoreCase("false")) {
			baseClass.passedStep("The text tab is Not retained");
		} else {
			baseClass.failedStep("The images tab is retained");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51013
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51013",enabled = true, alwaysRun = true, groups = { "regression" })
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
		if (docViewRedact.getKeywordInPersistentHitPanel().isDisplayed()) {
			baseClass.passedStep("Keyword assigned to security group is present in the persistent hit panel");
		} else {
			baseClass.failedStep("Keyword assigned to security group is not present in the panel");
		}

		assignmentsPage.deleteAssgnmntUsingPagination(assname);
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51113
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51113",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyImagesTabDropDownAsSA() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51113");
		baseClass.stepInfo(
				"Verify user after impersonation can select any of the menu option from Images tab- Zoom in, Zoom out, rotation, Slider, Fit to Width");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingImagesTab();
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesTabZoomOut());
		docViewRedact.imagesTabZoomOut().Click();
		if (docViewRedact.imagesTabZoomOut().isDisplayed()) {
			baseClass.passedStep("images Tab zomm out is Visible");
		} else {
			baseClass.failedStep("images Tab zoom out is not visible");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesTabZoomIn());
		docViewRedact.imagesTabZoomIn().Click();
		if (docViewRedact.imagesTabZoomIn().isDisplayed()) {
			baseClass.passedStep("images Tab zoom in is Visible");
		} else {
			baseClass.failedStep("images Tab zoom in is not visible");
		}
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesTabZoomFitToScreen());
		docViewRedact.imagesTabZoomFitToScreen().Click();
		if (docViewRedact.imagesTabZoomIn().isDisplayed()) {
			baseClass.passedStep("images Tab fit to screen option is avilablee");
		} else {
			baseClass.failedStep("images Tab fit to screen option is not avilable");
		}
		loginPage.logout();
	}

	

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51432
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51432",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
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
		if (docViewRedact.getInputSearchBox().Visible()) {
			baseClass.failedStep("The search box is present in the default view of DocView");
		} else {
			baseClass.passedStep("The search box is not present in the default view of docview");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50849
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-50849",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyAllObjectsDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-50849");
		baseClass.stepInfo(
				"To verify that when user navigates to doc view from the Basic search view, then all the objects on doc view are displayed.");
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
		if (docViewRedact.HighliteIcon().isDisplayed()) {
			baseClass.passedStep("Highlite icon visible in DocView");
		} else {
			baseClass.failedStep("Highlite Icon not visible in DocView");
		}

		if (docViewRedact.redactionIcon().isDisplayed()) {
			baseClass.passedStep("Redaction icon visible in DocView");
		} else {
			baseClass.failedStep("Redaction Icon not visible in DocView");
		}

		if (docViewRedact.getSearchIcon().isDisplayed()) {
			baseClass.passedStep("Search icon visible in DocView");
		} else {
			baseClass.failedStep("Search Icon not visible in DocView");
		}

		if (docViewRedact.forwardNextDocBtn().isDisplayed()) {
			baseClass.passedStep("doc navigation forward icon visible in DocView");
		} else {
			baseClass.failedStep("doc navigation forwaard not visible in DocView");
		}

		if (docViewRedact.backwardPriviousDocBtn().isDisplayed()) {
			baseClass.passedStep("Doc Navigation backward visible in DocView");
		} else {
			baseClass.failedStep("Doc Navigation Backward not visible in DocView");
		}

		if (docViewRedact.docViewNextPage().isDisplayed()) {
			baseClass.passedStep("page Navigation forward visible in DocView");
		} else {
			baseClass.failedStep("Doc Navigation Backward not visible in DocView");
		}

		if (docViewRedact.docViewPreviousPage().isDisplayed()) {
			baseClass.passedStep("page Navigation backward visible in DocView");
		} else {
			baseClass.failedStep("Doc Navigation Backward not visible in DocView");
		}

		if (docViewRedact.pageNumberTextBox().isDisplayed()) {
			baseClass.passedStep("page Number box visible in DocView");
		} else {
			baseClass.failedStep("page number box not visible in DocView");
		}
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51254
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description = "RPMXCON-51254", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyTraverseForwardOnHitsAfterImpersonation() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "AAassignment2" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Test case Id: RPMXCON-51254");
		baseClass.stepInfo(
				"Verify user after impersonation can go to next/prvious highlight in doc view when there are multiple hits for each term in context of an assignment");
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
		if (getAttribute.equalsIgnoreCase("1")) {
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
		if (getAttribute2.equalsIgnoreCase("1")) {
			baseClass.passedStep("Sucsessfully moved forward on hits");
		} else {
			baseClass.failedStep("moving forward on hits unsuccessful");
		}
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51079
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51079",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
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
		baseClass.CloseSuccessMsgpopup();
		Thread.sleep(3000);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), -10, -10);
		actions.click();
		actions.build().perform();
		if (docViewRedact.deleteRedactioBtn().isElementAvailable(5) == true) {
			docViewRedact.deleteRedactioBtn().waitAndClick(5);
			baseClass.VerifySuccessMessage("Redaction Removed successfully.");
			baseClass.passedStep("The applied annotation has been successfully removed");
		} else {
			baseClass.failedStep("The applied annotation was not removed");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51057
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51057",enabled = true, alwaysRun = true, groups = { "regression" })
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
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51912
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51912",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyImagesTabRetainedFromHistoryDropdown(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51912");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and completes the document then should be on Images tab for next navigated document");
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
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("The images tab is retained when document selected from history DropDown");
		} else {
			baseClass.failedStep("The images tab is NOT retained");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51348
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51348",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyRemarksPanelRetainedOnDocNavigation(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51348");
		baseClass.stepInfo(
				"Verify when Reviewer Remarks panel is selected from doc view and navigates to another document from mini doc list child window the selected panels/menus previously selected should remain.");
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
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51927 Description :Verify that when performing doc-to-doc
	 * navigation the entry for the same document in mini-DocList must always
	 * present fully in the visible area of the mini-DocList (to the user).
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(description ="RPMXCON-51927",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyDocumentPresentAfterDocToDocNavigation(String fullName, String userName, String password)
			throws InterruptedException {
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
		// Verifying the Docids
		if (selectedId.equalsIgnoreCase(activeDocumentId)) {
			baseClass.passedStep("Presently Viewed Document is present in visible area of the mini-doclist");
		} else {
			baseClass.failedStep("Presently Viewed Document is not present in visible area of the mini-doclist");
		}
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51353 Description :Verify after impersonation when Persistent Hit
	 * panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected
	 * 
	 * @throws InterruptedException
	 * 
	 */
	@Test(description ="RPMXCON-51353",enabled = true, groups = { "regression" })
	public void verifyMenuStatusAfterMovingToNextDoc() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51353");
		baseClass.stepInfo(
				"Verify when Persistent Hit panel, Reviewer Remarks panel, Redactios menu, Highlights menu is selected after moving to next document");

		// Login to the application
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Impersonate from SA to RMU and verifying status
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Impersonated from SA to RMU");
		docView.statusCheckAfterImpersonation();

		// Login back to the application as SA
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Impersonate from SA to Reviewer and verifying status
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Impersonated from SA to Reviewer");
		docView.statusCheckAfterImpersonation();

		// Login back to the application as PA
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Impersonate from PA to RMU and verifying status
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonated from PA to RMU");
		docView.statusCheckAfterImpersonation();

		// Login back to the application as PA
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Impersonate from PA to Reviewer and verifying status
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Impersonated from PA to Reviewer");
		docView.statusCheckAfterImpersonation();

		// Login back to the application as RMU
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Impersonate from RMU to Reviewer and verifying status
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated from RMU to Reviewer");
		docView.statusCheckAfterImpersonation();
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51347
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51347",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyPersistentPanelRetainedOnDocNavigation(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-51347");
		baseClass.stepInfo(
				"Verify Persistent Hit panel is selected from doc view and navigates to another document from mini doc list child window the selected panels/menus previously selected should remain.");
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
			baseClass.passedStep(
					"The persistent hits panel is visible for non audio documents After navigating from child window is retained");
		} else {
			baseClass.failedMessage("The persistent hits panel is NOT visible for non audio documents After navigating from child window is retained");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51351
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-51351",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyRemarksPanelRetained(String fullName, String userName, String password) throws Exception {
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
			baseClass.passedStep("The remarks panel is visible for audio documents After navigating from child window");
		} else {
			assertTrue(false);
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51859 Description :Doc View: When users locale is German then on
	 * navigating to Doc View should not display warning message.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47736",enabled = true, groups = { "regression" })
	public void verifyWarningMessageDisplay() throws InterruptedException {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case id :RPMXCON-51859");
		baseClass.stepInfo(
				"When users locale is German then on navigating to Doc View should not display warning message");

		// Login to the application
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// Changing local to german as pre-requisite
		loginPage.editProfile("German - Germany");
		baseClass.stepInfo("Successfully selected German Language");

		// Performing basic search
		sessionSearch.basicContentSearch(Input.testData1);

		// Adding search results and view in docview
		sessionSearch.ViewInDocViewGerman();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to DocView");

		// verifying whether warning message displayed
		if (baseClass.getSuccessMsgHeader().isDisplayed()) {
			String warningMessage = baseClass.getSuccessMsgHeader().getText().toString();
			baseClass.stepInfo(warningMessage);
			baseClass.failedStep("Warning Message Displayed");
		} else {
			baseClass.passedStep("Warning Message Not Displayed");
		}

		// setting back to default locale
		loginPage.editProfile("English - United States");
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51115
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description ="RPMXCON-51115",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyPrintIconAfterDisablingToggle() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51115");
		baseClass.stepInfo(
				"Verify user after impersonation can go to next/prvious highlight in doc view when there are multiple hits for each term in context of an assignment");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		docViewRedact.DisablePrintToggleinAssignment();
		assignmentspage.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		assignmentspage.addReviewerAndDistributeDocs();
		baseClass.waitForElement(assignmentspage.getAssignmentSaveButton());
		assignmentspage.getAssignmentSaveButton().Click();
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.stepInfo("Assignment '" + assignmentName + "' is successfully viewed on DocView");
		String printIconStatus =docViewRedact.get_PrintIcon().GetAttribute("style");
		if (printIconStatus.contains("none")) {
			baseClass.passedStep("The print Icon is not present in DocView as expected");
		} else {
			baseClass.failedStep("The print Icon is present in DocView - failed");
		}
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentspage.SelectAssignmentByReviewer(assignmentName);
		if (printIconStatus.contains("none")) {
			baseClass.passedStep("The print Icon is not present in DocView as expected");
		} else {
			baseClass.failedStep("The print Icon is present in DocView - failed");
		}
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-48810
	 * 
	 */
	@Test(description ="RPMXCON-48811",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
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
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50774
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description ="RPMXCON-50774",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyAssignmentDocs() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50774");
		baseClass.stepInfo(
				"To verify that 'View All Doc in Doc View' option is disabled if assignment is not added in the list");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.bulkAssign();
		assignmentspage.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		String verifydocsCountInAssgnPage = assignmentspage.verifydocsCountInAssgnPage(assignmentName);

		if (verifydocsCountInAssgnPage.equalsIgnoreCase("201")) {
			baseClass.passedStep("The doc count has been verified");
		} else {
			baseClass.failedStep("The doc count is not verified");
		}
		assignmentspage.selectAssignmentToViewinDocview(assignmentName);
		baseClass.passedStep("Assignment viewed in DocView Successfully");
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50776
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description ="RPMXCON-50776",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyAssignmentPageViewInDocView() throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50776");
		baseClass.stepInfo(
				"To verify that 'View All Doc in Doc View' option is disabled if assignment is not added in the list");
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
		if (getAttribute == null) {
			assignmentspage.getAssignmentAction_ViewinDocView().waitAndClick(3);
			baseClass.VerifyWarningMessage("Please select at least one assignment from the displayed assignment list");

		} else {
			baseClass.passedStep("No assignments pre esist for the SG. The view in doc view Icon is disabled");
		}
		loginPage.logout();
	}

	/**
	 * Author : Iyappan.Kasinathan Description : Verify on click of the reviewer
	 * remark respecive page should be scrolled in doc view when redirecting from
	 * basic search/saved search/doc list
	 */
	@Test(description ="RPMXCON-51083",enabled = true, dataProvider = "UsersWithoutPA", alwaysRun = true, groups = { "regression" })
	public void VerifyReviewerRemarksPageFromSearchPg(String userName, String password, String fullName)
			throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51083");
		baseClass.stepInfo(
				" Verify on click of the reviewer remark respecive page should be scrolled in doc view when redirecting from basic search/saved search/doc list");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		String remarksName = "remarks" + Utility.dynamicNameAppender();
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
		// Thread sleep added for the page to adjust resolution
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
		String id = docView.getRemarksId(remarksName).GetAttribute("id");
		if (docView.getRemarksInPg(id).isElementAvailable(5)) {
			baseClass.passedStep("The page is loaded sucessfully where the remarks is added");
		} else {
			baseClass.failedStep("The page is not loaded where the remarks is added");
		}
		docViewRedact.clickingRemarksIcon();
		docView.deleteReamark(remarksName);
		loginPage.logout();
	}

	/**
	 * Author : Iyappan.Kasinathan Description : Verify on click of the reviewer
	 * remark respective page should be scrolled in doc view when redirecting from
	 * my assignment
	 */
	@Test(description ="RPMXCON-51082",enabled = true, dataProvider = "UsersWithoutPA", alwaysRun = true, groups = { "regression" })
	public void VerifyReviewerRemarksPageFromAssignments(String userName, String password, String fullName)
			throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51082");
		baseClass.stepInfo(
				"Verify on click of the reviewer remark respective page should be scrolled in doc view when redirecting from my assignment");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String remarksName = "remarks" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(userName, password);
		System.out.println(assignmentName);
		// create assignment
		if (fullName.contains("RMU")) {
			sessionsearch.basicContentSearch("null");
			sessionsearch.bulkAssign();
			assignmentPage.assignmentCreation(assignmentName, Input.codeFormName);
			assignmentPage.add2ReviewerAndDistribute();
			// Impersonate to reviewer
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
			assignmentPage.assgnViewInAllDocView();
			baseClass.stepInfo("Navigated to docview from assignment page successfully");
			driver.waitForPageToBeReady();
		} else {
			assignmentPage.SelectAssignmentByReviewer(assignmentName);
			baseClass.stepInfo("User on the doc view after selecting the assignment");
		}
		baseClass.waitForElement(docViewRedact.getDocView_PageNumber());
		docViewRedact.getDocView_PageNumber().SendKeys("4");
		docViewRedact.getSearchIcon().waitAndClick(5);
		docViewRedact.clickingRemarksIcon();
		baseClass.waitTillElemetToBeClickable(docViewRedact.getDocView_Redactrec_textarea());
		baseClass.waitTime(4);
		// Thread sleep added for the page to adjust resolution
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.waitTillElemetToBeClickable(docViewRedact.getDocView_Redactrec_textarea());
		docViewRedact.getDocView_Redactrec_textarea().waitAndClick(5);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), -10, 10).clickAndHold()
				.moveByOffset(200, 90).release().build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.addRemarksBtn());
		docViewRedact.addRemarksBtn().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(docViewRedact.addRemarksTextArea());
		docViewRedact.addRemarksTextArea().waitAndClick(10);
		docViewRedact.addRemarksTextArea().SendKeys(remarksName);
		baseClass.waitTillElemetToBeClickable(docViewRedact.saveRemarksBtn());
		docViewRedact.saveRemarksBtn().waitAndClick(10);
		baseClass.stepInfo("Remarks added successfully");
		baseClass.waitForElement(docView.getDocView_SelectRemarks(remarksName));
		docView.getDocView_SelectRemarks(remarksName).waitAndClick(10);
		String id = docView.getRemarksId(remarksName).GetAttribute("id");
		if (docView.getRemarksInPg(id).isElementAvailable(5)) {
			baseClass.passedStep("The page is loaded sucessfully where the remarks is added");
		} else {
			baseClass.failedStep("The page is not loaded where the remarks is added");
		}
		docViewRedact.clickingRemarksIcon();
		docView.deleteReamark(remarksName);
		if (fullName.contains("REV")) {
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		}
		loginPage.logout();
	}

	@DataProvider(name = "UsersWithoutPA")
	public Object[][] UsersWithoutPA() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51046 Description :Verify user can not see the keywords
	 * highlighted outside of an assignment when keyword groups assigned to the
	 * assignment only
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description ="RPMXCON-51046",enabled = true, groups = { "regression" })
	public void verifyKeywordsHighlightingWhenMappedToAssignment() throws InterruptedException, AWTException {

		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		keywordPage = new KeywordPage(driver);

		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();

		// Pre-requisites
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
		List<String> values = baseClass.availableListofElements(assignPage.getAssgn_AllKeywords());
		baseClass.stepInfo("Mapped keywords for the assignment are " + values);
		assignPage.getAssgn_Keywordokbutton().ScrollTo();
		assignPage.getAssgn_Keywordokbutton().isElementAvailable(10);
		assignPage.getAssgn_Keywordokbutton().Click();
		keywordPage.getYesButton().Click();
		driver.scrollPageToTop();;
		driver.waitForPageToBeReady();
		assignPage.addReviewerAndDistributeDocs();
		baseClass.stepInfo("Added reviewer and distributed docs");
		loginPage.logout();

		// Login as RMU and verify keyword Highlighting
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logined as RMU");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		// click eye icon and verify the highlighting of search term
		docView.getPersistentHit(Input.testData1);
		docView.verifyHighlightedKeywordInDocView();
		loginPage.logout();

		// login as reviewer and verify keyword highlighting
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logined as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("Select assigned assignment and navigated to docview");
		driver.waitForPageToBeReady();
		// click eye icon and verify the highlighting of search term
		docView.getPersistentHit(Input.testData1);
		docView.verifyHighlightedKeywordInDocView();
		loginPage.logout();
	}

	

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51557
	 * 
	 */
	@Test(description ="RPMXCON-51557",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifySearchIconGreyedForTiff(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51557");
		baseClass.stepInfo("Verify that by default, the document is simply shows the search icon [magnifying]");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("SourceDocID", null, Input.TiffDocId, null);
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
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51267
	 * 
	 */
	@Test(description ="RPMXCON-51267",enabled = true, alwaysRun = true, groups = { "regression" })
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

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50787
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-50787",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
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
				return docViewRedact.thumbNailsIcon().Visible() && docViewRedact.thumbNailsIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docViewRedact.thumbNailsIcon());
		docViewRedact.HighliteIcon().waitAndFind(30);
		if (docViewRedact.HighliteIcon().isDisplayed()) {
			baseClass.passedStep("Thumbnails icon visible in DocView");
		} else {
			baseClass.failedStep("Thumbnails Icon not visible in DocView");
		}
		if (docViewRedact.zoomInDocView().isDisplayed()) {
			baseClass.passedStep("Zoom in icon visible in DocView");
		} else {
			baseClass.failedStep("Zoom in Icon not visible in DocView");
		}
		if (docViewRedact.zoomOutDocView().isDisplayed()) {
			baseClass.passedStep("Zoom out visible in DocView");
		} else {
			baseClass.failedStep("Zoom out Icon not visible in DocView");
		}
		if (docViewRedact.zoomFitToScreenDocView().isDisplayed()) {
			baseClass.passedStep("Fit to screen icon visible in DocView");
		} else {
			baseClass.failedStep("Fit to screen forwaard not visible in DocView");
		}
		if (docViewRedact.rotateClockWise().isDisplayed()) {
			baseClass.passedStep("Rotate Clock wise visible in DocView");
		} else {
			baseClass.failedStep("Rotate clock wise not visible in DocView");
		}
		if (docViewRedact.rotateAntiClockWise().isDisplayed()) {
			baseClass.passedStep("Rotate AntiClock visible in DocView");
		} else {
			baseClass.failedStep("Rotate AntiClock not visible in DocView");
		}
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50787
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */

	@Test(description ="RPMXCON-50787",enabled = true, alwaysRun = true, groups = { "regression" })
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
		if (docViewRedact.HighliteIcon().isDisplayed()) {
			baseClass.passedStep("Thumbnails icon visible in DocView");
		} else {
			baseClass.failedStep("Thumbnails Icon not visible in DocView");
		}
		if (docViewRedact.zoomInDocView().isDisplayed()) {
			baseClass.passedStep("Zoom in icon visible in DocView");
		} else {
			baseClass.failedStep("Zoom in Icon not visible in DocView");
		}
		if (docViewRedact.zoomOutDocView().isDisplayed()) {
			baseClass.passedStep("Zoom out visible in DocView");
		} else {
			baseClass.failedStep("Zoom out Icon not visible in DocView");
		}
		if (docViewRedact.zoomFitToScreenDocView().isDisplayed()) {
			baseClass.passedStep("Fit to screen icon visible in DocView");
		} else {
			baseClass.failedStep("Fit to screen forwaard not visible in DocView");
		}
		if (docViewRedact.rotateClockWise().isDisplayed()) {
			baseClass.passedStep("Rotate Clock wise visible in DocView");
		} else {
			baseClass.failedStep("Rotate clock wise not visible in DocView");
		}
		if (docViewRedact.rotateAntiClockWise().isDisplayed()) {
			baseClass.passedStep("Rotate AntiClock visible in DocView");
		} else {
			baseClass.failedStep("Rotate AntiClock not visible in DocView");
		}

		if (docViewRedact.HighliteIcon().isDisplayed()) {
			baseClass.passedStep("Highlite icon visible in DocView");
		} else {
			baseClass.failedStep("Highlite Icon not visible in DocView");
		}

		if (docViewRedact.redactionIcon().isDisplayed()) {
			baseClass.passedStep("Redaction icon visible in DocView");
		} else {
			baseClass.failedStep("Redaction Icon not visible in DocView");
		}

		if (docViewRedact.getSearchIcon().isDisplayed()) {
			baseClass.passedStep("Search icon visible in DocView");
		} else {
			baseClass.failedStep("Search Icon not visible in DocView");
		}

		if (docViewRedact.remarksIcon().isDisplayed()) {
			baseClass.passedStep("Remarks iocn visible in DocView");
		} else {
			baseClass.failedStep("Remarks icon not visible in DocView");
		}

		if (docViewRedact.persistantHitBtn().isDisplayed()) {
			baseClass.passedStep("Persistent hit button visible in DocView");
		} else {
			baseClass.failedStep("persistent hit button not visible in DocView");
		}

		if (docViewRedact.downloadIcon().isDisplayed()) {
			baseClass.passedStep("Download iocn visible in DocView");
		} else {
			baseClass.failedStep("Download icon not visible in DocView");
		}

		if (docViewRedact.printIcon().isDisplayed()) {
			baseClass.passedStep("print iocn visible in DocView");
		} else {
			baseClass.failedStep("print icon not visible in DocView");
		}
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50785
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * 
	 */
	@Test(description ="RPMXCON-50785",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyAssignmentViewInDocViewZero() throws Exception {
		String assignmentName = "AAassignment" + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50785");
		baseClass.stepInfo(
				"To verify that on selecting 'View All Doc in Doc View' option when documents are not mapped to the assignment");
		docViewRedact = new DocViewRedactions(driver);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);
		assignmentspage.createAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignmentName + "' is created Successfully");
		String verifydocsCountInAssgnPage = assignmentspage.verifydocsCountInAssgnPage(assignmentName);
		if (verifydocsCountInAssgnPage.equalsIgnoreCase("0")) {
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
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51305 Description :Verify persistent Hit panel of DocView should
	 * present only content terms, not metadata terms when navigating from advance
	 * search
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51305",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyMetaDataTermOnPersistentPanel(String fullName, String userName, String password)
			throws InterruptedException {

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
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51324 Description :Verify all hits of the document should be
	 * highlighted without clicking the eye icon when user redirects to doc view
	 * from advance search
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51324",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyHighlightingWhenNavigatingFromAdvancedSearch(String fullName, String userName, String password)
			throws InterruptedException {

		baseClass = new BaseClass(driver);
		assignPage = new AssignmentsPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51324");
		baseClass.stepInfo("Verify keywords highlighting when navigating from advanced search");
		sessionsearch.advancedContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docView");
		docView.verifyTermHitsHighlightingInDocumentWithoutClickingEyeIcon(Input.searchString1);
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51012 Description :To verify that persistent search should be
	 * displayed on doc view if user navigates from Saved Search-Doc View.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51012",enabled = true, groups = { "regression" })
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
		baseClass.stepInfo(
				"verify that persistent search should be displayed on doc view if user navigates from Saved Search-Doc View");
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.saveSearch(searchName);
		savedsearch.savedSearchToDocView(searchName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to docView");
		docView.verifyTermHitsHighlightingInDocumentAfterClickingEyeIcon(Input.testData1);
		int availableTermwithCount = docView.getPersistantNames().size();
		if (availableTermwithCount > 0) {
			baseClass.passedStep("Hit terms with count displayed on the persistent panel");
		} else {
			baseClass.failedStep("Hit terms with count not displayed on the persistent panel");
		}
		loginPage.logout();
	}





	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51047 Description :Verify keyword highlighting color from doc view
	 * when mapped keywords are having same word with different color
	 * 
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51047",enabled = true, groups = { "regression" })
	public void verifyHighlightingWhenSameKeywordsHaveDifferentColor() throws AWTException, InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		savedsearch = new SavedSearch(driver);
		keywordPage = new KeywordPage(driver);

		String keywordGroupName1 = "firstGroup" + utility.dynamicNameAppender();
		String keywordGroupName2 = "secondGroup" + utility.dynamicNameAppender();
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		String[] Color = { Input.keywordColor1, Input.KeyWordColour };
		String[] keywordGroupName = { keywordGroupName1, keywordGroupName2 };

		// Pre-requisite
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51047");
		baseClass.stepInfo(
				"Verify keyword highlighting color from doc view when mapped keywords are having same word with different color");
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
		// Login as RMU and verify keyword color Highlighting
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
		// Login as Reviewer and verify keyword color Highlighting
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
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51461 Description :Verify that a DocView functionality is working
	 * properly through assignments. like - All Panels Verifications with child
	 * window / Complete
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-52658",enabled = true, groups = { "regression" })
	public void verifyDocviewFunctionalityThroughAssignments() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);

		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Pre-requisite
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
		// Login as reviewer and verify
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
		if (checkMarkIconsAfterEditingCF > checkMarkIconCount) {
			baseClass.passedStep("Check mark icon displayed for completed document");
		}
		loginPage.logout();
	}


	
	/**
	 * @Author : Krishna date: 31/01/2021 Modified date: NA Modified by:
	 * @Description:Verify assignment progress bar refresh after completing the
	 *                     document same as last prior documents should be completed
	 *                     by applying coding stamp after selecting code same as
	 *                     this action
	 * 
	 */
	@Test(description = "RPMXCON-51278", enabled = true, groups = { "regression" })
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
		String StampText = "Newcolor" + Utility.dynamicNameAppender();
		String colour = "RED";

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Searching documents based on search string and Navigate to DocView");
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
		docView.editCodingForm(filedText);
		driver.scrollPageToTop();
		docView.stampColourSelection(StampText, Input.stampColour);
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
	@Test(description = "RPMXCON-51277", enabled = true, groups = { "regression" })
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
		String StampText = "Newcolor" + Utility.dynamicNameAppender();

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
		docView.editCodingForm(filedText);
		driver.scrollPageToTop();
		docView.stampColourSelection(StampText, Input.stampColour);
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
		docView.editCodingForm(filedText);
		driver.scrollPageToTop();
		docView.stampColourSelection(StampText, Input.stampColour);
		docView.completeButton();
		baseClass.waitTime(1);
		docView.clickCodeSameAsLast();
		docViewRedact.verifyAssignmentBarInSelectedDocs(assname);
	}
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51961
	 * 
	 */
	@Test(description = "RPMXCON-51961",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyDocsWithHiddenProperties(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51961");
		baseClass.stepInfo(
				"Verify that document having any of the field value \"Hidden Properties\" \"ExcelProtectedSheets\" ExcelProtectedWorkbook should display alert message");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("DocID", null, Input.DocIdWithHiddenContent, null);
		sessionsearch.ViewInDocView();
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is presented in the Viewer.");
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51959
	 * 
	 */
	@Test(description = "RPMXCON-51959",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenInfoIcon(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51959");
		baseClass.stepInfo(
				"Verify that a Hidden Info icon should not be presented in the action icon section of Doc View if the document being viewed has not any Hidden Content");
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
		if (docViewRedact.hiddenInfoIcon().isDisplayed() == false) {
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
		if (docViewRedact.hiddenInfoIcon().isDisplayed() == true) {
			baseClass.passedStep("Hidden info Icon is visible for document with hidden content");
		} else {

			baseClass.passedStep("Hidden info icon is not visible for document with hidden content");
		}

	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify when navigation to the document with hidden content is
	 *               done after completing the document, should display the warning
	 *               message to indicate that document is having hidden content
	 * 
	 */
	@Test(description = "RPMXCON-51957",enabled = true, groups = { "regression" })
	public void verifyWarningMsgOfHiddenDocs() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51957");
		baseClass.stepInfo(
				"Verify when navigation to the document with hidden content is done after completing the document, should display the warning message to indicate that document is having hidden content");

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
		baseClass.selectproject(Input.additionalDataProject);
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
		String docIdBeforeCompleteAction = docView.getDocView_CurrentDocId().getText();
		docView.editCodingForm(Input.searchString1);
		baseClass.stepInfo("Coding form is edited successfully");
		docView.stampColourSelection(filedText, Input.stampColour);
		baseClass.CloseSuccessMsgpopup();
		// validation after complete action
		docView.completeButton();
		baseClass.stepInfo("After editing the coding form complete action is performed");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCompleteAction = docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCompleteAction, docIdAfterCompleteAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		// Validation after hidden info icon
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().waitAndClick(5);
		baseClass.stepInfo("Hidden info action is performed");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.passedStep("Warning message is displayed as expected");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeCodeSameAsLastAction = docView.getDocView_CurrentDocId().getText();
		// Validation after code same as last action
		baseClass.CloseSuccessMsgpopup();
		docView.clickCodeSameAsLast();
		baseClass.stepInfo("Code same as last action is performed");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docView.getDocView_CurrentDocId().ScrollTo();
		String docIdAfterCodeSameAsLastAction = docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCodeSameAsLastAction, docIdAfterCodeSameAsLastAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeSavedStampAction = docView.getDocView_CurrentDocId().getText();
		// Validation after saved stamp action
		docView.lastAppliedStamp(Input.stampColour);
		baseClass.stepInfo("Saved stamp is applied succesfully");
		baseClass.waitTime(3);
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterSavedStampAction = docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeSavedStampAction, docIdAfterSavedStampAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		softassertion.assertAll();
		loginPage.logout();
	}

	
	
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description Verify when navigation to the document with hidden content is
	 *              done after completing the document from coding form child
	 *              window, should display the warning message to indicate that
	 *              document is having hidden content
	 * 
	 */
	@Test(description ="RPMXCON-51958",enabled = true, groups = { "regression" })
	public void verifyWarningMsgOfHiddenDocsInChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51958");
		baseClass.stepInfo(
				"Verify when navigation to the document with hidden content is done after completing the document from coding form child window, should display the warning message to indicate that document is having hidden content");
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
		baseClass.selectproject(Input.additionalDataProject);
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
		String docIdBeforeCompleteAction = docView.getDocView_CurrentDocId().getText();
		docView.editCodingForm(Input.searchString1);
		baseClass.stepInfo("Coding form is edited successfully");
		docView.stampColourSelection(filedText, Input.stampColour);
		baseClass.CloseSuccessMsgpopup();
		docView.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Child window of coding form is opened");
		docView.switchToNewWindow(2);
		// Validation after complete button
		docView.completeButton();
		docView.switchToNewWindow(1);
		baseClass.stepInfo("After editing the coding form complete action is performed in child window");
		baseClass.handleAlert();
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCompleteAction = docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCompleteAction, docIdAfterCompleteAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		// Validation after hidden info icon
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().waitAndClick(5);
		baseClass.stepInfo("Hidden info action is performed");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.passedStep("Warning message is displayed as expected");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdBeforeCodeSameAsLastAction = docView.getDocView_CurrentDocId().getText();
		docView.switchToNewWindow(2);
		// Validation after code same as last
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getCodeSameAsLast());
		docView.getCodeSameAsLast().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(docView.getCodingFormSaveButton());
		docView.getCodingFormSaveButton().ScrollTo();
		docView.getCodingFormSaveButton().waitAndClick(10);	
		docView.switchToNewWindow(1);
		baseClass.handleAlert();
		baseClass.stepInfo("Code same as last action is performed in child window");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterCodeSameAsLastAction = docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeCodeSameAsLastAction, docIdAfterCodeSameAsLastAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		docView.getDocView_CurrentDocId().ScrollTo();
		String docIdBeforeSavedStampAction = docView.getDocView_CurrentDocId().getText();		
		docView.switchToNewWindow(2);
		// validation after saved stamp
		docView.lastAppliedStamp(Input.stampColour);
		baseClass.waitTillElemetToBeClickable(docView.getCodingFormSaveButton());
		docView.getCodingFormSaveButton().ScrollTo();
		docView.getCodingFormSaveButton().waitAndClick(10);		
		baseClass.stepInfo("Saved stamp is applied succesfully in child window");
		docView.closeWindow(1);
		docView.switchToNewWindow(1);
		baseClass.handleAlert();
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		baseClass.waitForElement(docView.getDocView_CurrentDocId());
		String docIdAfterSavedStampAction = docView.getDocView_CurrentDocId().getText();
		softassertion.assertNotEquals(docIdBeforeSavedStampAction, docIdAfterSavedStampAction);
		baseClass.passedStep("Cursor moved to nextdoc and warning message displayed as expected");
		softassertion.assertAll();
		docView.switchToNewWindow(2);
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50783 Description :To verify when user selects 'View All in Doc
	 * View' from Action drop down of Manage Assignments page when System Admin
	 * impersonates to RMU role
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50783",enabled = true, groups = { "regression" })
	public void verifyDocViewActionWhenSAImpersonateToRMU() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();

		// Impersonate from SA to RMU and verify view all in doc view action
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50783");
		baseClass.stepInfo(
				"To verify when user selects 'View All in Doc View' from Action drop down of Manage Assignments page when System Admin impersonates to RMU role");
		baseClass.impersonateSAtoRMU();
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Selected assignment and navigated to docview");
		int documentsAvailable = docView.getDocumetCountMiniDocList().size();
		if (documentsAvailable > 0) {
			baseClass.passedStep("List of documents present in the minidoclist panel");
		} else {
			baseClass.failedStep("List of documents not present in the minidoclist panel");
		}

	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50784 Description :To verify when user selects 'View All in Doc
	 * View' from Action drop down of Manage Assignments page when Project Admin
	 * impersonated to RMU role
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50784",enabled = true, groups = { "regression" })
	public void verifyDocViewActionWhenPAImpersonateToRMU() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);

		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();

		// Impersonate from PA to RMU and verify view all in doc view action
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50784");
		baseClass.stepInfo(
				"To verify when user selects 'View All in Doc View' from Action drop down of Manage Assignments page when Project Admin impersonated to RMU role");
		baseClass.impersonatePAtoRMU();
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Selected assignment and navigated to docview");
		int documentsAvailable = docView.getDocumetCountMiniDocList().size();
		if (documentsAvailable > 0) {
			baseClass.passedStep("List of documents present in the minidoclist panel");
		} else {
			baseClass.failedStep("List of documents not present in the minidoclist panel");
		}
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: To verify Doc View page for RMU and Reviewer
	 */
	@Test(description ="RPMXCON-50775",enabled = true, dataProvider = "UsersWithoutPA", groups = { "regression" })
	public void verifyDocViewPanels(String userName, String password, String fullName)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50775");
		baseClass.stepInfo("To verify Doc View page for RMU and Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		docView = new DocViewPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		if (fullName.contains("RMU")) {
			baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
			// Basic Search and select the pure hit count
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.bulkAssign();
			// create assignment and distribute the docs to reviewer
			assignmentsPage.assignmentCreation(assignmentName, Input.codingFormName);
			assignmentsPage.add2ReviewerAndDistribute();
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			assignmentsPage.viewSelectedAssgnUsingPagination(assignmentName);
			baseClass.waitTillElemetToBeClickable(assignmentsPage.getAssignmentActionDropdown());
			assignmentsPage.getAssignmentActionDropdown().waitAndClick(10);
			assignmentsPage.assignmentActions("DocView");
			baseClass.stepInfo("Navigated to docview page in the context of assignments");
		} else {
			baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
			baseClass.stepInfo("Logined as Reviewer");
			assignmentsPage.SelectAssignmentByReviewer(assignmentName);
		}
		driver.waitForPageToBeReady();
		// validations on displaying panels in docview page
		baseClass.waitForElement(docView.getDocView_CodingFormPanel());
		if (docView.getDocView_CodingFormPanel().isDisplayed()) {
			baseClass.passedStep("Codingform panel is displayed in docview page successfully");
		} else {
			baseClass.failedStep("Codingform panel is not displayed");
		}
		if (docView.getDocView_centralPanel().isDisplayed()) {
			baseClass.passedStep("Document view panel is displayed in docview page successfully");
		} else {
			baseClass.failedStep("Document view panel is not displayed");
		}
		if (docView.getDocView_MiniDoclistPanel().isDisplayed()) {
			baseClass.passedStep("Minidoclist panel is displayed in docview page successfully");
		} else {
			baseClass.failedStep("Minidoclist panel is not displayed");
		}
		if (docView.getDocView_MetadataPanel().isDisplayed()) {
			baseClass.passedStep("Metadata panel is displayed in docview page successfully");
		} else {
			baseClass.failedStep("Metadata panel is not displayed");
		}
		baseClass.waitForElement(docView.getDocView_AnalyticsPanel());
		if (docView.getDocView_AnalyticsPanel().isDisplayed()) {
			baseClass.passedStep("Analytics panel is displayed in docview page successfully");
		} else {
			baseClass.failedStep("Analytics panel is not displayed");
		}
		if (fullName.contains("REV")) {
			loginPage.logout();
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignmentsPage.deleteAssgnmntUsingPagination(assignmentName);
			loginPage.logout();
		}

	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify that document having any of the field value "Hidden
	 *               Properties" "ExcelProtectedSheets" ExcelProtectedWorkbook
	 *               viewed from analytics panel should display alert message
	 */
	@Test(description ="RPMXCON-51962",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyWarningMsgOfHiddenDocFromAnalyticsPanel(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51962");
		baseClass.stepInfo(
				"Verify that document having any of the field value \"Hidden Properties\" \"ExcelProtectedSheets\" ExcelProtectedWorkbook viewed from analytics panel should display alert message");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		if (fullName.contains("RMU")) {
			baseClass.stepInfo("Successfully login as Review Manager'" + userName + "'");
		}
		if (fullName.contains("PA")) {
			baseClass.stepInfo("Successfully login as Project admin'" + userName + "'");
		}
		if (fullName.contains("REV")) {
			baseClass.stepInfo("Successfully login as Reviewer'" + userName + "'");
		}
		sessionsearch.basicContentSearch(Input.hiddenDocId);
		sessionsearch.ViewInDocView();
		baseClass.CloseSuccessMsgpopup();
		// validations
		baseClass.passedStep("Warning message is displayed for the document has hidden properties");
		docView.selectDocsFromFamilyMemberAndViewTheDocument();
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		softassertion.assertEquals(baseClass.getSecondLineOfWarningMsg().getText(),
				"Hidden Columns;Hidden Rows;Hidden Sheets");
		baseClass.passedStep(
				"Warning message is displayed successfully when the document is selected which contains hidden properties from analytics panel");
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().ScrollTo();
		docViewRedact.hiddenInfoIcon().waitAndClick(10);
		baseClass.stepInfo("Hidden info action is performed");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		softassertion.assertEquals(baseClass.getSecondLineOfWarningMsg().getText(),
				"Hidden Columns;Hidden Rows;Hidden Sheets");
		baseClass.passedStep(
				"After performing hidden info action, Warning message is displayed for the document has hidden properties");
		softassertion.assertAll();
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51956
	 * 
	 */
	@Test(description ="RPMXCON-51956",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentMessagewhilenavigatingFromDocNumber(String fullName, String userName,
			String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is not presented in the Viewer. Please download the native to review.";
		String expectedMessage2 = "Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...";
		String expectedMessage3 = "Protected Excel Sheets";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51956");
		baseClass.stepInfo(
				"Verify that when document number is entered to view having hidden content then should display the warning message to indicate that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch("Hidden");
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.pageNumberTextBox().Visible() && docViewRedact.pageNumberTextBox().Enabled();
			}
		}), Input.wait30);
		docViewRedact.pageNumberTextBox().waitAndClick(10);
		docViewRedact.pageNumberTextBox().getWebElement().clear();
		docViewRedact.pageNumberTextBox().getWebElement().sendKeys(Input.pageNumber);
		driver.waitForPageToBeReady();
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);
	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51946
	 * 
	 */
	@Test(description ="RPMXCON-51946",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenInfoIconinDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51946");
		baseClass.stepInfo(
				"Verify that a Hidden Info icon is presented in the action icon section of Doc View if the document being viewed has any Hidden Content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.DocIdWithHiddenContent);
		sessionsearch.ViewInDocView();
		if (docViewRedact.hiddenInfoIcon().isDisplayed() == true) {
			baseClass.passedStep("Hidden info Icon is visible for document with hidden content");
		} else {
			baseClass.failedStep("Hidden info icon is not visible for document with hidden content");
		}
	}

	/**
	 * Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50780 Description :To verify menu bar options from doc view page
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50780",enabled = true, groups = { "regression" })
	public void verifyMenuBarOptionsInDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);

		String assignmentName = "Atestassignment" + Utility.dynamicNameAppender();

		// Login as RMU and verify menu bar in docView
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50780");
		baseClass.stepInfo("To verify menu bar options from doc view page");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		assignPage.addReviewerAndDistributeDocs();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignPage.selectAssignmentToViewinDocView(assignmentName);
		baseClass.stepInfo("Selected assignment and navigated to docview");
		// Verifying the menu bar option in docView page
		docView.verifyMenuBarOptionFromDocviewPanel();
	}

	
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify that when viewing the document having the
	 *               'ExcelProtectedWorkbook' value should provide indicator in
	 *               viewer to convey that document is having hidden content
	 */
	@Test(description ="RPMXCON-51952",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyExcelProtectWorkbookWarningMsg(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51952");
		baseClass.stepInfo(
				"Verify that when viewing the document having the 'ExcelProtectedWorkbook' value should provide indicator in viewer to convey that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		if (fullName.contains("RMU")) {
			baseClass.stepInfo("Successfully login as Review Manager'" + userName + "'");
		}
		if (fullName.contains("PA")) {
			baseClass.stepInfo("Successfully login as Project admin'" + userName + "'");
		}
		if (fullName.contains("REV")) {
			baseClass.stepInfo("Successfully login as Reviewer'" + userName + "'");
		}
		// document searched and navigated to docview
		sessionsearch.basicContentSearch(Input.excelProtectedHiddenDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docview page from search engine");
		// validate the warning message contains hidden properties
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		softassertion.assertEquals(baseClass.getSecondLineSuccessMsg(1).getText(),
				"Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...");
		softassertion.assertEquals(baseClass.getSecondLineSuccessMsg(2).getText(), "Protected Excel Workbook");
		baseClass.passedStep("Warning message is displayed for the excel protect workbook as expected");
		softassertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify that when document with hidden content is clicked to
	 *               view from mini doc list child window then should display the
	 *               warning message to indicate that document is having hidden
	 *               content
	 */
	@Test(description ="RPMXCON-51955",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyWarningMsgFromMinidoclistChildWindow(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject );
		baseClass.stepInfo("Test case Id: RPMXCON-51955");
		baseClass.stepInfo(
				"Verify that when document with hidden content is clicked to view from mini doc list child window then should display the warning message to indicate that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		if (fullName.contains("RMU")) {
			baseClass.stepInfo("Successfully login as Review Manager :" + userName + "'");
		}
		if (fullName.contains("PA")) {
			baseClass.stepInfo("Successfully login as Project admin :" + userName + "'");
		}
		if (fullName.contains("REV")) {
			baseClass.stepInfo("Successfully login as Reviewer :" + userName + "'");
		}
		// document searched and navigated to docview
		sessionsearch.basicContentSearch(Input.excelProtectedHiddenDocId);
		sessionsearch.ViewInDocView();
		// open the child window and validate the warning message
		docView.clickGearIconOpenMiniDocList();
		baseClass.stepInfo("Mini doc list child window is opened");
		docView.switchToNewWindow(2);
		baseClass.stepInfo("Switched to new child window successfully");
		baseClass.waitTillElemetToBeClickable(docView.getDocView_DocId(Input.excelProtectedHiddenDocId));
		docView.getDocView_DocId(Input.excelProtectedHiddenDocId).waitAndClick(10);
		docView.switchToNewWindow(1);
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		softassertion.assertEquals(baseClass.getSecondLineSuccessMsg(1).getText(),
				"Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...");
		softassertion.assertEquals(baseClass.getSecondLineSuccessMsg(2).getText(), "Protected Excel Workbook");
		baseClass.passedStep(
				"Warning message is displayed successfully when the document is selected which contains hidden properties from minidoclist child window");
		// validate the warning message after click the hidden info icon
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().waitAndClick(10);
		baseClass.stepInfo("Hidden info action is performed");
		baseClass.VerifyWarningMessage(
				"The document has the following hidden information that is not presented in the Viewer. Please download the native to review.");
		softassertion.assertEquals(baseClass.getSecondLineSuccessMsg(1).getText(),
				"Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...");
		softassertion.assertEquals(baseClass.getSecondLineSuccessMsg(2).getText(), "Protected Excel Workbook");
		baseClass.passedStep(
				"After performing hidden info action, Warning message is displayed for the document has hidden properties");
		softassertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @author
	 * @TestCase Id:51965 Validate Search, Highlighling, PDF creation, and download
	 *           of native, text and pdf
	 * @Description:To Validate Search, Highlighling, PDF creation, and download of
	 *                 native, text and pdf
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51965",alwaysRun = true, groups = { "regression" })
	public void verifySearchHighlighAndDownloadFiles() throws InterruptedException {
		String metaDataField = "DocFileExtension";
		String fileExtensionName = ".wmf";
		String downloadOption = "Native 1";
		String searchTerm = "enron";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51965 sprint 11");
		baseClass.stepInfo("###Validate Search, Highlighling, PDF creation, and download of native, text and pdf###");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		baseClass.stepInfo("basic metadata search");
		session.basicMetaDataSearch(metaDataField, null, fileExtensionName, null);
		baseClass.stepInfo("view in DocView");
		session.ViewInDocView();
		baseClass.stepInfo("Download and verify files ");
		docView.downloadFile(downloadOption, fileExtensionName);
		baseClass.stepInfo("verify images tab");
		docView.clickOnImageTab();
		baseClass.stepInfo("verify text Tab");
		docView.clickOnTextTab();
		baseClass.stepInfo("Navigate to search page");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.stepInfo("remove docs from cart");
		session.Removedocsfromresults();
		baseClass.stepInfo("basic content serch ");
		session.multipleBasicContentSearch(searchTerm);
		baseClass.stepInfo("view in docView");
		session.ViewInDocView();
		baseClass.stepInfo("verify highlighting of search term");
		docView.persistenHitWithSearchString(searchTerm);
	}

	/**
	 * @author
	 * @TestCase Id:51265 Verify user can view unicode files in near native view in
	 *           context of an assignment
	 * @Description:To Verify user can view unicode files in near native view in
	 *                 context of an assignment
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51265",alwaysRun = true, groups = { "regression" })
	public void verifyUnicodeFilesInNearNativeView() throws InterruptedException {
		String AssignmentName = Input.randomText + Utility.dynamicNameAppender();
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51265 sprint 10");
		baseClass
				.stepInfo("####Verify user can view unicode files in near native view in context of an assignment####");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docView = new DocViewPage(driver);
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		baseClass.stepInfo(" Basic content search for UniCode Docs");
		session.basicContentSearch(Input.UniCodeDocId);
		baseClass.stepInfo("Create bulk assign with new assignment with persistant hit.");
		session.bulkAssignWithNewAssignmentWithPersistantHit(AssignmentName, Input.codingFormName);
		baseClass.stepInfo("view Assignment in docView");
		assignmentPage.selectAssignmentToViewinDocview(AssignmentName);
		baseClass.stepInfo("verify files displayed in near native view");
		docView.clickOnDefaultTextTab();

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51266
	 * 
	 */
	@Test(description ="RPMXCON-51266",enabled = true, dataProvider = "userDetails2", alwaysRun = true, groups = { "regression" })
	public void verifyUniCodeFilesViewInDocViewforRMUandREV(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51266");
		baseClass.stepInfo("Verify user can view unicode files in default view outside of an assignment");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("DocID", null, Input.UniCodeDocId, null);
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		docViewRedact.verifyingActiveDocIdInDocView(Input.UniCodeDocId);
		baseClass.passedStep("Sucsessfully Viewed Uni code file in DocView");
		loginPage.logout();

	}


	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51954
	 * 
	 */
	@Test(description ="RPMXCON-51954",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyMessageForHiddenContentDocsExcelProtecTedWorkSheets(String fullName, String userName,
			String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is not presented in the Viewer. Please download the native to review.";
		String expectedMessage2 = "Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...";
		String expectedMessage3 = "Protected Excel Sheets";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51954");
		baseClass.stepInfo(
				"Verify that when document with hidden content is clicked to view from mini doc list then should display the warning message to indicate that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.HiddenContentExcelSheet);
		sessionsearch.ViewInDocView();
		DocViewPage docviewpage = new DocViewPage(driver);
		docviewpage.selectDocIdInMiniDocList(Input.HiddenContentExcelSheet);
		baseClass.stepInfo("Document with hidden content - excel protected worsheet selected from mini doclist");
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);

	}

	@DataProvider(name = "userDetailss")
	public Object[][] userLoginDetailss() {
		return new Object[][] { { Input.rmu1userName, Input.rmu1password, Input.rmu2userName, Input.rmu2password },
				{ Input.rev1userName, Input.rev1password, Input.rev2userName, Input.rev2password } };
	}

	/**
	 * Author : Steffy date: NA Modified date: NA Modified by: NA Test Case Id:
	 * RPMXCON-51540 Verify that when two different users deletes reviewer remarks
	 * to the same record successfully, and confirm that the XML nodes are all
	 * properly created/reflected in the XML
	 * stabilization done
	 */
	@Test(description ="RPMXCON-51540",enabled = true, dataProvider = "userDetailss", groups = { "regression" })
	public void verifyRemarkPanelRemarkDetailsAfterDeletionBetweenTwoUsers(String firstUserName,
			String firstUserPassword, String secondUserName, String secondUserPassword) throws Exception {

		String remark = Input.randomText + Utility.dynamicNameAppender();

		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		loginPage = new LoginPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51540");
		baseClass.stepInfo(
				"Verify that when two different users deletes reviewer remarks to the same record successfully, and confirm that the XML nodes are all properly created/reflected in the XML");

		baseClass.stepInfo("Creating Prerequisite Reviewer Remark");
		baseClass.stepInfo("Adding new remark to document as a prerequisite one");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Searching the docs using basic search and viewing in doc view");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.searchString2);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Perfrom non audio remark");
		docView.addRemarkByText(remark);
		loginPage.logout();
		baseClass.stepInfo("Prerequisites creation completed");

		baseClass.stepInfo("Login in to sightline using first user " + firstUserName);
		loginPage.loginToSightLine(firstUserName, firstUserPassword);
		baseClass.stepInfo("logged in as" + firstUserName);
		baseClass.stepInfo("Searching the docs using basic search and viewing in doc view");
		sessionsearch.basicContentSearch(Input.searchString2);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String firstUserWindow = driver.CurrentWindowHandle();
		baseClass.stepInfo("Opening a new tab");
		baseClass.openNewTab();
		driver.switchToChildWindow();
		baseClass.stepInfo("Navigating to Sighline URL");
		driver.Navigate().to(Input.url);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Login in to sightline using second user " + secondUserName);
		loginPage.loginToSightLine(secondUserName, secondUserPassword);
		baseClass.stepInfo("logged in as" + secondUserName);
		baseClass.stepInfo("Searching the docs using basic search and viewing in doc view");
		sessionsearch.basicContentSearch(Input.searchString2);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		String secondUserWindow = driver.CurrentWindowHandle();
		baseClass.stepInfo("Switching back to first window to delete remark");
		driver.switchToWindow(firstUserWindow);
		docView.deleteReamark(remark);
		baseClass.stepInfo("Switching back to second window to warning message displayed in all 3 panels");
		driver.switchToWindow(secondUserWindow);
		docView.verifyWarningMessage("Annotation");
		docView.verifyAppliedAnnotationSubMenusAreDisabled();
		docView.verifyWarningMessage("Redaction");
		docView.verifyAppliedRedactionSubMenusAreDisabled();
		docView.verifyWarningMessage("Remark");
		baseClass.stepInfo("Verify remark edit is not displayed for existing remarks");
		docView.verifyReviewRemarkActionPanel("Edit", remark);
		baseClass.stepInfo("Verify remark delete is not displayed for existing remarks");
		docView.verifyReviewRemarkActionPanel("Delete", remark);
		baseClass.stepInfo("Verify deleted remark is not displayed after reloading the second window page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.verifyReviewRemarkActionPanel("Remark", remark);
		driver.switchToWindow(firstUserWindow);
		baseClass.stepInfo("Verify deleted remark is not displayed after reloading the first window page");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.verifyReviewRemarkActionPanel("Remark", remark);


	}


	
	/*  
     *Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-50914
	 * Description :To verify user can view the document for review in text view after impersonation
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-50914",enabled = true, groups = {"regression" })
	public void verifyTextViewAfterImpersonation() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		
		String assignmentName = "Atestassignment"+utility.dynamicNameAppender();
		
		//Creating assignment for step 9 and 11 as a pre-requisite for RMU impersonation to avoid creating multiple assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50914");
		baseClass.stepInfo("To verify user can view the document for review in text view after impersonation");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignExisting(assignmentName);
		assignPage.editAssignment(assignmentName);
		assignPage.addReviewerAndDistributeDocs();
		loginPage.logout();
		
		//Impersonate from SA to PA and verify textView in docViewPage
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("Impersonated from SA to PA");
		docView.verifyTextviewInDocviewFromDifferentModuleforPAandREV();
		loginPage.logout();
		//Impersonate from SA to RMU and verify textView in docViewPage
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Impersonated from SA to RMU");
		docView.verifyTextviewInDocviewFromDifferentModuleforRMU(assignmentName);
		loginPage.logout();
		//Impersonate from SA to Reviewer and verify textView in docViewPage
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Impersonated from SA to Reviewer");
		docView.verifyTextviewInDocviewFromDifferentModuleforPAandREV();
		loginPage.logout();
		//Impersonate from PA to RMU and verify textView in docViewPage
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonated from PA to RMU");
		docView.verifyTextviewInDocviewFromDifferentModuleforRMU(assignmentName);
		loginPage.logout();
		//Impersonate from PA to Reviewer and verify textView in docViewPage
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Impersonated from PA to Reviewer");
		docView.verifyTextviewInDocviewFromDifferentModuleforPAandREV();
		loginPage.logout();	
		//Impersonate from  RMU to Reviewer and verify textView in docViewPage
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated from RMU to Reviewer");
		docView.verifyTextviewInDocviewFromDifferentModuleforPAandREV();
		
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51948
	 * 
	 */
	@Test(description ="RPMXCON-51948",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyMouseHoverOfHiddenContentIconDocView(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		Actions actions = new Actions(driver.getWebDriver());
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51948");
		baseClass.stepInfo("Verify that tool tip should be displayed on mouse hover of the hidden icon");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.HiddenContentExcelBook);
		sessionsearch.ViewInDocView();
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		
		actions.moveToElement(docViewRedact.hiddenInfoIcon().getWebElement()).build().perform();
		baseClass.waitForElement(docViewRedact.hiddenInfoIcon());
		String text = docViewRedact.hiddenInfoIconToolTip().GetAttribute("title");
		System.out.println(text);
		if(text.equalsIgnoreCase("Hidden properties")) {
			baseClass.passedStep("The mouse hover is on hidden info icon and disply Hidden Properties");
		} else {
		baseClass.failedStep("The mouse hover is on hidden info icon and do not disply Hidden Properties");
		}
	}
	
	
	

	/*  
     *Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51729
	 * Description :Verify that persistent hits should be highlighted when documents are assigned to existing assignment from Advanced Search > Doc List
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-51729",enabled = true, groups = {"regression" })
	public void verifyPersistentHitsHighlightWhenAssigned() throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);
		DocListPage docList = new DocListPage(driver);
		
		String assignmentName = "Atestassignment"+utility.dynamicNameAppender();
  	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-51729");
		baseClass.stepInfo("Verify that persistent hits should be highlighted when documents are assigned to existing assignment from Advanced Search");
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		int availableDocs =sessionsearch.advancedContentSearch(Input.testData1);
		sessionsearch.ViewInDocList();
		driver.waitForPageToBeReady();
		docList.documentSelection(availableDocs);
		docList.bulkAssignWithPersistantHit(assignmentName);
		assignPage.editAssignment(assignmentName);
		assignPage.addReviewerAndDistributeDocs();
		driver.waitForPageToBeReady();
		assignPage.Assignment_ManageRevtab_ViewinDocView();
		docView.verifyDocsPresentWithPersistentHits(Input.testData1);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");
		assignPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Select assigned assignment and Navigated to docview");
		docView.verifyDocsPresentWithPersistentHits(Input.testData1);
	}
	

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51960
	 * 
	 */
	@Test(description ="RPMXCON-51960",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentDocswhileSwitchingViews(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is not presented in the Viewer. Please download the native to review.";
		String expectedMessage2 = "Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...";
		String expectedMessage3 = "Protected Excel Workbook";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51960");
		baseClass.stepInfo("Verify that on tabs navigation if document loads with hidden content then should display the warning message to indicate that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.HiddenContentExcelBook);
		sessionsearch.ViewInDocView();	
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);
		baseClass.waitTillElemetToBeClickable(docViewRedact.imagesIconDocView());
		docViewRedact.imagesIconDocView().waitAndClick(10);
		baseClass.stepInfo("Navigated to images tab");
		docViewRedact.defaultViewTab().waitAndClick(10);
		baseClass.stepInfo("Navigated back to default/Native tab");
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);	
		baseClass.waitTillElemetToBeClickable(docViewRedact.hiddenInfoIcon());
		docViewRedact.hiddenInfoIcon().waitAndClick(10);
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);	
		loginPage.logout();
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51953
	 * 
	 */
	@Test(description ="RPMXCON-51953",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyMessageForHiddenContentDocsFromDocNavigation(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is presented in the Viewer.";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51953");
		baseClass.stepInfo("Verify that on doc-to-doc navigation if document loads with hidden content then should display the warning message to indicate that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.TextHidden);
		sessionsearch.ViewInDocView();
		baseClass.waitTillElemetToBeClickable(docViewRedact.forwardNextDocBtn());
		docViewRedact.forwardNextDocBtn().waitAndClick(5);
		baseClass.stepInfo("navigated to Document with hidden content");
		driver.waitForPageToBeReady();	
		baseClass.VerifyWarningMessage(expectedMessage1);
		if(docViewRedact.hiddenInfoIcon().isDisplayed()) {
			baseClass.passedStep("The hidden info icon is present in the docViewr to indicate hidden content");
		} else {
			baseClass.failedStep("The hidden info icon is NOT present in the docViewr to indicate hidden content");
		}
	}
	
	

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51106
	 * 
	 */
	@Test(description ="RPMXCON-51106",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyDocViewOptionsInTiff(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-51106");
		baseClass.stepInfo("Verify user can select any of the menu option from Images tab- Zoom in, Zoom out, rotation, Slider, Fit to Width");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.TiffDocId);
		sessionsearch.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docViewRedact.imagesIconDocView().Visible() && docViewRedact.imagesIconDocView().Enabled();
			}
		}), Input.wait30);
		docViewRedact.imagesIconDocView().waitAndClick(5);
		baseClass.stepInfo("Successfully navigated to images tab in DocView");
		if(docViewRedact.imagesTabZoomOut().isDisplayed()) {
			baseClass.passedStep("images Tab zomm out is Visible");
		} else {
			baseClass.failedStep("images Tab zoom out is not visible");
		}
		
		if(docViewRedact.imagesTabZoomIn().isDisplayed()) {
			baseClass.passedStep("images Tab zoom in is Visible");
		} else {
			baseClass.failedStep("images Tab zoom in is not visible");
		}
	
		if(docViewRedact.imagesTabZoomFitToScreen().isDisplayed()) {
			baseClass.passedStep("images Tab fit to screen option is avilablee");
		} else {
			baseClass.failedStep("images Tab fit to screen option is not avilable");
		}
		
		if(docViewRedact.ImagesTabRightRotate().isDisplayed()) {
			baseClass.passedStep("images Tab Rotate right option is avilablee");
		} else {
			baseClass.failedStep("images Tab Rotate right option is not avilable");
		}
		
		if(docViewRedact.ImagesTabLeftRotate().isDisplayed()) {
			baseClass.passedStep("images Tab Rotate left option is avilablee");
		} else {
			baseClass.failedStep("images Tab Rotate left option is not avilable");
		}
		
		if(docViewRedact.imagesTabSlider().isDisplayed()) {
			baseClass.passedStep("images Tab Slider option is avilablee");
		} else {
			baseClass.failedStep("images Tab Slider option is not avilable");
		}
		loginPage.logout();
		
	}

  
  	/*  
     *Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51306
	 * Description :Verify persistent Hit panel of DocView should present only content terms, not work product when navigating from advance search
	 */
	@Test(description ="RPMXCON-51306",enabled = true, dataProvider = "userDetails", groups = {"regression" })
	public void verifyWorkProductDisplayOnPersistentPanel(String fullName, String userName, String password) throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
	
		String searchName = "Asavedsearch"+utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(userName,password);
		baseClass.stepInfo("Test case Id: RPMXCON-51306");
		baseClass.stepInfo("Verify persistent Hit panel of DocView should present only content terms, not work product when navigating from advance search");
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.saveSearch(searchName);
		driver.waitForPageToBeReady();
		sessionsearch.selectSavedsearchInASWp(searchName);
		sessionsearch.serarchWP();
		sessionsearch.ViewInDocView();
		docView.verifyWorkProductTermDisplayingOnPersistentPanel(searchName);
		loginPage.logout();
		
	}
	

	/*  
     *Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51107
	 * Description :Verify user can download the redacted document from default view using print icon outside of an assignment.
	 * validated the step of checking the format of Downloaded document in pdf for rectangle and current page redaction
	 */
	@Test(description ="RPMXCON-51107",enabled = true, groups = {"regression" })
	public void verifyDownloadDocsInPDF() throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		
		//pre-requisites- Rectangle redaction and current page redaction
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51107");
		baseClass.stepInfo("Verify user can download the redacted document from default view using print icon outside of an assignment");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return docView.getDocView_RedactIcon().Visible() && docView.getDocView_RedactIcon().Enabled();
			}
		}), Input.wait30);
		baseClass.waitTillElemetToBeClickable(docView.getDocView_RedactIcon());
		docView.getDocView_RedactIcon().Click();
		docViewRedact.performThisPageRedaction(Input.defaultRedactionTag);
		baseClass.stepInfo("Current Page Redaction Completed");
		driver.waitForPageToBeReady();
		docViewRedact.redactionIcon().waitAndClick(10);
		docViewRedact.redactRectangleUsingOffset(0, 0, 50, 100);
		docViewRedact.selectingRectangleRedactionTag();
		baseClass.stepInfo("Rectangle Redaction Completed");
		loginPage.logout();
		
		//Login as RMU and verify the document download is in pdf format for rectangle and current page redaction
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.additionalDataProject);
		baseClass.stepInfo("Logged in as RMU");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docView.verifyDocumentDownloadInPdfFormat();
		loginPage.logout();
		//Login as Reviewer and verify the document download in pdf format for rectangle and current page redaction
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password, Input.additionalDataProject);
		baseClass.stepInfo("Logged in as Reviewer");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docView.verifyDocumentDownloadInPdfFormat();	
		
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51951
	 * 
	 */
	@Test(description ="RPMXCON-51951",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentDocs(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is not presented in the Viewer. Please download the native to review.";
		String expectedMessage2 = "Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...";
		String expectedMessage3 = "Protected Excel Workbook";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51951");
		baseClass.stepInfo("Verify that when viewing the document having the 'Hidden Properties' value should provide indicator in viewer to convey that document is having hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		
//Selecting Doc with excel protected workbook
		sessionsearch.basicContentSearch(Input.HiddenContentExcelBook);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document with hidden content - excel protected workbook selected from mini doclist");
		driver.waitForPageToBeReady();	
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);
		driver.waitForPageToBeReady();
		baseClass.selectproject(Input.additionalDataProject);

//Selecting Doc with excel protected worksheet		
		String expectedMessage4 = "Hidden Columns;Protected Sheets";
		String expectedMessage5 = "Protected Excel Sheets";
		sessionsearch.basicContentSearch(Input.HiddenContentExcelSheet);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document with hidden content - excel protected worksheet selected from mini doclist");	
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage4, expectedMessage5);	
		loginPage.logout();
		
	}
	/**
	 * Author :Vijaya.Rani date: 17/02/2022  Modified date: NA Modified by: NA Description
	 * :Verify user after impersonation user can see the keywords highlighted in doc
	 * view based on the assigned keyword group and color to the security group.
	 * 'RPMXCON-51035' Sprint-12
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51035",enabled = true, groups = { "regression" })
	public void verifyImpersonationKeyWordsHighLightingDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51035 sprint 12");
		SessionSearch search = new SessionSearch(driver);
		String keywordname = Input.randomText + Utility.dynamicNameAppender();
		String keyword = Input.randomText + Utility.dynamicNameAppender();
		KeywordPage keywordPage = new KeywordPage(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Verify user after impersonation user can see the keywords highlighted in doc view based on the assigned keyword group and color to the security group");

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();
		driver.Manage().window().fullscreen();
		baseClass.stepInfo("Add keyword");
		keywordPage.AddKeyword(keywordname, keyword);
		driver.Manage().window().maximize();
		baseClass.stepInfo("Get All Keywords in keywords lsit table");
		keywordPage.getAllKeywords();
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("View In Doc View");
		search.ViewInDocView();
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonating SA to Reviewer");
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("View In Doc View");
		search.ViewInDocView();
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();

		// login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("View In Doc View");
		search.ViewInDocView();
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();

		// login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonating PA to Reviewer");
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("View In Doc View");
		search.ViewInDocView();
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("View In Doc View");
		search.ViewInDocView();
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		docView.verifyKeywordHighlightedOnDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();

		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keyword);

	}

	/**
	 * Author :Vijaya.Rani date: 17/02/2022 Modified date: NA Modified by: NA Description:
	 * Verify user after impersonation can see the keywords highlighted in doc view
	 * based on the assigned keyword group and color to the assignment in context of
	 * assignment.'RPMXCON-51036' Sprint-12
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51036",enabled = true, groups = { "regression" })
	public void verifyImpersonationKeyWordsHighLightingAssignmentDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51036 sprint 12");
		AssignmentsPage assgnPage = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		String Assname = "Assigname" + Utility.dynamicNameAppender();
		String keywordname = Input.randomText + Utility.dynamicNameAppender();
		String keyword = Input.randomText + Utility.dynamicNameAppender();
		KeywordPage keywordPage = new KeywordPage(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo(
				"Verify user after impersonation can see the keywords highlighted in doc view based on the assigned keyword group and color to the assignment in context of assignment");

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();
		driver.Manage().window().fullscreen();
		baseClass.stepInfo("Add keyword");
		keywordPage.AddKeyword(keywordname, keyword);
		driver.Manage().window().maximize();
		baseClass.stepInfo("Get All Keywords in keywords lsit table");
		keywordPage.getAllKeywords();
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Bulk Assign");
		search.bulkAssign();
		baseClass.stepInfo("Assignment Creation");
		assgnPage.assignDocstoNewAssgnEnableAnalyticalPanel(Assname,  Input.codingFormName, SessionSearch.pureHit);
		baseClass.stepInfo(" Go to doc view from my assignment");
		assgnPage.selectAssignmentToViewinDocview(Assname);
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyHighlightedKeywordInDocView();
		loginPage.logout();

		// login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonating PA to RMU");
		baseClass.impersonatePAtoRMU();
		assgnPage.selectAssignmentToViewinDocview(Assname);
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyHighlightedKeywordInDocView();
		loginPage.logout();

		// login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		baseClass.impersonateRMUtoReviewer();
		// selecting the assignment
		baseClass.stepInfo("Selecting the assignment");
		assgnPage.SelectAssignmentByReviewer(Assname);
		baseClass.stepInfo("Persistent Hit With search string");
		docView.persistenHitWithSearchString(keyword);
		baseClass.stepInfo("Verify keyword highlighted on doc view.");
		baseClass.stepInfo("verify highlight keyword in document");
		docView.verifyHighlightedKeywordInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Navigate to keyword page");
		keywordPage.navigateToKeywordPage();
		baseClass.stepInfo("Delete keyword");
		keywordPage.deleteKeywordByName(keyword);

	}
	
	
	
	/*  
     *Author :Arunkumar date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51561
	 * Description :Verify that if the text is a multi-word text (e.g. Hello there), it is considered a phrase and highlights only the phrases "Hello there".
	 */
	@Test(description ="RPMXCON-51561",enabled = true, dataProvider = "userDetails", groups = {"regression" })
	public void verifyMultiwordTextHighlightAsPhrase(String fullName, String userName, String password) throws InterruptedException  {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		loginPage.loginToSightLine(userName,password);
		baseClass.stepInfo("Test case Id: RPMXCON-51561");
		baseClass.stepInfo("Verify that if the text is a multi-word text, it is considered a phrase and highlights only the phrases.");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		driver.waitForPageToBeReady();
		//verifying the multi word text highlight
		docView.verifyMultiwordTextHighlightOnDocview(Input.multiwordText);
	}
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-47230
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-47230",enabled = true, dataProvider = "userDetails", groups = {"regression" })
	public void verifyDocViewScreenNavigationFromBasicSearch(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		loginPage.loginToSightLine(userName,password);
		baseClass.stepInfo("Test case Id: RPMXCON-47230");
		baseClass.stepInfo("Shared Steps: Navigating to DocView Screen through Basic Search");
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Navigated to docView");
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Navigated to DocView page from basic search successfully");
		
	}
	
	/**
	 * @throws Exception
	 * @Author : Sai Krishna date: 24/02/2021 Modified date: NA Modified by:
	 * @Description:Verify RMU/Reviewer can see persistent search on doc view page
	 *                     for audio file in context of an assignment.
	 * 
	 */
	@Test(description = "RPMXCON-51056",enabled = true, groups = { "regression" })
	public void verifyCanSeePersistentSearchOnDocViewAudioFile() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-51056");
		baseClass.stepInfo(
				"Verify RMU/Reviewer can see persistent search on doc view page for audio file in context of an assignment");

		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docView = new DocViewPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String assignmentname = "assgnment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation on audio
		sessionsearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionsearch.addPureHit();
		driver.scrollPageToTop();
		sessionsearch.bulkAssignWithOutPureHit();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		baseClass.passedStep("persistent hits is enabled while creating the assignment");
		loginPage.logout();

		// select assignment on DocView
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		docViewRedact.checkingPersistentHitPanelAudio();
		baseClass.passedStep("Persistent search is displayed on audio doc");

		// search to Assignment creation on audio
		sessionsearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionsearch.addPureHit();
		driver.scrollPageToTop();
		sessionsearch.bulkAssignWithOutPureHit();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assignmentname, codingForm, SessionSearch.pureHit);
		baseClass.stepInfo("persistent hits is enabled while creating the assignment");
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentToViewinDocview(assignmentname);
		docViewRedact.checkingPersistentHitPanelAudio();
		if (docViewRedact.persistantHitRightNavigate().Displayed()
				&& docViewRedact.persistantHitRightNavigate().Enabled()) {
			assertTrue(true);
			baseClass.passedStep(
					assignmentname + "Audio doc is highlighted on doc view and show on the persistent hits panel");
		} else {
			assertTrue(false);
		}

		// verify another AudioDoc is highlighted on DocView
		driver.waitForPageToBeReady();
		assignmentsPage.selectAssignmentToViewinDocview(assname);
		docViewRedact.checkingPersistentHitPanelAudio();
		if (docViewRedact.persistantHitRightNavigate().Displayed()
				&& docViewRedact.persistantHitRightNavigate().Enabled()) {
			assertTrue(true);
			baseClass
					.passedStep(assname + "Audio doc is highlighted on doc view and show on the persistent hits panel");
		} else {
			assertTrue(false);
		}

	}

	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51994
	 * 
	 */
	@Test(description ="RPMXCON-51994",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentContainsComments(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is not presented in the Viewer. Please download the native to review.";
		String expectedMessage2 = "Contains Comments;Hidden Columns;Hidden Rows;Hidden Sheets;Pr...";
		String expectedMessage3 = "Protected Excel Sheets";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51994");
		baseClass.stepInfo("Verify that for hidden property \"contains comments\", the message should be modified that should ask the user to \"download the native\" to review");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.TextHidden);
		sessionsearch.ViewInDocView();
		DocViewPage docviewpage = new DocViewPage(driver);	
//Selecting Doc that contains comments
		docviewpage.selectDocIdInMiniDocList("ID00000173");
		baseClass.stepInfo("Document with hidden content -Contains comments selected from mini doclist");
		driver.waitForPageToBeReady();	
		baseClass.VerifyWarningMessageAdditionalLine(expectedMessage1, expectedMessage2, expectedMessage3);
		baseClass.passedStep("The document contains comments and displays download the native to review");
		loginPage.logout();
		
	}
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51993
	 * 
	 */
	
	
	@Test(description ="RPMXCON-51993",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentContainsTrackChanges(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is presented in the Viewer.";
		String expectedMessage2 = "Track Changes;Contains Comments;Hidden Text";
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51993");
		baseClass.stepInfo("Verify that for hidden property \"track changes\", the message should be modified that should not ask the user to download the native to review");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.TextHidden);
		sessionsearch.ViewInDocView();
		DocViewPage docviewpage = new DocViewPage(driver);	
//Selecting Doc that contains Track changes
		docviewpage.selectDocIdInMiniDocList(Input.DocIdWithTrackChanges);
		baseClass.stepInfo("Document with hidden content - Track changesselected from mini doclist");
		driver.waitForPageToBeReady();	
	driver.WaitUntil((new Callable<Boolean>() {
		public Boolean call() {
			return baseClass.getSuccessMsgHeader().Visible();
		}
	}), Input.wait30);
	Assert.assertEquals("Warning !", baseClass.getSuccessMsgHeader().getText().toString());
	Assert.assertEquals(expectedMessage1, baseClass.getSuccessMsg().getText().toString());
	Assert.assertEquals(expectedMessage2, baseClass.getSecondLineSuccessMsg(1).getText().toString());
	baseClass.passedStep("The document contains comments and displays download the native to review");
	loginPage.logout();
	}
	
	



/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51987
	 * 
	 */
	
	
	@Test(description ="RPMXCON-51987",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentFromIngestionName() throws Exception {
		baseClass = new BaseClass(driver);
		String expectedMessage1 = "The document has the following hidden information that is presented in the Viewer.";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51987");
		baseClass.stepInfo("Verify Warning message for hidden content if document is processed by NUIX <TBD>");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicMetaDataSearch("IngestionName", null, Input.HiddenIngestionName, null);
		sessionsearch.ViewInDocView();
		DocViewPage docviewpage = new DocViewPage(driver);	
		docviewpage.selectDocIdInMiniDocList(Input.HiddenIngestionDocId);
		baseClass.stepInfo("Document with hidden content selected from mini doclist");
		driver.waitForPageToBeReady();	
		baseClass.VerifyWarningMessage(expectedMessage1);
		loginPage.logout();
		
// Verifying as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.additionalDataProject);
		sessionsearch.basicContentSearch(Input.HiddenIngestionDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Document with hidden content selected from mini doclist");
		driver.waitForPageToBeReady();	
		docviewpage.selectDocIdInMiniDocList(Input.HiddenIngestionDocId);
		baseClass.VerifyWarningMessage(expectedMessage1);
		loginPage.logout();
		
// Verifying as Rev
				loginPage.loginToSightLine(Input.rev1userName, Input.rev1password, Input.additionalDataProject);
				sessionsearch.basicContentSearch(Input.HiddenIngestionDocId);
				sessionsearch.ViewInDocView();
				baseClass.stepInfo("Document with hidden content selected from mini doclist");
				driver.waitForPageToBeReady();	
				docviewpage.selectDocIdInMiniDocList(Input.HiddenIngestionDocId);
				baseClass.VerifyWarningMessage(expectedMessage1);
				loginPage.logout();
		
		
	}
	
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case Id:RPMXCON-51949
	 * 
	 */
	
	
	@Test(description ="RPMXCON-51949",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyHiddenContentExternalLink(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(userName, password, Input.additionalDataProject);
		baseClass.stepInfo("Test case Id: RPMXCON-51949");
		baseClass.stepInfo("Verify that when document is having external link as hidden then should not display the warning message and also should not display the icon to indicate hidden content");
		docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
//Searching for ducument with external link hidden		
		sessionsearch.basicContentSearch(Input.HiddenLinkDocId);
		sessionsearch.ViewInDocView();
		if(baseClass.getSuccessMsgHeader().isDisplayed()) {
			baseClass.failedStep("The document having external link hidden - displayed warning message");
		} else { 
			baseClass.passedStep("The warning message is not displayed for the document having external link as hidden content");
		}
	loginPage.logout();
	}
	
	
	
	/**
	 * @author Krishna TestCase Id:51243 Verify user after impersonation should see
	 *         the message like 'No files associated with this document' when user
	 *         ingest only metadata
	 *
	 */
	@Test(description ="RPMXCON-51234",alwaysRun = true, groups = { "regression" })
	public void verifyUserAfterImpersonationMsgOnlyMetaData() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51243");
		baseClass.stepInfo(
				"Verify user after impersonation should see the message like 'No files associated with this document' when user ingest only metadata");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String pdfDocId = Input.pdfDocId;

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonating SA to PA");
		baseClass.impersonateSAtoPA();
		// searching metadataSearch for default pdfDocId
		sessionSearch.basicMetaDataSearch("DocID", null,pdfDocId, null);
		sessionSearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonating SA to RMU");
		// searching metadataSearch for default pdfDocId
		baseClass.impersonateSAtoRMU();
		sessionSearch.basicMetaDataSearch("DocID", null, pdfDocId, null);
		sessionSearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Step 1: Impersonating SA to Rev");
		// searching metadataSearch for default pdfDocId
		baseClass.impersonateSAtoReviewer();
		sessionSearch.basicMetaDataSearch("DocID", null,pdfDocId, null);
		sessionSearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonating PA to Rmu");
		// searching metadataSearch for default pdfDocId
		baseClass.impersonatePAtoRMU();
		sessionSearch.basicMetaDataSearch("DocID", null,pdfDocId, null);
		sessionSearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Step 1: Impersonating PA to Rev");
		// searching metadataSearch for default pdfDocId
		baseClass.impersonatePAtoReviewer();
		sessionSearch.basicMetaDataSearch("DocID", null, pdfDocId, null);
		sessionSearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Step 1: Impersonating RMU to Reviewer");
		// searching metadataSearch for default pdfDocId
		baseClass.impersonateRMUtoReviewer();
		sessionSearch.basicMetaDataSearch("DocID", null,pdfDocId, null);
		sessionSearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51984 Verify in-doc search highlighting is working for Searchable
	 * PDF (with Mapped dataset having RequiredPDFGenartion is TRUE)
	 * 
	 */

	@Test(description ="RPMXCON-51984",enabled = true, dataProvider = "userDetails", alwaysRun = true, groups = { "regression" })
	public void verifyDocHighlightingIsWorkingForSearchablePdf(String fullName, String userName, String password)
			throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51984");
		baseClass.stepInfo(
				"Verify in-doc search highlighting  is working for Searchable PDF (with Mapped dataset having RequiredPDFGenartion is TRUE)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String text = "Message";
		String pdfDocId = Input.pdfDocId;

		// Searching for document with dataset having required PDF
		loginPage.loginToSightLine(userName, password);
		sessionsearch.basicContentSearch(pdfDocId);
		baseClass.stepInfo("Searching a documents having 'RequiredPDFGenertion is TRUE' ");
		sessionsearch.ViewInDocView();
		docView.verifyDisplaysTheDefaultPdfInDocView();

		// verifying a corresponding text and highlighting a document.
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
	}

	/**
	 * Author :Sakthivel date: 23/03/2022 Modified date: NA Modified by: NA
	 * Description Verify that In DocView Reviewer Remarks, when a user while
	 * deleting a reviewer remark gets an error and yet deletes the remark, the
	 * remark should be deleted and the highlighting should not be seen.
	 * 'RPMXCON-60572'
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-60572",enabled = true, groups = { "regression" })
	public void verifyDocViewReviewerRemarkDeletedAndHighlightedNotDisplayed() throws InterruptedException {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-60572");
		SessionSearch search = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		docView = new DocViewPage(driver);
		Actions actions = new Actions(driver.getWebDriver());
		baseClass.stepInfo(
				"Verify that In DocView Reviewer Remarks, when a user while deleting a reviewer remark gets an error and yet deletes the remark, the remark should be deleted and the highlighting should not be seen.");
		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"User successfully logged into slightline webpage as Reviewer with " + Input.rmu1userName + "");
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		String codingForm = Input.codingFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		WebDriverWait wait = new CustomWebDriverWait(driver.getWebDriver(), 100);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		search.basicContentSearch(Input.searchString2);
		search.bulkAssign();
		assignmentPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		tagsAndFolderPage.layerAnnotationsAsRMU();
		baseClass.stepInfo("Annotation layer is already present and released to security group");
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		assignmentPage.selectAssignmentToViewinDocview(assname);
		baseClass.stepInfo("Doc view page is selected from assigment page");
		// verify Remark icon
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_AddRemarkIcon());
		softAssertion.assertTrue(docView.getDocView_AddRemarkIcon().Displayed());
		baseClass.stepInfo("Reviewer remarks panel is displayed on selected document");
		docView.getDocView_AddRemarkIcon().waitAndClick(5);
		wait.until(
				ExpectedConditions.elementToBeClickable(docViewRedact.getDocView_Redactrec_textarea().getWebElement()));
		Thread.sleep(Input.wait3);
		baseClass.waitTime(4);
		// Thread sleep added for the page to adjust resolution
		baseClass.waitTillElemetToBeClickable(docViewRedact.getDocView_Redactrec_textarea());
		docViewRedact.getDocView_Redactrec_textarea().waitAndClick(5);
		actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), -10, 10).clickAndHold()
				.moveByOffset(200, 90).release().build().perform();
		baseClass.waitTillElemetToBeClickable(docViewRedact.addRemarksBtn());
		docViewRedact.addRemarksBtn().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(docViewRedact.addRemarksTextArea());
		docViewRedact.addRemarksTextArea().waitAndClick(10);
		actions.sendKeys("Remark by RMU");
		actions.build().perform();
		actions.moveToElement(docViewRedact.saveRemarksBtn().getWebElement());
		actions.click().build().perform();
		baseClass.passedStep("Verified Remarks is saved successfully");
		driver.waitForPageToBeReady();
		if (docViewRedact.deleteRemarksBtn().isDisplayed()) {
			wait.until(ExpectedConditions.elementToBeClickable(docViewRedact.deleteRemarksBtn().getWebElement()));
			docViewRedact.deleteRemarksBtn().waitAndClick(3);
			docViewRedact.confirmDeleteRemarksBtn().Click();
			baseClass.passedStep(
					"verified remark is deleted successfully and the highlighted on the document is removed");
		} else {
			baseClass.failedStep("verified remarks");
		}
		loginPage.logout();

	}

	

	@DataProvider(name = "multiUserCredentials")
	public Object[][] multiuserLoginDetails() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password, "RMU", "REV" },
				{ Input.pa1FullName, Input.pa1userName, Input.pa1password, "PA", "RMU" },
				{ Input.pa1FullName, Input.pa1userName, Input.pa1password, "PA", "rev" } };
	}

	/**
	 * @author Gopinath
	 * @TestCase Id:51438 Verify that after impersonation if the document
	 *           native/PDF/TIFF/Text is being presented, the N/P/T/X icon with the
	 *           accompanying mouse over tool tip must appear
	 * @Description :To Verify that after impersonation if the document
	 *              native/PDF/TIFF/Text is being presented, the N/P/T/X icon with
	 *              the accompanying mouse over tool tip must appear.
	 * @throws InterruptedException stabilization - not done
	 */
	@Test(description ="RPMXCON-51438",enabled = true,dataProvider="multiUserCredentials", groups = { "regression" })
	public void verifyDocIdIconOnDocViewPanal(String fullName, String userName, String password, String fromRole,String toRole) throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case id : RPMXCON-51438");
		baseClass.stepInfo(
				"Verify that after impersonation if the document native/PDF/TIFF/Text is being presented, the N/P/T/X icon with the accompanying mouse over tool tip must appear");
		String N_DocID = "ID00001351";
		String N_DocToolTipMessage = "Native file variant of the document being displayed";
		String X_DocID = "ID00000102";
		String X_DocToolTipMessage = "Text file variant of the document being displayed";
		String T_DocID = "ID00001012";
		String T_DocToolTipMessage = "TIFF file variant of the document being displayed";
		String P_DocId = "ID00001464";
		String P_DocToolTipMessage = "PDF file variant of the document being displayed";
		loginPage = new LoginPage(driver);
		
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		baseClass.stepInfo("Impersnated from " + fromRole + " to " + toRole);
		baseClass.rolesToImp(fromRole, toRole);

		baseClass.stepInfo("Step 1: Search for the docs ");
		session.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Step 2:view docS in DocView");
		session.ViewInDocView();

		baseClass.stepInfo("Verify T icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(T_DocID, T_DocToolTipMessage);

		baseClass.stepInfo("Verify X icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(X_DocID, X_DocToolTipMessage);

		baseClass.stepInfo("Verifying P icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(P_DocId, P_DocToolTipMessage);

		baseClass.stepInfo("Verifying N icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(N_DocID, N_DocToolTipMessage);

		loginPage.logout();
	}
	

	/**
	 * @author
	 * @TestCase Id:51963 Verify that document having any of the field value "Hidden
	 *           Properties" "ExcelProtectedSheets" ExcelProtectedWorkbook viewed
	 *           from analytics panel child window should display alert message
	 * @Description:To Verify that document having any of the field value "Hidden
	 *                 Properties" "ExcelProtectedSheets" ExcelProtectedWorkbook
	 *                 viewed from analytics panel child window should display alert
	 *                 message
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-51963", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyWarningMsgForHiddenProportiesDocChildWindow() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51963");
		String hiddenText = "ID00000159";
		baseClass.stepInfo(
				"###Verify that document having any of the field value \"Hidden Properties\" \"ExcelProtectedSheets\" ExcelProtectedWorkbook viewed from analytics panel child window should display alert message####");

		baseClass.stepInfo("Log in as user");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.additionalDataProject);

		baseClass.stepInfo("basic content search");
		sessionsearch.basicContentSearch(hiddenText);

		baseClass.stepInfo("view in docView");
		sessionsearch.ViewInDocView();

		baseClass.stepInfo("verify warning message for hidden properties document");
		docView.verifyhiddenPropertiesDOcWaringMessage(hiddenText);

	}
	
	
	/**
	 * @author 
	 * @TestCase Id:51950 Verify that when viewing the document having the 'ExcelProtectedSheets' value should provide indicator in viewer to convey that document is having hidden content
	 * @Description:To Verify that when viewing the document having the 'ExcelProtectedSheets' value should provide indicator in viewer to convey that document is having hidden content
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51950",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyWarningMsgForHiddenProportiesExternalProtectedSheet() throws InterruptedException {
		baseClass = new BaseClass(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51950");
		baseClass.stepInfo("###Verify that when viewing the document having the 'ExcelProtectedSheets' value should provide indicator in viewer to convey that document is having hidden content####");
		
		baseClass.stepInfo("Log in as user");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.additionalDataProject);
		
		baseClass.stepInfo("basic content search");
		sessionsearch.basicContentSearch(Input.HiddenLinkDocId);
		
		baseClass.stepInfo("view in docView");
		sessionsearch.ViewInDocView();
		
		baseClass.stepInfo("verify warning message for hidden properties document");
		docView.verifyWaringMessageForExternalProtectSheet(Input.HiddenLinkDocId);
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51976
	 * 
	 */

	@Test(description ="RPMXCON-51976",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifySinglePageTiffInDocView()
			throws Exception {
		baseClass = new BaseClass(driver);
		 DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51976");
		baseClass.stepInfo("Verify that Single page TIFF image should be load successfully in doc view");
		SessionSearch sessionsearch = new SessionSearch(driver);
		// Searching for document with single page tiff
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionsearch.basicContentSearch(Input.SinglePageTiffSourceDocID);
		baseClass.stepInfo("Searching a documents having 'RequiredPDFGenertion is TRUE' ");
		sessionsearch.ViewInDocView();
		docViewRedact.checkingPersistentHitPanel();
		baseClass.passedStep("Single page tiff successfully viewed in DocViw");
		loginPage.logout();
		
	}
	
	/**
	 * Author : Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51975
	 * 
	 */
	@Test(description ="RPMXCON-51976",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyStitchedPageTiffInDocViewImageTab()
			throws Exception {
		baseClass = new BaseClass(driver);
		 DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51975");
		baseClass.stepInfo("Verify that stitched TIFF should be load on Image tab in Doc View");
		SessionSearch sessionsearch = new SessionSearch(driver);
		// Searching for document with single page tiff
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionsearch.basicContentSearch(Input.StitchedTiffSourceDocID);
		baseClass.stepInfo("Searching a documents having 'RequiredPDFGenertion is TRUE' ");
		sessionsearch.ViewInDocView();
		docViewRedact.clickingImagesTab();
		String status = docViewRedact.imagesIconDocView().GetAttribute("aria-selected");
		System.out.println(status);
		if (status.equalsIgnoreCase("true")) {
			baseClass.passedStep("Single page tiff successfully viewed in DocViw Images tab");
		} else {
			baseClass.failedStep("Single page tiff not viewed in DocViw Images tab");
		}
		loginPage.logout();	
	}
	
	/**
	 * Author :Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-52211 Verify that email metadata should present with single quote
	 * on DocView
	 */

	@Test(description ="RPMXCON-52211",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyEmailMetaDataPresentOnDocView() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52211");
		baseClass.stepInfo("Verify that email metadata should present with single quote on DocView");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU");
		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewThreadedDocsInDocViews();
		baseClass.stepInfo("Basic Search and Docs are viewed in DocView successfully");

		// Verify concatenated and separate fields from metadata tab in DocView.
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docView.getDocView_MetaDataPanel_EmailAuthorName());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailAuthorName().isDisplayed());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailAuthorAddress().isDisplayed());
		baseClass.passedStep(
				"EmailAuthorName/EmailAuthorAddress fields is displayed on metadata panel and fields is a separate fields.");
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailAuthorNameAndAddress().isDisplayed());
		baseClass.passedStep(
				"EmailAuthorNameAndAddress field is displayed on metadata panel and field is a concatenated fields.");

		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docView.getDocView_MetaDataPanel_EmailToNames());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailToNames().isDisplayed());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailToAddresses().isDisplayed());
		baseClass.passedStep(
				"EmailToNames/EmailToAddresses fields is displayed on metadata panel and fields is a separate fields.");
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailToNamesAndAddresses().isDisplayed());
		baseClass.passedStep(
				"EmailToNamesAndAddresses field is displayed on metadata panel and field is a concatenated fields.");

		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docView.getDocView_MetaDataPanel_EmailCCNames());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailCCNames().isDisplayed());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailCCAddresses().isDisplayed());
		baseClass.passedStep(
				"EmailCCNames/EmailCCAddresses fields is displayed on metadata panel and fields is a separate fields.");
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailCCNamesAndAddresses().isDisplayed());
		baseClass.passedStep(
				"EmailCCNamesAndAddresses field is displayed on metadata panel and field is a concatenation fields.");

		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docView.getDocView_MetaDataPanel_EmailBCCNames());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailBCCNames().isDisplayed());
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailBCCAddresses().isDisplayed());
		baseClass.passedStep(
				"EmailBCCNames/EmailBCCAddresses fields is displayed on metadata panel and fields is a separate fields.");
		softassertion.assertTrue(docView.getDocView_MetaDataPanel_EmailBCCNameAndAddresses().isDisplayed());
		baseClass.passedStep(
				"EmailBCCNamesAndAddresses field is displayed on metadata panel and field is a concatenation fields.");
		softassertion.assertAll();
	}
	
	
	/**
	 * Author :Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51565 Verify when enteres the long text to search in a document
	 */

	@Test(description ="RPMXCON-51565",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyEntersLongTextToSearchDocument() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51565");
		baseClass.stepInfo("Verify when enteres the long text to search in a document");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");

		// verify a enter longer text in scrolling left in SearchBox
		docView.verifyEnterLongTextInScrollLeft();
	}

	/**
	 * Author :Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-52240 Verify that for the document PDF generated with exception
	 * without hidden content should not show the icon for hidden properties and
	 * also the warning message
	 */

	@Test(description ="RPMXCON-52240",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocWithHiddenContentIconNotShowForHiddenProperties() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52240");
		baseClass.stepInfo(
				"Verify that for the document PDF generated with exception without hidden content should not show the icon for hidden properties and also the warning message");
		SessionSearch sessionsearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		String withoutHiddenDocId = "\"Native Review\"";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.projectName01);
		baseClass.stepInfo("Login as RMU");
		sessionsearch.basicContentSearch(withoutHiddenDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_MiniDocListIds(2));
		docView.getDocView_MiniDocListIds(2).waitAndClick(5);

		// verify Hidden info icon is not visible on DocView
		if (docViewRedact.hiddenInfoIcon().isDisplayed() == false) {
			baseClass.passedStep("Hidden info icon is not visible for document with hidden content");
		} else {
			baseClass.failedStep("Hidden info Icon is visible for document without hidden content");
		}
		baseClass.VerifyWarningMessage(
				"This document does not contain a PDF rendering. Please download the native to review.");
		baseClass.passedStep("Icon for hidden properties is not displayed ");
	}

	/**
	 * Author :Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-52242 Verify that for the document with "Native Review Required"
	 * exception and with no hidden content then hidden properties icon should not
	 * appear on doc view
	 */

	@Test(description ="RPMXCON-52242",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocWithNativeReviewRequiredAndNoHiddenContent() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52242");
		baseClass.stepInfo(
				"Verify that for the document with \"Native Review Required\" exception and with no hidden content then hidden properties icon should not appear on doc view");
		SessionSearch sessionsearch = new SessionSearch(driver);
		docViewRedact = new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		String withoutHiddenDocId = "\"Native Review Required\"";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.projectName01);
		baseClass.stepInfo("Login as RMU");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(withoutHiddenDocId);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();

		// verify Hidden info icon is not visible on DocView
		softassertion.assertFalse((docViewRedact.hiddenInfoIcon().isDisplayed()));
		if (docViewRedact.hiddenInfoIcon().isDisplayed() == false) {
			baseClass.passedStep("Hidden info icon is not visible for document with hidden content");
		} else {
			baseClass.failedStep("Hidden info Icon is visible for document without hidden content");
		}
		baseClass.VerifyWarningMessage(
				"This document does not contain a PDF rendering. Please download the native to review.");
		baseClass.passedStep("Hidden Properties icon not appear in docview page");
		softassertion.assertAll();
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
