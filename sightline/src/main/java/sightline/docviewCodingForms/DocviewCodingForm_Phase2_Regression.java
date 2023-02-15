package sightline.docviewCodingForms;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocviewCodingForm_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	UserManagement userManagementPage;
	CommentsPage commentsPage;
	DocExplorerPage docExplorerPage;

	List<String> cfName = null;
	String codingform = "CFA" + Utility.dynamicNameAppender();
	String cfTwo = "CFB" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	@DataProvider(name = "rmuRev")
	public Object[][] rmuRev() {
		Object[][] users = { { "rmu", Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ "rev", Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that when user navigates from one doc to another doc,
	 *              irrespective of which coding form the was on for the previous
	 *              document, for the next doc, it always shows the default coding
	 *              form set.
	 */
	@Test(description = "RPMXCON-65632", enabled = true, dataProvider = "rmuRev", groups = { "regression" })
	public void validationForDefaultCfWhenNavigates(String roll, String username, String password, String fullName)
			throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65632");
		baseClass.stepInfo("Verify that when user navigates from one doc to another doc, "
				+ "irrespective of which coding form the was on for the previous document, "
				+ "for the next doc, it always shows the default coding form set.");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		loginPage.loginToSightLine(username, password);
		UtilityLog.info("Logged in as User: " + username);

		// creating codingform
		if (roll == "rmu") {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
			codingForm.createWithOneMetaData(codingform);
			baseClass.waitTime(5);
			cfName = codingForm.checkingBelow15CFCheckboxForSG();
			codingForm.makingDefaultCfToSg(Input.codingFormName);
			codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
		}
		// navigating to docview page
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("Navigating to docview page");

		// clicking cf panel dropdown
		List<String> expected = new ArrayList<String>();
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		docViewPage.getDocViewDrpDwnCf().waitAndClick(5);
		baseClass.waitTime(5);
		List<WebElement> cfDocviewPnael = docViewPage.getDocViewDrpDwnCf().selectFromDropdown().getOptions();
		for (WebElement webElement : cfDocviewPnael) {
			expected.add(webElement.getText().trim().toString());
		}
		Collections.sort(expected);
		Collections.sort(cfName);
		baseClass.waitTime(5);
		softAssertion.assertEquals(cfName, expected);
		baseClass.stepInfo("Assigned coding form to sg are displaying in cf panel dropdown");
		// selecting different codingform
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		docViewPage.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(codingform);
		// navigating to otherdocument
		baseClass.waitForElement(docViewPage.getClickDocviewID(2));
		docViewPage.getClickDocviewID(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		// validating when navigating to other docs default cf should assign
		String currentDRp = baseClass.getCurrentDropdownValue(docViewPage.getDocViewDrpDwnCf());
		System.out.println(currentDRp);
		baseClass.compareTextViaContains(currentDRp, Input.codeFormName, "pass", "fail");
		baseClass.passedStep("Default coding form assigned in cf panel,when navigate to other docs");

		// validating for child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.switchToNewWindow(2);
		// selecting different codingform
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCfChild());
		docViewPage.getDocViewDrpDwnCfChild().selectFromDropdown().selectByVisibleText(codingform);
		// navigating to otherdocument
		docViewPage.switchToNewWindow(1);
		baseClass.waitForElement(docViewPage.getClickDocviewID(2));
		docViewPage.getClickDocviewID(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.switchToNewWindow(2);
		baseClass.waitTime(5);
		// validating when navigating to other docs default cf should assign
		String currentDRpChild = baseClass.getCurrentDropdownValue(docViewPage.getDocViewDrpDwnCfChild());
		System.out.println(currentDRpChild);
		baseClass.compareTextViaContains(currentDRpChild, Input.codeFormName, "pass", "fail");
		baseClass.passedStep("Default coding form assigned in cf panel,when navigate to other docs");
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
//		codingForm.deleteCodingFormWithAlert(codingform,codingform);
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that when the user switches to a different coding form
	 *              and if the currently presented coding form has no new coding or
	 *              coding changes applied, then form simply switches without any
	 *              warning or any save changes.
	 */
	@Test(description = "RPMXCON-65507", enabled = true, dataProvider = "rmuRev", groups = { "regression" })
	public void validationNonErrorMsg(String roll, String username, String password, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65507");
		baseClass.stepInfo(
				"Verify that when the user switches to a different coding form and if the currently presented "
						+ "coding form has no new coding or coding changes applied, then form simply switches "
						+ "without any warning or any save changes.");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		String saveSearch = "save" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(username, password);
		UtilityLog.info("Logged in as User: " + username);

		// creating codingform
		if (roll == "rmu") {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
			codingForm.createWithOneMetaData(cfTwo);
			baseClass.waitTime(5);
			cfName = codingForm.checkingBelow15CFCheckboxForSG();
			System.out.println(cfName);
			codingForm.makingDefaultCfToSg(Input.codingFormName);
			codingForm.validatingDefaultSgFromManageScreen(Input.codingFormName);
			// navigating to docview page
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewInDocView();
			baseClass.stepInfo("Navigating to docview page");
		}
		if (roll == "rev") {
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.saveSearch(saveSearch);
			savedSearch.savedSearchToDocView(saveSearch);
		}
		// clicking cf panel dropdown
		List<String> expected = new ArrayList<String>();
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		docViewPage.getDocViewDrpDwnCf().waitAndClick(5);
		List<WebElement> cfDocviewPnael = docViewPage.getDocViewDrpDwnCf().selectFromDropdown().getOptions();
		for (WebElement webElement : cfDocviewPnael) {
			expected.add(webElement.getText().trim().toString());
		}
		Collections.sort(expected);
		Collections.sort(cfName);
		System.out.println(cfName);
		baseClass.waitTime(5);
		for (int i = 1; i < expected.size(); i++) {
			baseClass.compareListWithOnlyOneString(cfName, expected.get(i), "pass", "fail");

		}
		baseClass.stepInfo("Assigned coding form to sg are displaying in cf panel dropdown");
		// selecting different codingform
		baseClass.waitTime(5);
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		docViewPage.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(expected.get(1));

		// popup message without changes in cf panel
		boolean popUPCf = docViewPage.getCfPopUpMsgWithoutChanges().isElementAvailable(2);
		softAssertion.assertFalse(popUPCf);
		baseClass.passedStep("PopUp message not appeared when cf not get editing and changing cf");

		// validating for child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = docViewPage.switchTochildWindow();
		// selecting different codingform
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCfChild());
		docViewPage.getDocViewDrpDwnCfChild().selectFromDropdown().selectByVisibleText((expected.get(1)));
		// poup in child window
		boolean popUPCfChild = docViewPage.getCfPopUpMsgWithoutChanges().isElementAvailable(2);
		softAssertion.assertFalse(popUPCfChild);
		baseClass.passedStep("PopUp message not appeared when cf not get editing and changing cf in child");
		docViewPage.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();

		// from docexplorer
		if (roll == "rmu") {
			this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			for (int i = 1; i < 3; i++) {
				baseClass.waitForElement(docExplorerPage.getClickDocExplorerID(i));
				docExplorerPage.getClickDocExplorerID(i).waitAndClick(5);
			}
			docExplorerPage.docExpViewInDocView();
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
			docViewPage.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText((expected.get(1)));

			// popup message without changes in cf panel
			boolean popUPCfEX = docViewPage.getCfPopUpMsgWithoutChanges().isElementAvailable(2);
			softAssertion.assertFalse(popUPCfEX);
			baseClass.passedStep("PopUp message not appeared when cf not get editing and changing cf");

			// validating for child window
			docViewPage.clickGearIconOpenCodingFormChildWindow();
			String parentWindowDoc = docViewPage.switchTochildWindow();
			// selecting different codingform
			baseClass.waitForElement(docViewPage.getDocViewDrpDwnCfChild());
			docViewPage.getDocViewDrpDwnCfChild().selectFromDropdown().selectByVisibleText((expected.get(1)));
			// poup in child window
			boolean popUPCfChildEX = docViewPage.getCfPopUpMsgWithoutChanges().isElementAvailable(2);
			softAssertion.assertFalse(popUPCfChildEX);
			baseClass.passedStep("PopUp message not appeared when cf not get editing and changing cf in child");
			docViewPage.childWindowToParentWindowSwitching(parentWindowDoc);
			driver.waitForPageToBeReady();
		}

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : Verify that we have combo box in "Coding Form" panel when we
	 *              view Doc in DocView.
	 */
	@Test(description = "RPMXCON-65423", enabled = true, dataProvider = "rmuRev", groups = { "regression" })
	public void validationDropDownAvailable(String roll, String username, String password, String fullName)
			throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-65423");
		baseClass.stepInfo("Verify that we have combo box in \"Coding Form\" panel when we view Doc in DocView.");
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		docExplorerPage = new DocExplorerPage(driver);
		savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(username, password);
		UtilityLog.info("Logged in as User: " + username);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// clicking cf panel dropdown
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
		boolean flag = docViewPage.getDocViewDrpDwnCf().isElementAvailable(2);
		softAssertion.assertTrue(flag);

		// validating for child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = docViewPage.switchTochildWindow();
		// clicking cf panel dropdown
		baseClass.waitForElement(docViewPage.getDocViewDrpDwnCfChild());
		boolean flagTwo = docViewPage.getDocViewDrpDwnCfChild().isElementAvailable(2);
		softAssertion.assertTrue(flagTwo);
		docViewPage.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		baseClass.passedStep("Combobox and dropdown option list in docview page");

		// from docexplorer
		if (roll == "rmu") {
			this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
			for (int i = 1; i < 3; i++) {
				baseClass.waitForElement(docExplorerPage.getClickDocExplorerID(i));
				docExplorerPage.getClickDocExplorerID(i).waitAndClick(5);
			}
			docExplorerPage.docExpViewInDocView();
			driver.waitForPageToBeReady();
			// clicking cf panel dropdown
			baseClass.waitForElement(docViewPage.getDocViewDrpDwnCf());
			boolean flagEX = docViewPage.getDocViewDrpDwnCf().isElementAvailable(2);
			softAssertion.assertTrue(flagEX);

			// validating for child window
			docViewPage.clickGearIconOpenCodingFormChildWindow();
			String parentWindowDoc = docViewPage.switchTochildWindow();
			// clicking cf panel dropdown
			baseClass.waitForElement(docViewPage.getDocViewDrpDwnCfChild());
			boolean flagEXCF = docViewPage.getDocViewDrpDwnCfChild().isElementAvailable(2);
			softAssertion.assertTrue(flagEXCF);
			docViewPage.childWindowToParentWindowSwitching(parentWindowDoc);
			driver.waitForPageToBeReady();
			baseClass.stepInfo("When user navigates from docexplorer to docview drop dwon option listed");
		}

		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author :Indium-Baskar
	 * @Description : In Doc view upon selected Default Coding Form we should see
	 *              Complete and Code same as Button in Coding Panel
	 */

	@Test(description = "RPMXCON-65197", enabled = true, groups = { "regression" })
	public void codingFormChildWindowCodeSameAsLast() throws AWTException, InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-65197");
		baseClass.stepInfo("In Doc view upon selected Default Coding Form we should see Complete and "
				+ "Code same as Button in Coding Panel");
		// login as Rmu
		String assignmentName = "AssName" + Utility.dynamicNameAppender();
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		boolean sameLast = docViewPage.getCodeSameAsLast().Enabled();
		softAssertion.assertTrue(sameLast);
		boolean complete = docViewPage.getCompleteDocBtn().Enabled();
		softAssertion.assertTrue(complete);
		baseClass.passedStep("Code same as and complete button is clickable");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * @author Date:NA ModifyDate:NA RPMXCON-50967
	 * @throws Exception
	 * @Description Verify when user navigates from Basic Search/Saved search/Doc
	 *              List and coding form is assigned to security group, custom
	 *              coding form is editable
	 */
	@Test(description = "RPMXCON-50967", enabled = true, groups = { "regression" })
	public void verifyCustomCfIsEditable() throws Exception {
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-50967");
		base.stepInfo(
				"Verify when user navigates from Basic Search/Saved search/Doc List and coding form is assigned to security group, custom coding form is editable");
		CodingForm cf = new CodingForm(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert soft = new SoftAssert();
		String cfName = "coding" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		cf.navigateToCodingFormPage();

		// Creating Coding Form
		cf.createCodingform(cfName);
		// setting newly created coding Form as Default Coding Form
		baseClass.stepInfo("setting newly created coding Form as Default Coding Form.");
		driver.waitForPageToBeReady();
		cf.assignCodingFormToSG(cfName);

		// Searching Content document go to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();

		// custom CF is editable
		driver.waitForPageToBeReady();
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> CFInDocView = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListWithOnlyOneString(CFInDocView, cfName,
				cfName + "  custom coding forms is displayed on doc view page ",
				"creating coding forms is not displayed on doc view page");
		driver.waitForPageToBeReady();
		base.waitForElement(docView.getDocument_AddComment());
		soft.assertTrue(docView.getDocument_AddComment().isElementPresent());
		Boolean commentbox = docView.getDocument_AddComment().Enabled();
		if (commentbox == true) {
			base.waitForElement(docView.getDocument_AddComment());
			docView.getDocument_AddComment().Click();
			docView.getDocument_AddComment().SendKeys("comment");
			base.passedStep("Custom CF comment box is Editable on selected document as expected");
		} else {
			base.failedStep("custom CF comment box is not Editable");
		}
		soft.assertTrue(docView.getAttorney_ClientCheckBox().isElementPresent());
		Boolean checkbox = docView.getAttorney_ClientCheckBox().Enabled();
		if (checkbox == true) {
			base.waitForElement(docView.getAttorney_ClientCheckBox());
			docView.getAttorney_ClientCheckBox().waitAndClick(5);
			base.passedStep("Custom CF checkbox box is Editable on selected document as expected");
		} else {
			base.failedStep("Custom CF check box is not Editable");
		}
		base.passedStep(cfName + "   Custom coding form has been editable for the document successfully");
		soft.assertAll();
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Successfully login as Reviewer '" + Input.rev1userName + "'");
		// Searching Content document go to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		// custom CF is editable
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> CFInDocView1 = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListWithOnlyOneString(CFInDocView1, cfName,
				cfName + "  Custom coding forms is displayed on doc view page ",
				"Custom coding forms is not displayed on doc view page");
		driver.waitForPageToBeReady();
		base.waitForElement(docView.getDocument_AddComment());
		soft.assertTrue(docView.getDocument_AddComment().isElementPresent());
		Boolean commentbox1 = docView.getDocument_AddComment().Enabled();
		if (commentbox1 == true) {
			base.waitForElement(docView.getDocument_AddComment());
			docView.getDocument_AddComment().Click();
			docView.getDocument_AddComment().SendKeys("comment");
			base.passedStep("Custom CF comment box is Editable on selected document as expected");
		} else {
			base.failedStep("Custom CF comment box is not Editable");
		}
		soft.assertTrue(docView.getAttorney_ClientCheckBox().isElementPresent());
		Boolean checkbox1 = docView.getAttorney_ClientCheckBox().Enabled();
		if (checkbox1 == true) {
			base.waitForElement(docView.getAttorney_ClientCheckBox());
			docView.getAttorney_ClientCheckBox().waitAndClick(5);
			base.passedStep("Custom CF checkbox box is Editable on selected document as expected");
		} else {
			base.failedStep("Custom CF check box is not Editable");
		}
		base.passedStep(cfName + "   Custom coding form has been editable for the document successfully");
		soft.assertAll();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		cf.assignCodingFormToSG(Input.codeFormName);
		cf.deleteCodingForm(cfName,cfName);
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}

}
