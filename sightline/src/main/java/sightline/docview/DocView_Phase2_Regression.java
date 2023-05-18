package sightline.docview;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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

import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;
	DocViewPage docView;
	AssignmentsPage assignmentsPage;
	DocListPage doclist;

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
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	@DataProvider(name = "userDetails2")
	public Object[][] userLoginDetails2() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "userDetails3")
	public Object[][] userLoginDetails3() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.pa1FullName, Input.pa1userName, Input.pa1password } };
	}

	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	/**
	 * Author :Sakthivel date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51285 To verify that when user select redaction submenu, icons
	 * should be in On states
	 */

	@Test(description = "RPMXCON-51285", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyRedactionSubMenuIconsOnStates() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51285");
		baseClass.stepInfo("To verify that when user select redaction submenu, icons should be in On states");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.verifyPanel();
		baseClass.waitForElement(docView.getDocView_Text_redact());
		docView.getDocView_Text_redact().waitAndClick(5);
		baseClass.waitTime(10);
		String color = docView.get_textHighlightedColorOnRedactSubMenu().getWebElement().getCssValue("color");
		System.out.println(color);
		String ExpectedColor = org.openqa.selenium.support.Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		if (Input.iconColor.equalsIgnoreCase(ExpectedColor)) {
			baseClass.passedStep("Reduction submenu icon is highlighted red color is displayed successfully");
		} else {
			baseClass.failedStep("Reduction submenu icon is not highlighted");
		}
	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51744 Verify that all search hit terms should be displayed by
	 * default on the panel
	 */

	@Test(description = "RPMXCON-51744", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifySearchHitTermsDisplayedDefault() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51744");
		baseClass.stepInfo("To verify that when user select redaction submenu, icons should be in On states");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		SoftAssert softassertion = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as pa");
		String hitTerms = "The" + Utility.dynamicNameAppender();
		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.getPersistentHit(hitTerms);
		baseClass.stepInfo("Eye icon clicked to see the persistent hit panel");
		driver.waitForPageToBeReady();
		softassertion.assertTrue(docView.getHitPanleVerify(hitTerms).Displayed());
		baseClass.passedStep("persistent hit terms displayed on docview panel successfully");
		softassertion.assertAll();
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.deleteKeywordByName(hitTerms);
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as rmu");
		String hitTerms1 = "Then" + Utility.dynamicNameAppender();
		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms1, hitTerms1);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.getPersistentHit(hitTerms1);
		baseClass.stepInfo("Eye icon clicked to see the persistent hit panel");
		driver.waitForPageToBeReady();
		softassertion.assertTrue(docView.getHitPanleVerify(hitTerms1).Displayed());
		baseClass.passedStep("persistent hit terms displayed on docview panel successfully");
		softassertion.assertAll();
		loginPage.logout();

		// Login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login as rev");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.getPersistentHit(hitTerms1);
		baseClass.stepInfo("Eye icon clicked to see the persistent hit panel");
		driver.waitForPageToBeReady();
		softassertion.assertTrue(docView.getHitPanleVerify(hitTerms1).Displayed());
		baseClass.passedStep("persistent hit terms displayed on docview panel successfully");
		softassertion.assertAll();
		loginPage.logout();
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.deleteKeywordByName(hitTerms1);

	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51743 Verify that on hits panel option should be provided not to
	 * view the zero hit terms
	 */
	@Test(description = "RPMXCON-51743", enabled = true, alwaysRun = true, dataProvider = "userDetails", groups = {
			"regression" })
	public void verifyHitsPanelProvidedNotToView(String fullName, String userName, String password) throws Exception {

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51743");
		baseClass.stepInfo("Verify that on hits panel option should be provided not to view the zero hit terms");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();

		// login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("login as" + fullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.getEyeIcon().waitAndClick(10);
		baseClass.stepInfo("docView Eye Icon is Clicked Successfully");
		baseClass.passedStep("Persistent hits panel is opened");
		driver.waitForPageToBeReady();
		softassertion.assertTrue(docView.getPersistentToogle().isDisplayed());
		if (docView.getPersistentToogle().isDisplayed()) {
			baseClass.passedStep("Toggle is provided to view the zero hit terms is displayed successfully");

		} else {
			baseClass.failedStep("Toggle is provided to view the zero hit terms is not displayed");

		}
		softassertion.assertAll();

	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63735 Verify user can right clicks on the selected text and
	 * selects Copy action from viewer file and can select paste action in coding
	 * form field by right clicking
	 */

	@Test(description = "RPMXCON-63735", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifySelecttheTextRightClickCopyAndPasteInCodingFormField() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63735");
		baseClass.stepInfo(
				"Verify user can right clicks on the selected text and selects Copy action from viewer file and can select paste action in coding form field by right clicking");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docid = Input.DocIdCopyPaste;
		String docid1 = Input.DocIdCopyPaste1;

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		baseClass.stepInfo("Search Navigate To ViewInDocView");
		baseClass.waitTime(5);
		sessionsearch.basicSearchWithMetaDataQueryUsingSourceDOCID(docid);
		sessionsearch.viewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_CodingFormlist().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(5);
		docView.verifyClickRightClickAndCopyPasteRedacTextOnCommentBox();
		baseClass.waitTime(8);
		baseClass.waitForElement(docView.getAddComment1());
		docView.getAddComment1().isElementAvailable(5);
		String beforeText = docView.getAddComment1().getText();
		System.out.println(beforeText);
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");
		baseClass.stepInfo("Navigate to another document in mini doc list");
		docView.getCodeSameAsLast().waitAndClick(3);
		baseClass.stepInfo("clicked codesameas");
		baseClass.waitTime(5);
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(8);
		docView.getAddComment1().isElementAvailable(5);
		String afterText = docView.getAddComment1().getText();
		System.out.println(afterText);
		softassertion.assertEquals(beforeText, afterText);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on saved doc");
		driver.Navigate().refresh();

		// verify comment is same on save and next doc
		docView.ScrollAndSelectDocument(docid);
		driver.waitForPageToBeReady();
		docView.verifyClickRightClickAndCopyPasteRedacTextOnCommentBox();
		baseClass.waitTime(10);
		baseClass.waitForElement(docView.getAddComment1());
		docView.getAddComment1().isElementAvailable(5);
		String beforeText1 = docView.getAddComment1().getText();
		System.out.println(beforeText1);
		driver.waitForPageToBeReady();
		docView.getSaveAndNextButton().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");
		baseClass.waitTime(5);
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(8);
		baseClass.waitForElement(docView.getAddComment1());
		docView.getAddComment1().isElementAvailable(5);
		String afterText1 = docView.getAddComment1().getText();
		System.out.println(afterText1);
		softassertion.assertEquals(beforeText1, afterText1);
		baseClass.passedStep("After clicked saveandnext with the doc comments is same on saved doc");
		if (docView.getCopyPasteIcon().Enabled()) {
			baseClass.passedStep("Copy menu is retained after saved documents");
		} else {
			baseClass.failedStep("Copy menu is not retained after saved documents");

		}

		// Create assignment and go to docview
//		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.clickOnNewSearch();
		sessionsearch.multipleBasicContentSearch(Input.searchString1);
		sessionsearch.addPureHit();
		
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		String docId = docView.getDocumentWithoutRedaction();
//		docView.selectSourceDocIdInAvailableField("SourceDocID");
		MiniDocListPage minidoc = new MiniDocListPage(driver);
		minidoc.removingAllExistingFieldsAndAddingNewField("DocID");
		
//		Added on 
		DocViewRedactions docRedact = new DocViewRedactions(driver);
		docView.selectDocInMiniDocList(docId);
		baseClass.waitTime(3);
		baseClass.waitForElement(docRedact.activeDocId());
		String docID2 = docRedact.activeDocId().getText();
		
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docID2);
		baseClass.waitTime(5);
		docView.verifyClickRightClickAndCopyPasteRedacTextOnCommentBox();
		baseClass.waitTime(10);
		baseClass.waitForElement(docView.getAddComment1());
		String beforeText2 = docView.getAddComment1().getText();
		System.out.println(beforeText2);
		docView.getCompleteDocBtn().waitAndClick(2);
		baseClass.stepInfo("Document completed successfully");
		baseClass.stepInfo("Navigate to another document in mini doc list");
		docView.getCodeSameAsLast().waitAndClick(3);
		baseClass.stepInfo("clicked codesameas");
		baseClass.waitTime(5);
		docView.ScrollAndSelectDocument(docID2);
		baseClass.waitTime(10);
		baseClass.waitForElement(docView.getAddComment1());
		String afterText2 = docView.getAddComment1().getText();
		System.out.println(afterText2);
		softassertion.assertEquals(beforeText2, afterText2);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on completed doc");
		driver.Navigate().refresh();

	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63805 Verify user can select the text including special characters
	 * and perform Copy -Paste by using "Ctrl C" from viewer file and "Ctrl V" in to
	 * coding form field
	 */

	@Test(description = "RPMXCON-63805", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifySelecttheTextCopyAndPasteFileInViewToCodingFormField() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63805");
		baseClass.stepInfo(
				"Verify user can select the text including special characters and perform Copy -Paste by using \"Ctrl C\" from viewer file and \"Ctrl V\" in to coding form field");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docid = Input.DocIdCopyPaste;
		String docid1 = Input.DocIdCopyPaste1;

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");

		baseClass.stepInfo("Search Navigate To ViewInDocView");
		baseClass.waitTime(5);
		sessionsearch.basicSearchWithMetaDataQueryUsingSourceDOCID(docid);
		sessionsearch.viewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_CodingFormlist().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(8);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		baseClass.waitTime(10);
		baseClass.waitForElement(docView.getAddComment1());
		docView.getAddComment1().isElementAvailable(5);
		String beforeText = docView.getAddComment1().getText();
		System.out.println(beforeText);
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigate to another document in mini doc list");
		docView.getCodeSameAsLast().waitAndClick(3);
		baseClass.stepInfo("clicked codesameas");
		baseClass.waitTime(8);
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		String afterText = docView.getAddComment1().getText();
		System.out.println(afterText);
		softassertion.assertEquals(beforeText, afterText);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on saved doc");
		driver.Navigate().refresh();

		// Create assignment and go to docview
//		Added on 24
//		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.addNewSearch();
		sessionsearch.multipleBasicContentSearch(Input.searchString1);
		sessionsearch.addPureHit();
//		
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codeFormName, 0);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		
		String docId = docView.getDocumentWithoutRedaction();
		
//		docView.selectSourceDocIdInAvailableField("SourceDocID");
		MiniDocListPage minidoc = new MiniDocListPage(driver);
		minidoc.removingAllExistingFieldsAndAddingNewField("DocID");
		
//		Added on 
		DocViewRedactions docRedact = new DocViewRedactions(driver);
		docView.selectDocInMiniDocList(docId);
		baseClass.waitTime(3);
		baseClass.waitForElement(docRedact.activeDocId());
		String docID2 = docRedact.activeDocId().getText();
		
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docID2);
		baseClass.waitTime(5);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		baseClass.waitForElement(docView.getAddComment1());
		String beforeText2 = docView.getAddComment1().getText();
		System.out.println(beforeText2);
		docView.getCompleteDocBtn().waitAndClick(2);
		baseClass.stepInfo("Document completed successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigate to another document in mini doc list");
		docView.getCodeSameAsLast().waitAndClick(3);
		baseClass.stepInfo("clicked codesameas");
		baseClass.waitTime(5);
		docView.ScrollAndSelectDocument(docID2);
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		baseClass.waitForElement(docView.getAddComment1());
		String afterText2 = docView.getAddComment1().getText();
		System.out.println(afterText2);
		softassertion.assertEquals(beforeText2, afterText2);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on completed doc");
		driver.Navigate().refresh();

	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63732 Verify user can select the text and perform Copy -Paste by
	 * using "Ctrl C" from viewer file and "Ctrl V" in to coding form field
	 */

	@Test(description = "RPMXCON-63732", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifySelecttheTextCopyAndPasteInCodingFormField() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63732");
		baseClass.stepInfo(
				"Verify user can select the text and perform Copy -Paste by using \"Ctrl C\" from viewer file and \"Ctrl V\" in to coding form field");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String docid = Input.DocIdCopyPaste;
		String docid1 = Input.DocIdCopyPaste1;

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		baseClass.stepInfo("Search Navigate To ViewInDocView");
		baseClass.waitTime(5);
		sessionsearch.basicSearchWithMetaDataQueryUsingSourceDOCID(docid);
		sessionsearch.viewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return docView.getDocView_CodingFormlist().Displayed();
			}
		}), Input.wait30);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(5);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		String beforeText = docView.getAddComment1().getText();
		System.out.println(beforeText);
		baseClass.stepInfo("Navigate to another document in mini doc list");
		docView.getCodeSameAsLast().waitAndClick(3);
		baseClass.stepInfo("clicked codesameas");
		baseClass.waitTime(5);
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(8);
		docView.getAddComment1().isElementAvailable(5);
		String afterText = docView.getAddComment1().getText();
		System.out.println(afterText);
		softassertion.assertEquals(beforeText, afterText);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on saved doc");
		driver.Navigate().refresh();

		// verify comment is same on save and next doc
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid);
		driver.waitForPageToBeReady();
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		String beforeText1 = docView.getAddComment1().getText();
		System.out.println(beforeText1);
		driver.waitForPageToBeReady();
		docView.getSaveAndNextButton().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");
		baseClass.waitTime(8);
		docView.ScrollAndSelectDocument(docid);
		baseClass.waitTime(10);
		driver.waitForPageToBeReady();
		docView.getAddComment1().isElementAvailable(5);
		String afterText1 = docView.getAddComment1().getText();
		System.out.println(afterText1);
		softassertion.assertEquals(beforeText1, afterText1);
		baseClass.passedStep("After clicked saveandnext with the doc comments is same on saved doc");
		if (docView.getCopyPasteIcon().Enabled()) {
			baseClass.passedStep("Copy menu is retained after saved documents");
		} else {
			baseClass.failedStep("Copy menu is not retained after saved documents");

		}

		// Create assignment and go to docview
