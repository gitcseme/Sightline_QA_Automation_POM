package sightline.docview;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Regression8 {
	
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
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
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
		keywordPage.deleteKeyword(hitTerms);
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
		keywordPage.deleteKeyword(hitTerms1);

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
		docexp = new DocExplorerPage(driver);
		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		docexp.selectAllDocumentsFromCurrentPage();
		docexp.docExpViewInDocView();
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
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codingFormName, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid1);
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
		docView.ScrollAndSelectDocument(docid1);
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
		docexp = new DocExplorerPage(driver);
		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		baseClass.waitTime(5);
		docexp.selectAllDocumentsFromCurrentPage();
		docexp.docExpViewInDocView();
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
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, Input.codeFormName, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid1);
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
		docView.ScrollAndSelectDocument(docid1);
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
		docexp = new DocExplorerPage(driver);
		// DocExploer to viewindocView Page
		baseClass.stepInfo("DocExplorer Navigate To ViewInDocView");
		docexp.selectAllDocumentsFromCurrentPage();
		docexp.docExpViewInDocView();
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
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Reviwer is selecting assignment from Dashboard");
		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		docView.selectSourceDocIdInAvailableField("SourceDocID");
		baseClass.waitTime(2);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docView.ScrollAndSelectDocument(docid1);
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
		docView.ScrollAndSelectDocument(docid1);
		baseClass.waitTime(10);
		docView.getAddComment1().isElementAvailable(5);
		String afterText2 = docView.getAddComment1().getText();
		System.out.println(afterText2);
		softassertion.assertEquals(beforeText2, afterText2);
		baseClass.passedStep("After clicked codesameas with the doc comments is same on completed doc");
		driver.Navigate().refresh();

	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED ******");

	}

}