//		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.clickOnNewSearch();
		sessionsearch.multipleBasicContentSearch(Input.searchString1);
		sessionsearch.addPureHit();
		sessionsearch.bulkAssign();
		
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		
		String docId = docView.getDocumentWithoutRedaction();
//		docView.selectSourceDocIdInAvailableField("SourceDocID");
		MiniDocListPage minidoc = new MiniDocListPage(driver);
		minidoc.removingAllExistingFieldsAndAddingNewField("DocID");
		
//		Added on 
		DocViewRedactions docRedact = new DocViewRedactions(driver);
		docView.selectDocInMiniDocList(docId);
		baseClass.waitTime(3);
		baseClass.waitForElement(docRedact.activeDocId());
		String docID2 = docRedact.activeDocId().getText();
	
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docID2);
		baseClass.waitTime(5);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		String beforeText2 = docView.getAddComment1().getText();
		System.out.println(beforeText2);
		docView.getCompleteDocBtn().waitAndClick(2);
		baseClass.stepInfo("Document completed successfully");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigate to another document in mini doc list");
		docView.getCodeSameAsLast().waitAndClick(3);
		baseClass.stepInfo("clicked codesameas");
		baseClass.waitTime(5);
		docView.ScrollAndSelectDocument(docID2);
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		String afterText2 = docView.getAddComment1().getText();
		System.out.println(afterText2);
		softassertion.assertEquals(beforeText2, afterText2);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on completed doc");
		driver.Navigate().refresh();

	}

	/**
	 * @author Sakthivel ModifyDate:02/08/2022 RPMXCON-48811
	 * @throws Exception
	 * @Description Verify that "Text Highlighting" functionality is working proper
	 *              on DocView Screen.
	 * 
	 */
	@Test(description = "RPMXCON-48811", enabled = true, groups = { "regression" })
	public void verifyHighlightingTextIsWorkingOnDocViewScreen() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-48811");
		baseClass.stepInfo("Verify that \"Text Highlighting\" functionality is working proper on DocView Screen.");
		SessionSearch sessionsearch = new SessionSearch(driver);
		KeywordPage keywordPage = new KeywordPage(driver);
		String hitTerms = "Test" + Utility.dynamicNameAppender();
		;
		String color = "Red";
		DocViewPage docView = new DocViewPage(driver);

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.addKeywordWithColor(hitTerms, color);
		baseClass.stepInfo("Text Highlighting option color is in " + color);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("page is refreshed");
		driver.scrollPageToTop();
		docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
		loginPage.logout();

		// Login AS REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login as REV");
		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("page is refreshed");
		driver.scrollPageToTop();
		docView.verifyPersistingHitsHighlightingTextInSelectedDoc(hitTerms);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keywordPage.deleteKeywordByName(hitTerms);
		loginPage.logout();
	}

	/**
	 * Author : date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63743 Verify that when Copy menu is selected from doc view then on
	 * navigating to another document from document navigation previously selected
	 * panels/menus should retain
	 */

	@Test(description = "RPMXCON-63743", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyCopyMenuSelectedDocViewNavigateToDocNavigation() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63743");
		baseClass.stepInfo(
				"Verify that when Copy menu is selected from doc view then on navigating to another document from document navigation previously selected panels/menus should retain");
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert softassertion = new SoftAssert();
		String docid = "ID00000152";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		docexp = new DocExplorerPage(driver);
		DocListPage doclist = new DocListPage(driver);

		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
		doclist.getApplyFilter().waitAndClick(10);
		baseClass.waitTime(3);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(docid);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");

		// verify select icon is loaded
		baseClass.waitForElement(docView.getDocView_Next());
		docView.getDocView_Next().waitAndClick(5);
		driver.waitForPageToBeReady();
		String docId1 = docView.getDocView_CurrentDocId().getText();
		String nextDoc = docView.getDocView_Next().GetAttribute("value");
		System.out.println(nextDoc);
		baseClass.stepInfo("Navigated to next document is loaded in viewer ");
		baseClass.waitTime(5);
		docView.verifyCopyandPasteIconStatus();
		baseClass.waitForElement(docView.getDocView_Last());
		docView.getDocView_Last().waitAndClick(5);
		baseClass.waitTime(5);
		String docId2 = docView.getDocView_CurrentDocId().getText();
		System.out.println(docId2);
		String lastDoc = docView.getDocView_NumTextBox().GetAttribute("value");
		System.out.println(lastDoc);
		softassertion.assertEquals(lastDoc, "14");
		baseClass.stepInfo("Navigated to last document is loaded in viewer ");
		baseClass.waitTime(5);
		docView.verifyCopyandPasteIconStatus();
		baseClass.waitForElement(docView.getDocView_Previous());
		docView.getDocView_Previous().waitAndClick(5);
		driver.waitForPageToBeReady();
		String docId3 = docView.getDocView_CurrentDocId().getText();
		String previousDoc = docView.getDocView_NumTextBox().GetAttribute("value");
		softassertion.assertEquals(previousDoc, "13");
		baseClass.stepInfo("Navigated to previous document is loaded in viewer");
		baseClass.waitTime(5);
		docView.verifyCopyandPasteIconStatus();
		baseClass.waitForElement(docView.getDocView_First());
		docView.getDocView_First().waitAndClick(5);
		driver.waitForPageToBeReady();
		String docId4 = docView.getDocView_CurrentDocId().getText();
		String FirstDoc = docView.getDocView_NumTextBox().GetAttribute("value");
		softassertion.assertEquals(FirstDoc, "1");
		baseClass.stepInfo("Navigated to first document is loaded in viewer ");
		baseClass.waitTime(5);
		docView.verifyCopyandPasteIconStatus();

		// Verify Select docs in History button
		baseClass.waitTime(5);
		String[] docids = { docId1, docId2, docId3, docId4 };
		baseClass.waitForElement(docView.getDocView_HistoryButton());
		docView.getDocView_HistoryButton().waitAndClick(5);
		baseClass.passedStep("User clicked clock icon in minidoclist");
		baseClass.waitTime(5);
		int count = docView.getselectDocsFromClockIcon().size();
		ArrayList<String> dataList = new ArrayList<String>();
		for (int i = 1; i <= count; i++) {
			String docs = docView.getselectDocHistory(i).getText();
			System.out.println(docs);
			dataList.add(docs);
		}
		System.out.println(dataList);
		for (int j = 0; j < docids.length; j++) {
			baseClass.compareListWithOnlyOneString(dataList, docids[j], docids[j] + "is present in the history",
					docids[j] + " is not present in the history");
		}

		baseClass.waitForElement(docView.getselectDocFromClckIcon());
		docView.getselectDocFromClckIcon().waitAndClick(5);
		baseClass.passedStep("User selected the document from history drop down as expected");
		baseClass.waitTime(5);
		docView.verifyCopyandPasteIconStatus();
		softassertion.assertAll();
	}

	/**
	 * Author :Iyappan date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63747 Verify user can apply the saved coding stamp which includes
	 * Comments saved with the Copy menu
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-63747", enabled = true, alwaysRun = true, groups = { "regression" }, priority = 69)
	public void verifyUserCanApplySavedStampcommentsSavedCopyMenu() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63747");
		baseClass.stepInfo(
				"Verify user can apply the saved coding stamp which includes Comments saved with the Copy menu ");
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		assignmentsPage = new AssignmentsPage(driver);
		doclist = new DocListPage(driver);
		docexp = new DocExplorerPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String docid = "T2541D";
		String docid1 = "T2507D";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		baseClass.waitForElement(docexp.getDocExp_DocFiletypeSearchName());
		docexp.getDocExp_DocFiletypeSearchName().SendKeys("Text");
		doclist.getApplyFilter().waitAndClick(10);

		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		if (doclist.getYesAllPageDocs().isDisplayed()) {
			doclist.getYesAllPageDocs().waitAndClick(5);
			doclist.getPopUpOkBtn().waitAndClick(5);
		}
		baseClass.waitTime(5);
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		docView.ScrollAndSelectDocument(docid);
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.scrollPageToTop();
		docView.perfromCodingStampSelection(Input.stampColours);
		baseClass.waitForElement(docView.getMiniDocId(docid));
		docView.getMiniDocId(docid).waitAndClick(2);
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampSelection);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getCodingFormSaveThisForm());
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.passedStep("Applied coding saved successfully");
		baseClass.waitForElement(docView.getDocView_MiniDocListIds(3));
		docView.getDocView_MiniDocListIds(3).waitAndClick(2);
		docView.editCodingForm(comment);
		docView.clickGearIconOpenCodingFormChildWindow();
		docView.switchTochildWindow();
		docView.codingStampButton();
		docView.switchToNewWindow(1);
		docView.popUpAction(fieldText, Input.stampColour);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		docView.switchTochildWindow();
		String getAttribute = docView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		if (getAttribute.equals(comment)) {
			baseClass.passedStep("Comment is displayed on codingform panel successfully");
		} else {
			baseClass.failedStep("not displayed");
		}
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		docView.closeWindow(1);
		docView.switchToNewWindow(1);
		baseClass.passedStep("Applied coding saved successfully");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.deleteStampColour(Input.stampColours);
		docView.deleteStampColour(Input.stampSelection);
		docView.deleteStampColour(Input.stampColour);
		loginPage.logout();

		// Create assignment and go to docview
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentsPage.assignmentCreation(assname, codingForm);
		assignmentsPage.assignmentDistributingToReviewer();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid1);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		driver.scrollPageToTop();
		docView.perfromCodingStampSelection(Input.stampColours);
		baseClass.CloseSuccessMsgpopup();
		baseClass.waitForElement(docView.getMiniDocId(docid1));
		docView.getMiniDocId(docid1).waitAndClick(2);
		docView.editCodingForm();
		docView.perfromCodingStampSelection(Input.stampSelection);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		docView.completeButton();
		docView.editCodingForm(comment);
		docView.codingStampButton();
		docView.popUpAction(fieldText, Input.stampColour);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		baseClass.CloseSuccessMsgpopup();
		String getAttribute1 = docView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		if (getAttribute1.equals(comment)) {
			baseClass.passedStep("Comment is displayed on codingform panel successfully");
		} else {
			baseClass.failedStep("Not displayed");
		}
		docView.codingStampButton();
		baseClass.passedStep("Applied coding saved successfully");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.deleteStampColour(Input.stampColours);
		docView.deleteStampColour(Input.stampSelection);
		docView.deleteStampColour(Input.stampColour);
	}
	/**
	 * Author :Krishna date: 3/08/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-65057 Verify that error message should be displayed when document
	 * comments entered with < > * ; ‘ / ( ) # & from DocView
	 * 
	 */
	@Test(description = "RPMXCON-65057", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyErrorMsgDisplayedDocCommentEnteredFromDocView() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-65057");
		baseClass.stepInfo(
				"Verify that error message does not display and application accepts - when document comments entered with  < > * ; ‘ / ( ) # & ”  from DocView");
		DocViewPage docView = new DocViewPage(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String Specialchar = "< > * ; ‘ / ( ) #";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		docexp = new DocExplorerPage(driver);
		// DocExploer to viewindocView Page
		
//		Added o
		baseClass.waitForElement(docexp.getDocExplorerTabAfterDashBoard());
		docexp.getDocExplorerTabAfterDashBoard().waitAndClick(5);
		
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		docexp.selectAllDocumentsFromCurrentPage();
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.editCodingForm(Specialchar);
		baseClass.stepInfo("edit codingform and Clicked save button");
		docView.getCodingFormSaveThisForm().waitAndClick(3);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		baseClass.passedStep("Error message is NOT displayed document comments entered with Special character");
		driver.Navigate().refresh();
		baseClass.handleAlert();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		docView.editCodingForm(Specialchar);
		baseClass.stepInfo("edit codingform and clicked save and next button");
		docView.getSaveAndNextButton().waitAndClick(3);
		baseClass.VerifySuccessMessage("Applied coding saved successfully");
		baseClass.passedStep("Error message is NOT displayed document comments entered with Special character");
		loginPage.logout();

		// Create assignment and go to docview
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviewer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docView.editCodingForm(Specialchar);
		baseClass.stepInfo("edit codingform and clicked completed button");
		docView.getCompleteDocBtn().waitAndClick(2);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Error message is NOT displayed document comments entered with Special character");

	}
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-51985 Verify in-doc search highlighting is working for Searchable
	 * PDF (with Uploaded Data set)
	 * 
	 */
	@Test(description = "RPMXCON-51985", enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyDocHighlightingForSearchablePdfDataSet() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51985");
		baseClass.stepInfo("Verify in-doc search highlighting  is working for Searchable PDF (with Uploaded Dataset)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		DataSets dataset = new DataSets(driver);
		String text = "T";
		String Dataset = "ExtendedCharacters";
		DocListPage doc = new DocListPage(driver);
		String tagName = "tag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage	tagsAndFoldersPage = new TagsAndFoldersPage(driver);

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PA");
		dataset.navigateToDataSetsPage();
		baseClass.stepInfo("Navigating to dataset page");
		dataset.SelectingUploadedDataSets();
		baseClass.passedStep("Dataset is successfully published");
		driver.waitForPageToBeReady();
		dataset.SearchDataSetsInDocView(Dataset);
		baseClass.stepInfo("Selecting uploaded dataset and navigating to docview page");
		
		// verifying a corresponding text and highlighting a document.
		baseClass.waitTime(3);
		docView.verifyDisplaysTheDefaultPdfInDocView();
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
		tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
		driver.waitForPageToBeReady();
		dataset.navigateToDataSetsPage();
		driver.waitForPageToBeReady();
		dataset.SelectingUploadedDataSets();
		driver.waitForPageToBeReady();
		dataset.SearchDataSetsInDocList(Dataset);
		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagName);
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU");
		sessionsearch.switchToWorkproduct();
		sessionsearch.selectTagInASwp(tagName);
		baseClass.waitForElement(sessionsearch.getQuerySearchButton());
		sessionsearch.getQuerySearchButton().waitAndClick(3);
		sessionsearch.ViewInDocViews();
		
		// verifying a corresponding text and highlighting a document.
		baseClass.waitTime(3);
		docView.verifyDisplaysTheDefaultPdfInDocView();
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Login as REV");
		sessionsearch.switchToWorkproduct();
		sessionsearch.selectTagInASwp(tagName);
		sessionsearch.getQuerySearchButton().waitAndClick(3);
		sessionsearch.ViewInDocViews();
		
		// verifying a corresponding text and highlighting a document.
		baseClass.waitTime(3);
		docView.verifyDisplaysTheDefaultPdfInDocView();
		docView.verifyCorrespondingTextIsHighlightedOnDocs(text);
	}
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-63768 Verify Production should generated without comments if user
	 * saved Comments in the documents with 'Copy' menu in DocView
	 * 
	 * 
	 */
	@Test(description="RPMXCON-63768",enabled = true, alwaysRun = true, groups = { "regression" })
	public void verifyProductionGenerateWithoutCommentsDocsWithCopMenu() throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-63768");
		baseClass.stepInfo(
				"Verify Production should generated without comments if user saved Comments in the documents with 'Copy' menu in DocView");
		DocViewPage docView = new DocViewPage(driver);
		String docid = "ID00003432";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as Rmu");
		docexp = new DocExplorerPage(driver);
		DocListPage doclist = new DocListPage(driver);
		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		baseClass.waitForElement(docexp.getDocIdColumnTextField());
		docexp.getDocIdColumnTextField().SendKeys(docid);
		doclist.getApplyFilter().waitAndClick(10);
		baseClass.waitTime(3);
		docexp.getDocExp_SelectAllDocs().isElementAvailable(10);
		docexp.getDocExp_SelectAllDocs().Click();
		driver.waitForPageToBeReady();
		docexp.docExpViewInDocView();
		driver.waitForPageToBeReady();
		docView.getDocView_CodingFormlist().waitAndClick(5);
		docView.getDocView_CodingFormlist().selectFromDropdown().selectByVisibleText("Default Project Coding Form");
		baseClass.waitTime(3);
		docView.verifyCopyAndPasteRedacTextOnCommentBox();
		baseClass.waitTime(3);
		docView.getAddComment1().isElementAvailable(5);
		String Commenttext = docView.getAddComment1().getText();
		System.out.println(Commenttext);
		docView.getCodingFormSaveThisForm().waitAndClick(2);
		baseClass.stepInfo("Document saved successfully");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.isfileisExists(TiffFile);
		page.OCR_Verification_In_Generated_Tiff_tess4jNotDisplayed(FirstFile, LastFile, prefixID, suffixID,Commenttext);
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
